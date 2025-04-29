/*
 * Created on 2004. 6. 28.
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.edutrack.community.dao;


import java.sql.SQLException;
import javax.sql.RowSet;
import com.edutrack.common.CommonUtil;
import com.edutrack.community.dto.CommMembersDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;


public class CommMembersDAO extends AbstractDAO {

	/**
	 * 시샵을 변경한다.
	 * 
	 * @param systemcode
	 * @param commid
	 * @param userid
	 * @param modid
	 * @return
	 * @throws DAOException
	 */
	public boolean changeMaster(String systemcode, String commid, String userid, String modid) throws DAOException {
		QueryStatement[] sqls = new QueryStatement[3];

		// 기존의 마스트를 정회원으로 바꿔준다.
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE comm_members ");
		sb.append("\n    SET user_level = 'A', mod_id =  ?, mod_date = ? ");
		sb.append("\n  WHERE system_code = ? ");
		sb.append("\n    AND comm_id = ? ");
		sb.append("\n    AND user_level = 'M' ");
		sql.setSql(sb.toString());
		sql.setString(modid);
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(systemcode);
		sql.setInteger(commid);

		log.debug("[changeMaster 1]" + sql.toString());

		sb.delete(0, sb.length());

		sqls[0] = sql;
		// 선택한 멤버를 시샵으로 바꾸어준다.
		sql = new QueryStatement();
		sb.append("\n UPDATE comm_members ");
		sb.append("\n    SET user_level = 'M', mod_id =  ?, mod_date = ? ");
		sb.append("\n  WHERE system_code = ? ");
		sb.append("\n    AND comm_id = ? ");
		sb.append("\n    AND user_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(modid);
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(systemcode);
		sql.setInteger(commid);
		sql.setString(userid);

		log.debug("[changeMaster 2]" + sql.toString());

		sqls[1] = sql;

		sb.delete(0, sb.length());

		sql = new QueryStatement();
		sb.append("\n UPDATE comm_info ");
		sb.append("\n    SET comm_master = ? ");
		sb.append("\n  WHERE system_code = ? ");
		sb.append("\n    AND comm_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(userid);
		sql.setString(systemcode);
		sql.setInteger(commid);

		log.debug("[changeMaster 3]" + sql.toString());

		sqls[2] = sql;

		boolean retVal = false;
		try {
			retVal = broker.executeUpdate(sqls);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 커뮤니티 멤버의 삭제
	 * 
	 * @param systemCode
	 * @param commId
	 * @param modId
	 * @param memberId
	 * @param useYn
	 * @return
	 * @throws DAOException
	 */
	public int commMemberDelete(String systemCode, String commId, String memberId) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" delete comm_members ");
		sb.append(" where system_code = ? and comm_id = ? and user_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(commId);
		sql.setString(memberId);

		log.debug("[systemCode]" + systemCode);
		log.debug("[commId]" + commId);
		log.debug("[commMemberUseYnChange]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 커뮤니티 멤버의 승인
	 * 
	 * @param systemCode
	 * @param commId
	 * @param memberId
	 * @param useYn
	 * @return
	 * @throws DAOException
	 */
	public int commMemberUseYnChange(String systemCode, String commId, String modId, String memberId, String useYn) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update comm_members set user_level = 'A',use_yn = ?, mod_id = ?, mod_date = ? ");
		sb.append(" where system_code = ? and comm_id = ? and user_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(useYn);
		sql.setString(modId);
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(systemCode);
		sql.setString(commId);
		sql.setString(memberId);

		log.debug("[useYn]" + useYn);
		log.debug("[modId]" + modId);
		log.debug("[systemCode]" + systemCode);
		log.debug("[commId]" + commId);
		log.debug("[commMemberUseYnChange]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 커뮤니티 멤버의 개인정보 수정(닉네임, 자기소개)
	 * 
	 * @param commMembers
	 * @return
	 * @throws DAOException
	 */
	public int editCommMember(CommMembersDTO commMembers) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update comm_members set user_nick = ? ,user_intro = ? , mod_id = ? ,mod_date = ? ");
		sb.append(" where system_code = ? and comm_id = ?  and user_id = ? ");
		sql.setSql(sb.toString());
		log.debug("-----user_id : " + commMembers.getUserId());
		sql.setString(commMembers.getUserNick());
		sql.setString(commMembers.getUserIntro());
		sql.setString(commMembers.getModId());
		sql.setString(CommonUtil.getCurrentDate());

		sql.setString(commMembers.getSystemCode());
		sql.setInteger(commMembers.getCommId());
		sql.setString(commMembers.getUserId());

		log.debug("[editCommMembersInfoChange]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 커뮤니티 멤버의 정보받아오기
	 * 
	 * @param systemCode
	 * @param commId
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCommMemberInfo(String systemCode, String commId, String userId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, user_nick, user_intro ");
		sb.append(" from comm_members");
		sb.append(" where system_code = ? and comm_id = ? and user_id = ? ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(commId);
		sql.setString(userId);

		//---- 디버그 출력
		log.debug("[getCommMemberInfo]" + sql.toString());
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			//---- rs.close() 는 반드시 jsp 페이지에서 해줄것!!!!!
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 커뮤니티 멤버의 개인정보수정
	 * 
	 * @param commCategory
	 * @return
	 * @throws DAOException
	 */

	/*
	 * public int editCommMember(CommMembersDTO commMembers) throws DAOException{ int retVal = 0; QueryStatement sql =
	 * new QueryStatement(); StringBuffer sb = new StringBuffer(); CommMemDTO commMemDto =
	 * UserBroker.getCommInfo(request); String commId = commMemDto.commId; sb.append(" update comm_members set user_nick = ?
	 * ,user_intro = ? , mod_id = ? ,mod_date = ? "); sb.append(" where system_code = ? and comm_id = ? and user_id = ?
	 * "); sql.setSql(sb.toString()); sql.setString(commMembers.getUserNick());
	 * sql.setString(commMembers.getUserIntro()); sql.setString(commMembers.getModId());
	 * sql.setString(CommonUtil.getCurrentDate()); sql.setString(commMembers.getSystemCode()); sql.setString(commId);
	 * sql.setString(commId); log.debug("-----commId : "+commId); log.debug("[editCommCategory]" + sql.toString()); try{
	 * retVal = broker.executeUpdate(sql); }catch(Exception e){ log.error(e.getMessage()); throw new
	 * DAOException(e.getMessage()); } return retVal; }
	 */

	public RowSet getCommMemberList(String systemCode, String commId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select m.user_id, ");
		sb.append(" (select user_name from users where system_code = m.system_code and user_id = m.user_id ) as user_name ");
		sb.append(" from comm_members m ");
		sb.append(" where m.system_code = ? and m.comm_id = ? and m.use_yn = 'Y' and m.user_level = 'A' ");
		sb.append(" order by m.user_id asc ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setInteger(commId);

		//---- 디버그 출력
		log.debug("[getCommMemberList]" + sql.toString());
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			//---- rs.close() 는 반드시 jsp 페이지에서 해줄것!!!!!
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 커뮤니티 멤버들의 리스트를 페이징 형태로 받아온다
	 * 
	 * @param curpage
	 * @param systemCode
	 * @param commId
	 * @param pfield
	 * @param pvalue
	 * @return
	 * @throws DAOException
	 */
	public ListDTO getCommMembersPagingList(int curpage, String systemCode, String commId, String pfield, String pvalue) throws DAOException {
		ListDTO retVal = null;
		try {
			StringBuffer sb = new StringBuffer();
			StringBuffer sbAlias = new StringBuffer();

			sbAlias.append(" system_code, comm_id, comm_name, user_id, user_name, user_nick, reg_date, user_level ");
			sb.append(" i.system_code, i.comm_id, i.comm_name, m.user_id, u.user_name, m.user_nick, m.reg_date, m.user_level ");

			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("i.comm_id");
			sql.setCutCol("i.comm_id");
			sql.setAlias(sbAlias.toString());
			sql.setSelect(sb.toString());
			sql.setFrom(" users u, comm_info i, comm_members m");

			StringBuffer where = new StringBuffer();
			where.append(" i.system_code=u.system_code and i.system_code=m.system_code and i.comm_id=m.comm_id and m.user_id=u.user_id and i.system_code= ? and m.use_yn= 'Y' and m.comm_id= ? ");
			sql.setString(systemCode);
			sql.setString(commId);

			if (!pfield.equals("")) {
				if (!pfield.equals("user_id"))
					where.append(" and " + pfield + " like ? ");
				else
					where.append(" and u." + pfield + " like ? ");
				sql.setString("%" + pvalue + "%");
			}

			sql.setWhere(where.toString());
			sql.setGroupby(sb.toString());

			log.debug("[getCommMembersPagingList]" + sql.toString());
			retVal = broker.executeListQuery(sql, curpage);

			return retVal;
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
	}

	/**
	 * 커뮤니티 신규회원 페이지리스트
	 * 
	 * @param curpage
	 * @param systemCode
	 * @param commId
	 * @param useYn : Y 일경우 승인 리스트, N 일경우 미승인 리스트
	 * @param pfield
	 * @param pvalue
	 * @return
	 * @throws DAOException
	 */
	public ListDTO getCommNewMembersPagingList(int curpage, String systemCode, String commId, String useYn) throws DAOException {
		ListDTO retVal = null;
		try {
			// List Sql문 생성
			StringBuffer sbAlias = new StringBuffer();
			sbAlias.append(" comm_id, comm_name, user_id, user_name, user_nick, reg_date, user_level, use_yn ");
			
			ListStatement sql = new ListStatement();
			StringBuffer where = new StringBuffer(" i.system_code=u.system_code and i.system_code=m.system_code and i.comm_id=m.comm_id and m.user_id=u.user_id and i.system_code= ? and m.use_yn= 'N' and m.comm_id= ? ");
			
			sql.setTotalCol("i.comm_id");
			sql.setCutCol("i.comm_id");
			sql.setAlias(sbAlias.toString());
			sql.setSelect(" i.comm_id as comm_id, i.comm_name as comm_name, m.user_id as user_id, u.user_name as user_name, m.user_nick as user_nick, m.reg_date as reg_date, m.user_level as user_level, m.use_yn as use_yn ");
			sql.setFrom(" users u, comm_info i , comm_members m");
			sql.setString(systemCode);
			sql.setString(commId);
			sql.setGroupby(" i.comm_id, i.comm_name, m.user_id, u.user_name, m.user_nick, m.reg_date, m.user_level, m.use_yn ");

			sql.setWhere(where.toString());

			log.debug("[getCommNewMembersPagingList]" + sql.toString());
			retVal = broker.executeListQuery(sql, curpage);

			return retVal;

		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
	}

}


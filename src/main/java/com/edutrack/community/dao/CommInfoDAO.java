/*
 * Created on 2004. 6. 28.
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.edutrack.community.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.RowSet;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.dto.CodeDTO;
import com.edutrack.community.dto.CommInfoDTO;
import com.edutrack.community.dto.CommMembersDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;


public class CommInfoDAO extends AbstractDAO {
	/**
	 * 게시판정보에 자유게시판
	 *
	 * @param commInfo
	 * @return
	 * @throws DAOException
	 */
	public int addAutoMakebbsBoardReg(CommInfoDTO commInfo) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into comm_bbs_info(system_code, comm_id, bbs_id, bbs_type, bbs_name, use_yn, disp_line, disp_page, reg_id, reg_date, editor_yn, voice_yn, file_use_yn, comment_use_yn) ");
		sb.append(" values(?, ?, 2, 'B','자유게시판','Y','10','10', ?, ?, 'Y', 'N', 'Y', 'Y') ");

		sql.setSql(sb.toString());

		sql.setString(commInfo.getSystemCode());
		sql.setInteger(commInfo.getCommId());
		sql.setString(commInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());

		log.debug("[addMakeCommMemberReg]--+++--" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 게시판정보에 자료실
	 *
	 * @param commInfo
	 * @return
	 * @throws DAOException
	 */
	public int addAutoMakebbsFilesReg(CommInfoDTO commInfo) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into comm_bbs_info (system_code, comm_id, bbs_id, bbs_type" +
												", bbs_name, use_yn, disp_line, disp_page" +
												", reg_id, reg_date, editor_yn, voice_yn" +
												", file_use_yn, file_limit, comment_use_yn) ");
		sb.append(" values(?, ?, 3, 'F'" +
							",'자료실','Y','10','10'" +
							", ?, ?, 'Y', 'N'" +
							", 'Y', 2, 'Y') ");

		sql.setSql(sb.toString());

		sql.setString(commInfo.getSystemCode());
		sql.setInteger(commInfo.getCommId());
		sql.setString(commInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());

		log.debug("[addMakeCommMemberReg]--+++--" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 게시판정보에 공지등록
	 *
	 * @param commInfo
	 * @return
	 * @throws DAOException
	 */
	public int addAutoMakebbsNoticeReg(CommInfoDTO commInfo) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" INSERT INTO comm_bbs_info (system_code, comm_id, bbs_id, bbs_type" +
												", bbs_name, use_yn, disp_line, disp_page" +
												", reg_id, reg_date, editor_yn, voice_yn" +
												", file_use_yn, comment_use_yn) ");
		sb.append(" values(?, ?, 1, 'N'" +
							",'공지사항','Y','10','10'" +
							", ?, ?, 'Y', 'N'" +
							", 'Y', 'Y') ");

		sql.setSql(sb.toString());

		sql.setString(commInfo.getSystemCode());
		sql.setInteger(commInfo.getCommId());
		sql.setString(commInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());

		log.debug("[addMakeCommMemberReg]--+++--" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}


	/**
	 * 동아리 자랑 게시판 만들기.
	 * 게시판목록에서는 보여지지 않는 게시판임
	 * @param commInfo
	 * @return
	 * @throws DAOException
	 */
	public int addAutoMakePrideBoardReg(CommInfoDTO commInfo) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into comm_bbs_info(system_code, comm_id, bbs_id, bbs_type" +
											", bbs_name, use_yn, disp_line, disp_page" +
											", reg_id, reg_date, editor_yn, voice_yn" +
											", file_use_yn, file_limit, comment_use_yn) ");
		sb.append(" values(?, ?, 4, 'P'" +
							",'동아리자랑','Y','10','10'" +
							", ?, ?, 'Y', 'N'" +
							", 'Y', 2, 'Y') ");

		sql.setSql(sb.toString());

		sql.setString(commInfo.getSystemCode());
		sql.setInteger(commInfo.getCommId());
		sql.setString(commInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());

		log.debug("[addAutoMakePrideBoardReg]--+++--" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}


	/**
	 * 동아리 만들기의 멤버등록
	 *
	 * @param commInfo
	 * @return
	 * @throws DAOException
	 */
	public int addMakeCommMemberReg(CommInfoDTO commInfo, String regName) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO comm_members(system_code, comm_id, user_id, user_level, " + "\n                          user_nick, use_yn, reg_id, reg_date) ");
		sb.append("\n values(?, ?, ?, 'M', ?, 'Y', ?, ?) ");

		sql.setSql(sb.toString());

		sql.setString(commInfo.getSystemCode());
		sql.setInteger(commInfo.getCommId());
		sql.setString(commInfo.getCommMaster());
		sql.setString(regName);
		sql.setString(commInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());

		log.debug("[addMakeCommMemberReg]--+++--" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 동아리 만들기의 등록
	 *
	 * @param commInfo
	 * @return
	 * @throws DAOException
	 */

	/**
	 * 동아리 만들기의 등록
	 * @param commInfo
	 * @return
	 * @throws DAOException
	 */
	public int addMakeCommRegister(CommInfoDTO commInfo) throws DAOException{
		int retVal = 0;
		QueryStatement sql	= new QueryStatement();
		StringBuffer sb		= new StringBuffer();

		sb.append(" INSERT INTO comm_info(system_code, comm_id, cate_code, comm_name" +
										", comm_synopsis, keyword, comm_master, main_img" +
										", regist_type, reg_id, reg_date, open_yn" +
										", best_yn, use_yn) ");
		sb.append(" VALUES(?, ?, ?, ?" +
							", ?, ?, ?, ?" +
							", ?, ?, ?, ?" +
							", 'N','N') ");

		sql.setSql(sb.toString());

		sql.setString(commInfo.getSystemCode());
		sql.setInteger(commInfo.getCommId());
		sql.setString(commInfo.getCateCode());
		sql.setString(commInfo.getCommName());
		sql.setString(commInfo.getCommSynopsis());
		sql.setString(commInfo.getKeyword());
		sql.setString(commInfo.getCommMaster());
		sql.setString(commInfo.getMainImg());
		sql.setString(commInfo.getRegistType());
		sql.setString(commInfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(commInfo.getOpenYn());

		log.debug("[addMakeCommRegister]--+++--" + sql.toString());

		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}


	/**
	 * 동아리 폐쇄신청
	 *
	 * @param systemCode
	 * @param commId
	 * @return
	 */
	public int commCloseRegist(String systemcode, String userid, int commid, String closeyn) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update comm_info set close_yn = ?, mod_id =?, mod_date = ? ");
		sb.append(" where system_code = ? and comm_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(closeyn);
		sql.setString(userid);
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(systemcode);
		sql.setInteger(commid);

		log.debug("[commCloseRegist]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 폐쇄함.
	 *
	 * @param systemCode
	 * @param commId
	 * @param useYn
	 * @return
	 * @throws DAOException
	 */
	public int commInfoClosingChange(String systemCode, int commId, String useYn) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update comm_info set use_yn = ? ");
		sb.append(" where system_code = ? and comm_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(useYn);
		sql.setString(systemCode);
		sql.setInteger(commId);

		log.debug("[commInfoClosingChange]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 동아리 메인이미지 삭제
	 *
	 * @param systemCode
	 * @param commId
	 * @return
	 * @throws DAOException
	 */
	public int delCommInfoFile(String systemCode, String commId) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update comm_info set main_img = '' ");
		sb.append(" where system_code = ? and comm_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(commId);

		log.debug("[delCommInfoFile]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 동아리 메인이미지 삭제
	 *
	 * @param systemCode
	 * @param commId
	 * @return
	 * @throws DAOException
	 */
	public int delCommInfo(String systemCode, int commId) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" DELETE FROM comm_info ");
		sb.append(" WHERE system_code = ? and comm_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setInteger(commId);

		log.debug("[delCommInfo]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 동아리 멤버 탈퇴 (지우기)
	 *
	 * @param commInfo
	 * @return
	 * @throws DAOException
	 */
	public int delCommMemberReg(String systemCode, String userId, int commId) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" delete from comm_members ");
		sb.append(" where system_code = ? and comm_id = ? ");
		if ( !userId.equals(""))
			sb.append(" and user_id = ? ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setInteger(commId);

		if ( !userId.equals(""))
			sql.setString(userId);

		log.debug("[delCommMemberReg]--+++--" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 동아리 수정
	 *
	 * @param commInfo
	 * @return
	 * @throws DAOException
	 */
	public int editCommInfo(CommInfoDTO commInfo) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update comm_info set cate_code= ? ,comm_synopsis = ?" +
										", keyword = ? , main_img = ? " +
										", regist_type = ? , open_yn = ?  ");
		sb.append(" , mod_id = ?, mod_date = ? " +
				" where system_code = ? and comm_id = ? ");

		sql.setSql(sb.toString());

		sql.setString(commInfo.getCateCode());
		sql.setString(commInfo.getCommSynopsis());
		sql.setString(commInfo.getKeyword());
		sql.setString(commInfo.getMainImg());
		sql.setString(commInfo.getRegistType());
		sql.setString(commInfo.getOpenYn());
		sql.setString(commInfo.getModId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(commInfo.getSystemCode());
		sql.setInteger(commInfo.getCommId());

		log.debug("[editCommInfo]--+++--" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	public int editCommMembersInfo(CommMembersDTO commMembers) throws DAOException {
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
	 * 동아리 카테고리 리스트 - sangsang
	 *
	 * @param curpage
	 * @param systemCode
	 * @return
	 * @throws DAOException
	 */
	public ListDTO getCategoryPagingList(int curpage, String systemCode) throws DAOException {
		ListDTO retVal = null;
		try {
			// List Sql문 생성
			ListStatement sql = new ListStatement();
			StringBuffer where = new StringBuffer(" c.system_code= ? ");

			sql.setTotalCol("c.cate_code");
			sql.setCutCol("c.cate_code");
			sql.setSelect(" c.cate_code as cate_code, c.cate_name as cate_name, c.reg_date as reg_date, isnull(count(i.comm_id),0) as cnt ");
			sql.setFrom(" comm_category c left outer join comm_info i on i.system_code= c.system_code and i.cate_code=c.cate_code ");
			sql.setString(systemCode);
			sql.setGroupby(" c.cate_code,c.cate_name,c.reg_date ");
			sql.setOrderby(" c.cate_name ");

			sql.setWhere(where.toString());

			log.debug("[getCategoryPagingList]" + sql.toString());
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
	 * 동아리 게시판의 정보 가져오기
	 *
	 * @param systemCode
	 * @param bbsId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCommBbsInfo(String systemCode, int bbsId) throws DAOException {
		return getCommBbsInfo(systemCode, bbsId, "");
	}

	public RowSet getCommBbsInfo(String systemCode, int bbsId, String commid) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append("\n SELECT system_code, comm_id, bbs_id, bbs_type, bbs_name, use_yn, editor_yn, ");
		sb.append("\n        voice_yn, file_use_yn, file_limit, disp_line, disp_page, sub_manager, ");
		sb.append("\n        reg_id, reg_date, mod_id, mod_date, comment_use_yn");
		sb.append("\n   FROM comm_bbs_info ");
		sb.append("\n  WHERE system_code = ? ");
		sb.append("\n    AND bbs_id = ?  ");
		sql.setString(systemCode);
		sql.setInteger(bbsId);
		if (!commid.equals("")) {
			sb.append("\n    AND comm_id = ? ");
			sql.setInteger(commid);
		}

		sql.setSql(sb.toString());

		log.debug("[getCommBbsInfo]" + sql.toString());
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			//-- rs.close() 는 반드시 JSP 에서 해 줄
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * COMM_BBS_INFO INFORMATION LODDING
	 *
	 * @param systemCode
	 * @param commId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCommBbsInfo(String systemCode, String commId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select system_code, comm_id, bbs_id, bbs_type, bbs_name, use_yn, editor_yn, ");
		sb.append(" voice_yn, file_use_yn, file_limit, disp_line, disp_page, sub_manager, ");
		sb.append(" reg_id, reg_date, mod_id, mod_date , comment_use_yn");
		sb.append(" from comm_bbs_info ");
		sb.append(" where system_code = ? and comm_id = ? and bbs_type='N' ");

		sql.setSql(sb.toString());

		sql.setString(systemCode);
		sql.setInteger(commId);

		log.debug("[getCommBbsInfo2]" + sql.toString());
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			//-- rs.close() 는 반드시 JSP 에서 해 줄
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 통합게시판 관련 METHOD
	 *
	 * @param systemCode
	 * @param commId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCommBbsInfo(String systemCode, String commId, String pBbsId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select system_code, comm_id, bbs_id, bbs_type, bbs_name, use_yn, editor_yn, ");
		sb.append(" voice_yn, file_use_yn, file_limit, disp_line, disp_page, sub_manager, ");
		sb.append(" reg_id, reg_date, mod_id, mod_date , comment_use_yn");
		sb.append(" from comm_bbs_info ");
		sb.append(" where system_code = ? and comm_id = ? and bbs_id = ? ");

		sql.setSql(sb.toString());

		sql.setString(systemCode);
		sql.setInteger(commId);
		sql.setInteger(pBbsId);

		log.debug("[getCommBbsInfo3]" + sql.toString());
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			//-- rs.close() 는 반드시 JSP 에서 해 줄
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 커뮤티니 - 게시판관리 페이징리스트 가져온다.
	 * @param curpage
	 * @param systemCode
	 * @param commId
	 * @return
	 * @throws DAOException
	 */
	public ListDTO getCommBbsManagePagingList(int curpage, String systemCode, String commId) throws DAOException {
		ListDTO retVal = null;
		try {

			StringBuffer sb = new StringBuffer();
			StringBuffer sbAlias = new StringBuffer();

			sbAlias.append(" comm_id, comm_name, bbs_id, bbs_name, bbs_type, file_use_yn, use_yn, reg_date ");
			sb.append(" i.comm_id, i.comm_name, b.bbs_id, b.bbs_name, b.bbs_type, b.file_use_yn, b.use_yn, b.reg_date ");

			// List Sql문 생성
			ListStatement sql = new ListStatement();

			sql.setTotalCol("i.comm_id");
			sql.setCutCol("i.comm_id");
			sql.setAlias(sbAlias.toString());
			sql.setSelect(sb.toString());
			sql.setFrom(" comm_info i , comm_bbs_info b");
			sql.setString(systemCode);
			sql.setString(commId);
			StringBuffer where = new StringBuffer(" i.system_code=b.system_code and i.comm_id=b.comm_id and b.system_code= ? and b.comm_id= ? ");
			// and b.bbs_id > 4
			sql.setWhere(where.toString());
			sql.setGroupby(" i.comm_id, i.comm_name, b.bbs_id, b.bbs_name, b.bbs_type, b.file_use_yn, b.use_yn, b.reg_date");
			//sql.setOrderby(" b.reg_date desc ");
			sql.setOrderby(" b.bbs_id asc ");

			log.debug("[getCommBbsPagingList]" + sql.toString());

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
	 * commId 를 loading
	 *
	 * @param systemCode
	 * @return
	 * @throws DAOException
	 */
	public int getCommId(String systemCode) throws DAOException {
		int viewretVal = 0;
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer song = new StringBuffer();

		song.append(" select max( comm_id + 1 ) comm_id  from comm_info ");
		song.append(" where system_code = ? ");

		sql.setSql(song.toString());

		sql.setString(systemCode);

		log.debug("#####################" + sql.toString());
		try {
			RowSet rs = broker.executeQuery(sql);
			if (rs.next()) {
				viewretVal = rs.getInt("comm_id");

				if (viewretVal == 0 || viewretVal == 1)
					retVal = 2;
				else
					retVal = viewretVal;

			}
			rs.close();
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
		log.debug("++++++++++++++++++++++++++++" + retVal);
		return retVal;

	}

	public CommInfoDTO getCommInfo(String systemCode, String commId) throws DAOException {
		CommInfoDTO infoDto = new CommInfoDTO();
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, comm_id, comm_name, cate_code, comm_synopsis, keyword, regist_type, main_img, open_yn ");
		sb.append(" from comm_info");
		sb.append(" where system_code = ? and comm_id = ? ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(commId);

		//---- 디버그 출력
		log.debug("[getCommInfo]" + sql.toString());
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			if (rs.next()) {
				infoDto.setCommId(rs.getInt("comm_id"));
				infoDto.setCommName(rs.getString("comm_name"));
				infoDto.setCateCode(rs.getString("cate_code"));
				infoDto.setCommSynopsis(StringUtil.nvl(rs.getString("comm_synopsis")));
				infoDto.setKeyword(StringUtil.nvl(rs.getString("keyword")));
				infoDto.setRegistType(rs.getString("regist_type"));
				infoDto.setMainImg(StringUtil.nvl(rs.getString("main_img")));
				infoDto.setOpenYn(rs.getString("open_yn"));
			}
			//---- rs.close() 는 반드시 jsp 페이지에서 해줄것!!!!!
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}

		return infoDto;
	}

	/**
	 * 게시판 정보 리스트 가져오기(페이징 없이)
	 *
	 * @param systemCode
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getCommInfoList(String systemCode, String UserId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append("\n SELECT a.system_code, a.cate_code, ( select cate_name from comm_category where system_code = a.system_code and cate_code = a.cate_code) as cate_name, ");
		sb.append("\n        a.main_img, a.comm_id, a.comm_name, a.comm_master, a.use_yn as comm_use_yn, ");
		sb.append("\n        b.user_level, b.use_yn, ( select so_name from code_so where system_code = b.system_code and code_dae = '103' and code_so = b.user_level) as level_name ");
		sb.append("\n   FROM comm_info a , comm_members b ");
		sb.append("\n  WHERE a.system_code = b.system_code ");
		sb.append("\n    AND a.comm_id = b.comm_id ");
		sb.append("\n    AND a.system_code = ? ");
		sb.append("\n    AND b.user_id = ? ");
		sb.append("\n    AND a.use_yn = 'Y' ");
		sb.append("\n ORDER BY a.reg_date desc ");

		sql.setSql(sb.toString());

		sql.setString(systemCode);
		sql.setString(UserId);

		log.debug("[getCommInfoList]" + sql.toString());
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	public CommMembersDTO getCommMemberInfo(String systemCode, String commId, String userId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, user_nick, user_intro ");
		sb.append(" from comm_members");
		sb.append(" where system_code = ? and comm_id = ? and user_id = ? ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(commId);
		sql.setString(userId);
		CommMembersDTO memberInfo = new CommMembersDTO();
		//---- 디버그 출력
		log.debug("[getCommMemberInfo]" + sql.toString());
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);

			if (rs.next()) {
				memberInfo.setUserNick(StringUtil.nvl(rs.getString("user_nick")));
				memberInfo.setUserIntro(StringUtil.nvl(rs.getString("user_intro")));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}

		return memberInfo;
	}

	/**
	 * sangsang
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
			StringBuffer where = new StringBuffer(" i.system_code=u.system_code and i.system_code=m.system_code and i.comm_id=m.comm_id and m.user_id=u.user_id and i.system_code= ? and m.comm_id= ? ");
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
	 * 동아리 리스트-sangsang
	 *
	 * @param curpage
	 * @param systemCode
	 * @param pCateCode
	 * @param pfield
	 * @param pvalue
	 * @return
	 * @throws DAOException
	 */
	public ListDTO getCommPagingList(int curpage, String systemCode, String pCateCode, String pfield, String pvalue) throws DAOException {
		ListDTO retVal = null;
		try {
			StringBuffer sb = new StringBuffer();
			StringBuffer sbAlias = new StringBuffer();
			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sbAlias.append(" system_code, comm_id, comm_name, comm_master, best_yn, user_name, open_yn, use_yn, reg_date, comm_synopsis ");
			sb.append(" i.system_code, i.comm_id, i.comm_name, i.comm_master, i.best_yn, u.user_name, i.open_yn, i.use_yn, i.reg_date, i.comm_synopsis ");

			sql.setTotalCol("i.comm_id");
			sql.setCutCol("i.comm_id");
			sql.setAlias(sbAlias.toString() + ", cnt ");
			sql.setSelect(sb.toString() + ", (select count(*) from comm_members where system_code = i.system_code and comm_id = i.comm_id) as cnt ");
			sql.setFrom(" users u, comm_info i  ");
			StringBuffer where = new StringBuffer();
			where.append(" i.system_code=u.system_code and i.comm_master=u.user_id and i.system_code= ?  and i.use_yn = 'Y' and i.open_yn = 'Y' ");
			sql.setString(systemCode);

			if (!pfield.equals("")) {
				if (!pfield.equals("comm_name"))
					where.append(" and " + pfield + " like ? ");
				else if (!pfield.equals("keyword"))
					where.append(" and " + pfield + " like ? ");
				else if (!pfield.equals("comm_master"))
					where.append(" and u.user_name like ? ");
				else
					where.append(" and " + pfield + " like ? ");

				sql.setString("%" + pvalue + "%");
			}
			if (pCateCode != null && !pCateCode.equals("")) {
				where.append(" and i.cate_code = ? ");
				sql.setString(pCateCode);
			}

			sql.setWhere(where.toString());
			sql.setOrderby(" i.reg_date desc ");
			sql.setGroupby(sb.toString());

			log.debug("[getCommPagingList]" + sql.toString());
			retVal = broker.executeListQuery(sql, curpage, 5);

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
	 * COMM_INFO INFORMATION LODDING
	 *
	 * @param systemCode
	 * @param userId
	 * @param commId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCommSubInfo(String systemCode, String userId, String commId) throws DAOException {

		QueryStatement sql = new QueryStatement();
		StringBuffer song = new StringBuffer();

		song.append("\n SELECT i.system_code, i.comm_id, i.cate_code, ");
		song.append("\n        ( select cate_name from comm_category where cate_code = i.cate_code) as cate_name, ");
		song.append("\n        i.comm_name, i.comm_synopsis, i.use_yn, i.comm_master, ");
		song.append("\n        i.open_yn, i.main_img, ( select count(*) from comm_members where system_code=i.system_code and comm_id=i.comm_id ) tntmembers, ");
		song.append("\n        ifnull(( select user_nick from comm_members where system_code = i.system_code and comm_id = i.comm_id and user_id =i.comm_master), '') as nick_name, ");
		song.append("\n        i.reg_date");
		song.append("\n   FROM comm_info i ");
		song.append("\n  WHERE i.system_code = ? ");
		song.append("\n    AND i.comm_id = ? ");

		sql.setSql(song.toString());
		sql.setString(systemCode); //where절 값의 바인딩 ^^;
		sql.setString(commId);

		log.debug("[getCommSubInfo]" + sql.toString());
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * COMM_MEMBERS INFORMATION LODDING
	 *
	 * @param systemCode
	 * @param userId
	 * @param commId
	 * @return
	 * @throws DAOException
	 */
	//getCommSubMemInfo
	public RowSet getCommSubMemInfo(String systemCode, String userId, String commId) throws DAOException {

		QueryStatement sql = new QueryStatement();
		StringBuffer song = new StringBuffer();

		song.append("\n SELECT user_level");
		song.append("\n   FROM comm_members ");
		song.append("\n  WHERE system_code = ? ");
		song.append("\n    AND comm_id = ? ");
		song.append("\n    AND user_id = ? ");

		sql.setSql(song.toString());

		sql.setString(systemCode); //where절 값의 바인딩 ^^;
		sql.setString(commId);
		sql.setString(userId);

		log.debug("[getCommSubMemInfo]" + sql.toString());
		log.debug("getCommSubMemInfo -----------------------end success  ....");
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 동아리 최근 게시물 정보
	 *
	 * @param systemCode
	 * @param userId
	 * @param commId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCommSubNewContents(String systemCode, String commId) throws DAOException {

		QueryStatement sql = new QueryStatement();
		StringBuffer song = new StringBuffer();

		song.append("\n SELECT b.bbs_id, b.bbs_name, a.seq_no, a.subject, a.reg_name, a.reg_date, a.hit_no ");
		song.append("\n   FROM comm_bbs_contents a, comm_bbs_info b ");
		song.append("\n  WHERE a.system_code = b.system_code ");
		song.append("\n    AND a.comm_id = b.comm_id ");
		song.append("\n    AND a.bbs_id = b.bbs_id ");
		song.append("\n    AND a.system_code = ? ");
		song.append("\n    AND a.comm_id = ? ");
		song.append("\n    AND a.depth_no = 0 ");
		song.append("\n    AND b.bbs_id > 1 ");
		song.append("\n    AND b.bbs_type != 'P' ");
//		song.append("\n    AND limit 5 ");
		song.append("\n ORDER BY a.reg_date desc ");
		song.append("\n limit 5 ");

		sql.setSql(song.toString());

		sql.setString(systemCode); //where절 값의 바인딩 ^^;
		sql.setString(commId);

		log.debug("[getCommSubNewContents]" + sql.toString());
		log.debug("getCommSubNewContents -----------------------end success  ....");
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 동아리 만들기 분류정보 로딩
	 *
	 * @param systemCode
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getMakeCateInfo(String systemCode) throws DAOException {
		ArrayList codeList = new ArrayList();
		QueryStatement sql = new QueryStatement();
		StringBuffer song = new StringBuffer();

		song.append(" select cate_code, cate_name from comm_category ");
		song.append(" where system_code = ? ");
		song.append(" order by reg_date desc ");

		sql.setSql(song.toString());

		sql.setString(systemCode);

		log.debug("[getMakeCateInfo]" + sql.toString());
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			CodeDTO codeDto = null;
			while (rs.next()) {
				codeDto = new CodeDTO();
				codeDto.setCode(rs.getString("cate_code"));
				codeDto.setName(rs.getString("cate_name"));
				log.debug("cate_code =>" + codeDto.getCode());
				log.debug("cate_name =>" + codeDto.getName());
				codeList.add(codeDto);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}

		return codeList;
	}

	/**
	 * 신규동아리 출력 관련 sql
	 *
	 * @param systemCode
	 * @return
	 * @throws DAOException
	 */
	public RowSet getNewCommInfo(String systemCode) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer song = new StringBuffer();

		song.append(" select a.system_code, a.comm_id, a.cate_code, a.comm_name, a.comm_synopsis, a.comm_master, a.open_yn, a.reg_date, ");
		song.append(" (select count(*) from comm_members where system_code = a.system_code and comm_id=a.comm_id) memCount ");
		song.append(" from comm_info a where a.system_code = ? and a.use_yn = 'Y' and a.open_yn = 'Y' and (a.close_yn='N' or a.close_yn is null) order by a.reg_date desc ");

		sql.setSql(song.toString());

		sql.setString(systemCode); //where절 값의 바인딩 ^^;

		log.debug("[getNewCommInfo]" + sql.toString());
		log.debug("getNewCommInfo -----------------------end success");
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 동아리 자랑 (COMM_BBS_CONTENTS)
	 *
	 * @param systemCode
	 * @return
	 * @throws DAOException
	 */
	//getPridecommInfo
	public RowSet getPridecommInfo(String systemCode) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer song = new StringBuffer();

		song.append(" SELECT ");
		song.append(" X.system_code, X.comm_id, X.comm_name, X.main_img ");
		song.append(" , Y.* ");
		song.append(" FROM comm_info X ");
		song.append(" , ( ");
		song.append(	" SELECT ");
		song.append(	" system_code AS sys_code, comm_id AS com_id, comm_id, bbs_id ");
		song.append(	" , seq_no, bbs_no, subject, reg_date ");
		song.append(	" FROM comm_bbs_contents ");
		song.append(	" WHERE system_code = ? and bbs_id = 4 ");
		song.append(" ) Y ");
		song.append(" WHERE X.system_code = Y.sys_code and X.comm_id = Y.com_id ");
		song.append(" ORDER BY RAND() ");
		song.append(" LIMIT 1 ");

		sql.setSql(song.toString());

		sql.setString(systemCode); //where절 값의 바인딩 ^^;

		log.debug("[getPridecommInfo]" + sql.toString());

		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 추천 동아리
	 *
	 * @param systemCode
	 * @return
	 * @throws DAOException
	 */
	public RowSet getRecCommInfo(String systemCode) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer song = new StringBuffer();

		song.append(" select a.system_code, a.comm_id, a.cate_code, a.comm_name, a.comm_synopsis, a.comm_master, a.open_yn, a.reg_date, a.main_img, ");
		song.append(" (select count(*) from comm_members where system_code = a.system_code and comm_id=a.comm_id) memCount ");
		song.append(" FROM comm_info a " +
					" WHERE a.best_yn='Y' and a.system_code = ? " +
					" and a.use_yn = 'Y' and a.open_yn = 'Y' " +
					" and (a.close_yn='N' or a.close_yn is null) " +	//and limit 5
					" ORDER BY rand() " +
					" limit 5 ");

		sql.setSql(song.toString());

		sql.setString(systemCode); //where절 값의 바인딩 ^^;

		log.debug("[getRecCommInfo]" + sql.toString());
		log.debug("getRecCommInfo -----------------------end success");
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}


	/**
	 * 가입 하기 체크
	 *
	 * @param SystemCode
	 * @param BbsId
	 * @return
	 * @throws DAOException
	 */
	public String joinCheckComm(String systemCode, String userId, String commId) throws DAOException {
		String retVal = "";
		int valCnt = 0;

		StringBuffer sb = new StringBuffer();
		sb.append(" select count(*) as cnt from comm_members ");
		sb.append(" where system_code = ? and user_id = ? and comm_id = ? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(userId);
		sql.setString(commId);

		log.debug("[joinCheckComm]" + sql.toString());

		RowSet rs = null;

		try {
			rs = broker.executeQuery(sql);
			if (rs.next()) {
				valCnt = rs.getInt("cnt");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}

		if (valCnt <= 0)
			retVal = "Y";
		else
			retVal = "N";

		return retVal;
	}

	/**
	 * 사용여부의 CHECKING...
	 *
	 * @param systemCode
	 * @param userId
	 * @param commId
	 * @return
	 * @throws DAOException
	 */
	//joinCheckUseYn
	public String joinCheckUseYn(String systemCode, String userId, String commId) throws DAOException {

		String retVal = "";
		//int useYn = 0;

		StringBuffer sb = new StringBuffer();
		sb.append(" select regist_type from comm_info ");
		sb.append(" where system_code = ? and comm_id = ? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(commId);

		log.debug("[joinCheckUseYn]" + sql.toString());

		RowSet rs = null;

		try {
			rs = broker.executeQuery(sql);
			if (rs.next()) {
				retVal = rs.getString("regist_type");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}

		return retVal;

	}

	/**
	 * 동아리 가입하기
	 *
	 * @param systemCode
	 * @param userId
	 * @param commId
	 * @return
	 * @throws DAOException
	 */
	public int joinCommMemberReg(String systemCode, String userId, String commId, String useYn, String userLevel, String username) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into comm_members(system_code, comm_id, user_id,user_nick, user_level, use_yn, reg_id, reg_date) ");
		sb.append(" values(?, ?, ?, ?, ?, ?, ?, ?) ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setString(commId);
		sql.setString(userId);
		sql.setString(username);
		sql.setString(userLevel);
		sql.setString(useYn);
		sql.setString(userId);
		sql.setString(CommonUtil.getCurrentDate());

		log.debug("[joinCommMemberReg]--+++--" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}
}


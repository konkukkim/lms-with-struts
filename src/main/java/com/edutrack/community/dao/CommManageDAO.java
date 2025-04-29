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
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

public class CommManageDAO extends AbstractDAO {

	/**
	 *
	 * @param curpage
	 * @param systemCode
	 * @return
	 * @throws DAOException
	 */
	/*
	public ListDTO getCommInfoPagingList(int curpage, String systemCode) throws DAOException{
		ListDTO retVal = null;
		try{

			StringBuffer sb = new StringBuffer();
			StringBuffer sbAlias = new StringBuffer();

			sbAlias.append(" comm_id, comm_name, comm_master, best_yn, master_name, cnt, reg_date ");
			sb.append(" i.comm_id as comm_id, i.comm_name as comm_name, i.comm_master as comm_master, i.best_yn as best_yn, u.user_name as master_name, ifnull(count(m.user_id),0) as cnt, i.reg_date ");

			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("i.comm_id");
			sql.setCutCol("i.comm_id");
			sql.setAlias(sbAlias.toString());
			sql.setSelect(sb.toString());
//			sql.setSelect(" i.comm_id as comm_id, i.comm_name as comm_name, i.comm_master as comm_master, i.best_yn as best_yn, u.user_name as master_name, ifnull(count(m.user_id),0) as cnt, i.reg_date ");
			sql.setFrom(" users u, comm_info i left outer join comm_members m on i.system_code= m.system_code and i.comm_id=m.comm_id ");
			sql.setWhere(" i.system_code=u.system_code and i.comm_master=u.user_id and  i.system_code='"+systemCode+"' and i.use_yn = 'Y' ");
            sql.setGroupby(" i.comm_id,i.comm_name, i.comm_master, i.best_yn, u.user_name, i.reg_date ");
			sql.setOrderby(" i.reg_date desc ");

            // 파라미터 셋팅
			log.debug("[getCommInfoPagingList]" + sql.toString());
			//retVal = broker.executeListQuery(sql, curpage, dispLine, dispPage);
			retVal = broker.executeListQuery(sql, curpage);
			log.debug("[curpage]" + curpage);

			return retVal;
		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			 log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
	}
	*/

	/**
	 * 현재 사용중인 커뮤니티 목록 보기
	 * @param curpage
	 * @param systemCode
	 * @return
	 * @throws DAOException
	 */

	public ListDTO getCommInfoPagingList(int curpage, String systemCode) throws DAOException{
		ListDTO retVal = null;
		try{
			StringBuffer sb = new StringBuffer();
			StringBuffer sbAlias = new StringBuffer();
			sbAlias.append(	" comm_id, comm_name, comm_master, best_yn, user_name, reg_date ");
			sb.append(	" i.comm_id, i.comm_name, i.comm_master, i.best_yn, u.user_name, i.reg_date ");

			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol(" i.comm_id ");
			sql.setCutCol(	" i.comm_id ");
			sql.setAlias(	sbAlias.toString() + ", cnt ");
			sql.setSelect(	sb.toString() + ", ifnull(count(m.user_id), 0) as cnt ");
			sql.setFrom(	" users u, comm_info i LEFT OUTER JOIN comm_members m ON i.system_code=m.system_code and i.comm_id=m.comm_id \n");

			StringBuffer sbWhere = new StringBuffer();
			sbWhere.append(	" i.system_code=u.system_code and i.comm_master=u.user_id and i.system_code = ? and i.use_yn = 'Y' ");
			sql.setString(systemCode);
			sql.setWhere(sbWhere.toString());
			sql.setOrderby(	" i.reg_date desc ");
            sql.setGroupby(	sb.toString());

            // 파라미터 셋팅
			log.debug("[getCommInfoPagingList]" + sql.toString());
			retVal = broker.executeListQuery(sql, curpage);

			log.debug("[curpage]" + curpage);

			return retVal;
		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			 log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
	}


	/**
	 * 추천 커뮤니티 변경
	 * @param systemCode
	 * @param commId
	 * @param bestYn
	 * @return
	 * @throws DAOException
	 */
	public int commInfoBestYnChange(String systemCode, int commId, String bestYn) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE comm_info SET best_yn = ? ");
		sb.append("\n WHERE system_code = ? AND comm_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(bestYn);
		sql.setString(systemCode);
		sql.setInteger(commId);

		log.debug("[commInfoBestYnChange]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

//	/**
//	 * 모든 커뮤니티 삭제
//	 * @param systemCode
//	 * @param commId
//	 * @return
//	 * @throws DAOException
//	 */
//	public boolean commInfoAllDelete(String systemCode, int commId) throws DAOException{
//		boolean retVal = false;
//		try {
//			QueryStatement[] sqls = new QueryStatement[6];
//
//			StringBuffer sb = new StringBuffer();
//			sb.append("\n DELETE comm_bbs_comment ");
//			sb.append("\n WHERE system_code = ? AND comm_id = ? ");
//			sqls[0].setSql(sb.toString());
//			sqls[0].setString(systemCode);
//			sqls[0].setInteger(commId);
//
//			StringBuffer sb1 = new StringBuffer();
//			sb1.append("\n DELETE comm_bbs_contents ");
//			sb1.append("\n WHERE system_code = ? AND comm_id = ? ");
//			sqls[1].setSql(sb1.toString());
//			sqls[1].setString(systemCode);
//			sqls[1].setInteger(commId);
//
//			StringBuffer sb2 = new StringBuffer();
//			sb2.append("\n DELETE comm_bbs_info ");
//			sb2.append("\n WHERE system_code = ? AND comm_id = ? ");
//			sqls[2].setSql(sb2.toString());
//			sqls[2].setString(systemCode);
//			sqls[2].setInteger(commId);
//
//			StringBuffer sb3 = new StringBuffer();
//			sb3.append("\n DELETE comm_info ");
//			sb3.append("\n WHERE system_code = ? AND comm_id = ? ");
//			sqls[3].setSql(sb3.toString());
//			sqls[3].setString(systemCode);
//			sqls[3].setInteger(commId);
//
//			StringBuffer sb4 = new StringBuffer();
//			sb4.append("\n DELETE comm_invite ");
//			sb4.append("\n WHERE system_code = ? AND comm_id = ? ");
//			sqls[4].setSql(sb4.toString());
//			sqls[4].setString(systemCode);
//			sqls[4].setInteger(commId);
//
//			StringBuffer sb5 = new StringBuffer();
//			sb5.append("\n DELETE comm_members ");
//			sb5.append("\n WHERE system_code = ? AND comm_id = ? ");
//			sqls[5].setSql(sb5.toString());
//			sqls[5].setString(systemCode);
//			sqls[5].setInteger(commId);
//
//			log.debug("[commInfoAllDelete]" + sqls.toString());
//
//			retVal = broker.executeUpdate(sqls);
//		}catch(NullPointerException npe){
//			log.error(npe.getMessage());
//		}catch(Exception e){
//			log.error(e.getMessage());
//			throw new DAOException(e.getMessage());
//		}
//
//		return retVal;
//	}

	/**
	 * 모든 커뮤니티 삭제
	 * @param systemCode
	 * @param commId
	 * @return
	 * @throws DAOException
	 */
	public int commInfoAllDelete(String systemCode, int commId) throws DAOException{
		int retVal = 0;
		try {
			QueryStatement sqls = new QueryStatement();

			StringBuffer sb2 = new StringBuffer();
			sb2.append("\n DELETE FROM comm_bbs_info ");
			sb2.append("\n WHERE system_code = ? AND comm_id = ? ");
			sqls.setSql(sb2.toString());
			sqls.setString(systemCode);
			sqls.setInteger(commId);

			log.debug("[commInfoAllDelete]" + sqls.toString());

			retVal = broker.executeUpdate(sqls);

		}catch(NullPointerException npe){
			log.error(npe.getMessage());
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 신규 커뮤니티 목록 보기
	 * @param systemCode
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCommInfoNewList(String systemCode) throws DAOException{
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" select i.system_code, i.comm_id, i.cate_code, i.comm_name, i.comm_master,i.use_yn, i.best_yn, u.user_name");
		sb.append(" from comm_info i, users u ");
		sb.append(" where i.system_code=u.system_code and i.comm_master=u.user_id and i.system_code = ? and i.use_yn = 'N' and (i.close_yn = 'N' or i.close_yn is null) ");
		sb.append(" order by i.reg_date desc ");

		sql.setSql(sb.toString());

		sql.setString(systemCode);

		log.debug("[getCommInfoNewList]" + sql.toString());
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			//-- rs.close() 는 반드시 JSP 에서 해 줄
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return rs;
	}

	/**
	 * 폐쇄신청 및 폐쇄된 커뮤니티 목록 보기
	 * @param curpage
	 * @param systemCode
	 * @param useYn
	 * @return
	 * @throws DAOException
	 */
	public ListDTO getCommInfoClosingList(int curpage, String systemCode, String useYn) throws DAOException{
		ListDTO retVal = null;
		try{
			StringBuffer sb = new StringBuffer();
			StringBuffer sbAlias = new StringBuffer();
			sbAlias.append(" comm_id, comm_name, comm_master, use_yn, user_name ");
			sb.append(" i.comm_id, i.comm_name, i.comm_master, i.use_yn, u.user_name ");
			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("i.comm_id");
			sql.setCutCol("i.comm_id");
			sql.setAlias(sbAlias.toString());
			sql.setSelect(sb.toString());
			sql.setFrom(" users u, comm_info i ");
			StringBuffer sbWhere = new StringBuffer();
			sbWhere.append(	"  i.system_code=u.system_code and i.comm_master=u.user_id and i.system_code=? and i.close_yn='Y' ");
			sql.setString(systemCode);
			if(useYn.equals("N"))
				sbWhere.append(" and i.use_yn = 'N' ");
			else
				sbWhere.append(" and i.use_yn = 'C' ");

			sql.setWhere(sbWhere.toString());
			sql.setOrderby(" i.comm_name ");

            // 파라미터 셋팅
			log.debug("[getCommInfoClosingList]" + sql.toString());
			retVal = broker.executeListQuery(sql, curpage);

			return retVal;
		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			 log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
	}

	/**
	 * 폐쇄 여부
	 * @param systemCode
	 * @param commId
	 * @param useYn
	 * @return
	 * @throws DAOException
	 */
	public int commInfoClosingChange(String systemCode, int commId, String useYn) throws DAOException{
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
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}

	/**
	 * 커뮤니티를 재오픈 함.
	 * @param systemCode
	 * @param commId
	 * @return
	 * @throws DAOException
	 */
	public int commInfoReuse(String systemCode, int commId) throws DAOException{
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update comm_info set use_yn = 'Y', close_yn = 'N' ");
		sb.append(" where system_code = ? and comm_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setInteger(commId);

		log.debug("[commInfoClosingChange]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 커뮤니티 정보 보여주기
	 * @param systemCode
	 * @param commId
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCommInfo(String systemCode, int commId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select i.system_code as system_code, i.cate_code as cate_code, i.comm_name as comm_name, i.main_img as main_img, i.comm_synopsis as comm_synopsis, i.keyword as keyword, i.regist_type as regist_type, c.cate_name as cate_name ");
		sb.append(" from comm_category c, comm_info i");
		sb.append(" where c.system_code=i.system_code and i.system_code=? and c.cate_code=i.cate_code ");
		sb.append(" and i.comm_id=? ");

		sql.setSql(sb.toString());
		sql.setString(systemCode);
		sql.setInteger(commId);

		//---- 디버그 출력
		log.debug("[getCommInfo]" + sql.toString());
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			//---- rs.close() 는 반드시 jsp 페이지에서 해줄것!!!!!
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 *
	 * @param systemcode
	 * @param commid
	 * @param modid
	 * @return
	 * @throws DAOException
	 */

	public boolean approveCommInfo(String systemcode, String commid,String modid) throws DAOException {
		QueryStatement[] sqls = new QueryStatement[4];

	   	// 커뮤니티 정보를 업데이트 해준다.
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();

		sb.append(" update comm_info  ");
		sb.append(" set use_yn = 'Y', ");
		sb.append(" mod_id = ?, mod_date = ? ");
		sb.append(" where system_code = ? and comm_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(modid);
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(systemcode);
		sql.setInteger(commid);

		log.debug("[approveCommInfo 1]" + sql.toString());

		sb.delete(0,sb.length());

		sqls[0] = sql;

	    // 커뮤니티의 기본 게시판을 생성해준다.
		sql = new QueryStatement();

		sb.append(" insert into comm_bbs_info(system_code, comm_id, bbs_id, bbs_type, bbs_name, use_yn, disp_line, disp_page, editor_yn, file_use_yn, comment_use_yn, reg_id, reg_date) ");
		sb.append(" select ?, ?, ifnull(max(bbs_id), 0)+1 as bbs_id, ?, ?, 'Y', '10', '10', 'Y', 'Y', 'Y', ?, ? ");
		sb.append(" from comm_bbs_info ");
		sb.append(" where system_code = ? and comm_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setInteger(commid);
		sql.setString("N");
		sql.setString(StringUtil.outDataConverter("공지사항"));
		sql.setString(modid);
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(systemcode);
		sql.setInteger(commid);

		log.debug("[approveCommInfo 2]" + sql.toString());

		sqls[1] = sql;

		sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setInteger(commid);
		sql.setString("B");
		sql.setString(StringUtil.outDataConverter("자유게시판"));
		sql.setString(modid);
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(systemcode);
		sql.setInteger(commid);

		log.debug("[approveCommInfo 3]" + sql.toString());

		sqls[2] = sql;
		sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setInteger(commid);
		sql.setString("F");
		sql.setString(StringUtil.outDataConverter("자료실"));
		sql.setString(modid);
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(systemcode);
		sql.setInteger(commid);

		log.debug("[approveCommInfo 4]" + sql.toString());

		sqls[3] = sql;

		boolean retVal = false;
		 try{
			 retVal = broker.executeUpdate(sqls);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
		 return retVal;
	}


	/*
	public boolean approveCommInfo(String systemcode, String commid,String modid) throws DAOException {
		QueryStatement[] sqls = new QueryStatement[4];

	   	// 커뮤니티 정보를 업데이트 해준다.
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update comm_info  ");
		sb.append(" set use_yn = 'Y', ");
		sb.append(" mod_id = ?, mod_date = ? ");
		sb.append(" where system_code = ? and comm_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(modid);
		sql.setString(systemcode);
		sql.setInteger(commid);
		sql.setString(CommonUtil.getCurrentDate());

		log.debug("[approveCommInfo 1]" + sql.toString());

		sb.delete(0,sb.length());

		sqls[0] = sql;
	    // 커뮤니티의 기본 게시판을 생성해준다.
		sql = new QueryStatement();
		sb.append(" insert into comm_bbs_info(system_code, comm_id, bbs_id, bbs_type, bbs_name, use_yn, disp_line, disp_page, editor_yn, file_use_yn, comment_use_yn, reg_id, reg_date) ");
		sb.append(" select ?,?, ifnull(max(bbs_id),0)+1 as bbs_id,?,?,'Y','10','10','Y','Y','Y',?, ? ");
		sb.append(" from comm_bbs_info ");
		sb.append(" where system_code = ? and comm_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setInteger(commid);
		sql.setString("N");
		sql.setString(StringUtil.outDataConverter("공지사항"));
		sql.setString(modid);
		sql.setString(systemcode);
		sql.setInteger(commid);
		sql.setString(CommonUtil.getCurrentDate());

		log.debug("[approveCommInfo 2]" + sql.toString());

		sqls[1] = sql;

		sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setInteger(commid);
		sql.setString("B");
		sql.setString(StringUtil.outDataConverter("자유게시판"));
		sql.setString(modid);
		sql.setString(systemcode);
		sql.setInteger(commid);
		sql.setString(CommonUtil.getCurrentDate());

		log.debug("[approveCommInfo 3]" + sql.toString());

		sqls[2] = sql;

		sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setInteger(commid);
		sql.setString("F");
		sql.setString(StringUtil.outDataConverter("자료실"));
		sql.setString(modid);
		sql.setString(systemcode);
		sql.setInteger(commid);

		log.debug("[approveCommInfo 4]" + sql.toString());

		sqls[3] = sql;

		boolean retVal = false;
		 try{
			 retVal = broker.executeUpdate(sqls);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	   }

	   */
}
/*
 * Created on 2004. 10. 12.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curribbs.dao;

import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.curribbs.dao.CurriBbsCommentDAO;
import com.edutrack.curribbs.dto.CurriBbsCommentDTO;
import com.edutrack.common.CommonUtil;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;

/**
 * @author suna
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CurriBbsCommentDAO  extends AbstractDAO {


	/**
	 * 코멘트 comm_no 최고값+1 을 가져온다
	 * @param SystemCode
	 * @param BbsType
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int getMaxCommNo(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String BbsType, int SeqNo) throws DAOException{
	 int  maxCommNo = 0;

	 StringBuffer sb = new StringBuffer();
	  sb.append(" select ifnull(max(comm_no),0)+1 as max_seq_no ");
	  sb.append(" from curri_bbs_comment ");
	  sb.append(" where system_code = ? and bbs_type = ? and curri_code = ? and curri_year = ? and curri_term = ?   and seq_no = ?");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setString(BbsType);
	 sql.setString(CurriCode);
	 sql.setInteger(CurriYear);
	 sql.setInteger(CurriTerm);
	 sql.setInteger(SeqNo);

	 log.debug("[getMaxCommNo]" + sql.toString());

	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 if(rs.next()){
		 	maxCommNo = rs.getInt("max_seq_no");
		 }
		 rs.close();
	 }catch(Exception e){
	      e.printStackTrace();
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }finally{
		 try{
		  if(rs != null) rs.close();
		 }catch(SQLException e){
			throw new DAOException(e.getMessage());
		 }
	 }

	 return maxCommNo;
	}

	/**
	 * 등록된 코멘트 총 수를 가져온다.
	 * @param SystemCode
	 * @param BbsType
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int getCurriBbsCommentCount(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String BbsType, int SeqNo ) throws DAOException{
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" select ifnull(count(comm_no),0) as cnt ");
		  sb.append(" from curri_bbs_comment ");
		  sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and bbs_type= ?");
		  if(SeqNo>0)
		 	sb.append(" and seq_no = ? ");


		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(BbsType);
		 if(SeqNo>0)
		 	sql.setInteger(SeqNo);

		 log.debug("[getCurriBbsCommentCount]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	cnt = rs.getInt("cnt");
			 }
			 rs.close();
		 }catch(Exception e){
		      e.printStackTrace();
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			 try{
			  if(rs != null) rs.close();
			 }catch(SQLException e){
				throw new DAOException(e.getMessage());
			 }
		 }

		 return cnt;
		}


	/**
	 * 코멘트를 등록한다.
	 * @param bbsComment
	 * @return
	 * @throws DAOException
	 */
	public int addCurriBbsComment(CurriBbsCommentDTO curriBbsComment) throws DAOException{
		log.debug("[addCurriBbsComment] start" );
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 log.debug("[addCurriBbsComment] start2" );
	 StringBuffer sb = new StringBuffer();
	 log.debug("[addCurriBbsComment] start1" );
	 sb.append(" insert into curri_bbs_comment(system_code, curri_code, curri_year, curri_term,  bbs_type, seq_no");
	 sb.append(" , comm_no, course_id, contents, emoticon_num, reg_id, reg_name, reg_date ) ");
	 sb.append(" values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
	 sql.setSql(sb.toString());
	 log.debug("[addCurriBbsComment]" + sql.toString());
	 sql.setString(curriBbsComment.getSystemCode());
	 sql.setString(curriBbsComment.getCurriCode());
	 sql.setInteger(curriBbsComment.getCurriYear());
	 sql.setInteger(curriBbsComment.getCurriTerm());
	 sql.setString(curriBbsComment.getBbsType());
	 sql.setInteger(curriBbsComment.getSeqNo());
	 sql.setInteger(curriBbsComment.getCommNo());
	 sql.setString(curriBbsComment.getCourseId());
	 sql.setString(curriBbsComment.getContents());
	 sql.setString(curriBbsComment.getEmoticonNum());
	 sql.setString(curriBbsComment.getRegId());
	 sql.setString(curriBbsComment.getRegName());
	 sql.setString(CommonUtil.getCurrentDate());

	 log.debug("[addCurriBbsComment]" + sql.toString());
	 try{
	     retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

	 return retVal;
	}

	/**
	 * 코멘트를 삭제한다.
	 * @param SystemCode
	 * @param BbsId
	 * @param SeqNo
	 * @param CommNo
	 * @return
	 * @throws DAOException
	 */
	public int delCurriBbsComment(String SystemCode,String CurriCode, int CurriYear, int CurriTerm, String BbsType, int SeqNo, int CommNo) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" delete from curri_bbs_comment ");
	 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and bbs_type = ?");
	 if(SeqNo>0) sb.append(" and seq_no = ?");
	 if(CommNo>0) sb.append(" and comm_no = ?");
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setString(CurriCode);
	 sql.setInteger(CurriYear);
	 sql.setInteger(CurriTerm);
	 sql.setString(BbsType);
	 if(SeqNo>0) sql.setInteger(SeqNo);
	 if(CommNo>0) sql.setInteger(CommNo);

	 log.debug("[delCurriBbsComment]" + sql.toString());
	 try{
		 retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

	 return retVal;
	}
	/**
	 * 코멘트 정보 가져오기
	 * @param Systemcode
	 * @param BbsId
	 * @param SeqNo
	 * @param CommNo
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriBbsComment(String Systemcode, String CurriCode, int CurriYear, int CurriTerm, String BbsType, int SeqNo, int CommNo) throws DAOException{
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select system_code, curri_code, curri_year, curri_term, bbs_type, seq_no,  comm_no, course_id, contents, emoticon_num, reg_id, reg_name, reg_date ");
		 sb.append(" from curri_bbs_comment ");
		 sb.append(" where system_code = ? and curri_code=? and curri_year = ? and curri_term = ? and bbs_type = ? and seq_no = ? and comm_no = ? ");

		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(BbsType);
		 sql.setInteger(SeqNo);
		 sql.setInteger(CommNo);

		 log.debug("[getCurriBbsComment]" + sql.toString());
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
	 * comment 리스트 가져오기(페이징 없이)
	 * @param Systemcode
	 * @param BbsId
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getCurriBbsCommentList(String Systemcode, String CurriCode, int CurriYear, int CurriTerm, String BbsType, int SeqNo) throws DAOException{
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" select system_code, curri_code, curri_year, curri_term, bbs_type, seq_no,  comm_no, course_id, contents, emoticon_num, reg_id, reg_name,  reg_date ");
	 sb.append(" from curri_bbs_comment ");
	 sb.append(" where system_code = ? and curri_code=? and curri_year = ? and curri_term = ? and bbs_type = ?  and seq_no = ?");
	 sb.append(" order by comm_no desc ");

	 sql.setSql(sb.toString());
	 sql.setString(Systemcode);
	 sql.setString(CurriCode);
	 sql.setInteger(CurriYear);
	 sql.setInteger(CurriTerm);
	 sql.setString(BbsType);
	 sql.setInteger(SeqNo);

	 log.debug("[getCurriBbsCommentList]" + sql.toString());
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




	public static void main(String args[]) throws DAOException{
		CurriBbsCommentDAO ld = new CurriBbsCommentDAO();
	//    ld.getUserInfo("jang","jang");
	//    ld.checkUser("jang10");
	//    ld.userPagingList(3);

	//      UserInfo user = new UserInfo();
	//	  for(int i=0; i < 100; i++){
	//        user.setNumber(i+1);
	//	  	user.setUserId("jang"+i);
	//	    user.setUserPw("jang"+i);
	//
	//	    ld.addUser(user);
	//	  }
	//    ld.delUser("jang2");

	//    ArrayList a = ld.userList();
	//    ld.getBbsCommentList(1,10, 10, "1", 1, " subject like '%ss%' " , "");
	    int i = 0;
	}

	public ListDTO getCurriBbsCommentPagingList(int curpage, String systemCode, String curriCode, int curriYear, int curriTerm, String bbsType, int seqNo, int dispLine, int dispPage) throws DAOException {
		ListDTO retVal = null;
		try{
	 		//---- List Sql 문생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("comm_no");
			sql.setCutCol("comm_no");
			sql.setAlias("comm_no, contents, emoticon_num, reg_id, reg_name, reg_date");
			sql.setSelect("comm_no, contents, emoticon_num, reg_id, reg_name, concat(substring(reg_date,1,4),'-',substring(reg_date,5,2),'-',substring(reg_date,7,2)) as reg_date");
			sql.setFrom(" curri_bbs_comment");
			sql.setWhere(" system_code = ? and curri_code=? and curri_year = ? and curri_term = ? and bbs_type = ?  and seq_no = ? ");
			sql.setOrderby(" comm_no desc");

			sql.setString(systemCode);
			sql.setString(curriCode);
			sql.setInteger(curriYear);
			sql.setInteger(curriTerm);
			sql.setString(bbsType);
			sql.setInteger(seqNo);

			//---- 디버그 출력
			log.debug("[getCurriBbsCommentPagingList]" + sql.toString());

			//---- 쿼리실행 및 반환값 설정
			retVal = broker.executeListQuery(sql, curpage, dispLine, dispPage);
			return retVal;
		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
	}
}

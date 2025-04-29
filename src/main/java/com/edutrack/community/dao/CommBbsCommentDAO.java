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
import com.edutrack.community.dto.CommBbsCommentDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author black
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommBbsCommentDAO extends AbstractDAO {

	/**
	 * 등록된 코멘트 총 수를 가져온다.
	 * @param SystemCode
	 * @param BbsId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int getBbsCommentCount(String SystemCode, int BbsId,int SeqNo ) throws DAOException{
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" select ifnull(count(comm_no),0) as cnt ");
		  sb.append(" from comm_bbs_comment ");
		  sb.append(" where system_code = ? and bbs_id= ? ");
		  if(SeqNo>0)
		 	sb.append(" and seq_no = ? ");


		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setInteger(BbsId);
		 if(SeqNo>0)
		 	sql.setInteger(SeqNo);

		 log.debug("[getBbsCommentCount]" + sql.toString());

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
	 * Overriding getBbsCommentCount
	 * @param SystemCode
	 * @param BbsId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int getBbsCommentCount(String SystemCode, String commId,int BbsId,int SeqNo ) throws DAOException{
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" select ifnull(count(comm_no),0) as cnt ");
		  sb.append(" from comm_bbs_comment ");
		  sb.append(" where system_code = ? and comm_id = ? and bbs_id= ? ");
		  if(SeqNo>0)
		 	sb.append(" and seq_no = ? ");


		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(commId);
		 sql.setInteger(BbsId);
		 if(SeqNo>0)
		 	sql.setInteger(SeqNo);

		 log.debug("[getBbsCommentCount]" + sql.toString());

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
	 * comment 리스트 가져오기(페이징 없이)
	 * @param Systemcode
	 * @param BbsId
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getBbsCommentList(String Systemcode, int BbsId, int SeqNo) throws DAOException{
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" select system_code, bbs_id, seq_no,  comm_no, contents, emoticon_num, reg_id, reg_name, reg_email, reg_passwd, reg_date ");
	 sb.append(" from comm_bbs_comment ");
	 sb.append(" where system_code = ? and bbs_id = ? and seq_no = ? ");
	 sb.append(" order by comm_no desc ");

	 sql.setSql(sb.toString());
	 sql.setString(Systemcode);
	 sql.setInteger(BbsId);
	 sql.setInteger(SeqNo);

	 log.debug("[getBbsCommentList]" + sql.toString());
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
	public RowSet getBbsCommentList(String Systemcode, String commId, int BbsId, int SeqNo) throws DAOException{
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(	" select " +
	 				" system_code, bbs_id, seq_no, comm_no" +
	 				", contents, emoticon_num, reg_id, reg_name" +
	 				", reg_email, reg_passwd, reg_date ");
	 sb.append(	" from comm_bbs_comment ");
	 sb.append(	" where system_code = ? and comm_id = ? and bbs_id = ? and seq_no = ?");
	 sb.append(	" order by comm_no desc ");

	 sql.setSql(sb.toString());
	 sql.setString(Systemcode);
	 sql.setString(commId);
	 sql.setInteger(BbsId);
	 sql.setInteger(SeqNo);

	 log.debug("[getBbsCommentList]" + sql.toString());
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
	 * 코멘트 페이징 리스트
	 *
	 * @return
	 * @throws DAOException
	 */
	public ListDTO	getCommBbsCommentPagingList(int curpage, String Systemcode, String commId, int BbsId, int SeqNo, int dispLine, int dispPage) throws DAOException {
		ListDTO retVal = null;
		try {
	 		//---- List Sql 문생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("comm_no");
			sql.setCutCol(	"comm_no");
			sql.setAlias(	" system_code, bbs_id, seq_no, comm_no" +
							", contents, emoticon_num, reg_id, reg_name" +
							", reg_email, reg_passwd, reg_date ");
			sql.setSelect(	"  system_code, bbs_id, seq_no, comm_no" +
							", contents, emoticon_num, reg_id, reg_name" +
							", reg_email, reg_passwd, reg_date ");
			sql.setFrom(	" comm_bbs_comment ");
			sql.setWhere(	" system_code = ? and comm_id = ? and bbs_id = ? and seq_no = ? ");
			sql.setOrderby(	" comm_no desc ");

			sql.setString(Systemcode);
			sql.setString(commId);
			sql.setInteger(BbsId);
			sql.setInteger(SeqNo);

			//---- 디버그 출력
			log.debug("[getCommBbsCommentPagingList]" + sql.toString());

			//---- 쿼리실행 및 반환값 설정
			retVal = broker.executeListQuery(sql, curpage, dispLine, dispPage);

		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
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
	public RowSet getBbsComment(String Systemcode, int BbsId, int SeqNo, int CommNo) throws DAOException{
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select system_code, bbs_id, seq_no,  comm_no, contents, emoticon_num, reg_id, reg_name, reg_email, reg_passwd, reg_date ");
		 sb.append(" from comm_bbs_comment ");
		 sb.append(" where system_code = ? and bbs_id = ? and seq_no = ? and comm_no = ? ");

		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 sql.setInteger(BbsId);
		 sql.setInteger(SeqNo);
		 sql.setInteger(CommNo);

		 log.debug("[getBbsComment]" + sql.toString());
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
	 * 코멘트를 삭제한다.
	 * @param SystemCode
	 * @param BbsId
	 * @param SeqNo
	 * @param CommNo
	 * @return
	 * @throws DAOException
	 */
	public int delBbsComment(String SystemCode, int CommId, int BbsId, int SeqNo,int CommNo) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" delete from comm_bbs_comment ");
	 sb.append(" where system_code = ? and comm_id = ? and bbs_id = ?");
	 if(SeqNo>0) sb.append(" and seq_no = ?");
	 if(CommNo>0) sb.append(" and comm_no = ?");
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setInteger(CommId);
	 sql.setInteger(BbsId);
	 if(SeqNo>0) sql.setInteger(SeqNo);
	 if(CommNo>0) sql.setInteger(CommNo);

	 log.debug("[delBbsComment]" + sql.toString());
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
	public int delBbsComment(String SystemCode, int CommId) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" delete from comm_bbs_comment ");
	 sb.append(" where system_code = ? and comm_id = ? ");
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setInteger(CommId);

	 log.debug("[delBbsComment]" + sql.toString());
	 try{
		 retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

	 return retVal;
	}

	/**
	 * 코멘트 comm_no 최고값+1 을 가져온다
	 * @param SystemCode
	 * @param BbsId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int getMaxCommNo(String SystemCode, int BbsId, int SeqNo) throws DAOException{
	 int  maxCommNo = 0;

	 StringBuffer sb = new StringBuffer();
	  sb.append(" select ifnull(max(comm_no),0)+1 as max_seq_no ");
	  sb.append(" from comm_bbs_comment ");
	  sb.append(" where system_code = ? and bbs_id = ? and seq_no = ?");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setInteger(BbsId);
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
	 * Overriding getMaxCommNo - (String commId)
	 * @param SystemCode
	 * @param commId
	 * @param BbsId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int getMaxCommNo(String SystemCode, String commId, int BbsId,int SeqNo) throws DAOException{
		 int  maxCommNo = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" select ifnull(max(comm_no),0)+1 as max_seq_no ");
		  sb.append(" from comm_bbs_comment ");
		  sb.append(" where system_code = ? and comm_id = ? and bbs_id = ? and seq_no = ?");

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(commId);
		 sql.setInteger(BbsId);
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
	 * 코멘트를 등록한다.
	 * @param bbsComment
	 * @return
	 * @throws DAOException
	 */
	public int addBbsComment(CommBbsCommentDTO bbsComment) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" INSERT INTO comm_bbs_comment (system_code, comm_id, bbs_id, seq_no" +
	 										", comm_no, contents, emoticon_num, reg_id" +
	 										", reg_name, reg_email, reg_passwd, reg_date) ");
	 sb.append(" VALUES (?, ?, ?, ?" +
	 					", ?, ?, ?, ?" +
	 					", ?, ?, ?, ?) ");
	 sql.setSql(sb.toString());

	 sql.setString(StringUtil.nvl(bbsComment.getSystemCode()));
	 sql.setInteger(bbsComment.getCommId());
	 sql.setInteger(bbsComment.getBbsId());
	 sql.setInteger(bbsComment.getSeqNo());
	 sql.setInteger(bbsComment.getCommNo());
	 sql.setString(StringUtil.nvl(bbsComment.getContents()));
	 sql.setString(StringUtil.nvl(bbsComment.getEmoticonNum()));
	 sql.setString(StringUtil.nvl(bbsComment.getRegId()));
	 sql.setString(StringUtil.nvl(bbsComment.getRegName()));
	 sql.setString(StringUtil.nvl(bbsComment.getRegEmail()));
	 sql.setString(StringUtil.nvl(bbsComment.getRegPasswd()));
	 sql.setString(CommonUtil.getCurrentDate());

	 log.debug("[addBbsComment]" + sql.toString());


	 try{
	     retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

	 return retVal;
	}

}
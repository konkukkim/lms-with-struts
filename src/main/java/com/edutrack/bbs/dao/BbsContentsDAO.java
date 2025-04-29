/*
 * Created on 2004. 6. 28.
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.edutrack.bbs.dao;

import java.io.CharArrayReader;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.RowSet;

//import oracle.jdbc.OracleResultSet;
//import oracle.sql.CLOB;

import com.edutrack.bbs.dto.BbsContentsDTO;
import com.edutrack.common.CommonUtil;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;


/**
 * @author bschoi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BbsContentsDAO extends AbstractDAO {

	/**
	 * �н����� Ȯ��
	 * @param SystemCode
	 * @param BbsId
	 * @param SeqNo
	 * @param PassWd
	 * @return
	 * @throws DAOException
	 */
	public int getPasswdCnt(String SystemCode, int BbsId, int SeqNo, String PassWd) throws DAOException {
		int	retVal	=	0;
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT IFNULL(COUNT(seq_no),0) AS cnt ");
		sb.append(" FROM bbs_contents ");
		sb.append(" WHERE system_code = ? AND bbs_id = ? AND seq_no = ? AND reg_passwd = ? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setInteger(BbsId);
		sql.setInteger(SeqNo);
		sql.setString(PassWd);
		
		log.debug("[getPasswdCnt]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
				 retVal = rs.getInt("cnt");
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

		 return retVal;
	}

	/**
	 * �Խ��� seq_no �ְ�+1 �� �����´�
	 * @param SystemCode
	 * @param BbsId
	 * @return
	 * @throws DAOException
	 */
	public int getMaxSeqNo(String SystemCode, int BbsId) throws DAOException{
	 int  maxSeqNo = 0;

	 StringBuffer sb = new StringBuffer();
	  sb.append(" select ifnull(max(seq_no),0)+1 as max_seq_no ");
	  sb.append(" from bbs_contents ");
	  sb.append(" where system_code = ? and bbs_id = ? ");

	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setInteger(BbsId);

	 log.debug("[getMaxSeqNo]" + sql.toString());

	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 if(rs.next()){
		 	maxSeqNo = rs.getInt("max_seq_no");
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

	 return maxSeqNo;
	}

	/**
	 * ��ϵ� �Խñ� �� ���� �����´�.
	 * @param SystemCode
	 * @param BbsId
	 * @param Where
	 * @return
	 * @throws DAOException
	 */
	public int getBbsContentsCount(String SystemCode, int BbsId, String Where) throws DAOException{
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" select ifnull(count(seq_no),0) as cnt ");
		  sb.append(" from bbs_contents ");
		  sb.append(" where system_code = ? and bbs_id= ?");
		  if(!Where.equals("")){
		 	sb.append(" and "+Where);
		 }

		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setInteger(BbsId);


		 log.debug("[getBbsContentsCount]" + sql.toString());

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
	 * �Խñ� �� ���� �����´� (����Ʈ���� ���)
	 * @param SystemCode
	 * @param BbsId
	 * @param userType
	 * @param expireDate
	 * @param searchKey
	 * @param keyWord
	 * @return
	 * @throws DAOException
	 */
	public int getBbsContentsCount(String SystemCode, int BbsId, String userType, String expireDate, String searchKey, String keyWord) throws DAOException{
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" select ifnull(count(seq_no),0) as cnt ");
		  sb.append(" from bbs_contents ");
		  sb.append(" where system_code = ? and bbs_id= ?  ");
		  if(!userType.equals("") && !userType.equals("M"))
		  	sb.append(" and (target like ? or target='A')");
		  if(!expireDate.equals("")&& !userType.equals("M"))
		  	sb.append(" and (expire_date='' or expire_date <= ? ) ");
		  if(!keyWord.equals(""))
		  	sb.append("  and "+searchKey+" like ? ");

		  QueryStatement sql = new QueryStatement();
		  sql.setSql(sb.toString());
		  sql.setString(SystemCode);
		  sql.setInteger(BbsId);
		  if(!userType.equals("")&& !userType.equals("M"))
		  	sql.setString(userType);
		  if(!expireDate.equals("")&& !userType.equals("M"))
			sql.setString(expireDate);
		  if(!keyWord.equals(""))
			sql.setString("%"+keyWord+"%");

		 log.debug("[getBbsContentsListCount]" + sql.toString());

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
	 * �Խ��� ������ ����Ѵ�.
	 * @param bbsContents
	 * @return
	 * @throws DAOException
	 */
	public int addBbsContents2(BbsContentsDTO bbsContents) throws DAOException{
		int retVal = 0;

		 QueryStatement sql2 = new QueryStatement();
		 StringBuffer sb2 = new StringBuffer();
		 Statement st;

		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into test2(id, name)");
		 sb.append(" values( '8', empty_clob())");
		 sql.setSql(sb.toString());

		 try{
		     retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

	 return retVal;
	}

	public int addBbsContents(BbsContentsDTO bbsContents) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into bbs_contents(system_code, bbs_id, seq_no, bbs_no, depth_no, order_no, parent_no, editor_type,"+
		 		"contents_type, subject, keyword, contents, rfile_name1, sfile_name1, file_path1, file_size1,"+
		 		"rfile_name2, sfile_name2, file_path2, file_size2, rfile_name3, sfile_name3, file_path3, file_size3,"+
				"hit_no, expire_date, target, reg_id, reg_name, reg_email, reg_passwd, reg_date) ");
		 sb.append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
		 sql.setSql(sb.toString());

		 sql.setString(bbsContents.getSystemCode());
		 sql.setInteger(bbsContents.getBbsId());
		 sql.setInteger(bbsContents.getSeqNo());
		 sql.setInteger(bbsContents.getBbsNo());
		 sql.setInteger(bbsContents.getDepthNo());
		 sql.setInteger(bbsContents.getOrderNo());
		 sql.setInteger(bbsContents.getParentNo());
		 sql.setString(bbsContents.getEditorType());
		 sql.setString(bbsContents.getContentsType());
		 sql.setString(bbsContents.getSubject());
		 sql.setString(bbsContents.getKeyword());
		 sql.setString(bbsContents.getContents());
		 sql.setString(bbsContents.getRfileName1());
		 sql.setString(bbsContents.getSfileName1());
		 sql.setString(bbsContents.getFilePath1());
		 sql.setString(bbsContents.getFileSize1());
		 sql.setString(bbsContents.getRfileName2());
		 sql.setString(bbsContents.getSfileName2());
		 sql.setString(bbsContents.getFilePath2());
		 sql.setString(bbsContents.getFileSize2());
		 sql.setString(bbsContents.getRfileName3());
		 sql.setString(bbsContents.getSfileName3());
		 sql.setString(bbsContents.getFilePath3());
		 sql.setString(bbsContents.getFileSize3());
		 sql.setInteger(bbsContents.getHitNo());
		 sql.setString(bbsContents.getExpireDate());
		 sql.setString(bbsContents.getTarget());
		 sql.setString(bbsContents.getRegId());
		 sql.setString(bbsContents.getRegName());
		 sql.setString(bbsContents.getRegEmail());
		 sql.setString(bbsContents.getRegPasswd());
		 sql.setString(CommonUtil.getCurrentDate());

		 log.debug("[addBbsContents]" + sql.toString());
		 try{
		     retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}


	/**
	 * ��� ��Ͻ� ���� ������ ���� Update
	 * @param bbsContents
	 * @param actionMode	Ins,Del
	 * @return
	 * @throws DAOException
	 */
	public boolean replyUpdateBbsContents(BbsContentsDTO bbsContents, String actionMode) throws DAOException{
		boolean retVal = false;
		int iVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 if(actionMode.equals("Ins"))
		 	sb.append(" update bbs_contents set order_no = order_no+1  ");
		 else
		 	sb.append(" update bbs_contents set order_no = order_no-1  ");
		 sb.append(" where system_code = ? and bbs_id = ? and bbs_no = ? and order_no >= ? ");
		 sql.setSql(sb.toString());

		 sql.setString(bbsContents.getSystemCode());
		 sql.setInteger(bbsContents.getBbsId());
		 sql.setInteger(bbsContents.getBbsNo());
		 sql.setInteger(bbsContents.getOrderNo());

		 log.debug("[replyUpdateBbsContents]" + sql.toString());
		 try{
			 iVal = broker.executeUpdate(sql);
			 retVal  = true;
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
		}

	/**
	 * �Խ��� ������ �����Ѵ�.
	 * @param bbsContents
	 * @return
	 * @throws DAOException
	 */
	public int editBbsContents(BbsContentsDTO bbsContents) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update bbs_contents set editor_type = ? ,contents_type = ? , subject = ? , keyword = ? ,"+
		 	"contents = ?, rfile_name1 = ? , sfile_name1 = ? , file_path1 = ? , file_size1 = ? ,"+
	 		"rfile_name2 = ? , sfile_name2 = ? , file_path2 = ? , file_size2 = ? ,"+
			"rfile_name3 = ? , sfile_name3 = ? , file_path3 = ? , file_size3 = ? ,"+
			"expire_date = ? , target = ? ,reg_name = ? ,  reg_email = ? , mod_id = ? , mod_date = ?  ");
		 sb.append(" where system_code = ? and bbs_id = ? and seq_no = ?");
		 sql.setSql(sb.toString());

		 sql.setString(bbsContents.getEditorType());
		 sql.setString(bbsContents.getContentsType());
		 sql.setString(bbsContents.getSubject());
		 sql.setString(bbsContents.getKeyword());
		 sql.setString(bbsContents.getContents());
		 sql.setString(bbsContents.getRfileName1());
		 sql.setString(bbsContents.getSfileName1());
		 sql.setString(bbsContents.getFilePath1());
		 sql.setString(bbsContents.getFileSize1());
		 sql.setString(bbsContents.getRfileName2());
		 sql.setString(bbsContents.getSfileName2());
		 sql.setString(bbsContents.getFilePath2());
		 sql.setString(bbsContents.getFileSize2());
		 sql.setString(bbsContents.getRfileName3());
		 sql.setString(bbsContents.getSfileName3());
		 sql.setString(bbsContents.getFilePath3());
		 sql.setString(bbsContents.getFileSize3());
		 sql.setString(bbsContents.getExpireDate());
		 sql.setString(bbsContents.getTarget());
		 sql.setString(bbsContents.getRegName());
		 sql.setString(bbsContents.getRegEmail());
		 sql.setString(bbsContents.getModId());
		 sql.setString(CommonUtil.getCurrentDate());

		 sql.setString(bbsContents.getSystemCode());
		 sql.setInteger(bbsContents.getBbsId());
		 sql.setInteger(bbsContents.getSeqNo());

		 log.debug("[editBbsContents]" + sql.toString());
		 try{
		     retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
	}

	/**
	 * �Խù� ��ȸ�� �����ϱ�
	 * @param SystemCode
	 * @param BbsId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int hitUpBbsContents(String SystemCode, int BbsId, int SeqNo) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update bbs_contents set hit_no=hit_no+1");
		 sb.append(" where system_code = ? and bbs_id = ? and seq_no = ?");
		 sql.setSql(sb.toString());

		 sql.setString(SystemCode);
		 sql.setInteger(BbsId);
		 sql.setInteger(SeqNo);

		 log.debug("[hitUpBbsContents]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
		}

	/**
	 * �Խù� ���� ��������
	 * @param Systemcode
	 * @param BbsId
	 * @param SeqNo
	 * @return RowSet
	 * @throws DAOException
	 */
	public BbsContentsDTO getBbsContents(String Systemcode, int BbsId, int SeqNo) throws DAOException{
		 BbsContentsDTO contentsDto = null;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select system_code, bbs_id, seq_no, bbs_no, depth_no, order_no, parent_no, editor_type,"+
	 		"contents_type, subject, keyword, contents, rfile_name1, sfile_name1, file_path1, file_size1,"+
	 		"rfile_name2, sfile_name2, file_path2, file_size2, rfile_name3, sfile_name3, file_path3, file_size3,"+
			"hit_no, expire_date, target, reg_id, reg_name, reg_email, reg_passwd, reg_date ");
		 sb.append(" from bbs_contents ");
		 sb.append(" where system_code = ? and bbs_id = ? and seq_no = ?");
		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 sql.setInteger(BbsId);
		 sql.setInteger(SeqNo);
		 log.debug("[getBbsContents]" + sql.toString());

		 ResultSet rs = null;
//		 Connection conn = null;
//	     DBConnectionManager pool = null;
//	     StringBuffer output = new StringBuffer();
		 try{
//		 	pool = DBConnectionManager.getInstance();
//            conn = pool.getConnection();
			rs = broker.executeQuery(sql);
			if(rs.next()){
				contentsDto = new BbsContentsDTO();
				contentsDto.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				contentsDto.setBbsId(rs.getInt("bbs_id"));
				contentsDto.setSeqNo(rs.getInt("seq_no"));
				contentsDto.setBbsNo(rs.getInt("bbs_no"));
				contentsDto.setDepthNo(rs.getInt("depth_no"));
				contentsDto.setOrderNo(rs.getInt("order_no"));
				contentsDto.setParentNo(rs.getInt("parent_no"));
				contentsDto.setEditorType(StringUtil.nvl(rs.getString("editor_type")));
				contentsDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
				contentsDto.setSubject(StringUtil.nvl(rs.getString("subject")));
				contentsDto.setKeyword(StringUtil.nvl(rs.getString("keyword")));
				contentsDto.setContents(StringUtil.nvl(rs.getString("contents")));
				contentsDto.setRfileName1(StringUtil.nvl(rs.getString("rfile_name1")));
				contentsDto.setSfileName1(StringUtil.nvl(rs.getString("sfile_name1")));
				contentsDto.setFilePath1(StringUtil.nvl(rs.getString("file_path1")));
				contentsDto.setFileSize1(StringUtil.nvl(rs.getString("file_size1")));
				contentsDto.setRfileName2(StringUtil.nvl(rs.getString("rfile_name2")));
				contentsDto.setSfileName2(StringUtil.nvl(rs.getString("sfile_name2")));
				contentsDto.setFilePath2(StringUtil.nvl(rs.getString("file_path2")));
				contentsDto.setFileSize2(StringUtil.nvl(rs.getString("file_size2")));
				contentsDto.setRfileName3(StringUtil.nvl(rs.getString("rfile_name3")));
				contentsDto.setSfileName3(StringUtil.nvl(rs.getString("sfile_name3")));
				contentsDto.setFilePath3(StringUtil.nvl(rs.getString("file_path3")));
				contentsDto.setFileSize3(StringUtil.nvl(rs.getString("file_size3")));
				contentsDto.setHitNo(rs.getInt("hit_no"));
				contentsDto.setExpireDate(StringUtil.nvl(rs.getString("expire_date")));
				contentsDto.setTarget(StringUtil.nvl(rs.getString("target")));
				contentsDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				contentsDto.setRegName(StringUtil.nvl(rs.getString("reg_name")));
				contentsDto.setRegEmail(StringUtil.nvl(rs.getString("reg_email")));
				contentsDto.setRegPasswd(StringUtil.nvl(rs.getString("reg_passwd")));
				contentsDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

//				Reader input = rs.getCharacterStream("contents");
//		        char[] buffer = new char[1024];
//		        int byteRead;
//
//		        while((byteRead=input.read(buffer,0,1024))!=-1)
//		        {
//		            output.append(buffer,0,byteRead);
//		        }
//		        input.close();
//
//		        contentsDto.setContents(StringUtil.nvl(output.toString()));
//		        output.delete(0,output.length());
			}
			rs.close();
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			try{
				if(rs != null) rs.close();
//			    if(conn != null){
//			        conn.setAutoCommit( true );
//			        pool.freeConnection(conn); // ���ؼ� ����
//			    }
			}catch(SQLException e){
				throw new DAOException(e.getMessage());
			}
		 }
		 return contentsDto;
	}
	/**
	 * ������ SeqNo �ҷ�����
	 * @param Systemcode
	 * @param BbsId
	 * @param BbsNo
	 * @return
	 * @throws DAOException
	 */
	public int getBbsContentsPrevSeqNo(String Systemcode, int BbsId, int BbsNo) throws DAOException{
		 int retVal = 0;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(min(seq_no),0) as seq_no");
		 sb.append(" from bbs_contents ");
		 sb.append(" where system_code = ? and bbs_id = ? and bbs_no > ? and depth_no = 0");
		 QueryStatement sql = new QueryStatement();

		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 sql.setInteger(BbsId);
		 sql.setInteger(BbsNo);
		 log.debug("[getBbsContentsPrevSeqNo]" + sql.toString());
		 RowSet rs = null;
		 try{
		 	rs = broker.executeQuery(sql);
		 	if(rs.next()){
		 		retVal = rs.getInt("seq_no");
			 }
			 rs.close();
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			 try{
				  if(rs != null) rs.close();
			}catch(SQLException e){
					throw new DAOException(e.getMessage());
			}
		}
		 return retVal;
	}
	/**
	 * ������ SeqNo �ҷ�����
	 * @param Systemcode
	 * @param BbsId
	 * @param BbsNo
	 * @return
	 * @throws DAOException
	 */
	public int getBbsContentsNextSeqNo(String Systemcode, int BbsId, int BbsNo) throws DAOException{
		 int retVal = 0;
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(max(seq_no),0) as seq_no");
		 sb.append(" from bbs_contents ");
		 sb.append(" where system_code = ? and bbs_id = ? and bbs_no < ? and depth_no = 0");
		 QueryStatement sql = new QueryStatement();

		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 sql.setInteger(BbsId);
		 sql.setInteger(BbsNo);
		 log.debug("[getBbsContentsNextSeqNo]" + sql.toString());
		 RowSet rs = null;
		 try{
		 	rs = broker.executeQuery(sql);
		 	if(rs.next()){
		 		retVal = rs.getInt("seq_no");
			 }
			 rs.close();
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			 try{
				  if(rs != null) rs.close();
			}catch(SQLException e){
					throw new DAOException(e.getMessage());
			}
		}
		 return retVal;
	}
	/**
	 * ������, ������ ������ ��������
	 * @param Systemcode
	 * @param BbsId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public String getBbsContentsSubject(String Systemcode, int BbsId, int SeqNo) throws DAOException{
		 String retVal = "";
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select subject ");
		 sb.append(" from bbs_contents ");
		 sb.append(" where system_code = ? and bbs_id = ? and seq_no = ? ");
		 QueryStatement sql = new QueryStatement();

		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 sql.setInteger(BbsId);
		 sql.setInteger(SeqNo);
		 log.debug("[getBbsContentsSubject]" + sql.toString());
		 RowSet rs = null;
		 try{
		 	rs = broker.executeQuery(sql);
		 	if(rs.next()){
		 		retVal = StringUtil.nvl(rs.getString("subject"));
			 }
			 rs.close();
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			 try{
				  if(rs != null) rs.close();
			}catch(SQLException e){
					throw new DAOException(e.getMessage());
			}
		}
		 return retVal;
	}

	/**
	 * �Խù��� �����Ѵ�.
	 * @param SystemCode
	 * @param BbsId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int delBbsContents(String SystemCode, int BbsId, int SeqNo) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" delete from bbs_contents ");
	 sb.append(" where system_code = ? and bbs_id = ?");
	 if(SeqNo>0)
	 	sb.append(" and seq_no = ?");
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setInteger(BbsId);
	 sql.setInteger(SeqNo);

	 log.debug("[delBbsContents]" + sql.toString());
	 try{
		 retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }

	 return retVal;
	}
	/**
	 * ÷�� ���� ������ �������� ���� �̸� Null �� update
	 * @param SystemCode
	 * @param BbsId
	 * @param SeqNo
	 * @param FileNo
	 * @return
	 * @throws DAOException
	 */
	public int delBbsContentsFile(String SystemCode, int BbsId, int SeqNo, String FileNo) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update bbs_contents set rfile_name"+FileNo+" = '',sfile_name"+FileNo+" = '',file_path"+FileNo+" = '',file_size"+FileNo+" = '' ");
		 sb.append(" where system_code = ? and bbs_id = ? and seq_no = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setInteger(BbsId);
		 sql.setInteger(SeqNo);

		 log.debug("[delBbsContentsFile]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return retVal;
		}

	/**
	 * �Խñ� ����Ʈ ��������(����¡ ����)
	 * @param Systemcode
	 * @param BbsId
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getBbsContentsList(String Systemcode, int BbsId, String addWhere, String order) throws DAOException{
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append("SELECT " +
		 				" a.system_code, a.bbs_id, a.seq_no, a.bbs_no" +
		 				", a.depth_no, a.order_no, a.parent_no, a.editor_type" +
		 				", a.contents_type, a.subject, a.rfile_name1, a.sfile_name1" +
		 				", a.file_path1, a.file_size1, a.rfile_name2, a.sfile_name2" +
		 				", a.file_path2, a.file_size2, a.rfile_name3, a.sfile_name3" +
		 				", a.file_path3, a.file_size3, a.hit_no, a.expire_date" +
		 				", a.target, a.reg_id, a.reg_name, a.reg_email" +
		 				", a.reg_passwd, a.reg_date, IFNULL(COUNT(b.comm_no),0) as comm_cnt ");
		 sb.append(" FROM bbs_contents a LEFT OUTER JOIN bbs_comment b " +
		 		" ON a.system_code = b.system_code and a.bbs_id = b.bbs_id and a.seq_no = b.seq_no ");
		 sb.append(" WHERE a.system_code = ? and a.bbs_id = ?");
		 if(!addWhere.equals(""))
		 	sb.append(" and "+addWhere);

		sb.append(" GROUP BY a.system_code, a.bbs_id, a.seq_no, a.bbs_no" +
				", a.depth_no, a.order_no, a.parent_no, a.editor_type" +
				", a.contents_type, a.subject, a.rfile_name1, a.sfile_name1" +
				", a.file_path1, a.file_size1, a.rfile_name2, a.sfile_name2" +
				", a.file_path2, a.file_size2, a.rfile_name3, a.sfile_name3" +
				", a.file_path3, a.file_size3, a.hit_no, a.expire_date" +
				", a.target, a.reg_id, a.reg_name, a.reg_email" +
				", a.reg_passwd, a.reg_date ");

		if(order.equals(""))
			sb.append(" ORDER BY a.bbs_no DESC, a.order_no ASC ");
		else
			sb.append(" "+order);

		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 sql.setInteger(BbsId);

		 log.debug("[getBbsContentsList]" + sql.toString());

		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 //-- rs.close() �� �ݵ�� JSP ���� �� ��
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }

		 return rs;
	}

	/**
	 * �Խ��� ���� ����Ʈ�� ����¡ ó���Ͽ� �����´�.
	 * @param curpage
	 * @param Systemcode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getBbsContentsPagingList(int curpage,int DispLine, int DispPage, String Systemcode, int BbsId, String userType, String expireDate, String searchKey, String keyWord , String order) throws DAOException{
		ListDTO retVal = null;
		try{
			StringBuffer sb = new StringBuffer();
			StringBuffer sbAlias = new StringBuffer();
			sbAlias.append(" system_code, bbs_id, seq_no, bbs_no" +
						", depth_no, order_no, parent_no, editor_type" +
						", contents_type, subject, rfile_name1, sfile_name1" +
						", file_path1, file_size1, rfile_name2, sfile_name2" +
						", file_path2, file_size2, rfile_name3, sfile_name3" +
						", file_path3, file_size3,hit_no, expire_date" +
						", target, reg_id, reg_name, reg_email" +
						", reg_passwd, reg_date ");

			sb.append(" a.system_code, a.bbs_id, a.seq_no, a.bbs_no" +
					", a.depth_no, a.order_no, a.parent_no, a.editor_type" +
					", a.contents_type, a.subject, a.rfile_name1, a.sfile_name1" +
					", a.file_path1, a.file_size1, a.rfile_name2, a.sfile_name2" +
					", a.file_path2, a.file_size2, a.rfile_name3, a.sfile_name3" +
					", a.file_path3, a.file_size3, a.hit_no, a.expire_date" +
					", a.target, a.reg_id, a.reg_name, a.reg_email" +
					", a.reg_passwd, a.reg_date ");

			// List Sql�� ����
			ListStatement sql = new ListStatement();

			sql.setTotalCol("a.seq_no");
			sql.setCutCol(" concat(char(a.bbs_id),char(a.seq_no))");

			sql.setAlias(sbAlias.toString()+", comm_cnt");
			sql.setSelect(sb.toString()+", ifnull(count(b.comm_no),0) as comm_cnt");

			sql.setFrom(" bbs_contents a LEFT OUTER JOIN bbs_comment b " +
						" ON a.system_code=b.system_code and a.bbs_id=b.bbs_id and a.seq_no=b.seq_no ");
			StringBuffer sbWhere = new StringBuffer();
			sbWhere.append(" a.system_code = ? and a.bbs_id = ?  and a.contents_type ='S' ");
			sql.setString(Systemcode);
			sql.setInteger(BbsId);
			String strWhere = "";

			if(!userType.equals("M")){
				sbWhere.append(" and (a.target like ? or a.target like '%A%' ) ");
				sql.setString(userType);
			}
			if(!expireDate.equals("") && !userType.equals("M")){
				sbWhere.append(" and (a.expire_date='' or a.expire_date is null or a.expire_date >= ? ) ");
				sql.setString(expireDate);
			}
			if(!keyWord.equals("")){
				sbWhere.append(" and a."+searchKey+" like ? ");
				sql.setString("%"+keyWord+"%");
			}
			sql.setWhere(sbWhere.toString());

			if(order.equals(""))
				sql.setOrderby(" a.bbs_no desc, a.order_no asc");
			else
				sql.setOrderby(" "+order);
            sql.setGroupby(sb.toString());

            // �Ķ���� ����
			//log.debug("[getBbsContentsPagingList]" + sql.toString());
            //System.out.println("[getBbsContentsPagingList]" + sql.toString());
            
			retVal = broker.executeListQuery(sql, curpage, DispLine, DispPage);

			return retVal;
		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			 log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		 }
	}
	
 

	public static void main(String args[]) throws DAOException{
		BbsContentsDAO ld = new BbsContentsDAO();
	
	    int i = 0;
	}


}


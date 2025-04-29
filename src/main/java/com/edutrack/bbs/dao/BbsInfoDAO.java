/*
 * Created on 2004. 6. 28.
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.edutrack.bbs.dao;

import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.bbs.dto.BbsInfoDTO;
import com.edutrack.common.CommonUtil;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;

/**
 * @author bschoi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BbsInfoDAO extends AbstractDAO {

   
	/**
	 * 게시판 아이디 최고값+1 을 가져온다
	 * @param SystemCode
	 * @return
	 * @throws DAOException
	 */
	public int getMaxBbsId(String SystemCode) throws DAOException{
	 int  maxId = 0;

	 StringBuffer sb = new StringBuffer();
	  sb.append(" select ifnull(max(bbs_id),0)+1 as max_id "); 
	  sb.append(" from bbs_info ");
	  sb.append(" where system_code = ? ");
	 
	 QueryStatement sql = new QueryStatement();
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	
	 log.debug("[getMaxBbsId]" + sql.toString());
	 
	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 if(rs.next()){
			maxId = rs.getInt("max_id");
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

	 return maxId;   
	}
	
	/**
	 * 등록된 게시판 수를 가져온다.
	 * @param SystemCode
	 * @return
	 * @throws DAOException
	 */
	public int getBbsCount(String SystemCode) throws DAOException{
		 int  cnt = 0;

		 StringBuffer sb = new StringBuffer();
		  sb.append(" select ifnull(count(bbs_id),0) as cnt "); 
		  sb.append(" from bbs_info ");
		  sb.append(" where system_code = ? ");
		 
		 QueryStatement sql = new QueryStatement();
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		
		 log.debug("[getBbsCount]" + sql.toString());
		 
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
	 * 게시판 정보를 등록한다.
	 * @param bbsInfo
	 * @return
	 * @throws DAOException
	 */
	public int addBbsInfo(BbsInfoDTO bbsInfo) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" insert into bbs_info(system_code, bbs_id, bbs_name, bbs_type, disp_line, disp_page, manager_id, "+
	 		"file_use_yn, file_count, file_limit, use_yn, editor_yn, voice_yn, login_chk_yn, title_img, bbs_comment, "+
			"new_time, hot_chk, bad_word_use_yn, bad_word, mail_to_master, mail_to_manager, comment_use_yn, "+
			"emoticon_use_yn, view_thread_yn, view_prev_next_yn, reg_id, reg_date) "); 
	 sb.append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
	 sql.setSql(sb.toString());
	 
	 sql.setString(bbsInfo.getSystemCode());
	 sql.setInteger(bbsInfo.getBbsId());
	 sql.setString(bbsInfo.getBbsName());
	 sql.setString(bbsInfo.getBbsType());
	 sql.setInteger(bbsInfo.getDispLine() );
	 sql.setInteger(bbsInfo.getDispPage() );
	 sql.setString(bbsInfo.getManagerId() );
	 sql.setString(bbsInfo.getFileUseYn() );
	 sql.setInteger(bbsInfo.getFileCount() );
	 sql.setString(bbsInfo.getFileLimit() );
	 sql.setString(bbsInfo.getUseYn() );
	 sql.setString(bbsInfo.getEditorYn() );
	 sql.setString(bbsInfo.getVoiceYn() );
	 sql.setString(bbsInfo.getLoginChkYn() );
	 sql.setString(bbsInfo.getTitleImg() );
	 sql.setString(bbsInfo.getBbsComment());
	 sql.setInteger(bbsInfo.getNewTime() );
	 sql.setInteger(bbsInfo.getHotChk() );
	 sql.setString(bbsInfo.getBadWordUseYn() );
	 sql.setString(bbsInfo.getBadWord());
	 sql.setString(bbsInfo.getMailToMaster() );
	 sql.setString(bbsInfo.getMailToManager() );
	 sql.setString(bbsInfo.getCommentUseYn() );
	 sql.setString(bbsInfo.getEmoticonUseYn() );
	 sql.setString(bbsInfo.getViewThreadYn() );
	 sql.setString(bbsInfo.getViewPrevNextYn() );
	 sql.setString(bbsInfo.getRegId() );
	 sql.setString(CommonUtil.getCurrentDate());
 
	 log.debug("[addUser]" + sql.toString());
	 try{
	     retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }	

	 return retVal;   
	}
	/**
	 * 게시판 정보를 수정한다.
	 * @param bbsInfo
	 * @return
	 * @throws DAOException
	 */
	public int editBbsInfo(BbsInfoDTO bbsInfo) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update bbs_info set bbs_name = ? , bbs_type = ? , disp_line = ? , disp_page = ? , manager_id = ? , "+
	 		"file_use_yn = ? , file_count = ? , file_limit = ? , use_yn = ? , editor_yn = ? , voice_yn = ? , login_chk_yn = ? , title_img = ? , bbs_comment = ? , "+
			"new_time = ? , hot_chk = ? , bad_word_use_yn = ? , bad_word = ? , mail_to_master = ? , mail_to_manager = ? , comment_use_yn = ? , "+
			"emoticon_use_yn = ? , view_thread_yn = ? , view_prev_next_yn = ? , mod_id = ? , mod_date = ?  "); 
		 sb.append(" where system_code = ? and bbs_id = ?");
		 sql.setSql(sb.toString());

		 sql.setString(bbsInfo.getBbsName());
		 sql.setString(bbsInfo.getBbsType());
		 sql.setInteger(bbsInfo.getDispLine() );
		 sql.setInteger(bbsInfo.getDispPage() );
		 sql.setString(bbsInfo.getManagerId() );
		 sql.setString(bbsInfo.getFileUseYn() );
		 sql.setInteger(bbsInfo.getFileCount() );
		 sql.setString(bbsInfo.getFileLimit() );
		 sql.setString(bbsInfo.getUseYn() );
		 sql.setString(bbsInfo.getEditorYn() );
		 sql.setString(bbsInfo.getVoiceYn() );
		 sql.setString(bbsInfo.getLoginChkYn() );
		 sql.setString(bbsInfo.getTitleImg() );
		 sql.setString(bbsInfo.getBbsComment());
		 sql.setInteger(bbsInfo.getNewTime() );
		 sql.setInteger(bbsInfo.getHotChk() );
		 sql.setString(bbsInfo.getBadWordUseYn() );
		 sql.setString(bbsInfo.getBadWord());
		 sql.setString(bbsInfo.getMailToMaster() );
		 sql.setString(bbsInfo.getMailToManager() );
		 sql.setString(bbsInfo.getCommentUseYn() );
		 sql.setString(bbsInfo.getEmoticonUseYn() );
		 sql.setString(bbsInfo.getViewThreadYn() );
		 sql.setString(bbsInfo.getViewPrevNextYn() );
		 sql.setString(bbsInfo.getRegId() );
		 sql.setString(CommonUtil.getCurrentDate());
		 
		 sql.setString(bbsInfo.getSystemCode());
		 sql.setInteger(bbsInfo.getBbsId());

		 
		 log.debug("[editBbsInfo]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	

		 return retVal;   
		}


	/**
	 * 게시판 정보 가져오기
	 * @param Systemcode
	 * @param BbsId
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getBbsInfo(String Systemcode, int BbsId) throws DAOException{
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select ifnull(system_code,'') as system_code , ifnull(bbs_id, 0) as bbs_id,  ifnull(bbs_name,'') as  bbs_name, ifnull(bbs_type,'') as  bbs_type, ifnull(disp_line,10) as  disp_line, ifnull(disp_page,10) as  disp_page,  "+
		 		"ifnull(file_use_yn,'') as  file_use_yn, ifnull(file_count,0) as  file_count, ifnull(file_limit,0) as  file_limit, ifnull(use_yn,'N') as  use_yn, ifnull(editor_yn,'N') as  editor_yn, ifnull(voice_yn,'N') as  voice_yn, "+
				"ifnull(title_img,'') as  title_img, ifnull(bbs_comment,'') as  bbs_comment, ifnull(new_time,1) as  new_time, ifnull(hot_chk,100) as  hot_chk, ifnull(bad_word_use_yn,'N') as  bad_word_use_yn,"+
				"ifnull(bad_word,'') as  bad_word,  ifnull(comment_use_yn,'N') as  comment_use_yn, ifnull(emoticon_use_yn,'N') as  emoticon_use_yn, ifnull(view_thread_yn,'N') as  view_thread_yn,"+
				"ifnull(manager_id,'') as  manager_id, ifnull(mail_to_manager,'') as  mail_to_manager, ifnull(mail_to_master,'') as  mail_to_master, ifnull(login_chk_yn, 'Y') as  login_chk_yn , ifnull(mail_to_manager,'') as  mail_to_manager, ifnull(view_prev_next_yn,'N') as  view_prev_next_yn, ifnull(reg_id,'') as  reg_id, ifnull(reg_date,'') as  reg_date "); 
		 
		 sb.append(" from bbs_info ");
		 sb.append(" where system_code = ? and bbs_id = ? ");
		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 sql.setInteger(BbsId);
		 log.debug("[getBbsInfo]" + sql.toString());
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
	 * 게시판 정보 리스트 가져오기(페이징 없이)
	 * @param Systemcode
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getBbsInfoList(String Systemcode) throws DAOException{
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" select system_code, bbs_id, bbs_name, bbs_type, disp_line, disp_page, manager_id, "+
	 		"file_use_yn, file_count, file_limit, use_yn, editor_yn, voice_yn, login_chk_yn, title_img, "+
			"new_time, hot_chk, bad_word_use_yn, bad_word, mail_to_master, mail_to_manager, comment_use_yn, "+
			"emoticon_use_yn, view_thread_yn, view_prev_next_yn, reg_id, reg_date "); 
	 sb.append(" from bbs_info ");
	 sb.append(" where system_code = ? ");
	 sb.append(" order by bbs_id desc ");
	 sql.setSql(sb.toString());
	 sql.setString(Systemcode);
	 log.debug("[getBbsInfoList]" + sql.toString());
	 RowSet rs = null;
	 try{
		 rs = broker.executeQuery(sql);
		 //-- rs.close() 는 반드시 JSP 에서 해 줄
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }
//	 }finally{
//		 try{ 
//		  if(rs != null) rs.close();
//		 }catch(SQLException e){
//			throw new DAOException(e.getMessage());
//		 }
//	 }	

	 return rs;   
	}

	/**
	 * 게시판 정보를 삭제한다.
	 * @param SystemCode
	 * @param BbsId
	 * @return
	 * @throws DAOException
	 */
	public int delBbsInfo(String SystemCode, int BbsId) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 sb.append(" delete from bbs_info "); 
	 sb.append(" where system_code = ? and bbs_id = ?");
	 sql.setSql(sb.toString());
	 sql.setString(SystemCode);
	 sql.setInteger(BbsId);
	 
	 log.debug("[delBbsInfo]" + sql.toString());
	 try{
		 retVal = broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }	

	 return retVal;   
	}
	/**
	 * 첨부 파일 삭제시 정보에서 파일 이름 Null 로 update
	 * @param SystemCode
	 * @param BbsId
	 * @return
	 * @throws DAOException
	 */
	public int delBbsInfoFile(String SystemCode, int BbsId) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update bbs_info set title_img='' "); 
		 sb.append(" where system_code = ? and bbs_id = ?");
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setInteger(BbsId);
		 
		 log.debug("[delBbsInfoFile]" + sql.toString());
		 try{
			 retVal = broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	

		 return retVal;   
		}

	/**
	 * 게시판 정보 리스트를 페이징 처리하여 가져온다.
	 * @param curpage
	 * @param Systemcode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getBbsInfoPagingList(int curpage, String Systemcode) throws DAOException{
		ListDTO retVal = null;
		try{
			StringBuffer sb = new StringBuffer();
			sb.append(" system_code, bbs_id, bbs_name, bbs_type, disp_line, disp_page, manager_id, ");
			sb.append("file_use_yn, file_count, file_limit, use_yn, editor_yn, voice_yn, login_chk_yn, title_img, ");
			sb.append("new_time, hot_chk, bad_word_use_yn, bad_word, mail_to_master, mail_to_manager, ");
			sb.append(" comment_use_yn,emoticon_use_yn, view_thread_yn, view_prev_next_yn, reg_id, reg_date ");

			// List Sql문 생성 
			ListStatement sql = new ListStatement();
			sql.setTotalCol("bbs_id");
			sql.setCutCol("bbs_id");
			sql.setAlias(sb.toString());
			sql.setSelect(sb.toString()); 
			sql.setFrom(" bbs_info ");
			sql.setWhere(" system_code = "+"'"+Systemcode+"'");
			sql.setOrderby(" bbs_id desc ");
//            sql.setGroupby(" u_id, u_pw ");
            
            // 파라미터 셋팅
			log.debug("[getBbsInfoPagingList]" + sql.toString());
			//retVal = broker.executeListQuery(sql, curpage, dispLine, dispPage);
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

	
public static void main(String args[]) throws DAOException{
	BbsInfoDAO ld = new BbsInfoDAO();
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
    //ld.getBbsInfoPagingList(1,"1");
    int i = 0;
}

	
}


/*
 * Created on 2004. 10. 9.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curribbs.dao;

import java.sql.SQLException;


import javax.sql.RowSet;

import com.edutrack.curribbs.dto.CurriBbsInfoDTO;
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
public class CurriBbsInfoDAO extends AbstractDAO {
	 	
		/**
		 * 등록된 게시판 수를 가져온다.
		 * @param SystemCode
		 * @return
		 * @throws DAOException
		 */
		public int getCurriBbsCount(String SystemCode) throws DAOException{
			 int  cnt = 0;

			 StringBuffer sb = new StringBuffer();
			  sb.append(" select ifnull(count(bbs_type),0) as cnt "); 
			  sb.append(" from curri_bbs_info ");
			  sb.append(" where system_code = ? ");
			 
			 QueryStatement sql = new QueryStatement();
			 sql.setSql(sb.toString());
			 sql.setString(SystemCode);
			
			 log.debug("[getCurriBbsCount]" + sql.toString());
			 
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
		public int addCurriBbsInfo(CurriBbsInfoDTO curriBbsInfo) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into curri_bbs_info(system_code,  bbs_name, bbs_type, disp_line, disp_page,  "+
		 		"file_use_yn, file_count, file_limit, use_yn, editor_yn, voice_yn, title_img, bbs_comment, "+
				"new_time, hot_chk, bad_word_use_yn, bad_word,  comment_use_yn, "+
				"emoticon_use_yn, view_thread_yn, view_prev_next_yn, reg_id, reg_date) "); 
		 sb.append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		 sql.setSql(sb.toString());
		 
		 sql.setString(curriBbsInfo.getSystemCode());
		 sql.setString(curriBbsInfo.getBbsName());
		 sql.setString(curriBbsInfo.getBbsType());
		 sql.setInteger(curriBbsInfo.getDispLine() );
		 sql.setInteger(curriBbsInfo.getDispPage() );
		 sql.setString(curriBbsInfo.getFileUseYn() );
		 sql.setInteger(curriBbsInfo.getFileCount() );
		 sql.setString(curriBbsInfo.getFileLimit() );
		 sql.setString(curriBbsInfo.getUseYn() );
		 sql.setString(curriBbsInfo.getEditorYn() );
		 sql.setString(curriBbsInfo.getVoiceYn() );
		 sql.setString(curriBbsInfo.getTitleImg() );
		 sql.setString(curriBbsInfo.getBbsComment());
		 sql.setInteger(curriBbsInfo.getNewTime() );
		 sql.setInteger(curriBbsInfo.getHotChk() );
		 sql.setString(curriBbsInfo.getBadWordUseYn() );
		 sql.setString(curriBbsInfo.getBadWord());
		 sql.setString(curriBbsInfo.getCommentUseYn() );
		 sql.setString(curriBbsInfo.getEmoticonUseYn() );
		 sql.setString(curriBbsInfo.getViewThreadYn() );
		 sql.setString(curriBbsInfo.getViewPrevNextYn() );
		 sql.setString(curriBbsInfo.getRegId() );
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
		public int editCurriBbsInfo(CurriBbsInfoDTO curriBbsInfo) throws DAOException{
			 int retVal = 0;
			 QueryStatement sql = new QueryStatement();
			 StringBuffer sb = new StringBuffer();
			 sb.append(" update curri_bbs_info set bbs_name = ? ,  disp_line = ? , disp_page = ? , "+
		 		"file_use_yn = ? , file_count = ? , file_limit = ? , use_yn = ? , editor_yn = ? , voice_yn = ? , title_img = ? , bbs_comment = ? , "+
				"new_time = ? , hot_chk = ? , bad_word_use_yn = ? , bad_word = ? ,  comment_use_yn = ? , "+
				"emoticon_use_yn = ? , view_thread_yn = ? , view_prev_next_yn = ? , mod_id = ? , mod_date = ?  "); 
			 sb.append(" where system_code = ? and bbs_type = ?");
			 sql.setSql(sb.toString());

			 sql.setString(curriBbsInfo.getBbsName());			
			 sql.setInteger(curriBbsInfo.getDispLine() );
			 sql.setInteger(curriBbsInfo.getDispPage() );
			 sql.setString(curriBbsInfo.getFileUseYn() );
			 sql.setInteger(curriBbsInfo.getFileCount() );
			 sql.setString(curriBbsInfo.getFileLimit() );
			 sql.setString(curriBbsInfo.getUseYn() );
			 sql.setString(curriBbsInfo.getEditorYn() );
			 sql.setString(curriBbsInfo.getVoiceYn() );
			 sql.setString(curriBbsInfo.getTitleImg() );
			 sql.setString(curriBbsInfo.getBbsComment());
			 sql.setInteger(curriBbsInfo.getNewTime() );
			 sql.setInteger(curriBbsInfo.getHotChk() );
			 sql.setString(curriBbsInfo.getBadWordUseYn() );
			 sql.setString(curriBbsInfo.getBadWord());			
			 sql.setString(curriBbsInfo.getCommentUseYn() );
			 sql.setString(curriBbsInfo.getEmoticonUseYn() );
			 sql.setString(curriBbsInfo.getViewThreadYn() );
			 sql.setString(curriBbsInfo.getViewPrevNextYn() );
			 sql.setString(curriBbsInfo.getRegId() );
			 sql.setString(CommonUtil.getCurrentDate());
			 
			 sql.setString(curriBbsInfo.getSystemCode());
			 sql.setString(curriBbsInfo.getBbsType());
		
			 
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
		public RowSet getCurriBbsInfo(String Systemcode, String BbsType) throws DAOException{
			 QueryStatement sql = new QueryStatement();
			 StringBuffer sb = new StringBuffer();
			 sb.append(" select ifnull(system_code,'') system_code ,  ifnull(bbs_name,'') bbs_name, ifnull(bbs_type,'') bbs_type, ifnull(disp_line,10) disp_line, ifnull(disp_page,10) disp_page,  "+
			 		"ifnull(file_use_yn,'') file_use_yn, ifnull(file_count,0) file_count, ifnull(file_limit,0) file_limit, ifnull(use_yn,'N') use_yn, ifnull(editor_yn,'N') editor_yn, ifnull(voice_yn,'N') voice_yn, "+
					"ifnull(title_img,'') title_img, ifnull(bbs_comment,'') bbs_comment, ifnull(new_time,1) new_time, ifnull(hot_chk,100) hot_chk, ifnull(bad_word_use_yn,'N') bad_word_use_yn,"+
					"ifnull(bad_word,'') bad_word,  ifnull(comment_use_yn,'N') comment_use_yn, ifnull(emoticon_use_yn,'N') emoticon_use_yn, ifnull(view_thread_yn,'N') view_thread_yn,"+
					"ifnull(view_prev_next_yn,'N') view_prev_next_yn, ifnull(reg_id,'') reg_id, ifnull(reg_date,'') reg_date "); 
			 sb.append(" from curri_bbs_info ");
			 sb.append(" where system_code = ? and bbs_type = ? ");
			 sql.setSql(sb.toString());
			 sql.setString(Systemcode);
			 sql.setString(BbsType);
			 log.debug("[getCurriBbsInfo]" + sql.toString());
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
		public RowSet getCurriBbsInfoList(String Systemcode) throws DAOException{
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select system_code,  bbs_name, bbs_type, disp_line, disp_page,  "+
		 		" file_use_yn, file_count, file_limit, use_yn, editor_yn, voice_yn,  title_img, "+
				" new_time, hot_chk, bad_word_use_yn, bad_word,  comment_use_yn, "+
				" emoticon_use_yn, view_thread_yn, view_prev_next_yn, reg_id, reg_date "); 
		 sb.append(" from curri_bbs_info ");
		 sb.append(" where system_code = ? ");
		 sb.append(" order by bbs_type desc ");
		 sql.setSql(sb.toString());
		 sql.setString(Systemcode);
		 log.debug("[getCurriBbsInfoList]" + sql.toString());
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 //-- rs.close() 는 반드시 JSP 에서 해 줄
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
//		 }finally{
//			 try{ 
//			  if(rs != null) rs.close();
//			 }catch(SQLException e){
//				throw new DAOException(e.getMessage());
//			 }
//		 }	

		 return rs;   
		}

		/**
		 * 게시판 정보를 삭제한다.
		 * @param SystemCode
		 * @param BbsId
		 * @return
		 * @throws DAOException
		 */
		public int delCurriBbsInfo(String SystemCode, String BbsType) throws DAOException{
		 int retVal = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from curri_bbs_info "); 
		 sb.append(" where system_code = ? and bbs_type = ?");
		 sql.setSql(sb.toString());
		 sql.setString(SystemCode);
		 sql.setString(BbsType);
		 
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
		public int delCurriBbsInfoFile(String SystemCode, String BbsType) throws DAOException{
			 int retVal = 0;
			 QueryStatement sql = new QueryStatement();
			 StringBuffer sb = new StringBuffer();
			 sb.append(" update curri_bbs_info set title_img='' "); 
			 sb.append(" where system_code = ? and bbs_type = ?");
			 sql.setSql(sb.toString());
			 sql.setString(SystemCode);
			 sql.setString(BbsType);
			 
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
		public ListDTO getCurriBbsInfoPagingList(int curpage, String Systemcode) throws DAOException{
			ListDTO retVal = null;
			try{
				StringBuffer sb = new StringBuffer();
				sb.append(" system_code,  bbs_name, bbs_type, disp_line, disp_page,  ");
				sb.append("file_use_yn, file_count, file_limit, use_yn, editor_yn, voice_yn,  title_img, ");
				sb.append("new_time, hot_chk, bad_word_use_yn, bad_word, comment_use_yn, ");
				sb.append("emoticon_use_yn, view_thread_yn, view_prev_next_yn, reg_id, reg_date ");
				// List Sql문 생성 
				ListStatement sql = new ListStatement();
				sql.setTotalCol("bbs_type");
				sql.setCutCol("bbs_type");
				sql.setAlias(sb.toString());
				sql.setSelect(sb.toString());
				sql.setFrom(" curri_bbs_info ");
				sql.setWhere(" system_code = '"+Systemcode+"'");
				sql.setOrderby(" bbs_type desc ");
	            
	            // 파라미터 셋팅
				log.debug("[getCurriBbsInfoPagingList]" + sql.toString());
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
		CurriBbsInfoDAO ld = new CurriBbsInfoDAO();
//	    ld.getUserInfo("jang","jang");
//	    ld.checkUser("jang10");
//	    ld.userPagingList(3);

//	      UserInfo user = new UserInfo();
//		  for(int i=0; i < 100; i++){
//	        user.setNumber(i+1);
//		  	user.setUserId("jang"+i);
//		    user.setUserPw("jang"+i);
//		    
//		    ld.addUser(user);
//		  }  
//	    ld.delUser("jang2");
	      
//	    ArrayList a = ld.userList();
	    //ld.getCurriBbsInfoPagingList(1,"1");
	    int i = 0;
	}
		
}


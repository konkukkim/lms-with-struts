/*
 * Created on 2005. 8. 18.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.onlinequiz.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.currisub.dto.CurriSubDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.onlinequiz.dto.OnlineQuizDTO;


/**
 * @author HerSunJa
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OnlineQuizDAO  extends AbstractDAO{
	/**
	 * 온라인 퀴즈 리스트를 가져온다 
	 * @param Systemcode
	 * @return RowSet
	 * @throws DAOException
	 */
	public ArrayList getOnlineQuizPagingList(String Systemcode, String pKeyWord, String pSearchKey) throws DAOException{
	
	    OnlineQuizDTO onlineQuizDTO = null;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 ArrayList onlineQuizList = new ArrayList();
		 //ListStatement sql = new ListStatement();
		 
		 sb.append("SELECT system_code      ");
		 sb.append("      ,seq_no           ");
		 sb.append("      ,subject          ");
		 sb.append("      ,use_yn           ");
		 sb.append("      ,reg_date         ");
		 sb.append("  FROM online_quiz      ");
		 sb.append(" WHERE system_code = ?  ");
		 
		 sql.setSql(sb.toString());
		 sql.setString( Systemcode );
		 
			if(!pKeyWord.equals("")){
			    sb.append(" and a."+pSearchKey+" like ? ");
			    
				sql.setString("%"+pKeyWord+"%");
			}

		 
		 RowSet rs = null;
		 log.debug("[getOnlineQuizPagingList]" + sql.toString());		 
		 
		 try{
			 
		     rs = broker.executeQuery(sql);	
		     onlineQuizDTO =	null;		 
			 onlineQuizList = new ArrayList();

			 while(rs.next()){
			     onlineQuizDTO = new OnlineQuizDTO();

			     onlineQuizDTO.setSystemCode        ( rs.getString("system_code"        ) );
			     onlineQuizDTO.setSeqNo             ( rs.getString("seq_no"             ) );
			     onlineQuizDTO.setSubject           ( rs.getString("subject"            ) );
			     onlineQuizDTO.setUseYn             ( rs.getString("use_yn"             ) );
			     onlineQuizDTO.setRegDate           ( rs.getString("reg_date"           ) );
			     
			     onlineQuizList.add(onlineQuizDTO);
			 }
	
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
	
		 return onlineQuizList;
	}  
	
	
	/**
	 * 온라인 퀴즈 상세정보를 가져온다 
	 * @param Systemcode
	 * @return RowSet
	 * @throws DAOException
	 */
	public OnlineQuizDTO getOnlineQuizInfo(String Systemcode, String pSeqNo ) throws DAOException{
	
	     OnlineQuizDTO onlineQuizDTO = null;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();

		 sb.append("SELECT system_code        \n");
		 sb.append("      ,seq_no             \n");
		 sb.append("      ,subject            \n");
		 sb.append("      ,quiz_type          \n");
		 sb.append("      ,example1           \n");
		 sb.append("      ,example2           \n");
		 sb.append("      ,example3           \n");
		 sb.append("      ,example4           \n");
		 sb.append("      ,example5           \n");
		 sb.append("      ,answer             \n");
		 sb.append("      ,quiz_comment       \n");
		 sb.append("      ,quiz_curri_url     \n");
		 sb.append("      ,quiz_link_url      \n");
		 sb.append("      ,use_yn             \n");
		 sb.append("  FROM online_quiz        \n");
		 sb.append(" WHERE system_code = ?    \n");
		 
		 if(pSeqNo != null){
		     sb.append("   AND seq_no      = ?    \n");
		 } else {
		     sb.append("ORDER BY seq_no desc   \n");
		 }
		 
		 sql.setSql(sb.toString());
		 sql.setString( Systemcode );
		 
		 if(pSeqNo != null) {
			 sql.setString( pSeqNo     );
		 }
		 
		 RowSet rs = null;
		 log.debug("[getOnlineQuizInfo]" + sql.toString());		 
		 
		 try{
			 
		     rs = broker.executeQuery(sql);	

			 if(rs.next()){
			     onlineQuizDTO = new OnlineQuizDTO();
			     
			     onlineQuizDTO.setSystemCode       ( rs.getString("system_code"         ) );
			     onlineQuizDTO.setSeqNo            ( rs.getString("seq_no"              ) );
			     onlineQuizDTO.setSubject          ( rs.getString("subject"             ) );
			     onlineQuizDTO.setQuizType         ( rs.getString("quiz_type"           ) );
			     onlineQuizDTO.setExample1         ( rs.getString("example1"            ) );
			     onlineQuizDTO.setExample2         ( rs.getString("example2"            ) );
			     onlineQuizDTO.setExample3         ( rs.getString("example3"            ) );
			     onlineQuizDTO.setExample4         ( rs.getString("example4"            ) );
			     onlineQuizDTO.setExample5         ( rs.getString("example5"            ) );
			     onlineQuizDTO.setAnswer           ( rs.getString("answer"              ) );
			     onlineQuizDTO.setQuizComment      ( rs.getString("quiz_comment"        ) );
			     onlineQuizDTO.setQuizCurriUrl     ( rs.getString("quiz_curri_url"      ) );
			     onlineQuizDTO.setQuizLinkUrl      ( rs.getString("quiz_link_url"       ) );
			     onlineQuizDTO.setUseYn            ( rs.getString("use_yn"              ) );

			 }
	
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
	
		 return onlineQuizDTO;
	} 
	
	
	/**
	 * 연결개설강좌 를 가져온다 
	 * @param Systemcode
	 * @return RowSet
	 * @throws DAOException
	 */
	public ArrayList getCurriSubList(String Systemcode) throws DAOException{
	
	     QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 CurriSubDTO  curriSubDTO = new CurriSubDTO();
		 ArrayList    curriSubList = new ArrayList();
		 
		 sb.append("SELECT system_code      ");
		 sb.append("      ,curri_code       ");
		 sb.append("      ,curri_year       ");
		 sb.append("      ,curri_term       ");
		 sb.append("      ,curri_name       ");
		 sb.append("  FROM curri_sub        ");
		 sb.append(" WHERE system_code = ?  ");
		 sb.append("ORDER BY curri_year desc, curri_term desc  ");
		 
		 sql.setSql(sb.toString());
		 sql.setString( Systemcode );
		 
 		 RowSet rs = null;
		 log.debug("[getCurriSubList]" + sql.toString());		 
		 
		 try{
			 
		     rs = broker.executeQuery(sql);	
	
		     while(rs.next())
		     {
		         curriSubDTO = new CurriSubDTO();
		         
		         curriSubDTO.setSystemCode(rs.getString("system_code"));
		         curriSubDTO.setCurriCode(rs.getString("curri_code"));
		         curriSubDTO.setCurriYear(rs.getInt   ("curri_year"));
		         curriSubDTO.setCurriTerm(rs.getInt   ("curri_term"));
		         curriSubDTO.setCurriName(rs.getString("curri_name"));

		         curriSubList.add( curriSubDTO );
		     }
		     
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
	
		 return curriSubList;
	}

	
	/**
	 * 온라인 퀴즈 를 입력한다  
	 * @param movieHistory
	 * @return
	 * @throws DAOException
	 */
	public int addOnlineQuiz(OnlineQuizDTO onlineQuiz) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();

	 sb.append("INSERT INTO  online_quiz  ");
	 sb.append("     ( system_code        ");
	 sb.append("      ,seq_no             ");
	 sb.append("      ,subject            ");
	 sb.append("      ,quiz_type          ");
	 sb.append("      ,example1           ");
	 sb.append("      ,example2           ");
	 sb.append("      ,example3           ");
	 sb.append("      ,example4           ");
	 sb.append("      ,example5           ");
	 sb.append("      ,answer             ");
	 sb.append("      ,quiz_comment       ");
	 sb.append("      ,quiz_curri_url     ");
	 sb.append("      ,quiz_link_url      ");
	 sb.append("      ,use_yn             ");
	 sb.append("      ,reg_id             ");
	 sb.append("      ,reg_date )         ");
	 sb.append(" VALUES      ");
	 sb.append("     ( ?     ");   // system_code           
	 sb.append("      ,?     ");   // max_seq_no               
	 sb.append("      ,?     ");   // subject               
	 sb.append("      ,?     ");   // quiz_type             
	 sb.append("      ,?     ");   // example1              
	 sb.append("      ,?     ");   // example2              
	 sb.append("      ,?     ");   // example3              
	 sb.append("      ,?     ");   // example4              
	 sb.append("      ,?     ");   // example5              
	 sb.append("      ,?     ");   // answer                
	 sb.append("      ,?     ");   // quiz_comment          
	 sb.append("      ,?     ");   // quiz_curri_url        
	 sb.append("      ,?     ");   // quiz_link_url         
	 sb.append("      ,?     ");   // use_yn                
	 sb.append("      ,?     ");   // reg_id                
	 sb.append("      ,? )   ");   // reg_date        
	 
	 sql.setSql(sb.toString());
	 
	 sql.setString ( onlineQuiz.getSystemCode    () );
	 sql.setInteger( getOnlinePollMaxSeqNo(onlineQuiz.getSystemCode()) );
	 sql.setString ( onlineQuiz.getSubject       () );
	 sql.setString ( onlineQuiz.getQuizType      () );
	 sql.setString ( onlineQuiz.getExample1      () );
	 sql.setString ( onlineQuiz.getExample2      () );
	 sql.setString ( onlineQuiz.getExample3      () );
	 sql.setString ( onlineQuiz.getExample4      () );
	 sql.setString ( onlineQuiz.getExample5      () );
	 sql.setString ( onlineQuiz.getAnswer        () );
	 sql.setString ( onlineQuiz.getQuizComment   () );
	 sql.setString ( onlineQuiz.getQuizCurriUrl  () );
	 sql.setString ( onlineQuiz.getQuizLinkUrl   () );
	 sql.setString ( onlineQuiz.getUseYn         () );
	 sql.setString ( onlineQuiz.getRegId         () );
	 sql.setString ( CommonUtil.getCurrentDate());
 
	 log.debug("[addMovieHistory]" + sql.toString());


	 try{
	     retVal 		= 	broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }	
	 return retVal;   
	}
	
	
	
	/**
	 * (max(seq_no),0)+1  를 가져온다 
	 * @param Systemcode
	 * @return RowSet
	 * @throws DAOException
	 */
	public int getOnlinePollMaxSeqNo(String Systemcode) throws DAOException{
	
	     QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 CurriSubDTO  curriSubDTO = new CurriSubDTO();
		 ArrayList    curriSubList = new ArrayList();
		 int iMaxSeqNo = 0;
		 
		 sb.append("select ifnull(max(seq_no),0)+1 max_seq_no from online_quiz  where system_code =?   ");
		 
		 sql.setSql(sb.toString());
		 sql.setString( Systemcode );
		 
 		 RowSet rs = null;
		 log.debug("[getOnlinePollMaxSeqNo]" + sql.toString());		 
		 
		 try{
			 
		     rs = broker.executeQuery(sql);	
	
		     if(rs.next())
		     {
		     	iMaxSeqNo = rs.getInt("max_seq_no");
		     }
		     
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
	
		 return iMaxSeqNo;
	}
	
	
	/**
	 * 온라인 퀴즈 를  수정 한다  
	 * @param movieHistory
	 * @return
	 * @throws DAOException
	 */
	public int editOnlineQuiz(OnlineQuizDTO onlineQuiz) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();

	 sb.append("UPDATE online_quiz  ");
	 sb.append("  SET  subject        = ?  "); 
	 sb.append("     , quiz_type      = ?  ");
	 sb.append("     , example1       = ?  ");
	 sb.append("     , example2       = ?  ");
	 sb.append("     , example3       = ?  ");
	 sb.append("     , example4       = ?  ");
	 sb.append("     , example5       = ?  ");
	 sb.append("     , answer         = ?  ");
	 sb.append("     , quiz_comment   = ?  ");
	 sb.append("     , quiz_curri_url = ?  ");
	 sb.append("     , quiz_link_url  = ?  ");
	 sb.append("     , use_yn         = ?  ");
	 sb.append("     , mod_id         = ?  ");
	 sb.append("     , mod_date       = ?  ");
	 sb.append(" WHERE system_code    = ?  ");
	 sb.append("   AND seq_no         = ?  ");    

	 sql.setSql(sb.toString());
	 // update
	 sql.setString ( onlineQuiz.getSubject       () );
	 sql.setString ( onlineQuiz.getQuizType      () );
	 sql.setString ( onlineQuiz.getExample1      () );
	 sql.setString ( onlineQuiz.getExample2      () );
	 sql.setString ( onlineQuiz.getExample3      () );
	 sql.setString ( onlineQuiz.getExample4      () );
	 sql.setString ( onlineQuiz.getExample5      () );
	 sql.setString ( onlineQuiz.getAnswer        () );
	 sql.setString ( onlineQuiz.getQuizComment   () );
	 sql.setString ( onlineQuiz.getQuizCurriUrl  () );
	 sql.setString ( onlineQuiz.getQuizLinkUrl   () );
	 sql.setString ( onlineQuiz.getUseYn         () );
	 sql.setString ( onlineQuiz.getModId         () );
	 sql.setString ( CommonUtil.getCurrentDate());
	 // where
	 sql.setString ( onlineQuiz.getSystemCode    () );
	 sql.setString ( onlineQuiz.getSeqNo         () );
	 
	 log.debug("[editOnlineQuiz]" + sql.toString());
	 
	 try{
	     retVal 		= 	broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }	
	 return retVal;   
	}

	/**
	 * 온라인 퀴즈 를  삭제 한다  
	 * @param movieHistory
	 * @return
	 * @throws DAOException
	 */
	public int delOnlineQuiz(OnlineQuizDTO onlineQuiz) throws DAOException{
	 int retVal = 0;
	 QueryStatement sql = new QueryStatement();
	 StringBuffer sb = new StringBuffer();
	 
	 sb.append("DELETE FROM online_quiz    ");
	 sb.append(" WHERE system_code = ?       ");
	 sb.append("   AND seq_no      = ?       ");    
	 
	 sql.setSql(sb.toString());
	 
	 sql.setString ( onlineQuiz.getSystemCode  () );
	 sql.setInteger( onlineQuiz.getSeqNo       () );
	 
	 log.debug("[delOnlineQuiz]" + sql.toString());
	 
	 try{
	     retVal 		= 	broker.executeUpdate(sql);
	 }catch(Exception e){
		  log.error(e.getMessage());
		 throw new DAOException(e.getMessage());
	 }	
	 return retVal;   
	}
	
}

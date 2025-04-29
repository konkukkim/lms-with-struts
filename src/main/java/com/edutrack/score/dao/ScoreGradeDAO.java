/*
 * Created on 2007. 8. 9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.score.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.progauthor.dto.ProgMenuDTO;
import com.edutrack.score.dto.ScoreGradeDTO;

/**
 * @author HerSunJa
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ScoreGradeDAO extends AbstractDAO{

	
	public ArrayList getScoreGradeList(String systemcode,int pCurriYear, int pHakgiTerm, String pGradeCode) throws DAOException{
		 StringBuffer sb = new StringBuffer();
		 ArrayList arrList = new ArrayList();
		 ScoreGradeDTO scoreGradeDto = new ScoreGradeDTO();
		 
		 sb.append("select ");
		 sb.append(" CURRI_YEAR   ");
		 sb.append(" ,HAKGI_TERM   ");
		 sb.append(" ,GRADE_CODE   ");
		 sb.append(" ,grade_name   "); 
		 sb.append(" ,grade_point "); 	
		 sb.append(" ,fr_point	 "); 
		 sb.append(" ,to_point	 "); 
		 sb.append(" ,fr_rate	 "); 	
		 sb.append(" ,to_rate	 "); 	
		 sb.append(" ,use_yn "); 	
		 sb.append(" from score_grade ");
		 sb.append(" where system_code = ? ");
		 sb.append(" and CURRI_YEAR = ? ");
		 sb.append(" and HAKGI_TERM=? ");
		 if(pGradeCode!=null) sb.append(" and GRADE_CODE = '"+pGradeCode+"' ");
		 sb.append(" order by grade_point desc ");
		 	
		 QueryStatement sql = new QueryStatement();
		 sql.setString(systemcode);
		 sql.setInteger(pCurriYear);
		 sql.setInteger(pHakgiTerm);
        
		 sql.setSql(sb.toString());

		 log.debug("[getScoreGradeList]" + sql.toString());
		 
		 ResultSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 
			 while(rs.next()){
			 	scoreGradeDto = new ScoreGradeDTO(); 
			 	
			 	scoreGradeDto.setCurriYear (StringUtil.nvl(rs.getString("CURRI_YEAR"),0));
			 	scoreGradeDto.setHakgiTerm (StringUtil.nvl(rs.getString("HAKGI_TERM"),0));
			 	scoreGradeDto.setGradeCode (StringUtil.nvl(rs.getString("GRADE_CODE")));
			 	scoreGradeDto.setGradeName (StringUtil.nvl(rs.getString("grade_name")));
			 	scoreGradeDto.setGradePoint(Double.parseDouble(StringUtil.nvl(rs.getString("grade_point"),"0")));
			 	scoreGradeDto.setFrPoint  (Double.parseDouble(StringUtil.nvl(rs.getString("fr_point"),"0")));
			 	scoreGradeDto.setToPoint  (Double.parseDouble(StringUtil.nvl(rs.getString("to_point"),"0")));
			 	scoreGradeDto.setFrRate   (Double.parseDouble(StringUtil.nvl(rs.getString("fr_rate"),"0")));
			 	scoreGradeDto.setToRate   (Double.parseDouble(StringUtil.nvl(rs.getString("to_rate"),"0")));
			 	scoreGradeDto.setUseYn    (StringUtil.nvl(rs.getString("use_yn")));

			 	arrList.add(scoreGradeDto);
			 }
			 rs.close();
		 }catch(Exception e){
		      e.printStackTrace();
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			 try{ 
			  if(rs != null) rs.close();
			 }catch(Exception e){
				throw new DAOException(e.getMessage());
			 }
		 }
		 
		 return arrList;
	}
	

	/**
	 * 평가등급을 등록한다.
	 * @param scoreGradeDto
	 * @return
	 * @throws DAOException
	 */
	public int addScoreGrade(ScoreGradeDTO scoreGradeDto) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 		 
		 StringBuffer sb = new StringBuffer();
		 sb.append(" insert into  score_grade ( "); 
		 sb.append("  system_code 	"); 
		 sb.append(" ,curri_year	 "); 
		 sb.append(" ,hakgi_term	 "); 
		 sb.append(" ,grade_code	 "); 
		 sb.append(" ,grade_name	 "); 
		 sb.append(" ,grade_point "); 	
		 sb.append(" ,fr_point	 "); 
		 sb.append(" ,to_point	 "); 
		 sb.append(" ,fr_rate	 "); 	
		 sb.append(" ,to_rate	 "); 	
		 sb.append(" ,use_yn "); 	
		 sb.append(" ,reg_id	  ");  
		 sb.append(" ,reg_date )  "); 
		 sb.append(" select ?, ?, ?, ifnull(max(grade_code),0)+1, ? "); 
		 sb.append(" ,?, ?, ?, ?, ? ");
		 sb.append(" ,?, ?, getCurrentDate() ");
		 sb.append(" from score_grade  "); 
		 sb.append(" where system_code =? 	"); 
		 sb.append(" and curri_year=? "); 
		 sb.append(" and hakgi_term=? "); 
		 
		 sql.setSql(sb.toString());
	 	 sql.setString (scoreGradeDto.getSystemCode ());
	 	 sql.setInteger(scoreGradeDto.getCurriYear());
	 	 sql.setInteger(scoreGradeDto.getHakgiTerm   ());
	 	 sql.setString (scoreGradeDto.getGradeName ());
	 	 sql.setDouble (scoreGradeDto.getGradePoint ());
	 	 sql.setDouble (scoreGradeDto.getFrPoint());
	 	 sql.setDouble (scoreGradeDto.getToPoint());
	 	 sql.setDouble (scoreGradeDto.getFrRate ());
	  	 sql.setDouble (scoreGradeDto.getToRate ());
	  	 sql.setString (scoreGradeDto.getUseYn ());
	 	 sql.setString (scoreGradeDto.getRegId ());
	 	 // where
	 	 sql.setString (scoreGradeDto.getSystemCode ());
	 	 sql.setInteger(scoreGradeDto.getCurriYear());
	 	 sql.setInteger(scoreGradeDto.getHakgiTerm   ());
	 	 
		 log.debug("[addScoreGrade]" + sql.toString());

		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;
	}
	
	/**
	 * 평가등급을 수정한다.
	 * @param scoreGradeDto
	 * @return
	 * @throws DAOException
	 */
	public int editScoreGrade(ScoreGradeDTO scoreGradeDto) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update score_grade set  ");
		 sb.append("  GRADE_NAME=?, GRADE_POINT = ? , FR_POINT = ?, TO_POINT = ?, FR_RATE = ?, TO_RATE = ?");
		 sb.append(" ,use_yn=?, mod_id = ?, mod_date =  getCurrentDate() ");
		 sb.append(" where system_code = ? and CURRI_YEAR = ? and HAKGI_TERM=? and GRADE_CODE = ? ");
		 
		 sql.setSql(sb.toString());
		 sql.setString (scoreGradeDto.getGradeName ());
	 	 sql.setDouble (scoreGradeDto.getGradePoint ());
	 	 sql.setDouble (scoreGradeDto.getFrPoint());
	 	 sql.setDouble (scoreGradeDto.getToPoint());
	 	 sql.setDouble (scoreGradeDto.getFrRate ());
	  	 sql.setDouble (scoreGradeDto.getToRate ());
	  	 sql.setString (scoreGradeDto.getUseYn ());
	  	 sql.setString (scoreGradeDto.getModId ());
	  	 // where
	     sql.setString (scoreGradeDto.getSystemCode ());
	 	 sql.setInteger(scoreGradeDto.getCurriYear());
	 	 sql.setInteger(scoreGradeDto.getHakgiTerm   ());
	 	 sql.setString (scoreGradeDto.getGradeCode ());
	 	 
			
		 log.debug("[editScoreGrade]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;   
	} 
	
	
	/**
	 * 평가등급을 삭제한다...
	 * @param systemcode
	 * @param pCurriYear
	 * @param pHakgiTerm
	 * @param pGradeCode
	 * @return
	 * @throws DAOException
	 */
	public int delScoreGrade(String systemcode,int pCurriYear, int pHakgiTerm, String pGradeCode) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from score_grade  ");
		 sb.append(" where system_code = ? and CURRI_YEAR = ? and HAKGI_TERM=? ");
		 sb.append(" and GRADE_CODE = ? ");
		 
		 sql.setSql(sb.toString());
		 sql.setString(systemcode);
		 sql.setInteger(pCurriYear);
		 sql.setInteger(pHakgiTerm);
		 sql.setString(pGradeCode);
		 
		 log.debug("[delScoreGrade]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;   
	} 
}

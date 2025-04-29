/*
 * Created on 2004. 11. 18.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.recommendsite.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.progauthor.dto.ProgMenuDTO;
import com.edutrack.recommendsite.dto.RecommendSiteDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ISqlStatement;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RecommendSiteDAO extends AbstractDAO {

	/**
	 * 
	 */
	public RecommendSiteDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 메뉴를 뿌릴 때 사용..
	 * @param systemcode
	 * @param workGubun
	 * @param pMenuUser
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getRecommendSiteList(String systemcode, String pMasterCode, String pRecommCode, String pRecommDepth) throws DAOException{
		StringBuffer sb = new StringBuffer();
		ArrayList aList = new ArrayList();
		
		sb.append(" select a.system_code, ");
		sb.append("   a.recomm_code,");
		sb.append("   a.master_code,");
		sb.append("   a.recomm_name,");
		sb.append("   a.site_url 	");
		sb.append(" from recommend_site a ");
		sb.append(" where a.system_code = ? ");
		sb.append("  and a.master_code  = ?");
	
		if(pRecommCode!=null && !("").equals(pRecommCode)){
			sb.append("  and a.recomm_code = ?");
		}
		
		if(!("4").equals(pRecommDepth)){
			sb.append(" order by a.recomm_code  ");
		}else{
			sb.append(" order by a.recomm_name  ");
		}
		
		
		QueryStatement sql = new QueryStatement();
		ResultSet rs = null;
		RecommendSiteDTO RecommendSite = new RecommendSiteDTO();
		
		try{
			
			sql.setSql(sb.toString());
			sql.setString(systemcode);
			sql.setString(pMasterCode);
			if(pRecommCode!=null && !("").equals(pRecommCode)){
				sql.setString(pRecommCode);
			}
					 
			log.debug("[getRecommendSiteList]" + sql.toString());
			rs = broker.executeQuery(sql);
			
			while(rs.next()){
				RecommendSite = new RecommendSiteDTO();
				
				RecommendSite.setSystemCode (StringUtil.nvl(rs.getString("system_code")));
				RecommendSite.setRecommCode(StringUtil.nvl(rs.getString("recomm_code")));
				RecommendSite.setMasterCode(StringUtil.nvl(rs.getString("master_code")));
				RecommendSite.setRecommName(StringUtil.nvl(rs.getString("recomm_name")));
				RecommendSite.setSiteUrl(StringUtil.nvl(rs.getString("site_url")));
				
				aList.add(RecommendSite);
			}
 		}catch(Exception e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}	
		return aList;
	}
	

	/**
	 * 하단에 레이어에 투 depth 로 추천사이트를 보여준다.
	 * @param systemcode
	 * @param pMasterCode
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getRecommendSiteTwoDepth(String systemcode, String pMasterCode) throws DAOException{
		StringBuffer sb = new StringBuffer();
		ArrayList aList = new ArrayList();
		
		sb.append(" SELECT  x.recomm_code   ");	
		sb.append(" , x.master_code			");   
		sb.append(" , x.order_code          ");
		sb.append(" , x.recomm_name         ");
		sb.append(" , x.site_url            ");
		sb.append(" , x.recomm_depth         ");
		sb.append(" from (                  ");
		sb.append(" SELECT recomm_code      ");
		sb.append(" , master_code           ");
		sb.append(" , concat(recomm_code, '000000') order_code ");
		sb.append(" , recomm_depth           ");
		sb.append(" , recomm_name, site_url	");
		sb.append(" FROM recommend_site     ");
		sb.append(" where system_code=?     ");
		sb.append("  and master_code=? ");
		if(("000000").equals(pMasterCode)){
			sb.append(" and recomm_depth='1'");
		}else{
			sb.append(" and recomm_depth='3'");
		}
		sb.append(" UNION ALL               ");
		sb.append(" SELECT  b.recomm_code   ");
		sb.append(" , b.master_code         ");
		if(("000000").equals(pMasterCode)){
			sb.append(" , concat(b.master_code, b.recomm_code) order_code  ");
		}else{
			sb.append(" , concat(b.master_code, b.recomm_name) order_code  ");		// 4단계 data 는 이름순으로 sorting
		}
		sb.append(" , b.recomm_depth           ");
		sb.append(" , b.recomm_name ");
		sb.append(" , b.site_url    ");
		sb.append(" FROM recommend_site a , recommend_site b ");
		sb.append(" where a.system_code=b.system_code ");
		sb.append(" and a.recomm_code=b.master_code ");
		sb.append(" and a.system_code=? ");
		sb.append(" and a.master_code=? ");
		if(("000000").equals(pMasterCode)){
			sb.append(" and a.recomm_depth='1'      ");
			sb.append(" and b.recomm_depth='2'      ");
		}else{
			sb.append(" and a.recomm_depth='3'      ");
			sb.append(" and b.recomm_depth='4'      ");
		}
		sb.append(" ) x	");		
		sb.append(" order by x.order_code ");
		
		QueryStatement sql = new QueryStatement();
		ResultSet rs = null;
		RecommendSiteDTO RecommendSite = new RecommendSiteDTO();
		
		try{
			
			sql.setSql(sb.toString());
			sql.setString(systemcode);
			sql.setString(pMasterCode);
			sql.setString(systemcode);
			sql.setString(pMasterCode);
			
					 
			log.debug("[getRecommendSiteTwoDepth]" + sql.toString());
			rs = broker.executeQuery(sql);
			
			while(rs.next()){
				RecommendSite = new RecommendSiteDTO();
				
				RecommendSite.setRecommCode(StringUtil.nvl(rs.getString("recomm_code")));
				RecommendSite.setMasterCode(StringUtil.nvl(rs.getString("master_code")));
				RecommendSite.setRecommName(StringUtil.nvl(rs.getString("recomm_name")));
				RecommendSite.setSiteUrl(StringUtil.nvl(rs.getString("site_url")));
				RecommendSite.setRecommDepth(StringUtil.nvl(rs.getString("recomm_depth")));
				
				aList.add(RecommendSite);
			}
 		}catch(Exception e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}	
		return aList;
	}
	
	
	/**
	 * 추천사이트를 수정한다...
	 * @param prog
	 * @return
	 * @throws DAOException
	 */
	public int editRecommendSite(RecommendSiteDTO prog) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update recommend_site set ");
		 sb.append("  recomm_name = ?");
		 sb.append(" ,site_url = ? ");
		 sb.append(" ,mod_id = ?");
		 sb.append(" ,mod_date =  getCurrentDate() ");
		 sb.append(" where system_code = ? ");
		 sb.append(" and master_code = ? ");
		 sb.append(" and recomm_code = ? ");
		  	
		 sql.setSql(sb.toString());
		 sql.setString(prog.getRecommName());
		 sql.setString(prog.getSiteUrl());
		 sql.setString(prog.getModId());
		 //where
		 sql.setString(prog.getSystemCode());
		 sql.setString(prog.getMasterCode());
		 sql.setString(prog.getRecommCode());;
		 	
		 log.debug("[editRecommendSite]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;   
	} 
	
	/**
	 * 추천사이트를 등록한다.
	 * @param RecommendSite
	 * @return
	 * @throws DAOException
	 */
	public int addRecommendSite(RecommendSiteDTO RecommendSite) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 
		 StringBuffer sb = new StringBuffer();
		 
		 sb.append("insert into recommend_site ( "); 
		 sb.append(" system_code"); 
		 sb.append(",recomm_code"); 
		 sb.append(",master_code"); 	// 자신이 최상위 경우 master_code='000000' //6자리
		 sb.append(",recomm_name"); 
		 sb.append(",site_url "); 
		 sb.append(",recomm_depth"); 
		 sb.append(",reg_id "); 
		 sb.append(",reg_date  ) ");
		 sb.append("select "); 
		 sb.append(" ? "); 
		 sb.append(",lpad(ifnull(max(recomm_code),0)+1,6,'0') "); 
		 sb.append(",? "); 
		 sb.append(",? "); 
		 sb.append(",? "); 
		 sb.append(",? "); 
		 sb.append(",? "); 
		 sb.append(",getCurrentDate() "); 
		 sb.append("from recommend_site "); 
		 sb.append("where system_code=? ");
		 //sb.append("and master_code=? ");
     
		 sql.setSql(sb.toString());
	 	 sql.setString (RecommendSite.getSystemCode ());
	 	 sql.setString (RecommendSite.getMasterCode());
	 	 sql.setString (RecommendSite.getRecommName());
	 	 sql.setString (RecommendSite.getSiteUrl());
	 	 sql.setString (RecommendSite.getRecommDepth());
	     sql.setString (RecommendSite.getRegId());
	     sql.setString (RecommendSite.getSystemCode ());
	     //sql.setString (RecommendSite.getMasterCode());
	 	 	
		 log.debug("[addRecommendSiteInfo]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;
	} 

	
	/**
	 * 추천사이트를 삭제한다.
	 * @param prog
	 * @return
	 * @throws DAOException
	 */
	public int deleteRecommendSite(RecommendSiteDTO prog) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from recommend_site  ");
		 sb.append(" where system_code = ? ");
		 sb.append("   and master_code = ?  ");
		 sb.append("   and recomm_code = ? ");
		 sb.append("   and recomm_depth = ? ");
		 
		 sql.setSql(sb.toString());
		 sql.setString(prog.getSystemCode());
		 sql.setString(prog.getMasterCode());
		 sql.setString(prog.getRecommCode());
		 sql.setString(prog.getRecommDepth());
		 	
		 log.debug("[deleteRecommendSite]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;   
	}
	
	
	/**
	 * 하위단의 추천사이트가 있는지 체크함..
	 * @param systemcode
	 * @param pRecommCode
	 * @param pRecommDepth
	 * @return
	 * @throws DAOException
	 */
	public int getCntRecommendSite(String systemcode, String pRecommCode, String pRecommDepth) throws DAOException{
		StringBuffer sb = new StringBuffer();
		QueryStatement sql = new QueryStatement();
		int iRecommCnt = 0;
		
		sb.append(" select count(recomm_code) cnt");
		sb.append(" from recommend_site ");
		sb.append(" where system_code = ?  ");
		sb.append("   and master_code = ? ");
		sb.append("   and recomm_depth= ?+1 "); // 하위단의 data 가 있는지....체크		
		
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setString(pRecommCode);
		sql.setInteger(Integer.parseInt(pRecommDepth));

		ResultSet rs = null;
		try{
					 
			log.debug("[getCntRecommendSite]" + sql.toString());
			rs = broker.executeQuery(sql);
			
			if(rs.next()){
				iRecommCnt = rs.getInt("cnt");
			}
 		}catch(Exception e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}	
		return iRecommCnt;
	}
	
}

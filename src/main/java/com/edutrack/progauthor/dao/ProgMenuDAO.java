/*
 * Created on 2004. 11. 18.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.progauthor.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.RowSet;

import com.edutrack.progauthor.dto.ProgMenuDTO;
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
public class ProgMenuDAO extends AbstractDAO {

	/**
	 * 
	 */
	public ProgMenuDAO() {
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
	public ArrayList progMenuList(String systemcode, String workGubun, String pMenuUser, String pMenuCode, String pMenuMaster, String pUseYn) throws DAOException{
		StringBuffer sb = new StringBuffer();
		ArrayList aList = new ArrayList();
		
		sb.append("select a.system_code, ");
		sb.append(" a.work_gubun,");
		sb.append(" a.menu_user,");
		sb.append(" a.menu_code,");
		sb.append(" a.menu_master,");
		sb.append(" a.menu_url,");
		sb.append(" a.menu_order,");
		sb.append(" a.menu_name,");
		sb.append(" a.menu_comment,");
		sb.append(" a.r_right,");
		sb.append(" a.c_right,");
		sb.append(" a.u_right,");
		sb.append(" a.d_right,");
		sb.append(" a.add_url1,");
		sb.append(" a.add_url2,");
		sb.append(" a.add_url3,");
		sb.append(" a.add_url4,");
		sb.append(" a.add_url5,");
		sb.append(" a.title_img,");
		sb.append(" a.use_yn,");
		sb.append(" a.reg_id,");
		sb.append(" a.reg_date, ");
		sb.append(" a.mod_id,");
		sb.append(" a.mod_date ");
		sb.append("  ,(select count(b.menu_code) ");
		sb.append("  from progmenu b ");
		sb.append("  where b.system_code=a.system_code ");
		sb.append("  and b.work_gubun=a.work_gubun ");
		sb.append("  and b.menu_user=a.menu_user   ");
		sb.append("  and b.menu_master=a.menu_code ");
		if(pUseYn!=null && !("").equals(pUseYn)){
			sb.append("  and b.use_yn='"+pUseYn+"'");
		}
		sb.append("  ) cnt_sub_menu   ");
		sb.append(" from progmenu a ");
		sb.append("where a.system_code = ? ");
		sb.append("  and a.work_gubun = ?");
		sb.append("  and a.menu_user  = ?");
		if(pUseYn!=null && !("").equals(pUseYn)){
			sb.append("  and a.use_yn  = '"+pUseYn+"'");
		}
		if(pMenuCode!=null && !("").equals(pMenuCode)){
			sb.append("  and a.menu_code  = '"+pMenuCode+"'");
		}
		if(pMenuMaster!=null && !("").equals(pMenuMaster)){
			sb.append("  and a.menu_master  = '"+pMenuMaster+"'");
		}
		sb.append(" order by a.work_gubun, a.menu_user, a.menu_order  ");
		
		QueryStatement sql = new QueryStatement();
		ResultSet rs = null;
		ProgMenuDTO MenuInfo = new ProgMenuDTO();
		
		try{
			sql.setString(systemcode);
			sql.setString(workGubun);
			sql.setString(pMenuUser);
			sql.setSql(sb.toString());
			 
			log.debug("[progMenuList]" + sql.toString());
			rs = broker.executeQuery(sql);
			
			while(rs.next()){
				MenuInfo = new ProgMenuDTO();
				
			 	MenuInfo.setSystemCode (StringUtil.nvl(rs.getString("system_code")));
			 	MenuInfo.setWorkGubun  (StringUtil.nvl(rs.getString("work_gubun")));
			 	MenuInfo.setMenuCode   (StringUtil.nvl(rs.getString("menu_code")));
			 	MenuInfo.setMenuUser   (StringUtil.nvl(rs.getString("menu_user")));
			 	MenuInfo.setMenuUrl    (StringUtil.nvl(rs.getString("menu_url")));
			 	MenuInfo.setMenuOrder  (StringUtil.nvl(rs.getString("menu_order")));
			 	MenuInfo.setMenuMaster (StringUtil.nvl(rs.getString("menu_master")));
			 	MenuInfo.setMenuName   (StringUtil.nvl(rs.getString("menu_name")));
			 	MenuInfo.setMenuComment(StringUtil.nvl(rs.getString("menu_comment")));
			 	MenuInfo.setRRight     (StringUtil.nvl(rs.getString("r_right")));
			 	MenuInfo.setCRight     (StringUtil.nvl(rs.getString("c_right")));
			 	MenuInfo.setURight     (StringUtil.nvl(rs.getString("u_right")));
			 	MenuInfo.setDRight     (StringUtil.nvl(rs.getString("d_right")));
			 	MenuInfo.setTitleImg   (StringUtil.nvl(rs.getString("title_img")));
			 	MenuInfo.setAddUrl1    (StringUtil.nvl(rs.getString("add_url1")));
			 	MenuInfo.setAddUrl2    (StringUtil.nvl(rs.getString("add_url2")));
			 	MenuInfo.setAddUrl3    (StringUtil.nvl(rs.getString("add_url3")));
			 	MenuInfo.setAddUrl4    (StringUtil.nvl(rs.getString("add_url4")));
			 	MenuInfo.setAddUrl5    (StringUtil.nvl(rs.getString("add_url5")));
			 	MenuInfo.setUseYn      (StringUtil.nvl(rs.getString("use_yn")));
			 	MenuInfo.setRegId      (StringUtil.nvl(rs.getString("reg_id")));
			 	MenuInfo.setRegDate    (StringUtil.nvl(rs.getString("reg_date")));
			 	MenuInfo.setModId      (StringUtil.nvl(rs.getString("mod_id")));
			 	MenuInfo.setModDate    (StringUtil.nvl(rs.getString("mod_date")));
			 	MenuInfo.setCntSubMenu (rs.getInt("cnt_sub_menu"));
				
				aList.add(MenuInfo);
			}
 		}catch(Exception e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}	
		return aList;
	}
	

	
	/**
	 * 
	 * @param prog
	 * @return
	 * @throws DAOException
	 */
	public int editProgMenuInfo(ProgMenuDTO prog) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append(" update progmenu set menu_url = ? ");
		 sb.append(" ,menu_name = ?, menu_comment = ? ");
		 sb.append(" ,r_right = ?, c_right = ?, u_right = ?, d_right = ?");
		 sb.append(" ,add_url1 = ? ,add_url2 = ? ,add_url3 = ? ,add_url4 = ? ,add_url5 = ? ");
		 sb.append(" ,title_img = ?, use_yn=? ");
		 sb.append(" ,mod_id = ?, mod_date =  getCurrentDate() ");
		 sb.append(" where system_code = ? and work_gubun = ? and menu_user=? and menu_code = ? ");
		 
		 
		 	
		 sql.setSql(sb.toString());
		 sql.setString(prog.getMenuUrl());
		 sql.setString(prog.getMenuName());
		 sql.setString(prog.getMenuComment());
		 sql.setString(prog.getRRight());
		 sql.setString(prog.getCRight());
		 sql.setString(prog.getURight());
		 sql.setString(prog.getDRight());
		 sql.setString(prog.getAddUrl1());
		 sql.setString(prog.getAddUrl2());
		 sql.setString(prog.getAddUrl3());
		 sql.setString(prog.getAddUrl4());
		 sql.setString(prog.getAddUrl5());
		 sql.setString(prog.getTitleImg());
		 sql.setString(prog.getUseYn());
		 sql.setString(prog.getModId());
		 //where
		 sql.setString(prog.getSystemCode());
		 sql.setString(prog.getWorkGubun());
		 sql.setString(prog.getMenuUser());
		 sql.setInteger(prog.getMenuCode());
			
		 log.debug("[editProgMenuInfo]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;   
	} 
	
	/**
	 * 
	 * @param MenuInfo
	 * @return
	 * @throws DAOException
	 */
	public int addProgMenuInfo(ProgMenuDTO MenuInfo) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 String sMaxMenuCode = getMaxMenuCode(MenuInfo.getSystemCode(), MenuInfo.getWorkGubun(), MenuInfo.getMenuUser());
		 String sMaxMenuOrder = getMaxMenuOrder(MenuInfo.getSystemCode(), MenuInfo.getWorkGubun(), MenuInfo.getMenuUser(), MenuInfo.getMenuMaster ());
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append("insert into progmenu ( "); 
		 sb.append(" system_code, work_gubun,  menu_code,  menu_user, menu_order");  // 화면 아이디(uri) 
		 sb.append(", menu_master, menu_url, menu_name, menu_comment");
		 sb.append(", r_right,    c_right,     u_right,    d_right  ");
		 sb.append(" ,add_url1 ,add_url2 ,add_url3 ,add_url4 ,add_url5 ");
		 sb.append(", title_img , use_yn   ");
		 sb.append(", reg_id, reg_date  ) ");
		 sb.append("values( ?, ?, ?, ?, ? "); 
		 sb.append(" , ?, ?, ?, ? ");
		 sb.append(" , ?, ?, ?, ? ");
		 sb.append(" , ?, ?, ?, ?, ? ");
		 sb.append(" , ?, ? ");
		 sb.append(" , ?, getCurrentDate() )");
	 
		 sql.setSql(sb.toString());
	 	 sql.setString (MenuInfo.getSystemCode ());
	 	 sql.setString (MenuInfo.getWorkGubun  ());
	 	 sql.setString (sMaxMenuCode  );
	 	 sql.setString (MenuInfo.getMenuUser   ());
	 	 sql.setString (sMaxMenuOrder );/////
	 	 sql.setString (MenuInfo.getMenuMaster ());
	 	 sql.setString (MenuInfo.getMenuUrl    ());
	 	 sql.setString (MenuInfo.getMenuName   ());
	 	 sql.setString (MenuInfo.getMenuComment());
	 	 sql.setString (MenuInfo.getRRight     ());
	 	 sql.setString (MenuInfo.getCRight     ());
	 	 sql.setString (MenuInfo.getURight     ());
	 	 sql.setString (MenuInfo.getDRight     ());
	 	 sql.setString (MenuInfo.getAddUrl1());
		 sql.setString (MenuInfo.getAddUrl2());
		 sql.setString (MenuInfo.getAddUrl3());
		 sql.setString (MenuInfo.getAddUrl4());
		 sql.setString (MenuInfo.getAddUrl5());
		 sql.setString (MenuInfo.getTitleImg   ());
	 	 sql.setString (MenuInfo.getUseYn      ());
	 	 sql.setString (MenuInfo.getRegId      ());
	 	 	
		 log.debug("[addProgMenuInfo]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;
	} 

	
	
	/**
	 * 
	 * @param systemcode
	 * @param proggubun
	 * @return
	 * @throws DAOException
	 */
	public String getMaxMenuCode(String systemcode, String proggubun, String menuUser) throws DAOException{
		 
		 String maxMenuCode = "";
		 StringBuffer sb = new StringBuffer();
		 // menu_code 를 5자리로 만들기 (앞부분에 0으로 채움)
		 sb.append("select lpad(ifnull(max(menu_code),0)+1,5,'0') as menu_code  ");
		 sb.append(" from progmenu ");
		 sb.append(" where system_code = ? and work_gubun = ? and menu_user=? ");
		 	
		 QueryStatement sql = new QueryStatement();
		 sql.setString(systemcode);
		 sql.setString(proggubun);
		 sql.setString(menuUser);
		 
		 sql.setSql(sb.toString());

		 log.debug("[getMaxMenuCode]" + sql.toString());
		 
		 ResultSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 
			 if(rs.next()){
			 	maxMenuCode = StringUtil.nvl(rs.getString("menu_code"));
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
		 
		 return maxMenuCode;
	}
	

	/**
	 * 메뉴번호를 가져온다..
	 * @param systemcode
	 * @param proggubun
	 * @param menuMaster
	 * @return
	 * @throws DAOException
	 */
	public String getMaxMenuOrder(String systemcode, String workGubun, String menuUser, String menuMaster ) throws DAOException{
		 
		 String maxMenuOrder = "";
		 StringBuffer sb = new StringBuffer();
		 // menu_order 를 4자리로 만들기 (앞부분에 0으로 채움)
		 // master_order + '00' + su bmenu order number : 모두 4자리 코드..
		 QueryStatement sql = new QueryStatement();
		 
		 if(("0").equals(menuMaster)){
			 sb.append("select lpad(ifnull(max(a.menu_order),0)+1,3,'0') as max_menu_order  "); 
			 sb.append(" from progmenu a  ");
			 sb.append(" where system_code = ? and work_gubun = ? and menu_user=? and menu_master = ? ");

			 sql.setString(systemcode);
			 sql.setString(workGubun);
			 sql.setString(menuUser);
			 sql.setString(menuMaster);

		 }else{
			 sb.append("select concat( lpad( b.menu_order,3,'0'),'-', lpad(ifnull(replace(max(a.menu_order),concat(b.menu_order,'-'),''),0)+1,3,'0')) ");
			 sb.append("  as  max_menu_order ");
			 sb.append(" from progmenu a right outer join (select menu_order from progmenu where system_code=? and work_gubun=? and menu_user=? and menu_code=? ) b ");
			 sb.append(" on system_code = ? and work_gubun = ? and menu_user=? and menu_master = ? ");
			 // inline view
			 sql.setString(systemcode);
			 sql.setString(workGubun);
			 sql.setString(menuUser);
			 sql.setString(menuMaster);
			 //where
			 sql.setString(systemcode);
			 sql.setString(workGubun);
			 sql.setString(menuUser);
			 sql.setString(menuMaster);

		 }
		 
		 sql.setSql(sb.toString());

		 log.debug("[getMaxMenuCode]" + sql.toString());
		 
		 ResultSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 
			 if(rs.next()){
			 	maxMenuOrder = StringUtil.nvl(rs.getString("max_menu_order"));
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
		 
		 return maxMenuOrder;
	}
	
	/**
	 * 
	 * @param systemcode
	 * @param proggubun
	 * @param pMenuUser
	 * @param pMenuLevel
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getProgMenuMasterList(String systemcode,String proggubun, String pMenuUser) throws DAOException{
		 StringBuffer sb = new StringBuffer();
		 ArrayList arrList = new ArrayList();
		 ProgMenuDTO MenuInfo = null;
		 
		 sb.append("select menu_code   ");  // 메뉴 코드..5자리 
		 sb.append(" , menu_name   ");  // 메뉴명 
		 sb.append(" from progmenu ");
		 sb.append(" where system_code = ? and work_gubun = ? and menu_user=? and menu_master = '0' ");
		 //if(pMenuCode!=null && !("").equals(pMenuCode)) sb.append(" and menu_code = '"+pMenuCode+"' ");
		 sb.append(" order by menu_order ");
		 	
		 QueryStatement sql = new QueryStatement();
		 sql.setString(systemcode);
		 sql.setString(proggubun);
		 sql.setString(pMenuUser);
         
		 sql.setSql(sb.toString());

		 log.debug("[getProgMenuMasterList]" + sql.toString());
		 
		 ResultSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 
			 while(rs.next()){
			 	MenuInfo = new ProgMenuDTO(); 
			 	
			 	MenuInfo.setMenuMaster (StringUtil.nvl(rs.getString("menu_code")));
			 	MenuInfo.setMenuName   (StringUtil.nvl(rs.getString("menu_name")));

			 	arrList.add(MenuInfo);
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
	 * 메뉴 순서를 저장한다..
	 * @param systemCode
	 * @param pWorkGubun
	 * @param pMenuUser
	 * @param menuCode
	 * @param regId
	 * @param menuCnt
	 * @return
	 * @throws DAOException
	 */
	public boolean editMenuOrderRegist(String systemCode, String pWorkGubun, String pMenuUser, String[] menuCode, String regId, int menuCnt) throws DAOException{
		 boolean retVal 	= 	false;
		 QueryStatement sql = 	new QueryStatement();
		 ISqlStatement[] sqlArray = new ISqlStatement[menuCnt];
		 ProgMenuDTO MenuInfo = new ProgMenuDTO();
		 ArrayList subMenuList = new ArrayList();

		 StringBuffer sb = new StringBuffer();
		 
		 sb.append(" update progmenu set ");
		 sb.append(" menu_order = ? ");
		 sb.append(" ,mod_id = ?");
		 sb.append(" ,mod_date =  getCurrentDate() ");
		 sb.append(" where system_code = ? and work_gubun = ? and menu_user=?  and menu_code = ? and menu_master=?");
	 
		 try{
		 	
			 int rowCnt = 0;
			 String sMenuOrder, sMenuOrder2 = "";
			 String sSubMenuOrder,sSubMenuOrder2 = "";
			 
			 for(int i=0;i<menuCode.length;i++){
			 	 sql 	= 	new QueryStatement();
			 	 
			 	 sMenuOrder = "000" + String.valueOf(i+1);
			 	 sMenuOrder2 = sMenuOrder.substring(sMenuOrder.length()-3, sMenuOrder.length());
			 	 
			 	 sql.setSql(sb.toString());
				 sql.setString (sMenuOrder2);	// 1~ numbering
			 	 sql.setString (regId);
			 	 sql.setString (systemCode);
			 	 sql.setString (pWorkGubun);
			 	 sql.setString (pMenuUser);
			 	 sql.setString (menuCode[i]);
			 	 sql.setString ("0"); // 상위 메뉴..
	 	 
			 	 sqlArray[rowCnt++] = sql;
			 	 
				 log.debug("[editMenuOrderRegist1]" + sql.toString());
				 
			 	 // 서브메뉴 목록을 가져온다.
			 	 subMenuList = progMenuList(systemCode, pWorkGubun, pMenuUser, null, menuCode[i],null);
		 	 
			 	 for(int j=0;j<subMenuList.size();j++){
				 	 MenuInfo = (ProgMenuDTO)subMenuList.get(j);
				 	 sql 	= 	new QueryStatement();
				 	
				 	 sSubMenuOrder = "000" + String.valueOf(j+1);
				 	 sSubMenuOrder2 = sSubMenuOrder.substring(sSubMenuOrder.length()-3, sSubMenuOrder.length());			 	 
				 	 
					 sql.setSql(sb.toString());
					 sql.setString (sMenuOrder2 +"-" + sSubMenuOrder2 );
				 	 sql.setString (regId);
				 	 sql.setString (systemCode);
				 	 sql.setString (pWorkGubun);
				 	 sql.setString (pMenuUser);
				 				 	 
				 	 sql.setString (MenuInfo.getMenuCode());
				 	 sql.setString (menuCode[i]);	// master code
				 	 
				 	sqlArray[rowCnt++] = sql;
				 	log.debug("[editMenuOrderRegist2]" + sql.toString());
			 	 }
			 }
			 
		     retVal 		= 	broker.executeUpdate(sqlArray);
		     
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;
	} 
	
	
	/**
	 * 메뉴를 삭제한다.
	 * @param prog
	 * @return
	 * @throws DAOException
	 */
	public int deleteProgMenuInfo(ProgMenuDTO prog) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append(" delete from progmenu  ");
		 sb.append(" where system_code = ? and work_gubun = ? and menu_user= ? and ( menu_code = ? OR menu_master = ? )");
		 
		 sql.setSql(sb.toString());
		 sql.setString(prog.getSystemCode());
		 sql.setString(prog.getWorkGubun());
		 sql.setString(prog.getMenuUser());
		 sql.setInteger(prog.getMenuCode());
		 sql.setInteger(prog.getMenuCode());
			
		 log.debug("[deleteProgMenuInfo]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;   
	}
	
	
	/**
	 * 메뉴를 복사해온다.
	 * @param systemCode
	 * @param pWorkGubun
	 * @param pTMenuUser
	 * @param pFMenuUser
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public int progMenuCoopy(String systemCode, String pWorkGubun, String pFMenuUser, String pTMenuUser, String userId) throws DAOException{
		 int retVal 			= 	0;
		 QueryStatement sql 	= 	new QueryStatement();
		 
		 StringBuffer sb = new StringBuffer();
		 sb.append("insert into progmenu  ( "); 
		 sb.append(" system_code, work_gubun,  menu_code,  menu_user, menu_order");  // 화면 아이디(uri) 
		 sb.append(", menu_master, menu_url, menu_name, menu_comment");
		 sb.append(", r_right,    c_right,     u_right,    d_right  ");
		 sb.append(", title_img , use_yn   , reg_id, reg_date )");
		 sb.append(" select system_code, work_gubun,  menu_code,  ?, menu_order");  // 화면 아이디(uri) 
		 sb.append(", menu_master, menu_url, menu_name, menu_comment");
		 sb.append(", r_right,    c_right,     u_right,    d_right  ");
		 sb.append(", title_img , use_yn ,  ?, getCurrentDate() ");
		 sb.append("from progmenu ");
		 sb.append("where system_code=? ");
		 sb.append("and work_gubun=? ");
		 sb.append("and menu_user=? ");
		 
		 sql.setSql(sb.toString());
	 	 sql.setString (pTMenuUser);
	 	 sql.setString (userId);
	 	 sql.setString (systemCode);
	 	 sql.setString (pWorkGubun);
	 	 sql.setString (pFMenuUser);
	 	 	
		 //log.debug("[progMenuCoopy]" + sql.toString());
		 try{
		     retVal 		= 	broker.executeUpdate(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }	
		 return retVal;
	}
	

	
	/**
	 * 메뉴 권한을 가져온다..
	 * @param systemcode
	 * @param gubunMenuCode
	 * @param pMenuUrl
	 * @param pMenuUser
	 * @param pRights
	 * @return  테이블에 존재하지 않을 경우 -1,
	 *  테이블에 존재하지만 권한이 없을경우는 0, 권한이 있으면 1이상을 반환한다.
	 *  무조건 user type 에 관계없이 메뉴가 입력된 건이 있으면권한을 체크함..
	 * @throws DAOException
	 */
	public int getMenuAuthor(String systemcode, String pMenuUrl, String pMenuUser, String pRights) throws DAOException{
		 StringBuffer sb = new StringBuffer();
		 int menuCnt = 0;
		 int initUri = pMenuUrl.indexOf("&");
		 String sInitUrl = (initUri>0)? pMenuUrl.substring(0, pMenuUrl.indexOf("&")) : pMenuUrl ;
		 
		 sb.append(" select case "); 
		 sb.append(" when (select count(menu_url) cnt from progmenu where system_code = ? and menu_url like concat(?, '%') /* and menu_user = ?*/  )=0 ");
		 sb.append(" then -1");	
		 sb.append(" else (select count(menu_url) cnt from progmenu where system_code = ? and menu_url = ? and menu_user = ?  and use_yn='Y' and "+pRights+"_right= 'Y') ");
		 sb.append(" end as cnt ");    
		 
//System.out.println("pMenuUrl:"+ pMenuUrl);
//System.out.println("sInitUrl:"+ sInitUrl);
//System.out.println("pRights:"+ pRights);
//System.out.println("pMenuUser:"+ pMenuUser);

		 QueryStatement sql = new QueryStatement();
		 //when
		 sql.setString(systemcode);
		 sql.setString(sInitUrl );
		 //sql.setString(pMenuUser);	
		 //else
		 sql.setString(systemcode);
		 sql.setString(pMenuUrl);
		 sql.setString(pMenuUser);
		 
		 sql.setSql(sb.toString());

		 //log.debug("[getMenuAuthor]" + sql.toString());
		 
		 ResultSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 
			 if(rs.next()){
			 	menuCnt = rs.getInt("cnt");
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
		 
		 return menuCnt;
	}
	
	
	/**
	 * 페이지의 네비게이션 타이틀을 반환한다..
	 * @param systemcode
	 * @param pMenuUrl
	 * @param pMenuUser
	 * @return
	 * @throws DAOException
	 */
	public String[] getMenuNaviTitle(String systemcode, String pMenuUrl, String pMenuUser) throws DAOException{
		 StringBuffer sb = new StringBuffer();
		 String[] menuName = new String[2];
		 
		 sb.append(" select a.menu_name, ifnull((select menu_name from progmenu where system_code=a.system_code and work_gubun=a.work_gubun and menu_user=a.menu_user and menu_code=a.menu_master ),'') master_menu_name ");
		 sb.append(" from progmenu a ");
		 sb.append(" where a.system_code = ? and a.menu_url = ? and a.menu_user = ? ");
		 
		 QueryStatement sql = new QueryStatement();
		 sql.setString(systemcode);
		 sql.setString(pMenuUrl);
		 sql.setString(pMenuUser);
		 
		 sql.setSql(sb.toString());

		 ResultSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 
			 if(rs.next()){
			 	menuName[0] = StringUtil.nvl(rs.getString("master_menu_name") );
			 	menuName[1] = StringUtil.nvl(rs.getString("menu_name"));
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
		 
		 return menuName;
	}
	
	
	/**
	 * 
	 * @param systemcode
	 * @param pMenuUrl
	 * @param pMenuUser
	 * @return
	 * @throws DAOException
	 */
	public String getMenuPageTitle(String systemcode, String pMenuUrl, String pMenuUser) throws DAOException{
		 StringBuffer sb = new StringBuffer();
		 String sPageTitle = "";
		 
		 sb.append(" select ifnull(a.menu_name,'') menu_name ");
		 sb.append(" from progmenu a ");
		 sb.append(" where a.system_code = ? and a.menu_url = ? and a.menu_user = ? ");
		 
		 QueryStatement sql = new QueryStatement();
		 sql.setString(systemcode);
		 sql.setString(pMenuUrl);
		 sql.setString(pMenuUser);
		 
		 sql.setSql(sb.toString());

		 ResultSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 
			 if(rs.next()){
			 	sPageTitle = StringUtil.nvl(rs.getString("menu_name") );
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
		 
		 return sPageTitle;
	}
}

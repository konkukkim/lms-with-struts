/*
 * Created on 2004. 11. 18.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.progauthor.dto;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProgMenuDTO {

	protected String  systemCode   = "";
	protected String  workGubun    = "";  /* 메뉴 작업 구분(SOCODE 참조, 501) */
	protected String  menuCode     = "00000" ;  /* 메뉴 코드..5자리 */
	protected String  menuUser     = "";  /* 사용자 타입, 관리자, 학습자, 교수자 */
	protected String  menuUrl      = "";  /* 화면 아이디(uri) */
	protected String  addUrl1      = "";  /* 추가url1 */
	protected String  addUrl2      = "";  /* 추가url2 */
	protected String  addUrl3      = "";  /* 추가url3 */
	protected String  addUrl4      = "";  /* 추가url4 */
	protected String  addUrl5      = "";  /* 추가url5 */
	protected String  menuOrder    = "" ;  /* 메뉴 순서 */
	protected String  menuMaster   = "";  /* 나의 윗단계 메뉴 코드 */
	//protected int     menuLevel    = 1 ;  /* 메뉴 단계 ... 1단계:1, 2단계:2 */
	protected String  menuName     = "";  /* 메뉴명 */
	protected String  menuComment  = "";
	protected String  RRight       = "";  /* 읽기 권한.. */
	protected String  CRight       = "";  /* 쓰기 권한.. */
	protected String  URight       = "";  /* 수정 권한.. */
	protected String  DRight       = "";  /* 삭제 권한.. */
	protected String  titleImg     = "";  /* 메뉴타이틀 이미지.. */
	protected String  useYn        = "";
	protected String  regId        = "";
	protected String  regDate      = "";
	protected String  modId        = "";
	protected String  modDate      = "";
	protected int 	  cntSubMenu   = 0 ;

	
	/**
	 * @return Returns the addUrl1.
	 */
	public String getAddUrl1() {
		return addUrl1;
	}
	/**
	 * @param addUrl1 The addUrl1 to set.
	 */
	public void setAddUrl1(String addUrl1) {
		this.addUrl1 = addUrl1;
	}
	/**
	 * @return Returns the addUrl2.
	 */
	public String getAddUrl2() {
		return addUrl2;
	}
	/**
	 * @param addUrl2 The addUrl2 to set.
	 */
	public void setAddUrl2(String addUrl2) {
		this.addUrl2 = addUrl2;
	}
	/**
	 * @return Returns the addUrl3.
	 */
	public String getAddUrl3() {
		return addUrl3;
	}
	/**
	 * @param addUrl3 The addUrl3 to set.
	 */
	public void setAddUrl3(String addUrl3) {
		this.addUrl3 = addUrl3;
	}
	/**
	 * @return Returns the addUrl4.
	 */
	public String getAddUrl4() {
		return addUrl4;
	}
	/**
	 * @param addUrl4 The addUrl4 to set.
	 */
	public void setAddUrl4(String addUrl4) {
		this.addUrl4 = addUrl4;
	}
	/**
	 * @return Returns the addUrl5.
	 */
	public String getAddUrl5() {
		return addUrl5;
	}
	/**
	 * @param addUrl5 The addUrl5 to set.
	 */
	public void setAddUrl5(String addUrl5) {
		this.addUrl5 = addUrl5;
	}
	/**
	 * @return Returns the cRight.
	 */
	public String getCRight() {
		return CRight;
	}
	/**
	 * @param right The cRight to set.
	 */
	public void setCRight(String right) {
		CRight = right;
	}
	/**
	 * @return Returns the dRight.
	 */
	public String getDRight() {
		return DRight;
	}
	/**
	 * @param right The dRight to set.
	 */
	public void setDRight(String right) {
		DRight = right;
	}

	/**
	 * @return Returns the menuCode.
	 */
	public String getMenuCode() {
		return menuCode;
	}
	/**
	 * @param menuCode The menuCode to set.
	 */
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	/**
	 * @return Returns the menuComment.
	 */
	public String getMenuComment() {
		return menuComment;
	}
	/**
	 * @param menuComment The menuComment to set.
	 */
	public void setMenuComment(String menuComment) {
		this.menuComment = menuComment;
	}


	/**
	 * @return Returns the menuMaster.
	 */
	public String getMenuMaster() {
		return menuMaster;
	}
	/**
	 * @param menuMaster The menuMaster to set.
	 */
	public void setMenuMaster(String menuMaster) {
		this.menuMaster = menuMaster;
	}
	/**
	 * @return Returns the menuName.
	 */
	public String getMenuName() {
		return menuName;
	}
	/**
	 * @param menuName The menuName to set.
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * @return Returns the menuUser.
	 */
	public String getMenuUser() {
		return menuUser;
	}
	/**
	 * @param menuUser The menuUser to set.
	 */
	public void setMenuUser(String menuUser) {
		this.menuUser = menuUser;
	}
	/**
	 * @return Returns the modDate.
	 */
	public String getModDate() {
		return modDate;
	}
	/**
	 * @param modDate The modDate to set.
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	/**
	 * @return Returns the modId.
	 */
	public String getModId() {
		return modId;
	}
	/**
	 * @param modId The modId to set.
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}
	/**
	 * @return Returns the regDate.
	 */
	public String getRegDate() {
		return regDate;
	}
	/**
	 * @param regDate The regDate to set.
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	/**
	 * @return Returns the regId.
	 */
	public String getRegId() {
		return regId;
	}
	/**
	 * @param regId The regId to set.
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return Returns the rRight.
	 */
	public String getRRight() {
		return RRight;
	}
	/**
	 * @param right The rRight to set.
	 */
	public void setRRight(String right) {
		RRight = right;
	}

	/**
	 * @return Returns the systemCode.
	 */
	public String getSystemCode() {
		return systemCode;
	}
	/**
	 * @param systemCode The systemCode to set.
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	/**
	 * @return Returns the uRight.
	 */
	public String getURight() {
		return URight;
	}
	/**
	 * @param right The uRight to set.
	 */
	public void setURight(String right) {
		URight = right;
	}
	/**
	 * @return Returns the workGubun.
	 */
	public String getWorkGubun() {
		return workGubun;
	}
	/**
	 * @param workGubun The workGubun to set.
	 */
	public void setWorkGubun(String workGubun) {
		this.workGubun = workGubun;
	}
	/**
	 * @return Returns the useYn.
	 */
	public String getUseYn() {
		return useYn;
	}
	/**
	 * @param useYn The useYn to set.
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	/**
	 * @return Returns the menuUrl.
	 */
	public String getMenuUrl() {
		return menuUrl;
	}
	/**
	 * @param menuUrl The menuUrl to set.
	 */
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	/**
	 * @return Returns the cntSubMenu.
	 */
	public int getCntSubMenu() {
		return cntSubMenu;
	}
	/**
	 * @param cntSubMenu The cntSubMenu to set.
	 */
	public void setCntSubMenu(int cntSubMenu) {
		this.cntSubMenu = cntSubMenu;
	}
	/**
	 * @return Returns the titleImg.
	 */
	public String getTitleImg() {
		return titleImg;
	}
	/**
	 * @param titleImg The titleImg to set.
	 */
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	/**
	 * @param menuOrder The menuOrder to set.
	 */
	public void setMenuOrder(String menuOrder) {
		this.menuOrder = menuOrder;
	}
	/**
	 * @return Returns the menuOrder.
	 */
	public String getMenuOrder() {
		return menuOrder;
	}
}

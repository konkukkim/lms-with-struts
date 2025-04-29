/*
 * Created on 2004. 11. 18.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.recommendsite.dto;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RecommendSiteDTO {

	protected String  systemCode  = "" ;        // 시스템 코드  	
	protected String  recommCode  = "" ;        // 추천 사이트코드 
	protected String  masterCode  = "" ;        // 상위코드  		
	protected String  recommName  = "" ;        // 추천사이트명  	
	protected String  siteUrl     = "" ;        // 추천사이트URL  
	protected String  recommDepth = "" ;        // 추천사이트 단계
	protected String  regId       = "" ;        // 등록자ID
	protected String  regDate     = "" ;        // 등록일자  
	protected String  modId       = "" ;        // 수정자ID  
	protected String  modDate     = "" ;        // 수정일자  
	
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public String getRecommCode() {
		return recommCode;
	}
	public void setRecommCode(String recommCode) {
		this.recommCode = recommCode;
	}
	public String getMasterCode() {
		return masterCode;
	}
	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}
	public String getRecommName() {
		return recommName;
	}
	public void setRecommName(String recommName) {
		this.recommName = recommName;
	}
	public String getSiteUrl() {
		return siteUrl;
	}
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public String getRecommDepth() {
		return recommDepth;
	}
	public void setRecommDepth(String recommDepth) {
		this.recommDepth = recommDepth;
	}

	
}

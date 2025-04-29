/*
 * Created on 2006. 7. 11.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.currisub.dto;

/**
 * @author narcisus
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CompanyCourseDTO {

    protected	String systemCode	=	"";
    protected	String curriCode	=	"";
    protected	int curriYear		=	0;
    protected	int curriTerm		=	0;
    protected	int companyCourseId	=	0;
    protected	String deptDaeCode	=	"";
    protected	String deptSoCode	=	"";
    protected	String companyImg	=	"";
    protected	String regId		=	"";
    protected	String regDate		=	"";
    
    
	public int getCompanyCourseId() {
		return companyCourseId;
	}
	public void setCompanyCourseId(int companyCourseId) {
		this.companyCourseId = companyCourseId;
	}
	public String getCompanyImg() {
		return companyImg;
	}
	public void setCompanyImg(String companyImg) {
		this.companyImg = companyImg;
	}
	public String getCurriCode() {
		return curriCode;
	}
	public void setCurriCode(String curriCode) {
		this.curriCode = curriCode;
	}
	public int getCurriTerm() {
		return curriTerm;
	}
	public void setCurriTerm(int curriTerm) {
		this.curriTerm = curriTerm;
	}
	public int getCurriYear() {
		return curriYear;
	}
	public void setCurriYear(int curriYear) {
		this.curriYear = curriYear;
	}
	public String getDeptDaeCode() {
		return deptDaeCode;
	}
	public void setDeptDaeCode(String deptDaeCode) {
		this.deptDaeCode = deptDaeCode;
	}
	public String getDeptSoCode() {
		return deptSoCode;
	}
	public void setDeptSoCode(String deptSoCode) {
		this.deptSoCode = deptSoCode;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
}

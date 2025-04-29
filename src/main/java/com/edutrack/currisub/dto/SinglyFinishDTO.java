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
public class SinglyFinishDTO {

    protected	String systemCode	=	"";
    protected	String curriCode	=	"";
    protected	int curriYear		=	0;
    protected	int curriTerm		=	0;
    protected	int sfId			=	0;
    protected	String courseName	=	"";
    protected	String studyStart	=	"";
    protected	String studyEnd		=	"";
    protected	String studyWhere	=	"";
    protected	String sucYn		=	"";    
    protected	String regId		=	"";
    protected	String regDate		=	"";
    protected	String modId		=	"";
    protected	String modDate		=	"";

    
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
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
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
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
	public int getSfId() {
		return sfId;
	}
	public void setSfId(int sfId) {
		this.sfId = sfId;
	}
	public String getStudyEnd() {
		return studyEnd;
	}
	public void setStudyEnd(String studyEnd) {
		this.studyEnd = studyEnd;
	}
	public String getStudyStart() {
		return studyStart;
	}
	public void setStudyStart(String studyStart) {
		this.studyStart = studyStart;
	}
	public String getStudyWhere() {
		return studyWhere;
	}
	public void setStudyWhere(String studyWhere) {
		this.studyWhere = studyWhere;
	}
	public String getSucYn() {
		return sucYn;
	}
	public void setSucYn(String sucYn) {
		this.sucYn = sucYn;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
}

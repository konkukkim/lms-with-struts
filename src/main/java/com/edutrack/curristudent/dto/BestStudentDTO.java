/**
 * @(#)StudentDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.curristudent.dto;


/**
 * BEST_STUDENT 테이블 Dto 클래스.
 *
 ************************************************
 * DATE         AUTHOR      DESCRIPTION
 * ----------------------------------------------
 * 2007. 3. 20.  mediopia       Initial Release
 ************************************************
 *
 * @author      sangsang
 * @version     1.0
 * @since       2007. 3. 20.
 */
public class BestStudentDTO {

    protected String systemCode;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected String studentId;
    protected String contents;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
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
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
    

}

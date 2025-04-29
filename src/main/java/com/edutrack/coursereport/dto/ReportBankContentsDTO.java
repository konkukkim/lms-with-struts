/**
 * @(#)ReportBkContentsDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.coursereport.dto;


/**
 * EXAM_BK_CONTENTS 테이블 Dto 클래스.
 *
 ************************************************
 * DATE         AUTHOR      DESCRIPTION
 * ----------------------------------------------
 * 2004. 9. 13.  mediopia       Initial Release
 ************************************************
 *
 * @author      mediopia
 * @version     1.0
 * @since       2004. 9. 13.
 */
public class ReportBankContentsDTO {

    protected String systemCode="";
    protected String courseId="";
    protected long reportBankId=0;
    protected int reportId=0;
    protected String reportSubject="";
    protected String reportContents="";
    protected String rfileName="";
    protected String sfileName="";
    protected String filePath="";
    protected String fileSize="";
    protected String regId="";
    protected String regDate="";
    protected String modId="";
    protected String modDate="";

    protected String oldRfile = "";
    protected String oldSfile = "";
    protected String oldFilePath = "";
    protected String oldFileSize = "";
    protected String fileCheck = "";
    

	/**
	 * @return the courseId
	 */
	public String getCourseId() {
		return courseId;
	}

	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the fileSize
	 */
	public String getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the modDate
	 */
	public String getModDate() {
		return modDate;
	}

	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	/**
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}

	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the reportBankId
	 */
	public long getReportBankId() {
		return reportBankId;
	}

	/**
	 * @param reportBankId the reportBankId to set
	 */
	public void setReportBankId(long reportBankId) {
		this.reportBankId = reportBankId;
	}

	/**
	 * @return the reportContent
	 */
	public String getReportContents() {
		return reportContents;
	}

	/**
	 * @param reportContent the reportContent to set
	 */
	public void setReportContents(String reportContents) {
		this.reportContents = reportContents;
	}

	/**
	 * @return the reportId
	 */
	public int getReportId() {
		return reportId;
	}

	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the reportSubject
	 */
	public String getReportSubject() {
		return reportSubject;
	}

	/**
	 * @param reportSubject the reportSubject to set
	 */
	public void setReportSubject(String reportSubject) {
		this.reportSubject = reportSubject;
	}

	/**
	 * @return the rfileName
	 */
	public String getRfileName() {
		return rfileName;
	}

	/**
	 * @param rfileName the rfileName to set
	 */
	public void setRfileName(String rfileName) {
		this.rfileName = rfileName;
	}

	/**
	 * @return the sfileName
	 */
	public String getSfileName() {
		return sfileName;
	}

	/**
	 * @param sfileName the sfileName to set
	 */
	public void setSfileName(String sfileName) {
		this.sfileName = sfileName;
	}

	/**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	
	/**
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return  "systemCode="+systemCode+",courseId="+courseId+",reportBankId="+reportBankId+",reportId="+reportId
               +",reportSubject="+reportSubject+",reportContents="+reportContents
               +",rfileName="+rfileName+",sfileName="+sfileName+",filePath="+filePath+",fileSize="+fileSize
               +",regId="+regId+",regDate="+regDate+",modId="+modId
               +",modDate="+modDate;
    }

	/**
	 * @return the fileCheck
	 */
	public String getFileCheck() {
		return fileCheck;
	}

	/**
	 * @param fileCheck the fileCheck to set
	 */
	public void setFileCheck(String fileCheck) {
		this.fileCheck = fileCheck;
	}

	/**
	 * @return the oldFilePath
	 */
	public String getOldFilePath() {
		return oldFilePath;
	}

	/**
	 * @param oldFilePath the oldFilePath to set
	 */
	public void setOldFilePath(String oldFilePath) {
		this.oldFilePath = oldFilePath;
	}

	/**
	 * @return the oldFileSize
	 */
	public String getOldFileSize() {
		return oldFileSize;
	}

	/**
	 * @param oldFileSize the oldFileSize to set
	 */
	public void setOldFileSize(String oldFileSize) {
		this.oldFileSize = oldFileSize;
	}

	/**
	 * @return the oldRfile
	 */
	public String getOldRfile() {
		return oldRfile;
	}

	/**
	 * @param oldRfile the oldRfile to set
	 */
	public void setOldRfile(String oldRfile) {
		this.oldRfile = oldRfile;
	}

	/**
	 * @return the oldSfile
	 */
	public String getOldSfile() {
		return oldSfile;
	}

	/**
	 * @param oldSfile the oldSfile to set
	 */
	public void setOldSfile(String oldSfile) {
		this.oldSfile = oldSfile;
	}

}

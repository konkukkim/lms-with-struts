/**
 * @(#)ReportInfoDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.coursereport.dto;


/**
 * REPORT_INFO 테이블 Dto 클래스.
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
public class ReportInfoDTO {

    protected String systemCode;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected String courseId;
    protected int reportId;
    protected String reportSubject;
    protected String reportContents;
    protected String reportType1;
    protected String reportType2;
    protected String reportStartDate;
    protected String reportEndDate;
    protected String reportExtendDate;
    protected String reportScoreYn;
    protected String reportOpenYn;
    protected String reportRegistYn;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    
    protected String autoBankYn;
    protected int reportBankId;
    protected String reportSendDelYn;
    
    protected String sendCheckYn;
    protected String markCheckYn;
    protected String insertYn;
    protected String stuOpenDate;
    protected String writeYn;
	
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
	 * @return the curriCode
	 */
	public String getCurriCode() {
		return curriCode;
	}


	/**
	 * @param curriCode the curriCode to set
	 */
	public void setCurriCode(String curriCode) {
		this.curriCode = curriCode;
	}


	/**
	 * @return the curriTerm
	 */
	public int getCurriTerm() {
		return curriTerm;
	}


	/**
	 * @param curriTerm the curriTerm to set
	 */
	public void setCurriTerm(int curriTerm) {
		this.curriTerm = curriTerm;
	}


	/**
	 * @return the curriYear
	 */
	public int getCurriYear() {
		return curriYear;
	}


	/**
	 * @param curriYear the curriYear to set
	 */
	public void setCurriYear(int curriYear) {
		this.curriYear = curriYear;
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
	 * @return the reportContents
	 */
	public String getReportContents() {
		return reportContents;
	}


	/**
	 * @param reportContents the reportContents to set
	 */
	public void setReportContents(String reportContents) {
		this.reportContents = reportContents;
	}


	/**
	 * @return the reportEndDate
	 */
	public String getReportEndDate() {
		return reportEndDate;
	}


	/**
	 * @param reportEndDate the reportEndDate to set
	 */
	public void setReportEndDate(String reportEndDate) {
		this.reportEndDate = reportEndDate;
	}


	/**
	 * @return the reportExtendDate
	 */
	public String getReportExtendDate() {
		return reportExtendDate;
	}


	/**
	 * @param reportExtendDate the reportExtendDate to set
	 */
	public void setReportExtendDate(String reportExtendDate) {
		this.reportExtendDate = reportExtendDate;
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
	 * @return the reportOpenYn
	 */
	public String getReportOpenYn() {
		return reportOpenYn;
	}


	/**
	 * @param reportOpenYn the reportOpenYn to set
	 */
	public void setReportOpenYn(String reportOpenYn) {
		this.reportOpenYn = reportOpenYn;
	}


	/**
	 * @return the reportRegistYn
	 */
	public String getReportRegistYn() {
		return reportRegistYn;
	}


	/**
	 * @param reportRegistYn the reportRegistYn to set
	 */
	public void setReportRegistYn(String reportRegistYn) {
		this.reportRegistYn = reportRegistYn;
	}


	/**
	 * @return the reportScoreYn
	 */
	public String getReportScoreYn() {
		return reportScoreYn;
	}


	/**
	 * @param reportScoreYn the reportScoreYn to set
	 */
	public void setReportScoreYn(String reportScoreYn) {
		this.reportScoreYn = reportScoreYn;
	}


	/**
	 * @return the reportStartDate
	 */
	public String getReportStartDate() {
		return reportStartDate;
	}


	/**
	 * @param reportStartDate the reportStartDate to set
	 */
	public void setReportStartDate(String reportStartDate) {
		this.reportStartDate = reportStartDate;
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
	 * @return the reportType1
	 */
	public String getReportType1() {
		return reportType1;
	}


	/**
	 * @param reportType1 the reportType1 to set
	 */
	public void setReportType1(String reportType1) {
		this.reportType1 = reportType1;
	}


	/**
	 * @return the reportType2
	 */
	public String getReportType2() {
		return reportType2;
	}


	/**
	 * @param reportType2 the reportType2 to set
	 */
	public void setReportType2(String reportType2) {
		this.reportType2 = reportType2;
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
        return  "systemCode="+systemCode+",curriCode="+curriCode+",curriYear="+curriYear+",curriTerm="+curriTerm+",courseId="+courseId
               +",reportId="+reportId+",reportSubject="+reportSubject+",reportContents="+reportContents+",reportType1="+reportType1+",reportType2="+reportType2
               +",reportStartDate="+reportStartDate+",reportEndDate="+reportEndDate+",reportExtendDate="+reportExtendDate
               +",reportScoreYn="+reportScoreYn+",reportOpenYn="+reportOpenYn+",reportRegistYn="+reportRegistYn
               +",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }


	/**
	 * @return the autoBankYn
	 */
	public String getAutoBankYn() {
		return autoBankYn;
	}


	/**
	 * @param autoBankYn the autoBankYn to set
	 */
	public void setAutoBankYn(String autoBankYn) {
		this.autoBankYn = autoBankYn;
	}


	/**
	 * @return the reportBankId
	 */
	public int getReportBankId() {
		return reportBankId;
	}


	/**
	 * @param reportBankId the reportBankId to set
	 */
	public void setReportBankId(int reportBankId) {
		this.reportBankId = reportBankId;
	}


	/**
	 * @return the reportSendDelYn
	 */
	public String getReportSendDelYn() {
		return reportSendDelYn;
	}


	/**
	 * @param reportSendDelYn the reportSendDelYn to set
	 */
	public void setReportSendDelYn(String reportSendDelYn) {
		this.reportSendDelYn = reportSendDelYn;
	}


	/**
	 * @return the autoCheckYn
	 */
	public String getMarkCheckYn() {
		return markCheckYn;
	}


	/**
	 * @param autoCheckYn the autoCheckYn to set
	 */
	public void setMarkCheckYn(String markCheckYn) {
		this.markCheckYn = markCheckYn;
	}


	/**
	 * @return the insertYn
	 */
	public String getInsertYn() {
		return insertYn;
	}


	/**
	 * @param insertYn the insertYn to set
	 */
	public void setInsertYn(String insertYn) {
		this.insertYn = insertYn;
	}


	/**
	 * @return the sendCheckYn
	 */
	public String getSendCheckYn() {
		return sendCheckYn;
	}


	/**
	 * @param sendCheckYn the sendCheckYn to set
	 */
	public void setSendCheckYn(String sendCheckYn) {
		this.sendCheckYn = sendCheckYn;
	}


	/**
	 * @return the stuOpenDate
	 */
	public String getStuOpenDate() {
		return stuOpenDate;
	}


	/**
	 * @param stuOpenDate the stuOpenDate to set
	 */
	public void setStuOpenDate(String stuOpenDate) {
		this.stuOpenDate = stuOpenDate;
	}


	/**
	 * @return the writeYn
	 */
	public String getWriteYn() {
		return writeYn;
	}


	/**
	 * @param writeYn the writeYn to set
	 */
	public void setWriteYn(String writeYn) {
		this.writeYn = writeYn;
	}

}

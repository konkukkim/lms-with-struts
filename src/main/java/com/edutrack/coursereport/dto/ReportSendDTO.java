/**
 * @(#)ReportSendDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.coursereport.dto;


/**
 * REPORT_SEND 테이블 Dto 클래스.
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
public class ReportSendDTO {

    protected String systemCode;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected String courseId;
    protected int reportId;
    protected String userId;
    protected int subReportId;
    protected String reportSendSubject;
    protected String reportSendContents;
    protected String editorType;
    protected String rfileName1;
    protected String sfileName1;
    protected String filePath1;
    protected String fileSize1;
    protected String rfileName2;
    protected String sfileName2;
    protected String filePath2;
    protected String fileSize2;
    protected String rfileName3;
    protected String sfileName3;
    protected String filePath3;
    protected String fileSize3;
    protected int sendCount;
    protected String stuOpenDate;
    protected String stuRegId;
    protected String stuRegDate;
    protected String stuModId;
    protected String stuModDate;
    protected String profMarkDate;
    protected int score;
    protected String profComment;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    
    protected String oldRfile1 = "";
    protected String oldSfile1 = "";
    protected String oldFilePath1 = "";
    protected String oldFileSize1 = "";
    protected String fileCheck1 = "";
    
    protected String oldRfile2 = "";
    protected String oldSfile2 = "";
    protected String oldFilePath2 = "";
    protected String oldFileSize2 = "";
    protected String fileCheck2 = "";
    
    protected String oldRfile3 = "";
    protected String oldSfile3 = "";
    protected String oldFilePath3 = "";
    protected String oldFileSize3 = "";
    protected String fileCheck3 = "";
    
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
	 * @return the editorType
	 */
	public String getEditorType() {
		return editorType;
	}
	/**
	 * @param editorType the editorType to set
	 */
	public void setEditorType(String editorType) {
		this.editorType = editorType;
	}
	/**
	 * @return the filePath1
	 */
	public String getFilePath1() {
		return filePath1;
	}
	/**
	 * @param filePath1 the filePath1 to set
	 */
	public void setFilePath1(String filePath1) {
		this.filePath1 = filePath1;
	}
	/**
	 * @return the filePath2
	 */
	public String getFilePath2() {
		return filePath2;
	}
	/**
	 * @param filePath2 the filePath2 to set
	 */
	public void setFilePath2(String filePath2) {
		this.filePath2 = filePath2;
	}
	/**
	 * @return the filePath3
	 */
	public String getFilePath3() {
		return filePath3;
	}
	/**
	 * @param filePath3 the filePath3 to set
	 */
	public void setFilePath3(String filePath3) {
		this.filePath3 = filePath3;
	}
	/**
	 * @return the fileSize1
	 */
	public String getFileSize1() {
		return fileSize1;
	}
	/**
	 * @param fileSize1 the fileSize1 to set
	 */
	public void setFileSize1(String fileSize1) {
		this.fileSize1 = fileSize1;
	}
	/**
	 * @return the fileSize2
	 */
	public String getFileSize2() {
		return fileSize2;
	}
	/**
	 * @param fileSize2 the fileSize2 to set
	 */
	public void setFileSize2(String fileSize2) {
		this.fileSize2 = fileSize2;
	}
	/**
	 * @return the fileSize3
	 */
	public String getFileSize3() {
		return fileSize3;
	}
	/**
	 * @param fileSize3 the fileSize3 to set
	 */
	public void setFileSize3(String fileSize3) {
		this.fileSize3 = fileSize3;
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
	 * @return the profComment
	 */
	public String getProfComment() {
		return profComment;
	}
	/**
	 * @param profComment the profComment to set
	 */
	public void setProfComment(String profComment) {
		this.profComment = profComment;
	}
	/**
	 * @return the profMarkDate
	 */
	public String getProfMarkDate() {
		return profMarkDate;
	}
	/**
	 * @param profMarkDate the profMarkDate to set
	 */
	public void setProfMarkDate(String profMarkDate) {
		this.profMarkDate = profMarkDate;
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
	 * @return the reportSendcontents
	 */
	public String getReportSendContents() {
		return reportSendContents;
	}
	/**
	 * @param reportSendcontents the reportSendcontents to set
	 */
	public void setReportSendContents(String reportSendContents) {
		this.reportSendContents = reportSendContents;
	}
	/**
	 * @return the reportSendsubject
	 */
	public String getReportSendSubject() {
		return reportSendSubject;
	}
	/**
	 * @param reportSendsubject the reportSendsubject to set
	 */
	public void setReportSendSubject(String reportSendSubject) {
		this.reportSendSubject = reportSendSubject;
	}
	/**
	 * @return the rfileName1
	 */
	public String getRfileName1() {
		return rfileName1;
	}
	/**
	 * @param rfileName1 the rfileName1 to set
	 */
	public void setRfileName1(String rfileName1) {
		this.rfileName1 = rfileName1;
	}
	/**
	 * @return the rfileName2
	 */
	public String getRfileName2() {
		return rfileName2;
	}
	/**
	 * @param rfileName2 the rfileName2 to set
	 */
	public void setRfileName2(String rfileName2) {
		this.rfileName2 = rfileName2;
	}
	/**
	 * @return the rfileName3
	 */
	public String getRfileName3() {
		return rfileName3;
	}
	/**
	 * @param rfileName3 the rfileName3 to set
	 */
	public void setRfileName3(String rfileName3) {
		this.rfileName3 = rfileName3;
	}
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * @return the sendCount
	 */
	public int getSendCount() {
		return sendCount;
	}
	/**
	 * @param sendCount the sendCount to set
	 */
	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}
	/**
	 * @return the sfileName1
	 */
	public String getSfileName1() {
		return sfileName1;
	}
	/**
	 * @param sfileName1 the sfileName1 to set
	 */
	public void setSfileName1(String sfileName1) {
		this.sfileName1 = sfileName1;
	}
	/**
	 * @return the sfileName2
	 */
	public String getSfileName2() {
		return sfileName2;
	}
	/**
	 * @param sfileName2 the sfileName2 to set
	 */
	public void setSfileName2(String sfileName2) {
		this.sfileName2 = sfileName2;
	}
	/**
	 * @return the sfileName3
	 */
	public String getSfileName3() {
		return sfileName3;
	}
	/**
	 * @param sfileName3 the sfileName3 to set
	 */
	public void setSfileName3(String sfileName3) {
		this.sfileName3 = sfileName3;
	}
	/**
	 * @return the stuModDate
	 */
	public String getStuModDate() {
		return stuModDate;
	}
	/**
	 * @param stuModDate the stuModDate to set
	 */
	public void setStuModDate(String stuModDate) {
		this.stuModDate = stuModDate;
	}
	/**
	 * @return the stuModId
	 */
	public String getStuModId() {
		return stuModId;
	}
	/**
	 * @param stuModId the stuModId to set
	 */
	public void setStuModId(String stuModId) {
		this.stuModId = stuModId;
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
	 * @return the stuRegDate
	 */
	public String getStuRegDate() {
		return stuRegDate;
	}
	/**
	 * @param stuRegDate the stuRegDate to set
	 */
	public void setStuRegDate(String stuRegDate) {
		this.stuRegDate = stuRegDate;
	}
	/**
	 * @return the stuRegId
	 */
	public String getStuRegId() {
		return stuRegId;
	}
	/**
	 * @param stuRegId the stuRegId to set
	 */
	public void setStuRegId(String stuRegId) {
		this.stuRegId = stuRegId;
	}
	/**
	 * @return the subReportId
	 */
	public int getSubReportId() {
		return subReportId;
	}
	/**
	 * @param subReportId the subReportId to set
	 */
	public void setSubReportId(int subReportId) {
		this.subReportId = subReportId;
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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the fileCheck1
	 */
	public String getFileCheck1() {
		return fileCheck1;
	}
	/**
	 * @param fileCheck1 the fileCheck1 to set
	 */
	public void setFileCheck1(String fileCheck1) {
		this.fileCheck1 = fileCheck1;
	}
	/**
	 * @return the fileCheck2
	 */
	public String getFileCheck2() {
		return fileCheck2;
	}
	/**
	 * @param fileCheck2 the fileCheck2 to set
	 */
	public void setFileCheck2(String fileCheck2) {
		this.fileCheck2 = fileCheck2;
	}
	/**
	 * @return the fileCheck3
	 */
	public String getFileCheck3() {
		return fileCheck3;
	}
	/**
	 * @param fileCheck3 the fileCheck3 to set
	 */
	public void setFileCheck3(String fileCheck3) {
		this.fileCheck3 = fileCheck3;
	}
	/**
	 * @return the oldFilePath1
	 */
	public String getOldFilePath1() {
		return oldFilePath1;
	}
	/**
	 * @param oldFilePath1 the oldFilePath1 to set
	 */
	public void setOldFilePath1(String oldFilePath1) {
		this.oldFilePath1 = oldFilePath1;
	}
	/**
	 * @return the oldFilePath2
	 */
	public String getOldFilePath2() {
		return oldFilePath2;
	}
	/**
	 * @param oldFilePath2 the oldFilePath2 to set
	 */
	public void setOldFilePath2(String oldFilePath2) {
		this.oldFilePath2 = oldFilePath2;
	}
	/**
	 * @return the oldFilePath3
	 */
	public String getOldFilePath3() {
		return oldFilePath3;
	}
	/**
	 * @param oldFilePath3 the oldFilePath3 to set
	 */
	public void setOldFilePath3(String oldFilePath3) {
		this.oldFilePath3 = oldFilePath3;
	}
	/**
	 * @return the oldFileSize1
	 */
	public String getOldFileSize1() {
		return oldFileSize1;
	}
	/**
	 * @param oldFileSize1 the oldFileSize1 to set
	 */
	public void setOldFileSize1(String oldFileSize1) {
		this.oldFileSize1 = oldFileSize1;
	}
	/**
	 * @return the oldFileSize2
	 */
	public String getOldFileSize2() {
		return oldFileSize2;
	}
	/**
	 * @param oldFileSize2 the oldFileSize2 to set
	 */
	public void setOldFileSize2(String oldFileSize2) {
		this.oldFileSize2 = oldFileSize2;
	}
	/**
	 * @return the oldFileSize3
	 */
	public String getOldFileSize3() {
		return oldFileSize3;
	}
	/**
	 * @param oldFileSize3 the oldFileSize3 to set
	 */
	public void setOldFileSize3(String oldFileSize3) {
		this.oldFileSize3 = oldFileSize3;
	}
	/**
	 * @return the oldRfile1
	 */
	public String getOldRfile1() {
		return oldRfile1;
	}
	/**
	 * @param oldRfile1 the oldRfile1 to set
	 */
	public void setOldRfile1(String oldRfile1) {
		this.oldRfile1 = oldRfile1;
	}
	/**
	 * @return the oldRfile2
	 */
	public String getOldRfile2() {
		return oldRfile2;
	}
	/**
	 * @param oldRfile2 the oldRfile2 to set
	 */
	public void setOldRfile2(String oldRfile2) {
		this.oldRfile2 = oldRfile2;
	}
	/**
	 * @return the oldRfile3
	 */
	public String getOldRfile3() {
		return oldRfile3;
	}
	/**
	 * @param oldRfile3 the oldRfile3 to set
	 */
	public void setOldRfile3(String oldRfile3) {
		this.oldRfile3 = oldRfile3;
	}
	/**
	 * @return the oldSfile1
	 */
	public String getOldSfile1() {
		return oldSfile1;
	}
	/**
	 * @param oldSfile1 the oldSfile1 to set
	 */
	public void setOldSfile1(String oldSfile1) {
		this.oldSfile1 = oldSfile1;
	}
	/**
	 * @return the oldSfile2
	 */
	public String getOldSfile2() {
		return oldSfile2;
	}
	/**
	 * @param oldSfile2 the oldSfile2 to set
	 */
	public void setOldSfile2(String oldSfile2) {
		this.oldSfile2 = oldSfile2;
	}
	/**
	 * @return the oldSfile3
	 */
	public String getOldSfile3() {
		return oldSfile3;
	}
	/**
	 * @param oldSfile3 the oldSfile3 to set
	 */
	public void setOldSfile3(String oldSfile3) {
		this.oldSfile3 = oldSfile3;
	}

    

}

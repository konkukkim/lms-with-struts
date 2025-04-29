/**
 * @(#)ReportAnswerDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.coursereport.dto;


/**
 * EXAM_ANSWER 테이블 Dto 클래스.
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
public class ReportAnswerDTO {

    protected String systemCode="";
    protected String curriCode="";
    protected int curriYear=0;
    protected int curriTerm=0;
    protected String courseId="";
    protected String userId="";
    protected int reportId=0;
    protected String reportOrder="";
    protected String reportNo="";
    protected int subReportId=0;
    protected String answer="";
    protected String rfileName1="";
    protected String sfileName1="";
    protected String filePath1="";
    protected String fileSize1="";
    protected String rfileName2="";
    protected String sfileName2="";
    protected String filePath2="";
    protected String fileSize2="";
    protected String rfileName3="";
    protected String sfileName3="";
    protected String filePath3="";
    protected String fileSize3="";
    protected String reportAnswer="";
    protected String reportScore="";
    protected String answerScore="";
    protected int totalScore=0;
    protected int answerTime=0;
    protected String resultCheck="N";
    protected String startDate="";
    protected String endDate="";
    protected String feedBack="";
    protected String flagRetest="";
    protected String regId="";
    protected String regDate="";
    protected String modId="";
    protected String modDate="";

    
	/**
	 * @return Returns the subReportId.
	 */
	public int getSubReportId() {
		return subReportId;
	}
	/**
	 * @param subReportId The subReportId to set.
	 */
	public void setSubReportId(int subReportId) {
		this.subReportId = subReportId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getAnswerScore() {
		return answerScore;
	}
	public void setAnswerScore(String answerScore) {
		this.answerScore = answerScore;
	}
	public int getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(int answerTime) {
		this.answerTime = answerTime;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
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
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getFeedBack() {
		return feedBack;
	}
	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}
	public String getFilePath1() {
		return filePath1;
	}
	public void setFilePath1(String filePath1) {
		this.filePath1 = filePath1;
	}
	public String getFilePath2() {
		return filePath2;
	}
	public void setFilePath2(String filePath2) {
		this.filePath2 = filePath2;
	}
	public String getFilePath3() {
		return filePath3;
	}
	public void setFilePath3(String filePath3) {
		this.filePath3 = filePath3;
	}
	public String getFileSize1() {
		return fileSize1;
	}
	public void setFileSize1(String fileSize1) {
		this.fileSize1 = fileSize1;
	}
	public String getFileSize2() {
		return fileSize2;
	}
	public void setFileSize2(String fileSize2) {
		this.fileSize2 = fileSize2;
	}
	public String getFileSize3() {
		return fileSize3;
	}
	public void setFileSize3(String fileSize3) {
		this.fileSize3 = fileSize3;
	}
	public String getFlagRetest() {
		return flagRetest;
	}
	public void setFlagRetest(String flagRetest) {
		this.flagRetest = flagRetest;
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
	public String getReportAnswer() {
		return reportAnswer;
	}
	public void setReportAnswer(String reportAnswer) {
		this.reportAnswer = reportAnswer;
	}
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public String getReportNo() {
		return reportNo;
	}
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	public String getReportOrder() {
		return reportOrder;
	}
	public void setReportOrder(String reportOrder) {
		this.reportOrder = reportOrder;
	}
	public String getReportScore() {
		return reportScore;
	}
	public void setReportScore(String reportScore) {
		this.reportScore = reportScore;
	}
	public String getResultCheck() {
		return resultCheck;
	}
	public void setResultCheck(String resultCheck) {
		this.resultCheck = resultCheck;
	}
	public String getRfileName1() {
		return rfileName1;
	}
	public void setRfileName1(String rfileName1) {
		this.rfileName1 = rfileName1;
	}
	public String getRfileName2() {
		return rfileName2;
	}
	public void setRfileName2(String rfileName2) {
		this.rfileName2 = rfileName2;
	}
	public String getRfileName3() {
		return rfileName3;
	}
	public void setRfileName3(String rfileName3) {
		this.rfileName3 = rfileName3;
	}
	public String getSfileName1() {
		return sfileName1;
	}
	public void setSfileName1(String sfileName1) {
		this.sfileName1 = sfileName1;
	}
	public String getSfileName2() {
		return sfileName2;
	}
	public void setSfileName2(String sfileName2) {
		this.sfileName2 = sfileName2;
	}
	public String getSfileName3() {
		return sfileName3;
	}
	public void setSfileName3(String sfileName3) {
		this.sfileName3 = sfileName3;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * toString methode: creates a String representation of the object
	 * @return the String representation
	 * @author info.vancauwenberge.tostring plugin
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ReportAnswerDTO[");
		buffer.append("systemCode = ").append(systemCode);
		buffer.append(", curriCode = ").append(curriCode);
		buffer.append(", curriYear = ").append(curriYear);
		buffer.append(", curriTerm = ").append(curriTerm);
		buffer.append(", courseId = ").append(courseId);
		buffer.append(", userId = ").append(userId);
		buffer.append(", reportId = ").append(reportId);
		buffer.append(", reportOrder = ").append(reportOrder);
		buffer.append(", reportNo = ").append(reportNo);
		buffer.append(", answer = ").append(answer);
		buffer.append(", rfileName1 = ").append(rfileName1);
		buffer.append(", sfileName1 = ").append(sfileName1);
		buffer.append(", filePath1 = ").append( filePath1);
		buffer.append(", fileSize1 = ").append( fileSize1);
		buffer.append(", rfileName2 = ").append(rfileName2);
		buffer.append(", sfileName2 = ").append(sfileName2);
		buffer.append(", filePath2 = ").append( filePath2);
		buffer.append(", fileSize2 = ").append( fileSize2);
		buffer.append(", rfileName3 = ").append(rfileName3);
		buffer.append(", sfileName3 = ").append(sfileName3);
		buffer.append(", filePath3 = ").append( filePath3);
		buffer.append(", fileSize3 = ").append( fileSize3);
		buffer.append(", reportScore = ").append(reportScore);
		buffer.append(", answerScore = ").append(answerScore);
		buffer.append(", totalScore = ").append(totalScore);
		buffer.append(", answerTime = ").append(answerTime);
		buffer.append(", startDate = ").append(startDate);
		buffer.append(", endDate = ").append(endDate);
		buffer.append(", feedBack = ").append(feedBack);
		buffer.append(", flagRetest = ").append(flagRetest);
		buffer.append(", regId = ").append(regId);
		buffer.append(", regDate = ").append(regDate);
		buffer.append(", modId = ").append(modId);
		buffer.append(", modDate = ").append(modDate);
		buffer.append("]");
		return buffer.toString();
	}
}
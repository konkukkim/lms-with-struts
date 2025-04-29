/**
 * @(#)CurriContentsDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.currisub.dto;


/**
 * 강의실 사용자 목차생성 DTO 클래스.
 *
 */
public class LecContentsDTO {

    protected String systemCode;
    protected String userId;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected String courseId;
    protected String contentsId;
    protected String contentsOrder;
    protected String contentsType;
    protected String contentsName;
    
    protected String filePath;
    protected String asfFilePath;
    protected String serverPath;
    
    protected String rFileName1;
    protected String sFileName1;
    protected String attachPath1;
    protected String rFileName2;
    protected String sFileName2;
    protected String attachPath2;
    
    protected String openChk;
    protected String openDate;
    protected String quizPass;
    protected String quizYn;
    protected int showTime;
    protected int totalTime;
    protected int joinCount;
    protected String sizeApp;
    protected int contentsWidth;
    protected int contentsHeight;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    protected String lectureGubun;
    protected String startDate;
    protected String endDate;
    protected String attendance;
    protected String curDate;
    protected String profName;

    
    


	/**
	 * @return attachPath1
	 */
	public String getAttachPath1() {
		return attachPath1;
	}
	/**
	 * @param attachPath1 설정하려는 attachPath1
	 */
	public void setAttachPath1(String attachPath1) {
		this.attachPath1 = attachPath1;
	}
	/**
	 * @return attachPath2
	 */
	public String getAttachPath2() {
		return attachPath2;
	}
	/**
	 * @param attachPath2 설정하려는 attachPath2
	 */
	public void setAttachPath2(String attachPath2) {
		this.attachPath2 = attachPath2;
	}
	/**
	 * @return rFileName1
	 */
	public String getRFileName1() {
		return rFileName1;
	}
	/**
	 * @param fileName1 설정하려는 rFileName1
	 */
	public void setRFileName1(String fileName1) {
		rFileName1 = fileName1;
	}
	/**
	 * @return rFileName2
	 */
	public String getRFileName2() {
		return rFileName2;
	}
	/**
	 * @param fileName2 설정하려는 rFileName2
	 */
	public void setRFileName2(String fileName2) {
		rFileName2 = fileName2;
	}
	/**
	 * @return sFileName1
	 */
	public String getSFileName1() {
		return sFileName1;
	}
	/**
	 * @param fileName1 설정하려는 sFileName1
	 */
	public void setSFileName1(String fileName1) {
		sFileName1 = fileName1;
	}
	/**
	 * @return sFileName2
	 */
	public String getSFileName2() {
		return sFileName2;
	}
	/**
	 * @param fileName2 설정하려는 sFileName2
	 */
	public void setSFileName2(String fileName2) {
		sFileName2 = fileName2;
	}
	/**
	 * @return profName
	 */
	public String getProfName() {
		return profName;
	}
	/**
	 * @param profName 설정하려는 profName
	 */
	public void setProfName(String profName) {
		this.profName = profName;
	}
	/**
	 * @return filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath 설정하려는 filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the curDate
	 */
	public String getCurDate() {
		return curDate;
	}
	/**
	 * @param curDate the curDate to set
	 */
	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}
	/**
	 * @return Returns the endDate.
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return Returns the lectureGubun.
	 */
	public String getLectureGubun() {
		return lectureGubun;
	}
	/**
	 * @param lectureGubun The lectureGubun to set.
	 */
	public void setLectureGubun(String lectureGubun) {
		this.lectureGubun = lectureGubun;
	}
	/**
	 * @return Returns the startDate.
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getContentsId() {
		return contentsId;
	}
	public void setContentsId(String contentsId) {
		this.contentsId = contentsId;
	}
	public String getContentsName() {
		return contentsName;
	}
	public void setContentsName(String contentsName) {
		this.contentsName = contentsName;
	}
	public String getContentsOrder() {
		return contentsOrder;
	}
	public void setContentsOrder(String contentsOrder) {
		this.contentsOrder = contentsOrder;
	}
	public String getContentsType() {
		return contentsType;
	}
	public void setContentsType(String contentsType) {
		this.contentsType = contentsType;
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
	public int getJoinCount() {
		return joinCount;
	}
	public void setJoinCount(int joinCount) {
		this.joinCount = joinCount;
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
	public String getOpenChk() {
		return openChk;
	}
	public void setOpenChk(String openChk) {
		this.openChk = openChk;
	}
	public String getQuizPass() {
		return quizPass;
	}
	public void setQuizPass(String quizPass) {
		this.quizPass = quizPass;
	}
	public String getQuizYn() {
		return quizYn;
	}
	public void setQuizYn(String quizYn) {
		this.quizYn = quizYn;
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
	public String getServerPath() {
		return serverPath;
	}
	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}
	public int getShowTime() {
		return showTime;
	}
	public void setShowTime(int showTime) {
		this.showTime = showTime;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public int getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getContentsHeight() {
		return contentsHeight;
	}
	public void setContentsHeight(int contentsHeight) {
		this.contentsHeight = contentsHeight;
	}
	public int getContentsWidth() {
		return contentsWidth;
	}
	public void setContentsWidth(int contentsWidth) {
		this.contentsWidth = contentsWidth;
	}
	public String getSizeApp() {
		return sizeApp;
	}
	public void setSizeApp(String sizeApp) {
		this.sizeApp = sizeApp;
	}

	/**
	 * @return Returns the attendance.
	 */
	public String getAttendance() {
		return attendance;
	}
	/**
	 * @param attendance The attendance to set.
	 */
	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	/**
	 * @return the openDate
	 */
	public String getOpenDate() {
		return openDate;
	}
	/**
	 * @param openDate the openDate to set
	 */
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getAsfFilePath() {
		return asfFilePath;
	}
	public void setAsfFilePath(String asfFilePath) {
		this.asfFilePath = asfFilePath;
	}
}

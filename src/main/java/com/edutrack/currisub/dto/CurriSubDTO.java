/**
 * @(#)CurriSubDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.currisub.dto;


/**
 * CURRI_SUB 테이블 Dto 클래스.
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
public class CurriSubDTO {

    protected String 	systemCode;
    protected String 	curriCode;
    protected String 	curriName;
    protected int 		curriYear;
    protected int 		curriTerm;
    protected String 	profId;
    protected int 		price;
    protected int 		credit;
    protected String 	enrollStart;
    protected String 	enrollEnd;
    protected String 	cancelStart;
    protected String 	cancelEnd;
    protected String 	serviceStart;
    protected String 	serviceEnd;
    protected String 	chungEnd;
    protected String 	flagLimit;
    protected int 		limitNum;
    protected int 		completeAverage;
    protected int 		completeCourse;
    protected String 	assessmentEnd;
    protected String 	completeEnd;
    protected String 	completeScore;
    protected String	 regId;
    protected String 	regDate;
    protected String 	modId;
    protected String 	modDate;
    protected String 	bestYn;
    protected String 	sampleContents;
    protected int 		cancelConfigDay;
    protected int 		cancelConfigstudy;
    protected String 	government;
    protected String 	printImg;
    protected String 	supportAdd;
    protected int 		courseCount;
    protected String 	curriType;
    protected int 		hakgiTerm;
    protected String 	scoreGubun;
    protected String 	evalGubun;

    protected String	pareCode1;
    protected String	pareCode2;
    protected String	cateCode;
    protected String	cateName;
    protected String	profName;
    protected int		processStudentCnt;
    protected int		allStudentCnt;
    protected int		passStudentCnt;
    protected int		noPassStudentCnt;
    protected String	connPoint;
    protected String	userId;
    protected String	userName;


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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the allStudentCnt
	 */
	public int getAllStudentCnt() {
		return allStudentCnt;
	}
	/**
	 * @param allStudentCnt the allStudentCnt to set
	 */
	public void setAllStudentCnt(int allStudentCnt) {
		this.allStudentCnt = allStudentCnt;
	}
	/**
	 * @return the cateCode
	 */
	public String getCateCode() {
		return cateCode;
	}
	/**
	 * @param cateCode the cateCode to set
	 */
	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}
	/**
	 * @return the cateName
	 */
	public String getCateName() {
		return cateName;
	}
	/**
	 * @param cateName the cateName to set
	 */
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	/**
	 * @return the connPoint
	 */
	public String getConnPoint() {
		return connPoint;
	}
	/**
	 * @param connPoint the connPoint to set
	 */
	public void setConnPoint(String connPoint) {
		this.connPoint = connPoint;
	}
	/**
	 * @return the noPassStudentCnt
	 */
	public int getNoPassStudentCnt() {
		return noPassStudentCnt;
	}
	/**
	 * @param noPassStudentCnt the noPassStudentCnt to set
	 */
	public void setNoPassStudentCnt(int noPassStudentCnt) {
		this.noPassStudentCnt = noPassStudentCnt;
	}
	/**
	 * @return the pareCode1
	 */
	public String getPareCode1() {
		return pareCode1;
	}
	/**
	 * @param pareCode1 the pareCode1 to set
	 */
	public void setPareCode1(String pareCode1) {
		this.pareCode1 = pareCode1;
	}
	/**
	 * @return the pareCode2
	 */
	public String getPareCode2() {
		return pareCode2;
	}
	/**
	 * @param pareCode2 the pareCode2 to set
	 */
	public void setPareCode2(String pareCode2) {
		this.pareCode2 = pareCode2;
	}
	/**
	 * @return the passStudentCnt
	 */
	public int getPassStudentCnt() {
		return passStudentCnt;
	}
	/**
	 * @param passStudentCnt the passStudentCnt to set
	 */
	public void setPassStudentCnt(int passStudentCnt) {
		this.passStudentCnt = passStudentCnt;
	}
	/**
	 * @return the profName
	 */
	public String getProfName() {
		return profName;
	}
	/**
	 * @param profName the profName to set
	 */
	public void setProfName(String profName) {
		this.profName = profName;
	}
	/**
	 * @return the hakgiTerm
	 */
	public int getHakgiTerm() {
		return hakgiTerm;
	}
	/**
	 * @param hakgiTerm the hakgiTerm to set
	 */
	public void setHakgiTerm(int hakgiTerm) {
		this.hakgiTerm = hakgiTerm;
	}
	public String getCurriType() {
		return curriType;
	}
	public void setCurriType(String curriType) {
		this.curriType = curriType;
	}
	public String getAssessmentEnd() {
		return assessmentEnd;
	}
	public void setAssessmentEnd(String assessmentEnd) {
		this.assessmentEnd = assessmentEnd;
	}
	public String getBestYn() {
		return bestYn;
	}
	public void setBestYn(String bestYn) {
		this.bestYn = bestYn;
	}
	/**
	 * @return Returns the evalGubun.
	 */
	public String getEvalGubun() {
		return evalGubun;
	}
	/**
	 * @param evalGubun The evalGubun to set.
	 */
	public void setEvalGubun(String evalGubun) {
		this.evalGubun = evalGubun;
	}
	/**
	 * @return Returns the scoreGubun.
	 */
	public String getScoreGubun() {
		return scoreGubun;
	}
	/**
	 * @param scoreGubun The scoreGubun to set.
	 */
	public void setScoreGubun(String scoreGubun) {
		this.scoreGubun = scoreGubun;
	}
	public int getCancelConfigDay() {
		return cancelConfigDay;
	}
	public void setCancelConfigDay(int cancelConfigDay) {
		this.cancelConfigDay = cancelConfigDay;
	}
	public int getCancelConfigstudy() {
		return cancelConfigstudy;
	}
	public void setCancelConfigstudy(int cancelConfigstudy) {
		this.cancelConfigstudy = cancelConfigstudy;
	}
	public String getCancelEnd() {
		return cancelEnd;
	}
	public void setCancelEnd(String cancelEnd) {
		this.cancelEnd = cancelEnd;
	}
	public String getCancelStart() {
		return cancelStart;
	}
	public void setCancelStart(String cancelStart) {
		this.cancelStart = cancelStart;
	}
	public String getChungEnd() {
		return chungEnd;
	}
	public void setChungEnd(String chungEnd) {
		this.chungEnd = chungEnd;
	}
	public int getCompleteAverage() {
		return completeAverage;
	}
	public void setCompleteAverage(int completeAverage) {
		this.completeAverage = completeAverage;
	}
	public int getCompleteCourse() {
		return completeCourse;
	}
	public void setCompleteCourse(int completeCourse) {
		this.completeCourse = completeCourse;
	}
	public String getCompleteEnd() {
		return completeEnd;
	}
	public void setCompleteEnd(String completeEnd) {
		this.completeEnd = completeEnd;
	}
	public String getCompleteScore() {
		return completeScore;
	}
	public void setCompleteScore(String completeScore) {
		this.completeScore = completeScore;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getCurriCode() {
		return curriCode;
	}
	public void setCurriCode(String curriCode) {
		this.curriCode = curriCode;
	}
	public String getCurriName() {
		return curriName;
	}
	public void setCurriName(String curriName) {
		this.curriName = curriName;
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
	public String getEnrollEnd() {
		return enrollEnd;
	}
	public void setEnrollEnd(String enrollEnd) {
		this.enrollEnd = enrollEnd;
	}
	public String getEnrollStart() {
		return enrollStart;
	}
	public void setEnrollStart(String enrollStart) {
		this.enrollStart = enrollStart;
	}
	public String getFlagLimit() {
		return flagLimit;
	}
	public void setFlagLimit(String flagLimit) {
		this.flagLimit = flagLimit;
	}
	public String getGovernment() {
		return government;
	}
	public void setGovernment(String government) {
		this.government = government;
	}
	public int getLimitNum() {
		return limitNum;
	}
	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getPrintImg() {
		return printImg;
	}
	public void setPrintImg(String printImg) {
		this.printImg = printImg;
	}
	public String getProfId() {
		return profId;
	}
	public void setProfId(String profId) {
		this.profId = profId;
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
	public String getSampleContents() {
		return sampleContents;
	}
	public void setSampleContents(String sampleContents) {
		this.sampleContents = sampleContents;
	}
	public String getServiceEnd() {
		return serviceEnd;
	}
	public void setServiceEnd(String serviceEnd) {
		this.serviceEnd = serviceEnd;
	}
	public String getServiceStart() {
		return serviceStart;
	}
	public void setServiceStart(String serviceStart) {
		this.serviceStart = serviceStart;
	}
	public String getSupportAdd() {
		return supportAdd;
	}
	public void setSupportAdd(String supportAdd) {
		this.supportAdd = supportAdd;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

    public int getCourseCount() {
		return courseCount;
	}
	public void setCourseCount(int courseCount) {
		this.courseCount = courseCount;
	}
	/**
     * toString
     *
     * @return  String
     */
    public String toString() {
        return  "systemCode="+systemCode+",curriCode="+curriCode+",curriYear="+curriYear+",curriTerm="+curriTerm+",profId="+profId
               +",price="+price+",credit="+credit+",enrollStart="+enrollStart+",enrollEnd="+enrollEnd+",cancelStart="+cancelStart
               +",cancelEnd="+cancelEnd+",serviceStart="+serviceStart+",serviceEnd="+serviceEnd+",chungEnd="+chungEnd+",flagLimit="+flagLimit
               +",limitNum="+limitNum+",completeAverage="+completeAverage+",completeCourse="+completeCourse+",assessmentEnd="+assessmentEnd+",completeEnd="+completeEnd
               +",completeScore="+completeScore+",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate+",bestYn="+bestYn+",government="+government+",printImg="+printImg+",supportAdd="+supportAdd;


    }
	/**
	 * @return the processStudentCnt
	 */
	public int getProcessStudentCnt() {
		return processStudentCnt;
	}
	/**
	 * @param processStudentCnt the processStudentCnt to set
	 */
	public void setProcessStudentCnt(int processStudentCnt) {
		this.processStudentCnt = processStudentCnt;
	}
}

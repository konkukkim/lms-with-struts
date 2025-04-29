/**
 * @(#)CurriSubDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.currisub.dto;


/**
 * CURRI_SUB   Info Dto 클래스.
 *
 ************************************************
 * DATE         AUTHOR      DESCRIPTION
 * ----------------------------------------------
 * 2004. 9. 13.  bschoi       Initial Release
 ************************************************
 *
 * @author      mediopia
 * @version     1.0
 * @since       2004. 9. 13.
 */
public class CurriSubInfoDTO {

    protected String systemCode;
    protected String pareCode1;
    protected String pareCode2;
    protected String cateCode;
    protected String cateStatus;
    protected int cateDepth;
    protected String cateName1;
    protected String cateName2;
    protected String cateName3;
    protected String curriCode;
	protected String curriName;
    protected int curriYear;
    protected int curriTerm;
    protected String profId;
    protected int price;
    protected int credit;
    protected String enrollStart;
	protected String enrollStartYear;
	protected String enrollStartMonth;
	protected String enrollStartDay;
    protected String enrollEnd;
	protected String enrollEndYear;
	protected String enrollEndMonth;
	protected String enrollEndDay;
    protected String cancelStart;
	protected String cancelStartYear;
	protected String cancelStartMonth;
	protected String cancelStartDay;
    protected String cancelEnd;
	protected String cancelEndYear;
	protected String cancelEndMonth;
	protected String cancelEndDay;
    protected String serviceStart;
	protected String serviceStartYear;
	protected String serviceStartMonth;
	protected String serviceStartDay;
    protected String serviceEnd;
	protected String serviceEndYear;
	protected String serviceEndMonth;
	protected String serviceEndDay;
    protected String chungEnd;
	protected String chungEndYear;
	protected String chungEndMonth;
	protected String chungEndDay;
    protected String flagLimit;
    protected int limitNum;
    protected int completeAverage;
    protected int completeCourse;
    protected String assessmentEnd;
	protected String assessmentEndYear;
	protected String assessmentEndMonth;
	protected String assessmentEndDay;
    protected String completeEnd;
	protected String completeEndYear;
	protected String completeEndMonth;
	protected String completeEndDay;
    protected String completeScore;
    protected String regId;
    protected String regDate;
    protected String bestYn;
    protected String sampleContents;
    protected int cancelConfigDay;
    protected int cancelConfigStudy;

	protected String curriInfo;
	protected String curriGoal;
	protected String curriTarget;
	protected String curriEnv;
	protected String assessment;
	protected String beforeInfo;
	protected String learningTime;
	protected String curriProperty2;
	protected String codeSo;
	protected String curriTypeName;
	protected String curriContents;
	protected String curriContentsText;


	protected String userPhoto="";
    protected String photoPath="";
    protected String phoneNumber="";
    protected String career="";
    protected String major="";
    protected String books="";
    protected String intro="";

    protected String government="";
    protected String printImg="";
    protected String supportAdd="";

    protected String curriType="";
    protected int hakgiTerm;

    protected String scoreGubun;
    protected String evalGubun;

	public int getHakgiTerm() {
		return hakgiTerm;
	}
	public void setHakgiTerm(int hakgiTerm) {
		this.hakgiTerm = hakgiTerm;
	}
	public String getCurriType() {
		return curriType;
	}
	public void setCurriType(String curriType) {
		this.curriType = curriType;
	}
	public String getAssessment() {
		return assessment;
	}
	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}
	public String getAssessmentEnd() {
		return assessmentEnd;
	}
	public void setAssessmentEnd(String assessmentEnd) {
		this.assessmentEnd = assessmentEnd;
	}
	public String getAssessmentEndDay() {
		return assessmentEndDay;
	}
	public void setAssessmentEndDay(String assessmentEndDay) {
		this.assessmentEndDay = assessmentEndDay;
	}
	public String getAssessmentEndMonth() {
		return assessmentEndMonth;
	}
	public void setAssessmentEndMonth(String assessmentEndMonth) {
		this.assessmentEndMonth = assessmentEndMonth;
	}
	public String getAssessmentEndYear() {
		return assessmentEndYear;
	}
	public void setAssessmentEndYear(String assessmentEndYear) {
		this.assessmentEndYear = assessmentEndYear;
	}
	public String getBeforeInfo() {
		return beforeInfo;
	}
	public void setBeforeInfo(String beforeInfo) {
		this.beforeInfo = beforeInfo;
	}
	public String getBestYn() {
		return bestYn;
	}
	public void setBestYn(String bestYn) {
		this.bestYn = bestYn;
	}
	public String getBooks() {
		return books;
	}
	public void setBooks(String books) {
		this.books = books;
	}
	public int getCancelConfigDay() {
		return cancelConfigDay;
	}
	public void setCancelConfigDay(int cancelConfigDay) {
		this.cancelConfigDay = cancelConfigDay;
	}
	public int getCancelConfigStudy() {
		return cancelConfigStudy;
	}
	public void setCancelConfigStudy(int cancelConfigStudy) {
		this.cancelConfigStudy = cancelConfigStudy;
	}
	public String getCancelEnd() {
		return cancelEnd;
	}
	public void setCancelEnd(String cancelEnd) {
		this.cancelEnd = cancelEnd;
	}
	public String getCancelEndDay() {
		return cancelEndDay;
	}
	public void setCancelEndDay(String cancelEndDay) {
		this.cancelEndDay = cancelEndDay;
	}
	public String getCancelEndMonth() {
		return cancelEndMonth;
	}
	public void setCancelEndMonth(String cancelEndMonth) {
		this.cancelEndMonth = cancelEndMonth;
	}
	public String getCancelEndYear() {
		return cancelEndYear;
	}
	public void setCancelEndYear(String cancelEndYear) {
		this.cancelEndYear = cancelEndYear;
	}
	public String getCancelStart() {
		return cancelStart;
	}
	public void setCancelStart(String cancelStart) {
		this.cancelStart = cancelStart;
	}
	public String getCancelStartDay() {
		return cancelStartDay;
	}
	public void setCancelStartDay(String cancelStartDay) {
		this.cancelStartDay = cancelStartDay;
	}
	public String getCancelStartMonth() {
		return cancelStartMonth;
	}
	public void setCancelStartMonth(String cancelStartMonth) {
		this.cancelStartMonth = cancelStartMonth;
	}
	public String getCancelStartYear() {
		return cancelStartYear;
	}
	public void setCancelStartYear(String cancelStartYear) {
		this.cancelStartYear = cancelStartYear;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getCateCode() {
		return cateCode;
	}
	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}
	public int getCateDepth() {
		return cateDepth;
	}
	public void setCateDepth(int cateDepth) {
		this.cateDepth = cateDepth;
	}
	public String getCateStatus() {
		return cateStatus;
	}
	public void setCateStatus(String cateStatus) {
		this.cateStatus = cateStatus;
	}
	public String getCateName1() {
		return cateName1;
	}
	public void setCateName1(String cateName1) {
		this.cateName1 = cateName1;
	}
	public String getCateName2() {
		return cateName2;
	}
	public void setCateName2(String cateName2) {
		this.cateName2 = cateName2;
	}
	public String getCateName3() {
		return cateName3;
	}
	public void setCateName3(String cateName3) {
		this.cateName3 = cateName3;
	}
	public String getChungEnd() {
		return chungEnd;
	}
	public void setChungEnd(String chungEnd) {
		this.chungEnd = chungEnd;
	}
	public String getChungEndDay() {
		return chungEndDay;
	}
	public void setChungEndDay(String chungEndDay) {
		this.chungEndDay = chungEndDay;
	}
	public String getChungEndMonth() {
		return chungEndMonth;
	}
	public void setChungEndMonth(String chungEndMonth) {
		this.chungEndMonth = chungEndMonth;
	}
	public String getChungEndYear() {
		return chungEndYear;
	}
	public void setChungEndYear(String chungEndYear) {
		this.chungEndYear = chungEndYear;
	}
	public String getCodeSo() {
		return codeSo;
	}
	public void setCodeSo(String codeSo) {
		this.codeSo = codeSo;
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
	public String getCompleteEndDay() {
		return completeEndDay;
	}
	public void setCompleteEndDay(String completeEndDay) {
		this.completeEndDay = completeEndDay;
	}
	public String getCompleteEndMonth() {
		return completeEndMonth;
	}
	public void setCompleteEndMonth(String completeEndMonth) {
		this.completeEndMonth = completeEndMonth;
	}
	public String getCompleteEndYear() {
		return completeEndYear;
	}
	public void setCompleteEndYear(String completeEndYear) {
		this.completeEndYear = completeEndYear;
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
	public String getCurriContents() {
		return curriContents;
	}
	public void setCurriContents(String curriContents) {
		this.curriContents = curriContents;
	}
	public String getCurriContentsText() {
		return curriContentsText;
	}
	public void setCurriContentsText(String curriContentsText) {
		this.curriContentsText = curriContentsText;
	}
	public String getCurriEnv() {
		return curriEnv;
	}
	public void setCurriEnv(String curriEnv) {
		this.curriEnv = curriEnv;
	}
	public String getCurriGoal() {
		return curriGoal;
	}
	public void setCurriGoal(String curriGoal) {
		this.curriGoal = curriGoal;
	}
	public String getCurriInfo() {
		return curriInfo;
	}
	public void setCurriInfo(String curriInfo) {
		this.curriInfo = curriInfo;
	}
	public String getCurriName() {
		return curriName;
	}
	public void setCurriName(String curriName) {
		this.curriName = curriName;
	}
	public String getCurriProperty2() {
		return curriProperty2;
	}
	public void setCurriProperty2(String curriProperty2) {
		this.curriProperty2 = curriProperty2;
	}
	public String getCurriTarget() {
		return curriTarget;
	}
	public void setCurriTarget(String curriTarget) {
		this.curriTarget = curriTarget;
	}
	public int getCurriTerm() {
		return curriTerm;
	}
	public void setCurriTerm(int curriTerm) {
		this.curriTerm = curriTerm;
	}
	public String getCurriTypeName() {
		return curriTypeName;
	}
	public void setCurriTypeName(String curriTypeName) {
		this.curriTypeName = curriTypeName;
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
	public String getEnrollEndDay() {
		return enrollEndDay;
	}
	public void setEnrollEndDay(String enrollEndDay) {
		this.enrollEndDay = enrollEndDay;
	}
	public String getEnrollEndMonth() {
		return enrollEndMonth;
	}
	public void setEnrollEndMonth(String enrollEndMonth) {
		this.enrollEndMonth = enrollEndMonth;
	}
	public String getEnrollEndYear() {
		return enrollEndYear;
	}
	public void setEnrollEndYear(String enrollEndYear) {
		this.enrollEndYear = enrollEndYear;
	}
	public String getEnrollStart() {
		return enrollStart;
	}
	public void setEnrollStart(String enrollStart) {
		this.enrollStart = enrollStart;
	}
	public String getEnrollStartDay() {
		return enrollStartDay;
	}
	public void setEnrollStartDay(String enrollStartDay) {
		this.enrollStartDay = enrollStartDay;
	}
	public String getEnrollStartMonth() {
		return enrollStartMonth;
	}
	public void setEnrollStartMonth(String enrollStartMonth) {
		this.enrollStartMonth = enrollStartMonth;
	}
	public String getEnrollStartYear() {
		return enrollStartYear;
	}
	public void setEnrollStartYear(String enrollStartYear) {
		this.enrollStartYear = enrollStartYear;
	}
	public String getFlagLimit() {
		return flagLimit;
	}
	public void setFlagLimit(String flagLimit) {
		this.flagLimit = flagLimit;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getLearningTime() {
		return learningTime;
	}
	public void setLearningTime(String learningTime) {
		this.learningTime = learningTime;
	}
	public int getLimitNum() {
		return limitNum;
	}
	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getPareCode1() {
		return pareCode1;
	}
	public void setPareCode1(String pareCode1) {
		this.pareCode1 = pareCode1;
	}
	public String getPareCode2() {
		return pareCode2;
	}
	public void setPareCode2(String pareCode2) {
		this.pareCode2 = pareCode2;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
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
	public String getServiceEndDay() {
		return serviceEndDay;
	}
	public void setServiceEndDay(String serviceEndDay) {
		this.serviceEndDay = serviceEndDay;
	}
	public String getServiceEndMonth() {
		return serviceEndMonth;
	}
	public void setServiceEndMonth(String serviceEndMonth) {
		this.serviceEndMonth = serviceEndMonth;
	}
	public String getServiceEndYear() {
		return serviceEndYear;
	}
	public void setServiceEndYear(String serviceEndYear) {
		this.serviceEndYear = serviceEndYear;
	}
	public String getServiceStart() {
		return serviceStart;
	}
	public void setServiceStart(String serviceStart) {
		this.serviceStart = serviceStart;
	}
	public String getServiceStartDay() {
		return serviceStartDay;
	}
	public void setServiceStartDay(String serviceStartDay) {
		this.serviceStartDay = serviceStartDay;
	}
	public String getServiceStartMonth() {
		return serviceStartMonth;
	}
	public void setServiceStartMonth(String serviceStartMonth) {
		this.serviceStartMonth = serviceStartMonth;
	}
	public String getServiceStartYear() {
		return serviceStartYear;
	}
	public void setServiceStartYear(String serviceStartYear) {
		this.serviceStartYear = serviceStartYear;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public String getUserPhoto() {
		return userPhoto;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getGovernment() {
		return government;
	}
	public void setGovernment(String government) {
		this.government = government;
	}
	public String getPrintImg() {
		return printImg;
	}
	public void setPrintImg(String printImg) {
		this.printImg = printImg;
	}
	public String getSupportAdd() {
		return supportAdd;
	}
	public void setSupportAdd(String supportAdd) {
		this.supportAdd = supportAdd;
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
 }

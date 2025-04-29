/**
 * @(#)StudentDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.curristudent.dto;


/**
 * STUDENT 테이블 Dto 클래스.
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
public class StudentDTO {

    protected String systemCode;
    protected String userId;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected int enrollNo;
    protected String enrollStats;
    protected String servicestartDate;
    protected String serviceendDate;
    protected String chungendDate;
    protected int teamNo;
    protected String enrollDate;
    protected String cancelDate;
    protected String completeDate;
    protected String completeNo;
    protected String getCredit;
    protected String firstCon;
    protected String lastCon;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    protected int paymentIdx;

    protected String curriName;
    protected String userName;
    protected String curriType;
    protected String email;

    //나의 학습실, 진행중인 과정 리스트를 위한 변수 추가[2007.8.28]
    protected int reportCnt;
    protected int examCnt;
    protected int forumCnt;
    protected int contentsCnt;
    protected int showContentsCnt;
    protected int showTime;
    protected int totalTime;
    protected double curriPercent;
    protected String cateName;
    protected String serviceStart;
    protected String curriInfo;

    //진도현황 리스트
    protected int allAttend;
    protected int attendCnt;
    protected int allReportCnt;
    protected int allExamCnt;
    protected int allForumCnt;

    //수강생현황 리스트(위탁관리)
    protected String grade;
    protected int credit;
    protected String scoreGubun;
    protected String completeYn;
    protected double scoreExam;
    protected double scoreReport;
    protected double scoreAttend;
    protected double scoreForum;
    protected double scoreEtc1;
    protected double scoreEtc2;
    protected double totalScore;
    
    protected String regMode;
    protected int openCnt;
    protected String chungendYn;
    
    // 오프라인과목 출석, 동영상 시간
    protected int offTotalTime;	//
    protected int offShowTime;
    protected int offContentsCnt;
    protected int offAttendCnt;



	/**
	 * @return chungendYn
	 */
	public String getChungendYn() {
		return chungendYn;
	}
	/**
	 * @param chungendYn 설정하려는 chungendYn
	 */
	public void setChungendYn(String chungendYn) {
		this.chungendYn = chungendYn;
	}
	/**
	 * @return openCnt
	 */
	public int getOpenCnt() {
		return openCnt;
	}
	/**
	 * @param openCnt 설정하려는 openCnt
	 */
	public void setOpenCnt(int openCnt) {
		this.openCnt = openCnt;
	}
	/**
	 * @return offAttendCnt
	 */
	public int getOffAttendCnt() {
		return offAttendCnt;
	}
	/**
	 * @param offAttendCnt 설정하려는 offAttendCnt
	 */
	public void setOffAttendCnt(int offAttendCnt) {
		this.offAttendCnt = offAttendCnt;
	}
	/**
	 * @return offContentsCnt
	 */
	public int getOffContentsCnt() {
		return offContentsCnt;
	}
	/**
	 * @param offContentsCnt 설정하려는 offContentsCnt
	 */
	public void setOffContentsCnt(int offContentsCnt) {
		this.offContentsCnt = offContentsCnt;
	}
	/**
	 * @return offShowTime
	 */
	public int getOffShowTime() {
		return offShowTime;
	}
	/**
	 * @param offShowTime 설정하려는 offShowTime
	 */
	public void setOffShowTime(int offShowTime) {
		this.offShowTime = offShowTime;
	}
	/**
	 * @return offTotalTime
	 */
	public int getOffTotalTime() {
		return offTotalTime;
	}
	/**
	 * @param offTotalTime 설정하려는 offTotalTime
	 */
	public void setOffTotalTime(int offTotalTime) {
		this.offTotalTime = offTotalTime;
	}
	/**
	 * @return regMode
	 */
	public String getRegMode() {
		return regMode;
	}
	/**
	 * @param regMode 설정하려는 regMode
	 */
	public void setRegMode(String regMode) {
		this.regMode = regMode;
	}
	/**
	 * @return the allAttend
	 */
	public int getAllAttend() {
		return allAttend;
	}
	/**
	 * @param allAttend the allAttend to set
	 */
	public void setAllAttend(int allAttend) {
		this.allAttend = allAttend;
	}
	/**
	 * @return the allExamCnt
	 */
	public int getAllExamCnt() {
		return allExamCnt;
	}
	/**
	 * @param allExamCnt the allExamCnt to set
	 */
	public void setAllExamCnt(int allExamCnt) {
		this.allExamCnt = allExamCnt;
	}
	/**
	 * @return the allForumCnt
	 */
	public int getAllForumCnt() {
		return allForumCnt;
	}
	/**
	 * @param allForumCnt the allForumCnt to set
	 */
	public void setAllForumCnt(int allForumCnt) {
		this.allForumCnt = allForumCnt;
	}
	/**
	 * @return the allReportCnt
	 */
	public int getAllReportCnt() {
		return allReportCnt;
	}
	/**
	 * @param allReportCnt the allReportCnt to set
	 */
	public void setAllReportCnt(int allReportCnt) {
		this.allReportCnt = allReportCnt;
	}
	/**
	 * @return the attendCnt
	 */
	public int getAttendCnt() {
		return attendCnt;
	}
	/**
	 * @param attendCnt the attendCnt to set
	 */
	public void setAttendCnt(int attendCnt) {
		this.attendCnt = attendCnt;
	}
	/**
	 * @return the curriPercent
	 */
	public double getCurriPercent() {
		return curriPercent;
	}
	/**
	 * @param curriPercent the curriPercent to set
	 */
	public void setCurriPercent(double curriPercent) {
		this.curriPercent = curriPercent;
	}
	/**
	 * @return the curriInfo
	 */
	public String getCurriInfo() {
		return curriInfo;
	}
	/**
	 * @param curriInfo the curriInfo to set
	 */
	public void setCurriInfo(String curriInfo) {
		this.curriInfo = curriInfo;
	}
	/**
	 * @return the serviceStart
	 */
	public String getServiceStart() {
		return serviceStart;
	}
	/**
	 * @param serviceStart the serviceStart to set
	 */
	public void setServiceStart(String serviceStart) {
		this.serviceStart = serviceStart;
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
	 * @return the contentsCnt
	 */
	public int getContentsCnt() {
		return contentsCnt;
	}
	/**
	 * @param contentsCnt the contentsCnt to set
	 */
	public void setContentsCnt(int contentsCnt) {
		this.contentsCnt = contentsCnt;
	}
	/**
	 * @return the examCnt
	 */
	public int getExamCnt() {
		return examCnt;
	}
	/**
	 * @param examCnt the examCnt to set
	 */
	public void setExamCnt(int examCnt) {
		this.examCnt = examCnt;
	}
	/**
	 * @return the forumCnt
	 */
	public int getForumCnt() {
		return forumCnt;
	}
	/**
	 * @param forumCnt the forumCnt to set
	 */
	public void setForumCnt(int forumCnt) {
		this.forumCnt = forumCnt;
	}
	/**
	 * @return the reportCnt
	 */
	public int getReportCnt() {
		return reportCnt;
	}
	/**
	 * @param reportCnt the reportCnt to set
	 */
	public void setReportCnt(int reportCnt) {
		this.reportCnt = reportCnt;
	}
	/**
	 * @return the showContentsCnt
	 */
	public int getShowContentsCnt() {
		return showContentsCnt;
	}
	/**
	 * @param showContentsCnt the showContentsCnt to set
	 */
	public void setShowContentsCnt(int showContentsCnt) {
		this.showContentsCnt = showContentsCnt;
	}
	/**
	 * @return the showTime
	 */
	public int getShowTime() {
		return showTime;
	}
	/**
	 * @param showTime the showTime to set
	 */
	public void setShowTime(int showTime) {
		this.showTime = showTime;
	}
	/**
	 * @return the totalTime
	 */
	public int getTotalTime() {
		return totalTime;
	}
	/**
	 * @param totalTime the totalTime to set
	 */
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	/**
	 * @return Returns the paymentIdx.
	 */
	public int getPaymentIdx() {
		return paymentIdx;
	}
	/**
	 * @param paymentIdx The paymentIdx to set.
	 */
	public void setPaymentIdx(int paymentIdx) {
		this.paymentIdx = paymentIdx;
	}
    /**
     * get systemCode.
     *
     * @return      systemCode
     */
    public String getSystemCode() {
        return  this.systemCode;
    }

    /**
     * set systemCode.
     *
     * @param       systemCode
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * get userId.
     *
     * @return      userId
     */
    public String getUserId() {
        return  this.userId;
    }

    /**
     * set userName.
     *
     * @param       userName
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * get userName.
     *
     * @return      userName
     */
    public String getUserName() {
        return  this.userName;
    }

    /**
     * set userId.
     *
     * @param       userId
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * get curriCode.
     *
     * @return      curriCode
     */
    public String getCurriCode() {
        return  this.curriCode;
    }

    /**
     * set curriCode.
     *
     * @param       curriCode
     */
    public void setCurriCode(String curriCode) {
        this.curriCode = curriCode;
    }

    /**
     * get curriName.
     *
     * @return      curriName
     */
    public String getCurriName() {
        return  this.curriName;
    }

    /**
     * set curriName.
     *
     * @param       curriName
     */
    public void setCurriName(String curriName) {
        this.curriName = curriName;
    }

    /**
     * get curriYear.
     *
     * @return      curriYear
     */
    public int getCurriYear() {
        return  this.curriYear;
    }

    /**
     * set curriYear.
     *
     * @param       curriYear
     */
    public void setCurriYear(int curriYear) {
        this.curriYear = curriYear;
    }

    /**
     * get curriTerm.
     *
     * @return      curriTerm
     */
    public int getCurriTerm() {
        return  this.curriTerm;
    }

    /**
     * set curriTerm.
     *
     * @param       curriTerm
     */
    public void setCurriTerm(int curriTerm) {
        this.curriTerm = curriTerm;
    }

    /**
     * get enrollNo.
     *
     * @return      enrollNo
     */
    public int getEnrollNo() {
        return  this.enrollNo;
    }

    /**
     * set enrollNo.
     *
     * @param       enrollNo
     */
    public void setEnrollNo(int enrollNo) {
        this.enrollNo = enrollNo;
    }

    /**
     * get enrollStats.
     *
     * @return      enrollStats
     */
    public String getEnrollStats() {
        return  this.enrollStats;
    }

    /**
     * set enrollStats.
     *
     * @param       enrollStats
     */
    public void setEnrollStats(String enrollStats) {
        this.enrollStats = enrollStats;
    }

    /**
     * get servicestartDate.
     *
     * @return      servicestartDate
     */
    public String getServicestartDate() {
        return  this.servicestartDate;
    }

    /**
     * set servicestartDate.
     *
     * @param       servicestartDate
     */
    public void setServicestartDate(String servicestartDate) {
        this.servicestartDate = servicestartDate;
    }

    /**
     * get serviceendDate.
     *
     * @return      serviceendDate
     */
    public String getServiceendDate() {
        return  this.serviceendDate;
    }

    /**
     * set serviceendDate.
     *
     * @param       serviceendDate
     */
    public void setServiceendDate(String serviceendDate) {
        this.serviceendDate = serviceendDate;
    }

    /**
     * get chungendDate.
     *
     * @return      chungendDate
     */
    public String getChungendDate() {
        return  this.chungendDate;
    }

    /**
     * set chungendDate.
     *
     * @param       chungendDate
     */
    public void setChungendDate(String chungendDate) {
        this.chungendDate = chungendDate;
    }

    /**
     * get teamNo.
     *
     * @return      teamNo
     */
    public int getTeamNo() {
        return  this.teamNo;
    }

    /**
     * set teamNo.
     *
     * @param       teamNo
     */
    public void setTeamNo(int teamNo) {
        this.teamNo = teamNo;
    }

    /**
     * get enrollDate.
     *
     * @return      enrollDate
     */
    public String getEnrollDate() {
        return  this.enrollDate;
    }

    /**
     * set enrollDate.
     *
     * @param       enrollDate
     */
    public void setEnrollDate(String enrollDate) {
        this.enrollDate = enrollDate;
    }

    /**
     * get cancelDate.
     *
     * @return      cancelDate
     */
    public String getCancelDate() {
        return  this.cancelDate;
    }

    /**
     * set cancelDate.
     *
     * @param       cancelDate
     */
    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    /**
     * get completeDate.
     *
     * @return      completeDate
     */
    public String getCompleteDate() {
        return  this.completeDate;
    }

    /**
     * set completeDate.
     *
     * @param       completeDate
     */
    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    /**
     * get completeNo.
     *
     * @return      completeNo
     */
    public String getCompleteNo() {
        return  this.completeNo;
    }

    /**
     * set completeNo.
     *
     * @param       completeNo
     */
    public void setCompleteNo(String completeNo) {
        this.completeNo = completeNo;
    }

    /**
     * get getCredit.
     *
     * @return      getCredit
     */
    public String getGetCredit() {
        return  this.getCredit;
    }

    /**
     * set getCredit.
     *
     * @param       getCredit
     */
    public void setGetCredit(String getCredit) {
        this.getCredit = getCredit;
    }

    /**
     * get firstCon.
     *
     * @return      firstCon
     */
    public String getFirstCon() {
        return  this.firstCon;
    }

    /**
     * set firstCon.
     *
     * @param       firstCon
     */
    public void setFirstCon(String firstCon) {
        this.firstCon = firstCon;
    }

    /**
     * get lastCon.
     *
     * @return      lastCon
     */
    public String getLastCon() {
        return  this.lastCon;
    }

    /**
     * set lastCon.
     *
     * @param       lastCon
     */
    public void setLastCon(String lastCon) {
        this.lastCon = lastCon;
    }

    /**
     * get regId.
     *
     * @return      regId
     */
    public String getRegId() {
        return  this.regId;
    }

    /**
     * set regId.
     *
     * @param       regId
     */
    public void setRegId(String regId) {
        this.regId = regId;
    }

    /**
     * get regDate.
     *
     * @return      regDate
     */
    public String getRegDate() {
        return  this.regDate;
    }

    /**
     * set regDate.
     *
     * @param       regDate
     */
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    /**
     * get modId.
     *
     * @return      modId
     */
    public String getModId() {
        return  this.modId;
    }

    /**
     * set modId.
     *
     * @param       modId
     */
    public void setModId(String modId) {
        this.modId = modId;
    }

    /**
     * get modDate.
     *
     * @return      modDate
     */
    public String getModDate() {
        return  this.modDate;
    }

    /**
     * set modDate.
     *
     * @param       modDate
     */
    public void setModDate(String modDate) {
        this.modDate = modDate;
    }

    /**
	 * @return Returns the curriType.
	 */
	public String getCurriType() {
		return curriType;
	}
	/**
	 * @param curriType The curriType to set.
	 */
	public void setCurriType(String curriType) {
		this.curriType = curriType;
	}

    /**
     * toString
     *
     * @return  String
     */
    public String toString() {
        return  "systemCode="+systemCode+",userId="+userId+",curriCode="+curriCode+",curriYear="+curriYear+",curriTerm="+curriTerm
               +",enrollNo="+enrollNo+",enrollStats="+enrollStats+",servicestartDate="+servicestartDate+",serviceendDate="+serviceendDate+",chungendDate="+chungendDate
               +",teamNo="+teamNo+",enrollDate="+enrollDate+",cancelDate="+cancelDate+",completeDate="+completeDate+",completeNo="+completeNo
               +",getCredit="+getCredit+",firstCon="+firstCon+",lastCon="+lastCon+",regId="+regId+",regDate="+regDate
               +",modId="+modId+",modDate="+modDate;
    }



	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the completeYn
	 */
	public String getCompleteYn() {
		return completeYn;
	}
	/**
	 * @param completeYn the completeYn to set
	 */
	public void setCompleteYn(String completeYn) {
		this.completeYn = completeYn;
	}
	/**
	 * @return the credit
	 */
	public int getCredit() {
		return credit;
	}
	/**
	 * @param credit the credit to set
	 */
	public void setCredit(int credit) {
		this.credit = credit;
	}
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	/**
	 * @return the scoreAttend
	 */
	public double getScoreAttend() {
		return scoreAttend;
	}
	/**
	 * @param scoreAttend the scoreAttend to set
	 */
	public void setScoreAttend(double scoreAttend) {
		this.scoreAttend = scoreAttend;
	}
	/**
	 * @return the scoreEtc1
	 */
	public double getScoreEtc1() {
		return scoreEtc1;
	}
	/**
	 * @param scoreEtc1 the scoreEtc1 to set
	 */
	public void setScoreEtc1(double scoreEtc1) {
		this.scoreEtc1 = scoreEtc1;
	}
	/**
	 * @return the scoreEtc2
	 */
	public double getScoreEtc2() {
		return scoreEtc2;
	}
	/**
	 * @param scoreEtc2 the scoreEtc2 to set
	 */
	public void setScoreEtc2(double scoreEtc2) {
		this.scoreEtc2 = scoreEtc2;
	}
	/**
	 * @return the scoreExam
	 */
	public double getScoreExam() {
		return scoreExam;
	}
	/**
	 * @param scoreExam the scoreExam to set
	 */
	public void setScoreExam(double scoreExam) {
		this.scoreExam = scoreExam;
	}
	/**
	 * @return the scoreForum
	 */
	public double getScoreForum() {
		return scoreForum;
	}
	/**
	 * @param scoreForum the scoreForum to set
	 */
	public void setScoreForum(double scoreForum) {
		this.scoreForum = scoreForum;
	}
	/**
	 * @return the scoreGubun
	 */
	public String getScoreGubun() {
		return scoreGubun;
	}
	/**
	 * @param scoreGubun the scoreGubun to set
	 */
	public void setScoreGubun(String scoreGubun) {
		this.scoreGubun = scoreGubun;
	}
	/**
	 * @return the scoreReport
	 */
	public double getScoreReport() {
		return scoreReport;
	}
	/**
	 * @param scoreReport the scoreReport to set
	 */
	public void setScoreReport(double scoreReport) {
		this.scoreReport = scoreReport;
	}
	/**
	 * @return the totalScore
	 */
	public double getTotalScore() {
		return totalScore;
	}
	/**
	 * @param totalScore the totalScore to set
	 */
	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}
    }

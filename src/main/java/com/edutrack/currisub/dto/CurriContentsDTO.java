/**
 * @(#)CurriContentsDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.currisub.dto;


/**
 * CURRI_CONTENTS 테이블 Dto 클래스.
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
public class CurriContentsDTO {

    protected String systemCode = "";
    protected String curriCode= "";
    protected int curriYear = 0;
    protected int curriTerm = 0;
    protected String courseId= "";
    protected String contentsId= "";
    protected String contentsOrder= "";
    protected String contentsName= "";
    protected String filePath= "";
    protected String asfFilePath= "";
    protected String serverPath= "";
    protected String contentsType= "";
    protected int quizCount = 0;
    protected int quizPoint = 0;
    protected int showTime = 0;
    protected String preRequisite= "";
    protected String preType= "";
    protected String timeLimitAction= "";
    protected String dataFromLms= "";
    protected String masteryScore= "";
    protected String maxTimeAllowed= "";
    protected String regId= "";
    protected String regDate= "";
    protected String modId= "";
    protected String modDate= "";

    /** 추가 사항 **/
    protected int allContentsCnt = 0;
    protected int contentsCnt = 0;
    protected int totalTime = 0;
    protected int seqNo = 0;
    protected String courseName = "";
    protected int contentsWidth;
    protected int contentsHeight;
    protected String sizeApp;
    protected double processRate;

    // 컨텐츠 등록/수정/삭제 관리를 위한 필드 sangsang 2007.05.18
    protected String parentContentsName;
    protected String regContentsType;
    protected String workMode;

    // offline lesson 일경우...
    protected String lectureGubunCd="";
    protected String lectureGubun="";
    protected String startDate="";
    protected String endDate="";
    protected int totalOfflineCnt = 0;;
    protected int allAttendCnt = 0;
    protected int attendLateCnt = 0;
    protected int noAttendCnt = 0;
    
    protected String curriInfo = "";
    protected String profName = "";
    protected int credit = 0;
    protected String curriName = "";

    
	/**
	 * @return curriName
	 */
	public String getCurriName() {
		return curriName;
	}

	/**
	 * @param curriName 설정하려는 curriName
	 */
	public void setCurriName(String curriName) {
		this.curriName = curriName;
	}

	/**
	 * @return credit
	 */
	public int getCredit() {
		return credit;
	}

	/**
	 * @param credit 설정하려는 credit
	 */
	public void setCredit(int credit) {
		this.credit = credit;
	}

	/**
	 * @return curriInfo
	 */
	public String getCurriInfo() {
		return curriInfo;
	}

	/**
	 * @param curriInfo 설정하려는 curriInfo
	 */
	public void setCurriInfo(String curriInfo) {
		this.curriInfo = curriInfo;
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
	 * @return the allAttendCnt
	 */
	public int getAllAttendCnt() {
		return allAttendCnt;
	}

	/**
	 * @param allAttendCnt the allAttendCnt to set
	 */
	public void setAllAttendCnt(int allAttendCnt) {
		this.allAttendCnt = allAttendCnt;
	}

	/**
	 * @return the attendLateCnt
	 */
	public int getAttendLateCnt() {
		return attendLateCnt;
	}

	/**
	 * @param attendLateCnt the attendLateCnt to set
	 */
	public void setAttendLateCnt(int attendLateCnt) {
		this.attendLateCnt = attendLateCnt;
	}

	/**
	 * @return the noAttendCnt
	 */
	public int getNoAttendCnt() {
		return noAttendCnt;
	}

	/**
	 * @param noAttendCnt the noAttendCnt to set
	 */
	public void setNoAttendCnt(int noAttendCnt) {
		this.noAttendCnt = noAttendCnt;
	}

	/**
	 * @return the totalOfflineCnt
	 */
	public int getTotalOfflineCnt() {
		return totalOfflineCnt;
	}

	/**
	 * @param totalOfflineCnt the totalOfflineCnt to set
	 */
	public void setTotalOfflineCnt(int totalOfflineCnt) {
		this.totalOfflineCnt = totalOfflineCnt;
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
     * get courseId.
     *
     * @return      courseId
     */
    public String getCourseId() {
        return  this.courseId;
    }

    /**
     * set courseId.
     *
     * @param       courseId
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * get contentsId.
     *
     * @return      contentsId
     */
    public String getContentsId() {
        return  this.contentsId;
    }

    /**
     * set contentsId.
     *
     * @param       contentsId
     */
    public void setContentsId(String contentsId) {
        this.contentsId = contentsId;
    }

    /**
     * get contentsOrder.
     *
     * @return      contentsOrder
     */
    public String getContentsOrder() {
        return  this.contentsOrder;
    }

    /**
     * set contentsOrder.
     *
     * @param       contentsOrder
     */
    public void setContentsOrder(String contentsOrder) {
        this.contentsOrder = contentsOrder;
    }

    /**
     * get contentsName.
     *
     * @return      contentsName
     */
    public String getContentsName() {
        return  this.contentsName;
    }

    /**
     * set contentsName.
     *
     * @param       contentsName
     */
    public void setContentsName(String contentsName) {
        this.contentsName = contentsName;
    }

    /**
     * get filePath.
     *
     * @return      filePath
     */
    public String getFilePath() {
        return  this.filePath;
    }

    /**
     * set filePath.
     *
     * @param       filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * get serverPath.
     *
     * @return      serverPath
     */
    public String getServerPath() {
        return  this.serverPath;
    }

    /**
     * set serverPath.
     *
     * @param       serverPath
     */
    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    /**
     * get contentsType.
     *
     * @return      contentsType
     */
    public String getContentsType() {
        return  this.contentsType;
    }

    /**
     * set contentsType.
     *
     * @param       contentsType
     */
    public void setContentsType(String contentsType) {
        this.contentsType = contentsType;
    }

    /**
     * get quizCount.
     *
     * @return      quizCount
     */
    public int getQuizCount() {
        return  this.quizCount;
    }

    /**
     * set quizCount.
     *
     * @param       quizCount
     */
    public void setQuizCount(int quizCount) {
        this.quizCount = quizCount;
    }

    /**
     * get quizPoint.
     *
     * @return      quizPoint
     */
    public int getQuizPoint() {
        return  this.quizPoint;
    }

    /**
     * set quizPoint.
     *
     * @param       quizPoint
     */
    public void setQuizPoint(int quizPoint) {
        this.quizPoint = quizPoint;
    }

    /**
     * get showTime.
     *
     * @return      showTime
     */
    public int getShowTime() {
        return  this.showTime;
    }

    /**
     * set showTime.
     *
     * @param       showTime
     */
    public void setShowTime(int showTime) {
        this.showTime = showTime;
    }

    /**
     * get preRequisite
     * @return	preRequisite
     */
    public String getPreRequisite() {
        return  this.preRequisite;
    }
    /**
     * set preRequisite
     * @param preRequisite
     */
    public void setPreRequisite(String preRequisite) {
        this.preRequisite = preRequisite;
    }
    /**
     * get	preType
     * @return	preType
     */
    public String getPreType() {
        return  this.preType;
    }
    /**
     * set preType
     * @param preType
     */
    public void setPreType(String preType) {
        this.preType = preType;
    }
    /**
     * get timeLimitAction
     * @return	timeLimitAction
     */
    public String getTimeLimitAction() {
        return  this.timeLimitAction;
    }
    /**
     * set	timeLimitAction
     * @param timeLimitAction
     */
    public void setTimeLimitAction(String timeLimitAction) {
        this.timeLimitAction = timeLimitAction;
    }
    /**
     * get 	dataFromLms
     * @return	dataFromLms
     */
    public String getDataFromLms() {
        return  this.dataFromLms;
    }
    /**
     * set dataFromLms
     * @param dataFromLms
     */
    public void setDataFromLms(String dataFromLms) {
        this.dataFromLms = dataFromLms;
    }
    /**
     * get	masteryScore
     * @return	masteryScore
     */
    public String getMasteryScore() {
        return  this.masteryScore;
    }
    /**
     * set 	masteryScore
     * @param masteryScore
     */
    public void setMasteryScore(String masteryScore) {
        this.masteryScore = masteryScore;
    }
    /**
     * get	maxTimeAllowed
     * @return	maxTimeAllowed
     */
    public String getMaxTimeAllowed() {
        return  this.maxTimeAllowed;
    }
    /**
     * set	maxTimeAllowed
     * @param maxTimeAllowed
     */
    public void setMaxTimeAllowed(String maxTimeAllowed) {
        this.maxTimeAllowed = maxTimeAllowed;
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
	 * @return Returns the allContentsCnt.
	 */
	public int getAllContentsCnt() {
		return allContentsCnt;
	}
	/**
	 * @param allContentsCnt The allContentsCnt to set.
	 */
	public void setAllContentsCnt(int allContentsCnt) {
		this.allContentsCnt = allContentsCnt;
	}
	/**
	 * @return Returns the contentsCnt.
	 */
	public int getContentsCnt() {
		return contentsCnt;
	}
	/**
	 * @param contentsCnt The contentsCnt to set.
	 */
	public void setContentsCnt(int contentsCnt) {
		this.contentsCnt = contentsCnt;
	}
	/**
	 * @return Returns the totalTime.
	 */
	public int getTotalTime() {
		return totalTime;
	}
	/**
	 * @param totalTime The totalTime to set.
	 */
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	/**
	 * @return Returns the seqNo.
	 */
	public int getSeqNo() {
		return seqNo;
	}
	/**
	 * @param seqNo The seqNo to set.
	 */
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	/**
	 * @return Returns the courseName.
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * @param courseName The courseName to set.
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
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

	public String getParentContentsName() {
		return parentContentsName;
	}

	public void setParentContentsName(String parentContentsName) {
		this.parentContentsName = parentContentsName;
	}

	public String getRegContentsType() {
		return regContentsType;
	}

	public void setRegContentsType(String regContentsType) {
		this.regContentsType = regContentsType;
	}

	public String getWorkMode() {
		return workMode;
	}

	public void setWorkMode(String workMode) {
		this.workMode = workMode;
	}

	/**
     * toString
     *
     * @return  String
     */
    public String toString() {
        return  "systemCode="+systemCode+",curriCode="+curriCode+",curriYear="+curriYear+",curriTerm="+curriTerm+",courseId="+courseId
               +",contentsId="+contentsId+",contentsOrder="+contentsOrder+",contentsName="+contentsName+",filePath="+filePath+",serverPath="+serverPath
               +",contentsType="+contentsType+",quizCount="+quizCount+",quizPoint="+quizPoint+",showTime="+showTime+",regId="+regId
               +",regDate="+regDate+",modId="+modId+",modDate="+modDate;
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
	 * @return Returns the offlineDate1.
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param offlineDate1 The offlineDate1 to set.
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return Returns the offlineDate2.
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param offlineDate2 The offlineDate2 to set.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return Returns the lectureGubunCd.
	 */
	public String getLectureGubunCd() {
		return lectureGubunCd;
	}
	/**
	 * @param lectureGubunCd The lectureGubunCd to set.
	 */
	public void setLectureGubunCd(String lectureGubunCd) {
		this.lectureGubunCd = lectureGubunCd;
	}

	/**
	 * @return the processRate
	 */
	public double getProcessRate() {
		return processRate;
	}

	/**
	 * @param processRate the processRate to set
	 */
	public void setProcessRate(double processRate) {
		this.processRate = processRate;
	}

	public String getAsfFilePath() {
		return asfFilePath;
	}

	public void setAsfFilePath(String asfFilePath) {
		this.asfFilePath = asfFilePath;
	}
    }

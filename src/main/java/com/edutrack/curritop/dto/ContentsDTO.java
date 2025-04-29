/**
 * @(#)ContentsDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.curritop.dto;


/**
 * CONTENTS 테이블 Dto 클래스.
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
public class ContentsDTO {

    protected String systemCode;
    protected String courseId;
    protected String contentsId;
    protected String contentsOrder;
    protected String contentsName;
    protected String filePath;
    protected String asfFilePath;
    protected String serverPath;
    protected String contentsType;
    protected int quizCount;
    protected int quizPoint;
    protected int showTime;
    protected String preRequisite;
    protected String preType;
    protected String timeLimitAction;
    protected String dataFromLms;
    protected String masteryScore;
    protected String maxTimeAllowed;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;

    protected String isvisible;
    protected String parameters;
    protected String completionthreshold;
    protected String viewYn;

    protected int contentsWidth;
    protected int contentsHeight;
    protected String sizeApp;
    protected int progressRate;

    // 컨텐츠 등록/수정/삭제 관리를 위한 필드 sangsang 2007.05.18
    protected String parentContentsName;
    protected String regContentsType;
    protected String workMode;

    // 강의타입(온, 오프라인)
    protected String lectureGubun;
    

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

	public String getCompletionthreshold() {
		return completionthreshold;
	}
	public void setCompletionthreshold(String completionthreshold) {
		this.completionthreshold = completionthreshold;
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
	public String getIsvisible() {
		return isvisible;
	}
	public void setIsvisible(String isvisible) {
		this.isvisible = isvisible;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public int getProgressRate() {
		return progressRate;
	}
	public void setProgressRate(int progressRate) {
		this.progressRate = progressRate;
	}
	public String getSizeApp() {
		return sizeApp;
	}
	public void setSizeApp(String sizeApp) {
		this.sizeApp = sizeApp;
	}
	public String getViewYn() {
		return viewYn;
	}
	public void setViewYn(String viewYn) {
		this.viewYn = viewYn;
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
        return  "systemCode="+systemCode+",courseId="+courseId+",contentsId="+contentsId+",contentsOrder="+contentsOrder+",contentsName="+contentsName
               +",filePath="+filePath+",serverPath="+serverPath+",contentsType="+contentsType+",quizCount="+quizCount+",quizPoint="+quizPoint
               +",showTime="+showTime+",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
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

	public String getAsfFilePath() {
		return asfFilePath;
	}

	public void setAsfFilePath(String asfFilePath) {
		this.asfFilePath = asfFilePath;
	}
    }

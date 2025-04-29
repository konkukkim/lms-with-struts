/**
 * @(#)CurriTopDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.curritop.dto;


/**
 * CURRI_TOP 테이블 Dto 클래스.
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
public class CurriTopDTO {

    protected String systemCode;
    protected String curriCode;
	protected String pareCode1;
	protected String pareCode2;
    protected String cateCode;
    protected String curriProperty1;
    protected String curriProperty2;
    protected String curriName;
    protected int credit;
    protected String curriGoal;
    protected String curriInfo;
    protected String curriEnv;
    protected String assessment;
    protected String beforeInfo;
    protected String learningTime;
    protected String curriTarget;
    protected String curriContents;
    protected String curriContentsText;
    protected String curriImg1;
    protected String curriImg2;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    protected String pareCode1Name;
	protected String pareCode2Name;
	protected String cateCodeName;
	protected int courseCnt;
	
	protected int curriYear;
	protected int curriTerm;
	protected String profName;
	protected int dispLine;


    /**
	 * @return dispLine
	 */
	public int getDispLine() {
		return dispLine;
	}

	/**
	 * @param dispLine 설정하려는 dispLine
	 */
	public void setDispLine(int dispLine) {
		this.dispLine = dispLine;
	}

	/**
	 * @return curriTerm
	 */
	public int getCurriTerm() {
		return curriTerm;
	}

	/**
	 * @param curriTerm 설정하려는 curriTerm
	 */
	public void setCurriTerm(int curriTerm) {
		this.curriTerm = curriTerm;
	}

	/**
	 * @return curriYear
	 */
	public int getCurriYear() {
		return curriYear;
	}

	/**
	 * @param curriYear 설정하려는 curriYear
	 */
	public void setCurriYear(int curriYear) {
		this.curriYear = curriYear;
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
	 * @return the courseCnt
	 */
	public int getCourseCnt() {
		return courseCnt;
	}

	/**
	 * @param courseCnt the courseCnt to set
	 */
	public void setCourseCnt(int courseCnt) {
		this.courseCnt = courseCnt;
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
     * get cateCode.
     *
     * @return      cateCode
     */
    public String getCateCode() {
        return  this.cateCode;
    }

    /**
     * set cateCode.
     *
     * @param       cateCode
     */
    public void setCateCode(String cateCode) {
        this.cateCode = cateCode;
    }

    /**
     * get curriProperty1.
     *
     * @return      curriProperty1
     */
    public String getCurriProperty1() {
        return  this.curriProperty1;
    }

    /**
     * set curriProperty1.
     *
     * @param       curriProperty1
     */
    public void setCurriProperty1(String curriProperty1) {
        this.curriProperty1 = curriProperty1;
    }

    /**
     * get curriProperty2.
     *
     * @return      curriProperty2
     */
    public String getCurriProperty2() {
        return  this.curriProperty2;
    }

    /**
     * set curriProperty2.
     *
     * @param       curriProperty2
     */
    public void setCurriProperty2(String curriProperty2) {
        this.curriProperty2 = curriProperty2;
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
     * get credit.
     *
     * @return      credit
     */
    public int getCredit() {
        return  this.credit;
    }

    /**
     * set credit.
     *
     * @param       credit
     */
    public void setCredit(int credit) {
        this.credit = credit;
    }

    /**
     * get curriGoal.
     *
     * @return      curriGoal
     */
    public String getCurriGoal() {
        return  this.curriGoal;
    }

    /**
     * set curriGoal.
     *
     * @param       curriGoal
     */
    public void setCurriGoal(String curriGoal) {
        this.curriGoal = curriGoal;
    }

    /**
     * get curriInfo.
     *
     * @return      curriInfo
     */
    public String getCurriInfo() {
        return  this.curriInfo;
    }

    /**
     * set curriInfo.
     *
     * @param       curriInfo
     */
    public void setCurriInfo(String curriInfo) {
        this.curriInfo = curriInfo;
    }

    /**
     * get curriEnv.
     *
     * @return      curriEnv
     */
    public String getCurriEnv() {
        return  this.curriEnv;
    }

    /**
     * set curriEnv.
     *
     * @param       curriEnv
     */
    public void setCurriEnv(String curriEnv) {
        this.curriEnv = curriEnv;
    }

    /**
     * get assessment.
     *
     * @return      assessment
     */
    public String getAssessment() {
        return  this.assessment;
    }

    /**
     * set assessment.
     *
     * @param       assessment
     */
    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    /**
     * get beforeInfo.
     *
     * @return      beforeInfo
     */
    public String getBeforeInfo() {
        return  this.beforeInfo;
    }

    /**
     * set beforeInfo.
     *
     * @param       beforeInfo
     */
    public void setBeforeInfo(String beforeInfo) {
        this.beforeInfo = beforeInfo;
    }

    /**
     * get learningTime.
     *
     * @return      learningTime
     */
    public String getLearningTime() {
        return  this.learningTime;
    }

    /**
     * set learningTime.
     *
     * @param       learningTime
     */
    public void setLearningTime(String learningTime) {
        this.learningTime = learningTime;
    }

    /**
     * get curriTarget.
     *
     * @return      curriTarget
     */
    public String getCurriTarget() {
        return  this.curriTarget;
    }

    /**
     * set curriTarget.
     *
     * @param       curriTarget
     */
    public void setCurriTarget(String curriTarget) {
        this.curriTarget = curriTarget;
    }

    /**
     * get curriImg1.
     *
     * @return      curriImg1
     */
    public String getCurriImg1() {
        return  this.curriImg1;
    }

    /**
     * set curriImg1.
     *
     * @param       curriImg1
     */
    public void setCurriImg1(String curriImg1) {
        this.curriImg1 = curriImg1;
    }

    /**
     * get curriImg2.
     *
     * @return      curriImg2
     */
    public String getCurriImg2() {
        return  this.curriImg2;
    }

    /**
     * set curriImg2.
     *
     * @param       curriImg2
     */
    public void setCurriImg2(String curriImg2) {
        this.curriImg2 = curriImg2;
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
     * toString
     *
     * @return  String
     */
    public String toString() {
        return  "systemCode="+systemCode+",curriCode="+curriCode+",cateCode="+cateCode+",curriProperty1="+curriProperty1+",curriProperty2="+curriProperty2
               +",curriName="+curriName+",credit="+credit+",curriGoal="+curriGoal+",curriInfo="+curriInfo+",curriEnv="+curriEnv
               +",assessment="+assessment+",beforeInfo="+beforeInfo+",learningTime="+learningTime+",curriTarget="+curriTarget+",curriContents="+curriContents+",curriContentsText="+curriContentsText+",curriImg1="+curriImg1
               +",curriImg2="+curriImg2+",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

	/**
	 * @return Returns the curriContents.
	 */
	public String getCurriContents() {
		return curriContents;
	}
	/**
	 * @param curriContents The curriContents to set.
	 */
	public void setCurriContents(String curriContents) {
		this.curriContents = curriContents;
	}
	/**
	 * @return Returns the curriContentsText.
	 */
	public String getCurriContentsText() {
		return curriContentsText;
	}
	/**
	 * @param curriContentsText The curriContentsText to set.
	 */
	public void setCurriContentsText(String curriContentsText) {
		this.curriContentsText = curriContentsText;
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


	public String getPareCode1Name() {
		return pareCode1Name;
	}
	public void setPareCode1Name(String pareCode1Name) {
		this.pareCode1Name = pareCode1Name;
	}
	public String getPareCode2Name() {
		return pareCode2Name;
	}
	public void setPareCode2Name(String pareCode2Name) {
		this.pareCode2Name = pareCode2Name;
	}

	public String getCateCodeName() {
		return cateCodeName;
	}
	public void setCateCodeName(String cateCodeName) {
		this.cateCodeName = cateCodeName;
	}
}

/**
 * @(#)CurriSubCourseDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.currisub.dto;


/**
 * CURRI_SUB_COURSE 테이블 Dto 클래스.
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
public class CurriSubCourseDTO {

    protected String systemCode;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected String courseId;
    protected String courseName;
    protected String courseType;
    protected String profId;
    protected int examBase;
    protected int reportBase;
    protected int attendBase;
    protected int concernBase;
    protected int forumBase;
    protected int etc1Base;
    protected int etc2Base;
    protected String completeScore;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    
    protected String onlineYn;

    
    /**
	 * @return onlineYn
	 */
	public String getOnlineYn() {
		return onlineYn;
	}

	/**
	 * @param onlineYn 설정하려는 onlineYn
	 */
	public void setOnlineYn(String onlineYn) {
		this.onlineYn = onlineYn;
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
     * get courseType.
     *
     * @return      courseType
     */
    public String getCourseType() {
        return  this.courseType;
    }

    /**
     * set courseType.
     *
     * @param       courseType
     */
    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    /**
     * get profId.
     *
     * @return      profId
     */
    public String getProfId() {
        return  this.profId;
    }

    /**
     * set profId.
     *
     * @param       profId
     */
    public void setProfId(String profId) {
        this.profId = profId;
    }

    /**
     * get examBase.
     *
     * @return      examBase
     */
    public int getExamBase() {
        return  this.examBase;
    }

    /**
     * set examBase.
     *
     * @param       examBase
     */
    public void setExamBase(int examBase) {
        this.examBase = examBase;
    }

    /**
     * get reportBase.
     *
     * @return      reportBase
     */
    public int getReportBase() {
        return  this.reportBase;
    }

    /**
     * set reportBase.
     *
     * @param       reportBase
     */
    public void setReportBase(int reportBase) {
        this.reportBase = reportBase;
    }

    /**
     * get attendBase.
     *
     * @return      attendBase
     */
    public int getAttendBase() {
        return  this.attendBase;
    }

    /**
     * set attendBase.
     *
     * @param       attendBase
     */
    public void setAttendBase(int attendBase) {
        this.attendBase = attendBase;
    }

    /**
     * get concernBase.
     *
     * @return      concernBase
     */
    public int getConcernBase() {
        return  this.concernBase;
    }

    /**
     * set concernBase.
     *
     * @param       concernBase
     */
    public void setConcernBase(int concernBase) {
        this.concernBase = concernBase;
    }

    /**
     * get forumBase.
     *
     * @return      forumBase
     */
    public int getForumBase() {
        return  this.forumBase;
    }

    /**
     * set forumBase.
     *
     * @param       forumBase
     */
    public void setForumBase(int forumBase) {
        this.forumBase = forumBase;
    }

    /**
     * get etc1Base.
     *
     * @return      etc1Base
     */
    public int getEtc1Base() {
        return  this.etc1Base;
    }

    /**
     * set etc1Base.
     *
     * @param       etc1Base
     */
    public void setEtc1Base(int etc1Base) {
        this.etc1Base = etc1Base;
    }

    /**
     * get etc2Base.
     *
     * @return      etc2Base
     */
    public int getEtc2Base() {
        return  this.etc2Base;
    }

    /**
     * set etc2Base.
     *
     * @param       etc2Base
     */
    public void setEtc2Base(int etc2Base) {
        this.etc2Base = etc2Base;
    }

    /**
     * get completeScore.
     *
     * @return      completeScore
     */
    public String getCompleteScore() {
        return  this.completeScore;
    }

    /**
     * set completeScore.
     *
     * @param       completeScore
     */
    public void setCompleteScore(String completeScore) {
        this.completeScore = completeScore;
    }

    
    public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
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
        return  "systemCode="+systemCode+",curriCode="+curriCode+",curriYear="+curriYear+",curriTerm="+curriTerm+",courseId="+courseId
               +",courseType="+courseType+",profId="+profId+",examBase="+examBase+",reportBase="+reportBase+",attendBase="+attendBase
               +",concernBase="+concernBase+",forumBase="+forumBase+",etc1Base="+etc1Base+",etc2Base="+etc2Base+",completeScore="+completeScore
               +",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

/**
 * @(#)CourseDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.curritop.dto;


/**
 * COURSE 테이블 Dto 클래스.
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
public class CourseDTO {

    protected String systemCode;
    protected String courseId;
    protected String courseName;
    protected String profId;
    protected String courseImg1;
    protected String courseImg2;
    protected String contentsWidth;
    protected String contentsHeight;
    protected String contentsType;
    protected String flagUse;
    protected String flagUseName;
    protected String flagNavi;
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
     * get courseName.
     *
     * @return      courseName
     */
    public String getCourseName() {
        return  this.courseName;
    }

    /**
     * set courseName.
     *
     * @param       courseName
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
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
     * get courseImg1.
     *
     * @return      courseImg1
     */
    public String getCourseImg1() {
        return  this.courseImg1;
    }

    /**
     * set courseImg1.
     *
     * @param       courseImg1
     */
    public void setCourseImg1(String courseImg1) {
        this.courseImg1 = courseImg1;
    }

    /**
     * get courseImg2.
     *
     * @return      courseImg2
     */
    public String getCourseImg2() {
        return  this.courseImg2;
    }

    /**
     * set courseImg2.
     *
     * @param       courseImg2
     */
    public void setCourseImg2(String courseImg2) {
        this.courseImg2 = courseImg2;
    }

    /**
     * get contentsWidth.
     *
     * @return      contentsWidth
     */
    public String getContentsWidth() {
        return  this.contentsWidth;
    }

    /**
     * set contentsWidth.
     *
     * @param       contentsWidth
     */
    public void setContentsWidth(String contentsWidth) {
        this.contentsWidth = contentsWidth;
    }

    /**
     * get contentsHeight.
     *
     * @return      contentsHeight
     */
    public String getContentsHeight() {
        return  this.contentsHeight;
    }

    /**
     * set contentsHeight.
     *
     * @param       contentsHeight
     */
    public void setContentsHeight(String contentsHeight) {
        this.contentsHeight = contentsHeight;
    }
    
    /**
     * get contentsType.
     * 
     * @return contentsType
     */
    public String getContentsType() {
    	return this.contentsType;
    }
    
    /**
     * set contentsType.
     * 
     * @param contentsType
     */
    public void setContentsType(String contentsType) {
    	this.contentsType = contentsType;
    }

    /**
     * get flagUse.
     *
     * @return      flagUse
     */
    public String getFlagUse() {
        return  this.flagUse;
    }

    /**
     * set flagUse.
     *
     * @param       flagUse
     */
    public void setFlagUse(String flagUse) {
        this.flagUse = flagUse;
    }

    public String getFlagUseName() {
		return flagUseName;
	}

	public void setFlagUseName(String flagUseName) {
		this.flagUseName = flagUseName;
	}

	/**
     * get flagNavi.
     *
     * @return      flagNavi
     */
    public String getFlagNavi() {
        return  this.flagNavi;
    }

    /**
     * set flagNavi.
     *
     * @param       flagNavi
     */
    public void setFlagNavi(String flagNavi) {
        this.flagNavi = flagNavi;
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
        return  "systemCode="+systemCode+",courseId="+courseId+",courseName="+courseName+",profId="+profId+",courseImg1="+courseImg1
               +",courseImg2="+courseImg2+",contentsWidth="+contentsWidth+",contentsHeight="+contentsHeight+",flagUse="+flagUse+",flagNavi="+flagNavi
               +",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

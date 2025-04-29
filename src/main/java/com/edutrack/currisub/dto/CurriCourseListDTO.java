/**
 * @(#)CurriCourseListDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.currisub.dto;


/**
 * CURRI_COURSE_LIST 테이블 Dto 클래스.
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
public class CurriCourseListDTO {

    protected String systemCode;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected String courseId;
    protected String courseName;
    protected String profId;
    protected String profName;
    protected String regDate;
    protected String contentsType;
    protected String contentsWidth;
    protected String contentsHeight;
    protected String flagNavi;

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
     * get profName.
     *
     * @return      profName
     */
    public String getProfName() {
        return  this.profName;
    }

    /**
     * set profName.
     *
     * @param       profName
     */
    public void setProfName(String profName) {
        this.profName = profName;
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
    
}

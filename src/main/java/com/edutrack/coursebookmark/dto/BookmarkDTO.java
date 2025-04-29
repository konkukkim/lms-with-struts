/**
 * @(#)BookmarkDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.coursebookmark.dto;


/**
 * BOOKMARK 테이블 Dto 클래스.
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
public class BookmarkDTO {

    protected String systemCode;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected String userId;
    protected String courseId;
    protected String contentsId;
    protected String contentsOrder;
    protected String openChk;
    protected String openDate;
    protected String quizPass;
    protected int scoreRaw;
    protected int scoreMax;
    protected int scoreMin;
    protected int totalTime;
    protected int sessionTime;
    protected int joinCount;
    protected long cmiIdref;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;

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
     * get userId.
     *
     * @return      userId
     */
    public String getUserId() {
        return  this.userId;
    }

    /**
     * set userId.
     *
     * @param       userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * get openChk.
     *
     * @return      openChk
     */
    public String getOpenChk() {
        return  this.openChk;
    }

    /**
     * set openChk.
     *
     * @param       openChk
     */
    public void setOpenChk(String openChk) {
        this.openChk = openChk;
    }

    /**
     * get openDate.
     *
     * @return      openDate
     */
    public String getOpenDate() {
        return  this.openDate;
    }

    /**
     * set openDate.
     *
     * @param       openDate
     */
    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    /**
     * get quizPass.
     *
     * @return      quizPass
     */
    public String getQuizPass() {
        return  this.quizPass;
    }

    /**
     * set quizPass.
     *
     * @param       quizPass
     */
    public void setQuizPass(String quizPass) {
        this.quizPass = quizPass;
    }

    /**
     * get scoreRaw.
     *
     * @return      scoreRaw
     */
    public int getScoreRaw() {
        return  this.scoreRaw;
    }

    /**
     * set scoreRaw.
     *
     * @param       scoreRaw
     */
    public void setScoreRaw(int scoreRaw) {
        this.scoreRaw = scoreRaw;
    }

    /**
     * get scoreMax.
     *
     * @return      scoreMax
     */
    public int getScoreMax() {
        return  this.scoreMax;
    }

    /**
     * set scoreMax.
     *
     * @param       scoreMax
     */
    public void setScoreMax(int scoreMax) {
        this.scoreMax = scoreMax;
    }

    /**
     * get scoreMin.
     *
     * @return      scoreMin
     */
    public int getScoreMin() {
        return  this.scoreMin;
    }

    /**
     * set scoreMin.
     *
     * @param       scoreMin
     */
    public void setScoreMin(int scoreMin) {
        this.scoreMin = scoreMin;
    }

    /**
     * get totalTime.
     *
     * @return      totalTime
     */
    public int getTotalTime() {
        return  this.totalTime;
    }

    /**
     * set totalTime.
     *
     * @param       totalTime
     */
    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * get sessionTime.
     *
     * @return      sessionTime
     */
    public int getSessionTime() {
        return  this.sessionTime;
    }

    /**
     * set sessionTime.
     *
     * @param       sessionTime
     */
    public void setSessionTime(int sessionTime) {
        this.sessionTime = sessionTime;
    }

    /**
     * get joinCount.
     *
     * @return      joinCount
     */
    public int getJoinCount() {
        return  this.joinCount;
    }

    /**
     * set joinCount.
     *
     * @param       joinCount
     */
    public void setJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }

    /**
     * get cmiIdref.
     *
     * @return      cmiIdref
     */
    public long getCmiIdref() {
        return  this.cmiIdref;
    }

    /**
     * set cmiIdref.
     *
     * @param       cmiIdref
     */
    public void setCmiIdref(long cmiIdref) {
        this.cmiIdref = cmiIdref;
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
        return  "systemCode="+systemCode+",curriCode="+curriCode+",curriYear="+curriYear+",curriTerm="+curriTerm+",userId="+userId
               +",courseId="+courseId+",contentsId="+contentsId+",contentsOrder="+contentsOrder+",openChk="+openChk+",openDate="+openDate
               +",quizPass="+quizPass+",scoreRaw="+scoreRaw+",scoreMax="+scoreMax+",scoreMin="+scoreMin+",totalTime="+totalTime
               +",sessionTime="+sessionTime+",joinCount="+joinCount+",cmiIdref="+cmiIdref+",regId="+regId+",regDate="+regDate
               +",modId="+modId+",modDate="+modDate;
    }

    }

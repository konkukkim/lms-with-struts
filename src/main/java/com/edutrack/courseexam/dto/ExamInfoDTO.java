/**
 * @(#)ExamInfoDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.courseexam.dto;


/**
 * EXAM_INFO 테이블 Dto 클래스.
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
public class ExamInfoDTO {

    protected String systemCode="";
    protected String curriCode="";
    protected int curriYear=0;
    protected int curriTerm=0;
    protected String courseId="";
    protected int examId=0;
    protected String subject="";
    protected String comment="";
    protected String examType="";
    protected String startDate="";
    protected String endDate="";
    protected String extendEnd="";
    protected String resultDate="";
    protected int runTime=0;
    protected String viewStyle="";
    protected String flagUse="";
    protected String flagTime="";
    protected String regId="";
    protected String regDate="";
    protected String modId="";
    protected String modDate="";

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
     * get examId.
     *
     * @return      examId
     */
    public int getExamId() {
        return  this.examId;
    }

    /**
     * set examId.
     *
     * @param       examId
     */
    public void setExamId(int examId) {
        this.examId = examId;
    }

    /**
     * get subject.
     *
     * @return      subject
     */
    public String getSubject() {
        return  this.subject;
    }

    /**
     * set subject.
     *
     * @param       subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * get xcomment.
     *
     * @return      xcomment
     */
    public String getComment() {
        return  this.comment;
    }

    /**
     * set xcomment.
     *
     * @param       xcomment
     */
    public void setComment(String xcomment) {
        this.comment = xcomment;
    }

    /**
     * get examType.
     *
     * @return      examType
     */
    public String getExamType() {
        return  this.examType;
    }

    /**
     * set examType.
     *
     * @param       examType
     */
    public void setExamType(String examType) {
        this.examType = examType;
    }

    /**
     * get startDate.
     *
     * @return      startDate
     */
    public String getStartDate() {
        return  this.startDate;
    }

    /**
     * set startDate.
     *
     * @param       startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * get endDate.
     *
     * @return      endDate
     */
    public String getEndDate() {
        return  this.endDate;
    }

    /**
     * set endDate.
     *
     * @param       endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * get extendEnd.
     *
     * @return      extendEnd
     */
    public String getExtendEnd() {
        return  this.extendEnd;
    }

    /**
     * set extendEnd.
     *
     * @param       extendEnd
     */
    public void setExtendEnd(String extendEnd) {
        this.extendEnd = extendEnd;
    }

    /**
     * get resultDate.
     *
     * @return      resultDate
     */
    public String getResultDate() {
        return  this.resultDate;
    }

    /**
     * set resultDate.
     *
     * @param       resultDate
     */
    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }

    /**
     * get runTime.
     *
     * @return      runTime
     */
    public int getRunTime() {
        return  this.runTime;
    }

    /**
     * set runTime.
     *
     * @param       runTime
     */
    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    /**
     * get viewStyle.
     *
     * @return      viewStyle
     */
    public String getViewStyle() {
        return  this.viewStyle;
    }

    /**
     * set viewStyle.
     *
     * @param       viewStyle
     */
    public void setViewStyle(String viewStyle) {
        this.viewStyle = viewStyle;
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

    /**
     * get flagTime.
     *
     * @return      flagTime
     */
    public String getFlagTime() {
        return  this.flagTime;
    }

    /**
     * set flagTime.
     *
     * @param       flagTime
     */
    public void setFlagTime(String flagTime) {
        this.flagTime = flagTime;
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
               +",examId="+examId+",subject="+subject+",xcomment="+comment+",examType="+examType+",startDate="+startDate
               +",endDate="+endDate+",extendEnd="+extendEnd+",resultDate="+resultDate+",runTime="+runTime+",viewStyle="+viewStyle
               +",flagUse="+flagUse+",flagTime="+flagTime+",regId="+regId+",regDate="+regDate+",modId="+modId
               +",modDate="+modDate;
    }

    }

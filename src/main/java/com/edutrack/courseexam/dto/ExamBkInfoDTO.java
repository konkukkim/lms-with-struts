/**
 * @(#)ExamBkInfoDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.courseexam.dto;


/**
 * EXAM_BK_INFO 테이블 Dto 클래스.
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
public class ExamBkInfoDTO {

    protected String systemCode="";
    protected String courseId="";
    protected int examBkCode=0;
    protected String examBkName="";
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
     * get examBkCode.
     *
     * @return      examBkCode
     */
    public int getExamBkCode() {
        return  this.examBkCode;
    }

    /**
     * set examBkCode.
     *
     * @param       examBkCode
     */
    public void setExamBkCode(int examBkCode) {
        this.examBkCode = examBkCode;
    }

    /**
     * get examBkName.
     *
     * @return      examBkName
     */
    public String getExamBkName() {
        return  this.examBkName;
    }

    /**
     * set examBkName.
     *
     * @param       examBkName
     */
    public void setExamBkName(String examBkName) {
        this.examBkName = examBkName;
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
        return  "systemCode="+systemCode+",courseId="+courseId+",examBkCode="+examBkCode+",examBkName="+examBkName+",regId="+regId
               +",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

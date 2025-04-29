/**
 * @(#)ReportBkInfoDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.coursereport.dto;


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
public class ReportBkInfoDTO {

    protected String systemCode="";
    protected String courseId="";
    protected int reportBkCode=0;
    protected String reportBkName="";
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
     * get reportBkCode.
     *
     * @return      reportBkCode
     */
    public int getReportBkCode() {
        return  this.reportBkCode;
    }

    /**
     * set reportBkCode.
     *
     * @param       reportBkCode
     */
    public void setReportBkCode(int reportBkCode) {
        this.reportBkCode = reportBkCode;
    }

    /**
     * get reportBkName.
     *
     * @return      reportBkName
     */
    public String getReportBkName() {
        return  this.reportBkName;
    }

    /**
     * set reportBkName.
     *
     * @param       reportBkName
     */
    public void setReportBkName(String reportBkName) {
        this.reportBkName = reportBkName;
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
        return  "systemCode="+systemCode+",courseId="+courseId+",reportBkCode="+reportBkCode+",reportBkName="+reportBkName+",regId="+regId
               +",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

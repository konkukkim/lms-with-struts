/**
 * @(#)CourseConnectDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.connstatus.dto;


/**
 * COURSE_CONNECT 테이블 Dto 클래스.
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
public class CourseConnectDTO {

    protected String systemCode;
    protected String selectGb;
    protected String courseId;
    protected int cnt;
    protected String startYear="all";
    protected String startMon="all";
    protected String startDay="all";
    protected String endYear="all";
    protected String endMon="all";
    protected String endDay="all";
    protected String startTime ="00";
    protected String endTime = "24";
    
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
     * get selectGb.
     *
     * @return      selectGb
     */
    public String getSelectGb() {
        return  this.selectGb;
    }
    
    /**
     * set selectGb.
     *
     * @param       selectGb
     */
    public void setSelectGb(String selectGb) {
        this.selectGb = selectGb;
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
     * get cnt.
     *
     * @return      cnt
     */
    public int getCnt() {
        return  this.cnt;
    }

    /**
     * set cnt.
     *
     * @param       cnt
     */
    public void setCnt(int cnt) {
        this.cnt = cnt;
    }    

    /**
     * get startYear.
     *
     * @return      startYear
     */
    public String getStartYear() {
        return  this.startYear;
    }

    /**
     * set startYear.
     *
     * @param       startYear
     */
    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }
    
    /**
     * get startMon.
     *
     * @return      startMon
     */
    public String getStartMon() {
        return  this.startMon;
    }

    /**
     * set startMon.
     *
     * @param       startMon
     */
    public void setStartMon(String startMon) {
        this.startMon = startMon;
    }
    
    /**
     * get startDay.
     *
     * @return      startDay
     */
    public String getStartDay() {
        return  this.startDay;
    }

    /**
     * set startDay.
     *
     * @param       startDay
     */
    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }
    
    /**
     * get endYear.
     *
     * @return      endYear
     */
    public String getEndYear() {
        return  this.endYear;
    }

    /**
     * set endYear.
     *
     * @param       endYear
     */
    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }
    
    /**
     * get endMon.
     *
     * @return      endMon
     */
    public String getEndMon() {
        return  this.endMon;
    }

    /**
     * set endMon.
     *
     * @param       endMon
     */
    public void setEndMon(String endMon) {
        this.endMon = endMon;
    }

    /**
     * get endDay.
     *
     * @return      endDay
     */
    public String getEndDay() {
        return  this.endDay;
    }

    /**
     * set endDay.
     *
     * @param       endDay
     */
    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    /**
     * get startTime.
     *
     * @return      startTime
     */
    public String getStartTime() {
        return  this.startTime;
    }

    /**
     * set startTime.
     *
     * @param       startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * get endTime.
     *
     * @return      endTime
     */
    public String getEndTime() {
        return  this.endTime;
    }

    /**
     * set endTime.
     *
     * @param       endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    /**
     * toString 
     *
     * @return  String 
     */
//    public String toString() {
//        return  "systemCode="+systemCode+",selectGb="+selectGb+",courseId="+courseId+",connYear="+connYear+",connMonth="+connMonth+",connDay="+connDay+",connHour="+connHour
//        +",age="+age+",sexType="+sexType+",area="+area+",dept="+dept+",etc="+etc+",cnt="+cnt
//        +",startYear="+startYear+",startMon="+startMon+",startDay="+startDay+",endYear="+endYear+",endMon="+endMon
//        +",endDay="+endDay+",startTime="+startTime+",endTime="+endTime;
//    }
}
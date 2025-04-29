/**
 * @(#)CurriConnectDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.connstatus.dto;


/**
 * CURRI_CONNECT 테이블 Dto 클래스.
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
public class CurriConnectDTO {

    protected String systemCode;
    protected String selectGb;
    protected String selectCate;
    protected String selectType;
    protected String curriCode;
    protected String curriYear;
    protected String curriTerm;    
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
	 * @return Returns the selectCate.
	 */
	public String getSelectCate() {
		return selectCate;
	}
	/**
	 * @param selectCate The selectCate to set.
	 */
	public void setSelectCate(String selectCate) {
		this.selectCate = selectCate;
	}
	/**
	 * @return Returns the selectType.
	 */
	public String getSelectType() {
		return selectType;
	}
	/**
	 * @param selectType The selectType to set.
	 */
	public void setSelectType(String selectType) {
		this.selectType = selectType;
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
    public String getCurriYear() {
        return  this.curriYear;
    }

    /**
     * set curriYear.
     *
     * @param       curriYear
     */
    public void setCurriYear(String curriYear) {
        this.curriYear = curriYear;
    }

    /**
     * get curriYear.
     *
     * @return      curriYear
     */
    public String getCurriTerm() {
        return  this.curriTerm;
    }

    /**
     * set curriYear.
     *
     * @param       curriYear
     */
    public void setCurriTerm(String curriTerm) {
        this.curriTerm = curriTerm;
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
//        return  "systemCode="+systemCode+",selectGb="+selectGb+",curriCode="+curriCode+",connYear="+connYear+",connMonth="+connMonth+",connDay="+connDay+",connHour="+connHour
//        +",curriYear="+curriYear+",curriTerm="+curriTerm+",age="+age+",sexType="+sexType+",area="+area+",dept="+dept+",etc="+etc+",cnt="+cnt
//        +",startYear="+startYear+",startMon="+startMon+",startDay="+startDay+",endYear="+endYear+",endMon="+endMon
//        +",endDay="+endDay+",startTime="+startTime+",endTime="+endTime;
//    }
}
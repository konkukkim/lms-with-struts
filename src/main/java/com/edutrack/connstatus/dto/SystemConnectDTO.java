/**
 * @(#)SystemConnectDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.connstatus.dto;

import com.edutrack.framework.util.StringUtil;


/**
 * SYSTEM_CONNECT 테이블 Dto 클래스.
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
public class SystemConnectDTO {

    protected String systemCode;
    protected String selectGb;
    protected String dateGb;
    protected String studentGb;
    protected int cnt;
    protected String startYear="all";
    protected String startMon="all";
    protected String startDay="all";
    protected String endYear="all";
    protected String endMon="all";
    protected String endDay="all";
    protected String startTime ="00";
    protected String endTime = "24";
    protected String cateCode = "";
    protected String property1 = "";
    protected String property2 = "";
    protected String curriCode = "";
    
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
	 * @return Returns the dateGb.
	 */
	public String getDateGb() {
		return dateGb;
	}
	/**
	 * @param dateGb The dateGb to set.
	 */
	public void setDateGb(String dateGb) {
		this.dateGb = dateGb;
	}
	
	/**
	 * @return Returns the studentGb.
	 */
	public String getStudentGb() {
		return studentGb;
	}
	/**
	 * @param studentGb The studentGb to set.
	 */
	public void setStudentGb(String studentGb) {
		this.studentGb = studentGb;
	}

	
	
	/**
	 * @return Returns the studentGb.
	 */
	public String getCateCode() {
		return cateCode;
	}
	/**
	 * @param studentGb The studentGb to set.
	 */
	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}
	
	/**
	 * @return Returns the studentGb.
	 */
	public String getProperty1() {
		return property1;
	}
	/**
	 * @param studentGb The studentGb to set.
	 */
	public void setProperty1(String property1) {
		this.property1 = property1;
	}
	
	/**
	 * @return Returns the studentGb.
	 */
	public String getProperty2() {
		return property2;
	}
	/**
	 * @param studentGb The studentGb to set.
	 */
	public void setProperty2(String property2) {
		this.property2 = property2;
	}
	
	/**
	 * @return Returns the studentGb.
	 */
	public String getCurriCode() {
		return curriCode;
	}
	/**
	 * @param studentGb The studentGb to set.
	 */
	public void setCurriCode(String curriCode) {
		this.curriCode = curriCode;
	}
	
	/**
     * toString 
     *
     * @return  String 
     */
//    public String toString() {
//        return  "systemCode="+systemCode+",selectGb="+selectGb+",connYear="+connYear+",connMonth="+connMonth+",connDay="+connDay+",connHour="+connHour
//        +",age="+age+",sexType="+sexType+",area="+area+",dept="+dept+",etc="+etc+",cnt="+cnt
//        +",startYear="+startYear+",startMon="+startMon+",startDay="+startDay+",endYear="+endYear+",endMon="+endMon
//        +",endDay="+endDay+",startTime="+startTime+",endTime="+endTime;
//    }
}
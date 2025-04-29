package com.edutrack.common;

import java.util.Map;

/*********************************************************
 * 프로그램 : DateSetter.java
 * 모 듈 명 : 
 * 설    명 : 
 * 테 이 블 :
 * 작 성 자 : chorongjang
 * 작 성 일 : 2004. 7. 15.
 * 수 정 일 :
 * 수정사항 : 
 *********************************************************/
public class DateSetter {
    private String startDt = "";
    private String endDt = "";
    private String year1 = "";
    private String month1 = "";
    private String day1 = "";
    private String hour1 = "";
    private String min1 = ""; 
    private String sec1 = "";
    private String year2 = "";
    private String month2 = "";
    private String day2 = "";
    private String hour2 = "";
    private String min2 = "";
    private String sec2 = "";
    
    /**
     *  날짜 타입 구분 
     * 0 : YYYYMMDDHH
     * 1 : YYYYMMDD
     * 2 : YYYYMM
     * 3 : YYYY
     */
    public DateSetter() {
        super();
        // TODO Auto-generated constructor stub
    }

    public DateSetter(String startdt){
        if(startdt == null) startdt = "";
        setSpliteDate(startdt, "s");
    }
    
    public DateSetter(String startdt, String enddt) {
        if(startdt == null) startdt = "";
        if(enddt == null) enddt = "";
        setSpliteDate(startdt, "s");
        setSpliteDate(enddt, "e");
    }
    
    private void setSpliteDate(String date, String type){
        String imsi="";
        if(date.length() == 14){
            imsi += date.substring(0,4);
            imsi += date.substring(4,6);
            imsi += date.substring(6,8);
            imsi += date.substring(8,10);
            imsi += date.substring(10,12);
            imsi += date.substring(12,14);
        }else if(date.length() == 12){
            imsi += date.substring(0,4);
            imsi += date.substring(4,6);
            imsi += date.substring(6,8);
            imsi += date.substring(8,10);
            imsi += date.substring(10,12);
            if(type.equals("s")) imsi += "01";
            else imsi += "59";
        }else if(date.length() == 10){
            imsi += date.substring(0,4);
            imsi += date.substring(4,6);
            imsi += date.substring(6,8);
            imsi += date.substring(8,10);
            if(type.equals("s")) imsi += "0101";
            else imsi += "5959";
		}else if(date.length() == 8){		    
            imsi += date.substring(0,4);
            imsi += date.substring(4,6);
            imsi += date.substring(6,8);
            if(type.equals("s")) imsi += "010101";
            else imsi += "235959";
		}else if(date.length() == 6){
            imsi += date.substring(0,4);
            imsi += date.substring(4,6);
            if(type.equals("s")) imsi += "01010101";
            else imsi += "31235959";
		}else if(date.length() == 4){
            imsi += date.substring(0,4);
            if(type.equals("s")) imsi += "0101010101";
            else imsi += "1231235959";
		}
	   		
		setDate(imsi, type);
    }

    private void setDate(String date, String type){
       if(date != null && !date.equals(""))
    	if(type.equals("s")){
             year1 = date.substring(0,4);
             month1 = date.substring(4,6);
             day1 = date.substring(6,8);
             hour1 = date.substring(8,10);
             min1 = date.substring(10,12);
             sec1 = date.substring(12,14);
         }else{
             year2 = date.substring(0,4);
             month2 = date.substring(4,6);
             day2 = date.substring(6,8);
             hour2 = date.substring(8,10);
             min2 = date.substring(10,12);
             sec2 = date.substring(12,14);
         }
    }
    
    /**
     * @return Returns the endDt.
     */
    public String getEndDt() {
        return endDt;
    }
    /**
     * @param endDt The endDt to set.
     */
    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }
    /**
     * @return Returns the startDt.
     */
    public String getStartDt() {
        return startDt;
    }
    /**
     * @param startDt The startDt to set.
     */
    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }
    /**
     * @return Returns the day1.
     */
    public String getDay1() {
        return day1;
    }
    /**
     * @param day1 The day1 to set.
     */
    public void setDay1(String day1) {
        this.day1 = day1;
    }
    /**
     * @return Returns the day2.
     */
    public String getDay2() {
        return day2;
    }
    /**
     * @param day2 The day2 to set.
     */
    public void setDay2(String day2) {
        this.day2 = day2;
    }
    /**
     * @return Returns the month1.
     */
    public String getMonth1() {
        return month1;
    }
    /**
     * @param month1 The month1 to set.
     */
    public void setMonth1(String month1) {
        this.month1 = month1;
    }
    /**
     * @return Returns the month2.
     */
    public String getMonth2() {
        return month2;
    }
    /**
     * @param month2 The month2 to set.
     */
    public void setMonth2(String month2) {
        this.month2 = month2;
    }

    /**
     * @return Returns the year1.
     */
    public String getYear1() {
        return year1;
    }
    /**
     * @param year1 The year1 to set.
     */
    public void setYear1(String year1) {
        this.year1 = year1;
    }
    /**
     * @return Returns the year2.
     */
    public String getYear2() {
        return year2;
    }
    /**
     * @param year2 The year2 to set.
     */
    public void setYear2(String year2) {
        this.year2 = year2;
    }
    
    /**
     * @return Returns the hour1.
     */
    public String getHour1() {
        return hour1;
    }
    /**
     * @param hour1 The hour1 to set.
     */
    public void setHour1(String hour1) {
        this.hour1 = hour1;
    }
    /**
     * @return Returns the hour2.
     */
    public String getHour2() {
        return hour2;
    }
    /**
     * @param hour2 The hour2 to set.
     */
    public void setHour2(String hour2) {
        this.hour2 = hour2;
    }
    /**
     * @return Returns the min1.
     */
    public String getMin1() {
        return min1;
    }
    /**
     * @param min1 The min1 to set.
     */
    public void setMin1(String min1) {
        this.min1 = min1;
    }
    /**
     * @return Returns the min2.
     */
    public String getMin2() {
        return min2;
    }
    /**
     * @param min2 The min2 to set.
     */
    public void setMin2(String min2) {
        this.min2 = min2;
    }

    /**
     * @return Returns the sec1.
     */
    public String getSec1() {
        return sec1;
    }
    /**
     * @param sec1 The sec1 to set.
     */
    public void setSec1(String sec1) {
        this.sec1 = sec1;
    }
    /**
     * @return Returns the sec2.
     */
    public String getSec2() {
        return sec2;
    }
    /**
     * @param sec2 The sec2 to set.
     */
    public void setSec2(String sec2) {
        this.sec2 = sec2;
    }
    
	public DateSetter link(Map model) {
		model.put("dateyn", "Y");
		return this;
	}
}

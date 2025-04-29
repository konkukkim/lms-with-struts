package com.edutrack.framework.util;

import java.util.Calendar;
import java.util.Locale;

/**
 * 	현재 시각 또는 'YYYYMMDD24HHMISS' 형태의 문자열을 이용하여
 * 	'YYYY/MM/DD 24HH:MI:SS' 형태의 문자열 변환
 *
 */

public class DateTimeUtil {
	private static final String DATE_GUBUN = ".";
	private static final String TIME_GUBUN = ":";
    private static DateTimeUtil dateTimeUtilIns = null;
	
    public static DateTimeUtil getInstance(){  
	    if(dateTimeUtilIns == null) {
	        dateTimeUtilIns = new DateTimeUtil();
	     }
	
	     return dateTimeUtilIns;
    }

	/**
	 * 현재 시간을 돌려준다. - HH:MI:SS 
	 */
	public static String getTimeText(int type, String szTime)
	{
		if(szTime != null && szTime.length() != 6) return ""; 
		
		
		if(szTime != null && szTime.length() == 6){			
			String hour = StringUtil.substring(szTime,0, 2);
			String minute = StringUtil.substring(szTime, 2, 4);
			String second = StringUtil.substring(szTime,4, 6);
		
			switch(type) {
		        case 1:
		            return hour + TIME_GUBUN + minute + TIME_GUBUN+ second;
		        case 2:
		            return  hour + TIME_GUBUN + minute;
		        case 3:    
		            return  hour;
	       }
		}	

		return "";
	}

	/**
	 * 현재 시간을 돌려준다. - HHMISS 
	 */
	public static String getTime(){
		String hour, min, sec;

 		Calendar cal = Calendar.getInstance(Locale.getDefault());

 		StringBuffer buf = new StringBuffer();

 		hour = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
 		if(hour.length() == 1) hour = "0" + hour;

 		min = Integer.toString(cal.get(Calendar.MINUTE));
 		if(min.length() == 1) min = "0" + min;

 		sec = Integer.toString(cal.get(Calendar.SECOND));
 		if(sec.length() == 1) sec = "0" + sec;

 		buf.append(hour);
 		buf.append(min);
 		buf.append(sec);

		return buf.toString();
	}

	/**
	 * 현재 년월일을 돌려준다. - YYYY.MM.DD
	 * TYPE 1 : YYYY.MM.DD
	 * TYPE 2 : YY.MM.DD
	 * TYPE 3 : MM.DD
	 * TYPE 4 : YYYY.MM
	 * TYPE 5 : YYYY
	 * TYPE 6 : YYYY-MM-DD
	 */
	public static String getDateText(int type, String szdate){
	    return getDateText(type, szdate,DATE_GUBUN);
	}
	public static String getDateText(int type, String szdate,String delimeter)
	{
		String reDate = "";
		
		if(szdate != null && szdate.length() != 8) return ""; 
		
		
		if(szdate != null && szdate.length() == 8){			
			String year = szdate.substring(0, 4);
			String month = szdate.substring(4, 6);
			String day = szdate.substring(6, 8);
		
			switch(type) {
		        case 1:
		            return  year + delimeter + month + delimeter + day;
		        case 2:
		            return  year.substring(2, 4) + delimeter + month + delimeter + day;
		        case 3:    
		            return month + delimeter + day;
		        case 4:
		            return  year + delimeter + month;
		        case 5:
		            return year;
		        case 6:
		        	return  year + "-" + month + "-" + day;
		        	
	       }
		}	
		
		return "";
	}
	
	/**
	 * 특정형태의 날자 타입을 돌려준다.
	 * TYPE 0 : YYYY.MM.DD HH:MI:SS
	 * TYPE 1 : YYYY.MM.DD
	 * TYPE 2 : YY.MM.DD
	 * TYPE 3 : MM.DD
	 * TYPE 4 : YYYY.MM
	 * TYPE 5 : YYYY
	 * TYPE 6 : MM.DD HH:MI
	 * TYPE 7 : HH:MI
	 * TYPE 8 : YYYY-MM-DD
     * @param type
	 * @param dateTime
	 * @return
	 */
	public static String getDateType(int type, String date){
	    return getDateType(type, date, DATE_GUBUN);
	}
	
	public static String getDateType(int type, String date, String delimeter)
	{
		if (date == null) {
			return "";
		}

		if(date.length() == 12) date += "01";
	    else if(date.length() == 10) date += "0101";
        else if(date.length() == 8) date += "010101";
        else if(date.length() == 6) date += "01010101";
        else if(date.length() == 4) date += "0101010101";

        switch(type) {
	        case 0:
	            return getDateText(1,StringUtil.substring(date, 0, 8), delimeter) + " " + getTimeText(1,StringUtil.substring(date, 8, 14));
	        case 1:
	            return getDateText(1,StringUtil.substring(date, 0, 8), delimeter);
	        case 2:
	            return getDateText(2,StringUtil.substring(date, 0, 8), delimeter);
	        case 3:
	            return getDateText(3,StringUtil.substring(date, 0, 8), delimeter);
	        case 4:
	            return getDateText(4,StringUtil.substring(date, 0, 8), delimeter);
	        case 5:
	            return getDateText(5,StringUtil.substring(date, 0, 8), delimeter);
	        case 6:
	            return getDateText(3,StringUtil.substring(date, 0, 8), delimeter) + " " + getTimeText(2,StringUtil.substring(date, 8, 14));
	        case 7:
	            return getTimeText(2,StringUtil.substring(date, 8, 14));
	        case 8:
	            return getDateText(1,StringUtil.substring(date, 0, 8), "-");
        }
        
        return "";
	}

    /**
     * 현재 년도를 돌려준다.
     * @return
     */
	public static String getYear(){
	    String ym = getYearMonth();
	    
	    return ym.substring(0,4);
	} 

	/**
	 * 현재 달을 돌려준다.
	 * @return
	 */
	public static String getMonth(){
	    String ym = getYearMonth();
	    
	    return ym.substring(4,6);
	}
	
	/**
	 * 현재 년월을 돌려준다 - YYYYMM 
	 */
	public static String getYearMonth(){
		String month;

 		Calendar cal = Calendar.getInstance(Locale.getDefault());

 		StringBuffer buf = new StringBuffer();

 		buf.append(Integer.toString(cal.get(Calendar.YEAR)));
 		month = Integer.toString(cal.get(Calendar.MONTH)+1);
 		if(month.length() == 1) month = "0" + month;
 		
 		buf.append(month);
 		
		return buf.toString();
	}
	
	   public static String getDate()
	    {
	        Calendar cal = Calendar.getInstance(Locale.getDefault());
	        StringBuffer buf = new StringBuffer();
	        buf.append(Integer.toString(cal.get(1)));
	        String month = Integer.toString(cal.get(2) + 1);
	        if(month.length() == 1)
	            month = "0" + month;
	        String day = Integer.toString(cal.get(5));
	        if(day.length() == 1)
	            day = "0" + day;
	        buf.append(month);
	        buf.append(day);
	        return buf.toString();
	    }
	   
	   
	   public static String getAddDate( int amount )
	    {
	        Calendar cal = Calendar.getInstance(Locale.getDefault());
	        		 cal.add(Calendar.DATE, amount);
	        StringBuffer buf = new StringBuffer();
	        buf.append(Integer.toString(cal.get(1)));
	        String month = Integer.toString(cal.get(2) + 1);
	        if(month.length() == 1)
	            month = "0" + month;
	        String day = Integer.toString(cal.get(5));
	        if(day.length() == 1)
	            day = "0" + day;
	        buf.append(month);
	        buf.append(day);
	        return buf.toString();
	    }	   
       
	   /**
	    * TimeMillis값을 Date형식으로 바꾸어 준다.
	    * @param time
	    * @return
	    */
	   public static String getTimeMillisDate(long time){
		return getTimeMillisDate(time, DATE_GUBUN);
       }

	   public static String getTimeMillisDate(long time, String delimeter){
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(time);
   
			return getDate(cal, delimeter);
	   }
	   
		/**
		 * 해당 날짜의 년월일을 돌려준다. - YYYYMMDD
		 * @param cal - 해당날짜의 Calendar 객체 
		 */
		public static String getDate(Calendar cal, String delimeter){
			String month, day;

	 		StringBuffer buf = new StringBuffer();

	 		buf.append(Integer.toString(cal.get(Calendar.YEAR)));
	 		if(!delimeter.equals("")) buf.append(delimeter);
	 		month = Integer.toString(cal.get(Calendar.MONTH)+1);
	 		if(month.length() == 1) month = "0" + month;
	 		day = Integer.toString(cal.get(Calendar.DATE));
	 		if(day.length() == 1) day = "0" + day;

	 		buf.append(month);
	 		if(!delimeter.equals("")) buf.append(delimeter);
	 		buf.append(day);
	 		
			return buf.toString();
		}
		
		/**
		 * TimeMillis값을 해당 날짜의 년월일을 돌려준다. - YYYYMMDDHHMI
		 * @param time
		 * @param delimeter
		 * @return
		 */
		public static String getTimeMillisDateYYYYMMDDHHMI(long time, String delimeter){
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(time);
			
			String month, day,hour,min;
			StringBuffer buf = new StringBuffer();
			
			buf.append(Integer.toString(cal.get(Calendar.YEAR)));
	 		if(!delimeter.equals("")) buf.append(delimeter);
	 		month = Integer.toString(cal.get(Calendar.MONTH)+1);
	 		if(month.length() == 1) month = "0" + month;
	 		day = Integer.toString(cal.get(Calendar.DATE));
	 		if(day.length() == 1) day = "0" + day;
	 		hour = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
	 		if(hour.length() == 1) hour = "0" + hour;
	 		min = Integer.toString(cal.get(Calendar.MINUTE));
	 		if(min.length() == 1) min = "0" + min;
	 		
	 		buf.append(month);
	 		if(!delimeter.equals("")) buf.append(delimeter);
	 		buf.append(day);
	 		if(!delimeter.equals("")) buf.append(delimeter);
	 		buf.append(hour);
	 		if(!delimeter.equals("")) buf.append(delimeter);
	 		buf.append(min);
   
			return buf.toString();
	   }

}


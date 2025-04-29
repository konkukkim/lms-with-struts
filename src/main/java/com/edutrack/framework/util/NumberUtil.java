/*
 * Created on 2003-04-15
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.edutrack.framework.util;

/**
 * @author 신정훈
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NumberUtil {
	/**
	 *	NumberFormatException 을 처리
	 *	Integer.parseInt 와 동일, 단 Exception 발생시 -1 리턴
	 */
	public static int parseInt(String src){
		return parseInt(src, -1);
	}
	
	/**
	 *	NumberFormatException 을 처리
	 *	Integer.parseInt 와 동일, 단 Exception 발생시 dft 리턴
	 */
	public static int parseInt(String src, int dft){
		int result = dft;
		
		try{
			result = Integer.parseInt(src);
		}catch(Exception e){
			result = dft;
		}
		
		return result;
	}
	
	/**
	 *	NumberFormatException 을 처리
	 *	Long.parseLong 와 동일, 단 Exception 발생시 -1l 리턴
	 */
	public static long parseLong(String src){
		return parseLong(src, -1l);
	}
	
	/**
	 *	NumberFormatException 을 처리
	 *	Long.parseLong 와 동일, 단 Exception 발생시 dft 리턴
	 */
	public static long parseLong(String src, long dft){
		long result = dft;
		
		try{
			result = Long.parseLong(src);
		}catch(Exception e){
			result = dft;
		}
		
		return result;
	}
	
	/**
	 *	NumberFormatException 을 처리
	 *	Double.parseDouble 와 동일, 단 Exception 발생시 -1l 리턴
	 */
	public static double parseDouble(String src){
		return parseDouble(src, -1l);
	}
	
	/**
	 *	NumberFormatException 을 처리
	 *	Double.parseDouble 와 동일, 단 Exception 발생시 dft 리턴
	 */
	public static double parseDouble(String src, double dft){
		double result = dft;
		
		try{
			result = Double.parseDouble(src);
		}catch(Exception e){
			result = dft;
		}
		
		return result;
	}
	
	public static int round(float a){
		return Math.round(a);
	}

	public static long round(double a){
		return Math.round(a);
	}
	
	public static double random(){
		return Math.random();
	}
	
	public static int max(int a, int b){
		return Math.max(a,b);
	}
	public static long max(long a, long b){
		return Math.max(a,b);
	}
	
	public static float max(float a,float b){
		return Math.max(a,b);
	}
	
	public static double max(double a,double b){
		return Math.max(a,b);
	}

	public static int min(int a, int b){
		return Math.max(a,b);
	}
	public static long min(long a, long b){
		return Math.max(a,b);
	}
	
	public static float min(float a,float b){
		return Math.max(a,b);
	}
	
	public static double min(double a,double b){
		return Math.max(a,b);
	}

}

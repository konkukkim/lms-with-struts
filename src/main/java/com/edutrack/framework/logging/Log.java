/*
 * Created on 2003-04-04
 *
 */
package com.edutrack.framework.logging;

/**
 * 로그관련 기능을 정의한  인터페이스 
 * 
 * @author $Author: cvs $<p><p>$Id: Log.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 *
 */
public interface Log
{
	
	/**
	 *  Trace Message를 출력해 준다.
	 * @param message
	 */
	public void trace(Object message);

	/**
	 * Debug Message를 출력해 준다. 
	 * @param message
	 */
	public void debug(Object message);
	
	/**
	 * Info Message를 출력해 준다.
	 * @param message
	 */
	public void info(Object message);
	

	/**
	 * Warning Message를 출력해 준다.
	 * @param message
	 */
	public void warn(Object message);

	/**
	 * Error Message를 출력해준다.
	 * @param message
	 */
	public void error(Object message);
	

	public void error(String message, Throwable e);
	/**
	 * Fatal Message를 출력해 준다. 
	 */
	public void fatal(Object message);
	

}
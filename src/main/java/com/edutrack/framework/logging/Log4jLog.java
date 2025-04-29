/*
 * Created on 2003-04-04
 *
 */
package com.edutrack.framework.logging;


import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Log 인터페이스의 구현 클래스
 * @author $Author: cvs $<p><p>$Id: Log4jLog.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 */
public class Log4jLog  implements Log
{
	
	private Logger logger = null;
	
	
   /*
    * 생성자 
    */    	
	public Log4jLog()
	{
		
	}
	
   /*
    * 지정한 name에 따라 Logger 클래스를 셋팅해준다.
    */    	
	public Log4jLog(String name)
	{
		this.logger = Logger.getLogger(name);
		
	}
	
   /*
    * 지정한 class에 따라 Logger 클래스를 셋팅해준다.
    */    	
	public Log4jLog(Class clazz)
	{
		this.logger = Logger.getLogger(clazz);
	}
	
   /*
    * Logger 클래스를 이용하여 Trace를 출력한다.
    */    	
	public void trace(Object message)
	{

	}

   /*
    * Logger 클래스를 이용하여 Info를 출력한다.
    */    	
	public void info(Object message)
	{
		logger.info(message);
	}

   /*
    * Logger 클래스를 이용하여 Debug를 출력한다.
    */    	
	public void debug(Object message)
	{
		logger.debug(message);

	}

   /*
    * Logger 클래스를 이용하여 Error를 출력한다.
    */    	
	public void error(Object message)
	{
		if(message instanceof SQLException){
			SQLException e = (SQLException)message;
			String msg = "SQLCode:"+e.getErrorCode()+"\n"+e.getMessage();
			logger.error(msg);
		}
		logger.error(message);

	}
	
   /*
    * Logger 클래스를 이용하여 Fatal을 출력한다.
    */    	
	public void fatal(Object message)
	{
		logger.fatal(message);		
	}
	
   /*
    * Logger 클래스를 이용하여 Warning을 출력한다.
    */    	
	public void warn(Object message)
	{
		logger.warn(message);
	}
	
	public void error(String message, Throwable e){
		logger.error(message, e);
	}
}

package com.edutrack.framework.logging;

import com.edutrack.framework.Constants;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.util.FileUtil;



/**
 * 로그(Log)객체를 얻기 위한 추상화 팩토리(abstract factory) 클래스
 * 상세 구현 팩토리클래스는 Configuration 파일에 명시하고
 * 런타임에서 동적으로 로딩한다.
 * <p>
 * configuration 파일에 등록된 LogFactory
 * 
 * <pre>
 * framework.logging.factory=framework.logging.Log4jFactory
 * include=log4j.properties
 * </pre>
 * 
 * <p>
 * 사용예제
 * <pre>
 * 
 * Log log = LogFactory.getLog(BusinessComponent.class);
 * 
 * Log mylog = LogFactory.getLog(this.getClass().getName());
 * 
 * log.debug("디버그메시지");
 * log.error("에러메시지");
 * 
 * </pre>
 * 
 */
public abstract class LogFactory implements Constants
{
	
	private static LogFactory instance = null;
	
	/**
	 * @roseuid 3E8AC51D0184
	 */
	protected LogFactory() 
	{
	}
	
	
	public abstract Log getLogInstance(String name);
	
	public abstract Log getLogInstance(Class clazz);
	
	/**
	 * Configuration 파일에 지정된 logfactory 클래스를 이용해서 
	 * 동적으로 인스턴스를 만든다.
	 * 
	 * @return
	 */
	public static LogFactory getInstance()
	{
		
		if (instance != null) return instance;
		
		// Config로부터 LogFactory 클래스명을 얻어오기 위함. 
		
		Configuration config = ConfigurationFactory.getInstance().getConfiguration();
		
		
		// Create Log Factory instance from logging properties
		
		String factoryClass = config.getString(KEY_LOG_FACTORY);
		
		LogFactory factory = null;

		factory = (LogFactory)FileUtil.newInstance(factoryClass);
			
		// create default LogFactory Implementation 
		if (factory == null)
		{
			factory = (LogFactory)FileUtil.newInstance(DEFAULT_LOG_FACTORY);				
		}

		return factory;
	}
	
	/**
	 * 지정된 이름에 해당하는 Log 클래스를 넘겨준다.
	 * @param name
	 */
	public static Log getLog(String name)
	{
		Log log = null;
		log = getInstance().getLogInstance(name);	

		return log;

	}
	
	/**
	 * 클래스에 해당하는 Log 클래스를 넘겨준다.
	 * @param name
	 */
	public static Log getLog(Class clazz)
	{
		return (getInstance().getLogInstance(clazz));
	}
}

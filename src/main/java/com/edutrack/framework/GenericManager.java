package com.edutrack.framework;

import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.logging.Log;
import com.edutrack.framework.logging.LogFactory;
//import framework.logging.*;

/**
 * 이 클래스는 프레임워크의 Manager 클래스의 상위 클래스이며,
 * 인스턴스가 생성될 때, initialize() 를 수행한다.
 * 
 * @author $Author: cvs $<p><p>$Id: GenericManager.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 *
 */
public class GenericManager implements Initializable, Disposable, Constants
{
	protected Configuration config = ConfigurationFactory.getInstance().getConfiguration();
	protected Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * 생성자
	 */
	public GenericManager() 
	{
		initialize();
	}
	
	/**
	 * 초기화 
	 */
	public void initialize() 
	{
		
	}
	
	/**
	 * 초기화 및 메모리 해제 메소드
	 */
	public void dispose() 
	{
		
	}
	
	/**
	 *  환경설정 Configuration 객체를 돌려준다.  
	 */		
	public Configuration getConfig()
	{
		return config;
	}
	
}

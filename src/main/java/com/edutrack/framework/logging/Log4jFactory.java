/*
 * Created on 2003-04-04
 *
 */
package com.edutrack.framework.logging;

import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.edutrack.framework.Constants;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;


/**
 * Log 클래스를  생성해 주는 팩토리 클래스 
 * @author $Author: cvs $<p><p>$Id: Log4jFactory.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 */
public class Log4jFactory extends LogFactory implements Constants
{
	private Hashtable instances = new Hashtable();


   /*
    * 생성자
    */    	
	public Log4jFactory()
	{
		super();
		
		Configuration config = ConfigurationFactory.getInstance().getConfiguration();
		try
		{
			String homeDir = config.getString(KEY_HOME);
			String logDir = homeDir + File.separator + "logs";
			File dir = new File(logDir);
			if(dir.exists() == false ){
				dir.mkdir(); 
			}

			Properties props = new Properties();
			props.setProperty(KEY_HOME, homeDir);
			FileInputStream fi = new FileInputStream(homeDir + File.separator + "config" + File.separator + DEFAULT_LOG4J_CONFIG_FILENAME);
			props.load(fi);
			PropertyConfigurator.configure(props);
			fi.close();
		} catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	
   /*
    * 클래스에 해당하는 Log 인스턴스를 돌려준다.
    */    	
	public Log getLogInstance(Class clazz)

	{
		Log instance = (Log) instances.get(clazz);
		if( instance != null )
			return instance;

		instance=new Log4jLog( clazz);
		instances.put( clazz, instance );
		return instance;
	}


   /*
    * 설정된 name에  해당하는 Log 인스턴스를 돌려준다.
    */    	
	public Log getLogInstance(String name)

	{
		Log instance = (Log) instances.get(name);
		if( instance != null )
			return instance;

		instance=new Log4jLog( name );
		instances.put( name, instance );
		return instance;
	}	


}

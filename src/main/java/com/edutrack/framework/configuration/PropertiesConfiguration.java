/*
 * Created on 2003-04-04
 *
 */
package com.edutrack.framework.configuration;

import java.io.IOException;
import java.io.InputStream;


/**
 * Properties 파일 형식으로 제공되는 설정파일을 지원하는 클래스
 *   
 * 
 * @author $Author: cvs $<p><p>$Id: PropertiesConfiguration.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 *
 */
public class PropertiesConfiguration extends BaseConfiguration
	implements Configuration
{
	
	public PropertiesConfiguration()
	{
		delegate = new org.apache.commons.configuration.PropertiesConfiguration();
	}
	
	public PropertiesConfiguration(String fileName)
		throws IOException
	{
		delegate = new org.apache.commons.configuration.PropertiesConfiguration(fileName);
						
	}
	
	public void load(InputStream is) throws IOException
	{
		((org.apache.commons.configuration.PropertiesConfiguration)delegate).load(is);
	}
	
	/**
	 * '/'로 시작하는 절대경로일경우, 해당 파일에 대한 프로퍼티를 읽고, 
	 * 만약 상대경로로 지정된다면, 클래스경에서 주어진 파일명에 해당하는 
	 * 리소스스트림을 찾아서 로딩한다.
	 * @param filename
	 * @throws IOException
	 */
	public void load(String filename) throws IOException
	{
		((org.apache.commons.configuration.PropertiesConfiguration)delegate).load(filename);
	}
	
	public InputStream getPropertyStream(String resourceName)
		throws IOException
	{
		return ((org.apache.commons.configuration.PropertiesConfiguration)delegate).getPropertyStream(resourceName);
	}


}

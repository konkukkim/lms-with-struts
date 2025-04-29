/*
 * Created on 2003-04-04
 *
 */
package com.edutrack.framework.configuration;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.DOM4JConfiguration;

/**
 * XML 형식의 Configuration 을 지원하기 위한 클래스 
 * 
 * @author $Author: cvs $<p><p>$Id: XMLConfiguration.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 *
 */
public class XMLConfiguration extends BaseConfiguration
	implements Configuration
{
	//protected DOM4JConfiguration delegate = null;
	
	public XMLConfiguration()
	{
		delegate = new DOM4JConfiguration();
	}
	
	public XMLConfiguration(String fileName)
		throws Exception
	{
		delegate = new DOM4JConfiguration(fileName);
	}
	
	public XMLConfiguration(File file) throws Exception
	{
		delegate = new DOM4JConfiguration(file);
	}
	
	public void setFileName(String fileName)
	{
		((DOM4JConfiguration)delegate).setFileName(fileName);
	}

	public synchronized void save() throws IOException
	{
		((DOM4JConfiguration)delegate).save();
	}
	
	public void setAutoSave(boolean autoSave)
	{
		((DOM4JConfiguration)delegate).setAutoSave(autoSave);
	}
	

}

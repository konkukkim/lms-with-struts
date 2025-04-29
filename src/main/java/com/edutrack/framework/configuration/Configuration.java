/*
 * Created on 2003-04-03
 *
 */
package com.edutrack.framework.configuration;

import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;


/**
 * Configuration 파일에서 설정정보를 추출하기 위한 메소드 정의 인터페이스
 *  
 * 
 * @author $Author: cvs $<p><p>$Id: Configuration.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 *
 */
public interface Configuration
{

	public Configuration subset(String prefix);
	
	public Iterator getKeys();
	
	public Iterator getKeys(String prefix);
	
	public void addProperty(String key, Object token);

	public Object getProperty(String key);
	
	public Properties getProperties(String key);
	
	public Properties getProperties();
	
	public int getInt(String key);
	
	public int getInt(String key, int def);
	
	public long getLong(String key);
	
	public long getLong(String key, long def);
	
	public float getFloat(String key);
	
	public float getFloat(String key, float def);
	
	public double getDouble(String key);
	
	public double getDouble(String key, double def);
	
	public byte getByte(String key);
	
	public byte getByte(String key, byte def);
	
	public boolean getBoolean(String key);
	
	public boolean getBoolean(String key, boolean def);
	
	public short getShort(String key);
	
	public short getShort(String key, short def);
	
	public String getString(String key);
	
	public String getString(String key, String def);
	
	public String[] getStringArray(String key);
	
	public Vector getVector(String key);
	
	public Vector getVector(String key, Vector def);
	
}

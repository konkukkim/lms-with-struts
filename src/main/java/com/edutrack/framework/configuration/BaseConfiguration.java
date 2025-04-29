/*
 * Created on 2003. 4. 14.
 *
 */
package com.edutrack.framework.configuration;

import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

/**
 * Configuration을 구현한 상위 클래스로 내부적으로 Jakarta commons configuration 
 * 의 클래스를 프록시로 사용함.
 * 
 * @author $Author: cvs $<p><p>$Id: BaseConfiguration.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 *
 */
public class BaseConfiguration implements Configuration
{
	protected org.apache.commons.configuration.Configuration delegate = null;
	
	public BaseConfiguration()
	{
		delegate = new org.apache.commons.configuration.BaseConfiguration();
	}

	public BaseConfiguration(org.apache.commons.configuration.Configuration delegate)
	{
		this.delegate = delegate;
	}

	public void addProperty(String key, Object token)
	{
		delegate.addProperty(key,token);
	}
	
	public void clearProperty(String key)
	{
		delegate.clearProperty(key);
	}
	
	public Iterator getKeys()
	{
		return delegate.getKeys();
	}
	
	public Iterator getKeys(String prefix)
	{
		return delegate.getKeys(prefix);
	}
	
	public Object getProperty(String key)
	{
		return delegate.getProperty(key);
	}
	
	public Properties getProperties(String key)
	{
		return delegate.getProperties(key);
	}
	
	public Properties getProperties()
	{
		Properties props = new Properties();
		Iterator iter = delegate.getKeys();
		
		while(iter.hasNext())
		{
			String key = (String)iter.next();
			props.setProperty(key, delegate.getString(key));
		}
		
		return props;
	}
		
	public Configuration subset(String prefix)
	{
		Configuration subset = new BaseConfiguration();
		
		org.apache.commons.configuration.Configuration set = delegate.subset(prefix);
		
		if (set != null)
		{
			Iterator keys = set.getKeys(prefix);
			while(keys.hasNext())
			{
				String key = (String)keys.next();
				String value = set.getString(key);
				subset.addProperty(key, value);
			}
		}
		
		return subset;
	}
			
	/* (non-Javadoc)
	 * @see framework.configuration.Configuration#getInt(java.lang.String)
	 */
	public int getInt(String key)
	{
		return delegate.getInt(key);
	}
	
	public int getInt(String key, int def)
	{
		return delegate.getInt(key, def);
	}

	/* (non-Javadoc)
	 * @see framework.configuration.Configuration#getFloat(java.lang.String)
	 */
	public float getFloat(String key)
	{

		return delegate.getFloat(key);
	}
	
	public float getFloat(String key, float def)
	{
		return delegate.getFloat(key, def);
	}

	/* (non-Javadoc)
	 * @see framework.configuration.Configuration#getDouble(java.lang.String)
	 */
	public double getDouble(String key)
	{
		return delegate.getDouble(key);
	}
	
	public double getDouble(String key, double def)
	{
		return delegate.getDouble(key,def);
		
	}
	/* (non-Javadoc)
	 * @see framework.configuration.Configuration#getByte(java.lang.String)
	 */
	public byte getByte(String key)
	{
		return delegate.getByte(key);
	}
	
	public byte getByte(String key, byte def)
	{
		return delegate.getByte(key, def);
	}

	/* (non-Javadoc)
	 * @see framework.configuration.Configuration#getBoolean(java.lang.String)
	 */
	public boolean getBoolean(String key)
	{
		return delegate.getBoolean(key);
	}
	
	public boolean getBoolean(String key, boolean def)
	{
		return delegate.getBoolean(key,def);
	}

	/* (non-Javadoc)
	 * @see framework.configuration.Configuration#getShort(java.lang.String)
	 */
	public short getShort(String key)
	{
		return delegate.getShort(key);
	}
	
	public short getShort(String key, short def)
	{
		return delegate.getShort(key, def);
	}

	/* (non-Javadoc)
	 * @see framework.configuration.Configuration#getString(java.lang.String)
	 */
	public String getString(String key)
	{
		return delegate.getString(key);
	}
	
	public String getString(String key, String def)
	{
		return delegate.getString(key, def);
	}

	/* (non-Javadoc)
	 * @see framework.configuration.Configuration#getStringArray(java.lang.String)
	 */
	public String[] getStringArray(String key)
	{

		return delegate.getStringArray(key);
	}	
	
	public Vector getVector(String key)
	{
		return delegate.getVector(key);
		
	}
	
	public Vector getVector(String key, Vector def)
	{
		return delegate.getVector(key, def);
	}
	
	public long getLong(String key)
	{
		return delegate.getLong(key);
	}
	
	public long getLong(String key, long def)
	{
		return delegate.getLong(key, def);
	}
	

}

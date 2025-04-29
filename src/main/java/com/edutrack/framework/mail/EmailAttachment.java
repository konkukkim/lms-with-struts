package com.edutrack.framework.mail;

import java.net.URL;

public class EmailAttachment
{
	public final static String ATTACHMENT = javax.mail.Part.ATTACHMENT;
	public final static String INLINE = javax.mail.Part.INLINE;

	/** The name of this attachment. */
	private String name = "";

	/** The description of this attachment. */
	private String description = "";

	/** The full path to this attachment (ie c:/path/to/file.jpg). */
	private String path = "";

	/** The HttpURI where the file can be got. */
	private URL url = null;

	/** The disposition. */
	private String disposition = ATTACHMENT;

	
	public EmailAttachment(String name, String description, String path, URL url)
	{
		this.name = name;
		this.description = description;
		this.path = path;
		this.url = url;
	}
	/**
	 * Get the description.
	 *
	 * @return description
	 */
     
	public String getDescription()
	{
		return description;
	}

	/**
	 * Get the name.
	 *
	 * @return A String.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Get the path.
	 *
	 * @return A String.
	 */
	public String getPath()
	{
		return path;
	}

	/**
	 * Get the URL.
	 *
	 * @return A URL.
	 */
	public URL getURL()
	{
		return url;
	}

	/**
	 * Get the disposition.
	 *
	 * @return A String.
	 */
	public String getDisposition()
	{
		return disposition;
	}

	/**
	 * Set the description.
	 *
	 * @param desc A String.
	 */
	public void setDescription(String desc)
	{
		this.description = desc;
	}

	/**
	 * Set the name.
	 *
	 * @param name A String.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Set the path.
	 *
	 * @param path A String.
	 */
	public void setPath(String path)
	{
		this.path = path;
	}

	/**
	 * Set the URL.
	 *
	 * @param url A URL.
	 */
	public void setURL(URL url)
	{
		this.url = url;
	}

	/**
	 * Set the disposition.
	 *
	 * @param disposition A String.
	 */
	public void setDisposition(String disposition)
	{
		this.disposition = disposition;
	}
}


package com.edutrack.framework.persist.util;
/*
 * Created on 2004. 7. 7.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DbInfo {
    private String root = "";
    private String driver = "";
    private String url = "";
    private String user = "";
    private String passwd = "";
    private String schema = "";
	/**
	 * 
	 */
	public DbInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return Returns the driver.
	 */
	public String getDriver() {
		return driver;
	}
	/**
	 * @param driver The driver to set.
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}
	/**
	 * @return Returns the passwd.
	 */
	public String getPasswd() {
		return passwd;
	}
	/**
	 * @param passwd The passwd to set.
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	/**
	 * @return Returns the root.
	 */
	public String getRoot() {
		return root;
	}
	/**
	 * @param root The root to set.
	 */
	public void setRoot(String root) {
		this.root = root;
	}
	/**
	 * @return Returns the schema.
	 */
	public String getSchema() {
		return schema;
	}
	/**
	 * @param schema The schema to set.
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}
	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return Returns the user.
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user The user to set.
	 */
	public void setUser(String user) {
		this.user = user;
	}
}

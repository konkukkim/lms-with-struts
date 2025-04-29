/*
 * Created on 2004. 11. 5.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.connstatus.dto;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CourseConnectResDTO {
     protected String name = "";
     protected ConnectResDTO list = null;
	/**
	 * 
	 */
	public CourseConnectResDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return Returns the list.
	 */
	public ConnectResDTO getList() {
		return list;
	}
	/**
	 * @param list The list to set.
	 */
	public void setList(ConnectResDTO list) {
		this.list = list;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
}

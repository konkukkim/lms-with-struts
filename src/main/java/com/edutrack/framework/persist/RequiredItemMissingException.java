/*
 * Created on 2003-05-30
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.edutrack.framework.persist;

/**
 * @author 신정훈
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RequiredItemMissingException extends DAOException {

	private String missginItems = null;
	/**
	 * 
	 */
	public RequiredItemMissingException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param s
	 */
	public RequiredItemMissingException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	public RequiredItemMissingException(String s, String items) {
		super(s);
		this.missginItems = items;
		// TODO Auto-generated constructor stub
	}
	
	public void setMissingItems(String items){
		this.missginItems = items;
	}
	
	public String getMessage(){
		return "Required Items : " + this.missginItems;
	}

}

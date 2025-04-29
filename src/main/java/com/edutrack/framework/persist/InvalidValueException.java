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
public class InvalidValueException extends DAOException {

	private String missginItems = null;
	/**
	 * 
	 */
	public InvalidValueException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param s
	 */
	public InvalidValueException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	public InvalidValueException(String s, String items) {
		super(s);
		this.missginItems = items;
		// TODO Auto-generated constructor stub
	}
	
	public void setInvalidValue(String items){
		this.missginItems = items;
	}
	
	public String getMessage(){
		return "InvalidValue : " + this.missginItems;
	}

}

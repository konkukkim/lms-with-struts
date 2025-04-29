/*
 * Created on 2004. 10. 6.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.user.action;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ParseParam {
    protected int lineNo = 0;
    protected String userId = "";
    protected String userName = "";
    protected String juminNo = "";
    protected String errorType = "";
    
	/**
	 * 
	 */
	public ParseParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return Returns the lineNo.
	 */
	public int getLineNo() {
		return lineNo;
	}
	/**
	 * @param lineNo The lineNo to set.
	 */
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}
	
	/**
	 * @return Returns the errorType.
	 */
	public String getErrorType() {
		return errorType;
	}
	/**
	 * @param errorType The errorType to set.
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	
	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return Returns the juminNo.
	 */
	public String getJuminNo() {
		return juminNo;
	}
	/**
	 * @param juminNo The juminNo to set.
	 */
	public void setJuminNo(String juminNo) {
		this.juminNo = juminNo;
	}
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}

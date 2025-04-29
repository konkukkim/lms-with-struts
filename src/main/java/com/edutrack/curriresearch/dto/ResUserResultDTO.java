/*
 * Created on 2004. 10. 28.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curriresearch.dto;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResUserResultDTO {
    protected String userId="";
    protected String userName="";
    protected String sexType="";
    protected int age = 0;
   	/**
	 * 
	 */
	public ResUserResultDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return Returns the age.
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age The age to set.
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return Returns the sexType.
	 */
	public String getSexType() {
		return sexType;
	}
	/**
	 * @param sexType The sexType to set.
	 */
	public void setSexType(String sexType) {
		this.sexType = sexType;
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

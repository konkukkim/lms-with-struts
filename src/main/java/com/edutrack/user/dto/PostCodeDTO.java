/**
 * @(#)LmPostCodeDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.user.dto;

/**
 * TB_POSTCODE 테이블 Dto 클래스.
 *
 ************************************************
 * DATE         AUTHOR      DESCRIPTION
 * ----------------------------------------------
 * 2004. 7. 7.  mediopia       Initial Release
 ************************************************
 *
 * @author      mediopia
 * @version     1.0
 * @since       2004. 7. 7.
 */
public class PostCodeDTO {

    protected int postId=0;
    protected String postCode="";
    protected String addr="";
    protected String fullAddr="";
    
	/**
	 * @return Returns the addr.
	 */
	public String getAddr() {
		return addr;
	}
	/**
	 * @param addr The addr to set.
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	/**
	 * @return Returns the fullAddr.
	 */
	public String getFullAddr() {
		return fullAddr;
	}
	/**
	 * @param fullAddr The fullAddr to set.
	 */
	public void setFullAddr(String fullAddr) {
		this.fullAddr = fullAddr;
	}
	/**
	 * @return Returns the postCode.
	 */
	public String getPostCode() {
		return postCode;
	}
	/**
	 * @param postCode The postCode to set.
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	/**
	 * @return Returns the postId.
	 */
	public int getPostId() {
		return postId;
	}
	/**
	 * @param postId The postId to set.
	 */
	public void setPostId(int postId) {
		this.postId = postId;
	}
}

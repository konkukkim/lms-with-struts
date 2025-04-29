/*
 * Created on 2004. 12. 15.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.community.dto;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommInviteDTO {
	protected int seqNo = 0; 
	protected String senderId ="";
	protected String senderName ="";
	protected String subject =""; 
	protected String contents =""; 
	protected int commId =0;
	protected String commName ="";
	/**
	 * 
	 */
	public CommInviteDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @return Returns the commId.
	 */
	public int getCommId() {
		return commId;
	}
	/**
	 * @param commId The commId to set.
	 */
	public void setCommId(int commId) {
		this.commId = commId;
	}
	/**
	 * @return Returns the commName.
	 */
	public String getCommName() {
		return commName;
	}
	/**
	 * @param commName The commName to set.
	 */
	public void setCommName(String commName) {
		this.commName = commName;
	}
	/**
	 * @return Returns the contents.
	 */
	public String getContents() {
		return contents;
	}
	/**
	 * @param contents The contents to set.
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	/**
	 * @return Returns the senderId.
	 */
	public String getSenderId() {
		return senderId;
	}
	/**
	 * @param senderId The senderId to set.
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	/**
	 * @return Returns the seqNo.
	 */
	public int getSeqNo() {
		return seqNo;
	}
	/**
	 * @param seqNo The seqNo to set.
	 */
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return Returns the userName.
	 */
	public String getSenderName() {
		return senderName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setSenderName(String userName) {
		this.senderName = userName;
	}
}

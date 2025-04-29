/**
 * @(#)MessageDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.message.dto;


/**
 * MESSAGE 테이블 Dto 클래스.
 *
 ************************************************
 * DATE         AUTHOR      DESCRIPTION
 * ----------------------------------------------
 * 2004. 9. 13.  mediopia       Initial Release
 ************************************************
 *
 * @author      mediopia
 * @version     1.0
 * @since       2004. 9. 13.
 */
public class MessageDTO {

    protected String systemCode;
    protected String senderId;
    protected int seqNo;
    protected String senderName;
    protected String receiverId;
    protected String receiverName;
    protected String editorType;
    protected String subject;
    protected String keyword;
    protected String contents;
    protected String rfileName;
    protected String sfileName;
    protected String filePath;
    protected String fileSize;
    protected String sendDate;
    protected String senderDelYn;
    protected String receiveDate;
    protected String receiverDelYn;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    
    /**추가 **/
    protected String receiveType;
    protected String receiveId;

    /**
     * get systemCode.
     *
     * @return      systemCode
     */
    public String getSystemCode() {
        return  this.systemCode;
    }

    /**
     * set systemCode.
     *
     * @param       systemCode
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * get senderId.
     *
     * @return      senderId
     */
    public String getSenderId() {
        return  this.senderId;
    }

    /**
     * set senderId.
     *
     * @param       senderId
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     * get seqNo.
     *
     * @return      seqNo
     */
    public int getSeqNo() {
        return  this.seqNo;
    }

    /**
     * set seqNo.
     *
     * @param       seqNo
     */
    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * get senderName.
     *
     * @return      senderName
     */
    public String getSenderName() {
        return  this.senderName;
    }

    /**
     * set senderName.
     *
     * @param       senderName
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * get receiverId.
     *
     * @return      receiverId
     */
    public String getReceiverId() {
        return  this.receiverId;
    }

    /**
     * set receiverId.
     *
     * @param       receiverId
     */
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * get receiverName.
     *
     * @return      receiverName
     */
    public String getReceiverName() {
        return  this.receiverName;
    }

    /**
     * set receiverName.
     *
     * @param       receiverName
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * get editorType.
     *
     * @return      editorType
     */
    public String getEditorType() {
        return  this.editorType;
    }

    /**
     * set editorType.
     *
     * @param       editorType
     */
    public void setEditorType(String editorType) {
        this.editorType = editorType;
    }

    /**
     * get subject.
     *
     * @return      subject
     */
    public String getSubject() {
        return  this.subject;
    }

    /**
     * set subject.
     *
     * @param       subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * get keyword.
     *
     * @return      keyword
     */
    public String getKeyword() {
        return  this.keyword;
    }

    /**
     * set keyword.
     *
     * @param       keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * get contents.
     *
     * @return      contents
     */
    public String getContents() {
        return  this.contents;
    }

    /**
     * set contents.
     *
     * @param       contents
     */
    public void setContents(String contents) {
        this.contents = contents;
    }

    /**
     * get rfileName.
     *
     * @return      rfileName
     */
    public String getRfileName() {
        return  this.rfileName;
    }

    /**
     * set rfileName.
     *
     * @param       rfileName
     */
    public void setRfileName(String rfileName) {
        this.rfileName = rfileName;
    }

    /**
     * get sfileName.
     *
     * @return      sfileName
     */
    public String getSfileName() {
        return  this.sfileName;
    }

    /**
     * set sfileName.
     *
     * @param       sfileName
     */
    public void setSfileName(String sfileName) {
        this.sfileName = sfileName;
    }

    /**
     * get filePath.
     *
     * @return      filePath
     */
    public String getFilePath() {
        return  this.filePath;
    }

    /**
     * set filePath.
     *
     * @param       filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * get fileSize.
     *
     * @return      fileSize
     */
    public String getFileSize() {
        return  this.fileSize;
    }

    /**
     * set fileSize.
     *
     * @param       fileSize
     */
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * get sendDate.
     *
     * @return      sendDate
     */
    public String getSendDate() {
        return  this.sendDate;
    }

    /**
     * set sendDate.
     *
     * @param       sendDate
     */
    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * get senderDelYn.
     *
     * @return      senderDelYn
     */
    public String getSenderDelYn() {
        return  this.senderDelYn;
    }

    /**
     * set senderDelYn.
     *
     * @param       senderDelYn
     */
    public void setSenderDelYn(String senderDelYn) {
        this.senderDelYn = senderDelYn;
    }

    /**
     * get receiveDate.
     *
     * @return      receiveDate
     */
    public String getReceiveDate() {
        return  this.receiveDate;
    }

    /**
     * set receiveDate.
     *
     * @param       receiveDate
     */
    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * get receiverDelYn.
     *
     * @return      receiverDelYn
     */
    public String getReceiverDelYn() {
        return  this.receiverDelYn;
    }

    /**
     * set receiverDelYn.
     *
     * @param       receiverDelYn
     */
    public void setReceiverDelYn(String receiverDelYn) {
        this.receiverDelYn = receiverDelYn;
    }

    /**
     * get regId.
     *
     * @return      regId
     */
    public String getRegId() {
        return  this.regId;
    }

    /**
     * set regId.
     *
     * @param       regId
     */
    public void setRegId(String regId) {
        this.regId = regId;
    }

    /**
     * get regDate.
     *
     * @return      regDate
     */
    public String getRegDate() {
        return  this.regDate;
    }

    /**
     * set regDate.
     *
     * @param       regDate
     */
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    /**
     * get modId.
     *
     * @return      modId
     */
    public String getModId() {
        return  this.modId;
    }

    /**
     * set modId.
     *
     * @param       modId
     */
    public void setModId(String modId) {
        this.modId = modId;
    }

    /**
     * get modDate.
     *
     * @return      modDate
     */
    public String getModDate() {
        return  this.modDate;
    }

    /**
     * set modDate.
     *
     * @param       modDate
     */
    public void setModDate(String modDate) {
        this.modDate = modDate;
    }

    
	/**
	 * @return Returns the receiveId.
	 */
	public String getReceiveId() {
		return receiveId;
	}
	/**
	 * @param receiveId The receiveId to set.
	 */
	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}
	/**
	 * @return Returns the receiveType.
	 */
	public String getReceiveType() {
		return receiveType;
	}
	/**
	 * @param receiveType The receiveType to set.
	 */
	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}
    /**
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return  "systemCode="+systemCode+",senderId="+senderId+",seqNo="+seqNo+",senderName="+senderName+",receiverId="+receiverId
               +",receiverName="+receiverName+",editorType="+editorType+",subject="+subject+",keyword="+keyword+",contents="+contents
               +",rfileName="+rfileName+",sfileName="+sfileName+",filePath="+filePath+",fileSize="+fileSize+",sendDate="+sendDate
               +",senderDelYn="+senderDelYn+",receiveDate="+receiveDate+",receiverDelYn="+receiverDelYn+",regId="+regId+",regDate="+regDate
               +",modId="+modId+",modDate="+modDate;
    }

    }

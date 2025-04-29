/**
 * @(#)FaqContentsDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.faq.dto;


/**
 * FAQ_CONTENTS 테이블 Dto 클래스.
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
public class FaqContentsDTO {

    protected String systemCode;
    protected int seqNo;
    protected int cateId;
    protected String subject;
    protected String contents;
    protected String keyword;
    protected String rfileName;
    protected String sfileName;
    protected String filePath;
    protected String fileSize;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    
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
     * get cateId.
     *
     * @return      cateId
     */
    public int getCateId() {
        return  this.cateId;
    }

    /**
     * set cateId.
     *
     * @param       cateId
     */
    public void setCateId(int cateId) {
        this.cateId = cateId;
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
	 * @return Returns the keyword.
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @param keyword The keyword to set.
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return  "systemCode="+systemCode+",seqNo="+seqNo+",cateId="+cateId+",subject="+subject+",contents="+contents
               +",rfileName="+rfileName+",sfileName="+sfileName+",filePath="+filePath+",fileSize="+fileSize+",regId="+regId
               +",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

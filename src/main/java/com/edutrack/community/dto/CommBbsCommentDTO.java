/**
 * @(#)BbsCommentDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.community.dto;


/**
 * BBS_COMMENT 테이블 Dto 클래스.
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
public class CommBbsCommentDTO {

    protected String systemCode;
    protected int commId;
    protected int bbsId;
    protected int seqNo;
    protected int commNo;
    protected String contents;
    protected String emoticonNum;
    protected String regId;
    protected String regName;
    protected String regEmail;
    protected String regPasswd;
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
     * 
     * @return
     */
    public int getCommId(){
    	return this.commId;
    }
    
    /**
     * 
     * @param commId
     */
    public void setCommId(int commId) {
        this.commId = commId;
    }
    
    
    /**
     * get bbsId.
     *
     * @return      bbsId
     */
    public int getBbsId() {
        return  this.bbsId;
    }

    /**
     * set bbsId.
     *
     * @param       bbsId
     */
    public void setBbsId(int bbsId) {
        this.bbsId = bbsId;
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
     * get commNo.
     *
     * @return      commNo
     */
    public int getCommNo() {
        return  this.commNo;
    }

    /**
     * set commNo.
     *
     * @param       commNo
     */
    public void setCommNo(int commNo) {
        this.commNo = commNo;
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
     * get emoticonNum.
     *
     * @return      emoticonNum
     */
    public String getEmoticonNum() {
        return  this.emoticonNum;
    }

    /**
     * set emoticonNum.
     *
     * @param       emoticonNum
     */
    public void setEmoticonNum(String emoticonNum) {
        this.emoticonNum = emoticonNum;
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
     * get regName.
     *
     * @return      regName
     */
    public String getRegName() {
        return  this.regName;
    }

    /**
     * set regName.
     *
     * @param       regName
     */
    public void setRegName(String regName) {
        this.regName = regName;
    }

    /**
     * get regEmail.
     *
     * @return      regEmail
     */
    public String getRegEmail() {
        return  this.regEmail;
    }

    /**
     * set regEmail.
     *
     * @param       regEmail
     */
    public void setRegEmail(String regEmail) {
        this.regEmail = regEmail;
    }

    /**
     * get regPasswd.
     *
     * @return      regPasswd
     */
    public String getRegPasswd() {
        return  this.regPasswd;
    }

    /**
     * set regPasswd.
     *
     * @param       regPasswd
     */
    public void setRegPasswd(String regPasswd) {
        this.regPasswd = regPasswd;
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
        return  "systemCode="+systemCode+",bbsId="+bbsId+",seqNo="+seqNo+",commNo="+commNo+",contents="+contents
               +",emoticonNum="+emoticonNum+",regId="+regId+",regName="+regName+",regEmail="+regEmail+",regPasswd="+regPasswd
               +",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

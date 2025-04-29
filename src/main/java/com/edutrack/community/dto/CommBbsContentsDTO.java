/**
 * @(#)CommBbsInfoDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.community.dto;


/**
 * COMM_BBS_CONTENTS 테이블 Dto 클래스.
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
public class CommBbsContentsDTO {

    protected String systemCode;
    protected int commId;
    protected int bbsId;
    protected int seqNo;
    protected int bbsNo;
    protected int depthNo;
    protected int orderNo;
    protected int parentNo;
    protected String editorType;
    protected String contentsType;
    protected String subject;
    protected String keyword;
    protected String contents;
    protected String rfileName1;
    protected String sfileName1;
    protected String filePath1;
    protected String fileSize1;
    protected String rfileName2;
    protected String sfileName2;
    protected String filePath2;
    protected String fileSize2;
    protected String rfileName3;
    protected String sfileName3;
    protected String filePath3;
    protected String fileSize3;
    protected int hitNo;
    protected String expireDate;
    protected String target;
    protected String regId;
    protected String regName;
    protected String regEmail;
    protected String regPasswd;
    protected String regDate;
    protected String modId;
    protected String modDate;
    protected String nickName;
    protected int commCnt;

    /**
	 * @return the commCnt
	 */
	public int getCommCnt() {
		return commCnt;
	}

	/**
	 * @param commCnt the commCnt to set
	 */
	public void setCommCnt(int commCnt) {
		this.commCnt = commCnt;
	}

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

    public int getCommId() {
        return  this.commId;
    }
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
     * get bbsNo.
     *
     * @return      bbsNo
     */
    public int getBbsNo() {
        return  this.bbsNo;
    }

    /**
     * set bbsNo.
     *
     * @param       bbsNo
     */
    public void setBbsNo(int bbsNo) {
        this.bbsNo = bbsNo;
    }

    /**
     * get depthNo.
     *
     * @return      depthNo
     */
    public int getDepthNo() {
        return  this.depthNo;
    }

    /**
     * set depthNo.
     *
     * @param       depthNo
     */
    public void setDepthNo(int depthNo) {
        this.depthNo = depthNo;
    }

    /**
     * get orderNo.
     *
     * @return      orderNo
     */
    public int getOrderNo() {
        return  this.orderNo;
    }

    /**
     * set orderNo.
     *
     * @param       orderNo
     */
    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * get parentNo.
     *
     * @return      parentNo
     */
    public int getParentNo() {
        return  this.parentNo;
    }

    /**
     * set parentNo.
     *
     * @param       parentNo
     */
    public void setParentNo(int parentNo) {
        this.parentNo = parentNo;
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
     * get contentsType.
     *
     * @return      contentsType
     */
    public String getContentsType() {
        return  this.contentsType;
    }

    /**
     * set contentsType.
     *
     * @param       contentsType
     */
    public void setContentsType(String contentsType) {
        this.contentsType = contentsType;
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
     * get rfileName1.
     *
     * @return      rfileName1
     */
    public String getRfileName1() {
        return  this.rfileName1;
    }

    /**
     * set rfileName1.
     *
     * @param       rfileName1
     */
    public void setRfileName1(String rfileName1) {
        this.rfileName1 = rfileName1;
    }

    /**
     * get sfileName1.
     *
     * @return      sfileName1
     */
    public String getSfileName1() {
        return  this.sfileName1;
    }

    /**
     * set sfileName1.
     *
     * @param       sfileName1
     */
    public void setSfileName1(String sfileName1) {
        this.sfileName1 = sfileName1;
    }

    /**
     * get filePath1.
     *
     * @return      filePath1
     */
    public String getFilePath1() {
        return  this.filePath1;
    }

    /**
     * set filePath1.
     *
     * @param       filePath1
     */
    public void setFilePath1(String filePath1) {
        this.filePath1 = filePath1;
    }

    /**
     * get fileSize1.
     *
     * @return      fileSize1
     */
    public String getFileSize1() {
        return  this.fileSize1;
    }

    /**
     * set fileSize1.
     *
     * @param       fileSize1
     */
    public void setFileSize1(String fileSize1) {
        this.fileSize1 = fileSize1;
    }

    /**
     * get rfileName2.
     *
     * @return      rfileName2
     */
    public String getRfileName2() {
        return  this.rfileName2;
    }

    /**
     * set rfileName2.
     *
     * @param       rfileName2
     */
    public void setRfileName2(String rfileName2) {
        this.rfileName2 = rfileName2;
    }

    /**
     * get sfileName2.
     *
     * @return      sfileName2
     */
    public String getSfileName2() {
        return  this.sfileName2;
    }

    /**
     * set sfileName2.
     *
     * @param       sfileName2
     */
    public void setSfileName2(String sfileName2) {
        this.sfileName2 = sfileName2;
    }

    /**
     * get filePath2.
     *
     * @return      filePath2
     */
    public String getFilePath2() {
        return  this.filePath2;
    }

    /**
     * set filePath2.
     *
     * @param       filePath2
     */
    public void setFilePath2(String filePath2) {
        this.filePath2 = filePath2;
    }

    /**
     * get fileSize2.
     *
     * @return      fileSize2
     */
    public String getFileSize2() {
        return  this.fileSize2;
    }

    /**
     * set fileSize2.
     *
     * @param       fileSize2
     */
    public void setFileSize2(String fileSize2) {
        this.fileSize2 = fileSize2;
    }

    /**
     * get rfileName3.
     *
     * @return      rfileName3
     */
    public String getRfileName3() {
        return  this.rfileName3;
    }

    /**
     * set rfileName3.
     *
     * @param       rfileName3
     */
    public void setRfileName3(String rfileName3) {
        this.rfileName3 = rfileName3;
    }

    /**
     * get sfileName3.
     *
     * @return      sfileName3
     */
    public String getSfileName3() {
        return  this.sfileName3;
    }

    /**
     * set sfileName3.
     *
     * @param       sfileName3
     */
    public void setSfileName3(String sfileName3) {
        this.sfileName3 = sfileName3;
    }

    /**
     * get filePath3.
     *
     * @return      filePath3
     */
    public String getFilePath3() {
        return  this.filePath3;
    }

    /**
     * set filePath3.
     *
     * @param       filePath3
     */
    public void setFilePath3(String filePath3) {
        this.filePath3 = filePath3;
    }

    /**
     * get fileSize3.
     *
     * @return      fileSize3
     */
    public String getFileSize3() {
        return  this.fileSize3;
    }

    /**
     * set fileSize3.
     *
     * @param       fileSize3
     */
    public void setFileSize3(String fileSize3) {
        this.fileSize3 = fileSize3;
    }

    /**
     * get hitNo.
     *
     * @return      hitNo
     */
    public int getHitNo() {
        return  this.hitNo;
    }

    /**
     * set hitNo.
     *
     * @param       hitNo
     */
    public void setHitNo(int hitNo) {
        this.hitNo = hitNo;
    }

    /**
     * get expireDate.
     *
     * @return      expireDate
     */
    public String getExpireDate() {
        return  this.expireDate;
    }

    /**
     * set expireDate.
     *
     * @param       expireDate
     */
    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * get target.
     *
     * @return      target
     */
    public String getTarget() {
        return  this.target;
    }

    /**
     * set target.
     *
     * @param       target
     */
    public void setTarget(String target) {
        this.target = target;
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
     * get nickName
     *
     * @return 		nickName
     */
    public String getNickName() {
    	return this.nickName;
    }

    /**
     * set nickName
     *
     * @param 		nickName
     */
    public void setNickName(String nickName) {
    	this.nickName = nickName;
    }

    /**
     * toString
     *
     * @return  String
     */
    public String toString() {
        return  "systemCode="+systemCode+",bbsId="+bbsId+",seqNo="+seqNo+",bbsNo="+bbsNo
               +",depthNo="+depthNo+",orderNo="+orderNo+",parentNo="+parentNo+",editorType="+editorType+",contentsType="+contentsType
               +",subject="+subject+",keyword="+keyword+",contents="+contents+",rfileName1="+rfileName1+",sfileName1="+sfileName1
               +",filePath1="+filePath1+",fileSize1="+fileSize1+",rfileName2="+rfileName2+",sfileName2="+sfileName2+",filePath2="+filePath2
               +",fileSize2="+fileSize2+",rfileName3="+rfileName3+",sfileName3="+sfileName3+",filePath3="+filePath3+",fileSize3="+fileSize3
               +",hitNo="+hitNo+",expireDate="+expireDate+",target="+target+",regId="+regId+",regName="+regName
               +",regEmail="+regEmail+",regPasswd="+regPasswd+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

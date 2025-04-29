/**
 * @(#)CurriResAnswerDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.curriresearch.dto;


/**
 * CURRI_RES_ANSWER 테이블 Dto 클래스.
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
public class CurriResAnswerDTO {

    protected String systemCode="";
    protected String curriCode="";
    protected int curriYear;
    protected int curriTerm;
    protected String userId="";
    protected int resId=0;
    protected int resNo=0;
    protected String contents="";
    protected String contentsType="";
    protected String example[]=null;
    protected String answer="";
    protected String regId="";
    protected String regDate="";
    protected String modId="";
    protected String modDate="";

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
     * get curriCode.
     *
     * @return      curriCode
     */
    public String getCurriCode() {
        return  this.curriCode;
    }

    /**
     * set curriCode.
     *
     * @param       curriCode
     */
    public void setCurriCode(String curriCode) {
        this.curriCode = curriCode;
    }

    /**
     * get curriYear.
     *
     * @return      curriYear
     */
    public int getCurriYear() {
        return  this.curriYear;
    }

    /**
     * set curriYear.
     *
     * @param       curriYear
     */
    public void setCurriYear(int curriYear) {
        this.curriYear = curriYear;
    }

    /**
     * get curriTerm.
     *
     * @return      curriTerm
     */
    public int getCurriTerm() {
        return  this.curriTerm;
    }

    /**
     * set curriTerm.
     *
     * @param       curriTerm
     */
    public void setCurriTerm(int curriTerm) {
        this.curriTerm = curriTerm;
    }

    /**
     * get userId.
     *
     * @return      userId
     */
    public String getUserId() {
        return  this.userId;
    }

    /**
     * set userId.
     *
     * @param       userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * get resId.
     *
     * @return      resId
     */
    public int getResId() {
        return  this.resId;
    }

    /**
     * set resId.
     *
     * @param       resId
     */
    public void setResId(int resId) {
        this.resId = resId;
    }

    /**
     * get resNo.
     *
     * @return      resNo
     */
    public int getResNo() {
        return  this.resNo;
    }

    /**
     * set resNo.
     *
     * @param       resNo
     */
    public void setResNo(int resNo) {
        this.resNo = resNo;
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
     * get example1.
     *
     * @return      example1
     */
    public String[] getExample() {
        return  this.example;
    }

    /**
     * set example1.
     *
     * @param       example1
     */
    public void setExample(String[] example) {
        this.example = example;
    }

    /**
     * get etcAnswer.
     *
     * @return      etcAnswer
     */
    public String getAnswer() {
        return  this.answer;
    }

    /**
     * set etcAnswer.
     *
     * @param       etcAnswer
     */
    public void setAnswer(String etcAnswer) {
        this.answer = etcAnswer;
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

    }

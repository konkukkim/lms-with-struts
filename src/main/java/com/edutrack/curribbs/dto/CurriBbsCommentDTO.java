/**
 * @(#)CurriBbsCommentDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.curribbs.dto;


/**
 * CURRI_BBS_COMMENT 테이블 Dto 클래스.
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
public class CurriBbsCommentDTO {

    protected String systemCode;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected String bbsType;
    protected int seqNo;
    protected int commNo;
    protected String courseId;
    protected String contents;
    protected String emoticonNum;
    protected String regId;
    protected String regName;
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
     * get bbsType.
     *
     * @return      bbsType
     */
    public String getBbsType() {
        return  this.bbsType;
    }

    /**
     * set bbsType.
     *
     * @param       bbsType
     */
    public void setBbsType(String bbsType) {
        this.bbsType = bbsType;
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
     * get courseId.
     *
     * @return      courseId
     */
    public String getCourseId() {
        return  this.courseId;
    }

    /**
     * set courseId.
     *
     * @param       courseId
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
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
        return  "systemCode="+systemCode+",curriCode="+curriCode+",curriYear="+curriYear+",curriTerm="+curriTerm+",bbsType="+bbsType
               +",seqNo="+seqNo+",commNo="+commNo+",courseId="+courseId+",contents="+contents+",emoticonNum="+emoticonNum
               +",regId="+regId+",regName="+regName+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

/**
 * @(#)CurriResInfoDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.curriresearch.dto;


/**
 * CURRI_RES_INFO 테이블 Dto 클래스.
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
public class CurriResInfoDTO {

    protected String systemCode="";
    protected String curriCode="";
    protected int curriYear;
    protected int curriTerm;
    protected int resId;
    protected String subject="";
    protected String contents="";
    protected String startDate="";
    protected String endDate="";
    protected String openYn="";
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
     * get startDate.
     *
     * @return      startDate
     */
    public String getStartDate() {
        return  this.startDate;
    }

    /**
     * set startDate.
     *
     * @param       startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * get endDate.
     *
     * @return      endDate
     */
    public String getEndDate() {
        return  this.endDate;
    }

    /**
     * set endDate.
     *
     * @param       endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * get openYn.
     *
     * @return      openYn
     */
    public String getOpenYn() {
        return  this.openYn;
    }

    /**
     * set openYn.
     *
     * @param       openYn
     */
    public void setOpenYn(String openYn) {
        this.openYn = openYn;
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
        return  "systemCode="+systemCode+",curriCode="+curriCode+",curriYear="+curriYear+",curriTerm="+curriTerm+",resId="+resId
               +",subject="+subject+",contents="+contents+",startDate="+startDate+",endDate="+endDate+",openYn="+openYn
               +",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

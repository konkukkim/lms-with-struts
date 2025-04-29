/**
 * @(#)CommCategoryDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.community.dto;


/**
 * COMM_CATEGORY 테이블 Dto 클래스.
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
public class CommCategoryDTO {

    protected String systemCode;
    protected String cateCode;
    protected String cateName;
    protected String cateImg1;
    protected String cateImg2;
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
     * get cateCode.
     *
     * @return      cateCode
     */
    public String getCateCode() {
        return  this.cateCode;
    }

    /**
     * set cateCode.
     *
     * @param       cateCode
     */
    public void setCateCode(String cateCode) {
        this.cateCode = cateCode;
    }

    /**
     * get cateName.
     *
     * @return      cateName
     */
    public String getCateName() {
        return  this.cateName;
    }

    /**
     * set cateName.
     *
     * @param       cateName
     */
    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    /**
     * get cateImg1.
     *
     * @return      cateImg1
     */
    public String getCateImg1() {
        return  this.cateImg1;
    }

    /**
     * set cateImg1.
     *
     * @param       cateImg1
     */
    public void setCateImg1(String cateImg1) {
        this.cateImg1 = cateImg1;
    }

    /**
     * get cateImg2.
     *
     * @return      cateImg2
     */
    public String getCateImg2() {
        return  this.cateImg2;
    }

    /**
     * set cateImg2.
     *
     * @param       cateImg2
     */
    public void setCateImg2(String cateImg2) {
        this.cateImg2 = cateImg2;
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
        return  "systemCode="+systemCode+",cateCode="+cateCode+",cateName="+cateName+",cateImg1="+cateImg1+",cateImg2="+cateImg2
               +",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

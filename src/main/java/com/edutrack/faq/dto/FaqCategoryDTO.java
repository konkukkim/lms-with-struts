/**
 * @(#)FaqCategoryDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.faq.dto;


/**
 * FAQ_CATEGORY 테이블 Dto 클래스.
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
public class FaqCategoryDTO {

    protected String systemCode;
    protected int cateId;
    protected String cateName;
    protected String xcomment;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    protected int conCnt;

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
     * get xcomment.
     *
     * @return      xcomment
     */
    public String getXcomment() {
        return  this.xcomment;
    }

    /**
     * set xcomment.
     *
     * @param       xcomment
     */
    public void setXcomment(String xcomment) {
        this.xcomment = xcomment;
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
	 * @return Returns the conCnt.
	 */
	public int getConCnt() {
		return conCnt;
	}
	/**
	 * @param conCnt The conCnt to set.
	 */
	public void setConCnt(int conCnt) {
		this.conCnt = conCnt;
	}
    /**
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return  "systemCode="+systemCode+",cateId="+cateId+",cateName="+cateName+",xcomment="+xcomment+",regId="+regId
               +",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

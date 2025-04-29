/**
 * @(#)CommInfoDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.community.dto;


/**
 * COMM_INFO 테이블 Dto 클래스.
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
public class CommInfoDTO {

    protected String systemCode="";
    protected int commId=0;
    protected String cateCode="";
    protected String commName="";
    protected String commSynopsis="";
    protected String keyword="";
    protected String useYn="";
    protected String commMaster="";
    protected String openYn="";
    protected String bestYn="";
    protected String mainImg="";
    protected String registType="";
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
     * get commId.
     *
     * @return      commId
     */
    public int getCommId() {
        return  this.commId;
    }

    /**
     * set commId.
     *
     * @param       commId
     */
    public void setCommId(int commId) {
        this.commId = commId;
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
     * get commName.
     *
     * @return      commName
     */
    public String getCommName() {
        return  this.commName;
    }

    /**
     * set commName.
     *
     * @param       commName
     */
    public void setCommName(String commName) {
        this.commName = commName;
    }

    /**
     * get commSynopsis.
     *
     * @return      commSynopsis
     */
    public String getCommSynopsis() {
        return  this.commSynopsis;
    }

    /**
     * set commSynopsis.
     *
     * @param       commSynopsis
     */
    public void setCommSynopsis(String commSynopsis) {
        this.commSynopsis = commSynopsis;
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
     * get useYn.
     *
     * @return      useYn
     */
    public String getUseYn() {
        return  this.useYn;
    }

    /**
     * set useYn.
     *
     * @param       useYn
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    /**
     * get commMaster.
     *
     * @return      commMaster
     */
    public String getCommMaster() {
        return  this.commMaster;
    }

    /**
     * set commMaster.
     *
     * @param       commMaster
     */
    public void setCommMaster(String commMaster) {
        this.commMaster = commMaster;
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
     * get bestYn.
     *
     * @return      bestYn
     */
    public String getBestYn() {
        return  this.bestYn;
    }

    /**
     * set bestYn.
     *
     * @param       bestYn
     */
    public void setBestYn(String bestYn) {
        this.bestYn = bestYn;
    }

    /**
     * get mainImg.
     *
     * @return      mainImg
     */
    public String getMainImg() {
        return  this.mainImg;
    }

    /**
     * set mainImg.
     *
     * @param       mainImg
     */
    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    /**
     * get registType.
     *
     * @return      registType
     */
    public String getRegistType() {
        return  this.registType;
    }

    /**
     * set registType.
     *
     * @param       registType
     */
    public void setRegistType(String registType) {
        this.registType = registType;
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
        return  "systemCode="+systemCode+",commId="+commId+",cateCode="+cateCode+",commName="+commName+",commSynopsis="+commSynopsis
               +",keyword="+keyword+",useYn="+useYn+",commMaster="+commMaster+",openYn="+openYn+",bestYn="+bestYn
               +",mainImg="+mainImg+",registType="+registType+",regId="+regId+",regDate="+regDate+",modId="+modId
               +",modDate="+modDate;
    }

    }

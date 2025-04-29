/**
 * @(#)CodeDaeDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.code.dto;


/**
 * CODE_DAE 테이블 Dto 클래스.
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
public class CodeDaeDTO {

    protected String systemCode;
    protected String codeDae;
    protected String daeName;
    protected String xcomment;
    protected String useYn;
    protected String useName;
    protected int codeSoCnt;
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
     * get codeDae.
     *
     * @return      codeDae
     */
    public String getCodeDae() {
        return  this.codeDae;
    }

    /**
     * set codeDae.
     *
     * @param       codeDae
     */
    public void setCodeDae(String codeDae) {
        this.codeDae = codeDae;
    }

    /**
     * get daeName.
     *
     * @return      daeName
     */
    public String getDaeName() {
        return  this.daeName;
    }

    /**
     * set daeName.
     *
     * @param       daeName
     */
    public void setDaeName(String daeName) {
        this.daeName = daeName;
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

    public int getCodeSoCnt() {
		return codeSoCnt;
	}

	public void setCodeSoCnt(int codeSoCnt) {
		this.codeSoCnt = codeSoCnt;
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

    public String getUseName() {
		return useName;
	}

	public void setUseName(String useName) {
		this.useName = useName;
	}

	/**
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return  "systemCode="+systemCode+",codeDae="+codeDae+",daeName="+daeName+",xcomment="+xcomment+",useYn="+useYn
               +",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

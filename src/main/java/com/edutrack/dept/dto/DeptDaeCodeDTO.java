/**
 * @(#)DeptDaeCodeDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.dept.dto;


/**
 * DEPT_DAECODE 테이블 Dto 클래스.
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
public class DeptDaeCodeDTO {

    protected String systemCode;
    protected String deptDaecode;
    protected String deptDaename;
    protected String useYn;
    protected String useName;
    protected int deptSoCnt;
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
     * get deptDaecode.
     *
     * @return      deptDaecode
     */
    public String getDeptDaecode() {
        return  this.deptDaecode;
    }

    /**
     * set deptDaecode.
     *
     * @param       deptDaecode
     */
    public void setDeptDaecode(String deptDaecode) {
        this.deptDaecode = deptDaecode;
    }

    /**
     * get deptDaename.
     *
     * @return      deptDaename
     */
    public String getDeptDaename() {
        return  this.deptDaename;
    }

    /**
     * set deptDaename.
     *
     * @param       deptDaename
     */
    public void setDeptDaename(String deptDaename) {
        this.deptDaename = deptDaename;
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

    public String getUseName() {
		return useName;
	}

	public void setUseName(String useName) {
		this.useName = useName;
	}

	public int getDeptSoCnt() {
		return deptSoCnt;
	}

	public void setDeptSoCnt(int deptSoCnt) {
		this.deptSoCnt = deptSoCnt;
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
        return  "systemCode="+systemCode+",deptDaecode="+deptDaecode+",deptDaename="+deptDaename+",useYn="+useYn+",regId="+regId
               +",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

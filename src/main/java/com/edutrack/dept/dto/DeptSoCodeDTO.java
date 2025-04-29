/**
 * @(#)DeptSoCodeDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.dept.dto;


/**
 * DEPT_SOCODE 테이블 Dto 클래스.
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
public class DeptSoCodeDTO {

    protected String systemCode = "";
    protected String deptDaecode= "";
    protected String deptSocode = "";
    protected String deptSoname = "";
    protected String useYn      = "";
    protected String userId     = "";
    protected String postCode   = "";
    protected String address    = "";
    protected String phone      = "";
    protected String position   = "";
    protected String useName   = "";
    protected String regId      = "";
    protected String regDate    = "";
    protected String modId      = "";
    protected String modDate    = "";
	protected String compNo		= "";
	
	
	
    public String getCompNo() {
		return compNo;
	}
	

	public void setCompNo(String compNo) {
		this.compNo = compNo;
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

    /**
     * get deptSocode.
     *
     * @return      deptSocode
     */
    public String getDeptSocode() {
        return  this.deptSocode;
    }

    /**
     * set deptSocode.
     *
     * @param       deptSocode
     */
    public void setDeptSocode(String deptSocode) {
        this.deptSocode = deptSocode;
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
     * get deptSoname.
     *
     * @return      deptSoname
     */
    public String getDeptSoname() {
        return  this.deptSoname;
    }

    /**
     * set deptSoname.
     *
     * @param       deptSoname
     */
    public void setDeptSoname(String deptSoname) {
        this.deptSoname = deptSoname;
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
     * get userId.
     *
     * @return      userId
     */
    public String getUserId (){
        return  this.userId;
    }

    /**
     * set userId.
     *
     * @param       userId
     */
    public void setUserId(String userId){
        this.userId = userId;
    }
    /**
     * get postCode.
     *
     * @return      postCode
     */
    public String getPostCode(){
        return this.postCode;
    }
    
    /**
     * set postCode.
     *
     * @param       postCode
     */
   public void setPostCode(String postCode){
        this.postCode = postCode;
    }
    
    /**
     * get address.
     *
     * @return      address
     */
    public String getAddress(){
        return this.address;
    }
    /**
     * set address.
     *
     * @param       address
     */
    public void setAddress(String address){
        this.address = address ;
    }
    
    /**
     * get phone.
     *
     * @return      phone
     */
    public String getPhone(){
        return this.phone;
    }
    
    /**
     * set phone.
     *
     * @param       phone
     */
    public void setPhone(String phone){
        this.phone = phone ;
    }
    /**
     * get position.
     *
     * @return      position
     */
    public String getPosition(){
        return this.position;
    }
    
    /**
     * set position.
     *
     * @param       position
     */
    public void setPosition(String position){
        this.position = position;
    }

    
    public String getUseName() {
		return useName;
	}


	public void setUseName(String useName) {
		this.useName = useName;
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
        return  "systemCode="+systemCode+",deptSocode="+deptSocode+",deptDaecode="+deptDaecode+",deptSoname="+deptSoname+",useYn="+useYn
               +",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

}

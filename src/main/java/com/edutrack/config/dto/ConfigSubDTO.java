/**
 * @(#)ConfigSubDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.config.dto;


/**
 * CONFIG_SUB 테이블 Dto 클래스.
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
public class ConfigSubDTO {

    protected String systemCode;
    protected String configType;
    protected String configCode;
    protected String typeName;
    protected String configValue;
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
     * get configType.
     *
     * @return      configType
     */
    public String getConfigType() {
        return  this.configType;
    }

    /**
     * set configType.
     *
     * @param       configType
     */
    public void setConfigType(String configType) {
        this.configType = configType;
    }

    /**
     * get configCode.
     *
     * @return      configCode
     */
    public String getConfigCode() {
        return  this.configCode;
    }

    /**
     * set configCode.
     *
     * @param       configCode
     */
    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    /**
     * get typeName.
     *
     * @return      typeName
     */
    public String getTypeName() {
        return  this.typeName;
    }

    /**
     * set typeName.
     *
     * @param       typeName
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * get configValue.
     *
     * @return      configValue
     */
    public String getConfigValue() {
        return  this.configValue;
    }

    /**
     * set configValue.
     *
     * @param       configValue
     */
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
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
        return  "systemCode="+systemCode+",configType="+configType+",configCode="+configCode+",typeName="+typeName+",configValue="+configValue
               +",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

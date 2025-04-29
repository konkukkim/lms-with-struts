/**
 * @(#)CommMembersDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.community.dto;


/**
 * COMM_MEMBERS 테이블 Dto 클래스.
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
public class CommMembersDTO {

    protected String systemCode;
    protected int commId;
    protected String userId;
    protected String userLevel;
    protected String userNick;
    protected String useYn;
    protected String userIntro;
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
     * set commId.
     *
     * @param       commId
     */
    public void setCommId(String commId) {
        this.commId = Integer.parseInt(commId);
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
     * get userLevel.
     *
     * @return      userLevel
     */
    public String getUserLevel() {
        return  this.userLevel;
    }

    /**
     * set userLevel.
     *
     * @param       userLevel
     */
    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * get userNick.
     *
     * @return      userNick
     */
    public String getUserNick() {
        return  this.userNick;
    }

    /**
     * set userNick.
     *
     * @param       userNick
     */
    public void setUserNick(String userNick) {
        this.userNick = userNick;
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
     * get userIntro.
     *
     * @return      userIntro
     */
    public String getUserIntro() {
        return  this.userIntro;
    }

    /**
     * set userIntro.
     *
     * @param       userIntro
     */
    public void setUserIntro(String userIntro) {
        this.userIntro = userIntro;
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
        return  "systemCode="+systemCode+",commId="+commId+",userId="+userId+",userLevel="+userLevel+",userNick="+userNick
               +",useYn="+useYn+",userIntro="+userIntro+",regId="+regId+",regDate="+regDate+",modId="+modId
               +",modDate="+modDate;
    }

    }

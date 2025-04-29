/**
 * @(#)UserConnectDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.user.dto;


/**
 * USER_CONNECT 테이블 Dto 클래스.
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
public class UserConnectDTO {

    protected String systemCode="";
    protected String userId="";
    protected String firstCon="";
    protected long lastCon=999;
    protected int connectNo=0;
    protected String sessionId="";
    protected String remoteIp="";
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
     * get firstCon.
     *
     * @return      firstCon
     */
    public String getFirstCon() {
        return  this.firstCon;
    }

    /**
     * set firstCon.
     *
     * @param       firstCon
     */
    public void setFirstCon(String firstCon) {
        this.firstCon = firstCon;
    }

    /**
     * get lastCon.
     *
     * @return      lastCon
     */
    public long getLastCon() {
        return  this.lastCon;
    }

    /**
     * set lastCon.
     *
     * @param       lastCon
     */
    public void setLastCon(long lastCon) {
        this.lastCon = lastCon;
    }

    /**
     * get connectNo.
     *
     * @return      connectNo
     */
    public int getConnectNo() {
        return  this.connectNo;
    }

    /**
     * set connectNo.
     *
     * @param       connectNo
     */
    public void setConnectNo(int connectNo) {
        this.connectNo = connectNo;
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
	 * @return Returns the remoteIp.
	 */
	public String getRemoteIp() {
		return remoteIp;
	}
	/**
	 * @param remoteIp The remoteIp to set.
	 */
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	/**
	 * @return Returns the sessionId.
	 */
	public String getSessionId() {
		return sessionId;
	}
	/**
	 * @param sessionId The sessionId to set.
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
    /**
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return  "systemCode="+systemCode+",userId="+userId+",firstCon="+firstCon+",lastCon="+lastCon+",connectNo="+connectNo
               +",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

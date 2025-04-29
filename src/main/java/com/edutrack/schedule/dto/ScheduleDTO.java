/**
 * @(#)ScheduleDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.schedule.dto;


/**
 * SCHEDULE 테이블 Dto 클래스.
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
public class ScheduleDTO {

    protected String systemCode;
    protected String userId;
    protected int schId;
    protected String startDate;
    protected String endDate;
    protected String schType;
    protected String contents;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
	protected String[] scheCnt;
	protected String[][] tschContents;
	protected String[] conCnt;
	protected String[][] tschType;
	protected String[][] tschId;
	protected int tcnt;

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
     * get schId.
     *
     * @return      schId
     */
    public int getSchId() {
        return  this.schId;
    }

    /**
     * set schId.
     *
     * @param       schId
     */
    public void setSchId(int schId) {
        this.schId = schId;
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
     * get schType.
     *
     * @return      schType
     */
    public String getSchType() {
        return  this.schType;
    }

    /**
     * set schType.
     *
     * @param       schType
     */
    public void setSchType(String schType) {
        this.schType = schType;
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
	 * @return Returns the scheCnt.
	 */
	public String[] getScheCnt() {
		return scheCnt;
	}
	/**
	 * @param scheCnt The scheCnt to set.
	 */
	public void setScheCnt(String[] scheCnt) {
		this.scheCnt = scheCnt;
	}	
		
	/**
	 * @return Returns the conCnt.
	 */
	public String[] getConCnt() {
		return conCnt;
	}
		
	
	/**
	 * @return Returns the tschContents.
	 */
	public String[][] getTschContents() {
		return tschContents;
	}
	/**
	 * @param tschContents The tschContents to set.
	 */
	public void setTschContents(String[][] tschContents) {
		this.tschContents = tschContents;
	}
	/**
	 * @return Returns the tschType.
	 */
	public String[][] getTschType() {
		return tschType;
	}
	/**
	 * @param tschType The tschType to set.
	 */
	public void setTschType(String[][] tschType) {
		this.tschType = tschType;
	}
	/**
	 * @param conCnt The conCnt to set.
	 */
	public void setConCnt(String[] conCnt) {
		this.conCnt = conCnt;
	}
	
	
	/**
	 * @return Returns the tschId.
	 */
	public String[][] getTschId() {
		return tschId;
	}
	/**
	 * @param tschId The tschId to set.
	 */
	public void setTschId(String[][] tschId) {
		this.tschId = tschId;
	}
	
	
	/**
	 * @return Returns the tcnt.
	 */
	public int getTcnt() {
		return tcnt;
	}
	/**
	 * @param tcnt The tcnt to set.
	 */
	public void setTcnt(int tcnt) {
		this.tcnt = tcnt;
	}
	/**
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return  "systemCode="+systemCode+",userId="+userId+",schId="+schId+",startDate="+startDate+",endDate="+endDate
               +",schType="+schType+",contents="+contents+",regId="+regId+",regDate="+regDate+",modId="+modId
               +",modDate="+modDate;
    }

    }

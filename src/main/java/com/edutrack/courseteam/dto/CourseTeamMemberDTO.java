/**
 * @(#)CourseTeamMemberDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.courseteam.dto;


/**
 * COURSE_TEAM_INFO 테이블 Dto 클래스.
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
public class CourseTeamMemberDTO {

    protected String systemCode;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected String courseId;
    protected int teamNo;
    protected String userId;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    protected int teamCnt;
    
    /** 추가 **/
    protected String userName;
    protected String deptSoName;
    protected String[] chkId;
    

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
     * get curriCode.
     *
     * @return      curriCode
     */
    public String getCurriCode() {
        return  this.curriCode;
    }

    /**
     * set curriCode.
     *
     * @param       curriCode
     */
    public void setCurriCode(String curriCode) {
        this.curriCode = curriCode;
    }

    /**
     * get curriYear.
     *
     * @return      curriYear
     */
    public int getCurriYear() {
        return  this.curriYear;
    }

    /**
     * set curriYear.
     *
     * @param       curriYear
     */
    public void setCurriYear(int curriYear) {
        this.curriYear = curriYear;
    }

    /**
     * get curriTerm.
     *
     * @return      curriTerm
     */
    public int getCurriTerm() {
        return  this.curriTerm;
    }

    /**
     * set curriTerm.
     *
     * @param       curriTerm
     */
    public void setCurriTerm(int curriTerm) {
        this.curriTerm = curriTerm;
    }

    /**
     * get courseId.
     *
     * @return      courseId
     */
    public String getCourseId() {
        return  this.courseId;
    }

    /**
     * set courseId.
     *
     * @param       courseId
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * get teamNo.
     *
     * @return      teamNo
     */
    public int getTeamNo() {
        return  this.teamNo;
    }

    /**
     * set teamNo.
     *
     * @param       teamNo
     */
    public void setTeamNo(int teamNo) {
        this.teamNo = teamNo;
    }

  
	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * @return Returns the teamCnt.
	 */
	public int getTeamCnt() {
		return teamCnt;
	}
	/**
	 * @param teamCnt The teamCnt to set.
	 */
	public void setTeamCnt(int teamCnt) {
		this.teamCnt = teamCnt;
	}
	
	
	/**
	 * @return Returns the deptSoName.
	 */
	public String getDeptSoName() {
		return deptSoName;
	}
	/**
	 * @param deptSoName The deptSoName to set.
	 */
	public void setDeptSoName(String deptSoName) {
		this.deptSoName = deptSoName;
	}
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}	
	
	/**
	 * @return Returns the chkId.
	 */
	public String[] getChkId() {
		return chkId;
	}
	/**
	 * @param chkId The chkId to set.
	 */
	public void setChkId(String[] chkId) {
		this.chkId = chkId;
	}
    /**
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return  "systemCode="+systemCode+",curriCode="+curriCode+",curriYear="+curriYear+",curriTerm="+curriTerm+",courseId="+courseId
               +",teamNo="+teamNo+",userId="+userId+",regId="+regId+",regDate="+regDate+",modId="+modId
               +",modDate="+modDate;
    }

    }

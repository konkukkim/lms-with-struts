/**
 * @(#)CourseForumCommentDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.courseforum.dto;


/**
 * COURSE_FORUM_COMMENT 테이블 Dto 클래스.
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
public class CourseForumCommentDTO {

    protected String systemCode;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected String courseId;
    protected int forumId;
    protected int seqNo;
    protected int commNo;
    protected String contents;
    protected int emoticonNum;
    protected String regId;
    protected String regName;
    protected String regPasswd;
    protected String regDate;
    protected String modId;
    protected String modDate;
    

    /**
	 * @return regPasswd
	 */
	public String getRegPasswd() {
		return regPasswd;
	}

	/**
	 * @param regPasswd 설정하려는 regPasswd
	 */
	public void setRegPasswd(String regPasswd) {
		this.regPasswd = regPasswd;
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
     * get forumId.
     *
     * @return      forumId
     */
    public int getForumId() {
        return  this.forumId;
    }

    /**
     * set forumId.
     *
     * @param       forumId
     */
    public void setForumId(int forumId) {
        this.forumId = forumId;
    }

    /**
     * get seqNo.
     *
     * @return      seqNo
     */
    public int getSeqNo() {
        return  this.seqNo;
    }

    /**
     * set seqNo.
     *
     * @param       seqNo
     */
    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * get commNo.
     *
     * @return      commNo
     */
    public int getCommNo() {
        return  this.commNo;
    }

    /**
     * set commNo.
     *
     * @param       commNo
     */
    public void setCommNo(int commNo) {
        this.commNo = commNo;
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
     * get emoticonNum.
     *
     * @return      emoticonNum
     */
    public int getEmoticonNum() {
        return  this.emoticonNum;
    }

    /**
     * set emoticonNum.
     *
     * @param       emoticonNum
     */
    public void setEmoticonNum(int emoticonNum) {
        this.emoticonNum = emoticonNum;
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
        return  "systemCode="+systemCode+",curriCode="+curriCode+",curriYear="+curriYear+",curriTerm="+curriTerm+",courseId="+courseId
               +",forumId="+forumId+",seqNo="+seqNo+",commNo="+commNo+",contents="+contents+",emoticonNum="+emoticonNum
               +",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

	/**
	 * @return Returns the regName.
	 */
	public String getRegName() {
		return regName;
	}
	/**
	 * @param regName The regName to set.
	 */
	public void setRegName(String regName) {
		this.regName = regName;
	}
    }

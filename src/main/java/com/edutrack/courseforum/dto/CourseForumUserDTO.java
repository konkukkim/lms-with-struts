/**
 * @(#)CourseForumUserDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.courseforum.dto;


/**
 * COURSE_FORUM_USER 테이블 Dto 클래스.
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
public class CourseForumUserDTO {

    protected String systemCode;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected String courseId;
    protected int forumId;
    protected String userId;
    protected String userName; 
    protected String subject;
    protected int subForumId;
    protected int score;
    protected int connectNo;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    
    
    protected int contentsCnt;
    protected int commentCnt;
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
     * set userName.
     *
     * @param       userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
  
    /**
     * get userName.
     *
     * @return      userName
     */
    public String getUserName() {
        return  this.userName;
    } 
    /**
     * get subject.
     *
     * @return      subject
     */
    public String getSubject() {
        return  this.subject;
    }

    /**
     * set subject.
     *
     * @param       subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
    /**
     * get subForumId.
     *
     * @return      subForumId
     */
    public int getSubForumId() {
        return  this.subForumId;
    }

    /**
     * set subForumId.
     *
     * @param       subForumId
     */
    public void setSubForumId(int subForumId) {
        this.subForumId = subForumId;
    }

    /**
     * get score.
     *
     * @return      score
     */
    public int getScore() {
        return  this.score;
    }

    /**
     * set score.
     *
     * @param       score
     */
    public void setScore(int score) {
        this.score = score;
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
     * get contentsCnt.
     *
     * @return      contentsCnt
     */
    public int getContentsCnt() {
        return  this.contentsCnt;
    }

    /**
     * set contentsCnt.
     *
     * @param       contentsCnt
     */
    public void setContentsCnt(int contentsCnt) {
        this.contentsCnt = contentsCnt;
    }  
    
  
    
    
    /**
     * get commentCnt.
     *
     * @return      commentCnt
     */
    public int getCommentCnt() {
        return  this.commentCnt;
    }

    /**
     * set contentsCnt.
     *
     * @param       commentCnt
     */
    public void setCommentCnt(int commentCnt) {
        this.commentCnt = commentCnt;
    }  
    
    
    
    
    
    /**
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return  "systemCode="+systemCode+",curriCode="+curriCode+",curriYear="+curriYear+",curriTerm="+curriTerm+",courseId="+courseId
               +",forumId="+forumId+",userId="+userId+",subForumId="+subForumId+",score="+score+",connectNo="+connectNo
               +",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

    }

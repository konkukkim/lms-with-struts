/**
 * @(#)CurriQuizAnswerDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.curriquiz.dto;


/**
 * CURRI_QUIZ_ANSWER 테이블 Dto 클래스.
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
public class CurriQuizAnswerDTO {

    protected String systemCode;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected String userId;
    protected String courseId;
    protected String contentsId;
    protected int quizOrder;
    protected String quizType;
    protected String contents;
    protected String answer;
    protected int score;
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
     * get contentsId.
     *
     * @return      contentsId
     */
    public String getContentsId() {
        return  this.contentsId;
    }

    /**
     * set contentsId.
     *
     * @param       contentsId
     */
    public void setContentsId(String contentsId) {
        this.contentsId = contentsId;
    }

    /**
     * get quizOrder.
     *
     * @return      quizOrder
     */
    public int getQuizOrder() {
        return  this.quizOrder;
    }

    /**
     * set quizOrder.
     *
     * @param       quizOrder
     */
    public void setQuizOrder(int quizOrder) {
        this.quizOrder = quizOrder;
    }

    /**
     * get quizType.
     *
     * @return      quizType
     */
    public String getQuizType() {
        return  this.quizType;
    }

    /**
     * set quizType.
     *
     * @param       quizType
     */
    public void setQuizType(String quizType) {
        this.quizType = quizType;
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
     * get answer.
     *
     * @return      answer
     */
    public String getAnswer() {
        return  this.answer;
    }

    /**
     * set answer.
     *
     * @param       answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
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
        return  "systemCode="+systemCode+",curriCode="+curriCode+",curriYear="+curriYear+",curriTerm="+curriTerm+",userId="+userId
               +",courseId="+courseId+",contentsId="+contentsId+",quizOrder="+quizOrder+",quizType="+quizType+",contents="+contents
               +",answer="+answer+",score="+score+",regId="+regId+",regDate="+regDate+",modId="+modId
               +",modDate="+modDate;
    }

    }

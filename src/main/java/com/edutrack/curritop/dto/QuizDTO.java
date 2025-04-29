/**
 * @(#)QuizDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.curritop.dto;


/**
 * QUIZ 테이블 Dto 클래스.
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
public class QuizDTO {

    protected String systemCode;
    protected String courseId;
    protected String contentsId;
    protected int quizOrder;
    protected String quizType;
    protected String contents;
    protected String contentsText;
    protected String example1;
    protected String example2;
    protected String example3;
    protected String example4;
    protected String example5;
    protected String answer;
    protected String comment;
    protected String rfileName;
    protected String sfileName;
    protected String filePath;
    protected String fileSize;
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
     * get contentsText.
     *
     * @return      contentsText
     */
    public String getContentsText() {
        return  this.contentsText;
    }

    /**
     * set contentsText.
     *
     * @param       contentsText
     */
    public void setContentsText(String contentsText) {
        this.contentsText = contentsText;
    }

    /**
     * get example1.
     *
     * @return      example1
     */
    public String getExample1() {
        return  this.example1;
    }

    /**
     * set example1.
     *
     * @param       example1
     */
    public void setExample1(String example1) {
        this.example1 = example1;
    }

    /**
     * get example2.
     *
     * @return      example2
     */
    public String getExample2() {
        return  this.example2;
    }

    /**
     * set example2.
     *
     * @param       example2
     */
    public void setExample2(String example2) {
        this.example2 = example2;
    }

    /**
     * get example3.
     *
     * @return      example3
     */
    public String getExample3() {
        return  this.example3;
    }

    /**
     * set example3.
     *
     * @param       example3
     */
    public void setExample3(String example3) {
        this.example3 = example3;
    }

    /**
     * get example4.
     *
     * @return      example4
     */
    public String getExample4() {
        return  this.example4;
    }

    /**
     * set example4.
     *
     * @param       example4
     */
    public void setExample4(String example4) {
        this.example4 = example4;
    }

    /**
     * get example5.
     *
     * @return      example5
     */
    public String getExample5() {
        return  this.example5;
    }

    /**
     * set example5.
     *
     * @param       example5
     */
    public void setExample5(String example5) {
        this.example5 = example5;
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
     * get comment.
     *
     * @return      comment
     */
    public String getComment() {
        return  this.comment;
    }

    /**
     * set comment.
     *
     * @param       comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * get rfileName.
     *
     * @return      rfileName
     */
    public String getRfileName() {
        return  this.rfileName;
    }

    /**
     * set rfileName.
     *
     * @param       rfileName
     */
    public void setRfileName(String rfileName) {
        this.rfileName = rfileName;
    }

    /**
     * get sfileName.
     *
     * @return      sfileName
     */
    public String getSfileName() {
        return  this.sfileName;
    }

    /**
     * set sfileName.
     *
     * @param       sfileName
     */
    public void setSfileName(String sfileName) {
        this.sfileName = sfileName;
    }

    /**
     * get filePath.
     *
     * @return      filePath
     */
    public String getFilePath() {
        return  this.filePath;
    }

    /**
     * set filePath.
     *
     * @param       filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * get fileSize.
     *
     * @return      fileSize
     */
    public String getFileSize() {
        return  this.fileSize;
    }

    /**
     * set fileSize.
     *
     * @param       fileSize
     */
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
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
        return  "systemCode="+systemCode+",courseId="+courseId+",contentsId="+contentsId+",quizOrder="+quizOrder+",quizType="+quizType
               +",contents="+contents+",example1="+example1+",example2="+example2+",example3="+example3+",example4="+example4
               +",example5="+example5+",answer="+answer+",comment="+comment+",rfileName="+rfileName+",sfileName="+sfileName
               +",filePath="+filePath+",fileSize="+fileSize+",regId="+regId+",regDate="+regDate+",modId="+modId
               +",modDate="+modDate;
    }

    }

/**
 * @(#)ExamAnswerDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.courseexam.dto;


/**
 * EXAM_ANSWER 테이블 Dto 클래스.
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
public class ExamAnswerDTO {

    protected String systemCode="";
    protected String curriCode="";
    protected int curriYear=0;
    protected int curriTerm=0;
    protected String courseId="";
    protected String userId="";
    protected int examId=0;
    protected String examOrder="";
    protected String examNo="";
    protected String answer="";
    protected String examAnswer="";
    protected String examScore="";
    protected String answerScore="";
    protected int totalScore=0;
    protected int answerTime=0;
    protected String resultCheck="N";
    protected String startDate="";
    protected String endDate="";
    protected String feedBack="";
    protected String flagRetest="";
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
     * get examId.
     *
     * @return      examId
     */
    public int getExamId() {
        return  this.examId;
    }

    /**
     * set examId.
     *
     * @param       examId
     */
    public void setExamId(int examId) {
        this.examId = examId;
    }

    /**
     * get examNo.
     *
     * @return      examNo
     */
    public String getExamNo() {
        return  this.examNo;
    }

    /**
     * set examNo.
     *
     * @param       examNo
     */
    public void setExamNo(String examNo) {
        this.examNo = examNo;
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
    public String getAnswerScore() {
        return  this.answerScore;
    }

    /**
     * set score.
     *
     * @param       score
     */
    public void setAnswerScore(String score) {
        this.answerScore = score;
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
	 * @return Returns the answerTime.
	 */
	public int getAnswerTime() {
		return answerTime;
	}
	/**
	 * @param answerTime The answerTime to set.
	 */
	public void setAnswerTime(int answerTime) {
		this.answerTime = answerTime;
	}
	/**
	 * @return Returns the endDate.
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return Returns the examScore.
	 */
	public String getExamScore() {
		return examScore;
	}
	/**
	 * @param examScore The examScore to set.
	 */
	public void setExamScore(String examScore) {
		this.examScore = examScore;
	}
	/**
	 * @return Returns the feedBack.
	 */
	public String getFeedBack() {
		return feedBack;
	}
	/**
	 * @param feedBack The feedBack to set.
	 */
	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}
	/**
	 * @return Returns the flagRetest.
	 */
	public String getFlagRetest() {
		return flagRetest;
	}
	/**
	 * @param flagRetest The flagRetest to set.
	 */
	public void setFlagRetest(String flagRetest) {
		this.flagRetest = flagRetest;
	}
	/**
	 * @return Returns the startDate.
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return Returns the totalScore.
	 */
	public int getTotalScore() {
		return totalScore;
	}
	/**
	 * @param totalScore The totalScore to set.
	 */
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	
	/**
	 * @return Returns the examOrder.
	 */
	public String getExamOrder() {
		return examOrder;
	}
	/**
	 * @param examOrder The examOrder to set.
	 */
	public void setExamOrder(String examOrder) {
		this.examOrder = examOrder;
	}

	/**
	 * @return Returns the examAnswer.
	 */
	public String getExamAnswer() {
		return examAnswer;
	}
	/**
	 * @param examAnswer The examAnswer to set.
	 */
	public void setExamAnswer(String examAnswer) {
		this.examAnswer = examAnswer;
	}
	/**
	 * @return Returns the resultCheck.
	 */
	public String getResultCheck() {
		return resultCheck;
	}
	/**
	 * @param resultCheck The resultCheck to set.
	 */
	public void setResultCheck(String resultCheck) {
		this.resultCheck = resultCheck;
	}
	/**
	 * toString methode: creates a String representation of the object
	 * @return the String representation
	 * @author info.vancauwenberge.tostring plugin
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ExamAnswerDTO[");
		buffer.append("systemCode = ").append(systemCode);
		buffer.append(", curriCode = ").append(curriCode);
		buffer.append(", curriYear = ").append(curriYear);
		buffer.append(", curriTerm = ").append(curriTerm);
		buffer.append(", courseId = ").append(courseId);
		buffer.append(", userId = ").append(userId);
		buffer.append(", examId = ").append(examId);
		buffer.append(", examOrder = ").append(examOrder);
		buffer.append(", examNo = ").append(examNo);
		buffer.append(", answer = ").append(answer);
		buffer.append(", examScore = ").append(examScore);
		buffer.append(", answerScore = ").append(answerScore);
		buffer.append(", totalScore = ").append(totalScore);
		buffer.append(", answerTime = ").append(answerTime);
		buffer.append(", startDate = ").append(startDate);
		buffer.append(", endDate = ").append(endDate);
		buffer.append(", feedBack = ").append(feedBack);
		buffer.append(", flagRetest = ").append(flagRetest);
		buffer.append(", regId = ").append(regId);
		buffer.append(", regDate = ").append(regDate);
		buffer.append(", modId = ").append(modId);
		buffer.append(", modDate = ").append(modDate);
		buffer.append("]");
		return buffer.toString();
	}
}
/**
 * @(#)ResultCourseDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.resultcourse.dto;


/**
 * 시험,토론,포럼 개별 성적 결과를 담을 DTO 클래스
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
public class ResultScoreDTO {

    protected String scoreId;
    protected String subject;
    protected int baseScore;
    protected int resultScore;
    protected String startDate;
    protected String endDate;
    
    /** 추가 **/
    protected int deptScore;
    protected String deptName;
    protected int userCnt;

    /**
     * get scoreId.
     *
     * @return      scoreId
     */
    public String getScoreId() {
        return  this.scoreId;
    }

    /**
     * set scoreId.
     *
     * @param       scoreId
     */
    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
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
     * get baseScore.
     *
     * @return      baseScore
     */
    public int getBaseScore() {
        return  this.baseScore;
    }

    /**
     * set baseScore.
     *
     * @param       baseScore
     */
    public void setBaseScore(int baseScore) {
        this.baseScore = baseScore;
    }

    /**
     * get resultScore.
     *
     * @return      resultScore
     */
    public int getResultScore() {
        return  this.resultScore;
    }

    /**
     * set resultScore.
     *
     * @param       resultScore
     */
    public void setResultScore(int resultScore) {
        this.resultScore = resultScore;
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
	 * @return Returns the deptName.
	 */
	public String getDeptName() {
		return deptName;
	}
	/**
	 * @param deptName The deptName to set.
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}	
	
	/**
	 * @return Returns the userCnt.
	 */
	public int getUserCnt() {
		return userCnt;
	}
	/**
	 * @param userCnt The userCnt to set.
	 */
	public void setUserCnt(int userCnt) {
		this.userCnt = userCnt;
	}
    /**
	 * @return Returns the deptScore.
	 */
	public int getDeptScore() {
		return deptScore;
	}
	
	/**
	 * @param deptScore The deptScore to set.
	 */
	public void setDeptScore(int deptScore) {
		this.deptScore = deptScore;
	}
    }

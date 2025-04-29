/**
 * @(#)ResultCourseDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.resultcourse.dto;


/**
 * RESULT_COURSE 테이블 Dto 클래스.
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
public class ResultCourseDTO {

    protected String systemCode;
    protected String curriCode;
    protected String curriName;
    protected int curriYear;
    protected int curriTerm;
    protected String userId;
    protected String userName;
    protected String courseId;
    protected double scoreExam;
    protected double scoreReport;
    protected double scoreAttend;
    protected double scoreForum;
    protected double scoreEtc1;
    protected double scoreEtc2;
    protected double scoreTotal;
    protected String getCredit;
    protected String grade;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    protected String enrollStatus;

    /** 추가 **/
    protected int[] baseExam;
    protected int[] baseReport;
    protected int[] baseAttend;
    protected int[] baseForum;
    protected int[] baseEtc1;
    protected int[] baseEtc2;
    protected double[] scoreExam1;
    protected double[] scoreReport1;
    protected double[] scoreAttend1;
    protected double[] scoreForum1;
    protected double[] scoreEtc11;
    protected double[] scoreEtc21;
    protected double[] scoreTotal1;
    protected String[] courseId1;
    protected int totCnt;

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
     * get scoreExam.
     *
     * @return      scoreExam
     */
    public double getScoreExam() {
        return  this.scoreExam;
    }

    /**
     * set scoreExam.
     *
     * @param       scoreExam
     */
    public void setScoreExam(double scoreExam) {
        this.scoreExam = scoreExam;
    }

    /**
     * get scoreReport.
     *
     * @return      scoreReport
     */
    public double getScoreReport() {
        return  this.scoreReport;
    }

    /**
     * set scoreReport.
     *
     * @param       scoreReport
     */
    public void setScoreReport(double scoreReport) {
        this.scoreReport = scoreReport;
    }

    /**
     * get scoreAttend.
     *
     * @return      scoreAttend
     */
    public double getScoreAttend() {
        return  this.scoreAttend;
    }

    /**
     * set scoreAttend.
     *
     * @param       scoreAttend
     */
    public void setScoreAttend(double scoreAttend) {
        this.scoreAttend = scoreAttend;
    }

    /**
     * get scoreForum.
     *
     * @return      scoreForum
     */
    public double getScoreForum() {
        return  this.scoreForum;
    }

    /**
     * set scoreForum.
     *
     * @param       scoreForum
     */
    public void setScoreForum(double scoreForum) {
        this.scoreForum = scoreForum;
    }

    /**
     * get scoreEtc1.
     *
     * @return      scoreEtc1
     */
    public double getScoreEtc1() {
        return  this.scoreEtc1;
    }

    /**
     * set scoreEtc1.
     *
     * @param       scoreEtc1
     */
    public void setScoreEtc1(double scoreEtc1) {
        this.scoreEtc1 = scoreEtc1;
    }

    /**
     * get scoreEtc2.
     *
     * @return      scoreEtc2
     */
    public double getScoreEtc2() {
        return  this.scoreEtc2;
    }

    /**
     * set scoreEtc2.
     *
     * @param       scoreEtc2
     */
    public void setScoreEtc2(double scoreEtc2) {
        this.scoreEtc2 = scoreEtc2;
    }

    /**
     * get scoreTotal.
     *
     * @return      scoreTotal
     */
    public double getScoreTotal() {
        return  this.scoreTotal;
    }

    /**
     * set scoreTotal.
     *
     * @param       scoreTotal
     */
    public void setScoreTotal(double scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    /**
     * get grade.
     *
     * @return      grade
     */
    public String getGrade() {
        return  this.grade;
    }

    /**
     * set grade.
     *
     * @param       grade
     */
    public void setGrade(String grade) {
        this.grade = grade;
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
	 * @return Returns the baseAttend.
	 */
	public int[] getBaseAttend() {
		return baseAttend;
	}
	/**
	 * @param baseAttend The baseAttend to set.
	 */
	public void setBaseAttend(int[] baseAttend) {
		this.baseAttend = baseAttend;
	}
	/**
	 * @return Returns the baseEtc1.
	 */
	public int[] getBaseEtc1() {
		return baseEtc1;
	}
	/**
	 * @param baseEtc1 The baseEtc1 to set.
	 */
	public void setBaseEtc1(int[] baseEtc1) {
		this.baseEtc1 = baseEtc1;
	}
	/**
	 * @return Returns the baseEtc2.
	 */
	public int[] getBaseEtc2() {
		return baseEtc2;
	}
	/**
	 * @param baseEtc2 The baseEtc2 to set.
	 */
	public void setBaseEtc2(int[] baseEtc2) {
		this.baseEtc2 = baseEtc2;
	}
	/**
	 * @return Returns the baseExam.
	 */
	public int[] getBaseExam() {
		return baseExam;
	}
	/**
	 * @param baseExam The baseExam to set.
	 */
	public void setBaseExam(int[] baseExam) {
		this.baseExam = baseExam;
	}
	/**
	 * @return Returns the baseForum.
	 */
	public int[] getBaseForum() {
		return baseForum;
	}
	/**
	 * @param baseForum The baseForum to set.
	 */
	public void setBaseForum(int[] baseForum) {
		this.baseForum = baseForum;
	}
	/**
	 * @return Returns the baseReport.
	 */
	public int[] getBaseReport() {
		return baseReport;
	}
	/**
	 * @param baseReport The baseReport to set.
	 */
	public void setBaseReport(int[] baseReport) {
		this.baseReport = baseReport;
	}
	/**
	 * @return Returns the courseId1.
	 */
	public String[] getCourseId1() {
		return courseId1;
	}
	/**
	 * @param courseId1 The courseId1 to set.
	 */
	public void setCourseId1(String[] courseId1) {
		this.courseId1 = courseId1;
	}
	/**
	 * @return Returns the scoreAttend1.
	 */
	public double[] getScoreAttend1() {
		return scoreAttend1;
	}
	/**
	 * @param scoreAttend1 The scoreAttend1 to set.
	 */
	public void setScoreAttend1(double[] scoreAttend1) {
		this.scoreAttend1 = scoreAttend1;
	}
	/**
	 * @return Returns the scoreEtc11.
	 */
	public double[] getScoreEtc11() {
		return scoreEtc11;
	}
	/**
	 * @param scoreEtc11 The scoreEtc11 to set.
	 */
	public void setScoreEtc11(double[] scoreEtc11) {
		this.scoreEtc11 = scoreEtc11;
	}
	/**
	 * @return Returns the scoreEtc21.
	 */
	public double[] getScoreEtc21() {
		return scoreEtc21;
	}
	/**
	 * @param scoreEtc21 The scoreEtc21 to set.
	 */
	public void setScoreEtc21(double[] scoreEtc21) {
		this.scoreEtc21 = scoreEtc21;
	}
	/**
	 * @return Returns the scoreExam1.
	 */
	public double[] getScoreExam1() {
		return scoreExam1;
	}
	/**
	 * @param scoreExam1 The scoreExam1 to set.
	 */
	public void setScoreExam1(double[] scoreExam1) {
		this.scoreExam1 = scoreExam1;
	}
	/**
	 * @return Returns the scoreForum1.
	 */
	public double[] getScoreForum1() {
		return scoreForum1;
	}
	/**
	 * @param scoreForum1 The scoreForum1 to set.
	 */
	public void setScoreForum1(double[] scoreForum1) {
		this.scoreForum1 = scoreForum1;
	}
	/**
	 * @return Returns the scoreReport1.
	 */
	public double[] getScoreReport1() {
		return scoreReport1;
	}
	/**
	 * @param scoreReport1 The scoreReport1 to set.
	 */
	public void setScoreReport1(double[] scoreReport1) {
		this.scoreReport1 = scoreReport1;
	}
	/**
	 * @return Returns the scoreTotal1.
	 */
	public double[] getScoreTotal1() {
		return scoreTotal1;
	}
	/**
	 * @param scoreTotal1 The scoreTotal1 to set.
	 */
	public void setScoreTotal1(double[] scoreTotal1) {
		this.scoreTotal1 = scoreTotal1;
	}
	/**
	 * @return Returns the totCnt.
	 */
	public int getTotCnt() {
		return totCnt;
	}
	/**
	 * @param totCnt The totCnt to set.
	 */
	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}
    /**
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return  "systemCode="+systemCode+",curriCode="+curriCode+",curriYear="+curriYear+",curriTerm="+curriTerm+",userId="+userId
               +",courseId="+courseId+",scoreExam="+scoreExam+",scoreReport="+scoreReport+",scoreAttend="+scoreAttend
               +",scoreForum="+scoreForum+",scoreEtc1="+scoreEtc1+",scoreEtc2="+scoreEtc2+",scoreTotal="+scoreTotal+",grade="+grade
               +",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
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
	 * @return Returns the getCredit.
	 */
	public String getGetCredit() {
		return getCredit;
	}
	/**
	 * @param getCredit The getCredit to set.
	 */
	public void setGetCredit(String getCredit) {
		this.getCredit = getCredit;
	}
	/**
	 * @return Returns the enrollStatus.
	 */
	public String getEnrollStatus() {
		return enrollStatus;
	}
	/**
	 * @param enrollStatus The enrollStatus to set.
	 */
	public void setEnrollStatus(String enrollStatus) {
		this.enrollStatus = enrollStatus;
	}

	public String getCurriName() {
		return curriName;
	}

	public void setCurriName(String curriName) {
		this.curriName = curriName;
	}
    }

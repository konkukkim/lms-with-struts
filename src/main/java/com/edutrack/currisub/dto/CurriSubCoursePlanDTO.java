/**
 * @(#)CurriSubCourseDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.currisub.dto;


/**
 * CURRI_SUB_COURSE_PLAN 테이블 Dto 클래스.
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
public class CurriSubCoursePlanDTO {

    protected String systemCode;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected String courseId;
    protected String infoTitle1;
    protected String infoText1;
    protected String infoTitle2;
    protected String infoText2;
    protected String infoTitle3;
    protected String infoText3;
    protected String infoTitle4;
    protected String infoText4;
    protected String infoTitle5;
    protected String infoText5;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;

    protected String rFileName = "";
    protected String sFileName = "";
    protected String filePath = "";
    protected String filesize = "";



    /**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the filesize
	 */
	public String getFilesize() {
		return filesize;
	}

	/**
	 * @param filesize the filesize to set
	 */
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	/**
	 * @return the rFileName
	 */
	public String getRFileName() {
		return rFileName;
	}

	/**
	 * @param fileName the rFileName to set
	 */
	public void setRFileName(String fileName) {
		rFileName = fileName;
	}

	/**
	 * @return the sFileName
	 */
	public String getSFileName() {
		return sFileName;
	}

	/**
	 * @param fileName the sFileName to set
	 */
	public void setSFileName(String fileName) {
		sFileName = fileName;
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
     * get infoTitle1.
     *
     * @return      infoTitle1
     */
    public String getInfoTitle1() {
        return  this.infoTitle1;
    }

    /**
     * set infoTitle1.
     *
     * @param       infoTitle1
     */
    public void setInfoTitle1(String infoTitle1) {
        this.infoTitle1 = infoTitle1;
    }

    /**
     * get infoText1.
     *
     * @return      infoText1
     */
    public String getInfoText1() {
        return  this.infoText1;
    }

    /**
     * set infoText1.
     *
     * @param       infoText1
     */
    public void setInfoText1(String infoText1) {
        this.infoText1 = infoText1;
    }

    /**
     * get infoTitle2.
     *
     * @return      infoTitle2
     */
    public String getInfoTitle2() {
        return  this.infoTitle2;
    }

    /**
     * set infoTitle2.
     *
     * @param       infoTitle2
     */
    public void setInfoTitle2(String infoTitle2) {
        this.infoTitle2 = infoTitle2;
    }

    /**
     * get infoText2.
     *
     * @return      infoText2
     */
    public String getInfoText2() {
        return  this.infoText2;
    }

    /**
     * set infoText2.
     *
     * @param       infoText2
     */
    public void setInfoText2(String infoText2) {
        this.infoText2 = infoText2;
    }

    /**
     * get infoTitle3.
     *
     * @return      infoTitle3
     */
    public String getInfoTitle3() {
        return  this.infoTitle3;
    }

    /**
     * set infoTitle3.
     *
     * @param       infoTitle3
     */
    public void setInfoTitle3(String infoTitle3) {
        this.infoTitle3 = infoTitle3;
    }

    /**
     * get infoText3.
     *
     * @return      infoText3
     */
    public String getInfoText3() {
        return  this.infoText3;
    }

    /**
     * set infoText3.
     *
     * @param       infoText3
     */
    public void setInfoText3(String infoText3) {
        this.infoText3 = infoText3;
    }

    /**
     * get infoTitle4.
     *
     * @return      infoTitle4
     */
    public String getInfoTitle4() {
        return  this.infoTitle4;
    }

    /**
     * set infoTitle4.
     *
     * @param       infoTitle4
     */
    public void setInfoTitle4(String infoTitle4) {
        this.infoTitle4 = infoTitle4;
    }

    /**
     * get infoText4.
     *
     * @return      infoText4
     */
    public String getInfoText4() {
        return  this.infoText4;
    }

    /**
     * set infoText4.
     *
     * @param       infoText4
     */
    public void setInfoText4(String infoText4) {
        this.infoText4 = infoText4;
    }

    /**
     * get infoTitle5.
     *
     * @return      infoTitle5
     */
    public String getInfoTitle5() {
        return  this.infoTitle5;
    }

    /**
     * set infoTitle5.
     *
     * @param       infoTitle5
     */
    public void setInfoTitle5(String infoTitle5) {
        this.infoTitle5 = infoTitle5;
    }

    /**
     * get infoText5.
     *
     * @return      infoText5
     */
    public String getInfoText5() {
        return  this.infoText5;
    }

    /**
     * set infoText5.
     *
     * @param       infoText5
     */
    public void setInfoText5(String infoText5) {
        this.infoText5 = infoText5;
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



    }

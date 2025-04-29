/**
 * @(#)ReportBkContentsDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.coursereport.dto;


/**
 * EXAM_BK_CONTENTS 테이블 Dto 클래스.
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
public class ReportBkContentsDTO {

    protected String systemCode="";
    protected String courseId="";
    protected long reportBkCode=0;
    protected int reportNo=0;
    protected String reportType="";
    protected String contents="";
    protected String contentsText="";
    protected String reportple1="";
    protected String reportple2="";
    protected String reportple3="";
    protected String reportple4="";
    protected String reportple5="";
    protected String answer="";
    protected String multiAnswer="";
    protected String comment="";
    protected String rfileName="";
    protected String sfileName="";
    protected String filePath="";
    protected String fileSize="";
    protected String regId="";
    protected String regDate="";
    protected String modId="";
    protected String modDate="";

    protected String oldRfile = "";
    protected String oldSfile = "";
    protected String oldFilePath = "";
    protected String oldFileSize = "";
    protected String fileCheck = "";
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
     * get reportBkCode.
     *
     * @return      reportBkCode
     */
    public long getReportBkCode() {
        return  this.reportBkCode;
    }

    /**
     * set reportBkCode.
     *
     * @param       reportBkCode
     */
    public void setReportBkCode(long reportBkCode) {
        this.reportBkCode = reportBkCode;
    }

    /**
     * get reportNo.
     *
     * @return      reportNo
     */
    public int getReportNo() {
        return  this.reportNo;
    }

    /**
     * set reportNo.
     *
     * @param       reportNo
     */
    public void setReportNo(int reportNo) {
        this.reportNo = reportNo;
    }

    /**
     * get reportType.
     *
     * @return      reportType
     */
    public String getReportType() {
        return  this.reportType;
    }

    /**
     * set reportType.
     *
     * @param       reportType
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
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
     * get reportple1.
     *
     * @return      reportple1
     */
    public String getReportple1() {
        return  this.reportple1;
    }

    /**
     * set reportple1.
     *
     * @param       reportple1
     */
    public void setReportple1(String reportple1) {
        this.reportple1 = reportple1;
    }

    /**
     * get reportple2.
     *
     * @return      reportple2
     */
    public String getReportple2() {
        return  this.reportple2;
    }

    /**
     * set reportple2.
     *
     * @param       reportple2
     */
    public void setReportple2(String reportple2) {
        this.reportple2 = reportple2;
    }

    /**
     * get reportple3.
     *
     * @return      reportple3
     */
    public String getReportple3() {
        return  this.reportple3;
    }

    /**
     * set reportple3.
     *
     * @param       reportple3
     */
    public void setReportple3(String reportple3) {
        this.reportple3 = reportple3;
    }

    /**
     * get reportple4.
     *
     * @return      reportple4
     */
    public String getReportple4() {
        return  this.reportple4;
    }

    /**
     * set reportple4.
     *
     * @param       reportple4
     */
    public void setReportple4(String reportple4) {
        this.reportple4 = reportple4;
    }

    /**
     * get reportple5.
     *
     * @return      reportple5
     */
    public String getReportple5() {
        return  this.reportple5;
    }

    /**
     * set reportple5.
     *
     * @param       reportple5
     */
    public void setReportple5(String reportple5) {
        this.reportple5 = reportple5;
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
     * get xcomment.
     *
     * @return      xcomment
     */
    public String getComment() {
        return  this.comment;
    }

    /**
     * set xcomment.
     *
     * @param       xcomment
     */
    public void setComment(String xcomment) {
        this.comment = xcomment;
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
	 * @return Returns the multiAnswer.
	 */
	public String getMultiAnswer() {
		return multiAnswer;
	}
	/**
	 * @param multiAnswer The multiAnswer to set.
	 */
	public void setMultiAnswer(String multiAnswer) {
		this.multiAnswer = multiAnswer;
	}
	
	
	/**
	 * @return Returns the fileCheck.
	 */
	public String getFileCheck() {
		return fileCheck;
	}
	/**
	 * @param fileCheck The fileCheck to set.
	 */
	public void setFileCheck(String fileCheck) {
		this.fileCheck = fileCheck;
	}
	/**
	 * @return Returns the oldFilePath.
	 */
	public String getOldFilePath() {
		return oldFilePath;
	}
	/**
	 * @param oldFilePath The oldFilePath to set.
	 */
	public void setOldFilePath(String oldFilePath) {
		this.oldFilePath = oldFilePath;
	}
	/**
	 * @return Returns the oldFileSize.
	 */
	public String getOldFileSize() {
		return oldFileSize;
	}
	/**
	 * @param oldFileSize The oldFileSize to set.
	 */
	public void setOldFileSize(String oldFileSize) {
		this.oldFileSize = oldFileSize;
	}
	/**
	 * @return Returns the oldRfile.
	 */
	public String getOldRfile() {
		return oldRfile;
	}
	/**
	 * @param oldRfile The oldRfile to set.
	 */
	public void setOldRfile(String oldRfile) {
		this.oldRfile = oldRfile;
	}
	/**
	 * @return Returns the oldSfile.
	 */
	public String getOldSfile() {
		return oldSfile;
	}
	/**
	 * @param oldSfile The oldSfile to set.
	 */
	public void setOldSfile(String oldSfile) {
		this.oldSfile = oldSfile;
	}
	
	/**
	 * @return Returns the contentsText.
	 */
	public String getContentsText() {
		return contentsText;
	}
	/**
	 * @param contentsText The contentsText to set.
	 */
	public void setContentsText(String contentsText) {
		this.contentsText = contentsText;
	}
    /**
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return  "systemCode="+systemCode+",courseId="+courseId+",reportBkCode="+reportBkCode+",reportNo="+reportNo+",reportType="+reportType
               +",contents="+contents+",reportple1="+reportple1+",reportple2="+reportple2+",reportple3="+reportple3+",reportple4="+reportple4
               +",reportple5="+reportple5+",answer="+answer+",xcomment="+comment+",rfileName="+rfileName+",sfileName="+sfileName
               +",filePath="+filePath+",fileSize="+fileSize+",regId="+regId+",regDate="+regDate+",modId="+modId
               +",modDate="+modDate;
    }

    }

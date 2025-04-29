/**
 * 
 */
package com.edutrack.coursereport.dto;

/**
 * @author HYUN
 *
 */
public class ReportSubInfoDTO {
	
	protected String systemCode;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected String courseId;
    protected int reportId;
    protected int subReportId;
    protected String subReportSubject;
    protected String subReportContents;
    protected String rfileName;
    protected String sfileName;
    protected String filePath;
    protected String fileSize;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    
    protected String oldRfile = "";
    protected String oldSfile = "";
    protected String oldFilePath = "";
    protected String oldFileSize = "";
    protected String fileCheck = "";
    
    protected String autoBankYn = "";
    protected int reportBankId = 0;
    
	/**
	 * @return the fileCheck
	 */
	public String getFileCheck() {
		return fileCheck;
	}
	/**
	 * @param fileCheck the fileCheck to set
	 */
	public void setFileCheck(String fileCheck) {
		this.fileCheck = fileCheck;
	}
	/**
	 * @return the oldFilePath
	 */
	public String getOldFilePath() {
		return oldFilePath;
	}
	/**
	 * @param oldFilePath the oldFilePath to set
	 */
	public void setOldFilePath(String oldFilePath) {
		this.oldFilePath = oldFilePath;
	}
	/**
	 * @return the oldFileSize
	 */
	public String getOldFileSize() {
		return oldFileSize;
	}
	/**
	 * @param oldFileSize the oldFileSize to set
	 */
	public void setOldFileSize(String oldFileSize) {
		this.oldFileSize = oldFileSize;
	}
	/**
	 * @return the oldRfile
	 */
	public String getOldRfile() {
		return oldRfile;
	}
	/**
	 * @param oldRfile the oldRfile to set
	 */
	public void setOldRfile(String oldRfile) {
		this.oldRfile = oldRfile;
	}
	/**
	 * @return the oldSfile
	 */
	public String getOldSfile() {
		return oldSfile;
	}
	/**
	 * @param oldSfile the oldSfile to set
	 */
	public void setOldSfile(String oldSfile) {
		this.oldSfile = oldSfile;
	}
	/**
	 * @return the courseId
	 */
	public String getCourseId() {
		return courseId;
	}
	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	/**
	 * @return the curriCode
	 */
	public String getCurriCode() {
		return curriCode;
	}
	/**
	 * @param curriCode the curriCode to set
	 */
	public void setCurriCode(String curriCode) {
		this.curriCode = curriCode;
	}
	/**
	 * @return the curriTerm
	 */
	public int getCurriTerm() {
		return curriTerm;
	}
	/**
	 * @param curriTerm the curriTerm to set
	 */
	public void setCurriTerm(int curriTerm) {
		this.curriTerm = curriTerm;
	}
	/**
	 * @return the curriYear
	 */
	public int getCurriYear() {
		return curriYear;
	}
	/**
	 * @param curriYear the curriYear to set
	 */
	public void setCurriYear(int curriYear) {
		this.curriYear = curriYear;
	}
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
	 * @return the fileSize
	 */
	public String getFileSize() {
		return fileSize;
	}
	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 * @return the modDate
	 */
	public String getModDate() {
		return modDate;
	}
	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	/**
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}
	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}
	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}
	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}
	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}
	/**
	 * @return the reportId
	 */
	public int getReportId() {
		return reportId;
	}
	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	/**
	 * @return the rfileName
	 */
	public String getRfileName() {
		return rfileName;
	}
	/**
	 * @param rfileName the rfileName to set
	 */
	public void setRfileName(String rfileName) {
		this.rfileName = rfileName;
	}
	/**
	 * @return the sfileName
	 */
	public String getSfileName() {
		return sfileName;
	}
	/**
	 * @param sfileName the sfileName to set
	 */
	public void setSfileName(String sfileName) {
		this.sfileName = sfileName;
	}
	/**
	 * @return the subReportContents
	 */
	public String getSubReportContents() {
		return subReportContents;
	}
	/**
	 * @param subReportContents the subReportContents to set
	 */
	public void setSubReportContents(String subReportContents) {
		this.subReportContents = subReportContents;
	}
	/**
	 * @return the subReportId
	 */
	public int getSubReportId() {
		return subReportId;
	}
	/**
	 * @param subReportId the subReportId to set
	 */
	public void setSubReportId(int subReportId) {
		this.subReportId = subReportId;
	}
	/**
	 * @return the subReportSubject
	 */
	public String getSubReportSubject() {
		return subReportSubject;
	}
	/**
	 * @param subReportSubject the subReportSubject to set
	 */
	public void setSubReportSubject(String subReportSubject) {
		this.subReportSubject = subReportSubject;
	}
	/**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}
	/**
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	/**
	 * @return the autoBankYn
	 */
	public String getAutoBankYn() {
		return autoBankYn;
	}
	/**
	 * @param autoBankYn the autoBankYn to set
	 */
	public void setAutoBankYn(String autoBankYn) {
		this.autoBankYn = autoBankYn;
	}
	/**
	 * @return the reportBankId
	 */
	public int getReportBankId() {
		return reportBankId;
	}
	/**
	 * @param reportBankId the reportBankId to set
	 */
	public void setReportBankId(int reportBankId) {
		this.reportBankId = reportBankId;
	}
}

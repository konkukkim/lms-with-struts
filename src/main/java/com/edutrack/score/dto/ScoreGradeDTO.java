/*
 * Created on 2007. 8. 9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.score.dto;

/**
 * @author HerSunJa
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ScoreGradeDTO {

	protected String systemCode	;       //  시스템구분    
	protected int    curriYear	;       //  년도        
	protected int    hakgiTerm	;       //  학기        
	protected String gradeCode	;       //  일련번호    
	protected String gradeName	;       //  평가등급       
	protected double gradePoint	;       //  학점    
	protected double frPoint	;          //  시작점수
	protected double toPoint	;          //  종료점수
	protected double frRate		;       //  시작비율
	protected double toRate		;       //  종료비율
	protected String regId		;       //  등록자ID  
	protected String regDate	;       //  등록일자  
	protected String modId		;       //  수정자ID  
	protected String modDate	;       //  수정일자  
	protected String useYn 	;       //  사용여부  

	

	/**
	 * @return Returns the curriTerm.
	 */
	public int getHakgiTerm() {
		return hakgiTerm;
	}
	/**
	 * @param curriTerm The curriTerm to set.
	 */
	public void setHakgiTerm(int hakgiTerm) {
		this.hakgiTerm = hakgiTerm;
	}
	/**
	 * @return Returns the curriYear.
	 */
	public int getCurriYear() {
		return curriYear;
	}
	/**
	 * @param curriYear The curriYear to set.
	 */
	public void setCurriYear(int curriYear) {
		this.curriYear = curriYear;
	}
	/**
	 * @return Returns the frPoint.
	 */
	public double getFrPoint() {
		return frPoint;
	}
	/**
	 * @param frPoint The frPoint to set.
	 */
	public void setFrPoint(double frPoint) {
		this.frPoint = frPoint;
	}
	/**
	 * @return Returns the frRate.
	 */
	public double getFrRate() {
		return frRate;
	}
	/**
	 * @param frRate The frRate to set.
	 */
	public void setFrRate(double frRate) {
		this.frRate = frRate;
	}
	/**
	 * @return Returns the gradeCode.
	 */
	public String getGradeCode() {
		return gradeCode;
	}
	/**
	 * @param gradeCode The gradeCode to set.
	 */
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	/**
	 * @return Returns the gradeName.
	 */
	public String getGradeName() {
		return gradeName;
	}
	/**
	 * @param gradeName The gradeName to set.
	 */
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	/**
	 * @return Returns the gradePoint.
	 */
	public double getGradePoint() {
		return gradePoint;
	}
	/**
	 * @param gradePoint The gradePoint to set.
	 */
	public void setGradePoint(double gradePoint) {
		this.gradePoint = gradePoint;
	}
	/**
	 * @return Returns the modDate.
	 */
	public String getModDate() {
		return modDate;
	}
	/**
	 * @param modDate The modDate to set.
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	/**
	 * @return Returns the modId.
	 */
	public String getModId() {
		return modId;
	}
	/**
	 * @param modId The modId to set.
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}
	/**
	 * @return Returns the regDate.
	 */
	public String getRegDate() {
		return regDate;
	}
	/**
	 * @param regDate The regDate to set.
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	/**
	 * @return Returns the regId.
	 */
	public String getRegId() {
		return regId;
	}
	/**
	 * @param regId The regId to set.
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}
	/**
	 * @return Returns the systemCode.
	 */
	public String getSystemCode() {
		return systemCode;
	}
	/**
	 * @param systemCode The systemCode to set.
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	/**
	 * @return Returns the toPoint.
	 */
	public double getToPoint() {
		return toPoint;
	}
	/**
	 * @param toPoint The toPoint to set.
	 */
	public void setToPoint(double toPoint) {
		this.toPoint = toPoint;
	}
	/**
	 * @return Returns the toRate.
	 */
	public double getToRate() {
		return toRate;
	}
	/**
	 * @param toRate The toRate to set.
	 */
	public void setToRate(double toRate) {
		this.toRate = toRate;
	}
	/**
	 * @return Returns the useYn.
	 */
	public String getUseYn() {
		return useYn;
	}
	/**
	 * @param useYn The useYn to set.
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
}

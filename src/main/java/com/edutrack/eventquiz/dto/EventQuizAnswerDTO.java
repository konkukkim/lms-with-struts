/*
 * Created on 2004. 10. 25.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.eventquiz.dto;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EventQuizAnswerDTO {
	protected String systemCode;
    protected int quizId;
    protected String userId;
    protected int answerNo;
    protected String prizeYn;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    
 
	/**
	 * @return Returns the answerNo.
	 */
	public int getAnswerNo() {
		return answerNo;
	}
	/**
	 * @param answerNo The answerNo to set.
	 */
	public void setAnswerNo(int answerNo) {
		this.answerNo = answerNo;
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
	 * @return Returns the prizeYn.
	 */
	public String getPrizeYn() {
		return prizeYn;
	}
	/**
	 * @param prizeYn The prizeYn to set.
	 */
	public void setPrizeYn(String prizeYn) {
		this.prizeYn = prizeYn;
	}
	/**
	 * @return Returns the quizId.
	 */
	public int getQuizId() {
		return quizId;
	}
	/**
	 * @param quizId The quizId to set.
	 */
	public void setQuizId(int quizId) {
		this.quizId = quizId;
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
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
}

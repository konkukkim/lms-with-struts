/*
 * Created on 2004. 11. 18.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.progauthor.dto;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProgAuthorDTO {
	protected String systemCode ="";
	protected String workGubun = "";
	protected int progSeq = 0;
	protected String progId = "";
	protected String progName = "";
	protected String progComment = "";
	protected String viewLevel = "";
	protected String regId = "";
	protected String regDate = "";
	protected String modId = "";
	protected String modDate = "";
	
	/**
	 * 
	 */
	public ProgAuthorDTO() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return Returns the progId.
	 */
	public String getProgId() {
		return progId;
	}
	/**
	 * @param progId The progId to set.
	 */
	public void setProgId(String progId) {
		this.progId = progId;
	}
	/**
	 * @return Returns the progName.
	 */
	public String getProgName() {
		return progName;
	}
	/**
	 * @param progName The progName to set.
	 */
	public void setProgName(String progName) {
		this.progName = progName;
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
	 * @return Returns the vewLevel.
	 */
	public String getViewLevel() {
		return viewLevel;
	}
	/**
	 * @param vewLevel The vewLevel to set.
	 */
	public void setViewLevel(String vewLevel) {
		this.viewLevel = vewLevel;
	}
	/**
	 * @return Returns the workGubun.
	 */
	public String getWorkGubun() {
		return workGubun;
	}
	/**
	 * @param workGubun The workGubun to set.
	 */
	public void setWorkGubun(String workGubun) {
		this.workGubun = workGubun;
	}
	
	/**
	 * @return Returns the progNo.
	 */
	public int getProgSeq() {
		return progSeq;
	}

	/**
	 * @return Returns the progComment.
	 */
	public String getProgComment() {
		return progComment;
	}
	/**
	 * @param progComment The progComment to set.
	 */
	public void setProgComment(String progComment) {
		this.progComment = progComment;
	}
	/**
	 * @param progSeq The progSeq to set.
	 */
	public void setProgSeq(int progSeq) {
		this.progSeq = progSeq;
	}
}

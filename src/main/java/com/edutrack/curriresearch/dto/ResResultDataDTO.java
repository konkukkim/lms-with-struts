/*
 * Created on 2004. 10. 28.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curriresearch.dto;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResResultDataDTO {
    protected ArrayList userList = null;
    protected HashMap resultList = null;
    protected int contentsCnt = 0;
	/**
	 * 
	 */
	public ResResultDataDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return Returns the resultList.
	 */
	public HashMap getResultList() {
		return resultList;
	}
	/**
	 * @param resultList The resultList to set.
	 */
	public void setResultList(HashMap resultList) {
		this.resultList = resultList;
	}
	/**
	 * @return Returns the userList.
	 */
	public ArrayList getUserList() {
		return userList;
	}
	/**
	 * @param userList The userList to set.
	 */
	public void setUserList(ArrayList userList) {
		this.userList = userList;
	}
	
	/**
	 * @return Returns the contentsCnt.
	 */
	public int getContentsCnt() {
		return contentsCnt;
	}
	/**
	 * @param contentsCnt The contentsCnt to set.
	 */
	public void setContentsCnt(int contentsCnt) {
		this.contentsCnt = contentsCnt;
	}
}

/*
 * Created on 2004. 11. 5.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.connstatus.dto;

import java.util.ArrayList;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConnectResDTO {
    protected ArrayList yearData = null;
    protected String[] dateData = null;
    protected int totalCnt = 0;
    protected String name = "";
	/**
	 * 
	 */
	public ConnectResDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return Returns the dateData.
	 */
	public String[] getDateData() {
		return dateData;
	}
	/**
	 * @param dateData The dateData to set.
	 */
	public void setDateData(String[] dateData) {
		this.dateData = dateData;
	}
	/**
	 * @return Returns the yearData.
	 */
	public ArrayList getYearData() {
		return yearData;
	}
	/**
	 * @param yearData The yearData to set.
	 */
	public void setYearData(ArrayList yearData) {
		this.yearData = yearData;
	}
	
	/**
	 * @return Returns the totalCnt.
	 */
	public int getTotalCnt() {
		return totalCnt;
	}
	/**
	 * @param totalCnt The totalCnt to set.
	 */
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
}

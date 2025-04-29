/*
 * Created on 2004. 10. 18.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.coursereport.dto;

import java.util.ArrayList;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportOrderDTO {
    protected int reportOrder = 0;
    protected int score = 0;
    protected ArrayList contentsList = null;
	/**
	 * 
	 */
	public ReportOrderDTO(int order, int sc) {
		super();
		// TODO Auto-generated constructor stub
		reportOrder = order;
		score = sc;
	}

	/**
	 * @return Returns the contentsList.
	 */
	public ArrayList getContentsList() {
		return contentsList;
	}
	/**
	 * @param contentsList The contentsList to set.
	 */
	public void setContentsList(ArrayList contentsList) {
		this.contentsList = contentsList;
	}
	/**
	 * @return Returns the reportOrder.
	 */
	public int getReportOrder() {
		return reportOrder;
	}
	/**
	 * @param reportOrder The reportOrder to set.
	 */
	public void setReportOrder(int reportOrder) {
		this.reportOrder = reportOrder;
	}
	/**
	 * @return Returns the score.
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score The score to set.
	 */
	public void setScore(int score) {
		this.score = score;
	}

}

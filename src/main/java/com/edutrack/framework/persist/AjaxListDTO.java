package com.edutrack.framework.persist;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 	JSP 등에서 페이지 단위의 리스트 형태를 출력할때 사용
 *
 *	데이터 추출에 필요한 RetrieveStatement 와 현재 페이지, 현재 블록 등을 세팅한 후
 *	getListItems() 을 호출하면, 지정된 페이지에 해당하는 데이터가 String[][] 형태로
 *	리턴됨.
 *	페이지 이동 링크에는 javascript:goPage(page, block) 형태로 링크가 생성.
 *
 *	@author  saijer
 * 	@version %0%, %9%
 * 	@see     com.epiontech.persist.PersistentBroker
 */
public class AjaxListDTO implements Serializable{

	private ArrayList dataList;
	private ArrayList data1List;
	private String pagging;
	private int totalDataCount; //리스트 시작 번호

	public ArrayList getDataList() {
		return dataList;
	}
	public ArrayList getData1List() {
		return data1List;
	}
	public void setData1List(ArrayList data1List) {
		this.data1List = data1List;
	}
	public void setDataList(ArrayList dataList) {
		this.dataList = dataList;
	}
	public String getPagging() {
		return pagging;
	}
	public void setPagging(String pagging) {
		this.pagging = pagging;
	}
	public int getTotalDataCount() {
		return totalDataCount;
	}
	public void setTotalDataCount(int totalDataCount) {
		this.totalDataCount = totalDataCount;
	}


}

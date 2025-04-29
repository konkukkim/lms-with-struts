/*
 * Created on 2004. 12. 16.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.common;


/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EbizSiteNavigation extends SiteNavigation {

	/**
	 * 
	 */
    public EbizSiteNavigation(String pmode) {
		super();
		add("Home", "/Main.cmd?cmd=mainShow&pMode=Home");
		if(pmode.toLowerCase().equals("search"))
			add("e비즈니스 DB검색", "/Search.cmd?cmd=search_Ctgy&pMode=search&MENUNO=1");
		if(pmode.toLowerCase().equals("policy"))
			add("정책/통계/동향", "/ReportSearch.cmd?cmd=searchReportList&pTable=report&pWhere=policy&pTitle=정책자료&MENUNO=2");
		if(pmode.toLowerCase().equals("uni"))
			add("e-biz 대학지원사업", "/ReportSearch.cmd?cmd=searchReportList&pTable=report&pWhere=uni&pTitle=커리큘럼&MENUNO=3");
		if(pmode.toLowerCase().equals("ex"))
			add("e-biz 사례", "/ReportSearch.cmd?cmd=searchReportList&pTable=report&pWhere=ex&pTitle=국내Case&MENUNO=4");
		if(pmode.toLowerCase().equals("center"))
			add("인력개발센터자료", "/ReportSearch.cmd?cmd=searchReportList&pTable=report&pWhere=center&pTitle=인력개발교육교재&MENUNO=5");
		if(pmode.toLowerCase().equals("news"))
			add("뉴스&이벤트", "/ReportSearch.cmd?cmd=searchReportList&pTable=news&pWhere=news&pTitle=뉴스&MENUNO=0");
		if(pmode.toLowerCase().equals("new"))
			add("최신자료", "/ReportSearch.cmd?cmd=searchReportList&pTable=report&pWhere=new&pTitle=&MENUNO=0");
		if(pmode.toLowerCase().equals("best"))
			add("인기자료", "/ReportSearch.cmd?cmd=searchReportList&pTable=report&pWhere=best&pTitle=&MENUNO=0");
		
		setMode("Ebiz");
    }
    
}

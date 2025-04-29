/*
 * Created on 2004. 7. 6.
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.edutrack.framework.util.StringUtil;


public class SiteNavigation {
	private static final String LAST_START_TAG = "<strong>";
	private static final String LAST_END_TAG = "</strong>";
	//public static final String DELIMETER = " &gt; ";
	public static final String DELIMETER = " <img src=\"/img/1/common/history_blet.gif\" width=\"17\" height=\"6\"> ";
	public static final String SITE_NAVIGATION = "site_navigation";
	public static final String NEW_SITE_NAVIGATION = "site_navigation";

	protected  String MAIN_URL = "";
    protected  String mode = "";
	private List navigationList;

	/**
	 * 생성자
	 */
	public SiteNavigation() {
		navigationList = new ArrayList();
	}

	public SiteNavigation(String pmode) {
		navigationList = new ArrayList();
		add("Home", "/Main.cmd?cmd=mainShow&pMode=Home&MENU=0");
		if(pmode.equals("Info"))
			add("대학소개", "/Main.cmd?cmd=infoShow&pMode=Info&MENU=0");
		if(pmode.equals("Enter"))
			add("입학", "/Main.cmd?cmd=infoShow&pMode=Enter&MENU=0");
		if(pmode.equals("News"))
			add("학교소식", "/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=17&pMode=News&MENUNO=0");
		if(pmode.equals("Help"))
			add("고객지원", "/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=1&pMode=Help&MENUNO=0");		
		
		if(pmode.equals("MyPage"))
			add("나의학습실", "/Main.cmd?cmd=stuCurriList&pMode=MyPage&MENUNO=0");
		if(pmode.equals("Lecture"))
			add("강의실", "/LectureMain.cmd?cmd=LectureMainShow&pMode=Lecture&MENUNO=0");
		
		if(pmode.equals("Community"))
			add("동아리", "/Main.cmd?cmd=communityShow&pMode=Community&MENUNO=0");
		if(pmode.equals("CommSub"))
			add("동아리", "/Main.cmd?cmd=communityShow&pMode=Community&MENUNO=0");
		
		if(pmode.equals("Member"))
			add("회원가입","/User.cmd?cmd=userAgreeShow&pMode=Member&MENUNO=0");
		
		if(pmode.equals("SiteMap"))
			add("SITEMAP", "/Main.cmd?cmd=siteMapShow&pMode=SiteMap&pMENUNO=0");

		setMode(pmode);
	}
	
	/**
	 * db 에 저장된 타이틀을 가져오기..
	 * @param request
	 * @param navigationText
	 * @return
	 */
	public SiteNavigation add(HttpServletRequest request, String navigationText) {
		String[] sNaviTitle = CommonUtil.getNaviTitle(request);
		SiteNavigation returnNavi = null;
		
		if(sNaviTitle[0]!=null && !("").equals(sNaviTitle[0])) returnNavi = add(sNaviTitle[0],null);
		if(sNaviTitle[1]!=null && !("").equals(sNaviTitle[1])) returnNavi = add(sNaviTitle[1],null);
		if(returnNavi==null) returnNavi = add(navigationText, null);
	
		return returnNavi;
	}

	public SiteNavigation add(String navigationText) {
		return add(navigationText, null);
	}

	public SiteNavigation add(String navigationText, String navigationLink) {
	    if(navigationLink != null) navigationLink = MAIN_URL + navigationLink;
		String[] navigationSet = { navigationText, navigationLink };
		navigationList.add(navigationSet);
		return this;
	}

	public String getSiteNavigation() {
		StringBuffer buf = new StringBuffer(96);

		Iterator ie = navigationList.iterator();

		if (ie.hasNext()) {
			generateNavigation(buf, ie.next());
		}

		for (; ie.hasNext();) {
			buf.append(DELIMETER);
			generateNavigation(buf, ie.next(), !ie.hasNext());
		}
		return buf.toString();
	}

	private void generateNavigation(StringBuffer buf, Object navigation, boolean isLast) {
		String[] navigationSet = (String[])navigation;

		if (isLast) {
			buf.append(wrapLinkTag(navigationSet[0], navigationSet[1]));
		} else {
			buf.append(wrapLinkTag(navigationSet[0], navigationSet[1]));
		}
	}

	public String wrapLastTag(String content) {
		return new StringBuffer(LAST_START_TAG).append(content).append(LAST_END_TAG).toString();
	}

	public String wrapLinkTag(String content, String link) {
		content = StringUtil.outDataConverter(content);

		if (link == null) {
			return content;
		}

		return new StringBuffer("<a href=\"")
			.append(link)
			.append("\">")
			.append(content)
			.append("</a>")
			.toString();
	}

	private void generateNavigation(StringBuffer buf, Object navigation) {
		generateNavigation(buf, navigation, false);
	}

	public SiteNavigation link(Map model) {
		model.put(SITE_NAVIGATION, getSiteNavigation());
		model.put("pMode", mode);
		return this;
	}

	/**
	 * @return Returns the pMode.
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @param mode The pMode to set.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
}

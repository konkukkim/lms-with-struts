/*
 * Created on 2004. 8. 10.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.common;


/*********************************************************
 * 프로그램 : CourseSiteNavigation.java
 * 모 듈 명 : 
 * 설    명 : 
 * 테 이 블 :
 * 작 성 자 : chorongjang
 * 작 성 일 : 2004. 8. 10.
 * 수 정 일 :
 * 수정사항 : 
 *********************************************************/
public class CurriSiteNavigation extends SiteNavigation{

    /**
     * 
     */
    public CurriSiteNavigation(String pmode) {
        super();
        add("Home", "/Main.cmd?cmd=mainShow&pMode=Home");
        add("강의실", "/LectureMain.cmd?cmd=LectureMainShow&pMode=Lecture");
		setMode(pmode);
    }

}



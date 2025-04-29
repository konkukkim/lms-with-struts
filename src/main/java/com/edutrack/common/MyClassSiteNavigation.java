/*
 * Created on 2004. 9. 16.
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
public class MyClassSiteNavigation extends SiteNavigation{

	/**
	 * 
	 */
    public MyClassSiteNavigation(String pmode) {
        super();
        add("강의실", "#");
		setMode(pmode);
    }

	 
}

/*
 * Created on 2004. 9. 16.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.common.dto;

import java.io.Serializable;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserMemDTO implements Serializable{
	public String systemCode = "";
	public String userId = "";
    public String userName = ""; 
    public String userType = "";
    public String sessionId = "";
    public String userIp = "";
    public String deptDaeCode = "";
    public String deptSoCode = "";
    public long conTime = 0;
    public int conCnt = 0;
    public String curMenuNo = "";
    public String schoolYear = "";
    public CurriMemDTO curriInfo = null; 
    public CommMemDTO commInfo = null;
    public boolean deptManager = false;
}

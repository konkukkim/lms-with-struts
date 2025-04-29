/*
 * Created on 2005. 11. 7.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.framework.util;

/**
 * @author 장철웅
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class UserAuthentication extends Authenticator {
	PasswordAuthentication pa;
	
	public UserAuthentication(String id, String password) {
		pa = new PasswordAuthentication(id, password);
	}
	
	
	public PasswordAuthentication getPasswordAuthentication() {
		return pa;
	}
}	

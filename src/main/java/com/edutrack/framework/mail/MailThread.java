/*
 * Created on 2003-07-17
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.edutrack.framework.mail;


/**
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MailThread implements Runnable {
	String toAddr = null;  
	String toName = null;
	String fromAddr = null;
	String fromName = null;
	String subject = null;
	String body = null;
	
	public MailThread(String toAddr, String toName, String fromAddr, String fromName, String subject, String body){
		this.toAddr = toAddr;
		this.toName = toName;
		this.fromAddr = fromAddr;
		this.fromName = fromName;
		this.subject = subject; 
		this.body = body;
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		MailManager.getInstance().sendSimpleMail(this.toAddr, this.toName, this.fromAddr, this.fromName, this.subject, this.body);
	}

}

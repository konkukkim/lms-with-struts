package com.edutrack.framework.mail;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.edutrack.framework.Constants;
import com.edutrack.framework.GenericManager;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;

public class MailManager extends GenericManager implements Constants
{
//	private static Log log = LogFactory.getLog(MailManager.class);
	
	private static final String MAIL_HOST = "mail.host";
	private static final String MAIL_TRNS = "mail.transport.protocol";
	
	private static MailManager instance = null;
	private static boolean mailDebug = false;
	
	
	private Properties  props = null;
	
	private MailManager() 
	{
		initialize();
	}
	

	public static MailManager getInstance() 
	{
		if (instance == null)
		{
			synchronized(MailManager.class)
			{
				instance = new MailManager();
			}
		}
		return instance;
	}
	
	/**
	 * 간단한 메일을 전송한다.
	 * 
	 * @param toAddress 수신자 전자우편 주소
	 * @param toName 수신자명 
	 * @param fromAddress 송신자 전자우편주소
	 * @param fromName	송신자명 
	 * @param subject 메일 제목 
	 * @param mesgBody 메일 본문
	 * @throws MessagingException
	 */
	public void sendSimpleMail(String toAddress, String toName, 
														String fromAddress, String fromName, 
														String subject, String mesgBody)
	{
		try
		{
			SimpleEmail mail = new SimpleEmail();
			mail.addTo(toAddress, toName);
			mail.setFrom(fromAddress, fromName);
			mail.setSubject(subject);
			mail.setMessage(mesgBody);
			send(mail);
						
		} catch(MessagingException me)
		{
			log.error("sendSimpleMail error!!");
			log.error(me.getMessage());
		}
	}
	
	/**
	 * SimpleEmail 객체나 MultiPartEmail 메일을 전송한다. 
	 * 
	 * @param mail
	 * @throws MessagingException
	 */
	public void sendMail(BaseEmail mail)
	{
		try
		{
			send(mail);
			
		} catch(MessagingException me)
		{
			log.error("send error!!");
			log.error(me.getMessage());
		}
	}
	
	private void send(BaseEmail mail) throws MessagingException
	{
		if (mail == null) return;
		try
		{
			mail.send();
			log.debug("mail send ok");
		} catch(MessagingException me)
		{
			log.error(me.getMessage());
		}
	}
	
	/**
	 * Configuration 파일에서 메일 서버와 전송 프로토콜을 읽어
	 * 셋팅한다.
	 *
	 */
	public void initialize()
	{
		this.props = new Properties();
		String mailHost = config.getString(KEY_MAIL_HOST, "localhost");
		String mailTrns = config.getString(KEY_MAIL_TRANS, "smtp");

		props.put(MAIL_HOST, mailHost);
		props.put(MAIL_TRNS, mailTrns);
		
		mailDebug = config.getBoolean(KEY_MAIL_DEBUG, false);
	}

	protected Properties getMailProps()
	{
		return props;
	}
   
   /**
    * 메일 세션을 얻어온다. 세션을 위한 속성은 초기화시
    * 로댕했더 메일 설정정보를 이용한다.
    * 
    * @return
    */
	protected Session getMailSession()
	{
		Properties mailProps = getMailProps();
		Authenticator auth = new PopupAuthenticator();
		
//		Session session = Session.getDefaultInstance(mailProps, auth);
		Session session = Session.getDefaultInstance(mailProps, null);
		session.setDebug(mailDebug);
		
		return session;    		
	}
}

class PopupAuthenticator extends Authenticator  implements Constants{

  public PasswordAuthentication getPasswordAuthentication() {
	String username = null;
	String password = null;
	Configuration config = ConfigurationFactory.getInstance().getConfiguration();
	
	username = config.getString(KEY_MAIL_USER, null);
	password = config.getString(KEY_MAIL_PASSWORD, null);
	
	return new PasswordAuthentication(username, password);
  }

}


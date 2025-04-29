package com.edutrack.framework.mail;

import java.util.Date;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.edutrack.framework.Constants;

/**
 * 이클래스는 메일을 추상화한 클래스이다. 
 * 
 * @author 
 *
 */
public abstract class BaseEmail implements Constants 
{
	public static final String TEXT_HTML = "text/html";
	public static final String TEXT_PLAIN = "text/plain";
	
	
	private Vector toList;
	private Vector ccList;
	private Vector bccList;
	private Vector replyList;
	protected Message message;
	
	/**
	 * MailManager에 등록된 메일세션 정보를 이용해서 
	 * Message 오브젝트를 생성하고, 수신자 리스트 백터를 
	 * 초기화한다.
	 * 
	 * @throws MessagingException
	 */
	protected void init() throws MessagingException 
	{
		message = new MimeMessage(MailManager.getInstance().getMailSession());
		message.setSentDate(new Date());
		toList = new Vector();
		ccList = new Vector();
		bccList = new Vector();
		replyList = new Vector();
	}
	
	/**
	 * 메일 메시지의 제목을 셋팅
	 * @param subject
	 * @return
	 * @throws MessagingException
	 */
	public BaseEmail setSubject(String subject) throws MessagingException 
	{
		message.setSubject(subject);
		return this;    
	}
	
	/**
	 * 메일 송신자를 셋팅한다. 송신자 이름이 null 경우,
	 * email 주소를 사용한다. 
	 * 
	 * @param email 송신자 전자우편주소 
	 * @param name 송신자 이름
	 * @return
	 * @throws MessagingException
	 */
	public BaseEmail setFrom(String email, String name) 
	throws MessagingException 
    {
        try
		{
            if( (name == null) || (name.trim().equals("")) )
                name = email;
            // message.setFrom(new InternetAddress( email, new String(name.getBytes("8859_1"),"EUC_KR")) );
			message.setFrom(new InternetAddress( email, name, "EUC_KR"));
            // message.setFrom(new InternetAddress( email ) );
        }
        catch( Exception e )
        {
            throw new MessagingException("Cannot set from mail", e);
        }
        return this;    
   }
	/**
	 * 메일 수신자를 추가한다.  
	 *  
	 * @param email 수신자 전자우편주소 
	 * @param name 수신자 이름 
	 * @return
	 * @throws MessagingException
	 */
	public BaseEmail addTo(String email, String name) 
		throws MessagingException 
   {
        try
        {
            if( (name == null) || (name.trim().equals("")) ) {
                name = email;
            }
            toList.addElement( new InternetAddress( email, name ) );
        }
        catch( Exception e )
        {
            throw new MessagingException("Cannot add to mail", e);
        }
        return this;    
   }
   
   /**
    * 참조 수신자 (CC)를 추가한다.
    * 
    * @param email 참조 수신자 전자우편주소 
    * @param name 참조 수신자 이름 
    * @return
    * @throws MessagingException
    */
	public BaseEmail addCc(String email, String name) 
		throws MessagingException 
   {
        try
        {
            if( (name == null) || (name.trim().equals("")) ) {
                name = email;
            }
            ccList.addElement( new InternetAddress( email, name ) );
        }
        catch( Exception e )
        {
            throw new MessagingException("Cannot add cc mail", e);
        }
        return this;    
   }
	
	/**
	 * 숨은 참조수신자(BCC)를 추가한다.
	 * 
	 * @param email 숨은 참조수신자 전자우편주소 
	 * @param name 숨은 참조수신자 이름
	 * @return
	 * @throws MessagingException
	 */
	public BaseEmail addBcc(String email, String name) 
		throws MessagingException 
   {
        try
        {
            if( (name == null) || (name.trim().equals("")) )
            {
                name = email;
            }
            bccList.addElement( new InternetAddress( email, name ) );
        }
        catch( Exception e )
        {
            throw new MessagingException("Cannot add bcc mail", e);
        }
        return this;    
   }
	
	/**
	 * 송신자의 회신주소(replay address)를 셋팅한다.
	 * 
	 * @param email 회신 전자우편 주소 
	 * @param name 회신 이름
	 * @return
	 * @throws MessagingException
	 */
	public BaseEmail addReply(String email, String name) 
		throws MessagingException 
	{
        try
        {
            if( (name == null) || (name.trim().equals("")) ) {
                name = email;
            }
            replyList.addElement( new InternetAddress( email, name)); 
            	
        }
        catch( Exception e )
        {
            throw new MessagingException("Cannot add cc mail", e);
        }
        return this;    
   }
	/**
	 * Message 객체의 전송일자를 셋팅한다. 기본으로 현재 날자가 등록된다. 
	 * 
	 * @param date
	 * @return
	 * @throws MessagingException
	 */
	public BaseEmail setSentDate(Date date) throws MessagingException 
	{
		message.setSentDate( date );
		return this;
	}
   
   /**
    * 메일전송을 위한 추상화 메소드, 이 클래스를 상속받은 클래스에서 구체화 
    * 시킨다.
    * 
    * @param mesgBody 메일 메시지 본문
    * @return
    * @throws MessagingException
    */
   public abstract BaseEmail setMessage(String mesgBody) throws MessagingException;
   
   /**
    * 현 객체에 셋팅된 정보를 바탕으로 메일을 전송한다.
    * 
    * @throws MessagingException
    */
   public void send() throws MessagingException 
   {
        message.setRecipients(Message.RecipientType.TO, toInternetAddressArray(toList));                              
        message.setRecipients(Message.RecipientType.CC, toInternetAddressArray(ccList));
        message.setRecipients(Message.RecipientType.BCC, toInternetAddressArray(bccList));
        message.setReplyTo(toInternetAddressArray(replyList));

        Transport.send( message );    
   }
   
	// 백터를 InternetAddress 배열로 변경
	private InternetAddress[] toInternetAddressArray(Vector v) 
	{
        int size = v.size();

        InternetAddress[] ia = new InternetAddress[size];
        for (int i=0; i<size; i++)
        {
            ia[i] = (InternetAddress)v.elementAt(i);
        }
        return ia;
	}
   
}

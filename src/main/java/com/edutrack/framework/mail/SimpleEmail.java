package com.edutrack.framework.mail;

import javax.mail.MessagingException;



/**
 * 이 클래스는 첨부파일 없는 일반적인 메일처리를 위해서 작성된
 * 클래스이다. 
 * 
 * 
 * @author 
 *
 */
public class SimpleEmail extends BaseEmail
{
	private String content_type;	
	
	public SimpleEmail() throws MessagingException 
	{
		super.init();
		this.content_type = "text/html;charset=euc-kr";
	}
	
	/**
	 *  메일 본문의 content type 을 지정한다. text/html, text/plain
	 * 
	 * @param contentType
	 */
	public void setContentType(String contentType) 
	{
		content_type = contentType;    
	}
	
	/**
	 * 메일본문을 셋팅한다. 내부적으로 Message 객체의 setContent()
	 * 를 호출한다.
	 */
	public BaseEmail setMessage(String mesgBody) throws MessagingException 
	{
	    message.setContent( mesgBody, content_type );
	    return this;    
	}
}

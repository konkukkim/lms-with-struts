package com.edutrack.framework.mail;

import java.net.URL;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class MultiPartEmail extends BaseEmail  
{
	protected MimeMultipart emailBody;
	protected MimeBodyPart main;
	
   public MultiPartEmail() throws MessagingException 
   {
        this.init();    
   }  
	
   protected void init() throws MessagingException 
   {
        super.init();

        emailBody = new MimeMultipart();
        

        message.setContent( emailBody );


        main = new MimeBodyPart();
        emailBody.addBodyPart(main);    
   }
	
   public BaseEmail setMessage(String msgBody) throws MessagingException 
   {
        main.setText(msgBody);
        return this;    
   }

	/**
	 * 메일에 첨부파일을 추가한다.
	 *  
	 * @param attachment
	 * @return
	 * @throws MessagingException
	 */	
	public MultiPartEmail attach(EmailAttachment attachment) throws MessagingException 
	{
    	URL url = attachment.getURL();
        if( url == null )
        {
            try
            {
                String file = attachment.getPath();
                url = new URL( "file", null , file );
            }
            catch( Exception e)
            {
                throw new MessagingException("Cannot find file", e);
            }
        }

        return attach(url,
                      attachment.getName(),
                      attachment.getDescription(),
                      attachment.getDisposition() );    
   }
	
	/**
	 * 메일에 첨부파일을 추가한다.
	 * @param url
	 * @param name
	 * @param description
	 * @param disposition
	 * @return
	 * @throws MessagingException
	 */
	public MultiPartEmail attach(URL url, String name, String description, String disposition) 
   		throws MessagingException 
	{
        MimeBodyPart mbp = new MimeBodyPart();
        emailBody.addBodyPart( mbp );

        mbp.setDisposition( disposition );
        mbp.setFileName ( name );
        mbp.setDescription ( description );
        mbp.setDataHandler ( new DataHandler( new URLDataSource(url) ) );

        return this;    
   }

	/**
	 * 메일에 첨부파일을 추가한다.
	 * 
	 * @param path 파일 데이터소스 경로
	 * @param name 메일파트에 추가할 파일명
	 * @return
	 * @throws MessagingException
	 */
	public MultiPartEmail attach(String path, String name) 
		throws MessagingException 
	{
		MimeBodyPart mbp = new MimeBodyPart();
		emailBody.addBodyPart(mbp);
		
		DataSource source = 
      	new FileDataSource(path);
      
      mbp.setDataHandler(new DataHandler(source));
      mbp.setFileName(name);
      
      
		return this;    
	}
}

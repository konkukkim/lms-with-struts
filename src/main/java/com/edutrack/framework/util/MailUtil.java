/*
 * Created on 2003-07-17
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.edutrack.framework.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.edutrack.common.dao.CommonDAO;
import com.edutrack.framework.Constants;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.logging.Log;
import com.edutrack.framework.logging.LogFactory;
import com.edutrack.framework.mail.MailManager;
import com.edutrack.framework.mail.MailThread;
import com.edutrack.message.dto.MessageDTO;
import com.edutrack.user.dto.UsersDTO;


/**
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MailUtil {
	private static Log log = null;
	private static Configuration config = null;
	private static String fromName = null;
	private static String fromAddr = null;
	private static String serverUrl = null;
	
	private static String mailTop = "";
	private static String mailBottom = "";

	static{
		log = LogFactory.getLog(MailUtil.class);
		config = ConfigurationFactory.getInstance().getConfiguration();
		//fromName = config.getString("framework.mail.fromName");
		fromName = "사이버노동대학";
		fromAddr = config.getString("framework.mail.fromAddr");
		serverUrl = config.getString("framework.system.server_url");
		
		StringBuffer sbTop = new StringBuffer();
//		sbTop.append("<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"> \r\n");
//		sbTop.append("  <tr> \r\n");
//		sbTop.append("    <td align=\"center\">\r\n");
//		sbTop.append("      <table width=\"700\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" background=\""+serverUrl+"/img/1/common/mail05.gif\"> \r\n");
//		sbTop.append("        <tr>  \r\n");
//		sbTop.append("          <td height=\"90\" colspan=\"2\" align=\"center\" valign=\"bottom\" bgcolor=\"#FFFFFF\"><img src=\""+serverUrl+"/img/1/common/mail01.gif\" width=\"700\" height=\"56\"></td> \r\n");
//		sbTop.append("        </tr> \r\n");
//		sbTop.append("        <tr>  \r\n");
//		sbTop.append("          <td align=\"center\" valign=\"top\">  \r\n");
		mailTop = sbTop.toString();
		
		StringBuffer sbBottom = new StringBuffer();
//		sbBottom.append("            </td> \r\n");
//		sbBottom.append("        </tr> \r\n");
//		sbBottom.append("        <tr align=\"left\" valign=\"top\" bgcolor=\"#FFFFFF\">  \r\n");
//		sbBottom.append("          <td height=\"90\" colspan=\"2\"><img src=\""+serverUrl+"/img/1/common/mail04.gif\" width=\"700\" height=\"57\"></td> \r\n");
//		sbBottom.append("        </tr> \r\n");
//		sbBottom.append("      </table></td> \r\n");
//		sbBottom.append("  </tr> \r\n");
//		sbBottom.append("</table> \r\n");
		mailBottom = sbBottom.toString();
		
		log.debug("Init : " + fromName + "," + fromAddr + "," +serverUrl);
	}
	
	public static void sendMail(String toAddr, String toName, String subject, String body){
		if(toAddr == null || toName == null || subject == null || body == null)
			return;
		 
		log.debug("'"+toAddr +"','"+toName+"','"+subject+"','"+body);
		
		MailThread mailThread = new MailThread(toAddr, toName, fromAddr, fromName, subject, body); 
		Thread t = new Thread(mailThread);
		t.start();
	 
	}

	public static void sendDirectMail(String toAddr, String toName, String subject, String body){
		if(toAddr == null || toName == null || subject == null || body == null) return;
		
		log.debug("'"+toAddr +"','"+toName+"','"+subject+"','"+body);
			
	        MailManager mail = MailManager.getInstance();
	        
	        mail.sendSimpleMail(toAddr, toName, fromAddr,fromName, subject, mailTop+StringUtil.nvl(body)+mailBottom);	 
	}
	
	public static void sendMailBatch(ArrayList toAddrList, ArrayList toNameList, ArrayList subjectList,ArrayList bodyList){		
		if(toAddrList == null || toNameList == null || subjectList == null || bodyList == null)
			return;
					
		String fileName = "";
		int len1 = toAddrList.size();
		int len2 = toNameList.size();
		int len3 = subjectList.size();
		int len4 = bodyList.size();

		if(StringUtil.isNotNull(fileName) && len1 == len2 && len2==len3 && len3==len4 && len1 > 0) {
			log.debug("Template processing...");
			MailManager mng = MailManager.getInstance();

			int size = toAddrList.size();
			log.debug("Batch size : " + size);
			for(int i=0; i<size; i++){
				String toAddr = (String)toAddrList.get(i);
				String toName = (String)toNameList.get(i);
				String subject = (String)subjectList.get(i);
				String body = (String)bodyList.get(i);
				
				log.debug(i+"'th '"+toAddr +"','"+toName+"','"+subject);
				
				mng.sendSimpleMail(toAddr, toName, fromAddr, fromName, subject, mailTop+StringUtil.nvl(body)+mailBottom);
				
			}
		}
	}
	/**
	 * 메일보내기.....
	 * @param systemCode
	 * @param messageDto
	 * @param userList
	 * @param fileLoc
	 * @return
	 * @throws Exception
	 */
	public static int mailSendResult(String systemCode, MessageDTO messageDto, ArrayList userList, String fileLoc) throws Exception {      
            
	    log.debug("*************** Send Mail ****************");
	    
        Properties props = System.getProperties();
        props.put("mail.smtp.host", config.getString(Constants.KEY_MAIL_HOST));
        //UserAuthentication auth = new UserAuthentication("", "");//-- 아이디와 패스워드 넣기
        //Session ss = Session.getDefaultInstance(props, auth);
        Session ss = Session.getDefaultInstance(props, null); 
        String	from	=	"";
        Message msg = new MimeMessage(ss);
        UsersDTO userDto = null;
        CommonDAO commonDao = new CommonDAO();
        from = commonDao.getEmail(systemCode,messageDto.getRegId());
        int	cnt	=	0;
        boolean errorChk =	false;
        log.debug("*************** Send Mail 2...*"+from);
        for(int i=0;i < userList.size();i++){
            errorChk =	false;
            try{
	            userDto = (UsersDTO)userList.get(i); 
	            InternetAddress[] address = {new InternetAddress(StringUtil.nvl(userDto.getEmail()), StringUtil.inDataConverter(userDto.getUserName()))};
	            InternetAddress  address2 = new InternetAddress();
	            address2.setAddress(from);
	            address2.setPersonal(StringUtil.inDataConverter(messageDto.getSenderName()));
	            log.debug("Sendmail User : "+ StringUtil.inDataConverter(messageDto.getSenderName()));
	            msg.setFrom(address2);
	            msg.setRecipients(Message.RecipientType.TO, address);
				msg.setSubject(StringUtil.inDataConverter(messageDto.getSubject()));
				
				msg.setContent(mailTop+StringUtil.nvl(messageDto.getContents())+mailBottom,"text/html; charset=euc-kr");
				
				if(!fileLoc.equals("")){
				    Multipart mt	=	new MimeMultipart();
					MimeBodyPart mbp1	=	new MimeBodyPart();
					MimeBodyPart mbp2	=	new MimeBodyPart();	
					
					FileDataSource  fds = new FileDataSource(fileLoc);
										
					mbp1.setContent(mailTop+StringUtil.nvl(messageDto.getContents())+mailBottom,"text/html; charset=euc-kr"); // HTML 형식
					
					mbp2.setDataHandler(new DataHandler(fds));
					mbp2.setFileName(StringUtil.ksc2asc(fds.getName()));										
					mt.addBodyPart(mbp1);
					mt.addBodyPart(mbp2);
					msg.setContent(mt);
				}
				msg.setSentDate(new Date());				
				Transport.send(msg);			
            }catch(MessagingException e){
                	log.debug("SendMail Fail..............."+ e.getMessage());
                	errorChk =	true;
            }catch(Exception e){
                log.debug(e.getMessage()+"**"+userDto.getEmail());
                errorChk =	true;
            }finally{
                if(!errorChk) cnt++;                	
            }
        }
        return cnt;
          
    }
	
	/**
	 * 메일보내기.....
	 * @param systemCode
	 * @param toAddr
	 * @param toName
	 * @param subject
	 * @param body
	 * @param fileLoc
	 * @return
	 * @throws Exception
	 */
	public static boolean mailSendResult(String systemCode, String toAddr, String toName, String subject, String body, String fileLoc) throws Exception {      
        
	    boolean retVal = false;
	    Properties props = System.getProperties();
        props.put("mail.smtp.host", "mediopia.co.kr");        
        //UserAuthentication auth = new UserAuthentication("", "");
        //Session ss = Session.getDefaultInstance(props, auth);
        Session ss = Session.getDefaultInstance(props, null);      
        Message msg = new MimeMessage(ss);                
        try{           
            InternetAddress[] address = {new InternetAddress(StringUtil.nvl(toAddr), StringUtil.inDataConverter(toName))};
            InternetAddress  address2 = new InternetAddress();
            address2.setAddress(fromAddr);
            address2.setPersonal(fromName);            
            msg.setFrom(address2);
            msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(StringUtil.inDataConverter(subject));
			msg.setContent(StringUtil.nvl(body),"text/html; charset=euc-kr");			 
			if(!fileLoc.equals("")){
			    Multipart mt	=	new MimeMultipart();
				MimeBodyPart mbp1	=	new MimeBodyPart();
				MimeBodyPart mbp2	=	new MimeBodyPart();	
				
				FileDataSource  fds = new FileDataSource(fileLoc);
									
				mbp1.setContent(mailTop+StringUtil.nvl(body)+mailBottom,"text/html; charset=euc-kr"); // HTML 형식
				
				mbp2.setDataHandler(new DataHandler(fds));
				mbp2.setFileName(StringUtil.ksc2asc(fds.getName()));										
				mt.addBodyPart(mbp1);
				mt.addBodyPart(mbp2);
				msg.setContent(mt);
			}			 
			msg.setSentDate(new Date());			
			Transport.send(msg);
			retVal = true;
        }catch(MessagingException e){
            log.debug(e.getMessage());
            retVal = false;            
        }
        
        return retVal;
    }
	
}

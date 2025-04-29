/*
 * Created on 2004. 10. 23.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.common.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.VBN.VBN_files;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.curristudent.dao.CurriStudentDAO;
import com.edutrack.curristudent.dao.StudentDAO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.MailUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.edutrack.message.dao.PopMessageDAO;
import com.edutrack.message.dto.MessageDTO;
import com.edutrack.user.dao.UserAdminDAO;
import com.edutrack.user.dto.UsersDTO;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author epitaph
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommonMailAction extends StrutsDispatchAction{

    /**
     *
     */
    public CommonMailAction() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
    public ActionForward mailWrite (ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
        String systemCode = UserBroker.getSystemCode(request);
        String pMessageKb	= StringUtil.nvl(request.getParameter("pMessageKb"));
	    String pMessageWhere = StringUtil.nvl(request.getParameter("pMessageWhere"));
	    String pMessageWhereSub = StringUtil.nvl(request.getParameter("pMessageWhereSub"));
	    String pResultCode1 = StringUtil.nvl(request.getParameter("pResultCode1"));
	    String pResultCode2 = StringUtil.nvl(request.getParameter("pResultCode2"));
	    String pResultCode3 = StringUtil.nvl(request.getParameter("pResultCode3"));
	    String pKb		=	"";
	    if(pMessageKb.equals("S")){		// 선택해서 메일을 보낼경우...
	        String[] pCHK		=	request.getParameterValues("pCHK");
		    String[] pCHK_SPLIT	=	{"","",""};
		    UsersDTO	usersDto = null;
		    ArrayList chkList = new ArrayList();
		    log.debug("+++++++++++++++++ pCHK.length = "+pCHK.length);
		    for(int i=0;i < pCHK.length;i++){
		    	log.debug("+++++++++++++++++ pCHK[i] = "+pCHK[i]);
		    	log.debug("+++++++++++++++++ pCHK[i].indexOf(\"^\") = "+pCHK[i].indexOf("^"));
		        if(pCHK[i].indexOf("^") != -1){
		            pCHK_SPLIT = StringUtil.split(pCHK[i], "^");
		            usersDto = new UsersDTO();
		            usersDto.setUserId(pCHK_SPLIT[0]);
		            usersDto.setUserName(pCHK_SPLIT[1]);
		            usersDto.setEmail(pCHK_SPLIT[2]);
		            chkList.add(usersDto);
		        }
		    }
		    log.debug("+++++++++++++++++ chkList.size = "+chkList.size());
		    model.put("pCHK",chkList);

	    }else{
	        int	totCnt		=	0;
	        if(pMessageWhere.equals("Lecture")){	// 강의실...
	            StudentDAO studentDao = new StudentDAO();
		        UserMemDTO userMemDto = UserBroker.getUserInfo(request);
			    CurriMemDTO curriMemDto = userMemDto.curriInfo;
			    String pCurriCode = curriMemDto.curriCode;
				int pCurriYear = Integer.parseInt(curriMemDto.curriYear);
				int pCurriTerm = Integer.parseInt(curriMemDto.curriTerm);
				if(pMessageWhereSub.equals("Student")){
				    pKb = "confirmPass";
				    totCnt = studentDao.getTotCount(systemCode,pCurriCode, pCurriYear, pCurriTerm, pKb);
				}else{	//	과제...시험...토론....조건...제시...

				}
				model.put("totCnt", Integer.toString(totCnt));
	        }else{									// 강의실 밖..사용자관리..
	           UserAdminDAO useradminDao = new UserAdminDAO();
	           totCnt = useradminDao.getUserCount(systemCode, pResultCode1, pResultCode2, pResultCode3);

	           model.put("totCnt", Integer.toString(totCnt));
	        }
	    }

	    model.put("pKb", pKb);
	    model.put("pMessageKb", pMessageKb);
	    model.put("pMessageWhere", pMessageWhere);
	    model.put("pMessageWhereSub", pMessageWhereSub);
	    model.put("pResultCode1", pResultCode1);
	    model.put("pResultCode2", pResultCode2);
	    model.put("pResultCode3", pResultCode3);

	    new SiteNavigation(LECTURE).add(request,"메일").link(model);
	    return forward(request, model, "/common/mailWrite.jsp");
    }

    /**
     *
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
    public ActionForward mailSend (ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
        String systemCode = UserBroker.getSystemCode(request);
	    UserMemDTO	usermemdto = UserBroker.getUserInfo(request);
	    String	pUserId	=	usermemdto.userId;
	    String	pUserName = StringUtil.nvl(usermemdto.userName);
	    String  pRealFileName = StringUtil.inDataConverter(request.getParameter("pRealFileName"));
	    log.debug("&&&&&&&&&&&&&&&&&&&="+pUserName);
	    MultipartRequest multipartRequest = null;
	    String RegMonth = CommonUtil.getCurrentDate().substring(0,6);
	    String	FilePath = FileUtil.FILE_DIR+systemCode+"/mail/"+RegMonth+"/";

	    UploadFiles uploadEntity	=	FileUtil.upload(request, FilePath, pRealFileName, "M");
	    //UploadFiles uploadEntity	=	FileUtil.upload(request, FilePath, pUserId, "M");

	    multipartRequest = uploadEntity.getMultipart();
	    String status = uploadEntity.getStatus();

	    String 	pMessageKb	= StringUtil.nvl(multipartRequest.getParameter("pMessageKb"));
	    String 	pMessageWhere = StringUtil.nvl(multipartRequest.getParameter("pMessageWhere"));
	    String 	pMessageWhereSub = StringUtil.nvl(multipartRequest.getParameter("pMessageWhereSub"));
	    String 	pResultCode1 = StringUtil.nvl(multipartRequest.getParameter("pResultCode1"));
	    String 	pResultCode2 = StringUtil.nvl(multipartRequest.getParameter("pResultCode2"));
	    String 	pResultCode3 = StringUtil.nvl(multipartRequest.getParameter("pResultCode3"));
	    String	pKb	=	StringUtil.nvl(multipartRequest.getParameter("pKb"));

	    String pFILE_NEW1_ori = StringUtil.nvl(multipartRequest.getParameter("pFILE_NEW1_ori"));

	    String[] pCHK		=	multipartRequest.getParameterValues("pCHK");
	    String	pSUBJECT	=	StringUtil.nvl(multipartRequest.getParameter("pSubject"));
	    String	pCONTENTS	=	StringUtil.nvl(multipartRequest.getParameter("pContents"));
	    String pKeyword 		= 	StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
		String pEditorType		=	StringUtil.nvl(multipartRequest.getParameter("pEditorType"));

	    PopMessageDAO popmessageDao = new PopMessageDAO();
	    MessageDTO 	 messageDto = new MessageDTO();

	    String pRFILENAME = "";
		String pSFILENAME = "";
		String pFILEPATH = "";
		String pFILESIZE = "";

		String rFileName = "";
		String sFileName = "";
		long fileSize = 0;
		String filePath = "";

		log.debug("*******************="+pMessageKb);
		if (status.equals("E")){
			log.debug("첨부 파일 올리려다 실패하였습니다.");
		}else if (status.equals("O")){
			log.debug("첨부하신 파일이 용량을 초과했습니다.");
		}else if (status.equals("I")){
			log.debug("첨부 파일의 정보가 잘못되었습니다.");
		}else if(status.equals("S")){
			// 업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.

			log.debug("첨부 파일을 첨부하는데 성공하였습니다.");

			if(!pFILE_NEW1_ori.equals("")) {	/** 웹에디터에서 첨부파일을 첨부시 인식하는걸 막기위해 **/
				ArrayList files = uploadEntity.getFiles();
				UploadFile file = null;

				for(int i = 0 ; i < files.size(); i++){
					file = (UploadFile)files.get(i);

					if(pFILE_NEW1_ori.indexOf(StringUtil.nvl(file.getRootName())) != -1) {
						rFileName = StringUtil.nvl(file.getRootName());
						sFileName = file.getUploadName();
						fileSize = file.getSize();
						filePath = uploadEntity.getUploadPath();
					}
				}
			}

			if(!rFileName.equals("")) {			//파일첨부를 했을경우
				messageDto.setRfileName(rFileName);
				messageDto.setSfileName(sFileName);
				messageDto.setFilePath(filePath);
				messageDto.setFileSize(Double.toString(fileSize));
			} else {								//파일첨부를 하지 않았을 경우
				messageDto.setRfileName(pRFILENAME);
				messageDto.setSfileName(pSFILENAME);
				messageDto.setFilePath(pFILEPATH);
				messageDto.setFileSize(pFILESIZE);
			}

			log.debug("파일업로드를 정상적으로 종료 후 DTO에 값 담는다.");
		}
		 log.debug("**************여기.1.....*"+pKb);
//		----------- 웹에디터 시작 ------------------------------------
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH+FilePath;
		String WeasFileUrl =  ServiceUrl+"/data/"+FilePath;
		log.debug("WeasFilePath = "+WeasFilePath);
		VBN_files v_objFile = null;

		java.io.File dir = new java.io.File(WeasFilePath);
		if (!dir.exists())	dir.mkdirs();

		v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
		pCONTENTS = v_objFile.VBN_uploadMultiFiles(pCONTENTS, multipartRequest);
		//----------- 웹에디터 끝 ------------------------------------
		messageDto.setSystemCode(systemCode);
		messageDto.setSubject(pSUBJECT);
		messageDto.setContents(pCONTENTS);
		messageDto.setRegId(pUserId);
		messageDto.setModId(pUserId);
		messageDto.setKeyword(pKeyword);
		messageDto.setEditorType(pEditorType);
		messageDto.setSenderName(pUserName);
		 log.debug("**************여기2......*"+pMessageKb+"**"+pMessageWhere+"**"+pMessageWhereSub);
		UsersDTO  usersDto = null;
		ArrayList userList	=	new ArrayList();
		if(pMessageKb.equals("S")){
			String[] pCHK_SPLIT	=	{"","",""};
			for(int i=0;i<pCHK.length;i++){
			    if(pCHK[i].indexOf("^") != -1){
			        pCHK_SPLIT = StringUtil.split(pCHK[i], "^");
		            usersDto = new UsersDTO();
		            usersDto.setUserId(StringUtil.nvl(pCHK_SPLIT[0]));
		            usersDto.setUserName(StringUtil.nvl(pCHK_SPLIT[1]));
		            usersDto.setEmail(StringUtil.nvl(pCHK_SPLIT[2]));
		            userList.add(usersDto);
			    }
			}
			 log.debug("**************여기3......*");
		}else{
		    if(pMessageWhere.equals("Lecture")){	// 강의실...
		        CurriMemDTO curriMemDto = usermemdto.curriInfo;
			    String pCurriCode = curriMemDto.curriCode;
				int pCurriYear = Integer.parseInt(curriMemDto.curriYear);
				int pCurriTerm = Integer.parseInt(curriMemDto.curriTerm);

		        if(pMessageWhereSub.equals("Student")){	 // 수강생조회에서 쪽지보낼때..
		            CurriStudentDAO curristudentDao = new CurriStudentDAO();
		            //CurriStudentDAO curristudentDao = new CurriStudentDAO();
		            userList = curristudentDao.getConfirmPassStudentList2(systemCode, pCurriCode, pCurriYear, pCurriTerm,"","",pKb);

		        }else{		// 기타 시험,토론,과제등에서 쪽지 보낼때..

		        }

		    }else{		// 사용자관리...
		        UserAdminDAO useradminDao = new UserAdminDAO();
		        userList = useradminDao.userList(systemCode, pResultCode1, pResultCode2, pResultCode3, "","");
		    }
		}
		 log.debug("**************여기4......*");
		int	retVal	=	0;
		String msg = "";
		String fileLoc = rFileName.equals("") ? "" : FileUtil.UPLOAD_PATH + FilePath + sFileName ;

		log.debug("**************여기5......*"+fileLoc);

    	retVal =  MailUtil.mailSendResult(systemCode, messageDto, userList, fileLoc);
		log.debug("**************여기7......*");
		int	failCnt = userList.size() - retVal;
		if(retVal == userList.size()) {
			msg = "메일을 성공적으로 전송하였습니다.";
		}else{
		    if(retVal == 0) msg = "ERROR!!메일을 보내는데 에러가 발생하였습니다.";
		    else msg = userList.size()+ "명 중" + retVal + "명에게 메일전송을 성공했으며, " + failCnt + "명에게 는 시스템 오류로 메일전송을 실패했습니다.";
		}
		return notifyCloseReload(systemCode, model, msg, LECTURE);

    }

    /*
    public int mailSendResult(String systemCode, MessageDTO messageDto, ArrayList userList, String fileLoc) throws Exception {

        log.debug("***************메일을 보내는 함수내...*");
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "mediopia.co.kr");
        Session ss = Session.getDefaultInstance(props, null);
        String	from	=	"";
        Message msg = new MimeMessage(ss);
        UsersDTO userDto = null;
        CommonDAO commonDao = new CommonDAO();
        from = commonDao.getEmail(systemCode,messageDto.getRegId());
        int	cnt	=	0;
        boolean errorChk =	false;
        log.debug("***************메일을 보내는 함수2...*"+from);
        for(int i=0;i < userList.size();i++){
            errorChk =	false;
            try{
	            userDto = (UsersDTO)userList.get(i);
	            InternetAddress[] address = {new InternetAddress(StringUtil.nvl(userDto.getEmail()), StringUtil.inDataConverter(userDto.getUserName()))};
	            InternetAddress  address2 = new InternetAddress();
	            address2.setAddress(from);
	            address2.setPersonal(StringUtil.inDataConverter(messageDto.getSenderName()));
	            log.debug("보내는 사람이름 : "+ StringUtil.inDataConverter(messageDto.getSenderName()));
	            msg.setFrom(address2);
	            msg.setRecipients(Message.RecipientType.TO, address);
				msg.setSubject(StringUtil.inDataConverter(messageDto.getSubject()));
				msg.setContent(StringUtil.nvl(messageDto.getContents()),"text/html");
				 log.debug("***************메일을 보내는 함수3...*"+userDto.getEmail());
				if(!fileLoc.equals("")){
				    Multipart mt	=	new MimeMultipart();
					MimeBodyPart mbp1	=	new MimeBodyPart();
					MimeBodyPart mbp2	=	new MimeBodyPart();

					FileDataSource  fds = new FileDataSource(fileLoc);
					log.debug("***************메일을 보내는 함수4...*");
					mbp1.setContent(StringUtil.nvl(messageDto.getContents()),"text/html"); // HTML 형식

					mbp2.setDataHandler(new DataHandler(fds));
					mbp2.setFileName(fds.getName());
					mt.addBodyPart(mbp1);
					mt.addBodyPart(mbp2);
					msg.setContent(mt);
				}
				 log.debug("***************메일을 보내는 함수5...*");
				msg.setSentDate(new Date());
				Transport.send(msg);
            }catch(MessagingException e){
                	log.debug(e.getMessage());
                	log.debug("##########메일 보내기 실패#####3"+userDto.getEmail());
                	errorChk =	true;
            }catch(Exception e){
                log.debug(e.getMessage()+"**"+userDto.getEmail());
                errorChk =	true;
            }finally{
                if(!errorChk) cnt++;
            }
        }
        log.debug("***************메일을 보내는 함수6...*"+cnt);
        return cnt;

    }
    */



}

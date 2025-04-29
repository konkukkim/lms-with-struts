/*
 * Created on 2004. 10. 9.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.message.action;

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
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.edutrack.message.dao.MessageDAO;
import com.edutrack.message.dto.MessageDTO;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MessageAction extends StrutsDispatchAction{
	/**
	 * 받은쪽지함 리스트 페이지로 이동을 합니다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward receiveMessagePagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String pSystemCode  = 	UserBroker.getSystemCode(request);
		String pUserId		=	UserBroker.getUserId(request);

		int curPage = 1;
		if(request.getParameter("curPage") != null) {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		}

		MessageDAO messageDAO	=	new MessageDAO();

		new SiteNavigation(MYPAGE).add(request,"쪽지").link(model);
		model.put("receiveMessageList", messageDAO.ReceiveMessagePagingList(curPage, pSystemCode, pUserId));
		return forward(request, model, "/message/receiveMessageList.jsp");
	}

	/**
	 * 보낸쪽지함 리스트 페이지로 이동을 합니다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendMessagePagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String pSystemCode  = 	UserBroker.getSystemCode(request);
		String pUserId		=	UserBroker.getUserId(request);

		int curPage = 1;
		if(request.getParameter("curPage") != null) {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		}

		MessageDAO messageDAO	=	new MessageDAO();

		new SiteNavigation(MYPAGE).add(request,"쪽지").link(model);
		model.put("curPage", Integer.toString(curPage));
		model.put("sendMessageList", messageDAO.SendMessagePagingList(curPage, pSystemCode, pUserId));
		return forward(request, model, "/message/sendMessageList.jsp");
	}

	/**
	 * 쪽지보기 페이지로 이동합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward messageShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		int		pSeqNo		=	Integer.parseInt(request.getParameter("pSEQ_NO"));
		String	pBackPage	=	StringUtil.nvl(request.getParameter("pBackPage"));
		String 	curPage 	= 	StringUtil.nvl(request.getParameter("curPage"));		//페이지번호
		String  pSystemCode = 	UserBroker.getSystemCode(request);
		String  pUserId		=	UserBroker.getUserId(request);

		new SiteNavigation(MYPAGE).add(request,"쪽지").link(model);

		MessageDAO messageDAO	=	new MessageDAO();
		MessageDTO messageDto	= 	new MessageDTO();

		model.put("pBackPage", pBackPage);
		model.put("pSeqNo", Integer.toString(pSeqNo));
		model.put("curPage", curPage);

		String url = "";
		String pId  =   StringUtil.nvl(request.getParameter("pSEND_ID"));

		if(pBackPage.equals("R")) { //받은 쪽지인 경우
			url =  "/message/receiveMessageShow.jsp";
		}else{	//보낸쪽지인 경우
			url =  "/message/sendMessageShow.jsp";
		}

		messageDto = messageDAO.getMessage(pSystemCode, pId, pSeqNo);


		//받은 쪽지를 확인하지 않은경우
		if(messageDto.getReceiveDate().equals("") && pBackPage.equals("R")){
			int retVal = messageDAO.editReceiveDate(pSystemCode, pId, pSeqNo, pUserId );
			if(retVal < 0){
				log.debug("쪽지 확인 에러발생");
			}
		}

		model.put("pSendId",pId);
		model.put("messageShow", messageDto);
		return forward(request, model, url);

	}

	/**
	 * 쪽지쓰기 페이지로 이동을 합니다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward messageWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		//--	일반변수를값을 받아옵니다
		String pSystemCode 		= 	UserBroker.getSystemCode(request);
		String curPage 			= 	StringUtil.nvl(request.getParameter("curPage"));	//페이지번호
		String pUtype			=	StringUtil.nvl(request.getParameter("pUtype"),"A");
		String pRegMode			=	StringUtil.nvl(request.getParameter("pRegMode"));

		MessageDAO messageDAO	=	new MessageDAO();

		//답변 쓰기인경우
		if(pRegMode.equals("R")){
			int	pSeqNo				=	Integer.parseInt(request.getParameter("pSEQ_NO"));
			String  pSendId  	=   StringUtil.nvl(request.getParameter("pSEND_ID"));
			model.put("pSendId", pSendId);
			model.put("messageShow", messageDAO.getMessage(pSystemCode, pSendId, pSeqNo));
		}

		new SiteNavigation(MYPAGE).add(request,"쪽지").link(model);

		model.put("curPage", curPage);
		model.put("pUtype", pUtype);
		model.put("pRegMode", pRegMode);
		return forward(request, model, "/message/messageWrite.jsp");
	}

	/**
	 * 쪽지를 입력 합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward messageRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String pSystemCode  = 	UserBroker.getSystemCode(request);
		UserMemDTO user = UserBroker.getUserInfo(request);
		String pUserId = user.userId;
		String pUserName = user.userName;
		MultipartRequest multipartRequest = null;
		String RegMonth = CommonUtil.getCurrentDate().substring(0,6);
		String FilePath = FileUtil.FILE_DIR+pSystemCode+"/message/"+RegMonth+"/";//-- + 년월

		// 파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request,FilePath,pUserId);

		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();

		String status = uploadEntity.getStatus();

		String pFILE_NEW1_ori = StringUtil.nvl(multipartRequest.getParameter("pFILE_NEW1_ori"));

		//입력폼  값 가져오기
		String pSubject 		= 	StringUtil.nvl(multipartRequest.getParameter("pSubject"));
		String pContents 		= 	StringUtil.nvl(multipartRequest.getParameter("pContents"));
		String pKeyword 		= 	StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));

		String pEditorType		=	StringUtil.nvl(multipartRequest.getParameter("pEditorType"));
		String pUtype			=	StringUtil.nvl(multipartRequest.getParameter("pUtype"));
		String pId = "";
		if(pUtype.equals("A")){
			//개인일경우
			pId			=	StringUtil.nvl(multipartRequest.getParameter("pId"));
		}

		MessageDTO messageDto = new MessageDTO();
		MessageDAO messageDao = new MessageDAO();

		//----------- 파일 처리 시작  ------------------------------------
		String pRFILENAME = "";
		String pSFILENAME = "";
		String pFILEPATH = "";
		String pFILESIZE = "";

		String rFileName = "";
		String sFileName = "";
		long fileSize = 0;
		String filePath = "";

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

		//----------- 파일 처리 끝  ------------------------------------


		//----------- 웹에디터 시작 ------------------------------------
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH+FilePath;
		String WeasFileUrl =  ServiceUrl+"/data/"+FilePath;
		log.debug("WeasFilePath = "+WeasFilePath);
		VBN_files v_objFile = null;

		java.io.File dir = new java.io.File(WeasFilePath);
		if (!dir.exists())	dir.mkdirs();

		v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
		pContents = v_objFile.VBN_uploadMultiFiles(pContents, multipartRequest);
		//----------- 웹에디터 끝 ------------------------------------
		messageDto.setSystemCode(pSystemCode);
		messageDto.setSubject(pSubject);
		messageDto.setContents(pContents);
		messageDto.setRegId(pUserId);
		messageDto.setModId(pUserId);
		messageDto.setKeyword(pKeyword);
		messageDto.setEditorType(pEditorType);
		messageDto.setReceiveType(pUtype);
		messageDto.setReceiveId(pId);
		messageDto.setSenderName(pUserName);

		int		retVal			=	0;

		String returnurl = "";
	    String msg = "";

    	returnurl = "/Message.cmd?cmd=messageWrite";
    	msg = "ERROR!!쪽지를 보내는데 에러가 발생하였습니다.";
		retVal = messageDao.sendMessage(messageDto);
		if(retVal > 0) {
			returnurl = "/Message.cmd?cmd=receiveMessagePagingList";
			msg = "쪽지를  성공적으로 전송하였습니다.";
		}
		return notifyAndExit(pSystemCode, model, msg, returnurl, MYPAGE);
	}

	/**
	 * 받은쪽지를 삭제합니다. (receiver_del_yn = 'Y' 변경)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward receiveMessageDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String[] pChk = request.getParameterValues("pCHK");
		String pSystemCode = UserBroker.getSystemCode(request);

		MessageDAO messageDao = new MessageDAO();

		String returnurl = "/Message.cmd?cmd=receiveMessagePagingList";
		String msg = "ERROR!!쪽지를 삭제하는데 에러가 발생하였습니다.";

		new SiteNavigation(MYPAGE).add(request,"쪽지").link(model);

		boolean retVal =messageDao.delReceiveMessage(pSystemCode,pChk);

		if(retVal){
			msg = "쪽지를 성공적으로 삭제하였습니다.";
		}
		return notifyAndExit(pSystemCode, model, msg, returnurl, MYPAGE);
	}

	/**
	 * 보낸쪽지를 삭제합니다. (sender_del_yn = 'Y' 변경)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendMessageDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String[] pChk = request.getParameterValues("pCHK");
		String pSystemCode = UserBroker.getSystemCode(request);

		MessageDAO messageDao = new MessageDAO();

		String returnurl = "/Message.cmd?cmd=sendMessagePagingList";
		String msg = "ERROR!!쪽지를 삭제하는데 에러가 발생하였습니다.";

		new SiteNavigation(MYPAGE).add(request,"쪽지").link(model);

		boolean retVal =messageDao.delSendMessage(pSystemCode,pChk);

		if(retVal){
			msg = "쪽지를 성공적으로 삭제하였습니다.";
		}
		return notifyAndExit(pSystemCode, model, msg, returnurl, MYPAGE);
	}


}

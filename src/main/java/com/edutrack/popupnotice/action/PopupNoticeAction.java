/*
 * Created on 2004. 10. 4.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.popupnotice.action;

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
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFiles;
import com.edutrack.popupnotice.dao.PopupNoticeDAO;
import com.edutrack.popupnotice.dto.PopupNoticeDTO;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PopupNoticeAction extends StrutsDispatchAction{

	/**
	 * 메인화면에 사용중인 팝업공지를 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward popupNoticeShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		int		pSeqNo		=	Integer.parseInt(request.getParameter("pSEQ_NO"));
		String  pSystemCode = 	UserBroker.getSystemCode(request);

		PopupNoticeDTO popupnoticeDto = new PopupNoticeDTO();
		PopupNoticeDAO popupnoticeDao = new PopupNoticeDAO();

		model.put("popupnoticeShow", popupnoticeDao.getPopupNotice(pSystemCode, pSeqNo));
		return forward(request, model, "/popupnotice/popupNoticeShow.jsp");
	}
	/**
	 * 메인 화면에 사용중인 팝업 공지를 레이어로 띄워 준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward popupLayerShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		int		pSeqNo		=	Integer.parseInt(request.getParameter("pSEQ_NO"));
		String  pSystemCode = 	UserBroker.getSystemCode(request);

		PopupNoticeDTO popupnoticeDto = new PopupNoticeDTO();
		PopupNoticeDAO popupnoticeDao = new PopupNoticeDAO();

		model.put("popupnoticeShow", popupnoticeDao.getPopupNotice(pSystemCode, pSeqNo));
		return forward(request, model, "/popupnotice/popupLayerShow.jsp");
	}
	/**
	 * 팝업공지 리스트 페이지로 이동을 합니다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward popupNoticePagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String pSystemCode  = 	UserBroker.getSystemCode(request);
		String pOp 			= 	StringUtil.nvl(request.getParameter("pOP"));
		String pSearch 		= 	StringUtil.nvl(request.getParameter("pSEARCH"));

		int curPage = 1;
		if(StringUtil.nvl(request.getParameter("curPage")) != "") {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		}

		PopupNoticeDAO popupNoticeDAO	=	new PopupNoticeDAO();

		new SiteNavigation(MYPAGE).add(request,"팝업공지관리").link(model);

		model.put("pOp", pOp);
		model.put("pSearch", pSearch);
		model.put("curPage", Integer.toString(curPage));
		model.put("PopupNoticePagingList", popupNoticeDAO.PopupNoticePagingList(curPage, pSystemCode, pOp, pSearch));
		return forward(request, model, "/popupnotice/popupNoticeList.jsp");
	}

	/**
	 * 팝업공지를 추가하는 페이지로 이동을 합니다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward popupNoticeWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		//--	일반변수를값을 받아옵니다
		String pRegMode			=	StringUtil.nvl(request.getParameter("pRegMode"));
		String pSystemCode 		= 	UserBroker.getSystemCode(request);
		String curPage 		= 	StringUtil.nvl(request.getParameter("curPage"));	//페이지번호

		PopupNoticeDAO popupNoticeDAO	=	new PopupNoticeDAO();
		PopupNoticeDTO popupNoticeDTO	=   new PopupNoticeDTO();

		new SiteNavigation(MYPAGE).add(request,"팝업공지관리").link(model);

		//수정모드일 경우
		if(pRegMode.equals("E")){
			int pSeqNo		=	Integer.parseInt(request.getParameter("pSeqNo"));

			model.put("popupnoticeShow", popupNoticeDAO.getPopupNotice(pSystemCode, pSeqNo));
			model.put("pSeqNo",Integer.toString(pSeqNo));
		}
		model.put("pRegMode",pRegMode);
		model.put("curPage", curPage);
		return forward(request, model, "/popupnotice/popupNoticeWrite.jsp?pMode="+MYPAGE);
	}

	/**
	 * 팝업공지를 추가/수정 합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward popupNoticeRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String pSystemCode  = 	UserBroker.getSystemCode(request);
		String pUserId		=	UserBroker.getUserId(request);

		MultipartRequest multipartRequest = null;

		String FilePath = FileUtil.FILE_DIR+pSystemCode+"/popupnotice/";

		// 파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request,FilePath,pUserId);

		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();

		//입력폼  값 가져오기
		String pRegMode			= 	StringUtil.nvl(multipartRequest.getParameter("pRegMode"));
		String pSubject 		= 	StringUtil.nvl(multipartRequest.getParameter("pSubject"));
		String pContents 		= 	StringUtil.nvl(multipartRequest.getParameter("pContents"));
		String pUseYn	 		= 	StringUtil.nvl(multipartRequest.getParameter("pUseYn"));
		String pTarget	 		= 	StringUtil.nvl(multipartRequest.getParameter("pTarget"));
		int	pWidth	 			= 	Integer.parseInt(multipartRequest.getParameter("pWidth"));
		int pHeight	 			= 	Integer.parseInt(multipartRequest.getParameter("pHeight"));
		int pXposition 			= 	Integer.parseInt(multipartRequest.getParameter("pXposition"));
		int pYposition 			= 	Integer.parseInt(multipartRequest.getParameter("pYposition"));
		String pKeyword 		= 	StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));

		log.debug("pRegMode = "+pRegMode);

		PopupNoticeDTO popupnoticeDto = new PopupNoticeDTO();
		PopupNoticeDAO popupnoticeDao = new PopupNoticeDAO();

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
		//-- 컨텐츠 읽기 폭 조절
		pContents = StringUtil.ReplaceAll(pContents,"WIDTH: 550px;","WIDTH: "+(pWidth-50)+"px;");

		popupnoticeDto.setSystemCode(pSystemCode);
		popupnoticeDto.setSubject(pSubject);
		popupnoticeDto.setContents(pContents);
		popupnoticeDto.setWinWidth(pWidth);
		popupnoticeDto.setWinHeight(pHeight);
		popupnoticeDto.setWinXposition(pXposition);
		popupnoticeDto.setWinYposition(pYposition);
		popupnoticeDto.setUseYn(pUseYn);
		popupnoticeDto.setRegId(pUserId);
		popupnoticeDto.setModId(pUserId);
		popupnoticeDto.setKeyword(pKeyword);
		popupnoticeDto.setPopupTarget(pTarget);

		log.debug("pUseYn:"+pUseYn);
		log.debug("pRegMode:"+pRegMode);

		int		retVal			=	0;

		String returnurl = "";
	    String msg = "";

    	returnurl = "/PopupNotice.cmd?cmd=popupNoticeWrite&pMode="+MYPAGE+"&pRegMode=W";
    	msg = "ERROR!!팝업공지를 등록하는데 에러가 발생하였습니다.";

 		//입력일 경우
		if(pRegMode.equals("W")) {
			retVal = popupnoticeDao.addPopupNotice(popupnoticeDto);
			if(retVal > 0) {
				returnurl = "/PopupNotice.cmd?cmd=popupNoticePagingList&pMode="+MYPAGE;
				msg = "팝업공지를 등록하였습니다.";
			}
		}else{
			String pSeqNo 	= StringUtil.nvl(multipartRequest.getParameter("pSeqNo"));
			popupnoticeDto.setSeqNo(Integer.parseInt(pSeqNo));

			//수정일 경우
			if(pRegMode.equals("E")){
				retVal = popupnoticeDao.editPopupNotice(popupnoticeDto);
				if(retVal > 0){
					returnurl = "/PopupNotice.cmd?cmd=popupNoticePagingList&pMode="+MYPAGE ;
					msg = "팝업공지를 수정하였습니다.";
				}else{
					returnurl = "/PopupNotice.cmd?cmd=popupNoticeWrite&pRegMode=E&pSEQ_NO="+pSeqNo;
					msg = "ERROR!!팝업공지를 수정하는데 에러가 발생하였습니다.";
				}
				return notifyAndExit(pSystemCode, model, msg, returnurl, MYPAGE);

			}
		}
		return notifyAndExit(pSystemCode, model, msg, returnurl, MYPAGE);
	}

	/**
	 * 팝업공지를 삭제합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward popupNoticeDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String  pSystemCode		=	UserBroker.getSystemCode(request);
		String  pSeqNo 			=	StringUtil.nvl(request.getParameter("pSeqNo"));
		String  pCONTENTS 		= 	StringUtil.nvl(request.getParameter("pCONTENTS"));

		//----------- 웹에디터 시작 ----------------------------------
		String FilePath = FileUtil.FILE_DIR+pSystemCode+"/popupnotice/";
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH+FilePath;
		String WeasFileUrl =  ServiceUrl+"/data/"+FilePath;
		//----------- 웹에디터 끝 ------------------------------------

		PopupNoticeDAO popupnoticeDao = new PopupNoticeDAO();
		PopupNoticeDTO popupnoticeDto = new PopupNoticeDTO();

		int		retVal			=	0;
		String returnurl = "";
	    String msg = "";

		returnurl = "/PopupNotice.cmd?cmd=popupNoticeWrite&pRegMode=E&pSeqNo="+pSeqNo;
		msg = "Error!!팝업공지를 삭제하는데 에러가 발생하였습니다.";

		popupnoticeDto.setSystemCode(pSystemCode);
		popupnoticeDto.setSeqNo(Integer.parseInt(pSeqNo));

		retVal = popupnoticeDao.delPopupNotice(popupnoticeDto);
		if(retVal > 0 ) {
			//웹에디터 관련 파일 삭제
			VBN_files v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
			v_objFile.VBN_deleteFiles(pCONTENTS);

			returnurl = "/PopupNotice.cmd?cmd=popupNoticePagingList&pSeqNo="+pSeqNo;
			msg = "팝업공지를 삭제하였습니다.";
		}
		return notifyAndExit(pSystemCode, model, msg, returnurl, MYPAGE);
	}
}

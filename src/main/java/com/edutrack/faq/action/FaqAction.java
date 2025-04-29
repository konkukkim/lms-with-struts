package com.edutrack.faq.action;


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
import com.edutrack.faq.dao.FaqDAO;
import com.edutrack.faq.dto.FaqCategoryDTO;
import com.edutrack.faq.dto.FaqContentsDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;


public class FaqAction extends StrutsDispatchAction{

	/**
	 * FAQ정보 리스트 페이지로 이동을 합니다 (HOME)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward homeFaqList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String pSystemCode  = UserBroker.getSystemCode(request);
		String pMode        = StringUtil.nvl(request.getParameter("pMode"),HOME);
		String pSearch      = StringUtil.nvl(request.getParameter("pSearch"));
		String cateid		= "";
		cateid	= StringUtil.nvl(request.getParameter("pCateId"));

		FaqDAO faqDAO	=	new FaqDAO();

		if(cateid.equals("")){
			cateid	= faqDAO.getMinCateId(pSystemCode);
		}

		

		model.put("pCateId", cateid);
		model.put("faqcategoryList", faqDAO.getFaqCategoryList(pSystemCode));

		if(!cateid.equals("")){
			model.put("faqcategoryView", faqDAO.getFaqCategory(pSystemCode, cateid));
			model.put("faqcontentsList", faqDAO.getFaqContentsList(pSystemCode, cateid, pSearch));
		}

		new SiteNavigation(pMode).add(request,"FAQ").link(model);
		return forward(request, model, "/faq/homeFaqList.jsp");
	}

	/**
	 * FAQ정보 리스트 페이지로 이동을 합니다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward faqCategoryList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String pSystemCode  = UserBroker.getSystemCode(request);
		String pMode		= StringUtil.nvl(request.getParameter("pMode"), MYPAGE);

		FaqDAO faqDAO	=	new FaqDAO();

		new SiteNavigation(MYPAGE).add(request,"FAQ").link(model);

		model.put("faqcategoryList", faqDAO.getFaqCategoryList(pSystemCode));
		model.put("pMode", pMode);

		return forward(request, model, "/faq/faqCategoryList.jsp");
	}


	/**
	 * FAQ정보를 추가하는 페이지로 이동을 합니다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward faqCategoryWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		//--	일반변수를값을 받아옵니다
		String pRegMode		= StringUtil.nvl(request.getParameter("pRegMode"));
		String pSystemCode	= UserBroker.getSystemCode(request);
		String pMode		= StringUtil.nvl(request.getParameter("pMode"));

		FaqDAO faqDAO						=	new FaqDAO();
		FaqCategoryDTO faqcategoryDto		=   new FaqCategoryDTO();

		new SiteNavigation(MYPAGE).add(request,"FAQ").link(model);

		//수정모드일 경우
		if(pRegMode.equals("E")){
			String cateid		=	StringUtil.nvl(request.getParameter("pCateId"));
			model.put("faqcategoryView", faqDAO.getFaqCategory(pSystemCode, cateid));
			model.put("pCateId",cateid);
		}

		model.put("pRegMode",pRegMode);
		model.put("pMode", pMode);

		return forward(request, model, "/faq/faqCategoryWrite.jsp");
	}

	/**
	 * FAQ정보를 추가/수정 합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward faqCategoryRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String	pRegMode		=	StringUtil.nvl(request.getParameter("pRegMode"));
		String  pCateName 	 	=   StringUtil.nvl(request.getParameter("pCateName"));
		String  pComment 		=   StringUtil.nvl(request.getParameter("pComment"));
		String  pSystemCode  	=   UserBroker.getSystemCode(request);
		String  pUserId			= 	UserBroker.getUserId(request);
		String pMode			= 	StringUtil.nvl(request.getParameter("pMode"));

		FaqDAO faqDAO						=	new FaqDAO();
		FaqCategoryDTO faqcategoryDto		=	new FaqCategoryDTO();

		int		retVal			=	0;

		new SiteNavigation(MYPAGE).add(request,"FAQ").link(model);

		String returnurl = "";
	    String msg = "";

		returnurl = "/Faq.cmd?cmd=faqCategoryWrite&pRegMode="+pRegMode+"&pMode="+pMode;
		msg = "Error!!카테고리를 등록 하는데 에러가 발생하였습니다.";

		faqcategoryDto.setSystemCode(pSystemCode);
		faqcategoryDto.setRegId(pUserId);
		faqcategoryDto.setCateName(pCateName);
		faqcategoryDto.setXcomment(pComment);
		faqcategoryDto.setModId(pUserId);

		if(pRegMode.equals("W")){
			retVal = faqDAO.addFaqCategory(faqcategoryDto);
			if (retVal > 0) {
				returnurl = "/Faq.cmd?cmd=faqCategoryList&pMode="+pMode;
				msg = "카테고리를 등록하였습니다.";
			}
			return notifyAndExit(pSystemCode, model, msg, returnurl, pMode);
		}else if(pRegMode.equals("E")){
			String cateid		=	StringUtil.nvl(request.getParameter("pCateId"));
			faqcategoryDto.setCateId(Integer.parseInt(cateid));

			retVal = faqDAO.editFaqCategory(faqcategoryDto);
			if (retVal > 0) {
				returnurl = "/Faq.cmd?cmd=faqCategoryList&pMode="+pMode;
				msg = "카테고리를 수정하였습니다.";
			}else {
				returnurl = "/Faq.cmd?cmd=faqCategoryWrite&pRegMode="+pRegMode+"&pCateId="+cateid+"&pMode="+pMode;
				msg = "Error!!카테고리를 수정 하는데 에러가 발생하였습니다.";
			}
			return notifyAndExit(pSystemCode, model, msg, returnurl, pMode);

		}else{
			return notifyAndExit(pSystemCode, model, msg, returnurl, pMode);
		}
	}

	/**
	 * FAQ정보를 삭제합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward faqCategoryDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String pSystemCode	= UserBroker.getSystemCode(request);
		int	cateid			= Integer.parseInt(StringUtil.nvl(request.getParameter("pCateId")));
		String pMode		= StringUtil.nvl(request.getParameter("pMode"));

		FaqDAO faqDAO		=	new FaqDAO();
		FaqCategoryDTO faqcategoryDto		=	new FaqCategoryDTO();

		new SiteNavigation(MYPAGE).add(request,"FAQ").link(model);

		int		retVal			=	0;

		String returnurl = "";
	    String msg = "";

		returnurl = "/Faq.cmd?cmd=faqCategoryList&pMode="+pMode;
		msg = "Error!!카테고리를 삭제 하는데 에러가 발생하였습니다.";

		faqcategoryDto.setSystemCode(pSystemCode);
		faqcategoryDto.setCateId(cateid);

		retVal = faqDAO.delFaqCategory(faqcategoryDto);

		if(retVal > 0){
        	returnurl = "/Faq.cmd?cmd=faqCategoryList&pMode="+pMode;
        	msg = "카테고리를 삭제하였습니다.";
			return notifyAndExit(pSystemCode, model, msg, returnurl, pMode);
        }
		return notifyAndExit(pSystemCode, model, msg, returnurl, pMode);
	}


	/**
	 * FAQ 게시물 리스트 페이지로 이동을 합니다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward faqContentsPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String cateid		=	StringUtil.nvl(request.getParameter("pCateId"));
		String pSystemCode  = 	UserBroker.getSystemCode(request);
		String pOp 			= 	StringUtil.nvl(request.getParameter("pOP"));
		String pSearch 		= 	StringUtil.nvl(request.getParameter("pSEARCH"));
		String pMode		= 	StringUtil.nvl(request.getParameter("pMode"));

		int curPage = 1;
		if(StringUtil.nvl(request.getParameter("curPage")) != "") {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		}

		FaqDAO faqDAO	=	new FaqDAO();

		new SiteNavigation(MYPAGE).add(request,"FAQ").link(model);

		model.put("pOp", pOp);
		model.put("pSearch", pSearch);
		model.put("curPage", Integer.toString(curPage));
		model.put("pCateId", cateid);
		model.put("faqcontentsList", faqDAO.FaqContentsPagingList(curPage, pSystemCode, cateid, pOp, pSearch));
		model.put("pMode", pMode);

		return forward(request, model, "/faq/faqContentsList.jsp");
	}


	/**
	 * FAQ 게시물을 추가하는 페이지로 이동을 합니다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward faqContentsWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		//--	일반변수를값을 받아옵니다
		String pRegMode			=	StringUtil.nvl(request.getParameter("pRegMode"));
		String pSystemCode 		= 	UserBroker.getSystemCode(request);
		int cateid				=	Integer.parseInt(request.getParameter("pCateId"));
		String 	curPage 		= 	StringUtil.nvl(request.getParameter("curPage"));	//페이지번호
		String pMode			= 	StringUtil.nvl(request.getParameter("pMode"));

		FaqDAO faqDAO						=	new FaqDAO();
		FaqContentsDTO faqcontentsDTO		=   new FaqContentsDTO();

		new SiteNavigation(MYPAGE).add(request,"FAQ").link(model);

		//수정모드일 경우
		if(pRegMode.equals("E")){
			int pSeqNo		=	Integer.parseInt(request.getParameter("pSeqNo"));

			model.put("faqcontentsShow", faqDAO.getFaqContents(pSystemCode, cateid, pSeqNo));
			model.put("pSeqNo",Integer.toString(pSeqNo));
		}

		model.put("pRegMode",pRegMode);
		model.put("pCateId",Integer.toString(cateid));
		model.put("curPage", curPage);
		model.put("pMode", pMode);

		return forward(request, model, "/faq/faqContentsWrite.jsp");
	}

	/**
	 * FAQ 게시물 정보를 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward faqContentsShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		int		cateid		=	Integer.parseInt(request.getParameter("pCateId"));
		int		pSeqNo		=	Integer.parseInt(request.getParameter("pSEQ_NO"));
		String  pSystemCode = 	UserBroker.getSystemCode(request);
		String 	curPage 	= 	StringUtil.nvl(request.getParameter("curPage"));		//페이지번호

		model.put("pCateId", Integer.toString(cateid));
		model.put("pSeqNo", Integer.toString(pSeqNo));
		model.put("curPage", curPage);

		new SiteNavigation(MYPAGE).add(request,"FAQ").link(model);

		FaqContentsDTO faqcontentsDto = new FaqContentsDTO();
		FaqDAO faqDao = new FaqDAO();

		faqcontentsDto.setSystemCode(pSystemCode);
		faqcontentsDto.setCateId(cateid);
		faqcontentsDto.setSeqNo(pSeqNo);

		//게시물 정보
		model.put("faqcontentsShow", faqDao.getFaqContents(pSystemCode, cateid, pSeqNo));

		return forward(request, model, "/faq/faqContentsShow.jsp");
	}
	/**
	 * FAQ 게시물을 추가/수정 합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward faqContentsRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String pSystemCode  = 	UserBroker.getSystemCode(request);
		String pUserId		=	UserBroker.getUserId(request);

		MultipartRequest multipartRequest = null;

		String FilePath = FileUtil.FILE_DIR+pSystemCode+"/faq/";

		// 파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request,FilePath,pUserId);

		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();

		String status = uploadEntity.getStatus();

		String pFILE_NEW1_ori = StringUtil.nvl(multipartRequest.getParameter("pFILE_NEW1_ori"));

		String pRegMode	= StringUtil.nvl(multipartRequest.getParameter("pRegMode"));
		int	cateid		= Integer.parseInt(multipartRequest.getParameter("pCateId"));
		String pMode	= StringUtil.nvl(multipartRequest.getParameter("pMode"));

		//입력폼  값 가져오기
		String pSubject 		= 	StringUtil.nvl(multipartRequest.getParameter("pSubject"));
		String pContents 		= 	StringUtil.nvl(multipartRequest.getParameter("pContents"));
		String pKeyword 		= 	StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));

		FaqContentsDTO faqcontentsDto = new FaqContentsDTO();
		FaqDAO faqDao = new FaqDAO();

		//----------- 파일 처리 시작  ------------------------------------
		String pRFILENAME = "";
		String pSFILENAME = "";
		String pFILEPATH = "";
		String pFILESIZE = "";
		String pFILEDEL = "";

		//수정일 경우 파일정보 가져오기
		if(pRegMode.equals("E")) {
			pRFILENAME = StringUtil.nvl(multipartRequest.getParameter("pRFILENAME"));
			pSFILENAME = StringUtil.nvl(multipartRequest.getParameter("pSFILENAME"));
			pFILEPATH  = StringUtil.nvl(multipartRequest.getParameter("pFILEPATH"));
			pFILESIZE  = StringUtil.nvl(multipartRequest.getParameter("pFILESIZE"));
			pFILEDEL = StringUtil.nvl(multipartRequest.getParameter("pFile_DEL"));

			log.debug("pFILEDEL:"+pFILEDEL);

		}

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
				faqcontentsDto.setRfileName(rFileName);
				faqcontentsDto.setSfileName(sFileName);
				faqcontentsDto.setFilePath(filePath);
				faqcontentsDto.setFileSize(Double.toString(fileSize));

				if(!pFILEPATH.equals("") && !pSFILENAME.equals("")) {	//이전 첨부파일을 삭제할 경우
					FileUtil.delFile(pFILEPATH, pSFILENAME);
					log.debug("이전 첨부파일을 삭제하였습니다.");
				}
			//파일첨부를 하지 않았을 경우
			} else {

				//기존첨부파일 삭제 체크시
				if(pFILEDEL.equals("on")){
					FileUtil.delFile(pFILEPATH, pSFILENAME);
					log.debug("기존 첨부파일을 삭제하였습니다.");
				}else{
					faqcontentsDto.setRfileName(pRFILENAME);
					faqcontentsDto.setSfileName(pSFILENAME);
					faqcontentsDto.setFilePath(pFILEPATH);
					faqcontentsDto.setFileSize(pFILESIZE);
				}
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

		faqcontentsDto.setSystemCode(pSystemCode);
		faqcontentsDto.setCateId(cateid);
		faqcontentsDto.setSubject(pSubject);
		faqcontentsDto.setContents(pContents);
		faqcontentsDto.setRegId(pUserId);
		faqcontentsDto.setModId(pUserId);
		faqcontentsDto.setKeyword(pKeyword);

		int		retVal			=	0;

		String returnurl = "";
	    String msg = "";

    	returnurl = "/Faq.cmd?cmd=faqContentsWrite&pMode="+pMode+"&pRegMode="+pRegMode+"&pCateId="+cateid;
    	msg = "ERROR!!게시물을 등록하는데 에러가 발생하였습니다.";

		//입력일 경우
		if(pRegMode.equals("W")) {
			retVal = faqDao.addFaqContents(faqcontentsDto);
			if(retVal > 0) {
				returnurl = "/Faq.cmd?cmd=faqContentsPagingList&pMode="+pMode+"&pCateId="+cateid;
				msg = "게시물을 등록하였습니다.";
			}
		}else{
			String pSeqNo 	= StringUtil.nvl(multipartRequest.getParameter("pSeqNo"));

			faqcontentsDto.setSeqNo(Integer.parseInt(pSeqNo));

			
			//수정일 경우
			if(pRegMode.equals("E")){
				retVal = faqDao.editFaqContents(faqcontentsDto);
				if(retVal > 0){
					returnurl = "/Faq.cmd?cmd=faqContentsPagingList&pMode="+pMode+"&pCateId="+cateid;
					msg = "게시물을 수정하였습니다.";
				}else{
					returnurl = "/Faq.cmd?cmd=faqContentsWrite&pMode="+pMode+"&pRegMode="+pRegMode+"&pCateId="+cateid+"&pSEQ_NO="+pSeqNo;
					msg = "ERROR!!게시물을 수정하는데 에러가 발생하였습니다.";
				}
				return notifyAndExit(pSystemCode, model, msg, returnurl, pMode);

			}
		}
		return notifyAndExit(pSystemCode, model, msg, returnurl, pMode);
	}

	/**
	 * FAQ정보를 삭제합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward faqContentsDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String pSystemCode	= UserBroker.getSystemCode(request);
		int cateid			= Integer.parseInt(request.getParameter("pCateId"));
		String pSeqNo 		= StringUtil.nvl(request.getParameter("pSeqNo"));
		String pRfileName	= StringUtil.nvl(request.getParameter("pRFILENAME"));
		String pSfileName	= StringUtil.nvl(request.getParameter("pSFILENAME"));
		String pFilePath	= StringUtil.nvl(request.getParameter("pFILEPATH"));
		String pCONTENTS 	= StringUtil.nvl(request.getParameter("pCONTENTS"));
		String pMode		= StringUtil.nvl(request.getParameter("pMode"));

		//----------- 웹에디터 시작 ----------------------------------
		String FilePath = FileUtil.FILE_DIR+pSystemCode+"/faq/";
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH+FilePath;
		String WeasFileUrl =  ServiceUrl+"/data/"+FilePath;
		//----------- 웹에디터 끝 ------------------------------------
		FaqDAO faqDao = new FaqDAO();
		FaqContentsDTO faqcontentsDto = new FaqContentsDTO();

		int retVal			= 0;
		String returnurl	= "";
	    String msg 			= "";

		returnurl = "/Faq.cmd?cmd=faqContentsWrite&pRegMode=E&pCateId="+cateid+"&pSeqNo="+pSeqNo+"&pMode="+pMode;
		msg = "Error!!게시물을 삭제하는데 에러가 발생하였습니다.";

		faqcontentsDto.setSystemCode(pSystemCode);
		faqcontentsDto.setCateId(cateid);
		faqcontentsDto.setSeqNo(Integer.parseInt(pSeqNo));
		retVal = faqDao.delFaqContents(faqcontentsDto);

		if(retVal > 0 ) {
			//웹에디터 관련 파일 삭제
			VBN_files v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
			v_objFile.VBN_deleteFiles(pCONTENTS);

			if(!pFilePath.equals("") && !pSfileName.equals("")){
				//파일삭제
				FileUtil.delFile(pFilePath, pSfileName);
				log.debug("파일이 정상적으로 삭제되었습니다.");
			}
			returnurl = "/Faq.cmd?cmd=faqContentsPagingList&pCateId="+cateid+"&pSeqNo="+pSeqNo+"&pMode="+pMode;
			msg = "게시물을 삭제하였습니다.";
		}
		return notifyAndExit(pSystemCode, model, msg, returnurl, pMode);
	}
}

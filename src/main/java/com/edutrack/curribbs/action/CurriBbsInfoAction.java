/*
 * Created on 2004. 10. 12.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.edutrack.curribbs.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.curribbs.dao.CurriBbsInfoDAO;
import com.edutrack.curribbs.dao.CurriBbsContentsDAO;
import com.edutrack.curribbs.dto.CurriBbsInfoDTO;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author suna
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CurriBbsInfoAction  extends StrutsDispatchAction{

	   // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();


	/**
	 * 게시판 생성 폼을 띄워준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriBbsInfoWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		//이미 등록되어 있는 bbs_type을 가져와서 중복을 피하게 막자.
		 CurriBbsInfoDAO curriBbsInfoDao = new CurriBbsInfoDAO();
		 RowSet InfoRs = curriBbsInfoDao.getCurriBbsInfoList(systemCode);
		 model.put("InfoRs", InfoRs);

		new SiteNavigation(MYPAGE).add(request,"과정게시판관리").link(model);
		return forward(request, model, "/curri_bbs/curriBbsInfoWrite.jsp");
	}

	/**
	 * 게시판 정보 수정 폼을 띄운다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriBbsInfoEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pBbsType = request.getParameter("pBbsType");
		String FilePath = FileUtil.IMG_DIR+systemCode+"/curri_bbs/";
		model.put("FilePath",FilePath);
		CurriBbsInfoDAO curriBbsInfoDao = new CurriBbsInfoDAO();
		RowSet rs = curriBbsInfoDao.getCurriBbsInfo(systemCode, pBbsType);
		model.put("rs", rs);
		model.put("pBbsType", pBbsType);
		//이미 등록되어 있는 bbs_type을 가져와서 중복을 피하게 막자.
		 RowSet InfoRs = curriBbsInfoDao.getCurriBbsInfoList(systemCode);
		 model.put("InfoRs", InfoRs);
		new SiteNavigation(MYPAGE).add(request,"과정게시판관리").link(model);
        return forward(request, model, "/curri_bbs/curriBbsInfoEdit.jsp");
	}
	/**
	 * 게시판 정보를 삭제한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriBbsInfoDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pBbsType = request.getParameter("pBbsType");

		CurriBbsInfoDAO curriBbsInfoDao = new CurriBbsInfoDAO();

		RowSet bi = curriBbsInfoDao.getCurriBbsInfo(systemCode,pBbsType);//-- 첨부파일 삭제를 위해
		bi.next();
		String FilePath = FileUtil.IMG_DIR+systemCode+"/curri_bbs/";
		String titleImg = StringUtil.nvl(bi.getString("title_img"));

		int retVal = 0;
		retVal = curriBbsInfoDao.delCurriBbsInfo(systemCode, pBbsType);
		CurriBbsContentsDAO curriBbsContentsDao = new CurriBbsContentsDAO();
		if(!titleImg.equals("") && retVal > 0){ //-- 게시판정보 삭제 성공하고 첨부 이미지 있는경우
			FileUtil.delFile(FilePath, titleImg);
			log.debug("정보 첨부파일을 삭제하였습니다."+FilePath+titleImg);
		}

		retVal = curriBbsContentsDao.delCurriBbsContents(systemCode, pBbsType , 0);

		String msg = "삭제하였습니다.";
		String returnUrl = "/CurriBbsInfo.cmd?cmd=curriBbsInfoPagingList";

		 new SiteNavigation(MYPAGE).add(request,"과정게시판관리").link(model);
		 return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}
	/**
	 * 게시판 정보 첨부파일(타이틀 이미지) 삭제
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriBbsInfoFileDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=====================bbsInfoFileDelete start");
		String systemCode = UserBroker.getSystemCode(request);
		String pBbsType = request.getParameter("pBbsType");
		CurriBbsInfoDAO curriBbsInfoDao = new CurriBbsInfoDAO();

		RowSet bi = curriBbsInfoDao.getCurriBbsInfo(systemCode,pBbsType);//-- 첨부파일 삭제를 위해
		bi.next();
		String FilePath = FileUtil.IMG_DIR+systemCode+"/curri_bbs/";
		String titleImg = StringUtil.nvl(bi.getString("title_img"));

		int retVal = 0;
		retVal = curriBbsInfoDao.delCurriBbsInfoFile(systemCode,pBbsType);

		if(!titleImg.equals("") && retVal > 0){ //-- 게시판정보 수정 성공하고 첨부 이미지 있는경우
			FileUtil.delFile(FilePath, titleImg);
			log.debug("정보 첨부파일을 삭제하였습니다."+FilePath+titleImg);
		}

		String msg = "삭제하였습니다";
		String returnUrl = "/CurriBbsInfo.cmd?cmd=curriBbsInfoEdit&pBbsType="+pBbsType;
		new SiteNavigation(MYPAGE).add(request,"과정게시판관리").link(model);
		log.debug("=====================bbsInfoFileDelete End");
        return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}

	/**
	 * 게시판 정보를 등록/수정 한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriBbsInfoRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=====================curribbsInfoRegist start");
		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);
		String pBbsType = StringUtil.nvl(request.getParameter("pBbsType"), "");

		CurriBbsInfoDAO curriBbsInfoDao = new CurriBbsInfoDAO();
		CurriBbsInfoDTO curriBbsInfoDto = new CurriBbsInfoDTO();

		MultipartRequest multipartRequest = null;
		int retVal = 0;
		String FilePath = FileUtil.IMG_DIR+systemCode+"/curri_bbs/";
		
		String pOldFileName = "";
		String originName = "";
		String renFileName = "";

		String regMode = StringUtil.nvl(request.getParameter("pRegMode"));
		
		//		  파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath,"bbsTitle_"+String.valueOf(pBbsType),"M");
		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();
		String status = uploadEntity.getStatus();


		if(regMode.equals("Edit"))// 수정 모드 접근시		파일 삭제 정보 가져오기
		{
			pOldFileName = StringUtil.nvl(multipartRequest.getParameter("pOldFileName"));
		}

		if (status.equals("E")){
			log.debug("첨부 파일 올리려다 실패하였습니다.");
		}else if (status.equals("O")){
			log.debug("첨부하신 파일이 용량을 초과했습니다.");
		}else if (status.equals("I")){
			log.debug("첨부 파일의 정보가 잘못되었습니다.");
		}else if(status.equals("S")){
			// 업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			log.debug("첨부 파일을 첨부하는데 성공하였습니다.");
			ArrayList files = uploadEntity.getFiles();
			UploadFile file = null;
			for(int i = 0 ; i < files.size(); i++){
				file = (UploadFile)files.get(i);
				originName = StringUtil.nvl(file.getRootName());
				if(!originName.equals("")) {
					renFileName = file.getUploadName();
				}
			}

			if(!originName.equals("")) {			//파일첨부를 했을경우
				curriBbsInfoDto.setTitleImg(renFileName);
				if(!pOldFileName.equals("")) {		//이전 첨부파일을 삭제할 경우
					FileUtil.delFile(FilePath, pOldFileName);
				}
			}else{									// 첨부 안한 경우
				curriBbsInfoDto.setTitleImg(pOldFileName);
			}
		}

		curriBbsInfoDto.setSystemCode(systemCode);
		curriBbsInfoDto.setBbsName(StringUtil.nvl(multipartRequest.getParameter("pBbsName")));
		curriBbsInfoDto.setBbsType(StringUtil.nvl(multipartRequest.getParameter("pBbsType")));
		curriBbsInfoDto.setDispLine(StringUtil.nvl(multipartRequest.getParameter("pDispLine"),10));
		curriBbsInfoDto.setDispPage(StringUtil.nvl(multipartRequest.getParameter("pDispPage"),10));
		curriBbsInfoDto.setFileUseYn(StringUtil.nvl(multipartRequest.getParameter("pFileUseYn")));
		curriBbsInfoDto.setFileCount(StringUtil.nvl(multipartRequest.getParameter("pFileCount"),1));
		curriBbsInfoDto.setFileLimit(StringUtil.nvl(multipartRequest.getParameter("pFileLimit")));
		curriBbsInfoDto.setUseYn(StringUtil.nvl(multipartRequest.getParameter("pUseYn")));
		curriBbsInfoDto.setEditorYn(StringUtil.nvl(multipartRequest.getParameter("pEditorYn")));
		curriBbsInfoDto.setVoiceYn(StringUtil.nvl(multipartRequest.getParameter("pVoiceYn")));
		curriBbsInfoDto.setBbsComment(StringUtil.nvl(multipartRequest.getParameter("pBbsComment")));
		curriBbsInfoDto.setNewTime(StringUtil.nvl(multipartRequest.getParameter("pNewTime"),24));
		curriBbsInfoDto.setHotChk(StringUtil.nvl(multipartRequest.getParameter("pHotChk"),100));
		curriBbsInfoDto.setBadWordUseYn(StringUtil.nvl(multipartRequest.getParameter("pBadWordUseYn")));
		curriBbsInfoDto.setBadWord(StringUtil.nvl(multipartRequest.getParameter("pBadWord")));
		curriBbsInfoDto.setCommentUseYn(StringUtil.nvl(multipartRequest.getParameter("pCommentUseYn")));
		curriBbsInfoDto.setEmoticonUseYn(StringUtil.nvl(multipartRequest.getParameter("pEmoticonUseYn")));
		curriBbsInfoDto.setViewThreadYn(StringUtil.nvl(multipartRequest.getParameter("pViewThreadYn")));
		curriBbsInfoDto.setViewPrevNextYn(StringUtil.nvl(multipartRequest.getParameter("pViewPrevNextYn")));
		curriBbsInfoDto.setRegId(regId);
	
		String msg = "";
		String returnUrl = "/CurriBbsInfo.cmd?cmd=curriBbsInfoPagingList";
		if(regMode.equals("Add"))// 입력모드
		{
			retVal = curriBbsInfoDao.addCurriBbsInfo(curriBbsInfoDto);
			msg = "등록완료";
		}else{
			retVal = curriBbsInfoDao.editCurriBbsInfo(curriBbsInfoDto);
			msg = "수정완료";
			if(retVal <= 0){
				returnUrl = "/CurriBbsInfo.cmd?cmd=curriBbsInfoEdit&pBbsType="+pBbsType;
				msg = "수정오류 다시 진행해 주세요";
			}
		}
		new SiteNavigation(MYPAGE).add(request,"과정게시판관리").link(model);
        return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}

	/**
	 * 게시판 정보 리스트를 가져온다.(페이징 없음)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriBbsInfoList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		 String systemCode = UserBroker.getSystemCode(request);
		 int totCnt = 0;
		 CurriBbsInfoDAO curriBbsInfoDao = new CurriBbsInfoDAO();
		 totCnt = curriBbsInfoDao.getCurriBbsCount(systemCode);
		 RowSet rs = curriBbsInfoDao.getCurriBbsInfoList(systemCode);
		 //model.put("systemCode", systemCode);
		 model.put("totCnt", String.valueOf(totCnt));
		 model.put("rs", rs);
		 new SiteNavigation(MYPAGE).add(request,"과정게시판관리").link(model);
         return forward(request, model, "/curri_bbs/curriBbsInfoList.jsp");
	}
	/**
	 * 게시판 정보 리스트를 페이징 처리 하여 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriBbsInfoPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		ListDTO curriBbsInfoList = null;
		int totCnt = 0;
		int curPage = 1;
		if(request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));
		CurriBbsInfoDAO curriBbsInfoDao = new CurriBbsInfoDAO();
		totCnt = curriBbsInfoDao.getCurriBbsCount(systemCode);
		model.put("totCnt", String.valueOf(totCnt));
		model.put("CurriBbsInfoList", curriBbsInfoDao.getCurriBbsInfoPagingList(curPage,systemCode));
		new SiteNavigation(MYPAGE).add(request,"과정게시판관리").link(model);
		return forward(request, model, "/curri_bbs/curriBbsInfoPagingList.jsp");
	}




}

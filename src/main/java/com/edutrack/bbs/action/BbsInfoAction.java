package com.edutrack.bbs.action;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.bbs.dao.BbsContentsDAO;
import com.edutrack.bbs.dao.BbsInfoDAO;
import com.edutrack.bbs.dto.BbsInfoDTO;
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
 * @author bschoi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BbsInfoAction extends StrutsDispatchAction{
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
	public ActionForward bbsInfoWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		new SiteNavigation(MYPAGE).add(request,"게시판관리").link(model);
		return forward(request, model, "/bbs/bbsInfoWrite.jsp");
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
	public ActionForward bbsInfoEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		int bbsId = Integer.parseInt(request.getParameter("pBbsId"));
		String FilePath = FileUtil.IMG_DIR+systemCode+"/bbs/";
		model.put("FilePath",FilePath);
		model.put("pBbsId",String.valueOf(bbsId));
		BbsInfoDAO bbsInfoDao = new BbsInfoDAO();
		RowSet rs = bbsInfoDao.getBbsInfo(systemCode,bbsId);
		RowSet rs2 = bbsInfoDao.getBbsInfo(systemCode,bbsId);
		rs2.next();
		log.debug("=system_code	="+StringUtil.nvl(rs2.getString("system_code")));

		log.debug("=bbs_name	="+StringUtil.nvl(rs2.getString("bbs_name")));
		log.debug("=manager_id	="+StringUtil.nvl(rs2.getString("manager_id")));
		log.debug("=title_img	="+StringUtil.nvl(rs2.getString("title_img")));
		log.debug("=bbs_comment	="+StringUtil.nvl(rs2.getString("bbs_comment")));
		log.debug("=bad_word	="+StringUtil.nvl(rs2.getString("bad_word")));

		model.put("rs", rs);
		//String pMode = "MyPage";
		//new SiteNavigation("MyPage").add(request,"게시판관리").link(model);
		new SiteNavigation(MYPAGE).add(request,"게시판관리").link(model);
        return forward(request, model, "/bbs/bbsInfoEdit.jsp");
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
	public ActionForward bbsInfoDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		int bbsId = Integer.parseInt(request.getParameter("pBbsId"));
		BbsInfoDAO bbsInfoDao = new BbsInfoDAO();

		RowSet bi = bbsInfoDao.getBbsInfo(systemCode,bbsId);//-- 첨부파일 삭제를 위해
		bi.next();
		String FilePath = FileUtil.IMG_DIR+systemCode+"/bbs/";
		String titleImg = StringUtil.nvl(bi.getString("title_img"));

		int retVal = 0;
		retVal = bbsInfoDao.delBbsInfo(systemCode,bbsId);
		BbsContentsDAO bbsContentsDao = new BbsContentsDAO();
		if(!titleImg.equals("") && retVal > 0){ //-- 게시판정보 삭제 성공하고 첨부 이미지 있는경우
			FileUtil.delFile(FilePath, titleImg);
			retVal = bbsContentsDao.delBbsContents(systemCode,bbsId,0);
			log.debug("정보 첨부파일을 삭제하였습니다."+FilePath+titleImg);
		}

		String msg = "삭제하였습니다.";
		String returnUrl = "/BbsInfo.cmd?cmd=bbsInfoPagingList";

		 new SiteNavigation(MYPAGE).add(request,"게시판관리").link(model);
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
	public ActionForward bbsInfoFileDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=====================bbsInfoFileDelete start");
		String systemCode = UserBroker.getSystemCode(request);
		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));
		BbsInfoDAO bbsInfoDao = new BbsInfoDAO();

		RowSet bi = bbsInfoDao.getBbsInfo(systemCode,pBbsId);//-- 첨부파일 삭제를 위해
		bi.next();
		String FilePath = FileUtil.IMG_DIR+systemCode+"/bbs/";
		String titleImg = StringUtil.nvl(bi.getString("title_img"));

		int retVal = 0;
		retVal = bbsInfoDao.delBbsInfoFile(systemCode,pBbsId);

		if(!titleImg.equals("") && retVal > 0){ //-- 게시판정보 수정 성공하고 첨부 이미지 있는경우
			FileUtil.delFile(FilePath, titleImg);
			log.debug("정보 첨부파일을 삭제하였습니다."+FilePath+titleImg);
		}

		String msg = "삭제하였습니다";
		String returnUrl = "/BbsInfo.cmd?cmd=bbsInfoEdit&pBbsId="+pBbsId;
		//new SiteNavigation(MYPAGE).add(request,"게시판관리").link(model);
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
	public ActionForward bbsInfoRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=====================bbsInfoRegist start");

		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);
		int pBbsId = Integer.parseInt(StringUtil.nvl(request.getParameter("pBbsId"),"0"));

		BbsInfoDAO bbsInfoDao = new BbsInfoDAO();
		BbsInfoDTO bbsInfoDto = new BbsInfoDTO();

		MultipartRequest multipartRequest = null;
		int retVal = 0;
		String FilePath = FileUtil.IMG_DIR+systemCode+"/bbs/";
		log.debug("FilePath ==>"+FilePath);
		String pOldFileName = "";
		String originName = "";
		String renFileName = "";

		String regMode = StringUtil.nvl(request.getParameter("pRegMode"));
		log.debug("regMode ==>"+regMode);
		//int maxId = bbsInfoDao.getMaxBbsId(systemCode);

		if(regMode.equals("Add"))// 입력모드
		{
			pBbsId = bbsInfoDao.getMaxBbsId(systemCode);
		}

//		  파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath,"bbsTitle_"+String.valueOf(pBbsId),"M");
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
					log.debug("++++++++++++++++ ObjName = "+file.getObjName());
					log.debug("++++++++++++++++ path = "+uploadEntity.getUploadPath());

					renFileName = file.getUploadName();
				}
			}

			if(!originName.equals("")) {			//파일첨부를 했을경우
				bbsInfoDto.setTitleImg(renFileName);
				if(!pOldFileName.equals("")) {		//이전 첨부파일을 삭제할 경우
					log.debug("이전 파일 삭제하기"+pOldFileName);
					//FileUtil.delFile(FilePath, pOldFileName);
					log.debug("이전 첨부파일을 삭제하였습니다.");
				}
			}else{									// 첨부 안한 경우
				bbsInfoDto.setTitleImg(pOldFileName);
			}
		}
		bbsInfoDto.setSystemCode(systemCode);
		bbsInfoDto.setBbsId(pBbsId);
		bbsInfoDto.setBbsName(StringUtil.nvl(multipartRequest.getParameter("pBbsName")));
		bbsInfoDto.setBbsType(StringUtil.nvl(multipartRequest.getParameter("pBbsType")));
		bbsInfoDto.setDispLine(StringUtil.nvl(multipartRequest.getParameter("pDispLine"),10));
		bbsInfoDto.setDispPage(StringUtil.nvl(multipartRequest.getParameter("pDispPage"),10));
		bbsInfoDto.setManagerId(StringUtil.nvl(multipartRequest.getParameter("pManagerId")));
		bbsInfoDto.setFileUseYn(StringUtil.nvl(multipartRequest.getParameter("pFileUseYn")));
		bbsInfoDto.setFileCount(StringUtil.nvl(multipartRequest.getParameter("pFileCount"),1));
		bbsInfoDto.setFileLimit(StringUtil.nvl(multipartRequest.getParameter("pFileLimit")));
		bbsInfoDto.setUseYn(StringUtil.nvl(multipartRequest.getParameter("pUseYn")));
		bbsInfoDto.setEditorYn(StringUtil.nvl(multipartRequest.getParameter("pEditorYn")));
		bbsInfoDto.setVoiceYn(StringUtil.nvl(multipartRequest.getParameter("pVoiceYn"),"N"));
		bbsInfoDto.setLoginChkYn(StringUtil.nvl(multipartRequest.getParameter("pLoginChkYn")));
		bbsInfoDto.setBbsComment(StringUtil.nvl(multipartRequest.getParameter("pBbsComment")));
		bbsInfoDto.setNewTime(StringUtil.nvl(multipartRequest.getParameter("pNewTime"),24));
		bbsInfoDto.setHotChk(StringUtil.nvl(multipartRequest.getParameter("pHotChk"),100));
		bbsInfoDto.setBadWordUseYn(StringUtil.nvl(multipartRequest.getParameter("pBadWordUseYn")));
		bbsInfoDto.setBadWord(StringUtil.nvl(multipartRequest.getParameter("pBadWord")));
		bbsInfoDto.setMailToMaster(StringUtil.nvl(multipartRequest.getParameter("pMailToMaster"),"N"));
		bbsInfoDto.setMailToManager(StringUtil.nvl(multipartRequest.getParameter("pMailToManager"),"N"));
		bbsInfoDto.setCommentUseYn(StringUtil.nvl(multipartRequest.getParameter("pCommentUseYn")));
		bbsInfoDto.setEmoticonUseYn(StringUtil.nvl(multipartRequest.getParameter("pEmoticonUseYn")));
		bbsInfoDto.setViewThreadYn(StringUtil.nvl(multipartRequest.getParameter("pViewThreadYn")));
		bbsInfoDto.setViewPrevNextYn(StringUtil.nvl(multipartRequest.getParameter("pViewPrevNextYn")));
		bbsInfoDto.setRegId(regId);
		String msg = "";
		String returnUrl = "/BbsInfo.cmd?cmd=bbsInfoPagingList";
		if(regMode.equals("Add"))// 입력모드
		{
			retVal = bbsInfoDao.addBbsInfo(bbsInfoDto);
			msg = "등록완료";
		}else{
			retVal = bbsInfoDao.editBbsInfo(bbsInfoDto);
			msg = "수정완료";
			if(retVal <= 0){
				returnUrl = "/BbsInfo.cmd?cmd=bbsInfoEdit&pBbsId="+pBbsId;
				msg = "수정오류 다시 진행해 주세요";
			}
		}
		log.debug("retVal ==>"+retVal);

		new SiteNavigation(MYPAGE).add(request,"게시판관리").link(model);
		log.debug("======================bbsInfoRegist end");
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
	public ActionForward bbsInfoList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		 int totCnt = 0;
		 BbsInfoDAO bbsInfoDao = new BbsInfoDAO();
		 totCnt = bbsInfoDao.getBbsCount(systemCode);
		 RowSet rs = bbsInfoDao.getBbsInfoList(systemCode);
		 //model.put("systemCode", systemCode);
		 model.put("totCnt", String.valueOf(totCnt));
		 model.put("rs", rs);
		 new SiteNavigation(MYPAGE).add(request,"게시판관리").link(model);
         return forward(request, model, "/bbs/bbsInfoList.jsp");
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
	public ActionForward bbsInfoPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		ListDTO bbsInfoList = null;
		int totCnt = 0;
		int curPage = 1;
		if(request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));
		BbsInfoDAO bbsInfoDao = new BbsInfoDAO();
		totCnt = bbsInfoDao.getBbsCount(systemCode);
		model.put("totCnt", String.valueOf(totCnt));
		model.put("bbsInfoList", bbsInfoDao.getBbsInfoPagingList(curPage,systemCode));
		new SiteNavigation(MYPAGE).add(request,"게시판관리").link(model);
		return forward(request, model, "/bbs/bbsInfoPagingList.jsp");
	}

}

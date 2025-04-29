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

import com.edutrack.VBN.VBN_files;
import com.edutrack.bbs.dao.BbsContentsDAO;
import com.edutrack.bbs.dto.BbsContentsDTO;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.DateSetter;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.curribbs.dao.CurriBbsCommentDAO;
import com.edutrack.curribbs.dao.CurriBbsContentsDAO;
import com.edutrack.curribbs.dao.CurriBbsInfoDAO;
import com.edutrack.curribbs.dto.CurriBbsContentsDTO;
import com.edutrack.curribbs.dto.CurriBbsInfoDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;



/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CurriBbsContentsAction  extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();


	/**
	 * 게시글 작성 폼을 띄워준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriBbsContentsWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;

	    String systemCode = userMemDto.systemCode;

	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pBbsType = StringUtil.nvl(request.getParameter("pBbsType"));

		int pSeqNo = StringUtil.nvl(request.getParameter("pSeqNo"),0);
		String pRegMode = "Add";
		if(pSeqNo > 0 ){
			CurriBbsContentsDAO curriBbsContentsDao = new CurriBbsContentsDAO();
			CurriBbsContentsDTO curriBbsContentsDto = new CurriBbsContentsDTO();
			curriBbsContentsDto = curriBbsContentsDao.getCurriBbsContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pBbsType, pSeqNo);
			model.put("pBbsNo", String.valueOf(curriBbsContentsDto.getBbsNo()));
			model.put("pDepthNo", String.valueOf(curriBbsContentsDto.getDepthNo()));
			model.put("pOrderNo", String.valueOf(curriBbsContentsDto.getOrderNo()));
			model.put("pContentsType", String.valueOf(StringUtil.nvl(curriBbsContentsDto.getContentsType())));
			model.put("pParNo", String.valueOf(pSeqNo));
			pRegMode = "Reply";
		}
		CurriBbsInfoDAO curriBbsInfoDao = new CurriBbsInfoDAO();
		RowSet infoRs = curriBbsInfoDao.getCurriBbsInfo(systemCode, pBbsType);
		infoRs.next();

		String bbsName = StringUtil.nvl(infoRs.getString("bbs_name"));
		model.put("titleImg", StringUtil.nvl(infoRs.getString("title_img")));
		model.put("pRegMode", String.valueOf(pRegMode));
		model.put("pBbsType", pBbsType);
		model.put("pFileUseYn", StringUtil.nvl(infoRs.getString("file_use_yn")));
		model.put("pFileCount", String.valueOf(infoRs.getInt("file_count")));
		model.put("pEditorYn", StringUtil.nvl(infoRs.getString("editor_yn")));
		model.put("pBadWordUseYn", StringUtil.nvl(infoRs.getString("bad_word_use_yn")));
		model.put("pBadWord", StringUtil.nvl(infoRs.getString("bad_word")));

		DateSetter ds = new DateSetter("").link(model);
		model.put("ds", ds);

		new SiteNavigation(LECTURE).add(request,StringUtil.inDataConverter(bbsName)).link(model);
		return forward(request, model, "/curri_bbs/curriBbsContentsWrite.jsp");
	}

	/**
	 * 게시글 수정 폼을 띄운다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriBbsContentsEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("__________________________________ curriBbsContentsEdit Start");
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;

	    String systemCode = userMemDto.systemCode;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		String pBbsType = StringUtil.nvl(request.getParameter("pBbsType"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));

		log.debug("__________________________________ curriBbsContentsEdit 1");
		CurriBbsInfoDAO curriBbsInfoDao = new CurriBbsInfoDAO();
		RowSet infoRs = curriBbsInfoDao.getCurriBbsInfo(systemCode,pBbsType);
		infoRs.next();
		String bbsName = StringUtil.nvl(infoRs.getString("bbs_name"));
		model.put("pCurriCode", pCurriCode);
		model.put("pCurriYear", Integer.toString(pCurriYear));
		model.put("pCurriTerm", Integer.toString(pCurriTerm));
		model.put("pBbsType", pBbsType);
		model.put("pFileUseYn", StringUtil.nvl(infoRs.getString("file_use_yn")));
		model.put("pFileCount", String.valueOf(infoRs.getInt("file_count")));
		model.put("pEditorYn", StringUtil.nvl(infoRs.getString("editor_yn")));
		model.put("pBadWordUseYn", StringUtil.nvl(infoRs.getString("bad_word_use_yn")));
		model.put("pBadWord", StringUtil.nvl(infoRs.getString("bad_word")));
		model.put("titleImg", StringUtil.nvl(infoRs.getString("title_img")));
		CurriBbsContentsDAO curriBbsContentsDao = new CurriBbsContentsDAO();
		CurriBbsContentsDTO curriBbsContentsDto = new CurriBbsContentsDTO();
		curriBbsContentsDto = curriBbsContentsDao.getCurriBbsContents(systemCode,pCurriCode, pCurriYear, pCurriTerm, pBbsType, pSeqNo);
		model.put("contentsDto",curriBbsContentsDto);

		new SiteNavigation(LECTURE).add(request,StringUtil.inDataConverter(bbsName)).link(model);
		log.debug("__________________________________ curriBbsContentsEdit End");
        return forward(request, model, "/curri_bbs/curriBbsContentsEdit.jsp");
	}

	/**
	 * 게시글 보여주기
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriBbsContentsShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("__________________________________ curriBbsContentsShow Start1");
		String	userType	=	UserBroker.getUserType(request);
		String	userId		=	UserBroker.getUserId(request);
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;

	    String systemCode = userMemDto.systemCode;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pBbsType = StringUtil.nvl(request.getParameter("pBbsType"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));

		CurriBbsInfoDAO curriBbsInfoDao = new CurriBbsInfoDAO();
		RowSet infoRs = curriBbsInfoDao.getCurriBbsInfo(systemCode,pBbsType);
		infoRs.next();
		String bbsName = StringUtil.nvl(infoRs.getString("bbs_name"));
		String pCommentUseYn = StringUtil.nvl(infoRs.getString("comment_use_yn"));
		String pViewThreadYn = StringUtil.nvl(infoRs.getString("view_thread_yn"));
		String pViewPrevNextYn = StringUtil.nvl(infoRs.getString("view_prev_next_yn"));
		model.put("titleImg", StringUtil.nvl(infoRs.getString("title_img")));
		model.put("pCurriCode", pCurriCode);
		model.put("pCurriYear", Integer.toString(pCurriYear));
		model.put("pCurriTerm", Integer.toString(pCurriTerm));
		model.put("pBbsType", pBbsType);
		model.put("pFileUseYn", StringUtil.nvl(infoRs.getString("file_use_yn")));
		model.put("pFileCount", String.valueOf(infoRs.getInt("file_count")));
		model.put("pCommentUseYn", pCommentUseYn);
		model.put("pEmoticonUseYn", StringUtil.nvl(infoRs.getString("emoticon_use_yn")));
		model.put("pViewThreadYn", pViewThreadYn);
		model.put("pViewPrevNextYn", pViewPrevNextYn);
		model.put("pNewTime", String.valueOf(infoRs.getInt("new_time")));
		model.put("pHotChk", String.valueOf(infoRs.getInt("hot_chk")));
		CurriBbsContentsDAO curriBbsContentsDao = new CurriBbsContentsDAO();

		CurriBbsContentsDTO curriBbsContentsDto = new CurriBbsContentsDTO();
		curriBbsContentsDto = curriBbsContentsDao.getCurriBbsContents(systemCode,pCurriCode, pCurriYear, pCurriTerm, pBbsType,pSeqNo);
		model.put("contentsDto",curriBbsContentsDto);
		if(pViewPrevNextYn.equals("Y")){
			String pPrevSubject = "";
			String pNextSubject = "";

			int pPrevSeqNo = curriBbsContentsDao.getCurriBbsContentsPrevSeqNo(systemCode,pCurriCode, pCurriYear, pCurriTerm, pBbsType,curriBbsContentsDto.getBbsNo());
			log.debug("----------- pPrevSeqNo"+pPrevSeqNo);
			if(pPrevSeqNo > 0)
				pPrevSubject = curriBbsContentsDao.getCurriBbsContentsSubject(systemCode,pCurriCode, pCurriYear, pCurriTerm, pBbsType,pPrevSeqNo);
			int pNextSeqNo = curriBbsContentsDao.getCurriBbsContentsNextSeqNo(systemCode,pCurriCode, pCurriYear, pCurriTerm, pBbsType, curriBbsContentsDto.getBbsNo());
			log.debug("----------- pNextSeqNo"+pNextSeqNo);
			if(pNextSeqNo > 0)
				pNextSubject = curriBbsContentsDao.getCurriBbsContentsSubject(systemCode,pCurriCode, pCurriYear, pCurriTerm, pBbsType, pNextSeqNo);
			model.put("pPrevSeqNo",String.valueOf(pPrevSeqNo));
			model.put("pPrevSubject",pPrevSubject);
			model.put("pNextSeqNo",String.valueOf(pNextSeqNo));
			model.put("pNextSubject",pNextSubject);
		}
		log.debug("__________________________________ curriBbsContentsShow 3");
		String AddWhere = "";
		if(pViewThreadYn.equals("Y")){
			AddWhere = " bbs_no="+curriBbsContentsDto.getBbsNo();
			String Order = "";
			int listCnt = 0;
			listCnt = curriBbsContentsDao.getCurriBbsContentsCount(systemCode,pCurriCode, pCurriYear, pCurriTerm, pBbsType,AddWhere);
			model.put("listCnt", String.valueOf(listCnt));
			if(listCnt>1){
				RowSet listRs = curriBbsContentsDao.getCurriBbsContentsList(systemCode, pCurriCode, pCurriYear, pCurriTerm, pBbsType, AddWhere, Order, 0);
				model.put("listRs", listRs);
			}
		}
		//-- 삭제시 하위글 있는지 체크하기위해서 아래 글 갯수를 가져온다.
		int pChildCnt = 0;
		AddWhere = "par_no="+pSeqNo;
		pChildCnt = curriBbsContentsDao.getCurriBbsContentsCount(systemCode,pCurriCode, pCurriYear, pCurriTerm, pBbsType,AddWhere);
		model.put("pChildCnt", String.valueOf(pChildCnt));

		//-- 조회수 증가 시키기
		int retVal = 0;
		if(!userType.equals("M") && !userId.equals(StringUtil.nvl(curriBbsContentsDto.getRegId()))) {
			retVal = curriBbsContentsDao.hitUpCurriBbsContents(systemCode,pCurriCode, pCurriYear, pCurriTerm, pBbsType, pSeqNo);
		}

		model.put("curPage", StringUtil.nvl(request.getParameter("curPage")));

		new SiteNavigation(LECTURE).add(request,StringUtil.inDataConverter(bbsName)).link(model);
		log.debug("__________________________________ curriBbsContentsShow End");
        return forward(request, model, "/curri_bbs/curriBbsContentsShow.jsp");
	}

	/**
	 * 게시글을 삭제한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriBbsContentsDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("===========================curriBbsContentsDelete start");
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		String pBbsType = StringUtil.nvl(request.getParameter("pBbsType"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));
		String pContents = StringUtil.nvl(request.getParameter("pContents"));
		CurriBbsInfoDAO curriBbsInfoDao = new CurriBbsInfoDAO();
		CurriBbsContentsDAO curriBbsContentsDao = new CurriBbsContentsDAO();

		RowSet infoRs = curriBbsInfoDao.getCurriBbsInfo(systemCode,pBbsType);
		infoRs.next();
		String bbsName = StringUtil.nvl(infoRs.getString("bbs_name"));
		model.put("titleImg", StringUtil.nvl(infoRs.getString("title_img")));
		CurriBbsContentsDTO curriBbsContentsDto = new CurriBbsContentsDTO();
		curriBbsContentsDto = curriBbsContentsDao.getCurriBbsContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pBbsType ,pSeqNo);
		String rfile_name = "";
		String sfile_name = "";
		String filePath = "";

		String msg = "삭제하였습니다.";
		String returnUrl = "/CurriBbsContents.cmd?cmd=curriBbsContentsPagingList&pBbsType="+pBbsType;

		int retVal = 0;
		retVal = curriBbsContentsDao.delCurriBbsContents(systemCode, pBbsType, pSeqNo);
		log.debug("+++++++++++++++++++ retVal = "+retVal);
		if(retVal > 0){ //-- 게시글 삭제 성공한 경우 첨부 이미지 있으면 삭제
			for(int i=1;i<=3;i++){
				if(i == 1){
					rfile_name = StringUtil.nvl(curriBbsContentsDto.getRfileName1());
					sfile_name = StringUtil.nvl(curriBbsContentsDto.getSfileName1());
					filePath = StringUtil.nvl(curriBbsContentsDto.getFilePath1());
				}else if(i ==2){
					rfile_name = StringUtil.nvl(curriBbsContentsDto.getRfileName2());
					sfile_name = StringUtil.nvl(curriBbsContentsDto.getSfileName2());
					filePath = StringUtil.nvl(curriBbsContentsDto.getFilePath2());
				}else if(i == 3){
					rfile_name = StringUtil.nvl(curriBbsContentsDto.getRfileName3());
					sfile_name = StringUtil.nvl(curriBbsContentsDto.getSfileName3());
					filePath = StringUtil.nvl(curriBbsContentsDto.getFilePath3());
				}
				if(!rfile_name.equals("") && !sfile_name.equals("") && !filePath.equals("")){
					FileUtil.delFile(filePath, sfile_name);
					log.debug(" 첨부파일을 삭제하였습니다."+filePath+sfile_name);
				}
			}
			String RegMonth = StringUtil.nvl(curriBbsContentsDto.getRegDate()).substring(0,6);
			String FilePath = FileUtil.FILE_DIR+systemCode+"/curri_bbs/"+pBbsType+"/"+RegMonth+"/";//-- 게시판아이디 + 년월
			log.debug("FilePath ==========================>"+FilePath);
			/** 웹에디터 셋팅 추가 Start**/
			String ServiceUrl = CommonUtil.SERVERURL;
			String WeasFilePath = FileUtil.UPLOAD_PATH+FilePath;
			String WeasFileUrl =  ServiceUrl+"/data/"+FilePath;
			log.debug("WeasFilePath = "+WeasFilePath);

			VBN_files v_objFile = null;
			v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
			log.debug("contents ==========================>"+pContents);
			//v_objFile.VBN_deleteFiles(pContents);
			//--************************* contents 삭제시 반드시 해 줄
			pContents = StringUtil.ReplaceAll(StringUtil.nvl(curriBbsContentsDto.getContents()),"&quot;","\"");
			v_objFile.VBN_deleteFiles(pContents);
			//-- 글 순서 정렬
			CurriBbsContentsDTO curriBbsContentsDto2 = new CurriBbsContentsDTO();
			curriBbsContentsDto2.setSystemCode(systemCode);
			curriBbsContentsDto2.setBbsType(pBbsType);
			curriBbsContentsDto2.setBbsNo(curriBbsContentsDto.getBbsNo());
			curriBbsContentsDto2.setOrderNo(curriBbsContentsDto.getOrderNo());
			boolean bVal = curriBbsContentsDao.replyUpdateCurriBbsContents(curriBbsContentsDto,"Del");
		}else{
			msg = "삭제 실패 하였습니다.";
			returnUrl = "/CurriBbsContents.cmd?cmd=curriBbsContentsShow&pBbsType="+pBbsType+"&pSeqNo="+pSeqNo;
		}

		new SiteNavigation(LECTURE).add(request,StringUtil.inDataConverter(bbsName)).link(model);
		 log.debug("===========================curriBbsContentsDelete end");
		 return notifyAndExit(systemCode, model, msg, returnUrl, LECTURE);
	}
	/**
	 * 게시글 첨부파일 개별 삭제
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriBbsContentsFileDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("+++++++++++++++++++ curriBbsContentsFileDelete start ");
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		log.debug("+++++++++++++++++++ curriBbsContentsFileDelete ");
		String pBbsType = StringUtil.nvl(request.getParameter("pBbsType"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));
		String pFileNo = request.getParameter("pFileNo");

		CurriBbsInfoDAO curriBbsInfoDao = new CurriBbsInfoDAO();
		CurriBbsContentsDAO curriBbsContentsDao = new CurriBbsContentsDAO();

		RowSet infoRs = curriBbsInfoDao.getCurriBbsInfo(systemCode,pBbsType);
		infoRs.next();
		String bbsName = StringUtil.nvl(infoRs.getString("bbs_name"));
		model.put("titleImg", StringUtil.nvl(infoRs.getString("title_img")));
		CurriBbsContentsDTO curriBbsContentsDto = new CurriBbsContentsDTO();
		curriBbsContentsDto = curriBbsContentsDao.getCurriBbsContents(systemCode,pCurriCode, pCurriYear, pCurriTerm, pBbsType,pSeqNo);

		                                                                                                                                                                                                                               String rfile_name = "";
		String sfile_name = "";
		String filePath = "";
		log.debug("+++++++++++++++++++ curriBbsContentsFileDelete start = 2");
		String msg = "삭제하였습니다.";
		String returnUrl = "/CurriBbsContents.cmd?cmd=curriBbsContentsEdit&pBbsType="+pBbsType+"&pSeqNo="+pSeqNo;

		int retVal = 0;
		retVal = curriBbsContentsDao.delCurriBbsContentsFile(systemCode,pCurriCode, pCurriYear, pCurriTerm, pBbsType, pSeqNo, pFileNo);

		if(pFileNo.equals("1")){
			rfile_name = StringUtil.nvl(curriBbsContentsDto.getRfileName1());
			sfile_name = StringUtil.nvl(curriBbsContentsDto.getSfileName1());
			filePath = StringUtil.nvl(curriBbsContentsDto.getFilePath1());
		}else if(pFileNo.equals("2")){
			rfile_name = StringUtil.nvl(curriBbsContentsDto.getRfileName2());
			sfile_name = StringUtil.nvl(curriBbsContentsDto.getSfileName2());
			filePath = StringUtil.nvl(curriBbsContentsDto.getFilePath2());
		}else if(pFileNo.equals("3")){
			rfile_name = StringUtil.nvl(curriBbsContentsDto.getRfileName3());
			sfile_name = StringUtil.nvl(curriBbsContentsDto.getSfileName3());
			filePath = StringUtil.nvl(curriBbsContentsDto.getFilePath3());
		}
		if(!rfile_name.equals("") && !sfile_name.equals("") && !filePath.equals("")){
			FileUtil.delFile(filePath, sfile_name);
			log.debug(" 첨부파일을 삭제하였습니다."+filePath+sfile_name);
			log.debug("+++++++++++++++++++ 첨부파일을 삭제하였습니다 = 2");
		}


		if(retVal <=0){
			msg = "첨부파일 삭제 실패 하였습니다.";
			log.debug("+++++++++++++++++++ 첨부파일을 삭제 실패하였습니다 ");
		}


		new SiteNavigation(LECTURE).add(request,StringUtil.inDataConverter(bbsName)).link(model);

		log.debug("+++++++++++++++++++ curriBbsContentsFileDelete  end");
        return notifyAndExit(systemCode, model, msg, returnUrl, LECTURE);
	}

	/**
	 * 게시글 등록/수정 한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriBbsContentsRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=================curriBbsContentsRegist start");

		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String    systemCode = userMemDto.systemCode;
		String    regId = userMemDto.userId;
		String    pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int	pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int	pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String	userName = userMemDto.userName;
			log.debug("=================curriBbsContentsRegis2");

		String pBbsType = StringUtil.nvl(request.getParameter("pBbsType"));
		int pSeqNo = Integer.parseInt(StringUtil.nvl(request.getParameter("pSeqNo"),"1"));
		CurriBbsInfoDAO curriBbsInfoDao = new CurriBbsInfoDAO();
		CurriBbsInfoDTO curriBbsInfoDto = new CurriBbsInfoDTO();
		RowSet infoRs = curriBbsInfoDao.getCurriBbsInfo(systemCode,pBbsType);
		model.put("infoRs", infoRs);
		infoRs.next();
		String bbsName = StringUtil.nvl(infoRs.getString("bbs_name"));
		model.put("titleImg", StringUtil.nvl(infoRs.getString("title_img")));
		CurriBbsContentsDAO curriBbsContentsDao = new CurriBbsContentsDAO();
		CurriBbsContentsDTO curriBbsContentsDto = new CurriBbsContentsDTO();
		log.debug("=================curriBbsContentsRegis2");
		MultipartRequest multipartRequest = null;
		int retVal = 0;
		String objName = "";
		String rFileName = "";
		String sFileName = "";
		String filePath = "";
		String fileSize = "";

		String regMode = StringUtil.nvl(request.getParameter("pRegMode"));
		log.debug("regMode ========================>"+regMode);

		if(regMode.equals("Add") || regMode.equals("Reply") )// 입력모드
		{
			pSeqNo = curriBbsContentsDao.getMaxSeqNo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pBbsType);
		}
		String RegMonth = CommonUtil.getCurrentDate().substring(0,6);
		if(regMode.equals("Edit")){//-- 수정모드시는 현재 월이 아닌 등록된 글의 년월을 가져온다.
			RegMonth = StringUtil.nvl(request.getParameter("pRegDate")).substring(0,6);
		}

		String FilePath = FileUtil.FILE_DIR+systemCode+"/curri_bbs/"+pBbsType+"/"+RegMonth+"/";//-- 게시판아이디 + 년월
		log.debug("FilePath ==========================>"+FilePath);
		if(systemCode.equals(""))
			systemCode = StringUtil.nvl(request.getParameter("pSystemCode"), "");
		if(regId.equals(""))
			regId = StringUtil.nvl(request.getParameter("pUserId"), "");

//		  파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath,pSeqNo+"_"+regId);
		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();
		String status = uploadEntity.getStatus();

		//String pFile[] = new String[4];
		String pOldrFileName[] = new String[4];
		String pOldsFileName[] = new String[4];
		String pOldFilePath[] = new String[4];
		String pOldFileSize[] = new String[4];
		for(int i=1;i<=3;i++){
			//pFile[i] = StringUtil.nvl(multipartRequest.getParameter("pFile["+i+"]"));
			pOldrFileName[i] = StringUtil.nvl(multipartRequest.getParameter("pOldrFileName["+i+"]"));
			pOldsFileName[i] = StringUtil.nvl(multipartRequest.getParameter("pOldsFileName["+i+"]"));
			pOldFilePath[i] = StringUtil.nvl(multipartRequest.getParameter("pOldFilePath["+i+"]"));
			pOldFileSize[i] = StringUtil.nvl(multipartRequest.getParameter("pOldFileSize["+i+"]"));
		}

		curriBbsContentsDto.setRfileName1(pOldrFileName[1]);		curriBbsContentsDto.setSfileName1(pOldsFileName[1]);
		curriBbsContentsDto.setFilePath1(pOldFilePath[1]);		curriBbsContentsDto.setFileSize1(pOldFileSize[1]);
		curriBbsContentsDto.setRfileName2(pOldrFileName[2]);		curriBbsContentsDto.setSfileName2(pOldsFileName[2]);
		curriBbsContentsDto.setFilePath2(pOldFileSize[2]);		curriBbsContentsDto.setFileSize2(pOldFileSize[2]);
		curriBbsContentsDto.setRfileName3(pOldrFileName[3]);		curriBbsContentsDto.setSfileName3(pOldsFileName[3]);
		curriBbsContentsDto.setFilePath3(pOldFileSize[3]);		curriBbsContentsDto.setFileSize3(pOldFileSize[3]);

		String pSubject = StringUtil.nvl(multipartRequest.getParameter("pSubject"));
//		String pKeyword = StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
//		String pContents = StringUtil.nvl(multipartRequest.getParameter("pContents"));

		String pKeyword = "";
		String pEditorType = "";
		if( new String("true").equals(multipartRequest.getParameter("VBN_FORM_WEAS")) == true ){//-- 웹에디터 사용시
			 pKeyword = StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
			 pEditorType = "W";
		}else{//-- 웹에디터 사용 안할경
			pKeyword = StringUtil.nvl(multipartRequest.getParameter("pContents"));
			pEditorType = "H";
		}
		//-- 음성게시물은 V 로 셋팅
		if(StringUtil.nvl(multipartRequest.getParameter("pEditorType")).equals("V"))
			pEditorType = "V";

		String pContents = StringUtil.nvl(multipartRequest.getParameter("pContents"));
		String pCourseId = StringUtil.nvl(multipartRequest.getParameter("pCourseId"));
		if (status.equals("E")){
			log.debug("첨부 파일 올리려다 실패하였습니다.");
		}else if (status.equals("O")){
			log.debug("첨부하신 파일이 용량을 초과했습니다.");
		}else if (status.equals("I")){
			log.debug("첨부 파일의 정보가 잘못되었습니다.");
		}else if(status.equals("S")){
			// 업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			log.debug("첨부 파일을 첨부하는데 성공하였습니다.");
			/** 웹에디터에서 첨부파일을 첨부시 인식하는걸 막기위해 **/
			ArrayList files = uploadEntity.getFiles();
			UploadFile file = null;
			for(int i = 0 ; i < files.size(); i++){
				file = (UploadFile)files.get(i);
				objName = file.getObjName();
				rFileName = StringUtil.nvl(file.getRootName());
				if(!rFileName.equals("")) {
					log.debug("++++++++++++++++ ObjName = "+file.getObjName());
					log.debug("++++++++++++++++ FileName = "+file.getRootName());
					log.debug("++++++++++++++++ path = "+uploadEntity.getUploadPath());
					sFileName = file.getUploadName();
					fileSize = String.valueOf(file.getSize());
					filePath = uploadEntity.getUploadPath();
				}
				if(objName.indexOf("pFile[")>=0){
					int idx = Integer.parseInt(String.valueOf(objName.charAt(6)));
					log.debug("++++++++++++++++ idx = "+idx);
					if(idx == 1){
						curriBbsContentsDto.setRfileName1(rFileName);	curriBbsContentsDto.setSfileName1(sFileName);
						curriBbsContentsDto.setFilePath1(filePath);		curriBbsContentsDto.setFileSize1(fileSize);
					}else if(idx == 2){
						curriBbsContentsDto.setRfileName2(rFileName);	curriBbsContentsDto.setSfileName2(sFileName);
						curriBbsContentsDto.setFilePath2(filePath);		curriBbsContentsDto.setFileSize2(fileSize);
					}else if(idx == 3){
						curriBbsContentsDto.setRfileName3(rFileName);	curriBbsContentsDto.setSfileName3(sFileName);
						curriBbsContentsDto.setFilePath3(filePath);		curriBbsContentsDto.setFileSize3(fileSize);
					}
					if(!pOldsFileName[idx].equals("")) {		//이전 첨부파일을 삭제할 경우
						log.debug("이전 파일 삭제하기"+pOldFilePath[idx]+pOldsFileName[idx]);
						FileUtil.delFile(pOldFilePath[idx], pOldsFileName[idx]);
					}
				}
			}// For End
		}

		/** 웹에디터 셋팅 추가 Start**/
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH+FilePath;
		String WeasFileUrl =  ServiceUrl+"/data/"+FilePath;
		log.debug("WeasFilePath = "+WeasFilePath);
		VBN_files v_objFile = null;


		java.io.File dir = new java.io.File(WeasFilePath);
		if (!dir.exists())	dir.mkdirs();

		v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
		pContents = v_objFile.VBN_uploadMultiFiles(pContents, multipartRequest);

		/** 웹에디터 셋팅 추가 End**/

//		String pEditorType = StringUtil.nvl(multipartRequest.getParameter("pEditorType"));

		int pBbsNo = StringUtil.nvl(multipartRequest.getParameter("pBbsNo"),pSeqNo);
		int pDepthNo = StringUtil.nvl(multipartRequest.getParameter("pDepthNo"),0);
		int pOrderNo = StringUtil.nvl(multipartRequest.getParameter("pOrderNo"),0);
		int pParNo= StringUtil.nvl(multipartRequest.getParameter("pParNo"),0);

		String pContentsType = StringUtil.nvl(multipartRequest.getParameter("pContentsType"));
		log.debug("--------------  pEditorType = "+pEditorType);
		log.debug("+++++++++++++++++++++++++++++++++   pContentsType = "+pContentsType);

		if(regMode.equals("Add")){
			pBbsNo = pSeqNo;
			pDepthNo = 0;
			pOrderNo = 0;
			pParNo = 0;
		}
		if(regMode.equals("Reply")){
			//pBbsNo = pBbsNo;
			pDepthNo = pDepthNo+1;
			pOrderNo = pOrderNo+1;
			//pParNo = pParNo;
		}
		log.debug("+++++++++++++++++++++++++++++++++   chk Point");

		if(userName.equals(""))
			userName = StringUtil.nvl(multipartRequest.getParameter("pRegName"));
		curriBbsContentsDto.setSystemCode(systemCode);
		curriBbsContentsDto.setCurriCode(pCurriCode);
		curriBbsContentsDto.setCurriYear(pCurriYear);
		curriBbsContentsDto.setCurriTerm(pCurriTerm);
		curriBbsContentsDto.setBbsType(pBbsType);
		curriBbsContentsDto.setSeqNo(pSeqNo);
		curriBbsContentsDto.setCourseId(pCourseId);
		curriBbsContentsDto.setBbsNo(pBbsNo);
		curriBbsContentsDto.setDepthNo(pDepthNo);
		curriBbsContentsDto.setOrderNo(pOrderNo);
		curriBbsContentsDto.setParNo(pParNo);
		curriBbsContentsDto.setEditorType(pEditorType);
		curriBbsContentsDto.setContentsType(pContentsType);
		curriBbsContentsDto.setSubject(pSubject);
		curriBbsContentsDto.setKeyword(pKeyword);
		curriBbsContentsDto.setContents(pContents);
		curriBbsContentsDto.setRegId(regId);
		curriBbsContentsDto.setRegName(userName);
		curriBbsContentsDto.setModId(regId);
		String msg = "";



		String returnUrl = "/CurriBbsContents.cmd?cmd=curriBbsContentsPagingList&pBbsType="+pBbsType;
		log.debug("+++++++++++++++++++++++++++++++++   chk Point 2");
		if(regMode.equals("Add"))// 입력모드
		{
			retVal = curriBbsContentsDao.addCurriBbsContents(curriBbsContentsDto);
			msg = "등록완료";
		}else if(regMode.equals("Edit")){
			retVal = curriBbsContentsDao.editCurriBbsContents(curriBbsContentsDto);
			msg = "수정완료";
			if(retVal <= 0){
				returnUrl = "/CurriBbsContents.cmd?cmd=curriBbsContentsEdit&pBbsType="+pBbsType+"&pSeqNo="+pSeqNo;
				msg = "수정오류 다시 진행해 주세요";
			}
		}else if(regMode.equals("Reply")){
			boolean bVal = curriBbsContentsDao.replyUpdateCurriBbsContents(curriBbsContentsDto,"Ins");
			if(bVal)	retVal  = 1;
			else		retVal  = 0;
			if(retVal >0){
				retVal = curriBbsContentsDao.addCurriBbsContents(curriBbsContentsDto);
				msg = "등록완료";
			}
			if(retVal <= 0){
				returnUrl = "/CurriBbsContents.cmd?cmd=curriBbsContentsWrite&pBbsType="+pBbsType+"&pSeqNo="+pSeqNo;
				msg = "등록오류 다시 진행해 주세요";
			}
		}
		log.debug("retVal ==>"+retVal);
		log.debug("returnUrl ==>"+returnUrl);

		new SiteNavigation(LECTURE).add(request,StringUtil.inDataConverter(bbsName)).link(model);
		log.debug("=============================================================curriBbsContentsRegist end");
        return notifyAndExit(systemCode, model, msg, returnUrl, LECTURE);
	}

	/**
	 * 게시글 리스트를 가져온다.(페이징 없음)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriBbsContentsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("============================= curriBbsContentsList");
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		String pBbsType = request.getParameter("pBbsType");

		int totCnt = 0;
		CurriBbsInfoDAO curriBbsInfoDao = new CurriBbsInfoDAO();
		CurriBbsContentsDAO curriBbsContentsDao = new CurriBbsContentsDAO();
		RowSet infoRs = curriBbsInfoDao.getCurriBbsInfo(systemCode,pBbsType);
		model.put("infoRs", infoRs);
		infoRs.next();
		String bbsName = StringUtil.nvl(infoRs.getString("bbs_name"));
		model.put("titleImg", StringUtil.nvl(infoRs.getString("title_img")));
		String pWhere = "";
		totCnt = curriBbsContentsDao.getCurriBbsContentsCount(systemCode, pBbsType, pWhere, "");
		String AddWhere = "";
		String Order = "";
		RowSet contentsRs = curriBbsContentsDao.getCurriBbsContentsList(systemCode, pCurriCode, pCurriYear, pCurriTerm, pBbsType, AddWhere,Order,0);
		model.put("totCnt", String.valueOf(totCnt));
		model.put("contentsRs", contentsRs);

		model.put("pBbsType", pBbsType);

		new SiteNavigation(LECTURE).add(request,StringUtil.inDataConverter(bbsName)).link(model);
		log.debug("============================= curriBbsContentsList End");
        return forward(request, model, "/curri_bbs/curriBbsContentsList.jsp");
	}


	/**
	 * 게시글 리스트를 페이징 처리 하여 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriBbsContentsPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("_________________________ curriBbsContentsPagingList start");

		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;

	    String systemCode = userMemDto.systemCode;
		String	pBbsType = request.getParameter("pBbsType");
		int curPage = StringUtil.nvl(request.getParameter("curPage"),1);

		CurriBbsInfoDAO curriBbsInfoDao = new CurriBbsInfoDAO();
		CurriBbsContentsDAO curriBbsContentsDao = new CurriBbsContentsDAO();
		RowSet infoRs = curriBbsInfoDao.getCurriBbsInfo(systemCode, pBbsType);
		model.put("infoRs", infoRs);
		infoRs.next();
		String	bbsName	=	StringUtil.nvl(infoRs.getString("bbs_name"));
		model.put("pBbsType", pBbsType);
		model.put("pVoiceYn", StringUtil.nvl(infoRs.getString("voice_yn")));
		model.put("titleImg", StringUtil.nvl(infoRs.getString("title_img")));
		model.put("pNewTime", String.valueOf(infoRs.getInt("new_time")));
		model.put("pHotChk",  String.valueOf(infoRs.getInt("hot_chk")));

		model.put("curPage", String.valueOf(curPage));

		new SiteNavigation(LECTURE).add(request,StringUtil.inDataConverter(bbsName)).link(model);
		log.debug("_________________________ curriBbsContentsPagingList end");
        return forward(request, model, "/curri_bbs/curriBbsContentsPagingList.jsp?pBbsType="+pBbsType);
	}


	/**
	 * [2007.8.13] 과정게시판 리스트 AJAX 작업
	 * @param curPage
	 * @param pBbsType
	 * @param pSearchKey
	 * @param pKeyWord
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO getCurriBbsPageList(int curPage, String pBbsType, String pSearchKey, String pKeyWord, HttpServletRequest request, HttpServletResponse response) throws Exception{
		UserMemDTO	userMemDto	=	UserBroker.getUserInfo(request);
	    CurriMemDTO	curriMemDto	=	userMemDto.curriInfo;

	    String		systemCode	=	userMemDto.systemCode;
	    String		userType	=	userMemDto.userType;
	    if(userType.equals("")) userType="G";

	    pSearchKey	=	StringUtil.nvl(pSearchKey);
		pKeyWord	=	AjaxUtil.ajaxDecoding(StringUtil.nvl(pKeyWord));
		curPage		=	(curPage == 0) ? 1 : curPage;

	    String	pCurriCode = curriMemDto.curriCode;
		int		pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int		pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		//	게시판 설정 정보 가져오기
		CurriBbsInfoDAO		curriBbsInfoDao		=	new CurriBbsInfoDAO();
		RowSet		infoRs	=	curriBbsInfoDao.getCurriBbsInfo(systemCode, pBbsType);
		infoRs.next();
		String	bbsName		=	StringUtil.nvl(infoRs.getString("bbs_name"));
		int		dispLine	=	infoRs.getInt("disp_line");
		int		dispPage	=	infoRs.getInt("disp_page");
		String	pOrder		=	"";
		String	AddWhere	=	" a.contents_type = 'N'";

		//	리턴 데이타 세팅
		CurriBbsContentsDAO	curriBbsContentsDao	=	new CurriBbsContentsDAO();
		CurriBbsContentsDTO	bbsContentsDao	=	new CurriBbsContentsDTO();
		AjaxListDTO		ajaxListDto		=	new AjaxListDTO();
		ArrayList		data1List		=	new ArrayList();
		CurriBbsContentsDTO	noticeDto	=	null;

		//	공지사항 리스트
		if(curPage <= 1){
			RowSet contentsRs = curriBbsContentsDao.getCurriBbsContentsList(systemCode, pCurriCode, pCurriYear, pCurriTerm, pBbsType, AddWhere, pOrder,0);
			if(contentsRs.next()) {
				noticeDto	=	new CurriBbsContentsDTO();
				noticeDto.setCurriCode(StringUtil.nvl(contentsRs.getString("curri_code")));
				noticeDto.setCurriYear(contentsRs.getInt("curri_year"));
				noticeDto.setCurriTerm(contentsRs.getInt("curri_term"));
				noticeDto.setBbsType(StringUtil.nvl(contentsRs.getString("bbs_type")));
				noticeDto.setSeqNo(contentsRs.getInt("seq_no"));
				//noticeDto.setCourseId(StringUtil.nvl(contentsRs.getString("course_id")));
				noticeDto.setBbsNo(contentsRs.getInt("bbs_no"));
				noticeDto.setDepthNo(contentsRs.getInt("depth_no"));
				noticeDto.setOrderNo(contentsRs.getInt("order_no"));
				noticeDto.setParNo(contentsRs.getInt("par_no"));

				noticeDto.setCommCnt(contentsRs.getInt("comm_cnt"));
				noticeDto.setHitNo(contentsRs.getInt("hit_no"));

				noticeDto.setSubject(StringUtil.nvl(contentsRs.getString("subject")));
				noticeDto.setContentsType(StringUtil.nvl(contentsRs.getString("contents_type")));
				noticeDto.setEditorType(StringUtil.nvl(contentsRs.getString("editor_type")));
				noticeDto.setRegName(StringUtil.nvl(contentsRs.getString("reg_name")));
				noticeDto.setRegDate(StringUtil.nvl(contentsRs.getString("reg_date")));

				data1List.add(noticeDto);
			}
			contentsRs.close();
			ajaxListDto.setData1List(data1List);
		}

		ListDTO listObj = null;
		listObj	=	curriBbsContentsDao.getCurriBbsContentsPagingList(curPage, dispLine, dispPage, systemCode, pCurriCode, pCurriYear, pCurriTerm, pBbsType, userType, pSearchKey, pKeyWord , pOrder);
		//--	일반 게시글
		ArrayList			dataList	=	new ArrayList();
		CurriBbsContentsDTO	bbsDto		=	null;
		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				bbsDto	=	new CurriBbsContentsDTO();
				bbsDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				bbsDto.setCurriYear(rs.getInt("curri_year"));
				bbsDto.setCurriTerm(rs.getInt("curri_term"));
				bbsDto.setBbsType(StringUtil.nvl(rs.getString("bbs_type")));
				bbsDto.setSeqNo(rs.getInt("seq_no"));
				//bbsDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
				bbsDto.setBbsNo(rs.getInt("bbs_no"));
				bbsDto.setDepthNo(rs.getInt("depth_no"));
				bbsDto.setOrderNo(rs.getInt("order_no"));
				bbsDto.setParNo(rs.getInt("par_no"));

				bbsDto.setCommCnt(rs.getInt("comm_cnt"));
				bbsDto.setHitNo(rs.getInt("hit_no"));

				bbsDto.setSubject(StringUtil.nvl(rs.getString("subject")));
				bbsDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
				bbsDto.setEditorType(StringUtil.nvl(rs.getString("editor_type")));
				bbsDto.setRegName(StringUtil.nvl(rs.getString("reg_name")));
				bbsDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

				dataList.add(bbsDto);
			}
			rs.close();
			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}
		return ajaxListDto;

	}


	/**
	 * [2007.07.03] 게시글 정보 받아오기
	 *
	 * @return CurriBbsContentsDTO
	 * @throws Exception
	 */
	public CurriBbsContentsDTO getCurriBbsContentsInfo(String CurriCode, int CurriYear, int CurriTerm
													, String CourseId, String BbsType, int SeqNo
													, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		CurriBbsContentsDAO curriBbsContentsDao = new CurriBbsContentsDAO();
		CurriBbsContentsDTO contentsDto = null;

		RowSet	rs	=	curriBbsContentsDao.getCurriBbsContentsInfo(systemCode, CurriCode, CurriYear, CurriTerm, CourseId, BbsType, SeqNo);

		if(rs.next()) {
			contentsDto	=	new CurriBbsContentsDTO();
			contentsDto.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
			contentsDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
			contentsDto.setCurriYear(rs.getInt("curri_year"));
			contentsDto.setCurriTerm(rs.getInt("curri_term"));
			contentsDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			contentsDto.setBbsType(StringUtil.nvl(rs.getString("bbs_type")));
			contentsDto.setSeqNo(rs.getInt("seq_no"));
			contentsDto.setBbsNo(rs.getInt("bbs_no"));
			contentsDto.setDepthNo(rs.getInt("depth_no"));
			contentsDto.setOrderNo(rs.getInt("order_no"));
			contentsDto.setParNo(rs.getInt("par_no"));
			contentsDto.setRfileName1(StringUtil.nvl(rs.getString("rfile_name1")));
			contentsDto.setSfileName1(StringUtil.nvl(rs.getString("sfile_name1")));
			contentsDto.setFilePath1(StringUtil.nvl(rs.getString("file_path1")));
			contentsDto.setFileSize1(StringUtil.nvl(rs.getString("file_size1")));
			contentsDto.setRfileName2(StringUtil.nvl(rs.getString("rfile_name2")));
			contentsDto.setSfileName2(StringUtil.nvl(rs.getString("sfile_name2")));
			contentsDto.setFilePath2(StringUtil.nvl(rs.getString("file_path2")));
			contentsDto.setFileSize2(StringUtil.nvl(rs.getString("file_size2")));
			contentsDto.setRfileName3(StringUtil.nvl(rs.getString("rfile_name3")));
			contentsDto.setSfileName3(StringUtil.nvl(rs.getString("sfile_name3")));
			contentsDto.setFilePath3(StringUtil.nvl(rs.getString("file_path3")));
			contentsDto.setFileSize3(StringUtil.nvl(rs.getString("file_size3")));
		}
		rs.close();

		return contentsDto;
	}

	/**
	 * [2007.07.03] 첨부파일 삭제
	 *
	 * @return FileNum
	 */
	public String bbsFileDelete(String CurriCode, int CurriYear, int CurriTerm, String CourseId, String BbsType, int SeqNo, String FileNum, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String	systemCode		=	UserBroker.getSystemCode(request);

		String	deleteColumn	=	"";
		String	pathColumn		=	"";

		if(FileNum.equals("1")) {
			deleteColumn	=	"sfile_name1";
			pathColumn		=	"file_path1";
		} else if(FileNum.equals("2")) {
			deleteColumn	=	"sfile_name2";
			pathColumn		=	"file_path2";
		} else if(FileNum.equals("3")) {
			deleteColumn	=	"sfile_name3";
			pathColumn		=	"file_path3";
		}

		CurriBbsContentsDAO curriBbsContentsDao = new CurriBbsContentsDAO();
		String	sFileName	=	StringUtil.nvl(curriBbsContentsDao.getCurriBbsContentsColumnData(deleteColumn, systemCode, CurriCode, CurriYear, CurriTerm, CourseId, BbsType, SeqNo));
		String	filePath	=	StringUtil.nvl(curriBbsContentsDao.getCurriBbsContentsColumnData(pathColumn, systemCode, CurriCode, CurriYear, CurriTerm, CourseId, BbsType, SeqNo));
		int		result		=	0;
		if(!sFileName.equals("") && !filePath.equals("")) {
			FileUtil.delFile(filePath, sFileName);
			result = curriBbsContentsDao.delCurriBbsContentsFile(systemCode,CurriCode, CurriYear, CurriTerm, BbsType, SeqNo, FileNum);
		}
		return FileNum;
	}

}

package com.edutrack.bbs.action;


import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.VBN.VBN_files;
import com.edutrack.bbs.dao.BbsContentsDAO;
import com.edutrack.bbs.dao.BbsInfoDAO;
import com.edutrack.bbs.dto.BbsContentsDTO;
import com.edutrack.bbs.dto.BbsInfoDTO;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.DateSetter;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.config.dao.ConfigSubDAO;
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


public class BbsContentsAction extends StrutsDispatchAction{
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
	public ActionForward bbsContentsWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String	systemCode	=	UserBroker.getSystemCode(request);
		int 	pBbsId 		= 	Integer.parseInt(request.getParameter("pBbsId"));
		int 	pSeqNo 		= 	StringUtil.nvl(request.getParameter("pSeqNo"),0);
		String 	pSec 		= 	StringUtil.nvl(request.getParameter("pSec"));
		String	MENUNO		=	StringUtil.nvl(request.getParameter("MENUNO"));
		String	MainMenu	=	StringUtil.nvl(request.getParameter("MainMenu"));
		
		String pRegMode = "Add";

		if(pSeqNo > 0 ){
			BbsContentsDAO bbsContentsDao = new BbsContentsDAO();
			BbsContentsDTO bbsContentsDto = new BbsContentsDTO();
			bbsContentsDto = bbsContentsDao.getBbsContents(systemCode,pBbsId,pSeqNo);
			model.put("pBbsNo", String.valueOf(bbsContentsDto.getBbsNo()));
			model.put("pDepthNo", String.valueOf(bbsContentsDto.getDepthNo()));
			model.put("pOrderNo", String.valueOf(bbsContentsDto.getOrderNo()));
			model.put("pContentsType", String.valueOf(StringUtil.nvl(bbsContentsDto.getContentsType())));
			model.put("pParentNo", String.valueOf(pSeqNo));
			pRegMode = "Reply";
		}
		
		BbsInfoDAO bbsInfoDao = new BbsInfoDAO();
		RowSet infoRs = bbsInfoDao.getBbsInfo(systemCode,pBbsId);
		infoRs.next();
		String bbsName = StringUtil.nvl(infoRs.getString("bbs_name"));
		model.put("titleImg", StringUtil.nvl(infoRs.getString("title_img")));
		model.put("pRegMode", String.valueOf(pRegMode));
		model.put("pBbsId", String.valueOf(pBbsId));
		model.put("pBbsType", StringUtil.nvl(infoRs.getString("bbs_type")));
		model.put("pFileUseYn", StringUtil.nvl(infoRs.getString("file_use_yn")));
		model.put("pFileCount", String.valueOf(infoRs.getInt("file_count")));
		model.put("pEditorYn", StringUtil.nvl(infoRs.getString("editor_yn")));
		model.put("pBadWordUseYn", StringUtil.nvl(infoRs.getString("bad_word_use_yn")));
		model.put("pBadWord", StringUtil.nvl(infoRs.getString("bad_word")));
		
		//-- 도배방지 랜덤 숫자/알파벳 발행
		Random		random		=	new Random();
		String[]	randomStr	=	new String[5];
		int			randomInt	=	0;		
		for(int i=0; i< 3; i++) {
			randomInt	=	random.nextInt(9);
			randomStr[i] =	String.valueOf(randomInt);
		}
	    for (int i = 1; i <= 2; i++) {
	        char ch = (char)((Math.random() * 26) + 65);
	        randomStr[i+2]	=	String.valueOf(ch);
	    }
		model.put("RandomStr", randomStr);
		//-- 도배방지 랜덤 숫자/알파벳 발행 끝

		DateSetter ds = new DateSetter("").link(model);
		model.put("ds", ds);
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals("")) pMode = MYPAGE;
		model.put("pMode", pMode);
		model.put("pSec", pSec);
		model.put("MENUNO", MENUNO);
		model.put("MainMenu", MainMenu);
		
		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter(bbsName)).link(model);
		return forward(request, model, "/bbs/bbsContentsWrite.jsp");
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
	public ActionForward bbsContentsEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("__________________________________ bbsContentsEdit Start");
		String 	systemCode 	= 	UserBroker.getSystemCode(request);
		int 	pBbsId 		= 	Integer.parseInt(request.getParameter("pBbsId"));
		int 	pSeqNo 		= 	Integer.parseInt(request.getParameter("pSeqNo"));
		String 	pSec 		= 	StringUtil.nvl(request.getParameter("pSec"));
		String	MENUNO		=	StringUtil.nvl(request.getParameter("MENUNO"));
		String	MainMenu	=	StringUtil.nvl(request.getParameter("MainMenu"));		

		BbsInfoDAO bbsInfoDao = new BbsInfoDAO();
		RowSet infoRs = bbsInfoDao.getBbsInfo(systemCode,pBbsId);
		infoRs.next();
		String bbsName = StringUtil.nvl(infoRs.getString("bbs_name"));
		model.put("pBbsId", String.valueOf(pBbsId));
		model.put("pBbsType", StringUtil.nvl(infoRs.getString("bbs_type")));
		model.put("pFileUseYn", StringUtil.nvl(infoRs.getString("file_use_yn")));
		model.put("pFileCount", String.valueOf(infoRs.getInt("file_count")));
		model.put("pEditorYn", StringUtil.nvl(infoRs.getString("editor_yn")));
		model.put("pBadWordUseYn", StringUtil.nvl(infoRs.getString("bad_word_use_yn")));
		model.put("pBadWord", StringUtil.nvl(infoRs.getString("bad_word")));
		model.put("titleImg", StringUtil.nvl(infoRs.getString("title_img")));

		BbsContentsDAO bbsContentsDao = new BbsContentsDAO();
		BbsContentsDTO bbsContentsDto = new BbsContentsDTO();
		bbsContentsDto = bbsContentsDao.getBbsContents(systemCode,pBbsId,pSeqNo);
		model.put("contentsDto",bbsContentsDto);

		String expireDate = StringUtil.nvl(bbsContentsDto.getExpireDate());
		log.debug("+++++++++"+StringUtil.nvl(bbsContentsDto.getExpireDate()));

		if(expireDate.equals("000000"))
			expireDate="";

		DateSetter ds = new DateSetter((String)StringUtil.nvl(bbsContentsDto.getExpireDate())).link(model);
		model.put("ds", ds);

		String pMode = StringUtil.nvl(request.getParameter("pMode"));

		if(pMode.equals(""))
			pMode = MYPAGE;

		model.put("pMode", pMode);
		model.put("pSec", pSec);
		model.put("MENUNO", MENUNO);
		model.put("MainMenu", MainMenu);

		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter(bbsName)).link(model);
		log.debug("__________________________________ bbsContentsEdit End");
		return forward(request, model, "/bbs/bbsContentsEdit.jsp");
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
	public ActionForward bbsContentsShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("__________________________________ bbsContentsShow Start");
		String	systemCode	=	UserBroker.getSystemCode(request);
		String	userType	=	UserBroker.getUserType(request);
		String	userId		=	UserBroker.getUserId(request);
		int 	pBbsId 		= 	Integer.parseInt(request.getParameter("pBbsId"));
		int 	pSeqNo 		= 	Integer.parseInt(request.getParameter("pSeqNo"));
		String 	pSec 		= 	StringUtil.nvl(request.getParameter("pSec"));
		String	MENUNO		=	StringUtil.nvl(request.getParameter("MENUNO"));
		String	MainMenu	=	StringUtil.nvl(request.getParameter("MainMenu"));

		BbsInfoDAO bbsInfoDao = new BbsInfoDAO();

		RowSet infoRs = bbsInfoDao.getBbsInfo(systemCode,pBbsId);
		infoRs.next();
		String bbsName = StringUtil.nvl(infoRs.getString("bbs_name"));
		String pCommentUseYn = StringUtil.nvl(infoRs.getString("comment_use_yn"));
		String pViewThreadYn = StringUtil.nvl(infoRs.getString("view_thread_yn"));
		String pViewPrevNextYn = StringUtil.nvl(infoRs.getString("view_prev_next_yn"));
		model.put("titleImg", StringUtil.nvl(infoRs.getString("title_img")));
		model.put("pBbsId", String.valueOf(pBbsId));
		model.put("pBbsType", StringUtil.nvl(infoRs.getString("bbs_type")));
		model.put("pFileUseYn", StringUtil.nvl(infoRs.getString("file_use_yn")));
		model.put("pFileCount", String.valueOf(infoRs.getInt("file_count")));
		model.put("pLoginChkYn", StringUtil.nvl(infoRs.getString("login_chk_yn")));
		model.put("pCommentUseYn", pCommentUseYn);
		model.put("pEmoticonUseYn", StringUtil.nvl(infoRs.getString("emoticon_use_yn")));
		model.put("pViewThreadYn", pViewThreadYn);
		model.put("pViewPrevNextYn", pViewPrevNextYn);
		model.put("pNewTime", String.valueOf(infoRs.getInt("new_time")));
		model.put("pHotChk", String.valueOf(infoRs.getInt("hot_chk")));

		BbsContentsDAO bbsContentsDao = new BbsContentsDAO();
		BbsContentsDTO bbsContentsDto = new BbsContentsDTO();
		
		//-- 게시판 내용을 담는다.
		bbsContentsDto = bbsContentsDao.getBbsContents(systemCode,pBbsId,pSeqNo);
		model.put("contentsDto",bbsContentsDto);
		
		//-- 세상돌아가는이야기 게시판일 때
		if(bbsContentsDto.getBbsId() == 2) {
			ConfigSubDAO	configDao	=	new ConfigSubDAO();
			String	pUccIp		=	StringUtil.nvl(configDao.getConfigSubValue(systemCode, "310", "020"));
			String	pUccAlias	=	StringUtil.nvl(configDao.getConfigSubValue(systemCode, "310", "023"));
			String	pUccProtocol =	StringUtil.nvl(configDao.getConfigSubValue(systemCode, "310", "027"));
			
			model.put("pUccIp", pUccIp);
			model.put("pUccAlias", pUccAlias);
			model.put("pUccProtocol", pUccProtocol);
		}

		if(pViewPrevNextYn.equals("Y")){
			String pPrevSubject = "";
			String pNextSubject = "";
			int pPrevSeqNo = bbsContentsDao.getBbsContentsPrevSeqNo(systemCode,pBbsId,bbsContentsDto.getBbsNo());
			log.debug("----------- pPrevSeqNo"+pPrevSeqNo);
			if(pPrevSeqNo > 0)
				pPrevSubject = bbsContentsDao.getBbsContentsSubject(systemCode,pBbsId,pPrevSeqNo);
			int pNextSeqNo = bbsContentsDao.getBbsContentsNextSeqNo(systemCode,pBbsId,bbsContentsDto.getBbsNo());
			log.debug("----------- pNextSeqNo"+pNextSeqNo);
			if(pNextSeqNo > 0)
				pNextSubject = bbsContentsDao.getBbsContentsSubject(systemCode,pBbsId,pNextSeqNo);
			model.put("pPrevSeqNo",String.valueOf(pPrevSeqNo));
			model.put("pPrevSubject",pPrevSubject);
			model.put("pNextSeqNo",String.valueOf(pNextSeqNo));
			model.put("pNextSubject",pNextSubject);
		}
		String AddWhere = "";
		if(pViewThreadYn.equals("Y")){
			AddWhere = " bbs_no="+bbsContentsDto.getBbsNo();
			String Order = "";
			int listCnt = 0;
			listCnt = bbsContentsDao.getBbsContentsCount(systemCode,pBbsId,AddWhere);
			model.put("listCnt", String.valueOf(listCnt));
			if(listCnt>1){
				RowSet listRs = bbsContentsDao.getBbsContentsList(systemCode,pBbsId,AddWhere,Order);
				model.put("listRs", listRs);
			}
		}
		//-- 삭제시 하위글 있는지 체크하기위해서 아래 글 갯수를 가져온다.
		int pChildCnt = 0;
		AddWhere = "parent_no="+pSeqNo;
		pChildCnt = bbsContentsDao.getBbsContentsCount(systemCode,pBbsId,AddWhere);
		model.put("pChildCnt", String.valueOf(pChildCnt));

		//-- 조회수 증가 시키기
		int retVal = 0;
		if(!userType.equals("M") && (("").equals(StringUtil.nvl(bbsContentsDto.getRegId())) || !userId.equals(StringUtil.nvl(bbsContentsDto.getRegId()))  )) {
			retVal = bbsContentsDao.hitUpBbsContents(systemCode,pBbsId,pSeqNo);
		}

		model.put("curPage", StringUtil.nvl(request.getParameter("curPage")));
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals("")) pMode = MYPAGE;
		model.put("pMode", pMode);
		model.put("pSec", pSec);
		model.put("MENUNO", MENUNO);
		model.put("MainMenu", MainMenu);

		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter(bbsName)).link(model);
		log.debug("__________________________________ bbsContentsShow End");
		return forward(request, model, "/bbs/bbsContentsShow.jsp");
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
	public ActionForward bbsContentsDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("===========================bbsContentsDelete start");
		String 	systemCode 	= 	UserBroker.getSystemCode(request);
		int 	pBbsId 		= 	Integer.parseInt(request.getParameter("pBbsId"));
		int 	pSeqNo 		= 	Integer.parseInt(request.getParameter("pSeqNo"));
		String 	pContents 	= 	StringUtil.nvl(request.getParameter("pContents"));
		String 	pSec 		= 	StringUtil.nvl(request.getParameter("pSec"));
		String	MENUNO		=	StringUtil.nvl(request.getParameter("MENUNO"));
		String	MainMenu	=	StringUtil.nvl(request.getParameter("MainMenu"));
		String pMode = StringUtil.nvl(request.getParameter("pMode"), "MYPAGE");

		BbsInfoDAO bbsInfoDao = new BbsInfoDAO();
		BbsContentsDAO bbsContentsDao = new BbsContentsDAO();
		RowSet infoRs = bbsInfoDao.getBbsInfo(systemCode,pBbsId);
		infoRs.next();
		String 	bbsName 	= 	StringUtil.nvl(infoRs.getString("bbs_name"));
		model.put("titleImg", StringUtil.nvl(infoRs.getString("title_img")));
		BbsContentsDTO bbsContentsDto = new BbsContentsDTO();
		bbsContentsDto = bbsContentsDao.getBbsContents(systemCode,pBbsId,pSeqNo);
		String rfile_name = "";
		String sfile_name = "";
		String filePath = "";
		String msg = "삭제하였습니다.";
		String returnUrl = "";

		//-- 일반게시판
		returnUrl = "/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId="+pBbsId+"&MENUNO="+MENUNO+"&MainMenu="+MainMenu+"&pSec="+pSec+"&pMode="+pMode;

		int retVal = 0;
		retVal = bbsContentsDao.delBbsContents(systemCode,pBbsId,pSeqNo);
		log.debug("+++++++++++++++++++ retVal = "+retVal);

		if(retVal > 0){ //-- 게시글 삭제 성공한 경우 첨부 이미지 있으면 삭제
			for(int i=1;i<=3;i++){
				if(i == 1){
					rfile_name = StringUtil.nvl(bbsContentsDto.getRfileName1());
					sfile_name = StringUtil.nvl(bbsContentsDto.getSfileName1());
					filePath = StringUtil.nvl(bbsContentsDto.getFilePath1());
				}else if(i ==2){
					rfile_name = StringUtil.nvl(bbsContentsDto.getRfileName2());
					sfile_name = StringUtil.nvl(bbsContentsDto.getSfileName2());
					filePath = StringUtil.nvl(bbsContentsDto.getFilePath2());
				}else if(i == 3){
					rfile_name = StringUtil.nvl(bbsContentsDto.getRfileName3());
					sfile_name = StringUtil.nvl(bbsContentsDto.getSfileName3());
					filePath = StringUtil.nvl(bbsContentsDto.getFilePath3());
				}
				if(!rfile_name.equals("") && !sfile_name.equals("") && !filePath.equals("")){
					FileUtil.delFile(filePath, sfile_name);
					log.debug(" 첨부파일을 삭제하였습니다."+filePath+sfile_name);
				}
			}
			String 	RegMonth 	= 	StringUtil.nvl(bbsContentsDto.getRegDate()).substring(0,6);
			String 	FilePath 	= 	FileUtil.FILE_DIR+systemCode+"/bbs/"+pBbsId+"/"+RegMonth+"/";//-- 게시판아이디 + 년월
			/** 웹에디터 셋팅 추가 Start**/
			String ServiceUrl = CommonUtil.SERVERURL;
			String WeasFilePath = FileUtil.UPLOAD_PATH+FilePath;
			String WeasFileUrl =  ServiceUrl+"/data/"+FilePath;
			log.debug("WeasFilePath = "+WeasFilePath);
			VBN_files v_objFile = null;
			v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
			//--************************* contents 삭제시 반드시 해 줄
			pContents = StringUtil.ReplaceAll(StringUtil.nvl(bbsContentsDto.getContents()),"&quot;","\"");
			v_objFile.VBN_deleteFiles(pContents);
			//-- 글 순서 정렬
			BbsContentsDTO bbsContentsDto2 = new BbsContentsDTO();
			bbsContentsDto2.setSystemCode(systemCode);
			bbsContentsDto2.setBbsId(pBbsId);
			bbsContentsDto2.setBbsNo(bbsContentsDto.getBbsNo());
			bbsContentsDto2.setOrderNo(bbsContentsDto.getOrderNo());
			boolean bVal = bbsContentsDao.replyUpdateBbsContents(bbsContentsDto2,"Del");

		} else {
			msg = "삭제 실패 하였습니다.";
			returnUrl = "/BbsContents.cmd?cmd=bbsContentsShow&pBbsId="+pBbsId+"&MENUNO="+MENUNO+"&MainMenu="+MainMenu+"&pSec="+pSec+"&pSeqNo="+pSeqNo+"&pMode="+pMode;
		}
		model.put("pMode", pMode);
		model.put("pSec", pSec);

		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter(bbsName)).link(model);
		log.debug("===========================bbsContentsDelete end");
		return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
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
	public ActionForward bbsContentsFileDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));
		String pFileNo = request.getParameter("pFileNo");
		String pSec = StringUtil.nvl(request.getParameter("pSec"));

		BbsInfoDAO bbsInfoDao = new BbsInfoDAO();
		BbsContentsDAO bbsContentsDao = new BbsContentsDAO();
		RowSet infoRs = bbsInfoDao.getBbsInfo(systemCode,pBbsId);
		infoRs.next();
		String bbsName = StringUtil.nvl(infoRs.getString("bbs_name"));
		model.put("titleImg", StringUtil.nvl(infoRs.getString("title_img")));
		BbsContentsDTO bbsContentsDto = new BbsContentsDTO();
		bbsContentsDto = bbsContentsDao.getBbsContents(systemCode,pBbsId,pSeqNo);
		String rfile_name = "";
		String sfile_name = "";
		String filePath = "";

		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals("")) pMode = MYPAGE;
		model.put("pMode", pMode);
		String msg = "삭제하였습니다.";

		String returnUrl = "/BbsContents.cmd?cmd=bbsContentsEdit&pMode="+pMode+"&pBbsId="+pBbsId+"&pSec="+pSec+"&pSeqNo="+pSeqNo;

		int retVal = 0;
		retVal = bbsContentsDao.delBbsContentsFile(systemCode,pBbsId,pSeqNo,pFileNo);

		if(pFileNo.equals("1")){
			rfile_name = StringUtil.nvl(bbsContentsDto.getRfileName1());
			sfile_name = StringUtil.nvl(bbsContentsDto.getSfileName1());
			filePath = StringUtil.nvl(bbsContentsDto.getFilePath1());
		}else if(pFileNo.equals("2")){
			rfile_name = StringUtil.nvl(bbsContentsDto.getRfileName2());
			sfile_name = StringUtil.nvl(bbsContentsDto.getSfileName2());
			filePath = StringUtil.nvl(bbsContentsDto.getFilePath2());
		}else if(pFileNo.equals("3")){
			rfile_name = StringUtil.nvl(bbsContentsDto.getRfileName3());
			sfile_name = StringUtil.nvl(bbsContentsDto.getSfileName3());
			filePath = StringUtil.nvl(bbsContentsDto.getFilePath3());
		}

		if(!rfile_name.equals("") && !sfile_name.equals("") && !filePath.equals("")){
			FileUtil.delFile(filePath, sfile_name);
			log.debug(" 첨부파일을 삭제하였습니다."+filePath+ "  :::  " + sfile_name);
		}

		if(retVal <=0){
			msg = "첨부파일 삭제 실패 하였습니다.";
		}

		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter(bbsName)).link(model);

		return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
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
	public ActionForward bbsContentsRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=============================================================bbsContentsRegist start");
		String 	systemCode 	= 	UserBroker.getSystemCode(request);
		String 	regId 		= 	UserBroker.getUserId(request);
		int 	pBbsId 		= 	Integer.parseInt(StringUtil.nvl(request.getParameter("pBbsId"),"0"));
		int 	pSeqNo 		= 	Integer.parseInt(StringUtil.nvl(request.getParameter("pSeqNo"),"1"));
		String 	pSec 		= 	StringUtil.nvl(request.getParameter("pSec"));

		BbsInfoDAO bbsInfoDao = new BbsInfoDAO();
		BbsInfoDTO bbsInfoDto = new BbsInfoDTO();
		RowSet infoRs = bbsInfoDao.getBbsInfo(systemCode,pBbsId);
		model.put("infoRs", infoRs);
		infoRs.next();
		String 	bbsName 	= 	StringUtil.nvl(infoRs.getString("bbs_name"));
		model.put("titleImg", StringUtil.nvl(infoRs.getString("title_img")));
		BbsContentsDAO bbsContentsDao = new BbsContentsDAO();
		BbsContentsDTO bbsContentsDto = new BbsContentsDTO();

		MultipartRequest multipartRequest = null;
		int retVal = 0;
		String objName = "";
		String rFileName = "";
		String sFileName = "";
		String filePath = "";
		String fileSize = "";

		String 	regMode 	= 	StringUtil.nvl(request.getParameter("pRegMode"));
		log.debug("regMode ========================>"+regMode);
		//int maxId = bbsInfoDao.getMaxBbsId(systemCode);

		if(regMode.equals("Add") || regMode.equals("Reply") )// 입력모드
		{
			pSeqNo = bbsContentsDao.getMaxSeqNo(systemCode,pBbsId);
		}
		String RegMonth = CommonUtil.getCurrentDate().substring(0,6);

		if(regMode.equals("Edit")){//-- 수정모드시는 현재 월이 아닌 등록된 글의 년월을 가져온다.
			RegMonth = StringUtil.nvl(request.getParameter("pRegDate")).substring(0,6);
		}

		String FilePath = FileUtil.FILE_DIR+systemCode+"/bbs/"+pBbsId+"/"+RegMonth+"/";//-- 게시판아이디 + 년월
		log.debug("FilePath ==========================>"+FilePath);
		if(systemCode.equals(""))
			systemCode = StringUtil.nvl(request.getParameter("pSystemCode"));;
		if(regId.equals(""))
			regId = StringUtil.nvl(request.getParameter("pUserId"));

		//  파일 업로드 수행
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

		bbsContentsDto.setRfileName1(pOldrFileName[1]);		bbsContentsDto.setSfileName1(pOldsFileName[1]);
		bbsContentsDto.setFilePath1(pOldFilePath[1]);		bbsContentsDto.setFileSize1(pOldFileSize[1]);
		bbsContentsDto.setRfileName2(pOldrFileName[2]);		bbsContentsDto.setSfileName2(pOldsFileName[2]);
		bbsContentsDto.setFilePath2(pOldFileSize[2]);		bbsContentsDto.setFileSize2(pOldFileSize[2]);
		bbsContentsDto.setRfileName3(pOldrFileName[3]);		bbsContentsDto.setSfileName3(pOldsFileName[3]);
		bbsContentsDto.setFilePath3(pOldFileSize[3]);		bbsContentsDto.setFileSize3(pOldFileSize[3]);

		String pSubject = StringUtil.nvl(multipartRequest.getParameter("pSubject"));
		String MENUNO = StringUtil.nvl(multipartRequest.getParameter("MENUNO"));
		String MainMenu = StringUtil.nvl(multipartRequest.getParameter("MainMenu"));
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
					sFileName = file.getUploadName();
					fileSize = String.valueOf(file.getSize());
					filePath = uploadEntity.getUploadPath();
					//-- 세상돌아가는이야기의 VOD 서버에 파일올리기
					if(pBbsId == 2 && ( CommonUtil.getFileType(sFileName).equals("wmv") || CommonUtil.getFileType(sFileName).equals("asf") || CommonUtil.getFileType(sFileName).equals("wma")) ) {
						CommonUtil.VodFTPUpload(FilePath, sFileName);
					}
					
				}
				if(objName.indexOf("pFile[")>=0){
					int idx = Integer.parseInt(String.valueOf(objName.charAt(6)));
					log.debug("++++++++++++++++ idx = "+idx);
					if(idx == 1){
						bbsContentsDto.setRfileName1(rFileName);	bbsContentsDto.setSfileName1(sFileName);
						bbsContentsDto.setFilePath1(filePath);		bbsContentsDto.setFileSize1(fileSize);
					}else if(idx == 2){
						bbsContentsDto.setRfileName2(rFileName);	bbsContentsDto.setSfileName2(sFileName);
						bbsContentsDto.setFilePath2(filePath);		bbsContentsDto.setFileSize2(fileSize);
					}else if(idx == 3){
						bbsContentsDto.setRfileName3(rFileName);	bbsContentsDto.setSfileName3(sFileName);
						bbsContentsDto.setFilePath3(filePath);		bbsContentsDto.setFileSize3(fileSize);
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
		String WeasFileUrl =  "./data/"+FilePath;
		VBN_files v_objFile = null;
		java.io.File dir = new java.io.File(WeasFilePath);
		if (!dir.exists())	dir.mkdirs();
		v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
		pContents = v_objFile.VBN_uploadMultiFiles(pContents, multipartRequest);
		/** 웹에디터 셋팅 추가 End**/

		int pBbsNo = StringUtil.nvl(multipartRequest.getParameter("pBbsNo"),pSeqNo);
		int pDepthNo = StringUtil.nvl(multipartRequest.getParameter("pDepthNo"),0);
		int pOrderNo = StringUtil.nvl(multipartRequest.getParameter("pOrderNo"),0);
		int pParentNo= StringUtil.nvl(multipartRequest.getParameter("pParentNo"),0);
		String pContentsType = StringUtil.nvl(multipartRequest.getParameter("pContentsType"));
		
		if(pContentsType.equals("")) pContentsType = "S";

		String pYear = StringUtil.nvl(multipartRequest.getParameter("pYear1"));
		String pMonth = StringUtil.nvl(multipartRequest.getParameter("pMonth1"));
		String pDay = StringUtil.nvl(multipartRequest.getParameter("pDay1"));
		String pDate1 = StringUtil.nvl(multipartRequest.getParameter("pDate1"));
		String pExpireDate =pYear+pMonth+pDay+"000000";
		if(pYear.equals("")) pExpireDate="";

		String pTarget = "";
		String arrTarget[] = multipartRequest.getParameterValues("pTarget");
		for (int i = 0; i < arrTarget.length; i++) {
				pTarget = pTarget + arrTarget[i];
		}

		if(regMode.equals("Add")){
			pBbsNo = pSeqNo;
			pDepthNo = 0;
			pOrderNo = 0;
			pParentNo = 0;
		}
		if(regMode.equals("Reply")){
			//pBbsNo = pBbsNo;
			pDepthNo = pDepthNo+1;
			pOrderNo = pOrderNo+1;
			//pParentNo = pParentNo;
		}
		log.debug("+++++++++++++++++++++++++++++++++   chk Point");
		String userName = UserBroker.getUserName(request);

		if(userName.equals(""))
			userName = StringUtil.nvl(multipartRequest.getParameter("pRegName"));
		String regPasswd = StringUtil.nvl(multipartRequest.getParameter("pRegPasswd"));
		String regEmail = StringUtil.nvl(multipartRequest.getParameter("pRegEmail"));
		bbsContentsDto.setSystemCode(systemCode);
		bbsContentsDto.setBbsId(pBbsId);
		bbsContentsDto.setSeqNo(pSeqNo);
		bbsContentsDto.setBbsNo(pBbsNo);
		bbsContentsDto.setDepthNo(pDepthNo);
		bbsContentsDto.setOrderNo(pOrderNo);
		bbsContentsDto.setParentNo(pParentNo);
		bbsContentsDto.setEditorType(pEditorType);
		bbsContentsDto.setContentsType(pContentsType);
		bbsContentsDto.setSubject(pSubject);
		bbsContentsDto.setKeyword(pKeyword);
		bbsContentsDto.setContents(pContents);
		bbsContentsDto.setExpireDate(pExpireDate);
		bbsContentsDto.setTarget(pTarget);
		bbsContentsDto.setRegId(regId);
		bbsContentsDto.setRegName(userName);
		bbsContentsDto.setRegEmail(regEmail);
		bbsContentsDto.setRegPasswd(regPasswd);
		bbsContentsDto.setModId(regId);
		String msg = "";
		String pMode = StringUtil.nvl(multipartRequest.getParameter("pMode"));
		if(pMode.equals("")) pMode = MYPAGE;


		String returnUrl = "";
			//-- 일반게시판
			returnUrl = "/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId="+pBbsId+"&MENUNO="+MENUNO+"&MainMenu="+MainMenu+"&pMode="+pMode;

		//-- 입력모드
		if (regMode.equals("Add")) {
			retVal = bbsContentsDao.addBbsContents(bbsContentsDto);
			msg = "등록완료";

		//-- 수정모드
		} else if (regMode.equals("Edit")){
			retVal = bbsContentsDao.editBbsContents(bbsContentsDto);
			msg = "수정완료";

			if(retVal <= 0){
				returnUrl = "/BbsContents.cmd?cmd=bbsContentsEdit&pBbsId="+pBbsId+"&MENUNO="+MENUNO+"&MainMenu="+MainMenu+"&pSec="+pSec+"&pSeqNo="+pSeqNo+"&pMode="+pMode;
				msg = "수정오류 다시 진행해 주세요";
			}
		} else if (regMode.equals("Reply")){
			boolean bVal = bbsContentsDao.replyUpdateBbsContents(bbsContentsDto,"Ins");
			if(bVal)	retVal  = 1;
			else		retVal  = 0;
			if (retVal > 0){
				retVal = bbsContentsDao.addBbsContents(bbsContentsDto);
				msg = "등록완료";
			}

			if(retVal <= 0){
				returnUrl = "/BbsContents.cmd?cmd=bbsContentsWrite&pBbsId="+pBbsId+"&MENUNO="+MENUNO+"&MainMenu="+MainMenu+"&pSec="+pSec+"&pSeqNo="+pSeqNo+"&pMode="+pMode;
				msg = "등록오류 다시 진행해 주세요";
			}
		}
		
		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter(bbsName)).link(model);
		log.debug("=============================================================bbsContentsRegist end");
		//return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
		return alertAndExit(systemCode, model, msg, returnUrl, pMode);
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
	public ActionForward bbsContentsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("============================= bbsContentsList");
		String systemCode = UserBroker.getSystemCode(request);
		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));

		int totCnt = 0;
		BbsInfoDAO bbsInfoDao = new BbsInfoDAO();
		BbsContentsDAO bbsContentsDao = new BbsContentsDAO();
		RowSet infoRs = bbsInfoDao.getBbsInfo(systemCode,pBbsId);
		model.put("infoRs", infoRs);
		infoRs.next();
		String bbsName = StringUtil.nvl(infoRs.getString("bbs_name"));
		model.put("titleImg", StringUtil.nvl(infoRs.getString("title_img")));
		String pWhere = "";
		totCnt = bbsContentsDao.getBbsContentsCount(systemCode,pBbsId,pWhere);
		String AddWhere = "";
		String Order = "";
		RowSet contentsRs = bbsContentsDao.getBbsContentsList(systemCode,pBbsId,AddWhere,Order);
		model.put("totCnt", String.valueOf(totCnt));
		model.put("contentsRs", contentsRs);
		model.put("pBbsId", String.valueOf(pBbsId));
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals("")) pMode = MYPAGE;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter(bbsName)).link(model);
		log.debug("============================= bbsContentsList End");
        return forward(request, model, "/bbs/bbsContentsList.jsp");
	}


	/**
	 * 게시글 리스트를 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward bbsContentsPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("_________________________ bbsContentsPagingList start");
		String	systemCode	= 	UserBroker.getSystemCode(request);
		String	pSec		= 	StringUtil.nvl(request.getParameter("pSec"));
		int 	pBbsId		= 	Integer.parseInt(request.getParameter("pBbsId"));
		int		curPage		=	StringUtil.nvl(request.getParameter("curPage"), 1);
		String	MENUNO		=	StringUtil.nvl(request.getParameter("MENUNO"));
		String	MainMenu	=	StringUtil.nvl(request.getParameter("MainMenu"));
		String	pOrder		=	"";		//	ORDER BY

		model.put("pBbsId", String.valueOf(pBbsId));
		model.put("curPage", String.valueOf(curPage));

		BbsInfoDAO		bbsInfoDao		=	new BbsInfoDAO();
		BbsContentsDAO	bbsContentsDao	=	new BbsContentsDAO();

		RowSet infoRs = bbsInfoDao.getBbsInfo(systemCode, pBbsId);
		infoRs.next();
		String bbsName	= StringUtil.nvl(infoRs.getString("bbs_name"));
		String pBbsType = StringUtil.nvl(infoRs.getString("bbs_type"));
		model.put("pBbsType", 		pBbsType);
		model.put("titleImg", 		StringUtil.nvl(infoRs.getString("title_img")));
		model.put("textHeader", 	StringUtil.nvl(infoRs.getString("bbs_comment")));
		model.put("pNewTime", 		String.valueOf(infoRs.getInt("new_time")));
		model.put("pHotChk", 		String.valueOf(infoRs.getInt("hot_chk")));
		model.put("pSec", pSec);

		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals(""))
			pMode = MYPAGE;
		model.put("pMode", pMode);
		model.put("MENUNO", MENUNO);
		model.put("MainMenu", MainMenu);

		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter(bbsName)).link(model);
		log.debug("_________________________ bbsContentsPagingList end");
		return forward(request, model, "/bbs/bbsContentsPagingList.jsp");
	}

	/**
	 * AJAXLIST
	 * @param curPage
	 * @param pBbsId
	 * @param pSearchKey
	 * @param pKeyWord
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO bbsContentsListAuto(int curPage, int pBbsId, String pSearchKey, String pKeyWord, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode	= UserBroker.getSystemCode(request);
		String userType		= UserBroker.getUserType(request);

		if(userType.equals(""))
			userType="G";

		pSearchKey	=	StringUtil.nvl(pSearchKey);
		pKeyWord	=	AjaxUtil.ajaxDecoding(StringUtil.nvl(pKeyWord));

		curPage = (curPage == 0) ? 1 : curPage;

		BbsInfoDAO bbsInfoDao			= new BbsInfoDAO();

		RowSet infoRs = bbsInfoDao.getBbsInfo(systemCode, pBbsId);
		infoRs.next();

		String	pBbsType	=	StringUtil.nvl(infoRs.getString("bbs_type"));
		int dispLine = infoRs.getInt("disp_line");
		int dispPage = infoRs.getInt("disp_page");

		String pExpireDate = "";
		if(pBbsType.equals("notice"))
			pExpireDate = CommonUtil.getCurrentDate();

		String pOrder = "";		//	ORDER BY

		// 리턴 데이타 세팅
		BbsContentsDAO bbsContentsDao	= new BbsContentsDAO();
		AjaxListDTO		ajaxListDto	=	new AjaxListDTO();
		ArrayList		data1List	=	new ArrayList();
		BbsContentsDTO	noticeDto	=	null;

		//--	공지사항
		if(curPage <= 1){
			String AddWhere = " a.contents_type ='N' ";
			if(!pKeyWord.equals("")) AddWhere += " AND a."+pSearchKey+" like '%"+pKeyWord+"%' ";

			RowSet contentsRs = bbsContentsDao.getBbsContentsList(systemCode,pBbsId,AddWhere,pOrder);
			while(contentsRs.next()) {
				noticeDto	=	new BbsContentsDTO();
				noticeDto.setBbsId(contentsRs.getInt("bbs_id"));
				noticeDto.setSeqNo(contentsRs.getInt("seq_no"));
				noticeDto.setBbsNo(contentsRs.getInt("bbs_no"));
				noticeDto.setDepthNo(contentsRs.getInt("depth_no"));
				noticeDto.setOrderNo(contentsRs.getInt("order_no"));
				noticeDto.setParentNo(contentsRs.getInt("parent_no"));

				noticeDto.setEditorType(StringUtil.nvl(contentsRs.getString("editor_type")));
				noticeDto.setContentsType(StringUtil.nvl(contentsRs.getString("contents_type")));
				noticeDto.setSubject(StringUtil.nvl(contentsRs.getString("subject")));
				noticeDto.setHitNo(contentsRs.getInt("hit_no"));
				noticeDto.setExpireDate(StringUtil.nvl(contentsRs.getString("expire_date")));
				noticeDto.setTarget(StringUtil.nvl(contentsRs.getString("target")));
				noticeDto.setRegId(StringUtil.nvl(contentsRs.getString("reg_id")));
				noticeDto.setRegName(StringUtil.nvl(contentsRs.getString("reg_name")));
				noticeDto.setRegDate(StringUtil.nvl(contentsRs.getString("reg_date")));
				noticeDto.setCommCnt(contentsRs.getInt("comm_cnt"));

				data1List.add(noticeDto);
			}
			contentsRs.close();
			ajaxListDto.setData1List(data1List);
		}

		// 데이타를 담는다.
		ListDTO listObj = null;
		listObj = bbsContentsDao.getBbsContentsPagingList(curPage, dispLine, dispPage, systemCode, pBbsId, userType, pExpireDate, pSearchKey, pKeyWord, pOrder);
		//--	일반 게시글
		ArrayList		dataList	=	new ArrayList();
		BbsContentsDTO	bbsDto		=	null;
		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				bbsDto	=	new BbsContentsDTO();
				bbsDto.setBbsId(rs.getInt("bbs_id"));
				bbsDto.setSeqNo(rs.getInt("seq_no"));
				bbsDto.setBbsNo(rs.getInt("bbs_no"));
				bbsDto.setDepthNo(rs.getInt("depth_no"));
				bbsDto.setOrderNo(rs.getInt("order_no"));
				bbsDto.setParentNo(rs.getInt("parent_no"));
				bbsDto.setEditorType(StringUtil.nvl(rs.getString("editor_type")));
				bbsDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
				bbsDto.setSubject(StringUtil.nvl(rs.getString("subject")));
				bbsDto.setHitNo(rs.getInt("hit_no"));
				bbsDto.setExpireDate(StringUtil.nvl(rs.getString("expire_date")));
				bbsDto.setTarget(StringUtil.nvl(rs.getString("target")));
				bbsDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				bbsDto.setRegName(StringUtil.nvl(rs.getString("reg_name")));
				bbsDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				bbsDto.setCommCnt(rs.getInt("comm_cnt"));
				bbsDto.setDispLine(dispLine);

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
	 * 로그인 이전의 글에 대한 패스워드 확인
	 * @param bbsId
	 * @param seqNo
	 * @param passwd
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int confirmContentsPasswd(int bbsId, int seqNo, String passwd, HttpServletRequest request)  throws Exception{		
		String 	systemCode	=	UserBroker.getSystemCode(request);
		int		retVal		=	0;
		
		BbsContentsDAO	bbsDao	=	new BbsContentsDAO();
		retVal	=	bbsDao.getPasswdCnt(systemCode, bbsId, seqNo, passwd);
		
		return retVal;
	}

}

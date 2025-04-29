package com.edutrack.community.action;


import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.edutrack.VBN.VBN_files;
import com.edutrack.bbs.dto.BbsContentsDTO;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.DateSetter;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CommMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.community.dao.CommBbsCommentDAO;
import com.edutrack.community.dao.CommBbsInfoDAO;
import com.edutrack.community.dao.CommCategoryDAO;
import com.edutrack.community.dao.CommInfoDAO;
import com.edutrack.community.dao.CommMembersDAO;
import com.edutrack.community.dto.CommBbsContentsDTO;
import com.edutrack.community.dto.CommBbsInfoDTO;
import com.edutrack.community.dto.CommInfoDTO;
import com.edutrack.community.dto.CommMembersDTO;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;


public class CommunityAction extends StrutsDispatchAction {

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
	public ActionForward bbsContentsDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		log.debug("===========================bbsContentsDelete start");
		String systemCode = UserBroker.getSystemCode(request);
		int pCommId = Integer.parseInt(request.getParameter("pCommId"));
		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));
		String pContents = StringUtil.nvl(request.getParameter("pContents"));

		CommInfoDAO commInfoDao = new CommInfoDAO();
		CommBbsInfoDAO commbbsInfoDao = new CommBbsInfoDAO();

		RowSet infoRs = commInfoDao.getCommBbsInfo(systemCode, pBbsId);
		infoRs.next();
		String bbsName = infoRs.getString("bbs_name");

		CommBbsContentsDTO contentsDto = commbbsInfoDao.getCommPrideContents(systemCode, pBbsId, pSeqNo);
		String rfile_name = "";
		String sfile_name = "";
		String filePath = "";

		String msg = "삭제하였습니다.";
		String returnUrl = "/Community.cmd?cmd=commPridebbsPagingList&pBbsId=" + pBbsId;

		int retVal = 0;
		retVal = commbbsInfoDao.delBbsContents(systemCode, pCommId, pBbsId, pSeqNo);
		log.debug("+++++++++++++++++++ retVal = " + retVal);
		if (retVal > 0) { //-- 게시글 삭제 성공한 경우 첨부 이미지 있으면 삭제
			for (int i = 1; i <= 3; i++) {
				if (i == 1) {
					rfile_name = contentsDto.getRfileName1();
					sfile_name = contentsDto.getSfileName1();
					filePath = contentsDto.getFilePath1();
				} else if (i == 2) {
					rfile_name = contentsDto.getRfileName2();
					sfile_name = contentsDto.getSfileName2();
					filePath = contentsDto.getFilePath2();
				} else if (i == 3) {
					rfile_name = contentsDto.getRfileName3();
					sfile_name = contentsDto.getSfileName3();
					filePath = contentsDto.getFilePath3();
				}
				if (!rfile_name.equals("") && !sfile_name.equals("") && !filePath.equals("")) {
					FileUtil.delFile(filePath, sfile_name);
					log.debug(" 첨부파일을 삭제하였습니다." + filePath + sfile_name);
				}
			}
			String RegMonth = contentsDto.getRegDate().substring(0, 6);
			String FilePath = FileUtil.FILE_DIR + systemCode + "/commPride/" + pBbsId + "/" + RegMonth + "/";//-- 게시판아이디 + 년월
			log.debug("FilePath ==========================>" + FilePath);
			/** 웹에디터 셋팅 추가 Start**/
			String ServiceUrl = CommonUtil.SERVERURL;
			String WeasFilePath = FileUtil.UPLOAD_PATH + FilePath;
			String WeasFileUrl = ServiceUrl + "/data/" + FilePath;
			log.debug("WeasFilePath = " + WeasFilePath);
			VBN_files v_objFile = null;
			v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
			log.debug("contents ==========================>" + pContents);
			//v_objFile.VBN_deleteFiles(pContents);
			//--************************* contents 삭제시 반드시 해 줄
			pContents = StringUtil.ReplaceAll(contentsDto.getContents(), "&quot;", "\"");
			v_objFile.VBN_deleteFiles(pContents);
			//-- 글 순서 정렬

			CommBbsContentsDTO commbbsInfoDto = new CommBbsContentsDTO();

			commbbsInfoDto.setSystemCode(systemCode);
			commbbsInfoDto.setBbsId(pBbsId);
			commbbsInfoDto.setBbsNo(contentsDto.getBbsNo());
			commbbsInfoDto.setOrderNo(contentsDto.getOrderNo());
			boolean bVal = commbbsInfoDao.replyUpdateBbsContents(commbbsInfoDto, "Del");
		} else {
			msg = "삭제 실패 하였습니다.";
			returnUrl = "/Community.cmd?cmd=commPrideContentsShow&pCommId=" + pCommId + "pBbsId=" + pBbsId + "&pSeqNo=" + pSeqNo;
		}
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if (pMode.equals("")) pMode = COMMUNITY;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,"동아리자랑").link(model);
		log.debug("===========================bbsContentsDelete end");
		return notifyAndExit(systemCode, model, msg, returnUrl, COMMUNITY);
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
	public ActionForward bbsContentsEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		log.debug("__________________________________ bbsContentsEdit Start");

		String systemCode = UserBroker.getSystemCode(request);
		int pCommId = Integer.parseInt((String)request.getParameter("pCommId"));
		int pBbsId = Integer.parseInt((String)request.getParameter("pBbsId"));
		int pSeqNo = Integer.parseInt((String)request.getParameter("pSeqNo"));

		CommInfoDAO commInfoDao = new CommInfoDAO();

		RowSet infoRs = commInfoDao.getCommBbsInfo(systemCode, pBbsId, String.valueOf(pCommId));
		infoRs.next();
		String bbsName = infoRs.getString("bbs_name");
		model.put("pCommId", String.valueOf(pCommId));
		model.put("pBbsId", String.valueOf(pBbsId));
		model.put("pBbsType", infoRs.getString("bbs_type"));
		model.put("pFileUseYn", infoRs.getString("file_use_yn"));
		model.put("pEditorYn", infoRs.getString("editor_yn"));

		CommBbsInfoDAO commbbsInfoDao = new CommBbsInfoDAO();

		CommBbsContentsDTO contentsDto = commbbsInfoDao.getCommPrideContents(systemCode, pBbsId, pSeqNo);
		model.put("contentsDto", contentsDto);
		CommBbsContentsDTO contentsDto2 = commbbsInfoDao.getCommPrideContents(systemCode, pBbsId, pSeqNo);

		String expireDate = StringUtil.nvl(contentsDto2.getExpireDate());
		log.debug("+++++++++" + contentsDto2.getExpireDate());
		if (expireDate.equals("000000")) expireDate = "";
		DateSetter ds = new DateSetter(StringUtil.nvl(contentsDto2.getExpireDate())).link(model);
		model.put("ds", ds);
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if (pMode.equals("")) pMode = COMMUNITY;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,"동아리자랑").link(model);
		log.debug("__________________________________ bbsContentsEdit End");
		return forward(request, model, "/community/commPrideEdit.jsp");
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
	public ActionForward bbsContentsFileDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		int pCommId = Integer.parseInt(request.getParameter("pCommId"));
		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));
		String pFileNo = request.getParameter("pFileNo");

		CommInfoDAO commInfoDao = new CommInfoDAO();

		CommBbsInfoDAO commbbsInfoDao = new CommBbsInfoDAO();

		RowSet infoRs = commInfoDao.getCommBbsInfo(systemCode, pBbsId);
		infoRs.next();
		String bbsName = infoRs.getString("bbs_name");

		CommBbsContentsDTO contentsDto = commbbsInfoDao.getCommPrideContents(systemCode, pBbsId, pSeqNo);

		String rfile_name = "";
		String sfile_name = "";
		String filePath = "";

		String msg = "삭제하였습니다.";
		String returnUrl = "";

		if (pCommId > 1)
//			returnUrl = "/Community.cmd?cmd=comSubNoticeContentsEdit&pCommId=" + pCommId + "&pBbsId=" + pBbsId + "&pSeqNo=" + pSeqNo;
			returnUrl = "/Community.cmd?cmd=commSubBoardContentsEdit&pCommId=" + pCommId + "&pBbsId=" + pBbsId + "&pSeqNo=" + pSeqNo;
		else
			returnUrl = "/Community.cmd?cmd=bbsContentsEdit&pCommId=" + pCommId + "&pBbsId=" + pBbsId + "&pSeqNo=" + pSeqNo;

		int retVal = 0;
		retVal = commbbsInfoDao.delBbsContentsFile(systemCode, pCommId, pBbsId, pSeqNo, pFileNo);

		if (pFileNo.equals("1")) {
			rfile_name = contentsDto.getRfileName1();
			sfile_name = contentsDto.getSfileName1();
			filePath = contentsDto.getFilePath1();
		} else if (pFileNo.equals("2")) {
			rfile_name = contentsDto.getRfileName2();
			sfile_name = contentsDto.getSfileName2();
			filePath = contentsDto.getFilePath2();
		} else if (pFileNo.equals("3")) {
			rfile_name = contentsDto.getRfileName3();
			sfile_name = contentsDto.getSfileName3();
			filePath = contentsDto.getFilePath3();
		}

		if (!rfile_name.equals("") && !sfile_name.equals("") && !filePath.equals("")) {
			FileUtil.delFile(filePath, sfile_name);
			log.debug(" 첨부파일을 삭제하였습니다." + filePath + sfile_name);
		}

		if (retVal <= 0) {
			msg = "첨부파일 삭제 실패 하였습니다.";
		}

		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if (pMode.equals("")) pMode = COMMUNITY;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,"동아리자랑").link(model);
		return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
	}

	/**
	 * 사용자를 등록해 준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commBbsInfoRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception {
		String pGubun = StringUtil.nvl(request.getParameter("pGubun"), "write");
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		CommBbsInfoDTO bbsInfoDto = new CommBbsInfoDTO();
		bbsInfoDto.setSystemCode(systemCode);
		CommMemDTO memDto = UserBroker.getCommInfo(request);
		bbsInfoDto.setCommId(StringUtil.nvl(memDto.commId, 0));

		CommBbsInfoDAO bbsInfoDAO = new CommBbsInfoDAO();
		CommBbsHelper helper = new CommBbsHelper();
		helper.getBbsInfoParam(request, bbsInfoDto);
		String msg = "";

		if (pGubun.equals("write")) {
			bbsInfoDto.setRegId(userId);
			bbsInfoDAO.commBbsRegister(bbsInfoDto);
			msg = "게시판을 생성하였습니다.";
		} else {
			bbsInfoDto.setModId(userId);
			bbsInfoDAO.commBbsEdit(bbsInfoDto);
			msg = "게시판을 수정하였습니다.";
		}

		return notifyAndExit(systemCode, model, msg, "/Community.cmd?cmd=commBbsManagePagingList", COMMSUB);
	}

	/**
	 * 게시판 정보를 등록/수정한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commBbsInfoWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception {
		String pGubun = StringUtil.nvl(request.getParameter("pGubun"), "write");
		String navigationMsg = "";
		CommBbsInfoDTO bbsInfo = null;

		if (pGubun.equals("write")) {
			navigationMsg = "게시판등록";
			bbsInfo = new CommBbsInfoDTO();
		} else {
			navigationMsg = "게시판수정";
			CommBbsInfoDAO bbsInfoDao = new CommBbsInfoDAO();
			String systemCode = UserBroker.getSystemCode(request);
			CommMemDTO memDto = UserBroker.getCommInfo(request);
			String pBbsId = StringUtil.nvl(request.getParameter("pBbsId"));

			bbsInfo = bbsInfoDao.getCommBbsInfo(systemCode, memDto.commId, pBbsId);
		}

		new SiteNavigation(COMMSUB).add(request,navigationMsg).link(model);

		model.put("pGubun", pGubun);
		model.put("bbsInfo", bbsInfo);

		return forward(request, model, "/community/commBbsInfoWrite.jsp");
	}

	/**
	 * 동아리관리->게시판관리
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commBbsManagePagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		ListDTO commBbsManagePagingList = null;

		int totCnt = 0;
		int curPage = 1;
		if (request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));

		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String commId = commMemDto.commId;

		CommInfoDAO commInfoDao = new CommInfoDAO();
		commBbsManagePagingList = commInfoDao.getCommBbsManagePagingList(curPage, systemCode, commId);
		model.put("commBbsManagePagingList", commBbsManagePagingList);

		new SiteNavigation(COMMSUB).add(request,"동아리").link(model);
		return forward(request, model, "/community/commBbsManagePagingList.jsp");
	}

	/**
	 * 동아리 카테고리 리스트
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commCategoryPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		log.debug("=============================================================commPagingList start");
		String systemCode = UserBroker.getSystemCode(request);
		ListDTO commCategoryPagingList = null;

		int totCnt = 0;
		int curPage = 1;
		if (request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));
		log.debug("[curPage=======]" + curPage);

		CommInfoDAO commInfoDao = new CommInfoDAO();
		commCategoryPagingList = commInfoDao.getCategoryPagingList(curPage, systemCode);
		model.put("commCategoryPagingList", commCategoryPagingList);

		log.debug("=============================================================commPagingList end");
		new SiteNavigation(COMMUNITY).add(request,"동아리").link(model);
		return forward(request, model, "/community/commCategoryPagingList.jsp");
	}

	/**
	 * 동아리 폐쇄등록 신청
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commCloseRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String pCloseYn = StringUtil.nvl(request.getParameter("pCloseYn"), "Y");
		int pCommId = Integer.parseInt(request.getParameter("pCommId"));
		CommInfoDAO commInfoDao = new CommInfoDAO();
		int retVal = 0;
		if (pCloseYn.equals("N")) {
			retVal = commInfoDao.commCloseRegist(systemCode, userId, pCommId, "N");
			retVal = commInfoDao.commInfoClosingChange(systemCode, pCommId, "Y");
			return notifyAndExit(systemCode, model, "동아리 폐쇄신청을 취소 하였습니다.", "/CommManage.cmd?cmd=commInfoClosingList&pUseYn=C", MYPAGE);
		} else {
			retVal = commInfoDao.commCloseRegist(systemCode, userId, pCommId, pCloseYn);
			retVal = commInfoDao.commInfoClosingChange(systemCode, pCommId, "C");
			return createMessageForward(systemCode, model, "동아리 폐쇄신청을 하였습니다.", "/Community.cmd?cmd=commInfoList&pMode=Community", "", "true", "false", "false", "", COMMSUB);
		}
	}

	/**
	 * 동아리 폐쇄여부를 변경한
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commInfoClosingChange(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		log.debug("======================commInfoBestYnChange start");
		String systemCode = UserBroker.getSystemCode(request);
		int commId = Integer.parseInt(request.getParameter("pCommId"));

		String useYn = StringUtil.nvl(request.getParameter("pUseYn"));

		CommInfoDAO commInfoDao = new CommInfoDAO();

		String msg = "동아리 폐쇄 여부 변경했습니다.";
		String returnUrl = "/CommManage.cmd?cmd=commInfoClosingList&pUseYn=C";

		int retVal = 0;
		retVal = commInfoDao.commInfoClosingChange(systemCode, commId, useYn);

		if (retVal <= 0) {
			msg = "동아리 폐쇄 여부 변경 실패 하였습니다.";
		}

		new SiteNavigation(MYPAGE).add(request,StringUtil.inDataConverter("동아리관리")).link(model);
		return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}

	/**
	 * 동아리정보관리-수정
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commInfoEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String commId = commMemDto.commId;

		CommInfoDAO commInfoDao = new CommInfoDAO();
		CommInfoDTO commInfo = commInfoDao.getCommInfo(systemCode, commId);

		model.put("commId", commId);
		model.put("commInfo", commInfo);

		new SiteNavigation(COMMSUB).add(request,"동아리").link(model);
		return forward(request, model, "/community/commInfoEdit.jsp");
	}

	/**
	 * 동아리카테고리 파일삭제
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commInfoFileDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		log.debug("======================commInfoFileDelete start");
		String systemCode = UserBroker.getSystemCode(request);
		String commId = request.getParameter("pCommId");
		String pWhere = StringUtil.nvl(request.getParameter("pWhere"), "A");

		CommInfoDAO commInfoDao = new CommInfoDAO();
		CommInfoDTO commInfo = commInfoDao.getCommInfo(systemCode, commId);
		String mainImg = "";

		String msg = "삭제하였습니다.";
		String returnUrl = "/Community.cmd?cmd=makeCommunity&pGubun=Edit&pCommId=" + commId;

		int retVal = 0;
		retVal = commInfoDao.delCommInfoFile(systemCode, commId);

		mainImg = commInfo.getMainImg();

		log.debug(" mainImg..." + mainImg);
		if (!mainImg.equals("")) {
			FileUtil.delFile(mainImg);
			log.debug(" 첨부파일을 삭제하였습니다." + mainImg);
		}

		if (retVal <= 0) {
			msg = "첨부파일 삭제 실패 하였습니다.";
		}

		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if (pMode.equals("")) pMode = COMMSUB;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter("동아리")).link(model);
		return notifyAndExit(systemCode, model, msg, returnUrl, COMMSUB);
	}
	/**
	 * 나의 동아리 리스트를 가져온다.(페이징 없음)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commInfoList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		if (userId.equals("") || userId.equals("@")) return alertAndExit(UserBroker.getSystemCode(request), model, "먼저 로그인을 하셔야 합니다.", "/Main.cmd?cmd=mainShow&pMode=Home", HOME);

		int totCnt = 0;
		CommInfoDAO comminfoDao = new CommInfoDAO();

		RowSet rs = comminfoDao.getCommInfoList(systemCode, userId);

		model.put("rs", rs);

		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if (pMode.equals("MyPage")) {
			new SiteNavigation(MYPAGE).add(request,"나의 동아리").link(model);
			return forward(request, model, "/main/MycommunityMain.jsp");
		} else {
			new SiteNavigation(COMMUNITY).add(request,"나의 동아리").link(model);
			return forward(request, model, "/community/MycommunityMain.jsp");
		}
	}

	/**
	 * 동아리 회원 삭
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commMemberDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		log.debug("======================commMemberDelete start");
		String systemCode = UserBroker.getSystemCode(request);
		String modId = UserBroker.getUserId(request);
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String commId = commMemDto.commId;

		String memberId = StringUtil.nvl(request.getParameter("pMemberId"));

		CommMembersDAO commMembersDao = new CommMembersDAO();

		String msg = "회원을 삭제했습니다.";
		String returnUrl = "/Community.cmd?cmd=commMembersManagePagingList&MENUNO=0";

		int retVal = 0;
		retVal = commMembersDao.commMemberDelete(systemCode, commId, memberId);

		if (retVal <= 0) {
			msg = "회원 삭제를 실패 하였습니다.";
		}

		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if (pMode.equals("")) pMode = COMMSUB;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter("동아리")).link(model);
		return notifyAndExit(systemCode, model, msg, returnUrl, COMMSUB);
	}

	/**
	 * 동아리 회원 정보수정
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commMemberEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String commId = commMemDto.commId;

		CommInfoDAO commInfoDao = new CommInfoDAO();

		model.put("commId", commId);
		model.put("memberInfo", commInfoDao.getCommMemberInfo(systemCode, commId, userId));

		new SiteNavigation(COMMSUB).add(request,"동아리").link(model);
		return forward(request, model, "/community/commMemberEdit.jsp");
	}

	/**
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commMemberRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		log.debug("=====================commMemberRegist start");
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String commId = commMemDto.commId;

		CommMembersDAO commMembersDao = new CommMembersDAO();
		CommMembersDTO commMembersDto = new CommMembersDTO();

		String regMode = StringUtil.nvl(request.getParameter("pRegMode"));
		String userNick = StringUtil.nvl(request.getParameter("pUserNick"));
		String userIntro = StringUtil.nvl(request.getParameter("pUserIntro"));

		log.debug("userNick ==>" + userNick);
		log.debug("userIntro ==>" + userIntro);
		log.debug("regMode ==>" + regMode);

		int retVal = 0;

		if (regMode.equals("Add"))// 입력모드
		{
		}

		if (regMode.equals("Edit"))// 수정 모드 접근시
		{
		}

		commMembersDto.setSystemCode(systemCode);
		commMembersDto.setCommId(commId);
		commMembersDto.setUserId(userId);
		commMembersDto.setUserNick(userNick);
		commMembersDto.setUserIntro(userIntro);

		String msg = "";
		String returnUrl = "Community.cmd?cmd=goCommunity&pCommId=" + commId;
		if (regMode.equals("Add"))// 입력모드
		{
			commMembersDto.setRegId(userId);
			//			retVal = commMembersDao.addCommMember(commMembersDto);
			msg = "등록완료";
		} else {
			commMembersDto.setModId(userId);
			retVal = commMembersDao.editCommMember(commMembersDto);
			msg = "수정완료";
			if (retVal <= 0) {
				msg = "수정오류 다시 진행해 주세요";
			}
		}
		log.debug("retVal ==>" + retVal);

		new SiteNavigation(COMMSUB).add(request,"동아리").link(model);
		log.debug("======================commMemberRegist end");
		return notifyAndExit(systemCode, model, msg, returnUrl, COMMSUB);
	}

	/**
	 * sangsang
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commMembersManagePagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		CommInfoDAO commInfoDao = new CommInfoDAO();
		CommInfoDTO commInfoDto = new CommInfoDTO();
		CommMembersDAO commMembersDao = new CommMembersDAO();
		String commId = commMemDto.commId;

		// 회원관리
		log.debug("=============================================================commMembersPagingList start");
		String pFields = StringUtil.nvl(request.getParameter("pFields"));
		String pValue = StringUtil.nvl(request.getParameter("pValue"));
		log.debug("[pFields=======]" + pFields);
		log.debug("[pValue=======]" + pValue);

		commInfoDto = commInfoDao.getCommInfo(systemCode, commId);
		model.put("commInfoDto", commInfoDto);

		ListDTO commMembersPagingList = null;
		int curPage = 1;
		if (request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));
		log.debug("[curPage=======]" + curPage);
		log.debug("[commId=======]" + commId);

		commMembersPagingList = commMembersDao.getCommMembersPagingList(curPage, systemCode, commId, pFields, pValue);
		model.put("commMembersPagingList", commMembersPagingList);
		model.put("pFields", pFields);
		model.put("pValue", pValue);

		log.debug("=============================================================commMembersPagingList end");

		// 신규가입회원
		log.debug("=============================================================commNewMembersPagingList start");
		ListDTO commNewMembersPagingList = null;

		int curPageNew = 1;
		if (request.getParameter("curPageNew") != null) curPageNew = Integer.parseInt(request.getParameter("curPageNew"));
		log.debug("[curPage=======]" + curPageNew);
		log.debug("[commId=======]" + commId);

		commNewMembersPagingList = commMembersDao.getCommNewMembersPagingList(curPageNew, systemCode, commId, "N");
		model.put("commNewMembersPagingList", commNewMembersPagingList);
		log.debug("=============================================================commNewMembersPagingList end");

		new SiteNavigation(COMMSUB).add(request,"동아리").link(model);
		return forward(request, model, "/community/commMembersManagePagingList.jsp");
	}

	/**
	 * sangsang
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commMembersPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		log.debug("=============================================================commMembersPagingList start");
		String systemCode = UserBroker.getSystemCode(request);
		String pFields = StringUtil.nvl(request.getParameter("pFields"));
		String pValue = StringUtil.nvl(request.getParameter("pValue"));
		log.debug("[pFields=======]" + pFields);
		log.debug("[pValue=======]" + pValue);

		int totCnt = 0;
		int curPage = 1;
		if (request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));

		log.debug("[curPage=======]" + curPage);
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String commId = commMemDto.commId;

		log.debug("[commId=======]" + commId);
		ListDTO commMembersPagingList = null;
		CommInfoDAO commInfoDao = new CommInfoDAO();
		commMembersPagingList = commInfoDao.getCommMembersPagingList(curPage, systemCode, commId, pFields, pValue);

		model.put("commMembersPagingList", commMembersPagingList);
		model.put("pFields", pFields);
		model.put("pValue", pValue);
		model.put("curPage", String.valueOf(curPage));

		log.debug("=============================================================commMembersPagingList end");
		new SiteNavigation(COMMSUB).add(request,"동아리").link(model);
		return forward(request, model, "/community/commMembersPagingList.jsp");
	}

	/**
	 * 동아리 멤버 승인여부 변경
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commMemberUseYnChange(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		log.debug("======================commMemberUseYnChange start");
		String systemCode = UserBroker.getSystemCode(request);
		String modId = UserBroker.getUserId(request);
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String commId = commMemDto.commId;

		String memberId = StringUtil.nvl(request.getParameter("pMemberId"));
		String useYn = StringUtil.nvl(request.getParameter("pUseYn"));

		CommMembersDAO commMembersDao = new CommMembersDAO();

		String msg = "회원가입을 승인했습니다.";
		String returnUrl = "/Community.cmd?cmd=commMembersManagePagingList&MENUNO=0";

		int retVal = 0;
		retVal = commMembersDao.commMemberUseYnChange(systemCode, commId, modId, memberId, useYn);

		if (retVal <= 0) {
			msg = "회원가입을 승인 실패 하였습니다.";
		}

		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if (pMode.equals("")) pMode = COMMSUB;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter("동아리")).link(model);
		return notifyAndExit(systemCode, model, msg, returnUrl, COMMSUB);
	}

	/**
	 * 동아리리스트(검색결과)-sangsang
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		log.debug("=============================================================commPagingList start");
		String systemCode = UserBroker.getSystemCode(request);
		String pFields = StringUtil.nvl(request.getParameter("pFields"));
		String pValue = StringUtil.nvl(request.getParameter("pValue"));
		String pCateCode = StringUtil.nvl(request.getParameter("pCateCode"));
		log.debug("[pFields=======]" + pFields);
		log.debug("[pValue=======]" + pValue);
		log.debug("[pCateCode=======]" + pCateCode);

		ListDTO commPagingList = null;

		int totCnt = 0;
		int curPage = 1;
		if (request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));
		log.debug("[curPage=======]" + curPage);

		CommInfoDAO commInfoDao = new CommInfoDAO();
		CommCategoryDAO commCategoryDao = new CommCategoryDAO();
		RowSet rs4 = commCategoryDao.getCommCategoryList(systemCode);
		model.put("rs4", rs4);

		commPagingList = commInfoDao.getCommPagingList(curPage, systemCode, pCateCode, pFields, pValue);
		model.put("pCateCode", pCateCode);
		model.put("commPagingList", commPagingList);
		model.put("pFields", pFields);
		model.put("pValue", pValue);

		log.debug("=============================================================commPagingList end");
		new SiteNavigation(COMMUNITY).add(request,"동아리").link(model);
		return forward(request, model, "/community/commPagingList.jsp");
	}

	/**
	 * 동아리 자랑의 리스트
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commPridebbsPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);
		String userType = UserBroker.getUserType(request);
		String userId = UserBroker.getUserId(request);

		if (userId.equals("") || userId.equals("@")) return alertAndExit(UserBroker.getSystemCode(request), model, "먼저 로그인을 하셔야 합니다.", "/Main.cmd?cmd=mainShow&pMode=Home", HOME);

		//동아리 자랑은 ID 가 4 이다.
		int pBbsId = (request.getParameter("pBbsId") != null)? 4 : Integer.parseInt(request.getParameter("pBbsId"));

		String pSearchKey = StringUtil.nvl(request.getParameter("pSearchKey"));
		String pKeyWord = StringUtil.nvl(request.getParameter("pKeyWord"));

		ListDTO bbsContentsList = null;
		int curPage = 1 ;//StringUtil.nvl(request.getParameter("curPage"), 1);
		if (request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));

		CommBbsInfoDAO commbbsInfoDao = new CommBbsInfoDAO();

		String pBbsType = "P"; // 동아리 자랑 게시판.//infoRs.getString("bbs_type");

		model.put("pBbsType", pBbsType);

		String pOrder = "";

		model.put("pBbsId", String.valueOf(pBbsId));
		model.put("pSearchKey", pSearchKey);
		model.put("pKeyWord", pKeyWord);

		model.put("contentsList", commbbsInfoDao.getCommPrideBbsPagingList(curPage, pBbsId, systemCode, pSearchKey, pKeyWord, pOrder));

		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if (pMode.equals("")) pMode = COMMUNITY;
		model.put("pMode", pMode);

		new SiteNavigation(pMode).add(request,"동아리자랑").link(model);
		//log.debug("_________________________ commPridebbsPagingList end");
		return forward(request, model, "/community/commPridePagingList.jsp");
	}

	/**
	 * 동아리자랑 게시글  보여주기
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commPrideContentsShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		log.debug("__________________________________ commPrideContentsShow Start");
		String systemCode = UserBroker.getSystemCode(request);
		int pCommId = Integer.parseInt(request.getParameter("pCommId"));
		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));

		CommInfoDAO commInfoDao = new CommInfoDAO();

		RowSet infoRs = commInfoDao.getCommBbsInfo(systemCode, pBbsId);
		infoRs.next();
		String bbsName = infoRs.getString("bbs_name");
		String pCommentUseYn = infoRs.getString("comment_use_yn");
		model.put("pCommId", String.valueOf(pCommId));
		model.put("pBbsId", String.valueOf(pBbsId));
		model.put("pBbsType", infoRs.getString("bbs_type"));
		model.put("pFileUseYn", infoRs.getString("file_use_yn"));
		model.put("pCommentUseYn", pCommentUseYn);

		CommBbsInfoDAO commBbsInfoDao = new CommBbsInfoDAO();

		//		-- 조회수 증가 시키기
		int retVal = 0;
		retVal = commBbsInfoDao.hitUpBbsContents(systemCode, pCommId, pBbsId, pSeqNo);

		CommBbsContentsDTO contentsDto = commBbsInfoDao.getCommPrideContents(systemCode, pBbsId, pSeqNo);
		model.put("contentsDto", contentsDto);

		String AddWhere = "";

		//-- 삭제시 하위글 있는지 체크하기위해서 아래 글 갯수를 가져온다.
		int pChildCnt = 0;
		AddWhere = "parent_no=" + pSeqNo;
		pChildCnt = commBbsInfoDao.getCommBbsContentsCount(systemCode, pBbsId, AddWhere);
		model.put("pChildCnt", String.valueOf(pChildCnt));

		//-- 코멘트 사용시 코멘트 갯수 목록 가져오기
		CommBbsCommentDAO commbbsCommentDao = new CommBbsCommentDAO();
		if (pCommentUseYn.equals("Y")) {
			int pCommentCnt = 0;
			pCommentCnt = commbbsCommentDao.getBbsCommentCount(systemCode, pBbsId, pSeqNo);
			model.put("pCommentCnt", String.valueOf(pCommentCnt));
			if (pCommentCnt > 0) {
				RowSet commentList = commbbsCommentDao.getBbsCommentList(systemCode, pBbsId, pSeqNo);
				model.put("commentList", commentList);
			}
		}
		model.put("curPage", StringUtil.nvl(request.getParameter("curPage"),"1"));
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if (pMode.equals("")) pMode = COMMUNITY;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,"동아리자랑").link(model);
		log.debug("__________________________________ commPrideContentsShow End");
		return forward(request, model, "/community/commPrideShow.jsp");
	}

	/**
	 * 동아리 자랑 게시글 등록/수정 한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commPrideRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {

		log.debug("=============================================================bbsContentsRegist start");
		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);

        int pCommId = StringUtil.nvl(request.getParameter("pCommId"),1);
		int pBbsId = StringUtil.nvl(request.getParameter("pBbsId"), 0);
		int pSeqNo = StringUtil.nvl(request.getParameter("pSeqNo"), 1);

		CommInfoDAO commBbsDao = new CommInfoDAO();

		CommBbsInfoDAO commBbsInfoDao = new CommBbsInfoDAO();
		CommBbsContentsDTO commBbsInfoDto = new CommBbsContentsDTO();

		MultipartRequest multipartRequest = null;
		int retVal = 0;
		String objName = "";
		String rFileName = "";
		String sFileName = "";
		String filePath = "";
		String fileSize = "";

		String regMode = StringUtil.nvl(request.getParameter("pRegMode"));

		if (regMode.equals("Add") || regMode.equals("Reply"))// 입력모드
		{
			pSeqNo = commBbsInfoDao.getMaxSeqNo(systemCode, pBbsId);
		}

		String RegMonth = CommonUtil.getCurrentDate().substring(0, 6);
		if (regMode.equals("Edit")) {//-- 수정모드시는 현재 월이 아닌 등록된 글의 년월을 가져온다.
			RegMonth = StringUtil.nvl(request.getParameter("pRegDate")).substring(0, 6);
		}

		String FilePath = FileUtil.FILE_DIR + systemCode + "/commPride/" + pBbsId + "/" + RegMonth + "/";//-- 게시판아이디 + 년월

		if (systemCode.equals("")) systemCode = StringUtil.nvl(request.getParameter("pSystemCode"));;
		if (regId.equals("")) regId = StringUtil.nvl(request.getParameter("pUserId"));

		//		  파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath, pSeqNo + "_" + regId);
		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();
		String status = uploadEntity.getStatus();

		String pOldrFileName[] = new String[4];
		String pOldsFileName[] = new String[4];
		String pOldFilePath[] = new String[4];
		String pOldFileSize[] = new String[4];
		for (int i = 1; i <= 3; i++) {
			pOldrFileName[i] = StringUtil.nvl(multipartRequest.getParameter("pOldrFileName[" + i + "]"));
			pOldsFileName[i] = StringUtil.nvl(multipartRequest.getParameter("pOldsFileName[" + i + "]"));
			pOldFilePath[i] = StringUtil.nvl(multipartRequest.getParameter("pOldFilePath[" + i + "]"));
			pOldFileSize[i] = StringUtil.nvl(multipartRequest.getParameter("pOldFileSize[" + i + "]"));
		}

		commBbsInfoDto.setRfileName1(pOldrFileName[1]);
		commBbsInfoDto.setSfileName1(pOldsFileName[1]);
		commBbsInfoDto.setFilePath1(pOldFilePath[1]);
		commBbsInfoDto.setFileSize1(pOldFileSize[1]);
		commBbsInfoDto.setRfileName2(pOldrFileName[2]);
		commBbsInfoDto.setSfileName2(pOldsFileName[2]);
		commBbsInfoDto.setFilePath2(pOldFileSize[2]);
		commBbsInfoDto.setFileSize2(pOldFileSize[2]);
		commBbsInfoDto.setRfileName3(pOldrFileName[3]);
		commBbsInfoDto.setSfileName3(pOldsFileName[3]);
		commBbsInfoDto.setFilePath3(pOldFileSize[3]);
		commBbsInfoDto.setFileSize3(pOldFileSize[3]);

		String pSubject = StringUtil.nvl(multipartRequest.getParameter("pSubject"));
		String pKeyword = StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
		String pContents = StringUtil.nvl(multipartRequest.getParameter("pContents"));

		if (status.equals("E")) {
			log.debug("첨부 파일 올리려다 실패하였습니다.");
		} else if (status.equals("O")) {
			log.debug("첨부하신 파일이 용량을 초과했습니다.");
		} else if (status.equals("I")) {
			log.debug("첨부 파일의 정보가 잘못되었습니다.");
		} else if (status.equals("S")) {
			// 업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			log.debug("첨부 파일을 첨부하는데 성공하였습니다.");
			/** 웹에디터에서 첨부파일을 첨부시 인식하는걸 막기위해 **/
			ArrayList files = uploadEntity.getFiles();
			UploadFile file = null;
			for (int i = 0; i < files.size(); i++) {
				file = (UploadFile) files.get(i);
				objName = file.getObjName();
				rFileName = StringUtil.nvl(file.getRootName());
				if (!rFileName.equals("")) {
					sFileName = file.getUploadName();
					fileSize = String.valueOf(file.getSize());
					filePath = uploadEntity.getUploadPath();
				}
				if (objName.indexOf("pFile[") >= 0) {
					int idx = Integer.parseInt(String.valueOf(objName.charAt(6)));

					if (idx == 1) {
						commBbsInfoDto.setRfileName1(rFileName);
						commBbsInfoDto.setSfileName1(sFileName);
						commBbsInfoDto.setFilePath1(filePath);
						commBbsInfoDto.setFileSize1(fileSize);
					} else if (idx == 2) {
						commBbsInfoDto.setRfileName2(rFileName);
						commBbsInfoDto.setSfileName2(sFileName);
						commBbsInfoDto.setFilePath2(filePath);
						commBbsInfoDto.setFileSize2(fileSize);
					} else if (idx == 3) {
						commBbsInfoDto.setRfileName3(rFileName);
						commBbsInfoDto.setSfileName3(sFileName);
						commBbsInfoDto.setFilePath3(filePath);
						commBbsInfoDto.setFileSize3(fileSize);
					}
					if (!pOldsFileName[idx].equals("")) { //이전 첨부파일을 삭제할 경우
						log.debug("이전 파일 삭제하기" + pOldFilePath[idx] + pOldsFileName[idx]);
						FileUtil.delFile(pOldFilePath[idx], pOldsFileName[idx]);
					}
				}
			}// For End
		}

		/** 웹에디터 셋팅 추가 Start**/
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH + FilePath;
		String WeasFileUrl = ServiceUrl + "/data/" + FilePath;
		log.debug("WeasFilePath = " + WeasFilePath);
		VBN_files v_objFile = null;

		java.io.File dir = new java.io.File(WeasFilePath);
		if (!dir.exists()) dir.mkdirs();

		v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
		pContents = v_objFile.VBN_uploadMultiFiles(pContents, multipartRequest);
		/** 웹에디터 셋팅 추가 End**/

		String pEditorType = StringUtil.nvl(multipartRequest.getParameter("pEditorType"));
		int pBbsNo = StringUtil.nvl(multipartRequest.getParameter("pBbsNo"), pSeqNo);
		int pDepthNo = StringUtil.nvl(multipartRequest.getParameter("pDepthNo"), 0);
		int pOrderNo = StringUtil.nvl(multipartRequest.getParameter("pOrderNo"), 0);
		int pParentNo = StringUtil.nvl(multipartRequest.getParameter("pParentNo"), 0);
		String pContentsType = StringUtil.nvl(multipartRequest.getParameter("pContentsType"));

		String pYear = StringUtil.nvl(multipartRequest.getParameter("pYear1"));
		String pMonth = StringUtil.nvl(multipartRequest.getParameter("pMonth1"));
		String pDay = StringUtil.nvl(multipartRequest.getParameter("pDay1"));
		String pDate1 = StringUtil.nvl(multipartRequest.getParameter("pDate1"));
		String pExpireDate = pYear + pMonth + pDay + "000000";
		if (pYear.equals("")) pExpireDate = "";

		String pTarget = "";
		String arrTarget[] = multipartRequest.getParameterValues("pTarget");
		for (int i = 0; i < arrTarget.length; i++) {
			pTarget = pTarget + arrTarget[i];
		}

		if (regMode.equals("Add")) {
			pBbsNo = pSeqNo;
			pDepthNo = 0;
			pOrderNo = 0;
			pParentNo = 0;
		}
		if (regMode.equals("Reply")) {
			pDepthNo = pDepthNo + 1;
			pOrderNo = pOrderNo + 1;
		}
		log.debug("+++++++++++++++++++++++++++++++++   chk Point");
		String userName = UserBroker.getUserName(request);

		if (userName.equals("")) userName = StringUtil.nvl(multipartRequest.getParameter("pRegName"));
		String regPasswd = StringUtil.nvl(multipartRequest.getParameter("pRegPasswd"));
		String regEmail = StringUtil.nvl(multipartRequest.getParameter("pRegEmail"));
		commBbsInfoDto.setSystemCode(systemCode);
		commBbsInfoDto.setCommId(pCommId);
		commBbsInfoDto.setBbsId(pBbsId);
		commBbsInfoDto.setSeqNo(pSeqNo);
		commBbsInfoDto.setBbsNo(pBbsNo);
		commBbsInfoDto.setDepthNo(pDepthNo);
		commBbsInfoDto.setOrderNo(pOrderNo);
		commBbsInfoDto.setParentNo(pParentNo);
		commBbsInfoDto.setEditorType(pEditorType);
		commBbsInfoDto.setContentsType(pContentsType);
		commBbsInfoDto.setSubject(pSubject);
		commBbsInfoDto.setKeyword(pKeyword);
		commBbsInfoDto.setContents(pContents);
		commBbsInfoDto.setExpireDate(pExpireDate);
		commBbsInfoDto.setTarget(pTarget);
		commBbsInfoDto.setRegId(regId);
		commBbsInfoDto.setRegName(userName);
		commBbsInfoDto.setRegEmail(regEmail);
		commBbsInfoDto.setRegPasswd(regPasswd);
		commBbsInfoDto.setModId(regId);
		String msg = "";
		String pMode = StringUtil.nvl(multipartRequest.getParameter("pMode"));
		if (pMode.equals("")) pMode = COMMUNITY;

		String returnUrl = "/Community.cmd?cmd=commPridebbsPagingList&pMode=" + pMode + "&pBbsId=" + pBbsId;
		log.debug("+++++++++++++++++++++++++++++++++   chk Point 2");
		if (regMode.equals("Add")) { // 입력모드
			retVal = commBbsInfoDao.addPrideRegister(commBbsInfoDto);
			msg = "등록완료";
		} else if (regMode.equals("Edit")) {
			retVal = commBbsInfoDao.editPrideRegister(commBbsInfoDto);
			msg = "수정완료";
			if (retVal <= 0) {
				returnUrl = "/Community.cmd?cmd=bbsContentsEdit&pCommId="+pCommId + "&pBbsId=" + pBbsId + "&pSeqNo=" + pSeqNo;
				msg = "수정오류 다시 진행해 주세요";
			}
		} else if (regMode.equals("Reply")) {
			boolean bVal = commBbsInfoDao.replyUpdateBbsContents(commBbsInfoDto, "Ins");
			if (bVal)
				retVal = 1;
			else
				retVal = 0;
			if (retVal > 0) {
				retVal = commBbsInfoDao.addPrideRegister(commBbsInfoDto);
				msg = "등록완료";
			}
			if (retVal <= 0) {
				returnUrl = "/Community.cmd?cmd=commPrideWrite&pCommId="+pCommId + "&pBbsId=" + pBbsId + "&pSeqNo=" + pSeqNo;
				msg = "등록오류 다시 진행해 주세요";
			}
		}
		log.debug("retVal ==>" + retVal);
		log.debug("returnUrl ==>" + returnUrl);
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,"동아리자랑").link(model);
		log.debug(pMode);
		log.debug("=============================================================bbsContentsRegist end");
		return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
	}

	/**
	 * 동아리자랑 작성 폼을 띄워준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commPrideWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		CommBbsContentsDTO contentsDto = null;
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		// 동아리 자랑 : 4
		int pBbsId = (request.getParameter("pBbsId") != null)? Integer.parseInt(request.getParameter("pBbsId")) : 4;
		int pSeqNo = StringUtil.nvl(request.getParameter("pSeqNo"), 0);

		String pRegMode = "Add";

		// 나의 동아리 목록 가져오기.
		if (userId.equals("") || userId.equals("@")) return alertAndExit(UserBroker.getSystemCode(request), model, "먼저 로그인을 하셔야 합니다.", "/Main.cmd?cmd=mainShow&pMode=Home", HOME);

		CommInfoDAO comminfoDao = new CommInfoDAO();
		RowSet rs = comminfoDao.getCommInfoList(systemCode, userId);

		model.put("rs", rs);


		if (pSeqNo > 0) {
			CommBbsInfoDAO commbbsInfoDao = new CommBbsInfoDAO();
			contentsDto = new CommBbsContentsDTO();
			contentsDto = commbbsInfoDao.getCommPrideContents(systemCode, pBbsId, pSeqNo);

			model.put("pBbsNo", String.valueOf(contentsDto.getBbsNo()));
			model.put("pDepthNo", String.valueOf(contentsDto.getDepthNo()));
			model.put("pOrderNo", String.valueOf(contentsDto.getOrderNo()));
			model.put("pParentNo", String.valueOf(pSeqNo));
			pRegMode = "Reply";
		}

		CommInfoDAO commInfoDao = new CommInfoDAO();

		model.put("pRegMode", String.valueOf(pRegMode));
		model.put("pBbsId", String.valueOf(pBbsId));
		model.put("pBbsType", "P");
		model.put("pFileUseYn", "Y");
		model.put("pEditorYn", "Y");

		DateSetter ds = new DateSetter("").link(model);
		model.put("ds", ds);
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if (pMode.equals("")) pMode = COMMUNITY;
		model.put("pMode", pMode);

		new SiteNavigation(pMode).add(request,"동아리자랑").link(model);
		return forward(request, model, "/community/commPrideWrite.jsp");
	}

	/*******************/
	/*******************/
	/*통합게시판 관련 메서드*/
	/*******************/
	/*******************/



	/**
	 * 나의 동아리 탈퇴 하기
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	//delUserCommunity
	public ActionForward delUserCommunity(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		int commId = Integer.parseInt(request.getParameter("pCommId"));

		log.debug("++++++++++++++++++++++++" + commId);

		CommInfoDAO comminfoDao = new CommInfoDAO();

		comminfoDao.delCommMemberReg(systemCode, userId, commId);

		RowSet rs = comminfoDao.getCommInfoList(systemCode, userId);
		model.put("rs", rs);

		new SiteNavigation(COMMUNITY).add(request,"나의 동아리").link(model);
		return forward(request, model, "/community/MycommunityMain.jsp");
	}

	/**
	 * 서브동아리메인화면으로~~
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward goCommunity(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		UserMemDTO userMem = UserBroker.getUserInfo(request);
		String userId = userMem.userId;
		String pCommId = StringUtil.nvl(request.getParameter("pCommId"));

		/* 동아리 제목 받아오기 ㅡㅡ^*/
		CommInfoDAO comminfoDao = new CommInfoDAO();
		RowSet rs1 = comminfoDao.getCommSubInfo(systemCode, userId, pCommId);
		rs1.next();
		String commName = rs1.getString("comm_name");
		rs1.close();

		/* 유저 레벨 받아오기  */
		String memLevelInfo = "";
		RowSet rs2 = comminfoDao.getCommSubMemInfo(systemCode, userId, pCommId);
		if (rs2.next()) {
			memLevelInfo = StringUtil.nvl(rs2.getString("user_level"));
		} else {
			memLevelInfo = "G";
		}

		rs2.close();

		// 관리자는 모든 동아리의 시샵 권한을 가진다.
		if (userMem.userType.equals("M")) memLevelInfo = "M";

		/* 동아리 정보 셋팅 */
		CommMemDTO commMemInfo = new CommMemDTO();
		commMemInfo.commId = pCommId;
		commMemInfo.commName = commName;
		commMemInfo.userLevel = memLevelInfo;

		UserBroker.setCommInfo(request, commMemInfo);
		/* 동아리 정보 셋팅 끝 */

		model.put("commId", pCommId);
		model.put("userId", userId);
		model.put("pMode", COMMSUB);

		log.debug("=============================================================goCommunity end");

		RowSet rs = comminfoDao.getCommSubInfo(systemCode, userId, pCommId);
		model.put("rs", rs);

		/*최근 게시물 로딩*/
		RowSet rs3 = comminfoDao.getCommSubNewContents(systemCode, pCommId);
		model.put("rs3", rs3);

		//		new SiteNavigation(COMMSUB).add(request,"동아리").link(model);
		new SiteNavigation(COMMSUB).add(request,commName).link(model);
		return forward(request, model, "/main/main_community_sub.jsp");
	}

	public ActionForward goCommunityMain(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		CommMemDTO commMemInfo = UserBroker.getCommInfo(request);

		CommInfoDAO comminfoDao = new CommInfoDAO();
		model.put("commId", commMemInfo.commId);

		model.put("userId", userId);

		log.debug("=============================================================goCommunity end");

		RowSet rs = comminfoDao.getCommSubInfo(systemCode, userId, commMemInfo.commId);
		model.put("rs", rs);

		/*최근 게시물 로딩*/
		RowSet rs3 = comminfoDao.getCommSubNewContents(systemCode, commMemInfo.commId);
		model.put("rs3", rs3);

		new SiteNavigation(COMMSUB).add(request,"동아리").link(model);
		return forward(request, model, "/main/main_community_sub.jsp");
	}

	/**
	 * 동아리 가입
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	//joinCommunity
	public ActionForward joinCommunity(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		UserMemDTO userInfo = UserBroker.getUserInfo(request);
		String commId = request.getParameter("pCommId");
		String userLevel = "";
		log.debug("++++++++++++++++++++++++" + commId);

		CommInfoDAO comminfoDao = new CommInfoDAO();

		String useYn = comminfoDao.joinCheckUseYn(systemCode, userInfo.userId, commId);

		if (useYn.equals("1")) {
			useYn = "Y";
			userLevel = "A";
		} else {
			useYn = "N";
			userLevel = "B";
		}

		String infoRe = comminfoDao.joinCheckComm(systemCode, userInfo.userId, commId);
		if (infoRe.equals("Y")) {
			comminfoDao.joinCommMemberReg(systemCode, userInfo.userId, commId, useYn, userLevel, userInfo.userName);
		} else
			return alertAndExit(systemCode, model, "이미 동아리에 가입 하셨습니다.", "/Community.cmd?cmd=commInfoList", COMMUNITY);

		RowSet rs = comminfoDao.getCommInfoList(systemCode, userInfo.userId);
		model.put("rs", rs);

		new SiteNavigation(COMMUNITY).add(request,"나의 동아리").link(model);
		return forward(request, model, "/community/MycommunityMain.jsp");
	}


	/**
	 * 동아리 만들기
	 * 동아리 수정부분 추가 - sangsang - 2004.11.05
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward makeCommRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {

		log.debug("=============================================================makeCommRegist start");
		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);
		String regName = UserBroker.getUserName(request);

		CommInfoDAO comminfoDao = new CommInfoDAO();
		CommInfoDTO commInfoDto = new CommInfoDTO();

		MultipartRequest multipartRequest = null;

		int retVal = 0;
		String objName = "";
		String rFileName = "";
		String sFileName = "";
		String filePath = "";
		String fileSize = "";

		String FilePath = FileUtil.IMG_DIR + "communityMainImg/";
		//			  파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath, "commMakeImg");

		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();
		String status = uploadEntity.getStatus();
		String pGubun = StringUtil.nvl(multipartRequest.getParameter("pGubun")); // Add or Edit
		String pCommId = StringUtil.nvl(multipartRequest.getParameter("pCommId"));
		String pWhere = StringUtil.nvl(multipartRequest.getParameter("pWhere"));

		ArrayList files = uploadEntity.getFiles();
		UploadFile file = null;
		for (int i = 0; i < files.size(); i++) {
			file = (UploadFile) files.get(i);
			objName = file.getObjName();
			rFileName = StringUtil.nvl(file.getRootName());
			sFileName = file.getUploadName();

			String realFile;
			realFile = uploadEntity.getUploadPath() + sFileName;
			commInfoDto.setMainImg(realFile);

		}// For End

		//수정모드일때...이미지가 존재하는데 첨부파일이 없다면 기존이민지 유지 -- sangsang
		if (pGubun.equals("Edit")) {
			if (files.size() == 0) {
				String main_img = null;
				CommInfoDAO commInfoDao = new CommInfoDAO();
				CommInfoDTO rs = commInfoDao.getCommInfo(systemCode, pCommId);
				main_img = rs.getMainImg();

				if (!main_img.equals("")) commInfoDto.setMainImg(main_img);
			}
		}

		if (status.equals("E")) {
			log.debug("첨부 파일 올리려다 실패하였습니다.");
		} else if (status.equals("O")) {
			log.debug("첨부하신 파일이 용량을 초과했습니다.");
		} else if (status.equals("I")) {
			log.debug("첨부 파일의 정보가 잘못되었습니다.");
		} else if (status.equals("S")) {
			log.debug("첨부 파일을 첨부하는데 성공하였습니다.");
		}

		String returnUrl = "/Community.cmd?cmd=commInfoList";

		if (pWhere.equals("M"))
			returnUrl = "/CommManage.cmd?cmd=commInfoPagingList";
		else if (pWhere.equals("C")) returnUrl = "/Community.cmd?cmd=goCommunityMain";
		//log.debug("+++++++++++++++++++++++++++++++++   chk Point 2");

		String commName = StringUtil.nvl(multipartRequest.getParameter("commName"));
		String cate = StringUtil.nvl(multipartRequest.getParameter("cate"));
		String commSynopsis = StringUtil.nvl(multipartRequest.getParameter("commSynopsis"));
		String searchWord = StringUtil.nvl(multipartRequest.getParameter("searchWord"));
		String comfirm = StringUtil.nvl(multipartRequest.getParameter("comfirm"));
		String openYn = StringUtil.nvl(multipartRequest.getParameter("openYn"));

		commInfoDto.setSystemCode(systemCode);
		commInfoDto.setCateCode(cate);
		commInfoDto.setCommMaster(regId);
		commInfoDto.setCommName(commName);
		commInfoDto.setCommSynopsis(commSynopsis);
		commInfoDto.setKeyword(searchWord);
		commInfoDto.setRegistType(comfirm);
		commInfoDto.setOpenYn(openYn);
		commInfoDto.setRegId(regId);
		commInfoDto.setModId(regId);

		String msg;

//System.out.println("============ Check01 ");
		
		if (pGubun.equals("Edit")) {
			commInfoDto.setCommId(Integer.parseInt(pCommId));
			retVal = comminfoDao.editCommInfo(commInfoDto); //comm_info 수정

			if (retVal > 0) {
				msg = "수정완료";
			} else {
				returnUrl = "/Community.cmd?cmd=makeCommunity&pWhere=" + pWhere + "&pGubun=Edit&pCommId=" + pCommId;
				msg = "수정오류 다시 진행해 주세요";
			}
		} else {
		
			/*commId loading... ^^:*/
			int newCommId = comminfoDao.getCommId(systemCode);
			commInfoDto.setCommId(newCommId);
			retVal = comminfoDao.addMakeCommRegister(commInfoDto); 	//comm_info 에 등록
			comminfoDao.addMakeCommMemberReg(commInfoDto, regName);	//동아리 유저에 등록
			
			comminfoDao.addAutoMakebbsNoticeReg(commInfoDto);  		//게시판 정보에 공지등록
			comminfoDao.addAutoMakebbsBoardReg(commInfoDto);		//게시판 정보에 자유게시판 등록
			comminfoDao.addAutoMakebbsFilesReg(commInfoDto);		//게시판 정보에 자료실등록
			comminfoDao.addAutoMakePrideBoardReg(commInfoDto);		//게시판 정보에 동아리 자랑 게시판 등록

			if (retVal > 0) {
				msg = "등록완료";
			} else {
				returnUrl = "/Community.cmd?cmd=makeCommunity";
				msg = "등록오류 다시 진행해 주세요";
			}
		
		}
		String pMode = COMMUNITY;
		String navigate = "동아리만들기";
		if (pWhere.equals("M")) {
			pMode = MYPAGE;
			navigate = "동아리관리";
		} else if (pWhere.equals("C")) {
			pMode = COMMSUB;
			navigate = "동아리정보수정";
		}

//System.out.println("============ Check10 ");
		
		new SiteNavigation(pMode).add(request,navigate).link(model);
		//log.debug("=============================================================makeCommRegist end");
		return notifyAndExit(systemCode, model, msg, returnUrl, pMode);

	}

	/**
	 * 동아리 만들기
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward makeCommunity(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		if (userId.equals("") || userId.equals("@")) return alertAndExit(UserBroker.getSystemCode(request), model, "먼저 로그인을 하셔야 합니다.", "/Main.cmd?cmd=mainShow&pMode=Home", HOME);

		String pGubun = StringUtil.nvl(request.getParameter("pGubun"), "Write");
		String pCommId = StringUtil.nvl(request.getParameter("pCommId"));
		String pWhere = StringUtil.nvl(request.getParameter("pWhere"), "A");

		CommInfoDTO infoDto = null;
		if (pGubun.equals("Write"))
			infoDto = new CommInfoDTO();
		else {
			CommInfoDAO infoDao = new CommInfoDAO();
			infoDto = infoDao.getCommInfo(systemCode, pCommId);
		}

		model.put("commInfo", infoDto);
		model.put("pGubun", pGubun);
		model.put("pCommId", pCommId);
		model.put("pWhere", pWhere);
		String pMode = COMMUNITY;
		String navigate = "동아리만들기";
		if (pWhere.equals("M")) {
			pMode = COMMUNITY;
			navigate = "동아리관리";
		} else if (pWhere.equals("C")) {
			pMode = COMMSUB;
			navigate = "동아리정보수정";
		}
		new SiteNavigation(pMode).add(request,navigate).link(model);
		return forward(request, model, "/community/makeCommunity.jsp");
	}

	/**
	 * 신규 동아리 View
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward newCommunity(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		if (userId.equals("") || userId.equals("@")) return alertAndExit(UserBroker.getSystemCode(request), model, "먼저 로그인을 하셔야 합니다.", "/Main.cmd?cmd=mainShow&pMode=Home", HOME);

		CommInfoDAO comminfoDao = new CommInfoDAO();
		RowSet rs = comminfoDao.getNewCommInfo(systemCode);

		model.put("rs", rs);
		model.put("userId", userId);
		new SiteNavigation(COMMUNITY).add(request,"신규동아리").link(model);
		return forward(request, model, "/community/newCommunity.jsp");
	}

	/**
	 * 추천 동아리
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward recCommunity(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		if (userId.equals("") || userId.equals("@"))
			return alertAndExit(UserBroker.getSystemCode(request), model, "먼저 로그인을 하셔야 합니다.", "/Main.cmd?cmd=mainShow&pMode=Home", HOME);



		CommInfoDAO comminfoDao = new CommInfoDAO();
		RowSet rs = comminfoDao.getRecCommInfo(systemCode);

		model.put("rs", rs);
		new SiteNavigation(COMMUNITY).add(request,"추천동아리").link(model);
		return forward(request, model, "/community/recCommunity.jsp");
	}


	/**
	 * 동아리 공지글 수정 폼
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward comSubNoticeContentsEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		log.debug("__________________________________ comSubContentsEdit Start");

		String systemCode = UserBroker.getSystemCode(request);

		/* setting value loadding */
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String commName = commMemDto.commName;
		String commId = commMemDto.commId;
		String userLevel = commMemDto.userLevel;

		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));

		CommInfoDAO commInfoDao = new CommInfoDAO();

		RowSet infoRs = commInfoDao.getCommBbsInfo(systemCode, commId);
		infoRs.next();

		model.put("pCommId", commId);
		model.put("commName", commName);

		model.put("pBbsId", String.valueOf(pBbsId));
		model.put("pBbsType", infoRs.getString("bbs_type"));
		model.put("pFileUseYn", infoRs.getString("file_use_yn"));
		model.put("pEditorYn", infoRs.getString("editor_yn"));

		CommBbsInfoDAO commbbsInfoDao = new CommBbsInfoDAO();


		RowSet contentsRs = commbbsInfoDao.getcommPrideContents(systemCode, pBbsId, pSeqNo);
//		RowSet contentsRs = commbbsInfoDao.getcommPrideContents(systemCode, commId, pBbsId, pSeqNo);
		model.put("contentsRs", contentsRs);

		RowSet contentsRs2 = commbbsInfoDao.getcommPrideContents(systemCode, pBbsId, pSeqNo);
//		RowSet contentsRs = commbbsInfoDao.getcommPrideContents(systemCode, commId, pBbsId, pSeqNo);
		contentsRs2.next();
		String expireDate = contentsRs2.getString("expire_date");
		log.debug("+++++++++" + contentsRs2.getString("expire_date"));

//		if (expireDate.equals("000000"))
//			expireDate = "";

		DateSetter ds = new DateSetter((String) contentsRs2.getString("expire_date")).link(model);
		model.put("ds", ds);
		String pMode = StringUtil.nvl(request.getParameter("pMode"));

		if (pMode.equals(""))
			pMode = COMMUNITY;

		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,"동아리").link(model);
		log.debug("__________________________________ comSubContentsEdit End");
		return forward(request, model, "/community/commSubNoticeEdit.jsp");
	}

}


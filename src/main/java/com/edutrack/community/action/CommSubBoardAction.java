/**
 *
 */
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
import com.edutrack.common.CommonUtil;
import com.edutrack.common.DateSetter;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CommMemDTO;
import com.edutrack.community.dao.CommBbsInfoDAO;
import com.edutrack.community.dao.CommInfoDAO;
import com.edutrack.community.dto.CommBbsContentsDTO;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author famlilia
 *
 */
public class CommSubBoardAction  extends StrutsDispatchAction {

	/**
	 *  통합보드 글쓰기 폼
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commSubBoardWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		CommBbsContentsDTO contentsDto = null;
		String systemCode = UserBroker.getSystemCode(request);
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);

		String commName = commMemDto.commName;
		String commId = commMemDto.commId;
		String userLevel = commMemDto.userLevel;

		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));
		int pSeqNo = StringUtil.nvl(request.getParameter("pSeqNo"), 0);
		String pBbsType = request.getParameter("pBbsType");

		String pRegMode = "Add";

		if (pSeqNo > 0) {
			CommBbsInfoDAO commbbsInfoDao = new CommBbsInfoDAO();
			contentsDto = new CommBbsContentsDTO();
			contentsDto = commbbsInfoDao.getCommPrideContents(systemCode, commId, pBbsId, pSeqNo);

			model.put("pBbsNo", String.valueOf(contentsDto.getBbsNo()));
			model.put("pDepthNo", String.valueOf(contentsDto.getDepthNo()));
			model.put("pOrderNo", String.valueOf(contentsDto.getOrderNo()));
			model.put("pParentNo", String.valueOf(pSeqNo));
			pRegMode = "Reply";
		}

		CommInfoDAO commInfoDao = new CommInfoDAO();

		RowSet infoRs = commInfoDao.getCommBbsInfo(systemCode, pBbsId, commId);
		infoRs.next();
		String bbsName = infoRs.getString("bbs_name");

		model.put("pRegMode", String.valueOf(pRegMode));
		model.put("pBbsId", String.valueOf(pBbsId));
		model.put("pBbsType", infoRs.getString("bbs_type"));
		model.put("pFileUseYn", infoRs.getString("file_use_yn"));
		model.put("pEditorYn", infoRs.getString("editor_yn"));
		model.put("pBbsType", pBbsType);
		model.put("bbsName", bbsName);

		model.put("commId", commId);
		model.put("commName", commName);
		model.put("userLevel", userLevel);

		DateSetter ds = new DateSetter("").link(model);
		model.put("ds", ds);
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if (pMode.equals("")) pMode = COMMUNITY;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,"커뮤니티").link(model);
		return forward(request, model, "/community/commSubBoardWrite.jsp");
	}


	/**
	 * 통합보드 글 보여주기
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commSubBoardShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		log.debug("__________________________________ commSubBoardShow Start");
		String		systemCode	= 	UserBroker.getSystemCode(request);
		String		userType	=	UserBroker.getUserType(request);
		String		userId		=	UserBroker.getUserId(request);
		CommMemDTO	commMemDto	=	UserBroker.getCommInfo(request);
		String 		commName 	= 	commMemDto.commName;
		String 		commId 		= 	commMemDto.commId;
		String 		userLevel 	= 	commMemDto.userLevel;
		model.put("commName", commName);
		model.put("userLevel", userLevel);

		int			pCommId		= 	Integer.parseInt(request.getParameter("pCommId"));
		int			pBbsId		= 	Integer.parseInt(request.getParameter("pBbsId"));
		String		pBbsType 	= 	request.getParameter("pBbsType");
		int			pSeqNo 		= 	Integer.parseInt(request.getParameter("pSeqNo"));

		CommInfoDAO commInfoDao = new CommInfoDAO();
		RowSet	infoRs 		= commInfoDao.getCommBbsInfo(systemCode, pBbsId, commId);
		infoRs.next();
		String	bbsName 		=	infoRs.getString("bbs_name");
		String	pCommentUseYn 	=	infoRs.getString("comment_use_yn");

		model.put("pCommId", String.valueOf(pCommId));
		model.put("pBbsId", String.valueOf(pBbsId));
		model.put("pBbsType", infoRs.getString("bbs_type"));
		model.put("pFileUseYn", infoRs.getString("file_use_yn"));
		model.put("pCommentUseYn", pCommentUseYn);
		infoRs.close();

		CommBbsInfoDAO		commBbsInfoDao	=	new CommBbsInfoDAO();
		CommBbsContentsDTO	contentsDto 	=	commBbsInfoDao.getCommPrideContents(systemCode, commId, pBbsId, pSeqNo);
		model.put("contentsDto", contentsDto);

		String AddWhere = "";

		//-- 삭제시 하위글 있는지 체크하기위해서 아래 글 갯수를 가져온다.
		int pChildCnt = 0;
		AddWhere = "parent_no=" + pSeqNo;
		pChildCnt = commBbsInfoDao.getCommBbsContentsCount(systemCode, commId, pBbsId, AddWhere);
		model.put("pChildCnt", String.valueOf(pChildCnt));

		//		-- 조회수 증가 시키기
		int retVal = 0;
		if(!userType.equals("M") || !userId.equals(StringUtil.nvl(contentsDto.getRegId()))) {
			retVal = commBbsInfoDao.hitUpBbsContents(systemCode, pCommId, pBbsId, pSeqNo);
		}

		model.put("curPage", StringUtil.nvl(request.getParameter("curPage"),"1"));
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if (pMode.equals("")) pMode = COMMUNITY;
		model.put("pMode", pMode);
		model.put("bbsName", bbsName);

		new SiteNavigation(pMode).add(request,"커뮤니티").link(model);
		return forward(request, model, "/community/commSubBoardShow.jsp");
	}

	/**
	 * 통합보드 수정
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commSubBoardContentsEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		log.debug("__________________________________ comSubBoardContentsEdit Start");

		String systemCode = UserBroker.getSystemCode(request);

		/* setting value loadding */
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String commName = commMemDto.commName;
		String commId = commMemDto.commId;
		String userLevel = commMemDto.userLevel;

		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));
		String pBbsType = request.getParameter("pBbsType");

		CommInfoDAO commInfoDao = new CommInfoDAO();

		RowSet infoRs = commInfoDao.getCommBbsInfo(systemCode, pBbsId, commId);
		infoRs.next();

		model.put("pCommId", commId);
		model.put("commName", commName);

		model.put("pBbsId", String.valueOf(pBbsId));
		model.put("pBbsType", infoRs.getString("bbs_type"));
		model.put("pFileUseYn", infoRs.getString("file_use_yn"));
		model.put("pEditorYn", infoRs.getString("editor_yn"));
		model.put("bbsName", infoRs.getString("bbs_name"));

		CommBbsInfoDAO commbbsInfoDao = new CommBbsInfoDAO();

		CommBbsContentsDTO contentsDto = commbbsInfoDao.getCommPrideContents(systemCode, commId, pBbsId, pSeqNo);
		model.put("contentsDto", contentsDto);
		CommBbsContentsDTO contentsDto2 = commbbsInfoDao.getCommPrideContents(systemCode, commId, pBbsId, pSeqNo);

		String expireDate = StringUtil.nvl(contentsDto2.getExpireDate());
		log.debug("+++++++++" + StringUtil.nvl(contentsDto2.getExpireDate()));

		if (expireDate.equals("000000")) expireDate = "";
		DateSetter ds = new DateSetter((String) contentsDto2.getExpireDate()).link(model);
		model.put("ds", ds);
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if (pMode.equals("")) pMode = COMMUNITY;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,"커뮤니티").link(model);
		log.debug("__________________________________ comSubBoardContentsEdit End");
		return forward(request, model, "/community/commSubBoardEdit.jsp");
	}

	/**
	 * 통합보드 글 지우기
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commSubBoardDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		log.debug("===========================commSubBoardDelete start");

		String systemCode = UserBroker.getSystemCode(request);
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String commName = commMemDto.commName;
		String commId = commMemDto.commId;
		String userLevel = commMemDto.userLevel;

		int pCommId = Integer.parseInt(request.getParameter("pCommId"));
		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));
		String pContents = StringUtil.nvl(request.getParameter("pContents"));
		String pBbsType = request.getParameter("pBbsType");

		CommInfoDAO commInfoDao = new CommInfoDAO();
		CommBbsInfoDAO commbbsInfoDao = new CommBbsInfoDAO();

		CommBbsContentsDTO contentsDto = commbbsInfoDao.getCommPrideContents(systemCode, commId, pBbsId, pSeqNo);

		String rfile_name = "";
		String sfile_name = "";
		String filePath = "";

		String msg = "삭제하였습니다.";
		String returnUrl = "/CommSubBoard.cmd?cmd=commSubBoard&MENUNO=0&pCommId=" + pCommId + "&pBbsId=" + pBbsId + "&pSeqNo=" + pSeqNo + "&pBbsType=" + pBbsType;

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
			String FilePath = FileUtil.FILE_DIR + systemCode + "/commSub/" + commId + "/" + pBbsId + "/" + RegMonth + "/";//-- 게시판아이디 + 년월
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
			returnUrl = "/CommSubBoard.cmd?cmd=commSubBoardShow&pCommId=" + pCommId + "&pBbsId=" + pBbsId + "&pSeqNo=" + pSeqNo + "&pBbsType=" + pBbsType;
		}

        String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if (pMode.equals("")) pMode = COMMSUB;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,"커뮤니티").link(model);
		log.debug("===========================bbsContentsDelete end");
		return notifyAndExit(systemCode, model, msg, returnUrl, COMMSUB);
	}

	/**
	 * 통합보드 등록/수정 /답변
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commSubBoardRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);

		/* setting value loadding */
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		//String commName = StringUtil.nvl(commMemDto.commName);
		String commId = StringUtil.nvl(commMemDto.commId);
		String userLevel = StringUtil.nvl(commMemDto.userLevel);

		int pBbsId = Integer.parseInt(StringUtil.nvl(request.getParameter("pBbsId"), "0"));
		int pSeqNo = Integer.parseInt(StringUtil.nvl(request.getParameter("pSeqNo"), "1"));
		String pBbsType = StringUtil.nvl(request.getParameter("pBbsType"));

		CommInfoDAO commBbsDao = new CommInfoDAO();
		RowSet infoRs = commBbsDao.getCommBbsInfo(systemCode, pBbsId, commId);
		model.put("infoRs", infoRs);
		infoRs.next();
		String bbsName = StringUtil.nvl(infoRs.getString("bbs_name"));

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
			pSeqNo = commBbsInfoDao.getMaxSeqNo(systemCode, commId, pBbsId);
		}

		String RegMonth = CommonUtil.getCurrentDate().substring(0, 6);
		if (regMode.equals("Edit")) {//-- 수정모드시는 현재 월이 아닌 등록된 글의 년월을 가져온다.
			RegMonth = StringUtil.nvl(request.getParameter("pRegDate")).substring(0, 6);
		}

		String FilePath = FileUtil.FILE_DIR + systemCode + "/commSub/" + commId + "/" + pBbsId + "/" + RegMonth + "/";//-- 게시판아이디 + 년월


		if (systemCode.equals("")) systemCode = StringUtil.nvl(request.getParameter("pSystemCode"));;
		if (regId.equals("")) regId = StringUtil.nvl(request.getParameter("pUserId"));

		//			  파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath, pSeqNo + "_" + regId);
		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();
		String status = uploadEntity.getStatus();

		//String pFile[] = new String[4];
		String pOldrFileName[] = new String[4];
		String pOldsFileName[] = new String[4];
		String pOldFilePath[] = new String[4];
		String pOldFileSize[] = new String[4];
		for (int i = 1; i <= 3; i++) {
			//pFile[i] = StringUtil.nvl(multipartRequest.getParameter("pFile["+i+"]"));
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
						FileUtil.delFile(pOldFilePath[idx], pOldsFileName[idx]);
					}
				}
			}// For End
		}

		/** 웹에디터 셋팅 추가 Start**/
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH + FilePath;
		String WeasFileUrl = ServiceUrl + "/data/" + FilePath;

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

		String arrTarget[] = null;
		arrTarget = multipartRequest.getParameterValues("pTarget");
		if (arrTarget!=null) {
			for (int i = 0; i < arrTarget.length; i++) {
				pTarget = pTarget + arrTarget[i];
			}
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
		String userName = UserBroker.getUserName(request);

		if (userName.equals("")) userName = StringUtil.nvl(multipartRequest.getParameter("pRegName"));
		String regPasswd = StringUtil.nvl(multipartRequest.getParameter("pRegPasswd"));
		String regEmail = StringUtil.nvl(multipartRequest.getParameter("pRegEmail"));
		commBbsInfoDto.setSystemCode(systemCode);
		commBbsInfoDto.setCommId(Integer.parseInt(commId));
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

		///community/commPridePagingList.jsp
		String returnUrl = "/CommSubBoard.cmd?cmd=commSubBoard&MENUNO=0&pBbsId=" + pBbsId;

		if (regMode.equals("Add"))// 입력모드
		{
			retVal = commBbsInfoDao.addcommSubNoticeRegister(commBbsInfoDto);
			msg = "등록완료";
		} else if (regMode.equals("Edit")) {
			retVal = commBbsInfoDao.editPrideRegister(commBbsInfoDto);
			msg = "수정완료";
			if (retVal <= 0) {
				returnUrl = "/CommSubBoard.cmd?cmd=bbsContentsEdit&pBbsId=" + pBbsId + "&pSeqNo=" + pSeqNo;
				msg = "수정오류 다시 진행해 주세요";
			}
		} else if (regMode.equals("Reply")) {
			boolean bVal = commBbsInfoDao.replyUpdateBbsContents(commBbsInfoDto, "Ins");
			if (bVal)
				retVal = 1;
			else
				retVal = 0;
			if (retVal > 0) {
				retVal = commBbsInfoDao.addcommSubBoardRegister(commBbsInfoDto);
				msg = "등록완료";
			}
			if (retVal <= 0) {
				returnUrl = "/CommSubBoard.cmd?cmd=commSubBoardWrite&pBbsId=" + pBbsId + "&pSeqNo=" + pSeqNo + "pBbsType=" + pBbsType;
				msg = "등록오류 다시 진행해 주세요";
			}
		}

		model.put("pMode", pMode);
		model.put("pBbsType", pBbsType);

		new SiteNavigation(pMode).add(request,"커뮤니티").link(model);
		return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
	}

	/**
	 * 통합보드 데이터 로딩
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commSubBoard(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		int pBbsId = StringUtil.nvl(request.getParameter("pBbsId"), 0);
		String pMode = StringUtil.nvl(request.getParameter("pMode"));

		/* 커뮤니티 정보의 로딩 */
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String commName = commMemDto.commName;
		String commId = commMemDto.commId;
		String userLevel = commMemDto.userLevel;
		/*-----------------*/

		if (userType.equals("")) userType = "G";

		CommInfoDAO commInfoDao = new CommInfoDAO();
		CommBbsInfoDAO commbbsInfoDao = new CommBbsInfoDAO();
		RowSet infoRs = commInfoDao.getCommBbsInfo(systemCode, pBbsId, commId);
		model.put("infoRs", infoRs);
		infoRs.next();
		String pBbsType = infoRs.getString("bbs_type");
		String bbsName = infoRs.getString("bbs_name");
		pBbsId = infoRs.getInt("bbs_id");

		model.put("commId", commId);
		model.put("pBbsId", "" + pBbsId);
		model.put("commName", commName);
		model.put("userLevel", userLevel);
		model.put("pBbsType", pBbsType); //게시판의 타입
		model.put("bbsName", bbsName);
		model.put("pMode", pMode);

		log.debug("============================= cpmmSubBoard end1");
		new SiteNavigation(COMMSUB).add(request,"커뮤니티").link(model);
		return forward(request, model, "/community/commSubBoard.jsp");
	}

	/**
	 * [2007.8.16] 커뮤니티 게시판 AJAX 적용
	 * @param curPage
	 * @param commId
	 * @param bbsId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO subBoardListAuto(int curPage, String commId, int bbsId, String pSearchKey, String pKeyWord, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String	systemCode	=	UserBroker.getSystemCode(request);
		String	pMode		=	StringUtil.nvl(request.getParameter("pMode"));
		String	userType	=	UserBroker.getUserType(request);
		if (userType.equals("")) userType = "G";
		curPage = (curPage == 0) ? 1 : curPage;

		/* 커뮤니티 정보의 로딩 */
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String commName = commMemDto.commName;
		String userLevel = commMemDto.userLevel;
		/*-----------------*/

		ListDTO bbsContentsList = null;
		CommInfoDAO commInfoDao = new CommInfoDAO();
		CommBbsInfoDAO commbbsInfoDao = new CommBbsInfoDAO();

		RowSet infoRs = commInfoDao.getCommBbsInfo(systemCode, bbsId, commId);
		infoRs.next();
		String pBbsType = infoRs.getString("bbs_type");
		String bbsName = infoRs.getString("bbs_name");
		int dispLine = infoRs.getInt("disp_line");
		int dispPage = infoRs.getInt("disp_page");

		String pExpireDate = "";
		String pOrder = "";

		AjaxListDTO	ajaxListDto	=	new AjaxListDTO();
		//	데이타를 담는다.
		ListDTO listObj = null;
		listObj = commbbsInfoDao.getCommSubBoardPagingList(curPage, dispLine, dispPage, systemCode, commId, userType, pExpireDate, pSearchKey, pKeyWord, pOrder, bbsId);

		//--	일반 게시글
		ArrayList		dataList	=	new ArrayList();
		CommBbsContentsDTO	bbsDto		=	null;
		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				bbsDto	=	new CommBbsContentsDTO();
				bbsDto.setCommId(rs.getInt("comm_id"));
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
				//bbsDto.setRegName(StringUtil.nvl(rs.getString("reg_name")));
				bbsDto.setNickName(StringUtil.nvl(rs.getString("nick_name")));
				bbsDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				bbsDto.setCommCnt(rs.getInt("comm_cnt"));

				dataList.add(bbsDto);
			}
			rs.close();
		}
		ajaxListDto.setTotalDataCount(listObj.getStartPageNum());				// 전체 글 수
		ajaxListDto.setDataList(dataList);										// 데이타리스트
		ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링

		return ajaxListDto;
	}
}

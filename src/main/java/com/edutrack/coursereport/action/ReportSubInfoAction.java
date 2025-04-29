/*
 * Created on 2004. 10. 14.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.coursereport.action;

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
import com.edutrack.common.CurriSiteNavigation;
import com.edutrack.common.DateSetter;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.courseexam.action.ExamHelper;
import com.edutrack.courseexam.dao.ExamAdminDAO;
import com.edutrack.courseexam.dto.ExamInfoDTO;
import com.edutrack.coursereport.dao.ReportAdminDAO;
import com.edutrack.coursereport.dao.ReportBankDAO;
import com.edutrack.coursereport.dao.ReportSubInfoDAO;
import com.edutrack.coursereport.dto.ReportBankContentsDTO;
import com.edutrack.coursereport.dto.ReportSubInfoDTO;
import com.edutrack.coursereport.dto.ReportSendDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportSubInfoAction extends StrutsDispatchAction{
	/**
	 * 상위 과제별 과제리스트(Ajax)--------###############################
	 * 2007.06.13 sangsang
	 * @param courseId
	 * @param bankId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList reportSubInfoListAuto(String courseId, String reportId, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String		systemCode		=	UserBroker.getSystemCode(request);
		UserMemDTO	userInfo		=	UserBroker.getUserInfo(request);
		CurriMemDTO	curriInfo		=	UserBroker.getCurriInfo(request);

		courseId = StringUtil.nvl(courseId);
		int tempReportId = StringUtil.nvl(reportId,0);

		// 리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		ReportSubInfoDAO reportSubInfoDao 	= new ReportSubInfoDAO();
		ReportSubInfoDTO reportSubInfoDTO	= null;

		RowSet rs = reportSubInfoDao.getReportSubInfoList(systemCode, userInfo.curriInfo, courseId, tempReportId);
		while(rs.next()){
			reportSubInfoDTO	= 	new ReportSubInfoDTO();
			reportSubInfoDTO.setCourseId(courseId);
			reportSubInfoDTO.setReportId(tempReportId);
			reportSubInfoDTO.setSubReportId(rs.getInt("sub_report_id"));
			reportSubInfoDTO.setSubReportSubject(StringUtil.setMaxLength(StringUtil.nvl(rs.getString("sub_report_subject")),60));
			reportSubInfoDTO.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
			reportSubInfoDTO.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
			reportSubInfoDTO.setFilePath(StringUtil.nvl(rs.getString("file_path")));
			reportSubInfoDTO.setFileSize(StringUtil.nvl(rs.getString("file_size")));

			arrayList.add(reportSubInfoDTO);
		}
		rs.close();
		return arrayList;
	}


	/**
	 * 과제 등록, 수정 폼을 띄워준다.(팝업)-----###########################
	 * 2007.06.13 sangsang
	 * @param courseId
	 * @param bankId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward subReportWriteForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		UserMemDTO	userInfo		=	UserBroker.getUserInfo(request);
		CurriMemDTO	curriInfo		=	UserBroker.getCurriInfo(request);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pReportId = StringUtil.nvl(request.getParameter("pReportId"),0);
		int pSubReportId = StringUtil.nvl(request.getParameter("pSubReportId"),0);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));

		ReportSubInfoDTO reportSubInfo = null;

		if(pMODE.equals("ADD")){
			reportSubInfo = new ReportSubInfoDTO();
		}else{
			ReportSubInfoDAO reportSubInfoDao = new ReportSubInfoDAO();
			reportSubInfo = reportSubInfoDao.getReportSubInfo(systemCode, userInfo.curriInfo, pCourseId, pReportId, pSubReportId);
		}

		model.put("reportSubInfo",reportSubInfo);
        model.put("pCourseId", pCourseId);
        model.put("pReportId", String.valueOf(pReportId));
        model.put("pSubReportId", String.valueOf(pSubReportId));
        model.put("pMODE", pMODE);

		return forward(request, model, "/coursereport/report_sub_info_write.jsp");
	}


	/**
	 * 과제 문제를 등록/수정한다. -----------###################################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportSubInfoRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);

		UserMemDTO userInfo = UserBroker.getUserInfo(request);

		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		ReportSubInfoDAO reportSubInfoDao = new ReportSubInfoDAO();
		ReportSubInfoDTO reportSubInfoDto =  new ReportSubInfoDTO();
		ReportHelper helper = new ReportHelper();

		MultipartRequest multipartRequest = null;

		String filePath = FileUtil.FILE_DIR+systemCode+"/coursereport/reportSubInfo/"+curriInfo.curriCode+"/"+curriInfo.curriYear+"/"+curriInfo.curriTerm+"/";

		UploadFiles uploadEntity = FileUtil.upload(request, filePath,curriInfo.curriCode+curriInfo.curriYear+curriInfo.curriTerm);
		multipartRequest		=	uploadEntity.getMultipart();

		String pMODE = StringUtil.nvl(multipartRequest.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(multipartRequest.getParameter("pCourseId"));
		String pImsiReportId = multipartRequest.getParameter("pReportId");
		String pImsiSubReportId = multipartRequest.getParameter("pSubReportId");
		String pSubject = StringUtil.nvl(multipartRequest.getParameter("pSubject"));

		String pBankInfoYn =	StringUtil.nvl(multipartRequest.getParameter("pBankInfoYn"));
		String pImsiBankId =	StringUtil.nvl(multipartRequest.getParameter("pBankId"));

		int pReportId = 0;
		int pSubReportId = 0;
		int pBankId = 0;

		if(pImsiReportId != null && !pImsiReportId.equals("")) pReportId = Integer.parseInt(pImsiReportId);
		if(pImsiSubReportId != null && !pImsiSubReportId.equals("")) pSubReportId = Integer.parseInt(pImsiSubReportId);
		if(pImsiBankId != null && !pImsiBankId.equals("")) pBankId = Integer.parseInt(pImsiBankId);

		//old 파일 정보 DTO에 담기
		helper.getReportSubInfoParam(multipartRequest, reportSubInfoDto);

		reportSubInfoDto.setCourseId(pCourseId);
		reportSubInfoDto.setReportId(pReportId);
		reportSubInfoDto.setSubReportId(pSubReportId);
		String status = uploadEntity.getStatus();
        String rFileName = "",sFileName="";
        long fileSize=0;
		if(status.equals("S")){
			//--	업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			ArrayList files		=	uploadEntity.getFiles();
			UploadFile file		=	null;
			String objName      = "";
			for(int i = 0 ; i < files.size(); i++){
				file			=	(UploadFile)files.get(i);
				objName         = file.getObjName();
                if(objName.equals("pFile")){
					sFileName		=	file.getUploadName();
					rFileName       =   file.getRootName();
					fileSize        =   file.getSize();
                }
			}
		}

		if(!rFileName.equals("")){
			reportSubInfoDto.setRfileName(rFileName);
			reportSubInfoDto.setSfileName(sFileName);
			reportSubInfoDto.setFileSize(""+fileSize);
			reportSubInfoDto.setFilePath(filePath);

		}else{
            if(reportSubInfoDto.getFileCheck().equals("")){
            	reportSubInfoDto.setRfileName(reportSubInfoDto.getOldRfile());
            	reportSubInfoDto.setSfileName(reportSubInfoDto.getOldSfile());
            	reportSubInfoDto.setFileSize(reportSubInfoDto.getOldFileSize());
            	reportSubInfoDto.setFilePath(reportSubInfoDto.getOldFilePath());
            }

		}

		String pContents = "";
		String contentsText = "";

		pContents	=	StringUtil.nvl(multipartRequest.getParameter("pContents"));
		if( new String("true").equals(multipartRequest.getParameter("VBN_FORM_WEAS")) == true ){//-- 웹에디터 사용시
			contentsText = StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
		}else{//-- 웹에디터 사용 안할경우
			contentsText = pContents;
		}

		/** 웹에디터 셋팅 추가 Start**/
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH+filePath;
		String WeasFileUrl =  ServiceUrl+"/data/"+filePath;
		VBN_files v_objFile = null;
		java.io.File dir = new java.io.File(WeasFilePath);
		if (!dir.exists())	dir.mkdirs();

		v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
		pContents = v_objFile.VBN_uploadMultiFiles(pContents, multipartRequest);
		/** 웹에디터 셋팅 추가 End**/


		reportSubInfoDto.setSubReportSubject(pSubject);
		reportSubInfoDto.setSubReportContents(pContents);

		int retVal = 0;
		String msg = "";
		if(pMODE.equals("ADD")){
			reportSubInfoDto.setRegId(userInfo.userId);
			retVal = reportSubInfoDao.addReportSubInfo(systemCode, curriInfo, reportSubInfoDto);

			//문제 은행에 문제 자동 등록시
			if(retVal > 0 && pBankInfoYn.equals("Y")) {
				ReportBankDAO contentsDao = new ReportBankDAO();
				ReportBankContentsDTO contentsDto = new ReportBankContentsDTO();

				contentsDto.setCourseId(pCourseId);
				contentsDto.setReportBankId(pBankId);
				contentsDto.setReportSubject(pSubject);
				contentsDto.setReportContents(pContents);
				contentsDto.setRfileName(rFileName);
				contentsDto.setSfileName(sFileName);
				contentsDto.setFileSize(""+fileSize);
				contentsDto.setFilePath(filePath);

				contentsDao.addReportBankContentsInfo(systemCode, contentsDto);
			}
			if(retVal > 0) {
				msg = "저장되었습니다.";
			}
		}else{
			reportSubInfoDto.setModId(userInfo.userId);
			retVal = reportSubInfoDao.editReportSubInfo(systemCode, curriInfo, reportSubInfoDto);
			if(retVal > 0) {
				msg = "수정되었습니다.";
			}
		}
		return closePopupReload(systemCode, model, msg, "O", "POPUP");
	}

	/**
	 * 과제 첨부파일 삭제(Ajax) -##################################
	 * 2007.06.14 sangsang
	 * @param courseId
	 * @param bankId
	 * @param reportNo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String reportSubInfoFileDelete(String courseId, int reportId, int subReportId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		ReportSubInfoDAO reportSubInfoDao = new ReportSubInfoDAO();
		int retVal = 0;
		retVal = reportSubInfoDao.delReportSubInfoFile(systemCode, curriInfo, courseId, reportId, subReportId, userId);

		return  String.valueOf(retVal);
	}


	/**
	 * 과제 하위 정보 삭제 -##########################################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportSubInfoDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
		HttpServletResponse httpServletResponse, Map model) throws Exception {

		UserMemDTO	userInfo		=	UserBroker.getUserInfo(request);
		CurriMemDTO	curriInfo		=	UserBroker.getCurriInfo(request);

		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);

		String	systemCode			= 	UserBroker.getSystemCode(request);
		String	pMODE 				= 	StringUtil.nvl(request.getParameter("pMODE"));
		String	pCourseId 			= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int		pReportId 			= 	StringUtil.nvl(request.getParameter("pReportId"),0);
		int		pSubReportId 		= 	StringUtil.nvl(request.getParameter("pSubReportId"),0);
		int 	retVal 		= 	0;

		ReportSubInfoDAO	reportSubInfoDao	=	new ReportSubInfoDAO();

		retVal		=	reportSubInfoDao.delReportSubInfo(systemCode, curriInfo, pCourseId, pReportId, pSubReportId);

		String	msg 	=	"";

		if(retVal > 0) {
			msg = "삭제 되었습니다.";
		}

		return alertPopupCloseResponse(systemCode, model, msg, pMode);
	}


	/**
	 * 과제물 정보 리스트 페이지로 이동을 합니다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportInfoList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriterm = StringUtil.nvl(curriMemDto.curriTerm,0);

		//-- 일반변수들 값을 받아옵니다
		String pSystemCode  = 	UserBroker.getSystemCode(request);
		String pCourseId	= 	StringUtil.nvl(request.getParameter("pCourseId"));

		//과제평가 모드일 경우
		String pRtype		=	StringUtil.nvl(request.getParameter("pRtype"),"I");

		ReportSubInfoDAO reportinfoDao	=	new ReportSubInfoDAO();

		model.put("pCourseId", pCourseId);
		new SiteNavigation(LECTURE).add(request,"과제관리").link(model);
		return forward(request, model, "/report/reportInfoList.jsp");

	}

	/**
	 * 과제물 정보 리스트 페이지로 이동을 합니다.(학생)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportInfoStuList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriterm = StringUtil.nvl(curriMemDto.curriTerm,0);

		//-- 일반변수들 값을 받아옵니다
		String pSystemCode  = 	UserBroker.getSystemCode(request);
		String pUserId		=	UserBroker.getUserId(request);

		ReportSubInfoDAO reportinfoDao	=	new ReportSubInfoDAO();

		new SiteNavigation(LECTURE).add(request,"과제방").link(model);
		return forward(request, model, "/report/reportInfoStuList.jsp");
	}

	/**
	 * 과제물을 추가하는 페이지로 이동을 합니다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportInfoWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		//-- 과정정보 가져오기 시작
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriterm = StringUtil.nvl(curriMemDto.curriTerm,0);

		//-- 일반변수들 값을 받아옵니다
		String pRegMode			=	StringUtil.nvl(request.getParameter("pRegMode"));
		String pSystemCode 		= 	UserBroker.getSystemCode(request);
		String pCourseId		= 	StringUtil.nvl(request.getParameter("pCourseId"));

		//-- 팀생성
		String pTeamMode		=	StringUtil.nvl(request.getParameter("pTeamMode"));

		ReportSubInfoDAO reportinfoDao	=	new ReportSubInfoDAO();

		ReportSendDTO reportsendDto=	new ReportSendDTO();

		new SiteNavigation(LECTURE).add(request,"과제관리").link(model);

		DateSetter ds = new DateSetter(DateTimeUtil.getDate(), DateTimeUtil.getDate()).link(model);
		DateSetter ds1 = new DateSetter(DateTimeUtil.getDate()).link(model);

		int parReportId = 0;
		int reportid = 0;

		if(!StringUtil.nvl(request.getParameter("pParReportId")).equals("")){
			parReportId = Integer.parseInt(request.getParameter("pParReportId"));
		}

		if(!StringUtil.nvl(request.getParameter("pReportId")).equals("")){
			reportid = Integer.parseInt(request.getParameter("pReportId"));
		}

		if(pTeamMode.equals("T") && pRegMode.equals("W")){
			reportid = parReportId;
		}

		if(pRegMode.equals("E") || pTeamMode.equals("T")){

			//과제제출수
		    reportsendDto.setSystemCode(pSystemCode);
		    reportsendDto.setCurriCode(pCurriCode);
		    reportsendDto.setCurriYear(pCurriYear);
		    reportsendDto.setCurriTerm(pCurriterm);
		    reportsendDto.setCourseId(pCourseId);
		    reportsendDto.setReportId(reportid);

			model.put("pReportId",Integer.toString(reportid));
			model.put("pParReportId",Integer.toString(parReportId));
		}

		model.put("pRegMode",pRegMode);
		model.put("pCourseId", pCourseId);
		model.put("ds", ds);
		model.put("ds1", ds1);

		if(pTeamMode.equals("T")){	//팀생성일경우
			return forward(request, model, "/report/reportInfoTeamWrite.jsp");
		}else{
			return forward(request, model, "/report/reportInfoWrite.jsp");
		}

	}

	/**
	 * 과제물 정보를 추가/수정 합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportInfoRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		//-- 과정정보 가져오기 시작
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriterm = StringUtil.nvl(curriMemDto.curriTerm,0);

		String  pSystemCode  	=   UserBroker.getSystemCode(request);
		String  pUserId			= 	UserBroker.getUserId(request);
		String	pRegMode		=	StringUtil.nvl(request.getParameter("pRegMode"));
		String  pCourseId		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		String  pParReportId 	=   StringUtil.nvl(request.getParameter("pParReportId"));
		String  pReportId 		=   StringUtil.nvl(request.getParameter("pReportId"));
		//-- 팀생성
		String pTeamMode		=	StringUtil.nvl(request.getParameter("pTeamMode"),"N");

		ReportSubInfoDAO reportinfoDao	=	new ReportSubInfoDAO();
		//ReportInfoDTO reportinfoDto =	new ReportInfoDTO();

		int parReportId = 0;
		int reportid = 0;
		int freportid = 0;

		if(!pParReportId.equals("") && !pParReportId.equals("null")){
			parReportId = Integer.parseInt(pParReportId);
			freportid =parReportId;
		}

		if(!pReportId.equals("") && !pReportId.equals("null")){
			reportid = Integer.parseInt(pReportId);
			freportid = reportid;
		}

		if(pTeamMode.equals("T")){
			if(pRegMode.equals("W")){
				reportid = parReportId;
			}
			freportid = parReportId;
		}else if( pTeamMode.equals("N")&& pRegMode.equals("W")){

		}

		MultipartRequest multipartRequest = null;

		String FilePath = FileUtil.FILE_DIR+pSystemCode+"/report/"+pCurriCode+"/"+pCurriYear+"_"+pCurriterm+"/"+pCourseId+"/"+freportid+"/info/";

		// 파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request,FilePath,pUserId);

		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();

		String status = uploadEntity.getStatus();

		String pFILE_NEW1_ori 	= 	StringUtil.nvl(multipartRequest.getParameter("pFILE_NEW1_ori"));

		String  pReportType		= 	StringUtil.nvl(multipartRequest.getParameter("pReportType"));
		String  pSubject		= 	StringUtil.nvl(multipartRequest.getParameter("pSubject"));
		String  pContents		= 	StringUtil.nvl(multipartRequest.getParameter("pContents"));
		String  pStartDate 	 	=   StringUtil.nvl(multipartRequest.getParameter("pDate1"));
		String  pEndDate 	 	=   StringUtil.nvl(multipartRequest.getParameter("pDate2"));
		String  pExDate 	 	=   StringUtil.nvl(multipartRequest.getParameter("pExDate1"));
		int 	pReportScore	=	Integer.parseInt(multipartRequest.getParameter("pReportScore"));
		String  pOpenYn 	 	=   StringUtil.nvl(multipartRequest.getParameter("pOpenYn"));
		String  pRegistYn 	 	=   StringUtil.nvl(multipartRequest.getParameter("pRegistYn"));
		String  pEditorYn 	 	=   StringUtil.nvl(multipartRequest.getParameter("pEditorYn"));
		String  pVoiceYn 	 	=   StringUtil.nvl(multipartRequest.getParameter("pVoiceYn"));

		//팀과제일 경우
		if(pRegMode.equals("W") && pReportType.equals("T")){
			pRegistYn = "N";
		}

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

		new SiteNavigation(LECTURE).add(request,"과제관리").link(model);

		int		retVal			=	0;

		String returnurl = "";
	    String msg = "";

	    if(pTeamMode.equals("T")){
	    	returnurl = "/ReportInfo.cmd?cmd=reportInfoWrite&pRegMode="+pRegMode+"&pTeamMode=T&pCourseId="+pCourseId+"&pParReportId="+parReportId;
	    }else{
	    	returnurl = "/ReportInfo.cmd?cmd=reportInfoWrite&pRegMode="+pRegMode+"&pCourseId="+pCourseId;
	    }
	    msg = "Error!!과제정보를 등록 하는데 에러가 발생하였습니다.";

		if(pRegMode.equals("W")){

			if (retVal > 0) {
				if(pTeamMode.equals("T")){
					returnurl = "/ReportInfo.cmd?cmd=reportInfoWrite&pCourseId="+pCourseId+"&pRegMode=E&pReportId="+reportid;
				}else{
					returnurl = "/ReportInfo.cmd?cmd=reportInfoList&pCourseId="+pCourseId;
				}
				msg = "과제물 정보를  등록하였습니다.";
			}
			return notifyAndExit(pSystemCode, model, msg, returnurl, LECTURE);
		}else if(pRegMode.equals("E")){

			if (retVal > 0) {
				if(pTeamMode.equals("T")){
					returnurl = "/ReportInfo.cmd?cmd=reportInfoWrite&pCourseId="+pCourseId+"&pRegMode=E&pReportId="+parReportId;
				}else{
					returnurl = "/ReportInfo.cmd?cmd=reportInfoList&pCourseId="+pCourseId;
				}
				msg = "과제물 정보를 수정하였습니다.";
			}else {
				if(pTeamMode.equals("T")){
					returnurl = "/ReportInfo.cmd?cmd=reportInfoWrite&pTeamMode=T&pCourseId="+pCourseId+"&pRegMode="+pRegMode+"&pReportId="+parReportId;
				}else{
					returnurl = "/ReportInfo.cmd?cmd=reportInfoWrite&pCourseId="+pCourseId+"&pRegMode="+pRegMode+"&pReportId="+reportid;
				}
				msg = "Error!!과제물 정보를 수정 하는데 에러가 발생하였습니다.";
			}
			return notifyAndExit(pSystemCode, model, msg, returnurl, LECTURE);

		}else{
			return notifyAndExit(pSystemCode, model, msg, returnurl, LECTURE);
		}
	}


	/**
	 * 과제물 정보를 삭제합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportInfoDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		//-- 과정정보 가져오기 시작
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriterm = StringUtil.nvl(curriMemDto.curriTerm,0);

		String  pSystemCode		=	UserBroker.getSystemCode(request);
		String  pCourseId		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int		reportid		=	Integer.parseInt(request.getParameter("pReportId"));
		String	pRfileName		=   StringUtil.nvl(request.getParameter("pRFILENAME"));
		String	pSfileName		= 	StringUtil.nvl(request.getParameter("pSFILENAME"));
		String  pFilePath		= 	StringUtil.nvl(request.getParameter("pFILEPATH"));
		String  pCONTENTS 		= 	StringUtil.nvl(request.getParameter("pCONTENTS"));

		//-- 팀생성
		String pTeamMode		=	StringUtil.nvl(request.getParameter("pTeamMode"),"");

		int parReportId = 0;

		//팀일경우
		if(pTeamMode.equals("T")){
			parReportId = Integer.parseInt(request.getParameter("pParReportId"));
		}else{
			parReportId = reportid;
		}

		//----------- 웹에디터 시작 ----------------------------------
		String FilePath = FileUtil.FILE_DIR+pSystemCode+"/report/"+pCurriCode+"/"+pCurriYear+"_"+pCurriterm+"/"+pCourseId+"/"+parReportId+"/info/";
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH+FilePath;
		String WeasFileUrl =  ServiceUrl+"/data/"+FilePath;
		//----------- 웹에디터 끝 ------------------------------------

		ReportSubInfoDAO reportinfoDao	=	new ReportSubInfoDAO();
		//ReportInfoDTO reportinfoDto =	new ReportInfoDTO();

		int		retVal			=	0;
		String returnurl = "";
	    String msg = "";

	    if(pTeamMode.equals("T")){
	    	returnurl = "/ReportInfo.cmd?cmd=reportInfoWrite&pRegMode=E&pTeamMode=T&pCourseId="+pCourseId+"&pReportId="+parReportId;
	    }else{
			returnurl = "/ReportInfo.cmd?cmd=reportinfoWrite&pCourseId="+pCourseId+"&pRegMode=E&pReportId="+reportid;
	    }

		msg = "Error!!과제물 정보를  삭제하는데 에러가 발생하였습니다.";

		if(retVal > 0 ) {

			//웹에디터 관련 파일 삭제
			VBN_files v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
			v_objFile.VBN_deleteFiles(pCONTENTS);

			if(!pFilePath.equals("") && !pSfileName.equals("")){
				//파일삭제
				FileUtil.delFile(pFilePath, pSfileName);
				log.debug("파일이 정상적으로 삭제되었습니다.");
			}

			if(pTeamMode.equals("T")){
				returnurl = "/ReportInfo.cmd?cmd=reportInfoWrite&pCourseId="+pCourseId+"&pRegMode=E&pReportId="+parReportId;
			}else{
				returnurl = "/ReportInfo.cmd?cmd=reportInfoList&pCourseId="+pCourseId;
			}

			msg = "과제물 정보를 삭제하였습니다.";
		}
		return notifyAndExit(pSystemCode, model, msg, returnurl, LECTURE);
	}



}

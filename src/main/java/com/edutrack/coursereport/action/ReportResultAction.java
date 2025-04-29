/*
 * Created on 2004. 10. 20.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.coursereport.action;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.VBN.VBN_files;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.CurriSiteNavigation;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.coursereport.dao.ReportAdminDAO;
import com.edutrack.coursereport.dao.ReportBankDAO;
import com.edutrack.coursereport.dao.ReportResultDAO;
import com.edutrack.coursereport.dao.ReportSubInfoDAO;
import com.edutrack.coursereport.dto.ReportBankContentsDTO;
import com.edutrack.coursereport.dto.ReportInfoDTO;
import com.edutrack.coursereport.dto.ReportSendDTO;
import com.edutrack.coursereport.dto.ReportSubInfoDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportResultAction extends StrutsDispatchAction{
	 /**
	 *
	 */
	public ReportResultAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 과제 평가 제출 리스트(교수자)	-######################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */

	public ActionForward reportUserResultList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String 			systemCode 	= 	UserBroker.getSystemCode(request);
		CurriMemDTO		curriInfo 	= 	UserBroker.getCurriInfo(request);
		String 			pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int 			pReportId 	= 	StringUtil.nvl(request.getParameter("pReportId"), 0);
		String			pReportSubject	=	StringUtil.nvl(request.getParameter("pReportSubject"));

		ReportResultDAO	resultDao 		= 	new ReportResultDAO();
		RowSet 			reportSendList 	= 	null;

		reportSendList = resultDao.getReportUserList(systemCode, curriInfo, pCourseId, pReportId);

		model.put("reportSendList", reportSendList);
		model.put("pCourseId",pCourseId);
		model.put("pReportId",String.valueOf(pReportId));
		model.put("pReportSubject",pReportSubject);

		new CurriSiteNavigation(LECTURE).add(request,"제출결과리스트").link(model);

		return forward(request, model, "/coursereport/report_result_st_list.jsp");
	}

	/**
	 * 과제 평가 폼(교수자)-----###########################
	 * 2007.06.13 sangsang
	 * @param courseId
	 * @param bankId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportProfSendWriteForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode 		= UserBroker.getSystemCode(request);
		CurriMemDTO	curriInfo 	= UserBroker.getCurriInfo(request);
		String pCourseId 		= StringUtil.nvl(request.getParameter("pCourseId"));
		int pReportId 			= StringUtil.nvl(request.getParameter("pReportId"),0);
		String pMODE 			= "";
		String pUserId			= StringUtil.nvl(request.getParameter("pUserId"));
		String pUserName		= StringUtil.nvl(request.getParameter("pUserName"));
		String pRegional		= StringUtil.nvl(request.getParameter("pRegional"));
		String pReportSubject	=	StringUtil.nvl(request.getParameter("pReportSubject"));
		String modId 			= UserBroker.getUserId(request);

		ReportResultDAO reportResultDao = null;
		ReportSubInfoDTO reportSubInfo = null;
		ReportSendDTO reportSendDto = null;

		//출제정보 가져오기
		reportResultDao = new ReportResultDAO();
		reportSendDto = reportResultDao.getReportSend(systemCode, curriInfo, pCourseId, pReportId, pUserId);
		reportSubInfo = reportResultDao.getReportSubInfoRandom2(systemCode, curriInfo, pCourseId, pReportId, pUserId);

		String temp = StringUtil.nvl(reportSendDto.getProfMarkDate());

		if(temp.equals("")) {
			pMODE = "ADD";
		} else {
			pMODE = "EDIT";
		}

		model.put("reportSendDto", reportSendDto);
		model.put("reportSubInfo", reportSubInfo);
        model.put("pCourseId", pCourseId);
        model.put("pReportId", String.valueOf(pReportId));
        model.put("pUserId", pUserId);
        model.put("pUserName", pUserName);
        model.put("pRegional", pRegional);
        model.put("pReportSubject",pReportSubject);
        model.put("pMODE", pMODE);

        new CurriSiteNavigation(LECTURE).add(request,"과제평가").link(model);

		return forward(request, model, "/coursereport/report_result_st_write.jsp");
	}

	/**
	 * 평가 정보를 등록 및 수정한다.(교수자) -----------###################################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportProfSendRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);


		UserMemDTO userInfo = UserBroker.getUserInfo(request);

		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		ReportResultDAO reportResultDao = new ReportResultDAO();
		ReportSendDTO reportSendDto =  new ReportSendDTO();
		ReportHelper helper = new ReportHelper();

		MultipartRequest multipartRequest = null;

		String filePath = FileUtil.FILE_DIR+systemCode+"/coursereport/reportProfSend/"+curriInfo.curriCode+"/"+curriInfo.curriYear+"/"+curriInfo.curriTerm+"/";

		UploadFiles uploadEntity = FileUtil.upload(request, filePath,curriInfo.curriCode+curriInfo.curriYear+curriInfo.curriTerm);
		multipartRequest		=	uploadEntity.getMultipart();

		String pMODE 	 = StringUtil.nvl(multipartRequest.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(multipartRequest.getParameter("pCourseId"));
		int pReportId 	 = StringUtil.nvl(multipartRequest.getParameter("pReportId"),0);
		String pReportSubject	=	StringUtil.nvl(multipartRequest.getParameter("pReportSubject"));
		String pUserId	 = StringUtil.nvl(multipartRequest.getParameter("pUserId"), userId);
		int pScore 	 = StringUtil.nvl(multipartRequest.getParameter("pScore"),0);


		//old 파일 정보 DTO에 담기
		helper.getReportProfSendParam(multipartRequest, reportSendDto);

		reportSendDto.setCourseId(pCourseId);
		reportSendDto.setReportId(pReportId);
		reportSendDto.setScore(pScore);
		reportSendDto.setUserId(pUserId);

		String status = uploadEntity.getStatus();

		String rFileName3 = "";
        String sFileName3 ="";
        long fileSize3 = 0;

		if(status.equals("S")){
			//--	업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			ArrayList files		=	uploadEntity.getFiles();
			UploadFile file		=	null;
			String objName      = "";
			for(int i = 0 ; i < files.size(); i++){
				file			=	(UploadFile)files.get(i);
				objName         = file.getObjName();
                if(objName.equals("pFile3")){
					sFileName3		=	file.getUploadName();
					rFileName3      =   file.getRootName();
					fileSize3      =   file.getSize();
                }
			}
		}

		if(!rFileName3.equals("")){
			reportSendDto.setRfileName3(rFileName3);
			reportSendDto.setSfileName3(sFileName3);
			reportSendDto.setFileSize3(String.valueOf(fileSize3));
			reportSendDto.setFilePath3(filePath);

		}else{
            if(reportSendDto.getFileCheck3().equals("")){
            	reportSendDto.setRfileName3(reportSendDto.getOldRfile3());
            	reportSendDto.setSfileName3(reportSendDto.getOldSfile3());
            	reportSendDto.setFileSize3(reportSendDto.getOldFileSize3());
            	reportSendDto.setFilePath3(reportSendDto.getOldFilePath3());
            }
		}

		String pContents = "";
		String contentsText = "";
		String pEditorType = "";

		pContents	=	StringUtil.nvl(multipartRequest.getParameter("pContents"));
		if( new String("true").equals(multipartRequest.getParameter("VBN_FORM_WEAS")) == true ){//-- 웹에디터 사용시
			contentsText = StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
		}else{//-- 웹에디터 사용 안할경우
			contentsText = pContents;
		}

		reportSendDto.setProfComment(pContents);


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


		int retVal = 0;
		String returnUrl = "/ReportResult.cmd?cmd=reportUserResultList&pCourseId="+pCourseId+"&pReportId="+pReportId+"&pReportSubject="+StringUtil.ksc2asc(pReportSubject);
		reportSendDto.setModId(userInfo.userId);
		retVal = reportResultDao.editReportProfSend(systemCode, curriInfo, reportSendDto);

		return redirect(returnUrl);
	}


	/**
	 * 서브과제 정보 및 과제 제출 등록 및 수정 폼(학생)-----###########################
	 * 2007.06.13 sangsang
	 * @param courseId
	 * @param bankId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportStSendWriteForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode 		= UserBroker.getSystemCode(request);
		CurriMemDTO	curriInfo 	= UserBroker.getCurriInfo(request);
		String pCourseId 		= StringUtil.nvl(request.getParameter("pCourseId"));
		int pReportId 			= StringUtil.nvl(request.getParameter("pReportId"),0);
		String pReportType2		= StringUtil.nvl(request.getParameter("pReportType2"));
		String pMODE 			= StringUtil.nvl(request.getParameter("pMODE"));
		String pInsertYn		= StringUtil.nvl(request.getParameter("pInsertYn"));
		String pEndYn			= StringUtil.nvl(request.getParameter("pEndYn"));
		String pMarkCheckYn		= StringUtil.nvl(request.getParameter("pMarkCheckYn"));
		String pSendCheckYn		= StringUtil.nvl(request.getParameter("pSendCheckYn"));
		String pStuOpenDate		= StringUtil.nvl(request.getParameter("pStuOpenDate"));
		String userId 			= UserBroker.getUserId(request);

		String returnUrl 		= "";

		//과제 상위 정보
		ReportInfoDTO reportInfo = null;
		ReportAdminDAO reportDao = new ReportAdminDAO();
		reportInfo = reportDao.getReportInfo(systemCode,curriInfo ,pCourseId,pReportId);

		//평가 완료시 평가 점수 확인한 날짜 입력
		if(!pMarkCheckYn.equals("") && pStuOpenDate.equals("")) {
			ReportResultDAO reportResultDao = new ReportResultDAO();
			reportResultDao.addReportSendStuOpenDate(systemCode, curriInfo, pCourseId, pReportId, userId, userId);
		}

		//랜덤 출제일 경우
		if(pReportType2.equals("R")) {
			ReportResultDAO reportResultDao = null;
			ReportSubInfoDTO reportSubInfo = null;
			ReportSendDTO reportSendDto = null;

			//최초 선택시
			if(pInsertYn.equals("")) {
				reportResultDao = new ReportResultDAO();
				reportSubInfo = reportResultDao.getReportSubInfoRandom(systemCode, curriInfo, pCourseId, pReportId);

				int subReportId = reportSubInfo.getSubReportId();

				//진행중일 경우에만 insert
				if(pEndYn.equals("Y")) {
					//과제 정보 클릭시 기본정보 report_send 테이블에 insert
					reportResultDao.addRandomReportSend(systemCode, curriInfo, pCourseId, pReportId, subReportId, userId);
				}

				//출제정보 가져오기
				reportSendDto = reportResultDao.getReportSend(systemCode, curriInfo, pCourseId, pReportId, userId);

				pInsertYn = "Y";
			} else {
				//한번 셋팅된 기본정보 가져오기
				reportResultDao = new ReportResultDAO();
				reportSubInfo = reportResultDao.getReportSubInfoRandom2(systemCode, curriInfo, pCourseId, pReportId, userId);

				//출제정보 가져오기
				reportSendDto = reportResultDao.getReportSend(systemCode, curriInfo, pCourseId, pReportId, userId);

			}
			model.put("reportSubInfo", reportSubInfo);
			model.put("reportSendDto", reportSendDto);

			returnUrl = "/coursereport/report_st_random_write.jsp";
		//선택출제일 경우
		} else if(pReportType2.equals("C")) {
			ReportSubInfoDAO reportSubInfoDao 	= new ReportSubInfoDAO();

			RowSet reportSubList = null;

			ReportResultDAO reportResultDao = null;
			ReportSendDTO reportSendDto = null;
			ReportSubInfoDTO reportSubInfo = null;
			//입력
			if(pInsertYn.equals("")) {
				reportSubList = reportSubInfoDao.getReportSubInfoList(systemCode, curriInfo, pCourseId, pReportId);
				reportSendDto = new ReportSendDTO();
				reportSubInfo = new ReportSubInfoDTO();

				returnUrl = "/coursereport/report_st_select_write.jsp";
			//수정
			} else {
				//출제정보 가져오기
				reportResultDao = new ReportResultDAO();
				reportSendDto = reportResultDao.getReportSend(systemCode, curriInfo, pCourseId, pReportId, userId);
				reportSubInfo = reportResultDao.getReportSubInfoRandom2(systemCode, curriInfo, pCourseId, pReportId, userId);

				returnUrl = "/coursereport/report_st_select_edit.jsp";
			}

			model.put("reportSubList", reportSubList);
			model.put("reportSendDto", reportSendDto);
			model.put("reportSubInfo", reportSubInfo);

		}

		model.put("reportInfo",reportInfo);	//과제 상위정보
        model.put("pCourseId", pCourseId);
        model.put("pReportId", String.valueOf(pReportId));
        model.put("pMODE", pMODE);
        model.put("pInsertYn", pInsertYn);
        model.put("pEndYn", pEndYn);
        model.put("pMarkCheckYn", pMarkCheckYn);
        model.put("pSendCheckYn", pSendCheckYn);

        new CurriSiteNavigation(LECTURE).add(request,"과제제출").link(model);
		return forward(request, model, returnUrl);
	}


	/**
	 * 출제 정보를 등록/수정 한다.(학생) -----------###################################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportSendRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);


		UserMemDTO userInfo = UserBroker.getUserInfo(request);

		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		ReportResultDAO reportResultDao = new ReportResultDAO();
		ReportSendDTO reportSendDto =  new ReportSendDTO();
		ReportHelper helper = new ReportHelper();

		MultipartRequest multipartRequest = null;

		String filePath = FileUtil.FILE_DIR+systemCode+"/coursereport/reportSend/"+curriInfo.curriCode+"/"+curriInfo.curriYear+"/"+curriInfo.curriTerm+"/";

		UploadFiles uploadEntity = FileUtil.upload(request, filePath,curriInfo.curriCode+curriInfo.curriYear+curriInfo.curriTerm);
		multipartRequest		=	uploadEntity.getMultipart();

		String pMODE 	 = StringUtil.nvl(multipartRequest.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(multipartRequest.getParameter("pCourseId"));
		int pReportId 	 = StringUtil.nvl(multipartRequest.getParameter("pReportId"),0);
		int pSubReportId = StringUtil.nvl(multipartRequest.getParameter("pSubReportId"),0);
		String pUserId	 = StringUtil.nvl(multipartRequest.getParameter("pUserId"), userId);
		String pSubject  = StringUtil.nvl(multipartRequest.getParameter("pSubject"));
		String pInsertYn = StringUtil.nvl(multipartRequest.getParameter("pInsertYn"));

		//old 파일 정보 DTO에 담기
		helper.getReportSendParam(multipartRequest, reportSendDto);

		reportSendDto.setCourseId(pCourseId);
		reportSendDto.setReportId(pReportId);
		reportSendDto.setUserId(pUserId);
		reportSendDto.setSubReportId(pSubReportId);
		reportSendDto.setReportSendSubject(pSubject);

		String status = uploadEntity.getStatus();

		String rFileName1 = "";
        String sFileName1 ="";
        long fileSize1 = 0;

        String rFileName2 = "";
        String sFileName2 ="";
        long fileSize2 = 0;

		if(status.equals("S")){
			//--	업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			ArrayList files		=	uploadEntity.getFiles();
			UploadFile file		=	null;
			String objName      = "";
			for(int i = 0 ; i < files.size(); i++){
				file			=	(UploadFile)files.get(i);
				objName         = file.getObjName();
                if(objName.equals("pFile1")){
					sFileName1		=	file.getUploadName();
					rFileName1      =   file.getRootName();
					fileSize1       =   file.getSize();
                }
                if(objName.equals("pFile2")){
                	sFileName2		=	file.getUploadName();
					rFileName2      =   file.getRootName();
					fileSize2       =   file.getSize();
                }
			}
		}

		if(!rFileName1.equals("")){
			reportSendDto.setRfileName1(rFileName1);
			reportSendDto.setSfileName1(sFileName1);
			reportSendDto.setFileSize1(String.valueOf(fileSize1));
			reportSendDto.setFilePath1(filePath);

		}else{
            if(reportSendDto.getFileCheck1().equals("")){
            	reportSendDto.setRfileName1(reportSendDto.getOldRfile1());
            	reportSendDto.setSfileName1(reportSendDto.getOldSfile1());
            	reportSendDto.setFileSize1(reportSendDto.getOldFileSize1());
            	reportSendDto.setFilePath1(reportSendDto.getOldFilePath1());
            }

		}

		if(!rFileName2.equals("")){
			reportSendDto.setRfileName2(rFileName2);
			reportSendDto.setSfileName2(sFileName2);
			reportSendDto.setFileSize2(String.valueOf(fileSize2));
			reportSendDto.setFilePath2(filePath);

		}else{
            if(reportSendDto.getFileCheck2().equals("")){
            	reportSendDto.setRfileName2(reportSendDto.getOldRfile2());
            	reportSendDto.setSfileName2(reportSendDto.getOldSfile2());
            	reportSendDto.setFileSize2(reportSendDto.getOldFileSize2());
            	reportSendDto.setFilePath2(reportSendDto.getOldFilePath2());
            }

		}


		String pContents = "";
		String contentsText = "";
		String pEditorType = "";

		pContents	=	StringUtil.nvl(multipartRequest.getParameter("pContents"));
		if( new String("true").equals(multipartRequest.getParameter("VBN_FORM_WEAS")) == true ){//-- 웹에디터 사용시
			contentsText = StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
			pEditorType = "W";
		}else{//-- 웹에디터 사용 안할경우
			contentsText = pContents;
			pEditorType = "H";
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

		reportSendDto.setReportSendContents(pContents);
		reportSendDto.setEditorType(pEditorType);

		int retVal = 0;
		String returnUrl = "/ReportAdmin.cmd?cmd=reportStList&pCourseId="+pCourseId;

		if(pMODE.equals("ADD") && pInsertYn.equals("")){
			reportSendDto.setRegId(userInfo.userId);
			retVal = reportResultDao.addReportSend(systemCode, curriInfo, reportSendDto);
		}else{
			reportSendDto.setModId(userInfo.userId);
			retVal = reportResultDao.editReportSend(systemCode, curriInfo, reportSendDto, pMODE);
		}
		return redirect(returnUrl);
	}

	/**
	 * 과제 첨부파일 삭제(Ajax)	-################################
	 * 2007.06.14 sangsang
	 * @param courseId
	 * @param bankId
	 * @param reportNo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String reportSendFileDelete(String courseId, int reportId, String userId, String type, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode 	= UserBroker.getSystemCode(request);
		CurriMemDTO	curriInfo 	= UserBroker.getCurriInfo(request);
		String modId 		= UserBroker.getUserId(request);

		ReportResultDAO reportResultDao = new ReportResultDAO();

		int retVal = 0;

		retVal = reportResultDao.delReportSendFile(systemCode, curriInfo, courseId, reportId, userId, modId, type);

		return  String.valueOf(retVal);
	}

	/**
	 * 과제물 평가 점수를 업로드할수 있는 페이지로 이동한다.		-#########################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportSendtUpLoadWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{


		String systemCode 	= UserBroker.getSystemCode(request);
		CurriMemDTO	curriInfo 	= UserBroker.getCurriInfo(request);

		String pCourseId		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int pReportId 			= 	Integer.parseInt(request.getParameter("pReportId"));


		model.put("pCourseId", pCourseId);
		model.put("pReportId",Integer.toString(pReportId));

		return forward(request, model, "/coursereport/report_send_upload_write.jsp");
	}

	/**
	 * 과제물 평가 점수를 업로드합니다.	--########################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportSendUpLoadRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String systemCode 		= UserBroker.getSystemCode(request);
		CurriMemDTO	curriInfo 	= UserBroker.getCurriInfo(request);
		String modId 			= UserBroker.getUserId(request);

		ReportResultDAO   reportResultDao = new ReportResultDAO();
		ReportSendDTO	reportsendDto = new ReportSendDTO();

        MultipartRequest multipartRequest = null;
        String filePath = FileUtil.FILE_DIR+systemCode+"/coursereport/reportSend/"+curriInfo.curriCode+"/"+curriInfo.curriYear+"/"+curriInfo.curriTerm+"/";

 		// 파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request,filePath,curriInfo.curriCode+curriInfo.curriYear+curriInfo.curriTerm);

		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();

		String pCourseId  	= StringUtil.nvl(multipartRequest.getParameter("pCourseId"));
		int pReportId 		= StringUtil.nvl(multipartRequest.getParameter("pReportId"),0);

		String status = uploadEntity.getStatus();

	    String originName = "";
		String objName = "";
		String RfileName = "";
		String SfileName = "";
		String FilePath = "";
		double FileSize = 0;
		String msg ="";
		int	retVal = 0;
		int	success	= 0;
		int	fail = 0;
		int insertGubun = 0;
		int studentGubun = 0;
		String failStr = "";

		if (status.equals("E")){
			log.debug("첨부 파일 올리려다 실패하였습니다.");
		}else if (status.equals("O")){
			log.debug("첨부하신 파일이 용량을 초과했습니다.");
		}else if (status.equals("I")){
			log.debug("첨부 파일의 정보가 잘못되었습니다.");
		}else if(status.equals("S")){
			log.debug("첨부 파일을 첨부하는데 성공하였습니다.");

			ArrayList files = uploadEntity.getFiles();
			UploadFile file = null;

			for(int i = 0 ; i < files.size(); i++){
				file = (UploadFile)files.get(i);
				originName = StringUtil.nvl(file.getRootName());
				objName = StringUtil.nvl(file.getObjName());
				if(!originName.equals("")) {
					RfileName = originName;
					SfileName = file.getUploadName();
					FilePath = uploadEntity.getUploadPath();
					FileSize = file.getSize();

					//-- 파일 읽어 들이기
					String  rootPath 		= 	FileUtil.UPLOAD_PATH;
					String	read_file		=	rootPath+filePath+ SfileName;

					FileReader fr			=	new FileReader(read_file);
					BufferedReader	in		=	new BufferedReader(fr);
					String			st		=	null;

					int     k =0;
					int		wk_fcnt			=	0;

					int cnt	=	0;

					while ((st=in.readLine()) != null) {
						wk_fcnt++;
						StringTokenizer	Line=	new StringTokenizer(st, ",", false);
						int	cols				=	Line.countTokens();				//	항목수 (2개)

						if(cols != 2) {
							log.debug("입력하신 항목수가 일치 하지 않습니다.");
							fail++;
							failStr += ((fail>1) ? "," : "")+wk_fcnt;
						}
						else
						{
							String	token[]		=	new String[cols];
							int		idx			=	0;
							while (Line.hasMoreTokens()) {
								token[idx]		=	Line.nextToken();
								idx++;
							}

							//점수가 숫자인지 체크
							boolean numCheck =  StringUtil.isNumber(token[1].trim());
							if(numCheck == false) {
								log.debug("점수가 숫자 형식이 아닙니다.");
								fail++;
								failStr += ((fail>1) ? "," : "")+wk_fcnt;
							} else {
								//100점을 넘는지 체크
								if(Integer.parseInt(token[1].trim()) > 100) {
									log.debug("점수가 100점을 넘습니다.");
									fail++;
									failStr += ((fail>1) ? "," : "")+wk_fcnt;
								} else {
									studentGubun = reportResultDao.getReportStudentGubun(systemCode, curriInfo, token[0].trim());
									if(studentGubun == 1) {
										log.debug("수강중인 학생이 아닙니다.");
										fail++;
										failStr += ((fail>1) ? "," : "")+wk_fcnt;
									} else {
										//insert & update 구분
										insertGubun = reportResultDao.getReportSendWriteGubun(systemCode, curriInfo, pCourseId, pReportId, token[0].trim());

										//입력 & 수정
										retVal = reportResultDao.addReportSendScore(systemCode, curriInfo, pCourseId, pReportId, token[0].trim(), Integer.parseInt(token[1].trim()), modId, insertGubun);

										if(retVal > 0){
											success++;
										}else{
											fail++;
											failStr += ((fail>1) ? "," : "")+wk_fcnt;
										}

									}
								}
							}

						}
					}

					//--처리 후 첨부한 파일 삭제
					if(!filePath.equals("") && !SfileName.equals("")) {	//이전 첨부파일을 삭제할 경우
						FileUtil.delFile(filePath, SfileName);
						log.debug("첨부파일을 삭제하였습니다.");
					}
			}//end if
			}// end for
		}//end if

		if(fail>0) {
			failStr = "<br>"+failStr + " 라인 입력 오류 입니다.";
		}

        msg +=  "입력 파일  처리 결과<br>입력성공 건수 : "+success+"<br>입력실패 건수 : "+fail;
        msg += failStr;

        return notifyCloseReload(systemCode,model,msg,"N");
	}


	/**
	 * 수강생이 제출한 과제 리스트를 다운로드 한다.		-#######################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportResultDownLoad(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String 			systemCode 	= 	UserBroker.getSystemCode(request);
		CurriMemDTO		curriInfo 	= 	UserBroker.getCurriInfo(request);
		String 			pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int 			pReportId 	= 	StringUtil.nvl(request.getParameter("pReportId"), 0);
		String			pReportSubject	=	StringUtil.nvl(request.getParameter("pReportSubject"));

		ReportResultDAO	resultDao 		= 	new ReportResultDAO();
		RowSet 			reportSendList 	= 	null;

		reportSendList = resultDao.getReportUserList(systemCode, curriInfo, pCourseId, pReportId);

		model.put("reportSendList", reportSendList);
		model.put("pCourseId",pCourseId);
		model.put("pReportId",String.valueOf(pReportId));
		model.put("pReportSubject",pReportSubject);;

		//다운로드 파일명
		String	str_now			=	DateTimeUtil.getDate();

		model.put("pFileName", "report_"+curriInfo.curriCode+"_"+curriInfo.curriYear+"_"+curriInfo.curriTerm+"_"+pCourseId+"_"+str_now);

		return forward(request, model, "/coursereport/report_send_down.jsp");
	}

	/**
	 * 과제점수를 일괄 입력한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportResultAllScoreRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String 			systemCode 	= 	UserBroker.getSystemCode(request);
		CurriMemDTO		curriInfo 	= 	UserBroker.getCurriInfo(request);
		String 			pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int 			pReportId 	= 	StringUtil.nvl(request.getParameter("pReportId"), 0);
		String			pReportSubject	=	StringUtil.nvl(request.getParameter("pReportSubject"));
		String[] pScore			= 	request.getParameterValues("pScore");
		String[] pScoreUserId	= 	request.getParameterValues("pScoreUserId");
		String[] pNullYn		=	request.getParameterValues("pNullYn");
		String pModId			=	UserBroker.getUserId(request);
		int gubun 				=	0;
		ReportResultDAO	resultDao 		= 	new ReportResultDAO();

		for(int i=0;i<pScore.length;i++) {
			if(pNullYn[i].equals("")) {
				gubun = 1;
			} else {
				gubun = 2;
			}
			resultDao.addReportSendScore(systemCode, curriInfo, pCourseId, pReportId, pScoreUserId[i], Integer.parseInt(pScore[i]), pModId, gubun);
		}

		String returnUrl = "/ReportResult.cmd?cmd=reportUserResultList&pCourseId="+pCourseId+"&pReportId="+pReportId+"&pReportSubject="+StringUtil.ksc2asc(pReportSubject);

		return redirect(returnUrl);
	}

	/**
	 * 다른 학생 과제 보기 리스트(학생)	-######################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */

	public ActionForward reportOtherUserList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String 			systemCode 	= 	UserBroker.getSystemCode(request);
		CurriMemDTO		curriInfo 	= 	UserBroker.getCurriInfo(request);
		String 			pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int 			pReportId 	= 	StringUtil.nvl(request.getParameter("pReportId"), 0);
		String			pReportSubject	=	StringUtil.nvl(request.getParameter("pReportSubject"));
		String 			userId			=	UserBroker.getUserId(request);
		ReportResultDAO	resultDao 		= 	new ReportResultDAO();
		RowSet 			reportSendList 	= 	null;

		reportSendList = resultDao.getReportOtherUserList(systemCode, curriInfo, pCourseId, pReportId, userId);

		model.put("reportSendList", reportSendList);
		model.put("pCourseId",pCourseId);
		model.put("pReportId",String.valueOf(pReportId));
		model.put("pReportSubject",pReportSubject);

		new CurriSiteNavigation(LECTURE).add(request,"제출 리스트").link(model);

		return forward(request, model, "/coursereport/report_other_send_list.jsp");
	}

	/**
	 * 다른 학생 과제 보기 상세화면(학생)	-######################
	 * 2007.06.13 sangsang
	 * @param courseId
	 * @param bankId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportOtherUserShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode 		= UserBroker.getSystemCode(request);
		CurriMemDTO	curriInfo 	= UserBroker.getCurriInfo(request);
		String pCourseId 		= StringUtil.nvl(request.getParameter("pCourseId"));
		int pReportId 			= StringUtil.nvl(request.getParameter("pReportId"),0);
		String pUserId			= StringUtil.nvl(request.getParameter("pUserId"));
		String pUserName		= StringUtil.nvl(request.getParameter("pUserName"));
		String pReportSubject	=	StringUtil.nvl(request.getParameter("pReportSubject"));


		ReportResultDAO reportResultDao = null;
		ReportSubInfoDTO reportSubInfo = null;
		ReportSendDTO reportSendDto = null;

		//과제 상위 정보
		ReportInfoDTO reportInfo = null;
		ReportAdminDAO reportDao = new ReportAdminDAO();
		reportInfo = reportDao.getReportInfo(systemCode,curriInfo ,pCourseId,pReportId);

		//출제정보 가져오기
		reportResultDao = new ReportResultDAO();
		reportSendDto = reportResultDao.getReportSend(systemCode, curriInfo, pCourseId, pReportId, pUserId);
		reportSubInfo = reportResultDao.getReportSubInfoRandom2(systemCode, curriInfo, pCourseId, pReportId, pUserId);


		model.put("reportInfo", reportInfo);
		model.put("reportSendDto", reportSendDto);
		model.put("reportSubInfo", reportSubInfo);
        model.put("pCourseId", pCourseId);
        model.put("pReportId", String.valueOf(pReportId));
        model.put("pUserId", pUserId);
        model.put("pUserName", pUserName);
        model.put("pReportSubject",pReportSubject);

        new CurriSiteNavigation(LECTURE).add(request,"과제제출상세보기").link(model);

		return forward(request, model, "/coursereport/report_other_send_show.jsp");
	}

}

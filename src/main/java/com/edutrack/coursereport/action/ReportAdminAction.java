/*
 * Created on 2004. 10. 8.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.coursereport.action;

import java.util.Map;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.CommonUtil;
import com.edutrack.common.CurriSiteNavigation;
import com.edutrack.common.DateSetter;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.curristudent.dao.CurriStudentDAO;
import com.edutrack.coursereport.dao.ReportAdminDAO;
import com.edutrack.coursereport.dao.ReportBankDAO;
import com.edutrack.coursereport.dao.ReportSubInfoDAO;
import com.edutrack.coursereport.dto.ReportBankContentsDTO;
import com.edutrack.coursereport.dto.ReportInfoDTO;
import com.edutrack.coursereport.dto.ReportSubInfoDTO;
import com.edutrack.coursereport.dto.ReportSendDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportAdminAction extends StrutsDispatchAction{

	/**
	 *
	 */
	public ReportAdminAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 과제 리스트를 가져온다.(교수자) ---###############################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String		pCourseId		=	StringUtil.nvl(request.getParameter("pCourseId"));

		model.put("pCourseId",pCourseId);

		new CurriSiteNavigation(LECTURE).add(request,"과제리스트").link(model);

		return forward(request, model, "/coursereport/report_mg_list.jsp");
	}

	/**
	 * 과제 리스트를 가져온다.(교수자) (Ajax)	-###############################
	 * 2007.06.13 sangsang
	 * @param courseId
	 * @param bankId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList reportListAuto(String pCourseId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String		systemCode		=	UserBroker.getSystemCode(request);
		CurriMemDTO	curriInfo		=	UserBroker.getCurriInfo(request);
		pCourseId = StringUtil.nvl(pCourseId);

		// 리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		ReportAdminDAO	reportAdminDao	=	new ReportAdminDAO();
		ReportInfoDTO reportInfoDto	= null;

		RowSet rs = reportAdminDao.getReportList(systemCode, curriInfo, pCourseId,0);

		while(rs.next()){
			reportInfoDto	= new ReportInfoDTO();
			reportInfoDto.setCourseId(pCourseId);
			reportInfoDto.setReportId(rs.getInt("report_id"));
			reportInfoDto.setReportSubject(StringUtil.setMaxLength(StringUtil.nvl(rs.getString("report_subject")),60));
			reportInfoDto.setReportType1(StringUtil.nvl(rs.getString("report_type1")));
			reportInfoDto.setReportType2(StringUtil.nvl(rs.getString("report_type2")));
			reportInfoDto.setReportStartDate(DateTimeUtil.getDateType(1,StringUtil.nvl(rs.getString("report_start_date"))));
			reportInfoDto.setReportEndDate(DateTimeUtil.getDateType(1,StringUtil.nvl(rs.getString("report_end_date"))));
			reportInfoDto.setReportExtendDate(DateTimeUtil.getDateType(1,StringUtil.nvl(rs.getString("report_extend_date"))));
			reportInfoDto.setReportScoreYn(StringUtil.nvl(rs.getString("report_score_yn")));
			reportInfoDto.setReportOpenYn(StringUtil.nvl(rs.getString("report_open_yn")));
			reportInfoDto.setReportRegistYn(StringUtil.nvl(rs.getString("report_regist_yn")));

			arrayList.add(reportInfoDto);
		}
		rs.close();
		return arrayList;
	}

    /**
     * 과제 등록,수정 폼을 띄워준다. -##########################
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
	public ActionForward reportWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	pMENUNO			=	StringUtil.nvl(request.getParameter("pMENUNO"), "7");
		String	systemCode		=	UserBroker.getSystemCode(request);
		String	pMODE			=	StringUtil.nvl(request.getParameter("pMODE"));
		String	pCourseId		=	StringUtil.nvl(request.getParameter("pCourseId"));
		int		pReportId		=	StringUtil.nvl(request.getParameter("pReportId"),0);

		//과제 진행 방식 구분
		String	pReportType1		=	StringUtil.nvl(request.getParameter("pReportType1"));

		ReportInfoDTO	reportInfo		=	null;
		ReportHelper	helper			=	new ReportHelper();
		DateSetter		reportTerm		=	null;
		DateSetter		reportResult	=	null;
		int				subReprotCnt	=	0;
		String			reportStartDate	=	"";
		String 			reportSendDelYn = 	"";

		if(pMODE.equals("write")) {		//과제추가
			String		curDate		=	CommonUtil.getCurrentDate();
			reportInfo		=	new ReportInfoDTO();
		   	reportTerm		=	new DateSetter(curDate,curDate).link(model);
		   	reportResult	=	new DateSetter(curDate).link(model);
		   	CurriMemDTO		curriInfo	=	UserBroker.getCurriInfo(request);
		   	ReportAdminDAO	reportDao	=	new ReportAdminDAO();

		}
		else {			//과제수정
			CurriMemDTO			curriInfo	=	UserBroker.getCurriInfo(request);
			ReportAdminDAO		reportDao	=	new ReportAdminDAO();
			reportInfo		=	reportDao.getReportInfo(systemCode,curriInfo,pCourseId,pReportId);
			reportTerm		=	new DateSetter(reportInfo.getReportStartDate(),reportInfo.getReportEndDate()).link(model);
			reportResult	=	new DateSetter(reportInfo.getReportExtendDate()).link(model);
			//서브과제 갯수
			subReprotCnt	=	reportDao.getReportSubInfoCnt(systemCode, curriInfo.curriCode, curriInfo.curriYear, curriInfo.curriTerm, pCourseId, pReportId);

			//과제 시작일(현재 날짜와 비교)
			reportStartDate	=	reportInfo.getReportStartDate().substring(0,12);
			//현재 시작일&시간
			String 			toDate			=	DateTimeUtil.getDate();
			String 			toTime			=	DateTimeUtil.getTime().substring(0, 4);

			long startDate = Long.parseLong(reportStartDate);
			long toDateTime = Long.parseLong(toDate+toTime);


			if(startDate <= toDateTime) {
				reportSendDelYn = "Y";
			} else {
				reportSendDelYn = "N";
			}

		}

		model.put("reportInfo",reportInfo);
		model.put("reportTerm", reportTerm);
		model.put("reportResult",reportResult);
		model.put("pMODE", pMODE);
        model.put("pCourseId", pCourseId);
		model.put("pReportId",String.valueOf(pReportId));
		model.put("pSubReprotCnt", String.valueOf(subReprotCnt));
		model.put("pReportSendDelYn", reportSendDelYn);

        String navMsg = "과제등록";
        if(pMODE.equals("edit"))	navMsg	=	"과제수정";
		new CurriSiteNavigation(LECTURE).add(request,navMsg).link(model);


		return forward(request, model, "/coursereport/report_info_write.jsp");

	}

	/**
     * 상위 과제 상세보기 & 서브과제 리스트 -######################
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
	public ActionForward reportShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String	systemCode		=	UserBroker.getSystemCode(request);
		String	pMODE			=	StringUtil.nvl(request.getParameter("pMODE"));
		String	pCourseId		=	StringUtil.nvl(request.getParameter("pCourseId"));
		int		pReportId		=	StringUtil.nvl(request.getParameter("pReportId"),0);

		//과제 진행 방식 구분
		String	pReportType1		=	StringUtil.nvl(request.getParameter("pReportType1"));

		ReportInfoDTO	reportInfo		=	null;
		ReportHelper	helper			=	new ReportHelper();
		DateSetter		reportTerm		=	null;
		DateSetter		reportResult	=	null;
		int				subReprotCnt	=	0;
		String			reportStartDate	=	"";
		String 			pSubReportEditYn = 	"";


		CurriMemDTO			curriInfo	=	UserBroker.getCurriInfo(request);
		ReportAdminDAO		reportDao	=	new ReportAdminDAO();
		reportInfo		=	reportDao.getReportInfo(systemCode,curriInfo,pCourseId,pReportId);

		//과제 시작일(현재 날짜와 비교)
		reportStartDate	=	reportInfo.getReportStartDate().substring(0,12);
		//현재 시작일&시간
		String 			toDate			=	DateTimeUtil.getDate();
		String 			toTime			=	DateTimeUtil.getTime().substring(0, 4);

		long startDate = Long.parseLong(reportStartDate);
		long toDateTime = Long.parseLong(toDate+toTime);


		if(startDate <= toDateTime) {
			pSubReportEditYn = "Y";
		} else {
			pSubReportEditYn = "N";
		}

		model.put("reportInfo",reportInfo);
		model.put("reportTerm", reportTerm);
		model.put("reportResult",reportResult);
		model.put("pMODE", pMODE);
        model.put("pCourseId", pCourseId);
		model.put("pReportId",String.valueOf(pReportId));
		model.put("pSubReprotCnt", String.valueOf(subReprotCnt));
		model.put("pSubReportEditYn", pSubReportEditYn);

        String navMsg = "서브과제관리";

		new CurriSiteNavigation(LECTURE).add(request,navMsg).link(model);

		return forward(request, model, "/coursereport/report_info_show.jsp");

	}

	/**
	 * 과제정보를 등록한다.		-###########################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String		systemCode		=	UserBroker.getSystemCode(request);
		UserMemDTO	userInfo		=	UserBroker.getUserInfo(request);
		CurriMemDTO	curriInfo		=	UserBroker.getCurriInfo(request);

		MultipartRequest multipartRequest = null;

		UploadFiles uploadEntity = FileUtil.upload(request, "",curriInfo.curriCode+curriInfo.curriYear+curriInfo.curriTerm);
		multipartRequest		=	uploadEntity.getMultipart();

		String		pMODE			=	StringUtil.nvl(multipartRequest.getParameter("pMODE"));
		String		pCourseId		=	StringUtil.nvl(multipartRequest.getParameter("pCourseId"));
		String		pCourseName		=	StringUtil.nvl(multipartRequest.getParameter("pCourseName"));
		String		pReportType		=	StringUtil.nvl(multipartRequest.getParameter("pReportType"));
		int			pReportId		=	StringUtil.nvl(multipartRequest.getParameter("pReportId"),0);

		ReportAdminDAO	reportDao	=	new ReportAdminDAO();
		ReportHelper	helper		=	new ReportHelper();
		ReportInfoDTO	reportInfo	=	new ReportInfoDTO();
		
		helper.getReportParam(multipartRequest, reportInfo);

		if(pMODE.equals("write")){
			reportInfo.setReportId(pReportId);
			reportInfo.setRegId(userInfo.userId);
		}
		else{
			reportInfo.setModId(userInfo.userId);
			reportInfo.setReportId(pReportId);
		}

		String pContents 	= 	"";
		String contentsText = 	"";

		pContents	=	StringUtil.nvl(multipartRequest.getParameter("pContents"));
		if( new String("true").equals(multipartRequest.getParameter("VBN_FORM_WEAS")) == true ){//-- 웹에디터 사용시
			contentsText = StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
		}else{//-- 웹에디터 사용 안할경우
			contentsText = pContents;
		}
		reportInfo.setReportContents(pContents);
		new CurriSiteNavigation(LECTURE).add(request,"과제리스트").link(model);

		String		returnUrl	=	"";
		int			retVal		=	0;

		if(pMODE.equals("edit")){
			retVal		=	reportDao.editReportInfo(systemCode, userInfo.curriInfo, reportInfo);
		} else {
			retVal		=	reportDao.addReportInfo(systemCode, userInfo.curriInfo, reportInfo);
		}

		if(retVal > 0){
			returnUrl	=	"/ReportAdmin.cmd?cmd=reportList&pCourseId="+pCourseId+"&pCourseName="+pCourseName;
		}
		return redirect(returnUrl);
		//return notifyAndExit(systemCode, model, msg, returnUrl, LECTURE);
	}

	/**
	 * 과제 상위 정보 삭제(하위 과제 및 제출 정보 일괄 삭제)	-##############################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
		HttpServletResponse httpServletResponse, Map model) throws Exception {

		UserMemDTO	userInfo		=	UserBroker.getUserInfo(request);
		CurriMemDTO	curriInfo		=	UserBroker.getCurriInfo(request);

		String	systemCode			= 	UserBroker.getSystemCode(request);
		String	pMODE 				= 	StringUtil.nvl(request.getParameter("pMODE"));
		String	pCourseId 			= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int		pReportId 			= 	StringUtil.nvl(request.getParameter("pReportId"),0);
		int 	retVal 		= 	0;

		ReportAdminDAO	reportAdminDao	=	new ReportAdminDAO();

		retVal		=	reportAdminDao.delReportInfo(systemCode, userInfo.curriInfo, pCourseId, pReportId);

		String	returnUrl 	=	"";

		if(retVal > 0) {
			returnUrl = "/ReportAdmin.cmd?cmd=reportList&pCourseId="+pCourseId;
		}

		return redirect(returnUrl);
	}


	/**
	 * 학습자 - 과제리스트	-###########################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportStList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String		pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));

		model.put("pCourseId",pCourseId);

		new CurriSiteNavigation(LECTURE).add(request,"과제리스트").link(model);
		return forward(request, model, "/coursereport/report_st_list.jsp");
	}

	/**
	 * 과제 리스트를 가져온다.(학생) (Ajax)	-###############################
	 * 2007.06.13 sangsang
	 * @param courseId
	 * @param bankId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList reportStListAuto(String pCourseId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String 		systemCode	=	UserBroker.getSystemCode(request);
		UserMemDTO	userDto 	=	UserBroker.getUserInfo(request);
		CurriMemDTO	curriInfo 	= 	userDto.curriInfo;
		String		userId 		= 	userDto.userId;
		pCourseId = StringUtil.nvl(pCourseId);

		// 리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		ReportAdminDAO	reportAdminDao	=	new ReportAdminDAO();
		ReportInfoDTO reportInfoDto	= null;

		RowSet rs = reportAdminDao.getStReportList(systemCode, curriInfo, pCourseId, userId);

		long startDate = 0;
		long extendDate = 0;
		long curDate = Long.parseLong(CommonUtil.getCurrentDate());
		String writeYn = "";


		while(rs.next()){
			reportInfoDto	= new ReportInfoDTO();
			reportInfoDto.setCourseId(pCourseId);
			reportInfoDto.setReportId(rs.getInt("report_id"));
			reportInfoDto.setReportSubject(StringUtil.setMaxLength(StringUtil.nvl(rs.getString("report_subject")),60));
			reportInfoDto.setReportType2(StringUtil.nvl(rs.getString("report_type2")));
			reportInfoDto.setReportStartDate(DateTimeUtil.getDateType(1,StringUtil.nvl(rs.getString("report_start_date"))));
			reportInfoDto.setReportEndDate(DateTimeUtil.getDateType(1,StringUtil.nvl(rs.getString("report_end_date"))));
			reportInfoDto.setReportExtendDate(DateTimeUtil.getDateType(1,StringUtil.nvl(rs.getString("report_extend_date"))));
			reportInfoDto.setReportScoreYn(StringUtil.nvl(rs.getString("report_score_yn")));
			reportInfoDto.setReportOpenYn(StringUtil.nvl(rs.getString("report_open_yn")));
			reportInfoDto.setReportRegistYn(StringUtil.nvl(rs.getString("report_regist_yn")));
			reportInfoDto.setSendCheckYn(StringUtil.nvl(rs.getString("sendCheckYn")));
			reportInfoDto.setMarkCheckYn(StringUtil.nvl(rs.getString("markCheckYn")));
			reportInfoDto.setInsertYn(StringUtil.nvl(rs.getString("insertYn")));
			reportInfoDto.setStuOpenDate(StringUtil.nvl(rs.getString("stu_open_date")));

			startDate =  Long.parseLong(StringUtil.nvl(rs.getString("report_start_date")));
			extendDate =  Long.parseLong(StringUtil.nvl(rs.getString("report_extend_date")));

			//진행&종료값
			if (startDate <= curDate && extendDate >= curDate) {
		    	writeYn = "Y";
		    } else if (extendDate < curDate) {
				writeYn = "N";
			}
			reportInfoDto.setWriteYn(StringUtil.nvl(writeYn));

			arrayList.add(reportInfoDto);
		}
		rs.close();
		return arrayList;
	}


	/**
	 * 학습자 - 과제상위정보(팝업)	-################################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportStShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	systemCode		=	UserBroker.getSystemCode(request);
		CurriMemDTO	curriInfo	=	UserBroker.getCurriInfo(request);
		String	pCourseId		=	StringUtil.nvl(request.getParameter("pCourseId"));
		int		pReportId		=	StringUtil.nvl(request.getParameter("pReportId"),0);

		ReportInfoDTO reportInfo = null;
		ReportHelper helper = new ReportHelper();
		ReportAdminDAO reportDao = new ReportAdminDAO();

		reportInfo = reportDao.getReportInfo(systemCode,curriInfo ,pCourseId,pReportId);

        model.put("pCourseId", pCourseId);
		model.put("pReportId", String.valueOf(pReportId));
		model.put("reportInfo",reportInfo);

		return forward(request, model, "/coursereport/report_st_show.jsp");
	}

	/**
	 * 학습자 - 과제서브정보(팝업)	-################################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportStSubShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	systemCode		=	UserBroker.getSystemCode(request);
		CurriMemDTO	curriInfo	=	UserBroker.getCurriInfo(request);
		String	pCourseId		=	StringUtil.nvl(request.getParameter("pCourseId"));
		int		pReportId		=	StringUtil.nvl(request.getParameter("pReportId"),0);
		int		pSubReportId		=	StringUtil.nvl(request.getParameter("pSubReportId"),0);

		ReportSubInfoDAO reportSubInfoDao = new ReportSubInfoDAO();
		ReportSubInfoDTO reportSubInfo = reportSubInfoDao.getReportSubInfo(systemCode, curriInfo, pCourseId, pReportId, pSubReportId);

        model.put("pCourseId", pCourseId);
		model.put("pReportId", String.valueOf(pReportId));
		model.put("pSubReportId", String.valueOf(pSubReportId));
		model.put("reportSubInfo",reportSubInfo);

		return forward(request, model, "/coursereport/report_st_sub_show.jsp");
	}

}

/*
 * Created on 2004. 10. 21.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.coursereport.action;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.coursereport.dao.ReportBankDAO;
import com.edutrack.coursereport.dto.ReportBankContentsDTO;
import com.edutrack.coursereport.dto.ReportBankInfoDTO;
import com.edutrack.courseteam.dao.CourseTeamDAO;
import com.edutrack.courseteam.dto.CourseTeamInfoDTO;
import com.edutrack.curritop.dao.QuizDAO;
import com.edutrack.curritop.dto.QuizDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
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
public class ReportBankAction extends StrutsDispatchAction {

	/**
	 *
	 */
	public ReportBankAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 문제은행 문제 리스트 페이지 이동	-##############################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportBankContentsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);

/*		// 권한체크
		if(CommonUtil.checkAuth("Y", userId, "MP", userType) == false)
			return alertAndExit(UserBroker.getSystemCode(request), model, "접근권한이 없습니다.", errorPagePath, HOME);
*/
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pGubun = StringUtil.nvl(request.getParameter("pGubun"),"M");
		String pReportId = StringUtil.nvl(request.getParameter("pReportId"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pBankId = StringUtil.nvl(request.getParameter("pBankId"));

		model.put("pCourseId",pCourseId);
		model.put("pBankId",pBankId);
		model.put("pMODE",pMODE);
		model.put("pGubun",pGubun);
		model.put("pReportId",pReportId);

		String returnUrl = "/coursereport/report_contents_add_bank.jsp";
		if(pGubun.equals("M")) returnUrl = "/coursereport/report_bk_info_list.jsp";

		new CurriSiteNavigation(pMode).add(request,"문제은행").link(model);
		return forward(request, model, returnUrl);
	}

	/**
	 * 과목별 문제은행항목 리스트를 셀렉트 박스로 보내준다..(ajax)	-##########################
	 * 2007.06.14 sangsang
	 * @param courseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map reportBankInfoSelectList(String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);
		courseId = StringUtil.nvl(courseId);

		ReportBankDAO bankDao = new ReportBankDAO();
		Map map = new HashMap();
		RowSet rs = bankDao.getReportBankInfoCodeList(systemCode, courseId);
		while(rs.next()){
			map.put(StringUtil.nvl(rs.getString("report_bank_id")),StringUtil.nvl(rs.getString("report_bank_name")));
		}
		rs.close();
		return map;
	}

	/**
	 * 문제은행 구분항목(그룹) 등록/수정/삭제 (Ajax)	-#################################
	 * @param regMode
	 * @param courseId
	 * @param bankId
	 * @param bankName
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String reportBankInfoRegist(String regMode, String courseId, String bankId, String bankName, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		courseId = StringUtil.nvl(courseId);
		bankId = StringUtil.nvl(bankId,"0");
		bankName = AjaxUtil.ajaxDecoding(bankName);

		ReportBankDAO reportBankDao = new ReportBankDAO();

	    int	retVal = 0;
		if(regMode.equals("ADD")){
			retVal = reportBankDao.addReportBankInfo(systemCode,userId,courseId,bankName);
		}else if(regMode.equals("EDIT")){
			retVal = reportBankDao.editReportBankInfo(systemCode,userId,courseId,Long.parseLong(bankId),bankName);
		}else if(regMode.equals("DEL")){
			boolean retValDel = false;
			retValDel = reportBankDao.delReportBankInfo(systemCode,userId,courseId,Long.parseLong(bankId));
			if(retValDel)
				retVal = 1;
			else
				retVal = 0;
		}
		return String.valueOf(retVal);
	}

	/**
	 * 문제은행 항목별 문제리스트(Ajax)	-###############################
	 * 2007.06.13 sangsang
	 * @param courseId
	 * @param bankId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList reportBankContentsListAuto(String courseId, String bankId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		courseId = StringUtil.nvl(courseId);
		bankId = StringUtil.nvl(bankId,"0");

		// 리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		ReportBankDAO reportBankDao = new ReportBankDAO();
		ReportBankContentsDTO reportBankContentsDto	= null;

		RowSet rs = reportBankDao.getReportBankContentsList(systemCode,courseId,Long.parseLong(bankId));
		while(rs.next()){
			reportBankContentsDto	= new ReportBankContentsDTO();
			reportBankContentsDto.setCourseId(courseId);
			reportBankContentsDto.setReportBankId(Long.parseLong(bankId));
			reportBankContentsDto.setReportId(rs.getInt("report_id"));
			reportBankContentsDto.setReportSubject(StringUtil.setMaxLength(StringUtil.nvl(rs.getString("report_subject")),60));
			reportBankContentsDto.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
			reportBankContentsDto.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
			reportBankContentsDto.setFilePath(StringUtil.nvl(rs.getString("file_path")));
			reportBankContentsDto.setFileSize(StringUtil.nvl(rs.getString("file_size")));

			arrayList.add(reportBankContentsDto);
		}
		rs.close();
		return arrayList;
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
	public String reportBankContentsFileDelete(String courseId, int bankId, int reportId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		ReportBankDAO reportBankDao = new ReportBankDAO();
		int retVal = 0;
		retVal = reportBankDao.delReportBankContentsFile(systemCode, courseId, bankId,reportId,userId);

/*		// 첨부파일 삭제 안함-DB만 삭제
		ReportBkContentsDTO reportBkContentsDto = new ReportBkContentsDTO();
		reportBkContentsDto = reportBankDao.getReportBkContentsInfo(systemCode, courseId, bankId, reportNo);
		String oldrFileName = StringUtil.nvl(reportBkContentsDto.getRfileName());
		String oldsFileName = StringUtil.nvl(reportBkContentsDto.getSfileName());
		String oldFilePath = StringUtil.nvl(reportBkContentsDto.getFilePath());

		if(!oldrFileName.equals("") && !oldrFileName.equals("") && !oldrFileName.equals("")){
			FileUtil.delFile(oldFilePath, oldsFileName);
		}
*/
		return  String.valueOf(retVal);
	}

	/**
	 * 문제 은행 개별 문제 등록/수정 폼	-#############################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportBankContentsWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pGubun = StringUtil.nvl(request.getParameter("pGubun"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pReportId = StringUtil.nvl(request.getParameter("pReportId"),0);
		String pBankId = StringUtil.nvl(request.getParameter("pBankId"),"0");

		ReportBankContentsDTO contentsInfo = null;

		if(pMODE.equals("ADD")){
		     contentsInfo = new ReportBankContentsDTO();
		}else{
			ReportBankDAO bankDao = new ReportBankDAO();
			contentsInfo = bankDao.getReportBkContentsInfo(systemCode,pCourseId,Long.parseLong(pBankId), pReportId);
		}

		model.put("contentsInfo",contentsInfo);
		model.put("pMODE", pMODE);
		model.put("pGubun", pGubun);
        model.put("pCourseId", pCourseId);
		model.put("pReportId",""+pReportId);
		model.put("pBankId",""+pBankId);

        String navMsg = "문제등록";

        if(!pMODE.equals("ADD")) navMsg = "문제수정";

		new CurriSiteNavigation(LECTURE).add(request,navMsg).link(model);

		return forward(request, model, "/coursereport/report_bk_contents_write.jsp");
	}

	/**
	 * 문제 은행 개별 문제 삭제	-##########################################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportBankContentsDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);

/*		// 권한체크
		if(CommonUtil.checkAuth("Y", userId, "MP", userType) == false)
			return alertAndExit(UserBroker.getSystemCode(request), model, "접근권한이 없습니다.", errorPagePath, HOME);
*/
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		long pBankId = Long.parseLong(request.getParameter("pBankId"));
		int pReportId = StringUtil.nvl(request.getParameter("pReportId"),0);

		ReportBankDAO bankDao = new ReportBankDAO();
		int retVal = 0;
		retVal = bankDao.delReportBankContents(systemCode,pCourseId,pBankId, pReportId);

        new CurriSiteNavigation(pMode).add(request,"문제리스트").link(model);
		return redirect("/ReportBank.cmd?cmd=reportBankContentsList&pCourseId="+pCourseId+"&pBankId="+pBankId);
	}

	/**
	 * 문제은행 문항 등록&수정	-################################
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportBankContentsRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);

		/*// 권한체크
		if(CommonUtil.checkAuth("Y", userId, "MP", userType) == false)
			return alertAndExit(UserBroker.getSystemCode(request), model, "접근권한이 없습니다.", errorPagePath, HOME);
*/
		UserMemDTO userInfo = UserBroker.getUserInfo(request);
		String courseId = request.getParameter("pCourseId");
		String bankId = request.getParameter("pBankId");

		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		ReportBankDAO contentsDao = new ReportBankDAO();
		ReportBankContentsDTO contentsInfo = new ReportBankContentsDTO();
		ReportHelper helper = new ReportHelper();

		MultipartRequest multipartRequest = null;

		String filePath = FileUtil.FILE_DIR+systemCode+"/coursereport/bank/"+curriInfo.curriCode+"/"+curriInfo.curriYear+"/"+curriInfo.curriTerm+"/"+courseId+"/"+bankId+"/";

		UploadFiles uploadEntity = FileUtil.upload(request, filePath,curriInfo.curriCode+curriInfo.curriYear+curriInfo.curriTerm);
		multipartRequest		=	uploadEntity.getMultipart();

		String pMODE = StringUtil.nvl(multipartRequest.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(multipartRequest.getParameter("pCourseId"));
		String pImsiBankId = multipartRequest.getParameter("pBankId");
		String pImsiReportId = multipartRequest.getParameter("pReportId");
		String pSubject = StringUtil.nvl(multipartRequest.getParameter("pSubject"));
		long pBankId = 0;
		int pReportId = 0;
		if(pImsiBankId != null && !pImsiBankId.equals("")) pBankId = Long.parseLong(pImsiBankId);
		if(pImsiReportId != null && !pImsiReportId.equals("")) pReportId = Integer.parseInt(pImsiReportId);

		//old 파일 정보 DTO에 담기
		helper.getReportBankContentsParam(multipartRequest, contentsInfo);

		contentsInfo.setCourseId(pCourseId);
		contentsInfo.setReportBankId(pBankId);
		contentsInfo.setReportId(pReportId);
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
			contentsInfo.setRfileName(rFileName);
			contentsInfo.setSfileName(sFileName);
			contentsInfo.setFileSize(""+fileSize);
			contentsInfo.setFilePath(filePath);

		}else{
            if(contentsInfo.getFileCheck().equals("")){
			    contentsInfo.setRfileName(contentsInfo.getOldRfile());
				contentsInfo.setSfileName(contentsInfo.getOldSfile());
				contentsInfo.setFileSize(contentsInfo.getOldFileSize());
				contentsInfo.setFilePath(contentsInfo.getOldFilePath());
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

		contentsInfo.setReportSubject(pSubject);
		contentsInfo.setReportContents(pContents);

		if(pMODE.equals("ADD")){
	        contentsInfo.setRegId(userInfo.userId);
			contentsDao.addReportBankContentsInfo(systemCode, contentsInfo);
		}else{
			contentsInfo.setModId(userInfo.userId);
			contentsDao.editReportBankContentsInfo(systemCode, contentsInfo);
		}

		new CurriSiteNavigation(pMode).add(request,"문제리스트").link(model);
		return redirect("/ReportBank.cmd?cmd=reportBankContentsList&pCourseId="+pCourseId+"&pBankId="+pBankId);

	}

	public ActionForward reportBankContentsShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pReportType = StringUtil.nvl(request.getParameter("pReportType"));
		int pBankCode = StringUtil.nvl(request.getParameter("pBankCode"),0);
		int pReportNo = StringUtil.nvl(request.getParameter("pReportNo"),0);

		ReportBankDAO bankDao = new ReportBankDAO();
		ReportBankContentsDTO contentsInfo =  bankDao.getReportBkContentsInfo(systemCode,pCourseId,pBankCode, pReportNo);

		model.put("contentsInfo",contentsInfo);
		model.put("pReportType",pReportType);
	    return forward(request, model, "/coursereport/report_bk_contents_show.jsp");
	}
}

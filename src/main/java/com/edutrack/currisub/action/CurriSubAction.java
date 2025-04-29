
package com.edutrack.currisub.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.CommonUtil;
import com.edutrack.common.DateSetter;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.curridate.dao.CurriDateDAO;
import com.edutrack.curridate.dto.CurriDateDTO;
import com.edutrack.curristudent.dao.StudentDAO;
import com.edutrack.curristudent.dto.StudentDTO;
import com.edutrack.currisub.dao.CompanyCourseDAO;
import com.edutrack.currisub.dao.CurriCourseEnrollDAO;
import com.edutrack.currisub.dao.CurriSubCourseDAO;
import com.edutrack.currisub.dao.CurriSubDAO;
import com.edutrack.currisub.dao.CurriSubJogyoDAO;
import com.edutrack.currisub.dto.CompanyCourseDTO;
import com.edutrack.currisub.dto.CurriContentsDTO;
import com.edutrack.currisub.dto.CurriSubDTO;
import com.edutrack.currisub.dto.CurriSubInfoDTO;
import com.edutrack.curritop.dao.CurriCategoryDAO;
import com.edutrack.curritop.dao.CurriTopDAO;
import com.edutrack.curritop.dto.CurriCategoryDTO;
import com.edutrack.curritop.dto.CurriTopDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;

/*
 * @author Jamfam
 *
 * 개설과정 관리
 */

public class CurriSubAction extends StrutsDispatchAction{
	/**
	 *
	 */
	public CurriSubAction() {
		super();
		// TODO Auto-generated constructor stub
	}
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();


	/**
	 * 개설과정 리스트를 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriSubList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		String cateCode = StringUtil.nvl(request.getParameter("pCateCode"));
		String curriCode = StringUtil.nvl(request.getParameter("pCurriCode"));
		String curYear = DateTimeUtil.getYear();
		int curriYear = Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriYear"),curYear));

		model.put("cateCode", cateCode);
		model.put("curriCode", curriCode);
		model.put("curriYear",Integer.toString(curriYear));

		new SiteNavigation(pMode).add(request,"개설과정관리").link(model);
		return forward(request, model, "/curri_sub/curriSubList.jsp");
	}

	/**
	 * 개설과정 생성 폼을 띄워준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriSubWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		//---- 추가해야할 사항
		//---- config 설정에서 코드값 자동 등록인지 입력인지 확인해서 cateCode 넘겨 주는 부분 설정 해야함

		String systemCode = UserBroker.getSystemCode(request);
		String curriCode = StringUtil.nvl(request.getParameter("pCurriCode"),"");
		String cateCode = StringUtil.nvl(request.getParameter("pCateCode"),"");
		int curriYear = Integer.parseInt(request.getParameter("pCurriYear"));

		//---- 과정의 정보를 가져옵니다.
		CurriTopDAO curriTopDao = new CurriTopDAO();
		CurriTopDTO curriTopDto = new CurriTopDTO();
		curriTopDto = curriTopDao.getCurriTopInfo(systemCode, curriCode);

		//---- 입력정보 세팅
		model.put("pCateCode", cateCode);
		model.put("pCurriCode", curriCode);
		model.put("curriName", StringUtil.nvl(curriTopDto.getCurriName()));
		model.put("curriProperty2", StringUtil.nvl(curriTopDto.getCurriProperty2()));
		model.put("pCurriYear", Integer.toString(curriYear));
		model.put("pCredit", Integer.toString(curriTopDto.getCredit()));

		DateSetter ds = new DateSetter("").link(model);
		model.put("enroll_date", ds);
		model.put("cancel_date", ds);
		model.put("service_date", ds);
		model.put("chung_end", ds);
		model.put("assessment_end", ds);
		model.put("complete_end", ds);

		new SiteNavigation(MYPAGE).add(request,"개설과정관리").link(model);
		return forward(request, model, "/curri_sub/curriSubWrite.jsp");
	}

	/**
	 * 개설과정분류 생성 폼을 띄워준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriSubEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String curriCode = StringUtil.nvl(request.getParameter("pCurriCode"),"");
		String cateCode = StringUtil.nvl(request.getParameter("pCateCode"),"");
		int curriYear = Integer.parseInt(request.getParameter("pCurriYear"));
		int curriTerm = Integer.parseInt(request.getParameter("pCurriTerm"));

//		---- 과정의 정보를 가져옵니다.
		CurriTopDAO curriTopDao = new CurriTopDAO();
		CurriTopDTO curriTopDto = new CurriTopDTO();
		curriTopDto = curriTopDao.getCurriTopInfo(systemCode, curriCode);
		String curriType = StringUtil.nvl(curriTopDto.getCurriProperty2(),"R");

		model.put("curriProperty2", curriType);
		model.put("pCateCode", cateCode);
		model.put("pCurriCode", curriCode);
		model.put("pCurriYear", Integer.toString(curriYear));
		model.put("pCurriTerm", Integer.toString(curriTerm));

		//---- 과정 개설 정보를 가져옵니다.
		CurriSubDAO curriSubDao = new CurriSubDAO();
		CurriSubInfoDTO curriSubInfo = new CurriSubInfoDTO();
		curriSubInfo = curriSubDao.getCurriSubInfo(systemCode, curriCode, curriYear, curriTerm);
		String	pGovernment	=	"";
		String	pPrintImg	=	"";
		String	pSupportAdd	=	"";
		pGovernment	=	StringUtil.nvl(curriSubInfo.getGovernment());
		pPrintImg	=	StringUtil.nvl(curriSubInfo.getPrintImg());
		pSupportAdd	=	StringUtil.nvl(curriSubInfo.getSupportAdd());

		model.put("curriName", StringUtil.nvl(curriSubInfo.getCurriName()));

		model.put("pProfId", StringUtil.nvl(curriSubInfo.getProfId()));
		model.put("pPrice", Integer.toString(curriSubInfo.getPrice()));
		model.put("pCredit", StringUtil.nvl(Integer.toString(curriSubInfo.getCredit()),"0"));
		model.put("pSampleContents", StringUtil.nvl(curriSubInfo.getSampleContents()));
		model.put("pCancelConfigDay", StringUtil.nvl(Integer.toString(curriSubInfo.getCancelConfigDay()),"0"));
		model.put("pCancelConfigStudy", StringUtil.nvl(Integer.toString(curriSubInfo.getCancelConfigStudy()),"0"));
		model.put("pCourseType", StringUtil.nvl(curriSubInfo.getCurriType()));
		model.put("pGovernment", pGovernment);
		model.put("pPrintImg", pPrintImg);
		model.put("pSupportAdd", pSupportAdd);
		model.put("pHakgiTerm", String.valueOf(curriSubInfo.getHakgiTerm()));

		DateSetter ds = new DateSetter((String)StringUtil.nvl(curriSubInfo.getEnrollStart())).link(model);
		if(curriType.equals("R"))
		{// 정규과정의 경우
			ds = new DateSetter(curriSubInfo.getEnrollStart(),curriSubInfo.getEnrollEnd()).link(model);
			model.put("enroll_date", ds);

			ds = new DateSetter(curriSubInfo.getCancelStart(),curriSubInfo.getCancelEnd()).link(model);
			model.put("cancel_date", ds);

			ds = new DateSetter(StringUtil.nvl(curriSubInfo.getServiceStart()),StringUtil.nvl(curriSubInfo.getServiceEnd())).link(model);
			model.put("service_date", ds);

			ds = new DateSetter(StringUtil.nvl(curriSubInfo.getChungEnd())).link(model);
			model.put("chung_end", ds);

			ds = new DateSetter(StringUtil.nvl(curriSubInfo.getAssessmentEnd())).link(model);
			model.put("assessment_end", ds);
			model.put("assessment_end_date", StringUtil.nvl(curriSubInfo.getAssessmentEnd()));

			ds = new DateSetter(StringUtil.nvl(curriSubInfo.getCompleteEnd())).link(model);
			model.put("complete_end", ds);


			String chkLimitY = "";
			String chkLimitN = "";
			if(StringUtil.nvl(curriSubInfo.getFlagLimit(),"N").equals("Y")) chkLimitY = "checked";
			else chkLimitN = "checked";
			model.put("chkLimitY", chkLimitY);
			model.put("chkLimitN", chkLimitN);
			model.put("pScoreGubun", StringUtil.nvl(curriSubInfo.getScoreGubun(),"1"));
			model.put("pEvalGubun" , StringUtil.nvl(curriSubInfo.getEvalGubun(),"1"));			

			model.put("pLimitNum", StringUtil.nvl(Integer.toString(curriSubInfo.getLimitNum()),"0"));
			model.put("pCompleteAverage", Integer.toString(curriSubInfo.getCompleteAverage()));
			model.put("pCompleteCourse", Integer.toString(curriSubInfo.getCompleteCourse()));
//			 정규과정 End
		}else{
			model.put("pServiceStart",curriSubInfo.getServiceStart());
		}
		
		//	 기업과정 관련 정보를 가져옵니다.
		CompanyCourseDAO companyCourseDao = new CompanyCourseDAO();
		CompanyCourseDTO companyCourseDto = new CompanyCourseDTO();
		companyCourseDto = companyCourseDao.getCurriCompanyCourseList(systemCode, curriCode, curriYear, curriTerm, "");

		if(companyCourseDto!=null){
			model.put("pCompanyCourseId", String.valueOf(companyCourseDto.getCompanyCourseId()));
			model.put("pDeptDaeCode", String.valueOf(companyCourseDto.getDeptDaeCode()));
			model.put("pDeptSoCode", String.valueOf(companyCourseDto.getDeptSoCode()));
			model.put("pCompanyImg", companyCourseDto.getCompanyImg());
		}

		new SiteNavigation(MYPAGE).add(request,"개설과정관리").link(model);
		return forward(request, model, "/curri_sub/curriSubEdit.jsp");
	}

	/**
	 * 개설과정정보를 저장한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriSubRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("--------------------- curriSubRegist Start ---------------------------");
		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);

		CurriSubDAO curriSubDao = new CurriSubDAO();
		CurriSubDTO curriSubDto = new CurriSubDTO();
		CompanyCourseDAO companyCourseDao = new CompanyCourseDAO();
		CompanyCourseDTO companyCourseDto = new CompanyCourseDTO();


		MultipartRequest multipartRequest = null;
		String objName 			= "";
		String uploadFileName	= "";
		String uploadFilePath	= "";
		String FilePath = FileUtil.IMG_DIR + systemCode + "/curri_sub/";
		log.debug("FilePath ==>"+FilePath);

		//	파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath, "currisubImg_");

		// 	파라미터를 빼온다.
		multipartRequest	= uploadEntity.getMultipart();
		String status		= uploadEntity.getStatus();
		String pOldFileName[] = new String[3];

		for (int i=1; i<=2; i++) {
			pOldFileName[i] = StringUtil.nvl(multipartRequest.getParameter("pOldFile"+String.valueOf(i)));
		}
		curriSubDto.setPrintImg(pOldFileName[1]);
		companyCourseDto.setCompanyImg(pOldFileName[2]);

		uploadFilePath = uploadEntity.getUploadPath();

		if (status.equals("E")) {
			log.debug("첨부 파일 올리려다 실패하였습니다.");

		}else if (status.equals("O")) {
			log.debug("첨부하신 파일이 용량을 초과했습니다.");

		}else if (status.equals("I")) {
			log.debug("첨부 파일의 정보가 잘못되었습니다.");

		}else if(status.equals("S")) {
			// 업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			log.debug("첨부 파일을 첨부하는데 성공하였습니다.");
			ArrayList files = uploadEntity.getFiles();
			UploadFile file = null;

			for (int i = 0 ; i < files.size(); i++) {
				file = (UploadFile)files.get(i);
				objName = file.getObjName();
				uploadFileName = StringUtil.nvl(file.getUploadName());
				if(objName.indexOf("pImg") >= 0 && !uploadFileName.equals("") && uploadFileName!=null) {
					int idx = Integer.parseInt(String.valueOf(objName.charAt(4)));
					log.debug("++++++++++++++++ idx = "+idx);

					if (idx == 1) {
						curriSubDto.setPrintImg(uploadFileName);
					}
					else if(idx == 2) {
						companyCourseDto.setCompanyImg(uploadFileName);
					}

					if (!pOldFileName[idx].equals("")) {		//이전 첨부파일을 삭제할 경우
						log.debug("이전 파일 삭제하기"+FilePath+pOldFileName[idx]);
						FileUtil.delFile(FilePath, pOldFileName[idx]);
					}
				}

			}
		}

		int curriTerm = StringUtil.nvl(multipartRequest.getParameter("pCurriTerm"), 0);
		int hakgiTerm = StringUtil.nvl(multipartRequest.getParameter("pHakgiTerm"), 1);

		String regMode = StringUtil.nvl(multipartRequest.getParameter("pRegMode"),"Add");
		String curriType = StringUtil.nvl(multipartRequest.getParameter("pCurriType"),"R");
		String courseType = StringUtil.nvl(multipartRequest.getParameter("pCourseType"));	//(평생) 과목구분 추가
		String cateCode = multipartRequest.getParameter("pCateCode");
		String curriCode = multipartRequest.getParameter("pCurriCode");
		String curriName = multipartRequest.getParameter("pCurriName");
		int curriYear = StringUtil.nvl(multipartRequest.getParameter("pCurriYear"), 2007);
		String profId = multipartRequest.getParameter("pProfId");
		int price = Integer.parseInt(StringUtil.nvl(multipartRequest.getParameter("pPrice"),"0"));
		int credit = Integer.parseInt(StringUtil.nvl(multipartRequest.getParameter("pCredit"),"0"));
		String enrollStart = "";	String enrollEnd = "";
		String cancelStart = "";	String cancelEnd = "";
		String serviceStart = "";	String serviceEnd = "";
		String chungEnd = "";		String assessmentEnd = "";		String completeEnd = "";
		String limitYn = multipartRequest.getParameter("pLimitYn");
		int limitNum = Integer.parseInt(StringUtil.nvl(multipartRequest.getParameter("pLimitNum"),"0"));
		int completeAverage = Integer.parseInt(StringUtil.nvl(multipartRequest.getParameter("pCompleteAverage"),"0"));
		int completeCourse = Integer.parseInt(StringUtil.nvl(multipartRequest.getParameter("pCompleteCourse"),"0"));

		if(curriType.equals("R"))
		{// 정규과정의 경우
			if(!multipartRequest.getParameter("pEY1").equals("") && !multipartRequest.getParameter("pEM1").equals("") && !multipartRequest.getParameter("pED1").equals(""))
				enrollStart = multipartRequest.getParameter("pEY1")+multipartRequest.getParameter("pEM1")+multipartRequest.getParameter("pED1")+"000001";
			if(!multipartRequest.getParameter("pEY2").equals("") && !multipartRequest.getParameter("pEM2").equals("") && !multipartRequest.getParameter("pED2").equals(""))
				enrollEnd = multipartRequest.getParameter("pEY2")+multipartRequest.getParameter("pEM2")+multipartRequest.getParameter("pED2")+"235959";
			if(!multipartRequest.getParameter("pCY1").equals("") && !multipartRequest.getParameter("pCM1").equals("") && !multipartRequest.getParameter("pCD1").equals(""))
				cancelStart = multipartRequest.getParameter("pCY1")+multipartRequest.getParameter("pCM1")+multipartRequest.getParameter("pCD1")+"000001";
			else cancelStart = enrollStart;
			if(!multipartRequest.getParameter("pCY2").equals("") && !multipartRequest.getParameter("pCM2").equals("") && !multipartRequest.getParameter("pCD2").equals(""))
				cancelEnd = multipartRequest.getParameter("pCY2")+multipartRequest.getParameter("pCM2")+multipartRequest.getParameter("pCD2")+"235959";
			else cancelEnd = enrollEnd;
			if(!multipartRequest.getParameter("pSY1").equals("") && !multipartRequest.getParameter("pSM1").equals("") && !multipartRequest.getParameter("pSD1").equals(""))
				serviceStart = multipartRequest.getParameter("pSY1")+multipartRequest.getParameter("pSM1")+multipartRequest.getParameter("pSD1")+"000001";
			if(!multipartRequest.getParameter("pSY2").equals("") && !multipartRequest.getParameter("pSM2").equals("") && !multipartRequest.getParameter("pSD2").equals(""))
				serviceEnd = multipartRequest.getParameter("pSY2")+multipartRequest.getParameter("pSM2")+multipartRequest.getParameter("pSD2")+"235959";
			if(!multipartRequest.getParameter("pCHEY1").equals("") && !multipartRequest.getParameter("pCHEM1").equals("") && !multipartRequest.getParameter("pCHED1").equals(""))
				chungEnd = multipartRequest.getParameter("pCHEY1")+multipartRequest.getParameter("pCHEM1")+multipartRequest.getParameter("pCHED1")+"235959";
			else chungEnd = serviceEnd;
			if(!multipartRequest.getParameter("pREY1").equals("") && !multipartRequest.getParameter("pREM1").equals("") && !multipartRequest.getParameter("pRED1").equals(""))
				assessmentEnd = multipartRequest.getParameter("pREY1")+multipartRequest.getParameter("pREM1")+multipartRequest.getParameter("pRED1")+"235959";
			if(!multipartRequest.getParameter("pCOEY1").equals("") && !multipartRequest.getParameter("pCOEM1").equals("") && !multipartRequest.getParameter("pCOED1").equals(""))
				completeEnd = multipartRequest.getParameter("pCOEY1")+multipartRequest.getParameter("pCOEM1")+multipartRequest.getParameter("pCOED1")+"235959";

		}else{
			serviceStart = StringUtil.nvl(multipartRequest.getParameter("pServiceStart"));
		}

		String bestYn = multipartRequest.getParameter("pBestYn");
		String scoreGubun = multipartRequest.getParameter("pScoreGubun");	// 성적처리 구분..
		String evalGubun  = multipartRequest.getParameter("pEvalGubun");	// 평가처리 구분..
		String sampleContents = multipartRequest.getParameter("pSampleContents");
		String deptSaleYn = multipartRequest.getParameter("pDeptSaleYn");

		int cancelConfigDay = Integer.parseInt(StringUtil.nvl(multipartRequest.getParameter("pCancelConfigDay"),"0"));
		int cancelConfigStudy = Integer.parseInt(StringUtil.nvl(multipartRequest.getParameter("pCancelConfigStudy"),"0"));


		String government = StringUtil.nvl(multipartRequest.getParameter("government"), "N");
		String pSupportAdd = StringUtil.nvl(multipartRequest.getParameter("pSupportAdd"), "");			// 추가지원정보

		String pCompanyCourseId = StringUtil.nvl(multipartRequest.getParameter("pCompanyCourseId"), "0");	//  기업과정id
		String pDeptDaecode = StringUtil.nvl(multipartRequest.getParameter("pDeptDaecode"), "");			// 소속코드(대)
		String pDeptSocode = StringUtil.nvl(multipartRequest.getParameter("pDeptSocode"), "");			// 소속코드(소)

		int	array1 = Integer.parseInt(StringUtil.nvl(multipartRequest.getParameter("array1"), "0"));		// 개별과정 수
		String[] pSfId = new String[array1];	//	개별과정 id
		String[] pCourseName = new String[array1];	//	개별과정명
		String[] pStudyStart = new String[array1];	//	개별과정명
		String[] pStudyEnd = new String[array1];	//	개별과정명
		String[] pStudyWhere = new String[array1];	//	개별과정명
		String[] pSucYn = new String[array1];	//	개별과정명
		for (int i=0; i<array1; i++) {
			pSfId[i]		=	StringUtil.nvl(multipartRequest.getParameter("pSfId" + String.valueOf(i)), "");
			pCourseName[i]	=	StringUtil.nvl(multipartRequest.getParameter("pCourseName" + String.valueOf(i)), "");
			pStudyWhere[i]	=	StringUtil.nvl(multipartRequest.getParameter("pStudyWhere" + String.valueOf(i)), "");
			pSucYn[i]		=	StringUtil.nvl(multipartRequest.getParameter("pSucYn" + String.valueOf(i)), "N");

			if (!multipartRequest.getParameter("pStudyStartY"+String.valueOf(i)+"1").equals("") &&
				!multipartRequest.getParameter("pStudyStartM"+String.valueOf(i)+"1").equals("") &&
				!multipartRequest.getParameter("pStudyStartD"+String.valueOf(i)+"1").equals("")) {
					pStudyStart[i] = multipartRequest.getParameter("pStudyStartY"+String.valueOf(i)+"1") +
									 multipartRequest.getParameter("pStudyStartM"+String.valueOf(i)+"1") +
									 multipartRequest.getParameter("pStudyStartD"+String.valueOf(i)+"1") + "000001";
			}
			if (!multipartRequest.getParameter("pStudyStartY"+String.valueOf(i)+"2").equals("") &&
				!multipartRequest.getParameter("pStudyStartM"+String.valueOf(i)+"2").equals("") &&
				!multipartRequest.getParameter("pStudyStartD"+String.valueOf(i)+"2").equals("")) {
					pStudyEnd[i] = multipartRequest.getParameter("pStudyStartY"+String.valueOf(i)+"2") +
								   multipartRequest.getParameter("pStudyStartM"+String.valueOf(i)+"2") +
								   multipartRequest.getParameter("pStudyStartD"+String.valueOf(i)+"2") + "235959";
			}

		}

		int	arrayadd1 = Integer.parseInt(StringUtil.nvl(multipartRequest.getParameter("arrayadd1"), "0"));		// 추가개별과정 수
		String[] pAddSfId = new String[arrayadd1];	//	개별과정 id
		String[] pAddCourseName = new String[arrayadd1];	//	개별과정명
		String[] pAddStudyStart = new String[arrayadd1];	//	개별과정명
		String[] pAddStudyEnd = new String[arrayadd1];	//	개별과정명
		String[] pAddStudyWhere = new String[arrayadd1];	//	개별과정명
		String[] pAddSucYn = new String[arrayadd1];	//	개별과정명
		for (int i=0; i<arrayadd1; i++) {
			pAddSfId[i]		=	StringUtil.nvl(multipartRequest.getParameter("pAddSfId" + String.valueOf(i)), "");
			pAddCourseName[i]	=	StringUtil.nvl(multipartRequest.getParameter("pAddCourseName" + String.valueOf(i)), "");
			pAddStudyWhere[i]	=	StringUtil.nvl(multipartRequest.getParameter("pAddStudyWhere" + String.valueOf(i)), "");
			pAddSucYn[i]		=	StringUtil.nvl(multipartRequest.getParameter("pAddSucYn" + String.valueOf(i)), "N");

			if (!multipartRequest.getParameter("pAddStudyStartY"+String.valueOf(i)+"1").equals("") &&
				!multipartRequest.getParameter("pAddStudyStartM"+String.valueOf(i)+"1").equals("") &&
				!multipartRequest.getParameter("pAddStudyStartD"+String.valueOf(i)+"1").equals("")) {
					pAddStudyStart[i] = multipartRequest.getParameter("pAddStudyStartY"+String.valueOf(i)+"1") +
									    multipartRequest.getParameter("pAddStudyStartM"+String.valueOf(i)+"1") +
									    multipartRequest.getParameter("pAddStudyStartD"+String.valueOf(i)+"1") + "000001";
			}
			if (!multipartRequest.getParameter("pAddStudyStartY"+String.valueOf(i)+"2").equals("") &&
				!multipartRequest.getParameter("pAddStudyStartM"+String.valueOf(i)+"2").equals("") &&
				!multipartRequest.getParameter("pAddStudyStartD"+String.valueOf(i)+"2").equals("")) {
					pAddStudyEnd[i] = multipartRequest.getParameter("pAddStudyStartY"+String.valueOf(i)+"2") +
								      multipartRequest.getParameter("pAddStudyStartM"+String.valueOf(i)+"2") +
								      multipartRequest.getParameter("pAddStudyStartD"+String.valueOf(i)+"2") + "235959";
			}

		}

		curriSubDto.setSystemCode(systemCode);
		curriSubDto.setCurriCode(curriCode);
		curriSubDto.setCurriName(curriName);
		curriSubDto.setCurriYear(curriYear);
		curriSubDto.setCurriTerm(curriTerm);
		curriSubDto.setProfId(profId);
		curriSubDto.setPrice(price);
		curriSubDto.setCredit(credit);
		curriSubDto.setEnrollStart(enrollStart);
		curriSubDto.setEnrollEnd(enrollEnd);
		curriSubDto.setCancelStart(cancelStart);
		curriSubDto.setCancelEnd(cancelEnd);
		curriSubDto.setServiceStart(serviceStart);
		curriSubDto.setServiceEnd(serviceEnd);
		curriSubDto.setChungEnd(chungEnd);
		curriSubDto.setFlagLimit(limitYn);
		curriSubDto.setLimitNum(limitNum);
		curriSubDto.setCompleteAverage(completeAverage);
		curriSubDto.setCompleteCourse(completeCourse);
		curriSubDto.setAssessmentEnd(assessmentEnd);
		curriSubDto.setCompleteEnd(completeEnd);
		curriSubDto.setRegId(regId);
		curriSubDto.setBestYn(bestYn);
		curriSubDto.setScoreGubun(scoreGubun);
		curriSubDto.setEvalGubun(evalGubun);
		curriSubDto.setSampleContents(sampleContents);
		curriSubDto.setCancelConfigDay(cancelConfigDay);
		curriSubDto.setCancelConfigstudy(cancelConfigStudy);

		curriSubDto.setCurriType(courseType);
		curriSubDto.setHakgiTerm(hakgiTerm);

		curriSubDto.setGovernment(government);
		//curriSubDto.setPrintImg(pPrintImg);
		if (government.equals("Y")) {
			curriSubDto.setSupportAdd(pSupportAdd);
		}

		log.debug("---- setDto End");

		CurriSubInfoDTO oldInfo = new CurriSubInfoDTO();
		String oldServiceStart = "";
		String oldServiceEnd = "";
		String oldChungEnd = "";
		boolean chkDateModify = false;
		int chkStudentCnt = 0;
		if(curriType.equals("R") && regMode.equals("Edit")){
			//-- 정규 과정의 경우 일자 수정을 대비 하여 수강일, 청강일을 불러온다.
			oldInfo = curriSubDao.getCurriSubInfo(systemCode, curriCode, curriYear, curriTerm);
			oldServiceStart = oldInfo.getServiceStart();
			oldServiceEnd = oldInfo.getServiceEnd();
			oldChungEnd = oldInfo.getChungEnd();
			if(!serviceStart.equals(oldServiceStart) || !serviceEnd.equals(oldServiceEnd) || !chungEnd.equals(oldChungEnd))
				chkDateModify = true;
			StudentDAO studentDao = new StudentDAO();
			chkStudentCnt = studentDao.getTotCount(systemCode, curriCode, curriYear, curriTerm,"");
			log.debug("==========chkStudentCnt = "+chkStudentCnt);
		}


		int retVal = 0;
		String msg = "";
		String returnUrl = "/CurriSub.cmd?cmd=curriSubList&pMode="+MYPAGE+"&pCateCode="+cateCode+"&pCurriCode="+curriCode+"&pCurriYear="+curriYear;

		int	maxCurriTerm = curriSubDao.getMaxCurriTerm(systemCode, curriCode, curriYear);
		
		if (regMode.equals("Add")) {// 입력모드
			retVal = curriSubDao.addCurriSubInfo(curriSubDto);

			if(retVal > 0) {

				int retVal2 = 0;
				CurriSubCourseDAO curriSubCourseDao = new CurriSubCourseDAO();
				retVal2 = curriSubCourseDao.topCourseToSubCourse(systemCode, curriCode, curriYear, maxCurriTerm, regId);
				
				// 조교관리...
				// 조교를 삭제후 등록한다..
			 	String pJogyoId = StringUtil.nvl(multipartRequest.getParameter("pJogyoId"));
			 	String[] aJogyoId = pJogyoId.split(",");
			 	CurriSubJogyoDAO curriSubJogyoDao = new CurriSubJogyoDAO();
			 	curriSubJogyoDao.addCurriSubJogyo(systemCode, curriCode, curriYear, maxCurriTerm, aJogyoId, regId);
				
			}


			msg = "등록완료";
		}
		else if (regMode.equals("Edit")) {

			retVal = curriSubDao.editCurriSubInfo(curriSubDto);

			if(retVal > 0 ){

				if(chkDateModify && chkStudentCnt > 0){
					//-- 정규 과정의 경우 일자 수정과 등록된 수강생이 있는 경우 수강생 테이블의 수강일자,청강일자를 변경 하여 준다.
					StudentDAO studentDao = new StudentDAO();
					StudentDTO studentDto = new StudentDTO();
					studentDto.setServicestartDate(serviceStart);
					studentDto.setServiceendDate(serviceEnd);
					studentDto.setChungendDate(chungEnd);
					studentDto.setRegId(regId);
					studentDto.setSystemCode(systemCode);
					studentDto.setCurriCode(curriCode);
					studentDto.setCurriYear(curriYear);
					studentDto.setCurriTerm(curriTerm);

					retVal = studentDao.editStudentDateInfo(studentDto);
				}
			}

			msg = "수정완료";
			if(retVal <= 0){
				returnUrl = "/CurriSub.cmd?cmd=curriSubEdit&pMode="+MYPAGE+"&pCateCode="+cateCode+"&pCurriCode="+curriCode+"&pCurriYear="+curriYear+"&pCurriTerm="+curriTerm;
				msg = "수정오류 다시 진행해 주세요";
			}
		}

		
		new SiteNavigation(MYPAGE).add(request,"개설과정관리").link(model);
		return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}

	/**
	 * 개설과정 정보를 삭제한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriSubDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String curriCode = request.getParameter("pCurriCode");
		String cateCode = request.getParameter("pCateCode");
		int curriYear = Integer.parseInt(request.getParameter("pCurriYear"));
		int curriTerm = Integer.parseInt(request.getParameter("pCurriTerm"));

		CurriSubCourseDAO curriSubcourseDao = new CurriSubCourseDAO();
		int	totCnt = curriSubcourseDao.getTotCount(systemCode, curriCode, curriYear, curriTerm);
		String msg = "";
		String returnUrl = "";

		if (totCnt==0) {

	//		---- 개설과정분류 기업과정 추가부분 디비 정보 삭제
			CompanyCourseDAO companyCourseDao = new CompanyCourseDAO();
			int retVal2 = companyCourseDao.delCompanyCourse(systemCode, curriCode, curriYear, curriTerm, "");

			//---- 개설과정분류 디비 정보 삭제
			CurriSubDAO curriSubDao = new CurriSubDAO();
			int retVal = 0;
			retVal = curriSubDao.delCurriSubInfo(systemCode, curriCode, curriYear, curriTerm);
			if(retVal > 0) //---- 디비정보 삭제 성공했을 경우
			{
				msg = "삭제하였습니다.";
				returnUrl = "/CurriSub.cmd?cmd=curriSubList&pMode="+MYPAGE+"&pCateCode="+cateCode+"&pCurriCode="+curriCode+"&pCurriYear="+curriYear;
				CurriSubDTO curriSubDto = new CurriSubDTO();

				curriSubDto.setSystemCode(systemCode);
				curriSubDto.setCurriCode(curriCode);
				curriSubDto.setCurriYear(curriYear);
				curriSubDto.setCurriTerm(curriTerm);
	//			 색인 작업용
		        String	sDbName = CommonUtil.WoorinDasenCurriDbId;
			 	int searchIdx = 0;
				if(searchIdx >0) {

				}
			}else{
				msg = "삭제 실패 하였습니다.";
				returnUrl = "/CurriSub.cmd?cmd=curriSubEdit&pMode="+MYPAGE+"&pCateCode="+cateCode+"&pCurriCode="+curriCode+"&pCurriYear="+curriYear+"&pCurriTerm="+curriTerm;
			}
		}
		else {
			msg = "삭제 실패 하였습니다. <br> 연결된 과목정보를 먼저 삭제 한 다음 개설과정을 삭제하세요.";
			returnUrl = "/CurriSub.cmd?cmd=curriSubList&pMode="+MYPAGE+"&pCateCode="+cateCode+"&pCurriCode="+curriCode+"&pCurriYear="+curriYear;
		}
		
		
		// 조교삭제...
		CurriSubJogyoDAO curriSubJogyoDao = new CurriSubJogyoDAO();
		curriSubJogyoDao.delCurriSubJogyo(systemCode, curriCode, curriYear, curriTerm, null);
		
		new SiteNavigation(MYPAGE).add(request,"개설과정관리").link(model);
		return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}


	/**
	 * 나의학습실 -> 수강신청 페이지 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward currentList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("-------------------- currentList Start -------------------------");
		String 	systemCode 	= 	UserBroker.getSystemCode(request);
		String	schoolYear	=	StringUtil.nvl(UserBroker.getSchoolYear(request), "1");	//학년
		String	userId		=	UserBroker.getUserId(request);
		String	userType	=	UserBroker.getUserType(request);
		String	property1	=	StringUtil.nvl(request.getParameter("pProperty1"), "Cyber");
		String	pare_code1	=	StringUtil.nvl(request.getParameter("pare_code1"));
		String	pare_code2	=	StringUtil.nvl(request.getParameter("pPare_code2"), "1");
		String	cate_code	=	StringUtil.nvl(request.getParameter("cate_code"));
		String	pRegMode	=	StringUtil.nvl(request.getParameter("pRegMode"), "WRITE");		
		String	addWhere	=	" AND enroll_start <= "+CommonUtil.getCurrentDate().substring(0, 14)+" AND cancel_end >= "+CommonUtil.getCurrentDate().substring(0, 14);
		int		currentYear	=	Integer.parseInt(CommonUtil.getCurrentDate().substring(0, 4));
		
		Map		paramMap	=	new HashMap();
		paramMap.put("pUserId", userId);
		paramMap.put("pSchoolYear", schoolYear);
		
		//-- 학생의 수강신청 여부
		int		pStuCurriCount	=	0;
		StudentDAO	stuDao		=	new StudentDAO();
		pStuCurriCount			=	stuDao.getStuCurriCount(systemCode, paramMap);
		if(pStuCurriCount > 0) {
			pRegMode	=	"EDIT";
		}
		
		//-- 관리자, 교수자, 조교자일때는 학년 세팅이 되지 않으므로.....
		if(userType.equals("M") || userType.equals("P") || userType.equals("J"))	schoolYear	=	pare_code2;
		
		CurriCourseEnrollDAO	enrollDao	=	new CurriCourseEnrollDAO();
		CurriDateDAO			dateDao		=	new CurriDateDAO();
		CurriDateDTO			dateDto		=	dateDao.getCurriDateInfo(systemCode, currentYear, 0, addWhere);
		
		RowSet	rs			=	enrollDao.getCurriCategoryCountList(systemCode, pare_code1, schoolYear, cate_code);
		int		No			=	0;
		
//		while(rs.next()) {			
//			if(No == 0) paramMap.put("code_size", StringUtil.nvl(rs.getString("cate_cnt"), "0"));			
//			paramMap.put("pareCode2["+No+"]", StringUtil.nvl(rs.getString("pare_code2")));
//			paramMap.put("cateCode["+No+"]", StringUtil.nvl(rs.getString("cate_code")));
//			No++;
//		}
		
		model.put("curriEnrollList", enrollDao.getCurriCourseEnrollList(systemCode, pare_code1, schoolYear, cate_code, property1, "R", paramMap));
		model.put("curriDateDto", dateDto);
		model.put("ParamMap", paramMap);
		model.put("pRegMode", pRegMode);
		model.put("pPare_code2", pare_code2);
		
		new SiteNavigation(MYPAGE).add(request,"수강신청").link(model);
		log.debug("-------------------- currentList End   -------------------------");
		return forward(request, model, "/curri_sub/enrollCurrentList.jsp");
	}

	/**
	 * 진행 중인 개설과정 리스트를 가져온다.(마이페이지에서)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward currentMypageList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("-------------------- currentList Start -------------------------");
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String property1 = StringUtil.nvl(request.getParameter("pProperty1"),"Cyber");
		if(userId.equals("") || userId.equals("@"))
			return alertAndExit(UserBroker.getSystemCode(request), model, "먼저 로그인을 하셔야 합니다.", "/Main.cmd?cmd=mainShow&pMode=Home",HOME);

		CurriSubDAO curriSubDao = new CurriSubDAO();

		RowSet curriRList = curriSubDao.currentCurriList(systemCode, "", "", "", property1, "R", "", 0);
		model.put("curriRList", curriRList);
		RowSet curriOList = curriSubDao.currentCurriList(systemCode, "", "", "", property1, "O", "", 0);
		model.put("curriOList", curriOList);

		model.put("pProperty1", property1);

		new SiteNavigation(MYPAGE).add(request,"진행중인과정").link(model);
		log.debug("-------------------- currentList End   -------------------------");
		return forward(request, model, "/curri_sub/currentMypageList.jsp");
	}
	
	
	/**
	 * 종료된과정리스트
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward completeMypageList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("-------------------- currentList Start -------------------------");
		String systemCode = UserBroker.getSystemCode(request);
		String property1 = StringUtil.nvl(request.getParameter("pProperty1"),"Cyber");
		
		CurriSubDAO curriSubDao = new CurriSubDAO();

		//RowSet curriRList = curriSubDao.completeCurriList(systemCode, "",property1,"R", );
		//model.put("curriRList", curriRList);
		RowSet rs = curriSubDao.getCurriYearTerm(systemCode, null);
		
		model.put("pProperty1", property1);
		model.put("rs", rs);
		
		new SiteNavigation(MYPAGE).add(request,"강의종료과정").link(model);
		log.debug("-------------------- currentList End   -------------------------");
		return forward(request, model, "/curri_sub/completeMypageList.jsp");
	}
	

	/**
	 * 
	 * @param CurriYear
	 * @param HakgiTerm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ArrayList completeMypageListAuto(String property1, int CurriYear, int HakgiTerm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		log.debug("-------------------- currentList Start -------------------------");
		String systemCode = UserBroker.getSystemCode(request);
		
		CurriSubDAO curriSubDao = new CurriSubDAO();
		CurriSubDTO curriSubDto = new CurriSubDTO();
		
		ArrayList aList = new ArrayList();
		//RowSet curriRList = curriSubDao.completeCurriList(systemCode, "",property1,"R", CurriYear, HakgiTerm);
		RowSet list = curriSubDao.completeCurriList(systemCode, "",property1,"R", CurriYear, HakgiTerm);
		
		while(list.next()){
			curriSubDto = new CurriSubDTO();
			
			curriSubDto.setEnrollStart(DateTimeUtil.getDateType(3,StringUtil.nvl(list.getString("enroll_start"))));
			curriSubDto.setEnrollEnd(DateTimeUtil.getDateType(3,StringUtil.nvl(list.getString("enroll_start"))));
			curriSubDto.setServiceStart(DateTimeUtil.getDateType(3,StringUtil.nvl(list.getString("service_start"))));
			curriSubDto.setServiceEnd(DateTimeUtil.getDateType(3,StringUtil.nvl(list.getString("service_end"))));
			curriSubDto.setCurriCode(StringUtil.nvl(list.getString("curri_code")));
			curriSubDto.setCurriName(StringUtil.nvl(list.getString("curri_name")));
			curriSubDto.setCurriYear(StringUtil.nvl(list.getInt("curri_year"),0));
			curriSubDto.setCurriTerm(StringUtil.nvl(list.getInt("curri_term"),0));
			curriSubDto.setSampleContents(StringUtil.nvl(list.getString("sample_contents")));
			
			aList.add(curriSubDto);
		}
		
		return aList;
	}
	
	
	/**
	 * 과정분류 리스트를 셀렉트 박스로 보내준다..(ajax)
	 * 2007.05.16 sangsang
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map cateSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);

		Map map = new TreeMap();
		CurriCategoryDAO	curriCategoryDao	=	new CurriCategoryDAO();
		CurriCategoryDTO	curriCategoryDto	=	null;
		RowSet 		rs 		= 	curriCategoryDao.getCategoryListAll(systemCode);
		ArrayList	arrList	=	new ArrayList();
		
		while(rs.next()){
			map.put(StringUtil.nvl(rs.getString("cate_code")),StringUtil.nvl(rs.getString("cate_name")));
		}
		
		rs.close();
		return map;
	}

	/**
	 * 과정분류별 과정 리스트를 셀렉트 박스로 보내준다..(ajax)
	 * 2007.05.16 sangsang
	 * @param cateCode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map curriSelectList(String cateCode, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);

		cateCode = StringUtil.nvl(cateCode);
		CurriTopDAO curriTopDao = new CurriTopDAO();
		Map map = new TreeMap();
		RowSet rs = curriTopDao.getCurriTopListAll(systemCode, cateCode);
		while(rs.next()){
			map.put(StringUtil.nvl(rs.getString("curri_code")),StringUtil.nvl(rs.getString("curri_name")));
		}
		rs.close();
		return map;
	}

	/**
	 * 과정리스트를 자동으로 바꾼다 (ajax)
	 * 2007.05.16 sangsang
	 * @param cateCode
	 * @param curriCode
	 * @param curriYearStr
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList curriSubListAuto(String cateCode, String curriCode, String curriYearStr, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);
		String userType = UserBroker.getUserType(request);
		String loginId = UserBroker.getUserId(request);
		cateCode = StringUtil.nvl(cateCode);
		curriCode = StringUtil.nvl(curriCode);

		String curYear = DateTimeUtil.getYear();
		int curriYear = Integer.parseInt(StringUtil.nvl(curriYearStr,curYear));

		CurriSubDAO curriSubDao = new CurriSubDAO();

		// 리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		CurriSubDTO curriSubDto = null;

		String pProfId = (("P").equals(userType)? loginId : "" );
		RowSet rs = curriSubDao.getCurriSubList(systemCode, cateCode, curriCode, curriYear, pProfId);
		while(rs.next()){
			curriSubDto = new CurriSubDTO();
			curriSubDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
			curriSubDto.setCurriYear(rs.getInt("curri_year"));
			curriSubDto.setCurriTerm(rs.getInt("curri_Term"));
			//curriSubDto.setCurriName(StringUtil.nvl(rs.getString("year_term")));
			curriSubDto.setCurriName(StringUtil.nvl(rs.getString("curri_name")));
			curriSubDto.setEnrollStart(StringUtil.nvl(rs.getString("enroll_date")));
			curriSubDto.setServiceStart(StringUtil.nvl(rs.getString("service_date")));
			curriSubDto.setCourseCount(rs.getInt("course_count"));

			arrayList.add(curriSubDto);
		}
		rs.close();

		return arrayList;
	}

	/**
	 * 입력시점에서 과정기수 중복 체크(Ajax)
	 * 2007.05.08 sangsang
	 * @param cateCode
	 * @param curriCode
	 * @param curriYearStr
	 * @param curriTermStr
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String curriTermCheck(String cateCode, String curriCode, String curriYearStr, String curriTermStr, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);

		cateCode = StringUtil.nvl(cateCode);
		curriCode = StringUtil.nvl(curriCode);
		String curYear = DateTimeUtil.getYear();
		int curriYear = Integer.parseInt(StringUtil.nvl(curriYearStr,curYear));
		int curriTerm = Integer.parseInt(StringUtil.nvl(curriTermStr,"-1"));

		CurriSubDAO curriSubDao = new CurriSubDAO();
		int retVal = curriSubDao.getCurriSubTermCount(systemCode, cateCode, curriCode, curriYear, curriTerm);

		return  String.valueOf(retVal);
	}
	
	
	/**
	 * 정규강좌, 공개강좌 리스트
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward currentCurriContentsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	pPareCode1	=	StringUtil.nvl(request.getParameter("pPareCode1"));
		String	pPareCode2	=	StringUtil.nvl(request.getParameter("pPareCode2"));
		int		dispLine	=	10;
		int		dispPage	=	10;
			
		model.put("pPareCode1", pPareCode1);
		model.put("pPareCode2", pPareCode2);
		model.put("dispLine", dispLine);
		
		new SiteNavigation(HOME).add(request, "정규강좌").link(model);
		return forward(request, model, "/curri_sub/currentCurriContentsList.jsp");
	}
	
	/**
	 * 정규과정 리스트
	 * @param curPage
	 * @param dispLine
	 * @param dispPage
	 * @param pareCode1
	 * @param pareCode2
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO currentCurriContentsAutoList(int curPage, int dispLine, int dispPage, String pareCode1, String pareCode2, HttpServletRequest request) throws Exception {
		String	systemCode	=	UserBroker.getSystemCode(request);

		// 페이징
		curPage = (curPage == 0) ? 1 : curPage;
		dispLine = (dispLine == 0) ? 10 : dispLine;
		dispPage = (dispPage == 0) ? 10 : dispPage;
//System.out.println("============================>> check01");
		CurriSubDAO			curriSubDao	=	new CurriSubDAO();
		CurriContentsDTO	contentsDto	=	null;
		ListDTO		listObj 		= 	null;
		listObj		=	curriSubDao.currentCurriContentsPagingList(curPage, dispLine, dispPage, systemCode, pareCode1, pareCode2);
		
		AjaxListDTO	ajaxListDto		=	new AjaxListDTO();
		ArrayList 	dataList 		= 	new ArrayList();
		
		if (listObj.getItemCount() > 0) {
			RowSet rs	=	listObj.getItemList();
		
			while(rs.next()){
				contentsDto	=	new CurriContentsDTO();
				contentsDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				contentsDto.setCurriYear(rs.getInt("curri_year"));
				contentsDto.setCurriTerm(rs.getInt("curri_term"));
				contentsDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
				contentsDto.setCurriName(StringUtil.nvl(rs.getString("curri_name")));
				contentsDto.setContentsId(StringUtil.nvl(rs.getString("contents_id")));
				contentsDto.setContentsName(StringUtil.nvl(rs.getString("contents_name")));
				contentsDto.setProfName(StringUtil.nvl(rs.getString("prof_name")));
				contentsDto.setStartDate(DateTimeUtil.getDateType(1, StringUtil.nvl(rs.getString("start_date"))));
				contentsDto.setEndDate(DateTimeUtil.getDateType(1, StringUtil.nvl(rs.getString("end_date"))));
				
				dataList.add(contentsDto);
			}
			rs.close();
			
			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());				// 전체 글 수
			ajaxListDto.setDataList(dataList);										// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
			
		}
		return ajaxListDto;
	}
	
	
}
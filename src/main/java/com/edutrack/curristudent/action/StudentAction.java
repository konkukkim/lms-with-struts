package com.edutrack.curristudent.action;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.curristudent.dao.StudentDAO;
import com.edutrack.curristudent.dto.StudentDTO;
import com.edutrack.currisub.dao.CurriContentsDAO;
import com.edutrack.currisub.dao.CurriCourseEnrollDAO;
import com.edutrack.currisub.dao.CurriSubDAO;
import com.edutrack.currisub.dto.CurriSubInfoDTO;
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
import com.edutrack.user.dao.UserDAO;
import com.oreilly.servlet.MultipartRequest;

/*
 * @author Jamfam
 *
 * 수강, 수강생 관리
 */

public class StudentAction extends StrutsDispatchAction{


	public StudentAction() {
		super();
		// TODO Auto-generated constructor stub
	}
	//	 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();


	/**
	 * 현재 진행 중인 개설과정 리스트를 가져온다.
	 * @param SystemCode
	 * @return String
	 * @throws Exception
	 */
	public String currentCurriList(String systemCode) throws Exception {
		String retVal = "";
		RowSet rs = null;
		CurriSubDAO curriSubDao = new CurriSubDAO();
		rs = curriSubDao.currentCurriList(systemCode, "", "", "", "", "", "", 0);
		retVal = "<select name=pCurriYearTerm style='width:150'><option value=''>-- "+StringUtil.ksc2asc("개설 과정 선택")+ "--</option>";

		while(rs.next()) {
			retVal += "<option value='"+StringUtil.nvl(rs.getString("curri_code"))+"|"+rs.getInt("curri_year")+"|"+rs.getInt("curri_term")+"'>"+StringUtil.nvl(rs.getString("curri_name"))+"</option>";
		}
		retVal += "</select>";
		rs.close();
		return retVal;
	}

	/**
	 * 수강종료된 정규과정 수강생 목록 가져오기(페이징처리)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward completeStudentList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("------------------ completeStudentList Start -------------------");
		String systemCode = UserBroker.getSystemCode(request);
		ListDTO studentList = null;
		String pCurriCode = StringUtil.nvl(request.getParameter("pCurriCode"));
		int pCurriYear = Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriYear"),"0"));
		int pCurriTerm = Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriTerm"),"0"));
		String pSTarget = StringUtil.nvl(request.getParameter("pSTarget"));
		String pSWord = StringUtil.nvl(request.getParameter("pSWord"));
		String mode = "confirmPass";

		// ---- 페이징 설정
		int totCnt = 0;
		int curPage = 1;
		if(request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));

		StudentDAO studentDao = new StudentDAO();
		studentList = studentDao.getCompleteStudentList(curPage, systemCode, pCurriCode, pCurriYear, pCurriTerm, pSTarget, pSWord, mode);

		model.put("curriSelectList", currentCurriList(systemCode));
		model.put("pCurriCode", pCurriCode);
		model.put("pCurriYear", Integer.toString(pCurriYear));
		model.put("pCurriTerm", Integer.toString(pCurriTerm));
		model.put("pSTarget", pSTarget);
		model.put("pSWord", pSWord);
		model.put("studentList", studentList);

		log.debug("------------------ completeStudentList End -------------------");
		new SiteNavigation(MYPAGE).add(request,"수료관리").link(model);
		return forward(request, model, "/curri_student/completeStudentList.jsp");
	}
	/**
	 *  상시과정 수강종료된 수강생 목록 가져오기(페이징처리)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward completeStudentList2(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("------------------ completeStudentList2 Start -------------------");
		String systemCode = UserBroker.getSystemCode(request);
		ListDTO studentList = null;
		String pCurriCode = StringUtil.nvl(request.getParameter("pCurriCode"));
		int pCurriYear = Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriYear"),"0"));
		int pCurriTerm = Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriTerm"),"0"));
		String pSTarget = StringUtil.nvl(request.getParameter("pSTarget"));
		String pSWord = StringUtil.nvl(request.getParameter("pSWord"));
		String mode = "confirmPass";

		// ---- 페이징 설정
		int totCnt = 0;
		int curPage = 1;
		if(request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));

		StudentDAO studentDao = new StudentDAO();
		studentList = studentDao.getCompleteStudentList2(curPage, systemCode, pCurriCode, pCurriYear, pCurriTerm, pSTarget, pSWord, mode);

		//-- 총 강의 수 가져오기
		CurriContentsDAO curriContentsDao = new CurriContentsDAO();
		int contentsCnt = curriContentsDao.getFCurriContentsCnt(systemCode, pCurriCode, pCurriYear, pCurriTerm, "");
		model.put("contentsCnt",Integer.toString(contentsCnt));

		model.put("curriSelectList", currentCurriList(systemCode));
		model.put("pCurriCode", pCurriCode);
		model.put("pCurriYear", Integer.toString(pCurriYear));
		model.put("pCurriTerm", Integer.toString(pCurriTerm));
		model.put("pSTarget", pSTarget);
		model.put("pSWord", pSWord);
		model.put("studentList", studentList);

		log.debug("------------------ completeStudentList2 End -------------------");
		new SiteNavigation(MYPAGE).add(request,"수료관리").link(model);
		return forward(request, model, "/curri_student/completeStudentList2.jsp");
	}

	public ActionForward completeStudentRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("------------------ completeStudentRegist Start -------------------");
		String systemCode = UserBroker.getSystemCode(request);
		String modId = UserBroker.getUserId(request);
		ListDTO studentList = null;
		String pCurriCode = StringUtil.nvl(request.getParameter("pCurriCode"));
		int pCurriYear = Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriYear"),"0"));
		int pCurriTerm = Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriTerm"),"0"));
		String pSTarget = StringUtil.nvl(request.getParameter("pSTarget"));
		String pSWord = StringUtil.nvl(request.getParameter("pSWord"));
		int processNum = Integer.parseInt(StringUtil.nvl(request.getParameter("processNum"),"0"));
		String pStudentId[] = new String[processNum];
		String pStatus[] = new String[processNum];


		// ---- 페이징 설정
		int curPage = 1;
		if(request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));

		StudentDAO studentDao = new StudentDAO();
		StudentDTO studentDto = new StudentDTO();
		studentDto.setSystemCode(systemCode);
		studentDto.setCurriCode(pCurriCode);
		studentDto.setCurriYear(pCurriYear);
		studentDto.setCurriTerm(pCurriTerm);
		studentDto.setModId(modId);
		for(int i=0;i<processNum;i++){
			pStudentId[i] = StringUtil.nvl(request.getParameter("pStudentId["+i+"]"));
			pStatus[i] = StringUtil.nvl(request.getParameter("pStatus["+i+"]"));

			studentDto.setUserId(pStudentId[i]);
			studentDto.setEnrollStats(pStatus[i]);
			studentDao.completetudentStatus(studentDto);
		}


		String msg = "정상 처리되었습니다.";
		String returlUrl = "/Student.cmd?cmd=completeStudentList&MENUNO=3&pCurriCode="+pCurriCode+"&pCurriYear="+pCurriYear+"&pCurriTerm="+pCurriTerm+"&curPage="+curPage+"&pSTarget="+pSTarget+"&pSWord="+pSWord;
		log.debug("------------------ completeStudentRegist End -------------------");
		new SiteNavigation(MYPAGE).add(request,"수료관리").link(model);
		return notifyAndExit(systemCode, model, msg, returlUrl,MYPAGE);
	}


	/**
	 * 관리자용 수강신청 폼을 연결한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward enrollStudentWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("------------------ enrollStudentWrite Start -------------------");
		String systemCode = UserBroker.getSystemCode(request);
		String pCurriCode = StringUtil.nvl(request.getParameter("pCurriCode"));
		int pCurriYear = Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriYear"),"0"));
		int pCurriTerm = Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriTerm"),"0"));
//		model.put("curriSelectList", currentCurriList(systemCode));
		model.put("pCurriCode", pCurriCode);
		model.put("pCurriYear", Integer.toString(pCurriYear));
		model.put("pCurriTerm", Integer.toString(pCurriTerm));

		log.debug("------------------ enrollStudentWrite End   -------------------");
		//new SiteNavigation(MYPAGE).add(request,"수강신청관리").link(model);
		return forward(request, model, "/curri_student/enrollStudentWrite.jsp");
	}

	/**
	 * 관리자용 수강생 등록 폼을 연결한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward confirmStudentWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("------------------ enrollStudentWrite Start -------------------");
		String systemCode = UserBroker.getSystemCode(request);
		String pCurriCode = StringUtil.nvl(request.getParameter("pCurriCode"));
		int pCurriYear = Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriYear"),"0"));
		int pCurriTerm = Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriTerm"),"0"));
//		model.put("curriSelectList", currentCurriList(systemCode));
		model.put("pCurriCode", pCurriCode);
		model.put("pCurriYear", Integer.toString(pCurriYear));
		model.put("pCurriTerm", Integer.toString(pCurriTerm));

		log.debug("------------------ enrollStudentWrite End   -------------------");
		//new SiteNavigation(MYPAGE).add(request,"수강신청관리").link(model);
		return forward(request, model, "/curri_student/confirmStudentWrite.jsp");
	}

	
	/**
	 * 수강인증, 수강취소, 수강신청삭제를 실행한다.
	 * @param chkBoxStr
	 * @param sGubun
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public String checkConfirmAjax(String chkBoxStr, String sGubun, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		
		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);
		//String Values = request.getParameter("pEnrollInfo");
		String initMsg = (sGubun.equals("S") ? "수강인증" : (sGubun.equals("N") ? "수강취소" : "삭제" ) );

		String EnrollInfo[] = chkBoxStr.split("/");

		StudentDTO studentDTO = new StudentDTO();
		StudentDAO studentDao = new StudentDAO();
		int retVal = 0;
		int totCnt = 0;
		studentDTO.setRegId(regId);
		studentDTO.setEnrollStats(sGubun);
		studentDTO.setSystemCode(systemCode);
		for(int i=1; i < EnrollInfo.length; i++)
		{
			totCnt++;

			String EnrollUserInfo[] = EnrollInfo[i].split("\\|");
			studentDTO.setUserId(EnrollUserInfo[0]);
			studentDTO.setCurriCode(EnrollUserInfo[1]);
			studentDTO.setCurriYear(Integer.parseInt(EnrollUserInfo[2]));
			studentDTO.setCurriTerm(Integer.parseInt(EnrollUserInfo[3]));
			studentDTO.setEnrollNo(Integer.parseInt(EnrollUserInfo[4]));
			
			if(sGubun.equals("S") || sGubun.equals("N") ){
				retVal += studentDao.changeEnrollStats(studentDTO);
			}else if(sGubun.equals("D")){
				retVal += studentDao.delStudentInfo(studentDTO);
			}
		}
		
		String msg = ""+retVal+"/"+totCnt+"명의 "+initMsg+"을 완료했습니다.";
		return msg;
		
	}

	/**
	 * 수강신청 프로세스
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward enrollProcess(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("------------------ enrollProcess Start -------------------");
		String	systemCode	=	UserBroker.getSystemCode(request);
		String	schoolYear	=	UserBroker.getSchoolYear(request);	//학년
		String	regId		=	UserBroker.getUserId(request);
		String	msg			=	"";
		int 	retVal		=	0;
		String 	returnUrl	=	"";
		String	CurriType	=	StringUtil.nvl(request.getParameter("CurriType"), "R");
		String	pRegMode	=	StringUtil.nvl(request.getParameter("pRegMode"));
		
		CurriCourseEnrollDAO	enrollDao	=	new CurriCourseEnrollDAO();
		RowSet	rs			=	enrollDao.getCurriCategoryCountList(systemCode, "", schoolYear, "");
		int 	codeSize	=	StringUtil.nvl(request.getParameter("pCodeSize"), 0);		
		String	cateCode[]	=	new String[codeSize];
		String	pCurriInfo[] =	new String[codeSize];
		String	pSubInfo[]	=	new String[3];
		int		No			=	0;		
//		Map		paramMap	=	new HashMap();
//		paramMap.put("CodeSize", codeSize);
		
		String	pCurriCode	=	"";
		int		pCurriYear	=	0;
		int		pCurriTerm	=	0;
		String[] checkString = 	new String[2];
		
		while(rs.next()) {
			cateCode[No]	=	StringUtil.nvl(rs.getString("cate_code"));
			pCurriInfo[No]	=	StringUtil.nvl(request.getParameter(cateCode[No]+"_CHK"));
//			paramMap.put("pCurriInfo["+No+"]", pCurriInfo[No]);
			
			if(!cateCode[No].equals("") && !pCurriInfo[No].equals("")) {
				
				pSubInfo	=	StringUtil.split(pCurriInfo[No], "^");
				pCurriCode	=	StringUtil.nvl(pSubInfo[0]);
				pCurriYear	=	StringUtil.nvl(pSubInfo[1], 0);
				pCurriTerm	=	StringUtil.nvl(pSubInfo[2], 0);
				
				//수강신청하기
				if(pRegMode.equals("WRITE")) {
					checkString = checkProcess(systemCode, regId, pCurriCode, pCurriYear, pCurriTerm);
					
					if(Integer.parseInt(checkString[0]) > 0) {
						//---- 수강신청에 에러가 있는 경우
						returnUrl = "/CurriSub.cmd?cmd=currentList&pMode=MyPage&MENUNO=0";
						msg = checkString[1];				
						return alertAndExit(systemCode, model, msg, returnUrl, MYPAGE);
					} else {
						StudentDTO studentDto = new StudentDTO();
						//---- 수강신청 까지만 합니다.
						studentDto.setSystemCode(systemCode);
						studentDto.setUserId(regId);
						studentDto.setCurriCode(pCurriCode);
						studentDto.setCurriYear(pCurriYear);
						studentDto.setCurriTerm(pCurriTerm);
						studentDto.setEnrollStats("E");
						studentDto.setRegId(regId);
						studentDto.setRegMode(pRegMode);

						StudentDAO studentDao = new StudentDAO();
						retVal = studentDao.addStudentInfo(studentDto,CurriType);
						if(retVal > 0) {
							msg = "수강신청을 완료하였습니다. 수강인증을 받아야 수강을 하실 수 있습니다.";
							//returnUrl = "/CurriSub.cmd?cmd=currentList&pMode=Enroll&MENUNO=0";
							// 수강신청후 수강중인 과정으로 이동시킨다.(재수강 신청하는 것을 방지하기 위해)
							returnUrl = "/Main.cmd?cmd=stuCurriList&MENUNO=1&pMode=MyPage";
						} else {
							msg = "수강신청에 실패하였습니다.";
							returnUrl = "/CurriSub.cmd?cmd=currentList&pMode=MyPage&MENUNO=0";
						}				
					}
				} //수강신청 정정하기 
				else {
					StudentDTO studentDto = new StudentDTO();
					//---- 수강신청 까지만 합니다.
					studentDto.setSystemCode(systemCode);
					studentDto.setUserId(regId);
					studentDto.setCurriCode(pCurriCode);
					studentDto.setCurriYear(pCurriYear);
					studentDto.setCurriTerm(pCurriTerm);
					studentDto.setEnrollStats("E");
					studentDto.setRegId(regId);
					studentDto.setRegMode(pRegMode);
					studentDto.setTeamNo(No);

					StudentDAO studentDao = new StudentDAO();
					retVal = studentDao.addStudentInfo(studentDto,CurriType);
					if(retVal > 0) {
						msg = "수강신청 정정을 완료하였습니다. 수강인증을 받아야 수강을 하실 수 있습니다.";
						//returnUrl = "/CurriSub.cmd?cmd=currentList&pMode=Enroll&MENUNO=0";
						// 수강신청후 수강중인 과정으로 이동시킨다.(재수강 신청하는 것을 방지하기 위해)
						returnUrl = "/Main.cmd?cmd=stuCurriList&MENUNO=1&pMode=MyPage";
					} else {
						msg = "수강신청 정정에 실패하였습니다.";
						returnUrl = "/CurriSub.cmd?cmd=currentList&pMode=MyPage&MENUNO=0";
					}
				}
			}
			
			No++;
		}


		log.debug("------------------ enrollProcess End   -------------------");
		return alertAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}

	/**
	 * 수강신청 프로세스 - 수강신청 제한
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String[] checkProcess(String SystemCode, String UserId, String CurriCode, int CurriYear, int CurriTerm) throws Exception {
		//---- 수강신청시 제어해야할 것 등이 들어간다.
		log.debug("------------ checkProcess Start ------------");
		String[] retVal = new String[2];
		int errCode = 0;
		StudentDAO studentDao = new StudentDAO();
		//---- 현재 수강중인 강좌인지 검색
		if(errCode == 0){
			int cnt1 = studentDao.getStudentEnrollCnt(SystemCode, UserId, CurriCode, CurriYear, CurriTerm);
			log.debug("cnt1:"+Integer.toString(cnt1));
			if (cnt1 > 0) {
				errCode = 100;
				retVal[0] = "100";
				retVal[1] = "현재 수강중이거나 수강신청 중인 개설과정 입니다.<br>중복 수강신청은 하실 수 없습니다.";
				log.debug(retVal[1]);
			}
		}
		log.debug("넘어온다.");
		//---- 수강신청에 이상이 없을 시
		if(errCode == 0)
		{
			retVal[0] = "0";
			retVal[1] = "수강신청";
		}
		log.debug("------------ checkProcess End   ------------");
		return retVal;
	}

	/**
	 * 수강 취소에 대한 환불액 등의 안내를 보연준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward enrollCancel(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("------------------ enrollCancel Start -------------------");
		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);
		String msg = "";
        String returnUrl = "";

		String CurriCode = request.getParameter("pCurriCode");
		int CurriYear = Integer.parseInt(request.getParameter("pCurriYear"));
		int CurriTerm = Integer.parseInt(request.getParameter("pCurriTerm"));
		String pPaymentIdx = StringUtil.nvl(request.getParameter("pPaymentIdx"));
		//String CurriType = StringUtil.nvl(request.getParameter("pCurriType"),"R");

		StudentDAO studentDao = new StudentDAO();
		StudentDTO studentDto = new StudentDTO();

		studentDto.setSystemCode(systemCode);
		studentDto.setUserId(regId);
		studentDto.setCurriCode(CurriCode);
		studentDto.setCurriYear(CurriYear);
		studentDto.setCurriTerm(CurriTerm);
log.debug( "======================>" + pPaymentIdx);
		RowSet rs = studentDao.getEnrollCancelInfo(studentDto, pPaymentIdx );
		String totCurriCnt = studentDao.getTotContentsCnt(studentDto);
		String totBookMarkCnt = studentDao.getBookMarkCnt(studentDto);

		model.put("enrollCancelInfo" , rs );
		model.put("totCurriCnt"    , totCurriCnt    );
		model.put("totBookMarkCnt" , totBookMarkCnt );

		log.debug("------------------ enrollCancel End   -------------------");
		new SiteNavigation(MYPAGE).add(request,"수강취소").link(model);
		return forward(request, model, "/curri_student/enrollCancel.jsp");
	}

	/**
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward popEnrollCancel(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);

		return forward(request, model, "/curri_student/popEnrollCancel.jsp");
	}

	/**
	 * 수료처리 과정 리스트를 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward completeCurriList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("");
		log.debug("------------------------- start completeCurriList ------------------------");
		CurriSubDAO curriSubDao = new CurriSubDAO();
		String systemCode = UserBroker.getSystemCode(request);
		String property1 = StringUtil.nvl(request.getParameter("pProperty1"));

		int curPage = Integer.parseInt(StringUtil.nvl(request.getParameter("curPage"),"1"));

		model.put("pProperty1", property1);
		model.put("completeCurriList",curriSubDao.getCompleteCurriList(curPage, systemCode, property1));

		new SiteNavigation(MYPAGE).add(request, "수료관리").link(model);
		return forward(request, model, "/curri_student/completeCurriList.jsp");
	}

	/**
	 * 수강생 일괄 등록 페이지 이동
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward confirmStudentUploadWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String CurriCode = request.getParameter("pCurriCode");
		int CurriYear = Integer.parseInt(request.getParameter("pCurriYear"));
		int CurriTerm = Integer.parseInt(request.getParameter("pCurriTerm"));

		model.put("pCurriCode", CurriCode);
		model.put("pCurriYear", Integer.toString(CurriYear));
		model.put("pCurriTerm", Integer.toString(CurriTerm));

		return forward(request, model, "/curri_student/confirmStudentUploadWrite.jsp");
	}

	/**
	 * 수강생 일괄 등록 업로드합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward confirmStudentUploadRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		//-- 과정정보 가져오기 시작
		String pCurriCode = request.getParameter("pCurriCode");
		int pCurriYear = Integer.parseInt(request.getParameter("pCurriYear"));
		int pCurriTerm = Integer.parseInt(request.getParameter("pCurriTerm"));
		String CurriType = StringUtil.nvl(request.getParameter("pCurriType"),"R");
		String pSystemCode	= 	UserBroker.getSystemCode(request);
		String pUserId = UserBroker.getUserId(request);

		StudentDAO studentDao = new StudentDAO();
		StudentDTO	studentDto = new StudentDTO();
		UserDAO	userDao = new UserDAO();

        MultipartRequest multipartRequest = null;
		String FilePath1 = FileUtil.FILE_DIR+pSystemCode+"/student_upload/";

 		// 파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request,FilePath1,pUserId);

		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();
		String status = uploadEntity.getStatus();


		studentDto.setEnrollStats("S");
		studentDto.setSystemCode(pSystemCode);
		studentDto.setCurriCode(pCurriCode);
		studentDto.setCurriYear(pCurriYear);
		studentDto.setCurriTerm(pCurriTerm);
		studentDto.setRegId(pUserId);

	    String originName = "";
		String objName = "";
		String RfileName = "";
		String SfileName = "";
		String FilePath = "";
		double FileSize = 0;
		String msg ="";
		int chkUser = 0;
		int	chkStudent = 0;
		int	retVal1 = 0;
		int	retVal = 0;
		int	success	= 0;
		int	fail = 0;
		int existStudent = 0;
		int nonUser = 0;
		String strNonUser = "";

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
					String	read_file		=	rootPath+FilePath1+ SfileName;

					FileReader fr			=	new FileReader(read_file);
					BufferedReader	in		=	new BufferedReader(fr);
					String			st		=	null;

					int     k =0;

					while ((st=in.readLine()) != null) {
						int		idx			=	0;
						log.debug("=========================== student Id = "+st);
						if(!st.equals(""))
						{
							studentDto.setUserId(st);
							//-- 등록된 사용자 인지 체크
							chkUser = userDao.userIdCheck(pSystemCode,st);

							if(chkUser <= 0){
								nonUser++;
								if(nonUser > 0)
									strNonUser += ",";
								strNonUser += st;
							}else{
								//-- 기존 수강생으로 등록 되어 있는지 확인
								chkStudent = studentDao.isStudent(pSystemCode,st,pCurriCode,pCurriYear,pCurriTerm);
								if(chkStudent > 0){
									existStudent++;
								}else{
										retVal1 = studentDao.addStudentInfo(studentDto,CurriType);
										if(retVal1 > 0){
											retVal = studentDao.changeEnrollStats(studentDto);
										}else{
											retVal = 0;
										}

										if(retVal > 0){
											success++;
										}else{
											fail++;
										}
								}
							}
						}

					}
					//-- loop end

					//--처리 후 첨부한 파일 삭제
					if(!FilePath1.equals("") && !SfileName.equals("")) {	//이전 첨부파일을 삭제할 경우
						FileUtil.delFile(FilePath1, SfileName);
						log.debug("첨부파일을 삭제하였습니다.");
					}
				}//end if
			}// end for
		}//end if


        msg +=  "입력 파일  처리 결과<br>입력성공 건수 : "+success+"<br>입력실패 건수 : "+fail;
        if(existStudent > 0)
        	msg += "<br>기존 수강생 : "+existStudent;
        if(nonUser > 0){
        	msg += "<br>미등록 학습자 : "+nonUser;
        	msg += "<br>미등록 아이디 : "+strNonUser;
        }
        return notifyCloseReload(pSystemCode,model,msg,"N");
	}

	/**
	 * 수료증을 보여준다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward cerificationPrn(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("------------------ cerificationPrn Start -------------------");
		String	pUserId		=	"";
		String	pUserType	=	UserBroker.getUserType(request);

		if(pUserType.equals("M")) {
			pUserId	=	StringUtil.nvl(request.getParameter("pUserId"));
		} else {
			pUserId	=	UserBroker.getUserId(request);
		}

		String systemCode = UserBroker.getSystemCode(request);
		String pCurriCode = StringUtil.nvl(request.getParameter("pCurriCode"));
		int pCurriYear = Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriYear"),"0"));
		int pCurriTerm = Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriTerm"),"0"));
		int pEnrollNo = Integer.parseInt(StringUtil.nvl(request.getParameter("pEnrollNo"),"0"));

		StudentDAO studentDao = new StudentDAO();
		//int certNo = studentDao.getStudentCertNo(systemCode, pCurriCode, pCurriYear, pCurriTerm,pUserId,pEnrollNo);
		model.put("cerificationPrn", studentDao.getCerificationPrn(pUserId, systemCode, pCurriCode, pCurriYear, pCurriTerm));
		model.put("pUserId", pUserId);
		model.put("pCurriYear", String.valueOf(pCurriYear));
		model.put("pCurriTerm", String.valueOf(pCurriTerm));

		log.debug("------------------ cerificationPrn End   -------------------");
		//new SiteNavigation(MYPAGE).add(request,"수료").link(model);
		return forward(request, model, "/curri_student/cerification_print.jsp");
	}

	/**
	 * 수강 (신청)확인서를 보여준다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward EnrollConfirm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("------------------ EnrollconfirmPrn Start -------------------");
		String pUserId 		= UserBroker.getUserId(request);
		String systemCode 	= UserBroker.getSystemCode(request);
		String pCurriCode 	= StringUtil.nvl(request.getParameter("pCurriCode"));
		int pCurriYear 		= Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriYear"),"0"));
		int pCurriTerm 		= Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriTerm"),"0"));
		int pEnrollNo 		= Integer.parseInt(StringUtil.nvl(request.getParameter("pIdx"),"0"));

		String pEnrollType	= StringUtil.nvl(request.getParameter("pEnrollType"));


		String retUrl		= "";
		if(pEnrollType.equals("confirm"))
			retUrl	=	"/curri_student/enrollConfirm_print.jsp";
		else if(pEnrollType.equals("enroll"))
			retUrl	=	"/curri_student/Enrollstatus_print.jsp";

		StudentDAO studentDao = new StudentDAO();
		model.put("cerificationPrn", studentDao.getenrollConfirmPrn(pUserId, systemCode, pCurriCode, pCurriYear, pCurriTerm, pEnrollType, pEnrollNo ));

		model.put("pUserId", pUserId);
		model.put("pCurriYear", String.valueOf(pCurriYear));
		model.put("pCurriTerm", String.valueOf(pCurriTerm));

		log.debug("------------------ EnrollconfirmPrn End   -------------------");
		return forward(request, model, retUrl );
	}

	/**
	 * 수강신청인증된 수강생 리스트 페이지로 이동
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward confirmStudentList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userType = UserBroker.getUserType(request);
		String userId = UserBroker.getUserId(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		new SiteNavigation(pMode).add(request,"수강생관리").link(model);
		return forward(request, model, "/curri_student/confirmStudentList.jsp");
	}

	/**
	 * 수강신청한 수강생 리스트 페이지로 이동
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward enrollStudentList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		new SiteNavigation(pMode).add(request,"수강신청관리").link(model);
		return forward(request, model, "/curri_student/enrollStudentList.jsp");
	}

	/**
	 * 수강신청자 목록 가져오기(Ajax)
	 * 2007.05.21 sangsang
	 * @param curPage
	 * @param sTarget
	 * @param sWord
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO studentListAuto(int curPage, String mode, String sTarget, String sWord, String curriCode, int curriYear, int curriTerm, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		// sorting
		sTarget = StringUtil.nvl(sTarget,"");
		sWord = AjaxUtil.ajaxDecoding(StringUtil.nvl(sWord));
		curriCode = StringUtil.nvl(curriCode);

		// 페이징
		curPage = (curPage == 0) ? 1 : curPage;

		// 데이타를 담는다.
		ListDTO listObj = null;
		StudentDAO studentDao = new StudentDAO();
		listObj = studentDao.getStudentList(curPage, systemCode, curriCode, curriYear, curriTerm, sTarget, sWord, mode);

		// 리턴 데이타 세팅
		AjaxListDTO ajaxListDto = new AjaxListDTO();
		ArrayList dataList = new ArrayList();
		StudentDTO studentDto = null;

		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				studentDto = new StudentDTO();
				studentDto.setUserId(StringUtil.nvl(rs.getString("user_id")));
				studentDto.setUserName(StringUtil.nvl(rs.getString("user_name")));
				studentDto.setCurriName(StringUtil.nvl(rs.getString("curri_name")));
				studentDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				studentDto.setCurriYear(rs.getInt("curri_year"));
				studentDto.setCurriTerm(rs.getInt("curri_term"));	
				studentDto.setEnrollNo(rs.getInt("enroll_no"));
				studentDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

				dataList.add(studentDto);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}

		return ajaxListDto;
	}

	/**
	 *
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String masterEnrollRegist(String regMode, String pId, String pCurriCode, int pCurriYear, int pCurriTerm, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.debug("------------------ masterEnrollRegist Start -------------------");
		int retVal = 0;
		//String regMode = request.getParameter("regMode");
		String 	systemCode 	= 	UserBroker.getSystemCode(request);
		String 	regId 		= 	UserBroker.getUserId(request);
		String 	pUserId 	= 	pId ; //request.getParameter("pId");
		String 	CurriType 	= 	StringUtil.nvl(request.getParameter("pCurriType"),"R");
		CurriSubDAO 	curriSubDao 	= 	new CurriSubDAO();
		CurriSubInfoDTO	curriSubInfo	= 	new CurriSubInfoDTO();
		curriSubInfo	=	curriSubDao.getCurriSubInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm);
		CurriType		=	StringUtil.nvl(curriSubInfo.getCurriProperty2(), "R");

		if (!CurriType.equals("R"))
			CurriType	=	curriSubInfo.getServiceStart();

		StudentDAO studentDao = new StudentDAO();
		StudentDTO studentDto = new StudentDTO();
		String msg = "";

		//-- 등록된 사용자 인지 체크
		int chkUser = 0;
		UserDAO userDao = new UserDAO();
		chkUser = userDao.userIdCheck(systemCode,pUserId);
		if(chkUser <= 0)
		{
			msg = "등록된 사용자가 아닙니다.";
		}else{

	//		-- 기존 수강생으로 등록 되어 있는지 확인
			retVal = studentDao.isStudent(systemCode,pUserId,pCurriCode,pCurriYear,pCurriTerm);
			if(retVal > 0){
				msg = "이미 수강신청 된  수강자 입니다.";
			}else{
				//---- 수강신청 까지만 합니다.
				studentDto.setSystemCode(systemCode);
				studentDto.setUserId(pUserId);

				studentDto.setCurriCode(pCurriCode);
				studentDto.setCurriYear(pCurriYear);
				studentDto.setCurriTerm(pCurriTerm);
				studentDto.setEnrollStats("E");
				studentDto.setRegId(regId);
				retVal = studentDao.addStudentInfo(studentDto,CurriType);

				if(retVal > 0) {
					//---- 수강추가인 경우 수강신청 인증
					if(regMode.equals("Confirm")) {
						log.debug("수강인증 모둘 시작 합니다. enroll confirm module start");
						studentDto.setEnrollStats("S");
						int retVal1 = 0;
						retVal1 = studentDao.changeEnrollStats(studentDto);
						
						if(retVal1 > 0) {
							log.debug("수강인증을 완료하였습니다.");
							msg = "수강인증을 완료하였습니다.";
						} else {
							log.debug("수강신청까지만 완료되었습니다. 수강인증은 따로 해 주셔야 합니다.");
							msg = "수강신청까지만 완료되었습니다. 수강인증은 따로 해 주셔야 합니다.";
						}
					} else {
						msg = "수강신청을 추가 하였습니다.";
					}

				} else {
					msg = "수강신청 추가에 실패하였습니다.";
				}
			}
		}
		log.debug("------------------ masterEnrollRegist End   -------------------");
		return msg;
	}

	
	/**
	 * 수강생 목록 다운로드....다음에......
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward confirmStudentExcelDown(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);

		// sorting
		String pSTarget = StringUtil.nvl(request.getParameter("pSTarget"));
		String pSWord = StringUtil.nvl(request.getParameter("pSWord"));
		String pCurriYearTerm = StringUtil.nvl(request.getParameter("pCurriYearTerm"));
		String mode = "confirm";
		
		String[] strs = pCurriYearTerm.split("\\|");
		String curriCode = "";
		String sCurriYear = "0";
		String sCurriTerm = "0";

		if(strs.length ==3){
			curriCode = strs[0];
			sCurriYear  = strs[1];
			sCurriTerm = strs[2];
		}
		
		
		int iCurriYear = StringUtil.nvl(sCurriYear,0);
		int iCurriTerm = StringUtil.nvl(sCurriTerm,0);
		
		// 데이타를 담는다.
		StudentDAO studentDao = new StudentDAO();
		ArrayList dataList = studentDao.getStudentListAll(systemCode, curriCode, iCurriYear, iCurriTerm, pSTarget, pSWord, mode);
		
		model.put("dataList", dataList);
		return excelDown(request,httpServletResponse, model, "/curri_student/comfirmStudentExcelDown.jsp","junnodaeStudentList.xls");
		
	}
	
}
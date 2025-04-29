package com.edutrack.curristudent.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.curristudent.dao.CurriStudentDAO;
import com.edutrack.curristudent.dao.StudentDAO;
import com.edutrack.curristudent.dto.BestStudentDTO;
import com.edutrack.curristudent.dto.StudentDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.AjaxUtil;
/*
 * @author pearlm
 *
 * 수강, 수강생 관리
 */

public class CurriStudentAction extends StrutsDispatchAction{


	public CurriStudentAction() {
		super();
		// TODO Auto-generated constructor stub
	}
	//	 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	/**
	 * 수강생조회(수강생, 수료생)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward confirmPassStudentList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

	    UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    String userType	=	userMemDto.userType;
		model.put("userType",userType);

		new SiteNavigation(LECTURE).add(request,"수강생조회").link(model);
		return forward(request, model, "/curri_student/confirmPassStudentList.jsp");

	}


	/**
	 * 수강생조회 리스트를 자동으로 바꾼다 (ajax)
	 * 2007.03.16 sangsang
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	public AjaxListDTO confirmPassStudentListAjax(String sTarget, String sWord, int curPage, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    String systemCode = UserBroker.getSystemCode(request);
	    UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    String userType	=	userMemDto.userType;
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = Integer.parseInt(curriMemDto.curriYear);
		int pCurriTerm = Integer.parseInt(curriMemDto.curriTerm);


		// sorting
		sTarget = StringUtil.nvl(sTarget,"");
		sWord = AjaxUtil.ajaxDecoding(StringUtil.nvl(sWord));

		String mode = "confirmPass";

		// 페이징
		curPage = (curPage == 0) ? 1 : curPage;

		// 데이타를 담는다.
		ListDTO listObj = null;
		CurriStudentDAO curristudentDao = new CurriStudentDAO();
		listObj = curristudentDao.getConfirmPassStudentList(curPage, systemCode, pCurriCode, pCurriYear, pCurriTerm, sTarget, sWord, mode);

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

				if( rs.getLong("last_con")< CommonUtil.CONCHECKTIME ){
					studentDto.setLastCon("접속중");
				}else{
					studentDto.setLastCon("미접속");
				}

				studentDto.setEmail(StringUtil.nvl(rs.getString("email")));

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
	 * 우수학생 정보받기 (ajax)
	 * 2007.03.20 sangsang
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	public void bestStudentInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
	    String systemCode = UserBroker.getSystemCode(request);
	    ListDTO listObj = new ListDTO();
	    UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    String userId	=	userMemDto.userId;
	    String userType	=	userMemDto.userType;
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = Integer.parseInt(curriMemDto.curriYear);
		int pCurriTerm = Integer.parseInt(curriMemDto.curriTerm);

		String studentId = StringUtil.nvl(request.getParameter("pStudentId"));
		String contents = "";

		CurriStudentDAO curristudentDao = new CurriStudentDAO();
		contents = StringUtil.nvl(curristudentDao.getBestStudent(systemCode, pCurriCode, pCurriYear, pCurriTerm, studentId));

		PrintWriter out = response.getWriter();
	    response.setContentType("application/xml");
		StringBuffer strXML = new StringBuffer();
		strXML.append("<?xml version=\"1.0\" encoding=\"euc-kr\" ?>");
		strXML.append("<bestStudentInfo>");
		strXML.append("<selectValue>");
		strXML.append("<studentId>"+studentId+"</studentId>");
		strXML.append("<contents><![CDATA["+contents+"]]></contents>");
		strXML.append("</selectValue>");
		strXML.append("</bestStudentInfo>");
		out.println(strXML.toString());
	}

	/**
	 * 우수학생 등록/삭제/수정 (ajax)
	 * 2007.03.16 sangsang
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	public void bestStudentRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
	    String systemCode = UserBroker.getSystemCode(request);
	    ListDTO listObj = new ListDTO();
	    UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    String userId	=	userMemDto.userId;
	    String userType	=	userMemDto.userType;
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = Integer.parseInt(curriMemDto.curriYear);
		int pCurriTerm = Integer.parseInt(curriMemDto.curriTerm);

		String mode = StringUtil.nvl(request.getParameter("pMode"));
		String stuIds = StringUtil.nvl(request.getParameter("pStuIds"));
		String studentId = StringUtil.nvl(request.getParameter("pStudentId"));
		String contents = "";

	    CurriStudentDAO curriStudentDao= new CurriStudentDAO();
		BestStudentDTO bestStudentDto = null;

  	    PrintWriter out = response.getWriter();
		StringBuffer strXML = new StringBuffer();

		if(mode.equals("Add")){
			contents = StringUtil.nvl(request.getParameter("pContents"));
			contents = AjaxUtil.ajaxDecoding(contents);	// Ajax 파라미터 디코딩..

			ArrayList bestStudentList = new ArrayList();

			String[] stuIdArray = StringUtil.split(stuIds, "^");
		    for(int i=0;i < stuIdArray.length-1;i++){
		        bestStudentDto = new BestStudentDTO();
		        bestStudentDto.setSystemCode(systemCode);
		        bestStudentDto.setCurriCode(pCurriCode);
		        bestStudentDto.setCurriYear(pCurriYear);
		        bestStudentDto.setCurriTerm(pCurriTerm);
		        bestStudentDto.setStudentId(stuIdArray[i]);
		        bestStudentDto.setContents(contents);
		        bestStudentDto.setRegId(userId);

		        if(stuIdArray[i] !=null && !stuIdArray[i].equals("")){
		        	bestStudentList.add(bestStudentDto);
		        }
		    }

			boolean result = false;
		    result = curriStudentDao.addBestStudent(bestStudentList);

		    response.setContentType("application/xml");
			strXML.append("<?xml version=\"1.0\" encoding=\"euc-kr\" ?>");
			strXML.append("<studentList>");
			strXML.append("<selectValue>");
			strXML.append("<result>"+result+"</result>");
			strXML.append("</selectValue>");
			strXML.append("</studentList>");
			out.println(strXML.toString());
		}else if(mode.equals("Edit")){
			contents = StringUtil.nvl(request.getParameter("pContents2"));
			contents = AjaxUtil.ajaxDecoding(contents);	// Ajax 파라미터 디코딩..

			bestStudentDto = new BestStudentDTO();
	        bestStudentDto.setSystemCode(systemCode);
	        bestStudentDto.setCurriCode(pCurriCode);
	        bestStudentDto.setCurriYear(pCurriYear);
	        bestStudentDto.setCurriTerm(pCurriTerm);
	        bestStudentDto.setStudentId(studentId);
	        bestStudentDto.setContents(contents);
	        bestStudentDto.setModId(userId);

	        int retVal = 0;
	        retVal = curriStudentDao.editBestStudent(bestStudentDto);

		    response.setContentType("application/xml");
			strXML.append("<?xml version=\"1.0\" encoding=\"euc-kr\" ?>");
			strXML.append("<studentList>");
			strXML.append("<selectValue>");
			strXML.append("<result>"+retVal+"</result>");
			strXML.append("</selectValue>");
			strXML.append("</studentList>");
			out.println(strXML.toString());

		}else if(mode.equals("Delete")){
	        int retVal = 0;
	        retVal = curriStudentDao.deleteBestStudent(systemCode, pCurriCode, pCurriYear, pCurriTerm, studentId);

		    response.setContentType("application/xml");
			strXML.append("<?xml version=\"1.0\" encoding=\"euc-kr\" ?>");
			strXML.append("<studentList>");
			strXML.append("<selectValue>");
			strXML.append("<result>"+retVal+"</result>");
			strXML.append("</selectValue>");
			strXML.append("</studentList>");
			out.println(strXML.toString());
		}
	}


}
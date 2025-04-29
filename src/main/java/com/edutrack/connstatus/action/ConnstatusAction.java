/*
 * Created on 2004. 10. 27.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.connstatus.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.code.dao.CodeSoDAO;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.connstatus.dao.CourseConnDAO;
import com.edutrack.connstatus.dao.CurriConnDAO;
import com.edutrack.connstatus.dao.StudentStatusDAO;
import com.edutrack.connstatus.dao.SystemConnDAO;
import com.edutrack.connstatus.dto.CourseConnectDTO;
import com.edutrack.connstatus.dto.CurriConnectDTO;
import com.edutrack.connstatus.dto.SystemConnectDTO;
import com.edutrack.curritop.dao.CourseDAO;
import com.edutrack.curritop.dao.CurriCategoryDAO;
import com.edutrack.curritop.dao.CurriTopDAO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.connstatus.action.*;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConnstatusAction extends StrutsDispatchAction{

	/**
	 *
	 */
	public ConnstatusAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * system접속 정보를 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward systemConnAction(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
	    SystemConnectDTO systemConnectDto = new SystemConnectDTO();

		String systemCode = UserBroker.getSystemCode(request);

		String curDate = CommonUtil.getCurrentDate();
		systemConnectDto.setSelectGb(StringUtil.nvl(request.getParameter("selectGb"),"1"));
		//systemConnectDto.setDateGb(StringUtil.nvl(request.getParameter("dateGb"),"11"));
		systemConnectDto.setStartYear(StringUtil.nvl(request.getParameter("startYear"),curDate.substring(0,4)));
		systemConnectDto.setStartMon(StringUtil.nvl(request.getParameter("startMon"),curDate.substring(4,6)));
		systemConnectDto.setStartDay(StringUtil.nvl(request.getParameter("startDay"),curDate.substring(6,8)));
		systemConnectDto.setEndYear(StringUtil.nvl(request.getParameter("endYear"),curDate.substring(0,4)));
		systemConnectDto.setEndMon(StringUtil.nvl(request.getParameter("endMon"),curDate.substring(4,6)));
		systemConnectDto.setEndDay(StringUtil.nvl(request.getParameter("endDay"),curDate.substring(6,8)));

		String navigationMsg = "";
		String navigationWhere = MYPAGE;

		navigationMsg = "시스템 접속 통계";
		SystemConnDAO connstatusDao = new SystemConnDAO();

		model.put("systemConnectDto", systemConnectDto);
		int totalCnt = connstatusDao.getTotalCnt(systemCode, systemConnectDto);
		model.put("totalCnt",Integer.toString(totalCnt));
		model.put("systemConCntList", connstatusDao.getSystemConCnt(systemCode, systemConnectDto));

		navigationWhere = MYPAGE;
		new SiteNavigation(navigationWhere).add(request,navigationMsg).link(model);

		return forward(request, model,"/connstatus/connstatusMain.jsp");
	}

	/**
	 * 과정분류 리스트를 셀렉트 박스로 리턴한다..
	 * @param cateCode
	 * @return String
	 * @throws Exception
	 */
	public String cateSelectList(String SystemCode, String pCateCode) throws Exception {
		String retVal = "";
		String selected = "";
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='pCateCode' onChange=\"fnSelectGb()\">");
		sb.append("<option value='' >전체분류</option>");
		CurriCategoryDAO curriCategoryDao = new CurriCategoryDAO();
		RowSet rs = curriCategoryDao.getCategoryListAll(SystemCode);
		while(rs.next()){
			selected = "";
			if(StringUtil.nvl(rs.getString("cate_code")).equals(pCateCode)) selected = "selected";
			sb.append("<option value='"+StringUtil.nvl(rs.getString("cate_code"))+"' "+selected+">"+StringUtil.nvl(rs.getString("cate_name"))+"</option>");
		}
		sb.append("</select>");
		retVal = sb.toString();
		rs.close();
		return retVal;
	}

	/**
	 * 과정속성 리스트를 셀렉트 박스로 리턴한다..
	 * @param cateCode
	 * @return String
	 * @throws Exception
	 */
	public String codeSelectList(String SystemCode, String pProperty1, String pProperty2) throws Exception {
		String retVal = "";
		String selected = "";
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='pProperty2' onChange=\"fnSelectGb()\">");
		sb.append("<option value='' >전체과정</option>");
		CodeSoDAO codeSoDao = new CodeSoDAO();
		RowSet rs = codeSoDao.getCodeSoListAll(SystemCode, pProperty1);
		while(rs.next()){
			selected = "";
			if(StringUtil.nvl(rs.getString("code_so")).equals(pProperty2)) selected = "selected";
			sb.append("<option value='"+StringUtil.nvl(rs.getString("code_so"))+"' "+selected+">"+StringUtil.nvl(rs.getString("so_name"))+"</option>");
		}
		sb.append("</select>");
		retVal = sb.toString();
		rs.close();
		return retVal;
	}

	/**
	 * 과정 리스트를 셀렉트 박스로 리턴한다..
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String curriSelectList(String SystemCode,String CateCode, String pProperty1, String pProperty2, String pCurriCode) throws Exception{
		String retVal = "";
		String selected = "";
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='pCurriCode'  onChange=\"fnSelectGb()\">");
		sb.append("<option value='' >전체과정</option>");
		CurriTopDAO curriTopDao = new CurriTopDAO();
		RowSet rs = curriTopDao.getCurriTopListAll(SystemCode, CateCode, pProperty1, pProperty2);
		while(rs.next()){
			selected = "";
			if(StringUtil.nvl(rs.getString("curri_code")).equals(pCurriCode)) selected = "selected";
			sb.append("<option value='"+StringUtil.nvl(rs.getString("curri_code"))+"' "+selected+">"+StringUtil.nvl(rs.getString("curri_name"))+"</option>");
		}
		sb.append("</select>");
		retVal = sb.toString();
		rs.close();
		return retVal;

	}

	public String courseSelectList(String SystemCode,String pCourseId) throws Exception{
		String retVal = "";
		String selected = "";
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='pCourseId'  onChange=\"fnSelectGb()\">");
		sb.append("<option value='' >전체과목</option>");
		CourseDAO courseDao = new CourseDAO();
		RowSet rs = courseDao.getCourseListAll(SystemCode);
		while(rs.next()){
			selected = "";
			if(StringUtil.nvl(rs.getString("course_id")).equals(pCourseId)) selected = "selected";
			sb.append("<option value='"+StringUtil.nvl(rs.getString("course_id"))+"' "+selected+">"+StringUtil.nvl(rs.getString("course_name"))+"</option>");
		}
		sb.append("</select>");
		retVal = sb.toString();
		rs.close();
		return retVal;

	}

	/**
	 * 과정접속 정보를 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriConnAction(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
	    CurriConnectDTO curriConnectDto = new CurriConnectDTO();

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String curDate = CommonUtil.getCurrentDate();

		String pCateCode = StringUtil.nvl(request.getParameter("pCateCode"),"");
		String pProperty1 = StringUtil.nvl(request.getParameter("pProperty1"),"Cyber");
		String pProperty2 = StringUtil.nvl(request.getParameter("pProperty2"),"");
		String pCurriCode = StringUtil.nvl(request.getParameter("pCurriCode"),"");

		curriConnectDto.setSelectGb(StringUtil.nvl(request.getParameter("selectGb"),"1"));
		curriConnectDto.setSelectCate(pCateCode);
		curriConnectDto.setSelectType(pProperty2);
		curriConnectDto.setCurriCode(pCurriCode);
		curriConnectDto.setCurriYear(StringUtil.nvl(request.getParameter("curriYear")));
		curriConnectDto.setCurriTerm(StringUtil.nvl(request.getParameter("curriTerm")));
		curriConnectDto.setStartYear(StringUtil.nvl(request.getParameter("startYear"),curDate.substring(0,4)));
		curriConnectDto.setStartMon(StringUtil.nvl(request.getParameter("startMon"),curDate.substring(4,6)));
		curriConnectDto.setStartDay(StringUtil.nvl(request.getParameter("startDay"),curDate.substring(6,8)));
		curriConnectDto.setEndYear(StringUtil.nvl(request.getParameter("endYear"),curDate.substring(0,4)));
		curriConnectDto.setEndMon(StringUtil.nvl(request.getParameter("endMon"),curDate.substring(4,6)));
		curriConnectDto.setEndDay(StringUtil.nvl(request.getParameter("endDay"),curDate.substring(6,8)));

		model.put("cateSelect", cateSelectList(systemCode,StringUtil.nvl(pCateCode)));
		model.put("codeSelect", codeSelectList(systemCode,pProperty1,pProperty2));
		model.put("curriSelect", curriSelectList(systemCode,pCateCode,pProperty1,pProperty2,pCurriCode));

		String navigationMsg = "";
		String navigationWhere = MYPAGE;

		navigationMsg = "과정 접속 통계";
		CurriConnDAO connstatusDao = new CurriConnDAO();

		model.put("curriConnectDto", curriConnectDto);
		int totalCnt = connstatusDao.getTotalCnt(systemCode, curriConnectDto);
		model.put("totalCnt",Integer.toString(totalCnt));
		model.put("curriConCntList", connstatusDao.getCurriConCnt(systemCode, curriConnectDto));

		navigationWhere = MYPAGE;
		new SiteNavigation(navigationWhere).add(request,navigationMsg).link(model);

		return forward(request, model,"/connstatus/connstatusCurri.jsp");
	}

	/**
	 * 과목 접속 정보를 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseConnAction(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
	    CourseConnectDTO courseConnectDto = new CourseConnectDTO();

	    String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String curDate = CommonUtil.getCurrentDate();

		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"),"");
		courseConnectDto.setCourseId(pCourseId);
		courseConnectDto.setSelectGb(StringUtil.nvl(request.getParameter("selectGb"),"1"));
		courseConnectDto.setStartYear(StringUtil.nvl(request.getParameter("startYear"),curDate.substring(0,4)));
		courseConnectDto.setStartMon(StringUtil.nvl(request.getParameter("startMon"),curDate.substring(4,6)));
		courseConnectDto.setStartDay(StringUtil.nvl(request.getParameter("startDay"),curDate.substring(6,8)));
		courseConnectDto.setEndYear(StringUtil.nvl(request.getParameter("endYear"),curDate.substring(0,4)));
		courseConnectDto.setEndMon(StringUtil.nvl(request.getParameter("endMon"),curDate.substring(4,6)));
		courseConnectDto.setEndDay(StringUtil.nvl(request.getParameter("endDay"),curDate.substring(6,8)));

		model.put("courseSelect", courseSelectList(systemCode,pCourseId));

		String navigationMsg = "";
		String navigationWhere = MYPAGE;

		navigationMsg = "과목 접속 통계";
		CourseConnDAO connstatusDao = new CourseConnDAO();

		model.put("courseConnectDto", courseConnectDto);
		int totalCnt = connstatusDao.getTotalCnt(systemCode, courseConnectDto);
		model.put("totalCnt",Integer.toString(totalCnt));
		model.put("courseConCntList", connstatusDao.getCourseConCnt(systemCode, courseConnectDto));

		navigationWhere = MYPAGE;
		new SiteNavigation(navigationWhere).add(request,navigationMsg).link(model);

		return forward(request, model,"/connstatus/connstatusCourse.jsp");
	}



	public ActionForward studentStatusAction(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
	    SystemConnectDTO systemConnectDto = new SystemConnectDTO();
	    String curDate = CommonUtil.getCurrentDate();
		String systemCode = UserBroker.getSystemCode(request);

		systemConnectDto.setStudentGb(StringUtil.nvl(request.getParameter("studentGb"),"S"));
		systemConnectDto.setSelectGb(StringUtil.nvl(request.getParameter("selectGb"),"6"));
		systemConnectDto.setDateGb(StringUtil.nvl(request.getParameter("dateGb"),"11"));
		systemConnectDto.setStartYear(StringUtil.nvl(request.getParameter("startYear"),curDate.substring(0,4)));
		systemConnectDto.setStartMon(StringUtil.nvl(request.getParameter("startMon"),curDate.substring(4,6)));
		systemConnectDto.setStartDay(StringUtil.nvl(request.getParameter("startDay"),curDate.substring(6,8)));
		systemConnectDto.setEndYear(StringUtil.nvl(request.getParameter("endYear"),curDate.substring(0,4)));
		systemConnectDto.setEndMon(StringUtil.nvl(request.getParameter("endMon"),curDate.substring(4,6)));
		systemConnectDto.setEndDay(StringUtil.nvl(request.getParameter("endDay"),curDate.substring(6,8)));


		String navigationMsg = "";
		String navigationWhere = MYPAGE;

		navigationMsg = "수강 접속 통계";
		StudentStatusDAO studentstatusDao = new StudentStatusDAO();
        ///////////////////
		String pCateCode = StringUtil.nvl(request.getParameter("pCateCode"),"");
		String pProperty1 = StringUtil.nvl(request.getParameter("pProperty1"),"Cyber");
		String pProperty2 = StringUtil.nvl(request.getParameter("pProperty2"),"");
		String pCurriCode = StringUtil.nvl(request.getParameter("pCurriCode"),"");

		model.put("cateSelect", cateSelectList(systemCode,StringUtil.nvl(pCateCode)));
		model.put("codeSelect", codeSelectList(systemCode,pProperty1,pProperty2));
		model.put("curriSelect", curriSelectList(systemCode,pCateCode,pProperty1,pProperty2,pCurriCode));
		/////////////////////

		systemConnectDto.setCateCode (pCateCode  );
		systemConnectDto.setProperty1(pProperty1 );
		systemConnectDto.setProperty2(pProperty2 );
		systemConnectDto.setCurriCode(pCurriCode );

		model.put("systemConnectDto" , systemConnectDto);
		model.put("studentConCntList", studentstatusDao.getCurriSelect(systemCode, systemConnectDto));

		navigationWhere = MYPAGE;
		new SiteNavigation(navigationWhere).add(request,navigationMsg).link(model);

		return forward(request, model,"/connstatus/connstatusStudent.jsp");
	}
}

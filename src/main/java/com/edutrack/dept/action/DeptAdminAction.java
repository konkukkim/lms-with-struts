/*
 * Created on 2005. 8. 25.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.dept.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dao.CommonDAO;
import com.edutrack.common.dto.CodeDTO;
import com.edutrack.curristudent.dao.StudentDAO;
import com.edutrack.currisub.dao.CurriContentsDAO;
import com.edutrack.currisub.dao.CurriSubCourseDAO;
import com.edutrack.dept.dao.DeptDaeDAO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.resultcourse.dao.ResultCourseDAO;
import com.edutrack.user.dao.UserAdminDAO;
/**
 * @author soon
 *
 * 단체 관리자 소속회원관리
 */
public class DeptAdminAction extends StrutsDispatchAction{

	public DeptAdminAction()  {
		super();
		// TODO Auto-generated constructor stub
	}

	 // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	public ActionForward deptUserStudyShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("=================================deptUserStudyShow start");
		//UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    //CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String 	systemCode 	= 	UserBroker.getSystemCode(request);
	    String 	userId 	  	= 	StringUtil.nvl(request.getParameter("pUserId"));
		String 	pCurriCode 	= 	StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 	= 	StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int 	pCurriTerm 	= 	StringUtil.nvl(request.getParameter("pCurriTerm"),0);
		String 	pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		String  pCourseName	=	StringUtil.nvl(request.getParameter("pCourseName"));


		model.put("pCurriCode", pCurriCode);
		model.put("pCurriYear", String.valueOf(pCurriYear) );
		model.put("pCurriTerm", String.valueOf(pCurriTerm) );

		if(pCourseId.equals("")){//-- 과목코드를 가져오지 않으면 첫번째 과목 코드를 사용한다.
			log.debug("_________________ non courseId");
			CommonDAO common = new CommonDAO();
		   	ArrayList codeList = null;
		   	CodeDTO code = null;
		   	codeList = common.getCurriCourse(systemCode,pCurriCode,pCurriYear,pCurriTerm,"");
	    	code = (CodeDTO)codeList.get(0);
	    	pCourseId = code.getCode();
	    	pCourseName = code.getName();
		}

		ResultCourseDAO resultCourseDao	=	new ResultCourseDAO();
		CurriSubCourseDAO curriCourseDao = new CurriSubCourseDAO();
		//-- 성적 배점 비율 정보 가져오기
		RowSet curriCourseInfo = curriCourseDao.getCurriSubCourseInfo(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId);
		model.put("curriCourseInfo", curriCourseInfo);
		//-- result_course 에 등록되어 있는 현재 점수 가져오기
		RowSet resultCourseInfo = resultCourseDao.getResultCourseList(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,userId, null);
		resultCourseInfo.next();
		model.put("pStudentName", StringUtil.nvl(resultCourseInfo.getString("user_name")));
		model.put("pScoreExam", String.valueOf(resultCourseInfo.getDouble("score_exam")));
		model.put("pScoreReport", String.valueOf(resultCourseInfo.getDouble("score_report")));
		model.put("pScoreAttend", String.valueOf(resultCourseInfo.getDouble("score_attend")));
		model.put("pScoreForum", String.valueOf(resultCourseInfo.getDouble("score_forum")));
		model.put("pScoreEtc1", String.valueOf(resultCourseInfo.getDouble("score_etc1")));
		model.put("pScoreEtc2", String.valueOf(resultCourseInfo.getDouble("score_etc2")));
		model.put("pServiceStart", String.valueOf(resultCourseInfo.getString("SERVICESTART_DATE")));
		model.put("pServiceEnd"  , String.valueOf(resultCourseInfo.getString("SERVICEEND_DATE")));

		//-- 시험 정보/점수 리스트 가져오기
		model.put("examScoreList", resultCourseDao.getStudentExamScoreList(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,userId));
		//-- 과제 정보/점수 리스트 가져오기
		model.put("reportScoreList", resultCourseDao.getStudentReportScoreList(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,userId));
		//-- 토론 정보/점수 리스트 가져오기
		model.put("forumScoreList", resultCourseDao.getStudentForumScoreList(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,userId));
		CurriContentsDAO contentsDao = new CurriContentsDAO();
		//		출석
		model.put("attendView", contentsDao.getMainAttendShow(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,userId));

		model.put("pCourseId", pCourseId);
		model.put("pCourseName", pCourseName);

		log.debug("=================================deptUserStudyShow end");

		new SiteNavigation(MYPAGE).add(request,"소속회원현황").link(model);
		return forward(request, model, "/dept_admin/deptUserStudyShow.jsp");
	}


}

/**
 *
 */
package com.edutrack.connstatus.action;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.edutrack.connstatus.dao.CurriSubStaticDAO;
import com.edutrack.connstatus.dto.CurriSubStaticDTO;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

public class CurriSubStaticAction extends StrutsDispatchAction{

	public CurriSubStaticAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 교육과정별 통계
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward allCurriSubStatic(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	pSystemCode		=	UserBroker.getSystemCode(request);
		String	pCurDate		=	CommonUtil.getCurrentDate();
		int		curYear			=	Integer.parseInt(pCurDate.substring(0, 4));
		int		pCurriYear		=	StringUtil.nvl(request.getParameter("pCurriYear"), curYear);
		int		curPage			=	StringUtil.nvl(request.getParameter("curPage"), 1);

		model.put("curPage", String.valueOf(curPage));
		model.put("pCurriYear", String.valueOf(pCurriYear));
		model.put("pCurriTerm", StringUtil.nvl(request.getParameter("pCurriTerm")));
		model.put("pPareCode1", StringUtil.nvl(request.getParameter("pPareCode1")));

		new SiteNavigation(MYPAGE).add(request,"과정별통계현황").link(model);
		return forward(request, model,"/connstatus/allCurriSubStatic.jsp");
	}

	/**
	 * 과정별통계현황 엑셀파일 출력하기
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward excelAllCurriSubStatic(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	pSystemCode		=	UserBroker.getSystemCode(request);
		String	pCurDate		=	CommonUtil.getCurrentDate();
		String	pGubun			=	StringUtil.nvl(request.getParameter("pGubun"));
		int		curYear			=	Integer.parseInt(pCurDate.substring(0, 4));
		int		pCurriYear		=	StringUtil.nvl(request.getParameter("pCurriYear"), curYear);
		String	pCurriCode		=	StringUtil.nvl(request.getParameter("pCurriCode"));
		int		pCurriTerm		=	StringUtil.nvl(request.getParameter("pCurriTerm"), 0);
		String	goUrl			=	"";

		Map		paramMap		=	new HashMap();
		paramMap.put("DispLine", "10000");
		paramMap.put("pSystemCode", pSystemCode);
		paramMap.put("pCurriCode", pCurriCode);
		paramMap.put("pCurriYear", String.valueOf(pCurriYear));
		paramMap.put("pCurriTerm", StringUtil.nvl(request.getParameter("pCurriTerm")));
		paramMap.put("pPareCode1", StringUtil.nvl(request.getParameter("pPareCode1")));	//대교육과정분류
		paramMap.put("pAgeYn", StringUtil.nvl(request.getParameter("pAgeYn")));

		CurriSubStaticDAO	staticDao	=	new CurriSubStaticDAO();

		if(pGubun.equals("info")) {
			goUrl	=	"/connstatus/excellAllCurriSubInfo.jsp";
			model.put("StaticList", staticDao.getCateCurriSubInfo(paramMap));
		} else if(pGubun.equals("detail")) {
			goUrl	=	"/connstatus/excellAllCurriSubDetail.jsp";

			model.put("StaticList", staticDao.getCateCurriSubStatic(paramMap));
			model.put("pCurriName", CommonUtil.getCurriSubName(pSystemCode, pCurriCode, pCurriYear, pCurriTerm));
		}

		model.put("pCurriYear", String.valueOf(pCurriYear));
		model.put("pCurriTerm", StringUtil.nvl(request.getParameter("pCurriTerm")));
		model.put("pAgeYn", StringUtil.nvl(request.getParameter("pAgeYn")));

		return forward(request, model, goUrl);
	}


	/**
	 * 학기, 기수 등의 정보를 가져온다.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList termSelectList(int curriYear, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String	systemCode	=	UserBroker.getSystemCode(request);

		CurriSubStaticDAO	staticDao	=	new CurriSubStaticDAO();
		CurriSubStaticDTO	staticDto	=	null;
		ArrayList	list	=	new ArrayList();
		RowSet		rs		=	staticDao.getCurriTermGroup(systemCode, curriYear);
		while(rs.next()) {
			staticDto	=	new CurriSubStaticDTO();
			staticDto.setCurriTerm(rs.getInt("curri_term"));
			list.add(staticDto);
		}
		rs.close();
		return list;
	}

	/**
	 * 대분류과정코드 셀렉트목록
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map cateSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);

		Map map = new HashMap();
		CurriSubStaticDAO	staticDao	=	new CurriSubStaticDAO();
		RowSet	rs	=	staticDao.getCurriPcategoryList(systemCode);
		while(rs.next()) {
			map.put(StringUtil.nvl(rs.getString("pare_code1")), StringUtil.nvl(rs.getString("cate_name")));
		}
		rs.close();
		return map;
	}

	/**
	 * 교육과정별통계 -  화면상단 과정정보 가져오기
	 * @param curriYear
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO curriSubStaticInfo(int curPage, int curriYear, int curriTerm, String pareCode1, int dispLine, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String	pSystemCode		=	UserBroker.getSystemCode(request);
		int		curyear			=	Integer.parseInt(CommonUtil.getCurrentDate().substring(0, 4));

		curPage = (curPage == 0) ? 1 : curPage;
		int		DispLine		=	(dispLine == 0) ? 10 : dispLine;
		int		DispPage		=	StringUtil.nvl(request.getParameter("DispPage"), 10);
		int		pCurriYear		=	(curriYear == 0) ? curyear : curriYear;

		Map		paramMap		=	new HashMap();
		paramMap.put("pSystemCode", pSystemCode);
		paramMap.put("curPage", String.valueOf(curPage));
		paramMap.put("DispLine", String.valueOf(DispLine));
		paramMap.put("DispPage", String.valueOf(DispPage));
		paramMap.put("pCurriYear", String.valueOf(pCurriYear));
		paramMap.put("pCurriTerm", String.valueOf(curriTerm));
		paramMap.put("pPareCode1", pareCode1);	//대교육과정분류

		CurriSubStaticDAO	staticDao	=	new CurriSubStaticDAO();
		AjaxListDTO			ajaxListDto	=	new AjaxListDTO();
		CurriSubStaticDTO	staticDto	=	null;
		ArrayList	list		=	new ArrayList();
		ListDTO 	listObj 	=	null;

		listObj	=	staticDao.getCateCurriSubInfo(paramMap);
		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				staticDto	=	new CurriSubStaticDTO();
				staticDto.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				staticDto.setCurriYear(rs.getInt("curri_year"));
				staticDto.setCurriTerm(rs.getInt("curri_term"));
				staticDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				staticDto.setCurriName(StringUtil.nvl(rs.getString("curri_name")));
				staticDto.setPareCode1(StringUtil.nvl(rs.getString("pare_code1")));
				staticDto.setPareCode2(StringUtil.nvl(rs.getString("pare_code2")));
				staticDto.setCateCode(StringUtil.nvl(rs.getString("cate_code")));
				staticDto.setUserId(StringUtil.nvl(rs.getString("curri_code")));
				staticDto.setContentsCnt(rs.getInt("contents_cnt"));

				staticDto.setCompleteManCnt(rs.getInt("complete_man_cnt"));
				staticDto.setCompleteWomenCnt(rs.getInt("complete_women_cnt"));
				staticDto.setCompleteTotalCnt(rs.getInt("complete_total_cnt"));
				staticDto.setNoManCnt(rs.getInt("no_man_cnt"));
				staticDto.setNoWomenCnt(rs.getInt("no_women_cnt"));
				staticDto.setNoTotalCnt(rs.getInt("no_total_cnt"));
				staticDto.setManTotalCnt(rs.getInt("man_total_cnt"));
				staticDto.setWomenTotalCnt(rs.getInt("women_total_cnt"));
				staticDto.setTotalCnt(rs.getInt("total_cnt"));

				list.add(staticDto);
			}	//end while
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(list);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(pSystemCode,"goPage"));	// 페이징 스트링
		}	// end if
		return ajaxListDto;
	}

	/**
	 * 교육과정별통계 - 화면하단통계화면
	 *
	 * @return
	 * @throws Exception
	 */
	public ArrayList curriSubStatic (String curriCode, int curriYear, int curriTerm, String pareCode1, String ageYn, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String	pSystemCode		=	UserBroker.getSystemCode(request);
		int		curyear			=	Integer.parseInt(CommonUtil.getCurrentDate().substring(0, 4));
		int		pCurriYear		=	(curriYear == 0) ? curyear : curriYear;

		Map		paramMap		=	new HashMap();
		paramMap.put("pSystemCode", pSystemCode);
		paramMap.put("pCurriCode", curriCode);
		paramMap.put("pAgeYn", ageYn);
		paramMap.put("pCurriYear", String.valueOf(pCurriYear));
		paramMap.put("pCurriTerm", String.valueOf(curriTerm));
		paramMap.put("pPareCode1", pareCode1);	//대교육과정분류

		CurriSubStaticDAO	staticDao	=	new CurriSubStaticDAO();
		CurriSubStaticDTO	staticDto	=	null;
		ArrayList	list		=	new ArrayList();

		RowSet	rs	=	staticDao.getCateCurriSubStatic(paramMap);
		while(rs.next()) {
			staticDto	=	new CurriSubStaticDTO();
			staticDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
			staticDto.setCurriYear(StringUtil.nvl(rs.getString("curri_year"), 0));
			staticDto.setCurriTerm(StringUtil.nvl(rs.getString("curri_term"), 0));
			staticDto.setPareCode1(StringUtil.nvl(rs.getString("pare_code1")));

			if(ageYn.equals("Y")) {
				staticDto.setAge(StringUtil.nvl(rs.getString("age"), 0));
			} else {
				staticDto.setArea(StringUtil.nvl(rs.getString("area")));
			}

			staticDto.setCodeName(StringUtil.nvl(rs.getString("so_name")));
			staticDto.setCompleteManCnt(StringUtil.nvl(rs.getString("pass_man"), 0));
			staticDto.setCompleteWomenCnt(StringUtil.nvl(rs.getString("pass_women"), 0));
			staticDto.setCompleteTotalCnt(StringUtil.nvl(rs.getString("pass_total"), 0));
			staticDto.setNoManCnt(StringUtil.nvl(rs.getString("np_man"), 0));
			staticDto.setNoWomenCnt(StringUtil.nvl(rs.getString("np_women"), 0));
			staticDto.setNoTotalCnt(StringUtil.nvl(rs.getString("np_total"), 0));
			staticDto.setManTotalCnt(StringUtil.nvl(rs.getString("total_man"), 0));
			staticDto.setWomenTotalCnt(StringUtil.nvl(rs.getString("total_women"), 0));
			staticDto.setTotalCnt(StringUtil.nvl(rs.getString("total_cnt"), 0));

			list.add(staticDto);
		}
		rs.close();
		return list;
	}

	/**
	 * 과정별 수강현황
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward attendCurriSubStatic(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	pSystemCode		=	UserBroker.getSystemCode(request);
		String	pCurDate		=	CommonUtil.getCurrentDate();
		int		curYear			=	Integer.parseInt(pCurDate.substring(0, 4));
		int		pCurriYear		=	StringUtil.nvl(request.getParameter("pCurriYear"), curYear);

		model.put("pCurriYear", String.valueOf(pCurriYear));
		model.put("pCurriTerm", StringUtil.nvl(request.getParameter("pCurriTerm")));

		new SiteNavigation(MYPAGE).add(request,"과정별 수강현황").link(model);
		return forward(request, model,"/connstatus/attendCurriSubStatic.jsp");
	}

	/**
	 * 수강현황 엑셀다운로드
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward attendCurriSubExcelDown(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	pSystemCode		=	UserBroker.getSystemCode(request);
		String	pCurDate		=	CommonUtil.getCurrentDate();
		int		curYear			=	Integer.parseInt(pCurDate.substring(0, 4));
		int		pCurriYear		=	StringUtil.nvl(request.getParameter("pCurriYear"), curYear);
		int		pCurriTerm		=	StringUtil.nvl(request.getParameter("pCurriTerm"), 0);
		String	pCurriCode		=	StringUtil.nvl(request.getParameter("pCurriCode"));
		String	pGubun			=	StringUtil.nvl(request.getParameter("pGubun"));
		String	goPage			=	"";

		Map		paramMap		=	new HashMap();
		paramMap.put("pSystemCode", pSystemCode);
		paramMap.put("pCurriCode", pCurriCode);
		paramMap.put("pCurriYear", String.valueOf(pCurriYear));
		paramMap.put("pCurriTerm", String.valueOf(pCurriTerm));

		RowSet	staticList	=	null;
		CurriSubStaticDAO	staticDao	=	new CurriSubStaticDAO();

		if(pGubun.equals("info")) {
			goPage	=	"/connstatus/ExcelCurriSubCourse.jsp";
			staticList		=	staticDao.getCurriSubCourseStatic(paramMap);
		} else if(pGubun.equals("detail")) {
			goPage	=	"/connstatus/ExcelCurriSubStudent.jsp";
			staticList		=	staticDao.getCourseStudentStatic(paramMap);
			model.put("pCurriName", CommonUtil.getCurriSubName(pSystemCode, pCurriCode, pCurriYear, pCurriTerm));
		}

		model.put("StaticList", staticList);

		model.put("pCurriYear", String.valueOf(pCurriYear));
		model.put("pCurriTerm", String.valueOf(pCurriTerm));
		return forward(request, model, goPage);
	}


	/**
	 * 과정별 수강현황 통계리스트
	 * @param curriYear
	 * @param curriTerm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList curriSubCourseList (int curriYear, int curriTerm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String	pSystemCode		=	UserBroker.getSystemCode(request);
		int		curyear			=	Integer.parseInt(CommonUtil.getCurrentDate().substring(0, 4));
		int		pCurriYear		=	(curriYear == 0) ? curyear : curriYear;

		Map		paramMap		=	new HashMap();
		paramMap.put("pSystemCode", pSystemCode);
		paramMap.put("pCurriYear", String.valueOf(pCurriYear));
		paramMap.put("pCurriTerm", String.valueOf(curriTerm));

		CurriSubStaticDAO	staticDao	=	new CurriSubStaticDAO();
		CurriSubStaticDTO	staticDto	=	null;
		ArrayList	list		=	new ArrayList();

		RowSet		rs		=	staticDao.getCurriSubCourseStatic(paramMap);
		while(rs.next()) {
			staticDto	=	new CurriSubStaticDTO();
			staticDto.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
			staticDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
			staticDto.setCurriName(StringUtil.nvl(rs.getString("curri_name")));
			staticDto.setPareCode1(StringUtil.nvl(rs.getString("pare_code1")));
			staticDto.setPareCode2(StringUtil.nvl(rs.getString("pare_code2")));
			staticDto.setCurriYear(rs.getInt("curri_year"));
			staticDto.setCurriTerm(rs.getInt("curri_term"));

			staticDto.setResultYn(StringUtil.nvl(rs.getString("complete_yn")));
			staticDto.setStudentCnt(rs.getInt("student_cnt"));
			staticDto.setProfName(StringUtil.nvl(rs.getString("prof_name")));
			staticDto.setNoticeCnt(rs.getInt("notice_cnt"));
			staticDto.setQanCnt(rs.getInt("qna_cnt"));
			staticDto.setReqCnt(rs.getInt("req_cnt"));
			staticDto.setResCnt(rs.getInt("res_cnt"));
			staticDto.setForumCnt(rs.getInt("forum_cnt"));
			staticDto.setForumContentsCnt(rs.getInt("forum_contents_cnt"));
			staticDto.setReportCnt(rs.getInt("report_cnt"));
			staticDto.setExamCnt(rs.getInt("exam_cnt"));

			list.add(staticDto);
		}
		return list;
	}


	/**
	 * 과정별 수강현황 -> 과정별 수강생 통계 리스트
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList courseStudentStaticList (String curriCode, int curriYear, int curriTerm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String	pSystemCode		=	UserBroker.getSystemCode(request);
		int		curyear			=	Integer.parseInt(CommonUtil.getCurrentDate().substring(0, 4));
		int		pCurriYear		=	(curriYear == 0) ? curyear : curriYear;

		Map		paramMap		=	new HashMap();
		paramMap.put("pSystemCode", pSystemCode);
		paramMap.put("pCurriCode", curriCode);
		paramMap.put("pCurriYear", String.valueOf(pCurriYear));
		paramMap.put("pCurriTerm", String.valueOf(curriTerm));

		CurriSubStaticDAO	staticDao	=	new CurriSubStaticDAO();
		CurriSubStaticDTO	staticDto	=	null;
		ArrayList	list		=	new ArrayList();

		RowSet	rs	=	staticDao.getCourseStudentStatic(paramMap);
		while(rs.next()) {
			staticDto	=	new CurriSubStaticDTO();
			staticDto.setCurriName(StringUtil.nvl(rs.getString("curri_name")));
			staticDto.setUserName(StringUtil.nvl(rs.getString("user_name")));
			staticDto.setQanCnt(rs.getInt("qna_cnt"));
			staticDto.setForumContentsCnt(rs.getInt("forum_contents_cnt"));
			staticDto.setReportCnt(rs.getInt("report_cnt"));
			staticDto.setExamCnt(rs.getInt("exam_cnt"));
			//staticDto.setScoreTotal(rs.getDouble("score_total"));
			staticDto.setResult(StringUtil.nvl(rs.getString("result")));
			staticDto.setReqCnt(rs.getInt("req_cnt"));
			staticDto.setResCnt(rs.getInt("res_cnt"));
			staticDto.setCourseRate(rs.getDouble("course_rate"));
			list.add(staticDto);
		}
		return list;

	}

	/**
	 * 과정명 받아오기
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getCurriSubName(String curriCode, int curriYear, int curriTerm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String	pSystemCode		=	UserBroker.getSystemCode(request);
		int		curyear			=	Integer.parseInt(CommonUtil.getCurrentDate().substring(0, 4));
		int		pCurriYear		=	(curriYear == 0) ? curyear : curriYear;

		String	curriName	=	"";
		curriName	=	CommonUtil.getCurriSubName(pSystemCode, curriCode, pCurriYear, curriTerm);

		return curriName;
	}


}

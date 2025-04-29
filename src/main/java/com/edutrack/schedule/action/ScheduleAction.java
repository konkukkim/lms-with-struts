/*
 * Created on 2004. 10. 5.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.schedule.action;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.CommonUtil;
import com.edutrack.common.DateSetter;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.curridate.dao.CurriDateDAO;
import com.edutrack.curridate.dto.CurriDateDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.schedule.dao.ScheduleDAO;
import com.edutrack.schedule.dto.ScheduleDTO;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ScheduleAction extends StrutsDispatchAction{

	/**
	 * 학사일정관리
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward haksaSchedule(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	pMode	= 	StringUtil.nvl(request.getParameter("pMode"), ENTER);
		
		int		currentYear	=	Integer.parseInt(CommonUtil.getCurrentDate().substring(0, 4));
		int		currentMonth =	Integer.parseInt(CommonUtil.getCurrentDate().substring(4, 6));
		int		hakgiTerm	=	1;
		if(currentMonth >= 7) {
			hakgiTerm	=	2;
		}
		
		model.put("pCurrentYear", String.valueOf(currentYear));
		model.put("pCurrentMonth", String.valueOf(currentMonth));
		model.put("pHakgiTerm", String.valueOf(hakgiTerm));		

		new SiteNavigation(pMode).add(request,"학사일정").link(model);
		return forward(request, model, "/schedule/haksaSchedule.jsp");
	}
	
	/**
 	 * 월별 일정을 보여주는 페이지로 이동한다.
 	 * @param actionMapping
 	 * @param actionForm
 	 * @param request
 	 * @param httpServletResponse
 	 * @param model
 	 * @return
 	 * @throws Exception
 	 */
	public ActionForward scheduleView(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		//log.debug("========= start view ");
		String	systemCode = UserBroker.getSystemCode(request);
		String	userId = UserBroker.getUserId(request);
		String	usertype = UserBroker.getUserType(request);
		String	pVMode = StringUtil.nvl(request.getParameter("pVMode"));
		if (usertype.equals("M")) pVMode="1";
		if (pVMode.equals("1")) userId="A";

		String pBEFORE = StringUtil.nvl(request.getParameter("pBEFORE"), "Y");		// 이전,이후월 선택 허용
		String pCU_YEAR = StringUtil.nvl(request.getParameter("pCU_YEAR"));			// 선택한 년도
		String pCU_MON = StringUtil.nvl(request.getParameter("pCU_MON"));			// 선택한 월

		Calendar st_date = Calendar.getInstance();		// 날짜시작
		Calendar ed_date = Calendar.getInstance();		// 날짜끝

		int st_year = 0;		// 시작년도
		int st_mon = 0;			// 시작월
		int ed_year = 0;		// 끝년도
		int ed_mon = 0;			// 끝월
		int st_week = 0;		// 월의 시작 요일
		int ed_day = 0;			// 월의 날짜
		int pr_year = 0;		// 이전 년도
		int pr_mon = 0;			// 이전 월
		int ne_year = 0;		// 다음 년도
		int ne_mon = 0;			// 다음 월

		//-- 넘겨받은 년도가 없으면 현재날짜의 년도로 설정
		if (pCU_YEAR.equals(""))
			st_year = st_date.get(Calendar.YEAR);
		else
			st_year = Integer.parseInt(pCU_YEAR);

		//-- 넘겨받은 월이 없으면 현재날짜의 월로 설정
		if (pCU_MON.equals(""))
			st_mon = st_date.get(Calendar.MONTH)+1;
		else
			st_mon = Integer.parseInt(pCU_MON);

		//-- 현재 월이 12월일 경우
		if (st_mon >= 12) {
			ed_day = 31;							// 마지막 날짜
			pr_year = st_year;
			pr_mon = st_mon - 1;
			ne_year = st_year + 1;
			ne_mon = 1;
			st_date.set(st_year, st_mon-1, 1);		// 표시 시작 날짜 설정
		} else {
			ed_year = st_year;
			ed_mon = st_mon + 1;
			st_date.set(st_year, st_mon-1, 1);		// 표시 시작 날짜 설정
			ed_date.set(ed_year, ed_mon-1, 1);		// 표시 끝 날짜 설정
			ed_day = ed_date.get(Calendar.DAY_OF_YEAR) - st_date.get(Calendar.DAY_OF_YEAR); // 마지막 날짜

			//-- 현재 월이 1월인 경우
			if (st_mon == 1) {
				pr_year = st_year - 1;		// 이전 년도
				pr_mon = 12;				// 이전 월
			} else {
				pr_year = st_year;			// 이전 년도
				pr_mon = st_mon - 1;		// 이전 월
			}
			ne_year = st_year;				// 다음 년도
			ne_mon = st_mon + 1;			// 다음 월
		}
		st_week = st_date.get(Calendar.DAY_OF_WEEK);// 시작위치 (1일의 요일)

		ScheduleDAO scheduleDAO = new ScheduleDAO();

		new SiteNavigation(MYPAGE).add(request,"일정관리").link(model);

		model.put("st_year", Integer.toString(st_year));
		model.put("st_mon", Integer.toString(st_mon));
		model.put("ed_day", Integer.toString(ed_day));
		model.put("pr_year", Integer.toString(pr_year));
		model.put("pr_mon", Integer.toString(pr_mon));
		model.put("ne_year", Integer.toString(ne_year));
		model.put("ne_mon", Integer.toString(ne_mon));
		model.put("st_week", Integer.toString(st_week));
		model.put("pBEFORE", pBEFORE);
		model.put("pVMode", pVMode);
		model.put("scheduleview", scheduleDAO.scheduleView(systemCode, userId, Integer.toString(st_year), Integer.toString(st_mon), pVMode));
		return forward(request, model, "/schedule/scheduleView.jsp");
	}


	/**
	 * 일정을 추가하는 페이지로 이동을 합니다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward ScheduleWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		//--	일반변수를값을 받아옵니다
		String	pRegMode		=	StringUtil.nvl(request.getParameter("pRegMode"));
		String	pSystemCode 	= 	UserBroker.getSystemCode(request);
		String	pUserId			= 	UserBroker.getUserId(request);
		String	pVMode			=	StringUtil.nvl(request.getParameter("pVMode"));

		DateSetter ds = null;

		//날짜
		String pYear			=	StringUtil.nvl(request.getParameter("pYear"));
		String pMonth			=	StringUtil.nvl(request.getParameter("pMonth"));
		String pDay				=	StringUtil.nvl(request.getParameter("pDay"));
		String pDate			= "";

		if(!pYear.equals("") && !pMonth.equals("") && !pDay.equals("")){
			if(pMonth.length() == 1 ){
				pMonth = "0"+pMonth;
			}

			if(pDay.length() == 1 ){
				pDay = "0"+pDay;
			}

			pDate = pYear + pMonth + pDay ;
			ds = new DateSetter(pDate, pDate).link(model);
		}else{
			ds = new DateSetter( DateTimeUtil.getDate(), DateTimeUtil.getDate()).link(model);
		}

		ScheduleDAO scheduleDao = new ScheduleDAO();
		ScheduleDTO scheduleDto = new ScheduleDTO();

		new SiteNavigation(MYPAGE).add(request,"일정관리").link(model);


		//수정모드일 경우
		if(pRegMode.equals("E")){
			String schid		=	StringUtil.nvl(request.getParameter("pSchId"), "");
			if (schid==null || schid.equals("") || schid.equals("null")) schid="";


			//시작 , 종료날짜 셋팅
			if (!pVMode.equals("1")) {
				scheduleDto = scheduleDao.getSchedule(pSystemCode, pUserId, schid);
			}
			else {
				scheduleDto = scheduleDao.getSchedule(pSystemCode, pUserId, schid, pVMode);
			}

			ds = new DateSetter( scheduleDto.getStartDate(), scheduleDto.getEndDate()).link(model);

			model.put("scheduleView",scheduleDto);
			model.put("pSchId",schid);
		}
		model.put("pVMode", pVMode);
		model.put("ds", ds);
		model.put("pRegMode",pRegMode);
		return forward(request, model, "/schedule/scheduleWrite.jsp");
	}

	/**
	 * 일정을 추가/수정 합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward scheduleRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	pRegMode		=	StringUtil.nvl(request.getParameter("pRegMode"));
		String  sYear			=	StringUtil.nvl(request.getParameter("pYear1"));
		String  eYear			=	StringUtil.nvl(request.getParameter("pYear2"));
		String  sMonth			=	StringUtil.nvl(request.getParameter("pMonth1"));
		String  eMonth			=	StringUtil.nvl(request.getParameter("pMonth2"));
		String  sDay			=	StringUtil.nvl(request.getParameter("pDay1"));
		String  eDay			=	StringUtil.nvl(request.getParameter("pDay2"));
		String  pStartDate 	 	=   sYear+sMonth+sDay;
		String  pEndDate 	 	=   eYear+eMonth+eDay;
		String  pSchType 		=   StringUtil.nvl(request.getParameter("pSchType"));
		String  pContents 		=   StringUtil.nvl(request.getParameter("pContents"));

		String  pSystemCode  	=   UserBroker.getSystemCode(request);
		String  pUserId			= 	UserBroker.getUserId(request);
		String	pVMode			=	StringUtil.nvl(request.getParameter("pVMode"));
		if (pVMode.equals("1"))	pUserId="A";

		ScheduleDAO scheduleDao = new ScheduleDAO();
		ScheduleDTO scheduleDto = new ScheduleDTO();

		int		retVal			=	0;

		new SiteNavigation(MYPAGE).add(request,"일정관리").link(model);

		String returnurl = "";
	    String msg = "";

		returnurl = "/Schedule.cmd?cmd=scheduleWrite&pRegMode="+pRegMode+"&pMode=MyPage";
		msg = "Error!!일정을 등록 하는데 에러가 발생하였습니다.";

		scheduleDto.setSystemCode(pSystemCode);
		scheduleDto.setUserId(pUserId);
		scheduleDto.setRegId(UserBroker.getUserId(request));
		scheduleDto.setStartDate(pStartDate);
		scheduleDto.setEndDate(pEndDate);
		scheduleDto.setSchType(pSchType);
		scheduleDto.setContents(pContents);
		scheduleDto.setModId(UserBroker.getUserId(request));

		if(pRegMode.equals("W")){
			retVal = scheduleDao.addSchedule(scheduleDto);
			if (retVal > 0) {
				returnurl = "/Schedule.cmd?cmd=scheduleView&pVMode="+pVMode+"&pMode=MyPage";
				msg = "일정을 등록하였습니다.";
			}
			//return notifyAndExit(pSystemCode, model, msg, returnurl, MYPAGE);
			return redirect(returnurl);
		}else if(pRegMode.equals("E")){
			String schid		=	StringUtil.nvl(request.getParameter("pSchId"));
			scheduleDto.setSchId(Integer.parseInt(schid));

			retVal = scheduleDao.editSchedule(scheduleDto, pVMode);
			if (retVal > 0) {
				returnurl = "/Schedule.cmd?cmd=scheduleView&pVMode="+pVMode+"&pMode=MyPage";
				msg = "일정을 수정하였습니다.";
			}else {
				returnurl = "/Schedule.cmd?cmd=scheduleWrite&pVMode="+pVMode+"&pRegMode="+pRegMode+"&pSchId="+schid+"&pMode=MyPage";
				msg = "Error!!일정을 수정 하는데 에러가 발생하였습니다.";
			}
			//return notifyAndExit(pSystemCode, model, msg, returnurl, MYPAGE);
			return redirect(returnurl);

		}else{
			//return notifyAndExit(pSystemCode, model, msg, returnurl, MYPAGE);
			return redirect(returnurl);
		}
	}

	/**
	 * 일정을 삭제합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward scheduleDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String  pSystemCode  	=   UserBroker.getSystemCode(request);
		String  pUserId			= 	UserBroker.getUserId(request);
		String schid			=	StringUtil.nvl(request.getParameter("pSchId"));
		String	pVMode			=	StringUtil.nvl(request.getParameter("pVMode"));

		ScheduleDAO scheduleDao = new ScheduleDAO();

		new SiteNavigation(MYPAGE).add(request,"일정관리").link(model);

		int		retVal			=	0;

		String returnurl = "";
	    String msg = "";

		returnurl = "/Schedule.cmd?cmd=scheduleWrite&pVMode="+pVMode+"&pRegMode=E&pSchId="+schid;
		msg = "Error!!일정을 삭제 하는데 에러가 발생하였습니다.";

		retVal = scheduleDao.delSchedule(pSystemCode, pUserId, schid, pVMode);

		if(retVal > 0){
        	returnurl = "/Schedule.cmd?cmd=scheduleView&pVMode="+pVMode;
        	msg = "일정을 삭제하였습니다.";
			return notifyAndExit(pSystemCode, model, msg, returnurl, MYPAGE);
        }
		return notifyAndExit(pSystemCode, model, msg, returnurl, MYPAGE);
	}

}

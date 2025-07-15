/**
 *
 */
package com.edutrack.curridate.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.DateSetter;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.curridate.dao.CurriDateDAO;
import com.edutrack.curridate.dto.CurriDateDTO;
import com.edutrack.currisub.dao.CurriSubDAO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/**
 * @author famlilia
 *
 */
public class CurriDateAction extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();


	public static String getDateFlag(String systemCode, String colName1, String colName2, String inputDate) {
		String dateFlag = "";
		CurriDateDAO curriDateDao = new CurriDateDAO();
		try {
			dateFlag = curriDateDao.getDateFlag(systemCode, colName1, colName2, inputDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateFlag;
	}


	/**
	 * 학기일자설정 리스트를 가져온다.
	 *
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriDateList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String	pGubun	=	StringUtil.nvl(request.getParameter("pGubun"));
		String	pRegMode	=	StringUtil.nvl(request.getParameter("pRegMode"));
		String	pCurriCode	=	StringUtil.nvl(request.getParameter("pCurriCode"));
		int		pCurriYear	=	0;//StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int		pHakgiTerm	=	0;
		int		pTotCount	=	0;

//		if(pGubun.equals("popup")) {
//			CurriSubDAO	subDao	=	new CurriSubDAO();
//			pTotCount	=	subDao.getTotCount(systemCode, "", pCurriCode, pCurriYear);
//			if(pTotCount != 0)
//				pHakgiTerm	=	subDao.getMaxCurriTerm(systemCode, pCurriCode, pCurriYear);
//		}

		CurriDateDAO	curriDateDao	=	new CurriDateDAO();
		ArrayList	dateList	=	curriDateDao.curriDateList(systemCode, pGubun, pCurriYear, pHakgiTerm);
		model.put("CurriDateList", dateList);
		model.put("pRegMode", pRegMode);

		String	returnUrl	=	"";
		if(pGubun.equals("popup")) {
			returnUrl	=	"/curri_date/curriDatePopupList.jsp";
		} else {
			returnUrl	=	"/curri_date/curriDateList.jsp";
		}

		new SiteNavigation(MYPAGE).add(request,"학기일자 설정").link(model);
        return forward(request, model, returnUrl);
	}

	/**
	 * 학기일자설정 등록, 수정한다.
	 *
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriDateWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String	systemCode	=	UserBroker.getSystemCode(request);
		String	pGubun		=	StringUtil.nvl(request.getParameter("pGubun"));
		int		pCurriYear	=	StringUtil.nvl(request.getParameter("pCurriYear"), 0);
		int		pHakgiTerm	=	StringUtil.nvl(request.getParameter("pHakgiTerm"), 0);

		if(pGubun.equals("W")) {

			DateSetter ds = new DateSetter("").link(model);
			model.put("enroll_date", ds);
			model.put("cancel_date", ds);
			model.put("service_date", ds);
			model.put("chung_end", ds);
			model.put("assessment_end", ds);
			model.put("complete_end", ds);

		} else if(pGubun.equals("E")) {

			CurriDateDAO	curriDateDao	=	new CurriDateDAO();
			CurriDateDTO	curriDateDto	=	curriDateDao.getCurriDateInfo(systemCode, pCurriYear, pHakgiTerm, "");
			model.put("curriDateDto", curriDateDto);

			DateSetter ds = new DateSetter((String)StringUtil.nvl(curriDateDto.getEnrollStart())).link(model);
			ds = new DateSetter(curriDateDto.getEnrollStart(),curriDateDto.getEnrollEnd()).link(model);
			model.put("enroll_date", ds);

			ds = new DateSetter(curriDateDto.getCancelStart(),curriDateDto.getCancelEnd()).link(model);
			model.put("cancel_date", ds);

			ds = new DateSetter((String)StringUtil.nvl(curriDateDto.getServiceStart()),(String)StringUtil.nvl(curriDateDto.getServiceEnd())).link(model);
			model.put("service_date", ds);

			ds = new DateSetter((String)StringUtil.nvl(curriDateDto.getChungEnd())).link(model);
			model.put("chung_end", ds);

			ds = new DateSetter((String)StringUtil.nvl(curriDateDto.getAssessmentEnd())).link(model);
			model.put("assessment_end", ds);

			ds = new DateSetter((String)StringUtil.nvl(curriDateDto.getCompleteEnd())).link(model);
			model.put("complete_end", ds);

		}
		model.put("pGubun", pGubun);


		new SiteNavigation(MYPAGE).add(request,"학기일자 설정").link(model);
        return forward(request, model, "/curri_date/curriDateWrite.jsp");
	}


	/**
	 * 중복되는 학기를 확인한다.
	 *
	 * @return
	 * @throws Exception
	 */
	public String checkHakgiTerm(int hakgiTerm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);

		CurriDateDAO	dateDao	=	new CurriDateDAO();
		int	retVal	=	dateDao.checkHakgiTerm(systemCode, hakgiTerm);

		return  String.valueOf(retVal);
	}


	/**
	 * 학기일자를 등록한다.
	 *
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriDateRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String	systemCode	=	UserBroker.getSystemCode(request);
		String	pGubun		=	StringUtil.nvl(request.getParameter("pGubun"));
		String	userId		=	UserBroker.getUserId(request);

		//--	파라미터값들 받아오기
		int		pCurriYear	=	StringUtil.nvl(request.getParameter("pCurriYear"), 0);
		int		pHakgiTerm	=	StringUtil.nvl(request.getParameter("pHakgiTerm"), 0);

		String	enrollStart	=	StringUtil.nvl(request.getParameter("pEDate1")).substring(0, 8)+"000001";
		String	enrollEnd	=	StringUtil.nvl(request.getParameter("pEDate2")).substring(0, 8)+"235959";
		String	cancelStart	=	StringUtil.nvl(request.getParameter("pCDate1")).substring(0, 8)+"000001";
		String	cancelEnd	=	StringUtil.nvl(request.getParameter("pCDate2")).substring(0, 8)+"235959";
		String	serviceStart	=	StringUtil.nvl(request.getParameter("pSDate1")).substring(0, 8)+"000001";
		String	serviceEnd	=	StringUtil.nvl(request.getParameter("pSDate2")).substring(0, 8)+"235959";
		String	chungEnd	=	StringUtil.nvl(request.getParameter("pDate71")).substring(0, 8)+"235959";
		String	assessmentEnd	=	StringUtil.nvl(request.getParameter("pDate81")).substring(0, 8)+"235959";
		String	completeEnd	=	StringUtil.nvl(request.getParameter("pDate91")).substring(0, 8)+"235959";
		String	pServiceYn	=	StringUtil.nvl(request.getParameter("pServiceYn"));

		//--	DTO에 SET하기
		CurriDateDTO	curriDateDto	=	new CurriDateDTO();
		curriDateDto.setSystemCode(systemCode);
		curriDateDto.setCurriYear(pCurriYear);
		curriDateDto.setHakgiTerm(pHakgiTerm);
		curriDateDto.setEnrollStart(enrollStart);
		curriDateDto.setEnrollEnd(enrollEnd);
		curriDateDto.setCancelStart(cancelStart);
		curriDateDto.setCancelEnd(cancelEnd);
		curriDateDto.setServiceStart(serviceStart);
		curriDateDto.setServiceEnd(serviceEnd);
		curriDateDto.setChungEnd(chungEnd);
		curriDateDto.setAssessmentEnd(assessmentEnd);
		curriDateDto.setCompleteEnd(completeEnd);
		curriDateDto.setServiceYn(pServiceYn);
		//-------------------------------------------
		curriDateDto.setRegId(userId);
		curriDateDto.setModId(userId);


		String	returnUrl	=	"/CurriDate.cmd?cmd=curriDateList";
		String	msg			=	"";
		int		retVal		=	0;
		CurriDateDAO	curriDateDao	=	new CurriDateDAO();

		if(pGubun.equals("W")) {
			retVal	=	curriDateDao.insertCurriDate(curriDateDto);
			if(retVal > 0) msg = "학기일자 설정을 등록했습니다.";
			else {
				msg			=	"학기일자를 등록하는데 실패했습니다.";
				returnUrl	=	"/CurriDate.cmd?cmd=CurriDateWrite&pGubun=W";
			}
		} else {
			retVal	=	curriDateDao.editCurriDate(curriDateDto);
			if(retVal > 0) msg = "학기일자 설정을 수정했습니다.";
			else {
				msg			=	"학기일자를 수정하는데 실패했습니다.";
				returnUrl	=	"/CurriDate.cmd?cmd=CurriDateWrite&pGubun=E&pCurriYear="+pCurriYear+"&pHakgiTerm="+pHakgiTerm;
			}
		}

		new SiteNavigation(MYPAGE).add(request,"학기일자설정").link(model);
		return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}

}

/*
 * Created on 2004. 10. 27.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curriresearch.action;

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
import com.edutrack.common.CurriSiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.curriresearch.dao.ResearchBankDAO;
import com.edutrack.curriresearch.dto.ResBkContentsDTO;
import com.edutrack.curriresearch.dto.ResBkInfoDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResearchBankAction extends StrutsDispatchAction{

	/**
	 *
	 */
	public ResearchBankAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActionForward researchBankContentsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);

		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pGubun = StringUtil.nvl(request.getParameter("pGubun"),"M");
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
		int pContentsResId = StringUtil.nvl(request.getParameter("pContentsResId"),0);

		model.put("pMODE",pMODE);
		model.put("pGubun",pGubun);
		model.put("pResId",""+pResId);
		model.put("pContentsResId",""+pContentsResId);

		String returnUrl = "/research/resh_contents_add_bank.jsp";
		if(pGubun.equals("M")) returnUrl = "/research/resh_bk_info_list.jsp";

		new CurriSiteNavigation(pMode).add(request,"설문문제은행").link(model);
		return forward(request, model, returnUrl);
	}

	/**
	 * 문제은행 항목별 문제리스트(Ajax)
	 * 2007.06.19 sangsang
	 * @param resId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList researchBankContentsListAuto(int resId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);

		// 리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		ResearchBankDAO researchBankDao = new ResearchBankDAO();
		ResBkContentsDTO resBkContentsDto	= null;

		RowSet rs = researchBankDao.getResearchBankContentsList(systemCode, resId,0);
		while(rs.next()){
			resBkContentsDto	= new ResBkContentsDTO();
			resBkContentsDto.setResId(resId);
			resBkContentsDto.setResNo(rs.getInt("res_no"));
			resBkContentsDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
			resBkContentsDto.setContents(StringUtil.setMaxLength(StringUtil.nvl(rs.getString("contents")),80));
			arrayList.add(resBkContentsDto);
		}
		rs.close();
		return arrayList;
	}

	/**
	 * 설문 문제은행항목 리스트를 셀렉트 박스로 보내준다..(ajax)
	 * 2007.06.19 sangsang
	 * @param userId
	 * @param shareYn
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map researchBankInfoSelectList(String userId, String shareYn, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);
		userId = StringUtil.nvl(userId);

		ResearchBankDAO bankDao = new ResearchBankDAO();
		Map map = new HashMap();
		RowSet rs = bankDao.getResearchBankInfoCodeList(systemCode, userId, shareYn);
		while(rs.next()){
			map.put(StringUtil.nvl(rs.getString("res_id")),StringUtil.nvl(rs.getString("subject")));
		}
		rs.close();
		return map;
	}

	/**
	 * 문제은행 구분항목(그룹) 등록/수정/삭제 (Ajax)
	 * @param regMode
	 * @param resId
	 * @param subject
	 * @param shareYn
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String researchBankInfoRegist(String regMode, int resId, String subject, String shareYn, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		subject = StringUtil.nvl(subject);
		shareYn = StringUtil.nvl(shareYn,"N");

		ResBkInfoDTO bankinfo = new ResBkInfoDTO();
		bankinfo.setShareYn(shareYn);
		bankinfo.setSubject(subject);
		bankinfo.setUserId(userId);
		bankinfo.setResId(resId);

		ResearchBankDAO bankDao = new ResearchBankDAO();

	    int	retVal = 0;
		if(regMode.equals("write")){
			retVal = bankDao.addResearchBankInfo(systemCode,bankinfo);
		}else if(regMode.equals("edit")){
			retVal = bankDao.editResearchBankInfo(systemCode, bankinfo);
		}else if(regMode.equals("delete")){
			boolean retValDel = false;
			retValDel = bankDao.delResearchBankInfo(systemCode, resId);
			if(retValDel)
				retVal = 1;
			else
				retVal = 0;
		}
		return String.valueOf(retVal);
	}

	/**
	 * 문제항목 정보를 가져온다 (Ajax)
	 * 2007.06.20 sangsang
	 * @param resId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ResBkInfoDTO researchBankInfo(int resId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		ResBkInfoDTO resBankInfoDto = null;
		ResearchBankDAO bankDao = new ResearchBankDAO();
		resBankInfoDto = bankDao.getResearchBankInfo(systemCode, resId);

		return resBankInfoDto;
	}

	/**
	 * 문제항목 문제정보를 가져온다 (Ajax)
	 * 2007.06.21 sangsang
	 * @param resId
	 * @param resNo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ResBkContentsDTO researchBankContentsInfo(int resId, int resNo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		ResBkContentsDTO resBkContentsDto = new ResBkContentsDTO();
		ResearchBankDAO researchBankDao = new ResearchBankDAO();
		resBkContentsDto = researchBankDao.getResearchBankContentsInfo(systemCode ,resId, resNo);

		return resBkContentsDto;
	}


	public int researchBankContentsRegist(ResBkContentsDTO resBkContentsDto, String regMode, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		ResearchBankDAO researchBankDao = new ResearchBankDAO();
	    int	retVal = 0;
		if(regMode.equals("write")){
			resBkContentsDto.setRegId(userId);
			retVal = researchBankDao.addResearchBankContentsInfo(systemCode, resBkContentsDto);
		}else if(regMode.equals("edit")){
			resBkContentsDto.setModId(userId);
			retVal = researchBankDao.editResearchBankContentsInfo(systemCode, resBkContentsDto);
		}else if(regMode.equals("delete")){
			retVal = researchBankDao.delResearchBankContents(systemCode, resBkContentsDto.getResId(), resBkContentsDto.getResNo());
		}
		return retVal;
	}

	public ActionForward researchBankInfoWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pGubun = StringUtil.nvl(request.getParameter("pGubun"));
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
		ResBkInfoDTO bankInfo = null;
		if(pMODE.equals("write")){
		     bankInfo = new ResBkInfoDTO();
		}else{
			ResearchBankDAO bankDao = new ResearchBankDAO();
			bankInfo = bankDao.getResearchBankInfo(systemCode, pResId);
		}

		model.put("bankInfo",bankInfo);
		model.put("pMODE", pMODE);
		model.put("pGubun", pGubun);
        model.put("pResId", ""+pResId);

		return forward(request, model, "/research/resh_bk_type_write.jsp");
	}

	public ActionForward researcBankInfoRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
        String pShareYn = StringUtil.nvl(request.getParameter("pShareYn"));
        String pSubject = StringUtil.nvl(request.getParameter("pSubject"));
		String userId = UserBroker.getUserId(request);
		String msg = "항목을 등록하였습니다.";

		ResBkInfoDTO bankinfo = new ResBkInfoDTO();
		bankinfo.setShareYn(pShareYn);
		bankinfo.setSubject(pSubject);
		bankinfo.setUserId(userId);
		bankinfo.setResId(pResId);

		ResearchBankDAO bankDao = new ResearchBankDAO();
		if(pMODE.equals("write")){
			bankDao.addResearchBankInfo(systemCode,bankinfo);
		}else{
			msg = "항목을 수정하였습니다.";
			bankDao.editResearchBankInfo(systemCode, bankinfo);
		}

		return closePopupReload(systemCode, model, msg,"O","POPUP");
	}

	public ActionForward researcBankInfoDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);

		ResearchBankDAO bankDao = new ResearchBankDAO();
		bankDao.delResearchBankInfo(systemCode, pResId);

		return closePopupReload(systemCode, model, "항목을 삭제하였습니다.","P","POPUP");
	}

	public ActionForward researchBankContentsWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
		int pResNo = StringUtil.nvl(request.getParameter("pResNo"),0);
		String pContentsType = StringUtil.nvl(request.getParameter("pContentsType"));

		ResBkContentsDTO contentsInfo = null;
		ResearchHelper helper = new ResearchHelper();
		if(pMODE.equals("write")){
		     contentsInfo = new ResBkContentsDTO();
		}else{
			ResearchBankDAO contentsDao = new ResearchBankDAO();
			contentsInfo = contentsDao.getResearchBankContentsInfo(systemCode ,pResId, pResNo);
		}

		model.put("contentsInfo",contentsInfo);
    	model.put("pMODE", pMODE);
		model.put("pResId",""+pResId);
		model.put("pResNo",""+pResNo);
		model.put("pContentsType",pContentsType);

        String navMsg = "설문등록";

        if(!pMODE.equals("write")) navMsg = "설문수정";

		new CurriSiteNavigation(LECTURE).add(request,navMsg).link(model);

		return forward(request, model, "/research/resh_bk_contents_write.jsp");
	}

	public ActionForward researchBankContentsShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
		int pResNo = StringUtil.nvl(request.getParameter("pResNo"),0);

		ResearchBankDAO contentsDao = new ResearchBankDAO();
		ResBkContentsDTO contentsInfo = contentsDao.getResearchBankContentsInfo(systemCode ,pResId, pResNo);

		model.put("contentsInfo",contentsInfo);

		return forward(request, model, "/research/resh_bk_contents_show.jsp");
	}


}

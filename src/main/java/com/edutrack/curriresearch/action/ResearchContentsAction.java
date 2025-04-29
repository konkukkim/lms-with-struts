/*
 * Created on 2004. 10. 25.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curriresearch.action;

import java.util.ArrayList;
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
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.curriresearch.dao.ResearchAnswerDAO;
import com.edutrack.curriresearch.dao.ResearchContentsDAO;
import com.edutrack.curriresearch.dao.ResearchInfoDAO;
import com.edutrack.curriresearch.dto.CurriResContentsDTO;
import com.edutrack.curriresearch.dto.CurriResInfoDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResearchContentsAction extends StrutsDispatchAction{

	/**
	 *
	 */
	public ResearchContentsAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 과정별 설문 문제 리스트로 이동한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward researchContentsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);

		ResearchAnswerDAO answerDao = new ResearchAnswerDAO();
		int answerUserCnt = answerDao.getResearchAnswerCnt(systemCode, curriInfo, pResId,"");

		model.put("answerUserCnt", ""+answerUserCnt);
		model.put("pResId",""+pResId);

		new CurriSiteNavigation(LECTURE).add(request,"설문문항리스트").link(model);
		return forward(request, model, "/research/resh_mg_contents_list.jsp");
	}

	/**
	 * 문제은행 과정별 문제리스트(Ajax)
	 * 2007.06.20 sangsang
	 * @param resId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList researchContentsListAuto(int resId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		// 리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		ResearchContentsDAO researchContentsDao = new ResearchContentsDAO();
		CurriResContentsDTO curriResContentsDto	= null;

		RowSet rs = researchContentsDao.getResearchContentsList(systemCode, curriInfo, resId, 0);
		while(rs.next()){
			curriResContentsDto	= new CurriResContentsDTO();
			curriResContentsDto.setResId(resId);
			curriResContentsDto.setResNo(rs.getInt("res_no"));
			curriResContentsDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
			curriResContentsDto.setContents(StringUtil.setMaxLength(StringUtil.nvl(rs.getString("contents")),80));
			arrayList.add(curriResContentsDto);
		}
		rs.close();
		return arrayList;
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
	public CurriResContentsDTO researchContentsInfo(int resId, int resNo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		ResearchContentsDAO researchContentsDao = new ResearchContentsDAO();
		CurriResContentsDTO curriResContentsDto =  new CurriResContentsDTO();
		curriResContentsDto = researchContentsDao.getResearchContentsInfo(systemCode,curriInfo ,resId, resNo);

		return curriResContentsDto;
	}

	/**
	 * 과정별 설문문제 등록/수정/삭제 (Ajax)
	 * 2007.06.22 sangsang
	 * @param curriResContentsDto
	 * @param regMode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public int researchContentsRegist(CurriResContentsDTO curriResContentsDto, String regMode, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		UserMemDTO userInfo = UserBroker.getUserInfo(request);
		String userId = userInfo.userId;

		ResearchContentsDAO researchContentsDao = new ResearchContentsDAO();
	    int	retVal = 0;
		if(regMode.equals("write")){
			curriResContentsDto.setRegId(userId);
			retVal = researchContentsDao.addResearchContentsInfo(systemCode, userInfo.curriInfo, curriResContentsDto);
		}else if(regMode.equals("edit")){
			curriResContentsDto.setModId(userId);
			retVal = researchContentsDao.editResearchContentsInfo(systemCode, userInfo.curriInfo, curriResContentsDto);
		}else if(regMode.equals("delete")){
			retVal = researchContentsDao.delResearchContents(systemCode, userInfo.curriInfo, curriResContentsDto.getResId(), curriResContentsDto.getResNo());
		}

		return retVal;
	}

	/**
	 * 설문문제 입력
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward researchContentsWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);

		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
		int pResNo = StringUtil.nvl(request.getParameter("pResNo"),0);
		String pContentsType = StringUtil.nvl(request.getParameter("pContentsType"));
		int answerUserCnt = StringUtil.nvl(request.getParameter("answerUserCnt"),0);

		CurriResContentsDTO contentsInfo = null;
		ResearchHelper helper = new ResearchHelper();
		if(pMODE.equals("write")){
		     contentsInfo = new CurriResContentsDTO();
		}else{
			ResearchContentsDAO contentsDao = new ResearchContentsDAO();
			CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
			contentsInfo = contentsDao.getResearchContentsInfo(systemCode,curriInfo ,pResId, pResNo);
		}

		model.put("contentsInfo",contentsInfo);
    	model.put("pMODE", pMODE);
		model.put("pResId",""+pResId);
		model.put("pResNo",""+pResNo);
		model.put("pContentsType",pContentsType);
		model.put("answerUserCnt", ""+answerUserCnt);

        String navMsg = "설문등록";
        if(!pMODE.equals("write")) navMsg = "설문수정";

		new CurriSiteNavigation(pMode).add(request,navMsg).link(model);
		return forward(request, model, "/research/resh_mg_contents_write.jsp");
	}


    /**
     * 설문을 보여준다.
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
	public ActionForward researchContentsShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		if(pMODE.equals("R")){
			ResearchAnswerDAO answerDao = new ResearchAnswerDAO();
			int answerUserCnt = answerDao.getResearchAnswerCnt(systemCode, curriInfo, pResId,userId);

			if(answerUserCnt > 0) return alertPopupCloseResponse(systemCode, model,"이미 설문에 참여 하셨습니다.",LECTURE);
		}

		ResearchInfoDAO infoDao = new ResearchInfoDAO();
		CurriResInfoDTO infoDto = infoDao.getResearchInfo(systemCode, curriInfo, pResId);

		ResearchContentsDAO contentsDao = new ResearchContentsDAO();
		RowSet contentsList = contentsDao.getResearchContentsList(systemCode, curriInfo, pResId,0);

		model.put("pMODE",pMODE);
		model.put("pResId",""+pResId);
		model.put("infoDto",infoDto);
		model.put("contentsList",contentsList);

		return forward(request, model, "/research/resh_st_show.jsp");
	}

	/**
	 * 문제은행에서 설문으로 문제 복사
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward researchBankContentsCopy(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);

		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
		int pContentsResId = StringUtil.nvl(request.getParameter("pContentsResId"),0);
		String[] resNo = request.getParameterValues("contentsBox");
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
		ResearchContentsDAO contentsDao = new ResearchContentsDAO();
		boolean retVal = contentsDao.moveBankContents(systemCode,curriInfo,pResId,pContentsResId,userId,resNo);

		String returnUrl="/ResearchContents.cmd?cmd=researchContentsList&pResId="+pContentsResId;
		if(retVal){
			return redirect(returnUrl);
		}else{
			String msg = "문제 등록에 실패하였습니다.<br>다시 진행해 주세요";
			return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
		}
	}

	/**
	 * 설문문제 삭제
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward researchContentsDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);

		int pResId = StringUtil.nvl(request.getParameter("pResId"),0);
		int pResNo = StringUtil.nvl(request.getParameter("pResNo"),0);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		ResearchContentsDAO contentsDao = new ResearchContentsDAO();
		contentsDao.delResearchContents(systemCode,curriInfo, pResId, pResNo);

		return redirect("/ResearchContents.cmd?cmd=researchContentsList&pResId="+pResId);
	}
}

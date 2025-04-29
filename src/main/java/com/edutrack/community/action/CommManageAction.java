package com.edutrack.community.action;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.community.dao.CommBbsCommentDAO;
import com.edutrack.community.dao.CommBbsInfoDAO;
import com.edutrack.community.dao.CommInfoDAO;
import com.edutrack.community.dao.CommManageDAO;
import com.edutrack.community.dao.CommMembersDAO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;
/*
 * Created on 2004. 6. 28.
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

/**
 * @author sangsang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommManageAction extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();


	/**
	 * 동아리 관리
	 * 제주원격교육연수원 - jonghyun.park - 2006.03.21
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commInfoPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		ListDTO commInfoPagingList = null;

		int curPage = 1;

		if(request.getParameter("curPage") != null)
			curPage = Integer.parseInt(request.getParameter("curPage"));

		CommManageDAO commManageDao = new CommManageDAO();
		commInfoPagingList = commManageDao.getCommInfoPagingList(curPage, systemCode);
        model.put("commInfoPagingList", commInfoPagingList);

		new SiteNavigation(COMMUNITY).add(request,"동아리관리").link(model);

		return forward(request, model, "/commManage/commInfoPagingList.jsp");
	}


	
	/**
	 * 폐쇄신청 동아리
	 * 제주원격교육연수원 - jonghyun.park - 2006.03.21
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commInfoClosingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode	= UserBroker.getSystemCode(request);
		String pMode		= StringUtil.nvl(request.getParameter("pMode"),COMMUNITY);
		String useYn 		= request.getParameter("pUseYn");
		String	naviMsg		=	"";

		ListDTO commInfoClosingList = null;
		int totCnt	= 0;
		int curPage	= 1;

		if(request.getParameter("curPage") != null)
			curPage = Integer.parseInt(request.getParameter("curPage"));

		CommManageDAO commManageDao = new CommManageDAO();
		commInfoClosingList = commManageDao.getCommInfoClosingList(curPage, systemCode, useYn);

		model.put("commInfoClosingList", commInfoClosingList);

        String forwardUrl = "";	 									//	포워드 url

		if(useYn.equals("C")) {
			// 폐쇄신청동아리일때
			forwardUrl 	= 	"/commManage/commInfoClosingList.jsp";
			naviMsg		=	"폐쇄신청 동아리";
		}        	
        else {
        	// 폐쇄된동아리일때
        	forwardUrl 	= 	"/commManage/commInfoClosedList.jsp";
        	naviMsg		=	"폐쇄된 동아리";
        }        	

		new SiteNavigation(pMode).add(request, naviMsg).link(model);
		return forward(request, model, forwardUrl);
	}


	/**
	 * 동아리를 추천여부를 설정한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commInfoBestYnChange(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("======================commInfoBestYnChange start");
		String systemCode = UserBroker.getSystemCode(request);
		log.debug("=============pCommId : "+request.getParameter("pCommId"));
		int commId=Integer.parseInt(request.getParameter("pCommId"));

		String bestYn = StringUtil.nvl(request.getParameter("pBestYn"));
		String curPos = StringUtil.nvl(request.getParameter("pCurPos"));
		if(bestYn.equals("Y"))
			bestYn="N";
		else
			bestYn="Y";

		CommManageDAO commManageDao = new CommManageDAO();

		String msg = "추천동아리 여부 변경했습니다.";
		String returnUrl = "";
		if(curPos.equals("LIST")){
			returnUrl = "/CommManage.cmd?cmd=commInfoPagingList";
		}
		else if(curPos.equals("NEW")){
			returnUrl = "/CommManage.cmd?cmd=commInfoNewList";
		}

		int retVal = 0;
		retVal = commManageDao.commInfoBestYnChange(systemCode, commId, bestYn);

		if(retVal <=0){
			msg = "추천동아리 변경 실패 하였습니다.";
		}

		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals("")) pMode = MYPAGE;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter("동아리")).link(model);
        return notifyAndExit(systemCode, model, msg, returnUrl, COMMUNITY);
	}


	/**
	 * NEW 커뮤티니티
	 * 제주원격교육연수원 - jonghyun.park - 2006.03.21
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commInfoNewList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode	= UserBroker.getSystemCode(request);
		String pMode		= StringUtil.nvl(request.getParameter("pMode"),COMMUNITY);
		int totCnt 			= 0;

		CommManageDAO commManageDao = new CommManageDAO();
		RowSet rs = commManageDao.getCommInfoNewList(systemCode);

		model.put("rs", rs);

		new SiteNavigation(pMode).add(request,"동아리").link(model);
        return forward(request, model, "/commManage/commInfoNewList.jsp");
	}


	/**
	 * 동아리 폐쇄여부를 변경한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commInfoClosingChange(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("======================commInfoBestYnChange start");
		String systemCode = UserBroker.getSystemCode(request);
		int commId=Integer.parseInt(request.getParameter("pCommId"));

		String useYn = StringUtil.nvl(request.getParameter("pUseYn"));

		CommManageDAO commManageDao = new CommManageDAO();

		String msg = "동아리 폐쇄 여부 변경했습니다.";
		String returnUrl = "/CommManage.cmd?cmd=commInfoClosingList&MENUNO=14&pUseYn=C";

		int retVal = 0;
		retVal = commManageDao.commInfoClosingChange(systemCode, commId, useYn);

		if(retVal <=0){
			msg = "동아리 폐쇄 여부 변경 실패 하였습니다.";
		}

		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals("")) pMode = MYPAGE;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter("동아리")).link(model);
        return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}

	/**
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commInfoReuse(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("======================commInfoReuse start");
		String systemCode = UserBroker.getSystemCode(request);
		int commId=Integer.parseInt(request.getParameter("pCommId"));

		String useYn = StringUtil.nvl(request.getParameter("pUseYn"));

		CommManageDAO commManageDao = new CommManageDAO();

		String msg = "동아리를 재사용할 수 있게  변경하는데 성공하였습니다.";
		String returnUrl = "/CommManage.cmd?cmd=commInfoClosingList&pUseYn=N";
		int retVal = 0;
		retVal = commManageDao.commInfoReuse(systemCode, commId);

		if(retVal <=0){
			msg = "동아리를 재사용할 수 있게  변경하는데 실패 하였습니다.";
		}

		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals("")) pMode = MYPAGE;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter("동아리")).link(model);
        return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}

	/**
	 * 동아리 완전삭제(데이타삭제)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commInfoDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("======================commInfoDelete start");
		String systemCode = UserBroker.getSystemCode(request);
		int commId=Integer.parseInt(request.getParameter("pCommId"));

		CommBbsCommentDAO commBbsCommentDao = new CommBbsCommentDAO();
		CommBbsInfoDAO commBbsInfoDao = new CommBbsInfoDAO();
		CommInfoDAO commInfoDao = new CommInfoDAO();
		CommManageDAO commManageDao = new CommManageDAO();

		String msg = "동아리를 완전삭제 하였습니다.";
		String returnUrl = "/CommManage.cmd?cmd=commInfoClosingList&pUseYn=N&pMode=Community";

		//동아리 서브 테이블에서 데이터 삭제
		int retVal = 0;
		retVal = commBbsCommentDao.delBbsComment(systemCode, commId);
		retVal = commBbsInfoDao.delBbsContents(systemCode, commId);
		retVal = commManageDao.commInfoAllDelete(systemCode, commId);
		retVal = commInfoDao.delCommInfo(systemCode, commId);
		retVal = commInfoDao.delCommMemberReg(systemCode, "", commId);

		if(retVal <= 0 ){
			msg = "동아리 완전삭제 실패하였습니다.";
		}

		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals("")) pMode = MYPAGE;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter("동아리")).link(model);
        //return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
		return redirect(returnUrl);
	}

	/**
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commInfoShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("====================== commInfoShow start");
		String systemCode = UserBroker.getSystemCode(request);
		int commId = Integer.parseInt(request.getParameter("pCommId"));
		String pGubun = StringUtil.nvl(request.getParameter("pGubun"));
		CommManageDAO commManageDao = new CommManageDAO();
		RowSet rs = commManageDao.getCommInfo(systemCode, commId);
		rs.next();

		model.put("cate_code", rs.getString("cate_code"));
		model.put("comm_name", rs.getString("comm_name"));
		model.put("cate_name", rs.getString("cate_name"));

		model.put("main_img", rs.getString("main_img"));
		model.put("comm_synopsis", rs.getString("comm_synopsis"));
		model.put("keyword", rs.getString("keyword"));
		model.put("regist_type", rs.getString("regist_type"));
		model.put("pGubun",pGubun); // 관리자와 일반 사용자에게 정보 공개 난위도 조정을 위해..
		rs.close();
		new SiteNavigation(MYPAGE).add(request,"동아리관리").link(model);
		return forward(request, model, "/commManage/commInfoShow.jsp");
	}

	/**
	 * 시샵을 변경한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeMemberList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pCommId = StringUtil.nvl(request.getParameter("pCommId"),"0");

		CommMembersDAO membersDao = new CommMembersDAO();
		model.put("userList", membersDao.getCommMemberList(systemCode, pCommId));
		model.put("pCommId", pCommId);
		return forward(request, model, "/commManage/commMasterChange.jsp");
	}

	/**
	 * 마스터를 변경한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeMaster(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String pCommId = StringUtil.nvl(request.getParameter("pCommId"),"0");
		String pCommUserId = StringUtil.nvl(request.getParameter("pCommUserId"));
        log.debug(pCommUserId+"-"+pCommId);
		CommMembersDAO membersDao = new CommMembersDAO();
		boolean retVal = membersDao.changeMaster(systemCode, pCommId, pCommUserId,userId);
		String msg = "마스터를 변경하였습니다.";
		if(!retVal) msg = "마스터를 변경하는데 실패하였습니다.";

		return closePopupReload(systemCode, model, msg,"O","POPUP");
	}

	/**
	 * 동아리를 승인한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward approveCommInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String pCommId = StringUtil.nvl(request.getParameter("pCommId"),"0");
		CommManageDAO manageDao = new CommManageDAO();
		boolean retVal = manageDao.approveCommInfo(systemCode, pCommId,userId);
		String msg = "동아리 승인을 완료 하였습니다.";
		if(!retVal) msg = "동아리 승인하는데 실패하였습니다.";

		return notifyAndExit(systemCode, model, msg, "/CommManage.cmd?cmd=commInfoNewList", COMMUNITY);
	}
}

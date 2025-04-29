package com.edutrack.community.action;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CommMemDTO;
import com.edutrack.community.dao.CommBbsCommentDAO;
import com.edutrack.community.dto.CommBbsCommentDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;
/*
 * Created on 2004. 6. 28.
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

/**
 * @author bschoi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */  
public class CommPrideCommentAction extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();
	

	/**
	 * 코멘트 삭제 비밀번호 입력 폼
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward bbsCommentPassChkForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("===========================bbsCommentPassChkForm start");
		String systemCode = UserBroker.getSystemCode(request);
		String curPage = StringUtil.nvl(request.getParameter("curPage"));
		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));
		int pCommNo = Integer.parseInt(request.getParameter("pCommNo"));
		String pMode = StringUtil.nvl(request.getParameter("pMode"));

		model.put("systemCode", systemCode);
		model.put("curPage", curPage);
		model.put("pBbsId", Integer.toString(pBbsId));	
		model.put("pSeqNo", Integer.toString(pSeqNo));
		model.put("pCommNo", Integer.toString(pCommNo));
		model.put("pMode", pMode);
		model.put("passChk", "N");
		log.debug("===========================bbsCommentPassChkForm end");
		return forward(request, model, "/commnuty/PrideCommentPassChkForm.jsp");
	}
	/**
	 * 코멘트 삭제 (비밀번호 비교 포함)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward bbsCommentDeletePassChk(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("===========================bbsCommentDelete start");
		String systemCode = UserBroker.getSystemCode(request);
		String curPage = StringUtil.nvl(request.getParameter("curPage"));
		int pCommId = Integer.parseInt(request.getParameter("pCommId"));
		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));
		int pCommNo = Integer.parseInt(request.getParameter("pCommNo"));
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		String pPasswd = StringUtil.nvl(request.getParameter("pPasswd"));
		
		//BbsCommentDAO bbsCommentDao = new BbsCommentDAO();
		CommBbsCommentDAO commbbsCommentDao = new CommBbsCommentDAO();
		
		RowSet infoRs = commbbsCommentDao.getBbsComment(systemCode,pBbsId,pSeqNo,pCommNo);
		infoRs.next();
		String regPasswd = infoRs.getString("reg_passwd");
		String msg = "";
		//String returnUrl = "/BbsContents.cmd?cmd=bbsContentsShow&pBbsId="+pBbsId+"&pSeqNo="+pSeqNo+"&curPage="+curPage;
		model.put("systemCode", systemCode);
		model.put("curPage", curPage);
		model.put("pCommId", Integer.toString(pCommId));		
		model.put("pBbsId", Integer.toString(pBbsId));	
		model.put("pSeqNo", Integer.toString(pSeqNo));
		model.put("pCommNo", Integer.toString(pCommNo));
		model.put("pMode", pMode);
		model.put("passChk", "Y");
		int retVal = 0;
		if(pPasswd.equals(regPasswd)){
			//-- 비밀 번호 일치할 경우
			retVal = commbbsCommentDao.delBbsComment(systemCode,pCommId,pBbsId,pSeqNo,pCommNo);		
			log.debug("+++++++++++++++++++ retVal = "+retVal);
			if(retVal > 0)
				msg = "삭제하였습니다.";
			else
				msg = "삭제 실패 하였습니다.";
			return closePopupReload(systemCode, model, msg, "N","O","");	
		}else{
			return forward(request, model, "/commnuty/PrideCommentPassChkForm.jsp");		
		}
	
	}

	/**
	 * Comment 삭제한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward bbsCommentDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("===========================bbsCommentDelete start");
		String systemCode = UserBroker.getSystemCode(request);
		String curPage = StringUtil.nvl(request.getParameter("curPage"));
		int pCommId = Integer.parseInt(request.getParameter("pCommId"));		
		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));
		int pCommNo = Integer.parseInt(request.getParameter("pCommNo"));

		//BbsCommentDAO bbsCommentDao = new BbsCommentDAO();
		CommBbsCommentDAO commbbsCommentDao = new CommBbsCommentDAO();
		
		String msg = "";
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals("")) pMode = COMMUNITY;
		
		String returnUrl="";
		
		if(pCommId == 1)
			returnUrl = "/Community.cmd?cmd=commPrideContentsShow&pMode="+pMode+"&pCommId="+pCommId+"&pBbsId="+pBbsId+"&pSeqNo="+pSeqNo+"&curPage="+curPage;
		else
			returnUrl = "/Community.cmd?cmd=commSubNoticeShow&pMode="+pMode+"&pCommId="+pCommId+"&pBbsId="+pBbsId+"&pSeqNo="+pSeqNo+"&curPage="+curPage;
		
		int retVal = 0;
		retVal = commbbsCommentDao.delBbsComment(systemCode,pCommId,pBbsId,pSeqNo,pCommNo);		
		log.debug("+++++++++++++++++++ retVal = "+retVal);
		log.debug("===========================bbsCommentDelete end");
		 return redirect(returnUrl);
	}
	
	
	/**
	 * Comment 등록 한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward bbsCommentRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=============================================================bbsCommentRegist start");
		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);
		String curPage = StringUtil.nvl(request.getParameter("curPage"));
		int pCommId = StringUtil.nvl(request.getParameter("pCommId"),0);
		int pBbsId = StringUtil.nvl(request.getParameter("pBbsId"),0);
		int pSeqNo = StringUtil.nvl(request.getParameter("pSeqNo"),1);

		log.debug("+++++++++++++++++++++++++++++++++   chk Point0");

		CommBbsCommentDAO commbbsCommentDao = new CommBbsCommentDAO();
		CommBbsCommentDTO commbbsCommentDto = new CommBbsCommentDTO();
		
		log.debug("+++++++++++++++++++++++++++++++++   chk Point1");
		int retVal = 0;
		int pCommNo = commbbsCommentDao.getMaxCommNo(systemCode,pBbsId,pSeqNo);
		
		String pContents = StringUtil.nvl(request.getParameter("pContents"));
		String pEmoticon	= StringUtil.nvl(request.getParameter("pEmoticon"));
		
		log.debug("+++++++++++++++++++++++++++++++++   chk Point2");
		String regName = StringUtil.nvl(request.getParameter("pRegName"));
		String regPasswd = StringUtil.nvl(request.getParameter("pRegPasswd"));
		String regEmail = StringUtil.nvl(request.getParameter("pRegEmail"));
		commbbsCommentDto.setSystemCode(systemCode);
		commbbsCommentDto.setCommId(pCommId);
		commbbsCommentDto.setBbsId(pBbsId);
		commbbsCommentDto.setSeqNo(pSeqNo);
		commbbsCommentDto.setCommNo(pCommNo);
		commbbsCommentDto.setContents(pContents);
		commbbsCommentDto.setEmoticonNum(pEmoticon);
		commbbsCommentDto.setRegId(regId);
		commbbsCommentDto.setRegName(regName);
		commbbsCommentDto.setRegEmail(regEmail);
		commbbsCommentDto.setRegPasswd(regPasswd);
		String msg = "";
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals("")) pMode = COMMUNITY;
		
		String returnUrl="";
		
		if(pCommId == 1)
			returnUrl = "/Community.cmd?cmd=commPrideContentsShow&pMode="+pMode+"&pCommId="+pCommId+"&pBbsId="+pBbsId+"&pSeqNo="+pSeqNo+"&curPage="+curPage;
		else
			returnUrl = "/Community.cmd?cmd=commSubNoticeShow&pMode="+pMode+"&pCommId="+pCommId+"&pBbsId="+pBbsId+"&pSeqNo="+pSeqNo+"&curPage="+curPage;
		
		log.debug("+++++++++++++++++++++++++++++++++   chk Point 3");
		retVal = commbbsCommentDao.addBbsComment(commbbsCommentDto);
		log.debug("=============================================================bbsCommentRegist end");
        return redirect(returnUrl);
	}
	/*********************************************/
	/** 통합 보드 관련 메서드
	/*********************************************/
	
	public ActionForward boardCommentRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=============================================================bbsCommentRegist start");
		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);
		
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String commName = commMemDto.commName;
		String commId	= commMemDto.commId;
		String userLevel = commMemDto.userLevel;
		
		String curPage = StringUtil.nvl(request.getParameter("curPage"));
		int pCommId = StringUtil.nvl(request.getParameter("pCommId"),0);
		int pBbsId = StringUtil.nvl(request.getParameter("pBbsId"),0);
		int pSeqNo = StringUtil.nvl(request.getParameter("pSeqNo"),1);
		String pBbstype = StringUtil.nvl(request.getParameter("pBbstype"));

		log.debug("+++++++++++++++++++++++++++++++++   chk Point0");

		CommBbsCommentDAO commbbsCommentDao = new CommBbsCommentDAO();
		CommBbsCommentDTO commbbsCommentDto = new CommBbsCommentDTO();
		
		log.debug("+++++++++++++++++++++++++++++++++   chk Point1");
		int retVal = 0;
		int pCommNo = commbbsCommentDao.getMaxCommNo(systemCode,commId,pBbsId,pSeqNo);
		
		String pContents = StringUtil.nvl(request.getParameter("pContents"));
		String pEmoticon	= StringUtil.nvl(request.getParameter("pEmoticon"));
		
		log.debug("+++++++++++++++++++++++++++++++++   chk Point2");
		String regName = UserBroker.getUserName(request);
		if(regName.equals(""))
			regName = StringUtil.nvl(request.getParameter("pRegName"));
		String regPasswd = StringUtil.nvl(request.getParameter("pRegPasswd"));
		String regEmail = StringUtil.nvl(request.getParameter("pRegEmail"));
		commbbsCommentDto.setSystemCode(systemCode);
		commbbsCommentDto.setCommId(pCommId);
		commbbsCommentDto.setBbsId(pBbsId);
		commbbsCommentDto.setSeqNo(pSeqNo);
		commbbsCommentDto.setCommNo(pCommNo);
		commbbsCommentDto.setContents(pContents);
		commbbsCommentDto.setEmoticonNum(pEmoticon);
		commbbsCommentDto.setRegId(regId);
		commbbsCommentDto.setRegName(regName);
		commbbsCommentDto.setRegEmail(regEmail);
		commbbsCommentDto.setRegPasswd(regPasswd);
		String msg = "";
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals("")) pMode = COMMUNITY;
		
		String returnUrl="";
		
			returnUrl = "/Community.cmd?cmd=commSubBoardShow&pMode="+pMode+"&pBbstype="+pBbstype+"&pCommId="+pCommId+"&pBbsId="+pBbsId+"&pSeqNo="+pSeqNo+"&curPage="+curPage;
		
		log.debug("+++++++++++++++++++++++++++++++++   chk Point 3");
		retVal = commbbsCommentDao.addBbsComment(commbbsCommentDto);
		log.debug("=============================================================bbsCommentRegist end");
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
	public ActionForward commSubCommentDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("===========================commSubCommentDelete start");
		String systemCode = UserBroker.getSystemCode(request);
		String curPage = StringUtil.nvl(request.getParameter("curPage"));
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String commName = commMemDto.commName;
		String commId	= commMemDto.commId;
		String userLevel = commMemDto.userLevel;
		
		int pCommId = Integer.parseInt(request.getParameter("pCommId"));		
		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));
		int pCommNo = Integer.parseInt(request.getParameter("pCommNo"));
		String pBbstype = StringUtil.nvl(request.getParameter("pBbstype"));

		//BbsCommentDAO bbsCommentDao = new BbsCommentDAO();
		CommBbsCommentDAO commbbsCommentDao = new CommBbsCommentDAO();
		
		String msg = "";
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals("")) pMode = COMMUNITY;
		
		String returnUrl="";

		returnUrl = "/Community.cmd?cmd=commSubBoardShow&pMode="+pMode+"&pBbstype="+pBbstype+"&pCommId="+pCommId+"&pBbsId="+pBbsId+"&pSeqNo="+pSeqNo+"&curPage="+curPage;
		
		int retVal = 0;
		retVal = commbbsCommentDao.delBbsComment(systemCode,pCommId,pBbsId,pSeqNo,pCommNo);		
		log.debug("+++++++++++++++++++ retVal = "+retVal);
		log.debug("===========================bbsCommentDelete end");
		 return redirect(returnUrl);
	}	
}

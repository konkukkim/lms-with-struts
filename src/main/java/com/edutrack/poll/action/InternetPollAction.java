/*
 * Created on 2004. 9. 20.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.poll.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.DateSetter;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.poll.dao.InternetPollDAO;
import com.edutrack.poll.dto.InternetPollCommentDTO;
import com.edutrack.poll.dto.InternetPollDTO;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InternetPollAction extends StrutsDispatchAction{

	/**
	 * 인터넷 투표 리스트를 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward pollList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		 String systemCode = UserBroker.getSystemCode(request);
		 String pMode = StringUtil.nvl(request.getParameter("pMode"),HOME);

		 InternetPollDAO pollDao = new InternetPollDAO();

		 model.put("pMode", pMode);
		 model.put("pollList", pollDao.getPollList(systemCode));

		 if(pMode.equals(MYPAGE)){
		 	new SiteNavigation(MYPAGE).add(request,"온라인투표관리").link(model);
		 }else{
		 	new SiteNavigation(HOME).add(request,"온라인투표관리").link(model);
		 }

         return forward(request, model, "/poll/pollList.jsp");
	}

	/**
	 * 인터넷 투표 생성/수정 폼을 띄워준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward pollWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String pRegMode		= StringUtil.nvl(request.getParameter("pRegMode"));
		String pMode 		= StringUtil.nvl(request.getParameter("pMode"),HOME);

		new SiteNavigation(MYPAGE).add(request,"인터넷투표").link(model);
		model.put("pRegMode", pRegMode);
		model.put("pMode", pMode);

		if(pRegMode.equals("E")){
			String systemCode 	= UserBroker.getSystemCode(request);
			String	pPollId = StringUtil.nvl(request.getParameter("pPollId"));
			String  userId = UserBroker.getUserId(request);

			InternetPollDAO	pollDao	= new InternetPollDAO();
			InternetPollDTO	pollDto	= new InternetPollDTO();

			pollDto = pollDao.getPollShow(systemCode, Integer.parseInt(pPollId),userId);

			model.put("pPollId", pPollId);
			model.put("pollShow",pollDto);
			DateSetter ds = new DateSetter((String)pollDto.getStartDate(), (String)pollDto.getEndDate() ).link(model);
			model.put("ds", ds);
		}else{
				DateSetter ds = new DateSetter(DateTimeUtil.getDate(), DateTimeUtil.getDate()).link(model);
				model.put("ds", ds);
		}

		return forward(request, model, "/poll/pollWrite.jsp");
	}

	/**
	 * 인터넷 투표를 등록/수정 한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward pollRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode 	= UserBroker.getSystemCode(request);
		String regMode		= StringUtil.nvl(request.getParameter("pRegMode"));
		String userId		= UserBroker.getUserId(request);
		String example[]	= request.getParameterValues("pExample");

		InternetPollDAO	pollDao	= new InternetPollDAO();
		InternetPollDTO	pollDto	= new InternetPollDTO();

		String pPollId = "";
		if(regMode.equals("W"))// 입력모드
		{
			pPollId = Integer.toString(pollDao.getMaxPollId(systemCode));
		}else{
			pPollId = StringUtil.nvl(request.getParameter("pPollId"));
		}

		String pYear1 = StringUtil.nvl(request.getParameter("pYear1"));
		String pMonth1 = StringUtil.nvl(request.getParameter("pMonth1"));
		String pDay1 = StringUtil.nvl(request.getParameter("pDay1"));
		String pDate1 = StringUtil.nvl(request.getParameter("pDate1"));
		String	pStartDate =pYear1+pMonth1+pDay1+"000000";
		if(pYear1.equals("")) pStartDate="";
		String pYear2 = StringUtil.nvl(request.getParameter("pYear2"));
		String pMonth2 = StringUtil.nvl(request.getParameter("pMonth2"));
		String pDay2 = StringUtil.nvl(request.getParameter("pDay2"));
		String pDate2 = StringUtil.nvl(request.getParameter("pDate2"));
		String	pEndDate =pYear2+pMonth2+pDay2+"235959";
		if(pYear2.equals("")) pEndDate="";
		log.debug("+++ startDate =   "+pStartDate+"   endDate = "+pEndDate);
		pollDto.setSystemCode(systemCode);
		pollDto.setPollId(Integer.parseInt(pPollId));

		pollDto.setStartDate(pStartDate);
		pollDto.setEndDate(pEndDate);
		pollDto.setStatus(StringUtil.nvl(request.getParameter("pStatus")));
		pollDto.setSubject(StringUtil.nvl(request.getParameter("pSubject")));
		pollDto.setContents(StringUtil.nvl(request.getParameter("pContents")));
		pollDto.setExample(example);
		pollDto.setRegId(userId);

		String msg = "Error!!인터넷 투표를 등록하는데 에러가 발생하였습니다.";
		String returnUrl = "/Poll.cmd?cmd=pollWrite&pRegMode=W&pMode=MyPage";

		int retVal = 0;

		if(regMode.equals("W"))// 입력모드
		{
			retVal = pollDao.addPoll(pollDto);

			if(retVal > 0){
				msg = "인터넷투표를 성공적으로 등록하였습니다.";
				returnUrl = "/Poll.cmd?cmd=pollList&pMode=MyPage";
			}
		}else{
			retVal = pollDao.editPoll(pollDto);

			if(retVal > 0){
				returnUrl = "/Poll.cmd?cmd=pollList&pMode=MyPage";
				msg = "인터넷투표를 성공적으로 수정하였습니다.";
			}else{
				returnUrl = "/Poll.cmd?cmd=pollWrite&pRegMode=E&pMode=MyPage";
				msg = "Error!!인터넷 투표를 수정하는데 에러가 발생하였습니다.";

			}
		}

		new SiteNavigation(MYPAGE).add(request,"온라인투표관리").link(model);
        return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}

	/**
	 * 인터넷 투표를 삭제한다.
	 * 관련 한줄답변, 참여 정보 또한 삭제 함
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward pollDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		int pollId = Integer.parseInt(request.getParameter("pPollId"));

		InternetPollDAO	pollDao	= new InternetPollDAO();

		int retVal = 0;
//		 Transaction Start........
 		Connection conn = null;
 		DBConnectionManager pool = null;
 		try{
 			pool = DBConnectionManager.getInstance();
			conn = pool.getConnection();
			conn.setAutoCommit( false );
			retVal = pollDao.delPollComment(systemCode,pollId,conn);
			retVal = pollDao.delPollJoin(systemCode,pollId,conn);
 			retVal = pollDao.delPoll(systemCode,pollId,conn);
 		}catch(Exception e){
		    e.printStackTrace();
		    log.debug("에러남............"+e );
            try {
				if(conn != null) conn.rollback();
			} catch(SQLException ignore) {
				log.error(ignore.getMessage(), ignore);
			}
		}finally{
		    conn.setAutoCommit( true );
			pool.freeConnection(conn); // 컨넥션 해제
		    if(conn != null) conn.close();
		}

		String msg = "Error!!인터넷 투표를  삭제하는데 에러가 발생하였습니다.";
		String returnUrl = "/Poll.cmd?cmd=pollWrite&pRegMode=E&pMode=MyPage&pPollId="+pollId;

		if(retVal > 0){
			msg = "인터넷 투표를  삭제하였습니다.";
			returnUrl = "/Poll.cmd?cmd=pollList&pMode=MyPage";
		}

		new SiteNavigation(MYPAGE).add(request,"온라인투표관리").link(model);
        return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}

	/**
	 * 인터넷 투표 상태를 변경한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward pollStatRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		int pollId 		= Integer.parseInt(request.getParameter("pPollId"));
		String status 	= StringUtil.nvl(request.getParameter("pStatus"));

		InternetPollDAO	pollDao	= new InternetPollDAO();

		int retVal = 0;
		retVal = pollDao.statEditPoll(systemCode,pollId, status);

		String msg = "Error!!인터넷 투표 상태를 변경하는데 에러가 발생하였습니다.";
		String returnUrl = "/Poll.cmd?cmd=pollStatEdit&pPollId="+pollId;

		if(retVal > 0){
			msg = "인터넷 투표 상태를  변경하였습니다.";
			returnUrl = "/Poll.cmd?cmd=pollList";
		}
		return notifyCloseReload(systemCode, model, msg, "N");
	}


	/**
	 * 사용자 투표 결과를 저장한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward pollUserRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String  userId = UserBroker.getUserId(request);
		int pollId 		= Integer.parseInt(request.getParameter("pPollId"));
		int hitNo 		= Integer.parseInt(request.getParameter("pNO"));
		String status = StringUtil.nvl(request.getParameter("pStatus"));
		InternetPollDAO	pollDao	= new InternetPollDAO();
		InternetPollDTO	pollDto	= new InternetPollDTO();
		String	pGubun	=	StringUtil.nvl(request.getParameter("pGubun"));

		pollDto = pollDao.getPollShow(systemCode, pollId,userId);


		int retVal = 0;
		retVal = pollDao.userAddPoll(systemCode,pollId,userId, hitNo);

		String msg = "투표에 실패하였습니다";
		String returnUrl = "/Main.cmd?cmd=mainShow&pMode=Home";

		if(retVal > 0){

			msg = "투표하였습니다 ";
			returnUrl = "/Poll.cmd?cmd=pollUserResultShow&pPollId="+pollId+"&pGubun="+pGubun;
		}
		if(status.equals("N")){
			msg = "비공개 투표 이므로 투표 종료 후 결과를 확인 하실 수 있습니다.";
			returnUrl = "/Main.cmd?cmd=mainShow&pMode=Home";
		}
		//return redirect(returnUrl);

		return alertAndExit(systemCode, model, msg, returnUrl, "N");
	}

	/**
	 * 인터넷 투표 결과를 보여준다. (사용자)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward pollUserResultShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String	systemCode	=	UserBroker.getSystemCode(request);
		String  userId		=	UserBroker.getUserId(request);
	    String	pMode		=	StringUtil.nvl(request.getParameter("pMode"),HOME);
		int		pollId		=	Integer.parseInt(request.getParameter("pPollId"));
		String	pGubun		=	StringUtil.nvl(request.getParameter("pGubun"));
		String	returnUrl	=	"";
		if(pGubun.equals("MAIN")) {
			returnUrl	=	"/poll/pollPopupUserResultShow.jsp";
		} else {
			returnUrl	=	"/poll/pollUserResultShow.jsp";
		}

		InternetPollDAO	pollDao	= new InternetPollDAO();
		InternetPollDTO	pollDto	= new InternetPollDTO();
		InternetPollCommentDTO	CommentDto	= new InternetPollCommentDTO();

		new SiteNavigation(pMode).add(request,"온라인투표관리").link(model);

		model.put("pPollId", Integer.toString(pollId));
		model.put("pollShow",pollDao.getPollShow(systemCode, pollId,userId));

		model.put("commentList", pollDao.getPollCommentList(systemCode, pollId));
		model.put("pMode", pMode);

		return forward(request, model, returnUrl);
	}

	/**
	 * 사용자 한줄답변을 저장한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward pollCommentRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		int pollId 		= Integer.parseInt(request.getParameter("pPollId"));
		//String regId 	= StringUtil.nvl(request.getParameter("pRegId"));
		String	regId	=	UserBroker.getUserId(request);
		String regName	= StringUtil.nvl(request.getParameter("pRegName"));
		String regPasswd= StringUtil.nvl(request.getParameter("pRegPasswd"));
		String regEmail	= StringUtil.nvl(request.getParameter("pRegEmail"));
		String emoticon	= StringUtil.nvl(request.getParameter("pEmoticon"));
		String contents	= StringUtil.nvl(request.getParameter("pContents"));
		String pMode 		= StringUtil.nvl(request.getParameter("pMode"),HOME);
		String pGubun	=	StringUtil.nvl(request.getParameter("pGubun"));

		InternetPollDAO	pollDao	= new InternetPollDAO();
		InternetPollCommentDTO	CommentDto	= new InternetPollCommentDTO();

		CommentDto.setSystemCode(systemCode);
		CommentDto.setPollId(pollId);
		CommentDto.setRegId(regId);
		CommentDto.setRegName(regName);
		CommentDto.setRegPasswd(regPasswd);
		CommentDto.setRegEmail(regEmail);
		CommentDto.setEmoticonNum(emoticon);
		CommentDto.setContents(contents);

		int retVal = 0;
		retVal = pollDao.addPollComment(CommentDto);
		String url ="/Poll.cmd?cmd=pollUserResultShow&pMode="+pMode+"&pPollId="+pollId+"&pGubun="+pGubun;
		return redirect(url);
	}

	/**
	 * 한줄답변 삭제폼을 띄워준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward pollPasswdForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode 	= UserBroker.getSystemCode(request);
		int pollId = Integer.parseInt(request.getParameter("pPollId"));
		int seqNo  = Integer.parseInt(request.getParameter("pSeqNo"));
		String d_mode = StringUtil.nvl(request.getParameter("dMode"));
		String pMode 		= StringUtil.nvl(request.getParameter("pMode"),HOME);

		InternetPollDAO	pollDao	= new InternetPollDAO();

		model.put("pPollId", Integer.toString(pollId));
		model.put("pSeqNo", Integer.toString(seqNo));
		model.put("pMode", pMode);

		return forward(request, model, "/poll/pollCommentDel.jsp");
	}

	/**
	 * 한줄답변을 삭제한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward pollCommentDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String	systemCode	=	UserBroker.getSystemCode(request);
		int		pollId		=	Integer.parseInt(request.getParameter("pPollId"));
		int		seqNo		=	Integer.parseInt(request.getParameter("pSeqNo"));
		String	pMode		=	StringUtil.nvl(request.getParameter("pMode"),HOME);
		String	pGubun		=	StringUtil.nvl(request.getParameter("pGubun"));
		String	dmode		=	"";
		dmode	=	StringUtil.nvl(request.getParameter("pDmode"));

		InternetPollDAO	pollDao	= new InternetPollDAO();
		InternetPollCommentDTO commentDto = new InternetPollCommentDTO();

		model.put("pPollId", Integer.toString(pollId));
		model.put("pSeqNo", Integer.toString(seqNo));
		model.put("pMode",pMode);
		commentDto = pollDao.getPollCommentShow(systemCode, pollId, seqNo);

		int retVal = 0;
		String msg = "Error!!한줄답변을  삭제하지 못하였습니다.";
		log.debug("dmode:"+dmode);

		//비로그인 삭제일 경우
		if(("U").equals(dmode)){
			String passwd = StringUtil.nvl(request.getParameter("pPasswd"));

			//비밀번호가 일치할경우
			if(commentDto.getRegPasswd().equals(passwd)){
				retVal = pollDao.delPollComment(systemCode, pollId, seqNo);
				if(retVal > 0) {
					msg = "한줄답변을 삭제하였습니다.";
				}
				return notifyCloseReload(systemCode, model, msg, "N");
			}else{
				return forward(request, model, "/poll/pollCommentDel.jsp");
			}
		}else{
			retVal = pollDao.delPollComment(systemCode, pollId, seqNo);
			if(retVal > 0) {
				msg = "한줄답변을 삭제하였습니다.";
			}
			String url ="/Poll.cmd?cmd=pollUserResultShow&pMode="+pMode+"&pPollId="+pollId+"&pGubun="+pGubun;
			return redirect(url);

		}
	}


	/**
	 * 팝업용 투표 보여주기(NDL 사용)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward pollPopupShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
			//String systemCode 	= UserBroker.getSystemCode(request);
			//String	pPollId = StringUtil.nvl(request.getParameter("pPollId"));
			String systemCode = "1";
			String pPollId = "0";
			String  userId = StringUtil.nvl(request.getParameter("userId"));

			InternetPollDAO	pollDao	= new InternetPollDAO();
			InternetPollDTO	pollDto	= new InternetPollDTO();

			pollDto = pollDao.getPollShow(systemCode, Integer.parseInt(pPollId),userId);
			model.put("userId",userId);
			model.put("pPollId", pPollId);
			model.put("pollShow",pollDto);
		return forward(request, model, "/poll/pollPopupShow.jsp");
	}

	/**
	 * 팝업용 투표 결과를 저장한다.(NDL 사용)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward pollPopupRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String systemCode = "1";
		String  userId = StringUtil.nvl(request.getParameter("userId"));
		int pollId 		= Integer.parseInt(request.getParameter("pPollId"));
		int hitNo 		= Integer.parseInt(request.getParameter("pNO"));
		String status = StringUtil.nvl(request.getParameter("pStatus"));
		InternetPollDAO	pollDao	= new InternetPollDAO();
		InternetPollDTO	pollDto	= new InternetPollDTO();

		pollDto = pollDao.getPollShow(systemCode, pollId,userId);


		int retVal = 0;
		retVal = pollDao.userAddPoll(systemCode,pollId,userId, hitNo);

		String msg = "투표에 실패하였습니다";
		String returnUrl = "/Poll.cmd?cmd=pollPopupShow&userId="+userId;

		if(retVal > 0){
			msg = "투표하였습니다 ";
			returnUrl = "/Poll.cmd?cmd=pollPopupResultShow&pPollId="+pollId;
		}
		if(status.equals("N")){
			msg = "비공개 투표 이므로 투표 종료 후 결과를 확인 하실 수 있습니다.";
			returnUrl = "/Poll.cmd?cmd=pollPopupShow&userId="+userId;
		}
		//return redirect(returnUrl);

		return alertAndExit(systemCode, model, msg, returnUrl, "N");
	}


	/**
	 * 팝업 인터넷 투표 결과를 보여준다. (NDL 사용)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward pollPopupResultShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = "1";
		String  userId = StringUtil.nvl(request.getParameter("userId"));
		int pollId = Integer.parseInt(request.getParameter("pPollId"));

		InternetPollDAO	pollDao	= new InternetPollDAO();
		InternetPollDTO	pollDto	= new InternetPollDTO();

		model.put("userId",userId);
		model.put("pPollId", Integer.toString(pollId));
		model.put("pollShow",pollDao.getPollShow(systemCode, pollId,userId));

		return forward(request, model, "/poll/pollPopupResultShow.jsp");
	}
}

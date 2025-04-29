package com.edutrack.bbs.action;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.bbs.dao.BbsCommentDAO;
import com.edutrack.bbs.dto.BbsCommentDTO;
import com.edutrack.common.UserBroker;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.StringUtil;

/**
 * @author bschoi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BbsCommentAction extends StrutsDispatchAction{
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
		return forward(request, model, "/bbs/bbsCommentPassChkForm.jsp");
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
		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));
		int pCommNo = Integer.parseInt(request.getParameter("pCommNo"));
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		String pPasswd = StringUtil.nvl(request.getParameter("pPasswd"));
		BbsCommentDAO bbsCommentDao = new BbsCommentDAO();
		RowSet infoRs = bbsCommentDao.getBbsComment(systemCode,pBbsId,pSeqNo,pCommNo);
		infoRs.next();
		String regPasswd = StringUtil.nvl(infoRs.getString("reg_passwd"));
		String msg = "";
		String returnUrl = "/BbsContents.cmd?cmd=bbsContentsShow&pMode="+pMode+"&pBbsId="+pBbsId+"&pSeqNo="+pSeqNo+"&curPage="+curPage;
		model.put("systemCode", systemCode);
		model.put("curPage", curPage);
		model.put("pBbsId", Integer.toString(pBbsId));
		model.put("pSeqNo", Integer.toString(pSeqNo));
		model.put("pCommNo", Integer.toString(pCommNo));
		model.put("pMode", pMode);
		model.put("passChk", "Y");
		int retVal = 0;
		if(pPasswd.equals(regPasswd)){
			//-- 비밀 번호 일치할 경우
			retVal = bbsCommentDao.delBbsComment(systemCode,pBbsId,pSeqNo,pCommNo);
			log.debug("+++++++++++++++++++ retVal = "+retVal);
			if(retVal > 0)
				msg = "삭제하였습니다.";
			else
				msg = "삭제 실패 하였습니다.";
			return notifyCloseForward(systemCode, model, msg,"",pMode, returnUrl);
		}else{
			return forward(request, model, "/bbs/bbsCommentPassChkForm.jsp");
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
	public ActionForward old_bbsCommentDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("===========================bbsCommentDelete start");
		String systemCode = UserBroker.getSystemCode(request);
		String curPage = StringUtil.nvl(request.getParameter("curPage"));
		int pBbsId = Integer.parseInt(request.getParameter("pBbsId"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));
		int pCommNo = Integer.parseInt(request.getParameter("pCommNo"));
		String pSec = StringUtil.nvl(request.getParameter("pSec"));

		BbsCommentDAO bbsCommentDao = new BbsCommentDAO();

		String msg = "";
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals("")) pMode = MYPAGE;

		String returnUrl = "/BbsContents.cmd?cmd=bbsContentsShow&pMode="+pMode+"&pBbsId="+pBbsId+"&pSec="+pSec+"&pSeqNo="+pSeqNo+"&curPage="+curPage;

		int retVal = 0;
		retVal = bbsCommentDao.delBbsComment(systemCode,pBbsId,pSeqNo,pCommNo);
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
	public ActionForward old_bbsCommentRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=============================================================bbsCommentRegist start");
		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);
		String curPage = StringUtil.nvl(request.getParameter("curPage"));
		int pBbsId = StringUtil.nvl(request.getParameter("pBbsId"),0);
		int pSeqNo = StringUtil.nvl(request.getParameter("pSeqNo"),1);
		String pSec = StringUtil.nvl(request.getParameter("pSec"));

		log.debug("+++++++++++++++++++++++++++++++++   chk Point0");
		BbsCommentDAO bbsCommentDao = new BbsCommentDAO();
		BbsCommentDTO bbsCommentDto = new BbsCommentDTO();
		log.debug("+++++++++++++++++++++++++++++++++   chk Point1");
		int retVal = 0;
		int pCommNo = bbsCommentDao.getMaxCommNo(systemCode,pBbsId,pSeqNo);

		String pContents = StringUtil.nvl(request.getParameter("pContents"));
		String pEmoticon	= StringUtil.nvl(request.getParameter("pEmoticon"));

		log.debug("+++++++++++++++++++++++++++++++++   chk Point2");
		String regName = UserBroker.getUserName(request);
		if(regName.equals(""))
			regName = StringUtil.nvl(request.getParameter("pRegName"));

		String regPasswd = StringUtil.nvl(request.getParameter("pRegPasswd"));
		String regEmail = StringUtil.nvl(request.getParameter("pRegEmail"));
		bbsCommentDto.setSystemCode(systemCode);
		bbsCommentDto.setBbsId(pBbsId);
		bbsCommentDto.setSeqNo(pSeqNo);
		bbsCommentDto.setCommNo(pCommNo);
		bbsCommentDto.setContents(pContents);
		bbsCommentDto.setEmoticonNum(pEmoticon);
		bbsCommentDto.setRegId(regId);
		bbsCommentDto.setRegName(regName);
		bbsCommentDto.setRegEmail(regEmail);
		bbsCommentDto.setRegPasswd(regPasswd);
		String msg = "";
		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals("")) pMode = MYPAGE;

		String returnUrl = "/BbsContents.cmd?cmd=bbsContentsShow&pMode="+pMode+"&pBbsId="+pBbsId+"&pSec="+pSec+"&pSeqNo="+pSeqNo+"&curPage="+curPage;
		log.debug("+++++++++++++++++++++++++++++++++   chk Point 3");

		retVal = bbsCommentDao.addBbsComment(bbsCommentDto);
		log.debug("=============================================================bbsCommentRegist end");

		return redirect(returnUrl);
	}

	/**
	 * [2007.8.17] AJAX 적용. 코멘트 등록
	 *
	 * @return
	 * @throws Exception
	 */
	public String bbsCommentRegist(int bbsId, int seqNo, String regName, String regPass, String contents, String emoticon, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		BbsCommentDAO bbsCommentDao = new BbsCommentDAO();
		BbsCommentDTO bbsCommentDto = new BbsCommentDTO();
		int retVal = 0;
		int commNo = 0;
		contents = AjaxUtil.ajaxDecoding(contents);
		emoticon = StringUtil.nvl(emoticon);
		if(emoticon.equals("") || emoticon.equals("0")) {
			emoticon	=	"1";
		}

		regName = AjaxUtil.ajaxDecoding(regName);

		if(regName.equals(""))
			regName = UserBroker.getUserName(request);
		int pCommNo = bbsCommentDao.getMaxCommNo(systemCode, bbsId, seqNo);

		bbsCommentDto.setSystemCode(systemCode);
		bbsCommentDto.setBbsId(bbsId);
		bbsCommentDto.setSeqNo(seqNo);
		bbsCommentDto.setCommNo(pCommNo);
		bbsCommentDto.setContents(contents);
		bbsCommentDto.setEmoticonNum(emoticon);
		bbsCommentDto.setRegId(userId);
		bbsCommentDto.setRegName(regName);
		bbsCommentDto.setRegPasswd(regPass);

		retVal	=	bbsCommentDao.addBbsComment(bbsCommentDto);
		return String.valueOf(retVal);

	}


	/**
	 * [2007.8.17] AJAX 적용. 코멘트 리스트
	 *
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO bbsCommentAutoList(int curPageComment, int bbsId, int seqNo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);

		//	-- 페이징
		curPageComment = (curPageComment == 0) ? 1 : curPageComment;

		//	 데이타를 담는다.
		ListDTO listObj = null;
		BbsCommentDAO bbsCommentDao = new BbsCommentDAO();
		listObj	=	bbsCommentDao.getBbsCommentPagingList(curPageComment, systemCode, bbsId, seqNo, 5, 10);

		// 리턴 데이타 세팅
		AjaxListDTO ajaxListDto = new AjaxListDTO();
		ArrayList dataList = new ArrayList();
		BbsCommentDTO bbsCommentDto = null;
		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				bbsCommentDto	=	new BbsCommentDTO();
				bbsCommentDto.setCommNo(rs.getInt("comm_no"));
				bbsCommentDto.setContents(StringUtil.nvl(rs.getString("comment_contents")));
				bbsCommentDto.setEmoticonNum(StringUtil.nvl(rs.getString("emoticon_num")));
				bbsCommentDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				bbsCommentDto.setRegName(StringUtil.nvl(rs.getString("reg_name")));
				bbsCommentDto.setRegPasswd(StringUtil.nvl(rs.getString("reg_passwd")));
				bbsCommentDto.setRegDate(DateTimeUtil.getDateType(1, StringUtil.nvl(rs.getString("reg_date"))));

				dataList.add(bbsCommentDto);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}
		return ajaxListDto;
	}

	/**
	 * [AJAX] 전체 코멘트 개수를 가져온다.
	 * @param bbsId
	 * @param seqNo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public int getCommentCount(int bbsId, int seqNo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		int	retVal	=	0;

		BbsCommentDAO bbsCommentDao = new BbsCommentDAO();
		retVal	=	bbsCommentDao.getCommentCount(systemCode, bbsId, seqNo);

		return retVal;
	}

	/**
	 * [2007.8.17] AJAX 적용. 코멘트 글 삭제하기.
	 *
	 * @return
	 * @throws Exception
	 */
	public String bbsCommentDelete(int bbsId, int seqNo, int commNo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String	systemCode	=	UserBroker.getSystemCode(request);
		String	userId		=	UserBroker.getUserId(request);
		int			retVal		=	0;

		BbsCommentDAO bbsCommentDao = new BbsCommentDAO();
		retVal	=	bbsCommentDao.delBbsComment(systemCode, bbsId, seqNo, commNo);

		return String.valueOf(retVal);
	}

}

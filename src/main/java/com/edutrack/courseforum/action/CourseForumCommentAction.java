/*
 * Created on 2004. 10. 15.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseforum.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.courseforum.dao.CourseForumCommentDAO;
import com.edutrack.courseforum.dto.CourseForumCommentDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/**
 * @author suna
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CourseForumCommentAction  extends StrutsDispatchAction{
	// 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	
	/**
	 * 댓글의 개수를 가져온다. [AJAX]
	 * @param pCurriCode
	 * @param pCurriYear
	 * @param pCurriTerm
	 * @param pCourseId
	 * @param pForumId
	 * @param seqNo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getForumCommentCount(String pCurriCode, int pCurriYear, int pCurriTerm, String pCourseId, int pForumId, int seqNo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String 	systemCode 	= 	UserBroker.getSystemCode(request);
		int		retVal		=	0;
		
		CourseForumCommentDAO	commentDao	=	new CourseForumCommentDAO();
		retVal	=	commentDao.getCourseForumCommentCount(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, seqNo);
		
		return String.valueOf(retVal);
	}
	
	/**
	 * 댓글을 목록을 가져온다 (Ajax)
	 * 2007.04.07 
	 * @param curPageComment
	 * @param bbsType
	 * @param seqNo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO courseForumCommentPagingListAjax(int curPageComment, String pCourseId, int pForumId, int seqNo, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;	    
        String curriCode = StringUtil.nvl(curriMemDto.curriCode);
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);  
		
		// 페이징
		curPageComment = (curPageComment == 0) ? 1 : curPageComment;
	
		// 데이타를 담는다.
		ListDTO listObj = null;
		CourseForumCommentDAO courseForumCommentDao = new CourseForumCommentDAO();
		listObj = courseForumCommentDao.getCourseForumCommentPagingList(curPageComment, systemCode, curriCode, curriYear, curriTerm, pCourseId, pForumId, seqNo);
		
		// 리턴 데이타 세팅
		AjaxListDTO ajaxListDto = new AjaxListDTO();
		ArrayList dataList = new ArrayList();
		CourseForumCommentDTO courseForumCommentDto = null;
	
		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){	
				courseForumCommentDto = new CourseForumCommentDTO();
				courseForumCommentDto.setCommNo(rs.getInt("comm_no"));
				courseForumCommentDto.setContents(StringUtil.nvl(rs.getString("contents")));
				courseForumCommentDto.setEmoticonNum(StringUtil.nvl(rs.getString("emoticon_num"),0));
				courseForumCommentDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				courseForumCommentDto.setRegName(StringUtil.nvl(rs.getString("reg_name")));
				courseForumCommentDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				dataList.add(courseForumCommentDto);
			}
			rs.close();
	
			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}
			
		return ajaxListDto;
	}
	
	
	/**
	 * 공개강좌용 토론방 댓글 리스트 [AJAX]
	 * 
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO publishForumCommentPagingListAjax(int curPageComment, String pCurriCode, int pCurriYear, int pCurriTerm, String pCourseId, int pForumId, int seqNo, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);		
		// 페이징
		curPageComment = (curPageComment == 0) ? 1 : curPageComment;
	
		// 데이타를 담는다.
		ListDTO listObj = null;
		CourseForumCommentDAO courseForumCommentDao = new CourseForumCommentDAO();
		listObj = courseForumCommentDao.getPublishForumCommentPagingList(curPageComment, systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, seqNo);
		
		// 리턴 데이타 세팅
		AjaxListDTO ajaxListDto = new AjaxListDTO();
		ArrayList dataList = new ArrayList();
		CourseForumCommentDTO courseForumCommentDto = null;
	
		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){	
				courseForumCommentDto = new CourseForumCommentDTO();
				courseForumCommentDto.setCommNo(rs.getInt("comm_no"));
				courseForumCommentDto.setContents(StringUtil.nvl(rs.getString("contents")));
				courseForumCommentDto.setEmoticonNum(StringUtil.nvl(rs.getString("emoticon_num"),0));
				courseForumCommentDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				courseForumCommentDto.setRegName(StringUtil.nvl(rs.getString("reg_name")));
				courseForumCommentDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				dataList.add(courseForumCommentDto);
			}
			rs.close();
	
			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}
			
		return ajaxListDto;
	}

	
	/**
	 * 댓글을 등록한다(Ajax)
	 * @param pCourseId
	 * @param pForumId
	 * @param pSeqNo
	 * @param pContents
	 * @param pEmoticon
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String courseForumCommentRegist(String pCourseId, int pForumId, int pSeqNo, String pContents, int pEmoticon, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String	systemCode 	= 	UserBroker.getSystemCode(request);
   
    	UserMemDTO	userMemDto	=	UserBroker.getUserInfo(request);
	    CurriMemDTO	curriMemDto =	userMemDto.curriInfo;
	    String	pCurriCode 	= 	StringUtil.nvl(curriMemDto.curriCode);
		int		pCurriYear 	= 	StringUtil.nvl(curriMemDto.curriYear,0);
		int		pCurriTerm 	= 	StringUtil.nvl(curriMemDto.curriTerm,0);
		String 	regId 		= 	UserBroker.getUserId(request);

		CourseForumCommentDAO courseForumCommentDao = new CourseForumCommentDAO();
		CourseForumCommentDTO courseForumCommentDto = new CourseForumCommentDTO();
		int retVal = 0;
		int pCommNo = courseForumCommentDao.getMaxCommNo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pSeqNo );
		
		courseForumCommentDto.setSystemCode(systemCode);
		courseForumCommentDto.setCurriCode(pCurriCode);
		courseForumCommentDto.setCurriYear(pCurriYear);
		courseForumCommentDto.setCurriTerm(pCurriTerm);
		courseForumCommentDto.setCourseId(pCourseId);
		courseForumCommentDto.setForumId(pForumId);
		courseForumCommentDto.setSeqNo(pSeqNo);
		courseForumCommentDto.setCommNo(pCommNo);
		courseForumCommentDto.setContents(pContents);
		courseForumCommentDto.setEmoticonNum(pEmoticon);
		courseForumCommentDto.setRegId(regId);
		
		String msg = "";
		
		String returnUrl = "/CourseForumContents.cmd?cmd=courseForumContentsShow&pCourseId="+pCourseId+"&pForumId="+pForumId+"&pSeqNo="+pSeqNo; //+"&curPage="+curPage
		retVal = courseForumCommentDao.addCourseForumComment(courseForumCommentDto);
		return String.valueOf(retVal);
	}
	
	/**
	 * 공개과정용 토론 댓글 등록 [AJAX]
	 * 
	 * @return
	 * @throws Exception
	 */
	public String publishForumCommentRegist(String pCurriCode, int pCurriYear, int pCurriTerm, String pCourseId, int pForumId, int pSeqNo, String pContents, int pEmoticon, String pRegName, String pRegPasswd, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String	systemCode 	= 	UserBroker.getSystemCode(request);
   		String 	regId 		= 	UserBroker.getUserId(request);
   		pEmoticon = (pEmoticon == 0) ? 1 : pEmoticon;

		CourseForumCommentDAO courseForumCommentDao = new CourseForumCommentDAO();
		CourseForumCommentDTO courseForumCommentDto = new CourseForumCommentDTO();
		int retVal = 0;
		int pCommNo = courseForumCommentDao.getMaxCommNo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pSeqNo );
		
		courseForumCommentDto.setSystemCode(systemCode);
		courseForumCommentDto.setCurriCode(pCurriCode);
		courseForumCommentDto.setCurriYear(pCurriYear);
		courseForumCommentDto.setCurriTerm(pCurriTerm);
		courseForumCommentDto.setCourseId(pCourseId);
		courseForumCommentDto.setForumId(pForumId);
		courseForumCommentDto.setSeqNo(pSeqNo);
		courseForumCommentDto.setCommNo(pCommNo);
		courseForumCommentDto.setContents(pContents);
		courseForumCommentDto.setEmoticonNum(pEmoticon);
		courseForumCommentDto.setRegId(regId);
		courseForumCommentDto.setRegName(pRegName);
		courseForumCommentDto.setRegPasswd(pRegPasswd);
		String msg = "";
		
		String returnUrl = "/CourseForumContents.cmd?cmd=courseForumContentsShow&pCourseId="+pCourseId+"&pForumId="+pForumId+"&pSeqNo="+pSeqNo; //+"&curPage="+curPage
		retVal = courseForumCommentDao.addCourseForumComment(courseForumCommentDto);
		return String.valueOf(retVal);
	}
	
	
	/**
	 * 댓글을 삭제한다 (Ajax)
	 * @param pCourseId
	 * @param pForumId
	 * @param pSeqNo
	 * @param pCommNo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String courseForumCommentDelete(String pCourseId, int pForumId, int pSeqNo, int pCommNo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);		
		
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;	    
        String curriCode = StringUtil.nvl(curriMemDto.curriCode);
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0); 

		int retVal = 0;
		CourseForumCommentDAO courseForumCommentDao = new CourseForumCommentDAO();
		retVal = courseForumCommentDao.delCourseForumComment(systemCode, curriCode, curriYear, curriTerm, pCourseId, pForumId, pSeqNo, pCommNo);	

		return String.valueOf(retVal);
	}
	
	/**
	 * 공개강좌 토론방 댓글을 삭제한다. [AJAX] 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String publishForumCommentDelete(String pCurriCode, int pCurriYear, int pCurriTerm, String pCourseId, int pForumId, int pSeqNo, int pCommNo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		
		int retVal = 0;
		CourseForumCommentDAO courseForumCommentDao = new CourseForumCommentDAO();
		retVal = courseForumCommentDao.delCourseForumComment(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pSeqNo, pCommNo);	

		return String.valueOf(retVal);
	}
	
	
}
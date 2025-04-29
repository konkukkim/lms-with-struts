/*
 * Created on 2004. 10. 15.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseforum.action;


import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.VBN.VBN_files;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.courseforum.dao.CourseForumUserDAO;
import com.edutrack.courseforum.dao.CourseForumContentsDAO;
import com.edutrack.courseforum.dao.CourseForumCommentDAO;
import com.edutrack.courseforum.dto.CourseForumCommentDTO;
import com.edutrack.courseforum.dto.CourseForumContentsDTO;
import com.edutrack.courseforum.dao.CourseForumInfoDAO;
import com.edutrack.courseforum.dto.CourseForumInfoDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;

import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author suna
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CourseForumContentsAction  extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	/**
	 * 음성게시글 작성 폼을 띄워준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumContentsVoiceWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    if(userType.equals("")) userType="G";

	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pForumId = StringUtil.nvl(request.getParameter("pForumId"),0);
		int pSeqNo = StringUtil.nvl(request.getParameter("pSeqNo"),0);
		model.put("pCourseId", pCourseId);
		model.put("pForumId", String.valueOf(pForumId));
		model.put("pSeqNo", String.valueOf(pSeqNo));

		String pRegMode = "Add";
		if(pSeqNo > 0 ){
			CourseForumContentsDAO courseForumContentsDao = new CourseForumContentsDAO();
			CourseForumContentsDTO ContentsDto = new CourseForumContentsDTO();
			ContentsDto = courseForumContentsDao.getCourseForumContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pSeqNo);

			model.put("pBbsNo", String.valueOf(ContentsDto.getBbsNo()));
			model.put("pDepthNo", String.valueOf(ContentsDto.getDepthNo()));
			model.put("pLevelNo", String.valueOf(ContentsDto.getLevelNo()));
			model.put("pParentNo", String.valueOf(pSeqNo));
			pRegMode = "Reply";

		}
		model.put("pRegMode", pRegMode);
		CourseForumInfoDAO courseForumInfoDao = new CourseForumInfoDAO();
		model.put("forumInfo", courseForumInfoDao.getCourseForumInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId));


		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
		return forward(request, model, "/course_forum/courseForumContentsVoiceWrite.jsp");
	}

		
	/**
	 * 게시글 작성 폼을 띄워준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumContentsWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
	    String	systemCode 	= 	UserBroker.getSystemCode(request);
	    String	pMode		=	StringUtil.nvl(request.getParameter("pMode"));
	    
	    String	pCurriCode 	= 	"";
		int		pCurriYear 	= 	0;
		int 	pCurriTerm 	= 	0;
		String	returnPage	=	"";
	    
	    // 메인의 공개강좌에서 접근했을 때
		if(pMode.equals("Home")) {
	    	pCurriCode 	= 	StringUtil.nvl(request.getParameter("pCurriCode"));
	    	pCurriYear	=	StringUtil.nvl(request.getParameter("pCurriYear"), 0);
	    	pCurriTerm	=	StringUtil.nvl(request.getParameter("pCurriTerm"), 0);
			String	pGubun	=	StringUtil.nvl(request.getParameter("pGubun"));
			returnPage	=	"/publish_curri_sub/publishForumContentsWrite.jsp";
			
			//-- 도배방지 랜덤 숫자/알파벳 발행
			Random		random		=	new Random();
			String[]	randomStr	=	new String[5];
			int			randomInt	=	0;		
			for(int i=0; i< 3; i++) {
				randomInt	=	random.nextInt(9);
				randomStr[i] =	String.valueOf(randomInt);
			}
		    for (int i = 1; i <= 2; i++) {
		        char ch = (char)((Math.random() * 26) + 65);
		        randomStr[i+2]	=	String.valueOf(ch);
		    }
			model.put("RandomStr", randomStr);
			//-- 도배방지 랜덤 숫자/알파벳 발행 끝
			
			model.put("pCurriCode", pCurriCode);
			model.put("pCurriYear", pCurriYear);
			model.put("pCurriTerm", pCurriTerm);
			model.put("pGubun", pGubun);
	    } // 강의실의 토론방에서 접근
	    else {
	    	UserMemDTO	userMemDto	=	UserBroker.getUserInfo(request);
		    CurriMemDTO	curriMemDto =	userMemDto.curriInfo;
		    pCurriCode 	= 	StringUtil.nvl(curriMemDto.curriCode);
			pCurriYear 	= 	StringUtil.nvl(curriMemDto.curriYear,0);
			pCurriTerm 	= 	StringUtil.nvl(curriMemDto.curriTerm,0);
			returnPage	=	"/course_forum/courseForumContentsWrite.jsp";
	    }
		
		String 	pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int 	pForumId 	= 	StringUtil.nvl(request.getParameter("pForumId"), 0);
		int 	pSeqNo 		= 	StringUtil.nvl(request.getParameter("pSeqNo"), 0);
		String 	pRegMode 	= 	"Add";
		

		if(pSeqNo > 0 ){
			CourseForumContentsDAO courseForumContentsDao = new CourseForumContentsDAO();
			CourseForumContentsDTO ContentsDto = new CourseForumContentsDTO();
			ContentsDto = courseForumContentsDao.getCourseForumContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pSeqNo);

			model.put("pBbsNo", String.valueOf(ContentsDto.getBbsNo()));
			model.put("pDepthNo", String.valueOf(ContentsDto.getDepthNo()));
			model.put("pLevelNo", String.valueOf(ContentsDto.getLevelNo()));
			model.put("pParentNo", String.valueOf(pSeqNo));
			model.put("pSubject", StringUtil.nvl(ContentsDto.getSubject()));
			pRegMode = "Reply";
		}
		
		model.put("pCourseId", pCourseId);
		model.put("pForumId", String.valueOf(pForumId));
		model.put("pSeqNo", String.valueOf(pSeqNo));
		model.put("pRegMode", pRegMode);
		
		CourseForumInfoDAO courseForumInfoDao = new CourseForumInfoDAO();
		model.put("forumInfo", courseForumInfoDao.getCourseForumInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId));

		new SiteNavigation(pMode).add(request,"토론방").link(model);
		return forward(request, model, returnPage);
	}


	/**
	 * 게시글 수정 폼을 띄운다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumContentsEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String	systemCode 	= 	UserBroker.getSystemCode(request);
	    String	pMode		=	StringUtil.nvl(request.getParameter("pMode"));
	    
	    String	pCurriCode 	= 	"";
		int		pCurriYear 	= 	0;
		int 	pCurriTerm 	= 	0;
		String	returnPage	=	"";
	    
	    // 메인의 공개강좌에서 접근했을 때
		if(pMode.equals("Home")) {
	    	pCurriCode 	= 	StringUtil.nvl(request.getParameter("pCurriCode"));
	    	pCurriYear	=	StringUtil.nvl(request.getParameter("pCurriYear"), 0);
	    	pCurriTerm	=	StringUtil.nvl(request.getParameter("pCurriTerm"), 0);
			String	pGubun	=	StringUtil.nvl(request.getParameter("pGubun"));
			returnPage	=	"/publish_curri_sub/publishForumContentsEdit.jsp";
			
			model.put("pCurriCode", pCurriCode);
			model.put("pCurriYear", pCurriYear);
			model.put("pCurriTerm", pCurriTerm);
			model.put("pGubun", pGubun);
	    } // 강의실의 토론방에서 접근
	    else {
	    	UserMemDTO	userMemDto	=	UserBroker.getUserInfo(request);
		    CurriMemDTO	curriMemDto =	userMemDto.curriInfo;
		    pCurriCode 	= 	StringUtil.nvl(curriMemDto.curriCode);
			pCurriYear 	= 	StringUtil.nvl(curriMemDto.curriYear,0);
			pCurriTerm 	= 	StringUtil.nvl(curriMemDto.curriTerm,0);
			returnPage	=	"/course_forum/courseForumContentsEdit.jsp";
	    }

		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pForumId = Integer.parseInt(request.getParameter("pForumId"));
		int pSeqNo = Integer.parseInt(request.getParameter("pSeqNo"));

		model.put("pCourseId",pCourseId);
		model.put("pForumId",String.valueOf(pForumId));
		model.put("pSeqNo",String.valueOf(pSeqNo));

		CourseForumInfoDAO courseForumInfoDao = new CourseForumInfoDAO();
		model.put("forumInfo", courseForumInfoDao.getCourseForumInfo(systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId));

		CourseForumContentsDAO courseForumContentsDao = new CourseForumContentsDAO();
		model.put("contentsDto", courseForumContentsDao.getCourseForumContents(systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pSeqNo));

		new SiteNavigation(pMode).add(request,"토론방").link(model);
        return forward(request, model, returnPage);
	}


	/**
	 * 게시글 보여주기
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumContentsShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String	systemCode 	= 	UserBroker.getSystemCode(request);
		String	userType	=	UserBroker.getUserType(request);
	    String	pMode		=	StringUtil.nvl(request.getParameter("pMode"));
	    
	    String	pCurriCode 	= 	"";
		int		pCurriYear 	= 	0;
		int 	pCurriTerm 	= 	0;
		String	returnPage	=	"";
		String	userId		=	"";
	    
	    // 메인의 공개강좌에서 접근했을 때
		if(pMode.equals("Home")) {
	    	pCurriCode 	= 	StringUtil.nvl(request.getParameter("pCurriCode"));
	    	pCurriYear	=	StringUtil.nvl(request.getParameter("pCurriYear"), 0);
	    	pCurriTerm	=	StringUtil.nvl(request.getParameter("pCurriTerm"), 0);
			String	pGubun	=	StringUtil.nvl(request.getParameter("pGubun"));
			returnPage	=	"/publish_curri_sub/publishForumContentsShow.jsp";
			
			model.put("pCurriCode", pCurriCode);
			model.put("pCurriYear", pCurriYear);
			model.put("pCurriTerm", pCurriTerm);
			model.put("pGubun", pGubun);
	    } // 강의실의 토론방에서 접근
	    else {
	    	UserMemDTO	userMemDto	=	UserBroker.getUserInfo(request);
		    CurriMemDTO	curriMemDto =	userMemDto.curriInfo;
		    userId		=	StringUtil.nvl(userMemDto.userId);
		    pCurriCode 	= 	StringUtil.nvl(curriMemDto.curriCode);
			pCurriYear 	= 	StringUtil.nvl(curriMemDto.curriYear,0);
			pCurriTerm 	= 	StringUtil.nvl(curriMemDto.curriTerm,0);
			returnPage	=	"/course_forum/courseForumContentsShow.jsp";
	    }		
		
		String	pCourseId	=	StringUtil.nvl(request.getParameter("pCourseId"));
		int 	pForumId 	= 	StringUtil.nvl(request.getParameter("pForumId"),0);
		int 	pSeqNo 		= 	Integer.parseInt(request.getParameter("pSeqNo"));

		model.put("pCourseId",pCourseId);
		model.put("pForumId",String.valueOf(pForumId));
		model.put("pSeqNo",String.valueOf(pSeqNo));

		CourseForumInfoDAO courseForumInfoDao = new CourseForumInfoDAO();
		CourseForumInfoDTO	forumInfo	= new CourseForumInfoDTO();
		CourseForumUserDAO	forumUser	= new CourseForumUserDAO();
		forumInfo = courseForumInfoDao.getCourseForumInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId);
		model.put("forumInfo", forumInfo);

		//글쓸 권한 체
		String ckWrite = "Y";
		if(StringUtil.nvl(forumInfo.getForumType()).equals("T")){
			int SubTeamNo = forumUser.getSubForumId(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, forumInfo.getForumId(), userId);
			if(pForumId == SubTeamNo)
				ckWrite = "Y";
			else
				ckWrite = "N";
		}
		model.put("ckWrite", ckWrite);

		CourseForumContentsDAO courseForumContentsDao = new CourseForumContentsDAO();
		//-- 본문내용을 DTO에 담는다.
		CourseForumContentsDTO contentsDto = courseForumContentsDao.getCourseForumContents(systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pSeqNo);
		model.put("contentsDto",contentsDto);

		//-- 조회수 증가 시키기
		int retVal = 0;
		if(!userId.equals(StringUtil.nvl(contentsDto.getRegId()))) {
			retVal = courseForumContentsDao.hitUpCourseForumContents(systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pSeqNo);			
		}		

		if(StringUtil.nvl(forumInfo.getViewPrevNextYn()).equals("Y")){
			String pPrevSubject = "";
			String pNextSubject = "";
			int pPrevSeqNo = courseForumContentsDao.getCourseForumContentsPrevSeqNo(systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, contentsDto.getBbsNo());
			log.debug("----------- pPrevSeqNo"+pPrevSeqNo);
			if(pPrevSeqNo > 0)
				pPrevSubject = courseForumContentsDao.getCourseForumContentsSubject(systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pPrevSeqNo);
			int pNextSeqNo = courseForumContentsDao.getCourseForumContentsNextSeqNo(systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, contentsDto.getBbsNo());
			log.debug("----------- pNextSeqNo"+pNextSeqNo);
			if(pNextSeqNo > 0)
				pNextSubject = courseForumContentsDao.getCourseForumContentsSubject(systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pNextSeqNo);
			model.put("pPrevSeqNo",String.valueOf(pPrevSeqNo));
			model.put("pPrevSubject",pPrevSubject);
			model.put("pNextSeqNo",String.valueOf(pNextSeqNo));
			model.put("pNextSubject",pNextSubject);
		}
		String AddWhere = "";
		if(StringUtil.nvl(forumInfo.getViewThreadYn()).equals("Y")){
			AddWhere = " and bbs_no="+contentsDto.getBbsNo();
			String Order = "";
			int listCnt = 0;
			listCnt = courseForumContentsDao.getCourseForumContentsCount(systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, AddWhere);
			model.put("listCnt", String.valueOf(listCnt));
			if(listCnt>1){
				model.put("contentsList", courseForumContentsDao.getCourseForumContentsList(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId,  AddWhere, Order));
			}
		}
		//-- 삭제시 하위글 있는지 체크하기위해서 아래 글 갯수를 가져온다.
		int pChildCnt = 0;
		AddWhere = " and parent_no="+pSeqNo;
		pChildCnt = courseForumContentsDao.getCourseForumContentsCount(systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, AddWhere);
		model.put("pChildCnt", String.valueOf(pChildCnt));
		//-- 코멘트 사용시 코멘트 갯수 목록 가져오기
		CourseForumCommentDAO courseForumCommentDao = new CourseForumCommentDAO();
		if(StringUtil.nvl(forumInfo.getCommentUseYn()).equals("Y")){
			int pCommentCnt = 0;
			pCommentCnt = courseForumCommentDao.getCourseForumCommentCount(systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pSeqNo);
			model.put("pCommentCnt", String.valueOf(pCommentCnt));
			//if(pCommentCnt>0){
			//	model.put("commentList",courseForumCommentDao.getCourseForumCommentList(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId,pForumId, pSeqNo));
			//}
		}
		model.put("curPage", String.valueOf(StringUtil.nvl(request.getParameter("curPage"),1)));
		log.debug("----------- courseForumContentsShow end");
		new SiteNavigation(pMode).add(request,"토론방").link(model);
        return forward(request, model, returnPage);
	}

	
	/**
	 * 게시글 등록/수정 한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumContentsVoiceRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String	systemCode 	= 	StringUtil.nvl(request.getParameter("pSystemCode"));
		String	regId 		= 	StringUtil.nvl(request.getParameter("pUserId"));
		String	pCurriCode 	= 	StringUtil.nvl(request.getParameter("pCurriCode"));
		int	 	pCurriYear 	= 	StringUtil.nvl(request.getParameter("pCurriYear"), 0);
		int	 	pCurriTerm 	= 	StringUtil.nvl(request.getParameter("pCurriTerm"), 0);
		String	userName 	= 	StringUtil.nvl(request.getParameter("pUserName"));

		String 	pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int 	pSeqNo 		= 	StringUtil.nvl(request.getParameter("pSeqNo"),1);
		int 	pForumId 	= 	StringUtil.nvl(request.getParameter("pForumId"),0);

		if(systemCode.equals(""))
			systemCode 		= 	StringUtil.nvl(request.getParameter("pSystemCode"), "");
		if(regId.equals(""))
			regId 			= 	StringUtil.nvl(request.getParameter("pUserId"), "");

		String 	regMode 	= 	StringUtil.nvl(request.getParameter("pRegMode"));

		CourseForumInfoDAO courseForumInfoDao = new CourseForumInfoDAO();
		CourseForumInfoDTO courseForumInfoDto = new CourseForumInfoDTO();

		CourseForumContentsDAO courseForumContentsDao = new CourseForumContentsDAO();
		CourseForumContentsDTO courseForumContentsDto = new CourseForumContentsDTO();
		MultipartRequest multipartRequest = null;
		int 	retVal 			= 0;
		String 	originName 		= "";
		String 	rFileName		= "";
		String 	sFileName 		= "";
		String 	filePath 		= "";
		String 	fileSize 		= "";

		String	pOldRfileName 	= 	"";
		String	pOldSfileName 	= 	"";
		String	pOldFilePath 	= 	"";
		String	pOldFileSize 	= 	"";

		if(regMode.equals("Add") || regMode.equals("Reply") )// 입력모드
		{
			pSeqNo 	= 	courseForumContentsDao.getMaxSeqNo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId);
		}

		//파일 업로드
		String RegMonth = CommonUtil.getCurrentDate().substring(0,6);

		String FilePath = FileUtil.FILE_DIR+systemCode+"/course_forum/"+pCourseId +"_"+ pForumId +"/"+RegMonth+"/";

		UploadFiles uploadEntity = FileUtil.upload(request, FilePath, pSeqNo+"_"+regId);
		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();
		String status = uploadEntity.getStatus();


		if(regMode.equals("Edit")){//-- 수정모드시는 현재 월이 아닌 등록된 글의 년월을 가져온다.
			RegMonth = StringUtil.nvl(request.getParameter("pRegDate")).substring(0,6);
			pOldRfileName 	= StringUtil.nvl(multipartRequest.getParameter("pOldRfileName"));
			pOldSfileName 	= StringUtil.nvl(multipartRequest.getParameter("pOldSfileName"));
			pOldFilePath 	= StringUtil.nvl(multipartRequest.getParameter("pOldFilePath"));
			pOldFileSize 	= StringUtil.nvl(multipartRequest.getParameter("pOldFileSize"));
		}


		String pSubject = StringUtil.nvl(multipartRequest.getParameter("pSubject"));
		String pKeyword = StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
		String pContents = StringUtil.nvl(multipartRequest.getParameter("pContents"));

		if (status.equals("E")){
			log.debug("첨부 파일 올리려다 실패하였습니다.");
		}else if (status.equals("O")){
			log.debug("첨부하신 파일이 용량을 초과했습니다.");
		}else if (status.equals("I")){
			log.debug("첨부 파일의 정보가 잘못되었습니다.");
		}else if(status.equals("S")){
			// 업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			log.debug("첨부 파일을 첨부하는데 성공하였습니다.");
			/** 웹에디터에서 첨부파일을 첨부시 인식하는걸 막기위해 **/
			ArrayList files = uploadEntity.getFiles();
			UploadFile file = null;

			for(int i = 0 ; i < files.size(); i++){
				file = (UploadFile)files.get(i);
				originName = StringUtil.nvl(file.getRootName());
				if(!originName.equals("")) {
					rFileName = StringUtil.nvl(file.getRootName());
					sFileName = file.getUploadName();
					fileSize = String.valueOf(file.getSize());
					filePath = uploadEntity.getUploadPath();

				}
			}

			if(!originName.equals("")) {			//파일첨부를 했을경우
				courseForumContentsDto.setRfileName(rFileName);
				courseForumContentsDto.setSfileName(sFileName);
				courseForumContentsDto.setFileSize(fileSize);
				courseForumContentsDto.setFilePath(filePath); // FilePath가 맞는 듯 함.

				log.debug("++++++++++++++++ path =" + rFileName + ", " + sFileName + ", "+ fileSize + ", "+ filePath);
				if(!pOldSfileName.equals("")) {		//이전 첨부파일을 삭제할 경우
					FileUtil.delFile(FilePath, pOldSfileName);
				}
			}else{									// 첨부 안한 경우
				courseForumContentsDto.setRfileName(pOldRfileName);
				courseForumContentsDto.setSfileName(pOldSfileName);
				courseForumContentsDto.setFileSize(pOldFileSize);
				courseForumContentsDto.setFilePath(pOldFilePath);
			}
		}

		/** 웹에디터 셋팅 추가 Start**/
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH+FilePath;
		String WeasFileUrl =  ServiceUrl+"/data/"+FilePath;
		log.debug("WeasFilePath = "+WeasFilePath);
		VBN_files v_objFile = null;


		java.io.File dir = new java.io.File(WeasFilePath);
		if (!dir.exists())	dir.mkdirs();

		v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
		pContents = v_objFile.VBN_uploadMultiFiles(pContents, multipartRequest);

		/** 웹에디터 셋팅 추가 End**/

		String pEditorType = StringUtil.nvl(multipartRequest.getParameter("pEditorType"));

		int pBbsNo = StringUtil.nvl(multipartRequest.getParameter("pBbsNo"),pSeqNo);
		int pDepthNo = StringUtil.nvl(multipartRequest.getParameter("pDepthNo"),0);
		int pLevelNo = StringUtil.nvl(multipartRequest.getParameter("pLevelNo"),0);
		int pParentNo= StringUtil.nvl(multipartRequest.getParameter("pParentNo"),0);

		String pContentsType = StringUtil.nvl(multipartRequest.getParameter("pContentsType"));

		if(regMode.equals("Add")){
			pBbsNo = pSeqNo;
			pDepthNo = 0;
			pLevelNo = 0;
			pParentNo = 0;
		}
		if(regMode.equals("Reply")){
			pDepthNo = pDepthNo+1;
			pLevelNo = pLevelNo+1;
		}

		courseForumContentsDto.setSystemCode(systemCode);
		courseForumContentsDto.setCurriCode(pCurriCode);
		courseForumContentsDto.setCurriYear(pCurriYear);
		courseForumContentsDto.setCurriTerm(pCurriTerm);
		courseForumContentsDto.setCourseId(pCourseId);
		courseForumContentsDto.setForumId(pForumId);
		courseForumContentsDto.setSeqNo(pSeqNo);
		courseForumContentsDto.setBbsNo(pBbsNo);
		courseForumContentsDto.setDepthNo(pDepthNo);
		courseForumContentsDto.setLevelNo(pLevelNo);
		courseForumContentsDto.setParentNo(pParentNo);
		courseForumContentsDto.setEditorType(pEditorType);
		courseForumContentsDto.setContentsType(pContentsType);
		courseForumContentsDto.setSubject(pSubject);
		courseForumContentsDto.setKeyword(pKeyword);
		courseForumContentsDto.setContents(pContents);
		courseForumContentsDto.setRegId(regId);
		courseForumContentsDto.setModId(regId);
		String msg = "";



		String returnUrl = "/CourseForumContents.cmd?cmd=courseForumContentsPagingList&pCourseId="+pCourseId+"&pForumId="+pForumId;
		if(regMode.equals("Add"))// 입력모드
		{
			retVal = courseForumContentsDao.addCourseForumContents(courseForumContentsDto);
			msg = "등록완료";
		}else if(regMode.equals("Edit")){
			retVal = courseForumContentsDao.editCourseForumContents(courseForumContentsDto);
			msg = "수정완료";
			if(retVal <= 0){
				returnUrl = "/CourseForumContents.cmd?cmd=courseForumContentsEdit&pCourseId="+pCourseId+"&pForumId="+pForumId+"&pSeqNo="+pSeqNo;
				msg = "수정오류 다시 진행해 주세요";
			}
		}else if(regMode.equals("Reply")){
			boolean bVal = courseForumContentsDao.replyUpdateCourseForumContents(courseForumContentsDto,"Ins");
			if(bVal)	retVal  = 1;
			else		retVal  = 0;
			if(retVal >0){
				retVal = courseForumContentsDao.addCourseForumContents(courseForumContentsDto);
				msg = "등록완료";
			}
			if(retVal <= 0){
				returnUrl = "/CourseForumContents.cmd?cmd=courseForumContentsWrite&pCourseId="+pCourseId+"&pForumId="+pForumId+"&pSeqNo="+pSeqNo;
				msg = "등록오류 다시 진행해 주세요";
			}
		}

		new SiteNavigation(LECTURE).add(request,"토론").link(model);
        return notifyAndExit(systemCode, model, msg, returnUrl, LECTURE);
	}

	
	/**
	 * 게시글 등록/수정 한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumContentsRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		UserMemDTO 	userMemDto 	= 	UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = 	userMemDto.curriInfo;
		String    	systemCode 	= 	userMemDto.systemCode;
		String    	regId 		= 	userMemDto.userId;
		String    	pCurriCode 	= 	StringUtil.nvl(curriMemDto.curriCode);
		int			pCurriYear 	= 	StringUtil.nvl(curriMemDto.curriYear,0);
		int			pCurriTerm 	= 	StringUtil.nvl(curriMemDto.curriTerm,0);		
		
		String		pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int		 	pForumId 	= 	StringUtil.nvl(request.getParameter("pForumId"),0);
		int 		pSeqNo 		= 	StringUtil.nvl(request.getParameter("pSeqNo"),1);

		if(systemCode.equals(""))
			systemCode = StringUtil.nvl(request.getParameter("pSystemCode"), "");
		if(regId.equals(""))
			regId = StringUtil.nvl(request.getParameter("pUserId"), "");


		String regMode = StringUtil.nvl(request.getParameter("pRegMode"));

		CourseForumInfoDAO courseForumInfoDao = new CourseForumInfoDAO();
		CourseForumInfoDTO courseForumInfoDto = new CourseForumInfoDTO();

		CourseForumContentsDAO courseForumContentsDao = new CourseForumContentsDAO();
		CourseForumContentsDTO courseForumContentsDto = new CourseForumContentsDTO();

		MultipartRequest multipartRequest = null;
		int retVal = 0;
		String originName = "";
		String rFileName = "";
		String sFileName = "";
		String filePath = "";
		String fileSize = "";

		String	pOldRfileName = "";
		String	pOldSfileName = "";
		String	pOldFilePath = "";
		String	pOldFileSize = "";

		if(regMode.equals("Add") || regMode.equals("Reply") )// 입력모드
		{
			pSeqNo = courseForumContentsDao.getMaxSeqNo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId);
		}

		//파일 업로드
		String RegMonth = CommonUtil.getCurrentDate().substring(0,6);
		String FilePath = FileUtil.FILE_DIR+systemCode+"/course_forum/"+pCourseId +"_"+ pForumId +"/"+RegMonth+"/";
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath, pSeqNo+"_"+regId);
		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();
		String status = uploadEntity.getStatus();

		if(regMode.equals("Edit")){//-- 수정모드시는 현재 월이 아닌 등록된 글의 년월을 가져온다.
			RegMonth = StringUtil.nvl(request.getParameter("pRegDate")).substring(0,6);
			pOldRfileName 	= StringUtil.nvl(multipartRequest.getParameter("pOldRfileName"));
			pOldSfileName 	= StringUtil.nvl(multipartRequest.getParameter("pOldSfileName"));
			pOldFilePath 	= StringUtil.nvl(multipartRequest.getParameter("pOldFilePath"));
			pOldFileSize 	= StringUtil.nvl(multipartRequest.getParameter("pOldFileSize"));
		}

		String pSubject = StringUtil.nvl(multipartRequest.getParameter("pSubject"));
		String pKeyword = "";
		String pEditorType = "";
		if( new String("true").equals(multipartRequest.getParameter("VBN_FORM_WEAS")) == true ){//-- 웹에디터 사용시
			 pKeyword = StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
			 pEditorType = "W";
		}else{//-- 웹에디터 사용 안할경
			pKeyword = StringUtil.nvl(multipartRequest.getParameter("pContents"));
			pEditorType = "H";
		}
//		-- 음성게시물은 V 로 셋팅
		if(StringUtil.nvl(multipartRequest.getParameter("pEditorType")).equals("V"))
			pEditorType = "V";

		String pContents = StringUtil.nvl(multipartRequest.getParameter("pContents"));

		if (status.equals("E")){
			log.debug("첨부 파일 올리려다 실패하였습니다.");
		}else if (status.equals("O")){
			log.debug("첨부하신 파일이 용량을 초과했습니다.");
		}else if (status.equals("I")){
			log.debug("첨부 파일의 정보가 잘못되었습니다.");
		}else if(status.equals("S")){
			// 업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			log.debug("첨부 파일을 첨부하는데 성공하였습니다.");
			/** 웹에디터에서 첨부파일을 첨부시 인식하는걸 막기위해 **/
			ArrayList files = uploadEntity.getFiles();
			UploadFile file = null;

			for(int i = 0 ; i < files.size(); i++){
				file = (UploadFile)files.get(i);
				originName = StringUtil.nvl(file.getRootName());
				if(!originName.equals("")) {
					rFileName = StringUtil.nvl(file.getRootName());
					sFileName = file.getUploadName();
					fileSize = String.valueOf(file.getSize());
					filePath = uploadEntity.getUploadPath();
				}
			}

			if(!originName.equals("")) {			//파일첨부를 했을경우
				courseForumContentsDto.setRfileName(rFileName);
				courseForumContentsDto.setSfileName(sFileName);
				courseForumContentsDto.setFileSize(fileSize);
				courseForumContentsDto.setFilePath(filePath); // FilePath가 맞는 듯 함.

				if(!pOldSfileName.equals("")) {		//이전 첨부파일을 삭제할 경우
					FileUtil.delFile(FilePath, pOldSfileName);
				}
			}else{									// 첨부 안한 경우
				courseForumContentsDto.setRfileName(pOldRfileName);
				courseForumContentsDto.setSfileName(pOldSfileName);
				courseForumContentsDto.setFileSize(pOldFileSize);
				courseForumContentsDto.setFilePath(pOldFilePath);
			}
		}

		/** 웹에디터 셋팅 추가 Start**/
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH+FilePath;
		String WeasFileUrl =  ServiceUrl+"/data/"+FilePath;

		VBN_files v_objFile = null;

		java.io.File dir = new java.io.File(WeasFilePath);
		if (!dir.exists())	dir.mkdirs();

		v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
		pContents = v_objFile.VBN_uploadMultiFiles(pContents, multipartRequest);

		/** 웹에디터 셋팅 추가 End**/

		int pBbsNo = StringUtil.nvl(multipartRequest.getParameter("pBbsNo"),pSeqNo);
		int pDepthNo = StringUtil.nvl(multipartRequest.getParameter("pDepthNo"),0);
		int pLevelNo = StringUtil.nvl(multipartRequest.getParameter("pLevelNo"),0);
		int pParentNo= StringUtil.nvl(multipartRequest.getParameter("pParentNo"),0);

		String pContentsType = StringUtil.nvl(multipartRequest.getParameter("pContentsType"));

		if(regMode.equals("Add")){
			pBbsNo = pSeqNo;
			pDepthNo = 0;
			pLevelNo = 0;
			pParentNo = 0;
		}
		if(regMode.equals("Reply")){
			pDepthNo = pDepthNo+1;
			pLevelNo = pLevelNo+1;
		}

		courseForumContentsDto.setSystemCode(systemCode);
		courseForumContentsDto.setCurriCode(pCurriCode);
		courseForumContentsDto.setCurriYear(pCurriYear);
		courseForumContentsDto.setCurriTerm(pCurriTerm);
		courseForumContentsDto.setCourseId(pCourseId);
		courseForumContentsDto.setForumId(pForumId);
		courseForumContentsDto.setSeqNo(pSeqNo);
		courseForumContentsDto.setBbsNo(pBbsNo);
		courseForumContentsDto.setDepthNo(pDepthNo);
		courseForumContentsDto.setLevelNo(pLevelNo);
		courseForumContentsDto.setParentNo(pParentNo);
		courseForumContentsDto.setEditorType(pEditorType);
		courseForumContentsDto.setContentsType(pContentsType);
		courseForumContentsDto.setSubject(pSubject);
		courseForumContentsDto.setKeyword(pKeyword);
		courseForumContentsDto.setContents(pContents);
		courseForumContentsDto.setRegId(regId);
		courseForumContentsDto.setModId(regId);
		String msg = "";


		String returnUrl = "/CourseForumContents.cmd?cmd=courseForumContentsPagingList&pCourseId="+pCourseId+"&pForumId="+pForumId;

		if(regMode.equals("Add"))// 입력모드
		{
			retVal = courseForumContentsDao.addCourseForumContents(courseForumContentsDto);
			msg = "등록완료";
		}else if(regMode.equals("Edit")){
			retVal = courseForumContentsDao.editCourseForumContents(courseForumContentsDto);
			msg = "수정완료";
			if(retVal <= 0){
				returnUrl = "/CourseForumContents.cmd?cmd=courseForumContentsEdit&pCourseId="+pCourseId+"&pForumId="+pForumId+"&pSeqNo="+pSeqNo;
				msg = "수정오류 다시 진행해 주세요";
			}
		}else if(regMode.equals("Reply")){
			boolean bVal = courseForumContentsDao.replyUpdateCourseForumContents(courseForumContentsDto,"Ins");
			if(bVal)	retVal  = 1;
			else		retVal  = 0;
			if(retVal >0){
				retVal = courseForumContentsDao.addCourseForumContents(courseForumContentsDto);
				msg = "등록완료";
			}
			if(retVal <= 0){
				returnUrl = "/CourseForumContents.cmd?cmd=courseForumContentsWrite&pCourseId="+pCourseId+"&pForumId="+pForumId+"&pSeqNo="+pSeqNo;
				msg = "등록오류 다시 진행해 주세요";
			}
		}

		new SiteNavigation(LECTURE).add(request,"토론").link(model);
        return notifyAndExit(systemCode, model, msg, returnUrl, LECTURE);
	}


	/**
	 * 게시글을 삭제한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumContentsDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		String pCourseId 	= StringUtil.nvl(request.getParameter("pCourseId"));
		int pForumId 		= Integer.parseInt(request.getParameter("pForumId"));
		int pSeqNo 			= Integer.parseInt(request.getParameter("pSeqNo"));
		String pContents 	= StringUtil.nvl(request.getParameter("pContents"));


		CourseForumInfoDAO courseForumInfoDao = new CourseForumInfoDAO();
		CourseForumContentsDAO courseForumContentsDao = new CourseForumContentsDAO();

		CourseForumInfoDTO forumInfo = courseForumInfoDao.getCourseForumInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId);

		CourseForumContentsDTO contentsDto = courseForumContentsDao.getCourseForumContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId ,pForumId, pSeqNo);

		String rfile_name = "";
		String sfile_name = "";
		String filePath = "";

		String msg = "삭제하였습니다.";
		String returnUrl = "/CourseForumContents.cmd?cmd=courseForumContentsPagingList&pCourseId="+pCourseId+"&pForumId="+pForumId;

		int retVal = 0;
		retVal = courseForumContentsDao.delCourseForumContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pSeqNo);
		if(retVal > 0){ //-- 게시글 삭제 성공한 경우 첨부 이미지 있으면 삭제
			rfile_name 	= contentsDto.getRfileName();
			sfile_name 	= contentsDto.getSfileName();
			filePath 	= contentsDto.getFilePath();
			if(!rfile_name.equals("") && !sfile_name.equals("") && !filePath.equals("")){
				FileUtil.delFile(filePath, sfile_name);
			}

			String RegMonth = contentsDto.getRegDate().substring(0,6);
			String FilePath = FileUtil.FILE_DIR+systemCode+"/course_forum/"+pCourseId+"_"+pForumId+"/"+RegMonth+"/";
			/** 웹에디터 셋팅 추가 Start**/
			String ServiceUrl = CommonUtil.SERVERURL;
			String WeasFilePath = FileUtil.UPLOAD_PATH+FilePath;
			String WeasFileUrl =  ServiceUrl+"/data/"+FilePath;
			VBN_files v_objFile = null;
			v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
			//v_objFile.VBN_deleteFiles(pContents);
			//--************************* contents 삭제시 반드시 해 줄
			pContents = StringUtil.ReplaceAll(contentsDto.getContents(),"&quot;","\"");
			v_objFile.VBN_deleteFiles(pContents);

			//-- 글 순서 정렬
			CourseForumContentsDTO courseForumContentsDto = new CourseForumContentsDTO();

			courseForumContentsDto.setSystemCode(systemCode);
			courseForumContentsDto.setCourseId(pCourseId);
			courseForumContentsDto.setForumId(pForumId);
			courseForumContentsDto.setBbsNo(contentsDto.getBbsNo());
			courseForumContentsDto.setLevelNo(contentsDto.getLevelNo());
			boolean bVal = courseForumContentsDao.replyUpdateCourseForumContents(courseForumContentsDto,"Del");
		}else{
			msg = "삭제 실패 하였습니다.";
			returnUrl = "/CourseForumContents.cmd?cmd=courseForumContentsShow&pCourseId="+pCourseId+"&pForumId="+pForumId+"&pSeqNo="+pSeqNo;
		}

		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
		 return notifyAndExit(systemCode, model, msg, returnUrl, LECTURE);
	}

	/**
	 * 게시글을 삭제한다. [AJAX] 
	 *
	 * @return
	 * @throws Exception
	 */
	public String courseForumContentsDeleteAjax(String pCurriCode, int pCurriYear, int pCurriTerm, String pCourseId, int pForumId, int pSeqNo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String	systemCode	=	UserBroker.getSystemCode(request);
		int		retVal		=	0;
		
		CourseForumContentsDAO courseForumContentsDao = new CourseForumContentsDAO();
		CourseForumContentsDTO contentsDto = courseForumContentsDao.getCourseForumContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId ,pForumId, pSeqNo);

		String	rfile_name	=	"";
		String	sfile_name 	= 	"";
		String	filePath 	= 	"";
		
		retVal = courseForumContentsDao.delCourseForumContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pSeqNo);
		if(retVal > 0){ //-- 게시글 삭제 성공한 경우 첨부 이미지 있으면 삭제
			rfile_name 	= contentsDto.getRfileName();
			sfile_name 	= contentsDto.getSfileName();
			filePath 	= contentsDto.getFilePath();
			if(!rfile_name.equals("") && !sfile_name.equals("") && !filePath.equals("")){
				FileUtil.delFile(filePath, sfile_name);
			}

			String RegMonth = contentsDto.getRegDate().substring(0,6);
			String FilePath = FileUtil.FILE_DIR+systemCode+"/course_forum/"+pCourseId+"_"+pForumId+"/"+RegMonth+"/";
			/** 웹에디터 셋팅 추가 Start**/
			String ServiceUrl = CommonUtil.SERVERURL;
			String WeasFilePath = FileUtil.UPLOAD_PATH+FilePath;
			String WeasFileUrl =  ServiceUrl+"/data/"+FilePath;
			VBN_files v_objFile = null;
			v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
			//--************************* contents 삭제시 반드시 해 줄
			String	pContents = StringUtil.ReplaceAll(contentsDto.getContents(),"&quot;","\"");
			v_objFile.VBN_deleteFiles(pContents);

			//-- 글 순서 정렬
			CourseForumContentsDTO courseForumContentsDto = new CourseForumContentsDTO();

			courseForumContentsDto.setSystemCode(systemCode);
			courseForumContentsDto.setCourseId(pCourseId);
			courseForumContentsDto.setForumId(pForumId);
			courseForumContentsDto.setBbsNo(contentsDto.getBbsNo());
			courseForumContentsDto.setLevelNo(contentsDto.getLevelNo());
			boolean bVal = courseForumContentsDao.replyUpdateCourseForumContents(courseForumContentsDto,"Del");
		}
		
		return String.valueOf(retVal);
	}
	

	/**
	 * 토론방 정보 첨부파일( 이미지) 삭제
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumContentsFileDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		//		-- 과정정보 가져오기 시작
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String systemCode 		= 	UserBroker.getSystemCode(request);
		String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);


		//-- 일반변수들 값을 받아옵니다
		String pRegMode			=	StringUtil.nvl(request.getParameter("pRegMode"));
		String pCourseId		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int pForumId 			= Integer.parseInt(request.getParameter("pForumId"));
		int pSeqNo 				= Integer.parseInt(request.getParameter("pSeqNo"));
		model.put("pRegMode", pRegMode);
		model.put("pCourseId", pCourseId);
		model.put("pForumId", String.valueOf(pForumId));
		model.put("pSeqNo", String.valueOf(pSeqNo));

		CourseForumContentsDAO courseForum = new CourseForumContentsDAO();
		CourseForumContentsDTO courseForumContents	= courseForum.getCourseForumContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId , pSeqNo );

		String FilePath = courseForumContents.getFilePath();
		String SfileName = courseForumContents.getSfileName();

		int retVal = 0;
		retVal = courseForum.delCourseForumContentsFile(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pSeqNo);

		if(!courseForumContents.getSfileName().equals("") && retVal > 0){ //-- 게시판정보 수정 성공하고 첨부 이미지 있는경우
			FileUtil.delFile(FilePath, courseForumContents.getSfileName());
		}

		String msg = "삭제하였습니다";
		String returnUrl = "/CourseForumContents.cmd?cmd=courseForumContentsEdit&pCourseId="+pCourseId+"&pForumId="+pForumId+"&pSeqNo="+pSeqNo;
		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
        return notifyAndExit(systemCode, model, msg, returnUrl, LECTURE);
	}

	
	/**
	 * 게시글 리스트를 페이징 처리 하여 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumContentsPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;

	    String systemCode 	= userMemDto.systemCode;
	    String userType 	= userMemDto.userType;
	    String userId		= userMemDto.userId;

	    if(userType.equals("")) userType="G";

	    String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pForumId = StringUtil.nvl(request.getParameter("pForumId"),0);
		String pSearchKey = StringUtil.nvl(request.getParameter("pSearchKey"));
		String pKeyWord = StringUtil.nvl(request.getParameter("pKeyWord"));
		ListDTO courseForumContentsList = null;
		//포럼리스트에서 온 경우 접속수를 증가시켜준다.
		String pConnectYN 	= StringUtil.nvl(request.getParameter("pConnectYN"));
		CourseForumUserDAO	courseForumUserDao = new CourseForumUserDAO();
		CourseForumInfoDAO courseForumInfoDao 	= 	new CourseForumInfoDAO();
		CourseForumInfoDTO courseForumInfo		=	new CourseForumInfoDTO();
		CourseForumContentsDAO courseForumContentsDao = new CourseForumContentsDAO();
		courseForumInfo = courseForumInfoDao.getCourseForumInfo( systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId );
		//글쓸 권한 체크
		String ckWrite = "Y";
		if(courseForumInfo.getForumType().equals("T")){
			int SubTeamNo = courseForumUserDao.getSubForumId(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, courseForumInfo.getForumId(), userId);

			if(pForumId == SubTeamNo)
				ckWrite = "Y";
			else
				ckWrite = "N";
		}

		// connect info insert
		if(pConnectYN.equals("Y")){
			if(courseForumInfo.getForumType().equals("T")){
				courseForumUserDao.editConnetNo( systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, courseForumInfo.getParentForumId() , userId );
			}else{
				courseForumUserDao.editConnetNo( systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId , userId );
			}
		}

		model.put("courseForumInfo", courseForumInfo);
		model.put("ckWrite", ckWrite);
		model.put("pCourseId", pCourseId);
		model.put("pForumId", String.valueOf(pForumId));

		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
	    return forward(request, model, "/course_forum/courseForumContentsPagingList.jsp");
	}

	
	/**
	 * 게시글 리스트를 페이징 처리 하여 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO courseForumContentsPagingListAjax(int curPage, String pCourseId, int pForumId, String pSearchKey, String pKeyWord, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;

	    String systemCode 	= userMemDto.systemCode;
	    String userType 	= userMemDto.userType;
	    String userId		= userMemDto.userId;
	    if(userType.equals("")) userType="G";

	    String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		CourseForumInfoDAO courseForumInfoDao 	= 	new CourseForumInfoDAO();
		CourseForumContentsDAO courseForumContentsDao = new CourseForumContentsDAO();

		String pOrder = "";
		String pWhere = "";
		if(pSearchKey.equals("")){
			pWhere = " and contents_type='S' ";
		}

		AjaxListDTO ajaxListDto = new AjaxListDTO();
		ArrayList dataList = new ArrayList();
		CourseForumContentsDTO courseForumContentsDto = null;
		ArrayList arrList = new ArrayList();
		if( curPage <= 1 ){
			String	AddWhere = " and contents_type = 'N' ";
			//공지 리스트만 뽑아서 넘겨줘야 함.
			arrList = courseForumContentsDao.getCourseForumContentsList(systemCode, pCurriCode, pCurriYear, pCurriTerm,  pCourseId, pForumId, AddWhere, pOrder);

			for(int i=0;i<arrList.size(); i++){
				courseForumContentsDto = (CourseForumContentsDTO)arrList.get(i);
				courseForumContentsDto.setRegName(CommonUtil.getUserName(systemCode, courseForumContentsDto.getRegId()));
				dataList.add(courseForumContentsDto);
			}
		}

		// 데이타를 담는다.
		ListDTO listObj = null;
		CourseForumCommentDAO courseForumCommentDao = new CourseForumCommentDAO();
		listObj = courseForumContentsDao.getCourseForumContentsPagingList(curPage, 10, 10, systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, userType,pSearchKey,pKeyWord, pWhere, pOrder);

		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				courseForumContentsDto = new CourseForumContentsDTO();
				// pk
				courseForumContentsDto.setForumId(rs.getInt("forum_id"));
				courseForumContentsDto.setSeqNo(rs.getInt("seq_no"));
				// data
				courseForumContentsDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
				courseForumContentsDto.setContents(StringUtil.nvl(rs.getString("contents")));
				courseForumContentsDto.setSubject(StringUtil.nvl(rs.getString("subject")));
				courseForumContentsDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				courseForumContentsDto.setRegName(CommonUtil.getUserName(systemCode,rs.getString("reg_id")));
				courseForumContentsDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				courseForumContentsDto.setHitNo(rs.getInt("hit_no"));
				courseForumContentsDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				courseForumContentsDto.setContentsType("S");	// 공지와 구분을 지어주기 위해..

				dataList.add(courseForumContentsDto);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}

		return ajaxListDto;
	}



}

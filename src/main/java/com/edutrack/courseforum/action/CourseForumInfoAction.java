/*
 * Created on 2004. 10. 15.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseforum.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.code.dao.CodeSoDAO;
import com.edutrack.code.dto.CodeSoDTO;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.DateSetter;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.courseforum.dao.CourseForumTeamDAO;
import com.edutrack.courseforum.dao.CourseForumUserDAO;
import com.edutrack.courseforum.dto.CourseForumUserDTO;
import com.edutrack.courseforum.dao.CourseForumInfoDAO;
import com.edutrack.courseforum.dto.CourseForumInfoDTO;
import com.edutrack.courseforum.dao.CourseForumContentsDAO;
import com.edutrack.curristudent.dao.StudentDAO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.DateTimeUtil;
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
public class CourseForumInfoAction  extends StrutsDispatchAction{

	   // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	/**
	 * 토론 정보생성 폼을 띄워준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumInfoWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		//		-- 과정정보 가져오기 시작
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String systemCode 		= 	UserBroker.getSystemCode(request);

		String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		int pParentForumId = 0;

		//-- 일반변수들 값을 받아옵니다
		String pRegMode			= StringUtil.nvl(request.getParameter("pRegMode"));
		String pCourseId		= StringUtil.nvl(request.getParameter("pCourseId"));
		String sParentForumId	= StringUtil.nvl(request.getParameter("pParentForumId"));
		int pForumId 			= StringUtil.nvl(request.getParameter("pForumId"),0);

		CourseForumInfoDAO courseForum = new CourseForumInfoDAO();
		CourseForumInfoDTO courseForumInfo	= courseForum.getCourseForumInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId);

		//	현재 총 학생수와 토론에 등록된 학생수를 가져온다.
		StudentDAO		studentDao	= new StudentDAO();
		CourseForumTeamDAO courseForumTeam	= new CourseForumTeamDAO();

		model.put("curri_cnt", String.valueOf(studentDao.getTotCount(systemCode, pCurriCode, pCurriYear, pCurriTerm, "S")));
		model.put("forum_scnt", String.valueOf(courseForumTeam.getCourseForumTeamMemberCnt(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId)));
		model.put("courseForumInfo", courseForumInfo);
		model.put("pForumId", String.valueOf(pForumId));
		model.put("pRegMode",pRegMode);
		model.put("pCourseId", pCourseId);
		model.put("pParentForumId", String.valueOf(pParentForumId));
		DateSetter ds = new DateSetter();

		if(("Add").equals(pRegMode)){
			ds = new DateSetter(DateTimeUtil.getDate(), DateTimeUtil.getDate() ).link(model);
		}else{
			ds = new DateSetter((String)courseForumInfo.getStartDate(), (String)courseForumInfo.getEndDate() ).link(model);
		}
		model.put("ds", ds);

		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
		return forward(request, model, "/course_forum/courseForumInfoWrite.jsp");
	}


	/**
	 * 토론방정보 보기 폼을 띄운다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumInfoView(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		//		-- 과정정보 가져오기 시작
		UserMemDTO userMemDto 	= UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String systemCode 		= 	UserBroker.getSystemCode(request);
		String pCurriCode 		= curriMemDto.curriCode;
		String userType 		= userMemDto.userType;
		String userId			= userMemDto.userId;
		int pCurriYear 			= StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm 			= StringUtil.nvl(curriMemDto.curriTerm,0);


		//-- 일반변수들 값을 받아옵니다
		String pCourseId		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int pForumId 			= Integer.parseInt(request.getParameter("pForumId"));


		model.put("pCourseId", pCourseId);
		model.put("pForumId", String.valueOf(pForumId));
		model.put("curPage", String.valueOf(StringUtil.nvl(request.getParameter("curPage"),1)));

		CourseForumInfoDAO courseForum = new CourseForumInfoDAO();
		StudentDAO		studentDao	= new StudentDAO();
		CourseForumUserDAO courseForumUser	= new CourseForumUserDAO();
		CourseForumInfoDTO courseForumInfo	= courseForum.getCourseForumInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId);

		//토론방 참여 학생인지.
		if(userType.equals("S")){
			model.put("pMySubForumId", String.valueOf(courseForumUser.getSubForumId(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, userId )));
		}

		String pWhere = "";
		if(courseForumInfo.getParentForumId() == 0)
			pWhere 			=	" and parent_forum_id = '"+pForumId+"' ";
		else
			pWhere 			=	" and parent_forum_id = '"+courseForumInfo.getParentForumId()+"' ";

		String pOrderby			=	" sub_team_no asc ";
		ArrayList courseFourmInfoList = courseForum.getCourseForumInfoList(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pWhere, pOrderby );

		model.put("courseForumInfo", courseForumInfo);
		model.put("courseFourmInfoList", courseFourmInfoList);

		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
        return forward(request, model, "/course_forum/courseForumInfoView.jsp");
	}



	/**
	 * 토론 점수 쓰기 폼을 띄운다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumScoreWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		//		-- 과정정보 가져오기 시작
		UserMemDTO userMemDto 	= UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String systemCode 		= 	UserBroker.getSystemCode(request);
		String pCurriCode 		= curriMemDto.curriCode;
		String userType 		= userMemDto.userType;
		String userId			= userMemDto.userId;
		int pCurriYear 			= StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm 			= StringUtil.nvl(curriMemDto.curriTerm,0);


		//-- 일반변수들 값을 받아옵니다
		String pCourseId		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int pForumId 			= 	StringUtil.nvl(request.getParameter("pForumId"),0);
		model.put("pCourseId", pCourseId);
		model.put("pForumId", String.valueOf(pForumId));

		//토론 참여학생 리스트페이지를
		int curPage = 1;
		if(request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));


		CourseForumInfoDAO 	courseForum 	= new CourseForumInfoDAO();
		StudentDAO			studentDao		= new StudentDAO();
		CourseForumUserDAO 	courseForumUser	= new CourseForumUserDAO();

		CourseForumInfoDTO 	courseForumInfo	= courseForum.getCourseForumInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId);

		ArrayList courseFourmInfoList = null;

		String pWhere	=	" and parent_forum_id = '"+pForumId+"' ";;
		String pOrderby	=	" sub_team_no asc ";
		if(courseForumInfo.getParentForumId() == 0){
			model.put("userList", courseForumUser.getCourseForumUserJoinContentsPagingList(curPage , systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId));
		}else{
			model.put("userList", courseForumUser.getCourseForumUserJoinContentsPagingList(curPage , systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId,  courseForumInfo.getForumId() ));
		}
		courseFourmInfoList = courseForum.getCourseForumInfoList(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pWhere, pOrderby );

		model.put("courseForumInfo", courseForumInfo);
		model.put("courseFourmInfoList", courseFourmInfoList);

		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
       return forward(request, model, "/course_forum/courseForumScoreWrite.jsp");
	}



	/**
	 * 토론방 정보를 등록/수정 한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumInfoRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String regId	= userMemDto.userId;

		int pForumId = Integer.parseInt(StringUtil.nvl(request.getParameter("pForumId"),"1"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String regMode = StringUtil.nvl( request.getParameter("pRegMode") );

		log.debug("regMode ==>"+regMode + " :: courseid =>" + pCourseId);

		CourseForumInfoDAO courseForumInfoDao = new CourseForumInfoDAO();
		CourseForumInfoDTO courseForumInfoDto = new CourseForumInfoDTO();

		if(regMode.equals("Add")){
			pForumId = courseForumInfoDao.getMaxForumId(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId);
		}

		MultipartRequest multipartRequest = null;
		int retVal = 0;
		String FilePath = FileUtil.FILE_DIR+systemCode+"/course_forum/";
		log.debug("FilePath ==>"+FilePath);
		String pOldRfileName = "";
		String pOldSfileName = "";
		String pOldFileSize = "";
		String pOldFilePath = "";
		String originName = "";
		String rFileName = "";
		String sFileName = "";
		String filePath = "";
		String fileSize = "";
		int pParentForumId = 0;
		String pStartDate = "";
		String pEndDate = "";
		String pOldForumType = "";
		String pOldRegistYn = "";

		//		  파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath, "courseforum_"+pForumId);
		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();
		String status = uploadEntity.getStatus();


		if(regMode.equals("Edit"))// 수정 모드 접근시		파일 삭제 정보 가져오기
		{
			pOldRfileName = StringUtil.nvl(multipartRequest.getParameter("pOldRfileName"));
			pOldSfileName = StringUtil.nvl(multipartRequest.getParameter("pOldSfileName"));
			pOldFileSize = StringUtil.nvl(multipartRequest.getParameter("pOldFileSize"));
			pOldFilePath = StringUtil.nvl(multipartRequest.getParameter("pOldFilePath"));
			pOldForumType = StringUtil.nvl( multipartRequest.getParameter("pOldForumType"));
			pOldRegistYn = StringUtil.nvl( multipartRequest.getParameter("pOldRegistYn"));
		}

		if (status.equals("E")){
			log.debug("첨부 파일 올리려다 실패하였습니다.");
		}else if (status.equals("O")){
			log.debug("첨부하신 파일이 용량을 초과했습니다.");
		}else if (status.equals("I")){
			log.debug("첨부 파일의 정보가 잘못되었습니다.");
		}else if(status.equals("S")){
			// 업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			log.debug("첨부 파일을 첨부하는데 성공하였습니다.");
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
				courseForumInfoDto.setRfileName(rFileName);
				courseForumInfoDto.setSfileName(sFileName);
				courseForumInfoDto.setFileSize(fileSize);
				courseForumInfoDto.setFilePath(filePath); // FilePath가 맞는 듯 함.

				if(!pOldSfileName.equals("")) {		//이전 첨부파일을 삭제할 경우
					FileUtil.delFile(FilePath, pOldSfileName);
				}
			}else{									// 첨부 안한 경우
				courseForumInfoDto.setRfileName(pOldRfileName);
				courseForumInfoDto.setSfileName(pOldSfileName);
				courseForumInfoDto.setFileSize(pOldFileSize);
				courseForumInfoDto.setFilePath(pOldFilePath);
			}
		}

		pParentForumId = StringUtil.nvl(multipartRequest.getParameter("pParentForumId"),0);
		//분임토론의 경우 부모의 시간을 받는다.
		if(pParentForumId > 0 ){
			pStartDate 	= multipartRequest.getParameter("pStartDate");
			pEndDate 	= multipartRequest.getParameter("pEndDate");
		}else{
			String pYear1 = StringUtil.nvl(multipartRequest.getParameter("pYear1"));
			String pMonth1 = StringUtil.nvl(multipartRequest.getParameter("pMonth1"));
			String pDay1 = StringUtil.nvl(multipartRequest.getParameter("pDay1"));
			String pDate1 = StringUtil.nvl(multipartRequest.getParameter("pDate1"));
					pStartDate =pYear1+pMonth1+pDay1+"000000";
			if(pYear1.equals("")) pStartDate="";

			String pYear2 = StringUtil.nvl(multipartRequest.getParameter("pYear2"));
			String pMonth2 = StringUtil.nvl(multipartRequest.getParameter("pMonth2"));
			String pDay2 = StringUtil.nvl(multipartRequest.getParameter("pDay2"));
			String pDate2 = StringUtil.nvl(multipartRequest.getParameter("pDate2"));
					pEndDate =pYear2+pMonth2+pDay2+"235959";
			if(pYear2.equals("")) pEndDate="";
		}
		String pForumType = StringUtil.nvl(multipartRequest.getParameter("pForumType"));

		courseForumInfoDto.setSystemCode(systemCode);
		courseForumInfoDto.setCurriCode(pCurriCode);
		courseForumInfoDto.setCurriYear(pCurriYear);
		courseForumInfoDto.setCurriTerm(pCurriTerm);
		courseForumInfoDto.setCourseId(pCourseId);
		courseForumInfoDto.setForumId(pForumId);
		courseForumInfoDto.setForumType(pForumType);
		courseForumInfoDto.setParentForumId(pParentForumId);
		courseForumInfoDto.setSubTeamNo(StringUtil.nvl(multipartRequest.getParameter("pSubTeamNo"),0));
		courseForumInfoDto.setSubject(StringUtil.nvl(multipartRequest.getParameter("pSubject")));
		courseForumInfoDto.setContents(StringUtil.nvl(multipartRequest.getParameter("pContents")));
		courseForumInfoDto.setForumScore(StringUtil.nvl(multipartRequest.getParameter("pForumScore"),1));
		courseForumInfoDto.setStartDate(pStartDate);
		courseForumInfoDto.setEndDate(pEndDate);
		courseForumInfoDto.setOpenYn(StringUtil.nvl(multipartRequest.getParameter("pOpenYn")));
		courseForumInfoDto.setRegistYn(StringUtil.nvl(multipartRequest.getParameter("pRegistYn")));
		courseForumInfoDto.setEditorYn(StringUtil.nvl(multipartRequest.getParameter("pEditorYn")));
		courseForumInfoDto.setVoiceYn(StringUtil.nvl(multipartRequest.getParameter("pVoiceYn")));
		courseForumInfoDto.setCommentUseYn(StringUtil.nvl(multipartRequest.getParameter("pCommentUseYn")));
		courseForumInfoDto.setEmoticonUseYn(StringUtil.nvl(multipartRequest.getParameter("pEmoticonUseYn")));
		courseForumInfoDto.setViewThreadYn(StringUtil.nvl(multipartRequest.getParameter("pViewThreadYn")));
		courseForumInfoDto.setViewPrevNextYn(StringUtil.nvl(multipartRequest.getParameter("pViewPrevNextYn")));
		courseForumInfoDto.setRegId(regId);

		//추가시 전체의 경우는 course_forum_user에 수강생 모두를 추가시킨다.
		//forum_id와 sub_team_no는 같다.
		if(regMode.equals("Add") && pForumType.equals("A") && multipartRequest.getParameter("pRegistYn").equals("Y") ){
			// 유저테이블에 과정 학생리스트를 등록시킨다.
			// 이미 이전 등록했는지도 체크해야한다. 수정시 문제가 될 듯.
			StudentDAO studentDao = new StudentDAO();
			ArrayList studentList = (ArrayList)studentDao.getStudentList( systemCode,  pCurriCode,  pCurriYear,  pCurriTerm );

			CourseForumUserDAO courseForumUserDao = new CourseForumUserDAO();
			CourseForumUserDTO courseForumUser	= new CourseForumUserDTO();

			courseForumUser.setSystemCode(systemCode);
			courseForumUser.setCurriCode(pCurriCode);
			courseForumUser.setCurriYear(pCurriYear);
			courseForumUser.setCurriTerm(pCurriTerm);
			courseForumUser.setCourseId(pCourseId);
			courseForumUser.setForumId(pForumId);
			courseForumUser.setSubForumId(pForumId);

			courseForumUser.setScore(0);
			courseForumUser.setConnectNo(0);
			courseForumUser.setRegId(regId);

			int retSubVal = 0;
			String UserId = "";
			for(int i=0; i < studentList.size(); i++){
				UserId = (String)studentList.get(i);
				courseForumUser.setUserId( UserId );
				retSubVal = courseForumUserDao.addCourseForumUser(courseForumUser);
			}
		}
		//수정시( 전체고  이전에 미등록인 경우 )
		//1=> 이전에 조별인경우 :: 인포와 유저의 기존 데이타 삭제 후 유저테이블에 유저 추가
		//2=> 지금 등록이고 ( 이전에 전체이고 미등록의 경우, 이전에 조별인경우 미등록) :: 그냥 유저테이블에 추가
		if(regMode.equals("Edit") && pForumType.equals("A") && pOldRegistYn.equals("N")){
			StudentDAO studentDao = new StudentDAO();
			CourseForumUserDAO courseForumUserDao = new CourseForumUserDAO();
			CourseForumUserDTO courseForumUser	= new CourseForumUserDTO();
			int retSubVal = 0;
			//관련 기존 데이타 삭제
			if(pOldForumType.equals("T")){
				String pWhere = " and parent_forum_id = '"+ pForumId +"'";
				ArrayList delForumList = courseForumInfoDao.getCourseForumInfoList( systemCode, pCurriCode, pCurriYear, pCurriTerm , pCourseId , pWhere , "" );
				CourseForumInfoDTO delForumDto = null;
				for(int d=0; d < delForumList.size(); d++){
					delForumDto = new CourseForumInfoDTO();
					FileUtil.delFile(FilePath, delForumDto.getSfileName());//분임조의 첨부파일을 삭제한다.
				}
				//관련 분임조를 삭제한다.
				retSubVal = courseForumInfoDao.delCourseForumInfo( systemCode, pCurriCode, pCurriYear, pCurriTerm , pCourseId , pWhere );
				//유저 삭제
				pWhere = " and forum_id ='"+pForumId+"'";
				retSubVal = courseForumUserDao.delCourseForumUser(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pWhere, "");

			}
			//유저테이블에 과정 학생리스트를 등록시킨다.
			if(multipartRequest.getParameter("pRegistYn").equals("Y")){
				ArrayList studentList = (ArrayList)studentDao.getStudentList( systemCode,  pCurriCode,  pCurriYear,  pCurriTerm );
				courseForumUser.setSystemCode(systemCode);
				courseForumUser.setCurriCode(pCurriCode);
				courseForumUser.setCurriYear(pCurriYear);
				courseForumUser.setCurriTerm(pCurriTerm);
				courseForumUser.setCourseId(pCourseId);
				courseForumUser.setForumId(pForumId);
				courseForumUser.setSubForumId(pForumId);
				courseForumUser.setScore(0);
				courseForumUser.setConnectNo(0);
				courseForumUser.setRegId(regId);
				courseForumUser.setModId(regId);
				String UserId = "";
				for(int i=0; i < studentList.size(); i++){
					UserId = (String)studentList.get(i);
					courseForumUser.setUserId( UserId );
					//우선 등록되어 있는지 확인 작업을..
					int cnt = 0;
					cnt = courseForumUserDao.getUserIdRegistCount(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId , pForumId , UserId);
					if(cnt == 0){//삽입
						retVal = courseForumUserDao.addCourseForumUser(courseForumUser);
					}else{//수정
						retVal = courseForumUserDao.editCourseForumUser(courseForumUser);
					}
				}
			}
		}

		String msg = "";
		String returnUrl = "";
		if(	pParentForumId == 0){
			returnUrl = "/CourseForumInfo.cmd?cmd=courseForumInfoPagingList&pCourseId="+pCourseId;
		}else{
			returnUrl = "/CourseForumInfo.cmd?cmd=courseForumInfoEdit&pCourseId="+pCourseId+"&pForumId="+pParentForumId;
		}
		if(regMode.equals("Add"))// 입력모드
		{
			retVal = courseForumInfoDao.addCourseForumInfo(courseForumInfoDto);
			msg = "등록완료";
		}else{
			courseForumInfoDto.setModId(regId);
			retVal = courseForumInfoDao.editCourseForumInfo(courseForumInfoDto);
			msg = "수정완료";
			if(retVal <= 0){
				returnUrl = "/CourseForumInfo.cmd?cmd=courseForumInfoEdit&pCourseId="+pCourseId+"&pForumId="+pForumId;
				msg = "수정오류 다시 진행해 주세요";
			}
		}

		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
        return notifyAndExit(systemCode, model, msg, returnUrl, LECTURE);
	}


	/**
	 * 토론방 정보를 삭제한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumInfoDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		//		-- 과정정보 가져오기 시작
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String systemCode 	= 	UserBroker.getSystemCode(request);
		String pCurriCode = curriMemDto.curriCode;
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		//-- 일반변수들 값을 받아옵니다
		//String pRegMode		=	StringUtil.nvl(request.getParameter("pRegMode"));
		String pCourseId		= StringUtil.nvl(request.getParameter("pCourseId"));
		String pForumType		= StringUtil.nvl(request.getParameter("pForumType"));
		int pForumId 			= StringUtil.nvl(request.getParameter("pForumId"),0);
		int pParentForumId 		= StringUtil.nvl(request.getParameter("pParentForumId"),0);
		String pDelGubun		= StringUtil.nvl(request.getParameter("pDelGubun"));
		String pCallGubun		= StringUtil.nvl(request.getParameter("pCallGubun"));	// call for Ajax

		int retVal = 0;
		String pWhere1 = "";
		String pWhere = "";

		//model.put("pRegMode", pRegMode);
		model.put("pCourseId", pCourseId);
		model.put("pForumId", String.valueOf(pForumId));
		model.put("pForumType", pForumType);
		model.put("pParentForumId", String.valueOf(pParentForumId));

		CourseForumInfoDAO courseForum 					= 	new CourseForumInfoDAO();
		CourseForumUserDAO courseForumUserDao 			= 	new CourseForumUserDAO();
		CourseForumContentsDAO	courseForumContentsDao	=	new CourseForumContentsDAO();

		CourseForumInfoDTO courseForumInfo				= 	courseForum.getCourseForumInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId);

		String FilePath = FileUtil.FILE_DIR+systemCode+"/course_forum/";
		String SfileName = courseForumInfo.getSfileName();

		pWhere1 = "  and ( forum_id = '"+ pForumId +"' )";
		if(("ALL").equals(pDelGubun.toUpperCase()) && pParentForumId==0 ){
			pWhere1 += "  or ( parent_forum_id = '"+ pForumId +"') ";
		}

		//		토론방과 관련된 유저테이블과 게시판, 꼬리말 등을 함께 삭제해 주어야 함. (우선은 info, user만)
		if(pForumType.equals("T")){

			ArrayList delForumList = courseForum.getCourseForumInfoList( systemCode, pCurriCode, pCurriYear, pCurriTerm , pCourseId , pWhere1 , "" );
			CourseForumInfoDTO delForumDto = null;

			for(int d=0; d < delForumList.size(); d++){
				delForumDto = new CourseForumInfoDTO();
				FileUtil.delFile(FilePath, delForumDto.getSfileName());//분임조의 첨부파일을 삭제한다.

				//게시글 삭제(꼬리말은  delCourseForumContents안에서 삭제함)
				log.debug("dsss :: " + delForumDto.getForumId());
				pWhere = " and forum_id='"+ pForumId + "'";
				retVal 	= 	courseForumUserDao.delCourseForumUser(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pWhere , "");

				courseForumContentsDao.delCourseForumContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, delForumDto.getForumId(), 0);
			}
			//log.debug("============================>" + retVal + " :::: " + pForumId);

		}else{
			pWhere = " and forum_id='"+ pForumId + "'";
			retVal 	= 	courseForumUserDao.delCourseForumUser(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId,pWhere , "");
			retVal	=	courseForumContentsDao.delCourseForumContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, 0);
		}

		// parent forum delete
		retVal = courseForum.delCourseForumInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pWhere1);

		if(!courseForumInfo.getSfileName().equals("") && retVal > 0){ //-- 게시판정보 수정 성공하고 첨부 이미지 있는경우
			FileUtil.delFile(FilePath, courseForumInfo.getSfileName());
		}


		String msg = "성공적으로 삭제하였습니다.";
		new SiteNavigation(LECTURE).add(request,"토론방").link(model);

		String returnUrl = "";

		if(("Ajax").equals(pCallGubun)){
			returnUrl = "/jsp/1/course_forum/callback_courseForumJoInfoRegist.jsp";
			return alertAndExit(systemCode, model, msg, returnUrl, LECTURE);
		}else{
			returnUrl = "/CourseForumInfo.cmd?cmd=courseForumInfoPagingList&pCourseId="+pCourseId;
		    return notifyAndExit(systemCode, model, msg, returnUrl, LECTURE);
		}

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
	public ActionForward courseForumInfoFileDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
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
		model.put("pRegMode", pRegMode);
		model.put("pCourseId", pCourseId);
		model.put("pForumId", String.valueOf(pForumId));

		CourseForumInfoDAO courseForum = new CourseForumInfoDAO();
		CourseForumInfoDTO courseForumInfo	= courseForum.getCourseForumInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId);

		String FilePath = FileUtil.FILE_DIR+systemCode+"/course_forum/";
		String SfileName = courseForumInfo.getSfileName();

		int retVal = 0;
		retVal = courseForum.delCourseForumInfoFile(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId);

		if(!courseForumInfo.getSfileName().equals("") && retVal > 0){ //-- 게시판정보 수정 성공하고 첨부 이미지 있는경우
			FileUtil.delFile(FilePath, courseForumInfo.getSfileName());
			log.debug("정보 첨부파일을 삭제하였습니다." + FilePath + courseForumInfo.getSfileName());
		}

		String msg = "삭제하였습니다";
		String returnUrl = "/CourseForumInfo.cmd?cmd=courseForumInfoWrite&pCourseId="+pCourseId+"&pForumId="+pForumId;

		// courseForumMeber  에서 호출시..
		if(("Y").equals(StringUtil.nvl(request.getParameter("pCallJsp")))){
			returnUrl = "/jsp/1/course_forum/callback_courseForumJoInfoRegist.jsp";
			return alertAndExit(systemCode, model, msg, returnUrl, LECTURE);
		}else{
			new SiteNavigation(LECTURE).add(request,"토론방").link(model);
	        return notifyAndExit(systemCode, model, msg, returnUrl, LECTURE);
		}

	}



	/**
	 * 토론방정보 정보 리스트를 페이징 처리 하여 가져온다.(학생과 교수자일 경우 다르게)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumInfoPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String userId	= userMemDto.userId;

	    String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
	    model.put("pCourseId", pCourseId);

		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
		return forward(request, model, "/course_forum/courseForumInfoPagingList.jsp");

	}


	/**
	 * 토론방정보 정보 리스트를 페이징 처리 하여 가져온다.(학생과 교수자일 경우 다르게)
	 * web2.0(ajax) 를 이용..
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO courseForumInfoListAuto(int curPage, String pCurriCode, String pCurriYear, String pCurriTerm, String pCourseId, HttpServletRequest request, HttpServletResponse httpServletResponse ) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String userId	= userMemDto.userId;

	    int iCurriYear = Integer.parseInt(pCurriYear);
	    int iCurriTerm = Integer.parseInt(pCurriTerm);
		// sorting
		//col = StringUtil.nvl(col,"code_so");
		//order = StringUtil.nvl(order,"asc");

		// 페이징
		curPage = (curPage == 0) ? 1 : curPage;

		// 데이타를 담는다.
		ListDTO listObj = null;

		// 리턴 데이타 세팅
		AjaxListDTO ajaxListDto = new AjaxListDTO();
		ArrayList dataList = new ArrayList(1);
		CourseForumInfoDTO  courseForumDto= new CourseForumInfoDTO();

		CourseForumInfoDAO courseForumInfoDao = new CourseForumInfoDAO();
		String pWhere = "";

		if(userType.equals("S")){
			listObj = courseForumInfoDao.getCourseForumInfoJoinCFUserPagingList(curPage,systemCode, pCurriCode, iCurriYear, iCurriTerm, userId, pWhere);
		}else{
			pWhere = " and parent_forum_id = '0' ";

			listObj = courseForumInfoDao.getCourseForumInfoPagingList(curPage,systemCode, pCurriCode, iCurriYear, iCurriTerm, pCourseId, pWhere);
		}

		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				courseForumDto = new CourseForumInfoDTO();
				courseForumDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				courseForumDto.setCurriYear(rs.getInt("curri_year"));
				courseForumDto.setCurriTerm(rs.getInt("curri_term"));
				courseForumDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
				courseForumDto.setCourseName(CommonUtil.getCourseName(systemCode, StringUtil.nvl(rs.getString("course_id"))));
				courseForumDto.setForumId(rs.getInt("forum_id"));
				courseForumDto.setSubject(StringUtil.nvl(rs.getString("subject")));
				courseForumDto.setOpenYn(StringUtil.nvl(rs.getString("open_yn")));
				courseForumDto.setRegistYn(StringUtil.nvl(rs.getString("regist_yn")));
				courseForumDto.setForumType(StringUtil.nvl(rs.getString("forum_type")));
				courseForumDto.setStartDate(DateTimeUtil.getDateType(1,StringUtil.nvl(rs.getString("start_date"))) );
				courseForumDto.setEndDate(DateTimeUtil.getDateType(1,StringUtil.nvl(rs.getString("end_date"))));
				courseForumDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

				if( Integer.parseInt(StringUtil.substring(rs.getString("start_date"),0,8)) <= Integer.parseInt(DateTimeUtil.getDate()) ){
					courseForumDto.setJoinForumYN("Y");
				}else{
					courseForumDto.setJoinForumYN("N");
				}

				if(("S").equals(userType) && ("T").equals(StringUtil.nvl(rs.getString("forum_type")))){
					courseForumDto.setSubForumId(rs.getInt("sub_team_no"));
				}

				dataList.add(courseForumDto);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 데이타 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}
		return ajaxListDto;

	}


	/**
	 * 토론방정보 정보 리스트를 가져온다.(결과)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumResultList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String userId	= userMemDto.userId;

	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));

		CourseForumInfoDAO courseForumInfoDao = new CourseForumInfoDAO();
		CourseForumInfoDTO courseForumInfoList = new CourseForumInfoDTO();

		model.put("CourseForumInfoList", courseForumInfoDao.getCourseForumInfoJoinContentsList(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId));

		model.put("pCourseId", pCourseId);

		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
		return forward(request, model, "/course_forum/courseForumResultList.jsp");
	}




}

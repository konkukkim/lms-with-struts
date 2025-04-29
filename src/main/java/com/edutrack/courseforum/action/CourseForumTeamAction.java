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

import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.courseforum.dao.CourseForumInfoDAO;
import com.edutrack.courseforum.dao.CourseForumTeamDAO;
import com.edutrack.courseforum.dto.CourseForumInfoDTO;
import com.edutrack.courseforum.dto.CourseForumTeamDTO;
import com.edutrack.courseteam.dao.CourseTeamDAO;
import com.edutrack.courseteam.dto.CourseTeamInfoDTO;
import com.edutrack.courseteam.dto.CourseTeamMemberDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.edutrack.user.dto.UsersDTO;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author suna
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CourseForumTeamAction  extends StrutsDispatchAction{


	/**
	 * 팀별 정보 목록을 볼수 있다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumTeamList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		UserMemDTO userMemDto 	= UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String systemCode 		= 	UserBroker.getSystemCode(request);
		String pCurriCode 		= curriMemDto.curriCode;
		String userType 		= userMemDto.userType;
		String userId			= userMemDto.userId;
		int pCurriYear 			= StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm 			= StringUtil.nvl(curriMemDto.curriTerm,0);

		//-- 일반변수들 값을 받아옵니다
		String pCourseId	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		String pForumType	= 	"";
		int pForumId = Integer.parseInt(StringUtil.nvl(request.getParameter("pForumId"),"0"));

		CourseForumInfoDAO courseForum = new CourseForumInfoDAO();
		CourseForumInfoDTO courseForumInfo	= courseForum.getCourseForumInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId);
		if(courseForumInfo!=null){
			pForumType =  courseForumInfo.getForumType() ;
		}

		model.put("courseForumInfo", courseForumInfo);
		model.put("pCourseId", pCourseId);
		model.put("pForumId", String.valueOf(pForumId));
		model.put("curPage", String.valueOf(StringUtil.nvl(request.getParameter("curPage"),1)));
		model.put("pForumType", pForumType );

		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
		return forward(request, model, "/course_forum/courseForumTeamList.jsp");

	}



	/**
	 * 토론 조의 개별 정보 입력/수정 저장..
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumTeamInfoRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model)throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String regId	= userMemDto.userId;

		int pForumId = Integer.parseInt(StringUtil.nvl(request.getParameter("pForumId"),"1"));

		MultipartRequest multipartRequest = null;
		int retVal = 0;
		String FilePath = FileUtil.FILE_DIR+systemCode+"/course_forum/";
		//		  파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath, "courseforum_"+pForumId);
		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();
		String status = uploadEntity.getStatus();

		String pCourseId = StringUtil.nvl(multipartRequest.getParameter("pCourseId"));
		String pRegMode = StringUtil.nvl( multipartRequest.getParameter("pRegMode") );
		String pSeq = StringUtil.nvl( multipartRequest.getParameter("pSeq") );

		CourseForumInfoDAO courseForumInfoDao = new CourseForumInfoDAO();
		// get parent infomation....
		CourseForumInfoDAO 	courseForum 	= new CourseForumInfoDAO();
		CourseForumInfoDTO 	courseForumInfo	= new CourseForumInfoDTO();

		// old file
		String pOldRfileName = "";
		String pOldSfileName = "";
		String pOldFileSize = "";
		String pOldFilePath = "";
		// new file
		String originName = "";
		String rFileName = "";
		String sFileName = "";
		String filePath = "";
		String fileSize = "";
		int pSubTeamNo = StringUtil.nvl(multipartRequest.getParameter("pSubTeamNo"+pSeq),0);
		String pSubTeamName = StringUtil.nvl(multipartRequest.getParameter("pSubTeamName"+pSeq));
		int pParentForumId = StringUtil.nvl(multipartRequest.getParameter("pParentForumId"+pSeq),0);
		String pSubject =  multipartRequest.getParameter("pSubject"+pSeq);
		String pContents =  multipartRequest.getParameter("pContents"+pSeq);
		String pOldForumType = multipartRequest.getParameter("pOldForumType"+pSeq);
		String pOldRegistYn =  multipartRequest.getParameter("pOldRegistYn"+pSeq);
		String pForumType = StringUtil.nvl( multipartRequest.getParameter("pForumType"+pSeq) );


		if(pRegMode.equals("Add"))// 입력모드
		{	courseForumInfo	= courseForum.getCourseForumInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId);
		}

		if(pRegMode.equals("Edit"))// 수정 모드 접근시		파일 삭제 정보 가져오기
		{
			pOldRfileName = StringUtil.nvl(multipartRequest.getParameter("pOldRfileName"+pSeq));
			pOldSfileName = StringUtil.nvl(multipartRequest.getParameter("pOldSfileName"+pSeq));
			pOldFileSize = StringUtil.nvl(multipartRequest.getParameter("pOldFileSize"+pSeq));
			pOldFilePath = StringUtil.nvl(multipartRequest.getParameter("pOldFilePath"+pSeq));
		}

		if (status.equals("E")){
			log.debug("첨부 파일 올리려다 실패하였습니다.");
		}else if (status.equals("O")){
			log.debug("첨부하신 파일이 용량을 초과했습니다.");
		}else if (status.equals("I")){
			log.debug("첨부 파일의 정보가 잘못되었습니다.");
		}else if(status.equals("S")){
			// 업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
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
				courseForumInfo.setRfileName(rFileName);
				courseForumInfo.setSfileName(sFileName);
				courseForumInfo.setFileSize(fileSize);
				courseForumInfo.setFilePath(filePath); // FilePath가 맞는 듯 함.

				if(!pOldSfileName.equals("")) {		//이전 첨부파일을 삭제할 경우
					FileUtil.delFile(FilePath, pOldSfileName);
				}
			}else{									// 첨부 안한 경우
				courseForumInfo.setRfileName(pOldRfileName);
				courseForumInfo.setSfileName(pOldSfileName);
				courseForumInfo.setFileSize(pOldFileSize);
				courseForumInfo.setFilePath(pOldFilePath);
			}
		}


		// 상위의 토론 아이디를 세팅한다.
		courseForumInfo.setParentForumId(pParentForumId);
		courseForumInfo.setRegId(regId);
		courseForumInfo.setModId(regId);
		courseForumInfo.setForumType(pForumType);

		// pk
		courseForumInfo.setSystemCode(systemCode);
		courseForumInfo.setCurriCode(pCurriCode);
		courseForumInfo.setCurriYear(pCurriYear);
		courseForumInfo.setCurriTerm(pCurriTerm);
		courseForumInfo.setCourseId(pCourseId);
		courseForumInfo.setForumId(pForumId);
		// team info
		courseForumInfo.setSubTeamNo(pSubTeamNo);
		courseForumInfo.setSubTeamName(pSubTeamName);
		courseForumInfo.setSubject(pSubject);
		courseForumInfo.setContents(pContents);

		String msg = "";
		String returnUrl = "/jsp/1/course_forum/callback_courseForumJoInfoRegist.jsp";

		CourseForumTeamDAO courseForumTeamDao = new CourseForumTeamDAO();
		CourseTeamInfoDTO courseTeamInfoDto = new CourseTeamInfoDTO();
		ArrayList arrayList = new ArrayList();

		if(pRegMode.equals("Add"))// 입력모드
		{
			courseForumInfo.setParentForumId(pForumId);
			// team
			courseTeamInfoDto.setTeamNo(pSubTeamNo);
			courseTeamInfoDto.setTeamName(pSubTeamName);
			arrayList.add(courseTeamInfoDto);

			boolean bRetVal = courseForumTeamDao.addCourseForumSubTeamInfoAuto(arrayList, courseForumInfo, null);

			msg = "성공적으로 등록하였습니다";
			if(!bRetVal){
				msg = "등록오류 다시 진행해 주세요";
			}
		}else{
			courseForumInfo.setModId(regId);
			retVal = courseForumTeamDao.editCourseForumTeamInfo(courseForumInfo);
			msg = "성공적으로 수정하였습니다";
			if(retVal <= 0){
				//returnUrl = "/CourseForumInfo.cmd?cmd=courseForumInfoEdit&pCourseId="+pCourseId+"&pForumId="+pForumId;
				msg = "수정오류 다시 진행해 주세요";
			}
		}


		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
		return alertAndExit(systemCode, model, msg, returnUrl, LECTURE);
		//return alertPopupCloseResponse(systemCode, model, msg, LECTURE);
	}


	/**
	 * 팀을 자동생성하는 팝업을 오픈한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumTeamAutoWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		//-- 일반변수들 값을 받아옵니다
		String pCourseId	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		//int subTeamNo = Integer.parseInt(StringUtil.nvl(request.getParameter("pSubTeamNo"),"0"));

		model.put("pCourseId", pCourseId);
		model.put("pForumId", request.getParameter("pForumId"));
		//model.put("pParentForumId", request.getParameter("pForumId"));

		new SiteNavigation(LECTURE).add(request,"팀자동생성").link(model);
		return forward(request, model, "/course_forum/courseForumTeamAutoWrite.jsp");
	}

	/**
	 * 팀을 자동생성한다.
	 * @param courseId
	 * @param teamNoStr
	 * @param selectedStuId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumTeamAutoRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {

		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String curriCode = StringUtil.nvl(curriMemDto.curriCode);
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String regId	= userMemDto.userId;

		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));

		int pForumId = StringUtil.nvl(request.getParameter("pForumId"),0); // parent_forum_id
		CourseForumTeamDAO courseForumTeamDao = new CourseForumTeamDAO();
		CourseForumInfoDAO courseForumInfoDao = new CourseForumInfoDAO();
		CourseForumInfoDTO courseForumInfoDto = new CourseForumInfoDTO();
		//	  리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		CourseTeamDAO courseTeamDao	= new CourseTeamDAO();

		courseForumInfoDto = courseForumInfoDao.getCourseForumInfo(systemCode, curriCode, curriYear, curriTerm, pCourseId, pForumId ) ;

		// 상위의 토론 아이디를 세팅한다.
		courseForumInfoDto.setParentForumId(pForumId);
		courseForumInfoDto.setRegId(regId);

		// 템플릿 토론팀 정보를 가져온다.
		String orderBy = " order by team_no asc" ;
		arrayList = courseTeamDao.getCourseTeamList(systemCode, curriCode, curriYear, curriTerm, pCourseId, orderBy);


		// file 처리 필요..
		String[] targetSfileName = new String[arrayList.size()];
		if(!("").equals(courseForumInfoDto.getRfileName())){
			int vForumId = pForumId;
			String FilePath = FileUtil.FILE_DIR+systemCode+"/course_forum/";
			// 토론팀의 수만큼 첨부파일을 카피한다
			for(int i=0;i<arrayList.size();i++){
				targetSfileName[i] = "copy_"+ (vForumId+i+1) +"_"+courseForumInfoDto.getSfileName();
				FileUtil.fileCopy(systemCode, FilePath, FilePath, courseForumInfoDto.getSfileName(), targetSfileName[i]) ;
			}
		}
		courseForumInfoDto.setSfileName2(targetSfileName);


		// 토론팀을 등록한다..
		boolean retVal = courseForumTeamDao.addCourseForumSubTeamInfoAuto(arrayList, courseForumInfoDto,"auto");

//		 토론팀 멤버를 등록한다..
		int retVal2 = courseForumTeamDao.addCourseForumSubTeamMemberAuto(courseForumInfoDto);


		String msg = "";
		String returnUrl = "";
		if(	retVal ){
			msg = "토론 팀을 성공적으로 구성하였습니다.";
		}

		if(	retVal2>0 ){
			msg = "토론 팀 멤버를  성공적으로 구성하였습니다.";
		}

		new SiteNavigation(LECTURE).add(request,"토론방").link(model);
//        return closePopupReload(systemCode, model, msg, "", LECTURE);
		return alertAndExit(systemCode, model, msg, "/jsp/1/course_forum/callback_courseForumJoInfoRegist.jsp", LECTURE);

	}

	/**
	 * 토론 팀구성원 리스트 페이지로 이동을 합니다
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseForumTeamMemList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		//-- 일반변수들 값을 받아옵니다
		String pCourseId	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		//int subTeamNo = Integer.parseInt(StringUtil.nvl(request.getParameter("pSubTeamNo"),"0"));

		model.put("pCourseId", pCourseId);
		model.put("pForumId", request.getParameter("pForumId"));
		model.put("pParentForumId", request.getParameter("pParentForumId"));

		new SiteNavigation(LECTURE).add(request,"팀관리").link(model);
		return forward(request, model, "/course_forum/courseForumTeamMemberList.jsp");
	}

	/**
	 * 토론 팀 리스트 (ajax)..select box
	 * @param courseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList courseForumTeamListAuto(String courseId, int pParentForumId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		courseId = StringUtil.nvl(courseId);

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String curriCode = curriMemDto.curriCode;
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		// 리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		CourseForumInfoDAO	courseForumInfoDao	=	new	CourseForumInfoDAO();

		String pWhere = " and a.parent_forum_id = '" + pParentForumId + "' ";
		String pOrderby	= " a.sub_team_no asc ";

		arrayList = courseForumInfoDao.getCourseForumInfoList(systemCode, curriCode, curriYear, curriTerm, courseId, pWhere, pOrderby);

		return arrayList;
	}

	/**
	 * 팀 대기자 명단을 보여줍니다(Ajax)
	 * 2007.05.22 sangsang
	 * @param courseId
	 * @param column
	 * @param orderBy
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList courseForumStudentList(String courseId, int parentForumId, String column, String orderBy, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		courseId = StringUtil.nvl(courseId);
		column = StringUtil.nvl(column);
		orderBy = StringUtil.nvl(orderBy);

		if(column.equals("name"))
			column = "u.user_name";
		else if(column.equals("id"))
			column = "s.user_id";

		if(orderBy.equals("a"))
			orderBy = "asc";
		else if(orderBy.equals("d"))
			orderBy = "desc";

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String curriCode = curriMemDto.curriCode;
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		// 리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		CourseForumTeamDAO courseForumTeamDao	=	new CourseForumTeamDAO();
		UsersDTO usersDto = null;
		RowSet rs = courseForumTeamDao.getCourseForumStudentList(systemCode,curriCode, curriYear, curriTerm, courseId, parentForumId, column, orderBy);
		while(rs.next()){
			usersDto	= new UsersDTO();
			usersDto.setUserId(rs.getString("user_id"));
			usersDto.setUserName(StringUtil.nvl(rs.getString("user_name")));

			arrayList.add(usersDto);
		}
		rs.close();
		return arrayList;
	}



	/**
	 * 팀별 팀원명단을 보여줍니다(Ajax)
	 * @param courseId
	 * @param teamNoStr
	 * @param column
	 * @param orderBy
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList courseForumTeamMemberList(String courseId, int forumId, String column, String orderBy, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		courseId = StringUtil.nvl(courseId);
		column = StringUtil.nvl(column);
		orderBy = StringUtil.nvl(orderBy);

		if(column.equals("name"))
			column = "u.user_name";
		else if(column.equals("id"))
			column = "m.user_id";

		if(orderBy.equals("a"))
			orderBy = "asc";
		else if(orderBy.equals("d"))
			orderBy = "desc";

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String curriCode = curriMemDto.curriCode;
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		// 리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		CourseForumTeamDAO courseForumTeamDao	=	new CourseForumTeamDAO();
		CourseTeamMemberDTO courseTeamMemberDto = null;

		RowSet rs = courseForumTeamDao.getCourseForumTeamMemberList(systemCode,curriCode, curriYear, curriTerm, courseId, forumId, column, orderBy);
		while(rs.next()){
			courseTeamMemberDto	= new CourseTeamMemberDTO();
			courseTeamMemberDto.setUserId(rs.getString("user_id"));
			courseTeamMemberDto.setUserName(StringUtil.nvl(rs.getString("user_name")));

			arrayList.add(courseTeamMemberDto);
		}
		rs.close();
		return arrayList;
	}



	/**
	 * 팀 멤버 등록/삭제를 합니다.(Ajax)
	 * 2007.05.22 sangsang
	 * @param courseId
	 * @param teamNoStr
	 * @param selectedStuId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public boolean courseForumTeamMemberRegist(String courseId, int forumId, String[] selectedStuId, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		courseId = StringUtil.nvl(courseId);

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String curriCode = curriMemDto.curriCode;
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		boolean retVal = false;

		CourseForumTeamDAO courseForumTeamDao	=	new CourseForumTeamDAO();
		int ret = 0;

		if(selectedStuId != null && selectedStuId.length > 0){

			// 기존 팀구성원 삭제
			ret = courseForumTeamDao.deleteCourseForumTeamMember(systemCode, curriCode, curriYear, curriTerm, courseId, forumId );

			CourseForumTeamDTO courseForumTeamDto	=	new CourseForumTeamDTO();
			courseForumTeamDto.setSystemCode(systemCode);
			courseForumTeamDto.setCurriCode(curriCode);
			courseForumTeamDto.setCurriYear(curriYear);
			courseForumTeamDto.setCurriTerm(curriTerm);
			courseForumTeamDto.setCourseId(courseId);
			courseForumTeamDto.setForumId(forumId);
			courseForumTeamDto.setSubTeamNo(0);
			courseForumTeamDto.setRegId(userId);
			courseForumTeamDto.setChkId(selectedStuId);

			retVal = courseForumTeamDao.addCourseForumTeamMember(courseForumTeamDto);
		}

		if(retVal == false){
			if(ret > 0)
				retVal = true;
		}

	    return retVal;
	}
}

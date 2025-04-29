package com.edutrack.curritop.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.curritop.dao.CourseDAO;
import com.edutrack.curritop.dto.CourseDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;

/*
 * @author sangsang
 *
 * 과목 관리
 */

public class CourseAction extends StrutsDispatchAction{
	/**
	 *
	 */
	public CourseAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 과목의 파일저장경로를 가져온다.
	 * @param systemCode
	 * @return FilePath
	 */
	public String getFilePath(String systemCode){
		String FilePath = "";
		FilePath = FileUtil.IMG_DIR+systemCode+"/course/";
		return FilePath;
	}

    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();


	/**
	 * 과목 리스트 페이지로 이동한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		new SiteNavigation(pMode).add(request,"과목관리").link(model);
		return forward(request, model, "/course/courseList.jsp?pMode="+pMode);
	}

	/**
	 * 과목리스트를 보여준다(Ajax)
	 * 2007.05.18 sangsang
	 * @param curPage
	 * @param pSTarget
	 * @param pSWord
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO courseListAuto(int curPage, String pSTarget, String pSWord, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		// sorting
		pSTarget = StringUtil.nvl(pSTarget);
		pSWord = AjaxUtil.ajaxDecoding(StringUtil.nvl(pSWord));

		// 페이징
		curPage = (curPage == 0) ? 1 : curPage;

		// 데이타를 담는다.
		ListDTO listObj = null;
		CourseDAO courseDao = new CourseDAO();
		listObj = courseDao.getCourseList(curPage,systemCode, pSTarget, pSWord);

		// 리턴 데이타 세팅
		AjaxListDTO ajaxListDto = new AjaxListDTO();
		ArrayList dataList = new ArrayList();
		CourseDTO courseDto = null;

		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				courseDto = new CourseDTO();
				courseDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
				courseDto.setCourseName(StringUtil.nvl(rs.getString("course_name")));
				courseDto.setProfId(StringUtil.nvl(rs.getString("prof_id")));
				courseDto.setFlagUse(StringUtil.nvl(rs.getString("flag_use")));
				courseDto.setFlagUseName(StringUtil.nvl(rs.getString("flag_use_name")));
				courseDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				courseDto.setOnlineYn(StringUtil.nvl(rs.getString("online_yn")));
				
				dataList.add(courseDto);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}
		return ajaxListDto;
	}

	/**
	 * 과목 검색 리스트를 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseSearchList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		String CONTEXTPATH = request.getContextPath();
		CourseDAO courseDao = new CourseDAO();

		//---- 검색 설정
		String pSTarget = StringUtil.nvl(request.getParameter("pSTarget"),"");
		String pSWord = StringUtil.nvl(request.getParameter("pSWord"));
		String pCurriCode = StringUtil.nvl(request.getParameter("pCurriCode"));
		String pCurriProperty2 = StringUtil.nvl(request.getParameter("pCurriProperty2"));
		int pCurriYear = Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriYear"),"0"));
		int pCurriTerm = Integer.parseInt(StringUtil.nvl(request.getParameter("pCurriTerm"),"0"));
		String pTarget = StringUtil.nvl(request.getParameter("pTarget"),"curriTop");
		String selCourseLink = "";
		int totCnt = 0;
		totCnt = courseDao.getTotCount(systemCode);
		RowSet Rs = courseDao.getCourseListAll(systemCode, pCurriCode, pCurriYear, pCurriTerm, pSTarget,pSWord);
		model.put("totCnt", String.valueOf(totCnt));
		model.put("courseList", Rs);
		model.put("pCurriCode", pCurriCode);
		model.put("pCurriTerm", pCurriTerm);
		model.put("pCurriProperty2", pCurriProperty2);
		model.put("pSWord", pSWord);
		model.put("pTarget", pTarget);
		if(pTarget.equals("curriTop")) {
			selCourseLink = CONTEXTPATH+"/CurriTopCourse.cmd?cmd=curriTopCourseWrite&pCurriCode="+pCurriCode;
		} else {
			selCourseLink = CONTEXTPATH+"/CurriSubCourse.cmd?cmd=curriSubCourseWrite&pCurriCode="+pCurriCode+"&pCurriYear="+pCurriYear+"&pCurriTerm="+pCurriTerm;
		}
		model.put("selCourseLink",selCourseLink);
		return forward(request, model, "/course/courseSearchList.jsp");
	}

	/**
	 * 과목 정보를 저장한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		CourseDAO courseDao = new CourseDAO();
		CourseDTO courseDto = new CourseDTO();

		MultipartRequest multipartRequest = null;
		int retVal = 0;
		String objName = "";
		String uploadFileName = "";
		String uploadFilePath = "";

		String FilePath = getFilePath(systemCode);

//		파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath, "courseImg_");
// 		파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();

		String regMode = StringUtil.nvl(multipartRequest.getParameter("pRegMode"),"Add");
		String pCourseId = StringUtil.nvl(multipartRequest.getParameter("pCourseId"));
		String pCourseName = StringUtil.nvl(multipartRequest.getParameter("pCourseName"));
		String pProfId = StringUtil.nvl(multipartRequest.getParameter("pProfId"));
		String pContentsWidth = StringUtil.nvl(multipartRequest.getParameter("pContentsWidth"));
		String pContentsHeight = StringUtil.nvl(multipartRequest.getParameter("pContentsHeight"));
		String pContentsType = StringUtil.nvl(multipartRequest.getParameter("pContentsType"));
		String pOnlineYn = StringUtil.nvl(multipartRequest.getParameter("pOnlineYn"));
		String pFlagUse = StringUtil.nvl(multipartRequest.getParameter("pFlagUse"));
		String pFlagNavi = StringUtil.nvl(multipartRequest.getParameter("pFlagNavi"));

		String status = uploadEntity.getStatus();
		uploadFilePath = uploadEntity.getUploadPath();
		if (status.equals("E")){
			log.debug("첨부 파일 올리려다 실패하였습니다.");
		}else if (status.equals("O")){
			log.debug("첨부하신 파일이 용량을 초과했습니다.");
		}else if (status.equals("I")){
			log.debug("첨부 파일의 정보가 잘못되었습니다.");
		}else if(status.equals("S")){
//			 업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			log.debug("첨부 파일을 첨부하는데 성공하였습니다.");
			ArrayList files = uploadEntity.getFiles();
			UploadFile file = null;
			for(int i = 0 ; i < files.size(); i++){
				file = (UploadFile)files.get(i);
				objName = file.getObjName();
				uploadFileName = StringUtil.nvl(file.getUploadName());
				if(objName.indexOf("pTitleImg[")>=0){
					int idx = Integer.parseInt(String.valueOf(objName.charAt(10)));
					if(idx == 1){
						courseDto.setCourseImg1(uploadFileName);
					}else if(idx == 2){
						courseDto.setCourseImg2(uploadFileName);
					}
				}
			}
		}
		courseDto.setSystemCode(systemCode);
		courseDto.setCourseId(pCourseId);
		courseDto.setCourseName(pCourseName);
		courseDto.setProfId(pProfId);
		courseDto.setContentsWidth(pContentsWidth);
		courseDto.setContentsHeight(pContentsHeight);
		courseDto.setContentsType(pContentsType);
		courseDto.setOnlineYn(pOnlineYn);
		courseDto.setFlagUse(pFlagUse);
		courseDto.setFlagNavi(pFlagNavi);
		courseDto.setRegId(userId);

		String msg = "";
		String returnUrl = "/Course.cmd?cmd=courseList";
		new SiteNavigation(MYPAGE).add(request,"과목관리").link(model);

		if(regMode.equals("Add"))// 입력모드
		{
			retVal = courseDao.addCourseInfo(courseDto);
			msg = "등록완료";

			if(retVal > 0){
				//-- 등록 성공시 /contents/과목폴더/parameters.xml 을 생성한다.
				String contentsDir =  FileUtil.UPLOAD_PATH + FileUtil.CONTENTS_DIR+systemCode+"/"+pCourseId;
				StringBuffer sb = new StringBuffer();
				sb.append("<?xml version=\"1.0\"?>");
				sb.append("<parameters>");
				sb.append("		<parameter default=\"CourseID\">");
				sb.append("			<CourseID>course_id</CourseID>");
				sb.append("		</parameter>");
				sb.append("		<etc>");
				sb.append("			<Repository>"+contentsDir+"/</Repository>");
				sb.append("			<FileDelimiter>/</FileDelimiter>");
				sb.append("		</etc>");
				sb.append("</parameters>");
				String paramFile = sb.toString();
				FileUtil.createFile(contentsDir,"parameters.xml",paramFile);
				//-- 생성 종료

				return redirect(returnUrl);
			}else{
				msg = "등록오류 다시 진행해 주세요";
				return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
			}

		}else if(regMode.equals("Edit")){
			retVal = courseDao.editCourseInfo(courseDto);
			if(retVal > 0){
				return redirect(returnUrl);
			}else{
				msg = "수정오류 다시 진행해 주세요";
				return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
			}
		}else
			return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
	}

	/**
	 * 과목정보를 받는다(Ajax)
	 * 2007.05.08 sangsang
	 * @param courseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public CourseDTO courseInfo(String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		CourseDTO courseDto = new CourseDTO();
		CourseDAO courseDao = new CourseDAO();

		RowSet rs = courseDao.getCourseInfo(systemCode, courseId);
		if(rs.next()){
			courseDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			courseDto.setCourseName(StringUtil.nvl(rs.getString("course_name")));
			courseDto.setProfId(StringUtil.nvl(rs.getString("prof_id")));
			courseDto.setFlagUse(StringUtil.nvl(rs.getString("flag_use")));
			courseDto.setFlagNavi(StringUtil.nvl(rs.getString("flag_navi")));
			courseDto.setContentsWidth(StringUtil.nvl(rs.getString("contents_width")));
			courseDto.setContentsHeight(StringUtil.nvl(rs.getString("contents_height")));
			courseDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
			courseDto.setCourseImg1(StringUtil.nvl(rs.getString("course_img1")));
			courseDto.setCourseImg2(StringUtil.nvl(rs.getString("course_img2")));
			courseDto.setOnlineYn(StringUtil.nvl(rs.getString("online_yn")));
		}
		rs.close();

		return courseDto;
	}

	/**
	 * 입력시점에서 과목코드 중복 체크(Ajax)
	 * 2007.05.08 sangsang
	 * @param courseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String courseIdCheck(String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);
		CourseDAO courseDao = new CourseDAO();
		int retVal = courseDao.getTotCount(systemCode, courseId);

		return  String.valueOf(retVal);
	}

	/**
	 * 코스이미지 개별삭제(Ajax)
	 * 2007.05.08 sangsang
	 * @param courseId
	 * @param titleImgPos
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String courseImgDelete(String courseId, String titleImgPos, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);

		String deleteImgColumn = "";
		if(titleImgPos.equals("1"))
			deleteImgColumn = "course_img1";
		else if(titleImgPos.equals("2"))
			deleteImgColumn = "course_img2";

		CourseDAO courseDao = new CourseDAO();
		String courseImgFileName = StringUtil.nvl(courseDao.getColumnData(systemCode, courseId, deleteImgColumn));

		//---- 파일이 있는경우 파일 삭제
		int result=0;
		String FilePath = getFilePath(systemCode);
		if(!courseImgFileName.equals("")){
			FileUtil.delFile(FilePath, courseImgFileName);
			result = courseDao.columnUpdate(systemCode, courseId, deleteImgColumn, "");
		}

	    return  titleImgPos;
	}

	/**
	 * 과목 정보를 삭제한다.(Ajax)
	 * 2007.05.08 sangsang
	 * @param courseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String courseDelete(String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);

		int retVal = 0;
		RowSet rs = null;
		String courseImg1 = "";
		String courseImg2 = "";
		CourseDAO courseDao = new CourseDAO();
		rs = courseDao.getCourseInfo(systemCode, courseId);
		if(rs.next()){
			courseImg1 = StringUtil.nvl(rs.getString("course_img1"));
			courseImg2 = StringUtil.nvl(rs.getString("course_img2"));
		}
		rs.close();

		retVal = courseDao.delCourseInfo(systemCode, courseId);		// 코스정보삭제
		if(retVal > 0){ //---- 디비정보 삭제 성공했을 경우
			//---- 파일이 있는경우 파일 삭제
			String FilePath = getFilePath(systemCode);
			if(!courseImg1.equals(""))
				FileUtil.delFile(FilePath, courseImg1);
			if(!courseImg2.equals(""))
				FileUtil.delFile(FilePath, courseImg2);
		}
		return  String.valueOf(retVal);
	}
}
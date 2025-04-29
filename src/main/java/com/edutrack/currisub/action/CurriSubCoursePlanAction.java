package com.edutrack.currisub.action;

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
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.currisub.dao.CurriSubCourseDAO;
import com.edutrack.currisub.dao.CurriSubCoursePlanDAO;
import com.edutrack.currisub.dto.CurriSubCoursePlanDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.edutrack.user.dao.UserAdminDAO;
import com.edutrack.user.dto.ProfInfoDTO;
import com.oreilly.servlet.MultipartRequest;

/*
 * @author Jamfam
 *
 * 과정 과목 강의 계획서  관리
 */

public class CurriSubCoursePlanAction extends StrutsDispatchAction{
	/**
	 *
	 */
	public CurriSubCoursePlanAction() {
		super();
		// TODO Auto-generated constructor stub
	}

    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	/**
	 * 강의계획서 파일 첨부하는 팝업창 띄우기
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward openCoursePlanFilePopup(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		model.put("pCourseId", pCourseId);

		new SiteNavigation(LECTURE).add(request,"강의계획서").link(model);
		return forward(request, model, "/curri_sub/openCoursePlanFilePopup.jsp");
	}

	/**
	 * 강의계획서 파일 첨부 등록
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward coursePlanFileRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String curriCode = curriMemDto.curriCode;
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		CurriSubCoursePlanDTO	coursePlanDto	=	new CurriSubCoursePlanDTO();
		MultipartRequest multipartRequest = null;
		int retVal = 0;
		String objName = "";
		String rFileName = "";
		String sFileName = "";
		String filePath = "";
		String fileSize = "";

		String FilePath = FileUtil.FILE_DIR+systemCode+"/curriSubCoursePlan/"+curriCode+"/"+curriYear+"/"+curriTerm+"/"+pCourseId;//-- 과정코드+개설년+개설학기+과목코드
		// 파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath, regId);

		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();
		String status = uploadEntity.getStatus();

		String callBackMethod = StringUtil.nvl(multipartRequest.getParameter("callBackMethod"));
		String target = StringUtil.nvl(multipartRequest.getParameter("target"));

		String pOldrFileName = StringUtil.nvl(multipartRequest.getParameter("pOldrFileName"));
		String pOldsFileName = StringUtil.nvl(multipartRequest.getParameter("pOldsFileName"));
		String pOldFilePath = StringUtil.nvl(multipartRequest.getParameter("pOldFilePath"));
		String pOldFileSize = StringUtil.nvl(multipartRequest.getParameter("pOldFileSize"));

		coursePlanDto.setRFileName(pOldrFileName);
		coursePlanDto.setSFileName(pOldsFileName);
		coursePlanDto.setFilePath(pOldFilePath);
		coursePlanDto.setFilesize(pOldFileSize);

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
				objName = file.getObjName();
				rFileName = StringUtil.nvl(file.getRootName());
				if(!rFileName.equals("")) {
					sFileName = file.getUploadName();
					fileSize = String.valueOf(file.getSize());
					filePath = uploadEntity.getUploadPath();
				}
				coursePlanDto.setRFileName(rFileName);	coursePlanDto.setSFileName(sFileName);
				coursePlanDto.setFilePath(filePath);	coursePlanDto.setFilesize(fileSize);

				if(!pOldsFileName.equals("")) {		//이전 첨부파일을 삭제할 경우
					log.debug("이전 파일 삭제하기"+pOldFilePath+pOldsFileName);
					FileUtil.delFile(pOldFilePath, pOldsFileName);
				}
			}// For End
		}

		coursePlanDto.setModId(regId);
		coursePlanDto.setRegId(regId);
		coursePlanDto.setSystemCode(systemCode);
		coursePlanDto.setCurriCode(curriCode);
		coursePlanDto.setCurriYear(curriYear);
		coursePlanDto.setCurriTerm(curriTerm);
		coursePlanDto.setCourseId(pCourseId);

		String	msg	=	"강의계획서 업로드를 하지 못했습니다.";
		String	returnUrl = "/CurriSubCoursePlan.cmd?cmd=openCoursePlanFilePopup&pCourseId="+pCourseId;
		CurriSubCoursePlanDAO curriSubCoursePlanDao = new CurriSubCoursePlanDAO();
		// 등록되어있는 강의계획서 수를 받아서 등록/수정 여부를 파악한다.
	    int curriSubCoursePlanCnt = curriSubCoursePlanDao.getCurriSubCoursePlanCount(systemCode, curriCode, curriYear, curriTerm, pCourseId);

	    if(curriSubCoursePlanCnt > 0){	// 수정
			retVal = curriSubCoursePlanDao.editCurriSubCoursePlanFile(coursePlanDto);
		}else{		// 등록
			retVal = curriSubCoursePlanDao.addCurriSubCoursePlanFile(coursePlanDto);
		}
	    if(retVal > 0) {
			msg	=	"강의계획서를 업로드 하였습니다.";
			returnUrl = CALLBACKURL+callBackMethod;
		}

	    return alertAndExit(systemCode, model, msg, returnUrl,"POPUP");
	}

	/**
	 * [AJAX] 첨부파일을 삭제한다.
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String planFileDelete(String curriCode, int curriYear, int curriTerm, String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String	systemCode	=	UserBroker.getSystemCode(request);
		int		retVal		=	0;

		CurriSubCoursePlanDAO planDao = new CurriSubCoursePlanDAO();
		RowSet rs = planDao.getCurriSubCoursePlan(systemCode, curriCode, curriYear, curriTerm, courseId);
		String	rFileName	=	"";
		String	sFileName	=	"";
		String	filePath	=	"";

		if(rs.next()) {
			rFileName	=	StringUtil.nvl(rs.getString("rfile_name"));
			sFileName	=	StringUtil.nvl(rs.getString("sfile_name"));
			filePath	=	StringUtil.nvl(rs.getString("file_path"));
		}
		rs.close();

		retVal	=	planDao.planFileDelete(systemCode, curriCode, curriYear, curriTerm);
		if(retVal > 0) {
			if(!rFileName.equals("") && !rFileName.equals("") && !filePath.equals("")){
				FileUtil.delFile(filePath, sFileName);
				log.debug(" 첨부파일을 삭제하였습니다."+filePath+sFileName);
				log.debug("+++++++++++++++++++ 첨부파일을 삭제하였습니다 = 2");
			}
		}

		return String.valueOf(retVal);
	}

	/**
	 * 과목 강의 계획서 정보 페이지로 이동한다..
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriSubCoursePlanShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		model.put("pCourseId", pCourseId);

		new SiteNavigation(LECTURE).add(request,"강의계획서").link(model);
		return forward(request, model, "/curri_sub/curriSubCoursePlanShow.jsp");
	}

	/**
	 * 과목 강의 계획서 정보를 보여준다(Ajax)
	 * 2007.06.12 sangsang
	 * @param courseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public CurriSubCoursePlanDTO curriSubCoursePlanShowAuto(String courseId, String viewType, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String	systemCode		=	UserBroker.getSystemCode(request);
		String	userType		=	UserBroker.getUserType(request);
		courseId = StringUtil.nvl(courseId);

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String curriCode = curriMemDto.curriCode;
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		// 리턴 데이타 세팅
		CurriSubCoursePlanDAO curriSubCoursePlanDao = new CurriSubCoursePlanDAO();
		CurriSubCoursePlanDTO curriSubCoursePlanDto	= null;

		RowSet rs = curriSubCoursePlanDao.getCurriSubCoursePlan(systemCode, curriCode, curriYear, curriTerm, courseId);
		if(rs.next()){
			curriSubCoursePlanDto	= new CurriSubCoursePlanDTO();

			curriSubCoursePlanDto.setSystemCode(systemCode);
			curriSubCoursePlanDto.setCurriCode(curriCode);
			curriSubCoursePlanDto.setCurriYear(curriYear);
			curriSubCoursePlanDto.setCurriTerm(curriTerm);
			curriSubCoursePlanDto.setCourseId(courseId);
			curriSubCoursePlanDto.setModDate(userType);		//사용자권한 넘긴다. 파일삭제권한을 조정하기 위해서

			curriSubCoursePlanDto.setInfoTitle1(StringUtil.nvl(rs.getString("info_title_1")));
			curriSubCoursePlanDto.setInfoTitle2(StringUtil.nvl(rs.getString("info_title_2")));
			curriSubCoursePlanDto.setInfoTitle3(StringUtil.nvl(rs.getString("info_title_3")));
			curriSubCoursePlanDto.setInfoTitle4(StringUtil.nvl(rs.getString("info_title_4")));
			curriSubCoursePlanDto.setInfoTitle5(StringUtil.nvl(rs.getString("info_title_5")));
			if(viewType.equals("SHOW")){
				curriSubCoursePlanDto.setInfoText1(StringUtil.getHtmlContents(StringUtil.nvl(rs.getString("info_text_1"))));
				curriSubCoursePlanDto.setInfoText2(StringUtil.getHtmlContents(StringUtil.nvl(rs.getString("info_text_2"))));
				curriSubCoursePlanDto.setInfoText3(StringUtil.getHtmlContents(StringUtil.nvl(rs.getString("info_text_3"))));
				curriSubCoursePlanDto.setInfoText4(StringUtil.getHtmlContents(StringUtil.nvl(rs.getString("info_text_4"))));
				curriSubCoursePlanDto.setInfoText5(StringUtil.getHtmlContents(StringUtil.nvl(rs.getString("info_text_5"))));
			}else{
				curriSubCoursePlanDto.setInfoText1(StringUtil.nvl(rs.getString("info_text_1")));
				curriSubCoursePlanDto.setInfoText2(StringUtil.nvl(rs.getString("info_text_2")));
				curriSubCoursePlanDto.setInfoText3(StringUtil.nvl(rs.getString("info_text_3")));
				curriSubCoursePlanDto.setInfoText4(StringUtil.nvl(rs.getString("info_text_4")));
				curriSubCoursePlanDto.setInfoText5(StringUtil.nvl(rs.getString("info_text_5")));
			}
			curriSubCoursePlanDto.setRFileName(StringUtil.nvl(rs.getString("rfile_name")));
			curriSubCoursePlanDto.setSFileName(StringUtil.nvl(rs.getString("sfile_name")));
			curriSubCoursePlanDto.setFilePath(StringUtil.nvl(rs.getString("file_path")));
			curriSubCoursePlanDto.setFilesize(StringUtil.nvl(rs.getString("file_size")));
		}
		rs.close();

		return curriSubCoursePlanDto;
	}

	/**
	 * 과목 강의 계획서 정보를 저장한다(Ajax)
	 * 2007.06.12 sangsang
	 * @param curriSubCoursePlanDto
	 * @param courseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String curriSubCoursePlanRegist(CurriSubCoursePlanDTO curriSubCoursePlanDto, String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		courseId = StringUtil.nvl(courseId);

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String curriCode = curriMemDto.curriCode;
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		String infoTitle1 = AjaxUtil.ajaxDecoding(curriSubCoursePlanDto.getInfoTitle1());
		String infoText1 = AjaxUtil.ajaxDecoding(curriSubCoursePlanDto.getInfoText1());
		String infoTitle2 = AjaxUtil.ajaxDecoding(curriSubCoursePlanDto.getInfoTitle2());
		String infoText2 = AjaxUtil.ajaxDecoding(curriSubCoursePlanDto.getInfoText2());
		String infoTitle3 = AjaxUtil.ajaxDecoding(curriSubCoursePlanDto.getInfoTitle3());
		String infoText3 = AjaxUtil.ajaxDecoding(curriSubCoursePlanDto.getInfoText3());
		String infoTitle4 = AjaxUtil.ajaxDecoding(curriSubCoursePlanDto.getInfoTitle4());
		String infoText4 = AjaxUtil.ajaxDecoding(curriSubCoursePlanDto.getInfoText4());
		String infoTitle5 = AjaxUtil.ajaxDecoding(curriSubCoursePlanDto.getInfoTitle5());
		String infoText5 = AjaxUtil.ajaxDecoding(curriSubCoursePlanDto.getInfoText5());

		curriSubCoursePlanDto.setSystemCode(systemCode);
		curriSubCoursePlanDto.setCurriCode(curriCode);
		curriSubCoursePlanDto.setCurriYear(curriYear);
		curriSubCoursePlanDto.setCurriTerm(curriTerm);
		curriSubCoursePlanDto.setCourseId(courseId);
		curriSubCoursePlanDto.setInfoTitle1(infoTitle1);
		curriSubCoursePlanDto.setInfoText1(infoText1);
		curriSubCoursePlanDto.setInfoTitle2(infoTitle2);
		curriSubCoursePlanDto.setInfoText2(infoText2);
		curriSubCoursePlanDto.setInfoTitle3(infoTitle3);
		curriSubCoursePlanDto.setInfoText3(infoText3);
		curriSubCoursePlanDto.setInfoTitle4(infoTitle4);
		curriSubCoursePlanDto.setInfoText4(infoText4);
		curriSubCoursePlanDto.setInfoTitle5(infoTitle5);
		curriSubCoursePlanDto.setInfoText5(infoText5);
		curriSubCoursePlanDto.setRegId(userId);

		CurriSubCoursePlanDAO curriSubCoursePlanDao = new CurriSubCoursePlanDAO();
		// 등록되어있는 강의계획서 수를 받아서 등록/수정 여부를 파악한다.
	    int curriSubCoursePlanCnt = curriSubCoursePlanDao.getCurriSubCoursePlanCount(systemCode, curriCode, curriYear, curriTerm, courseId);

	    int	retVal = 0;
		if(curriSubCoursePlanCnt > 0){	// 수정
			retVal = curriSubCoursePlanDao.editCurriSubCoursePlan(curriSubCoursePlanDto);
		}else{		// 등록
			retVal = curriSubCoursePlanDao.addCurriSubCoursePlan(curriSubCoursePlanDto);
		}

		return String.valueOf(retVal);
	}


	/**
	 * 교수자 정보 가져오기(Ajax)
	 * 2007.06.11 sangsang
	 * @param profId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ProfInfoDTO profInfo(String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		courseId = StringUtil.nvl(courseId);

		//--------과정정보 가져오기 시작 --------
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
		String curriCode = curriMemDto.curriCode;
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		CurriSubCourseDAO curriSubCourseDAO = new CurriSubCourseDAO();
		String profId = curriSubCourseDAO.getCurriSubCourseProfId(systemCode, curriCode, curriYear, curriTerm, courseId);

		// 리턴 데이타 세팅
		UserAdminDAO userAdminDao = new UserAdminDAO();
		ProfInfoDTO profInfoDto = new ProfInfoDTO();
		profInfoDto = userAdminDao.getProfInfo(systemCode,profId);

		String profName = CommonUtil.getUserName(systemCode,profId);
		String major = StringUtil.getHtmlContents(profInfoDto.getMajor());
		String books = StringUtil.getHtmlContents(profInfoDto.getBooks());
		String career = StringUtil.getHtmlContents(profInfoDto.getCareer());

		profInfoDto.setSystemCode(systemCode);
		profInfoDto.setUserId(profId);
		profInfoDto.setUserName(profName);
		profInfoDto.setMajor(major);
		profInfoDto.setBooks(books);
		profInfoDto.setCareer(career);

		return profInfoDto;
	}
}
package com.edutrack.currisub.action;
import java.util.ArrayList;
import java.util.Map;

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
import com.edutrack.coursebookmark.dao.BookmarkDAO;
import com.edutrack.coursebookmark.dto.BookmarkDTO;
import com.edutrack.currisub.dao.CurriContentsDAO;
import com.edutrack.currisub.dao.CurriQuizDAO;
import com.edutrack.currisub.dto.CurriContentsDTO;
import com.edutrack.currisub.dto.CurriQuizDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author bschoi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CurriQuizAction extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();
	/**
	 * 목차 Quiz 정보 리스트를 가져온다.(페이징 없음)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriQuizList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String 	pCurriCode 	= StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 	= StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int 	pCurriTerm 	= StringUtil.nvl(request.getParameter("pCurriTerm"),0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pCourseName = StringUtil.nvl(request.getParameter("pCourseName"));
		String pContentsId = StringUtil.nvl(request.getParameter("pContentsId"));
		String pContentsName = StringUtil.nvl(request.getParameter("pContentsName"));

		CurriContentsDAO contentsDao = new CurriContentsDAO();
		RowSet rsContents = contentsDao.getCurriContents(systemCode,pCurriCode,pCurriYear,pCurriTerm, pCourseId, pContentsId);
		rsContents.next();
		model.put("pQuizCount",String.valueOf(rsContents.getInt("quiz_count")));
		model.put("pQuizPoint",String.valueOf(rsContents.getInt("quiz_point")));
		rsContents.close();

		CurriQuizDAO quizDao = new CurriQuizDAO();
		RowSet rs = quizDao.getCurriQuizList(systemCode,pCurriCode,pCurriYear,pCurriTerm, pCourseId, pContentsId);

		model.put("pCurriCode",pCurriCode);
		model.put("pCurriYear",String.valueOf(pCurriYear));
		model.put("pCurriTerm",String.valueOf(pCurriTerm));
		model.put("pCourseId",pCourseId);
		model.put("pCourseName",pCourseName);
		model.put("pContentsId",pContentsId);
		model.put("pContentsName",pContentsName);
		model.put("rs",rs);

		new SiteNavigation(MYPAGE).add(request,"단원평가관리").link(model);
        return forward(request, model, "/curri_quiz/curriQuizList.jsp");
	}
	/**
	 * 목차 Quiz 등록 폼을 띄운다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriQuizWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		String 	pCurriCode 	= StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 	= StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int 	pCurriTerm 	= StringUtil.nvl(request.getParameter("pCurriTerm"),0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pContentsId = StringUtil.nvl(request.getParameter("pContentsId"));
		String pQuizType = StringUtil.nvl(request.getParameter("pQuizType"));

		model.put("pCurriCode",pCurriCode);
		model.put("pCurriYear",String.valueOf(pCurriYear));
		model.put("pCurriTerm",String.valueOf(pCurriTerm));
		model.put("pCourseId",pCourseId);
		model.put("pContentsId",pContentsId);
		model.put("pQuizType",pQuizType);

		new SiteNavigation(pMode).add(request,"단원평가관리").link(model);
        return forward(request, model, "/curri_quiz/curriQuizWrite.jsp");
	}
	/**
	 * 목차 Quiz 수정 폼을 띄운다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriQuizEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		String 	pCurriCode 	= StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 	= StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int 	pCurriTerm 	= StringUtil.nvl(request.getParameter("pCurriTerm"),0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pContentsId = StringUtil.nvl(request.getParameter("pContentsId"));
		int pQuizOrder = StringUtil.nvl(request.getParameter("pQuizOrder"),0);
		String pQuizType = StringUtil.nvl(request.getParameter("pQuizType"));

		CurriQuizDAO quizDao = new CurriQuizDAO();
		CurriQuizDTO quizDto = new CurriQuizDTO();
		quizDto = quizDao.getCurriQuiz(systemCode,pCurriCode,pCurriYear,pCurriTerm, pCourseId, pContentsId,pQuizOrder);

		model.put("pCurriCode",pCurriCode);
		model.put("pCurriYear",String.valueOf(pCurriYear));
		model.put("pCurriTerm",String.valueOf(pCurriTerm));
		model.put("pCourseId",pCourseId);
		model.put("pContentsId",pContentsId);
		model.put("pQuizOrder",String.valueOf(pQuizOrder));
		model.put("pQuizType",pQuizType);
		model.put("quizDto",quizDto);
		new SiteNavigation(pMode).add(request,"단원평가관리").link(model);
        return forward(request, model, "/curri_quiz/curriQuizEdit.jsp");
	}

	/**
	 * 단원평가 설정 폼을 띄운다.(Ajax)
	 * 2007.05.31 sangsang
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param courseId
	 * @param contentsId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public CurriContentsDTO curriQuizConfigContentsWrite(String curriCode, int curriYear, int curriTerm, String courseId, String contentsId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		curriCode = StringUtil.nvl(curriCode);
		courseId = StringUtil.nvl(courseId);
		contentsId = StringUtil.nvl(contentsId);

		CurriContentsDTO curriContentsDto = new CurriContentsDTO();
		CurriContentsDAO curriContentsDao = new CurriContentsDAO();

		RowSet rs = curriContentsDao.getCurriContents(systemCode, curriCode, curriYear, curriTerm, courseId, contentsId);
		if(rs.next()){
			curriContentsDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			curriContentsDto.setContentsId(StringUtil.nvl(rs.getString("contents_id")));
			curriContentsDto.setQuizCount(rs.getInt("quiz_count"));
			curriContentsDto.setQuizPoint(rs.getInt("quiz_point"));
		}
		rs.close();

        return curriContentsDto;
	}

	/**
	 * 단원평가 설정값을 변경한다.(Ajax)
	 * 2007.05.31 sangsang
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param courseId
	 * @param contentsId
	 * @param quizCount
	 * @param quizPoint
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String curriQuizConfigContentsRegist(String curriCode, int curriYear, int curriTerm, String courseId, String contentsId, int quizCount, int quizPoint, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		curriCode = StringUtil.nvl(curriCode);
		courseId = StringUtil.nvl(courseId);
		contentsId = StringUtil.nvl(contentsId);

		CurriContentsDAO curriContentsDao = new CurriContentsDAO();
		int retVal = curriContentsDao.editCurriContentsQuizConfig(systemCode, curriCode, curriYear, curriTerm, courseId, contentsId,quizCount,quizPoint,userId);

		return String.valueOf(retVal);
	}

	/**
	 * 퀴즈 첨부파일 삭제(Ajax)
	 * 2007.06.04 sangsang
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param courseId
	 * @param contentsId
	 * @param quizOrder
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String curriQuizFileDelete(String curriCode, int curriYear, int curriTerm, String courseId, String contentsId, int quizOrder, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		CurriQuizDAO quizDao = new CurriQuizDAO();

		int retVal = 0;
		retVal = quizDao.delCurriQuizFile(systemCode,curriCode,curriYear,curriTerm, courseId, contentsId,quizOrder,userId);

		return  String.valueOf(retVal);
	}

	/**
	 * 퀴즈를 삭제한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriQuizDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		String 	pCurriCode 	= StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 	= StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int 	pCurriTerm 	= StringUtil.nvl(request.getParameter("pCurriTerm"),0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pContentsId = StringUtil.nvl(request.getParameter("pContentsId"));
		int pQuizOrder = StringUtil.nvl(request.getParameter("pQuizOrder"),0);
		CurriQuizDAO quizDao = new CurriQuizDAO();

		String msg = "삭제하였습니다.";
		String returnUrl = "/CurriQuiz.cmd?cmd=curriQuizList&pCurriCode="+pCurriCode+"&pCurriYear="+pCurriYear+"&pCurriTerm="+pCurriTerm+"&pCourseId="+pCourseId+"&pContentsId="+pContentsId;

		int retVal = 0;
		retVal = quizDao.delCurriQuiz(systemCode,pCurriCode,pCurriYear,pCurriTerm, pCourseId, pContentsId,pQuizOrder);

		new SiteNavigation(pMode).add(request,"단원평가관리").link(model);
		if(retVal > 0){

			return redirect(returnUrl);
		}else{
			msg = "삭제 실패 하였습니다.";
			returnUrl = "/CurriQuiz.cmd?cmd=curriQuizEdit&pCurriCode="+pCurriCode+"&pCurriYear="+pCurriYear+"&pCurriTerm="+pCurriTerm+"&pCourseId="+pCourseId+"&pContentsId="+pContentsId+"&pQuizOrder="+pQuizOrder;
			return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
		}
	}

	/**
	 * 목차 Quiz 등록 / 수정
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriQuizRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		String 	pCurriCode 	= StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 	= StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int 	pCurriTerm 	= StringUtil.nvl(request.getParameter("pCurriTerm"),0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pContentsId = StringUtil.nvl(request.getParameter("pContentsId"));


		String pRegMode = StringUtil.nvl(request.getParameter("pRegMode"));

		CurriQuizDAO quizDao = new CurriQuizDAO();
		CurriQuizDTO quizDto = new CurriQuizDTO();

		int pQuizOrder = 0;
		int retVal = 0;
		String objName = "";
		String rFileName = "";
		String sFileName = "";
		String filePath = "";
		String fileSize = "";

		if(pRegMode.equals("ADD"))
			pQuizOrder = quizDao.getMaxCurriQuizOrder(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,pContentsId);
		else
			pQuizOrder = StringUtil.nvl(request.getParameter("pQuizOrder"),0);

		MultipartRequest multipartRequest = null;

		String FilePath = FileUtil.FILE_DIR+systemCode+"/quiz/"+pCourseId+"/";

//		  파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath,pContentsId+"_"+pQuizOrder+"_"+userId);
		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();
		String status = uploadEntity.getStatus();

		//String pFile[] = new String[4];
		String pOldrFileName = "";		String pOldsFileName = "";
		String pOldFilePath = "";		String pOldFileSize = "";
		pOldrFileName = StringUtil.nvl(multipartRequest.getParameter("pOldrFileName"));
		pOldsFileName = StringUtil.nvl(multipartRequest.getParameter("pOldsFileName"));
		pOldFilePath = StringUtil.nvl(multipartRequest.getParameter("pOldFilePath"));
		pOldFileSize = StringUtil.nvl(multipartRequest.getParameter("pOldFileSize"));

		quizDto.setRfileName(pOldrFileName);	quizDto.setSfileName(pOldsFileName);
		quizDto.setFilePath(pOldFileSize);		quizDto.setFileSize(pOldFileSize);

		String contentsText = "";
		if( new String("true").equals(multipartRequest.getParameter("VBN_FORM_WEAS")) == true ){//-- 웹에디터 사용시
			contentsText = StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
		}else{//-- 웹에디터 사용 안할경
			contentsText = StringUtil.nvl(multipartRequest.getParameter("pContents"));
		}

		String pQuizType = StringUtil.nvl(multipartRequest.getParameter("pQuizType"));
		String pContents = StringUtil.nvl(multipartRequest.getParameter("pContents"));
		String pAnswer = StringUtil.nvl(multipartRequest.getParameter("pAnswer"+pQuizType));
		String pComment = StringUtil.nvl(multipartRequest.getParameter("pComment"));
		String pExample1 = StringUtil.nvl(multipartRequest.getParameter("pExample1"));
		String pExample2 = StringUtil.nvl(multipartRequest.getParameter("pExample2"));
		String pExample3 = StringUtil.nvl(multipartRequest.getParameter("pExample3"));
		String pExample4 = StringUtil.nvl(multipartRequest.getParameter("pExample4"));
		String pExample5 = StringUtil.nvl(multipartRequest.getParameter("pExample5"));

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
					log.debug("++++++++++++++++ ObjName = "+file.getObjName());
					log.debug("++++++++++++++++ FileName = "+file.getRootName());
					log.debug("++++++++++++++++ path = "+uploadEntity.getUploadPath());
					sFileName = file.getUploadName();
					fileSize = String.valueOf(file.getSize());
					filePath = uploadEntity.getUploadPath();
				}
				if(objName.equals("pFile")){
					quizDto.setRfileName(rFileName);	quizDto.setSfileName(sFileName);
					quizDto.setFilePath(filePath);		quizDto.setFileSize(fileSize);

					if(!pOldsFileName.equals("")) {		//이전 첨부파일을 삭제할 경우
						log.debug("이전 파일 삭제하기"+pOldFilePath+pOldsFileName);
						FileUtil.delFile(pOldFilePath, pOldsFileName);
					}
				}
			}// For End
		}
		if(pRegMode.equals("ADD")){//-- 과정 퀴즈는 컨텐츠 내부 첨부 파일도 삭제 하지 않음
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
		}

		quizDto.setSystemCode(systemCode);
		quizDto.setCurriCode(pCurriCode);
		quizDto.setCurriYear(pCurriYear);
		quizDto.setCurriTerm(pCurriTerm);
		quizDto.setCourseId(pCourseId);
		quizDto.setContentsId(pContentsId);
		quizDto.setQuizOrder(pQuizOrder);
		quizDto.setQuizType(pQuizType);
		quizDto.setContents(pContents);
		quizDto.setContentsText(contentsText);
		quizDto.setExample1(pExample1);
		quizDto.setExample2(pExample2);
		quizDto.setExample3(pExample3);
		quizDto.setExample4(pExample4);
		quizDto.setExample5(pExample5);
		quizDto.setAnswer(pAnswer);
		quizDto.setComment(pComment);
		quizDto.setRegId(userId);
		quizDto.setModId(userId);
		String msg = "";

		String returnUrl = "/CurriQuiz.cmd?cmd=curriQuizList&pCurriCode="+pCurriCode+"&pCurriYear="+pCurriYear+"&pCurriTerm="+pCurriTerm+"&pCourseId="+pCourseId+"&pContentsId="+pContentsId;
		new SiteNavigation(pMode).add(request,"단원평가관리").link(model);
		if(pRegMode.equals("ADD"))// 입력모드
		{
			retVal = quizDao.addCurriQuiz(quizDto);
			if(retVal > 0){
				return redirect(returnUrl);
			}else{
				msg = "등록오류 다시 진행해 주세요";
				return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
			}
		}else if(pRegMode.equals("EDIT")){
			retVal = quizDao.editCurriQuiz(quizDto);
			msg = "수정완료";
			if(retVal > 0){
				return redirect(returnUrl);
			}else{
				returnUrl = "/CurriQuiz.cmd?cmd=curriQuizWrite&pCurriCode="+pCurriCode+"&pCurriYear="+pCurriYear+"&pCurriTerm="+pCurriTerm+"&pCourseId="+pCourseId+"&pContentsId="+pContentsId+"&pQuizType="+pQuizType;
				msg = "수정오류 다시 진행해 주세요";
				return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
			}
		}else
			return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
	}

	/**
	 * 단원평가 이월(Ajax)
	 * 2007.06.04 sangsang
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param courseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String curriQuizAuto(String curriCode, int curriYear, int curriTerm, String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String 	systemCode 	= UserBroker.getSystemCode(request);
		String 	userId 		= UserBroker.getUserId(request);

		curriCode = StringUtil.nvl(curriCode);
		courseId = StringUtil.nvl(courseId);

		int retVal = 0;
		CurriQuizDAO quizDao = new CurriQuizDAO();
		retVal = quizDao.addCurriQuizAuto(systemCode,curriCode,curriYear,curriTerm, courseId, userId);

		return String.valueOf(retVal);
	}


	public ActionForward curriQuizStShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
//		----- 메모리에서 정보 가져오기.
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String regId = userMemDto.userId;
	    String userName = userMemDto.userName;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
	    int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pCourseName = StringUtil.nvl(request.getParameter("pCourseName"));
		String pContentsId = StringUtil.nvl(request.getParameter("pContentsId"));

		CurriContentsDAO	curriContentsDao 	= 	new CurriContentsDAO();
		CurriQuizDAO		curriQuizDao		=	new CurriQuizDAO();
		RowSet contentsInfo = curriContentsDao.getCurriContents(systemCode,pCurriCode,pCurriYear,pCurriTerm, pCourseId,pContentsId);
		contentsInfo.next();
		String pContentsName = StringUtil.nvl(contentsInfo.getString("contents_name"));
		int QuizCount = contentsInfo.getInt("quiz_count");
		int QuizPoint = contentsInfo.getInt("quiz_point");
		contentsInfo.close();

		model.put("pCourseId",pCourseId);
		model.put("pCourseName",pCourseName);
		model.put("pContentsId",pContentsId);
		model.put("pContentsName",pContentsName);
		model.put("QuizCount",String.valueOf(QuizCount));
		model.put("QuizPoint",String.valueOf(QuizPoint));
		model.put("quizList",curriQuizDao.getRandQuizList(systemCode,curriMemDto,pCourseId,pContentsId,QuizCount));
		log.debug("=============================================================curriQuizStShow end");
        return forward(request, model, "/curri_quiz/curriQuizStShow.jsp");
	}

	public ActionForward curriQuizStRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=============================================================curriQuizStRegist start");
//		----- 메모리에서 정보 가져오기.
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String regId = userMemDto.userId;
	    String userName = userMemDto.userName;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
	    int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pContentsId = StringUtil.nvl(request.getParameter("pContentsId"));
		String pContentsName = StringUtil.nvl(request.getParameter("pContentsName"));
		int QuizCount = Integer.parseInt(StringUtil.nvl(request.getParameter("QuizCount")));
		int QuizPoint = Integer.parseInt(StringUtil.nvl(request.getParameter("QuizPoint")));
		log.debug("++++++++++++ chk1");
		String pQuizOrder[] = new String[QuizCount+1];
		String pQuizType[] = new String[QuizCount+1];
		String pRAnswer[] = new String[QuizCount+1];
		String pAnswer[] = new String[QuizCount+1];
		log.debug("++++++++++++ chk2");
		CurriQuizDAO	curriQuizDao	=	new CurriQuizDAO();
		BookmarkDAO		bookmarkDao		=	new BookmarkDAO();
		BookmarkDTO		bookmarkDto		=	new BookmarkDTO();


		RowSet bInfo = bookmarkDao.getBookmark(systemCode,pCurriCode,pCurriYear,pCurriTerm, pCourseId,regId,pContentsId);
		bInfo.next();
		String quizPass = 	StringUtil.nvl(bInfo.getString("quiz_pass"));
		int scoreMax	=	bInfo.getInt("score_max");
		int scoreRaw	=	bInfo.getInt("score_raw");
		int scoreMin	=	bInfo.getInt("score_min");
		String newQuizPass = "";
		int newScoreMax = 0;
		int newScoreMin = 0;
		bInfo.close();
		log.debug("++++++++++++ chk3");
		int passCount = 0;
		for(int i=1;i<=QuizCount;i++){
			log.debug("++++++++++++ i = "+i);
			pRAnswer[i] = StringUtil.nvl(request.getParameter("pRAnswer["+i+"]"));
			pAnswer[i] = StringUtil.nvl(request.getParameter("pAnswer["+i+"]"));
			if(pRAnswer[i].equals(pAnswer[i])){
				passCount++;
			}
		}
		if(passCount > scoreMax)			newScoreMax = passCount;
		else			newScoreMax = scoreMax;
		if(passCount < scoreMin)			newScoreMin = passCount;
		else			newScoreMin = scoreMin;

		log.debug("++++++++++++ chk4");

		if(scoreMax == 0 && scoreRaw == 0 && scoreMin == 0)	newScoreMin = passCount;
		if(passCount >= QuizPoint )	newQuizPass = "P";//--pass
		else		newQuizPass = "F";//--fail

		if(quizPass.equals("P")){
			newQuizPass = "P";//--pass
		}

		log.debug("++++++++++++ chk5");

		bookmarkDto.setSystemCode(systemCode);
		bookmarkDto.setCurriCode(pCurriCode);
		bookmarkDto.setCurriYear(pCurriYear);
		bookmarkDto.setCurriTerm(pCurriTerm);
		bookmarkDto.setCourseId(pCourseId);
		bookmarkDto.setUserId(regId);
		bookmarkDto.setContentsId(pContentsId);
		bookmarkDto.setQuizPass(newQuizPass);
		bookmarkDto.setScoreMax(newScoreMax);
		bookmarkDto.setScoreRaw(passCount);
		bookmarkDto.setScoreMin(newScoreMin);
		bookmarkDto.setModId(regId);
		log.debug("++++++++++++ chk6");
		int retVal = 0;
		retVal = bookmarkDao.editBookmarkQuiz(bookmarkDto);
		log.debug("++++++++++++ chk7");
		model.put("pCourseId",pCourseId);
		model.put("pContentsId",pContentsId);
		model.put("pContentsName",pContentsName);
		model.put("QuizCount",String.valueOf(QuizCount));
		model.put("QuizPoint",String.valueOf(QuizPoint));
		model.put("passCount",String.valueOf(passCount));
		model.put("quizPass",quizPass);
		model.put("newQuizPass",newQuizPass);
		model.put("retVal",String.valueOf(retVal));
		log.debug("=============================================================curriQuizStRegist end");
        return forward(request, model, "/curri_quiz/curriQuizStRegist.jsp");
	}

}

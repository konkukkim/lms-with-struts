package com.edutrack.curritop.action;
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
import com.edutrack.curritop.dao.ContentsDAO;
import com.edutrack.curritop.dao.QuizDAO;
import com.edutrack.curritop.dto.ContentsDTO;
import com.edutrack.curritop.dto.QuizDTO;
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
public class QuizAction extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	/**
	 * 단원평가 설정 폼을 띄운다.(Ajax)
	 * 2007.05.31 sangsang
	 * @param courseId
	 * @param contentsId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ContentsDTO quizConfigContentsWrite(String courseId, String contentsId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		courseId = StringUtil.nvl(courseId);
		contentsId = StringUtil.nvl(contentsId);

		ContentsDTO contentsDto = new ContentsDTO();
		ContentsDAO contentsDao = new ContentsDAO();

		RowSet rs = contentsDao.getContents(systemCode, courseId, contentsId);
		if(rs.next()){
			contentsDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			contentsDto.setContentsId(StringUtil.nvl(rs.getString("contents_id")));
			contentsDto.setQuizCount(rs.getInt("quiz_count"));
			contentsDto.setQuizPoint(rs.getInt("quiz_point"));
		}
		rs.close();

        return contentsDto;
	}

	/**
	 * 단원평가 설정값을 변경한다.(Ajax)
	 * 2007.05.31 sangsang
	 * @param courseId
	 * @param contentsId
	 * @param quizCount
	 * @param quizPoint
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String quizConfigContentsRegist(String courseId, String contentsId, int quizCount, int quizPoint, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		courseId = StringUtil.nvl(courseId);
		contentsId = StringUtil.nvl(contentsId);

		ContentsDAO contentsDao = new ContentsDAO();
		int retVal = contentsDao.editContentsQuizConfig(systemCode, courseId, contentsId,quizCount,quizPoint,userId);

		return String.valueOf(retVal);
	}

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
	public ActionForward quizList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pContentsId = StringUtil.nvl(request.getParameter("pContentsId"));

		ContentsDAO contentsDao = new ContentsDAO();
		RowSet rsContents = contentsDao.getContents(systemCode, pCourseId, pContentsId);
		rsContents.next();
		model.put("pQuizCount",String.valueOf(rsContents.getInt("quiz_count")));
		model.put("pQuizPoint",String.valueOf(rsContents.getInt("quiz_point")));
		rsContents.close();

		QuizDAO quizDao = new QuizDAO();
		RowSet rs = quizDao.getQuizList(systemCode, pCourseId, pContentsId);

		model.put("pCourseId",pCourseId);
		model.put("pContentsId",pContentsId);
		model.put("rs",rs);

		new SiteNavigation(MYPAGE).add(request,"단원평가관리").link(model);
        return forward(request, model, "/quiz/quizList.jsp");
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
	public ActionForward quizWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pContentsId = StringUtil.nvl(request.getParameter("pContentsId"));
		String pQuizType = StringUtil.nvl(request.getParameter("pQuizType"));

		model.put("pCourseId",pCourseId);
		model.put("pContentsId",pContentsId);
		model.put("pQuizType",pQuizType);

		new SiteNavigation(pMode).add(request,"단원평가관리").link(model);
        return forward(request, model, "/quiz/quizWrite.jsp");
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
	public ActionForward quizEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pContentsId = StringUtil.nvl(request.getParameter("pContentsId"));
		int pQuizOrder = StringUtil.nvl(request.getParameter("pQuizOrder"),0);
		String pQuizType = StringUtil.nvl(request.getParameter("pQuizType"));

		QuizDAO quizDao = new QuizDAO();
		QuizDTO quizDto = new QuizDTO();
		quizDto = quizDao.getQuiz(systemCode, pCourseId, pContentsId,pQuizOrder);

		model.put("pCourseId",pCourseId);
		model.put("pContentsId",pContentsId);
		model.put("pQuizOrder",String.valueOf(pQuizOrder));
		model.put("pQuizType",pQuizType);
		model.put("quizDto",quizDto);

		new SiteNavigation(pMode).add(request,"단원평가관리").link(model);
        return forward(request, model, "/quiz/quizEdit.jsp");
	}

	/**
	 * 퀴즈 첨부파일 삭제(Ajax)
	 * 2007.05.31 sangsang
	 * @param courseId
	 * @param contentsId
	 * @param quizOrder
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String quizFileDelete(String courseId, String contentsId, int quizOrder, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		QuizDAO quizDao = new QuizDAO();
		QuizDTO quizDto = new QuizDTO();
		quizDto = quizDao.getQuiz(systemCode, courseId, contentsId,quizOrder);
		String oldrFileName = StringUtil.nvl(quizDto.getRfileName());
		String oldsFileName = StringUtil.nvl(quizDto.getSfileName());
		String oldFilePath = StringUtil.nvl(quizDto.getFilePath());

		int retVal = 0;
		retVal = quizDao.delQuizFile(systemCode, courseId, contentsId,quizOrder,userId);
		if(!oldrFileName.equals("") && !oldrFileName.equals("") && !oldrFileName.equals("")){
			FileUtil.delFile(oldFilePath, oldsFileName);
		}

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
	public ActionForward quizDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pContentsId = StringUtil.nvl(request.getParameter("pContentsId"));
		int pQuizOrder = StringUtil.nvl(request.getParameter("pQuizOrder"),0);
		QuizDAO quizDao = new QuizDAO();
		QuizDTO quizDto = new QuizDTO();
		quizDto = quizDao.getQuiz(systemCode, pCourseId, pContentsId,pQuizOrder);

		String pOldrFileName = StringUtil.nvl(quizDto.getRfileName());
		String pOldsFileName = StringUtil.nvl(quizDto.getSfileName());
		String pOldFilePath = StringUtil.nvl(quizDto.getFilePath());

		String msg = "삭제하였습니다.";
		String returnUrl = "/Quiz.cmd?cmd=quizList&pCourseId="+pCourseId+"&pContentsId="+pContentsId;

		int retVal = 0;
		retVal = quizDao.delQuiz(systemCode, pCourseId, pContentsId,pQuizOrder);

		new SiteNavigation(pMode).add(request,"단원평가관리").link(model);
		if(retVal > 0){
			if(!pOldrFileName.equals("") && !pOldsFileName.equals("") && !pOldFilePath.equals("")){
				FileUtil.delFile(pOldFilePath, pOldsFileName);
			}
			return redirect(returnUrl);
		}else{
			msg = "삭제 실패 하였습니다.";
			returnUrl = "/Quiz.cmd?cmd=quizEdit&pCourseId="+pCourseId+"&pContentsId="+pContentsId+"&pQuizOrder="+pQuizOrder;
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
	public ActionForward quizRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pContentsId = StringUtil.nvl(request.getParameter("pContentsId"));
		String pRegMode = StringUtil.nvl(request.getParameter("pRegMode"));

		QuizDAO quizDao = new QuizDAO();
		QuizDTO quizDto = new QuizDTO();

		int pQuizOrder = 0;
		int retVal = 0;
		String objName = "";
		String rFileName = "";
		String sFileName = "";
		String filePath = "";
		String fileSize = "";

		if(pRegMode.equals("ADD"))
			pQuizOrder = quizDao.getMaxQuizOrder(systemCode,pCourseId,pContentsId);
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

		String pQuizType = StringUtil.nvl(multipartRequest.getParameter("pQuizType"));
		String pContents = StringUtil.nvl(multipartRequest.getParameter("pContents"));
		String pAnswer = StringUtil.nvl(multipartRequest.getParameter("pAnswer"+pQuizType));
		String pComment = StringUtil.nvl(multipartRequest.getParameter("pComment"));
		String pExample1 = "";
		String pExample2 = "";
		String pExample3 = "";
		String pExample4 = "";
		String pExample5 = "";
		if(pQuizType.equals("K")){
			pExample1 = StringUtil.nvl(multipartRequest.getParameter("pExample1"));
			pExample2 = StringUtil.nvl(multipartRequest.getParameter("pExample2"));
			pExample3 = StringUtil.nvl(multipartRequest.getParameter("pExample3"));
			pExample4 = StringUtil.nvl(multipartRequest.getParameter("pExample4"));
			pExample5 = StringUtil.nvl(multipartRequest.getParameter("pExample5"));
		}
		String contentsText = "";
		if( new String("true").equals(multipartRequest.getParameter("VBN_FORM_WEAS")) == true ){//-- 웹에디터 사용시
			contentsText = StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
		}else{//-- 웹에디터 사용 안할경
			contentsText = pContents;
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

		quizDto.setSystemCode(systemCode);
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

		new SiteNavigation(pMode).add(request,"단원평가관리").link(model);
		String returnUrl = "/Quiz.cmd?cmd=quizList&pCourseId="+pCourseId+"&pContentsId="+pContentsId;
		if(pRegMode.equals("ADD")){// 입력모드
			retVal = quizDao.addQuiz(quizDto);
			if(retVal > 0){
				return redirect(returnUrl);
			}else{
				msg = "등록오류 다시 진행해 주세요";
				return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
			}
		}else if(pRegMode.equals("EDIT")){
			retVal = quizDao.editQuiz(quizDto);
			if(retVal > 0){
				return redirect(returnUrl);
			}else{
				returnUrl = "/Quiz.cmd?cmd=quizWrite&pCourseId="+pCourseId+"&pContentsId="+pContentsId+"&pQuizType="+pQuizType;
				msg = "수정오류 다시 진행해 주세요";
				return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
			}
		}else
			return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
	}
}

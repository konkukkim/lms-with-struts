/*
 * Created on 2004. 10. 14.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseexam.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.VBN.VBN_files;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.CurriSiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.courseexam.dao.ExamAdminDAO;
import com.edutrack.courseexam.dao.ExamAnswerDAO;
import com.edutrack.courseexam.dao.ExamBankDAO;
import com.edutrack.courseexam.dao.ExamContentsDAO;
import com.edutrack.courseexam.dto.ExamAnswerDTO;
import com.edutrack.courseexam.dto.ExamBkContentsDTO;
import com.edutrack.courseexam.dto.ExamContentsDTO;
import com.edutrack.courseexam.dto.ExamInfoDTO;
import com.edutrack.courseexam.dto.ExamOrderDTO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExamContentsAction extends StrutsDispatchAction {

	/**
	 *
	 */
	public ExamContentsAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 시험 리스트를 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward examContentsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
        int pExamId = StringUtil.nvl(request.getParameter("pExamId"),0);

		ExamContentsDAO contentsDao = new ExamContentsDAO();
		ArrayList contentsList = null;

		log.debug("PCOURSEID =>"+pCourseId+",pExamId =>"+pExamId);

		contentsList = contentsDao.getExamContentsList(systemCode, curriInfo, pCourseId,pExamId,0);
        ExamAnswerDAO answerDao = new ExamAnswerDAO();
		int answerUserCnt = answerDao.getAnswerCnt(systemCode,curriInfo, pCourseId,pExamId);
		model.put("contentsList", contentsList);
		model.put("pCourseId",pCourseId);
		model.put("pExamId",""+pExamId);
		model.put("answerUserCnt",""+answerUserCnt);

		new CurriSiteNavigation(LECTURE).add(request,"문제리스트").link(model);

		return forward(request, model, "/courseexam/exam_mg_contents_list.jsp");
	}

    /**
     * 시험 등록,수정 폼을 띄워준다.
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
	public ActionForward examContentsWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pExamId = StringUtil.nvl(request.getParameter("pExamId"),0);
		String pExamType = StringUtil.nvl(request.getParameter("pExamType"));
		int pExamOrder = StringUtil.nvl(request.getParameter("pExamOrder"),0);
		int pExamNo = StringUtil.nvl(request.getParameter("pExamNo"),0);
		int answerUserCnt = StringUtil.nvl(request.getParameter("answerUserCnt"),0);

		log.debug("pCourseId =>"+pCourseId+",pExamId =>"+pExamId+",pExamType =>"+pExamType);

		ExamContentsDTO contentsInfo = null;
		ExamHelper helper = new ExamHelper();
		if(pMODE.equals("ADD")){
		     contentsInfo = new ExamContentsDTO();
		}else{
			ExamContentsDAO examDao = new ExamContentsDAO();
			CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
			contentsInfo = examDao.getExamContentsInfo(systemCode,curriInfo ,pCourseId,pExamId, pExamOrder,pExamNo);
		}

		model.put("contentsInfo",contentsInfo);
		model.put("pMODE", pMODE);
        model.put("pCourseId", pCourseId);
		model.put("pExamId",""+pExamId);
		model.put("pExamOrder",""+pExamOrder);
		model.put("pExamNo",""+pExamNo);
		model.put("pExamType",pExamType);
		model.put("answerUserCnt",""+answerUserCnt);

        String navMsg = "문제등록";

        if(!pMODE.equals("ADD")) navMsg = "문제수정";

		new CurriSiteNavigation(LECTURE).add(request,navMsg).link(model);

		return forward(request, model, "/courseexam/exam_mg_contents_write.jsp");
	}

	/**
	 * 시험정보를 등록한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward examContentsRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		UserMemDTO userInfo = UserBroker.getUserInfo(request);
		CurriMemDTO curriInfo = userInfo.curriInfo;
		String examId = request.getParameter("pExamId");
		String courseId = request.getParameter("pCourseId");

		ExamContentsDAO contentsDao = new ExamContentsDAO();
		ExamHelper helper = new ExamHelper();
		ExamContentsDTO contentsInfo = new ExamContentsDTO();

		MultipartRequest multipartRequest = null;

		String filePath = FileUtil.FILE_DIR+systemCode+"/exam/"+curriInfo.curriCode+"/"+curriInfo.curriYear+"/"+curriInfo.curriTerm+"/"+courseId+"/"+examId+"/";

		UploadFiles uploadEntity = FileUtil.upload(request, filePath,curriInfo.curriCode+curriInfo.curriYear+curriInfo.curriTerm);
		multipartRequest		=	uploadEntity.getMultipart();

		String pMODE = StringUtil.nvl(multipartRequest.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(multipartRequest.getParameter("pCourseId"));
		int pExamId = StringUtil.nvl(multipartRequest.getParameter("pExamId"),0);
		helper.getExamContentsParam(multipartRequest, contentsInfo);
		String status = uploadEntity.getStatus();
        String rFileName = "",sFileName="";
        long fileSize=0;
		if(status.equals("S")){
			//--	업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			ArrayList files		=	uploadEntity.getFiles();
			UploadFile file		=	null;
			String objName      = "";
			for(int i = 0 ; i < files.size(); i++){
				file			=	(UploadFile)files.get(i);
				objName         = file.getObjName();
                if(objName.equals("pFile")){
					sFileName		=	file.getUploadName();
					rFileName       =   file.getRootName();
					fileSize        =   file.getSize();
                }
			}
		}

		if(!rFileName.equals("")){
			contentsInfo.setRfileName(rFileName);
			contentsInfo.setSfileName(sFileName);
			contentsInfo.setFileSize(""+fileSize);
			contentsInfo.setFilePath(filePath);
//			if(!contentsInfo.getOldSfile().equals("")){
//				// 이전 파일을 삭제해 준다.
//				FileUtil.delFile(filePath, contentsInfo.getOldSfile());
//			}
		}else{
            if(contentsInfo.getFileCheck().equals("")){
			    contentsInfo.setRfileName(contentsInfo.getOldRfile());
				contentsInfo.setSfileName(contentsInfo.getOldSfile());
				contentsInfo.setFileSize(contentsInfo.getOldFileSize());
				contentsInfo.setFilePath(contentsInfo.getOldFilePath());
            }
//			if(!contentsInfo.getOldSfile().equals("") && !contentsInfo.getFileCheck().equals("")){
//				// 이전 파일을 삭제해 준다.
//				FileUtil.delFile(filePath, contentsInfo.getOldSfile());
//			}
		}
		String pContents = "";
		/** 웹에디터 셋팅 추가 Start**/
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH+filePath;
		String WeasFileUrl =  ServiceUrl+"/data/"+filePath;
		log.debug("WeasFilePath = "+WeasFilePath);
		VBN_files v_objFile = null;


		java.io.File dir = new java.io.File(WeasFilePath);
		if (!dir.exists())	dir.mkdirs();

		v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
		String contentsText = StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
		if(contentsText.equals("")){
			String returnUrl = "/ExamContents.cmd?cmd=examContentsWrite&pMODE="+pMODE+"&pCourseId="+pCourseId+"&pExamId="+pExamId+"&pExamOrder="+contentsInfo.getExamOrder()+"&pExamNo="+contentsInfo.getExamNo()+"&pExamType="+contentsInfo.getExamType();
			FileUtil.delFile(filePath, contentsInfo.getSfileName());
			return notifyAndExit(systemCode,model, "문제 입력 오류입니다.",returnUrl,LECTURE);
		}

		contentsInfo.setContentsText(contentsText);
		pContents = v_objFile.VBN_uploadMultiFiles(contentsInfo.getContents(), multipartRequest);
		/** 웹에디터 셋팅 추가 End**/
		log.debug("pContents"+pContents);
		contentsInfo.setContents(pContents);

		if(pMODE.equals("ADD")){
	        contentsInfo.setRegId(userInfo.userId);
			contentsDao.addExamContentsInfo(systemCode, userInfo.curriInfo, contentsInfo);
//			 문제은행 자동 등록 체크
			String pAutoBankRegist = StringUtil.nvl(multipartRequest.getParameter("pAutoBankRegist"));
			String pImsiBankCode = StringUtil.nvl(multipartRequest.getParameter("pBankCode"));
			long pBankCode = 0;
			if(pImsiBankCode != null && !pImsiBankCode.equals("")) pBankCode = Long.parseLong(pImsiBankCode);
			String pBankName = StringUtil.nvl(multipartRequest.getParameter("pBankName"));
			ExamBankDAO bankContentsDao = new ExamBankDAO();
			ExamBkContentsDTO bankContentsInfo = new ExamBkContentsDTO();
			if(pAutoBankRegist.equals("Y")){ //-- 문제은행 등록 할 경우
				if(pImsiBankCode.equals("")){//-- 신규 항목으로 등록시
					ExamBankDAO bankDao = new ExamBankDAO();
					pBankCode = bankDao.addExamBankInfoReturnBankCode(systemCode,userInfo.userId,pCourseId,pBankName);
				}
				bankContentsInfo.setExamBkCode(pBankCode);
				helper.getExamBkContentsParam(multipartRequest, bankContentsInfo);
				bankContentsInfo.setCourseId(pCourseId);
				bankContentsInfo.setExamBkCode(pBankCode);
				bankContentsInfo.setRfileName(contentsInfo.getRfileName());
				bankContentsInfo.setSfileName(contentsInfo.getSfileName());
				bankContentsInfo.setFileSize(contentsInfo.getFileSize());
				bankContentsInfo.setFilePath(contentsInfo.getFilePath());
				bankContentsInfo.setContentsText(contentsText);
				bankContentsInfo.setContents(pContents);
				bankContentsInfo.setRegId(userInfo.userId);
				bankContentsDao.addExamBankContentsInfo(systemCode, bankContentsInfo);
			}

		}else{
			contentsInfo.setModId(userInfo.userId);
			contentsDao.editExamContentsInfo(systemCode, userInfo.curriInfo, contentsInfo);
		}

		new CurriSiteNavigation(LECTURE).add(request,"문제리스트").link(model);

		return redirect("/ExamContents.cmd?cmd=examContentsList&pCourseId="+pCourseId+"&pExamId="+pExamId);
	}

	/**
	 * 시험을 등록해 준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward examRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pExamId = StringUtil.nvl(request.getParameter("pExamId"),0);
		int maxOrder = StringUtil.nvl(request.getParameter("pMaxOrder"),0);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		log.debug("maxOrder =>"+maxOrder);

		int score = 0;
		ArrayList scoreList = new ArrayList();
		ExamOrderDTO order = null;
		for(int i =1; i <= maxOrder; i++){
			score = StringUtil.nvl(request.getParameter("score"+i),0);
			if(score > 0){
				order = new ExamOrderDTO(i,score);
				scoreList.add(order);
			}
		}
		ExamContentsDAO contentsDao = new ExamContentsDAO();

		boolean retVal = contentsDao.editExamScore(systemCode, curriInfo,pCourseId, pExamId, scoreList);
		String msg = "";
		String returnUrl = "";

		if(pMODE.equals("D")){
			ExamAdminDAO adminDao = new ExamAdminDAO();
			adminDao.editExamUseStatus(systemCode, curriInfo, pCourseId,pExamId,"Y");
			msg = "시험을 등록하였습니다.";
			returnUrl = "/ExamAdmin.cmd?cmd=examList&pCourseId="+pCourseId;
		}else{
			msg = "배점주기에 성공하였습니다.";
			returnUrl = "/ExamContents.cmd?cmd=examContentsList&pCourseId="+pCourseId+"&pExamId="+pExamId;
		}

	   return notifyAndExit(systemCode,model, msg, returnUrl,LECTURE);
	}

	public ActionForward examFrameShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pExamId = StringUtil.nvl(request.getParameter("pExamId"),0);

		model.put("pMODE",pMODE);
		model.put("pCourseId",pCourseId);
		model.put("pExamId",""+pExamId);

		return forward(request, model, "/courseexam/exam_st_frame.jsp");
	}

	public ActionForward examShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pExamId = StringUtil.nvl(request.getParameter("pExamId"),0);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
		// 시험 정보를 가져온다.
		ExamAdminDAO adminDao = new ExamAdminDAO();
		ExamInfoDTO examInfo = adminDao.getExamInfo(systemCode, curriInfo,pCourseId, pExamId);

		// 시험 답변정보를 가져온다.
		ExamAnswerDAO answerDao = new ExamAnswerDAO();
		ExamAnswerDTO answerInfo = null;
		if(pMODE.equals("P")){
			answerInfo = new ExamAnswerDTO();
		}else{
			answerInfo = answerDao.getExamAnswerInfo(systemCode, curriInfo,pCourseId, pExamId, userId);
            long curDate = Long.parseLong(CommonUtil.getCurrentDate());

			if(Long.parseLong(examInfo.getStartDate()) > curDate || Long.parseLong(examInfo.getEndDate()) < curDate){
				StringBuffer sb = new StringBuffer().append("시험기간은 ");
				sb.append(DateTimeUtil.getDateType(0,examInfo.getStartDate())+"부터 ");
				sb.append(DateTimeUtil.getDateType(0,examInfo.getEndDate())+"까지  입니다.");
				return alertPopupCloseResponse(systemCode, model,sb.toString(),"POPUP");
			}

			if(answerInfo.getUserId().equals("")){ // 처음 시험 보러 들어온 사용자일 경우 시험 정보 추가하고 통과
			   answerDao.addExamAnswerInfo(systemCode, curriInfo, pCourseId, pExamId,userId);
			}else{
				// 시험 제출일이 없어도 시간이 시험시간과 비교하여 없을 경우
				if(!answerInfo.getStartDate().equals("") && answerInfo.getFlagRetest().equals("N")) return alertPopupCloseResponse(systemCode, model,"시험을 이미 보셨습니다.","POPUP");
				else answerDao.editExamAnswerTime(systemCode, curriInfo, pCourseId, pExamId,userId,"S");
//				if(answerInfo.getEndDate().equals("")){
//                   	if((examInfo.getRunTime()-answerInfo.getAnswerTime()) < 1)
//                   		 return alertPopupCloseResponse(systemCode, model,"시험지를 제출하지 않았으나 시험시간이 없으므로 시험을 볼 수 없습니다.\\n재시험을 신청해 주세요.",LECTURE);
//				}else return alertPopupCloseResponse(systemCode, model,"시험을 이미 보셨습니다.",LECTURE);
			}
		}
		model.put("answerInfo",answerInfo);
		model.put("examInfo",examInfo);
		// 시험문제를 가져온다.
		ExamContentsDAO contentsDao = new ExamContentsDAO();
		model.put("contentsList",contentsDao.getRandExamContentsList(systemCode, curriInfo,pCourseId, pExamId));
		model.put("pMODE",pMODE);
		model.put("pCourseId",pCourseId);
		model.put("pExamId",""+pExamId);
	   return forward(request, model, "/courseexam/exam_st_show.jsp");
	}

	public ActionForward examBankContentsCopy(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);

		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pExamId = StringUtil.nvl(request.getParameter("pExamId"),0);
		int pExamOrder = StringUtil.nvl(request.getParameter("pExamOrder"),0);
		String pBankCode = StringUtil.nvl(request.getParameter("pBankCode"));
		String[] examNo = request.getParameterValues("contentsBox");
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		ExamContentsDAO contentsDao = new ExamContentsDAO();
		boolean retVal = contentsDao.moveBankContents(systemCode,curriInfo,pCourseId,Long.parseLong(pBankCode),examNo, pExamId, pExamOrder,userId );

		String returnUrl="/ExamContents.cmd?cmd=examContentsList&pCourseId="+pCourseId+"&pExamId="+pExamId;
		if(retVal){
			return redirect(returnUrl);
		}else{
			String msg = "문제 등록에 실패하였습니다.<br>다시 진행해 주세요";
			return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
		}
	}

	public ActionForward examContentsDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);

		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pExamId = StringUtil.nvl(request.getParameter("pExamId"),0);
		int pExamOrder = StringUtil.nvl(request.getParameter("pExamOrder"),0);
		int pExamNo = StringUtil.nvl(request.getParameter("pExamNo"),0);
		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		ExamContentsDAO contentsDao = new ExamContentsDAO();
		boolean retVal = contentsDao.delExamContents(systemCode, curriInfo,pCourseId, pExamId, pExamOrder, pExamNo);

		String returnUrl="/ExamContents.cmd?cmd=examContentsList&pCourseId="+pCourseId+"&pExamId="+pExamId;
		if(retVal){
			return redirect(returnUrl);
		}else{
			String msg = "문제 삭제를 실패하였습니다.<br>다시 진행해 주세요";
			return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
		}
	}

	/**
	 * 시험 첨부파일 삭제(Ajax)
	 * 2007.06.14 sangsang
	 * @param courseId
	 * @param examId
	 * @param examNo
	 * @param examOrder
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public int examContentsFileDelete(String courseId, int examId, int examNo, int examOrder,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		ExamContentsDAO examContentsDao = new ExamContentsDAO();
		ExamContentsDTO examContentsDto = new ExamContentsDTO();

		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);
		examContentsDto = examContentsDao.getExamContentsInfo(systemCode,curriInfo ,courseId,examId, examOrder,examNo);
		examContentsDto.setModId(userId);

		int retVal = 0;
		retVal = examContentsDao.delExamContentsFile(examContentsDto);

/*		// 첨부파일 삭제 안함-DB만 삭제
		String oldrFileName = StringUtil.nvl(examContentsDto.getRfileName());
		String oldsFileName = StringUtil.nvl(examContentsDto.getSfileName());
		String oldFilePath = StringUtil.nvl(examContentsDto.getFilePath());

		if(!oldrFileName.equals("") && !oldrFileName.equals("") && !oldrFileName.equals("")){
			FileUtil.delFile(oldFilePath, oldsFileName);
		}
*/
		return  retVal;
	}
}

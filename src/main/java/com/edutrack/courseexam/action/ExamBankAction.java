/*
 * Created on 2004. 10. 21.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseexam.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.VBN.VBN_files;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.CurriSiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.courseexam.dao.ExamBankDAO;
import com.edutrack.courseexam.dto.ExamBkContentsDTO;
import com.edutrack.courseexam.dto.ExamBkInfoDTO;
import com.edutrack.courseexam.dao.ExamBankDAO;
import com.edutrack.courseexam.dto.ExamBkContentsDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
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
public class ExamBankAction extends StrutsDispatchAction {

	/**
	 *
	 */
	public ExamBankAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 문제은행 문제 리스트
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward examBankContentsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);

		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pGubun = StringUtil.nvl(request.getParameter("pGubun"),"M");
		String pExamId = StringUtil.nvl(request.getParameter("pExamId"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pBankCode = StringUtil.nvl(request.getParameter("pBankCode"));

		model.put("pCourseId",pCourseId);
		model.put("pBankCode",pBankCode);
		model.put("pMODE",pMODE);
		model.put("pGubun",pGubun);
		model.put("pExamId",pExamId);

		String returnUrl = "/courseexam/exam_contents_add_bank.jsp";
		if(pGubun.equals("M")) returnUrl = "/courseexam/exam_bk_info_list.jsp";

		new CurriSiteNavigation(pMode).add(request,"문제은행").link(model);
		return forward(request, model, returnUrl);
	}

	/**
	 * 과목별 문제은행항목 리스트를 셀렉트 박스로 보내준다..(ajax)
	 * 2007.06.14 sangsang
	 * @param courseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map examBankInfoSelectList(String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);
		courseId = StringUtil.nvl(courseId);

		ExamBankDAO bankDao = new ExamBankDAO();
		Map map = new HashMap();
		RowSet rs = bankDao.getExamBankInfoCodeList(systemCode, courseId);
		while(rs.next()){
			map.put(StringUtil.nvl(rs.getString("exam_bk_code")),StringUtil.nvl(rs.getString("exam_bk_name")));
		}
		rs.close();
		return map;
	}

	/**
	 * 문제은행 구분항목(그룹) 등록/수정/삭제 (Ajax)
	 * @param regMode
	 * @param courseId
	 * @param bankCode
	 * @param bankName
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String examBankInfoRegist(String regMode, String courseId, String bankCode, String bankName, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		courseId = StringUtil.nvl(courseId);
		bankCode = StringUtil.nvl(bankCode,"0");
		bankName = AjaxUtil.ajaxDecoding(bankName);

		ExamBankDAO examBankDao = new ExamBankDAO();

	    int	retVal = 0;
		if(regMode.equals("ADD")){
			retVal = examBankDao.addExamBankInfo(systemCode,userId,courseId,bankName);
		}else if(regMode.equals("EDIT")){
			retVal = examBankDao.editExamBankInfo(systemCode,userId,courseId,Long.parseLong(bankCode),bankName);
		}else if(regMode.equals("DEL")){
			boolean retValDel = false;
			retValDel = examBankDao.delExamBankInfo(systemCode,userId,courseId,Long.parseLong(bankCode));
			if(retValDel)
				retVal = 1;
			else
				retVal = 0;
		}
		return String.valueOf(retVal);
	}

	/**
	 * 문제은행 항목별 문제리스트(Ajax)
	 * 2007.06.13 sangsang
	 * @param courseId
	 * @param bankCode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList examBankContentsListAuto(String courseId, String bankCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		courseId = StringUtil.nvl(courseId);
		bankCode = StringUtil.nvl(bankCode,"0");

		// 리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		ExamBankDAO examBankDao = new ExamBankDAO();
		ExamBkContentsDTO examBkContentsDto	= null;

		RowSet rs = examBankDao.getExamBankContentsList(systemCode,courseId,Long.parseLong(bankCode));
		while(rs.next()){
			examBkContentsDto	= new ExamBkContentsDTO();
			examBkContentsDto.setCourseId(courseId);
			examBkContentsDto.setExamBkCode(Long.parseLong(bankCode));
			examBkContentsDto.setExamNo(rs.getInt("exam_no"));
			examBkContentsDto.setExamType(StringUtil.nvl(rs.getString("exam_type")));
			examBkContentsDto.setContentsText(StringUtil.setMaxLength(StringUtil.nvl(rs.getString("contents_text")),60));
			examBkContentsDto.setRfileName(StringUtil.nvl(rs.getString("rfile_name")));
			examBkContentsDto.setSfileName(StringUtil.nvl(rs.getString("sfile_name")));
			examBkContentsDto.setFilePath(StringUtil.nvl(rs.getString("file_path")));
			examBkContentsDto.setFileSize(StringUtil.nvl(rs.getString("file_size")));

			arrayList.add(examBkContentsDto);
		}
		rs.close();
		return arrayList;
	}

	/**
	 * 과제 첨부파일 삭제(Ajax)
	 * 2007.06.14 sangsang
	 * @param courseId
	 * @param bankCode
	 * @param examNo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String examBankContentsFileDelete(String courseId, int bankCode, int examNo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		ExamBankDAO examBankDao = new ExamBankDAO();
		int retVal = 0;
		retVal = examBankDao.delExamBankContentsFile(systemCode, courseId, bankCode,examNo,userId);

/*		// 첨부파일 삭제 안함-DB만 삭제
		ExamBkContentsDTO examBkContentsDto = new ExamBkContentsDTO();
		examBkContentsDto = examBankDao.getExamBkContentsInfo(systemCode, courseId, bankCode, examNo);
		String oldrFileName = StringUtil.nvl(examBkContentsDto.getRfileName());
		String oldsFileName = StringUtil.nvl(examBkContentsDto.getSfileName());
		String oldFilePath = StringUtil.nvl(examBkContentsDto.getFilePath());

		if(!oldrFileName.equals("") && !oldrFileName.equals("") && !oldrFileName.equals("")){
			FileUtil.delFile(oldFilePath, oldsFileName);
		}
*/
		return  String.valueOf(retVal);
	}

	/**
	 * 문제 은행 개별 문제 등록/수정 폼
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward examBankContentsWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);

		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pGubun = StringUtil.nvl(request.getParameter("pGubun"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pExamType = StringUtil.nvl(request.getParameter("pExamType"));
		int pExamNo = StringUtil.nvl(request.getParameter("pExamNo"),0);
		String pImsiBankCode = request.getParameter("pBankCode");
		long pBankCode = 0;
		if(pImsiBankCode != null && !pImsiBankCode.equals("")) pBankCode = Long.parseLong(pImsiBankCode);

		ExamBkContentsDTO contentsInfo = null;
		if(pMODE.equals("ADD")){
		     contentsInfo = new ExamBkContentsDTO();
		}else{
			ExamBankDAO bankDao = new ExamBankDAO();
			contentsInfo = bankDao.getExamBkContentsInfo(systemCode,pCourseId,pBankCode, pExamNo);
		}

		model.put("contentsInfo",contentsInfo);
		model.put("pMODE", pMODE);
		model.put("pGubun", pGubun);
        model.put("pCourseId", pCourseId);
		model.put("pExamNo",""+pExamNo);
		model.put("pExamType",pExamType);
		model.put("pBankCode",""+pBankCode);

        String navMsg = "문제등록";
        if(!pMODE.equals("ADD")) navMsg = "문제수정";

		new CurriSiteNavigation(pMode).add(request,navMsg).link(model);
		return forward(request, model, "/courseexam/exam_bk_contents_write.jsp");
	}
	/**
	 * 문제 은행 개별 문제 삭제
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward examBankContentsDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);

		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		int pExamNo = StringUtil.nvl(request.getParameter("pExamNo"),0);
		long pBankCode = Long.parseLong(request.getParameter("pBankCode"));


		log.debug("pCourseId =>"+pCourseId+",pExamType =>"+pExamNo);

		ExamBankDAO bankDao = new ExamBankDAO();
		int retVal = 0;
		retVal = bankDao.delExamBankContents(systemCode,pCourseId,pBankCode, pExamNo);

        new CurriSiteNavigation(pMode).add(request,"문제리스트").link(model);
		return redirect("/ExamBank.cmd?cmd=examBankContentsList&pCourseId="+pCourseId+"&pBankCode="+pBankCode);
	}

	public ActionForward examBankContentsRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),LECTURE);

		UserMemDTO userInfo = UserBroker.getUserInfo(request);
		String courseId = request.getParameter("pCourseId");
		String bankCode = request.getParameter("pBankCode");

		CurriMemDTO curriInfo = UserBroker.getCurriInfo(request);

		ExamBankDAO contentsDao = new ExamBankDAO();
		ExamHelper helper = new ExamHelper();
		ExamBkContentsDTO contentsInfo = new ExamBkContentsDTO();

		MultipartRequest multipartRequest = null;

		String filePath = FileUtil.FILE_DIR+systemCode+"/exam/bank/"+curriInfo.curriCode+"/"+curriInfo.curriYear+"/"+curriInfo.curriTerm+"/"+courseId+"/"+bankCode+"/";

		UploadFiles uploadEntity = FileUtil.upload(request, filePath,curriInfo.curriCode+curriInfo.curriYear+curriInfo.curriTerm);
		multipartRequest		=	uploadEntity.getMultipart();

		String pMODE = StringUtil.nvl(multipartRequest.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(multipartRequest.getParameter("pCourseId"));
		String pImsiBankCode = multipartRequest.getParameter("pBankCode");
		long pBankCode = 0;
		if(pImsiBankCode != null && !pImsiBankCode.equals("")) pBankCode = Long.parseLong(pImsiBankCode);
		helper.getExamBkContentsParam(multipartRequest, contentsInfo);
		contentsInfo.setCourseId(pCourseId);
		contentsInfo.setExamBkCode(pBankCode);
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

		}else{
            if(contentsInfo.getFileCheck().equals("")){
			    contentsInfo.setRfileName(contentsInfo.getOldRfile());
				contentsInfo.setSfileName(contentsInfo.getOldSfile());
				contentsInfo.setFileSize(contentsInfo.getOldFileSize());
				contentsInfo.setFilePath(contentsInfo.getOldFilePath());
            }
		}

		String pContents = "";
		/** 웹에디터 셋팅 추가 Start**/
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH+filePath;
		String WeasFileUrl =  ServiceUrl+"/data/"+filePath;
		VBN_files v_objFile = null;

		java.io.File dir = new java.io.File(WeasFilePath);
		if (!dir.exists())	dir.mkdirs();

		v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
		String contentsText = StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
		if(contentsText.equals("")){
			String returnUrl = "/ExamBank.cmd?cmd=examBankContentsWrite&pMODE="+pMODE+"&pCourseId="+pCourseId+"&pExamType="+contentsInfo.getExamType()+"&pBankCode="+pBankCode+"&pExamNo="+contentsInfo.getExamNo();
			FileUtil.delFile(filePath, contentsInfo.getSfileName());
			return notifyAndExit(systemCode,model, "문제 입력 오류입니다.",returnUrl,LECTURE);
		}

		contentsInfo.setContentsText(contentsText);
		pContents = v_objFile.VBN_uploadMultiFiles(contentsInfo.getContents(), multipartRequest);
		/** 웹에디터 셋팅 추가 End**/
		contentsInfo.setContents(pContents);


		if(pMODE.equals("ADD")){
	        contentsInfo.setRegId(userInfo.userId);
			contentsDao.addExamBankContentsInfo(systemCode, contentsInfo);
		}else{
			contentsInfo.setModId(userInfo.userId);
			contentsDao.editExamBankContentsInfo(systemCode, contentsInfo);
		}

		new CurriSiteNavigation(pMode).add(request,"문제리스트").link(model);
		return redirect("/ExamBank.cmd?cmd=examBankContentsList&pCourseId="+pCourseId+"&pBankCode="+pBankCode);
	}

	public ActionForward examBankContentsShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMODE = StringUtil.nvl(request.getParameter("pMODE"));
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pBankCode = StringUtil.nvl(request.getParameter("pBankCode"));
		String pExamType = StringUtil.nvl(request.getParameter("pExamType"));
		int pExamNo = StringUtil.nvl(request.getParameter("pExamNo"),0);

		ExamBankDAO bankDao = new ExamBankDAO();
		ExamBkContentsDTO contentsInfo =  bankDao.getExamBkContentsInfo(systemCode,pCourseId,Long.parseLong(pBankCode), pExamNo);

		model.put("contentsInfo",contentsInfo);
		model.put("pExamType",pExamType);
	    return forward(request, model, "/courseexam/exam_bk_contents_show.jsp");
	}
}

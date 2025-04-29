/*
 * Created on 2004. 10. 4.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.resultcourse.action;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dao.CommonDAO;
import com.edutrack.common.dto.CodeDTO;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.courseexam.dao.ExamInfoDAO;
import com.edutrack.courseforum.dao.CourseForumInfoDAO;
import com.edutrack.coursereport.dao.ReportAdminDAO;
import com.edutrack.curristudent.dao.StudentDAO;
import com.edutrack.currisub.dao.CurriContentsDAO;
import com.edutrack.currisub.dao.CurriSubCourseDAO;
import com.edutrack.currisub.dao.CurriSubDAO;
import com.edutrack.currisub.dto.CurriSubCourseDTO;
import com.edutrack.currisub.dto.CurriSubInfoDTO;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.edutrack.offlineattend.dao.OfflineAttendDAO;
import com.edutrack.resultcourse.dao.ResultCourseDAO;
import com.edutrack.resultcourse.dto.ResultCourseDTO;
import com.edutrack.resultcourse.dto.ResultScoreDTO;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author bschoi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResultCourseAction extends StrutsDispatchAction{


	/**
	 * 성적현황 리스트 페이징 처리
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward resultCoursePagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		String userType = userMemDto.userType;

		CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
		String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		String pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		CurriSubDAO curriSubDao = new CurriSubDAO();
		CurriSubInfoDTO curriSubInfoDto = curriSubDao.getCurriSubInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm);
		model.put("pCourseId",pCourseId);

		// 학점처리여부..
		if(curriSubInfoDto!=null){
			model.put("pScoreGubun", curriSubInfoDto.getScoreGubun());
			model.put("pEvalGubun", curriSubInfoDto.getEvalGubun() );
		}
		String naviMsg = "";

		if(("M").equals(userType) || ("P").equals(userType) )
			naviMsg ="평가관리";
		else
			naviMsg ="성적현황";

		new SiteNavigation(LECTURE).add(request,naviMsg).link(model);
		return forward(request, model, "/result_course/resultCoursePagingList.jsp");
	}



	/**
	 * 평가관리 목록 보여주기 .... Ajax
	 * @param pCourseId
	 * @param pOp
	 * @param pSearch
	 * @param curPage
	 * @param pagingYn : 페이징 처리 여부..
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ArrayList resultCourseListAuto(String pCourseId, String pOp, String pSearch, int  curPage, String pagingYn, String orderBy, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{

		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
		String userType = userMemDto.userType;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		CurriSubCourseDAO curriCourseDao = new CurriSubCourseDAO();
		ResultCourseDAO resultCourseDao	=	new ResultCourseDAO();
		ResultCourseDTO resultCourseDto	=	new ResultCourseDTO();
		CurriSubCourseDTO curriSubCourseDto	= new CurriSubCourseDTO();

		ListDTO listObj = new ListDTO();
		ArrayList dataList = new ArrayList();
		AjaxListDTO ajaxListDto = new AjaxListDTO();
		RowSet curriCourseInfo = null;
		RowSet resultCourseList = null;

		RowSet rs= null;
		if(!pCourseId.equals("")){

			if(("Y").equals(pagingYn)){
				listObj = resultCourseDao.getResultCoursePagingList(curPage, systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId, pOp, pSearch,orderBy);
			}else{
				rs= resultCourseDao.getResultCourseList(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,"",orderBy);
			}

			curriCourseInfo = curriCourseDao.getCurriSubCourseInfo(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId);

			if(curriCourseInfo.next()){
				curriSubCourseDto = new CurriSubCourseDTO();
				curriSubCourseDto.setExamBase(curriCourseInfo.getInt("exam_base"));
				curriSubCourseDto.setReportBase(curriCourseInfo.getInt("report_base"));
				curriSubCourseDto.setAttendBase(curriCourseInfo.getInt("attend_base"));
				curriSubCourseDto.setForumBase(curriCourseInfo.getInt("forum_base"));
				curriSubCourseDto.setEtc1Base(curriCourseInfo.getInt("etc1_base"));
				curriSubCourseDto.setEtc2Base(curriCourseInfo.getInt("etc2_base"));
				curriCourseInfo.close();
			}
		}


		//-- 등록된 시험, 과제, 토론 수를 가져온다.
		ExamInfoDAO	examInfoDao = new ExamInfoDAO();
		String examWhere = " and exam_type='Y' ";
		int examCnt = examInfoDao.getExamCnt(systemCode,curriMemDto,pCourseId,examWhere);
		ReportAdminDAO	reportInfoDao = new ReportAdminDAO();
		String reportWhere = " and regist_yn='Y' and parent_report_id=0 ";
		//int reportCnt = reportInfoDao.getReportInfoCount(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,reportWhere);
		int reportCnt = reportInfoDao.getReportInfoCount(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId);
		CourseForumInfoDAO	courseForumInfoDao = new CourseForumInfoDAO();
		String forumWhere = " and regist_yn='Y' and parent_forum_id=0 ";
		int forumCnt = courseForumInfoDao.getCourseForumInfoCount(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,forumWhere);

		if ( (("Y").equals(pagingYn) && listObj.getItemCount()>0 )
		   ||(("N").equals(pagingYn) && rs != null ) ) {

			if(("Y").equals(pagingYn)){
				rs= listObj.getItemList();
			}

			while(rs.next()){
				resultCourseDto = new ResultCourseDTO();
				resultCourseDto.setUserName(StringUtil.nvl(rs.getString("user_name")));
				resultCourseDto.setUserId(StringUtil.nvl(rs.getString("user_id")));
				resultCourseDto.setScoreExam(rs.getDouble("score_exam"));
				resultCourseDto.setScoreReport(rs.getDouble("score_report"));
				resultCourseDto.setScoreAttend(rs.getDouble("score_attend"));
				resultCourseDto.setScoreForum(rs.getDouble("score_forum"));
				resultCourseDto.setScoreEtc1(rs.getDouble("score_etc1"));
				resultCourseDto.setScoreEtc2(rs.getDouble("score_etc2"));
				resultCourseDto.setScoreTotal(rs.getDouble("score_total"));
				resultCourseDto.setGetCredit(StringUtil.nvl(rs.getString("get_credit")));
				resultCourseDto.setGrade(StringUtil.nvl(rs.getString("grade")));
				resultCourseDto.setEnrollStatus((rs.getString("enroll_status")));

				dataList.add(resultCourseDto);
			}
			rs.close();

			if(("Y").equals(pagingYn)){
				ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 리스트 시작 번호
				ajaxListDto.setDataList(dataList);								// 데이타리스트
				ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
			}else{
				ajaxListDto.setTotalDataCount(dataList.size());		// 리스트 시작 번호
				ajaxListDto.setDataList(dataList);								// 데이타리스트
				ajaxListDto.setPagging("");	// 페이징 스트링
			}
		}

		ArrayList retArray = new ArrayList();

		retArray.add(ajaxListDto);
		retArray.add(String.valueOf(examCnt));
		retArray.add(String.valueOf(reportCnt));
		retArray.add(String.valueOf(forumCnt));
		retArray.add(curriSubCourseDto);

		return retArray;
	}

	/**
	 * 성적현황 리스트 엑셀로 다운받기
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward resultCourseExcelDown(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("============================= resultCourseListExcelDown");
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));

		ResultCourseDAO resultCourseDao	=	new ResultCourseDAO();
		CurriSubCourseDAO curriCourseDao = new CurriSubCourseDAO();
		StudentDAO	studentDao = new StudentDAO();
		RowSet curriCourseInfo = null;
		RowSet resultCourseList = null;
		int studentCnt = 0;
		if(!pCourseId.equals("")){
			studentCnt = studentDao.getTotCount(systemCode,pCurriCode,pCurriYear,pCurriTerm,"SC");
			resultCourseList = resultCourseDao.getResultCourseList(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,"",null);
			curriCourseInfo = curriCourseDao.getCurriSubCourseInfo(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId);
		}
		model.put("pCourseId",pCourseId);
		model.put("curriCourseInfo", curriCourseInfo);
		model.put("resultCourseList", resultCourseList);
		model.put("studentCnt",String.valueOf(studentCnt));

		return excelDown(request,httpServletResponse, model, "/result_course/resultCourseExcelDown.jsp","resultCourseList.xls");
	}
	/**
	 * 성적 자동처리 팝업 프레임
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward resultCourseScoreAutoSetFrame(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=================================resultCourseScoreAutoSetFrame start");
		String 	pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		String 	pSetTarget 		= 	StringUtil.nvl(request.getParameter("pSetTarget"));

		model.put("pCourseId", pCourseId);
		model.put("pSetTarget", pSetTarget);
        return forward(request, model, "/result_course/resultCourseScoreAutoSetFrame.jsp");
    }
	/**
	 * 성적 자동처리(hidden 에서 진행)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward resultCourseScoreAutoSet(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=================================resultCourseScoreAutoSet start");
		UserMemDTO 	userMemDto 	= UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String 		systemCode 	= userMemDto.systemCode;
	    String 		regId 	  	= userMemDto.userId;
	    String 		pCurriCode 	= StringUtil.nvl(curriMemDto.curriCode);
		int 		pCurriYear 	= StringUtil.nvl(curriMemDto.curriYear,0);
		int 		pCurriTerm 	= StringUtil.nvl(curriMemDto.curriTerm,0);
		String 		pCourseId 	= StringUtil.nvl(request.getParameter("pCourseId"));
		String 		pSetTarget 	= StringUtil.nvl(request.getParameter("pSetTarget"));

		ResultCourseDAO resultCourseDao	=	new ResultCourseDAO();
		ResultCourseDTO resultCourseDto	=	new ResultCourseDTO();

		resultCourseDto.setSystemCode(systemCode);
		resultCourseDto.setCurriCode(pCurriCode);
		resultCourseDto.setCurriYear(pCurriYear);
		resultCourseDto.setCurriTerm(pCurriTerm);
		resultCourseDto.setCourseId(pCourseId);
		resultCourseDto.setRegId(regId);
		resultCourseDto.setModId(regId);

		int retVal =0;

	    model.put("retVal", String.valueOf(retVal));
        return forward(request, model, "/result_course/resultCourseScoreAutoSet.jsp");
    }
	/**
	 * 성적 자동처리 결과
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward resultCourseScoreAutoSetComplete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String 	retVal 		= 	StringUtil.nvl(request.getParameter("retVal"));
		model.put("retVal", retVal);
        return forward(request, model, "/result_course/resultCourseScoreAutoSetComplete.jsp");
    }


	/**
	 * 성적파일 업로드 프레임
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward resultCourseScoreUploadFrame(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=================================resultCourseScoreUploadFrame start");
		String 	pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));

		model.put("pCourseId", pCourseId);
        return forward(request, model, "/result_course/resultCourseScoreUploadFrame.jsp");
    }
	/**
	 * 성적파일 업로드 폼
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward resultCourseScoreUploadForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=================================resultCourseScoreUploadForm start");
		String 	pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));

		model.put("pCourseId", pCourseId);
        return forward(request, model, "/result_course/resultCourseScoreUploadForm.jsp");
    }

	/**
	 * 성적파일 업로드 진행(hidden 에서 처리)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward resultCourseScoreUpload(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=================================resultCourseScoreUpload start");
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String 	regId 	  = userMemDto.userId;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String 	pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));

		ResultCourseDAO resultCourseDao	=	new ResultCourseDAO();
		ResultCourseDTO resultCourseDto	=	new ResultCourseDTO();

		resultCourseDto.setSystemCode(systemCode);
		resultCourseDto.setCurriCode(pCurriCode);
		resultCourseDto.setCurriYear(pCurriYear);
		resultCourseDto.setCurriTerm(pCurriTerm);
		resultCourseDto.setCourseId(pCourseId);
		resultCourseDto.setRegId(regId);
		resultCourseDto.setModId(regId);

		int retVal =0;
		String msg = "";
		String objName = "";
		String rFileName = "";
		String sFileName = "";
		String filePath = "";
		String fileSize = "";
		int		wk_fcnt			=	0;//-- 라인수
		int		wk_ecnt1		=	0;//--  에러 수
		int tmpCols = 8;//-- 업로드된 파일과 비교할 컬럼 수
		MultipartRequest multipartRequest = null;

		String FilePath = FileUtil.FILE_DIR+systemCode+"/resultCourse/";
		log.debug("FilePath ==========================>"+FilePath);
//		  파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath,pCurriCode+"_"+pCurriYear+"_"+pCurriTerm+"_"+pCourseId+"_"+regId);

//		 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();
		String status = uploadEntity.getStatus();
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
//					-- 파일 읽어 들이기
				String FrootPath = FileUtil.UPLOAD_PATH;
				String read_file = FrootPath+FilePath+ sFileName;
				log.debug("__________ read_file = "+read_file);
				FileReader fr			=	new FileReader(read_file);
				BufferedReader	in		=	new BufferedReader(fr);
				String			st		=	null;
				log.debug("ha");
//					-- loop 파일 읽어 들여 돌림
				while ((st=in.readLine()) != null) {
					wk_fcnt++;
					StringTokenizer	Line=	new StringTokenizer(st, ",", false);
					int	cols				=	Line.countTokens();				//	항목수 (8개)
					log.debug("++++++++++++++++++++++++++++++++++++++++ cols = "+cols);

					if(cols != tmpCols) {
						msg = "<center><br><font color=red >"+wk_fcnt+"번째 라인의 항목수가 맞지 않습니다. </font><br></center>";
						wk_ecnt1	++;
					}
					else
					{
						String	token[]		=	new String[cols];
						int		idx			=	0;
						while (Line.hasMoreTokens()) {
							token[idx]		=	Line.nextToken();
							idx++;
						}
						resultCourseDto.setUserId(token[0]);
						resultCourseDto.setScoreExam(Double.parseDouble(token[1]));
						resultCourseDto.setScoreReport(Double.parseDouble(token[2]));
						resultCourseDto.setScoreAttend(Double.parseDouble(token[3]));
						resultCourseDto.setScoreForum(Double.parseDouble(token[4]));
						resultCourseDto.setScoreEtc1(Double.parseDouble(token[5]));
						resultCourseDto.setScoreEtc2(Double.parseDouble(token[6]));
						resultCourseDto.setScoreTotal(Double.parseDouble(token[7]));

						//-- Dto setting end
						retVal = resultCourseDao.registResultCourse(resultCourseDto);
					}
				}//while end

			}// For End
		}

	    model.put("retVal", String.valueOf(retVal));
        return forward(request, model, "/result_course/resultCourseScoreUpload.jsp");
    }
	/**
	 * 성적파일 업로드 결과
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward resultCourseScoreUploadComplete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String 	retVal 		= 	StringUtil.nvl(request.getParameter("retVal"));
		model.put("retVal", retVal);
        return forward(request, model, "/result_course/resultCourseScoreUploadComplete.jsp");
    }


	/**
	 * 성적을 등록한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward resultCourseScoreRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String 	systemCode 	= 	userMemDto.systemCode;
	    String 	regId 	  	= 	userMemDto.userId;
	    String 	pCurriCode 	= 	StringUtil.nvl(curriMemDto.curriCode);
	    int 	pCurriYear 	= 	StringUtil.nvl(curriMemDto.curriYear,0);
		int 	pCurriTerm 	= 	StringUtil.nvl(curriMemDto.curriTerm,0);
	    String 	pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		String 	pSelect 	= 	StringUtil.nvl(request.getParameter("pSelect"));
		String 	pStudentId	=	StringUtil.nvl(request.getParameter("pStudentId"));
		String[] pStudentIds=   null;
		int idLength = StringUtil.nvl(request.getParameter("idLength"),0);
		
		int 	retVal = 0;
		String msg = "에러가 발생하였습니다. 새로 진행해 주세요";

		ResultCourseDAO resultCourseDao	=	new ResultCourseDAO();
		ResultCourseDTO resultCourseDto	=	new ResultCourseDTO();
		
		if(pStudentId.equals("")){
			retVal = 1;
		}else{
			//double pScoreExam 	=	Double.parseDouble(StringUtil.nvl(request.getParameter("pScoreExam")));
			double pScoreReport =	Double.parseDouble(StringUtil.nvl(request.getParameter("pScoreReport")));
			double pScoreAttend =	Double.parseDouble(StringUtil.nvl(request.getParameter("pScoreAttend")));
			double pScoreForum 	=	Double.parseDouble(StringUtil.nvl(request.getParameter("pScoreForum")));
			double pScoreEtc1 	=	Double.parseDouble(StringUtil.nvl(request.getParameter("pScoreEtc1")));
			double pScoreEtc2 	=	Double.parseDouble(StringUtil.nvl(request.getParameter("pScoreEtc2")));
			double pScoreTotal 	=	Double.parseDouble(StringUtil.nvl(request.getParameter("pScoreTotal")));

			resultCourseDto.setSystemCode(systemCode);
			resultCourseDto.setCurriCode(pCurriCode);
			resultCourseDto.setCurriYear(pCurriYear);
			resultCourseDto.setCurriTerm(pCurriTerm);
			resultCourseDto.setCourseId(pCourseId);
			resultCourseDto.setUserId(pStudentId);
			//resultCourseDto.setScoreExam(pScoreExam);
			resultCourseDto.setScoreReport(pScoreReport);
			resultCourseDto.setScoreAttend(pScoreAttend);
			resultCourseDto.setScoreForum(pScoreForum);
			resultCourseDto.setScoreEtc1(pScoreEtc1);
			resultCourseDto.setScoreEtc2(pScoreEtc2);
			resultCourseDto.setScoreTotal(pScoreTotal);
			resultCourseDto.setRegId(regId);
			resultCourseDto.setModId(regId);
			//-- Dto setting end
			retVal = resultCourseDao.registResultCourse(resultCourseDto);
		}

		if(retVal <= 0){
			//-- 등록과정에 에러 발생시    메세지 띄우고 창닫기
			notifyCloseReload(systemCode,model,msg,LECTURE);
		}else{
		//-- 성공시 다음 학생 진행
			String[] aStudentIds = null;

			if(idLength > 0){//-- 다음 학생이 있는경우
				if(pSelect.equals("SEL")){
					pStudentIds = request.getParameterValues("pStudentIds");
					idLength = pStudentIds.length;
					aStudentIds = new String[idLength-1];
					for(int i=0 ;i<pStudentIds.length ;i++){
						if(i==0){
							pStudentId = pStudentIds[i];
						}else{
							aStudentIds[(i-1)] = pStudentIds[i];
						}
			        }
				}else if(pSelect.equals("ALL")){
					StudentDAO studentDao = new StudentDAO();
					int studentCnt = studentDao.getTotCount(systemCode,pCurriCode,pCurriYear,pCurriTerm,"SC");
					idLength = studentCnt;

					aStudentIds = new String[studentCnt-1];
					ArrayList arrList = new ArrayList();
					arrList = studentDao.getStudentList(systemCode,pCurriCode,pCurriYear,pCurriTerm);
					for(int i=0; i < arrList.size(); i++){
						String stId = (String)arrList.get(i);
						if(i==0){
							pStudentId = stId;
						}else{
							aStudentIds[(i-1)] = stId;
						}
					}
				}

				CurriSubCourseDAO curriCourseDao = new CurriSubCourseDAO();
				StudentDAO	studentDao = new StudentDAO();
				//-- 성적 배점 비율 정보 가져오기
				RowSet curriCourseInfo = curriCourseDao.getCurriSubCourseInfo(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId);
				model.put("curriCourseInfo", curriCourseInfo);
				//-- result_course 에 등록되어 있는 현재 점수 가져오기
				RowSet resultCourseInfo = resultCourseDao.getResultCourseList(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,pStudentId, null);
				resultCourseInfo.next();
				model.put("pStudentName", StringUtil.nvl(resultCourseInfo.getString("user_name")));
				model.put("pScoreExam", String.valueOf(resultCourseInfo.getDouble("score_exam")));
				model.put("pScoreReport", String.valueOf(resultCourseInfo.getDouble("score_report")));
				model.put("pScoreAttend", String.valueOf(resultCourseInfo.getDouble("score_attend")));
				model.put("pScoreForum", String.valueOf(resultCourseInfo.getDouble("score_forum")));
				model.put("pScoreEtc1", String.valueOf(resultCourseInfo.getDouble("score_etc1")));
				model.put("pScoreEtc2", String.valueOf(resultCourseInfo.getDouble("score_etc2")));

				//-- 시험 정보/점수 리스트 가져오기
				model.put("examScoreList", resultCourseDao.getStudentExamScoreList(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,pStudentId));
				//-- 과제 정보/점수 리스트 가져오기
				model.put("reportScoreList", resultCourseDao.getStudentReportScoreList(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,pStudentId));
				//-- 토론 정보/점수 리스트 가져오기
				model.put("forumScoreList", resultCourseDao.getStudentForumScoreList(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,pStudentId));
				//-- 출석
				CurriContentsDAO contentsDao = new CurriContentsDAO();
				model.put("attendView", contentsDao.getMainAttendShow(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,pStudentId));


//				 컨텐츠 목록(offline)
				OfflineAttendDAO offlineAttendDao = new OfflineAttendDAO();
				RowSet offAtList= offlineAttendDao.getOfflineAttendanceCnt(systemCode,pCurriCode,pCurriYear,pCurriTerm, pCourseId, pStudentId);	// only offline lesson
				model.put("offAtList", offAtList);

				model.put("pCourseId", pCourseId);
				model.put("pStudentId", pStudentId);
				model.put("idLength",String.valueOf(idLength));
				if(idLength > 0){
					log.debug("=================================aStudentIds="+aStudentIds.length);
					model.put("aStudentIds", aStudentIds);
				}

				log.debug("=================================resultCourseScoreRegist end");
		        return forward(request, model, "/result_course/resultCourseScoreWrite.jsp");

			}

		}//-- retVal if end
//		-- 다음 진행할 학생이 없는경우
		msg = "정상 처리가 되었습니다.";
//		--    메세지 띄우고 창닫기
		//return notifyCloseReload(systemCode,model,msg,LECTURE);
		return alertAndExit(systemCode, model, msg, CALLBACKURL+"opener.autoReload();self.close()", "POPUP");
    }//-- resultCourseScoreRegist End


	/**
	 * 성적을 학생에게 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward resultCourseStShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String 	systemCode 	= 	userMemDto.systemCode;
	    String 	userId 	  	= 	userMemDto.userId;
	    String 	pCurriCode 	= 	StringUtil.nvl(curriMemDto.curriCode);
		int 	pCurriYear 	= 	StringUtil.nvl(curriMemDto.curriYear,0);
		int 	pCurriTerm 	= 	StringUtil.nvl(curriMemDto.curriTerm,0);
		String 	pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		String  pCourseName	=	 StringUtil.nvl(request.getParameter("pCourseName"));

		if(pCourseId.equals("")){//-- 과목코드를 가져오지 않으면 첫번째 과목 코드를 사용한다.
			log.debug("_________________ non courseId");
			CommonDAO common = new CommonDAO();
		   	ArrayList codeList = null;
		   	CodeDTO code = null;
		   	codeList = common.getCurriCourse(systemCode,pCurriCode,pCurriYear,pCurriTerm,"");
	    	code = (CodeDTO)codeList.get(0);
	    	pCourseId = code.getCode();
	    	pCourseName = code.getName();
		}

		ResultCourseDAO resultCourseDao	=	new ResultCourseDAO();
		CurriSubCourseDAO curriCourseDao = new CurriSubCourseDAO();
		//-- 성적 배점 비율 정보 가져오기
		RowSet curriCourseInfo = curriCourseDao.getCurriSubCourseInfo(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId);
		model.put("curriCourseInfo", curriCourseInfo);
		//-- result_course 에 등록되어 있는 현재 점수 가져오기
		RowSet resultCourseInfo = resultCourseDao.getResultCourseList(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,userId,null);
		resultCourseInfo.next();
		model.put("pStudentName", StringUtil.nvl(resultCourseInfo.getString("user_name")));
		model.put("pScoreExam", String.valueOf(resultCourseInfo.getDouble("score_exam")));
		model.put("pScoreReport", String.valueOf(resultCourseInfo.getDouble("score_report")));
		model.put("pScoreAttend", String.valueOf(resultCourseInfo.getDouble("score_attend")));
		model.put("pScoreForum", String.valueOf(resultCourseInfo.getDouble("score_forum")));
		model.put("pScoreEtc1", String.valueOf(resultCourseInfo.getDouble("score_etc1")));
		model.put("pScoreEtc2", String.valueOf(resultCourseInfo.getDouble("score_etc2")));
		//-- 시험 정보/점수 리스트 가져오기
		model.put("examScoreList", resultCourseDao.getStudentExamScoreList(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,userId));
		//-- 과제 정보/점수 리스트 가져오기
		model.put("reportScoreList", resultCourseDao.getStudentReportScoreList(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,userId));
		//-- 토론 정보/점수 리스트 가져오기
		model.put("forumScoreList", resultCourseDao.getStudentForumScoreList(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,userId));
		CurriContentsDAO contentsDao = new CurriContentsDAO();
		//		출석
		model.put("attendView", contentsDao.getMainAttendShow(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,userId));
		//--	컨텐츠 목록(offline)
		OfflineAttendDAO offlineAttendDao = new OfflineAttendDAO();
		RowSet offAtList= offlineAttendDao.getOfflineAttendanceCnt(systemCode,pCurriCode,pCurriYear,pCurriTerm, pCourseId, userId);	// only offline lesson
		model.put("offAtList", offAtList);

		model.put("pCourseId", pCourseId);
		model.put("pCourseName", pCourseName);

		new SiteNavigation(LECTURE).add(request,"성적현황").link(model);
		return forward(request, model, "/result_course/resultCourseStShow.jsp");
    }

	
	
	/**
	 * 나의학습실에 학습자들 개인의 이전 평가를 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward resultCourseStTotalShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    String 	systemCode 	= 	userMemDto.systemCode;
	    String 	userId 	  	= 	userMemDto.userId;
	    
	    CurriSubDAO curriSubDao	=	new CurriSubDAO();
		RowSet rs = curriSubDao.getCurriYearTerm(systemCode, userId);
		
	    model.put("rs", rs);
	    
		new SiteNavigation(MYPAGE).add(request,"평가").link(model);
		return forward(request, model, "/result_course/resultCourseStTotalShow.jsp");
    }
	
	/**
	 * 나의학습실에 학습자들 개인의 이전 평가를 보여준다...ajax
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ArrayList resultCourseStTotalShowAuto(int pCurriYear, int pCurriTerm, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String 	systemCode 	= 	userMemDto.systemCode;
	    String 	userId 	  	= 	userMemDto.userId;
		ArrayList aList = new ArrayList();

		ResultCourseDAO resultCourseDao	=	new ResultCourseDAO();
		
		RowSet rs = resultCourseDao.getResultCourseListTotal(systemCode, pCurriYear, pCurriTerm, userId);
		ResultCourseDTO resultCourseDto = new ResultCourseDTO();
		
		while(rs.next()){
			resultCourseDto = new ResultCourseDTO();
			
			resultCourseDto.setCurriName(StringUtil.nvl(rs.getString("curri_name")));
			resultCourseDto.setScoreAttend(rs.getDouble("score_attend"));
			resultCourseDto.setScoreReport(rs.getDouble("score_report"));
			resultCourseDto.setScoreForum(rs.getDouble("score_forum"));
			resultCourseDto.setScoreEtc1(rs.getDouble("score_etc1"));
			resultCourseDto.setScoreTotal(rs.getDouble("score_total"));
			resultCourseDto.setGrade(StringUtil.nvl(rs.getString("grade")));
			
			aList.add(resultCourseDto);
		}
		rs.close();
		
		return aList;
    }
	
	
	

	/**
	 * 성적처리완료 처리
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward resultCourseCompleteScore(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("============================= resultCourseCompleteScore");
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String 	userId 	  	= 	userMemDto.userId;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));

		CurriSubDAO curriSubDao = new CurriSubDAO();
		CurriSubInfoDTO curriSubInfoDto = curriSubDao.getCurriSubInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm);

		String pScoreGubun = "";
		String pEvalGubun = "";

//		 학점처리 구분..
		if(curriSubInfoDto!=null){
			pScoreGubun = (String)curriSubInfoDto.getScoreGubun();
			pEvalGubun = (String)curriSubInfoDto.getEvalGubun();
		}


		int retVal = 0;
		boolean bRetVal = true;
		ResultCourseDAO resultCourseDao	=	new ResultCourseDAO();

		retVal = resultCourseDao.completeCurriSubCourse(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,userId);

		// 학점을 등록한다...상대평가일경우...
		if(("1").equals(pScoreGubun) && ("1").equals(pEvalGubun)){
			bRetVal = resultCourseDao.completeScoreProcess1(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,userId);
		}
		// 학점을 등록한다...절대평가일경우...
		if(("1").equals(pScoreGubun) && ("2").equals(pEvalGubun)){
			bRetVal = resultCourseDao.completeScoreProcess2(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,userId);
		}
		String msg="오류 발생 새로 진행해 주세요.";
		if(retVal > 0 && bRetVal ) msg = "정상 처리 되었습니다.";

		model.put("pCourseId",pCourseId);
		String returnUrl = CALLBACKURL+"parent.autoReload();";

		new SiteNavigation(LECTURE).add(request,"성적현황").link(model);
		return alertAndExit(systemCode, model, msg, returnUrl, LECTURE);
	}

	/**
	 * 계층별 점수현황을 팝업으로 보여준다. (메인)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward mainPopupResultCourseShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode 	= UserBroker.getSystemCode(request);
		ResultCourseDAO resultCourseDao	=	new ResultCourseDAO();

		String now_date = DateTimeUtil.getDate();
		model.put("deptresultList", resultCourseDao.getMainDeptScoreList(systemCode,now_date,0));
		return forward(request, model, "/result_course/resultCourseShowPopup.jsp");
	}

}

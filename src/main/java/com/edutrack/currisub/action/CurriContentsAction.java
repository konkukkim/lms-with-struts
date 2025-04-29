package com.edutrack.currisub.action;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import mediopia3.lpm.Loader;

import org.adl.datamodels.SCODataManager;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.config.dao.ConfigSubDAO;
import com.edutrack.coursebookmark.dao.BookmarkDAO;
import com.edutrack.courseforum.dao.CourseForumInfoDAO;
import com.edutrack.courseforum.dto.CourseForumInfoDTO;
import com.edutrack.curristudent.dao.StudentDAO;
import com.edutrack.curristudent.dto.StudentDTO;
import com.edutrack.currisub.dao.CurriContentsDAO;
import com.edutrack.currisub.dao.CurriQuizDAO;
import com.edutrack.currisub.dao.CurriSubCourseDAO;
import com.edutrack.currisub.dao.ScormStudyDAO;
import com.edutrack.currisub.dto.CurriContentsDTO;
import com.edutrack.currisub.dto.CurriCourseListDTO;
import com.edutrack.currisub.dto.LecContentsDTO;
import com.edutrack.curritop.dao.ContentsDAO;
import com.edutrack.curritop.dao.CourseDAO;
import com.edutrack.curritop.dao.CurriTopDAO;
import com.edutrack.curritop.dto.ContentsDTO;
import com.edutrack.curritop.dto.ContentsFileDTO;
import com.edutrack.curritop.dto.CurriTopDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;

/**
 * @author bschoi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CurriContentsAction extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();
	protected ArrayList WCMfileList = null;



	/**
	 * 상위 목차 번호를 가져온다.
	 * @param contentsOrder
	 * @return
	 * @throws Exception
	 */
	public String getParentOrder(String contentsOrder) throws Exception{
		String retVal = "";
		if(!contentsOrder.equals("")){
			String[] Orders = StringUtil.split(contentsOrder,".");
			int cnt = Orders.length;
			int depth = 0;
			int iOrder = 0;
			for(int i=0;i<cnt;i++){
				iOrder = Integer.parseInt(Orders[i]);
				if(iOrder > 0){
					depth = i+1;
					if(retVal.equals("")) retVal = Orders[i];
					else retVal = retVal+"."+Orders[i];
				}
			}
		}else{
			retVal = "";
		}
		return retVal;
    }

	public ActionForward curriContentsDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=============================================================curriContentsDelete start");
		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);
		String 	pCurriCode 	= StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 	= StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int 	pCurriTerm 	= StringUtil.nvl(request.getParameter("pCurriTerm"),0);
		String  pCourseId 		= StringUtil.nvl(request.getParameter("pCourseId"));
		String 	pCourseName 	= 	StringUtil.nvl(request.getParameter("pCourseName"));
		String 	pContentsType 	= 	StringUtil.nvl(request.getParameter("pContentsType"));
		String 	pContentsOrder 	= 	StringUtil.nvl(request.getParameter("pContentsOrder"));
		String 	pContentsName 	= 	StringUtil.nvl(request.getParameter("pContentsName"));
		String  targetOrder = "";
		int retVal = 0;
		CurriContentsDAO contentsDao = new CurriContentsDAO();

		if(pContentsType.equals("C")){
			String[] arrOrder = StringUtil.split(pContentsOrder,".");
		 	int splitCnt = arrOrder.length;
		 	for(int i=0;i<splitCnt;i++){
		 		if(Integer.parseInt(arrOrder[i]) > 0){
		 			targetOrder = targetOrder+arrOrder[i];
		 			if(i<splitCnt)targetOrder = targetOrder+".";
		 		}
			}
		}else{
			targetOrder = pContentsOrder;
		}
		retVal = contentsDao.delCurriContents(systemCode,pCurriCode,pCurriYear,pCurriTerm,pCourseId,targetOrder);

		log.debug("=============================================================curriContentsDelete end");
		return redirect("/CurriContents.cmd?cmd=curriContentsList&pCurriCode="+pCurriCode+"&pCurriYear="+pCurriYear+"&pCurriTerm="+pCurriTerm+"&pCourseId="+pCourseId+"&pCourseName="+pCourseName);
	}

	/**
	 * 교재 목차 정보를 등록/수정한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String curriContentsRegist(CurriContentsDTO curriContentsDto, String regMode, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		regMode = StringUtil.nvl(regMode);

		String curriCode = StringUtil.nvl(curriContentsDto.getCurriCode());
		int curriYear =	curriContentsDto.getCurriYear();
		int curriTerm =	curriContentsDto.getCurriTerm();
		String courseId = StringUtil.nvl(curriContentsDto.getCourseId());
		String contentsId = StringUtil.nvl(curriContentsDto.getContentsId());
		String contentsOrder = StringUtil.nvl(curriContentsDto.getContentsOrder());
		String contentsName = StringUtil.nvl(AjaxUtil.ajaxDecoding(curriContentsDto.getContentsName()));
		String contentsType = StringUtil.nvl(curriContentsDto.getContentsType());
		String serverPath 	= StringUtil.nvl(curriContentsDto.getServerPath());
		String filePath =  StringUtil.nvl(curriContentsDto.getFilePath());
		String asfFilePath =  StringUtil.nvl(curriContentsDto.getAsfFilePath());
		String sizeApp 	= 	StringUtil.nvl(curriContentsDto.getSizeApp(),"N");
		int showTime =	curriContentsDto.getShowTime();
		int contentsWidth =	curriContentsDto.getContentsWidth();
		int contentsHeight =	curriContentsDto.getContentsHeight();

		CurriContentsDAO curriContentsDao = new CurriContentsDAO();
		if(regMode.equals("ADD")){
			int totalContentsCnt = curriContentsDao.getTotalCurriContentsCnt(systemCode,curriCode,curriYear,curriTerm,courseId);
			contentsId = CommonUtil.getCurrentDate();
			int cntLength = String.valueOf(totalContentsCnt).length();
			for(int i=0;i<(6-cntLength);i++){
				contentsId=contentsId+"0";
			}
			contentsId = contentsId+totalContentsCnt;
			String parentOrder = getParentOrder(contentsOrder);
			contentsOrder = curriContentsDao.getMakeCurriContentsOrder(systemCode,curriCode,curriYear,curriTerm,courseId,parentOrder);
		}

		curriContentsDto.setSystemCode(systemCode);
		curriContentsDto.setCurriCode(curriCode);
		curriContentsDto.setCurriYear(curriYear);
		curriContentsDto.setCurriTerm(curriTerm);
		curriContentsDto.setCourseId(courseId);
		curriContentsDto.setContentsId(contentsId);
		curriContentsDto.setContentsOrder(contentsOrder);
		curriContentsDto.setContentsName(contentsName);
		curriContentsDto.setContentsType(contentsType);
		curriContentsDto.setSizeApp(sizeApp);
		curriContentsDto.setContentsWidth(contentsWidth);
		curriContentsDto.setContentsHeight(contentsHeight);
		if(contentsType.equals("C")){
			curriContentsDto.setFilePath(filePath);
			curriContentsDto.setAsfFilePath("");
			curriContentsDto.setServerPath("");
		}else{
			curriContentsDto.setFilePath(filePath);
			curriContentsDto.setAsfFilePath(asfFilePath);
			curriContentsDto.setServerPath(serverPath);
		}
		curriContentsDto.setShowTime(showTime);

		int retVal = 0;
		if(regMode.equals("ADD")){	// 입력모드
			curriContentsDto.setRegId(userId);
			retVal = curriContentsDao.addCurriContents(curriContentsDto);

		}else if(regMode.equals("EDIT")){	// 수정모드
			curriContentsDto.setModId(userId);
			retVal = curriContentsDao.editCurriContents(curriContentsDto);

		}else if(regMode.equals("DEL")){	// 삭제모드
			String  targetOrder = "";
			if(contentsType.equals("C")){
				String[] arrOrder = StringUtil.split(contentsOrder,".");
			 	int splitCnt = arrOrder.length;
			 	for(int i=0;i<splitCnt;i++){
			 		if(Integer.parseInt(arrOrder[i]) > 0){
			 			targetOrder = targetOrder+arrOrder[i];
			 			if(i<splitCnt)targetOrder = targetOrder+".";
			 		}
				}
			}else{
				targetOrder = contentsOrder;
			}

			retVal = curriContentsDao.delCurriContents(systemCode,curriCode,curriYear,curriTerm,courseId,targetOrder);
		}

		return String.valueOf(retVal);
	}

	/**
	 * 교재 목차 구성 화면(WebContentsManager) 을 띄운다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriWCM(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String 	pCurriCode 	= StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 	= StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int 	pCurriTerm 	= StringUtil.nvl(request.getParameter("pCurriTerm"),0);

		String 	pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		String 	pCourseName 	= 	StringUtil.nvl(request.getParameter("pCourseName"));
		String 	pWorkType 		= 	StringUtil.nvl(request.getParameter("pWorkType"));
		String 	pContentsType 	= 	StringUtil.nvl(request.getParameter("pContentsType"));
		String 	pContentsId 	= 	StringUtil.nvl(request.getParameter("pContentsId"));
		String 	pContentsOrder 	= 	StringUtil.nvl(request.getParameter("pContentsOrder"));
		String 	pContentsName 	= 	StringUtil.nvl(request.getParameter("pContentsName"));
		String 	pServerPath 	= 	StringUtil.nvl(request.getParameter("pServerPath"));
		String 	pShowTime 		= 	StringUtil.nvl(request.getParameter("pShowTime"));
		String  pServerPathDir 	=   "";
		String  ContentsRoot 	=   FileUtil.CONTENTS_DIR+systemCode+"/"+pCourseId;
		if(pServerPath.equals("")){
			pServerPath = ContentsRoot;
			log.debug("===========equals============== pServerPath = "+pServerPath);
			pServerPathDir = pServerPath;
		}else{
			pServerPathDir = pServerPath.substring(0,pServerPath.lastIndexOf("/"));
		}

//		String  pDirName = FileUtil.getFileDir(pServerPathDir);

		String	curPath		=	FileUtil.UPLOAD_PATH+ContentsRoot;
		ArrayList arrflist = new ArrayList();
		this.WCMfileList = new ArrayList();
		arrflist = WCMDirList(curPath,"0");
		this.WCMfileList = null;


		model.put("pCurriCode",pCurriCode);
		model.put("pCurriYear",String.valueOf(pCurriYear));
		model.put("pCurriTerm",String.valueOf(pCurriTerm));
		model.put("ContentsRoot", ContentsRoot);
		model.put("pFileList", arrflist);
		model.put("pCourseId", pCourseId);
        model.put("pCourseName", pCourseName);
        model.put("pWorkType", pWorkType);
        model.put("pContentsType", pContentsType);
        model.put("pContentsId", pContentsId);
        model.put("pContentsOrder", pContentsOrder);
        model.put("pContentsName", pContentsName);
        model.put("pServerPath", pServerPath);
        model.put("pServerPathDir", pServerPathDir);
        model.put("pShowTime", pShowTime);
        return forward(request, model, "/curri_contents/curriWCM.jsp");
    }
	/**
	 * 목차 구성(WebContentsManager)시 서버의 디렉토리 구성 정보를 가져오기 위한 Method
	 * @param filePath
	 * @param depth
	 * @return
	 * @throws Exception
	 */
	public ArrayList WCMDirList(String filePath,String depth)throws Exception{
		ContentsFileDTO fileDto = null;
		File	sfile		=	new File(filePath);
		String[] flist		=	sfile.list();
		int dirCnt =0;
		String fileDir = filePath.replaceAll(FileUtil.UPLOAD_PATH,"");
		//String fileDir = FileUtil.UPLOAD_PATH;
		for (int i=0; i<flist.length; i++) {
			fileDto = new ContentsFileDTO();
			File	sfile2		=	new File(filePath+"/"+flist[i]);
			String tmpDir="";

			String tmpDepth = depth;
			if(sfile2.isDirectory()){
				dirCnt++;
				if(depth.equals("0")) tmpDepth = String.valueOf(dirCnt);
				else tmpDepth = depth+"_"+String.valueOf(dirCnt);
				fileDto.setFileName(flist[i]);
				fileDto.setFileDir(fileDir);
				fileDto.setDepth(tmpDepth);
				fileDto.setThisDir(sfile2.isDirectory());
				fileDto.setThisFile(sfile2.isFile());
				this.WCMfileList.add(fileDto);
				tmpDir = filePath+"/"+flist[i];
				log.debug("========================= depth = "+depth);
				WCMDirList(tmpDir,tmpDepth);
			}
		}
		fileDto = null;
		return this.WCMfileList;
	}

	/**
	 * 목차 정보 리스트를 가져온다.(페이징 없음)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriContentsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		String 	pCateCode 	= StringUtil.nvl(request.getParameter("pCateCode"));
		String 	pCurriCode 	= StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 	= StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int 	pCurriTerm 	= StringUtil.nvl(request.getParameter("pCurriTerm"),0);
		String 	pCourseId 	= StringUtil.nvl(request.getParameter("pCourseId"));

//		 스콤 컨텐츠 미리보기를 위해 가상의 curriMemDto 생성
		CurriMemDTO curriInfo =  new CurriMemDTO();
		curriInfo.curriCode = pCurriCode;
		curriInfo.curriName = "preView";
		curriInfo.curriYear = String.valueOf(pCurriYear);
		curriInfo.curriTerm = String.valueOf(pCurriTerm);
		curriInfo.curriType = "R";
		UserBroker.setCurriInfo(request,curriInfo);
//		 스콤 컨텐츠 미리보기를 위해 가상의 curriMemDto 생성 End

		CourseDAO courseDao = new CourseDAO();
		RowSet rsCourse = courseDao.getCourseInfo(systemCode, pCourseId);
		if(rsCourse.next()){
			model.put("pContentsType", StringUtil.nvl(rsCourse.getString("contents_type")));
			model.put("pCourseName",StringUtil.nvl(rsCourse.getString("course_name")));
			model.put("pFlagNavi",StringUtil.nvl(rsCourse.getString("flag_navi")));
			model.put("pContentsWidth",StringUtil.nvl(rsCourse.getString("contents_width")));
			model.put("pContentsHeight",StringUtil.nvl(rsCourse.getString("contents_height")));
		}
		rsCourse.close();

		CurriQuizDAO quizDao = new CurriQuizDAO();
		int pQuizCnt = quizDao.getTotalCurriQuizCnt(systemCode,pCurriCode,pCurriYear,pCurriTerm, pCourseId,"");

		CurriContentsDAO contentsDao = new CurriContentsDAO();
		int pContentsCnt = contentsDao.getTotalCurriContentsCnt(systemCode,pCurriCode,pCurriYear,pCurriTerm, pCourseId);

		model.put("pQuizCnt",String.valueOf(pQuizCnt));
		model.put("pContentsCnt",String.valueOf(pContentsCnt));
		model.put("pCurriCode",pCurriCode);
		model.put("pCurriYear",String.valueOf(pCurriYear));
		model.put("pCurriTerm",String.valueOf(pCurriTerm));
		model.put("pCourseId",pCourseId);
		model.put("dateyn","Y");

		new SiteNavigation(pMode).add(request,"교재관리").link(model);
        return forward(request, model, "/curri_contents/curriContentsList.jsp");
	}

	/**
	 *
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param courseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList curriContentsListAuto(String curriCode, int curriYear, int curriTerm, String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		curriCode = StringUtil.nvl(curriCode);
		courseId = StringUtil.nvl(courseId);

		// 리턴 데이타 세팅
		//ArrayList arrayList = new ArrayList();
		CurriContentsDAO curriContentsDao = new CurriContentsDAO();
		//CurriContentsDTO curriContentsDto = null;

		RowSet rs = curriContentsDao.getCurriContentsList(systemCode,curriCode,curriYear,curriTerm, courseId, null);

		ArrayList arrayList = new ArrayList();
		CurriContentsDTO curriContentsDto = null;

		while(rs.next()){
			curriContentsDto = new CurriContentsDTO();
			curriContentsDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			curriContentsDto.setContentsId(StringUtil.nvl(rs.getString("contents_id")));
			curriContentsDto.setContentsOrder(StringUtil.nvl(rs.getString("contents_order")));
			curriContentsDto.setContentsName(StringUtil.nvl(rs.getString("contents_name")));
			curriContentsDto.setFilePath(StringUtil.nvl(rs.getString("file_path")));
			curriContentsDto.setServerPath(StringUtil.nvl(rs.getString("server_path")));
			curriContentsDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
			curriContentsDto.setQuizCount(StringUtil.nvl(rs.getString("quiz_count"),0));
			curriContentsDto.setQuizPoint(StringUtil.nvl(rs.getString("quiz_point"),0));
			curriContentsDto.setShowTime(StringUtil.nvl(rs.getString("show_time"),0));
			curriContentsDto.setContentsWidth(StringUtil.nvl(rs.getString("contents_width"),0));
			curriContentsDto.setContentsHeight(StringUtil.nvl(rs.getString("contents_height"),0));
			curriContentsDto.setSizeApp(StringUtil.nvl(rs.getString("size_app"),"N"));

			// 온/오프
			curriContentsDto.setLectureGubunCd(StringUtil.nvl(rs.getString("lecture_gubun")));
			curriContentsDto.setLectureGubun(StringUtil.nvl(CommonUtil.getCodeSoName(systemCode,"110",StringUtil.nvl(rs.getString("lecture_gubun"),"-1"))) );
			curriContentsDto.setStartDate(StringUtil.nvl(rs.getString("start_date")));
			curriContentsDto.setEndDate(StringUtil.nvl(rs.getString("end_date")));
			arrayList.add(curriContentsDto);
		}
		rs.close();

		return arrayList;
	}


	/**
	 * 컨텐츠 이월
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String curriContentsAuto(String curriCode, int curriYear, int curriTerm, String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String 	systemCode 	= UserBroker.getSystemCode(request);
		String 	userId 		= UserBroker.getUserId(request);

		curriCode = StringUtil.nvl(curriCode);
		courseId = StringUtil.nvl(courseId);

		int retVal = 0;
		CurriContentsDAO contentsDao = new CurriContentsDAO();
		retVal = contentsDao.addCurriContentsAuto(systemCode,curriCode,curriYear,curriTerm, courseId, userId);

		return String.valueOf(retVal);
	}

	public CurriContentsDTO curriContentsInfo(String curriCode, int curriYear, int curriTerm, String courseId, String contentsId, String workMode, String regContentsType, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		curriCode = StringUtil.nvl(curriCode);
		courseId = StringUtil.nvl(courseId);
		contentsId = StringUtil.nvl(contentsId);
		workMode = StringUtil.nvl(workMode);
		regContentsType = StringUtil.nvl(regContentsType);

		String contentsOrder="";
		String contentsType="";
		String parentContentsName = "";
		CurriContentsDAO curriContentsDao = new CurriContentsDAO();
		CurriContentsDTO curriContentsDto = null;
		RowSet rs = curriContentsDao.getCurriContents(systemCode, curriCode,curriYear,curriTerm, courseId, contentsId);

		if(rs.next()){

			curriContentsDto = new CurriContentsDTO();

			contentsOrder = rs.getString("contents_order");
			contentsType = StringUtil.nvl(rs.getString("contents_type"));

			String parentOrder = "";
			if(contentsType.equals("C") && regContentsType.equals("F")){
				parentContentsName = curriContentsDao.getCurriContentsNameByOrder(systemCode, curriCode,curriYear,curriTerm, courseId, contentsOrder);
			}else{
				parentOrder = getParentFullOrder(contentsOrder);
				if(!parentOrder.equals(""))
					parentContentsName = curriContentsDao.getCurriContentsNameByOrder(systemCode, curriCode,curriYear,curriTerm, courseId, parentOrder);
			}

			curriContentsDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			curriContentsDto.setContentsId(StringUtil.nvl(rs.getString("contents_id")));
			curriContentsDto.setContentsOrder(StringUtil.nvl(rs.getString("contents_order")));
			curriContentsDto.setContentsName(StringUtil.nvl(rs.getString("contents_name")));
			curriContentsDto.setFilePath(StringUtil.nvl(rs.getString("file_path")));
			curriContentsDto.setAsfFilePath(StringUtil.nvl(rs.getString("asffile_path")));
			curriContentsDto.setServerPath(StringUtil.nvl(rs.getString("server_path")));
			curriContentsDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
			curriContentsDto.setQuizCount(StringUtil.nvl(rs.getString("quiz_count"),0));
			curriContentsDto.setQuizPoint(StringUtil.nvl(rs.getString("quiz_point"),0));
			curriContentsDto.setShowTime(StringUtil.nvl(rs.getString("show_time"),0));
			curriContentsDto.setContentsWidth(StringUtil.nvl(rs.getString("contents_width"),0));
			curriContentsDto.setContentsHeight(StringUtil.nvl(rs.getString("contents_height"),0));
			curriContentsDto.setSizeApp(StringUtil.nvl(rs.getString("size_app"),"N"));

			curriContentsDto.setParentContentsName(parentContentsName);
			curriContentsDto.setRegContentsType(regContentsType);
			curriContentsDto.setWorkMode(workMode);

			curriContentsDto.setLectureGubun(StringUtil.nvl(rs.getString("lecture_gubun"),"1"));
			curriContentsDto.setStartDate(StringUtil.nvl(rs.getString("start_date")));
			curriContentsDto.setEndDate(StringUtil.nvl(rs.getString("end_date")));

		}
		rs.close();

		return curriContentsDto;
	}

	/**
	 * 상위목차 오더를 만든다.
	 * 2007.04.26 sangsang
	 * @param contentsOrder
	 * @return
	 * @throws Exception
	 */
	public String getParentFullOrder(String contentsOrder) throws Exception{
		String retVal = "";
		if(!contentsOrder.equals("")){
			String[] Orders = StringUtil.split(contentsOrder,".");
			int cnt = Orders.length;
			int depth = 0;
			int iOrder = 0;
			for(int i=0;i<cnt;i++){
				iOrder = Integer.parseInt(Orders[i]);
				if(iOrder > 0){
					depth = i+1;
					if(retVal.equals("")) retVal = Orders[i];
					else retVal = retVal+"."+Orders[i];
				}
			}

			if(retVal.lastIndexOf('.') > 0){
				retVal = retVal.substring(0,retVal.lastIndexOf('.'));
				int retValCnt = StringUtil.split(retVal,".").length;
				for(int i=retValCnt;i<cnt;i++){
					retVal +=".000";
				}
			}
			else
				retVal = "";
		}else{
			retVal = "";
		}
		return retVal;
    }

	/**
	 * 강의실 목차 정보를  가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward lecContents(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("---------- lecContents Start --------------");
		//----- 메모리에서 정보 가져오기.
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
		String viewUrl = "";

		//-- 컨텐츠 타입 검색
		CourseDAO	courseDao = new CourseDAO();
		RowSet courseInfo = courseDao.getCourseInfo(systemCode,pCourseId);
		courseInfo.next();
		String ContentsType = StringUtil.nvl(courseInfo.getString("contents_type"));
		courseInfo.close();

		//-- 스콤 데이터 저장 유무 결정(기본은 저장으로 설정)
		String credit = "credit";
		String lessonMode = "nomal";

		//---- 수강생인지 검색
		StudentDAO studentDao = new StudentDAO();

		model.put("pCourseId", pCourseId);
		model.put("pContentsId", pContentsId);
		model.put("pContentsType", ContentsType);

		int studentYn = studentDao.isStudent(systemCode, regId, pCurriCode, pCurriYear, pCurriTerm);

		ConfigSubDAO configSubDao = new ConfigSubDAO();
		RowSet ConfigRs = configSubDao.getConfigSub(systemCode, "400", "001");
		ConfigRs.next();
		String QuizConfigYn = StringUtil.nvl(ConfigRs.getString("config_value"));
		ConfigRs.close();
		model.put("QuizConfigYn", QuizConfigYn);

		if(studentYn > 0) {
			//---- bookmarking 정보 저장
			log.debug("---- set Bookmark ----");
			BookmarkDAO bookmarkDao = new BookmarkDAO();
			bookmarkDao.setBookmark(systemCode, regId, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pContentsId, studentYn);
			viewUrl = "/curri_contents/lecContentsFrame.jsp";

		} else {
			credit = "no-credit";
			lessonMode = "browse";
			viewUrl = "/curri_contents/lecContentsFrame.jsp";
		}

		log.debug("================ ContentsType = "+ContentsType);
//		-- 컨텐츠 타입이 SCORM 형(S) 인경우
		if(ContentsType.equals("S")){
			log.debug("Scrom data");

			//-- 교재 정보를 가져온다.    cmi_core 셋팅용
			CurriContentsDAO curriContentsDao = new CurriContentsDAO();
			RowSet rs = curriContentsDao.getCurriContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pContentsId);
			rs.next();
			// prerequisite, pre_type, timelimitaction, datafromlms, masteryscore, maxtimeallowed
			String  prerequisite= StringUtil.nvl(rs.getString("prerequisite"));
			String  pre_type= StringUtil.nvl(rs.getString("pre_type"));
			String  timelimitaction= StringUtil.nvl(rs.getString("timelimitaction"));
			String  datafromlms= StringUtil.nvl(rs.getString("datafromlms"));
			String  masteryscore= StringUtil.nvl(rs.getString("masteryscore"));
			String  maxtimeallowed= StringUtil.nvl(rs.getString("maxtimeallowed"));
			rs.close();
			String homeDir = FileUtil.ABS_PATH;
			String scoFile =  homeDir + FileUtil.FILE_DIR+systemCode+"/temp_rtefiles/"+pCourseId+"/"+regId;
			log.debug("======================= scoFile = "+scoFile);
            String RTESCODataFile = "";
            File theRTESCODataFile = null;
            ScormStudyDAO	scormStudyDao	= 	new ScormStudyDAO();
            ScormStudyAction	scormStudyAction	= 	new ScormStudyAction();
            SCODataManager 	rteSCOData = new SCODataManager();

            scormStudyAction.set_session(request, "scoID", pContentsId);
            scormStudyAction.set_session(request, "credit", credit);
            scormStudyAction.set_session(request, "lessonMode", lessonMode);
            try {
                File theRTESCODataDir = new File( scoFile );
                if ( !theRTESCODataDir.isDirectory() ) {
//                	log.debug("======================= theRTESCODataDir.mkdirs() ");
                    theRTESCODataDir.mkdirs();
                }else {
                    String[] file_list      = theRTESCODataDir.list();
                    for(int i= 0 ; i < file_list.length ; i++) {                //  file delete
                        File f = new java.io.File(theRTESCODataDir + "/" + file_list[i]);
                        boolean tf      = f.delete();
                    }
                }

                RTESCODataFile = scoFile + "/" + pContentsId;
//                log.debug("======================= RTESCODataFile = "+RTESCODataFile);
                theRTESCODataFile = new File( RTESCODataFile );

                int chkCnt =  scormStudyDao.getCmiCoreCnt(systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pContentsId,regId);

                if (chkCnt > 0) {
                    rteSCOData = scormStudyAction.existSCOData(rteSCOData, systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pContentsId,regId);
//                    log.debug("++++++++++++++++ chkCnt > 0");
                } else {
                    rteSCOData = scormStudyAction.initSCOData(rteSCOData, systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pContentsId,regId,userName
                    		, masteryscore,maxtimeallowed, timelimitaction, datafromlms, credit, lessonMode);
//                    log.debug("++++++++++++++++ chkCnt !> 0");
                }
                FileOutputStream fo    = new FileOutputStream( RTESCODataFile );
                ObjectOutputStream outPut = new ObjectOutputStream( fo );
                outPut.writeObject( rteSCOData );
                outPut.close();

            } catch ( Exception e ) {
                  e.printStackTrace();
            }

		}

		log.debug("---------- lecContents End   -------------- viewUrl = "+viewUrl);
		new SiteNavigation(LECTURE).add(request,"교안목차리스트").link(model);
		return forward(request, model, viewUrl);
	}

	/**
	 * 해당 컨텐츠 수강 가능 한지 검색.. 1은 가능 2는 불가능
	 * @param systemCode
	 * @param userId
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param courseId
	 * @param contentsId
	 * @param studentYn
	 * @return int
	 * @throws Exception
	 */
	public int isLearningYn(String systemCode, String userId, String curriCode, int curriYear, int curriTerm, String courseId, String contentsId) throws Exception {
		int retVal = 0;
		//---- 설정 검색
		ConfigSubDAO configSubDao = new ConfigSubDAO();
		RowSet rs = configSubDao.getConfigSub(systemCode, "400", "001");
		rs.next();
		String QuizYn = StringUtil.nvl(rs.getString("config_value"));
		rs.close();
		if(QuizYn.equals("Y")) {//---- 퀴즈통과 해야 할 경우
			//---- 상위 퀴즈를 모두 통과했는지 검색.
			BookmarkDAO bookmarkDao = new BookmarkDAO();
			if(bookmarkDao.isQuizPass(systemCode, userId, curriCode, curriYear, curriTerm, courseId, contentsId) > 0) retVal =1;
			else retVal = 0;
		} else {
			retVal = 1;
		}
		return retVal;
	}

	/**
	 * 강의실 목차 정보  가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward lecContentsShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("---------- lecContentsShow Start --------------");
		//----- 메모리에서 정보 가져오기.
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String regId = userMemDto.userId;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
	    int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
	    int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pContentsId = StringUtil.nvl(request.getParameter("pContentsId"));
		String contentsBase = "/data/";

		CurriContentsDAO curriContentsDao = new CurriContentsDAO();
		RowSet rs = curriContentsDao.getCurriContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pContentsId);
		rs.next();

		/*********    modified  --> BalckMan**/
		String ContentsUrl = StringUtil.nvl(rs.getString("server_path"));
		int chkPosition = ContentsUrl.lastIndexOf("/CMU/");

		chkPosition = 5;
		String chkPath = "";
		chkPath = ContentsUrl.substring(0,chkPosition);

		if(!chkPath.equals("/REP/"))
			  ContentsUrl = contentsBase+ContentsUrl;
		/*********    modified  --> BalckMan**/
		rs.close();
		log.debug("--- Contentsurl : "+ContentsUrl);
		model.put("ContentsUrl", ContentsUrl);
		log.debug("---------- lecContentsShow End   --------------");
		return forward(request, model, "/curri_contents/lecContentsShow.jsp");
	}

	/**
	 * 강의실 목차 정보 리스트를 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward lecBookmarkStart(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("---------- lecBookmarkStart Start --------------");
		//----- 메모리에서 정보 가져오기.
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String regId = userMemDto.userId;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
	    int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pContentsId = StringUtil.nvl(request.getParameter("pContentsId"));

		//---- 현재 시간을 구해 온다.
		String StartTime = CommonUtil.getCurrentDate();
		String CONTEXTPATH = request.getContextPath();
		String BookmarkUrl = CONTEXTPATH+"/CurriContents.cmd?cmd=lecBookmarkEnd&pCourseId="+pCourseId+"&pContentsId="+pContentsId+"&pStartTime="+StartTime;
		model.put("pCourseId",pCourseId);
		model.put("pContentsId",pContentsId);
		model.put("pStartTime",StartTime);
		model.put("BookmarkUrl",BookmarkUrl);
		log.debug("StartTime : "+StartTime);

		log.debug("---------- lecBookmarkStart End   --------------");
		return forward(request, model, "/curri_contents/lecBookmarkStart.jsp");
	}

	/**
	 * 스콤 컨텐츠 업로드 프레임
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriSCMFrame(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String 	pCurriCode 	= StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 	= StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int 	pCurriTerm 	= StringUtil.nvl(request.getParameter("pCurriTerm"),0);
		String 	pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		model.put("pCurriCode",pCurriCode);
		model.put("pCurriYear",String.valueOf(pCurriYear));
		model.put("pCurriTerm",String.valueOf(pCurriTerm));
		model.put("pCourseId", pCourseId);
        return forward(request, model, "/curri_contents/curriSCMFrame.jsp");
    }
	/**
	 * 스콤 컨텐츠 업로드 폼
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriSCMUploadForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String 	pCurriCode 	= StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 	= StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int 	pCurriTerm 	= StringUtil.nvl(request.getParameter("pCurriTerm"),0);
		String 	pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));

		CurriContentsDAO curriContentsDao = new CurriContentsDAO();
		int pContentsCnt = curriContentsDao.getTotalCurriContentsCnt(systemCode, pCurriCode, pCurriYear, pCurriTerm,pCourseId);
		model.put("pCurriCode",pCurriCode);
		model.put("pCurriYear",String.valueOf(pCurriYear));
		model.put("pCurriTerm",String.valueOf(pCurriTerm));
		model.put("pCourseId", pCourseId);
        model.put("pContentsCnt", String.valueOf(pContentsCnt));
        return forward(request, model, "/curri_contents/curriSCMUploadForm.jsp");
    }
	/**
	 * 스콤 컨텐츠 업로드에서 사용하는 contents_order 만들기
	 * @param contents_order
	 * @param add_no
	 * @return	String
	 */
	public String fn_make_order(String contents_order, int add_no){
		String rtnOrder = "";
		if(add_no > 0){
			int c_num = Integer.parseInt(contents_order.substring(0,3))+add_no;
			if(c_num < 100) rtnOrder = "0";
			if(c_num < 10)  rtnOrder +="0";
			rtnOrder += Integer.toString(c_num);
			rtnOrder += contents_order.substring(3);
		}else{
			rtnOrder =  contents_order;
		}
		return rtnOrder;
	}
	/**
	 * 스콤 컨텐츠 업로드 진행(hidden 에서 처리)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriSCMUpload(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=================================SCMUpload start");
		String 	systemCode 		= 	UserBroker.getSystemCode(request);
		String 	regId 			= 	UserBroker.getUserId(request);
		String 	pCurriCode 		= 	StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 		= 	StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int 	pCurriTerm 		= 	StringUtil.nvl(request.getParameter("pCurriTerm"),0);
		String 	pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		String 	pUploadMode 		= 	StringUtil.nvl(request.getParameter("pUploadMode"));
		String 	ContentsRoot 	=   FileUtil.CONTENTS_DIR+systemCode+"/"+pCourseId;
		String 	ParamXmlPath		=	FileUtil.UPLOAD_PATH+ContentsRoot+"/parameters.xml";
		CurriContentsDAO contentsDao	=	new CurriContentsDAO();
		CurriContentsDTO contentsDto	=	null;

		int addOrder = 0;
		int retVal =0;
		String msg = "";
		if(pUploadMode.equals("ADD"))
			addOrder = Integer.parseInt(contentsDao.getMakeCurriContentsOrder(systemCode, pCurriCode, pCurriYear, pCurriTerm,pCourseId,"").substring(0,3));

		log.debug("___________________ ParamXmlPath   "+ParamXmlPath);
		Loader ld = new Loader(request.getInputStream(), ParamXmlPath);
		log.debug("___________________ FileUtil.UPLOAD_PATH = "+FileUtil.UPLOAD_PATH);
		String course_id = ld.getParaValue("CourseID");
		log.debug("___________________ course_id   "+course_id);
	    if (ld.paraOK.equalsIgnoreCase("OK")) {

	    	if(pUploadMode.equals("MOD"))
	    		retVal = contentsDao.delCurriContents(systemCode, pCurriCode, pCurriYear, pCurriTerm,pCourseId,"");
	    	int itemSize = ld.itemInfoSet.size();
	        String rootPath = ld.zipFileFolder;
	        String itemID               = new String();
	        String itemOrder            = new String();
	        String itemName             = new String();
	        String itemPath             = new String();
	        String itemType             = new String();
	        String itemPreRequisite     = new String();
	        String itemPreType          = new String();
	        String itemMaxTimeAllowed   = new String();
	        String itemTimeLimitAction  = new String();
	        String itemDataFromLMS      = new String();
	        String itemMasteryScore     = new String();
			String itemIsVisible		= new String();

			for (int i=0;i<itemSize;i++) {
	            Vector itemInfo = (Vector)ld.itemInfoSet.get(i);
	            itemID               = (String)itemInfo.get(0);
	            itemOrder            = (String)itemInfo.get(1);
	            itemName             = (String)itemInfo.get(2);
	            itemPath             = (String)itemInfo.get(3);
	            itemType             = (String)itemInfo.get(4);
	            itemPreRequisite     = (String)itemInfo.get(5);
	            itemPreType          = (String)itemInfo.get(6);
	            itemMaxTimeAllowed   = (String)itemInfo.get(7);
	            itemTimeLimitAction  = (String)itemInfo.get(8);
	            itemDataFromLMS      = (String)itemInfo.get(9);
	            itemMasteryScore     = (String)itemInfo.get(10);
				itemIsVisible		 = (String)itemInfo.get(11);

	            if (itemPath != null && !itemPath.equalsIgnoreCase("null") && !itemPath.equals("") && !itemPath.equals(" ")) {
	                itemPath = rootPath + itemPath;
					//-- For kimos & ituniv	 서버 패스 있으면 F 로 설정
					//itemType = "F";
	                itemPath = StringUtil.ReplaceAll(itemPath,FileUtil.UPLOAD_PATH,"");
	            }
	            else {
	                itemPath = "";
	            }

				if(itemIsVisible.equals("T")){
					//-- 데이터 입력
					contentsDto 	= new CurriContentsDTO();
					contentsDto.setSystemCode(systemCode);
					contentsDto.setCurriCode(pCurriCode);
					contentsDto.setCurriYear(pCurriYear);
					contentsDto.setCurriTerm(pCurriTerm);
					contentsDto.setCourseId(pCourseId);
					contentsDto.setContentsId(CommonUtil.getCurrentDate()+"_"+itemID);
					contentsDto.setContentsOrder(fn_make_order(itemOrder,addOrder));
					log.debug("___________________ fn_make_order(itemOrder,addOrder)= "+fn_make_order(itemOrder,addOrder));
					contentsDto.setContentsName(StringUtil.outDataConverter(itemName));
					contentsDto.setFilePath(itemPath);
					contentsDto.setServerPath(itemPath);
					contentsDto.setContentsType(itemType);
					contentsDto.setShowTime(0);
					contentsDto.setPreRequisite(itemPreRequisite);
					contentsDto.setPreType(itemPreType);
					contentsDto.setTimeLimitAction(itemTimeLimitAction);
					contentsDto.setDataFromLms(itemDataFromLMS);
					contentsDto.setMasteryScore(itemMasteryScore);
					contentsDto.setMaxTimeAllowed(itemMaxTimeAllowed);
					contentsDto.setRegId(regId);
					log.debug("___________________ chk insert");
					retVal = contentsDao.addCurriContents(contentsDto);

				}
			}
	    }

		if(retVal >0){
			msg = "정상 업로드 되었습니다.";
		}else{
			msg = "업로드에 문제가 발생하였습니다. <br> 처음부터 다시 진행해 주세요 <br> 지속적인 문제 발생시 관리자에게 문의해 주세요.";
		}

	    model.put("retVal", String.valueOf(retVal));
        return forward(request, model, "/curri_contents/curriSCMUpload.jsp");
    }
	/**
	 * 스콤 컨텐츠 업로드 결과
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriSCMComplete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String 	retVal 		= 	StringUtil.nvl(request.getParameter("retVal"));
		model.put("retVal", retVal);
        return forward(request, model, "/curri_contents/curriSCMComplete.jsp");
    }

	/**
	 * 과정의 학생별 수강진도현황을 가져옵니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward lecStudyStatus(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		int				pDispLine		=	StringUtil.nvl(request.getParameter("pDispLine"), 10);
		int				curPage			=	StringUtil.nvl(request.getParameter("curPage"), 1);

		model.put("curPage", String.valueOf(curPage));
		model.put("pDispLine", String.valueOf(pDispLine));

		new SiteNavigation(LECTURE).add(request,"진도현황").link(model);
		//return forward(request, model, viewUrl);
		return forward(request, model, "/curri_contents/autoLecStudyStatusPagingList.jsp");
	}

	/**
	 * AJAX 적용 - 과정별 학생별 수강현황 정보를 가져온다.
	 * @param curPage
	 * @param dispLine
	 * @param pSearchKey
	 * @param pKeyWord
	 * @param request
	 * @param response
	 *
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO  autoLecStudyStatus(int curPage, int dispLine, String pSearchKey, String pKeyWord, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("---------- AutoLecStudyStatus Start --------------");

		//----- 메모리에서 정보 가져오기.
		UserMemDTO		userMemDto		=	UserBroker.getUserInfo(request);
		CurriMemDTO		curriMemDto		= 	userMemDto.curriInfo;
	    String			systemCode		= 	userMemDto.systemCode;
	    String 			pCurriCode 		= 	StringUtil.nvl(curriMemDto.curriCode);
		int 			pCurriYear 		= 	StringUtil.nvl(curriMemDto.curriYear,0);
		int			 	pCurriTerm 		= 	StringUtil.nvl(curriMemDto.curriTerm,0);

		curPage = (curPage == 0) ? 1 : curPage;
		dispLine = (dispLine == 0) ? 10 : dispLine;
		String		pOrder 			= 	"";

		AjaxListDTO			ajaxListDto			=	new AjaxListDTO();
		CurriContentsDAO	curriContentsDao	=	new CurriContentsDAO();
		
		//데이터를 담는다.
		ListDTO listObj = null;
		listObj	=	curriContentsDao.getAutoLecStudyStatusPagingList(curPage, dispLine, 10, systemCode, pCurriCode, pCurriYear, pCurriTerm, pSearchKey, pKeyWord, pOrder);

		ArrayList		dataList	=	new ArrayList();
		StudentDTO 		studentDto 	= 	new StudentDTO();

		if (listObj.getItemCount() > 0) {

			RowSet rs= listObj.getItemList();
			while(rs.next()){
				studentDto	=	new StudentDTO();
				studentDto.setUserId(StringUtil.nvl(rs.getString("user_id")));
				studentDto.setUserName(StringUtil.nvl(rs.getString("user_name")));
				studentDto.setTotalTime(rs.getInt("total_time"));		//-- 수강시간
				studentDto.setAllAttend(rs.getInt("all_attend"));		//-- 총 오프라인 출석 수
				studentDto.setAttendCnt(rs.getInt("attendance"));		//-- 오프라인 출석 수(O일 때만)
				studentDto.setCurriPercent(rs.getDouble("pro_rate"));	//-- 진도율
				studentDto.setAllReportCnt(rs.getInt("all_report_cnt"));
				studentDto.setReportCnt(rs.getInt("report_cnt"));
				studentDto.setAllExamCnt(rs.getInt("all_exam_cnt"));
				studentDto.setExamCnt(rs.getInt("exam_cnt"));
				studentDto.setAllForumCnt(rs.getInt("all_forum_cnt"));
				studentDto.setForumCnt(rs.getInt("forum_cnt"));

				dataList.add(studentDto);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}

		return ajaxListDto;
	}


	/**
	 * 특정 수강생의 각 단원별 수강 현황을 가져 옵니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward lecStudyStatusShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("---------- lecContentsList Start --------------");
		//----- 메모리에서 정보 가져오기.
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String regId = userMemDto.userId;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
		int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pContentsType = StringUtil.nvl(request.getParameter("pContentsType"));
		String pStudentId = StringUtil.nvl(request.getParameter("pStudentId"));
		String viewUrl = "";

		CurriContentsDAO curriContentsDao = new CurriContentsDAO();
		RowSet contentsList = null;
		contentsList = curriContentsDao.getBookmarkContentsList(systemCode, pCurriCode, pCurriYear, pCurriTerm, "", pStudentId);
		viewUrl = "/curri_contents/lecStudyStatusShow.jsp";
		model.put("contentsList", contentsList);
		model.put("pStudentId", pStudentId);
		model.put("pStudentName", CommonUtil.getUserName(systemCode,pStudentId));
		new SiteNavigation(LECTURE).add(request,"진도현황").link(model);
		return forward(request, model, viewUrl);
	}


	public ActionForward SiCM(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String 	pCurriCode 	= StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 	= StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int 	pCurriTerm 	= StringUtil.nvl(request.getParameter("pCurriTerm"),0);
		String 	pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		String 	pContentsOrder 	= StringUtil.nvl(request.getParameter("pContentsOrder"));
		model.put("pCurriCode",pCurriCode);
		model.put("pCurriYear",String.valueOf(pCurriYear));
		model.put("pCurriTerm",String.valueOf(pCurriTerm));
		model.put("pCourseId", pCourseId);
		model.put("pContentsOrder", pContentsOrder);
        return forward(request, model, "/curri_contents/SiCM.jsp");
    }

	public ActionForward SiCMRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode 		= 	UserBroker.getSystemCode(request);
		String 	pCurriCode 	= StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 	= StringUtil.nvl(request.getParameter("pCurriYear"),0);
		int 	pCurriTerm 	= StringUtil.nvl(request.getParameter("pCurriTerm"),0);
		String regId 			= 	UserBroker.getUserId(request);
		String pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		String[] selectedItem = null;
		String arrTarget[] = request.getParameterValues("checkBoxSelected");
		String identifier = "";
		String titleName = "";
		String strselectedItem = "";
		String tmpOrgId = "";
		String tmpOrgName = "";
		String tmpParentOrder = StringUtil.nvl(request.getParameter("pContentsOrder"));
		log.debug("check tmpParentOrder+++"+tmpParentOrder);
		int retVal = 0;
		String  pContentsId = "";
		String  pContentsName = "";
		String  pContentsOrder = "";
		String  ParentOrder = "";
		String 	pContentsType 	= "";

		CurriContentsDAO contentsDao = new CurriContentsDAO();
		CurriContentsDTO contentsDto = new CurriContentsDTO();
		contentsDto.setSystemCode(systemCode);
		contentsDto.setCurriCode(pCurriCode);
		contentsDto.setCurriYear(pCurriYear);
		contentsDto.setCurriTerm(pCurriTerm);
		contentsDto.setCourseId(pCourseId);
		contentsDto.setFilePath("");
		contentsDto.setServerPath("");
		contentsDto.setShowTime(0);
		contentsDto.setRegId(regId);

		int pShowTime 		= 	0;
		String msg = "";
		for (int i = 0; i < arrTarget.length; i++) {

			strselectedItem = strselectedItem + arrTarget[i]+"<br>";
			selectedItem = StringUtil.split(arrTarget[i],"|");
			log.debug("check org ++++"+tmpOrgId+"+++++"+selectedItem[0]);
			if((i==0 || !tmpOrgId.equals(selectedItem[0])) && tmpParentOrder.equals("")){
				tmpOrgId	=	selectedItem[0];
				tmpOrgName 	= 	selectedItem[1];
				//-- 장 등록 작업
				pContentsType = "C";
				pContentsOrder = contentsDao.getMakeCurriContentsOrder(systemCode,pCurriCode, pCurriYear, pCurriTerm,pCourseId,"");
				tmpParentOrder = pContentsOrder;
				contentsDto.setContentsId(tmpOrgId);
				contentsDto.setContentsOrder(pContentsOrder);
				contentsDto.setContentsName(tmpOrgName);
				contentsDto.setContentsType(pContentsType);

				log.debug("++++"+pContentsType+"+++++"+pContentsOrder);
				retVal = contentsDao.addCurriContents(contentsDto);

			}
			//-- 단원 등록 작업
			identifier = selectedItem[2];
			titleName = selectedItem[3];
			log.debug("chk tmpParentOrder ++++"+tmpParentOrder);
			pContentsType = "F";
			ParentOrder = getParentOrder(tmpParentOrder);
			pContentsOrder = contentsDao.getMakeCurriContentsOrder(systemCode,pCurriCode, pCurriYear, pCurriTerm,pCourseId,ParentOrder);
			pShowTime 		= 	StringUtil.nvl(request.getParameter("showTime_"+identifier),0);
			contentsDto.setContentsId(identifier);
			contentsDto.setContentsOrder(pContentsOrder);
			contentsDto.setContentsName(titleName);
			contentsDto.setContentsType(pContentsType);
			contentsDto.setShowTime(pShowTime);
			log.debug("++++pShowTime+++++"+pShowTime);
			retVal = contentsDao.addCurriContents(contentsDto);

		}
	    msg = "목차를 등록하였습니다.";
		log.debug("+++++++++"+strselectedItem);

		model.put("selectedItem", strselectedItem);
        return forward(request, model, "/contents/SiCMRegist.jsp");
    }

	/**
	 * 개설과목 교재관리에서 컨텐츠 미리보기 프레임
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward preViewContents(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("---------- preViewContents Start --------------");
		//----- 메모리에서 정보 가져오기.
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
		String viewUrl = "";

		//-- 컨텐츠 타입 검색
		CourseDAO	courseDao = new CourseDAO();
		RowSet courseInfo = courseDao.getCourseInfo(systemCode,pCourseId);
		courseInfo.next();
		String ContentsType = StringUtil.nvl(courseInfo.getString("contents_type"));
		courseInfo.close();

		//-- 스콤 데이터 저장 유무 결정(기본은 저장으로 설정==> 미리보기이므로 저장 안함설정)
		String credit = "credit";
		String lessonMode = "nomal";
		credit = "no-credit";
		lessonMode = "browse";

		model.put("pCourseId", pCourseId);
		model.put("pContentsId", pContentsId);
		model.put("pContentsType", ContentsType);

		viewUrl = "/curri_contents/preViewContentsFrame.jsp";

		log.debug("================ ContentsType = "+ContentsType);
//		-- 컨텐츠 타입이 SCORM 형(S) 인경우
		if(ContentsType.equals("S")){
			log.debug("Scrom data");

			//-- 교재 정보를 가져온다.    cmi_core 셋팅용
			CurriContentsDAO contentsDao = new CurriContentsDAO();
			RowSet rs = contentsDao.getCurriContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pContentsId);
			rs.next();
			// prerequisite, pre_type, timelimitaction, datafromlms, masteryscore, maxtimeallowed
			String  prerequisite= StringUtil.nvl(rs.getString("prerequisite"));
			if(prerequisite.equals("null")) prerequisite="";
			String  pre_type= StringUtil.nvl(rs.getString("pre_type"));
			if(pre_type.equals("null")) pre_type="";
			String  timelimitaction= StringUtil.nvl(rs.getString("timelimitaction"));
			if(timelimitaction.equals("null")) timelimitaction="";
			String  datafromlms= StringUtil.nvl(rs.getString("datafromlms"));
			if(datafromlms.equals("null")) datafromlms="";
			String  masteryscore= StringUtil.nvl(rs.getString("masteryscore"));
			if(masteryscore.equals("null")) masteryscore="";
			String  maxtimeallowed= StringUtil.nvl(rs.getString("maxtimeallowed"));
			if(maxtimeallowed.equals("null")) maxtimeallowed="";
			rs.close();
			String homeDir = FileUtil.ABS_PATH;
			String scoFile =  homeDir + FileUtil.FILE_DIR+systemCode+"/temp_rtefiles/"+pCourseId+"/"+regId;
			log.debug("======================= scoFile = "+scoFile);
            String RTESCODataFile = "";
            File theRTESCODataFile = null;
            ScormStudyDAO	scormStudyDao	= 	new ScormStudyDAO();
            ScormStudyAction	scormStudyAction	= 	new ScormStudyAction();
            SCODataManager 	rteSCOData = new SCODataManager();

            scormStudyAction.set_session(request, "scoID", pContentsId);
            scormStudyAction.set_session(request, "credit", credit);
            scormStudyAction.set_session(request, "lessonMode", lessonMode);
            try {
                File theRTESCODataDir = new File( scoFile );
                if ( !theRTESCODataDir.isDirectory() ) {
//                	log.debug("======================= theRTESCODataDir.mkdirs() ");
                    theRTESCODataDir.mkdirs();
                }else {
                    String[] file_list      = theRTESCODataDir.list();
                    for(int i= 0 ; i < file_list.length ; i++) {                //  file delete
                        File f = new java.io.File(theRTESCODataDir + "/" + file_list[i]);
                        boolean tf      = f.delete();
                    }
                }

                RTESCODataFile = scoFile + "/" + pContentsId;
//                log.debug("======================= RTESCODataFile = "+RTESCODataFile);
                theRTESCODataFile = new File( RTESCODataFile );

                int chkCnt =  scormStudyDao.getCmiCoreCnt(systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pContentsId,regId);

                if (chkCnt > 0) {
                    rteSCOData = scormStudyAction.existSCOData(rteSCOData, systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pContentsId,regId);
//                    log.debug("++++++++++++++++ chkCnt > 0");
                } else {
                    rteSCOData = scormStudyAction.initSCOData(rteSCOData, systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pContentsId,regId,userName
                    		, masteryscore,maxtimeallowed, timelimitaction, datafromlms, credit, lessonMode);
//                    log.debug("++++++++++++++++ chkCnt !> 0");
                }
                FileOutputStream fo    = new FileOutputStream( RTESCODataFile );
                ObjectOutputStream outPut = new ObjectOutputStream( fo );
                outPut.writeObject( rteSCOData );
                outPut.close();

            } catch ( Exception e ) {
                  e.printStackTrace();
            }

		}

		log.debug("---------- preViewContents End   -------------- viewUrl = "+viewUrl);
		new SiteNavigation(LECTURE).add(request,"교안목차리스트").link(model);
		return forward(request, model, viewUrl);
	}
	/**
	 * 개설과목 교재관리에서 컨텐츠 미리보기 show
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward preViewContentsShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("---------- preViewContentsShow Start --------------");
		//----- 메모리에서 정보 가져오기.
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


		String contentsBase = "/data/";

		CurriContentsDAO contentsDao = new CurriContentsDAO();
		RowSet rs = contentsDao.getCurriContents(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pContentsId);
		rs.next();
		String ContentsUrl = StringUtil.nvl(rs.getString("server_path"));
		int chkPosition = ContentsUrl.lastIndexOf("/CMU/");
		String chkPath = "";
		if(chkPosition > 0)
			chkPath = ContentsUrl.substring(0,chkPosition);
		log.debug("===========chkPath============== chkPath = "+chkPath);
		if(!chkPath.equals("/REP"))
			  ContentsUrl = contentsBase+ContentsUrl;
		rs.close();
		log.debug("--- Contentsurl : "+ContentsUrl);

		model.put("ContentsUrl", ContentsUrl);
		log.debug("---------- lecContentsShow End   --------------");
		return forward(request, model, "/curri_contents/preViewContentsShow.jsp");
	}

	/**
	 * 강의실 목차 정보 리스트를 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */

	public ActionForward lecContentsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String	pMode		=	StringUtil.nvl(request.getParameter("pMode"));
		String 	systemCode 	= 	UserBroker.getSystemCode(request);
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		
		String	pCurriCode	=	"";
		int		curriYear	=	0;
		int		curriTerm	=	0;
		String	courseId	=	"";
		String	returnPage	=	"/curri_contents/lecContentsList.jsp";
		
		//--	강의실에서 접근
		if(pMode.equals("Lecture")) {
			CurriMemDTO curriMemDto = userMemDto.curriInfo;		    
		    pCurriCode 	= 	StringUtil.nvl(curriMemDto.curriCode);
			curriYear 	= 	StringUtil.nvl(curriMemDto.curriYear,0);
			curriTerm 	= 	StringUtil.nvl(curriMemDto.curriTerm,0);
			courseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));
			returnPage	=	"/curri_contents/lecContentsList.jsp";
		} //--	메인의 공개강좌에서 접근
		else if(pMode.equals("Home")) {
			pCurriCode 	= 	StringUtil.nvl(request.getParameter("pCurriCode"));
			curriYear	=	StringUtil.nvl(request.getParameter("curriYear"), 0);
			curriTerm	=	StringUtil.nvl(request.getParameter("curriTerm"), 0);
			courseId	=	StringUtil.nvl(request.getParameter("pCourseId"));
			String	pGubun	=	StringUtil.nvl(request.getParameter("pGubun"));
			returnPage	=	"/publish_curri_sub/publish_lecContentsList.jsp";
			
			//-- 상단 과정정보
			CurriTopDAO	curriTopDao	=	new CurriTopDAO();
			CurriTopDTO	curriTopDto	=	new CurriTopDTO();
			curriTopDto	=	curriTopDao.getCurriTopInfo(systemCode, pCurriCode);

			model.put("CurriTopDTO", curriTopDto);
			model.put("pCurriCode", pCurriCode);
			model.put("curriYear", curriYear);
			model.put("curriTerm", curriTerm);
			model.put("pGubun", pGubun);
		}
		
		//---- 과목의 정보를 가져옵니다.
		CurriSubCourseDAO curriSubCoruseDao = new CurriSubCourseDAO();
		ArrayList curriCourseList = new ArrayList();
		curriCourseList = curriSubCoruseDao.getCurriCourseList(systemCode,pCurriCode,curriYear,curriTerm,"");
		CurriCourseListDTO list = null;

		if(courseId.equals("")) { //---- 과목 아이디가 안넘어왔을경우 처음 과목을 셋팅
			if(curriCourseList.size() > 0){
				list = (CurriCourseListDTO)curriCourseList.get(0);
				courseId = list.getCourseId();
			}
		}
		
		ConfigSubDAO cofigSubDao = new ConfigSubDAO();
		String quizConfigYn = cofigSubDao.getConfigSubValue(systemCode,"400","001");
		
		if(pMode.equals("Home")) {
			//-- 개설된 토론방의 아이디를 받아온다.
			CourseForumInfoDAO	forumDao	=	new CourseForumInfoDAO();
			int	forumId			=	forumDao.getForumId(systemCode, pCurriCode, curriYear, curriTerm, courseId);
			
			model.put("forumId", forumId);
		}

		model.put("courseId", courseId);
		model.put("quizConfigYn", quizConfigYn);

		new SiteNavigation(pMode).add(request,"강의목차").link(model);
		return forward(request, model, returnPage);
	}

	/**
	 * [AJAX] 강의실 목차 정보 리스트를 가져온다.
	 * @param courseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList contentsListAuto(String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		courseId = StringUtil.nvl(courseId);

		// 스콤 컨텐츠 미리보기를 위해 가상의 curriMemDto 생성
		CurriMemDTO curriInfo =  new CurriMemDTO();
		curriInfo.curriCode = "preView";
		curriInfo.curriName = "preView";
		curriInfo.curriYear = "2007";
		curriInfo.curriTerm = "0";
		curriInfo.curriType = "R";
		UserBroker.setCurriInfo(request,curriInfo);
		// 스콤 컨텐츠 미리보기를 위해 가상의 curriMemDto 생성 End

		// 리턴 데이타 세팅
		ArrayList arrayList = new ArrayList();
		ContentsDAO contentsDao = new ContentsDAO();
		ContentsDTO contentsDto = null;

		RowSet rs = contentsDao.getContentsList(systemCode, courseId);
		while(rs.next()){
			contentsDto = new ContentsDTO();
			contentsDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			contentsDto.setContentsId(StringUtil.nvl(rs.getString("contents_id")));
			contentsDto.setContentsOrder(StringUtil.nvl(rs.getString("contents_order")));
			contentsDto.setContentsName(StringUtil.nvl(rs.getString("contents_name")));
			contentsDto.setFilePath(StringUtil.nvl(rs.getString("file_path")));
			contentsDto.setServerPath(StringUtil.nvl(rs.getString("server_path")));
			contentsDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
			contentsDto.setQuizCount(StringUtil.nvl(rs.getString("quiz_count"),0));
			contentsDto.setQuizPoint(StringUtil.nvl(rs.getString("quiz_point"),0));
			contentsDto.setShowTime(StringUtil.nvl(rs.getString("show_time"),0));
			contentsDto.setContentsWidth(StringUtil.nvl(rs.getString("contents_width"),0));
			contentsDto.setContentsHeight(StringUtil.nvl(rs.getString("contents_height"),0));
			contentsDto.setSizeApp(StringUtil.nvl(rs.getString("size_app"),"N"));
			contentsDto.setProgressRate(StringUtil.nvl(rs.getString("progress_rate"),0));
			contentsDto.setLectureGubun(StringUtil.nvl(rs.getString("lecture_gubun")));

			arrayList.add(contentsDto);
		}
		rs.close();

		return arrayList;
	}

	public ArrayList lecContentsListAuto(String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception{

		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String userId = userMemDto.userId;

		//----- 메모리에서 정보 가져오기.
	    String curriCode = StringUtil.nvl(curriMemDto.curriCode);
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		courseId = StringUtil.nvl(courseId);

		// 학습자 목차정보 세팅
		ArrayList arrayList = new ArrayList();
		CurriContentsDAO curriContentsDao = new CurriContentsDAO();
		LecContentsDTO lecContentsDto = null;
		RowSet rs = curriContentsDao.getBookmarkContentsList(systemCode, curriCode, curriYear, curriTerm, courseId, userId);

		while(rs.next()){
			lecContentsDto = new LecContentsDTO();
			lecContentsDto.setCurriCode(curriCode);
			lecContentsDto.setCurriYear(curriYear);
			lecContentsDto.setCurriTerm(curriTerm);
			lecContentsDto.setUserId(userId);
			lecContentsDto.setCourseId(courseId);
			lecContentsDto.setContentsId(rs.getString("contents_id"));
			lecContentsDto.setContentsType(rs.getString("contents_type"));
			lecContentsDto.setContentsOrder(rs.getString("contents_order"));
			lecContentsDto.setContentsName(rs.getString("contents_name"));
			lecContentsDto.setServerPath(StringUtil.nvl(rs.getString("server_path")));
			lecContentsDto.setOpenChk(rs.getString("open_chk"));
			lecContentsDto.setOpenDate(rs.getString("open_date"));
			lecContentsDto.setShowTime(rs.getInt("show_time"));
			lecContentsDto.setJoinCount(rs.getInt("join_count"));
			lecContentsDto.setTotalTime(rs.getInt("total_time"));
			lecContentsDto.setQuizYn(rs.getString("quiz_yn"));
			lecContentsDto.setQuizPass(StringUtil.nvl(rs.getString("quiz_pass")));
			lecContentsDto.setSizeApp(StringUtil.nvl(rs.getString("size_app"),"N"));
			lecContentsDto.setContentsWidth(StringUtil.nvl(rs.getString("contents_width"),0));
			lecContentsDto.setContentsHeight(StringUtil.nvl(rs.getString("contents_height"),0));
			lecContentsDto.setLectureGubun(StringUtil.nvl(rs.getString("lecture_gubun")));
			lecContentsDto.setStartDate(StringUtil.nvl(rs.getString("start_date")));
			lecContentsDto.setEndDate(StringUtil.nvl(rs.getString("end_date")));
			lecContentsDto.setAttendance(StringUtil.nvl(rs.getString("attendance")));
			lecContentsDto.setCurDate(StringUtil.nvl(rs.getString("cur_date")));
			lecContentsDto.setFilePath(StringUtil.nvl(rs.getString("file_path"))); 
			lecContentsDto.setAsfFilePath(StringUtil.nvl(rs.getString("asffile_path"))); 
			
			arrayList.add(lecContentsDto);
		}
		rs.close();

		return arrayList;
	}

	/**
	 * 북마킹 시간 정보 남기기(Ajax)
	 * 2007.05.22 sangsang
	 * @param courseId
	 * @param contentsId
	 * @param startTime
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String lecBookmarkEnd(String courseId, String contentsId, String startTime, HttpServletRequest request, HttpServletResponse response) throws Exception{

		//----- 메모리에서 정보 가져오기.
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String regId = userMemDto.userId;
	    String curriCode = StringUtil.nvl(curriMemDto.curriCode);
	    int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		courseId = StringUtil.nvl(courseId);
		contentsId = StringUtil.nvl(contentsId);
		startTime = StringUtil.nvl(startTime);

		StudentDAO studentDao = new StudentDAO();
		int studentYn = studentDao.isStudent(systemCode, regId, curriCode, curriYear, curriTerm);
		int retVal = 0;
		if(studentYn > 0) { //---- 수강생인 경우만 북마크 정보 남김
			//---- 현재 시간을 구해 온다.
			BookmarkDAO bookmarkDao = new BookmarkDAO();
			retVal = bookmarkDao.setLearningTime(systemCode, regId, curriCode, curriYear, curriTerm, courseId, contentsId, startTime);
		}

		return String.valueOf(retVal);
	}
	
	/**
	 * 메인화면의 정규강좌 소개정보
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward getContentsIntroduction(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String	systemCode	=	UserBroker.getSystemCode(request);
		String	pCurriCode	=	StringUtil.nvl(request.getParameter("pCurriCode"));
		int		pCurriYear	=	StringUtil.nvl(request.getParameter("pCurriYear"), 0);
		int		pCurriTerm	=	StringUtil.nvl(request.getParameter("pCurriTerm"), 0);
		String	pCourseId	=	StringUtil.nvl(request.getParameter("pCourseId"));
		String	pContentsId	=	StringUtil.nvl(request.getParameter("pContentsId"));
		
		CurriContentsDTO	contentsDto	=	new CurriContentsDTO();
		CurriContentsDAO	contentsDao	=	new CurriContentsDAO();
		contentsDto		=	contentsDao.getCurriContentsIntroduce(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pContentsId);
		
		model.put("ContentsDTO", contentsDto);
		
		new SiteNavigation(HOME).add(request,"정규강좌 정보").link(model);
		return forward(request, model, "/main/popupContentsIntroduction.jsp");
	}

}

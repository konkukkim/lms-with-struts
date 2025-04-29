/**
 * 
 */
package com.edutrack.currisub.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.courseforum.dao.CourseForumCommentDAO;
import com.edutrack.courseforum.dao.CourseForumContentsDAO;
import com.edutrack.courseforum.dao.CourseForumInfoDAO;
import com.edutrack.courseforum.dto.CourseForumContentsDTO;
import com.edutrack.courseforum.dto.CourseForumInfoDTO;
import com.edutrack.currisub.dao.CurriContentsDAO;
import com.edutrack.currisub.dao.CurriSubCourseDAO;
import com.edutrack.currisub.dao.PublishCurriSubDAO;
import com.edutrack.currisub.dto.LecContentsDTO;
import com.edutrack.curritop.dto.CurriTopDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;
import com.oroinc.net.ftp.FTPClient;
import com.oroinc.net.ftp.FTPReply;

/**
 * @author jangms
 *
 */
public class PublishCurriSubAction extends StrutsDispatchAction{
	/**
	 *
	 */
	public PublishCurriSubAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * 공개과정 리스트
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward publishCurriSubPageList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String pMode = StringUtil.nvl(request.getParameter("pMode"), HOME);
		
		//-- 공개광좌 카테고리에 해당하는 pare_code1, pare_code2
		String	pPareCode1	=	StringUtil.nvl(request.getParameter("pPareCode1"), "Test0001");
		String	pPareCode2	=	StringUtil.nvl(request.getParameter("pPareCode2"), "OPEN0001");
		String	MENUNO		=	StringUtil.nvl(request.getParameter("MENUNO"));
		String	pGubun		=	StringUtil.nvl(request.getParameter("pGubun"));
		String	pMenuStr	=	"";
		if(pGubun.equals("1")) {
			pMenuStr		=	"기획교양강좌";
		} else if(pGubun.equals("2")) {
			pMenuStr		=	"정세와 쟁점";
		}

		int 	dispLine	=	10;
		int		dispPage	=	10;
		
		model.put("pPareCode1", pPareCode1);
		model.put("pPareCode2", pPareCode2);
		model.put("pMode", "HOME");
		model.put("MENUNO", MENUNO);
		model.put("pGubun", pGubun);
		model.put("dispLine", ""+dispLine);
		model.put("dispPage", ""+dispPage);
		
		
		new SiteNavigation(pMode).add(request, pMenuStr).link(model);
		return forward(request, model, "/publish_curri_sub/publishCurriSubPageList.jsp");
	}
	
	/**
	 * 공개과정 리스트 AJAX
	 *
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO publishCurriSubPageAutoList(int curPage, int dispLine, int dispPage, String pPareCode1, String pPareCode2, String pSearchKey, String pKeyWord, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String	systemCode	=	UserBroker.getSystemCode(request);
		pSearchKey	=	StringUtil.nvl(pSearchKey);
		pKeyWord	=	AjaxUtil.ajaxDecoding(StringUtil.nvl(pKeyWord));
		curPage = (curPage == 0) ? 1 : curPage;
		String 	pOrder 		= 	" cs.reg_date desc";		//	ORDER BY
		
		//-- DAO로 넘길 파라미터값 세팅
		Map		paramMap	=	new HashMap();
		paramMap.put("pSearchKey", pSearchKey);
		paramMap.put("pKeyWord", pKeyWord);
		paramMap.put("order", pOrder);
		paramMap.put("pPareCode1", pPareCode1);
		paramMap.put("pPareCode2", pPareCode2);
		paramMap.put("pProperty2", "P");		//과정구분 : 공개과정
		
		PublishCurriSubDAO	publishDao	=	new PublishCurriSubDAO();
		AjaxListDTO			ajaxListDto	=	new AjaxListDTO();
		ArrayList			dataList	=	new ArrayList();
		CurriTopDTO			curriTop	=	null;
		ListDTO 			listObj 	= 	null;
		
		listObj	=	publishDao.getPublishCurriSubPageList(curPage, dispLine, dispPage, systemCode, paramMap);
		if (listObj.getItemCount() > 0) {
			RowSet	rs		=	listObj.getItemList();
			
			while(rs.next()){
				curriTop	=	new CurriTopDTO();
				curriTop.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				curriTop.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				curriTop.setCurriYear(rs.getInt("curri_year"));
				curriTop.setCurriTerm(rs.getInt("curri_term"));
				curriTop.setCurriGoal(StringUtil.nvl(rs.getString("curri_goal")));
				curriTop.setCurriInfo(StringUtil.nvl(rs.getString("curri_info")));
				curriTop.setCurriYear(rs.getInt("curri_year"));
				curriTop.setCurriTerm(rs.getInt("curri_term"));
				curriTop.setCurriName(StringUtil.nvl(rs.getString("curri_name")));
				curriTop.setProfName(StringUtil.nvl(rs.getString("prof_name")));
				curriTop.setDispLine(dispLine);
				
				dataList.add(curriTop);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());				// 전체 글 수
			ajaxListDto.setDataList(dataList);										// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}
		return ajaxListDto;
	}
	
	/**
	 * 공개강좌의 강의리스트
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param courseId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ArrayList lecContentsListAuto(String curriCode, int curriYear, int curriTerm, String courseId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		curriCode = StringUtil.nvl(curriCode);
	    curriYear = StringUtil.nvl(curriYear, 0);
	    curriTerm = StringUtil.nvl(curriTerm, 0);
	    courseId = StringUtil.nvl(courseId);

		// 학습자 목차정보 세팅
		ArrayList arrayList = new ArrayList();
		CurriContentsDAO curriContentsDao = new CurriContentsDAO();
		LecContentsDTO lecContentsDto = null;
		//RowSet rs = curriContentsDao.getBookmarkContentsList(systemCode, curriCode, curriYear, curriTerm, courseId, "");
		RowSet rs = curriContentsDao.getCurriContentsList(systemCode, curriCode, curriYear, curriTerm, courseId, "");
		
		String	filePath	=	"";
		String	serverPath	=	"";
		int		indexNo		=	0;
		String	rfileName01	=	"";
		String	sfileName01	=	"";
		String	rfileName02	=	"";
		String	sfileName02	=	"";
		String	attachPath01	=	"";
		String	attachPath02	=	"";
		
		while(rs.next()){
			lecContentsDto = new LecContentsDTO();
			lecContentsDto.setCurriCode(curriCode);
			lecContentsDto.setCurriYear(curriYear);
			lecContentsDto.setCurriTerm(curriTerm);
			lecContentsDto.setCourseId(courseId);
			lecContentsDto.setContentsId(rs.getString("contents_id"));
			lecContentsDto.setContentsType(rs.getString("contents_type"));
			lecContentsDto.setContentsOrder(rs.getString("contents_order"));
			lecContentsDto.setContentsName(rs.getString("contents_name"));
			
			lecContentsDto.setFilePath(StringUtil.nvl(rs.getString("file_path")));
			lecContentsDto.setServerPath(StringUtil.nvl(rs.getString("server_path")));
			//--------------------------------------------------------------------------------
						
			filePath	=	StringUtil.nvl(rs.getString("file_path"));
			if(filePath.length() > 0) {
				indexNo		=	filePath.lastIndexOf("/");
				rfileName01	=	filePath.substring(indexNo+1, filePath.length());
				sfileName01	=	filePath.substring(indexNo+1, filePath.length());
				attachPath01 = 	filePath.substring(0, indexNo);
			}
			lecContentsDto.setRFileName1(StringUtil.nvl(rfileName01));
			lecContentsDto.setSFileName1(StringUtil.nvl(sfileName01));
			lecContentsDto.setAttachPath1(StringUtil.nvl(attachPath01));
			
			//--------------------------------------------------------------------------------
			
//			serverPath	=	StringUtil.nvl(rs.getString("server_path"));
//			if(serverPath.length() > 0) {
//				indexNo		=	serverPath.lastIndexOf("/");
//				rfileName02	=	serverPath.substring(indexNo+1, serverPath.length());
//				sfileName02	=	serverPath.substring(indexNo+1, serverPath.length());
//				attachPath02 = 	serverPath.substring(0, indexNo);
//			}
			lecContentsDto.setRFileName2(StringUtil.nvl(rs.getString("asffile_path")));
			lecContentsDto.setSFileName2(StringUtil.nvl(rs.getString("asffile_path")));
			lecContentsDto.setAttachPath2(StringUtil.nvl(rs.getString("asffile_path")));
			
			//--------------------------------------------------------------------------------
			
			lecContentsDto.setShowTime(rs.getInt("show_time"));
			lecContentsDto.setSizeApp(StringUtil.nvl(rs.getString("size_app"),"N"));
			lecContentsDto.setContentsWidth(StringUtil.nvl(rs.getString("contents_width"),0));
			lecContentsDto.setContentsHeight(StringUtil.nvl(rs.getString("contents_height"),0));
			lecContentsDto.setLectureGubun(StringUtil.nvl(rs.getString("lecture_gubun")));
			lecContentsDto.setStartDate(StringUtil.nvl(rs.getString("start_date")));
			lecContentsDto.setEndDate(StringUtil.nvl(rs.getString("end_date")));
			lecContentsDto.setProfName(StringUtil.nvl(rs.getString("prof_name")));
			lecContentsDto.setRegDate(DateTimeUtil.getDateType(1, StringUtil.nvl(rs.getString("reg_date"))));

			arrayList.add(lecContentsDto);
		}
		rs.close();

		return arrayList;
	}
	
	/**
	 * 공개과정 강의보기
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward publishLecContentsShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	systemCode 	= 	UserBroker.getSystemCode(request);
		String	pServerPath	=	StringUtil.nvl(request.getParameter("pServerPath"));

		model.put("pServerPath", pServerPath);

		new SiteNavigation("HOME").add(request,"강의보기").link(model);
		return forward(request, model, "/publish_curri_sub/publish_lecContentsShow.jsp");
	}
	
	/**
	 * 토론방 추가
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward addForumInfoPopup(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	systemCode		= 	UserBroker.getSystemCode(request);
		String	pCurriCode		=	StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear		=	StringUtil.nvl(request.getParameter("pCurriYear"), 0);
		int 	pCurriTerm		=	StringUtil.nvl(request.getParameter("pCurriTerm"), 0);
		String	pCourseId		=	"";
		int		forumInfoCnt	=	0;
		int		pForumId		=	0;
		
		CurriSubCourseDAO	curriSubCoruseDao	=	new CurriSubCourseDAO();
		ListDTO	courseList	=	curriSubCoruseDao.getCurriSubCourseList(1, systemCode, pCurriCode, pCurriYear, pCurriTerm, "", "", 1);
		RowSet	rs			=	null;
		if(courseList.getItemCount() > 0) {
			rs	=	courseList.getItemList();
			while(rs.next()) {
				pCourseId		=	StringUtil.nvl(rs.getString("course_id"));
			}
		}
		
		//-- 토론방이 개설되어 있는지를 카운트 한다.
		CourseForumInfoDAO	forumDao	=	new CourseForumInfoDAO();
		CourseForumInfoDTO	forumDto	=	new CourseForumInfoDTO();
		forumInfoCnt	=	forumDao.getCourseForumInfoCount(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, "");
		if(forumInfoCnt > 0) {
			pForumId	=	forumDao.getForumId(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId);
			forumDto	=	forumDao.getCourseForumInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId);
			model.put("pRegMode", "Edit");
		} else {
			model.put("pRegMode", "Add");
		}
		
		model.put("pCurriCode", pCurriCode);
		model.put("pCurriYear", pCurriYear);
		model.put("pCurriTerm", pCurriTerm);
		model.put("pCourseId", pCourseId);
		model.put("pForumId", pForumId);
		model.put("CourseForumInfoDTO", forumDto);
		
		new SiteNavigation("HOME").add(request,"토론방개설").link(model);
		return forward(request, model, "/publish_curri_sub/addForumInfoPopup.jsp");
	}
	
	/**
	 * 토론방 정보를 등록/수정 합니다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */	
	public ActionForward ForumInfoRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	systemCode		= 	UserBroker.getSystemCode(request);
		//-- 공통 파라미터
		String	pCurriCode		=	StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear		=	StringUtil.nvl(request.getParameter("pCurriYear"), 0);
		int 	pCurriTerm		=	StringUtil.nvl(request.getParameter("pCurriTerm"), 0);
		String	pCourseId		=	StringUtil.nvl(request.getParameter("pCourseId"));
		String	pRegMode		=	StringUtil.nvl(request.getParameter("pRegMode"));
		int		pForumId		=	StringUtil.nvl(request.getParameter("pForumId"), 0);
		int		maxForumCnt		=	0;
		int		retVal			=	0;
		String	msg				=	"";
		
		//-- 토론방 정보
		String	pSubject		=	StringUtil.nvl(request.getParameter("pSubject"));
		String	pContents		=	StringUtil.nvl(request.getParameter("pContents"));
		String	pOpenYn			=	StringUtil.nvl(request.getParameter("pOpenYn"));
		
		CourseForumInfoDAO	forumDao	=	new CourseForumInfoDAO();
		CourseForumInfoDTO	forumDto	=	new CourseForumInfoDTO();
		forumDto.setSystemCode(systemCode);
		forumDto.setCurriCode(pCurriCode);
		forumDto.setCurriYear(pCurriYear);
		forumDto.setCurriTerm(pCurriTerm);
		forumDto.setCourseId(pCourseId);
		forumDto.setSubject(pSubject);
		forumDto.setContents(pContents);
		forumDto.setOpenYn(pOpenYn);
		forumDto.setRegistYn("Y");
		forumDto.setEditorYn("Y");
		forumDto.setCommentUseYn("Y");
		forumDto.setEmoticonUseYn("Y");
		
		if(pRegMode.equals("Add")) {
			//-- 토론방의 MAX_ID를 받아온다.
			maxForumCnt		=	forumDao.getMaxForumId(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId);
			forumDto.setForumId(maxForumCnt);
			
			retVal	=	forumDao.addCourseForumInfo(forumDto);
			if(retVal > 0) {
				msg	=	"토론방이 개설되었습니다.";
			} else {
				msg	=	"토론방이 개설되지 않았습니다. \n관리자에게 문의해 주세요.";
			}
		} else {
			forumDto.setForumId(pForumId);
			
			retVal	=	forumDao.editCourseForumInfo(forumDto);
			if(retVal > 0) {
				msg	=	"토론방 정보가 수정되었습니다.";
			} else {
				msg	=	"토론방 정보를 수정하는데 실패했습니다. \n관리자에게 문의해 주세요.";
			}
		}
		
		return closePopupReload(systemCode, model, msg, "", HOME);
	}
	
	/**
	 * 공개과정 토론방 리스트
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward publishForumContentsPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	systemCode 	= 	UserBroker.getSystemCode(request);
	    String	pCurriCode 	= 	StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 	= 	StringUtil.nvl(request.getParameter("pCurriYear"), 0);
		int 	pCurriTerm 	= 	StringUtil.nvl(request.getParameter("pCurriTerm"), 0);
		String 	pCourseId 	= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int 	pForumId 	= 	StringUtil.nvl(request.getParameter("pForumId"),0);
		String	pGubun		=	StringUtil.nvl(request.getParameter("pGubun"));
		
		CourseForumInfoDAO courseForumInfoDao 	= 	new CourseForumInfoDAO();
		CourseForumInfoDTO courseForumInfo		=	new CourseForumInfoDTO();
		courseForumInfo = courseForumInfoDao.getCourseForumInfo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId);
		
		model.put("courseForumInfo", courseForumInfo);
		model.put("pCurriCode", pCurriCode);
		model.put("pCurriYear", pCurriYear);
		model.put("pCurriTerm", pCurriTerm);
		model.put("pCourseId", pCourseId);
		model.put("pGubun", pGubun);
		model.put("pForumId", String.valueOf(pForumId));

		new SiteNavigation(HOME).add(request,"토론방").link(model);
	    return forward(request, model, "/publish_curri_sub/publishForumContentsPagingList.jsp");
	}
	
	/**
	 * 토론방 글 리스트를 담는다.
	 * 
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO courseForumContentsAutoList(int curPage, String pCurriCode, int pCurriYear, int pCurriTerm, String pCourseId, int pForumId, String pSearchKey, String pKeyWord, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		String	systemCode 	= 	UserBroker.getSystemCode(request);
		
		CourseForumInfoDAO courseForumInfoDao 	= 	new CourseForumInfoDAO();
		CourseForumContentsDAO courseForumContentsDao = new CourseForumContentsDAO();

		String pOrder = "";
		String pWhere = "";
		if(pSearchKey.equals("")){
			pWhere = " and contents_type='S' ";
		}

		AjaxListDTO ajaxListDto = new AjaxListDTO();
		ArrayList dataList = new ArrayList();
		CourseForumContentsDTO courseForumContentsDto = null;
		ArrayList arrList = new ArrayList();
		if( curPage <= 1 ){
			String	AddWhere = " and contents_type = 'N' ";
			//공지 리스트만 뽑아서 넘겨줘야 함.
			arrList = courseForumContentsDao.getCourseForumContentsList(systemCode, pCurriCode, pCurriYear, pCurriTerm,  pCourseId, pForumId, AddWhere, pOrder);

			for(int i=0;i<arrList.size(); i++){
				courseForumContentsDto = (CourseForumContentsDTO)arrList.get(i);
				courseForumContentsDto.setRegName(CommonUtil.getUserName(systemCode, courseForumContentsDto.getRegId()));
				dataList.add(courseForumContentsDto);
			}
		}

		// 데이타를 담는다.
		ListDTO listObj = null;
		CourseForumCommentDAO courseForumCommentDao = new CourseForumCommentDAO();
		listObj = courseForumContentsDao.getCourseForumContentsPagingList(curPage, 10, 10, systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, "",pSearchKey,pKeyWord, pWhere, pOrder);

		if (listObj.getItemCount() > 0) {
			RowSet 	rs			=	listObj.getItemList();
			String	regDateEtc	=	"";

			while(rs.next()){
				courseForumContentsDto = new CourseForumContentsDTO();
				// pk
				courseForumContentsDto.setForumId(rs.getInt("forum_id"));
				courseForumContentsDto.setSeqNo(rs.getInt("seq_no"));
				// data
				courseForumContentsDto.setDepthNo(rs.getInt("depth_no"));
				courseForumContentsDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
				courseForumContentsDto.setContents(StringUtil.nvl(rs.getString("contents")));
				courseForumContentsDto.setSubject(StringUtil.nvl(rs.getString("subject")));
				courseForumContentsDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				courseForumContentsDto.setRegName(StringUtil.nvl(rs.getString("reg_name")));
				courseForumContentsDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				courseForumContentsDto.setHitNo(rs.getInt("hit_no"));
				courseForumContentsDto.setContentsType("S");	// 공지와 구분을 지어주기 위해..
				courseForumContentsDto.setDispLine(10);
				
				regDateEtc	=	StringUtil.ReplaceAll(StringUtil.nvl(rs.getString("reg_date")), ".", "");
				courseForumContentsDto.setRegDateEtc(regDateEtc);
				courseForumContentsDto.setCommCnt(rs.getInt("comm_cnt"));
				

				dataList.add(courseForumContentsDto);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}

		return ajaxListDto;
	}
	
	
	/**
	 * 토론방의 입력자 비밀번호를 확인한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String publishForumContentsPassChkForm (String pCurriCode, int pCurriYear, int pCurriTerm, String pCourseId, int pForumId, int pSeqNo, String pRegPasswd, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String	systemCode	=	UserBroker.getSystemCode(request);
		int		retVal		=	0;
		
		CourseForumContentsDAO courseForumContentsDao = new CourseForumContentsDAO();
		retVal	=	courseForumContentsDao.publishForumContentsPassChk(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pSeqNo, pRegPasswd);
		
		return String.valueOf(retVal);
	}
	
	/**
	 * 토론방 댓글의 입력자 비밀번호를 입력한다.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String publishForumCommentPassChkForm (String pCurriCode, int pCurriYear, int pCurriTerm, String pCourseId, int pForumId, int pSeqNo, int commNo, String pRegPasswd, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String	systemCode	=	UserBroker.getSystemCode(request);
		int		retVal		=	0;
		
		CourseForumCommentDAO courseForumCommentDao = new CourseForumCommentDAO();//getRegPassConfirm
		retVal	=	courseForumCommentDao.publishForumCommentPassChk(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId, pSeqNo, commNo, pRegPasswd);
		
		return String.valueOf(retVal);
	}
	
	/**
	 * 토론글 등록
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward publishForumContentsRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	systemCode 		= 	UserBroker.getSystemCode(request);
		String	pCurriCode 		= 	StringUtil.nvl(request.getParameter("pCurriCode"));
		int 	pCurriYear 		= 	StringUtil.nvl(request.getParameter("pCurriYear"), 0);
		int 	pCurriTerm 		= 	StringUtil.nvl(request.getParameter("pCurriTerm"), 0);
		String	pGubun			=	StringUtil.nvl(request.getParameter("pGubun"));
		String 	pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		int 	pForumId 		= 	StringUtil.nvl(request.getParameter("pForumId"),0);
		int 	pSeqNo 			= 	StringUtil.nvl(request.getParameter("pSeqNo"),1);
		String	regId			=	StringUtil.nvl(UserBroker.getUserId(request));
		
		String 	regMode 		= 	StringUtil.nvl(request.getParameter("pRegMode"));
		int 	retVal 			= 	0;
		String 	originName 		= 	"";
		String 	rFileName 		= 	"";
		String 	sFileName 		= 	"";
		String 	filePath 		= 	"";
		String 	fileSize 		= 	"";

		String	pOldRfileName 	= 	"";
		String	pOldSfileName 	= 	"";
		String	pOldFilePath	= 	"";
		String	pOldFileSize 	= 	"";
		
		CourseForumContentsDAO 	courseForumContentsDao 	= 	new CourseForumContentsDAO();
		CourseForumContentsDTO 	courseForumContentsDto 	= 	new CourseForumContentsDTO();
		if(regMode.equals("Add") || regMode.equals("Reply") )// 입력모드
		{
			pSeqNo = courseForumContentsDao.getMaxSeqNo(systemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId, pForumId);
		}

		MultipartRequest 		multipartRequest 		= 	null;
		//파일 업로드
		String		RegMonth		= 	CommonUtil.getCurrentDate().substring(0,6);
		String		FilePath 		= 	FileUtil.FILE_DIR+systemCode+"/course_forum/"+pCourseId +"_"+ pForumId +"/"+RegMonth+"/";
		UploadFiles	uploadEntity	=	FileUtil.upload(request, FilePath, pSeqNo+"_"+regId);
		// 파라미터를 빼온다.
		multipartRequest			=	uploadEntity.getMultipart();
		String		status 			=	uploadEntity.getStatus();

		if(regMode.equals("Edit")){//-- 수정모드시는 현재 월이 아닌 등록된 글의 년월을 가져온다.
			RegMonth 		= 	StringUtil.nvl(request.getParameter("pRegDate")).substring(0,6);
			pOldRfileName 	= 	StringUtil.nvl(multipartRequest.getParameter("pOldRfileName"));
			pOldSfileName 	= 	StringUtil.nvl(multipartRequest.getParameter("pOldSfileName"));
			pOldFilePath 	= 	StringUtil.nvl(multipartRequest.getParameter("pOldFilePath"));
			pOldFileSize 	= 	StringUtil.nvl(multipartRequest.getParameter("pOldFileSize"));
		}
		
		String	pSubject		=	StringUtil.nvl(multipartRequest.getParameter("pSubject"));
		String	pKeyword 		= 	"";
		String	pEditorType 	= 	"";
		if( new String("true").equals(multipartRequest.getParameter("VBN_FORM_WEAS")) == true ){//-- 웹에디터 사용시
			 pKeyword = StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
			 pEditorType = "W";
		}else{//-- 웹에디터 사용 안할경
			pKeyword = StringUtil.nvl(multipartRequest.getParameter("pContents"));
			pEditorType = "H";
		}
		String pContents = StringUtil.nvl(multipartRequest.getParameter("pContents"));

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
				originName = StringUtil.nvl(file.getRootName());
				if(!originName.equals("")) {
					rFileName = StringUtil.nvl(file.getRootName());
					sFileName = file.getUploadName();
					fileSize = String.valueOf(file.getSize());
					filePath = uploadEntity.getUploadPath();
				}
			}
			if(!originName.equals("")) {			//파일첨부를 했을경우
				courseForumContentsDto.setRfileName(rFileName);
				courseForumContentsDto.setSfileName(sFileName);
				courseForumContentsDto.setFileSize(fileSize);
				courseForumContentsDto.setFilePath(filePath); // FilePath가 맞는 듯 함.

				if(!pOldSfileName.equals("")) {		//이전 첨부파일을 삭제할 경우
					FileUtil.delFile(FilePath, pOldSfileName);
				}
			
			}else{									// 첨부 안한 경우
				courseForumContentsDto.setRfileName(pOldRfileName);
				courseForumContentsDto.setSfileName(pOldSfileName);
				courseForumContentsDto.setFileSize(pOldFileSize);
				courseForumContentsDto.setFilePath(pOldFilePath);
			}
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

		int 	pBbsNo 			= 	StringUtil.nvl(multipartRequest.getParameter("pBbsNo"),pSeqNo);
		int 	pDepthNo 		= 	StringUtil.nvl(multipartRequest.getParameter("pDepthNo"),0);
		int 	pLevelNo 		= 	StringUtil.nvl(multipartRequest.getParameter("pLevelNo"),0);
		int 	pParentNo		= 	StringUtil.nvl(multipartRequest.getParameter("pParentNo"),0);
		String	pContentsType	=	StringUtil.nvl(multipartRequest.getParameter("pContentsType"));
		String	pRegName		=	StringUtil.nvl(multipartRequest.getParameter("pRegName"));
		String	pRegPasswd		=	StringUtil.nvl(multipartRequest.getParameter("pRegPasswd"));

		if(regMode.equals("Add")){
			pBbsNo = pSeqNo;
			pDepthNo = 0;
			pLevelNo = 0;
			pParentNo = 0;
		}
		if(regMode.equals("Reply")){
			pDepthNo = pDepthNo+1;
			pLevelNo = pLevelNo+1;
		}

		courseForumContentsDto.setSystemCode(systemCode);
		courseForumContentsDto.setCurriCode(pCurriCode);
		courseForumContentsDto.setCurriYear(pCurriYear);
		courseForumContentsDto.setCurriTerm(pCurriTerm);
		courseForumContentsDto.setCourseId(pCourseId);
		courseForumContentsDto.setForumId(pForumId);
		courseForumContentsDto.setSeqNo(pSeqNo);
		courseForumContentsDto.setBbsNo(pBbsNo);
		courseForumContentsDto.setDepthNo(pDepthNo);
		courseForumContentsDto.setLevelNo(pLevelNo);
		courseForumContentsDto.setParentNo(pParentNo);
		courseForumContentsDto.setEditorType(pEditorType);
		courseForumContentsDto.setContentsType(pContentsType);
		courseForumContentsDto.setSubject(pSubject);
		courseForumContentsDto.setKeyword(pKeyword);
		courseForumContentsDto.setContents(pContents);
		courseForumContentsDto.setRegId(regId);
		courseForumContentsDto.setModId(regId);
		courseForumContentsDto.setRegName(pRegName);
		courseForumContentsDto.setRegPasswd(pRegPasswd);
		
		String msg = "";
		
		String	param		=	"&pCurriCode="+pCurriCode+"&pCurriYear="+pCurriYear+"&pCurriTerm="+pCurriTerm+"&pCourseId="+pCourseId+"&pForumId="+pForumId+"&MENUNO=1&pMode=Home&pGubun="+pGubun;
		String 	returnUrl 	= 	"/PublishCurriSub.cmd?cmd=publishForumContentsPagingList"+param;

		
		if(regMode.equals("Add"))// 입력모드
		{
			retVal	=	courseForumContentsDao.addCourseForumContents(courseForumContentsDto);
			msg		=	"등록완료";
		}else if(regMode.equals("Edit")){
			retVal	=	courseForumContentsDao.editCourseForumContents(courseForumContentsDto);
			msg		=	"수정완료";
			
			if(retVal <= 0){
				returnUrl	=	"/CourseForumContents.cmd?cmd=courseForumContentsEdit"+param+"&pSeqNo="+pSeqNo;
				msg			=	"수정오류 다시 진행해 주세요";
			}
		}else if(regMode.equals("Reply")){
			boolean		bVal	=	courseForumContentsDao.replyUpdateCourseForumContents(courseForumContentsDto,"Ins");
			if(bVal) retVal = 1;
			else retVal = 0;
			
			if(retVal >0){
				retVal = courseForumContentsDao.addCourseForumContents(courseForumContentsDto);
				msg = "등록완료";
			}
			if(retVal <= 0){
				returnUrl = "/CourseForumContents.cmd?cmd=courseForumContentsWrite"+param+"&pSeqNo="+pSeqNo;
				msg = "등록오류 다시 진행해 주세요";
			}
		}

		new SiteNavigation(HOME).add(request,"토론").link(model);
        return alertAndExit(systemCode, model, msg, returnUrl, HOME);
	}
	
}

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
import com.edutrack.code.dao.CodeSoDAO;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.currisub.dao.CurriSubDAO;
import com.edutrack.curritop.dao.CurriCategoryDAO;
import com.edutrack.curritop.dao.CurriTopCourseDAO;
import com.edutrack.curritop.dao.CurriTopDAO;
import com.edutrack.curritop.dto.CourseDTO;
import com.edutrack.curritop.dto.CurriCategoryTotalDTO;
import com.edutrack.curritop.dto.CurriTopDTO;
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
 * @author Jamfam
 *
 * 과정 관리
 */

public class CurriTopAction extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	/**
	 * 과정분류 리스트를 셀렉트 박스로 리턴한다..
	 * @param cateCode
	 * @return String
	 * @throws Exception
	 */
	public String cateSelectList(String SystemCode, String pCateCode) throws Exception {
		String retVal = "";
		String selected = "";
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='pCateCode'>");
		CurriCategoryDAO curriCategoryDao = new CurriCategoryDAO();
		RowSet rs = curriCategoryDao.getCategoryListAll(SystemCode);
		while(rs.next()){
			selected = "";
			if(StringUtil.nvl(rs.getString("cate_code")).equals(pCateCode)) selected = "selected";
			sb.append("<option value='"+StringUtil.nvl(rs.getString("cate_code"))+"' "+selected+">"+StringUtil.nvl(rs.getString("cate_name"))+"</option>");
		}
		sb.append("</select>");
		retVal = sb.toString();
		rs.close();
		return retVal;
	}


	/**
	 * 과정속성 리스트를 셀렉트 박스로 리턴한다..
	 * @param cateCode
	 * @return String
	 * @throws Exception
	 */
	public String codeSelectList(String SystemCode, String pProperty1, String pProperty2) throws Exception {
		String retVal = "";
		String selected = "";
		StringBuffer sb = new StringBuffer();
		sb.append("<select name='pProperty2'>");
		CodeSoDAO codeSoDao = new CodeSoDAO();
		RowSet rs = codeSoDao.getCodeSoListAll(SystemCode, pProperty1);
		while(rs.next()){
			selected = "";
			if(StringUtil.nvl(rs.getString("code_so")).equals(pProperty2)) selected = "selected";
			sb.append("<option value='"+StringUtil.nvl(rs.getString("code_so"))+"' "+selected+">"+StringUtil.nvl(rs.getString("so_name"))+"</option>");
		}
		sb.append("</select>");
		retVal = sb.toString();
		rs.close();
		return retVal;
	}


	/**
	 * ==================================================
	 * 					과정 리스트
	 * ==================================================
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriTopList(ActionMapping actionMapping,	ActionForm actionForm,	HttpServletRequest request,	HttpServletResponse httpServletResponse,	Map model) throws Exception{
		String 	pProperty1	= 	StringUtil.nvl(request.getParameter("pProperty1"), "Cyber");
		String	pProperty2	= 	StringUtil.nvl(request.getParameter("pProperty2"));
		String 	pMode		= 	StringUtil.nvl(request.getParameter("pMode"));
		String	pModeStr	=	"";
		String 	pTitle		=	"";
		if(pMode.equals("MyPage")) {
			pModeStr	=	"MYPAGE";
			pTitle		=	"과정관리";
		} else if(pMode.equals("Home")) {
			pModeStr	=	"HOME";
			pTitle		=	"공개과정";
		}
		
		model.put("pProperty1", pProperty1);
		model.put("pProperty2", pProperty2);
		model.put("pMode", pModeStr);

		new SiteNavigation(pMode).add(request, pTitle).link(model);
		return forward(request, model, "/curri_top/curriTopList.jsp");
	}


	public AjaxListDTO curriTopAutoList(int curPage, String pProperty1, String pProperty2, String pSTarget, String pSWord, HttpServletRequest request) throws Exception {
		String	systemCode		= 	UserBroker.getSystemCode(request);

		//	sorting
		pSTarget = StringUtil.nvl(pSTarget);
		pSWord = AjaxUtil.ajaxDecoding(StringUtil.nvl(pSWord));

		// 페이징
		curPage = (curPage == 0) ? 1 : curPage;

		CurriTopDAO curriTopDao 	= 	new CurriTopDAO();
		ListDTO		listObj 		= 	null;
		listObj	=	curriTopDao.getCurriTopList(curPage, systemCode, pProperty1, pProperty2, pSTarget, pSWord);

		AjaxListDTO	ajaxListDto		=	new AjaxListDTO();
		ArrayList 	dataList 		= 	new ArrayList();
		CurriTopDTO	curriTopDto		=	null;

		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				curriTopDto		=	new CurriTopDTO();
				curriTopDto.setCurriCode(StringUtil.nvl(rs.getString("curri_code")));
				curriTopDto.setCurriProperty2(StringUtil.nvl(rs.getString("curri_property2")));
				curriTopDto.setCurriName(StringUtil.nvl(rs.getString("curri_name")));
				curriTopDto.setCateCodeName(StringUtil.nvl(rs.getString("cate_name")));
				curriTopDto.setPareCode2Name(StringUtil.nvl(rs.getString("property2_name")));
				curriTopDto.setCourseCnt(rs.getInt("course_cnt"));

				dataList.add(curriTopDto);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}
		return ajaxListDto;
	}


	/**
	 * ==================================================
	 * 					과정등록 폼
	 * ==================================================
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriTopWrite(ActionMapping actionMapping,
										ActionForm actionForm,
										HttpServletRequest request,
										HttpServletResponse response,
										Map model) throws Exception{

		String systemCode	= UserBroker.getSystemCode(request);
		String cateCode		= StringUtil.nvl(request.getParameter("pCateCode"),"");

		String property1 	= StringUtil.nvl(request.getParameter("pProperty1"), "Cyber");
		String property2 	= StringUtil.nvl(request.getParameter("pProperty2"), "");


		CurriCategoryDAO curriCategoryDao		= new CurriCategoryDAO();
		CurriCategoryTotalDTO curriPCategoryDto = new CurriCategoryTotalDTO();

		//-- select box start
		ArrayList pCate1 = null;
		pCate1 = curriCategoryDao.getCategory(systemCode, "2");
		model.put("pCate1", pCate1);

		ArrayList pCate2 = null;
		pCate2 = curriCategoryDao.getCategory(systemCode, "3");
		model.put("pCate2", pCate2);

		ArrayList pCate3 = null;
		pCate3 = curriCategoryDao.getCategory(systemCode, "4");
		model.put("pCate3", pCate3);
		//-- select box end

//		model.put("cateSelect",cateSelect1(systemCode, cateCode));
//		model.put("cateSelect2",cateSelect2(systemCode, cateCode));
		model.put("codeSelect",codeSelectList(systemCode,property1,property2));
		model.put("codeSelect",codeSelectList(systemCode,property1,"R"));
		model.put("pProperty1", property1);

		new SiteNavigation(MYPAGE).add(request,"과정관리").link(model);

		return forward(request, model, "/curri_top/curriTopWrite.jsp");
	}


	/**
	 * ==================================================
	 * 					과정수정 폼
	 * ==================================================
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriTopEdit(ActionMapping actionMapping,
									ActionForm actionForm,
									HttpServletRequest request,
									HttpServletResponse response,
									Map model) throws Exception{

		String systemCode	= UserBroker.getSystemCode(request);
		String curriCode	= request.getParameter("pCurriCode");

		CurriTopDAO curriTopDao = new CurriTopDAO();
		CurriTopDTO curriTopDto = new CurriTopDTO();
		curriTopDto = curriTopDao.getCurriTopInfo(systemCode, curriCode);

		String curriImgPath1 = "";
		String curriImgPath2 = "";
		if(!StringUtil.nvl(curriTopDto.getCurriImg1()).equals("")) curriImgPath1 = "&nbsp;"+StringUtil.ksc2asc("Old File:")+StringUtil.nvl(curriTopDto.getCurriImg1())+"<input type=hidden name=pOldFile[1] value='"+StringUtil.nvl(curriTopDto.getCurriImg1())+"'><br>";
		if(!StringUtil.nvl(curriTopDto.getCurriImg2()).equals("")) curriImgPath2 = "&nbsp;"+StringUtil.ksc2asc("Old File:")+StringUtil.nvl(curriTopDto.getCurriImg2())+"<input type=hidden name=pOldFile[2] value='"+StringUtil.nvl(curriTopDto.getCurriImg2())+"'><br>";

		CurriCategoryDAO curriCategoryDao		= new CurriCategoryDAO();

		//-- select box start
		ArrayList pCate1 = null;
		pCate1 = curriCategoryDao.getCategory(systemCode, "2");
		model.put("pCate1", pCate1);

		ArrayList pCate2 = null;
		pCate2 = curriCategoryDao.getCategory(systemCode, "3");
		model.put("pCate2", pCate2);

		ArrayList pCate3 = null;
		pCate3 = curriCategoryDao.getCategory(systemCode, "4");
		model.put("pCate3", pCate3);
		//-- select box end

		model.put("curriCode", 			StringUtil.nvl(curriTopDto.getCurriCode()));
		model.put("curriName", 			StringUtil.nvl(curriTopDto.getCurriName()));
		model.put("pPareCode1",			StringUtil.nvl(curriTopDto.getPareCode1()));
		model.put("pPareCode2",			StringUtil.nvl(curriTopDto.getPareCode2()));
		model.put("pCateCode",			StringUtil.nvl(curriTopDto.getCateCode()));
		model.put("pPareCode1Name",		StringUtil.nvl(curriTopDto.getPareCode1Name()));
		model.put("pPareCode2Name",		StringUtil.nvl(curriTopDto.getPareCode2Name()));
		model.put("pCateCodeName",		StringUtil.nvl(curriTopDto.getCateCodeName()));
//		model.put("cateSelect", 		cateSelectList(systemCode,StringUtil.nvl(curriTopDto.getCateCode())));
		model.put("codeSelect", 		codeSelectList(systemCode,StringUtil.nvl(curriTopDto.getCurriProperty1()),StringUtil.nvl(curriTopDto.getCurriProperty2())));

		model.put("pProperty1", 		StringUtil.nvl(curriTopDto.getCurriProperty1()));
		model.put("credit", 			Integer.toString(curriTopDto.getCredit()));
		model.put("learningTime",		StringUtil.nvl(curriTopDto.getLearningTime()));
		model.put("curriGoal", 			StringUtil.nvl(curriTopDto.getCurriGoal()));
		model.put("curriInfo", 			StringUtil.nvl(curriTopDto.getCurriInfo()));
		model.put("curriEnv", 			StringUtil.nvl(curriTopDto.getCurriEnv()));
		model.put("assessment", 		StringUtil.nvl(curriTopDto.getAssessment()));
		model.put("beforeInfo", 		StringUtil.nvl(curriTopDto.getBeforeInfo()));
		model.put("curriTarget", 		StringUtil.nvl(curriTopDto.getCurriTarget()));
		model.put("curriContents", 		StringUtil.nvl(curriTopDto.getCurriContents()));
		model.put("curriContentsText", 	StringUtil.nvl(curriTopDto.getCurriContentsText()));
		model.put("curriOldImg1",  		curriImgPath1);
		model.put("curriOldImg2",  		curriImgPath2);

		//-- 개설된 과정 수 가져오기(삭제 체크 용)
		CurriSubDAO curriSubDao = new CurriSubDAO();
		int curriSubCnt = curriSubDao.getTotCount(systemCode,"",curriCode,0);
		model.put("curriSubCnt",String.valueOf(curriSubCnt));
		//-- 연결된 과목 수 가져오기(삭제 체크 용)
		CurriTopCourseDAO curriTopCourseDao = new CurriTopCourseDAO();
		int curriCourseCnt = curriTopCourseDao.getTotCount(systemCode,curriCode);
		model.put("curriCourseCnt",String.valueOf(curriCourseCnt));

		new SiteNavigation(MYPAGE).add(request,"과정관리").link(model);
		return forward(request, model, "/curri_top/curriTopEdit.jsp");
	}


	/**
	 * ==================================================
	 * 					과정정보 등록
	 * ==================================================
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriTopRegist(ActionMapping actionMapping,
										ActionForm actionForm,
										HttpServletRequest request,
										HttpServletResponse response,
										Map model) throws Exception{

		String systemCode	= UserBroker.getSystemCode(request);
		String regId		= UserBroker.getUserId(request);

		CurriTopDAO curriTopDao = new CurriTopDAO();
		CurriTopDTO curriTopDto = new CurriTopDTO();

		MultipartRequest multipartRequest = null;
		int retVal				= 0;
		String objName 			= "";
		String uploadFileName 	= "";
		String uploadFilePath 	= "";

		String FilePath = FileUtil.IMG_DIR+systemCode+"/curri_top/";
		log.debug("FilePath ==>"+FilePath);

		String regMode = StringUtil.nvl(request.getParameter("pRegMode"),"Add");
		log.debug("regMode ==>"+regMode);

		// 파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath, "curriImg_");
		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();
		log.debug("pCredit ==>"+multipartRequest.getParameter("pCredit"));

		String pCurriCode 		= StringUtil.nvl(multipartRequest.getParameter("pCurriCode"));
		String pCurriName 		= StringUtil.nvl(multipartRequest.getParameter("pCurriName"));
		String pPareCode1		= StringUtil.nvl(multipartRequest.getParameter("pPareCode1"));
		String pPareCode2       = StringUtil.nvl(multipartRequest.getParameter("pPareCode2"));
		String pCateCode 		= StringUtil.nvl(multipartRequest.getParameter("pCateCode"));
		String pChkEditCode		= StringUtil.nvl(multipartRequest.getParameter("pChkEditCode"));
		String pLearningTime	= StringUtil.nvl(multipartRequest.getParameter("pLearningTime"));
		String pCurriGoal 		= StringUtil.nvl(multipartRequest.getParameter("pCurriGoal"));
		String pCurriInfo 		= StringUtil.nvl(multipartRequest.getParameter("pCurriInfo"));
		String pCurriEnv 		= StringUtil.nvl(multipartRequest.getParameter("pCurriEnv"));
		String pAssessment 		= StringUtil.nvl(multipartRequest.getParameter("pAssessment"));
		String pCurriContents 	= StringUtil.nvl(multipartRequest.getParameter("pCurriContents"));
		String pBeforeInfo 		= StringUtil.nvl(multipartRequest.getParameter("pBeforeInfo"));
		String pCurriTarget 	= StringUtil.nvl(multipartRequest.getParameter("pTarget"));

		String pCurriProperty1 = StringUtil.nvl(multipartRequest.getParameter("pProperty1"),"Cyber");
		String pCurriProperty2 = StringUtil.nvl(multipartRequest.getParameter("pProperty2"));
		int pCredit = Integer.parseInt(multipartRequest.getParameter("pCredit"));

		String msg = "";
		String returnUrl = "/CurriTop.cmd?cmd=curriTopList&pProperty1="+pCurriProperty1+"&pMode=MyPage";

		//-- 코드 중복 여부 확인용		bschoi	2004.11.11
		if(regMode.equals("Add")){
			retVal = curriTopDao.getTotCount(systemCode,pCurriCode);

			if (retVal > 0) {
				msg = "이미 사용하고 있는 코드입니다.<br>다른 코드를 사용해 주십시요";
				returnUrl = "/CurriTop.cmd?cmd=curriTopWrite&pProperty1=Cyber&MENUNO=2&pMode=MyPage";
				return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
			}
		}

		String status = uploadEntity.getStatus();

		String pOldFileName[] = new String[3];

		for (int i=1; i<=2; i++) {
			pOldFileName[i] = StringUtil.nvl(multipartRequest.getParameter("pOldFile["+i+"]"));
		}
		curriTopDto.setCurriImg1(pOldFileName[1]);
		curriTopDto.setCurriImg2(pOldFileName[2]);

		uploadFilePath = uploadEntity.getUploadPath();

		if (status.equals("E")) {
			log.debug("첨부 파일 올리려다 실패하였습니다.");
		} else if (status.equals("O")) {
			log.debug("첨부하신 파일이 용량을 초과했습니다.");
		} else if (status.equals("I")) {
			log.debug("첨부 파일의 정보가 잘못되었습니다.");
		} else if (status.equals("S")) {
			//-- 업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			log.debug("첨부 파일을 첨부하는데 성공하였습니다.");
			ArrayList files = uploadEntity.getFiles();
			UploadFile file = null;

			for (int i=0; i<files.size(); i++) {
				file = (UploadFile)files.get(i);
				objName = file.getObjName();
				uploadFileName = StringUtil.nvl(file.getUploadName());

				if (objName.indexOf("pTitleImg[") >= 0) {
					int idx = Integer.parseInt(String.valueOf(objName.charAt(10)));
					log.debug("++++++++++++++++ idx = "+idx);

					if (idx == 1) {
						curriTopDto.setCurriImg1(uploadFileName);
					} else if(idx == 2) {
						curriTopDto.setCurriImg2(uploadFileName);
					}

					if (!pOldFileName[idx].equals("")) {		//이전 첨부파일을 삭제할 경우
						log.debug("이전 파일 삭제하기"+FilePath+pOldFileName[idx]);
						FileUtil.delFile(FilePath, pOldFileName[idx]);
					}
				}
			}
		}
		curriTopDto.setSystemCode(systemCode);
		curriTopDto.setCurriCode(pCurriCode);
		curriTopDto.setCurriName(pCurriName);
		curriTopDto.setPareCode1(pPareCode1);
		curriTopDto.setPareCode2(pPareCode2);
		curriTopDto.setCateCode(pCateCode);
		curriTopDto.setLearningTime(pLearningTime);
		curriTopDto.setCurriGoal(pCurriGoal);
		curriTopDto.setCurriInfo(pCurriInfo);
		curriTopDto.setCurriEnv(pCurriEnv);
		curriTopDto.setAssessment(pAssessment);
		curriTopDto.setBeforeInfo(pBeforeInfo);
		curriTopDto.setCurriTarget(pCurriTarget);
		curriTopDto.setCurriProperty1(pCurriProperty1);
		curriTopDto.setCurriProperty2(pCurriProperty2);
		curriTopDto.setCredit(pCredit);
		curriTopDto.setRegId(regId);

		String pKeyword = "";

		if (new String("true").equals(multipartRequest.getParameter("VBN_FORM_WEAS")) == true) {//-- 웹에디터 사용시
			 pKeyword = StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_Preview"));
		} else {
			pKeyword = pCurriContents;
		}

		/** 웹에디터 셋팅 추가 Start**/
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH+FilePath;
		String WeasFileUrl =  "./data/"+FilePath;
		VBN_files v_objFile = null;

		java.io.File dir = new java.io.File(WeasFilePath);
		if (!dir.exists())	dir.mkdirs();

		v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
		pCurriContents = v_objFile.VBN_uploadMultiFiles(pCurriContents, multipartRequest);
		/** 웹에디터 셋팅 추가 End**/
		curriTopDto.setCurriContents(pCurriContents);
		curriTopDto.setCurriContentsText(pKeyword);
		CurriSubDAO curriSubDao = new CurriSubDAO();

		if (regMode.equals("Add")) {
			//-- 입력모드
			retVal = curriTopDao.addCurriTopInfo(curriTopDto);
			msg = "등록완료";
		} else if (regMode.equals("Edit")) {
			//-- 수정모드
			retVal = curriTopDao.editCurriTopInfo(curriTopDto, pChkEditCode);
			msg = "수정완료";

			if (retVal <= 0) {
				returnUrl = "/CurriTop.cmd?cmd=curriTopEdit&pCurriCode="+pCurriCode+"&pMode=MyPage";
				msg = "수정오류 다시 진행해 주세요";
			} else {
				retVal = curriSubDao.editCurriSubCurriName(systemCode,pCurriCode,pCurriName,regId);
			}
		}

		log.debug("retVal ==>"+retVal);
		log.debug("returnUrl ==>"+returnUrl);

		new SiteNavigation(MYPAGE).add(request,"과정관리").link(model);

		log.debug("=============================================================curriCategoryRegist end");

		return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}


	/**
	 * ==================================================
	 * 					과정정보 삭제
	 * ==================================================
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriTopDelete(ActionMapping actionMapping,
										ActionForm actionForm,
										HttpServletRequest request,
										HttpServletResponse response,
										Map model) throws Exception{

		String systemCode	= UserBroker.getSystemCode(request);
		String curriCode	= request.getParameter("pCurriCode");

		CurriTopDAO curriTopDao = new CurriTopDAO();
		//---- 개설과정 정보 검색후 삭제 루틴 만들기....

		CurriTopDTO curriTopDto = new CurriTopDTO();
		curriTopDto = curriTopDao.getCurriTopInfo(systemCode, curriCode);

		int retVal			= 0;
		String msg			= "";
		String returnUrl 	= "";

		retVal = curriTopDao.delCurriTopInfo(systemCode, curriCode);
		String fileName = "";

		if (retVal > 0) {
			msg = "삭제하였습니다.";
			returnUrl = "/CurriTop.cmd?cmd=curriTopList";

			//---- 파일이 있는경우 파일 삭제
			String filePath = FileUtil.IMG_DIR+systemCode+"/curri_top/";
			fileName = StringUtil.nvl(curriTopDto.getCurriImg1());

			if (!fileName.equals("")) {
				FileUtil.delFile(filePath, fileName);
				log.debug(" 첨부파일을 삭제하였습니다."+filePath+fileName);
			}
			fileName = StringUtil.nvl(curriTopDto.getCurriImg2());

			if (!fileName.equals("")) {
				FileUtil.delFile(filePath, fileName);
				log.debug(" 첨부파일을 삭제하였습니다."+filePath+fileName);
			}
		}else{
			msg = "삭제 실패 하였습니다.";
			returnUrl = "/CurriTop.cmd?cmd=curriTopEdit&pCurriCode="+curriCode;
		}

		new SiteNavigation(MYPAGE).add(request,"과정관리").link(model);

		return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}
}

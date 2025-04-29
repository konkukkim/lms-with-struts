package com.edutrack.curritop.action;


import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.curritop.dao.CourseDAO;
import com.edutrack.curritop.dao.CurriCategoryDAO;
import com.edutrack.curritop.dto.CourseDTO;
import com.edutrack.curritop.dto.CurriCategoryDTO;
import com.edutrack.curritop.dto.CurriCategoryTotalDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;


public class CurriCategoryAction extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();


	/**
	 * ==================================================
	 * 					과정분류 메인
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2006.05.19
	 * ==================================================
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriCategoryMain(ActionMapping actionMapping,
											ActionForm actionForm,
											HttpServletRequest request,
											HttpServletResponse response,
											Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		new SiteNavigation(MYPAGE).add(request,"과정분류관리").link(model);

		return forward(request, model, "/curri_category/categoryMain.jsp");
	}


	/**
	 * ==================================================
	 * 					과정분류 리스트
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2006.05.19
	 * ==================================================
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriCategoryList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String systemCode	= UserBroker.getSystemCode(request);
		String pStep		= StringUtil.nvl(request.getParameter("pStep"),"1");

		ListDTO curriCategoryList = null;
		int totCnt	= 0;
		int curPage = 1;

		if(request.getParameter("curPage") != null)
			curPage = Integer.parseInt(request.getParameter("curPage"));

		CurriCategoryDAO curriCategoryDao = new CurriCategoryDAO();

		model.put("curriCategoryList",	curriCategoryDao.getPcategory1List(curPage, systemCode, pStep));
		model.put("pStep",	pStep);

		if (pStep.equals("2") || pStep.equals("3")) {
			int cnt = curriCategoryDao.getCategoryCount(systemCode, pStep);
			model.put("cnt", String.valueOf(cnt));
		}

		new SiteNavigation(MYPAGE).add(request,"과정분류관리").link(model);

		return forward(request, model, "/curri_category/categoryList.jsp");
	}

	/**
	 * ==================================================
	 * 					과정분류 등록 폼
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2006.05.19
	 * ==================================================
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward curriCategoryWrite(ActionMapping actionMapping,
											ActionForm actionForm,
											HttpServletRequest request,
											HttpServletResponse response,
											Map model) throws Exception{

		String systemCode	= UserBroker.getSystemCode(request);
		String pStep		= StringUtil.nvl(request.getParameter("pStep"));

		CurriCategoryDAO curriCategoryDao		= new CurriCategoryDAO();
		CurriCategoryTotalDTO curriPCategoryDto = new CurriCategoryTotalDTO();

		//-- 상위 과정 리스트
		if (pStep.equals("2")) {
			ArrayList step2 = null;
			step2 = curriCategoryDao.getCategory(systemCode, pStep);
			model.put("step2", step2);
		} else if (pStep.equals("3")) {
			ArrayList step3 = null;
			step3 = curriCategoryDao.getCategory(systemCode, pStep);
			model.put("step3", step3);
		}

		model.put("pStep", pStep);

		new SiteNavigation(MYPAGE).add(request,"과정분류관리").link(model);

		return forward(request, model, "/curri_category/categoryWrite.jsp");
	}


	/**
	 * ==================================================
	 * 					과정분류 등록
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2005.05.17
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
	public ActionForward curriCategoryRegist(ActionMapping actionMapping,
												ActionForm actionForm,
												HttpServletRequest request,
												HttpServletResponse response,
												Map model) throws Exception{

		String systemCode	= UserBroker.getSystemCode(request);
		String regId		= UserBroker.getUserId(request);

		CurriCategoryDAO curriCategoryDao			= new CurriCategoryDAO();
		CurriCategoryTotalDTO curriCategoryTotalDto = new CurriCategoryTotalDTO();

		MultipartRequest multipartRequest = null;

		int retVal				= 0;
		String objName 			= "";
		String uploadFileName	= "";
		String uploadFilePath	= "";
		String	pCateInfo		=	"";

		String FilePath = FileUtil.IMG_DIR+systemCode+"/curri_category/";
		log.debug("FilePath ==>"+FilePath);

		String regMode = StringUtil.nvl(request.getParameter("pRegMode"),"Add");
		log.debug("regMode ==>"+regMode);

		//	파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath, "cateImg_");

		// 	파라미터를 빼온다.
		multipartRequest	= uploadEntity.getMultipart();
		String status		= uploadEntity.getStatus();

		String pStep 		= StringUtil.nvl(multipartRequest.getParameter("pStep"));
		String pCateCode	= StringUtil.nvl(multipartRequest.getParameter("pCateCode"));
		String pCateName 	= StringUtil.nvl(multipartRequest.getParameter("pCateName"));

		int pCateDepth = 0;

		if (pStep.equals("1")) {
			pCateDepth		= Integer.parseInt(multipartRequest.getParameter("pCateDepth"));
		}
		else if (pStep.equals("2")) {
			pCateInfo	=	StringUtil.nvl(multipartRequest.getParameter("pCateInfo"), "");
		}
		String pCateStatus	= null;

		//-- CateDepth 3 --> CateStatus 0
		//-- CateDepth 2 --> CateStatus 1
		if (pCateDepth == 3) {
			pCateStatus = "0";
		} else {
			pCateStatus = "1";
		}
		String pUseYn 		= StringUtil.nvl(multipartRequest.getParameter("pUseYn"));
		String pPareCode1	= StringUtil.nvl(multipartRequest.getParameter("pPareCode1"));
		String pPareCode2	= StringUtil.nvl(multipartRequest.getParameter("pPareCode2"));

		String msg = "";
		String returnUrl = "/CurriCategory.cmd?cmd=curriCategoryList&pStep="+pStep;

		//-- 과정분류 코드 중복 검사
		if (regMode.equals("Add")) {
			retVal = curriCategoryDao.checkCode(systemCode, pCateCode, pCateStatus, pStep);

			if (retVal > 0) {
				msg = "이미 사용하고 있는 분류코드입니다.<br>다른 코드를 사용해 주십시요";
				returnUrl = "/CurriCategory.cmd?cmd=curriCategoryWrite&pStep="+pStep;
				return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
			}
		}
//		String pOldFileName[] = new String[4];

//		for (int i=1; i<=3; i++) {
//			pOldFileName[i] = StringUtil.nvl(multipartRequest.getParameter("pOldFile["+i+"]"));
//		}
//		curriCategoryTotalDto.setCateImg1(pOldFileName[1]);
//		curriCategoryTotalDto.setCateImg2(pOldFileName[2]);
//		curriCategoryTotalDto.setCateImg3(pOldFileName[3]);
//
//		uploadFilePath = uploadEntity.getUploadPath();
//
//		if (status.equals("E")) {
//			log.debug("첨부 파일 올리려다 실패하였습니다.");
//
//		}else if (status.equals("O")) {
//			log.debug("첨부하신 파일이 용량을 초과했습니다.");
//
//		}else if (status.equals("I")) {
//			log.debug("첨부 파일의 정보가 잘못되었습니다.");
//
//		}else if(status.equals("S")) {
//			// 업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
//			log.debug("첨부 파일을 첨부하는데 성공하였습니다.");
//			ArrayList files = uploadEntity.getFiles();
//			UploadFile file = null;
//
//			for (int i = 0 ; i < files.size(); i++) {
//				file = (UploadFile)files.get(i);
//				objName = file.getObjName();
//				uploadFileName = StringUtil.nvl(file.getUploadName());
//
//				if(objName.indexOf("pTitleImg[") >= 0) {
//					int idx = Integer.parseInt(String.valueOf(objName.charAt(10)));
//					log.debug("++++++++++++++++ idx = "+idx);
//
//					if (idx == 1) {
//						curriCategoryTotalDto.setCateImg1(uploadFileName);
//
//					} else if(idx == 2) {
//						curriCategoryTotalDto.setCateImg2(uploadFileName);
//
//					} else if(idx == 3) {
//						curriCategoryTotalDto.setCateImg3(uploadFileName);
//					}
//
//					if (!pOldFileName[idx].equals("")) {		//이전 첨부파일을 삭제할 경우
//						log.debug("이전 파일 삭제하기"+FilePath+pOldFileName[idx]);
//						FileUtil.delFile(FilePath, pOldFileName[idx]);
//					}
//				}
//			}
//		}
		//-- DTO Setting
		curriCategoryTotalDto.setSystemCode(systemCode);

		if (pStep.equals("1")) {
			curriCategoryTotalDto.setPareCode1(pCateCode);
		}
		else if (pStep.equals("2")) {
			curriCategoryTotalDto.setPareCode1(pPareCode1);
			curriCategoryTotalDto.setPareCode2(pCateCode);
			curriCategoryTotalDto.setCateInfo(pCateInfo);
		}
		else if (pStep.equals("3")) {
			String	pareCode[]	=	StringUtil.split(pPareCode2, "|");
			curriCategoryTotalDto.setPareCode1(pareCode[0]);
			curriCategoryTotalDto.setPareCode2(pareCode[1]);
			curriCategoryTotalDto.setCateCode(pCateCode);
		}
		curriCategoryTotalDto.setCateName(pCateName);
		curriCategoryTotalDto.setCateDepth(pCateDepth);
		curriCategoryTotalDto.setCateStatus(pCateStatus);

		curriCategoryTotalDto.setUseYn(pUseYn);
		curriCategoryTotalDto.setRegId(regId);

		if (regMode.equals("Add")) {
			retVal = curriCategoryDao.addPCategoryInfo(curriCategoryTotalDto, pStep);
			msg = "등록완료";

		} else if (regMode.equals("Edit")) {
			retVal = curriCategoryDao.editPCategoryInfo(curriCategoryTotalDto, pStep);
			msg = "수정완료";

			if (retVal <= 0) {
				returnUrl = "/CurriCategory.cmd?cmd=categoryEdit&pCateCode="+pCateCode+"&pType="+pCateStatus+"&pStep="+pStep;
				msg = "수정오류 다시 진행해 주세요";
			}
		}
		log.debug("retVal ==>"+retVal);
		log.debug("returnUrl ==>"+returnUrl);

		new SiteNavigation(MYPAGE).add(request,"과정분류관리").link(model);

		//return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
		return redirect(returnUrl);
	}


	/**
	 * ==================================================
	 * 					과정분류 수정 폼
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2005.05.17
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
	public ActionForward curriCategoryEdit(ActionMapping actionMapping,
											ActionForm actionForm,
											HttpServletRequest request,
											HttpServletResponse response,
											Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		String cateCode	= StringUtil.nvl(request.getParameter("pCateCode"));
		String pStep	= StringUtil.nvl(request.getParameter("pStep"));

		CurriCategoryDAO curriCategoryDao = new CurriCategoryDAO();
		RowSet rs = curriCategoryDao.getCategoryInfo(systemCode, cateCode, pStep);
		rs.next();

		String chkUseY = "";
		String chkUseN = "";
		String cateImgPath1 = "";
		String cateImgPath2 = "";
		String cateImgPath3 = "";

		if(!StringUtil.nvl(rs.getString("cate_img1")).equals(""))
			cateImgPath1 = "&nbsp;"+"이전파일:"+StringUtil.nvl(rs.getString("cate_img1"))+"<input type=hidden name=pOldFile[1] value='"+StringUtil.nvl(rs.getString("cate_img1"))+"'><br>";

		if(!StringUtil.nvl(rs.getString("cate_img2")).equals(""))
			cateImgPath2 = "&nbsp;"+"이전파일:"+StringUtil.nvl(rs.getString("cate_img2"))+"<input type=hidden name=pOldFile[2] value='"+StringUtil.nvl(rs.getString("cate_img2"))+"'><br>";

		if(!StringUtil.nvl(rs.getString("cate_img3")).equals(""))
			cateImgPath3 = "&nbsp;"+"이전파일:"+StringUtil.nvl(rs.getString("cate_img3"))+"<input type=hidden name=pOldFile[3] value='"+StringUtil.nvl(rs.getString("cate_img3"))+"'><br>";

		if(StringUtil.nvl(rs.getString("use_yn")).equals("Y"))
			chkUseY = "checked";

		else
			chkUseN = "checked";

		//--
		if (pStep.equals("1")) {
			model.put("cateCode", 	StringUtil.nvl(rs.getString("pare_code1")));
			model.put("cate_depth", new Integer(rs.getInt("cate_depth")));
			model.put("cateCode", 	cateCode);
		}
		else if (pStep.equals("2")) {
			model.put("pare_code1", StringUtil.nvl(rs.getString("pare_code1")));
			model.put("cateCode", 	StringUtil.nvl(rs.getString("pare_code2")));
			model.put("cateInfo", 	StringUtil.nvl(rs.getString("cate_info")));

			ArrayList step2 = null;
			step2 = curriCategoryDao.getCategory(systemCode, pStep);
			model.put("step2", 		step2);
		}
		else if (pStep.equals("3")) {
			model.put("pare_code1", StringUtil.nvl(rs.getString("pare_code1")));
			model.put("pare_code2", StringUtil.nvl(rs.getString("pare_code2")));
			model.put("cateCode", 	StringUtil.nvl(rs.getString("cate_code")));

			ArrayList step3 = null;
			step3 = curriCategoryDao.getCategory(systemCode, pStep);
			model.put("step3", 		step3);
		}
		model.put("cateName", 		StringUtil.nvl(rs.getString("cate_name")));
		model.put("cateOldImg1",  	cateImgPath1);
		model.put("cateOldImg2",  	cateImgPath2);
		model.put("cateOldImg3",  	cateImgPath3);
		model.put("chkUseY", 		chkUseY);
		model.put("chkUseN", 		chkUseN);
		rs.close();

		model.put("pStep", pStep);

		if (pStep.equals("1") || pStep.equals("2")) {
			int curriCnt	= curriCategoryDao.getCategoryCount(systemCode, cateCode, pStep);
			model.put("curriCnt", 		String.valueOf(curriCnt));
		}

		int cnt = 0;
		if (pStep.equals("1")) {
			cnt			= curriCategoryDao.getCount(systemCode, cateCode, pStep);
		}

		model.put("cnt", 			String.valueOf(cnt));

		new SiteNavigation(MYPAGE).add(request,"과정분류관리").link(model);

		return forward(request, model, "/curri_category/categoryEdit.jsp");
	}


	/**
	 * ==================================================
	 * 					과정분류 삭제
	 * --------------------------------------------------
	 * 프로젝트명	: 한국디자인진흥원 (KIDP)
	 * 작성일		: 2005.05.17
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
	public ActionForward curriCategoryDelete(ActionMapping actionMapping,
											ActionForm actionForm,
											HttpServletRequest request,
											HttpServletResponse response,
											Map model) throws Exception{

		String systemCode	= UserBroker.getSystemCode(request);
		String cateCode		= StringUtil.nvl(request.getParameter("pCateCode"));
		String pStep		= StringUtil.nvl(request.getParameter("pStep"));

		CurriCategoryDAO curriCategoryDao = new CurriCategoryDAO();

		RowSet rs = curriCategoryDao.getCategoryInfo(systemCode, cateCode, pStep);
		rs.next();

		//-- 과정분류 디비 정보 삭제
		int retVal = 0;
		String msg = "";
		String returnUrl = "";

		retVal = curriCategoryDao.deleteCategoryInfo(systemCode, cateCode, pStep);

		String fileName = "";

		if (retVal > 0) {
			//--삭제 성공 시
			msg = "삭제하였습니다.";
			returnUrl = "/CurriCategory.cmd?cmd=curriCategoryList&pStep="+pStep;

			//-- 파일이 있는경우 파일 삭제
			String filePath = FileUtil.IMG_DIR+systemCode+"/curri_category/";

			for (int i=1; i<=3; i++) {
				fileName = StringUtil.nvl(rs.getString("cate_img"+i));

				if (!fileName.equals("")) {
					FileUtil.delFile(filePath, fileName);
					log.debug(" 첨부파일을 삭제하였습니다."+filePath+fileName);
				}
			}
		} else {
			msg = "삭제 실패 하였습니다.";
			returnUrl = "/CurriCategory.cmd?cmd=curriCategoryEdit&pCateCode="+cateCode+"&pStep="+pStep;
		}
		rs.close();

		new SiteNavigation(MYPAGE).add(request,"과정분류관리").link(model);

		//return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
		
		return redirect(returnUrl);
	}
}

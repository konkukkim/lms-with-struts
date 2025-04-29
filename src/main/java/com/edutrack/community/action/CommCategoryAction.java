package com.edutrack.community.action;
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
import com.edutrack.community.dao.CommCategoryDAO;
import com.edutrack.community.dto.CommCategoryDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;
/*
 * Created on 2004. 6. 28.
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

/**
 * @author bschoi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommCategoryAction extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();


	/**
	 * 동아리 분류관리(페이징 없음)
	 * 제주원격교육연수원 - jonghyun.park - 2006.03.21
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commCategoryList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode	= UserBroker.getSystemCode(request);
		String pMode 		= StringUtil.nvl(request.getParameter("pMode"),COMMUNITY);
		int totCnt 			= 0;

		CommCategoryDAO commCategoryDao = new CommCategoryDAO();
//		totCnt = commCategoryDao.getBbsCount(systemCode);
		RowSet rs = commCategoryDao.getCommCategoryList(systemCode);

//		model.put("systemCode", systemCode);
//		model.put("totCnt", String.valueOf(totCnt));
		model.put("rs", rs);
		model.put("pMode", pMode);

		new SiteNavigation(pMode).add(request,"동아리 분류관리").link(model);
        return forward(request, model, "/commCategory/commCatetoryList.jsp");
	}


	/**
	 * 게시판 정보 리스트를 가져온다.(페이징처리)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commCategoryPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("_______________________________________commCategoryPagingList start");
		String systemCode = UserBroker.getSystemCode(request);
		String pSearchKey = StringUtil.nvl(request.getParameter("pSearchKey"));
		String pKeyWord = StringUtil.nvl(request.getParameter("pKeyWord"));
		log.debug("_________pSearchKey:["+pSearchKey+"]");
		log.debug("_________pKeyWord:["+pKeyWord+"]");

		int totCnt = 0;
		int curPage = 1;
		if (request.getParameter("curPage") != null ) curPage = Integer.parseInt(request.getParameter("curPage"));
		log.debug("_________curPage:["+curPage+"]");

		ListDTO commCategoryPagingList = null;
		CommCategoryDAO  commCategoryDao = new CommCategoryDAO();
		commCategoryPagingList = commCategoryDao.getCommCategoryPagingList(curPage, systemCode, pSearchKey, pKeyWord );
		model.put("commCategoryPagingList", commCategoryPagingList);
		model.put("pSearchKey", pSearchKey);
		model.put("pKeyWord", pKeyWord);
		model.put("curPage", String.valueOf(curPage));

		log.debug("_______________________________________commCategoryPagingList end");

		 new SiteNavigation(MYPAGE).add(request,"동아리 분류관리").link(model);
         return forward(request, model, "/commCategory/commCatetoryPagingList.jsp");
	}

	/**
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commCategoryWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode	= UserBroker.getSystemCode(request);
		String pMode 		= StringUtil.nvl(request.getParameter("pMode"),COMMUNITY);
		
		model.put("pMode", pMode);
		
		new SiteNavigation(COMMUNITY).add(request,"동아리 카테고리 생성").link(model);
		return forward(request, model, "/commCategory/commCategoryWrite.jsp");
	}

	/**
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commCategoryRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=====================commCategoryRegist start");
		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);
		String pMode 		= StringUtil.nvl(request.getParameter("pMode"),COMMUNITY);

		CommCategoryDAO commCategoryDao = new CommCategoryDAO();
		CommCategoryDTO commCategoryDto = new CommCategoryDTO();

//		int pBbsId = Integer.parseInt(StringUtil.nvl(request.getParameter("pBbsId"),"0"));	//source
//		String pCateCode = StringUtil.nvl(request.getParameter("pCateCode"),"1");
//		int pCateCode = commCategoryDao.getMaxCateCode(systemCode);
//		log.debug("pCateCode ==>"+pCateCode);

		MultipartRequest multipartRequest = null;
		int retVal = 0;
		String FilePath = FileUtil.IMG_DIR+systemCode+"/community/";
		log.debug("FilePath ==>"+FilePath);

		String pOldFileName1 = "";
		String pOldFileName2 = "";
		String originName[] = new String[2];
		String renFileName[] = new String[2];

		String regMode = StringUtil.nvl(request.getParameter("pRegMode"));
		log.debug("regMode ==>"+regMode);
		//int maxId = bbsInfoDao.getMaxBbsId(systemCode);

		if(regMode.equals("Add"))// 입력모드
		{
//			pBbsId = bbsInfoDao.getMaxBbsId(systemCode);		// source
//			pCateCode = 중복체크 메소드 들어갈 자리
		}

//		  파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath,"cate_img");
//		UploadFiles uploadEntity = FileUtil.upload(request, FilePath,pSeqNo+"_"+regId);
		// 파라미터를 빼온다.
		multipartRequest = uploadEntity.getMultipart();
		String status = uploadEntity.getStatus();

		if(regMode.equals("Edit"))// 수정 모드 접근시		파일 삭제 정보 가져오기
		{
			log.debug("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			pOldFileName1 = StringUtil.nvl(multipartRequest.getParameter("pOldFileName1"));
			log.debug("pOldFileName1 ==>"+pOldFileName1);
			pOldFileName2 = StringUtil.nvl(multipartRequest.getParameter("pOldFileName2"));
			log.debug("pOldFileName2 ==>"+pOldFileName2);
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
			ArrayList files = uploadEntity.getFiles();
			log.debug("files.size() ==>"+files.size());
			UploadFile file = null;
			for(int i = 0 ; i < files.size(); i++){
				log.debug("++++++++++++++++ files.size() = "+files.size());
				file = (UploadFile)files.get(i);
				originName[i] = StringUtil.nvl(file.getRootName(),"");
				log.debug("++++++++++++++++ originName"+i+" = "+originName[i]);

				if(!originName.equals("")) {
					log.debug("++++++++++++++++ ObjName = "+file.getObjName());
					log.debug("++++++++++++++++ path = "+uploadEntity.getUploadPath());

					renFileName[i] = StringUtil.nvl(file.getUploadName(),"");
				}

				log.debug("++++++++++++++++ renFileName"+i+" = "+renFileName[i]);
			}


			if(originName[0]!=null && !originName[0].equals("")) {			//파일첨부를 했을경우
				log.debug("++++++++++++++++ renFileName[0] = "+renFileName[0]);
				commCategoryDto.setCateImg1(FilePath+renFileName[0]);
				if(pOldFileName1!=null && !pOldFileName1.equals("")) {		//이전 첨부파일을 삭제할 경우
					log.debug("이전 파일 삭제하기111"+pOldFileName1);
					FileUtil.delFile(FilePath, pOldFileName1);
					log.debug("이전 첨부파일을 삭제하였습니다.");
				}
			}else{									// 첨부 안한 경우
				if(pOldFileName1!=null && !pOldFileName1.equals("")) {		//이전 첨부파일을 삭제할 경우
					commCategoryDto.setCateImg1(pOldFileName1);
				}
				else{
					commCategoryDto.setCateImg1(pOldFileName1);
				}
			}

			if(originName[1]!=null && !originName[1].equals("")) {			//파일첨부를 했을경우
				log.debug("++++++++++++++++ renFileName = "+renFileName[1]);
				commCategoryDto.setCateImg2(FilePath+renFileName[1]);
				if(pOldFileName2!=null && !pOldFileName2.equals("")) {		//이전 첨부파일을 삭제할 경우
					log.debug("이전 파일 삭제하기222"+pOldFileName2);
					FileUtil.delFile(FilePath, pOldFileName2);
					log.debug("이전 첨부파일을 삭제하였습니다.");
				}
			}else{									// 첨부 안한 경우
				if(pOldFileName2!=null && !pOldFileName2.equals("")) {		//이전 첨부파일을 삭제할 경우
					commCategoryDto.setCateImg2(pOldFileName2);
				}
				else{
					commCategoryDto.setCateImg2(pOldFileName2);
				}
			}
		}
		String cateCode = StringUtil.nvl(multipartRequest.getParameter("pCateCode"));
		String cateName = StringUtil.nvl(multipartRequest.getParameter("pCateName"));
		commCategoryDto.setSystemCode(systemCode);
		commCategoryDto.setCateCode(cateCode);
		commCategoryDto.setCateName(cateName);

		String msg = "";
		String returnUrl = "/CommCategory.cmd?cmd=commCategoryList&pMode="+pMode+"&MENUNO=0";
		if(regMode.equals("Add"))// 입력모드
		{
			if(commCategoryDao.isCateCodeValid(systemCode,cateCode) == false){
				msg = cateCode+"는 이미 사용된 코드입니다. 다른 코드를 사용해주세요.";
				returnUrl = "/CommCategory.cmd?cmd=commCategoryWrite&pCateName="+cateName+"&pMode="+pMode+"&MENUNO=0";
			}
			else{
				commCategoryDto.setRegId(regId);
				retVal = commCategoryDao.addCommCategory(commCategoryDto);
				msg = "등록완료";
			}
		}else{
			commCategoryDto.setModId(regId);
			retVal = commCategoryDao.editCommCategory(commCategoryDto);
			msg = "수정완료";
			if(retVal <= 0){
				returnUrl = "/CommCategory.cmd?cmd=commCategoryEdit&pCateCode="+cateCode+"&pMode="+pMode+"&MENUNO=0";
				msg = "수정오류 다시 진행해 주세요";
			}
		}
		log.debug("retVal ==>"+retVal);

		new SiteNavigation(COMMUNITY).add(request,"동아리 관리").link(model);
		log.debug("======================commCategoryRegist end");
        //return notifyAndExit(systemCode, model, msg, returnUrl, COMMUNITY);
		return redirect(returnUrl);
	}

	/**
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commCategoryEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String cateCode = request.getParameter("pCateCode");
		String pMode 		= StringUtil.nvl(request.getParameter("pMode"),COMMUNITY);

		CommCategoryDAO commCategoryDao = new CommCategoryDAO();
		RowSet rs = commCategoryDao.getCommCategoryInfo(systemCode, cateCode);
		rs.next();

		model.put("cateCode", rs.getString("cate_code"));
		model.put("cateName", rs.getString("cate_name"));
		model.put("cateImg1", rs.getString("cate_img1"));
		model.put("cateImg2", rs.getString("cate_img2"));

		rs.close();
		
		model.put("pMode", pMode);

		new SiteNavigation(pMode).add(request,"동아리관리").link(model);
		return forward(request, model, "/commCategory/commCategoryEdit.jsp");
	}

	/**
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commCategoryFileDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("======================commCategoryFileDelete start");
		String systemCode = UserBroker.getSystemCode(request);
		String cateCode = request.getParameter("pCateCode");
		String pFileNo = request.getParameter("pFileNo");

		CommCategoryDAO commCategoryDao = new CommCategoryDAO();
		RowSet commCategoryRs = commCategoryDao.getCommCategoryInfo(systemCode,cateCode);
		commCategoryRs.next();
		String cateImg = "";

		String msg = "삭제하였습니다.";
		String returnUrl = "/CommCategory.cmd?cmd=commCategoryEdit&pCateCode="+cateCode;

		int retVal = 0;
		retVal = commCategoryDao.delCommCategoryFile(systemCode, cateCode, pFileNo);

		cateImg = commCategoryRs.getString("cate_img"+pFileNo);

		log.debug(" cateImg..."+cateImg);
		if(!cateImg.equals("")){
			FileUtil.delFile(cateImg);
			log.debug(" 첨부파일을 삭제하였습니다."+cateImg);
		}

		if(retVal <=0){
			msg = "첨부파일 삭제 실패 하였습니다.";
		}

		String pMode = StringUtil.nvl(request.getParameter("pMode"));
		if(pMode.equals("")) pMode = MYPAGE;
		model.put("pMode", pMode);
		new SiteNavigation(pMode).add(request,StringUtil.inDataConverter("동아리")).link(model);
        return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}

	/**
	 * 동아리분류 삭제
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward commCategoryDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String cateCode = request.getParameter("pCateCode");
		String pMode 		= StringUtil.nvl(request.getParameter("pMode"),COMMUNITY);
		CommCategoryDAO commCategoryDao = new CommCategoryDAO();

		RowSet bi = commCategoryDao.getCommCategoryInfo(systemCode,cateCode);//-- 첨부파일 삭제를 위해
		bi.next();
//		String FilePath = FileUtil.IMG_DIR+systemCode+"/bbs/";
		String cateImg1 = StringUtil.nvl(bi.getString("cate_img1"));
		String cateImg2 = StringUtil.nvl(bi.getString("cate_img2"));

		int retVal = 0;
		retVal = commCategoryDao.delCommCategory(systemCode,cateCode);
//		BbsContentsDAO bbsContentsDao = new BbsContentsDAO();
		if(!cateImg1.equals("") && retVal > 0){ //-- 게시판정보 삭제 성공하고 첨부 이미지 있는경우
			FileUtil.delFile(cateImg1);
//			retVal = bbsContentsDao.delBbsContents(systemCode,bbsId,0);
			log.debug("첫번째 첨부파일을 삭제하였습니다."+cateImg1);
		}

		if(!cateImg2.equals("") && retVal > 0){ //-- 게시판정보 삭제 성공하고 첨부 이미지 있는경우
			FileUtil.delFile(cateImg2);
//			retVal = bbsContentsDao.delBbsContents(systemCode,bbsId,0);
			log.debug("두번째 첨부파일을 삭제하였습니다."+cateImg2);
		}

		String msg = "삭제하였습니다.";
		String returnUrl = "/CommCategory.cmd?cmd=commCategoryList&pMode="+pMode+"&MENUNO=0";

		new SiteNavigation(MYPAGE).add(request,"동아리관리").link(model);
		return alertAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}
}

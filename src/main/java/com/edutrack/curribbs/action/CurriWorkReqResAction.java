/*
 * Created on 2004. 10. 26.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curribbs.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.VBN.VBN_files;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.curribbs.dao.CurriWorkReqResDAO;
import com.edutrack.curribbs.dto.CurriWorkReqResDTO;
import com.edutrack.currisub.dao.CurriSubCourseDAO;
import com.edutrack.currisub.dto.CurriCourseListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author epitaph
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CurriWorkReqResAction extends StrutsDispatchAction {

    /**
     *
     */
    public CurriWorkReqResAction() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * @param systemCode
     * @param curriCode
     * @param curriYear
     * @param curriTerm
     * @return
     * @throws Exception
     */
    public String curriCourseProfList(String systemCode, String curriCode, int curriYear, int curriTerm, String profId, String courseId) throws Exception{
        String retVal	=	"";
        ArrayList	curriCourseProfList	=	new ArrayList();
        CurriSubCourseDAO currisubcourseDao = new CurriSubCourseDAO();
        CurriCourseListDTO curricourselistDto = null;
        curriCourseProfList = currisubcourseDao.getCurriCourseList(systemCode, curriCode, curriYear, curriTerm, "");
        retVal = "<select name=pCurriCourseProfId style='width:150'><option value=''>-- 교수선택 --</option>";
        String chk	=	"";
        for(int i=0;i < curriCourseProfList.size(); i++){
            curricourselistDto = (CurriCourseListDTO)curriCourseProfList.get(i);
            chk = "";
            if((profId + "^" + courseId).equals(curricourselistDto.getProfId()+"^"+curricourselistDto.getCourseId())) chk = "selected";
            retVal += "<option "+chk+" value='"+curricourselistDto.getProfId()+"^"+curricourselistDto.getCourseId()+"'>"+curricourselistDto.getProfName()+"--"+curricourselistDto.getCourseName()+"</option>";
        }

        retVal += "</select>";
        return retVal;
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
    public ActionForward workReqResList (ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

        log.debug("############# workResList   start ###########################");

        String systemCode = UserBroker.getSystemCode(request);
        UserMemDTO usermemDto = UserBroker.getUserInfo(request);
        String	user_id = usermemDto.userId;
        String	user_type = usermemDto.userType;
        String	pWhere	=	StringUtil.nvl(request.getParameter("pWhere"));
        String	pSTarget	=  StringUtil.nvl(request.getParameter("pSTarget"));
        String	pSWord	=  StringUtil.nvl(request.getParameter("pSWord"));
        String  pData = StringUtil.nvl(request.getParameter("pData"));

        String pCurriCode = "";
		int pCurriYear = 0;
		int pCurriTerm = 0;
        if(!pData.equals("ALL") && !user_type.equals("S") && pWhere.equals("Lecture")){
	        CurriMemDTO curriMemDto = usermemDto.curriInfo;
		    pCurriCode = curriMemDto.curriCode;
			pCurriYear = Integer.parseInt(curriMemDto.curriYear);
			pCurriTerm = Integer.parseInt(curriMemDto.curriTerm);
        }

        if(pData.equals("ALL")) user_type = "M";
        log.debug("관리자............***"+pCurriCode+"**"+pCurriYear+"**"+pCurriTerm+"**"+user_type+"**********"+pWhere);
        ListDTO curriWorkList	=	null;

        //---- 페이징 설정
		int totCnt = 0;
		int curPage = 1;
		if(request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));

        CurriWorkReqResDAO curriworkreqresDao = new CurriWorkReqResDAO();
        totCnt = curriworkreqresDao.getCount(systemCode, user_id, user_type, pCurriCode, pCurriYear, pCurriTerm, pSTarget, pSWord );
        curriWorkList = curriworkreqresDao.getWorkReqResList(curPage, systemCode, user_id, user_type, pCurriCode, pCurriYear, pCurriTerm, pSTarget, pSWord);

        model.put("pUserType", user_type);
        model.put("totCnt", Integer.toString(totCnt));
        model.put("curriWorkList", curriWorkList);
        model.put("pWhere", pWhere);
        model.put("pSTarget", pSTarget);
        model.put("pSWord", pSWord);
        String	retUrl = pWhere.equals("Lecture") ?  "/curri_bbs/curriWorkReqResList.jsp" : "/curri_bbs/workReqResList.jsp";

        if(pData.equals("ALL")) retUrl = "/curri_bbs/workReqResListAll.jsp";

        log.debug("############# workResList   end ###########################");

        new SiteNavigation(pWhere).add(request,"개인면담").link(model);
		return forward(request, model, retUrl);
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
    public ActionForward workReqResWrite (ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model)throws Exception{

        log.debug("&&&&&&&&&& workReqResWrite start &&&&&&&&&&&&&");
        String 	systemCode 	= UserBroker.getSystemCode(request);
        String	regType		= StringUtil.nvl(request.getParameter("pRegType"));
        String	pMethodType		= StringUtil.nvl(request.getParameter("pMethodType"));
        String	pSeqNo		=	StringUtil.nvl(request.getParameter("pSeqNo"));
        String	pWhere	=	StringUtil.nvl(request.getParameter("pWhere"));
        int		seqNo		=	pSeqNo.equals("") ? 0 : Integer.parseInt(pSeqNo);
        UserMemDTO	usermemDto = UserBroker.getUserInfo(request);
        String	userId	=	usermemDto.userId;
        String	userType = usermemDto.userType;
        String	userName = usermemDto.userName;
		String pCurriCode = "";
		int pCurriYear = 0;
		int pCurriTerm = 0;
        if(pWhere.equals("Lecture")){
	        CurriMemDTO curriMemDto = usermemDto.curriInfo;
		    pCurriCode = curriMemDto.curriCode;
			pCurriYear = Integer.parseInt(curriMemDto.curriYear);
			pCurriTerm = Integer.parseInt(curriMemDto.curriTerm);
        }

        CurriWorkReqResDTO	curriworkreqresDto 	= 	null;
        CurriWorkReqResDAO	curriworkreqresDao	=	null;
        int	result	=	0;
        String profId = "";
        String courseId = "";

        if(regType.equals("Regist")){
            if(!pMethodType.equals("Insert")){
                curriworkreqresDao	=	new	CurriWorkReqResDAO();
                curriworkreqresDto = curriworkreqresDao.getWorkReqResInfo(systemCode, seqNo);
                profId = curriworkreqresDto.getProfId();
                courseId = curriworkreqresDto.getCourseId();
                model.put("workReqResInfo", curriworkreqresDto);
            }
            if(pMethodType.equals("Delete")){
                if(!curriworkreqresDto.getRfileName1().equals("")) fileDelete(curriworkreqresDto.getFilePath1(), curriworkreqresDto.getSfileName1());
                result = curriworkreqresDao.workReqResDelete(systemCode, seqNo);
            }
        }else if(regType.equals("Reply")){    //	교수자나, 관리자가 요청사항에 대해 답변을 할때...
            //curriworkreqresDto 	= 	new CurriWorkReqResDTO();
            curriworkreqresDao	=	new	CurriWorkReqResDAO();
            curriworkreqresDto = curriworkreqresDao.getWorkReqResInfo(systemCode, seqNo);
            model.put("workReqResInfo", curriworkreqresDto);

            if(pMethodType.equals("Insert")){		// 최초등록

            }else if(pMethodType.equals("Update")){	//	수정

            }
        }

        String	retUrl = pWhere.equals("Lecture") ?  "/curri_bbs/curriWorkReqResWrite.jsp" : "/curri_bbs/workReqResWrite.jsp";

        if(pMethodType.equals("Delete")){
            String msg = "삭제를 완료했습니다.";
            msg = result == 0 ? "삭제를 하는데 오류가 발생했습니다." : msg;
            log.debug("&&&&&&&&&& workReqResWrite end &&&&&&&&&&&&&");
	        new SiteNavigation(pWhere).add(request,"일대일 면담").link(model);
	        return notifyAndExit(systemCode, model, msg, "/CurriWorkReqRes.cmd?cmd=workReqResList&pWhere="+pWhere+"&pMode=Lecture", pWhere);
        }else{
            model.put("curriCourseProfList", curriCourseProfList(systemCode,pCurriCode, pCurriYear, pCurriTerm, profId, courseId));
	        model.put("methodType", pMethodType);
	        model.put("userId", userId);
	        model.put("userName", userName);
	        model.put("userType", userType);
	        model.put("regType", regType);
	        model.put("seqNo", pSeqNo);
	        model.put("pWhere", pWhere);
	        model.put("pSystemCode", systemCode);
	        model.put("profId",profId);
	        log.debug("&&&&&&&&&& workReqResWrite end &&&&&&&&&&&&&");
	        new SiteNavigation(pWhere).add(request,"일대일 면담").link(model);
            return forward(request, model, retUrl);
        }
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
    public ActionForward curriWorkReqResRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model)throws Exception{

        log.debug("&&&&&&&&&& curriWorkReqResRegist start &&&&&&&&&&&&&");

        String 	systemCode = UserBroker.getSystemCode(request);
        UserMemDTO	usermemDto = UserBroker.getUserInfo(request);
        String	pUserId	=	usermemDto.userId;
        String	userType = usermemDto.userType;
        String	userName = usermemDto.userName;
	    String	pWhere	=	StringUtil.nvl(request.getParameter("pWhere"));

		String pCurriCode = "";
		int pCurriYear = 0;
		int pCurriTerm = 0;
        if(pWhere.equals("Lecture")){
	        CurriMemDTO curriMemDto = usermemDto.curriInfo;
		    pCurriCode = curriMemDto.curriCode;
			pCurriYear = Integer.parseInt(curriMemDto.curriYear);
			pCurriTerm = Integer.parseInt(curriMemDto.curriTerm);
        }

		MultipartRequest multipartRequest = null;

        String RegMonth = CommonUtil.getCurrentDate().substring(0,6);
	    String	FilePath = FileUtil.FILE_DIR+systemCode+"/curriWorkReqRes/"+RegMonth+"/";

	    UploadFiles uploadEntity	=	FileUtil.upload(request, FilePath, pUserId);

	    multipartRequest = uploadEntity.getMultipart();
	    String status = uploadEntity.getStatus();

        String  pCurriCourseProfId = StringUtil.nvl(multipartRequest.getParameter("pCurriCourseProfId"));
        log.debug("!!!!!!!!!pCurriCourseProfId="+pCurriCourseProfId);
        String 	pRegType	=	StringUtil.nvl(multipartRequest.getParameter("pRegType"));
        String 	pMethodType	=	StringUtil.nvl(multipartRequest.getParameter("pMethodType"));
        String 	pSeqNo	=	StringUtil.nvl(multipartRequest.getParameter("pSeqNo")).equals("") ? "0" : StringUtil.nvl(multipartRequest.getParameter("pSeqNo"));
        String 	pEditorType	=	"";
        String pFILE_NEW1_ori = StringUtil.nvl(multipartRequest.getParameter("pFILE_NEW1_ori"));
	    String	pSUBJECT	=	StringUtil.nvl(multipartRequest.getParameter("pSubject"));
	    String	pCONTENTS	=	StringUtil.nvl(multipartRequest.getParameter("pContents"));
	    String	pOldRfileName	=	StringUtil.nvl(multipartRequest.getParameter("oldRfileName"));
	    String	pOldSfileName	=	StringUtil.nvl(multipartRequest.getParameter("oldSfileName"));
	    String	pOldFilePath	=	StringUtil.nvl(multipartRequest.getParameter("oldFilePath"));
	    String	pOldFileSize	=	StringUtil.nvl(multipartRequest.getParameter("oldFileSize"));

	    if( new String("true").equals(StringUtil.nvl(multipartRequest.getParameter("VBN_FORM_WEAS"))) == true ){//-- 웹에디터 사용시
			 pEditorType = "W";
		}else{//-- 웹에디터 사용 안할경우
			pEditorType = "H";
		}
	    CurriWorkReqResDAO  curriworkreqresDao = new CurriWorkReqResDAO();
	    CurriWorkReqResDTO	curriworkreqresDto = new CurriWorkReqResDTO();

	    String pRFILENAME = "";
		String pSFILENAME = "";
		String pFILEPATH = "";
		String pFILESIZE = "";

		String rFileName = "";
		String sFileName = "";
		long fileSize = 0;
		String filePath = "";
		boolean noRfile	=	false;

		if (status.equals("E")){
			log.debug("첨부 파일 올리려다 실패하였습니다.");
		}else if (status.equals("O")){
			log.debug("첨부하신 파일이 용량을 초과했습니다.");
		}else if (status.equals("I")){
			log.debug("첨부 파일의 정보가 잘못되었습니다.");
		}else if(status.equals("S")){
			// 업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.

			log.debug("첨부 파일을 첨부하는데 성공하였습니다.");

			if(!pFILE_NEW1_ori.equals("")) {	/** 웹에디터에서 첨부파일을 첨부시 인식하는걸 막기위해 **/
				ArrayList files = uploadEntity.getFiles();
				UploadFile file = null;

				for(int i = 0 ; i < files.size(); i++){
					file = (UploadFile)files.get(i);

					if(pFILE_NEW1_ori.indexOf(StringUtil.nvl(file.getRootName())) != -1) {
						rFileName = StringUtil.nvl(file.getRootName());
						sFileName = file.getUploadName();
				//log.debug("++++++++++++++++++++++++++++++++++++++++++++++="+sFileName+"**"+rFileName);
						fileSize = file.getSize();
						filePath = uploadEntity.getUploadPath();
					}
				}
			}

			if(pRegType.equals("Regist")){

				if(!rFileName.equals("")) {			//파일첨부를 했을경우
				    curriworkreqresDto.setRfileName1(rFileName);
				    curriworkreqresDto.setSfileName1(sFileName);
				    curriworkreqresDto.setFilePath1(filePath);
				    curriworkreqresDto.setFileSize1(Double.toString(fileSize));
				    noRfile = false;
				} else {								//파일첨부를 하지 않았을 경우
				    curriworkreqresDto.setRfileName1(pRFILENAME);
				    curriworkreqresDto.setSfileName1(pSFILENAME);
				    curriworkreqresDto.setFilePath1(pFILEPATH);
				    curriworkreqresDto.setFileSize1(pFILESIZE);
				    noRfile = true;
				}
			}else{
			    if(!rFileName.equals("")) {			//파일첨부를 했을경우
				    curriworkreqresDto.setRfileName2(rFileName);
				    curriworkreqresDto.setSfileName2(sFileName);
				    curriworkreqresDto.setFilePath2(filePath);
				    curriworkreqresDto.setFileSize2(Double.toString(fileSize));
				    noRfile = false;
				} else {								//파일첨부를 하지 않았을 경우
				    curriworkreqresDto.setRfileName2(pRFILENAME);
				    curriworkreqresDto.setSfileName2(pSFILENAME);
				    curriworkreqresDto.setFilePath2(pFILEPATH);
				    curriworkreqresDto.setFileSize2(pFILESIZE);
				    noRfile = true;
				}
			}
			log.debug("파일업로드를 정상적으로 종료 후 DTO에 값 담는다.");
		}

//		----------- 웹에디터 시작 ------------------------------------
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH+FilePath;
		String WeasFileUrl =  ServiceUrl+"/data/"+FilePath;
		log.debug("WeasFilePath = "+WeasFilePath);
		VBN_files v_objFile = null;

		java.io.File dir = new java.io.File(WeasFilePath);
		if (!dir.exists())	dir.mkdirs();

		v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
		pCONTENTS = v_objFile.VBN_uploadMultiFiles(pCONTENTS, multipartRequest);
		//----------- 웹에디터 끝 ------------------------------------

		log.debug("웹에디터 끝....");
		curriworkreqresDto.setCurriCode(pCurriCode);
		curriworkreqresDto.setCurriYear(pCurriYear);
		curriworkreqresDto.setCurriTerm(pCurriTerm);
		log.debug("*********"+pRegType+"**"+pMethodType+"******111111111");
		int	result	=	0;
		if(pRegType.equals("Regist")){
		    log.debug("*********"+pRegType+"**"+pMethodType+"******22222222");
		    curriworkreqresDto.setSeqNo(Integer.parseInt(pSeqNo));
		    log.debug("*********"+pRegType+"**"+pMethodType+"******2222222211111");
		    if(pMethodType.equals("Delete")){
		       result = curriworkreqresDao.workReqResDelete(systemCode, Integer.parseInt(pSeqNo));
		    }else{
		        curriworkreqresDto.setStudentId(pUserId);
		        log.debug("*********"+pRegType+"**"+pMethodType+"******3333333333");
		        String[]	courseProf	=	{"",""};
			    if(pCurriCourseProfId.indexOf("^") != -1)	courseProf = StringUtil.split(pCurriCourseProfId,"^");
		        curriworkreqresDto.setProfId(courseProf[0]);
		        curriworkreqresDto.setCourseId(courseProf[1]);
			    curriworkreqresDto.setReqSubject(pSUBJECT);
			    curriworkreqresDto.setReqContents(pCONTENTS);
			    curriworkreqresDto.setHtmlUse1(pEditorType);

			    if(pMethodType.equals("Insert")){
			        result = curriworkreqresDao.workReqResRegistInsert(systemCode, curriworkreqresDto);
			    }else{
			        if(!rFileName.equals("") && !pOldRfileName.equals("")) fileDelete(pOldFilePath,pOldSfileName);
			        result = curriworkreqresDao.workReqResRegistUpdate(systemCode, curriworkreqresDto, noRfile);
			    }
		    }

		}else{
		    curriworkreqresDto.setResRegId(pUserId);
		    curriworkreqresDto.setResModId(pUserId);
		    curriworkreqresDto.setResContents(pCONTENTS);
		    curriworkreqresDto.setHtmlUse2(pEditorType);
		    curriworkreqresDto.setSeqNo(Integer.parseInt(pSeqNo));
		    if(pMethodType.equals("Insert")){
		        result = curriworkreqresDao.workReqResReplyUpdate(systemCode, curriworkreqresDto, "Insert", noRfile);
		    }else{
		        if(!rFileName.equals("") && !pOldRfileName.equals("")) fileDelete(pOldFilePath,pOldSfileName);
		        result = curriworkreqresDao.workReqResReplyUpdate(systemCode, curriworkreqresDto, "Update", noRfile);
		    }

		}
		log.debug("*********"+pRegType+"**"+pMethodType+"******444444444");
		String msg = "처리중에 오류가 발생했습니다..다시 시도 바랍니다.";
		if(result != 0) msg = "해당 처리를 완료했습니다.";

		log.debug("&&&&&&&&&& curriWorkReqResRegist end &&&&&&&&&&&&&");

		new SiteNavigation(pWhere).add(request,"일대일 면담").link(model);
		return notifyAndExit(systemCode, model, msg, "/CurriWorkReqRes.cmd?cmd=workReqResList&pWhere="+pWhere, pWhere);
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
    public ActionForward curriWorkReqResShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model)throws Exception{
        log.debug("********* curriWorkReqResShow  start ******");
        String  systemCode = UserBroker.getSystemCode(request);
        UserMemDTO	usermemDto = UserBroker.getUserInfo(request);
        String	pUserId	=	usermemDto.userId;
        String	userType = usermemDto.userType;
        String	pWhere	=	StringUtil.nvl(request.getParameter("pWhere"));
        String	pSeqNo	=	StringUtil.nvl(request.getParameter("pSeqNo")).equals("") ? "0" : StringUtil.nvl(request.getParameter("pSeqNo"));

        CurriWorkReqResDAO 	curriworkreqresDao = new CurriWorkReqResDAO();
        CurriWorkReqResDTO	curriworkreqresDto = curriworkreqresDao.getWorkReqResInfo(systemCode, Integer.parseInt(pSeqNo));

        model.put("systemCode", systemCode);
        model.put("pUserType", userType);
        model.put("curriWorkReqResInfo", curriworkreqresDto);
        model.put("pSeqNo", pSeqNo);
        model.put("pWhere", pWhere);

        String	retUrl = pWhere.equals("Lecture") ?  "/curri_bbs/curriWorkReqResShow.jsp" : "/curri_bbs/workReqResShow.jsp";

        log.debug("********* curriWorkReqResShow  end ******");
        new SiteNavigation(pWhere).add(request,"일대일 면담").link(model);
		return forward(request, model, retUrl);

    }

    public void fileDelete(String filePath, String sFileName) throws Exception{

        FileUtil.delFile(filePath, sFileName);
		log.debug(" 첨부파일을 삭제하였습니다."+filePath+sFileName);
		log.debug("+++++++++++++++++++ 첨부파일을 삭제하였습니다 ");
    }

    public ActionForward insertPoint (ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model)throws Exception{
        String	systemCode = UserBroker.getSystemCode(request);
        String	pSeqNo	=	StringUtil.nvl(request.getParameter("pSeqNo")).equals("") ? "0" : StringUtil.nvl(request.getParameter("pSeqNo"));
        String	pPoint	=	StringUtil.nvl(request.getParameter("pPoint")).equals("") ? "0" : StringUtil.nvl(request.getParameter("pPoint"));
        String	pWhere	=	StringUtil.nvl(request.getParameter("pWhere"));
        CurriWorkReqResDAO curriworkreqresDao = new CurriWorkReqResDAO();
        int	result = curriworkreqresDao.insertPoint(systemCode,Integer.parseInt(pSeqNo), Integer.parseInt(pPoint));
        String	msg = "평가를 수행하는데 오류가 발생했습니다.";
        if(result > 0)
            msg = "평가를 완료했습니다.";

        new SiteNavigation(pWhere).add(request,"일대일 면담").link(model);
		return notifyAndExit(systemCode, model, msg, "/CurriWorkReqRes.cmd?cmd=workReqResList&pWhere="+pWhere, pWhere);
    }

    public ActionForward workReqResStatList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model)throws Exception{

        log.debug("********* workReqResStatList  start ******");

        String	systemCode = UserBroker.getSystemCode(request);
        String	pWhere	=	StringUtil.nvl(request.getParameter("pWhere"));
        String	pSTarget	=  StringUtil.nvl(request.getParameter("pSTarget"));
        String	pSWord	=  StringUtil.nvl(request.getParameter("pSWord"));

        ListDTO statList = null;
        int totCnt = 0;
		int curPage = 1;
		if(request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));

        CurriWorkReqResDAO curriworkreqresDao = new CurriWorkReqResDAO();

        log.debug("^^^^^^^^^^^^^^^^^^^^ = "+ pSTarget+"**"+pSWord);

        CurriWorkReqResDTO curriworkreqresDto = null;
        totCnt 		=	curriworkreqresDao.getWorkReqResStatCount(systemCode, pSTarget, pSWord);
        statList	=	curriworkreqresDao.getWorkReqResStatList(curPage, systemCode, pSTarget, pSWord);

        model.put("totCnt", Integer.toString(totCnt));
        model.put("pWhere", pWhere);
        model.put("statList", statList);
        model.put("pSTarget", pSTarget);
        model.put("pSWord", pSWord);

        String	retUrl	=	"/curri_bbs/workReqResStatList.jsp";

        log.debug("********* workReqResStatList  end ******");

        new SiteNavigation(pWhere).add(request,"통 계").link(model);
		return forward(request, model, retUrl);

    }

}

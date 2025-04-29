package com.edutrack.curritop.action;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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

import com.edutrack.code.dao.CodeDaeDAO;
import com.edutrack.code.dto.CodeDaeDTO;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.config.dao.ConfigSubDAO;
import com.edutrack.config.dto.ConfigSubDTO;
import com.edutrack.currisub.action.ScormStudyAction;
import com.edutrack.currisub.dao.ScormStudyDAO;
import com.edutrack.curritop.dao.ContentsDAO;
import com.edutrack.curritop.dao.CourseDAO;
import com.edutrack.curritop.dto.ContentsDTO;
import com.edutrack.curritop.dto.ContentsFileDTO;
import com.edutrack.curritop.dto.CourseDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;

/*
 * Created on 2004. 10. 07
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
public class ContentsAction extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();
	protected ArrayList WCMfileList = null;


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
		log.debug("===== contentsOrder ="+contentsOrder+"===== getParentOrder="+retVal);
		return retVal;
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
	public ActionForward WCM(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String 	pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		String 	pCourseName 	= 	StringUtil.nvl(request.getParameter("pCourseName"));
		String 	pWorkType 		= 	StringUtil.nvl(request.getParameter("pWorkType"));
		String 	pContentsType 	= 	StringUtil.nvl(request.getParameter("pContentsType"));
		String 	pContentsId 	= 	StringUtil.nvl(request.getParameter("pContentsId"));
		String 	pContentsOrder 	= 	StringUtil.nvl(request.getParameter("pContentsOrder"));
		String 	pContentsName 	= 	StringUtil.nvl(request.getParameter("pContentsName"));
		String 	pServerPath 	= 	StringUtil.nvl(request.getParameter("pServerPath"));
		String 	pShowTime 		= 	StringUtil.nvl(request.getParameter("pShowTime"));
		String 	pSpeContents 	= 	StringUtil.nvl(request.getParameter("pSpeContents"));
		String  pServerPathDir 	=   "";
		String  ContentsRoot 	=   FileUtil.CONTENTS_DIR+systemCode+"/"+pCourseId;

		if(pServerPath.equals("")){
			pServerPath = ContentsRoot;
			pServerPathDir = pServerPath;
		}else{
			pServerPathDir = pServerPath.substring(0,pServerPath.lastIndexOf("/"));
		}

		String curPath	= FileUtil.UPLOAD_PATH+ContentsRoot;
		ArrayList arrflist = new ArrayList();
		this.WCMfileList = new ArrayList();
		arrflist = WCMDirList(curPath, "0");
		//arrflist = contentsFileStructure(curPath, "0");
		this.WCMfileList = null;

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
        model.put("pSpeContents", pSpeContents);

        //return forward(request, model, "/contents/WCM.jsp");
        return forward(request, model, "/contents/contentsFileTree.jsp");
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
				WCMDirList(tmpDir,tmpDepth);
			}
		}
		fileDto = null;
		return this.WCMfileList;
	}

	/**
	 * 목차 구성(WebContentsManager)시 서버의 디렉토리 구성 정보를 가져오기 위한 Method
	 * 파일리스트까지 받아올때 (sangsang 2007.05.02)
	 * @param filePath
	 * @param depth
	 * @return
	 * @throws Exception
	 */
	public ArrayList contentsFileStructure(String filePath,String depth)throws Exception{
		ContentsFileDTO fileDto = null;
		File	sfile		=	new File(filePath);
		String[] flist		=	sfile.list();
		int dirCnt =0;
		String fileDir = filePath.replaceAll(FileUtil.UPLOAD_PATH,"");
		for (int i=0; i<flist.length; i++) {
			fileDto = new ContentsFileDTO();
			File	sfile2		=	new File(filePath+"/"+flist[i]);
			String tmpDir="";

			String tmpDepth = depth;
			dirCnt++;
			if(depth.equals("0")) tmpDepth = String.valueOf(dirCnt);
			else tmpDepth = depth+"_"+String.valueOf(dirCnt);
			fileDto.setFileName(flist[i]);
			fileDto.setFileDir(fileDir);
			fileDto.setDepth(tmpDepth);
			fileDto.setThisDir(sfile2.isDirectory());
			fileDto.setThisFile(sfile2.isFile());
			if(sfile2.isFile()){
				fileDto.setFileExt(FileUtil.getFileExtention(flist[i].toLowerCase()));
				fileDto.setFileExtImg(WCMGetThumb(fileDto.getFileExt()));
				fileDto.setFileTime(DateTimeUtil.getTimeMillisDate(sfile2.lastModified(),"-"));
				fileDto.setFileSize(sfile2.length());
			}

			this.WCMfileList.add(fileDto);
			tmpDir = filePath+"/"+flist[i];
			if(sfile2.isDirectory())
				contentsFileStructure(tmpDir,tmpDepth);

		}
		fileDto = null;
		return this.WCMfileList;
	}

	/**
	 * 목차 구성(WebContentsManager)시 서버 파일에 맞는 이미지를 부여하기 위한 Method
	 * @param fileExt
	 * @return
	 * @throws Exception
	 */
	public String WCMGetThumb(String fileExt) throws Exception{
		String thumb = "ffile.gif";
		if(fileExt.equals(".au")) thumb = "fsound.gif";
		if(fileExt.equals(".asf")) thumb = "fmedia.gif";
		if(fileExt.equals(".asx")) thumb = "fmedia.gif";
		if(fileExt.equals(".avi")) thumb = "fmedia.gif";
		if(fileExt.equals(".bmp")) thumb = "fpaint.gif";
		if(fileExt.equals(".doc")) thumb = "fdoc.gif";
		if(fileExt.equals(".dot")) thumb = "fdoc.gif";
		if(fileExt.equals(".exe")) thumb = "fexe.gif";
		if(fileExt.equals(".gif")) thumb = "fimage.gif";
		if(fileExt.equals(".gz")) thumb = "fzip.gif";
		if(fileExt.equals(".html")) thumb = "fhtml.gif";
		if(fileExt.equals(".htm")) thumb = "fhtml.gif";
		if(fileExt.equals(".jpg")) thumb = "fimage.gif";
		if(fileExt.equals(".mdb")) thumb = "faccess.gif";
		if(fileExt.equals(".mid")) thumb = "fsound.gif";
		if(fileExt.equals(".mov")) thumb = "fmedia.gif";
		if(fileExt.equals(".mp3")) thumb = "fsound.gif";
		if(fileExt.equals(".mpg")) thumb = "fmedia.gif";
		if(fileExt.equals(".pdf")) thumb = "fimage.gif";
		if(fileExt.equals(".png")) thumb = "fimage.gif";
		if(fileExt.equals(".ppt")) thumb = "fpowerp.gif";
		if(fileExt.equals(".psd")) thumb = "fpsd.gif";
		if(fileExt.equals(".ps")) thumb = "fimage.gif";
		if(fileExt.equals(".rtf")) thumb = "frtf.gif";
		if(fileExt.equals(".tar")) thumb = "fzip.gif";
		if(fileExt.equals(".tif")) thumb = "fpsd.gif";
		if(fileExt.equals(".txt")) thumb = "ftxt.gif";
		if(fileExt.equals(".wav")) thumb = "fsound.gif";
		if(fileExt.equals(".wmv")) thumb = "fmedia.gif";
		if(fileExt.equals(".xls")) thumb = "fxls.gif";
		if(fileExt.equals(".zip")) thumb = "fzip.gif";
		return thumb;
    }

	/**
	 * 목차 구성(WebContentsManager)시 파일 리스트를 가져오는부분
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward WCMFrameRight(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String 	pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		String 	pContentsType 	= 	StringUtil.nvl(request.getParameter("pContentsType"));
		String 	pServerPath 	= 	StringUtil.inDataConverter(StringUtil.nvl(request.getParameter("pServerPath")));
		String  pPathDir 		=   StringUtil.inDataConverter(StringUtil.nvl(request.getParameter("pPathDir")));


		String	curPath		=	FileUtil.UPLOAD_PATH+pServerPath;

		if(!pPathDir.equals("")) curPath = FileUtil.UPLOAD_PATH+pPathDir;
		if(pPathDir.equals("")) pPathDir = pServerPath;
		ArrayList arrFileList = null;
		ArrayList arrDirList = null;
		log.debug("+++++++++++++++++ cur_path = "+curPath);
		File	sfile		=	new File(curPath);
		String[] flist		=	sfile.list();
		//if(flist.length > 0){
			arrFileList = new ArrayList();
			arrDirList = new ArrayList();
		//}
		ContentsFileDTO contentsFileDto = null;
		String fileDir = curPath.replaceAll(FileUtil.UPLOAD_PATH,"");
		for (int i=0; i<flist.length; i++) {
		    contentsFileDto = new ContentsFileDTO();
			File	sfile2		=	new File(curPath+"/"+flist[i]);
			log.debug("========================= sfile2 = "+curPath+"/"+flist[i]);
			contentsFileDto.setFileName(flist[i]);
			contentsFileDto.setFileExt(FileUtil.getFileExtention(flist[i].toLowerCase()));
			contentsFileDto.setFileExtImg(WCMGetThumb(contentsFileDto.getFileExt()));
			contentsFileDto.setThisDir(sfile2.isDirectory());
			contentsFileDto.setThisFile(sfile2.isFile());
			contentsFileDto.setFileDir(fileDir);
			if(sfile2.isFile()){
				contentsFileDto.setFileTime(DateTimeUtil.getTimeMillisDate(sfile2.lastModified(),"-"));
				contentsFileDto.setFileSize(sfile2.length());
				arrFileList.add(contentsFileDto);
			}
			else
				arrDirList.add(contentsFileDto);
		}
		model.put("systemCode", systemCode);
		model.put("userId", userId);
		model.put("pCourseId", pCourseId);
        model.put("pServerPath", pServerPath);
        model.put("pPathDir", pPathDir);
        model.put("pFileList", arrFileList);
        model.put("pDirList", arrDirList);
        //return forward(request, model, "/contents/WCMFrameRight.jsp");
        return forward(request, model, "/contents/fileListShow.jsp");
    }


	/**
	 * webFtp 접속
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward webFtp(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String 	pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		String 	pServer 		= 	StringUtil.nvl(request.getParameter("pServer"));
        String 	os_type 		= StringUtil.nvl(CommonUtil.SERVERTYPE);
        String year = "2007";
        String term = "1";
        model.put("pSERVER", pServer);
        model.put("year", year);
        model.put("term", term);
        model.put("pCourseId", pCourseId);
        model.put("os_type", os_type);
        return forward(request, model, "/contents/webFtp.jsp");
    }

	/**
	 * webFtpConfig
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward webFtpConfig(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
        String  year			=	StringUtil.nvl(request.getParameter("pYEAR"));
        String  term			=	StringUtil.nvl(request.getParameter("pTERM"));
        String 	pCOURSE_ID 		= 	StringUtil.nvl(request.getParameter("pCOURSE_ID"));
        String 	pUSER_ID 		= 	StringUtil.nvl(request.getParameter("pUSER_ID"));

        ConfigSubDAO configSubDao = new ConfigSubDAO();
		ConfigSubDTO configSubDto = new ConfigSubDTO();

//        String	courseVodserver		=	Integer.toString(lmcontentsdto.getVodServer());
//    	if (courseVodserver.length() == 1) {
//    		courseVodserver			=	"0" + wk_code2;
//    	}  //-- 과목의 동영상 서버 번호 (1,2) 를 던져주기    기본 1로 설정
		String courseVodserver = "01";
        String ConfigValue	=	"+";
        String pConfigType =  "310";//-- 교재편집도구환경설정
        RowSet rsSub = configSubDao.getConfigSubList(systemCode,pConfigType);
        while(rsSub.next()){
        	if(StringUtil.nvl(rsSub.getString("config_code")).substring(0,2).equals("00") || StringUtil.nvl(rsSub.getString("config_code")).substring(0,2).equals(courseVodserver)){
        		ConfigValue += StringUtil.nvl(rsSub.getString("config_value")) + "+";
            }
        }
        rsSub.close();
        model.put("ConfigValue", ConfigValue);
        return forward(request, model, "/contents/webFtpConfig.jsp");
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
	public ActionForward SCMFrame(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String 	pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		model.put("pCourseId", pCourseId);
        return forward(request, model, "/contents/SCMFrame.jsp");
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
	public ActionForward SCMUploadForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String 	pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));

		ContentsDAO contentsDao = new ContentsDAO();
		int pContentsCnt = contentsDao.getTotalContentsCnt(systemCode,pCourseId);
		model.put("pCourseId", pCourseId);
        model.put("pContentsCnt", String.valueOf(pContentsCnt));
        return forward(request, model, "/contents/SCMUploadForm.jsp");
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
	public ActionForward SCMUpload(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=================================SCMUpload start");
		String systemCode 		= 	UserBroker.getSystemCode(request);
		String regId 			= 	UserBroker.getUserId(request);
		String pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		String pUploadMode 		= 	StringUtil.nvl(request.getParameter("pUploadMode"));
		String ContentsRoot 	=   FileUtil.CONTENTS_DIR+systemCode+"/"+pCourseId;
		String ParamXmlPath		=	FileUtil.UPLOAD_PATH+ContentsRoot+"/parameters.xml";
		ContentsDAO contentsDao	=	new ContentsDAO();
		ContentsDTO contentsDto	=	null;


		int addOrder = 0;
		int retVal =0;
		String msg = "";
		if(pUploadMode.equals("ADD"))
			addOrder = Integer.parseInt(contentsDao.getMakeContentsOrder(systemCode,pCourseId,"").substring(0,3));

		log.debug("___________________ ParamXmlPath   "+ParamXmlPath);
		Loader ld = new Loader(request.getInputStream(), ParamXmlPath);
		log.debug("___________________ FileUtil.UPLOAD_PATH = "+FileUtil.UPLOAD_PATH);
		String course_id = ld.getParaValue("CourseID");
		log.debug("___________________ course_id   "+course_id);
	    if (ld.paraOK.equalsIgnoreCase("OK")) {

	    	if(pUploadMode.equals("MOD"))
	    		retVal = contentsDao.delContents(systemCode,pCourseId,"");
	    	log.debug("___________________ del retVal   "+retVal);
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
					contentsDto 	= new ContentsDTO();
					contentsDto.setSystemCode(systemCode);
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
					retVal = contentsDao.addContents(contentsDto);

				}
			}
	    }

		if(retVal >0){
			msg = "정상 업로드 되었습니다.";
		}else{
			msg = "업로드에 문제가 발생하였습니다. <br> 처음부터 다시 진행해 주세요 <br> 지속적인 문제 발생시 관리자에게 문의해 주세요.";
		}

	    model.put("retVal", String.valueOf(retVal));
        return forward(request, model, "/contents/SCMUpload.jsp");
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
	public ActionForward SCMComplete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String 	retVal 		= 	StringUtil.nvl(request.getParameter("retVal"));
		model.put("retVal", retVal);
        return forward(request, model, "/contents/SCMComplete.jsp");
    }


	public ActionForward SiCM(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String 	pCourseId 		= 	StringUtil.nvl(request.getParameter("pCourseId"));
		String 	pContentsOrder 	= StringUtil.nvl(request.getParameter("pContentsOrder"));
		String 	pKeyWord 	= StringUtil.nvl(request.getParameter("pKeyWord"));
		model.put("pKeyWord", pKeyWord);
		model.put("pCourseId", pCourseId);
		model.put("pContentsOrder", pContentsOrder);
        return forward(request, model, "/contents/SiCM.jsp");
    }


	public ActionForward SiCMInnerManifest(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode 		= 	UserBroker.getSystemCode(request);
		String 	pKeyWord 	= StringUtil.nvl(request.getParameter("pKeyWord"));
		ContentsDAO contentsDao	=	new ContentsDAO();
		model.put("rsManiList", contentsDao.getSiCMManiList(systemCode,pKeyWord));
        return forward(request, model, "/contents/SiCMInnerManifest.jsp");
    }
//	public ActionForward SiCMInnerItems(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
//		String strManifest=StringUtil.nvl(request.getParameter("maniID"));
//		DBConnectionManager pool = null;
//		Connection conn = null;
//		pool = DBConnectionManager.getInstance();
//		conn = pool.getConnection();
//
//		//DbJndiDataSource ds = new DbJndiDataSource();
//		//Connection conn = ds.getConnection();
//		ManifestSelectModule smodule=new ManifestSelectModule(conn);
//		Manifest mf=smodule.GetManifest(strManifest,true,false,false);
//		String retVal = "";
//		if(mf==null){
//			//out.println("failed get manifest)=");
//			retVal = "";
//		}else{
//			OrgBrowser ob=new OrgBrowser();
//			retVal = ob.GetHTMLCode(mf);
//			//out.println(ob.GetHTMLCode(mf));
//		}
//		//ds.freeConnection(conn);
//		pool.freeConnection(conn); // 컨넥션 해제
//		model.put("itemList",retVal);
//		return forward(request, model, "/contents/SiCMInnerItems.jsp");
//    }

	public ActionForward SiCMRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode 		= 	UserBroker.getSystemCode(request);
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

		ContentsDAO contentsDao = new ContentsDAO();
		ContentsDTO contentsDto = new ContentsDTO();
		contentsDto.setSystemCode(systemCode);
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
				pContentsOrder = contentsDao.getMakeContentsOrder(systemCode,pCourseId,"");
				tmpParentOrder = pContentsOrder;
				contentsDto.setContentsId(tmpOrgId);
				contentsDto.setContentsOrder(pContentsOrder);
				contentsDto.setContentsName(tmpOrgName);
				contentsDto.setContentsType(pContentsType);

				log.debug("++++"+pContentsType+"+++++"+pContentsOrder);
				retVal = contentsDao.addContents(contentsDto);

			}
			//-- 단원 등록 작업
			identifier = selectedItem[2];
			titleName = selectedItem[3];
			log.debug("chk tmpParentOrder ++++"+tmpParentOrder);
			pContentsType = "F";
			ParentOrder = getParentOrder(tmpParentOrder);
			pContentsOrder = contentsDao.getMakeContentsOrder(systemCode,pCourseId,ParentOrder);
			pShowTime 		= 	StringUtil.nvl(request.getParameter("showTime_"+identifier),0);
			contentsDto.setContentsId(identifier);
			contentsDto.setContentsOrder(pContentsOrder);
			contentsDto.setContentsName(titleName);
			contentsDto.setContentsType(pContentsType);
			contentsDto.setShowTime(pShowTime);
			log.debug("++++pShowTime+++++"+pShowTime);
			retVal = contentsDao.addContents(contentsDto);

		}
	    msg = "목차를 등록하였습니다.";
		log.debug("+++++++++"+strselectedItem);

		model.put("selectedItem", strselectedItem);
        return forward(request, model, "/contents/SiCMRegist.jsp");
    }

	/**
	 * 과목 교재관리에서 컨텐츠 미리보기 프레임
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

		viewUrl = "/contents/preViewContentsFrame.jsp";

		log.debug("================ ContentsType = "+ContentsType);
//		-- 컨텐츠 타입이 SCORM 형(S) 인경우
		if(ContentsType.equals("S")){
			log.debug("Scrom data");

			//-- 교재 정보를 가져온다.    cmi_core 셋팅용
			ContentsDAO contentsDao = new ContentsDAO();
			RowSet rs = contentsDao.getContents(systemCode, pCourseId, pContentsId);
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
	 * 과목 교재관리에서 컨텐츠 미리보기 show
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
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String regId = userMemDto.userId;
//	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
//	    int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
//	    int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String pContentsId = StringUtil.nvl(request.getParameter("pContentsId"));


		String contentsBase = "/data/";

		ContentsDAO contentsDao = new ContentsDAO();
		RowSet rs = contentsDao.getContents(systemCode, pCourseId, pContentsId);
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
		return forward(request, model, "/contents/preViewContentsShow.jsp");
	}

	/**
	 * 목차 정보 리스트페이지로 이동.(페이징 없음)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward contentsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));

		CourseDAO courseDao = new CourseDAO();
		RowSet rsCourse = courseDao.getCourseInfo(systemCode, pCourseId);
		rsCourse.next();
		model.put("pContentsType", StringUtil.nvl(rsCourse.getString("contents_type")));
		model.put("pCourseName",StringUtil.nvl(rsCourse.getString("course_name")));
		model.put("pFlagNavi",StringUtil.nvl(rsCourse.getString("flag_navi")));
		model.put("pContentsWidth",StringUtil.nvl(rsCourse.getString("contents_width")));
		model.put("pContentsHeight",StringUtil.nvl(rsCourse.getString("contents_height")));
		rsCourse.close();

		model.put("pCourseId",pCourseId);
		model.put("dateyn","Y");

		new SiteNavigation(pMode).add(request,"교재관리").link(model);
        return forward(request, model, "/contents/contentsList.jsp");
	}

	/**
	 * 컨텐츠리스트를 받아온다(ajax)
	 * sangsang 2007.05.18
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
//		 스콤 컨텐츠 미리보기를 위해 가상의 curriMemDto 생성 End

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
			//contentsDto.setLectureGubun(CommonUtil.getSoName(systemCode,"110",StringUtil.nvl(rs.getString("lecture_gubun"))));
			contentsDto.setLectureGubun(StringUtil.nvl(rs.getString("lecture_gubun")));

			arrayList.add(contentsDto);
		}
		rs.close();

		return arrayList;
	}

	/**
	 * 컨텐츠정보를 받아온다 (Ajax)
	 * 2007.04.26 sangsang
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	public ContentsDTO contentsInfo(String courseId, String contentsId, String workMode, String regContentsType, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		courseId = StringUtil.nvl(courseId);
		contentsId = StringUtil.nvl(contentsId);
		workMode = StringUtil.nvl(workMode);
		regContentsType = StringUtil.nvl(regContentsType);

		String contentsOrder="";
		String contentsType="";
		String parentContentsName = "";
		ContentsDAO contentsDao = new ContentsDAO();
		ContentsDTO contentsDto = null;
		RowSet rs = contentsDao.getContents(systemCode, courseId, contentsId);

		if(rs.next()){

			contentsDto = new ContentsDTO();

			contentsOrder = rs.getString("contents_order");
			contentsType = StringUtil.nvl(rs.getString("contents_type"));

			String parentOrder = "";
			if(contentsType.equals("C") && regContentsType.equals("F")){
				parentContentsName = contentsDao.getContentsNameByOrder(systemCode, courseId, contentsOrder);
			}else{
				parentOrder = getParentFullOrder(contentsOrder);
				if(!parentOrder.equals(""))
					parentContentsName = contentsDao.getContentsNameByOrder(systemCode, courseId, parentOrder);
			}

			contentsDto.setCourseId(StringUtil.nvl(rs.getString("course_id")));
			contentsDto.setContentsId(StringUtil.nvl(rs.getString("contents_id")));
			contentsDto.setContentsOrder(StringUtil.nvl(rs.getString("contents_order")));
			contentsDto.setContentsName(StringUtil.nvl(rs.getString("contents_name")));
			contentsDto.setFilePath(StringUtil.nvl(rs.getString("file_path")));
			contentsDto.setAsfFilePath(StringUtil.nvl(rs.getString("asffile_path")));
			contentsDto.setServerPath(StringUtil.nvl(rs.getString("server_path")));
			contentsDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
			contentsDto.setQuizCount(StringUtil.nvl(rs.getString("quiz_count"),0));
			contentsDto.setQuizPoint(StringUtil.nvl(rs.getString("quiz_point"),0));
			contentsDto.setShowTime(StringUtil.nvl(rs.getString("show_time"),0));
			contentsDto.setContentsWidth(StringUtil.nvl(rs.getString("contents_width"),0));
			contentsDto.setContentsHeight(StringUtil.nvl(rs.getString("contents_height"),0));
			contentsDto.setSizeApp(StringUtil.nvl(rs.getString("size_app"),"N"));

			contentsDto.setParentContentsName(parentContentsName);
			contentsDto.setRegContentsType(regContentsType);
			contentsDto.setWorkMode(workMode);

			contentsDto.setLectureGubun(StringUtil.nvl(rs.getString("lecture_gubun"),"1"));
			
		}
		rs.close();

		return contentsDto;
	}

	/**
	 * 컨텐츠정보 등록/수정/삭제 (Ajax)
	 * 2007.04.26 sangsang
	 * @param contentsDto
	 * @param regMode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String contentsRegist(ContentsDTO contentsDto, String regMode, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		regMode = StringUtil.nvl(regMode);

		String courseId = StringUtil.nvl(contentsDto.getCourseId());
		String contentsId = StringUtil.nvl(contentsDto.getContentsId());
		String contentsOrder = StringUtil.nvl(contentsDto.getContentsOrder());
		String contentsName = StringUtil.nvl(AjaxUtil.ajaxDecoding(contentsDto.getContentsName()));
		String contentsType = StringUtil.nvl(contentsDto.getContentsType());
		String serverPath 	= 	StringUtil.nvl(contentsDto.getServerPath());
		String sizeApp 	= 	StringUtil.nvl(contentsDto.getSizeApp(),"N");
		int showTime =	contentsDto.getShowTime();
		int contentsWidth =	contentsDto.getContentsWidth();
		int contentsHeight =	contentsDto.getContentsHeight();
		String lectureGubun = StringUtil.nvl(contentsDto.getLectureGubun());
		String filePath 	= StringUtil.nvl(contentsDto.getFilePath());
		String asfFilePath 	= StringUtil.nvl(contentsDto.getAsfFilePath());

		ContentsDAO contentsDao = new ContentsDAO();
		if(regMode.equals("ADD")){
			int totalContentsCnt = contentsDao.getTotalContentsCnt(systemCode,courseId);
			contentsId = CommonUtil.getCurrentDate();
			int cntLength = String.valueOf(totalContentsCnt).length();
			for(int i=0;i<(6-cntLength);i++){
				contentsId=contentsId+"0";
			}
			contentsId = contentsId+totalContentsCnt;
			String parentOrder = getParentOrder(contentsOrder);
			contentsOrder = contentsDao.getMakeContentsOrder(systemCode,courseId,parentOrder);
		}

		contentsDto.setSystemCode(systemCode);
		contentsDto.setCourseId(courseId);
		contentsDto.setContentsId(contentsId);
		contentsDto.setContentsOrder(contentsOrder);
		contentsDto.setContentsName(contentsName);
		contentsDto.setContentsType(contentsType);
		contentsDto.setSizeApp(sizeApp);
		contentsDto.setContentsWidth(contentsWidth);
		contentsDto.setContentsHeight(contentsHeight);
		contentsDto.setFilePath("");
		contentsDto.setAsfFilePath("");
		if(contentsType.equals("C")){
			contentsDto.setServerPath("");
			contentsDto.setFilePath("");
			contentsDto.setAsfFilePath("");
		}else{
			contentsDto.setServerPath(serverPath);
			contentsDto.setFilePath(filePath);
			contentsDto.setAsfFilePath(asfFilePath);
		}
		contentsDto.setShowTime(showTime);
		contentsDto.setLectureGubun(lectureGubun);
		

		int retVal = 0;
		if(regMode.equals("ADD")){	// 입력모드
			contentsDto.setRegId(userId);
			retVal = contentsDao.addContents(contentsDto);

		}else if(regMode.equals("EDIT")){	// 수정모드
			contentsDto.setModId(userId);
			retVal = contentsDao.editContents(contentsDto);

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

			retVal = contentsDao.delContents(systemCode,courseId,targetOrder);

		}

		return String.valueOf(retVal);
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


	public ActionForward previewContentsVideo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String	pFileUrl	=	StringUtil.nvl(request.getParameter("pFileUrl"));

		model.put("pFileUrl",pFileUrl);

		new SiteNavigation("HOME").add(request,"교재관리").link(model);
        return forward(request, model, "/contents/preViewContentsVideo.jsp");
	}

}


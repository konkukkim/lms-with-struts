package com.edutrack.user.action;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.VBN.VBN_files;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dao.CommonDAO;
import com.edutrack.common.dto.CodeDTO;
import com.edutrack.curritop.dao.CourseDAO;
import com.edutrack.curritop.dao.CurriCategoryDAO;
import com.edutrack.curritop.dao.CurriTopDAO;
import com.edutrack.curritop.dto.CourseDTO;
import com.edutrack.dept.dao.DeptDaeDAO;
import com.edutrack.dept.dao.DeptSoDAO;
import com.edutrack.dept.dto.DeptSoCodeDTO;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.edutrack.user.dao.UserAdminDAO;
import com.edutrack.user.dao.UserDAO;
import com.edutrack.user.dto.ProfInfoDTO;
import com.edutrack.user.dto.UsersDTO;
import com.oreilly.servlet.MultipartRequest;


public class UserAdminAction extends StrutsDispatchAction{
    protected static String[] errorName = new String[]{"사용자 아이디 충돌","주민등록번호 충돌","DB 등록실패","사용자구분 코드에러","계층구분 코드에러","DB 작업 시 오류발생"};

	public UserAdminAction() {
		super();
	}

	/**
	 * 사용자 등록 폼을 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward userWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode 	= UserBroker.getSystemCode(request);

		String pGUBUN		= StringUtil.nvl(request.getParameter("pGUBUN"),"write");		//-- 입력, 수정
		String userId		= StringUtil.nvl(request.getParameter("userId"));
		String userType 	= StringUtil.nvl(request.getParameter("userType"),"S");
		String firstCheck 	= StringUtil.nvl(request.getParameter("firstCheck"));
		String pDeptDaecode = StringUtil.nvl(request.getParameter("pDeptDaecode"));
		String pDeptSocode  = StringUtil.nvl(request.getParameter("pDeptSocode"));
		String bGroupMng    = "false";														//-- 단체관리자 여부

		String navigationMsg	= "";
		String navigationWhere	= HOME;

		UsersDTO userInfo = null;
		ProfInfoDTO profInfo = null;


		// 등록하기 시작
		if (pGUBUN.equals("write")) {
		    pDeptDaecode = ("").equals(pDeptDaecode) ? "100" : pDeptDaecode;
		    pDeptSocode  = ("").equals(pDeptSocode) ? "100" : pDeptSocode;

			navigationMsg = "회원가입";
            userInfo = new UsersDTO();

			userInfo.setDeptDaecode(pDeptDaecode);
			userInfo.setDeptSocode (pDeptSocode);

            if (userType.equals("P"))
				profInfo = new ProfInfoDTO();

			if (firstCheck.equals("")) {
				userInfo.setUserType(userType);
			} else {
				log.debug(pGUBUN);
            	new UserHelper().getUserParam(request, userInfo);

            	if (userType.equals("P")) {
            		new UserHelper().getProfParam(request, profInfo);
            		log.debug(profInfo.toString());
            	}
            }
		} else {
			navigationMsg = "회원정보수정";

			UserDAO userDao = new UserDAO();
			userInfo = userDao.getUserInfo(systemCode, userId);

			if(!pDeptDaecode.equals("")){userInfo.setDeptDaecode(pDeptDaecode);}
			if(!pDeptSocode.equals("")){userInfo.setDeptSocode (pDeptSocode);}

			// 단체관리자 여부
			DeptSoDAO deptSoDao = new DeptSoDAO();
			RowSet rs = deptSoDao.getDeptSoInfo( systemCode, userInfo.getDeptDaecode(), userInfo.getDeptSocode() );

			if (rs.next()) {
				bGroupMng = (StringUtil.nvl(rs.getString("user_id")).equals(userId) ?  "true" : "false" );
				rs.close();
			}

			if (firstCheck.equals("")) {
			    UserAdminDAO userAdminDao = new UserAdminDAO();

				if (userType.equals("P"))
					profInfo = userAdminDao.getProfInfo(systemCode, userId);
					navigationWhere = MYPAGE;
			} else {
				if (userType.equals("P")) {
					profInfo = new ProfInfoDTO();
					new UserHelper().getProfParam(request, profInfo);
				}
			}
		}

		new SiteNavigation(MYPAGE).add(request,navigationMsg).link(model);

		model.put("pGUBUN",		pGUBUN);
		model.put("userInfo",	userInfo);
		model.put("bGroupMng",	bGroupMng );

		if (userType.equals("P")){
			model.put("profInfo",	profInfo);
		}

		return forward(request, model,"/admin/users/users_regist_write.jsp");
	}

	/**
	 * ==================================================
	 * 					사용자를 등록
	 * --------------------------------------------------
	 * 작성일		: 2006. 06. 29
	 * ==================================================
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 * 수정자 : 2007.1.19 ---> ^^*BlackMan
	 */
	public int userJuminNoCheckAjax(String systemCode, String pResidentId1, String pResidentId2, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{

		String sJuminNo  = pResidentId1+"-"+pResidentId2 ;
		String returnMsg = "0";
		UserDAO userDAO = new UserDAO();
		// 주민등록번호가 중복이 있나를 검사한다.
		int check = 0;
		check = userDAO.juminNoYn(systemCode, sJuminNo);
		
		return check;
	}
	

	/**
	 * ==================================================
	 * 					사용자를 등록
	 * --------------------------------------------------
	 * 작성일		: 2006. 06. 29
	 * ==================================================
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 * 수정자 : 2007.1.19 ---> ^^*BlackMan
	 */
	public ActionForward userRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		UsersDTO userDto = new UsersDTO();
		String pGUBUN = request.getParameter("pGUBUN");

        userDto.setSystemCode(systemCode);
        userDto.setUseStatus(request.getParameter("pUseYn"));

		String identify = null;
		identify = DateTimeUtil.getTime();

		String FilePath = FileUtil.FILE_DIR+systemCode+"/users/"+identify+"/";
        new UserHelper().getUserParam(request, userDto);
		//-- MultipartRequest end
        UserDAO userDAO = new UserDAO();

		if (pGUBUN.equals("write")) {
			userDto.setRegId(userDto.getUserId());

			// 주민등록번호가 중복이 있나를 검사한다.
			int check = 0;
			check = userDAO.juminNoYn(systemCode, userDto.getJuminNo());

			if (check > 0)
				return alertAndExit(systemCode, model, "주민등록번호가 이미 사용중 입니다.", "/UserAdmin.cmd?cmd=userWrite&pGUBUN=write",MYPAGE);

			userDAO.addUsers(userDto);		// 사용자 정보를 저장한다.

		} else {
			userDto.setModId(UserBroker.getUserId(request));
			userDAO.editUsers(userDto);
		}

		return redirect("/UserAdmin.cmd?cmd=userProcRedirect&systemCode="+systemCode+"&pProc="+pGUBUN+"&succYn=Y&userType="+userDto.getUserType());
	}


	/**
	 * ==================================================
	 * 		사용자 등록, 수정 처리 후 메시지를 처리해 준다.
	 * --------------------------------------------------
	 * 작성일		: 2006. 06. 29
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
    public ActionForward userProcRedirect(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
	    String systemCode	= StringUtil.nvl(request.getParameter("systemCode"));
	    String pProc 		= StringUtil.nvl(request.getParameter("pProc"));
	    String succYn 		= StringUtil.nvl(request.getParameter("succYn"));
	    String userType 	= StringUtil.nvl(request.getParameter("userType"),"S");
	    String pWorker 		= StringUtil.nvl(request.getParameter("pWorker"));
		String targetId 	= StringUtil.nvl(request.getParameter("targetId"));

		String msg 			= "";
        String returnUrl	= "";

        if (pProc.equals("write")) {
        	msg = "사용자 등록에 성공하였습니다.";
        	returnUrl = "/UserAdmin.cmd?cmd=userPagingList&userType="+userType;
        } else {
        	msg = "사용자 정보수정에 성공하였습니다.";
        	returnUrl = "/UserAdmin.cmd?cmd=userPagingList&userType="+userType;
        }

		if (pWorker.equals("P"))
        	returnUrl = "/UserAdmin.cmd?cmd=profWrite&pGUBUN=edit&userId="+targetId+"&userType=P&pWorker=P&MENUNO=0";

		return notifyAndExit(systemCode,model, msg, returnUrl, MYPAGE);
    }


	/**
	 * 교수자 정보 관리
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward profWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String pGUBUN		= StringUtil.nvl(request.getParameter("pGUBUN"));
		String userId		= StringUtil.nvl(request.getParameter("userId"));
		String userType		= StringUtil.nvl(request.getParameter("userType"),"S");
        String firstCheck	= StringUtil.nvl(request.getParameter("firstCheck"));
        String pWorker		= StringUtil.nvl(request.getParameter("pWorker"));

		String navigationMsg	= "";
		String navigationWhere	= HOME;

		UsersDTO userInfo		= null;
		ProfInfoDTO profInfo	= null;

		if (pGUBUN.equals("write")){
			navigationMsg = "회원가입";
            userInfo = new UsersDTO();

			if (userType.equals("P"))
				profInfo = new ProfInfoDTO();

			if (firstCheck.equals("")){
				userInfo.setUserType(userType);
            } else {
            	log.debug(pGUBUN);
            	new UserHelper().getUserParam(request, userInfo);
            	userInfo.setDeptSocode("");

				if (userType.equals("P")) {
            		new UserHelper().getProfParam(request, profInfo);
            		log.debug(profInfo.toString());
            	}
            }
		} else {
			navigationMsg = "회원정보수정";

			if (firstCheck.equals(""))	{
			    UserDAO userDao = new UserDAO();
			    UserAdminDAO userAdminDao = new UserAdminDAO();
				String systemCode = UserBroker.getSystemCode(request);
				userInfo = userDao.getUserInfo(systemCode, userId);

				if (userType.equals("P"))
					profInfo = userAdminDao.getProfInfo(systemCode, userId);

				navigationWhere = MYPAGE;
			} else {
				userInfo = new UsersDTO();
				new UserHelper().getUserParam(request, userInfo);
				userInfo.setDeptSocode("");

				if (userType.equals("P")) {
					profInfo = new ProfInfoDTO();
					new UserHelper().getProfParam(request, profInfo);
				}
			}
		}

		new SiteNavigation(MYPAGE).add(request,navigationMsg).link(model);

		model.put("pGUBUN",		pGUBUN);
		model.put("userInfo", 	userInfo);
		model.put("pWorker", 	pWorker);

		if (userType.equals("P")){
			model.put("profInfo",	profInfo);
		}

		return forward(request, model,"/admin/users/prof_regist_write.jsp");
	}


	public ActionForward profRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode	= UserBroker.getSystemCode(request);
		String userId		= UserBroker.getUserId(request);

		MultipartRequest multipartRequest = null;

		String dir = FileUtil.IMG_DIR+systemCode+"/picture";

		UploadFiles uploadEntity	= FileUtil.upload(request, dir,"picture");
		multipartRequest			= uploadEntity.getMultipart();

		String pIntro = StringUtil.nvl(multipartRequest.getParameter("pIntro"));

		String pGUBUN = multipartRequest.getParameter("pGUBUN");
		String status = uploadEntity.getStatus();
        String picture = "";

		if (status.equals("S")) {
			//-- 업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			ArrayList files		=	uploadEntity.getFiles();
			UploadFile file		=	null;

			if (files.size() > 0) {
				file = (UploadFile)files.get(0);

				if (file.getObjName().equals("pUserPicture"))
					picture = file.getUploadName();
			}
		}

		log.debug("=======>pIntro:" + pIntro);
		/** 웹에디터 셋팅 추가 Start**/
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH+dir+"/";
		String WeasFileUrl =  ServiceUrl+"/data/sys_img/"+systemCode+"/picture/";

		VBN_files v_objFile = null;

		//java.io.File dirs = new java.io.File(WeasFilePath);
		//if (!dirs.exists())	dirs.mkdirs();

		v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
		pIntro = v_objFile.VBN_uploadMultiFiles(pIntro, multipartRequest);
		/** 웹에디터 셋팅 추가 End**/
		UsersDTO userDto = new UsersDTO();
		ProfInfoDTO profDto = new ProfInfoDTO();

        new UserHelper().getUserMultiParam(multipartRequest, userDto);
        new UserHelper().getProfMultiParam(multipartRequest, profDto);

        profDto.setIntro(pIntro);

        String pWorker = StringUtil.nvl(multipartRequest.getParameter("pWorker"));

        // 사용자 정보에 기본정보를 셋팅한다.
       // userDto.setSystemCode(systemCode);
		profDto.setSystemCode(systemCode);
		if(!picture.equals("")) FileUtil.delFile(dir, profDto.getOldUserPhoto());
        if(picture.equals("")) picture = profDto.getOldUserPhoto();


        log.debug("Path ==>"+dir);

        profDto.setPhotoPath(dir);
        profDto.setUserPhoto(picture);

		UserAdminDAO adminDao = new UserAdminDAO();


		profDto.setModId(userId);

		if(adminDao.existProfInfo(systemCode, profDto.getUserId()) > 0)
			adminDao.editProfInfo(profDto);

		else adminDao.addProfInfo(profDto);

		String targetId = profDto.getUserId();
		return redirect("/UserAdmin.cmd?cmd=userProcRedirect&systemCode="+systemCode+"&pProc="+pGUBUN+"&pWorker="+pWorker+"&targetId="+targetId+"&succYn=Y&userType="+userDto.getUserType());

	}


	public ActionForward userMultiRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		MultipartRequest multipartRequest = null;

		String dir = FileUtil.IMG_DIR+systemCode+"/picture";

		UploadFiles uploadEntity = FileUtil.upload(request, dir,"picture");
		multipartRequest	= uploadEntity.getMultipart();
		String pGUBUN       = multipartRequest.getParameter("pGUBUN");
		String pIntro = StringUtil.nvl(multipartRequest.getParameter("pIntro"));

		String status = uploadEntity.getStatus();
        String picture = "";
		if(status.equals("S")){
			//--	업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			ArrayList files		=	uploadEntity.getFiles();
			UploadFile file		=	null;
			if(files.size() > 0){
				file			=	(UploadFile)files.get(0);
				picture		=	file.getUploadName();
			}
		}

		/** 웹에디터 셋팅 추가 Start**/
		String ServiceUrl = CommonUtil.SERVERURL;
		String WeasFilePath = FileUtil.UPLOAD_PATH+dir+"/";
		String WeasFileUrl =  ServiceUrl+"/data/sys_img/"+systemCode+"/picture/";

		VBN_files v_objFile = null;

		v_objFile = new VBN_files(WeasFilePath, WeasFileUrl);
		pIntro = v_objFile.VBN_uploadMultiFiles(pIntro, multipartRequest);
		/** 웹에디터 셋팅 추가 End**/

		UsersDTO userDto = new UsersDTO();
		ProfInfoDTO profDto = new ProfInfoDTO();
		DeptSoCodeDTO deptSoCodeDto = new DeptSoCodeDTO();

        new UserHelper().getUserMultiParam(multipartRequest, userDto);		// 유저정보
        new UserHelper().getProfMultiParam(multipartRequest, profDto);		// 교수자정보

        // 사용자 정보에 기본정보를 셋팅한다.
        userDto.setSystemCode(systemCode);
		profDto.setSystemCode(systemCode);

        if(picture.equals("")) picture = profDto.getOldUserPhoto();


        log.debug("Path ==>"+dir);

        profDto.setPhotoPath(dir);
        profDto.setUserPhoto(picture);

		UserDAO userDao = new UserDAO();
		UserAdminDAO adminDao = new UserAdminDAO();

		if(pGUBUN.equals("write")){

			userDto.setRegId(userId);
			profDto.setRegId(userId);

			// 주민등록번호가 중복이 있나를 검사한다.
			int check = 0;
			check = userDao.juminNoYn(systemCode, userDto.getJuminNo());
			if(check > 0) {
				// 실패 시 업로드 파일 삭제
				FileUtil.delFile(dir, picture);
				return alertAndExit(systemCode, model, "주민등록번호가 이미 사용중 입니다.", "/UserAdmin.cmd?cmd=userWrite",HOME);
			}

			userDao.addUsers(userDto);	// 사용자 정보를 저장한다.

			adminDao.addProfInfo(profDto);

			// 새로운 사진이 올라왔을 경우 이전 파일을 삭제해준다.
			if(!picture.equals("")) FileUtil.delFile(dir, profDto.getOldUserPhoto());
		}else{
			userDto.setModId(userId);
			profDto.setModId(userId);

			userDao.editUsers(userDto);

			if(adminDao.existProfInfo(systemCode, profDto.getUserId()) > 0) adminDao.editProfInfo(profDto);
			else adminDao.addProfInfo(profDto);

		}
		return redirect("/UserAdmin.cmd?cmd=userProcRedirect&systemCode="+systemCode+"&pProc="+pGUBUN+"&succYn=Y&userType="+userDto.getUserType());
	}


	/**
	 * 사용자에게 쪽지, 메일, 엑셀다운로드등의 서비스를 해준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward userService(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String service = StringUtil.nvl(request.getParameter("service"));
		ArrayList userList = null;
		// 선택된 사용자에게 메일, 쪽지 보내기 - 리스트에서 선택한 사용자 일경우
	    if(service.equals("mail") || service.equals("message")){
	    	String[] users = request.getParameterValues("userBox");
	    	for(int i =0; i < users.length; i++){
	    		if(service.equals("mail")){
	    			// 메일 전송
	    		}else{
	    			// 쪽지 전송
	    		}
	    	}
	    }

	    if(service.equals("allmail") || service.equals("allmessage") || service.equals("excel")){
	    	// 사용자 리스트 엑셀 다운 받기 - 검색된 사용자 전체 대상
			// 전체 사용자에게 메일, 쪽지 보내기 - 검색된 사용자 전체 대상
	    	UserDAO userDao = new UserDAO();
	    	String userType = StringUtil.nvl(request.getParameter("userType"));
			String deptDaeCode = StringUtil.nvl(request.getParameter("deptDaeCode"));
			String deptSoCode = StringUtil.nvl(request.getParameter("deptSoCode"));
			String pFields = StringUtil.nvl(request.getParameter("pFields"));
			String pValue = StringUtil.nvl(request.getParameter("pValue"));

			userList = userDao.userList(systemCode, userType, deptDaeCode, deptSoCode,pFields,pValue);
	        if(!service.equals("excel")){
				for(int i=0; i < userList.size(); i++){
		    		if(service.equals("mail")){
		    			// 메일 전송
		    		}else{
		    			// 쪽지 전송
		    		}
	            }
	        } else {
	        	model.put("userList", userList);
	        }
	    }

		if(service.equals("excel")) return excelDown(request,httpServletResponse, model, "/admin/users/users_excel_list.jsp","junnodaeMembersList.xsl");
		else return redirect("/User.cmd?cmd=userProcRedirect&systemCode=");
	}


	/**
	 * 사용자의 사용 상태를 변경한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeUserStatus(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = StringUtil.nvl(request.getParameter("userId"));
		String userName = StringUtil.inDataConverter(request.getParameter("userName"));
		String userType = StringUtil.nvl(request.getParameter("userType"));
		String useStatus = StringUtil.nvl(request.getParameter("useStatus"));
		String email = StringUtil.nvl(request.getParameter("email"));
		String modId = UserBroker.getUserId(request);
		String msg = userName + " 회원님의 상태를 ";

		if(useStatus.equals("Y")) {
			useStatus = "N";
		    msg += "미사용 상태로 변경하였습니다.";
		}else {
			useStatus = "Y";
			msg += "사용 상태로 변경하였습니다.";
		}

		int curPage = 1;
		if(request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));

		UserAdminDAO userDao = new UserAdminDAO();
		if(userDao.changeUserStatus(systemCode,userId, useStatus, modId) < 1) msg = "사용자의 상태를 변경하는데 실패하였습니다.";
		else CommonUtil.defaultMailSend("회원님의 상태를 변경하였습니다.",email,userName,msg);

		return notifyAndExit(systemCode,model, msg, "/UserAdmin.cmd?cmd=userPagingList&userType="+userType+"&curPage="+curPage ,MYPAGE);
	}


	public ActionForward batchUserWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
        String userType = StringUtil.nvl(request.getParameter("userType"));
		model.put("userType", userType);
		return forward(request, model, "/admin/users/users_batch_write.jsp");
	}


	public ActionForward batchUserRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		MultipartRequest multipartRequest = null;

		String dir = FileUtil.FILE_DIR+systemCode+"/userbatchdata";

		UploadFiles uploadEntity = FileUtil.upload(request, dir,"user");
		multipartRequest		=	uploadEntity.getMultipart();

		String userType = StringUtil.nvl(multipartRequest.getParameter("userType"));

		String status = uploadEntity.getStatus();
        String batchFile = "";
		if(status.equals("S")){
			//--	업로드된 파일의 정보를 가져와서 데이터 베이스에 넣는 작업을 해준다.
			ArrayList files		=	uploadEntity.getFiles();
			UploadFile file		=	null;
			if(files.size() > 0){
				file			=	(UploadFile)files.get(0);
				batchFile		=	file.getUploadName();
				if(!batchFile.equals("")) batchFile = FileUtil.UPLOAD_PATH+dir + "/" + batchFile;
			}
		}

		// FileInputStream
		BufferedReader bufr = null;
		String line = null;
		//
		StringUtils csvUtil  = new StringUtils();
	    ArrayList userList   = new ArrayList();
	    ArrayList failList   = new ArrayList();
	    UserHelper helper    = new UserHelper();
	    int lineCnt =0;

		try {
			// 파일을 읽어들인다.
			bufr = new BufferedReader(new FileReader(batchFile));
			while ((line = bufr.readLine()) != null) {
		        // 헤더부분을 뛰어넘기위한 체크값
				lineCnt++;
				if(lineCnt == 1) continue;
				// 파서 오브젝트를 생성하고 데이터를 셋팅하여 객체를 가져온다.

			    helper.parseUserInfo(systemCode, userId, userType, lineCnt,  line, userList, failList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		   return alertAndExit(systemCode, model, "사용자 일괄등록 작업에 실패하였습니다.", "/UserAdmin.cmd?cmd=batchUserWrite&userType="+userType,MYPAGE);
		}finally{
			// 사용한 파일을 삭제한다.
			FileUtil.delFile(batchFile);
		}

		UserDAO userDao = new UserDAO();
		DeptDaeDAO daeDao = new DeptDaeDAO();
		DeptSoDAO soDao = new DeptSoDAO();

		UsersDTO user = null;

		int errorNum = -1;
		int succCnt = 0;
		for(int i =0; i < userList.size(); i++){
		    try{
		    	user = (UsersDTO)userList.get(i);

		    	// 사용자 아이디 존재 유무 체크
		    	if(userDao.userIdCheck(systemCode, user.getUserId()) > 0) {
		    		errorNum = 0;
		    	}

		    	// 주민등록 번호가 존재하는지 체크
		    	if(errorNum < 0 && userDao.juminNoYn(systemCode, user.getUserId()) > 0) {
		    		errorNum = 1;
		    	}

		    	// 사용자구분에 대한 검증
                if(errorNum < 0 && daeDao.chkDaecode(systemCode, user.getDeptDaecode()) < 1){
                	errorNum = 3;
                }

                // 소속코에 대한 검증
                if(errorNum < 0 && soDao.chkSocode(systemCode,user.getDeptDaecode(),user.getDeptSocode()) < 1){
                	errorNum = 4;
                }

		    	// 사용자 등록 작업
		    	if(errorNum < 0 && userDao.addUsers(user) < 1){
		    		errorNum = 2;
		    	}

		 	} catch (Exception e) {
			   // 에러 발생
				errorNum = 5;
			}

		 	if(errorNum > -1){
		        ParseParam param = new ParseParam();
				param.setLineNo(user.getLineNo());
				param.setErrorType(StringUtil.outDataConverter(errorName[errorNum]));
				param.setUserId(user.getUserId());

				failList.add(param);
		 	}else succCnt += 1;
	    }

		model.put("userType",userType);
		model.put("result", failList);
		model.put("jobCnt", ""+(lineCnt-1));
		model.put("succCnt",""+succCnt);
		model.put("errorCnt",""+failList.size());

		return forward(request, model, "/admin/users/users_batch_result.jsp");
	}


	/**
	 * 사용자 타입변경
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward userTypeAddForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String userType = StringUtil.nvl(request.getParameter("userType"));
		model.put("userType",userType);

		return forward(request, model,"/admin/users/users_type_add_form.jsp");
	}


	public ActionForward userTypeAddRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String pUserType = StringUtil.nvl(request.getParameter("pUserType"));
		String pId = StringUtil.nvl(request.getParameter("pId"));
		UserAdminDAO userAdminDao = new UserAdminDAO();

		int retVal = 0;
		String msg = "사용자 권한 변경 하였습니다.";
		retVal = userAdminDao.setUserType(systemCode, pId, pUserType, userId);
		return notifyCloseReload(systemCode, model, msg, "");
	}

	/**
	 * 사용자 페이지 리스트를 가져온다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward userPagingList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		model.put("userType",StringUtil.nvl(request.getParameter("userType")));

		new SiteNavigation(pMode).add(request,"사용자관리").link(model);
		return forward(request, model, "/admin/users/users_list.jsp");
	}

	/**
	 * 소속분류 리스트를 셀렉트 박스로 보내준다..(ajax)
	 * 2007.03.16 sangsang
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	public void deptSoCodeSelectListAuto(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);

		String deptDaeCode = StringUtil.nvl(request.getParameter("deptDaeCode"));
		String strForm = StringUtil.nvl(request.getParameter("f"));
		String strElem = StringUtil.nvl(request.getParameter("e"));

  	    response.setContentType("application/xml");

		CommonDAO common = new CommonDAO();
	   	ArrayList codeList = null;
	   	CodeDTO code = null;
	   	codeList = common.getDeptSoCode(systemCode,deptDaeCode);

		StringBuffer strXML = new StringBuffer();
		strXML.append("<?xml version=\"1.0\" encoding=\"euc-kr\" ?>");
		strXML.append("<dataSet>");
		strXML.append("<selectValue>");
		strXML.append("<formName>"+strForm+"</formName>");
		strXML.append("<formElem>"+strElem+"</formElem>");
		strXML.append("</selectValue>");
		strXML.append("<entry>");
		strXML.append("<optionText>--소속분류--</optionText>");
		strXML.append("<optionValue><![CDATA[]]></optionValue>");
		strXML.append("</entry>");
		for(int i=0; i<codeList.size();i++){
	    	code = (CodeDTO)codeList.get(i);

			strXML.append("<entry>");
			strXML.append("<optionText>"+code.getName()+"</optionText>");
			strXML.append("<optionValue>"+code.getCode()+"</optionValue>");
			strXML.append("</entry>");
		}
		strXML.append("</dataSet>");
		response.getWriter().print(strXML.toString());
	}


	/**
	 * 대코드 리스트를 셀렉트 박스로 보내준다..(ajax)
	 * 2007.05.16 sangsang
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map deptDaeSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);

		Map map = new TreeMap();
		DeptDaeDAO deptDaeDao = new DeptDaeDAO();
		RowSet rs = deptDaeDao.getDeptDaeList(systemCode);
		while(rs.next()){
			map.put(StringUtil.nvl(rs.getString("dept_daecode")),StringUtil.nvl(rs.getString("dept_daename")));
		}

		rs.close();
		return map;
	}

	/**
	 * 소코드 리스트를 셀렉트 박스로 보내준다..(ajax)
	 * 2007.05.16 sangsang
	 * @param deptDaeCode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map deptSoSelectList(String deptDaeCode, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);

		deptDaeCode = StringUtil.nvl(deptDaeCode);
		DeptSoDAO deptSoDao = new DeptSoDAO();
		Map map = new TreeMap();
		RowSet rs = deptSoDao.getDeptSoList(systemCode, deptDaeCode);
		while(rs.next()){
			map.put(StringUtil.nvl(rs.getString("dept_socode")),StringUtil.nvl(rs.getString("dept_soname")));
		}
		//map.size();
		
		rs.close();
		return map;
	}

	public AjaxListDTO userPagingListAuto(int curPage, String fields, String value, String userType, String deptDaeCode, String deptSoCode, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		// sorting
		userType = StringUtil.nvl(userType);
		fields = StringUtil.nvl(fields);
		value = AjaxUtil.ajaxDecoding(StringUtil.nvl(value));

		// 페이징
		curPage = (curPage == 0) ? 1 : curPage;

		// 데이타를 담는다.
		ListDTO listObj = null;
		UserAdminDAO userDao = new UserAdminDAO();
		listObj = userDao.userPagingList(curPage,systemCode, userType, deptDaeCode, deptSoCode, fields, value);

		// 리턴 데이타 세팅
		AjaxListDTO ajaxListDto = new AjaxListDTO();
		ArrayList dataList = new ArrayList();
		UsersDTO usersDto = null;

		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				usersDto = new UsersDTO();
				usersDto.setUserId(StringUtil.nvl(rs.getString("user_id")));
				usersDto.setUserName(StringUtil.nvl(rs.getString("user_name")));
				usersDto.setDeptSoname(StringUtil.nvl(rs.getString("dept_soname")));
				usersDto.setEmail(StringUtil.nvl(rs.getString("email")));
				usersDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

				dataList.add(usersDto);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}
		return ajaxListDto;
	}

}

package com.edutrack.user.action;


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
import com.edutrack.dept.dao.DeptDaeDAO;
import com.edutrack.dept.dao.DeptSoDAO;
import com.edutrack.dept.dto.DeptDaeCodeDTO;
import com.edutrack.dept.dto.DeptSoCodeDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.MailUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.framework.util.UploadFile;
import com.edutrack.framework.util.UploadFiles;
import com.edutrack.user.dao.UserDAO;
import com.edutrack.user.dto.UsersDTO;
import com.oreilly.servlet.MultipartRequest;


public class UserAction extends StrutsDispatchAction{

	/**
	 *
	 */
	public UserAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 사용자 등록 약관 페이지를 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward userAgreeShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
        String PMODE = StringUtil.nvl(request.getParameter("pMode"));

	    model.put("PMODE", PMODE);

		new SiteNavigation(MEMBER).add(request,"약관동의").link(model);

		return forward(request, model, "/users/users_regist_info.jsp");
	}


	/**
	 *
	 * 사용자 정보 등록 & 수정 폼
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward userWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String pGUBUN = StringUtil.nvl(request.getParameter("pGUBUN"));
		String navigationMsg = "";
		String navigationWhere = HOME;
		UsersDTO userInfo = null;
		DeptSoCodeDTO  deptSoCode	= new DeptSoCodeDTO();
		DeptDaeCodeDTO deptDaeCode	= new DeptDaeCodeDTO();

		if(pGUBUN.equals("write")){
			navigationMsg = "입학신청";
			userInfo = new UsersDTO();
			userInfo.setUserType("S");
			userInfo.setDeptDaecode("100");
			userInfo.setDeptSocode ("100"); // Add by HerSunJa. Date: 2005.08.01

		} else {
			navigationMsg = "개인정보 ";
			UserDAO userDao     	= new UserDAO();
			DeptSoDAO deptSoDAO  	= new DeptSoDAO();
			DeptDaeDAO deptDaeDAO	= new DeptDaeDAO();

			RowSet rs_So  = null ;
			RowSet rs_Dae = null ;

			String systemCode = UserBroker.getSystemCode(request);
			String userId     = UserBroker.getUserId(request);

			userInfo      = userDao.getUserInfo(systemCode, userId);

			// 단체 관리자일경우
			// Add Date : 2005.08.02
			// Add : Her SunJa
			rs_So    = deptSoDAO.getDeptSoInfo(systemCode, userInfo.getDeptDaecode(), userInfo.getDeptSocode() );
			rs_Dae   = deptDaeDAO.getDeptDaeInfo(systemCode, userInfo.getDeptDaecode()  );

			if (rs_So.next()){
			    deptSoCode.setDeptDaecode(StringUtil.nvl(userInfo.getDeptDaecode() ));
			    deptSoCode.setDeptSocode (StringUtil.nvl(userInfo.getDeptSocode () ));
			    deptSoCode.setDeptSoname (StringUtil.nvl(rs_So.getString("dept_soname")));
			    deptSoCode.setUserId     (StringUtil.nvl(rs_So.getString("user_id"    )));
			    deptSoCode.setPostCode   (StringUtil.nvl(rs_So.getString("post_code"  )));
			    deptSoCode.setAddress    (StringUtil.nvl(rs_So.getString("address"    )));
			    deptSoCode.setPhone      (StringUtil.nvl(rs_So.getString("phone"      )));
			    deptSoCode.setPosition   (StringUtil.nvl(rs_So.getString("position"   )));
				// dept_socode 테이블에 데이터 입력
				deptSoCode.setCompNo	 (StringUtil.nvl(rs_So.getString("comp_no")));
			}
			if(rs_Dae.next()) {
			    deptDaeCode.setDeptDaename(StringUtil.nvl(rs_Dae.getString("dept_daename") ));
			}
			navigationWhere = MYPAGE;
		}

		new SiteNavigation(navigationWhere).add(request,navigationMsg).link(model);

		model.put("pGUBUN",			pGUBUN );
		model.put("userInfo"  , 	userInfo);
		model.put("deptSoCode", 	deptSoCode);
		model.put("deptDaeCode",	deptDaeCode);
		
		return forward(request, model,"/users/users_regist_write.jsp");
	}


	/**
	 * ==================================================
	 * 					사용자 등록
	 * --------------------------------------------------
	 * 프로젝트명 : 한국디지안진흥원 (KIDP)
	 * 작성일		: 2006. 06. 08
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
	public ActionForward userRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);

		UsersDTO      userDto       = new UsersDTO();
		DeptSoCodeDTO deptSoCodeDto = new DeptSoCodeDTO();

		int retVal1 = 0;
		int retVal2 = 0;

        userDto.setSystemCode(systemCode);
//      new UserHelper().getUserParam(request, userDto);

		MultipartRequest multipartRequest = null;
		String objName = "";
		String rFileName = "";
		String sFileName = "";
		String filePath = "";
		String fileSize = "";

		String identify = null;
		identify = DateTimeUtil.getTime();

		String FilePath = FileUtil.FILE_DIR+systemCode+"/users/"+identify+"/";


		//  파일 업로드 수행
		UploadFiles uploadEntity = FileUtil.upload(request, FilePath, identify);

		// 파라미터를 빼온다.
		multipartRequest	= uploadEntity.getMultipart();
		String status		= uploadEntity.getStatus();

		new UserHelper().getUserMultiParam(multipartRequest, userDto);

		String pGUBUN       = multipartRequest.getParameter("pGUBUN");
		String pMemberGb    = multipartRequest.getParameter("pMemberGb");
		String deptSoCode   = multipartRequest.getParameter("pDeptSocode");

		UserDAO   userDAO   = new UserDAO();
		DeptSoDAO deptSoDAO = new DeptSoDAO();

//		String pFile[] = new String[4];
		String pOldrFileName[] = new String[4];
		String pOldsFileName[] = new String[4];
		String pOldFilePath[] = new String[4];
		String pOldFileSize[] = new String[4];

		for(int i=1; i<=1; i++){
			//pFile[i] = StringUtil.nvl(multipartRequest.getParameter("pFile["+i+"]"));
			pOldrFileName[i] = StringUtil.nvl(multipartRequest.getParameter("pOldrFileName["+i+"]"));
			pOldsFileName[i] = StringUtil.nvl(multipartRequest.getParameter("pOldsFileName["+i+"]"));
			pOldFilePath[i] = StringUtil.nvl(multipartRequest.getParameter("pOldFilePath["+i+"]"));
			pOldFileSize[i] = StringUtil.nvl(multipartRequest.getParameter("pOldFileSize["+i+"]"));
		}

		userDto.setRfileName(pOldrFileName[1]);		userDto.setSfileName(pOldsFileName[1]);
		userDto.setFilePath(pOldFileSize[1]);		userDto.setFileSize(pOldFileSize[1]);

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
				if(objName.indexOf("pFile[")>=0){
					int idx = Integer.parseInt(String.valueOf(objName.charAt(6)));
					log.debug("++++++++++++++++ idx = "+idx);

					if(idx == 1){
						userDto.setRfileName(rFileName);	userDto.setSfileName(sFileName);
						userDto.setFilePath(filePath);		userDto.setFileSize(fileSize);
					}

					if(!pOldsFileName[idx].equals("")) {		//이전 첨부파일을 삭제할 경우
						log.debug("이전 파일 삭제하기"+pOldFilePath[idx]+pOldsFileName[idx]);
						FileUtil.delFile(pOldFilePath[idx], pOldsFileName[idx]);
					}
				}
			}// For End
		}

		log.debug("pMemberGb===>" + pMemberGb);


		// 단체관리자일경우
		if( ("2").equals(pMemberGb) ) {

		    if(pGUBUN.equals("write")){
		        deptSoCode = deptSoDAO.maxSocode(systemCode, userDto.getDeptDaecode() ) ;
		    }

            userDto.setDeptSocode (StringUtil.nvl(deptSoCode )); // users table 에 담기 위해.

		    deptSoCodeDto.setSystemCode (StringUtil.nvl(systemCode));
		    deptSoCodeDto.setDeptDaecode(StringUtil.nvl(userDto.getDeptDaecode()));
		    deptSoCodeDto.setDeptSocode (StringUtil.nvl(deptSoCode));
		    deptSoCodeDto.setDeptSoname (StringUtil.nvl(multipartRequest.getParameter("pDeptSoname")));
		    deptSoCodeDto.setUseYn      ("N");
		    deptSoCodeDto.setUserId     (StringUtil.nvl(multipartRequest.getParameter("pUserId")));
		    deptSoCodeDto.setPostCode   (StringUtil.nvl(multipartRequest.getParameter("pGroupPost")));
		    deptSoCodeDto.setAddress    (StringUtil.nvl(multipartRequest.getParameter("pGroupAddress")));
		    deptSoCodeDto.setPhone      (StringUtil.nvl(multipartRequest.getParameter("pGroupPhone")));
		    deptSoCodeDto.setPosition   (StringUtil.nvl(multipartRequest.getParameter("pGroupPosition")));

			deptSoCodeDto.setCompNo		(StringUtil.nvl(multipartRequest.getParameter("pCompNo")));

			deptSoCodeDto.setRegId      (StringUtil.nvl(userDto.getUserId()));
		    deptSoCodeDto.setRegDate    (StringUtil.nvl(userDto.getRegDate()));
		    deptSoCodeDto.setModId      (StringUtil.nvl(userDto.getModId()));
		    deptSoCodeDto.setModDate    (StringUtil.nvl(userDto.getModDate()));

		}

		String msg			= "";
        String returnUrl	= "";
	    String where		= "";

		if(pGUBUN.equals("write")){ //- 가입시
			userDto.setRegId(userDto.getUserId());

			// 주민등록번호가 중복이 있나를 검사한다.
			int check = 0;
			check = userDAO.juminNoYn(systemCode, userDto.getJuminNo());

			if(check > 0)
				return alertAndExit(systemCode, model, "주민등록번호가 이미 사용중 입니다.", "/User.cmd?cmd=userWrite&pGUBUN=write",HOME);

			String strDeptAdmin="&nbsp;";

			// 단체관리자일경우
			if( ("2").equals(pMemberGb) ) {
			    retVal1 = deptSoDAO.addDeptSoInfo(deptSoCodeDto);
			    strDeptAdmin="※ 회원님은 단체 관리자로 가입 하셨기에 관리자 승인 후 사이트 이용이 가능합니다.";
			}

			try{
			    retVal2 = userDAO.addUsers(userDto);	// 사용자 정보를 저장한다.
			}catch(Exception e){
				throw e;
			}

			model.put("pMemberGb",	pMemberGb);	//-- 1 : 개인, 2: 단체 관리자
        	model.put("pUserId",	userDto.getUserId());
        	model.put("pUserName",	userDto.getUserName());
        	model.put("pUserPass",	userDto.getPasswd());
        	model.put("pUserEMail",	userDto.getEmail());
        	model.put("pRecvSms",	userDto.getRecvSms());
        	model.put("pPhoneHand",	userDto.getPhoneMobile());
        	
        	new SiteNavigation(HOME).add(request,"입학신청결과").link(model);
			return forward(request, model, "/users/users_regist_result.jsp");

		}else{//-- 정보 수정시
			userDto.setModId(UserBroker.getUserId(request));

			// 단체관리자일경우
			if( ("2").equals(pMemberGb) ) {
			    deptSoDAO.editDeptSoInfo(deptSoCodeDto);
			}

			userDAO.editUsers(userDto);

			msg = "사용자 정보수정에 성공하였습니다.";
        	returnUrl = "/Main.cmd?cmd=mainShow&pMode="+MYPAGE;
        	where = MYPAGE;
        	return notifyAndExit(systemCode,model, msg, returnUrl,where);
		}
	}


	/**
	 * 사용자를 회원 탈퇴를 시켜 준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward userDrop(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pGUBUN       = request.getParameter("pGUBUN");
		String pMemberGb    = request.getParameter("pMemberGb");
		String deptSoCode   = request.getParameter("pDeptSocode");


		UsersDTO      userDto       = new UsersDTO();
		DeptSoCodeDTO deptSoCodeDto = new DeptSoCodeDTO();

		int retVal = 0;
        userDto.setSystemCode(systemCode);
		userDto.setModId(UserBroker.getUserId(request));


		new UserHelper().getUserParam(request, userDto);
		String pJuminNo   = userDto.getJuminNo().substring(0,8);
		pJuminNo = pJuminNo+"000000";
		userDto.setJuminNo(pJuminNo);

		UserDAO   userDAO   = new UserDAO();
		DeptSoDAO deptSoDAO = new DeptSoDAO();

		if( ("2").equals(pMemberGb) ) {

		    deptSoCodeDto.setSystemCode (StringUtil.nvl(systemCode   ));
		    deptSoCodeDto.setDeptDaecode(StringUtil.nvl(userDto.getDeptDaecode() ));
		    deptSoCodeDto.setDeptSocode (StringUtil.nvl(deptSoCode ));

		}
		retVal = userDAO.dropUsers(userDto);	// 사용자 정보를 삭제 한다.

		// 단체관리자일경우
		if( ("2").equals(pMemberGb) ) {
		    deptSoDAO.dropDeptSoInfo(deptSoCodeDto);		// 단체관리자 일경우 소속소코드에서도 데이타를 지운
		}

		return redirect("/Main.cmd?cmd=userLogOff&systemCode="+systemCode+"&pProc="+pGUBUN+"&succYn=Y");
	}


	/**
	 * 사용자의 아이디 사용가능 여부 체크
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
    public ActionForward userIdCheck(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
	    String pGUBUN= StringUtil.nvl(request.getParameter("pGUBUN"));
	    String pNEW_ID = StringUtil.nvl(request.getParameter("pNEW_ID"));

	    model.put("pGUBUN",pGUBUN);
	    model.put("pNEW_ID",pNEW_ID);

	    log.info("pGUBUN"+pGUBUN);
	    log.info("pNEW_ID"+pNEW_ID);

	    if(!pNEW_ID.equals("")){
		     UserDAO userDao = new UserDAO();

		     if(userDao.userIdCheck(UserBroker.getSystemCode(request), pNEW_ID) > 0) model.put("idCnt", "1");
		     else  model.put("idCnt", "0");
	    }else model.put("idCnt", "-1");

	    return forward(request, model, "/users/users_id_check.jsp");
    }

    /**
     * 사용자의 ID나 Password를 검색할 수 있는 폼을 띄워준다.
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
    public ActionForward searchIdPwShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

    	new SiteNavigation(HOME).add(request,"아이디/비밀번호 찾기").link(model);

	    return forward(request, model, "/users/users_idpw_search.jsp");
    }

    /**
     * 사용자의 ID나 Password를 찾아서 메일로 전송을 한다.
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
    public ActionForward searchIdPw(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
       String systemCode = UserBroker.getSystemCode(request);
       String msg = "검색 조건에 맞는 사용자가 존재하지 않습니다.";
       String mailMsg = "";
	   String pGUBUN = StringUtil.nvl(request.getParameter("pGUBUN"));
	   String pUserName = StringUtil.nvl(request.getParameter("pUserName"));
	   String pUserId = StringUtil.nvl(request.getParameter("pUserId"));
	   String pResidentId = StringUtil.nvl(request.getParameter("pResidentId1"))+"-"+StringUtil.nvl(request.getParameter("pResidentId2"));

	   UserDAO userDao = new UserDAO();
       UsersDTO user = userDao.getUserInfo(systemCode,pUserId,pUserName,pResidentId);

	   if(pGUBUN.equals("id") && !user.getUserId().equals("")){
	   		//msg = "ID를 검색하여 메일로 전송하였습니다.<br>메일을 확인하시기 바랍니다.";
	        msg = "회원님은 아이디 <font color='#F4623D'><b>["+user.getUserId() +"]</b></font>으로 회원 가입 하셨습니다.";
	   		// 메일로 아이디를 전송해준다.
	   		//mailMsg = "귀하의 ID는 "+user.getUserId()+"입니다.";
	   		//new UserHelper().searchIdPwMailSend(user.getEmail(), user.getUserName(), mailMsg);
	   		//log.debug("userMail Address =>"+user.getEmail());
	   }

	   if(pGUBUN.equals("pass") && !user.getUserId().equals("")){
	        msg  = "회원님의 메일 <font color='#F4623D'><b>["+user.getEmail() +"]</b></font> ";
	        msg += "로 전송되었습니다.<br>전송받은 비밀번호는 로그인하신 후 회원정보에서 새로운 비밀번호로<br>변경하여 사용하시기 바랍니다.";

				/**
				 * 메일로 비밀번호를 전송해준다.
				 */
				String toMail = user.getEmail();
				String toName = user.getUserName();
				String toId = user.getUserId();
				String toPass = user.getPasswd();
				String serverUrl = "http://"+request.getServerName();
				String currentDate = DateTimeUtil.getDateText(1,user.getRegDate());
				StringBuffer sb = new StringBuffer();
				sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
				sb.append("  <tr> ");
				sb.append("	<td colspan='2'><img src='"+serverUrl+"/img/1/mail/mail_icon01.gif' width='28' height='28'><br>");
				sb.append("	  <img src='"+serverUrl+"/img/1/mail/title_password01.gif' width='119' height='27'> ");
				sb.append("	</td>");
				sb.append("  </tr>");
				sb.append("  <tr> ");
				sb.append("	<td background='"+serverUrl+"/img/1/mail/mail_linebg.gif' height='1' colspan='2'><img src='"+serverUrl+"/img/1/common/blank.gif' width='1' height='1'></td>");
				sb.append("  </tr>");
				sb.append("  <tr> ");
				sb.append("	<td width='45'> </td>");
				sb.append("	<td height='85' class='mail' width='515'><img src='"+serverUrl+"/img/1/common/icon02.gif' width='4' height='32' align='left' vspace='2'> ");
				sb.append("	  KOFA 영화학교에서 알려드립니다. <br>");
				sb.append("	  회원님께서 요청하신 비밀번호 정보는 다음과 같습니다.</td>");
				sb.append("  </tr>");
				sb.append("  <tr> ");
				sb.append("	<td>&nbsp;</td>");
				sb.append("	<td> ");
				sb.append("	  <table width='460' border='0' cellspacing='0' cellpadding='0'>");
				sb.append("		<tr> ");
				sb.append("		  <td class='mailb' height='33'><font size='3' face='돋움, 돋움체'>[회원정보]</font></td>");
				sb.append("		</tr>");
				sb.append("		<tr> ");
				sb.append("		  <td> ");
				sb.append("			<table width='460' border='0' cellspacing='0' cellpadding='0'>");
				sb.append("			  <tr> ");
				sb.append("				<td colspan='4' bgcolor='E4B793'><img src='"+serverUrl+"/img/1/common/blank.gif' width='1' height='1'></td>");
				sb.append("			  </tr>");
				sb.append("			  <tr valign='middle'> ");
				sb.append("				<td width='20'>&nbsp;</td>");
				sb.append("				<td width='60' class='mail'>성명</td>");
				sb.append("				<td width='30' align='center'><img src='"+serverUrl+"/img/1/center/center_line02.gif' width='1' height='21'></td>");
				sb.append("				<td class='mailb' width='350'>"+toName+"</td>");
				sb.append("			  </tr>");
				sb.append("			  <tr valign='middle'> ");
				sb.append("				<td colspan='4' bgcolor='E4B793'><img src='"+serverUrl+"/img/1/common/blank.gif' width='1' height='1'></td>");
				sb.append("			  </tr>");
				sb.append("			  <tr valign='middle'> ");
				sb.append("				<td width='20'>&nbsp;</td>");
				sb.append("				<td width='60' class='mail'>아이디</td>");
				sb.append("				<td width='30' align='center'><img src='"+serverUrl+"/img/1/center/center_line02.gif' width='1' height='21'></td>");
				sb.append("				<td class='mailb'>"+toId+"</td>");
				sb.append("			  </tr>");
				sb.append("			  <tr> ");
				sb.append("				<td colspan='4' bgcolor='E4B793'><img src='"+serverUrl+"/img/1/common/blank.gif' width='1' height='1'></td>");
				sb.append("			  </tr>");
				sb.append("			  <tr valign='middle'> ");
				sb.append("				<td width='20'>&nbsp;</td>");
				sb.append("				<td width='60' class='mail'>비밀번호</td>");
				sb.append("				<td width='30' align='center'><img src='"+serverUrl+"/img/1/center/center_line02.gif' width='1' height='21'></td>");
				sb.append("				<td class='mailb'>"+toPass+"</td>");
				sb.append("			  </tr>");
				sb.append("			  <tr valign='middle'> ");
				sb.append("				<td colspan='4' bgcolor='E4B793'><img src='"+serverUrl+"/img/1/common/blank.gif' width='1' height='1'></td>");
				sb.append("			  </tr>");
				sb.append("			  <tr valign='middle' align='left'> ");
				sb.append("				<td colspan='4' height='30' class='mail'>&nbsp;</td>");
				sb.append("			  </tr>");
				sb.append("			  <tr valign='middle' align='left'> ");
				sb.append("				<td colspan='4' height='80' class='mail'>");
				sb.append("				  해당정보로 로그인 하신 후 <br> ");
				sb.append("				  고객님 정보의 안전한 보호를 위해 비밀번호를 변경하여 사용하시기 바랍니다.</td>");
				sb.append("			  </tr>");
				sb.append("			</table>");
				sb.append("		  </td>");
				sb.append("		</tr>");
				sb.append("	  </table>");
				sb.append("	</td>");
				sb.append("  </tr>");
				sb.append("  <tr align='center'> ");
				sb.append("	<td colspan='2' height='30'>&nbsp;</td>");
				sb.append("  </tr>");
				sb.append("  <tr align='center'> ");
				sb.append("	<td colspan='2'><a href='"+serverUrl+"' target='_blank'><img src='"+serverUrl+"/img/1/mail/button_home.gif' width='183' height='23' vspace='10' border='0'></a></td>");
				sb.append("  </tr>");
				sb.append("</table>");

				MailUtil.sendDirectMail(toMail, toName, "KOFA 영화학교 아이디/비밀번호 찾기 메일", sb.toString());

   			//mailMsg = "귀하의 패스워드는 <b>"+user.getPasswd()+"</b>입니다.";
   			//new UserHelper().searchIdPwMailSend(user.getEmail(), user.getUserName(), mailMsg);
   			log.debug("userMail Address =>"+user.getEmail());
 	   }

	   model.put("MSG" , msg);

	   new SiteNavigation(HOME).add(request,"아이디/비밀번호 찾기").link(model);
	   return forward(request, model, "/users/users_idpw_search.jsp");
    }


	/**
	 * 사용자 로긴 페이지를 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward usersLoginShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

	    String PMODE = StringUtil.nvl(request.getParameter("pMode"));

	    model.put("PMODE", PMODE);

		new SiteNavigation(MEMBER).add(request,"로그인").link(model);

		return forward(request, model, "/users/users_login.jsp");
	}


    /**
     * 사용자 정보
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
    public ActionForward userInfoShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = StringUtil.nvl(request.getParameter("userId"));

		UserDAO userDao = new UserDAO();
		UsersDTO userInfo = userDao.getUserInfo(systemCode, userId);

		model.put("userInfo",userInfo);

		return forward(request, model, "/users/users_info_show.jsp");
    }

    /**
     * 사용자 등록, 수정 처리 후 메시지를 처리해 준다.
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
    public ActionForward userProcRedirect(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
	    String systemCode= StringUtil.nvl(request.getParameter("systemCode"));
	    String pProc = StringUtil.nvl(request.getParameter("pProc"));
	    String succYn = StringUtil.nvl(request.getParameter("succYn"));
	    String pMemberGb = StringUtil.nvl(request.getParameter("pMemberGb"));
	    String pUserId = StringUtil.nvl(request.getParameter("pUserId"));
	    String pUserName = StringUtil.nvl(request.getParameter("pUserName"));
	    String pUserPass = StringUtil.nvl(request.getParameter("pUserPass"));

        String msg = "";
        String returnUrl = "";
	    String where = "";
        if(pProc.equals("write")){
        	//  결과 페이지 보여주기
        	model.put("pMemberGb",pMemberGb);
        	model.put("pUserId",pUserId);
        	model.put("pUserName",pUserName);
        	model.put("pUserPass",pUserPass);
        	return forward(request, model, "/users/users_regist_result.jsp");
        }else{
        	msg = "사용자 정보수정에 성공하였습니다.";
        	returnUrl = "/Main.cmd?cmd=mainShow&pMode="+MYPAGE;
        	where = MYPAGE;
        	return notifyAndExit(systemCode,model, msg, returnUrl,where);
        }
    }

    /**
     *  사용자를 검색(아이디, 이름) 할 수 있는 폼을 띄워준다.
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
    public ActionForward searchUserList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
	    String  systemCode = UserBroker.getSystemCode(request);
		String 	pOp 	  =	StringUtil.nvl(request.getParameter("pOp"));
		String 	pSearch   =	StringUtil.nvl(request.getParameter("pSearch"));

		UserDAO userDao = new UserDAO();

		if(!pOp.equals("") && !pSearch.equals("")){
			model.put("userSearchList", userDao.userSearchList(systemCode, pOp, pSearch));
		}
		model.put("pOp",pOp);
		model.put("pSearch",pSearch);

	    return forward(request, model, "/users/userSearchList.jsp");
    }

    /**
     * 단체별 유저를 가져온다.
     * @param actionMapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @param model
     * @return
     * @throws Exception
     */
    public ActionForward searchUserListGroup(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
	    String  systemCode = UserBroker.getSystemCode(request);
		String 	pOp 	  =	StringUtil.nvl(request.getParameter("pOp"));
		String 	pSearch   =	StringUtil.nvl(request.getParameter("pSearch"));
		String 	pDeptDaeCode   = request.getParameter("pDeptDaeCode");
		String 	pDeptSoCode    = request.getParameter("pDeptSoCode");
		String 	pUserType      = request.getParameter("pUserType");		// 학생들만 단체를 가지기 때문..

		UserDAO userDao = new UserDAO();

		if(!pOp.equals("") && !pSearch.equals("")){
			model.put("userSearchList", userDao.userSearchList(systemCode, pOp, pSearch, pDeptDaeCode, pDeptSoCode, pUserType ));
		}
		model.put("pOp",pOp);
		model.put("pSearch",pSearch);

		return forward(request, model, "/users/userSearchList.jsp");
    }


    public ActionForward securityInfoShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		new SiteNavigation(MEMBER).add(request,"개인정보보호정책").link(model);

	    return forward(request, model, "/users/securityInfo.jsp");
    }


	// 파일 삭제
	public ActionForward userFileDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("==========> start aspContentsFileDelete");

		//	Main SystemCode
		String systemCode = UserBroker.getSystemCode(request);

		String pMode = StringUtil.nvl(request.getParameter("pMode"));

		model.put("pMode", pMode);

		String pFileNo = request.getParameter("pFileNo");

		UserDAO userDao		= new UserDAO();
		UsersDTO usersDto	= new UsersDTO();

		String userId     = UserBroker.getUserId(request);

		usersDto = userDao.getUserInfo(systemCode, userId);

		String rfile_name 	= "";
		String sfile_name 	= "";
		String filePath 	= "";

		String msg = "삭제하였습니다.";
		String returnUrl = "/User.cmd?cmd=userWrite&pGUBUN=edit&MENUNO=0";

		int retVal = 0;
		retVal = userDao.delUserFile(systemCode, userId, pFileNo);

		if(pFileNo.equals("1")){
			rfile_name = StringUtil.nvl(usersDto.getRfileName());
			sfile_name = StringUtil.nvl(usersDto.getSfileName());
			filePath = StringUtil.nvl(usersDto.getFilePath());

		}else if(pFileNo.equals("2")){
			rfile_name = StringUtil.nvl(usersDto.getRfileName());
			sfile_name = StringUtil.nvl(usersDto.getSfileName());
			filePath = StringUtil.nvl(usersDto.getFilePath());

		}else if(pFileNo.equals("3")){
			rfile_name = StringUtil.nvl(usersDto.getRfileName());
			sfile_name = StringUtil.nvl(usersDto.getSfileName());
			filePath = StringUtil.nvl(usersDto.getFilePath());

		}else if(pFileNo.equals("4")){
			rfile_name = StringUtil.nvl(usersDto.getRfileName());
			sfile_name = StringUtil.nvl(usersDto.getSfileName());
			filePath = StringUtil.nvl(usersDto.getFilePath());
		}


		if(!rfile_name.equals("") && !sfile_name.equals("") && !filePath.equals("")){
			FileUtil.delFile(filePath, sfile_name);
			log.debug(" 첨부파일을 삭제하였습니다."+filePath+ "  :::  " + sfile_name);
		}

		if(retVal <=0){
			msg = "첨부파일 삭제 실패 하였습니다.";
		}

		new SiteNavigation(pMode).link(model);
		return notifyAndExit(systemCode, model, msg, returnUrl, pMode);
	}
}

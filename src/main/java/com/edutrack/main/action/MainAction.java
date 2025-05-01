/*
 * Created on 2004. 9. 17.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.main.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.bbs.dao.BbsContentsDAO;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.community.dao.CommBbsInfoDAO;
import com.edutrack.community.dao.CommCategoryDAO;
import com.edutrack.community.dao.CommInfoDAO;
import com.edutrack.community.dao.CommInviteDAO;
import com.edutrack.curristudent.dto.StudentDTO;
import com.edutrack.currisub.dao.CurriContentsDAO;
import com.edutrack.currisub.dao.CurriCourseEnrollDAO;
import com.edutrack.currisub.dao.CurriSubDAO;
import com.edutrack.curritop.dao.CurriCategoryDAO;
import com.edutrack.curritop.dto.CurriCategoryDTO;
import com.edutrack.dept.dao.DeptSoDAO;
import com.edutrack.framework.Constants;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.message.dao.MessageDAO;
import com.edutrack.poll.dao.InternetPollDAO;
import com.edutrack.poll.dto.InternetPollDTO;
import com.edutrack.popupnotice.dao.PopupNoticeDAO;
import com.edutrack.schedule.dao.ScheduleDAO;
import com.edutrack.user.dao.UserDAO;
import com.edutrack.user.dto.UserConnectDTO;
import com.edutrack.user.dto.UsersDTO;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MainAction extends StrutsDispatchAction{
	/**
	 *
	 */
	public MainAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 사용자 로그인 처리를 해준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward setLogin(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String systemCode = UserBroker.getSystemCode(request);
		String soCodeUseYn = "Y";
		String soCodeUserId = "";
		String goUrl = "";
		String pGubun = StringUtil.nvl(request.getParameter("pGubun"));		//팝업로그인여부
		String pMenu = StringUtil.nvl(request.getParameter("pMenu"));		//나의강의실이나 커뮤니티 직접이동 여부
		String pMode = StringUtil.nvl(request.getParameter("pMode"));

		log.debug("사용자 로그인 요청 "+userId+","+userPw);
		// DAO 생성
		UserDAO userDao = new UserDAO();
		DeptSoDAO deptSoDao = new DeptSoDAO();
		UsersDTO userInfo = userDao.getUserInfo(systemCode, userId);
		RowSet rs = deptSoDao.getDeptSoInfo( systemCode, userInfo.getDeptDaecode(), userInfo.getDeptSocode() );

		// 단체관리자일경우 승인 여부를 체크..
		if(rs.next() ) {
			soCodeUserId = StringUtil.nvl(rs.getString("user_id"));
			soCodeUseYn  = StringUtil.nvl(rs.getString("use_yn"));

			rs.close();
		}

		if(!userInfo.getUserId().equals("")){ // 사용자가 존재의 존재유무 판단.
			log.debug("사용자 정보를 가져왔다."+userInfo.getUserId());
			if(userInfo.getPasswd().equals(userPw)){ // 패스워드가 맞는지 판단한다.
				// 사용자가 인증이 안되어 있을 경우
				if(userInfo.getUseStatus().equals("N") || ( soCodeUserId.equals(userId) && soCodeUseYn.equals("N")) ) return alertAndExit(systemCode, model, "인증되지 않은 사용자 입니다.", "/User.cmd?cmd=usersLoginShow&pMode=MEMBER",HOME);
				log.debug("사용자 인증 통과");
				UserConnectDTO conInfo = userDao.getUserConnect(systemCode, userId);

				/* 중복 로그인 허용시 주석 처리 해야 할 부분 시작 */
/*
                if(conInfo != null){
					// 현재 접속 중인지를 확인한다.
					if(conInfo.getLastCon() < CommonUtil.CONCHECKTIME) {
						// 현재 어떤 아이피에서 접근 중인지를 팝업메시지를 띄워주고 기존에 접속해 있는 사용자를
						// 로그오프 시킬지에 대한  처리를 해준다.
						log.debug("기존 사용자 로그인 중");
						String userIp = conInfo.getRemoteIp();
						if(userIp.equals(request.getRemoteAddr())) return confirmAndMove(systemCode, model, "이전에 강제 종료를 하셨거나  이미 로그인 중입니다.\\n접속중인 사용자를 종료 시키고 접속하시겠습니까?", "/Main.cmd?cmd=changeUser&userId="+userId,"/Main.cmd?cmd=mainShow&pMode="+HOME, HOME);

						return confirmAndMove(systemCode, model, "이전에 강제 종료를 하셨거나 사용자가 "+userIp+"에서  이미 로그인 중입니다.\\n접속중인 사용자를 종료 시키고 접속하시겠습니까?", "/Main.cmd?cmd=changeUser&userId="+userId,"/Main.cmd?cmd=mainShow&pMode="+HOME, HOME);
					}
                }
*/
				/* 중복 로그인 허용시 주석 처리 해야 할 부분 끝
				 * com.edutrack.framework.struts.StrutsDispatchAction.java 의
				 * 74라인  UserConnectDTO conInfo = null; 부분 부터 주석 처리
				 */
				log.debug("새로운 사용자 셋팅");
				setUserInfoMem(request, userDao, userInfo, conInfo);

			}else return alertAndExit(systemCode, model, "사용자의 패스워드가 잘못되었습니다.", "/Main.cmd?cmd=mainShow",HOME);
		}else{
			log.debug("사용자의 ID가 존재하지 않습니다.");
			return alertAndExit(systemCode, model, "사용자의 ID가 존재하지 않습니다.", "/Main.cmd?cmd=mainShow",HOME);
		}

		int sucessYn = userDao.getUserConnectCnt(systemCode, userId,request.getRemoteAddr());

		if((userInfo.getUserType()).equals("M")) goUrl = "/CurriSub.cmd?cmd=currentMypageList&MENUNO=0";
		else if((userInfo.getUserType()).equals("P") || (userInfo.getUserType()).equals("J")) goUrl = "/Main.cmd?cmd=profmangerCurriList&pGubun=1&MENUNO=0";
		else if((userInfo.getUserType()).equals("C")) goUrl = "/CompanyMasterCourse.cmd?cmd=companyMasterCurriList&MENUNO=0";
		else  goUrl = "/Main.cmd?cmd=stuCurriList&MENUNO=0";

		if(pGubun.equals("popup") && pMenu.equals("MyClass")) {
			return closePopupReload(systemCode, model, "", "", "POPUP", goUrl);		//나의 강의실 직접이동
		}
		else if(pGubun.equals("popup") && pMenu.equals("Community")) {
			return closePopupReload(systemCode, model, "", "", "POPUP", "/Community.cmd?cmd=recCommunity&MENUNO=0");	//커뮤니티 메인 이동
		}
		else if(pGubun.equals("popup")) {
			return closePopupReload(systemCode, model, "", "", "POPUP");		//해당 페이지 리로드
		}
		else {
			return redirect(goUrl);		//메인에서 로그인 했을 때 나의 강의실로 직접이동
		}

//		return redirect("/Main.cmd?cmd=mainShow&pMode="+HOME);
	}

	/**
	 * 기존 사용자가 이미 로그인을 했을 시 먼저 로그인 한 사용자를 쫓아내고 로그인을 한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeUser(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String userId = request.getParameter("userId");
		String systemCode = UserBroker.getSystemCode(request);
		log.debug("기존 사용자를 로그오프 시키고 새로운 사용자를 로그인 시켜준다."+userId);
		// DAO 생성
		String goUrl = "";
		UserDAO userDao = new UserDAO();
		UsersDTO userInfo = userDao.getUserInfo(systemCode, userId);
		UserConnectDTO conInfo = userDao.getUserConnect(systemCode, userId);

		setUserInfoMem(request, userDao, userInfo, conInfo);

		log.debug("사용자 로그인 성공!!");
		if((userInfo.getUserType()).equals("M")) goUrl = "/CurriSub.cmd?cmd=currentMypageList&MENUNO=0";
		else if((userInfo.getUserType()).equals("P")) goUrl = "Main.cmd?cmd=profmangerCurriList&pGubun=1&MENUNO=0";
		else  goUrl = "Main.cmd?cmd=stuCurriList&MENUNO=0";

		int sucessYn = userDao.getUserConnectCnt(systemCode, userId,request.getRemoteAddr());
		return redirect(goUrl);
	}

	/**
	 * 메인 홈으로 이동을 해준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward mainShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		log.debug("----------------- mainHome Start ----------------");
		String 	systemCode	= 	UserBroker.getSystemCode(request);
		String 	userType 	= 	UserBroker.getUserType(request);
		String 	pMode 		= 	StringUtil.nvl(request.getParameter("pMode"),HOME);
		String 	userId 		= 	UserBroker.getUserId(request);
		String	property1	=	StringUtil.nvl(request.getParameter("pProperty1"), "Cyber");

		//---인터넷 투표 start ----
		InternetPollDAO	pollDao	= new InternetPollDAO();
		InternetPollDTO	pollDto	= new InternetPollDTO();
		pollDto = pollDao.getPollShow(systemCode,0,userId);
		model.put("pollShow",pollDto);

		//---팝업공지 ----
		PopupNoticeDAO	popupnoticeDao	= new PopupNoticeDAO();
		//-- LMS 에서 열리는 팝업을 확인하기 위해 L 을 부여
		model.put("popupnoticeList", popupnoticeDao.getUsePopupNotice(systemCode,"L"));

		//읽지 않은 쪽지숫자 가져오기
		MessageDAO messageDao = new MessageDAO();
		int UnReadCnt	=	StringUtil.nvl( String.valueOf(messageDao.getUnReadCnt(systemCode,userId)), 0);
		model.put("UnReadCnt", String.valueOf(UnReadCnt));

		//---- 공지사항
		BbsContentsDAO bbsContentsDao = new BbsContentsDAO();
		model.put("Haksa_NoticeList", bbsContentsDao.getBbsContentsPagingList(1,4,10, systemCode, 17, userType, CommonUtil.getCurrentDate(),"","",""));
		model.put("NoticeList", bbsContentsDao.getBbsContentsPagingList(1,4,10, systemCode, 18, userType, CommonUtil.getCurrentDate(),"","",""));
		model.put("NewsList", bbsContentsDao.getBbsContentsPagingList(1,4,10, systemCode, 19, userType, CommonUtil.getCurrentDate(),"","",""));

		model.put("pProperty1", property1);
		model.put("MENUNO", "");

		//공개강좌 5개 불러오기
		CurriSubDAO	curriSubDao	=	new CurriSubDAO();
		RowSet	curriList = curriSubDao.currentCurriList(systemCode, "99999", "", "", "CYBER", "P", "", 5);
		model.put("CurriList", curriList);
		//1학년 정규강좌 가져오기
		RowSet	curriList01 = curriSubDao.currentCurriContentsList(systemCode, "00001", "1", "", "CYBER", "R", "A", 4);
		model.put("CurriList01", curriList01);
		//2학년 정규강좌 가져오기
		RowSet	curriList02 = curriSubDao.currentCurriContentsList(systemCode, "00001", "2", "", "CYBER", "R", "A", 4);
		model.put("CurriList02", curriList02);
		//3학년 정규강좌 가져오기
		RowSet	curriList03 = curriSubDao.currentCurriContentsList(systemCode, "00001", "3", "", "CYBER", "R", "A", 4);
		model.put("CurriList03", curriList03);

		new SiteNavigation(pMode).link(model);
		return forward(request, model, "/main/main_home.jsp");
	}

	/**
	 * 상시과정 컨텐츠 팝업으로 띄우기
	 *
	 * @return
	 * @throws Exception
	 */
	public ActionForward popupOrdinaryContents(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	systemCode 	= 	UserBroker.getSystemCode(request);

		model.put("pCurriCode", StringUtil.nvl(request.getParameter("pCurriCode")));
		model.put("pCurriYear", StringUtil.nvl(request.getParameter("pCurriYear"), "2007"));
		model.put("pCurriTerm", StringUtil.nvl(request.getParameter("pCurriTerm"), "1"));

		new SiteNavigation("HOME").add(request,"상시과정").link(model);
		return forward(request, model, "/main/popupOrdinaryContents.jsp");
	}

	public ActionForward popupOrdinaryMenu(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	systemCode 	= 	UserBroker.getSystemCode(request);

		String	pCurriCode	=	StringUtil.nvl(request.getParameter("pCurriCode"));
		int		pCurriYear	=	StringUtil.nvl(request.getParameter("pCurriYear"), 2007);
		int		pCurriTerm	=	StringUtil.nvl(request.getParameter("pCurriTerm"), 1);

		CurriContentsDAO	contentsDao	=	new CurriContentsDAO();
		RowSet	contentsList	=	contentsDao.ordinaryCourseContentsList(systemCode, pCurriCode, pCurriYear, pCurriTerm);
		model.put("ContentsList", contentsList);
		model.put("pCurriCode", pCurriCode);
		model.put("pCurriYear", String.valueOf(pCurriYear));
		model.put("pCurriTerm", String.valueOf(pCurriTerm));

		new SiteNavigation("HOME").add(request,"상시과정").link(model);
		return forward(request, model, "/main/popupOrdinaryMenu.jsp");
	}

	public ActionForward popupOrdinaryMain(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	systemCode 	= 	UserBroker.getSystemCode(request);
		String	pServerPath	=	StringUtil.nvl(request.getParameter("pServerPath"));

		model.put("pServerPath", pServerPath);

		new SiteNavigation("HOME").add(request,"상시과정").link(model);
		return forward(request, model, "/main/popupOrdinaryMain.jsp");
	}

	/**
	 * 사용자 타입별 마이페이지를 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward myPageShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userType = UserBroker.getUserType(request);
		String userId = UserBroker.getUserId(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		if(pMode.equals(MYPAGE) || pMode.equals(COMMUNITY)){
			if(userId.equals("") || userId.equals("@"))
				return alertAndExit(UserBroker.getSystemCode(request), model, "먼저 로그인을 하셔야 합니다.", "/Main.cmd?cmd=mainShow&pMode=Home",HOME);
		}
		CurriSubDAO curriSubDao = new CurriSubDAO();
		RowSet curriList = null;
		RowSet curriBeforeList = null;
		if(userType.equals("M")){//-- 관리자 타입의 경우
			curriList = curriSubDao.currentCurriList(systemCode, "", "", "", "", "", "", 0);
		}
		if(userType.equals("C")){//-- 과정운영자자 타입의 경우
			//curriList = curriSubDao.getManagerCurriList(systemCode,userId,"ES", 5);
			curriList = curriSubDao.getManagerCurriList(systemCode,userId,"", 5);
		}
		if(userType.equals("P")){//-- 교수자 타입의 경우
			//curriList = curriSubDao.getProfCurriList(systemCode,userId,"ES", 5);
			curriList = curriSubDao.getProfCurriList(systemCode,userId,"ES", 5);
		}
		if(userType.equals("S")){//-- 학습자 타입의 경우
			// 수강중인 과정
			curriList = curriSubDao.getStCurriList(systemCode,userId,"S", 5,null);
			// 수강 신청중인 과정
			curriBeforeList = curriSubDao.getStCurriList(systemCode,userId,"EN", 5,null);
		}

		//---- 공지사항
		//model.put("pMode", pMode);
		BbsContentsDAO bbsContentsDao = new BbsContentsDAO();
		model.put("noticeList", bbsContentsDao.getBbsContentsPagingList(1,5,10, systemCode,1, userType,CommonUtil.getCurrentDate(),"","",""));

		MessageDAO messageDao = new MessageDAO();
		int	unReadMessageCnt = messageDao.getUnReadCnt(systemCode, userId);
		model.put("unMsgCnt", Integer.toString(unReadMessageCnt));

		//---- 나의일정
		ScheduleDAO scheduleDao = new ScheduleDAO();
		int now_date = Integer.parseInt(DateTimeUtil.getDate());
		model.put("scheduleList", scheduleDao.getScheduleList(systemCode,userId,now_date));

		//과정분류
		CurriCategoryDAO curriCategoryDao = new CurriCategoryDAO();
		RowSet curriCateList =  curriCategoryDao.getCategoryListAll(systemCode);
		model.put("curriCateList", curriCateList);

		model.put("curriList",curriList);
		model.put("curriBeforeList",curriBeforeList);
		String mappingUrl = "main_mypage"+userType+".jsp";

		new SiteNavigation(pMode).link(model);

		return forward(request, model, "/main/"+mappingUrl);
	}

	/**
	 * 커뮤니티 메인의 처리
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward communityShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode	= UserBroker.getSystemCode(request);
		String userType		= UserBroker.getUserType(request);
		String pMode 		= StringUtil.nvl(request.getParameter("pMode"),HOME);
		String userId 		= UserBroker.getUserId(request);

/*
		if(pMode.equals(MYPAGE) || pMode.equals(COMMUNITY)){
			if(userId.equals("") || userId.equals("@"))
				return alertAndExit(UserBroker.getSystemCode(request), model, "먼저 로그인을 하셔야 합니다.", "/Main.cmd?cmd=mainShow&pMode=Home",HOME);
		}
*/
		CommInfoDAO commInfoDao 		= new CommInfoDAO();
		CommBbsInfoDAO commbbsInfoDao	= new CommBbsInfoDAO();
		CommCategoryDAO commCategoryDao = new CommCategoryDAO();

		RowSet rs  = commInfoDao.getNewCommInfo(systemCode);			//	신규커뮤니티
		RowSet rs1 = commInfoDao.getRecCommInfo(systemCode);			//	추천커뮤니티
		RowSet rs2 = commInfoDao.getPridecommInfo(systemCode);			//	커뮤니티 자랑
		RowSet rs3 = commInfoDao.getCommInfoList(systemCode, userId);	//	나의커뮤니티
		RowSet rs4 = commCategoryDao.getCommCategoryList(systemCode);	//	커뮤니티분류

		// 초대메시지의 갯수를 가져온다.
		CommInviteDAO inviteDao = new CommInviteDAO();
		int msgCnt = inviteDao.getInviteMsgCnt(systemCode, userId);

		model.put("rs",rs);
		model.put("rs1",rs1);
		model.put("rs2",rs2);
		model.put("rs3",rs3);
		model.put("rs4",rs4);
		model.put("inviteMsgCnt",""+msgCnt);

		new SiteNavigation(pMode).link(model);

		return forward(request, model, "/main/main_community.jsp");
	}

	/**
	 * 사용자 로그오프 처리를 한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward userLogOff(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		HttpSession session = request.getSession(false);
		UserDAO userDao = new UserDAO();
		userDao.editUserConnect(UserBroker.getSystemCode(request), UserBroker.getUserId(request),session.getId(), request.getRemoteAddr(), "O");

		session.removeAttribute("USERINFO");
		session.invalidate();

		return redirect("/Main.cmd?cmd=mainShow&pMode="+HOME);
	}

	/**
	 * 사용자 로그인 정보를 세션과 메모리에 셋팅을 해준다.
	 * @param request
	 * @param userinfo
	 */
	private void setUserInfoMem(HttpServletRequest request, UserDAO userdao, UsersDTO userinfo, UserConnectDTO coninfo) throws Exception{
		long curTime = System.currentTimeMillis();
		HttpSession session = request.getSession();
		// 사용자 접속 정보를 셋팅해준다.
		if(coninfo.getUserId().equals("")) userdao.addUserConnect(userinfo.getSystemCode(), userinfo.getUserId(),session.getId(), request.getRemoteAddr());
		else userdao.editUserConnect(userinfo.getSystemCode(), userinfo.getUserId(),session.getId(), request.getRemoteAddr(), "A");

		// 세션 정보를 셋팅해 준다.
		session.setAttribute(Constants.LOGIN_SYSTEMCODE, userinfo.getSystemCode());
		session.setAttribute(Constants.LOGIN_USERID, userinfo.getUserId());
		session.setAttribute(Constants.LOGIN_USERTYPE, userinfo.getUserType());
		session.setAttribute(Constants.LOGIN_USERNAME, userinfo.getUserName());
		session.setAttribute(Constants.LOGIN_SCHOOLYEAR, userinfo.getSchoolYear());

		DeptSoDAO deptSoDao = new DeptSoDAO();
		// 사용자 정보를 새롭게 생성한다.
		UserMemDTO newUserInfo =  new UserMemDTO();
		newUserInfo.systemCode = userinfo.getSystemCode();
		newUserInfo.userId = userinfo.getUserId();
		newUserInfo.userName = userinfo.getUserName();
		newUserInfo.userType = userinfo.getUserType();
		newUserInfo.conCnt = coninfo.getConnectNo();
		newUserInfo.deptSoCode = userinfo.getDeptSocode();
		newUserInfo.deptDaeCode = userinfo.getDeptDaecode();
		newUserInfo.conTime = curTime;
		newUserInfo.sessionId = session.getId();
		newUserInfo.userIp = request.getRemoteAddr();
		newUserInfo.schoolYear = userinfo.getSchoolYear();
		newUserInfo.deptManager = deptSoDao.chkDeptManager(userinfo.getSystemCode(),userinfo.getDeptDaecode(),userinfo.getDeptSocode(),userinfo.getUserId());

		// 새로운 사용자 정보를 셋팅해준다.
		session.setAttribute("USERINFO",newUserInfo);
	}

	/**
	 * 대학소개 메뉴 페이지
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward infoShow (ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"), INFO);
		String pInfoNum = StringUtil.nvl(request.getParameter("pInfoNum"),"0");
		String pInfoNum2 = StringUtil.nvl(request.getParameter("pInfoNum2"),"0");
		String strNavi = "대학소개";
		String strJsp = "/info/info_organi_1_1.jsp";

		if(pMode.equals("Info")) {
			if(pInfoNum.equals("0") || pInfoNum.equals("1")){
				strNavi = "인사말";
				strJsp = "/info/info_organi_1_1.jsp";

				if(pInfoNum2.equals("1")){
					strJsp = "/info/info_organi_1_1.jsp";
				}
				else if(pInfoNum2.equals("2")){
					strJsp = "/info/info_organi_1_2.jsp";
				}
			}
			else if(pInfoNum.equals("2")){
				strNavi = "설립취지 연혁";
				strJsp = "/info/info_organi_2_1.jsp";

				if(pInfoNum2.equals("1")){
					strJsp = "/info/info_organi_2_1.jsp";
				}
				else if(pInfoNum2.equals("2")){
					strJsp = "/info/info_organi_2_2.jsp";
				}
				else if(pInfoNum2.equals("3")){
					//연혁 2000년도의 팝업창
					strJsp = "/info/info_organi_2_3.jsp";
				}
			}
			else if(pInfoNum.equals("3")){
				strNavi = "교육내용 과정";
				strJsp = "/info/info_organi_3_1.jsp";

				if(pInfoNum2.equals("1")){
					strJsp = "/info/info_organi_3_1.jsp";
				}
				else if(pInfoNum2.equals("2")){
					strJsp = "/info/info_organi_3_2.jsp";
				}
			}
			else if(pInfoNum.equals("4")){
				strNavi = "교수진 소개";
				strJsp = "/info/info_organi_4_1.jsp";

				if(pInfoNum2.equals("1")){
					strJsp = "/info/info_organi_4_1.jsp";
				}
				else if(pInfoNum2.equals("2")){
					strJsp = "/info/info_organi_4_2.jsp";
				}
			}
			else if(pInfoNum.equals("5")){
				strNavi = "대학의 조직과 기구";
				strJsp = "/info/info_organi_5_1.jsp";

				if(pInfoNum2.equals("1")){
					strJsp = "/info/info_organi_5_1.jsp";
				}
				else if(pInfoNum2.equals("2")){
					strJsp = "/info/info_organi_5_2.jsp";
				}
				else if(pInfoNum2.equals("3")){
					strJsp = "/info/info_organi_5_3.jsp";
				}
				else if(pInfoNum2.equals("4")){
					strJsp = "/info/info_organi_5_4.jsp";
				}
			}
			else if(pInfoNum.equals("6")){
				strNavi = "찾아오는 길";
				strJsp = "/info/use_info3.jsp";
			}
		}
		else if(pMode.equals("Help")) {
			strNavi = "이용안내";
			strJsp = "/info/use_help.jsp";
		}



		new SiteNavigation(pMode).add(request,strNavi).link(model);
		return forward(request, model, strJsp);
	}

	/**
	 * 입학안내
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward enterShow (ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String pMode = StringUtil.nvl(request.getParameter("pMode"), ENTER);
		String pInfoNum = StringUtil.nvl(request.getParameter("pInfoNum"),"0");
		String strNavi = "입학안내";
		String strJsp = "/info/enter_organi_1_1.jsp";


		if(pInfoNum.equals("0") || pInfoNum.equals("1")){
			strNavi = "입학안내";
			strJsp = "/info/enter_organi_1_1.jsp";
		}
		else if(pInfoNum.equals("2")){
			strNavi = "수강안내";
			strJsp = "/info/enter_organi_1_2.jsp";
		}
		else if(pInfoNum.equals("3")){
			return redirect("/Schedule.cmd?cmd=haksaSchedule&MENUNO=0&pMode=Enter");
		}

		new SiteNavigation(pMode).add(request,strNavi).link(model);
		return forward(request, model, strJsp);
	}


	/**
	 * 학생지원센터
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward StudentCenterShow (ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String pMode = StringUtil.nvl(request.getParameter("pMode"), STUDENTCENTER);
		String pInfoNum = StringUtil.nvl(request.getParameter("pInfoNum"),"0");
		String pInfoNum2 = StringUtil.nvl(request.getParameter("pInfoNum2"),"0");
		String pInfoNum3 = StringUtil.nvl(request.getParameter("pInfoNum3"),"0");
		String strNavi = "입학에서 졸업까지";
		String strJsp = "/info/student_center_1.jsp";

		if(pInfoNum.equals("11") || pInfoNum2.equals("1")){
			strNavi = "입학에서 졸업까지";
			strJsp = "/info/student_center_1.jsp";
		}
		else if(pInfoNum2.equals("2")){
			strNavi = "대학생활 및 학습방법";
			strJsp = "/info/student_center_2.jsp";
		}
		else if(pInfoNum2.equals("3")){
			strNavi = "교재안내";
			strJsp = "/info/student_center_3_1.jsp";

			if(pInfoNum3.equals("1")){
				strJsp = "/info/student_center_3_1.jsp";
			}
			else if(pInfoNum3.equals("2")){
				strJsp = "/info/student_center_3_2.jsp";
			}
			else if(pInfoNum3.equals("3")){
				strJsp = "/info/student_center_3_3.jsp";
			}
		}

		new SiteNavigation(pMode).add(request,strNavi).link(model);
		return forward(request, model, strJsp);
	}


	/**
	 * 나의 수강과정을 보여준다. (학습자 로그인후 메인)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward stuCurriList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{

		String	systemCode 		= 	UserBroker.getSystemCode(request);
		String	userType 		= 	UserBroker.getUserType(request);
		String	userId 			= 	UserBroker.getUserId(request);
		String	schoolYear		=	UserBroker.getSchoolYear(request);
		String 	pMode			= 	StringUtil.nvl(request.getParameter("pMode"),MYPAGE);
		String 	pGubun 			= 	StringUtil.nvl(request.getParameter("pGubun"),"1");
		String 	enrollStatus 	= 	"";

		if(("1").equals(pGubun)) enrollStatus = "S"; 		// 수강중인과정
		else if(("2").equals(pGubun)) enrollStatus = "EN";	// 수강신청중인 과정
		else if(("3").equals(pGubun)) enrollStatus = "CF";	// 수료과정

		CurriCategoryDAO curriCategoryDao = new CurriCategoryDAO();
		CurriCategoryDTO curriCategoryDto = new CurriCategoryDTO();
		ArrayList arrList = new ArrayList();
		//RowSet cateRs = curriCategoryDao.getCategoryList1(systemCode);
		RowSet cateRs = curriCategoryDao.getCategoryCntList1(systemCode, userId);
		CurriSubDAO curriSubDao = new CurriSubDAO();
		//학생이 수강하는 총 강좌 갯수
		int rowLength = 0;
		rowLength = curriSubDao.getTotalCurriCount(systemCode, enrollStatus, userId);
		model.put("rowLength", String.valueOf(rowLength));
		log.debug("학생이 수강하는 총 강좌 갯수; rowLength : "+rowLength);

		try{
			while(cateRs.next()){
				curriCategoryDto = new CurriCategoryDTO();
				curriCategoryDto.setCateCode(StringUtil.nvl(cateRs.getString("pare_code1")));
				curriCategoryDto.setCateName(StringUtil.nvl(cateRs.getString("cate_name")));

				arrList.add(curriCategoryDto);
			}
			model.put("cateList",arrList);

		}catch(Exception e){
			log.debug(e);
		}finally{
			cateRs.close();
		}

		//-- 수강신청하지 않은 강좌 리스트
		Map		paramMap	=	new HashMap();
		paramMap.put("pPageGubun", "PR");	//process
		paramMap.put("pUserId", userId);
		CurriCourseEnrollDAO	enrollDao	=	new CurriCourseEnrollDAO();
		RowSet	rs	=	enrollDao.getCurriCourseEnrollList(systemCode, "", schoolYear, "", "Cyber", "R", paramMap);
		model.put("NoCurriList", rs);

		String naviMsg = "";
		if(("1").equals(pGubun)) naviMsg ="수강중인과정";
		else if(("2").equals(pGubun)) naviMsg ="수강신청현황";
		else if(("3").equals(pGubun)) naviMsg ="지난과정";


		new SiteNavigation(MYPAGE).add(request,naviMsg).link(model);
		return forward(request, model, "/main/stu_curri_list.jsp");
	}

	/**
	 * 나의 수강과정을 보여준다. (학습자 로그인후 메인)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ArrayList stuCurriListAuto(String pGubun, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userType = UserBroker.getUserType(request);
		String userId = UserBroker.getUserId(request);

		CurriSubDAO curriSubDao = new CurriSubDAO();
		CurriCategoryDAO curriCategoryDao = new CurriCategoryDAO();
		StudentDTO studentDto = new StudentDTO();

		ArrayList curriArrList = new ArrayList();		// 카테고리, 과정
		ArrayList retArrList  = new ArrayList();		// 리턴 arrayList

		RowSet cateRs = curriCategoryDao.getCategoryList1(systemCode);

		RowSet curriList = null;
		String enrollStatus = "";

		if(("1").equals(pGubun)) enrollStatus = "S"; 		// 수강중인과정
		else if(("2").equals(pGubun)) enrollStatus = "EN";	// 수강신청중인 과정
		else if(("3").equals(pGubun)) enrollStatus = "CF";	// 수료과정

		int iServiceStart = 0;
		int iServiceEnd   = 0;
		int iChungEnd   = 0;
		int currentDate = Integer.parseInt(StringUtil.substring(CommonUtil.getCurrentDate(),0,8));

		try{
			ArrayList categoryArrList = new ArrayList();		// 카테고리
			ArrayList arrList1 = new ArrayList();
			int i = 0;

			while(cateRs.next()){

				arrList1 = new ArrayList();		// 과정
				categoryArrList.add(StringUtil.nvl(cateRs.getString("pare_code1")));

				curriList = null;
				curriList = curriSubDao.getStCurriList(systemCode,userId,enrollStatus, 0,cateRs.getString("pare_code1"));

				while(curriList.next()){
					studentDto = new StudentDTO();
					i++;
//System.out.println("===================================>> check01");
					studentDto.setCurriName(StringUtil.nvl(curriList.getString("curri_name")));
					studentDto.setCurriCode(StringUtil.nvl(curriList.getString("curri_code")));
					studentDto.setCurriYear(curriList.getInt   ("curri_year"));
//System.out.println("===================================>> check02");
					studentDto.setCurriTerm(curriList.getInt   ("curri_term"));
					studentDto.setCurriType(StringUtil.nvl(curriList.getString("curri_property2")));
//System.out.println("===================================>> check03");
					studentDto.setServicestartDate(DateTimeUtil.getDateType(1,StringUtil.substring(curriList.getString("servicestart_date"),0,8)));
					studentDto.setServiceendDate(DateTimeUtil.getDateType(1,StringUtil.substring(curriList.getString("serviceend_date"),0,8)));
					studentDto.setChungendDate(StringUtil.nvl(curriList.getString("chungend_date")));
					studentDto.setGetCredit(curriList.getString("credit"));
//System.out.println("===================================>> check04");
					studentDto.setPaymentIdx(curriList.getInt("payment_idx"));
					studentDto.setEnrollStats(StringUtil.nvl(curriList.getString("enroll_status")));
					studentDto.setEnrollNo(curriList.getInt("enroll_no"));
//System.out.println("===================================>> check05");

					studentDto.setReportCnt(curriList.getInt("report_cnt"));
					studentDto.setExamCnt(curriList.getInt("exam_cnt"));
					studentDto.setForumCnt(curriList.getInt("forum_cnt"));
//System.out.println("===================================>> check06");

					studentDto.setContentsCnt(curriList.getInt("contents_cnt"));			//전체 컨텐츠 갯수
					studentDto.setShowContentsCnt(curriList.getInt("show_contents_cnt"));	//수강한 컨텐츠 갯수
					studentDto.setCurriPercent(curriList.getDouble("pro_rate"));			//진도율
//System.out.println("===================================>> check07");
					studentDto.setCateName(curriList.getString("cate_name"));				//대분류 네임
					studentDto.setServiceStart(curriList.getString("service_start"));		//상시과정 기한(일)
					studentDto.setCurriInfo(curriList.getString("curri_info"));				//과정정보
//System.out.println("===================================>> check08");

					studentDto.setOpenCnt(curriList.getInt("open_cnt"));					//동영상을 오픈한 횟수
					studentDto.setOffTotalTime(curriList.getInt("off_total_time"));			//젼체동영상시간
//System.out.println("===================================>> check09");
					studentDto.setOffShowTime(curriList.getInt("off_show_time"));			//수강한동영상시간
					studentDto.setOffContentsCnt(curriList.getInt("off_contents_cnt"));		//총오프라인수업 갯수
					studentDto.setOffAttendCnt(curriList.getInt("off_attend_cnt"));			//오프라인 출석 횟수
//System.out.println("===================================>> check10");

					if(!StringUtil.nvl(curriList.getString("servicestart_date")).equals(""))
						iServiceStart = Integer.parseInt(StringUtil.substring(StringUtil.nvl(curriList.getString("servicestart_date")),0,8));
					if(!StringUtil.nvl(curriList.getString("serviceend_date")).equals(""))
						iServiceEnd = Integer.parseInt(StringUtil.substring(StringUtil.nvl(curriList.getString("serviceend_date")),0,8));
					if(!StringUtil.nvl(curriList.getString("chungend_date")).equals(""))
						iChungEnd = Integer.parseInt(StringUtil.substring(StringUtil.nvl(curriList.getString("chungend_date")),0,8));
//System.out.println("===================================>> check11");

					if(currentDate > iServiceEnd) studentDto.setFirstCon("after");		// use flag
					if(currentDate < iServiceStart) studentDto.setFirstCon("before");
					if(currentDate > iServiceEnd) studentDto.setFirstCon("completing");

					//-- 청강기간이 끝났는지의 여부 - 끝났으면 지난강의에서 강의실에 입장하지 못한다.
					if(iChungEnd <= currentDate) studentDto.setChungendYn("N");
					else studentDto.setChungendYn("Y");
//System.out.println("===================================>> check12");

					arrList1.add(studentDto);
//System.out.println("===================================>> check13");

				} // end while

				curriList.close();
				curriArrList.add(arrList1);
			}
			retArrList.add(categoryArrList);
			retArrList.add(curriArrList);
//System.out.println("===================================>> categoryArrList.size() : "+categoryArrList.size());
//System.out.println("===================================>> curriArrList.size() : "+curriArrList.size());
//System.out.println("===================================>> retArrList.size() : "+retArrList.size());
		}catch(Exception e){
			log.debug(e);
		}finally{
			if(cateRs!=null) cateRs.close();
			if(curriList!=null) curriList.close();
		}

		return retArrList;
	}

	public ActionForward stuCurriListIng(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userType = UserBroker.getUserType(request);
		String userId = UserBroker.getUserId(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		CurriSubDAO curriSubDao = new CurriSubDAO();
		RowSet curriList = curriSubDao.getStCurriList(systemCode,userId,"S", 0,null);
		model.put("curriList",curriList);


		new SiteNavigation(MYPAGE).add(request,"학습중인과정").link(model);
		return forward(request, model, "/main/stu_curri_ing.jsp");

	}

	public ActionForward stuCurriHistory(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userType = UserBroker.getUserType(request);
		String userId = UserBroker.getUserId(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		CurriSubDAO curriSubDao = new CurriSubDAO();

		RowSet curriBeforeList = curriSubDao.getStCurriList(systemCode,userId,"EN", 0,null);
		model.put("curriBeforeList",curriBeforeList);

		RowSet historyList = curriSubDao.getStCurriList(systemCode,userId,"CF", 0,null);
		model.put("historyList",historyList);

		new SiteNavigation(MYPAGE).add(request,"수강이력조회").link(model);
		return forward(request, model, "/main/stu_curri_history.jsp");

	}
	/*
	 * by BlackMan End
	 */


	/**
	 * 나의 수강신청 과정을 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward stuCurriBeforeList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userType = UserBroker.getUserType(request);
		String userId = UserBroker.getUserId(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		CurriSubDAO curriSubDao = new CurriSubDAO();
		RowSet curriList = null;
		curriList = curriSubDao.getStCurriList(systemCode,userId,"EN", 0,null);

		model.put("curriList",curriList);

		new SiteNavigation(MYPAGE).add(request,"수강신청현황").link(model);
		return forward(request, model, "/main/stu_curri_before_list.jsp");
	}



	/**
	 * 나의 수강히스토리를 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward stuCurriHistoryList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userType = UserBroker.getUserType(request);
		String userId = UserBroker.getUserId(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		CurriSubDAO curriSubDao = new CurriSubDAO();
		RowSet historyList = null;
		historyList = curriSubDao.getStCurriList(systemCode,userId,"C", 0,null);

		model.put("historyList",historyList);

		new SiteNavigation(MYPAGE).add(request,"수강히스토리").link(model);
		return forward(request, model, "/main/stu_history_list.jsp");
	}

	/**
	 * 교수자 & 조교자
	 * 나의 담당과정 보여준다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward profmangerCurriList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	systemCode	=	UserBroker.getSystemCode(request);
		String	userType 	= 	UserBroker.getUserType(request);
		String	userId 		= 	UserBroker.getUserId(request);
		String	pMode 		= 	StringUtil.nvl(request.getParameter("pMode"),MYPAGE);
		String	pGubun		=	StringUtil.nvl(request.getParameter("pGubun"));
		String	linkStr		=	"나의 담당과정";

		CurriSubDAO curriSubDao = new CurriSubDAO();
		RowSet curriList = null;

		//교수자 진행중과정
		if(userType.equals("P") && pGubun.equals("1")){
			curriList 	= 	curriSubDao.getProfCurriList(systemCode, userId, "ES", 0);
		}//교수자 지난과정
		else if(userType.equals("P") && pGubun.equals("2")){
			curriList 	= 	curriSubDao.getProfCurriList(systemCode, userId, "CF", 0);
			linkStr		=	"나의 지난과정";

		}//과정운영자
		else if(userType.equals("C")) {
			curriList 	= 	curriSubDao.getManagerCurriList(systemCode, userId, "ES", 0);
		}//조교자 진행중과정
		else if(userType.equals("J") && pGubun.equals("1")) {
			curriList	=	curriSubDao.getJogyoCurriList(systemCode, userId, "ES", 0);
		}//조교자 지난과정
		else if(userType.equals("J") && pGubun.equals("2")) {
			curriList	=	curriSubDao.getJogyoCurriList(systemCode, userId, "CF", 0);
			linkStr		=	"나의 지난과정";
		}


		model.put("curriList", curriList);
		model.put("pGubun", pGubun);

		new SiteNavigation(MYPAGE).add(request, linkStr).link(model);
		return forward(request, model, "/main/profmanger_curri_list.jsp");
	}

	/**
	 * SiteMap show
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward siteMapShow(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"), SITEMAP);
		String userId = UserBroker.getUserId(request);


		//new SiteNavigation(pMode).link(model);
		new SiteNavigation(pMode).add(request,"사이트맵").link(model);
		return forward(request, model, "/sitemap/sitemap_main.jsp");
	}
}

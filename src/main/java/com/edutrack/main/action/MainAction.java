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
     * ����� �α��� ó���� ���ش�.
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
        String pGubun = StringUtil.nvl(request.getParameter("pGubun"));		//�˾��α��ο���
        String pMenu = StringUtil.nvl(request.getParameter("pMenu"));		//���ǰ��ǽ��̳� Ŀ�´�Ƽ �����̵� ����
        String pMode = StringUtil.nvl(request.getParameter("pMode"));

        log.debug("����� �α��� ��û "+userId+","+userPw);
		// DAO ����
		UserDAO userDao = new UserDAO();
		DeptSoDAO deptSoDao = new DeptSoDAO();
		UsersDTO userInfo = userDao.getUserInfo(systemCode, userId);
		RowSet rs = deptSoDao.getDeptSoInfo( systemCode, userInfo.getDeptDaecode(), userInfo.getDeptSocode() );

		// ��ü�������ϰ�� ���� ���θ� üũ..
		if(rs.next() ) {
			soCodeUserId = StringUtil.nvl(rs.getString("user_id"));
			soCodeUseYn  = StringUtil.nvl(rs.getString("use_yn"));

			rs.close();
		}

		if(!userInfo.getUserId().equals("")){ // ����ڰ� ������ �������� �Ǵ�.
		log.debug("����� ������ �����Դ�."+userInfo.getUserId());
			if(userInfo.getPasswd().equals(userPw)){ // �н����尡 �´��� �Ǵ��Ѵ�.
				// ����ڰ� ������ �ȵǾ� ���� ���
				if(userInfo.getUseStatus().equals("N") || ( soCodeUserId.equals(userId) && soCodeUseYn.equals("N")) ) return alertAndExit(systemCode, model, "�������� ���� ����� �Դϴ�.", "/User.cmd?cmd=usersLoginShow&pMode=MEMBER",HOME);
				log.debug("����� ���� ���");
				UserConnectDTO conInfo = userDao.getUserConnect(systemCode, userId);

/* �ߺ� �α��� ���� �ּ� ó�� �ؾ� �� �κ� ���� */
/*
                if(conInfo != null){
					// ���� ���� �������� Ȯ���Ѵ�.
					if(conInfo.getLastCon() < CommonUtil.CONCHECKTIME) {
						// ���� � �����ǿ��� ���� �������� �˾��޽����� ����ְ� ������ ������ �ִ� ����ڸ�
						// �α׿��� ��ų���� ����  ó���� ���ش�.
						log.debug("���� ����� �α��� ��");
						String userIp = conInfo.getRemoteIp();
						if(userIp.equals(request.getRemoteAddr())) return confirmAndMove(systemCode, model, "������ ���� ���Ḧ �ϼ̰ų�  �̹� �α��� ���Դϴ�.\\n�������� ����ڸ� ���� ��Ű�� �����Ͻðڽ��ϱ�?", "/Main.cmd?cmd=changeUser&userId="+userId,"/Main.cmd?cmd=mainShow&pMode="+HOME, HOME);

						return confirmAndMove(systemCode, model, "������ ���� ���Ḧ �ϼ̰ų� ����ڰ� "+userIp+"����  �̹� �α��� ���Դϴ�.\\n�������� ����ڸ� ���� ��Ű�� �����Ͻðڽ��ϱ�?", "/Main.cmd?cmd=changeUser&userId="+userId,"/Main.cmd?cmd=mainShow&pMode="+HOME, HOME);
					}
                }
*/
/* �ߺ� �α��� ���� �ּ� ó�� �ؾ� �� �κ� ��
 * com.edutrack.framework.struts.StrutsDispatchAction.java ��
 * 74����  UserConnectDTO conInfo = null; �κ� ���� �ּ� ó��
 */
				log.debug("���ο� ����� ����");
				setUserInfoMem(request, userDao, userInfo, conInfo);

			}else return alertAndExit(systemCode, model, "������� �н����尡 �߸��Ǿ����ϴ�.", "/Main.cmd?cmd=mainShow",HOME);
		}else{
			log.debug("������� ID�� �������� �ʽ��ϴ�.");
			return alertAndExit(systemCode, model, "������� ID�� �������� �ʽ��ϴ�.", "/Main.cmd?cmd=mainShow",HOME);
		}

		int sucessYn = userDao.getUserConnectCnt(systemCode, userId,request.getRemoteAddr());

		if((userInfo.getUserType()).equals("M")) goUrl = "/CurriSub.cmd?cmd=currentMypageList&MENUNO=0";
		else if((userInfo.getUserType()).equals("P") || (userInfo.getUserType()).equals("J")) goUrl = "/Main.cmd?cmd=profmangerCurriList&pGubun=1&MENUNO=0";
		else if((userInfo.getUserType()).equals("C")) goUrl = "/CompanyMasterCourse.cmd?cmd=companyMasterCurriList&MENUNO=0";
		else  goUrl = "/Main.cmd?cmd=stuCurriList&MENUNO=0";

		if(pGubun.equals("popup") && pMenu.equals("MyClass")) {
			return closePopupReload(systemCode, model, "", "", "POPUP", goUrl);		//���� ���ǽ� �����̵�
		}
		else if(pGubun.equals("popup") && pMenu.equals("Community")) {
			return closePopupReload(systemCode, model, "", "", "POPUP", "/Community.cmd?cmd=recCommunity&MENUNO=0");	//Ŀ�´�Ƽ ���� �̵�
		}
		else if(pGubun.equals("popup")) {
			return closePopupReload(systemCode, model, "", "", "POPUP");		//�ش� ������ ���ε�
		}
		else {
			return redirect(goUrl);		//���ο��� �α��� ���� �� ���� ���ǽǷ� �����̵�
		}

//		return redirect("/Main.cmd?cmd=mainShow&pMode="+HOME);
    }

	/**
	 * ���� ����ڰ� �̹� �α����� ���� �� ���� �α��� �� ����ڸ� �ѾƳ��� �α����� �Ѵ�.
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
        log.debug("���� ����ڸ� �α׿��� ��Ű�� ���ο� ����ڸ� �α��� �����ش�."+userId);
		// DAO ����
        String goUrl = "";
		UserDAO userDao = new UserDAO();
		UsersDTO userInfo = userDao.getUserInfo(systemCode, userId);
		UserConnectDTO conInfo = userDao.getUserConnect(systemCode, userId);

		setUserInfoMem(request, userDao, userInfo, conInfo);

		log.debug("����� �α��� ����!!");
		if((userInfo.getUserType()).equals("M")) goUrl = "/CurriSub.cmd?cmd=currentMypageList&MENUNO=0";
		else if((userInfo.getUserType()).equals("P")) goUrl = "Main.cmd?cmd=profmangerCurriList&pGubun=1&MENUNO=0";
		else  goUrl = "Main.cmd?cmd=stuCurriList&MENUNO=0";

		int sucessYn = userDao.getUserConnectCnt(systemCode, userId,request.getRemoteAddr());
		return redirect(goUrl);
    }

	/**
	 * ���� Ȩ���� �̵��� ���ش�.
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
		System.out.println("DbJndiDataSource.java ----------- mainShow() ----------------");

		String 	systemCode	= 	UserBroker.getSystemCode(request);
		String 	userType 	= 	UserBroker.getUserType(request);
		String 	pMode 		= 	StringUtil.nvl(request.getParameter("pMode"),HOME);
		String 	userId 		= 	UserBroker.getUserId(request);
		String	property1	=	StringUtil.nvl(request.getParameter("pProperty1"), "Cyber");

		//공개강좌 5개 불러오기
		CurriSubDAO	curriSubDao	=	new CurriSubDAO();

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
	 * ��ð��� ������ �˾����� ����
	 *
	 * @return
	 * @throws Exception
	 */
	public ActionForward popupOrdinaryContents(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	systemCode 	= 	UserBroker.getSystemCode(request);

		model.put("pCurriCode", StringUtil.nvl(request.getParameter("pCurriCode")));
		model.put("pCurriYear", StringUtil.nvl(request.getParameter("pCurriYear"), "2007"));
		model.put("pCurriTerm", StringUtil.nvl(request.getParameter("pCurriTerm"), "1"));

		new SiteNavigation("HOME").add(request,"��ð���").link(model);
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

		new SiteNavigation("HOME").add(request,"��ð���").link(model);
		return forward(request, model, "/main/popupOrdinaryMenu.jsp");
	}

	public ActionForward popupOrdinaryMain(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String	systemCode 	= 	UserBroker.getSystemCode(request);
		String	pServerPath	=	StringUtil.nvl(request.getParameter("pServerPath"));

		model.put("pServerPath", pServerPath);

		new SiteNavigation("HOME").add(request,"��ð���").link(model);
		return forward(request, model, "/main/popupOrdinaryMain.jsp");
	}

	/**
	 * ����� Ÿ�Ժ� ������������ �����ش�.
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
				return alertAndExit(UserBroker.getSystemCode(request), model, "���� �α����� �ϼž� �մϴ�.", "/Main.cmd?cmd=mainShow&pMode=Home",HOME);
		}
		CurriSubDAO curriSubDao = new CurriSubDAO();
		RowSet curriList = null;
		RowSet curriBeforeList = null;
		if(userType.equals("M")){//-- ������ Ÿ���� ���
			curriList = curriSubDao.currentCurriList(systemCode, "", "", "", "", "", "", 0);
		}
		if(userType.equals("C")){//-- ��������� Ÿ���� ���
			//curriList = curriSubDao.getManagerCurriList(systemCode,userId,"ES", 5);
			curriList = curriSubDao.getManagerCurriList(systemCode,userId,"", 5);
		}
		if(userType.equals("P")){//-- ������ Ÿ���� ���
			//curriList = curriSubDao.getProfCurriList(systemCode,userId,"ES", 5);
			curriList = curriSubDao.getProfCurriList(systemCode,userId,"ES", 5);
		}
		if(userType.equals("S")){//-- �н��� Ÿ���� ���
			// �������� ����
			curriList = curriSubDao.getStCurriList(systemCode,userId,"S", 5,null);
			// ���� ��û���� ����
			curriBeforeList = curriSubDao.getStCurriList(systemCode,userId,"EN", 5,null);
		}

		//---- ��������
		//model.put("pMode", pMode);
		BbsContentsDAO bbsContentsDao = new BbsContentsDAO();
		model.put("noticeList", bbsContentsDao.getBbsContentsPagingList(1,5,10, systemCode,1, userType,CommonUtil.getCurrentDate(),"","",""));

		MessageDAO messageDao = new MessageDAO();
		int	unReadMessageCnt = messageDao.getUnReadCnt(systemCode, userId);
		model.put("unMsgCnt", Integer.toString(unReadMessageCnt));

		//---- ��������
		ScheduleDAO scheduleDao = new ScheduleDAO();
		int now_date = Integer.parseInt(DateTimeUtil.getDate());
		model.put("scheduleList", scheduleDao.getScheduleList(systemCode,userId,now_date));

		//�����з�
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
	 * Ŀ�´�Ƽ ������ ó��
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
				return alertAndExit(UserBroker.getSystemCode(request), model, "���� �α����� �ϼž� �մϴ�.", "/Main.cmd?cmd=mainShow&pMode=Home",HOME);
		}
*/
		CommInfoDAO commInfoDao 		= new CommInfoDAO();
		CommBbsInfoDAO commbbsInfoDao	= new CommBbsInfoDAO();
		CommCategoryDAO commCategoryDao = new CommCategoryDAO();

		RowSet rs  = commInfoDao.getNewCommInfo(systemCode);			//	�ű�Ŀ�´�Ƽ
		RowSet rs1 = commInfoDao.getRecCommInfo(systemCode);			//	��õĿ�´�Ƽ
		RowSet rs2 = commInfoDao.getPridecommInfo(systemCode);			//	Ŀ�´�Ƽ �ڶ�
		RowSet rs3 = commInfoDao.getCommInfoList(systemCode, userId);	//	����Ŀ�´�Ƽ
		RowSet rs4 = commCategoryDao.getCommCategoryList(systemCode);	//	Ŀ�´�Ƽ�з�

		// �ʴ�޽����� ������ �����´�.
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
	 * ����� �α׿��� ó���� �Ѵ�.
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
	 * ����� �α��� ������ ���ǰ� �޸𸮿� ������ ���ش�.
	 * @param request
	 * @param userinfo
	 */
	private void setUserInfoMem(HttpServletRequest request, UserDAO userdao, UsersDTO userinfo, UserConnectDTO coninfo) throws Exception{
		long curTime = System.currentTimeMillis();
		HttpSession session = request.getSession();
		// ����� ���� ������ �������ش�.
		if(coninfo.getUserId().equals("")) userdao.addUserConnect(userinfo.getSystemCode(), userinfo.getUserId(),session.getId(), request.getRemoteAddr());
		else userdao.editUserConnect(userinfo.getSystemCode(), userinfo.getUserId(),session.getId(), request.getRemoteAddr(), "A");

		// ���� ������ ������ �ش�.
        session.setAttribute(Constants.LOGIN_SYSTEMCODE, userinfo.getSystemCode());
        session.setAttribute(Constants.LOGIN_USERID, userinfo.getUserId());
        session.setAttribute(Constants.LOGIN_USERTYPE, userinfo.getUserType());
        session.setAttribute(Constants.LOGIN_USERNAME, userinfo.getUserName());
        session.setAttribute(Constants.LOGIN_SCHOOLYEAR, userinfo.getSchoolYear());

        DeptSoDAO deptSoDao = new DeptSoDAO();
		// ����� ������ ���Ӱ� �����Ѵ�.
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

		// ���ο� ����� ������ �������ش�.
		session.setAttribute("USERINFO",newUserInfo);
	}

	/**
	 * ���мҰ� �޴� ������
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
		String strNavi = "���мҰ�";
		String strJsp = "/info/info_organi_1_1.jsp";
		
		if(pMode.equals("Info")) {
			if(pInfoNum.equals("0") || pInfoNum.equals("1")){
				strNavi = "�λ縻";
				strJsp = "/info/info_organi_1_1.jsp";

				if(pInfoNum2.equals("1")){
					strJsp = "/info/info_organi_1_1.jsp";
				}
				else if(pInfoNum2.equals("2")){
					strJsp = "/info/info_organi_1_2.jsp";
				}
			}
			else if(pInfoNum.equals("2")){
				strNavi = "�������� ����";
				strJsp = "/info/info_organi_2_1.jsp";
				
				if(pInfoNum2.equals("1")){
					strJsp = "/info/info_organi_2_1.jsp";
				}
				else if(pInfoNum2.equals("2")){
					strJsp = "/info/info_organi_2_2.jsp";
				}
				else if(pInfoNum2.equals("3")){
					//���� 2000�⵵�� �˾�â 
					strJsp = "/info/info_organi_2_3.jsp";
				}
			}
			else if(pInfoNum.equals("3")){
				strNavi = "�������� ����";
				strJsp = "/info/info_organi_3_1.jsp";
				
				if(pInfoNum2.equals("1")){
					strJsp = "/info/info_organi_3_1.jsp";
				}
				else if(pInfoNum2.equals("2")){
					strJsp = "/info/info_organi_3_2.jsp";
				}
			}
			else if(pInfoNum.equals("4")){
				strNavi = "������ �Ұ�";
				strJsp = "/info/info_organi_4_1.jsp";
				
				if(pInfoNum2.equals("1")){
					strJsp = "/info/info_organi_4_1.jsp";
				}
				else if(pInfoNum2.equals("2")){
					strJsp = "/info/info_organi_4_2.jsp";
				}
			}
			else if(pInfoNum.equals("5")){
				strNavi = "������ ������ �ⱸ";
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
				strNavi = "ã�ƿ��� ��";
				strJsp = "/info/use_info3.jsp";
			}
		}
		else if(pMode.equals("Help")) {
			strNavi = "�̿�ȳ�";
			strJsp = "/info/use_help.jsp";
		}



		new SiteNavigation(pMode).add(request,strNavi).link(model);
		return forward(request, model, strJsp);
	}
	
	/**
	 * ���оȳ�
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
		String strNavi = "���оȳ�";
		String strJsp = "/info/enter_organi_1_1.jsp";
		

		if(pInfoNum.equals("0") || pInfoNum.equals("1")){
			strNavi = "���оȳ�";
			strJsp = "/info/enter_organi_1_1.jsp";
		}
		else if(pInfoNum.equals("2")){
			strNavi = "�����ȳ�";
			strJsp = "/info/enter_organi_1_2.jsp";
		}
		else if(pInfoNum.equals("3")){
			return redirect("/Schedule.cmd?cmd=haksaSchedule&MENUNO=0&pMode=Enter");
		}
		
		new SiteNavigation(pMode).add(request,strNavi).link(model);
		return forward(request, model, strJsp);
	}

	
	/**
	 * �л���������
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
		String strNavi = "���п��� ��������";
		String strJsp = "/info/student_center_1.jsp";
		
		if(pInfoNum.equals("11") || pInfoNum2.equals("1")){
			strNavi = "���п��� ��������";
			strJsp = "/info/student_center_1.jsp";
		}
		else if(pInfoNum2.equals("2")){
			strNavi = "���л�Ȱ �� �н����";
			strJsp = "/info/student_center_2.jsp";
		}
		else if(pInfoNum2.equals("3")){
			strNavi = "����ȳ�";
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
	 * ���� ���������� �����ش�. (�н��� �α����� ����)
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

		if(("1").equals(pGubun)) enrollStatus = "S"; 		// �������ΰ���
		else if(("2").equals(pGubun)) enrollStatus = "EN";	// ������û���� ����
		else if(("3").equals(pGubun)) enrollStatus = "CF";	// �������

		CurriCategoryDAO curriCategoryDao = new CurriCategoryDAO();
		CurriCategoryDTO curriCategoryDto = new CurriCategoryDTO();
		ArrayList arrList = new ArrayList();
		//RowSet cateRs = curriCategoryDao.getCategoryList1(systemCode);
		RowSet cateRs = curriCategoryDao.getCategoryCntList1(systemCode, userId);
		CurriSubDAO curriSubDao = new CurriSubDAO();
		//�л��� �����ϴ� �� ���� ����
		int rowLength = 0;
		rowLength = curriSubDao.getTotalCurriCount(systemCode, enrollStatus, userId);
		model.put("rowLength", String.valueOf(rowLength));
		log.debug("�л��� �����ϴ� �� ���� ����; rowLength : "+rowLength);

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
		
		//-- ������û���� ���� ���� ����Ʈ
		Map		paramMap	=	new HashMap();
		paramMap.put("pPageGubun", "PR");	//process
		paramMap.put("pUserId", userId);
		CurriCourseEnrollDAO	enrollDao	=	new CurriCourseEnrollDAO();
		RowSet	rs	=	enrollDao.getCurriCourseEnrollList(systemCode, "", schoolYear, "", "Cyber", "R", paramMap);
		model.put("NoCurriList", rs);

		String naviMsg = "";
		if(("1").equals(pGubun)) naviMsg ="�������ΰ���";
		else if(("2").equals(pGubun)) naviMsg ="������û��Ȳ";
		else if(("3").equals(pGubun)) naviMsg ="��������";


		new SiteNavigation(MYPAGE).add(request,naviMsg).link(model);
		return forward(request, model, "/main/stu_curri_list.jsp");
   }

	/**
	 * ���� ���������� �����ش�. (�н��� �α����� ����)
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

		ArrayList curriArrList = new ArrayList();		// ī�װ�, ����
		ArrayList retArrList  = new ArrayList();		// ���� arrayList

		RowSet cateRs = curriCategoryDao.getCategoryList1(systemCode);

		RowSet curriList = null;
		String enrollStatus = "";

		if(("1").equals(pGubun)) enrollStatus = "S"; 		// �������ΰ���
		else if(("2").equals(pGubun)) enrollStatus = "EN";	// ������û���� ����
		else if(("3").equals(pGubun)) enrollStatus = "CF";	// �������

		int iServiceStart = 0;
		int iServiceEnd   = 0;
		int iChungEnd   = 0;
		int currentDate = Integer.parseInt(StringUtil.substring(CommonUtil.getCurrentDate(),0,8));

		try{
			ArrayList categoryArrList = new ArrayList();		// ī�װ�
			ArrayList arrList1 = new ArrayList();
			int i = 0;

			while(cateRs.next()){

				arrList1 = new ArrayList();		// ����
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

					studentDto.setContentsCnt(curriList.getInt("contents_cnt"));			//��ü ������ ����
					studentDto.setShowContentsCnt(curriList.getInt("show_contents_cnt"));	//������ ������ ����
					studentDto.setCurriPercent(curriList.getDouble("pro_rate"));			//������
//System.out.println("===================================>> check07");
					studentDto.setCateName(curriList.getString("cate_name"));				//��з� ����
					studentDto.setServiceStart(curriList.getString("service_start"));		//��ð��� ����(��)
					studentDto.setCurriInfo(curriList.getString("curri_info"));				//��������
//System.out.println("===================================>> check08");
					
					studentDto.setOpenCnt(curriList.getInt("open_cnt"));					//�������� ������ Ƚ��
					studentDto.setOffTotalTime(curriList.getInt("off_total_time"));			//��ü������ð�
//System.out.println("===================================>> check09");
					studentDto.setOffShowTime(curriList.getInt("off_show_time"));			//�����ѵ�����ð�
					studentDto.setOffContentsCnt(curriList.getInt("off_contents_cnt"));		//�ѿ������μ��� ����
					studentDto.setOffAttendCnt(curriList.getInt("off_attend_cnt"));			//�������� �⼮ Ƚ��
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
					
					//-- û���Ⱓ�� ���������� ���� - �������� �������ǿ��� ���ǽǿ� �������� ���Ѵ�.
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


		new SiteNavigation(MYPAGE).add(request,"�н����ΰ���").link(model);
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

		new SiteNavigation(MYPAGE).add(request,"�����̷���ȸ").link(model);
		return forward(request, model, "/main/stu_curri_history.jsp");

	}
	/*
	 * by BlackMan End
	 */


	/**
	 * ���� ������û ������ �����ش�.
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

		new SiteNavigation(MYPAGE).add(request,"������û��Ȳ").link(model);
		return forward(request, model, "/main/stu_curri_before_list.jsp");
    }



	/**
	 * ���� ���������丮�� �����ش�.
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

		new SiteNavigation(MYPAGE).add(request,"���������丮").link(model);
		return forward(request, model, "/main/stu_history_list.jsp");
    }

	/**
	 * ������ & ������
	 * ���� ������ �����ش�.
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
		String	linkStr		=	"���� ������";

		CurriSubDAO curriSubDao = new CurriSubDAO();
		RowSet curriList = null;

		//������ �����߰���
		if(userType.equals("P") && pGubun.equals("1")){
			curriList 	= 	curriSubDao.getProfCurriList(systemCode, userId, "ES", 0);
		}//������ ��������
		else if(userType.equals("P") && pGubun.equals("2")){
			curriList 	= 	curriSubDao.getProfCurriList(systemCode, userId, "CF", 0);
			linkStr		=	"���� ��������";

		}//�������
		else if(userType.equals("C")) {
			curriList 	= 	curriSubDao.getManagerCurriList(systemCode, userId, "ES", 0);
		}//������ �����߰���
		else if(userType.equals("J") && pGubun.equals("1")) {
			curriList	=	curriSubDao.getJogyoCurriList(systemCode, userId, "ES", 0);
		}//������ ��������
		else if(userType.equals("J") && pGubun.equals("2")) {
			curriList	=	curriSubDao.getJogyoCurriList(systemCode, userId, "CF", 0);
			linkStr		=	"���� ��������";
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
		new SiteNavigation(pMode).add(request,"����Ʈ��").link(model);
		return forward(request, model, "/sitemap/sitemap_main.jsp");
    }
}

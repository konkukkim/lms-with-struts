/*
 * Created on 2004. 11. 18.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.progauthor.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.progauthor.dao.ProgMenuDAO;
import com.edutrack.progauthor.dto.ProgMenuDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProgMenuAction extends StrutsDispatchAction{

	/**
	 *
	 */
	public ProgMenuAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActionForward getProgMenuList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userType = UserBroker.getUserType(request);
        String pMode = StringUtil.nvl(request.getParameter("pMode"), "MyPage");
        String pWorkGubun = StringUtil.nvl(request.getParameter("pWorkGubun"),"10");
		String pFields = StringUtil.nvl(request.getParameter("pFields"));
		String pValue = StringUtil.nvl(request.getParameter("pValue"));
		String pMenuUser = StringUtil.nvl(request.getParameter("pMenuUser"),userType);

		//int curPage = 1;
		//if(request.getParameter("curPage") != null) curPage = Integer.parseInt(request.getParameter("curPage"));

		ProgMenuDAO MenuDao = new ProgMenuDAO();

		model.put("pWorkGubun",pWorkGubun);
		model.put("pMenuUser",pMenuUser);
		model.put("pFields",pFields);
		model.put("pValue",pValue);

		new SiteNavigation(pMode).add(request,"메뉴권한관리").link(model);
		return forward(request, model,"/progauthor/progMenuList.jsp");
	}

	/**
	 *
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ArrayList progMenuListAuto(String pWorkGubun, String pMenuUser, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userType = UserBroker.getUserType(request);
        String pMode = StringUtil.nvl(request.getParameter("pMode"), "MyPage");

		ProgMenuDAO MenuDao = new ProgMenuDAO();
		ArrayList menuList = MenuDao.progMenuList(systemCode, pWorkGubun, pMenuUser, null, null, null );

		return menuList;
	}


	/**
	 * 메뉴 입력/수정 폼..
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward progMenuWrite(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception {
		String userType = UserBroker.getUserType(request);
        String systemCode = UserBroker.getSystemCode(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"), "MyPage");
		String pGUBUN = StringUtil.nvl(request.getParameter("pGUBUN"));
        String pWorkGubun = StringUtil.nvl(request.getParameter("pWorkGubun"));
        String pMenuCode = StringUtil.nvl(request.getParameter("pMenuCode"));
        String pMenuUser = StringUtil.nvl(request.getParameter("pMenuUser"),userType);

		String navigationMsg = "";
		String navigationWhere = pMode;
		//ProgMenuDTO MenuInfo = null;

		ProgMenuDAO MenuDao = new ProgMenuDAO();

		ArrayList aMenuList = new ArrayList();
		if(pGUBUN.equals("write")) {
			navigationMsg = "메뉴권한등록";
		} else if(pGUBUN.equals("edit")) {
			navigationMsg = "메뉴권한수정";
			aMenuList = MenuDao.progMenuList(systemCode, pWorkGubun, pMenuUser, pMenuCode, null, null );
		}

		model.put("pGUBUN",pGUBUN);
		model.put("pWorkGubun",pWorkGubun);
		model.put("pMenuUser",pMenuUser);
		model.put("pMenuList",aMenuList);
		model.put("pMenuMaster",MenuDao.getProgMenuMasterList(systemCode, pWorkGubun, pMenuUser)); // 1단계  메뉴를 가져온다.

		new SiteNavigation(navigationWhere).add(request,navigationMsg).link(model);
		return forward(request, model,"/progauthor/progMenuWrite.jsp");
	}


	/**
	 * 상위 메뉴 목록을 가져온다...Ajax
	 * @param pWorkGubun
	 * @param pMenuUser
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ArrayList progMenuMasterList(String pWorkGubun, String pMenuUser, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		ProgMenuDAO MenuDao = new ProgMenuDAO();

		ArrayList aList = MenuDao.getProgMenuMasterList(systemCode, pWorkGubun, pMenuUser);

		return 	aList; // 1단계 작은 메뉴를 가져온다.
	}


	/**
	 * 메뉴정보를 저장한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward progMenuRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"), "MyPage");

		String pGUBUN = StringUtil.nvl(request.getParameter("pGUBUN"));
		String userId = UserBroker.getUserId(request);
		String pWorkGubun = StringUtil.nvl(request.getParameter("pWorkGubun"));
		String pMenuCode   = StringUtil.nvl(request.getParameter("pMenuCode"));
		String callBackMethod   = StringUtil.nvl(request.getParameter("callBackMethod"));

		//상위메뉴가 있으면  상위메뉴코드, 없으면 0.....현재 3단계는 생각하지 않음..
		String pMenuMaster = StringUtil.nvl(request.getParameter("pMenuMaster"));
		String pMenuUser   = StringUtil.nvl(request.getParameter("pMenuUser"));

		ProgMenuDAO MenuDao = new ProgMenuDAO();
		ProgMenuDTO MenuDto = new ProgMenuDTO();

		MenuDto.setSystemCode(systemCode);
		MenuDto.setWorkGubun(pWorkGubun);
		MenuDto.setMenuUser(pMenuUser);
		MenuDto.setMenuCode(pMenuCode);
		MenuDto.setMenuMaster(pMenuMaster);
		MenuDto.setMenuUrl(StringUtil.nvl(request.getParameter("pMenuUrl")));
		MenuDto.setMenuName(StringUtil.nvl(request.getParameter("pMenuName")));
		MenuDto.setMenuComment(StringUtil.nvl(request.getParameter("pMenuComment")));
		MenuDto.setUseYn (StringUtil.nvl(request.getParameter("pUseYn")));
		MenuDto.setRRight(StringUtil.nvl(request.getParameter("pRRight")));
		MenuDto.setCRight(StringUtil.nvl(request.getParameter("pCRight")));
		MenuDto.setURight(StringUtil.nvl(request.getParameter("pURight")));
		MenuDto.setDRight(StringUtil.nvl(request.getParameter("pDRight")));
		MenuDto.setAddUrl1(StringUtil.nvl(request.getParameter("pAddUrl1")));
		MenuDto.setAddUrl2(StringUtil.nvl(request.getParameter("pAddUrl2")));
		MenuDto.setAddUrl3(StringUtil.nvl(request.getParameter("pAddUrl3")));
		MenuDto.setAddUrl4(StringUtil.nvl(request.getParameter("pAddUrl4")));
		MenuDto.setAddUrl5(StringUtil.nvl(request.getParameter("pAddUrl5")));

		String msg = "";
		int retVal = 0;

		try{
			if(pGUBUN.equals("write")) {
				MenuDto.setRegId(userId);
				retVal= MenuDao.addProgMenuInfo(MenuDto);
				msg = "메뉴를 추가하는데 ";
			} else if(pGUBUN.equals("edit")){
				MenuDto.setModId(userId);
				retVal= MenuDao.editProgMenuInfo(MenuDto);
				msg = "메뉴를 수정하는데 ";
			}

			if(retVal>0) msg +="성공하였습니다.";
			else msg +="실패하였습니다.";

		}catch(Exception e){
			log.debug(e);
		}
		return alertAndExit(systemCode, model, msg, CALLBACKURL+callBackMethod, pMode);
	}


	public String progMenuDelete(String pWorkGubun, String pMenuUser, String pMenuCode, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"), "MyPage");

		//String pWorkGubun = StringUtil.nvl(request.getParameter("pWorkGubun"));
		//String pMenuCode   = StringUtil.nvl(request.getParameter("pMenuCode"));

		ProgMenuDAO MenuDao = new ProgMenuDAO();
		ProgMenuDTO MenuDto = new ProgMenuDTO();

		MenuDto.setSystemCode(systemCode);
		MenuDto.setWorkGubun(pWorkGubun);
		MenuDto.setMenuUser(pMenuUser);
		MenuDto.setMenuCode(pMenuCode);

		String msg = "";
		try{
			if(MenuDao.deleteProgMenuInfo(MenuDto)<=0) throw new Exception("Db Error");
			msg = "메뉴를 삭제하는데 성공하였습니다.";
		}catch(Exception e){
			log.debug(e);
			msg = "메뉴를 삭제하는데 실패하였습니다.["+ e +"]";
		}
		//return alertAndExit(systemCode, model, msg, "", pMode);
		return msg;
	}


	/**
	 * 메뉴 순서를 저장한다..Ajax
	 * @param pWorkGubun
	 * @param pMenuUser
	 * @param pMenuCode
	 * @param menuCnt
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public String progMenuOrderRegist(String pWorkGubun, String pMenuUser, String pMenuCode, int menuCnt, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"), "MyPage");
		String userId = UserBroker.getUserId(request);

		String[] menuCode = pMenuCode.split("_item");
		String msg = "";
		ProgMenuDAO MenuDao = new ProgMenuDAO();
		boolean retVal = false;

		try{

			if(!MenuDao.editMenuOrderRegist(systemCode, pWorkGubun, pMenuUser, menuCode, userId, menuCnt)) throw new Exception("DB error");
			else msg = "메뉴 순서 저장에 성공하였습니다.";

		}catch(Exception e){
			log.error(e);
			msg = "메뉴  순서 저장에 실패하였습니다.";
		}

		return msg;
	}


	/**
	 * 메뉴를 복사한다..Ajax
	 * @param pWorkGubun
	 * @param pMenuUser
	 * @param pMenuCode
	 * @param menuCnt
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public String progMenuCopy(String pWorkGubun, String pFMenuUser, String pTMenuUser, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"), "MyPage");
		String userId = UserBroker.getUserId(request);

		ProgMenuDAO MenuDao = new ProgMenuDAO();
		int retVal = 0;
		String msg = "";

		try{

			retVal = MenuDao.progMenuCoopy(systemCode, pWorkGubun, pFMenuUser, pTMenuUser, userId);

			if(retVal<=0) throw new Exception("DB error");
			else msg = "메뉴 복사에 성공하였습니다.";

		}catch(Exception e){
			log.error(e);
			msg = "메뉴  순서 저장에 실패하였습니다. ";
			if(retVal==0) msg += "\n\n복사할 메뉴가 존재하지 않습니다.";
		}
		return msg;
	}
}

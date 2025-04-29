package com.edutrack.config.action;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.config.dao.ConfigSubDAO;
import com.edutrack.config.dao.ConfigTopDAO;
import com.edutrack.config.dto.ConfigSubDTO;
import com.edutrack.config.dto.ConfigTopDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/*
 * Created on 2004. 10. 06.
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
public class ConfigSubAction extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	/**
	 * 시스템설정 수정 폼을 띄운다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward configSubEditForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pConfigType = StringUtil.nvl(request.getParameter("pConfigType"));
		String pConfigCode = StringUtil.nvl(request.getParameter("pConfigCode"));

		ConfigSubDAO configSubDao = new ConfigSubDAO();
		ConfigSubDTO configSubDto = new ConfigSubDTO();

		RowSet rs = configSubDao.getConfigSub(systemCode,pConfigType,pConfigCode);
		model.put("rs", rs);
		model.put("pConfigType", pConfigType);
		model.put("pConfigCode", pConfigCode);
		new SiteNavigation(MYPAGE).add(request,"시스템설정관리").link(model);
        return forward(request, model, "/config/configSubEditForm.jsp");
	}

	/**
	 * 시스템설정 정보를 수정 한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward confgiSubRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("=====================confgiSubRegist start");
		String systemCode = UserBroker.getSystemCode(request);
		String modId = UserBroker.getUserId(request);
		String pConfigType = StringUtil.nvl(request.getParameter("pConfigType"));
		String pConfigCode = StringUtil.nvl(request.getParameter("pConfigCode"));
		String pTypeName = StringUtil.nvl(request.getParameter("pTypeName"));
		String pConfigValue = StringUtil.nvl(request.getParameter("pConfigValue"));

		ConfigTopDAO configTopDao = new ConfigTopDAO();
		ConfigSubDAO configSubDao = new ConfigSubDAO();
		ConfigSubDTO configSubDto = new ConfigSubDTO();

		configSubDto.setSystemCode(systemCode);
		configSubDto.setConfigType(pConfigType);
		configSubDto.setConfigCode(pConfigCode);
		configSubDto.setTypeName(pTypeName);
		configSubDto.setConfigValue(pConfigValue);
		configSubDto.setModId(modId);

		int retVal = 0;
		String msg = "";
		String returnUrl = "/ConfigSub.cmd?cmd=configSubList&pConfigType="+pConfigType;

		retVal = configSubDao.editConfigSub(configSubDto);
		if(retVal > 0){
			msg = "적용완료";
		}else{
			returnUrl = "/ConfigSub.cmd?cmd=configSubEditForm&pConfigType="+pConfigType+"&pConfigCode="+pConfigCode;
			msg = "적용오류 다시 진행해 주세요";
		}
		log.debug("retVal ==>"+retVal);

		new SiteNavigation(MYPAGE).add(request,"시스템설정관리").link(model);
		log.debug("======================confgiSubRegist end");
        return notifyAndExit(systemCode, model, msg, returnUrl, MYPAGE);
	}

	/**
	 * 시스템설정 리스트를 가져온다.(페이징 없음)
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward configSubList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String pConfigType = StringUtil.nvl(request.getParameter("pConfigType"));
		if(pConfigType.equals("")) pConfigType = "310";
		ConfigTopDAO configTopDao = new ConfigTopDAO();
		ConfigSubDAO configSubDao = new ConfigSubDAO();
		ConfigSubDTO configSubDto = new ConfigSubDTO();
		ConfigTopDTO configTopDto = new ConfigTopDTO();
		RowSet rsTop = configTopDao.getConfigTopList(systemCode);
		RowSet rsSub = configSubDao.getConfigSubList(systemCode,pConfigType);
		model.put("rsTop", rsTop);
		model.put("rsSub", rsSub);
		model.put("pConfigType", pConfigType);
		new SiteNavigation(MYPAGE).add(request,"시스템설정관리").link(model);
        return forward(request, model, "/config/configSubList.jsp");
	}

	/**
	 * webFtp 접속 테스트
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward webFtpTest(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
        String year = "2005";
        String term = "1";
        String pCourseId = "Cyber0001";
        String 	pSERVER 		=  "0";
        String 	os_type 		= StringUtil.nvl(CommonUtil.SERVERTYPE);
        model.put("pSERVER", pSERVER);
        model.put("year", year);
        model.put("term", term);
        model.put("pCourseId", pCourseId);
        model.put("os_type", os_type);
        return forward(request, model, "/config/webFtpTest.jsp");
    }

	/**
	 * webFtpConfigTest
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward webFtpConfigTest(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{

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
        return forward(request, model, "/config/webFtpConfigTest.jsp");
    }
}

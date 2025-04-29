package com.edutrack.framework.struts;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.util.MailUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.user.dao.UserDAO;

/*********************************************************
 * 프로그램 : StrutsDispatchAction.java
 * 모 듈 명 : 
 * 설    명 : 
 * 테 이 블 :
 * 작 성 자 : chorongjang
 * 작 성 일 : 2004. 7. 14.
 * 수 정 일 :
 * 수정사항 : 
 *********************************************************/
public class StrutsDispatchAction extends DispatchAction {
    protected static Configuration config = ConfigurationFactory.getInstance().getConfiguration();
    protected static String defaultPagePath = config.getString("framework.system.jsp_path");
    protected static String errorPagePath = config.getString("framework.system.error_page_path");
    protected static String HOME = "Home";
    protected static String INFO = "Info";
    protected static String HELP = "Help";
    protected static String MYPAGE = "MyPage";
    protected static String ENTER = "Enter";
    protected static String NEWS = "News";
    protected static String STUDENTCENTER = "StudentCenter";
    protected static String COMMUNITY = "Community";
    protected static String COMMSUB = "CommSub";
    protected static String LECTURE = "Lecture";
    protected static String MEMBER = "Member"; 
    protected static String SITEMAP = "SiteMap"; 
	protected static String CALLBACKURL = "/jsp/1/common/common_callback.jsp?callBackMethod=";
	
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            Map model) throws Exception {

		// Identify the request parameter containing the method name
		String parameter = mapping.getParameter();
		if (parameter == null) {
		String message =
		messages.getMessage("dispatch.handler", mapping.getPath());
		log.error(message);
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
		          message);
		return (null);
		}

	    // 시스템 코드를 가져온다.		
		String systemCode = UserBroker.getSystemCode(request);

		// 사용자 아이디를 가져온다. UserBroker에서 일단 빼오고 없을 경우 request의 파라미터를 뒤져서 아이디를 가져온다.		
		String userId = UserBroker.getUserId(request); 
	    UserDAO userDao = new UserDAO();
		HttpSession session = request.getSession();
		
/* 중복 로그인 허용시 주석 처리 해야 할 부분 시작 */		
/*		
        UserConnectDTO conInfo = null;
        
        if(!systemCode.equals("") && !userId.equals(""))
        	conInfo = userDao.getUserConnect(systemCode, userId);
		UserMemDTO userInfo =  UserBroker.getUserInfo(request);
		
        if(userInfo != null && conInfo != null && !userInfo.sessionId.equals(conInfo.getSessionId())) {
	        session.setAttribute(Constants.LOGIN_USERID, "");  
	        session.setAttribute(Constants.LOGIN_USERTYPE, "");
	        session.setAttribute(Constants.LOGIN_USERNAME,"");
	        session.removeAttribute("USERINFO");
	        //return alertAndExit(systemCode,model,"다른곳에서 로그인을 하여 로그오프 되었습니다.","/Main.cmd?cmd=mainShow&pMode="+HOME,HOME); 
		}
*/        
/* 중복 로그인 허용시 주석 처리 해야 할 부분 끝
 * com.edutrack.main.action.MainAction.java
 * 95 라인 if(conInfo != null){ 부터 주석 처리
 * */
		
		// 사용자 메모리를 가져온다.
		if(!systemCode.equals("") && !userId.equals(""))
           userDao.editUserConnect(systemCode, userId,session.getId(), request.getRemoteAddr(),"C");		

		
		// 권한체크.....uri 경로를 가져온다.........add by HerSunJa
		int iMenuNoIndexOf = request.getQueryString().indexOf("&MENUNO") ;
		int iPmodeIndexOf = request.getQueryString().indexOf("&pMode") ; // url 을 direct 로 치고 들어올경우..
	    String pMenuUrl = request.getServletPath() +"?"+ request.getQueryString();
	    String pMenuUrl2 = request.getServletPath() +"?cmd="+ request.getParameter("cmd");
	    String pMode = request.getParameter("pMode");
	    
	    if(iMenuNoIndexOf>0) 
	    { 
	    	pMenuUrl = request.getServletPath() +"?"+ request.getQueryString().substring(0, iMenuNoIndexOf) ; 
	    }
	    else { 
	    	pMenuUrl = request.getServletPath() +"?"+ request.getQueryString() ; 
	    }
	    
	    //if(iMenuNoIndexOf>0 || (HOME).equals(StringUtil.nvl(pMode)) ){
	    // 홈일경우에는 열린마당의 게시판의 권한때문에 제함...
	    if(iMenuNoIndexOf>0 ){
		    session.setAttribute("MENUURL",pMenuUrl); 
	    }

	    if(iPmodeIndexOf <=0  || (iPmodeIndexOf>0 && (  ("Info").equals(request.getParameter("pMode") )
						    		                  ||("Enter").equals(request.getParameter("pMode"))
						    		                  ||("News").equals(request.getParameter("pMode") )
						    		            	  ||("Help").equals(request.getParameter("pMode") )
						    		            	 ))){
	        session.setAttribute("MENUURL","");
	    }
	    // Page Loding 시에 url 권한 체크...
	    // url 을 조작하여 들어올경우에 대한 권한 체크..
	    // 임시로 pmode 가 home 일때 권한을 풀어줌......HerSunja for 시연
		if( !(HOME).equals(StringUtil.nvl(pMode)) && !CommonUtil.getAuthorCheckPageLoding(request, pMenuUrl2)){
			String sMsg = "";
			
			if(userId.equals("")) sMsg = "비정상 로그아웃 되었거나 로그인을 하지 않으셨습니다.<br><br> 로그인후 다시 진행하여 주십시요";
			else sMsg = "접근 권한이 허용되지 않은 페이지입니다.<br><br>관리자에게 문의 후 다시 진행하여 주십시요.";

			return notifyAndExit(systemCode, model, sMsg, "Main.cmd?cmd=mainShow&pMode="+HOME, HOME);
		}

		
		// Identify the method name to be dispatched to.
		// dispatchMethod() will call unspecified() if name is null
		String name = request.getParameter(parameter);
		
		// Invoke the named method, and return the result
		return dispatchMethod(mapping, form, request, response, model, name);
	}

    /**
     *  주어진 주소로 Redirect를 해준다.
     * @param mapname
     * @return
     */
    protected ActionForward redirect(String url){
        ActionForward forward = new ActionForward();
        forward.setRedirect(true);
        forward.setPath(url);
        
        return forward;
    }

    /**
     * Struts Config에서 맵핑한 주소로 Redirect를 해준다.
     * @param actionMapping
     * @param mapname
     * @return 
     */
    protected ActionForward redirect(HttpServletRequest request, String page){
        ActionForward forward = new ActionForward();
        forward.setPath(defaultPagePath+UserBroker.getSystemCode(request)+page);
        forward.setRedirect(true);
        
        return forward;
    }

    protected ActionForward forward(HttpServletRequest request, Map model, String page){
        ActionForward forward = new ActionForward();
    	forward.setPath(defaultPagePath+UserBroker.getSystemCode(request)+page);
        return forward;
    }

    protected ActionForward excelDown(HttpServletRequest request,HttpServletResponse response,Map model, String page,String filename) throws Exception{
        ActionForward forward = new ActionForward();
    	forward.setPath(defaultPagePath+UserBroker.getSystemCode(request)+page);
    	
    	log.debug("Excel Down Encoding =>"+request.getCharacterEncoding());
    	
		response.reset();
		response.setContentType("application/x-msdownload;charset="+request.getCharacterEncoding());
		response.setHeader("Content-Disposition","attachment; filename="+filename); 
		response.setHeader("Cache-Control", "no-cache");

		return forward;
    }
    
    protected void sendMail(String addr, String username, String subject, String body){
    	MailUtil.sendMail(addr, username, subject,body);
    }
    
    /**
     * 해당 주소로 Forward 시켜준다.
     * @param url
     * @return
     */
  	protected ActionForward forward(String url) {
  	      ActionForward forward = new ActionForward();
  	      forward.setPath(url);
  	      return forward;	    
  	}
 
 	public ActionForward notifyAndExit(String systemcode, Map model, String msg,String pmode) {
  	    return notifyAndExit(systemcode, model, msg, "",pmode);
  	}
  	
  	public ActionForward notifyAndExit(String systemcode, Map model, String msg, String returnurl,String pmode) {
		ActionForward forward = createMessageForward(systemcode, model, msg, returnurl, "message", "false", "false", "false","",pmode);

		return forward;
  	}

  	public ActionForward notifyAndClose(String systemcode, Map model, String msg,String pmode) {
		ActionForward forward = createMessageForward(systemcode, model, msg, "", "message", "false", "true", "false","",pmode);
	    
		return forward;
  	}

  	public ActionForward errorAndExit(String systemcode, Map model, String msg, String returnurl,String pmode) {
		ActionForward forward = createMessageForward(systemcode, model, msg, returnurl, "message", "false", "false", "false","",pmode);
	    
		return forward;	    
  	}

  	public ActionForward errorAndExit(String systemcode, Map model, Exception e, String msg, String returnurl,String pmode) {
  		return createMessageForward(systemcode, model, msg, returnurl, "message", "false", "false", "false",e.getMessage(),pmode);
  	}
  		
  	public ActionForward alertAndExit(String systemcode, Map model, String msg, String url,String pmode) {
  		return createMessageForward(systemcode, model, msg, url, "history", "true", "true", "false","",pmode);
  	}

  	public ActionForward confirmAndMove(String systemcode, Map model, String msg, String okurl,String nourl, String pmode) {
  		return createMessageForward(systemcode, model, msg, okurl, "confirm", "false", "false", "false",nourl,"",pmode);
  	}
  	public ActionForward alertPopupCloseResponse(String systemcode, Map model, String msg,String pmode){
  		return createMessageForward(systemcode, model, msg, "", "popup", "true", "true", "false", "",pmode);
  	}	

  	public ActionForward alertPopupClose(String systemcode, Map model, String msg){
  		return createMessageForward(systemcode, model, msg, "", "", "true", "false", "false","","POPUP");
  	}	
	
  	/**
  	 * 
  	 * @param systemcode
  	 * @param model
  	 * @param msg
  	 * @param param : P -> parent.location.reload();, 그외는 opener.location.reload(); 를 호출함..
  	 * @param pmode
  	 * @return
  	 */
  	public ActionForward closePopupReload(String systemcode, Map model, String msg,String param,String pmode){  	        
 	      return closePopupReload(systemcode, model, msg,param,pmode, "");	     
 	}	
  	
  	public ActionForward closePopupReload(String systemcode, Map model, String msg,String param,String pmode, String returnurl){  	        
  	     ActionForward forward = createMessageForward(systemcode, model, msg, returnurl, "popup", "true", "true", "true", "true",param,pmode);

  	      return forward;	     
  	}	

  	public ActionForward notifyCloseReload(String systemcode, Map model, String msg,String pmode) {
  	    //return notifyCloseReload(systemcode, model,msg,"O",pmode);
  		return notifyCloseReload(systemcode, model,msg,"O","POPUP");
  	}
  	
  	public ActionForward notifyCloseReload(String systemcode, Map model, String msg, String param,String pmode) {
		ActionForward forward = createMessageForward(systemcode, model, msg, "", "message", "false", "false", "false","",param,pmode);
	    
		return forward;
  	}
  	public ActionForward notifyCloseForward(String systemcode, Map model, String msg, String param,String pmode,String returnUrl) {
		ActionForward forward = createMessageForward(systemcode, model, msg, returnUrl, "message", "false", "false", "false","",param,"POPUP");
	    
		return forward;
  	}
  	
    public ActionForward createMessageForward(String systemcode, Map model, String msg, String returnurl, String alertmode, String alert, String close, String reload, String exception,String pmode) {
    	return createMessageForward(systemcode, model, msg, returnurl, alertmode, alert, close, reload, exception,"",pmode);
    }
    
  	/**
     * @param path
     * @param model
     * @param msg
     * @param popup
     * @param msgtype
     * @param alert
     * @param close
     * @param reload
     * @return
     */                            
    private ActionForward createMessageForward(String systemcode, Map model, String msg, String returnurl, String alertmode, String alert, String close, String reload, String exception,String param,String pmode) {
        // 메시지 셋팅
		model.put("ALERT_MODE",alertmode);
		model.put("ALERT",alert);	
		model.put("MSG",StringUtil.outDataConverter(msg));
		model.put("CLOSE",close);
		model.put("RELOAD", reload);
		model.put("URL", returnurl);
		model.put("EXCEPTION", exception);
		model.put("PARAM", param);
		model.put("GO", "");
		model.put("pMode", pmode);
		
		new SiteNavigation(pmode).add("메시지").link(model);	
		
  	    ActionForward forward = new ActionForward();
  	    if(pmode.equals("POPUP"))
  	    	forward.setPath(defaultPagePath+systemcode+"/common/popup_message.jsp");
  	    else if(pmode.equals(LECTURE))
  	    	forward.setPath(defaultPagePath+systemcode+"/common/course_message.jsp");
  	    else
  	    	forward.setPath(defaultPagePath+systemcode+"/common/message.jsp");
  	    return forward;
    }

    /**
       * @param path
       */
      private String getMessagePath(String path) {
            String   msgPath = "/template/common/Message.tmpl";
            
            if(path.equals("1")) msgPath="/template/common/Message1.tmpl";
            else if(path.equals("2")) msgPath="/template/common/Message2.tmpl";
                
            return msgPath;
      }
}

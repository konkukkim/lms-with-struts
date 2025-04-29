package com.edutrack.framework.struts;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.framework.util.StringUtil;

public class DispatchAction extends AbstractAction{
  /**
   * The Class instance of this <code>DispatchAction</code> class.
   */
  protected Class clazz = this.getClass();


  /**
   * The message resources for this package.
   */
  protected static MessageResources messages =
   MessageResources.getMessageResources
      ("org.apache.struts.actions.LocalStrings");


  /**
   * The set of Method objects we have introspected for this class,
   * keyed by method name.  This collection is populated as different
   * methods are called, so that introspection needs to occur only
   * once per method name.
   */
  protected HashMap methods = new HashMap();


  /**
   * The set of argument type classes for the reflected method call.  These
   * are the same for all calls, so calculate them only once.
   */
  protected Class types[] = {
      ActionMapping.class, ActionForm.class,
      HttpServletRequest.class, HttpServletResponse.class, Map.class };

  // --------------------------------------------------------- Public Methods

  /**
   * Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it).
   * Return an <code>ActionForward</code> instance describing where and how
   * control should be forwarded, or <code>null</code> if the response has
   * already been completed.
   *
   * @param mapping The ActionMapping used to select this instance
   * @param form The optional ActionForm bean for this request (if any)
   * @param request The HTTP request we are processing
   * @param response The HTTP response we are creating
   *
   * @exception Exception if an exception occurs
   */
  public ActionForward execute(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               Map model)
      throws Exception {

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

      // Identify the method name to be dispatched to.
      // dispatchMethod() will call unspecified() if name is null
      String name = request.getParameter(parameter);

      // Invoke the named method, and return the result
      return dispatchMethod(mapping, form, request, response, model,name);
  }


  /**
   * Method which is dispatched to when there is no value for specified
   * request parameter included in the request.  Subclasses of
   * <code>DispatchAction</code> should override this method if they wish
   * to provide default behavior different than producing an HTTP
   * "Bad Request" error.
   *
   */
  protected ActionForward unspecified(ActionMapping mapping,
                                   ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
      throws Exception {

      String message =
          messages.getMessage("dispatch.parameter", mapping.getPath(),
                              mapping.getParameter());
      log.error(message);
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
      return (null);

  }


  // ----------------------------------------------------- Protected Methods


  /**
   * Dispatch to the specified method.
   * @since Struts 1.1
   */
   protected ActionForward dispatchMethod(ActionMapping mapping,
                                          ActionForm form,
                                          HttpServletRequest request,
                                          HttpServletResponse response,
                                          Map model,
                                          String name) throws Exception {

      // Make sure we have a valid method name to call.
      // This may be null if the user hacks the query string.
      if (name == null) {
          return this.unspecified(mapping, form, request, response);
      }

      // Identify the method object to be dispatched to
      Method method = null;
      try {
          method = getMethod(name);
      } catch (NoSuchMethodException e) {
          String message =
              messages.getMessage("dispatch.method", mapping.getPath(),
                                  name);
          log.error(message, e);
          response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                             message);
          return (null);
      }

      ActionForward forward = null;
      int succCheck = 0;
      try {
          Object args[] = { mapping, form, request, response, model};
          forward = (ActionForward) method.invoke(this, args);
      }catch(Exception e){
          e.printStackTrace();
	      String systemCode = UserBroker.getSystemCode(request);
	      String pMode = "Home";
		  String returnUrl =  "/Main.cmd?cmd=mainShow&pMode="+pMode;
		  String pageUrl= "/jsp/"+systemCode+"/common/message.jsp";
           
		  model.put("ALERT_MODE","message");
		  model.put("MSG_TYPE","N");		
		  model.put("ALERT","false");	
		  model.put("MSG",StringUtil.outDataConverter("비정상 로그아웃 되었거나 비정상 데이터의 처리로 인하여 시스템 오류가 발생하였습니다. <br> 지속적인 오류 발생시 관리자에게 문의 하여 주십시요."));
		  model.put("CLOSE","false");
		  model.put("RELOAD", "false");
		  model.put("URL", returnUrl);
		  model.put("EXCEPTION", "");
		  model.put("PARAM", "");
		  model.put("GO", "");
		  model.put("pMode", pMode);
			
		  new SiteNavigation(pMode).add("메시지").link(model);
			
		  forward = new ActionForward();
		  forward.setPath(pageUrl);
		     
		  return (forward);
	  }

      String menuNo = StringUtil.nvl(request.getParameter("MENUNO"),"");
      
      if(!menuNo.equals("")){ 
      	if(!menuNo.equals("0"))
      		UserBroker.setCurMenuNo(request, menuNo);
      	else
      		UserBroker.setCurMenuNo(request, "");
      }		

      model.put("MENUNO", StringUtil.nvl(UserBroker.getCurMenuNo(request),menuNo));

      // Return the returned ActionForward instance
     return (forward);
 
  }

  /**
   * Introspect the current class to identify a method of the specified
   * name that accepts the same parameter types as the <code>execute</code>
   * method does.
   *
   * @param name Name of the method to be introspected
   *
   * @exception NoSuchMethodException if no such method can be found
   */
  protected Method getMethod(String name)
      throws NoSuchMethodException {

      synchronized (methods) {
          Method method = (Method) methods.get(name);
          if (method == null) {
              method = clazz.getMethod(name, types);
              methods.put(name, method);
          }
          return (method);
      }

  }
  
  

}
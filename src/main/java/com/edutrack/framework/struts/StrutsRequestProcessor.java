/*
 * Created on 2004. 6. 28.
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.edutrack.framework.struts;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ModuleConfig;

import com.edutrack.framework.logging.Log;
import com.edutrack.framework.logging.LogFactory;

/**
 * @author chorongjang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StrutsRequestProcessor extends RequestProcessor {
	protected String requestEncoding = null;
	public Log log = LogFactory.getLog(getClass());
	
	public void init(ActionServlet servlet, ModuleConfig moduleConfig) throws javax.servlet.ServletException{
		this.requestEncoding = servlet.getInitParameter("request_encoding");

		log.debug("RequestEncoding : " + this.requestEncoding);
		super.init(servlet, moduleConfig);
	}
	
	public void destroy(){
		this.requestEncoding = null;
		super.destroy();
	}
	
	public void process(javax.servlet.http.HttpServletRequest request,
						javax.servlet.http.HttpServletResponse response)
				 throws java.io.IOException,
						javax.servlet.ServletException {

		if(this.requestEncoding != null ){
			request.setCharacterEncoding(this.requestEncoding);
		    response.setContentType("text/html;charset="+requestEncoding);
		}	 

		super.process(request,response);
	}
}

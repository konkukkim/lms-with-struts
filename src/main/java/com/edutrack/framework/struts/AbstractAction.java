package com.edutrack.framework.struts;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.framework.logging.Log;
import com.edutrack.framework.logging.LogFactory;

/**
 * 
 * <p>
 * $Id
 *
 */
public abstract class AbstractAction extends Action
{
	public static String name = "com.edutrack.framework.sturts.AbstractAction";
	public Log log = LogFactory.getLog(this.getClass());
	
	public static final String KEY_MODEL = "MODEL";
		
	public AbstractAction(){	
		super();
	}
	 
	public abstract ActionForward execute(ActionMapping mapping, 
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		Map model )	throws Exception;
	
	public ActionForward execute(ActionMapping mapping,
								 ActionForm form,
								 HttpServletRequest request,
								 HttpServletResponse response)
						  throws java.lang.Exception
	{
		

		Map model = new HashMap();
		request.setAttribute(KEY_MODEL, model);

		return execute(mapping,form,request,response,model);
	}
	
	public static String getName()
	{
		return name;
	}

}

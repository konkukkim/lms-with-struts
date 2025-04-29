package com.example.action;

import com.example.form.HelloWorldForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {

        HelloWorldForm helloWorldForm = (HelloWorldForm) form;
        System.out.println("this is HelloWorldAction.java");
        helloWorldForm.setGreeting("this is HelloWorldAction.java");

        return mapping.findForward("success");
    }

}
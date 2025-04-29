/*
 * Created on 2004. 12. 15.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.community.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CommMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.community.dao.CommInviteDAO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommInviteAction extends StrutsDispatchAction{

	/**
	 * 
	 */
	public CommInviteAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActionForward inviteUserList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
	    String  systemCode = UserBroker.getSystemCode(request);
		String 	pOp 	  =	StringUtil.nvl(request.getParameter("pOp"));
		String 	pSearch   =	StringUtil.nvl(request.getParameter("pSearch"));
		CommMemDTO commDto = UserBroker.getCommInfo(request); 
		
		CommInviteDAO inviteDao = new CommInviteDAO();
			
		if(!pOp.equals("") && !pSearch.equals("")){
			model.put("userList", inviteDao.inviteUserList(systemCode, pOp, pSearch, commDto.commId));
		}	
		model.put("pOp",pOp);
		model.put("pSearch",pSearch);	

	    return forward(request, model, "/community/commMemberInvite.jsp");
    }

    public ActionForward inviteUserRegist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
	    boolean retVal = false;
    	String  systemCode = UserBroker.getSystemCode(request);
	    UserMemDTO userDto = UserBroker.getUserInfo(request);
		CommMemDTO commDto = UserBroker.getCommInfo(request); 
		String[] inviteUserList = request.getParameterValues("inviteId");
		String pContents = request.getParameter("pContents");
		
		if(inviteUserList != null){ 
			CommInviteDAO inviteDao = new CommInviteDAO();
			retVal = inviteDao.inviteUserRegist(systemCode, commDto.commId, commDto.commName, userDto.userId,userDto.userName, inviteUserList, pContents);	
		}
		String msg = "선택한 사용자에게 초대 메시지를 전송하였습니다.";
		if(!retVal) msg = "초대 메시지를 전송하는데 실패하였습니다.";
		
	    return alertPopupCloseResponse(systemCode, model, msg,"POPUP");
    }
    
	public ActionForward inviteMsgList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
	    String  systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request); 
		
		CommInviteDAO inviteDao = new CommInviteDAO();
		
		model.put("inviteMsgList",inviteDao.getInviteMsg(systemCode, userId));
         
	    return forward(request, model, "/community/inviteMsgList.jsp");
    }
    
	public ActionForward inviteMsgDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
	    String  systemCode = UserBroker.getSystemCode(request);
		String  userId = UserBroker.getUserId(request); 
		String  seqNo = StringUtil.nvl(request.getParameter("pSeqNo"),"");
		CommInviteDAO inviteDao = new CommInviteDAO();
		
		inviteDao.delInviteMsg(systemCode, userId, seqNo);
                   
		int retVal = inviteDao.getInviteMsgCnt(systemCode, userId);
		
		if(retVal > 0) return redirect("/CommInvite.cmd?cmd=inviteMsgList&MENUNO=0");
		else return alertPopupCloseResponse(systemCode, model, "초대 메시지가 더 이상 없습니다.","POPUP");
    }
	
}

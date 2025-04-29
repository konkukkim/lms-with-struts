 package com.edutrack.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.edutrack.common.dto.CommMemDTO;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.framework.Constants;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.user.dao.UserDAO;

/*******************************************************************************
 * 프로그램 : UserBroker.java 
 * 모 듈 명 : 
 * 설 명 :
 *  테 이 블 : 
 * 작 성 자 : chorongjang 
 * 작 성 일 : 2004. 7. 10. 
 * 수 정 일 : 
 * 수정사항 :
 ******************************************************************************/
public class UserBroker implements Constants {
    private static Configuration config = ConfigurationFactory.getInstance().getConfiguration();
    private static UserBroker userBrokerins = null;
    /**
     *   
     */
    public UserBroker() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static UserBroker getInstance(){  
	    if(userBrokerins == null) {
	        userBrokerins = new UserBroker();
	     }
	
	     return userBrokerins;
    }

    /**
     * 사용자 캐쉬 정보를 가져온다.
     * @param req
     * @return
     */
    public static final UserMemDTO getUserInfo(HttpServletRequest req){
		UserMemDTO user = null;
        HttpSession session = req.getSession();
        user = (UserMemDTO)session.getAttribute("USERINFO");
		
		return user;
    }
    
    /**
     * req를 해석해서 현재 로그인한 고객 UserId를 반환한다.
     * 
     * @return 로그인한 고객 UserId, 로그인 하지 않았다면 null을 반환
     */
    public static final String getUserId(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String systemCode = getSystemCode(req);
        String userId = (String)session.getAttribute(LOGIN_USERID);
        
        if(userId == null) return "";         
                
        return StringUtil.nvl(userId);
    }
    
    /**
     * 사용자의 학년을 가져온다.
     * @param req
     * @return
     */
    public static final String getSchoolYear(HttpServletRequest req) {
    	HttpSession session = req.getSession();
    	String schoolYear = (String)session.getAttribute(LOGIN_SCHOOLYEAR);
    	
    	if(schoolYear == null) return "";
    	
    	return StringUtil.nvl(schoolYear);    	
    }

    /**
     * 사용자가 접근한 시스템 코드를 가져온다.
     * @param req
     * @return
     */ 
    public static final String getSystemCode(HttpServletRequest req){
        String systemCode = "";

        HttpSession session = req.getSession();
        systemCode = (String) session.getAttribute(LOGIN_SYSTEMCODE);

        if(systemCode == null ){ 
            systemCode = CommonUtil.getSystemCode(req.getServerName() + ":" + req.getServerPort());
        	session.setAttribute(LOGIN_SYSTEMCODE, systemCode);
        }
      
        return StringUtil.nvl(systemCode);
    }

    /**
     * 사용자 타입을 가져온다.
     * @param systemcode
     * @param userid
     * @param usertype
     */
    public static final String getUserType(HttpServletRequest req){
    	String userType = "";
        HttpSession session = req.getSession();
        userType = StringUtil.nvl((String)session.getAttribute(LOGIN_USERTYPE),"G");
 
        return StringUtil.nvl(userType);
    }
    
    /**
     * 사용자 이름을 가져온다.
     * @param req
     * @return
     */
    public static final String getUserName(HttpServletRequest req){
    	String userName = "";
        HttpSession session = req.getSession();
        userName = (String)session.getAttribute(LOGIN_USERNAME);
 
        return StringUtil.nvl(userName);
    }    
    
    /**
     * 강좌 정보를 가져온다.
     * @param systemcode
     * @param userid
     * @return
     */
    public static final CurriMemDTO getCurriInfo(HttpServletRequest req){
        UserMemDTO userInfo = getUserInfo(req);
        CurriMemDTO curriInfo = null;
        
        if(userInfo != null) return userInfo.curriInfo;        	
        else return null;
    }    

    /**
     * 강좌 정보를 셋팅한다.
     * @param systemcode
     * @param userid
     * @param courseinfo
     */
    public static final void setCurriInfo(HttpServletRequest req, CurriMemDTO curriinfo){
        UserMemDTO userInfo = getUserInfo(req);
        CurriMemDTO courseInfo = null;
        
        if(userInfo != null) userInfo.curriInfo = curriinfo;        	
    }    

    /**
     * 커뮤니티 정보를 가져온다.
     * @param systemcode
     * @param userid
     * @return
     */
    public static final CommMemDTO getCommInfo(HttpServletRequest req){
        UserMemDTO userInfo = getUserInfo(req);
        CommMemDTO commInfo = null;
        
        if(userInfo != null) return userInfo.commInfo;        	
        else return null;
    }    

    /**
     * 커뮤니티 정보를 셋팅한다.
     * @param systemcode
     * @param userid
     * @param courseinfo
     */
    public static final void setCommInfo(HttpServletRequest req, CommMemDTO comminfo){
        UserMemDTO userInfo = getUserInfo(req);
        CurriMemDTO courseInfo = null;
        
        if(userInfo != null) userInfo.commInfo = comminfo;        	
    }    

    /**
     * 현재 접속중인 사용자수를 가져온다.
     * @param curtime
     * @return
     */
    public static final int getConnectUserCnt(String systemcode, int checktime){
    	UserDAO userDao = new UserDAO();
    	int userCnt = 0;
    	try{
    		userCnt = userDao.getUseUserCnt(systemcode, checktime);
    	}catch(Exception e){}
    	
    	return userCnt;
    }
    
    /**
     * 사용자의 총 접속 수를 가져온다.
     * @param req
     * @return
     */
    public static  final int getConCnt(HttpServletRequest req){
        UserMemDTO user = getUserInfo(req);
        
		if (user == null) return 1;
        		
        return user.conCnt;
    }
    /**
     * 접속자가 단체 관리자인지 확인 한다.
     * @param req
     * @return
     */
    public static  final boolean chkDeptManager(HttpServletRequest req){
        UserMemDTO user = getUserInfo(req);
        
		if (user == null) return false;
        		
        return user.deptManager;
    }
    /**
     * 접속자의 소속 소코드를 반환한다.
     * @param req
     * @return
     */
    public static  final String getDeptSoCode(HttpServletRequest req){
        UserMemDTO user = getUserInfo(req);
        
		if (user == null) return "";
        		
        return user.deptSoCode;
    }
    /**
     * 접속자의 소속 대코드를 반환한다.
     * @param req
     * @return
     */
    public static  final String getDeptDaeCode(HttpServletRequest req){
        UserMemDTO user = getUserInfo(req);
        
		if (user == null) return "";
        		
        return user.deptDaeCode;
    }
    
    /**
     * 현재 접근한 메뉴번호를 가져온다.
     * @param request
     * @return
     */
    public static final String getCurMenuNo(HttpServletRequest request){
    	UserMemDTO userInfo = getUserInfo(request);
        
        if(userInfo != null) return userInfo.curMenuNo;        	
        else return "";
    }    
    
    /**
     * 현재 접근하고 있는 메뉴번호를 셋팅한다.
     * @param request
     * @param menuno
     */
    public static final void setCurMenuNo(HttpServletRequest request, String menuno){
    	UserMemDTO userInfo = getUserInfo(request);
        
        if(userInfo != null) userInfo.curMenuNo = menuno;        	
    } 

}
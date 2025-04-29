/*
 * Created on 2004. 10. 5.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.user.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.MailUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.user.dto.ProfInfoDTO;
import com.edutrack.user.dto.UsersDTO;
import com.edutrack.dept.dto.DeptSoCodeDTO;
import com.oreilly.servlet.MultipartRequest;


/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserHelper{

	/**
	 * 
	 */
	public UserHelper() {
		super();
		// TODO Auto-generated constructor stub
	}

    /**
     * ID/Pass찾기 메일전송
     * @param addr
     * @param username
     * @param msg
     */
    public void searchIdPwMailSend(String addr, String username,String msg){
//		StringBuffer sb = new StringBuffer();
//		sb.append("<html>");
//    	sb.append("<head>");
//    	sb.append("<title>ID/Pass 찾기 메일</title>");
//    	sb.append("</head>");
//    	sb.append("<body>");
//    	sb.append("<TABLE id=\"Table1\" cellSpacing=\"1\" cellPadding=\"1\" width=\"300\" border=\"1\">");
//    	sb.append("<TR>");
//    	sb.append("<TD style=\"HEIGHT: 60px;BACKGROUND-COLOR: #ccffcc\">");
//    	sb.append("<P align=\"center\"><FONT face=\"굴림\"><STRONG>ID / Pass 찾기</STRONG> </FONT>");
//    	sb.append("</P>");
//    	sb.append("</TD>");
//    	sb.append("</TR>");
//    	sb.append("<TR>");
//    	sb.append("<TD style=\"HEIGHT: 47px\"><FONT face=\"굴림\">"+msg+"</FONT></TD>");
//    	sb.append("</TR>");
//    	sb.append("</TABLE>");
//    	sb.append("</body>");
//    	sb.append("</html>");
        
    	MailUtil.sendDirectMail(addr, username, "KOFA 영화학교 아이디/패스워드 찾기", msg); 
    }
    /**
     * 단체 가입 승인 메일 전송
     * @param addr
     * @param username
     * @param msg
     */
    public void deptUseMailSend(String addr, String username,String msg){
//		StringBuffer sb = new StringBuffer();
//		sb.append("<html>");
//    	sb.append("<head>");
//    	sb.append("<title>단체 가입 승인 메일</title>");
//    	sb.append("</head>");
//    	sb.append("<body>");
//    	sb.append("<TABLE id=\"Table1\" cellSpacing=\"1\" cellPadding=\"1\" width=\"300\" border=\"1\">");
//    	sb.append("<TR>");
//    	sb.append("<TD style=\"HEIGHT: 60px;BACKGROUND-COLOR: #ccffcc\">");
//    	sb.append("<P align=\"center\"><FONT face=\"굴림\"><STRONG>단체 가입 승인 메일</STRONG> </FONT>");
//    	sb.append("</P>");
//    	sb.append("</TD>");
//    	sb.append("</TR>");
//    	sb.append("<TR>");
//    	sb.append("<TD style=\"HEIGHT: 47px\"><FONT face=\"굴림\">"+msg+"</FONT></TD>");
//    	sb.append("</TR>");
//    	sb.append("</TABLE>");
//    	sb.append("</body>");
//    	sb.append("</html>");
        
    	MailUtil.sendDirectMail(addr, username, "KOFA 영화학교 단체 가입 승인 메일", msg); 
    }
    
    /**
     * 사용자 정보 파라미터를 가져온다.
     * @param request
     * @param user
     * @return
     */
    public void getUserParam(HttpServletRequest request, UsersDTO user){
		String pResidentId1 = StringUtil.nvl(request.getParameter("pResidentId1"));
		String pResidentId2 = StringUtil.nvl(request.getParameter("pResidentId2"));
		
		user.setUserId(StringUtil.nvl(request.getParameter("pUserId")));
		user.setUserName(StringUtil.nvl(request.getParameter("pUserName")));
		user.setPasswd(StringUtil.nvl(request.getParameter("pUserPass1")));
		user.setUserType(StringUtil.nvl(request.getParameter("userType")));
		
		if (!pResidentId1.equals(""))
			user.setJuminNo(pResidentId1+"-"+pResidentId2);
		
		user.setPhoneHome(StringUtil.nvl(request.getParameter("pPhoneHome")));
		user.setPhoneMobile(StringUtil.nvl(request.getParameter("pPhoneHand")));
		user.setPostCode(StringUtil.nvl(request.getParameter("pZipCode")));
		user.setAddress(StringUtil.nvl(request.getParameter("pAddress")));
		user.setEmail(StringUtil.nvl(request.getParameter("pEMail")));
		user.setDeptDaecode(StringUtil.nvl(request.getParameter("pDeptDaecode")));
		user.setDeptSocode(StringUtil.nvl(request.getParameter("pDeptSocode")));
		user.setJobCode(StringUtil.nvl(request.getParameter("pJobCode")));
		
		
		user.setSchoolYear(String.valueOf(StringUtil.nvl(request.getParameter("pDeptDaecode"),0)-100));  // 학년...only 사이버 노동..
		
		user.setCompName(StringUtil.nvl(request.getParameter("pCompName")));
		user.setCompPhone(StringUtil.nvl(request.getParameter("pCompPhone")));
		user.setCompPostCode(StringUtil.nvl(request.getParameter("pCompPostCode")));
		user.setCompAddress(StringUtil.nvl(request.getParameter("pCompAddress")));
		
		if (!pResidentId2.equals("")) 
			user.setSexType(pResidentId2.substring(0,1));
		 
		user.setUseStatus("Y");
    }

    /**
     * 파일 업로드 시 사용자 정보를 가져온다.
     * @param request
     * @param user
     */
    public void getUserMultiParam(MultipartRequest request, UsersDTO user){
		String pResidentId1 = StringUtil.nvl(request.getParameter("pResidentId1"));
		String pResidentId2 = StringUtil.nvl(request.getParameter("pResidentId2"));
		
		user.setUserId(StringUtil.nvl(request.getParameter("pUserId")));
		user.setUserName(StringUtil.nvl(request.getParameter("pUserName")));
		user.setPasswd(StringUtil.nvl(request.getParameter("pUserPass1")));
		user.setUserType(StringUtil.nvl(request.getParameter("userType")));
		
		if(!pResidentId1.equals(""))
			user.setJuminNo(pResidentId1+"-"+pResidentId2);
		
		user.setPhoneHome(StringUtil.nvl(request.getParameter("pPhoneHome")));
		user.setPhoneMobile(StringUtil.nvl(request.getParameter("pPhoneHand")));
		user.setPostCode(StringUtil.nvl(request.getParameter("pZipCode")));
		user.setAddress(StringUtil.nvl(request.getParameter("pAddress")));
		user.setEmail(StringUtil.nvl(request.getParameter("pEMail")));
		user.setDeptDaecode(StringUtil.nvl(request.getParameter("pDeptDaecode")));
		user.setDeptSocode(StringUtil.nvl(request.getParameter("pDeptSocode")));
		user.setJobCode(StringUtil.nvl(request.getParameter("pJobCode")));
		
		if(!pResidentId2.equals("")) 
			user.setSexType(pResidentId2.substring(0,1));
		// 나중에 디비의  Config값에 따라 디폴트 사용유무를 넣어준다. 
		user.setUseStatus("Y");
		
		user.setUserNameEng(StringUtil.nvl(request.getParameter("pUserNameEng")));
		user.setCompType(StringUtil.nvl(request.getParameter("pCompType")));
		user.setCompName(StringUtil.nvl(request.getParameter("pCompName")));
		user.setCompNo(StringUtil.nvl(request.getParameter("pCompNo")));
		user.setCompPostCode(StringUtil.nvl(request.getParameter("pCompPostCode")));
		user.setCompAddress(StringUtil.nvl(request.getParameter("pCompAddress")));
		user.setCompPhone(StringUtil.nvl(request.getParameter("pCompPhone")));
		user.setCompGrade(StringUtil.nvl(request.getParameter("pCompGrade")));
		user.setCareer(StringUtil.nvl(request.getParameter("pCareer")));
		user.setMajor(StringUtil.nvl(request.getParameter("pMajor")));
		user.setSchool(StringUtil.nvl(request.getParameter("pSchool")));
		user.setRecvMail(StringUtil.nvl(request.getParameter("pRecvMail")));
		user.setRecvSms(StringUtil.nvl(request.getParameter("pRecvSms")));
		
		//-- 추가
//		user.setProfCareer(StringUtil.nvl(request.getParameter("pProfCareer")));
//		user.setProfSchool(StringUtil.nvl(request.getParameter("pProfSchool")));
    }
    
	
    /**
     * 교수의 추가 정보 파라미터를 가져온다.
     * @param request
     * @param prof
     */
    public void getProfParam(HttpServletRequest request, ProfInfoDTO prof){
        prof.setUserId(StringUtil.nvl(request.getParameter("pUserId")));
        prof.setOldUserPhoto(StringUtil.nvl(request.getParameter("pOldUserPicture")));
        prof.setPhoneNumber(StringUtil.nvl(request.getParameter("pPhoneNumber")));
        prof.setBooks(StringUtil.nvl(request.getParameter("pBooks")));
        prof.setCareer(StringUtil.nvl(request.getParameter("pCareer")));
        prof.setMajor(StringUtil.nvl(request.getParameter("pMajor")));
        prof.setIntro(StringUtil.nvl(request.getParameter("pIntro")));
    }

    /**
     * 파일 업로드일때 교수 정보를 가져온다.
     * @param request
     * @param prof
     */
    public void getProfMultiParam(MultipartRequest request, ProfInfoDTO prof){
        prof.setUserId(StringUtil.nvl(request.getParameter("pUserId")));
        prof.setOldUserPhoto(StringUtil.nvl(request.getParameter("pOldUserPicture")));
        prof.setPhoneNumber(StringUtil.nvl(request.getParameter("pPhoneNumber")));
        prof.setBooks(StringUtil.nvl(request.getParameter("pBooks")));
        prof.setCareer(StringUtil.nvl(request.getParameter("pProfCareer")));
        prof.setMajor(StringUtil.nvl(request.getParameter("pProfSchool")));
        prof.setIntro(StringUtil.nvl(request.getParameter("pIntro")));
    }


    /**
     * 파일 업로드일때 소속코드정보를 가져온다.
     * @param request
     * @param prof
     */
    public void getDeptSoCodeParam(HttpServletRequest request, DeptSoCodeDTO deptSoCodeDto){
	    deptSoCodeDto.setDeptDaecode(StringUtil.nvl(request.getParameter("pDeptDaecode") )); 
	    deptSoCodeDto.setDeptSocode (StringUtil.nvl(request.getParameter("pDeptSocode"  ))); 
	    deptSoCodeDto.setDeptSoname (StringUtil.nvl(request.getParameter("pDeptSoname")   )); 
	    deptSoCodeDto.setUseYn      (StringUtil.nvl(request.getParameter("pUseYn")        )); 
	    deptSoCodeDto.setUserId     (StringUtil.nvl(request.getParameter("pUserId")       )); 
	    deptSoCodeDto.setPostCode   (StringUtil.nvl(request.getParameter("pGroupPost")    )); 
	    deptSoCodeDto.setAddress    (StringUtil.nvl(request.getParameter("pGroupAddress") )); 
	    deptSoCodeDto.setPhone      (StringUtil.nvl(request.getParameter("pGroupPhone")   )); 
	    deptSoCodeDto.setPosition   (StringUtil.nvl(request.getParameter("pGroupPosition"))); 
	    
    }
    
    
    /**
     * 파일 업로드일때 소속코드정보를 가져온다.
     * @param request
     * @param prof
     */
    public void getDeptSoCodeMultiParam(MultipartRequest request, DeptSoCodeDTO deptSoCodeDto){
	    deptSoCodeDto.setDeptDaecode(StringUtil.nvl(request.getParameter("pDeptDaecode") )); 
	    deptSoCodeDto.setDeptSocode (StringUtil.nvl(request.getParameter("pDeptSocode"  ))); 
	    deptSoCodeDto.setDeptSoname (StringUtil.nvl(request.getParameter("pDeptSoname")   )); 
	    deptSoCodeDto.setUseYn      (StringUtil.nvl(request.getParameter("pUseYn")        )); 
	    deptSoCodeDto.setUserId     (StringUtil.nvl(request.getParameter("pUserId")       )); 
	    deptSoCodeDto.setPostCode   (StringUtil.nvl(request.getParameter("pGroupPost")    )); 
	    deptSoCodeDto.setAddress    (StringUtil.nvl(request.getParameter("pGroupAddress") )); 
	    deptSoCodeDto.setPhone      (StringUtil.nvl(request.getParameter("pGroupPhone")   )); 
	    deptSoCodeDto.setPosition   (StringUtil.nvl(request.getParameter("pGroupPosition"))); 
	    //deptSoCodeDto.setRegId      (StringUtil.nvl(userDto.getUserId()  )); 
	    //deptSoCodeDto.setRegDate    (StringUtil.nvl(userDto.getRegDate() )); 
	    //deptSoCodeDto.setModId      (StringUtil.nvl(userDto.getModId()   )); 
	    //deptSoCodeDto.setModDate    (StringUtil.nvl(userDto.getModDate() )); 

	    
    }
    
    
    /**
     * 사용자 등록정보를 파싱한다.
     * @param systemcode
     * @param regid
     * @param lineno
     * @param line
     * @param userlist
     * @param faillist
     */
   public void parseUserInfo(String systemcode, String regid, String usertype,int lineno, String line, ArrayList userlist, ArrayList faillist){
   	    String[] strArr = StringUtil.split(line, ",");
        ParseParam param = new ParseParam();


        // 데이터를 구분자별로 파싱한다.
   	    if (strArr == null || strArr.length != 11 ) {
			param.setLineNo(lineno);
			param.setErrorType(StringUtil.outDataConverter("CSV파일구분자(,) 오류"));
			if(strArr != null) param.setUserId(strArr[2]);
			 
			faillist.add(param);
			return;
		}
        // 소속대코드에 대한 체크     	    
   	    if(strArr[0].getBytes().length < 1 || strArr[0].getBytes().length > 6){
			param.setLineNo(lineno);
			param.setErrorType(StringUtil.outDataConverter("소속대코드 길이 오류"));
			if(strArr != null) param.setUserId(strArr[2]);
			
			faillist.add(param);
			return;   	    	
   	    }
        // 소속소코드에 대한 체크
   	    if(strArr[1].getBytes().length < 1 || strArr[1].getBytes().length > 6){
			param.setLineNo(lineno);
			param.setErrorType(StringUtil.outDataConverter("소속소코드 길이 오류"));
			if(strArr != null) param.setUserId(strArr[2]);
			
			faillist.add(param);
			return;   	    	
   	    }
        // 사용자 아이디 체크
   	    if(strArr[2].getBytes().length < 1 || strArr[2].getBytes().length > 15){
			param.setLineNo(lineno);
			param.setErrorType(StringUtil.outDataConverter("사용자아이디  길이 오류"));
			if(strArr != null) param.setUserId(strArr[2]);
			
			faillist.add(param);
			return;   	    	
   	    }
   	    // 이름 체크
   	    if(strArr[3].getBytes().length < 1 || strArr[3].getBytes().length > 20){
			param.setLineNo(lineno);
			param.setErrorType(StringUtil.outDataConverter("사용자이름  길이 오류"));
			if(strArr != null) param.setUserId(strArr[2]);
			
			faillist.add(param);
			return;   	    	
   	    }  
   	    // 암호 체크
   	    if(strArr[4].getBytes().length < 1 || strArr[4].getBytes().length > 20){
			param.setLineNo(lineno);
			param.setErrorType(StringUtil.outDataConverter("사용자암호  길이 오류"));
			if(strArr != null) param.setUserId(strArr[2]);
			
			faillist.add(param);
			return;   	    	
   	    }
 	    
   	    // 주민번호 체크
   	    String juminNo =strArr[5]; 
   	    if(juminNo.getBytes().length < 1 || juminNo.getBytes().length > 14){
			param.setLineNo(lineno);
			param.setErrorType(StringUtil.outDataConverter("주민번호  길이 오류"));
			if(strArr != null) param.setUserId(strArr[2]);
			
			faillist.add(param);
			return;   	    	
   	    }   	    
   	    // 주민번호의 형식을 체크한다.
   	    String[] jumin = StringUtil.split(juminNo,"-");
   	    if (strArr == null || jumin.length < 2 || jumin[0].getBytes().length != 6 || jumin[1].getBytes().length != 7 || !StringUtil.isNumber(jumin[0]) || !StringUtil.isNumber(jumin[1])) {
			param.setLineNo(lineno);
			param.setErrorType(StringUtil.outDataConverter("주민번호 형식 오류"));
			if(strArr != null) param.setUserId(strArr[2]);
			
			faillist.add(param);
			return;
		}   	    
   	    // 우편번호 체크
        String[] post = StringUtil.split(strArr[6],"-"); 
   	    if(post == null || post.length < 2 || post[0].getBytes().length != 3 || post[1].getBytes().length != 3 || !StringUtil.isNumber(post[0]) || !StringUtil.isNumber(post[1])){
			param.setLineNo(lineno);
			param.setErrorType(StringUtil.outDataConverter("우편번호 형식 오류"));
			if(strArr != null) param.setUserId(strArr[2]);
			
			faillist.add(param);
			return;   	    	
   	    }   	    
   	    if(strArr[6].getBytes().length > 7){
			param.setLineNo(lineno);
			param.setErrorType(StringUtil.outDataConverter("우편번호  길이 오류"));
			if(strArr != null) param.setUserId(strArr[2]);
			
			faillist.add(param);
			return;   	    	
   	    }   	    
   	    // 주소 체크 
   	    if(strArr[7].getBytes().length > 200){
			param.setLineNo(lineno);
			param.setErrorType(StringUtil.outDataConverter("주소  길이 오류"));
			if(strArr != null) param.setUserId(strArr[2]);
			
			faillist.add(param);
			return;   	    	
   	    }   	    
   	    // 전화번호(자택) 체크
   	    if(strArr[8].getBytes().length > 20){
			param.setLineNo(lineno);
			param.setErrorType(StringUtil.outDataConverter("전화번호(자택)  길이 오류"));
			if(strArr != null) param.setUserId(strArr[2]);
			
			faillist.add(param);
			return;   	    	
   	    }   	    
   	    // 전화번호(핸드폰) 체크
   	    if(strArr[9].getBytes().length > 20){
			param.setLineNo(lineno);
			param.setErrorType(StringUtil.outDataConverter("전화번호(핸드폰)  길이 오류"));
			if(strArr != null) param.setUserId(strArr[2]);
			
			faillist.add(param);
			return;   	    	
   	    }   	    
        // 이메일 체크 
   	    if(strArr[10].getBytes().length > 80){
		 	param.setLineNo(lineno);
			param.setErrorType(StringUtil.outDataConverter("Email  길이 오류"));
			if(strArr != null) param.setUserId(strArr[2]);
			
			faillist.add(param);
			return;   	    	
   	    }   	    
  	   
 	   
	    
   	    UsersDTO userDto = new UsersDTO();
   	    userDto.setSystemCode(systemcode);
   	    userDto.setDeptDaecode(strArr[0]);
		userDto.setDeptSocode(strArr[1]);
   	    userDto.setUserId(strArr[2]);
   	    userDto.setUserType(usertype);
		userDto.setUserName(StringUtil.outDataConverter(strArr[3]));
		userDto.setPasswd(strArr[4]);
		userDto.setJuminNo(strArr[5]);
		userDto.setPostCode(strArr[6]);
		userDto.setAddress(StringUtil.outDataConverter(strArr[7]));
		userDto.setPhoneHome(strArr[8]);
		userDto.setPhoneMobile(strArr[9]);		
		userDto.setEmail(strArr[10]);
		userDto.setRegId(regid);
		userDto.setSexType(jumin[1].substring(0,1));
		userDto.setUseStatus("Y");
		userDto.setLineNo(lineno);
		
		userlist.add(userDto);

   }
}

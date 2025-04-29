<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>

<% 
	String errorCode = request.getParameter("pErrorCode");
	String errorMsg = "";
	if(errorCode.equals("1"))
		errorMsg = "로그인이 필요합니다.";
	else if(errorCode.equals("2"))
		errorMsg = "권한이 필요합니다.";
	else
		errorMsg = "알수없는 오류 발생.";
	
%>
<% out.println(errorMsg); %>

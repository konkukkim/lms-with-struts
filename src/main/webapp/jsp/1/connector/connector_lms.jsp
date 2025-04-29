<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ page import="mediopia.lms.standard.connector.Connector"%>
<%

	// LMS Connector 호출
	Connector connector = new Connector();

	connector.doGet(request, response);

%>

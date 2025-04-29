<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.community.dao.CommInfoDAO"%>
<%@ page import ="javax.sql.RowSet"%>

									<select name="select2" onChange="window.open(this.options[this.selectedIndex].value,'edutrack_main')">
									<option selected>가입동아리 바로가기</option>
<%
	// 가입 동아리
	CommInfoDAO comminfoDao = new CommInfoDAO();
	RowSet list = comminfoDao.getCommInfoList(SYSTEMCODE, USERID);

	String goUrl = "";
	int commId			= 0;

	while(list.next()){
		commId		= list.getInt("comm_id");

		goUrl		=	CONTEXTPATH+"/Community.cmd?cmd=goCommunity&pCommId="+commId+"&MENUNO=0";
%>
<option value="<%=goUrl%>">- <%=list.getString("comm_name")%></a>
<%
	}
	list.close();
%>
									</select>

<%@ page contentType="text/html; charset=KS_C_5601-1987" %>
<%@ page errorPage="/jsp/1/search/error/error_jsp.jsp" %>

<!--DB 번호 정의 파일 include-->
<%@ include file="/jsp/1/search/common/multi_db_no.jsp" %>

<%
	int dbNo = Integer.parseInt(request.getParameter("dbNo"));

    if (dbNo == getMultiDBNo("KTSET") || dbNo == getMultiDBNo("KTSET2"))
    {
%>
       <table width=620 border=0 cellspacing=2 cellpading=4 class='table1'>
         <tr align=center>
           <td width=40 class=td1><b>번호</b></td>
           <td width=430 class=td1><b>제목</b></td>
           <td width=100 class=td1><b>저자</b></td>
           <td width=40 class=td1><b>연도</b></td>
         </tr>
       </table> 
<%
    } 
    else if (dbNo == getMultiDBNo("lms_course"))
    {
%>
       <table width="690" border="0" cellspacing="0" cellpadding="0">
          <tr> 
	        <td colspan="6" bgcolor="C2B29D"><img src="/img/1/common/blank.gif" width="1" height="2"></td>
	      </tr>
	      <tr align="center"  height="24"> 
	        <!--<td width=40 class=td1><b>번호</b></td>-->
            <td width=430  bgcolor="F7F1D1"><b>과정명</b></td>
            <td width=100  bgcolor="F7F1D1"><b>과정구분</b></td>
            <td width=140  bgcolor="F7F1D1"><b>수강기간</b></td>
          </tr>
	      <tr> 
	        <td colspan="3" height="1" bgcolor="#ffffff"><img src="/img/1/common/blank.gif" width="1" height="1"></td>
	      </tr>
	      <tr> 
	        <td colspan="3" height="1" bgcolor="C2B29D"><img src="/img/1/common/blank.gif" width="1" height="1"></td>
	      </tr>
		</table>
<%
    }
    else
    {
%>
       <!--<table width="690" border="0" cellspacing="0" cellpadding="0">-->
         
<%
    }
%>
<%@ page contentType="text/html; charset=KS_C_5601-1987" %>
<%@ page import="javaclientapi.*" %>
<%@ page import="woorin.dasen.bean.*" %>
<%@ page import="woorin.dasen.util.BeanUtil" %>
<%@ page errorPage="/jsp/1/search/error/error_jsp.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link rel=stylesheet type=text/css href=/jsp/1/search/common/style.css>
<title>다센21 정보검색시스템 v4.1</title>
</head>
<body bgcolor=#FFFFFF marginheight=0 marginwidth=0 leftmargin=0 topmargin=0>
<center>
<br>
<table width=700 border=0 cellspacing=0 cellpadding=0>
  <tr>
    <td colspan=3 background=/jsp/1/search/img/box_t.gif height=75 valign=top>
      <table cellpadding=0 cellspacing=0 border=0 width=100%>
        <tr>
          <td><a href=/jsp/1/><img src=/jsp/1/search/img/box_title1.gif border=0></a></td>
          <td align=right valign=bottom><img src=/jsp/1/search/img/box_title2.gif></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td background=/jsp/1/search/img/line2.gif width=3><img src=/jsp/1/search/img/space.gif width=3 height=1></td>
    <td align=center>
       <img src=/jsp/1/search/img/space.gif width=1 height=8><br>
       <img src=/jsp/1/search/img/line3.gif width=690 height=1><br>
       <table cellpadding=0 cellspacing=0 border=0 width=690>
         <tr>
           <td background=/jsp/1/search/img/line4.gif height=30 valign=middle class=txt5>
           <img src=/jsp/1/search/img/icon2.gif hspace=5>상세정보</td>
           <td background=/jsp/1/search/img/line4.gif align=right><a href=javascript:history.go(-1)><img src=/jsp/1/search/img/page_list.gif border=0 alt='목록' hspace=10></a></td>
         </tr>
       </table>
       <br>

<!--DB 번호 정의 파일 include-->
<%@ include file="/jsp/1/search/common/multi_db_no.jsp" %>
<%
    // 검색필수객체 선언
    JavaExternalAPI japi = null;
    DS_Handle hptr = null;
    SearchParaBean sbean = null;

	// 검색결과 사용변수 선언
    ResultSet_Doc docInfo = null;
    ResultSet_Field fieldInfo = null;
    int ret;

	if (request.getAttribute("RET")!=null)
    {
        int status = 0;
        status = Integer.parseInt((String)request.getAttribute("RET"));

%>
        <jsp:include page="/jsp/1/search/common/returnError.jsp" flush="true">
            <jsp:param name="status" value="<%=status%>" />
        </jsp:include>
<%
        return;
    }
    else
    {
        japi = (JavaExternalAPI) request.getAttribute("JAPI");
        hptr = (DS_Handle) request.getAttribute("HPTR");
        sbean = (SearchParaBean) request.getAttribute("SBEAN");
	}

	if (sbean.getDbNo() == getMultiDBNo("KTSET"))
	{
%>
		<jsp:include page="/jsp/1/search/common/full_content_ktset.jsp" flush="true" />
		
<%
	}
	else
	{
%>
		아직 구현되지 않았습니다.
<%
	}
%>

        <br>
      </td>
      <td background=/jsp/1/search/img/line2.gif width=3><img src=/jsp/1/search/img/space.gif width=3 height=1></td>
    </tr>
    <tr>
      <td colspan=3>
        <table cellpadding=0 cellspacing=0 border=0 width=100%>
          <tr>
            <td width=14><img src=/jsp/1/search/img/box_lb.gif></td>
            <td background=/jsp/1/search/img/box_b.gif align=right>
              <a href=javascript:history.go(-1)><img src=/jsp/1/search/img/page_list.gif border=0 alt='목록'></a></td>
            <td align=right width=14><img src=/jsp/1/search/img/box_rb.gif></td>
          </tr>
        </table>
       </td>
        </table>
<img src=/jsp/1/search/img/space.gif width=1 height=5><br>
<span class=copy>Copyright (c) 1998-2003 WOORIN Information. All rights Reserved. </span>
<br><br>
</center>
</body>
</html>
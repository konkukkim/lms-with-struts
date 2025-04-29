<%@ page contentType="text/html; charset=KS_C_5601-1987" %>
<%@ page import="javaclientapi.*" %>
<%@ page import="woorin.dasen.bean.*" %>
<%@ page import="woorin.dasen.util.BeanUtil" %>
<%@ page errorPage="/jsp/1/search/error/error_jsp.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel=stylesheet type=text/css href=/jsp/1/search/common/style.css>
<title>다센21 정보검색시스템 v4.1</title>
</head>

<body bgcolor=#FFFFFF marginheight=0 marginwidth=0 leftmargin=0 topmargin=0>
<center>
<br><table width=700 border=0 cellspacing=0 cellpadding=0>
  <tr>
    <td colspan=3 background="/jsp/1/search/search/img/box_t.gif height=75 valign=top>
      <table cellpadding=0 cellspacing=0 border=0 width=100%>
        <tr>
          <td><a href="/jsp/1/search/><img src="/jsp/1/search/search/img/box_title1.gif border=0></a></td>
          <td align=right valign=bottom><img src="/jsp/1/search/search/img/box_title2.gif></td>
        </tr>
      </table>
    </td>
  </tr>

  <tr>
    <td background="/jsp/1/search/search/img/line2.gif width=3><img src="/jsp/1/search/search/img/space.gif width=3 height=1></td>
    <td align=center>
       <img src="/jsp/1/search/search/img/space.gif width=1 height=8><br>
       <img src="/jsp/1/search/search/img/line3.gif width=690 height=1><br>

<!--DB 번호 정의 파일 include-->
<%@ include file="/jsp/1/search/common/multi_db_no.jsp" %>

<%
    // 검색필수객체 선언
    JavaExternalAPI japi = null;
    DS_Handle hptr = null;
    SearchParaBean sbean = null;

	// 검색결과 사용변수 선언
    ResultSet_Set setInfo = null;
    ResultSet_Doc docInfo = null;
    ResultSet_Field fieldInfo = null;
    int ret;
    String kwStr = "";
	String dbStr = "";
    String cmpStr = "";

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
        kwStr = (String)request.getAttribute("kwStr");
    }

	int total_cnt = japi.DS_GetTotalDocCount(hptr);
%>

       <table cellpadding=0 cellspacing=0 border=0 width=690>
         <tr>
           <td background="/jsp/1/search/search/img/line4.gif height=30 valign=middle class=txt5>
             <img src="/jsp/1/search/search/img/icon4.gif hspace=5>총 <span class=txt6><%=total_cnt%></span> 개의 자료가 검색되었습니다.
           </td>
         </tr>
       </table>
       <br>

		<table border=0 align=center cellspacing=0 cellpadding=0 width=620>
		  <form method=post name=select action='./SearchServlet?command=search.brief'>
		  <input type=hidden name=dbList value=<%=sbean.getDbList()%>>
		  <input type=hidden name=dbNo value=<%=sbean.getDbNo()%>>
		  <input type=hidden name=kwStr value="<%=kwStr%>">
          <tr>
            <td class=txt1 height=33>
			  <select name=field size=1 class=input2>
                <option value=전체>전체
				<option value=한글제목>한글제목
				<option value=한글저자>한글저자
				<option value=한글초록>한글초록
				<option value=한글제목+한글초록>한글제목+한글초록
				<option value=영문제목>영문제목
				<option value=영문저자>영문저자
				<option value=영문초록>영문초록
				<option value=영문제목+영문초록>영문제목+영문초록
              </select>
            <td class=txt2>&nbsp;<input type=text name=refineStr size=30 class=input1>
			  <input type=image src="/jsp/1/search/search/img/but_search.gif border=0 hspace=10 alt='검색' align="absmiddle">
			  &nbsp;&nbsp;&nbsp; 
			  <input type="checkbox" name="refineMode" value="1"> 결과내 재검색
			</td>
          </tr>
		  </form>
        </table>
		<br>

	<!-- 검색건수 및 페이지 출력 -->
	<jsp:include page="/jsp/1/search/common/search_cnt_table.jsp" flush="true">
    <jsp:param name="searchCnt" value="<%=sbean.getSearchCnt()%>" />
	<jsp:param name="dbList" value="<%=sbean.getDbList()%>" />
    <jsp:param name="dbNo" value="<%=sbean.getDbNo()%>" />
    <jsp:param name="kwStr" value="<%=kwStr%>" />
	<jsp:param name="dbStr" value="<%=dbStr%>" />
	<jsp:param name="cmpStr" value="<%=cmpStr%>" />
    <jsp:param name="expMode" value="<%=sbean.getExpMode()%>" />
    <jsp:param name="pageNum" value="<%=sbean.getPageNum()%>" />
	<jsp:param name="searchMode" value="<%=sbean.getSearchMode()%>" />
    <jsp:param name="sortFld" value="<%=sbean.getSortFld()%>" />
    <jsp:param name="sortMode" value="<%=sbean.getSortMode()%>" />
	<jsp:param name="comMode" value="brief" />
	<jsp:param name="curPageNo" value="<%=sbean.getCurPageNo()%>" />
	<jsp:param name="totalPageCnt" value="<%=sbean.getTotalPageCnt()%>" />

    </jsp:include>

	<!-- 검색 결과 내용 헤더 출력 -->
	<jsp:include page="/jsp/1/search/common/search_content_header.jsp" flush="true">
	<jsp:param name="dbNo" value="<%=sbean.getDbNo()%>" />
	</jsp:include>

	<!-- 검색 결과 내용 목록 출력 -->
<%			
	if (sbean.getDbNo() == getMultiDBNo("KTSET"))
	{
%>
		<jsp:include page="/jsp/1/search/common/search_content_ktset.jsp" flush="true">
		<jsp:param name="dbNo" value="<%=sbean.getDbNo()%>" />
		<jsp:param name="kwStr" value="<%=kwStr%>" />
		<jsp:param name="expMode" value="<%=sbean.getExpMode()%>" />
		</jsp:include>
<%
	}
	else if (sbean.getDbNo() == getMultiDBNo("KTSET2"))
	{
%>
		<jsp:include page="/jsp/1/search/common/search_content_ktset.jsp" flush="true">
		<jsp:param name="dbNo" value="<%=sbean.getDbNo()%>" />
		<jsp:param name="kwStr" value="<%=kwStr%>" />
		<jsp:param name="expMode" value="<%=sbean.getExpMode()%>" />
		</jsp:include>
<%
	}
	else
	{
%>
		<jsp:include page="/jsp/1/search/common/search_content_default.jsp" flush="true">
		<jsp:param name="dbNo" value="<%=sbean.getDbNo()%>" />
		<jsp:param name="kwStr" value="<%=kwStr%>" />
		<jsp:param name="expMode" value="<%=sbean.getExpMode()%>" />
		</jsp:include>
<%
	}
%>

       </table>
     <br>

     </td>
     <td background="/jsp/1/search/search/img/line2.gif width=3><img src="/jsp/1/search/search/img/space.gif width=3 height=1></td>
   </tr>

   <tr>
     <td colspan=3>
       <table cellpadding=0 cellspacing=0 border=0 width=100%>
         <td width=14><img src="/jsp/1/search/search/img/box_lb.gif></td>
         <td background="/jsp/1/search/search/img/box_b.gif align=right>
         <table><tr><td>
			<!-- 페이지 이동 -->
			<jsp:include page="/jsp/1/search/common/page_move.jsp" flush="true">
			<jsp:param name="kwStr" value="<%=kwStr%>" />
			<jsp:param name="dbList" value="<%=sbean.getDbList()%>" />
			<jsp:param name="dbNo" value="<%=sbean.getDbNo()%>" />
			<jsp:param name="pageNum" value="<%=sbean.getPageNum()%>" />
			<jsp:param name="startNo" value="<%=sbean.getStartNo()%>" />
			<jsp:param name="searchCnt" value="<%=sbean.getSearchCnt()%>" />
			<jsp:param name="searchMode" value="<%=sbean.getSearchMode()%>" />
			<jsp:param name="sortFld" value="<%=sbean.getSortFld()%>" />
			<jsp:param name="sortMode" value="<%=sbean.getSortMode()%>" />
			<jsp:param name="expMode" value="<%=sbean.getExpMode()%>" />
			</jsp:include>
         </td></tr></table>
         </td>
         <td align=right width=14><img src="/jsp/1/search/search/img/box_rb.gif></td>
       </tr>
     </table>
     </td>
   </tr>
</table>
<img src="/jsp/1/search/search/img/space.gif width=1 height=5><br>
<span class=copy>Copyright (c) 1998-2001 WOORIN Information. All rights Reserved. </span>
<br><br>
</center>
</body>
</html>
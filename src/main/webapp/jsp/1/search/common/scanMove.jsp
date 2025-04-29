<%@ page contentType="text/html; charset=KS_C_5601-1987" %>
<%@ page import = "java.net.URLEncoder" %>
<%@ page errorPage="/jsp/1/search/error/error_jsp.jsp" %>

<%
    String dbNo, fieldNo, prevNum, nextNum, prevStr, nextStr;
    dbNo = request.getParameter("dbNo");
    fieldNo = request.getParameter("fieldNo");
    prevNum = request.getParameter("prevNum");
    nextNum = request.getParameter("nextNum");
    prevStr = URLEncoder.encode(request.getParameter("prevStr"));
    nextStr = URLEncoder.encode(request.getParameter("nextStr"));

%>


<!-- ========== 화면 좌우측 맞춤 이미지 (아래 마지막에 연결되어야 함.) ========== -->
<table border=0 cellspacing=0 cellpadding=0 width=700>
    <tr>
        <td width=14 align=left><img src=/jsp/1/search/img/box_lb.gif></td>
        <td background=/jsp/1/search/img/box_b.gif align=right>


<!-- ========== 실제 페이지 이동 출력 ========== -->
<table border=0 cellspacing=0 cellpadding=0>
    <tr>
        <td>
            <!-- 이전 -->
            <a href=./SearchServlet?command=scan&termStr=<%=prevStr%>&dbNo=<%=dbNo%>&fieldNo=<%=fieldNo%>&prevNum=<%=prevNum%>&nextNum=<%=nextNum%>>
            <img src=../search/img/page_prev.gif border=0 alt='이전'>
            </a>

            <!-- 이후 -->
            <a href=./SearchServlet?command=scan&termStr=<%=nextStr%>&dbNo=<%=dbNo%>&fieldNo=<%=fieldNo%>>
            <img src=/jsp/1/search/img/page_next.gif border=0 alt='다음'>
            </a>
        </td>
    <tr>
</table>
<!-- ========== 실제 페이지 이동 출력 끝 ========== -->



<!-- ========== 화면 좌우측 맞춤 이미지 (위 처음에 연결되어야 함.) ========== -->
        </td>
        <td align=right width=14><img src=/jsp/1/search/img/box_rb.gif></td>
    </tr>
</table>


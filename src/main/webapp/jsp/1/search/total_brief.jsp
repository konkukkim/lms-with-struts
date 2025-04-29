<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="javaclientapi.*" %>
<%@ page import="woorin.dasen.bean.*" %>
<%@ page errorPage="/jsp/1/search/error/error_jsp.jsp" %>
<%@ page import ="javax.sql.RowSet"%> 
<%@include file="../common/header.jsp" %>
<td colspan="2" width="100%" valign="top" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/title/search01.gif" width="721" height="51"></td>
               </tr>
              <!-- main 시작 -->
              <tr> 
                <td  valign="top" height="510" bgcolor="FEFEF6" align=center> 

<!-- 여기까지 LMS -->

<%
	boolean print_noresults = true;
%>
				

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
    int[] resultDBArr = null;

    HostParam hbean = (HostParam) request.getAttribute("HBEAN");

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
        resultDBArr = (int[]) request.getAttribute("resultDBArr");
    }

	int total_cnt = japi.DS_GetTotalDocCount(hptr);
%>

<%
    //### 맨처음 결과 셋으로 이동 ###//
    ret = japi.DS_SetResultPos(hptr, japi.DS_FIRST);

    for (int i=0; i < resultDBArr.length; i++)
    {
        if (resultDBArr[i] > 0)
        {
            setInfo = japi.DS_GetResultInfo(hptr);
%>

            <!-- 검색건수 및 페이지 출력 -->
            <jsp:include page="/jsp/1/search/common/search_cnt_table.jsp" flush="true">
            <jsp:param name="searchCnt" value="<%=setInfo.getSearchnum()%>" />
			<jsp:param name="dbList" value="<%=sbean.getDbList()%>" />
            <jsp:param name="dbNo" value="<%=setInfo.getSetno()%>" />
            <jsp:param name="kwStr" value="<%=kwStr%>" />
			<jsp:param name="dbStr" value="<%=dbStr%>" />
			<jsp:param name="cmpStr" value="<%=cmpStr%>" />
            <jsp:param name="expMode" value="<%=sbean.getExpMode()%>" />
            <jsp:param name="pageNum" value="<%=sbean.getPageNum()%>" />
			<jsp:param name="searchMode" value="<%=sbean.getSearchMode()%>" />
			<jsp:param name="sortFld" value="<%=sbean.getSortFld()%>" />
			<jsp:param name="sortMode" value="<%=sbean.getSortMode()%>" />
			<jsp:param name="comMode" value="total.brief" />
            </jsp:include>

			<!-- 검색 결과 내용 헤더 출력 -->
			<jsp:include page="/jsp/1/search/common/search_content_header.jsp" flush="true">
			<jsp:param name="dbNo" value="<%=setInfo.getSetno()%>" />
			</jsp:include>

			<!-- 검색 결과 내용 목록 출력 -->
<%			
			if (setInfo.getSetno() == getMultiDBNo("KTSET"))
			{
%>
				<jsp:include page="/jsp/1/search/common/search_content_ktset.jsp" flush="true">
				<jsp:param name="dbNo" value="<%=setInfo.getSetno()%>" />
				<jsp:param name="kwStr" value="<%=kwStr%>" />
				<jsp:param name="expMode" value="<%=sbean.getExpMode()%>" />
				</jsp:include>
<%
			}
			else if (setInfo.getSetno() == getMultiDBNo("lms_ebook"))
			{
%>
				<jsp:include page="/jsp/1/search/common/search_contents_lms_ebook.jsp" flush="true">
				<jsp:param name="dbNo" value="<%=setInfo.getSetno()%>" />
				<jsp:param name="kwStr" value="<%=kwStr%>" />
				<jsp:param name="expMode" value="<%=sbean.getExpMode()%>" />
				</jsp:include>
<%
			}
			else if (setInfo.getSetno() == getMultiDBNo("lms_course"))
			{
%>
				<jsp:include page="/jsp/1/search/common/search_contents_lms_course.jsp" flush="true">
				<jsp:param name="dbNo" value="<%=setInfo.getSetno()%>" />
				<jsp:param name="kwStr" value="<%=kwStr%>" />
				<jsp:param name="expMode" value="<%=sbean.getExpMode()%>" />
				</jsp:include>
<%
			}
			else
			{
%>
				<jsp:include page="/search/common/search_content_default.jsp" flush="true">
				<jsp:param name="dbNo" value="<%=setInfo.getSetno()%>" />
				<jsp:param name="kwStr" value="<%=kwStr%>" />
				<jsp:param name="expMode" value="<%=sbean.getExpMode()%>" />
				</jsp:include>
<%			} %>



<%
		}
		else if (print_noresults == true)
        {
%>
            <!-- 검색건수 및 페이지 출력 -->
            <jsp:include page="/jsp/1/search/common/search_cnt_table.jsp" flush="true">
            <jsp:param name="searchCnt" value="0" />
			<jsp:param name="dbList" value="<%=sbean.getDbList()%>" />
            <jsp:param name="dbNo" value="<%=sbean.getDbNoArr()[i]%>" />
            <jsp:param name="kwStr" value="<%=kwStr%>" />
			<jsp:param name="dbStr" value="<%=dbStr%>" />
			<jsp:param name="cmpStr" value="<%=cmpStr%>" />
            <jsp:param name="expMode" value="<%=sbean.getExpMode()%>" />
            <jsp:param name="pageNum" value="<%=sbean.getPageNum()%>" />
			<jsp:param name="searchMode" value="<%=sbean.getSearchMode()%>" />
			<jsp:param name="sortFld" value="<%=sbean.getSortFld()%>" />
			<jsp:param name="sortMode" value="<%=sbean.getSortMode()%>" />
			<jsp:param name="comMode" value="total.brief" />
            </jsp:include>

			<!-- 검색 결과 내용 헤더 출력 -->
			<jsp:include page="/jsp/1/search/common/search_content_header.jsp" flush="true">
			<jsp:param name="dbNo" value="<%=sbean.getDbNoArr()[i]%>" />
			</jsp:include>

			<!-- 검색 결과 내용 목록 출력 -->
			<jsp:include page="/jsp/1/search/common/search_content_empty.jsp" flush="true">
			<jsp:param name="dbNo" value="<%=sbean.getDbNoArr()[i]%>" />
            </jsp:include>


<%
        }

		// 다음 결과셋으로 이동
        ret = japi.DS_SetResultPos(hptr, japi.DS_NEXT);
    }
%>

    

<!-- 여기부터 LMS -->
				</td>
				<td valign="top" width="1" bgcolor="FEFEF6" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/white.gif" width="1" height="26"><br>
                  <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
              </tr>
              <!-- main 끝 -->
<%@include file="../common/footer.jsp" %>
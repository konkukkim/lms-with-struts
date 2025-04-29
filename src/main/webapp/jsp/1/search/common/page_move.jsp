<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.net.*" %>
<%@ page import="woorin.dasen.util.BeanUtil" %>
<%@ page errorPage="/jsp/1/search/error/error_jsp.jsp" %>

<%

    //### 사용 변수 선언 ###//
    String search_url = "./SearchServlet?command=search.brief";
    String kwStr, dbStr, cmpStr;
	String dbNo, dbList;
	String sortFld, searchMode, sortMode, expMode;
	int i, searchCnt, cur_page, total_page, start_page, end_page;
	int startNo, pageNum;

	kwStr = dbStr = cmpStr = "";
	dbNo = dbList = "";
	searchMode= sortMode = expMode = "";

    i = searchCnt = cur_page =  total_page = start_page = end_page = 0;
	startNo = pageNum = 0;

    kwStr = request.getParameter("kwStr");
    kwStr = BeanUtil.korEnc(kwStr);
    kwStr = URLEncoder.encode(kwStr);

	dbStr = request.getParameter("dbStr");
    dbStr = BeanUtil.korEnc(dbStr);
    dbStr = URLEncoder.encode(dbStr);

	cmpStr = request.getParameter("cmpStr");
    cmpStr = BeanUtil.korEnc(cmpStr);
    cmpStr = URLEncoder.encode(cmpStr);

    dbNo = request.getParameter("dbNo");
	dbList = request.getParameter("dbList");
	sortFld = request.getParameter("sortFld");
	searchMode = request.getParameter("searchMode");
	sortMode = request.getParameter("sortMode");
	expMode = request.getParameter("expMode");
    searchCnt = Integer.parseInt(request.getParameter("searchCnt"));
    startNo = Integer.parseInt(request.getParameter("startNo"));
    pageNum = Integer.parseInt(request.getParameter("pageNum"));

    if ((searchCnt % pageNum)==0)
    {
        total_page = (int) (searchCnt / pageNum);
    }
    else
    {
        total_page = (int) (searchCnt / pageNum) + 1;
    }

    cur_page = (int) (startNo / pageNum) + 1;

%>

<!-- ========== 실제 페이지 이동 출력 ========== -->
<table border=0 cellspacing=0 cellpadding=0>
    <tr>

<%
    if(cur_page > 1)
    {
%>
        <td>
<%
        // 처음
        if(cur_page > 2)
        {
%>
            <a href=<%=search_url%>&kwStr=<%=kwStr%>&dbStr=<%=dbStr%>&cmpStr=<%=cmpStr%>&dbList=<%=dbList%>&dbNo=<%=dbNo%>&startNo=1&pageNum=<%=pageNum%>&searchMode=<%=searchMode%>&sortFld=<%=sortFld%>&sortMode=<%=sortMode%>&expMode=<%=expMode%>><img src=../search/img/page_first.gif border=0 alt='처음'></a>&nbsp;
<%
        }

        // 이전
        int startPage = startNo - pageNum;
%>
    	    <a href=<%=search_url%>&kwStr=<%=kwStr%>&dbStr=<%=dbStr%>&cmpStr=<%=cmpStr%>&dbList=<%=dbList%>&dbNo=<%=dbNo%>&startNo=<%=startPage%>&pageNum=<%=pageNum%>&searchMode=<%=searchMode%>&sortFld=<%=sortFld%>&sortMode=<%=sortMode%>&expMode=<%=expMode%>><img src=../search/img/page_prev.gif border=0 alt='이전'></a>&nbsp;
        </td>

<%
    }

    // 전체 페이지
    if(total_page == 1)
    {
%>
        <td class=txt4>
            [ <span class=txt4><font color=red>1</font></span> ]</font></span>
        </td>
<%
    }

    if(total_page > 1 && total_page < 10)
    {
%>
        <td class=txt4>
<%
        for(i=1; i<=total_page; i++)
        {
            if(i == cur_page)
            {
%>
                [ <span class=txt4><font color=red> <%=i%> </font></span> ]</font></span>
<%
                continue;
            }
            int vStart = (i-1)*pageNum+1;
%>
            <a href=<%=search_url%>&kwStr=<%=kwStr%>&dbStr=<%=dbStr%>&cmpStr=<%=cmpStr%>&dbList=<%=dbList%>&dbNo=<%=dbNo%>&startNo=<%=vStart%>&pageNum=<%=pageNum%>&searchMode=<%=searchMode%>&sortFld=<%=sortFld%>&sortMode=<%=sortMode%>&expMode=<%=expMode%>> [ <span class=txt4> <%=i%> </span> ]</a>
<%
        }
%>
        </td>
<%
    }
    else if(total_page >= 10)
    {
        if(cur_page > 5)
        {
            start_page = cur_page - 5;
        }
        else
        {
            start_page = 1;
        }
        if(start_page + 9 > total_page)
        {
            end_page = total_page;
        }
        else
        {
            end_page = start_page + 9;
        }
%>
        <td class=txt4>
<%
        if(start_page != 1)
        {
%>
            ...
<%
        }
        for(i=start_page; i<=end_page; i++)
        {
            if(i == cur_page)
            {
%>
                [ <span class=txt4><font color=red> <%=i%> </font></span> ]</font></span>
<%
                continue;
            }
            int vStart = (i-1)*pageNum+1;
%>
            <a href=<%=search_url%>&kwStr=<%=kwStr%>&dbStr=<%=dbStr%>&cmpStr=<%=cmpStr%>&dbList=<%=dbList%>&dbNo=<%=dbNo%>&startNo=<%=vStart%>&pageNum=<%=pageNum%>&searchMode=<%=searchMode%>&sortFld=<%=sortFld%>&sortMode=<%=sortMode%>&expMode=<%=expMode%>> [ <span class=txt4> <%=i%> </span> ]</a>
<%
        }

        if(i != total_page + 1)
        {
%>
            ...
<%
        }
%>
        </td>
<%
    }

    // 다음페이지, 마지막페이지
    if(cur_page != total_page)
    {
%>
        <td>
<%
        // 다음
        if((startNo+pageNum) < (total_page-1)*pageNum)
        {
            int startPage = startNo + pageNum;
%>
            &nbsp; <a href=<%=search_url%>&kwStr=<%=kwStr%>&dbStr=<%=dbStr%>&cmpStr=<%=cmpStr%>&dbList=<%=dbList%>&dbNo=<%=dbNo%>&startNo=<%=startPage%>&pageNum=<%=pageNum%>&searchMode=<%=searchMode%>&sortFld=<%=sortFld%>&sortMode=<%=sortMode%>&expMode=<%=expMode%>><img src=../search/img/page_next.gif border=0 alt='다음'></a>
<%
        }
        int startPage = (total_page -1)*pageNum+1;
%>
        &nbsp; <a href=<%=search_url%>&kwStr=<%=kwStr%>&dbStr=<%=dbStr%>&cmpStr=<%=cmpStr%>&dbList=<%=dbList%>&dbNo=<%=dbNo%>&startNo=<%=startPage%>&pageNum=<%=pageNum%>&searchMode=<%=searchMode%>&sortFld=<%=sortFld%>&sortMode=<%=sortMode%>&expMode=<%=expMode%>><img src=../search/img/page_end.gif border=0 alt='마지막'></a>
<%
    }
%>
        </td>
    </tr>
</table>
<!-- ========== 실제 페이지 이동 부분 출력 끝 ========== -->
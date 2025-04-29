<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.net.*" %>
<%@ page import="woorin.dasen.util.BeanUtil" %>
<%@ page import ="com.edutrack.framework.util.StringUtil"%>
<%@ page errorPage="/jsp/1/search/error/error_jsp.jsp" %>
<%@ include file="/jsp/1/search/common/multi_db_no.jsp" %>
<%!
public String getMultiDBName(int dbNo)
    {
        String dbName = "";

        if (dbNo == 21 ){
			dbName = "영화이북";
		} else if (dbNo == 22 ){
			dbName = "영화강좌";
		}
        return dbName;
    }
%>
<%
    int searchCnt = Integer.parseInt(request.getParameter("searchCnt"));
	int dbList = Integer.parseInt(request.getParameter("dbList"));
    int dbNo = Integer.parseInt(request.getParameter("dbNo"));

	String expMode = request.getParameter("expMode");
    String pageNum = request.getParameter("pageNum");
    String kwStr = request.getParameter("kwStr");
	String dbStr = request.getParameter("dbStr");
	String cmpStr = request.getParameter("cmpStr");
	String comMode = request.getParameter("comMode");
	String searchMode = request.getParameter("searchMode");
	String sortFld = request.getParameter("sortFld");
	String sortMode = request.getParameter("sortMode");

    // 한글 인코딩 처리는 플랫폼에 따라 각기 다름.
    //kwStr=URLEncoder.encode(kwStr);
	dbStr=URLEncoder.encode(dbStr);
	cmpStr=URLEncoder.encode(cmpStr);

    String briefUrl = "./SearchServlet?command=search.brief";
	String totalbriefUrl = "./SearchServlet?command=total.brief";
%>
				<table width="690" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="10" ><img src="img/1/common/blank.gif" width="10" height="1"></td>
                      <td align="left"><img src="img/1/common/blank.gif" width="1" height="45"><br>
                        <img src="img/1/common/blank.gif" width="22" height="1"></td>
                    </tr>
<%
	if (comMode.equals("brief"))
	{
		String curPageNo = request.getParameter("curPageNo");
		String totalPageCnt = request.getParameter("totalPageCnt");
%>
           <td class=txt5><img src=/jsp/1/search/img/icon4.gif hspace=5> <%=getMultiDBName(dbNo)%> : <span class=txt6><%=searchCnt%></span>건
		   (<font color=red><%=curPageNo%></font> / <%=totalPageCnt%>)
		   </td>
		   <td class=txt5 align=right>
		   <a href="<%=totalbriefUrl%>&pageNum=5&dbList=<%=dbList%>&kwStr=<%=kwStr%>&dbStr=<%=dbStr%>&cmpStr=<%=cmpStr%>&expMode=<%=expMode%>&searchMode=<%=searchMode%>&sortFld=<%=sortFld%>&sortMode=<%=sortMode%>">전체목록</a>&nbsp;
		   </td>
<%
	}
	else
	{
%>

					<tr> 
                      <td>&nbsp;</td>
                      <td align="left" valign="top" class="brownb"> <%=getMultiDBName(dbNo)%> (총 <%=searchCnt%>개)</td>
		   			  <td align=right>
<%
		if(searchCnt > Integer.parseInt(pageNum))
		{
%>
<form method="post" name="SearchSelect<%=dbNo%>" action="/servlet/SearchServlet?command=total.brief">
<input type=hidden name=pageNum value=100>
<input type=hidden name=dbNoArr value='<%=dbNo%>'>
<input type=hidden name=kwStr value='<%=kwStr%>'>
		   <input type="image" src=/jsp/1/search/img/more.gif border=0>&nbsp;
</form>			   
<%
		}
	}
%>
		   
					 </tr>
				   </table>



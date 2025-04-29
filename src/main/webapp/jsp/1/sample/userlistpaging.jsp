<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.util.Map,java.util.ArrayList,com.edutrack.framework.persist.ListDTO,javax.sql.RowSet"%> 
<%  
    String left = "MyPage"; 
    String userType= "M";

%>
<%@include file="../common/header.jsp" %>
    <form name="f" method="post" action="<%=request.getContextPath()%>/Login.cmd?cmd=getUserPagingList" >
     <input type="hidden" name="curPage" >
       <%
          Map data = (Map)request.getAttribute("MODEL");
          ListDTO listObj = (ListDTO)data.get("userList"); 
       %> 
       
       <%= listObj.getPageScript("f", "curPage", "goPage") %>
    <table>
       <%
		  RowSet list= listObj.getItemList();	
       if(listObj.getItemCount() > 0){															
			while(list.next()){
		%>	
        <tr>   
         <td><%=StringUtil.nvl(list.getString("u_id"))%></td><td><%=StringUtil.nvl(list.getString("u_pw"))%></td>
        </tr>   
		<%
		   }
	   }else{
	   %>		
			<TR>
				<TD colspan="2"> 등록된 사용자가 없습니다.</TD>
			</TR>
      <% } %>
    </table>
    <table>
      <tr>
        <td>
          <%= listObj.getPagging(SYSTEMCODE,"goPage") %>
        </td>
      </tr>
    </table>  
</form>  
<%@include file="../common/footer.jsp" %>

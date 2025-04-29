<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,java.util.HashMap,java.util.ArrayList"%> 
<%@ page import ="com.edutrack.curriresearch.dto.ResResultDataDTO,com.edutrack.curriresearch.dto.ResUserResultDTO"%> 
<%
   response.setHeader("Content-Disposition", "attachment; filename=researchResult.xls");
   response.setHeader("Content-Description", "JSP Generated Data");
	Map model = (Map)request.getAttribute("MODEL");
    ResResultDataDTO data = (ResResultDataDTO)model.get("resultData");
    ArrayList userList = data.getUserList();
    HashMap answerList = data.getResultList();
    int contentsCnt = data.getContentsCnt();
%>
<html>
<table border=0 cellpadding=0 cellspacing=0 width=96%>
	<tr>
		<td height=30 align="center"> <font size=3><b>설문 결과</b></font> </td>
	</tr>
</table>
<table border=1 cellpadding=0 cellspacing=0 bordercolor="gray" width=96%>
 <tr bgcolor="#f3f3f3">
   <td>No</td><td>아이디</td><td>	이름</td><td>성별</td><td>연령</td>
   <% for(int i = 0; i < contentsCnt;i++){%>
   <td>설문항목<%=i+1%></td>
   <% } %>
 </tr>
   <% 
    ResUserResultDTO user = null;
    String[] answer = null;
    String userId = null;
    for(int i =0; i < userList.size();i++){
       user = (ResUserResultDTO)userList.get(i);
       userId = user.getUserId();
       answer = (String[])answerList.get(userId);     
    %>
    <tr bgcolor="#ffffff">
    <td><%=i+1%></td><td><%=userId%></td><td><%=user.getUserName()%></td><td><%=user.getSexType()%></td><td><%=user.getAge()%></td>
    <%
      if(answer != null){
      	for(int j = 0; j < contentsCnt;j++){
    %>
      <td><%=answer[j]%></td>
    <%}
       }else{
       for(int j = 0; j < contentsCnt;j++){%>
       <td>&nbsp;</td>
     <% } %>
   <% } %>
    </tr>   
 <% } %> 
 </table>
</html>
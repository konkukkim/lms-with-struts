<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.sample.dto.UserInfo,com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam"%> 
<%  
    String pMode = "MyPage";
    String userType= "M";
    String userId = "";
%>
<script language="javascript">
   function Change_Code(obj){
      alert(obj.value+"를 선택하였습니다.");
   }
</script>
<%@include file="../common/header.jsp" %>
<%= request.getServerName() + ":" + request.getServerPort()%><br>
    <table border=0 cellpadding=0 cellspacing=1 bgcolor="gray" width=96%>
    <form name="fo" method="post">       
		<tr bgcolor="#f3f3f3">
			<td align=center>아이디</td>
			<td align=center>패스워드</td>
		</tr>
       <%
          ArrayList list = (ArrayList)model.get("userList");
          UserInfo user = null;
          for(int i=0; i < list.size(); i++){
            user = (UserInfo)list.get(i);
       %>
		<tr bgcolor="#ffffff">
			<td align=center><%=user.getUserId()%></td>
			<td align=center><%=user.getUserPw()%></td>
        </tr>   
       <%
          }
       %>   
       <tr>
         <td bgcolor="#ffffff" colspan="2"> 날자 사용 : <br>
         <%
         	DateParam dateParam = new DateParam();
			dateParam.setCount(2);
			dateParam.setDateType(0);
			dateParam.setForm("fo");
			dateParam.setDate("date");
			dateParam.setYear("year");
			dateParam.setMonth("month");
			dateParam.setDay("day");
			dateParam.setHour("hour");
			dateParam.setMinute("minute");
         
	        out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("ds"))); 
         %>
         </td>
       </tr>  
       <tr>
         <td bgcolor="#ffffff"  colspan="2"> 셀렉트박스 코드 사용 : <br>
        <%
	        CodeParam param = new CodeParam();
			param.setSystemcode("1");
			param.setType("select");
			param.setName("soCode");
			param.setFirst("-- 코드 선택--");
			param.setEvent("Change_Code(this)");
			param.setSelected("C");        
            out.print(CommonUtil.getSoCode(param, "101"));
        %> 
         </td>
       </tr>  
       
       <tr>
         <td bgcolor="#ffffff"  colspan="2"> 체크박스 코드 사용 : <br>
        <%
	        CodeParam param1 = new CodeParam();
			param1.setSystemcode("1");
			param1.setType("check");
			param1.setName("checkSoCode");
			param1.setEvent("Change_Code(this)");
			param1.setSelected("C");        
            out.print(CommonUtil.getSoCode(param1, "101"));
        %> 
         </td>
       </tr>  
       <tr>
         <td bgcolor="#ffffff"  colspan="2"> 라디오 코드 사용 : <br>
        <%
	        CodeParam param2 = new CodeParam();
			param2.setSystemcode("1");
			param2.setType("radio");
			param2.setName("radioSoCode");
			param2.setEvent("Change_Code(this)");
			param2.setSelected("C");        
            out.print(CommonUtil.getSoCode(param2, "101"));
        %> 
         </td>
       </tr>  
       
    </form>
    </table>
<%@include file="../common/footer.jsp" %>
 
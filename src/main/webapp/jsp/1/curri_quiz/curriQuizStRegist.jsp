<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%> 
<%@include file="../common/popup_header.jsp" %>
<%
    String pCourseId = (String)model.get("pCourseId");
    String pCourseName = (String)model.get("pCourseName");
    String pContentsId = (String)model.get("pContentsId");
    String pContentsName = (String)model.get("pContentsName");
    String QuizCount = (String)model.get("QuizCount");
    String QuizPoint = (String)model.get("QuizPoint");
    String passCount = (String)model.get("passCount");
    String quizPass = (String)model.get("quizPass");
    String newQuizPass = (String)model.get("newQuizPass");
    int retVal = Integer.parseInt((String)model.get("retVal"));
     String LineBgColor = "#40B389";
%>
<script language="javascript">
	function click() {
		if (event.button == 2) {
			alert('왼쪽 마우스 버튼만 사용하세요');
			return;
		}
		if (event.button == 3) {
			alert('왼쪽 마우스 버튼만 사용하세요');
			return;
		}
	}
	document.onmousedown = click

	function OnKeyDown() {
		if (event.keyCode==93 || event.keyCode==116 ) {
		        event.keyCode=0; 
				alert('마우스 왼쪽 버튼만 사용가능합니다');
				return false;
		}
	}
	document.onkeydown = OnKeyDown
			
	
	
	function win_close(){
		
//		opener.location.reload();
		opener.changeCourseInfo();	// 목차페이지 자동업데이
	    self.close();

	}
	
	
</script>
<table width="100%" border="0" cellpadding=0 cellspacing=0>
  <tr> 
    <!-- (Width, Height ) Fix--><td width="10" height="10"><img src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/com_tit01.gif" width="10" height="10"></td>
    <!-- (Height ) Fix--><td width="560" height="10" background="/img/<%=SYSTEMCODE%>/popup/com_tit05.gif"></td>
    <!-- (Width, Height ) Fix--><td width="10" height="10"><img src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/com_tit02.gif" width="10" height="10"></td>
  </tr>
  <tr> 
    <!-- (Width) Fix--><td width="10" background="/img/<%=SYSTEMCODE%>/popup/com_tit05.gif">&nbsp;</td>
    <td width="560" height="35" align="center" background="/img/<%=SYSTEMCODE%>/popup/com_tit05.gif"><img src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/com_icon.gif" width="11" height="11"><span class="pop"><font color="#FFFFFF">&nbsp;</font></span><font color="#FFFFFF" size="3">
    <strong>&nbsp;단원명 : <%=pContentsName%> <br> [통과문항수 / 총문항수  : <%=QuizPoint%> / <%=QuizCount%>]&nbsp;</strong></font></td>
    <!-- (Width) Fix--><td width="10" background="/img/<%=SYSTEMCODE%>/popup/com_tit05.gif">&nbsp;</td>
  </tr>
  <tr>
    <!-- (Width, Height ) Fix--><td width="10" height="10"><img src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/com_tit03.gif" width="10" height="10"></td>
    <!-- (Height ) Fix--><td width="560" height="10" background="/img/<%=SYSTEMCODE%>/popup/com_tit05.gif"></td>
    <!-- (Width, Height ) Fix--><td width="10" height="10"><img src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/com_tit04.gif" width="10" height="10"></td>
  </tr>
  <tr> 
    <!-- (Width, Height ) Fix--><td width="10" height="6"></td>
    <!-- (Height ) Fix--><td width="560" height="6"></td>
    <!-- (Width, Height ) Fix--><td width="10" height="6"></td>
  </tr>
</table>

<table width="100%" border="0" cellspacing="1" cellpadding="2">
  <tr>
  	<td height=2 bgcolor="<%=LineBgColor%>"></td>
  </tr>
   		        <tr bgcolor="D5D5D5"> 
		          <td height="1"></td>
		        </tr> 
  <tr>
  	<td height=10></td>
  </tr>
  <tr> 
    <td width="100%" height="180" align='center'>
    <%if(retVal >0){%>
	 	단원평가 결과 : <%=passCount%> 문항을 맞추셨습니다.
	 	<%if(newQuizPass.equals("N")){%>
		통과 하지 못하셨으므로 재 응시 하여 꼭~ 통과 하세요.
		<%}%>
	<%}else{%>
		단원평가 결과 처리중 오류가 발생하였습니다.<br>
		다시 진행해 주시고 계속하여 오류 발생시 관리자에게 문의하여 주십시요.
	<%}%>
	</td>
  </tr>
  <tr>
  	<td height=10></td>
  </tr>  
  		        <tr bgcolor="D5D5D5"> 
		          <td height="1"></td>
		        </tr>
  <tr>
  	<td height=2 bgcolor="<%=LineBgColor%>"></td>
  </tr>
</table>
<br>
<table width="100%" border="0" cellpadding=0 cellspacing=0>
  <tr>
    <td width="35%"  align="center">
    <a href="javascript:win_close();"><img src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_co_comfirm.gif" class=no align=absmiddle></a>
    </td>
  </tr>
</table>
</body>
</html>
<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.eventquiz.dto.EventQuizInfoDTO"%>
<%@include file="../common/popup_header.jsp" %>
<%
	String pQuizId  = (String)model.get("pQuizId");

	EventQuizInfoDTO infoView = (EventQuizInfoDTO)model.get("quizinfoShow"); 
	String subject = infoView.getSubject();
	int quizcnt  = infoView.getQuizCnt();

	String chk1				=	"";
	String chk2				=	"";
	String chk3				=	"checked";

	//실시중
	if(infoView.getStatus().equals("Y")) {
		chk1				=	"checked";
		chk2				=	"";
		chk3				=	"";	
	//대기중
	}else if(infoView.getStatus().equals("N")) {
		chk1				=	  "";
		chk2				=	"checked";
		chk3				=	"";
	}
%>
<table width="370" border="0" cellpadding="0" cellspacing="1" bgcolor="#DBDBDB">
<form name=Input method="post" action="<%=CONTEXTPATH%>/EventQuiz.cmd?cmd=eventQuizStatRegist">
<input type='hidden' name ='pQuizId' value='<%=pQuizId%>'>
  <tr>
    <td align="center" bgcolor="#FFFFFF">
      <!--둘러싸고 있는 테이블과 안의 테이프 width값 차이 4px -->
      <table width="363" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <!-- (Height ) Fix--><td height="2"></td>
        </tr>
        <tr> 
          <td height="45" align="center" background="./img/<%=SYSTEMCODE%>/popup/com_bg.gif"><!-- Title --><font color="#FFFFFF" size="3"><strong> 이벤트 퀴즈</strong></font></td>
        </tr>
		<tr> 
		  <td height="5"></td>
		</tr>
		<tr> 
		  <td height="3" background="./img/<%=SYSTEMCODE%>/title/line_01.gif"></td>
		</tr>
		<tr> 
		  <td height="25">&nbsp;</td>
		</tr>
        <tr> 
          <td align="center">
            <!-- Contents Start-->
            <table width="335" border="0" cellspacing="5" cellpadding="0">
              <tr> 
                <td><B>이벤트퀴즈 상태를 변경합니다</B></td>
              </tr>
              <tr> 
                <td height="1" background="./img/<%=SYSTEMCODE%>/popup/score_dot.gif"></td>
              </tr>
				<tr>
					<td bgcolor="#ffffff" height='26'>제목 : <%=subject%></td>
				</tr>
				<tr>
					<td bgcolor="#ffffff" height='26'>상태 : 
					<input name="pStatus" type=radio class=no value="Y" <%=chk1%>><font color=blue>실시</font> &nbsp;&nbsp;&nbsp;
				<%
				if (quizcnt == 0) {
				%>
							<input name="pStatus" type=radio class=no value="N" <%=chk2%>><font color=red>대기</font>
							<hr size=1 noshade>
							대기상태를 실시상태로 변경하면 이미 실시중이던 이벤트는 중지됩니다.
				<%
				}
				else {
				%>
							<input name="pStatus" type=radio class=no value="E" <%=chk3%>><font color=#339900>종료</font>
							<hr size=1 noshade>
							이벤트를 종료하면 다시 변경할 수 없습니다.
				<%
				}
				%>
					</td>
				</tr>
            </table>
            <!--Contents End -->
          </td>
        </tr>
        <tr> 
          <!-- (Height ) Fix--><td height="20">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
</table>
<br>
<table width="100%" >
<tr> 
  <td align="center">
	<input type="image" src="./img/<%=SYSTEMCODE%>/button/modify.gif" class=no>
	<a href="javascript:window.close();"><img src="./img/<%=SYSTEMCODE%>/button/b_co_close.gif"></a>
  </td>
</tr>
</form>
</table>
<%@include file="../common/popup_footer.jsp" %>
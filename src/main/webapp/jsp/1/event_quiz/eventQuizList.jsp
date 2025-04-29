<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.eventquiz.dto.EventQuizInfoDTO"%> 
<%@include file="../common/header.jsp" %>
<script language="javascript">
	function goAdd(mode,quizid)
	{
		if(mode == "W") {
			document.location = "<%=CONTEXTPATH%>/EventQuiz.cmd?cmd=eventQuizWrite&pRegMode="+mode;
		}else{
			document.location = "<%=CONTEXTPATH%>/EventQuiz.cmd?cmd=eventQuizWrite&pRegMode="+mode+"&pQuizId="+quizid;
		}
	}

	function goStat(quizid)
	{
		var url = "<%=CONTEXTPATH%>/EventQuiz.cmd?cmd=eventQuizStatEdit&pQuizId="+quizid;
		ShowInfo = window.open(url,"poll_win","top=100,left=162,toolbar=0,scrollbars=yes,directories=0,status=0,menubar=0,resizable=yes,width=400,height=280");
	}

	function goResult(quizid)
	{
		document.location = "<%=CONTEXTPATH%>/EventQuiz.cmd?cmd=eventQuizAnswerList&pQuizId="+quizid;
	}

</script>
      <table width="538" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td height="10"></td>
        </tr>
        <tr> 
          <td><!-- title imgae --><img src="./img/<%=SYSTEMCODE%>/title/ad_quiz.gif"></td>
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
		  <td align='right' height="35"><img src="./img/<%=SYSTEMCODE%>/button/b_qu_regi.gif" onClick="goAdd('W','')" style="cursor:hand;"></td>
		</tr>
        <tr> 
          <td>
            <!--list table start -->
            <table width="538" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="2" bgcolor="<%=LineBgColor%>"></td>
              </tr>
              <tr> 
                <td height="25"><table width="538" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="40" align="center"><font color="606060"><strong>번호</strong></font></td>
                      <td width="1"><img src="./img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="240" align="center"><font color="606060"><strong>이벤트제목</strong></font></td>
                      <td width="1"><img src="./img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="73" align="center"><font color="606060"><strong>시작일자</strong></font></td>
                      <td width="1"><img src="./img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="73" align="center"><font color="606060"><strong>종료일자</strong></font></td>
                      <td width="1"><img src="./img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="55" align="center"><font color="606060"><strong>상태</strong></font></td>
                      <td width="1"><img src="./img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="55" align="center"><font color="606060"><strong>참여자</strong></font></td>
                      <td width="1"><img src="./img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="55" align="center"><font color="606060"><strong>결과</strong></font></td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td height="1" bgcolor="<%=LineBgColor%>"></td>
              </tr>
              <tr> 
                <td><table width="538" border="0" cellspacing="0" cellpadding="0">

<%
	int num = 0;
	String status = "";
	ArrayList list = (ArrayList)model.get("quizinfoList");
	EventQuizInfoDTO info = null;
	for(int i=0; i < list.size(); i++){
		info = (EventQuizInfoDTO)list.get(i);
		num = num + 1;

		//투표상태
		if(info.getStatus().equals("Y")) {
			status = "<a href=\"javascript:goStat('"+info.getQuizId()+"')\"><font color='blue'>실시</font></a>";
		}else if(info.getStatus().equals("N")) {
			status = "<a href=\"javascript:goStat('"+info.getQuizId()+"')\"><font color='red'>대기</font></a>";
		}else{
			status = "<font color=#339900>종료</font>";
		}
%>
                    <tr> 
                      <td width="40" height="20" align="center"><%=num%></td>
                      <td width="241" height="20">&nbsp;&nbsp;<a href="javascript:goAdd('E','<%=info.getQuizId()%>')" class="list" title="<%=info.getSubject()%>"><%=StringUtil.setMaxLength(info.getSubject(),30)%></a></td>
                      <td width="74" height="20" align="center"><%=DateTimeUtil.getDateType(1, info.getStartDate())%></td>
                      <td width="74" height="20" align="center"><%=DateTimeUtil.getDateType(1, info.getEndDate())%></td>
					  <td width="55" height="20" align="center"><%=status%></td>
                      <td width="55" height="20" align="center"><%=info.getQuizCnt()%></td>
                      <td width="55" height="20" align="center"><a href="javascript:goResult(<%=info.getQuizId()%>)"><img src="./img/<%=SYSTEMCODE%>/button/b_ad_result.gif" align="absmiddle"></a></td>
                    </tr>
                    <tr> 
                      <td height="3" colspan="7"  background="./img/<%=SYSTEMCODE%>/common/line_dot.gif"></td>
                    </tr>

<%
   }//end for
	
%>
                  </table></td>
              </tr>
              <tr> 
                <td height="2" bgcolor="<%=LineBgColor%>"></td>
              </tr>
            </table>
            <!--list table end -->
          </td>
        </tr>
      </table> 
      <!-- contents end -->
    </td>
<%@include file="../common/footer.jsp" %>

<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.framework.persist.ListDTO,javax.sql.RowSet,com.edutrack.eventquiz.dto.EventQuizAnswerDTO,com.edutrack.eventquiz.dto.EventQuizInfoDTO"%> 
<%@include file="../common/header.jsp" %>
<%
	int curPage = Integer.parseInt((String)model.get("curPage")); 
	String pQuizId  = (String)model.get("pQuizId");
	String pEtype  = (String)model.get("pEtype");
	EventQuizInfoDTO infoView = (EventQuizInfoDTO)model.get("quizinfoShow"); 
%>

<script language="javascript">
	function goAnswerList(type)
	{
		document.location = "<%=CONTEXTPATH%>/EventQuiz.cmd?cmd=eventQuizAnswerList&pEtype="+type+"&pQuizId=<%=pQuizId%>&pAnswer=<%=infoView.getQuizAnswer()%>&curPage=<%=curPage%>";
	}

	function goInfoList()
	{
		document.location = "<%=CONTEXTPATH%>/EventQuiz.cmd?cmd=eventQuizList";
	}

   function checkAll(){
            var form = document.f;	   
            if(form.checkYn.value == "Y") {
                form.checkYn.value = "N";
            	setChkboxAll(form, "pCHK",false);
            }else{
                form.checkYn.value = "Y";
            	setChkboxAll(form, "pCHK", true);
            }
	   }   

   function goEditFlag(flag){
		var form = document.f;	   
		var checkVal = getChkBoxByValue(form, "pCHK", "");
		
		if(checkVal == "") {
			if(flag == "Y") {
				alert("당첨자로 선정할 참여자를 선택하세요.");
			}else if(flag == "N") {
				alert("당첨취소할 참여자를 선택하세요.");
			}
			return;
		}
		
		form.pPrizeYn.value = flag;
		form.action="<%=CONTEXTPATH%>/EventQuiz.cmd?cmd=eventQuizAnswerEditFlag";
		form.submit();
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
		  <td height="35"><img src="./img/<%=SYSTEMCODE%>/popup/score_icon_01.gif">이벤트 제목:<%=infoView.getSubject()%></td>
		</tr>
		<tr>
		  <td align='right' height="35">
		    <img src="./img/<%=SYSTEMCODE%>/button/b_ad_all.gif" onClick="goAnswerList('A')" style="cursor:hand;">
		    <img src="./img/<%=SYSTEMCODE%>/button/b_ad_prize.gif" onClick="goAnswerList('B')" style="cursor:hand;">
		    <img src="./img/<%=SYSTEMCODE%>/button/b_ad_correct.gif" onClick="goAnswerList('C')" style="cursor:hand;">
		  </td>
		</tr>
        <tr> 
          <td>
            <!--list table start -->
            <table width="538" border="0" cellspacing="0" cellpadding="0">
			<%
				ListDTO listObj = (ListDTO)model.get("answerList"); 
			%>
			<%= listObj.getPageScript("f", "curPage", "goPage") %>
			<form name="f" method="post" action="<%=CONTEXTPATH%>/EventQuiz.cmd?cmd=eventQuizAnswerList" >
			<input type="hidden" name="curPage" value="1">
			<input type="hidden" name="checkYn" value="N">
			<input type="hidden" name="pQuizId" value="<%=pQuizId%>">
			<input type="hidden" name="pEtype" value="<%=pEtype%>">
			<input type="hidden" name="pPrizeYn">
              <tr> 
                <td height="2" bgcolor="<%=LineBgColor%>"></td>
              </tr>
              <tr> 
                <td height="25"><table width="538" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="40" align="center"><font color="606060"><strong>번호</strong></font></td>
                      <td width="1"><img src="./img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="217" align="center"><font color="606060"><strong>참여자</strong></font></td>
                      <td width="1"><img src="./img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="73" align="center"><font color="606060"><strong>선택답</strong></font></td>
                      <td width="1"><img src="./img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="86" align="center"><font color="606060"><strong>참여일</strong></font></td>
                      <td width="1"><img src="./img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="65" align="center"><font color="606060"><strong>당첨여부</strong></font></td>
                      <td width="1"><img src="./img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="55" align="center"><font color="606060"><strong><a href="javascript:checkAll();">선택</a></strong></font></td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td height="1" bgcolor="<%=LineBgColor%>"></td>
              </tr>
              <tr> 
                <td><table width="538" border="0" cellspacing="0" cellpadding="0">

<%
	String prize = "";
    int No = listObj.getStartPageNum();
	RowSet list= listObj.getItemList();	
	if(listObj.getItemCount() > 0){				
		while(list.next()){

			if(StringUtil.nvl(list.getString("prize_yn")).equals("Y")) prize = "<font color='blue'>당첨</font>";
			else prize = "<font color='red'>미당첨</font>";
%>	
                    <tr> 
                      <td width="40" height="20" align="center"><%=No%></td>
                      <td width="218" height="20">&nbsp;&nbsp;<%=StringUtil.nvl(list.getString("user_name"))%> (<%=StringUtil.nvl(list.getString("user_id"))%>)</td>
                      <td width="74" height="20" align="center"><%=list.getInt("answer_no")%></td>
                      <td width="87" height="20" align="center"><%=DateTimeUtil.getDateType(1, StringUtil.nvl(list.getString("reg_date")))%></td>
					  <td width="66" height="20" align="center"><%=prize%></td>
                      <td width="56" height="20" align="center"><input type="checkbox" name="pCHK" value="<%=StringUtil.nvl(list.getString("user_id"))%>" class=no></td>
                    </tr>
                    <tr> 
                      <td height="3" colspan="6"  background="./img/<%=SYSTEMCODE%>/common/line_dot.gif"></td>
                    </tr>

<%
			No = No-1;

		   }//end while
	 }else{
%>		
                    <tr> 
                      <td colspan="6" height="20" align="center"> 이벤트 참여자가 없습니다.</td>
                    </tr>
<%
	}//end if
 	list.close();
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
        <tr> 
          <td height="23" align="center" valign="bottom" class="page">
		<% if(listObj.getTotalItemCount() > 0){ %>
		<%= listObj.getPagging(SYSTEMCODE, "goPage") %>
		<% } %> 
		  </td>
        </tr>
        <tr> 
          <td height="15"></td>
        </tr>
		</form>
        <tr> 
          <td>
            <!--검색하기 & 글쓰기 버튼 start -->
            <table width="538" border="0" cellspacing="0" cellpadding="0">
			<td height=30 align=right>
				<img src="./img/<%=SYSTEMCODE%>/button/b_ad_pr_select.gif" onClick="javascript:goEditFlag('Y');" style="cursor:hand;">
				<img src="./img/<%=SYSTEMCODE%>/button/b_ad_pr_cancel.gif" onClick="javascript:goEditFlag('N');" style="cursor:hand;">
				<img src="./img/<%=SYSTEMCODE%>/button/list.gif" width="48" height="20" style="cursor:hand;" onClick="javascript:goInfoList();">
			</td>
			</table>
		  </td>
		</tr>
      </table> 
      <!-- contents end -->
    </td>
<%@include file="../common/footer.jsp" %>

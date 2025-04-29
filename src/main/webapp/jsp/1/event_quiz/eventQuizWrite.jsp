<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.eventquiz.dto.EventQuizInfoDTO"%> 
<%@include file="../common/header.jsp" %>
<%
	Map data = (Map)request.getAttribute("MODEL"); 
	String pRegMode = (String)model.get("pRegMode");
	String pQuizId  = (String)model.get("pQuizId");

	String subject  = "";
	String contents = "";
	int quizAnswer = 1;
	int exampleCnt = 0;
	String[] example = new String[5];

	//수정모드일 경우
	if(pRegMode.equals("E")) {
		EventQuizInfoDTO infoView = (EventQuizInfoDTO)data.get("quizinfoShow"); 
		subject = infoView.getSubject();
		contents = infoView.getContents();
		exampleCnt =  infoView.getExampleCnt();
		quizAnswer =  infoView.getQuizAnswer();

		for(int i = 0; i < exampleCnt; i++ ) { 
			example[i] =  infoView.getExample()[i];
		}
	}
%>

<script language="javascript">
	function submitCheck()
	{
		var f = document.Input;
		
	 	if(!validate(f)) 
        	return false;
        else
            return true;     
		
	}

<%	if(pRegMode.equals("E")) {	%>
	var trSeq = <%=exampleCnt%>;
<%	}else{	%>
	var trSeq = 2;
<%	}	%>

    function addTr()
    {
        var frm     = document.Input;

        ++trSeq;

		if(trSeq > 5 ) {
			alert("항목은 5개를 초과할 수 없습니다.");
			trSeq--;
			return;		
		}

		var idx = table1.rows.length;
		var row = table1.insertRow();
		row.id = "tr"+ trSeq;

		var cell;

		cell = row.insertCell();
		cell.align = 'left';
		cell.innerHTML  = '<input type=text name="pExample" value=""  size="50" maxlength="100" dispName="항목" notNull>\n'+
						  '<a href="JavaScript:deleteTr(tr'+ trSeq +');" title="항목삭제"><font color="red">[삭제]</font></a>\n'+
						  '';

		setEventHandler(frm);

	}

    function deleteTr(row)
    {
		trSeq--;
		table1.deleteRow(row.rowIndex);
    }


	function goDel(quiz_id) {
		var stat = confirm("이벤트퀴즈 정보를 삭제하시겠습니까?\n\n주의: 삭제된 자료는 복구가 불가능합니다.");
		if (stat == true) {
			document.location = "<%=CONTEXTPATH%>/EventQuiz.cmd?cmd=eventQuizDelete&pQuizId="+quiz_id;
		}
	}

	function goList() {
			document.location = "<%=CONTEXTPATH%>/EventQuiz.cmd?cmd=eventQuizList";
	}

</script>
      <table width="538" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td height="10"></td>
        </tr>
        <tr> 
          <td>
            <!-- title imgae -->
            <img src="./img/<%=SYSTEMCODE%>/title/ad_quiz.gif"></td>
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
          <td>
            <!--write form start -->
            <table width="538" border="0" cellspacing="0" cellpadding="0">
			<form name=Input method="post" action="<%=CONTEXTPATH%>/EventQuiz.cmd?cmd=eventQuizRegist" onSubmit="return submitCheck()">
			<input type='hidden' name ='pRegMode' value='<%=pRegMode%>'>
			<input type='hidden' name ='pQuizId' value='<%=pQuizId%>'>
			<input type='hidden' name ='pAnswerCnt' value=''>
            <tr> 
                <td height="2" bgcolor="<%=LineBgColor%>"></td>
              </tr>
              <tr> 
                <td height="4"><table width="538" border="0" cellspacing="1" cellpadding="2">
                    <tr> 
                      <td width="108" height="2"></td>
                      <td width="1" height="2"></td>
                      <td width="190" height="2"></td>
                      <td width="58" height="2"></td>
                      <td width="1" height="2"></td>
                      <td width="180" height="2"></td>
                    </tr>
                    <tr> 
                      <td width="108" height="20"><font color="606060"><strong>제목</strong></font></td>
                      <td width="1" height="20"><img src="./img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="190" height="20" colspan='4'><input type=text name="pSubject" value="<%=subject%>"  size="60" maxlength="100" dispName="제목" notNull></td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="108" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="190" height="1"></td>
                      <td width="58" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="180" height="1"></td>
                    </tr>
                    <tr> 
                      <td width="108" height="20"><font color="606060"><strong>내용</strong></font></td>
                      <td width="1" height="20"></td>
                      <td width="190" height="20"></td>
                      <td width="58" height="20"></td>
                      <td width="1" height="20"></td>
                      <td width="180" height="20"></td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="108" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="180" height="1"></td>
                      <td width="88" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="180" height="1"></td>
                    </tr>
                    <tr align="center"> 
                      <td height="20" colspan="6"><textarea name="pContents" cols='72' rows='8' wrap="VIRTUAL"  dispName="내용" notNull><%=contents%></textarea></td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="108" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="190" height="1"></td>
                      <td width="58" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="180" height="1"></td>
                    </tr>
                    <tr> 
                      <td width="108" height="20"><font color="606060"><strong>항목</strong></font>&nbsp;<span><a href="JavaScript:addTr();" title="항목추가"><font color='blue'>[추가]</font></a></span></td>
                      <td width="1" height="20"><img src="./img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td height="20" colspan='4'>
					  <!-- 항목 추가 -->
						<table id="table1" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="%" align="" class="">
							<%
								if(pRegMode.equals("E")) {
									for(int i = 0; i < exampleCnt; i++ ) { 
							%>
									<input type=text name="pExample" value="<%=example[i]%>"  size="50" maxlength="100" dispName="항목" notNull>	
							<%		
									}//end for
								}else{
							%>
									<input type=text name="pExample" value=""  size="50" maxlength="100" dispName="항목" notNull>
									<input type=text name="pExample" value=""  size="50" maxlength="100" dispName="항목" notNull>
							<%
								}
							%>
							</td>
						</tr>
						</table>
					  <!-- 항목 끝-->
					  </td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="108" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="180" height="1"></td>
                      <td width="88" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="180" height="1"></td>
                    </tr>
                    <tr> 
                      <td width="108" height="20"><font color="606060"><strong>정답</strong></font></td>
                      <td width="1" height="20"><img src="./img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="190" height="20" colspan='4'><input type=text name="pQuizAnswer" value="<%=quizAnswer%>"  size="1" maxlength="1" dispName="정답" notNull  dataType=number maxValue=5>번</td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="108" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="190" height="1"></td>
                      <td width="58" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="180" height="1"></td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td height="2" bgcolor="<%=LineBgColor%>"></td>
              </tr>
            </table>
            <!--write form end-->
          </td>
        </tr>
        <tr>
          <td height="4"></td>
        </tr>
        <tr> 
          <td align="right">
			<img src="./img/<%=SYSTEMCODE%>/button/list.gif" width="48" height="20" style="cursor:hand;" onClick="javascript:goList();">
			<%	if(pRegMode.equals("E")) {	%>
			<input type="image" src="./img/<%=SYSTEMCODE%>/button/modify.gif" class=no>
			<img src="./img/<%=SYSTEMCODE%>/button/delete_01.gif" width="48" height="20" style="cursor:hand;" onClick="javascript:goDel('<%=pQuizId%>')">
			<%	}else{	%>
			<input type="image" src="./img/<%=SYSTEMCODE%>/button/reg_01.gif" class=no>
			<%	}	%>
			<img src="./img/<%=SYSTEMCODE%>/button/cancel.gif" width="48" height="20" style="cursor:hand;" onClick="javascript:document.Input.reset();">
		</td>
        </tr>
        <tr> 
          <td height="50">&nbsp;</td>
        </tr>
	  </form>	
      </table> 
      <!-- contents end -->
    </td>
<%@include file="../common/footer.jsp" %>
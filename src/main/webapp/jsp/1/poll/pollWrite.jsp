<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam"%>
<%@ page import ="com.edutrack.poll.dto.InternetPollDTO"%>
<%@include file="../common/header.jsp" %>
<%
	Map data = (Map)request.getAttribute("MODEL");
	String pRegMode = (String)model.get("pRegMode");
	String pPollId  = (String)model.get("pPollId");

	String subject  = "";
	String status = "Y";
	String contents = "";
	int exampleCnt = 0;
	String[] example = new String[10];

	//수정모드일 경우
	if(pRegMode.equals("E")) {
		InternetPollDTO pollView = (InternetPollDTO)data.get("pollShow");
		subject = pollView.getSubject();
		status = pollView.getStatus();
		contents = pollView.getContents();
		exampleCnt =  pollView.getExampleCnt();

		for(int i = 0; i < exampleCnt; i++ ) {
			example[i] =  pollView.getExample()[i];
		}
	}
	String 	pMode 		=  (String)model.get("pMode");

%>

<script language="javascript">
	function submitCheck()
	{
		var f = document.Input;

	 	if(!validate(f))
        	return false;
//        else
//            return true;
		f.submit();

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

		if(trSeq > 10 ) {
			alert("항목은 10개를 초과할 수 없습니다.");
			trSeq--;
			return;
		}

		var idx = table1.rows.length;
		var row = table1.insertRow();
		row.id = "tr"+ trSeq;

		var cell;

		cell = row.insertCell();
		cell.align = 'left';
		cell.innerHTML  = '<input type=text name="pExample" value="" class=green size="60" maxlength="100" dispName="항목" notNull>\n'+
						  '<a href="JavaScript:deleteTr(tr'+ trSeq +');" title="항목삭제"><font color="red">[삭제]</font></a>\n'+
						  '';

		setEventHandler(frm);
    }

    function deleteTr(row)
    {
		trSeq--;
		table1.deleteRow(row.rowIndex);
    }


	function goDel(poll_id) {
		var stat = confirm("인터넷투표 정보를 삭제하시겠습니까?\n\n주의: 삭제된 자료는 복구가 불가능합니다.");
		if (stat == true) {
			document.location = "<%=CONTEXTPATH%>/Poll.cmd?cmd=pollDelete&pPollId="+poll_id;
		}
	}

	function goList() {
			document.location = "<%=CONTEXTPATH%>/Poll.cmd?cmd=pollList&pMode=<%=PMODE%>";
	}
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start   onSubmit="return submitCheck()"  -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/Poll.cmd?cmd=pollRegist">
<input type='hidden' name ='pRegMode' value='<%=pRegMode%>'>
<input type='hidden' name ='pPollId' value='<%=pPollId%>'>

											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">제목</td>
												<td class="s_tab_view02">
													<input type="text" name="pSubject" class="" size="60" maxlength="100" value="<%=subject%>"  dispName="제목" notNull>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">투표기간</td>
												<td class="s_tab_view02">
<%
	DateParam dateParam = new DateParam();
	dateParam.setCount(2);
	dateParam.setDateType(1);
	dateParam.setForm("Input");
	dateParam.setDate("pDate");
	dateParam.setYear("pYear");
	dateParam.setMonth("pMonth");
	dateParam.setDay("pDay");
	dateParam.setHour("pHour");
	dateParam.setMinute("pMinute");
	dateParam.setPosX(500);
	dateParam.setPosY(0);
    out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("ds")));
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">투표결과공개</td>
												<td class="s_tab_view02">
													<input type="radio" name="pStatus" value="Y" <%if(status.equals("Y")) { out.println("checked"); } %> class="no"> 공개
								                    <input type="radio" name="pStatus" value="N" <%if(status.equals("N")) { out.println("checked"); } %> class="no"> 비공개
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">내용</td>
												<td class="s_tab_view03">
													<textarea name="pContents" class="green" cols="80" rows="5" wrap="VIRTUAL"  dispName="내용" notNull><%=contents%></textarea>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">항목<a href="JavaScript:addTr();"><font color="#FF6600">[추가]</font></a></td>
												<td class="s_tab_view02">
													<table id="table1" border="0" width="100%" cellpadding="0" cellspacing="0">
														<tr>
															<td>
<%
	if (pRegMode.equals("E")) {
		for (int i = 0; i < exampleCnt; i++ ) {
%>
																<input type="text" name="pExample" class="green" size="60" value="<%=example[i]%>" maxlength="100" dispName="항목" notNull>
																<br>
<%
		}
	} else {
%>
																<input type="text" name="pExample" class="green" size="60" value="" maxlength="100" dispName="항목" notNull>
	                              								<br>
	                              								<input type="text" name="pExample" class="green" size="60" value="" maxlength="100" dispName="항목" notNull>
<%
	}
%>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
<%
	if(pRegMode.equals("E")) {
%>
&nbsp;<script language=javascript>Button3("수정", "submitCheck()", "");</script>
&nbsp;<script language=javascript>Button3("삭제", "goDel('<%=pPollId%>')", "");</script>
<%
	}else{
%>
&nbsp;<script language=javascript>Button3("등록", "submitCheck()", "");</script>
<%
	}
%>
												</td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>
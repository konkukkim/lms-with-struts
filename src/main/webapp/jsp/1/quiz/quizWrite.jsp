<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.common.dto.EditorParam"%>
<%@include file="../common/header.jsp" %>
<% 	String pCourseId = (String)model.get("pCourseId");
	String pContentsId = (String)model.get("pContentsId");
	String pQuizType = (String)model.get("pQuizType");
	String[] width = new String[]{"150","407"};
	String selK = ""; String selD =""; String selS = "";
	if(pQuizType.equals("K")) selK = " selected";
	if(pQuizType.equals("D")) selD = " selected";
	if(pQuizType.equals("S")) selS = " selected";
%>
<script language="javascript">
	function goList(){
		document.location.href="<%=CONTEXTPATH%>/Quiz.cmd?cmd=quizList&pCourseId=<%=pCourseId%>&pContentsId=<%=pContentsId%>";
	}

	function initWriteForm(type){
		var f = document.Input;
		if(type == 'K'){
			f.pExample1.value="";
			f.pExample2.value="";
			f.pExample3.value="";
			f.pExample4.value="";
			f.pExample5.value="";

			for(i=0;i<5;i++){
				f.pAnswerK[i].checked = false;
			}
		}else if(type == 'D'){
			f.pAnswerD.value="";
		}else if(type == 'S'){
			for(i=0;i<2;i++){
				f.pAnswerS[i].checked = false;
			}
		}
	}
	function change_type(type){
		var quizWriteKDiv = document.getElementById("quizWriteK");
		var quizWriteDDiv = document.getElementById("quizWriteD");
		var quizWriteSDiv = document.getElementById("quizWriteS");

		initWriteForm(type);
		if(type == 'K'){
			quizWriteDDiv.style.display = "none";
			quizWriteSDiv.style.display = "none";
			quizWriteKDiv.style.display = "block";
		}else if(type == 'D'){
			quizWriteSDiv.style.display = "none";
			quizWriteKDiv.style.display = "none";
			quizWriteDDiv.style.display = "block";
		}else if(type == 'S'){
			quizWriteDDiv.style.display = "none";
			quizWriteKDiv.style.display = "none";
			quizWriteSDiv.style.display = "block";
		}
	}
	function isEmpty(data)
	{
		if(data != null){
			for ( var i = 0 ; i < data.length ; i++ ) {
				if ( data.substring( i, i+1 ) != ' ' )
					return false;
			}
		}
		return true;
	}
	function getLength(str) {
		return (str.length + (escape(str) + "/%u").match(/%u/g).length-1);
	}
	function chkForm()
	{
		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */

		var f = document.Input;
		var quizType = f.pQuizType[f.pQuizType.selectedIndex].value;
		if(quizType == "K"){
			if(isEmpty(f.pContents.value)) {
				alert('문제를 입력하세요');
				return false;
			}
			for(i=1;i<=4;i++){
				if(isEmpty(f["pExample"+i].value)) {
					alert('선택형 보기예제는 필수4문항  입니다');
					f["pExample"+i].focus();
					return false;
				}
			}
			var cnt = 0;
			for(i=0;i<5;i++){
				if(f.pAnswerK[i].checked == true)
					cnt++;
			}

			if(cnt == 0) {
				alert('정답을 선택하십시요');
				return false;
			}
		}else if(quizType == "D"){//--단답형
			if(isEmpty(f.pContents.value)) {
				alert('문제를 입력하세요');
				return false;
			}

			if(isEmpty(f.pAnswerD.value)) {
				alert('정답을 입력하세요');
				f.pAnswerD.focus();
				return false;
			}

			if (getLength(f.pAnswerD.value) > 2000) {
				alert('정답의 길이가 너무 길어 입력이 불가능합니다.(2000자이내)');
				f.pAnswerD.focus();
				return false;
			}
		}else if(quizType == "S"){//--OX형
			if(isEmpty(f.pContents.value)) {
				alert('문제를 입력하세요');
				return false;
			}
			var cnt = 0;
			for(i=0;i<2;i++)
				if(f.pAnswerS[i].checked == true) cnt++;

			if(cnt == 0) {
				alert('정답을 선택하십시요');
				return false;
			}
		}
		f.submit();
	}
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<form method="post" name="Input" action = "<%=CONTEXTPATH%>/Quiz.cmd?cmd=quizRegist&pRegMode=ADD&pCourseId=<%=pCourseId%>&pContentsId=<%=pContentsId%>"  enctype="multipart/form-data">
<input type='hidden' name='pCourseId' value='<%=pCourseId%>'>
<input type='hidden' name='pContentsId' value='<%=pContentsId%>'>
<!-- contents start   onSubmit="return chkForm(this)" -->
											<tr>
												<td colspan="2" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%"></td>
															<td align=right width="50%" height=30>
<select name='pQuizType'onchange="change_type(this.value)">
	<option value='K' <%=selK%>>선택형</option>
	<option value='D' <%=selD%>>단답형</option>
	<option value='S' <%=selS%>>OX형</option>
</select>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">문제</td>
												<td class="s_tab_view03" width="200">
<textarea name="pContents" rows=9 cols=77></textarea>
<%
	EditorParam editerParam = new EditorParam();
	editerParam.setShowFlag("true");
	editerParam.setWidth(520);
	editerParam.setHeight(200);
	editerParam.setToolBarHidden("attachFile");
	out.print(CommonUtil.getEditorScript(editerParam));
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">첨부화일</td>
												<td class="s_tab_view03"><input type="file" name="pFile" size="60"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>

											<tr>
												<td colspan="2">
<!-- 객관식 -->
<div id="quizWriteK" style="display:block">
													<table width="100%" cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td class="s_tab_view01" width="120">보기1</td>
															<td class="s_tab_view03"><textarea name="pExample1" cols="77" wrap="VIRTUAL"></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">보기2</td>
															<td class="s_tab_view03"><textarea name="pExample2" cols="77" wrap="VIRTUAL"></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">보기3</td>
															<td class="s_tab_view03"><textarea name="pExample3" cols="77" wrap="VIRTUAL"></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">보기4</td>
															<td class="s_tab_view03"><textarea name="pExample4" cols="77" wrap="VIRTUAL"></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">보기5</td>
															<td class="s_tab_view03"><textarea name="pExample5" cols="77" wrap="VIRTUAL"></textarea></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td class="s_tab_view01" width="120">정답</td>
															<td class="s_tab_view02">
																1.<input type='radio' name="pAnswerK" style="border:0" value='1'>
																2.<input type='radio' name="pAnswerK" style="border:0" value='2'>
																3.<input type='radio' name="pAnswerK" style="border:0" value='3'>
																4.<input type='radio' name="pAnswerK" style="border:0" value='4'>
																5.<input type='radio' name="pAnswerK" style="border:0" value='5'>
															</td>
														</tr>
													</table>
</div>
<!-- 단답형 -->
<div id="quizWriteD" style="display:none">
													<table width="100%" cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td class="s_tab_view01" width="120">정답</td>
															<td class="s_tab_view03"><textarea name="pAnswerD" rows="3" cols="77"></textarea></td>
														</tr>
													</table>
</div>
<!-- OX형 -->
<div id="quizWriteS" style="display:none">
													<table width="100%" cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td class="s_tab_view01" width="120">정답</td>
															<td class="s_tab_view02">
																맞음:<input type="radio" name="pAnswerS" value="O" style=border=0>&nbsp;&nbsp;
																틀림:<input type="radio" name="pAnswerS" value="X" style=border=0>
															</td>
														</tr>
													</table>
</div>
												</td>
											</tr>

											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">문제에 대한 해설</td>
												<td class="s_tab_view03"><textarea name="pComment" rows="4" cols="77"></textarea></td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("등록", "chkForm()", "");</script>&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
												</td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->
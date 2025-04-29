<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.currisub.dto.CurriSubDTO, com.edutrack.common.DateSetter, com.edutrack.common.dto.DateParam, com.edutrack.common.dto.CodeParam, com.edutrack.common.dao.CommonDAO, com.edutrack.common.dto.CodeDTO"%>
<%@ page import ="com.edutrack.config.dao.ConfigSubDAO"%>
<%@include file="../common/header.jsp" %>
<%
	DateParam dateParam = null;
	String curriType = StringUtil.nvl((String)model.get("curriProperty2"),"R");

	String	pCurriCode = StringUtil.nvl(model.get("pCurriCode"));
	String	pCurriYear = StringUtil.nvl(model.get("pCurriYear"));
%>
<Script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriSubJogyoWork.js"></Script>
<Script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_sub/curriSubJogyo.js"></Script>
<script language="javascript">
<!--

	function submitCheck()
	{
		var f = document.Input;
<%
if(curriType.equals("R"))
{	//-- 정규과정의경우 일자 비교 한다.
%>
		var enrollStart = f.pEDate1.value;
		var enrollEnd = f.pEDate2.value;
		var cancelStart = f.pCDate1.value;
		var cancelEnd = f.pCDate2.value;
		var serviceStart = f.pSDate1.value;
		var serviceEnd = f.pSDate2.value;
		var chungEnd = f.pDate71.value;
		var assessmentEnd = f.pDate81.value;
		var completeEnd = f.pDate91.value;

		if(isEmpty(enrollStart)){
			alert('수강신청 시작일을 입력해 주세요');
			return false;
		}
		if(isEmpty(enrollEnd)){
			alert('수강신청 종료일을 입력해 주세요');
			return false;
		}
		if(isEmpty(cancelStart)){
			alert('수강취소 시작일을 입력해 주세요');
			return false;
		}
		if(isEmpty(cancelEnd)){
			alert('수강취소 종료일을 입력해 주세요');
			return false;
		}
		if(isEmpty(serviceStart)){
			alert('수강기간 시작일을 입력해 주세요');
			return false;
		}
		if(isEmpty(serviceEnd)){
			alert('수강기간 종료일을 입력해 주세요');
			return false;
		}
		if(isEmpty(chungEnd)){
			alert('청강종료일을 입력해 주세요');
			return false;
		}
		if(isEmpty(assessmentEnd)){
			alert('평가종료일을 입력해 주세요');
			return false;
		}
		if(isEmpty(completeEnd)){
			alert('수료종료일을 입력해 주세요');
			return false;
		}

		if(parseFloat(cancelStart) < parseFloat(enrollStart)){
			alert('수강취소 시작일은 수강신청일과 같거나 이후여야 합니다.');
			return false;
		}
		//if(parseFloat(serviceStart) <= parseFloat(cancelEnd)){
		//	alert('수강시작일은 수강취소 종료일 이후여야 합니다.');
		//	return false;
		//}
		if(parseFloat(chungEnd) < parseFloat(serviceEnd)){
			alert('청강 종료일은 수강 종료일과 같거나 이후여야 합니다.');
			return false;
		}
		if(parseFloat(assessmentEnd) < parseFloat(serviceEnd)){
			alert('평가 종료일은 수강 종료일과 같거나 이후여야 합니다.');
			return false;
		}
		if(parseFloat(completeEnd) < parseFloat(assessmentEnd)){
			alert('수료처리 종료일은 평가 종료일과 같거나 이후여야 합니다.');
			return false;
		}
<%
} //-- 정규 과정의 경우 End
%>

		if(!validate(f)) return false;

		f.submit();
	}

	function goList() {
		document.location = "<%=CONTEXTPATH%>/CurriSub.cmd?cmd=curriSubList&pCateCode=<%=model.get("pCateCode")%>&pCurriCode=<%=model.get("pCurriCode")%>&pCurriYear=<%=model.get("pCurriYear")%>";
	}
-->
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--

	function selectarticle(selected) {
		var frm	=	document.Input;
		if (selected == '') {
			initSel();
			frm.pDeptSocode.options[0] = new Option ('-- 소속코드 --');
			frm.pDeptSocode.options[0].value = '';
		}
<%
   	CommonDAO common = new CommonDAO();
   	ArrayList codeList = null;
	codeList = common.getDeptDaeCode(SYSTEMCODE);
	int	cntDaeCode	=	codeList.size();
	String[][]	DaeCode = new String[2][cntDaeCode];
	CodeDTO code = null;
	for(int i = 0; i < cntDaeCode; i++){
		code = (CodeDTO)codeList.get(i);
		DaeCode[0][i]	=	code.getCode();
		DaeCode[1][i]	=	code.getName();
%>
		else if (selected == '<%=DaeCode[0][i]%>') {
			initSel();
<%
		CodeDTO code2 = null;
		ArrayList codeList2 = null;
		codeList2 = common.getDeptSoCode(SYSTEMCODE, code.getCode());
		for(int j = 0; j < codeList2.size(); j++){
			code2 = (CodeDTO)codeList2.get(j);
%>
			frm.pDeptSocode.options[<%=j%>] = new Option ('<%=code2.getName()%>');
			frm.pDeptSocode.options[<%=j%>].value = '<%=code2.getCode()%>';
<%
		}	// for
%>
		}
<%
	}	// for
%>
	}

	function initSel() {
		var frm	=	document.Input;
		with( frm ){
			frm.pDeptSocode.value = "";
			frm.pDeptSocode.length = 0; //셀렉트박스 초기화
		}
	}

	function setHakgiDate() {
		var url = "<%=CONTEXTPATH%>/CurriDate.cmd?cmd=curriDateList&pGubun=popup&pCurriCode=<%=pCurriCode%>&pCurriYear=<%=pCurriYear%>";
		window.open(url, "setHakgi", "toolbar=no,location=no, width=500, height=500,top=0, left=0, directories=no,status=no,menubar=no,scrollbars=yes,resizable=no");
	}

	
	function chgCerYn(val) {
	    
	    tmpVal = val-1;
	    document.all.score[0].style.display="none";
	    document.all.score[1].style.display="none";

	    document.all.score[tmpVal].style.display="";
	    
	    if(tmpVal=="1"){
	        document.all.pEvalGubun[0].disabled = true ;
	        document.all.pEvalGubun[1].disabled = true ;
	    }else{
	        document.all.pEvalGubun[0].disabled = false ;
	        document.all.pEvalGubun[1].disabled = false ;
        }

	}
	
//-->
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- FORM STARRT   onSubmit="return submitCheck()" -->
<form name="Input" method="post" action = "<%=CONTEXTPATH%>/CurriSub.cmd?cmd=curriSubRegist&pMode=<%=PMODE %>&pRegMode=Add" enctype="multipart/form-data">
<input type="hidden" name="pCurriCode" value="<%=model.get("pCurriCode")%>">
<input type="hidden" name="pCateCode" value="<%=model.get("pCateCode")%>">
<!-- 과정유형 ==> 정규과정 : R 상시과정 : O 공개과정 : P -->
<input type="hidden" name="pCurriType" value="<%=curriType%>">
<input type="hidden" name="pCredit" value="<%=model.get("pCredit")%>">
<%
if(!curriType.equals("R"))
{	//-- 공개과정일경우...
%>
<input type="hidden" name="pCurriYear" value="<%=model.get("pCurriYear")%>">
<%
}
%>


											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="140">과정명</td>
												<td class="s_tab_view02" colspan="3"><input type="text" name="pCurriName" size=60 maxlength=100 dispName="과정명" notNull value="<%=model.get("curriName")%>"> <!-- (<%=model.get("pCurriCode")%>) --></td>
											</tr>
<%	if(curriType.equals("P")) {	%>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">담당교수</td>
												<td class="s_tab_view02" colspan="3">
<%
		CodeParam param1 = new CodeParam();
		param1.setSystemcode(SYSTEMCODE);
		param1.setType("select");
		param1.setName("pProfId");
		param1.setFirst("-- 교수자선택--");
		//param1.setEvent("Change_Code()");
		//param1.setSelected(profId);
		out.print(CommonUtil.getUserList(param1,"P"));
%>
												</td>
											</tr>
<%	
	}
	if(!curriType.equals("P")) {	%>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">담당교수</td>
												<td class="s_tab_view02">
<%
		CodeParam param1 = new CodeParam();
		param1.setSystemcode(SYSTEMCODE);
		param1.setType("select");
		param1.setName("pProfId");
		param1.setFirst("-- 교수자선택--");
		//param1.setEvent("Change_Code()");
		//param1.setSelected(profId);
		out.print(CommonUtil.getUserList(param1,"P"));
%>
												</td>
												<td class="s_tab_view01">과목구분</td>
												<td class="s_tab_view02">
<%
		CodeParam param2 = new CodeParam();
		param2.setSystemcode(SYSTEMCODE);
		param2.setType("select");
		param2.setName("pCourseType");
		param2.setFirst("--과목구분선택--");
		//param2.setEvent("Change_Code()");
		//param2.setSelected(profId);
		out.print(CommonUtil.getSoCode(param2,"120"));
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">수강료</td>
												<td class="s_tab_view02" colspan="3">
													<input type="text" name="pPrice" size=12 maxlength=7 style="text-align:right" dispName="수강료" notNull dataType='number' value="0"> 원
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">과정개설년도</td>
												<td class="s_tab_view02">
<input type="text" name=pCurriYear size=4 maxlength=4 value="<%=model.get("pCurriYear")%>" dispName="과정개설년도" notNull readonly > 년도
<%	if(curriType.equals("R")) { %>&nbsp;<a href="javascript:setHakgiDate()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_termdata.gif" border="0" align="absmiddle"></a><%}%></td>
												<td class="s_tab_view01">과정개설학기</td>
												<td class="s_tab_view02">
<input type="text" name=pHakgiTerm  size=4 maxlength=4  style="text-align:right" dispName="과정개설학기" notNull dataType='number' value=""> 학기
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%
		if (curriType.equals("R")) {	//-- 정규과정의경우 일자를 입력 받는다.
%>
											<tr>
												<td class="s_tab_view01">수강신청기간</td>
												<td class="s_tab_view02" colspan="3">
<%
			dateParam = new DateParam();
			dateParam.setCount(2);
			dateParam.setDateType(1);
			dateParam.setForm("Input");
			dateParam.setDate("pEDate");
			dateParam.setYear("pEY");
			dateParam.setMonth("pEM");
			dateParam.setDay("pED");
			dateParam.setHour("pEHH");
			dateParam.setMinute("pEMI");

			out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("enroll_date")));
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">수강정정(취소)기간</td>
												<td class="s_tab_view02" colspan="3">
<%
			dateParam = new DateParam();
			dateParam.setCount(2);
			dateParam.setDateType(1);
			dateParam.setForm("Input");
			dateParam.setDate("pCDate");
			dateParam.setYear("pCY");
			dateParam.setMonth("pCM");
			dateParam.setDay("pCD");
			dateParam.setHour("pCHH");
			dateParam.setMinute("pCMI");

			out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("cancel_date")));
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">수강기간</td>
												<td class="s_tab_view02" colspan="3">
<%
			dateParam = new DateParam();
			dateParam.setCount(2);
			dateParam.setDateType(1);
			dateParam.setForm("Input");
			dateParam.setDate("pSDate");
			dateParam.setYear("pSY");
			dateParam.setMonth("pSM");
			dateParam.setDay("pSD");
			dateParam.setHour("pSHH");
			dateParam.setMinute("pSMI");
			dateParam.setPosX(760);
			dateParam.setPosY(0);

			out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("service_date")));
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">청강종료일</td>
												<td class="s_tab_view02" colspan="3">
<%
			dateParam = new DateParam();
			dateParam.setCount(1);
			dateParam.setDateType(1);
			dateParam.setForm("Input");
			dateParam.setDate("pDate7");
			dateParam.setYear("pCHEY");
			dateParam.setMonth("pCHEM");
			dateParam.setDay("pCHED");
			dateParam.setHour("pHour7");
			dateParam.setMinute("pMinute7");

			out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("chung_end")));
%>&nbsp;&nbsp;<font class="s_text03">* 수강종료일보다 크거나 같게 해 주세요</font>
												 </td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">평가종료일</td>
												<td class="s_tab_view02" colspan="3">
<%
			dateParam = new DateParam();
			dateParam.setCount(1);
			dateParam.setDateType(1);
			dateParam.setForm("Input");
			dateParam.setDate("pDate8");
			dateParam.setYear("pREY");
			dateParam.setMonth("pREM");
			dateParam.setDay("pRED");
			dateParam.setHour("pHour8");
			dateParam.setMinute("pMinute8");

			out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("assessment_end")));
%>&nbsp;&nbsp;<font class="s_text03">* 수강종료일보다 크거나 같게 해 주세요</font>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">수료처리종료일</td>
												<td class="s_tab_view02" colspan="3">
<%
			dateParam = new DateParam();
			dateParam.setCount(1);
			dateParam.setDateType(1);
			dateParam.setForm("Input");
			dateParam.setDate("pDate9");
			dateParam.setYear("pCOEY");
			dateParam.setMonth("pCOEM");
			dateParam.setDay("pCOED");
			dateParam.setHour("pHour9");
			dateParam.setMinute("pMinute9");

			out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("complete_end")));
%>&nbsp;&nbsp;<font class="s_text03">* 수강종료일보다 크거나 같게 해 주세요</font>
												</td>
											</tr>

<%											
ConfigSubDAO configSubDao = new ConfigSubDAO();
String configVal = StringUtil.nvl(configSubDao.getConfigSubValue(SYSTEMCODE, "400", "003"));


// 학점처리만 할 경우
if(("1").equals(configVal)){
%>											
<input type=hidden name="pScoreGubun" value="1">
<%
}
// 수료처리만 할 경우
else if(("2").equals(configVal)){
%>
<input type=hidden name="pScoreGubun" value="2">
<input type=hidden name="pEvalGubun"  value="">
<%
}
// 학점처리, 수료처리 둘다
else if(("3").equals(configVal)){
%>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">성적처리방식</td>
												<td class="s_tab_view02" colspan="3">
													<input type="radio" name="pScoreGubun" value="1" style="border:0" onClick="javacript:chgCerYn('1')" checked ><font class="s_text01">학점처리</font>&nbsp;&nbsp;&nbsp;
													<input type="radio" name="pScoreGubun" value="2" style="border:0" onClick="javacript:chgCerYn('2')" >수료처리</font>
													&nbsp;&nbsp;<font class="s_text03">
													<div id="score" style="display:">* 학점처리는 성적이 학점(A,B+ 등..)처리가 되며, 수료증 발급이 되지 않습니다</div>
													<div id="score" style="display:none">* 수료처리는 성적이 점수로 산정되며, 수료증이 발급됩니다</div>
													</font>
												</td>
											</tr>
<%
}
if(("1").equals(configVal) || ("3").equals(configVal) ){
%>

											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">평가방식</td>
												<td class="s_tab_view02" colspan="3">
													<input type="radio" name="pEvalGubun" value="1" style="border:0" checked ><font class="s_text01">상대평가</font>&nbsp;&nbsp;&nbsp;
													<input type="radio" name="pEvalGubun" value="2" style="border:0" >절대평가</font>
													<% if(("3").equals(configVal)){ %>&nbsp;&nbsp;<font class="s_text03">* 학점처리시에만 선택하세요</font><% } %>
												</td>
											</tr>
<%
}
%>

											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">인원제한유무</td>
												<td class="s_tab_view02">
													<input class=no type=radio value="Y" name=pLimitYn checked><font class="s_text01">사용</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input class=no type=radio value="N" name=pLimitYn><font class="s_text02">사용안함</font>
												</td>
												<td class="s_tab_view01">제한인원</td>
												<td class="s_tab_view02">
													<input type="text" name="pLimitNum" maxlength=4 size=4 style="text-align:right" dispName="제한인원" dataType="number" value="0"> 명
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">취소가능경과일</td>
												<td class="s_tab_view02">
													<input type="text" name="pCancelConfigDay" maxlength=3 size=4 style="text-align:right" value="0" dispName="취소가능경과일" dataType='number'> 일
												</td>
												<td class="s_tab_view01">과락점수</td>
												<td class="s_tab_view02">
													<input type="text" name="pCompleteCourse" maxlength=3 size=4 style="text-align:right" value="40" dispName="과락점수" notNull dataType='number'> 점
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">담당조교</td>
												<td class="s_tab_view02" Style="padding-top:5px" colspan=3>
													<div id="jogyoSelectDiv" style="width:100%;display:none;"></div>
													<Script>Button5("담당조교추가", "getJogyoSelect()", "");</Script>
													<input type=hidden name=pJogyoId value="" size=40>
												</td>
											</tr>											
<%
		//-- 정규 과정의 경우 End
		}
		else {
		//-- 상시 과정의 경우
%>
											<tr>
												<td class="s_tab_view01">수강일수</td>
												<td class="s_tab_view02" colspan="3">
													<input type="text" name="pServiceStart" maxlength=3 size=4 dispName="수강일수" value="" style="text-align:right" dataType='number'> 일
												</td>
											</tr>
<%
		}
	}	//end 공개강좌가 아닐때
%>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %><script language=javascript>Button3("등록", "submitCheck()", "");</script><%	}	%>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
												</td>
											</tr>

										</table>

										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						

</form>

<%@include file="../common/footer.jsp" %>


<Script Language=javascript>
    init('<%=CONTEXTPATH %>', '<%=SYSTEMCODE %>','W' );
</Script>  
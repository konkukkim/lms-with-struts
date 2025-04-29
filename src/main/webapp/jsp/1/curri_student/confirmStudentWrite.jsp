<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.curristudent.dto.StudentDTO,com.edutrack.common.dto.CodeParam"%> 
<%@include file="../common/popup_header.jsp" %>
<%
	String pCurriCode = (String)model.get("pCurriCode");
	String pCurriYear = (String)model.get("pCurriYear");
	String pCurriTerm = (String)model.get("pCurriTerm");
	String selCurri = pCurriCode+"|"+pCurriYear+"|"+pCurriTerm;
%>
<script language="javascript">
	function submitCheck()
	{
		var f = document.Input;
		if(f.pCurriYearTerm[f.pCurriYearTerm.selectedIndex].value == "") {
			alert("개설과정을 선택하세요!");
			return false;
		} else {
			var str = f.pCurriYearTerm[f.pCurriYearTerm.selectedIndex].value;
			var strs = str.split("\|");
			f.pCurriCode.value = strs[0];
			f.pCurriYear.value = strs[1];
			f.pCurriTerm.value = strs[2];
		}
		if(!validate(f)) return false;
		else return true;
	}
	function goUserSearch() {
		window.open("<%=CONTEXTPATH%>/User.cmd?cmd=searchUserList", "usersearch","toolbar=no,location=no, width=500, height=300,top=50, left=50, directories=no,status=no,menubar=no,scrollbars=yes,resizable=no");
	}
</script>
<form name="Input" method="post" action="<%=CONTEXTPATH%>/Student.cmd?cmd=masterEnrollRegist&regMode=Confirm" onSubmit="return submitCheck()">
<input type="hidden" name="pCurriCode">
<input type="hidden" name="pCurriYear">
<input type="hidden" name="pCurriTerm">

<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
<tr>
<td width="500" height="42" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/pop_title08.gif">&nbsp;</td>
</tr>
<tr>
<td width="500">

	<!-- 라운드 박스 -->
	<table width="98%" border="0" cellpadding="0" cellspacing="0" align="center">
		<tr>
		<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_01.gif" width="7" height="6"></td>
		<td width="100%" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_05.gif"></td>
		<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_03.gif" width="7" height="6"></td>
		</tr>
		<tr>
		<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_07.gif"></td>
		<td >
		<br>

			<table width="90%" class="search" align="center" border="0" cellpadding="0" cellspacing="0" >
				<tr> 
				<td height="2" class="b_td01" colspan="3"> </td>
				</tr>
				<tr>
				<td align="right"  width="110" height="32" bgcolor="#F8F6F3"><b><font color="#9A8B7C">과정명</font></b></td>
				<td align="center"  class="nu02" width="4" height="32">
				<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21" border="0"> </td>
				<td align="left"  class="nu02" width="300" height="32">
				&nbsp;
<%
	CodeParam param1 = new CodeParam();
	param1.setSystemcode(SYSTEMCODE); 
	param1.setType("select"); 
	param1.setName("pCurriYearTerm"); 
	param1.setFirst("-- 개설 과정 선택--"); 
	//param1.setEvent("Change_Code()"); 
	param1.setSelected(selCurri); 
	out.print(CommonUtil.getCurrentCurriList(param1,"",""));
%>
				</td>
				</tr>
				<tr>
				<td height="1"  class="b_td04" colspan="3" width="422"></td>
				</tr>
				<tr>
				<td align="right"  width="110" height="30" bgcolor="#F8F6F3"><b><font color="#9A8B7C">사용자 ID</font></b></td>
				<td align="center"  class="nu02" width="4" height="30">
				<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21" border="0"> </td>
				<td align="left"  class="nu02" width="300" height="30">
				&nbsp;
				<input type="text" name="pId" size="15" maxlenght="15" dispName="사용자ID" notNull>
            <a href="javascript:goUserSearch()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/btn_search1.gif" align="absmiddle" border="0"></a> 
				</td>
				</tr>
				<tr>
				<td height="1" class="b_td06" colspan="3" width="422"></td>
				</tr>
				<tr >
				<td  class="nu02" colspan="3" width="422" height="43">&nbsp;&nbsp;* 현재 
					진행중인 개설 과정이나 수강신청 중인 개설 
					과정에만 수강신청을<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;하실 
					수 있습니다.
				</td>
				</tr>
				<tr>
				<td align="center"  class="nu02" colspan="3" height="28" width="422"><input type="image" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bttn_ok.gif" border="0" class="solid0"></td>
				</tr>
			</table>

		</td>
		<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_08.gif"></td>
		</tr>
		<tr>
		<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_02.gif" width="7" height="6"></td>
		<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_06.gif"></td>
		<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_04.gif" width="7" height="6"></td>
		</tr>
	</table>
	<!-- 라운드 박스 -->

</td>
</tr>
<tr>
<td width="500" height="30" align="right"><a href="javascript:window.close();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/close.gif" border="0"></a></td>
</tr>
</table>

</form>
 <%@include file="../common/popup_footer.jsp" %>
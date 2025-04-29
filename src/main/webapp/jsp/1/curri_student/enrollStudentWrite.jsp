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
<form name=Input method="post" action="<%=CONTEXTPATH%>/Student.cmd?cmd=masterEnrollRegist&regMode=Enroll" onSubmit="return submitCheck()">
<input type="hidden" name="pCurriCode">
<input type="hidden" name="pCurriYear">
<input type="hidden" name="pCurriTerm">
<table width="500" border="0" cellpadding="0" cellspacing="1" bgcolor="#DBDBDB">
  <tr>
    <td align="center" bgcolor="#FFFFFF">
      <!--둘러싸고 있는 테이블과 안의 테이프 width값 차이 4px -->
      <table width="500" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <!-- (Height ) Fix--><td height="2"></td>
        </tr>
        <tr> 
          <td height="45" align="center" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/com_bg.gif"><!-- Title -->
          <font color="#FFFFFF" size="3"><strong>수강 신청 추가</strong></font></td>
        </tr>
		<tr> 
		  <td height="5"></td>
		</tr>
		<tr> 
		  <td height="2" background="./img/<%=SYSTEMCODE%>/title/line_01.gif"></td>
		</tr>
		<tr> 
		  <td height="25">&nbsp;</td>
		</tr>
        <tr> 
          <td align="center">
          	<table width="500" border="0" cellspacing="0" cellpadding="0">
	          <tr> 
	            <td height="2" bgcolor="#40B389"></td>
	          </tr>
	          <tr>
	          	<td height="4"><table width="500" border="0" cellspacing="0" cellpadding="2">
	                <tr> 
	                  <td width="120" height="2"></td>
	                  <td width="1" height="2"></td>
	                  <td width="140" height="2"></td>
	                  <td width="100" height="2"></td>
	                  <td width="1" height="2"></td>
	                  <td width="138" height="2"></td>
	                </tr>	               
	                 <tr> 
	                  <td width="120" height="20"><font color="606060"><strong>개설과정선택</strong></font></td>
	                  <td width="1" height="20"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
	                  <td height="20" colspan="4">&nbsp;<%
					    CodeParam param1 = new CodeParam();
					    param1.setSystemcode(SYSTEMCODE); 
					    param1.setType("select"); 
					    param1.setName("pCurriYearTerm"); 
					    param1.setFirst("-- 개설 과정 선택--"); 
					    //param1.setEvent("Change_Code()"); 
					    param1.setSelected(selCurri); 
					    out.print(CommonUtil.getCurrentCurriList(param1,"",""));
				  %></td>
	                </tr>
	                 <tr bgcolor="D5D5D5"> 
	                  <td width="120" height="1"></td>
	                  <td width="1" height="1"></td>
	                  <td width="140" height="1"></td>
	                  <td width="100" height="1"></td>
	                  <td width="1" height="1"></td>
	                  <td width="138" height="1"></td>
	                </tr>
	                 <tr> 
	                  <td width="158" height="20"><font color="606060"><strong>사용자ID</strong></font></td>
	                  <td width="1" height="20"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
	                  <td height="20" colspan="4">&nbsp;<input type=text name=pId size=15 maxlenght=15 dispName="사용자ID" notNull>
	                  <a href="javascript:goUserSearch()"><img src="./img/<%=SYSTEMCODE%>/button/b_my_search.gif" align="absmiddle"></a>
	                </tr>
	                <tr bgcolor="D5D5D5"> 
	                  <td width="120" height="1"></td>
	                  <td width="1" height="1"></td>
	                  <td width="140" height="1"></td>
	                  <td width="100" height="1"></td>
	                  <td width="1" height="1"></td>
	                  <td width="138" height="1"></td>
	                </tr> 
        			</table>     
	    		</td>
	          </tr>
	          <tr> 
	            <td height="2" bgcolor="#40B389"></td>
	          </tr>
	          <tr> 
	            <td height="5" >&nbsp;</td>
	          </tr>
	          <tr> 
	            <td>현재 진행중인 개설 과정이나 수강신청 중인 개설 과정에만<br>수강신청을 하실 수 있습니다.</td>
	          </tr>
	        </table>
	      	 </td>
	        </tr>
	      </table>
	       <table border=0 cellpadding=0 cellspacing=0 width=500>
			<tr>
				<td height=30 align=center>
					<input type=image src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_co_comfirm.gif" align=absmiddle class=no>&nbsp;					
					<a href="javascript:self.close();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_co_close.gif" align=absmiddle class=no></a>				
				</td>
			</tr>
			<tr>
				<td height=3></td>
			</tr>
			</form>
			</table>   
<%@include file="../common/popup_footer.jsp" %>
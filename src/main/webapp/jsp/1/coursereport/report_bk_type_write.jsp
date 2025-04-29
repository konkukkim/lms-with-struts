<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.coursereport.dto.ReportBankInfoDTO"%> 
<%@include file="../common/popup_header.jsp" %>	
<%
	    ReportBankInfoDTO bankInfo = (ReportBankInfoDTO)model.get("bankInfo");
	    String pMODE        = (String)model.get("pMODE");
	    String pCourseId    = (String)model.get("pCourseId");
	    String pBankCode    = (String)model.get("pBankCode");
	    String[] width      = new String[]{"100","300"};
	    String LineBgColor  = "#40B389";
		String msg		=	"추가하고자 하는 항목을 입력하세요.";
		String titleimg	=	"center17_img11_2.gif";
		if (pMODE.equals("write")) {
			msg	=	"추가하고자 하는 항목을 입력하세요.";
			titleimg	=	"center17_img11_2.gif";
		}
		else {
			msg	=	"수정하고자 하는 항목을 입력하세요.";
			titleimg	=	"center17_img11_3.gif";
		}
	%>
<Script Language=javascript>
	function Regist_Bank(){
	  var form = document.Input;
	  
	  if(!validate(form)) return;
	  
	  form.submit();
	}
</Script>



<table width="95%" height="42" border="0" cellspacing="0" cellpadding="0" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/center/<%=titleimg%>"> 
	<tr>
		<td></td>
	</tr>
</table>





<form name="Input" method="post" action="<%=CONTEXTPATH%>/ReportBank.cmd?cmd=reportBankInfoRegist">
<input type="hidden" name="pMODE" value="<%=pMODE%>">
<input type="hidden" name="pCourseId" value="<%=pCourseId %>">
<input type="hidden" name="pBankCode" value="<%=pBankCode %>">
<table width="95%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="2" class="b_td01"></td>
	</tr>
	<tr>
		<td height="30" class="b_td02">
			<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
				<tr align="center" valign="middle">
					<td class="b_td02" align="center">
					<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" width="7" height="7" vspace="2" hspace="2" align="absmiddle">
					<%=msg%>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" class="b_td03"> </td>
	</tr>
	<tr>
		<td>
			<!-- 리스트 -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				
				<tr>
					<td height="25" onMouseOver="this.className='tdcolor2';" onMouseOut="this.className='tdcolor1'">
						<table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="center">
								<br><br>
								<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon08.gif" width="7" height="7" vspace="2" hspace="2" align="absmiddle">
								항목명 :
								<input type="text" name="pBankName" size="40" value="<%=bankInfo.getReportBkName()%>" notNull dispName="항목명" lenCheck="25">
								<br><br><br>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td height="1" class="b_td03" ></td>
				</tr>

			</table>
			<!-- 리스트 -->
		</td>
	</tr>
</table>
<br>

<a href="javascript:Regist_Bank();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/<%=( pMODE.equals("write")? "bttn_ok.gif" : "bttn_edit.gif" ) %>" align="absmiddle" border="0"></a>
<a href="javascript:window.close();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bttn_close.gif" align="absmiddle" border="0"></a>


</form>

<Script Language="javascript">
document.Input.pBankName.focus();
</Script>
<%@include file="../common/popup_footer.jsp" %>	
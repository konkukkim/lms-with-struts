<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../../common/popup_header.jsp" %>	
<%
    String userType = (String)model.get("userType");
    String[] width = new String[]{"150","349"};
    String LineBgColor = "#91C694";
%>
<Script Language="javascript">
<!--
	function SubmitCheck()
	{
		var f = document.Regist;
		
		if(!validate(f)) return;
	    
	    var file = f.pUserPicture.value;
	    var fileInfo = file.split(".");
	    
	    if(fileInfo[1] !=  "csv" && fileInfo[1] !=  "CSV"){
	       alert("파일 형태가 csv인 형태만 가능합니다.");
	       return;
	    }
	    
		f.submit();
    }
//-->
</Script>
<form name=Regist method="post"  enctype="multipart/form-data" action="<%=CONTEXTPATH%>/UserAdmin.cmd?cmd=batchUserRegist">
<input type=hidden name="userType" value="<%=userType%>">
	<table width="100%" height="100%" border="0">
		<tr> 
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">사용자일괄등록</font></td>
			<td class="pop_topright"></td>
		</tr>
		<tr> 
			<td class="pop_midleft"></td>
			<td valign="top" class="pop_form">
				<!-- 내용 -->
				<table width="90%" height="100%" align="center">
					<tr height="97%">
						<td align="left" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td class="s_tab_view02" colspan="2">
										<img src="/img/1/common/blet04.gif" align="absmiddle" width="12" height="11" border="0">
										파일로 사용자 정보 생성을 합니다.<br> 아래의 사항을 준수하여 파일을 작성하기 바랍니다.

										<font color="black" style="font-size:9pt;line-height:12pt;font-family:굴림체">먼저 사용자 등록 파일 <a href="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName=newuserlist.csv&sFileName=newuserlist.csv&filePath=/files/common/">newuserlist.csv</a>을 다운을 받습니다.</font><br><br>
								 	    <font color="brown" style="font-size:9pt;line-height:12pt;font-family:굴림체">주의사항 => 자료를 작성하실때 빈란으로 두지 마시고, 스페이스 한칸이라도 입력하셔야 합니다. </font><br><br>
								 	    <font color="black" style="font-size:9pt;line-height:12pt;font-family:굴림체"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon05.gif" valign="absmiddle"> 소속대코드, 소속소코드, 사용자ID, 이름, 암호, 주민등록번호(구분자 '-'포함 14자리), 우편번호(구분자 '-'포함 7자리), 주소, 전화번호(자택),전화번호(핸드폰), 이메일 순으로 작성</font><br><br>
							            <font color="red" style="font-size:9pt;line-height:12pt;font-family:굴림체">※ 일반회인인 경우 소속대코드 - 100, 소속소코드 - 100 번 입력</font><br><br>
									</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td align="center" colspan="2" class="s_tab_view01"><input type="file" name="pUserPicture" class=green size="40" dispName="파일업로드" notNull lenCheck=200 ></td></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view02">
										&nbsp;&nbsp;* <font color="#FF6600">확장자가 CSV or
										TXT 파일만 업로드 하실 수 있습니다.</font><br>
									</td>
								</tr>

								<tr>
									<td colspan="2" align="center" valign="top" class="pop_btn">
										<script language=javascript>Button3("등록", "SubmitCheck()", "");</script>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<!-- // 내용 -->
			</td>
			<td class="pop_midright"></td>
		</tr>
		<tr height="6"> 
			<td class="pop_bottomleft"></td>
			<td class="pop_bottombg"></td>
			<td class="pop_bottomright"></td>
		</tr>
		<tr> 
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif" onClick="window.close()" style="cursor:hand"></td>
		</tr>
	</table>

</form>
<%@include file="../../common/popup_footer.jsp" %>
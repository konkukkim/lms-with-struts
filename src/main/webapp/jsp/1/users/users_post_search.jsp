<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.user.dto.PostCodeDTO"%>
<%@include file="../common/popup_header.jsp" %>
<%	ArrayList postList  = (ArrayList)model.get("postList");
    String pForm        = (String)model.get("pForm");
    String pZip         = (String)model.get("pZip");
    String pAddr        = (String)model.get("pAddr");
    String LineBgColor  = "#40B389";	%>

<Script Language="javascript">
	function ClickZipCode(zip, addr)
	{
		var f = opener.document.<%=pForm %>;

		f.<%=pZip  %>.value = zip;
		f.<%=pAddr %>.value = addr;
		f.<%=pAddr %>.focus();
		self.close();
	}
    function formCheck(){
	  var form = document.ZipSearch;
	  if(!validate(form)) return;
	  form.submit();
	}
</Script>

<table width="400" height="300" border="0" cellpadding="5" cellspacing="0">
<!-- form start -->
<form name=ZipSearch method=post action="<%=CONTEXTPATH%>/Common.cmd?cmd=searchPost">
<input type=hidden name=pForm value="<%=pForm%>">
<input type=hidden name=pZip  value="<%=pZip%>">
<input type=hidden name=pAddr value="<%=pAddr%>">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">우편번호검색</font></td>
			<td class="pop_topright"></td>
		</tr>
		<tr>
			<td class="pop_midleft"></td>
			<td valign="top" class="pop_form">
				<!-- 내용 -->
				<table width="90%" height="100%" align="center">
					<tr height="97%">
						<td align="left" valign="top">
							<table width="92%" border="0" cellspacing="0" cellpadding="0">
								<tr class="s_tab01">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td class="s_tab_view02" colspan="2" align="center">
										<input type="text" name=pDong size=20 maxlength=20 style="ime-mode:active" dispName="주소" notNull lenCheck=20 >
										<a href="javascript:formCheck();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_comsearch.gif" align="absmiddle"></a>
									</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view02" colspan="2" align="center"> ( 예: 도곡동 , 상일동 , 삼전동 ) </td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td colspan="2">
										<table height="120" width="100%" border="0" cellspacing="0" cellpadding="0">
<%	//-- 우편번호 리스트

	PostCodeDTO postCode = null;

    if (postList.size() > 0 ) {

    	for (int i =0; i < postList.size(); i++) {
    		postCode = (PostCodeDTO)postList.get(i);	%>
											<tr>
												<td class="s_tab_view02"><a href="javascript:ClickZipCode('<%=postCode.getPostCode()%>','<%=postCode.getAddr()%>')" onMouseOver="window.status='우편번호선택';return true" onMouseOut="window.status='';return true"><%= postCode.getPostCode()%></a></td>
												<td class="s_tab_view02"><a href="javascript:ClickZipCode('<%=postCode.getPostCode()%>','<%=postCode.getAddr()%>')" onMouseOver="window.status='우편번호선택';return true" onMouseOut="window.status='';return true"><%=postCode.getFullAddr()%></a></td>
											</tr>
											<tr>
												<td height="1" colspan="2" class="c_test_tablien01"> </td>
											</tr>
<%		}
	} else {	%>
											<tr>
												<td class="s_tab_view02" colspan="2" align="center">검색된 우편번호가 없습니다.</td>
											</tr>
<%	}	%>
										</table>
									</td>
								</tr>
								<tr class="s_tab05">
									<td colspan="2"></td>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a href="javascript:window.close()" onMouseOver="window.status='창을 닫습니다.';return true" onMouseOut="window.status='';return true"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>


</form>
<!-- form end --->
</table>

<%@include file="../common/popup_footer.jsp" %>
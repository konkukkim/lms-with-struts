<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/header.jsp" %>
<%
	String pConfigType = (String)model.get("pConfigType");
	String pConfigCode = (String)model.get("pConfigCode");
	RowSet rs= (RowSet)model.get("rs");
	rs.next();
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

	function goList() {
		document.location = "<%=CONTEXTPATH%>/ConfigSub.cmd?cmd=configSubList&pMode=MyPage&pConfigType=<%=pConfigType%>";
	}
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start   onSubmit="return submitCheck()"  -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/ConfigSub.cmd?cmd=confgiSubRegist">
<input type=hidden name='pConfigType' value='<%=pConfigType%>'>
<input type=hidden name='pConfigCode' value='<%=pConfigCode%>'>
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">설정코드</td>
												<td class="s_tab_view02" width="200">
													<%=pConfigCode%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">설정명</td>
												<td class="s_tab_view02" colspan="3"><input type=text name="pTypeName" size=65 value="<%=StringUtil.nvl(rs.getString("type_name"))%>"  maxlength=100 dispName="설정명" notNull></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">설정값</td>
												<td class="s_tab_view02" colspan="3"><input type=text name="pConfigValue" size=65 value="<%=StringUtil.nvl(rs.getString("config_value"))%>"  maxlength=200 dispName="설정명" notNull></td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
                                                <% if(CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %><script language=javascript>Button3("수정", "submitCheck()", "");</script><%	}	%>
                                                &nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
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

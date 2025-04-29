<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/header.jsp" %>
<script language="javascript">
	function goFtp(){
		var url = "<%=CONTEXTPATH%>/ConfigSub.cmd?cmd=webFtpTest";
		window.open(url,"ftp_win","top=100,left=162,toolbar=0,scrollbars=yes,directories=0,status=0,menubar=0,width=720,height=450,resizable=yes");
	}
</script>


										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="3" class="s_btn01">
													<table border="0" width="100%" align="left">
														<tr>
<%
	int no=0;
	String pConfigType = (String)model.get("pConfigType");
  	RowSet listTop= (RowSet)model.get("rsTop");

	while(listTop.next()){

		if(pConfigType.equals(StringUtil.nvl(listTop.getString("config_type")))){
			out.println("<td>&nbsp;<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/blet01.gif' align='absmiddle'><b>"+StringUtil.nvl(listTop.getString("config_name"))+"</b></td>");
		} else {
%>
															<td align="left">
																<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" width="18" height="16" align="absmiddle">
																<a href="<%=CONTEXTPATH%>/ConfigSub.cmd?cmd=configSubList&pMode=MyPage&pConfigType=<%=StringUtil.nvl(listTop.getString("config_type"))%>"><%=StringUtil.nvl(listTop.getString("config_name"))%></a>
															</td>
<%		}

		no++;

		if(no%3 == 0)	out.println("</tr>");
	}

	if(no%3 == 1)
		out.println("</tr>");

	listTop.close();
%>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="3"></td>
											</tr>
											<tr class="s_tab02">
												<td width="370">설정항목</td>
												<td class="s_tablien"></td>
												<td width="399">설정값</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="3"></td>
											</tr>
<%
	int		i		=	0;
    RowSet	list	=	(RowSet)model.get("rsSub");
    String viewLink = "";
    while (list.next()) {
    	i++;
		if(i > 1) {
%>
											<tr class="s_tab03">
												<td colspan="3"></td>
											</tr>
<%		} 
	
	if(CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  {
		viewLink = "<a href='"+CONTEXTPATH+"/ConfigSub.cmd?cmd=configSubEditForm&pMode=MyPage&pConfigType="+pConfigType+"&pConfigCode="+StringUtil.nvl(list.getString("config_code"))+"'>"+StringUtil.nvl(list.getString("type_name"))+"</a>";
	}else{
		viewLink = StringUtil.nvl(list.getString("type_name"));
	}

%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04"><%=viewLink %></td>
												<td></td>
												<td class="s_tab04"><%=StringUtil.nvl(list.getString("config_value"))%></td>
											</tr>
<%
	}
	list.close();
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>













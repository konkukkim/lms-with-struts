<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/course_header.jsp" %>
<%
    String pResId = (String)model.get("pResId");
    String pResNo = (String)model.get("pResNo");
%>
<Script Language="javascript">
    function goCancel(){
       var f = document.f;
       var resid = f.pResId.value;
 	   var loc="<%=CONTEXTPATH%>/ResearchResult.cmd?cmd=getResearchResult&pResId="+resid;
	   document.location = loc;
    }
</Script>
								<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"설문결과관리")%></b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									</td>
									<!-- // history -->
								</tr>
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/ResearchInfo.cmd?cmd=researchInfoWrite" >
<input type=hidden name="pMODE" value="">
<input type=hidden name="pResId" value="<%=pResId%>">
<input type=hidden name="pResNo" value="<%=pResNo%>">

											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="80">설문자명</td>
												<td class="s_tablien"></td>
												<td width="">진술내용</td>

											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%	int No = 0;
    RowSet list = (RowSet)model.get("answerList");

	if (list != null) {

		while (list.next()) {
			No++;
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No %></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("user_name"))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.getHtmlContents(StringUtil.nvl(list.getString("answer")))%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%		}

		list.close();
	}

	if (No < 1) {	%>
											<tr>
												<td class="s_tab04_cen" colspan="11">진술내용이 존재하지 않습니다.</td>
											</tr>
<%	}	%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<script language=javascript>Button3("결과보기", "goCancel()", "");</script>
												</td>
											</tr>
</form>
<!-- form end -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>

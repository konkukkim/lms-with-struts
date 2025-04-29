<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/course_header.jsp" %>
<script language="javascript">
    function Show_AnswerUser(resid){
 	    var loc="<%=CONTEXTPATH%>/ResearchResult.cmd?cmd=getResearchUserList&pResId="+resid;
		document.location = loc;
    }

    function Show_ResearchContents(resid){
       var f = document.f;

       f.pResId.value = resid;
       f.action="<%=CONTEXTPATH%>/ResearchContents.cmd?cmd=researchContentsList";
       f.submit();
    }

    function Show_ResearchInfo(resid){
 	   var loc="<%=CONTEXTPATH%>/ResearchResult.cmd?cmd=researchResultInfoShow&pResId="+resid;
 	   var winOption = "left="+windowLeftPosition(500)+",top="+windowTopPosition(300)+",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=no,resizable=no,width=500,height=300";
	   ShowInfo = window.open(loc,"researchinfoshow",winOption);
    }

    function Show_ResearchResult(resid){
 	   var loc="<%=CONTEXTPATH%>/ResearchResult.cmd?cmd=getResearchResult&pResId="+resid;
	   document.location = loc;
    }

</script>
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

											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="">설문명</td>
												<td class="s_tablien"></td>
												<td width="150">설문기간</td>
												<td class="s_tablien"></td>
												<td width="80">설문정보</td>
												<td class="s_tablien"></td>
												<td width="80">설문결과</td>

											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%	int No = 0;
    RowSet list = (RowSet)model.get("resList");

	if (list != null) {

		while (list.next()) {
			No++;
			if(No > 1) {
%>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%			} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04"><a href="javascript:Show_AnswerUser('<%=list.getInt("res_id")%>');"><%=StringUtil.nvl(list.getString("subject"))%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("start_date")))+" ~ "+DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("end_date")))%></td>
												<td></td>
												<td class="s_tab04_cen"><a
												href="javascript:Show_ResearchInfo('<%=list.getInt("res_id")%>');"><b>[보기]</b></a></td>
												<td></td>
												<td class="s_tab04_cen"><a
												href="javascript:Show_ResearchResult('<%=list.getInt("res_id")%>');"><b>[결과]</b></a></td>
											</tr>
<%		}

		list.close();
	}

	if (No < 1) {	%>
											<tr>
												<td class="s_tab04_cen" colspan="11">설문리스트가 존재하지 않습니다.</td>
											</tr>
<%	}	%>
											<tr class="s_tab05">
												<td colspan="11"></td>
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

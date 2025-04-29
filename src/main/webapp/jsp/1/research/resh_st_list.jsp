<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/course_header.jsp" %>

<script language="javascript">
    function Create_Research(){
       var f = document.f;

       f.pMODE.value = "write";
       f.submit();
    }

    function Show_ResearchInfo(resid){
        var Page = "<%=CONTEXTPATH%>/ResearchInfo.cmd?cmd=researchStInfoShow&pResId="+resid;

        var winOption = "left="+windowLeftPosition(425)+",top="+windowTopPosition(320)+",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=auto,resizable=no,width=600,height=400";

		ShowInfo = window.open(Page,"researchinfoshow", winOption);
    }

    function research_win(resid){
	   var f = document.f;

	   var loc="<%=CONTEXTPATH%>/ResearchContents.cmd?cmd=researchContentsShow&pMODE=R&pResId="+resid;

	   var winOption = "left="+windowLeftPosition(620)+",top="+windowTopPosition(560)+",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=yes,resizable=no,width=620,height=560";

	   ShowInfo = window.open(loc,"research",winOption);
	}
</script>

							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"설문")%></b></font></td>
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
												<td width="100">설문참여</td>

											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%	int No = 0;
    RowSet list = (RowSet)model.get("resList");
    long startDt = 0;
    long endDt = 0;
    long curDate = Long.parseLong(CommonUtil.getCurrentDate());

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
												<td class="s_tab04_cen"><%=No %></td>
												<td></td>
												<td class="s_tab04"><a href="javascript:Show_ResearchInfo('<%=list.getInt("res_id")%>');"><%=StringUtil.nvl(list.getString("subject"))%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("start_date")))+" ~ "+DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("end_date")))%></td>
												<td></td>
												<td class="s_tab04_cen">
<%			startDt = Long.parseLong(StringUtil.nvl(list.getString("start_date")));
    		endDt = Long.parseLong(StringUtil.nvl(list.getString("end_date")));

			if (startDt <= curDate && endDt >= curDate) {
   				if (list.getInt("attend_yn") < 1) {	%>
													<a href="javascript:research_win('<%=list.getInt("res_id")%>');"><b><font color="#3073C4">[참여하기]</font></b></a>
<%				} else {	%>
													<font color="red">완료</font>
<%				}
			} else if (endDt < curDate) {	%>
													<font color=blue>설문종료</font>
<%			}	%>
												</td>
											</tr>
<%		}

		list.close();
	}

	if (No < 1) {	%>
											<tr>
												<td class="s_tab04_cen" colspan="11">설문이 존재하지 않습니다.</td>
											</tr>
<%	}	%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>
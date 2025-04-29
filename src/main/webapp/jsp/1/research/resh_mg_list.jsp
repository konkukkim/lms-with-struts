<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/course_header.jsp" %>
<Script Language="javascript">
    function Create_Research(){
       var f = document.f;

       f.pMODE.value = "write";
       f.submit();
    }

    function Show_ResearchContents(resid){
       var f = document.f;

       f.pResId.value = resid;
       f.action="<%=CONTEXTPATH%>/ResearchContents.cmd?cmd=researchContentsList";
       f.submit();
    }

    function Show_ResearchInfo(resid){
       var f = document.f;

       f.pResId.value = resid;

       f.pMODE.value = "edit";
       f.submit();
    }
</Script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"설문관리")%></b></font></td>
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
<input type=hidden name="pResId" value="">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															<td align=right width="50%" height=30>
<script language=javascript>Button5("설문만들기", "Create_Research()", "");</script>&nbsp;
															</td>
														</tr>
													</table>
												</td>
											</tr>
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
												<td width="80">등록여부</td>
												<td class="s_tablien"></td>
												<td width="90">수정/삭제</td>
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
												<td class="s_tab04"><a href="javascript:Show_ResearchContents('<%=list.getInt("res_id")%>');"><%=StringUtil.nvl(list.getString("subject"))%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("start_date")))+" ~ "+DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("end_date")))%></td>
												<td></td>
												<td class="s_tab04_cen">
<%			if (StringUtil.nvl(list.getString("open_yn")).equals("Y")) {	%>
													<font color="blue">등록완료</font>
<%			} else {	%>
													<font color="red">등록대기</font>
<%			}	%>
												</td>
												<td></td>
												<td class="s_tab04_cen"><a href="javascript:Show_ResearchInfo('<%=list.getInt("res_id")%>');"><b>[수정/삭제]</b></a></td>
											</tr>
<%		}

		list.close();
	}

	if (No < 1) {	%>
											<tr>
												<td class="s_tab04_cen" colspan="11">설문 리스트가 존재하지 않습니다.</td>
											</tr>
<%	}	%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>










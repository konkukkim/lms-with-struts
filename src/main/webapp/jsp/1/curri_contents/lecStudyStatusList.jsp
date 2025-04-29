<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/course_header.jsp" %>
<%
	String 	pViewType 		=  (String)model.get("pViewType");
	String 	pSearchKey 		=  (String)model.get("pSearchKey");
	String 	pKeyWord 		=  (String)model.get("pKeyWord");
	int 	contentsCnt 	=  Integer.parseInt((String)model.get("contentsCnt"));
	int 	studentCnt 		=  Integer.parseInt((String)model.get("studentCnt"));
%>
<script language="javascript">
	function goStuPagingList() {
		document.location.href = "<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecStudyStatus&pViewType=Page&MENUNO=0";
	}

	function goSearchList() {
		document.f.submit();
	}
</script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"수강생진도현황")%></b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
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
<form name="f" method="post" action="<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecStudyStatus" >
<input type="hidden" name="pViewType" value="<%=pViewType%>" >

											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("페이지별 학생보기", "goStuPagingList()", "");</script>
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
												<td width="100">이름</td>
												<td class="s_tablien"></td>
												<td width="">진도율</td>
												<td class="s_tablien"></td>
												<td width="90">수강시간</td>

											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%	int i	=	studentCnt;
	int No	=	0;
	int statusWidth = 0;
	int studyHour = 0;
	int studyMin = 0;
	String strStudyTime = "";
   	RowSet list= (RowSet)model.get("statusList");

	if(i > 0) {
		while (list.next()) {
			No++;
			studyHour = 0;
			studyMin = list.getInt("study_time");

			int	studycnt	=	0;
			studycnt	=	list.getInt("study_cnt");

			if (studycnt==0 || contentsCnt==0)
				statusWidth = 0;
			else
				statusWidth = studycnt*100 / contentsCnt;

			if (list.getInt("study_time") >= 60 ) {
				studyHour = list.getInt("study_time") / 60;
				studyMin = list.getInt("study_time") - (studyHour*60);
				strStudyTime = studyHour+"시간"+studyMin+"분";
			} else {
				strStudyTime = studyMin+"분";
			}

			if(No > 1) {
%>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%			}	%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No %></td>
												<td></td>
												<td class="s_tab04_cen"><a href="<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecStudyStatusShow&pStudentId=<%=StringUtil.nvl(list.getString("user_id"))%>"><%=StringUtil.nvl(list.getString("user_name"))%>(<%=StringUtil.nvl(list.getString("user_id"))%>)</a></td>
												<td></td>
												<td class="s_tab04"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=statusWidth*2%>" height="13" align="absmiddle"> <%=statusWidth%>% </td>
												<td></td>
												<td class="s_tab04_cen"><%=strStudyTime%></td>
											</tr>
<%			i--;
		}
		list.close();
	} else {
%>
											<tr>
												<td class="s_tab04_cen" colspan="11">해당 학생이 없습니다.</td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="10" align="right">
												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<table>
														<tr>
															<td align=middle height=30>
																<select name=pSearchKey>
																	<option value="u.user_name" <%if(pSearchKey.equals("u.user_name")){%>selected<%}%>>이름</option>
						      										<option value="s.user_id" <%if(pSearchKey.equals("s.user_id")){%>selected<%}%>>아이디</option>
																</select>
																<input maxlength=30 size=22 name="pKeyWord" value="<%=pKeyWord%>">
																<a href="javascript:goSearchList()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- // 페이지 리스트, 검색부분 -->
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


<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.currisub.dto.CurriContentsDTO"%>
<%@include file="../common/course_header.jsp" %>
<%
	String lecLink = "";
	String pStudentName = (String)model.get("pStudentName");
	String pStudentId = (String)model.get("pStudentId");
%>
<script language="javascript">
	function goStuPagingList() {
		document.location.href = "<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecStudyStatus&pViewType=Page&MENUNO=0";
	}

	function goAllStuList() {
		document.location.href = "<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecStudyStatus&pViewType=All&MENUNO=0";
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
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">대상 학생 : <b><%=pStudentName%>(<%=pStudentId%>)<b></td>
															<td align=right width="50%" height=30>
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
												<td width="">목차명</td>
												<td class="s_tablien"></td>
												<td width="100">최근학습일</td>
												<td class="s_tablien"></td>
												<td width="60">평가</td>
												<td class="s_tablien"></td>
												<td width="80">접속회수</td>
												<td class="s_tablien"></td>
												<td width="100">접속시간(분)</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%	RowSet list= (RowSet)model.get("contentsList");
	int itemCnt = 0;
	String contentsQuizYn = "";
	String bookmarkQuizPass = "";
	String listQuizStatus = "";
	String QuizLink="";
	String prevContentsView="Y";
	String prevQuizPass="P";
	String contentsImg = "page_ncheck.gif";
	String contentsDepth = "";
	String strOpenDate = "";

	while (list.next()) {
		strOpenDate = "";
		contentsDepth = "&nbsp;&nbsp;";
		contentsImg = "icon_note.gif";
		listQuizStatus = "";
		contentsQuizYn = StringUtil.nvl(list.getString("quiz_yn"));
		bookmarkQuizPass = StringUtil.nvl(list.getString("quiz_pass"));
		itemCnt++;
		lecLink = StringUtil.nvl(list.getString("contents_name"));

		if (!StringUtil.nvl(list.getString("contents_type")).equals("C") || !StringUtil.nvl(list.getString("server_path")).equals("")) {
			if (StringUtil.nvl(list.getString("open_chk")).equals("Y"))
				contentsImg = "icon_view01.gif";
		} else {
			contentsImg = "icon_folder.gif";
			contentsDepth = "";
		}

		if (contentsQuizYn.equals("Y")) {
			if (bookmarkQuizPass.equals("N"))
				listQuizStatus = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/contents/q_npass.gif' align='absmiddle'>";
			if (bookmarkQuizPass.equals("F"))
				listQuizStatus = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/contents/q_npass.gif' align='absmiddle'>";
			if (bookmarkQuizPass.equals("P"))
				listQuizStatus = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/contents/q_pass.gif' align='absmiddle'>";
		}

		if (!StringUtil.nvl(list.getString("open_date")).equals(""))
			strOpenDate = "<a href='#' title='"+StringUtil.nvl(list.getString("open_date")).substring(11)+"'>"+StringUtil.nvl(list.getString("open_date")).substring(0,10)+"</a>";

		prevContentsView = StringUtil.nvl(list.getString("open_chk"));

		if (contentsQuizYn.equals("N") || bookmarkQuizPass.equals("P"))
			prevQuizPass ="P";
		else
			prevQuizPass = bookmarkQuizPass;

		if(itemCnt > 1) {
%>

											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%		} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=itemCnt%></td>
												<td></td>
												<td class="s_tab04">
													<%=contentsDepth%> <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/<%=contentsImg%>" align="absmiddle"> <%=lecLink%></td>
												<td></td>
												<td class="s_tab04_cen"><%=("2").equals(StringUtil.nvl(list.getString("lecture_gubun"))) ?  StringUtil.nvl(list.getString("start_date")) +(!("").equals(StringUtil.nvl(list.getString("attendance")))? "(" + StringUtil.nvl(list.getString("attendance")) +")" : "" ) : strOpenDate %></td>
												<td></td>
												<td class="s_tab04_cen"><%=listQuizStatus%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("join_count"),"")%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("total_time"),"")%></td>
											</tr>
<%	}

	list.close();

	if (itemCnt == 0) {	%>
											<tr>
												<td class="s_tab04_cen" colspan="11">등록된 목차가 없습니다.</td>
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
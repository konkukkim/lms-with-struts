<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@include file="../common/course_header.jsp" %>
<%	String 	pViewType 		=  (String)model.get("pViewType");
	String 	pSearchKey 		=  (String)model.get("pSearchKey");
	String 	pKeyWord 		=  (String)model.get("pKeyWord");
	int 	contentsCnt 	=  Integer.parseInt((String)model.get("contentsCnt"));
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/popupbox.js"></script>
<Script Language="javascript">
	function goAllStuList() {
		document.location.href = "<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecStudyStatus&pViewType=All&MENUNO=0";
	}

	function goSearchPagingList() {
		document.f.submit();
	}
	
	
	function offlineAttenPop() {

	    popupbox1.clear();
		showPopupBox(popupbox1);

        var vUrl="<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecOfflineAttenPopup";
        
        var frame = "<iframe name='Frame1' width='100%' height='100%' frameborder='0' src='"+vUrl+"'></iframe>";
        popupbox1.addContents(frame);

	}
</Script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"수강생 진도현황")%></b></font></td>
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
<%
 ListDTO listObj = (ListDTO)model.get("statusList");
 int iTotCnt = listObj.getTotalItemCount();
 int iCurPage = listObj.getCurrentPage();
 int iDispLine = listObj.getListScale();
%>
<%= listObj.getPageScript("f", "curPage", "goPage") %>
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecStudyStatus" >
<input type="hidden" name="curPage" value="<%=iCurPage%>">
<input type="hidden" name="pViewType" value="<%=pViewType%>" >
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("전체학생보기", "goAllStuList()", "");</script>
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
<%	int No = listObj.getStartPageNum();
  	int i	=	0;
  	int statusWidth = 0;
  	int studyHour = 0;
  	int studyMin = 0;
  	String strStudyTime = "";
  	RowSet list= listObj.getItemList();

   	if (listObj.getItemCount() > 0) {

		while (list.next()) {
			i++;
			studyHour = 0;
			studyMin = list.getInt("study_time");
			int	studycnt	=	0;
			studycnt	=	list.getInt("study_cnt");

			if (studycnt==0 || contentsCnt==0)
				statusWidth = 0;
			else
				statusWidth = studycnt*100 / contentsCnt;

			if (list.getInt("study_time") >=60 ) {
				studyHour = list.getInt("study_time") / 60;
				studyMin = list.getInt("study_time") - (studyHour*60);
				strStudyTime = studyHour+"시간"+studyMin+"분";
			} else {
				strStudyTime = studyMin+"분";
			}
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04_cen"><a href="<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecStudyStatusShow&pStudentId=<%=StringUtil.nvl(list.getString("user_id"))%>"><%=StringUtil.nvl(list.getString("user_name"))%>(<%=StringUtil.nvl(list.getString("user_id"))%>)</a></td>
												<td></td>
												<td class="s_tab04"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=statusWidth*2%>" height="13" align="absmiddle"> <%=statusWidth%>% </td>
												<td></td>
												<td class="s_tab04_cen"><%=strStudyTime%></td>
											</tr>
<%			if(No > 1) { %>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
			}
			No = No-1;
		}
	}
	else {
%>


											<tr>
												<td class="s_tab04_cen" colspan="11">해당 학생이 없습니다.</td>
											</tr>
<%
	}

	list.close();
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
													<table valign=top height="25">
														<tr>
															<td><%= listObj.getPagging(SYSTEMCODE,"goPage") %></td>
														</tr>
													</table>
													<table>
														<tr>
															<td align=middle height=30>
																<select name=pSearchKey>
																	<option value="u.user_name" <%if(pSearchKey.equals("u.user_name")){%>selected<%}%>>이름</option>
					      											<option value="s.user_id" <%if(pSearchKey.equals("s.user_id")){%>selected<%}%>>아이디</option>
																</select>
																<input maxlength=30 size=22 name="pKeyWord" value="<%=pKeyWord%>">
																<a href="javascript:goSearchPagingList()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- // 페이지 리스트, 검색부분 -->
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


<Script Language=javascript>

    var popupbox1 = new PopupBox("출결관리(오프라인)", "style=normal,width=500,height=500, bgcolor=white, modal=yes,resizable=yes,move=yes,titlebar=yes");

</Script>
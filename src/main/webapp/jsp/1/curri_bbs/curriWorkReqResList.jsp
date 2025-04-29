<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet, com.edutrack.framework.persist.ListDTO, com.edutrack.curribbs.dto.CurriWorkReqResDTO"%>
<%@include file="../common/course_header.jsp" %>
<%
	String	pUserType = (String)model.get("pUserType");
	String  pWhere	= (String)model.get("pWhere");
	String  pSTarget	= (String)model.get("pSTarget");
	String  pSWord	= (String)model.get("pSWord");
%>
<script language="javascript">
	function goAdd(){
		location.href="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=workReqResWrite&pRegType=Regist&pMethodType=Insert&pWhere=<%=pWhere%>";
	}

	function replyAdd(param, status){
		var f = document.Input;
		f.pSeqNo.value = param;
		f.pRegType.value = "Reply";
		if(status == "0") f.pMethodType.value = "Insert";
		else f.pMethodType.value = "Update";
		f.action="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=workReqResWrite&pWhere=<%=pWhere%>";
		f.submit();
	}
</script>
<%
	ListDTO listObj 	= (ListDTO)model.get("curriWorkList");
 	int iTotCnt 		= 	listObj.getTotalItemCount();
 	int iCurPage 		= 	listObj.getCurrentPage();
 	int iDispLine 		= 	listObj.getListScale();

%>
<%= listObj.getPageScript("Input", "curPage", "goPage") %>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"개인면담")%></b></font></td>
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
<form name=Input method=post>
<input type="hidden" name="curPage" value="<%=iCurPage%>">
<input type=hidden name=pRegType value="">
<input type=hidden name=pMethodType value="">
<input type=hidden name=pSeqNo value="">
</form>
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
																<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bullet01.gif" align="absmiddle" width="10" height="10" border="0">
																<b><font color="#33CCCC">요청</font></b>(학습자)
																→ <b><font color="#FF6600">답변</font></b>(교수자)
																→ <b><font color="#66CC66">만족도</font></b>(학습자)
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
												<td width="">제목</td>
												<td class="s_tablien"></td>
<%
	if (pUserType.equals("S")) {
%>
												<td width="110">답변대상</td>
												<td class="s_tablien"></td>
<%
	} else {
%>
												<td width="110">요청자</td>
												<td class="s_tablien"></td>
<%
	}
%>
												<td width="100">요청일자</td>
												<td class="s_tablien"></td>
												<td width="80">상태</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	String totCnt = (String)model.get("totCnt");
	String	statusStr	=	"";
	int No = Integer.parseInt(totCnt);
	RowSet list= listObj.getItemList();

	if(listObj.getItemCount() > 0){

			while(list.next()){
				statusStr = StringUtil.nvl(list.getString("work_status"),"0").equals("0") ? "<b><font color='#33CCCC'>요청</font></b>" : list.getInt("point") == 0 ? "<b><font color='#FF6600'>답변</font></b>" : "<b><font color='#66CC66'>만족도</font></b>";
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04">
<%
			if(pUserType.equals("S") || list.getInt("point") != 0) {
%>
													<a href="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=curriWorkReqResShow&pWhere=<%=pWhere%>&pMode=Lecture&pSeqNo=<%=list.getInt("seq_no")%>"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(StringUtil.nvl(list.getString("req_subject"))),40)%></a>
<%
            } else {
%>
													<a href="javascript:replyAdd('<%=list.getInt("seq_no")%>', '<%=StringUtil.nvl(list.getString("work_status"),"0")%>');"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(StringUtil.nvl(list.getString("req_subject"))),40)%></a>
<%
			}
%>
												</td>
												<td></td>
												<td class="s_tab04_cen">
<%
			// 학생자일경우 교수 아이디를 보여주고
			// 그 이외에는  학생 아이디를 보여준다

			if (pUserType.equals("S")) {
%>
													<%=CommonUtil.getUserName(SYSTEMCODE,StringUtil.nvl(list.getString("prof_id")))%>
<%
			} else {
%>
    												<%=CommonUtil.getUserName(SYSTEMCODE,StringUtil.nvl(list.getString("student_id")))%>
<%
			}
%>
												</td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("req_reg_date")))%></td>
												<td></td>
												<td class="s_tab04_cen">
<%
	if (pUserType.equals("S") || list.getInt("point") != 0) {
%>
													<%=statusStr%>
<%
	} else {
%>
													<a href="javascript:replyAdd('<%=list.getInt("seq_no")%>', '<%=StringUtil.nvl(list.getString("work_status"))%>');"><%=statusStr%></a>
<%
	}
%>
												</td>
											</tr>
<%			if(No > 1) { %>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
			}
			No = No-1;
		}
		list.close();
	} else {
%>
											<tr>
												<td class="s_tab04_cen" colspan="11">등록된 정보가  없습니다.</td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<%
	if(pUserType.equals("S")){
%>
<script language=javascript>Button3("글쓰기", "goAdd()", "");</script>
<%
	}
%>
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
<!-- form start -->
<form name=Search method=post aciton="/">
														<tr>
															<td align=middle height=30>
																<select name=pSTarget>
<%
	if(!pUserType.equals("S")){
%>
																	<option value="student_id" <%=pSTarget.equals("student_id") ? "selected" : ""%>>요청자ID</option>
<%
	}

	if(!pUserType.equals("P")){
%>
																	<option value="prof_id" <%=pSTarget.equals("prof_id") ? "selected" : ""%>>교수자ID</option>
<%
	}
%>
                                    								<option value="req_subject" <%=pSTarget.equals("req_subject") ? "selected" : ""%>>제목</option>
																</select>
																<input maxlength=30 name=pSWord size=22 value="<%=pSWord%>">
																<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle onClick="javascript:document.Search.submit();" style="cursor:hand;">
															</td>
														</tr>
</form>
													</table>
												</td>
											</tr>
											<!-- // 페이지 리스트, 검색부분 -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>
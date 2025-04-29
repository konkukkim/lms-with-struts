<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.curritop.dto.CurriTopCourseDTO"%>
<%@include file="../common/header.jsp" %>
<script language="javascript">
	function goAdd() {
		var url = "<%=CONTEXTPATH%>/Course.cmd?cmd=courseSearchList&pTaret=curriTop&pCurriCode=<%=model.get("pCurriCode")%>&pCurriProperty2=<%=model.get("pCurriProperty2")%>";
		courseSearchWin = window.open(url,"courseWin","width=530,height=300,left=0,top=0,resizable=no,scrollbars=1");
		courseSearchWin.focus();
	}

	function goEdit(str) {
		
		if(u_right!="true")
	    { 
	    	alert("수정 권한이 없습니다. \n\n관리자에게 문의 하세요");
	    	return;
	    }
	    
		editWin = window.open("<%=CONTEXTPATH%>/CurriTopCourse.cmd?cmd=curriTopCourseEdit&pCurriCode=<%=model.get("pCurriCode")%>&pCourseId="+str,"courseWin","width=530,height=300,left=0,top=0,resizable=no,scrollbars=1");
		editWin.focus();
	}

	function goDelete(str) {
		
		if(d_right!="true")
	    { 
	    	alert("삭제 권한이 없습니다. \n\n관리자에게 문의 하세요");
	    	return;
	    }
		var yes = confirm("과정에 연결된 과목을 삭제 하려고 합니다.\n\n삭제 하시겠습니까?");
		if(yes == true) document.location.href="<%=CONTEXTPATH%>/CurriTopCourse.cmd?cmd=curriTopCourseDelete&pCurriCode=<%=model.get("pCurriCode")%>&pCourseId="+str;
		else return;
	}
	function goList() {
		document.location = "<%=CONTEXTPATH%>/CurriTop.cmd?cmd=curriTopList&pProperty1=Cyber&MENUNO=2&pMode=MyPage";
	}
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<form name="form1" method="post" action="">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%"></td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("목록", "goList()", "");</script>
<% if(CommonUtil.getAuthorCheck(request,  "C") || CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button5("과목연결", "goAdd()", "");</script>&nbsp;<%	}	%>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="">과목명</td>
												<td class="s_tablien"></td>
												<td width="80">교수자</td>
												<td class="s_tablien"></td>
												<td width="90">연결일</td>
												<td class="s_tablien"></td>
												<td width="90">수정/삭제</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	ListDTO listObj = (ListDTO)model.get("curriTopCourseList");
	String totCnt = (String)model.get("totCnt");
	int No = Integer.parseInt(totCnt);
	//int No = 50;
	RowSet list= listObj.getItemList();
	if (listObj.getItemCount() > 0) {
		while (list.next()) {
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("course_name"))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("prof_name"))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("reg_date"))%></td>
												<td></td>
												<td class="s_tab04_cen">
													<b>[<a href="javascript:goEdit('<%=StringUtil.nvl(list.getString("course_id"))%>')">수정</a> / <a href="javascript:goDelete('<%=StringUtil.nvl(list.getString("course_id"))%>')">삭제</a>]</b>
												</td>
											</tr>
<%			if(No > 1) { %>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
			}
			No--;
		}
		list.close();
	}
	else {
%>
											<tr>
												<td class="s_tab04_cen" colspan="11">연결된 과목이 없습니다.</td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11" height="10" align="right"></td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<table valign=top height="25">
														<tr>
															<td><%= listObj.getPagging(SYSTEMCODE,"goPage") %></td>
														</tr>
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
					
</form>
<%@include file="../common/footer.jsp" %>
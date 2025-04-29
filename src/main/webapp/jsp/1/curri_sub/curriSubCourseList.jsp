<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.currisub.dto.CurriSubCourseDTO"%>
<%@ page import ="com.edutrack.common.CommonUtil"%>
<%@include file="../common/header.jsp" %>
<%
	//String serverUrl = CommonUtil.SERVERURL;
	String serverUrl = "http://"+request.getServerName();
	String drmServer = CommonUtil.DigCapDRMServerDoamin;
%>
<script language="javascript">
	function goAdd() {
		courseSearchWin = window.open("<%=CONTEXTPATH%>/Course.cmd?cmd=courseSearchList&pTarget=courseSub&pCurriProperty2=<%=model.get("pCurriProperty2")%>&pCurriCode=<%=model.get("pCurriCode")%>&pCurriYear=<%=model.get("pCurriYear")%>&pCurriTerm=<%=model.get("pCurriTerm")%>","courseWin","width=550,height=300,left=0,top=0,resizable=no,scrollbars=1");
		courseSearchWin.focus();
	}

	function goEdit(str) {
		
		if(u_right!="true")
	    { 
	    	alert("수정 권한이 없습니다. \n\n관리자에게 문의 하세요");
	    	return;
	    }
		editWin = window.open("<%=CONTEXTPATH%>/CurriSubCourse.cmd?cmd=curriSubCourseEdit&pMode=<%=PMODE %>&pCurriCode=<%=model.get("pCurriCode")%>&pCurriYear=<%=model.get("pCurriYear")%>&pCurriTerm=<%=model.get("pCurriTerm")%>&pCourseId="+str,"courseWin","width=550,height=300,left=0,top=0,resizable=no,scrollbars=1");
		editWin.focus();
	}

	function goDelete(str) {
		
		if(d_right!="true")
	    { 
	    	alert("삭제 권한이 없습니다. \n\n관리자에게 문의 하세요");
	    	return;
	    }
	    
		var yes = confirm("과정에 연결된 과목을 삭제 하려고 합니다.\n\n삭제 하시겠습니까?");
		if(yes == true) document.location.href="<%=CONTEXTPATH%>/CurriSubCourse.cmd?cmd=curriSubCourseDelete&pMode=<%=PMODE %>&pCateCode=<%=model.get("pCateCode")%>&pCurriCode=<%=model.get("pCurriCode")%>&pCurriYear=<%=model.get("pCurriYear")%>&pCurriTerm=<%=model.get("pCurriTerm")%>&pCourseId="+str;
		else return;
	}
	function goList()
	{
		document.location = "<%=CONTEXTPATH%>/CurriSub.cmd?cmd=curriSubList&pMode=<%=PMODE %>&pCateCode=<%=model.get("pCateCode")%>&pCurriCode=<%=model.get("pCurriCode")%>&pCurriYear=<%=model.get("pCurriYear")%>&pCurriTerm=<%=model.get("pCurriTerm")%>";
	}

	function goSort()
	{
	   document.location = "<%=CONTEXTPATH%>/CurriSort.cmd?cmd=curriSubSortList&pMode=<%=PMODE %>&pCurriCode=<%=model.get("pCurriCode")%>&pCurriYear=<%=model.get("pCurriYear")%>&pCurriTerm=<%=model.get("pCurriTerm")%>";
	}

	/* 미디어 DRM 쿠키 셋팅 후 교재 관리 페이지로 이동*/
	function goDrmSetCookie(cateCode,curriCode,curriYear,curriTerm,courseId,courseName){
		var loc = "<%=drmServer%>/setDRMCookies.asp?"
				+ "UserID=<%=USERID%>"
				+ "&cmd=curriContentsList"
				+ "&pCateCode="+cateCode
				+ "&pCurriCode="+curriCode
				+ "&pCurriYear="+curriYear
				+ "&pCurriTerm="+curriTerm
				+ "&pCourseId="+courseId
				+ "&pCourseName="+courseName
				+ "&retURL=<%=serverUrl%>/CurriContents.cmd";
		document.location.href = loc;
	}
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">

<%
	ListDTO listObj = (ListDTO)model.get("curriSubCourseList");
    int iTotCnt 	= listObj.getTotalItemCount();
    int iCurPage 	= listObj.getCurrentPage();
    int iDispLine 	= listObj.getListScale();
%>
<%= listObj.getPageScript("form1", "curPage", "goPage") %>
<form name="form1" method="post" action="">
<input type="hidden" name="curPage"	value="<%=iCurPage%>">
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
												<td class="s_tablien"></td>
												<td width="90">교재관리</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	String totCnt = (String)model.get("totCnt");
//	int No = Integer.parseInt(totCnt);
    int No = listObj.getStartPageNum();

	RowSet list= listObj.getItemList();
	if(listObj.getItemCount() > 0){
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
												<td></td>
												<td class="s_tab04_cen"><a href="/CurriContents.cmd?cmd=curriContentsList&pMode=MyPage&pCateCode=<%=model.get("pCateCode")%>&pCurriCode=<%=model.get("pCurriCode")%>&pCurriYear=<%=model.get("pCurriYear")%>&pCurriTerm=<%=model.get("pCurriTerm")%>&pCourseId=<%=list.getString("course_id")%>&pCourseName=<%=list.getString("course_name")%>"><b>[교재관리]</b></a></td>
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
												<td class="s_tab04_cen" colspan="11">※ 등록된 과목 연결 정보가 없습니다.</td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11" height="10"></td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<%=listObj.getPagging(SYSTEMCODE,"goPage")%>
												</td>
											</tr>
											<!-- // 페이지 리스트, 검색부분 -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
					
<%@include file="../common/footer.jsp" %>






















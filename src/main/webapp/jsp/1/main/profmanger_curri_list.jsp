<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/header.jsp" %>
<%
	String	pGubun		=	StringUtil.nvl(model.get("pGubun"));
	String	titleImg	=	"ptit1_01.gif";
	String	titleStr	=	"담당과정";
	if(pGubun.equals("1")) {
		titleImg	=	"ptit1_01.gif";
		titleStr	=	"담당과정";
	} else {
		titleImg	=	"stit01_03.gif";
		titleStr	=	"지난과정";
	}
%>

										<!-- 내용 -->
										<!-- 정규과정 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="3" class="s_tabtit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" width="18" height="16" align="absmiddle"> <%=titleStr%> </td>
											</tr>
										</table>
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="3"></td>
											</tr>
											<tr class="s_tab02">
												<td width="">과정명</td>
												<td class="s_tablien"></td>
												<td width="160">수강기간</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="3"></td>
											</tr>
<%
	RowSet 	list= (RowSet)model.get("curriList");
	int 	curriListNum	=	0;
	String	courseDate		=	"";
	String	startDate		=	"";
	String	endDate			=	"";
	
	while(list.next()){
		curriListNum++;
		
		courseDate	=	"";
		startDate	=	StringUtil.nvl(list.getString("service_start"));
		endDate		=	StringUtil.nvl(list.getString("service_end"));
		if(!startDate.equals("") && !endDate.equals("")) courseDate = DateTimeUtil.getDateType(1,startDate)+" ~ "+DateTimeUtil.getDateType(1, endDate);

		if(curriListNum > 1) {
%>
											<tr class="s_tab03">
												<td colspan="3"></td>
											</tr>
<%		} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04"><a 
	href="<%=CONTEXTPATH%>/LectureMain.cmd?cmd=goLecture&pMode=Lecture&MENUNO=0&pCurriCode=<%=StringUtil.nvl(list.getString("curri_code"))%>&pCurriYear=<%=list.getInt("curri_year")%>&pCurriTerm=<%=list.getInt("curri_term")%>&pCurriType=<%=StringUtil.nvl(list.getString("curri_property2"))%>&pCurriName=<%=StringUtil.nvl(list.getString("curri_name"))%>"><%=StringUtil.nvl(list.getString("curri_name"))%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=courseDate%></td>
											</tr>
<%
	}
	list.close();
	if(curriListNum == 0) {
%>
											<tr>
												<td class="s_tab04_cen" colspan="3">담당과정이 없습니다.</td>
											</tr>
<%
	}//end if
%>
											<tr class="s_tab05">
												<td colspan="3"></td>
											</tr>
											<tr>
												<td colspan="3" height="30"></td>
											</tr>
										</table>
										<!-- // 정규과정 끝 -->
									</td>
								</tr>
							</table>
<%@include file="../common/footer.jsp" %>
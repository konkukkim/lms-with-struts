<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import = "com.edutrack.curridate.dto.CurriDateDTO" %>
<%@include file="../common/header.jsp" %>
<script language="javascript">
	function goAdd() {
		document.location.href = "<%=CONTEXTPATH%>/CurriDate.cmd?cmd=curriDateWrite&pGubun=W";
	}
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="13" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_term_schedule.gif" width="110" height="25" onClick="goAdd()" style="cursor:hand"><%	}	%>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="13"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="70">년도</td>
												<td class="s_tablien"></td>
												<td width="70">학기</td>
												<td class="s_tablien"></td>
												<td width="">수강신청기간</td>
												<td class="s_tablien"></td>
												<td width="">수강기간</td>
												<td class="s_tablien"></td>
												<td width="">수강완료</td>
												<td class="s_tablien"></td>
												<td width="70">사용여부</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
<%
	int		totCnt		=	0;
	String	serviceStr	=	"";
	ArrayList	list	=	(ArrayList)model.get("CurriDateList");
	
	if(list != null && list.size() > 0) {

		CurriDateDTO	dateDto	=	null;
		for(int i = 0; i < list.size(); i++) {
			dateDto	=	(CurriDateDTO)list.get(i);

			if(StringUtil.nvl(dateDto.getServiceYn()).equals("Y"))	serviceStr	=	"<font color='red'>사용</font>";
			else if(StringUtil.nvl(dateDto.getServiceYn()).equals("N"))	serviceStr	=	"<font color='blue'>미사용</font>";

			if(i >= 1) {
%>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
<%			} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=i+1%></td>
												<td></td>
												<td class="s_tab04_cen"><%=dateDto.getCurriYear()%></td>
												<td></td>
												<td class="s_tab04_cen"><a href="<%=CONTEXTPATH%>/CurriDate.cmd?cmd=curriDateWrite&pMode=<%=PMODE%>&pGubun=E&pCurriYear=<%=dateDto.getCurriYear()%>&pHakgiTerm=<%=dateDto.getHakgiTerm()%>"><%=dateDto.getHakgiTerm()%></a></td>
												<td></td>
												<td class="s_tab04_cen"><a href="<%=CONTEXTPATH%>/CurriDate.cmd?cmd=curriDateWrite&pMode=<%=PMODE%>&pGubun=E&pCurriYear=<%=dateDto.getCurriYear()%>&pHakgiTerm=<%=dateDto.getHakgiTerm()%>"><%=DateTimeUtil.getDateType(1, StringUtil.nvl(dateDto.getEnrollStart()))%> ~ <%=DateTimeUtil.getDateType(1, StringUtil.nvl(dateDto.getEnrollEnd()))%></a></td>
												<td></td>
												<td class="s_tab04_cen"><a href="<%=CONTEXTPATH%>/CurriDate.cmd?cmd=curriDateWrite&pMode=<%=PMODE%>&pGubun=E&pCurriYear=<%=dateDto.getCurriYear()%>&pHakgiTerm=<%=dateDto.getHakgiTerm()%>"><%=DateTimeUtil.getDateType(1, StringUtil.nvl(dateDto.getServiceStart()))%> ~ <%=DateTimeUtil.getDateType(1, StringUtil.nvl(dateDto.getServiceEnd()))%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1, StringUtil.nvl(dateDto.getCompleteEnd()))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=serviceStr%></td>
											</tr>
<%
		}	//end for
	}
	else {
%>
											<tr>
												<td class="s_tab04_cen" colspan="13">등록된 학기일자가 없습니다.</td>
											</tr>
<%	}	//end if %>



											<tr class="s_tab05">
												<td colspan="13"></td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						<!-- // 본문 -->
<%@include file="../common/footer.jsp" %>

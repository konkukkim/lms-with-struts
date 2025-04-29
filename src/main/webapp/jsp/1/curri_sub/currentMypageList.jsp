<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.currisub.dto.CurriSubDTO"%>
<%@include file="../common/header.jsp" %>
										<!-- 내용 -->
										<!-- 정규과정 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="5" class="s_tabtit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" width="18" height="16" align="absmiddle">정규과정</td>
											</tr>
										</table>
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="5"></td>
											</tr>
											<tr class="s_tab02">
												<td width="348">과정명</td>
												<td class="s_tablien"></td>
												<td width="160">신청기간</td>
												<td class="s_tablien"></td>
												<td width="160">수강기간</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="5"></td>
											</tr>
<%
	RowSet list = null;
	String sampleContentsLink = "";
	String tmpSampleContents = "";
	int Rnum = 0;
	list = (RowSet)model.get("curriRList");
	while(list.next()) {
		Rnum++;
		tmpSampleContents = StringUtil.nvl(list.getString("sample_contents"));
		if(!tmpSampleContents.equals(""))
			sampleContentsLink = "<a href=\"javascript:SampleWin('"+tmpSampleContents+"')\"><img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button/edu_sample.gif\" width=\"62\" height=\"15\" align=\"absmiddle\" hspace=\"4\"></a>";
		else
			sampleContentsLink = "";

		if(Rnum > 1) {
%>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
<%		} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04"><a href="<%=CONTEXTPATH%>/LectureMain.cmd?cmd=goLecture&pMode=Lecture&MENUNO=0&pCurriCode=<%=StringUtil.nvl(list.getString("curri_code"))%>&pCurriYear=<%=list.getInt("curri_year")%>&pCurriTerm=<%=list.getInt("curri_term")%>&pCurriType=R&pCurriName=<%=StringUtil.nvl(list.getString("curri_name"))%>"><%=StringUtil.setMaxLength(StringUtil.nvl(list.getString("curri_name")),40)%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("enroll_start")))%> ~ <%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("enroll_end")))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("service_start")))%> ~ <%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("service_end")))%></td>
											</tr>
<%
	}
	list.close();

	if(Rnum == 0) {
%>


											<tr>
												<td class="s_tab04_cen" colspan="5">등록된 과정이 없습니다.</td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="5"></td>
											</tr>
											<tr>
												<td colspan="5" height="30"></td>
											</tr>
										</table>
										<!-- // 정규과정 끝 -->
										<!-- 상시과정 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="5" class="s_tabtit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" width="18" height="16" align="absmiddle">상시과정</td>
											</tr>
										</table>
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="5"></td>
											</tr>
											<tr class="s_tab02">
												<td colspan="3" width="509">과정명</td>
												<td class="s_tablien"></td>
												<td width="160">수강일수</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="5"></td>
											</tr>
<%
	int Onum = 0;
	list = (RowSet)model.get("curriOList");
	while(list.next()) {
		Onum++;
		tmpSampleContents = StringUtil.nvl(list.getString("sample_contents"));
		if(!tmpSampleContents.equals(""))
			sampleContentsLink = "<a href=\"javascript:SampleWin('"+tmpSampleContents+"')\"><img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button/edu_sample.gif\" width=\"62\" height=\"15\" align=\"absmiddle\" hspace=\"4\"></a>";
		else
			sampleContentsLink = "";

		if(Onum > 1) {
%>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
<%		} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td colspan="3" class="s_tab04"><a href="<%=CONTEXTPATH%>/LectureMain.cmd?cmd=goLecture&pMode=Lecture&MENUNO=0&pCurriCode=<%=StringUtil.nvl(list.getString("curri_code"))%>&pCurriYear=<%=list.getInt("curri_year")%>&pCurriTerm=<%=list.getInt("curri_term")%>&pCurriType=O&pCurriName=<%=StringUtil.nvl(list.getString("curri_name"))%>"><%=StringUtil.setMaxLength(StringUtil.nvl(list.getString("curri_name")),40)%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("service_start"))%>일</td>
											</tr>
<%
	}
	list.close();

	if(Onum == 0) {
%>
											<tr>
												<td colspan="5" class="s_tab04_cen">등록된 과정이 없습니다.</td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="5"></td>
											</tr>
											<tr>
												<td colspan="5" height="30"></td>
											</tr>
										</table>
										<!-- // 상시과정 끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
<%@include file="../common/footer.jsp" %>

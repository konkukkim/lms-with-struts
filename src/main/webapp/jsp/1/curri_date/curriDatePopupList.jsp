<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import = "com.edutrack.curridate.dto.CurriDateDTO" %>
<%@include file="../common/popup_header.jsp" %>
<%
	String	pRegMode	=	StringUtil.nvl(model.get("pRegMode"));
%>
<script language="javascript">
	function setHakgiDate(currYear, hakgi, enrollstart, enrollend, cancelstart, cancelend, servicestart, serviceend, chungend, assessment, complete) {
		var f = opener.document.Input;

<%	if(!pRegMode.equals("Edit")) {%>
		//--년도,학기설정
		f.pCurriYear.value = currYear;
		f.pHakgiTerm.value = hakgi;
<%	}	%>

		//--수강신청기간 pEDate1
		f.pEY1.value	=	enrollstart.substring(0, 4);
		f.pEM1.value	=	enrollstart.substring(4, 6);
		f.pED1.value	=	enrollstart.substring(6, 8);
		f.pEDate1.value = 	enrollstart;

		//--수강신청기간 pEDate2
		f.pEY2.value	=	enrollend.substring(0, 4);
		f.pEM2.value	=	enrollend.substring(4, 6);
		f.pED2.value	=	enrollend.substring(6, 8);
		f.pEDate2.value = 	enrollend;

		//--수강취소기간 pCDate1
		f.pCY1.value	=	cancelstart.substring(0, 4);
		f.pCM1.value	=	cancelstart.substring(4, 6);
		f.pCD1.value	=	cancelstart.substring(6, 8);
		f.pCDate1.value = 	cancelstart;

		//--수강취소기간 pCDate2
		f.pCY2.value	=	cancelend.substring(0, 4);
		f.pCM2.value	=	cancelend.substring(4, 6);
		f.pCD2.value	=	cancelend.substring(6, 8);
		f.pCDate2.value = 	cancelend;

		//--수강기간 pSDate1
		f.pSY1.value	=	servicestart.substring(0, 4);
		f.pSM1.value	=	servicestart.substring(4, 6);
		f.pSD1.value	=	servicestart.substring(6, 8);
		f.pSDate1.value = 	servicestart;

		//--수강기간 pSDate2
		f.pSY2.value	=	serviceend.substring(0, 4);
		f.pSM2.value	=	serviceend.substring(4, 6);
		f.pSD2.value	=	serviceend.substring(6, 8);
		f.pSDate2.value = 	serviceend;

		//--청강종료일 pDate71
		f.pCHEY1.value	=	chungend.substring(0, 4);
		f.pCHEM1.value	=	chungend.substring(4, 6);
		f.pCHED1.value	=	chungend.substring(6, 8);
		f.pDate71.value	=	chungend;

		//--평가종료일 pDate81
		f.pREY1.value	=	assessment.substring(0, 4);
		f.pREM1.value	=	assessment.substring(4, 6);
		f.pRED1.value	=	assessment.substring(6, 8);
		f.pDate81.value	=	assessment;

		//--수료처리종료일 pDate91
		f.pCOEY1.value	=	complete.substring(0, 4);
		f.pCOEM1.value	=	complete.substring(4, 6);
		f.pCOED1.value	=	complete.substring(6, 8);
		f.pDate91.value	=	complete;

		self.close();
	}
</script>
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">학기일자설정</font></td>
			<td class="pop_topright"></td>
		</tr>
		<tr>
			<td class="pop_midleft"></td>
			<td valign="top" class="pop_form">
				<!-- 내용 -->
				<table width="90%" height="100%" align="center">
					<tr height="97%">
						<td align="left" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="s_tab01">
									<td colspan="11"></td>
								</tr>
								<tr class="s_tab02">
									<td width="">년도 - 학기</td>
									<td class="s_tablien"></td>
									<td width="">수강신청기간</td>
									<td class="s_tablien"></td>
									<td width="">수강기간</td>
									<td class="s_tablien"></td>
									<td width="">수강완료</td>
								</tr>
								<tr class="s_tab03">
									<td colspan="11"></td>
								</tr>
<%
	int		totCnt		=	0;
	ArrayList	list	=	(ArrayList)model.get("CurriDateList");

	if(list != null && list.size() > 0) {

		CurriDateDTO	dateDto	=	null;
		for(int i = 0; i < list.size(); i++) {
			dateDto	=	(CurriDateDTO)list.get(i);

			if(i >= 1) {
%>
								<tr class="s_tab03">
									<td colspan="13"></td>
								</tr>
<%			} %>
								<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
									<td class="s_tab04_cen"><a href="javascript:setHakgiDate('<%=dateDto.getCurriYear()%>','<%=dateDto.getHakgiTerm()%>','<%=dateDto.getEnrollStart()%>','<%=dateDto.getEnrollEnd()%>','<%=dateDto.getCancelStart()%>','<%=dateDto.getCancelEnd()%>','<%=dateDto.getServiceStart()%>','<%=dateDto.getServiceEnd()%>', '<%=dateDto.getChungEnd()%>', '<%=dateDto.getAssessmentEnd()%>', '<%=dateDto.getCompleteEnd()%>')"><%=dateDto.getCurriYear()%> - <%=dateDto.getHakgiTerm()%></a></td>
									<td></td>
									<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1, StringUtil.nvl(dateDto.getEnrollStart()))%> ~ <%=DateTimeUtil.getDateType(1, StringUtil.nvl(dateDto.getEnrollEnd()))%></td>
									<td></td>
									<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1, StringUtil.nvl(dateDto.getServiceStart()))%> ~ <%=DateTimeUtil.getDateType(1, StringUtil.nvl(dateDto.getServiceEnd()))%></td>
									<td></td>
									<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1, StringUtil.nvl(dateDto.getCompleteEnd()))%></td>
								</tr>
<%
		}	//end for
	}
	else {
%>
								<tr>
									<td class="s_tab04_cen" colspan="11">등록된 학기일자가 없습니다.</td>
								</tr>
<%	}	//end if %>
								<tr class="s_tab05">
									<td colspan="11"></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<!-- // 내용 -->
			</td>
			<td class="pop_midright"></td>
		</tr>
		<tr height="6">
			<td class="pop_bottomleft"></td>
			<td class="pop_bottombg"></td>
			<td class="pop_bottomright"></td>
		</tr>
		<tr>
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a href="javascript:window.close()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>

<%@include file="../common/popup_footer.jsp" %>
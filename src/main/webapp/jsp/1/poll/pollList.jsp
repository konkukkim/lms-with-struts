<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.poll.dto.InternetPollDTO"%>
<%@include file="../common/header.jsp" %>
<%
	String 	pMode 		=  (String)model.get("pMode");
	boolean bHome       = pMode.equals("Home"  ) ? true : false  ;
	String  img_path    =  CONTEXTPATH+"/img/"+SYSTEMCODE ;
%>
<script language="javascript">
	function goAdd(mode,pollid)
	{
		if(mode == "W") {
			document.location = "<%=CONTEXTPATH%>/Poll.cmd?cmd=pollWrite&pMode=<%=PMODE%>&pRegMode="+mode;
		}else{
			document.location = "<%=CONTEXTPATH%>/Poll.cmd?cmd=pollWrite&pMode=<%=PMODE%>&pRegMode="+mode+"&pPollId="+pollid;
		}
	}

	function goStat(pollid)
	{
		var url = "<%=CONTEXTPATH%>/Poll.cmd?cmd=pollStatEdit&pPollId="+pollid;
		ShowInfo = window.open(url,"poll_win","top=100,left=162,toolbar=0,scrollbars=yes,directories=0,status=0,menubar=0,resizable=yes,width=400,height=320");
	}

	function goResult(pollid,status)
	{
		if(status == 'N')
			alert('결과 비공개 투표 이므로 \n\n투표 종료 후 결과를 확인 하실 수 있습니다.');
		else
			document.location = "<%=CONTEXTPATH%>/Poll.cmd?cmd=pollUserResultShow&pMode=<%=PMODE%>&pPollId="+pollid;
	}

</Script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="13" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%"></td>
															<td align=right width="50%" height=30>
<%
	if(USERTYPE.equals("M") && !bHome) {
%>
<script language=javascript>Button5("투표 등록", "goAdd('W','')", "");</script>
<%
	} else {
%>
&nbsp;
<%
	}
%>
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
												<td width="">제목</td>
												<td class="s_tablien"></td>
												<td width="100">시작일자</td>
												<td class="s_tablien"></td>
												<td width="100">종료일자</td>
												<td class="s_tablien"></td>
<%
		if (USERTYPE.equals("M") && !bHome) {
%>
												<td width="60">상태</td>
												<td class="s_tablien"></td>
<%
		}
%>
												<td width="60">응답수</td>
												<td class="s_tablien"></td>
												<td width="80">결과</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
<%
	int num = 0;
	String status = "";
	ArrayList list = (ArrayList)model.get("pollList");
	InternetPollDTO poll = null;
	String chkStatus = "";
	long endDt = 0;
	long curDate = Long.parseLong(CommonUtil.getCurrentDate());

	for(int i=0; i < list.size(); i++){
		poll = (InternetPollDTO)list.get(i);
		num = num + 1;

		//투표상태
		if(poll.getStatus().equals("Y")) {
			status = "<font color='#33CCCC'>공개</font>";
		}else if(poll.getStatus().equals("N")) {
			status = "<font color='#FF9900'>비공개</font>";
		}

		chkStatus = poll.getStatus();
		endDt = Long.parseLong(StringUtil.nvl(poll.getEndDate()));

		if( endDt < curDate)
			chkStatus = "Y";
		if(USERTYPE.equals("M"))
			chkStatus = "Y";

		if(num > 1) {
%>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
<%		} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=num%></td>
												<td></td>
												<td class="s_tab04">
<%
		if(USERTYPE.equals("M") && !bHome) {
%>
													<a href="javascript:goAdd('E','<%=poll.getPollId()%>')" class="list"><%=poll.getSubject()%></a>
<%
		}else{
%>
													<%=poll.getSubject()%>
<%
		}
%>
												</td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1, poll.getStartDate())%></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1, poll.getEndDate())%></td>
												<td></td>
<%
		if (USERTYPE.equals("M") && !bHome) {
%>
												<td class="s_tab04_cen"><b><font color="#33CCCC"><%=status%></font></b></td>
												<td></td>
<%
		}
%>
												<td class="s_tab04_cen"><%=poll.getHitCnt()%></td>
												<td></td>
												<td class="s_tab04_cen">
													<a href="javascript:goResult(<%=poll.getPollId()%>,'<%=chkStatus%>')">
														<b>[결과보기]</b>
													</a>
												</td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="13"></td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import = "com.edutrack.curridate.dto.CurriDateDTO, java.util.Calendar"%>
<%@ page import = "com.edutrack.schedule.dao.ScheduleDAO, com.edutrack.schedule.dto.ScheduleDTO"%>
<%@include file="../common/header.jsp" %>
<%
	int		pCurrentYear	=	StringUtil.nvl(model.get("pCurrentYear"), 0);	
	int		pHakgiTerm		=	StringUtil.nvl(model.get("pHakgiTerm"), 0);
	int		pCurrentMonth	=	0;
	int		pEndMonth		=	0;
	if(pHakgiTerm == 1) {
		pCurrentMonth	=	1;
		pEndMonth		=	6;
	} else {
		pCurrentMonth	=	7;
		pEndMonth		=	12;
	}	

	int 	pCurrentDay		=	0; //현재날
	boolean	flag			=	false;
	int 	tempy			=	0;
	int		tempm			=	0;
%>

<!-- 내용 시작 -->
						<table width="670" height="100%" align="center" border="0">
<%
		Calendar 		cal 	= 	Calendar.getInstance();
		cal.set(Calendar.YEAR, pCurrentYear);	// 받아온 년도를 셋팅 	

		ScheduleDAO	scheDuleDao	=	null;
		ScheduleDTO	scheDuleDto	=	null;
		ScheduleDTO	scheListDto	=	null;
		ArrayList	scheYnList	=	null;
		ArrayList	scheduleList =	null;
		String		bgcolor 	= 	"";
		String		selectClass = 	"";
		
		for(int k = pCurrentMonth; k <= pEndMonth; k++) {
		
			cal.set(Calendar.MONTH, k-1); //받아온 달을 셋팅 (db에서 받아온 달에서 1을 빼야  그달이 된다).	
%>

							<tr>
								<td class="sub_contentform">
									<!-- 10월 -->
									<table cellspacing=0 cellpadding=0 width="98%" border=0 align="center">
										<tr>
											<!-- 달력 -->
											<td valign=top width=230 align="right">
												<table cellspacing=0 cellpadding=0 width="224" border=0>
													<tr>
														<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/life_s1_4_back01.gif" height=4></td>
													</tr>
													<tr>
														<td valign=top align=middle background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/life_s1_4_back02.gif">
															<table cellspacing=0 cellpadding=0 width=202 border=0>
																<tr>
																	<td valign=top height=29>
																		<table cellspacing=0 cellpadding=0 width="100%" border=0>
																			<tr>
																				<td class=calendartext valign=bottom align=middle height=28><%=pCurrentYear%>년 <%=k%>월</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr>
																	<td>
																		<table cellspacing=0 cellpadding=0 width=202 border=0>
																			<tr>
																				<td valign=bottom height=29><img height=23 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/life_s1_4_day.gif" width=202></td>
																			</tr>
																			<tr>
																				<td>
																					<table cellspacing=1 cellpadding=0 width="100%" border=0>
<%
				// DAY_OF_WEEK --> return 1~7, 1(일),2(월)...
				scheDuleDao	=	new ScheduleDAO();
				scheDuleDto	=	new ScheduleDTO();
				scheYnList	=	scheDuleDao.getScheduleYnList(SYSTEMCODE, pCurrentYear, k);
				
				for(int i = 0; i < scheYnList.size(); i++) {
					pCurrentDay++;
					scheDuleDto	=	(ScheduleDTO)scheYnList.get(i);					
					
					cal.set(Calendar.DAY_OF_MONTH, pCurrentDay); // 그달의 일을 day로 설정.
					if(cal.get(Calendar.DAY_OF_MONTH) != pCurrentDay)// 그달의 일이 끝나면 루프를 벗어남
					{
						break;
					}
					
					if(cal.get(Calendar.DAY_OF_MONTH)==1) // 날짜 출력
					{
						out.println("<tr align=middle bgcolor=#fafafa height=21>");
						for(int a=1; a<cal.get(Calendar.DAY_OF_WEEK); a++)// 1일이 시작되는 요일부터 출력
						{
							out.println("<td><span class=calendar_gray>&nbsp;</span></td>");
						}
					}					
					
					if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)
					{
						bgcolor="calendar_blue";
					}else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
					{
						bgcolor="calendar_red";
					}else
					{
						bgcolor="calendar_gray";
					}
					
					if(scheDuleDto.getTcnt() > 0) {
						selectClass	=	"class=calendar_select";
					}
					
					
					// 날짜 출력 //
					out.println("<td "+selectClass+"><span class="+bgcolor+">"+cal.get(Calendar.DAY_OF_MONTH)+"</span></td>");
					
					if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY) // 토요일까지 출력후 줄바꿈
					{
						out.println("</tr><tr align=middle bgcolor=#fafafa height=21>");
					}
					
					bgcolor = "";
					selectClass = "";
					
				}
				
				if(cal.get(Calendar.DAY_OF_WEEK)!=Calendar.SATURDAY) // 주가 바뀌면 다음줄로 이동.
				{
					out.println("</tr>");
				}
%>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/life_s1_4_back03.gif" height=4></td>
													</tr>
													<tr>
														<td style="padding:15 0 0 0"><img height=10 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/life_s1_4_text03.gif" width=194></td>
													</tr>
												</table>
											</td>
											<!-- 주요일정 -->
											<td valign=top align=right>
												<table height=176 cellspacing=0 cellpadding=0 width=396 border=0>
													<tr>
														<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/life_s1_4_back01_1.gif" height=4></td>
													</tr>
													<tr>
														<td valign=top align=middle background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/life_s1_4_back02_1.gif">
															<table cellspacing=0 cellpadding=0 width=374 border=0>
																<tr>
																	<td valign=top height=29>
																		<table cellspacing=0 cellpadding=0 width="100%" border=0>
																			<tr>
																				<td class=calendartext valign=bottom height=27>주요일정</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr>
																	<td height=6></td></tr>
																<tr>
																	<td bgcolor=#d9d9d9 height=2></td></tr>
																<tr>
																	<td>
																		<table cellspacing=0 cellpadding=0 width="100%" border=0>
<%
				scheduleList	=	scheDuleDao.getScheduleList(SYSTEMCODE, pCurrentYear, k, "99");
				String	pListDate1	=	"";
				String	pListDate2	=	"";
				String	pListDate3	=	"";
				if(scheduleList != null && scheduleList.size() > 0) {
					scheListDto	=	new ScheduleDTO();
					for(int j=0; j<scheduleList.size(); j++) {
						scheListDto	=	(ScheduleDTO)scheduleList.get(j);
						pListDate1	=	StringUtil.nvl(scheListDto.getStartDate());
						pListDate2	=	StringUtil.nvl(scheListDto.getEndDate());
						if(pListDate1.equals(pListDate2)) {
							pListDate3	=	pListDate1.substring(4, 6)+"월 "+pListDate1.substring(6, 8)+"일";
						} else {
							pListDate3	=	pListDate1.substring(4, 6)+"월 "+pListDate1.substring(6, 8)+"일 ~ "+pListDate2.substring(4, 6)+"월 "+pListDate2.substring(6, 8)+"일";
						}
						
%>
																			<tr>
																				<td align=middle>
																					<table style="margin-top: 4px; margin-bottom: 2px" cellspacing=3 cellpadding=0 width=370 border=0>
																						<tr>
																							<td><strong><%=pListDate3%></strong> &nbsp;&nbsp; <%=StringUtil.getHtmlContents(StringUtil.nvl(scheListDto.getContents())) %> </td>
																						
																						</tr>
																					</table>
																				</td>
																			</tr>
																			<tr>
																				<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/life_s1_4_line.gif" height=1></td>
																			</tr>
<%
						pListDate1	=	"";
						pListDate2	=	"";
						pListDate3	=	"";
					}
				}
%>

																			<tr>
																				<td height=6></td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/life_s1_4_back03_1.gif" height=4></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td colspan="2" height="25"></td>
										</tr>
									</table>
								</td>
							</tr>
<%		
			pCurrentDay		=	0;
			scheYnList		=	null;
		}	//end of for %>
						</table>
						<!-- 내용 끝 -->
					</td>
				</tr>
			</table>
<%@include file="../common/footer.jsp" %>
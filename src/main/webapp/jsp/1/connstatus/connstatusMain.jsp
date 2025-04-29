<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>
<%@ page import ="java.text.DecimalFormat,com.edutrack.connstatus.dto.SystemConnectDTO,java.text.DecimalFormat"%>

<% 	ArrayList resultList = (ArrayList)model.get("systemConCntList");
 	SystemConnectDTO systemConnectDto = (SystemConnectDTO)model.get("systemConnectDto");

	double totalCnt = Double.parseDouble((String)model.get("totalCnt"));
	int year = Integer.parseInt(DateTimeUtil.getYear());		//현재 년
	int mon = Integer.parseInt(DateTimeUtil.getMonth());		//현재 월
	String selectGb = systemConnectDto.getSelectGb();
	String dateGb = systemConnectDto.getDateGb();
	String startYear = systemConnectDto.getStartYear();
	String startMon = systemConnectDto.getStartMon();
	String startDay = systemConnectDto.getStartDay();
	String endYear = systemConnectDto.getEndYear();
	String endMon = systemConnectDto.getEndMon();
	String endDay = systemConnectDto.getEndDay();


	int count = 0;		//년/월/일/시간의 값을 구하기 위한 변수(for)
	String cDate = "0";
	if(selectGb.equals("") || selectGb == null){
		selectGb = "";
	}
%>

<script language="javascript">
	function fnSelectGb(){
       document.myForm.submit();
	}
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name="myForm" method="get" action="<%=CONTEXTPATH%>/ConnStatus.cmd">
<input type=hidden name="cmd" value="systemConnAction">
<input type=hidden name="MENUNO" value="5">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">날짜선택</td>
												<td class="s_tab_view02">
													<select name="startYear">
														<option value="all" <% if(startYear.equals("all") || startYear.equals("")) out.print("selected");%> >전체</option>
<%
	for(count=year; count>=2005; count--){
%>
														<option value="<%=count%>" <% if(startYear.equals(String.valueOf(count))) out.print("selected");%>><%=count%> 년</option>
<%
	}
%>
													</select>
													<select name="startMon">
														<option value="all" <% if(startMon.equals("all") || startMon.equals("")) out.print("selected");%>>전체</option>
<%
	for(count=1; count<=12; count++){
		if(count < 10)
			cDate = "0"+count;
		else
			cDate = ""+count;
%>
														<option value="<%=cDate%>" <% if(startMon.equals(cDate)) out.print("selected");%>><%=count%> 월</option>
<%
	}
%>
													</select>
													<select name="startDay">
														<option value="all" <% if(startDay.equals("all") || startDay.equals("")) out.print("selected");%> >전체</option>
<%
	for(count=1; count<=31; count++){
		if(count < 10)
			cDate = "0"+count;
		else
			cDate = ""+count;
%>
														<option value="<%=cDate%>" <% if(startDay.equals(cDate)) out.print("selected");%>><%=count%> 일</option>
<%
	}
%>
													</select>
													~
													<select name="endYear">
														<option value="all" <% if(endYear.equals("all") || endYear.equals("")) out.print("selected");%> >전체</option>
<%
	for(count=year; count>=2005; count--){
%>
														<option value="<%=count%>" <% if(endYear.equals(String.valueOf(count))) out.print("selected");%> ><%=count%> 년</option>
<%
	}
%>
													</select>
													<select name="endMon">
														<option value="all" <% if(endMon.equals("all") || endMon.equals("")) out.print("selected");%> >전체</option>
<%
	for(count=1; count<=12; count++){
		if(count < 10)
			cDate = "0"+count;
		else
			cDate = ""+count;
%>
														<option value="<%=cDate%>" <% if(endMon.equals(cDate)) out.print("selected");%> ><%=count%> 월</option>
<%
	}
%>
													</select>
													<select name="endDay">
														<option value="all" <% if(endDay.equals("all") || endDay.equals("")) out.print("selected");%> >전체</option>
<%
	for(count=1; count<=31; count++){
		if(count < 10)
			cDate = "0"+count;
		else
			cDate = ""+count;
%>
														<option value="<%=cDate%>" <% if(endDay.equals(cDate)) out.print("selected");%> ><%=count%> 일</option>
<%
	}
%>
													</select>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">구분선택</td>
												<td class="s_tab_view02">
													<select name="selectGb" onChange="fnSelectGb()">
														<option value="1" <% if(selectGb.equals("1") || selectGb.equals("")) out.print("selected");%> >시간별</option>
														<option value="2" <% if(selectGb.equals("2")) out.print("selected");%> >일 별</option>
														<option value="3" <% if(selectGb.equals("3")) out.print("selected");%> >월 별</option>
														<option value="4" <% if(selectGb.equals("4")) out.print("selected");%> >년 별</option>
														<option value="5" <% if(selectGb.equals("5")) out.print("selected");%> >요일별</option>
														<option value="6" <% if(selectGb.equals("6")) out.print("selected");%> >연령별</option>
														<option value="7" <% if(selectGb.equals("7")) out.print("selected");%> >성 별</option>
														<option value="8" <% if(selectGb.equals("8")) out.print("selected");%> >지역별</option>
													</select>
													<%--<input type="text" name="TMP_SEARCH_TEXT" size="22" maxlength="30">--%>
													<img width='285' height=0>
													<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" align="absmiddle" border="0" onClick="javascript:fnSelectGb();" style="cursor:hand;">

												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>

											<tr>
												<td height="25" colspan="2"></td>
											</tr>
											<!-- 그래프영역 시작 -->
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
<%
	String[] tmpResult = null;
	String tmpTime = "";
	int tmpCnt = 0;
	String percent = "0.0";
    int barWidth = 400;
    DecimalFormat decimal = new DecimalFormat("#.#");
	int	totaltmpCnt	=	0;

	for(int i=0;i<resultList.size();i++) {
		tmpResult = (String[])resultList.get(i);
		tmpTime = tmpResult[0];
		tmpCnt = Integer.parseInt(tmpResult[1]);
		if(totalCnt == 0) {
			percent = "0";
		} else {
			percent = decimal.format((tmpCnt/totalCnt)*100.0);
		}
		barWidth = (int)((tmpCnt*100)/totalCnt)*3;
		totaltmpCnt	+=	tmpCnt;

		if(i >= 1) {
%>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%		}	%>
											<tr>
												<td colspan="2">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td class="s_tab_view01" width="105"><%=tmpTime%></td>
															<td class="s_tab_view02">
																<table width="350" height=17 cellspacing=1 cellpadding=1 bgcolor=#f4f4f4>
																	<tr>
																		<td width=250 bgcolor=#ffffff height=17>
																			<table height="17" width="250" align="center">
																				<tr>
																					<td class="ballot"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=barWidth%>" height="13"></td>
																				</tr>
																			</table>
																		</td>
																	</tr>
																</table>
															</td>
															<td class="s_tab_view02" width="50"><b><font class="s_text03"><%=tmpCnt%></font></b>명</td>
															<td class="s_tab_view02" width="50"><font class="s_text03"><%=percent%></font>%</td>
														<tr>
													</table>
												</td>
											</tr>
<%	}	%>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>
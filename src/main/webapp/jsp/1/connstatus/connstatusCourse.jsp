<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>
<%@ page import ="java.text.DecimalFormat,com.edutrack.connstatus.dto.CourseConnectDTO,java.text.DecimalFormat"%> 

<% 	ArrayList resultList = (ArrayList)model.get("courseConCntList");
 	CourseConnectDTO courseConnectDto = (CourseConnectDTO)model.get("courseConnectDto");
 	
	double totalCnt = Double.parseDouble((String)model.get("totalCnt"));
	int year = Integer.parseInt(DateTimeUtil.getYear());		//현재 년
	int mon = Integer.parseInt(DateTimeUtil.getMonth());		//현재 월
	String selectGb = courseConnectDto.getSelectGb();	
	String startYear = courseConnectDto.getStartYear();
	String startMon = courseConnectDto.getStartMon();
	String startDay = courseConnectDto.getStartDay();
	String endYear = courseConnectDto.getEndYear();
	String endMon = courseConnectDto.getEndMon();
	String endDay = courseConnectDto.getEndDay();


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
                </tr>
                <!-- main 시작 -->
                <tr> 
					<td height="410" align="left" valign="top" style="padding:0 10 0 15">	
           				<table width="100%" height="35" border="0" cellpadding="0" cellspacing="0" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/03_cen_titlebg.gif">
							<tr>
								<td align="left" valign="middle" style="padding:0 0 0 8"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/03_title30.gif" width="134" height="19"></td>
							</tr>
						</table>
							
						<br>
							
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<!-- form start -->
						<form name="myForm" method="get" action="<%=CONTEXTPATH%>/ConnStatus.cmd">
						<input type=hidden name="cmd" value="courseConnAction">
						<input type=hidden name="MENUNO" value="5">	
							<tr>
								<td align="center" valign="top">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="center" valign="top">&nbsp;</td>
										</tr>
									</table>
							
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="2" class="b_td01"> </td>
										</tr>
										<tr>
											<td height="30" >
												<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<%--<td width="80" align="center" class="b_td02"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_t152.gif" width="43" height="13"></td>--%>
														<td width="80" align="center" class="b_td02">날짜선택</td>
														<td width="4" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 10">
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
		    if(count < 10) cDate = "0"+count;
		    else cDate = ""+count;
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
		    if(count < 10) cDate = "0"+count;
		    else cDate = ""+count;
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
		    if(count < 10) cDate = "0"+count;
		    else cDate = ""+count;
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
		    if(count < 10) cDate = "0"+count;
		    else cDate = ""+count;
%>
																<option value="<%=cDate%>" <% if(endDay.equals(cDate)) out.print("selected");%> ><%=count%> 일</option>
<%
		}
%>								
															</select>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="1" class="b_td03"> </td>
										</tr>
										<tr>
											<td height="30" >
										
												<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<%--<td width="80" align="center" class="b_td02"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_t153.gif" width="43" height="13"></td>--%>
														<td width="80" align="center" class="b_td02">구분선택</td>
														<td width="4" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti1.gif"></td>
														<td style="padding:0 10 0 10">
															<%=model.get("courseSelect")%>
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
													        <img width='20' height=0>
															<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/btn_search1.gif" align="absmiddle" width="49" height="19" border="0" onClick="javascript:fnSelectGb();" style="cursor:hand;">
														</td>
													</tr>
												</table>
							
											</td>
										</tr>
										<tr>
											<td height="1" class="b_td03"> </td>
										</tr>
										<tr>
											<td height="30" style="padding:10 0 10 0">
												<!-- 테이블 -->
												<table align="center" border="0" cellpadding="0" cellspacing="1" width="100%" bgcolor="#D5CCC3">
<%
	String[] tmpResult = null;
	String tmpTime = "";
	int tmpCnt = 0;
	String percent = "0.0";
    int barWidth = 400;
    DecimalFormat decimal = new DecimalFormat("#.#");
	int	totaltmpCnt	=	0;
	
	for (int i=0;i<resultList.size();i++) {
		tmpResult = (String[])resultList.get(i);
		tmpTime = tmpResult[0];
		tmpCnt = Integer.parseInt(tmpResult[1]);
		percent = decimal.format((tmpCnt/totalCnt)*100.0);
		barWidth = (int)((tmpCnt*100)/totalCnt)*3;
		totaltmpCnt	+=	tmpCnt;
%>													
													<tr>
														<td width="82" bgcolor="#F8F6F3" align="center"><%=tmpTime%></td>
														<td bgcolor="white">
											
															<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
																<tr>
																	<td width="355">
																			<!-- 그래프 -->
																			<table align="center" border="0" cellpadding="1" cellspacing="0" width="98% height=" 10" bgcolor="#CCCCCC">
																				<tr>
																					<td width="100%" height="10" bgcolor="white">
																						<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%" height="8">
																							<tr>
																								<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/ing_bar.gif" width="<%=barWidth%>" height="13" hspace="2" align="absmiddle"></td>
																							</tr>
																						</table>
																					</td>
																				</tr>
																			</table>
																			<!-- 그래프 -->
																		</td>
																		<td width="82" align="center"><b><font color="#FF6600"><%=tmpCnt%></font></b>명</td>
																		<td width="76" align="center"><b><font color="#FF6600"><%=percent%></font></b>%</td>
																	</tr>
																</table>
															</td>
														</tr>
<%
	}
%>
													</table>
													<!-- 테이블 -->
												</td>
											</tr>
											<tr>
												<td height="25" align="right">
												<b>전체 : <font color="#FF6600"><%=totaltmpCnt%></font> 명</b>
												</td>
											</tr>
											<tr>
												<td height="2" class="b_td01" >
											</td>
										</tr>
									</table>
							
								</td>
							</tr>
						</form>
						<!-- form end -->
						</table>
					</td>
				</tr>

           				
<%@include file="../common/footer.jsp" %>
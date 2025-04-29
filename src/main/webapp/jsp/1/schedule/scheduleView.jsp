<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Date"%>
<%@ page import ="com.edutrack.schedule.dto.ScheduleDTO"%>
<%@include file="../common/header.jsp" %>
<%

	if(USERID.equals("")) {
%>
<script>
alert('로그인 하신후 이용하세요.');
history.back();
</script>
<%
		return;
	}

///////////////////////////////////////////////// 날짜시간
	int idate = new Date().getDate();
/////////////////////////////////////////////////
	String pVMode = StringUtil.nvl((String)model.get("pVMode"), "");

	//String	titimg	=	"03_centitle06.gif";
    //
	//if (pVMode.equals("1")) {
	//	titimg	=	"03_centitle06_1.gif";
	//}
	//else {
	//	titimg	=	"03_centitle06.gif";
	//}

	Map data = (Map)request.getAttribute("MODEL");
	String st_year = (String)model.get("st_year");
	String st_mon = (String)model.get("st_mon");
	String ed_day = (String)model.get("ed_day");
	String pr_year = (String)model.get("pr_year");
	String pr_mon = (String)model.get("pr_mon");
	String ne_year = (String)model.get("ne_year");
	String ne_mon = (String)model.get("ne_mon");
	String st_week = (String)model.get("st_week");
	String pBEFORE = (String)model.get("pBEFORE");

	ScheduleDTO scheduleview = (ScheduleDTO)data.get("scheduleview");
	int	stcnt	=	scheduleview.getTcnt();
	if (stcnt==0) stcnt=1;

	String[] contents = new String[stcnt+1];


	//월 이미지
	String st_mon_img = "";
	if(Integer.parseInt(st_mon) < 10){
		st_mon_img = "month_" + "0" + st_mon + ".gif";
	}else{
		st_mon_img = "month_" + st_mon + ".gif";
	}
%>
<script language="javascript">
	function goAdd(mode, sch_id, year, month, day)
	{
		if(mode == "W") {
			document.location = "<%=CONTEXTPATH%>/Schedule.cmd?cmd=ScheduleWrite&pVMode=<%=pVMode%>&pRegMode="+mode+"&pYear="+year+"&pMonth="+month+"&pDay="+day;
		}else{
			document.location = "<%=CONTEXTPATH%>/Schedule.cmd?cmd=ScheduleWrite&pVMode=<%=pVMode%>&pRegMode="+mode+"&pSchId="+sch_id;
		}
	}

	function showHideLayers(i)
	{
<%
		for (int v=0; v < stcnt+1; v++){
			contents[v]	=	"";
%>
			if(i == <%=v%>) {
				document.all.schedule<%=v%>.style.visibility = "visible";
			}else{
				document.all.schedule<%=v%>.style.visibility = "hidden";
			}
<%
		}
%>
	}

</Script>

										<!-- 내용 -->
										<table width="670" border="0" cellspacing="0" cellpadding="0">
<form name=Input method=post>
<input type="hidden" name="tcnt" value="<%=stcnt%>">
											<tr>
												<td height="50"></td>
												<td height="50" width="82">
													<table width="100%" border="0" cellspacing="5" cellpadding="0" height="50">
														<tr height="27" align="center">
															<td width="41" valign="bottom"><font  color="#999999"><b><%=st_year%></b></font></td>
															<td width="41" valign="middle"><img src="./img/<%=SYSTEMCODE%>/calendar/<%=st_mon_img%>" align="absmiddle"></td>
														</tr>
														<tr height="13">
															<td width="41"><a href="/Schedule.cmd?cmd=scheduleView&pVMode=<%=pVMode%>&pCU_YEAR=<%=pr_year%>&pCU_MON=<%=pr_mon%>&pBEFORE=<%=pBEFORE%>" onMouseOver="window.status='Prev'; return true" onMouseOut="window.status=''; return true"><img src="./img/<%=SYSTEMCODE%>/calendar/main_prev.gif"></a></td>
															<td width="41"><a href="/Schedule.cmd?cmd=scheduleView&pVMode=<%=pVMode%>&pCU_YEAR=<%=ne_year%>&pCU_MON=<%=ne_mon%>&pBEFORE=<%=pBEFORE%>" onMouseOver="window.status='Next'; return true" onMouseOut="window.status=''; return true"><img src="./img/<%=SYSTEMCODE%>/calendar/main_next.gif"></a></td>
														</tr>
													</table>
												</td>
<%
		if (!pVMode.equals("1") && !USERID.equals("")) {
%>
												<td align="center">
<span style="font-size:9pt;"><font color="red">※ 일자를 클릭하면 일정을 등록할 수 있습니다.</font></span>
												</td>
												<td align="right">
<!--a href="javascript:goAdd('W','','','','');"><img src="./img/<%=SYSTEMCODE%>/button/b_my_schedule.gif" border="0" align="absmiddle"></a-->
<script language=javascript>Button5("일정등록", "goAdd('W','','','','')", "");</script>
												</td>
<%
		}
%>
											</tr>
										</table>
										<table border=0 cellspacing=0 cellpadding=0 width="670" height="280">
											<tr>
												<td height="221" align="center">
													<table width="100%" border="0" cellspacing="1" cellpadding="0" height="221"  bgcolor='#EAEAEA'>
														<tr>
															<td bgcolor="#F29152" height="20" align="left"><font color="#FFFFFF">&nbsp;<b>일</b></font></td>
															<td bgcolor="#CFCFCF" height="20" align="left"><font color="#FFFFFF">&nbsp;<b>월</b></font></td>
															<td bgcolor="#CFCFCF" height="20" align="left"><font color="#FFFFFF">&nbsp;<b>화</b></font></td>
															<td bgcolor="#CFCFCF" height="20" align="left"><font color="#FFFFFF">&nbsp;<b>수</b></font></td>
															<td bgcolor="#CFCFCF" height="20" align="left"><font color="#FFFFFF">&nbsp;<b>목</b></font></td>
															<td bgcolor="#CFCFCF" height="20" align="left"><font color="#FFFFFF">&nbsp;<b>금</b></font></td>
															<td bgcolor="#8F9D0A" height="20" align="left"><font color="#FFFFFF">&nbsp;<b>토</b></font></td>
														</tr>
<%
		int a = 0; 	int DD = 0; int idx1 = 0; int idx2 = 0;
		String scolor = ""; String stype_img = "";
		int rowIndex = 0;
		String	todayEvent	=	"오늘의 일정이 없습니다.";

		//-- 6주 출력
		for(int i=1; i <= 6; i++ ) {
			if(DD < Integer.parseInt(ed_day)){
				if(i == 1) idx1 = 0;
				else if(DD+7 >= Integer.parseInt(ed_day)) idx1 =2;
				else idx1 = 1;
				rowIndex++;
%>
														<tr>
<%
			//--일요일 ~ 토요일까지--
			for(int j=1; j<=7; j++) {
				idx2 = 0;
				if(j == 1) {
					idx2 = 0;
				}else if (j == 7) {
					idx2 = 2;
				}else{
					idx2 = 1;
				}

				//첫번째주에서 날짜가 없는 칸 배열(매월의 시작일보다 작은경우)
				if (i == 1 && j < Integer.parseInt(st_week)) {
%>
															<td bgcolor="#ffffff" align=center valign=top height=45 width=75  >&nbsp;</td>
<%
				//마지막주에서 날짜가 없는 칸 배열(매월의 종료일보다 큰경우)
				}else if(DD >= Integer.parseInt(ed_day)){
%>
															<td bgcolor="#ffffff" align=center valign=top height=44 width=75 >&nbsp;</td>
<%
				//1일부터 ~ 말일까지 처리
				}else{

					DD = DD + 1;
					scolor = "#B0B0B0";		//평일
					if( j == 1) scolor = "#FF6699";		//일요일
					else if( j == 7) scolor = "#1593B8"; //토요일
%>
															<td bgcolor="#ffffff" align="center" valign="top" height="60" width="14%">
																<table width="100%" border="0" cellspacing="0" cellpadding="0" height="44">
																	<tr height="15">
																		<td width="15" align="center" valign="bottom"><b>
<%
					if (USERTYPE.equals("M") || !pVMode.equals("1")) {
%>
<a href="javascript:goAdd('W','','<%=st_year%>','<%=st_mon%>','<%=DD%>')">
<%
					}
%>
<font color=<%=scolor%>><%=DD%></font></b></a></td>
																		<td>&nbsp;</td>
																	</tr>
																	<tr>
																		<td colspan="2" align="left">
<%
					for(int k=0; k < Integer.parseInt(scheduleview.getConCnt()[DD-1]); k++ ){
						if(scheduleview.getTschType()[DD-1][k].equals("1") )  stype_img = "<img src=img/"+SYSTEMCODE+"/calendar/icon_private.gif align=absmiddle>";
						if(scheduleview.getTschType()[DD-1][k].equals("2") )  stype_img = "<img src=img/"+SYSTEMCODE+"/calendar/icon_study.gif align=absmiddle>";
						if(scheduleview.getTschType()[DD-1][k].equals("3") )  stype_img = "<img src=img/"+SYSTEMCODE+"/calendar/icon_anniversary.gif align=absmiddle>";
						if(scheduleview.getTschType()[DD-1][k].equals("4") )  stype_img = "<img src=img/"+SYSTEMCODE+"/calendar/icon_congratulate.gif align=absmiddle>";
						if(scheduleview.getTschType()[DD-1][k].equals("99"))  stype_img = "<img src=img/"+SYSTEMCODE+"/calendar/icon_hacksa.gif align=absmiddle>";

						String	tmpcont	=	"";
						if (USERTYPE.equals("M") || !pVMode.equals("1")) {

							if(!USERTYPE.equals("M") && !(scheduleview.getTschType()[DD-1][k].equals("1") || scheduleview.getTschType()[DD-1][k].equals("3"))){
								tmpcont	=	"<a href=\"javascript:goViewBelowDiv('" + stype_img + "','" + scheduleview.getTschContents()[DD-1][k] + "')\">" + StringUtil.setMaxLength(scheduleview.getTschContents()[DD-1][k], 24) + "</a>";
							}else{
								tmpcont	=	"<a href=\"javascript:goAdd('E','"+scheduleview.getTschId()[DD-1][k]+"')\">" + StringUtil.setMaxLength(scheduleview.getTschContents()[DD-1][k], 24) + "</a>";
							}
							
						}
						else {
							tmpcont	=	StringUtil.setMaxLength(scheduleview.getTschContents()[DD-1][k], 24);
						}
						if (tmpcont==null) tmpcont="";

						if (!tmpcont.equals("")) {
							if (k==0) {
								contents[a] = stype_img + tmpcont;
							}
							else {
								contents[a] += "<br>" + stype_img + tmpcont;
							}
						}

					}//end for
					if (!contents[a].equals("") && idate==DD) {
						todayEvent	=	contents[a];
					}
					if (!contents[a].equals("")) {
%><%=contents[a]%><%
						a++;
					}
%>
																		</td>
																	</tr>
																</table>
															</td>
<%
				}//end if
			}//end for
		}//end if
%>
														</tr>
<%
	}//end for
%>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td height="32" colspan=7>&nbsp;
												<img src="img/<%=SYSTEMCODE%>/calendar/icon_private.gif" align="absmiddle"> 업무일정
												<img src="img/<%=SYSTEMCODE%>/calendar/icon_study.gif" align="absmiddle"> 학습일정
												<img src="img/<%=SYSTEMCODE%>/calendar/icon_hacksa.gif" align="absmiddle"> 학사일정
												<img src="img/<%=SYSTEMCODE%>/calendar/icon_congratulate.gif" align="absmiddle"> 학습관일정
												<img src="img/<%=SYSTEMCODE%>/calendar/icon_anniversary.gif" align="absmiddle"> 기념일
												</td>
											</tr>

<!--
										</table>

									</td>
								</tr>
								<tr>
									<td>
-->
											<tr>
												<td colspan="2" class="content_top" valign="top">
													<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
														<tr>
															<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/calendar/score_icon_01.gif">오늘의 일정</td>
														</tr>
													</table>
													<table width="100%" border="0" cellspacing="1" cellpadding="10" bgcolor="#D5D5D5">
														<tr>
															<td height="60" bgcolor="#FFFFFF"><div id="scheduleViewDiv"><%=todayEvent%></div></td>
					
<%
/*
					int topPosition = 415 + 47 * rowIndex;
					for(int k=0; k < stcnt; k++)  {
%>
					<div id="schedule<%=k%>" style="position:absolute; left:225px; top:<%=topPosition%>px; width:400px; height:100px; z-index:1; visibility: hidden;margin-left:50;margin-top:40">
					<div align=left valign=top><font color='#008080'><%//=StringUtil.getHtmlContents(contents[k])%></font></div>
					</div>
<%
					}
*/
%></td>
													</tr>
												</table>

												</td>
											</tr>
											<tr>
												<td height="4"></td>
											</tr>
</form>
                  						</table>
									<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>

<Script>

function goViewBelowDiv(img_val, val){
	document.all.scheduleViewDiv.innerHTML = img_val + val;
}
</Script>
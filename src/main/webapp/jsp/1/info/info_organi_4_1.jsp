<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>
<script language="javascript">
<!--
	for (var k=1; k<=3; k++)
	{
		eval('menu'+k+'A = new Image();');
		eval('menu'+k+'B = new Image();');
		
		eval('menu'+k+'A.src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb13_0'+k+'_off.gif";');
		eval('menu'+k+'B.src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb13_0'+k+'_on.gif";');
	
	}
	
	function changeDivMenuLayer(n, i) {
	    for(var j=1; j<=n; j++) {
	        $("profInfoDiv0"+j).style.display = "none";
	    }
	    $("profInfoDiv0"+i).style.display = "";
	}
//-->
</script>
									<table width="660" align="center">
										<tr valign="top">
											<td>
												<table width="100%" class="Tab01">
													<tr valign="top">
<td width="91" class="Tab02"><img 
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab14_01_on.gif" name="tab01" width="91" height="26" border="0"></td>
<td><a 
	href="javascript:alert('준비중입니다.');" onFocus='blur()'; onMouseOut="MM_swapImgRestore()" 
	onMouseOver="MM_swapImage('tab_02','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab14_02_on.gif',1)"><img 
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab14_02_off.gif" name="tab_02" width="94" height="26" border="0"></a></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr valign="top">
											<td>
												<table width="640" align="center" onload="MM_preloadImages('<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb13_01_on.gif','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb13_02_on.gif','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb13_02_on.gif')">
													<tr valign="top">
														<td class="ctit_pd02">
															<table width="100%" align="left">
																<tr>
<td width="120"><A onMouseOver="chng11('menu1','menu1B');chng11('menu2','menu2A');chng11('menu3','menu3A');
					changeDivMenuLayer(3,1)"><IMG id=menu1 height=25 
					src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb13_01_on.gif" width=116 border=0 name=menu1></A></td>
<td width="160"><A onMouseOver="chng11('menu2','menu2B');chng11('menu1','menu1A');chng11('menu3','menu3A');
					changeDivMenuLayer(3,2)"><IMG id=menu2 height=25 
					src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb13_02_off.gif" width=156 border=0 name=menu2></A></td>
<td><A onMouseOver="chng11('menu3','menu3B');chng11('menu1','menu1A');chng11('menu2','menu2A');
		changeDivMenuLayer(3,3)"><IMG id=menu3 height=25 
		src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb13_03_off.gif" width=116 border=0 name=menu3></A></td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<!-- 컨텐츠 내용 -->
										<tr valign="top">
											<td>
<DIV id="profInfoDiv01" style="display:show;">
												<!-- 1학년--> 
												<table width="630" align="center">
													<!-- 1학년 1학기-->
													<tr>
														<td class="ctit" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01.gif"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
															<!-- 사회과학 -->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>사회과학 </strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr01.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>강남훈(한신대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 정치경제학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr02.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>박승호(전태일을 따르는 민주<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;노동연구소)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 정치경제학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr03.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>허석렬(충북대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 사회학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr04.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>이승협(중앙대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 사회학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr05.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>이종구(성공회대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 사회학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr06.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>정해구(성공회대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 정치학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr07.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>김 원(서강대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 정치학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr08.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>송주명(한신대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 정치학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- 역사 -->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>역사 </strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr09.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>이세영(한신대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 한국현대사</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"></b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"></span></td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr11.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>홍석률(성신여대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 한국현대사</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr12.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>이승휘(명지대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 동아시아현대사</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr13.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>이희옥(중앙대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 동아시아현대사</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr14.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>김종원(경희대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 서양현대사</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- 일반교양-->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>일반교양 </strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr15.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>박병상(환경운동가)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 과학기술</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr16.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>황지우(한국예술종합학교)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 문화예술</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr17.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>이정덕(전북대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 인간학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr18.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>노용석(진실화해위원회)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 인간학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- 종합교양-->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>종합교양 </strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr19.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>박성수(한국해양대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 철학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300"></td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
															</table>
														</td>
													</tr>
													<!-- 1학년 2학기 -->
													<tr>
														<td class="ctit" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02.gif"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
															<!-- 사회과학 -->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>사회과학 </strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr01.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>강남훈(한신대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 정치경제학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr02.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>박승호(전태일을 따르는 민주<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;노동연구소)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 정치경제학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr20.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>정태석(전북대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 사회학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr21.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>박주원(이화여대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 정치학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
															</table>
															<!-- 역사 -->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>역사 </strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr11.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>홍석률(성신여대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 한국현대사</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"></td>
																						</tr>
																						<tr> 
																							<td height="22"></td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr23.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>하종문(한산대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 동아시아현대사</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr24.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>박상철(서울대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 서양현대사</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- 일반교양-->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>일반교양 </strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr25.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>이필렬(방송대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 과학기술</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr26.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>신동일(한국예술종합학교)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 문화예술</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr27.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>손병휘(민중가수)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 문화예술</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr28.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>전지영(국악평론가)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 문화예술</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- 종합교양-->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>종합교양 </strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr29.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>홍영두(한국철학연구회)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 철학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300"></td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
															</table>
														</td>
													</tr>
													<!-- // 1학년 2학기 -->
												</table>
</DIV>
<DIV id="profInfoDiv02" style="display:none;">
												<!-- 2학년 -->
												<table width="630" align="center">
													<!-- 2학년 1학기-->
													<tr>
														<td class="ctit" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01.gif"></td>
													</tr>
													<tr valign="top">
														<td class="ctit_pd01">
															<!-- 오늘의 자본주의와 노자관계 -->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>오늘의 자본주의와 노자관계</strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr01.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>김수행(서울대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 영국의 자본주의와 노자관계</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr02.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>정병기(서울대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 독일 사민주의 정치와 노동운동</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr03.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>오삼교(위덕대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 브라질의 자본주의와 노자관계</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr04.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>김영수(경상대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 남아공의 자본주의와 노자관계</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- 오늘의 세계 -->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>오늘의 세계 </strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr05.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>김민웅(성공회대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 오늘의 국제 정치</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr06.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>김성구(한신대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 세계화와 전지구적 자본주의와 당면의 대안</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr07.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>이찬근(인천대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 세계화와 전지구적 자본주의와 당면의 대안</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr08.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>백승욱(중앙대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 세계화와 전지구적 자본주의와 당면의 대안</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr02.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>박승호(민노연)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 세계화와 전지구적 자본주의와 당면의 대안</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr09.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>이채언(전남대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 서세계화와 전지구적 자본주의와 당면의 대안</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- 오늘의 한국사회-->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>오늘의 한국사회</strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr10.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>유철규(성공회대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 한국 자본주의의 성격과 변혁</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr11.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>홍장표(부경대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 한국 자본주의의 성격과 변혁</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr12.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>이병훈(중앙대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 한국 자본주의의 성격과 변혁</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr13.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>김수행(서울대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 한국 자본주의의 성격과 변혁</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr14.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>신광영(중앙대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 한국 자본주의의 성격과 변혁</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr15.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>강정구(동국대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 민족의 분단과 통일</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- 종합교양-->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>종합교양 </strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr16.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>이병창(동아대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 철학(20세기 좌파사상)</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr29.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>김승호(전태일을 따르는 민주<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;노동연구소)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 노동운동사</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
															</table>
														</td>
													</tr>
													<!-- 2학년 2학기 -->
													<tr>
														<td class="ctit" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02.gif"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
															<!-- 사회주의에 대한 비판적 성찰과 인간해방의 전망 -->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>사회주의에 대한 비판적 성찰과 인간해방의 전망 </strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr17.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>차문석(성균관대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 소련 사회주의</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr18.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>최병두(대구대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 소련 사회주의</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr19.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>유팔무(한림대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 소련 사회주의</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr20.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>조정환(정치철학 연구가)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 소련 사회주의</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr21.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>채만수(노동사회과학연구소)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 소련 사회주의</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr22.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>이갑영(인천대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 소련 사회주의</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr02.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>박승호(전태일을 따르는 민주<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;노동연구소)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 소련 사회주의</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr23.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>김현철(고려대 아시아문제연구소)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 현실 사회주의(쿠바)</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr24.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>김귀옥(한성대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 현실 사회주의(북한)</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr25.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>백승욱(중앙대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 현실 사회주의(중국)</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- 사민주의와 인간해방의 전망 -->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>사민주의와 인간해방의 전망 </strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr26.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>신정완(성공회대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 스웨덴 사회민주주의</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr27.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>박승희(성균관대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 스웨덴 사회민주주의</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- 21세기 사회주의 베네수엘라 혁명-->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>21세기 사회주의 베네수엘라 혁명</strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr17.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>전태일을 따르는 민주노동연구소 중남미 방문팀</b></span></td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"></td>
																				<td align="left" valign="top"></td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- 종합교양-->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>종합교양 </strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr16.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>이병창(동아대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 철학</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr29.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>김승호(전태일을 따르는 민주<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;노동연구소)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 노동운동사</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>	
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
															</table>
														</td>
													</tr>
													<!-- // 2학년 2학기 -->
												</table>
</DIV>
<DIV id="profInfoDiv03" style="display:none;">
												<!-- 3학년 -->
												<table width="630" align="center">
													<tr valign="top">
														<td class="ctit_pd01">
															<!-- 정치운동론(한국 -->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>정치운동론(한국)</strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03_pr01.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>박영균(한국철학연구회)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 각 계급 / 분파에 따른 정치지형에 대한 비판적 분석</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_02_pr29.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>김승호(전태일을 따르는 민주<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;노동연구소)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 계급적'변혁적 정치운동으로 노동정치 혁신'발전의 과제와 방향</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- 정치운동 활동론 -->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>정치운동 활동론</strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03_pr02.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>성두현(해방연대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 정치운동의 활동원칙</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03_pr03.gif" width="100" height="120"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>조돈문(카톨릭대)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 노동자 정치운동(계급적)과 시민운동 (초 계급적, 초 민족적)의 관계</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- 노조운동의 혁신과 전략 -->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>노조운동의 혁신과 전략</strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_01_pr02.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>박승호(전태일을 따르는 민주노동연구소)</b></span></td>
																						</tr>
																						<tr> 
																							<td height="22"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon01.gif"><span class="ctext02"><b>담당과목 :</b></span> 혁신</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"></td>
																				<td align="left" valign="top"></td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<!-- 노조운동의 혁신과 전략 -->
															<table width="610" border="0" cellspacing="0" cellpadding="0" align="center">
																<tr> 
																	<td height="2" colspan="2" align="left" valign="top" bgcolor="#D4D4D4" > </td>
																</tr>
																<tr> 
																	<td height="25" colspan="2" align="left">&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><strong>한국사회운동사</strong></td>
																</tr>
																<tr> 
																	<td height="1" colspan="2" align="left" valign="top" bgcolor="#D4D4D4"></td>
																</tr>
																<tr> 
																	<td width="300" style="padding:10 0 10 0">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03_pr04.gif"></td>
																				<td align="left" valign="top">
																					<table width="100%" border="0" cellspacing="0" cellpadding="0">
																						<tr> 
																							<td height="22"><span class="ctext02"><b>전명혁(역사학 연구소)</b></span></td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																	<td width="300">
																		<table width="95%" border="0" cellspacing="0" cellpadding="0" align="center">
																			<tr> 
																				<td width="110"></td>
																				<td align="left" valign="top"></td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr> 
																	<td height="3" colspan="2" align="left" valign="top" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img14_03.gif"> </td>
																</tr>
															</table>
														</td>
													</tr>
													<!-- // 3학년 2학기 -->
												</table>
</DIV>
											</td>
										</tr>
										<!-- // 컨텐츠 내용 -->
									</table>
								</td>
							</tr>
						</table>
						<!-- 내용 끝 -->
<!-- /var/www/html/junnodae/jsp/1/info/info_organi_4_1.jsp --> 

<%@include file="../common/footer.jsp" %>

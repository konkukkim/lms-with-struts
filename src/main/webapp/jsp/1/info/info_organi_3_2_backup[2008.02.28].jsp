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
	        $("HakInfoDiv0"+j).style.display = "none";
	    }
	    $("HakInfoDiv0"+i).style.display = "";
	}
//-->
</script>
									<table width="660" align="center">
										<tr valign="top">
											<td>
												<table width="100%" class="Tab01">
													<tr valign="top">
														<td width="91" class="Tab02"><a href="<%=CONTEXTPATH%>/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=3&pInfoNum2=1" onfocus='blur()'; onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('tab_01','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab13_01_on.gif',1)"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab13_01_off.gif" name="tab_01" width="91" height="26" border="0"></a></td>
														<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab13_02_on.gif" name="tab02" width="94" height="26" border="0"></td>
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
					changeDivMenuLayer(3,1)" href="#"><IMG id=menu1 height=25 
					src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb13_01_on.gif" width=116 border=0 name=menu1></A></td>
<td width="160"><A onMouseOver="chng11('menu2','menu2B');chng11('menu1','menu1A');chng11('menu3','menu3A');
					changeDivMenuLayer(3,2)" href="#"><IMG id=menu2 height=25  
					src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb13_02_off.gif" width=156 border=0 name=menu2></A></td>
<td><A onMouseOver="chng11('menu3','menu3B');chng11('menu1','menu1A');chng11('menu2','menu2A');
		changeDivMenuLayer(3,3)" href="#"><IMG id=menu3 height=25  
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
<DIV id="HakInfoDiv01" style="display:show;">
												<!-- 1학년 --> 
												<table width="630" align="center">
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct13_11.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
														<div align="justify">
														1학년 기초교양 과정에서는 사회와 역사, 인간을 이해하는 일반적이고 기초적인 관점과 지식을 공부합니다.
														특히 오늘날 우리 노동자·민중의 삶을 규정하고 있는 ‘자본주의’에 대한 여러 측면(자본주의의 역사, 운행원리,
														생활양식, 국가와 정치, 자본주의 하에서의 인간에 대한 이해 등)을 두루 살펴보면서 문제의식을 갖게 하고 자본
														주의를 과학적으로 인식하기 위한 기본 개념들을 소화하는데 중점을 두고 있습니다. </div></td>
													</tr>
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct13_12.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
															<table width="100%"  border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td height="30" colspan="2" bgcolor="eeede8" class='bod_tl_t'><b>과 목</b></td>
																	<td width="37%" bgcolor="eeede8" class='bod_tl_t'><b>주 제</b></td>
																	<td width="35%" bgcolor="eeede8" class='bod_tl_t'><b>온라인 강의 담당교수</b></td>
																</tr>
																<tr> 
																	<td width="8%" rowspan="3" bgcolor="f9f8f3" class='bod_tl'>사회<br>과학<br>(택1)</td>
																	<td width="20%" class="bod_co01">정치경제학 I</td>
																	<td class="bod_co01">자본주의 역사 바로 알기</td>
																	<td class="bod_co01">강남훈(한신대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">사회학Ⅰ</td>
																	<td class="bod_co01">노동사회의 비판</td>
																	<td class="bod_co01">허석렬(충북대), 이승협(중앙대), 이종구(성공회대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">정치학Ⅰ</td>
																	<td class="bod_co01">당대 한국 정치의 인식</td>
																	<td class="bod_co01">정해구(성공회대)</td>
																</tr>
																<tr> 
																	<td rowspan="3" bgcolor="f9f8f3" class='bod_tl'>역사<br>(택1)</td>
																	<td class="bod_co01">한국현대사Ⅰ</td>
																	<td class="bod_co01">해방에서 전후사의 인식</td>
																	<td class="bod_co01">이세영(한신대), 고지훈(국사편찬위, 홍석률(성신여대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">동아시아현대사Ⅰ</td>
																	<td class="bod_co01">현대 중국 바로 알기</td>
																	<td class="bod_co01">이승휘(명지대), 이희옥(한신대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">서양사현대사Ⅰ</td>
																	<td class="bod_co01">서구 제국주의의 침략사</td>
																	<td class="bod_co01">김종원(경희대)</td>
																</tr>
																<tr> 
																	<td rowspan="3" bgcolor="f9f8f3" class='bod_tl'>일반<br>교양<br>(택1)</td>
																	<td class="bod_co01">과학기술Ⅰ</td>
																	<td class="bod_co01">생명과 인간</td>
																	<td class="bod_co01">박병상 (환경운동가)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">문화예술Ⅰ</td>
																	<td class="bod_co01">현대 미술과 문화</td>
																	<td class="bod_co01">황지우(한국예술종합학교)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">인간학Ⅰ</td>
																	<td class="bod_co01">인류,인종,민족 문제 바로 알기</td>
																	<td class="bod_co01">이정덕(전북대), 노용석(진실화해위원회)</td>
																</tr>
																<tr> 
																	<td rowspan="2" bgcolor="f9f8f3" class='bod_tl'>종합<br>교양<br>(택1)</td>
																	<td class="bod_co01">철 학Ⅰ</td>
																	<td class="bod_co01">인간해방의 철학Ⅰ</td>
																	<td class="bod_co01">박성수(해양대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">노동운동사Ⅰ</td>
																	<td class="bod_co01">한국노동운동사Ⅰ<br>(일제하 ~ 전평까지)</td>
																	<td class="bod_co01">미 정</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct13_13.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
															<table width="100%"  border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td height="30" colspan="2" bgcolor="eeede8" class='bod_tl_t'><b>과 목</b></td>
																	<td width="34%" bgcolor="eeede8" class='bod_tl_t'><b>주 제</b></td>
																	<td width="38%" bgcolor="eeede8" class='bod_tl_t'><b>온라인 강의 담당교수</b></td>
																</tr>
																<tr> 
																	<td width="8%" rowspan="3" bgcolor="f9f8f3" class='bod_tl'>사회<br>과학<br>(택1)</td>
																	<td width="20%" class="bod_co01">정치경제학 II</td>
																	<td class="bod_co01">자본주의 운행 원리의 이해</td>
																	<td class="bod_co01">강남훈(한신대), <br>박승호(전태일을 따르는 민주노동연구소)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">사회학 II</td>
																	<td class="bod_co01">자본주의 생활양식 비판</td>
																	<td class="bod_co01">정태석(전북대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">정치학 II</td>
																	<td class="bod_co01">자본주의 정치의 이해</td>
																	<td class="bod_co01">박주원(이화여대)<br></td>
																</tr>
																<tr> 
																	<td rowspan="3" bgcolor="f9f8f3" class='bod_tl'>역사<br>(택1)</td>
																	<td class="bod_co01">한국현대사 II</td>
																	<td class="bod_co01">4.19에서 현재까지</td>
																	<td class="bod_co01">홍석률(성신여대), <br>최영묵(국사편찬위원회)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">동아시아현대사 II</td>
																	<td class="bod_co01">일본 군국주의 근현대사</td>
																	<td class="bod_co01">하종문(한신대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">서양사현대사 II</td>
																	<td class="bod_co01">러시아 혁명사</td>
																	<td class="bod_co01">박상철(서울대)</td>
																</tr>
																<tr> 
																	<td rowspan="3" bgcolor="f9f8f3" class='bod_tl'>일반<br>교양<br>(택1)</td>
																	<td class="bod_co01">과학기술 II</td>
																	<td class="bod_co01">현대 과학기술의 제 문제</td>
																	<td class="bod_co01">이필렬(방송대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">문화예술 II</td>
																	<td class="bod_co01">음악의 이해</td>
																	<td class="bod_co01">신동일(한국예술종합학교), <br>손병휘(민중가수), 전지영(국악평론가)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">인간학 II</td>
																	<td class="bod_co01">여성문제의 이해</td>
																	<td class="bod_co01">미 정</td>
																</tr>
																<tr> 
																	<td rowspan="2" bgcolor="f9f8f3" class='bod_tl'>종합<br>교양<br>(택1)</td>
																	<td class="bod_co01">철 학 II</td>
																	<td class="bod_co01">역사유물론</td>
																	<td class="bod_co01">홍영두(한국철학연구회)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">노동운동사 II</td>
																	<td class="bod_co01">한국노동운동사 Ⅱ<br>(한국전쟁 이후 87 대투쟁)</td>
																	<td class="bod_co01">미 정</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
</DIV>
<DIV id="HakInfoDiv02" style="display:none;">
												<!-- 2학년 --> 
												<table width="630" align="center">
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct13_11.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
														<div align="justify">
														&nbsp;&nbsp;2학년 기본이론 과정에서는 1학년 기초교양 과정의 문제의식과 개념들을 바탕으로 해서 우리 노동자계급의 실천에 직접적으로 
														맞닿아 있는 당대의 정치·경제·사회적 현실, 즉 우리가 부딪치고 있는 '세상'을 주체적이고 과학적으로 인식하고 그 세상을 바꾸어 나가기 위한 전망과 대안에 대해 배우고 고민해 봅니다. <br>
														1학기에는 노동운동을 둘러싼 현실을 주체적, 과학적으로 인식하는 데 중점을 두고, 2학기에는 전망과 대안에 중점을 둡니다.1학기 교과과정은 오늘의 세계, 
														오늘의 자본주의와 노자관계, 오늘의 한국사회, 종합교양(철학과 노동운동사)의 4과목으로 구성됩니다. 
														특히 자본의 세계화에 따라 전 세계가 긴밀한 연계 속에서 움직이고 있는 현실을 감안하여 세계적 시야에서 세상을 볼 수 있게 하는 데 주안점을 두었습니다. <br>
														2학기 교과과정은 현실에 대한 구체적 인식을 바탕으로 해서 노동운동이 지향하는 전망목표와 거기에 이르는 길에 대해 탐구하는 데 초점을 둡니다. 
														그 가운데서도 전망목표 내지 대안에 중점을 둡니다. 그 목표에 이르는 길에 대해서는 3학년 과정에서 보다 집중적으로 공부합니다.<br>
														대안문제와 관련된 첫 번째 강좌는 소련 사회주의와 현실 사회주의 두 과목 가운데 한 과목을 선택하는 방식이고, 두 번째, 
														세 번째 강좌는 선택과목을 두지 않고 단일과목으로 편성되어 있습니다. 종합교양은 철학과 노동운동사 두 과목 가운데 한 과목을 선택하는 방식을 취합니다. </div></td>
													</tr>
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct13_14.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
															<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td height="30" colspan="2" bgcolor="eeede8" class='bod_tl_t'><b>과 목</b></td>
																	<td width="42%" bgcolor="eeede8" class='bod_tl_t'><b>주 제</b></td>
																	<td width="39%" bgcolor="eeede8" class='bod_tl_t'><b>온라인 강의 담당교수</b></td>
																</tr>
																<tr> 
																	<td colspan="2" rowspan="4" bgcolor="f9f8f3" class='bod_tl'>오늘의 자본주의와 <br>노자관계 (택1)</td>
																	<td class="bod_co01">영국의 자본주의와 노자관계</td>
																	<td class="bod_co01">김수행(서울대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">독일 사민주의 정치와 노동운동</td>
																	<td class="bod_co01">정병기(서울대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">브라질의 자본주의와 노자관계</td>
																	<td class="bod_co01">오삼교(위덕대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">남아공의 자본주의와 노자관계</td>
																	<td class="bod_co01">김영수(경상대)</td>
																</tr>
																<tr> 
																	<td width="9%" rowspan="2" bgcolor="f9f8f3" class='bod_tl'>오늘의 <br>세계<br>(택1)</td>
																	<td width="10%" class="bod_co01">정 치</td>
																	<td class="bod_co01">오늘의 국제 정치</td>
																	<td class="bod_co01">김민웅(성공회대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">경 제</td>
																	<td class="bod_co01">세계화와 전지구적 자본주의와 당면의 대안</td>
																	<td class="bod_co01">김성구(한신대), 이찬근(인천대), <br>백승욱(중앙대), 박승호(민노연), <br>이채언(전남대)</td>
																</tr>
																<tr> 
																	<td colspan="2" rowspan="2" bgcolor="f9f8f3" class='bod_tl'>오늘의 한국사회<br>(택1)</td>
																	<td class="bod_co01"> 한국 자본주의의 성격과 변혁</td>
																	<td class="bod_co01">유철규(성공회대), 홍장표(부경대), <br>이병훈(중앙대), 김성구(한신대), <br>김수행(서울대), 신광영(중앙대) </td>
																</tr>
																<tr> 
																	<td class="bod_co01"> 민족의 분단과 통일</td>
																	<td class="bod_co01">강정구(동국대)</td>
																</tr>
																<tr> 
																	<td rowspan="2" bgcolor="f9f8f3" class='bod_tl'>종합교양(택1)</td>
																	<td class="bod_co01">철학 III</td>
																	<td class="bod_co01"> 20세기 좌파사상</td>
																	<td class="bod_co01">이병창(동아대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">노동<br>운동사 III</td>
																	<td class="bod_co01"> 당대 노동운동사(87년 이후)</td>
																	<td class="bod_co01">김승호(전태일을 따르는 <br>민주노조운동연구소)</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct13_15.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
															<table width="100%"  border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td height="30" colspan="2" bgcolor="eeede8" class='bod_tl_t'><b>과 목</b></td>
																	<td width="42%" bgcolor="eeede8" class='bod_tl_t'><b>주제</b></td>
																	<td width="39%" bgcolor="eeede8" class='bod_tl_t'><b>온라인 강의 담당교수</b></td>
																</tr>
																<tr> 
																	<td colspan="2" rowspan="2" bgcolor="f9f8f3" class='bod_tl'>사회주의에 대한 <br>비판적 성찰과 <br>인간해방의 전망<br>(택 1)</td>
																	<td width="18%" class="bod_co01">소련 사회주의</td>
																	<td class="bod_co01">차문석(성균관대), 최병두(대구대), 
																	<br>유팔무(한림대), 조정환(정치철학 연구가), 
																	<br>채만수(노동사회과학연구소), 
																	<Br>이갑영(인천대), 
																	<br>박승호(전태일을 따르는 민주노동연구소)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">현실 사회주의(쿠바, 북한, 중국)</td>
																	<td class="bod_co01">김연철(고려대 아시아문제연구소), <Br>김귀옥(한성대), <br>백승욱(중앙대)</td>
																</tr>
																<tr> 
																	<td colspan="2" bgcolor="f9f8f3" class='bod_tl'>사민주의와 <br>인간해방의 전망</td>
																	<td class="bod_co01">스웨덴 사민주의</td>
																	<td class="bod_co01">신정완(성공회대), 박승희(성균관대)</td>
																</tr>
																<tr> 
																	<td colspan="2" bgcolor="f9f8f3" class='bod_tl'>21세기 사회주의 <br>베네수엘라 혁명</td>
																	<td class="bod_co01">21세기 사회주의로 이행하는 베네수엘라</td>
																	<td class="bod_co01">전태일을 따르는 민주노동연구소 중남미 방문팀</td>
																</tr>
																<tr> 
																	<td width="9%" rowspan="2" bgcolor="f9f8f3" class='bod_tl'>종합 <br>교양<br>(택1)</td>
																	<td width="10%" class="bod_co01">철학(Ⅳ)</td>
																	<td class="bod_co01">포스트 모더니즘의 이해</td>
																	<td class="bod_co01">이병창(동아대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">노동<br>운동사<br>(Ⅳ)</td>
																	<td class="bod_co01">당대노동운동사 Ⅱ<br>(전노협 사수에서 노개투 총파업까지)</td>
																	<td class="bod_co01">김승호 (전태일을 따르는 <br>민주노동연구소)</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
</DIV>
<DIV id="HakInfoDiv03" style="display:none;">
												<!-- 3학년 -->
												<table width="630" align="center">
													<tr>
														<td width="631" class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct13_11.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="bod_pd">
														<div align="justify">
														&nbsp;&nbsp;3학년 과정은 노동운동 전문실무 과정입니다. 민주노조운동과 노동자 정치운동, 노동자 시민운동으로 전공을 선택하여 공부합니다. 
														1학기에는 각 운동에 대한 전문이론을 공부하고 2학기에는 우리의 현실 속에서 민주노조운동, 노동자 정치운동, 
														노동자 정치운동의 구체적인 활동 현황을 공유하고 발전방향을 모색하는 활동론입니다. 각 전공과정을 통해 노동운동의 활동가로서 실천적 이론과 전문적 실무를 연마합니다. </div></td>
													</tr>
													<tr>
														<td class="ctit_pd">
															<table width="100%"  border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td width="26%" bgcolor="eeede8" class='bod_tl_t'> <b>노동자 정치운동</b></td>
																	<td width="74%" class="bod_co02">노동자 정치운동론(일반),세계노동자 정치운동사, <br>노동자 정치운동론(일반), 한국사회운동사(3학년 공통과목) </td>
																</tr>
																<tr> 
																	<td width="26%" bgcolor="eeede8" class='bod_tl_t'> <b>민주노조운동과정</b></td>
																	<td width="74%" class="bod_co02">세계노동운동사, 노조운동론(일반), <br>노조운동론(한국), 한국사회운동사(3학년 공통과목) </td>
																</tr>
																<tr> 
																	<td width="26%" bgcolor="eeede8" class='bod_tl_t'> <b>노동자 시민운동과정</b></td>
																	<td width="74%" class="bod_co02">시민(사회)운동론(일반), 시민(사회)운동론(한국), <br>시민(사회)운동의 제조류, 한국사회운동사(3학년 공통과목) </td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct13_16.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="bod_pd">
														1학기 강좌는 교과 신설과 전면개편을 준비하는 동안 진행될 교과입니다.</td>
													</tr>
													<tr>
														<td style="padding:5 0 5 20">
														<span class="ctext01"><b> <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/dot01.gif" align="middle"> 정치운동 과정</b></span></td>
													</tr>
													<tr>
														<td class="bod_pd">
															<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td width="20%" height="30" bgcolor="eeede8" class='bod_tl_t'><b>과 목</b></td>
																	<td width="57%" bgcolor="eeede8" class='bod_tl_t'><b>주 제</b></td>
																	<td width="23%" bgcolor="eeede8" class='bod_tl_t'><b>온라인 강의 담당교수</b></td>
																</tr>
																<tr> 
																	<td rowspan="3" bgcolor="f9f8f3" class='bod_tl'>정치운동론<br>(일반)</td>
																	<td class="bod_co01">정치운동의 제조류</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">정치사상론</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">정치 조직/투쟁론</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td rowspan="6" bgcolor="f9f8f3" class='bod_tl'>세계노동자정치<br>운동사</td>
																	<td class="bod_co01">2월 혁명</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">파리꼬뮌</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">러시아혁명</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">1차대전 이후 노동자 계급의 폭발적 투쟁</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">2차 세계대전 이후 제3세계에서 벌어진<br>민족주의운동과 결합한 혁명운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">현재의 세계 노동자 정치운동<Br>(트로츠키운동,세계사회포럼 등)</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td rowspan="3" bgcolor="f9f8f3" class='bod_tl'>정치운동론<br>(한국)</td>
																	<td class="bod_co01">역사 : 정치운동사(일제하에서 당대까지) </td>
																	<td class="bod_co01"> </td>
																</tr>
																<tr> 
																	<td class="bod_co01">현재 : 각 계급 / 분파에 따른 정치지형에<br>대한 비판적 분석</td>
																	<td class="bod_co01">박영균(한국철학연구회)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">계급적ㆍ변혁적 정치운동으로 노동정치 <br>혁신ㆍ발전의 과제와 방향</td>
																	<td class="bod_co01"> 김승호(전태일을 따르는<br>민주노동연구소)</td>
																</tr>
																<tr> 
																	<td rowspan="2" bgcolor="f9f8f3" class='bod_tl'>변혁적 <br>노동운동론</td>
																	<td class="bod_co01">변혁적 노동운동론</td>
																	<td class="bod_co01"></td>
																</tr>																				
																<tr> 
																	<td class="bod_co01">변혁적 노동자정치운동론</td>
																	<td class="bod_co01"></td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td style="padding:5 0 5 20">
														<span class="ctext01"><b> <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/dot01.gif" align="middle"> 노조운동 과정</b></span></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
															<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td width="20%" height="30" bgcolor="eeede8" class='bod_tl_t'><b>과 목</b></td>
																	<td width="57%" bgcolor="eeede8" class='bod_tl_t'><b>주 제</b></td>
																	<td width="23%" bgcolor="eeede8" class='bod_tl_t'><b>온라인 강의 담당교수</b></td>
																</tr>
																<tr> 
																	<td rowspan="6" bgcolor="f9f8f3" class='bod_tl'>세계노동운동사</td>
																	<td class="bod_co01">노동조합운동의 발생기 :<br>영국노동운동사를 중심으로(19세기 초~중반)</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">독점자본의 등장과 산별노조운동 :<br>독일·프랑스·미국 노동운동사를<br>중심으로(19세기 후반~20세기 전반)</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">민지 나라들에서의 노조운동 : 중남미, <br>아시아를 중심으로(20세기 전후)</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">제2차 세계대전 이후의 <br>세계노조운동(1945~1970년대 말)</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">신자유주의 시대<br>세계노조운동(1980~20세기 말)</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">21세기 세계노조운동(전환기)</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td rowspan="6" bgcolor="f9f8f3" class='bod_tl'>노조운동론<br>(일반)</td>
																	<td class="bod_co01">노조운동의 제 형태 : 사상ㆍ이념</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">조직</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">투쟁 </td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">사상ㆍ이념을 중심으로 한 노조운동론의<br>역사</td>
																	<td class="bod_co01"></td>
																</tr> 
																<tr> 
																	<td class="bod_co01">현대의 노조운동론 (68년 이후를 <br>중심으로)</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">낡은 노동운동의 위기와 새로운 모색</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr>
																	<td rowspan="6" bgcolor="f9f8f3" class='bod_tl'>노조운동론<br>(한국)</td>
																	<td class="bod_co01">해방공간에서의 전평의<br>운동노선(1945~1950)</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">1970년대 민주노조운동론(1970~1980)</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">1980년대 변혁적 노동운동과<br>노조운동론(1980~1987)</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">전노협의 운동노선(1987~1995)</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">민주노총의 운동노선(1995~현재)</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">민주노조운동 혁신의 방향과 전략</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td rowspan="2" bgcolor="f9f8f3" class='bod_tl'>변혁적 노동운동론</td>
																	<td class="bod_co01">변혁적 노동운동론</td>
																	<td class="bod_co01"></td>
																</tr>																				
																<tr> 
																	<td class="bod_co01">변혁적 노조운동론/td>
																	<td class="bod_co01"></td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td style="padding:5 0 5 20">
														<span class="ctext01"><b> <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/dot01.gif" align="middle"> 시민사회운동 과정</b></span></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
															<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td width="20%" height="30" bgcolor="eeede8" class='bod_tl_t'><b>과 목</b></td>
																	<td width="57%" bgcolor="eeede8" class='bod_tl_t'><b>주 제</b></td>
																	<td width="23%" bgcolor="eeede8" class='bod_tl_t'><b>온라인 강의 담당교수</b></td>
																</tr>
																<tr> 
																	<td rowspan="8" bgcolor="f9f8f3" class='bod_tl'>시민사회운동의<br>일반이론</td>
																	<td class="bod_co01">개념적 논의</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">시민사회와 시민사회론</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">2차 대전 이후의 서구사회와 신사회운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">전후 서구사회의 변화와 사회운동에 대한<br>이론적 논의들</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">전후 서구자본주의와 소비?문화</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">지구화와 시민사회운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">현대자본주의와 생태환경 이슈</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">지구화와 지역의 변화</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td rowspan="12" bgcolor="f9f8f3" class='bod_tl'>한국의 시민사회<br>운동</td>
																	<td class="bod_co01">87년 이후의 한국사회변화와 사회운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">87년 이후의 민주개혁운동과 시민운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">87년 이후의 제도정치와 민중정치, <br>시민정치</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">사상ㆍ이념을 중심으로 한 노조운동론의<br>역사</td>
																	<td class="bod_co01"></td>
																</tr> 
																<tr> 
																	<td class="bod_co01">87년 이후의 환경과 환경운동의 변화</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">87년 이후의 평화운동의 변화</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">87년 이후의 인권과 인권운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">87년 이후 지역운동의 변화</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">87년 이후의 소수자이유와 소수자운동의<br>변화</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">지구화와 시민사회운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">시민사회의 보수적 분화와 뉴라이트운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">공동체운동과 대안운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr>
																	<td rowspan="8" bgcolor="f9f8f3" class='bod_tl'>서구의 <br>시민사회운동</td>
																	<td class="bod_co01">미국 전후 체제의 균열과 미국 사회운동의<br>전개</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">미국 사회운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">1968년 혁명과 유럽의 신사회운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">유럽의 환경운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">유럽의 평화운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">유럽의 인권운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">프랑스의 사회운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co01">남미의 사회운동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td rowspan="2" bgcolor="f9f8f3" class='bod_tl'>변혁적 노동운동론</td>
																	<td class="bod_co01">변혁적 노동운동론</td>
																	<td class="bod_co01"></td>
																</tr>																				
																<tr> 
																	<td class="bod_co01">변혁적 노동자시민사회운동론/td>
																	<td class="bod_co01"></td>
																</tr>
															</table>
														</td>
													</tr>													
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct13_17.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td style="padding:5 0 5 20">
														<span class="ctext01"><b> <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/dot01.gif" align="middle"> 정치운동 과정</b></span></td>
													</tr>
													<tr valign="top">
														<td class="bod_pd">
															<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td width="17%" height="30" bgcolor="eeede8" class='bod_tl_t'><b>과 목</b></td>
																	<td colspan="2" bgcolor="eeede8" class='bod_tl_t'><b>주 제</b></td>
																	<td width="27%" bgcolor="eeede8" class='bod_tl_t'><b>온라인 강의 담당교수</b></td>
																</tr>
																<tr> 
																	<td rowspan="5" bgcolor="f9f8f3" class='bod_tl'>노동자 정치운동 <br>활동론</td>
																	<td colspan="2" class="bod_co01">정치세력화의 의의와 상에 대한 성찰</td>
																	<td class="bod_co01">박찬식 (전태일을 따르는 <br>민주노동연구소)</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">정치운동의 활동원칙</td>
																	<td class="bod_co01">성두현(평등연대</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">노동자 정치운동(계급적)과 <br>시민운동 (초 계급적, 초 민족적)의 관계</td>
																	<td class="bod_co01">조돈문(카톨릭대)</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">노동자 정치운동의 과제와 방향</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">노동자 정치활동(의식화, 조직화, 투쟁)</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td rowspan="5" bgcolor="f9f8f3" class='bod_tl'>노동자 정당<br>(민주노동당)의 <br>현황과 과제</td>
																	<td colspan="2" class="bod_co01">연혁과 정체성</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">일상활동(교육<b> · </b>선전<b> · </b>조직활동)</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">대선투쟁 현황과 과제</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">쟁점과 과제<br>- 의회주의냐, 사회운동적 정당이냐를 <br>쟁점으로</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">계급대중 정당으로의 혁신문제</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td rowspan="11" bgcolor="f9f8f3" class='bod_tl'>정치운동 조직의 <br>현황과 과제</td>
																	<td width="14%" rowspan="5" class="bod_co01">대중정치운동 조직</td>
																	<td width="42%" class="bod_co02">민중연대의 연혁과 정체성</td>
																	<td rowspan="2" class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co02">민중연대의 주요 활동과 발전전망</td>
																</tr>
																<tr> 
																	<td class="bod_co02">통일운동 - 통일연대의 주요 활동<Br>및 전망과 과제</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co02">FTA반대 운동<br>-한미 FTA저지 범국민운동본부<br>주요 활동과 과제</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co02">비판</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td rowspan="6" class="bod_co01">정파조직</td>
																	<td class="bod_co02">다함께 : 기본 입장과 운영,<br>주요활동 및 과제</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co02">사회당 : 사회주의를 추구하는 <br>이념적 대중정당</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co02">노동자의 힘 : 현황과 과제</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co02">전진 : 현황과 과제</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co02">민주노동자 전국회의 : 현황과 과제</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td class="bod_co02">비판</td>
																	<td class="bod_co01"></td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td style="padding:5 0 5 20">
														<span class="ctext01"><b> <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/dot01.gif" align="middle"> 노조운동 과정</b></span></td>
													</tr>
													<tr valign="top">
														<td class="bod_pd">
															<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td width="17%" height="30" bgcolor="eeede8" class='bod_tl_t'><b>과 목</b></td>
																	<td colspan="2" bgcolor="eeede8" class='bod_tl_t'><b>주 제</b></td>
																	<td width="27%" bgcolor="eeede8" class='bod_tl_t'><b>온라인 강의 담당교수</b></td>
																</tr>
																<tr> 
																	<td rowspan="5" bgcolor="f9f8f3" class='bod_tl'>노조운동의 혁신과<br>전략</td>
																	<td rowspan="4" class="bod_co01">혁신</td>
																	<td class="bod_co01">사회운동적 노조주의의 <br>등장배경과 현실</td>
																	<td rowspan="4" class="bod_co01">박승호<br>(전태일을 따르는<br>민주노동연구소)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">사회운동적 노조주의의 상</td>
																</tr>
																<tr> 
																	<td class="bod_co01">사상ㆍ이념ㆍ정책 차원에서의 <br>사회운동적 노조주의 </td>
																</tr>
																<tr> 
																	<td class="bod_co01">요구ㆍ조직ㆍ투쟁 차원에서의<br>사회운동적 노조주의 </td>
																</tr>
																<tr> 
																	<td class="bod_co01">조직화<br>전략</td>
																	<td class="bod_co01">조직화 전략과 사례</td>
																</tr>
																<tr> 
																	<td rowspan="5" bgcolor="f9f8f3" class='bod_tl'>민주노조 활동론</td>
																	<td colspan="2" class="bod_co01">일상활동, 현황과 문제</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">일상활동의 혁신</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">의식화와 혁신 </td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">조직화와 혁신 </td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">투쟁과 혁신</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td rowspan="6" bgcolor="f9f8f3" class='bod_tl'>상급 및 <br>연대조직의 운영과<Br>주요 활동 및 혁신</td>
																	<td colspan="2" class="bod_co01">민주노총의 운영과 주요활동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">금속노조의 운영과 주요활동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01"> 서울본부의 운영과 주요활동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">일반노조의 운영과 주요활동</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">특수고용 노동자 화물연대</td>
																	<td class="bod_co01"></td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">연대 및 상급조직 활동의 혁신</td>
																	<td class="bod_co01"></td>
																</tr>
																
															</table>
														</td>
													</tr>
													<tr>
														<td style="padding:5 0 5 20">
														<span class="ctext01"><b> <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/dot01.gif" align="middle"> 시민사회운동 과정</b></span></td>
													</tr>
													<tr valign="top">
														<td class="bod_pd">
															<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td width="17%" height="30" bgcolor="eeede8" class='bod_tl_t'><b>과 목</b></td>
																	<td colspan="2" bgcolor="eeede8" class='bod_tl_t'><b>주 제</b></td>
																	<td width="27%" bgcolor="eeede8" class='bod_tl_t'><b>온라인 강의 담당교수</b></td>
																</tr>
																<tr> 
																	<td rowspan="5" bgcolor="f9f8f3" class='bod_tl'>인권</td>
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
																<tr> 
																	<td rowspan="5" bgcolor="f9f8f3" class='bod_tl'>생태,여성</td>
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
																<tr> 
																	<td rowspan="6" bgcolor="f9f8f3" class='bod_tl'>평화</td>
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">&nbsp;</td>
																	<td class="bod_co01">&nbsp;</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td style="padding:5 0 5 20">
														<span class="ctext01"><b> <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/dot01.gif" align="middle"> 공통 과목</b></span></td>
													</tr>
													<tr valign="top">
														<td class="bod_pd">
															<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td width="17%" height="30" bgcolor="eeede8" class='bod_tl_t'><b>과 목</b></td>
																	<td colspan="2" bgcolor="eeede8" class='bod_tl_t'><b>주 제</b></td>
																	<td width="27%" bgcolor="eeede8" class='bod_tl_t'><b>온라인 강의 담당교수</b></td>
																</tr>
																<tr> 
																	<td rowspan="6" bgcolor="f9f8f3" class='bod_tl'>한국사회운동사</td>
																	<td colspan="2" class="bod_co01">한국사회주의운동의 기원,<br>사회주의운동조직의 형성?분화</td>
																	<td class="bod_co01">전명혁(역사학 연구소)</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">조선공산당과 1920년대 사회주의운동</td>
																	<td class="bod_co01">전명혁(역사학 연구소)</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">1930년대 당재건운동과 민족해방운동</td>
																	<td class="bod_co01">전명혁(역사학 연구소)</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">해방 후</td>
																	<td class="bod_co01">전명혁(역사학 연구소)</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">1960년대</td>
																	<td class="bod_co01">전명혁(역사학 연구소)</td>
																</tr>
																<tr> 
																	<td colspan="2" class="bod_co01">현재까지 </td>
																	<td class="bod_co01"></td>
																</tr>
															</table>
														</td>
													</tr>																										
												</table>
</DIV>
											</td>
										</tr>
										<!-- // 컨텐츠 내용 -->
									</table>
								</td>
							</tr>
						</table>

<%@include file="../common/footer.jsp" %>
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
																	<td class="bod_co01">허석렬(충북대), 이승협(중앙대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">정치학Ⅰ</td>
																	<td class="bod_co01">자본주의 정치의 비판적 이해</td>
																	<td class="bod_co01">박주원(영남대)
</td>
																</tr>
																<tr> 
																	<td rowspan="3" bgcolor="f9f8f3" class='bod_tl'>역사<br>(택1)</td>
																	<td class="bod_co01">한국현대사Ⅰ</td>
																	<td class="bod_co01">해방에서 전후사의 인식</td>
																	<td class="bod_co01">이세영(한신대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">동아시아현대사Ⅰ</td>
																	<td class="bod_co01">현대 중국 바로 알기</td>
																	<td class="bod_co01">이승휘(명지대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">서양사현대사Ⅰ</td>
																	<td class="bod_co01">서구 제국주의의 침략사</td>
																	<td class="bod_co01">김종원(경희대)</td>
																</tr>
																<tr> 
																	<td rowspan="3" bgcolor="f9f8f3" class='bod_tl'>일반<br>교양<br>(택1)</td>
																	<td class="bod_co01">과학기술Ⅰ</td>
																	<td class="bod_co01">과학기술의 제문제</td>
																	<td class="bod_co01">이필렬(방송대), 최종덕(상지대)</td>
</td>
																</tr>
																<tr> 
																	<td class="bod_co01">문화예술Ⅰ</td>
																	<td class="bod_co01">현대 미술과 문화</td>
																	<td class="bod_co01">황지우(한국예술종합학교)<br> 최석태(미술평론가)
</td>
																</tr>
																<tr> 
																	<td class="bod_co01">인간학Ⅰ</td>
																	<td class="bod_co01">인류,인종,민족 문제 바로 알기</td>
																	<td class="bod_co01">노용석(진실화해위원회)</td>
																</tr>
																<tr> 
																	<td rowspan="2" bgcolor="f9f8f3" class='bod_tl'>종합<br>교양<br>(택1)</td>
																	<td class="bod_co01">철 학Ⅰ</td>
																	<td class="bod_co01">인간해방의 철학Ⅰ<br>(실천적 유물론)</td>
																	<td class="bod_co01">안상헌(충북대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">노동운동사Ⅰ</td>
																	<td class="bod_co01">한국노동운동사Ⅰ<br>(일제하 ~ 전평까지)</td>
																	<td class="bod_co01">신인철(전태일 노동대학)
</td>
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
																	<td class="bod_co01">강남훈(한신대), <br>박승호(전태일 노동연구소)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">사회학 II</td>
																	<td class="bod_co01">자본주의 생활양식 비판</td>
																	<td class="bod_co01">정태석(전북대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">정치학 II</td>
																	<td class="bod_co01">제국주의 시대의 국제정치</td>
																	<td class="bod_co01">박찬식(전태일노동연구소)<br></td>
																</tr>
																<tr> 
																	<td rowspan="3" bgcolor="f9f8f3" class='bod_tl'>역사<br>(택1)</td>
																	<td class="bod_co01">한국현대사 II</td>
																	<td class="bod_co01">4.19에서 현재까지</td>
																	<td class="bod_co01">홍석률(성신여대), <br>김보영(이화여대)</td>
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
																	<td class="bod_co01">인간과 환경의 관계 성찰</td>
																	<td class="bod_co01">최병두(대구대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">문화예술 II</td>
																	<td class="bod_co01">음악의 이해</td>
																	<td class="bod_co01">신동일(한국예술종합학교), <br>손병휘(민중가수), 전지영(국악평론가)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">인간학 II</td>
																	<td class="bod_co01">여성문제의 이해</td>
																	<td class="bod_co01">최윤희견(계명대), 정숙정(경북대)
</td>
																</tr>
																<tr> 
																	<td rowspan="2" bgcolor="f9f8f3" class='bod_tl'>종합<br>교양<br>(택1)</td>
																	<td class="bod_co01">철 학 II</td>
																	<td class="bod_co01">인간해방의 철학II<br>(역사유물론)</td>
																	<td class="bod_co01">안상헌(충북대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">노동운동사 II</td>
																	<td class="bod_co01">한국노동운동사 Ⅱ<br>(한국전쟁 이후 87 대투쟁)</td>
																	<td class="bod_co01">신인철(전태일 노동대학)
</td>
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
														  <p>2학년 기본이론 과정에서는 1학년 기초교양 과정에서 획득한 문제의식과 개념들을 바탕으로 우리 노동자계급의 실천과 직접적으로 맞닿아 있는 당대의 정치?경제?사회적 현실에 대해 배웁니다. 또한 당대의 현실에 대한 이해를 통해 우리가 살아가고 있는 ‘세상'을 주체적인 시각에서 과학적으로 인식하고 세상을 바꾸어나가기 위한 전망과 대안에 대해 배우고 고민하는 데 초점이 맞춰집니다. 1학기에는 노동운동을 둘러싼 현실을 주체적?과학적으로 인식하는 데 중점을 두고, 2학기에는 전망과 대안에 중점을 둡니다. 
                                                          </p>
														  <p>2학년 교과과정은 오늘의 세계, 오늘의 자본주의와 노자관계, 오늘의 한국사회, 종합교양(철학과 노동운동사)의 4과목으로 구성됩니다. 특히 자본의 세계화에 따라 전 세계가 긴밀한 연계 속에서 움직이고 있는 현실을 감안해 세계적 시야에서 세상을 볼 수 있게 하는 데 주안점을 두었습니다. </p>
														  <p>2학기 교과 과정은 1학기 공부를 통해 획득한 현실 인식을 바탕으로 노동운동이 지향하는 전망과 목표, 그리고 그러한 목표에 이르는 길을 탐구하는 데 초점을 맞춥니다. 그 가운데서도 노동운동이 전망하는 목표 내지 대안에 중점을 두고자 합니다. 2학기 과정은 ‘현실 사회주의에 대한 비판적 성찰과 인간해방의 전망', ‘인간해방에 대한 현재의 실험들', ‘신자유주의 세계화에 대한 당면의 대안', 종합교양(철학과 노동운동사)의 4과목으로 구성됩니다. </p>
														</div></td>
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
																	<td width="9%" rowspan="3" bgcolor="f9f8f3" class='bod_tl'>오늘의 <br>세계<br>(택1)</td>
																	<td width="10%" class="bod_co01">정 치</td>
																	<td class="bod_co01">21세기 국제정치와 전쟁</td>
																	<td class="bod_co01">박찬식(성공회대)
</td>
																</tr>
																<tr> 
																	<td class="bod_co01">경 제</td>
																	<td class="bod_co01">자본주의 위기와 세계대공황
 </td>
																	<td class="bod_co01">강남훈(한신대),<br> 박승호(전태일 노동연구소)
</td>
																</tr>
																
																<tr> 
																	<td class="bod_co01"></td>
																	<td class="bod_co01"> </td>
																	<td class="bod_co01"></td>
																</tr>
																
																<tr> 
																	<td colspan="2" rowspan="2" bgcolor="f9f8f3" class='bod_tl'>오늘의 자본주의와 노자관계<br>(택1)</td>
																	<td class="bod_co01">선진 자본주의 노자관계<br>																	  
																	  (영국, EU, 일본, 미국)</td>
																	<td class="bod_co01">김수행(성공회대), 임운택(계명대),<br>이종구(성공회대), 권영숙(서울대)
</td>
																</tr>
																<tr> 
																	<td class="bod_co01">신흥 자본주의 노자관계<br>(브라질, 남아공, 중국)</td>
																	<td class="bod_co01">조돈문(가톨릭대), 김영수(경상대),<br> 장영석(성공회대)</td>
																</tr>
																<tr> 
																	<td colspan="2" rowspan="3" bgcolor="f9f8f3" class='bod_tl'>오늘의 한국사회<br>(택1)</td>
																	<td class="bod_co01"> 한국 자본주의의 성격</td>
																	<td class="bod_co01">김정주(경상대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01"> 계급형성, 계급구조,<br>정치적ㆍ이데올로기적 지형</td>
																	<td class="bod_co01">신광영(중앙대)</td>
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
																	<td class="bod_co01"> 당대 노동운동사I(87년 이후)</td>
																	<td class="bod_co01">전태일 노동대학</td>
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
																	<td width="18%" class="bod_co01">역사적 사회주의(소련, 동구)</td>
																	<td class="bod_co01">																	유팔무(한림대), 조정환(정치철학 연구가), 
																	<br>채만수(노동사회과학연구소), 
																	<Br>이갑영(인천대), 
																	<Br>김인식(다함께), 
																	<br>박승호(전태일 노동연구소)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">현실 사회주의(쿠바, 북한, 중국)</td>
																	<td class="bod_co01">유재현(작가), <Br>김귀옥(한성대), <br>백승욱(중앙대)</td>
																</tr>
																	<td colspan="2" rowspan="4" bgcolor="f9f8f3" class='bod_tl'>
																	비(非) 사회주의 유형의 인간해방의 전망(공통과목)</td>
																	<td width="18%" class="bod_co01">스웨덴 사민주의 </td>
																	<td class="bod_co01">김인춘(연세대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">생산자 협동조합(몬드라곤)</td>
																	<td class="bod_co01">김성오(생협연구소)</td>
																</tr>

																<tr> 
																	<td class="bod_co01">사민주의 일반</td>
																	<td class="bod_co01">주은선(대구대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01"></td>
																	<td class="bod_co01"></td>
																</tr>

																<tr> 
																	<td colspan="2" rowspan="2" bgcolor="f9f8f3" class='bod_tl'>21세기 사회주의 <br>이행의 실험들</td>
																	<td class="bod_co01">베네수엘라</td>
																	<td class="bod_co01">강남훈(한신대), 허석렬(충북대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">중남미</td>
																	<td class="bod_co01">허석렬(충북대)</td>
																</tr>
																<tr> 
																	<td width="9%" rowspan="2" bgcolor="f9f8f3" class='bod_tl'>종합 <br>교양<br>(택1)</td>
																	<td width="10%" class="bod_co01">철학(Ⅳ)</td>
																	<td class="bod_co01">포스트 모더니즘의 이해와 비판</td>
																	<td class="bod_co01">이병창(동아대)</td>
																</tr>
																<tr> 
																	<td class="bod_co01">노동<br>운동사<br>(Ⅳ)</td>
																	<td class="bod_co01">당대노동운동사 Ⅱ<br>(전노협 사수에서 노개투 총파업까지)</td>
																	<td class="bod_co01">전태일 노동대학</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
</DIV>
<DIV id="HakInfoDiv03" style="display:none">
												<table width="630" align="center">
												<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct13_11.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="bod_pd">
														<div align="justify">
														3학년 과정은 노동운동 전문실무 과정입니다. 민주노조운동과 노동자 정치운동, 노동자 시민운동으로 전공을 선택하여 공부합니다. 
														1학기에는 각 운동에 대한 전문이론을 공부하고 2학기에는 우리의 현실 속에서 민주노조운동, 노동자 정치운동, 
														노동자 정치운동의 구체적인 활동 현황을 공유하고 발전방향을 모색하는 활동론입니다. 각 전공과정을 통해 노동운동의 활동가로서 실천적 이론과 전문적 실무를 연마합니다. </div></td>
													</tr>
													<tr>
														<td class="ctit_pd">
															<table width="100%"  border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td width="26%" bgcolor="eeede8" class='bod_tl_t'> <b>노동자 정치운동 과정</b></td>
																	<td width="74%" class="bod_co02">세계노동자 정치운동사, 정치운동론(일반),정치운동론(한국), <br>, 노동자정치운동의 혁신, 변혁적 정치운동의 실천<br>
																    사회주의 노동운동론(공통과목) </td>
																</tr>
																<tr> 
																	<td width="26%" bgcolor="eeede8" class='bod_tl_t'> <b>노조운동 과정</b></td>
																	<td width="74%" class="bod_co02">세계노동운동사, 전투적 노조운동의 교훈, <br>
																	  사회주의 노조운동론, 민주노조운동의 혁신, 변혁적 노조운동의 실천<br>
																    사회주의 노동운동론(공통과목) </td>
																</tr>
																<tr> 
																	<td width="26%" bgcolor="eeede8" class='bod_tl_t'> <b>노동자 시민사회운동 과정</b></td>
																	<td width="74%" class="bod_co02">한국사회운동사, 시민사회 운동의 제 유형1,
																	시민사회 운동의 제 유형2, <br> 시민사회운동의 혁신과 실천1, 시민사회운동의 혁신과 실천2 <br>
																    사회주의 노동운동론(공통과목) </td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct13_16.gif" width="204" height="13"></td>
													</tr>
													
													<tr>
														<td class="bod_pd">
															<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td width="14%" height="30" bgcolor="eeede8" class='bod_tl_t'><div align="center"><strong>순 서 </strong></div></td>
																	<td width="24%" bgcolor="eeede8" class='bod_tl_t'><div align="center"><strong>노동자 정치운동 과정</strong> </div></td>
																	<td width="36%" bgcolor="eeede8" class='bod_tl_t'><div align="center"><strong>노조운동 과정 </strong></div></td>
																	<td width="26%" bgcolor="eeede8" class='bod_tl_t'><div align="center"><strong>노동자 시민사회운동 과정 </strong></div></td>
																</tr>
																<tr> 
																	<td bgcolor="f9f8f3" class='bod_tl'>전공 1강좌</td>
																	<td class="bod_co01"><div align="center"><B>세계노동자정치운동사</B>
																    </div>
																  <p align="center"></p></td>
																	<td class="bod_co01"><div align="center"><B>세계노동운동사</B>
																    </div>
																  <p align="center">김래용(노동운동가)</p></td>
																	<td class="bod_co01"><div align="center">한국사회운동사
																    </div>
																  <p align="center">전명혁(역사학연구소)</p></td>
																</tr>
																<tr> 
																	<td bgcolor="f9f8f3" class='bod_tl'>전공 2강좌</td>
																	<td class="bod_co01"><div align="center"><B>정치운동론(일반)</B>
																	
																    </div>
																  <p align="center">성두현(해방연대)</p></td>
																	<td class="bod_co01"><div align="center"><B>전투적 노조운동의 교훈</B> 
																	<br>
																    전평, 전노협, 민주노총의 운동노선</div><br>이호룡(민주화운동기념사업회)
																	, 박성인(노동운동가), 박승호(전태일 노동연구소)</td>
																	<td class="bod_co01"><div align="center"><B>시민(사회)운동의<br> 제 유형Ⅰ</B><br>
																   인권, 평화, 이주민 운동</div><br>박래군(인권운동사랑방), 이대훈(성공회대), 박경태 (성공회대)</td>
																</tr>
																<tr> 
																	<td bgcolor="f9f8f3" class='bod_tl'>전공 3강좌</td>
																	<td class="bod_co01"><div align="center"><B>정치운동론(한국)</B></div><br>장석준(진보신당)</td>
																	<td class="bod_co01"><div align="center"><B>사회주의 노조운동론</B> <br>
																	  맑스-레닌주의 노조운동론<br>사회운동적 노조주의 비판<br>21세기 사회주의 노조운동론<br>
																    조직과 투쟁 </div><br>박승호(전태일 노동연구소)</td>
																	<td class="bod_co01"><div align="center"><B>시민(사회)운동의<br> 제 유형Ⅱ</B><br>
																    여성, 생태운동</div><br>고정갑희(한신대), 이헌석 (청년환경센터)</td>
																</tr>
																<tr> 
																	<td bgcolor="f9f8f3" class='bod_tl'>전공 4강좌<br>(공통과목)</td>
																	<td colspan="3" class="bod_co01"><div align="center"><B>사회주의 노동운동론</B> <br>
																     <br>사회주의란 무엇인가 </div><br>김승호 (전태일 노동대학)</td>
																</tr>
															</table>
														</td>													
													</tr>
													<!--3학년 2학기-->													
													<tr>
														<td class="ctit" height=5></td>
													</tr>
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct13_17.gif" width="204" height="13"></td>
													</tr>

<!--------------------------------------------------------------																										
---------------->																										
													<tr>
														<td class="bod_pd">
															<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td width="14%" height="30" bgcolor="eeede8" class='bod_tl_t'><div align="center"><strong>순 서 </strong></div></td>
																	<td width="24%" bgcolor="eeede8" class='bod_tl_t'><div align="center"><strong>노동자 정치운동 과정</strong> </div></td>
																	<td width="36%" bgcolor="eeede8" class='bod_tl_t'><div align="center"><strong>노조운동 과정 </strong></div></td>
																	<td width="26%" bgcolor="eeede8" class='bod_tl_t'><div align="center"><strong>노동자 시민사회운동 과정 </strong></div></td>
																</tr>
																<tr> 
																	<td bgcolor="f9f8f3" class='bod_tl'>전공 1강좌</td>
																	<td class="bod_co01"><div align="center"><B><B>세계노동자정치운동사Ⅱ</B></B>
																    </div>
																  <p align="center"></p></td>
																	<td class="bod_co01"><div align="center"><B><B>세계노동운동사Ⅱ</B></B>
																    </div>
																  <p align="center">김래용(노동운동가)</p></td>
																	<td class="bod_co01"><div align="center"><B><B>한국사회운동사Ⅱ</B></B>
																    </div>
																  <p align="center">조희연(성공회대)</p></td>
																</tr>
																<tr> 
																	<td bgcolor="f9f8f3" class='bod_tl'>전공 2강좌</td>
																	<td class="bod_co01"><div align="center"><B>노동자정치운동의 혁신</B>
																	
																    </div>
																  <p align="center">민노당, 진보신당,<br> 사노련, 사노준,<br> 해방연대,사회당,<br> 다함께
</p></td>
																	<td class="bod_co01"><div align="center"><B>민주노조운동의 혁신</B> 
																	<br>
																    <br>박승호(전태일 노동연구소)</td>
																	<td class="bod_co01"><div align="center"><B>시민(사회)운동의<br> 혁신과 실천 1</B><br>
																   인권, 평화, 이주민 운동</div><br>박래군(인권운동사랑방),<br> 이대훈(성공회대),<br> 박경태 (성공회대)</td>
																</tr>
																<tr> 
																	<td bgcolor="f9f8f3" class='bod_tl'>전공 3강좌</td>
																	<td class="bod_co01"><div align="center"><B>변혁적 정치운동의<br> 실천</B></div><br>베네수엘라 산디노, <br>필리핀 NDF 갈랑</td>
																	<td class="bod_co01"><div align="center"><B>변혁적 노조운동의 실천</B> <br>
																	<br>  의료연대, 운수노조, 건설노조</td>
																	<td class="bod_co01"><div align="center"><B>시민(사회)운동의<br> 혁신과 실천 2</B><br>
																    여성, 생태운동</div><br>이헌석 (청년환경센터)</td>
																</tr>
																<tr> 
																	<td bgcolor="f9f8f3" class='bod_tl'>전공 4강좌<br>(공통과목)</td>
																	<td colspan="3" class="bod_co01"><div align="center"><B>사회주의 노동운동론Ⅱ</B> <br>
																     <br>실천론(비전과 전략) </div><br>김승호 (전태일 노동대학)</td>
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
<!-- /var/www/html/junnodae/jsp/1//info/info_organi_3_2.jsp -->
<%@include file="../common/footer.jsp" %>

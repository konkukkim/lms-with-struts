<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>
<script language="javascript">
<!--
	for (var k=3; k<=4; k++)
	{
		eval('bookMenu'+k+'A = new Image();');
		eval('bookMenu'+k+'B = new Image();');
		
		eval('bookMenu'+k+'A.src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb61_0'+k+'_off.gif";');
		eval('bookMenu'+k+'B.src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb61_0'+k+'_on.gif";');
	
	}
	
	function changeDivMenuLayer(n, i) {
	    for(var j=1; j<=n; j++) {
	        $("HakInfoDiv0"+j).style.display = "none";
	    }
	    $("HakInfoDiv0"+i).style.display = "";
	}
//-->
</script>
									<!-- 내용 -->
									<table width="660" align="center">
										<tr valign="top">
											<td>
												<table width="100%" class="Tab01">
													<tr valign="top" align="left">
<td  class="Tab02" width="91"><a 
	href="<%=CONTEXTPATH%>/Main.cmd?cmd=StudentCenterShow&pMode=StudentCenter&pInfoNum2=3&pInfoNum3=1&MENUNO=0" onfocus='blur()'; 
	onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('tab_01','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab61_01_on.gif',1)"><img 
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab61_01_off.gif" name="tab_01" width="91" height="26" border="0"></a></td>
<td width="91"><img 
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab61_02_on.gif" name="tab_02" width="91" height="26" border="0"></td>
<td><a 
	href="<%=CONTEXTPATH%>/Main.cmd?cmd=StudentCenterShow&pMode=StudentCenter&pInfoNum2=3&pInfoNum3=3&MENUNO=0" onfocus='blur()'; 
	onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('tab_03','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab61_03_on.gif',1)"><img 
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab61_03_off.gif" name="tab_03" width="94" height="26" border="0"></a></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr valign="top">
											<td>
												<table width="640" align="center" onload="MM_preloadImages('<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb61_03_on.gif','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb61_04_on.gif')">
													<tr valign="top">
														<td class="ctit_pd02">
															<table width="100%" align="left">
																<tr>
<td width="134"><A 
	onmouseover="chng11('bookMenu3','bookMenu3B');chng11('bookMenu4','bookMenu4A');changeDivMenuLayer(2,1)" href="#"><IMG 
	id=bookMenu3 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb61_03_on.gif" 
	width=131 border=0 name=bookMenu3></A></td>
<td><A 
	onmouseover="chng11('bookMenu3','bookMenu3A');chng11('bookMenu4','bookMenu4B');changeDivMenuLayer(2,2)" href="#"><IMG 
	id=bookMenu4 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb61_04_off.gif" 
	width=131 border=0 name=bookMenu4></A></td>
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
												<!-- 2학년 --> 
												<table width="630" align="center">
													<tr>
														<td class="ctit_pd01">
														<div align="justify">
														아래의 교재들 중에서 일반 서점에서 구입할 수 있는 교재들은 직접 구입하시면 됩니다. 일반 서점에서 구입하기 어려우면 예스24(www.yes24.com) 또는 알라딘(www.aladdin.co.kr)과 같은 인터넷 서점을 이용하면 전국 어디서든 3∼5일 이내에 책을 받아볼 수 있습니다. 
														인터넷 서점을 이용하면 대개 정가에서 10∼20% 정도 할인되며, 구입총액이 40,000원 이상일 경우에는 우송료가 면제됩니다. 
														구입총액이 40,000원 이하일 경우에는 2000원의 우송료가 붙습니다. <br>
														일반 서점이나 인터넷 서점을 통해 직접 구입하기 어려운 분들은 대학본부로 주문하면 인터넷 서점을 통해 구입, 우송해 드리겠습니다. 이 경우 할인액에 비해 우송료가 다소 더 들 수도 있고 덜 들 수도 있습니다. 
														이런 사정을 일일이 구분하여 정확하게 금액을 산정하기에는 번거로움이 있기 때문에 대학본부로 주문할 경우 할인액이 얼마냐와 상관 없이 우송료를 포함하여 책의 정가대로 받도록 하겠습니다.(구입액수가 많을 경우에는 직접 인터넷 서점으로 주문하시면 할인혜택을 받을 수 있습니다.)<br><br>
														** 교재 대금 입금 안내 **<br>
														대학본부로 교재를 신청할 경우 주문 후에 대금을 교재비 통장으로 입금해 주십시오.<br>
														입금 통장 : 우체국 012732-02-039149 신인철 (등록금 통장과 다르므로 구분하여 입금해 주십시오.)</div></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
<DIV id="HakInfoDiv01" style="display:show;">
															<!-- 2학년 1학기 -->
															<table width="100%"  border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr>
																	<td width="61" rowspan="5" bgcolor="eeede8" class='bod_tl_t'><b>오늘의 <br>자본주의와<br>노자관계(택1)<br></b></td>
																	<td width="118" height="30" bgcolor="eeede8" class='bod_tl_t'><b>과목</b></td>
																	<td width="302" height="30" bgcolor="eeede8" class='bod_tl_t'><b>교재</b></td>
																	<td width="105" height="30" bgcolor="eeede8" class='bod_tl_t'><b>책</b></td>
																</tr>
																<tr>
																	<td width="118" bgcolor="f9f8f3" class='bod_tl'><b>독일 사민주의 <br>정치와 노동운동</b></td>
																	<td width="302" class="bod_co02"><p align="justify"><span class="ctext02"><b><참고도서></b></span><br>
																	『노동운동과 민족운동』<br>
																	(역사비평사, p33~106참조. 박호성.1994. <span class="ctext01">8,000원</span>)<br><Br>
																	『개혁을 위한 연대:독일사회민주당과 노동조합』<br>
																	(한울, 이진모. 2001. <span class="ctext01">12,000원</span>)<br><Br>
																	독일 노동조합 체계『해외 산별노조 사례연구』<br>
																	(현장에서 미래를, 정병기. 2001)<br><br>
																	독일『유럽노동운동은 끝났는가』<br>
																	(주간노동자신문(절판), 정병기역. 1994. <span class="ctext01">6,000원</span>)</p></td>
																	<td width="105" align="center" class="bod_co02"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_03_book01.gif"></td>
																</tr>
																<tr>
																	<td width="118" bgcolor="f9f8f3" class='bod_tl'><b>영국의 자본주의와 <br>노자관계 </b></td>
																	<td width="302" class="bod_co02"><p align="justify">
																	<span class="ctext02"><b><참고도서></b></span><br>
																	『알기 쉬운 정치경제학』<br>
																	(서울대학교 출판부, 김수행. 2001. <span class="ctext01">15,000원</span>)<br><br>
																	『영국노동당사』<br>
																	(나남, 고세훈. 1999. <span class="ctext01">18,000원</span>)</td>
																	<td class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_03_book02.gif"><br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_03_book03.gif"></td>
																</tr>
																<tr>
																	<td width="118" bgcolor="f9f8f3" class='bod_tl'><b>브라질 자본주의와<br> 노자관계</b></td>
																	<td width="302" class="bod_co02"><p align="justify">
																	<span class="ctext02"><b><참고도서></b></span><br>
																	브라질 노동운동의 산별노조 건설『산별노조의 과거, 현재 그리고 미래』<br>
																	(한국노동사회연구, p173~210참조. 1996. <span class="ctext01">10,000원</span>)<br><br>
																	『다른 세계는 가능하다-브라질 노동자당에서 배운다』<br>
																	(책갈피, 에미르 사데르, 켄 실버스타인. 최규엽역. 2002. <span class="ctext01">12,000원</span>)<br><br>
																	브라질 노동자당의 역사『노동사회』8월호(품절)<br>
																	(한국노동사회연구소, 신원철. 1997)<br><br>
																	브라질 노동자당의 건설과 성장『노동자 정치세력화, 진단과 모색』<br>
																	(한국노동사회연구소, 오삼교. 1999)</p></td>
																	<td width="105" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_03_book04.gif"></td>
																</tr>
																<tr>
																	<td width="118" bgcolor="f9f8f3" class='bod_tl'><b>남아공 자본주의와<br> 노자관계</b></td>
																	<td width="302" class="bod_co02"><p align="justify"><span class="ctext02"><b><참고도서></b></span><br>
																	『남아공의 변혁운동과 노동조합』
																	(현장에서 미래를, 김영수편역. <span class="ctext01">12,000원</span>)</p></td>
																	<td width="105" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_03_book05.gif"></td>
																</tr>
																<tr>
																	<td width="61" rowspan="3" bgcolor="eeede8" class='bod_tl_t'><b>역사<br>(택1)</b></td>
																	<td width="118" bgcolor="f9f8f3" class='bod_tl'><b>정치 : 오늘의 <br>국제정치 </b></td>
																	<td width="302" class="bod_co02"><p align="justify">보조교재 :『거대한 체스판』<br>
																	(삼인, 즈비그뉴 브레진스키. 김명섭옮김. 2000. <span class="ctext01">9,500원</span>)</p></td>
																	<td class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_03_book06.gif"></td>
																</tr>
																<tr>
																	<td width="118" bgcolor="f9f8f3" class='bod_tl'><b>경제 : 세계화와 <br>전지국적 자본주의</b></td>
																	<td width="302" class="bod_co02"><p align="justify">주교재 : 『반 세계화의 논리』<br>(월간 말, 윌리엄K탭. 2001. <span class="ctext01">9,600원</span>)</td>
																	<td width="105" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_03_book07.gif"></td>
																</tr>
																<tr>
																	<td width="118" bgcolor="f9f8f3" class='bod_tl'><b>사회 : 정보사회와 <br>인터넷</b></td>
																	<td width="302" class="bod_co02"><p align="justify">주교재 :『디지털이 세상을 바꾼다』<br>(문학과 지성사, 백욱인. 1998. <span class="ctext01">4,500원</span>)</p></td>
																	<td width="105" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_03_book08.gif"></td>
																</tr>
																<tr>
																	<td width="61" rowspan="3" bgcolor="eeede8" class='bod_tl_t'><b>일반 교양<br>(택1)<br></b></td>
																	<td width="118" bgcolor="f9f8f3" class='bod_tl'><b>한국 자본주의의<br> 성격</b></td>
																	<td width="302" class="bod_co02"><p align="justify">미정</p></td>
																	<td class="bod_co02" align="center"></td>
																</tr>
																<tr>
																	<td width="118" bgcolor="f9f8f3" class='bod_tl'><b>계급구조와 정치적, <br>이데올로기적 지형</b></td>
																	<td width="302" class="bod_co02"><p align="justify">미정</p></td>
																	<td width="105" class="bod_co02"></td>
																</tr>
																<tr>
																	<td width="118" bgcolor="f9f8f3" class='bod_tl'><b>민족의 분단과 통일</b></td>
																	<td width="302" class="bod_co02"> <p align="justify"><span class="ctext02"><b><주교재></b></span><br>
																	『현대 한국사회의 이해와 전망』<br>
																	(한울, 강정구. 2000. <span class="ctext01">18,000원</span>)<br><br>
																	<span class="ctext02"><b><참고도서></b></span><br>
																	『0.75평 지상에서 가장 작은 내 방 하나』<br>
																	(창, 김선명외. 2000. <span class="ctext01">8,500원</span>)<br><br>
																	『노근리 그 후:주한미군 범죄 55년 사』<br>
																	(말(품절), 오연호. 1998)<br><br>
																	『이제 미국이 대답할 차례다』<br>
																	(한겨레, 정경모. 2001. <span class="ctext01">8,000원</span>)<br><br>
																	『불량국가』<br>
																	(두레, 노암 촘스키. 장영준역. 2001년. <span class="ctext01">12,800원</span>)</p></td>
																	<td width="105" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_03_book09.gif"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_03_book10.gif"></td>
																</tr>
																<tr>
																	<td width="61" rowspan="2" bgcolor="eeede8" class='bod_tl_t'><b>종합교양<br>(택1)<br></b></td>
																	<td width="118"  bgcolor="f9f8f3" class='bod_tl'><b>철 학 Ⅲ</b></td>
																	<td width="302" class="bod_co02"><p align="justify"><span class="ctext02"><b><주교재></b></span><br>
																	『이야기로 풀어보는 20세기 사상사 』<br>
																	(천지, 이병창. 2001년. <span class="ctext01">12,000원</span>)</td>
																	<td class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_03_book11.gif"></td>
																</tr>
																<tr>
																	<td width="118" bgcolor="f9f8f3" class='bod_tl'><b>노동운동사 Ⅲ</b></td>
																	<td width="302" class="bod_co02">교재는 따로 없고 강의 텍스트를 보시면 됩니다.</td>
																	<td width="105" class="bod_co02"></td>
																</tr>
															</table>
</DIV>
<DIV id="HakInfoDiv02" style="display:none;">
															<!-- 2학년 2학기 -->
															<table width="100%"  border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td width="104" rowspan="6" bgcolor="eeede8" class='bod_tl_t'><b>현실 사회주의에 대한 비판적 성찰과 인간해방의 전망<br></b></td>
																	<td width="389" class="bod_co02"><span class="ctext03"><b>1강 : 탈근대주의 입장 </b></span><br>
																	<span class="ctext02"><b><참고도서></b></span> 반노동의 유토피아,  차문석 저, 박종철출판사</td>
																	<td align="center" class="bod_co02"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_04_book01.gif"></td>
																</tr>
																<tr> 
																	<td class="bod_co02"><span class="ctext03"><b>2강 : 생태사회주의 입장</b></span><br>
																	<span class="ctext02"><b><교재></b></span><br>
																	- 최병두 1995, 환경사회이론과 세계환경문제, 한울. 특히 ‘환경운동의 철학적 기초와 전망’에 관한 장(148-180쪽) 참조. <br>
																	- 최병두, 2001, 생태정치와 정치생태학, 그리고 맑스주의, <환경사회학연구 ECO> 창간호, 124-145쪽. <br><br>
																	<span class="ctext02"><b><참고도서> </b></span><br>
																	- 문순홍, 1992, 생태위기와 녹색의 대안, 나라사랑. 특히 85-122쪽. <br>
																	- 문순홍 편저, 1999, 생태학의 담론, 솔. 특히 제 7장, 21세기 문턱에 선 생태사회주의, 제 8장 생태맑스주의: 지속가능한 자본주의는 가능한가. <br>
																	- 데이비드 페퍼 지음, 이명우 외 옮김, 1989, 현대환경론, 한길사, 제 6장 마르크스주의 환경론의 이해. <br>
																	- 라이너 그룬트만 지음, 박만준,박준건 옮김, 1995, 마르크스주의와 생태학, 동녘 
</td>
																	<td class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_04_book02.gif"><br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_04_book03.gif"></td>
																</tr>
																<tr> 
																	<td class="bod_co02"><span class="ctext03"><b>3강 : 사회민주주의 입장</b></span><br>
																	교재는 없고 강의 교안을 보시면 됩니다.</td>
																	<td class="bod_co02"></td>
																</tr>
																<tr> 
																	<td class="bod_co02"><span class="ctext03"><b>4강 : 다중 자율주의 입장</b></span><br> 
																	<span class="ctext02"><b><참고 도서></b></span><br>
																	- 21세기 스파르타쿠스, 조정환,갈무리, 2002의 제1부 <br>
																	- 소련 국가자본주의, 토니 클리프,책갈피, 1993의 제1장과 제6장 <br>
																	- 권력으로 세상을 바꿀 수 있는가, 존 홀러웨이, 갈무리, 2002의 제2～3장 <br>
																	- 동유럽에서의 계급투쟁, 크리스 하먼, 갈무리, 1995의 서문 <br>
																	- 서유럽 사회주의의 역사, 이안 버첼,갈무리, 1996 제1부 서설 <br>
																	- 마르크스의 초기 저작: 비판과 언론, 칼 마르크스, 열음사, 332～338쪽 </td>
																	<td class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_04_book04.gif"></td>
																</tr>
																<tr> 
																	<td class="bod_co02"><span class="ctext03"><b>5강 : 구좌파 마르크스주의 입장 </b></span><br>
																	교재는 없고 강의 교안을 보시면 됩니다.</td>
																	<td class="bod_co02"></td>
																</tr>
																<tr> 
																	<td class="bod_co02"><span class="ctext03"><b>6강 : 프로레타리아 민주주의 입장</b></span><br>
																	교재는 없고 강의 교안을 보시면 됩니다.<br><br>
																	<span class="ctext02"><b><참고 교재></b></span><br>
																	1. 로자 룩셈부르크, 박영옥 옮김, 「러시아혁명」, 『러시아혁명/레닌주의냐 마르크스주의냐』, 두레, 1989. <br>
																	2. 레온 뜨로츠키, 김성훈 옮김, 『배반당한 혁명』, 갈무리, 1995. <br>
																	3. 빅또르 세르쥬, 김주한?황동하 옮김, 『러시아 혁명의 진실』, 풀무질, 1996. </td>
																	<td width="95" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_04_book05.gif"></td>
																</tr>
																<tr> 
																	<td width="104" rowspan="4" bgcolor="eeede8" class='bod_tl_t'><b>인간해방에 대한 현재의 실험들 </b></td>
																	<td class="bod_co02"><span class="ctext03"><b>1강 : 쿠바(정통 사회주의)</b></span><br>
																	교재는 없고 강의 교안을 보시면 됩니다.</p></td>
																	<td class="bod_co02" align="center"></td>
																</tr>
																<tr> 
																	<td class="bod_co02"><span class="ctext03"><b>2강 : 북한(주체형 사회주의)</b></span><br>
																	<span class="ctext02"><b><참고 교재></b></span><br>
																	『북한의 정치와 사회 I, II』김남식 외. 1995. 서울: 한길사.<br>
																	『김정일』이찬행. 2001.서울: 백산서당.<br>
																	『김일성리더십연구』이태섭. 2001. . 서울: 들녘.<br>
																	『곁에서본 김정일』정창현. 2000. .서울: 토지. <br>
																	“사회주의는 과학이다” 김정일. 1994,『로동신문』1994. 11. 4일자.</td>
																	<td width="95" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_04_book06.gif"><br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_04_book07.gif"></td>
																</tr>
																<tr> 
																	<td class="bod_co02"><span class="ctext03"><b>3강: 스웨덴(사회민주주의) </b></span><br>
																	<span class="ctext02"><b><참고도서></b></span><br>
																	- 고세훈(1999) “서유럽 사민주의의 대안과 선택” 『경제와 사회』 통권 42호, 여름 <br>
																	- 김수진(2001) 『민주주의와 계급정치: 서유럽 정치와 정치경제의 역사적 전개』 서울: 백산서당 <br>
																	- 성낙선(2001) “세계화와 스웨덴모델의 위기” 전창환?조영철 (편) 『미국식 자본주의와 사회민주적 대안』 서울: 당대 <br>
																	- 송호근(1997) 『시장과 복지정치: 사민주의 스웨덴 연구』 서울: 사회비평사 <br>
																	- 신광영(1994) “스웨덴 사회민주주의 60년” 『계간사상』 <br>
																	- 안재흥(1995) “스웨덴 모델의 형성과 노동의 정치경제” 『한국정치학회보』 29집 3호 <br>
																	- 신광영, 조돈문(1997) “스웨덴모델의 미래: 사회민주당의 계급연합 전략과 지지기반의 변화” 『산업노동연구』 제3권 2호 <br>
																	- 전창환(2001) “스웨덴 사민주의, 금융위기 그리고 유럽통화연맹” 전창환, 조영철 (편) 『미국식 자본주의와 사회민주적 대안』 서울: 당대 </td>
																	<td width="95" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_04_book08.gif"><br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_04_book09.gif"></td>
																</tr>
																<tr> 
																	<td class="bod_co02">
																	<span class="ctext03"><b>4강 : 브라질의 포르투 알레그레(참여민주주의)</b></span><br>
																	<span class="ctext02"><b><참고 자료></b></span><br>
																	레베카 아베르스, (관념에서 실천으로 - 포르투 알레그레의 참여정치),<br> <읽을꺼리> 2호<br>
																	호세 코레아 라이테라 외, (뜨거워지는 브라질), <읽을꺼리> 5호. <br>
																	* 위의 글들은 구 카피레프트모임 홈페이지(http://copyle.jinbo.net)에서 볼 수 있다.<br><br>
																	장석준, (브라질 노동자당, 무엇을 하고 있는가?), <이론과 실천> 창간준비2호. <br>
																	장석준, (라틴아메리카 좌파의 침체, 그리고 다시 그 부활), <이론과 실천> 2001년 8월호. <br>
																	* 위의 글들은 민주노동당 홈페이지(http://www.kdlp.org)의 <이론과 실천> 사이트에서 볼 수 있다.<br><br>
																	하승우 편역, (포르투 알레그레에서의 실험: 직접민주주의는 가능한가?),<br><주민자치센터 뉴스레터> 25～28호. (http://grassroot.or.kr/newsletter) 
</td>
																	<td width="95" class="bod_co02" align="center"></td>
																</tr>
																<tr> 
																	<td rowspan="5" bgcolor="eeede8" class='bod_tl_t'><b>신자유주의 세계화에 대한 당면의 대안<br></b></td>
																	<td class="bod_co02"><span class="ctext03"><b>1강 : 전지구적 차원에서, 자본의 세계화에 대한 과학적 이해</b></span><br>
																	교재는 없고 강의 교안을 보시면 됩니다.</td>
																	<td class="bod_co02" align="center"></td>
																</tr>
																<tr> 
																	<td class="bod_co02"><span class="ctext03"><b>2강 : 전지구적 차원에서, 초국적 금융자본의 세계화에 대한 정책 대안</b></span><br>
																	교재는 없고 강의 교안을 보시면 됩니다.</td>
																	<td width="95" class="bod_co02"></td>
																</tr>
																<tr> 
																	<td class="bod_co02"><span class="ctext03"><b>3강 : 전지구적 차원에서, 무장한 세계화에 대한 현황과 대응 </b></span><br>
																	<span class="ctext02"><b><참고 교재></b></span><br>
																	 미국 패권의 몰락, 이매뉴얼 월러스틴, 창비, 2004. </td>
																	<td width="95" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_04_book10.gif"></td>
																</tr>
																<tr> 
																	<td class="bod_co02"><span class="ctext03"><b>4강 : 지역적 차원에서, 민족경제들간 협력에 관한 정책대안 </b></span><br>
																	<span class="ctext02"><b><참고 교재> </b></span><br>
																	엔블록과 동아시아 경제, 김용복, 책세상,2002년</td>
																	<td width="95" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_04_book11.gif"></td>
																</tr>
																<tr> 
																	<td class="bod_co02"><span class="ctext03"><b>5강 : 일국적 차원에서, 진보적 민족경제 재구성에 관한 정책대안</b></span><br>
																	교재는 없고 강의 교안을 보시면 됩니다.</td>
																	<td width="95" class="bod_co02" align="center"></td>
																</tr>
																<tr> 
																	<td rowspan="2" bgcolor="eeede8" class='bod_tl_t'><b>종합교양(택1)<br></b></td>
																	<td class="bod_co02"><span class="ctext03"><b>철 학</b></span><br>
																	교재는 없고 강의 교안을 보시면 됩니다.</td>
																	<td class="bod_co02" align="center"></td>
																</tr>
																<tr> 
																	<td class="bod_co02"><span class="ctext03"><b>노동운동사</b></span><br>
																	교재는 없고 강의 교안을 보시면 됩니다.</td>
																	<td width="95" class="bod_co02"></td>
																</tr>
														  </table>
</DIV>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<!-- // 컨텐츠 내용 -->
									</table>
								</td>
							</tr>
						</table>


<%@include file="../common/footer.jsp" %>
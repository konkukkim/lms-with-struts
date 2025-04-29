<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>
<script language="javascript">
<!--
	for (var k=1; k<=2; k++)
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
														<td  class="Tab02" width="91"><img 
															src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab61_01_on.gif" name="tab_01" width="91" height="26" border="0"></td>
														<td width="91"><a 
															href="<%=CONTEXTPATH%>/Main.cmd?cmd=StudentCenterShow&pMode=StudentCenter&pInfoNum2=3&pInfoNum3=2&MENUNO=0" onfocus='blur()'; onMouseOut="MM_swapImgRestore()" 
															onMouseOver="MM_swapImage('tab_02','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab61_02_on.gif',1)"><img 
															src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab61_02_off.gif" name="tab_02" width="91" height="26" border="0"></a></td>
														<td><a 
															href="<%=CONTEXTPATH%>/Main.cmd?cmd=StudentCenterShow&pMode=StudentCenter&pInfoNum2=3&pInfoNum3=3&MENUNO=0" onfocus='blur()'; onMouseOut="MM_swapImgRestore()" 
															onMouseOver="MM_swapImage('tab_03','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab61_03_on.gif',1)"><img 
															src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab61_03_off.gif" name="tab_03" width="94" height="26" border="0"></a></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr valign="top">
											<td>
												<table width="640" align="center" onload="MM_preloadImages('<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb61_01_on.gif','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb61_02_on.gif')">
													<tr valign="top">
														<td class="ctit_pd02">
															<table width="100%" align="left">
																<tr>
<td width="134"><A 
	onMouseOver="chng11('bookMenu1','bookMenu1B');chng11('bookMenu2','bookMenu2A');changeDivMenuLayer(2,1)" href="#"><IMG 
	id=bookMenu1 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb61_01_on.gif" 
	width=131 border=0 name=bookMenu1></A></td>
<td><A 
	onMouseOver="chng11('bookMenu1','bookMenu1A');chng11('bookMenu2','bookMenu2B');changeDivMenuLayer(2,2)" href="#"><IMG 
	id=bookMenu2 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb61_02_off.gif" 
	width=131 border=0 name=bookMenu2></A></td>
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

												<!-- 1학년 --> 
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
														<!-- 1학년 1학기 -->
<DIV id="HakInfoDiv01" style="display:show;">
															<table width="100%"  border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr>
																	<td width="65" rowspan="4" bgcolor="eeede8" class='bod_tl_t'><b>사회과학<br>(택1)<br></b></td>
																	<td width="83" height="30" bgcolor="eeede8" class='bod_tl_t'><b>과목</b></td>
																	<td width="330" height="30" bgcolor="eeede8" class='bod_tl_t'><b>교재</b></td>
																	<td width="152" height="30" bgcolor="eeede8" class='bod_tl_t'><b>책</b></td>
																</tr>
																<tr>
																	<td width="83" bgcolor="f9f8f3" class='bod_tl'><b>정치경제학ㅣ</b></td>
																	<td width="330" class="bod_co02"><p align="justify">『자본주의 역사 바로 알기』(Man's Worldly Goods)(리오 휴버만 저, 장상환 역, 책벌레, 2000)<br>서점에서 구입할 수 있습니다.<br><br><span class="ctext01">책값 : 13,000원</span></p></td>
																	<td align="center" class="bod_co02"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_01_book01.gif"></td>
																</tr>
																<tr>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>정치학ㅣ</b></td>
																	<td class="bod_co02"><p align="justify">강의교재는 온라인 선생님과 협의 중이고, 강의 텍스트를 보시면 됩니다.</td>
																	<td class="bod_co02"></td>
																</tr>
																<tr>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>사회학ㅣ</b></td>
																	<td class="bod_co02"><p align="justify">부교재 :『자동차절망공장』(가마타 사토시 씀, 허명구 서혜영 역, 우리일터기획, 1995)<br>절판되어 서점에서 구입할 수 없습니다. 대학본부에서 마스터본을 구입할 수 있습니다.<br><br><span class="ctext01">책값 : 6,000원</span></p></td>
																	<td width="152" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_01_book02.gif"></td>
																</tr>
																<tr>
																	<td width="65" rowspan="3" bgcolor="eeede8" class='bod_tl_t'><b>역사<br>(택1)</b></td>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>한국사ㅣ</b></td>
																	<td class="bod_co02"><p align="justify">보조교재 :『현대사 인물들의 재구성 』( 고지훈, 고경일 씀, 앨피, 2005)<br>서점에서 구입할 수 있습니다.<br><br><span class="ctext01">책값 : 18,000원</span></p></td>
																	<td class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_01_book03.gif"></td>
																</tr>
																<tr>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>동아시아사ㅣ</b></td>
																	<td class="bod_co02"><p align="justify">보조교재 :『살아간다는 것』(余華 지음 / 백원담 역, 푸른숲, 1997)<br>서점에서 구입할 수 있습니다. 국민당과 공산당의 내전 시기부터 최근에 이르는 중국의 현대사를 배경으로 해서 부귀라는 주인공의 파란만장한 삶의 여정을 그린 소설입니다.<br><br><span class="ctext01">책값 : 8,500원</span></td>
																	<td width="152" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_01_book04.gif"></td>
																</tr>
																<tr>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>서양사ㅣ</b></td>
																	<td class="bod_co02"><p align="justify">참고교재 : <br>
																	1)『정복은 계속된다』(노암 촘스키 지음, 오애리 옮김, 이후, 2007) 올해 개정판이 나왔습니다.<br>
																	<span class="ctext01">책값 : 14,000원</span><br><br>
																	2)『영국 제국주의 : 1750~1970』, 사이먼 C.스미스 지음, 김종원·이태숙 옮김, 동문선, 2001<br>
																	<span class="ctext01">책값 : 16,000원</span><br><br>두 권 모두 서점에서 구입할 수 있습니다.</p></td>
																	<td width="152" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_01_book05.gif"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_01_book06.gif"></td>
																</tr>
																<tr>
																	<td rowspan="3" bgcolor="eeede8" class='bod_tl_t'><b>일반 교양<br>(택1)<br></b></td><td bgcolor="f9f8f3" class='bod_tl'><b>과학·기술ㅣ</b></td>
																	<td class="bod_co02"><p align="justify">기본교재 :『파우스트의 선택』, 박병상 저, 녹색평론사 2000년<br>서점에서 구입할 수 있습니다.<br>
																	<span class="ctext01">책값 : 7,000원</span><br><br>
																	참고교재:『바이오테크 시대』, 제레미 리프킨 저, 전영택 전병기 옮김, 민음사 1999년.<br>서점에서 구입할 수 있습니다. <br>1기 교재였는데 다소 어렵고 분량이 많습니다. 생명공학의 문제를 좀더 깊이 있게 알고자 하는 분들에게 권합니다. <br>
																	<span class="ctext01">책값 : 12,000원</span></p></td>
																	<td class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_01_book07.gif"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_01_book08.gif"></td>
																</tr>
																<tr>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>문화·예술ㅣ</b></td>
																	<td class="bod_co02"><p align="justify">강의교재는 온라인 선생님과 협의 중이고, 강의 텍스트를 보시면 됩니다.</p></td>
																	<td width="152" class="bod_co02"></td>
																</tr>
																<tr>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>인간학ㅣ</b></td>
																	<td class="bod_co02"> <p align="justify">참고교재 :『흑인, 그들은 누구인가』(장태한, 한국경제신문사, 1993)<br><br>
																	<span class="ctext01">책값 : 6,000원</span><br>절판되어 교재 선택을 선생님과 협의 중입니다.</p></td>
																	<td width="152" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_01_book09.gif"></td>
																</tr>
																<tr>
																	<td rowspan="2" bgcolor="eeede8" class='bod_tl_t'><b>종합교양<br>(택1)<br></b></td>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>철 학 ㅣ</b></td>
																	<td class="bod_co02"><p align="justify">주교재는 선생님과 협의 중입니다.<br>보조교재 :『청년맑스의 철학』(N. 로텐스트라이히 지음, 정승현 옮김, 미래사, 1985)<br>절판되어 서점에서 구입할 수 없습니다. 대학본부에서 두 종류로 제작하여 판매하고 있습니다. 하나는 원본(221쪽)을 그대로 인쇄한 것이고, 다른 하나는 포이에르바하에 관한 테제를 다루고 있는 부분(105쪽까지)만 확대하여 인쇄한 것입니다.(원본이 글자 크기가 너무 작기 때문에읽기에 불편한 분들을 위해 확대본을 제작한 것입니다.)<br><br><span class="ctext01">책값 : 두 종류 각 5,000원</span></td>
																	<td class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_01_book10.gif"></td>
																</tr>
																<tr>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>노동운동사ㅣ</b></td>
																	<td class="bod_co02">교재는 따로 없고 강의 텍스트를 보시면 됩니다.</td>
																	<td width="152" class="bod_co02"></td>
																</tr>
															</table>
</DIV>
<DIV id="HakInfoDiv02" style="display:none;">
															<!-- 1학년 2학기 -->
															<table width="100%"  border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr>
																	<td width="65" rowspan="4" bgcolor="eeede8" class='bod_tl_t'><b>사회과학<br>(택1)<br></b></td>
																	<td width="83" height="30" bgcolor="eeede8" class='bod_tl_t'><b>과목</b></td>
																	<td width="330" height="30" bgcolor="eeede8" class='bod_tl_t'><b>교재</b></td>
																	<td width="152" height="30" bgcolor="eeede8" class='bod_tl_t'><b>책</b></td>
																</tr>
																<tr>
																	<td width="83" bgcolor="f9f8f3" class='bod_tl'><b>정치경제학Ⅱ</b></td>
																	<td width="330" class="bod_co02"><p align="justify">『자본주의란 무엇인가?』<br>(잘레 지음, 배규식 옮김, 책벌레) <br><br><span class="ctext01">책값 : 6,000원</span></p></td>
																	<td align="center" class="bod_co02"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_02_book01.gif"></td>
																</tr>
																<tr>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>정치학Ⅱ</b></td>
																	<td class="bod_co02"><p align="justify">없음(강의교안 참고)</td>
																	<td class="bod_co02"></td>
																</tr>
																<tr>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>사회학Ⅱ</b></td>
																	<td class="bod_co02"><p align="justify">『소비의 사회』,<br>장 보드리야르, 문예출판사(권장도서)<br><br><span class="ctext01">책값 : 10,000원</span></p></td>
																	<td width="152" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_02_book02.gif"></td>
																</tr>
																<tr>
																	<td width="65" rowspan="3" bgcolor="eeede8" class='bod_tl_t'><b>역사<br>(택1)</b></td>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>한국사Ⅱ</b></td>
																	<td class="bod_co02"><p align="justify">미정, 추후 공지</span></p></td>
																	<td class="bod_co02" align="center"></td>
																</tr>
																<tr>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>동아시아사Ⅱ</b></td>
																	<td class="bod_co02"><p align="justify">미정, 추후 공지</span></td>
																	<td width="152" class="bod_co02" align="center"></td>
																</tr>
																<tr>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>서양사Ⅱ</b></td>
																	<td class="bod_co02"><p align="justify">『러시아 혁명, 무엇을 할 것인가』<br>(니콜라 베르트 지음, 변지현 옮김, 시공사) <br>
																	<span class="ctext01">책값 : 7,000원</span></p></td>
																	<td width="152" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_02_book03.gif"></td>
																</tr>
																<tr>
																	<td rowspan="3" bgcolor="eeede8" class='bod_tl_t'><b>일반 교양<br>(택1)<br></b></td><td bgcolor="f9f8f3" class='bod_tl'><b>과학·기술Ⅱ</b></td>
																	<td class="bod_co02"><p align="justify">『인간과 과학』<br>(이필렬 지음, 방송대출판사) <br>
																	<span class="ctext01">책값 : 4,900원</span></span></p></td>
																	<td class="bod_co02" align="center"></td>
																</tr>
																<tr>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>문화·예술Ⅱ</b></td>
																	<td class="bod_co02"><p align="justify">없음</p></td>
																	<td width="152" class="bod_co02"></td>
																</tr>
																<tr>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>인간학Ⅱ</b></td>
																	<td class="bod_co02"> <p align="justify"><참고교재><br>
																	- 장미경 편,「오늘의 페미니즘, 세계여성운동」문원 <br>
																	- 로즈마리 통(1994), 「페미니즘 사상:종합적 접근」,한신문화사 <br>
																	- 미즈 시바, 「에코 페미니즘」, 창작과 비평사 <br>
																	- 이효재(1989),「한국의 여성운동」,정우사. </p></td>
																	<td width="152" class="bod_co02" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img52_02_book04.gif"></td>
																</tr>
																<tr>
																	<td rowspan="2" bgcolor="eeede8" class='bod_tl_t'><b>종합교양<br>(택1)<br></b></td>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>철 학 Ⅱ</b></td>
																	<td class="bod_co02"><p align="justify"> 추후 공지(강의교안 참고)</span></td>
																	<td class="bod_co02" align="center"></td>
																</tr>
																<tr>
																	<td bgcolor="f9f8f3" class='bod_tl'><b>노동운동사Ⅱ</b></td>
																	<td class="bod_co02">없음(강의교안 참고)</td>
																	<td width="152" class="bod_co02"></td>
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
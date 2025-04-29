<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>
<%
	String	pInfoNum2	=	StringUtil.nvl(request.getParameter("pInfoNum2"));
%>
									<table width="660" align="center">
										<tr>
											<td>
												<table width="100%" class="Tab01">
													<tr valign="top">
<td width="91" class="Tab02"><a 
	href="<%=CONTEXTPATH%>/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=5&pInfoNum2=1" onfocus='blur()'; onMouseOut="MM_swapImgRestore()" 
	onMouseOver="MM_swapImage('tab_01','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab15_01_on.gif',1)"><img 
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab15_01_off.gif" name="tab_01" width="91" height="26" border="0"></a></td>
<td width="91" ><img 
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab15_02_on.gif" name="tab02" width="91" height="26" border="0"></td>
<td width="91" ><a 
	href="<%=CONTEXTPATH%>/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=5&pInfoNum2=3" onfocus='blur()'; onMouseOut="MM_swapImgRestore()" 
	onMouseOver="MM_swapImage('tab_03','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab15_03_on.gif',1)"><img 
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab15_03_off.gif" name="tab_03" width="91" height="26" border="0"></a></td>
<td><a 
	href="<%=CONTEXTPATH%>/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=5&pInfoNum2=4" onfocus='blur()'; onMouseOut="MM_swapImgRestore()" 
	onMouseOver="MM_swapImage('tab_04','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab15_04_on.gif',1)"><img 
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab15_04_off.gif" name="tab_04" width="94" height="26" border="0"></a></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr valign="top">
											<td>
												<table width="640" align="center">
													<tr>
														<td align="right" style="padding:0 10 10 0"><a href="http://www.trainingclu.org" target="_target"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img15_02.gif"></a></td>
													</tr>
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct15_02.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
														* 사이버 노동대학 마음수련원은 2003년 7월부터 그간의 교육활동의 연장선에서 지식공부와 더불어, 살아 움직이며 실천하는 참 노동자로서 스스로를 성찰하고 변혁하는 마음공부를 강화키로 하고, 그 터전으로 마음수련원을 준비해 왔습니다.  <br>
														* 지난 2003년 11월 15일, 백두대간 기슭에 위치한 충북 영동군 매곡면 소재 (구)천덕초등학교 시설을 
														수련원 터로 임대하였고 이듬해인 2004년 약간의 보수 공사를 거쳐 세계 노동자들의 단결과 투쟁의 날인 5월 1일, 마음수련원을 개원하였습니다.<br>
														* 마음수련원은 그 동안 사이버 노동대학의 설립과 운영 및 유지와 발전에 함께해 왔던 추진위원, 교수진, 학생 등 대학 관계자들의 뜻을 
														모아 개원, 노동대학 모든 관계자들의 힘과 지혜를 모아 운영해 나가고 있습니다. <br>
														* 마음수련원에서 정규과정 학생들의 마음수련 프로그램을 실시하고 있습니다. 마음수련은 사적 이기주의적 개인으로부터 사회적·공동체적 
														개인으로 자기를 혁신하는 훈련입니다. 현재 1학년 초급과정(1박2일), 2학년 중급과정(2박3일), 3학년 고급과정(3박4일)을 실행하고 있습니다. <br>
														* 마음수련원은 노조, 정당, 단체, 모임 및 가족 단위로 누구나 백두대간의 대 자연 속에서 교육, 연수, 문화활동, 체육활동, 놀이 및 휴식을 취할 
														수 있는 열린 터전으로 이용하는 사람들이 주인된 마음으로 사용하고, 다음에 사용할 사람을 배려하여 치우고, 닦고, 손질함으로써 이용하는 사람들 모두가 함께 가꾸어 나가는 공용의 공간으로 운영되고 있습니다. 
														</td>
													</tr>
												</table>
												<table width="640" align="center">
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct15_03.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td style="padding:0 0 3 15"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><b>생활시설</b></td>
													</tr>
													<tr valign="top">
														<td class="bod_pd">
														<font class="ctext02"><b>숙실 : </b></font>전기온돌이 설비된 세 개의 숙실로 나눠져 있고 각 실별로 기준 인원에 맞춰 침구가 마련되어 있습니다.<br>
														<font class="ctext02"><b>샤워실/화장실 : </b></font>남녀 각 샤워기, 세면기, 화장실 3개씩. <br>
														<font class="ctext02"><b>식당/주방/휴게실 : </b></font>식당에는 40명 기준으로 식사와 회식을 할 수 있는 식탁과 의자가 준비되어 있습니다. <br>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;주방에는 50인용 식기류와 기본 주방설비가 되어 있고 휴게실은 차를 마시며 독서 또는 <br>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;담소를 나눌 수 있게 5인용 소파와 잡지대가 마련되어 있습니다.
														</td>
													</tr>
													<tr>
														<td style="padding:0 0 3 15"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon06.gif" align="absmiddle"><b>교육 및 수련시설</b></td>
													</tr>
													<tr valign="top">
														<td class="bod_pd">
														<font class="ctext02"><b>교육실 : </b></font>40여 명이 교육과 세미나를 할 수 있고 인터넷을 사용할 수 있는 컴퓨터와 DVD 영상시설이 갖추어져<br>
														 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;있습니다.<br>
														<font class="ctext02"><b>강의실 : </b></font>50 ~ 100여 명이 함께 교육과 각종 문화활동을 할 수 있고 책상과 의자는 없고 바닥에 앉아 진행할 수 <br>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;있도록 바닥깔개가 갖추어져 있습니다. <br>
														<font class="ctext02"><b>운동장 : </b></font>축구, 족구 등 각종 체육행사와 집단 놀이를 할 수 있습니다.</td>
													</tr>
													<tr>
														<td class="bod_pd">
															<table width="100%"  border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
																<tr> 
																	<td width="20%" bgcolor="eeede8" class='bod_tl_t'> 주소</td>
																	<td width="80%" class="bod_co02">충청북도 영동군 매곡면 공수리 463-2 구)천덕초등학교</td>
																</tr>
																<tr> 
																	<td width="20%" bgcolor="eeede8" class='bod_tl_t'> 홈페이지</td>
																	<td width="80%" class="bod_co02"> http://www.trainingclu.org</td>
																</tr>
																<tr> 
																	<td width="20%" bgcolor="eeede8" class='bod_tl_t'> 전화번호</td>
																	<td width="80%" class="bod_co02">043-743-7418</td>
																</tr>
																<tr> 
																	<td width="20%" bgcolor="eeede8" class='bod_tl_t'> 이메일 </td>
																	<td width="80%" class="bod_co02"><a href="mailto:maum@junnodae.org"><u>maum@junnodae.org</u></a></td>
																</tr>
															</table>
														</td>
													</tr>
													<tr> 
														<td class="ctit_pd01"><font class="ctext02"><b>* 찾아 오시는 길 :</b></font> 자가용 이용시 황간IC 혹은 김천IC를 이용하시면 됩니다. 대중교통 이용 시에는 철도의 경우, 경부선 영동역, 황간역, 김천역등을 이용하시거나 버스의 경우 김천, 영동 터미널로 오시면 됩니다. (자세한 안내는 홈페이지를 참조하십시오)  </td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>

<%@include file="../common/footer.jsp" %>

<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>
<script language="javascript">
<!--
	for (var k=1; k<=7; k++)
	{
		eval('year0'+k+'A = new Image();');
		eval('year0'+k+'B = new Image();');
		
		eval('year0'+k+'A.src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_year200'+k+'_off.gif";');
		eval('year0'+k+'B.src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_year200'+k+'_on.gif";');
	
	}
	
	function changeDivMenuLayer(n, i) {
	    for(var j=1; j<=n; j++) {
	        $("HakInfoDiv0"+j).style.display = "none";
	    }
	    $("HakInfoDiv0"+i).style.display = "";
	}
	
	function getCookie(name)
	{ 
		var Found = false
		var start, end 
		var i = 0 

		// cookie 문자열 전체를 검색 
		while(i <= document.cookie.length)
		{ 
			start = i 
			end = start + name.length

			// name과 동일한 문자가 있다면
			if(document.cookie.substring(start, end) == name)
			{ 
				Found = true
				break
			}
			i++
		} 

		// name 문자열을 cookie에서 찾았다면 
		if(Found == true)
		{ 
			start = end + 1 
			end = document.cookie.indexOf(";", start) 
			// 마지막 부분이라는 것을 의미
			if(end < start) 
			end = document.cookie.length 
			// name에 해당하는 value값을 추출하여 리턴한다. 
			return document.cookie.substring(start, end) 
		} 
		// 찾지 못했다면 
		return "" 
	} 
	
	function uccShow() {
        var url = "/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=2&pInfoNum2=3";
        var name = "";
        var features = "width=375,height=700,scrollbars=yes,top=0,left=100";
        if ( getCookie("event") != "noshow" )
		{
        	var popupWin = window.open(url, name, features);
        	popupWin.focus();
        }
        //centerSubWindow(popupWin, 375, 700);
	}
//-->
</script>
						<!-- 내용 시작 -->
						<table width="670" height="100%" align="center">
							<tr valign="top">
								<td class="sub_contentform">
									<table width="660" align="center">
										<tr valign="top">
											<td>
												<table width="100%" class="Tab01">
													<tr valign="top">
<td width="91" class="Tab02"><a 
	href="<%=CONTEXTPATH%>/Main.cmd?cmd=infoShow&pMode=Info&pInfoNum=2&pInfoNum2=1" onfocus='blur()'; onMouseOut="MM_swapImgRestore()" 
	onMouseOver="MM_swapImage('tab_01','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab12_01_on.gif',1)"><img 
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab12_01_off.gif" name="tab_01" width="91" height="26" border="0"></a></td>
<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab12_02_on.gif" name="tab02" width="94" height="26" border="0"></td>
													</tr>
												</table>
											</td>
										</tr>
										<!-- 컨텐츠 내용 -->
										<tr valign="top">
											<td>
												<!-- 연혁시작 --> 
												<table width="630" align="center">
													<!-- 년도별 연혁 -->
													<tr>
														<td style="padding:0 0 20 0">
															<table width="100%" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_bar_bg.gif">
																<tr>
																	<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_bar_top.gif"></td>
																</tr>
																<tr>
																	<td height="45">
																		<table width="566" height="30" align="center" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_year_bg.gif">
<tr>
	<td align="left" width="4" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_year_left.gif"></td>
	<td align="left"><A 
		onMouseOver="chng11('year01','year01B');chng11('year02','year02A');chng11('year03','year03A');chng11('year04','year04A');
		chng11('year05','year05A');chng11('year06','year06A');chng11('year07','year07A');changeDivMenuLayer(7,1)" href="#"><img 
		src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_year2001_on.gif" name="year01" 
		width="74" height="30" border="0"></a></td>
	<td align="left"><A 
		onMouseOver="chng11('year01','year01A');chng11('year02','year02B');chng11('year03','year03A');chng11('year04','year04A');
		chng11('year05','year05A');chng11('year06','year06A');chng11('year07','year07A');changeDivMenuLayer(7,2)" href="#"><img 
		src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_year2002_off.gif" name="year02" 
		width="108" height="30" border="0"></a></td>
	<td align="left"><A 
		onMouseOver="chng11('year01','year01A');chng11('year02','year02A');chng11('year03','year03B');chng11('year04','year04A');
		chng11('year05','year05A');chng11('year06','year06A');chng11('year07','year07A');changeDivMenuLayer(7,3)" href="#"><img 
		src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_year2003_off.gif" name="year03" 
		width="74" height="30" border="0"></a></td>
	<td align="left"><A 
		onMouseOver="chng11('year01','year01A');chng11('year02','year02A');chng11('year03','year03A');chng11('year04','year04B');
		chng11('year05','year05A');chng11('year06','year06A');chng11('year07','year07A');changeDivMenuLayer(7,4)" href="#"><img 
		src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_year2004_off.gif" name="year04" 
		width="74" height="30" border="0"></a></td>
	<td align="left"><A 
		onMouseOver="chng11('year01','year01A');chng11('year02','year02A');chng11('year03','year03A');chng11('year04','year04A');
		chng11('year05','year05B');chng11('year06','year06A');chng11('year07','year07A');changeDivMenuLayer(7,5)" href="#"><img 
		src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_year2005_off.gif" name="year05" 
		width="74" height="30" border="0"></a></td>
	<td align="left"><A 
		onMouseOver="chng11('year01','year01A');chng11('year02','year02A');chng11('year03','year03A');chng11('year04','year04A');
		chng11('year05','year05A');chng11('year06','year06B');chng11('year07','year07A');changeDivMenuLayer(7,6)" href="#"><img 
		src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_year2006_off.gif" name="year06" 
		width="74" height="30" border="0"></a></td>
	<td align="left"><A 
		onMouseOver="chng11('year01','year01A');chng11('year02','year02A');chng11('year03','year03A');chng11('year04','year04A');
		chng11('year05','year05A');chng11('year06','year06A');chng11('year07','year07B');changeDivMenuLayer(7,7)" href="#"><img 
		src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_year2007_off.gif" name="year07" 
		width="68" height="30" border="0"></a></td>
	<td align="right" width="4" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_year_right.gif"></td>
</tr>
																		</table>
																	</td>
																</tr>
																<tr>
																	<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_bar_bottom.gif"></td>
																</tr>
															</table>
														</td>
													</tr>
													<!-- // 년도별 연혁 -->
													<!-- 상세내용 -->
													<tr>
														<td>
<DIV id="HakInfoDiv01" style="display:show;">
															<!-- 2000년 -->
															<table cellspacing=0 cellpadding=0 width="630" border=0 align="center">
																<tr>
																	<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 20px; padding-top: 0px"></td>
																</tr>
																<tr>
																	<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 5px">
																		<table class=krfont cellspacing=0 cellpadding=0 width=600 border=0 align="center">
																			<tr valign=top>
																				<!-- 년도, 이미지 -->
																				<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 0px" width=133>
																					<table cellspacing=0 cellpadding=0 width=133 border=0>
																						<tr>
																							<td width=133 height=29><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/year2000.gif" width="79" height="28"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#6c96d7 height=1><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#f5f5f5 height=2><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 15px"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/photo2000_01.gif"></td>
																						</tr>
																					</table>
																				</td>
																				<!-- 년도별 상세내용 -->
																				<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 0px" width=467>
																					<table cellspacing=0 cellpadding=0 width=467 border=0>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 20px; padding-bottom: 0px; padding-top: 0px" width=467 height=29><font color=#5581c6><b>사이버노동대학의 연혁을 소개합니다.</b></font></td>
																						</tr>
																						<tr>
																							<td bgcolor=#d5d3d3 height=1><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#f5f5f5 height=2><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 20px; padding-bottom: 20px; padding-top: 10px" valign=top height=150>
																								<table cellspacing=0 cellpadding=0 width=447 border=0>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 1월, 김승호 설립 발기인 대표(전태일을 따르는 민주노조운동연구소 이사장, 전 전노협 지도위원)의 제안으로 사이버노동대학(가칭)의 설립을 모색하기 시작.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 2000년 중 설립을 목표로 본격적으로 추진, 6월 21일 언론을 통해 공개적으로 추진위원 모집을 광고. </td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 7월부터 ‘사이버 노동대학 설립추진위원회’의 구성을 공식적으로 추진, 진보적 지식인 100여 명과 노동운동 활동가 170여 명 등 270여 명으로 ‘사이버 노동대학 설립추진위원회’를 구성.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 서울을 비롯하여 경기남부, 구미, 대구, 제주, 부산, 강원 등 각 지역에서 지역추진 모임을 조직. 그 밖에도 울산, 대전, 경주, 포항, 경북, 경남, 인천, 광주?전남, 충청 등 전 지역에 걸쳐 설명회를 개최하거나 추진위원 모임을 조직. </td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 온라인 상으로 추진위원회 임시 사이트를 개통, 설립 취지와 목적 및 교육방향 등을 게시하고 추진위원 전용 게시판을 통해 추진위원들의 의견 개진과 토론 진행.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 기초교양과정의 강의 컨텐츠를 준비하기 위해 기초교양과정 교수단 수련회를 5차례 개최.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 제1기 학생모집을 11월 6일부터 시작.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 11월 16일부터 사이버 노동대학 홈페이지 개통</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 11월 25일 김수행 총장 및 교수진 취임.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 11월 26일 대학본부 사무실 입주(서울역 앞).</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 11월 27일 전태일 평전 읽기 시범강좌 시작, 12월 첫째 주부터 제1기 1학기 정규강좌 시작.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 개교 기념식 &nbsp;<a href="javascript:uccShow()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/btn_cyber_link.gif" align="absmiddle"></a></td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																								</table>
																							</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
</DIV>
<DIV id="HakInfoDiv02" style="display:none;">
															<!-- 2001~2002년 -->
															<table cellspacing=0 cellpadding=0 width="630" border=0 align="center">
																<tr>
																	<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 20px; padding-top: 0px"></td>
																</tr>
																<tr>
																	<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 5px">
																		<table class=krfont cellspacing=0 cellpadding=0 width=600 border=0 align="center">
																			<tr valign=top>
																				<!-- 년도, 이미지 -->
																				<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 0px" width=133>
																					<table cellspacing=0 cellpadding=0 width=133 border=0>
																						<tr>
																							<td width=133 height=29><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/year2001.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#6c96d7 height=1><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#f5f5f5 height=2><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 15px"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/photo2001_01.gif"><br></td>
																						</tr>
																					</table>
																				</td>
																				<!-- 년도별 상세내용 -->
																				<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 0px" width=467>
																					<table cellspacing=0 cellpadding=0 width=467 border=0>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 20px; padding-bottom: 0px; padding-top: 0px" width=467 height=29><font color=#5581c6><b>사이버노동대학의 연혁을 소개합니다.</b></font></td>
																						</tr>
																						<tr>
																							<td bgcolor=#d5d3d3 height=1><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#f5f5f5 height=2><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 20px; padding-bottom: 20px; padding-top: 10px" valign=top height=150>
																								<table cellspacing=0 cellpadding=0 width=447 border=0>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 1학년 기초교양 과정 1, 2학기 온라인 강의 개설 및 지역별 오프라인 강의 실시, 24개 지역학습관 및 학습실 설치, ‘이채언 교수와 함께 하는 자본론 읽기’ 특강 개설</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 2학년 노동운동 기본이론 과정 개설. </td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 2, 3기 신입생 모집 &nbsp;<a href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/intro/3rd.wmv"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/btn_cyber_link.gif" align="absmiddle"></a></td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																								</table>
																							</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
</DIV>
<DIV id="HakInfoDiv03" style="display:none;">
															<!-- 2003년 -->
															<table cellspacing=0 cellpadding=0 width="630" border=0 align="center">
																<tr>
																	<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 20px; padding-top: 0px"></td>
																</tr>
																<tr>
																	<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 5px">
																		<table class=krfont cellspacing=0 cellpadding=0 width=600 border=0 align="center">
																			<tr valign=top>
																				<!-- 년도, 이미지 -->
																				<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 0px" width=133>
																					<table cellspacing=0 cellpadding=0 width=133 border=0>
																						<tr>
																							<td width=133 height=29><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/year2003.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#6c96d7 height=1><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#f5f5f5 height=2><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 15px"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/photo2003_01.gif"><br></td>
																						</tr>
																					</table>
																				</td>
																				<!-- 년도별 상세내용 -->
																				<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 0px" width=467>
																					<table cellspacing=0 cellpadding=0 width=467 border=0>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 20px; padding-bottom: 0px; padding-top: 0px" width=467 height=29><font color=#5581c6><b>사이버노동대학의 연혁을 소개합니다.</b></font></td>
																						</tr>
																						<tr>
																							<td bgcolor=#d5d3d3 height=1><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#f5f5f5 height=2><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 20px; padding-bottom: 20px; padding-top: 10px" valign=top height=150>
																								<table cellspacing=0 cellpadding=0 width=447 border=0>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">-  3학년 노동운동 기본이론 과정 개설</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 2003년 9월부터 설립 추진위원회 정상화와 확대, 법인 전환을 위해 태스크포스 팀을 구성.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 학점은행제 실시와 오프라인 연수원(현 마음수련원) 마련 등을 추진하기 위해 조사와 현지답사 등을 진행.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 10월 3학년 전체 수련회 진행</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 11월 마음수련원 임대 계약 체결.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 11월 4기 신입생 모집 &nbsp;<a href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/intro/4th2.wmv"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/btn_cyber_link.gif" align="absmiddle"></a></td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 12월 1회 졸업식 진행 &nbsp;<a href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/admin/hangjung/graduate/1220-1.wmv"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/btn_cyber_link.gif" align="absmiddle"></a></td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																								</table>
																							</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
</DIV>
<DIV id="HakInfoDiv04" style="display:none;">
															<!-- 2004년 -->
															<table cellspacing=0 cellpadding=0 width="630" border=0 align="center">
																<tr>
																	<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 20px; padding-top: 0px"></td>
																</tr>
																<tr>
																	<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 5px">
																		<table class=krfont cellspacing=0 cellpadding=0 width=600 border=0 align="center">
																			<tr valign=top>
																				<!-- 년도, 이미지 -->
																				<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 0px" width=133>
																					<table cellspacing=0 cellpadding=0 width=133 border=0>
																						<tr>
																							<td width=133 height=29><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/year2004.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#6c96d7 height=1><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#f5f5f5 height=2><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 15px"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/photo2004_01.gif"><br></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 15px"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/photo2004_02.gif"><br></td>
																						</tr>
																					</table>
																				</td>
																				<!-- 년도별 상세내용 -->
																				<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 0px" width=467>
																					<table cellspacing=0 cellpadding=0 width=467 border=0>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 20px; padding-bottom: 0px; padding-top: 0px" width=467 height=29><font color=#5581c6><b>사이버노동대학의 연혁을 소개합니다.</b></font></td>
																						</tr>
																						<tr>
																							<td bgcolor=#d5d3d3 height=1><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#f5f5f5 height=2><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 20px; padding-bottom: 20px; padding-top: 10px" valign=top height=150>
																								<table cellspacing=0 cellpadding=0 width=447 border=0>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">-  11월, 4년간 사이버 노동대학 총장을 맡아 온 김수행 교수가 사의를 표명하고, 2대 총장으로 김상곤 한신대 교수가 추천되었으며, 추진위원들의 동의를 얻어 김상곤 교수를 2대 총장으로 추대.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 5월 1일 마음수련원 개원</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 3학년 노동운동 전문실무 과정 전면 보완</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 10월 11월 5기 신입생 모집 &nbsp;<!--a href=""><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/btn_cyber_link.gif" align="absmiddle"--></td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 11월 12월 2회 졸업식 진행 &nbsp;<a href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/admin/hangjung/graduate/041228_2th_vod.wmv"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/btn_cyber_link.gif" align="absmiddle"></a></td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																								</table>
																							</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
</DIV>
<DIV id="HakInfoDiv05" style="display:none;">
															<!-- 2005년 -->
															<table cellspacing=0 cellpadding=0 width="630" border=0 align="center">
																<tr>
																	<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 20px; padding-top: 0px"></td>
																</tr>
																<tr>
																	<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 5px">
																		<table class=krfont cellspacing=0 cellpadding=0 width=600 border=0 align="center">
																			<tr valign=top>
																				<!-- 년도, 이미지 -->
																				<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 0px" width=133>
																					<table cellspacing=0 cellpadding=0 width=133 border=0>
																						<tr>
																							<td width=133 height=29><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/year2005.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#6c96d7 height=1><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#f5f5f5 height=2><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 15px"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/photo2005_01.gif"><br></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 15px"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/photo2005_02.gif"><br></td>
																						</tr>
																					</table>
																				</td>
																				<!-- 년도별 상세내용 -->
																				<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 0px" width=467>
																					<table cellspacing=0 cellpadding=0 width=467 border=0>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 20px; padding-bottom: 0px; padding-top: 0px" width=467 height=29><font color=#5581c6><b>사이버노동대학의 연혁을 소개합니다.</b></font></td>
																						</tr>
																						<tr>
																							<td bgcolor=#d5d3d3 height=1><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#f5f5f5 height=2><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 20px; padding-bottom: 20px; padding-top: 10px" valign=top height=150>
																								<table cellspacing=0 cellpadding=0 width=447 border=0>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">-  1월, 김상곤 교수 2대 총장 취임.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 하반기부터 정규대학 인가 문제를 검토, 11월 6기 신입생 모집을 시작하면서, 법인으로 전환하여 전문학사학위 과정의 원격대학 인가를 본격적으로 추진키로 하고 학위인정 시범강좌 준비.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 1학년 기초교양 과정 온라인 강의 전면 업데이트 및 업그레이드</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 마음수련 초급과정 개설</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 원격대학 설치와 비영리 재단법인 설립을 위해 교육인적자원부와 기존 사이버대학 등을 방문조사하고, 교사(校舍) 확보를 위해 마음수련원 매입을 추진. </td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 5월 마음수련원 1주년 개원잔치 진행 </td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 11월 6기 신입생 모집 &nbsp;<a href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/lecturedir2/6th.wmv"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/btn_cyber_link.gif" align="absmiddle"></a></td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 12월 3회 졸업식 진행 &nbsp;<!--a href="http://"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/btn_cyber_link.gif" align="absmiddle"></a--></td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																								</table>
																							</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
</DIV>
<DIV id="HakInfoDiv06" style="display:none;">
															<!-- 2006년 -->
															<table cellspacing=0 cellpadding=0 width="630" border=0 align="center">
																<tr>
																	<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 20px; padding-top: 0px"></td>
																</tr>
																<tr>
																	<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 5px">
																		<table class=krfont cellspacing=0 cellpadding=0 width=600 border=0 align="center">
																			<tr valign=top>
																				<!-- 년도, 이미지 -->
																				<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 0px" width=133>
																					<table cellspacing=0 cellpadding=0 width=133 border=0>
																						<tr>
																							<td width=133 height=29><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/year2006.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#6c96d7 height=1><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#f5f5f5 height=2><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 15px"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/photo2006_01.gif"><br></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 15px"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/photo2006_02.gif"><br></td>
																						</tr>
																					</table>
																				</td>
																				<!-- 년도별 상세내용 -->
																				<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 0px" width=467>
																					<table cellspacing=0 cellpadding=0 width=467 border=0>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 20px; padding-bottom: 0px; padding-top: 0px" width=467 height=29><font color=#5581c6><b>사이버노동대학의 연혁을 소개합니다.</b></font></td>
																						</tr>
																						<tr>
																							<td bgcolor=#d5d3d3 height=1><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#f5f5f5 height=2><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 20px; padding-bottom: 20px; padding-top: 10px" valign=top height=150>
																								<table cellspacing=0 cellpadding=0 width=447 border=0>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">-  6기 신입생부터 학위인정 시범강좌 진행.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 3월 25일, 설립추진위 총회 개최. 설립추진위 회칙 제정 및 원격대학 설치를 위한 재단법인 설립을 결의하고 35명의 운영위원 선임. 1차 설립추진위 운영위 진행</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 3월 30일, 교육인적자원부에 원격대학 설치계획서 제출, 6월 30일 승인하지 않음을 통보받음(교육인적자원부는 7월 3일자 보도자료를 통해 2007학년도 개교를 목표로 원격대학 설치계획서를 제출한 9개 기관에 대한 심사 결과, ‘신규 설치인가 대학 없음’을 밝히고 7월 11일자 보도자료를 통해 ‘원격대학 제도개선 추진계획’을 발표, 원격대학 근거법률을 평생교육법에서 고등교육법으로 변경하겠다고 발표함)</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 4월부터 부설 문화교육원 개원 준비</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 마음수련 중급과정 개설</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 5월 마음수련원 1주년 개원잔치 진행 </td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 7월 2차 설립추진위 운영위 개최.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 8월 정기 대학운영위원회 개최, 2006년 2학기 사업계획 심의</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 9월 마음수련원 주민잔치 진행.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 11월 정기 대학운영위 개최, 2007년 1학기 사업계획 심의</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 11월 3차 설립추진위 운영위 개최, 추진위원 확대 및 기금 모금 결의, 시설·설비 교체 및 문화교육원 개원 결의 </td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 11월 7기 신입생 모집 &nbsp;<a href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/lecturedir2/movie/cyber2007-1.wmv"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/btn_cyber_link.gif" align="absmiddle"></a></td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 12월 4회 졸업식 진행 &nbsp;<a href="<%=CONTEXTPATH%>/data/migrationData/intro_movie/lecturedir2/movie/gra2006-1.wmv"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/btn_cyber_link.gif" align="absmiddle"></a></td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																								</table>
																							</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
</DIV>
<DIV id="HakInfoDiv07" style="display:none;">
															<!-- 2007년 -->
															<table cellspacing=0 cellpadding=0 width="630" border=0 align="center">
																<tr>
																	<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 20px; padding-top: 0px"></td>
																</tr>
																<tr>
																	<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 5px">
																		<table class=krfont cellspacing=0 cellpadding=0 width=600 border=0 align="center">
																			<tr valign=top>
																				<!-- 년도, 이미지 -->
																				<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 0px" width=133>
																					<table cellspacing=0 cellpadding=0 width=133 border=0>
																						<tr>
																							<td width=133 height=29><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/year2007.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#6c96d7 height=1><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#f5f5f5 height=2><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 15px"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/photo2007_02.gif"><br></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 15px"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/photo2007_01.gif"><br></td>
																						</tr>
																					</table>
																				</td>
																				<!-- 년도별 상세내용 -->
																				<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; padding-top: 0px" width=467>
																					<table cellspacing=0 cellpadding=0 width=467 border=0>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 20px; padding-bottom: 0px; padding-top: 0px" width=467 height=29><font color=#5581c6><b>사이버노동대학의 연혁을 소개합니다.</b></font></td>
																						</tr>
																						<tr>
																							<td bgcolor=#d5d3d3 height=1><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td bgcolor=#f5f5f5 height=2><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/blank.gif"></td>
																						</tr>
																						<tr>
																							<td style="padding-right: 0px; padding-left: 20px; padding-bottom: 20px; padding-top: 10px" valign=top height=150>
																								<table cellspacing=0 cellpadding=0 width=447 border=0>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 부설 문화교육원 개원, 수강생 모집, 3월 5일 개강</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 1월부터 시설·설비 교체 실무 준비</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 설립·발전 추진위원 확대 및 기금 모금 진행.</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 4월 e-stream presto4 강의 저작툴 도입, 정규과정 1, 2학년 온라인 강의 업그레이드</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 6월 정기 대학운영위 개최, 2007년 2학기 사업계획 심의</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 8월 부설 문화교육원 강화도 여름캠프 진행</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 8월 홈페이지 전면 개편, (주)메디오피아테크와 계약 체결 </td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 9월 서버 교체 </td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 11월 7기 신입생 모집</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 12월 정기 대학운영위 개최, 2008년 1학기 사업계획 심의</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 12월 새로운 홈페이지 개통</td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 12월 5회 졸업식 진행 &nbsp;<!--img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/btn_cyber_link.gif" align="absmiddle"--></td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 8 기 신입생 모집 동영상 &nbsp;<a href="<%=CONTEXTPATH%>/LectureDir2/movie/8th_int.wmv" target=hiddenFrame><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/btn_cyber_link.gif" align="absmiddle"></a></td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 5 회 졸업식 1부 &nbsp;<a href="<%=CONTEXTPATH%>/data/files/data1/1/bbs/17/200712/7_master_1227172455374.wmv" target=hiddenFrame><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/btn_cyber_link.gif" align="absmiddle"></a></td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 5 회 졸업식 2부 &nbsp;<a href="<%=CONTEXTPATH%>/data/files/data1/1/bbs/17/200712/8_master_1228103314737.wmv" target=hiddenFrame><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/btn_cyber_link.gif" align="absmiddle"></a></td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																									<tr>
																										<td style="padding-right: 0px; padding-left: 0px; padding-bottom: 5px; padding-top: 5px">- 5 회 졸업식 3부 &nbsp;<a href="<%=CONTEXTPATH%>/data/files/data1/1/bbs/17/200712/9_master_1228113915736.wmv" target=hiddenFrame><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/btn_cyber_link.gif" align="absmiddle"></a></td>
																									</tr>
																									<tr>
																										<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/history_lien.gif"></td>
																									</tr>
																								</table>
																							</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
</div>
															
															
															
														</td>
													</tr>
													<!-- // 상세내용 -->
												</table>
												<!-- // 연혁끝 --> 
											</td>
										</tr>
										<!-- // 컨텐츠 내용 -->
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

<%@include file="../common/footer.jsp" %>
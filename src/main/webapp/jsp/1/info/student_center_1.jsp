<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>
<script language="javascript">
<!--
	for (var k=1; k<=8; k++)
	{
		eval('introMenu'+k+'A = new Image();');
		eval('introMenu'+k+'B = new Image();');
		
		eval('introMenu'+k+'A.src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb52_0'+k+'_off.gif";');
		eval('introMenu'+k+'B.src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb52_0'+k+'_on.gif";');
	
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
									<!-- 대학생활 개요 -->
									<table width="660" align="center">
										<tr>
											<td style="padding:0 10 30 0" align="center">
											<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/img21_03.gif">
											</td>
										</tr>
										<tr valign="top">
											<td style="padding:0 0 30 0" align="center">
												<table width="660" align="center">
													<tr valign="top">
														<td>
															<table width="100%" align="left">
<tr>
	<td width="159"><A onClick="chng11('introMenu1','introMenu1B');chng11('introMenu2','introMenu2A');chng11('introMenu3','introMenu3A');
		chng11('introMenu4','introMenu4A');chng11('introMenu5','introMenu5A');chng11('introMenu6','introMenu6A');chng11('introMenu7','introMenu7A');
		chng11('introMenu8','introMenu8A');changeDivMenuLayer(8,1)" href="#"><IMG 
		id=introMenu1 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb52_01_on.gif" width=159 border=0 
		name=introMenu1></A></td>
	<td width="159"><A onClick="chng11('introMenu1','introMenu1A');chng11('introMenu2','introMenu2B');chng11('introMenu3','introMenu3A');
		chng11('introMenu4','introMenu4A');chng11('introMenu5','introMenu5A');chng11('introMenu6','introMenu6A');chng11('introMenu7','introMenu7A');
		chng11('introMenu8','introMenu8A');changeDivMenuLayer(8,2)" href="#"><IMG 
		id=introMenu2 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb52_02_off.gif" width=159 border=0 
		name=introMenu2></A></td>
	<td width="159"><A onClick="chng11('introMenu1','introMenu1A');chng11('introMenu2','introMenu2A');chng11('introMenu3','introMenu3B');
		chng11('introMenu4','introMenu4A');chng11('introMenu5','introMenu5A');chng11('introMenu6','introMenu6A');chng11('introMenu7','introMenu7A');
		chng11('introMenu8','introMenu8A');changeDivMenuLayer(8,3)" href="#"><IMG 
		id=introMenu3 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb52_03_off.gif" width=159 border=0 
		name=introMenu3></A></td>
	<td width="159"><A onClick="chng11('introMenu1','introMenu1A');chng11('introMenu2','introMenu2A');chng11('introMenu3','introMenu3A');
		chng11('introMenu4','introMenu4B');chng11('introMenu5','introMenu5A');chng11('introMenu6','introMenu6A');chng11('introMenu7','introMenu7A');
		chng11('introMenu8','introMenu8A');changeDivMenuLayer(8,4)" href="#"><IMG 
		id=introMenu4 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb52_04_off.gif" width=159 border=0 
		name=introMenu4></A></td>
</tr>
<tr colspan="4">
	<td height="5"></td>
</tr>
<tr>
	<td width="159"><A onClick="chng11('introMenu1','introMenu1A');chng11('introMenu2','introMenu2A');chng11('introMenu3','introMenu3A');
		chng11('introMenu4','introMenu4A');chng11('introMenu5','introMenu5B');chng11('introMenu6','introMenu6A');chng11('introMenu7','introMenu7A');
		chng11('introMenu8','introMenu8A');changeDivMenuLayer(8,5)" href="#"><IMG 
		id=introMenu5 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb52_05_off.gif" width=159 border=0 
		name=introMenu5></A></td>
	<td width="159"><A onClick="chng11('introMenu1','introMenu1A');chng11('introMenu2','introMenu2A');chng11('introMenu3','introMenu3A');
		chng11('introMenu4','introMenu4A');chng11('introMenu5','introMenu5A');chng11('introMenu6','introMenu6B');chng11('introMenu7','introMenu7A');
		chng11('introMenu8','introMenu8A');changeDivMenuLayer(8,6)" href="#"><IMG 
		id=introMenu6 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb52_06_off.gif" width=159 border=0 
		name=introMenu6></A></td>
	<td width="159"><A onClick="chng11('introMenu1','introMenu1A');chng11('introMenu2','introMenu2A');chng11('introMenu3','introMenu3A');
		chng11('introMenu4','introMenu4A');chng11('introMenu5','introMenu5A');chng11('introMenu6','introMenu6A');chng11('introMenu7','introMenu7B');
		chng11('introMenu8','introMenu8A');changeDivMenuLayer(8,7)" href="#"><IMG 
		id=introMenu7 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb52_07_off.gif" width=159 border=0 
		name=introMenu7></A></td>
	<td width="159"><A onClick="chng11('introMenu1','introMenu1A');chng11('introMenu2','introMenu2A');chng11('introMenu3','introMenu3A');
		chng11('introMenu4','introMenu4A');chng11('introMenu5','introMenu5A');chng11('introMenu6','introMenu6A');chng11('introMenu7','introMenu7A');
		chng11('introMenu8','introMenu8B');changeDivMenuLayer(8,8)" href="#"><IMG 
		id=introMenu8 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb52_08_off.gif" width=159 border=0 
		name=introMenu8></A></td>
</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
									<!--온라인 수강 -->
<DIV id="HakInfoDiv01" style="display:show;">
									<table width="660" align="center">
										<tr>
											<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct51_01.gif"></td>
										</tr>
										<tr>
											<td class="ctit_pd">
											온라인 강의는 수강신청 한 4개 강좌가 한꺼번에 진행되는 것이 아니고 순차적으로 진행됩니다.<br>
											1학년 강의의 경우 사회과학, 역사, 일반교양, 종합교양 과목의 순으로 진행됩니다. 강의 게시 일정은 학사일정을 참고<br>
											하시기 바랍니다.<br><br>
											온라인 강의의 수강 방법은 다음과 같습니다. <br><br>
											현재 온라인 강의는 <span class="ctext01">음성 + 칠판</span> 강의와 <span class="ctext01">동영상 + 칠판</span> 강의 두 방식으로 진행됩니다.<br>
											모든 강의가 동영상 + 칠판 강의방식으로 업그레이드 하고 있으나 아직 업그레이드 되지 않은 강의는 
											음성 + 칠판 방식으로 진행하고 있습니다.<br><br>
											<span class="ctext02"><b>① 먼저 음성, 칠판 강의를 듣기 위해서는 GVA 학생용 프로그램 다운받아 설치해야 합니다.</b></span><br>
											&nbsp;&nbsp;&nbsp;&nbsp;‘GVA학생용’ 프로그램은 초기화면 왼쪽 메뉴에 있는 【GVA download】단추를 클릭하고 ‘현재 위치에서 이 프로<br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;그램을 실행’을 선택하여 순서에 따라 설치하면 됩니다. 설치 후 강의파일을 [열기] 선택하면 자동 실행됩니다.<br>
											<div align="right"><a href="<%=CONTEXTPATH%>/doc/GVA2000_Student.exe"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/login/down.gif"></a></div>
											<br>
											<span class="ctext02"><b>② 동영상 강의는</b></span> 윈도우 XP 사용자는 팝업창을 꼭 허용해 주십시오. 강의실에 들어가 [강의듣기]를 누르면 뷰어가 <br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;자동 설치되면서 강의가 진행됩니다. 팝업창을 항상 허용해 주시고 뷰어 설치를 허용해 주시면 다음 강의부터는 <br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;바로 실행이 됩니다. <br><br>
											<span class="ctext02"><b>③ 로그인하기</b></span><br>
											&nbsp;&nbsp;&nbsp;&nbsp;【사용자】와【비밀번호】를 입력하고【들어가기】단추를 누릅니다. 그러면 왼쪽에 있는 메뉴가 정규과정 메뉴로 <br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 바뀝니다. <br><br>
											<span class="ctext02"><b>④ 강의실 들어가기 </b></span><br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;로그인 하고 정규과정으로 들오가면 메인 화면에 수강신청한 과목이 <수강 중인 과정>의 과목으로 뜹니다.<br><br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;수강 중인 과정의 과목을 선택하면 해당 과목 【강의실】로 들어갑니다.<br><br>
											<span class="ctext02"><b>⑤ 수강 강좌 들어가기</b></span> <br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;수강 중인 과정의 과목을 선택하고 <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_lecture01.gif" align="absmiddle"> 버튼은 누르면 강의를 들을 수 있습니다.<br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;또한 해당 과목의 토론이나 리포트 제출 여부와 출석현황이 보입니다. <br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;목록의 과목에서 <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_lecture01.gif" align="absmiddle">를 누르면 수강신청하지 않은 과목들도 강의를 들을 수 있습니다.<br><br>
											<span class="ctext02"><b>⑥ 온라인 강의 수강 </b></span><br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;목록에서 듣고자 하는 과목를 선택한 다음 더블 클릭합니다. <br><br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;▶ GVA 강의는 ‘현재의 위치에서 이 파일을 엽니다’를 선택하는 것이 좋습니다. 그러면 잠시 내려받기가 <br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;진행되고 GVA2000 학생용 프로그램이 뜨면서【강의설치 및 재생】 화면이 나옵니다. 여기서【시작】<br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;단추를 누르면 강의화면이 뜨고, 왼쪽 상단에 있는 【재생】을 누르면 음성강의가 시작됩니다. <br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;이미 다운 받은 파일(***.gdb 형태)은 윈도 탐색기에서 파일이름만 더블클릭해도 자동으로 GVA 프로<br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;그램이 뜨면서【시작】으로 가게 됩니다.<br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;▶ 동영상 강의는 바로 뷰어창이 뜨면서 실행됩니다.<br><br>
											&nbsp;&nbsp;&nbsp;【공지사항】,【강의계획서】,【토론방】【과제】등의 메뉴도 꼭 확인해 봅시다. <br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;특히 이 중 【토론방】은 매주 온라인 강의별로 토론 주제가 게시되니 토론에 주체적으로 참여하기를 권장합니다. <br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 【과제】는 온라인 강의와 오프라인 강의가 마무리 되면 출제<br>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;됩니다. 
											</td>
										</tr>
									</table>
</DIV>
<DIV id="HakInfoDiv02" style="display:none;">
									<!--그룹공부 참여 -->
									<table width="660" align="center">
										<tr>
											<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct51_02.gif"></td>
										</tr>
										<tr>
											<td class="ctit_pd">
											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 그룹공부란..</b></span><br><br>
											각 지역 학습관 별로 학생들은 그룹공부를 진행합니다. 온라인 강의는 시?공간의 제약을 줄이는 좋은 장점이 있는 반면에 학습을 집중해서 충실히 하기 어렵다는 것과 교수의 강의방식이 일방적이라는 한계가 있습니다. 이러한 한계를 극복하기 위해서 각 지역 학습관 별로 함께 모여 책을 읽거나 온라인 강의를 들으면서 가졌던 의문이나 문제들을 서로 토론하는 학습활동이 그룹공부입니다. <br><br>
											모든 강의가 동영상 + 칠판 강의방식으로 업그레이드 하고 있으나 아직 업그레이드 되지 않은 강의는 
											음성 + 칠판 방식으로 진행하고 있습니다.<br><br>

											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 진행방식</b></span><br><br>
											그룹공부에는 졸업생이나 지역학습관 운영위원 중에서 후배들을 위해 그룹공부를 뒷바라지 하는 도우미가 함께 합니다. 그룹공부는 각 지역 학습관 별로 진행되며 일주일에 한번 씩 진행하는 것을 원칙으로 하고 있습니다. <br><br>그룹공부는 사이버 노동대학의 학습과정 중에서 매우 중요한 학습실천입니다. 그룹공부에 함께하는 학우들은 서로 북돋아주고 이끌어주며 3년의 공부를 함께 해 나갑시다!! </td>
										</tr>
									</table>
</DIV>
<DIV id="HakInfoDiv03" style="display:none;">
									<!--온라인토론 참여 -->
									<table width="660" align="center">
										<tr>
											<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct51_03.gif"></td>
										</tr>
										<tr>
											<td class="ctit_pd">
											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 참여방법</b></span><br><br>
											로그인하고 과목 선택 후에 강의실 하위 메뉴 중에서 토론을 선택한 후 주제를 선택한 후  버튼을 누르고 토론 내용을 올리면 됩니다. <br>
											토론방에는 온라인 담당 교수님이 각각의 강의에 대한 토론주제를 제출합니다.(매 강의마다 토론주제를 올려주는 교수님이 있는 반면, 몇 개의 강의를 묶어 토론주제를 올려주는 교수님도 있습니다) 이 토론주제에 학생들의 의견을 자유로이 개진하는 것입니다. 의견이 다른 부분이 있으면 리플을 달아 쟁점을 형성하여 토론할 수도 있겠지요. 또한, 교수님이 내어 준 주제 말고 토의해 볼 주제가 있다면 자유토론방에 학생들이 스스로 토론주제를 올려 의견을 개진할 수도 있습니다.<br><br>

											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 온라인토론의 장점</b></span><br><br>
											전국의 학생들과 토의 토론을 할 수 있다는 것입니다. 그룹공부를 진행하면서 토의되었던 내용을 올리는 것도 다른 지역의 학우들의 토론을 활성화하는데 도움이 되겠습니다.</td>
										</tr>
									</table>
</DIV>
<DIV id="HakInfoDiv04" style="display:none;">
									<!-- 오프라인 강의수강 -->
									<table width="660" align="center">
										<tr>
											<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct51_04.gif"></td>
										</tr>
										<tr>
											<td class="ctit_pd">
											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 오프라인 강의</b></span><br><br>
											각 지역별로 진행되며, 각 과목의 온라인 강의가 모두 게시된 후 진행합니다. 따라서 약 한 달에 한 번씩 진행되는 것입니다. 강의 일정은 오프라인 강의 담당교수님과 지역 학생들의 일정을 고려하여 정하고 강의 열흘에서 이주일 전에 메일, 문자, 전화통화 등으로 공지합니다. 오프라인 강의는 직접 출석을 적극 권장하며, 출석하지 못한 학생들은 오프라인 동영상 강좌로 온라인 출석할 수 있습니다. <br><br>

											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 오프라인 강의 학습의 장점</b></span><br><br>
											학생들이 온라인 강의를 수강하고 난 후 각 강좌의 주제에 대해 다시 한번 핵심적인 내용들을 정리하는 기회이며, 질의 응답을 통해 학습 내용을 심화할 수 있는 장이기도 합니다. </td>
										</tr>
									</table>
</DIV>
<DIV id="HakInfoDiv05" style="display:none;">
									<!-- 리포트 -->
									<table width="660" align="center">
										<tr>
											<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct51_05.gif"></td>
										</tr>
										<tr>
											<td class="ctit_pd">
											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 제출방법</b></span><br><br>
											로그인하고 과목 선택 후에 강의실 하위 메뉴 중에서 과제를 선택한 후 주제를 선택한 후  버튼을 누르고 과제를 제출하시면 됩니다. <br><br>
											각 강의 당 과제 주제가 출제되며 리포트 주제는 각 과목이 마무리되는 토론주간에 게시됩니다. 그룹공부에서 토의한 내용을 바탕으로 그룹과제를 작성하는 것도 적극 권장합니다. <br><br>
											과제 제출과 관련한 어려움이 있으실 시에는 교무실<span class="ctext01">(전화 02-779-7427~8 혹은 study@junnodae.org)</span>로 전화하셔서 상의하세요. </td>
										</tr>
									</table>
</DIV>
<DIV id="HakInfoDiv06" style="display:none;">
									<!-- 마음수련 참가 -->
									<table width="660" align="center">
										<tr>
											<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct51_06.gif"></td>
										</tr>
										<tr>
											<td class="ctit_pd">
											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 마음수련은</b></span><br><br>
											충북 영동에 위치한 마음수련원에서 진행되며 마음수련은 1년에 1회 참석하면 됩니다. 1학년 초급과정(2박 3일), 2학년 중급과정(2박 3일), 3학년 고급과정(3박 4일)으로 실시됩니다. <br><br>
											
											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 참여방법은</b></span><br><br>
											학사일정, 공지사항이나 이메일을 통해 공지된 일정 계획을 보고 개인 일정을 고려하여 참여를 신청합니다.(전화나 이메일) 마음수련 초급과정은 매 차수마다 10명 내외로 진행하고 있습니다. 마음수련도 정규과정의 하나이기 때문에 마음수련에 참여하지 않으면 고학년으로의 진급이 되지 않습니다.<br><Br>
											
											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 특히 1학년에 진행되는 마음수련 초급과정은</b></span><br><br>
											자주적인 인간, 사회적·공동체적 인간으로서의 인격형성을 목표로 하고 있습니다. 크게 몸 수련과 마음 수련으로 나누어 진행됩니다.<br><br>
											마음수련과 관련한 문의 사항은 마음수련원 <span class="ctext01">전화(043-743-7418) 혹은 이메일(maum@junnodae.org)</span>로 문의바랍니다. </td>
										</tr>
									</table>
</DIV>
<DIV id="HakInfoDiv07" style="display:none;">
									<!-- 평가 -->
									<table width="660" align="center">
										<tr>
											<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct51_07.gif"></td>
										</tr>
										<tr>
											<td class="ctit_pd">
											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 평가는</b></span><br><br>
											우열을 나누고자 함이 아니라, 학습하면서 부족한 부분이 무엇이었는가를 알아보고 함께 채워가고자 하는 것입니다.  <br><br>
											
											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 강좌 당 학점과 평가기준 </b></span><br><br>
											각 강좌 당 3학점이고, D<sup>0</sup>이상 (60점 이상)을 취득 학점으로 합니다. 즉 59점 이하는 학점으로 인정이 되지 않습니다. 이 강좌에 대해서는 재수강을 해야 합니다. 이 중 마음수련은 4학점입니다. 평가기준은 절대평가로 진행합니다.<br><br>

											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 성적 산출 </b></span><br><br>
												<table width="95%" align="center" border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
													<tr> 
														<td height="30" width="130" bgcolor="eeede8" class='bod_tl_t'>배점 항목</td>
														<td width="50" bgcolor="eeede8" class='bod_tl_t'>점  수</td>
														<td bgcolor="eeede8" class='bod_tl_t'>상세 배점 내역</td>
													</tr>
													<tr> 
														<td bgcolor="f9f8f3" class='bod_tl'>온라인 강의출석</td>
														<td class="bod_co01">40점</td>
														<td class="bod_co02">온라인 강의 당 출석 점수 : 2점 * 20강 = 40점</td>
													</tr>
													<tr> 
														<td bgcolor="f9f8f3" class='bod_tl'>오프라인 강의 출석</td>
														<td class="bod_co01">30점</td>
														<td class="bod_co02">직접 출석 30점, 동영상 강의 출석 20점 </td>
													</tr>
													<tr> 
														<td bgcolor="f9f8f3" class='bod_tl'>그룹공부 참여</td>
														<td class="bod_co01">10점</td>
														<td class="bod_co02">2.5점 * 4주 = 10점</td>
													</tr>
													<tr> 
														<td bgcolor="f9f8f3" class='bod_tl'>온라인 토론 참여 </td>
														<td class="bod_co01">10점</td>
														<td class="bod_co02">2.5점 * 4차 = 10점 </td>
													</tr>
													<tr> 
														<td bgcolor="f9f8f3" class='bod_tl'>리포트 </td>
														<td class="bod_co01">10점</td>
														<td class="bod_co02">A (10점), B (9점), C (8점), D (6점), 제출 시 기본 점부 6점  </td>
													</tr>
													<tr> 
														<td bgcolor="f9f8f3" class='bod_tl'>총계</td>
														<td class="bod_co01">100점</td>
														<td class="bod_co02"></td>
													</tr>
												</table>
												<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;* 구속자의 경우 리포트 제출과 이를 평가한 것을 기반으로 학점 취득이 가능합니다.<Br><br>

											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 강좌 당 학점과 평가기준 </b></span><br><br>
											각 강좌 당 3학점이고, D<sup>0</sup>이상 (60점 이상)을 취득 학점으로 합니다. 즉 59점 이하는 학점으로 인정이 되지 않습니다. 이 강좌에 대해서는 재수강을 해야 합니다. 이 중 마음수련은 4학점입니다. 평가기준은 절대평가로 진행합니다.<br><br>
												<table width="95%" align="center" border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
													<tr> 
														<td height="30" width="17%" bgcolor="eeede8" class='bod_tl_t'>등급</td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>A<sup>+</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>A<sup>0</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>B<sup>+</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>B<sup>0</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>C<sup>+</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>C<sup>0</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>D<sup>+</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>D<sup>0</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>F</td>
													</tr>
													<tr> 
														<td width="17%" bgcolor="eeede8" class='bod_tl_t'>점수</td>
														<td class="bod_co01">95~100</td>
														<td class="bod_co01">90~94</td>
														<td class="bod_co01">85~89</td>
														<td class="bod_co01">80~84</td>
														<td class="bod_co01">75~79</td>
														<td class="bod_co01">70~74</td>
														<td class="bod_co01">65~69</td>
														<td class="bod_co01">60~64</td>
														<td class="bod_co01">0~59</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
</DIV>
<DIV id="HakInfoDiv08" style="display:none;">
									<!-- 졸업 -->
									<table width="660" align="center">
										<tr>
											<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct51_08.gif"></td>
										</tr>
										<tr>
											<td class="ctit_pd">
											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 이수요건 </b></span><br><br>

												<table width="95%" align="center" border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
													<tr> 
														<td width="160" bgcolor="eeede8" class='bod_tl_t'>이수 학기</td>
														<td class="bod_co02">3년 6학기</td>
													</tr>
													<tr> 
														<td bgcolor="eeede8" class='bod_tl_t'>수강 과목</td>
														<td class="bod_co02">학기 당 4과목, 계 24과목 </td>
													</tr>
													<tr> 
														<td bgcolor="eeede8" class='bod_tl_t'>학점</td>
														<td class="bod_co02">3학과 72학점, 마음수련 12학점, 계 84학점 </td>
													</tr>
												</table>
												<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;* 구속자의 경우 리포트 제출과 이를 평가한 것을 기반으로 학점 취득이 가능합니다.<Br><br>

											<br><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon09.gif" align="absmiddle"><span class="ctext02"><b> 강좌 당 학점과 평가기준 </b></span><br><br>
											각 강좌 당 3학점이고, D<sup>0</sup>이상 (60점 이상)을 취득 학점으로 합니다. 즉 59점 이하는 학점으로 인정이 되지 않습니다. 이 강좌에 대해서는 재수강을 해야 합니다. 이 중 마음수련은 4학점입니다. 평가기준은 절대평가로 진행합니다.<br><br>
												<table width="95%" align="center" border="1" cellpadding="0" cellspacing="0" bordercolor="d7d5cb">
													<tr> 
														<td height="30" width="17%" bgcolor="eeede8" class='bod_tl_t'>등급</td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>A<sup>+</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>A<sup>0</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>B<sup>+</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>B<sup>0</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>C<sup>+</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>C<sup>0</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>D<sup>+</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>D<sup>0</sup></td>
														<td width="55" bgcolor="f9f8f3" class='bod_tl'>F</td>
													</tr>
													<tr> 
														<td width="17%" bgcolor="eeede8" class='bod_tl_t'>점수</td>
														<td class="bod_co01">95~100</td>
														<td class="bod_co01">90~94</td>
														<td class="bod_co01">85~89</td>
														<td class="bod_co01">80~84</td>
														<td class="bod_co01">75~79</td>
														<td class="bod_co01">70~74</td>
														<td class="bod_co01">65~69</td>
														<td class="bod_co01">60~64</td>
														<td class="bod_co01">0~59</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
</DIV>
								</td>
							</tr>
						</table>
<%@include file="../common/footer.jsp" %>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>

<Script Language=Javascript>

function chgTab(val){
    objName = eval("document.all.divTab"+ val);

    document.all.divTab1.style.display = "none";
    document.all.divTab2.style.display = "none";
    
    objName.style.display = "";
    
    if(val=="2") chgSubTab("1");
     
}
  
  
function chgSubTab(val){
    objName = eval("document.all.subDivTab"+ val);
    objImgName = "menu"+ val;

    document.all.subDivTab1.style.display = "none";
    document.all.subDivTab2.style.display = "none";
    document.all.subDivTab3.style.display = "none";
    document.all.subDivTab4.style.display = "none";
    
    objName.style.display = "";

    MM_swapImage('menu1','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_01_off.gif',1);
    MM_swapImage('menu2','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_02_off.gif',1);
    MM_swapImage('menu3','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_03_off.gif',1);
    MM_swapImage('menu4','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_04_off.gif',1);
    
    MM_swapImage(objImgName,'','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_0'+val+'_on.gif',1);
    
}


</Script>

						<!-- 내용 시작 -->
						<table id=divTab1 Style="display:" width="670" height="100%" align="center">
							<tr valign="top">
								<td class="sub_contentform">
									<table width="660" align="center">
										<tr valign="top">
											<td>
												<table width="100%" class="Tab01">
													<tr valign="top">
														<td width="91" class="Tab02"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab41_01_on.gif" name="tab01" width="91" height="26" border="0"></td>
                                                        <td><a href="javascript:chgTab('2')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab41_02_off.gif" name="tab_02" width=120" height="26" border="0"></a></td>
													</tr>
												</table>
											</td>
										</tr>
										<!-- 컨텐츠 내용 -->
										<tr valign="top">
											<td>
												<!-- 메인화면 --> 
												<table width="630" align="center">
													<tr>
														<td class="ctit"><div align="justify">
														홈페이지 상단에는 사이버 노동대학에 대한 소개와 입학 안내와 소식 그리고 홈페이지 이용안내로 구성되어 있습니다. 
                                                        홈페이지 왼편에는 정규과정 학생 뿐 아니라 일반인들을 위한 공개 컨텐츠와 관련된 메뉴들이 배열되어  있습니다.<br><br>
                                                        * 왼쪽에 있는 메뉴는 일반 이용자, 학생, 교수에 따라 달라집니다. 처음 홈페이지에 들어오면 일반 
                                                        이용자 메뉴(열린마당 메뉴)가 뜹니다. 학생이 사용자(ID)와 비밀번호(password)를 치고 
                                                        <span class="ctext01"><b>'들어가기'</b></span>선택하면 학생전용(정규과정) 메뉴로 바뀝니다. 교수의 경우에는 
                                                        교수전용 메뉴로 바뀝니다. <br>
                                                        * 학생은 입학신청과 동시에 신청한 사용자와 비밀번호로 로그인 하여 학생전용 메뉴를 사용할 수 있습니다.</div><br></td>
													</tr>
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct41_01.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
															<table width="100%" cellpadding="0" cellspacing="0">
																<tr> 
																	<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_01.gif"></td>
																</tr>
                                                                <tr> 
																	<td style="padding:15 0 0 0"><span class="ctext02"><b>① 상단메뉴 안내입니다.</b></span><br><br>
                                                                    <b>&nbsp;&nbsp;&nbsp;&nbsp;[대학소개] : 대학에 대한 전반적인 소개와 조직,기구 등에 대한 내용을 볼 수 있습니다</b><br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 인사말 : 설립추진위 대표님과 총장님의 인사말을 볼 수 있습니다<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 설립취지,연혁 : 사이버 노동대학의 설립취지와 연혁을 년도별로 볼 수 있습니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육내용,과정 : 교과 과정의 내용과 1,2,3 학년의 교과과정을 볼 수 있습니다<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교수진 소개 : 온라인, 오프라인 교수진들에 대한 간략한 정보를 볼 수 있습니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 대학의 조직과 기구 : 사이버 노동대학의 조직과 기구를 볼 수 있습니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 찾아오는길 : 사이버 노동대학의 약도를 볼 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;&nbsp;[입학안내]</b><br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 입학안내 : 입학 신청서 제출부터 개강까지의 안내를 볼 수 있습니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 수강안내 : 수강신청 방법, 변경 방법 등에 대한 내용을 알 수 있습니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 학사일정 : 한 학기에 대한 학사일정을 볼 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;&nbsp;[학교소식]</b><br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 학사공지 : 온라인, 오프라인 강의 등 주요 학사 공지를 볼 수 있는 게시판입니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 일반공지 : 학사 공지 외에 공지사항을 볼 수 있는 게시판입니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 소식 : 학교 관계자들의 활동이나 다양한 소식을 볼 수 있는 게시판입니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;&nbsp;[이용안내] : 사이트 이용하기 위한 안내를 볼 수 있다.</b><br></td>
																</tr>
                                                                <tr> 
																	<td style="padding:20 0 0 0"><span class="ctext02"><b>② 왼편 메뉴 안내입니다.</b></span><br><br>
                                                                    <b>&nbsp;&nbsp;&nbsp;&nbsp;[정규과정] : 학생들 전용 메뉴로 로그인 후에 학생들의 공부와 관련한 메뉴입니다.</b><br>
                                                                    <b>&nbsp;&nbsp;&nbsp;&nbsp;[열린마당]</b><br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 공개강좌 : 기획교양강좌, 정세와 쟁점 등 공개강좌를 들을 수 
                                                                    있습니다. <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 정세와 쟁점은 강의를 듣고 토론도 할 수 있습니다.<br>
                                                                    <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_02.gif"><br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 좋은 글 좋은 책 : 좋은 글과 좋은 책을 선정하여 주기적으로 올립니다. <br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 세상 돌아가는 이야기 : 노동자, 민중운동의 동향과 투쟁현장 등을 
                                                                    볼 수 있는 곳입니다. <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	&nbsp;주로 동영상으로 제작하여 올리고 있습니다. <br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 문화마당 : 각종 문화정보와 그림, 음악 등을 보여주는 곳입니다.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;문화에 대한 내용을 공유할 수 있습니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 마음공부 : 인간적 &middot; 도덕적으로 보다 성숙한 인생관을 
                                                                    정립을 위하여 마련된 컨텐츠입니다.<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	'아침시향','엄마 밥줘', '애인에게 들려주는 도덕경','생태적 영성', '열사일화'등이 있습니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 자료나눔터 : 일반 이용자, 학생, 교수 등이 다른 사람들도 공유했으면 하는 자료를 올리는 곳입니다. <br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 대학공동체 : 설립추진위, 대학운영위, 후원위원, 교수, 동창회, 재학생들 각각 서로 소통하고 의견을<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																	&nbsp;나누고 공유하기 위한 게시판입니다.<br>
																	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 대자보 : 일반 이용자, 학생, 교수 등이 자유롭게 글을 올리는 자유게시판 기능입니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 추천사이트: 추천사이트 목록을 보고 링크를 클릭해서 사이트 이동을 할 수 있습니다<br></td>
																</tr>
                                                                <tr> 
																	<td style="padding:25 0 0 28"><span class="ctext01"><b>* 대학공동체, 대자보, 자료나눔터 등 게시판의 웹 
                                                                    에디터의 편집도구 사용방법입니다.</b></span>                                                                    </td>
                                                                </tr>
                                                                <tr> 
																	<td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_03.gif"><br></td>
                                                                </tr>
                                                                <tr> 
																	<td style="padding:7 0 0 28">
                                                                    <b>→</b> 글을 입력 시에 에디터 툴로 글을 다이나믹하게 변경하거나, 동영상, 이미지 파일 등을 글에 함께<br>
                                                                     &nbsp;&nbsp;&nbsp;&nbsp;삽입하여 올릴 수 있습니다. 이를 웹 에디터라 지칭하는데 사용법에 대해 간략하게 설명합니다.                                                                    </td>
                                                                </tr>
                                                                 <tr> 
																   <td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_04.gif"  align="absmiddle">
                                                                   &nbsp;&nbsp;<b>→</b> 글꼴과 글씨의 크기를 변경할
                                                                    수 있다.</td>
                                                                </tr>
                                                                <tr> 
																   <td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_05.gif"  align="absmiddle">
                                                                   &nbsp;&nbsp;<b>→</b> 새 글 또는 여러 페이지일 경우 그 동안 작업한 것을 삭제하고 다시 글을 작성할<br>
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;수 있습니다.</td>
                                                                </tr>
                                                                <tr> 
																   <td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_06.gif"  align="absmiddle">
                                                                   &nbsp;&nbsp;<b>→</b> undo, redo (이전작업을 취소하거나, 취소한 작업을 다시 복구합니다)</td>
                                                                </tr>
                                                                <tr> 
																   <td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_07.gif"  align="absmiddle">
                                                                   &nbsp;&nbsp;<b>→</b> 글씨체를 볼드체, 이태릭체, 글씨에 언더라인을 적용시킵니다. (이미 글씨를 쓰고나서<br> 
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;글씨체를 변경시키려면 내가 적용할 글씨체를
                                                                    선택한 후에 아이콘을 눌러서 적용을<br>
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;시켜야  합니다)</td>
                                                                </tr>
                                                                <tr> 
																   <td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_08.gif"  align="absmiddle">
                                                                   &nbsp;&nbsp;<b>→</b> 글자 색, 글자 배경색, 배경이미지를 적용시킬 수 있습니다.</td>
                                                                </tr>
                                                                <tr> 
																   <td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_09.gif"  align="absmiddle">
                                                                   &nbsp;&nbsp;<b>→</b> 글씨의 정렬을 할 수 있습니다.</td>
                                                                </tr>
                                                                <tr> 
																   <td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_10.gif"  align="absmiddle">
                                                                   &nbsp;&nbsp;<b>→</b> 페이지가 여러 개일 경우 이전페이지, 다음페이지로 이동 할 수 있고 <br>
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MPage 에는 현재 페이지 번호가 보여집니다.</td>
                                                                </tr>
                                                                <tr> 
																   <td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_11.gif"  align="absmiddle">
                                                                   &nbsp;&nbsp;<b>→</b> 페이지 추가와 페이지 삭제를 할 수 있습니다. (페이지 추가 또는 삭제 할 경우에는 경고창이<br>
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                   &nbsp;&nbsp;뜨는데 확인을 누르면 원하는 작업이 적용됩니다)</td>
                                                                </tr>
                                                                <tr> 
																   <td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_12.gif"  align="absmiddle">
                                                                   &nbsp;&nbsp;<b>→</b> 이미지삽입, 비디오삽입, 오디오/음성삽입, 플래쉬 삽입 아이콘 각각의 아이콘을<br>
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;클릭하면 그에 맞게 업로드 가능할 
                                                                   파일명이 아래에 보여지고 찾아보기 버튼도<br>
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;함께 보여집니다.<br>
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                   <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_13.gif"  align="absmiddle"><br>
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                   찾아보기를 클릭해서 파일을 업로드 할 수 있습니다.  </td>
                                                                </tr>
                                                                <tr> 
																   <td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_14.gif"  align="absmiddle">
                                                                   &nbsp;&nbsp;<b>→</b> 테이블 모양의 표를  삽입, 표의 행, 열 수를 수정, 선/기호를 삽입할 수 있습니다.</td>
                                                                </tr>
                                                                <tr> 
																   <td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_15.gif"  align="absmiddle">
                                                                   &nbsp;&nbsp;<b>→</b> 하이퍼링크, 이미지링크, 멀티미디어 링크 등을 삽입할 수 있습니다.</td>
                                                                </tr>
                                                                <tr> 
																   <td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_16.gif"  align="absmiddle">
                                                                   &nbsp;&nbsp;<b>→</b> html 태그를 편집할 수 있습니다. 클릭하면 현 프레임의 입력된 정보가 html  태크 형태로 변환<br>
                                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                   되어 태그를 직접 수정할 수 있는 팝업 화면을 볼 수 있습니다.</td>
                                                                </tr>
                                                                <tr> 
																   <td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_17.gif"  align="absmiddle">
                                                                   &nbsp;&nbsp;<b>→</b> 글 등록을 하기 전에 글 모양이 어떻게 보일지 미리 보기를 하여 볼 수 있습니다.</td>
                                                                </tr>
                                                                <tr> 
																   <td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_18.gif"  align="absmiddle">
                                                                   &nbsp;&nbsp;<b>→</b> 물음표 아이콘을 클릭하면 웹 에디터의 매뉴얼을 팝업으로 볼 수 있습니다.</td>
                                                                </tr>
                                                                <tr> 
																   <td style="padding:10 0 0 28"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid1_19.gif"  align="absmiddle">
                                                                   &nbsp;&nbsp;<b>→</b> 프레임의 크기를 줄이거나 키울 수 있습니다.</td>
                                                                </tr>
															</table>
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
						<!-- 내용 끝 -->
					
					
						<!-- 내용 시작 -->
						<table id=divTab2 Style="display:none" width="670" height="100%" align="center">
							<tr valign="top">
								<td class="sub_contentform">
									<table width="660" align="center">
										<tr valign="top">
											<td>
												<table width="100%" class="Tab01">
													<tr valign="top">
														<td width="91" class="Tab02"><a href="javascript:chgTab('1')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab41_01_off.gif" name="tab_01" width="91" height="26" border="0"></a></td>
														<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab41_02_on.gif" name="tab02" width="120" height="26" border="0"></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr valign="top">
											<td>
												<table width="640" align="center" onload="MM_preloadImages('<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_01_on.gif','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_02_on.gif','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_03_on.gif','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_04_on.gif')">
													<tr valign="top">
														<td class="ctit_pd02">
															<table width="100%" align="left">
																<tr>
																	<td width="153"><A onMouseOver="chgSubTab('1');MM_swapImage('menu1','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_01_on.gif',1)" Style="cursor:hand"><IMG id=menu1 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_01_off.gif" width=149 border=0 name=menu1></A></td>
																	<td width="153"><A onMouseOver="chgSubTab('2');MM_swapImage('menu2','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_02_on.gif',1)" Style="cursor:hand"><IMG id=menu2 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_02_off.gif" width=149 border=0 name=menu2></A></td>
																	<td width="153"><A onMouseOver="chgSubTab('3');MM_swapImage('menu3','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_03_on.gif',1)" Style="cursor:hand"><IMG id=menu3 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_03_off.gif" width=149 border=0 name=menu3></A></td>
                                                                    <td width="153"><A onMouseOver="chgSubTab('4');MM_swapImage('menu4','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_04_on.gif',1)" Style="cursor:hand"><IMG id=menu4 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb41_04_off.gif" width=149 border=0 name=menu4></A></td>
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
												<!-- 나의학습실 --> 
												<table width="630" align="center" id=subDivTab1 style="DISPLAY: ">
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct41_02.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
															<table width="100%" cellpadding="0" cellspacing="0">
																<tr valign="top"> 
																	<td width="165" style="padding:10 0 0 0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_01.gif"></td>
                                                                    <td width="430" style="padding:15 0 0 0"><span class="ctext02"><b>1.1 나의 학습실 메뉴</b></span><br>
                                                                    <b>&nbsp;&nbsp;&nbsp;① 나가기 : </b>사이트 Log-Out 합니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;② 내정보 : </b>자기 정보를 보거나 수정할 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;③ 수강신청 : </b>수강신청 메뉴로 이동합니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;④ 수강현황 - 수강중인 과정 : </b>학습자가 수강중인 과정의 리스트를 볼 수<br>
                                                                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                     &nbsp;&nbsp;있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑤ 수강현황 - 지난강의 : </b>학습자가 수료한 과정의 리스트를 볼 수 있습니<br>
                                                                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑥ 개인정보 : </b>자기 정보를 보거나 수정할 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑦ 개인면담 : </b>교수자와의 개인면담 전체 글을 볼 수 있다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑧ 쪽지함 : </b>학생들 간에, 학생과 교수간, 학생과 관리자 간에 서로 쪽지를<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;주고 받는 기능입니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑨ 일정관리 : </b>일정관리를 합니다. 주요 오프라인 강의 일정 등이 공지됩니<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑩ 평가 : </b>연도/학기, 과정별 점수를 볼 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑪ 자유게시판 : </b>자유롭게 학생의 의견을 글로 올릴 수 있는 곳입니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑫ 학교에바란다 : </b>학교에 바라는 것을 자유로이 작성하는 게시판입니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑬ 동아리 : </b>동아리로 이동합니다.<br>
</td>
																</tr>
                                                                <tr> 
																   <td colspan="2" style="padding:20 0 0 0"><span class="ctext02"><b>1.2 수강신청</b></span><br>
                                                                    &nbsp;&nbsp;&nbsp;- 수강신청과 수강신청 정정기간 동안 자유로이 수강신청과 수정을 할 수 있습니다. <br>
                                                                    &nbsp;&nbsp;&nbsp;- <b>한 과목 카테고리 당 하나의 과목을 선택하여 수강신청을 할 수 있습니다.</b><br></td>
                                                                </tr>
                                                                <tr> 
																   <td colspan="2" style="padding:20 0 0 0"><span class="ctext02"><b>1.3 수강중인 과정</b></span><br>
                                                                    <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_02.gif"></td>
                                                                </tr>
                                                                <tr> 
																   <td colspan="2">
                                                                    &nbsp;&nbsp;&nbsp;* 학생이 수강 신청한 과정과 전체 개설 강좌를 수강할 수 있는 과정의 리스트가 나옵니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;* 수강신청 기간과 수강신청 정정 기간동안 화면 오른쪽 상단의 <span class="ctext01"><b>
                                                                    ☞ 수강신청 정정하기</b></span> 
                                                                    버튼을 통해서 수강<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;신청내역을 수정할 수 있습니다.<br><br>
                                                                    수강신청한 과정의 진도율, 출석상황, 과제, 토론 현황을 볼 수 있습니다.<br>
                                                                    <span class="ctext01"><b>☞ 학습하기 버튼을 클릭하면 강의실로 입장합니다.</b></span><br><br>
                                                                    화면 하단에는 수강신청 하지 않았지만 개설되는 전체 과목이 나옵니다. 수강신청 하지 않은 과목도 
                                                                    강의를 들을 수 있습니다. </td>
                                                                </tr>
                                                                <tr> 
																   <td colspan="2" style="padding:20 0 0 0"><span class="ctext02"><b>1.4 지난 강의</b></span><br>
                                                                    <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_03.gif"><br>
                                                                    &nbsp;&nbsp;&nbsp;* 수강기간이 끝난 학기 과목의 리스트를 볼 수 있습니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;* 과정의 수료여부와 학생의 수강 기록을 볼 수 있습니다.</td>
                                                                </tr>
                                                                <tr> 
																   <td colspan="2" style="padding:20 0 0 0"><span class="ctext02"><b>1.5 쪽지함</b></span><br>
                                                                    <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_04.gif"><br>
                                                                    &nbsp;&nbsp;&nbsp;* 쪽지를 보내거나 받은 쪽지를 관리하는 쪽지함입니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;* 쪽지를 작성할 시의 수신 대상은 사용자검색 버튼을 클릭하여 뜨는 팝업창으로 합니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;* 쪽지를 삭제할 때에는 리스트 왼쪽의 선택 폼을 클릭하여 삭제합니다.<br>
                                                                    </td>
                                                                </tr>
                                                                <tr> 
																   <td colspan="2" style="padding:20 0 0 0"><span class="ctext02"><b>1.6 평가</b></span><br>
                                                                    <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_05.gif"><br>
                                                                    &nbsp;&nbsp;&nbsp;* 년도 학기 과정별 전체 평가 점수를 볼 수 있습니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;* 학습자는 점수를 확인 한 후 문의사항이 있을 경우 해당 과정 강의실의 개인면담 게시판을 
                                                                    이용하여 <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;교수에게 면담합니다.<br>
                                                                    </td>
                                                                </tr>
															</table>
													  </td>
													</tr>
												</table>
                                                <!-- 강의실 -->
                                                <table width="630" align="center" id=subDivTab2 style="DISPLAY: none">
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct41_03.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
															<table width="100%" cellpadding="0" cellspacing="0">
																<tr valign="top"> 
																   <td colspan="2" style="padding:5 0 0 0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_06.gif"></td>
                                                                </tr>
                                                                <tr> 
																   <td colspan="2"style="padding:10 0 0 0">
                                                                   <span class="ctext02"><b>* 강의실 메인화면입니다.</b></span><br>
                                                                    &nbsp;&nbsp;&nbsp;- <span class="ctext01"><b>☞ [강의보기]</b></span>를 클릭하면 학습하기 화면으로 넘어가며, 
                                                                    강의 컨텐츠를 수강할 수 있습니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;- 강의 수강 현황과 진도율, 출석시간 등의 학습현황 데이터를 확인할 수 있습니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;- 진행되는 토론과 과제가 있다면 [진행 중]이란 메시지가 보이면서, 각 토론, 
                                                                    과제 페이지로 직접 이동할 수 <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;있는 링크가 활성화됩니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;- 최근 공지사항을 볼 수 있습니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;- 각 평가 항목에 대한 성적을 확인할 수 있습니다.<br> </td>
                                                                </tr>
                                                                <tr valign="top"> 
																	<td width="165" style="padding:20 0 0 0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_07.gif"></td>
                                                                    <td width="430" style="padding:30 0 0 0"><span class="ctext02"><b>2.1 강의실 메뉴</b></span><br>
                                                                    <b>&nbsp;&nbsp;&nbsp;① 내 수강 과목 바로가기  : </b>학습자가 수강하고 있는 과정의 리스트가 셀렉<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;트 박스로 활성화 되어 있습니다. 각 과정명을 선택하면,
                                                                     해당 과정의 강의<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;실로 이동합니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;② 강의 계획서  : </b>강의 계획서와 교수님 소개를 볼 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;③ 학습하기 : </b>강의 컨텐츠 목록을 통해 강의를 수강할 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;④ 공지사항 : </b>해당 과목의 공지사항을 확인할 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑤ 게시판 : </b>해당 과목을 수강하는 수강생들이 자유롭게 글을 올리는 게시판<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;입니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑥ 자료실 : </b>교수와 학생들이 서로 자료를 나누고 공유하는 게시판입니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑦ Q&A : </b>수강생들과 교수자, 조교자 들이 서로 질문과 답변을 주고 받을 수<br>
                                                                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                     &nbsp;&nbsp;&nbsp;있는 게시판입니다<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑧ 개인면담 : </b>수강생이 교수자와 면담을 할 경우에 활용하는 게시판입니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑨ 토론방 : </b>토론주제별로 온라인 토론을 하는 방입니다. <br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑩ 과제 : </b>리포트를 출제하고 제출하는 방입니다. <br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑪ 설문 : </b>해당과목의 설문이 진행되는 방입니다. <br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑫ 성적조회 : </b>수강생 본인의 성적을 조회할 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑬ 강의실 나가기 : </b>강의실을 빠져 나와 나의 학습실로 이동합니다. <br>
</td>
																</tr>
                                                                <tr> 
																   <td colspan="2" style="padding:20 0 0 0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet02.gif" align="absmiddle">
                                                                   <span class="ctext02"><b>학습하기</b></span><br>
                                                                   <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_08.gif"><br>
                                                                    &nbsp;&nbsp;&nbsp;- 수강생이 강의를 학습할 수 있습니다. <br>
                                                                    &nbsp;&nbsp;&nbsp;- 강의는 온라인으로 이루어지는 강의와 오프라인으로 이루어지는 강의로 구분됩니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;- <span class="ctext01"><b>☞ 목차명</b></span>을  클릭하면 강의 컨텐츠가 
                                                                    팝업창으로 뜹니다.<br></td>
                                                                </tr>
                                                                <tr> 
																   <td colspan="2" style="padding:20 0 0 0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet02.gif" align="absmiddle">
                                                                   <span class="ctext02"><b>토론방</b></span><br>
                                                                   <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_09.gif"><br>
                                                                    &nbsp;&nbsp;&nbsp;- 토론을 할 수 있는 토론방입니다. <br>
                                                                    &nbsp;&nbsp;&nbsp;- 토론방은 운영방식에 따라, 팀 토론방과 전체 토론방으로 구분됩니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;- <span class="ctext01"><b>☞ 입장 항목</b></span>을 클릭하면 토론방으로 입장합니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;- 제목을 클릭하면 토론방의 정보를 볼 수 있는 화면이 나옵니다.<br> 
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="ctext01"><b>☞ 참여하기</b></span> 
                                                                    버튼을 클릭하면 토론방으로 입장합니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;- 토론방으로 입장하면 화면 상단에는 토론방의 정보를 아래에는 토론글의 리스트를 볼 수 있습니다.<
                                                                    <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;토론방 게시판의 사용법은 일반 게시판의 사용법과 같습니다.</td>
                                                                </tr>
                                                                <tr> 
																   <td colspan="2" style="padding:20 0 0 0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet02.gif" align="absmiddle">
                                                                   <span class="ctext02"><b>과제</b></span><br>
                                                                   <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_10.gif"><br>
                                                                    &nbsp;&nbsp;&nbsp;- 제출된 과제를 제출할 수 있습니다.<br></td>
                                                                </tr>
                                                                <tr> 
																   <td colspan="2" style="padding:20 0 0 0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet02.gif" align="absmiddle">
                                                                   <span class="ctext02"><b>설문</b></span><br>
                                                                   <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_11.gif"><br>
                                                                    &nbsp;&nbsp;&nbsp;- 강의실 안에서 진행되는 설문입니다. <br>
                                                                    &nbsp;&nbsp;&nbsp;- 설문명을 클릭하면 설문에 대한 정보를 볼 수 있습니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;- 설문참여 항목의 <span class="ctext01"><b>☞ [참여하기]</b></span>를 
                                                                    클릭하면 설문창이 팝업창으로 나타납니다.</td>
                                                                </tr>
                                                                <tr> 
																   <td colspan="2" style="padding:20 0 0 0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet02.gif" align="absmiddle">
                                                                   <span class="ctext02"><b>성적조회</b></span><br>
                                                                   <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_12.gif"><br>
                                                                    &nbsp;&nbsp;&nbsp;- 수강생 본인의 성적을 조회할 수 있습니다. <br>
                                                                    &nbsp;&nbsp;&nbsp;- 점수에 대한 의문이나 문제를 제기하고 싶을 때엔 개인면담 게시판을 이용하여 교수자와 면담을<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;할 수 있습니다.</td>
                                                                </tr>
															</table>
													  </td>
													</tr>
												</table>
                                                <!-- 동아리 -->
												<table width="630" align="center" id=subDivTab3 style="DISPLAY: none">
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct41_04.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
															<table width="100%" cellpadding="0" cellspacing="0">
																<tr> 
															      <td colspan="2" style="padding:15 0 0 0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_13.gif"></td>
                                                                </tr>
                                                                <tr> 
																   <td colspan="2">
                                                                    &nbsp;&nbsp;&nbsp;* 학생이 자율적으로 동아리를 개설하고 운영하는 곳입니다. 다양한 동아리 활동이 가능합니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;* 새로운 동아리는 누구라도 만들 수 있습니다. <br>
                                                                    &nbsp;&nbsp;&nbsp;* 단, 동아리의 생성에는 관리자의 승인이 필요합니다.</td>
                                                                </tr>
                                                                <tr valign="top"> 
																	<td width="165" style="padding:20 0 0 0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_14.gif"></td>
                                                                    <td width="430" style="padding:25 0 0 0"><span class="ctext02"><b>3.1 동아리 메뉴</b></span><br>
                                                                    <b>&nbsp;&nbsp;&nbsp;① 나의동아리 : </b>개설한 동아리와 가입한 동아리의 리스트를 볼 수 있으며,<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    리스트를 통해 동아리로 입장합니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;② 동아리 자랑 : </b>동아리를 소개하거나, 동아리를 자랑하는 글을 자유로이<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    올릴 수 있는 게시판입니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;③ 신규 동아리 : </b>최근에 만들어진 동아리 순대로 동아리에 대한 간단한 <br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    정보를 볼 수 있는 리스트입니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;④ 추천 동아리 : </b>회원들의 추천으로 선정된 추천 동아리들의 리스트를 <br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    볼 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑤ 동아리 만들기 : </b>새로운 동아리를 만들 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑥ 동아리 나가기 : </b>현재 동아리 페이지를 빠져나가 나의 학습실로 돌아<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;갑니다.<br></td>
																</tr>
                                                                <tr> 
																   <td colspan="2" style="padding:20 0 0 0"><span class="ctext02"><b>3.2 나의 동아리</b></span><br>
                                                                   <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_15.gif"><br>
                                                                    &nbsp;&nbsp;&nbsp;* 개설하거나 가입한 동아리의 리스트입니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;* 리스트에서 학생들이 개설한 동아리의 정보를 수정할 수 있으며, 본인이 가입한 동아리의 
                                                                    회원등급, <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;가입상태, 동아리의 정보 등을 볼 수 있습니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;* 동아리명을 클릭하면 해당 동아리의 페이지로 입장합니다.<br></td>
                                                                </tr>
                                                                <tr> 
																   <td colspan="2" style="padding:20 0 0 0"><span class="ctext02"><b>3.3 동아리 자랑</b></span><br>
                                                                    <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_16.gif"><br>
                                                                    &nbsp;&nbsp;&nbsp;* 자기 동아리를 소개하거나, 동아리를 자랑하는 글을 자유로이 올릴 수 있는 게시판입니다.<br></td>
                                                                </tr>
                                                                <tr> 
																   <td colspan="2" style="padding:20 0 0 0"><span class="ctext02"><b>3.4 신규 동아리</b></span><br>
                                                                    <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_17.gif"><br>
                                                                    &nbsp;&nbsp;&nbsp;* 신규로 개설된 동아리 순으로 나열된 동아리의 간단한 정보를 볼 수 있습니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;* 둘러보기를 클릭하면 <span class="ctext01"><b>[손님]</b></span>의 권한으로 동아리를 둘러볼 수 있습니다.</td>
                                                                </tr>
                                                                <tr> 
																   <td colspan="2" style="padding:20 0 0 0"><span class="ctext02"><b>3.5 추천 동아리</b></span><br>
                                                                    <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_18.gif"><br>
                                                                    &nbsp;&nbsp;&nbsp;* 회원등의 추천으로 추천 동아리로 지정된 동아리의 리스트를 볼 수 있습니다.<br>.<br>                                                                    </td>
                                                                </tr>
                                                                <tr> 
																   <td colspan="2" style="padding:20 0 0 0"><span class="ctext02"><b>3.6 동아리 만들기</b></span><br>
                                                                    <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_19.gif"><br>
                                                                    &nbsp;&nbsp;&nbsp;* 동아리를 개설합니다.<br>
                                                                    &nbsp;&nbsp;&nbsp;* 개설된 동아리는 관리자의 승인을 거쳐야 사용자들에게 공개됩니다. <br>                                                                    </td>
                                                                </tr>
															</table>
													  </td>
													</tr>
												</table>
												<!-- 내 동아리 관리 -->
												<table width="630" align="center" id=subDivTab4 style="DISPLAY: none">
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct41_05.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
															<table width="100%" cellpadding="0" cellspacing="0">
																<tr> 
															      <td colspan="2" style="padding:15 0 0 0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_20.gif"></td>
                                                              </tr>
                                                                <tr> 
																   <td colspan="2">
                                                                    &nbsp;&nbsp;&nbsp;* 동아리를 개설한 시샵이 동아리에 입장한 후의 메인 화면입니다. <br>
                                                                    &nbsp;&nbsp;&nbsp;* 동아리의 정보와 최근 게시물의 리스트를 볼 수 있습니다.</td>
                                                                </tr>
                                                                <tr valign="top"> 
																	<td width="165" style="padding:20 0 0 0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/guid2_21.gif"></td>
                                                                    <td width="430" style="padding:25 0 0 0"><span class="ctext02"><b>4.1 내 동아리 메뉴</b></span><br>
                                                                    <b>&nbsp;&nbsp;&nbsp;① 게시판 : </b>동아리의 공지사항, 자유게시판, 자료실, 동아리자랑 등의 게시<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;판을 사용할 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;② 회원보기 : </b>동아리의 회원정보를 볼 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;③ 개인정보수정 : </b>사용자 본인의 닉네임과 자기소개를 수정할 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;④ 초대하기 : </b>현 동아리에 다른 사람을 초대할 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑤ 동아리관리 - 회원관리 : </b>회원관리를 합니다. 회원정보의 삭제와 시샵 <br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    변경 등의 기능이 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑥ 동아리관리 - 게시판관리 : </b>사용할 게시판을 추가하거나 게시판의 설정<br>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                    &nbsp;을 관리합니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑦ 동아리관리 - 동아리 정보관리 : </b>동아리의 정보를 수정할 수 있습니다.<br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑧ 동아리관리 - 동아리 폐쇄신청 : </b>동아리의 폐쇄를 신청할 수 있습니다. <br>
                                                                    <b>&nbsp;&nbsp;&nbsp;⑨ 동아리 나가기 : </b>동아리를 나갑니다. <br></td>
																</tr>
															</table>
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
						<!-- 내용 끝 -->
											
					</td>
				</tr>
			</table>


<%@include file="../common/footer.jsp" %>
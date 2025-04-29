<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.common.dto.CodeParam,com.edutrack.common.dao.CommonDAO,com.edutrack.common.dto.CodeDTO"%>
<%@include file="../common/header.jsp" %>
<style>	
body, td , div ,p, input  { 
	font: 9pt 돋움, georgia; 
	color: #4E4E4E;  
	line-height:140%;
	margin: 0px;
	scrollbar-face-color: #ffffff;
    scrollbar-shadow-color: #ffffff;
    scrollbar-highlight-color: #FFFFFF;
    scrollbar-3dlight-color: #7D9FC5;
    scrollbar-darkshadow-color: #7D9FC5;
    scrollbar-track-color:#ffffff;
    scrollbar-arrow-color: #7D9FC5;
	selector-dummy : expression(this.hideFocus=true);
	
	}

a:link { 
	text-decoration: none; 
	color: #4F4F4F;
	}
a:visited { 
	text-decoration: none; 
	color: #4F4F4F;
	}
a:hover, a:active { 
	text-decoration: underline; 
	color: #4F4F4F;
	}

.left {
	border-top:0px 	;
	border-left:0;
	border-right:1px solid #FCCED6;
	border-bottom:0	 ;
	background:url(<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/icon/blet_r1.gif) no-repeat ;
	text-indent: 15px;
	height:20px;
	font-weight:bold;
	margin-left:6px;

}

.left-sub {
	border-top:0px 	;
	border-left:0;
	border-right:1px solid #FCCED6;
	border-bottom:0	 ;
	background:url(<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/icon/blet_r2.gif) no-repeat 15px 0 ;
	text-indent: 25px;
	height:20px;
	margin-left:6px;

}

.left-over {
	border-left:1px solid #FCCED6;
	border-top:1px solid #FCCED6;
	border-bottom:1px solid #FCCED6;
	border-right:0;
	background:#FEF5F5	url(<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/icon/blet_r2.gif) no-repeat 15px 0 ;
	margin-left:6px;
	text-indent: 25px;
}

.right {
	background:#FEF5F5	url(<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/icon/blet_r3.gif) no-repeat 10px 0;
	text-indent: 20px;
	height:20px;
	
}
</style>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/recommend_site/recommendSiteList.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/RecommendSiteWork.js"></script>


										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
										<%
										/** 관리자일경우에만 등록을 할 수 있다 */
										if(("M").equals(USERTYPE)){
										%>
										<tr>
											<td colspan="11" class="s_btn01">
												<table width="100%" align="center">
													<tr>
														<td>
															<div id="WriteDiv" Style='display:none'>
															<form name=Regist method="post">
															<input type=hidden name="pGUBUN"      value="write">
															<input type=hidden name="pMasterCode" value="">
															<input type=hidden name="pRecommCode" value="">
															<input type=hidden name="pRecommName" value="">
															<table width="670" align="center"  border=1>		
																<tr class="s_tab01">
																	<td colspan="2"></td>
																</tr>
																<tr>
																	<td class="s_tab_view01"> 1단계</td>
																	<td class="s_tab_view02">
																		<span id='dSel1' Style='display:show'>
																		<select name=pSelCode1 Style='width:200px' onChange="viewMasterCode('2')">
																			<option value="">- 선택 -</option>
																		</select>
																		</span>
																		<span id='dAddBut1' Style='display:show'>
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_a.gif" onClick="changeForm('1','write')" Style="cursor:hand" alt=추가 align="absmiddle">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_c.gif" onClick="changeForm('1','edit')"  Style="cursor:hand" alt=수정 align="absmiddle">
																		</span>
																		<span id='dRegBut1' Style='display:none'>
																		<input type=text name=pSelName1 size=50 notNull lenCheck=100 maxLength=100 dispName="1단계분류">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_e.gif"   onClick="registSite('1')"    Style="cursor:hand" alt=등록 align="absmiddle">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_d2.gif"  onClick="deleteSite('1')"    Style="cursor:hand" alt=삭제 align="absmiddle">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif"   onClick="changeForm('1','')" Style="cursor:hand" alt=취소 align="absmiddle">
																		<b>&nbsp;[최대 100 자까지]</b>
																		</span>
																	</td>
																</tr>
																<tr class="s_tab03">
																	<td colspan="2"></td>
																</tr>
																<tr>
																	<td class="s_tab_view01"> 2단계</td>
																	<td class="s_tab_view02">
																		<span id='dSel2' Style='display:show'>
																		<select name=pSelCode2 Style='width:200px' onChange="viewMasterCode('3')">
																			<option value="">- 선택 -</option>
																		</select>
																		</span>
																		<span id='dAddBut2' Style='display:show'>
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_a.gif" onClick="changeForm('2','write')" Style="cursor:hand" alt=추가 align="absmiddle">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_c.gif" onClick="changeForm('2','edit')"  Style="cursor:hand" alt=수정 align="absmiddle">
																		</span>
																		<span id='dRegBut2' Style='display:none'>
																		<input type=text name=pSelName2 size=50 notNull lenCheck=100 maxLength=100 dispName="2단계분류">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_e.gif"   onClick="registSite('2')"    Style="cursor:hand" alt=등록 align="absmiddle">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_d2.gif"  onClick="deleteSite('2')"    Style="cursor:hand" alt=삭제 align="absmiddle">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif"   onClick="changeForm('2','')" Style="cursor:hand" alt=취소 align="absmiddle">
																		<b>&nbsp;[최대 100 자까지]</b>
																		</span>
																	</td>
																</tr>
																<tr class="s_tab03">
																	<td colspan="2"></td>
																</tr>
																<tr>
																	<td class="s_tab_view01"> 3단계</td>
																	<td class="s_tab_view02">
																		<span id='dSel3' Style='display:'>
																		<select name=pSelCode3 Style='width:200px' onChange="viewMasterCode('4')">
																			<option value="">- 선택 -</option>
																		</select>
																		</span>
																		<span id='dAddBut3' Style='display:'>
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_a.gif" onClick="changeForm('3','write')" Style="cursor:hand" alt=추가 align="absmiddle">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_c.gif" onClick="changeForm('3','edit')"  Style="cursor:hand" alt=수정 align="absmiddle">
																		</span>
																		<span id='dRegBut3' Style='display:none'>
																		<input type=text name=pSelName3 size=50 notNull lenCheck=100 maxLength=100 dispName="3단계">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_e.gif"   onClick="registSite('3')"    Style="cursor:hand" alt=등록 align="absmiddle">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_d2.gif"  onClick="deleteSite('3')"    Style="cursor:hand" alt=삭제 align="absmiddle">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif"   onClick="changeForm('3','')" Style="cursor:hand" alt=취소 align="absmiddle">
																		<b>&nbsp;[최대 100 자까지]</b>
																		</span>
																	</td>
																</tr>
																<tr class="s_tab03">
																	<td colspan="2"></td>
																</tr>
																<tr>
																	<td class="s_tab_view01"> 추천 사이트</td>
																	<td class="s_tab_view02">
																		<span id='dSel4' Style='display:'>
																		<select name=pSelCode4 Style='width:200px' onChange="viewMasterCode('')">
																			<option value="">- 선택 -</option>
																		</select>
																		</span>
																		<span id='dAddBut4' Style='display:'>
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_a.gif" onClick="changeForm('4','write')" Style="cursor:hand" alt=추가 align="absmiddle">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_c.gif" onClick="changeForm('4','edit')"  Style="cursor:hand" alt=수정 align="absmiddle">
																		</span>
																		<span id='dRegBut4' Style='display:none'>
																		<input type=text name=pSelName4 size=50 notNull lenCheck=100 maxLength=100 dispName="추천사이트">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_e.gif"   onClick="registSite('4')"    Style="cursor:hand" alt=등록 align="absmiddle">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_d2.gif"  onClick="deleteSite('4')"    Style="cursor:hand" alt=삭제 align="absmiddle">
																		<img src="<%=CONTEXTPATH %>/img/<%=SYSTEMCODE%>/button_img/btn_d.gif"   onClick="changeForm('4','')" Style="cursor:hand" alt=취소 align="absmiddle">
																		<b>&nbsp;[최대 100 자까지]</b>
																		</span>
																	</td>
																</tr>
																<tr class="s_tab03">
																	<td colspan="2"></td>
																</tr>
																<tr>
																	<td class="s_tab_view01">사이트URL</td>
																	<td class="s_tab_view02">
																	    <input type="text" name="pSiteUrl" size=50 dispName="사이트URL" lenCheck=200 value="" dispName="사이트URL"><b>&nbsp;[최대 200 자까지]</b>
																	    <br>예시) http://www.junnodae.org 
																	</td>
																</tr>
																<tr class="s_tab03">
																	<td colspan="2"></td>
																</tr>
															</table>
															</form>
															</div>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr class="s_tab04">
											<td colspan="11" align=right>
												<script language=javascript>Button5("추천사이트 등록", "addForm('block')", "");</script>
												<Span id="CancelButDiv" Style='display:none'><script language=javascript>Button5("취소", "addForm('none')", "");</script></Span>
											</td>
										</tr>
										<% } //end if %>
										<tr class="s_tab01">
											<td colspan="11"></td>
										</tr>
										<tr>
											<td colspan="11">
											<div style="border-top:2px solid #CC3048 ;border-left:1px solid #DBDBDB ;border-right:1px solid #DBDBDB ;border-bottom:1px solid #DBDBDB ; padding:10px ; width:670px">
	
												<table  border="0" cellspacing="0" cellpadding="0">
													<!--tr>
														<td width=50% class="s_tab_view02"><div id="leftListDiv"></div></td>
														<td width=50% valign=top><div id="rightListDiv"></div></td>
													</tr-->
													<tr>
														<td valign=top><div style="width:340px;" id="leftListDiv"></div></td>
														<td  style="background:#FEF5F5 ;width:300px ;height:100%">
															<table style="border-right:1px solid #FCCED6 ;border-top:1px solid #FCCED6 ;border-bottom:1px solid #FCCED6 ;height:100% ;width:300px;" cellspacing="0" cellpadding="0" >
																<tr>
																	<td valign="top"><div id="rightListDiv" style="padding-top:10px ;padding-bottom:10px"></div></td>
																</tr>
															</table>
														</td>
												 	</tr>
												</table>
											</div>
											</td>
										</tr>
										<tr class="s_tab03">
											<td colspan="11"></td>
										</tr>
										<tr class="s_tab05">
											<td colspan="11"></td>
										</tr>

										<tr>
											<td class="s_list_btn" colspan="11" height="30" align="center" valign="middle">
											</td>
										</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						

<%@include file="../common/footer.jsp" %>


<Script Language=javascript>
    init("<%=SYSTEMCODE %>", "<%=CONTEXTPATH %>");
</Script>
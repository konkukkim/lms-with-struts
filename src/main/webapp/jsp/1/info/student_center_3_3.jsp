<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>
<script language="javascript">
<!--
	for (var k=5; k<=6; k++)
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
	href="<%=CONTEXTPATH%>/Main.cmd?cmd=StudentCenterShow&pMode=StudentCenter&pInfoNum2=3&pInfoNum3=1&MENUNO=0" onfocus='blur()'; onMouseOut="MM_swapImgRestore()" 
	onMouseOver="MM_swapImage('tab_01','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab61_01_on.gif',1)"><img 
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab61_01_off.gif" name="tab_01" width="91" height="26" border="0"></a></td>
<td width="91"><a 
	href="<%=CONTEXTPATH%>/Main.cmd?cmd=StudentCenterShow&pMode=StudentCenter&pInfoNum2=3&pInfoNum3=2&MENUNO=0" onfocus='blur()'; onMouseOut="MM_swapImgRestore()" 
	onMouseOver="MM_swapImage('tab_02','','<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab61_02_on.gif',1)"><img 
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab61_02_off.gif" name="tab_02" width="91" height="26" border="0"></a></td>
<td><img 
	src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tab61_03_on.gif" name="tab_03" width="94" height="26" border="0"></td>
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
																<tr><!-- changeDivMenuLayer(2,1)   changeDivMenuLayer(2,2) -->
<td width="134"><A onMouseOver="chng11('bookMenu5','bookMenu5B');chng11('bookMenu6','bookMenu6A');" href="#"><IMG 
	id=bookMenu5 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb61_05_on.gif" 
	width=131 border=0 name=bookMenu5></A></td>
<td><A onMouseOver="chng11('bookMenu5','bookMenu5A');chng11('bookMenu6','bookMenu6B');" href="#"><IMG 
	id=bookMenu6 height=25 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/tabb61_06_off.gif" 
	width=131 border=0 name=bookMenu6></A></td>
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
												<!-- 3학년 --> 
												<table width="630" align="center">
													<tr>
														<td class="ctit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/homepage/ct13_02.gif" width="204" height="13"></td>
													</tr>
													<tr>
														<td class="ctit_pd01">
														<div align="justify">
														준비중입니다..</div></td>
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
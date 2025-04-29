<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.curriresearch.dto.CurriResContentsDTO"%>
<%@include file="../common/course_header.jsp" %>
<% String pResId = (String)model.get("pResId");%>
<script language="javascript">
    function back_page(){
        var f = document.f;

        var loc="<%=CONTEXTPATH%>/ResearchResult.cmd?cmd=researchResultList";
        document.location = loc;
    }

    function Show_Answer(resno){
        var f = document.f;

        var resid = f.pResId.value;
        var loc="<%=CONTEXTPATH%>/ResearchResult.cmd?cmd=getResearchResultAnswer&pResId="+resid+"&pResNo="+resno;
        document.location = loc;
    }

    function Excel_ResultDown(){
        var f = document.f;

        f.action="<%=CONTEXTPATH%>/ResearchResult.cmd?cmd=getResearchResultExcel";
        f.target = "hiddenFrame";
        f.submit();
    }
</Script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"설문결과관리")%></b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									</td>
									<!-- // history -->
								</tr>

								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center" border="0">
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/ResearchResult.cmd?cmd=getResultExcelDown" >
<input type=hidden name="pResId" value="<%=pResId%>">

											<tr class="s_tab01">
												<td colspan="5"></td>
											</tr>
											<tr class="s_tab02">
												<td width="49">번호</td>
												<td width="1" class="s_tablien"></td>
												<td width="419">설문내용</td>
												<td width="1" class="s_tablien"></td>
												<td width="200">응답결과</td>
											</tr>

											<tr class="s_tab03">
												<td colspan="5"></td>
											</tr>

<%	ArrayList resultList = (ArrayList)model.get("resultList");
    int totSize = resultList.size();
    CurriResContentsDTO contents = null;
    String[] result = null;
    int[] checkCnt = null;
	int		No	=	0;

    if (totSize > 0) {

    	for (int i=0; i < resultList.size();i++) {
			No++;
    		contents = (CurriResContentsDTO)resultList.get(i);
            result = contents.getAnswer();
            checkCnt = contents.getCheckCnt();

            if (result == null) {
            	result = new String[]{"0","0","0","0","0","0","0","0","0","0","0"};
                checkCnt = new int[]{0,0,0,0,0,0,0,0,0,0,0};
            }

            // 선택형일경우
            if (contents.getContentsType().equals("K")) {
				if(No > 1) {
%>
											<tr class="s_tab03">
												<td colspan="5"></td>
											</tr>
<%				}	%>
											<!-- 문제1 -->
											<tr>
												<td class="s_tab04_cen"><%=i+1%></td>
												<td></td>
												<td class="s_tab04" colspan="3"><%=contents.getContents()%></td>
											</tr>
											<tr>
												<td colspan="2"></td>
												<td colspan="3" height="1" class="c_test_tablien01"></td>
											</tr>
<% 				if (!StringUtil.nvl(contents.getExample1()).equals("")) {	%>
											<tr>
												<td rowspan="<%=contents.getExampleCnt()%>" class="s_tab04_cen">&nbsp;</td>
												<td></td>
												<td class="s_tab04">1. <%=contents.getExample1()%></td>
												<td></td>
												<td class="s_tab04">
													<table height="17" width="200" align="center">
														<tr>
															<td width="130" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=Double.parseDouble(result[1])%>" height="13" align="absmiddle"></td>
															<td width="50" align="center"><font class="s_text03"><b><%=checkCnt[1]%></b></font>표(<%=result[1]%>)</td>
														</tr>
													</table>
												</td>
											</tr>
<% 				}	%>
<% 				if (!StringUtil.nvl(contents.getExample2()).equals("")) {	%>
											<tr>
												<td></td>
												<td class="s_tab04">2. <%=contents.getExample2()%></td>
												<td></td>
												<td class="s_tab04">
													<table height="17" width="200" align="center">
														<tr>
															<td width="130" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=Double.parseDouble(result[2])%>" height="13" align="absmiddle"></td>
															<td width="50" align="center"><font class="s_text03"><b><%=checkCnt[2]%></b></font>표(<%=result[2]%>)</td>
														</tr>
													</table>
												</td>
											</tr>
<% 				}	%>

<% 				if (!StringUtil.nvl(contents.getExample3()).equals("")) {	%>
											<tr>
												<td></td>
												<td class="s_tab04">3. <%=contents.getExample3()%></td>
												<td></td>
												<td class="s_tab04">
													<table height="17" width="200" align="center">
														<tr>
															<td width="130" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=Double.parseDouble(result[3])%>" height="13" align="absmiddle"></td>
															<td width="50" align="center"><font class="s_text03"><b><%=checkCnt[3]%></b></font>표(<%=result[3]%>)</td>
														</tr>
													</table>
												</td>
											</tr>
<% 				}	%>

<% 				if (!StringUtil.nvl(contents.getExample4()).equals("")) {	%>
											<tr>
												<td></td>
												<td class="s_tab04">4. <%=contents.getExample4()%></td>
												<td></td>
												<td class="s_tab04">
													<table height="17" width="200" align="center">
														<tr>
															<td width="130" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=Double.parseDouble(result[4])%>" height="13" align="absmiddle"></td>
															<td width="50" align="center"><font class="s_text03"><b><%=checkCnt[4]%></b></font>표(<%=result[4]%>)</td>
														</tr>
													</table>
												</td>
											</tr>
<% 				}	%>

<% 				if (!StringUtil.nvl(contents.getExample5()).equals("")) {	%>
											<tr>
												<td></td>
												<td class="s_tab04">5. <%=contents.getExample5()%></td>
												<td></td>
												<td class="s_tab04">
													<table height="17" width="200" align="center">
														<tr>
															<td width="130" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=Double.parseDouble(result[5])%>" height="13" align="absmiddle"></td>
															<td width="50" align="center"><font class="s_text03"><b><%=checkCnt[5]%></b></font>표(<%=result[5]%>)</td>
														</tr>
													</table>
												</td>
											</tr>
<% 				}	%>

<% 				if (!StringUtil.nvl(contents.getExample6()).equals("")) {	%>
											<tr>
												<td></td>
												<td class="s_tab04">6. <%=contents.getExample6()%></td>
												<td></td>
												<td class="s_tab04">
													<table height="17" width="200" align="center">
														<tr>
															<td width="130" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=Double.parseDouble(result[6])%>" height="13" align="absmiddle"></td>
															<td width="50" align="center"><font class="s_text03"><b><%=checkCnt[6]%></b></font>표(<%=result[6]%>)</td>
														</tr>
													</table>
												</td>
											</tr>
<% 				}	%>

<% 				if (!StringUtil.nvl(contents.getExample7()).equals("")) {	%>
											<tr>
												<td></td>
												<td class="s_tab04">7. <%=contents.getExample7()%></td>
												<td></td>
												<td class="s_tab04">
													<table height="17" width="200" align="center">
														<tr>
															<td width="130" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=Double.parseDouble(result[7])%>" height="13" align="absmiddle"></td>
															<td width="50" align="center"><font class="s_text03"><b><%=checkCnt[7]%></b></font>표(<%=result[7]%>)</td>
														</tr>
													</table>
												</td>
											</tr>
<% 				}	%>

<% 				if (!StringUtil.nvl(contents.getExample8()).equals("")) {	%>
											<tr>
												<td></td>
												<td class="s_tab04">8. <%=contents.getExample8()%></td>
												<td></td>
												<td class="s_tab04">
													<table height="17" width="200" align="center">
														<tr>
															<td width="130" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=Double.parseDouble(result[8])%>" height="13" align="absmiddle"></td>
															<td width="50" align="center"><font class="s_text03"><b><%=checkCnt[8]%></b></font>표(<%=result[8]%>))</td>
														</tr>
													</table>
												</td>
											</tr>
<% 				}	%>

<% 				if (!StringUtil.nvl(contents.getExample9()).equals("")) {	%>
											<tr>
												<td></td>
												<td class="s_tab04">9. <%=contents.getExample9()%></td>
												<td></td>
												<td class="s_tab04">
													<table height="17" width="200" align="center">
														<tr>
															<td width="130" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=Double.parseDouble(result[9])%>" height="13" align="absmiddle"></td>
															<td width="50" align="center"><font class="s_text03"><b><%=checkCnt[9]%></b></font>표(<%=result[9]%>)</td>
														</tr>
													</table>
												</td>
											</tr>
<% 				}	%>

<% 				if (!StringUtil.nvl(contents.getExample10()).equals("")) {	%>
											<tr>
												<td></td>
												<td class="s_tab04">10. <%=contents.getExample10()%></td>
												<td></td>
												<td class="s_tab04">
													<table height="17" width="200" align="center">
														<tr>
															<td width="130" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=Double.parseDouble(result[10])%>" height="13" align="absmiddle"></td>
															<td width="50" align="center"><font class="s_text03"><b><%=checkCnt[10]%></b></font>표(<%=result[10]%>)</td>
														</tr>
													</table>
												</td>
											</tr>
<%
				}
			//-- OX 일경우
			} else if(contents.getContentsType().equals("S")) {
				if(No > 1) {
%>
											<tr class="s_tab03">
												<td colspan="5"></td>
											</tr>
<%				}	%>
											<!-- 문제2 -->
											<tr>
												<td class="s_tab04_cen"><%=i+1%></td>
												<td></td>
												<td class="s_tab04"><%=contents.getContents()%></td>
												<td></td>
												<td class="s_tab04"></td>
											</tr>
											<tr>
												<td colspan="2"></td>
												<td colspan="3" height="1" class="c_test_tablien01"></td>
											</tr>
											<tr>
												<td rowspan="2" class="s_tab04_cen">&nbsp;</td>
												<td></td>
												<td class="s_tab04">맞음</td>
												<td></td>
												<td class="s_tab04">
													<table height="17" width="200" align="center">
														<tr>
															<td width="130" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=result[1]%>" height="13" align="absmiddle"></td>
															<td width="50" align="center"><font class="s_text03"><b><%=checkCnt[1]%></b>표(<%=result[1]%>)</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td></td>
												<td class="s_tab04">틀림</td>
												<td></td>
												<td class="s_tab04">
													<table height="17" width="200" align="center">
														<tr>
															<td width="130" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=Double.parseDouble(result[2])%>" height="13" align="absmiddle"></td>
															<td width="50" align="center"><font class="s_text03"><b><%=checkCnt[2]%></b>표(<%=result[2]%>)</td>
														</tr>
													</table>
												</td>
											</tr>
<%			//-- 단답식
			} else {
				if(No > 1) {
%>
											<tr class="s_tab03">
												<td colspan="5"></td>
											</tr>
<%				}	%>
											<tr>
												<td class="s_tab04_cen"><%=i+1%></td>
												<td></td>
												<td class="s_tab04"><%=contents.getContents()%></td>
												<td></td>
												<td class="s_tab04"><a href="javascript:Show_Answer('<%=contents.getResNo()%>')"><u>[서술형 결과보기]</u></a></td>
											</tr>
<%			}
		}	//end for
	} else {	%>
											<tr>
												<td class="s_tab04_cen" colspan="5"> 등록된 문제가 존재하지 않습니다. </td>
											</tr>
<%	}	%>

											<tr class="s_tab05">
												<td colspan="5"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="5" height="30" align="right">
<script language=javascript>Button3("목록", "back_page()", "");</script></td>
											</tr>
</form>
<!-- form end -->

										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->

<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@include file="../common/course_header.jsp" %>
<%
	String	pBbsType 	= (String)model.get("pBbsType");
	String	pVoiceYn 	= (String)model.get("pVoiceYn");
	String	pSearchKey 	= (String)model.get("pSearchKey");
	String	pKeyWord 	= (String)model.get("pKeyWord");
	int pNewTime		= Integer.parseInt((String)model.get("pNewTime"));
	int	pHotChk 		= Integer.parseInt((String)model.get("pHotChk"));
	int		curPage		= Integer.parseInt((String)model.get("curPage"));
	String 	bbsHot 		= "";
	String 	bbsNew		= "";
	String 	bbsTitleImg	= "ctit3_01.gif";

	String 	bbs = "";
	if(pBbsType.equals("notice")){
			bbs = "공지사항";
			bbsTitleImg	= "ctit3_01.gif";
	}else	if(pBbsType.equals("bbs")){
			bbs = "게시판";
			bbsTitleImg	= "ctit4_01.gif";
	}else	if(pBbsType.equals("qna")){
			bbs = "Q&A";
			bbsTitleImg	= "ctit6_01.gif";
	}else	if(pBbsType.equals("pds")){
			bbs = "자료실";
			bbsTitleImg	= "ctit5_01.gif";
	}

    boolean chkLogin = true;
 //  if(pLoginChkYn.equals("Y") && USERID.equals(""))	chkLogin = false;
%>

<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriBbsContentsWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_bbs/curriBbsContents.js"></script>


<script language="javascript">
	function goAdd()
	{
		document.location = "<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsWrite&pBbsType=<%=pBbsType%>";
	}
	function goPagingList()
	{
		document.location = "<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsPagingList&pBbsType=<%=pBbsType%>";
	}
	function goSearchList() {
		document.f.submit();
	}
</script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,bbs)%> </b></font></td>
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
									<!-- // history -->
								</tr>
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name="f">
<input type="hidden" name="curPage" value="<%=curPage%>">
<input type="hidden" name="pBbsType" value="<%=pBbsType%>">
<input type="hidden" name="pHotChk"	value="<%=pHotChk%>">
<input type="hidden" name="pAddDate" value="<%=DateTimeUtil.getAddDate(-1)%>">

											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="54">번호</td>
												<td class="s_tablien"></td>
												<td width="360">제목</td>
												<td class="s_tablien"></td>
												<td width="94">등록자</td>
												<td class="s_tablien"></td>
												<td width="94">등록일</td>
												<td class="s_tablien"></td>
												<td width="65">조회수</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
													<!-- 리스트 -->
														<div id="bbsList" style="width:100%;display:none"></div>
													<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<%	if(pBbsType.equals("notice") && (USERTYPE.equals("M") || USERTYPE.equals("P") || USERTYPE.equals("J"))) { %>
	<script language=javascript>Button3("글쓰기", "goAdd()", "");</script>
<%	} else if(!pBbsType.equals("notice")) { %>
	<script language=javascript>Button3("글쓰기", "goAdd()", "");</script>
<%	} %>
												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<table valign=top height="25">
														<tr>
															<td><!-- 페이징 -->
																<div id="getPagging" style="width:100%;display:none"></div></td>
														</tr>
													</table>
													<table>
														<tr>
															<td align=middle height=30>
																<select name=pSearchKey>
																	<option value=subject selected>제목</option>
																	<option value=keyword>내용</option>
																	<option value=reg_name>작성자</option>
																</select>
																<input maxlength=30 size=22 name=pKeyWord value="">
																<a href="javascript:goSearchList()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
															</td>
														</tr>
													</table>
												</td>
											</tr>
</form>
											<!-- // 페이지 리스트, 검색부분 -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<script language="javascript">
	list_init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>');
</script>
<%@include file="../common/course_footer.jsp" %>
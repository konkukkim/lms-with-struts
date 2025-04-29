<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.community.dto.CommBbsInfoDTO"%>
<%@include file="../common/community_header.jsp" %>
<%
    String pGubun = (String)model.get("pGubun");
	CommBbsInfoDTO bbsInfo = (CommBbsInfoDTO)model.get("bbsInfo");
	String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;
%>
<script language="javascript">
	function submitCheck() {
		var f = document.Input;

	 	if(!validate(f))
        	return ;
        f.submit();
	}

	function goList() {
		document.location = "<%=CONTEXTPATH%>/Community.cmd?cmd=commBbsManagePagingList";
	}
</script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>나의 동아리</b></font></td>
									<!-- // community title -->
									<!-- history -->
									<td class="com_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									</td>
								</tr>
								<tr valign="top">
									<td colspan="2" class="com_content_top">
									<!-- 내용 -->
									<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/Community.cmd?cmd=commBbsInfoRegist">
<input type="hidden" name="pGubun" value="<%=pGubun%>">
<input type="hidden" name="pBbsId" value="<%=bbsInfo.getBbsId()%>">
<input type="hidden" name="pBbsType" value="B">
<input type="hidden" name="pVoiceYn" value="N">

											<tr class="com_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02" width="92">게시판명</td>
												<td class="s_tab_view02"><input name="pBbsName" size="70" maxlength="100" dispName="게시판명" notNull lenCheck="100" value="<%=bbsInfo.getBbsName()%>"></td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02">리스트 라인 수</td>
												<td class="s_tab_view02"><input name="pDispLine" size="3" dispName="리스트 라인 수" dataType="number"  style="text-align:right" value="<%=bbsInfo.getDispLine()%>"> 줄</td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02">페이지 표시 수</td>
												<td class="s_tab_view02"><input name="pDispPage" size="3" dispName="페이지 표시 수" dataType="number"  style="text-align:right"  value="<%=bbsInfo.getDispPage()%>"> 페이지</td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02">파일첨부기능</td>
												<td class="s_tab_view02">
													<input type=radio name="pFileUseYn" value="Y" style="border:0" class="no" <% if(bbsInfo.getFileUseYn().equals("Y") || bbsInfo.getFileUseYn().equals("")){%>checked<%}%>> 사용 &nbsp;&nbsp;
                                                    <input type=radio name="pFileUseYn" value="N" style="border:0" class="no" <% if(bbsInfo.getFileUseYn().equals("N")){%>checked<%}%>> 미사용
												</td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02">첨부파일 최대크기</td>
												<td class="s_tab_view02"><input name="pFileLimit" size="3" dataType="number" dispName="첨부 파일 최대 크기" style="text-align:right" value="<%=bbsInfo.getFileLimit()%>"> 메가(mb)</td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02">웹에디터 사용</td>
												<td class="s_tab_view02">
													<input type=radio name="pEditorYn" value="Y" style="border:0" class="no" <% if(bbsInfo.getEditorYn().equals("Y")){%>checked<%}%>> 사용 &nbsp;&nbsp;
                                                    <input type=radio name="pEditorYn" value="N" style="border:0" class="no" <% if(bbsInfo.getEditorYn().equals("N") || bbsInfo.getEditorYn().equals("")){%>checked<%}%>> 미사용
												</td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02">Comment 사용여부</td>
												<td class="s_tab_view02">
													<input type=radio name="pCommentUseYn" value="Y" style="border:0" class="no" <% if(bbsInfo.getCommentUseYn().equals("Y") || bbsInfo.getCommentUseYn().equals("")){%>checked<%}%>> 사용 &nbsp;&nbsp;
													<input type=radio name="pCommentUseYn" value="N" style="border:0" class="no" <% if(bbsInfo.getCommentUseYn().equals("N")){%>checked<%}%>> 미사용
												</td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02">게시판 사용여부</td>
												<td class="s_tab_view02">
													<input type=radio name="pUseYn" value="Y" style="border:0" class="no" <% if(bbsInfo.getUseYn().equals("Y") || bbsInfo.getUseYn().equals("")){%>checked<%}%>> 사용 &nbsp;&nbsp;
                                                    <input type=radio name="pUseYn" value="N" style="border:0" class="no" <% if(bbsInfo.getUseYn().equals("N")){%>checked<%}%>> 미사용
												</td>
											</tr>
											<tr class="com_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
&nbsp;<script language=javascript>Button3("등록", "submitCheck()", "");</script>
												</td>
											</tr>

										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<script>
   document.Input.pBbsName.focus();
</script>
<%@include file="../common/community_footer.jsp" %>
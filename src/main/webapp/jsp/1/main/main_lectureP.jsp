<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@include file="../common/course_header.jsp" %>
<%

	String depthSpace = "";
	int chkCnt = 5;
%>


								<tr valign="top">
									<td height="100%" colspan="2" class="c_content_top">
									<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr>
												<!-- notice1 -->
												<td width="335" valign="top" style="padding-left: 7px;">
													<table cellspacing=0 cellpadding=0 width="94%" align="left">
														<tr>
															<td valign=top height=21><img height=16 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/notice_tit01.gif" width=250 ></td>
															<td valign="middle" align=right><a href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsPagingList&pBbsType=notice"><img height=13 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/button_more_brown.gif" width=44></a></td>
														</tr>
														<tr>
															<td colspan="2" height="1">
																<table cellspacing=0 cellpadding=0 width="100%" border=0>
																	<tr>
																		<td bgcolor=#dddddd></td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td style="padding-right: 0px; padding-left: 3px; padding-bottom: 0px" valign=top colspan=2 height="100%">
																<table cellspacing=0 cellpadding=1 width="100%" border=0>
<%
	RowSet noticeList = (RowSet)model.get("noticeList");

	while(noticeList.next()){
		depthSpace = "";
		chkCnt--;
		if(noticeList.getInt("depth_no") > 0)
			depthSpace = "▷";
		for(int j=0;j<noticeList.getInt("depth_no");j++)
			depthSpace = "&nbsp;"+depthSpace;
%>
																	<tr>
																		<td width="5%" valign="middle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet03.gif"></td>
																		<td width="80%" class="c_mbbs_lien"><a href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsShow&pBbsType=notice&pSeqNo=<%=noticeList.getInt("seq_no")%>&curPage=1"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(StringUtil.nvl(noticeList.getString("subject"))),50)%></a></td>
																		<td align=right width="15"% class="c_mbbs_lien1">[<%=DateTimeUtil.getDateType(1, noticeList.getString("reg_date"))%>]</td>
																	</tr>
																	<tr>
																		<td style="padding-left: 10px" colspan=3 background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/notice_dot_b.gif"></td>
																	</tr>
<%
	}
	for(int i=1;i<=chkCnt;i++){
%>
																	<tr>
																		<td width="5%" valign="middle"></td>
																		<td width="80%" class="c_mbbs_lien"></td>
																		<td align=right width="15"% class="c_mbbs_lien1"></td>
																	</tr>
																	<tr>
																		<td style="padding-left: 10px" colspan=3 background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/notice_dot_b.gif"></td>
																	</tr>
<%
	}
%>
																</table>
															</td>
														</tr>
													</table>
												</td>
												<!-- // notice1 -->
												<!-- notice2 -->
												<td width="335" valign="top" style="padding-right: 7px;">
													<table cellspacing=0 cellpadding=0 width="94%" align="right">
														<tr>
															<td valign=top height=21><img height=16 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/notice_tit02.gif" width=250 usemap=#tip border=0></td>
															<td valign="middle" align=right><a href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsPagingList&pBbsType=bbs"><img height=13 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/button_more_brown.gif" width=44></a></td>
														</tr>
														<tr>
															<td colspan="2" height="1">
																<table cellspacing=0 cellpadding=0 width="100%" border=0>
																	<tr>
																		<td bgcolor=#dddddd></td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td style="padding-right: 0px; padding-left: 3px; padding-bottom: 0px" valign=top colspan=2 height="100%">
																<table cellspacing=0 cellpadding=1 width="100%" border=0>
<%
	RowSet bbsList= (RowSet)model.get("bbsList");
	chkCnt = 5;
	while (bbsList.next()) {
		depthSpace = "";
		chkCnt--;
		if (bbsList.getInt("depth_no") > 0)
			depthSpace = "▷";
		for (int j=0; j<bbsList.getInt("depth_no"); j++)
			depthSpace = "&nbsp;"+depthSpace;
%>
																	<tr>
																		<td width="5%" valign="middle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet03.gif"></td>
																		<td width="80%" class="c_mbbs_lien"><a href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsShow&pBbsType=bbs&pSeqNo=<%=bbsList.getInt("seq_no")%>&curPage=1"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(StringUtil.nvl(bbsList.getString("subject"))),50)%></a></td>
																		<td align=right width="15"% class="c_mbbs_lien1">[<%=DateTimeUtil.getDateType(1, bbsList.getString("reg_date"))%>]</td>
																	</tr>
																	<tr>
																		<td style="padding-left: 10px" colspan=3 background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/notice_dot_b.gif"></td>
																	</tr>
<%
		}
		for (int i=1; i<=chkCnt; i++) {
%>
																	<tr>
																		<td width="5%" valign="middle"></td>
																		<td width="80%" class="c_mbbs_lien"></td>
																		<td align=right width="15"% class="c_mbbs_lien1"></td>
																	</tr>
																	<tr>
																		<td style="padding-left: 10px" colspan=3 background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/notice_dot_b.gif"></td>
																	</tr>
<%
	}
%>
																</table>
															</td>
														</tr>
													</table>
												</td>
												<!-- // notice2 -->
											</tr>
											<tr>
												<td height="40"></td>
											</tr>
											<tr>
												<!-- notice3 -->
												<td width="335" valign="top" style="padding-left: 7px;">
													<table cellspacing=0 cellpadding=0 width="94%" align="left">
														<tr>
															<td valign=top height=21><img height=16 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/notice_tit03.gif" width=250 ></td>
															<td valign="middle" align=right><a href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsPagingList&pBbsType=pds"><img height=13 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/button_more_brown.gif" width=44></a></td>
														</tr>
														<tr>
															<td colspan="2" height="1">
																<table cellspacing=0 cellpadding=0 width="100%" border=0>
																	<tr>
																		<td bgcolor=#dddddd></td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td style="padding-right: 0px; padding-left: 3px; padding-bottom: 0px" valign=top colspan=2 height="100%">
																<table cellspacing=0 cellpadding=1 width="100%" border=0>
<%
	RowSet pdsList= (RowSet)model.get("pdsList");
	chkCnt = 5;
	while (pdsList.next()) {
		depthSpace = "";
		chkCnt--;
		if (pdsList.getInt("depth_no") > 0)
			depthSpace = "▷";
		for (int j=0;j< pdsList.getInt("depth_no"); j++)
			depthSpace = "&nbsp;"+depthSpace;
%>
																	<tr>
																		<td width="5%" valign="middle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet03.gif"></td>
																		<td width="80%" class="c_mbbs_lien"><a href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsShow&pBbsType=pds&pSeqNo=<%=pdsList.getInt("seq_no")%>&curPage=1"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(StringUtil.nvl(pdsList.getString("subject"))),50)%></a></td>
																		<td align=right width="15"% class="c_mbbs_lien1">[<%=DateTimeUtil.getDateType(1, pdsList.getString("reg_date"))%>]</td>
																	</tr>
																	<tr>
																		<td style="padding-left: 10px" colspan=3 background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/notice_dot_b.gif"></td>
																	</tr>
<%
	}
	for (int i=1; i<=chkCnt; i++) {
%>
																	<tr>
																		<td width="5%" valign="middle"></td>
																		<td width="80%" class="c_mbbs_lien"></td>
																		<td align=right width="15"% class="c_mbbs_lien1"></td>
																	</tr>
																	<tr>
																		<td style="padding-left: 10px" colspan=3 background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/notice_dot_b.gif"></td>
																	</tr>
<%
	}
%>
																</table>
															</td>
														</tr>
													</table>
												</td>
												<!-- // notice3 -->
												<!-- notice4 -->
												<td width="335" valign="top" style="padding-right: 7px;">
													<table cellspacing=0 cellpadding=0 width="94%" align="right">
														<tr>
															<td valign=top height=21><img height=16 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/notice_tit04.gif" width=250 usemap=#tip border=0></td>
															<td valign="middle" align=right><a href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsPagingList&pBbsType=qna"><img height=13 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/button_more_brown.gif" width=44></a></td>
														</tr>
														<tr>
															<td colspan="2" height="1">
																<table cellspacing=0 cellpadding=0 width="100%" border=0>
																	<tr>
																		<td bgcolor=#dddddd></td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td style="padding-right: 0px; padding-left: 3px; padding-bottom: 0px" valign=top colspan=2 height="100%">
																<table cellspacing=0 cellpadding=1 width="100%" border=0>
<%
	RowSet qnaList= (RowSet)model.get("qnaList");
	chkCnt = 5;
	while (qnaList.next()) {
		depthSpace = "";
		chkCnt--;
		if (qnaList.getInt("depth_no") > 0)
			depthSpace = "▷";
		for (int j=0; j< qnaList.getInt("depth_no"); j++)
			depthSpace = "&nbsp;"+depthSpace;
%>
																	<tr>
																		<td width="5%" valign="middle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet03.gif"></td>
																		<td width="80%" class="c_mbbs_lien"><a href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsShow&pBbsType=bbs&pSeqNo=<%=qnaList.getInt("seq_no")%>&curPage=1"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(StringUtil.nvl(qnaList.getString("subject"))),50)%></a></td>
																		<td align=right width="15"% class="c_mbbs_lien1">[<%=DateTimeUtil.getDateType(1, qnaList.getString("reg_date"))%>]</td>
																	</tr>
																	<tr>
																		<td style="padding-left: 10px" colspan=3 background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/notice_dot_b.gif"></td>
																	</tr>
<%
	}
	for (int i=1; i<=chkCnt; i++) {
%>
																	<tr>
																		<td width="5%" valign="middle"></td>
																		<td width="80%" class="c_mbbs_lien"></td>
																		<td align=right width="15"% class="c_mbbs_lien1"></td>
																	</tr>
																	<tr>
																		<td style="padding-left: 10px" colspan=3 background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/notice_dot_b.gif"></td>
																	</tr>
<%
	}
%>
																</table>
															</td>
														</tr>
													</table>
												</td>
												<!-- // notice4 -->
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>
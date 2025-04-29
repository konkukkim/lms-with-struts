<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@ page import ="com.edutrack.currisub.dto.CurriContentsDTO,javax.sql.RowSet,com.edutrack.courseforum.dto.CourseForumContentsDTO"%>
<%@ page import ="com.edutrack.coursereport.dto.ReportInfoDTO,com.edutrack.courseexam.dto.ExamInfoDTO"%>
<%@ page import ="com.edutrack.resultcourse.dto.ResultCourseDTO"%>
<%@include file="../common/course_header.jsp" %>
<Script Language=javaScript>
function goStudy(){
	location.href = "<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecContentsList&MENUNO=0&pMode=<%=PMODE %>&pCourseId=<%=COURSEID%>&pCourseName=<%=COURSENAME%>";	
}
</Script>

							<tr valign="top"> 
								<!-- sub title -->
								<td height="34" width="302" class="c_stit_title"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/icon07.gif" align="absmiddle"><font face='돋움' size="3"><b> 강의실</b></font></td>
								<!-- // sub title -->
								<!-- history -->
								<td class="c_stit_history" valign="bottom" align="right" width="378"> 
<% 
		String NAVIGATION = "";
		if (model != null) NAVIGATION = (String)model.get("site_navigation");
		if (NAVIGATION != "") {
			out.println(NAVIGATION) ;
		} // end if
%>
								</td>
								<!-- // history -->
							</tr>
							<!--img -->
							<!--tr valign="top"> 
								<td colspan="2" class="c_img"></td>
							</tr-->
							<tr valign="top"> 
								<td colspan="2" class="c_content_top" valign="top"> 
									<!-- 내용 -->
										<!-- 학습현황 -->
										<table width="670" align="center">
											<tr>
												<td colspan="5" class="s_tabtit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" width="18" height="16" align="absmiddle"><b>학습현황</b></td>
											</tr>
										</table>
										<table width="670" align="center">
											<tr>
												<td width="329" class="c_stu01">
													<table cellspacing=0 cellpadding=0 width=300 border=0>
														<tr>
															<td width="300"><img height=5 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/layout_top.gif" width=300></td>
														</tr>
														<tr>
															<td align=middle background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/layout_back.gif">
																<table cellspacing=0 cellpadding=0 width="300" border=0>
																	<tr>
																		<td height=35 align="center">
																			<table cellspacing=0 cellpadding=0 width="85%" border=0>
																				<tr>
																					<td colspan="2" align="left"  height="40" valign="middle"  style="padding-left: 5px" ><a href="javascript:goStudy()"><img height=20 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/lecture_view.gif" width=74 border=0  alt=학습하기></A></td>
																				</tr>
<%
	//출석정보
	CurriContentsDTO attend = (CurriContentsDTO)model.get("attendView");
%>
																				<tr>
																					<td colspan="2" align="center"><strong><font color=#007c8a>총 <%=attend.getAllContentsCnt()%> 개의 강의중 <%=attend.getContentsCnt()%> 개를 학습중입니다. </font></strong></td>
																				</tr>
																				<tr>
																					<td colspan="2" height="5"></td>
																				</tr>
																				<tr>
																					<td align=middle bgcolor=#e1e1e1 height=1 colspan="2"></td>
																				</tr>
																			</table>
																		</td>
																	</tr>
																	<tr>
																		<td height=35 align="center">
																			<table cellspacing=0 cellpadding=0 width="85%" border=0>
																				<tr>
																					<td align="center"><font class="c_stu02">진도율 <%=attend.getProcessRate()%> %</font></td>
																					<td align=right width=1><font class="c_stu02"><strong>ㅣ</strong></font></td>
																					<td align="center"><font class="c_stu02">출석시간 : <%=attend.getTotalTime()%> 분</font></td>
																				</tr>
																				<tr>
																					<td colspan="3" height="5"></td>
																				</tr>
																				<tr>
																					<td align=middle bgcolor=#e1e1e1 height=1 colspan="3"></td>
																				</tr>
																			</table>
																		</td>
																	</tr>
																	<tr>
																		<td height=70>
																			<table cellspacing=0 cellpadding=0 width="70%" border=0 align="center">
<%
	int	statForum	=	0;
	String	linkForum	=	"";
	String	linkExam	=	"";
	String	linkReport	=	"";
	ArrayList flist = (ArrayList)model.get("forumList");
	CourseForumContentsDTO foruminfo = null;
	
	long curDate = Long.parseLong(CommonUtil.getCurrentDate());

	for(int i=0; i < flist.size(); i++){
		foruminfo = (CourseForumContentsDTO)flist.get(i);
		
		long startDt = Long.parseLong(StringUtil.nvl(foruminfo.getStartDate(), "0"));
		long endDt = Long.parseLong(StringUtil.nvl(foruminfo.getEndDate(), "0"));
		
		if(i == flist.size()-1 && startDt <= curDate && endDt >= curDate) {
			linkForum	=	"<a href=\"/CourseForumInfo.cmd?cmd=courseForumInfoPagingList&pCourseId="+foruminfo.getCourseId()+"&MENUNO=0\">진행중</a>";		
		} else {
			linkForum	=	"종료";
		}
		
		statForum++;
	}
	if (statForum==0) {
		linkForum	=	"없음";
	}
		

	String report_yn = "";
	ArrayList rlist = (ArrayList)model.get("reportList");
	ReportInfoDTO reportinfo = null;
	
	for(int i=0; i < rlist.size(); i++){
		reportinfo = (ReportInfoDTO)rlist.get(i);

		long startDt = Long.parseLong(StringUtil.nvl(reportinfo.getReportStartDate(), "0"));
		long endDt = Long.parseLong(StringUtil.nvl(reportinfo.getReportEndDate(), "0"));

		if(i == rlist.size()-1) {
			if(!reportinfo.getRegId().equals("N")) report_yn = "응시";
			else report_yn = "미응시";

			if (startDt <= curDate && endDt >= curDate) {
				if (linkReport.equals("")) {
					linkReport	=	"<a href=\"/ReportAdmin.cmd?cmd=reportStList&pCourseId="+reportinfo.getCourseId()+"&MENUNO=0\">진행중</a>";
				}
			}
			else if (endDt < curDate) {
				if (linkReport.equals("")) {
					linkReport	=	"종료";
				}
			}
		}
	
	}
	if (linkReport.equals("")) {
		linkReport	=	"없음";
	}
%>
																				<tr>
																					<!-- 토론방 -->
																					<td align=middle>
																						<table cellspacing=0 cellpadding=0 width=81 border=0>
																							<tr>
																								<td align=middle background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/layout01_top.gif"height=27>
																									<table cellspacing=0 cellpadding=0 width=81 border=0>
																										<tr>
																											<td width=28 align="right" style="padding-right: 3px"><img height=12 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/icon01.gif" width=13></td>
																											<td width="63"><strong><font color=#ffffff>토론방</font></strong></td>
																										</tr>
																									</table>
																								</td>
																							</tr>
																							<tr>
																								<td align=middle background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/layout01_middle.gif" height=18>
																									<table cellspacing=0 cellpadding=0 width=81 border=0>
																										<tr>
																											<td align="center"><%=linkForum %></td>
																										</tr>
																									</table>
																								</td>
																							</tr>
																							<tr>
																								<td><img height=5 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/layout01_bottom.gif" width=81></td>
																							</tr>
																						</table>
																					</td>
																					
																					<!-- 과제 -->
																					<td align=middle>
																						<table cellspacing=0 cellpadding=0 width=81 border=0>
																							<tr>
																								<td align=middle background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/layout01_top.gif"height=27>
																									<table cellspacing=0 cellpadding=0 width=81 border=0>
																										<tr>
																											<td width=33 align="right" style="padding-right: 3px"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/icon03.gif" width=13 height=13 ></td>
																											<td width="48"><strong><font color=#ffffff>과제</font></strong></td>
																										</tr>
																									</table>
																								</td>
																							</tr>
																							<tr>
																								<td align=middle background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/layout01_middle.gif" height=18>
																									<table cellspacing=0 cellpadding=0 width=81 border=0>
																										<tr>
																											<td align="center"><%=linkReport %></td>
																										</tr>
																									</table>
																								</td>
																							</tr>
																							<tr>
																								<td><img height=5 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/layout01_bottom.gif" width=81></td>
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
														<tr>
															<td><img height=5 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/layout_bottom.gif" width=300></td>
														</tr>
													</table>
												</td>
												<!-- 공지사항 -->
												<td style="padding:5 0 0 0" valign=top width=329>
													<table cellspacing=0 cellpadding=0 width="98%" align="left">
														<tr>
															<td valign=top height=21><img height=16 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/notice_tit01.gif" width=250 ></td>
															<td valign="middle" align=right><a href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsPagingList&pBbsType=notice&MENUNO=0&pMode=Lecture"><img height=13 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/button_more_brown.gif" width=44></a></td>
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
	ListDTO listObj = (ListDTO)model.get("noticeList");
	RowSet noticeList= listObj.getItemList();
	int num = 0;
	while (noticeList.next()) {
		num++;
%>
																	<tr>
																		<td width="5%" valign="middle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet03.gif"></td>
																		<td width="80%" class="c_mbbs_lien"><a href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsShow&pBbsType=notice&pSeqNo=<%=noticeList.getInt("seq_no")%>&curPage=1"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(StringUtil.nvl(noticeList.getString("subject"))),40)%></a></td>
																		<td align=right width="15"% class="c_mbbs_lien1">[<%=DateTimeUtil.getDateType(1,StringUtil.nvl(noticeList.getString("reg_date")))%>]</td>
																	</tr>
																	<tr>
																		<td style="padding-left: 10px" colspan=3 background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/notice_dot_b.gif"></td>
																	</tr>
<%
	}
	noticeList.close();
	if(num == 0) {
%>
																	<tr>
																		<td width="5%" valign="middle" align="center" height="27" colspan="3">등록된 공지사항이 없습니다.</td>
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
												<!-- // 공지사항 -->
											</tr>
										</table>
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="5" class="s_tabtit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" width="18" height="16" align="absmiddle"><b>성적</b></td>
											</tr>
										</table>
										<!-- 과목1 -->

										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="15"></td>
											</tr>
											<tr class="s_tab02">
												<td width="164">과목명</td>
												<td class="s_tablien"></td>
												<td width="55">출석</td>
												<td class="s_tablien"></td>
												<td width="55">토론</td>
												<td class="s_tablien"></td>
												<td width="60">리포트</td>
												<td class="s_tablien"></td>
												<td width="74">그룹학습</td>
												<td class="s_tablien"></td>
												<td width="92">오프라인 출석</td>
												<td class="s_tablien"></td>
												<td width="108">오프라인 동영상</td>
												<td class="s_tablien"></td>
												<td width="55">점수</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="15"></td>
											</tr>
<%
	ResultCourseDTO courseresult = (ResultCourseDTO)model.get("courseresult");
	for(int i=0; i < courseresult.getTotCnt(); i++){
		if(i > 0) {
%>
											<tr class="s_tab03">
												<td colspan="15"></td>
											</tr>
<%		}	%>
											<tr>
												<td class="s_tab04_cen"><strong><%=CommonUtil.getCourseName(SYSTEMCODE, courseresult.getCourseId1()[i])%></strong></td>
												<td></td>
												<td class="s_tab04_cen"><%=courseresult.getScoreAttend1()[i]%></td>
												<td></td>
												<td class="s_tab04_cen"><%=courseresult.getScoreForum1()[i]%></td>
												<td></td>
												<td class="s_tab04_cen"><%=courseresult.getScoreReport1()[i]%></td>
												<td></td>
												<td class="s_tab04_cen">10</td>
												<td></td>
												<td class="s_tab04_cen">0</td>
												<td></td>
												<td class="s_tab04_cen">0</td>
												<td></td>
												<td class="s_tab04_cen"><%=courseresult.getScoreTotal1()[i]%></td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="15"></td>
											</tr>
											<tr>
												<td colspan="15" height="15"></td>
											</tr>
										</table>
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->

<%@include file="../common/course_footer.jsp" %>
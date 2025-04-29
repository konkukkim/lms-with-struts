<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet, com.edutrack.coursereport.dto.ReportInfoDTO"%>
<%@include file="../common/course_header.jsp" %>
<%
    String pCourseId = (String)model.get("pCourseId");
    String pReportId = (String)model.get("pReportId");
    String pTeamReportId = (String)model.get("pTeamReportId");
    String pReportType = (String)model.get("pReportType");
%>
<script language="javascript">
    function Show_Result(resultyn, userid, teamreportid){
       var f = document.f;

       if(resultyn == "N"){
          alert("미제출자 및 미완료자는 채점결과를 볼 수 없습니다.");
          return;
       }
        var courseid = f.pCourseId.value;
        var reportid = f.pReportId.value;

	    var loc="<%=CONTEXTPATH%>/ReportResult.cmd?cmd=reportUserResult&pCourseId="+courseid+"&pReportId="+reportid+"&pUserId="+userid+"&pSubReportId="+teamreportid+"&pReportType=<%=pReportType%>";
		document.location = loc;
    }


   function Retest_Regist(){
  	 var f = document.f;
  	 var checkVal = getChkBoxByValue(f, "retest", "");

	 if(checkVal == "") {
	     alert("재제출할 사용자를 선택해 주세요.");
	     return;
	 }

	 var st = confirm('재제출 처리되면 이전의 제출정보가 없어집니다.\n\n 재제출 설정을 하시겠습니까?');
	 if (st == true) f.submit();
	 else return;
  }

  function goCancel(){
    var f = document.f;

    var courseid = f.pCourseId.value;

    var loc = "<%=CONTEXTPATH%>/ReportResult.cmd?cmd=reportResultList&pCourseId="+courseid;
    document.location = loc;
}

</script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"과제평가")%></b></font></td>
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
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/ReportAnswer.cmd?cmd=reportRetestEdit" >
<input type=hidden name="pCourseId" value="<%=pCourseId%>">
<input type=hidden name="pReportId"   value="<%=pReportId%>">
<input type=hidden name="pReportType"   value="<%=pReportType%>">
<input type=hidden name="checkYn"   value="N">
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<%		int			team_cnt	=	0;
		ArrayList	list		=	null;
		list	=	(ArrayList)model.get("TeamList");
		team_cnt = list.size();
		int num = 1;
		if(team_cnt > 0) {
			ReportInfoDTO reportinfo = null;
			for(int i=0; i < list.size(); i++){
				reportinfo = (ReportInfoDTO)list.get(i);	%>
											<tr>
												<td colspan="13" class="s_btn01">
													<b><%=num++%>팀 : <%=reportinfo.getSubject()%></b>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="13"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="130">이름(아이디)</td>
												<td class="s_tablien"></td>
												<td width="140">응시여부</td>
												<td class="s_tablien"></td>
												<td width="140">총점</td>
												<td class="s_tablien"></td>
												<td width="110">채점여부</td>
												<td class="s_tablien"></td>
												<td width="74">채점/결과</td>
												<td class="s_tablien"></td>
												<td width="74"><a href="javascript:setAllToken(f.retest)">재제출</a></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
<%					int No = 0;
					String apply = "";
					String mark = "";
					String resultYn = "N";
					String userId = "";
					RowSet userlist = (RowSet)model.get("userList"+i);
					if (userlist != null) {
						while(userlist.next()){
							userId = StringUtil.nvl(userlist.getString("user_id"));
							No++;
							if(No > 1) {
%>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
<%							}%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04"><%=StringUtil.nvl(userlist.getString("user_name"))%>(<%=userId%>)</td>
												<td></td>
<%							// 제출여부 판단
							if (StringUtil.nvl(userlist.getString("start_date")).equals("")) {
								apply = "<font color=\"red\">미제출</font>";
								resultYn = "N";
							} else {
								apply = "<font color=\"blue\">제출</font>";

								if (StringUtil.nvl(userlist.getString("end_date")).equals("")) {
									apply += "[<font color=\"red\">미완료</font>]";
									resultYn = "N";
								} else {
									resultYn = "Y";
								}
							}	%>
												<td class="s_tab04_cen"><%=apply%></td>
												<td></td>
												<td class="s_tab04_cen"><%=userlist.getInt("total_score")%>점</td>
												<td></td>
<%							if (StringUtil.nvl(userlist.getString("result_check")).equals("Y")) mark = "완료";
							else mark = "미완료";		%>
												<td class="s_tab04_cen"><%=mark%></td>
												<td></td>
												<td class="s_tab04_cen">
													<a href="javascript:Show_Result('<%=resultYn%>','<%=userId%>', '<%=userlist.getInt("sub_report_id")%>');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bttn_marking_result.gif" align="absmiddle" border="0"></a>
												</td>
												<td></td>
												<td class="s_tab04_cen"><input type="checkbox" name="retest" value="<%=userId%>" class=no></td>
											</tr>
<%
						}
						userlist.close();
						if(No == 0) {
%>
											<tr>
												<td class="s_tab04_cen" colspan="13">등록된 팀원이 없습니다.</td>
											</tr>
<%
						}
					}	%>

											<tr class="s_tab05">
												<td colspan="13"></td>
											</tr>
											<tr class="">
												<td colspan="13" height="20"></td>
											</tr>
<%
			}
		} else {%>
											<tr>
												<td class="s_tab04_cen" colspan="13">생성된 팀이 없습니다.</td>
											</tr>
<%		}	%>
											<tr>
												<td class="s_list_btn" colspan="13" height="30" align="right">
<script language=javascript>Button3("목록", "goCancel()", "");</script>
&nbsp;<script language=javascript>Button3("재제출설정", "Retest_Regist()", "");</script>
												</td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>
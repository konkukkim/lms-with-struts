<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/course_header.jsp" %>
<%
    String pCourseId = (String)model.get("pCourseId");
    String pReportId = (String)model.get("pReportId");
    String pReportSubject = (String)model.get("pReportSubject");

    long curDate = Long.parseLong(CommonUtil.getCurrentDate());
%>
<script language="javascript">

  //평가 입력 폼
  function goRise(userid,username,regional){
  	 var f = document.f;
  	 f.pUserId.value = userid;
  	 f.pRegional.value = regional;
  	 f.pUserName.value = username;
  	 f.action = "<%=CONTEXTPATH%>/ReportResult.cmd?cmd=reportProfSendWriteForm";
	 f.method = "post";
	 f.submit();

  }

  //점수 일괄 입력
  function addAllScore(){
	var cnt = 0;
	var cnt_01 = 0;
	var f = document.f;
	for(i=0;i<f.pScore.length;i++) {
		if(isNaN(f.pScore[i].value) || f.pScore[i].value == "")
		{
			cnt++;
        } else {
	        if(f.pScore[i].value > 100) {
	        	cnt_01++;
			}
		}
	}

	if(cnt > 0) {
		alert("숫자만 입력하세요.");
        f.pScore[i].value = 0;
        return;
	}

	if(cnt_01 > 0) {
		alert('100점 이내로 입력해 주세요');
		return;
	}
	if(confirm("점수를 일괄 등록 하시겠습니까?")) {
		f.action = "/ReportResult.cmd?cmd=reportResultAllScoreRegist";
		f.submit();
	}

  }

  //점수 일괄 업로드
  function goUpload() {
		var f = document.f;
		var pCourseId =  f.pCourseId.value;
		var pReportId =  f.pReportId.value;

		url = "/ReportResult.cmd?cmd=reportSendtUpLoadWrite&pCourseId="+pCourseId+"&pReportId="+pReportId;

		window.open(url, "upload", "toolbar=no,location=no, width=500, height=270,top=100, left=100, directories=no,status=no,menubar=no,scrollbars=yes,resizable=no");

	}

	//다운로드
	function goDown() {
		var f = document.f;
		var pCourseId =  f.pCourseId.value;
		var pReportId =  f.pReportId.value;
		var pReportSubject =  "<%=pReportSubject%>";
		document.location = "/ReportResult.cmd?cmd=reportResultDownLoad&pCourseId="+pCourseId+"&pReportId="+pReportId+"&pReportSubject="+pReportSubject;
	}

    //취소
    function goCancel(){
        var f = document.f;

        var courseid = f.pCourseId.value;

        var loc="/ReportAdmin.cmd?cmd=reportList&pCourseId="+courseid;
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
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/ReportAnswer.cmd?cmd=reportRetestEdit" >
<input type=hidden name="pCourseId" value="<%=pCourseId%>">
<input type=hidden name="pReportId"   value="<%=pReportId%>">
<input type=hidden name="pReportSubject"   value="<%=pReportSubject%>">
<input type=hidden name="pUserId"   value="">
<input type=hidden name="pUserName"   value="">
<input type=hidden name="pRegional"   value="">
<input type=hidden name="checkYn"   value="N">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td height=30><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" align="absmiddle" border="0"> <b>과제명 : <%=pReportSubject%></b></td>
															<td align=right></td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="100">성명</td>
												<td class="s_tablien"></td>
												<td width="100">학습자 ID</td>
												<td class="s_tablien"></td>
												<td width="100">제출여부</td>
												<td class="s_tablien"></td>
												<td width="100">점수</td>
												<td class="s_tablien"></td>
												<td width="">제출일자</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%	int No = 0;
	String apply = "";
	String mark = "";
	String resultYn = "N";
	String userId = "";
	String status = "";
	String regional = "";	//지연여부
	long endDt  = 0;
	long extendDt = 0;
	long stuModDt = 0;
	RowSet list = (RowSet)model.get("reportSendList");

	if (list != null) {
		while(list.next()){

		endDt = Long.parseLong(StringUtil.nvl(list.getString("report_end_date")));
		extendDt = Long.parseLong(StringUtil.nvl(list.getString("report_extend_date")));
		if(StringUtil.nvl(list.getString("stu_mod_date")).equals("")) {
			stuModDt = 0;
		} else {
			stuModDt = Long.parseLong(StringUtil.nvl(list.getString("stu_mod_date")));
		}


		if (endDt >= stuModDt && endDt != 0 && stuModDt != 0) {
			status = "<font color=\"blue\"> (정상제출)</font>";
			regional = "Y";
		} else if(stuModDt > endDt && stuModDt <= extendDt) {
			status = "<font color=\"red\"> (지연제출)</font>";
			regional = "N";
		} else {
			status = "";
			regional = "";
		}

		if(No >= 1) {
%>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%		}	%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=++No%></td>
												<td></td>
												<td class="s_tab04_cen"><a
													href="javascript:goRise('<%=StringUtil.nvl(list.getString("user_id"))%>','<%=StringUtil.nvl(list.getString("user_name"))%>','<%=regional%>')"><%=StringUtil.nvl(list.getString("user_name"))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("user_id"))%></td>
												<td></td>
<%			// 제출여부 판단
			if (StringUtil.nvl(list.getString("checkYn")).equals("")) {
				apply = "<font color=\"red\">미제출</font>";

			} else {
				apply = "<font color=\"blue\">제출</font>";
			}
%>
												<td class="s_tab04_cen"><%=apply%></td>
												<td></td>
												<td class="s_tab04_cen">
													<input type="text" name="pScore" value="<%=list.getInt("score")%>" size="3" maxlength="3" style="text-align: right" dispName="점수" notNull dataType = "number" tabIndex="1">점
													<!-- 점수 일괄 입력을 히든값 -->
													<input type=hidden name="pScoreUserId"   value="<%=StringUtil.nvl(list.getString("user_id"))%>">
													<input type=hidden name="pNullYn"  		 value="<%=StringUtil.nvl(list.getString("report_id"))%>">
												</td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("stu_mod_date")))%>  <%=status%></td>
											</tr>
<%		}

		list.close();
	}

	if (No < 1) {	%>

											<tr>
												<td class="s_tab04_cen" colspan="11">학습자가 존재하지 않습니다.</td>
											</tr>
<%	}	%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<script language=javascript>Button3("점수일괄적용", "addAllScore()", "");</script>
&nbsp;<script language=javascript>Button3("업로드", "goUpload()", "");</script>
&nbsp;<script language=javascript>Button3("다운로드", "goDown()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goCancel()", "");</script>
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

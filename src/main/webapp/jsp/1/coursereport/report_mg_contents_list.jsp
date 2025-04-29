<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.coursereport.dto.ReportContentsDTO,com.edutrack.coursereport.dto.ReportOrderDTO"%>
<%@include file="../common/course_header.jsp" %>
<%
    int score = 0;
    String contentsType = "";
    ReportOrderDTO order = null;
    ReportContentsDTO contents = null;
    ArrayList contentsList = null;
    String pCourseId = (String)model.get("pCourseId");
    String pReportId = (String)model.get("pReportId");
    String pTeamMode = (String)model.get("pTeamMode");
    String pParentReportId = (String)model.get("pParentReportId");
    String answerUserCnt = (String)model.get("answerUserCnt");
    ArrayList list = (ArrayList)model.get("contentsList");
    int totSize = list.size();
%>

<Script Language="javascript">
    function Create_Contents(){
       var f = document.f;

       f.pMODE.value = "write";
       f.submit();
    }

	function addContents(x){
	    var f = document.f;
		if(<%=answerUserCnt%> > 0){
			alert('<%=answerUserCnt%> 명의 수강생이 과제를 제출한 상태입니다 \n\n 문제 출제가 불가능합니다');
		}else{
		    f.pMODE.value="ADD";
		    f.pReportType.value=x;

			f.action="<%=CONTEXTPATH%>/ReportContents.cmd?cmd=reportContentsWrite";
			f.submit();
		}
	}

    function checkAnswer(){
       if(<%=answerUserCnt%> > 0){
   		alert('<%=answerUserCnt%> 명의 수강생이 과제를 제출한 상태입니다 \n\n 배점 조정이 불가능합니다');
		return false;
       }

       return true;
    }
    function Regist_Report(mode){
       var f = document.f;

       if(!checkAnswer()) return;

	   if(!validate(f)) return;

	   var totalScore = 0;
	 <%  for(int i=0; i < list.size();i++){
	     order = (ReportOrderDTO)list.get(i);
      %>
         totalScore += parseInt(f.score<%=order.getReportOrder()%>.value);
      <% } %>

 		 if(totalScore != 100){
			alert('총배점의 합계는 100 점을 기준으로 합니다.\n\n'
					+ '현재 총 배점은 '+ totalScore +' 점입니다. \n\n 100 점이 되어야 과제가 정상적으로 등록됩니다');
             return;
		  }

		if(totalScore == 100 && mode == "D"){
			var st = confirm('과제를 최종으로 등록합니다');
			if(st == false) return;
		}

	   f.pMODE.value=mode;
       f.action="<%=CONTEXTPATH%>/ReportContents.cmd?cmd=reportRegist";
       f.submit();
    }

	function report_win(contentssize){
	   var f = document.f;
	   var leftPosition = screen.width - 900;
	   var topPosition = screen.height - 660;

	   if(contentssize < 1){
         alert("문제를 먼저 등록해 주세요.");
         return;
       }

	   leftPosition = (leftPosition < 0) ? 0 : leftPosition/2;
	   topPosition  = (topPosition < 0)  ? 0 : topPosition /2;

	   var winOption = "left="+leftPosition+",top="+topPosition+",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=no,resizable=yes,width=900,height=660";

	   var courseid = f.pCourseId.value;
	   var reportid = f.pReportId.value;
	   var teamMode = "<%=pTeamMode%>";
       var parentReportId = "<%=pParentReportId%>";

	   var loc="<%=CONTEXTPATH%>/ReportContents.cmd?cmd=reportShow&pMODE=P&pCourseId="+courseid+"&pReportId="+reportid+"&pTeamMode="+teamMode+"&pParentReportId="+parentReportId;
	   ShowInfo = window.open(loc,"test",winOption);
	}

    function Show_ReportContents(reporttype, reportorder, reportno){
       var f = document.f;

       f.pReportType.value=reporttype;
       f.pReportOrder.value=reportorder;
       f.pReportNo.value=reportno;
       f.pMODE.value = "edit";
       f.submit();
    }

    function back_page(){
        var f = document.f;
        var courseid = f.pCourseId.value;
        var parentReportId = "<%=pParentReportId%>";

        if("<%=pTeamMode%>" == "") {
        	var loc="<%=CONTEXTPATH%>/ReportAdmin.cmd?cmd=reportList&pCourseId="+courseid;
        } else {
        	var loc = "/ReportAdmin.cmd?cmd=reportWrite&pCourseId="+courseid+"&pMODE=edit&pReportId="+parentReportId;
        }
        document.location = loc;
    }

    function add_bank_contents(){
        var f = document.f;

        if(!checkAnswer()) return;

        var courseid = f.pCourseId.value;
        var gubun = f.pGubun.value;
        var reportid = f.pReportId.value;
        var teamMode = "<%=pTeamMode%>";
        var parentReportId = "<%=pParentReportId%>";
        var loc="<%=CONTEXTPATH%>/ReportBank.cmd?cmd=reportBankContentsList&pCourseId="+courseid+"&pGubun="+gubun+"&pReportId="+reportid+"&pTeamMode="+teamMode+"&pParentReportId="+parentReportId;
        document.location = loc;
    }
</Script>
								<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"과제관리")%></b></font></td>
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
<form name="f" method="post" action="<%=CONTEXTPATH%>/ReportContents.cmd?cmd=reportContentsWrite" >
<input type="hidden" name="pMODE" value="">
<input type="hidden" name="pGubun" value="A">
<input type="hidden" name="pCourseId" value="<%=pCourseId%>">
<input type="hidden" name="pReportId" value="<%=pReportId%>">
<input type="hidden" name="pTeamMode" value="<%=pTeamMode%>">
<input type="hidden" name="pParentReportId" value="<%=pParentReportId%>">
<input type="hidden" name="pReportType" value="">
<input type="hidden" name="pReportOrder" value="">
<input type="hidden" name="pReportNo" value="">
<input type="hidden" name="answerUserCnt" value="<%=answerUserCnt%>">

											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" align="absmiddle"> 총 배점 <b>[100점]</b></select>
															</td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("문제은행보기", "add_bank_contents()", "");</script>
&nbsp;<script language=javascript>Button5("서술형", "addContents('J')", "");</script>
&nbsp;<script language=javascript>Button5("파일형", "addContents('F')", "");</script>
															</td>
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
												<td width="80">배점</td>
												<td class="s_tablien"></td>
												<td width="417">문제내용</td>
												<td class="s_tablien"></td>
												<td width="100">문제유형</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	if (totSize > 0) {

		for (int i=0; i < list.size();i++) {
			order = (ReportOrderDTO)list.get(i);
            contentsList = (ArrayList)order.getContentsList();

            if(order.getScore() < 1) score = 100/totSize;
            else score = order.getScore();

			if(i >= 1) {
%>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%			} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td colspan="11">
													<table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td width="70" class="s_tab04_cen" rowspan="<%=contentsList.size()%>"><%=order.getReportOrder()%></td>
															<td rowspan="<%=contentsList.size()%>"></td>
															<td width="80" class="s_tab04_cen" rowspan="<%=contentsList.size()%>">
																<input type="text" name="score<%=order.getReportOrder()%>" value="<%=score%>" size=5 notNull dataType="number" minValue="1" maxValue="100" dispName="<%=order.getReportOrder()%>번문제 배점">점
															</td>
															<td rowspan="<%=contentsList.size()%>"></td>
<%
			for (int j=0; j< contentsList.size(); j++) {

				if (j>0) out.println("\t\t\t\t<tr>");

				contents = (ReportContentsDTO)contentsList.get(j);
                contentsType	=	CommonUtil.getContentsTypeName(contents.getReportType());
%>
															<td width="417" class="s_tab04">
																<%=contents.getReportNo()%>.<a href="javascript:Show_ReportContents('<%=contents.getReportType()%>','<%=contents.getReportOrder()%>','<%=contents.getReportNo()%>');"><%=StringUtil.setMaxLength(contents.getContentsText(), 60)%></a>
															</td>
															<td></td>
															<td width="100" class="s_tab04_cen"><%=contentsType%></td>
<%
			}	 // for
%>
														</tr>
													</table>
												</td>
											</tr>
<%
		}	// for
%>
											<input type="hidden" name="pMaxOrder" value="<%=order.getReportOrder()%>">
<%
	}
	else {
%>
											<tr>
												<td class="s_tab04_cen" colspan="11">등록된 문제가 존재하지 않습니다.</td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<script language=javascript>Button3("배점주기", "Regist_Report('S')", "");</script>
&nbsp;<script language=javascript>Button3("미리보기", "report_win('<%=totSize%>')", "");</script>
&nbsp;<script language=javascript>Button3("과제등록", "Regist_Report('D')", "");</script>
&nbsp;<script language=javascript>Button3("목록", "back_page()", "");</script>
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
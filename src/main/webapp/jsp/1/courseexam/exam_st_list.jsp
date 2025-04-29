<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/course_header.jsp" %>
<%
    String pCourseId = (String)model.get("pCourseId");
    long curDate = Long.parseLong(CommonUtil.getCurrentDate());
%>
<script language="javascript">
    function Show_ExamContents(courseid,examid){
       var f = document.f;

       f.pExamId.value = examid;
       f.pCourseId,value=courseid;
       f.action="<%=CONTEXTPATH%>/ExamContents.cmd?cmd=examContentsList";
       f.submit();
    }

	function Show_Exam(courseid, examid,estartdt, eenddt,astartdt, aenddt, resultdt, resultcheck){
	   var f = document.f;
	   var curdt = parseFloat(f.curDate.value);
	   var loc = "";
	   var winOption = "";

	   // 시험기간이전인지, 내인지, 지났는지를 확인한다.
	   if(curdt < parseFloat(estartdt)){ // 시험기간 이전
	       alert("시험기간 중이 아닙니다.");
	       return;
	   }else{
	      if(curdt > parseFloat(eenddt)){
                if(curdt > parseFloat(resultdt)){
                     if(resultcheck == "N"){
			          alert("채점이 완료되지 않았습니다.");
			          return;
                     }else{
					  var winOption = "left="+windowLeftPosition(700)+",top="+windowTopPosition(640)+",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=yes,resizable=no,width=700,height=660";

                      // 시험 결과를 보여준다.
                      loc="<%=CONTEXTPATH%>/ExamResult.cmd?cmd=examUserResult&pUserId=<%=USERID%>&pCourseId="+courseid+"&pExamId="+examid;
                     }
		        }else{
		          alert("시험기간이 지났습니다. 결과확인일 이후에 결과를 확인하세요.");
		          return;
		        }
	      }else{
	          if(astartdt != "" ){
	              alert("이미 시험을 보셨습니다. 결과확인일 이후에 결과를 확인해 주세요.");
	              return;
	          }else{
				  var winOption = "left="+windowLeftPosition(900)+",top="+windowTopPosition(660)+",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=no,resizable=yes,width=900,height=660";

	              // 시험을 보러 들어간다.
	              loc="<%=CONTEXTPATH%>/ExamContents.cmd?cmd=examShow&pMODE=R&pCourseId="+courseid+"&pExamId="+examid;
	          }
	      }
	   }

	   if(loc == "") return;

	   ShowInfo = window.open(loc,"test",winOption);

    }

    function Show_ExamInfo(courseid,examid){
	   var f = document.f;
	   var loc="<%=CONTEXTPATH%>/ExamAdmin.cmd?cmd=examStShow&pCourseId="+courseid+"&pExamId="+examid;
	   ShowInfo = window.open(loc,"test","left=0,top=0,toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=yes,resizable=no,width=600,height=450");
    }

    function Change_Course(){
       var f = document.f;
       f.action="<%=CONTEXTPATH%>/ExamAdmin.cmd?cmd=examStList";
       f.submit();
    }
</script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"시험")%></b></font></td>
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
<form name="f" method="post" action="<%=CONTEXTPATH%>/ExamAdmin.cmd?cmd=examWrite" >
<input type=hidden name="pMODE" value="">
<input type=hidden name="pExamId" value="">
<input type=hidden name="curDate" value="<%=curDate%>">
<input type=hidden name="pCourseId" value="">
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
<%	if (COURSELISTSIZE > 1) {	%>
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="100">과목명</td>
												<td class="s_tablien"></td>
												<td width="225">시험명(정보)</td>
												<td class="s_tablien"></td>
												<td width="150">시험기간</td>
												<td class="s_tablien"></td>
												<td width="75">시간제한</td>
												<td class="s_tablien"></td>
												<td width="75">응시/결과</td>
<%	} else {	%>
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="326">시험명(정보)</td>
												<td class="s_tablien"></td>
												<td width="150">시험기간</td>
												<td class="s_tablien"></td>
												<td width="75">시간제한</td>
												<td class="s_tablien"></td>
												<td width="75">응시/결과</td>
<%	}	%>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%	int No = 0;
	long startDt = 0;
	long endDt = 0;
    RowSet list = (RowSet)model.get("examList");
	String	examStr	=	"";

    if (list != null) {

    	while (list.next()) {
    	    
    	    examStr	=	"";
    	    
			No++;
			if(No > 1) {
%>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%			} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
<%			if (COURSELISTSIZE > 1) {	%>
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("course_name"))%></td>
												<td></td>
												<td class="s_tab04">
													<a href="javascript:Show_ExamInfo('<%=StringUtil.nvl(list.getString("course_id"))%>','<%=list.getInt("exam_id")%>');"><%=StringUtil.nvl(list.getString("subject"))%>
<%				startDt = Long.parseLong(StringUtil.nvl(list.getString("start_date")));
		        endDt = Long.parseLong(StringUtil.nvl(list.getString("end_date")));

		        if (startDt <= curDate && endDt >= curDate) {	%>
		              								<font color=blue>(시험진행중)</font>
<%				} else if (endDt < curDate) {	%>
													<font color=blue>(시험종료)</font>
<%				}	%>
												</td>
												<td></td>
												<td class="s_tab04_cen">
													<%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("start_date")))+" ~ "+DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("end_date")))%>
												</td>
												<td></td>
												<td class="s_tab04_cen">
<%				if (list.getString("flag_time").equals("Y")) {	%>
													<%=list.getInt("run_time")%>분</td>
<%				} else {	%>
													제한없음
<%				}	%>
												</td>
<%
				if(StringUtil.nvl(list.getString("astart_date")).equals("") && StringUtil.nvl(list.getString("result_check")).equals("N")) {
					examStr	=	"<b><font color='#FF6600'>[시험응시]</font></b>";
				}
				else if(!StringUtil.nvl(list.getString("astart_date")).equals("") && StringUtil.nvl(list.getString("result_check")).equals("N")) {
					examStr	=	"<b><font color='#66CC66'>[응시완료]</font></b>";
				}
				else if(!StringUtil.nvl(list.getString("astart_date")).equals("") && StringUtil.nvl(list.getString("result_check")).equals("Y")) {
					examStr	=	"<b><font color='#3399BB'>[결과보기]</font></b>";
				}
%>
												<td></td>
												<td class="s_tab04_cen"><a href="javascript:Show_Exam('<%=StringUtil.nvl(list.getString("course_id"))%>','<%=list.getInt("exam_id")%>','<%=StringUtil.nvl(list.getString("start_date"))%>','<%=StringUtil.nvl(list.getString("end_date"))%>','<%=StringUtil.nvl(list.getString("astart_date"))%>','<%=StringUtil.nvl(list.getString("aend_date"))%>','<%=StringUtil.nvl(list.getString("result_date"))%>','<%=StringUtil.nvl(list.getString("result_check"))%>');"><%=examStr%></a></td>
<%			} else {	%>
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04_cen">
													<a href="javascript:Show_ExamInfo('<%=StringUtil.nvl(list.getString("course_id"))%>','<%=list.getInt("exam_id")%>');"><%=StringUtil.nvl(list.getString("subject"))%>
<%				startDt = Long.parseLong(StringUtil.nvl(list.getString("start_date")));
		        endDt = Long.parseLong(StringUtil.nvl(list.getString("end_date")));

		        if (startDt <= curDate && endDt >= curDate) {	%>
		              								<font color=blue>(시험진행중)</font>
<%				} else if (endDt < curDate) {	%>
													<font color=blue>(시험종료)</font>
<%				}	%>
												</td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("start_date")))+" ~ "+DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("end_date")))%></td>
												<td></td>
												<td class="s_tab04_cen">
<%				if (list.getString("flag_time").equals("Y")) {	%>
													<%=list.getInt("run_time")%>분</td>
<%				} else {	%>
													제한없음
<%				}	%>
												</td>
												<td></td>
<%
				if(StringUtil.nvl(list.getString("astart_date")).equals("") && StringUtil.nvl(list.getString("result_check")).equals("N")) {
					examStr	=	"<b><font color='#FF6600'>[시험응시]</font></b>";
				}
				else if(!StringUtil.nvl(list.getString("astart_date")).equals("") && StringUtil.nvl(list.getString("result_check")).equals("N")) {
					examStr	=	"<b><font color='#66CC66'>[응시완료]</font></b>";
				}
				else if(!StringUtil.nvl(list.getString("astart_date")).equals("") && StringUtil.nvl(list.getString("result_check")).equals("Y")) {
					examStr	=	"<b><font color='#3399BB'>[결과보기]</font></b>";
				}

%>												
												<td class="s_tab04_cen">
													<a href="javascript:Show_Exam('<%=StringUtil.nvl(list.getString("course_id"))%>','<%=list.getInt("exam_id")%>','<%=StringUtil.nvl(list.getString("start_date"))%>','<%=StringUtil.nvl(list.getString("end_date"))%>','<%=StringUtil.nvl(list.getString("astart_date"))%>','<%=StringUtil.nvl(list.getString("aend_date"))%>','<%=StringUtil.nvl(list.getString("result_date"))%>','<%=StringUtil.nvl(list.getString("result_check"))%>');"><%=examStr%></a>
												</td>
<%			}	%>
											</tr>

<%		}

		list.close();
	}

	if (No < 1) {	%>
											<tr>
												<td class="s_tab04_cen" colspan="11">시험 리스트가 존재하지 않습니다.</td>
											</tr>
<%	}	%>
											<tr class="s_tab05">
												<td colspan="11"></td>
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

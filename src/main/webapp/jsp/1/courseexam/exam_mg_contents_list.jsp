<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.courseexam.dto.ExamContentsDTO,com.edutrack.courseexam.dto.ExamOrderDTO"%>
<%@include file="../common/course_header.jsp" %>
<%
    int score = 0;
    String contentsType = "";
    ExamOrderDTO order = null;
    ExamContentsDTO contents = null;
    ArrayList contentsList = null;
    String pCourseId = (String)model.get("pCourseId");
    String pExamId = (String)model.get("pExamId");
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
			alert('<%=answerUserCnt%> 명의 응시자가 시험에 응시한 상태입니다 \n\n 문제 출제가 불가능합니다');
		}else{
		    f.pMODE.value="ADD";
		    f.pExamType.value=x;

			f.action="<%=CONTEXTPATH%>/ExamContents.cmd?cmd=examContentsWrite";
			f.submit();
		}
	}

    function checkAnswer(){
       if(<%=answerUserCnt%> > 0){
   		alert('<%=answerUserCnt%> 명의 응시자가 시험에 응시한 상태입니다 \n\n 배점 조정이 불가능합니다');
		return false;
       }

       return true;
    }
    function Regist_Exam(mode){
       var f = document.f;

       if(!checkAnswer()) return;

	   if(!validate(f)) return;

	   var totalScore = 0;
	 <%  for(int i=0; i < list.size();i++){
	     order = (ExamOrderDTO)list.get(i);
      %>
         totalScore += parseInt(f.score<%=order.getExamOrder()%>.value);
      <% } %>

 		 if(totalScore != 100){
			alert('총배점의 합계는 100 점을 기준으로 합니다.\n\n'
					+ '현재 총 배점은 '+ totalScore +' 점입니다.	\n\n 100 점이 되어야 시험이 정상적으로 등록됩니다');
             return;
		  }

		if(totalScore == 100 && mode == "D"){
			var st = confirm('시험을 최종으로 등록합니다');
			if(st == false) return;
		}

	   f.pMODE.value=mode;
       f.action="<%=CONTEXTPATH%>/ExamContents.cmd?cmd=examRegist";
       f.submit();
    }

	function exam_win(contentssize){
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
	   var examid = f.pExamId.value;

	   var loc="<%=CONTEXTPATH%>/ExamContents.cmd?cmd=examShow&pMODE=P&pCourseId="+courseid+"&pExamId="+examid;
	   ShowInfo = window.open(loc,"test",winOption);
	}

    function Show_ExamContents(examtype, examorder, examno){
       var f = document.f;

       f.pExamType.value=examtype;
       f.pExamOrder.value=examorder;
       f.pExamNo.value=examno;
       f.pMODE.value = "edit";
       f.submit();
    }

    function back_page(){
        var f = document.f;

        var courseid = f.pCourseId.value;

        var loc="<%=CONTEXTPATH%>/ExamAdmin.cmd?cmd=examList&pCourseId="+courseid;
        document.location = loc;
    }

    function add_bank_contents(){
        var f = document.f;

        if(!checkAnswer()) return;

        var courseid = f.pCourseId.value;
        var gubun = f.pGubun.value;
        var examid = f.pExamId.value;
        var loc="<%=CONTEXTPATH%>/ExamBank.cmd?cmd=examBankContentsList&pCourseId="+courseid+"&pGubun="+gubun+"&pExamId="+examid;
        document.location = loc;
    }
</Script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"시험출제")%></b></font></td>
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
<form name="f" method="post" action="<%=CONTEXTPATH%>/ExamContents.cmd?cmd=examContentsWrite" >
<input type=hidden name="pMODE"         value="">
<input type=hidden name="pGubun"        value="A">
<input type=hidden name="pCourseId"     value="<%=pCourseId%>">
<input type=hidden name="pExamId"       value="<%=pExamId%>">
<input type=hidden name="pExamType"     value="">
<input type=hidden name="pExamOrder"    value="">
<input type=hidden name="pExamNo"       value="">
<input type=hidden name="answerUserCnt" value="<%=answerUserCnt%>">

											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" align="absmiddle" border="0">
															총 배점 <b>[100점]</b>
															</td>
															<td align=right height=30>
<script language=javascript>Button5("문제은행보기", "add_bank_contents()", "");</script>
&nbsp;<script language=javascript>Button5("서술형", "addContents('J')", "");</script>
&nbsp;<script language=javascript>Button5("선택형", "addContents('K')", "");</script>
&nbsp;<script language=javascript>Button5("단답형", "addContents('D')", "");</script>
&nbsp;<script language=javascript>Button5("OX형", "addContents('S')", "");</script>
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
												<td width="80">문제유형</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%	if (totSize > 0) {

		for (int i=0; i < list.size();i++) {
			order = (ExamOrderDTO)list.get(i);
            contentsList = (ArrayList)order.getContentsList();

            if (order.getScore() < 1)
            	score = 100/totSize;

            else
            	score = order.getScore();
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
															<td width="70" class="s_tab04_cen" rowspan="<%=contentsList.size()%>"><%=order.getExamOrder()%></td>
															<td rowspan="<%=contentsList.size()%>"></td>
															<td width="80" class="s_tab04_cen" rowspan="<%=contentsList.size()%>">
																<input type="text" name="score<%=order.getExamOrder()%>" value="<%=score%>" size=5 notNull dataType="number" minValue="1" maxValue="100" dispName="<%=order.getExamOrder()%>번문제 배점">점
															</td>
															<td rowspan="<%=contentsList.size()%>"></td>
<%			for (int j=0; j< contentsList.size(); j++) {

            	if (j>0)
            		out.println("\t\t\t\t<tr>");

                contents = (ExamContentsDTO)contentsList.get(j);
                contentsType	=	CommonUtil.getContentsTypeName(contents.getExamType());	%>
															<td width="417" class="s_tab04">
																<%=contents.getExamNo()%>.<a href="javascript:Show_ExamContents('<%=contents.getExamType()%>','<%=contents.getExamOrder()%>','<%=contents.getExamNo()%>');"><%=StringUtil.setMaxLength(contents.getContentsText(), 60)%></a>
															</td>
															<td></td>
															<td width="100" class="s_tab04_cen"><%=contentsType%></td>
<%			}	%>
														</tr>
													</table>
												</td>
											</tr>
<%		}	%>
                        							<input type=hidden name="pMaxOrder" value="<%=order.getExamOrder()%>">
<%	} else {	%>
											<tr>
												<td class="s_tab04_cen" colspan="11">등록된 문제가 존재하지 않습니다.</td>
											</tr>
<%	}	%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<script language=javascript>Button3("배점주기", "Regist_Exam('S')", "");</script>
&nbsp;<script language=javascript>Button3("미리보기", "exam_win('<%=totSize%>')", "");</script>
&nbsp;<script language=javascript>Button3("시험등록", "Regist_Exam('D')", "");</script>
&nbsp;<script language=javascript>Button3("목록", "back_page()", "");</script>
												</td>
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
<%@include file="../common/course_footer.jsp" %>






































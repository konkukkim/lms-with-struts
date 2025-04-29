<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/course_header.jsp" %>
<%
    String pCourseId = (String)model.get("pCourseId");
    String pExamId = (String)model.get("pExamId");
%>
<script language="javascript">
    function Show_Result(resultyn, userid){
       var f = document.f;

       if(resultyn == "N"){
          alert("미응시자 및 미완료자는 채점결과를 볼 수 없습니다.");
          return;
       }

        var courseid = f.pCourseId.value;
        var examid = f.pExamId.value;

	    var loc="<%=CONTEXTPATH%>/ExamResult.cmd?cmd=examUserResult&pCourseId="+courseid+"&pExamId="+examid+"&pUserId="+userid;
		document.location = loc;
    }

	function checkAll(){
      var f = document.f;
      if(f.checkYn.value == "Y") {
        f.checkYn.value = "N";
    	setChkboxAll(f, "retest",false);
      }else{
        f.checkYn.value = "Y";
    	setChkboxAll(f, "retest", true);
      }
   }

   function Retest_Regist(){
  	 var f = document.f;
  	 var checkVal = getChkBoxByValue(f, "retest", "");

	 if(checkVal == "") {
	     alert("재시험 볼 사용자를 선택해 주세요.");
	     return;
	 }

	 var st = confirm('재시험 처리되면 이전의 시험정보가 없어집니다.\n\n 재시험 설정을 하시겠습니까?');
	 if (st == true) f.submit();
	 else return;
  }

  function goCancel(){
    var f = document.f;

    var courseid = f.pCourseId.value;

    var loc = "<%=CONTEXTPATH%>/ExamResult.cmd?cmd=examResultList&pCourseId="+courseid;
    document.location = loc;
}

</script>
								<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"시험평가")%></b></font></td>
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
<form name="f" method="post" action="<%=CONTEXTPATH%>/ExamAnswer.cmd?cmd=examRetestEdit" >
<input type=hidden name="pCourseId" value="<%=pCourseId%>">
<input type=hidden name="pExamId"   value="<%=pExamId%>">
<input type=hidden name="checkYn"   value="N">

											<tr class="s_tab01">
												<td colspan="15"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="">이름(아이디)</td>
												<td class="s_tablien"></td>
												<td width="80">응시여부</td>
												<td class="s_tablien"></td>
												<td width="60">총점</td>
												<td class="s_tablien"></td>
												<td width="80">채점여부</td>
												<td class="s_tablien"></td>
												<td width="80">채점/결과</td>
												<td class="s_tablien"></td>
												<td width="80">재제출</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="15"></td>
											</tr>
<%	int No = 0;
  	String apply = "";
  	String mark = "";
  	String resultYn = "N";
  	String userId = "";
  	RowSet list = (RowSet)model.get("userList");

  	if (list != null) {

  		while(list.next()) {
      		userId = StringUtil.nvl(list.getString("user_id"));
			No++;

			if(No > 1) {
%>
											<tr class="s_tab03">
												<td colspan="15"></td>
											</tr>
<%			} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("user_name"))%>(<%=userId%>)</td>
												<td></td>
<%	//-- 응시여부 판단
		    if (StringUtil.nvl(list.getString("start_date")).equals("")) {
		    	apply = "<font color=\"red\">미응시</font>";
		     	resultYn = "N";
		    } else {
		    	apply = "<font color=\"blue\">응시</font>";

		        if (StringUtil.nvl(list.getString("end_date")).equals("")) {
		        	apply += "[<font color=\"red\">미완료</font>]";
		            resultYn = "N";
		        } else {
		        	resultYn = "Y";
		        }
			}
%>
												<td class="s_tab04_cen"><%=apply%></td>
												<td></td>
												<td class="s_tab04_cen"><%=list.getInt("total_score")%>점</td>
												<td></td>
<%			if (StringUtil.nvl(list.getString("result_check")).equals("Y"))
				mark = "완료";

			else
				mark = "미완료";	%>
												<td class="s_tab04_cen"><%=mark%></td>
												<td></td>
												<td class="s_tab04_cen"><a href="javascript:Show_Result('<%=resultYn%>','<%=userId%>');"><img
													src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bttn_marking_result.gif" align="absmiddle" border="0"></a></td>
												<td></td>
												<td class="s_tab04_cen"><input type="checkbox" name="retest" value="<%=userId%>" class="no"></td>
											</tr>
<%		}

		list.close();
	}

	if (No < 1) {	%>
											<tr>
												<td class="s_tab04_cen" colspan="15">사용자가 존재하지 않습니다.</td>
											</tr>
<%	}	%>
											<tr class="s_tab05">
												<td colspan="15"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="15" height="30" align="right">
<script language=javascript>Button3("목록", "goCancel()", "");</script>
&nbsp;<script language=javascript>Button3("재시험설정", "Retest_Regist()", "");</script>
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





























<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.curriresearch.dto.CurriResInfoDTO"%>
<%@include file="../common/popup_header.jsp" %>
<%
    CurriResInfoDTO infoDto = (CurriResInfoDTO)model.get("infoDto");
    String pMODE = (String)model.get("pMODE");
    String pResId = (String)model.get("pResId");
    String contentsType = "";
    int resNo = 0;
    String objType = "";
    String LineBgColor = "#40B389";
%>
<script language="javascript">
	self.resizeTo(620,600);

	function research_cancel(){
	   var st = confirm('설문을 제출하지 않았습니다 \n\n      종료 합니까?');
		if (st == true) {
		   window.close();
		}
	}

	function Research_Submit(){
	    var f = document.Input;
	    var maxnum = f.maxNum.value;

	    if(!validate(f)) return;

	    var info;
	    var checkObj;
	    var checkCnt = 0;
	    for(var i =1; i <= maxnum; i++){
	       info = f["pContentsInfo"+i].value.split("/");
	       checkCnt = 0;
	       if(info[1] == "K"){
	           checkObj = f["pExample"+info[0]];
	           for(var j = 0; j < 10; j++){
	              if(checkObj[j] != undefined && checkObj[j].checked == true) checkCnt++;
 	           }

 	           if(checkCnt == 0){
 	              alert(i+" 문항을 선택해 주세요.");
 	              return;
 	           }
	       }
	    }

		var st = confirm('설문 제출은 한번만 가능합니다 답안을 설문을 제출합니까?');
		if (st == true) {
           f.submit();
		}else return;
	}
</script>
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="../img/1/common/blet05.gif" align="absmiddle"><font class="pop_tit">설문참여</font></td>
			<td class="pop_topright"></td>
		</tr>
		<tr>
			<td class="pop_midleft"></td>
			<td valign="top" class="pop_form">
				<!-- 내용 -->
				<table width="90%" height="100%" align="center">
<!-- form start -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/ResearchAnswer.cmd?cmd=researchAnswerRegist">
<input type="hidden" name="pResId" value="<%=pResId%>">
					<tr height="97%">
						<td align="left" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="s_tab01">
									<td colspan="2"></td>
								</tr>
<%	int No = 1;
  	RowSet list = (RowSet)model.get("contentsList");

  	if (list != null) {

  		while (list.next()) {
	  		contentsType = StringUtil.nvl(list.getString("contents_type"));
	  		resNo = list.getInt("res_no");
%>
								<tr>
									<td class="s_tab_view02"><font class="c_text02"><b>&nbsp;<%=No%>.&nbsp;<%=StringUtil.getHtmlContents(StringUtil.nvl(list.getString("contents")))%>&nbsp;(<%=CommonUtil.getContentsTypeName(contentsType)%>)</b></font></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
<%			if (contentsType.equals("J")) {	%>
								<tr>
									<td class="s_tab_view03">
										<textarea name="pAnswer<%=resNo%>" class="brown" style="width:100%;height:50;" wrap="VIRTUAL" dispName="<%=resNo%>번 문항" notNull></textarea>
		  								<input type="hidden" name="pContentsInfo<%=No%>" value="<%=resNo%>/<%=contentsType%>">
									</td>
								</tr>
<%			} else if (contentsType.equals("K")) {
				if (StringUtil.nvl(list.getString("example_num")).equals("X"))
					objType = "checkbox";
				else
					objType = "radio";
%>
								<tr>
									<td class="s_tab_view02">
<%				if (!StringUtil.nvl(list.getString("example1")).equals("")) {	%>
			     						&nbsp;1.&nbsp;<input type='<%=objType%>' name="pExample<%=resNo%>" style=border:0 value=1 ><%=StringUtil.nvl(list.getString("example1"))%><br>
<%				}	%>
<% 				if (!StringUtil.nvl(list.getString("example2")).equals("")) {	%>
			     						&nbsp;2.&nbsp;<input type='<%=objType%>' name="pExample<%=resNo%>" style=border:0 value=2 ><%=StringUtil.nvl(list.getString("example2"))%><br>
<%				}	%>
<%				if (!StringUtil.nvl(list.getString("example3")).equals("")) {	%>
			     						&nbsp;3.&nbsp;<input type='<%=objType%>' name="pExample<%=resNo%>" style=border:0 value=3 ><%=StringUtil.nvl(list.getString("example3"))%><br>
<%				}	%>
<%				if (!StringUtil.nvl(list.getString("example4")).equals("")) {	%>
			     						&nbsp;4.&nbsp;<input type='<%=objType%>' name="pExample<%=resNo%>" style=border:0 value=4 ><%=StringUtil.nvl(list.getString("example4"))%><br>
<%				}	%>
<%				if (!StringUtil.nvl(list.getString("example5")).equals("")) {	%>
			     						&nbsp;5.&nbsp;<input type='<%=objType%>' name="pExample<%=resNo%>" style=border:0 value=5 ><%=StringUtil.nvl(list.getString("example5"))%><br>
<%				}	%>
<%				if (!StringUtil.nvl(list.getString("example6")).equals("")) {	%>
			     						&nbsp;6.&nbsp;<input type='<%=objType%>' name="pExample<%=resNo%>" style=border:0 value=6 ><%=StringUtil.nvl(list.getString("example6"))%><br>
<%				}	%>
<% 				if(!StringUtil.nvl(list.getString("example7")).equals("")){%>
			     						&nbsp;7.&nbsp;<input type='<%=objType%>' name="pExample<%=resNo%>" style=border:0 value=7 ><%=StringUtil.nvl(list.getString("example7"))%><br>
<% 				}	%>
<% 				if(!StringUtil.nvl(list.getString("example8")).equals("")){%>
			     						&nbsp;8.&nbsp;<input type='<%=objType%>' name="pExample<%=resNo%>" style=border:0 value=8 ><%=StringUtil.nvl(list.getString("example8"))%><br>
<% 				}	%>
<% 				if(!StringUtil.nvl(list.getString("example9")).equals("")){%>
			     						&nbsp;9.&nbsp;<input type='<%=objType%>' name="pExample<%=resNo%>" style=border:0 value=9 ><%=StringUtil.nvl(list.getString("example9"))%><br>
<% 				}	%>
<% 				if(!StringUtil.nvl(list.getString("example10")).equals("")){%>
			     						&nbsp;10.&nbsp;<input type='<%=objType%>' name="pExample<%=resNo%>" style=border:0 value=10 ><%=StringUtil.nvl(list.getString("example10"))%><br>
<% 				}	%>
										<input type="hidden" name="pContentsInfo<%=No%>" value="<%=resNo%>/<%=contentsType%>">
									</td>
								</tr>
<%			} else if (contentsType.equals("S")) {	%>
								<tr>
									<td class="s_tab_view02">
										&nbsp;맞음:<input type="radio" name="pExample<%=resNo%>" value="1" style=border=0 checked>&nbsp;&nbsp;
										틀림:<input type="radio" name="pExample<%=resNo%>" value="2" style=border=0 >
										<input type="hidden" name="pContentsInfo<%=No%>" value="<%=resNo%>/<%=contentsType%>">
									</td>
								</tr>
<%			}	%>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
<%
			No++;
		}
	}	%>
							    <input type="hidden" name="maxNum" value="<%=No-1%>">
								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td colspan="2" align="center" valign="top" class="pop_btn">
<%	if (!pMODE.equals("P")) {	%>
<script language=javascript>Button3("등록", "Research_Submit()", "");</script>
<%	}	%>
&nbsp;<script language=javascript>Button3("취소", "research_cancel()", "");</script>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<!-- // 내용 -->
			</td>
			<td class="pop_midright"></td>
		</tr>
		<tr height="6">
			<td class="pop_bottomleft"></td>
			<td class="pop_bottombg"></td>
			<td class="pop_bottomright"></td>
		</tr>
		<!-- tr>
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><img src="../img/1/button_img/btn_popclose.gif"></td>
		</tr-->
	</table>
</form>
<!-- form end -->
<%@include file="../common/popup_footer.jsp" %>
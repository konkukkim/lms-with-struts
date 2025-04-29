<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.currisub.dto.CurriQuizDTO"%> 
<%@include file="../common/popup_header.jsp" %>
<%
    String pCourseId = (String)model.get("pCourseId");
    String pCourseName = (String)model.get("pCourseName");
    String pContentsId = (String)model.get("pContentsId");
    String pContentsName = (String)model.get("pContentsName");
    String QuizCount = (String)model.get("QuizCount");
    String QuizPoint = (String)model.get("QuizPoint");
    ArrayList quizList = (ArrayList)model.get("quizList");
    CurriQuizDTO qI = null;
    //RowSet list = (RowSet)model.get("quizList");
    String LineBgColor = "#40B389";
%>
<script language="javascript">
	function click() {
		if (event.button == 2) {
			alert('왼쪽 마우스 버튼만 사용하세요');
			return;
		}
		if (event.button == 3) {
			alert('왼쪽 마우스 버튼만 사용하세요');
			return;
		}
	}
	document.onmousedown = click

	function OnKeyDown() {
		if (event.keyCode==93 || event.keyCode==116 ) {
		        event.keyCode=0; 
				alert('마우스 왼쪽 버튼만 사용가능합니다');
				return false;
		}
	}
	document.onkeydown = OnKeyDown
			
	
	
	function quiz_cancel(){
	   var st = confirm('답안 제출을 하지 않았습니다 \n\n      종료 합니까?');  
		if (st == true) {
		   window.close();
		} 
	}
	
	function Answer_Submit(){
	    var f = document.Input;
		var st = confirm('점수 미달시 언제든지 재응시 가능합니다 답안을 제출합니까?');  
		if(st == true){
		   f.submit();
		}else return;
	}
	
    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc = "<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;        
       hiddenFrame.document.location = loc;
    }	
</script>
<form name="Input" method="post" action = "<%=CONTEXTPATH%>/CurriQuiz.cmd?cmd=curriQuizStRegist">
  <input type="hidden" name="pCourseId" value="<%=pCourseId%>">
  <input type="hidden" name="pContentsId" value="<%=pContentsId%>">
  <input type="hidden" name="pContentsName" value="<%=pContentsName%>">
  <input type="hidden" name="QuizCount" value="<%=QuizCount%>">
  <input type="hidden" name="QuizPoint" value="<%=QuizPoint%>">
<table width="580" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <!-- (Width, Height ) Fix--><td width="10" height="10"><img src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/com_tit01.gif" width="10" height="10"></td>
    <!-- (Height ) Fix--><td width="560" height="10" background="/img/<%=SYSTEMCODE%>/popup/com_tit05.gif"></td>
    <!-- (Width, Height ) Fix--><td width="10" height="10"><img src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/com_tit02.gif" width="10" height="10"></td>
  </tr>
  <tr> 
    <!-- (Width) Fix--><td width="10" background="/img/<%=SYSTEMCODE%>/popup/com_tit05.gif">&nbsp;</td>
    <td width="560" height="35" align="center" background="/img/<%=SYSTEMCODE%>/popup/com_tit05.gif"><img src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/com_icon.gif" width="11" height="11"><span class="pop"><font color="#FFFFFF">&nbsp;</font></span><font color="#FFFFFF" size="3">
    <strong>&nbsp;단원명 : <%=pContentsName%> <br> [통과문항수 / 총문항수  : <%=QuizPoint%> / <%=QuizCount%>]&nbsp;</strong></font></td>
    <!-- (Width) Fix--><td width="10" background="/img/<%=SYSTEMCODE%>/popup/com_tit05.gif">&nbsp;</td>
  </tr>
  <tr>
    <!-- (Width, Height ) Fix--><td width="10" height="10"><img src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/com_tit03.gif" width="10" height="10"></td>
    <!-- (Height ) Fix--><td width="560" height="10" background="/img/<%=SYSTEMCODE%>/popup/com_tit05.gif"></td>
    <!-- (Width, Height ) Fix--><td width="10" height="10"><img src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/com_tit04.gif" width="10" height="10"></td>
  </tr>
  <tr> 
    <!-- (Width, Height ) Fix--><td width="10" height="6"></td>
    <!-- (Height ) Fix--><td width="560" height="6"></td>
    <!-- (Width, Height ) Fix--><td width="10" height="6"></td>
  </tr>
</table>
 <table width="580" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr> 
      <td height="15"></td>
    </tr>
    <tr> 
      <td>
        <!--list table start -->
<%	int No = 0;
	int    pQuizOrder = 0;
	String pQuizType = "";
	String pQuizContents = "";
	String pExample1 = "";
	String pExample2 = "";
	String pExample3 = "";
	String pExample4 = "";
	String pExample5 = "";
	String pRAnswer = "";
	String rfile_name = "";
	String sfile_name = "";
	String file_path = "";
	String file_size = "";
	
	for(int i =0; i < quizList.size(); i++){
	
		 qI = (CurriQuizDTO)quizList.get(i); 
	 	No++;
	 	pQuizOrder = qI.getQuizOrder();
		pQuizType = StringUtil.nvl(qI.getQuizType());
		pQuizContents = StringUtil.nvl(qI.getContents());
		pExample1 = StringUtil.nvl(qI.getExample1());
		pExample2 = StringUtil.nvl(qI.getExample2());
		pExample3 = StringUtil.nvl(qI.getExample3());
		pExample4 = StringUtil.nvl(qI.getExample4());
		pExample5 = StringUtil.nvl(qI.getExample5());
		pRAnswer = StringUtil.nvl(qI.getAnswer());
		rfile_name = StringUtil.nvl(qI.getRfileName());
		sfile_name = StringUtil.nvl(qI.getSfileName());
		file_path = StringUtil.nvl(qI.getFilePath());
		file_size = StringUtil.nvl(qI.getFileSize());
		
	%>
    <input type="hidden" name="pQuizOrder[<%=No%>]" value="<%=pQuizOrder%>">
	<input type="hidden" name="pQuizType[<%=No%>]" value="<%=pQuizType%>">
	<input type="hidden" name="pRAnswer[<%=No%>]" value="<%=pRAnswer%>">
		<table width="580" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
		    <td height="2" bgcolor="<%=LineBgColor%>"></td> 
		  </tr>
		  <tr> 
		    <td height="4">
		       <table width="580" border="0" cellspacing="1" cellpadding="2">
		        <tr> 
		          <td height="2"></td>
		        </tr>
		        <tr> 
		          <td height="20" align="left">
                 <%=No%>번 문제
                  </td>
		        </tr>
		        <tr bgcolor="D5D5D5"> 
		          <td height="1"></td>
		        </tr>
		        <tr> 
		          <td height="20" align="left">
		           <%=pQuizContents%>
				   <%if(!rfile_name.equals("")){ %>
				     <a href="javascript:fileDownload('<%=rfile_name%>','<%=sfile_name%>','<%=file_path%>','<%=file_size%>');"><font color="orange">* 관련파일 다운</font></a>
				   <%}%>
                  </td>
		        </tr>
		        <tr bgcolor="D5D5D5"> 
		          <td height="1"></td>
		        </tr>
				<% if(pQuizType.equals("K")){ %>  
		        <tr> 
		          <td height="20" align="left">&nbsp;(정답을 선택해 주세요)<br>
				    <% if(!pExample1.equals("")){%> 
				     1.&nbsp;<%=pExample1%> <input type='radio' name="pAnswer[<%=No%>]" value="1" style=border=0> <br> 
					<% } %>
				    <% if(!pExample2.equals("")){%> 
				     2.&nbsp;<%=pExample2%> <input type='radio' name="pAnswer[<%=No%>]" value="2" style=border=0> <br> 
					<% } %>
				    <% if(!pExample3.equals("")){%> 
				     3.&nbsp;<%=pExample3%> <input type='radio' name="pAnswer[<%=No%>]" value="3" style=border=0> <br>
					<% } %>
				    <% if(!pExample4.equals("")){%> 
				     4.&nbsp;<%=pExample4%> <input type='radio' name="pAnswer[<%=No%>]" value="4" style=border=0> <br> 
					<% } %>
				    <% if(!pExample5.equals("")){%> 
				     5.&nbsp;<%=pExample5%> <input type='radio' name="pAnswer[<%=No%>]" value="5" style=border=0> <br> 
				     <% } %>
                  </td>
		        </tr>
		        <tr bgcolor="D5D5D5"> 
		          <td height="1"></td>
		        </tr>
		        <%}else if(pQuizType.equals("D")){%>  
		        <tr> 
		          <td height="20" align="center">
		          	<textarea name="pAnswer[<%=No%>]" cols="90" wrap="VIRTUAL"></textarea>
		          </td>
		        </tr>
		        <%}else if(pQuizType.equals("S")){%>  
		        <tr> 
		          <td height="20" align="left">
	                맞음,틀림 중 하나를 선택해 주세요.<br>
		 			맞음:<input type="radio" name="pAnswer[<%=No%>]" value="O" style=border=0 checked>
					&nbsp;&nbsp;
					틀림:<input type="radio" name="pAnswer[<%=No%>]" value="X" style=border=0 > 
                  </td>
		        </tr>
		        <tr bgcolor="D5D5D5"> 
		          <td height="1"></td>
		        </tr>
		        <%} %>
		        </table>
		     </td>
		  </tr>
		  <tr> 
		    <td height="2" bgcolor="<%=LineBgColor%>"></td>
          </tr>
        </table>  
   <% 
      } 
   %> 
        <!--list table end -->
      </td>
    </tr>
    <tr> 
      <td height="5" align="center">&nbsp;</td>
    </tr>
    <tr> 
      <td height="15" align="center">
      <%if(USERTYPE.equals("S")){%>
      <a href="javascript:Answer_Submit();" onmouseover="window.status='제출';return true;" onmouseout="window.status='';return true"><img src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_co_comfirm.gif"></a>
      <% } %>  
      <a href="javascript:quiz_cancel();" onmouseover="window.status='취소';return true;" onmouseout="window.status='';return true"><img src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_co_cancel.gif"></a>
      </td>
    </tr>
  </table> 
<%@include file="../common/popup_footer.jsp" %>
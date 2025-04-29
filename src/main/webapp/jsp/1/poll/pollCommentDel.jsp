<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.poll.dto.InternetPollCommentDTO"%>
<%@include file="../common/popup_header.jsp" %>
<%
	Map data = (Map)request.getAttribute("MODEL"); 
	String pPollId  = (String)model.get("pPollId");
	String pSeqNo  = (String)model.get("pSeqNo");
%>
<script language="javascript">
	function submitCheck()
	{
		var f = document.Input;
		
	 	if(!validate(f)) 
        	return false;
        else
            return true;     
	}
</script>
<form name=Input method="post" action="<%=CONTEXTPATH%>/Poll.cmd?cmd=pollCommentDelete" onSubmit="return submitCheck()">
<input type='hidden' name ='pPollId' value='<%=pPollId%>'>
<input type='hidden' name ='pSeqNo' value='<%=pSeqNo%>'>
<input type='hidden' name ='pDmode' value='U'>
<table width="370" border="0" cellpadding="2" cellspacing="1" bgcolor="#DBDBDB">
  <tr>
    <td align="center" bgcolor="#FFFFFF">
      <!--둘러싸고 있는 테이블과 안의 테이프 width값 차이 4px -->
      <table width="363" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <!-- (Height ) Fix--><td height="2"></td>
        </tr>
        <!--tr> 
          <td height="45" align="center" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/com_bg.gif"><!-- Title --><font color="#FFFFFF" size="3"><strong> 인터넷 투표</strong></font></td>
        <!--/tr>
       		<tr> 
		  <td height="5"></td>
		</tr>
		<tr> 
		  <td height="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/title/line_01.gif"></td>
		</tr-->
		<tr> 
		  <td height="25">&nbsp;</td>
		</tr>
        <tr> 
          <td align="center">
            <!-- Contents Start-->
            <table width="335" border="0" cellspacing="5" cellpadding="0">
              <tr> 
                <td><B>글등록시 입력한 비밀번호를 입력하세요.</B></td>
              </tr>
              <tr> 
                <td height="1" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/score_dot.gif"></td>
              </tr>
				<tr>
					<td bgcolor="#ffffff" height='26' align="center">비밀번호 : <input type='password' name='pPasswd'  class=green size='10' maxlength='10' dispName='비밀번호' notNull></td>
				</tr>
            </table>
            <!--Contents End -->
          </td>
        </tr>
        <tr> 
          <!-- (Height ) Fix--><td height="20">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
</table>
<br>
<table width="100%" >
        <tr align="center"> 
          <td height="42"><br>
          </td>
          <td colspan="3" height="32" align="center"><input type="image" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/ok01.gif" width="56" height="21" class=no><a href="javascript:window.close()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/cancle01.gif" width="56" height="21" hspace=4></a></td>
        </tr>
      </table>
    </td>
</form>
</table>
<%@include file="../common/popup_footer.jsp" %>



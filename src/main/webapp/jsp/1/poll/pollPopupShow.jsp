<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.poll.dto.InternetPollDTO"%> 
<%@include file="../common/popup_header.jsp" %>
<%
	Map data = (Map)request.getAttribute("MODEL"); 
	InternetPollDTO pollView = (InternetPollDTO)model.get("pollShow");  
	String userId = (String)model.get("userId");
	CONTEXTPATH = "/lms";
	
	int chkCnt = 0;
	if(pollView != null) {	
		chkCnt = pollView.getChkCnt();
	}
%>
<script language="javascript">
//투표참여
function Poll_Answer() {
<%if(!userId.equals("")){%>
	var	f			=	document.poll;
	var poll_id     =   f.pPollId.value;
	var status     =   f.pStatus.value;
	var	chk_value	=	"";

	for(var i=0; i<f.pNO.length; i++) {
		if (f.pNO[i].checked == true) {
			chk_value = f.pNO[i].value;
		}
	}

	if (<%=chkCnt%> > 0) {
		alert("이미 투표를 하였습니다.");
	}
	else {

		if (chk_value == "") {
				alert( "투표항목을 선택하세요." );
			}
			else {
				document.location = "<%=CONTEXTPATH%>/Poll.cmd?cmd=pollPopupRegist&userId=<%=userId%>&pStatus="+status+"&pPollId="+poll_id+"&pNO="+chk_value;
			}
		}
	
	<%}else{%>
		alert('로그인을 하셔야 투표에 참여 하실 수 있습니다.');
	<%}%>
	}
	// 투표결과 보기
	function Poll_Result(poll_id) {
		document.location	=	"<%=CONTEXTPATH%>/Poll.cmd?cmd=pollPopupResultShow&pPollId="+poll_id;
	}
	</script> 
<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#DBDBDB">
<form name=poll >
 	<input type='hidden' name='pPollId' value='<%=pollView.getPollId()%>'>
 	<input type='hidden' name='pStatus' value='<%=pollView.getStatus()%>'>
  <tr>
    <td align="center" bgcolor="#FFFFFF">
      <!--둘러싸고 있는 테이블과 안의 테이프 width값 차이 4px -->
      <table width="98%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <!-- (Height ) Fix--><td height="2"></td>
        </tr>
        <tr> 
          <td height="45" align="center" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/com_bg.gif"><!-- Title --><font color="#FFFFFF" size="3"><strong>인터넷 투표</strong></font></td>
        </tr>
		<tr> 
		  <td height="5"></td>
		</tr>
		<tr> 
		  <td height="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/title/line_01.gif"></td>
		</tr>
		<tr> 
		  <td height="25">&nbsp;</td>
		</tr>
        <tr> 
          <td align="center">
            <!-- Contents Start-->
            <table width="95%" border="0" cellspacing="0" cellpadding="5">
              <tr> 
                <td><!--<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/calendar/score_icon_01.gif">투표내용 :--><b><%=pollView.getContents()%></b></td>
              </tr>
			  <tr> 
			    <td height="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/line_dot.gif"></td>
			  </tr>
			  <tr>
			  	<td align="center" valign=top bgcolor=#ffffff style="padding-bottom: 5px; padding-left: 5px; padding-right: 5px; padding-top: 5px"> 
					<table border=0 cellpadding=0 cellspacing=0 height=95 width="100%">
			<%
				for(int i=0; i < pollView.getExampleCnt(); i++) {
			%>
              <tr > 
                <td height="18"><input type="radio"  name='pNO' value="<%=i+1%>" style="border:0; background-color: #FFFFFF ;" class="no"><font color="#333333"><%=i+1%>. <%=pollView.getExample()[i]%> </font></td>
              </tr>
					
			<%
				}
			%>          
              <tr> 
                <td height="11"></td>
              </tr>
              <tr> 
                <td align="center">
                <img  onClick="javascript:Poll_Answer()" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/b_ma_poll.gif" width="72" height="19" style="cursor:hand;border:0">&nbsp;
                <%if(pollView.getStatus().equals("Y")){%>
				<img onClick="javascript:Poll_Result(<%=pollView.getPollId()%>)" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/main/b_ma_result.gif" width="72" height="19"  style="cursor:hand;border:0">
				<%}%>
				</td>
              </tr>
					</table>
				 </td>
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
<!--<table cellpadding=5 cellpadding=1 cellspacing=0 width=96%>
<tr>
	<td align='right'>
		<input type="checkbox" name="Notice" OnClick="javascript:closeWin();"  style="border:0">오늘 하루 이 창 닫기 
	</td>
</tr>
</table>-->
</form>
<%@include file="../common/popup_footer.jsp" %>

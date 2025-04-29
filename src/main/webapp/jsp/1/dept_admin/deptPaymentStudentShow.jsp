<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr"    %>
<%@ page import="com.edutrack.payment.dto.PaymentDTO"%>
<%@include file="../common/popup_header.jsp"        %>	
<%
ArrayList deptEnroll = (ArrayList) model.get("deptEnroll");
ArrayList studEnroll = (ArrayList) model.get("studEnroll");

String pCurriCode = StringUtil.nvl(request.getParameter("pCurriCode") );
String pCurriYear = StringUtil.nvl(request.getParameter("pCurriYear") );
String pCurriTerm = StringUtil.nvl(request.getParameter("pCurriTerm") );
String pEnrollIdx = StringUtil.nvl(request.getParameter("pEnrollIdx") );
String pConVal    = pCurriCode +"!"+ pCurriYear +"!"+ pCurriTerm +"!"+ pEnrollIdx ;

String serviceStartDate = "";
String serviceEndDate   = "";

PaymentDTO paymentDto = new PaymentDTO();

if(studEnroll.size() > 0 )
{
    paymentDto = (PaymentDTO)studEnroll.get(0);
    
    serviceStartDate = paymentDto.getServiceStartDate();
    serviceEndDate = paymentDto.getServiceEndDate();
    
}// end if
%>
<Script Language=javascript>
function cancel_page() {
	top.window.close();
}


function searchStudentList(val) {
    
    param = val.split("!");
    
	Page = "<%=CONTEXTPATH%>/DeptAdmin.cmd?cmd=deptPaymentStudentShow&pCurriCode="+param[0]+"&pCurriYear="+param[1]+"&pCurriTerm="+param[2]+"&pEnrollIdx="+param[3];

    location.href=Page;
}

</Script>
<table width="320" border="0" cellspacing="0" cellpadding="0" height="352">
  <tr align="center"> 
	<td valign="bottom" colspan="3" height="42" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/pop_title25.gif"> </td>
  </tr>
  <tr> 
    <td align="center" valign="middle"> 
      <table width="300" border="0" cellspacing="0" cellpadding="0">
		<tr> 
		  <td height="2" colspan="3" class="b_td03"></td>
		</tr>
        <tr> 
          <td class="b_td02" height="25" align="left">
		  과정명 :
		  </td>
          <td width="200" height="25">
            <select name="select2" class="sky" onChange="javascript:searchStudentList(this.value)">
            <%
            String tmpVal = "";
            
            for(int i=0; i<deptEnroll.size(); i++)
            {
                 paymentDto = (PaymentDTO)deptEnroll.get(i);
                 
                 tmpVal = paymentDto.getCurriCode() + "!" + paymentDto.getCurriYear() + "!" + paymentDto.getCurriTerm() + "!" + paymentDto.getDeptEnrollIdx() ;
            %>
              <option value="<%=tmpVal %>" <%=pConVal.equals(tmpVal)? "selected" : "" %>><%=paymentDto.getCurriName() %></option>
            <%
            } // end for
            %>
            </select>
          
          </td>
        </tr>
        <tr > 
          <td align="right" colspan="2"  class="b_td03" height="1"></td>
        </tr>
        <tr> 
          <td class="b_td02" height="25" align="left">
				  강의기간 : 
		  </td>
          <td height="25"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(serviceStartDate))%> ~ <%=DateTimeUtil.getDateType(1,StringUtil.nvl(serviceEndDate))%></td>
        </tr>
        <tr > 
          <td align="right" colspan="2"  class="b_td03" height="1"></td>
        </tr>
        <tr> 
          <td align="right" colspan="2">&nbsp;</td>
        </tr>
        <tr align="left"> 
          <td colspan="2">* 조회를 원하는 과정을 선택하시면 해당 과정의 수강인원을 조회하실 수 있습니다.</td>
        </tr>
        <tr align="center" valign="middle"> 
          <td colspan="2" height="20"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td align="center"  class="b_td03" valign="middle" height="2"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="2"></td>
  </tr>
  <tr>
    <td align="center" valign="middle" height="5">&nbsp;</td>
  </tr>
  <tr> 
    <td align="center" valign="middle">
      <table width="300" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="50" bgcolor="AAD5AD" align="center" height="24" class="b_td02"><strong>번호</strong></font></td>
          <td width="1" bgcolor="AAD5AD" align="center" valign="middle" class="b_td02"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/white.gif" width="1" height="24" vspace="0" align="absbottom" border="0" hspace="2"></td>
          <td width="124" bgcolor="AAD5AD" align="center" valign="middle" class="b_td02"><strong>아이디</strong></font></td>
          <td width="1" bgcolor="AAD5AD" align="center" valign="middle" class="b_td02"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/white.gif" width="1" height="24" vspace="0" align="absbottom" border="0" hspace="2"></td>
          <td width="124" bgcolor="AAD5AD" align="center" valign="middle" class="b_td02"><strong>성명</strong></font></td>
        </tr>
        <tr> 
          <td colspan="7" class="b_td03"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
        </tr>
        <%
        int iNo=1;
        for(int i=0; i<studEnroll.size(); i++)
        {
             paymentDto = (PaymentDTO)studEnroll.get(i);
        %>
        <tr> 
          <td align="center" height="22"><%=iNo++ %></td>
          <td align="center">&nbsp;</td>
          <td align="center"><%=paymentDto.getUserId() %></td>
          <td align="center">&nbsp;</td>
          <td align="center"><%=paymentDto.getUserName() %></td>
        </tr>
        <tr> 
          <td colspan="7" class="b_td03"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
        </tr>
        <%
        } // end for
        %>
      </table>
    </td>
  </tr>
  <tr> 
    <td align="center" height="5">&nbsp;</td>
  </tr>
  <tr> 
    <td align="center"  class="b_td03"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="2"></td>
  </tr>
  <tr> 
    <td align="center" height="34"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/close.gif" align="absmiddle" border="0" onClick="cancel_page()" style="cursor:hand"></td>
  </tr>
</table>


<%@include file="../common/popup_footer.jsp" %>	
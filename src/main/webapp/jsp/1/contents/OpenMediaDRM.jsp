<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.CommonUtil"%> 
<%@include file="../common/popup_header.jsp" %>

<%
	String CID = (String)model.get("CID");
	
	String serverUrl = CommonUtil.SERVERURL;
	String drmServer = CommonUtil.DigCapDRMServerDoamin;
%>
<script language=javascript>
	// empty check
	function empty_check(data) {
	    if (data.length == 0) {
	        return false;
	    }
	    else {
	        for (var i=0; i<data.length; i++ ) {
	             if (data.substring(i,i+1) != " ") {
	                return true;
	             }
	        }
	        return false;
	    }
	    return true;
	}
	
    
    function cancel_page() {
		top.window.close();
    }

</script>
<!--
<form name="Input" method=post action="<%=drmServer%>/packaging/elearnpack.asp " >
<input type="hidden" name="ConID" value="<%=CID%>">
<input type="hidden" name="LicURL" value="<%=drmServer%>/nolicense01.asp">
<table width="450" border="0" cellspacing="0" cellpadding="0" height="442">
  <tr align="center"> 
    <td colspan="5" height="61"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img140.gif" width="401" height="31"></td>
  </tr>

  <tr> 
    <td bgcolor="AAD5AD" width="1" rowspan="9"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
    <td width="24" height="337" rowspan="9"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="24" height="1"></td>
    <td  width="400" valign="top" height="100" colspan="2"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="5" bgcolor="AAD5AD" height="1"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
        </tr>
        <tr> 
          <td rowspan="3" bgcolor="AAD5AD" width="1"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
          <td rowspan="3"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="5" height="1"></td>
          <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="5"></td>
          <td rowspan="3"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="5" height="1"></td>
          <td rowspan="3" bgcolor="AAD5AD" width="1"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
        </tr>
        <tr> 
          <td height="89" bgcolor="ECF6E4"><font color="8D4806"><b>주의</b></font><br>
            <br>
            본 페이지는 동영상 강의 등록전 <br>
            대상 동영상을 암호화 패키징 하는 작업의 시작 입니다. <br><br>
            원본 동영상 파일과 별도로 패키징된 동영상 파일이 생성 됩니다.<br>
            아래 텍스트 상자에 생성될 동영상 파일의 이름을 입력해 주십시요 </td>
        </tr>
        <tr> 
          <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="5"></td>
        </tr>
        <tr> 
          <td colspan="5" bgcolor="AAD5AD"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
        </tr>
      </table>
    </td>
    <td width="24" rowspan="9"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="24" height="1"></td>
  </tr>
  <tr> 
    <td colspan="2" height="10"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="10"></td>
  </tr>


  <tr> 
    <td height="50" width="70" valign="top"><font color="8D4806"><b><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="10"><br>
      Warning </b></font>:<br>
    </td>
    <td height="50" width="330" valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="10"><br>
      The file must be free of 2-byte characters <br>
      in order to be successfully registered.</td>
  </tr>
  <tr> 
    <td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_bgimg.gif" width="400" height="1" colspan="2"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
  </tr>
   <tr> 
    <td colspan="2" height="10"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="10"></td>
  </tr>
  <tr> 
    <td width="70" valign="top" rowspan="2" height="100"><b>FileName </b></td>
    <td valign="top" width="330" height="60"> 
      <input type=text name="confilename" class="green" size=30>
      <br>
      legal characters:[<b><font color="8D4806">A~Z a~z 0~9 . - _</font></b>]</td>
  </tr>
  <tr>
    <td valign="middle" width="330" height="40" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/ok01.gif" width="56" height="21" border="0" hspace="5"  onClick="check_form(this.form)" style="cursor:hand"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/cancle01.gif" width="56" height="21" border="0" hspace="5"  onClick="cancel_page()" style="cursor:hand"></td>
  </tr>
  <tr> 
    <td colspan="5" bgcolor="AAD5AD" height="2"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
  </tr>
</table>
</form>
-->
<script>
 document.location = "<%=drmServer%>/packaging/elearnpack.asp?conid=<%=CID%>";
</script>
</body>
</html>
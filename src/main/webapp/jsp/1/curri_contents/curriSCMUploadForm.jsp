<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/popup_header.jsp" %>
<%
	String pCurriCode = (String)model.get("pCurriCode");
	String pCurriYear = (String)model.get("pCurriYear");
	String pCurriTerm = (String)model.get("pCurriTerm");
	String pCourseId = (String)model.get("pCourseId");
	int pContentsCnt = Integer.parseInt((String)model.get("pContentsCnt"));
	String Action = CONTEXTPATH+"/CurriContents.cmd?cmd=curriSCMUpload&pCurriCode="+pCurriCode+"&pCurriYear="+pCurriYear+"&pCurriTerm="+pCurriTerm+"&pCourseId="+pCourseId+"&pUploadMode=MOD";
	if(pContentsCnt > 0)
		Action = CONTEXTPATH+"/CurriContents.cmd?cmd=curriSCMUpload&pCurriCode="+pCurriCode+"&pCurriYear="+pCurriYear+"&pCurriTerm="+pCurriTerm+"&pCourseId="+pCourseId+"&pUploadMode=ADD";
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
	// alphabet, numeric check
	function alphabet_check(istr) {
	    for (var i=0; i<istr.length; i++) {
	        var str = istr.substring(i,i+1);
	        if ( !((str >= 'a' && str <= 'z') || (str >= 'A' && str <= 'Z') || (str >= '0' && str <= '9') 
	            || (str == '.') || (str == '-') || (str == '_')) ) {
	            return false;
	        }
	    }
	    return true;
	}
	// return true if data consists of only letters, numbers and/or underscores, dashes, slashes, semicolons and colons
	function alphanumeric_check(data) {
	    var others = "-_/;:.@?&=()";
	    for(var k=0;k<data.length;k++) {
	        c = data.charAt(k);
	        if((c < '0' || c > '9')&&(c < 'a' || c > 'z')&&(c < 'A' || c > 'Z')){
	            if (others.indexOf(c) < 0) {                
	                return false;
	            }
	        }
	    }            
	    return true;
	}
	function file_name(f_name) {
        var n_name = "";
        if (f_name != "") {
            var lst_in = f_name.lastIndexOf('\\');
            n_name = f_name.substring(lst_in+1);
        }
        return n_name;
    }

    function file_ext(f_name) {
        var ext = "";
        if (f_name != "") {
            var lst_in = f_name.lastIndexOf('.');
            ext = f_name.substring(lst_in+1);
            ext = ext.toLowerCase();
        }
        return ext;
    }
    
    function cancel_page() {
		top.window.close();
    }

    function check_submit(f) {
        if (!check_form(f)) {
            return false;
        }else{
        	return true;
        }
    }

    function check_form(frm) {
    	var frm = document.Input;
        var fname = frm.pFILE.value;
        if (!empty_check(fname)) {
            alert("교재패키지 파일을 입력하세요.");
        }else if(!alphabet_check(file_name(fname))) {
            alert("파일명에 사용 불가능한 문자가 있습니다. \n파일명에 사용 가능한 문자는 [A~Z a~z 0~9 . - _]입니다.");
        }else if (file_ext(file_name(fname)) != "zip") {
            alert("교재패키지 파일의 형식은 [.zip]입니다.");
        }else{
	        frm.submit();
    	    document.location = "<%=CONTEXTPATH%>/html/SCMUploading.html";
   	    }
    }
    function change_upload_mode(frm,mode){
    	frm.action="<%=CONTEXTPATH%>/CurriContents.cmd?cmd=curriSCMUpload&pCurriCode=<%=pCurriCode%>&pCurriYear=<%=pCurriYear%>&pCurriTerm=<%=pCurriTerm%>&pCourseId=<%=pCourseId%>&pUploadMode="+mode;
    }
</script>
<form name="Input" method=post action="<%=Action%>"  enctype="multipart/form-data"      target="scorm_contents_insert">
<input type="hidden" name="course_id" value="<%=pCourseId%>">
<table width="450" border="0" cellspacing="0" cellpadding="0" height="442">
  <tr align="center"> 
    <td colspan="5" height="61"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img140.gif" width="401" height="31"></td>
  </tr>
<%if(pContentsCnt > 0){%>
  <tr> 
    <td bgcolor="AAD5AD" width="1" rowspan="9"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
    <td width="24" height="337" rowspan="9"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="24" height="1"></td>
    <td bgcolor="ECF6E4" width="400" valign="top" height="100" colspan="2"> 
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
          <td height="89"><font color="8D4806"><b>주의</b></font><br>
            <br>
            이 과목은 교재가 등록되어 있습니다.<br>
            교재를 재등록하면 기존의 교재는 DB 상에서만 삭제되며 <br>
            저장소(서버)에는 남아 있습니다.<br>
            필요하지 않다면 저장소(서버)에 있는 데이터를 삭제 하여 주시기 <br>
            바랍니다. </td>
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
    <td height="70" width="400" colspan="2">등록 목차수: <font color="8D4806"><b><%=pContentsCnt%></b></font><br>
      기존 구성된 목차에 업로드 하는 컨텐츠를 추가 합니까? 
      <input type="radio" name="pUploadMode" value="ADD" style="border:0" checked onClick="change_upload_mode(this.form,'ADD')">
      Yes 
      <input type="radio" name="pUploadMode" value="MOD" style="border:0" onClick="change_upload_mode(this.form,'MOD')">
      No<br>
      <font color="8D4806"><b>(선택하지 않으면 기존 구성은 삭제됩니다.)</b></font></td>
  </tr>
  <tr> 
    <td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_bgimg.gif" width="400" height="1" colspan="2"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
  </tr>
<%}%>
  <tr> 
    <td height="50" width="70" valign="top"><font color="8D4806"><b><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="10"><br>
      Warning </b></font>:<br>
    </td>
    <td height="50" width="330" valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="10"><br>
      The ZIP file must be free of 2-byte characters <br>
      in order to be successfully registered.</td>
  </tr>
  <tr> 
    <td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_bgimg.gif" width="400" height="1" colspan="2"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
  </tr>
   <tr> 
    <td colspan="2" height="10"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="10"></td>
  </tr>
  <tr> 
    <td width="70" valign="top" rowspan="2" height="100"><b>Content </b></td>
    <td valign="top" width="330" height="60"> 
      <input type=file name="pFILE" class="green" size=30>
      <br>
      File Format: <b><font color="8D4806">.zip</font></b><br>
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
</body>
</html>
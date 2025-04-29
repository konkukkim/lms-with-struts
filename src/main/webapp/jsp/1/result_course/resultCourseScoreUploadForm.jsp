<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/popup_header.jsp" %>
<%
	String pCourseId = (String)model.get("pCourseId");
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

    function check_form() {
        var fname = document.Input.pFILE.value;
        if (!empty_check(fname)) {
            alert("성적 파일을 입력하세요.");
        }else if(!alphabet_check(file_name(fname))) {
            alert("파일명에 사용 불가능한 문자가 있습니다. \n파일명에 사용 가능한 문자는 [A~Z a~z 0~9 . - _]입니다.");
        }else if ((file_ext(file_name(fname)) != "csv") && (file_ext(file_name(fname)) != "txt")) {
            alert("성적 파일의 형식은 [.csv] or [.txt]입니다.");
        }else{
	        document.Input.submit();
    	    document.location = "<%=CONTEXTPATH%>/html/Uploading.html";
   	    }
    }
</script>
	<table width="100%" height="100%" border="0">
<form name="Input" method="post" action="<%=CONTEXTPATH%>/ResultCourse.cmd?cmd=resultCourseScoreUpload&pCourseId=<%=pCourseId%>"  enctype="multipart/form-data" target="score_insert">
<input type="hidden" name="course_id" value="<%=pCourseId%>">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="../img/1/common/blet05.gif" align="absmiddle"><font class="pop_tit">성적업로드</font></td>
			<td class="pop_topright"></td>
		</tr>
		<tr>
			<td class="pop_midleft"></td>
			<td valign="top" class="pop_form">
				<!-- 내용 -->
				<table width="90%" height="100%" align="center">
					<tr height="97%">
						<td align="left" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="s_tab01">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td class="c_tabbox" colspan="2">
										<b><font color="#B97F81">주의</font></b><br>
										업로드 하시는 파일은 형식을 지켜 주십시요.<br>
										[수강생아이디, 시험, 과제, 출석, 토론, 그룹공부, 기타, 총점] 형태로 구성되어 있어야 합니다.
									</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view02" height="30"></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view02"><b>Warning</b></td>
									<td class="s_tab_view02">The file must be free 2-byte characters in order to be successfully registered.</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">파일업로드</td>
									<td class="s_tab_view02">
										<input type=file name="pFILE" size="40"><br>
										File Format : <font color="teal"><b>.csv or .txt</b></font>
										<br>legal characters : [<b><font color="teal">A~Z, a~z, 0~9</font></b>]<br>&nbsp;
									</td>
								</tr>

								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td colspan="2" align="center" valign="top" class="pop_btn">
<script language=javascript>Button3("등록", "check_form()", "");</script>
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
		<tr>
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a
			href="javascript:cancel_page();"><img
			src="../img/1/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>
<%@include file="../common/popup_footer.jsp" %>
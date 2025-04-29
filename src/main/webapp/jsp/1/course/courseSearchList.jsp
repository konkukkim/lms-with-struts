<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.curritop.dto.CourseDTO"%>
<%@include file="../common/popup_header.jsp" %>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function cancel_page() {
		top.window.close();
    }

    function goRegist(courseid, profid, contentstype, onlineyn) {
    	var f = document.Input;
    	var url = "";
    	var param = "&pCourseId="+courseid+"&pProfId="+profid+"&pCourseType="+contentstype+"&pOnlineYn="+onlineyn;
    	var target = f.pTarget.value;
    	if(target == "courseSub") {
    		url = "<%=CONTEXTPATH%>/CurriSubCourse.cmd?cmd=curriSubCourseRegist";
    	} else {
    		url = "<%=CONTEXTPATH%>/CurriTopCourse.cmd?cmd=curriTopCourseRegist"
    	}
    	f.action = url+param;
    	f.submit();
    }
//-->
</SCRIPT>
<form name="Input" method="post" action="<%=CONTEXTPATH%>/Course.cmd?cmd=courseSearchList">
<input type="hidden" name="pCurriCode" value="<%=StringUtil.nvl(model.get("pCurriCode"))%>">
<input type="hidden" name="pCurriTerm" value="<%=StringUtil.nvl(model.get("pCurriTerm"))%>">
<input type="hidden" name="pTarget" value="<%=StringUtil.nvl(model.get("pTarget"))%>">
<input type="hidden" name="pCurriProperty2" value="<%=StringUtil.nvl(model.get("pCurriProperty2"))%>">
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">과목찾기</font></td>
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
								<tr>
									<td colspan="5" class="s_btn01" align="center">
										<select name="pSTarget" class="sky">
											<option value="course_name">과목명</option>
											<option value="course_id">과목ID</option>
										  </select>
										<input type="text" name="pSWord" value="<%=model.get("pSWord")%>" size="20">
										<input type="submit" name="찾아보기" value="찾아보기">
									</td>
								</tr>
								<tr>
									<td height="8"></td>
								</tr>
								<tr class="s_tab01">
									<td colspan="5"></td>
								</tr>
								<tr class="s_tab02">
									<td width="50%">과목명</td>
									<td class="s_tablien"></td>
									<td width="30%">교수자</td>
									<td class="s_tablien"></td>
									<td width="19%">선택</td>
								</tr>
								<tr class="s_tab03">
									<td colspan="5"></td>
								</tr>
<%
	String	pCurriProperty2	=	StringUtil.nvl(model.get("pCurriProperty2"));
	String	pCourseId		=	"";
	String	pProfId			=	"";
	String	pContentsType	=	"";
	String	pOnlineYn		=	"";
	String totCnt = (String)model.get("totCnt");
	String rinkUrl = "";
	int No = Integer.parseInt(totCnt);
	RowSet list= (RowSet)model.get("courseList");
	while (list.next()) {
		pCourseId	=	StringUtil.nvl(list.getString("course_id"));
		pProfId		=	StringUtil.nvl(list.getString("prof_id"));
		pContentsType = StringUtil.nvl(list.getString("contents_type"));
		pOnlineYn = StringUtil.nvl(list.getString("online_yn"));

		if(pCurriProperty2.equals("R")) {
			rinkUrl = StringUtil.nvl(model.get("selCourseLink"))+"&pCourseId="+pCourseId;
		} else {
			rinkUrl = "javascript:goRegist('"+pCourseId+"', '"+pProfId+"', '"+pContentsType+"', '"+pOnlineYn+"')";
		}
%>
								<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
									<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("course_name"))%></td>
									<td></td>
									<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("prof_name"))%></td>
									<td></td>
									<td class="s_tab04_cen"><a href="<%=rinkUrl%>"><b>[요청]</b></a></td>
								</tr>
<%		if(No > 1) { %>
								<tr class="s_tab03">
									<td colspan="5"></td>
								</tr>
<%
		}
		No = No-1;
		rinkUrl = "";
	}
	list.close();
%>
								<tr class="s_tab05">
									<td colspan="5"></td>
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
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a href="javascript:cancel_page()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>
</form>

<%@include file="../common/popup_footer.jsp" %>
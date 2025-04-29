<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%> 
<%@ page import ="com.edutrack.common.CommonUtil,com.edutrack.common.SiteNavigation"%>
<%@ page import ="com.edutrack.common.dto.UserMemDTO,com.edutrack.common.dto.CurriMemDTO,com.edutrack.common.dto.CodeParam"%> 
<%@ page import ="com.edutrack.currisub.dao.CurriSubCourseDAO,com.edutrack.currisub.dto.CurriCourseListDTO"%> 
<%@ page import ="com.edutrack.courseforum.dao.CourseForumInfoDAO,com.edutrack.courseforum.dto.CourseForumInfoDTO"%> 
<%@include file="../common/popup_header.jsp" %>
<%
	String pCourseId = (String)model.get("pCourseId");
%>
<Script Language="javascript">
	function goResultUserPaging()
	{
		document.location = "<%=CONTEXTPATH%>/CourseForumUser.cmd?cmd=courseForumResultUserPagingList&pCourseId=<%=pCourseId%>";
	}

	function goResultUserList()
	{	
		document.location = "<%=CONTEXTPATH%>/CourseForumUser.cmd?cmd=courseForumResultUserList&pCourseId=<%=pCourseId%>";
	}

   
</Script>
	<table width="100%" height="100%" border="0">
		<tr> 
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">토론결과</font></td>
			<td class="pop_topright"></td>
		</tr>
		<tr> 
			<td class="pop_midleft"></td>
			<td valign="top" class="pop_form">
				<!-- 내용 -->
				<table width="90%" height="100%" align="center">
					<tr height="97%">
						<td align="left" valign="top">
							<table width="100%" align="center">
<form name="f" method="post" action="<%=CONTEXTPATH%>/CourseForumInfo.cmd?cmd=courseForumInfoPagingList" >
								<tr>
									<td colspan="9" class="s_tabtit"><img 
										src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" width="18" height="16" align="absmiddle"><b>결과보기 (토론주제별현황)</b>
									</td>
								</tr>
								<tr>
									<td colspan="9" class="s_tabtit" align="right">
<script language=javascript>Button5("참여자현황-전체", "goResultUserList()", "");</script>&nbsp;
<script language=javascript>Button5("참여자현황-페이지", "goResultUserPaging()", "");</script>
									</td>
								</tr>
								<tr class="s_tab01">
									<td colspan="9"></td>
								</tr>
								<tr class="s_tab02">
									<td width="35">번호</td>
									<td class="s_tablien"></td>
									<td width="">제목</td>
									<td class="s_tablien"></td>
									<td width="135">토론기간</td>
									<td class="s_tablien"></td>
									<td width="70">게시물수</td>
									<td class="s_tablien"></td>
									<td width="50">조회수</td>
								</tr>
								<tr class="s_tab03">
									<td colspan="9"></td>
								</tr>
<%
	//이미 등록되어 있는 서브토론그룹리스트
	ArrayList list = (ArrayList)model.get("CourseForumInfoList");
	int No = list.size();
	String	start_end_date = "";

	for (int i=0; i < list.size(); i++) {
		CourseForumInfoDTO courseForumInfoList = (CourseForumInfoDTO)list.get(i);
		start_end_date = DateTimeUtil.getDateType(1,courseForumInfoList.getStartDate()) + " - " + DateTimeUtil.getDateType(1,courseForumInfoList.getEndDate());

%>
								<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
									<td class="s_tab04_cen"><%=No--%></td>
									<td></td>
									<td class="s_tab04"><%=courseForumInfoList.getSubject()%></td>
									<td></td>
									<td class="s_tab04_cen"><%=start_end_date%></td>
									<td></td>
									<td class="s_tab04_cen"><%=courseForumInfoList.getContentsCnt()%></td>
									<td></td>
									<td class="s_tab04_cen"><%=courseForumInfoList.getHitCnt()%></td>
								</tr>
<%
	} // end for
	
	if( list.size() <= 0) {
%>
								<tr>
									<td colspan="9" height="25" align="center" valign="middle">등록된 토론글이 없습니다.</td>
								</tr>
<%
	} // end if
%>
								<tr class="s_tab05">
									<td colspan="9"></td>
								</tr>
<!--tr>
	<td colspan="9" height="30" align="right" valign="middle"><script language=javascript>Button5("창닫기", "self.close()", "");</script></td>
</tr-->
							</table>
						</td>
					</tr>
</form>
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
				href="javascript:self.close()"><img 
				src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>

  
<%@include file="../common/popup_footer.jsp" %>
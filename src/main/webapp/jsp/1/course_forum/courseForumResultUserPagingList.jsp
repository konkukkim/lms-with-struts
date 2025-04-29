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
	int	forumInfoCnt	= Integer.parseInt((String)model.get("forumInfoCnt"));
%>
<form name="f" method="post" action="<%=CONTEXTPATH%>/CourseForumInfo.cmd?cmd=courseForumInfoPagingList" >
            
<script language="javascript">
	function goResultUserPaging()
	{
		document.location = "<%=CONTEXTPATH%>/CourseForumUser.cmd?cmd=courseForumResultUserPagingList&pCourseId=<%=pCourseId%>";
	}

	function goResultUserList()
	{	
		document.location = "<%=CONTEXTPATH%>/CourseForumUser.cmd?cmd=courseForumResultUserList&pCourseId=<%=pCourseId%>";
	}
	
	function goResultList()
	{	
		document.location = "<%=CONTEXTPATH%>/CourseForumInfo.cmd?cmd=courseForumResultList&pCourseId=<%=pCourseId%>";
	}
   
</script>
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
										src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" width="18" height="16" align="absmiddle"><b>결과보기 (참여자현황-페이지)</b>
									</td>
								</tr>
								<tr>
									<td colspan="9" class="s_tabtit" align="right">
<script language=javascript>Button5("토론참여자현황", "goResultList()", "");</script>&nbsp;
        <script language=javascript>Button5("참여자현황-전체", "goResultUserList()", "");</script>
									</td>
								</tr>
								<tr class="s_tab01">
									<td colspan="9"></td>
								</tr>
								<tr class="s_tab02">
									<td width="35">번호</td>
									<td class="s_tablien"></td>
									<td width="">수강생</td>
									<td class="s_tablien"></td>
<%
	for (int f=0; f < forumInfoCnt; f++ ){
%>
									<td width="50">토론<%=f+1%></td>
									<td class="s_tablien"></td>
<%
	}
%>
									<td width="70">총참여수</td>
								</tr>
								<tr class="s_tab03">
									<td colspan="9"></td>
								</tr>
<%
	
	ListDTO listObj = (ListDTO)model.get("CourseForumUserList"); 

	String totCnt = (String)model.get("totCnt");
	int No = Integer.parseInt(totCnt);
	int	i	=	0;
	RowSet list= listObj.getItemList();	
	int tcontents 	= 	0;
	int tconnect	=	0;

	if (listObj.getItemCount() > 0) {
		while (list.next()) {
			i++;
%>
								<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
									<td class="s_tab04"><%=No%></td>
									<td></td>
									<td class="s_tab04"><%=CommonUtil.getUserName(SYSTEMCODE, StringUtil.nvl(list.getString("user_id")))%>(<%=StringUtil.nvl(list.getString("user_id"))%>)</td>
									<td></td>
<%
			for (int f=0; f < forumInfoCnt; f++ ){
%>
									<td class="s_tab04"><%=list.getInt("contents"+f)%>,<%=list.getInt("connect"+f)%></td>
									<td></td>
<%
				tcontents = tcontents + list.getInt("contents"+f);
				tconnect = tconnect + list.getInt("connect"+f);
			}
%>
									<td class="s_tab04_cen"><%=tcontents%>개,<%=tconnect%>회</td>
								</tr>
<%
			tcontents 	= 0;
			tconnect		= 0; 
			No = No - 1; 	
		} 
		list.close();
	}
%>	
								<tr class="s_tab05">
									<td colspan="11"></td>
								</tr>
								<tr>
									<td colspan="11" height="30" align="center" valign="middle"><%= listObj.getPagging(SYSTEMCODE,"goPage") %></td>
								</tr>
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

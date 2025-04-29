<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.common.dto.CodeParam"%>
<%@include file="../common/community_header.jsp" %>
<%
	String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;
%>
<script language="javascript">
   function Search_User(){
   	  var f = document.f;

      if(!validate(f)) return;

      f.submit();
   }

   function Change_Code(){
      var f = document.f;

      f.submit();
   }

   function Show_UserInfo(userid){
      var Page = "/User.cmd?cmd=userInfoShow&userId="+userid;
	  ShowInfo	=	window.open(Page,"userinfoshow", "left=162,top=100,toolbar=0,directories=0,status=0,menubar=0,width=400,height=300,scrollbars=no");
  }
</script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>회원보기</b></font></td>
									<!-- // community title -->
									<!-- history -->
									<td class="com_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									</td>
								</tr>
<%
	ListDTO listObj = (ListDTO)model.get("commMembersPagingList");
	String pFields = (String)model.get("pFields");
	String pValue = (String)model.get("pValue");
%>
<%= listObj.getPageScript("f", "curPage", "goPage") %>
								<tr valign="top">
									<td colspan="2" class="com_content_top">
									<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/Community.cmd?cmd=commMembersPagingList" >
<input type="hidden" name="curPage" value="1">
											<tr class="com_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="com_tab02">
												<td width="70">번호</td>
												<td class="com_tablien"></td>
												<td width="130">회원명</td>
												<td class="com_tablien"></td>
												<td width="140">닉네임</td>
												<td class="com_tablien"></td>
												<td width="140">가입일</td>
												<td class="com_tablien"></td>
												<td width="110">회원등급</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
    int No = listObj.getStartPageNum();
    int i =0;
    RowSet list= listObj.getItemList();
    String userLevel 	= "";
    String userName 	= "";
    String userId 		= "";
    String userNick		= "";
    String regDate 		= "";
    String levelName	= "";

    if(listObj.getItemCount() > 0){
        while(list.next()){
            i++;
            userLevel	=	StringUtil.nvl(list.getString("user_level"));
            userName	=	StringUtil.nvl(list.getString("user_name"));
            userId		=	StringUtil.nvl(list.getString("user_id"));
            userNick	=	StringUtil.nvl(list.getString("user_nick"));
            regDate		=	StringUtil.nvl(list.getString("reg_date"));
            levelName	=	CommonUtil.getCodeSoName(list.getString("system_code"), "103", userLevel);
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04_cen"><%=userName%>(<%=userId%>)</td>
												<td></td>
												<td class="s_tab04_cen"><%=userNick%></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,regDate)%></td>
												<td></td>
												<td class="s_tab04_cen"><%=levelName%></td>
											</tr>
<%
			if (i < listObj.getItemCount()) {
%>
											<tr class="com_tab03">
												<td colspan="11"></td>
											</tr>
<%
			} else {
%>
											<tr class="com_tab05">
												<td colspan="11"></td>
											</tr>
<%
			}
            No = No-1;
        }
        list.close();
    }else{
%>
											<tr>
												<td class="com_tab04_cen" colspan="11">동아리 회원이 없습니다.</td>
											</tr>
											<tr class="com_tab05">
												<td colspan="11"></td>
											</tr>
<%
	}
%>
											<tr>
												<td class="s_list_btn" colspan="11" height="10" align="right">
												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
<%
	if(listObj.getTotalItemCount() > 0){
%>
													<table valign=top height="25">
														<tr>
															<td><%= listObj.getPagging(SYSTEMCODE,"goPage") %></td>
														</tr>
													</table>
<%
	}
%>
													<table>
														<tr>
															<td align=middle height=30>
																<select name=pFields>
																	<option value="user_name" <% if(pFields.equals("") || pFields.equals("user_name")){%>selected<%}%>>이름</option>
				                                                    <option value="user_id" <% if(pFields.equals("user_id")){%>selected<%}%>>아이디</option>
				                                                    <option value="user_nick" <% if(pFields.equals("user_nick")){%>selected<%}%>>닉네임</option>
																</select>
																<input maxlength=30 size=22 name=pValue value="<%=pValue%>" dispName="검색어" lenMCheck="2" lenCheck="20">
																<a href="javascript:Search_User();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle> </a>
															</td>
														</tr>
													</table>
												</td>
											</tr>
</form>
<!-- form end -->
											<!-- // 페이지 리스트, 검색부분 -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/community_footer.jsp" %>


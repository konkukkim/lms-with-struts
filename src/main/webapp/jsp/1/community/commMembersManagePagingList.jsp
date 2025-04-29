<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.common.dto.CodeParam"%>
<%@ page import ="com.edutrack.community.dto.CommInfoDTO"%>
<%@include file="../common/community_header.jsp" %>
<%
	CommInfoDTO commInfoDto = (CommInfoDTO)model.get("commInfoDto");
	String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;
%>
<script language="javascript">
   function Search_User(){
   	  var f = document.f;

      if(!validate(f)) return;

      f.submit();
   }


	function Show_UserInfo(userid){
	  var Page = "/User.cmd?cmd=userInfoShow&userId="+userid;
	  ShowInfo	=	window.open(Page,"userinfoshow", "left=162,top=100,toolbar=0,directories=0,status=0,menubar=0,width=380,height=400,scrollbars=yes");
	}

	function deleteCommMember(member_id) {
		var msg = "회원을 삭제하려 합니다.\n삭제 하시겠습니까?";

		var st = confirm(msg);
		if(st==true){
	       	document.location.href="/Community.cmd?cmd=commMemberDelete&pMemberId="+member_id;
		}
     }

	function changeUseYn(use_yn, member_id) {
		var msg = "회원가입을 승인합니다.\n승인 하시겠습니까?";

		var st = confirm(msg);
		if(st==true){
	       	document.location.href="/Community.cmd?cmd=commMemberUseYnChange&pUseYn="+use_yn+"&pMemberId="+member_id;
		}
     }

     // 커뮤니티정보 팝업 윈도우
	function showCommInfo(comm_id) {
		var page = "/CommManage.cmd?cmd=commInfoShow&pCommId="+comm_id;
		show =	window.open(page, "showCommInfo", "toolbar=false,directories=false,status=false,menubar=false,width=600,height=450,scollbar=true,resizable=yes");
	}

  	function Change_Master(commid){
	   var winOption = "left="+windowLeftPosition(100)+",top="+windowTopPosition(50)+",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=yes,resizable=no,width=430,height=200";
	   var loc = "/CommManage.cmd?cmd=changeMemberList&pCommId="+commid;
	   var ShowInfo = window.open(loc,"commChangeMaster",winOption);
	}
</script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>회원관리</b></font></td>
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
								<tr valign="top">
									<td colspan="2" class="com_content_top">
									<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<%
	if (!commInfoDto.getRegistType().equals("1")) {
		ListDTO newList = (ListDTO)model.get("commNewMembersPagingList");
%>
<%=newList.getPageScript("f1", "curPageNew", "goPage1")%>
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/Community.cmd?cmd=commMembersManagePagingList" >
<input type="hidden" name="curPageNew" value="1">
											<tr class="com_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="com_tab02">
												<td width="70">번호</td>
												<td class="com_tablien"></td>
												<td width="">회원명</td>
												<td class="com_tablien"></td>
												<td width="140">닉네임</td>
												<td class="com_tablien"></td>
												<td width="140">가입일</td>
												<td class="com_tablien"></td>
												<td width="110">회원등급<br>승인/탈퇴</td>

											</tr>
											<tr class="com_tab03">
												<td colspan="11"></td>
											</tr>
<%
	    int NoNew = newList.getStartPageNum();
	    RowSet list1 =  newList.getItemList();

	    if (newList.getItemCount() > 0) {

	        while (list1.next()) {
	            String userLevel	= "";
	            String userName		= "";
	            String userId		= "";
	            String userNick		= "";
	            String regDate		= "";

	            userLevel	= StringUtil.nvl(list1.getString("user_level"), "");
	            userName	= StringUtil.nvl(list1.getString("user_name"));
	            userId		= StringUtil.nvl(list1.getString("user_id"));
	            userNick	= StringUtil.nvl(list1.getString("user_nick"));
	            regDate		= StringUtil.nvl(list1.getString("reg_date"));
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="com_tab04_cen"><%=NoNew%></td>
												<td></td>
												<td class="com_tab04_cen"><a href="javascript:Show_UserInfo('<%=userId%>')"><%=userName%>(<%=userId%>)</a></td>
												<td></td>
												<td class="com_tab04_cen">><%=userNick%></td>
												<td></td>
												<td class="com_tab04_cen"><%=DateTimeUtil.getDateType(1,regDate)%></td>
												<td></td>
												<td class="com_tab04_cen"><a href="javascript:changeUseYn('Y','<%=userId%>')">[승 인]</a></td>

											</tr>
<%				if(NoNew > 1) { %>
											<tr class="com_tab03">
												<td colspan="11"></td>
											</tr>
<%
				}
	            NoNew = NoNew-1;
	        }
	        list1.close();
	    }else{
%>
											<tr>
												<td class="com_tab04_cen" colspan="11">신규가입 회원이 없습니다.</td>
											</tr>
<%
		}
%>
											<tr class="com_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="10" align="right">
												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
<%
		if(newList.getTotalItemCount() > 0){
%>
													<table valign=top height="25">
														<tr>
															<td>
																<%= newList.getPagging(SYSTEMCODE,"goPage") %>
															</td>
														</tr>
													</table>
<%
		}
	}

    ListDTO commMemberList = (ListDTO)model.get("commMembersPagingList");
    String pFields = (String)model.get("pFields");
    String pValue = (String)model.get("pValue");
%>
<%= commMemberList.getPageScript("f2", "curPage", "goPage2") %>
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("시샵변경", "Change_Master('<%=COMMINFO.commId%>')", "");</script>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="com_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="com_tab02">
												<td width="70">번호</td>
												<td class="com_tablien"></td>
												<td width="">회원명</td>
												<td class="com_tablien"></td>
												<td width="140">닉네임</td>
												<td class="com_tablien"></td>
												<td width="140">가입일</td>
												<td class="com_tablien"></td>
												<td width="110">회원등급<br>승인/탈퇴</td>

											</tr>
											<tr class="com_tab03">
												<td colspan="11"></td>
											</tr>
<%
		int No = commMemberList.getStartPageNum();
    	RowSet list= commMemberList.getItemList();

	    if (commMemberList.getItemCount() > 0) {

	        while (list.next()) {
	            String userLevel	= StringUtil.nvl(list.getString("user_level"));
	            String userName		= StringUtil.nvl(list.getString("user_name"));
	            String userId		= StringUtil.nvl(list.getString("user_id"));
	            String userNick		= StringUtil.nvl(list.getString("user_nick"));
	            String regDate		= StringUtil.nvl(list.getString("reg_date"));
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="com_tab04_cen"><%=No%></td>
												<td></td>
												<td class="com_tab04_cen"><a href="javascript:Show_UserInfo('<%=userId%>')"><%=userName%>(<%=userId%>)</a></td>
												<td></td>
												<td class="com_tab04_cen"><%=userNick%></td>
												<td></td>
												<td class="com_tab04_cen"><%=DateTimeUtil.getDateType(1,regDate)%></td>
												<td></td>
												<td class="com_tab04_cen">
<%
				if(!userLevel.equals("M")){
%>
													<a href="javascript:deleteCommMember('<%=userId%>')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/sbt_exit.gif" align="absmiddle" width="26" height="15" border="0"></a>
<%
				} else {
					out.println("시샵");
				}
%>
												</td>
											</tr>
<%				if(No > 1) { %>
											<tr class="com_tab03">
												<td colspan="11"></td>
											</tr>
<%
				}
	            No = No-1;
	        }
	        list.close();
	    } else {
%>
											<tr>
												<td class="com_tab04_cen" colspan="11">동아리 회원이 없습니다.</td>
											</tr>
<%
		}
%>
											<tr class="com_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="10" align="right">
												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
<%
	if(commMemberList.getTotalItemCount() > 0){
%>
													<table valign=top height="25">
														<tr>
															<td>
																<%= commMemberList.getPagging(SYSTEMCODE,"goPage") %>
															</td>
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
																<a href="javascript:Search_User()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- // 페이지 리스트, 검색부분 -->
</form>
<!-- form end -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/community_footer.jsp" %>

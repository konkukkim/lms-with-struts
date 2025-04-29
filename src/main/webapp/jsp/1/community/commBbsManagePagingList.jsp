<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.common.dto.CodeParam"%>
<%@include file="../common/community_header.jsp" %>
<%
	String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;
%>
<script language="javascript">
   function goAdd(){
      var doc = "<%=CONTEXTPATH%>/Community.cmd?cmd=commBbsInfoWrite&pGubun=write";
      document.location = doc;
   }

   function Edit_BbsInfo(bbsid){
      var doc = "<%=CONTEXTPATH%>/Community.cmd?cmd=commBbsInfoWrite&pGubun=edit&pBbsId="+bbsid;
      document.location = doc;
   }
</script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>게시판관리</b></font></td>
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
<% 	ListDTO listObj = (ListDTO)model.get("commBbsManagePagingList");
%>
<%= listObj.getPageScript("f", "curPage", "goPage") %>
								<tr valign="top">
									<td colspan="2" class="com_content_top">
									<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/Community.cmd?cmd=commMembersPagingList" >
<input type="hidden" name="curPageNew" value="1">
											<tr>
												<td colspan="11" class="com_tabtit">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("추가","goAdd()", "");</script>&nbsp;
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
												<td width="">게시판명</td>
												<td class="com_tablien"></td>
												<td width="90">첨부파일</td>
												<td class="com_tablien"></td>
												<td width="110">생성일</td>
												<td class="com_tablien"></td>
												<td width="90">사용여부</td>

											</tr>
											<tr class="com_tab03">
												<td colspan="11"></td>
											</tr>
<%
    int		No		=	listObj.getStartPageNum();
    RowSet	list	=	listObj.getItemList();
    int		cnt		=	0;

    if (listObj.getItemCount() > 0) {
        while (list.next()) {
        	cnt++;
            int bbsId=(list.getInt("bbs_id"));
            String bbsName=StringUtil.nvl(list.getString("bbs_name"));
            String bbsType=StringUtil.nvl(list.getString("bbs_type"));
            String fileUseYn=StringUtil.nvl(list.getString("file_use_yn"));
            String useYn=StringUtil.nvl(list.getString("use_yn"));
            String regDate=StringUtil.nvl(list.getString("reg_date"));
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="com_tab04_cen"><%=cnt%></td>
												<td></td>
												<td class="com_tab04"><a href="javascript:Edit_BbsInfo('<%=bbsId%>')"><%=bbsName%></a></td>
												<td></td>
												<td class="com_tab04_cen">
<%
    		if(fileUseYn.equals("Y"))
    			out.println("가능");
    		else
    			out.println("불가능");
%>
												</td>
												<td></td>
												<td class="com_tab04_cen"><%=DateTimeUtil.getDateType(1,regDate)%></td>
												<td></td>
												<td class="com_tab04_cen">
<%
    		if(useYn.equals("Y"))
    			out.println("사용");
    		else
    			out.println("사용안함");
%>
												</td>

											</tr>
											<tr class="com_tab03">
												<td colspan="11"></td>
											</tr>
<%
            No = No-1;
        }
        list.close();
    }else{
%>
											<tr>
												<td class="com_tab04_cen" colspan="11">개설된 게시판이 없습니다.</td>
											</tr>
<%
	}
%>
											<tr class="com_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="10" align="right"></td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
<%
	if(listObj.getTotalItemCount() > 0){
%>
													<table valign=top height="25">
														<tr>
															<td><%= listObj.getPagging(SYSTEMCODE,"goPage") %><td>
														</tr>
													</table>
<%
	}
%>
												</td>
											</tr>
											<!-- // 페이지 리스트, 검색부분 -->
</form>
<!-- form start -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/community_footer.jsp" %>

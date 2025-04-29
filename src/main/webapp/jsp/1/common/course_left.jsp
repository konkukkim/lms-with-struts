<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import = "com.edutrack.progauthor.dao.ProgMenuDAO"%>
<%@ page import ="com.edutrack.progauthor.dto.ProgMenuDTO"%>
									<table class="c_left_menu" align="right" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_classroom_bg.gif">
										<tr> 
											<td height="27" style="padding:0 0 15 0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_classroom.gif" alt="강의실 메뉴"></td>
										</tr>
<%
    if(CURRITYPE.equals("R")){


	    ProgMenuDAO MenuDao = new ProgMenuDAO();
	    ProgMenuDTO MenuInfo = new ProgMenuDTO();
	    ArrayList menuList = MenuDao.progMenuList(SYSTEMCODE, PMODE, USERTYPE, null, null, "Y" );

	    int subMenuCnt = 0;
	    int tmpSubCnt = 0;
	    int menuCnt = 0;



        // 메뉴 코드화....
        for(int i=0; i< menuList.size() ; i++){
            MenuInfo = (ProgMenuDTO)menuList.get(i);

            subMenuCnt = MenuInfo.getCntSubMenu() ;

            // 서브메뉴가 없을 경우..
            if(subMenuCnt==0){
%>
										<tr> 
											<td class="c_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet01.gif" width="16" height="11" align="absmiddle"><a href="<%=CONTEXTPATH%><%=MenuInfo.getMenuUrl() %>&MENUNO=0&pMode=<%=PMODE%>&pCourseId=<%=COURSEID%>&pCourseName=<%=COURSENAME%>" class="c_l_menu"><%=MenuInfo.getMenuName() %></a></td>
										</tr>
<% 	        }
            // 서브 메뉴가 존재할 시..
            else {

            tmpSubCnt = subMenuCnt ;
            menuCnt++;

%>
										<tr> 
											<td class="c_left_pd"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_blet01.gif" width="16" height="11" align="absmiddle"><a style="cursor:hand;" onClick="return toggleMenu('menu<%=menuCnt %>')" <%if (MENUNO.equals(String.valueOf(menuCnt))){%>class="c_l_menu"<%}%>><%=MenuInfo.getMenuName() %></a></td>
										</tr>
										<tr>
											<td>
												<!-- <%=MenuInfo.getMenuName() %> 서브메뉴 시작 -->
												<SPAN ID="menu<%=menuCnt %>" style="display:none;">
													<table class="l_menu_s">
														<% for(int j=0; j< tmpSubCnt ; j++){
												        i++;
												        MenuInfo = (ProgMenuDTO)menuList.get(i);
												        %>
												        <tr>
															<td height="22" align="left"><a href="<%=CONTEXTPATH %><%=MenuInfo.getMenuUrl() %>&MENUNO=<%=menuCnt %>&pMode=<%=PMODE%>&pCourseId=<%=COURSEID%>&pCourseName=<%=COURSENAME%>">- <%=MenuInfo.getMenuName() %></a></td>
														</tr>
														<%} %>
													</table>
												</SPAN>
												<!-- <%=MenuInfo.getMenuName() %> 서브메뉴 끝 -->
											</td>
										</tr>
<%
            }

        } // end for

	}
%>
										<tr> 
											<td height="15"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/left/left_classroom_bottom.gif" width="153" height="15"></td>
										</tr>
									</table>
<%
	if(!("0").equals(MENUNO) && MENUNO != null && !MENUNO.equals("")){
%>
<script language="javascript">
toggleMenu('menu<%=MENUNO%>');
</script>
<%
	}
%>
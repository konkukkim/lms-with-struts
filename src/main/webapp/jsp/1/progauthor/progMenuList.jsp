<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.common.dto.CodeParam"%>
<%@ page import ="com.edutrack.progauthor.dto.ProgMenuDTO"%>
<%@include file="../common/header.jsp" %>
<style type="text/css">
    #list {
      margin:0;
      margin-top:10px;
      padding:0;
      list-style-type: none;
    }
    #list li {
      cursor:move;
    }

</style>

<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/popupbox.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/scriptaculous/src/dragdrop.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/progauthor/progMenuList.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CodeSoWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ProgMenuListWork.js"></script>
<% 
   String pWorkGubun = (String)model.get("pWorkGubun");
   String pMenuUser = (String)model.get("pMenuUser");
%>

                                        <!-- 내용 --> 
										<table width="670" align="center">
										<form name="menuList" method="post">
											<!-- 메뉴권한관리 -->
											<tr>
												<td valign=top>
													<!-- selectbox, 메뉴추가버튼, 메뉴순서저장버튼 -->
													<table width="650" border=0 align="center">
													
														<tr>
															<td class=s_btn01 colspan=13>
																<table width="100%" align=center>
																	<tr>
																		<td width="50%">
<%
	CodeParam param1 = new CodeParam();
	param1.setSystemcode(SYSTEMCODE);
	param1.setType("select");
	param1.setName("pMenuUser");
	param1.setEvent("chgMenuUser()");
	//param1.setFirst("---사용자 타입---");
	param1.setSelected(pMenuUser);
	out.print(CommonUtil.getSoCode(param1, "101"));


	CodeParam param2 = new CodeParam();
	param2.setSystemcode(SYSTEMCODE);
	param2.setType("select");
	param2.setName("pWorkGubun");
	param2.setSelected(pWorkGubun);
	param2.setEvent("chgMenuUser()");
	//param2.setFirst("---메뉴 타입---");
	out.print(CommonUtil.getSoCode(param2, "501"));
%>
																		</td>
																		<td align=right width="50%" height=30>
																		    <Script Language=javascript>Button5("메뉴추가", "Create_Prog()", "");</Script>
																		    <Script Language=javascript>Button5("메뉴순서저장", "Save_Order()", "");</Script>
																		 </td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr class=s_tab01>
															<td colspan=13></td></tr>
														<tr>
														<tr>
															<td height="5"></td>
														</tr>
													</table>
													<!-- 메뉴 -->
                                        			<div id="menuListDiv"></div>
        									    </td>
        									</tr>
											<tr class="s_tab05">
												<td colspan="13"></td>
											</tr>
											<tr>
												<td colspan="13"height="40" align="center" valign="middle">
												    <table width="100%">
												        <tr>
												            <td width="50%">
        												    <p><font color=red>*</font> Drag and drop 으로 메뉴순서를 조정할 수 있습니다!</p>
        												    </td>
        												    <td width="50%" align=right>
        												    <Script Language=javascript>Button5("메뉴추가", "Create_Prog()", "");</Script>
        												    <Script Language=javascript>Button5("메뉴순서저장", "Save_Order()", "");</Script>
        												    </td>
        												</tr>
        										    </table>
												</td>
											</tr>
                                            </form>
											<!-- // 메뉴권한관리 -->
										</table>
										<!-- // 내용 -->
									</td>
								</tr>
							</table>	
												
<%@include file="../common/footer.jsp" %>

<Script Language=javascript>

    
    var popupbox1 = new PopupBox("메뉴권한관리", "style=normal,width=500,height=400, bgcolor=white, modal=yes,resizable=yes,move=yes,titlebar=yes,position=absolute,top=300,left=400");

    init("<%=SYSTEMCODE %>", "<%=CONTEXTPATH %>", "<%=pMenuUser %>" , "<%=pWorkGubun %>");


</Script>    

<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumInfoDTO"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumContentsDTO"%> 
<%@include file="../common/header.jsp" %>
<%
    String	pCurriCode	=	StringUtil.nvl(model.get("pCurriCode"));
    int		pCurriYear	=	StringUtil.nvl(model.get("pCurriYear"), 0);
    int		pCurriTerm	=	StringUtil.nvl(model.get("pCurriTerm"), 0);
    String	pCourseId 	= 	StringUtil.nvl(model.get("pCourseId"));
    int		pForumId 	= 	StringUtil.nvl(model.get("pForumId"), 0);
    String	pGubun	 	= 	StringUtil.nvl(model.get("pGubun"));
    
    CourseForumInfoDTO courseForumInfo = (CourseForumInfoDTO)model.get("courseForumInfo"); 
    String pInfoSubject		= StringUtil.nvl(courseForumInfo.getSubject());
    String pInfoContents    = StringUtil.nvl(courseForumInfo.getContents());
    String pCurriName		= StringUtil.nvl(courseForumInfo.getCurriName());
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/PublishCurriSubWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/publish_curri_sub/publishForumContents.js"></script>

										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<form name="f" method="post" action="<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecContentsList" >
<input type="hidden" name="curPage"     value="">
<input type="hidden" name="pCurriCode"	value="<%=pCurriCode%>">
<input type="hidden" name="pCurriYear" 	value="<%=pCurriYear%>">
<input type="hidden" name="pCurriTerm" 	value="<%=pCurriTerm%>">
<input type="hidden" name="pCourseId" 	value="<%=pCourseId%>">
<input type="hidden" name="pForumId"    value="<%=pForumId%>">
<input type="hidden" name="pGubun"    	value="<%=pGubun%>">
<input type="hidden" name="currentDate"    	value="<%=StringUtil.nvl(CommonUtil.getCurrentDate()).substring(0, 8)%>">
											<tr>
												<td colspan="11" class="s_btn01" align="right">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td class="s_btn01">
																<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" align="absmiddle">&nbsp;<b><%=pCurriName%></b>
															</td>
															<td class="s_btn01" align="right">
<script language=javascript>Button5("과정리스트", "goCurriList()", "");</script>
&nbsp;<script language=javascript>Button5("강의목록", "goContents()", "");</script>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">주제</td>
												<td class="s_tab_view02" width="550" colspan=3><%=pInfoSubject %></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">토론개요</td>
												<td class="s_tab_view02"  colspan=3><%=StringUtil.getHtmlContents(pInfoContents) %></td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
										</table>
										<br>	
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
	                						<tr class="s_tab01">
												<td colspan="20"></td>
											</tr>
											<tr class="s_tab02">
												<td width="070">번호</td>
												<td class="s_tablien"></td>
												<td width="330" >제목</td>
												<td class="s_tablien"></td>
												<td width="100" >등록자</td>
												<td class="s_tablien"></td>
												<td width="100" >등록일</td>
												<td class="s_tablien"></td>
												<td width="070" >조회수</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="20"></td>
											</tr>
										    <!-- 리스트 -->
										    <tr>
												<td colspan="20"><div id="courseForumContentsList" style="width:100%;display:none"></div></td>
											</tr>
	                        			  	<!-- 리스트 -->	
	                        			  	<tr class="s_tab05">
												<td colspan="20"></td>
											</tr>
											<tr>
	                        			  	    <td class="s_list_btn" colspan="9" height="30" align="right">
&nbsp;<Script Language=Javascript>Button3('글쓰기','goAdd()','')</Script>
												</td>
											</tr>	
	                        			  	<tr>
												<td colspan="11" align=center>
													<table valign=top height="25">
														<tr>
															<td>
																<div id="getPagging" style="width:100%;display:none"></div>
															</td>
														</tr>
													</table>
													<table>
														<tr>
															<td align=middle height=30>
															    <select name="pSearchKey">
	            				                                    <option value="subject" >제목</option>
	            				                                    <option value="keyword" >내용</option>
	            				                                </select>
															    <input maxlength=30 size=22 name=pKeyWord value="">
															    <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle Style="cursor:hand" onClick="javascript:goSearchList();"> 
															</td>
														</tr>
													</table>
												</td>
											</tr>
	                                        </form>
											<!-- // 페이지 리스트 -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
<Script Language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>');
</Script>

<%@include file="../common/footer.jsp" %>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.curristudent.dto.StudentDTO,com.edutrack.common.dto.CodeParam"%>
<%@include file="../common/header.jsp" %>
<%
	String pCurriCode = (String)model.get("pCurriCode");
	String pCurriYear = (String)model.get("pCurriYear");
	String pCurriTerm = (String)model.get("pCurriTerm");
	String pSTarget = (String)model.get("pSTarget");
	String pSWord = (String)model.get("pSWord");
	String selCurri = pCurriCode+"|"+pCurriYear+"|"+pCurriTerm;
	ListDTO listObj = (ListDTO)model.get("studentList");
	int contentsCnt = Integer.parseInt((String)model.get("contentsCnt"));

	int iCurPage = listObj.getCurrentPage();
%>
<%= listObj.getPageScript("f", "curPage", "goPage") %>
<script language="javascript">
	function goSearch() {
		var f	=	document.searchForm;
		f.submit();
	}

	function checkSubmit() {
		var f = document.form;
		f.submit();
	}
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- main 시작 -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/Student.cmd?cmd=completeStudentList2&MENUNO=3">
<input type="hidden" name="curPage" value="<%=iCurPage%>">
<input type="hidden" name="pCurriCode" value="<%=pCurriCode%>">
<input type="hidden" name="pCurriYear" value="<%=pCurriYear%>">
<input type="hidden" name="pCurriTerm" value="<%=pCurriTerm%>">
</form>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<form name="form" method="post" action="<%=CONTEXTPATH%>/Student.cmd?cmd=completeStudentRegist&MENUNO=3">
<input type="hidden" name="curPage" value="<%=iCurPage%>">
<input type="hidden" name="pCurriCode" value="<%=pCurriCode%>">
<input type="hidden" name="pCurriYear" value="<%=pCurriYear%>">
<input type="hidden" name="pCurriTerm" value="<%=pCurriTerm%>">
											<tr>
												<td colspan="9" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
<% if(CommonUtil.getAuthorCheck(request,  "C") || CommonUtil.getAuthorCheck(request,  "U") )/* 권한체크 */  { %><script language=javascript>Button5("수료구분일괄변경", "checkSubmit()", "");</script>&nbsp;<%	}	%>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="9"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="">이름(ID)</td>
												<td class="s_tablien"></td>
												<td width="140">수강기간</td>
												<td class="s_tablien"></td>
												<td width="140">진도율</td>
												<td class="s_tablien"></td>
												<td width="110">수료구분</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="9"></td>
											</tr>
<%
	int No = listObj.getStartPageNum();
	RowSet list= listObj.getItemList();
	String strStatusC = "";
	String strStatusF = "";
	int processNum = 0;
	double markPercent = 0.0;

	if (listObj.getItemCount() > 0) {
		while (list.next()) {
			if (StringUtil.nvl(list.getString("enroll_status")).equals("C")) {
				strStatusC = " checked";
				strStatusF = "";
			}
			else {
				strStatusC = "";
				strStatusF = " checked";
			}
			markPercent = 0.0;
			if (contentsCnt > 0) {
				markPercent = list.getInt("mark_cnt")/Double.parseDouble(Integer.toString(contentsCnt))*100;
			}
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("user_name"))%> (<%=StringUtil.nvl(list.getString("user_id"))%>)</td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("servicestart_date")))%> ~ <%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("serviceend_date")))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=markPercent%> %</td>
												<td></td>
												<td class="s_tab04_cen">
													<input type="radio" name="pStatus[<%=processNum%>]" value="C" class="no" <%=strStatusC%>><font color="blue">수료</font>
													<input type="radio" name="pStatus[<%=processNum%>]" value="F" class="no" <%=strStatusF%>><font color="red">미수료</font>
													<input type="hidden" name="pStudentId[<%=processNum%>]" value="<%=StringUtil.nvl(list.getString("user_id"))%>">
												</td>
												<td></td>
												<td class="s_tab04_cen"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="9"></td>
											</tr>
<%
			No = No-1;
			processNum++;
		}

	}
	else {
%>
											<tr>
												<td class="s_tab04_cen" colspan="9">
													등록된 수강 정보가  없습니다.
					  								<input style="display:none" type="checkbox" name="pChk">
					  							</td>
											</tr>
<%
	}
	list.close();
%>
											<tr class="s_tab05">
												<td colspan="9"></td>
											</tr>
											<tr>
												<td class="" colspan="9" height="10" align="right"></td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="9" align=center>
													<table valign=top height="25">
														<tr>
															<td>
																<%= listObj.getPagging(SYSTEMCODE,"goPage") %>
															</td>
														</tr>
													</table>
<input type="hidden" name="processNum" value="<%=processNum%>">
</form>
<form name="searchForm" method="post" action="<%=CONTEXTPATH%>/Student.cmd?cmd=completeStudentList2&MENUNO=3" >
<input type="hidden" name="curPage" value="<%=iCurPage%>">
<input type="hidden" name="pCurriCode" value="<%=pCurriCode%>">
<input type="hidden" name="pCurriYear" value="<%=pCurriYear%>">
<input type="hidden" name="pCurriTerm" value="<%=pCurriTerm%>">
													<table>
														<tr>
															<td align=middle height=30>
																<select name=pSTarget>
																	<option value="a.user_id" <%if(pSTarget.equals("a.user_id")){%>selected<%}%>>ID</option>
																	<option value="b.user_name" <%if(pSTarget.equals("b.user_name")){%>selected<%}%>>이름</option>
																	<option value="c.curri_name" <%if(pSTarget.equals("c.curri_name")){%>selected<%}%>>과정명</option>
																</select>
																<input type="text" name="pSWord" size="30" value="<%=pSWord%>">
																<a href="javascript:goSearch();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
															</td>
														</tr>
													</table>
</form>
												</td>
											</tr>
											<!-- // 페이지 리스트, 검색부분 -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>



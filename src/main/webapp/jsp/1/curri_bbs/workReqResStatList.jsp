<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet, com.edutrack.framework.persist.ListDTO, com.edutrack.curribbs.dto.CurriWorkReqResDTO"%>
<%@include file="../common/header.jsp" %>
<%
	String  pWhere	= (String)model.get("pWhere");
	String  pSTarget	= (String)model.get("pSTarget");
	String  pSWord	= (String)model.get("pSWord");
%>
<script language="javascript">
	function goAdd(){
		location.href="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=workReqResWrite&pRegType=Regist&pMethodType=Insert&pWhere=<%=pWhere%>";
	}

	function replyAdd(param, status){
		var f = document.Input;
		f.pSeqNo.value = param;
		f.pRegType.value = "Reply";
		if(status == "0") f.pMethodType.value = "Insert";
		else f.pMethodType.value = "Update";
		f.action="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=workReqResWrite&pWhere=<%=pWhere%>";
		f.submit();
	}

	function searchList() {
		document.Search.submit();
	}
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<%
	ListDTO listObj = (ListDTO)model.get("statList");
	int iTotCnt 		= 	listObj.getTotalItemCount();
 	int iCurPage 		= 	listObj.getCurrentPage();
 	int iDispLine 		= 	listObj.getListScale();
%>
<%= listObj.getPageScript("Input", "curPage", "goPage") %>
<form name=Input method=post>
<input type="hidden" name="curPage" value="<%=iCurPage%>">
<input type=hidden name=pRegType value="">
<input type=hidden name=pMethodType value="">
<input type=hidden name=pSeqNo value="">
</form>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="">교수자</td>
												<td class="s_tablien"></td>
												<td width="90">요청수</td>
												<td class="s_tablien"></td>
												<td width="90">처리수</td>
												<td class="s_tablien"></td>
												<td width="90">미처리수</td>
												<td class="s_tablien"></td>
												<td width="100">평가점수</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	String totCnt = (String)model.get("totCnt");
	int No = Integer.parseInt(totCnt);
 	RowSet list= listObj.getItemList();

 	if(listObj.getItemCount() > 0) {

		while(list.next()){
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("user_name")) + "("+ StringUtil.nvl(list.getString("prof_id")) + ")"%></td>
												<td></td>
												<td class="s_tab04_cen"><%=list.getInt("totCnt")%></td>
												<td></td>
												<td class="s_tab04_cen"><%=list.getInt("yesCnt")%></td>
												<td></td>
												<td class="s_tab04_cen"><%=list.getInt("noCnt")%></td>
												<td></td>
												<td class="s_tab04_cen"><%=list.getInt("pointSum")%></td>
											</tr>
<%			if(No > 1) { %>
											<tr class="s_tab03">
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
												<td class="s_tab04_cen" colspan="11">등록된 정보가  없습니다.</td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="10" align="right"></td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<table valign=top height="25">
														<tr>
															<td><%= listObj.getPagging(SYSTEMCODE,"goPage") %></td>
														</tr>
													</table>
													<table>
<form name=Search method=post aciton="/">
														<tr>
															<td align=middle height=30>
																<select name=pSTarget>
																	<option value="a.prof_id" <%=pSTarget.equals("a.prof_id") ? "selected" : ""%>>교수자ID</option>
	  																<option value="b.user_name" <%=pSTarget.equals("b.user_name") ? "selected" : ""%>>이름</option>
																</select>
																<input maxlength=30 size=22 name=pSWord value="<%=pSWord%>">
																<a href="javascript:searchList()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
															</td>
														</tr>
</form>
													</table>
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


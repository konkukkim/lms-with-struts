<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.curristudent.dto.StudentDTO"%>
<%@include file="../common/header.jsp" %>
<%
	ListDTO listObj = (ListDTO)model.get("completeCurriList");
	int iCurPage = listObj.getCurrentPage();
%>
<%= listObj.getPageScript("f", "curPage", "goPage") %>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- main 시작 -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/Student.cmd?cmd=completeCurriList&MENUNO=3" >
<input type="hidden" name="curPage" value="<%=iCurPage%>">
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="">개설과정</td>
												<td class="s_tablien"></td>
												<td width="140">강의속성</td>
												<td class="s_tablien"></td>
												<td width="150">수강기간</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	int No = listObj.getStartPageNum();
	RowSet list= listObj.getItemList();
	String CompleteLink = "";
	String curriType = "";
	String serviceDate = "";
	if(listObj.getItemCount() > 0){
		while(list.next()){
			curriType = StringUtil.nvl(list.getString("curri_property2"),"R");

			if(curriType.equals("R")){
				serviceDate = DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("service_start")))+" ~ "+DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("service_end")));
				CompleteLink = "<a href=\""+CONTEXTPATH+"/Student.cmd?cmd=completeStudentList&MENUNO=3&pCurriCode="+StringUtil.nvl(list.getString("curri_code"))+"&pCurriYear="+list.getInt("curri_year")+"&pCurriTerm="+list.getInt("curri_term")+"\">"+StringUtil.nvl(list.getString("curri_name"))+"</a>";
				if(list.getInt("non_complete_cnt") > 0 )
					CompleteLink = "<a href=\"javascript:alert('성적처리 미완료 과목이 있어서 수료 처리를 진행할 수 없습니다.\\n\\n수료진행을 위해서는 교수자께서 성적처리완료로 확정해 주셔야 합니다.');\">"+StringUtil.nvl(list.getString("curri_name"))+"</a>";
			}else{
				serviceDate = "결제일로부터 "+StringUtil.nvl(list.getString("service_start"))+" 일간";
				CompleteLink = "<a href=\""+CONTEXTPATH+"/Student.cmd?cmd=completeStudentList2&MENUNO=3&pCurriCode="+StringUtil.nvl(list.getString("curri_code"))+"&pCurriYear="+list.getInt("curri_year")+"&pCurriTerm="+list.getInt("curri_term")+"\">"+StringUtil.nvl(list.getString("curri_name"))+"</a>";
			}

%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04"><%=CompleteLink%></td>
												<td></td>
												<td class="s_tab04_cen"><%=list.getString("so_name")%></td>
												<td></td>
												<td class="s_tab04_cen"><%=serviceDate%></td>
											</tr>
<%			if(No > 1) { %>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
			}
			No = No-1;
		}

	}
	else {
%>
											<tr>
												<td class="s_tab04_cen" colspan="11">수료처리 대상이 되는 과정이 없습니다.</td>
											</tr>
<%
	}
	list.close();
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td colspan="11" height="10" align="right"></td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<table>
														<tr>
															<td align=middle height=30>
																<%= listObj.getPagging(SYSTEMCODE,"goPage") %>
															</td>
														</tr>
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

<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.framework.persist.ListDTO" %>
<%@ page import ="javax.sql.RowSet" %>
<%@ page import ="com.edutrack.faq.dto.FaqContentsDTO"%>
<%@include file="../common/header.jsp" %>
<%
	Map data = (Map)request.getAttribute("MODEL");
	String pCateId  = (String)model.get("pCateId");
	int curPage = Integer.parseInt((String)model.get("curPage"));
	String pMode = (String)model.get("pMode");
%>
<Script Language="javascript">
	function goAdd(mode,cate_id, seq_no)
	{
		if(mode == "W") {
			document.location = "<%=CONTEXTPATH%>/Faq.cmd?cmd=faqContentsWrite&pMode=<%=pMode%>&pRegMode="+mode+"&pCateId="+cate_id+"&curPage=1";
		}else{
			document.location = "<%=CONTEXTPATH%>/Faq.cmd?cmd=faqContentsWrite&pMode=<%=pMode%>&pRegMode="+mode+"&pCateId="+cate_id+"&pSeqNo="+seq_no+"&curPage=1";
		}
	}

	function goCategoryList(){
		document.location = "<%=CONTEXTPATH%>/Faq.cmd?cmd=faqCategoryList&pMode=<%=pMode%>";
	}

</Script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<%
	ListDTO listObj = (ListDTO)data.get("faqcontentsList");
%>
<%= listObj.getPageScript("f", "curPage", "goPage") %>
<form name="f" method="post" action="<%=CONTEXTPATH%>/Faq.cmd?cmd=faqContentsPagingList&pMode=<%=pMode%>" >
<input type="hidden" name="pCateId" value="<%=pCateId%>">
<input type="hidden" name="curPage" value="1">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("카테고리목록", "goCategoryList()", "");</script>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="">제목</td>
												<td class="s_tablien"></td>
												<td width="110">등록일</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	int No = listObj.getStartPageNum();
	int i =0;
	RowSet list= listObj.getItemList();

	if (listObj.getItemCount() > 0) {

    	while (list.next()) {
			i++;
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04">
													<a href="javascript:goAdd('E','<%=pCateId%>','<%=list.getInt("seq_no")%>')" class="list" title="<%=StringUtil.nvl(list.getString("subject"))%>"><%=StringUtil.setMaxLength(StringUtil.nvl(list.getString("subject")),50)%></a>
												</td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1, StringUtil.nvl(list.getString("reg_date")))%></td>
											</tr>
<%			if(No > 1) { %>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
			}
			No = No-1;
		}
	} else {
%>
											<tr>
												<td class="s_tab04_cen" colspan="11">등록된 게시물이 없습니다.</td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %><script language=javascript>Button3("글쓰기", "goAdd('W','<%=pCateId%>','')", "");</script><%	}	%>
												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<table valign=top height="25">
														<tr>
															<td>
<%
	if(listObj.getTotalItemCount() > 0) {
%>
																<%=listObj.getPagging(SYSTEMCODE, "goPage")%>
<%
	}
%>
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
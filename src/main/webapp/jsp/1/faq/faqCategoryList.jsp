<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.faq.dto.FaqCategoryDTO"%>
<%@include file="../common/header.jsp" %>
<%
	String pMode = (String)model.get("pMode");
%>
<Script Language="javascript">
	function goAdd(mode,cateid)
	{
		if(mode == "W") {
			document.location = "<%=CONTEXTPATH%>/Faq.cmd?cmd=faqCategoryWrite&pRegMode="+mode+"&pMode=<%=pMode%>";
		}else{
			document.location = "<%=CONTEXTPATH%>/Faq.cmd?cmd=faqCategoryWrite&pRegMode="+mode+"&pCateId="+cateid+"&pMode=<%=pMode%>";
		}
	}

	function goDel(cate_id) {
		var stat = confirm("카테고리를 삭제하시겠습니까?\n\n주의:관련 게시물도 함께 삭제됩니다.");
		if (stat == true) {
			document.location = "<%=CONTEXTPATH%>/Faq.cmd?cmd=faqCategoryDelete&pCateId="+cate_id+"&pMode=<%=pMode%>";
		}
	}

	function goContentList(cate_id){
		document.location = "<%=CONTEXTPATH%>/Faq.cmd?cmd=faqContentsPagingList&pCateId="+cate_id+"&pMode=<%=pMode%>";
	}
</Script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
<%
			if (pMode.equals("MyPage")) {
%>
<script language=javascript>Button5("카테고리생성", "goAdd('W','')", "");</script>
<%
			}
%>
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
<%
			if (pMode.equals("MyPage")) {
%>
												<td width="">카테고리명</td>
<%
			} else if (pMode.equals("Help")) {
%>
												<td width="">카테고리명</td>
<%
			}
%>
												<td class="s_tablien"></td>
												<td width="110">등록글</td>
												<td class="s_tablien"></td>
												<td width="110">등록일</td>
<%
			if (pMode.equals("MyPage")) {
%>
												<td class="s_tablien"></td>
												<td width="110">수정/삭제</td>
<%
			}
%>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	int num = 0;
	ArrayList list = (ArrayList)model.get("faqcategoryList");
	int j = 0;
	FaqCategoryDTO category = null;

	if(list.size() > 0){

    	for(int i=0; i < list.size(); i++){
    		j++;
    		category = (FaqCategoryDTO)list.get(i);
    		num = num + 1;

    		if(j > 1) {
%>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%			} %>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=num%></td>
												<td></td>
<%
			if (pMode.equals("MyPage")) {
%>
												<td class="s_tab04">
													<a href="javascript:goContentList('<%=category.getCateId()%>')" class="list"><%=category.getCateName()%></a>
												</td>
<%
			} else if (pMode.equals("Help")) {
%>
												<td class="s_tab04">
													<a href="javascript:goContentList('<%=category.getCateId()%>')" class="list"><%=category.getCateName()%></a>
												</td>
<%
			}
%>
												<td></td>
												<td class="s_tab04_cen"><%=category.getConCnt()%></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1, category.getRegDate())%></td>
<%
			if (pMode.equals("MyPage")) {
%>
												<td></td>
												<td class="s_tab04_cen">
													<a href="javascript:goAdd('E','<%=category.getCateId()%>')"><b>[수정]</b></a> /
													<a href="javascript:goDel('<%=category.getCateId()%>')"><b>[삭제]</b></a>
												</td>
<%
			}
%>
											</tr>
<%
		}
	} else {
%>
											<tr>
												<td class="s_tab04_cen" colspan="11">등록된 카테고리가 없습니다.</td>
											</tr>
<%
	}
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
					
<%@include file="../common/footer.jsp" %>
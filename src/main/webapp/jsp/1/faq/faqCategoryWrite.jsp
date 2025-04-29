<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.faq.dto.FaqCategoryDTO"%>
<%@include file="../common/header.jsp" %>
<%
	Map data = (Map)request.getAttribute("MODEL");
	String pRegMode = (String)model.get("pRegMode");
	String pCateId  = (String)model.get("pCateId");
	String pMode = (String)model.get("pMode");

	String cate_name  = "";
	String comment = "";

	//수정모드일 경우
	if(pRegMode.equals("E")) {
		FaqCategoryDTO categoryView = (FaqCategoryDTO)data.get("faqcategoryView");
		cate_name = categoryView.getCateName();
		comment = categoryView.getXcomment();
	}
%>

<Script Language="javascript">
	function submitCheck()
	{
		var f = document.Input;

	 	if(!validate(f))
        	return false;
//        else
//            return true;
		f.submit();
	}

	function goList() {
			document.location = "<%=CONTEXTPATH%>/Faq.cmd?cmd=faqCategoryList&pMode=<%=pMode%>";
	}
</Script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start   onSubmit="return submitCheck()"  -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/Faq.cmd?cmd=faqCategoryRegist&pMode=<%=pMode%>">
<input type='hidden' name ='pRegMode' value='<%=pRegMode%>'>
<input type='hidden' name ='pCateId' value='<%=pCateId%>'>

											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">카테고리명</td>
												<td class="s_tab_view02" width="200">
													<input type="text" name="pCateName" size="60" value="<%=cate_name%>" size="60" maxlength="100" dispName="카테고리명" notNull>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">내용</td>
												<td class="s_tab_view03" colspan="3">
													<textarea name="pComment" cols="80" rows="5" wrap="VIRTUAL" dispName="내용" notNull><%=comment%></textarea>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
<%if (pRegMode.equals("W")) {%>&nbsp;<script language=javascript>Button3("등록", "submitCheck()", "");</script>
<%} else {%>&nbsp;<script language=javascript>Button3("수정", "submitCheck()", "");</script>
<%}%>
												</td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>
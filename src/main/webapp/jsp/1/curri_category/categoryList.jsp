<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.curritop.dto.CurriCategoryDTO"%>
<%@include file="../common/header.jsp" %>
<%
	String pStep = (String)model.get("pStep");

	int		colspan	=	0;
	String	bgimgname	=	"mtit5_01_01.gif";
	String	imgname1	=	"tle_manage11.gif";
	String	imgname2	=	"tle_manage21.gif";
	String	imgname3	=	"tle_manage31.gif";

	if (pStep.equals("1")) {
		colspan = 11;
		imgname1	=	"tle_manage12.gif";
		bgimgname	=	"mtit5_01_01.gif";
	} else if (pStep.equals("2")) {
		colspan = 13;
		imgname2	=	"tle_manage22.gif";
		bgimgname	=	"mtit5_01_02.gif";
	} else {
		colspan = 15;
		imgname3	=	"tle_manage32.gif";
		bgimgname	=	"mtit5_01_03.gif";
	}

	ListDTO listObj = (ListDTO)model.get("curriCategoryList");
	int iCurPage = listObj.getCurrentPage();
%>
<%= listObj.getPageScript("f", "curPage", "goPage") %>

<Script Language="javascript">
	function goAdd() {
<%
	if (pStep.equals("1")) {
%>
		var loc = "CurriCategory.cmd?cmd=curriCategoryWrite&pStep=<%=pStep%>";
		document.location.href = loc;
<%
	} else if (pStep.equals("2")) {
%>
		if (<%=(String)model.get("cnt")%> == 0) {
			alert('대분류를 먼저 등록해주십시오.');
		} else {
			var loc = "CurriCategory.cmd?cmd=curriCategoryWrite&pStep=<%=pStep%>";
			document.location.href = loc;
		}
<%
	} else if (pStep.equals("3")) {
%>
		if (<%=(String)model.get("cnt")%> == 0) {
			alert('중분류를 먼저 등록해주십시오.');
		} else {
			var loc = "CurriCategory.cmd?cmd=curriCategoryWrite&pStep=<%=pStep%>";
			document.location.href = loc;
		}
<%
	}
%>
	}
	
function goMove(cateCode, val){
	
	if( u_right!="true" && d_right!="true"){
		alert("수정/삭제 권한이 없습니다. \n\n관리자에게 문의 하세요");
	    return;
	}
	
	
	loc = "<%=CONTEXTPATH%>/CurriCategory.cmd?cmd=curriCategoryEdit&pMode=<%=PMODE %>&pCateCode="+cateCode+"&pStep=<%=pStep%>";
	
	document.location.href = loc;

}

</Script>
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center" border="0">
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/CurriCategory.cmd?cmd=curriCategoryList&pStep=<%=pStep%>" >
<input type="hidden" name="curPage" value="<%=iCurPage%>">
											<!-- 카테고리 모음 -->
											<tr valign="middle" height="50">
												<td colspan="<%=colspan%>" valign="middle">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr valign="top">
		<td align="left"><a href="<%=CONTEXTPATH%>/CurriCategory.cmd?cmd=curriCategoryList&pMode=<%=PMODE%>&pStep=1"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/<%=imgname1%>" border="0"></a></td>
		<td align="center"><a href="<%=CONTEXTPATH%>/CurriCategory.cmd?cmd=curriCategoryList&pMode=<%=PMODE%>&pStep=2"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/<%=imgname2%>" border="0"></a></td>
		<td align="right"><a href="<%=CONTEXTPATH%>/CurriCategory.cmd?cmd=curriCategoryList&pMode=<%=PMODE%>&pStep=3"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/<%=imgname3%>" border="0"></a></td>
	</tr>
</table>
												</td>
											</tr>
											<!-- //  카테고리 모음 -->
											<tr>
												<td colspan="<%=colspan%>" class="s_btn01" align="right">
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %><script language=javascript>Button5("과정분류추가", "goAdd()", "");</script><%	}	%>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="<%=colspan%>"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="80">분류코드</td>
												<td class="s_tablien"></td>
												<td width="">대분류명</td>
												<td class="s_tablien"></td>
<%
	if (pStep.equals("2") || pStep.equals("3")) {
%>
												<td width="">중분류명</td>
												<td class="s_tablien"></td>
<%
	}
	if (pStep.equals("3")) {
%>
												<td width="">소분류명</td>
												<td class="s_tablien"></td>
<%
	}
%>
												<td width="50">상태</td>
												<td class="s_tablien"></td>
												<!--td width="70">등록일</td>
												<td class="s_tablien"></td-->
												<td width="85">수정/삭제</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="<%=colspan%>"></td>
											</tr>
<%
	int No = listObj.getStartPageNum();
	RowSet list= listObj.getItemList();
	int i = 0;

	if (listObj.getItemCount() > 0) {
		while (list.next()) {
		i++;
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=i%></td>
												<td></td>
<%
	if (pStep.equals("1")) {
%>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("pare_code1"))%></td>
												<td></td>
												<td class="s_tab04"><%=StringUtil.nvl(list.getString("cate_name"))%></td>
												<td></td>
<%
	}
	else if (pStep.equals("2")) {
%>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("pare_code2"))%></td>
												<td></td>
												<td class="s_tab04"><%=StringUtil.nvl(list.getString("step1_name"))%></td>
												<td></td>
												<td class="s_tab04"><%=StringUtil.nvl(list.getString("cate_name"))%></td>
												<td></td>
<%
	}
	else if (pStep.equals("3")) {
%>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("cate_code"))%></td>
												<td></td>
												<td class="s_tab04"><%=StringUtil.nvl(list.getString("step1_name"))%></td>
												<td></td>
												<td class="s_tab04"><%=StringUtil.nvl(list.getString("step2_name"))%></td>
												<td></td>
												<td class="s_tab04"><%=StringUtil.nvl(list.getString("cate_name"))%></td>
												<td></td>
<%
	}
%>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("use_name"))%></td>
												<td></td>
												<!--td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("reg_date"))%></td>
												<td></td-->
												<td class="s_tab04_cen">
<%
			String imsiParam = "";
			if (pStep.equals("1")) {
				imsiParam = StringUtil.nvl(list.getString("pare_code1")) ;
			}
			else if (pStep.equals("2")) {
				imsiParam = StringUtil.nvl(list.getString("pare_code2")) ;
			}
			else if (pStep.equals("3")) {
				imsiParam = StringUtil.nvl(list.getString("cate_code")) ;
			}
%> <b>[<a href="javascript:goMove('<%=imsiParam %>','UD')">수정/삭제</a>]</b>
												</td>
											</tr>
<%			if(No > 1) {%>
											<tr class="s_tab03">
												<td colspan="<%=colspan%>"></td>
											</tr>
<%
			}
			No = No-1;
		}	// while
	} else {
%>
											<tr>
												<td class="s_tab04_cen" colspan="<%=colspan%>">등록된 분류가 없습니다.</td>
											</tr>
<%
	}
	list.close();
%>
											<tr class="s_tab05">
												<td colspan="<%=colspan%>"></td>
											</tr>
											<tr>
												<td colspan="<%=colspan%>" height="10"></td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="<%=colspan%>" align=center>
													<table valign=top height="25">
														<tr>
															<td><%= listObj.getPagging(SYSTEMCODE,"goPage") %></td>
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
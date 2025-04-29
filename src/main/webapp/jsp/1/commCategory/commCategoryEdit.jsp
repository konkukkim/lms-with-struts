<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.community.dto.CommCategoryDTO"%>
<%@include file="../common/community_header.jsp" %>
<% String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE; 
	String	pMode	=	StringUtil.nvl(model.get("pMode"));
%>
<script language="javascript">
	function submitCheck() {
		var f = document.Input;
		if(!validate(f)) return false;
//		else return true;
		f.submit();
	}

	function delFile(fileNum){
		var st = confirm("이미지 파일을 삭제 하시겠습니까?");
		if (st == true) {
	       	document.location.href="/CommCategory.cmd?cmd=commCategoryFileDelete&pMode=<%=pMode%>&pCateCode=<%=model.get("cateCode")%>&pFileNo="+fileNum+"&MENUNO=0";
		}
    }

	function delCommCategory(){
		var st = confirm("동아리 카테고리를 삭제하시겠습니까?\n관련컨텐츠도 같이 삭제됩니다.");
		if (st == true) {
	       	document.location.href="/CommCategory.cmd?cmd=commCategoryDelete&pMode=<%=pMode%>&pCateCode=<%=model.get("cateCode")%>&MENUNO=0";
		}
    }

	function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc = "/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }

    function goList() {
    	document.location.href = "<%=CONTEXTPATH%>/CommCategory.cmd?cmd=commCategoryList&pMode=<%=pMode%>&MENUNO=0";
    }
</script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b>동아리분류관리</b></font></td>
									<!-- // community title -->
									<!-- history -->
									<td class="com_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									</td>
								</tr>
								<tr valign="top">
									<td colspan="2" class="com_content_top">
									<!-- 내용 -->
									<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start   onSubmit="return submitCheck()"  -->
<form name=Input method="post" action="/CommCategory.cmd?cmd=commCategoryRegist&pRegMode=Edit&pMode=<%=pMode%>" enctype="multipart/form-data">

											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">카테고리 코드</td>
												<td class="s_tab_view02"><input name="pCateCode" size="12" maxlength="5" dispName="카테고리코드" readonly value="<%=model.get("cateCode")%>" notNull> &nbsp;* 카테고리코드는 수정할수없습니다.</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">카테고리명</td>
												<td class="s_tab_view02"><input name="pCateName" size="40" maxlength="14" dispName="카테고리명" value="<%=model.get("cateName")%>" notNull></td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("수정", "submitCheck()", "");</script>
&nbsp;<script language=javascript>Button3("삭제", "delCommCategory()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
												</td>
											</tr>
											<tr>
												<td colspan="2" height="30" align="right">&nbsp;* 동아리 카테고리 코드는 되도록이면 1,2...숫자로 해주시기 바랍니다.</td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->

<%@include file="../common/community_footer.jsp" %>
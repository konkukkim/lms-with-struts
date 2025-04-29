<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.faq.dto.FaqContentsDTO, com.edutrack.common.dto.EditorParam"%>
<%@include file="../common/header.jsp" %>
<%
	Map data = (Map)request.getAttribute("MODEL");
	String pRegMode = (String)model.get("pRegMode");
	String pCateId  = (String)model.get("pCateId");
	String pSeqNo   = (String)model.get("pSeqNo");
	String curPage	= (String)model.get("curPage");
	String pMode	= (String)model.get("pMode");

	String subject  = "";
	String contents = "";
	String rfilename = "";
	String sfilename = "";
	String filepath = "";
	String filesize = "";

	//수정모드일 경우
	if(pRegMode.equals("E")) {
		FaqContentsDTO contentsView = (FaqContentsDTO)data.get("faqcontentsShow");
		subject = contentsView.getSubject();
		contents = contentsView.getContents();
		rfilename = contentsView.getRfileName();
		sfilename = contentsView.getSfileName();
		filepath = contentsView.getFilePath();
		filesize = contentsView.getFileSize();
	}
%>

<script language="javascript">
	function submitCheck()
	{
		var f = document.Input;

		if(window.VBN_prepareSubmit != null){
			if(!VBN_prepareSubmit())
				return false;
		}

		if(isEmpty(f.pSubject.value))
		{
			alert("제목을 입력하세요");
			f.pSubject.focus();
			return false;
		}

		if(isEmpty(f.pContents.value))
		{
			alert("내용을 입력하세요");
			//f.pContents.focus();
			return false;
		}

		f.pFILE_NEW1_ori.value = f.pFILE_NEW1.value;
//		return true;
		f.submit();

	}

	function goList(cate_id) {
			document.location = "<%=CONTEXTPATH%>/Faq.cmd?cmd=faqContentsPagingList&pMode=<%=pMode%>&pCateId="+cate_id;
	}

	function goDel() {
		var stat = confirm("게시물을 삭제하시겠습니까?");
		if (stat == true) {
			var	f = document.Input;
			f.action="<%=CONTEXTPATH%>/Faq.cmd?cmd=faqContentsDelete&pMode=<%=pMode%>";
            f.encoding = "application/x-www-form-urlencoded";
			f.submit();
		}
	}

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start    onSubmit="return submitCheck()"  -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/Faq.cmd?cmd=faqContentsRegist&pMode=<%=pMode%>" enctype="multipart/form-data">
<input type='hidden' name="pRegMode"       value="<%=pRegMode  %>">
<input type='hidden' name="pCateId"        value="<%=pCateId   %>">
<input type='hidden' name="pSeqNo"         value="<%=pSeqNo    %>">
<input type='hidden' name="curPage"        value="<%=curPage   %>">
<input type='hidden' name="pRFILENAME"     value="<%=rfilename %>">
<input type='hidden' name="pSFILENAME"     value="<%=sfilename %>">
<input type='hidden' name="pFILEPATH"      value="<%=filepath  %>">
<input type='hidden' name="pFILESIZE"      value="<%=filesize  %>">
<input type="hidden" name="pFILE_NEW1_ori" value="">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">제목</td>
												<td class="s_tab_view02"><input type="text" name="pSubject" size="70" value="<%=subject%>"  size="60" maxlength="100" dispName="제목" notNull></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view02">
													<input type="file" name="pFILE_NEW1" size="70" value="">
<%
	if(!rfilename.equals("") && !filepath.equals("")) {
%>
                                   					<br>&nbsp;기존파일 : <a href="javascript:fileDownload('<%=rfilename%>','<%=sfilename%>','<%=filepath%>','<%=filesize%>');">	<%=rfilename%></a>&nbsp;
                                   					[기존파일삭제<input type='checkbox' name='pFile_DEL' class=no>]
<%
	}
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">내용</td>
												<td class="s_tab_view03">
													<textarea name="pContents" cols="70" rows="5" wrap="VIRTUAL" dispName="내용" notNull><%=contents%></textarea>
<%
    EditorParam editerParam = new EditorParam();
    editerParam.setShowFlag("true");
    editerParam.setWidth(550);
    editerParam.setHeight(300);
    editerParam.setReadWidth("580");
    editerParam.setToolBarHidden("attachFile");

    out.print(CommonUtil.getEditorScript(editerParam));
%>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("목록", "goList('<%=pCateId%>')", "");</script>
<% if(!pRegMode.equals("E") && CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button3("등록", "submitCheck()", "");</script><%	}	%>
<% if( pRegMode.equals("E") && CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button3("수정", "submitCheck()", "");</script><%	}	%>
<% if( pRegMode.equals("E") && CommonUtil.getAuthorCheck(request,  "D"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button3("삭제", "goDel()", "");</script><%	}	%>

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
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->
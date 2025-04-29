<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.message.dto.MessageDTO"%>
<%@include file="../common/header.jsp" %>
<%
	Map data = (Map)request.getAttribute("MODEL");
	MessageDTO messageShow = (MessageDTO)data.get("messageShow");
	String curPage  = (String)model.get("curPage");
	String pSeqNo  = (String)model.get("pSeqNo");
	String pBackPage  = (String)model.get("pBackPage");
	String pSendId  = (String)model.get("pSendId");

	//첨부파일
	String rfilename = messageShow.getRfileName();
	String sfilename = messageShow.getSfileName();
	String filepath = messageShow.getFilePath();
	String filesize = messageShow.getFileSize();

%>

<Script Language="javascript">
	function goReplayAdd()
	{
		document.location = "<%=CONTEXTPATH%>/Message.cmd?cmd=messageWrite&pRegMode=R&pSEQ_NO=<%=pSeqNo%>&pSEND_ID=<%=pSendId%>&pcurPage=1";
	}

	function goList() {
			document.location = "<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessagePagingList";
	}

   function goDel(){

		var stat = confirm("받은쪽지를 삭제하시겠습니까?");
		if (stat == true) {
			var form = document.f;
			form.action="<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessageDelete";
			form.submit();
		}
	}

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }

</Script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name="f" method="post">
<input type="hidden" name="pCHK" value="<%=pSendId%>|<%=pSeqNo%>">
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">제목</td>
												<td class="s_tab_view02" width="200"><%=messageShow.getSubject()%></td>
												<td class="s_tab_view01" width="80">받은날짜</td>
												<td class="s_tab_view02" width=""><%=DateTimeUtil.getDateType(0, messageShow.getSendDate())%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">보낸사람</td>
												<td class="s_tab_view02"><%=messageShow.getSenderName()%>(<%=messageShow.getSenderId()%>)</td>
												<td class="s_tab_view01">받는사람</td>
												<td class="s_tab_view02"><%=messageShow.getReceiverName()%>(<%=messageShow.getReceiverId()%>)</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view02" colspan="3">
<%	if (!rfilename.equals("") && !filepath.equals("")) {	%>
													<a href="javascript:fileDownload('<%=rfilename%>','<%=sfilename%>','<%=filepath%>','<%=filesize%>');"><%=rfilename%></a>
<%	}	%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">내용</td>
												<td class="s_tab_view03" colspan="3">
													<%=StringUtil.ReplaceAll(messageShow.getContents(),"&quot;","\"")%>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
&nbsp;<script language=javascript>Button3("쪽지쓰기", "goReplayAdd()", "");</script>
&nbsp;<script language=javascript>Button3("삭제", "goDel()", "");</script>
</td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
</form>
<!-- form -->
							</table>
						


<%@include file="../common/footer.jsp" %>

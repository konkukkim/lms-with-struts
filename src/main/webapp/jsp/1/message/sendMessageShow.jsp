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

	String receive_date = "읽지않음";
	if(!messageShow.getReceiveDate().equals("")){
		receive_date = DateTimeUtil.getDateType(0, messageShow.getReceiveDate());
	}

%>

<Script Language="javascript">
	function goList() {
			document.location = "<%=CONTEXTPATH%>/Message.cmd?cmd=sendMessagePagingList";
	}

   function goDel(){
		var stat = confirm("보낸쪽지를 삭제하시겠습니까?");
		if (stat == true) {
			var form = document.f;
			form.action="<%=CONTEXTPATH%>/Message.cmd?cmd=sendMessageDelete";
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
												<td class="s_tab_view01" width="80">보낸날짜</td>
												<td class="s_tab_view02" width=""><%=DateTimeUtil.getDateType(0, messageShow.getSendDate())%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">받는사람</td>
												<td class="s_tab_view02"><%=messageShow.getReceiverName()%>(<%=messageShow.getReceiverId()%>)</td>
												<td class="s_tab_view01">확인시간</td>
												<td class="s_tab_view02"><%=receive_date%></td>
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
<script language=javascript>Button3("목록", "goList()", "");</script>&nbsp;<script language=javascript>Button3("삭제", "goDel()", "");</script>
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

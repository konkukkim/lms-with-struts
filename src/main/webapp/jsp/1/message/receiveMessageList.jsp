<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.framework.persist.ListDTO,javax.sql.RowSet,com.edutrack.message.dto.MessageDTO"%>
<%@include file="../common/header.jsp" %>
<script language="javascript">
	function goAdd(mode)
	{
	    var f = document.f;
        var curPage = f.curPage.value;
		document.location = "<%=CONTEXTPATH%>/Message.cmd?cmd=messageWrite&pRegMode="+mode+"&curPage="+curPage;
	}

	function goSendMessage()
	{
		document.location = "<%=CONTEXTPATH%>/Message.cmd?cmd=sendMessagePagingList";
	}

	function goView(seq_no, send_id)
	{
        var f = document.f;
        var curPage = f.curPage.value;
		document.location = "<%=CONTEXTPATH%>/Message.cmd?cmd=messageShow&pSEQ_NO="+seq_no+"&pSEND_ID="+send_id+"&curPage="+curPage+"&pBackPage=R";
	}

   function goDel(){
		var form = document.f;
		var checkVal = getChkBoxByValue(form, "pCHK", "");

		if(checkVal == "") {
			alert("삭제할 쪽지를 선택하세요.")
			return;
		}

		form.action="<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessageDelete";
		form.submit();
	}

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }

</script>

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
<script language=javascript>Button5("보낸쪽지함", "goSendMessage()", "");</script>
															</td>
														</tr>
													</table>
												</td>
											</tr>
<%
	ListDTO listObj = (ListDTO)model.get("receiveMessageList");
%>
<%= listObj.getPageScript("f", "curPage", "goPage") %>
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessagePagingList" >
<input type="hidden" name="curPage" value="1">
<input type="hidden" name="checkYn" value="N">
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70"><a href="javascript:setAllToken(f.pCHK)">선택</a></td>
												<td class="s_tablien"></td>
												<td width="">제목</td>
												<td class="s_tablien"></td>
												<td width="60">파일</td>
												<td class="s_tablien"></td>
												<td width="80">보낸사람</td>
												<td class="s_tablien"></td>
												<td width="110">보낸날짜</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	String rfilename = "";
	String sfilename = "";
	String filepath =  "";
	String filesize =  "";
	String subject = "";

    int No = listObj.getStartPageNum();
    int i =0;
	RowSet list= listObj.getItemList();

	if(listObj.getItemCount() > 0){

		while(list.next()){
			i++;
			rfilename = StringUtil.nvl(list.getString("rfile_name"));
			sfilename = StringUtil.nvl(list.getString("sfile_name"));
			filepath = StringUtil.nvl(list.getString("file_path"));
			filesize = StringUtil.nvl(list.getString("file_size"));

			if(!StringUtil.nvl(list.getString("receive_date")).equals("")){
				subject = StringUtil.nvl(list.getString("subject"));
			}else{
				subject = "<b>"+StringUtil.nvl(list.getString("subject"))+"</b>";
			}
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><input type="checkbox" name="pCHK" value="<%=StringUtil.nvl(list.getString("sender_id"))%>|<%=list.getInt("seq_no")%>" class="no"></td>
												<td></td>
												<td class="s_tab04"><a href="javascript:goView('<%=list.getInt("seq_no")%>','<%=StringUtil.nvl(list.getString("sender_id"))%>')"  class="list" title="<%=subject%>"><%=StringUtil.setMaxLength(subject,40)%></a></td>
												<td></td>
												<td class="s_tab04_cen">
<%
			if(!rfilename.equals("") && !filepath.equals("")) {
%>
													<a href="javascript:fileDownload('<%=rfilename%>','<%=sfilename%>','<%=filepath%>','<%=filesize%>');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/ico_file.gif" width="13" height="13" border="0"></a>
<%
			}
%>
												</td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("sender_name"))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1, StringUtil.nvl(list.getString("send_date")))%></td>
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
												<td class="s_tab04_cen" colspan="11">받은 쪽지가 없습니다.</td>
											</tr>
<%
	}
 	list.close();
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<script language=javascript>Button3("쪽지쓰기", "goAdd()", "");</script>&nbsp;<script language=javascript>Button3("삭제", "goDel()", "");</script>
												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<table valign=top height="25">
														<tr>
															<td>
<%
	if(listObj.getTotalItemCount() > 0){
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














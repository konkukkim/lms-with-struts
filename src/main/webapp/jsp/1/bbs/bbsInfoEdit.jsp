<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/header.jsp" %>
<%
		RowSet 	rsBbsInfo= (RowSet)model.get("rs");
		String	pBbsId	=	(String)model.get("pBbsId");
%>
<Script Language="javascript">
	function submitCheck()
	{
		var f = document.Input;

	 	if(!validate(f))
        	return false;

		f.submit();

	}
	function goDel(){
		document.location = "<%=CONTEXTPATH%>/BbsInfo.cmd?cmd=bbsInfoDelete&pMode=<%=PMODE%>&pBbsId=<%=pBbsId%>";
	}
////function delFile(){
////   	document.location.href="<%=CONTEXTPATH%>/BbsInfo.cmd?cmd=bbsInfoFileDelete&pBbsId=<%=pBbsId%>";
////}
    function goList(){
		document.location.href="<%=CONTEXTPATH%>/BbsInfo.cmd?cmd=bbsInfoPagingList&pMode=<%=PMODE%>";
	}

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }
</Script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<%	while (rsBbsInfo.next()) {	%>
<!-- form start  onSubmit="return submitCheck()" -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/BbsInfo.cmd?cmd=bbsInfoRegist&pRegMode=Edit&pBbsId=<%=pBbsId%>" enctype="multipart/form-data">
<input type=hidden name='pUseYn' value='Y'>
<input type=hidden name='pOldFileName' value='<%=StringUtil.nvl(rsBbsInfo.getString("title_img"))%>'>
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">게시판명</td>
												<td class="s_tab_view02" colspan="3"><input type="text" name="pBbsName" size="60" maxlength=100 notNull  dispName="게시판명" dataType="text" lenCheck="100" value="<%=StringUtil.nvl(rsBbsInfo.getString("bbs_name"))%>"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">타입</td>
												<td class="s_tab_view02" colspan="3">
													<select name="pBbsType">
			                                            <option value='notice' <% if(StringUtil.nvl(rsBbsInfo.getString("bbs_type")).equals("notice")) out.print(" selected");%>>공지사항</option>
			                                            <option value='bbs' <% if(StringUtil.nvl(rsBbsInfo.getString("bbs_type")).equals("bbs")) out.print(" selected");%>>일반게시판</option>
			                                        </select>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">라인수</td>
												<td class="s_tab_view02" width="220"><input type="text" name="pDispLine" size="5" dispName="라인수" dataType="number" value="<%=rsBbsInfo.getInt("disp_line")%>">줄</td>
												<td class="s_tab_view01" width="105">페이지수</td>
												<td class="s_tab_view02" width="220"><input type="text" name="pDispPage" size="5" dispName="페이지수" dataType="number" value="<%=rsBbsInfo.getInt("disp_page")%>">페이지</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">파일첨부</td>
												<td class="s_tab_view02" colspan="3">
													<input type=radio name="pFileUseYn" value="Y" style="border:0" <% if(StringUtil.nvl(rsBbsInfo.getString("file_use_yn")).equals("Y")) out.print(" checked");%>> 사용 &nbsp;&nbsp;
                                        			<input type=radio name="pFileUseYn" value="N" style="border:0" <% if(StringUtil.nvl(rsBbsInfo.getString("file_use_yn")).equals("N")) out.print(" checked");%>> 미사용
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">파일첨부갯수</td>
												<td class="s_tab_view02">
													<select name="pFileCount">
			                                            <option value='1'<% if(rsBbsInfo.getInt("file_count") == 1) out.print(" selected");%>>1</option>
			                                            <option value='2'<% if(rsBbsInfo.getInt("file_count") == 2) out.print(" selected");%>>2</option>
			                                            <option value='3'<% if(rsBbsInfo.getInt("file_count") == 3) out.print(" selected");%>>3</option>
			                                        </select>
												</td>
												<td class="s_tab_view01" width="105">파일최대크기</td>
												<td class="s_tab_view02"><input type="text" name="pFileLimit" size="5" dispName="첨부파일최대크기" dataType="number" value="<%=StringUtil.nvl(rsBbsInfo.getString("file_limit"))%>">Mbyte</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">웹에디터</td>
												<td class="s_tab_view02" colspan="3">
													<input type="radio" name="pEditorYn" value="Y" <% if(StringUtil.nvl(rsBbsInfo.getString("editor_yn")).equals("Y")) out.print(" checked");%>>사용
                                        			<input type="radio" name="pEditorYn" value="N" <% if(StringUtil.nvl(rsBbsInfo.getString("editor_yn")).equals("N")) out.print(" checked");%>>사용안함
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">로그인체크</td>
												<td class="s_tab_view02" colspan="3">
													<input type="radio" name="pLoginChkYn" value="Y" <% if(StringUtil.nvl(rsBbsInfo.getString("login_chk_yn")).equals("Y")) out.print(" checked");%>>사용
                                        			<input type="radio" name="pLoginChkYn" value="N" <% if(StringUtil.nvl(rsBbsInfo.getString("login_chk_yn")).equals("N")) out.print(" checked");%>>사용안함
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">New 표시</td>
												<td class="s_tab_view02"><input type="text" name="pNewTime" size="3" dispName="New 표시 일수" dataType="number" value="<%=rsBbsInfo.getInt("new_time")%>"></td>
												<td class="s_tab_view01" width="105">Hot(조회수)</td>
												<td class="s_tab_view02"><input type="text" name="pHotChk" size="3" dispName="Hot 표시 조회수" dataType="number" value='<%=rsBbsInfo.getInt("hot_chk")%>'>회 이상</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">금지단어</td>
												<td class="s_tab_view02" colspan="3">
													<input type="radio" name="pBadWordUseYn" value="Y" <% if(StringUtil.nvl(rsBbsInfo.getString("bad_word_use_yn")).equals("Y")) out.print(" checked");%>>금지단어제한
													<input type="radio" name="pBadWordUseYn" value="N" <% if(StringUtil.nvl(rsBbsInfo.getString("bad_word_use_yn")).equals("N")) out.print(" checked");%>>제한하지않음.<br>
													<textarea name="pBadWord" cols="85"><%=StringUtil.nvl(rsBbsInfo.getString("bad_word"))%></textarea>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">댓글기능</td>
												<td class="s_tab_view02">
													<input type="radio" name="pCommentUseYn" value="Y" <% if(StringUtil.nvl(rsBbsInfo.getString("comment_use_yn")).equals("Y")) out.print(" checked");%>>사용
													<input type="radio" name="pCommentUseYn" value="N" <% if(StringUtil.nvl(rsBbsInfo.getString("comment_use_yn")).equals("N")) out.print(" checked");%>>미사용
												</td>
												<td class="s_tab_view01" width="105">댓글이모티콘</td>
												<td class="s_tab_view02">
													<input type="radio" name="pEmoticonUseYn" value="Y" <% if(StringUtil.nvl(rsBbsInfo.getString("emoticon_use_yn")).equals("Y")) out.print(" checked");%>>사용
													<input type="radio" name="pEmoticonUseYn" value="N" <% if(StringUtil.nvl(rsBbsInfo.getString("emoticon_use_yn")).equals("N")) out.print(" checked");%>>미사용
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">관련글보기</td>
												<td class="s_tab_view02" colspan="3">
													<input type="radio" name="pViewThreadYn" value="Y" <% if(StringUtil.nvl(rsBbsInfo.getString("view_thread_yn")).equals("Y")) out.print(" checked");%>>보임
													<input type="radio" name="pViewThreadYn" value="N" <% if(StringUtil.nvl(rsBbsInfo.getString("view_thread_yn")).equals("N")) out.print(" checked");%>>안보임 &nbsp;&nbsp;* 뷰 화면에서 관련글 목록 보기
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="105">이전글<br>다음글</td>
												<td class="s_tab_view02" colspan="3">
													<input type="radio" name="pViewPrevNextYn" value="Y" <% if(StringUtil.nvl(rsBbsInfo.getString("view_prev_next_yn")).equals("Y")) out.print(" checked");%>>보임
													<input type="radio" name="pViewPrevNextYn" value="N" <% if(StringUtil.nvl(rsBbsInfo.getString("view_prev_next_yn")).equals("N")) out.print(" checked");%>>안보임 &nbsp;&nbsp;* 뷰 화면에서 이전글, 다음글 보기
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
<%
	//if(pBbsId.equals("9") || pBbsId.equals("16")) {	
		if(CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { 
%>&nbsp;<script language=javascript>Button3("수정", "submitCheck()", "");</script><%	}	%>
<% 		if(CommonUtil.getAuthorCheck(request,  "D"))/* 권한체크 */  {
%>&nbsp;<script language=javascript>Button3("삭제", "goDel()", "");</script>
<%		}
	//}	%>
												</td>
											</tr>
</form>
<!-- form end -->
<%	}

	rsBbsInfo.close();	%>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
					
<%@include file="../common/footer.jsp" %>

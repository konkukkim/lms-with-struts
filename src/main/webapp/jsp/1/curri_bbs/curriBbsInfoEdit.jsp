<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/header.jsp" %>
<%
		String	pBbsType		=	(String)model.get("pBbsType");
		RowSet 	rsBbsInfo	= 	(RowSet)model.get("rs");
%>
<Script Language="javascript">
	function submitCheck()
	{
		var f = document.Input;

	 	if(!validate(f))
        	return false;
//      else
//         return true;
		f.submit();
	}
	function goDel(){
		document.location = "<%=CONTEXTPATH%>/CurriBbsInfo.cmd?cmd=curriBbsInfoDelete&pMode=<%=PMODE%>&pBbsType=<%=pBbsType%>";
	}
	function delFile(){
       	document.location.href="<%=CONTEXTPATH%>/CurriBbsInfo.cmd?cmd=curriBbsInfoFileDelete&pBbsType=<%=pBbsType%>";
    }
    function goList(){
		document.location.href="<%=CONTEXTPATH%>/CurriBbsInfo.cmd?cmd=curriBbsInfoPagingList&pMode=<%=PMODE%>";
	}

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }
</Script>

										<!-- 내용 -->
<%
 	while(rsBbsInfo.next()) {
%>
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start   onSubmit="return submitCheck()"  -->
<form name=Input method="post"  action="<%=CONTEXTPATH%>/CurriBbsInfo.cmd?cmd=curriBbsInfoRegist&pRegMode=Edit&pBbsType=<%=pBbsType%>" enctype="multipart/form-data">
<input type=hidden name='pUseYn' value='Y'>
<input type=hidden name='pOldFileName' value='<%=StringUtil.nvl(rsBbsInfo.getString("title_img"))%>'>

											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">게시판명</td>
												<td class="s_tab_view02" colspan="3">
													<input type=text name="pBbsName" size=60 maxlength=100 dispName="게시판명" notNull lenCheck="100" value="<%=StringUtil.nvl(rsBbsInfo.getString("bbs_name"))%>">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">타입</td>
												<td class="s_tab_view02" colspan="3">
													<select name='pBbsType'>
<%
		RowSet list = (RowSet)model.get("InfoRs");
		String	BbsTypeString = "";

		while(list.next()) {
	    	BbsTypeString += StringUtil.nvl(list.getString("bbs_type")) + " ";
		}

		if( BbsTypeString.indexOf("notice") == -1  ) {
%>
                                            			<option value='notice'>공지사항</option>
<%
		}else if(BbsTypeString.indexOf("notice") >= 0  && StringUtil.nvl(rsBbsInfo.getString("bbs_type")).equals("notice")) {
%>
                                            			<option value='notice' selected >공지사항 </option>
<%
		}

		if(BbsTypeString.indexOf("bbs") == -1 ) {
%>
                                            			<option value='bbs'>일반게시판</option>
<%
		} else if(BbsTypeString.indexOf("bbs") >= 0   && StringUtil.nvl(rsBbsInfo.getString("bbs_type")).equals("bbs")) {
%>
                                            			<option value='bbs' selected >일반게시판</option>
<%
		}

		if(BbsTypeString.indexOf("pds") == -1 ){
%>
                                           				<option value='pds'>자료실</option>
<%
		} else if(BbsTypeString.indexOf("pds") >= 0  && StringUtil.nvl(rsBbsInfo.getString("bbs_type")).equals("pds")) {
%>
                                            			<option value='pds' selected >자료실</option>
<%
		}

		if(BbsTypeString.indexOf("qna") == -1 ) {
%>
                                            			<option value='qna'>Q&A</option>
<%
		} else if(BbsTypeString.indexOf("qna") >= 0  && StringUtil.nvl(rsBbsInfo.getString("bbs_type")).equals("qna")) {
%>
                                            			<option value='qna' selected >Q&A</option>
<%
	}
%>
                                        			</select>
                                        			과정게시판의 타입 변경은 불가 합니다.
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">라인수</td>
												<td class="s_tab_view02">
													<input type=text name="pDispLine" size=3 style="text-align:right" dataType="number" dispName="리스트 라인수" value='<%=rsBbsInfo.getInt("disp_line")%>'>줄
												</td>
												<td class="s_tab_view01" width="92">페이지수</td>
												<td class="s_tab_view02">
													<input type=text name="pDispPage" size=3 style="text-align:right" dateType="number" dispName="페이지 표시수" value='<%=rsBbsInfo.getInt("disp_page")%>' >페이지
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">첨부파일</td>
												<td class="s_tab_view02" colspan="3">
													<input type=radio name="pFileUseYn" value="Y" class="no"  <%=(StringUtil.nvl(rsBbsInfo.getString("file_use_yn")).equals("Y") ? " checked" : "") %>> 사용 &nbsp;&nbsp;
	                                        		<input type=radio name="pFileUseYn" value="N" class="no"  <%=(StringUtil.nvl(rsBbsInfo.getString("file_use_yn")).equals("N") ? " checked" : "") %>> 미사용
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">첨부파일갯수</td>
												<td class="s_tab_view02">
													<select name='pFileCount'>
			                                            <option value='1'<% if(rsBbsInfo.getInt("file_count") == 1) out.print(" selected");%>>1</option>
			                                            <option value='2'<% if(rsBbsInfo.getInt("file_count") == 2) out.print(" selected");%>>2</option>
			                                            <option value='3'<% if(rsBbsInfo.getInt("file_count") == 3) out.print(" selected");%>>3</option>
			                                        </select>
												</td>
												<td class="s_tab_view01" width="92">파일최대크기</td>
												<td class="s_tab_view02">
													<input type=text name="pFileLimit" size=3  dataType="number"  dispName="첨부파일최대크기"  style="text-align:right" value="<%=StringUtil.nvl(rsBbsInfo.getString("file_limit"))%>" >Mbyte
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">웹에디터</td>
												<td class="s_tab_view02" colspan="3">
													<input type=radio name="pEditorYn" value="Y" class="no" <%=(StringUtil.nvl(rsBbsInfo.getString("editor_yn")).equals("Y") ? " checked" :"" ) %>> 사용&nbsp;&nbsp;
					                                <input type=radio name="pEditorYn" value="N" class="no" <%=(StringUtil.nvl(rsBbsInfo.getString("editor_yn")).equals("N") ? " checked" :"" ) %>> 미사용
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<!--tr>
												<td class="s_tab_view01" width="92">상단이미지</td>
												<td class="s_tab_view03" colspan="3">
<%
	if(!StringUtil.nvl(rsBbsInfo.getString("title_img")).equals("")){
		String FileName = StringUtil.nvl(rsBbsInfo.getString("title_img"));
		String FilePath = (String)model.get("FilePath");

		out.print("<a href=\"javascript:fileDownload('"+FileName+"','"+FileName+"','"+FilePath+"','');\">"+FileName+"</a>");
		out.print("&nbsp;&nbsp;");
		out.print("<a href='#' onClick='javascript:delFile()'>기존파일삭제</a>");
		out.print("<br>&nbsp;");
	}
%>
                                        			<input type=file name="pTitleImg" size=50 value="">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">상단태그</td>
												<td class="s_tab_view03" colspan="3">
													<textarea name="pBbsComment" cols="80"><%=StringUtil.nvl(rsBbsInfo.getString("bbs_comment"))%></textarea>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr-->
											<tr>
												<td class="s_tab_view01" width="92">New 표시</td>
												<td class="s_tab_view02">
													<input type=text name="pNewTime" size=3 dispName="New 표시 일수" dataType="number" value='<%=rsBbsInfo.getInt("new_time")%>' style="text-align:right">일 동안
												</td>
												<td class="s_tab_view01" width="92">Hot(조회수)</td>
												<td class="s_tab_view02">
													<input type=text name="pHotChk" size=3 dispName="Hot 표시 조회수" dataType="number" value='<%=rsBbsInfo.getInt("hot_chk")%>' style="text-align:right">회 이상
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">금지단어</td>
												<td class="s_tab_view03" colspan="3">
													<input type=radio name="pBadWordUseYn" value="Y" class="no" <%=(StringUtil.nvl(rsBbsInfo.getString("bad_word_use_yn")).equals("Y") ? " checked" : "" ) %>> 금지단어제한
		                                        	<input type=radio name="pBadWordUseYn" value="N" class="no" <%=(StringUtil.nvl(rsBbsInfo.getString("bad_word_use_yn")).equals("N") ? " checked" : "" ) %>> 제한하지않음.<br>
		                                       		<textarea name="pBadWord" rows=3 cols=80><%=StringUtil.nvl(rsBbsInfo.getString("bad_word"))%></textarea>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">댓글기능</td>
												<td class="s_tab_view02">
													<input type="radio" name="pCommentUseYn" value="Y" class="no" <%=(StringUtil.nvl(rsBbsInfo.getString("comment_use_yn")).equals("Y")? " checked" : "" ) %>> 사용
	                                        		<input type="radio" name="pCommentUseYn" value="N" class="no" <%=(StringUtil.nvl(rsBbsInfo.getString("comment_use_yn")).equals("N")? " checked" : "" ) %>> 미사용
												</td>
												<td class="s_tab_view01" width="92">댓글이모티콘</td>
												<td class="s_tab_view02">
													<input type="radio" name="pEmoticonUseYn" value="Y" class="no" <%=(StringUtil.nvl(rsBbsInfo.getString("emoticon_use_yn")).equals("Y")? " checked": "" ) %>> 사용
	                                       			<input type="radio" name="pEmoticonUseYn" value="N" class="no" <%=(StringUtil.nvl(rsBbsInfo.getString("emoticon_use_yn")).equals("N")? " checked": "" ) %>> 미사용
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">관련글보기</td>
												<td class="s_tab_view02" colspan="3">
													<input type="radio" name="pViewThreadYn" value="Y" class="no" <%=(StringUtil.nvl(rsBbsInfo.getString("view_thread_yn")).equals("Y")? " checked" :"" ) %>> 보임
	                                        		<input type="radio" name="pViewThreadYn" value="N" class="no" <%=(StringUtil.nvl(rsBbsInfo.getString("view_thread_yn")).equals("N")? " checked" :"" ) %>> 안보임 * 뷰 화면에서 관련글 목록 보기
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">이전글<br>다음글</td>
												<td class="s_tab_view02" colspan="3">
													<input type="radio" name="pViewPrevNextYn" value="Y" class="no" <%=(StringUtil.nvl(rsBbsInfo.getString("view_prev_next_yn")).equals("Y")? " checked" : "" ) %>> 보임
	                                        		<input type="radio" name="pViewPrevNextYn" value="N" class="no" <%=(StringUtil.nvl(rsBbsInfo.getString("view_prev_next_yn")).equals("N")? " checked" : "" ) %>> 안보임 * 뷰 화면에서 이전글, 다음글 보기
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
<% if(CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button3("수정", "submitCheck()", "");</script><%	}	%>
												</td>
											</tr>
</form>
<%
	}
    rsBbsInfo.close();
%>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>
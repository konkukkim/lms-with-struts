<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.framework.persist.ListDTO,javax.sql.RowSet,com.edutrack.popupnotice.dto.PopupNoticeDTO"%>
<%@include file="../common/header.jsp" %>
<%
	Map data = (Map)request.getAttribute("MODEL");
	int curPage = Integer.parseInt((String)model.get("curPage"));
%>
<Script Language="javascript">
	function goAdd(mode, seq_no)
	{
		if(mode == "W") {
			document.location = "<%=CONTEXTPATH%>/PopupNotice.cmd?cmd=popupNoticeWrite&pMode=<%=PMODE %>&pRegMode="+mode+"&curPage=<%=curPage%>";
		}else{
			document.location = "<%=CONTEXTPATH%>/PopupNotice.cmd?cmd=popupNoticeWrite&pMode=<%=PMODE %>&pRegMode="+mode+"&pSeqNo="+seq_no+"&curPage=<%=curPage%>";
		}
	}
</Script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<%	ListDTO listObj = (ListDTO)data.get("PopupNoticePagingList"); %>
<%=listObj.getPageScript("f", "curPage", "goPage")%>
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %><script language=javascript>Button5("팝업공지추가", "goAdd('W','')", "");</script><%	}	%>
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
												<td width="">제목</td>
												<td class="s_tablien"></td>
												<td width="140">등록일</td>
												<td class="s_tablien"></td>
												<td width="80">사용유무</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	int num = 0;
	String use_yn = "";
	String target = "";

	int No = listObj.getStartPageNum();
	int i = 0;
	RowSet list= listObj.getItemList();

	if(listObj.getItemCount() > 0){

		while(list.next()){
			i++;
			target = StringUtil.nvl(list.getString("popup_target"));

    		if(target.equals("L"))
    			target = "LMS";
    		else
    			target = "HomePage";

    		if(StringUtil.nvl(list.getString("use_yn")).equals("Y"))
    			use_yn = "<font color='#FF6600'>사용</font>";
    		else
    			use_yn = "미사용";
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04"><a href="javascript:goAdd('E','<%=list.getInt("seq_no")%>')" title="<%=StringUtil.nvl(list.getString("subject"))%>"><%=StringUtil.setMaxLength(StringUtil.nvl(list.getString("subject")),50)%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1, StringUtil.nvl(list.getString("reg_date")))%></td>
												<td></td>
												<td class="s_tab04_cen"><b><%=use_yn%></b></td>
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
												<td class="s_tab04_cen" colspan="9">등록된 팝업이 없습니다.</td>
											</tr>
<%
	}
	list.close();
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="11" height="10" align="right"></td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<table valign=top height="25">
														<tr>
															<td>
<%
	if(listObj.getTotalItemCount() > 0){
    	out.println( listObj.getPagging(SYSTEMCODE, "goPage") );
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


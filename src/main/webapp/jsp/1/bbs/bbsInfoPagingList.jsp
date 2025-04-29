<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@include file="../common/header.jsp" %>

<script language="javascript">
	function goAdd()
	{
		document.location = "<%=CONTEXTPATH%>/BbsInfo.cmd?cmd=bbsInfoWrite";
	}
</script>
<%
    ListDTO listObj = (ListDTO)model.get("bbsInfoList");
    int iTotCnt     = listObj.getTotalItemCount();
    int iCurPage    = listObj.getCurrentPage();
    int iDispLine   = listObj.getListScale();
%>
<%= listObj.getPageScript("f", "curPage", "goPage") %>
<form name="f" method="post" action="<%=CONTEXTPATH%>/BbsInfo.cmd?cmd=bbsInfoPagingList&MENUNO=7&pMode=MyPage">
<input type="hidden" name="curPage" value="<%=iCurPage%>">
										<!-- 내용 -->
										<!-- 게시판리스트 시작 -->
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="13"></td>
											</tr>
											<tr class="s_tab02">
												<td width="66">번호</td>
												<td class="s_tablien"></td>
												<td width="66">코드</td>
												<td class="s_tablien"></td>
												<td width="267">게시판명</td>
												<td class="s_tablien"></td>
												<td width="66">타입</td>
												<td class="s_tablien"></td>
												<td width="66">파일</td>
												<td class="s_tablien"></td>
												<td width="66">웹에디터</td>
												<td class="s_tablien"></td>
												<td width="67">수정</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
<%
    int No = listObj.getStartPageNum();
    int i	=	0;

    RowSet list= listObj.getItemList();

    if (listObj.getItemCount() > 0) {

        while (list.next()) {
            i++;
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04_cen"><%=list.getInt("bbs_id")%></td>
												<td></td>
												<td class="s_tab04_cen"><!--a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=<%=list.getInt("bbs_id")%>"--><%=StringUtil.nvl(list.getString("bbs_name"))%><!--/a--></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("bbs_type"))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("file_use_yn"))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("editor_yn"))%></td>
												<td></td>
												<td class="s_tab04_cen"><a href="<%=CONTEXTPATH%>/BbsInfo.cmd?cmd=bbsInfoEdit&pMode=<%=PMODE%>&pBbsId=<%=list.getInt("bbs_id")%>"><b>[수정]</b></a></td>
											</tr>
<%
			if(i != listObj.getItemCount()) {
%>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
<%
			}
			No = No-1;
		}
	} else {
%>
											<tr>
												<td class="s_tab04_cen" colspan="13">등록된 게시판이 없습니다.</td>
											</tr>
<%
	}
	list.close();
%>
											<tr class="s_tab05">
												<td colspan="13"></td>
											</tr>
											<tr>
												<td align="center" valign="middle" height="30" colspan="13"><%=listObj.getPagging(SYSTEMCODE,"goPage")%></td>
											</tr>
</form>
										</table>
										<!-- // 게시판리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>
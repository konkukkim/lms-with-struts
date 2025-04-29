<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@include file="../common/header.jsp" %>

<Script Language="javascript">
<!--
	function goAdd()
	{
		document.location = "<%=CONTEXTPATH%>/CurriBbsInfo.cmd?cmd=curriBbsInfoWrite";
	}
//-->
</Script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<%
	 ListDTO listObj = (ListDTO)model.get("CurriBbsInfoList");
	 int iTotCnt = listObj.getTotalItemCount();
	 int iCurPage = listObj.getCurrentPage();
	 int iDispLine = listObj.getListScale();
%>
<%= listObj.getPageScript("f", "curPage", "goPage") %>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="">게시판명</td>
												<td class="s_tablien"></td>
												<td width="90">타입</td>
												<td class="s_tablien"></td>
												<td width="70">파일</td>
												<td class="s_tablien"></td>
												<td width="80">웹에디터</td>
												<td class="s_tablien"></td>
												<td width="70">수정</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	int No = listObj.getStartPageNum();
	int i	=	0;
	RowSet 	list	= listObj.getItemList();
	String	addBbsType	=	"";

	if(listObj.getItemCount() > 0){

		while(list.next()){
			i++;
			addBbsType += StringUtil.nvl(list.getString("bbs_type"));
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("bbs_name"))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("bbs_type"))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("file_use_yn"))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("editor_yn"))%></td>
												<td></td>
												<td class="s_tab04_cen">
													<a href="<%=CONTEXTPATH%>/CurriBbsInfo.cmd?cmd=curriBbsInfoEdit&pBbsType=<%=StringUtil.nvl(list.getString("bbs_type"))%>&pMode=<%=PMODE%>"><b>[수정]</b></a>
												</td>
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
												<td class="s_tab04_cen" colspan="11">등록된 게시판이 없습니다.</td>
											</tr>
<%
	}
	list.close();
%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>
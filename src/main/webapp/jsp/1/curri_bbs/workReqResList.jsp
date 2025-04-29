<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet, com.edutrack.framework.persist.ListDTO, com.edutrack.curribbs.dto.CurriWorkReqResDTO"%>
<%@include file="../common/header.jsp" %>
<%
	String	pUserType   = (String)model.get("pUserType");
	String  pWhere	    = (String)model.get("pWhere");
	String  pSTarget    = (String)model.get("pSTarget");
	String  pSWord	    = (String)model.get("pSWord");
%>

<Script Language="javascript">
////function goAdd(){
////	location.href="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=workReqResWrite&pWhere=<%=pWhere%>&pMode=<%= PMODE%>&pRegType=Regist&pMethodType=Insert";
////}

	function replyAdd(param, status){
		var f = document.Input;
		f.pSeqNo.value = param;
		f.pRegType.value = "Reply";
		if(status == "0") f.pMethodType.value = "Insert";
		else f.pMethodType.value = "Update";
		f.action="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=workReqResWrite&pWhere=<%=pWhere%>&pMode=<%= PMODE%>";
		f.submit();
	}

	function searchForm() {
		document.Search.submit();
	}
</Script>

<%
	ListDTO listObj = (ListDTO)model.get("curriWorkList");
	int iTotCnt 		= 	listObj.getTotalItemCount();
 	int iCurPage 		= 	listObj.getCurrentPage();
 	int iDispLine 		= 	listObj.getListScale();
%>
<%= listObj.getPageScript("Input", "curPage", "goPage") %>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name=Input method=post>
<input type="hidden" name="curPage" value="<%=iCurPage%>">
<input type=hidden name=pRegType    value="">
<input type=hidden name=pMethodType value="">
<input type=hidden name=pSeqNo      value="">
</form>
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td height="30" align="right">
																<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bullet01.gif" align="absmiddle" width="10" height="10" border="0">
																<b><font color="#33CCCC">요청</font></b>(학습자)
																→ <b><font color="#FF6600">답변</font></b>(교수자)
																→ <b><font color="#66CC66">만족도</font></b>(학습자)
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
<%
	if (pUserType.equals("S")) {
%>
												<td width="110">답변대상</td>
												<td class="s_tablien"></td>
<%
	} else {
%>
												<td width="110">요청자</td>
												<td class="s_tablien"></td>
<%
	}

	if (USERTYPE.equals("M")) {
%>
												<td width="110">답변대상</td>
												<td class="s_tablien"></td>
<%
	}
%>
												<td width="110">요청일자</td>
												<td class="s_tablien"></td>
												<td width="80">상태</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
    String totCnt = (String)model.get("totCnt");
    String	statusStr = "";
    int No = listObj.getStartPageNum();
    RowSet list= listObj.getItemList();

    if(listObj.getItemCount() > 0) {

        while (list.next()) {
            statusStr = StringUtil.nvl(list.getString("work_status")).equals("0") ? "<font color='#33CCCC'><b>요청</b></font>" : list.getInt("point") == 0 ? "<font color='#FF6600'><b>답변</b></font>" : "<font color='#66CC66'><b>만족도</b></font>";

%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04">
<%
			if(pUserType.equals("S") || list.getInt("point") != 0) {
%>
													<a href="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=curriWorkReqResShow&pWhere=<%=pWhere%>&pMode=<%= PMODE%>&pSeqNo=<%=list.getInt("seq_no")%>"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(StringUtil.nvl(list.getString("req_subject"))),40)%></a>
<%
            } else {
%>
													<a href="javascript:replyAdd('<%=list.getInt("seq_no")%>', '<%=StringUtil.nvl(list.getString("work_status"))%>');"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(StringUtil.nvl(list.getString("req_subject"))),40)%></a>
<%
			}
%>
												</td>
												<td></td>
												<td class="s_tab04_cen">
<%
			// 학생자일경우 교수 아이디를 보여주고
			// 그 이외에는  학생 아이디를 보여준다

			if (pUserType.equals("S")) {
				out.println( "\t" + CommonUtil.getUserName(SYSTEMCODE,StringUtil.nvl(list.getString("prof_id")))  );
			} else {
    			out.println( "\t" + CommonUtil.getUserName(SYSTEMCODE,StringUtil.nvl(list.getString("student_id"))) );
			}
%>
												</td>
												<td></td>
<%
			if (USERTYPE.equals("M")) {
%>
												<td class="s_tab04_cen"><%=CommonUtil.getUserName(SYSTEMCODE,StringUtil.nvl(list.getString("prof_id")))%></td>
												<td></td>
<%
			}
%>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("req_reg_date")))%></td>
												<td></td>
												<td class="s_tab04_cen">
<%
			if(pUserType.equals("S")) {
%>
													<%=statusStr%>
<%
			} else {
%>
													<a href="javascript:replyAdd('<%=list.getInt("seq_no")%>', '<%=StringUtil.nvl(list.getString("work_status"))%>');"><%=statusStr%></a>
<%
			}
%>
												</td>
											</tr>
<%		if(No > 1) { %>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
			}
        	No = No-1;
		}
        list.close();
    } else {
%>
											<tr>
												<td class="s_tab04_cen" colspan="11">등록된 정보가  없습니다.</td>
											</tr>
<%
	}
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
															<td><%= listObj.getPagging(SYSTEMCODE,"goPage") %></td>
														</tr>
													</table>
													<table>
<!-- form start -->
<form name=Search method=post aciton="/">
														<tr>
															<td align=middle height=30>
																<select name=psearchkey>
<%
	if(!pUserType.equals("S")){
%>
																	<option value="student_id" <%=pSTarget.equals("student_id") ? "selected" : ""%>>요청자ID</option>
<%
	}

	if(!pUserType.equals("P")){
%>
																	<option value="prof_id"    <%=pSTarget.equals("prof_id")    ? "selected" : ""%>>교수자ID</option>
<%
	}
%>
																	<option>제목</option>
																</select>
																<input maxlength=30 size=22 name=pSWord>
																<a href="javascript:searchForm()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
															</td>
														</tr>
</form>
<!-- form end -->
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
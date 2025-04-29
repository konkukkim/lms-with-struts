<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.common.dto.CodeParam,com.edutrack.common.dao.CommonDAO,com.edutrack.common.dto.CodeDTO"%>
<%@include file="../common/header.jsp" %>
<script language="javascript">
   function Search_Prog(){
   	  var f = document.ProgList;

      if(!validate(f)) return;

      f.action="<%=CONTEXTPATH%>/ProgAuthor.cmd?cmd=getProgAuthorList";
      f.submit();
   }

   function Create_Prog(){
   	  var f = document.ProgList;
   	  f.pGUBUN.value = "write";

      f.action = "<%=CONTEXTPATH%>/ProgAuthor.cmd?cmd=progAuthorWrite";
      f.submit();
   }

   function Show_AuthorInfo(workgubun, progseq){
        var loc="<%=CONTEXTPATH%>/ProgAuthor.cmd?cmd=progAuthorWrite&pGUBUN=edit&pSoCode="+workgubun+"&pProgSeq="+progseq;
        document.location = loc;
    }

</script>
<% ListDTO data = (ListDTO)model.get("authorList");
   String pSoCode = (String)model.get("pSoCode");
   String pFields = (String)model.get("pFields");
   String pValue = (String)model.get("pValue");
%>
<%= data.getPageScript("ProgList", "curPage", "goPage") %>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
<form name="ProgList" method="post" action="<%=CONTEXTPATH%>/ProgAuthor.cmd?cmd=getProgAuthorList" >
<input type="hidden" name="curPage" value="1">
<input type="hidden" name="pGUBUN" value="">
														<tr>
															<td width="50%">
<%
	CodeParam param2 = new CodeParam();
	param2.setSystemcode(SYSTEMCODE);
	param2.setType("select");
	param2.setName("pSoCode");
	param2.setFirst("--페이지 구분선택--");
	param2.setEvent("Search_Prog()");
	param2.setSelected(pSoCode);
	out.print(CommonUtil.getSoCode(param2, "501"));
%>
															</td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("프로그램권한등록", "Create_Prog()", "");</script>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="80">구분</td>
												<td class="s_tablien"></td>
												<td width="">프로그램명</td>
												<td class="s_tablien"></td>
												<td width="">프로그램ID</td>
												<td class="s_tablien"></td>
												<td width="70">접근가능</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	int No = data.getStartPageNum();
	RowSet list= (RowSet)data.getItemList();
	int	chkNo	=	0;
	while(list.next()){
	chkNo++;
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=No%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("work_name"))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("prog_name"))%></td>
												<td></td>
												<td class="s_tab04"><a href="javascript:Show_AuthorInfo('<%=StringUtil.nvl(list.getString("work_gubun"))%>','<%=list.getInt("prog_seq")%>');"><%=StringUtil.nvl(list.getString("prog_id"))%></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("view_level"))%></td>
											</tr>
<%			if(No > 1) { %>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
		}
		No = No-1;
	}
	list.close();
%>
<!-- 없을 경우 -->
<%
	if(data.getTotalItemCount() < 1){
%>
											<tr>
												<td class="s_tab04_cen" colspan="11">프로그램권한이 존재하지 않습니다.</td>
											</tr>
<%	}	%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="center" valign="middle">
<%
	CommonDAO common = new CommonDAO(); //"M:관리자, S:학습자, P:교수자"
	ArrayList codeList = common.getSoCode(SYSTEMCODE,"101");
	CodeDTO code = null;
	
	for(int i=0; i<codeList.size();i++){
		code = (CodeDTO)codeList.get(i);

		out.println( (i==0) ? "* ": ", ");	
		out.println(code.getCode() +":"+ code.getName());	
	}
%>													
												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<table valign=top height="25">
														<tr>
															<td>
																<%= data.getPagging(SYSTEMCODE, "goPage") %>
															</td>
														</tr>
													</table>
													<table>
														<tr>
															<td align=middle height=30>
																<select name=pFields>
																	<option value="prog_id" <% if(pFields.equals("") || pFields.equals("prog_id")){%>selected<%}%>>ID</option>
																	<option value="prog_name" <% if(pFields.equals("prog_name")){%>selected<%}%>>내용</option>
																</select>
																<input maxlength=30 size=22 name=pValue value="<%=pValue%>" dispName="검색어"  lenCheck=20>
																<a href="javascript:Search_Prog();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- // 페이지 리스트, 검색부분 -->
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						

<%@include file="../common/footer.jsp" %>

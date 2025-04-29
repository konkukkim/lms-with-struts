<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/header.jsp" %>
<%@ page import ="com.edutrack.progauthor.dto.ProgAuthorDTO,com.edutrack.common.dto.CodeParam"%>
<%
    ProgAuthorDTO authorInfo = (ProgAuthorDTO)model.get("authorInfo");
    String pGUBUN = (String)model.get("pGUBUN");
    String pSoCode = (String)model.get("pSoCode");
    String[] width = new String[]{"150","347"};
%>
<script language="javascript">
<!--
	function SubmitCheck()
	{
		var f = document.Regist;

		if(!validate(f)) return;


   	  if(f.pSoCode.value == ""){
   	     alert("페이지 구분을 선택해 주세요.");
   	     f.pSoCode.focus();
   	     return;
   	  }

  	   var checkVal = getChkBoxByValue(f, "pViewLevel", "");

	   if(checkVal == "") {
	       alert("권한을 선택해 주세요.");
	       return;
	   }

		f.submit();
    }

    function goList(){
        var f = document.Regist;

        var loc="<%=CONTEXTPATH%>/ProgAuthor.cmd?cmd=getProgAuthorList&pSoCode=<%=pSoCode%>";
        document.location = loc;
    }


    function doDelete(){
        var f = document.Regist;

        if(confirm("프로그램 권한관리를 삭제하시겠읍니까?") == false ) return ;

        f.action="<%=CONTEXTPATH%>/ProgAuthor.cmd?cmd=progAuthorDelete";
        f.submit();

    }
//-->
</script>

										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<form name=Regist method="post" action="<%=CONTEXTPATH%>/ProgAuthor.cmd?cmd=progAuthorRegist">
<input type=hidden name="pGUBUN" value="<%=pGUBUN%>">
<input type=hidden name="pProgSeq" value="<%=authorInfo.getProgSeq()%>">

											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">* 페이지구분</td>
												<td class="s_tab_view02">
<%

	if( pGUBUN.equals("write") ) {

		CodeParam param2 = new CodeParam();
		param2.setSystemcode(SYSTEMCODE);
		param2.setType("select");
		param2.setName("pSoCode");
		param2.setSelected(pSoCode);
		out.print(CommonUtil.getSoCode(param2, "501"));
	 }
	 else
	 {
		out.println("<input type=hidden name=pSoCode value='"+pSoCode+"'>");
		out.println( CommonUtil.getCodeSoName(SYSTEMCODE,"501", pSoCode));
	 }
 %>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">* 프로그램ID</td>
												<td class="s_tab_view02"><input type="text" name="pProgId" size=60 class="sky" dispName="프로그램ID" notNull lenCheck=100 value="<%=authorInfo.getProgId()%>" ></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">프로그램명</td>
												<td class="s_tab_view02"><input type="text" name="pProgName" size="60" class="sky" dispName="프로그램명" lenCheck=100 value="<%=authorInfo.getProgName()%>" ></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">프로그램설명</td>
												<td class="s_tab_view03"><textarea name="pProgComment" cols=70 rows=7 class="sky" dispName="프로그램설명" lenCheck=4000><%=authorInfo.getProgComment()%></textarea></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">* 접근가능</td>
												<td class="s_tab_view02">
<%
	CodeParam param = new CodeParam();
	param.setSystemcode(SYSTEMCODE);
	param.setType("check");
	param.setName("pViewLevel");
	param.setSelected(authorInfo.getViewLevel());
	out.print(CommonUtil.getSoCode(param, "101"));
%>
												</td>
											</tr>

											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
&nbsp;<script language=javascript>Button3("등록", "SubmitCheck('<%=pGUBUN%>')", "");</script>
<%	if(!pGUBUN.equals("write") ) { %>
&nbsp;<script language=javascript>Button3("삭제", "doDelete('<%=pGUBUN %>')", "");</script>
<%	} %>
												</td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						<!-- // 본문 -->
<%@include file="../common/footer.jsp" %>
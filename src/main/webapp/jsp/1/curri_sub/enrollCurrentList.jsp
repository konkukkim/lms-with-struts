<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.currisub.dao.CurriSubDAO,com.edutrack.currisub.dto.CurriSubDTO"%> 
<%@ page import = "com.edutrack.curridate.dto.CurriDateDTO"%>
<%@include file="../common/header.jsp" %>
<%
	//-- 소분류 카테고리 정보
	Map		paramMap	=	(Map)model.get("ParamMap");
	int		codeSize	=	StringUtil.nvl(paramMap.get("code_size"), 0);
	int[]	code_size	=	new int[codeSize];
	String	pareCode2[]	=	new String[codeSize];
	String	pPare_code2	=	StringUtil.nvl(model.get("pPare_code2"));
	for(int i=0; i<codeSize; i++) {
		pareCode2[i]	=	StringUtil.nvl(paramMap.get("cateCode["+i+"]"));
	}
	
	String	pRegMode	=	StringUtil.nvl(model.get("pRegMode"));
	CurriDateDTO	dateDto	=	null;
	dateDto	=	(CurriDateDTO)model.get("curriDateDto");
	String	enrollDate	=	"";
	if(dateDto != null) {
		//-- 임의로 값을 하나 받아와서 값이 있다면 수강신청기간 중, 아니면 수강신청 정정기간이 끝남.
		enrollDate	=	StringUtil.nvl(dateDto.getEnrollStart());
	}
%>
<script language="javascript">
<!--
	function Check_Submit() {
<%	if(!enrollDate.equals("")) {	%>

        <%	if(pRegMode.equals("EDIT")){	%>
		if(!confirm("수강신청을 정정하시겠습니까? \n\n수강신청 내용을 정정하시면 인증을 다시 받아야 강의를 들으실 수 있습니다.")) return;
		<% } %>
		
		var f = document.Input;
		f.submit();
<%	} else {	%>
		alert("수강신청 기간이 지났습니다.");
<%	}	%>
	}
	
	function autoReload() {
	
		var f = document.Input;
		f.action = "<%=CONTEXTPATH%>/CurriSub.cmd?cmd=currentList&pMode=<%=PMODE%>&MENUNO=0";
		f.submit();
	}
-->
</script>
										<table width="670" align="center" border="0">
<form name="Input" method="post" action="/Student.cmd?cmd=enrollProcess">
<input type="hidden" name="pCodeSize" value="<%=codeSize%>">
<input type="hidden" name="pRegMode" value="<%=pRegMode%>">
											<tr>
												<td height="27" colspan="13" class="s_btn01"><b>* 아래의 과목에서 한 과목씩을 선택하시여 신청해 주세요.</b></td>
											</tr>
<%	if(USERTYPE.equals("M") || USERTYPE.equals("P") || USERTYPE.equals("J")) {	%>
											<tr>
												<td colspan="13" class="s_btn01" align="right"><b>
학년 : <select name="pPare_code2" onChange="autoReload()">
	<option value="1" <%if(pPare_code2.equals("1")){%>selected<%}%>>1학년</option>
	<option value="2" <%if(pPare_code2.equals("2")){%>selected<%}%>>2학년</option>
	<option value="3" <%if(pPare_code2.equals("3")){%>selected<%}%>>3학년</option>
	<option value="4" <%if(pPare_code2.equals("4")){%>selected<%}%>>특강생</option>
</select></b>
												</td>
											</tr>
<%	}	%>
											<tr class="s_tab01">
												<td colspan="13"></td>
											</tr>
											<tr class="s_tab02">
												<td width="80">과목</td>
												<td class="s_tablien"></td>
												<td width="">선택과목</td>
												<td class="s_tablien"></td>
												<td width="60">선택</td>
												<td class="s_tablien"></td>
												<td width="160">주제</td>
												<td class="s_tablien"></td>
												<td width="100">교수 (ID)</td>
												<td class="s_tablien"></td>
												<td width="50">학점</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%
	//-- 수강신청 강좌 리스트
	RowSet	enrollList	=	(RowSet)model.get("curriEnrollList");
	int		No			=	0;
	int		No1			=	0;
	int		courseNo	=	0;
	String	cateCode	=	"";
	String	pCateCode 	=	"";	
	int		pStuCount	=	0;
	String	pCheck		=	"";
	String	pEnrollYn	=	"";
	
	while(enrollList.next()) {
		No1++;
		
		pEnrollYn	=	StringUtil.nvl(enrollList.getString("enroll_yn"));
		//-- 기존 카테고리 코드와 새로 받아온 코드가 다르면....
		if(!pCateCode.equals(StringUtil.nvl(enrollList.getString("cate_code")))) {

			//-- ROWSPAN 에 들어갈 값
			code_size[No]	=	StringUtil.nvl(enrollList.getString(StringUtil.nvl(enrollList.getString("cate_code"))+"_cnt"), 0)*2-1;
			
			//-- 소카테고리에 포함된 과정수가 0이라면..
			if(!enrollDate.equals("") && code_size[No] <= 0) {
				//No++;
				code_size[No]	=	StringUtil.nvl(enrollList.getString(pareCode2[No]+"_cnt"), 0)*2-1;
			}
			if(No1 > 1) {
%>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
<%			}
			if(code_size[No] > 0) {	%>
											<tr>
												<td class="s_tab04_cen" rowspan="<%=code_size[No]%>"><%=StringUtil.nvl(enrollList.getString("cate_name"))%></td>
												<td rowspan="<%=code_size[No]%>"></td>
												<td class="s_tab04"><%=StringUtil.nvl(enrollList.getString("curri_name"))%></td>
												<td></td>
												<td class="s_tab04_cen"><input type="radio" name="<%=StringUtil.nvl(enrollList.getString("cate_code"))%>_CHK" value="<%=StringUtil.nvl(enrollList.getString("curri_code"))%>^<%=StringUtil.nvl(enrollList.getString("curri_year"))%>^<%=StringUtil.nvl(enrollList.getString("curri_term"))%>" checked <% if(pEnrollYn.equals("N")){ %>disabled<% } %>></td>
												<td></td>
												<td class="s_tab04"><%=StringUtil.nvl(enrollList.getString("curri_info"))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(enrollList.getString("prof_name"))%> (<%=StringUtil.nvl(enrollList.getString("prof_id"))%>)</td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(enrollList.getString("credit"))%></td>
											</tr>
<%			} else {
				//수강신청 기간이 아니라면....
				if(enrollDate.equals("")) {	%>
											<tr>
												<td class="s_tab04_cen" rowspan="<%=code_size[No]%>"><%=StringUtil.nvl(enrollList.getString("cate_name"))%></td>
												<td rowspan="<%=code_size[No]%>"></td>
												<td class="s_tab04" colspan="9" align="center">수강신청 할 수 있는 해당 카테고리 과정이 없습니다.</td>
											</tr>
<%
				}
			}
			No++;
		} else {
			cateCode	=	StringUtil.nvl(enrollList.getString("cate_code"));
			courseNo	=	StringUtil.nvl(enrollList.getString(cateCode+"_cnt"), 0);
			if(!enrollDate.equals("") && courseNo > 0) {
				if(No1 > 1) {	%>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
<%				}
				pStuCount	=	StringUtil.nvl(enrollList.getString("stu_cnt"), 0);
%>
											<tr>
												<td class="s_tab04"><%=StringUtil.nvl(enrollList.getString("curri_name"))%></td>
												<td></td>
												<td class="s_tab04_cen"><input type="radio" name="<%=StringUtil.nvl(enrollList.getString("cate_code"))%>_CHK" value="<%=StringUtil.nvl(enrollList.getString("curri_code"))%>^<%=StringUtil.nvl(enrollList.getString("curri_year"))%>^<%=StringUtil.nvl(enrollList.getString("curri_term"))%>" <%if(pStuCount > 0) {%>checked<% } %> <% if(pEnrollYn.equals("N")){ %>disabled<% } %>></td>
												<td></td>
												<td class="s_tab04"><%=StringUtil.nvl(enrollList.getString("curri_info"))%></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(enrollList.getString("prof_name"))%> (<%=StringUtil.nvl(enrollList.getString("prof_id"))%>)</td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(enrollList.getString("credit"))%></td>
											</tr>
<%
			}
		}
		//-- 카테고리 코드를 저장한다.
		pCateCode	=	StringUtil.nvl(enrollList.getString("cate_code"));
		pEnrollYn	=	"";
	}
	enrollList.close();
%>
											<tr class="s_tab05">
												<td colspan="13"></td>
											</tr>
											<tr class="s_tab04">
												<td height="35" valign="middle" colspan="13" align="right">
<%	if(pRegMode.equals("WRITE")){	%>
<script language=javascript>Button3("신청하기", "Check_Submit()", "");</script>
<%	} else {	%>
<script language=javascript>Button3("정정하기", "Check_Submit()", "");</script>
<%
	}
%>
												</td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
</form>
<%@include file="../common/footer.jsp" %>
<Script>
    <%	if(pRegMode.equals("EDIT")){	%>
    alert("이미 수강신청을 하셨습니다.");
    <% } %>
</Script>
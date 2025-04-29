<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.curritop.dto.CurriCategoryDTO"%>
<%@ page import = "com.edutrack.curritop.dto.CurriCategoryTotalDTO" %>
<%@include file="../common/header.jsp" %>
<%
	String	pStep = (String)model.get("pStep");
	String	cate_info	=	"";
	String	bgimgname	=	"mtit5_01.gif";
	String	imgname1	=	"tle_manage11.gif";
	String	imgname2	=	"tle_manage21.gif";
	String	imgname3	=	"tle_manage31.gif";

	String temp = "";
	int cateDepth = 0;

	if (pStep.equals("1")) {
		temp = String.valueOf(model.get("cate_depth"));
		cateDepth = Integer.parseInt(temp);
		imgname1	=	"tle_manage12.gif";
		bgimgname	=	"mtit5_01_01.gif";
	}
	else if (pStep.equals("2")) {
		cate_info = (String)model.get("cateInfo");
		imgname2	=	"tle_manage22.gif";
		bgimgname	=	"mtit5_01_02.gif";
	}
	else {
		imgname3	=	"tle_manage32.gif";
		bgimgname	=	"mtit5_01_03.gif";
	}
%>
<script language="javascript">
	function submitCheck() {
		var f = document.Input;

		if(!validate(f))
			return false;

//		else return true;
		f.submit();
	}

	function goDelete() {
<%
	if (pStep.equals("1")) {
%>
		if (<%=(String)model.get("curriCnt")%> > 0) {
			alert('대분류에 속한 중분류가 <%=(String)model.get("curriCnt")%>개, 소분류가 <%=(String)model.get("cnt")%>개가 있습니다. \n\n삭제 하고자 할 경우 소분류부터 먼저 삭제해 주셔야 합니다.');
		} else {
<%
	}
	else if (pStep.equals("2")) {
%>
		if (<%=(String)model.get("curriCnt")%> > 0) {
			alert('중분류에 속한 소분류가 <%=(String)model.get("curriCnt")%>개가 있습니다. \n\n삭제 하고자 할 경우 소분류를 먼저 삭제해 주셔야 합니다.');
		} else {
<%
	}
%>
			var yes = confirm("과정분류를 삭제하려고 합니다.\n\n삭제하시겠습니까?");
			if (yes == true)
				document.location = "CurriCategory.cmd?cmd=curriCategoryDelete&pCateCode=<%=model.get("cateCode")%>&pStep=<%=pStep%>";
			else
				return;
<%
	if (pStep.equals("1") || pStep.equals("2")) {
%>
		}
<%
	}
%>
	}

	function goList() {
		document.location.href = "<%=CONTEXTPATH%>/CurriCategory.cmd?cmd=curriCategoryList&pStep=<%=pStep%>&MENUNO=2";
	}
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start   onSubmit="return submitCheck()"  -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/CurriCategory.cmd?cmd=curriCategoryRegist&pRegMode=Edit&pStep=<%=pStep%>" enctype="multipart/form-data">
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
<%
	if (pStep.equals("2")) {
		CurriCategoryTotalDTO curriCategoryTotalDto = null;
		ArrayList step2 = (ArrayList)model.get("step2");
%>
											<tr>
												<td class="s_tab_view01" width="120">대분류명</td>
												<td class="s_tab_view02" width="200" colspan="3">
													<select name="pPareCode1" notNull dispName="대분류">
														<option value="">-- 대분류를 선택해주세요 --</option>
<%
		for (int i=0; i<step2.size(); i++) {
			curriCategoryTotalDto = (CurriCategoryTotalDTO)step2.get(i);
%>
														<option value="<%=curriCategoryTotalDto.getPareCode1()%>" <%if (model.get("pare_code1").equals(curriCategoryTotalDto.getPareCode1())) out.println("selected");%>><%=curriCategoryTotalDto.getCateName()%></option>
<%
		}
%>
													</select>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%
	}
	if (pStep.equals("3")) {
		CurriCategoryTotalDTO curriCategoryTotalDto = null;
		ArrayList step3 = (ArrayList)model.get("step3");
%>
											<tr>
												<td class="s_tab_view01" width="120">중분류명</td>
												<td class="s_tab_view02" colspan="3">
													<select name="pPareCode2" notNull dispName="중분류">
														<option value="">-- 중분류를 선택해주세요 --</option>
<%
		for (int i=0; i<step3.size(); i++) {
			curriCategoryTotalDto = (CurriCategoryTotalDTO)step3.get(i);
%>

														<option value="<%=curriCategoryTotalDto.getPareCode1()%>|<%=curriCategoryTotalDto.getPareCode2()%>" <%if (((String)model.get("pare_code1")+(String)model.get("pare_code2")).equals(curriCategoryTotalDto.getPareCode1()+curriCategoryTotalDto.getPareCode2())) out.println("selected");%>>[<%=curriCategoryTotalDto.getPareCode1Name()%>]<%=curriCategoryTotalDto.getPareCode2Name()%></option>
<%
		}
%>
													</select>
													<input type="hidden" name="pPareCode1" value="<%=curriCategoryTotalDto.getPareCode1()%>">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%
	} // end if
%>
											<tr>
												<td class="s_tab_view01" width="120">분류코드</td>
												<td class="s_tab_view02" colspan="3">
													<%--<input type="text" name="pCateCode" value="<%=model.get("cateCode")%>" size="4" maxlenght="4" dispName="과정분류코드" notNull class="green">--%>
													&nbsp;<%=model.get("cateCode")%></b><input type=hidden name=pCateCode value='<%=model.get("cateCode")%>'>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">분류명</td>
												<td class="s_tab_view02" colspan="3">
													<input type="text" class="green" name="pCateName" size=40 maxlength=40 dispName="과정분류명" notNull value="<%=model.get("cateName")%>">
												</td>
											</tr>
<%
	if (pStep.equals("1")) {
%>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">분류레벨</td>
												<td class="s_tab_view02" colspan="3">
													<select name="pCateDepth" notNull dispName="분류 레벨">
														<option value="">-- 분류 레벨 --</option>
														<option value="2"<% if(cateDepth == 2) out.println(" selected");%>>2</option>
														<option value="3"<% if(cateDepth == 3) out.println(" selected");%>>3</option>
													</select>
												</td>
											</tr>
<%
	}
%>
											<!--tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">분류이미지1</td>
												<td class="s_tab_view03" colspan="3">
													<%=model.get("cateOldImg1")%><input type=file name="pTitleImg[1]" size="50">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">분류이미지2</td>
												<td class="s_tab_view03" colspan="3">
													<%=model.get("cateOldImg2")%><input type=file name="pTitleImg[2]" class="green" size="50">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">분류이미지3</td>
												<td class="s_tab_view03" colspan="3">
													<%=model.get("cateOldImg3")%><input type=file name="pTitleImg[3]" class="green" size="50">
												</td>
											</tr-->
<%
	if (pStep.equals("2")) {
%>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">상세정보</td>
												<td class="s_tab_view03" colspan="3">
													<textarea name="pCateInfo" cols="70" rows="20" class="green"><%=cate_info%></textarea>
												</td>
											</tr>
<%
	}
%>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">사용여부</td>
												<td class="s_tab_view02" colspan="3">
													<input type="radio"name="pUseYn" value="Y" <%=model.get("chkUseY")%> class="no">사용
													&nbsp;<input type="radio" name="pUseYn" value="N"  <%=model.get("chkUseN")%> class="no">사용안함
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<script language=javascript>Button3("수정", "submitCheck()", "");</script>
&nbsp;<script language=javascript>Button3("삭제", "goDelete()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script>
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







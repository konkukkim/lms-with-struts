<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.curritop.dto.CurriCategoryDTO"%>
<%@ page import = "com.edutrack.curritop.dto.CurriCategoryTotalDTO" %>
<%@include file="../common/header.jsp" %>
<%
	String pStep = (String)model.get("pStep");
	String	bgimgname	=	"mtit5_01.gif";

	if (pStep.equals("1")) {
		bgimgname	=	"mtit5_01_01.gif";
	}
	else if (pStep.equals("2")) {
		bgimgname	=	"mtit5_01_02.gif";
	}
	else {
		bgimgname	=	"mtit5_01_03.gif";
	}
%>
<script language="javascript">

	function submitCheck() {
		var f = document.Input;

		if(!validate(f))
			return false;

		if(!isHangulChat(f.pCateCode.value)) {
			alert('한글은 입력하실 수 없습니다.');
			f.pCateCode.focus();
			return false;
		}

		f.submit();
	}

	function goList() {
		document.location.href = "<%=CONTEXTPATH%>/CurriCategory.cmd?cmd=curriCategoryList&pStep=<%=pStep%>&MENUNO=2";
	}
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start   onSubmit="return submitCheck()"  -->
<form name="Input" method="post" action="<%=CONTEXTPATH%>/CurriCategory.cmd?cmd=curriCategoryRegist&pRegMode=Add&pStep=<%=pStep%>" enctype="multipart/form-data">
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
														<option value="<%=curriCategoryTotalDto.getPareCode1()%>"><%=curriCategoryTotalDto.getCateName()%></option>
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
														<option value="<%=curriCategoryTotalDto.getPareCode1()%>|<%=curriCategoryTotalDto.getPareCode2()%>">[<%=curriCategoryTotalDto.getPareCode1Name()%>]<%=curriCategoryTotalDto.getPareCode2Name()%></option>
<%
		} //end for
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
													<input type="text" name="pCateCode" value="" size="10" maxlenght="10" dispName="분류코드" notNull> * 영문 또는 숫자 10자리 까지
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">분류명</td>
												<td class="s_tab_view02" colspan="3">
													<input type="text" name="pCateName" size="40" maxlength="40" dispName="과정분류명" notNull>
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
														<option value="2">2</option>
														<option value="3">3</option>
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
													<input type="file" name="pTitleImg[1]" size="50">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">분류이미지2</td>
												<td class="s_tab_view03" colspan="3">
													<input type="file" name="pTitleImg[2]" size="50">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">분류이미지3</td>
												<td class="s_tab_view03" colspan="3">
													<input type="file" name="pTitleImg[3]" size="50">
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
													<textarea name="pCateInfo" cols="70" rows="20" class="green"><%//=cate_info%></textarea>
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
													<input type="radio"name="pUseYn" value="Y" class="no" checked>사용
													&nbsp;<input type="radio" name="pUseYn" value="N" class="no">사용안함
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<script language=javascript>Button3("등록", "submitCheck()", "");</script>
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
<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.curritop.dto.CurriTopDTO,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%>
<%@ page import = "com.edutrack.curritop.dao.CurriCategoryDAO, com.edutrack.curritop.dto.CurriCategoryTotalDTO" %>
<%@include file="../common/header.jsp" %>
<script language="javascript">
	function submitCheck()
	{
		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */
		var f = document.Input;
		if(!validate(f)) return false;
		//else return true;
		f.submit();
	}


	function selectarticle(selected) {
		var frm	=	document.Input;
<%
		CurriCategoryTotalDTO selectDto1 = null;
		ArrayList pCate1 = (ArrayList)model.get("pCate1");

		for (int i=0; i<pCate1.size(); i++) {
			selectDto1 = (CurriCategoryTotalDTO)pCate1.get(i);
			if (selectDto1.getCateStatus().equals("0")){
%>
				if (selected == '<%=selectDto1.getPareCode1()%>') {
					frm.pCateCode.disabled = false;
					frm.pCateCode.style.visibility="visible";
					initSel2();
<%
					CurriCategoryDAO curriCategoryDao = new CurriCategoryDAO();
					ArrayList pCate2 = null;
					pCate2 = curriCategoryDao.getCategory2(SYSTEMCODE, "3", selectDto1.getPareCode1());
					CurriCategoryTotalDTO select2Dto = null;

					for(int j=0; j<pCate2.size(); j++){
						select2Dto = (CurriCategoryTotalDTO)pCate2.get(j);
%>
						frm.pPareCode2.options[<%=j%>] = new Option ('<%=select2Dto.getCateName()%>');
						frm.pPareCode2.options[<%=j%>].value = '<%=select2Dto.getPareCode2()%>';
<%
					}
%>
					selectlesson(frm.pPareCode2.options[frm.pPareCode2.selectedIndex].value);

				}
<%
			}
			//else if(selectDto1.getCateStatus().equals("1")){
			else if (!selectDto1.getCateStatus().equals("0")){
%>
				if (selected == '<%=selectDto1.getPareCode1()%>') {
					initSel2();
<%
					CurriCategoryDAO curriCategoryDao = new CurriCategoryDAO();
					ArrayList pCate2 = null;
					pCate2 = curriCategoryDao.getCategory2(SYSTEMCODE, "3", selectDto1.getPareCode1());
					CurriCategoryTotalDTO select2Dto = null;

					for(int j=0; j<pCate2.size(); j++){
						select2Dto = (CurriCategoryTotalDTO)pCate2.get(j);
%>
						frm.pPareCode2.options[<%=j%>] = new Option ('<%=select2Dto.getCateName()%>');
						frm.pPareCode2.options[<%=j%>].value = '<%=select2Dto.getPareCode2()%>';
<%
					}
%>
					frm.pCateCode.disabled = false;
					initSel();

					frm.pCateCode.style.visibility="hidden";
					frm.pCateCode.options[0] = new Option ('오프');
					frm.pCateCode.options[0].value = 'off';

				}


<%
			}
		}
%>
	}

	function selectlesson (selected) {
		var frm	=	document.Input;
<%
		CurriCategoryTotalDTO selectDto3 = null;
		ArrayList pCate2 = (ArrayList)model.get("pCate2");

		for(int k=0; k<pCate2.size(); k++){
			selectDto3 = (CurriCategoryTotalDTO)pCate2.get(k);
%>
			if (selected == '<%=selectDto3.getPareCode2()%>') {
				frm.pCateCode.disabled = false;
				initSel();
<%
				CurriCategoryDAO curriCategoryDao = new CurriCategoryDAO();
				ArrayList pCate3 = null;
				pCate3 = curriCategoryDao.getCategory2(SYSTEMCODE, "4", selectDto3.getPareCode2());
				CurriCategoryTotalDTO selectDto5 = null;

				for(int l=0; l<pCate3.size(); l++){
					selectDto5 = (CurriCategoryTotalDTO)pCate3.get(l);
%>
					frm.pCateCode.options[<%=l%>] = new Option ('<%=selectDto5.getCateName()%>');
					frm.pCateCode.options[<%=l%>].value = '<%=selectDto5.getCateCode()%>';
<%
				}
%>
			}
<%
		}
%>
	}

	function initSel() {
		var frm	=	document.Input;
		with( frm ){
			frm.pCateCode.value = "";
			frm.pCateCode.length = 0; //셀렉트박스 초기화
		}
	}

	function initSel2() {
		var frm	=	document.Input;
		with( frm ){
			frm.pPareCode2.value = "";
			frm.pPareCode2.length = 0; //셀렉트박스 초기화
		}
	}

	function goList() {
		document.location = "<%=CONTEXTPATH%>/CurriTop.cmd?cmd=curriTopList&pMode=<%=PMODE%>&pProperty1=<%=model.get("pProperty1")%>";
	}
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start    onSubmit="return submitCheck()"  -->
<form name="Input" method="post" action="<%=CONTEXTPATH%>/CurriTop.cmd?cmd=curriTopRegist&pRegMode=Add" enctype="multipart/form-data">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정코드</td>
												<td class="s_tab_view02"><input type="text" name="pCurriCode" size="14" maxlength="12" dispName="과정코드" notNull value=""></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정명</td>
												<td class="s_tab_view02"><input type="text" name="pCurriName" maxlength="40" dispName="과정명" notNull size="50" value=""></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	CurriCategoryTotalDTO curriCategoryTotalDto = null;
	ArrayList pCate = (ArrayList)model.get("pCate1");
%>
											<tr>
												<td class="s_tab_view01" width="120">과정분류</td>
												<td class="s_tab_view02">
													<select name="pPareCode1" size="1" onChange="selectarticle(document.Input.pPareCode1.options[document.Input.pPareCode1.selectedIndex].value);">
														<option value="">--- 대분류 ---</option>
<%
	for (int i=0; i<pCate.size(); i++) {
			curriCategoryTotalDto = (CurriCategoryTotalDTO)pCate.get(i);
%>
														<option value="<%=curriCategoryTotalDto.getPareCode1()%>"><%=curriCategoryTotalDto.getCateName()%></option>
<%
	}
%>
													</select>
													&nbsp;
													<select name="pPareCode2" size="1"  onChange="selectlesson(document.Input.pPareCode2.options[document.Input.pPareCode2.selectedIndex].value);">
														<option value="">--- 중분류 ---</option>

													</select>
													&nbsp;&nbsp;
													<!-- pCateCode -->
													<select name="pCateCode" size="1">
														<option value="">--- 소분류 ---</option>
													</select>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정속성</td>
												<td class="s_tab_view02"><%=model.get("codeSelect")%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">학점</td>
												<td class="s_tab_view02"><input type="text" name="pCredit" size=3 maxlength=1 dataType="number" dispName="학점" notNull value="0"> 학점</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">학습시간</td>
												<td class="s_tab_view02"><input type="text" name="pLearningTime" size=3 maxlength=3 dataType="number" dispName="학습시간" value=""> 시간</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정목표</td>
												<td class="s_tab_view03"><textarea name="pCurriGoal" rows=3 cols=80 maxlength=2000 lenCheck="2000" dispName="과정목표"></textarea></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정정보</td>
												<td class="s_tab_view03"><textarea name="pCurriInfo" rows=3 cols=80 maxlength=2000 lenCheck="2000" dispName="과정정보"></textarea></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">학습방법</td>
												<td class="s_tab_view03"><textarea name="pCurriEnv" rows=3 cols=80 maxlength=2000 lenCheck="2000" dispName="학습방법"></textarea></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">수료조건</td>
												<td class="s_tab_view03"><textarea name="pAssessment" rows=3 cols=80 maxlength=2000 lenCheck="2000" dispName="수료조건"></textarea></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">학습목차</td>
												<td class="s_tab_view03"><textarea name="pCurriContents" rows=3 cols=80 dispName="학습목차"></textarea>
<%
	EditorParam editerParam = new EditorParam();
	editerParam.setShowFlag("true");
	editerParam.setWidth(500);
	editerParam.setHeight(200);
	editerParam.setToolBarHidden("attachFile");
	out.print(CommonUtil.getEditorScript(editerParam));
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>

											<tr>
												<td class="s_tab_view01" width="120">사전지식</td>
												<td class="s_tab_view02"><input type="text" name="pBeforeInfo" size="80" maxlength=200 lenCheck="200" dispName="사전지식" value=""></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정대상</td>
												<td class="s_tab_view02"><input type="text" name="pTarget" maxlength=200 lenCheck="200" dispName="과정대상" size="80" value=""></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정이미지1</td>
												<td class="s_tab_view03"><input type=file name="pTitleImg[1]" size="50"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정이미지2</td>
												<td class="s_tab_view03"><input type=file name="pTitleImg[2]" size="50"></td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %><script language=javascript>Button3("등록", "submitCheck()", "");</script><%	}	%>
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
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->

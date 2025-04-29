<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.curritop.dto.CurriTopDTO,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%>
<%@ page import = "com.edutrack.curritop.dao.CurriCategoryDAO, com.edutrack.curritop.dto.CurriCategoryTotalDTO" %>
<%@include file="../common/header.jsp" %>
<%
	String	curriSubCnt		=	(String)model.get("curriSubCnt");
	String	curriCourseCnt	=	(String)model.get("curriCourseCnt");
	String	pareCode1		=	(String)model.get("pPareCode1");
	String	pareCode2		=	(String)model.get("pPareCode2");
	String	cateCode		=	(String)model.get("pCateCode");
	String	pareCode1Name	=	(String)model.get("pPareCode1Name");
	String	pareCode2Name	=	(String)model.get("pPareCode2Name");
	String	cateCodeName	=	(String)model.get("pCateCodeName");

%>
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
	function curriDel(){
		if(<%=curriSubCnt%> > 0){
			alert('개설된 과정 정보가 있어서 삭제를 하실 수 없습니다.\n\n개설 과정 정보를 먼저 삭제해 주세요');
		}else if(<%=curriCourseCnt%> > 0){
			alert('연결된 과목 정보가 있어서 삭제를 하실 수 없습니다.\n\n연결 과목 정보를 먼저 삭제해 주세요');
		}else {
		     if(!confirm("삭제후 데이타 복구가 불가능합니다. \n\n정말로 과정을 삭제하시겠습니까?")) return;
		     
			document.location='<%=CONTEXTPATH%>/CurriTop.cmd?cmd=curriTopDelete&pMode=MyPage&pProperty1=<%=model.get("pProperty1")%>&pCurriCode=<%=model.get("curriCode")%>'
		}
	}

	function selectarticle(selected) {
		var frm	=	document.Input;
<%
		CurriCategoryTotalDTO selectDto1 = null;
		ArrayList pCate1 = (ArrayList)model.get("pCate1");

		for (int i=0; i<pCate1.size(); i++) {
			selectDto1 = (CurriCategoryTotalDTO)pCate1.get(i);
			if(selectDto1.getCateStatus().equals("0")){
%>
			if (selected == '') {
				initSel();
				initSel2();
				frm.pare_code2.options[0] = new Option ('--- 중분류 ---');
				frm.pare_code2.options[0].value = '';
				frm.cate_code.options[0] = new Option ('--- 소분류 ---');
				frm.cate_code.options[0].value = '';

			}
			else if (selected == '<%=selectDto1.getPareCode1()%>') {
				frm.cate_code.disabled = false;
				frm.cate_code.style.visibility="visible";
				initSel2();
<%
					CurriCategoryDAO curriCategoryDao = new CurriCategoryDAO();
					ArrayList pCate2 = null;
					pCate2 = curriCategoryDao.getCategory2(SYSTEMCODE, "3", selectDto1.getPareCode1());
					CurriCategoryTotalDTO select2Dto = null;

					for(int j=0; j<pCate2.size(); j++){
						select2Dto = (CurriCategoryTotalDTO)pCate2.get(j);
%>
						frm.pare_code2.options[<%=j%>] = new Option ('<%=select2Dto.getCateName()%>');
						frm.pare_code2.options[<%=j%>].value = '<%=select2Dto.getPareCode2()%>';
						frm.pPareCode2.value = '<%=select2Dto.getPareCode2()%>';
<%
					}
%>
					selectlesson(frm.pare_code2.options[frm.pare_code2.selectedIndex].value);
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
						frm.pare_code2.options[<%=j%>] = new Option ('<%=select2Dto.getCateName()%>');
						frm.pare_code2.options[<%=j%>].value = '<%=select2Dto.getPareCode2()%>';
						frm.pPareCode2.value = '<%=select2Dto.getPareCode2()%>';
<%
					}
%>
					frm.cate_code.disabled = false;
					initSel();

					frm.cate_code.style.visibility="hidden";
					frm.cate_code.options[0] = new Option ('오프');
					frm.cate_code.options[0].value = 'off';
					frm.pCateCode.value = 'off';

				}	// if

<%
			}	// else if
		}
%>
		selectinputcode();
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
				frm.cate_code.disabled = false;
				initSel();
<%
				CurriCategoryDAO curriCategoryDao = new CurriCategoryDAO();
				ArrayList pCate3 = null;
				pCate3 = curriCategoryDao.getCategory2(SYSTEMCODE, "4", selectDto3.getPareCode2());
				CurriCategoryTotalDTO selectDto5 = null;

				for(int l=0; l<pCate3.size(); l++){
					selectDto5 = (CurriCategoryTotalDTO)pCate3.get(l);
%>
					frm.cate_code.options[<%=l%>] = new Option ('<%=selectDto5.getCateName()%>');
					frm.cate_code.options[<%=l%>].value = '<%=selectDto5.getCateCode()%>';
					frm.pCateCode.value = '<%=selectDto5.getCateCode()%>';
<%
				}
%>
			}
<%
		}
%>
		selectinputcode();
	}

	function selectinputcode (selected) {
		var frm	=	document.Input;
		frm.pPareCode1.value	=	frm.pare_code1.value;
		frm.pPareCode2.value	=	frm.pare_code2.value;
		frm.pCateCode.value		=	frm.cate_code.value;
	}

	function initSel() {
		var frm	=	document.Input;
		with( frm ){
			frm.pCateCode.value = "";
			frm.cate_code.value = ""; //셀렉트박스 초기화
			frm.cate_code.length = 0; //셀렉트박스 초기화
		}
	}
	function initSel2() {
		var frm	=	document.Input;
		with( frm ){
			frm.pPareCode2.value = "";
			frm.pare_code2.value = "";
			frm.pare_code2.length = 0; //셀렉트박스 초기화
		}
	}

	function chkeditcode() {
		var frm = document.Input;
		if (frm.pChkEditCode.checked) {
			frm.pare_code1.disabled=false;
			frm.pare_code2.disabled=false;
			frm.cate_code.disabled=false;
		}
		else {
			frm.pare_code1.disabled=true;
			frm.pare_code2.disabled=true;
			frm.cate_code.disabled=true;
		}
	}

	function goList() {
		document.location = "<%=CONTEXTPATH%>/CurriTop.cmd?cmd=curriTopList&pMode=MyPage&pProperty1=<%=model.get("pProperty1")%>&pMode=<%=PMODE %>";
	}
</script>


										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start   onSubmit="return submitCheck()"  -->
<form name="Input" method="post" action="<%=CONTEXTPATH%>/CurriTop.cmd?cmd=curriTopRegist&pMode=MyPage&pRegMode=Edit" enctype="multipart/form-data">
<input type="hidden" name="pProperty1" value="<%=(String)model.get("pProperty1")%>">
<input type="hidden" name="pPareCode1" value="<%=pareCode1%>">
<input type="hidden" name="pPareCode2" value="<%=pareCode2%>">
<input type="hidden" name="pCateCode" value="<%=cateCode%>">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정코드</td>
												<td class="s_tab_view02"><input type="hidden" name="pCurriCode" value="<%=model.get("curriCode")%>"><%=model.get("curriCode")%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정명</td>
												<td class="s_tab_view02"><input type="text" name="pCurriName" maxlength=40 dispName="과정명" notNull size="50" value="<%=model.get("curriName")%>"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정분류</td>
												<td class="s_tab_view02">
<b><%=pareCode1Name%> &gt; <%=pareCode2Name%> &gt; <%=cateCodeName%></b>
(<input type="checkbox" name="pChkEditCode" value="T" class="solid0" onClick="javascript:chkeditcode();"> 수정) <br>
													<select name="pare_code1" size="1" onChange="selectarticle(document.Input.pare_code1.options[document.Input.pare_code1.selectedIndex].value);" disabled>
														<option value="">--- 대분류 ---</option>
<%
	CurriCategoryTotalDTO curriCategoryTotalDto = null;
	ArrayList pCate = (ArrayList)model.get("pCate1");
	for (int i=0; i<pCate1.size(); i++) {
		selectDto1 = (CurriCategoryTotalDTO)pCate1.get(i);

%>
														<option value="<%=selectDto1.getPareCode1()%>"><%=selectDto1.getCateName()%></option>
<%
	}
%>
													</select>
													&nbsp;
													<select name="pare_code2" size="1" onChange="selectlesson(document.Input.pare_code2.options[document.Input.pare_code2.selectedIndex].value);" disabled>
														<option value="">--- 중분류 ---</option>

													</select>
													&nbsp;&nbsp;

													<!-- pCateCode -->
													<select name="cate_code" size="1" onChange="selectinputcode();" disabled>
														<option value="OFF">--- 소분류 ---</option>
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
												<td class="s_tab_view02"><input type="text" name="pCredit" size=3 maxlength=1 dataType="number" dispName="학점" notNull value="<%=model.get("credit")%>"> 학점</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">학습시간</td>
												<td class="s_tab_view02"><input type="text" name="pLearningTime" size=3 maxlength=3 dataType="number"  dispName="학습시간" value="<%=model.get("learningTime")%>"> 시간</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정목표</td>
												<td class="s_tab_view03"><textarea name="pCurriGoal" rows=3 cols=80 maxlength=2000 lenCheck="2000" dispName="과정목표"><%=model.get("curriGoal")%></textarea></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정정보</td>
												<td class="s_tab_view03"><textarea name="pCurriInfo" rows=3 cols=80 maxlength=2000 lenCheck="2000" dispName="과정정보"><%=model.get("curriInfo")%></textarea></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">학습방법</td>
												<td class="s_tab_view03"><textarea name="pCurriEnv" rows=3 cols=80 maxlength=2000 lenCheck="2000" dispName="학습방법"><%=model.get("curriEnv")%></textarea></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">수료조건</td>
												<td class="s_tab_view03"><textarea name="pAssessment" rows=3 cols=80 maxlength=2000 lenCheck="2000" dispName="수료조건"><%=model.get("assessment")%></textarea></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">학습목차</td>
												<td class="s_tab_view03"><textarea name="pCurriContents" rows=3 cols=80 dispName="학습목차"><%=model.get("curriContents")%></textarea>
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
												<td class="s_tab_view02"><input type="text" name="pBeforeInfo" size="80" maxlength=200 lenCheck="200" dispName="사전지식" value="<%=model.get("beforeInfo")%>"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정대상</td>
												<td class="s_tab_view02"><input type="text" name="pTarget" maxlength=200 lenCheck="200" dispName="과정대상" size="80" value="<%=model.get("curriTarget")%>"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정이미지1</td>
												<td class="s_tab_view03"><%=model.get("curriOldImg1")%><br>
													<input type=file name="pTitleImg[1]" size="50">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120">과정이미지2</td>
												<td class="s_tab_view03"><%=model.get("curriOldImg2")%><br>
													<input type=file name="pTitleImg[2]" size="50">
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<% if(CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %><script language=javascript>Button3("수정", "submitCheck()", "");</script><%	}	%>
<% if(CommonUtil.getAuthorCheck(request,  "D"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button3("삭제", "curriDel()", "");</script><%	}	%>
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


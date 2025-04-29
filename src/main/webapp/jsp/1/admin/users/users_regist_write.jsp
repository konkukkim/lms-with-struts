<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../../common/header.jsp" %>
<%@ page import ="com.edutrack.common.dto.CodeParam" %>
<%@ page import ="com.edutrack.user.dto.ProfInfoDTO"%>
<%@ page import ="com.edutrack.user.dto.UsersDTO"   %>
<%@ page import ="com.edutrack.common.dto.EditorParam"%>

<%

	UsersDTO userInfo   = (UsersDTO)model.get("userInfo");

	//-- 단체관리자 여부(단체관리자:true)...단체관리자는 소속변경을 할수 없다.
	String   bGroupMng	= (String) model.get("bGroupMng");
	String   pGUBUN		= (String)model.get("pGUBUN");
	boolean bEdit		= ("edit").equals(pGUBUN);
	String aJuminNo []	= userInfo.getJuminNo().split("-") ;

	if (aJuminNo.length < 2 )
		aJuminNo = new String[2] ;
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/UsersWork.js"></script>
<Script Language="javascript">

	function SubmitCheck()
	{
		var f = document.Regist;

        if(f.pDeptSocode.value==""){
		    alert("소속소코드를 선택하세요.");
		    f.pDeptSocode.focus();
		    return;
		}

		if(!validate(f)) return;

		//if(!dataCheck(f.pUserId.value)){
		//    alert("사용자 아이디에 특수문자를 사용할 수 없습니다.");
		//    f.pUserId.focus();
		//    return;
		//}

		//if(!dataCheck(f.pUserPass1.value)){
		//    alert("패스워드에 특수문자를 사용할 수 없습니다.");
		//    f.pUserPass1.focus();
		//    return;
		//}

		if(f.pUserPass1.value != f.pUserPass2.value){
		    alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
		    f.pUserPass1.focus();
		    return;
		}


		if(f.userType.value != "P"){
			f.encoding = "application/x-www-form-urlencoded";
		}else{

    		/*  WEAS 삽입 스크립트3(시작) */
    		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
    		/* WEAS 삽입 스크립트3(끝) */

			f.encoding ="multipart/form-data";
			f.action="<%=CONTEXTPATH%>/UserAdmin.cmd?cmd=userMultiRegist";
		}
		
<%
if( !bEdit )
{
%>
/*  2021-12-02 주석처리 (BEGIN)
 *    if(!jsCheckJumin(f.pResidentId1.value + f.pResidentId2.value) ){
 *
 *	    alert("주민번호가 올바르지 않습니다.\n\n다시 입력하십시오.");
 *
 *	    f.pResidentId1.focus();
 *
 *	    return;
 *
 *	}else{
 */
		    UsersWork.userJuminNoCheckAjax("<%=SYSTEMCODE %>", f.pResidentId1.value, f.pResidentId2.value, callBackAfterJuminCheck);

/*		} 2021-12-02 주석처리 (END) */

<%
} // end if
else
{
%>
        f.submit();
<%
} // end if
%>

    }


<%
if( !bEdit )
{
%>
	function callBackAfterJuminCheck(val){
        var f = document.Regist;
        
        

//	    if(val=="1"){
//
//	        alert("주민등록번호가 이미 사용중 입니다.");
//
//	        f.pResidentId1.focus();
//
//	        f.pResidentId1.select();
//
//	    }
//
//	    else f.submit();

	    f.submit();


	}
<%
} // end if
%>



    /** @desc  단체관리자 요청 레이어의 display 여부를 관리한다.
     ** @function  doLayerVisible()
     ** @para      val ==> display 여부 값
	function doLayerVisible(val, val2){

	    document.all.sosokLayer.style.display = val ;
	    document.all.pMemberGb.value = val2 ;
	}
     */

    /** 소속코드가 변경될 경우..
     */
    function Change_Code(val){
      var f = document.Regist;

      if(<%= bGroupMng %>==true ){
        alert("【 <%=userInfo.getUserId()%> 】 는 단체관리자 입니다. (단체관리자는 소속을 변경할수 없습니다.)\n\n"+
              "【 나의 eda>시스템관리>소속코드관리 】에서  \n\n"+
              " 단체관리자 변경을 하신후 소속을 변경하십시오.");

        f.pDeptDaecode.value = "<%=userInfo.getDeptDaecode() %>";
        f.pDeptSocode .value = "<%=userInfo.getDeptSocode() %>" ;

        <%--  현유저의 소속코드가 승인받지 않았을 경우  빈칸이 보여지는 상태 방지하고 첫로우가 보여지도록... --%>
        if(f.pDeptSocode.selectedIndex==-1) f.pDeptSocode.selectedIndex ="0";

        return;
      }
      
      	if(val=="1"){
	      	var pDeptDaecode =  DWRUtil.getValue("pDeptDaecode");
			deptSoSelectList(pDeptDaecode);
		}
      	if(val=="2"){
	      	var pDeptSocode =  DWRUtil.getValue("pDeptSocode");
		} 		
      //f.encoding = "application/x-www-form-urlencoded";
      //f.action="<%=CONTEXTPATH%>/UserAdmin.cmd?cmd=userWrite";
      //f.submit();
    }

	// 소속분류 셀렉트박스 호출
	function deptSoSelectList(pDeptDaecode){
		UsersWork.deptSoSelectList(pDeptDaecode, deptSoSelectListCallback);
	}
	
	// 소속분류 셀렉트박스 표시
	function deptSoSelectListCallback(data){
		DWRUtil.removeAllOptions("pDeptSocode");
		var defaultSelect = {"":"--소속분류--"}; 
		
		DWRUtil.addOptions("pDeptSocode", defaultSelect);
		DWRUtil.addOptions("pDeptSocode", data);

		DWRUtil.setValue("pDeptSocode","<%=userInfo.getDeptSocode() %>");
	}

	function UserIdCheck(gubun)
	{
	    var newId = document.Regist.pUserId.value;
		var	Page="<%=CONTEXTPATH%>/User.cmd?cmd=userIdCheck&pNEW_ID="+newId+"&pGUBUN="+gubun;
		ShowInfo	=	window.open(Page,"useridcheck", "left=162,top=100,toolbar=0,directories=0,status=0,menubar=0,width=400,height=340,scrollbars=no");
	}

	function ZipCode(form, zip, addr) {
		Page = "<%=CONTEXTPATH%>/Common.cmd?cmd=searchPost&pForm="+form+"&pZip="+zip+"&pAddr="+addr;
		ShowInfo = window.open(Page,"info4", "left=162,top=100,toolbar=0,directories=0,status=0,menubar=0,width=420,height=360,scrollbars=yes");
	}

	function goList(usertype){
	    var loc="<%=CONTEXTPATH%>/UserAdmin.cmd?cmd=userPagingList&pMode=<%=PMODE %>&userType="+usertype;
		document.location = loc;
	}
	

</Script>
<%
	String type = null;
%>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name=Regist method="post" action="<%=CONTEXTPATH%>/UserAdmin.cmd?cmd=userRegist&pMode=<%=PMODE %>" enctype="multipart/form-data">
<input type=hidden name="pGUBUN"        value="<%=pGUBUN%>">
<input type=hidden name="userType"      value="<%=userInfo.getUserType()%>">
<input type=hidden name="firstCheck"    value="1">
											<tr>
												<td height="25" valign="top" colspan="4" align="right"><b><font color="red">*</font></b> 표시가 있는 항목은 반드시 입력해 주세요.</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 소속구분</td>
												<td class="s_tab_view02" width="200">
<%if(userInfo.getUserType().equals("S")) {%>
<%	//-- 소속기관명, 코드를 가져온다
	CodeParam param2 = new CodeParam();
	param2.setSystemcode(SYSTEMCODE);
	param2.setType("select");
	param2.setName("pDeptDaecode");
	param2.setSelected( userInfo.getDeptDaecode() );
	param2.setEvent( "javascrpt:Change_Code('1')" );
	out.print(CommonUtil.getDeptDaeCode(param2 ));
%>
<%}else{out.println("일반");
	out.println("<input type=hidden name='pDeptDaecode' value='100'>");
}%>
												</td>
												<td class="s_tab_view01" width="90"><font color="red">*</font> 소속분류</td>
												<td class="s_tab_view02"">
<%if(userInfo.getUserType().equals("S")) {%>

<!--  TODO -->														<select id="pDeptSocode" name="pDeptSocode" onChange="javascrpt:Change_Code('2')"></select>

<%--	//-- 소속기관명, 코드를 가져온다
	CodeParam param3 = new CodeParam();
	param3.setSystemcode(SYSTEMCODE);
	param3.setType("select");
	param3.setName("pDeptSocode");
	param3.setSelected( userInfo.getDeptSocode()  );
	param3.setEvent( "javascrpt:Change_Code()" );
	param3.setFirst("-- 소속코드 --");
	out.print(CommonUtil.getDeptSoCode(param3,  userInfo.getDeptDaecode()));
--%>

<%}else{//out.println("일반");

//	out.println("<input type=hidden name='pDeptSocode' value='100'>");
	out.println("<select id='pDeptSocode' name='pDeptSocode' value='100'>");

}%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 아이디</td>
												<td class="s_tab_view02" colspan="3"">
<%	if (!bEdit) {	%>
	<input type="text" name="pUserId" size="15" dispName="사용자ID" notNull lenCheck="15"  value="<%=userInfo.getUserId()%>" readOnly>
	<a href="javascript:UserIdCheck('1')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_idcheck.gif" align="absmiddle"></a>
	&nbsp;&nbsp;* ID 중복확인을 하신 후 입력해 주세요.
<%	} else {	%>
	<input type="hidden" name="pUserId" value="<%=userInfo.getUserId()%>"><B><%=userInfo.getUserId()%>
	<input type="hidden" name="userId" value="<%=userInfo.getUserId()%>">
													<B>
<%	} 	%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 비밀번호</td>
												<td class="s_tab_view02" width="200"><input type="password" name="pUserPass1" size="20" dispName="비밀번호" notNull lenCheck=15 value="<%=userInfo.getPasswd()%>"></td>
												<td class="s_tab_view01"><font color="red">*</font> 비밀번호확인</td>
												<td class="s_tab_view02""><input type="password" name="pUserPass2" size="20" dispName="비밀번호확인" notNull lenCheck="15" value="<%=( USERTYPE.equals("M") ? userInfo.getPasswd() : "" )%>"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 이름</td>
												<td class="s_tab_view02" colspan="3"">
<%	if (!bEdit) {	%>
   													<input type="text" name="pUserName" size="15" dispName="이 름" notNull lenCheck="15" value="">
<%	} else {	%>
   			        								<%=userInfo.getUserName() %>
   			        								<input type="hidden" name="pUserName" value="<%=userInfo.getUserName() %>">
<%	}	%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>

												<td class="s_tab_view01" width="120"><font color="red">*</font> 주민번호앞6자리</td>

												<td class="s_tab_view02" colspan="3"">
<%	if (!bEdit) {	%>
													<input type="text" name="pResidentId1" size="10" maxlength="6" dispName="주민등록번호 앞자리" notNull dataType="number" lenMCheck="6" lenCheck="6" value="">
<!--comment 2021-12-02 	- <input type="text" name="pResidentId2" size="10" maxlength="7" dispName="주민등록번호 뒷자리" notNull dataType="number" lenMCheck="7" lenCheck="7"  value=""> -->
													<input type="hidden" name="pResidentId2" value="ABCDEFG"> <!-- NB. meaningless value 2021-12-02 -->
<%	} else {	%>
													<%=StringUtil.nvl(aJuminNo[0]) %> - <%=StringUtil.nvl(aJuminNo[1]) %>
													<input type="hidden" name="pResidentId1" value="<%=StringUtil.nvl(aJuminNo[0]) %>">
													<input type="hidden" name="pResidentId2" value="<%=StringUtil.nvl(aJuminNo[1]) %>">
<%	}	%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120" rowspan="3"><font color="red">*</font> 주소</td>
												<td class="s_tab_view02" colspan="3"">
													<input type="text" name="pZipCode" size="20" maxlength="7" dispName="우편번호" value="<%=userInfo.getPostCode()%>">
													<A href="javascript:ZipCode('Regist', 'pZipCode', 'pAddress' )"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_post.gif" align="absmiddle" border="0"></a>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view02" colspan="3"">
													<input type="text" name="pAddress" size="50" dispName="주소"  value="<%=userInfo.getAddress()%>">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 전화번호</td>
												<td class="s_tab_view02">
													<input type="text" name="pPhoneHome" size="20" maxLength="20" dispName="전화번호" value="<%=userInfo.getPhoneHome() %>">
												</td>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 휴대전화</td>
												<td class="s_tab_view02">
													<input type="text" name="pPhoneHand" size="20" maxLength="20" dispName="휴대전화" value="<%=userInfo.getPhoneMobile() %>">
												</td>
											</tr>
											
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>

												<td class="s_tab_view01" width="120"> 회사명</td>

												<td class="s_tab_view02">
<input type="text" name="pCompName" size="20" maxLength=100 dispName="회사명" value="<%=userInfo.getCompName()%>">
												</td>

												<td class="s_tab_view01" width="120"> 회사 전화번호</td>

												<td class="s_tab_view02">
<input type="text" name="pCompPhone" size="20" maxLength=20 dispName="회사 전화번호" value="<%=userInfo.getCompPhone()%>">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120" rowspan="3"> 회사 주소</td>
												<td class="s_tab_view02" colspan="3">
<input type="text" name="pCompPostCode" size="20" maxlength="7" value="<%=userInfo.getCompPostCode()%>">
<a href="javascript:ZipCode('Regist', 'pCompPostCode', 'pCompAddress' )"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_post.gif" align="absmiddle"></a>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="3"></td>
											</tr>
											<tr>
												<td class="s_tab_view02" colspan="3">
<input type="text" name="pCompAddress"  size="50" value="<%=userInfo.getCompAddress()%>">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 이메일</td>
												<td class="s_tab_view02" colspan="3"">
<input type="text" name="pEMail" size="30" dispName="이메일 주소" dataType="text" value="<%=userInfo.getEmail()%>">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01"><font color="red">*</font> 사용여부</td>
												<td class="s_tab_view02" colspan="3">
													<input class=no type=radio value="Y" name=pUseYn <%=("Y").equals(userInfo.getUseStatus())? "checked" :"" %>><font class="s_text01">사용</font>&nbsp;&nbsp;&nbsp;
													<input class=no type=radio value="N" name=pUseYn <%=!("Y").equals(userInfo.getUseStatus())? "checked" :"" %>><font class="s_text02">사용안함</font>
												</td>
											</tr>
<%	//-- 교수자관리 일경우
	if (userInfo.getUserType().equals("P")) {
		ProfInfoDTO profInfo = (ProfInfoDTO)model.get("profInfo");
		String imgPath = "";

		if (profInfo.getUserPhoto().equals(""))
			imgPath = "sys_img/"+SYSTEMCODE+"/picture/common/none.gif";
		else
			imgPath = profInfo.getPhotoPath()+"/" + profInfo.getUserPhoto();
%>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">사진등록</td>
												<td class="s_tab_view03" colspan="3">
													<table height=140 width=100% align=left >
														<tr>
															<td valign="top">
																<input name="pUserPicture" type="file" size="30" dispName="사진" lenCheck="200">
																<input type="hidden" name="pOldUserPicture" value="<%=profInfo.getOldUserPhoto()%>">
															<br><font class="s_text03">* 권장사이즈- 가로:121픽셀, 세로:140픽셀</font>
															</td>
															<td height="140" width="105" align="left" background="../img/1/bbs/photo.gif" style="padding-right: 8px; padding-left: 8px; padding-bottom: 8px; padding-top: 8px">
															
<%
		if (!profInfo.getUserPhoto().equals("")) {
%>
																<img src="<%=CONTEXTPATH%>/data/<%=imgPath%>" width="105" height="120">
<%		}	%>
															</td>
															<td width="50"></td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01"">경력</td>
												<td class="s_tab_view03" colspan="3">
													<textarea rows=7 wrap="auto" cols="80" name="pProfCareer" dispName="경력"><%=profInfo.getCareer()%></textarea>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">학력</td>
												<td class="s_tab_view03" colspan="3">
													<textarea name="pProfSchool" rows=7 wrap="auto" cols="80" dispName="학력"><%=profInfo.getMajor()%></textarea>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">저서</td>
												<td class="s_tab_view03" colspan="3">
													<textarea name="pBooks" rows=7 wrap="auto" cols="80" dispName="저서"><%=profInfo.getBooks()%></textarea>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">자기소개</td>
												<td class="s_tab_view03" colspan="3">
													<textarea name="pIntro" rows=15 wrap="auto" cols="80" dispName="자기 소개"><%=profInfo.getIntro()%></textarea>
<%
		EditorParam editerParam = new EditorParam();
		editerParam.setShowFlag("true");
		editerParam.setWidth(550);
		editerParam.setHeight(300);
		editerParam.setReadWidth("380");
		editerParam.setToolBarHidden("attachFile");
		out.print(CommonUtil.getEditorScript(editerParam));
%>

												</td>
											</tr>
<%	}	%>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">

CommonUtil.getAuthorCheck(request,  "C")<%=CommonUtil.getAuthorCheck(request,  "C")%>
CommonUtil.getAuthorCheck(request,  "U")<%=CommonUtil.getAuthorCheck(request,  "U")%>

												    <% if(CommonUtil.getAuthorCheck(request,  "C") && (!bEdit) )/* 권한체크 */  { %>
												    <script language=javascript>Button3("등록", "SubmitCheck('<%=pGUBUN%>')", "");</script>&nbsp;
												    <%	}	%>
												    <% if(CommonUtil.getAuthorCheck(request,  "U") && (bEdit) )/* 권한체크 */  { %>
												    <script language=javascript>Button3("수정", "SubmitCheck('<%=pGUBUN%>')", "");</script>&nbsp;
												    <%	}	%>
												    <script language=javascript>Button3("목록", "goList('<%=userInfo.getUserType()%>')", "");</script>
												</td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../../common/footer.jsp" %>

<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->


<Script>
deptSoSelectList('<%=userInfo.getDeptDaecode() %>');
</Script>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr"    %>
<% //@ page import ="com.edutrack.sample.dto.UserInfo" %>
<%@ page import ="com.edutrack.user.dto.UsersDTO"   %>
<%@ page import ="com.edutrack.dept.dto.DeptDaeCodeDTO" %>
<%@ page import ="com.edutrack.dept.dto.DeptSoCodeDTO" %>
<%@ page import ="com.edutrack.common.dto.CodeParam"%>
<%@include file="../common/header.jsp" %>
<%
	UsersDTO       userInfo    = (UsersDTO)model.get("userInfo");
	DeptSoCodeDTO  deptSoCode  = (DeptSoCodeDTO)model.get("deptSoCode");
	DeptDaeCodeDTO deptDaeCode = (DeptDaeCodeDTO)model.get("deptDaeCode");
	String   pGUBUN   = (String)model.get("pGUBUN");
	String aJuminNo     [] = userInfo.getJuminNo()    .split("-") ;

	if( aJuminNo    .length < 2 ) aJuminNo     = new String[2] ;

	// 입력/수정 여부
	boolean bEdit     = ("edit").equals(pGUBUN) ;

	String	titleimg	=	"mem_centitle01.gif";
	if (bEdit) titleimg	=	"mem_centitle04.gif";

	String  bMemberGb = USERID.equals(deptSoCode.getUserId()) ? "2" : "1"  ;  // 1: 일반회원, 2:단체관리자

	if(USERID.equals("")) bMemberGb = "1"; //-- 최초 회원 가입시 일반회원으로 셋팅

	// 제목 이미지 변수 정의
	// 입력 수정을 같이 사용하기 때문
	String titImgPath          = ( !bEdit ? "member01.gif"    : "academy03.gif"    );
	String userId_img          = ( !bEdit ? "00/00_img08.gif" : "03/03_img17.gif"  );   // 아이디
	String passWd1_img         = ( !bEdit ? "00/00_img09.gif" : "03/03_img18.gif"  );   // 패스워드
	String passWd2_img         = ( !bEdit ? "00/00_img17.gif" : "03/03_img19.gif"  );   // 패스워드 확인
	String userNm_img          = ( !bEdit ? "00/00_img10.gif" : "03/03_img20.gif"  );   // 사용자이름
	String juminNo_img         = ( !bEdit ? "00/00_img11.gif" : "03/03_img21.gif"  );   // 주민번호
	String zipCode_img         = ( !bEdit ? "00/00_img12.gif" : "03/03_img22.gif"  );   // 우편번호
	String address_img         = ( !bEdit ? "00/00_img13.gif" : "03/03_img23.gif"  );   // 주소
	String phoneHome_img       = ( !bEdit ? "00/00_img14.gif" : "03/03_img24.gif"  );   // 전화번호
	String phoneMobile_img     = ( !bEdit ? "00/00_img15.gif" : "03/03_img25.gif"  );   // 핸드폰 번호
	String email_img           = ( !bEdit ? "00/00_img16.gif" : "03/03_img26.gif"  );   // 이메일
	String jobCode_img         = ( !bEdit ? "00/00_img18.gif" : "03/03_img27.gif"  );   // 우편번호
	String memberGB_img        = ( !bEdit ? "00/00_img19.gif" : ""                 );   // 회원구분(입력시)
	String deptDaeCode_img     = ( !bEdit ? "00/00_img21.gif" : "03/03_img29.gif"  );   // 소속기관
	String deptSoname_img      = ( !bEdit ? "00/00_img22.gif" : "03/03_img30.gif"  );   // 기관명
	String groupAddress_img    = ( !bEdit ? "00/00_img24.gif" : "03/03_img31.gif"  );   // 단체관리자 주소
	String groupPhone_img      = ( !bEdit ? "00/00_img25.gif" : "03/03_img32.gif"  );   // 단체관리자 전화번호
	String groupPosition_img   = ( !bEdit ? "00/00_img26.gif" : "03/03_img33.gif"  );   // 단체관리자 직책

%>

<Script Language="JavaScript">
<!--
	/* ADD 2013-11-01 (BEGIN) */
	function IsHangul(korname)
	{
	var temp="";
	var len;
	var found = false;
	//console.log("korname:" + korname);
	//console.log("korname.length:" + korname.length);
		for(k=0; k<korname.length; k++)
		{
			temp=korname.charAt(k);
			len = 0;
			len = escape(temp).length;
	//console.log("temp:" + temp);
	//console.log("len:" + len);
			if(len > 4)
			{
				found = true;;
				break;
			}
		}
	
		return (found);
	}

	/* ADD 2013-11-01 (END) */

	function SubmitCheck()
	{
		var f = document.Regist;

		/* ADD 2013-11-01 (BEGIN) */
		if(!IsHangul(f.pUserName.value)){
		    alert(" 한글이름이 아닙니다 ");
		    f.pUserName.focus();
		    f.pUserName.select();
		    return;
		}
		/* ADD 2013-11-01 (END) */

		if(!dataCheck(f.pUserId.value)){
		    alert("사용자 아이디에 특수문자를 사용할 수 없습니다.");
		    f.pUserId.focus();
		    f.pUserId.select();
		    return;
		}
		if(!dataCheck(f.pUserPass1.value)){
		    alert("패스워드에 특수문자를 사용할 수 없습니다.");
		    f.pUserPass1.focus();
		    f.pUserPass1.select();
		    return;
		}
		if(f.pUserPass1.value != f.pUserPass2.value){
		    alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
		    f.pUserPass2.focus();
		    f.pUserPass2.select();
		    return;
		}

		if(!validate(f)) return;

		//if(f.pJobCode.value == ''){
		//    alert("직업을 선택해 주세요");
		//    f.pJobCode.focus();
		//    return;
		//}




<%
if( !bEdit )
{
%>
/* 2014-12-01 주석처리 (BEGIN)
        if(!jsCheckJumin(f.pResidentId1.value + f.pResidentId2.value) ){
		    alert("주민번호가 올바르지 않습니다.\n\n다시 입력하십시오.");
		    f.pResidentId1.focus();
		    return;
		}
2014-12-01 주석처리 (END) */

<%
} // end if
%>

		f.submit();

    }


	function UserIdCheck(gubun)
	{
	    var newId = document.Regist.pUserId.value;
		var	Page="<%=CONTEXTPATH%>/User.cmd?cmd=userIdCheck&pNEW_ID="+newId+"&pGUBUN="+gubun;

		var winOption = "left="+windowLeftPosition(370)+",top="+windowTopPosition(300)+",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=yes,resizable=no,width=450,height=350";

		ShowInfo	=	window.open(Page,"useridcheck", winOption);
	}

	function ZipCode(form, zip, addr) {
		Page = "<%=CONTEXTPATH%>/Common.cmd?cmd=searchPost&pForm="+form+"&pZip="+zip+"&pAddr="+addr;

		var winOption = "left="+windowLeftPosition(420)+",top="+windowTopPosition(360)+",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=yes,resizable=no,width=450,height=350";

		ShowInfo = window.open(Page,"info4", winOption);
	}


    /** @desc  취소버튼 클릭시
     ** @function  goCancel()
     ** @para      gubun ==>
     */
	function goCancel(gubun){
	   // var loc="<%=CONTEXTPATH%>/Main.cmd?cmd=mainShow&pMode=Home";
		//if(gubun == "edit") loc="<%=CONTEXTPATH%>/Main.cmd?cmd=myPageShow&pMode=MyPage";
		//document.location = loc;
		history.go(-1);
	}

    /** @desc  단체관리자 요청 레이어의 display 여부를 관리한다.
     ** @function  doLayerVisible()
     ** @para      val ==> display 여부 값
     */
	function doLayerVisible(val, val2){

	    document.all.sosokLayer.style.display = val ;
	    document.all.pMemberGb.value = val2 ;
	}

    /** @desc  회원탈퇴
     ** @function  dropUser()
     */
	function dropUser(){
		if('<%=CHKDEPTMANAGER%>' != 'true')
		{
		    var str = "회원탈퇴시 본 사이트에서 제공하고 있는 다음의 서비스 사용이 금지됩니다. \n\n"
					+ " 1. 로그인 서비스 및 개인맞춤 서비스 사용 불가\n"
					+ " 2. 강의실 서비스 사용 불가 \n"
					+ " 3. EBook 온라인 서점 이용 불가\n"
					+ "   - 탈퇴전 자신의 PC에 내려받아야만 합니다.\n"
					+ "     온라인 서재의 이용은 사이트 회원정보와 일치할 경우만 제공되므로 사용하실 수 없습니다.\n\n"
					+ "정말로 회원탈퇴를 하시겠습니까?\n\n회원탈퇴를 하시면 같은 아이디로 회원가입을 할 수 없습니다.";

		    if(confirm(str) == false) return;

		    Regist.action = "<%=CONTEXTPATH%>/User.cmd?cmd=userDrop" ;
		    Regist.submit();
	    }else{
	    	alert("회원님은 단체관리자로 등록 되어 있습니다. \n\n단체관리자는 임의 탈퇴가 불가하며\n\n관리자에게 요청 하셔야 합니다.");
	    }

	}

	function delFile(fileNum){
      	document.location.href="/User.cmd?cmd=userFileDelete&pFileNo="+fileNum;
    }

     function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc = "/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }

//-->
</Script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name="Regist" method="post" action="<%=CONTEXTPATH%>/User.cmd?cmd=userRegist" enctype="multipart/form-data">
<input type="hidden" name="pGUBUN"       value="<%=pGUBUN %>">
<input type="hidden" name="userType"     value="<%=userInfo.getUserType()%>">
<input type="hidden" name="pDeptDaecode" value="<%=userInfo.getDeptDaecode()%>">
<input type="hidden" name="pDeptSocode"  value="<%=userInfo.getDeptSocode()%>">
<input type="hidden" name="pMemberGb"    value="<%=bMemberGb%>">

<input type="hidden" name="pOldrFileName[1]"    value="<%=StringUtil.nvl(userInfo.getRfileName())%>">
<input type="hidden" name="pOldsFileName[1]"    value="<%=StringUtil.nvl(userInfo.getSfileName())%>">
<input type="hidden" name="pOldFilePath[1]"     value="<%=StringUtil.nvl(userInfo.getFilePath())%>">
<input type="hidden" name="pOldFileSize[1]"     value="<%=StringUtil.nvl(userInfo.getFileSize())%>">

											<tr>
												<td height="25" valign="top" colspan="4" align="right"><b><font color="red">*</font></b> 표시가 있는 항목은 반드시 입력해 주세요.</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>

											<tr>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 아이디</td>
												<td class="s_tab_view02" colspan="3">
<%
	// 수정시
    if( userInfo.getUserId()!=null && !("").equals(userInfo.getUserId()) ) {
%>
                                					<input type="hidden" name="pUserId" value="<%=userInfo.getUserId()%>"><B><%=userInfo.getUserId()%><%=( ("2").equals(bMemberGb) ? "&nbsp;[단체관리자]" : "" ) %><B>
<%
	}
    // 입력시
    else {
%>
													<input type="text" name="pUserId" dispName="아이디" notNull lenMCheck="4" lenCheck="15" maxlength='15' value="">
													<a href="javascript:UserIdCheck('1')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_idcheck.gif" align="absmiddle"></a>
<%
	}
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 비밀번호</td>
												<td class="s_tab_view02" width="200"><input type="password" name="pUserPass1" size="15" dispName="비밀번호" notNull  lenMCheck="4" lenCheck="15"  maxlength='15' value="<%=userInfo.getPasswd()%>"></td>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 비밀번호확인</td>
												<td class="s_tab_view02""><input type="password" name="pUserPass2" size="15" dispName="비밀번호확인" notNull  value=""></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 이름</td>
												<td class="s_tab_view02" colspan="3">
<%
	// 입력시   이름을 입력하되
	// 수정시는 이름을 수정못하도록 한다
    if(!bEdit) {
%>
                            						<input type="text" name="pUserName" dispName="이름" notNull value="<%=userInfo.getUserName()%>">

<%
	} else {
%>
													<%=userInfo.getUserName()%>
													<input type="hidden" name="pUserName" value="<%=userInfo.getUserName()%>">
<%
	}
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 주민번호 앞자리 </td>
												<td class="s_tab_view02" colspan="3">
<%
	// 입력시   주민번호를 입력하되
	// 수정시는 주민번호를 수정못하도록 한다

	if(!bEdit) {
%>
													<input type="text" name="pResidentId1" maxLength=6 dispName="주민번호 앞자리" notNull  value="<%=(aJuminNo.length >0 ? StringUtil.nvl(aJuminNo[0]) : "" ) %>">
<!--comment 2015-02-03
													-
													<input type="text" name="pResidentId2" maxLength=7 dispName="주민번호 뒷자리" notNull  value="<%=(aJuminNo.length >1 ? StringUtil.nvl(aJuminNo[1]) : "" ) %>">
-->
													<input type="hidden" name="pResidentId2" value="ABCDEFG"> <!-- NB. meaningless value 2015-02-03 -->
<%
	} else {
%>
													<%=StringUtil.nvl(aJuminNo[0]) %> - <%=StringUtil.nvl(aJuminNo[1]) %>
													<input type="hidden" name="pResidentId1" value="<%=StringUtil.nvl(aJuminNo[0]) %>">
													<input type="hidden" name="pResidentId2" value="<%=StringUtil.nvl(aJuminNo[1]) %>">
<%
	}
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120" rowspan="3"><font color="red">*</font> 주소</td>
												<td class="s_tab_view02" colspan="3">
													<input type="text" name="pZipCode" size="20" maxlength="7" dispName="우편번호" notNull value="<%=userInfo.getPostCode()%>">
													<a href="javascript:ZipCode('Regist', 'pZipCode', 'pAddress' )"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_post.gif" align="absmiddle"></a>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="3"></td>
											</tr>
											<tr>
												<td class="s_tab_view02" colspan="3">
													<input type="text" name="pAddress"  size="50" dispName="주소" notNull value="<%=userInfo.getAddress()%>">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 전화번호</td>
												<td class="s_tab_view02">
													<input type="text" name="pPhoneHome" size="20" maxLength=20 dispName="전화번호" value="<%=userInfo.getPhoneHome() %>">
												</td>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 휴대전화</td>
												<td class="s_tab_view02">
													<input type="text" name="pPhoneHand" size="20" maxLength=20  dispName="휴대전화" value="<%=userInfo.getPhoneMobile() %>">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 회사명</td>
												<td class="s_tab_view02">
<input type="text" name="pCompName" size="20" maxLength=100 dispName="회사명" value="<%= deptSoCode.getDeptSoname() %>">
												</td>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 회사 전화번호</td>
												<td class="s_tab_view02">
<input type="text" name="pGroupPhone" size="20" maxLength=20 dispName="회사 전화번호" value="<%= deptSoCode.getPhone() %>">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120" rowspan="3">회사 주소</td>
												<td class="s_tab_view02" colspan="3">
													<input type="text" name="pCompPostCode" size="20" maxlength="7" value="<%=deptSoCode.getPostCode()%>">
													<a href="javascript:ZipCode('Regist', 'pCompPostCode', 'pCompAddress' )"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_post.gif" align="absmiddle"></a>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="3"></td>
											</tr>
											<tr>
												<td class="s_tab_view02" colspan="3">
													<input type="text" name="pCompAddress"  size="50" value="<%=deptSoCode.getAddress()%>">
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="120"><font color="red">*</font> 이메일</td>
												<td class="s_tab_view03" colspan="3">
													<input type="text" name="pEMail" size=30 dispName="이메일 주소" dataType="email" notNull value="<%=userInfo.getEmail()%>"><br>
													뉴스레터 수신동의? <input type="radio" name="pRecvMail" value="Y" class="no" checked <%if (userInfo.getRecvMail().equals("Y")) out.println("checked");%>>
													수신
													<input type="radio" name="pRecvMail" value="N" class="no" <%if (userInfo.getRecvMail().equals("N")) out.println("checked");%>>
													수신안함
												</td>
											</tr>
											
											

											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<%
	if(!bEdit) {
%>
<script language=javascript>Button3("원서제출", "SubmitCheck('<%=pGUBUN%>')", "");</script>
&nbsp;<script language=javascript>Button3("취소", "goCancel('<%=pGUBUN%>')", "");</script>
<%
	} else {
%>
<script language=javascript>Button3("수정", "SubmitCheck('<%=pGUBUN%>')", "");</script>
&nbsp;<script language=javascript>Button3("취소", "goCancel('<%=pGUBUN%>')", "");</script>
<%
	}
%>
												</td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						


<%@include file="../common/footer.jsp" %>

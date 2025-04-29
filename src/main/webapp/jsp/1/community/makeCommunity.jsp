<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.common.dto.CodeParam,com.edutrack.community.dto.CommInfoDTO"%>
<%@include file="../common/community_header.jsp" %>
<%
	CommInfoDTO commInfo= (CommInfoDTO)model.get("commInfo");
	String pGubun = (String)model.get("pGubun");
	String pCommId = (String)model.get("pCommId");
	String pWhere = (String)model.get("pWhere");

	String img_path = CONTEXTPATH + "/img/" + SYSTEMCODE;
	String titleStr = "동아리 만들기";
	if ( pGubun.equals("Edit"))
	   titleStr = "동아리 정보관리";
%>
<script language="javascript">
	//
	function onCheckFrom(){
	    var f = document.form;

	    if(!validate(f)) return;

        if(isEmpty(f.cate.value)){
			alert("동아리 분류를 선택 하지 않았습니다!\n\동아리 분류는 필수 입력 항목입니다.");
			f.cate.focus();
			return;
		}
<% if(commInfo.getMainImg().equals("")){ %>
		if(isEmpty(f.pFile.value)){
			var yes = confirm("메인 이미지를 첨부하지 않았습니다.\n\n메인이미지는 기본이미지로 적용하시겠습니까?");
			if(yes == false) return;
		}
<%}%>
		f.submit();
	}

	function goMain(){
	    var f = document.form;
	    var where = f.pWhere.value;
	    var returnUrl = "/Community.cmd?cmd=commInfoList";
	    if(where == "M") returnUrl = "/CommManage.cmd?cmd=commInfoPagingList";
	    else if(where == "C") returnUrl = "/Community.cmd?cmd=goCommunityMain";

		document.location = returnUrl;
	}

	function isEmpty(data)
	{
		for ( var i = 0 ; i < data.length ; i++ ) {
			if ( data.substring( i, i+1 ) != ' ' )
				return false;
		}
		return true;
	}

	function delFile(commId){
	    var f = document.form;
	    var where = f.pWhere.value;
		var st = confirm("이미지 파일을 삭제 하시겠습니까?");
		if (st == true) {
	       	document.location.href="/Community.cmd?cmd=commInfoFileDelete&pCommId="+commId+"&pWhere="+where;
		}
    }


    function Community_Close(){
        if(confirm('동아리가 폐쇄되면 동아리의 모든 정보가 삭제됩니다. \n\n동아리 폐쇄 신청을 하시겠습니까?')){
            hiddenFrame.document.location = "<%=CONTEXTPATH%>/Community.cmd?cmd=commCloseRegist&pCommId=<%=pCommId%>";
        }
    }

</script>
								<tr valign="top">
									<td colspan="2" height="10"></td>
								</tr>
								<tr valign="top">
									<!-- community title -->
									<td height="34" width="346" class="com_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/community/com_centitile.gif" width="14" height="17" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=titleStr%></b></font></td>
									<!-- // community title -->
									<!-- history -->
									<td class="com_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									</td>
								</tr>
								<tr valign="top">
									<td colspan="2" class="com_content_top">
									<!-- 내용 -->
									<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name="form" method="post" action="<%=CONTEXTPATH%>/Community.cmd?cmd=makeCommRegist" enctype="multipart/form-data">
<input type="hidden" name="pGubun" value="<%=pGubun%>">
<input type="hidden" name="pCommId" value="<%=pCommId%>">
<input type="hidden" name="pWhere" value="<%=pWhere%>">

											<tr class="com_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02" width="92">동아리명</td>
												<td class="s_tab_view02"><input name="commName" size="70" dispName="동아리명" notNull lenCheck=100 value="<%=commInfo.getCommName()%>" <%if(pGubun.equals("Edit")){%>readOnly<%}%>></td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02">분류</td>
												<td class="s_tab_view02">
<%
    CodeParam param1 = new CodeParam();
    param1.setSystemcode(SYSTEMCODE);
    param1.setType("select");
    param1.setName("cate");
    param1.setFirst("--분류선택--");
    param1.setSelected(commInfo.getCateCode());
    out.print(CommonUtil.getCommCategory(param1));
 %>
												</td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02">검색어</td>
												<td class="s_tab_view02"><input name="searchWord" id="searchWord" size="70" value="<%=commInfo.getKeyword()%>"></td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02">공개여부</td>
												<td class="s_tab_view02">
													<input type=radio name="openYn" value='Y' class="no" style="border:0" <% if(commInfo.getOpenYn().equals("Y") || commInfo.getOpenYn().equals("")){%>checked<%}%>> 공개
                                                    <input type=radio name="openYn" value='N' class="no" style="border:0" <% if(commInfo.getOpenYn().equals("N")){%>checked<%}%>> 비공개
												</td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02">회원가입방식</td>
												<td class="s_tab_view02">
													<input type=radio name=comfirm value='1' class="no" style="border:0" <% if(commInfo.getRegistType().equals("1") || commInfo.getRegistType().equals("")){%>checked<%}%>> 신청즉시 가입
                                                    <input type=radio name=comfirm value='2' class="no" style="border:0" <% if(commInfo.getRegistType().equals("2")){%>checked<%}%>> 시샵 확인 후 가입
												</td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02">이미지</td>
												<td class="s_tab_view02">
<%
	if(!commInfo.getMainImg().equals("")){
        String fileName="";
        String filePath="";
        String mainImg = commInfo.getMainImg();
        int lastIndex = mainImg.lastIndexOf("/");
        int strLen = mainImg.length();

        if(lastIndex != -1){
            filePath = mainImg.substring(0,(lastIndex+1));
            fileName = mainImg.substring(lastIndex+1);
        }
%>
															* 현재 이미지는 <a href="javascript:viewphoto('<%=CONTEXTPATH%>', '<%=filePath%><%=fileName%>' ,'Layer1')">&nbsp;<%=fileName%></a> 입니다. <a href="javascript:delFile('<%=commInfo.getCommId()%>')">[기존파일삭제]</a><br>
<%
	}
%>
                                                        	<input type="file" name="pFile" size="60">
                                                        	<br> &nbsp;* jpg, gif 이미지만 등록합니다.. 사이즈는 161x121 사이즈의 이미지만 올려주세요.<br>
												</td>
											</tr>
											<tr class="com_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="com_tab02">소개글</td>
												<td class="s_tab_view03"><textarea name="commSynopsis" rows=15 wrap="VIRTUAL" cols="90" dispName="동아리소개" notNull><%=commInfo.getCommSynopsis()%></textarea></td>
											</tr>
											<tr class="com_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<%
	if (USERTYPE.indexOf("M") > -1 && PMODE.equals("MyPage")) {
%>
<script language=javascript>Button3("동아리 폐쇄하기", "Community_Close()", "");</script>
<%
	}

	if (pGubun.equals("Edit")) {
%>
&nbsp;<script language=javascript>Button3("수정", "onCheckFrom()", "");</script>
<%
	} else {
%>
&nbsp;<script language=javascript>Button3("등록", "onCheckFrom()", "");</script>
<%
	}
%>
&nbsp;<script language=javascript>Button3("목록", "goMain()", "");</script>
												</td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->

<%@include file="../common/community_footer.jsp" %>
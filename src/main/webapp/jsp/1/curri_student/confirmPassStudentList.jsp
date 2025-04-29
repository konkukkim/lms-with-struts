<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.curristudent.dto.StudentDTO,com.edutrack.common.dto.CodeParam"%>
<%@include file="../common/course_header.jsp" %>
<%
	String pUserType = (String)model.get("userType");
%>
<!-- ajax 적용  start -->
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/ajaxCommon.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriStudentWork.js"></script>
<script language="javascript">

	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
		autoReload();
	}

	// 검색
	function goSearch(){

	   f.curPage.value = "1";

	   autoReload()

	}

	// 수강생리스트 받아오기
	function autoReload(){

       viewProgress('studentList','block','<%=CONTEXTPATH%>', '<%=SYSTEMCODE%>','Y');

	   var pSTarget = f.pSTarget[f.pSTarget.selectedIndex].value;
 	   var pSWord   = f.pSWord.value;
 	   var pCurPage = f.curPage.value;
	   
        CurriStudentWork.confirmPassStudentListAjax(pSTarget, pSWord, pCurPage, reloadStudentList);	   

	}

	// 수강생리스트 뿌려주기
	function reloadStudentList(data){
	    
	    var rowLength = 0;
	  	var paggingStr = data.pagging;
	  	var dataList = data.dataList;
		if(dataList != null) rowLength = dataList.length;
		     
	  	var studentListObj = $("studentList");
	  	var paggingObj = $("getPagging"); 
	  	var objStr = "<table width='670' align='center' >";
	  	
	  	// 페이징 처리..
	  	if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

	  	if(rowLength == 0){
		  	objStr += "<tr><td class='s_tab04_cen' colspan='11'>등록된 수강생 정보가 없습니다.</td></tr>";
	  	}else{
	  	
	  	    var totalCount = data.totalDataCount;
	  	    
		  	for(i=0;i<rowLength;i++){

                var studentDto = dataList[i];
                
		    	var userName    = studentDto.userName;
		    	var userId      = studentDto.userId;
		    	var email       = studentDto.email;
		    	var lastCon      = studentDto.lastCon;
		    	
				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
					+ "<td width='70' class='s_tab04_cen'>"+(totalCount--)+"</td>"
					+ "<td></td><td width='' class='s_tab04'>&nbsp;"+userName+"</td>"
					+ "<td></td><td width='140' class='s_tab04_cen'><a href=javascript:Show_UserInfo('"+userId+"');>"+userId+"</a></td>"
					+ "<td></td><td width='140' class='s_tab04_cen'>"+lastCon+"</td>"
					+ "<td></td><td width='74' class='s_tab04_cen'><input type='checkbox' class='solid0' name='pCHK' value="+userId+"^"+userName+"^"+email+"></td>"
					+ "</tr>";

				objStr += "<tr class='s_tab03'><td colspan='20'></td></tr>";

			}
		}
		objStr += "</table>";

		studentListObj.innerHTML = objStr;
		studentListObj.style.display = "block";
	 }

    
    function messageSend(str){
    	var f = document.f;
    	var	chk	=	false;
    	if(str == 'S'){
    		f.pMessageKb.value = "S";
    		var len = f.pCHK.length;
    		if(len > 1 ){
    			for(var i=0; i < len;i++){
    				if(f.pCHK[i].checked) {
    					chk = true;
    					break;
    				}
    			}
    			if(!chk){
    				alert("보낼사람을 선택하십시요!");
    				return;
    			}
    		}else{
    			if(!f.pCHK.checked) {
    				alert("보낼사람을 선택하십시요!");
    				return;
    			}
    		}
    	}else{
    		//setChkboxAll(f, "pCHK", true);
    		f.pMessageKb.value = "A";
    	}
    	var showInfo = window.open("", "popMessage","width=600,height=520,top=200,left=300,resizable=yes");
    	f.action="<%=CONTEXTPATH%>/PopMessage.cmd?cmd=messageSend";
    	f.target = "popMessage";
    	f.method = "post";
    	f.submit();
    }
    
    function mailSend(str){
    	var f = document.f;
    	var	chk	=	false;
    	if(str == 'S'){
    		f.pMessageKb.value = "S";
    		var len = f.pCHK.length;
    		if(len > 1 ){
    			for(var i=0; i < len;i++){
    				if(f.pCHK[i].checked) {
    					chk = true;
    					break;
    				}
    			}
    			if(!chk){
    				alert("보낼사람을 선택하십시요!");
    				return;
    			}
    		}else{
    			if(!f.pCHK.checked) {
    				alert("보낼사람을 선택하십시요!");
    				return;
    			}
    		}
    	}else{
    		//setChkboxAll(f, "pCHK", true);
    		f.pMessageKb.value = "A";
    	}
    	var showInfo = window.open("", "popMail","width=600,height=520,top=200,left=300,resizable=yes");
    	f.action="<%=CONTEXTPATH%>/Mail.cmd?cmd=mailWrite";
    	f.target = "popMail";
    	f.method = "post";
    	f.submit();
    }
    
    function Show_UserInfo(userid){
      var Page = "<%=CONTEXTPATH%>/User.cmd?cmd=userInfoShow&userId="+userid;
      ShowInfo	=	window.open(Page,"userinfoshow", "left=162,top=100,toolbar=0,directories=0,status=0,menubar=0,width=380,height=400,scrollbars=yes");
    }
</script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"수강생조회")%></b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									</td>
									<!-- // history -->
								</tr>
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name="f" method="post" action="javascript:goSearch()" >
<input type="hidden" name="curPage" value="">
<input type="hidden" name="checkYn" value="N">
<input type="hidden" name="pMessageKb" value="S">
<input type="hidden" name="pMessageWhere" value="Lecture">
<input type="hidden" name="pMessageWhereSub" value="Student">
<input type="hidden" name="pResultCode1" value="">
<input type="hidden" name="pResultCode2" value="">
<input type="hidden" name="pResultCode3" value="">
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="242">이름</td>
												<td class="s_tablien"></td>
												<td width="140">학습자ID</td>
												<td class="s_tablien"></td>
												<td width="140">접속상태</td>
												<td class="s_tablien"></td>
												<td width="74"><a href="javascript:setAllToken(f.pCHK)">선택</a></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
													<!-- 리스트 -->
														<div id="studentList" style="width:100%;display:no"></div>
													<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<script language=javascript>Button3("개별쪽지", "messageSend('S')", "");</script>
<%
	if(!pUserType.equals("S")){
%>
&nbsp;<script language=javascript>Button3("전체쪽지", "messageSend('A')", "");</script>
&nbsp;<script language=javascript>Button3("개별메일", "mailSend('S')", "");</script>
&nbsp;<script language=javascript>Button3("전체메일", "mailSend('A')", "");</script>
<%
	}
%>
												</td>
											</tr>
											<!-- 페이지 리스트, 검색부분 -->
											<tr>
												<td colspan="11" align=center>
													<table valign=top height="25">
														<tr>
															<td><!-- 페이징 -->
																<div id="getPagging" style="width:100%;display:no"></div>
															</td>
														</tr>
													</table>
    												<table id="searchDiv">
    													<tr>
    														<td align=middle height=30>
    															<select name=pSTarget>
    																<option value="b.user_name" >이름</option>
    																<option value="b.user_id"   >아이디</option>
    															</select>
    															<input maxlength=30 size=22 name="pSWord" value="">
    															<a href="javascript:goSearch();"><img src="/img/1/button_img/btn_search.gif" width=48 height=20 align=absmiddle></a>
    														</td>
    													</tr>
    												</table>
												</td>
											</tr>
											<!-- // 페이지 리스트, 검색부분 -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>
<script language="javascript">
	autoReload();
</script>

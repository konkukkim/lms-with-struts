	// 사용자리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var USER_TYPE = "";
	var PMODE = "";

	function init(systemCode,contextPath,userType, pMode) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.USER_TYPE = userType;
        this.PMODE = pMode;
        
		deptDaeSelectList();
	}

	function errorMessage(){
		alert("작업중 에러가 발생했습니다.\n재시도 해주세요!!!");
	}

	// 페이징처리
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
		autoReload();
	}

	// 소속구분 셀렉트박스 호출
	function deptDaeSelectList(){
		UsersWork.deptDaeSelectList(deptDaeSelectListCallback);
	}

	// 소속구분 셀렉트박스 표시
	function deptDaeSelectListCallback(data){
		DWRUtil.removeAllOptions("deptDaeCode");
		var defaultSelect = {"":"--소속구분--"};
		DWRUtil.addOptions("deptDaeCode", defaultSelect);
		DWRUtil.addOptions("deptDaeCode", data);		
		deptSoSelectList(DWRUtil.getValue("deptDaeCode"));
	}

	// 소속분류 셀렉트박스 호출
	function deptSoSelectList(deptDaeCode){
		UsersWork.deptSoSelectList(deptDaeCode, deptSoSelectListCallback);
	}

	// 소속분류 셀렉트박스 표시
	function deptSoSelectListCallback(data){
		DWRUtil.removeAllOptions("deptSoCode");
		var defaultSelect = {"":"--소속분류--"};
		DWRUtil.addOptions("deptSoCode", defaultSelect);
		DWRUtil.addOptions("deptSoCode", data);

		autoReload();
	}

	// 소속구분 변경
	function changeDeptDaeCode(){
		var f = document.f;
		f.pValue.value = "";	//검색어 초기화
		f.curPage.value = 1;	// 페이징 초기화

		var deptDaeCode =  DWRUtil.getValue("deptDaeCode");
		deptSoSelectList(deptDaeCode);
	}

	// 소속분류 변경
	function changeDeptSoCode(){
		var f = document.f;
		f.pValue.value = "";	//검색어 초기화
		f.curPage.value = 1;	// 페이징 초기화
		autoReload();
	}

	// 사용자리스트 호출 - 검색일경우
	function goSearch(){
		var deptDaeCode = DWRUtil.getValue("deptDaeCode");
		var deptSoCode = DWRUtil.getValue("deptSoCode");
		var curPage = 1;
		var fields = DWRUtil.getValue("pFields");
		var value = ajaxEnc(f.pValue.value);

		UsersWork.userPagingListAuto(curPage, fields,  value, USER_TYPE, deptDaeCode, deptSoCode, autoReloadCallback);
	}

	// 사용자리스트 호출
	function autoReload(){
		var deptDaeCode = DWRUtil.getValue("deptDaeCode");
		var deptSoCode = DWRUtil.getValue("deptSoCode");
		var curPage = f.curPage.value;
		var fields = DWRUtil.getValue("pFields");
		var value = ajaxEnc(f.pValue.value);

		UsersWork.userPagingListAuto(curPage, fields,  value, USER_TYPE, deptDaeCode, deptSoCode, autoReloadCallback);
	}

	// 사용자리스트 뿌려주기
	function autoReloadCallback(data){
	  	var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;
	  	var paggingStr = data.pagging;
		var rowLength = 0;
		if(dataList != null)
			rowLength = dataList.length;

	  	var userListObj = $("userList");
	  	var paggingObj = $("getPagging");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

		if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}else{
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "none";
		}

	  	if(rowLength == 0){
		  	objStr += "<tr><td colspan='11' align='center' height='50' bgcolor='#ffffff'>※ 등록된 사용자가 없습니다.</td></tr>"
			       + "<tr><td colspan='11' height='1' class='b_td03'></td></tr>";
	  	}else{
		  	for(i=0;i<rowLength;i++){
				var usersDTO = dataList[i];

		    	var userName = usersDTO.userName;
		    	var userId = usersDTO.userId;
		    	var deptSoname = usersDTO.deptSoname;
		    	var email = usersDTO.email;
		    	var regDate = usersDTO.regDate;

			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
			    	   + "		<td width='70' class=\"s_tab04_cen\">"+totalCount+"</td>"
			    	   + "	<td></td>";
			    	
			    	if(u_right=="true"){
						objStr += "		<td width='130' class=\"s_tab04_cen\"><a href=\"javascript:Modify_UserInfo('"+userId+"');\">"+userName+"</a></td>";
					}else{
						objStr += "		<td width='130' class=\"s_tab04_cen\">"+userName+"</td>";
					}
					
			    objStr += "		<td></td>"
			    	+ "			<td width='140' class=\"s_tab04_cen\"><a href=\"javascript:Show_UserInfo('"+userId+"');\">"+userId+"</a></td>"
					+ "			<td></td>"
			    	+ "			<td width='140' class=\"s_tab04_cen\">"+deptSoname+"</td>"
			    	+ "			<td></td>"
			    	+ "			<td width='110' class=\"s_tab04_cen\">"+regDate+"</td>"
			    	+ "			<td></td>"
			    	+ "			<td width='74' class=\"s_tab04_cen\"><input type='checkbox' name='pCHK' class='no' value='"+userId+"^"+userName+"^"+email+"'></td>"
			    	+ "		</tr><tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    totalCount--;
			}
		}
		objStr += "</table>";
		userListObj.innerHTML = objStr;
		userListObj.style.display = "block";
	 }

	function Users_Service(service){
		var f = document.f;
		f.service.value = service;
		f.target = "hiddenFrame";
		f.action = CONTEXTPATH+"/UserAdmin.cmd?cmd=userService";
		f.submit();
		f.target = "edutrack_main";
		f.action=CONTEXTPATH+"/UserAdmin.cmd?cmd=userPagingListAuto";
	}

	function User_Create(){
		var f = document.f;

		var usertype = f.userType.value;
		f.action=CONTEXTPATH+"/UserAdmin.cmd?cmd=userWrite&userType="+usertype+"&pMode="+PMODE+"&pGUBUN=write";
		f.submit();
	}

	function Modify_UserInfo(userid){
	
		var f = document.f;
		var usertype = f.userType.value;
		var loc=CONTEXTPATH+"/UserAdmin.cmd?cmd=userWrite&userType="+usertype+"&pMode="+PMODE+"&pGUBUN=edit&userId="+userid;
		document.location = loc;
	}

	function Change_UserStatus(userid, status, email, username){
		var f = document.f;
		var userType = f.userType.value;
		var curPage = f.curPage.value;

		var loc=CONTEXTPATH+"/UserAdmin.cmd?cmd=changeUserStatus&userId="+userid+"&userType="+userType+"&curPage="+curPage+"&useStatus="+status+"&email="+email+"&userName="+username;
		document.location = loc;
	}

	function Batch_AddUser(usertype){
		var Page=CONTEXTPATH+"/UserAdmin.cmd?cmd=batchUserWrite&userType="+usertype+"&pMode="+PMODE;
		ShowInfo = window.open(Page,"batchuseradd", "left=162,top=100,toolbar=0,directories=0,status=0,menubar=0,width=500,height=420,scrollbars=auto");
	}

	function Send_Mail(service){
		var newId = document.Regist.pUserId.value;
		var Page = CONTEXTPATH+"/User.cmd?cmd=userIdCheck&pNEW_ID="+newId+"&MENUNO=&pGUBUN="+gubun;
		ShowInfo	=	window.open(Page,"useridcheck", "left=162,top=100,toolbar=0,directories=0,status=0,menubar=0,width=338,height=300,scrollbars=no");
	}

  	function messageSend(str){
		var f = document.f;
		f.pResultCode2.value = f.deptDaeCode[f.deptDaeCode.selectedIndex].value;
		f.pResultCode3.value = f.deptSoCode[f.deptSoCode.selectedIndex].value;

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
		f.action = CONTEXTPATH+"/PopMessage.cmd?cmd=messageSend";
		f.target = "popMessage";
		f.method = "post";
		f.submit();
	}

  	function mailSend(str){
		var f = document.f;

		f.pResultCode2.value = f.deptDaeCode[f.deptDaeCode.selectedIndex].value;
		f.pResultCode3.value = f.deptSoCode[f.deptSoCode.selectedIndex].value;

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
		f.action = CONTEXTPATH+"/Mail.cmd?cmd=mailWrite";
		f.target = "popMail";
		f.method = "post";
		f.submit();
	}

	function Show_UserInfo(userid){
		var Page = CONTEXTPATH+"/User.cmd?cmd=userInfoShow&userId="+userid+"&pMode="+PMODE;
		ShowInfo = window.open(Page,"userinfoshow", "left=162,top=100,toolbar=0,directories=0,status=0,menubar=0,width=380,height=400,scrollbars=yes");
	}
	function UserAdd(param){
		var f = document.f;
		var deptDaeCode = f.deptDaeCode[f.deptDaeCode.selectedIndex].value;
		var deptSoCode = f.deptSoCode[f.deptSoCode.selectedIndex].value;
		var userType = f.userType.value;

		var Page = CONTEXTPATH+"/UserAdmin.cmd?cmd=userWrite&userType="+userType+"&pMode="+PMODE+"&pGUBUN="+param+"&pDeptDaecode="+deptDaeCode+"&pDeptSocode="+deptSoCode;

		f.method="post";
		f.action = Page ;
		f.submit();
	}

	function UserTypeAdd(){
		var f = document.f;
		var userType = f.userType.value;
		var Page = CONTEXTPATH+"/UserAdmin.cmd?cmd=userTypeAddForm&userType="+userType;
		ShowInfo = window.open(Page,"userinfoshow", "left=162, top=100, toolbar=0, directories=0, status=0, menubar=0, width=520, height=350, scrollbars=no");
	}
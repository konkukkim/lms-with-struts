	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var DEPT_DAECODE = "";

	function init(systemCode,contextPath, deptDaecode) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.DEPT_DAECODE = deptDaecode;

		autoReload();
	}

	// 페이징처리
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
  		deptSoWrite('none');
		autoReload();
	}

	// 폼체크
	function formCheck(){
		var f = document.f;
		if(trim(f.pDeptSocode.value) == ""){
			alert("소코드를 입력하세요");
			new Effect.Highlight("pDeptSocode");
			f.pDeptSocode.focus();
			return false;
		}
		if(trim(f.pDeptSoname.value) == ""){
			alert("소코드명을 입력하세요");
			new Effect.Highlight("pDeptSoname");
			f.pDeptSoname.focus();
			return false;
		}
		return true;
	}

	// 소코드리스트 받아오기
	function autoReload(){
		var f = document.f;
		DeptSoWork.deptSoListAuto(f.curPage.value, f.pCol.value, f.pOrder.value, DEPT_DAECODE, autoReloadCallback);
	}

	function autoReloadCallback(data){
	  	var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;
	  	var paggingStr = data.pagging;
		var rowLength = 0;
		var No = 0;
		if(dataList != null)
			rowLength = dataList.length;

	  	var deptSoListObj = $("deptSoList");
	  	var paggingObj = $("getPagging");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

		if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">등록된 소코드가 없습니다.</td></tr>";
	  	}else{
		  	for(i=0;i<rowLength;i++){
				var deptSoCodeDTO = dataList[i];
				No++;

		    	var deptSoname = deptSoCodeDTO.deptSoname;
		    	var deptDaecode = deptSoCodeDTO.deptDaecode;
		    	var deptSocode = deptSoCodeDTO.deptSocode;
		    	var useName = deptSoCodeDTO.useName;
		    	var regDate = deptSoCodeDTO.regDate;

			    objStr += "<tr>"
				+ "<td width='40' class=\"s_tab04_cen\">"+No+"</td>"
				+ "<td></td><td width='110' class=\"s_tab04_cen\">"+deptSocode+"</td>"
				+ "<td></td><td width='215' class=\"s_tab04_cen\">"+deptSoname+"</td>"
				+ "<td></td><td width='110' class=\"s_tab04_cen\">"+useName+"</td>"
				+ "<td></td><td width='110' class=\"s_tab04_cen\">"+regDate+"</td>"
				+ "<td></td><td width='80' class=\"s_tab04_cen\"><b>["
				+ "<a href=\"javascript:getDeptSoInfo('U','"+deptDaecode+"','"+deptSocode+"')\">수정</a>/"
				+ "<a href=\"javascript:getDeptSoInfo('D','"+deptDaecode+"','"+deptSocode+"')\">삭제</a>"
				+ "]</b></td>"
				+ "</tr>";

				if(totalCount > 1)	objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    totalCount--;
			}
		}
		objStr += "</table>";
		deptSoListObj.innerHTML = objStr;
		deptSoListObj.style.display = "block";
	}

	function deptSoWrite(value) {
		var f = document.f;
		f.pDeptSocode.value="";
		f.pDeptSoname.value="";
		f.pDeptSocode.readOnly = false;
		f.pDeptSocode.style.background = "#FFFFFF";
		f.pGroupPost.value="";
		f.pGroupAddress.value="";
		f.pGroupPhone.value="";
		f.pGroupPosition.value="";
	    f.pUseYn[0].checked = true;

		$("deptSoWrite").style.display = value;
		$("regButton").style.display = "block";
		$("modButton").style.display = "none";
	}

	// 소속소코드 객체
	function DeptSoCodeObject(deptDaecode, deptSocode, deptSoname, useYn, postCode, address, phone, position){
		this.deptDaecode = deptDaecode;
		this.deptSocode = deptSocode;
		this.deptSoname = deptSoname;
		this.useYn = useYn;
		this.postCode = postCode;
		this.address = address;
		this.phone = phone;
		this.position = position;
	}

	// 소코드 등록
	function manageDeptSo(regMode){
	   if(regMode == "ADD"){
	   	   if(formCheck()){
			   var f = document.f;
			   var deptSocode = trim(f.pDeptSocode.value);
			   var deptSoname = ajaxEnc(trim(f.pDeptSoname.value));
			   var useYn = "Y";
			   for(i=0;i<f.pUseYn.length;i++){
		   	   	   if(f.pUseYn[i].checked == true){
			   		   useYn = f.pUseYn[i].value;
			   	   }
			   }

			   var groupPost = trim(f.pGroupPost.value);
			   var groupAddress = ajaxEnc(trim(f.pGroupAddress.value));
			   var groupPhone = trim(f.pGroupPhone.value);
			   var groupPosition = ajaxEnc(trim(f.pGroupPosition.value));

			   var deptSoCodeDto =  new DeptSoCodeObject(DEPT_DAECODE,deptSocode,deptSoname,useYn,groupPost,groupAddress,groupPhone,groupPosition);
			   DeptSoWork.deptSoRegist(deptSoCodeDto, regMode, addDeptSoCallback);

		   }else return;
	    }else if(regMode == "EDIT" || regMode == "DEL"){
	   	   if(formCheck()){
		       var flagStr ="";
		       if(regMode == "EDIT")
		       	   flagStr = "수정 하시겠습니까?";
		       else
		       	   flagStr = "소코드를 삭제 할 경우 시스템 전반에 영향을 미칠 수 있습니다.\n삭제 하시겠습니까?";

		       var flag = confirm(flagStr);

		       if(flag == true){
				   var f = document.f;
				   var deptSocode = trim(f.pDeptSocode.value);
				   var deptSoname = ajaxEnc(trim(f.pDeptSoname.value));
				   var useYn = "Y";
				   for(i=0;i<f.pUseYn.length;i++){
			   	   	   if(f.pUseYn[i].checked == true){
				   		   useYn = f.pUseYn[i].value;
				   	   }
				   }
				   var groupPost = trim(f.pGroupPost.value);
				   var groupAddress = ajaxEnc(trim(f.pGroupAddress.value));
				   var groupPhone = trim(f.pGroupPhone.value);
				   var groupPosition = ajaxEnc(trim(f.pGroupPosition.value));

				   var deptSoCodeDto =  new DeptSoCodeObject(DEPT_DAECODE,deptSocode,deptSoname,useYn,groupPost,groupAddress,groupPhone,groupPosition);
				   DeptSoWork.deptSoRegist(deptSoCodeDto, regMode, addDeptSoCallback);

			    }else
			    	return;
		    }else return;
	    }else{
	    	return;
	    }
	}

	//소코드 등록 결과
	function addDeptSoCallback(data){
	  	var result = data;
	  	if(result == '1'){
	  		deptSoWrite('none');
		  	autoReload();
	  	}else{
	  		return;
	  	}
	}

	// 소코드정보 가져오기
	function getDeptSoInfo(vGubun, deptDaecode, deptSocode){
		
		if(vGubun=="U" && u_right!="true"){ 
	    	alert("수정 권한이 없습니다. \n\n관리자에게 문의 하세요");
	    	return;
	    }
	    
	    if(vGubun=="D" && d_right!="true"){ 
	    	alert("삭제 권한이 없습니다. \n\n관리자에게 문의 하세요");
	    	return;
	    }	
	    
	    if(deptSocode !=null && deptSocode !=''){
			DeptSoWork.deptSoInfo(deptDaecode, deptSocode, getDeptSoInfoCallback);
		}else{
			alert("소코드가 없습니다.");
			return;
		}
	}

	// 소코드 정보세팅
	function getDeptSoInfoCallback(data) {
		var f = document.f;
		f.pDeptSocode.value="";
		f.pDeptSoname.value="";

		var deptSocode =  data.deptSocode;
		var deptSoname =  data.deptSoname;
		var postCode =  data.postCode;
		var address =  data.address;
		var phone =  data.phone;
		var position =  data.position;
		var useYn =  data.useYn;

	  	if(deptSocode != null && deptSocode != ''){
		    f.pDeptSocode.value = deptSocode;
		    f.pDeptSoname.value = deptSoname;
			f.pGroupPost.value = postCode;
			f.pGroupAddress.value = address;
			f.pGroupPhone.value = phone;
			f.pGroupPosition.value = position;

		    for(i=0;i<f.pUseYn.length;i++){
	   	   	    if(useYn == f.pUseYn[i].value){
		   			f.pUseYn[i].checked=true;
		   		}
		    }

			$("deptSoWrite").style.display = "block";

			f.pDeptSocode.readOnly = true;
			f.pDeptSocode.style.background = "#F8F6F3";

			$("regButton").style.display = "none";
			$("modButton").style.display = "block";
	  	}else{
	  		return;
	  	}
	}

	// sorting
	function sorting(pos,col,order) {
		var f = document.f;
		f.pCol.value=col;
		f.pOrder.value=order;
		autoReload();

		var obj = document.getElementById('listTitle').getElementsByTagName('a');
		var len = obj.length;

		for(i=1;i<=len;i++){
			var objSort = document.getElementById("sort_"+i);
			if("sort_"+pos == "sort_"+i){
				if(i%2==0)	objSort.innerHTML = "▼";
				else	objSort.innerHTML = "▲";

				new Effect.Highlight("sort_"+i);
			}else{
				if(i%2==0)	objSort.innerHTML = "▽";
				else	objSort.innerHTML = "△";
			}
		}
	}

	// code_dae 중복체크 start
	function checkDeptSo(){

		var f = document.f;
		var deptDaecode = f.pDeptDaecode.value;
		var deptSocode = f.pDeptSocode.value;

		if(deptSocode !=null && deptSocode !=''){
			DeptSoWork.deptSoCheck(deptDaecode,deptSocode,{
				callback:function(data) {

					var deptSoCnt = data;
				  	if(deptSoCnt != '0'){
					  	viewMessage();
				  	}else{
				  		return;
				  	}
				}
			});
		}else{
			return;
		}
	}

	function viewMessage() {
		var f = document.f;
	    var deptSocode = f.pDeptSocode.value;
		alert("'"+deptSocode+"'는 사용중인 소코드입니다.\n다른 소코드를 입력해 주세요");
		f.pDeptSocode.value ="";
		new Effect.Highlight("pDeptSocode");
		f.pDeptSocode.focus();
	}
	// code_so 중복체크 end

	// 대코드목록으로 이동
	function goDeptDaeList() {
		var loc=CONTEXTPATH+"/DeptDae.cmd?cmd=deptDaeList&pMode=MyPage";
		document.location.href = loc;
	}

	// 우편번호찾기
    function ZipCode(form, zip, addr) {
		Page = CONTEXTPATH+"/Common.cmd?cmd=searchPost&pForm="+form+"&pZip="+zip+"&pAddr="+addr;
		ShowInfo = window.open(Page,"info4", "left=162,top=100,toolbar=0,directories=0,status=0,menubar=0,width=420,height=360,scrollbars=yes");
	}

	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}

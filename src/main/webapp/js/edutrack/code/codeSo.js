	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var CODEDAE = "";

	function init(systemCode,contextPath, codeDae) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.CODEDAE = codeDae;

		autoReload();
	}

	// 페이징처리
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
  		codeSoWrite('none');
		autoReload();
	}

	// 폼체크
	function formCheck(){
		var f = document.f;
		if(f.pCodeSo.value == ""){
			alert("소코드를 입력하세요");
			new Effect.Highlight("pCodeSo");
			f.pCodeSo.focus();
			return false;
		}
		if(f.pSoName.value == ""){
			alert("소코드명을 입력하세요");
			new Effect.Highlight("pSoName");
			f.pSoName.focus();
			return false;
		}
		return true;
	}

	// 소코드리스트 받아오기
	function autoReload(){
		var f = document.f;
		CodeSoWork.codeSoListAuto(f.curPage.value, f.pCol.value, f.pOrder.value, CODEDAE, autoReloadCallback);
	}

	function autoReloadCallback(data){
	  	var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;
	  	var paggingStr = data.pagging;
		var rowLength = 0;
		var No = 0;
		if(dataList != null)
			rowLength = dataList.length;

	  	var codeSoListObj = $("codeSoList");
	  	var paggingObj = $("getPagging");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

		if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">등록된 시스템 소코드가 없습니다.</td></tr>";
	  	}else{
		  	for(i=0;i<rowLength;i++){
				var codeSoDTO = dataList[i];
				No++;

		    	var soName = codeSoDTO.soName;
		    	var codeDae = codeSoDTO.codeDae;
		    	var codeSo = codeSoDTO.codeSo;
		    	var useName = codeSoDTO.useName;
		    	var regDate = codeSoDTO.regDate;

				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
					+ "<td width='40' class=\"s_tab04_cen\">"+No+"</td>"
					+ "<td></td><td width='110' class=\"s_tab04_cen\">"+codeSo+"</td>"
					+ "<td></td><td width='215' class=\"s_tab04_cen\">"+soName+"</td>"
					+ "<td></td><td width='110' class=\"s_tab04_cen\">"+useName+"</td>"
					+ "<td></td><td width='110' class=\"s_tab04_cen\">"+regDate+"</td>"
					+ "<td></td><td width='80' class=\"s_tab04_cen\"><a href=\"javascript:getCodeSoInfo('"+codeDae+"','"+codeSo+"')\"><b>[수정/삭제]</b></a></td>"
					+ "</tr>";

				if(totalCount > 1)	objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    totalCount--;
			}
		}
		objStr += "</table>";
		codeSoListObj.innerHTML = objStr;
		codeSoListObj.style.display = "block";
	}

	function codeSoWrite(value) {
		var f = document.f;
		f.pCodeSo.value="";
		f.pSoName.value="";
		f.pComment.value="";
		f.pCodeSo.readOnly = false;
		f.pCodeSo.style.background = "#FFFFFF";

		$("codeSoWrite").style.display = value;
		$("regButton").style.display = "block";
		$("modButton").style.display = "none";
	}

	// 소코드 등록
	function manageCodeSo(mode){
	   if(mode == "Add"){
	   	   if(formCheck()){
			   var f = document.f;
			   var codeDae = f.pCodeDae.value;
			   var codeSo = f.pCodeSo.value;
			   var soName = ajaxEnc(f.pSoName.value);
			   var comment = ajaxEnc(f.pComment.value);
			   var useYn = "Y";

			   for(i=0;i<f.pUseYn.length;i++){
		   	   	   if(f.pUseYn[i].checked == true){
			   		   useYn = f.pUseYn[i].value;
			   	   }
			   }
			   CodeSoWork.codeSoRegist(mode, codeDae, codeSo, soName, comment, useYn, addCodeSoCallback);

		   }else return;
	    }else if(mode == "Edit" || mode == "Delete"){
	   	   if(formCheck()){
		       var flagStr ="";
		       if(mode == "Edit")
		       	   flagStr = "수정 하시겠습니까?";
		       else
		       	   flagStr = "소코드를 삭제 할 경우 시스템 전반에 영향을 미칠 수 있습니다.\n삭제 하시겠습니까?";

		       var flag = confirm(flagStr);

		       if(flag == true){
				   var f = document.f;
				   var codeDae = f.pCodeDae.value;
				   var codeSo = f.pCodeSo.value;
				   var soName = ajaxEnc(f.pSoName.value);
				   var comment = ajaxEnc(f.pComment.value);
				   var useYn = "Y";

				   for(i=0;i<f.pUseYn.length;i++){
			   	   	   if(f.pUseYn[i].checked == true){
				   		   useYn = f.pUseYn[i].value;
				   	   }
				   }
				   CodeSoWork.codeSoRegist(mode, codeDae, codeSo, soName, comment, useYn, addCodeSoCallback);

			    }else
			    	return;
		    }else return;
	    }else{
	    	return;
	    }
	}

	//소코드 등록 결과
	function addCodeSoCallback(data){
	  	var result = data;
	  	if(result == '1'){
	  		codeSoWrite('none');
		  	autoReload();
	  	}else{
	  		return;
	  	}
	}

	// 소코드정보 가져오기
	function getCodeSoInfo(codeDae, codeSo){
	    if(codeSo !=null && codeSo !=''){
			CodeSoWork.codeSoInfo(codeDae, codeSo, getCodeSoInfoCallback);
		}else{
			alert("소코드가 없습니다.");
			return;
		}
	}

	// 소코드 정보세팅
	function getCodeSoInfoCallback(data) {
		var f = document.f;
		f.pCodeSo.value="";
		f.pSoName.value="";
		f.pComment.value="";

		var codeSo =  data.codeSo;
		var soName =  data.soName;
		var useYn =  data.useYn;
		var comment =  data.xcomment;

	  	if(codeSo != null && codeSo != ''){
		    f.pCodeSo.value = codeSo;
		    f.pSoName.value = soName;
		    f.pComment.value = comment;

		    for(i=0;i<f.pUseYn.length;i++){
	   	   	    if(useYn == f.pUseYn[i].value){
		   			f.pUseYn[i].checked=true;
		   		}
		    }

			$("codeSoWrite").style.display = "block";

			f.pCodeSo.readOnly = true;
			f.pCodeSo.style.background = "#F8F6F3";

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
	function checkCodeSo(){

		var f = document.f;
		var codeDae = f.pCodeDae.value;
		var codeSo = f.pCodeSo.value;

		if(codeSo !=null && codeSo !=''){
			CodeSoWork.codeSoCheck(codeDae,codeSo,{
				callback:function(data) {

					var codeSoCnt = data;
				  	if(codeSoCnt != '0'){
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
	    var codeSo = f.pCodeSo.value;
		alert("'"+codeSo+"'는 사용중인 소코드입니다.\n다른 소코드를 입력해 주세요");
		f.pCodeSo.value ="";
		new Effect.Highlight("pCodeSo");
		f.pCodeSo.focus();
	}
	// code_so 중복체크 end

	// 대코드목록으로 이
	function goCodeDaeList() {
		var loc=CONTEXTPATH+"/CodeDae.cmd?cmd=codeDaeList&pMode=MyPage";
		document.location.href = loc;
	}

	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}

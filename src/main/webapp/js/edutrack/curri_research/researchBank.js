	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var USER_ID = "";
	var RES_ID = "";
	var RES_NO = "";

	function init(systemCode,contextPath,userId,resId) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.USER_ID = userId;
		this.RES_ID = resId;

		researchBankInfoSelectList('Y');
	}

	function errorMessage(){
		alert("작업중 에러가 발생했습니다.\n재시도 해주세요!!!");
	}

	 // 폼체크 - 문제은행
	function formCheck(){
		var subject = DWRUtil.getValue("pSubject");

		if(subject == ""){
			alert("항목명을 입력해주세요");
			new Effect.Highlight("pSubject");
			$("pSubject").focus();
			return false;
		}
		return true;
	}

	 // 폼체크 - 문제
	function formCheckContents(){
		var f = document.f;
		var contentsType = DWRUtil.getValue("pContentsType");

		if(isEmpty(f.pContents.value)) {
			alert('문제를 입력하세요');
			new Effect.Highlight("pContents");
			f.pContents.focus();
			return false;
		}

		if(contentsType == "K"){
	        var checkExample = 0;
	        for(var i=1; i <= 10;i++) {
	        	if(f["pExample"+i].value != "") checkExample++;
	        }

	        if(checkExample == 0){
	           alert("보기를 입력해 주세요");
	           f.pExample1.focus();
	           return false;
	        }

	        if(checkExample < 2){
	           alert("보기는 하나 이상을 입력하셔야 합니다.");
	           f.pExample1.focus();
	           return false;
	        }
   		}
		return true;
	}

	// 추가로 이동
	function goAdd() {
		if(RES_ID == ""){
			alert("항목명을 선택해 주십시요.");
			$("pResId").focus();
			return;
		}

		initResBkContentsWrite();
		$("researchBankContentsListDIV").style.display = "none";
		$("researchBankContentsWriteDIV").style.display = "block";
		changeContentsType('K');
		$("modButtonContentsDiv").style.display = "none";
		$("regButtonContentsDiv").style.display = "block";
	}

	// 수정으로 이동
	function goEdit(resNo) {
		initResBkContentsWrite();
		$("researchBankContentsListDIV").style.display = "none";
		RES_NO = resNo;
		ResearchBankWork.researchBankContentsInfo(RES_ID, RES_NO, goEditCallback);
	}

	// 목록으로 이동
	function goList() {
		$("researchBankContentsWriteDIV").style.display = "none";
		$("researchBankContentsListDIV").style.display = "block";
	}

	// 문제유형변경
	function changeContentsType(contentsType) {
		if(contentsType == "K" )
			$("researchWriteK").style.display = "block";
		else
			$("researchWriteK").style.display = "none";
		setOptionSelected($("pContentsType"),contentsType);
	}

	// 화면초기화
	function initResBkContentsWrite(){
		var f = document.f;
		f.pContents.value="";
		f.pExample1.value="";
		f.pExample2.value="";
		f.pExample3.value="";
		f.pExample4.value="";
		f.pExample5.value="";
		f.pExample6.value="";
		f.pExample7.value="";
		f.pExample8.value="";
		f.pExample9.value="";
		f.pExample10.value="";

	    for(i=0;i<f.pExampleNum.length;i++){
   	   	    if(f.pExampleNum[i].value == 'O'){
	   	 	    f.pExampleNum[i].checked=true;
	   	    }
	    }
	}

	// 문제정보 세팅
	function goEditCallback(resBkContentsDto) {
	  	if(resBkContentsDto != null){
	  		var f = document.f;
			f.pContents.value = resBkContentsDto.contents;
	  		var contentsType = resBkContentsDto.contentsType;
			if(contentsType == "K"){
				f.pExample1.value = resBkContentsDto.example1;
				f.pExample2.value = resBkContentsDto.example2;
				f.pExample3.value = resBkContentsDto.example3;
				f.pExample4.value = resBkContentsDto.example4;
				f.pExample5.value = resBkContentsDto.example5;
				f.pExample6.value = resBkContentsDto.example6;
				f.pExample7.value = resBkContentsDto.example7;
				f.pExample8.value = resBkContentsDto.example8;
				f.pExample9.value = resBkContentsDto.example9;
				f.pExample10.value = resBkContentsDto.example10;
				var exampleNum = resBkContentsDto.exampleNum;

			    for(i=0;i<f.pExampleNum.length;i++){
		   	   	    if(exampleNum == f.pExampleNum[i].value){
			   			f.pExampleNum[i].checked=true;
			   		}
			    }
			}

			setOptionSelected($("pContentsType"),contentsType);
			$("researchBankContentsWriteDIV").style.display = "block";
			$("regButtonContentsDiv").style.display = "none";
			$("modButtonContentsDiv").style.display = "block";
			if(contentsType == "K" )
				$("researchWriteK").style.display = "block";
			else
				$("researchWriteK").style.display = "none";

	  	}else{
	  		return;
	  	}
	}

	// 문제은행 항목 셀렉트박스 초기화
	function researchBankInfoSelectList(selected){
		if(selected != "Y")
			RES_ID = "";

		closeResearchBankInfoWrite();
		ResearchBankWork.researchBankInfoSelectList(USER_ID, 'N', researchBankInfoSelectListCallback);
	}

	// 문제은행 항목 셀렉트박스 표시
	function researchBankInfoSelectListCallback(data){
		DWRUtil.removeAllOptions("pResId");
		var defaultSelect = {"":"--항목선택--"};
		DWRUtil.addOptions("pResId", defaultSelect);
		DWRUtil.addOptions("pResId", data);

		setOptionSelected($("pResId"),RES_ID);
		autoReload();
	}

	// 문제은행 항목 생성 열기
	function manageResearchBankInfo(regMode){
		if(regMode == "write"){
			$("modButtonDiv").style.display = "none";
			$("regButtonDiv").style.display = "block";
			$("researchBankInfoSelectDiv").style.display = "none";
			Effect.Appear("researchBankWriteDiv");
		}else if(regMode == "edit"){
			var resId = DWRUtil.getValue("pResId");
			if(resId != null && resId != ""){
				ResearchBankWork.researchBankInfo(resId,{
					callback:function(resBkInfoDto) {
					  	if(resBkInfoDto != null){
					  		var subject = resBkInfoDto.subject;
					  		var shareYn = resBkInfoDto.shareYn;
		  					var f = document.f;
						    for(i=0;i<f.pShareYn.length;i++){
					   	   	    if(shareYn == f.pShareYn[i].value){
						   			f.pShareYn[i].checked=true;
						   		}
						    }

							$("regButtonDiv").style.display = "none";
							$("modButtonDiv").style.display = "block";
					  		f.pSubject.value = subject;
							$("researchBankInfoSelectDiv").style.display = "none";
							Effect.Appear("researchBankWriteDiv");
					  	}else{
					  		return;
					  	}
					}
				});
			}else{
				alert("항목을 선택하세요");
				$("pResId").focus();
			}
		}else if(regMode == "delete"){
			var resId = DWRUtil.getValue("pResId");
			if(resId != null && resId != ""){
				var st = confirm('항목에 해당하는 모든 문제가 삭제 됩니다.\n\n 항목을 삭제하시겠습니까?');
				if (st == true) {
					ResearchBankWork.researchBankInfoRegist(regMode,resId,"","",{
						callback:function(data) {
						  	if(data != '0'){
						  		RES_ID = "";
							  	researchBankInfoSelectList('N');
						  	}else{
						  		return;
						  	}
						}
					});
				}
			}else{
				alert("항목을 선택하세요");
				$("pResId").focus();
			}
		}
	}

	// 문제은행 항목 등록/수정
	function registResearchBankInfo(regMode){
		var f = document.f;
		if(formCheck()){
			var resId = DWRUtil.getValue("pResId");
			var subject = DWRUtil.getValue("pSubject");
		   	var shareYn = "N";
		  	for(i=0;i<f.pShareYn.length;i++){
	   	   	   if(f.pShareYn[i].checked == true){
		   		   shareYn = f.pShareYn[i].value;
		   	   }
		   	}

			ResearchBankWork.researchBankInfoRegist(regMode,resId,subject,shareYn,{
				callback:function(data) {
				  	if(data != '0'){
				  		if(regMode == "write"){
					  		RES_ID = "";
					  		closeResearchBankInfoWrite();
						  	researchBankInfoSelectList('N');
					  	}else if(regMode == "edit"){
					  		RES_ID = resId;
					  		closeResearchBankInfoWrite();
						  	researchBankInfoSelectList('Y');
					  	}
				  	}else{
				  		return;
				  	}
				}
			});
		}else
			return;
	}

	// 문제은행 항목 생성 닫기
	function closeResearchBankInfoWrite(){
		var f = document.f;
		f.pSubject.value = "";
		$("researchBankWriteDiv").style.display = "none";
		Effect.Appear("researchBankInfoSelectDiv");
	}

	function autoReload(){
		RES_ID = DWRUtil.getValue("pResId");
		ResearchBankWork.researchBankContentsListAuto(RES_ID, autoReloadCallback);
	}

	// 과정리스트 뿌려주기
	function autoReloadCallback(data){
		// 화면전환
		$("researchBankContentsWriteDIV").style.display = "none";
		$("researchBankContentsListDIV").style.display = "block";

		// 데이타 목록 세팅
		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var objStr = "<table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
	  		if(RES_ID != ""){
			  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">등록된 문제가 없습니다.</td></tr>";
			}else{
			  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">항목을 선택하세요.</td></tr>";
			}
	  	}else{
	    	var contentsType = "";
	    	var resNo = "";
	    	var contents = "";
	    	var contentsTypeName = "";

		  	for(i=0;i<rowLength;i++){
    			var researchBkContentsDTO = data[i];

				contentsType = researchBkContentsDTO.contentsType;
				resNo = researchBkContentsDTO.resNo;
				contents = researchBkContentsDTO.contents;

				if(contentsType == "J") contentsTypeName	=	"서술형";
				else if(contentsType == "F") contentsTypeName	=	"파일형";
				else if(contentsType == "K") contentsTypeName	=	"선택형";
				else if(contentsType == "D") contentsTypeName	=	"단답형";
				else if(contentsType == "S") contentsTypeName	=	"OX형";

			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
			    	+ "<td width='70' class=\"s_tab04_cen\">"+allItemCnt+"</td>"
			    	+ "<td></td><td width='498' class=\"s_tab04\">"
			    	+ "<a href=\"javascript:goEdit('"+resNo+"');\">"+contents+"</a></td>"
					+ "<td></td><td  width='100' class=\"s_tab04_cen\">"+contentsTypeName+"</td>"
			    	+ "</tr>";

				if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    allItemCnt--;
			}
		}
		objStr += "</table>";

		$("researchBankContentsList").innerHTML = objStr;
		$("researchBankContentsList").style.display = "block";
	 }


	// 문제 입력/수정
	function registResearchBkContents(regMode){
		if(formCheckContents()){
			var resBkContetsDto = new Object();
			var f = document.f;
			var contentsType = DWRUtil.getValue("pContentsType");
			resBkContetsDto.resId = RES_ID;
			resBkContetsDto.resNo = RES_NO;
			resBkContetsDto.contentsType = DWRUtil.getValue("pContentsType");
			resBkContetsDto.contents = f.pContents.value;
			if(contentsType == "K"){
				resBkContetsDto.example1 = f.pExample1.value;
				resBkContetsDto.example2 = f.pExample2.value;
				resBkContetsDto.example3 = f.pExample3.value;
				resBkContetsDto.example4 = f.pExample4.value;
				resBkContetsDto.example5 = f.pExample5.value;
				resBkContetsDto.example6 = f.pExample6.value;
				resBkContetsDto.example7 = f.pExample7.value;
				resBkContetsDto.example8 = f.pExample8.value;
				resBkContetsDto.example9 = f.pExample9.value;
				resBkContetsDto.example10 = f.pExample10.value;

			   	var exampleNum = "O";
			  	for(i=0;i<f.pExampleNum.length;i++){
		   	   	   if(f.pExampleNum[i].checked == true){
			   		   exampleNum = f.pExampleNum[i].value;
			   	   }
			   	}
				resBkContetsDto.exampleNum = exampleNum;
			}

			ResearchBankWork.researchBankContentsRegist(resBkContetsDto, regMode,{
				callback:function(data) {
				  	if(data > 0){
						autoReload();
				  	}else{
				  		return;
				  	}
				}
			});
		}else{
			return;
		}
	}

	// 문제 삭제
	function deleteResearchBkContents(regMode){
		if(RES_NO != null && RES_NO != ""){
			var st = confirm('문제를 삭제하시겠습니까?');
			if(st){
				var resBkContetsDto = new Object();
				resBkContetsDto.resId = RES_ID;
				resBkContetsDto.resNo = RES_NO;
				ResearchBankWork.researchBankContentsRegist(resBkContetsDto, "delete",{
					callback:function(data) {
					  	if(data > 0){
							autoReload();
					  	}else{
					  		return;
					  	}
					}
				});
			}
		}else{
			return;
		}
	}

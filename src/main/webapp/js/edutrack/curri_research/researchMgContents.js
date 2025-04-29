	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var RES_ID = "";
	var RES_NO = "";
	var ANSWER_USER_CNT = "0";
	var RES_CONTENTS_CNT = "";

	function init(systemCode,contextPath,resId) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.RES_ID = resId;
		this.ANSWER_USER_CNT = document.f.pAnswerUserCnt.value;

		autoReload();
	}

	function errorMessage(){
		alert("작업중 에러가 발생했습니다.\n재시도 해주세요!!!");
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

		if(ANSWER_USER_CNT > 0){
			alert(ANSWER_USER_CNT+" 명의 대상자가 설문에 응한 상태입니다 \n\n 설문 출제를 할 수 없습니다!!!'");
		}else{
			initResContentsWrite();
			$("researchContentsListDIV").style.display = "none";
			$("researchContentsWriteDIV").style.display = "block";

			changeContentsType('K');
			$("modButtonContentsDiv").style.display = "none";
			$("regButtonContentsDiv").style.display = "block";
		}
	}

	// 수정으로 이동
	function goEdit(resNo) {
		initResContentsWrite();
		$("researchContentsListDIV").style.display = "none";
		RES_NO = resNo;
		ResearchContentsWork.researchContentsInfo(RES_ID, RES_NO, goEditCallback);
	}

	// 목록으로 이동
	function goList() {
		$("researchContentsWriteDIV").style.display = "none";
		$("researchContentsListDIV").style.display = "block";
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
	function initResContentsWrite(){
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
	function goEditCallback(curriResContentsDto) {
	  	if(curriResContentsDto != null){
	  		var f = document.f;
			f.pContents.value = curriResContentsDto.contents;
	  		var contentsType = curriResContentsDto.contentsType;
			if(contentsType == "K"){
				f.pExample1.value = curriResContentsDto.example1;
				f.pExample2.value = curriResContentsDto.example2;
				f.pExample3.value = curriResContentsDto.example3;
				f.pExample4.value = curriResContentsDto.example4;
				f.pExample5.value = curriResContentsDto.example5;
				f.pExample6.value = curriResContentsDto.example6;
				f.pExample7.value = curriResContentsDto.example7;
				f.pExample8.value = curriResContentsDto.example8;
				f.pExample9.value = curriResContentsDto.example9;
				f.pExample10.value = curriResContentsDto.example10;
				var exampleNum = curriResContentsDto.exampleNum;

			    for(i=0;i<f.pExampleNum.length;i++){
		   	   	    if(exampleNum == f.pExampleNum[i].value){
			   			f.pExampleNum[i].checked=true;
			   		}
			    }
			}

			setOptionSelected($("pContentsType"),contentsType);
			$("researchContentsWriteDIV").style.display = "block";
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

	function autoReload(){
		RES_ID = DWRUtil.getValue("pResId");
		ResearchContentsWork.researchContentsListAuto(RES_ID, autoReloadCallback);
	}

	// 과정설문문제리스트 뿌려주기
	function autoReloadCallback(data){
		// 화면전환
		$("researchContentsWriteDIV").style.display = "none";
		$("researchContentsListDIV").style.display = "block";

		// 데이타 목록 세팅
		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
			RES_CONTENTS_CNT = data.length;
		}

	  	var objStr = "<table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">등록된 문제가 없습니다.</td></tr>";
	  	}else{
	    	var contentsType = "";
	    	var resNo = "";
	    	var contents = "";
	    	var contentsTypeName = "";

		  	for(i=0;i<rowLength;i++){
    			var curriResContentsDTO = data[i];

				contentsType = curriResContentsDTO.contentsType;
				resNo = curriResContentsDTO.resNo;
				contents = curriResContentsDTO.contents;

				if(contentsType == "J") contentsTypeName	=	"서술형";
				else if(contentsType == "K") contentsTypeName	=	"선택형";
				else if(contentsType == "S") contentsTypeName	=	"OX형";

			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
			    	+ "<td width='70' class=\"s_tab04_cen\">"+allItemCnt+"</td>"
			    	+ "<td></td><td width='80' class=\"s_tab04_cen\">"+resNo+" 번</td>"
			    	+ "<td></td><td width='417' class=\"s_tab04\">"
			    	+ "<a href=\"javascript:goEdit('"+resNo+"');\">"+contents+"</a></td>"
					+ "<td></td><td width='80' class=\"s_tab04_cen\">"+contentsTypeName+"</td>"
			    	+ "</tr>";

			    if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    allItemCnt--;
			}
		}
		objStr += "</table>";

		$("researchContentsList").innerHTML = objStr;
		$("researchContentsList").style.display = "block";
	 }


	// 문제 입력/수정
	function registResearchContents(regMode){
		if(formCheckContents()){
			var curriResContetsDto = new Object();
			var f = document.f;
			var contentsType = DWRUtil.getValue("pContentsType");
			curriResContetsDto.resId = RES_ID;
			curriResContetsDto.resNo = RES_NO;
			curriResContetsDto.contentsType = DWRUtil.getValue("pContentsType");
			curriResContetsDto.contents = f.pContents.value;
			if(contentsType == "K"){
				curriResContetsDto.example1 = f.pExample1.value;
				curriResContetsDto.example2 = f.pExample2.value;
				curriResContetsDto.example3 = f.pExample3.value;
				curriResContetsDto.example4 = f.pExample4.value;
				curriResContetsDto.example5 = f.pExample5.value;
				curriResContetsDto.example6 = f.pExample6.value;
				curriResContetsDto.example7 = f.pExample7.value;
				curriResContetsDto.example8 = f.pExample8.value;
				curriResContetsDto.example9 = f.pExample9.value;
				curriResContetsDto.example10 = f.pExample10.value;

			   	var exampleNum = "O";
			  	for(i=0;i<f.pExampleNum.length;i++){
		   	   	   if(f.pExampleNum[i].checked == true){
			   		   exampleNum = f.pExampleNum[i].value;
			   	   }
			   	}
				curriResContetsDto.exampleNum = exampleNum;
			}

			ResearchContentsWork.researchContentsRegist(curriResContetsDto, regMode,{
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
	function deleteResearchContents(regMode){
		if(RES_NO != null && RES_NO != ""){
			var st = confirm('문제를 삭제하시겠습니까?');
			if(st){
				var curriResContetsDto = new Object();
				curriResContetsDto.resId = RES_ID;
				curriResContetsDto.resNo = RES_NO;
				ResearchContentsWork.researchContentsRegist(curriResContetsDto, "delete",{
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

	// 설문 등록
	function confirmResearchContents(){

		if(RES_CONTENTS_CNT < 1){
			alert("문제를 먼저 등록해 주세요.");
			return;
		}

		if(ANSWER_USER_CNT > 0){
			alert(ANSWER_USER_CNT+" 명의 대상자가 설문에 응한 상태입니다 \n\n 설문등록을 할 수 없습니다!!!");
			return;
		}

		f.action = CONTEXTPATH+"/ResearchInfo.cmd?cmd=researchOpen";
		f.submit();
	}

	// 미리보기
	function contentsPreview(){

		if(RES_CONTENTS_CNT < 1){
			alert("문제를 먼저 등록해 주세요.");
			return;
		}
		var winOption = "left="+windowLeftPosition(620)+",top="+windowTopPosition(560)+",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=yes,resizable=no,width=620,height=560";
		var loc = CONTEXTPATH+"/ResearchContents.cmd?cmd=researchContentsShow&pMODE=P&pResId="+RES_ID;
		ShowInfo = window.open(loc,"research",winOption);
	}

	// 설문목록페이지로 이동
    function goResInfoList(){
        var loc = CONTEXTPATH+"/ResearchInfo.cmd?cmd=researchInfoList";
        document.location = loc;
    }

    // 문제은행으로 이동
    function addBankContents(){

		if(ANSWER_USER_CNT > 0){
			alert(ANSWER_USER_CNT+" 명의 대상자가 설문에 응한 상태입니다 \n\n 설문등록을 할 수 없습니다!!!");
			return;
		}

        var f = document.f;
        var gubun = f.pGubun.value;
        var loc = CONTEXTPATH+"/ResearchBank.cmd?cmd=researchBankContentsList&pContentsResId="+RES_ID+"&pGubun="+gubun;
        document.location = loc;
    }

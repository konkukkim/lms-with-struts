	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var COURSE_ID = "";
	var BANK_CODE = "";

	function init(systemCode,contextPath,courseId,bankCode) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.COURSE_ID = courseId;
		this.BANK_CODE = bankCode;

		examBankInfoSelectList('Y');
	}

	function errorMessage(){
		alert("작업중 에러가 발생했습니다.\n재시도 해주세요!!!");
	}

	 // 폼체크
	function formCheck(){
		var bankName = DWRUtil.getValue("pBankName");;

		if(bankName == ""){
			alert("항목명을 입력해주세요");
			new Effect.Highlight("pBankName");
			$("pBankName").focus();
			return false;
		}
		return true;
	}

	// 문제은행 항목 셀렉트박스 초기화
	function examBankInfoSelectList(selected){
		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId !=null && tmpCourseId != ""){
			COURSE_ID = DWRUtil.getValue("pCourseId");
			setOptionSelected($("pCourseId"),COURSE_ID);
		}
		if(selected != "Y")
			BANK_CODE = "";

		closeExamBankInfoWrite();
		ExamBankWork.examBankInfoSelectList(COURSE_ID, examBankInfoSelectListCallback);
	}

	// 문제은행 항목 셀렉트박스 표시
	function examBankInfoSelectListCallback(data){
		DWRUtil.removeAllOptions("pBankCode");
		var defaultSelect = {"":"--항목선택--"};
		DWRUtil.addOptions("pBankCode", defaultSelect);
		DWRUtil.addOptions("pBankCode", data);

		setOptionSelected($("pBankCode"),BANK_CODE);
		autoReload();
	}

	// 문제은행 항목 생성 열기
	function manageExamBankInfo(regMode){
		if(regMode == "ADD"){
			$("modButtonDiv").style.display = "none";
			$("regButtonDiv").style.display = "block";
			$("examBankInfoSelectDiv").style.display = "none";
			Effect.Appear("examBankWriteDiv");
		}else if(regMode == "EDIT"){
			var bankCode = DWRUtil.getValue("pBankCode");
			if(bankCode != null && bankCode != ""){
				var bankName = DWRUtil.getText("pBankCode");
				$("regButtonDiv").style.display = "none";
				$("modButtonDiv").style.display = "block";
				$("pBankName").value = bankName;
				$("examBankInfoSelectDiv").style.display = "none";
				Effect.Appear("examBankWriteDiv");
			}else{
				alert("항목을 선택하세요");
				$("pBankCode").focus();
			}
		}else if(regMode == "DEL"){
			var bankCode = DWRUtil.getValue("pBankCode");
			if(bankCode != null && bankCode != ""){
				var st = confirm('항목에 해당하는 모든 문제가 삭제 됩니다.\n\n 항목을 삭제하시겠습니까?');
				if (st == true) {
					ExamBankWork.examBankInfoRegist(regMode,COURSE_ID,bankCode,"",{
						callback:function(data) {
						  	if(data != '0'){
						  		BANK_CODE = "";
							  	examBankInfoSelectList('N');
						  	}else{
						  		return;
						  	}
						}
					});
				}
			}else{
				alert("항목을 선택하세요");
				$("pBankCode").focus();
			}
		}
	}

	// 문제은행 항목 등록/수정
	function registExamBankInfo(regMode){
		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId !=null && tmpCourseId != "")
			COURSE_ID = DWRUtil.getValue("pCourseId");

		if(formCheck()){
			var bankCode = DWRUtil.getValue("pBankCode");
			var bankName = DWRUtil.getValue("pBankName");
			ExamBankWork.examBankInfoRegist(regMode,COURSE_ID,bankCode,bankName,{
				callback:function(data) {
				  	if(data != '0'){
				  		if(regMode == "ADD"){
					  		BANK_CODE = "";
					  		closeExamBankInfoWrite();
						  	examBankInfoSelectList('N');
					  	}else if(regMode == "EDIT"){
					  		BANK_CODE = bankCode;
					  		closeExamBankInfoWrite();
						  	examBankInfoSelectList('Y');
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
	function closeExamBankInfoWrite(){
		$("pBankName").value = "";
		$("examBankWriteDiv").style.display = "none";
		Effect.Appear("examBankInfoSelectDiv");
	}

	function autoReload(){
		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId !=null && tmpCourseId != "")
			COURSE_ID = DWRUtil.getValue("pCourseId");

		BANK_CODE = DWRUtil.getValue("pBankCode");
		ExamBankWork.examBankContentsListAuto(COURSE_ID, BANK_CODE, autoReloadCallback);
	}

	// 과정리스트 뿌려주기
	function autoReloadCallback(data){

		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var objStr = "<table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
	  		if(BANK_CODE != ""){
			  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">등록된 문제가 없습니다.</td></tr>";
			}else{
			  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">항목을 선택하세요.</td></tr>";
			}
	  	}else{
	    	var examType = "";
	    	var examNo = "";
	    	var contentsText = "";
	    	var rfileName = "";
	    	var sfileName = "";
	    	var filePath = "";
	    	var fileSize = "";
	    	var examTypeName = "";
	    	var fileDownLink = "";

		  	for(i=0;i<rowLength;i++){
    			var examBkContentsDTO = data[i];

				examType = examBkContentsDTO.examType;
				examNo = examBkContentsDTO.examNo;
				contentsText = examBkContentsDTO.contentsText;
				rfileName = examBkContentsDTO.rfileName;
				if(rfileName != ""){
					sfileName = examBkContentsDTO.sfileName;
					filePath = examBkContentsDTO.filePath;
					fileSize = examBkContentsDTO.fileSize;
					fileDownLink = "<a href=\"javascript:fileDownload('"+rfileName+"','"+sfileName+"','"+filePath+"','"+filePath+"');\">있음</a>";
				}else{
					fileDownLink = "없음";
				}

				if(examType == "J") examTypeName	=	"서술형";
				else if(examType == "F") examTypeName	=	"파일형";
				else if(examType == "K") examTypeName	=	"선택형";
				else if(examType == "D") examTypeName	=	"단답형";
				else if(examType == "S") examTypeName	=	"OX형";

			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
			    	+ "<td width='70' class=\"s_tab04_cen\">"+allItemCnt+"</td>"
			    	+ "<td></td><td width='' class=\"s_tab04\">"
			    	+ "<a href=\"javascript:showExamContents('"+examType+"','"+examNo+"');\">"+contentsText+"</a></td>"
					+ "<td></td><td width='100' class=\"s_tab04_cen\">"+examTypeName+"</td>"
			    	+ "<td></td><td width='100' class=\"s_tab04_cen\">"+fileDownLink+"</td>"
			    	+ "</tr>";

			    if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    allItemCnt--;
			}
		}
		objStr += "</table>";

		$("examBankContentsList").innerHTML = objStr;
		$("examBankContentsList").style.display = "block";
	 }

	 function addContents(examType){

		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId !=null && tmpCourseId != "")
			COURSE_ID = DWRUtil.getValue("pCourseId");

		if(COURSE_ID == ""){
			alert("과목을 선택해 주십시요.");
			$("pCourseId").focus();
			return;
		}

		if(BANK_CODE == ""){
			alert("항목명을 선택해 주십시요.");
			$("pBankCode").focus();
			return;
		}

		document.location.href = CONTEXTPATH+"/ExamBank.cmd?cmd=examBankContentsWrite&pMODE=ADD&pGubun=M&pCourseId="+COURSE_ID+"&pBankCode="+BANK_CODE+"&pExamType="+examType;
	 }

	function fileDownload(rfilename, sfilename, filepath, filesize){
		var loc=CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
		hiddenFrame.document.location = loc;
	}

	function showExamContents(examType,examNo){

		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId !=null && tmpCourseId != "")
			COURSE_ID = DWRUtil.getValue("pCourseId");

		if(COURSE_ID == ""){
			alert("과목을 선택해 주십시요.");
			$("pCourseId").focus();
			return;
		}

		if(BANK_CODE == ""){
			alert("항목명을 선택해 주십시요.");
			$("pBankCode").focus();
			return;
		}

		document.location.href = CONTEXTPATH+"/ExamBank.cmd?cmd=examBankContentsWrite&pMODE=EDIT&pGubun=M&pCourseId="+COURSE_ID+"&pBankCode="+BANK_CODE+"&pExamType="+examType+"&pExamNo="+examNo;
    }

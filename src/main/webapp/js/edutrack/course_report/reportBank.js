	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var COURSE_ID = "";
	var BANK_ID = "";

	function init(systemCode,contextPath,courseId,bankId) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.COURSE_ID = courseId;
		this.BANK_ID = bankId;

		reportBankInfoSelectList('Y');
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
	function reportBankInfoSelectList(selected){
		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId !=null && tmpCourseId != ""){
			COURSE_ID = DWRUtil.getValue("pCourseId");
			setOptionSelected($("pCourseId"),COURSE_ID);
		}
		if(selected != "Y")
			BANK_ID = "";

		closeReportBankInfoWrite();
		ReportBankWork.reportBankInfoSelectList(COURSE_ID, reportBankInfoSelectListCallback);
	}

	// 문제은행 항목 셀렉트박스 표시
	function reportBankInfoSelectListCallback(data){
		DWRUtil.removeAllOptions("pBankId");
		var defaultSelect = {"":"--항목선택--"};
		DWRUtil.addOptions("pBankId", defaultSelect);
		DWRUtil.addOptions("pBankId", data);

		setOptionSelected($("pBankId"),BANK_ID);
		autoReload();
	}

	// 문제은행 항목 생성 열기
	function manageReportBankInfo(regMode){
		if(regMode == "ADD"){
			$("modButtonDiv").style.display = "none";
			$("regButtonDiv").style.display = "block";
			$("reportBankInfoSelectDiv").style.display = "none";
			Effect.Appear("reportBankWriteDiv");
		}else if(regMode == "EDIT"){
			var bankId = DWRUtil.getValue("pBankId");
			if(bankId != null && bankId != ""){
				var bankName = DWRUtil.getText("pBankId");
				$("regButtonDiv").style.display = "none";
				$("modButtonDiv").style.display = "block";
				$("pBankName").value = bankName;
				$("reportBankInfoSelectDiv").style.display = "none";
				Effect.Appear("reportBankWriteDiv");
			}else{
				alert("항목을 선택하세요");
				$("pBankId").focus();
			}
		}else if(regMode == "DEL"){
			var bankId = DWRUtil.getValue("pBankId");
			if(bankId != null && bankId != ""){
				var st = confirm('항목에 해당하는 모든 문제가 삭제 됩니다.\n\n 항목을 삭제하시겠습니까?');
				if (st == true) {
					ReportBankWork.reportBankInfoRegist(regMode,COURSE_ID,bankId,"",{
						callback:function(data) {
						  	if(data != '0'){
						  		BANK_ID = "";
							  	reportBankInfoSelectList('N');
						  	}else{
						  		return;
						  	}
						}
					});
				}
			}else{
				alert("항목을 선택하세요");
				$("pBankId").focus();
			}
		}
	}

	// 문제은행 항목 등록/수정
	function registReportBankInfo(regMode){
		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId !=null && tmpCourseId != "")
			COURSE_ID = DWRUtil.getValue("pCourseId");

		if(formCheck()){
			var bankId = DWRUtil.getValue("pBankId");
			var bankName = DWRUtil.getValue("pBankName");
			ReportBankWork.reportBankInfoRegist(regMode,COURSE_ID,bankId,bankName,{
				callback:function(data) {
				  	if(data != '0'){
				  		if(regMode == "ADD"){
					  		BANK_ID = "";
					  		closeReportBankInfoWrite();
						  	reportBankInfoSelectList('N');
					  	}else if(regMode == "EDIT"){
					  		BANK_ID = bankId;
					  		closeReportBankInfoWrite();
						  	reportBankInfoSelectList('Y');
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
	function closeReportBankInfoWrite(){
		$("pBankName").value = "";
		$("reportBankWriteDiv").style.display = "none";
		Effect.Appear("reportBankInfoSelectDiv");
	}

	function autoReload(){
		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId !=null && tmpCourseId != "")
			COURSE_ID = DWRUtil.getValue("pCourseId");

		BANK_ID = DWRUtil.getValue("pBankId");
		ReportBankWork.reportBankContentsListAuto(COURSE_ID, BANK_ID, autoReloadCallback);
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
	  		if(BANK_ID != ""){
			  	objStr += "<tr><td colspan='7' align='center' height='50' bgcolor='#ffffff'>※ 등록된 문제가 없습니다.</td></tr>"
				       + "<tr><td colspan='7' height='1' class='b_td03'></td></tr>";
			}else{
			  	objStr += "<tr><td colspan='7' align='center' height='50' bgcolor='#ffffff'>※ 항목을 선택하세요.</td></tr>"
				       + "<tr><td colspan='7' height='1' class='b_td03'></td></tr>";
			}
	  	}else{
	    	var reportType = "";
	    	var reportNo = "";
	    	var contentsText = "";
	    	var rfileName = "";
	    	var sfileName = "";
	    	var filePath = "";
	    	var fileSize = "";
	    	var reportTypeName = "";
	    	var fileDownLink = "";

		  	for(i=0;i<rowLength;i++){
    			var reportBankContentsDTO = data[i];

				reportId = reportBankContentsDTO.reportId;
				reportSubject = reportBankContentsDTO.reportSubject;
				rfileName = reportBankContentsDTO.rfileName;
				if(rfileName != ""){
					sfileName = reportBankContentsDTO.sfileName;
					filePath = reportBankContentsDTO.filePath;
					fileSize = reportBankContentsDTO.fileSize;
					fileDownLink = "<a href=\"javascript:fileDownload('"+rfileName+"','"+sfileName+"','"+filePath+"','"+filePath+"');\">있음</a>";
				}else{
					fileDownLink = "없음";
				}

				var reportType = '';

			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
			    	+ "<td width='70' class=\"s_tab04_cen\">"+allItemCnt+"</td>"
			    	+ "<td></td><td class=\"s_tab04\">"
			    	+ "<a href=\"javascript:showReportContents('"+reportId+"');\">"+reportSubject+"</a></td>"
					+ "<td></td><td width='110' class=\"s_tab04_cen\">"+fileDownLink+"</td>"
					+ "</tr>";

			    if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"7\"></td></tr>";

			    allItemCnt--;
			}
		}
		objStr += "</table>";

		$("reportBankContentsList").innerHTML = objStr;
		$("reportBankContentsList").style.display = "block";
	 }

	 function addContents(){

		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId !=null && tmpCourseId != "")
			COURSE_ID = DWRUtil.getValue("pCourseId");

		if(COURSE_ID == ""){
			alert("과목을 선택해 주십시요.");
			$("pCourseId").focus();
			return;
		}

		if(BANK_ID == ""){
			alert("항목명을 선택해 주십시요.");
			$("pBankId").focus();
			return;
		}

		document.location.href = CONTEXTPATH+"/ReportBank.cmd?cmd=reportBankContentsWrite&pMODE=ADD&pGubun=M&pCourseId="+COURSE_ID+"&pBankId="+BANK_ID;
	 }

	function fileDownload(rfilename, sfilename, filepath, filesize){
		var loc=CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
		hiddenFrame.document.location = loc;
	}

	function showReportContents(reportId){

		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId !=null && tmpCourseId != "")
			COURSE_ID = DWRUtil.getValue("pCourseId");

		if(COURSE_ID == ""){
			alert("과목을 선택해 주십시요.");
			$("pCourseId").focus();
			return;
		}

		if(BANK_ID == ""){
			alert("항목명을 선택해 주십시요.");
			$("pBankId").focus();
			return;
		}

		document.location.href = CONTEXTPATH+"/ReportBank.cmd?cmd=reportBankContentsWrite&pMODE=EDIT&pGubun=M&pCourseId="+COURSE_ID+"&pBankId="+BANK_ID+"&pReportId="+reportId;
    }

	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "";
	var COURSE_ID = "";
	var REPORT_ID = "";

	var BANK_ID = "";

	function init(contextPath,systemCode,courseId,reportId,mode) {
		this.CONTEXTPATH = contextPath;
		this.SYSTEMCODE = systemCode;
		this.COURSE_ID = courseId;
		this.REPORT_ID = reportId;
		if(mode == "show") {
			autoReload();
		} else if(mode == "write") {
			reportBankInfoSelectList('Y');
		}
	}

	//문제 자동등록 여부
	function autoBankYn() {
		var f = document.Regist;

		var autoBank = DWRUtil.getValue("pAutoBankYn");
		var REGIST_YN = "";
		if(autoBank == "Y") {
			$("autoBankDiv").style.display = "block";
			f.pRegistYn[0].disabled = false;
			f.pRegistYn[1].checked = true;
			reportBankSelectList();
		} else {
			f.pRegistYn[0].disabled = true;
			f.pRegistYn[1].checked = true;
			$("autoBankDiv").style.display = "none";
		}
	}

	//문제은행 항목 셀렉트 박스(문제 자동 등록시)
	function reportBankSelectList() {
		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId != null && tmpCourseId != ""){
			COURSE_ID = DWRUtil.getValue("pCourseId");
		}
		ReportBankWork.reportBankInfoSelectList(COURSE_ID, reportBankSelectListCallback);
	}

	// 문제은행 항목 셀렉트박스 표시(문제 자동 등록시 사용)
	function reportBankSelectListCallback(data){
		DWRUtil.removeAllOptions("pBankId");
		DWRUtil.addOptions("pBankId", data);
	}

	//폼체크 & 서브및
	function SubmitCheck() {
		var f = document.Regist;

		if(!validate(f)) return;

		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */

		if(isEmpty(f.pContents.value)) {
			alert('과제설명을 입력하세요');
			return;
		}

	    var st_reportterm = f.year11.value + f.month11.value + f.day11.value + f.hour11.value + f.minute11.value + "00";
        f.reportTerm1.value = st_reportterm;

	    var ed_reportterm = f.year12.value + f.month12.value + f.day12.value + f.hour12.value + f.minute12.value + "00";
        f.reportTerm2.value = ed_reportterm;

	    var st_reportresult = f.year21.value + f.month21.value + f.day21.value + f.hour21.value + f.minute21.value + "00";
        f.reportResult1.value = st_reportresult;

	     // 제출연장일 유효성 체크
        if(parseFloat(ed_reportterm) > parseFloat(st_reportresult)) {
           alert("체출연장일자는 체출종료일자 이후에 설정이되어야 합니다.");
           return;
        }
		f.submit();
    }

    //과제 삭제
    function goDelete(CONTEXTPATH){
    	var chkDel =confirm('하위 과제 및 과제 제출 정보가 삭제됩니다.\n과제정보를 삭제 하시겠습니까?');
    	var f = document.Regist;

        var courseid = f.pCourseId.value;
        var reportid = f.pReportId.value;
        if(chkDel){
		    var loc = CONTEXTPATH+"/ReportAdmin.cmd?cmd=reportDelete&pCourseId="+courseid+"&pReportId="+reportid;
			document.location = loc;
		 }
    }

    //서브 과제 삭제
    function goSubDelete(CONTEXTPATH){
    	var chkDel = confirm('하위 과제정보를 삭제 하시겠습니까?');
    	var f = document.Input;

        var courseid = f.pCourseId.value;
        var reportid = f.pReportId.value;
        var subreportid	= f.pSubReportId.value;

        if(chkDel){
		    var loc = CONTEXTPATH+"/ReportSubInfo.cmd?cmd=reportSubInfoDelete&pCourseId="+courseid+"&pReportId="+reportid+"&pSubReportId="+subreportid;
			document.location = loc;
		 }

		 opener.autoReload();
    }

    //취소
    function goCancel(){
        var f = document.Regist;

        var courseid = f.pCourseId.value;

        var loc="/ReportAdmin.cmd?cmd=reportList&pCourseId="+courseid;
        document.location = loc;
    }

    //리스트 가져오기
    function autoReload(){
		ReportSubInfoWork.reportSubInfoListAuto(COURSE_ID, REPORT_ID, autoReloadCallback);
	}

	// 서브과제리스트 뿌려주기
	function autoReloadCallback(data){

		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var objStr = "<table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
			objStr += "<tr><td colspan='9' align='center' height='50' bgcolor='#ffffff'>※ 과제 리스트가 없습니다.</td></tr>"
				   + "<tr><td colspan='9' height='1' class='b_td03'></td></tr>";

	  	}else{
	    	var subReportId 		= "";
	    	var subReportSubject 	= "";
	    	var rfileName 			= "";
	    	var sfileName 			= "";
	    	var filePath 			= "";
	    	var fileSize 			= "";
	    	var fileDownLink = "";

		  	for(i=0;i<rowLength;i++){
    			var reportSubInfoDTO = data[i];

				subReportId 		= reportSubInfoDTO.subReportId;
				subReportSubject 	= reportSubInfoDTO.subReportSubject;
				rfileName 			= reportSubInfoDTO.rfileName;
				if(rfileName != "" && rfileName != "null"){
					sfileName 	= reportSubInfoDTO.sfileName;
					filePath 	= reportSubInfoDTO.filePath;
					fileSize 	= reportSubInfoDTO.fileSize;
					fileDownLink = "<a href=\"javascript:fileDownload('"+rfileName+"','"+sfileName+"','"+filePath+"','"+filePath+"');\">있음</a>";
				}else{
					fileDownLink = "없음";
				}

			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
			    	+ "<td width='70' class=\"s_tab04_cen\">"+allItemCnt+"</td>"
			    	+ "<td></td><td width='' class=\"s_tab04\">"
			    	+ " <a href=\"javascript:editSubReport('"+subReportId+"');\">"+subReportSubject+"</a></td>"
					+ "<td></td><td width='110' class=\"s_tab04_cen\">"+fileDownLink+"</td>"
					+ "</tr>";

				if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    allItemCnt--;
			    fileDownLink = "";
			}
		}
		objStr += "</table>";

		$("reportSubInfoList").innerHTML = objStr;
		$("reportSubInfoList").style.display = "block";
	 }

	 //파일 다운로드
	 function fileDownload(rfilename, sfilename, filepath, filesize){
		var loc=CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
		hiddenFrame.document.location = loc;
	 }


	// 첨부파일 제거 start
	function delFile(){
		var f = document.Input;
		var courseid 	= f.pCourseId.value;
	    var reportid 	= f.pReportId.value;
	    var subreportid	= f.pSubReportId.value;
		if(confirm("기존파일을 삭제하시겠습니까?")){
			ReportSubInfoWork.reportSubInfoFileDelete(courseid, reportid, subreportid, delFileCallback);
		}else
			return;
	}

	function delFileCallback(data){
		var result = data;
	  	if(result == '1'){
	  		var f = document.Input;
	  		f.pOldRfile.value="";
	  		f.pOldSfile.value="";
	  		f.pOldFilePath.value="";
	  		f.pOldFileSize.value="";

			$("fileDiv").innerHTML = "";
			$("fileDiv").style.display = "none";
	  	}else return;
	}
	// 첨부파일 제거 ends


	 //문제은행 항목 등록 보여주기/감추기
	 function pBankView() {
	 	var f = document.Regist;

		var autoBankYn = DWRUtil.getValue("pBankInfoYn");
		if(autoBankYn == "Y") {
			$("reportBankAutoYn").style.display = "block";
			window.resizeTo('715','710');
		} else {
			$("reportBankAutoYn").style.display = "none";
			window.resizeTo('715','680');
		}
	 }


   //서브 시험과제 등록 폼 띄우기(팝업)
   function addSubReport(){
	   var f = document.Regist;
	   var courseid = f.pCourseId.value;
	   var reportid = f.pReportId.value;

	   var loc="/ReportSubInfo.cmd?cmd=subReportWriteForm&pCourseId="+courseid+"&pReportId="+reportid+"&pMODE=ADD";
	   report = window.open(loc,"report","left=0,top=0,toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=auto,resizable=no,width=715,height=620");
    }

    //서브 시험과제 수정 폼 띄우기(팝업)
   function editSubReport(subReportId){
	   var f = document.Regist;
	   var courseid = f.pCourseId.value;
	   var reportid = f.pReportId.value;
	   var registYn = f.pReportRegistYn.value;
	   var editYn	= f.pSubReportEditYn.value;
	   if(registYn == "Y" && editYn == "Y") {
	   		alert(" 과제 정보를 등록 하였고 제출 시작일자가 현재 일자를 \n 지났으므로 수정/삭제 하실수 없습니다. ");
	   		return;
	   }
	   var loc="/ReportSubInfo.cmd?cmd=subReportWriteForm&pCourseId="+courseid+"&pReportId="+reportid+"&pSubReportId="+subReportId+"&pMODE=EDIT";
	   report = window.open(loc,"report","left=0,top=0,toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=auto,resizable=no,width=715,height=560");
    }

    // 폼체크 (report_sub_info 개별 등록 팝업 폼 체크)
	function chkForm(pMode)
	{
		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */

		var f = document.Input;

		if(isEmpty(f.pSubject.value)) {
			alert('제목을 입력하세요');
			f.pSubject.focus();
			return false;
		}

		if (getLength(f.pSubject.value) > 200) {
			alert('제목의 길이가 너무 길어 입력이 불가능합니다.(200자이내)');
			f.pSubject.focus();
			return false;
		}

		if(isEmpty(f.pContents.value)) {
			alert('문제를 입력하세요');
			return false;
		}

		if(pMode == "ADD") {
			var pBankInfoYn = ""
			if(f.pBankInfoYn[0].checked == true) {
				pBankInfoYn = "Y";
			} else {
				pBankInfoYn = "N";
			}

			if(pBankInfoYn == "Y") {
				if(f.pBankId.value == "") {
					alert('문제은행 항목을 선택해 주세요');
					f.pBankId.focus();
					return false;
				}
			}
		}
		return true;
	}

    // 서브밋
	function goSubmit(pMode){
		if(chkForm(pMode)){
			var f = document.Input;
			f.action = CONTEXTPATH+"/ReportSubInfo.cmd?cmd=reportSubInfoRegist";
			f.method = "post";
			f.submit();

			opener.autoReload();
		}else{
			return;
		}
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

	 // 폼체크 (문제은행 등록/수정/삭제 시 사용)
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


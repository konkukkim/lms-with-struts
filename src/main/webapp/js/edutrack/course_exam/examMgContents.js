	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var COURSE_ID = "";
	var EXAM_ID = "";
	var EXAM_NO = "";
	var REG_MODE = "";

	function init(systemCode,contextPath,courseId) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.COURSE_ID = courseId;

		var f = document.Input;
		this.EXAM_ID = f.pExamId.value;
		this.EXAM_NO = f.pExamNo.value;
		this.REG_MODE = f.pMODE.value;

		var examType = DWRUtil.getValue("pExamType");
		changeExamType(examType);

		if(REG_MODE == "ADD"){
			$("modButtonDiv").style.display = "none";
			$("regButtonDiv").style.display = "block";
		}else{
			$("regButtonDiv").style.display = "none";
			$("modButtonDiv").style.display = "block";
		}
	}

	function errorMessage(){
		alert("작업중 에러가 발생했습니다.\n재시도 해주세요!!!");
	}

	 // 문제은행 폼체크
	function checkFormBank(){
		var bankName = DWRUtil.getValue("pBankName");;

		if(bankName == ""){
			alert("항목명을 입력해주세요");
			new Effect.Highlight("pBankName");
			$("pBankName").focus();
			return false;
		}
		return true;
	}

	 // 폼체크
	function chkForm()
	{
		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */

		var f = document.Input;
		var examOrder = DWRUtil.getValue("pExamOrder");
		var answerUserCnt = DWRUtil.getValue("answerUserCnt");
		var examType = DWRUtil.getValue("pExamType");

		var modeName = "등록";
		if(REG_MODE != "ADD") modeName = "수정";

		if(parseInt(answerUserCnt) > 0 ){
		   alert("현재 응시자가 "+answerUserCnt+"명이 있으므로 "+modeName+"작업을 할 수 없습니다.");
		   return false;
		}

		if(isEmpty(examOrder)){
		   alert("문제번호는 필수입력항목입니다.");
		   new Effect.Highlight("pExamOrder");
		   f.pExamOrder.focus();
		   return false;
		}

		if(!isNumber(parseInt(examOrder))){
		   alert("문제번호는 숫자만 입력가능합니다.");
		   f.pExamOrder.focus();
		   return false;
		}

		if(parseInt(examOrder) < 1 || parseInt(examOrder) > 100){
		   alert("문제번호는 1 ~ 999 사이의 숫자만 입력가능합니다.");
		   f.pExamOrder.focus();
		   return;
		}

		if(isEmpty(f.pContents.value)) {
			alert('문제를 입력하세요');
			return false;
		}

		if(examType == "J"){	// 서술형

			if(isEmpty(f.pAnswerJ.value)) {
				alert('정답을 입력하세요');
				new Effect.Highlight("pAnswerJ");
				f.pAnswerJ.focus();
				return false;
			}

			if (getLength(f.pAnswerJ.value) > 2000) {
				alert('정답의 길이가 너무 길어 입력이 불가능합니다.(2000자이내)');
				new Effect.Highlight("pAnswerJ");
				f.pAnswerJ.focus();
				return false;
			}
		}else if(examType == "K"){	// 선택형
	        var checkAnswer = 0;
	        var checkExample = 0;

	        for(var i=1; i <= 5;i++) {
	        	if(f["pExample"+i].value != "") checkExample++;
	        }
	        if(checkExample == 0){
	           alert("보기를 입력해 주세요");
	           f.pExample1.focus();
	           return false;
	        }

	        if(checkExample < 2){
	           alert("보기는 두개 이상을 입력하셔야 합니다.");
	           f.pExample1.focus();
	           return false;
	        }

			var checkCnt = f.pAnswers.length;
	        for (var i = 0; i < checkCnt ; i++) {
              if(f.pAnswers[i].checked) checkAnswer++;
	        }

	        if(checkAnswer == 0){
	           alert("정답을 선택해 주세요");
	           return false;
	        }

	        var pAnswerCnt = f.pAnswerK[f.pAnswerK.selectedIndex].value;
	        if( pAnswerCnt !=   checkAnswer){
	        	alert("정답수와 선택한 정답번호 숫자가 일치하지 않습니다.");
	        	f.pAnswerK.focus();
	            return false;
	        }
		}else if(examType == "D"){	// 단답형

			if(isEmpty(f.pAnswerD.value)) {
				alert('정답을 입력하세요');
				new Effect.Highlight("pAnswerD");
				f.pAnswerD.focus();
				return false;
			}

			if (getLength(f.pAnswerD.value) > 2000) {
				alert('정답의 길이가 너무 길어 입력이 불가능합니다.(2000자이내)');
				new Effect.Highlight("pAnswerD");
				f.pAnswerD.focus();
				return false;
			}
		}else if(examType == "S"){	// OX형
			var cnt = 0;
			for(i=0;i<2;i++)
				if(f.pAnswerS[i].checked == true) cnt++;

			if(cnt == 0) {
				alert('정답을 선택하십시요');
				return false;
			}
		}

		if(REG_MODE == "ADD"){
			if(f.pAutoBankRegist[0].checked){
				if(f.pBankCode.value == ''){
					alert('문제은행에 자동 등록 하실려면 문제은행 항목을 선택하셔야 합니다.');
					return;
				}
			}
		}

		return true;
	}

	// 문제유형변경
	function changeExamType(examType){

		if(examType == 'K'){
			$("examWriteD").style.display = "none";
			$("examWriteS").style.display = "none";
			$("examWriteJ").style.display = "none";
			$("examWriteK").style.display = "block";
		}else if(examType == 'D'){
			$("examWriteS").style.display = "none";
			$("examWriteK").style.display = "none";
			$("examWriteJ").style.display = "none";
			$("examWriteD").style.display = "block";
		}else if(examType == 'S'){
			$("examWriteD").style.display = "none";
			$("examWriteK").style.display = "none";
			$("examWriteJ").style.display = "none";
			$("examWriteS").style.display = "block";
		}else if(examType == 'J'){
			$("examWriteD").style.display = "none";
			$("examWriteK").style.display = "none";
			$("examWriteS").style.display = "none";
			$("examWriteJ").style.display = "block";
		}
	}

	// 문제은행항목 보여주기
	function showBank(viewStyle){
		if(viewStyle == "none"){
			$("pBankName").value = "";
			$("pBankCode").value = "";
		}else{
			examBankInfoSelectList();
		}
		$("examBankInfoDiv").style.display = viewStyle;
	}

	// 문제은행 항목 셀렉트박스 초기화
	function examBankInfoSelectList(){
		closeExamBankInfoWrite();
		ExamBankWork.examBankInfoSelectList(COURSE_ID, examBankInfoSelectListCallback);
	}

	// 문제은행 항목 셀렉트박스 표시
	function examBankInfoSelectListCallback(data){
		DWRUtil.removeAllOptions("pBankCode");
		var defaultSelect = {"":"--항목선택--"};
		DWRUtil.addOptions("pBankCode", defaultSelect);
		DWRUtil.addOptions("pBankCode", data);
	}

	// 문제은행 항목 생성 열기
	function manageExamBankInfo(){
		$("examBankInfoSelectDiv").style.display = "none";
		Effect.Appear("examBankWriteDiv");
	}

	// 문제은행 항목 등록/수정
	function registExamBankInfo(regMode){
		if(checkFormBank()){
			var bankCode = "";
			var bankName = DWRUtil.getValue("pBankName");
			ExamBankWork.examBankInfoRegist(regMode,COURSE_ID,bankCode,bankName,{
				callback:function(data) {
				  	if(data != '0'){
				  		closeExamBankInfoWrite();
					  	examBankInfoSelectList();
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

	// 첨부파일 제거 start
	function delFile(){
		if(confirm("기존파일을 삭제하시겠습니까?")){
			var examOrder = DWRUtil.getValue("pExamOrder");
			ExamContentsWork.examContentsFileDelete(COURSE_ID, EXAM_ID, EXAM_NO, examOrder, delFileCallback);
		}else
			return;
	}

	function delFileCallback(data){
	  	if(data > 0){
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



	// 목록으로 이동
    function goList(){
        var f = document.Input;
        var examId = f.pExamId.value;
        var loc = CONTEXTPATH+"/ExamContents.cmd?cmd=examContentsList&pCourseId="+COURSE_ID+"&pExamId="+examId;

        document.location = loc;
    }

    // 문제 삭제
    function goDelete(){

        var f = document.Input;
        var answerUserCnt = f.answerUserCnt.value;
        var examOrder = f.pExamOrder.value;
        if(parseInt(answerUserCnt) > 0 ){
		   alert("현재 응시자가 "+answerUserCnt+"명이 있으므로 삭제작업을 할 수 없습니다.");
		   return;
		}else{
	    	var chkDel =confirm('문제를 삭제 하시겠습니까?');
	        if(chkDel){
			    var loc = CONTEXTPATH+"/ExamContents.cmd?cmd=examContentsDelete&pCourseId="+COURSE_ID+"&pExamId="+EXAM_ID+"&pExamNo="+EXAM_NO+"&pExamOrder="+examOrder;
				document.location = loc;
			 }
		}
    }

	// 파일다운로드
	function fileDownload(rfilename, sfilename, filepath, filesize){
		var loc=CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
		hiddenFrame.document.location = loc;
	}

	// 서브밋
	function goSubmit(){
		if(chkForm()){
			var f = document.Input;
			f.action = CONTEXTPATH+"/ExamContents.cmd?cmd=examContentsRegist&pCourseId="+COURSE_ID+"&pExamId="+EXAM_ID;
			f.method = "post";
			f.submit();
		}else{
			return;
		}
	}

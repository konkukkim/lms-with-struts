	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var COURSE_ID = "";
	var BANK_CODE = "";
	var EXAM_NO = "";
	var REG_MODE = "";

	function init(systemCode,contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;

		var f = document.Input;
		this.COURSE_ID = f.pCourseId.value;
		this.BANK_CODE = f.pBankCode.value;
		this.EXAM_NO = f.pExamNo.value;
		this.REG_MODE = f.pMODE.value;

		var examType = f.pExamType[f.pExamType.selectedIndex].value;
		changeExamType(examType);

		if(REG_MODE == "ADD"){
			$("modButtonDiv").style.display = "none";
			$("regButtonDiv").style.display = "block";
		}else if(REG_MODE == "EDIT"){
			$("regButtonDiv").style.display = "none";
			$("modButtonDiv").style.display = "block";
		}
	}

	function errorMessage(){
		alert("작업중 에러가 발생했습니다.\n재시도 해주세요!!!");
	}

	// 길이체크
	function getLength(str) {
		return (str.length + (escape(str) + "/%u").match(/%u/g).length-1);
	}

	 // 폼체크
	function chkForm()
	{
		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */

		var f = document.Input;
		var examType = f.pExamType[f.pExamType.selectedIndex].value;

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

	// 첨부파일 제거 start
	function delFile(){
		if(confirm("기존파일을 삭제하시겠습니까?")){
			ExamBankWork.examBankContentsFileDelete(COURSE_ID, BANK_CODE, EXAM_NO, delFileCallback);
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

	// 목록으로 이동
    function goList(){
        var f = document.Input;
        var gubun = f.pGubun.value;
        var loc = CONTEXTPATH+"/ExamBank.cmd?cmd=examBankContentsList&pCourseId="+COURSE_ID+"&pBankCode="+BANK_CODE+"&pGubun="+gubun;

        document.location = loc;
    }

    // 문제 삭제
    function goDelete(){
    	var chkDel =confirm('문제를 삭제 하시겠습니까?');
        if(chkDel){
		    var loc = CONTEXTPATH+"/ExamBank.cmd?cmd=examBankContentsDelete&pCourseId="+COURSE_ID+"&pBankCode="+BANK_CODE+"&pExamNo="+EXAM_NO;
			document.location = loc;
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
			f.action = CONTEXTPATH+"/ExamBank.cmd?cmd=examBankContentsRegist&pCourseId="+COURSE_ID+"&pBankCode="+BANK_CODE;
			f.method = "post";
			f.submit();
		}else{
			return;
		}
	}


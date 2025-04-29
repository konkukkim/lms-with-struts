	// ���񸮽�Ʈ
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
		alert("�۾��� ������ �߻��߽��ϴ�.\n��õ� ���ּ���!!!");
	}

	// ����üũ
	function getLength(str) {
		return (str.length + (escape(str) + "/%u").match(/%u/g).length-1);
	}

	 // ��üũ
	function chkForm()
	{
		/*  WEAS ���� ��ũ��Ʈ3(����) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS ���� ��ũ��Ʈ3(��) */

		var f = document.Input;
		var examType = f.pExamType[f.pExamType.selectedIndex].value;

		if(isEmpty(f.pContents.value)) {
			alert('������ �Է��ϼ���');
			return false;
		}

		if(examType == "J"){	// ������

			if(isEmpty(f.pAnswerJ.value)) {
				alert('������ �Է��ϼ���');
				new Effect.Highlight("pAnswerJ");
				f.pAnswerJ.focus();
				return false;
			}

			if (getLength(f.pAnswerJ.value) > 2000) {
				alert('������ ���̰� �ʹ� ��� �Է��� �Ұ����մϴ�.(2000���̳�)');
				new Effect.Highlight("pAnswerJ");
				f.pAnswerJ.focus();
				return false;
			}
		}else if(examType == "K"){	// ������
	        var checkAnswer = 0;
	        var checkExample = 0;

	        for(var i=1; i <= 5;i++) {
	        	if(f["pExample"+i].value != "") checkExample++;
	        }
	        if(checkExample == 0){
	           alert("���⸦ �Է��� �ּ���");
	           f.pExample1.focus();
	           return false;
	        }

	        if(checkExample < 2){
	           alert("����� �ΰ� �̻��� �Է��ϼž� �մϴ�.");
	           f.pExample1.focus();
	           return false;
	        }

			var checkCnt = f.pAnswers.length;
	        for (var i = 0; i < checkCnt ; i++) {
              if(f.pAnswers[i].checked) checkAnswer++;
	        }

	        if(checkAnswer == 0){
	           alert("������ ������ �ּ���");
	           return false;
	        }

	        var pAnswerCnt = f.pAnswerK[f.pAnswerK.selectedIndex].value;
	        if( pAnswerCnt !=   checkAnswer){
	        	alert("������� ������ �����ȣ ���ڰ� ��ġ���� �ʽ��ϴ�.");
	        	f.pAnswerK.focus();
	            return false;
	        }
		}else if(examType == "D"){	// �ܴ���

			if(isEmpty(f.pAnswerD.value)) {
				alert('������ �Է��ϼ���');
				new Effect.Highlight("pAnswerD");
				f.pAnswerD.focus();
				return false;
			}

			if (getLength(f.pAnswerD.value) > 2000) {
				alert('������ ���̰� �ʹ� ��� �Է��� �Ұ����մϴ�.(2000���̳�)');
				new Effect.Highlight("pAnswerD");
				f.pAnswerD.focus();
				return false;
			}
		}else if(examType == "S"){	// OX��
			var cnt = 0;
			for(i=0;i<2;i++)
				if(f.pAnswerS[i].checked == true) cnt++;

			if(cnt == 0) {
				alert('������ �����Ͻʽÿ�');
				return false;
			}
		}
		return true;
	}

	// ������������
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

	// ÷������ ���� start
	function delFile(){
		if(confirm("���������� �����Ͻðڽ��ϱ�?")){
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
	// ÷������ ���� ends

	// ������� �̵�
    function goList(){
        var f = document.Input;
        var gubun = f.pGubun.value;
        var loc = CONTEXTPATH+"/ExamBank.cmd?cmd=examBankContentsList&pCourseId="+COURSE_ID+"&pBankCode="+BANK_CODE+"&pGubun="+gubun;

        document.location = loc;
    }

    // ���� ����
    function goDelete(){
    	var chkDel =confirm('������ ���� �Ͻðڽ��ϱ�?');
        if(chkDel){
		    var loc = CONTEXTPATH+"/ExamBank.cmd?cmd=examBankContentsDelete&pCourseId="+COURSE_ID+"&pBankCode="+BANK_CODE+"&pExamNo="+EXAM_NO;
			document.location = loc;
		 }
    }

	// ���ϴٿ�ε�
	function fileDownload(rfilename, sfilename, filepath, filesize){
		var loc=CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
		hiddenFrame.document.location = loc;
	}

	// �����
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


	// ���񸮽�Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var CURRI_CODE = "";
	var CURRI_YEAR = "";
	var CURRI_TERM = "";
	var QUIZ_TYPE = "";

	function init(systemCode,contextPath,quizType) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.QUIZ_TYPE = quizType;

		var f = document.Input;
		this.CURRI_CODE = f.pCurriCode.value;
		this.CURRI_YEAR = f.pCurriYear.value;
		this.CURRI_TERM = f.pCurriTerm.value;
		change_type(quizType);
	}


	// ÷������ ���� start
	function delFile(){
		if(confirm("���������� �����Ͻðڽ��ϱ�?")){
			var f = document.Input;

			var courseId = f.pCourseId.value;
			var contentsId = f.pContentsId.value;
			var quizOrder = f.pQuizOrder.value;

			CurriQuizWork.curriQuizFileDelete(CURRI_CODE, CURRI_YEAR, CURRI_TERM, courseId, contentsId, quizOrder, delFileCallback);
		}else
			return;
	}

	function delFileCallback(data){
		var result = data;
	  	if(result == '1'){
	  		var f = document.Input;
	  		f.pOldrFileName.value="";
	  		f.pOldsFileName.value="";
	  		f.pOldFilePath.value="";
	  		f.pOldFileSize.value="";

			$("fileDiv").innerHTML = "";
			$("fileDiv").style.display = "none";
	  	}else return;
	}
	// ÷������ ���� ends

	function goSubmit(){
		var f = document.Input;
		if(chkForm()){
			var courseId = f.pCourseId.value;
			var contentsId = f.pContentsId.value;
			var quizOrder = f.pQuizOrder.value;

			f.action = CONTEXTPATH+"/CurriQuiz.cmd?cmd=curriQuizRegist&pRegMode=EDIT&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM+"&pCourseId="+courseId+"&pContentsId="+contentsId+"&pQuizOrder="+quizOrder;
			f.method = "post";
			f.submit();
		}else{
			return;
		}
	}

	function goList(){
		var f = document.Input;
		var courseId = f.pCourseId.value;
		var contentsId = f.pContentsId.value;

		document.location.href = CONTEXTPATH+"/CurriQuiz.cmd?cmd=curriQuizList&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM+"&pCourseId="+courseId+"&pContentsId="+contentsId;
	}

	function change_type(quizType){

		if(quizType == 'K'){
			$("quizWriteD").style.display = "none";
			$("quizWriteS").style.display = "none";
			$("quizWriteK").style.display = "block";
		}else if(quizType == 'D'){
			$("quizWriteS").style.display = "none";
			$("quizWriteK").style.display = "none";
			$("quizWriteD").style.display = "block";
		}else if(quizType == 'S'){
			$("quizWriteD").style.display = "none";
			$("quizWriteK").style.display = "none";
			$("quizWriteS").style.display = "block";
		}
	}

    function goDelete(){
		if(confirm("������ �����Ͻðڽ��ϱ�?")){
			var f = document.Input;
			var courseId = f.pCourseId.value;
			var contentsId = f.pContentsId.value;
			var quizOrder = f.pQuizOrder.value;

	       	document.location.href=CONTEXTPATH+"/CurriQuiz.cmd?cmd=curriQuizDelete&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM+"&pCourseId="+courseId+"&pContentsId="+contentsId+"&pQuizOrder="+quizOrder;
		}else
			return;
    }

	function isEmpty(data)
	{
		if(data != null){
			for ( var i = 0 ; i < data.length ; i++ ) {
				if ( data.substring( i, i+1 ) != ' ' )
					return false;
			}
		}
		return true;
	}

	function getLength(str) {
		return (str.length + (escape(str) + "/%u").match(/%u/g).length-1);
	}

	function chkForm()
	{
		/*  WEAS ���� ��ũ��Ʈ3(����) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS ���� ��ũ��Ʈ3(��) */

		var f = document.Input;
		var quizType = f.pQuizType[f.pQuizType.selectedIndex].value;
		if(quizType == "K"){
			if(isEmpty(f.pContents.value)) {
				alert('������ �Է��ϼ���');
				return false;
			}
			for(i=1;i<=4;i++){
				if(isEmpty(f["pExample"+i].value)) {
					alert('������ ���⿹���� �ʼ�4����  �Դϴ�');
					f["pExample"+i].focus();
					return false;
				}
			}
			var cnt = 0;
			for(i=0;i<5;i++){
				if(f.pAnswerK[i].checked == true)
					cnt++;
			}

			if(cnt == 0) {
				alert('������ �����Ͻʽÿ�');
				return false;
			}
		}else if(quizType == "D"){//--�ܴ���
			if(isEmpty(f.pContents.value)) {
				alert('������ �Է��ϼ���');
				return false;
			}

			if(isEmpty(f.pAnswerD.value)) {
				alert('������ �Է��ϼ���');
				f.pAnswerD.focus();
				return false;
			}

			if (getLength(f.pAnswerD.value) > 2000) {
				alert('������ ���̰� �ʹ� ��� �Է��� �Ұ����մϴ�.(2000���̳�)');
				f.pAnswerD.focus();
				return false;
			}
		}else if(quizType == "S"){//--OX��
			if(isEmpty(f.pContents.value)) {
				alert('������ �Է��ϼ���');
				return false;
			}
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

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc = CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }

	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}
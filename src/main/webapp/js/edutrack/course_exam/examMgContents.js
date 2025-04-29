	// ���񸮽�Ʈ
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
		alert("�۾��� ������ �߻��߽��ϴ�.\n��õ� ���ּ���!!!");
	}

	 // �������� ��üũ
	function checkFormBank(){
		var bankName = DWRUtil.getValue("pBankName");;

		if(bankName == ""){
			alert("�׸���� �Է����ּ���");
			new Effect.Highlight("pBankName");
			$("pBankName").focus();
			return false;
		}
		return true;
	}

	 // ��üũ
	function chkForm()
	{
		/*  WEAS ���� ��ũ��Ʈ3(����) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS ���� ��ũ��Ʈ3(��) */

		var f = document.Input;
		var examOrder = DWRUtil.getValue("pExamOrder");
		var answerUserCnt = DWRUtil.getValue("answerUserCnt");
		var examType = DWRUtil.getValue("pExamType");

		var modeName = "���";
		if(REG_MODE != "ADD") modeName = "����";

		if(parseInt(answerUserCnt) > 0 ){
		   alert("���� �����ڰ� "+answerUserCnt+"���� �����Ƿ� "+modeName+"�۾��� �� �� �����ϴ�.");
		   return false;
		}

		if(isEmpty(examOrder)){
		   alert("������ȣ�� �ʼ��Է��׸��Դϴ�.");
		   new Effect.Highlight("pExamOrder");
		   f.pExamOrder.focus();
		   return false;
		}

		if(!isNumber(parseInt(examOrder))){
		   alert("������ȣ�� ���ڸ� �Է°����մϴ�.");
		   f.pExamOrder.focus();
		   return false;
		}

		if(parseInt(examOrder) < 1 || parseInt(examOrder) > 100){
		   alert("������ȣ�� 1 ~ 999 ������ ���ڸ� �Է°����մϴ�.");
		   f.pExamOrder.focus();
		   return;
		}

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

		if(REG_MODE == "ADD"){
			if(f.pAutoBankRegist[0].checked){
				if(f.pBankCode.value == ''){
					alert('�������࿡ �ڵ� ��� �ϽǷ��� �������� �׸��� �����ϼž� �մϴ�.');
					return;
				}
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

	// ���������׸� �����ֱ�
	function showBank(viewStyle){
		if(viewStyle == "none"){
			$("pBankName").value = "";
			$("pBankCode").value = "";
		}else{
			examBankInfoSelectList();
		}
		$("examBankInfoDiv").style.display = viewStyle;
	}

	// �������� �׸� ����Ʈ�ڽ� �ʱ�ȭ
	function examBankInfoSelectList(){
		closeExamBankInfoWrite();
		ExamBankWork.examBankInfoSelectList(COURSE_ID, examBankInfoSelectListCallback);
	}

	// �������� �׸� ����Ʈ�ڽ� ǥ��
	function examBankInfoSelectListCallback(data){
		DWRUtil.removeAllOptions("pBankCode");
		var defaultSelect = {"":"--�׸���--"};
		DWRUtil.addOptions("pBankCode", defaultSelect);
		DWRUtil.addOptions("pBankCode", data);
	}

	// �������� �׸� ���� ����
	function manageExamBankInfo(){
		$("examBankInfoSelectDiv").style.display = "none";
		Effect.Appear("examBankWriteDiv");
	}

	// �������� �׸� ���/����
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

	// �������� �׸� ���� �ݱ�
	function closeExamBankInfoWrite(){
		$("pBankName").value = "";
		$("examBankWriteDiv").style.display = "none";
		Effect.Appear("examBankInfoSelectDiv");
	}

	// ÷������ ���� start
	function delFile(){
		if(confirm("���������� �����Ͻðڽ��ϱ�?")){
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
	// ÷������ ���� ends



	// ������� �̵�
    function goList(){
        var f = document.Input;
        var examId = f.pExamId.value;
        var loc = CONTEXTPATH+"/ExamContents.cmd?cmd=examContentsList&pCourseId="+COURSE_ID+"&pExamId="+examId;

        document.location = loc;
    }

    // ���� ����
    function goDelete(){

        var f = document.Input;
        var answerUserCnt = f.answerUserCnt.value;
        var examOrder = f.pExamOrder.value;
        if(parseInt(answerUserCnt) > 0 ){
		   alert("���� �����ڰ� "+answerUserCnt+"���� �����Ƿ� �����۾��� �� �� �����ϴ�.");
		   return;
		}else{
	    	var chkDel =confirm('������ ���� �Ͻðڽ��ϱ�?');
	        if(chkDel){
			    var loc = CONTEXTPATH+"/ExamContents.cmd?cmd=examContentsDelete&pCourseId="+COURSE_ID+"&pExamId="+EXAM_ID+"&pExamNo="+EXAM_NO+"&pExamOrder="+examOrder;
				document.location = loc;
			 }
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
			f.action = CONTEXTPATH+"/ExamContents.cmd?cmd=examContentsRegist&pCourseId="+COURSE_ID+"&pExamId="+EXAM_ID;
			f.method = "post";
			f.submit();
		}else{
			return;
		}
	}

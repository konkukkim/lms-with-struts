	// ���񸮽�Ʈ
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
		alert("�۾��� ������ �߻��߽��ϴ�.\n��õ� ���ּ���!!!");
	}

	 // ��üũ
	function formCheck(){
		var bankName = DWRUtil.getValue("pBankName");;

		if(bankName == ""){
			alert("�׸���� �Է����ּ���");
			new Effect.Highlight("pBankName");
			$("pBankName").focus();
			return false;
		}
		return true;
	}

	// �������� �׸� ����Ʈ�ڽ� �ʱ�ȭ
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

	// �������� �׸� ����Ʈ�ڽ� ǥ��
	function examBankInfoSelectListCallback(data){
		DWRUtil.removeAllOptions("pBankCode");
		var defaultSelect = {"":"--�׸���--"};
		DWRUtil.addOptions("pBankCode", defaultSelect);
		DWRUtil.addOptions("pBankCode", data);

		setOptionSelected($("pBankCode"),BANK_CODE);
		autoReload();
	}

	// �������� �׸� ���� ����
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
				alert("�׸��� �����ϼ���");
				$("pBankCode").focus();
			}
		}else if(regMode == "DEL"){
			var bankCode = DWRUtil.getValue("pBankCode");
			if(bankCode != null && bankCode != ""){
				var st = confirm('�׸� �ش��ϴ� ��� ������ ���� �˴ϴ�.\n\n �׸��� �����Ͻðڽ��ϱ�?');
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
				alert("�׸��� �����ϼ���");
				$("pBankCode").focus();
			}
		}
	}

	// �������� �׸� ���/����
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

	// �������� �׸� ���� �ݱ�
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

	// ��������Ʈ �ѷ��ֱ�
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
			  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">��ϵ� ������ �����ϴ�.</td></tr>";
			}else{
			  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">�׸��� �����ϼ���.</td></tr>";
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
					fileDownLink = "<a href=\"javascript:fileDownload('"+rfileName+"','"+sfileName+"','"+filePath+"','"+filePath+"');\">����</a>";
				}else{
					fileDownLink = "����";
				}

				if(examType == "J") examTypeName	=	"������";
				else if(examType == "F") examTypeName	=	"������";
				else if(examType == "K") examTypeName	=	"������";
				else if(examType == "D") examTypeName	=	"�ܴ���";
				else if(examType == "S") examTypeName	=	"OX��";

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
			alert("������ ������ �ֽʽÿ�.");
			$("pCourseId").focus();
			return;
		}

		if(BANK_CODE == ""){
			alert("�׸���� ������ �ֽʽÿ�.");
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
			alert("������ ������ �ֽʽÿ�.");
			$("pCourseId").focus();
			return;
		}

		if(BANK_CODE == ""){
			alert("�׸���� ������ �ֽʽÿ�.");
			$("pBankCode").focus();
			return;
		}

		document.location.href = CONTEXTPATH+"/ExamBank.cmd?cmd=examBankContentsWrite&pMODE=EDIT&pGubun=M&pCourseId="+COURSE_ID+"&pBankCode="+BANK_CODE+"&pExamType="+examType+"&pExamNo="+examNo;
    }

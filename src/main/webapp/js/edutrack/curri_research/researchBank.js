	// ���񸮽�Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var USER_ID = "";
	var RES_ID = "";
	var RES_NO = "";

	function init(systemCode,contextPath,userId,resId) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.USER_ID = userId;
		this.RES_ID = resId;

		researchBankInfoSelectList('Y');
	}

	function errorMessage(){
		alert("�۾��� ������ �߻��߽��ϴ�.\n��õ� ���ּ���!!!");
	}

	 // ��üũ - ��������
	function formCheck(){
		var subject = DWRUtil.getValue("pSubject");

		if(subject == ""){
			alert("�׸���� �Է����ּ���");
			new Effect.Highlight("pSubject");
			$("pSubject").focus();
			return false;
		}
		return true;
	}

	 // ��üũ - ����
	function formCheckContents(){
		var f = document.f;
		var contentsType = DWRUtil.getValue("pContentsType");

		if(isEmpty(f.pContents.value)) {
			alert('������ �Է��ϼ���');
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
	           alert("���⸦ �Է��� �ּ���");
	           f.pExample1.focus();
	           return false;
	        }

	        if(checkExample < 2){
	           alert("����� �ϳ� �̻��� �Է��ϼž� �մϴ�.");
	           f.pExample1.focus();
	           return false;
	        }
   		}
		return true;
	}

	// �߰��� �̵�
	function goAdd() {
		if(RES_ID == ""){
			alert("�׸���� ������ �ֽʽÿ�.");
			$("pResId").focus();
			return;
		}

		initResBkContentsWrite();
		$("researchBankContentsListDIV").style.display = "none";
		$("researchBankContentsWriteDIV").style.display = "block";
		changeContentsType('K');
		$("modButtonContentsDiv").style.display = "none";
		$("regButtonContentsDiv").style.display = "block";
	}

	// �������� �̵�
	function goEdit(resNo) {
		initResBkContentsWrite();
		$("researchBankContentsListDIV").style.display = "none";
		RES_NO = resNo;
		ResearchBankWork.researchBankContentsInfo(RES_ID, RES_NO, goEditCallback);
	}

	// ������� �̵�
	function goList() {
		$("researchBankContentsWriteDIV").style.display = "none";
		$("researchBankContentsListDIV").style.display = "block";
	}

	// ������������
	function changeContentsType(contentsType) {
		if(contentsType == "K" )
			$("researchWriteK").style.display = "block";
		else
			$("researchWriteK").style.display = "none";
		setOptionSelected($("pContentsType"),contentsType);
	}

	// ȭ���ʱ�ȭ
	function initResBkContentsWrite(){
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

	// �������� ����
	function goEditCallback(resBkContentsDto) {
	  	if(resBkContentsDto != null){
	  		var f = document.f;
			f.pContents.value = resBkContentsDto.contents;
	  		var contentsType = resBkContentsDto.contentsType;
			if(contentsType == "K"){
				f.pExample1.value = resBkContentsDto.example1;
				f.pExample2.value = resBkContentsDto.example2;
				f.pExample3.value = resBkContentsDto.example3;
				f.pExample4.value = resBkContentsDto.example4;
				f.pExample5.value = resBkContentsDto.example5;
				f.pExample6.value = resBkContentsDto.example6;
				f.pExample7.value = resBkContentsDto.example7;
				f.pExample8.value = resBkContentsDto.example8;
				f.pExample9.value = resBkContentsDto.example9;
				f.pExample10.value = resBkContentsDto.example10;
				var exampleNum = resBkContentsDto.exampleNum;

			    for(i=0;i<f.pExampleNum.length;i++){
		   	   	    if(exampleNum == f.pExampleNum[i].value){
			   			f.pExampleNum[i].checked=true;
			   		}
			    }
			}

			setOptionSelected($("pContentsType"),contentsType);
			$("researchBankContentsWriteDIV").style.display = "block";
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

	// �������� �׸� ����Ʈ�ڽ� �ʱ�ȭ
	function researchBankInfoSelectList(selected){
		if(selected != "Y")
			RES_ID = "";

		closeResearchBankInfoWrite();
		ResearchBankWork.researchBankInfoSelectList(USER_ID, 'N', researchBankInfoSelectListCallback);
	}

	// �������� �׸� ����Ʈ�ڽ� ǥ��
	function researchBankInfoSelectListCallback(data){
		DWRUtil.removeAllOptions("pResId");
		var defaultSelect = {"":"--�׸���--"};
		DWRUtil.addOptions("pResId", defaultSelect);
		DWRUtil.addOptions("pResId", data);

		setOptionSelected($("pResId"),RES_ID);
		autoReload();
	}

	// �������� �׸� ���� ����
	function manageResearchBankInfo(regMode){
		if(regMode == "write"){
			$("modButtonDiv").style.display = "none";
			$("regButtonDiv").style.display = "block";
			$("researchBankInfoSelectDiv").style.display = "none";
			Effect.Appear("researchBankWriteDiv");
		}else if(regMode == "edit"){
			var resId = DWRUtil.getValue("pResId");
			if(resId != null && resId != ""){
				ResearchBankWork.researchBankInfo(resId,{
					callback:function(resBkInfoDto) {
					  	if(resBkInfoDto != null){
					  		var subject = resBkInfoDto.subject;
					  		var shareYn = resBkInfoDto.shareYn;
		  					var f = document.f;
						    for(i=0;i<f.pShareYn.length;i++){
					   	   	    if(shareYn == f.pShareYn[i].value){
						   			f.pShareYn[i].checked=true;
						   		}
						    }

							$("regButtonDiv").style.display = "none";
							$("modButtonDiv").style.display = "block";
					  		f.pSubject.value = subject;
							$("researchBankInfoSelectDiv").style.display = "none";
							Effect.Appear("researchBankWriteDiv");
					  	}else{
					  		return;
					  	}
					}
				});
			}else{
				alert("�׸��� �����ϼ���");
				$("pResId").focus();
			}
		}else if(regMode == "delete"){
			var resId = DWRUtil.getValue("pResId");
			if(resId != null && resId != ""){
				var st = confirm('�׸� �ش��ϴ� ��� ������ ���� �˴ϴ�.\n\n �׸��� �����Ͻðڽ��ϱ�?');
				if (st == true) {
					ResearchBankWork.researchBankInfoRegist(regMode,resId,"","",{
						callback:function(data) {
						  	if(data != '0'){
						  		RES_ID = "";
							  	researchBankInfoSelectList('N');
						  	}else{
						  		return;
						  	}
						}
					});
				}
			}else{
				alert("�׸��� �����ϼ���");
				$("pResId").focus();
			}
		}
	}

	// �������� �׸� ���/����
	function registResearchBankInfo(regMode){
		var f = document.f;
		if(formCheck()){
			var resId = DWRUtil.getValue("pResId");
			var subject = DWRUtil.getValue("pSubject");
		   	var shareYn = "N";
		  	for(i=0;i<f.pShareYn.length;i++){
	   	   	   if(f.pShareYn[i].checked == true){
		   		   shareYn = f.pShareYn[i].value;
		   	   }
		   	}

			ResearchBankWork.researchBankInfoRegist(regMode,resId,subject,shareYn,{
				callback:function(data) {
				  	if(data != '0'){
				  		if(regMode == "write"){
					  		RES_ID = "";
					  		closeResearchBankInfoWrite();
						  	researchBankInfoSelectList('N');
					  	}else if(regMode == "edit"){
					  		RES_ID = resId;
					  		closeResearchBankInfoWrite();
						  	researchBankInfoSelectList('Y');
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
	function closeResearchBankInfoWrite(){
		var f = document.f;
		f.pSubject.value = "";
		$("researchBankWriteDiv").style.display = "none";
		Effect.Appear("researchBankInfoSelectDiv");
	}

	function autoReload(){
		RES_ID = DWRUtil.getValue("pResId");
		ResearchBankWork.researchBankContentsListAuto(RES_ID, autoReloadCallback);
	}

	// ��������Ʈ �ѷ��ֱ�
	function autoReloadCallback(data){
		// ȭ����ȯ
		$("researchBankContentsWriteDIV").style.display = "none";
		$("researchBankContentsListDIV").style.display = "block";

		// ����Ÿ ��� ����
		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var objStr = "<table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
	  		if(RES_ID != ""){
			  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">��ϵ� ������ �����ϴ�.</td></tr>";
			}else{
			  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">�׸��� �����ϼ���.</td></tr>";
			}
	  	}else{
	    	var contentsType = "";
	    	var resNo = "";
	    	var contents = "";
	    	var contentsTypeName = "";

		  	for(i=0;i<rowLength;i++){
    			var researchBkContentsDTO = data[i];

				contentsType = researchBkContentsDTO.contentsType;
				resNo = researchBkContentsDTO.resNo;
				contents = researchBkContentsDTO.contents;

				if(contentsType == "J") contentsTypeName	=	"������";
				else if(contentsType == "F") contentsTypeName	=	"������";
				else if(contentsType == "K") contentsTypeName	=	"������";
				else if(contentsType == "D") contentsTypeName	=	"�ܴ���";
				else if(contentsType == "S") contentsTypeName	=	"OX��";

			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
			    	+ "<td width='70' class=\"s_tab04_cen\">"+allItemCnt+"</td>"
			    	+ "<td></td><td width='498' class=\"s_tab04\">"
			    	+ "<a href=\"javascript:goEdit('"+resNo+"');\">"+contents+"</a></td>"
					+ "<td></td><td  width='100' class=\"s_tab04_cen\">"+contentsTypeName+"</td>"
			    	+ "</tr>";

				if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    allItemCnt--;
			}
		}
		objStr += "</table>";

		$("researchBankContentsList").innerHTML = objStr;
		$("researchBankContentsList").style.display = "block";
	 }


	// ���� �Է�/����
	function registResearchBkContents(regMode){
		if(formCheckContents()){
			var resBkContetsDto = new Object();
			var f = document.f;
			var contentsType = DWRUtil.getValue("pContentsType");
			resBkContetsDto.resId = RES_ID;
			resBkContetsDto.resNo = RES_NO;
			resBkContetsDto.contentsType = DWRUtil.getValue("pContentsType");
			resBkContetsDto.contents = f.pContents.value;
			if(contentsType == "K"){
				resBkContetsDto.example1 = f.pExample1.value;
				resBkContetsDto.example2 = f.pExample2.value;
				resBkContetsDto.example3 = f.pExample3.value;
				resBkContetsDto.example4 = f.pExample4.value;
				resBkContetsDto.example5 = f.pExample5.value;
				resBkContetsDto.example6 = f.pExample6.value;
				resBkContetsDto.example7 = f.pExample7.value;
				resBkContetsDto.example8 = f.pExample8.value;
				resBkContetsDto.example9 = f.pExample9.value;
				resBkContetsDto.example10 = f.pExample10.value;

			   	var exampleNum = "O";
			  	for(i=0;i<f.pExampleNum.length;i++){
		   	   	   if(f.pExampleNum[i].checked == true){
			   		   exampleNum = f.pExampleNum[i].value;
			   	   }
			   	}
				resBkContetsDto.exampleNum = exampleNum;
			}

			ResearchBankWork.researchBankContentsRegist(resBkContetsDto, regMode,{
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

	// ���� ����
	function deleteResearchBkContents(regMode){
		if(RES_NO != null && RES_NO != ""){
			var st = confirm('������ �����Ͻðڽ��ϱ�?');
			if(st){
				var resBkContetsDto = new Object();
				resBkContetsDto.resId = RES_ID;
				resBkContetsDto.resNo = RES_NO;
				ResearchBankWork.researchBankContentsRegist(resBkContetsDto, "delete",{
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

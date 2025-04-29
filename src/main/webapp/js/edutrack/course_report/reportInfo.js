	// ���񸮽�Ʈ
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

	//���� �ڵ���� ����
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

	//�������� �׸� ����Ʈ �ڽ�(���� �ڵ� ��Ͻ�)
	function reportBankSelectList() {
		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId != null && tmpCourseId != ""){
			COURSE_ID = DWRUtil.getValue("pCourseId");
		}
		ReportBankWork.reportBankInfoSelectList(COURSE_ID, reportBankSelectListCallback);
	}

	// �������� �׸� ����Ʈ�ڽ� ǥ��(���� �ڵ� ��Ͻ� ���)
	function reportBankSelectListCallback(data){
		DWRUtil.removeAllOptions("pBankId");
		DWRUtil.addOptions("pBankId", data);
	}

	//��üũ & �����
	function SubmitCheck() {
		var f = document.Regist;

		if(!validate(f)) return;

		/*  WEAS ���� ��ũ��Ʈ3(����) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS ���� ��ũ��Ʈ3(��) */

		if(isEmpty(f.pContents.value)) {
			alert('���������� �Է��ϼ���');
			return;
		}

	    var st_reportterm = f.year11.value + f.month11.value + f.day11.value + f.hour11.value + f.minute11.value + "00";
        f.reportTerm1.value = st_reportterm;

	    var ed_reportterm = f.year12.value + f.month12.value + f.day12.value + f.hour12.value + f.minute12.value + "00";
        f.reportTerm2.value = ed_reportterm;

	    var st_reportresult = f.year21.value + f.month21.value + f.day21.value + f.hour21.value + f.minute21.value + "00";
        f.reportResult1.value = st_reportresult;

	     // ���⿬���� ��ȿ�� üũ
        if(parseFloat(ed_reportterm) > parseFloat(st_reportresult)) {
           alert("ü�⿬�����ڴ� ü���������� ���Ŀ� �����̵Ǿ�� �մϴ�.");
           return;
        }
		f.submit();
    }

    //���� ����
    function goDelete(CONTEXTPATH){
    	var chkDel =confirm('���� ���� �� ���� ���� ������ �����˴ϴ�.\n���������� ���� �Ͻðڽ��ϱ�?');
    	var f = document.Regist;

        var courseid = f.pCourseId.value;
        var reportid = f.pReportId.value;
        if(chkDel){
		    var loc = CONTEXTPATH+"/ReportAdmin.cmd?cmd=reportDelete&pCourseId="+courseid+"&pReportId="+reportid;
			document.location = loc;
		 }
    }

    //���� ���� ����
    function goSubDelete(CONTEXTPATH){
    	var chkDel = confirm('���� ���������� ���� �Ͻðڽ��ϱ�?');
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

    //���
    function goCancel(){
        var f = document.Regist;

        var courseid = f.pCourseId.value;

        var loc="/ReportAdmin.cmd?cmd=reportList&pCourseId="+courseid;
        document.location = loc;
    }

    //����Ʈ ��������
    function autoReload(){
		ReportSubInfoWork.reportSubInfoListAuto(COURSE_ID, REPORT_ID, autoReloadCallback);
	}

	// �����������Ʈ �ѷ��ֱ�
	function autoReloadCallback(data){

		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var objStr = "<table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
			objStr += "<tr><td colspan='9' align='center' height='50' bgcolor='#ffffff'>�� ���� ����Ʈ�� �����ϴ�.</td></tr>"
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
					fileDownLink = "<a href=\"javascript:fileDownload('"+rfileName+"','"+sfileName+"','"+filePath+"','"+filePath+"');\">����</a>";
				}else{
					fileDownLink = "����";
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

	 //���� �ٿ�ε�
	 function fileDownload(rfilename, sfilename, filepath, filesize){
		var loc=CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
		hiddenFrame.document.location = loc;
	 }


	// ÷������ ���� start
	function delFile(){
		var f = document.Input;
		var courseid 	= f.pCourseId.value;
	    var reportid 	= f.pReportId.value;
	    var subreportid	= f.pSubReportId.value;
		if(confirm("���������� �����Ͻðڽ��ϱ�?")){
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
	// ÷������ ���� ends


	 //�������� �׸� ��� �����ֱ�/���߱�
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


   //���� ������� ��� �� ����(�˾�)
   function addSubReport(){
	   var f = document.Regist;
	   var courseid = f.pCourseId.value;
	   var reportid = f.pReportId.value;

	   var loc="/ReportSubInfo.cmd?cmd=subReportWriteForm&pCourseId="+courseid+"&pReportId="+reportid+"&pMODE=ADD";
	   report = window.open(loc,"report","left=0,top=0,toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=auto,resizable=no,width=715,height=620");
    }

    //���� ������� ���� �� ����(�˾�)
   function editSubReport(subReportId){
	   var f = document.Regist;
	   var courseid = f.pCourseId.value;
	   var reportid = f.pReportId.value;
	   var registYn = f.pReportRegistYn.value;
	   var editYn	= f.pSubReportEditYn.value;
	   if(registYn == "Y" && editYn == "Y") {
	   		alert(" ���� ������ ��� �Ͽ��� ���� �������ڰ� ���� ���ڸ� \n �������Ƿ� ����/���� �ϽǼ� �����ϴ�. ");
	   		return;
	   }
	   var loc="/ReportSubInfo.cmd?cmd=subReportWriteForm&pCourseId="+courseid+"&pReportId="+reportid+"&pSubReportId="+subReportId+"&pMODE=EDIT";
	   report = window.open(loc,"report","left=0,top=0,toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=auto,resizable=no,width=715,height=560");
    }

    // ��üũ (report_sub_info ���� ��� �˾� �� üũ)
	function chkForm(pMode)
	{
		/*  WEAS ���� ��ũ��Ʈ3(����) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS ���� ��ũ��Ʈ3(��) */

		var f = document.Input;

		if(isEmpty(f.pSubject.value)) {
			alert('������ �Է��ϼ���');
			f.pSubject.focus();
			return false;
		}

		if (getLength(f.pSubject.value) > 200) {
			alert('������ ���̰� �ʹ� ��� �Է��� �Ұ����մϴ�.(200���̳�)');
			f.pSubject.focus();
			return false;
		}

		if(isEmpty(f.pContents.value)) {
			alert('������ �Է��ϼ���');
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
					alert('�������� �׸��� ������ �ּ���');
					f.pBankId.focus();
					return false;
				}
			}
		}
		return true;
	}

    // �����
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


	// �������� �׸� ����Ʈ�ڽ� �ʱ�ȭ
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

	// �������� �׸� ����Ʈ�ڽ� ǥ��
	function reportBankInfoSelectListCallback(data){
		DWRUtil.removeAllOptions("pBankId");
		var defaultSelect = {"":"--�׸���--"};
		DWRUtil.addOptions("pBankId", defaultSelect);
		DWRUtil.addOptions("pBankId", data);

		setOptionSelected($("pBankId"),BANK_ID);
	}

	// �������� �׸� ���� ����
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
				alert("�׸��� �����ϼ���");
				$("pBankId").focus();
			}
		}else if(regMode == "DEL"){
			var bankId = DWRUtil.getValue("pBankId");
			if(bankId != null && bankId != ""){
				var st = confirm('�׸� �ش��ϴ� ��� ������ ���� �˴ϴ�.\n\n �׸��� �����Ͻðڽ��ϱ�?');
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
				alert("�׸��� �����ϼ���");
				$("pBankId").focus();
			}
		}
	}

	 // ��üũ (�������� ���/����/���� �� ���)
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

	// �������� �׸� ���/����
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

	// �������� �׸� ���� �ݱ�
	function closeReportBankInfoWrite(){
		$("pBankName").value = "";
		$("reportBankWriteDiv").style.display = "none";
		Effect.Appear("reportBankInfoSelectDiv");
	}


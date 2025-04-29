	// ���񸮽�Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var COURSE_ID = "";	
	var BANK_ID = "";		
	var REPORT_ID = "";		
	var REG_MODE = "";

	function init(systemCode,contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;	
		
		var f = document.Input;
		this.COURSE_ID = f.pCourseId.value;
		this.BANK_ID = f.pBankId.value;
		this.REPORT_ID = f.pReportId.value;
		this.REG_MODE = f.pMODE.value;
		
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
		return true;
	}
	
	// ÷������ ���� start
	function delFile(){
		if(confirm("���������� �����Ͻðڽ��ϱ�?")){
			ReportBankWork.reportBankContentsFileDelete(COURSE_ID, BANK_ID, REPORT_ID, delFileCallback);
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
			
    function goList(){
        var f = document.Input;
        var gubun = f.pGubun.value;
        var loc = CONTEXTPATH+"/ReportBank.cmd?cmd=reportBankContentsList&pCourseId="+COURSE_ID+"&pBankId="+BANK_ID+"&pGubun="+gubun;      

        document.location = loc;    
    }
    
    // ����Ʈ ����
    function goDelete(){
    	var chkDel =confirm('���� ���� ������ ���� �Ͻðڽ��ϱ�?'); 
        if(chkDel){
		    var loc = CONTEXTPATH+"/ReportBank.cmd?cmd=reportBankContentsDelete&pCourseId="+COURSE_ID+"&pBankId="+BANK_ID+"&pReportId="+REPORT_ID;      
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
			f.action = CONTEXTPATH+"/ReportBank.cmd?cmd=reportBankContentsRegist&pCourseId="+COURSE_ID+"&pBankId="+BANK_ID;
			f.method = "post";
			f.submit();
		}else{
			return;
		}
	}    
	
	
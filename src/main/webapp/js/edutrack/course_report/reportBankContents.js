	// 과목리스트
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
		return true;
	}
	
	// 첨부파일 제거 start
	function delFile(){
		if(confirm("기존파일을 삭제하시겠습니까?")){
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
	// 첨부파일 제거 ends
			
    function goList(){
        var f = document.Input;
        var gubun = f.pGubun.value;
        var loc = CONTEXTPATH+"/ReportBank.cmd?cmd=reportBankContentsList&pCourseId="+COURSE_ID+"&pBankId="+BANK_ID+"&pGubun="+gubun;      

        document.location = loc;    
    }
    
    // 레포트 삭제
    function goDelete(){
    	var chkDel =confirm('문제 은행 과제를 삭제 하시겠습니까?'); 
        if(chkDel){
		    var loc = CONTEXTPATH+"/ReportBank.cmd?cmd=reportBankContentsDelete&pCourseId="+COURSE_ID+"&pBankId="+BANK_ID+"&pReportId="+REPORT_ID;      
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
			f.action = CONTEXTPATH+"/ReportBank.cmd?cmd=reportBankContentsRegist&pCourseId="+COURSE_ID+"&pBankId="+BANK_ID;
			f.method = "post";
			f.submit();
		}else{
			return;
		}
	}    
	
	
// 첨부파일 제거 start
	function delFile(type){
		var f = document.Input;
		var courseid 	= f.pCourseId.value;
	    var reportid 	= f.pReportId.value;
	    var userid		= f.pUserId.value;	
		if(confirm("기존 첨부파일을 삭제하시겠습니까?")){
			ReportSendWork.reportSendFileDelete(courseid, reportid, userid, type, delFileCallback);
		}else	
			return;
	}	

	function delFileCallback(data){
		var result = data;
		var f = document.Input;   
	  	if(result == '1'){
	  		f.pOldRfile1.value="";
	  		f.pOldSfile1.value="";
	  		f.pOldFilePath1.value="";
	  		f.pOldFileSize1.value="";
			$("fileDiv1").innerHTML = "";
			$("fileDiv1").style.display = "none";			
		} else if(result == '2') {
			f.pOldRfile2.value="";
	  		f.pOldSfile2.value="";
	  		f.pOldFilePath2.value="";
	  		f.pOldFileSize2.value="";
			$("fileDiv2").innerHTML = "";
			$("fileDiv2").style.display = "none";
		} else if(result == '3') {
			f.pOldRfile3.value="";
	  		f.pOldSfile3.value="";
	  		f.pOldFilePath3.value="";
	  		f.pOldFileSize3.value="";
			$("fileDiv3").innerHTML = "";
			$("fileDiv3").style.display = "none";
	  	}else return;
	}          
// 첨부파일 제거 ends
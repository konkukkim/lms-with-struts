	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var COURSE_ID = "";
	var BANK_CODE = "";

	function init(systemCode,contextPath,courseId) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.COURSE_ID = courseId;

		examBankInfoSelectList();
	}

	function errorMessage(){
		alert("작업중 에러가 발생했습니다.\n재시도 해주세요!!!");
	}

	// 문제은행 항목 셀렉트박스 초기화
	function examBankInfoSelectList(){
		ExamBankWork.examBankInfoSelectList(COURSE_ID, examBankInfoSelectListCallback);
	}

	// 문제은행 항목 셀렉트박스 표시
	function examBankInfoSelectListCallback(data){
		DWRUtil.removeAllOptions("pBankCode");
		var defaultSelect = {"":"--항목선택--"};
		DWRUtil.addOptions("pBankCode", defaultSelect);
		DWRUtil.addOptions("pBankCode", data);

		autoReload();
	}

	function autoReload(){
		BANK_CODE = DWRUtil.getValue("pBankCode");
		ExamBankWork.examBankContentsListAuto(COURSE_ID, BANK_CODE, autoReloadCallback);
	}

	// 문제리스트 뿌려주기
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
			  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">등록된 문제가 없습니다.</td></tr>";
			}else{
			  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">항목을 선택하세요.</td></tr>";
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
					fileDownLink = "<a href=\"javascript:fileDownload('"+rfileName+"','"+sfileName+"','"+filePath+"','"+filePath+"');\">있음</a>";
				}else{
					fileDownLink = "없음";
				}

				if(examType == "J") examTypeName	=	"서술형";
				else if(examType == "F") examTypeName	=	"파일형";
				else if(examType == "K") examTypeName	=	"선택형";
				else if(examType == "D") examTypeName	=	"단답형";
				else if(examType == "S") examTypeName	=	"OX형";

			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
			    	+ "<td width='70' class=\"s_tab04_cen\">"+allItemCnt+"</td>"
			    	+ "<td></td><td width='322' class=\"s_tab04\">"
			    	+ " <a href=\"javascript:showExamContents('"+examType+"','"+examNo+"');\">"+contentsText+"</a></td>"
					+ "<td></td><td width='100' class=\"s_tab04_cen\">"+examTypeName+"</td>"
			    	+ "<td></td><td width='100' class=\"s_tab04_cen\">"+fileDownLink+"</td>"
			    	+ "<td></td><td width='74' class=\"s_tab04_cen\">"
					//+ " <a href=\"javascript:registSelect('"+examType+"','"+examNo+"');\">선택</a>"
					+ " <input type=checkbox name='contentsBox' value='"+examNo+"' style='border:0'></td>"
			    	+ "</tr>";

			    if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    allItemCnt--;
			}
		}
		objStr += "</table>";

		$("examBankContentsList").innerHTML = objStr;
		$("examBankContentsList").style.display = "block";
	 }

	// 파일다운로드
	function fileDownload(rfilename, sfilename, filepath, filesize){
		var loc=CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
		hiddenFrame.document.location = loc;
	}

	// 시험 내용 보기
	function showExamContents(examType,examNo){
       var Page =  CONTEXTPATH+"/ExamBank.cmd?cmd=examBankContentsShow&pCourseId="+COURSE_ID+"&pBankCode="+BANK_CODE+"&pExamNo="+examNo+"&pExamType="+examType;
	   ShowInfo = window.open(Page,"bankcotentsshow", "left=162,top=100,toolbar=0,directories=0,status=0,menubar=0,width=600,height=600,scrollbars=yes");
    }

    function checkAll(){
        var f = document.f;
        if(f.checkYn.value == "Y") {
          f.checkYn.value = "N";
    	  setChkboxAll(f, "contentsBox",false);
        }else{
          f.checkYn.value = "Y";
    	  setChkboxAll(f, "contentsBox", true);
       }
    }

    function addContents(){
  	   var f = document.f;
  	   var checkVal = getChkBoxByValue(f, "contentsBox", "");

	   if(checkVal == "") {
	       alert("등록할 문제를 선택해 주세요.");
	       return;
	   }

	   f.submit();
    }

    function registSelect(examType, examNo){
  	   var f = document.f;

		if(isEmpty(f.pExamOrder.value)){
		   alert("출제할 문제번호는 필수입력항목입니다.");
		   f.pExamOrder.focus();
		   return;
		}

		if(!isNumber(parseInt(f.pExamOrder.value))){
		   alert("출제할 문제번호는 숫자만 입력가능합니다.");
		   f.pExamOrder.focus();
		   return;
		}

		if(parseInt(f.pExamOrder.value) < 1 || parseInt(f.pExamOrder.value) > 100){
		   alert("출제할 문제번호는 1 ~ 999 사이의 숫자만 입력가능합니다.");
		   f.pExamOrder.focus();
		   return;
		}

		var examId = f.pExamId.value;
		var examOrder = f.pExamOrder.value;

		var loc = CONTEXTPATH+"/ExamContents.cmd?cmd=examBankContentsCopy&pCourseId="+COURSE_ID+"&pExamId="+examId+"&pExamType="+examType+"&pBankCode="+BANK_CODE+"&pExamOrder="+examOrder+"&contentsBox="+examNo;
		document.location = loc;
    }

	function goList(){
	   var f = document.f;
       var examId = f.pExamId.value;
       var loc = CONTEXTPATH+"/ExamContents.cmd?cmd=examContentsList&pCourseId="+COURSE_ID+"&pExamId="+examId;
       document.location = loc;
	}

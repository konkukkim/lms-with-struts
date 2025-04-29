	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";

	function init(systemCode,contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;

		autoReload();
	}

	function errorMessage(){
		alert("작업중 에러가 발생했습니다.\n재시도 해주세요!!!");
	}


	// 페이징처리
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
		autoReload();
	}

	// 수강생리스트 받아오기
	function changeCurriSub(){
		document.f.pSWord.value = "";	// 검색어 초기화
		document.f.curPage.value = "";	// 페이징 초기화
		autoReload();
	}

	// 수강생리스트 받아오기-검색
	function goSearch(){

	   var f = document.f;
	   var curPage = 1;
	   var sTarget = f.pSTarget[f.pSTarget.selectedIndex].value;
 	   var sWord = ajaxEnc(f.pSWord.value);
	   var str = f.pCurriYearTerm[f.pCurriYearTerm.selectedIndex].value;

	   var curriCode = "";
	   var curriYear = "";
	   var curriTerm = "";
	   if(str !="") {
		   var strs = str.split("\|");
		   curriCode = strs[0];
		   curriYear = strs[1];
		   curriTerm = strs[2];
	   }
	   StudentWork.studentListAuto(curPage, "confirm", sTarget, sWord, curriCode, curriYear, curriTerm, autoReloadCallback);
	}

	// 수강생리스트 받아오기
	function autoReload(){

	   var f = document.f;
	   var curPage = f.curPage.value;
	   var sTarget = f.pSTarget[f.pSTarget.selectedIndex].value;
 	   var sWord = ajaxEnc(f.pSWord.value);
	   var str = f.pCurriYearTerm[f.pCurriYearTerm.selectedIndex].value;

	   var curriCode = "";
	   var curriYear = "";
	   var curriTerm = "";
	   if(str !="") {
		   var strs = str.split("\|");
		   curriCode = strs[0];
		   curriYear = strs[1];
		   curriTerm = strs[2];
	   }
	   StudentWork.studentListAuto(curPage, "confirm", sTarget, sWord, curriCode, curriYear, curriTerm, autoReloadCallback);
	}

	// 수강생리스트 뿌려주기
	function autoReloadCallback(data){
	  	var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;
	  	var paggingStr = data.pagging;
		var rowLength = 0;
		if(dataList != null)
			rowLength = dataList.length;

	  	var studentListObj = $("studentList");
	  	var paggingObj = $("getPagging");
	  	var objStr = "<table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>";

		if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">등록된 수강신청 정보가 없습니다.</td></tr>";
	  	}else{

		  	for(i=0;i<rowLength;i++){
				var studentDTO = dataList[i];

		    	var userName = studentDTO.userName;
		    	var userId = studentDTO.userId;
		    	var stuCurriName = studentDTO.curriName;
		    	var curriCode = studentDTO.curriCode;
		    	var curriYear = studentDTO.curriYear;
		    	var curriTerm = studentDTO.curriTerm;
		    	var enrollNo = studentDTO.enrollNo;
		    	var regDate = studentDTO.regDate;

			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
				+" <td width='70' class=\"s_tab04_cen\">"+totalCount+"</td>"
				+ "<td></td><td width='100' class=\"s_tab04_cen\">"+userName+"("+userId+")</td>"
				+ "<td></td><td width='' class=\"s_tab04\">"+stuCurriName+"</td>"
				+ "<td></td><td width='140' class=\"s_tab04_cen\">"+regDate+"</td>"
				+ "<td></td><td width='74' class=\"s_tab04_cen\"><input type='checkbox' class='solid0' name='pChk' "
				+ " value="+userId+"|"+curriCode+"|"+curriYear+"|"+curriTerm+"|"+enrollNo+"></td>"
				+ "</tr>";

				if(totalCount > 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    totalCount--;
			}
		}
		objStr += "</table>";
		studentListObj.innerHTML = objStr;
		studentListObj.style.display = "block";
	 }

	/** 수강생 추가 버튼 클릭시
	 */
	function goAdd() {
		var str = f.pCurriYearTerm[f.pCurriYearTerm.selectedIndex].value;
		var loc = "";

		//if(str !="") {
		 	//var strs = str.split("\|");

		 	studentWrite.style.display = "block";
			//loc=CONTEXTPATH+"/Student.cmd?cmd=confirmStudentWrite&pCurriCode="+strs[0]+"&pCurriYear="+strs[1]+"&pCurriTerm="+strs[2];
		//}else{
			//loc=CONTEXTPATH+"/Student.cmd?cmd=confirmStudentWrite";
		//}
		//WriteWin = window.open(loc,"writewin","width=530,height=300,left=0,top=0,resizable=no,scrollbars=1");
		//WriteWin.focus();
	}
	function goAddFile() {
		 var str = f.pCurriYearTerm[f.pCurriYearTerm.selectedIndex].value;
		 if(str !="") {
		 	var strs = str.split("\|");
		 	if(strs[0] != ''){
				var loc=CONTEXTPATH+"/Student.cmd?cmd=confirmStudentUploadWrite&pCurriCode="+strs[0]+"&pCurriYear="+strs[1]+"&pCurriTerm="+strs[2];
				window.open(loc, "studentUpload", "toolbar=no,location=no, width=450, height=250,top=100, left=100, directories=no,status=no,menubar=no,scrollbars=yes,resizable=no");
		 	}else{
		 		alert('해당 과정을 먼저 선택 해 주세요');
		 	}
		 }else{
			alert('해당 과정을 먼저 선택 해 주세요');
		 }
	}

	function goSelDelete(){
		var f = document.f;
		var str = "";
		f.pChk = document.querySelectorAll("input[type='checkbox']");  // ADD 2021-12-01
		
		if(f.pChk==null){
			alert("선택할 대상이 없습니다!");
			return ;
		}
		
		var cnt = (f.pChk.length==null ? "1" : f.pChk.length );

		if(cnt == 1 ){
			if(f.pChk.checked) str = "/"+f.pChk.value;
		}else if(cnt > 1 ){
			for(i = 0; i < cnt; i++)
			{
				if(f.pChk[i].checked == true) str += "/"+f.pChk[i].value;
			}

		}
		
		if(str =="") {
			alert("수강취소할 대상을 선택해 주세요!");
			return ;
		}
		
		StudentWork.checkConfirmAjax(str, "N", AfterCheckConfirm );
	}


	// 수강인증, 수강취소, 수강신청삭제 후의 콜백 메소드
	function AfterCheckConfirm(msg){
		alert(msg);	
		
		autoReload();
	}
	

	/** 수강생 추가-취소버튼 클릭시
	 */
	function closeStudentWrite(){
	    studentWrite.style.display = "none";
	}



	/** 수강생 추가-등록버튼 클릭시
	 */
	function registStudents(){
		var str = f.pCurriYearTerm[f.pCurriYearTerm.selectedIndex].value;
		var pId = f.pId.value ;

		if(str ==""){
		    alert("과정명을 선택을 해 주세요.");
		    return;
		}else{
		   var strs = str.split("\|");
		   curriCode = strs[0];
		   curriYear = strs[1];
		   curriTerm = strs[2];
		}
		StudentWork.masterEnrollRegist("Confirm", pId, curriCode, curriYear, curriTerm, callBackAfterRegist);
		
	}


	/** 수강생 등록후 콜백 메소드..
	 */
	function callBackAfterRegist(msg){

		document.f.pId.value = "";

		alert(msg);
		autoReload();
	}
	
	/** 엑셀다운로드
	 */
	function doExcelDown(){
	    var sTarget = f.pSTarget[f.pSTarget.selectedIndex].value;
 	    var sWord = ajaxEnc(f.pSWord.value);
	    var str = f.pCurriYearTerm[f.pCurriYearTerm.selectedIndex].value;

		var curriCode = "";
		var curriYear = "";
		var curriTerm = "";
		
		if(str !=""){
		   var strs = str.split("\|");
		   curriCode = strs[0];
		   curriYear = strs[1];
		   curriTerm = strs[2];
		}
		
		f.action = CONTEXTPATH+"/Student.cmd?cmd=confirmStudentExcelDown";
	    f.target = "hiddenFrame";
	    f.submit();
	}

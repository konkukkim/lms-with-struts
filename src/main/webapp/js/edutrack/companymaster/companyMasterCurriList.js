	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var CURRIYEAR = 2007;

	function init(systemCode, contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.CURRIYEAR = DWRUtil.getValue("pCurriYear");

		termSelectList(CURRIYEAR);				//-- 학기셀렉트
		autoReload();							//-- 과정리스트
		curriCodeSelectList(CURRIYEAR, '');		//-- 셀렉트 과정리스트
	}

//---------------------------------------------------------------------------------------------

	//-- 과정리스트 데이터 가져오기
	function autoReload() {
		var curYear	=	DWRUtil.getValue("pCurriYear");
		var curTerm	=	DWRUtil.getValue("pCurriTerm");
//alert('check03');
		CompanyMasterCourseWork.autoCompanyMasterCurriList(curYear, curTerm, autoReloadCallback);
	}

	//-- 과정리스트 데이터 담기
	function autoReloadCallback(data) {
		var rowLength = 0;
		var allItemCnt = 0;
		var No = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}
//alert(rowLength);
		var masterListObj = $("masterCurriList");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"13\">등록된 과정이 없습니다.</td></tr>";
	  	}else{
			for(i=0; i<rowLength; i++) {
	  			var curriSubDto = data[i];
	  			No++;

				var curriCode 			= 	curriSubDto.curriCode;
				var curriYear 			= 	curriSubDto.curriYear;
				var curriTerm 			= 	curriSubDto.curriTerm;
				var curriName 			= 	curriSubDto.curriName;
				var passStudentCnt 		= 	curriSubDto.passStudentCnt;
				var noPassStudentCnt 	= 	curriSubDto.noPassStudentCnt;
				var processStudentCnt	=	curriSubDto.processStudentCnt;
				var allStudentCnt		=	curriSubDto.allStudentCnt;
				var status				=	curriSubDto.connPoint;
				var statusStr			=	"";
				if(status == "ING") statusStr = "<font color='#FF0000'>진행중</font>";
				else if(status == "END") statusStr = "<b>종료</b>";
				else if(status == "BEFORE") statusStr = "<font color='#f4a460'>대기중</font>";

				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">" +
						"<td width='277' class=\"s_tab04\">"+curriName+"</td>" +
						"<td width='70' class=\"s_tab04_cen\">"+processStudentCnt+"</td>" +
						"<td width='70' class=\"s_tab04_cen\">"+passStudentCnt+"</td>" +
						"<td width='80' class=\"s_tab04_cen\">"+noPassStudentCnt+"</td>" +
						"<td width='80' class=\"s_tab04_cen\">"+allStudentCnt+"</td>" +
						"<td width='70' class=\"s_tab04_cen\">"+statusStr+"</td></tr>";

				if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";
				allItemCnt--;
	  		}
	  	}
	  	objStr += "</table>";

		masterListObj.innerHTML = objStr;
		masterListObj.style.display = "block";
	}

//-------------------------------------------------------------------------------------

	//개설연도를 바꾸면 학기도 바꾼다.
	function changeYear() {
		var curriYear = DWRUtil.getValue("pCurriYear");

		termSelectList(curriYear);				//-- 학기 셀렉트
		curriCodeSelectList(curriYear, '');		//-- 과정 셀렉트
		autoReload();							//-- 과정 리스트
	}

//-------------------------------------------------------------------------------------

	//개설학기 초기화
	function termSelectList(curYear) {
		CurriSubStaticInfoWork.termSelectList(curYear, termSelectListCallBack);
	}

	//개설학기 셀렉트박스 표시
	function termSelectListCallBack(data) {
		var rowLength = 0;
		if(data != null){
			rowLength = data.length;
		}

		var curriTermListObj = $("curriTermList");
		var objStr = "<select name='pCurriTerm' onChange = 'changTerm()'>" +
						"<option value=''>--개설학기--</option>";

		if(rowLength == 0) {
			objStr += "";
		} else {
			var curriTerm	=	0;

			for(i=0;i<rowLength;i++){
	   			var curriSubStaticDTO = data[i];
	   			curriTerm	=	curriSubStaticDTO.curriTerm;

				objStr		+=	"<option value='"+curriTerm+"'>"+curriTerm+"</option>";
	   		}	//end for
		}
		objStr += "</select>";

		curriTermListObj.innerHTML = objStr;
		curriTermListObj.style.display = "block";
	}

	//개설학기 변경
	function changTerm() {
		var curriYear = DWRUtil.getValue("pCurriYear");
		var curriTerm = DWRUtil.getValue("pCurriTerm");

		curriCodeSelectList(curriYear, curriTerm);		//-- 과정 셀렉트
		autoReload();									//-- 과정 리스트
	}

//-------------------------------------------------------------------------------------

	//-- (셀렉트박스용) 위탁관리자가 담당하는 과정 리스트
	function curriCodeSelectList(curriYear, curriTerm) {
		CompanyMasterCourseWork.getCompanySelectCourseList(curriYear, curriTerm, curriCodeSelectListCallBack);
	}

	//-- (셀렉트박스용) 과정 리스트 데이터 세팅
	function curriCodeSelectListCallBack(data) {
		DWRUtil.removeAllOptions("pCurriCode");
		var defaultSelect = {"":"--과정전체--"};
		DWRUtil.addOptions("pCurriCode", defaultSelect);
		DWRUtil.addOptions("pCurriCode", data);
	}

	//-- 과정 변경 : 전체 선택시엔 과정리스트, 과정 선택시엔 과정의 학습자 현황 리스트
	function changeCurriCode() {
		var curriYear = DWRUtil.getValue("pCurriYear");
		var curriTerm = DWRUtil.getValue("pCurriTerm");
		var curriCode =  DWRUtil.getValue("pCurriCode");

		if(curriYear != 0 && curriTerm != 0) {
			if(curriCode == "") {
				autoReload();
				$("studentCurriDiv").style.display = "none";
				Effect.Appear("companyCurriDiv");
			} else {
				studentAutoReload(curriYear, curriTerm, curriCode);
				$("companyCurriDiv").style.display = "none";
				Effect.Appear("studentCurriDiv");
			}
		} else {
			alert('년도와 학기를 빠짐없이 선택해 주셔야 합니다.');
			curriCodeSelectList(curriYear, curriTerm);
		}

	}

//-------------------------------------------------------------------------------------

	//
	function studentAutoReload(curriYear, curriTerm, curriCode) {
		CompanyMasterCourseWork.getCompanyStudentCurriInfo(curriYear, curriTerm, curriCode, studentAutoReloadCallback);
	}

	//
	function studentAutoReloadCallback(data) {

		var rowLength = 0;
		var allItemCnt = 0;
		var No = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}
//alert(rowLength);
		var stuListObj = $("studentCurriList");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"17\">등록한 수강생이 없습니다.</td></tr>";
	  	}else{
			for(i=0; i<rowLength; i++) {
				var studentDto = data[i];
	  			No++;

	  			var userName	=	studentDto.userName;
	  			var userId		=	studentDto.userId;
	  			var scoreExam	=	studentDto.scoreExam;
	  			var scoreReport =	studentDto.scoreReport;
	  			var scoreAttend =	studentDto.scoreAttend;
	  			var scoreForum	=	studentDto.scoreForum;
	  			var scoreEtc1	=	studentDto.scoreEtc1;
	  			var scoreEtc2	=	studentDto.scoreEtc2;
	  			var totalScore	=	studentDto.totalScore;
	  			var scoreGubun	=	studentDto.scoreGubun;
	  			var grade		=	studentDto.grade;
	  			var getCredit	=	studentDto.getCredit;
	  			var completeYn	=	"";
	  			var enrollStatus =	studentDto.enrollStatus;
	  			var completeDate =	studentDto.completeDate;

	  			var completeStr	=	"";
	  			if(scoreGubun != "" && scoreGubun == "1") {
	  				completeStr	=	grade+"&nbsp;&nbsp;("+getCredit+")";
	  			} else if(scoreGubun != "" && scoreGubun == "2") {
	  				if(enrollStatus == "C" && completeDate != "")	completeStr = "<font color='#FF0000'><b>수료</b></font>";
	  				else	completeStr = "<font color='#8fbc8f'><b>미수료</b></font>";
	  			}


				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">" +
						"<td width='227' class=\"s_tab04\">"+userName+"&nbsp;&nbsp;("+userId+")"+"</td>" +
						"<td></td><td width='70' class=\"s_tab04_cen\">"+scoreExam+"</td>" +
						"<td></td><td width='70' class=\"s_tab04_cen\">"+scoreReport+"</td>" +
						"<td></td><td width='70' class=\"s_tab04_cen\">"+scoreAttend+"</td>" +
						"<td></td><td width='70' class=\"s_tab04_cen\">"+scoreForum+"</td>" +
						"<td></td><td width='70' class=\"s_tab04_cen\">"+totalScore+"</td>" +
						"<td></td><td width='70' class=\"s_tab04_cen\">"+completeStr+"</td></tr>";

				if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"17\"></td></tr>";
				allItemCnt--;
	  		}
	  	}
	  	objStr += "</table>";

		stuListObj.innerHTML = objStr;
		stuListObj.style.display = "block";
	}

//-------------------------------------------------------------------------------------

	function goList() {
		var curriYear = DWRUtil.getValue("pCurriYear");
		var curriTerm = DWRUtil.getValue("pCurriTerm");

		autoReload();
		curriCodeSelectList(curriYear, curriTerm);

		$("studentCurriDiv").style.display = "none";
		Effect.Appear("companyCurriDiv");
	}
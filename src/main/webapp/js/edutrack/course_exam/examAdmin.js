
	var COURSE_ID = "";
	var CONTEXTPATH = "";
	var COURSELISTSIZE = 0;

	// 시험출제 페이지 초기화
	function init(contextPath, courseListSize, courseId, status) {
		COURSE_ID = courseId;
		CONTEXTPATH = contextPath;
		COURSELISTSIZE = courseListSize
		if(status == "PROF") {
			autoReloadProf();
		} else {
			autoReloadStu();
		}
	}

	// 시험평가 페이지 초기화
	function resultInit(contextPath, courseListSize, courseId, status) {
		COURSE_ID = courseId;
		CONTEXTPATH = contextPath;
		COURSELISTSIZE = courseListSize
		if(status == "PROF") {
			profResultAutoReload();
		} else {
			stuResultAutoReload();
		}
	}

//---------------------------------------------------------------------------------

	//시험리스트 자동리스트 뿌려주기(교수자)
	function autoReloadProf(){
		var f = document.f;
		var COURSE_ID = f.pCourseId.value;
		ExamAdminWork.examListAuto(COURSE_ID, autoReloadProfCallback);
	}

	//시험리스트 뿌리기
	function autoReloadProfCallback(data) {
		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var objStr = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>"

	  	if(rowLength == 0){
			objStr 	+= "<tr><td class=\"s_tab04_cen\" colspan=\"13\">등록된 시험이 없습니다.</td></tr>"

	  	}else{
			var No = 0;
			var flagStr = "";
			var flagTimeStr = "";

		  	for(i=0;i<rowLength;i++){
    			var examInfoDTO = data[i];
    			No++;

    			var systemCode	=	examInfoDTO.systemCode;
    			var courseId	=	examInfoDTO.courseId;
    			var examId		=	examInfoDTO.examId;
    			var subject		=	examInfoDTO.subject;
    			var startDate	=	examInfoDTO.startDate;
    			var endDate		=	examInfoDTO.endDate;
    			var flagTime	=	examInfoDTO.flagTime;
    			var runTime		=	examInfoDTO.runTime;
    			var flagUse		=	examInfoDTO.flagUse;

    			if(flagUse == "Y") flagStr = "<b><font color='black' style='font-size:9pt;line-height:12pt;font-family:굴림체'>등록완료</font></b>";
    			else flagStr = "<font color='red' style='font-size:9pt;line-height:12pt;font-family:굴림체'>등록대기</font>";

    			if(flagTime == "Y") flagTimeStr = runTime;
    			else flagTimeStr = "제한없음";

				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
				    	+ "<td width='40' class=\"s_tab04_cen\">"+No+"</td>"
				    	+ "<td></td><td width='218' class=\"s_tab04\">"
				    	+ "<a href=\"javascript:Show_ExamContents('"+examId+"');\">"+subject+"</a></td>"
						+ "<td></td><td width='150' class=\"s_tab04_cen\">"+startDate+" ~ "+endDate+"</td>"
				    	+ "<td></td><td width='80' class=\"s_tab04_cen\">"+flagTimeStr+"</td>"
				    	+ "<td></td><td width='80' class=\"s_tab04_cen\">"+flagStr+"</td>"
				    	+ "<td></td><td width='74' class=\"s_tab04_cen\">"
				    	+ "<a href=\"javascript:Show_ExamInfo('"+examId+"');\">"
				    	+ "<img src="+CONTEXTPATH+"\"/img/"+systemCode+"/button/sbt_mody2.gif\" width=\"51\" height=\"15\" align=\"absmiddle\" border=\"0\"></a></td>"
				    	+ "</tr>";

				if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"13\"></td></tr>";
				allItemCnt--;
    		}
	  	}
	  	objStr += "</table>";

	  	$("examListDiv").innerHTML = objStr;
		$("examListDiv").style.display = "block";
	}


    function Create_Exam(){
       var f = document.f;

       if(COURSELISTSIZE > 1 && f.pCourseId.value == ""){
         alert('먼저 과목을 선택해 주십시요.');
         return;
       }

       f.pMODE.value = "write";
       f.submit();
    }

    function Show_ExamContents(examid){
       var f = document.f;

       f.pExamId.value = examid;
       f.action = CONTEXTPATH+"/ExamContents.cmd?cmd=examContentsList";
       f.submit();
    }

    function Show_ExamInfo(examid){
       var f = document.f;

       f.pExamId.value = examid;

       f.pMODE.value = "edit";
       f.submit();
    }

    function Change_Course(){
       var f = document.f;
       f.action = CONTEXTPATH+"/ExamAdmin.cmd?cmd=examList";
       f.submit();
    }

//---------------------------------------------------------------------------------

	//교수자 시험평가 리스트 데이타 가져오기
	function profResultAutoReload() {
		var f = document.f;
		var COURSE_ID = f.pCourseId.value;
		ExamResultWork.examResultAutoList(COURSE_ID, profResultAutoReloadCallback);
	}

	//시험평가 리스트 뿌리기
	function profResultAutoReloadCallback(data) {
		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var objStr = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>"

	  	if(rowLength == 0){
			objStr 	+= "<tr><td class=\"s_tab04_cen\" colspan=\"13\">등록된 시험이 없습니다.</td></tr>"

	  	}else{
			var No = 0;
			var flagStr = "";
			var flagTimeStr = "";

		  	for(i=0;i<rowLength;i++){
    			var examInfoDTO = data[i];
    			No++;

    			var systemCode	=	examInfoDTO.systemCode;
    			var courseId	=	examInfoDTO.courseId;
    			var examId		=	examInfoDTO.examId;
    			var subject		=	examInfoDTO.subject;
    			var startDate	=	examInfoDTO.startDate;
    			var endDate		=	examInfoDTO.endDate;
    			var flagTime	=	examInfoDTO.flagTime;
    			var runTime		=	examInfoDTO.runTime;

    			if(flagTime == "Y") flagTimeStr = runTime;
    			else flagTimeStr = "제한없음";

				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
				    	+ "<td width='70' class=\"s_tab04_cen\">"+No+"</td>"
				    	+ "<td></td><td width='263' class=\"s_tab04\">"
				    	+ "<a href=\"javascript:ShowResultContents('"+examId+"');\">"+subject+"</a></td>"
						+ "<td></td><td width='150' class=\"s_tab04_cen\">"+startDate+" ~ "+endDate+"</td>"
				    	+ "<td></td><td width='80' class=\"s_tab04_cen\">"+flagTimeStr+"</td>"
				    	+ "<td></td><td width='80' class=\"s_tab04_cen\">"
				    	+ "<a href=\"javascript:ShowExamInfo('"+examId+"');\"><b>[보기]</b></a></td>"
				    	+ "</tr>";

				if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"13\"></td></tr>";
				allItemCnt--;
    		}
	  	}
	  	objStr += "</table>";

	  	$("examResultListDiv").innerHTML = objStr;
		$("examResultListDiv").style.display = "block";
	}


    function ShowResultContents(examid){
       var f = document.f;

       f.pExamId.value = examid;
       f.action = CONTEXTPATH+"/ExamResult.cmd?cmd=examUserList";
       f.submit();
    }

    function ShowExamInfo(examid){
	   var f = document.f;
	   var courseid = f.pCourseId.value;
	   var loc = CONTEXTPATH+"/ExamAdmin.cmd?cmd=examStShow&pCourseId="+courseid+"&pExamId="+examid;
	   ShowInfo = window.open(loc,"test","left=0,top=0,toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=auto,resizable=yes,width=500,height=450");
    }

//---------------------------------------------------------------------------------

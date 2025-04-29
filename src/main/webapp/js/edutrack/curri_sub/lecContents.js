	// 목차리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var CLASS_USER_TYPE = "";
	var COURSE_ID = "";
	var COURSE_CONTENTS_TYPE = "";
	var FLAG_NAVI = "Y";
	var CONTENTS_WIDTH = "";
	var CONTENTS_HEIGHT = "";
	var QUIZ_CONFIG_YN = "N";

	// 초기설정
	function init(systemCode,contextPath,classUserType,courseId,quizConfigYn) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.CLASS_USER_TYPE = classUserType;
		this.COURSE_ID = courseId;
		this.QUIZ_CONFIG_YN = quizConfigYn;

		changeCourseInfo();
	}

	// 과목정보세팅
	function changeCourseInfo() {
		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId !=null && tmpCourseId != "")
			COURSE_ID = DWRUtil.getValue("pCourseId");

		CourseWork.courseInfo(COURSE_ID,courseInfoCallback);
	}

	// 수강시간 기록하기
	function bookmarkEnd(courseId,contentsId,startTime){
		CurriContentsWork.lecBookmarkEnd(courseId,contentsId,startTime, {
			callback:function(data) {
			  	if(data != null)
				  	CurriContentsWork.lecContentsListAuto(COURSE_ID, autoReloadCallback);
			  	else
			  		return;
			}
		});
	}

	// 과목정보세팅 callback, 목차리스트 리로드
	function courseInfoCallback(data){
		var courseDTO = data;
		if(data != null){
			COURSE_CONTENTS_TYPE = courseDTO.contentsType;
			FLAG_NAVI = courseDTO.flagNavi;
			CONTENTS_WIDTH = courseDTO.contentsWidth;
			CONTENTS_HEIGHT = courseDTO.contentsHeight;
		}
		CurriContentsWork.lecContentsListAuto(COURSE_ID, autoReloadCallback);
	}

	// 목차리스트 callback
	function autoReloadCallback(data){

		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null) {
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var contentsListObj = $("contentsList");
	  	var objStr = "<table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"17\">등록된 목차가 없습니다.</td></tr>";
	  	}else{

		  	var contentsImg = "icon_note.gif";
		  	var listQuizStatus = "";
		  	var lecLink = "";
		  	var quizLink = "";
		  	var contentsDepth = "";
		  	var prevContentsView = "Y";
		  	var prevQuizPass = "P";
		  	var itemNumView = "*";
		  	var chkPreview ="";
		  	var arrOrder = new Array();
			var textFilePathLink = "";
		  	
			if(CLASS_USER_TYPE == "P" )
				chkPreview = "true";

			var listNo = 1;
		  	for(i=0;i<rowLength;i++){

  				var lecContentsDTO = data[i];

                var curriCode = lecContentsDTO.curriCode;
                var curriYear = lecContentsDTO.curriYear;
                var curriTerm = lecContentsDTO.curriTerm;
                var userId = lecContentsDTO.userId;
                var userName = "";
                var contentsId  = lecContentsDTO.contentsId;
                var contentsType = lecContentsDTO.contentsType;
                var contentsOrder = lecContentsDTO.contentsOrder;
                var contentsName = lecContentsDTO.contentsName;
                var serverPath  = lecContentsDTO.serverPath;
                var openChk     = lecContentsDTO.openChk;
                var openDate	= lecContentsDTO.openDate;
                var showTime    = lecContentsDTO.showTime;
                var joinCount   = lecContentsDTO.joinCount;
                var totalTime   = lecContentsDTO.totalTime;
                var quizYn      = lecContentsDTO.quizYn;
                var quizPass    = lecContentsDTO.quizPass;
                var sizeApp     = lecContentsDTO.sizeApp;
                var cWidth      = lecContentsDTO.contentsWidth;
                var cHeight     = lecContentsDTO.contentsHeight;
                var cLectureGubun = lecContentsDTO.lectureGubun;
                var cStartDate  = lecContentsDTO.startDate;
                var cEndDate    = lecContentsDTO.endDate;
                var cAttendance = lecContentsDTO.attendance;
                var curDate 	= lecContentsDTO.curDate;
				var textFilePath= lecContentsDTO.filePath;
                var asfFilePath = lecContentsDTO.asfFilePath;

                var dateStr1 = "";
		    	var dateStr2 = "";
		    	var iconStr = "";

		    	if(cStartDate != "" && cEndDate != "") {
					if(cLectureGubun == "1") {
			    		dateStr1 = cStartDate.substring(4, 6)+"."+cStartDate.substring(6, 8)+" ~ ";
			    		dateStr2 = cEndDate.substring(4, 6)+"."+cEndDate.substring(6, 8);
			    	} else {
			    		dateStr1 = cStartDate.substring(4, 6)+"."+cStartDate.substring(6, 8)+" "+cStartDate.substring(8, 10)+":"+cStartDate.substring(10, 12)+"~";
			    		dateStr2 = cEndDate.substring(8, 10)+":"+cEndDate.substring(10, 12);
			    	}
				}

				if(contentsType == "F" && cLectureGubun == "1") {
					iconStr	=	"<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_on.gif' align='absmiddle'>";
				} else if(contentsType == "F" && cLectureGubun == "2") {
					iconStr	=	"<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_off.gif' align='absmiddle'>";
				}


		    	var viewWidth = "0";
		    	var viewHeight = "0";

	    		// 사이즈 적용여부
				if(FLAG_NAVI == "N"){
		    		if(sizeApp =="Y"){
		    			viewWidth = cWidth;
		    			viewHeight = cHeight;
		    		}else{
						viewWidth = CONTENTS_WIDTH;
						viewHeight = CONTENTS_HEIGHT;
		    		}
				}else{
					viewWidth = CONTENTS_WIDTH;
					viewHeight = CONTENTS_HEIGHT;
				}

		    	arrOrder = contentsOrder.split(".");
				var splitCnt = arrOrder.length;
				for(j=0; j<splitCnt; j++){
					if(parseInt(arrOrder[j]) > 0)
						contentsDepth +="&nbsp;&nbsp;&nbsp;";
				}
				arrOrder = null;

//		    	var openChkStr = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_view01.gif' align='absmiddle'>";
		    	var openChkStr = "";						// 컨텐츠를 수강시 표시
		    	contentsImg = "icon_note.gif";				// 챕터와 레슨을 표기하는 이미지
		    	listQuizStatus = "";
		    	//--	레슨일 때 리스트 표기
		    	if(contentsType !="C" || serverPath != ""){

		    		//-- 챕터명, 레슨명
		    		if(parseFloat(cStartDate) <= parseFloat(curDate) && cLectureGubun == "1") {
				  		if(contentsType =="C" && serverPath == ""){
				  			lecLink = "<a href=\"javascript:InitSCO('"+curriCode+"','"+COURSE_ID+"','"+contentsId+"','"+userId+"','"+userName+"'"
				  				+",'"+curriYear+"','"+curriTerm+"','"+SYSTEMCODE+"',"+chkPreview+","+viewWidth+","+viewHeight+")\">"+contentsName+"</a>";
				  		}else{
				  			lecLink = "<a href=\"javascript:goContents('"+COURSE_ID+"','"+contentsId+"',"+viewWidth+","+viewHeight+")\">"+contentsName+"</a>";
				  		}
		    		}
		    		else if(cLectureGubun == "2"){
		    			lecLink = contentsName;
		    		}
		    		else if(cStartDate == "" || cEndDate == "") {
						lecLink = "<a href=\"javascript:goContents('"+COURSE_ID+"','"+contentsId+"',"+viewWidth+","+viewHeight+")\">"+contentsName+"</a>";
		    		}
		    		else {
		    			lecLink = "<a href=\"javascript:alert('아직 수강기간이 시작되지 않았습니다.')\">"+contentsName;
		    		}


			  		//-- 단원평가 링크
			  		if(parseFloat(cStartDate) < parseFloat(curDate)) {
				  		quizLink="<a href=\"javascript:goQuiz('"+openChk+"','"+COURSE_ID+"','"+contentsId+"')\">";
			  		} else {
			  			quizLink = "";
			  		}
			  		if(CLASS_USER_TYPE == "P") {
			  			quizLink="<a href=\"javascript:goQuiz('Y','"+COURSE_ID+"','"+contentsId+"')\">";
			  		}

			  		//-- 단원평가 통과여부
					if(quizYn =='Y') {
						if(quizPass =='N' && quizPass =='F')
							listQuizStatus = quizLink+"<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/contents/q_npass.gif' align='absmiddle'></a>";
						if(quizPass =='P')
							listQuizStatus = quizLink+"<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/contents/q_pass.gif' align='absmiddle'></a>";

					}

					// 오프라인시 출석표기
		  			if(cLectureGubun == "2") {
		  			    //openChkStr = cStartDate
		  			    if(cAttendance != ""){
		  			        //openChkStr += "(" + cAttendance + ")";
		  			        openChkStr = cAttendance;
		  			    }
		  			}
		  			else {
					// 온라인시 출석표기
					// 1. 강의 기간 안에 open_date 가 있어야 한다.
					// 2. 수강 시간이 권장 시간보다 같거나 커야한다.
					// 3. 오픈 플래그가 Y여야 한다.
					// 4. 단원평가가 있을 경우 통과(P)해야 한다.
						//단원평가가 있을 경우

						if(quizYn == "Y") {
			  				//if((parseFloat(cStartDate) <= parseFloat(openDate) && parseFloat(cEndDate) >= parseFloat(openDate)) && (showTime > 0 && showTime <= totalTime) && openChk == "Y" && quizPass == "P") {
			  				if( (showTime > 0 && showTime <= totalTime) && openChk == "Y" && quizPass == "P") {
			  					openChkStr = "O";
			  				}
			  				//else if(openChk == "Y" && (parseFloat(cEndDate) < parseFloat(openDate) || (totalTime > 0 && showTime > totalTime) || quizPass != "P")) {
			  				else if(openChk == "Y" && ( (totalTime > 0 && showTime > totalTime) || quizPass != "P")) {
								openChkStr = "△";
			  				}
			  				else if(parseFloat(curDate) > parseFloat(cEndDate) && joinCount == 0) {
			  					openChkStr = "X";
			  				}
			  				else {
			  					openChkStr = "";
			  				}
						} else {
						//단원평가가 없는 경우
							//if((parseFloat(cStartDate) <= parseFloat(openDate) && parseFloat(cEndDate) >= parseFloat(openDate)) && (showTime > 0 && showTime <= totalTime) && openChk == "Y") {
							if( (showTime > 0 && showTime <= totalTime) && openChk == "Y") {
			  					openChkStr = "O";
			  				}
			  				//else if(openChk == "Y" && (parseFloat(cEndDate) < parseFloat(openDate) || (totalTime > 0 && showTime > totalTime))) {
							else if(openChk == "Y" && (totalTime > 0 && showTime > totalTime)) {
								openChkStr = "△";
			  				}
			  				else if(parseFloat(curDate) > parseFloat(cEndDate) && joinCount == 0) {
			  					openChkStr = "X";
			  				}
			  				else {
			  					openChkStr = "";
			  				}
						}
		  			}

		  			if(prevContentsView == "N" && CLASS_USER_TYPE != "P")
						lecLink = "<a href=\"javascript:alert('이전 강의를 수강 하셔야 합니다.')\">"+contentsName+"</a>";
					if(prevQuizPass != "P" && CLASS_USER_TYPE != "P" && QUIZ_CONFIG_YN == "Y")
						lecLink = "<a href=\"javascript:alert('이전  단원평가를 통과 하셔야 합니다.')\">"+contentsName+"</a>";

			  		if(contentsType != 'C' || serverPath !='' )
			  			prevContentsView = openChk;
			  		else
			  			prevContentsView = 'Y';

					if(quizYn == 'N' || quizPass == 'P')
						prevQuizPass ="P";
					else
						prevQuizPass = quizPass;

					textFilePathLink  = (textFilePath!="") ? "<a href=\"javascript:doFileDown('"+textFilePath+"')\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/tree/item.gif'  align='absmiddle' alt=강의교재다운></a>" : "";
					textFilePathLink += (asfFilePath!="") ? "<a href=\"javascript:doFileDown('"+asfFilePath+"')\" ><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/tree/fmedia.gif'  align='absmiddle' alt=동영상다운></a>" : "";
					
				} else {
					joinCount = "";
					totalTime = "";
					showTime = "";
					contentsImg = "icon_folder.gif";
					lecLink = contentsName;
					openChk = "";
					openChkStr = "";
					textFilePathLink = "";

				}

			    objStr += "<tr><td width='40' class=\"s_tab04_cen\">"+listNo+"</td>"
			    	+ "<td></td><td width='215'>"+contentsDepth+"<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/"+contentsImg+"' align='absmiddle'>&nbsp;"+lecLink+"</td>"
			    	+ "<td></td><td width='40' class=\"s_tab04_cen\">"+iconStr+"</td>"
			    	+ "<td></td><td width='130' class=\"s_tab04_cen\">"+dateStr1+dateStr2+"</td>"
			    	+ "<td></td><td width='45' class=\"s_tab04_cen\">"+openChkStr+"</td>"
			    	+ "<td></td><td width='45' class=\"s_tab04_cen\">"+joinCount+"</td>"
			    	+ "<td></td><td width='50' class=\"s_tab04_cen\">"+totalTime+"</td>"
			    	+ "<td></td><td width='50' class=\"s_tab04_cen\">"+showTime+"</td>"
			    	+ "<td></td><td width='45' class=\"s_tab04_cen\">"+textFilePathLink+"</td></tr>";

					if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"17\"></td></tr>";

					allItemCnt--;

				contentsDepth = "";
			    listNo++;
			} // end of for

			// SCORM API 설정
			if(COURSE_ID != ""){
				if(COURSE_CONTENTS_TYPE=="S")
					scormDiv(COURSE_ID, "block");
				else
					scormDiv(COURSE_ID, "none");
			}else
				scormDiv(COURSE_ID, "none");

		}
		objStr += "</table>";

		contentsListObj.innerHTML = objStr;
		contentsListObj.style.display = "block";
	 }

	function doFileDown(filePath){
       hiddenFrame.location.href = CONTEXTPATH+"/fileDownServlet?rFileName="+filePath+"&sFileName="+filePath+"&filePath=&fileSize=";
	}
	
	 function scormDiv(courseId, viewStyle){
	  	var scormApiObj = document.getElementById("scormApi");
	 	if(viewStyle == "block"){
		 	var objStr = "<iframe width=='0' height='0' frameborder='yes' scrolling='yes' src='"+CONTEXTPATH+"/ScormStudy.cmd?cmd=scormAppletInclude&pCourseId="+courseId+"' marginwidth='n' marginheight='n' name='wwws'></iframe>";
			scormApiObj.innerHTML = objStr;
		}
		scormApiObj.style.display = viewStyle;
	 }

	// 강의창 띄우기
	function goContents(courseid, contents_id, contentsWidth, contentsHeight)
	{
		var loc=CONTEXTPATH+"/CurriContents.cmd?cmd=lecContents&pCourseId="+courseid+"&pContentsId="+contents_id+"&MENUNO=0";
		var winOption = "left=0,top=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=auto,resizable=yes,width="+contentsWidth+",height="+contentsHeight;
		lecWin = window.open(loc, "lecWin", winOption);
		lecWin.focus();
	}

	//퀴즈창 띄우기
	function goQuiz(openChk,courseid, contents_id){
		if(openChk != 'Y'){
			alert('단원 수강후 단원 평가에 응시하여 주십시요');
		}else{
			var loc=CONTEXTPATH+"/CurriQuiz.cmd?cmd=curriQuizStShow&pCourseId="+courseid+"&pContentsId="+contents_id+"&MENUNO=0";
			var winOption = "left="+windowLeftPosition(610)+",top="+windowTopPosition(600)+",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=no,width=610,height=600";
			lecWin = window.open(loc, "lecWin", winOption);
			lecWin.focus();
		}
	}
	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}

	// 목차리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var CURRI_CODE = "";
	var CURRI_YEAR = "";
	var CURRI_TERM = "";
	var COURSE_ID = "";
	var COURSE_NAME = "";
	var FLAG_NAVI = "";
	var CONTENTS_WIDTH = "";
	var CONTENTS_HEIGHT = "";
    var CONTENTS_CNT = "";
    var QUIZ_CNT = "";

	function init(systemCode,contextPath,courseId,courseName, flagNavi, contentsWidth, contentsHeight) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.COURSE_ID = courseId;
		this.COURSE_NAME = courseName;
		this.FLAG_NAVI = flagNavi;
		this.CONTENTS_WIDTH = contentsWidth;
		this.CONTENTS_HEIGHT = contentsHeight;

		var f = document.WCMInput;
		this.CURRI_CODE = f.pCurriCode.value;
		this.CURRI_YEAR = f.pCurriYear.value;
		this.CURRI_TERM = f.pCurriTerm.value;
		this.CONTENTS_CNT = f.pContentsCnt.value;
		this.QUIZ_CNT = f.pQuizCnt.value;

		viewAutoButton('C',CONTENTS_CNT);
		viewAutoButton('Q',QUIZ_CNT);
		autoReload();
	}

	// global attribute
	var MANAGE_CONTENTS_ID = "";
	var MANAGE_CONTENTS_ORDER = "";
	var MANAGE_CONTENTS_TYPE = "";
	var INDEX_FILE = "";			// 시작파일
	var TEXT_INDEX_FILE = "";		// 강의교재
	var ASF_INDEX_FILE = "" ;       // 강의동영상
	var WORK_MODE = "";
	var REG_MODE = "";

	// 파일리스트 띄우기
	function openFileListTree(courseId, flag){
		popupbox1.clear();
		showPopupBox(popupbox1);
		var frame = "<iframe name='Frame1' width='100%' height='100%' frameborder='0' src='"+CONTEXTPATH+"/Contents.cmd?cmd=WCM&pCourseId="+courseId+"&pCourseName="+COURSE_NAME+"&pFlag="+flag+"'></iframe>";
		popupbox1.addContents(frame);
	}

	// 폼체크
	function formCheck(contentsType){
		var f = document.WCMInput;
		if(contentsType == 'C' || contentsType == 'CR'){
			if(f.pContentsNameChapter.value == ""){
				alert("목차명을 입력하세요");
				new Effect.Highlight("pContentsNameChapter");
				f.pContentsNameChapter.focus();
				return false;
			}
		}else if(contentsType == 'F'){
			if(f.pContentsName.value == ""){
				alert("목차명을 입력하세요");
				new Effect.Highlight("pContentsName");
				f.pContentsName.focus();
				return false;
			}
			if(f.pShowTime.value == ""){
				alert("기준학습시간을 입력하세요");
				new Effect.Highlight("pShowTime");
				f.pShowTime.focus();
				return false;
			}
			if(isNumber2(f.pShowTime) == false){
				new Effect.Highlight("pShowTime");
				f.pShowTime.focus();
				return false;
			}

			if(INDEX_FILE == "" && f.pLectureGubun[f.pLectureGubun.selectedIndex].value=="1"){
				alert("시작파일을 선택 하세요");
				new Effect.Highlight("indexFileName");
				return false;
			}
		}

		return true;
	}


	function autoReload(){
		CurriContentsWork.curriContentsListAuto(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID, autoReloadCallback);
	}

	// 목차리스트 뿌려주기
	function autoReloadCallback(data){
		var rowLength = 0;
		var allItemCnt = 0;

		if(data != null) {
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	viewAutoButton('C',rowLength);
	  	var contentsListObj = $("contentsList");
	  	var objStr = "<table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td align='center' height='50' bgcolor='#ffffff' colspan=\"17\">※ 등록된 목차가 없습니다. <a href=\"javascript:manageContents('W','CR');\"><font color=blue>[목차추가]</font></a></td></tr>";
	  	}else{

			var courseId="";
		  	var contentsImg = "ico_note.gif";
		  	//var quizLink = "";
		  	var contentsDepth = "";
		  	var itemNumView = "*";
		  	var previewLink="";
		  	var no = 0;
		  	var cMenu = "";
		  	var arrOrder = new Array();

		  	for(i=0;i<rowLength;i++){
    			var curriContentsDTO = data[i];

				courseId = curriContentsDTO.courseId;
		    	var contentsId = curriContentsDTO.contentsId;
		    	var contentsType = curriContentsDTO.contentsType;
		    	var contentsOrder = curriContentsDTO.contentsOrder;
		    	var contentsName = curriContentsDTO.contentsName;
		    	if(contentsName != "") {
		    		contentsName = contentsName.substring(0, 15)+"..";
		    	}
		    	var serverPath = curriContentsDTO.serverPath;
		    	var showTime = curriContentsDTO.showTime;
		    	if(showTime != "") {
		    		showTime = showTime+" 분";
		    	}
		    	var quizCount = curriContentsDTO.quizCount;
		    	var quizPoint = curriContentsDTO.quizPoint;
		    	var sizeApp = curriContentsDTO.sizeApp;
		    	var cWidth = curriContentsDTO.contentsWidth;
		    	var cHeight = curriContentsDTO.contentsHeight;

		    	var lectureGubun = curriContentsDTO.lectureGubun;
			    var lectureGubunCd = curriContentsDTO.lectureGubunCd;
//var offlineDate  = curriContentsDTO.startDate;
//var offlineTime  = curriContentsDTO.endDate;
//var offlineDateAll  = "";
    	    	var startDate = curriContentsDTO.startDate;
		    	var endDate = curriContentsDTO.endDate;
		    	var dateStr1 = "";
		    	var dateStr2 = "";
		    	var iconStr	= "";

				if(startDate != "" && endDate != "") {
					if(lectureGubunCd == "1") {
			    		dateStr1 = startDate.substring(4, 6)+"."+startDate.substring(6, 8)+" ~ ";
			    		dateStr2 = endDate.substring(4, 6)+"."+endDate.substring(6, 8);
			    	} else {
			    		dateStr1 = startDate.substring(4, 6)+"."+startDate.substring(6, 8)+" "+startDate.substring(8, 10)+":"+startDate.substring(10, 12)+"~";
			    		dateStr2 = endDate.substring(8, 10)+":"+endDate.substring(10, 12);
			    	}
				}

				if(contentsType == "F" && lectureGubunCd == "1") {
					iconStr	=	"<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_on.gif' align='absmiddle'>";
				} else if(contentsType == "F" && lectureGubunCd == "2") {
					iconStr	=	"<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_off.gif' align='absmiddle'>";
				}

		    	var viewWidth = "0";
		    	var viewHeight = "0";

		    	arrOrder = contentsOrder.split(".");
				var splitCnt = arrOrder.length;
				for(j=0; j<splitCnt; j++){
					if(parseInt(arrOrder[j]) > 0 )
						contentsDepth +="&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				arrOrder = null;
		    	contentsImg = "ico_note.gif";
		    	if(contentsType !="C" && serverPath != ""){
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

		    		// 미리보기 링크
		    		previewLink = "&nbsp;<a href=\"javascript:previewWin('"+serverPath+"','"+viewWidth+"','"+viewHeight+"');\" ><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_view01.gif' align='absmiddle' border='0'></a>";

				}else
					previewLink = "";

 // blended 일경우 필요함
// offlineDateAll = "";
// if(lectureGubunCd != "" && lectureGubunCd != "1" ){   // 온라인이 아닐경우..
//     offlineDateAll = offlineDate +"<br>(" + offlineTime +")";
// }

				// 아이콘/quiz/컨텍스트메뉴
				if(contentsType == "F"){
					contentsImg = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_note.gif' align='absmiddle'>";
					cMenu = "style='cursor:pointer' onmousedown=\"setContentsValue('"+contentsId+"','"+contentsOrder+"'); showContextMenu(cMenu2)\"";
					//quizLink = "<a href='"+CONTEXTPATH+"/CurriQuiz.cmd?cmd=curriQuizList&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM+"&pCourseId="+courseId+"&pCourseName="+COURSE_NAME+"&pContentsId="+contentsId+"&pContentsName="+contentsName+"'><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_quiz.gif' border='0' align='absmiddle'></a>";
				}
				else{
					contentsImg = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_folder.gif' align='absmiddle'>";
					//quizLink = "";
					quizCount = "";
					showTime = "";
					cMenu = "style='cursor:pointer' onmousedown=\"setContentsValue('"+contentsId+"','"+contentsOrder+"'); showContextMenu(cMenu1)\"";
				}

				if(c_right!="true" && u_right!="true" && d_right!="true"){
					cMenu = "";
				}
				
			    no++;
			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
				+" <td width='38' class=\"s_tab04_cen\">"+no+"</td>"
				+ "<td></td><td width='297'>"+contentsDepth+contentsImg+" "+contentsName+"&nbsp;&nbsp;<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_mgt.gif' align='absmiddle' "+cMenu+"></td>"
				+ "<td></td><td width='70' class=\"s_tab04_cen\">"+iconStr+"</td>"
				+ "<td></td><td width='70' class=\"s_tab04_cen\">"+previewLink+"</td>"
				+ "<td></td><td width='139' class=\"s_tab04_cen\">"+dateStr1+dateStr2+"</td>"				
				+ "<td></td><td width='65' class=\"s_tab04_cen\">"+showTime+"</td>"				
				+ "</tr><tr><td colspan='13' height='1'><div id='contentsWrite' style='width:100%;display:none'></td></tr>";

				if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"17\"></td></tr>";

				allItemCnt--;

		    	contentsDepth = "";
		    	dateStr1 = "";
		    	dateStr2 = "";
			} // end of for

		}
		objStr += "</table>";
		contentsListObj.innerHTML = objStr;
		contentsListObj.style.display = "block";
	 }

	// 컨텐츠 밸루세팅
	function setContentsValue(contentsId,contentsOrder){
		MANAGE_CONTENTS_ID = contentsId;
		MANAGE_CONTENTS_ORDER = contentsOrder;
	}


	function manageContents(workMode, contentsType){
		var f = document.WCMInput;

		//전역변수 설정
		MANAGE_CONTENTS_TYPE = contentsType;
		WORK_MODE = workMode;

		if(workMode == 'W' && contentsType == 'C'){
			initContentsWrite('ADD',contentsType);
			$("contentsWrite").style.display = "none";
			Effect.Appear("contentsWriteChapter");
			$("contentsSubject").style.display = "none";
			$("subContentsSubject").style.display = "block";
			$("chapterRegButton").style.display = "block";
			$("chapterModButton").style.display = "none";
		}else if(workMode == 'W' && contentsType == 'CR'){		// 루트에 장 추가할때
			initContentsWrite('ADD',contentsType);
			$("contentsWrite").style.display = "none";
			Effect.Appear("contentsWriteChapter");
			$("contentsSubject").style.display = "block";
			$("subContentsSubject").style.display = "none";
			$("chapterRegButton").style.display = "block";
			$("chapterModButton").style.display = "none";
			MANAGE_CONTENTS_ORDER = "";
		}else if(workMode == 'E' && contentsType == 'C'){
			$("contentsWrite").style.display = "none";
			getContentsInfo(MANAGE_CONTENTS_ID,workMode,contentsType);
			Effect.Appear("contentsWriteChapter");
			$("contentsSubject").style.display = "block";
			$("subContentsSubject").style.display = "none";
			$("chapterRegButton").style.display = "none";
			$("chapterModButton").style.display = "block";
		}else if(workMode == 'D' && contentsType == 'C'){

		}else if(workMode == 'W' && contentsType == 'F'){
			initContentsWrite('ADD',contentsType);
			$("contentsWriteChapter").style.display = "none";
			getContentsInfo(MANAGE_CONTENTS_ID,workMode,contentsType);
			Effect.Appear("contentsWrite");
			$("lessonRegButton").style.display = "block";
			$("lessonModButton").style.display = "none";
		}else if(workMode == 'E' && contentsType == 'F'){
			$("contentsWriteChapter").style.display = "none";
			getContentsInfo(MANAGE_CONTENTS_ID,workMode,contentsType);
			Effect.Appear("contentsWrite");
			$("lessonRegButton").style.display = "none";
			$("lessonModButton").style.display = "block";
		}else if(workMode == 'D' && contentsType == 'F'){

		}
	}

	// 목차정보가져오기
	function getContentsInfo(contentsId, workMode, regContentsType){
		CurriContentsWork.curriContentsInfo(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID, contentsId, workMode, regContentsType, getContentsInfoCallback);
	}

	// 목차정보세팅
	function getContentsInfoCallback(data){

   		var curriContentsDTO = data;

		var courseId = curriContentsDTO.courseId;
    	var contentsId = curriContentsDTO.contentsId;
    	var contentsType = curriContentsDTO.contentsType;
    	var contentsOrder = curriContentsDTO.contentsOrder;
    	var contentsName = curriContentsDTO.contentsName;
    	var serverPath = curriContentsDTO.serverPath;
    	var showTime = curriContentsDTO.showTime;
    	var quizCount = curriContentsDTO.quizCount;
    	var quizPoint = curriContentsDTO.quizPoint;
    	var sizeApp = curriContentsDTO.sizeApp;
    	var contentsWidth = curriContentsDTO.contentsWidth;
    	var contentsHeight = curriContentsDTO.contentsHeight;
    	var textFilePath = curriContentsDTO.filePath;		/* 2007.11.01 add */
        var asfFilePath = curriContentsDTO.asfFilePath;		/* 2008.1.2 add */
    	var parentContentsName = curriContentsDTO.parentContentsName;
    	var regContentsType = curriContentsDTO.regContentsType;
    	var workMode = curriContentsDTO.workMode;

	    // 온/오프라인
	    var lectureGubun = curriContentsDTO.lectureGubun;
	    var startDate  = curriContentsDTO.startDate;
    	var endDate    = curriContentsDTO.endDate;

		var f = document.WCMInput;
		if(MANAGE_CONTENTS_TYPE == "C"){
			f.pContentsNameChapter.value = contentsName;

		}else if(MANAGE_CONTENTS_TYPE == "F"){
			if(WORK_MODE == "E"){
				f.pContentsName.value = contentsName;
				f.pShowTime.value = showTime;
			    for(i=0;i<f.pSizeApp.length;i++){
		   	   	    if(sizeApp == f.pSizeApp[i].value){
			   	 	    f.pSizeApp[i].checked=true;
			   	    }
			    }
			    f.pContentsWidth.value = contentsWidth;
			    f.pContentsHeight.value = contentsHeight;

			    changeServerPath(serverPath);
				changeTextFilePath(textFilePath);
				changeAsfFilePath(asfFilePath);	    // 강의동영상

				if(parentContentsName !=null && parentContentsName != "")
					changeParentContentsName(parentContentsName);
				else
					changeParentContentsName(COURSE_NAME);

		        f.pLectureGubun.value = lectureGubun;

			    if(lectureGubun == "1") {
					f.pOnlineDate1.value = startDate;
			    	f.pOnlineDate2.value = endDate;

					f.pYY1.value     = startDate.substr(0,4);
				    f.pMM1.value     = startDate.substr(4,2);
				    f.pDD1.value     = startDate.substr(6,2);

				    f.pYY2.value     = endDate.substr(0,4);
				    f.pMM2.value     = endDate.substr(4,2);
				    f.pDD2.value     = endDate.substr(6,2);

				    $("onlineDateSetDiv").style.display = "block";
					$("offlineDateSetDiv").style.display = "none";
			    }
			    else if(lectureGubun == "2") {
			    	f.pOfflineDate1.value = startDate;
			    	f.pOfflineDate2.value = endDate;

					f.pOffYY1.value     = startDate.substr(0,4);
				    f.pOffMM1.value     = startDate.substr(4,2);
				    f.pOffDD1.value     = startDate.substr(6,2);
				    f.pOffHour1.value   = startDate.substr(8,2);
				    f.pOffMinute1.value = startDate.substr(10,2);

				    f.pOffYY2.value     = endDate.substr(0,4);
				    f.pOffMM2.value     = endDate.substr(4,2);
				    f.pOffDD2.value     = endDate.substr(6,2);
				    f.pOffHour2.value   = endDate.substr(8,2);
				    f.pOffMinute2.value = endDate.substr(10,2);

				    $("onlineDateSetDiv").style.display = "none";
					$("offlineDateSetDiv").style.display = "block";
			    }

			}else if(WORK_MODE == "W"){
				f.pContentsName.value = "";
				f.pShowTime.value = "";
				changeServerPath("");
				changeTextFilePath("");
                changeAsfFilePath("");	    // 강의동영상

				if(parentContentsName !=null && parentContentsName != "")
					changeParentContentsName(parentContentsName);
				else
					changeParentContentsName(COURSE_NAME);

				$("onlineDateSetDiv").style.display = "block";
				$("offlineDateSetDiv").style.display = "none";
			}
		}
	}

	//온오프 날짜세팅 보이기
	function showSetDate() {
		var f = document.WCMInput;
		var lectureGubun = f.pLectureGubun.value;

		if(lectureGubun == "1") {
			$("onlineDateSetDiv").style.display = "block";
			$("offlineDateSetDiv").style.display = "none";
		} else {
			$("offlineDateSetDiv").style.display = "block";
			$("onlineDateSetDiv").style.display = "none";
		}

	}

	// 상위목차명 변경
	function changeParentContentsName(contentsName){
		$("parentContentsName").innerHTML = contentsName;
		$("parentContentsName").style.display = "block";
	}

	// 시작파일명 변경
	function changeServerPath(serverPath){

		INDEX_FILE = serverPath;
		if(serverPath == ""){
			$("indexFileName").innerHTML = "<a href=\"javascript:openFileListTree('"+COURSE_ID+"','1');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='파일찾기'></a>";
		}else {
			$("indexFileName").innerHTML = serverPath + " &nbsp;&nbsp;&nbsp;<a href=\"javascript:openFileListTree('"+COURSE_ID+"','1');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='파일찾기'></a>";
		}

		$("indexFileName").style.display = "block";
	}

	// 강의교재명 변경
    function changeTextFilePath(textFilePath){
    	TEXT_INDEX_FILE = textFilePath;
    	if(textFilePath == ""){
    		$("textFileName").innerHTML = "<a href=\"javascript:openFileListTree('"+COURSE_ID+"','2');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='파일찾기'></a>";
    	}else {
    		$("textFileName").innerHTML = textFilePath + " &nbsp;&nbsp;&nbsp;<a href=\"javascript:openFileListTree('"+COURSE_ID+"','2');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='파일찾기'></a>&nbsp;<a href=\"javascript:delTextFilePath()\"><b>[강의교재없슴]</b></a>";
    	}
    
    	$("textFileName").style.display = "block";
    }
	
	// 강의동영상명 변경
	function changeAsfFilePath(asfFilePath){
		ASF_INDEX_FILE = asfFilePath;
		if(asfFilePath == ""){
			$("asfFileName").innerHTML = "<a href=\"javascript:openFileListTree('"+COURSE_ID+"','3');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='파일찾기'></a>";
		}else {
			$("asfFileName").innerHTML = asfFilePath + " &nbsp;&nbsp;&nbsp;<a href=\"javascript:openFileListTree('"+COURSE_ID+"','3');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='파일찾기'></a>&nbsp;<a href=\"javascript:delAsfFilePath()\"><b>[강의동영상없음]</b></a>";
		}

		$("asfFileName").style.display = "block";
	}
		
	// 강의교재 지워주기
    function delTextFilePath(){
		changeTextFilePath("");
	}

	// 강의동영상 지워주기
	function delAsfFilePath(){
		changeAsfFilePath("");
	}
	
	function CurriContentsObject(curriCode, curriYear, curriTerm, courseId, contentsId, contentsType,contentsOrder,contentsName,serverPath,showTime,sizeApp,contentsWidth,contentsHeight, lectureGubun, startDate, endDate, textFilePath, asfFilePath){
		
		this.curriCode = curriCode;
		this.curriYear = curriYear;
		this.curriTerm = curriTerm;
		this.courseId = courseId;
		this.contentsId = contentsId;
		this.contentsType = contentsType;
		this.contentsOrder = contentsOrder;
		this.contentsName = contentsName;
		this.serverPath = serverPath;
		this.showTime = showTime;
		this.sizeApp = sizeApp;
		this.contentsWidth = contentsWidth;
		this.contentsHeight = contentsHeight;
		this.lectureGubun = lectureGubun ;  // 온/오프 구분
		this.startDate  = startDate ;       // 오프라인 일자
		this.endDate  = endDate ;         // 오프라인 일자
		this.filePath = textFilePath;
		this.asfFilePath = asfFilePath;    // 강의동영상
		
	}

	function registContents(workMode,contentsType){

		var f = document.WCMInput;

		// 전역변수 설정
		MANAGE_CONTENTS_TYPE = contentsType;
		WORK_MODE = workMode;

		var contentsName = "";

		if(workMode == "W" && contentsType=="C"){
			if(formCheck(contentsType)){

				// 전역변수 설정
				REG_MODE = "ADD";

				var curriContentsDto =  new CurriContentsObject(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID, '', contentsType, MANAGE_CONTENTS_ORDER, ajaxEnc(f.pContentsNameChapter.value), '', '', '', '', '','');
				CurriContentsWork.curriContentsRegist(curriContentsDto, REG_MODE, registCurriContentsCallback);
			}else return;
		}else if(workMode == "E" && contentsType=="C"){
			if(formCheck(contentsType)){

				// 전역변수 설정
				REG_MODE = "EDIT";

				var curriContentsDto =  new CurriContentsObject(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID, MANAGE_CONTENTS_ID, contentsType, MANAGE_CONTENTS_ORDER, ajaxEnc(f.pContentsNameChapter.value), '', '', '', '', '','');
				CurriContentsWork.curriContentsRegist(curriContentsDto, REG_MODE, registCurriContentsCallback);

			}else return;
		}else if(workMode == "D" && contentsType=="C"){
			if(confirm("장을 삭제하시면 하위 목차까지 삭제됩니다.\n삭제하시겠습니까?")){
				// 전역변수 설정
				REG_MODE = "DEL";

				var curriContentsDto =  new CurriContentsObject(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID, '', contentsType, MANAGE_CONTENTS_ORDER, '', '', '', '', '', '','');
				CurriContentsWork.curriContentsRegist(curriContentsDto, REG_MODE, registCurriContentsCallback);
			}else
				return;
		}else if(workMode == "W" && contentsType=="F"){
			if(formCheck(contentsType)){
				contentsName = ajaxEnc(f.pContentsName.value);
				var showTime = f.pShowTime.value;
				var sizeApp = 'N';
			    for(i=0;i<f.pSizeApp.length;i++){
		   	   	    if(f.pSizeApp[i].checked == true){
			   		    sizeApp = f.pSizeApp[i].value;
			   	    }
			    }
				var contentsWidth = f.pContentsWidth.value;
				var contentsHeight = f.pContentsHeight.value;

                // 오프라인일경우...
				var lectureGubun = f.pLectureGubun.value;
//var startDate = (lectureGubun=="2") ? (f.pYY1.value + f.pMM1.value + f.pDD1.value + f.pHour1.value + f.pMinute1.value + "00") : "";
//var endDate   = (lectureGubun=="2") ? (f.pYY2.value + f.pMM2.value + f.pDD2.value + f.pHour2.value + f.pMinute2.value + "00") : "";
				var startDate = "";
				var endDate = "";
				if(lectureGubun == "1") {
					var onDate1 = f.pOnlineDate1.value;
					var onDate2 = f.pOnlineDate2.value;
					if(onDate1 != "" && onDate2 != "") {
						startDate = onDate1;
						endDate = onDate2.substring(0, 8) + "235959";
					}
				}
				else {
					var offDate1 = f.pOfflineDate1.value;
					var offDate2 = f.pOfflineDate2.value;
					if(offDate1 != "" && offDate2 != "") {
						startDate = f.pOffYY1.value + f.pOffMM1.value + f.pOffDD1.value + f.pOffHour1.value + f.pOffMinute1.value + "00";
						endDate = f.pOffYY2.value + f.pOffMM2.value + f.pOffDD2.value + f.pOffHour2.value + f.pOffMinute2.value + "00";
					}
				}

				// 전역변수 설정
				REG_MODE = "ADD";

				var curriContentsDto =  new CurriContentsObject(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID, '', contentsType, MANAGE_CONTENTS_ORDER, contentsName, INDEX_FILE, showTime, sizeApp, contentsWidth, contentsHeight, lectureGubun, startDate, endDate, TEXT_INDEX_FILE, ASF_INDEX_FILE);
				CurriContentsWork.curriContentsRegist(curriContentsDto, REG_MODE, registCurriContentsCallback);

			}else return;
		}else if(workMode == "E" && contentsType=="F"){
			if(formCheck(contentsType)){
				contentsName = ajaxEnc(f.pContentsName.value);
				var showTime = f.pShowTime.value;
				var sizeApp = 'N';
			    for(i=0;i<f.pSizeApp.length;i++){
		   	   	    if(f.pSizeApp[i].checked == true){
			   		    sizeApp = f.pSizeApp[i].value;
			   	    }
			    }
				var contentsWidth = f.pContentsWidth.value;
				var contentsHeight = f.pContentsHeight.value;

                // 오프라인일경우...
				var lectureGubun = f.pLectureGubun.value;
//var startDate = (lectureGubun=="2") ? (f.pYY1.value + f.pMM1.value + f.pDD1.value + f.pHour1.value + f.pMinute1.value + "00") : "";
//var endDate   = (lectureGubun=="2") ? (f.pYY2.value + f.pMM2.value + f.pDD2.value + f.pHour2.value + f.pMinute2.value + "00") : "";
				var startDate = "";
				var endDate = "";
				if(lectureGubun == "1") {
					var onDate1 = f.pOnlineDate1.value;
					var onDate2 = f.pOnlineDate2.value;
					if(onDate1 != "" && onDate2 != "") {
						startDate = onDate1;
						endDate = onDate2.substring(0, 8) + "235959";
					}
				}
				else {
					var offDate1 = f.pOfflineDate1.value;
					var offDate2 = f.pOfflineDate2.value;
					if(offDate1 != "" && offDate2 != "") {
						startDate = f.pOffYY1.value + f.pOffMM1.value + f.pOffDD1.value + f.pOffHour1.value + f.pOffMinute1.value + "00";
						endDate = f.pOffYY2.value + f.pOffMM2.value + f.pOffDD2.value + f.pOffHour2.value + f.pOffMinute2.value + "00";
					}
				}

				// 전역변수 설정
				REG_MODE = "EDIT";

				var curriContentsDto =  new CurriContentsObject(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID, MANAGE_CONTENTS_ID, contentsType, MANAGE_CONTENTS_ORDER, contentsName, INDEX_FILE, showTime, sizeApp, contentsWidth, contentsHeight, lectureGubun, startDate, endDate,TEXT_INDEX_FILE, ASF_INDEX_FILE);
				CurriContentsWork.curriContentsRegist(curriContentsDto, REG_MODE, registCurriContentsCallback);

			}else return;
		}else if(workMode == "D" && contentsType=="F"){
			if(confirm("목차를 삭제하시겠습니까?")){
				// 전역변수 설정
				REG_MODE = "DEL";

				var curriContentsDto =  new CurriContentsObject(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID, '', contentsType, MANAGE_CONTENTS_ORDER, '', '', '', '', '', '', '', '');
				CurriContentsWork.curriContentsRegist(curriContentsDto, REG_MODE, registCurriContentsCallback);
			}
			else
				return;
		}
	}

	function registCurriContentsCallback(data){
		var result = data;
	  	if(result > 0){
		  	initContentsWrite(REG_MODE,MANAGE_CONTENTS_TYPE);
			autoReload();
	  	}else{
		  	initContentsWrite(REG_MODE,MANAGE_CONTENTS_TYPE);
	  		return;
	  	}
	}

	function initContentsWrite(regMode,contentsType){
		var f = document.WCMInput;
  		if(regMode == 'ADD' && contentsType == 'F'){
  			f.pContentsName.value = "";
  			f.pShowTime.value = "";
  			f.pContentsWidth.value = "0";
  			f.pContentsHeight.value = "0";


		    for(i=0;i<f.pSizeApp.length;i++){
	   	   	    if(f.pSizeApp[i].value == 'N'){
		   	 	    f.pSizeApp[i].checked=true;
		   	    }
		    }
  			INDEX_FILE = "", TEXT_INDEX_FILE = "", ASF_INDEX_FILE = "";
  			changeServerPath(INDEX_FILE);		// 시작파일
 			changeTextFilePath(TEXT_INDEX_FILE);	    // 강의교재
            changeAsfFilePath(ASF_INDEX_FILE);	    // 강의동영상
            
  			f.pLectureGubun.value = "1";
			f.pOffYY1.value     = "";
			f.pOffMM1.value     = "";
			f.pOffDD1.value     = "";
			f.pOffHour1.value   = "";
			f.pOffMinute1.value = "";;
			f.pOffYY2.value     = "";
			f.pOffMM2.value     = "";
			f.pOffDD2.value     = "";
			f.pOffHour2.value   = "";
			f.pOffMinute2.value = "";
			f.pOfflineDate1.value = "";
			f.pOfflineDate2.value = "";
			f.pYY1.value			= "";
			f.pMM1.value			= "";
			f.pDD1.value			= "";
			f.pYY2.value			= "";
			f.pMM2.value			= "";
			f.pDD2.value			= "";
			f.pOnlineDate1.value = "";
			f.pOnlineDate2.value = "";

  		}else if(regMode == 'ADD' && (contentsType == 'C' || contentsType == 'CR')){
			f.pContentsNameChapter.value = "";
  			f.pShowTime.value = "";
  			INDEX_FILE = "", TEXT_INDEX_FILE = "", ASF_INDEX_FILE = "";
  			changeServerPath(INDEX_FILE);		// 시작파일
 			changeTextFilePath(TEXT_INDEX_FILE);
 			changeAsfFilePath(ASF_INDEX_FILE);	    // 강의동영상
 		}else if(regMode == 'EDIT' && contentsType == 'F'){
			f.pContentsNameChapter.value = "";
  			f.pShowTime.value = "";
  			f.pContentsWidth.value = "0";
  			f.pContentsHeight.value = "0";
		    for(i=0;i<f.pSizeApp.length;i++){
	   	   	    if(f.pSizeApp[i].value == 'N'){
		   	 	    f.pSizeApp[i].checked=true;
		   	    }
		    }
  			INDEX_FILE = "", TEXT_INDEX_FILE = "", ASF_INDEX_FILE = "";
  			changeServerPath(INDEX_FILE);		// 시작파일
 			changeTextFilePath(TEXT_INDEX_FILE);
 			changeAsfFilePath(ASF_INDEX_FILE);	    // 강의동영상
				
			$("contentsWrite").style.display = "none";
  		}else{
			MANAGE_CONTENTS_ID = "";
			MANAGE_CONTENTS_ORDER = "";

			$("contentsWriteChapter").style.display = "none";
			$("contentsWrite").style.display = "none";
  		}
	}

	function closeContentsWrite(mode){
		if(mode == 'C'){
			$("contentsWriteChapter").style.display = "none";
		}else{
			$("contentsWrite").style.display = "none";
		}
	}

	function goScormUpload() {	// SCORM Contents Upload
		var str = CONTEXTPATH+"/CurriContents.cmd?cmd=curriSCMFrame&pMode=MyPage&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM+"&pCourseId="+COURSE_ID+"&pCourseName="+COURSE_NAME;
		ShowInfo = window.open(str,"scorm","toolbar=0,scrollbars=yes,directories=0,status=1,menubar=0,left=100,top=100,resizable=yes,width=470,height=450");
	}

	function goWebFtp(server) {
		// server : 0: web
		//			1: vod 1
		//			2: vod 2
		var url = CONTEXTPATH+"/Contents.cmd?cmd=webFtp&pMode=MyPage&pCourseId="+COURSE_ID+"&pServer="+server;
		window.open(url,"ftp_win","top=100,left=162,toolbar=0,scrollbars=no,directories=0,status=0,menubar=0,width=740,height=500,resizable=yes");
	}

	function viewAutoButton(type,cnt){
		if(type == 'C'){
			if(cnt == 0) {
				$("addContentsAutoDiv").style.display="block";
				$("addContentsTd").style.width="90px";
			} else {
				$("addContentsAutoDiv").style.display="none";
				$("addContentsTd").style.width="0px";
			}
		}

		if(type == 'Q'){
			//if(cnt == 0) {
			//	$("addQuizAutoDiv").style.display="block";
			//	$("addQuizTd").style.width="100px";
			//} else {
			//	$("addQuizAutoDiv").style.display="none";
			//	$("addQuizTd").style.width="0px";
			//}
		}
	}

	// 교재목차 이월
	function addContentsAuto(){
		var yes = confirm("교재 목차를 자동이월 합니다.");
		if(yes){
			CurriContentsWork.curriContentsAuto(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID,{
				callback:function(data) {
					var result = data;
				  	if(result != '0'){
						viewAutoButton('C',result);
						autoReload();
					}
				  	else
				  		return;
				}
			});
		}else
			return;
	}

	// 단원평가 이월
	function addQuizAuto(){
		var yes = confirm("단원평가를 자동이월 하려합니다.\n\n반드시 초기에 한번만 단원평가이월을 진행해 주셔야 합니다.\n\n자동이월 하시겠습니까?");
		if(yes){
			CurriQuizWork.curriQuizAuto(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID,{
				callback:function(data) {
					var result = data;
				  	if(result != '0'){
						viewAutoButton('Q',result);
						alert("단원평가를 이월하였습니다.");
						autoReload();
					}
				  	else{
				  		alert("단원평가를 이월하지 못하였습니다.\n 단원평가 등록여부를 확인해주세요.");
				  		return;
				  	}
				}
			});
		}else
			return;
	}
	/*
	// 단원평가 이월
	function addQuizAuto() {
		var yes = confirm("단원평가를 자동이월 하려합니다.\n\n반드시 초기에 한번만 단원평가이월을 진행해 주셔야 합니다.\n\n자동이월 하시겠습니까?");
		if(yes){
			var f = document.WCMInput;
			f.action = CONTEXTPATH+'/CurriQuiz.cmd?cmd=curriQuizAuto';
			f.submit();
		}
	}
	*/
	/*LCMS Contents*/
	// LCMS로부터 교재 가져오기 함수
	function goLCMSList(pSystemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId) {
		var msg = "전체교재 가져오기를 선택하면 가져오려는 콘텐츠를 선택시\n이전에 등록된 교재 목차는 모두 삭제됩니다.\n그래도 계속하시겠습니까?";
		if(!confirm(msg)) return;

		var URL = CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/contents/connector.jsp?pSystemCode="+pSystemCode+"&pCurriCode="+pCurriCode+"&pCurriYear="+pCurriYear+"&pCurriTerm="+pCurriTerm+"&pCourseId="+pCourseId;
		window.open(URL, 'lcmsList', 'width=900, height=600,toolbar=no,menubar=no,status=yes,scrollbars=no,resizable=auto');
	}

	// 컨텐츠 미리보기
	function previewWin(serverPath, width, height) {
		var contentsUrl = CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/contents/previewContents.jsp?pServerPath="+serverPath;
		window.open(contentsUrl, "popup1", "width="+width+",height="+height+",resizable=no,scrollbars=no,top=0,left=0");
	}

	function goCourseList() {
		document.location = CONTEXTPATH+"/CurriSubCourse.cmd?cmd=curriSubCourseList&pMode=MyPage&pCateCode=&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM;
	}
	
	//자이닉스 업로드
	function goEszUpload(courseid){
		var f = document.WCMInput;
		f.action = CONTEXTPATH+"/WebTreeEsz.cmd?cmd=EszContentsUpload&pCourseId="+courseid;
		f.submit();
	}

	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}

	// ��������Ʈ
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
	var INDEX_FILE = "";			// ��������
	var TEXT_INDEX_FILE = "";		// ���Ǳ���
	var ASF_INDEX_FILE = "" ;       // ���ǵ�����
	var WORK_MODE = "";
	var REG_MODE = "";

	// ���ϸ���Ʈ ����
	function openFileListTree(courseId, flag){
		popupbox1.clear();
		showPopupBox(popupbox1);
		var frame = "<iframe name='Frame1' width='100%' height='100%' frameborder='0' src='"+CONTEXTPATH+"/Contents.cmd?cmd=WCM&pCourseId="+courseId+"&pCourseName="+COURSE_NAME+"&pFlag="+flag+"'></iframe>";
		popupbox1.addContents(frame);
	}

	// ��üũ
	function formCheck(contentsType){
		var f = document.WCMInput;
		if(contentsType == 'C' || contentsType == 'CR'){
			if(f.pContentsNameChapter.value == ""){
				alert("�������� �Է��ϼ���");
				new Effect.Highlight("pContentsNameChapter");
				f.pContentsNameChapter.focus();
				return false;
			}
		}else if(contentsType == 'F'){
			if(f.pContentsName.value == ""){
				alert("�������� �Է��ϼ���");
				new Effect.Highlight("pContentsName");
				f.pContentsName.focus();
				return false;
			}
			if(f.pShowTime.value == ""){
				alert("�����н��ð��� �Է��ϼ���");
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
				alert("���������� ���� �ϼ���");
				new Effect.Highlight("indexFileName");
				return false;
			}
		}

		return true;
	}


	function autoReload(){
		CurriContentsWork.curriContentsListAuto(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID, autoReloadCallback);
	}

	// ��������Ʈ �ѷ��ֱ�
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
		  	objStr += "<tr><td align='center' height='50' bgcolor='#ffffff' colspan=\"17\">�� ��ϵ� ������ �����ϴ�. <a href=\"javascript:manageContents('W','CR');\"><font color=blue>[�����߰�]</font></a></td></tr>";
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
		    		showTime = showTime+" ��";
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
		    		// ������ ���뿩��
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

		    		// �̸����� ��ũ
		    		previewLink = "&nbsp;<a href=\"javascript:previewWin('"+serverPath+"','"+viewWidth+"','"+viewHeight+"');\" ><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_view01.gif' align='absmiddle' border='0'></a>";

				}else
					previewLink = "";

 // blended �ϰ�� �ʿ���
// offlineDateAll = "";
// if(lectureGubunCd != "" && lectureGubunCd != "1" ){   // �¶����� �ƴҰ��..
//     offlineDateAll = offlineDate +"<br>(" + offlineTime +")";
// }

				// ������/quiz/���ؽ�Ʈ�޴�
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

	// ������ ��缼��
	function setContentsValue(contentsId,contentsOrder){
		MANAGE_CONTENTS_ID = contentsId;
		MANAGE_CONTENTS_ORDER = contentsOrder;
	}


	function manageContents(workMode, contentsType){
		var f = document.WCMInput;

		//�������� ����
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
		}else if(workMode == 'W' && contentsType == 'CR'){		// ��Ʈ�� �� �߰��Ҷ�
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

	// ����������������
	function getContentsInfo(contentsId, workMode, regContentsType){
		CurriContentsWork.curriContentsInfo(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID, contentsId, workMode, regContentsType, getContentsInfoCallback);
	}

	// ������������
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

	    // ��/��������
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
				changeAsfFilePath(asfFilePath);	    // ���ǵ�����

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
                changeAsfFilePath("");	    // ���ǵ�����

				if(parentContentsName !=null && parentContentsName != "")
					changeParentContentsName(parentContentsName);
				else
					changeParentContentsName(COURSE_NAME);

				$("onlineDateSetDiv").style.display = "block";
				$("offlineDateSetDiv").style.display = "none";
			}
		}
	}

	//�¿��� ��¥���� ���̱�
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

	// ���������� ����
	function changeParentContentsName(contentsName){
		$("parentContentsName").innerHTML = contentsName;
		$("parentContentsName").style.display = "block";
	}

	// �������ϸ� ����
	function changeServerPath(serverPath){

		INDEX_FILE = serverPath;
		if(serverPath == ""){
			$("indexFileName").innerHTML = "<a href=\"javascript:openFileListTree('"+COURSE_ID+"','1');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='����ã��'></a>";
		}else {
			$("indexFileName").innerHTML = serverPath + " &nbsp;&nbsp;&nbsp;<a href=\"javascript:openFileListTree('"+COURSE_ID+"','1');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='����ã��'></a>";
		}

		$("indexFileName").style.display = "block";
	}

	// ���Ǳ���� ����
    function changeTextFilePath(textFilePath){
    	TEXT_INDEX_FILE = textFilePath;
    	if(textFilePath == ""){
    		$("textFileName").innerHTML = "<a href=\"javascript:openFileListTree('"+COURSE_ID+"','2');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='����ã��'></a>";
    	}else {
    		$("textFileName").innerHTML = textFilePath + " &nbsp;&nbsp;&nbsp;<a href=\"javascript:openFileListTree('"+COURSE_ID+"','2');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='����ã��'></a>&nbsp;<a href=\"javascript:delTextFilePath()\"><b>[���Ǳ������]</b></a>";
    	}
    
    	$("textFileName").style.display = "block";
    }
	
	// ���ǵ������ ����
	function changeAsfFilePath(asfFilePath){
		ASF_INDEX_FILE = asfFilePath;
		if(asfFilePath == ""){
			$("asfFileName").innerHTML = "<a href=\"javascript:openFileListTree('"+COURSE_ID+"','3');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='����ã��'></a>";
		}else {
			$("asfFileName").innerHTML = asfFilePath + " &nbsp;&nbsp;&nbsp;<a href=\"javascript:openFileListTree('"+COURSE_ID+"','3');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='����ã��'></a>&nbsp;<a href=\"javascript:delAsfFilePath()\"><b>[���ǵ��������]</b></a>";
		}

		$("asfFileName").style.display = "block";
	}
		
	// ���Ǳ��� �����ֱ�
    function delTextFilePath(){
		changeTextFilePath("");
	}

	// ���ǵ����� �����ֱ�
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
		this.lectureGubun = lectureGubun ;  // ��/���� ����
		this.startDate  = startDate ;       // �������� ����
		this.endDate  = endDate ;         // �������� ����
		this.filePath = textFilePath;
		this.asfFilePath = asfFilePath;    // ���ǵ�����
		
	}

	function registContents(workMode,contentsType){

		var f = document.WCMInput;

		// �������� ����
		MANAGE_CONTENTS_TYPE = contentsType;
		WORK_MODE = workMode;

		var contentsName = "";

		if(workMode == "W" && contentsType=="C"){
			if(formCheck(contentsType)){

				// �������� ����
				REG_MODE = "ADD";

				var curriContentsDto =  new CurriContentsObject(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID, '', contentsType, MANAGE_CONTENTS_ORDER, ajaxEnc(f.pContentsNameChapter.value), '', '', '', '', '','');
				CurriContentsWork.curriContentsRegist(curriContentsDto, REG_MODE, registCurriContentsCallback);
			}else return;
		}else if(workMode == "E" && contentsType=="C"){
			if(formCheck(contentsType)){

				// �������� ����
				REG_MODE = "EDIT";

				var curriContentsDto =  new CurriContentsObject(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID, MANAGE_CONTENTS_ID, contentsType, MANAGE_CONTENTS_ORDER, ajaxEnc(f.pContentsNameChapter.value), '', '', '', '', '','');
				CurriContentsWork.curriContentsRegist(curriContentsDto, REG_MODE, registCurriContentsCallback);

			}else return;
		}else if(workMode == "D" && contentsType=="C"){
			if(confirm("���� �����Ͻø� ���� �������� �����˴ϴ�.\n�����Ͻðڽ��ϱ�?")){
				// �������� ����
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

                // ���������ϰ��...
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

				// �������� ����
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

                // ���������ϰ��...
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

				// �������� ����
				REG_MODE = "EDIT";

				var curriContentsDto =  new CurriContentsObject(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID, MANAGE_CONTENTS_ID, contentsType, MANAGE_CONTENTS_ORDER, contentsName, INDEX_FILE, showTime, sizeApp, contentsWidth, contentsHeight, lectureGubun, startDate, endDate,TEXT_INDEX_FILE, ASF_INDEX_FILE);
				CurriContentsWork.curriContentsRegist(curriContentsDto, REG_MODE, registCurriContentsCallback);

			}else return;
		}else if(workMode == "D" && contentsType=="F"){
			if(confirm("������ �����Ͻðڽ��ϱ�?")){
				// �������� ����
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
  			changeServerPath(INDEX_FILE);		// ��������
 			changeTextFilePath(TEXT_INDEX_FILE);	    // ���Ǳ���
            changeAsfFilePath(ASF_INDEX_FILE);	    // ���ǵ�����
            
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
  			changeServerPath(INDEX_FILE);		// ��������
 			changeTextFilePath(TEXT_INDEX_FILE);
 			changeAsfFilePath(ASF_INDEX_FILE);	    // ���ǵ�����
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
  			changeServerPath(INDEX_FILE);		// ��������
 			changeTextFilePath(TEXT_INDEX_FILE);
 			changeAsfFilePath(ASF_INDEX_FILE);	    // ���ǵ�����
				
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

	// ������� �̿�
	function addContentsAuto(){
		var yes = confirm("���� ������ �ڵ��̿� �մϴ�.");
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

	// �ܿ��� �̿�
	function addQuizAuto(){
		var yes = confirm("�ܿ��򰡸� �ڵ��̿� �Ϸ��մϴ�.\n\n�ݵ�� �ʱ⿡ �ѹ��� �ܿ����̿��� ������ �ּž� �մϴ�.\n\n�ڵ��̿� �Ͻðڽ��ϱ�?");
		if(yes){
			CurriQuizWork.curriQuizAuto(CURRI_CODE,CURRI_YEAR,CURRI_TERM,COURSE_ID,{
				callback:function(data) {
					var result = data;
				  	if(result != '0'){
						viewAutoButton('Q',result);
						alert("�ܿ��򰡸� �̿��Ͽ����ϴ�.");
						autoReload();
					}
				  	else{
				  		alert("�ܿ��򰡸� �̿����� ���Ͽ����ϴ�.\n �ܿ��� ��Ͽ��θ� Ȯ�����ּ���.");
				  		return;
				  	}
				}
			});
		}else
			return;
	}
	/*
	// �ܿ��� �̿�
	function addQuizAuto() {
		var yes = confirm("�ܿ��򰡸� �ڵ��̿� �Ϸ��մϴ�.\n\n�ݵ�� �ʱ⿡ �ѹ��� �ܿ����̿��� ������ �ּž� �մϴ�.\n\n�ڵ��̿� �Ͻðڽ��ϱ�?");
		if(yes){
			var f = document.WCMInput;
			f.action = CONTEXTPATH+'/CurriQuiz.cmd?cmd=curriQuizAuto';
			f.submit();
		}
	}
	*/
	/*LCMS Contents*/
	// LCMS�κ��� ���� �������� �Լ�
	function goLCMSList(pSystemCode, pCurriCode, pCurriYear, pCurriTerm, pCourseId) {
		var msg = "��ü���� �������⸦ �����ϸ� ���������� �������� ���ý�\n������ ��ϵ� ���� ������ ��� �����˴ϴ�.\n�׷��� ����Ͻðڽ��ϱ�?";
		if(!confirm(msg)) return;

		var URL = CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/contents/connector.jsp?pSystemCode="+pSystemCode+"&pCurriCode="+pCurriCode+"&pCurriYear="+pCurriYear+"&pCurriTerm="+pCurriTerm+"&pCourseId="+pCourseId;
		window.open(URL, 'lcmsList', 'width=900, height=600,toolbar=no,menubar=no,status=yes,scrollbars=no,resizable=auto');
	}

	// ������ �̸�����
	function previewWin(serverPath, width, height) {
		var contentsUrl = CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/contents/previewContents.jsp?pServerPath="+serverPath;
		window.open(contentsUrl, "popup1", "width="+width+",height="+height+",resizable=no,scrollbars=no,top=0,left=0");
	}

	function goCourseList() {
		document.location = CONTEXTPATH+"/CurriSubCourse.cmd?cmd=curriSubCourseList&pMode=MyPage&pCateCode=&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM;
	}
	
	//���̴н� ���ε�
	function goEszUpload(courseid){
		var f = document.WCMInput;
		f.action = CONTEXTPATH+"/WebTreeEsz.cmd?cmd=EszContentsUpload&pCourseId="+courseid;
		f.submit();
	}

	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}

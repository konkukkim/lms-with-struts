	// ��������Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var COURSEID = "";
	var COURSENAME = "";
	var FLAGNAVI = "";
	var CONTENTSWIDTH = "";
	var CONTENTSHEIGHT = "";

	function init(systemCode,contextPath,courseId,courseName, flagNavi, contentsWidth, contentsHeight) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.COURSEID = courseId;
		this.COURSENAME = courseName;
		this.FLAGNAVI = flagNavi;
		this.CONTENTSWIDTH = contentsWidth;
		this.CONTENTSHEIGHT = contentsHeight;

		autoReload(courseId);
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
		var frame = "<iframe name='Frame1' width='100%' height='100%' frameborder='0' src='"+CONTEXTPATH+"/Contents.cmd?cmd=WCM&pCourseId="+courseId+"&pCourseName="+COURSENAME+"&pFlag="+flag+"'></iframe>";
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

	function errorMessage(){
		alert("�۾��� ������ �߻��߽��ϴ�.\n��õ� ���ּ���!!!");
	}

	function autoReload(courseId){
		ContentsWork.contentsListAuto(courseId, autoReloadCallback);
	}

	// ��������Ʈ �ѷ��ֱ�
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
		  	objStr += "<tr><td align='center' height='50' bgcolor='#ffffff' colspan=\"17\">�� ��ϵ� ������ �����ϴ�. <a href=\"javascript:manageContents('W','CR');\"><font color=blue>[�����߰�]</font></a></td></tr>";

	  	}else{

			var courseId="";
		  	var contentsImg = "ico_note.gif";
		  	var contentsDepth = "";
		  	var itemNumView = "*";
		  	var previewLink="";
		  	var no = 0;
		  	var cMenu = "";
		  	var arrOrder = new Array();

		  	for(i=0;i<rowLength;i++){
    			var contentsDTO = data[i];

				courseId = contentsDTO.courseId;
		    	var contentsId = contentsDTO.contentsId;
		    	var contentsType = contentsDTO.contentsType;
		    	var contentsOrder = contentsDTO.contentsOrder;
		    	var contentsName = contentsDTO.contentsName;
		    	if(contentsName != "") {
		    		contentsName = contentsName.substring(0, 25)+"..";
		    	}
		    	var serverPath = contentsDTO.serverPath;
		    	var showTime = contentsDTO.showTime;
		    	if(showTime != "") {
		    		showTime = showTime+" ��";
		    	}
		    	var quizCount = contentsDTO.quizCount;
		    	var quizPoint = contentsDTO.quizPoint;
		    	var progressRate = contentsDTO.progressRate;
		    	var sizeApp = contentsDTO.sizeApp;

		    	var cWidth = contentsDTO.contentsWidth;
		    	var cHeight = contentsDTO.contentsHeight;

		    	var viewWidth = "0";
		    	var viewHeight = "0";

		    	var lectureGubun = contentsDTO.lectureGubun;
		    	
		    	var iconStr = "";				

				if(contentsType == "F" && lectureGubun == "1") {
					iconStr	=	"<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_on.gif' align='absmiddle'>";
				} else if(contentsType == "F" && lectureGubun == "2") {
					iconStr	=	"<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_off.gif' align='absmiddle'>";
				}

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
					if(FLAGNAVI == "N"){
			    		if(sizeApp =="Y"){
			    			viewWidth = cWidth;
			    			viewHeight = cHeight;
			    		}else{
							viewWidth = CONTENTSWIDTH;
							viewHeight = CONTENTSHEIGHT;
			    		}
					}else{
						viewWidth = CONTENTSWIDTH;
						viewHeight = CONTENTSHEIGHT;
					}

		    		// �̸����� ��ũ
		    		previewLink = "&nbsp;<a href=\"javascript:previewWin('"+serverPath+"','"+viewWidth+"','"+viewHeight+"');\" ><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_view01.gif' align='absmiddle' border='0'></a>";

				}else
					previewLink = "";

				// ������/quiz/���ؽ�Ʈ�޴�
				if(contentsType == "F"){
					contentsImg = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_note.gif' align='absmiddle'>";
					cMenu = "style='cursor:pointer' onmousedown=\"setContentsValue('"+contentsId+"','"+contentsOrder+"'); showContextMenu(cMenu2)\"";
				}
				else{
					contentsImg = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_folder.gif' align='absmiddle'>";
					quizCount = "";
					showTime = "";
					cMenu = "style='cursor:pointer' onmousedown=\"setContentsValue('"+contentsId+"','"+contentsOrder+"'); showContextMenu(cMenu1)\"";
				}

				if(c_right!="true" && u_right!="true" && d_right!="true"){
					cMenu = "";
				}
				
			    no++;
				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
				+"<td width='38' class=\"s_tab04_cen\">"+no+"</td>"
				+ "<td></td><td width='' >"+contentsDepth+contentsImg+" "+contentsName+"&nbsp;<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_mgt.gif' align='absmiddle' "+cMenu+"></td>"
				+ "<td></td><td width='77' class=\"s_tab04_cen\">"+iconStr+"</td>"
				+ "<td></td><td width='75' class=\"s_tab04_cen\">"+previewLink+"</td>"				
				//+ "<td></td><td width='' class=\"s_tab04_cen\">"+quizCount+"</td>"
				+ "<td></td><td width='145' class=\"s_tab04_cen\">"+showTime+"</td>"
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
		ContentsWork.contentsInfo(COURSEID, contentsId, workMode, regContentsType, getContentsInfoCallback);
	}

	// ������������
	function getContentsInfoCallback(data){

   		var contentsDTO = data;

		var courseId = contentsDTO.courseId;
    	var contentsId = contentsDTO.contentsId;
    	var contentsType = contentsDTO.contentsType;
    	var contentsOrder = contentsDTO.contentsOrder;
    	var contentsName = contentsDTO.contentsName;
    	var serverPath = contentsDTO.serverPath;
    	var showTime = contentsDTO.showTime;
    	var quizCount = contentsDTO.quizCount;
    	var quizPoint = contentsDTO.quizPoint;
    	var progressRate = contentsDTO.progressRate;
    	var sizeApp = contentsDTO.sizeApp;
    	var contentsWidth = contentsDTO.contentsWidth;
    	var contentsHeight = contentsDTO.contentsHeight;
		var textFilePath = contentsDTO.filePath;		/* 2007.11.01 add */
		var asfFilePath = contentsDTO.asfFilePath;		/* 2008.1.2 add */
    	var parentContentsName = contentsDTO.parentContentsName;
    	var regContentsType = contentsDTO.regContentsType;
    	var workMode = contentsDTO.workMode;

	    // ��/��������
	    var lectureGubun = contentsDTO.lectureGubun;
	    
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
					changeParentContentsName(COURSENAME);

		        f.pLectureGubun.value = lectureGubun;
			    

			} else if(WORK_MODE == "W") {
				f.pContentsName.value = "";
				f.pShowTime.value = "";
				changeServerPath("");
				changeTextFilePath("");
                changeAsfFilePath("");	    // ���ǵ�����
				
				if(parentContentsName !=null && parentContentsName != "")
					changeParentContentsName(parentContentsName);
				else
					changeParentContentsName(COURSENAME);
			}
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
			$("indexFileName").innerHTML = "<a href=\"javascript:openFileListTree('"+COURSEID+"','1');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='����ã��'></a>";
		}else {
			$("indexFileName").innerHTML = serverPath + " &nbsp;&nbsp;&nbsp;<a href=\"javascript:openFileListTree('"+COURSEID+"','1');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='����ã��'></a>";
		}

		$("indexFileName").style.display = "block";
	}


	// ���Ǳ���� ����
	function changeTextFilePath(textFilePath){
		TEXT_INDEX_FILE = textFilePath;
		if(textFilePath == ""){
			$("textFileName").innerHTML = "<a href=\"javascript:openFileListTree('"+COURSEID+"','2');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='����ã��'></a>";
		}else {
			$("textFileName").innerHTML = textFilePath + " &nbsp;&nbsp;&nbsp;<a href=\"javascript:openFileListTree('"+COURSEID+"','2');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='����ã��'></a>&nbsp;<a href=\"javascript:delTextFilePath()\"><b>[���Ǳ������]</b></a>";
		}

		$("textFileName").style.display = "block";
	}

	// ���ǵ������ ����
	function changeAsfFilePath(asfFilePath){
		ASF_INDEX_FILE = asfFilePath;
		if(asfFilePath == ""){
			$("asfFileName").innerHTML = "<a href=\"javascript:openFileListTree('"+COURSEID+"','3');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='����ã��'></a>";
		}else {
			$("asfFileName").innerHTML = asfFilePath + " &nbsp;&nbsp;&nbsp;<a href=\"javascript:openFileListTree('"+COURSEID+"','3');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/newfile.gif' align='absmiddle' border='0' alt='����ã��'></a>&nbsp;<a href=\"javascript:delAsfFilePath()\"><b>[���ǵ��������]</b></a>";
		}

		$("asfFileName").style.display = "block";
	}

	// ���ǵ����� �����ֱ�
	function delAsfFilePath(){
		changeAsfFilePath("");
	}
			
	// ���Ǳ��� �����ֱ�
	function delTextFilePath(){
		changeTextFilePath("");
	}
	
	function ContentsObject(courseId, contentsId, contentsType, contentsOrder, contentsName, serverPath, showTime, sizeApp, contentsWidth, contentsHeight, lectureGubun, textFilePath, asfFilePath){
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
		this.lectureGubun = lectureGubun;
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

				var contentsDto =  new ContentsObject(COURSEID, '', contentsType, MANAGE_CONTENTS_ORDER, ajaxEnc(f.pContentsNameChapter.value), '', '', '', '', '', '', '');
				ContentsWork.contentsRegist(contentsDto, REG_MODE, registContentsCallback);
			}else return;
		}else if(workMode == "E" && contentsType=="C"){
			if(formCheck(contentsType)){

				// �������� ����
				REG_MODE = "EDIT";

				var contentsDto =  new ContentsObject(COURSEID, MANAGE_CONTENTS_ID, contentsType, MANAGE_CONTENTS_ORDER, ajaxEnc(f.pContentsNameChapter.value), '', '', '', '', '', '', '');
				ContentsWork.contentsRegist(contentsDto, REG_MODE, registContentsCallback);

			}else return;
		}else if(workMode == "D" && contentsType=="C"){
			if(confirm("���� �����Ͻø� ���� �������� �����˴ϴ�.\n�����Ͻðڽ��ϱ�?")){
				// �������� ����
				REG_MODE = "DEL";

				var contentsDto =  new ContentsObject(COURSEID, '', contentsType, MANAGE_CONTENTS_ORDER, '', '', '', '', '', '', '', '');
				ContentsWork.contentsRegist(contentsDto, REG_MODE, registContentsCallback);
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
				
				// �������� ����
				REG_MODE = "ADD";

				var contentsDto =  new ContentsObject(COURSEID, '', contentsType, MANAGE_CONTENTS_ORDER, contentsName, INDEX_FILE, showTime, sizeApp, contentsWidth, contentsHeight, lectureGubun, TEXT_INDEX_FILE, ASF_INDEX_FILE);
				ContentsWork.contentsRegist(contentsDto, REG_MODE, registContentsCallback);

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
				
				// �������� ����
				REG_MODE = "EDIT";

				var contentsDto =  new ContentsObject(COURSEID, MANAGE_CONTENTS_ID, contentsType, MANAGE_CONTENTS_ORDER, contentsName, INDEX_FILE, showTime, sizeApp, contentsWidth, contentsHeight, lectureGubun, TEXT_INDEX_FILE, ASF_INDEX_FILE);
				ContentsWork.contentsRegist(contentsDto, REG_MODE, registContentsCallback);

			}else return;
		}else if(workMode == "D" && contentsType=="F"){
			if(confirm("������ �����Ͻðڽ��ϱ�?")){
				// �������� ����
				REG_MODE = "DEL";

				var contentsDto =  new ContentsObject(COURSEID, '', contentsType, MANAGE_CONTENTS_ORDER, '', '', '', '', '', '');
				ContentsWork.contentsRegist(contentsDto, REG_MODE, registContentsCallback);
			}
			else
				return;
		}
	}

	function registContentsCallback(data){
		var result = data;
	  	if(result > 0){
		  	initContentsWrite(REG_MODE,MANAGE_CONTENTS_TYPE);
			autoReload(COURSEID);
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
			changeTextFilePath(TEXT_INDEX_FILE);	// ���Ǳ���
            changeAsfFilePath(ASF_INDEX_FILE);	    // ���ǵ�����

  			f.pLectureGubun.value = "1";

  		}else if(regMode == 'ADD' && (contentsType == 'C' || contentsType == 'CR')){
			f.pContentsNameChapter.value = "";
  			f.pShowTime.value = "";
  			INDEX_FILE = "", TEXT_INDEX_FILE = "", ASF_INDEX_FILE = "";
  			changeServerPath(INDEX_FILE);			// ��������
  			changeTextFilePath(TEXT_INDEX_FILE);	// ���Ǳ���
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
  			changeServerPath(INDEX_FILE);				// ��������
			changeTextFilePath(TEXT_INDEX_FILE);		// ���Ǳ���
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
		var str = CONTEXTPATH+"/Contents.cmd?cmd=SCMFrame&pMode=MyPage&pCourseId="+COURSEID+"&pCourseName="+COURSENAME;
		ShowInfo = window.open(str,"scorm","toolbar=0,scrollbars=yes,directories=0,status=1,menubar=0,left=100,top=100,resizable=yes,width=470,height=450");
	}

	function goWebFtp(server) {
		// server : 0: web
		//			1: vod 1
		//			2: vod 2
		var url = CONTEXTPATH+"/Contents.cmd?cmd=webFtp&pMode=MyPage&pCourseId="+COURSEID+"&pServer="+server;
		window.open(url,"ftp_win","top=100,left=162,toolbar=0,scrollbars=no,directories=0,status=0,menubar=0,width=740,height=500,resizable=yes");
	}

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

	// ������� ���ư���
	function goList() {
		document.location.href = CONTEXTPATH+"/Course.cmd?cmd=courseList&pMode=MyPage";
	}
	
	//���̴н� ���ε�
	function goEszUpload(courseid){
		var f = document.WCMInput;
		f.action = CONTEXTPATH+"/WebTreeEsz.cmd?cmd=EszContentsUpload&pCourseId="+courseid+"&pCourseName="+COURSENAME;
		f.submit();
	}

	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}

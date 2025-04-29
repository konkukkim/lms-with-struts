	// 목차리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var CLASS_USER_TYPE = "";	
	var CURRI_CODE = "";
	var CURRI_YEAR = 0;
	var CURRI_TERM = 0;
	var COURSE_ID = "";
	var COURSE_CONTENTS_TYPE = "";
	var FLAG_NAVI = "Y";
	var CONTENTS_WIDTH = "";
	var CONTENTS_HEIGHT = "";
	var QUIZ_CONFIG_YN = "N";
	var PGUBUN = "";

	// 초기설정
	function init(systemCode,contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		
		var f = document.f;
		this.CURRI_CODE = f.pCurriCode.value;
		this.CURRI_YEAR = f.pCurriYear.value;
		this.CURRI_TERM = f.pCurriTerm.value;
		this.COURSE_ID = f.courseId.value;
		this.CLASS_USER_TYPE = f.classUserType.value;
		this.QUIZ_CONFIG_YN = f.quizConfigYn.value;
		this.PGUBUN = f.pGubun.value;

		changeCourseInfo();
	}

	// 과목정보세팅
	function changeCourseInfo() {
		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId !=null && tmpCourseId != "")
			COURSE_ID = DWRUtil.getValue("pCourseId");

		CourseWork.courseInfo(COURSE_ID, courseInfoCallback);
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
		PublishCurriSubWork.lecContentsListAuto(CURRI_CODE, CURRI_YEAR, CURRI_TERM, COURSE_ID, autoReloadCallback);
	}
	

	// 강의리스트 뿌리기
	function autoReloadCallback(data) {
		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null) {
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var contentsListObj = $("contentsList");
	  	var objStr = "<table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">등록된 강의가 없습니다.</td></tr>";
	  	}else{

		  	var contentsImg = "icon_note.gif";
		  	var iconStr = "";
		  	var fileIconStr1 = "";
		  	var fileIconStr2 = "";
		  	var listQuizStatus = "";
		  	var lecLink = "";
		  	var contentsDepth = "";
		  	var arrOrder = new Array();

			var listNo = 1;
		  	for(i=0;i<rowLength;i++){

  				var lecContentsDTO = data[i];

                var curriCode = lecContentsDTO.curriCode;
                var curriYear = lecContentsDTO.curriYear;
                var curriTerm = lecContentsDTO.curriTerm;
                                
                var contentsId  = lecContentsDTO.contentsId;
                var contentsType = lecContentsDTO.contentsType;
                var contentsOrder = lecContentsDTO.contentsOrder;
                var contentsName = lecContentsDTO.contentsName;
                var serverPath  = lecContentsDTO.serverPath;
                var showTime    = lecContentsDTO.showTime;
                var sizeApp     = lecContentsDTO.sizeApp;
                var cWidth      = lecContentsDTO.contentsWidth;
                var cHeight     = lecContentsDTO.contentsHeight;
                var cLectureGubun = lecContentsDTO.lectureGubun;
                var regDate		=	lecContentsDTO.regDate;
                var profName	=	lecContentsDTO.profName;
                var filePath	=	lecContentsDTO.filePath;
                
                var rFileName1	=	lecContentsDTO.RFileName1;
                var sFileName1	=	lecContentsDTO.SFileName1;
                var attachPath1	=	lecContentsDTO.attachPath1;
                var rFileName2	=	lecContentsDTO.RFileName2;
                var sFileName2	=	lecContentsDTO.SFileName2;
                var attachPath2	=	lecContentsDTO.attachPath2;
               
                
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

		    	var openChkStr = "";						// 컨텐츠를 수강시 표시
		    	contentsImg = "icon_note.gif";				// 챕터와 레슨을 표기하는 이미지
		    	listQuizStatus = "";
		    	//--	레슨일 때 리스트 표기
		    	if(contentsType !="C" || serverPath != ""){

		    		//-- 챕터명, 레슨명
		    		if(cLectureGubun == "1") {
				  		if(contentsType =="C" && serverPath == ""){
				  			lecLink = "<a href=\"javascript:InitSCO('"+curriCode+"','"+COURSE_ID+"','"+contentsId+"','"+userId+"','"+userName+"'"
				  				+",'"+curriYear+"','"+curriTerm+"','"+SYSTEMCODE+"','"+chkPreview+"','"+viewWidth+"','"+viewHeight+"')\">"+contentsName.substring(0, 15)+"</a>";
				  		}else{
				  			lecLink = "<a href=\"javascript:goContents('"+serverPath+"','"+viewWidth+"','"+viewHeight+"')\">"+contentsName.substring(0, 15)+"</a>";
				  		}
		    		}
		    		else if(cLectureGubun == "2"){
		    			lecLink = contentsName.substring(0, 17);
		    		}		    		

				} else {
					iconStr = "";
					showTime = "";
					contentsImg = "icon_folder.gif";
					lecLink = contentsName;	
					profName = "";
					regDate = "";
					filePath = "";
					serverPath = "";
					
					rFileName1 = "";
					sFileName1 = "";
					attachPath1 = "";
					rFileName2 = "";
					sFileName2 = "";
					attachPath2 = "";
				}
				
				if(contentsType == "F") {
					if(rFileName1 != "" && attachPath1 != "") fileIconStr1	=	"<a href=\"javascript:fileDownload('"+rFileName1+"','"+sFileName1+"','"+attachPath1+"','')\"><img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif\" align='absmiddle' border='0'></a>";
					if(rFileName2 != "" && attachPath2 != "") fileIconStr2	=	"<a href=\"javascript:fileDownload('"+rFileName2+"','"+sFileName2+"','"+attachPath2+"','')\">DOWN</a>";
				}

			    objStr += "\n<tr><td width='38' class=\"s_tab04_cen\">"+listNo+"</td>"
			    	+ "\n<td></td><td width='337'>"+contentsDepth+"<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/"+contentsImg+"' align='absmiddle'>&nbsp;"+lecLink+"</td>"
			    	+ "\n<td></td><td width='60' class=\"s_tab04_cen\">"+profName+"</td>"
			    	+ "\n<td></td><td width='80' class=\"s_tab04_cen\">"+regDate+"</td>"
			    	+ "\n<td></td><td width='60' class=\"s_tab04_cen\">"+fileIconStr1+"</td>"
			    	+ "\n<td></td><td width='90' class=\"s_tab04_cen\">"+fileIconStr2+"</td></tr>";

					if(allItemCnt != 1) objStr += "\n<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

					allItemCnt--;

				contentsDepth = "";
				fileIconStr1	=	"";
				fileIconStr2	=	"";
			    listNo++;
			} // end of for


		}
		objStr += "</table>";

		contentsListObj.innerHTML = objStr;
		contentsListObj.style.display = "block";	
	}
	
	// 강의창 띄우기
	function goContents(serverPath, contentsWidth, contentsHeight)
	{
		var loc=CONTEXTPATH+"/PublishCurriSub.cmd?cmd=publishLecContentsShow&pServerPath="+serverPath+"&MENUNO=0";
		var winOption = "left=0,top=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=auto,resizable=yes,width="+contentsWidth+",height="+contentsHeight;
		lecWin = window.open(loc, "lecWin", winOption);
		lecWin.focus();
	}

	// 파일다운로드
	function fileDownload(rfilename, sfilename, filepath, filesize) {
       var loc = CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }
	
	// 과정리스트로 돌아가기	
	function goCurriList() {
		document.location = CONTEXTPATH+"/PublishCurriSub.cmd?cmd=publishCurriSubPageList&pPareCode1=99999&pPareCode2=00002&MENUNO=1&pMode=Home&MainMenu=Y&pGubun="+PGUBUN;
	}
	
	// 토론방 게시글 리스트로 이동
	function goForum(forumId) {
		var	param	=	"&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM+"&pCourseId="+COURSE_ID+"&pForumId="+forumId+"&pGubun="+PGUBUN;
		var	goUrl	=	CONTEXTPATH+"/PublishCurriSub.cmd?cmd=publishForumContentsPagingList&MENUNO=1&pMode=Home";
		document.location = goUrl + param;
	}
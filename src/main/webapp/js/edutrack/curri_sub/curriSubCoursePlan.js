	// 목차리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var COURSE_ID = "";
	var COURSE_COUNT = "";

	function init(systemCode,contextPath,courseId,courseCount) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.COURSE_ID = courseId;
		this.COURSE_COUNT = courseCount;

		autoReload();
	}

	 // 폼체크
	 function formCheck(){
		var f = document.Input;

		if(f.pInfoText1.value != ""){
			if(f.pInfoTitle1.value == ""){
				alert("제목을 입력해주세요");
				new Effect.Highlight("pInfoTitle1");
				f.pInfoTitle1.focus();
				return false;
			}
		}
		if(f.pInfoText2.value != ""){
			if(f.pInfoTitle2.value == ""){
				alert("제목을 입력해주세요");
				new Effect.Highlight("pInfoTitle2");
				f.pInfoTitle2.focus();
				return false;
			}
		}
		if(f.pInfoText3.value != ""){
			if(f.pInfoTitle3.value == ""){
				alert("제목을 입력해주세요");
				new Effect.Highlight("pInfoTitle3");
				f.pInfoTitle3.focus();
				return false;
			}
		}
		if(f.pInfoText4.value != ""){
			if(f.pInfoTitle4.value == ""){
				alert("제목을 입력해주세요");
				new Effect.Highlight("pInfoTitle4");
				f.pInfoTitle4.focus();
				return false;
			}
		}
		if(f.pInfoText5.value != ""){
			if(f.pInfoTitle5.value == ""){
				alert("제목을 입력해주세요");
				new Effect.Highlight("pInfoTitle5");
				f.pInfoTitle5.focus();
				return false;
			}
		}

		return true;
	 }

	function openCoursePlanEdit() {
		initCoursePlanWrite();
		$("modButtonDiv").style.display = "none";
		$("regButtonDiv").style.display = "block";

		// 슬라이드 효과줄때
		//new Effect.SlideUp("coursePlanInfoDiv");

		// fade in/out 효과줄때
		Effect.Fade("coursePlanInfoDiv");
		getCoursePlanInfo();	// 정보를 받아와서 세팅
		Effect.Appear("coursePlanWriteDiv");
	}

	function showCoursePlan() {
		$("regButtonDiv").style.display = "none";
		$("modButtonDiv").style.display = "block";

		// 슬라이드 효과줄때
		//new Effect.SlideUp("coursePlanWriteDiv");
		//window.setTimeout("new Effect.SlideDown('coursePlanInfoDiv')",850);

		// fade in/out 효과줄때
		Effect.Fade("coursePlanWriteDiv");
		Effect.Appear("coursePlanInfoDiv");
	}

	function closeCoursePlanEdit() {
		if($("regButtonDiv") != null)
			$("regButtonDiv").style.display = "none";

		if($("modButtonDiv") != null)
			$("modButtonDiv").style.display = "block";

		$("coursePlanWriteDiv").style.display = "none"
	}

	// 화면초기화
	function initCoursePlanWrite(){
		var f = document.Input;
		f.pInfoTitle1.value="";
		f.pInfoText1.value="";
		f.pInfoTitle2.value="";
		f.pInfoText2.value="";
		f.pInfoTitle3.value="";
		f.pInfoText3.value="";
		f.pInfoTitle4.value="";
		f.pInfoText4.value="";
		f.pInfoTitle5.value="";
		f.pInfoText5.value="";
	}

	// 과목정보 가져오기
	function getCoursePlanInfo(){
		CurriSubCoursePlanWork.curriSubCoursePlanShowAuto(COURSE_ID,"INFO",getCoursePlanInfoInfoCallback);
	}

	// 과목정보세팅
	function getCoursePlanInfoInfoCallback(data) {
		var f = document.Input;

		if(data != null){
			var curriSubCoursePlanDTO = data;
			f.pInfoTitle1.value=curriSubCoursePlanDTO.infoTitle1;
			f.pInfoText1.value=curriSubCoursePlanDTO.infoText1;
			f.pInfoTitle2.value=curriSubCoursePlanDTO.infoTitle2;
			f.pInfoText2.value=curriSubCoursePlanDTO.infoText2;
			f.pInfoTitle3.value=curriSubCoursePlanDTO.infoTitle3;
			f.pInfoText3.value=curriSubCoursePlanDTO.infoText3;
			f.pInfoTitle4.value=curriSubCoursePlanDTO.infoTitle4;
			f.pInfoText4.value=curriSubCoursePlanDTO.infoText4;
			f.pInfoTitle5.value=curriSubCoursePlanDTO.infoTitle5;
			f.pInfoText5.value=curriSubCoursePlanDTO.infoText5;
	  	}else{
			f.pInfoTitle1.value="학습목표";
			f.pInfoTitle2.value="과목개요";
			f.pInfoTitle3.value="강의방식";
			f.pInfoTitle4.value="평가방법";
			f.pInfoTitle5.value="참고문헌";
	  	}
	}

	// 전체 내용 보여주기
	function autoReload(){
		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId !=null && tmpCourseId != "")
			COURSE_ID = DWRUtil.getValue("pCourseId");

		DWREngine.beginBatch();
		CurriSubCourseWork.curriSubCourseInfo(COURSE_ID,curriSubCourseInfoCallback);
		CurriSubCoursePlanWork.curriSubCoursePlanShowAuto(COURSE_ID,"SHOW",coursePlanShowCallback);
		CurriSubCoursePlanWork.profInfo(COURSE_ID,profInfoCallback);
		DWREngine.endBatch()
	}

	// 강의계획서 받아오기
	function coursePlanShow(){
		CurriSubCoursePlanWork.curriSubCoursePlanShowAuto(COURSE_ID,"SHOW",coursePlanShowCallback);
	}

	// 과목정보 뿌려주기
	function curriSubCourseInfoCallback(data){
	  	var courseInfoObj = $("courseInfoDiv");
	  	var objStr = "";

	  	if(data == null){
		  	objStr += "등록괸 과목정보가 없습니다.";
	  	}else{
	  		var curriSubCourseDTO = data;
	  		
	  		if(COURSE_COUNT ==1){
				$("courseNameDiv").innerHTML = curriSubCourseDTO.courseName;
				$("courseNameDiv").style.display = "block";
			}
			objStr +="과제 : "+curriSubCourseDTO.reportBase+"%, 출석 : "+curriSubCourseDTO.attendBase
                  + "%, 토론 : "+curriSubCourseDTO.forumBase+"%, 기타1 : "+curriSubCourseDTO.etc1Base+"%, 기타2 : "+curriSubCourseDTO.etc2Base+"%";
			//"시험 : "+curriSubCourseDTO.examBase+"%,
		}

		courseInfoObj.innerHTML = objStr;
		courseInfoObj.style.display = "block";
	}


	// 교수정보 뿌려주기
	function profInfoCallback(data){

		if(data != null){
			var profInfoDTO = data;

			$("profNameDiv").innerHTML = profInfoDTO.userName;
			$("profNameDiv").style.display = "block";
			$("profName2Div").innerHTML = profInfoDTO.userName + " ["+profInfoDTO.userId+"]";
			$("profName2Div").style.display = "block";
			$("profMajorDiv").innerHTML = profInfoDTO.major;
			$("profMajorDiv").style.display = "block";
			$("profBookDiv").innerHTML = profInfoDTO.books;
			$("profBookDiv").style.display = "block";
			$("profCareerDiv").innerHTML = profInfoDTO.career;
			$("profCareerDiv").style.display = "block";
			$("profIntroDiv").innerHTML = " "+profInfoDTO.intro;
			$("profIntroDiv").style.display = "block";
			if(profInfoDTO.userPhoto != ""){
				$("profPhotoDiv").innerHTML = "<img src='/data/"+profInfoDTO.photoPath+"/"+profInfoDTO.userPhoto+"' width='100' height='120'>";
				$("profPhotoDiv").style.display = "block";
			}else{
				$("profPhotoDiv").innerHTML = "";
				$("profPhotoDiv").style.display = "block";
			}
			//"+CONTEXTPATH+"
		}
	 }

	// 강의계획서 뿌려주기
	function coursePlanShowCallback(data){
		closeCoursePlanEdit();
		if($("attachFileDiv") != null)	$("attachFileDiv").style.display = "none";

		var curriSubCoursePlanDTO = data;
	  	var coursePlanInfoObj = $("coursePlanInfoDiv");
	  	
	  	var objStr = "<table width='670' border='0' cellspacing='0' cellpadding='0'>";	  	

	  	if(data == null){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"2\">등록된 강의계획서가 없습니다.</td></tr>"
		  			+ "<tr class=\"s_tab03\"><td colspan=\"2\"></td></tr>";
	  	}else{

    		//첨부파일이 있다면..
    		if(curriSubCoursePlanDTO.RFileName != "" && curriSubCoursePlanDTO.SFileName != "") {
    			var attachFileObj	=	$("attachFileDiv");
    			var fileObj = "<table width='335' border='0' cellspacing='0' cellpadding='0'>"
    						+ "<tr><td class=\"s_tab_view01\" style='width:125px'>강의계획서 파일</td>"
    						+ "<td class=\"s_tab_view03\" style='width:'><img src=\"/img/"+curriSubCoursePlanDTO.systemCode+"/common/ico_file.gif\" align='absmiddle'>&nbsp;"
    						+ "<a href=\"javascript:fileDownload('"+curriSubCoursePlanDTO.RFileName+"','"+curriSubCoursePlanDTO.SFileName+"','"+curriSubCoursePlanDTO.filePath+"','"+curriSubCoursePlanDTO.fileSize+"')\">"
    						+ curriSubCoursePlanDTO.RFileName+"</a>&nbsp;&nbsp;";

				if(curriSubCoursePlanDTO.modDate == "M" || curriSubCoursePlanDTO.modDate == "P" || curriSubCoursePlanDTO.modDate == "C" || curriSubCoursePlanDTO.modDate == "J") {
	    			fileObj += "<a href=\"javascript:fileDelete('"+curriSubCoursePlanDTO.curriCode+"','"+curriSubCoursePlanDTO.curriYear+"','"+curriSubCoursePlanDTO.curriTerm+"','"+COURSE_ID+"')\">"
	    					+ "<font color='#778899'>[파일삭제]</a>";
				}

    			fileObj += "</td></tr></table>";
    			attachFileObj.innerHTML = fileObj;
    			attachFileObj.style.display = "block";
    		}

			if(curriSubCoursePlanDTO.infoTitle1 != ""){
				objStr +="<tr><td class=\"s_tab_view01\" style='width:119px'>"+curriSubCoursePlanDTO.infoTitle1+"</td>"
                       + "<td class=\"s_tab_view03\" style='width:551px'>"+curriSubCoursePlanDTO.infoText1+"</td></tr>"
                       + "<tr class=\"s_tab03\"><td colspan=\"2\"></td></tr>";
            }
			if(curriSubCoursePlanDTO.infoTitle2 != ""){
				objStr +="<tr><td class=\"s_tab_view01\" style='width:119px'>"+curriSubCoursePlanDTO.infoTitle2+"</td>"
                       + "<td class=\"s_tab_view03\" style='width:551px'>"+curriSubCoursePlanDTO.infoText2+"</td></tr>"
                       + "<tr class=\"s_tab03\"><td colspan=\"2\"></td></tr>";
            }
			if(curriSubCoursePlanDTO.infoTitle3 != ""){
				objStr +="<tr><td class=\"s_tab_view01\" style='width:119px'>"+curriSubCoursePlanDTO.infoTitle3+"</td>"
                       + "<td class=\"s_tab_view03\" style='width:551px'>"+curriSubCoursePlanDTO.infoText3+"</td></tr>"
                       + "<tr class=\"s_tab03\"><td colspan=\"2\"></td></tr>";
            }
			if(curriSubCoursePlanDTO.infoTitle4 != ""){
				objStr +="<tr><td class=\"s_tab_view01\" style='width:119px'>"+curriSubCoursePlanDTO.infoTitle4+"</td>"
                       + "<td class=\"s_tab_view03\" style='width:551px'>"+curriSubCoursePlanDTO.infoText4+"</td></tr>"
                       + "<tr class=\"s_tab03\"><td colspan=\"2\"></td></tr>";
            }
			if(curriSubCoursePlanDTO.infoTitle5 != ""){
				objStr +="<tr><td class=\"s_tab_view01\" style='width:119px'>"+curriSubCoursePlanDTO.infoTitle5+"</td>"
                       + "<td class=\"s_tab_view03\" style='width:551px'>"+curriSubCoursePlanDTO.infoText5+"</td></tr>"
                       + "<tr class=\"s_tab03\"><td colspan=\"2\"></td></tr>";
            }
            
		}
		objStr += "</table>";

		coursePlanInfoObj.innerHTML = objStr;
		coursePlanInfoObj.style.display = "block";
	 }

	function fileDelete(curricode, curriyear, curriterm, courseid) {
		if(confirm("첨부파일을 삭제하시겠습니까?"))
			CurriSubCoursePlanWork.planFileDelete(curricode, curriyear, curriterm, courseid, fileDeleteCallback);
		else
			return;
	}

	function fileDeleteCallback(data) {
		var result = data;

		if(result != "0") {
			autoReload();
		} else {
			alert('파일을 삭제하지 못했습니다.');
		}
	}

	function fileDownload(rfilename, sfilename, filepath, filesize){
    	hiddenFrame.location.href = CONTEXTPATH+"/fileDownServlet?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
    }

	// 강의계획서 객체
	function CcoursePlanObject(infoTitle1,infoText1,infoTitle2,infoText2,infoTitle3,infoText3,infoTitle4,infoText4,infoTitle5,infoText5){
		this.infoTitle1 = infoTitle1;
		this.infoText1 = infoText1;
		this.infoTitle2 = infoTitle2;
		this.infoText2 = infoText2;
		this.infoTitle3 = infoTitle3;
		this.infoText3 = infoText3;
		this.infoTitle4 = infoTitle4;
		this.infoText4 = infoText4;
		this.infoTitle5 = infoTitle5;
		this.infoText5 = infoText5;
	}


	 // 강의계획서 등록/수정
	 function registCoursePlan(){
	 	var f = document.Input;
	 	var inputFile = document.InputFile;
		if(formCheck()){

			var infoTitle1 = ajaxEnc(f.pInfoTitle1.value);
			var infoText1 = ajaxEnc(f.pInfoText1.value);
			var infoTitle2 = ajaxEnc(f.pInfoTitle2.value);
			var infoText2 = ajaxEnc(f.pInfoText2.value);
			var infoTitle3 = ajaxEnc(f.pInfoTitle3.value);
			var infoText3 = ajaxEnc(f.pInfoText3.value);
			var infoTitle4 = ajaxEnc(f.pInfoTitle4.value);
			var infoText4 = ajaxEnc(f.pInfoText4.value);
			var infoTitle5 = ajaxEnc(f.pInfoTitle5.value);
			var infoText5 = ajaxEnc(f.pInfoText5.value);

			var coursePlanDto =  new CcoursePlanObject(infoTitle1,infoText1,infoTitle2,infoText2,infoTitle3,infoText3,infoTitle4,infoText4,infoTitle5,infoText5);
			CurriSubCoursePlanWork.curriSubCoursePlanRegist(coursePlanDto,COURSE_ID,registCoursePlanCallback);

		}else
			return;
	 }

	 // 강의계획서 등록/수정 결과
	 function registCoursePlanCallback(data){
		var result = data;
	  	if(result > 0){
		  	closeCoursePlanEdit();
		  	coursePlanShow();
	  	}else{
	  		return;
	  	}
	 }

	 // 강의계획서 파일업로드 팝업창 열기
	 function openUploadFilePopup() {
	 	var url = "/CurriSubCoursePlan.cmd?cmd=openCoursePlanFilePopup&pCourseId="+COURSE_ID;
	 	window.open(url, "fileUpload", "width=500, height=400, top=0, left=0, toolbar=0,directories=0,status=0,menubar=0,scrollbars=no");
	 }

	 function callbackAfterRegist() {
	 	alert('ping ping ping');
	 	autoReload();
	 }


	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}
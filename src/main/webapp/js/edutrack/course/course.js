	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";

	function init(systemCode,contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;

		autoReload();
	}


	// 교재관리로 이동하기..
	function goMangeCourse(courseId, courseName){
		var vUrl = "/Contents.cmd?cmd=contentsList&pMode=MyPage&pCourseId="+courseId+"&pCourseName="+courseName;
		document.location = vUrl ;
	}
	
	// 교재추가
	function goAdd() {
		initCourseWrite();
		$("courseListDiv").style.display = "none";
		$("modButtonDiv").style.display = "none";
		$("regButtonDiv").style.display = "block";
		Effect.Appear("courseWriteDiv");
	}

	// 교재수정
	function goEdit(courseId) {
		initCourseWrite();
		$("courseListDiv").style.display = "none";
		$("regButtonDiv").style.display = "none";
		$("modButtonDiv").style.display = "block";

		getCourseInfo(courseId);
		Effect.Appear("courseWriteDiv");
	}

	function goList(){
		$("courseWriteDiv").style.display = "none";
		$("courseList").innerHTML = "";
		Effect.Appear("courseListDiv");
		autoReload();
	}

	// 페이징처리
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
		autoReload();
	}

	function submitCheck(){
		var f = document.Input;
		if(f.pProfId.value == ""){
			alert("교수자를 선택하셔야 합니다.");
			f.pProfId.focus();
		}
		else{
			if(!validate(f)) return false;
			else return true;
		}
	}

	// 과목리스트 받아오기-검색
	function goSearch(){
		var f = document.f;
		var curPage = 1;
		var sTarget = f.pSTarget[f.pSTarget.selectedIndex].value;
		var sWord = ajaxEnc(f.pSWord.value);

		CourseWork.courseListAuto(curPage, sTarget, sWord, autoReloadCallback);
	}

	// 과목리스트 받아오기
	function autoReload(){
		var f = document.f;
		var curPage = f.curPage.value;
		var sTarget = f.pSTarget[f.pSTarget.selectedIndex].value;
		var sWord = ajaxEnc(f.pSWord.value);

		CourseWork.courseListAuto(curPage, sTarget, sWord, autoReloadCallback);
	}

	// 과목리스트 뿌려주기
	function autoReloadCallback(data){
	  	var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;
	  	var paggingStr = data.pagging;
		var rowLength = 0;
		if(dataList != null)
			rowLength = dataList.length;

	  	var courseListObj = $("courseList");
	  	var paggingObj = $("getPagging");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

		if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"13\">등록된 과목이 없습니다.</td></tr>";
	  	}else{
			var onlineStr = "";
			
		  	for(i=0;i<rowLength;i++){
				var courseDTO = dataList[i];

		    	var courseId = courseDTO.courseId;
		    	var courseName = courseDTO.courseName;
		    	var profId = courseDTO.profId;
		    	var flagUse = courseDTO.flagUse;
		    	var flagUseName = courseDTO.flagUseName;
		    	var regDate = courseDTO.regDate;
		    	var onlineYn = courseDTO.onlineYn;
		    	if(onlineYn == "Y") {
		    		onlineStr = "Online";
		    	} else {
		    		onlineStr = "OffLine";
		    	}

				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
				+"<td width='60' class=\"s_tab04_cen\">"+totalCount+"</td>"
				+ "<td></td><td width='98' class=\"s_tab04_cen\">"+courseId+"</td>"
				+ "<td></td><td width='' class=\"s_tab04\"><a href=\"javascript:goEdit('"+courseId+"');\">"+courseName+"</a></td>"
				+ "<td></td><td width='66' class=\"s_tab04_cen\">"+profId+"</td>"
				+ "<td></td><td width='50' class=\"s_tab04_cen\"><font color='#FF9900'><b>"+flagUseName+"</b></font></td>"
				+ "<td></td><td width='80' class=\"s_tab04_cen\">"+onlineStr+"</td>"
				+ "<td></td><td width='78' class=\"s_tab04_cen\"><a href=\"javascript:goMangeCourse('"+courseId+"','"+courseName+"');\">[교재관리]</td>"
				+ "</tr>";

				if(totalCount > 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"13\"></td></tr>";

				totalCount--;
			} // end of for

		}
		objStr += "</table>";

		courseListObj.innerHTML = objStr;
		courseListObj.style.display = "block";
	}

	// 과목정보 가져오기
	function getCourseInfo(courseId){
		CourseWork.courseInfo(courseId,getCourseInfoCallback);
	}

	
	// 과목정보세팅
	function getCourseInfoCallback(data) {

		var courseId =  data.courseId;
		var courseName =  data.courseName;
		var profId =  data.profId;
		var contentsWidth =  data.contentsWidth;
		var contentsHeight =  data.contentsHeight;
		var flagUse =  data.flagUse;
		var flagNavi =  data.flagNavi;
		var contentsType =  data.contentsType;
		var courseImg1 =  data.courseImg1;
		var courseImg2 =  data.courseImg2;
		var onlineYn = data.onlineYn;

		var f = document.Input;
	  	if(courseId != null && courseId != ''){

			f.pCourseId.value=courseId;
			f.pCourseName.value=courseName;
			f.pProfId.value=profId;
			f.pContentsWidth.value=contentsWidth;
			f.pContentsHeight.value=contentsHeight;

			f.pCourseId.readOnly = true;
			f.pCourseId.style.background = "#F8F6F3";

			var f = document.Input;
			for(i=0;i<f.pFlagUse.length;i++){
				if(flagUse == f.pFlagUse[i].value){
					f.pFlagUse[i].checked=true;
				}
			}

			for(i=0;i<f.pFlagNavi.length;i++){
				if(flagNavi == f.pFlagNavi[i].value){
					f.pFlagNavi[i].checked=true;
				}
			}

			for(i=0;i<f.pContentsType.length;i++){
				if(contentsType == f.pContentsType[i].value){
					f.pContentsType[i].checked=true;
				}
			}
			
			for(i=0;i<f.pOnlineYn.length;i++){
				if(onlineYn == f.pOnlineYn[i].value){
					f.pOnlineYn[i].checked=true;
				}
			}

			var imgPath = "course/";
			if(courseImg1 != ""){
				$("pTitleImg1Div").innerHTML = "<a href=\"javascript:imgresize('sys_img','"+CONTEXTPATH+"','"+SYSTEMCODE+"','"+imgPath+courseImg1+"');\">[미리보기]</a>&nbsp;&nbsp;<a href=\"javascript:deleteImg('1');\">[삭제]</a>";
				$("pTitleImg1Div").style.display = "block";
			}else{
				$("pTitleImg1Div").innerHTML = "";
				$("pTitleImg1Div").style.display = "none";
			}

			if(courseImg2 != ""){
				$("pTitleImg2Div").innerHTML = "<a href=\"javascript:imgresize('sys_img','"+CONTEXTPATH+"','"+SYSTEMCODE+"','"+imgPath+courseImg2+"');\">[미리보기]</a>&nbsp;&nbsp;<a href=\"javascript:deleteImg('2');\">[삭제]</a>";
				$("pTitleImg2Div").style.display = "block";
			}else{
				$("pTitleImg2Div").innerHTML = "";
				$("pTitleImg2Div").style.display = "none";
			}

	  	}else{
	  		return;
	  	}
	}

	function registCourse(mode){
		var f = document.Input;
		if(submitCheck()){
			f.pRegMode.value = mode;
			f.submit();
		}
		else
			return;
	}


	// course_id 중복체크 start
	function checkCourseId(){
		var f = document.Input;
		var courseId = f.pCourseId.value;

		if(courseId !=null && courseId !=''){
			CourseWork.courseIdCheck(courseId,{
				callback:function(data) {

					var result = data;
				  	if(result != '0'){
					  	viewMessage();
				  	}else{
				  		return;
				  	}
				}
			});
		}else{
			return;
		}
	}

	function viewMessage() {
		var f = document.Input;
		alert("'"+f.pCourseId.value+"'는 사용중인 과목코드입니다.\n다른 과목코드를 입력해 주세요");
		f.pCourseId.value ="";
		new Effect.Highlight("pCourseId");
		f.pCourseId.focus();
	}
	// course_id 중복체크 end

	// 과목이미지 제거 start
	function deleteImg(pos){
		if(confirm("과목이미지를 삭제하시겠습니까?")){
			var f = document.Input;
			var courseId = f.pCourseId.value;

			CourseWork.courseImgDelete(courseId, pos, deleteImgCallback);
		}else
			return;
	}

	function deleteImgCallback(data){
		var result = data;
	  	if(result == '1'){
			$("pTitleImg1Div").innerHTML = "";
			$("pTitleImg1Div").style.display = "none";

	  	}else if(result == '2'){
			$("pTitleImg2Div").innerHTML = "";
			$("pTitleImg2Div").style.display = "none";
	  	}else return;
	}
	// 과목이미지 제거 end

	// 과목삭제 start
	function deleteCourse(){
		if(confirm("과목을 삭제하시겠습니까?")){
			var f = document.Input;
			var courseId = f.pCourseId.value;
			if(courseId !=null && courseId !=''){
				CourseWork.courseDelete(courseId,{
					callback:function(data) {

						var result = data;
					  	if(result != '0'){
							initCourseWrite();
							goList();
					  	}else{
					  		return;
					  	}
					}
				});
			}else{
				return;
			}
		}
	}
	// 과목삭제 end

	// 화면초기화
	function initCourseWrite(){
		var f = document.Input;
		var courseId = f.pCourseId.value;
		f.pCourseId.value="";
		f.pCourseId.readOnly = false;
		f.pCourseId.style.background = "#FFFFFF";
		f.pCourseName.value="";
		f.pProfId.value="";
		f.pContentsWidth.value="";
		f.pContentsHeight.value="";
		document.f.curPage.value="1";

	    for(i=0;i<f.pFlagUse.length;i++){
   	   	    if(f.pFlagUse[i].value == 'Y'){
	   	 	    f.pFlagUse[i].checked=true;
	   	    }
	    }
	    for(i=0;i<f.pFlagNavi.length;i++){
   	   	    if(f.pFlagNavi[i].value == 'Y'){
	   	 	    f.pFlagNavi[i].checked=true;
	   	    }
	    }
	    for(i=0;i<f.pContentsType.length;i++){
   	   	    if(f.pContentsType[i].value == 'R'){
	   	 	    f.pContentsType[i].checked=true;
	   	    }
	    }

		$("pTitleImg1Div").innerHTML = "";
		$("pTitleImg1Div").style.display = "none";
		$("pTitleImg2Div").innerHTML = "";
		$("pTitleImg2Div").style.display = "none";
	}

	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}
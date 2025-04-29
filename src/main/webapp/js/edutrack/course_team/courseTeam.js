	// 목차리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var COURSE_ID = "";

	function init(systemCode,contextPath,courseId) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.COURSE_ID = courseId;

		autoReload();
	}

	// 팝업박스 객체 색성
	function goMemberList(teamNo){
		popupbox1.clear();
		showPopupBox(popupbox1);

		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId !=null && tmpCourseId != "")
			COURSE_ID = DWRUtil.getValue("pCourseId");

		var frame = "<iframe name='Frame1' width='100%' height='100%' frameborder='0' src='"+CONTEXTPATH+"/CourseTeam.cmd?cmd=courseTeamMemList&pCourseId="+COURSE_ID+"&pTeamNo="+teamNo+"'></iframe>";
		popupbox1.addContents(frame);
	}

	 // 폼체크
	 function formCheck(regMode, teamNo){
		var f = document.teamNameForm;
		if(regMode=='ADD'){
			if(f.pTeamName.value == ""){
				alert("팀이름을 입력해주세요");
				new Effect.Highlight("pTeamName");
				f.pTeamName.focus();
				return false;
			}
		}
		else if(regMode=='EDIT'){
			if(f["pEditTeamName_"+teamNo].value == ""){
				alert("팀이름을 입력해주세요");
				new Effect.Highlight("pEditTeamName_"+teamNo);
				f["pEditTeamName_"+teamNo].focus();
				return false;
			}
		}
		return true;
	 }

	// 팀리스트 받아오기-여러과목일때
	function autoReload(){
		var tmpCourseId = DWRUtil.getValue("pCourseId");
		if(tmpCourseId !=null && tmpCourseId != "")
			COURSE_ID = DWRUtil.getValue("pCourseId");

		CourseTeamWork.courseTeamListAuto(COURSE_ID,autoReloadCallback);
	}

	// 팀리스트 뿌려주기
	function autoReloadCallback(data){
		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var teamListObj = $("teamList");
	  	var objStr = "<table width='670' height='25' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">등록된 팀이 없습니다.</td></tr>";
	  	}else{
			var teamNo="";
			var teamName="";
			var teamCnt="";
		  	for(i=0;i<rowLength;i++){
    			var courseTeamInfoDTO = data[i];

		    	teamNo = courseTeamInfoDTO.teamNo;
				teamName = courseTeamInfoDTO.teamName;
				teamCnt = courseTeamInfoDTO.teamCnt;

			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
					+ "<td width='70' class=\"s_tab04_cen\">"+allItemCnt+"</td>"
			    	+ "<td></td><td width='310' class=\"s_tab04\">"
			    	+ "	<div id='viewTeamName_"+teamNo+"' style='display:block;cursor:pointer;width:180px' onClick=\"editTeamName('"+teamNo+"')\">"+teamName+"</div>"
			    	+ "	<div id='editTeamName_"+teamNo+"' style='display:none;'><input type='text' name='pEditTeamName_"+teamNo+"' size='25' value='"+teamName+"'>"
			    	+ " <a href=\"javascript:registTeam('EDIT','"+teamNo+"');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_c.gif' border='0' align= 'absmiddle'></a>"
			    	+ " <a href=\"javascript:cancelEditTeamName('"+teamNo+"');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_d.gif' border='0' align='absmiddle'></a>"
			    	+ " </div>"
			    	+ "</td>"
			    	+ "<td></td><td width='080' class=\"s_tab04_cen\">"+teamCnt+"명</td>"
			    	+ "<td></td><td width='102' class=\"s_tab04_cen\"><a href=\"javascript:goMemberList('"+teamNo+"')\">관리</a></td>"
			    	+ "<td></td><td width='115' class=\"s_tab04_cen\">"
			    	+ "	<a href=\"javascript:editTeamName('"+teamNo+"')\"><b>[수정]</b></a>"
			    	+ "	<a href=\"javascript:registTeam('DEL','"+teamNo+"')\"><b>[삭제]</b></a>"
			    	+ "</td></tr>";

			    if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"9\"></td></tr>";


			    allItemCnt--;
			} // end of for

		}
		objStr += "</table>";

		teamListObj.innerHTML = objStr;
		teamListObj.style.display = "block";
	 }

	 var prevTeamViewObj = null;
	 var prevTeamEditObj = null;
	 function editTeamName(teamNo){
		var editTeamNameDiv = $("editTeamName_"+teamNo);
		var viewTeamNameDiv = $("viewTeamName_"+teamNo);

	 	var f = document.teamNameForm;
	 	f["pEditTeamName_"+teamNo].value=viewTeamNameDiv.innerHTML;

		editTeamNameDiv.style.display = "block";
		viewTeamNameDiv.style.display = "none";

		if (prevTeamEditObj != null) {
			prevTeamEditObj.style.display = "none";
		}
		if (prevTeamViewObj != null) {
			prevTeamViewObj.style.display = "block";
		}

		prevTeamEditObj = editTeamNameDiv;
		prevTeamViewObj = viewTeamNameDiv;
	 }

	 function writeTeam(){
		checkWriteTeamName();
		Effect.Appear("inputBox");
	 }

	 function closeTeamWrite(){
		checkWriteTeamName();
	  	document.teamNameForm.pTeamName.value="";
		$("inputBox").style.display = "none";
	 }

	 // 팀 등록/수정/삭제
	 function registTeam(regMode,teamNo){
	 	var f = document.f;
		if(regMode=='ADD'){
			if(formCheck(regMode,'')){
				var teamName = ajaxEnc(document.teamNameForm.pTeamName.value);
				CourseTeamWork.courseTeamRegist(regMode, COURSE_ID, "", teamName, registTeamCallback);
			}else
				return;
		}else if(regMode=='EDIT'){
			if(formCheck(regMode,teamNo)){
				var teamName = ajaxEnc(document.teamNameForm["pEditTeamName_"+teamNo].value);
				CourseTeamWork.courseTeamRegist(regMode, COURSE_ID, teamNo, teamName, registTeamCallback);
			}else
				return;
		}else if(regMode=='DEL'){
			var check = confirm("팀을 삭제하시겠습니까?\n\n주의:관련 팀구성원도 함께 삭제됩니다.");
			if (check == true) {
				CourseTeamWork.courseTeamRegist(regMode, COURSE_ID, teamNo, "", registTeamCallback);
			}else
				return;
		}else
			return;
	 }

	 // 팀 등록/수정/삭제
	 function registTeamCallback(data){
		var result = data;
	  	if(result > 0){
		  	closeTeamWrite();
		  	document.teamNameForm.pTeamName.value="";
		  	autoReload();
	  	}else{
	  		return;
	  	}
	 }

	function checkWriteTeamName() {
		if (prevTeamEditObj != null) {
			prevTeamEditObj.style.display = "none";
		}
		if (prevTeamViewObj != null) {
			prevTeamViewObj.style.display = "block";
		}
	}

	function cancelEditTeamName(teamNo) {
		var editTeamNameDiv = document.getElementById("editTeamName_"+teamNo);
		var viewTeamNameDiv = document.getElementById("viewTeamName_"+teamNo);

		editTeamNameDiv.style.display = "none";
		viewTeamNameDiv.style.display = "block";

		prevTeamViewObj = null;
		prevTeamEditObj = null;
	}

	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}
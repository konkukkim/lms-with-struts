	// 목차리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var COURSE_ID = "";	
	var TEAM_NO = "";		

	function init(systemCode,contextPath,courseId, teamNo) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;	
		this.COURSE_ID = courseId;	
		this.TEAM_NO = teamNo;			

		courseTeamListSelectBox();	
	}


	// 팀 리스트 셀렉트박스 변경
	function changeCourseTeamList(){  
		changeTeamName();	   
	  	courseStudentList('name','a');	
	  	teamMemberList('id','a');	                        
	}
	
	// 팀 리스트 초기화
	function courseTeamListSelectBox(){  
		CourseTeamWork.courseTeamListAuto(COURSE_ID,courseTeamListSelectBoxCallback);
	}
		
	// 팀 셀렉트박스 표시
	function courseTeamListSelectBoxCallback(data){
		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}
		var objDDL = document.forms["Input"].elements["courseTeamSelect"];                                         
		objDDL.options.length = 0;      
	  	if(rowLength != 0){
		  	for(i=0;i<rowLength;i++){  
    			var courseTeamInfoDTO = data[i];
			    var option = new Option(courseTeamInfoDTO.teamName, courseTeamInfoDTO.teamNo);                           
			    objDDL.options.add(option, objDDL.options.length);  
		
				if(courseTeamInfoDTO.teamNo == TEAM_NO) {
			  		option.selected = true;                          	  
			  	}    
			}                  
	  	}                       
		changeTeamName();
	  	courseStudentList('name','a');	
	  	teamMemberList('id','a');		  	
	 }
	 
	function changeTeamName(){
		var f = document.Input;
		var teamNameObj = document.getElementById("teamName");
		var objStr = f.pTeamSelectList.options[f.pTeamSelectList.selectedIndex].text;     
		teamNameObj.innerHTML = objStr;
		teamNameObj.style.display = "block";		
	}

	// 대기학생 받아오기
	function courseStudentList(column,orderBy){
		viewProgress('studentList','block',CONTEXTPATH,SYSTEMCODE,'Y');
		CourseTeamWork.courseStudentList(COURSE_ID,column,orderBy,courseStudentListCallback);
	}

	// 대기학생 뿌려주기
	function courseStudentListCallback(data){
		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}
	  	var courseStudentObj = $("studentList");
	  	var objStr = "<select multiple size=15 style='width : 100%' name='original' ondblclick=\"javascript:doubleClick()\">";
	  	if(rowLength != 0){
		  	for(i=0;i<rowLength;i++){  
    			var usersDTO = data[i];
   			    objStr += "<option value='"+usersDTO.userId+"'>"+usersDTO.userName+" ("+usersDTO.userId+")</option>";    			
			}                  
	  	}  
		objStr += "</select>";
		courseStudentObj.innerHTML = objStr;
		courseStudentObj.style.display = "block";
	}   

	// 팀멤버 받아오기
	function teamMemberList(column,orderBy){
		viewProgress('teamMemberList','block',CONTEXTPATH,SYSTEMCODE,'Y');
		var teamNo = DWRUtil.getValue("pTeamSelectList");
		CourseTeamWork.teamMemberList(COURSE_ID,teamNo,column,orderBy,teamMemberListCallback);
	}

	// 팀멤버 뿌려주기
	function teamMemberListCallback(data){
		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var teamMemberListObj = $("teamMemberList");
	  	var objStr = "<select multiple size=15 style='width : 100%' name='add' ondblclick=\"javascript:delItem()\">";
	  	if(rowLength != 0){
		  	for(i=0;i<rowLength;i++){  
    			var courseTeamMemberDTO = data[i];
   			    objStr += "<option value='"+courseTeamMemberDTO.userId+"'>"+courseTeamMemberDTO.userName+" ("+courseTeamMemberDTO.userId+")</option>";    			
			}                  
	  	}  
		objStr += "</select>";
		teamMemberListObj.innerHTML = objStr;
		teamMemberListObj.style.display = "block";
	 }   	 
	 
	 
	// 구성내용저장 
	var reloadCnt=0;
	function saveItem(){
		var f = document.Input;
		var teamNo = f.pTeamSelectList.options[f.pTeamSelectList.selectedIndex].value;     
		
		var f2 = document.AddForm;
		   	   
		var obj=f2.add.options;
		var selectedStuId = new Array();
		var len = obj.length;
		
		for (var i=0; i<len; i++) {
	   	   obj[i].selected=true;
   	   	   selectedStuId[i] = obj[i].value;
	    }
	        	   	   
		var teamNo = DWRUtil.getValue("pTeamSelectList");
		CourseTeamWork.teamMemberRegist(COURSE_ID,teamNo,selectedStuId,saveItemCallback);
	}

	// 구성내용저장 결과
	function saveItemCallback(data){
	    var result = data;
	  	if(result == true){
		  	courseStudentList('name','a');	
		  	teamMemberList('id','a');	
		  	reloadCnt++;
	  	}else	return;
	 }   
	 	 	 	 
	 function doubleClick() {
		addItem();
	 }	 
	 
	 //팝업닫기
	 function closeWin() {
	 	if(reloadCnt > 0)
		 	parent.autoReload();
		 parent.popupbox1.close();
	 }		 	

	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}		 
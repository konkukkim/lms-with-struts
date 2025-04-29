	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";

	function init(systemCode, contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;

		autoReload();
	}

	function autoReload() {
		CompanyMasterCourseWork.autoDeptUserList(autoReloadCallback);
	}

	function autoReloadCallback(data) {
		var rowLength = 0;
		var allItemCnt = 0;
		var No = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}
//alert(rowLength);
		var masterListObj = $("studentInfoList");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"13\">등록된 수강생이 없습니다.</td></tr>";
	  	}else{
			for(i=0; i<rowLength; i++) {
				var usersDto	=	data[i];
				No++;

				var user_id = usersDto.userId;
				var user_name = usersDto.userName;
				var reg_date = usersDto.regDate;
				var dept_daeCode = usersDto.daptDaecode;
				var dept_soCode = usersDto.deptSocode;
				var curri_count = usersDto.curriCount;
				var complete_count = usersDto.completeCount;
				var connect_count = usersDto.connectCount;

				var linkStr = "<a href=\"javascript:menuclick('"+user_id+"')\">";

				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">" +
						"<td width='40' class=\"s_tab04_cen\">"+No+"</td>" +
						"<td></td><td width='207' class=\"s_tab04\">"+linkStr+user_name+"</a>&nbsp;&nbsp;("+user_id+")"+"</td>" +
						"<td></td><td width='100' class=\"s_tab04_cen\">"+reg_date+"</td>" +
						"<td></td><td width='100' class=\"s_tab04_cen\">"+curri_count+"</td>" +
						"<td></td><td width='100' class=\"s_tab04_cen\">"+complete_count+"</td>" +
						"<td></td><td width='100' class=\"s_tab04_cen\">"+connect_count+"</td></tr>";
				objStr += "<tr><td colspan=\"11\" style=\"padding:7,0,3,0\" align='right'><div id='usagi"+user_id+"' style='width:100%;display:none;'></div></td></tr>";
				if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";


				allItemCnt--;
			}
		}
		objStr += "</table>";

		masterListObj.innerHTML = objStr;
		masterListObj.style.display = "block";
	}

	var old_menu = '';
	var USERID = '';
	function menuclick( userid)
	{
		if( old_menu != 'usagi'+userid ) {
			if( old_menu !='' ) {
				$(old_menu).style.display = 'none';
			}
			//$('usagi'+userid).style.display = 'block';
			old_menu = 'usagi'+userid;
			USERID = userid;

			stuInfoAutoReload(userid);
			Effect.Appear('usagi'+userid);

		} else {
			$('usagi'+userid).style.display = 'none';
			old_menu = '';
			USERID = '';
		}
	}

	function stuInfoAutoReload(userId) {
		CompanyMasterCourseWork.autoDeptStuCurriInfoList(userId, stuInfoAutoReloadCallback);
	}

	function stuInfoAutoReloadCallback(data) {
		var rowLength = 0;
		var allItemCnt = 0;
		var No = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

		var masterListObj = $("usagi"+USERID);
		var objStr = "<table border='1' bordercolor='#dcdcdc'  width='95%' align='right'>" +
						"<tr bgcolor='#f5f5f5'>" +
						"<td width='40' class=\"s_tab04_cen\">번호</td>" +
						"<td width='' class=\"s_tab04_cen\">과정명</td>" +
						"<td width='140' class=\"s_tab04_cen\">수강기간</td>" +
						"<td width='90' class=\"s_tab04_cen\">총점</td>" +
						"<td width='90' class=\"s_tab04_cen\">수료여부</td>" +
						"</tr>" +
						"<tr class='s_tab03'>" +
						"<td colspan='11'></td>" +
						"</tr>";

		if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"13\">수강했거나 수강중인 과정이 없습니다.</td></tr>";
	  	}else{
			for(i=0; i<rowLength; i++) {
				var studentDto	=	data[i];
				No++;

				var curri_name = studentDto.curriName;
				var service_start_date = studentDto.servicestartDate;
				var service_end_date = studentDto.serviceendDate;
				var score_total = studentDto.totalScore;
				var score_gubun = studentDto.scoreGubun;
				var grade = studentDto.grade;
				var get_credit = studentDto.getCredit;
				var enroll_status = studentDto.enrollStatus;
				var complete_date = studentDto.completeDate;

				var dateStr = service_start_date+"~"+service_end_date;
				var comStr = "";

				if(score_gubun == "1") {
					comStr = grade+" ("+get_credit+")";
				} else {
					if(enroll_status == "C" || complete_date != "") {
						comStr	=	"<b><font color='#ff0000'>수료</font></b>";
					} else {
						comStr	=	"<b><font color='#8fbc8f'>미수료</font></b>";
					}
				}


				objStr += "<tr>" +
						"<td width='40' class=\"s_tab04_cen\">"+No+"</td>" +
						"<td width='' class=\"s_tab04\">"+curri_name+"</td>" +
						"<td width='140' class=\"s_tab04_cen\">"+dateStr+"</td>" +
						"<td width='90' class=\"s_tab04_cen\">"+score_total+"</td>" +
						"<td width='90' class=\"s_tab04_cen\">"+comStr+"</td></tr>";
			}
		}
		objStr += "</table><table width=100%><tr class=\"s_tab03\"><td></td></tr></table>";

		masterListObj.innerHTML = objStr;
		masterListObj.style.display = "block";
	}

	function openUserStudy(userId, curriCode, curriYear, curriTerm) {
		Page = CONTEXTPATH+"/DeptAdmin.cmd?cmd=deptUserStudyShow&pUserId="+userId+"&pCurriCode="+curriCode+"&pCurriYear="+curriYear+"&pCurriTerm="+curriTerm;

		var winOption = "left=100,top=100,toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=yes,resizable=no,width=550,height=700";

		ShowInfo = window.open(Page,"StudyInfo", winOption);
	}

	function Show_UserInfo(userid){
      var Page = CONTEXTPATH+"/User.cmd?cmd=userInfoShow&userId="+userid;
	  ShowInfo	=	window.open(Page,"userinfoshow", "left=162,top=100,toolbar=0,directories=0,status=0,menubar=0,width=380,height=360,scrollbars=no");
	}
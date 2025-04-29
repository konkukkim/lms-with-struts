	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";

	function init(systemCode,contextPath,curriyear) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;

		autoReload();
	}

	function autoReload() {
		var f = document.f;
		var pSearchKey = f.pSearchKey[f.pSearchKey.selectedIndex].value;
		var pKeyWord = ajaxEnc(f.pKeyWord.value);
		var dispLine 	=	DWRUtil.getValue("pDispLine");
		var curPage		=	DWRUtil.getValue("curPage");

		CurriContentsWork.autoLecStudyStatus(curPage, dispLine, pSearchKey, pKeyWord, autoReloadCallback);
	}

	function autoReloadCallback(data) {
		var f = document.f;
		var curPage = f.curPage.value;

		var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;		// 일반 게시글
	  	var paggingStr = data.pagging;
	  	var rowLength = 0;
	  	if(dataList != null)
	  		rowLength = dataList.length;

	  	var statusListObj = $("lecStudyStatusListDiv");
	  	var paggingObj = $("getPagging");

	  	if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

		var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";
		var No = 0;
	  	if(rowLength != 0) {
	  		for(i=0;i<rowLength;i++){
				var studentDTO = dataList[i];
				No++;

				var userId = studentDTO.userId;
				var userName = studentDTO.userName;
				var totalTime = studentDTO.totalTime;
				var allAttend = studentDTO.allAttend;	//ALL OffLine
				var attendCnt = studentDTO.attendCnt;	//OffLine
				var curriPercent = studentDTO.curriPercent;

				var all_report_cnt = studentDTO.allReportCnt;
				var report_cnt = studentDTO.reportCnt;
				var all_exam_cnt = studentDTO.allExamCnt;
				var exam_cnt = studentDTO.examCnt;
				var all_forum_cnt = studentDTO.allForumCnt;
				var forum_cnt = studentDTO.forumCnt;

				var subjectLink = "<a href=\""+CONTEXTPATH+"/CurriContents.cmd?cmd=lecStudyStatusShow&pStudentId="+userId+"\">";
				var percentImg = "<img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/ing_bar.gif\" width='"+curriPercent+"' height='13' align='absmiddle'>";
				var timeStr = "";
				if(totalTime != 0) {
					timeStr = " ("+totalTime+"분)";
				}

				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">" +
						"<td width='35' class=\"s_tab04_cen\">"+totalCount+"</td>" +
						"<td></td><td width='120' class=\"s_tab04_cen\">"+subjectLink+userName+" ("+userId+")"+"</a></td>" +
						"<td></td><td width='281' class=\"s_tab04\"><table width='100%' border='0' cellpadding='0' cellspacing='0'>" +
						"<tr><td>"+percentImg+"</td>" +
						"<td align='right'><b>"+curriPercent+"%</b>"+timeStr+"</td></tr></table></td>" +
						"<td></td><td width='70' class=\"s_tab04_cen\">"+report_cnt+"/"+all_report_cnt+"</td>" +
						//"<td></td><td width='70' class=\"s_tab04_cen\">"+exam_cnt+"/"+all_exam_cnt+"</td>" +
						"<td></td><td width='70' class=\"s_tab04_cen\">"+forum_cnt+"/"+all_forum_cnt+"</td>" +
						"<td></td><td width='70' class=\"s_tab04_cen\">"+attendCnt+"/"+allAttend+"</td>" +
						"</tr>";

				if(totalCount > 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"13\"></td></tr>";

				totalCount--;
	  		}
	  	}
	  	objStr += "</table>";

		statusListObj.innerHTML = objStr;
		statusListObj.style.display = "block";
	}

	// 페이징처리
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
		autoReload();
	}

	function goAllStuList() {
		document.location.href = "<%=CONTEXTPATH%>/CurriContents.cmd?cmd=lecStudyStatus&pViewType=All&MENUNO=0";
	}

		//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}
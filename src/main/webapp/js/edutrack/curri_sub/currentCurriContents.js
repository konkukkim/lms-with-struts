	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var PARECODE1 = "";
	var PARECODE2 = "";
	var DISPLINE = 0;

	//-- 페이지 초기화
	function init(systemCode,contextPath, pareCode1) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.PARECODE1 = pareCode1;
		
		var f = document.f;
		this.DISPLINE = f.dispLine.value;

		autoReload();
	}
	
	//-- 페이징 리스트 받아오기
	function autoReload() {
		var f = document.f;
		var curPage = DWRUtil.getValue("curPage");
		var pareCode2 = DWRUtil.getValue("pPareCode2");
		
		CurriSubWork.currentCurriContentsAutoList(curPage, DISPLINE, 10, PARECODE1, pareCode2, autoReloadCallback);
	}
	
	//-- 셀렉트 박스에서 학년 선택시..
	function selectSchoolYear() {
		var f = document.f;
		var curPage = 1;
		var pareCode2 = DWRUtil.getValue("pPareCode2");

		CurriSubWork.currentCurriContentsAutoList(curPage, DISPLINE, 10, PARECODE1, pareCode2, autoReloadCallback);
	}
	
	//-- 리스트 뿌리기
	function autoReloadCallback(data) {
		var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;
	  	var paggingStr = data.pagging;
		var rowLength = 0;
		var No = 0;
		if(dataList != null)
			rowLength = dataList.length;

	  	var contentsListObj = $("contentsListDiv");
	  	var paggingObj = $("getPagging");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

		if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">등록된 강의가 없습니다.</td></tr>";
	  	}else{

		  	for(i=0;i<rowLength;i++){
		  		No++;
				var curriContentsDTO = dataList[i];
				var curriCode = curriContentsDTO.curriCode;
				var curriYear = curriContentsDTO.curriYear;
				var curriTerm = curriContentsDTO.curriTerm;
				var courseId = curriContentsDTO.courseId;
				var curriName = curriContentsDTO.curriName;
				var contentsId = curriContentsDTO.contentsId;
				var contentsName = curriContentsDTO.contentsName;
				var profName = curriContentsDTO.profName;
				var startDate = curriContentsDTO.startDate;
				var endDate = curriContentsDTO.endDate;
				
				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
						+"<td width='38' class=\"s_tab04_cen\">"+totalCount+"</td>"
						+ "<td></td><td width='130' class=\"s_tab04\">["+curriYear+"-"+curriTerm+"]&nbsp;&nbsp;"+curriName+"</td>"
						+ "<td></td><td width='273' class=\"s_tab04\">"
						+ "<a href=\"javascript:curriContentsIntroduction('"+curriCode+"','"+curriYear+"','"+curriTerm+"','"+courseId+"', '"+contentsId+"')\">"
						+ contentsName+"</a></td>"
						+ "<td></td><td width='60' class=\"s_tab04_cen\">"+profName+"</td>"
						+ "<td></td><td width='130' class=\"s_tab04_cen\">"+startDate+" ~ "+endDate+"</td>"
						+ "</tr>";

				if(No != DISPLINE) objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";
				
				totalCount--;
			}	//end for
		}

		objStr += "</table>";

		contentsListObj.innerHTML = objStr;
		contentsListObj.style.display = "block";
		
	}
	
	// 페이징처리
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
		autoReload();
	}
	
	//-- 강의설명팝업
	function curriContentsIntroduction(curricode, curriyear, curriterm, courseid, contentsid) {
		var param	=	"&pCurriCode="+curricode+"&pCurriYear="+curriyear+"&pCurriTerm="+curriterm+"&pCourseId="+courseid+"&pContentsId="+contentsid;
		var strUrl	=	CONTEXTPATH+"/CurriContents.cmd?cmd=getContentsIntroduction"+param;
		window.open(strUrl, "contentsIntroduce", "width=600, height=400,top=0, left=0, directories=no,status=no,menubar=no,scrollbars=yes,resizable=no");
	}
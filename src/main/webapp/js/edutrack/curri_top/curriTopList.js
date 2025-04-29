	var CONTEXTPATH	= 	"";
	var SYSTEMCODE 	= 	"";
	var PROPERTY1	=	"";
	var PROPERTY2	=	"";

	//페이지 초기화
	function init(systemcode, contextpath, property1, property2) {
		this.SYSTEMCODE		=	systemcode;
		this.CONTEXTPATH	=	contextpath;
		this.PROPERTY1		=	property1;
		this.PROPERTY2		=	property2;

		autoReload();
	}

	//페이징 리스트 데이타 받아오기
	function autoReload() {
		var f = document.f;
		var pSTarget = f.pSTarget[f.pSTarget.selectedIndex].value;
		var pSWord = ajaxEnc(f.pSWord.value);
		var curPage = f.curPage.value;

		CurriTopWork.curriTopAutoList(curPage, PROPERTY1, PROPERTY2, pSTarget, pSWord, autoReloadCallback);
	}
	
	//검색 후 
	function searchList() {
		var f = document.f;
		var curPage = 1;
		var pSTarget = f.pSTarget[f.pSTarget.selectedIndex].value;
		var pSWord = ajaxEnc(f.pSWord.value);
		
		CurriTopWork.curriTopAutoList(curPage, PROPERTY1, PROPERTY2, pSTarget, pSWord, autoReloadCallback);
	}

	//리스트 화면 구현
	function autoReloadCallback(data) {
		var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;
	  	var paggingStr = data.pagging;
		var rowLength = 0;
		if(dataList != null)
			rowLength = dataList.length;

	  	var courseListObj = $("curriTopDiv");
	  	var paggingObj = $("getPagging");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

		if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"13\">등록된 과목이 없습니다.</td></tr>";
	  	}else{

		  	for(i=0;i<rowLength;i++){
				var curriTopDTO = dataList[i];

				 var curriCode = curriTopDTO.curriCode;
				 var curriProperty2 = curriTopDTO.curriProperty2;
				 var curriName = curriTopDTO.curriName;
				 var cateName = curriTopDTO.cateCodeName;
				 var property2Name = curriTopDTO.pareCode2Name;
				 var courseCnt = curriTopDTO.courseCnt;

				 var linkStr	=	"<a href=\"/CurriTop.cmd?cmd=curriTopEdit&pMode=MyPage&pCurriCode="+curriCode+"&pCurriProperty2="+curriProperty2+"\">";
				 var popLinkStr	=	"<a href=\""+CONTEXTPATH+"/CurriTopCourse.cmd?cmd=curriTopCourseList&pMode=MyPage&pCurriCode="+curriCode+"&pCurriProperty2="+curriProperty2+"\">";

				 objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
				+"<td width='40' class=\"s_tab04_cen\">"+totalCount+"</td>"
				+ "<td></td><td width='100' class=\"s_tab04_cen\">"+curriCode+"</td>"
				+ "<td></td><td width='247' class=\"s_tab04\">"+linkStr+curriName+"</a></td>"
				+ "<td></td><td width='120' class=\"s_tab04_cen\">"+cateName+"</td>"
				+ "<td></td><td width='70' class=\"s_tab04_cen\">"+property2Name+"</td>"
				+ "<td></td><td width='70' class=\"s_tab04_cen\">"+popLinkStr+"<b>[과목연결]</b></td>"
				+ "</tr>";

				if(totalCount > 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"13\"></td></tr>";

				totalCount--;
			}
		}
		objStr += "</table>";

		courseListObj.innerHTML = objStr;
		courseListObj.style.display = "block";
	}

	// 페이징처리
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
		autoReload();
	}

	function goAdd() {
		var loc = CONTEXTPATH+"CurriTop.cmd?cmd=curriTopWrite&pMode=MyPage&pProperty1="+PROPERTY1;
		document.location.href = loc;
	}
/*
	function goRecommend() {
		var loc = CONTEXTPATH+"CurriRecommend.cmd?cmd=curriRecommendView&pProperty1="+PROPERTY1;
		RecWin = window.open(loc,"recWin","width=500,height=400,top=0,left=0,directories=no,status=0,menubar=no,scrollbars=yes,resizable=no");
		RecWin.focus();
	}
*/

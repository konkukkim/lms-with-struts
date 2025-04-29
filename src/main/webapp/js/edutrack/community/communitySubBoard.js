	//커뮤니티 게시판
	var SYSTEMCODE = "1";
	var CONTEXTPATH = "";
	var COMMID = "";
	var BBSID = 0;

	//페이지 초기화
	function bbsList_init(systemCode, contextPath, commId, bbsId) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.COMMID = commId;
		this.BBSID = bbsId;

		bbsListAutoRelod();
	}

	//리스트 데이터 담아오기
	function bbsListAutoRelod() {
		var f = document.f;
		var curpage = f.curPage.value;
		var pSearchKey = f.pSearchKey[f.pSearchKey.selectedIndex].value;
		var pKeyWord = ajaxEnc(f.pKeyWord.value);

		CommunitySubBoardWork.subBoardListAuto(curpage, COMMID, BBSID, pSearchKey, pKeyWord, bbsListAutoReloadCallback);
	}

	//리스트 작성하기
	function bbsListAutoReloadCallback(data) {
		var f = document.f;
		var curPage = f.curPage.value;
		var mode = f.pMode.value;
		var userLevel = f.userLevel.value;
		var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;		// 일반 게시글
	  	var paggingStr = data.pagging;
		var rowLength = 0;

		if(dataList != null) rowLength = dataList.length;

		var bbsListObj = $("bbsList");
		var paggingObj = $("getPagging");

		if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

		var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";
		if(rowLength == 0) {
			objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"13\">등록된 글이 없습니다.</td></tr>";
		} else {
			for(i=0;i<rowLength;i++){
		  		var bbsDTO = dataList[i];

				var systemCode = bbsDTO.systemCode;
				var commId = bbsDTO.commId;
				var bbsId = bbsDTO.bbsId;
				var seqNo = bbsDTO.seqNo;
				var depthNo = bbsDTO.depthNo;
				var commCnt = bbsDTO.commCnt;
				var subject = bbsDTO.subject;
				var nickName = bbsDTO.nickName;
				var regDate = bbsDTO.regDate.substring(0, 8);
				var hitNo = bbsDTO.hitNo;

				var subjectLink = "";
				if(userLevel != "G") {
					subjectLink = "<a href='"+CONTEXTPATH+"/CommSubBoard.cmd?cmd=commSubBoardShow&pMode="+mode+"&pCommId="+commId+"&pBbsId="+bbsId+"&pSeqNo="+seqNo+"&curPage="+curPage+"'>";
				}

//				if(hitNo > hotchk) bbshot = "&nbsp;<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_hot.gif'>";
//				if(regDate > addDate) bbsNew = "&nbsp;<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_new.gif'>";
				var depthSpace = "";
				var commStr = "";
				if(depthNo > 0) depthSpace = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_re.gif'>";
				for(j=0; j<depthNo;j++) depthSpace = "&nbsp;"+depthSpace;
				if(commCnt > 0) commStr = "&nbsp;<span style='font-size:7pt;'>["+commCnt+"]</span>";

				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">" +
						"<td width='70' class=\"s_tab04_cen\">"+totalCount+"</td>" +
						"<td></td><td width='354' class=\"s_tab04\">"+depthSpace+subjectLink+subject+"</a>"+commStr+"</td>" +
						"<td></td><td width='80' class=\"s_tab04_cen\">"+nickName+"</td>" +
						"<td></td><td width='83' class=\"s_tab04_cen\">"+regDate.substring(0, 4)+"."+regDate.substring(4, 6)+"."+regDate.substring(6, 8)+"</td>" +
						"<td></td><td width='74' class=\"s_tab04_cen\">"+hitNo+"</td>" +
						"</tr>";

				if(totalCount > 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"13\"></td></tr>";

				totalCount--;
//				bbsHot = "";
//				bbsNew = "";
				depthSpace = "";
				commCnt = 0;
				commStr = "";
		  	}	//end for
		}
		objStr += "</table>";

		bbsListObj.innerHTML = objStr;
		bbsListObj.style.display = "block";
	}

	// 페이징처리
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
		bbsListAutoRelod();
	}

	// 리스트 받아오기-검색
	function goSearch(){
		var f = document.f;
		var curpage = 1;
		var pSearchKey = f.pSearchKey[f.pSearchKey.selectedIndex].value;
		var pKeyWord = ajaxEnc(f.pKeyWord.value);

		CommunitySubBoardWork.subBoardListAuto(curpage, COMMID, BBSID, pSearchKey, pKeyWord, bbsListAutoReloadCallback);
	}

	function goAdd() {
		var f = document.f;
		var bbsType = f.pBbstype.value;
		var mode = f.pMode.value;

		document.location = "/CommSubBoard.cmd?cmd=commSubBoardWrite&pBbsId="+BBSID+"&pMode="+mode+"&pBbstype="+bbsType;
	}

	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}

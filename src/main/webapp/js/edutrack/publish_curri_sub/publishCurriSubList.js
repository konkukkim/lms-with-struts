	var SYSTEMCODE = "";
	var CONTEXTPATH = "";
	var PARECODE1 = "";
	var PARECODE2 = "";
	var PGUBUN = "";
	var USERTYPE = "";
	
	function init(systemcode, contextpath, parecode1, parecode2, pgubun, usertype) {
		this.SYSTEMCODE = systemcode;
		this.CONTEXTPATH = contextpath;
		this.PARECODE1 = parecode1;
		this.PARECODE2 = parecode2;
		this.PGUBUN = pgubun;
		this.USERTYPE = usertype;
			
		autoReload();
	}
	
	//-- 해당 공개과정에 포함된 개설과정 리스트 담아오기
	function autoReload() {
		var f = document.f;
		var curPage = f.curPage.value;
		var pDispLine = f.dispLine.value;
		var pDispPage = f.dispPage.value;
		var pSearchKey = f.pSearchKey[f.pSearchKey.selectedIndex].value;
		var pKeyWord = ajaxEnc(f.pKeyWord.value);
			
		PublishCurriSubWork.publishCurriSubPageAutoList(curPage, pDispLine, pDispPage, PARECODE1, PARECODE2, pSearchKey, pKeyWord, autoReloadCallback);
	}

	// 페이징처리
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
		autoReload();
	}
		
	//-- 해당 공개과정에 포함된 개설과정 리스트 뿌리기
	function autoReloadCallback(data) {
		var f = document.f;
		var curPage = f.curPage.value;
		var mode = f.pMode.value;
		
		var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;		// 일반 게시글
	  	var paggingStr = data.pagging;
		var rowLength = 0;
		var No = 0;

		if(dataList != null)
			rowLength = dataList.length;
		
		var curriListObj = $("publishCurriSubList");
	  	var paggingObj = $("getPagging");

	  	if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}
		
		var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";
		
		
		if(rowLength == 0) {
			objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"13\">개설된 과정이 없습니다.</td></tr>";
		} else {
			var curriCode	=	"";
			var curriYear	=	0;
			var curriTerm	=	0;
			var curriName	=	"";
			var curriGoal	=	"";
			var curriInfo	=	"";
			var proName		=	"";
			var lecLink		=	"";
			var dispLine	=	0;
			var param		=	"";
			
		  	for(i=0;i<rowLength;i++){
		  		var curriTopDTO	=	dataList[i];		  		
		  			
		  		curriCode	=	curriTopDTO.curriCode;
		  		curriYear	=	curriTopDTO.curriYear;
		  		curriTerm	=	curriTopDTO.curriTerm;
		  		curriName	=	curriTopDTO.curriName;
		  		curriGoal	=	curriTopDTO.curriGoal;
		  		curriInfo	=	curriTopDTO.curriInfo;
		  		profName	=	curriTopDTO.profName;
		  		dispLine	=	curriTopDTO.dispLine;
		  		
		  		param		=	"&pCurriCode="+curriCode+"&curriYear="+curriYear+"&curriTerm="+curriTerm+"&pGubun="+PGUBUN;
		  		lecLink		=	CONTEXTPATH+"/CurriContents.cmd?cmd=lecContentsList&pMode=Home"+param;
		  		
		  		if(No > 0 && No != dispLine) objStr += "\n<tr class=\"s_tab03\"><td colspan=\"13\"></td></tr>";
		  		
		  		objStr	+= "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
						+ "\n<td width='40' class=\"s_tab04_cen\">"+totalCount+"</td>"
						+ "\n<td width='180' class=\"s_tab04\"><a href=\""+lecLink+"\">"+curriName+"</a></td>"
						+ "\n<td width='' class=\"s_tab04\">"+curriGoal+"</td>" //335
						+ "\n<td width='80' class=\"s_tab04_cen\">"+profName+"</td>";
						
				if(USERTYPE == "M" && PGUBUN == "2") {
					objStr += "<td width='80' class=\"s_tab04_cen\">"
						   + "<a href=\"javascript:addForumInfo('"+curriCode+"','"+curriYear+"','"+curriTerm+"')\">"
						   + "<b>토론방개설</b></a></td>";
				}
				
				objStr	+= "\n</tr>";
		  		
		  		totalCount--;
		  		lecLink		=	"";
		  		No++;
		  	}		  	
		}
		objStr += "</table>";

		curriListObj.innerHTML = objStr;
		curriListObj.style.display = "block";
	}
	
	// 공개강좌 토론방 생성
	function addForumInfo(curricode, curriyear, curriterm) {
		var param = "&pCurriCode="+curricode+"&pCurriYear="+curriyear+"&pCurriTerm="+curriterm+"&pGubun="+PGUBUN;
		var linkUrl = CONTEXTPATH+"/PublishCurriSub.cmd?cmd=addForumInfoPopup";
		var winOption = "left=0,top=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=auto,resizable=yes,width=600,height=500";
		lecWin = window.open(linkUrl+param, "forumInfoWin", winOption);
	}
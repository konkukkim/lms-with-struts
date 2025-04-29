	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";

	function init(systemCode,contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;

		autoReload()
	}

	function errorMessage(){
		alert("작업중 에러가 발생했습니다.\n재시도 해주세요!!!");
	}

	function autoReload(){
		
		viewProgress('listDiv','block',CONTEXTPATH, SYSTEMCODE,'Y');
		 
		var pCurriYearTerm = DWRUtil.getValue("pCurriYearTerm").split("!");
        var pProperty1 = DWRUtil.getValue("pProperty1");
        
        CurriSubWork.completeMypageListAuto(pProperty1, pCurriYearTerm[0], pCurriYearTerm[1], autoReloadCallback);
	}

	// 과정리스트 뿌려주기
	function autoReloadCallback(data){
		var rowLength = 0;
		
		if(data != null){
			rowLength = data.length;
		}

	  	var listObj = $("listDiv");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"5\">강의가 종료된 과정이 없습니다.</td></tr>";
	  	}else{
	    	var curriCode = "";
	    	var curriYear = "";
	    	var curriTerm = "";
	    	var yearTerm = "";
	    	var enrollDate = "";
	    	var serviceDate = "";
	    	var courseCount = "";

		  	for(i=0;i<rowLength;i++){
    			var curriSubDTO = data[i];

				curriCode = curriSubDTO.curriCode;
				curriYear = curriSubDTO.curriYear;
				curriTerm = curriSubDTO.curriTerm;
				curriName = curriSubDTO.curriName;
				enrollStart = curriSubDTO.enrollStart;
				enrollEnd = curriSubDTO.enrollEnd;
				serviceStart = curriSubDTO.serviceStart;
				serviceEnd = curriSubDTO.serviceEnd;
				
				tmpSampleContents = curriSubDTO.sampleContents;
				if( tmpSampleContents != "" )
					sampleContentsLink = "<a href=\"javascript:SampleWin('"+tmpSampleContents+"')\"><img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button/edu_sample.gif\" width=62 height=15 align=absmiddle hspace=4></a>";
				else
					sampleContentsLink = "";


			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
			    	+ "<td width='348' class=\"s_tab04_cen\">"
			    	+ "<a href='"+CONTEXTPATH+"/LectureMain.cmd?cmd=goLecture&pMode=Lecture&MENUNO=0&pCurriCode="+curriCode+"&pCurriYear="+curriYear+"&pCurriTerm="+curriTerm+"&pCurriType=R&pCurriName="+curriName+"'>"+curriName+"</a>"+sampleContentsLink+"</td>"
			    	+ "<td></td><td width='160' class=\"s_tab04_cen\">"+enrollStart +"~" + enrollEnd +"</td>"
			    	+ "<td></td><td class=\"s_tab04\">"
			    	+ "<td></td><td width='160' class=\"s_tab04_cen\">"+serviceStart+"~" + serviceEnd +"</td>"
			    	+ "</tr>";

				objStr += "<tr class=s_tab03><td colspan=7></td></tr>";

			}
		}
		objStr += "</table>";

		listObj.innerHTML = objStr;
		listObj.style.display = "block";
	 }



	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";

	function init(systemCode,contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;

		cateSelectList();
	}

	function errorMessage(){
		alert("작업중 에러가 발생했습니다.\n재시도 해주세요!!!");
	}

	// 과정생성
	function goAdd(){
		var f = document.Input;
		var cateCode = DWRUtil.getValue("pCateCode");
		var curriCode = DWRUtil.getValue("pCurriCode");

		if(cateCode == ''  || cateCode == '-1'){
			alert('과정 분류를 선택해 주세요');
			f.pCateCode.focus();
		}else if(curriCode == ''  || curriCode == '-1' ){
			alert('과정을 선택해 주세요');
			f.pCurriCode.focus();
		}else{
			var curriYear = f.pCurriYear.value;
			document.location.href = CONTEXTPATH+"/CurriSub.cmd?cmd=curriSubWrite&pCateCode="+cateCode+"&pCurriCode="+curriCode+"&pCurriYear="+curriYear+"&pMode=MyPage";
		}
	}

	function autoReload(){
		var cateCode = DWRUtil.getValue("pCateCode");
		var curriCode = DWRUtil.getValue("pCurriCode");
		var curriYear = DWRUtil.getValue("pCurriYear");
		CurriSubWork.curriSubListAuto(cateCode, curriCode, curriYear, autoReloadCallback);
	}

	// 과정리스트 뿌려주기
	function autoReloadCallback(data){
		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var curriSubListObj = $("curriSubList");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"5\">개설된 과정이 없습니다.</td></tr>";
	  	}else{
	    	var cateCode = DWRUtil.getValue("pCateCode");
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
				yearTerm = curriSubDTO.curriName;
				enrollDate = curriSubDTO.enrollStart;
				serviceDate = curriSubDTO.serviceStart;
				courseCount = curriSubDTO.courseCount;

			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
			    	+ "<td width='40' class=\"s_tab04_cen\">"+allItemCnt+"</td>"
			    	+ "<td></td><td class=\"s_tab04\">"
			    	+ "<a href='"+CONTEXTPATH+"/CurriSub.cmd?cmd=curriSubEdit&pMode=MyPage&pCateCode="+cateCode+"&pCurriCode="+curriCode+"&pCurriYear="+curriYear+"&pCurriTerm="+curriTerm+"'>"+yearTerm+"</a></td>"
			    	+ "<td></td><td width='150' class=\"s_tab04_cen\">"+enrollDate+"</td>"
			    	+ "<td></td><td width='150' class=\"s_tab04_cen\">"+serviceDate+"</td>"
			    	+ "<td></td><td width='70' class=\"s_tab04_cen\">"
			    	+ "<a href='"+CONTEXTPATH+"/CurriSubCourse.cmd?cmd=curriSubCourseList&pMode=MyPage&pCateCode="+cateCode+"&pCurriCode="+curriCode+"&pCurriYear="+curriYear+"&pCurriTerm="+curriTerm+"'><b><font color='#CC6600'>"+courseCount+"과목</font></b></a>"
			    	+ "</td>"
			    	+ "</tr>";

			    if(allItemCnt > 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    allItemCnt--;
			}
		}
		objStr += "</table>";

		curriSubListObj.innerHTML = objStr;
		curriSubListObj.style.display = "block";
	 }

	// 과정분류 셀렉트박스 초기화
	function cateSelectList(){
		CurriSubWork.cateSelectList(cateSelectListCallback);
	}

	// 과정분류 셀렉트박스 표시
	function cateSelectListCallback(data){
		DWRUtil.removeAllOptions("pCateCode");
		var defaultSelect = {"":"--과정분류--"};
		DWRUtil.addOptions("pCateCode", defaultSelect);
		DWRUtil.addOptions("pCateCode", data);

		curriSelectList(DWRUtil.getValue("pCateCode"));
	}

	// 과정 셀렉트박스 초기화
	function curriSelectList(cateCode){
		CurriSubWork.curriSelectList(cateCode, curriSelectListCallback);
	}

	// 과정 셀렉트박스 표시
	function curriSelectListCallback(data){
		DWRUtil.removeAllOptions("pCurriCode");
		var defaultSelect = {"":"--전체과정--"};
		DWRUtil.addOptions("pCurriCode", defaultSelect);
		DWRUtil.addOptions("pCurriCode", data);

		autoReload();
	}

	// 과정분류 변경
	function changeCategory(){
		var cateCode =  DWRUtil.getValue("pCateCode");
		curriSelectList(cateCode);
	}

	// 과정기수 중복체크 start
	function checkCurriTerm(){
		var f = document.Input;
		var cateCode = f.pCateCode.value;
		var curriCode = f.pCurriCode.value;
		var curriYear = f.pCurriYear.value;
		var curriTerm = f.pCurriTerm.value;

		if(curriTerm !=null && curriTerm !=''){
			CurriSubWork.curriTermCheck(cateCode,curriCode,curriYear,curriTerm,{
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
		alert("'"+f.pCurriTerm.value+"'는 사용중인 과정기수입니다.\n다른 과정기수를 입력해 주세요");
		f.pCurriTerm.value ="";
		new Effect.Highlight("pCurriTerm");
		f.pCurriTerm.focus();
	}
	// 과정기수 중복체크 end

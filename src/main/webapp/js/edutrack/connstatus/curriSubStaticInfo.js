	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var CURRIYEAR = 2007;
	var CURPAGE = 1;

	function init(systemCode,contextPath,curriyear) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.CURRIYEAR = curriyear;

		var f = document.f;
		this.CURPAGE = f.curPage.value;

		termSelectList(CURRIYEAR);
		cateSelectList();
		initScreen();
	}

//-------------------------------------------------------------------------------------

	function initScreen() {
		var staticListObj = $("curriSubStaticInfo");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>" +
	  				"<tr><td class=\"s_tab04_cen\" colspan=\"27\" height=\"30\">조건을 선택해 주세요.</td></tr>" +
	  				"</table>";

	  	staticListObj.innerHTML = objStr;
		staticListObj.style.display = "block";
	}

//-------------------------------------------------------------------------------------

	//과정정보 리스트 불러오기
	function autoReload() {
		var curriYear = DWRUtil.getValue("pCurriYear");
		var curriTerm = DWRUtil.getValue("pCurriTerm");
		var cateCode =  DWRUtil.getValue("pPareCode1");
		var dispLine =  DWRUtil.getValue("DispLine");

		CurriSubStaticInfoWork.curriSubStaticInfo(CURPAGE, curriYear, curriTerm, cateCode, dispLine, autoReloadCallback);
	}

	//과정정보 리스트
	function autoReloadCallback(data) {
		var dataList = data.dataList;		// 일반 게시글

		var rowLength = 0;
		var allItemCnt = 0;
		if(dataList != null){
			rowLength = dataList.length;
			allItemCnt = dataList.length;
		}

		var staticListObj = $("curriSubStaticInfo");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"27\">등록된 과정이 없습니다.</td></tr>";
	  	}else{

	  		for(i=0; i<rowLength; i++) {
	  			var curriSubDto = dataList[i];

		  		var curri_name = curriSubDto.curriName;
		  		var curri_code = curriSubDto.curriCode;
		  		var curri_term = curriSubDto.curriTerm;
		  		var contents_cnt = curriSubDto.contentsCnt;

		  		var complete_man_cnt = curriSubDto.completeManCnt;
		  		var complete_women_cnt = curriSubDto.completeWomenCnt;
		  		var complete_total_cnt = curriSubDto.completeTotalCnt;

		  		var no_man_cnt = curriSubDto.noManCnt;
		  		var no_women_cnt = curriSubDto.noWomenCnt;
		  		var no_total_cnt = curriSubDto.noTotalCnt;

		  		var total_man_cnt = curriSubDto.manTotalCnt;
		  		var total_women_cnt = curriSubDto.womenTotalCnt;
		  		var total_cnt = curriSubDto.totalCnt;

		  		objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">" +
		  			"<td width='100' class=\"s_tab04\">"+curri_name+"</td>" +
		  			"<td></td><td width='70' class=\"s_tab04_cen\">"+curri_term+"</td>" +
		  			"<td></td><td width='45' class=\"s_tab04_cen\">"+contents_cnt+"개</td>" +
		  			"<td></td><td width='40' class=\"s_tab04_cen\">"+complete_man_cnt+"</td>" +
		  			"<td></td><td width='40' class=\"s_tab04_cen\">"+complete_women_cnt+"</td>" +
		  			"<td></td><td width='40' class=\"s_tab04_cen\">"+complete_total_cnt+"</td>" +
		  			"<td></td><td width='40' class=\"s_tab04_cen\">"+no_man_cnt+"</td>" +
		  			"<td></td><td width='40' class=\"s_tab04_cen\">"+no_women_cnt+"</td>" +
		  			"<td></td><td width='40' class=\"s_tab04_cen\">"+no_total_cnt+"</td>" +
		  			"<td></td><td width='40' class=\"s_tab04_cen\">"+total_man_cnt+"</td>" +
		  			"<td></td><td width='40' class=\"s_tab04_cen\">"+total_women_cnt+"</td>" +
		  			"<td></td><td width='40' class=\"s_tab04_cen\">"+total_cnt+"</td>" +
		  			"<td></td><td width='60' class=\"s_tab04_cen\">" +
		  			"<a href=\"javascript:initStatic('"+curri_code+"', '"+curri_term+"','N')\">[지역별]</a>" +
		  			"<br><a href=\"javascript:initStatic('"+curri_code+"', '"+curri_term+"','Y')\">[연령별]</a></td>" +
		  			"</tr>";

				if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"27\"></td></tr>";

				allItemCnt--;
	  		}	//end for
	  	}
	  	objStr += "</table>";

		staticListObj.innerHTML = objStr;
		staticListObj.style.display = "block";
	}

//-------------------------------------------------------------------------------------

	//화면하단 통계테이블 초기화
	function initStatic(curricode, curriterm, ageyn) {
		staticAutoReload(curricode, curriterm, ageyn);

		document.f.pCurriCode.value = curricode;
		document.f.curriTerm.value = curriterm;
		document.f.pAgeYn.value = ageyn;

		$("infoListDiv").style.display = "none";

		Effect.Appear("staticListDiv");
	}

	function staticAutoReload(curricode, curriterm, ageyn) {
		var curriYear = DWRUtil.getValue("pCurriYear");
		var cateCode =  DWRUtil.getValue("pPareCode1");

		CurriSubStaticInfoWork.curriSubStatic(curricode, curriYear, curriterm, cateCode, ageyn, staticAutoReloadCallback);
	}

	//화면하단 통계테이블 만들기
	function staticAutoReloadCallback(data) {
		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

		var staticListObj = $("curriSubStatic");
		var nameObj = $("curriName");

	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";
	  	var nameStr = "";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"19\">등록된 수강생이 없습니다.</td></tr>";
	  	}else{
			for(i=0; i<rowLength; i++) {
	  			var curriSubDto = data[i];

				var code_name = curriSubDto.codeName;
				var area = curriSubDto.area;
				var age = curriSubDto.age;

				if(age != 0) {
					nameStr = "연령대";
				}
				else {
					nameStr = "지역명";
				}

		  		var complete_man_cnt = curriSubDto.completeManCnt;
		  		var complete_women_cnt = curriSubDto.completeWomenCnt;
		  		var complete_total_cnt = curriSubDto.completeTotalCnt;

		  		var no_man_cnt = curriSubDto.noManCnt;
		  		var no_women_cnt = curriSubDto.noWomenCnt;
		  		var no_total_cnt = curriSubDto.noTotalCnt;

		  		var total_man_cnt = curriSubDto.manTotalCnt;
		  		var total_women_cnt = curriSubDto.womenTotalCnt;
		  		var total_cnt = curriSubDto.totalCnt;


		  		objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">" +
		  			"<td width='136' class=\"s_tab04_cen\">"+code_name+"</td>" +
		  			"<td></td><td width='56' class=\"s_tab04_cen\">"+complete_man_cnt+"</td>" +
		  			"<td></td><td width='56' class=\"s_tab04_cen\">"+complete_women_cnt+"</td>" +
		  			"<td></td><td width='56' class=\"s_tab04_cen\">"+complete_total_cnt+"</td>" +
		  			"<td></td><td width='56' class=\"s_tab04_cen\">"+no_man_cnt+"</td>" +
		  			"<td></td><td width='56' class=\"s_tab04_cen\">"+no_women_cnt+"</td>" +
		  			"<td></td><td width='56' class=\"s_tab04_cen\">"+no_total_cnt+"</td>" +
		  			"<td></td><td width='57' class=\"s_tab04_cen\">"+total_man_cnt+"</td>" +
		  			"<td></td><td width='55' class=\"s_tab04_cen\">"+total_women_cnt+"</td>" +
		  			"<td></td><td width='55' class=\"s_tab04_cen\">"+total_cnt+"</td>" +
		  			"</tr>";

				if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"19\"></td></tr>";

				allItemCnt--;

		  	}	//end for
	  	}	//end if
	  	objStr += "</table>";

		staticListObj.innerHTML = objStr;
		staticListObj.style.display = "block";

		nameObj.innerHTML = nameStr;
		nameObj.style.display = "block";

	}

//-------------------------------------------------------------------------------------

	//개설연도를 바꾸면 학기도 바꾼다.
	function changeYear() {
		var curriYear = DWRUtil.getValue("pCurriYear");

		termSelectList(curriYear);
		autoReload();
	}

//-------------------------------------------------------------------------------------

	//개설학기 초기화
	function termSelectList(curYear) {
		CurriSubStaticInfoWork.termSelectList(curYear, termSelectListCallBack);
	}

	//개설학기 셀렉트박스 표시
	function termSelectListCallBack(data) {
		var rowLength = 0;
		if(data != null){
			rowLength = data.length;
		}

		var curriTermListObj = $("curriTermList");
		var objStr = "<select name='pCurriTerm' onChange = 'changTerm()'>" +
						"<option value=''>--개설학기--</option>";

		if(rowLength == 0) {
			objStr += "";
		} else {
			var curriTerm	=	0;

			for(i=0;i<rowLength;i++){
	   			var curriSubStaticDTO = data[i];
	   			curriTerm	=	curriSubStaticDTO.curriTerm;

				objStr		+=	"<option value='"+curriTerm+"'>"+curriTerm+"</option>";
	   		}	//end for
		}
		objStr += "</select>";

		curriTermListObj.innerHTML = objStr;
		curriTermListObj.style.display = "block";
	}

	//개설학기 변경
	function changTerm() {
		var curriTerm = DWRUtil.getValue("pCurriTerm");

		autoReload();
	}

//-------------------------------------------------------------------------------------

	// 과정분류 셀렉트박스 초기화
	function cateSelectList(){
		CurriSubStaticInfoWork.cateSelectList(cateSelectListCallback);
	}

	// 과정분류 셀렉트박스 표시
	function cateSelectListCallback(data){
		DWRUtil.removeAllOptions("pPareCode1");
		var defaultSelect = {"":"--과정분류--"};
		DWRUtil.addOptions("pPareCode1", defaultSelect);
		DWRUtil.addOptions("pPareCode1", data);
	}

	// 과정분류 변경
	function changeCategory(){
		var cateCode =  DWRUtil.getValue("pPareCode1");

		autoReload();
	}

	//과정정보화면으로 돌아가기
	function goList() {
		$("staticListDiv").style.display = "none";

		Effect.Appear("infoListDiv");
	}


	function downExcel(gubun) {

		var curriTerm = DWRUtil.getValue("curriTerm");
		var cateCode =  DWRUtil.getValue("pPareCode1");

		var curricode = DWRUtil.getValue("pCurriCode");
		var ageyn = DWRUtil.getValue("pAgeYn");

		var param1 = "&pCurriYear="+CURRIYEAR+"&pCurriTerm="+curriTerm+"&pPareCode1="+cateCode;
		var param2 = "&pCurriCode="+curricode+"&pAgeYn="+ageyn;

		document.location.href = CONTEXTPATH+"/CurriSubStatic.cmd?cmd=excelAllCurriSubStatic&pGubun="+gubun+param1+param2;
	}


	function errorMessage(){
		alert("작업중 에러가 발생했습니다.\n재시도 해주세요!!!");
	}

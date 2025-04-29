	// ���񸮽�Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var CURRIYEAR = 2007;

	function init(systemCode,contextPath,curriyear) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.CURRIYEAR = curriyear;

		termSelectList(CURRIYEAR);
		initScreen();
	}
//-------------------------------------------------------------------------------------

	function initScreen() {
		var staticListObj = $("curriSubCourseList");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>" +
	  				"<tr><td class=\"s_tab04_cen\" colspan=\"27\" height=\"30\">�б⸦ ������ �ּ���.</td></tr>" +
	  				"</table>";

	  	staticListObj.innerHTML = objStr;
		staticListObj.style.display = "block";
	}

//-------------------------------------------------------------------------------------

	//������ ��� ����Ʈ �ʱ�ȭ
	function initStudentList(curricode) {
		var f = document.f;
		f.pCurriCode.value = curricode;

		studentAutoReload(curricode);
		curriNameReload(curricode);

		$("infoListDiv").style.display = "none";
		Effect.Appear("staticListDiv");
	}

	//������ ��ƿ���
	function curriNameReload(curricode) {
		var curriYear = DWRUtil.getValue("pCurriYear");
		var curriTerm = DWRUtil.getValue("pCurriTerm");
		CurriSubCourseStaticWork.getCurriSubName(curricode, curriYear, curriTerm, curriNameReloadCallBack);
	}

	//������
	function curriNameReloadCallBack(data) {
		var curriTerm = DWRUtil.getValue("pCurriTerm");

		var curriName = "";
		if(data != null) {
			curriName = data;
		}

		var curriNameObj = $("curriNameDiv");
		var obj = "<img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/blet01.gif\" align=\"absmiddle\"><b>"+curriName+"&nbsp;&nbsp;" +
		"("+CURRIYEAR+"��&nbsp;"+curriTerm+"�б�)</b>";

		curriNameObj.innerHTML = obj;
		curriNameObj.style.display = "block";
	}

	//������ ��� ������ ��ƿ���
	function studentAutoReload(curricode) {
		var curriYear = DWRUtil.getValue("pCurriYear");
		var curriTerm = DWRUtil.getValue("pCurriTerm");

		CurriSubCourseStaticWork.courseStudentStaticList(curricode, curriYear, curriTerm, studentAutoReloadCallBack);
	}

	//������ ��� ����Ʈ
	function studentAutoReloadCallBack(data) {
		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null) {
			rowLength = data.length;
			allItemCnt = data.length;
		}

		var staticListObj = $("courseStudentList");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"27\">��ϵ� �������� �����ϴ�.</td></tr>";
	  	} else {
	  		for(i=0; i<rowLength; i++) {
	  			var curriSubDto = data[i];

	  			var userName = curriSubDto.userName;
	  			var courseRate = curriSubDto.courseRate;
	  			var qnaCnt = curriSubDto.qanCnt;
	  			var reqNo = curriSubDto.reqCnt;
	  			var resNo = curriSubDto.resCnt;
	  			var forumContentsCnt = curriSubDto.forumContentsCnt;
	  			var reportCnt = curriSubDto.reportCnt;
	  			var examCnt = curriSubDto.examCnt;
	  			//var scoreTotal = curriSubDto.scoreTotal;
	  			var result = curriSubDto.result;

	  			objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">" +
			  				"<td width='85' class=\"s_tab04\">"+userName+"</td>" +
			  				"<td></td><td width='83' class=\"s_tab04_cen\">"+courseRate+"%</td>" +
			  				"<td></td><td width='73' class=\"s_tab04_cen\">"+qnaCnt+"</td>" +
			  				"<td></td><td width='103' class=\"s_tab04_cen\">"+reqNo+" / "+resNo+"</td>" +
			  				"<td></td><td width='83' class=\"s_tab04_cen\">"+forumContentsCnt+"</td>" +
			  				"<td></td><td width='71' class=\"s_tab04_cen\">"+reportCnt+"</td>" +
			  				"<td></td><td width='81' class=\"s_tab04_cen\">"+examCnt+"</td>" +
			  				"<td></td><td width='80' class=\"s_tab04_cen\">"+result+"</td>" +
			  				"</tr>";

			  	if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"19\"></td></tr>";

				allItemCnt--;
	  		}
	  	}
	  	objStr += "</table>";

		staticListObj.innerHTML = objStr;
		staticListObj.style.display = "block";
	}

//-------------------------------------------------------------------------------------

	//�������� ����Ʈ ��������
	function autoReload() {
		var curriYear = DWRUtil.getValue("pCurriYear");
		var curriTerm = DWRUtil.getValue("pCurriTerm");

		CurriSubCourseStaticWork.curriSubCourseList(curriYear, curriTerm, autoReloadCallback);
	}

	//�������� ����Ʈ
	function autoReloadCallback(data) {
		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null) {
			rowLength = data.length;
			allItemCnt = data.length;
		}

		var staticListObj = $("curriSubCourseList");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"27\">��ϵ� ������ �����ϴ�.</td></tr>";
	  	} else {
	  		for(i=0; i<rowLength; i++) {
	  			var curriSubDto = data[i];

	  			var curriCode = curriSubDto.curriCode;
	  			var curriName = curriSubDto.curriName;
	  			var profName = curriSubDto.profName;
	  			var resultYn = curriSubDto.resultYn;
	  			var studentCnt = curriSubDto.studentCnt;
	  			var qnaCnt = curriSubDto.qanCnt;
	  			var noticeCnt = curriSubDto.noticeCnt;
	  			var reqCnt = curriSubDto.reqCnt;
	  			var resCnt = curriSubDto.resCnt;
	  			var forumCnt = curriSubDto.forumCnt;
	  			var forumContentsCnt = curriSubDto.forumContentsCnt;
	  			var reportCnt = curriSubDto.reportCnt;
	  			var examCnt = curriSubDto.examCnt;

	  			var detailLink = "<a href=\"javascript:initStudentList('"+curriCode+"')\">";

	  			objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">" +
		  				"<td width='133' class=\"s_tab04\">"+detailLink+curriName+"</a></td>" +
		  				"<td></td><td width='55' class=\"s_tab04_cen\">"+profName+"</td>" +
		  				"<td></td><td width='57' class=\"s_tab04_cen\">"+studentCnt+"</td>" +
		  				"<td></td><td width='66' class=\"s_tab04_cen\">"+qnaCnt+"</td>" +
		  				"<td></td><td width='65' class=\"s_tab04_cen\">"+noticeCnt+"</td>" +
		  				"<td></td><td width='102' class=\"s_tab04_cen\">"+reqCnt+"/"+resCnt+"</td>" +
		  				"<td></td><td width='40' class=\"s_tab04_cen\">"+forumCnt+"/"+forumContentsCnt+"</td>" +
		  				"<td></td><td width='40' class=\"s_tab04_cen\">"+reportCnt+"</td>" +
		  				"<td></td><td width='40' class=\"s_tab04_cen\">"+examCnt+"</td>" +
		  				"<td></td><td width='52' class=\"s_tab04_cen\">"+resultYn+"</td>" +
		  				"</tr>";

		  		if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"19\"></td></tr>";

				allItemCnt--;
	  		}
	  	}
	  	objStr += "</table>";

		staticListObj.innerHTML = objStr;
		staticListObj.style.display = "block";
	}


//-------------------------------------------------------------------------------------

	//���������� �ٲٸ� �б⵵ �ٲ۴�.
	function changeYear() {
		var curriYear = DWRUtil.getValue("pCurriYear");

		termSelectList(curriYear);
	}

//-------------------------------------------------------------------------------------

	//�����б� �ʱ�ȭ
	function termSelectList(curYear) {
		CurriSubCourseStaticWork.termSelectList(curYear, termSelectListCallBack);
	}

	//�����б� ����Ʈ�ڽ� ǥ��
	function termSelectListCallBack(data) {
		var rowLength = 0;
		if(data != null){
			rowLength = data.length;
		}

		var curriTermListObj = $("curriTermList");
		var objStr = "<select name='pCurriTerm' onChange = 'changTerm()'>" +
						"<option value=''>--�����б�--</option>";

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

	//�����б� ����
	function changTerm() {
		var curriTerm = DWRUtil.getValue("pCurriTerm");

		autoReload();
	}

//-------------------------------------------------------------------------------------

	function goList() {
		$("staticListDiv").style.display = "none";

		Effect.Appear("infoListDiv");
	}

	function exelDownload(gubun) {
		var curriYear = DWRUtil.getValue("pCurriYear");
		var curriTerm = DWRUtil.getValue("pCurriTerm");
		var curriCode = DWRUtil.getValue("pCurriCode");

		var param = "&pCurriCode="+curriCode+"&pCurriYear="+curriYear+"&pCurriTerm="+curriTerm;

		document.location.href = CONTEXTPATH+"/CurriSubStatic.cmd?cmd=attendCurriSubExcelDown&pGubun="+gubun+param;
	}

	function errorMessage(){
		alert("�۾��� ������ �߻��߽��ϴ�.\n��õ� ���ּ���!!!");
	}
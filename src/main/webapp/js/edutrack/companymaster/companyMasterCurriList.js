	// ���񸮽�Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var CURRIYEAR = 2007;

	function init(systemCode, contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.CURRIYEAR = DWRUtil.getValue("pCurriYear");

		termSelectList(CURRIYEAR);				//-- �б⼿��Ʈ
		autoReload();							//-- ��������Ʈ
		curriCodeSelectList(CURRIYEAR, '');		//-- ����Ʈ ��������Ʈ
	}

//---------------------------------------------------------------------------------------------

	//-- ��������Ʈ ������ ��������
	function autoReload() {
		var curYear	=	DWRUtil.getValue("pCurriYear");
		var curTerm	=	DWRUtil.getValue("pCurriTerm");
//alert('check03');
		CompanyMasterCourseWork.autoCompanyMasterCurriList(curYear, curTerm, autoReloadCallback);
	}

	//-- ��������Ʈ ������ ���
	function autoReloadCallback(data) {
		var rowLength = 0;
		var allItemCnt = 0;
		var No = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}
//alert(rowLength);
		var masterListObj = $("masterCurriList");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"13\">��ϵ� ������ �����ϴ�.</td></tr>";
	  	}else{
			for(i=0; i<rowLength; i++) {
	  			var curriSubDto = data[i];
	  			No++;

				var curriCode 			= 	curriSubDto.curriCode;
				var curriYear 			= 	curriSubDto.curriYear;
				var curriTerm 			= 	curriSubDto.curriTerm;
				var curriName 			= 	curriSubDto.curriName;
				var passStudentCnt 		= 	curriSubDto.passStudentCnt;
				var noPassStudentCnt 	= 	curriSubDto.noPassStudentCnt;
				var processStudentCnt	=	curriSubDto.processStudentCnt;
				var allStudentCnt		=	curriSubDto.allStudentCnt;
				var status				=	curriSubDto.connPoint;
				var statusStr			=	"";
				if(status == "ING") statusStr = "<font color='#FF0000'>������</font>";
				else if(status == "END") statusStr = "<b>����</b>";
				else if(status == "BEFORE") statusStr = "<font color='#f4a460'>�����</font>";

				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">" +
						"<td width='277' class=\"s_tab04\">"+curriName+"</td>" +
						"<td width='70' class=\"s_tab04_cen\">"+processStudentCnt+"</td>" +
						"<td width='70' class=\"s_tab04_cen\">"+passStudentCnt+"</td>" +
						"<td width='80' class=\"s_tab04_cen\">"+noPassStudentCnt+"</td>" +
						"<td width='80' class=\"s_tab04_cen\">"+allStudentCnt+"</td>" +
						"<td width='70' class=\"s_tab04_cen\">"+statusStr+"</td></tr>";

				if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";
				allItemCnt--;
	  		}
	  	}
	  	objStr += "</table>";

		masterListObj.innerHTML = objStr;
		masterListObj.style.display = "block";
	}

//-------------------------------------------------------------------------------------

	//���������� �ٲٸ� �б⵵ �ٲ۴�.
	function changeYear() {
		var curriYear = DWRUtil.getValue("pCurriYear");

		termSelectList(curriYear);				//-- �б� ����Ʈ
		curriCodeSelectList(curriYear, '');		//-- ���� ����Ʈ
		autoReload();							//-- ���� ����Ʈ
	}

//-------------------------------------------------------------------------------------

	//�����б� �ʱ�ȭ
	function termSelectList(curYear) {
		CurriSubStaticInfoWork.termSelectList(curYear, termSelectListCallBack);
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
		var curriYear = DWRUtil.getValue("pCurriYear");
		var curriTerm = DWRUtil.getValue("pCurriTerm");

		curriCodeSelectList(curriYear, curriTerm);		//-- ���� ����Ʈ
		autoReload();									//-- ���� ����Ʈ
	}

//-------------------------------------------------------------------------------------

	//-- (����Ʈ�ڽ���) ��Ź�����ڰ� ����ϴ� ���� ����Ʈ
	function curriCodeSelectList(curriYear, curriTerm) {
		CompanyMasterCourseWork.getCompanySelectCourseList(curriYear, curriTerm, curriCodeSelectListCallBack);
	}

	//-- (����Ʈ�ڽ���) ���� ����Ʈ ������ ����
	function curriCodeSelectListCallBack(data) {
		DWRUtil.removeAllOptions("pCurriCode");
		var defaultSelect = {"":"--������ü--"};
		DWRUtil.addOptions("pCurriCode", defaultSelect);
		DWRUtil.addOptions("pCurriCode", data);
	}

	//-- ���� ���� : ��ü ���ýÿ� ��������Ʈ, ���� ���ýÿ� ������ �н��� ��Ȳ ����Ʈ
	function changeCurriCode() {
		var curriYear = DWRUtil.getValue("pCurriYear");
		var curriTerm = DWRUtil.getValue("pCurriTerm");
		var curriCode =  DWRUtil.getValue("pCurriCode");

		if(curriYear != 0 && curriTerm != 0) {
			if(curriCode == "") {
				autoReload();
				$("studentCurriDiv").style.display = "none";
				Effect.Appear("companyCurriDiv");
			} else {
				studentAutoReload(curriYear, curriTerm, curriCode);
				$("companyCurriDiv").style.display = "none";
				Effect.Appear("studentCurriDiv");
			}
		} else {
			alert('�⵵�� �б⸦ �������� ������ �ּž� �մϴ�.');
			curriCodeSelectList(curriYear, curriTerm);
		}

	}

//-------------------------------------------------------------------------------------

	//
	function studentAutoReload(curriYear, curriTerm, curriCode) {
		CompanyMasterCourseWork.getCompanyStudentCurriInfo(curriYear, curriTerm, curriCode, studentAutoReloadCallback);
	}

	//
	function studentAutoReloadCallback(data) {

		var rowLength = 0;
		var allItemCnt = 0;
		var No = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}
//alert(rowLength);
		var stuListObj = $("studentCurriList");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"17\">����� �������� �����ϴ�.</td></tr>";
	  	}else{
			for(i=0; i<rowLength; i++) {
				var studentDto = data[i];
	  			No++;

	  			var userName	=	studentDto.userName;
	  			var userId		=	studentDto.userId;
	  			var scoreExam	=	studentDto.scoreExam;
	  			var scoreReport =	studentDto.scoreReport;
	  			var scoreAttend =	studentDto.scoreAttend;
	  			var scoreForum	=	studentDto.scoreForum;
	  			var scoreEtc1	=	studentDto.scoreEtc1;
	  			var scoreEtc2	=	studentDto.scoreEtc2;
	  			var totalScore	=	studentDto.totalScore;
	  			var scoreGubun	=	studentDto.scoreGubun;
	  			var grade		=	studentDto.grade;
	  			var getCredit	=	studentDto.getCredit;
	  			var completeYn	=	"";
	  			var enrollStatus =	studentDto.enrollStatus;
	  			var completeDate =	studentDto.completeDate;

	  			var completeStr	=	"";
	  			if(scoreGubun != "" && scoreGubun == "1") {
	  				completeStr	=	grade+"&nbsp;&nbsp;("+getCredit+")";
	  			} else if(scoreGubun != "" && scoreGubun == "2") {
	  				if(enrollStatus == "C" && completeDate != "")	completeStr = "<font color='#FF0000'><b>����</b></font>";
	  				else	completeStr = "<font color='#8fbc8f'><b>�̼���</b></font>";
	  			}


				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">" +
						"<td width='227' class=\"s_tab04\">"+userName+"&nbsp;&nbsp;("+userId+")"+"</td>" +
						"<td></td><td width='70' class=\"s_tab04_cen\">"+scoreExam+"</td>" +
						"<td></td><td width='70' class=\"s_tab04_cen\">"+scoreReport+"</td>" +
						"<td></td><td width='70' class=\"s_tab04_cen\">"+scoreAttend+"</td>" +
						"<td></td><td width='70' class=\"s_tab04_cen\">"+scoreForum+"</td>" +
						"<td></td><td width='70' class=\"s_tab04_cen\">"+totalScore+"</td>" +
						"<td></td><td width='70' class=\"s_tab04_cen\">"+completeStr+"</td></tr>";

				if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"17\"></td></tr>";
				allItemCnt--;
	  		}
	  	}
	  	objStr += "</table>";

		stuListObj.innerHTML = objStr;
		stuListObj.style.display = "block";
	}

//-------------------------------------------------------------------------------------

	function goList() {
		var curriYear = DWRUtil.getValue("pCurriYear");
		var curriTerm = DWRUtil.getValue("pCurriTerm");

		autoReload();
		curriCodeSelectList(curriYear, curriTerm);

		$("studentCurriDiv").style.display = "none";
		Effect.Appear("companyCurriDiv");
	}
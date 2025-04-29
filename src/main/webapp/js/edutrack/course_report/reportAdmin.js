
	var COURSE_ID = "";
	var CONTEXTPATH = "";
	var COURSELISTSIZE = 0;

	function init(contextPath, courseListSize, courseId, status) {
		COURSE_ID = courseId;
		CONTEXTPATH = contextPath;
		COURSELISTSIZE = courseListSize
		if(status == "PROF") {
			autoReloadProf();
		} else {
			autoReloadStu();
		}
	}

	//�����߰�(������)
	function Create_Report(){
       var f = document.f;

       if(COURSELISTSIZE > 1 && f.pCourseId.value == ""){
         alert("���� ������ ������ �ֽʽÿ�.");
         return;
       }

       f.pMODE.value = "write";

       f.submit();
    }

	//��������(������)
	function Edit_Report(reportid){
       var f = document.f;

       f.pReportId.value = reportid;
       f.pMODE.value = "edit";

       f.submit();
	}

    function Show_Report(reportid){
       var f = document.f;

       f.pReportId.value = reportid;

       f.action=CONTEXTPATH+"/ReportAdmin.cmd?cmd=reportShow";
       f.submit();
    }

    //�������(������)
    function reportResultUserList(reportid, reportSubject){
       var f = document.f;

       f.pReportId.value = reportid;
       f.pReportSubject.value = reportSubject;
       f.action=CONTEXTPATH+"/ReportResult.cmd?cmd=reportUserResultList";
       f.submit();
    }

	//��������Ʈ �ڵ����� �ѷ��ֱ�(������)
	function autoReloadProf(){
		var f = document.f;
		var COURSE_ID = f.pCourseId.value;
		ReportAdminWork.reportListAuto(COURSE_ID, autoReloadProfCallback);
	}

	// ���� ����Ʈ �ѷ��ֱ�(������)
	function autoReloadProfCallback(data){

		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var objStr = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>"

	  	if(rowLength == 0){
			objStr 	+= "<tr><td class=\"s_tab04_cen\" colspan=\"13\">��ϵ� ������ �����ϴ�.</td></tr>"

	  	}else{
	    	var courseId = "";
	    	var reportId = "";
	    	var reportSubject = "";
	    	var reportType1 = "";
	    	var reportType2 = "";
	    	var reportStartDate = "";
	    	var reportEndDate = "";
	    	var reportExtendDate = "";
	    	var reportScoreYn = "";
	    	var reportOpenYn = "";
	    	var reportRegistYn = "";
	    	var scoreYn = "";
	    	var registYn = "";
	    	var resultYn = "";

	    	var No = 0;
		  	for(i=0;i<rowLength;i++){
    			var reportInfoDTO = data[i];
    			No++;
    			courseId 			= reportInfoDTO.courseId;
				reportId 			= reportInfoDTO.reportId;
				reportSubject 		= reportInfoDTO.reportSubject;
				reportType1 		= reportInfoDTO.reportType1;
				reportType2 		= reportInfoDTO.reportType2;
				reportStartDate 	= reportInfoDTO.reportStartDate;
				reportEndDate 		= reportInfoDTO.reportEndDate;
				reportExtendDate 	= reportInfoDTO.reportExtendDate;
				reportScoreYn 		= reportInfoDTO.reportScoreYn;
				reportOpenYn 		= reportInfoDTO.reportOpenYn;
				reportRegistYn 		= reportInfoDTO.reportRegistYn;

				//���� ������ ����
				if(reportScoreYn == "Y") {
					scoreYn = "����";
				} else {
					scoreYn = "������";
				}
				//��Ͽ���
				if(reportRegistYn == "Y") {
					registYn = "<font color='black' style='font-size:9pt;line-height:12pt;font-family:����ü'>��ϿϷ�</font>"
					resultYn += "<a href=\"javascript:reportResultUserList('"+reportId+"','"+reportSubject+"');\">"
							 + "<font color='red' style='font-size:9pt;line-height:12pt;font-family:����ü'>�������</font></a>"
				} else {
					registYn = "<font color='red' style='font-size:9pt;line-height:12pt;font-family:����ü'>��ϴ��</font>"
					resultYn = "&nbsp;";
				}


			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
			    	+ "<td width='40' class=\"s_tab04_cen\">"+No+"</td>"
			    	+ "<td></td><td width='230' class=\"s_tab04\">"
			    	+ "<a href=\"javascript:Edit_Report('"+reportId+"');\">"+reportSubject+"</a></td>"
					+ "<td></td><td width='60' class=\"s_tab04_cen\">"+scoreYn+"</td>"
			    	+ "<td></td><td width='140' class=\"s_tab04_cen\">"+reportStartDate+" ~ "+reportEndDate+"</td>"
			    	+ "<td></td><td width='60' class=\"s_tab04_cen\">"+registYn+"</td>"
			    	+ "<td></td><td width='60' class=\"s_tab04_cen\">"
			    	+ "<a href=\"javascript:Show_Report('"+reportId+"');\">"
			    	+ "<font color='red' style='font-size:9pt;line-height:12pt;font-family:����ü'>����</font></a></td>"
			    	+ "<td></td><td width='60' class=\"s_tab04_cen\">"+resultYn+"</td>"
			    	+ "</tr>";

			    	resultYn = "";

			    	if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"13\"></td></tr>";
			    	allItemCnt--;
			}
		}
		objStr += "</table>";

		$("reportList").innerHTML = objStr;
		$("reportList").style.display = "block";
	 }

	 //����Ʈ �Է�&����&�󼼺���
	 function reportSendWriteForm(courseId, reportId, reportType2, insertYn, pMODE, writeYn, markCheckYn, sendCheckYn, stuOpenDate){
	   var f = document.f;
       f.action=CONTEXTPATH+"/ReportResult.cmd?cmd=reportStSendWriteForm&pCourseId="+courseId+"&pReportId="+reportId+"&pReportType2="+reportType2+"&pInsertYn="+insertYn+"&pMODE="+pMODE+"&pEndYn="+writeYn+"&pMarkCheckYn="+markCheckYn+"&pSendCheckYn="+sendCheckYn+"&pStuOpenDate="+stuOpenDate;
       f.submit();
    }

	 //��������Ʈ �ڵ����� �ѷ��ֱ�(�л�)
	function autoReloadStu(){
		var f = document.f;
		var COURSE_ID = f.pCourseId.value;
		ReportAdminWork.reportStListAuto(COURSE_ID, autoReloadStuCallback);
	}

	// ���� ����Ʈ �ѷ��ֱ�(�л�)
	function autoReloadStuCallback(data){

		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var objStr = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>"

	  	if(rowLength == 0){
			objStr 	+= "<tr><td height='25' align='center'>���� ����Ʈ�� �������� �ʽ��ϴ�.</td></tr>"
				   	+ "<tr><td height='1' class='b_td03'></td></tr>"

	  	}else{
	    	var courseId = "";
	    	var reportId = "";
	    	var reportSubject = "";
	    	var reportType2 = "";
	    	var reportStartDate = "";
	    	var reportEndDate = "";
	    	var reportExtendDate = "";
	    	var reportScoreYn = "";
	    	var reportOpenYn = "";
	    	var reportRegistYn = "";
	    	var sendCheckYn = "";
	    	var markCheckYn = "";
	    	var insertYn = "";
	    	var stuOpenDate = "";
	    	var writeYn = "";
	    	var pMODE = "";

	    	var ingMode = "";
	    	var type = "";
	    	var resultStatus = "";

	    	var No = 0;
		  	for(i=0;i<rowLength;i++){
    			var reportInfoDTO = data[i];
    			No++;
    			courseId 			= reportInfoDTO.courseId;
				reportId 			= reportInfoDTO.reportId;
				reportSubject 		= reportInfoDTO.reportSubject;
				reportType2 		= reportInfoDTO.reportType2;
				reportStartDate 	= reportInfoDTO.reportStartDate;
				reportEndDate 		= reportInfoDTO.reportEndDate;
				reportExtendDate 	= reportInfoDTO.reportExtendDate;
				reportScoreYn 		= reportInfoDTO.reportScoreYn;
				reportOpenYn 		= reportInfoDTO.reportOpenYn;
				reportRegistYn 		= reportInfoDTO.reportRegistYn;
				sendCheckYn			= reportInfoDTO.sendCheckYn;
				markCheckYn			= reportInfoDTO.markCheckYn;
				insertYn			= reportInfoDTO.insertYn;
				stuOpenDate			= reportInfoDTO.stuOpenDate;
				writeYn				= reportInfoDTO.writeYn;

				//�Է�&�������
				if(sendCheckYn == "") {
					pMODE = "ADD";
				} else {
					pMODE = "EDIT";
				}

				//���� ������ ����
				if(writeYn == "Y") {
					ingMode = "&nbsp;<font color=blue>(������)</font>";
				} else {
					ingMode = "&nbsp;<font color=red>(����)</font>";
				}

				//���� Ÿ��(����&����)
				if(reportType2 == "R") {
					type = "��������";
				} else {
					type = "��������";
				}

				//����ǥ��(����&��ü��&�򰡿Ϸ�)
				if(sendCheckYn == "" && markCheckYn == "") {
					resultStatus = "<font color=red>������</font>"
				}else if(sendCheckYn != "" && markCheckYn == "") {
					resultStatus = "<font color=blue>����</font>"
				}else if(markCheckYn != "") {
					resultStatus = "<font color=green>�򰡿Ϸ�</font>"
				}

			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
			    	+ "<td></td><td width='40' class=\"s_tab04_cen\">"+No+"</td>"
			    	+ "<td></td><td width='250' class=\"s_tab04\">"
			    	+ "<a href=\"javascript:reportSendWriteForm('"+courseId+"','"+reportId+"','"+reportType2+"','"+insertYn+"','"+pMODE+"','"+writeYn+"','"+markCheckYn+"','"+sendCheckYn+"','"+stuOpenDate+"');\">"+reportSubject
			    	+ ingMode+"</a></td>"
					+ "<td></td><td width='70' class=\"s_tab04_cen\">"+type+"</td>"
			    	+ "<td></td><td width='140' class=\"s_tab04_cen\">"+reportStartDate+" ~ "+reportEndDate+"</td>"
			    	+ "<td></td><td width='80' class=\"s_tab04_cen\">"+reportExtendDate+"</td>"
			    	+ "<td></td><td width='70' class=\"s_tab04_cen\">"+resultStatus+"</td>"
			    	+ "</tr>";

				if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"13\"></td></tr>";
				allItemCnt--;
			}
		}
		objStr += "</table>";

		$("reportStList").innerHTML = objStr;
		$("reportStList").style.display = "block";
	 }
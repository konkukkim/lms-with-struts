	//���ǽǰԽ���
	var SYSTEMCODE = "1";
	var CONTEXTPATH = "";
	var COURSEID = "";
	var BBSTYPE = "";
	var SEQNO = "";
	var CURPAGE = "";
	var CURRICODE = "";
	var CURRIYEAR = "";
	var CURRITERM = "";
	var COURSELISTSIZE = 0;
	var MODE = "";

	function init(systemCode,contextPath,courseId,bbsType,seqNo,curPage) {

		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.COURSEID = courseId;
		this.BBSTYPE = bbsType;
		this.SEQNO = seqNo;
		this.CURPAGE = curPage;

		var f = document.bbsEditForm;
		this.CURRICODE = f.CurriCode.value;
		this.CURRIYEAR = f.CurriYear.value;
		this.CURRITERM = f.CurriTerm.value;
		this.COURSELISTSIZE = f.COURSELISTSIZE.value;
		this.MODE = f.pMode.value;

		autoReload();
	}

	function autoReload(){
		CurriBbsContentsWork.getCurriBbsContentsInfo(CURRICODE, CURRIYEAR, CURRITERM, COURSEID, BBSTYPE, SEQNO, autoReloadCallback);
	}

	function autoReloadCallback(data){
		var rFileName1 = data.rfileName1;
		var sFileName1 = data.sfileName1;
		var filePath1 = data.filePath1;
		var fileSize1 = data.fileSize1;

		var rFileName2 = data.rfileName2;
		var sFileName2 = data.sfileName2;
		var filePath2 = data.filePath2;
		var fileSize2 = data.fileSize2;

		var rFileName3 = data.rfileName3;
		var sFileName3 = data.sfileName3;
		var filePath3 = data.filePath3;
		var fileSize3 = data.fileSize3;

		var imgPath = "curri_bbs/";

		if(rFileName1 != "") {
			$("pFile1").innerHTML = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif' align='absmiddle'>&nbsp;"
								+ "<a href=\"javascript:fileDownload('"+rFileName1+"','"+sFileName1+"','"+filePath1+"','"+fileSize1+"');\">"+rFileName1+"</a>&nbsp;&nbsp;<a href=\"javascript:delFile('1')\">[�������ϻ���]</a><br>";
			$("pFile1").style.display = "block";
		} else {
			//$("pFile1").innerHTML = "";
			//$("pFile1").style.display = "none";
		}
		if(rFileName2 != "") {
			$("pFile2").innerHTML = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif' align='absmiddle'>&nbsp;"
								+ "<a href=\"javascript:fileDownload('"+rFileName2+"','"+sFileName2+"','"+filePath2+"','"+fileSize2+"');\">"+rFileName2+"</a>&nbsp;&nbsp;<a href=\"javascript:delFile('2')\">[�������ϻ���]</a><br>";
			$("pFile2").style.display = "block";
		} else {
			//$("pFile2").innerHTML = "";
			//$("pFile2").style.display = "none";
		}
		if(rFileName3 != "") {
			$("pFile3").innerHTML = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif' align='absmiddle'>&nbsp;"
								+ "<a href=\"javascript:fileDownload('"+rFileName3+"','"+sFileName3+"','"+filePath3+"','"+fileSize3+"');\">"+rFileName3+"</a>&nbsp;&nbsp;<a href=\"javascript:delFile('3')\">[�������ϻ���]</a><br>";
			$("pFile3").style.display = "block";
		} else {
			//$("pFile3").innerHTML = "";
			//$("pFile3").style.display = "none";
		}

	}


	function fileDownload(rfilename, sfilename, filepath, filesize){
		var loc=CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
		hiddenFrame.document.location = loc;
    }

	// ÷������ ���� start
	function delFile(fileNum){
		if(confirm("÷�������� �����Ͻðڽ��ϱ�?"))
			CurriBbsContentsWork.bbsFileDelete(CURRICODE, CURRIYEAR, CURRITERM, COURSEID, BBSTYPE, SEQNO, fileNum, deleteFileCallback);
		else
			return;
    }

    function deleteFileCallback(data) {
		var result = data;
	  	if(result == '1'){
			$("pFile1").innerHTML = "";
			$("pFile1").style.display = "none";
	  	} else if(result == '2'){
			$("pFile2").innerHTML = "";
			$("pFile2").style.display = "none";
	  	} else if(result == '3'){
			$("pFile3").innerHTML = "";
			$("pFile3").style.display = "none";
	  	} else return;
    }
    // ÷������ ���� end


    function isEmpty(data)
	{
		for ( var i = 0 ; i < data.length ; i++ ) {
			if ( data.substring( i, i+1 ) != ' ' )
				return false;
		}
		return true;
	}

	function submitCheck()
	{
		/*  WEAS ���� ��ũ��Ʈ3(����) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS ���� ��ũ��Ʈ3(��) */
		var f = document.Input;
/*
		if(COURSELISTSIZE != 0 ) {
			if(f.pCourseId[f.pCourseId.selectedIndex].value == '') {
				alert('������ �����ϼ���');
				f.pCourseId.focus();
				return false;
			}
		}
*/
		if(!validate(f))
	   	   return false;

	   	f.submit();
	}

	function goDel(){
		document.location.href = CONTEXTPATH+"/CurriBbsContents.cmd?cmd=curriBbsContentsDelete&pMode="+MODE+"&pBbsType="+BBSTYPE+"&pSeqNo="+SEQNO+" ";
	}
    function goList(){
		document.location.href = CONTEXTPATH+"/CurriBbsContents.cmd?cmd=curriBbsContentsPagingList&pMode="+MODE+"&pBbsType="+BBSTYPE+" ";
	}
	function goReset() {
		document.Input.reset();
	}



	// ��ü�Խ��Ǹ���Ʈ
	function list_init(systemCode,contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;

		listAutoReload();
	}

	// �Խ��Ǹ���Ʈ �޾ƿ���
	function listAutoReload(){
		var f = document.f;
		var curPage = f.curPage.value;
		var pBbsType = f.pBbsType.value;
		var pSearchKey = f.pSearchKey[f.pSearchKey.selectedIndex].value;
		var pKeyWord = ajaxEnc(f.pKeyWord.value);

		CurriBbsContentsWork.getCurriBbsPageList(curPage, pBbsType, pSearchKey, pKeyWord, listAutoReloadCallback);
	}

	//�Խ��� ����Ʈ �����
	function listAutoReloadCallback(data) {
		var f = document.f;
		var hotchk = f.pHotChk.value;
		var addDate = f.pAddDate.value;
		var curPage = f.curPage.value;

		var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;		// �Ϲ� �Խñ�
	  	var noticeList = data.data1List;	// ������
	  	var paggingStr = data.pagging;
		var rowLength = 0;
		var noticeRowLength = 0;

		if(dataList != null)
			rowLength = dataList.length;
		if(noticeList != null)
			noticeRowLength = noticeList.length;

		var bbsListObj = $("bbsList");
	  	var paggingObj = $("getPagging");

	  	if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

		var depthSpace = "";
		var commCnt = 0;
		var commStr = "";
		var bbsHot = "";
		var bbsNew = "";
		var depthSpace = "";
		var hitNo = 0;
		var regDate = "";
		var depthNo = 0;
		var subject = "";
		var bbsType = "";
		var seqNo = 0;
		var regName = "";
		var subjectList = "";

		//--	�������� ���
		var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";
		if(noticeRowLength == 0 && rowLength == 0){
			objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"13\">��ϵ� ���� �����ϴ�.</td></tr>";
		} else {

			if(noticeRowLength != 0){

				if(curPage <= 1) {
					for(i=0; i<noticeRowLength; i++) {

						var noticeDto = noticeList[i];

						hitNo = noticeDto.hitNo;						// �� ��ȸ��
						regDate = noticeDto.regDate.substring(0,8);	// �� ��ϳ�¥
						depthNo = noticeDto.depthNo;					// �� ���� ��
						commCnt = noticeDto.commCnt;					// ���� �ڸ�Ʈ ��
						subject = noticeDto.subject;					// �� ����
						bbsType = noticeDto.bbsType;
						seqNo = noticeDto.seqNo;						// �� ��ȣ
						regName = noticeDto.regName;					// �� ����� �̸�

						subjectLink = "<a href=\""+CONTEXTPATH+"/CurriBbsContents.cmd?cmd=curriBbsContentsShow&pBbsType="+bbsType+"&pSeqNo="+seqNo+"\">";

						if(hitNo > hotchk) bbsHot = "&nbsp;<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_hot.gif'>";
						if(parseFloat(regDate) > parseFloat(addDate)) bbsNew = "&nbsp;<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_new.gif'>";
						if(depthNo > 0) depthSpace = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_re.gif'>";
						for(j=0; j<depthNo;j++) depthSpace = "&nbsp;"+depthSpace;
						if(commCnt > 0) commStr = "<span style='font-size:7pt;'>["+commCnt+"]</span>";

						objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">" +
								"<td width='54' class=\"s_tab04_cen\">*</td>" +
								"<td></td><td width='360' class=\"s_tab04\">"+depthSpace+subjectLink+subject+"</a>"+commStr+bbsNew+bbsHot+"</td>" +
								"<td></td><td width='94' class=\"s_tab04_cen\">"+regName+"</td>" +
								"<td></td><td width='98' class=\"s_tab04_cen\">"+regDate.substring(0, 4)+"."+regDate.substring(4, 6)+"."+regDate.substring(6, 8)+"</td>" +
								"<td></td><td width='67' class=\"s_tab04_cen\">"+hitNo+"</td>" +
								"</tr><tr class=\"s_tab03\"><td colspan=\"13\"></td></tr>";

						bbsHot = "";
						bbsNew = "";
						depthSpace = "";
						commCnt = 0;
						commStr = "";
					}

				}
			}

			bbsHot = "";
			bbsNew = "";
			depthSpace = "";
			commCnt = 0;
			commStr = "";

			if(rowLength != 0){

		  		for(i=0;i<rowLength;i++){
		  			var curriBbsDto = dataList[i];
		  			hitNo = curriBbsDto.hitNo;						// �� ��ȸ��
					regDate = curriBbsDto.regDate.substring(0,8);	// �� ��ϳ�¥
					depthNo = curriBbsDto.depthNo;					// �� ���� ��
					commCnt = curriBbsDto.commCnt;					// ���� �ڸ�Ʈ ��
					subject = curriBbsDto.subject;					// �� ����
					bbsType= curriBbsDto.bbsType;
					seqNo = curriBbsDto.seqNo;						// �� ��ȣ
					regName = curriBbsDto.regName;					// �� ����� �̸�

					subjectLink = "<a href=\""+CONTEXTPATH+"/CurriBbsContents.cmd?cmd=curriBbsContentsShow&pBbsType="+bbsType+"&pSeqNo="+seqNo+"&curPage="+curPage+"\">";

					if(hitNo > hotchk) bbsHot = "&nbsp;<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_hot.gif'>";
					if(regDate > addDate) bbsNew = "&nbsp;<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_new.gif'>";
					if(depthNo > 0) depthSpace = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_re.gif'>";
					for(j=0; j<depthNo;j++) depthSpace = "&nbsp;"+depthSpace;
					if(commCnt > 0)	commStr = "<span style='font-size:7pt;'>["+commCnt+"]</span>";

					objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">" +
							"<td width='54' class=\"s_tab04_cen\">"+totalCount+"</td>" +
							"<td></td><td width='360' class=\"s_tab04\">"+depthSpace+subjectLink+subject+"</a>"+commStr+bbsNew+bbsHot+"</td>" +
							"<td></td><td width='94' class=\"s_tab04_cen\">"+regName+"</td>" +
							"<td></td><td width='98' class=\"s_tab04_cen\">"+regDate.substring(0, 4)+"."+regDate.substring(4, 6)+"."+regDate.substring(6, 8)+"</td>" +
							"<td></td><td width='67' class=\"s_tab04_cen\">"+hitNo+"</td>" +
							"</tr>";

					if(totalCount > 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"13\"></td></tr>";

					totalCount--;
					bbsHot = "";
					bbsNew = "";
					depthSpace = "";
					commCnt = 0;
					commStr = "";
				}

			}

		}

		objStr += "</table>";

		bbsListObj.innerHTML = objStr;
		bbsListObj.style.display = "block";

	}


	// ����¡ó��
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
		listAutoReload();
	}



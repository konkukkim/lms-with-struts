	// ��ü�Խ���
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";

	function init(systemCode,contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;

		autoReload();
	}

	// �Խ��Ǹ���Ʈ �޾ƿ���
	function autoReload(){
		var f = document.f;
		var curPage = f.curPage.value;
		var pBbsId = f.pBbsId.value;
		var pSearchKey = f.pSearchKey[f.pSearchKey.selectedIndex].value;
		var pKeyWord = ajaxEnc(f.pKeyWord.value);

		BbsContentsListWork.bbsContentsListAuto(curPage, pBbsId, pSearchKey, pKeyWord, autoReloadCallback);

	}

	function autoReloadCallback(data){

		var f = document.f;
		var curPage = f.curPage.value;
		var mode = f.pMode.value;
		var sec = f.pSec.value;
		var menuno = f.MENUNO.value;

		var f1 = document.ff1;
		var hotchk = f1.pHotChk.value;
		var addDate = f1.pAddDate.value;

		var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;		// �Ϲ� �Խñ�
	  	var noticeList = data.data1List;	// ������
	  	var paggingStr = data.pagging;
		var rowLength = 0;
		var noticeRowLength = 0;
		var No = 0;

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
		var bbsId = 0;
		var seqNo = 0;
		var regName = "";
		var dispLine = 0;

		//--	�������� ���
		var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

		if(noticeRowLength == 0 && rowLength == 0) {
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"13\">��ϵ� ���� �����ϴ�.</td></tr>";
		} else {

			if(noticeRowLength != 0){

				if(curPage <= 1) {
					for(i=0; i<noticeRowLength; i++) {
						var bbsContentsDTO = noticeList[i];

						hitNo = bbsContentsDTO.hitNo;						// �� ��ȸ��
						regDate = bbsContentsDTO.regDate.substring(0,8);	// �� ��ϳ�¥
						depthNo = bbsContentsDTO.depthNo;					// �� ���� ��
						commCnt = bbsContentsDTO.commCnt;						// ���� �ڸ�Ʈ ��
						subject = bbsContentsDTO.subject;					// �� ����
						bbsId = bbsContentsDTO.bbsId;						// �Խ��� ���̵�
						seqNo = bbsContentsDTO.seqNo;						// �� ��ȣ
						regName = bbsContentsDTO.regName;					// �� ����� �̸�

						var subjectLink = "<a href='"+CONTEXTPATH+"/BbsContents.cmd?cmd=bbsContentsShow&pBbsId="+bbsId+"&pMode="+mode+"&pSec="+sec+"&MENUNO="+menuno+"&pSeqNo="+seqNo+"&curPage="+curPage+"'>";

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
					}	//end for
				}
		  	}

		  	if(rowLength != 0) {

		  		for(i=0;i<rowLength;i++){
		  			var bbsContentsDTO = dataList[i];
		  			No++;

		  			hitNo = bbsContentsDTO.hitNo;						// �� ��ȸ��
					regDate = bbsContentsDTO.regDate.substring(0,8);	// �� ��ϳ�¥
					depthNo = bbsContentsDTO.depthNo;					// �� ���� ��
					commCnt = bbsContentsDTO.commCnt;					// ���� �ڸ�Ʈ ��
					subject = bbsContentsDTO.subject.substring(0, 32)+"...";					// �� ����
					bbsId = bbsContentsDTO.bbsId;						// �Խ��� ���̵�
					seqNo = bbsContentsDTO.seqNo;						// �� ��ȣ
					regName = bbsContentsDTO.regName;					// �� ����� �̸�
					dispLine = bbsContentsDTO.dispLine;

					var subjectLink = "<a href='"+CONTEXTPATH+"/BbsContents.cmd?cmd=bbsContentsShow&pBbsId="+bbsId+"&pMode="+mode+"&pSec="+sec+"&pSeqNo="+seqNo+"&curPage="+curPage+"'>";

					if(hitNo > hotchk) bbsHot = "&nbsp;<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_hot.gif'>";
					if(regDate > addDate) bbsNew = "&nbsp;<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_new.gif'>";
					if(depthNo > 0) depthSpace = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_re.gif'>";
					for(j=0; j<depthNo;j++) depthSpace = "&nbsp;&nbsp;&nbsp;"+depthSpace;
					if(commCnt > 0) commStr = "<span style='font-size:7pt;'>["+commCnt+"]</span>";

					objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">" +
							"<td width='54' class=\"s_tab04_cen\">"+totalCount+"</td>" +
							"<td></td><td width='360' class=\"s_tab04\">"+depthSpace+subjectLink+subject+"</a>"+commStr+bbsNew+bbsHot+"</td>" +
							"<td></td><td width='94' class=\"s_tab04_cen\">"+regName+"</td>" +
							"<td></td><td width='98' class=\"s_tab04_cen\">"+regDate.substring(0, 4)+"."+regDate.substring(4, 6)+"."+regDate.substring(6, 8)+"</td>" +
							"<td></td><td width='67' class=\"s_tab04_cen\">"+hitNo+"</td>" +
							"</tr>";

					if(No != dispLine) objStr += "<tr class=\"s_tab03\"><td colspan=\"13\"></td></tr>";

					totalCount--;
					bbsHot = "";
					bbsNew = "";
					depthSpace = "";
					commCnt = 0;
					commStr = "";
		  		}	//end for

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
		autoReload();
	}

	// ����Ʈ �޾ƿ���-�˻�
	function goSearch(){
		var f = document.f;
		var curPage = 1;
		var pBbsId = f.pBbsId.value;
		var pSearchKey = f.pSearchKey[f.pSearchKey.selectedIndex].value;
		var pKeyWord = ajaxEnc(f.pKeyWord.value);

		BbsContentsListWork.bbsContentsListAuto(curPage, pBbsId, pSearchKey, pKeyWord, autoReloadCallback);
	}

	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}


	function goAdd() {
		var f = document.f;
		var pBbsId = f.pBbsId.value;
		var pSec = f.pSec.value;
		var pMode = f.pMode.value;
		var MENUNO = f.MENUNO.value;
		var MainMenu = f.MainMenu.value;
		
		var Param = "&MENUNO="+MENUNO+"&MainMenu="+MainMenu+"&pMode="+pMode+"&pSec="+pSec;

		document.location = CONTEXTPATH+"/BbsContents.cmd?cmd=bbsContentsWrite&pBbsId="+pBbsId+Param;
	}

	function askLogin() {
		alert('�α����� ���ּ���.');
		window.open(CONTEXTPATH+'/User.cmd?cmd=usersLoginShow&pMode=MEMBER','loginpopup','width=424, height=266,top=0, left=0, directories=no,status=no,menubar=no,scrollbars=yes,resizable=no');
	}

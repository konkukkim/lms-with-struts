	// ���ϴٿ�ε�
	function fileDownload(rfilename, sfilename, filepath, filesize) {
       var loc = CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }
	
	// ��������Ʈ�� ���ư���	
	function goCurriList() {
		document.location = CONTEXTPATH+"/PublishCurriSub.cmd?cmd=publishCurriSubPageList&MENUNO=1&pPareCode1=Test0001&pPareCode2=OPEN0002&pMode=Home&pGubun="+PGUBUN;
	}
	
	// ��й� �Խñ� ����Ʈ�� �̵�
	function goForum() {
		var	param	=	"&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM+"&pCourseId="+COURSE_ID;
		var	goUrl	=	CONTEXTPATH+"/PublishCurriSub.cmd?cmd=publishForumContentsPagingList";
		document.location = goUrl + param;
	}
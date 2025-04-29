	// 파일다운로드
	function fileDownload(rfilename, sfilename, filepath, filesize) {
       var loc = CONTEXTPATH+"/jsp/"+SYSTEMCODE+"/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }
	
	// 과정리스트로 돌아가기	
	function goCurriList() {
		document.location = CONTEXTPATH+"/PublishCurriSub.cmd?cmd=publishCurriSubPageList&MENUNO=1&pPareCode1=Test0001&pPareCode2=OPEN0002&pMode=Home&pGubun="+PGUBUN;
	}
	
	// 토론방 게시글 리스트로 이동
	function goForum() {
		var	param	=	"&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM+"&pCourseId="+COURSE_ID;
		var	goUrl	=	CONTEXTPATH+"/PublishCurriSub.cmd?cmd=publishForumContentsPagingList";
		document.location = goUrl + param;
	}
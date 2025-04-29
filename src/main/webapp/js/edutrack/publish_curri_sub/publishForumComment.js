	// 댓글리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var CURRI_CODE = "";
	var CURRI_YEAR = 0;
	var CURRI_TERM = 0;		
	var FORUM_ID = "";
	var COURSE_ID = "";
	var SEQ_NO = "";
	var COMMENT_USE_YN = "";
	var PGUBUN = "";
	var USER_TYPE = "";
	var CURPAGE = 0;
	var USERID = "";

	// 초기화
	function init(systemCode,contextPath, courseId, forumId, seqNo, commentUseYn, userType) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.COURSE_ID = courseId;
		this.FORUM_ID = forumId;
		this.SEQ_NO = seqNo;
		this.COMMENT_USE_YN = commentUseYn;
		this.USER_TYPE = userType;
		
		var f = document.f;
		this.CURRI_CODE = f.pCurriCode.value;
		this.CURRI_YEAR = f.pCurriYear.value;
		this.CURRI_TERM = f.pCurriTerm.value;
		this.PGUBUN = f.pGubun.value;
		this.CURPAGE = f.curPage.value;
		this.USERID = f.USERID.value;

		autoReload();
	}
	
	
	function autoReload() {
		var curPageComment = DWRUtil.getValue("curPageComment");
		CourseForumCommentWork.publishForumCommentPagingListAjax(curPageComment, CURRI_CODE, CURRI_YEAR, CURRI_TERM, COURSE_ID, FORUM_ID, SEQ_NO, autoReloadCallback);
	}
	
	
	function autoReloadCallback(data) {
	  	var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;
	  	var paggingStr = data.pagging;
		var rowLength = 0;

		if(dataList != null) rowLength = dataList.length;

	  	var commentListObj = $("commentList");
	  	var paggingObj = $("getPagging");
	  	var objStr = "<table width=\"100%\" align=\"center\"><tr><td class=\"reply_r_textform\"><table border='0' width=\"650\" align=\"center\">";

		if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}else{
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "none";
		}

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">등록된 코멘트가 없습니다.</td></tr>";
	  	}else{
			var preName = "";
		  	for(i=0;i<rowLength;i++){
				var courseForumCommentDTO = dataList[i];

		    	var commNo = courseForumCommentDTO.commNo;
		    	var contents = courseForumCommentDTO.contents;
		    	var emoticonNum = courseForumCommentDTO.emoticonNum;
		    	var regId = courseForumCommentDTO.regId;
		    	var regName = courseForumCommentDTO.regName;
		    	var regDate = courseForumCommentDTO.regDate;

		    	var emoticonImgSrc = "";
		    	var chkCommRegPass = "F";

		    	// 이모티콘 이미지
		    	if(emoticonNum !=""){
		    		if(emoticonNum != "0")
			    		emoticonImgSrc = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_0"+emoticonNum+".gif'  border='0' align='absmiddle'>";
			    	else
			    		emoticonImgSrc="";
		    	}
		    	
				objStr += "<tr><td valign=\"middle\" width=\"30\">"+emoticonImgSrc+"</td>"
					+ "<td width=\"100\"><font color=#9a7441>"+regName+"</font></td>"
					+ "<td>"+contents+"&nbsp;&nbsp;&nbsp;<img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_reply.gif\" align=\"absmiddle\">&nbsp;</td>"
					+ "<td width=\"100\" align=\"right\" style=\"padding-right:6px\">"+regDate+"</td>"
					+ "<td width=\"18\" align='right' valign='middle'>";
					
				if(USERID == regId) {
					objStr += "<a href=\"javascript:goCommentEdit('"+commNo+"');\">"
							+ "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/re_del.gif' alt='댓글삭제하기' width='9' height='9' align='absmiddle' border='0'></a>";	
				}
				else {
					objStr += "<a href=\"javascript:goTaken('"+commNo+"');\">"
							+ "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/re_del.gif' alt='댓글삭제하기' width='9' height='9' align='absmiddle' border='0'></a>";	
				}
				
				objStr += "</td>"
					+ "</tr>"
					+ "<tr><td colspan='5' class=\"s_tab03\" height=1></td></tr>";

			    totalCount--;
			}
			paggingObj.innerHTML = paggingStr;
   	  		paggingObj.style.display = "block";
		}
		objStr += "</table>";

		commentListObj.innerHTML = objStr;
		commentListObj.style.display = "block";		
	}
	
	// 페이징처리
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["Input"].elements["curPageComment"].value = page;
		autoReload();
	}

	// 폼체크
	function formCheck(strId){
		var f = document.Input;
		
		if(f.pRegName.value == ""){
			alert("이름을 입력하세요");
			new Effect.Highlight("pRegName");
			f.pRegName.focus();
			return false;
		}
			
		if(strId == '' || strId == 'null') {
			if(f.pRegPasswd.value == ""){
				alert("비밀번호를 입력하세요");
				new Effect.Highlight("pRegPasswd");
				f.pRegPasswd.focus();
				return false;
			}			
		}

		if(f.pContents.value == ""){
			alert("내용을 입력하세요");
			new Effect.Highlight("pContents");
			f.pContents.focus();
			return false;
		}
		return true;
	}
	
	// 댓글 등록
	function addComment(strId){
		var regName = "";
		var regPasswd = "";
		
		if(formCheck(strId)){
			var f = document.Input;
			var contents = ajaxEnc(f.pContents.value);
			var emoticonNum = f.pEmoticon.value;
			regName = f.pRegName.value;

			if(strId == '' || strId == 'null') {
				regPasswd = f.pRegPasswd.value;
			}

			CourseForumCommentWork.publishForumCommentRegist(CURRI_CODE, CURRI_YEAR, CURRI_TERM, COURSE_ID, FORUM_ID, SEQ_NO, contents, emoticonNum, regName, regPasswd, courseForumCommentRegistCallback);
			
		}else return;
	}

	//댓글 등록후 호출..
	function courseForumCommentRegistCallback(data){
 		var result = data;
	  	if(result == '1'){
	  		initComment();
	  		CourseForumCommentWork.getForumCommentCount(CURRI_CODE, CURRI_YEAR, CURRI_TERM, COURSE_ID, FORUM_ID, SEQ_NO, commentCountCallback);
			document.Input.curPageComment.value = "1";
		  	autoReload();
	  	}else{
	  		return;
	  	}
	}
	
	function initComment(){
		var f = document.Input;
		if(USER_TYPE == "" || USER_TYPE == "null") {
			f.pRegName.value = "";
		  	f.pRegPasswd.value = "";
		}
		f.pContents.value = "";
		f.pEmoticon.value = "0";

		//선택된 이모티콘창 지움
		$("selectedEmoticon").style.display = "none";
	}

	function selectEmoticon(emoticonNum){
    
		var f = document.Input;
		f.pEmoticon.value = emoticonNum;

	  	var emoticonObj = document.getElementById("selectedEmoticon");
		if(emoticonNum != 0 ){

			var objStr = "<a href=\"javascript:selectEmoticon('0')\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/emoticon/"+emoticonNum+".gif' border='0' align='absmiddle'></a>";

			emoticonObj.innerHTML = objStr;
			emoticonObj.style.display = "";
		}
		else{
			emoticonObj.style.display = "none";
		}
	}

//----------------------------------------------------------------------------------------------------------------------
	
	//-- 수정과 삭제 버튼 클릭시 하위에 비밀번호 입력창이 보여진다.
	function goTaken(str) {
		if($("regPassChkDiv").style.display == "block") {
			$("regPassChkDiv").style.display = "none";
		} else {
			$("regPassChkDiv").style.display = "block";
		}
		document.f1.pGoMode.value = str;
	}
	
	//-- 비밀번호를 체크하여 본인이 작성한 글인지를 판단한다. - 수정과 삭제 권한의 결정
	function goChkPass(){
		var f = document.f1;
		var regpass = f.regPass.value;
		var goMode = f.pGoMode.value;
		
		if(f.regPass.value == ""){
			alert("비밀번호를 입력하세요");
			new Effect.Highlight("pRegPasswd");
			f.regPass.focus();
			return false;
		}
		
		f.regPass.value = "";
		
		if(goMode == "Edit" || goMode == "Del") {
			PublishCurriSubWork.publishForumContentsPassChkForm(CURRI_CODE, CURRI_YEAR, CURRI_TERM, COURSE_ID, FORUM_ID, SEQ_NO, regpass, goChkPassCallback);	
		} else {
			var commNo = goMode;
			PublishCurriSubWork.publishForumCommentPassChkForm(CURRI_CODE, CURRI_YEAR, CURRI_TERM, COURSE_ID, FORUM_ID, SEQ_NO, commNo, regpass, goChkPassCallback);
		}
		$("regPassChkDiv").style.display = "none";
	}
	
	//-- 비밀번호 확인 후 수정과 삭제에 따라 각 기능을 분화
	function goChkPassCallback(data) {
		var result = data;
		var goMode = document.f1.pGoMode.value;
		
	  	if(result != "0"){
	  		if(goMode == 'Edit') {
	  			var param1 = "&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM;
				var param2 = "&pCourseId="+COURSE_ID+"&pForumId="+FORUM_ID+"&pSeqNo="+SEQ_NO+"&curPage="+CURPAGE+"&pGubun="+PGUBUN;
				document.location.href = CONTEXTPATH+"/CourseForumContents.cmd?cmd=courseForumContentsEdit&MENUNO=1&pMode=Home"+param1+param2;
	  		} 
	  		else if(goMode == 'Del') {

				var childCnt = document.f.pChildCnt.value;		//답변글
				var commCnt = document.f.pCommentCnt.value;		//댓글

				if(childCnt == 0 && commCnt == 0) {
					if(confirm("삭제 하시겠습니까?")){
						CourseForumContentsWork.courseForumContentsDeleteAjax(CURRI_CODE, CURRI_YEAR, CURRI_TERM, COURSE_ID, FORUM_ID, SEQ_NO, forumDeleteCallback);
					} else {
						return;			
					}
				} else if(childCnt != 0) {
					alert('하위글이 있어 삭제할 수 없습니다.');
				} else if(commCnt != 0) {
					alert('댓글이 있어 삭제할 수 없습니다.');
				}
	  		} else {
				if(confirm("댓글을 삭제 하시겠습니까?")){
					var f = document.Input;
					var seqNo = DWRUtil.getValue("pSeqNo");
					var commNo = goMode;
	
					CourseForumCommentWork.publishForumCommentDelete(CURRI_CODE, CURRI_YEAR, CURRI_TERM, COURSE_ID, FORUM_ID, SEQ_NO, commNo, courseForumCommentDeleteCallback);
					CourseForumCommentWork.getForumCommentCount(CURRI_CODE, CURRI_YEAR, CURRI_TERM, COURSE_ID, FORUM_ID, SEQ_NO, commentCountCallback);
				}else
					return;
	  		}
	  	}else{
	  		alert('비밀번호가 맞지 않습니다.');
	  	}
	}
	
	//-- 토론글 삭제 후 호출
	function forumDeleteCallback(data) {
		var result = data;
		if(result == "1") {
			var param1 = "&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM;
			var param2 = "&pCourseId="+COURSE_ID+"&pForumId="+FORUM_ID+"&curPage="+CURPAGE+"&pGubun="+PGUBUN;
			document.location.href = CONTEXTPATH+"/PublishCurriSub.cmd?cmd=publishForumContentsPagingList&MENUNO=1&pMode=Home"+param1+param2;
		} else {
			return;
		}
	}

	//댓글 삭제후 호출..
	function courseForumCommentDeleteCallback(data){
		var result = data;
	  	if(result == '1'){
	  		initComment();
		  	autoReload();
	  	}else{
	  		return;
	  	}
	}
	
	//댓글 등록/삭제 후 댓글 수 다시 체크 (본문글 삭제시 댓글 수가 있으면 삭제 안됨)
	function commentCountCallback(data) {
		var result = data;
		
		document.f.pCommentCnt.value = result;
	}


	//-- 토론글 리스트로	
	function goList() {
		var param1 = "&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM;
		var param2 = "&pCourseId="+COURSE_ID+"&pForumId="+FORUM_ID+"&curPage="+CURPAGE+"&pGubun="+PGUBUN;
		document.location.href = CONTEXTPATH+"/PublishCurriSub.cmd?cmd=publishForumContentsPagingList&MENUNO=1&pMode=Home"+param1+param2;
	}
	
	//-- 답글쓰기 화면으로...
	function goReply(){
		var param1 = "&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM;
		var param2 = "&pCourseId="+COURSE_ID+"&pForumId="+FORUM_ID+"&pSeqNo="+SEQ_NO+"&curPage="+CURPAGE+"&pGubun="+PGUBUN;
		document.location.href = CONTEXTPATH+"/CourseForumContents.cmd?cmd=courseForumContentsWrite&MENUNO=1&pMode=Home&pRegMode=Reply"+param1+param2;
	}
	
	//-- 본문글 수정과 삭제 (로그인 한 사용자의 경우)
	function goContentsEdit(str) {

		var childCnt = document.f.pChildCnt.value;		//답변글
		var commCnt = document.f.pCommentCnt.value;		//댓글
		
		if(str == 'Edit') {
			var param1 = "&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM;
			var param2 = "&pCourseId="+COURSE_ID+"&pForumId="+FORUM_ID+"&pSeqNo="+SEQ_NO+"&curPage="+CURPAGE+"&pGubun="+PGUBUN;
			document.location.href = CONTEXTPATH+"/CourseForumContents.cmd?cmd=courseForumContentsEdit&MENUNO=1&pMode=Home"+param1+param2;
		}
		else if(str == 'Del') {
			if(childCnt == 0 && commCnt == 0) {
				if(confirm("삭제 하시겠습니까?")){
					CourseForumContentsWork.courseForumContentsDeleteAjax(CURRI_CODE, CURRI_YEAR, CURRI_TERM, COURSE_ID, FORUM_ID, SEQ_NO, forumDeleteCallback);
				} else {
					return;			
				}
			} else if(childCnt != 0) {
				alert('하위글이 있어 삭제할 수 없습니다.');
			} else if(commCnt != 0) {
				alert('댓글이 있어 삭제할 수 없습니다.');
			}			
		}	
	}
	
	function goCommentEdit(commNo) {
		if(confirm("댓글을 삭제 하시겠습니까?")){
			var f = document.Input;
			var seqNo = DWRUtil.getValue("pSeqNo");

			CourseForumCommentWork.publishForumCommentDelete(CURRI_CODE, CURRI_YEAR, CURRI_TERM, COURSE_ID, FORUM_ID, SEQ_NO, commNo, courseForumCommentDeleteCallback);
			CourseForumCommentWork.getForumCommentCount(CURRI_CODE, CURRI_YEAR, CURRI_TERM, COURSE_ID, FORUM_ID, SEQ_NO, commentCountCallback);
		}else
			return;
	}

	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}
	// 댓글리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var FORUM_ID = "";
	var COURSE_ID = "";
	var USER_ID = "";
	var USER_TYPE = "";
	var SEQ_NO = "";
	var COMMENT_USE_YN = "";

	// 초기화
	function init(systemCode,contextPath, courseId, forumId, seqNo, userId, userType, commentUseYn) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.COURSE_ID = courseId;
		this.FORUM_ID = forumId;
		this.SEQ_NO = seqNo;
		this.USER_ID = userId;
		this.USER_TYPE = userType;
		this.COMMENT_USE_YN = commentUseYn;

		autoReload();
	}

	// 페이징처리
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["Input"].elements["curPageComment"].value = page;
		autoReload();
	}

	// 폼체크
	function formCheck(){
		var f = document.Input;
		if(f.pRegName.value == ""){
			alert("이름을 입력하세요");
			new Effect.Highlight("pRegName");
			f.pRegName.focus();
			return false;
		}

		if(f.pContents.value == ""){
			alert("내용을 입력하세요");
			new Effect.Highlight("pContents");
			f.pContents.focus();
			return false;
		}
		return true;
	}

	// 댓글리스트 받아오기
	function autoReload(){
		if(COMMENT_USE_YN=="Y") initComment();
		var f = document.Input;
		var curPageComment = DWRUtil.getValue("curPageComment");
		
		CourseForumCommentWork.courseForumCommentPagingListAjax(curPageComment, COURSE_ID, FORUM_ID, SEQ_NO, autoReloadCallback);
	}

	// 댓글리스트 뿌려주기
	function autoReloadCallback(data){

	  	var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;
	  	var paggingStr = data.pagging;
		var rowLength = 0;
		
		if(dataList != null) rowLength = dataList.length;

	  	var commentListObj = $("commentList");
	  	var paggingObj = $("getPagging");
	  	var objStr = "<table width=\"100%\" align=\"center\"><tr><td class=\"reply_r_textform\">"
	  				+"<table border='0' width=\"650\" align=\"center\">";

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

				objStr += "<tr><td valign=\"top\" width=\"30\">"+emoticonImgSrc+"</td>"
					+ "<td width=\"100\"><font color=#9a7441>"+regName+"</font></td>"
					+ "<td>"+contents+"&nbsp;&nbsp;&nbsp;<img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_reply.gif\" align=\"absmiddle\">&nbsp;</td>"
					+ "<td width=\"100\" align=\"right\" style=\"padding-right:6px\">"+regDate+"</td>"
					+ "<td width=\"18\" align='right' valign='top'>";
					
				if(	regId == USER_ID || USER_TYPE == 'M' || USER_TYPE == 'P'  ){
				    objStr += "<a href=\"javascript:deleteComment('"+commNo+"','"+regId+"','"+chkCommRegPass+"');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/re_del.gif' alt='댓글삭제하기' width='9' height='9' align='absmiddle' border='0'></a>";
				}
				
				objStr += "</td>"
					+ "</tr>";
				
				if(i>0) objStr += "<tr><td colspan='5' class=\"s_tab03\" height=1></td></tr>";

			    totalCount--;
			}
			paggingObj.innerHTML = paggingStr;
   	  		paggingObj.style.display = "block";
		}
		objStr += "</table>";

		commentListObj.innerHTML = objStr;
		commentListObj.style.display = "block";
	}

	// 댓글 등록
	function addComment(){

		if(formCheck()){
			var f = document.Input;
			var contents = ajaxEnc(f.pContents.value);
			var emoticonNum = f.pEmoticon.value;

			CourseForumCommentWork.courseForumCommentRegist( COURSE_ID, FORUM_ID, SEQ_NO, contents, emoticonNum, courseForumCommentRegistCallback);
			
		}else return;
	}

	
	//댓글 등록후 호출..
	function courseForumCommentRegistCallback(data){
 		var result = data;
	  	if(result == '1'){
	  		initComment();
			document.Input.curPageComment.value = "1";
		  	autoReload();
	  	}else{
	  		return;
	  	}
	}
	
	// 댓글 삭제
	function deleteComment(commNo,commRegId,chkRegPass){

	   if(chkRegPass == 'F'){//-- 코멘트가 로그인후 등록된 글인경우
	       if(( USER_ID != commRegId) && ( USER_TYPE != 'M')){
				alert('자신이 등록한 답변만 삭제 가능합니다.');
		   }else{
		       if(confirm("댓글를 삭제 하시겠습니까?")){
				   var f = document.Input;
				   var seqNo = DWRUtil.getValue("pSeqNo");

				   CourseForumCommentWork.courseForumCommentDelete(COURSE_ID, FORUM_ID, SEQ_NO, commNo, courseForumCommentDeleteCallback);
			   }else
			       return;
			   }
	   }else
	       return;
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

	function initComment(){
		var f = document.Input;
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

	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}
	// ��۸���Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var FORUM_ID = "";
	var COURSE_ID = "";
	var USER_ID = "";
	var USER_TYPE = "";
	var SEQ_NO = "";
	var COMMENT_USE_YN = "";

	// �ʱ�ȭ
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

	// ����¡ó��
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["Input"].elements["curPageComment"].value = page;
		autoReload();
	}

	// ��üũ
	function formCheck(){
		var f = document.Input;
		if(f.pRegName.value == ""){
			alert("�̸��� �Է��ϼ���");
			new Effect.Highlight("pRegName");
			f.pRegName.focus();
			return false;
		}

		if(f.pContents.value == ""){
			alert("������ �Է��ϼ���");
			new Effect.Highlight("pContents");
			f.pContents.focus();
			return false;
		}
		return true;
	}

	// ��۸���Ʈ �޾ƿ���
	function autoReload(){
		if(COMMENT_USE_YN=="Y") initComment();
		var f = document.Input;
		var curPageComment = DWRUtil.getValue("curPageComment");
		
		CourseForumCommentWork.courseForumCommentPagingListAjax(curPageComment, COURSE_ID, FORUM_ID, SEQ_NO, autoReloadCallback);
	}

	// ��۸���Ʈ �ѷ��ֱ�
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
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">��ϵ� �ڸ�Ʈ�� �����ϴ�.</td></tr>";
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

		    	// �̸�Ƽ�� �̹���
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
				    objStr += "<a href=\"javascript:deleteComment('"+commNo+"','"+regId+"','"+chkCommRegPass+"');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/re_del.gif' alt='��ۻ����ϱ�' width='9' height='9' align='absmiddle' border='0'></a>";
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

	// ��� ���
	function addComment(){

		if(formCheck()){
			var f = document.Input;
			var contents = ajaxEnc(f.pContents.value);
			var emoticonNum = f.pEmoticon.value;

			CourseForumCommentWork.courseForumCommentRegist( COURSE_ID, FORUM_ID, SEQ_NO, contents, emoticonNum, courseForumCommentRegistCallback);
			
		}else return;
	}

	
	//��� ����� ȣ��..
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
	
	// ��� ����
	function deleteComment(commNo,commRegId,chkRegPass){

	   if(chkRegPass == 'F'){//-- �ڸ�Ʈ�� �α����� ��ϵ� ���ΰ��
	       if(( USER_ID != commRegId) && ( USER_TYPE != 'M')){
				alert('�ڽ��� ����� �亯�� ���� �����մϴ�.');
		   }else{
		       if(confirm("��۸� ���� �Ͻðڽ��ϱ�?")){
				   var f = document.Input;
				   var seqNo = DWRUtil.getValue("pSeqNo");

				   CourseForumCommentWork.courseForumCommentDelete(COURSE_ID, FORUM_ID, SEQ_NO, commNo, courseForumCommentDeleteCallback);
			   }else
			       return;
			   }
	   }else
	       return;
	}
	


	//��� ������ ȣ��..
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

		//���õ� �̸�Ƽ��â ����
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

	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}
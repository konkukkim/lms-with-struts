	// ��۸���Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var BBS_TYPE = "";
	var COURSE_ID = "";
	var SEQ_NO = "";

	// �ʱ�ȭ
	function init(systemCode,contextPath,bbsType,courseId,seqNo) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.BBS_TYPE = bbsType;
		this.COURSE_ID = courseId;
		this.SEQ_NO = seqNo;

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
		initComment();
		var f = document.Input;
		var curPageComment = f.curPageComment.value;

		CurriBbsCommentWork.curriBbsCommentList(curPageComment, BBS_TYPE, SEQ_NO, autoReloadCallback);
		CurriBbsCommentWork.curriBbsCommentCount(BBS_TYPE, SEQ_NO, commentCallback);
	}

	// ��۸���Ʈ �ѷ��ֱ�
	function autoReloadCallback(data){

	  	var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;
	  	var paggingStr = data.pagging;
		var rowLength = 0;
		if(dataList != null)
			rowLength = dataList.length;

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
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">��ϵ� �ڸ�Ʈ�� �����ϴ�.</td></tr>";
	  	}else{

		  	for(i=0;i<rowLength;i++){
				var curriBbsCommentsDTO = dataList[i];

		    	var commNo = curriBbsCommentsDTO.commNo;
		    	var contents = curriBbsCommentsDTO.contents;
		    	var emoticonNum = curriBbsCommentsDTO.emoticonNum;
		    	var regId = curriBbsCommentsDTO.regId;
		    	var regName = curriBbsCommentsDTO.regName;
		    	var regDate = curriBbsCommentsDTO.regDate;

		    	var emoticonImgSrc = "";
		    	var chkCommRegPass = "F";

		    	// �̸�Ƽ�� �̹���
		    	if(emoticonNum !=""){
		    		if(emoticonNum != "0")
			    		emoticonImgSrc = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_0"+emoticonNum+".gif'  border='0' align='absmiddle'>";
			    	else
			    		emoticonImgSrc="";
		    	}

				objStr += "<tr><td  width=\"30\">"+emoticonImgSrc+"</td>"
					+ "<td width=\"60\"><font color=#9a7441>"+regName+"</font></td>"
					+ "<td>"+contents+"&nbsp;&nbsp;&nbsp;<img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_reply.gif\" align=\"absmiddle\">&nbsp;</td>"
					+ "<td width=\"65\" align=\"right\" style=\"padding-right:6px\">"+regDate+"</td>"
					+ "<td width=\"18\" align='right' valign='middle'><a href=\"javascript:deleteComment('"+commNo+"','"+regId+"','"+chkCommRegPass+"');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/re_del.gif' alt='��ۻ����ϱ�' width='9' height='9' align='absmiddle' border='0'></a></td>"
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

	// �ڸ�Ʈ ����
	function commentCallback(data) {
		var commCnt	=	data;

		var f = document.Input;
		f.commentCount.value = commCnt;
	}

	// ��� ���
	function addComment(){

		if(formCheck()){
			var f = document.Input;
			var contents = ajaxEnc(f.pContents.value);
			var regName = ajaxEnc(f.pRegName.value);
			var emoticonNum = f.pEmoticon.value;

			CurriBbsCommentWork.curriBbsCommentRegist(BBS_TYPE, SEQ_NO, regName, contents, emoticonNum, curriBbsCommentRegistCallback);
		}else return;
	}

	//��� ���
	function curriBbsCommentRegistCallback(data){
 		var result = data;
	  	if(result == '1'){
	  		initComment();
			document.Input.curPageComment.value = "1";
		  	autoReload();
	  	}else{
	  		return;
	  	}
	}

	//��� ����
	function curriBbsCommentDeleteCallback(data){
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

//		  	var objStr = "<table width=40 border='0' cellpadding='3' cellspacing='1' bgcolor='C0C0C0'>"
//		  		+"<tr><td bgcolor='ffffff' align='center'> "
//		  		+"<table width='100%' border='0' cellpadding='3' cellspacing='0'><tr align='center'> "
//		  		+"<td><a href=\"javascript:selectEmoticon('0')\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/emoticon/"+emoticonNum+".gif' border='0' align='absmiddle'></a></td> "
//		  		+"</tr></table></td></tr></table> ";

			var objStr = "<a href=\"javascript:selectEmoticon('0')\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/emoticon/"+emoticonNum+".gif' border='0' align='absmiddle'></a>";

			emoticonObj.innerHTML = objStr;
			emoticonObj.style.display = "block";
		}
		else{
			emoticonObj.style.display = "none";
		}
	}

	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}
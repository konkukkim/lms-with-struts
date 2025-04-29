	//Ŀ�´�Ƽ �ڸ�Ʈ
	var SYSTEMCODE = "1";
	var CONTEXTPATH = "";
	var COMMID = "";
	var BBSID = 0;
	var SEQNO = 0;
	var USERID = "";
	var USERTYPE = "";

	//�ʱ�ȭ
	function init(systemcode, contextpath, commid, bbsid, seqno) {
		SYSTEMCODE = systemcode;
		CONTEXTPATH = contextpath;
		COMMID = commid;
		BBSID = bbsid;
		SEQNO = seqno;

		var f = document.Input;
		USERID = f.userId.value;
		USERTYPE = f.userType.value;

		autoReload();
	}

	function initComment(){
		var f = document.Input;
		f.pContents.value = "";
		f.pEmoticon.value = "0";

		//���õ� �̸�Ƽ��â ����
		$("selectedEmoticon").style.display = "none";
	}

	//�̸�Ƽ�� ����
	function selectEmoticon(emoticonNum){
		var f = document.Input;
		f.pEmoticon.value = emoticonNum;

	  	var emoticonObj = document.getElementById("selectedEmoticon");
		if(emoticonNum != 0 ){

		  	var objStr = "<table width=40 border='0' cellpadding='3' cellspacing='1' bgcolor='C0C0C0'>"
		  		+"<tr><td bgcolor='ffffff' align='center'> "
		  		+"<table width='100%' border='0' cellpadding='3' cellspacing='0'><tr align='center'> "
		  		+"<td><a href=\"javascript:selectEmoticon('0')\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/emoticon/"+emoticonNum+".gif' border='0' align='absmiddle'></a></td> "
		  		+"</tr></table></td></tr></table> ";

			var objStr = "<a href=\"javascript:selectEmoticon('0')\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/emoticon/"+emoticonNum+".gif' border='0' align='absmiddle'></a>";

			emoticonObj.innerHTML = objStr;
			emoticonObj.style.display = "block";
		}
		else{
			emoticonObj.style.display = "none";
		}
	}

	// ��۸���Ʈ �޾ƿ���
	function autoReload(){
		initComment();
		var f = document.Input;
		var curPageComment = f.curPageComment.value;

		CommBbsCommentWork.commBbsCommentList(curPageComment, BBSID, SEQNO, autoReloadCallback);
		CommBbsCommentWork.commBbsCommentCount(BBSID, SEQNO, CommentCountCallback);
	}

	//��� ����Ʈ �ѷ��ֱ�
	function autoReloadCallback(data) {
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
	  	} else {
			for(i=0;i<rowLength;i++) {
				var commBbsDto	=	dataList[i];

				var commNo = commBbsDto.commNo;
		    	var contents = commBbsDto.contents;
		    	var emoticonNum = commBbsDto.emoticonNum;
		    	var regId = commBbsDto.regId;
		    	var regName = commBbsDto.regName;
		    	var regDate = commBbsDto.regDate;
		    	regDate = regDate.substring(0, 4)+"."+regDate.substring(4, 6)+"."+regDate.substring(6, 8);

		    	var emoticonImgSrc = "";
		    	var chkCommRegPass = "F";

		    	// �̸�Ƽ�� �̹���
		    	if(emoticonNum !=""){
		    		if(emoticonNum != "0")
			    		emoticonImgSrc = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_0"+emoticonNum+".gif'  border='0' align='absmiddle'>";
			    	else
			    		emoticonImgSrc="";
		    	}

				objStr += "<tr><td width=\"30\">"+emoticonImgSrc+"</td>"
					+ "<td width=\"60\"><font color=#9a7441>"+regName+"</font></td>"
					+ "<td>"+contents+"&nbsp;&nbsp;&nbsp;<img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_reply.gif\" align=\"absmiddle\">&nbsp;</td>"
					+ "<td width=\"65\" align=\"right\" style=\"padding-right:6px\">"+regDate+"</td>"
					+ "<td width=\"18\" align='right' valign='middle'><a href=\"javascript:deleteComment('"+commNo+"','"+regId+"');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/re_del.gif' alt='��ۻ����ϱ�' width='9' height='9' align='absmiddle' border='0'></a></td>"
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

	function CommentCountCallback(data) {
		var commCnt	=	data;

		var f = document.Input;
		f.pCommCnt.value = commCnt;
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
		if(!validate(Input)) return false;
		else return true;
	}

	// ��� ���
	function addComment(){

		if(formCheck()){
			var f = document.Input;
			var contents = ajaxEnc(f.pContents.value);

			var regName = ajaxEnc(f.pRegName.value);
			var emoticonNum = f.pEmoticon.value;

			CommBbsCommentWork.commBbsCommentRegist(BBSID, SEQNO, regName, contents, emoticonNum, curriBbsCommentRegistCallback);
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
	function deleteComment(commNo, regId) {

		//if(chkRegPass == 'F'){//-- �ڸ�Ʈ�� �α����� ��ϵ� ���ΰ��
			if((USERID != regId) && (USERTYPE != 'M')){
				alert('�ڽ��� ����� �亯�� ���� �����մϴ�.');
			} else {
				if(confirm("��۸� ���� �Ͻðڽ��ϱ�?")){
					var f = document.Input;
					var bbsType = f.pBbstype.value;
					var seqNo = f.pSeqNo.value;

					CommBbsCommentWork.commBbsCommentDelete(BBSID, SEQNO, commNo, curriBbsCommentDeleteCallback);
				} else {
					return;
				}
			}
		//} else {
		//	return;
		//}
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

	// ����¡ó��
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["Input"].elements["curPageComment"].value = page;
		autoReload();
	}

	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}
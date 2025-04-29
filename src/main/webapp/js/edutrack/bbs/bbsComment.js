	var SYSTEMCODE = "1";
	var CONTEXTPATH = "";
	var BBSID = 0;
	var SEQNO = 0;
	var USERID = "";
	var USERTYPE = "";
	var COMMENTUSEYN = "";

	//�ʱ�ȭ
	function init(systemcode, contextpath, bbsid, seqno, commentuseyn) {
		SYSTEMCODE = systemcode;
		CONTEXTPATH = contextpath;
		BBSID = bbsid;
		SEQNO = seqno;
		COMMENTUSEYN = commentuseyn;

		var f = document.Input;

		if(COMMENTUSEYN=="Y"){
		    USERID = f.userId.value;
		    USERTYPE = f.userType.value;

		    autoReload();
		}
		
		//������
		if(bbsid == '2') {
			UccViewInit();
		}
	}

	// ����¡ó��
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["Input"].elements["curPageComment"].value = page;
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

	// ��۸���Ʈ �޾ƿ���
	function autoReload(){
		initComment();

		var f = document.Input;
		var curPageComment = f.curPageComment.value;

		BbsCommentWork.bbsCommentAutoList(curPageComment, BBSID, SEQNO, autoReloadCallback);
		BbsCommentWork.getCommentCount(BBSID, SEQNO, commentCountCallback);
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
		    	var regPass	=	commBbsDto.regPasswd;
		    	var regDate = commBbsDto.regDate;

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
					+ "<td width=\"18\" align='right' valign='middle'><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/re_del.gif' alt='��ۻ����ϱ�' width='9' height='9' align='absmiddle' border='0' onClick=\"goDeleteComment('"+commNo+"','"+regId+"', '"+regPass+"')\" style='cursor:hand'></td>"
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

	function commentCountCallback(data) {
		var commCnt	=	data;

		var f = document.Input;
		document.forms["Input"].elements["commentCount"].value	=	commCnt;

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
		
		if(USERID == "") {
			if(f.pRegPass.value == ""){
				alert("��й�ȣ�� �Է��ϼ���");
				new Effect.Highlight("pRegPass");
				f.pRegPass.focus();
				return false;
			}
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
			var regpass = "";
			if(USERID == "") {
				regpass = ajaxEnc(f.pRegPass.value);
			}
			var emoticonNum = f.pEmoticon.value;

			BbsCommentWork.bbsCommentRegist(BBSID, SEQNO, regName, regpass, contents, emoticonNum, curriBbsCommentRegistCallback);
			
			if(USERID == ""){
				f.pRegName.value	=	"";
				f.pRegPass.value	=	"";
			}
			f.pContents.value	=	"";
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

	// ��� ����
	function goDeleteComment(commNo, regId, regPass) {
		var f = document.Input;
		
		if(USERID == '' || USERID == null) {
			if($("regPassDiv").style.display == "block") {
				$("regPassDiv").style.display = "none";
				f.OriginalPasswd.value = "";
			} else {
				$("regPassDiv").style.display = "block";
				f.OriginalPasswd.value = "";
			}
			f.OriginalPasswd.value = regPass;
			f.CommentNum.value = commNo;
		} else {
			if((USERID != regId) && (USERTYPE != 'M')){
				alert('�ڽ��� ����� �亯�� ���� �����մϴ�.');
			} else {
				if(confirm("��۸� ���� �Ͻðڽ��ϱ�?")){
					BbsCommentWork.bbsCommentDelete(BBSID, SEQNO, commNo, bbsCommentDeleteCallback);
				} else {
					return;
				}
			}			
		}
	}

	//�α��� ���� �ۼ��� ��� ����
	function deleteComment() {
		$("regPassDiv").style.display = "none";
		
		var f = document.Input;			
		var confirmPass = ajaxEnc(f.ConfirmPasswd.value);
		
		if(f.ConfirmPasswd.value == ""){
			alert("��й�ȣ�� �Է��ϼ���");
			new Effect.Highlight("ConfirmPasswd");
			f.ConfirmPasswd.focus();
			return false;
		}
		
		var regPass	= ajaxEnc(f.OriginalPasswd.value);
		var commNo = ajaxEnc(f.CommentNum.value);
		var bbsid = ajaxEnc(f.pBbsId.value);
		var seqno = ajaxEnc(f.pSeqNo.value);
		
		f.OriginalPasswd.value = "";
		f.ConfirmPasswd.value = "";

		if(confirmPass == regPass) {
			if(confirm("��۸� ���� �Ͻðڽ��ϱ�?")){
				BbsCommentWork.bbsCommentDelete(bbsid, seqno, commNo, bbsCommentDeleteCallback);
			} else {
				return;
			}
		} else {
			alert('��й�ȣ�� Ʋ���ϴ�.');
			f.ConfirmPasswd.focus();
			return false;	
		}
	}

	//��� ����
	function bbsCommentDeleteCallback(data){
		var result = data;
	  	if(result == '1'){
	  		initComment();
		  	autoReload();
	  	}else{
	  		return;
	  	}
	}
	
	//-- ������ �÷��̾� ���̱�
	function UccViewInit() {
		var uccCnt = 	DWRUtil.getValue("uccCnt");
		var uccStr	=	"";
		
		if(uccCnt != 0) {
			var	uccServerIp		=	DWRUtil.getValue("pUccIp");
			var uccServerAlias	=	DWRUtil.getValue("pUccAlias");
			var uccServerProtocol = DWRUtil.getValue("pUccProtocol");			
			
			var urlPath		=	uccServerProtocol+"://"+uccServerIp+uccServerAlias+"/";
			
			for(i = 1; i <= uccCnt; i++) {
				var uccStr		=	"";
				var	uccObj		=	$("UccViewDiv"+i);
				var uccFileName	=	DWRUtil.getValue("uccFileName0"+i);
				
				if(uccFileName != "") {
					
					//alert(urlPath+uccFileName);
					
					/*
					uccStr	= "<embed src=\""+urlPath+uccFileName+"\" "
							+ " width='340' height='340' "
							+ " showcontrols=true showstatusbar=true autostart=true enablecontextmenu=false "
							+ " style=\"filter:xray()\">";	
					*/
					
					uccStr	= '<EMBED invokeURLs="false" autostart="true" AllowScriptAccess="never" src="'+urlPath+uccFileName+'" type=video/x-ms-asf>';
				}
				uccObj.innerHTML	=	uccStr;
				uccObj.style.display = "block";
			}
		}
	}


	//-- �α��� ���� ���� ����/���� �� ��
	function enterPasswd(mode) {
		var f = document.contentsPasswdForm;
		if($("ContentsRegPassDiv").style.display == "block") {
			$("ContentsRegPassDiv").style.display = "none";
		} else {
			$("ContentsRegPassDiv").style.display = "block";
		}
		f.pPageMode.value = mode;
	}
	
	function confirmContentsPasswd() {
		var f = document.contentsPasswdForm;
		var bbsid = ajaxEnc(f.pBbsId.value);
		var seqno = ajaxEnc(f.pSeqNo.value);
		var pass = ajaxEnc(f.ContentsConfirmPasswd.value);
		
		f.ContentsConfirmPasswd.value = "";
		$("ContentsRegPassDiv").style.display = "none";
		
		BbsContentsListWork.confirmContentsPasswd(bbsid, seqno, pass, confirmContentsPasswdCallback);
	}
	
	function confirmContentsPasswdCallback(data) {
		var retVal	=	data;
		var f		= 	document.contentsPasswdForm;
		var bbsid 	= 	ajaxEnc(f.pBbsId.value);
		var seqno 	= 	ajaxEnc(f.pSeqNo.value);
		var curPage	=	ajaxEnc(f.curPage.value);
		var mode 	= 	f.pPageMode.value;
		
		if(data == "1") {
			if(mode == 'Edit') {
				document.location.href = CONTEXTPATH+"/BbsContents.cmd?cmd=bbsContentsEdit&pMode=Home&MENUNO=0&pBbsId="+bbsid+"&MainMenu=Y&pSec=&pSeqNo="+seqno+"&curPage="+curPage;
			} else {
				var stat = confirm("�Խñ��� �����Ͻðڽ��ϱ�?");
				if (stat == true)
					document.location.href = CONTEXTPATH+"/BbsContents.cmd?cmd=bbsContentsDelete&pMode=Home&MENUNO=0&pBbsId="+bbsid+"&MainMenu=Y&pSec=&pSeqNo="+seqno+"&curPage="+curPage;
			}
		} else {
			alert("��й�ȣ�� Ʋ���ϴ�!");
			return false;
		}
	
	}


	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}

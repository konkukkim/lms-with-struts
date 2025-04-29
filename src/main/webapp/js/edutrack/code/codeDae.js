	// ���񸮽�Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";

	function init(systemCode,contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;

		autoReload();
	}

	// ����¡ó��
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
  		codeDaeWrite('none');
		autoReload();
	}

	// ��üũ
	function formCheck(){
		var f = document.f;
		if(f.pCodeDae.value == ""){
			alert("���ڵ带 �Է��ϼ���");
			new Effect.Highlight("pCodeDae");
			f.pCodeDae.focus();
			return false;
		}
		if(f.pDaeName.value == ""){
			alert("���ڵ���� �Է��ϼ���");
			new Effect.Highlight("pDaeName");
			f.pDaeName.focus();
			return false;
		}
		return true;
	}

	function codeDaeWrite(value) {
		var f = document.f;
		f.pCodeDae.value="";
		f.pDaeName.value="";
		f.pComment.value="";
		f.pCodeDae.readOnly = false;
		f.pCodeDae.style.background = "#FFFFFF";

		$("codeDaeWrite").style.display = value;
		$("regButton").style.display = "block";
		$("modButton").style.display = "none";
	}

	// sorting
	function sorting(pos,col,order) {
		var f = document.f;
		f.pCol.value=col;
		f.pOrder.value=order;
		autoReload();

		var obj = document.getElementById('listTitle').getElementsByTagName('a');
		var len = obj.length;

		for(i=1;i<=len;i++){
			var objSort = document.getElementById("sort_"+i);
			if("sort_"+pos == "sort_"+i){
				if(i%2==0)	objSort.innerHTML = "��";
				else	objSort.innerHTML = "��";

				new Effect.Highlight("sort_"+i);
			}else{
				if(i%2==0)	objSort.innerHTML = "��";
				else	objSort.innerHTML = "��";
			}
		}
	}


	// code_dae �ߺ�üũ start
	function checkCodeDae(){

		var f = document.f;
		var codeDae = f.pCodeDae.value;

		if(codeDae !=null && codeDae !=''){
			CodeDaeWork.codeDaeCheck(codeDae,{
				callback:function(data) {

					var codeDaeCnt = data;
				  	if(codeDaeCnt != '0'){
					  	viewMessage();
				  	}else{
				  		return;
				  	}
				}
			});
		}else{
			return;
		}
	}

	function viewMessage() {
		var f = document.f;
	    var codeDae = f.pCodeDae.value;
		alert("'"+codeDae+"'�� ������� ���ڵ��Դϴ�.\n�ٸ� ���ڵ带 �Է��� �ּ���");
		f.pCodeDae.value ="";
		new Effect.Highlight("pCodeDae");
		f.pCodeDae.focus();
	}
	// code_dae �ߺ�üũ end

	//���ڵ� ���� �������� start
	function getCodeDaeInfo(codeDae){
	    if(codeDae !=null && codeDae !=''){
			CodeDaeWork.codeDaeInfo(codeDae,getCodeDaeInfoCallback);
		}else{
			alert("���ڵ尡 �����ϴ�.");
			return;
		}
	}

	// ���ڵ� ������ �ۼ�
	function getCodeDaeInfoCallback(data) {
		var f = document.f;
		f.pCodeDae.value="";
		f.pDaeName.value="";
		f.pComment.value="";
		f.pCodeSoCnt.value="0";

		var codeDae =  data.codeDae;
		var daeName =  data.daeName;
		var useYn =  data.useYn;
		var comment =  data.xcomment;
		var codeSoCnt =  data.codeSoCnt;

	  	if(codeDae != null && codeDae != ''){
		    f.pCodeDae.value = codeDae;
		    f.pDaeName.value = daeName;
		    f.pComment.value = comment;
		    f.pCodeSoCnt.value = codeSoCnt;

		   for(i=0;i<f.pUseYn.length;i++){
	   	   	   if(useYn == f.pUseYn[i].value){
		   		   f.pUseYn[i].checked=true;
		   	   }
		   }

			$("codeDaeWrite").style.display = "block";

			f.pCodeDae.readOnly = true;
			f.pCodeDae.style.background = "#F8F6F3";

			$("regButton").style.display = "none";
			$("modButton").style.display = "block";

	  	}else{
	  		return;
	  	}
	}
	//���ڵ� ���� �������� end

	// ���ڵ帮��Ʈ �޾ƿ���
	function autoReload(){
		var f = document.f;
		CodeDaeWork.codeDaeListAuto(f.curPage.value, f.pCol.value, f.pOrder.value, autoReloadCallback);
	}


	// ���ڵ帮��Ʈ �ѷ��ֱ�
	function autoReloadCallback(data){

	  	var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;
	  	var paggingStr = data.pagging;
		var rowLength = 0;
		var No = 0;
		if(dataList != null)
			rowLength = dataList.length;

	  	var codeDaeListObj = $("codeDaeList");
	  	var paggingObj = $("getPagging");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

		if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">��ϵ� �ý��� ���ڵ尡 �����ϴ�.</td></tr>";
	  	}else{
		  	for(i=0;i<rowLength;i++){
				var codeDaeDTO = dataList[i];
				No++;

		    	var codeDaeName = codeDaeDTO.daeName;
		    	var codeDae = codeDaeDTO.codeDae;
		    	var useName = codeDaeDTO.useName;
		    	var regDate = codeDaeDTO.regDate;

				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
					+ "<td width='40' class=\"s_tab04_cen\">"+No+"</td>"
					+ "<td></td><td width='110' class=\"s_tab04_cen\">"+codeDae+"</td>"
					+ "<td></td><td width='215' class=\"s_tab04_cen\"><a href='"+CONTEXTPATH+"/CodeSo.cmd?cmd=codeSoList&pMode=MyPage&pCodeDae="+codeDae+"'>"+codeDaeName+"</a></td>"
					+ "<td></td><td width='110' class=\"s_tab04_cen\">"+useName+"</td>"
					+ "<td></td><td width='110' class=\"s_tab04_cen\">"+regDate+"</td>"
					+ "<td></td><td width='80' class=\"s_tab04_cen\"><a href=\"javascript:getCodeDaeInfo('"+codeDae+"')\"><b>[����/����]</b></a></td>"
					+ "</tr>";

				if(totalCount > 1)	objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    totalCount--;
			}
		}
		objStr += "</table>";
		codeDaeListObj.innerHTML = objStr;
		codeDaeListObj.style.display = "block";
	}

	// ���ڵ� ���
	function manageCodeDae(mode){
	   if(mode == "Add"){		// ��Ͻ�
	   	   if(formCheck()){
			   var f = document.f;
			   var codeDae = f.pCodeDae.value;
			   var daeName = ajaxEnc(f.pDaeName.value);
			   var comment = ajaxEnc(f.pComment.value);
			   var useYn = "Y";

			   for(i=0;i<f.pUseYn.length;i++){
		   	   	   if(f.pUseYn[i].checked == true){
			   		   useYn = f.pUseYn[i].value;
			   	   }
			   }

			   CodeDaeWork.codeDaeRegist(mode, codeDae, daeName, comment, useYn, addCodeDaeCallback);
		   }else return;
	    }else if(mode == "Edit" || mode == "Delete"){	// ���� or ������
	    	var f = document.f;
			var codeSoCnt = f.pCodeSoCnt.value;

		    if(mode == "Delete" && codeSoCnt > 0){		// ������ �������ڵ� �ִ��� ���� üũ
		    	alert("��ϵ� ���ڵ尡 �ֽ��ϴ�.\n���ڵ带 ���� ���� �ϼ���!!!");
		    	return;
		    }else{
		   	   if(formCheck()){
			       var flagStr ="";
			       if(mode == "Edit")
			       	   flagStr = "���� �Ͻðڽ��ϱ�?";
			       else
			       	   flagStr = "���ڵ带 ���� �� ��� �ý��� ���ݿ� ������ ��ĥ �� �ֽ��ϴ�.\n���� �Ͻðڽ��ϱ�?";

			       var flag = confirm(flagStr);

			       if(flag == true){
					   var codeDae = f.pCodeDae.value;
					   var daeName = ajaxEnc(f.pDaeName.value);
					   var comment = ajaxEnc(f.pComment.value);
					   var useYn = "Y";

					   for(i=0;i<f.pUseYn.length;i++){
				   	   	   if(f.pUseYn[i].checked == true){
					   		   useYn = f.pUseYn[i].value;
					   	   }
					   }

					   CodeDaeWork.codeDaeRegist(mode, codeDae, daeName, comment, useYn, addCodeDaeCallback);
				    }else
				    	return;
			    }else return;
			}
	    }else{
	    	return;
	    }
	}

	//���ڵ� ���
	function addCodeDaeCallback(data){
	  	var result = data;
	  	if(result == '1'){
	  		codeDaeWrite('none');
		  	autoReload();
	  	}else{
	  		return;
	  	}
	}

	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}

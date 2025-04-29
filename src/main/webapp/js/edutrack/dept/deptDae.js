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
  		deptDaeWrite('none');
		autoReload();
	}

	// ��üũ
	function formCheck(){
		var f = document.f;
		if(trim(f.pDeptDaecode.value) == ""){
			alert("�Ҽ��ڵ带 �Է��ϼ���");
			new Effect.Highlight("pDeptDaecode");
			f.pDeptDaecode.focus();
			return false;
		}
		if(trim(f.pDeptDaename.value) == ""){
			alert("�Ҽ��ڵ���� �Է��ϼ���");
			new Effect.Highlight("pDeptDaename");
			f.pDeptDaename.focus();
			return false;
		}
		return true;
	}

	function deptDaeWrite(value) {
		var f = document.f;
		f.pDeptDaecode.value="";
		f.pDeptDaename.value="";
		f.pDeptDaecode.readOnly = false;
		f.pDeptDaecode.style.background = "#FFFFFF";

		$("deptDaeWriteDiv").style.display = value;
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


	// dept_daecode �ߺ�üũ start
	function checkDeptDaecode(){

		var f = document.f;
		var deptDaecode = f.pDeptDaecode.value;

		if(deptDaecode !=null && deptDaecode !=''){
			DeptDaeWork.deptDaeCheck(deptDaecode,{
				callback:function(data) {

					var deptDaeCnt = data;
				  	if(deptDaeCnt != '0'){
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
	    var deptDaecode = f.pDeptDaecode.value;
		alert("'"+deptDaecode+"'�� ������� �Ҽ��ڵ��Դϴ�.\n�ٸ� �Ҽ��ڵ带 �Է��� �ּ���");
		f.pDeptDaecode.value ="";
		new Effect.Highlight("pDeptDaecode");
		f.pDeptDaecode.focus();
	}
	// code_dae �ߺ�üũ end

	//�Ҽ��ڵ� ���� �������� start
	function getDeptDaeInfo(vGubun, deptDaecode){
		
		if(vGubun=="U" && u_right!="true"){ 
	    	alert("���� ������ �����ϴ�. \n\n�����ڿ��� ���� �ϼ���");
	    	return;
	    }
	    
	    if(vGubun=="D" && d_right!="true"){ 
	    	alert("���� ������ �����ϴ�. \n\n�����ڿ��� ���� �ϼ���");
	    	return;
	    }	     
	    if(deptDaecode !=null && deptDaecode !=''){
			DeptDaeWork.deptDaeInfo(deptDaecode,getDeptDaeInfoCallback);
		}else{
			alert("�Ҽ��ڵ尡 �����ϴ�.");
			return;
		}
	}

	// �Ҽ��ڵ� ������ �ۼ�
	function getDeptDaeInfoCallback(data) {
		var f = document.f;
		f.pDeptDaecode.value="";
		f.pDeptDaename.value="";
		f.pDeptSoCnt.value="0";

		var deptDaecode =  data.deptDaecode;
		var deptDaename =  data.deptDaename;
		var useYn =  data.useYn;
		var deptSoCnt =  data.deptSoCnt;

	  	if(deptDaecode != null && deptDaecode != ''){
		    f.pDeptDaecode.value = deptDaecode;
		    f.pDeptDaename.value = deptDaename;
		    f.pDeptSoCnt.value = deptSoCnt;

		   for(i=0;i<f.pUseYn.length;i++){
	   	   	   if(useYn == f.pUseYn[i].value){
		   		   f.pUseYn[i].checked=true;
		   	   }
		   }

			$("deptDaeWriteDiv").style.display = "block";

			f.pDeptDaecode.readOnly = true;
			f.pDeptDaecode.style.background = "#F8F6F3";

			$("regButton").style.display = "none";
			$("modButton").style.display = "block";

	  	}else{
	  		return;
	  	}
	}
	//�Ҽ��ڵ� ���� �������� end

	// �Ҽ��ڵ帮��Ʈ �޾ƿ���
	function autoReload(){
		var f = document.f;
		DeptDaeWork.deptDaeListAuto(f.curPage.value, f.pCol.value, f.pOrder.value, autoReloadCallback);
	}


	// �Ҽ��ڵ帮��Ʈ �ѷ��ֱ�
	function autoReloadCallback(data){

	  	var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;
	  	var paggingStr = data.pagging;
		var rowLength = 0;
		var No = 0;
		if(dataList != null)
			rowLength = dataList.length;

	  	var deptDaeListObj = $("deptDaeList");
	  	var paggingObj = $("getPagging");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

		if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">��ϵ� �ý��� �ڵ尡 �����ϴ�.</td></tr>";
	  	}else{
		  	for(i=0;i<rowLength;i++){
				var deptDaeCodeDTO = dataList[i];
				No++;

		    	var deptDaeName = deptDaeCodeDTO.deptDaename;
		    	var deptDaecode = deptDaeCodeDTO.deptDaecode;
		    	var useName = deptDaeCodeDTO.useName;
		    	var regDate = deptDaeCodeDTO.regDate;

				objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
					+ "<td width='40' class=\"s_tab04_cen\">"+No+"</td>"
					+ "<td></td><td width='110' class=\"s_tab04_cen\">"+deptDaecode+"</td>"
					+ "<td></td><td width='215' class=\"s_tab04_cen\"><a href='"+CONTEXTPATH+"/DeptSo.cmd?cmd=deptSoList&pMode=MyPage&pDeptDaecode="+deptDaecode+"'>"+deptDaeName+"</a></td>"
					+ "<td></td><td width='110' class=\"s_tab04_cen\">"+useName+"</td>"
					+ "<td></td><td width='110' class=\"s_tab04_cen\">"+regDate+"</td>"
					+ "<td></td><td width='80' class=\"s_tab04_cen\"><b>["
					+ "<a href=\"javascript:getDeptDaeInfo('U','"+deptDaecode+"')\">����</a>/"
					+ "<a href=\"javascript:getDeptDaeInfo('D','"+deptDaecode+"')\">����</a>"
					+ "]</b></td>"
					+ "</tr>";

				if(totalCount > 1)	objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    totalCount--;
			}
		}
		objStr += "</table>";
		deptDaeListObj.innerHTML = objStr;
		deptDaeListObj.style.display = "block";
	}

	// �Ҽ��ڵ� ���
	function manageDeptDae(mode){
	   if(mode == "Add"){		// ��Ͻ�
	   	   if(formCheck()){
			   var f = document.f;
			   var deptDaecode = trim(f.pDeptDaecode.value);
			   var deptDaename = ajaxEnc(trim(f.pDeptDaename.value));
			   var useYn = "Y";

			   for(i=0;i<f.pUseYn.length;i++){
		   	   	   if(f.pUseYn[i].checked == true){
			   		   useYn = f.pUseYn[i].value;
			   	   }
			   }

			   DeptDaeWork.deptDaeRegist(mode, deptDaecode, deptDaename, useYn, addDeptDaeCallback);
		   }else return;
	    }else if(mode == "Edit" || mode == "Delete"){	// ���� or ������
	    	var f = document.f;
			var deptSoCnt = f.pDeptSoCnt.value;

		    if(mode == "Delete" && deptSoCnt > 0){		// ������ �������ڵ� �ִ��� ���� üũ
		    	alert("��ϵ� ���ڵ尡 �ֽ��ϴ�.\n���ڵ带 ���� ���� �ϼ���!!!");
		    	return;
		    }else{
		   	   if(formCheck()){
			       var flagStr ="";
			       if(mode == "Edit")
			       	   flagStr = "���� �Ͻðڽ��ϱ�?";
			       else
			       	   flagStr = "�Ҽ��ڵ带 ���� �� ��� �ý��� ���ݿ� ������ ��ĥ �� �ֽ��ϴ�.\n���� �Ͻðڽ��ϱ�?";

			       var flag = confirm(flagStr);

			       if(flag == true){
					   var deptDaecode = trim(f.pDeptDaecode.value);
					   var deptDaename = ajaxEnc(trim(f.pDeptDaename.value));
					   var useYn = "Y";

					   for(i=0;i<f.pUseYn.length;i++){
				   	   	   if(f.pUseYn[i].checked == true){
					   		   useYn = f.pUseYn[i].value;
					   	   }
					   }

					   DeptDaeWork.deptDaeRegist(mode, deptDaecode, deptDaename, useYn, addDeptDaeCallback);
				    }else
				    	return;
			    }else return;
			}
	    }else{
	    	return;
	    }
	}

	//�Ҽ��ڵ� ���
	function addDeptDaeCallback(data){
	  	var result = data;
	  	if(result == '1'){
	  		deptDaeWrite('none');
		  	autoReload();
	  	}else{
	  		return;
	  	}
	}

	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}

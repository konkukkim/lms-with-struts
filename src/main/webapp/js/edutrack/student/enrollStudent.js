	// ���񸮽�Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";

	function init(systemCode,contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;

		autoReload();
	}

	function errorMessage(){
		alert("�۾��� ������ �߻��߽��ϴ�.\n��õ� ���ּ���!!!");
	}


	// ����¡ó��
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
		autoReload();
	}

	// ����������Ʈ �޾ƿ���
	function changeCurriSub(){
		document.f.pSWord.value = "";	// �˻��� �ʱ�ȭ
		document.f.curPage.value = "";	// ����¡ �ʱ�ȭ
		autoReload();
	}

	// ����������Ʈ �޾ƿ���
	function goSearch(){

	   autoReload();
	}

	// ����������Ʈ �޾ƿ���
	function autoReload(){

       viewProgress('studentList','block',CONTEXTPATH, SYSTEMCODE,'Y');

	   var f = document.f;
	   var curPage = f.curPage.value;
	   var sTarget = f.pSTarget[f.pSTarget.selectedIndex].value;
 	   var sWord = ajaxEnc(f.pSWord.value);
	   var str = f.pCurriYearTerm[f.pCurriYearTerm.selectedIndex].value;

	   var curriCode = "";
	   var curriYear = "";
	   var curriTerm = "";
	   if(str !="") {
		   var strs = str.split("\|");
		   curriCode = strs[0];
		   curriYear = strs[1];
		   curriTerm = strs[2];
	   }
	   StudentWork.studentListAuto(curPage, "enroll", sTarget, sWord, curriCode, curriYear, curriTerm, autoReloadCallback);
	}

	// ����������Ʈ �ѷ��ֱ�
	function autoReloadCallback(data){

	  	var totalCount = data.totalDataCount;
	  	var dataList = data.dataList;
	  	var paggingStr = data.pagging;
		var rowLength = 0;
		if(dataList != null) rowLength = dataList.length;

	  	var studentListObj = $("studentList");
	  	var paggingObj = $("getPagging");
	  	var objStr = "<table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>";

		if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">��ϵ� ������û ������ �����ϴ�.</td></tr>";
	  	}else{

		  	for(i=0;i<rowLength;i++){
				var studentDTO = dataList[i];

		    	var userName = studentDTO.userName;
		    	var userId = studentDTO.userId;
		    	var stuCurriName = studentDTO.curriName;
		    	var curriCode = studentDTO.curriCode;
		    	var curriYear = studentDTO.curriYear;
		    	var curriTerm = studentDTO.curriTerm;
		    	var enrollNo = studentDTO.enrollNo;
		    	var regDate = studentDTO.regDate;

			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
				+" <td width='70' class=\"s_tab04_cen\">"+totalCount+"</td>"
				+ "<td></td><td width='100' class=\"s_tab04_cen\">"+userName+"("+userId+")</td>"
				+ "<td></td><td width='' class=\"s_tab04_cen\">"+stuCurriName+"</td>"
				+ "<td></td><td width='140' class=\"s_tab04_cen\">"+regDate+"</td>"
				+ "<td></td><td width='74' class=\"s_tab04_cen\"><input type='checkbox' class='solid0' name='pChk' "
				+ " value="+userId+"|"+curriCode+"|"+curriYear+"|"+curriTerm+"|"+enrollNo+"></td>"
				+ "</tr>";

				if(totalCount > 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    totalCount--;
			}
		}
		objStr += "</table>";
		
		studentListObj.innerHTML = objStr;
		studentListObj.style.display = "block";
	 }


	function goAdd() {
		var str = f.pCurriYearTerm[f.pCurriYearTerm.selectedIndex].value;
		var loc = "";
		if(str !="") {
		 	var strs = str.split("\|");
			loc=CONTEXTPATH+"/Student.cmd?cmd=enrollStudentWrite&pCurriCode="+strs[0]+"&pCurriYear="+strs[1]+"&pCurriTerm="+strs[2];
			WriteWin = window.open(loc,"writewin","width=530,height=300,left=0,top=0,resizable=no,scrollbars=1");
			WriteWin.focus();
		}else{
			return;
		}
	}


	// ��������, �������, ������û������ �Ѵ�
	function checkConfirm(vGubun)
	{
		var f = document.f;
		var str="", loc="";
		
		f.pChk = document.querySelectorAll("input[type='checkbox']");  // ADD 2021-12-02
		
		
		var vMsg = (vGubun=='S' ? "��������" : (vGubun=='N' ? "�������" : "����" ) );
		
		console.log('f.pChk:', f.pChk);
		if(f.pChk==null){
			alert("������ ����� �����ϴ�!");
			return ;
		}
		
		var cnt = (f.pChk.length==null ? "1" : f.pChk.length );

		// if(cnt == 1 ){
			// if(f.pChk.checked) str = "/"+f.pChk.value;
		// }else if(cnt > 1 ){
			for(i = 0; i < cnt; i++)
			{
				if(f.pChk[i].checked == true) str += "/"+f.pChk[i].value;
			}

		//}
		
		if(str =="") {
			alert(vMsg + "�� ����� ������ �ּ���!")
			return ;
		}
		
		StudentWork.checkConfirmAjax(str, vGubun, AfterCheckConfirm );
	}
	
	// ��������, �������, ������û���� ���� �ݹ� �޼ҵ�
	function AfterCheckConfirm(msg){
		alert(msg);	
		
		autoReload();
	}
	
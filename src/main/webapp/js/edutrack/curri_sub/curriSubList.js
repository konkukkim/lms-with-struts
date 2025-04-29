	// ���񸮽�Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";

	function init(systemCode,contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;

		cateSelectList();
	}

	function errorMessage(){
		alert("�۾��� ������ �߻��߽��ϴ�.\n��õ� ���ּ���!!!");
	}

	// ��������
	function goAdd(){
		var f = document.Input;
		var cateCode = DWRUtil.getValue("pCateCode");
		var curriCode = DWRUtil.getValue("pCurriCode");

		if(cateCode == ''  || cateCode == '-1'){
			alert('���� �з��� ������ �ּ���');
			f.pCateCode.focus();
		}else if(curriCode == ''  || curriCode == '-1' ){
			alert('������ ������ �ּ���');
			f.pCurriCode.focus();
		}else{
			var curriYear = f.pCurriYear.value;
			document.location.href = CONTEXTPATH+"/CurriSub.cmd?cmd=curriSubWrite&pCateCode="+cateCode+"&pCurriCode="+curriCode+"&pCurriYear="+curriYear+"&pMode=MyPage";
		}
	}

	function autoReload(){
		var cateCode = DWRUtil.getValue("pCateCode");
		var curriCode = DWRUtil.getValue("pCurriCode");
		var curriYear = DWRUtil.getValue("pCurriYear");
		CurriSubWork.curriSubListAuto(cateCode, curriCode, curriYear, autoReloadCallback);
	}

	// ��������Ʈ �ѷ��ֱ�
	function autoReloadCallback(data){
		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var curriSubListObj = $("curriSubList");
	  	var objStr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
		  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"5\">������ ������ �����ϴ�.</td></tr>";
	  	}else{
	    	var cateCode = DWRUtil.getValue("pCateCode");
	    	var curriCode = "";
	    	var curriYear = "";
	    	var curriTerm = "";
	    	var yearTerm = "";
	    	var enrollDate = "";
	    	var serviceDate = "";
	    	var courseCount = "";

		  	for(i=0;i<rowLength;i++){
    			var curriSubDTO = data[i];

				curriCode = curriSubDTO.curriCode;
				curriYear = curriSubDTO.curriYear;
				curriTerm = curriSubDTO.curriTerm;
				yearTerm = curriSubDTO.curriName;
				enrollDate = curriSubDTO.enrollStart;
				serviceDate = curriSubDTO.serviceStart;
				courseCount = curriSubDTO.courseCount;

			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
			    	+ "<td width='40' class=\"s_tab04_cen\">"+allItemCnt+"</td>"
			    	+ "<td></td><td class=\"s_tab04\">"
			    	+ "<a href='"+CONTEXTPATH+"/CurriSub.cmd?cmd=curriSubEdit&pMode=MyPage&pCateCode="+cateCode+"&pCurriCode="+curriCode+"&pCurriYear="+curriYear+"&pCurriTerm="+curriTerm+"'>"+yearTerm+"</a></td>"
			    	+ "<td></td><td width='150' class=\"s_tab04_cen\">"+enrollDate+"</td>"
			    	+ "<td></td><td width='150' class=\"s_tab04_cen\">"+serviceDate+"</td>"
			    	+ "<td></td><td width='70' class=\"s_tab04_cen\">"
			    	+ "<a href='"+CONTEXTPATH+"/CurriSubCourse.cmd?cmd=curriSubCourseList&pMode=MyPage&pCateCode="+cateCode+"&pCurriCode="+curriCode+"&pCurriYear="+curriYear+"&pCurriTerm="+curriTerm+"'><b><font color='#CC6600'>"+courseCount+"����</font></b></a>"
			    	+ "</td>"
			    	+ "</tr>";

			    if(allItemCnt > 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    allItemCnt--;
			}
		}
		objStr += "</table>";

		curriSubListObj.innerHTML = objStr;
		curriSubListObj.style.display = "block";
	 }

	// �����з� ����Ʈ�ڽ� �ʱ�ȭ
	function cateSelectList(){
		CurriSubWork.cateSelectList(cateSelectListCallback);
	}

	// �����з� ����Ʈ�ڽ� ǥ��
	function cateSelectListCallback(data){
		DWRUtil.removeAllOptions("pCateCode");
		var defaultSelect = {"":"--�����з�--"};
		DWRUtil.addOptions("pCateCode", defaultSelect);
		DWRUtil.addOptions("pCateCode", data);

		curriSelectList(DWRUtil.getValue("pCateCode"));
	}

	// ���� ����Ʈ�ڽ� �ʱ�ȭ
	function curriSelectList(cateCode){
		CurriSubWork.curriSelectList(cateCode, curriSelectListCallback);
	}

	// ���� ����Ʈ�ڽ� ǥ��
	function curriSelectListCallback(data){
		DWRUtil.removeAllOptions("pCurriCode");
		var defaultSelect = {"":"--��ü����--"};
		DWRUtil.addOptions("pCurriCode", defaultSelect);
		DWRUtil.addOptions("pCurriCode", data);

		autoReload();
	}

	// �����з� ����
	function changeCategory(){
		var cateCode =  DWRUtil.getValue("pCateCode");
		curriSelectList(cateCode);
	}

	// ������� �ߺ�üũ start
	function checkCurriTerm(){
		var f = document.Input;
		var cateCode = f.pCateCode.value;
		var curriCode = f.pCurriCode.value;
		var curriYear = f.pCurriYear.value;
		var curriTerm = f.pCurriTerm.value;

		if(curriTerm !=null && curriTerm !=''){
			CurriSubWork.curriTermCheck(cateCode,curriCode,curriYear,curriTerm,{
				callback:function(data) {

					var result = data;
				  	if(result != '0'){
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
		var f = document.Input;
		alert("'"+f.pCurriTerm.value+"'�� ������� ��������Դϴ�.\n�ٸ� ��������� �Է��� �ּ���");
		f.pCurriTerm.value ="";
		new Effect.Highlight("pCurriTerm");
		f.pCurriTerm.focus();
	}
	// ������� �ߺ�üũ end

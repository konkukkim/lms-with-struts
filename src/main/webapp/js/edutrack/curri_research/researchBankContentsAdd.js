	// ���񸮽�Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var USER_ID = "";
	var RES_ID = "";


	function init(systemCode,contextPath,userId) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.USER_ID = userId;

		researchBankInfoSelectList();
	}

	function errorMessage(){
		alert("�۾��� ������ �߻��߽��ϴ�.\n��õ� ���ּ���!!!");
	}

	// �������� �׸� ����Ʈ�ڽ� �ʱ�ȭ
	function researchBankInfoSelectList(){
		ResearchBankWork.researchBankInfoSelectList(USER_ID, 'N', researchBankInfoSelectListCallback);
	}

	// �������� �׸� ����Ʈ�ڽ� ǥ��
	function researchBankInfoSelectListCallback(data){
		DWRUtil.removeAllOptions("pResId");
		var defaultSelect = {"":"--�׸���--"};
		DWRUtil.addOptions("pResId", defaultSelect);
		DWRUtil.addOptions("pResId", data);

		autoReload();
	}

	function autoReload(){
		RES_ID = DWRUtil.getValue("pResId");
		ResearchBankWork.researchBankContentsListAuto(RES_ID, autoReloadCallback);
	}

	// ��������Ʈ �ѷ��ֱ�
	function autoReloadCallback(data){

		var rowLength = 0;
		var allItemCnt = 0;
		if(data != null){
			rowLength = data.length;
			allItemCnt = data.length;
		}

	  	var objStr = "<table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>";

	  	if(rowLength == 0){
	  		if(RES_ID != ""){
			  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">��ϵ� ������ �����ϴ�.</td></tr>";
			}else{
			  	objStr += "<tr><td class=\"s_tab04_cen\" colspan=\"11\">�׸��� �����ϼ���.</td></tr>";
			}
	  	}else{
	    	var contentsType = "";
	    	var resNo = "";
	    	var contents = "";
	    	var contentsTypeName = "";

		  	for(i=0;i<rowLength;i++){
    			var researchBkContentsDTO = data[i];

				contentsType = researchBkContentsDTO.contentsType;
				resNo = researchBkContentsDTO.resNo;
				contents = researchBkContentsDTO.contents;

				if(contentsType == "J") contentsTypeName	=	"������";
				else if(contentsType == "S") contentsTypeName	=	"OX��";
				else if(contentsType == "K") contentsTypeName	=	"������";

			    objStr += "<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
			    	+ "<td width='70' class=\"s_tab04_cen\">"+allItemCnt+"</td>"
			    	+ "<td></td><td width='322' class=\"s_tab04\">"
			    	+ " <a href=\"javascript:showResearchContents('"+resNo+"');\">"+contents+"</a></td>"
					+ "<td></td><td width='100' class=\"s_tab04_cen\">"+contentsTypeName+"</td>"
			    	+ "<td></td><td width='74' class=\"s_tab04_cen\"><input type=checkbox name='contentsBox' value='"+resNo+"' style='border:0'></td>"
			    	+ "</tr>";

			    if(allItemCnt != 1) objStr += "<tr class=\"s_tab03\"><td colspan=\"11\"></td></tr>";

			    allItemCnt--;
			}
		}
		objStr += "</table>";

		$("researchBankContentsList").innerHTML = objStr;
		$("researchBankContentsList").style.display = "block";
	 }

	// ���� ���� ����
	function showResearchContents(resNo){
       var Page =  CONTEXTPATH+"/ResearchBank.cmd?cmd=researchBankContentsShow&pResId="+RES_ID+"&pResNo="+resNo;
       var winOption = "left="+windowLeftPosition(525)+",top="+windowTopPosition(400)+",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=yes,resizable=no,width=525,height=400";
	   ShowInfo = window.open(Page,"bankcotentsshow", winOption);
    }

    function checkAll(){
        var f = document.f;
        if(f.checkYn.value == "Y") {
          f.checkYn.value = "N";
    	  setChkboxAll(f, "contentsBox",false);
        }else{
          f.checkYn.value = "Y";
    	  setChkboxAll(f, "contentsBox", true);
       }
    }

    function addContents(){
  	   var f = document.f;
  	   var checkVal = getChkBoxByValue(f, "contentsBox", "");

	   if(checkVal == "") {
	       alert("����� ������ ������ �ּ���.");
	       return;
	   }

	   f.submit();
    }

 	function goList(){
	   var f = document.f;
	   var contentsResid = f.pContentsResId.value;
       var loc = CONTEXTPATH+"/ResearchContents.cmd?cmd=researchContentsList&pResId="+contentsResid;
       document.location = loc;
	}

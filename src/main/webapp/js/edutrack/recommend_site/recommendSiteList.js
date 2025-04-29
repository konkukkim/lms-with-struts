	// ��������Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var SEL_FLAG = "4";		// ��õ ����Ʈ �ܰ� ������..1�ܰ�, 2�ܰ�, 3�ܰ�
    var SITE_URL = ""; // ����Ʈ URL �� �߰��Ϸ��� �ߴٰ� ����Ұ�� TEXT BOX �� ���� ���ͽ�Ű�� ����
    var DIV_OBJ = ""; // left, right division 
    
	function init(systemCode, contextPath) {
		this.SYSTEMCODE  = systemCode;
		this.CONTEXTPATH = contextPath;	
		
		autoReload();
	}
 
    // ����Ʈ �ʱ�ȭ
    function autoReload(){
        viewProgress('leftListDiv','block',CONTEXTPATH, SYSTEMCODE,'Y');

		DIV_OBJ = leftListDiv;
		
   		RecommendSiteWork.recommendSiteTwoDepthAjax("000000", autoReloadList);
    }
	    

	// ������ division
	function viewRightDiv(vMasterCode){
        viewProgress('rightListDiv','block',CONTEXTPATH, SYSTEMCODE,'Y');

        DIV_OBJ = rightListDiv;
		
    	RecommendSiteWork.recommendSiteTwoDepthAjax(vMasterCode, autoReloadList);
    }
        	
    // ��� ǥ��
    function autoReloadList(dataList){
    
    	var data = "";
    	var rowLength = 0;
    	var befMasterCode = "";
    	
    	if(dataList != null){
    		befMasterCode = dataList[0];
    		data   = dataList[1];
    	}
    	if(data != null){
    		rowLength = data.length;
    	}

    	var listDiv = $(DIV_OBJ);;
    	var objStr = "";
    	
        //objStr += "\n<table width=100% align=center>";
        
    	if(rowLength == 0){
            								
            //objStr += "\n<td >";
            objStr += "\n�� ��ϵ� ��õ����Ʈ�� �����ϴ�. ";
            //objStr += "\n</td>";
			        
	  	}else{

            var recommendSite ;
            
            for(i=0;i<rowLength; i++){
                
                recommendSite = data[i];
            
                vRecommCode  = recommendSite.recommCode;
                vMasterCode  = recommendSite.masterCode;
                vRecommName  = recommendSite.recommName;
                vSiteUrl  	 = recommendSite.siteUrl;
               
                //objStr += "\n<tr>";
	            //objStr += "\n<td height=25>";
	            
		        if(vMasterCode==befMasterCode){
		        	if(i!=0){
	        			objStr += "\n<p style=\"border-right:1px solid #FCCED6;height:5px\"></p>";
	        			objStr += "\n</div>";
	        		}
	        		
	        		if(DIV_OBJ.id=="leftListDiv"){
		    		 	objStr += "\n<p class=left><a href=\"#\" onClick=\"javascript:viewRecommendSubList('divL_','divL_"+vRecommCode+"')\" >"+ vRecommName +"</p>";
		            	objStr += "\n<div id='divL_"+vRecommCode+"' Style='display:"+(i==0 ? "" : "none" )+"'>";
            		}
            		else if(DIV_OBJ.id=="rightListDiv"){
		    		 	objStr += "\n<p  class=left><a href=\"#\" onClick=\"javascript:viewRecommendSubList('divR_','divR_"+vRecommCode+"')\" >"+ vRecommName +"</p>";
		            	objStr += "\n<div id='divR_"+vRecommCode+"' Style='display:"+(i==0 ? "" : "none" )+"'>";
            		}
		    	
	        	}else{
	            	
	        		if(DIV_OBJ.id=="leftListDiv"){
		    			objStr += "<p class=left-sub>" ;
	        			objStr += "<a href=\"javascript:viewRightDiv('"+vRecommCode+"')\">"+vRecommName+"</a>";
		    		}
		    		else if(DIV_OBJ.id=="rightListDiv"){
		    			objStr += "<p class=right>" ;
	        			objStr += "<a href='"+vSiteUrl+"' target='_new'>"+vRecommName+"</a>";
	        		}

	            	objStr += "</p>" ;
	            }
	        	
	            //objStr += "\n</td>";
                //objStr += "\n</tr>";
			}
	        	

            //objStr += "\n</table>";
		}
		
		listDiv.innerHTML = objStr;
		listDiv.style.display = "block";

     }

	// Ŭ���� div  �� �����Ѵ�..
	function viewRecommendSubList(val, divName){
		
		if(divName.indexOf(val)==0){
			for(i in document.all){
				if(i.indexOf(val)<0) continue;
				document.all[i].style.display = "none";
			}
		}
		
		document.all[divName].style.display = "";
	}
	
   
	/** �߰���ư Ŭ����..
	 */
	function addForm(val){
		if(val=="none"){
			$("WriteDiv").style.display = val;
			$("CancelButDiv").style.display = val;
		}else{
			Effect.Appear(WriteDiv);
			Effect.Appear(CancelButDiv);
			viewMasterCode("1");
		}
		
		

	}
	
	/** ����Ʈ �ڽ��� input box �� ��ü�Ѵ�..
	 ** cancelGubun : 'A'-�߰�, 'C'- ���, 'M'-����
	 */
	function changeForm(val, cancelGubun){

		if(cancelGubun!="") DWRUtil.setValue("pGUBUN" , cancelGubun);

		// ���� Ŭ���� div id setting		
		dSelObj = eval("dSel"+val);
		dAddObj = eval("dAddBut"+val);             
		dRegObj = eval("dRegBut"+val);             
		
		cdObj = eval("Regist.pSelCode"+val);
		nmObj = eval("Regist.pSelName"+val);

		// ���õ� ���� ���µ�, �����Ϸ��� �Ұ��...
		if(cancelGubun=="edit" && cdObj.value==""){
			alert("���õ� ���� �����ϴ�.\n\n���� ������ �ϼ���.");
			new Effect.Highlight(cdObj);
			return false;
		}
		
		// �߰���ư Ŭ���Ŀ� ��ҹ�ư�� �ٷ� Ŭ���� ���
		// siteurl ���� ���������� ������..
		if(val=="4"){
			if(cancelGubun=="write")
				SITE_URL = DWRUtil.getValue("pSiteUrl") ;
			else if(cancelGubun=="")
				DWRUtil.setValue("pSiteUrl", SITE_URL);
		}

		
		// �����̳� �Է½ÿ� �������� disabled ��Ų��..
		if(cancelGubun=="write" || cancelGubun=="edit"){
			// ��� slectbox, inputbox �� disabled ��Ų��
			for(i in document.Regist){
				if(i.indexOf("pSelCode")<0 
				&& i.indexOf("pSelName")<0 
				&& i.indexOf("pSiteUrl")<0) continue;

				Regist[i].disabled = true;
			}

			// ��� ���,����,��ҹ�ư�� �����..
			for(i=1;i<=4;i++){
				$("dSel"   +i).style.display = "";			// select box div
				$("dAddBut"+i).style.display = "";			// �߰���ư div
				$("dRegBut"+i).style.display = "none";		// ��Ϲ�ư div
			}

			// ���� �����̳� �Է��ϰ� �ϴ� object �� Ȱ��ȭ��Ų��.
			cdObj.disabled = false;
			nmObj.disabled = false;
			dAddObj.style.display = "none";
			dSelObj.style.display = "none";
			dRegObj.style.display = ""; 
		
			// ����Ʈ url
			if(val=="4"){
				Regist.pSiteUrl.disabled = false;
				dRegObj.style.dispaly = "";
			}
			
			// �Է½ÿ� input box clear...
			if(cancelGubun=="write"){
		  		nmObj.value = "";
		  		
		  		if(val=="4") DWRUtil.setValue("pSiteUrl","");
		  	}
		  	// �����ÿ� text box�� ���õ� text �� ����...
			else if(cancelGubun=="edit"){
				DWRUtil.setValue("pRecommCode", cdObj[cdObj.selectedIndex].value);
	
				nmObj.value = cdObj[cdObj.selectedIndex].text;
			}
			
		}
		// ��ҽÿ� ��� Object Ȱ��ȭ
		else{
			for(i in Regist){
				if(i.indexOf("pSelCode")<0 
				&& i.indexOf("pSelName")<0 
				&& i.indexOf("pSiteUrl")<0) continue;
				
				Regist[i].disabled = false;
			}
			
			$(dSelObj).style.display = "";
			$(dAddObj).style.display = "";
			$(dRegObj).style.display = "none";
		} // end if
		
	}
	
	/** 1�ܰ� select box need data : value '1'
	 ** 2�ܰ� select box need data : value '2'
	 */
	function viewMasterCode(val){

		SEL_FLAG = val;
		
		obj = eval("Regist.pSelCode"+(Number(val)-1));
	
		vMasterCode    = ((val=="1") ? "000000" :  ((val=="") ? DWRUtil.getValue("pSelCode3") :  obj.value ) );
		vRecommCode    = ((val=="1") || (val=="2") || (val=="3") || (val=="4") ? null : DWRUtil.getValue("pSelCode4") ) ; 
		callbackMethod = ((val=="1") || (val=="2") || (val=="3") || (val=="4") ? callbackViewMasterCode : callbackSiteUtl ) ;

		RecommendSiteWork.recommendSiteListAuto(vMasterCode, vRecommCode, val, callbackMethod);
	}

	/** callback after 1,2,3�ܰ� selectbox change
	 */
	function callbackViewMasterCode(data){
		if(data != null){
    		rowLength = data.length;
    	}

    	masterObj = eval("Regist.pSelCode" + SEL_FLAG );
		masterObj.options.length = rowLength;
		masterObj.options[0] = new Option("- ���� -", "");
	
		for(i=0; i<rowLength ;i++) // 
		{
			recommendSite = data[i];
             
			masterObj.options[i+1] = new Option(recommendSite.recommName, recommendSite.recommCode);   // new Option(text, value)
		}
		
		// 1�ܰ谡 ����ɶ�, 2,3,4�ܰ踦 Ŭ���� ��Ų��.
		// 2�ܰ谡 ����ɶ�,   3,4�ܰ踦 Ŭ���� ��Ų��.
		// 3�ܰ谡 ����ɶ�,     4�ܰ踦 Ŭ���� ��Ų��.
		if(SEL_FLAG=="1" || SEL_FLAG=="2" || SEL_FLAG=="3"){
			
			// On Change select box -> next selectbox init...
			for(i=(Number(SEL_FLAG)+1); i<=4; i++)
			{
				tmpObj = eval("Regist.pSelCode" + i);
				
				tmpObj.options.length = 0;
				tmpObj.options[0] = new Option("- ���� -", "");
			}
			
			Regist.pSiteUrl.value ="";
		}
		else if(SEL_FLAG=="4"){
			Regist.pSiteUrl.value ="";
		}
		
	}
		
	/** callback after 4�ܰ�  selectbox change
	 */
	function callbackSiteUtl(data){
		if(data != null){
    		rowLength = data.length;
    	}

		if(rowLength>0){
			recommendSite = data[0];
    		DWRUtil.setValue("pSiteUrl", recommendSite.siteUrl);
		}
	}				

	/** �ִ� �Է� üũ
	 */
	function tmpLenCheck(obj){
		
		lenCheck = obj.getAttribute("lenCheck");
		dispName = obj.getAttribute("dispName");
		lenValue = obj.getAttribute("value");
		
		if(lenCheck != null )
        {
          if( jsByteLength(lenValue) > eval(lenCheck) )
            {
              alert(dispName + "��(��) " + lenCheck + " �ڸ��� ������ �����ϴ� ���� ���ڼ�("+jsByteLength(lenValue)+")");
              new Effect.Highlight(obj);
              obj.focus();
              return false;
            }
        }
	}
	
	/** ��õ ����Ʈ�� ����Ѵ�.
	 ** @param : val -1: 1�ܰ� save, 2: 2�ܰ� save, 3:3�ܰ� save
	 */
    function registSite(val){
    	
    	rObj = eval("Regist.pSelCode"+val);
    	inObj = eval("Regist.pSelName"+val);
    	
		var vMasterCode = "";
		var vRecommCode = rObj[rObj.selectedIndex].value;
		var vRecommName = inObj.value;
		var vSiteUrl 	= "";

		if(vRecommName==""){
			alert("�Է��� ���� �����ϴ�.\n\nȮ���� �ٽ� �õ��Ͻʽÿ�.");
			return;
		}else if(tmpLenCheck(inObj)=="false"){
			return;
		}
		
    	var vGUBUN = DWRUtil.getValue("pGUBUN");

    	if(val=="1"){
    		vMasterCode = "000000";
    	}
    	if(val=="2"){
    		vMasterCode = DWRUtil.getValue("pSelCode1");
    	}
    	if(val=="3"){
    		vMasterCode = DWRUtil.getValue("pSelCode2");
    	}
    	if(val=="4" ){
    		vMasterCode = DWRUtil.getValue("pSelCode3");
    		vSiteUrl 	= DWRUtil.getValue("pSiteUrl");
    		
    		if(trim(vSiteUrl)==""){
				alert("����Ʈ URL�� �����ϴ�.\n\nȮ���� �ٽ� �õ��Ͻʽÿ�.");
				return;
			}else if(tmpLenCheck($("pSiteUrl"))=="false"){
				 return;
			}
    	}

    	SEL_FLAG = val;
    	if(trim(vMasterCode)==""){
    		alert("���� �ڵ尡 �������� �ʾҽ��ϴ�.\n\nȮ���� �ٽ� �õ��Ͻʽÿ�.");
			return;
    	}
    	
    	RecommendSiteWork.recommendSiteRegistAjax(vGUBUN, val, vMasterCode, vRecommCode, vRecommName, vSiteUrl, callbackAfterRegist);
    }

	// ������ callback
	function callbackAfterRegist(msg){
		var vGUBUN = DWRUtil.getValue("pGUBUN");
		
		alert(msg);
		
		if(vGUBUN=="write"){
			DWRUtil.setValue("pGUBUN"	 ,"");
			DWRUtil.setValue("pMasterCode","");
			DWRUtil.setValue("pRecommCode","");
			DWRUtil.setValue("pRecommName","");
			DWRUtil.setValue("pSiteUrl","");
		}

		viewMasterCode(SEL_FLAG);
		changeForm(SEL_FLAG, "");
		
	}


	/** ��õ ����Ʈ�� �����Ѵ�.
	 ** @param : val -1: 1�ܰ� save, 2: 2�ܰ� save, 3:3�ܰ� save
	 */
    function deleteSite(val){

		if(DWRUtil.getValue("pGUBUN") =="write"){
			alert("������ ���� �����ϴ�. \n\nȮ���� �ٽ� �õ��Ͻʽÿ�.");
			return;
		}

    	rObj = eval("Regist.pSelCode"+val);
    	DWRUtil.setValue("pGUBUN" , "delete");

    	//if(confirm("������ �Ͻø� ���� ���� ����Ʈ�� ��� �����˴ϴ�.\n\n������ �����Ͻðڽ��ϱ�?")==false) return;
    	if(confirm("������ �Ͻø� ������ �Ұ����մϴ�.\n\n������ �����Ͻðڽ��ϱ�?")==false) return;
    	
		var vMasterCode="";
		var vRecommCode = rObj[rObj.selectedIndex].value;
		
    	if(val=="1"){
    		vMasterCode = "000000";
    	}else{
    		mObj = eval("Regist.pSelCode"+ Number(val-1) );
    		vMasterCode = mObj.value;
    	}
    	
		
    	SEL_FLAG = val;
    	RecommendSiteWork.recommendSiteDeleteAjax(vMasterCode, vRecommCode, val, callbackAfterRegist);
    }
    

	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}	

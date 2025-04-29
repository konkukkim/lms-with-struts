	// 목차리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var SEL_FLAG = "4";		// 추천 사이트 단계 구분자..1단계, 2단계, 3단계
    var SITE_URL = ""; // 사이트 URL 을 추가하려고 했다가 취소할경우 TEXT BOX 에 값을 복귀시키기 위함
    var DIV_OBJ = ""; // left, right division 
    
	function init(systemCode, contextPath) {
		this.SYSTEMCODE  = systemCode;
		this.CONTEXTPATH = contextPath;	
		
		autoReload();
	}
 
    // 리스트 초기화
    function autoReload(){
        viewProgress('leftListDiv','block',CONTEXTPATH, SYSTEMCODE,'Y');

		DIV_OBJ = leftListDiv;
		
   		RecommendSiteWork.recommendSiteTwoDepthAjax("000000", autoReloadList);
    }
	    

	// 오른쪽 division
	function viewRightDiv(vMasterCode){
        viewProgress('rightListDiv','block',CONTEXTPATH, SYSTEMCODE,'Y');

        DIV_OBJ = rightListDiv;
		
    	RecommendSiteWork.recommendSiteTwoDepthAjax(vMasterCode, autoReloadList);
    }
        	
    // 목록 표시
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
            objStr += "\n※ 등록된 추천사이트가 없습니다. ";
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

	// 클릭시 div  를 오픈한다..
	function viewRecommendSubList(val, divName){
		
		if(divName.indexOf(val)==0){
			for(i in document.all){
				if(i.indexOf(val)<0) continue;
				document.all[i].style.display = "none";
			}
		}
		
		document.all[divName].style.display = "";
	}
	
   
	/** 추가버튼 클릭시..
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
	
	/** 셀렉트 박스와 input box 를 교체한다..
	 ** cancelGubun : 'A'-추가, 'C'- 취소, 'M'-수정
	 */
	function changeForm(val, cancelGubun){

		if(cancelGubun!="") DWRUtil.setValue("pGUBUN" , cancelGubun);

		// 현재 클릭된 div id setting		
		dSelObj = eval("dSel"+val);
		dAddObj = eval("dAddBut"+val);             
		dRegObj = eval("dRegBut"+val);             
		
		cdObj = eval("Regist.pSelCode"+val);
		nmObj = eval("Regist.pSelName"+val);

		// 선택된 값이 없는데, 수정하려고 할경우...
		if(cancelGubun=="edit" && cdObj.value==""){
			alert("선택된 값이 없습니다.\n\n먼저 선택을 하세요.");
			new Effect.Highlight(cdObj);
			return false;
		}
		
		// 추가버튼 클릭후에 취소버튼을 바로 클릭할 경우
		// siteurl 값을 이전값으로 복구함..
		if(val=="4"){
			if(cancelGubun=="write")
				SITE_URL = DWRUtil.getValue("pSiteUrl") ;
			else if(cancelGubun=="")
				DWRUtil.setValue("pSiteUrl", SITE_URL);
		}

		
		// 수정이나 입력시에 나머지는 disabled 시킨다..
		if(cancelGubun=="write" || cancelGubun=="edit"){
			// 모든 slectbox, inputbox 를 disabled 시킨다
			for(i in document.Regist){
				if(i.indexOf("pSelCode")<0 
				&& i.indexOf("pSelName")<0 
				&& i.indexOf("pSiteUrl")<0) continue;

				Regist[i].disabled = true;
			}

			// 모든 등록,삭제,취소버튼을 숨긴다..
			for(i=1;i<=4;i++){
				$("dSel"   +i).style.display = "";			// select box div
				$("dAddBut"+i).style.display = "";			// 추가버튼 div
				$("dRegBut"+i).style.display = "none";		// 등록버튼 div
			}

			// 현재 수정이나 입력하고 하는 object 는 활성화시킨다.
			cdObj.disabled = false;
			nmObj.disabled = false;
			dAddObj.style.display = "none";
			dSelObj.style.display = "none";
			dRegObj.style.display = ""; 
		
			// 사이트 url
			if(val=="4"){
				Regist.pSiteUrl.disabled = false;
				dRegObj.style.dispaly = "";
			}
			
			// 입력시에 input box clear...
			if(cancelGubun=="write"){
		  		nmObj.value = "";
		  		
		  		if(val=="4") DWRUtil.setValue("pSiteUrl","");
		  	}
		  	// 수정시에 text box에 선택된 text 를 세팅...
			else if(cancelGubun=="edit"){
				DWRUtil.setValue("pRecommCode", cdObj[cdObj.selectedIndex].value);
	
				nmObj.value = cdObj[cdObj.selectedIndex].text;
			}
			
		}
		// 취소시에 모든 Object 활성화
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
	
	/** 1단계 select box need data : value '1'
	 ** 2단계 select box need data : value '2'
	 */
	function viewMasterCode(val){

		SEL_FLAG = val;
		
		obj = eval("Regist.pSelCode"+(Number(val)-1));
	
		vMasterCode    = ((val=="1") ? "000000" :  ((val=="") ? DWRUtil.getValue("pSelCode3") :  obj.value ) );
		vRecommCode    = ((val=="1") || (val=="2") || (val=="3") || (val=="4") ? null : DWRUtil.getValue("pSelCode4") ) ; 
		callbackMethod = ((val=="1") || (val=="2") || (val=="3") || (val=="4") ? callbackViewMasterCode : callbackSiteUtl ) ;

		RecommendSiteWork.recommendSiteListAuto(vMasterCode, vRecommCode, val, callbackMethod);
	}

	/** callback after 1,2,3단계 selectbox change
	 */
	function callbackViewMasterCode(data){
		if(data != null){
    		rowLength = data.length;
    	}

    	masterObj = eval("Regist.pSelCode" + SEL_FLAG );
		masterObj.options.length = rowLength;
		masterObj.options[0] = new Option("- 선택 -", "");
	
		for(i=0; i<rowLength ;i++) // 
		{
			recommendSite = data[i];
             
			masterObj.options[i+1] = new Option(recommendSite.recommName, recommendSite.recommCode);   // new Option(text, value)
		}
		
		// 1단계가 변경될때, 2,3,4단계를 클리어 시킨다.
		// 2단계가 변경될때,   3,4단계를 클리어 시킨다.
		// 3단계가 변경될때,     4단계를 클리어 시킨다.
		if(SEL_FLAG=="1" || SEL_FLAG=="2" || SEL_FLAG=="3"){
			
			// On Change select box -> next selectbox init...
			for(i=(Number(SEL_FLAG)+1); i<=4; i++)
			{
				tmpObj = eval("Regist.pSelCode" + i);
				
				tmpObj.options.length = 0;
				tmpObj.options[0] = new Option("- 선택 -", "");
			}
			
			Regist.pSiteUrl.value ="";
		}
		else if(SEL_FLAG=="4"){
			Regist.pSiteUrl.value ="";
		}
		
	}
		
	/** callback after 4단계  selectbox change
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

	/** 최대 입력 체크
	 */
	function tmpLenCheck(obj){
		
		lenCheck = obj.getAttribute("lenCheck");
		dispName = obj.getAttribute("dispName");
		lenValue = obj.getAttribute("value");
		
		if(lenCheck != null )
        {
          if( jsByteLength(lenValue) > eval(lenCheck) )
            {
              alert(dispName + "은(는) " + lenCheck + " 자리를 넘을수 없습니다 현재 글자수("+jsByteLength(lenValue)+")");
              new Effect.Highlight(obj);
              obj.focus();
              return false;
            }
        }
	}
	
	/** 추천 사이트를 등록한다.
	 ** @param : val -1: 1단계 save, 2: 2단계 save, 3:3단계 save
	 */
    function registSite(val){
    	
    	rObj = eval("Regist.pSelCode"+val);
    	inObj = eval("Regist.pSelName"+val);
    	
		var vMasterCode = "";
		var vRecommCode = rObj[rObj.selectedIndex].value;
		var vRecommName = inObj.value;
		var vSiteUrl 	= "";

		if(vRecommName==""){
			alert("입력할 값이 없읍니다.\n\n확인후 다시 시도하십시오.");
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
				alert("사이트 URL이 없습니다.\n\n확인후 다시 시도하십시오.");
				return;
			}else if(tmpLenCheck($("pSiteUrl"))=="false"){
				 return;
			}
    	}

    	SEL_FLAG = val;
    	if(trim(vMasterCode)==""){
    		alert("상위 코드가 설정되지 않았습니다.\n\n확인후 다시 시도하십시오.");
			return;
    	}
    	
    	RecommendSiteWork.recommendSiteRegistAjax(vGUBUN, val, vMasterCode, vRecommCode, vRecommName, vSiteUrl, callbackAfterRegist);
    }

	// 저장후 callback
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


	/** 추천 사이트를 삭제한다.
	 ** @param : val -1: 1단계 save, 2: 2단계 save, 3:3단계 save
	 */
    function deleteSite(val){

		if(DWRUtil.getValue("pGUBUN") =="write"){
			alert("삭제할 값이 없습니다. \n\n확인후 다시 시도하십시오.");
			return;
		}

    	rObj = eval("Regist.pSelCode"+val);
    	DWRUtil.setValue("pGUBUN" , "delete");

    	//if(confirm("삭제를 하시면 하위 단의 사이트도 모두 삭제됩니다.\n\n정말로 삭제하시겠습니까?")==false) return;
    	if(confirm("삭제를 하시면 복구가 불가능합니다.\n\n정말로 삭제하시겠습니까?")==false) return;
    	
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
    

	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}	

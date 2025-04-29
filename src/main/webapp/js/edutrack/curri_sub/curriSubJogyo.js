	// 목차리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var FLAG = "W";

////////////////// 입력/수정 공통 //////////////////////////////////////////

 	function init(vContextPath, vSystemCode, vFlag ){
        CONTEXTPATH = vContextPath ;
        SYSTEMCODE = vSystemCode ;
        FLAG = vFlag ;
    }
    
    // 조교자 select box setting1
    function getJogyoSelect(){
        CurriSubJogyoWork.curriSubJogyoSelectAuto(callbackGetJogyoSelect);
    }

    /** 조교자 select box setting2..callback
     */
    function callbackGetJogyoSelect(data){
    
    	var rowLength = 0;
    	var jogyoChgidNo = 0;
    	if(data != null){
    		rowLength = data.length;
    	}

		if(document.all.jogyoChgId==null)  jogyoChgidNo = 0;
		else if(document.all.jogyoChgId.length==null) jogyoChgidNo = 1;
		else jogyoChgidNo = document.all.jogyoChgId.length;
		
		
    	var jogyoDiv = $("jogyoSelectDiv");
    	var objStr = "";
    	
    	if(rowLength == 0){
    	    
            objStr += "\n등록된 조교자가 없읍니다";
			jogyoDiv.innerHTML = objStr;
		
	  	}else{
	    	
	    	objStr += "<div id=jogyoChgId Style='display:block;height:25px' >";
	    	objStr += "<select name=jogyoId"+jogyoChgidNo+" onChange=\"javascript:changeInputMode"+FLAG+"('"+jogyoChgidNo+"')\">\n";
	    	objStr += "<option value=''>- 선택 -</option>\n";

           for(i=0;i<rowLength;i++){

				codeDto = data[i];
				    
                objStr += "<option value='"+ codeDto.code+"'>"+ codeDto.name +"</option>\n";
            }
			objStr += "</select>";
			objStr += "<img src="+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_d.gif hspace=8 align=absmiddle Style='cursor:hand' onClick=\"javascript:cancelJogyoHtml('"+jogyoChgidNo+"')\">";
    		objStr += "</div>";
			
            jogyoDiv.innerHTML += objStr;
	  	}

		jogyoDiv.style.display = "block";
        
     }
	 

////////////////// 입력시 //////////////////////////////////////////
    
    /** 입력시
     ** selectbox 가 change 시 selectbox 를 감추고, 조교자 명을 text 로 보여준다...
     */
    function changeInputModeW(selboxId){

   		objNm = eval('document.Input.jogyoId'+selboxId );
    	
    	var objText = "";
    	var oldJogyoId = DWRUtil.getValue("pJogyoId");

    	if(oldJogyoId!="") oldJogyoId += ",";

    	vJogyoId = objNm[objNm.selectedIndex].value;
    	newJogyoId = oldJogyoId + vJogyoId;
  	
    	objText += objNm[objNm.selectedIndex].text;
    	objText += "<img src="+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_del.gif hspace=8 align=absmiddle Style='cursor:hand' onClick=\"javascript:delJogyoHtml('"+vJogyoId+"','"+selboxId+"')\">";
    	
		if(document.all.jogyoChgId.length==null)
			document.all.jogyoChgId.innerHTML = objText ;
		else
			document.all.jogyoChgId[selboxId].innerHTML = objText ;

    	DWRUtil.setValue("pJogyoId", newJogyoId );
    
	}

		
    /** 입력시
     ** 취소시  select box 감추기
     */
   function cancelJogyoHtml(divId){
	    if(document.all.jogyoChgId.length==null)
			imsiObj = document.all.jogyoChgId ;
		else
			imsiObj = document.all.jogyoChgId[divId];
			
		imsiObj.innerHTML = "" ;
		imsiObj.style.display="none";
   }
   
	/** 입력시
     ** 조교 삭제시 html 에서 감추기...
	 */
    function delJogyoHtml(vJogyoId, selboxId){
		var oldJogyoId = DWRUtil.getValue("pJogyoId");
		var newJogyoId = "";

		arr = oldJogyoId.split(",");
		newJogyoId = "";

		var cnt=0;
		for(i=0;i<arr.length;i++){
			if(arr[i]==vJogyoId) continue;
			
			if(cnt>0) newJogyoId += ",";
			newJogyoId += arr[i];
			
			cnt++;
		}
		
		DWRUtil.setValue("pJogyoId", newJogyoId );
		
		if(document.all.jogyoChgId.length==null)
			imsiObj = document.all.jogyoChgId ;
		else
			imsiObj = document.all.jogyoChgId[selboxId];
			
		imsiObj.innerHTML = "" ;
		imsiObj.style.display="none";

	}
	


////////////////// 수정시 //////////////////////////////////////////
	
	/** 수정시 조교리스트를 가져온다.
	 ** 조교자 select box setting1
	 */
    function getJogyoList(){

   		var pCurriCode = DWRUtil.getValue("pCurriCode");
    	var pCurriYear = DWRUtil.getValue("pCurriYear");
    	var pCurriTerm = DWRUtil.getValue("pCurriTerm");

        CurriSubJogyoWork.curriSubJogyoListAuto(pCurriCode, pCurriYear, pCurriTerm, callbackGetJogyoList);
    }

    /** 수정시 조교리스트를 가져온다.
	 ** 조교자 select box setting2..callback
     */
    function callbackGetJogyoList(data){
    
    	var rowLength = 0;
    	var jogyoChgidNo = 0;
    	if(data != null){
    		rowLength = data.length;
    	}

		if(document.all.jogyoChgId==null)  jogyoChgidNo = 0;
		else if(document.all.jogyoChgId.length==null) jogyoChgidNo = 1;
		else jogyoChgidNo = document.all.jogyoChgId.length;
		
		
    	var jogyoDiv = $("jogyoSelectDiv");
    	var objStr = "";
    	
    	if(rowLength > 0){
 
 			for(i=0; i<rowLength ;i++){
 				
 				userDto = data[i];
 				
		    	objStr += "<div id=jogyoChgId Style='display:block;height:25px' >";
		    	objStr += userDto.userName;
	    		objStr += "<img src="+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_del.gif hspace=8 align=absmiddle Style='cursor:hand' onClick=\"javascript:delJogyoDB('"+userDto.userId+"','"+(jogyoChgidNo+i)+"')\">";
	    		objStr += "</div>";
			}

	        jogyoDiv.innerHTML += objStr;
			jogyoDiv.style.display = "block";
			
	  	}
        
     }
     
	
	/** 수정시
     ** selectbox 가 change 시 selectbox 를 감추고, 조교자 명을 text 로 보여준다...
     */
    function changeInputModeE(selboxId){

		objNm = eval('document.Input.jogyoId'+selboxId );
    	
   		var pCurriCode = DWRUtil.getValue("pCurriCode");
    	var pCurriYear = DWRUtil.getValue("pCurriYear");
    	var pCurriTerm = DWRUtil.getValue("pCurriTerm");
    	var pJogyoId   = objNm[objNm.selectedIndex].value;
    	var pJogyoNm   = objNm[objNm.selectedIndex].text;
    	
    	CurriSubJogyoWork.curriSubJogyoRegistAuto(pCurriCode, pCurriYear, pCurriTerm , pJogyoId, pJogyoNm, selboxId, callbackAddJogyo );
	}
	
	/** 수정시 조교를 등록한후 callback
	 */
    function callbackAddJogyo(rData){
		
		var rowLength = 0;
		if(rData!=null){
			rowLength = rData.length;
		}
		
		if( rowLength == 4){
			jogyoId = rData[0];
			jogyoNm = rData[1];
			selboxId = rData[2];
			sucessFlag = rData[3];
			
			if(sucessFlag=="0"){
				alert("조교를 등록하는데 성공하였습니다.");
				
		    	objText  = jogyoNm;
		    	objText += "<img src="+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_del.gif hspace=8 align=absmiddle Style='cursor:hand' onClick=\"javascript:delJogyoDB('"+jogyoId+"','"+selboxId+"')\">";
		    	
				if(document.all.jogyoChgId.length==null)
					document.all.jogyoChgId.innerHTML = objText ;
				else
					document.all.jogyoChgId[selboxId].innerHTML = objText ;
			
			} else {
				alert("조교를 등록하는데 실패하였습니다.\n\n조교를 확인 후 다시 시도하십시오.");
			}
		}
		
	}
	
	
	/** 수정시
     ** 조교 삭제시 DB 에서 삭제하고, html 에서 감추기.
	 */
    function delJogyoDB(pJogyoId, selboxId){
		
		//objNm = eval('document.Input.jogyoId'+selboxId );
    	if(confirm("해당조교를 정말로 삭제하시겠습니까?")==false) return;
    	
   		var pCurriCode = DWRUtil.getValue("pCurriCode");
    	var pCurriYear = DWRUtil.getValue("pCurriYear");
    	var pCurriTerm = DWRUtil.getValue("pCurriTerm");
    	
    	CurriSubJogyoWork.curriSubJogyoDelAuto(pCurriCode, pCurriYear, pCurriTerm , pJogyoId, selboxId, callbackDelJogyo );
	}
	
	
	/** 수정시 조교를 삭제한후 callback
	 */
    function callbackDelJogyo(rData){
		
		var rowLength = 0;
		var sucessFlag = "-1";
		
		if(rData!=null){
			rowLength = rData.length;
		}
		
		if( rowLength == 2){
			selboxId = rData[0];
			sucessFlag = rData[1];
			
			if(sucessFlag=="0"){
				alert("조교를 삭제하는데 성공하였습니다.");
				
				if(document.all.jogyoChgId.length==null)
					imsiObj = document.all.jogyoChgId ;
				else
					imsiObj = document.all.jogyoChgId[selboxId];
					
				imsiObj.innerHTML = "" ;
				imsiObj.style.display="none";
				
			} else {
				alert("조교를 삭제하는데 실패하였습니다.\n\n조교를 확인 후 다시 시도하십시오.");
			}
		}
	}
	
	
	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}	


	// 목차리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
    
	function init(systemCode, contextPath ) {
		this.SYSTEMCODE  = systemCode;
		this.CONTEXTPATH = contextPath;	

		autoScoreGradeReload();	
	}
 
    // 리스트 초기화
    function autoScoreGradeReload(){
        
        $("infoWrite").style.display = "none";  // 평가등급 입력폼을 숨긴다..

        viewProgress('InfoList','block',CONTEXTPATH, SYSTEMCODE,'Y');
        
		vCurriYearTerm = DWRUtil.getValue("pCurriYearTerm").split("!");

		ScoreGradeWork.scoreGradeListAuto(vCurriYearTerm[0], vCurriYearTerm[1], null, callBackAutoScoreGrade);
    }

	    
    // 리스트 표시
    function callBackAutoScoreGrade(data){
    
    	var rowLength = 0;
    	if(data != null){
    		rowLength = data.length;
    	}
  	    
    	var InfoListObj = $("InfoList");
    	var objStr = "<table width='670' align='center' >";
	  	
    	
    	if(rowLength == 0){
    	    
    	       objStr += " \n<tr><td class='s_tab_view02' align=center>※ 등록된 평가등급이 없습니다. </td></tr>"
			   objStr += " \n<tr class='s_tab03'><td colspan='20'></td></tr>"
	  	}else{
	  	
    	    for(i=0;i<rowLength;i++){  
        
    			var scoreGradeDto = data[i];
    			
                var pCurriYear   = scoreGradeDto.curriYear;
                var pHakgiTerm   = scoreGradeDto.hakgiTerm;
                var pGradeCode   = scoreGradeDto.gradeCode;
                var pGradeName	 = scoreGradeDto.gradeName;
                var pGradePoint  = (scoreGradeDto.gradePoint).toFixed(1);
                var pFrPoint	 = (scoreGradeDto.frPoint).toFixed(1);
                var pToPoint	 = (scoreGradeDto.toPoint).toFixed(1);
                var pFrRate	     = (scoreGradeDto.frRate).toFixed(1);
                var pToRate	     = (scoreGradeDto.toRate).toFixed(1);
                var pUseYn		 = scoreGradeDto.useYn;
                
                var goEditUrl = pGradePoint ; 
                
                if(u_right=="true"){
                	goEditUrl = "<a href=\"javascript:goEdit('"+pCurriYear+"','"+pHakgiTerm+"','"+pGradeCode+"')\">"+pGradePoint+"</a>";
				}
                objStr += " \n<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">";
				objStr += " \n<td width=050 class='s_tab04_cen'>"+(i+1)+"</td>";
        		objStr += " \n<td width=1 ></td>";
        		objStr += " \n<td width=080 class='s_tab04_cen'>"+pGradeName+"</td>";
        		objStr += " \n<td width=1 ></td>";
        		objStr += " \n<td width=080 class='s_tab04_cen'>"+goEditUrl+"</td>";
        		objStr += " \n<td width=1 ></td>";
        		objStr += " \n<td width=150 class='s_tab04_cen'>"+pFrPoint +" ~ " +pToPoint+"</td>";
        		objStr += " \n<td width=1 ></td>";
        		objStr += " \n<td width=150 class='s_tab04_cen'>"+pFrRate  +" ~ " +pToRate+"</td>";
        		objStr += " \n<td width=1 ></td>";
        		objStr += " \n<td width=080 class='s_tab04_cen'>"+(pUseYn=="Y" ? "사용":"사용안함" )+"</td>";
        		objStr += " \n<td width=1 ></td>";
        		objStr += " \n<td width=080 class='s_tab04_cen'><a href=\"javascript:doDel('"+pCurriYear+"','"+pHakgiTerm+"','"+pGradeCode+"')\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_del.gif' width=36 height=18 align=absmiddle></a></td>";
                objStr += " \n</tr>";
				objStr += " \n<tr class='s_tab03'><td colspan='20'></td></tr>";
                
            }
        
	  	}
        
		objStr += "\n</table>";
		InfoListObj.innerHTML = objStr;
		InfoListObj.style.display = "block";
                     
     }
	
	function aaa(){
	 alert("aaa");   
	}

    ///////////////////
	// 평가등급 추가버튼 클릭시....
	function goAdd(){

        DWRUtil.setValue("pRegMode","W");
        Effect.Appear("infoWrite");
        
        initFormClear(document.Input);
        
    }
    
    /** 폼 클리어
     */
    function initFormClear(f){
	    
	    for (i = 0; i < f.elements.length; i++) {
            obj = eval(f.elements(i));
            if(obj.name != ""){
      
                if (obj.type == "text" ) obj.value ="";
            }   
        }
	}
	
    /** 저장할 때 
     */
    function submitCheck() {
		var f = document.Input;
		
		if(!validateAjax(f)) return false;
		
		vCurriYearTerm = DWRUtil.getValue("pCurriYearTerm").split("!");

		f.action = "/ScoreGrade.cmd?cmd=scoreGradeRegist&pCurriYear="+vCurriYearTerm[0]+"&pHakgiTerm="+vCurriYearTerm[1];
        f.target="hiddenFrame";
		f.submit();
	}
	
	
    /** 저장후 콜백..
     */
	function callbackAfterRegist(){

        autoScoreGradeReload();
    }
    
    
    /** 등급을 클릭시 수정데이타를 가져온다.
     */
	function goEdit(vCurriYear, vHakgiTerm, vGradeCode){
	    DWRUtil.setValue("pRegMode","E");

		ScoreGradeWork.scoreGradeListAuto(vCurriYear, vHakgiTerm, vGradeCode, callBackAutoScoreGradeShow);
	}

		
	/** 수정시 1건 조회하여 보여주기
	 */
    function callBackAutoScoreGradeShow(data){
        
        var f = document.Input;
		var rowLength = 0;
    	if(data != null){
    		rowLength = data.length;
    	}
 	    
    	if(rowLength > 0){
    	    
			var scoreGradeDto = data[0];
			
            var vCurriYear   = scoreGradeDto.curriYear;
            var vHakgiTerm   = scoreGradeDto.hakgiTerm;
            var vGradeCode   = scoreGradeDto.gradeCode;
            var vGradeName	 = scoreGradeDto.gradeName;
            var vGradePoint  = (scoreGradeDto.gradePoint).toFixed(1);
            var vFrPoint	 = (scoreGradeDto.frPoint).toFixed(1);
            var vToPoint	 = (scoreGradeDto.toPoint).toFixed(1);
            var vFrRate	     = (scoreGradeDto.frRate).toFixed(1);
            var vToRate	     = (scoreGradeDto.toRate).toFixed(1);
            var vUseYn		 = scoreGradeDto.useYn;
            
            DWRUtil.setValue("pCurriYear"   , vCurriYear    );
            DWRUtil.setValue("pHakgiTerm"   , vHakgiTerm    );
            DWRUtil.setValue("pGradeCode"   , vGradeCode    );
            DWRUtil.setValue("pGradeName"   , vGradeName    );
            DWRUtil.setValue("pGradePoint"  , vGradePoint   );
            DWRUtil.setValue("pFrPoint"     , vFrPoint      );
            DWRUtil.setValue("pToPoint"     , vToPoint      );
            DWRUtil.setValue("pFrRate"      , vFrRate       );
            DWRUtil.setValue("pToRate"	    , vToRate       );
          
            if(vUseYn=="Y" ) f.pUseYn[0].checked=true;
            else f.pUseYn[1].checked=true;
        
	  	}
        
        Effect.Appear("infoWrite");
                     
     }
     
     

    /** 삭제시..
     */
	function doDel(vCurriYear, vHakgiTerm, vGradeCode){
	    
	    if(d_right!="true")
	    { 
	    	alert("삭제 권한이 없습니다. \n\n관리자에게 문의 하세요");
	    	return;
	    }
	    
	    msg = "삭제시 복구가 불가능합니다. \n\n정말로 삭제하시겠습니까?";
	    
	    if(!confirm(msg)) return ;
	    
	    ScoreGradeWork.scoreGradeDeleteAjax(vCurriYear, vHakgiTerm, vGradeCode, callbackAfterDel);
    }
    
    
    /** 삭제후 콜백..
     */
	function callbackAfterDel(msg){
	    // 정상처리후에는 성공 메세지를 보여주지 않는다, 실패 메세지만 보여줌....(사용자가 번거로울 까봐..)

	    if(msg!="") alert(msg); 
        else autoScoreGradeReload();
    }
    
	    	 
    //취소 버튼 클릭시..
    function closeWin() {
        $("infoWrite").style.display = "none";
    }
	 

	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}	

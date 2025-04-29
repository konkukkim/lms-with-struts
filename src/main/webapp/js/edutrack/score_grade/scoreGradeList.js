	// ��������Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
    
	function init(systemCode, contextPath ) {
		this.SYSTEMCODE  = systemCode;
		this.CONTEXTPATH = contextPath;	

		autoScoreGradeReload();	
	}
 
    // ����Ʈ �ʱ�ȭ
    function autoScoreGradeReload(){
        
        $("infoWrite").style.display = "none";  // �򰡵�� �Է����� �����..

        viewProgress('InfoList','block',CONTEXTPATH, SYSTEMCODE,'Y');
        
		vCurriYearTerm = DWRUtil.getValue("pCurriYearTerm").split("!");

		ScoreGradeWork.scoreGradeListAuto(vCurriYearTerm[0], vCurriYearTerm[1], null, callBackAutoScoreGrade);
    }

	    
    // ����Ʈ ǥ��
    function callBackAutoScoreGrade(data){
    
    	var rowLength = 0;
    	if(data != null){
    		rowLength = data.length;
    	}
  	    
    	var InfoListObj = $("InfoList");
    	var objStr = "<table width='670' align='center' >";
	  	
    	
    	if(rowLength == 0){
    	    
    	       objStr += " \n<tr><td class='s_tab_view02' align=center>�� ��ϵ� �򰡵���� �����ϴ�. </td></tr>"
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
        		objStr += " \n<td width=080 class='s_tab04_cen'>"+(pUseYn=="Y" ? "���":"������" )+"</td>";
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
	// �򰡵�� �߰���ư Ŭ����....
	function goAdd(){

        DWRUtil.setValue("pRegMode","W");
        Effect.Appear("infoWrite");
        
        initFormClear(document.Input);
        
    }
    
    /** �� Ŭ����
     */
    function initFormClear(f){
	    
	    for (i = 0; i < f.elements.length; i++) {
            obj = eval(f.elements(i));
            if(obj.name != ""){
      
                if (obj.type == "text" ) obj.value ="";
            }   
        }
	}
	
    /** ������ �� 
     */
    function submitCheck() {
		var f = document.Input;
		
		if(!validateAjax(f)) return false;
		
		vCurriYearTerm = DWRUtil.getValue("pCurriYearTerm").split("!");

		f.action = "/ScoreGrade.cmd?cmd=scoreGradeRegist&pCurriYear="+vCurriYearTerm[0]+"&pHakgiTerm="+vCurriYearTerm[1];
        f.target="hiddenFrame";
		f.submit();
	}
	
	
    /** ������ �ݹ�..
     */
	function callbackAfterRegist(){

        autoScoreGradeReload();
    }
    
    
    /** ����� Ŭ���� ��������Ÿ�� �����´�.
     */
	function goEdit(vCurriYear, vHakgiTerm, vGradeCode){
	    DWRUtil.setValue("pRegMode","E");

		ScoreGradeWork.scoreGradeListAuto(vCurriYear, vHakgiTerm, vGradeCode, callBackAutoScoreGradeShow);
	}

		
	/** ������ 1�� ��ȸ�Ͽ� �����ֱ�
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
     
     

    /** ������..
     */
	function doDel(vCurriYear, vHakgiTerm, vGradeCode){
	    
	    if(d_right!="true")
	    { 
	    	alert("���� ������ �����ϴ�. \n\n�����ڿ��� ���� �ϼ���");
	    	return;
	    }
	    
	    msg = "������ ������ �Ұ����մϴ�. \n\n������ �����Ͻðڽ��ϱ�?";
	    
	    if(!confirm(msg)) return ;
	    
	    ScoreGradeWork.scoreGradeDeleteAjax(vCurriYear, vHakgiTerm, vGradeCode, callbackAfterDel);
    }
    
    
    /** ������ �ݹ�..
     */
	function callbackAfterDel(msg){
	    // ����ó���Ŀ��� ���� �޼����� �������� �ʴ´�, ���� �޼����� ������....(����ڰ� ���ŷο� ���..)

	    if(msg!="") alert(msg); 
        else autoScoreGradeReload();
    }
    
	    	 
    //��� ��ư Ŭ����..
    function closeWin() {
        $("infoWrite").style.display = "none";
    }
	 

	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}	

	// ��������Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var CURRICODE = "";	
	var CURRIYEAR = "";		
	var CURRITERM = "";		
    var COURSELISTSIZE = 1;
    var SCOREGUBUN = "";
    var TWIDTH = "";
    
	// global attribute
    var V_PAGING_YN = "Y";  // paging ó�� ����
    

	/** init
	 */
	function init(systemCode, contextPath, curriCode, curriYear, curriTerm, courseListsize, scoreGubun, pWidth ) {
		this.SYSTEMCODE  = systemCode;
		this.CONTEXTPATH = contextPath;	
        this.CURRICODE  = curriCode;	
        this.CURRIYEAR  = curriYear;		
        this.CURRITERM  = curriTerm;		
		this.COURSELISTSIZE = courseListsize;		
		this.SCOREGUBUN = scoreGubun;		
		this.TWIDTH = pWidth;		

        DWRUtil.setValue("pCurPage","1");
		autoReload();	
	}


	/** ��� refresh
	 ** param : val - ����¡ ��ȣ
	 */
    function autoReload(){
     
        viewProgress('listDiv','block',CONTEXTPATH, SYSTEMCODE,'Y');
        
        var vCourseId = DWRUtil.getValue("pCourseId");
        var vOp       = DWRUtil.getValue("pOp");
        var vSearch   = DWRUtil.getValue("pSearch");
        var vCurPage  = DWRUtil.getValue("pCurPage");
        var pOrderColm= DWRUtil.getValue("pOrderColm");
        
        ResultCourseWork.resultCourseListAuto(vCourseId, vOp, vSearch, vCurPage, V_PAGING_YN, pOrderColm, autoReloadCallback);	   
	}
    
    
    // ����
    function doOrder(columnNm, sID){
     
        DWRUtil.setValue("pOrderColm", columnNm);
        
        obj = $(sID);
        val = obj.innerText;
 
        if(val.indexOf("��")>0){
            obj.innerText = obj.innerText.replace("��","��");
            DWRUtil.setValue("pOrderColm", columnNm +" asc ");
        }else{
            obj.innerText = obj.innerText.replace("��","��");
            DWRUtil.setValue("pOrderColm", columnNm +" desc ");
        }
        autoReload();  
	}
	
   
	// ����¡ó��
	function goPage(page,type){
		if(page == null || page <= 0) return;
		DWRUtil.setValue("pCurPage",page);
		autoReload();
	}
	
	
	// ����Ʈ �ѷ��ֱ�
	function autoReloadCallback(data){
        var dataDto     ;
        var examCnt     ;
        var reportCnt   ;
        var forumCnt    ;
        var curriCourseInfo  ;
        var rowLength = 0;
        var paggingStr ;
         
		if(data != null) {
            dataDto    = data[0];
            examCnt     = data[1];
            reportCnt   = data[2];
            forumCnt    = data[3];
            curriSubCourseDto  = data[4];
            dataList  = dataDto.dataList;
            paggingStr = dataDto.pagging;

            if(dataList!=null) rowLength  = dataList.length;
		}
		
	  	var listObj = $("listDiv");
	  	var scoreRateObj = $("scoreRateDiv");   // ������ ����
	  	var cntScoreObj = $("cntScoreDiv");     // �Ǽ� �����ֱ�
	  	
	  	
	  	var scoreRateStr = "";
	  	var cntScoreStr  = "";

        if(curriSubCourseDto!=null){
            //scoreRateStr += "����  <font class=c_text01>"+ curriSubCourseDto.examBase   +"%</font> |";
            scoreRateStr += "����  <font class=c_text01>"+ curriSubCourseDto.reportBase +"%</font> |";
            scoreRateStr += "�⼮  <font class=c_text01>"+ curriSubCourseDto.attendBase +"%</font> |";
            scoreRateStr += "���  <font class=c_text01>"+ curriSubCourseDto.forumBase  +"%</font> |";
            scoreRateStr += "��Ÿ1 <font class=c_text01>"+ curriSubCourseDto.etc1Base   +"%</font> |";
            scoreRateStr += "��Ÿ2 <font class=c_text01>"+ curriSubCourseDto.etc2Base   +"%</font> |";
        }

        cntScoreStr += "���������&nbsp;&nbsp; ���� : �� <b>"+ reportCnt+" ��,</b> ��� : �� <b>"+ forumCnt+" ��</b>";
        //���� : �� <b>"+ examCnt +" ��,</b>

	  	scoreRateObj.innerHTML = scoreRateStr;
		scoreRateObj.style.display = "block";

	  	cntScoreObj.innerHTML = cntScoreStr;
		cntScoreObj.style.display = "block";
		
		
	  	var paggingObj = $("getPagging"); 
	  	var objStr = "<table width='670' align='center' >";
	  	
	  	// ����¡ ó��..
	  	if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}
        
        if(V_PAGING_YN =="N"){
            paggingObj.style.display = "none"; 
            $("searchDiv").style.display = "none";
        }else{
            $("searchDiv").style.display = "block";
        }



	  	if(rowLength == 0){
	  	    
	  	    $("buttDiv1").style.display = "none";
            $("buttDiv2").style.display = "none";
            $("buttDiv3").style.display = "none";
	  	    
	  	    var vCourseId = DWRUtil.getValue("pCourseId");
	  	    var msg = "";
	  	    
	  	    if(vCourseId =="")  msg = "�� ������ �����ϼ���.";
	  	    else msg = "��  ��ϵ� �������� �����ϴ�.";
	  	        
		  	objStr += "<tr><td class='s_tab04_cen'>"+msg+"</td></tr>"
			       + "<tr class='s_tab03'><td colspan='20'></td></tr>";
			       
			if(vCourseId =="")  msg = "�� ������ �����ϼ���.";


	  	}else{

            $("buttDiv1").style.display = "";
            $("buttDiv2").style.display = "";
            $("buttDiv3").style.display = "";

            
		    var totalCount = dataDto.totalDataCount;
            
		  	for(i=0;i<rowLength;i++){  
    			var resultCourseDto = dataList[i];
    			
				var courseId       = resultCourseDto.courseId    ;  
		    	var userName       = resultCourseDto.userName    ;   
		    	var userId         = resultCourseDto.userId      ;   
		    	var scoreExam      = resultCourseDto.scoreExam   ;   
		    	var scoreReport    = resultCourseDto.scoreReport ;   
		    	var scoreAttend    = resultCourseDto.scoreAttend ;   
		    	var scoreForum     = resultCourseDto.scoreForum  ;   
		    	var scoreEtc1      = resultCourseDto.scoreEtc1   ;   
		    	var scoreEtc2      = resultCourseDto.scoreEtc2   ;   
		    	var scoreTotal     = resultCourseDto.scoreTotal  ;   
		    	var getCredit      = resultCourseDto.getCredit;   
		    	var grade          = resultCourseDto.grade  ;   
                var enrollStatus   = resultCourseDto.enrollStatus  ;   


				objStr += "\n<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">";
                objStr +="\n	<td width=40 class=s_tab04_cen>"+ (totalCount--) +"</td>";
                objStr +="\n	<td></td>";
                objStr +="\n	<td width=130 class=s_tab04_cen>"+ userName +"("+ userId +")</td>";
                objStr +="\n	<td></td>";
//                objStr +="\n	<td width='"+TWIDTH+"' class=s_tab04_cen>"+ scoreExam +"</td>";
//                objStr +="\n	<td></td>";
                objStr +="\n	<td width='"+TWIDTH+"' class=s_tab04_cen>"+ scoreReport +"</td>";
                objStr +="\n	<td></td>";
                objStr +="\n	<td width='"+TWIDTH+"' class=s_tab04_cen>"+ scoreAttend +"</td>";
                objStr +="\n	<td></td>";
                objStr +="\n	<td width='"+TWIDTH+"' class=s_tab04_cen>"+ scoreForum +"</td>";
                objStr +="\n	<td></td>";
                objStr +="\n	<td width='"+TWIDTH+"' class=s_tab04_cen>"+ scoreEtc1 +"</td>";
                objStr +="\n	<td></td>";
                objStr +="\n	<td width='"+TWIDTH+"' class=s_tab04_cen>"+ scoreEtc2 +"</td>";
                objStr +="\n	<td></td>";
                objStr +="\n	<td width='"+TWIDTH+"' class=s_tab04_cen>"+ scoreTotal +"</td>";
                objStr +="\n	<td></td>";
                if(SCOREGUBUN=="1"){ // ����ó��
                objStr +="\n	<td width='"+TWIDTH+"' class=s_tab04_cen>"+ grade +"</td>";
                objStr +="\n	<td></td>";
                objStr +="\n	<td width='"+TWIDTH+"' class=s_tab04_cen>"+ getCredit +"</td>";
                objStr +="\n	<td></td>";
                }
				objStr +="\n	<td width=50 class=s_tab04_cen><input type=checkbox name=pStudentIds value=\""+userId+"\"></td>";
                objStr +="\n</tr>";
                											
				objStr +="\n<tr class='s_tab03'><td colspan='30'></td></tr>";
  	
			} // end of for
			
		}
		
		objStr += "\n</table>";
		listObj.innerHTML = objStr;
		listObj.style.display = "block";
		
		DWRUtil.setValue("idLength", rowLength);

	 }  


	/** ������ �����..
	 */
	function changeCourseId(courseId)
	{
        DWRUtil.setValue("pSearch","");
        DWRUtil.setValue("pCurPage","1");
		autoReload();
	}

	/** �������� ����
	 */
	function ratio_win(){
	    
	    var vCourseId = DWRUtil.getValue("pCourseId");
	    
		var page=CONTEXTPATH+"/CurriSubCourse.cmd?cmd=curriSubCourseEdit&pCurriCode="+CURRICODE+"&pCurriYear="+CURRIYEAR+"&pCurriTerm="+CURRITERM+"&pCourseId="+vCourseId+"&pMode=Lecture";
		editWin = window.open(page,"courseWin","width=500,height=300,left=0,top=0,resizable=no,scrollbars=1");
		editWin.focus();
	}

	/** ����ó���Ϸ�
	 */
	function scoreAutoSet(setTarget)
	{
	    var vCourseId = DWRUtil.getValue("pCourseId");

		var str = CONTEXTPATH+"/ResultCourse.cmd?cmd=resultCourseScoreAutoSetFrame&pCourseId="+vCourseId+"&pSetTarget="+setTarget;
		ShowInfo = window.open(str,"scoreWin","toolbar=0,scrollbars=yes,directories=0,status=1,menubar=0,left=100,top=100,resizable=yes,width=600,height=400");
	}
	
	
	/** ������ ����� ��ü ������ �Ҷ�
	 */
	function goAllList(){
	    
		if(V_PAGING_YN=="Y"){
		    V_PAGING_YN = "N";
            $("changBut").src = CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_grade06.gif";
		} else {
		    V_PAGING_YN = "Y";
            $("changBut").src = CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_grade05.gif";
        }
    
        DWRUtil.setValue("pSearch", "");
        DWRUtil.setValue("pCurPage","1");
		autoReload();
	}


	/** �������ε�
	 */
	function scoreUpload()
	{
	    var vCourseId = DWRUtil.getValue("pCourseId");

		var str = CONTEXTPATH+"/ResultCourse.cmd?cmd=resultCourseScoreUploadFrame&pCourseId="+vCourseId;
		ShowInfo = window.open(str,"scoreWin","toolbar=0,scrollbars=yes,directories=0,status=1,menubar=0,left=100,top=100,resizable=yes,width=600,height=380");
	}


	/** ���� ���� �ٿ�ε�
	 */
	function excelDown(){
	    var vCourseId = DWRUtil.getValue("pCourseId");

		document.location = CONTEXTPATH+"/ResultCourse.cmd?cmd=resultCourseExcelDown&pCourseId="+vCourseId;
	}

	/** �����Է� �˾�
	 */
	function score_win()
	{
		ScoreWin = window.open(CONTEXTPATH+"/html/blank.html", "openwin", "left=0,top=0,toolbar=0,scrollbars=yes,directories=0,status=yes,menubar=0,resizable=yes,width=570,height=600");
	}
	
	/**
	 */
	function result_check()
	{
		var f = document.f;
		var checkVal = getChkBoxByValue(f, "pStudentIds", "");
		if(checkVal == "") {
			alert("��� �������� �����ϼ���.")
			return;
		}

		var yes = confirm("������ �������� ������ ó�� �մϴ�. \n\n�����Ͻðڽ��ϱ�?");
		if(yes == true)
		{
			score_win();
			f.target = 'openwin';
			f.pSelect.value = 'SEL';
			f.idLength.value = '1';
			//f.action=CONTEXTPATH+"/ResultCourse.cmd?cmd=resultCourseScoreWrite";
			f.action=CONTEXTPATH+"/ResultCourse.cmd?cmd=resultCourseScoreRegist";
			f.submit();
		}
		else return;
	}

	function result_all()
	{
		var f = document.f;
		var yes = confirm("�������� ��� �������� ������ ó�� �մϴ�.\n\n�����Ͻðڽ��ϱ�?");
		if(yes == true)
		{
			score_win();
			f.target = 'openwin';
			f.pSelect.value = 'ALL';
			//f.action=CONTEXTPATH+"/ResultCourse.cmd?cmd=resultCourseScoreWrite";
			f.action=CONTEXTPATH+"/ResultCourse.cmd?cmd=resultCourseScoreRegist";
			f.submit();
		}
		else return;
	}

	
	/** ����ó���Ϸ�
	 */
	function CompleteScore(){
	     var vCourseId = DWRUtil.getValue("pCourseId");

		if(confirm('��� �л��� �� ó���� �����ϼž� �մϴ�. \n\n���� �մϱ�?')){
		    document.hiddenFrame.location = CONTEXTPATH+"/ResultCourse.cmd?cmd=resultCourseCompleteScore&pCourseId="+vCourseId;
		}
	}


	/** �˻�
	 */
	function goSearch() {
	    DWRUtil.setValue("pCurPage","1");
		autoReload();
	}
	


	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}	

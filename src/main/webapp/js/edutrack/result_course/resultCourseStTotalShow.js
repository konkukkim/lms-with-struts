	// ��������Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";


	/** init
	 */
	function init(systemCode, contextPath ) {
		this.SYSTEMCODE  = systemCode;
		this.CONTEXTPATH = contextPath;	

		autoReload();	
	}


	/** ��� refresh
	 ** param : val - ����¡ ��ȣ
	 */
    function autoReload(){
     
        viewProgress('listDiv','block',CONTEXTPATH, SYSTEMCODE,'Y');
        
        var pCurriYearTerm = DWRUtil.getValue("pCurriYearTerm").split("!");
        
        ResultCourseWork.resultCourseStTotalShowAuto(pCurriYearTerm[0], pCurriYearTerm[1], autoReloadCallback);	   
	}
    
	
	// ����Ʈ �ѷ��ֱ�
	function autoReloadCallback(data){

		var rowLength = 0;
 		if(data != null) {
            rowLength  = data.length;
		}
		
	  	var listObj = $("listDiv");
		var objStr = "<table width='670' align='center' border=0>";
  	
	  	if(rowLength<=0){
	  		
	  		objStr += "<tr><td class='s_tab04_cen'>�� ����ó���� �Ϸ���� �ʾҽ��ϴ�.</td></tr>"
			       + "<tr class='s_tab03'><td colspan='20'></td></tr>";
			 
	  	} else {
	  			
			objStr += "<tr class=s_tab02 height=50> ";
			objStr += "	<td width=180>����</td>";
			objStr += "	<td class=s_tablien></td>";
			objStr += "	<td width=300 colspan=3>������</td>";
			objStr += "	<td class=s_tablien></td>";
			objStr += "	<td width=170>������</td>";
			objStr += "</tr>";
			objStr += "<tr class=s_tab03>";
			objStr += "	<td colspan=11></td>";
			objStr += "</tr>";
	  			
	        for(i=0; i<rowLength;i++){
	        	
        		resultCourseDto = data[i];
        		
        		
				objStr +=" <tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\"> ";
				objStr +=" 	<td class=s_tab04_cen rowspan=9><b>"+ resultCourseDto.curriName +"</b></td>";
				objStr +=" 	<td rowspan=9></td>";
				objStr +=" 	<td class=s_tab04_cen>�⼮</td>";
				objStr +=" 	<td></td>";
				objStr +=" 	<td class=s_tab04_cen>"+ resultCourseDto.scoreAttend +"</td>";
				objStr +=" 	<td rowspan=9>"+ resultCourseDto.grade +"</td>";
				objStr +=" </tr>";
				objStr +=" <tr class=s_tab03>";
				objStr +=" 	<td colspan=3></td>";
				objStr +=" </tr>";
				objStr +=" <tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">";
				objStr +=" 	<td class=s_tab04_cen>����Ʈ</td>";
				objStr +=" 	<td></td>";
				objStr +=" 	<td class=s_tab04_cen>"+ resultCourseDto.scoreReport +"</td>";
				objStr +=" </tr>";
				objStr +=" <tr class=s_tab03>";
				objStr +=" 	<td colspan=3></td>";
				objStr +=" </tr>";
				objStr +=" <tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">";
				objStr +=" 	<td class=s_tab04_cen>���</td>";
				objStr +=" 	<td></td>";
				objStr +=" 	<td class=s_tab04_cen>"+ resultCourseDto.scoreForum +"</td>";
				objStr +=" </tr>";
				objStr +=" <tr class=s_tab03>";
				objStr +=" 	<td colspan=3></td>";
				objStr +=" </tr>";
				objStr +=" <tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">";
				objStr +=" 	<td class=s_tab04_cen>�׷��н�</td>";
				objStr +=" 	<td></td>";
				objStr +=" 	<td class=s_tab04_cen>"+ resultCourseDto.scoreEtc1 +"</td>";
				objStr +=" </tr>";
				objStr +=" <tr class=s_tab03>";
				objStr +=" 	<td colspan=3></td>";
				objStr +=" </tr>";
				objStr +=" <tr class=s_tab03>";
				objStr +=" 	<td class=s_tab04_cen>����</td>";
				objStr +=" 	<td></td>";
				objStr +=" 	<td class=s_tab04_cen>"+ resultCourseDto.scoreTotal +"</td>";
				objStr +=" </tr>";
				objStr +=" <tr class=s_tab03>";
				objStr +=" 	<td colspan=11></td>";
				objStr +=" </tr>";
	        }
	
	    }
		objStr += "\n</table>";
		
		listObj.innerHTML = objStr;
		listObj.style.display = "block";
		
	 }  




	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}	

	// ��������Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var COLUMNCNT  = 4  ;  // �ÿ���.....���Ŀ� 10���� ������...

	function init(systemCode, contextPath) {
		this.SYSTEMCODE  = systemCode;
		this.CONTEXTPATH = contextPath;	

        DWRUtil.setValue("pageNo","1");
        
		autoReload();	
	}

    	 
    // ����Ʈ �ʱ�ȭ
    function autoReload(){
        viewProgress('attendList','block',CONTEXTPATH, SYSTEMCODE,'Y');
        prevnextButtonCtl("none", "none");
       
        vCourseId = DWRUtil.getValue("pCourseId");
        vPageNo   = DWRUtil.getValue("pageNo");

    	OfflineAttendWork.offlineAttendListAuto(vCourseId, vPageNo, COLUMNCNT, autoReloadList);
    }

    
    // ����Ʈ ǥ��
    function autoReloadList(data){
    
    	var rowLength = 0;
    	var allItemCnt = 0;
    	var totColumn = COLUMNCNT+2;
    	
    	if(data != null){
    		rowLength  = data.length;
    		allItemCnt = data.length;
    	}
   	
    	var attendList = $("attendList");
    	var objStr = "";

  	
    	if(rowLength == 0){
    	    
		  	objStr += "<table width='670' align='center' valign=top>"
		  	        + "<tr class=s_tab01><td colspan=2></td></tr>"
                    + "<tr><td class='s_tab_view02' align=center>�� ��ȸ�� ����Ÿ�� �����ϴ�.</td></tr>"
			        + "<tr class='s_tab03'><td colspan='20'></td></tr>"
			        + "</table>";
	  	}else{
	  	
	  	    if(rowLength>0){  
              

                var colTitle   = data[0];   // �⼮����(����κ�)..arrayl
                var arrColList = data[1];   // ����Ÿ �κ�  .. arrayList
                var arrKeyList = data[2];   // contents id ( key ) ...arrayList
                var pageCnt    = data[3];   // �� ������ �� ... array
              
                var colCnt = colTitle.length;
                var rowCnt = arrColList.length;
                
                var tbWidth = 500 / Number(COLUMNCNT);
                objStr += "<table width=670 align=center border=0>";
                
                // Title
                if(colCnt>0){
                     
                    objStr += "\n<tr class=s_tab01>";
                    objStr += "\n<td colspan=30></td>";
                    objStr += "\n</tr>";
                    objStr += "\n<tr class=s_tab02>";
                    objStr += "\n	<td width=050>��ȣ</td>";
                    objStr += "\n	<td class=s_tablien></td>";
                    objStr += "\n	<td width=120>�̸�(ID)</td>";
                    
                    // attend Date Area
                    for(j=2;j<colCnt;j++)
                    {
                 
                        objStr += "\n    <td class=s_tablien></td>";
                        objStr += "\n    <td width="+tbWidth+" title='"+colTitle[j] +"'><a href=\"#\" onmousedown=\"setValueObj('"+(j-2)+"', '');showContextMenu(cMenu0)\">"+(colTitle[j].length>5 ? colTitle[j].substring(5): colTitle[j])  +"</a></td>";
                        objStr += "\n	 <input type=hidden name='contsId_"+(j-2)+"' value=\""+arrKeyList[j]+"\">";
                        
                    }
                    
                    // blank Area
                    
                    for(i=0;i<(totColumn-colCnt);i++){
                        objStr += "\n    <td class=s_tablien></td>";
                        objStr += "\n    <td width="+tbWidth+"></td>";
                    } 
                    objStr += "\n</tr>";
                }

                // List
                for(i=0;i<rowCnt; i++){
                    dataList = arrColList[i];
                
                    objStr += "\n<tr class=s_tab03>";
                    objStr += "\n	<td colspan=30></td>";
                    objStr += "\n</tr>";
                    objStr += "\n<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">";
                    objStr += "\n	<td class=s_tab04_cen>"+(i+1) +"</td>";
                    objStr += "\n	<td></td>";
                    objStr += "\n	<td class=s_tab04_cen>"+dataList[1]+"("+dataList[0]+")<input type=hidden name='stuId' value=\""+dataList[0]+"\"></td>";
                    
                    // data Area
                    for(j=2;j<colCnt; j++){
                        objStr += "\n	<td></td>";
                        objStr += "\n	<td class=s_tab04_cen>";
                        objStr += "\n	    <a href=\"#\" onmousedown=\"setValueObj('"+(j-2)+"', '"+i +"');showContextMenu(cMenu1);\"><span id=\"id_"+(j-2)+"\">"+ (dataList[j]==null? "���⼮" : dataList[j]) +"</span></a>";
                        objStr += "\n	</td>";
                    }
                    
                    // blank Area
                    for(j=0;j<(totColumn-colCnt);j++){
                        objStr += "\n    <td></td>";
                        objStr += "\n	<td class=s_tab04_cen></td>";
                    }
                    objStr += "\n</tr>";
                } // end for
                
                if(rowCnt==0){
                	objStr += "<table width='670' align='center' valign=top>"
			  	           + "<tr class=s_tab01><td colspan=2></td></tr>"
	                       + "<tr><td class='s_tab_view02' align=center>�� ��ȸ�� ����Ÿ�� �����ϴ�.</td></tr>"
				           + "<tr class='s_tab03'><td colspan='20'></td></tr>"
				           + "</table>";
				}

                objStr += "\n<tr class=s_tab05>";
                objStr += "\n	<td colspan=30></td>";
                objStr += "\n</tr>";
                objStr += "\n</table>";
                
            }
            
	  	}

		attendList.innerHTML = objStr;
		attendList.style.display = "block";
		
		// ����, ���� ��ư ��Ʈ��...
		var calPage = Number(pageCnt[1])-Number(pageCnt[0]);  // ��ü ������ - ����������
	
        if(Number(pageCnt[0])==1 && calPage>0 ){
		    prevnextButtonCtl("none", "");    
		} else if(Number(pageCnt[0])>1 && calPage==0 ){
		    prevnextButtonCtl("", "none");    
		} else if(calPage==0){
		    prevnextButtonCtl("none", "none");    
		} else {
		    prevnextButtonCtl("", "");    
		}

        
     }
	 

    /** ���� ��ư�� ��Ʈ�� �Ѵ�.
     */
    function prevnextButtonCtl(val1, val2){
		var prevBut = $("prevBut");
		var nextBut = $("nextBut");
		
		prevBut.style.display = val1;
		nextBut.style.display = val2;
    }
    
    
    // global attribute
    var objSpanNum ;
    var attIdOrder ;
    
    /** ���������� �����Ѵ�..
     */
    function setValueObj(objNm, orderNum){
        objSpanNum = objNm ;
        attIdOrder = orderNum;
    }
    

    // ���� �����..
	function chgCourseId() {
        DWRUtil.setValue("pageNo","1");
        
		autoReload();	
	}
	 
	 

    /** ����, ���� ��ư Ŭ����..
     */
    function goNext(gubun){
        
        var vPageNo = DWRUtil.getValue("pageNo");
        
        if(gubun=="P"){
            vPageNo = Number(vPageNo)-1;
        }else{
            vPageNo = Number(vPageNo)+1;
        }
        
        DWRUtil.setValue("pageNo",vPageNo);
        
        autoReload();
        
    }
    

    /** �⼮��带 �����Ѵ�..
     */
    function replaceAttend(val,gubun){
       
        objSpanName = eval("document.all.id_"+objSpanNum);

        if(objSpanName==null){
            alert("��ȸ�� ����Ÿ�� �����ϴ�.");
            return;
        }

        len = objSpanName.length;   // length

        // 1�� �ۿ� �������..
        if(len==null){
            objSpanName.innerText = val; 
            return;
        }
        
        // �Ѱ��� ����Ÿ�� �����
        if(gubun==""){
            objSpanName[attIdOrder].innerText = val ;
            return;
        }
        
        // ��ü�� ����Ÿ �����
        for(i=0; i<len; i++){
            objSpanName[i].innerText = val;
        }
    }
    


    /** ����Ŭ����..
     */
    function saveAttend(){
        var vCourseId = DWRUtil.getValue("pCourseId");
        var vConId  = "";
        var vAttSt  = "";
        var vStuId  = "";
        
        objSpanName = eval("document.all.id_"+objSpanNum);
        objStuId    = document.f.stuId ;
        objConId    = eval("document.f.contsId_"+objSpanNum);
        
        if(objSpanName==null){
            alert("������ ����Ÿ�� �����ϴ�.");
            return;
        }

        len = objSpanName.length;   // length

        // 1�� �ۿ� �������..
        if(len==null){
            vStuId+= objStuId.value ; 
            vAttSt+= objSpanName.innerText ; 
            
        }

        
        // ��ü�� ����Ÿ �����
        for(i=0; i<len; i++){
            if(i>0) {
                vStuId += "!";  // ������..
                vAttSt += "!";
            }
            vStuId+= objStuId[i].value ; 
            vAttSt+= objSpanName[i].innerText ;
        }
        
        vConId     = objConId.value ; 

        
        OfflineAttendWork.offlineAttendAllRegist(vCourseId, vStuId, vAttSt, vConId, callbackAfterRegist);
    }
    

    /** ������ �ݹ� �޼ҵ�..
     */
    function callbackAfterRegist(msg){
        alert( msg );
        autoReload()
    }


	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}	

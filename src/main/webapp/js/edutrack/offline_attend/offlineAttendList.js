	// 목차리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var COLUMNCNT  = 4  ;  // 시연용.....향후에 10개로 조정요...

	function init(systemCode, contextPath) {
		this.SYSTEMCODE  = systemCode;
		this.CONTEXTPATH = contextPath;	

        DWRUtil.setValue("pageNo","1");
        
		autoReload();	
	}

    	 
    // 리스트 초기화
    function autoReload(){
        viewProgress('attendList','block',CONTEXTPATH, SYSTEMCODE,'Y');
        prevnextButtonCtl("none", "none");
       
        vCourseId = DWRUtil.getValue("pCourseId");
        vPageNo   = DWRUtil.getValue("pageNo");

    	OfflineAttendWork.offlineAttendListAuto(vCourseId, vPageNo, COLUMNCNT, autoReloadList);
    }

    
    // 리스트 표시
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
                    + "<tr><td class='s_tab_view02' align=center>※ 조회된 데이타가 없습니다.</td></tr>"
			        + "<tr class='s_tab03'><td colspan='20'></td></tr>"
			        + "</table>";
	  	}else{
	  	
	  	    if(rowLength>0){  
              

                var colTitle   = data[0];   // 출석일자(제목부분)..arrayl
                var arrColList = data[1];   // 데이타 부분  .. arrayList
                var arrKeyList = data[2];   // contents id ( key ) ...arrayList
                var pageCnt    = data[3];   // 총 페이지 수 ... array
              
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
                    objStr += "\n	<td width=050>번호</td>";
                    objStr += "\n	<td class=s_tablien></td>";
                    objStr += "\n	<td width=120>이름(ID)</td>";
                    
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
                        objStr += "\n	    <a href=\"#\" onmousedown=\"setValueObj('"+(j-2)+"', '"+i +"');showContextMenu(cMenu1);\"><span id=\"id_"+(j-2)+"\">"+ (dataList[j]==null? "미출석" : dataList[j]) +"</span></a>";
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
	                       + "<tr><td class='s_tab_view02' align=center>※ 조회된 데이타가 없습니다.</td></tr>"
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
		
		// 이전, 다음 버튼 컨트롤...
		var calPage = Number(pageCnt[1])-Number(pageCnt[0]);  // 전체 페이지 - 현재페이지
	
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
	 

    /** 다음 버튼을 컨트롤 한다.
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
    
    /** 전역변수를 설정한다..
     */
    function setValueObj(objNm, orderNum){
        objSpanNum = objNm ;
        attIdOrder = orderNum;
    }
    

    // 과목 변경시..
	function chgCourseId() {
        DWRUtil.setValue("pageNo","1");
        
		autoReload();	
	}
	 
	 

    /** 이전, 다음 버튼 클릭시..
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
    

    /** 출석모드를 변경한다..
     */
    function replaceAttend(val,gubun){
       
        objSpanName = eval("document.all.id_"+objSpanNum);

        if(objSpanName==null){
            alert("조회된 데이타가 없습니다.");
            return;
        }

        len = objSpanName.length;   // length

        // 1건 밖에 없을경우..
        if(len==null){
            objSpanName.innerText = val; 
            return;
        }
        
        // 한개의 데이타만 변경시
        if(gubun==""){
            objSpanName[attIdOrder].innerText = val ;
            return;
        }
        
        // 전체의 데이타 변경시
        for(i=0; i<len; i++){
            objSpanName[i].innerText = val;
        }
    }
    


    /** 저장클릭시..
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
            alert("저장할 데이타가 없습니다.");
            return;
        }

        len = objSpanName.length;   // length

        // 1건 밖에 없을경우..
        if(len==null){
            vStuId+= objStuId.value ; 
            vAttSt+= objSpanName.innerText ; 
            
        }

        
        // 전체의 데이타 변경시
        for(i=0; i<len; i++){
            if(i>0) {
                vStuId += "!";  // 구분자..
                vAttSt += "!";
            }
            vStuId+= objStuId[i].value ; 
            vAttSt+= objSpanName[i].innerText ;
        }
        
        vConId     = objConId.value ; 

        
        OfflineAttendWork.offlineAttendAllRegist(vCourseId, vStuId, vAttSt, vConId, callbackAfterRegist);
    }
    

    /** 저장후 콜백 메소드..
     */
    function callbackAfterRegist(msg){
        alert( msg );
        autoReload()
    }


	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}	

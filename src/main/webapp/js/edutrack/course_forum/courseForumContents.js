	// ��������Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var COURSE_ID = "";	
	var FORUM_ID = "";	

	// global attribute
    var MANAGE_COURSE_ID = "";
    var MANAGE_FORUM_ID = "";
    
	function init(systemCode, contextPath, courseId, forumId ) {
		this.SYSTEMCODE  = systemCode;
		this.CONTEXTPATH = contextPath;	
		this.COURSE_ID   = courseId;	
		this.FORUM_ID   = forumId;	

		autoReload();	
	}


    function autoReload(){
		viewProgress('courseForumContentsList','block',CONTEXTPATH,SYSTEMCODE,'Y');

        var curPage = DWRUtil.getValue("curPage");
        var pSearchKey = DWRUtil.getValue("pSearchKey");
        var pKeyWord = DWRUtil.getValue("pKeyWord");

        CourseForumContentsWork.courseForumContentsPagingListAjax(curPage, COURSE_ID, FORUM_ID, pSearchKey, pKeyWord, autoReloadCallback);	   
	}
	

	// ����¡ó��
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
		autoReload();
	}


	// ����Ʈ �ѷ��ֱ�
	function autoReloadCallback(data){
		var rowLength = 0;
	  	var paggingStr = data.pagging;
	  	var dataList = data.dataList;
		if(dataList != null) rowLength = dataList.length;
		     
	  	//viewAutoButton('C',rowLength);
	  	var forumListObj = $("courseForumContentsList");
	  	var paggingObj = $("getPagging"); 
	  	var objStr = "<table width='670' align='center'>";
	  	
	  	// ����¡ ó��..
	  	if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

	  	if(rowLength == 0){
		  	objStr += "<tr><td class='s_tab04_cen'>�� ��ϵ� �Խñ��� �����ϴ�. </td></tr>"
			       + "<tr class='s_tab03'><td colspan='20'></td></tr>";
	  	}else{

		  	//var no = 0;
            var totalCount = data.totalDataCount;
            
		  	for(i=0;i<rowLength;i++){  
    			var courseForumContentsDto = dataList[i];
    			
		    	var forumId      = courseForumContentsDto.forumId;   
		    	var seqNo        = courseForumContentsDto.seqNo;   
		    	var subject      = courseForumContentsDto.subject;   
		    	var contents     = courseForumContentsDto.contents;   
		    	var contentsType = courseForumContentsDto.contentsType;
		    	var regId        = courseForumContentsDto.regId;   
		    	var regName      = courseForumContentsDto.regName;   
		    	var regDate      = courseForumContentsDto.regDate;   
		    	var hitNo        = courseForumContentsDto.hitNo;
	    	    var viewNo  = 0;
	    	    

                // �������ϰ�� ��ȣ���� * �� �����ش�..
                if(contentsType=="N")  viewNo = "*";
                else  viewNo = (totalCount--) ;
											
				objStr += " \n<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
                        + " \n<td width=070 class='s_tab04_cen'>"+viewNo+"</td>"
    			        + " \n<td width=1 ></td>"
    					+ " \n<td width=330 class='s_tab04_cen'><a href=\"javascript:goShow('"+seqNo+"')\">"+subject+"</a></td>"
    					+ " \n<td width=1 ></td>"
    					+ " \n<td width=100 class='s_tab04_cen'>"+regName+"</td>"
    					+ " \n<td width=1 ></td>"
    					+ " \n<td width=100 class='s_tab04_cen'>"+regDate+"</td>"
    					+ " \n<td width=1 ></td>"
    					+ " \n<td width=070 class='s_tab04_cen'>"+hitNo+"</td>"
            			+ " \n</tr>"
			 	        + " \n<tr class='s_tab03'><td colspan='20'></td></tr>";
  	
			} // end of for
			
		}
		objStr += "\n</table>";	
		forumListObj.innerHTML = objStr;
		forumListObj.style.display = "block";
	 }

    // searching
	function goSearchList()
	{
		DWRUtil.setValue("curPage","1");
		autoReload()
	}

    // ���ȭ������ �̵�..
	function goShow(seqNo)
	{
		var curPage = DWRUtil.getValue("curPage");
		
		document.location = CONTEXTPATH+"/CourseForumContents.cmd?cmd=courseForumContentsShow&pMode=Lecture&pCourseId="+COURSE_ID+"&pForumId="+FORUM_ID+"&pSeqNo="+seqNo+"&curPage="+curPage;
	}

	
    // ���ȭ������ �̵�..
	function goAdd()
	{
		document.location = CONTEXTPATH+"/CourseForumContents.cmd?cmd=courseForumContentsWrite&pMode=Lecture&pCourseId="+COURSE_ID+"&pForumId="+FORUM_ID;
	}

    // ��и������ �̵�..
	function goForumList()
	{
		document.location = CONTEXTPATH+"/CourseForumInfo.cmd?cmd=courseForumInfoPagingList&pMode=Lecture&pCourseId="+COURSE_ID+"&pForumId="+FORUM_ID;
	}

	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}	


////function goVoiceAdd()
////{
////	var f = document.f;
////	var pCourseId	= 	f.pCourseId.value;
////	var pForumId	=	f.pForumId.value;
////	document.location = "<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsVoiceWrite&pCourseId="+pCourseId+"&pForumId="+pForumId;
////}

	// 목차리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var GUBUN = "1";

	// global attribute
    var MANAGE_FORUM_ID = "";

	function init(systemCode, contextPath, pGubun ) {
		this.SYSTEMCODE  = systemCode;
		this.CONTEXTPATH = contextPath;
		this.GUBUN = pGubun;

		autoReload(pGubun);
	}


    function autoReload(pGubun){

        MainWork.stuCurriListAuto(pGubun, autoReloadCallback);
	}

	// 수강중인과정 리스트 뿌려주기
	function autoReloadCallback(dataArr){

		var rowLength = 0;
	  	var arrLength = 0;
	  	var cateRs = 0;
	  	var curriRsAll = 0;

		if(dataArr != null) arrLength = dataArr.length;

		if(arrLength==2){
		    cateRs = dataArr[0];
		    curriRsAll = dataArr[1];
        }

        var totalLength	= 0;
//alert('check01');
		for(i=0;i<curriRsAll.length;i++) {
            viewProgress("InfoList"+cateRs[i],'block',CONTEXTPATH, SYSTEMCODE, 'Y', '32');		//로딩하는 동안 꽃이 회전하는 효과

            var listObj = $("InfoList"+cateRs[i]);
            var objStr = "<table width='670' align='center' border='0' >";

            var nameObj = $("InfoName"+cateRs[i]);
            var nObjStr = "<table width='100%' border='0'>";

            var data = curriRsAll[i];
            rowLength = data.length;

            totalLength = rowLength + totalLength;

    	  	if(rowLength == 0){
//alert('rowLength == 0');
    	  	} else {

    	  		for(k=0;k<rowLength;k++){
        			var studentDto = data[k];
    				var curriCode   = studentDto.curriCode;
    		    	var curriYear   = studentDto.curriYear;
    		    	var curriTerm   = studentDto.curriTerm;
    		    	var curriType   = studentDto.curriType;
    		    	var curriName   = studentDto.curriName;
    		    	var servicestartDate= studentDto.servicestartDate;
    			    var serviceendDate = studentDto.serviceendDate;
    		    	var credit      = studentDto.credit;
    		    	var paymentIdx  = studentDto.paymentIdx;
    		    	var firstCon    = studentDto.firstCon;   // use flag
    		    	var enrollStats = studentDto.enrollStats;
    		    	var enrollNo    = studentDto.enrollNo;

    		    	var reportCnt	= studentDto.reportCnt;
    		    	var examCnt		= studentDto.examCnt;
    		    	var forumCnt	= studentDto.forumCnt;
    		    	var contentsCnt = studentDto.contentsCnt;
    		    	var showContentsCnt = studentDto.showContentsCnt;
    		    	var showTime	= studentDto.showTime;
    		    	var totalTime	= studentDto.totalTime;
    		    	var curriPercent = studentDto.curriPercent;

    		    	var cateName = studentDto.cateName;
    		    	var serviceStart = studentDto.serviceStart;
    		    	var curriInfo = studentDto.curriInfo;

    			    var chungEndYn = studentDto.chungendYn;		//청강기간이 끝났는지의 여부
    		    	var openCnt = studentDto.openCnt;
    		    	var offTotalTime = studentDto.offTotalTime;
    		    	var offShowTime = studentDto.offShowTime;
    		    	var offContentsCnt = studentDto.offContentsCnt;
    		    	var offAttendCnt = studentDto.offAttendCnt;


    		    	if(firstCon != "completing" && k == 0) {
    		    		nObjStr += "<tr><td class='s_tabtit'><img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/blet01.gif\" width=\"18\" height=\"16\" align=\"absmiddle\"><b> "+cateName+"</b></td></tr>";
    		    	}

    		    	if(GUBUN=="1") {

    		    		if(firstCon != "completing") {
//alert('============ gubun01 : check01');
    		    			var cancelLink = "";
		       			    var lectureLink = CONTEXTPATH+"/LectureMain.cmd?cmd=goLecture&pMode=Lecture&MENUNO=0&pCurriCode="+ curriCode +"&pCurriYear="+ curriYear +"&pCurriTerm="+ curriTerm +"&pCurriType="+curriType+"&pCurriName="+curriName;
		           			var enrollConfirmLink = "<a href=\"javascript:Enrollconfirm('"+curriCode+"','"+curriYear+"','"+curriTerm+"','"+ curriType +"','"+ paymentIdx +"','confirm')\">수강확인증</a>";

		           			if(firstCon=="before") {
		           			    lectureLink = "javascript:alert('강의 시작 전 입니다');";
		           			    enrollConfirmLink = "<a href=\"javascript:Enrollconfirm('"+curriCode+"','"+curriYear+"','"+curriTerm+"','"+ curriType +"','"+ paymentIdx +"','enroll')\">신청확인증</a>";
							}
		           			else if(firstCon=="completing") lectureLink = "javascript:alert('강의 종료되었습니다 \n\n 수료 처리 후 지난 과정에서 보실 수 있습니다.');";
		           			else if(firstCon=="after") cancelLink = "javascript:alert('강의 종료되었습니다 \n\n 수강 취소가 불가 합니다.');";

	        			    if (credit!=0) {
	        			        cancelLink = "javascript:doEnrollCancel('"+curriCode+"', '"+curriYear+"', '"+curriTerm+"', '"+curriType+"', '')" ;
	        			    }
	        			    else {
	        			        cancelLink = "javascript:doEnrollCancel('"+curriCode+"', '"+curriYear+"', '"+curriTerm+"', '"+curriType+"', '"+paymentIdx+"')" ;
	        			        enrollConfirmLink = "";
	        			    }

							objStr += "\n<tr>"
								   + "\n<td colspan='8' class='s_text07'><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/blet10.gif' align='absmiddle'><a href=\""+lectureLink +"\">["+curriName+"] ";

							if(curriType == "R") {
								objStr += "\n ("+servicestartDate +" ~ "+serviceendDate+")";
							}
							else if(curriType == "O") {
								objStr += "\n ( 결제일부터 "+serviceStart+"일 간 )";
							}

							objStr += "\n<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_lecture01.gif' align='absmiddle'></a>"
									 + "\n</td></tr>"
									 + "\n<tr><td colspan='8' background='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/tab_lien02.gif'></td></tr>"
									 + "\n<tr><td width='113'><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/blet10.gif' align='absmiddle'> <b>진도율 ("+showContentsCnt+"/"+contentsCnt+")</b>&nbsp;</td>"
									 + "\n<td width='155'><div class='graph_box'><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/graph.gif' width='"+curriPercent+"%' height='10' border='0' alt=''></div></td>"
									 + "\n<td width='69'><font class='s_text08'>"+curriPercent+"%</font></td>";

							if(curriType == "R") {
								objStr	 += "\n<td width='55'>출석<font class='s_text08'> "+showContentsCnt+"</font></td>"
										 + "\n<td width='55'>과제 <font class='s_text09'>"+reportCnt+"</font></td>"
										 + "\n<td width='55'>토론 <font class='s_text09'>"+forumCnt+"</font></td>"
										 + "\n<td width='81'>출석(off)<font class='s_text09'> "+offAttendCnt+"</font></td>"
										 + "\n<td width='87'>동영상(off) <font class='s_text09'>"+offShowTime+"</font></td>";
							} else if(curriType == "O") {
								objStr	 += "\n<td width='55'></td>"
										 + "\n<<td width='55'></td>"
										 + "\n<td width='55'></td>"
										 + "\n<td width='81'></td>"
										 + "\n<td width='87'></td>";
							}

							objStr	 += "\n</tr>"
									 + "\n<tr><td colspan='8' background='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/tab_lien02.gif'></td></tr>"
									 + "\n<tr><td colspan='8' height='15'></td></tr>";
						}

					}
					else if(GUBUN=="2") {
       			    
        			    
        			    if(curriType == "R") serviceDate = servicestartDate +" ~ "+ serviceendDate;
            		    else serviceDate = "결제일 부터 "+servicestartDate+"일간";

        			    var cancelLink = "";
        			    cancelLink = "javascript:doEnrollCancel('"+curriCode+"', '"+curriYear+"', '"+curriTerm+"', '"+curriType+"', '"+paymentIdx+"')" ;

						objStr += "\n<tr>"
								 + "\n<td colspan='8' class='s_text07'><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/blet10.gif' align='absmiddle'>["+curriName+"] ("+serviceDate+")";

						if(enrollStats == "E") {
							objStr += "\n&nbsp;<font color=orange>신청중</font>"
									+ "\n&nbsp;<a href=\""+cancelLink+"\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_lecture02.gif' align='absmiddle'></a>";
						} else {
							objStr += "\n&nbsp;<font color=red>취소</font>";
						}

						objStr += "\n</td></tr>"
								 + "\n<tr><td colspan='8' background='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/tab_lien02.gif'></td></tr>"
								 + "\n<tr><td width='115'><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/blet10.gif' align='absmiddle'> <b>과정 정보</b>&nbsp;</td>"
								 + "\n<td width='555' colspan='7' height='22' align='left'>"+curriInfo.substring(0, 250)+"</td>"
								 + "\n</tr>"
								 + "\n<tr><td colspan='8' background='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/tab_lien02.gif'></td></tr>"
								 + "\n<tr><td colspan='8' height='15'></td></tr>";
					}
					else {


						//var passString = "미수료";
						var passString = "종료";

                		if(enrollStats == "C" && curriType =="R" ){
                			//passString = "<font color=blue><b>수료</b></font> <a href=\"javascript:prnCerification('"+curriCode +"','"+ curriYear +"','" + curriTerm +"','" + enrollNo +"')\">[수료증]</a>";
                	    }
/*
						if(enrollStats == "C" && curriType == "O") {
                			passString = "<font color=blue><b>이수</b></font> <a href=\"javascript:prnCerification('"+curriCode +"','"+ curriYear +"','" + curriTerm +"','" + enrollNo +"')\">[이수증]</a>";
                	    }
*/
						objStr += "\n<tr>"
								 + "\n<td colspan='8' class='s_text07'><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/blet10.gif' align='absmiddle'>["+curriName+"] ("+passString+")";

						if(curriType =="R" && chungEndYn == "Y") {
							var lectureLink = CONTEXTPATH+"/LectureMain.cmd?cmd=goLecture&pMode=Lecture&MENUNO=0&pCurriCode="+ curriCode +"&pCurriYear="+ curriYear +"&pCurriTerm="+ curriTerm +"&pCurriType="+curriType+"&pCurriName="+curriName;
							objStr += "\n<a href=\""+lectureLink +"\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_lecture01.gif' align='absmiddle'></a>";
						} else if(curriType =="R" && chungEndYn == "N") {
							objStr += "\n<a href=\"javascript:alert('청강기간이 끝났습니다.')\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_lecture01.gif' align='absmiddle'></a>";
						}

						objStr += "\n</td></tr>"
								 + "\n<tr><td colspan='8' background='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/tab_lien02.gif'></td></tr>"
								 + "\n<tr><td width='113'><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/blet10.gif' align='absmiddle'> <b>진도율 ("+showContentsCnt+"/"+contentsCnt+")</b>&nbsp;</td>"
								 + "\n<td width='155'><div class='graph_box'><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/graph.gif' width='"+curriPercent+"%' height='10' border='0' alt=''></div></td>"
								 + "\n<td width='69'><font class='s_text08'>"+curriPercent+"%</font></td>";

						if(curriType =="R") {
							objStr += "\n<td width='55'>출석 <font class='s_text08'>"+showContentsCnt+"</font></td>"
									 + "\n<td width='55'>시험 <font class='s_text09'>"+examCnt+"</font></td>"
									 + "\n<td width='55'>토론 <font class='s_text09'>"+forumCnt+"</font></td>"
									 + "\n<td width='81'>출석(off)<font class='s_text09'> "+offAttendCnt+"</font></td>"
									 + "\n<td width='87'>동영상(off) <font class='s_text09'>"+offShowTime+"</font></td>";
						} else {
							objStr += "\n<td width='55'></td>"
									 + "\n<td width='55'></td>"
									 + "\n<td width='55'></td>"
									 + "\n<td width='81'></td>"
									 + "\n<td width='87'></td>";
						}


						objStr += "\n</tr>"
								 + "\n<tr><td colspan='8' background='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/tab_lien02.gif'></td></tr>"
								 + "\n<tr><td colspan='8' height='15'></td></tr>";
					}
    	  		}	//end for

    	  	}	//end else

			objStr += "\n</table>";
   			listObj.innerHTML = objStr;
   			listObj.style.display = "block";

   			nObjStr += "</table>";
   			nameObj.innerHTML = nObjStr;
   			nameObj.style.display = "block";

		}	//end for
//alert('totalLength : '+totalLength);
		//리스트가 하나도 없다면
		if(totalLength == 0) {
//alert("totalLength=0");
   	  	    var vMsg = "";
   	  	    if(GUBUN=="1") vMsg = "수강중인 과정이 없습니다.";
   		  	else if(GUBUN=="2") vMsg = "수강 신청중인 과정이 없습니다.";
   		  	else if(GUBUN=="3") vMsg = "수료한 과정이 없습니다.";

   		  	var zeroObj = $("ZeroInfoList");
            var zObjStr = "<table width='670' align='center' >"
						+ "<tr><td class='s_tab04_cen'>※ "+vMsg+" </td></tr>"
   			       		+ "<tr class='s_tab03'><td colspan='20'></td></tr>"
   			       		+ "</table>";

   			zeroObj.innerHTML = zObjStr;
   			zeroObj.style.display = "block";
		}
	}


    function prnCerification(pCurriCode, pCurriYear, pCurriTerm,pEnrollNo)
    {
        var Page = CONTEXTPATH +"/Student.cmd?cmd=cerificationPrn";
            Page += "&pCurriCode=" + pCurriCode ;
            Page += "&pCurriYear=" + pCurriYear ;
            Page += "&pCurriTerm=" + pCurriTerm ;
            Page += "&pEnrollNo=" + pEnrollNo ;

        window.open(Page,"cerification", "left=162,top=100,toolbar=0,directories=0,status=0,menubar=0,width=550,height=800,scrollbars=1");

    }

    function Enrollconfirm(pCurriCode, pCurriYear, pCurriTerm, pEnrollNo, pay_idx, cmd)
    {
    	var str;
    	str = CONTEXTPATH +"/Student.cmd?cmd=EnrollConfirm";
    	var Page = str;
        Page += "&pCurriCode=" + pCurriCode ;
        Page += "&pCurriYear=" + pCurriYear ;
        Page += "&pCurriTerm=" + pCurriTerm ;
        Page += "&pEnrollNo=" + pEnrollNo ;
        Page += "&pIdx=" + pay_idx ;
        Page += "&pEnrollType=" + cmd ;

        window.open(Page,"enrollConfirm", "left=162,top=100,toolbar=0,directories=0,status=0,menubar=0,width=550,height=800,scrollbars=1");

    }


    function doEnrollCancel(pCurriCode, pCurriYear, pCurriTerm, pCurriType, pPaymentIdx)
    {
        var Page = "";

       if(pPaymentIdx=="") {
            if(confirm("정말로 수강취소를 하시겠습니까?\n\n선택하신 강의는 무료 강의로 확인 클릭 시 바로 취소 처리 됩니다.")==false) return;
            Page = CONTEXTPATH +"/Student.cmd?cmd=enrollCancelProcess&pMode=MYPAGE&pGubun=" + GUBUN;
       }
       else{
            Page = CONTEXTPATH +"/Student.cmd?cmd=enrollCancel&pMode=MyPage&MENUNO=0&pGubun=" + GUBUN;
       }

            Page += "&pCurriCode="  + pCurriCode ;
            Page += "&pCurriYear="  + pCurriYear ;
            Page += "&pCurriTerm="  + pCurriTerm ;
            Page += "&pCurriType="  + pCurriType ;
            Page += "&pPaymentIdx=" + pPaymentIdx ;


        location.href= Page;
    }
    
    function EnrollEdit() {
    	document.location = CONTEXTPATH + "/CurriSub.cmd?cmd=currentList&pMode=MyPage&MENUNO=0";	
    }


	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}



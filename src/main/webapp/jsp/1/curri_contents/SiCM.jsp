<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/popup_header.jsp" %>
<%	String pCurriCode = (String)model.get("pCurriCode");
	String pCurriYear = (String)model.get("pCurriYear");
	String pCurriTerm = (String)model.get("pCurriTerm");
	String pCourseId = (String)model.get("pCourseId");
	String pContentsOrder = (String)model.get("pContentsOrder");
%>
<link href="<%=CONTEXTPATH%>/css/SiCM.css" rel="stylesheet" type="text/css">
<script language=javascript>
    function itemSearch(maniID, arg ) { 
//		if (!checkBoxSelected() ) return ;
	//	itemsList.innerHTML = "";
		//medilms.searchItems.length = 0;
		//medilms.chk.checked = false ;
		if( !checkBoxReverse(arg) ) return  ;
		document.all.itemsList.innerHTML = "" ;
		searchItems(maniID);
	}
	function checkBoxSelected() {
		var cnt = medilms.chb1.length ;
		var result   = false ;
		if (cnt ==  undefined ) {
			if( medilms.chb1.checked == true )  return  true; 
		
		} else {
			for ( var i = 0 ; i < cnt ; i++ ) {
				if ( medilms.chb1[i].checked == true ) return true ;
			}
		}

		return  false ;
	}
	
	function checkBoxReverse(arg) {
		var cnt = medilms.chb1.length ;
		var index = parseInt(arg);
		var result   = false ;
		if (cnt ==  undefined ) {
			if( medilms.chb1.checked == true )  result =  true; 
			else  result =  false ;
		} else {
			if ( medilms.chb1[index].checked == true ) {
				for (var i = 0 ; i < cnt ; i++ ) {

					if( index  != i ) {

						medilms.chb1[i].checked = false ;
					}
				}
				result =  true;
			}else {
				result =  false ;
			}
		}

		return result ;
	}	
	function searchItems( arg ) {
		medilms.method = "post";
		medilms.action = "<%=CONTEXTPATH%>/Contents.cmd?cmd=SiCMInnerItems"; 
		medilms.maniID.value = arg ;
		medilms.target = "hiddenFrame";
		medilms.flag.value = "Q";
		medilms.submit() ;		
	}
	function searchManifest() {
		var keyWord = medilms.pKeyWord.value;
		medilms.method = "post";
		medilms.action = "<%=CONTEXTPATH%>/Contents.cmd?cmd=SiCMInnerManifest&pKeyWord="+keyWord;
		medilms.target = "hiddenFrame";
		medilms.flag.value = "Q";
	

		medilms.submit() ;	

	}
 	function allChk() {

 		allSelected(medilms, medilms.chk.checked ) ;
 	}
 	function allSelected( f, arg  ) {
    	setChkboxAll(f, "orgCheck", arg);
    	setChkboxAll(f, "checkBoxSelected", arg);
 		return true ;
 	}
 	
	 function chkItemsID( arg ) {
	  	var toList   = document.medilms.selectedItems ;
	  	var len = toList.length ;
	  	var itemsID = "" ;
	  	var tempID = "" ;
	  	for ( var i = 0 ; i < len ; i++ ) { 
	  		tempID = toList.options[i].value ;
	  		if( tempID.substring(0,2) == 'Y^' ) {
	  			itemsID = tempID.substring(2,tempID.length) ;
	  		}else {
	  			itemsID = tempID ;
	  		}
	//  		alert(toList.options[i].value);
	  		if( arg== itemsID )  {
	  			return false ;
	  		}
	  	}
	  	return true ;
	 }

	function isValid(arg) {
		var f = medilms;
		var	chk	=	false;
		var len = f.checkBoxSelected.length;					
			if(len > 1 ){					
				for(var i=0; i < len;i++){
					if(f.checkBoxSelected[i].checked) {
						chk = true;
						break;
					}
				}				
				if(!chk){
					alert("등록 대상을 선택하십시요!");
					return;
				}
			}else{
				if(!f.checkBoxSelected.checked) {
					alert("등록 대상을 선택하십시요!");
					return;
				}				
			}
		return true ;
	}
	function main(arg) {
		
		switch(arg) {
			case 'I' :
				if(!isValid(arg) ) return ;
				if(!confirm("저장하시겠습니까?") ) return ;
				//allSelected(medilms.selectedItems, true);
				medilms.flag.value = arg;
				medilms.method = "post";
				medilms.action = "<%=CONTEXTPATH%>/CurriContents.cmd?cmd=SiCMRegist";
				//medilms.target=self;
				medilms.submit();
				break;			
			case 'X' :
				self.close();
				break;
		}
	}
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function orgViewEvent(arg) { 
		var cnt = 0 ;
		var objItem = "" ;
		var objImage = "" ;
		if ( arg == 0 ) {
			
			cnt = document.all.itemContent.length	 ;
			if  ( cnt == undefined ) {
				objItem = document.all.itemContent.style ;
				objImage = medilms.viewImage ;
			}else {
				objItem = document.all.itemContent[0].style ;
				objImage = medilms.viewImage[0];
			}

		}else {
			objItem = document.all.itemContent[arg].style ;
			objImage = medilms.viewImage[arg];
		}
		showToggle(objItem);
		showImageToggle(objImage, objItem);
	}
	function showToggle( argItem ) {
		argItem.display = (argItem.display == "block") ? "none": "block";

	}
	function showImageToggle(argImage,  argItem ) {
		
		if( argItem.display == "block" ) {
			argImage.src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/SiCM/btn_mibn.gif";
		}else {
			argImage.src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/SiCM/btn_plus.gif";
		}
	}
	function orgCheckEvent(argIndex) {
		var cnt = 0 ;
		var objItem = "" ;
		var startIndex	=	0 ;
		var endIndex	=	 0 ;
		if ( argIndex == 0 ) {
		
			cnt =  medilms.orgCheck.length	 ;
			if  ( cnt == undefined ) {
				objItem = medilms.orgCheck  ;
				startIndex = 0 ;
				endIndex = medilms.checkRow.value   ;
			}else {
				objItem = medilms.orgCheck[0] ;
				startIndex = 0 ;
				endIndex = medilms.checkRow[0].value    ;
			}
		}else {
			objItem = medilms.orgCheck[argIndex] ;
			startIndex =  medilms.checkRow[argIndex-1].value   ;
			endIndex =	 medilms.checkRow[argIndex].value   ;
		
			
		}
		startIndex = parseInt(startIndex, 10) ;
		endIndex = parseInt(endIndex, 10);
		partCheckSelected(objItem.checked, startIndex, endIndex);
	}
	function  partCheckSelected(flag , startIndex, endIndex ) {
		var cnt = 0 ;	
		cnt =  medilms.checkBoxSelected.length	 ;
	
		if ( cnt == undefined ) {
			medilms.checkBoxSelected.checked  = flag ;
		}else {
			for ( var i = startIndex; i < endIndex ; i++ ) {
				medilms.checkBoxSelected[i].checked  = flag ;
			}
		}
	}
//-->
</SCRIPT>
<form name="medilms" onSubmit="return(false);" >
<input type = "hidden" name ="flag">
<input type = "hidden" name="maniID">
<input type='hidden' name='pCurriCode' value='<%=pCurriCode%>'>
<input type='hidden' name='pCurriYear' value='<%=pCurriYear%>'>
<input type='hidden' name='pCurriTerm' value='<%=pCurriTerm%>'>
<input type = "hidden" name="pCourseId" value="<%=pCourseId%>" >
<input type = "hidden" name="pContentsOrder" value="<%=pContentsOrder%>" >
<table width="500" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td><!-- inner table setart -->
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr align="center" bgcolor="#6BBCE7">
					<td height="1"></td>
				</tr>
				<tr align="center" bgcolor="#FFFFFF">
					<td><!-- inner table start -->
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<colgroup> 
								<col width = '60' >
								<col width = '1' > 
								<col width = '320'>
								<col width = '1' >
								<col> 
							</colgroup> 				
							<tr>
								<td height="4" colspan="5" class="c0_bgc2"></td>
							</tr>
							<tr>
								<td height="1" colspan="5" bgcolor="#FFFFFF"></td>
							</tr>
							<tr>
								<td  height="25" align="center" class="c0_bgc1">선택</td>
								<td  bgcolor="#FFFFFF"></td>
								<td align="center" class="c0_bgc1">대분류명</td>
								<td  bgcolor="#FFFFFF"></td>
								<td align="center" class="c0_bgc1">등록자</td>
							</tr>
						</table><!-- inner table end-->
						<div  style="position:relative; top:0; left:0; height=200; width:520;"  id ="manifestList"   style= "overflow: auto;">
						</div>
					</td>
				</tr>
				<tr align="center" >
					<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/SiCM/shadow.gif" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/SiCM/shadow.gif" width="2" height="2"></td>
				</tr>
				<tr height='25'>
					<td align=center>Manifest 검색 <input type=text name='pKeyWord' value=''> <input type=button value='검색' onClick='searchManifest()'></td>
				</tr>
				<tr align="center" >
					<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/SiCM/shadow.gif" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/SiCM/shadow.gif" width="2" height="2"></td>
				</tr>
			</table><!-- inner table end -->
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="left" valign="bottom"><input type = "checkbox" id ="chk1" class = "rcCls" name = "chk"  onClick="allChk();"> <label for = "chk1" >전체선택</label></td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
	<tr>
		<td><!-- inner table start-->
			<div   id ="itemsList"   style= "overflow: auto;"></div>
			<!-- inner table end-->
		</td>
	</tr>
	<tr>
		<td align="center">&nbsp;</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#EEEEEE"></td>
	</tr>
	<tr>
		<td align="center">&nbsp;</td>
	</tr>
	<tr>
		<td align="center">
			<a href="javascript:main('I');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/SiCM/btn_keeping.gif" width="87" height="31" border = '0'></a>
			&nbsp;&nbsp;
			<a href="javascript:main('X');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/SiCM/btn_closed.gif" width="87" height="31" border ='0'></a>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<form>
<IFRAME ID="hiddenFrame" NAME="hiddenFrame" SRC="" WIDTH="0" HEIGHT="0" frameborder="0" scrolling="NO" noresize>
</IFRAME>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
searchManifest();
//-->
</SCRIPT>

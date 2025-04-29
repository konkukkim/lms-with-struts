
	function recommendSearch() {
	  var arg = document.qsearchForm.keyWord1.value;
	  CurriSubWork.currentSearchAuto(arg, recommendSearchCallback);    
	}
	   
	function selectKeyword(data){
		document.qsearchForm.keyWord1.value = data;
		$("searchResultDiv").innerHTML = "";
		$("searchResultDiv").style.display = "none";		
	}
	
	function recommendSearchCallback(data) {

		var searchResultObj = $("searchResultDiv");
	  	if(data != null && data != ""){

		  	var objStr = "<table width='85%' border='0' bgcolor='#FFFFFF' cellpadding='0' cellspacing='0'>";	  	
			for(i=0;i<data.length;i++){
		  		objStr += "<tr onMouseOver=\"this.className='tdcolor2';\" onMouseOut=\"this.className='tdcolor1'\">"
		  			+"<td><a href=\"javascript:selectKeyword('"+data[i]+"')\">"+data[i]+"</a></td></tr>";
		  	}
			objStr += "</table>";
			searchResultObj.innerHTML = objStr;
			searchResultObj.style.display = "block";		  	
	  	}else{
	  		searchResultObj.style.display = "none";
	  	}
/*  		  
	  // table 내 각 열에 대한 정의를 다음과 같이 각 열대로의 function 을 정의함
	  var cellFuncs = [
	    function(data) {
//	      return "<input type='text' name='recommend' style='border:0px;cursor:hand' " + 
//	        " value='" + data + "' onClick='selectRecommend(this.value)' readOnly />";
	      return data;	        
	    }
	  ];
		
//		alert(cellFuncs.length);
	  DWRUtil.removeAllRows("RecommendTable");
	  DWRUtil.addRows("RecommendTable", data, cellFuncs);
	  
*/	  
	}
	
	function selectRecommend(data) {
	  DWRUtil.setValue("recommendDiv", data);
	}
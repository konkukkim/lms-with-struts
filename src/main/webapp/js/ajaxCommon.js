/*
url-loading object and a request queue built on top of it
*/

/* namespacing object */
var validKey = "sangsang";
var net=new Object();

net.READY_STATE_UNINITIALIZED=0;
net.READY_STATE_LOADING=1;
net.READY_STATE_LOADED=2;
net.READY_STATE_INTERACTIVE=3;
net.READY_STATE_COMPLETE=4;


/*--- content loader object for cross-browser requests ---*/
net.ContentLoader=function(url,onload,onerror,method,params,contentType){
  this.req=null;
  this.onload=onload;
  this.onerror=(onerror) ? onerror : this.defaultError;
  this.loadXMLDoc(url,method,params,contentType);
}

net.ContentLoader.prototype.loadXMLDoc=function(url,method,params,contentType){
  if (!method){
    method="GET";
  }
  if (!contentType && method=="POST"){
    contentType='application/x-www-form-urlencoded';
  }

  if (window.XMLHttpRequest){
    this.req=new XMLHttpRequest();
  } else if (window.ActiveXObject){
    this.req=new ActiveXObject("Microsoft.XMLHTTP");
  }
  if (this.req){
    try{
      var loader=this;
      this.req.onreadystatechange=function(){
        net.ContentLoader.onReadyState.call(loader);
      }
      this.req.open(method,url,true);
      if (contentType){
        this.req.setRequestHeader('Content-Type', contentType);
        this.req.setRequestHeader('isValid', validKey);     
/*        
		// prepare the MIME POST data
		var boundaryString = 'capitano';
		var boundary = '--' + boundaryString;
		var requestbody = boundary + '\n' 
		+ 'Content-Disposition: form-data; name="mytext"' + '\n' 
		+ '\n' 
		+ mytext + '\n' 
		+ '\n' 
		+ boundary + '\n' 
		+ 'Content-Disposition: form-data; name="myfile"; filename="' 
		+ filename + '"' + '\n' 
		+ 'Content-Type: application/octet-stream' + '\n' 
		+ '\n'
		+ escape(binary.readBytes(binary.available()))
		+ '\n'
		+ boundary;
		
		this.req..setRequestHeader("Content-type", "multipart/form-data; \boundary=\"" + boundaryString + "\"");
//		this.req..setRequestHeader("Connection", "close");
		this.req..setRequestHeader("Content-length", requestbody.length);
		this.req..send(requestbody);
*/          
      }
      this.req.send(params);
    }catch (err){
      this.onerror.call(this);
    }
  }
}


net.ContentLoader.onReadyState=function(){
  var req=this.req;
  var ready=req.readyState;
  if (ready==net.READY_STATE_COMPLETE){
    var httpStatus=req.status;
    if (httpStatus==200 || httpStatus==0){
      this.onload.call(this);
    }else{
      this.onerror.call(this);
    }
  }
}

net.ContentLoader.prototype.defaultError=function(){
  alert("error fetching data!"
    +"\n\nreadyState:"+this.req.readyState
    +"\nstatus: "+this.req.status
    +"\nheaders: "+this.req.getAllResponseHeaders());
}

	
// 진행상태 표시
function viewProgress(elementId,displayType,contextPath,systemCode,viewFlag, height) {
	if(viewFlag=='Y'){
	  	var progressStr = "<table width='100%' height='"+height+"' border='0' cellpadding='0' cellspacing='0'>"
	 		+ "<tr><td align='center' height='50' bgcolor='#ffffff'>"
		    + "<img src='"+contextPath+"/img/"+systemCode+"/common/indicator-loader.gif' width='32' height='32' >"
	 		+ "</td></tr>"  		   
	        + "</table>";	

	    var progressObj = document.getElementById(elementId);
	    progressObj.innerHTML = progressStr;
	    progressObj.style.display = displayType;	    
    }else{
    	return;
    }
}
	
// 검색중 표시 sangsang 2007.03.21
/*
function viewProgress(elementId,displayType,contextPath,systemCode,viewFlag) {
	if(viewFlag=='Y'){
	  	var progressStr = "<table width='100%' height='25' border='0' cellpadding='0' cellspacing='0'>"
	 		+ "<tr><td align='center' height='50' bgcolor='#ffffff'>"
			+ "<img src='"+contextPath+"/img/"+systemCode+"/common/indicator-loader.gif' width='32' height='32' >"
	 		+ " </td></tr>"  		   
	        + "<tr><td height='1' class='b_td03'></td></tr>"	
	        + "</table>";	

	    var progressObj = document.getElementById(elementId);
	    progressObj.innerHTML = progressStr;
	    progressObj.style.display = displayType;	    
    }else{
    	return;
    }
}
*/

// 셀렉트 박스 selected 값 세팅
function setOptionSelected(optionObject,obtionValue){
	for (i=0;i<optionObject.length;i++ ) {
	    if ( optionObject[i].value == obtionValue ) {
	       optionObject[i].selected = true;
	       break;
	    }
	} 	
}
 	
// 파라미터 인코딩..현재는 encodeURIComponent 을 두번 함.. --;  sangsang 2007.03.21
// 파라미터 인코딩..현재는 encodeURIComponent 을 안 함.. --;  sangsang 2007.05.21
function ajaxEnc(data){
	var encData = "";
	if(data !=null && data !=""){
//		encData = encodeURIComponent(encodeURIComponent(data));
//		encData = encodeURIComponent(data);
		encData = data;
	}
	else
		encData ="";
		
	return encData;
}




    function validateAjax(form) {
        var obj;
        var dispName;
        var dataType;
        var minValue;
        var maxValue;
        var len;
        var lenCheck;
        var lenMCheck;
        var isValid;
        var value;
        for (i = 0; i < form.elements.length; i++) {
            obj = form.elements(i);
            
        if(obj.name != ""){ //이름이 있는 경우만 체크함.(2004.10.26 suna)
            	obj.value = trim(obj.value);
         	
            dispName 		= obj.getAttribute("dispName");
            dataType 		= obj.getAttribute("dataType");
            minValue 		= obj.getAttribute("minValue");
            maxValue 		= obj.getAttribute("maxValue");
            len      		= obj.getAttribute("len");
            lenCheck 		= obj.getAttribute("lenCheck");
            lenMCheck 		= obj.getAttribute("lenMCheck");

            value = obj.value;
            
            if (dispName == null) {
                dispName = obj.name;
            }

            // 필수 입력 항목 체크
            if (obj.getAttribute("notNull") != null) {
                isValid = false;

                if (obj.type == "radio" || obj.type == "checkbox") {
                    if (form.elements(obj.name).length) {
                        for (j = 0; j < form.elements(obj.name).length; j++) {
                            if (form.elements(obj.name)[j].checked) {
                                isValid = true;
                                break;
                            }
                        }
                    } else {
                        if (obj.checked) {
                            isValid = true;
                        }
                    }
                } else {
                    if (value != "") {
                        isValid = true;
                    } else {
                        if (obj.getAttribute("comma") != null) {
                            obj.value = 0;
                            isValid = true;
                        }
                    }
                }

                if (!isValid) {
                    alert(dispName + "을(를) 입력하십시오.");
                    new Effect.Highlight(obj);
                    obj.focus();
                    if (window.event) {
                        window.event.returnValue = false;
                    }
                    return  false;
                }
            }
             



            // 데이터 길이 체크
            if (len != null) {
                if (value.length != eval(len)) {
                    alert(dispName + "은(는) " + len + "자리를 입력해야 합니다.");
                    new Effect.Highlight(obj);
                    obj.focus();
                    if (window.event) {
                        window.event.returnValue = false;
                    }
                    return  false;
                }
            }
            
           if(lenCheck != null )
            {
            	
              if( jsByteLength(value) > eval(lenCheck) )
                {
                  alert(dispName + "은(는) " + lenCheck + " 자리를 넘을수 없습니다 현재 글자수("+jsByteLength(value)+")");
                  obj.value = value;
                  new Effect.Highlight(obj);
                  obj.focus();
                    if(window.event)
                    {
                       window.event.returnValue = false;
                    }
                
                    return false;
                }
               
            }            
            
            // 최소 입력자리수 체크(2004.07.21 추가     작성자 : 박광진)
            if(lenMCheck != null) {
            	if(value.length < eval(lenMCheck)) {
            		alert(dispName + "은(는) " + lenMCheck + " 자리수 이상 입력하셔야 합니다.");
            		new Effect.Highlight(obj);
                    obj.focus();
                    if(window.event)
            			window.event.returnValue = false;
            		
            		return false;
            	}
            }

            if (obj.type == "text") {
                // 데이터 타입 체크
                if (dataType == null) { // 2002.01.30 추가
                    if (obj.readOnly == false && jsByteLength(value) > obj.maxLength) {
                        alert(dispName + " 길이가 " + obj.maxLength + " 을(를) 넘습니다.");
                        new Effect.Highlight(obj);
                        obj.focus();
                        if (window.event) {
                            window.event.returnValue = false;
                        }

                        return  false;
                    }
                } else if ((value != "") && (dataType != null)) {
                    isValid = true;
                    checkValue = false;

                    if (dataType == "date") {
                        value = deleteDateFormatStr(value);
                        isValid = isDate(value);
                        checkValue = true;
                    } else if (dataType == "email") {
                        isValid = isEmail(value);
                    } else if (dataType == "float") {
                        value = deleteCommaStr(value);
                        isValid = isFloat(value);
                        checkValue = true;
                    } else if (dataType == "integer") {
                        value = deleteCommaStr(value);
                        isValid = isInteger(value);
                        checkValue = true;
                    } else if (dataType == "number") {
                        value = deleteCommaStr(value);
                        isValid = isNumber(value);
                        checkValue = true;
                    } else if (dataType == "double") {
                        value = deleteCommaStr(value);
                        isValid = isNumber(value);
                        checkValue = true;
                    }

                    if (!isValid) {
                        alert(dispName + " 형식이 올바르지 않습니다.");
                        if (dataType == "float" || dataType == "integer" || dataType == "number" || dataType == "double") {
                            obj.value = "0";
                        }
                        new Effect.Highlight(obj);
                        obj.focus();
                        if (window.event) {
                            window.event.returnValue = false;
                        }
                        return  false;
                    }

                    if (checkValue) {
                        if (minValue != null) {
                            if (eval(minValue) > eval(value)) {
                                alert(dispName + " 값은 최소값(" + minValue + ") 이상입니다.");
                                new Effect.Highlight(obj);
                                obj.focus();
                                if (window.event) {
                                    window.event.returnValue = false;
                                }
                                return  false;
                            }
                        }

                        if (isValid && (maxValue != null)) {
                            if (eval(maxValue) < eval(value)) {
                                alert(dispName + " 값이 최대값(" + maxValue + ")을 초과합니다.");
                                new Effect.Highlight(obj);
                                obj.focus();
                                if (window.event) {
                                    window.event.returnValue = false;
                                }
                                return  false;
                            }
                        }
                    }
                }
            }
         }
      }
        return  true;
    }
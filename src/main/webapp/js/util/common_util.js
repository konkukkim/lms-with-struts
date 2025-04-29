/*******************************************************************************
	DHTML 관련 공통 유틸리티

	Author: Kim Kyoung shil
*******************************************************************************/

var browserType			= checkBrowserType();	// 사용자 브라우져 타입


/**
 * 사용자 브라우져 체크
 */
function checkBrowserType() {
	var brType = "IE";
	if (document.all){
		brType = "IE";
	}
	else if (document.getElementById){
		brType = "NE";
	}
	else if (document.layers){
		brType = "NE4";
	}
	return brType;
}


/**
 * 마우스 X 위치
 */
function checkMouseX(evt) {
	var xCoord = 0;
	if (browserType == "IE"){
		xCoord = event.clientX;
	}
	else if (browserType == "NE"){
		xCoord = evt.pageX;
	}
	return xCoord;
}


/**
 * 마우스 Y 위치
 */
function checkMouseY(evt) {
	var yCoord = 0;
	if (browserType == "IE"){
		yCoord = event.clientY;
	}
	else if (browserType == "NE"){
		yCoord = evt.pageY;
	}
	return yCoord;
}


/**
 * 마우스 버튼값 구하기
 * @return left,center,right
 */
function getMouseButton(evt) {
	var button 		= "left";
	var mouseKey	= 1;
	if (browserType == "IE") {
		mouseKey = event.button
		if (mouseKey == 1) {
			button = "left";
		}
		else if (mouseKey == 2) {
			button = "right";
		}
		else if (mouseKey == 4) {
			button = "center";
		}
	}
	else {
		mouseKey = evt.which;
		if (mouseKey == 1) {
			button = "left";
		}
		else if (mouseKey == 2) {
			button = "center";
		}
		else if (mouseKey == 3) {
			button = "right";
		}
	}
	return button;
}


/**
 * 객체의 넓이 구하기
 */
function getWidth(obj) {
	if (obj) {
		var objWidth = obj.offsetWidth;
		return objWidth;
	}
}


/**
 * 객체의 높이 구하기
 */
function getHeight(obj) {
	if (obj) {
		var objHeight = obj.offsetHeight;
		return objHeight;
	}
}


/**
 * 윈도우 넓이 구하기
 */
function getWindowWidth() {
	var winWidth = 0;
	if (browserType == "IE") {
		winWidth = document.body.offsetWidth;
	}
	else if (browserType == "NE") {
		winWidth = window.innerWidth;
	}
	return winWidth;
}


/**
 * 윈도우 높이 구하기
 */
function getWindowHeight() {
	var winHeight = 0;
	if (browserType == "IE") {
		winHeight = document.body.offsetHeight;
	}
	else if (browserType == "NE") {
		winHeight = window.innerHeight;
	}
	return winHeight;
}
    

/**
 * 스크롤값(X) 구하기
 */
function getScrollX() {
	var scrollX		= 0;

    if (browserType == "NE") {
        scrollX		= window.pageXOffset;
    }
    else {
        scrollX		= document.body.scrollLeft;
    }

	return scrollX;
}


/**
 * 스크롤값(Y) 구하기
 */
function getScrollY() {
	var scrollY		= 0;
    
	if (browserType == "NE") {
        scrollY		= window.pageYOffset;
    }
    else {
        scrollY		= document.body.scrollTop;
    }

	return scrollY;
}


/**
 * 객체 가져오기
 * @param objName(객체명)
 */
function getObject(objName) {
	var returnObj = null;
	if (browserType == "IE") {
		returnObj = eval("document.all."+objName);
	}
	else if (browserType == "NE") {
		returnObj = document.getElementById(objName);
	}
	return returnObj;
}


/**
 * 객체 DISPLAY 변경
 */
function changeDisplay(obj) {
	if (obj) {
		var displayStyle = obj.style.display;
		if (displayStyle == "block") {
			offDisplay(obj);
		}
		else if (displayStyle == "none") {
			onDisplay(obj);
		}
	}
}


/**
 * DISPLAY on
 */
function onDisplay(obj) {
	if (obj) {
		obj.style.display = "block";
	}
}


/**
 * DISPLAY off
 */
function offDisplay(obj) {
	if (obj) {
		obj.style.display = "none";
	}
}


/**
 * 객체 보이기
 */
function visibleObject(obj) {
	if (obj) {
		obj.style.visibility = "visible";
	}
}


/**
 * 객체 숨기기
 */
function hiddenObject(obj) {
	if (obj) {
		obj.style.visibility = "hidden";
	}
}


/**
 * 객체 선택시 색상 반전
 */
function onSelectColor(obj) {
	if (obj) {
		obj.style.backgroundColor	= "highlight";
		obj.style.color				= "highlighttext";
	}
}


/**
 * 객체 선택 비활성 색상 복원
 */
function offSelectColor(obj, color, bgColor) {
	if (obj) {
		if (color == null) {
			color = "";
		}
		if (bgColor == null) {
			bgColor = "";
		}
		obj.style.color				= color;
		obj.style.backgroundColor	= bgColor;
	}
}


/*
 * 객체 사이즈 설정
 */
function resizeObject(obj, newWidth, newHeight) {
	if (obj) {
		obj.style.width     = newWidth+"px";
		obj.style.height    = newHeight+"px";
	}
}


/**
 * 객체 넓이 설정
 */
 function resizeWidth(obj, newWidth) {
 	if (obj) {
		obj.style.width		= newWidth+"px";
	}
 }


/**
 * 객체 높이 설정
 */  
function resizeHeight(obj, newHeight) {
	if (obj) {
		obj.style.height	= newHeight+"px";
	}
}


/**
 * 객체 이동 설정 (위치)
 */
function moveObject(obj, topPosition, leftPosition) {
	if (obj) {
		obj.style.top       = topPosition+"px";
		obj.style.left      = leftPosition+"px";
	}
}


/**
 * 객체의 위치 (document 내에서 절대적인 위치)
 */
function getPosition(obj) {
	var pos = { x:0, y:0 };
	
	if ( document.layers ) {
		pos.x = obj.x;
		pos.y = obj.y;
	}
	else {
		do { 
			pos.x += parseInt( obj.offsetLeft );
			pos.y += parseInt( obj.offsetTop );
			obj = obj.offsetParent;
		} while (obj);
	}
	return pos;
}


/**
 * 객체의 왼쪽 위치 (상대위치)
 */
function getLeftPosition(obj) {
	var value = 0;
	if (obj) {
		if (browserType == "IE") {
			if (obj.currentStyle.left == "auto") {
				value = 0;
			}
			else {
				value = parseInt(obj.currentStyle.left);
			}
		}
		else {
			value = parseInt(obj.style.left);
		}
	}
	return value;
}


/**
 * 객체 위쪽 위치 (상대위치)
 */
function getTopPosition(obj) {
	var value = 0;
	if (obj) {
		if (browserType == "IE") {
			if (obj.currentStyle.top == "auto") {
				value = 0;
			}
			else {
				value = parseInt(obj.currentStyle.top);
			}
		}
		else {
			value = parseInt(obj.style.top);
		}
	}
	return value;
}


/**
 * 객체의 속성값 구하기
 */
function getObjectAttribute(obj, attrName) {
	var attrValue	= null;

	if (obj) {
		attrValue	= obj.getAttribute(attrName);
	}
	return attrValue;
}


/**
 * 객체ID 구하기
 */
function getObjectId(obj) {
	var objectId = getObjectAttribute(obj, "id");
	return objectId;
}


/**
 * 객체를 맨 위로 위치하기
 */
function makeOnTop(obj) {
	if (obj) {
		var daiz;
		var max = 0;
		var da = null;
		if (browserType == "IE") {
			da = document.all;
		}
		else if (browserType == "NE") {
			da = document.getElementsByTagName("div");
		}
		for (var i=0; i<da.length; i++) {
			daiz = parseInt(da[i].style.zIndex);
			if (daiz != "" && daiz >= max) {
				max = daiz;
			}
		}
		obj.style.zIndex        = max + 1;
	}
}


/**
 * Attribute의 Value 추출 (name=value 에서 value 추출
 */
function getAttributeValue(attr, name) {
	attr = nullToEmpty(attr).toLowerCase();
	var value = "";
	if (attr.length > 1) {
		var idx1 = attr.indexOf(name);
		if (idx1 != -1) {
			var idx2 = attr.indexOf("=",idx1);
			if (idx2 != -1) {
				var idx3 = attr.indexOf(",",idx2);
				var oValue = "";
				if (idx3 == -1) {
					oValue = attr.substring(idx2+1);
				}
				else {
					oValue = attr.substring(idx2+1,idx3);
				}
				for (var i = 0; i < oValue.length; i++) {
					if (oValue.charCodeAt(i) != 32) {
						value += oValue.charAt(i);
					}
				}

			}
		}
		
	}
	return value;
}


/**
 * 이미지 변경
 */
function changeImage(imgObj, imgSrc) {
	imgObj.src = imgSrc;
}


/**
 * 문자열에서 구분자로 분자열 분리하기
 * @param str		문자열
 * @param idx		구분자를 기준으로 n번째 문자열 추출
 * @param divideStr 구분문자
 */
function getDivideString(str, idx, divideChar) {
	var result = "";
	if (nullToEmpty(str) != "") {
		startIdx 	= 0;
		endIdx 		= 0;
		var loopCnt	= 0;
		
		for (var i=0; i<idx; i++) {
			if (i > 0) {
				startIdx = endIdx;
			}
			
			loopCnt += 1;
			endIdx = str.indexOf(divideChar, endIdx) + 1;

			if (endIdx == 0) {
				break;
			}
		}

		if (endIdx == 0 && idx == loopCnt) {
			endIdx = str.length + 1;
		}
		
		if (endIdx == 0 && idx > loopCnt) {
			result = "";
		}
		else {
			result = str.substring(startIdx, endIdx - 1);
		}
	}
	
	return result;
}


/**
 * null 값을 ""으로 변환
 */
function nullToEmpty(value) {
	if (value == null) {
		value = "";
	}
	return value;
}
/*******************************************************************************
	DHTML ���� ���� ��ƿ��Ƽ

	Author: Kim Kyoung shil
*******************************************************************************/

var browserType			= checkBrowserType();	// ����� ������ Ÿ��


/**
 * ����� ������ üũ
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
 * ���콺 X ��ġ
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
 * ���콺 Y ��ġ
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
 * ���콺 ��ư�� ���ϱ�
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
 * ��ü�� ���� ���ϱ�
 */
function getWidth(obj) {
	if (obj) {
		var objWidth = obj.offsetWidth;
		return objWidth;
	}
}


/**
 * ��ü�� ���� ���ϱ�
 */
function getHeight(obj) {
	if (obj) {
		var objHeight = obj.offsetHeight;
		return objHeight;
	}
}


/**
 * ������ ���� ���ϱ�
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
 * ������ ���� ���ϱ�
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
 * ��ũ�Ѱ�(X) ���ϱ�
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
 * ��ũ�Ѱ�(Y) ���ϱ�
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
 * ��ü ��������
 * @param objName(��ü��)
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
 * ��ü DISPLAY ����
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
 * ��ü ���̱�
 */
function visibleObject(obj) {
	if (obj) {
		obj.style.visibility = "visible";
	}
}


/**
 * ��ü �����
 */
function hiddenObject(obj) {
	if (obj) {
		obj.style.visibility = "hidden";
	}
}


/**
 * ��ü ���ý� ���� ����
 */
function onSelectColor(obj) {
	if (obj) {
		obj.style.backgroundColor	= "highlight";
		obj.style.color				= "highlighttext";
	}
}


/**
 * ��ü ���� ��Ȱ�� ���� ����
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
 * ��ü ������ ����
 */
function resizeObject(obj, newWidth, newHeight) {
	if (obj) {
		obj.style.width     = newWidth+"px";
		obj.style.height    = newHeight+"px";
	}
}


/**
 * ��ü ���� ����
 */
 function resizeWidth(obj, newWidth) {
 	if (obj) {
		obj.style.width		= newWidth+"px";
	}
 }


/**
 * ��ü ���� ����
 */  
function resizeHeight(obj, newHeight) {
	if (obj) {
		obj.style.height	= newHeight+"px";
	}
}


/**
 * ��ü �̵� ���� (��ġ)
 */
function moveObject(obj, topPosition, leftPosition) {
	if (obj) {
		obj.style.top       = topPosition+"px";
		obj.style.left      = leftPosition+"px";
	}
}


/**
 * ��ü�� ��ġ (document ������ �������� ��ġ)
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
 * ��ü�� ���� ��ġ (�����ġ)
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
 * ��ü ���� ��ġ (�����ġ)
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
 * ��ü�� �Ӽ��� ���ϱ�
 */
function getObjectAttribute(obj, attrName) {
	var attrValue	= null;

	if (obj) {
		attrValue	= obj.getAttribute(attrName);
	}
	return attrValue;
}


/**
 * ��üID ���ϱ�
 */
function getObjectId(obj) {
	var objectId = getObjectAttribute(obj, "id");
	return objectId;
}


/**
 * ��ü�� �� ���� ��ġ�ϱ�
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
 * Attribute�� Value ���� (name=value ���� value ����
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
 * �̹��� ����
 */
function changeImage(imgObj, imgSrc) {
	imgObj.src = imgSrc;
}


/**
 * ���ڿ����� �����ڷ� ���ڿ� �и��ϱ�
 * @param str		���ڿ�
 * @param idx		�����ڸ� �������� n��° ���ڿ� ����
 * @param divideStr ���й���
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
 * null ���� ""���� ��ȯ
 */
function nullToEmpty(value) {
	if (value == null) {
		value = "";
	}
	return value;
}
/*******************************************************************************
	팝업박스 생성 스크립트

	사용법:
	1. 팝업박스 객체를 생성한다. 
		var popupboxname = new PopupBox("타이틀", "style=mormal,width=300,height=200,bgcolor=white,
				modal=yes,resizable=yes,move=yes,titlebar=yes,position=center,top=100,left=200");
			popupboxname:	생성된 테이블 객체
			style:		박스 스타일(normal: 이미지 디자인 적용 박스, simple:이미지 없는 박스)
			width: 		박스 넓이
			height: 	박스 높이
			bgcolor:	배경색 (기본값은 white)
			modal:		모달창 형식(yes/no)
			resizable:	박스 크기 변경 가능 여부(yes/no)
			move:		박스 이동 가능 여부(yes/no)
			titlebar:	타이틀바 표시 여부(yes/no)
			position:	박스를 표시할 위치(center:가운데, absolute:위치지정, mouse:마우스 클릭위치)
			top:		박스의 위쪽 위치(position=absolute 일 경우만 유효)
			left:		박스의 왼쪽 위치(position=absolute 일 경우만 유효)

	2. 팝업박스에 내용을 추가한다.
		popupboxname.addContents("박스에 내용을 추가합니다.<br>");

	3. 팝업창을 화면에 표시한다.
		showPopupBox(popupboxname);
			
	3. 팝업박스 내용을 모두 지운다.
		popupboxname.clear();

	4. 팝업박스를 닫는다.
		popupboxname.close();

	*주의:마우스로 클릭하여 팝업박스를 호출하지 않고 함수에 의해 자동으로 박스를 
	      호출할 경우에는 "position" 속성을 "center" 또는 "absolute"로 지정해야 한다.

	Author: Kim Kyoung shil
*******************************************************************************/

// 이미지 경로
var popupBoxImgPath			= "/img/1/icon/popupbox/";


// 윈도우 폰트 지정
var popupBoxFontSize		= "9pt";
var popupBoxFontFamily		= "굴림";
var popupBoxTitleColor		= "white";
var popupBoxTitleColorSimple = "black";

// 박스 테두리 설정 (style=simple 일 경우만 유효)
var popupBoxBorderColor		= "gray";	// 박스 테두리 라인 색상
var popupBoxBorderBgColor	= "silver";	// 테두리 배경색
var popupBoxBorderWidth		= 4;		// 테두리 두께	

var popupBoxAddCount		= 0;
var curPopupBox				= null;
var popupMoveBox			= null;
var popupMoveBoxHide		= null;
var popupBackBox			= null;

// 이미지 정의
var popupBoxTopLeftImg			= new Image();
var popupBoxTopCenterImg		= new Image();
var popupBoxTopRightImg			= new Image();		
var popupBoxTitleCenterImg		= new Image();
var popupBoxTitleLeftImg		= new Image();
var popupBoxTitleRightImg		= new Image();
var popupBoxLineLeftImg			= new Image();
var popupBoxLineRightImg		= new Image();
var popupBoxBottomLeftImg		= new Image();
var popupBoxBottomCenterImg		= new Image();
var popupBoxBottomRightImg		= new Image();
var popupBoxWinCloseOnImg		= new Image();
var popupBoxWinCloseOffImg		= new Image();
popupBoxTopLeftImg.src			= popupBoxImgPath+"popupbox_top_left.gif";
popupBoxTopCenterImg.src		= popupBoxImgPath+"popupbox_top_center.gif";
popupBoxTopRightImg.src			= popupBoxImgPath+"popupbox_top_right.gif";	
popupBoxTitleLeftImg.src		= popupBoxImgPath+"popupbox_title_left.gif";
popupBoxTitleCenterImg.src		= popupBoxImgPath+"popupbox_title_center.gif";
popupBoxTitleRightImg.src		= popupBoxImgPath+"popupbox_title_right.gif";
popupBoxLineLeftImg.src			= popupBoxImgPath+"popupbox_line_left.gif";
popupBoxLineRightImg.src		= popupBoxImgPath+"popupbox_line_right.gif";
popupBoxBottomLeftImg.src		= popupBoxImgPath+"popupbox_bottom_left.gif";
popupBoxBottomCenterImg.src		= popupBoxImgPath+"popupbox_bottom_center.gif";
popupBoxBottomRightImg.src		= popupBoxImgPath+"popupbox_bottom_right.gif";
popupBoxWinCloseOnImg.src		= popupBoxImgPath+"win_close_on.gif";
popupBoxWinCloseOffImg.src		= popupBoxImgPath+"win_close_off.gif";
	
	
/**
 * 팝업박스 생성
 */
function PopupBox(title, attribute) {
	var width		= getAttributeValue(attribute, "width");
	var height		= getAttributeValue(attribute, "height");
	var bgcolor		= getAttributeValue(attribute, "bgcolor");
	var resizable	= getAttributeValue(attribute, "resizable");
	var move		= getAttributeValue(attribute, "move");
	var modal		= getAttributeValue(attribute, "modal");
	var position	= getAttributeValue(attribute, "position");
	var top			= getAttributeValue(attribute, "top");
	var left		= getAttributeValue(attribute, "left");
	var titlebar	= getAttributeValue(attribute, "titlebar");
	var style		= getAttributeValue(attribute, "style");
	
	if (resizable == "yes" || resizable == "y" || resizable == "true") {
		resizable = "yes";
	}
	if (bgcolor == "") {
		bgcolor = "white";
	}
	if (position == "") {
		position = "center";
	}
	if (top == "") {
		top = "0";
	}
	if (left == "") {
		left = "0";
	}
	if (move == "yes" || move == "y" || move == "true") {
		move = "yes";
	}
	if (modal == "yes" || modal == "y" || modal == "true") {
		modal = "yes";
	}
	if (titlebar == "yes" || titlebar == "y" || titlebar == "true") {
		titlebar = "yes";
	}
	if (style == "") {
		style = "normal";
	}

	popupBoxAddCount += 1;
	var popupBoxId	= "POPUPBOX" + popupBoxAddCount;

	document.write("<div id='"+popupBoxId+"'></div>");
	var newPopupBox = getObject(popupBoxId);
	newPopupBox.id		= popupBoxId;
	newPopupBox.style.display	= "none";
	newPopupBox.style.position	= "absolute";
	newPopupBox.width			= width;
	newPopupBox.height			= height;
	newPopupBox.positionX		= 0;
	newPopupBox.positionY		= 0;
	newPopupBox.position		= position;
	newPopupBox.top				= top;
	newPopupBox.left			= left;
	newPopupBox.move			= move;
	newPopupBox.modal			= modal;
	newPopupBox.boxStyle		= style;

	var wResize 	= "default";
	var eResize 	= "default";
	var sResize 	= "default";
	var swResize 	= "default";
	var seResize 	= "default";
	if (resizable == "yes") {
		wResize		= "w-resize";
		eResize		= "e-resize";
		sResize		= "s-resize";
		swResize	= "sw-resize";
		seResize 	= "se-resize'";
	}

	var popupBoxContent = "";
	var contentsWidth	= parseInt(width);
	var contentsHeight	= parseInt(height);
	
	if (style == "normal") {
		var titleHeight 		= popupBoxTitleCenterImg.height;
		if (titlebar != "yes") {
			titleHeight			= popupBoxTopCenterImg.height;
		}
		contentsWidth 			= contentsWidth - popupBoxLineLeftImg.width - popupBoxLineRightImg.width;
		contentsHeight 			= contentsHeight - titleHeight - popupBoxBottomCenterImg.height;
		var titleWidth			= contentsWidth;

		newPopupBox.modHeight	= titleHeight + popupBoxBottomCenterImg.height;
		newPopupBox.modWidth	= popupBoxLineLeftImg.width + popupBoxLineRightImg.width;
		
		popupBoxContent = "<table border=0 cellspacing=0 cellpadding=0>"
			+ "<tr>";
		if (titlebar == "yes") {
			popupBoxContent	+= "<td width='"+popupBoxLineLeftImg.width+"' background='"+popupBoxImgPath+"popupbox_title_left.gif'>"
				+ "<img src='"+popupBoxImgPath+"blank.gif' width='"+popupBoxLineLeftImg.width+"' height='1'></td>"
				+ "<td height='"+popupBoxTitleCenterImg.height+"' "
				+ "  background='"+popupBoxImgPath+"popupbox_title_center.gif'>"
				+ "  <table id='"+popupBoxId+"_title' border=0 cellspacing=0 cellpadding=0 "
				+ "    style='table-layout:fixed;width:"+titleWidth+"px;cursor:default;'>"
				+ "  <tr>"
				+ "    <td style='height:"+popupBoxTitleCenterImg.height+";padding-top:3px;font-size:"+popupBoxFontSize+";font-family:"+popupBoxFontFamily+";"
				+ "      color:"+popupBoxTitleColor+";font-weight:bold;vertical-align:middle;overflow:hidden;' nowrap"
				+ "      onmousedown=\"startPopupBoxReset(this, '"+popupBoxId+"', 'move')\">"+title+"</td>"
				+ "    <td align=right width=20><img src='"+popupBoxImgPath+"win_close_off.gif' "
				+ "        onMouseOver='changeImage(this, \""+popupBoxWinCloseOnImg.src+"\");' "
				+ "        onMouseOut='changeImage(this, \""+popupBoxWinCloseOffImg.src+"\");' "
				+ "      border=0 onclick='closePopupBox(\""+popupBoxId+"\")'></td>"
				+ "  </tr>"
				+ "  </table>"
				+ "</td>"
				+ "<td width='"+popupBoxLineRightImg.width+"' background='"+popupBoxImgPath+"popupbox_title_right.gif'>"
				+ "<img src='"+popupBoxImgPath+"blank.gif' width='"+popupBoxLineLeftImg.width+"' height='1'></td>";
		}
		else {
			popupBoxContent	+= "<td width='"+popupBoxTopLeftImg.width+"' background='"+popupBoxImgPath+"popupbox_top_left.gif'>"
			+ "<img src='"+popupBoxImgPath+"blank.gif' width='"+popupBoxLineLeftImg.width+"' height='1'></td>"
			+ "<td height='"+popupBoxTopCenterImg.height+"' background='"+popupBoxImgPath+"popupbox_top_center.gif' "
			+ "  onmousedown=\"startPopupBoxReset(this, '"+popupBoxId+"', 'move')\"></td>"
			+ "<td width='"+popupBoxTopRightImg.width+"' background='"+popupBoxImgPath+"popupbox_top_right.gif'>"
			+ "<img src='"+popupBoxImgPath+"blank.gif' width='"+popupBoxLineLeftImg.width+"' height='1'></td>";
		}
			
		popupBoxContent	+= "</tr>"
			+ "<tr>"
			+ "  <td width='"+popupBoxLineLeftImg.width+"' background='"+popupBoxImgPath+"popupbox_line_left.gif'"
			+ "    style='cursor:"+wResize+";' onmousedown='startPopupBoxReset(this, \""+popupBoxId+"\")'>"
			+ "  </td>"
			+ "  <td>"
			+ "    <div id='"+popupBoxId+"_contents' style='width:"+contentsWidth+"px;height:"+contentsHeight+"px;"
			+ "      font-size:"+popupBoxFontSize+";font-family:"+popupBoxFontFamily+";"
			+ "      background-color:"+bgcolor+";overflow:auto;'></div>"
			+ "  </td>"
			+ "  <td width='"+popupBoxLineRightImg.width+"' background='"+popupBoxImgPath+"popupbox_line_right.gif'"
			+ "    style='cursor:"+eResize+";' onmousedown='startPopupBoxReset(this, \""+popupBoxId+"\")'>"
			+ "  </td>"			
			+ "</tr>"
			+ "<tr>"
			+ "  <td width='"+popupBoxLineLeftImg.width+"' background='"+popupBoxImgPath+"popupbox_bottom_left.gif'"
			+ "    style='cursor:"+swResize+";' onmousedown='startPopupBoxReset(this, \""+popupBoxId+"\")'>"
			+ "  </td>"
			+ "  <td height='"+popupBoxBottomCenterImg.height+"' style='cursor:"+sResize+";'"
			+ "    background='"+popupBoxImgPath+"popupbox_bottom_center.gif' "
			+ "    onmousedown='startPopupBoxReset(this, \""+popupBoxId+"\")'>"
			+ "  </td>"
			+ "  <td width='"+popupBoxLineRightImg.width+"' background='"+popupBoxImgPath+"popupbox_bottom_right.gif' "
			+ "    style='cursor:"+seResize+";' onmousedown='startPopupBoxReset(this, \""+popupBoxId+"\")'>"
			+ "  </td>"			
			+ "</tr>"
			+ "</table>";
	}
	else {
		newPopupBox.style.border 			= "1px solid "+popupBoxBorderColor;
		newPopupBox.style.backgroundColor	= popupBoxBorderBgColor;
		newPopupBox.style.width				= width+"px";
		newPopupBox.style.height			= height+"px";
		var contentsWidth	= parseInt(width)  - (popupBoxBorderWidth * 2);
		var contentsHeight	= parseInt(height);
		
		if (titlebar == "yes") {
			contentsHeight	= contentsHeight - popupBoxBorderWidth - 20;
			if (browserType == "NE") {
				contentsHeight -= 1;
			}
			
			popupBoxContent += "<table border=0 cellspacing=0 cellpadding=0 width=100% style='table-layout:fixed;'><tr>"
				+ "<td width="+popupBoxBorderWidth+"><img src='"+popupBoxImgPath+"blank.gif' width='"+popupBoxBorderWidth+"' height='1'></td>"
				+ "<td id='"+popupBoxId+"_title' height=20 style='font-size:"+popupBoxFontSize+";font-family:"+popupBoxFontFamily+";cursor:default;"
				+ "  color:"+popupBoxTitleColorSimple+";font-weight:bold;vertical-align:middle;overflow:hidden;white-space:nowrap;'"
				+ "  onmousedown=\"startPopupBoxReset(this, '"+popupBoxId+"', 'move')\">"+title+"</td>"
				+ "<td align=right width=20><img src='"+popupBoxImgPath+"win_close_off.gif' "
				+ "  onMouseOver='changeImage(this, \""+popupBoxWinCloseOnImg.src+"\");' "
				+ "  onMouseOut='changeImage(this, \""+popupBoxWinCloseOffImg.src+"\");' "
				+ "  border=0 onclick='closePopupBox(\""+popupBoxId+"\")'></td>"
				+ "<td width="+popupBoxBorderWidth+"><img src='"+popupBoxImgPath+"blank.gif' width='"+popupBoxBorderWidth+"' height='1'></td>"
				+ "</tr></table>";
		}
		else {
			contentsHeight	= contentsHeight - (popupBoxBorderWidth * 2);
			if (browserType == "NE") {
				contentsHeight -= 1;
			}
			
			popupBoxContent += "<table border=0 cellspacing=0 cellpadding=0 width=100% style='table-layout:fixed;'"
				+ "  onmousedown=\"startPopupBoxReset(this, '"+popupBoxId+"', 'move')\"><tr>"
				+ "<td width="+popupBoxBorderWidth+" height="+popupBoxBorderWidth+">"
				+ "<img src='"+popupBoxImgPath+"blank.gif' width='"+popupBoxBorderWidth+"' height='1'></td>"
				+ "<td id='"+popupBoxId+"_title' height="+popupBoxBorderWidth+"></td>"
				+ "<td width="+popupBoxBorderWidth+" height="+popupBoxBorderWidth+">"
				+ "<img src='"+popupBoxImgPath+"blank.gif' width='"+popupBoxBorderWidth+"' height='1'></td>"
				+ "</tr></table>"
		}
		
		popupBoxContent += "<table border=0 cellspacing=0 cellpadding=0 width=100%><tr>"
			+ "<td width="+popupBoxBorderWidth+" style='cursor:"+wResize+";' "
			+ "  onmousedown='startPopupBoxReset(this, \""+popupBoxId+"\")'></td>"
			+ "<td>"
			+ "  <div id='"+popupBoxId+"_contents' style='width:"+contentsWidth+"px;height:"+contentsHeight+"px;"
			+ "    font-size:"+popupBoxFontSize+";font-family:"+popupBoxFontFamily+";"
			+ "    background-color:"+bgcolor+";border:1px solid "+popupBoxBorderColor+";overflow:auto;'></div>"
			+ "</div>"
			+ "<td width="+popupBoxBorderWidth+" style='cursor:"+eResize+";' "
			+ "  onmousedown='startPopupBoxReset(this, \""+popupBoxId+"\")'></td>"
			+ "</tr></table>"
			+ "<table border=0 cellspacing=0 cellpadding=0 width=100% style='table-layout:fixed;'><tr>"
			+ "<td width="+popupBoxBorderWidth+" height="+popupBoxBorderWidth+" style='cursor:"+swResize+";'"
			+ "  onmousedown='startPopupBoxReset(this, \""+popupBoxId+"\")'></td>"
			+ "<td width=90% height="+popupBoxBorderWidth+" style='cursor:"+sResize+";'"
			+ "  onmousedown='startPopupBoxReset(this, \""+popupBoxId+"\")'></td>"
			+ "<td width="+popupBoxBorderWidth+" height="+popupBoxBorderWidth+" style='cursor:"+seResize+";'"
			+ "  onmousedown='startPopupBoxReset(this, \""+popupBoxId+"\")'></td>"
			+ "</tr></table>";
		
	}

	newPopupBox.innerHTML	= popupBoxContent;

	newPopupBox.titleObj	= getObject(popupBoxId+"_title");
	if (newPopupBox.titleObj != null) {
		newPopupBox.titleObj.onselectstart  = function() { return false }
		newPopupBox.titleObj.ondragstart    = function() { return false }
	}
	
	newPopupBox.contents	= getObject(popupBoxId+"_contents");
	newPopupBox.contents.style.scrollbarFaceColor		= "#FFFFFF";
	newPopupBox.contents.style.scrollbarShadowColor		= "#FFFFFF"; 
	newPopupBox.contents.style.scrollbarHighlightColor	= "#FFFFFF"; 
	newPopupBox.contents.style.scrollbarTrackColor		= "#E9E9E9"; 
	newPopupBox.contents.style.scrollbarArrowColor		= "#A3A3A3";
	
	newPopupBox.addContents		= addPopupBoxContents;
	newPopupBox.addObject		= addPopupBoxObject;
	newPopupBox.clear			= clearPopupBoxContents;
	newPopupBox.close			= closePopupBox;
	newPopupBox.modWidth		= parseInt(width) - contentsWidth;
	newPopupBox.modHeight		= parseInt(height) - contentsHeight;
	
	initPopupBackBox();
	initPopupMoveBox();
	
		
	return newPopupBox;
}


/**
 * 팝업박스 내용 추가
 */
function addPopupBoxContents(content) {
	var oldContent = this.contents.innerHTML;
	this.contents.innerHTML = oldContent + content;
}


/**
 * 팝업박스에 객체 추가
 */
function addPopupBoxObject(obj) {
	this.contents.appendChild(obj);
}


/**
 * 팝업박스 내용 삭제
 */ 
function clearPopupBoxContents() {
	this.contents.innerHTML = "";
}


/**
 * 팝업박스 이동/사이즈 변경 시작
 */
function startPopupBoxReset(obj, popupBoxId, type) {
	type = nullToEmpty(type);
	if (type == "") {
		type = "resize";
	}

	curPopupBox = getObject(popupBoxId);
		
	if (type == "resize") {
		var resizeStyle = obj.style.cursor;
		if (resizeStyle.indexOf("resize") < 1) {
			return false;
		}
		
		resizeStyle = resizeStyle.substring(0, resizeStyle.indexOf("-"));
		curPopupBox.resizeStyle	= resizeStyle;
		document.onmousemove	= resizePopupBox;
		document.onmouseup		= stopPopupBoxResize;
		popupMoveBox.onmouseup	= stopPopupBoxResize;
	}
	else {
		if (curPopupBox.move != "yes") {
			return false;
		}

		document.onmousemove	= movePopupBox;
		document.onmouseup		= stopPopupBoxMove;
		popupMoveBox.onmouseup	= stopPopupBoxMove;
	}

	makeOnTop(curPopupBox);	
	document.onselectstart  = function() { return false }
	document.ondragstart    = function() { return false }
	
	var width = getWidth(curPopupBox);
	var height	= getHeight(curPopupBox);
	if (browserType == "NE") {
		width -= 6;
		height -= 6;
	}

	var pos = getPosition(curPopupBox);
	curPopupBox.positionX	= pos.x;
	curPopupBox.positionY	= pos.y;
	curPopupBox.positionX2	= pos.x + width; 
	curPopupBox.positionY2	= pos.y + height;
		
	popupMoveBox.style.cursor	= obj.style.cursor;
	moveObject(popupMoveBox, pos.y, pos.x);
	resizeObject(popupMoveBox, width, height);
	popupMoveBox.addPosX	= 0;
	popupMoveBox.addPosY	= 0;
	popupMoveBox.moveStart	= false;
	popupMoveBox.resizeStart	= false;

	if (browserType == "IE") {
		var mouseX = checkMouseX();
		var mouseY = checkMouseY();
		popupMoveBox.addPosX = mouseX - curPopupBox.positionX;
		popupMoveBox.addPosY = mouseY - curPopupBox.positionY; 
		popupMoveBox.moveStart 		= true;
		popupMoveBox.resizeStart	= true;
	}

	popupMoveBoxHide.style.cursor	= obj.style.cursor;
	moveObject(popupMoveBoxHide, pos.y, pos.x);
	resizeObject(popupMoveBoxHide, width, height);
	
	makeOnTop(popupMoveBoxHide);
	makeOnTop(popupMoveBox);
	onDisplay(popupMoveBoxHide);
	onDisplay(popupMoveBox);
}


/**
 * 팝업박스 이동
 */
function movePopupBox(evt) {
	var mouseX = checkMouseX(evt);
	var mouseY = checkMouseY(evt);
	
	if (popupMoveBox.moveStart == false) {
		popupMoveBox.addPosX = mouseX - curPopupBox.positionX;
		popupMoveBox.addPosY = mouseY - curPopupBox.positionY; 
		popupMoveBox.moveStart = true;
	}

	var posX = mouseX - popupMoveBox.addPosX;
	var posY = mouseY - popupMoveBox.addPosY;
	moveObject(popupMoveBox, posY, posX);
}


/**
 * 팝업박스 이동 완료
 */
function stopPopupBoxMove(evt) {
	if (popupMoveBox.moveStart == true) {
		var pos		= getPosition(popupMoveBox);
		moveObject(curPopupBox, pos.y, pos.x);
		offDisplay(popupMoveBox);
		offDisplay(popupMoveBoxHide);
	
		popupMoveBox.moveStart		= false;
		popupMoveBox.onmouseup		= null;
		document.onmousemove		= null;
		document.onmouseup			= null;	
		document.body.focus();
	}
}


/**
 * 팝업박스 사이즈 변경 완료
 */
function stopPopupBoxResize() {
	if (popupMoveBox.resizeStart == true) {
		var pos			= getPosition(popupMoveBox);
		var boxWidth	= getWidth(popupMoveBox);
		var boxHeight	= getHeight(popupMoveBox);
		var width 		= boxWidth - curPopupBox.modWidth;
		var height		= boxHeight - curPopupBox.modHeight;
		var titleWidth = width;
	
		if (curPopupBox.boxStyle == "normal") {
			resizeWidth(curPopupBox.titleObj, titleWidth);
		}
		else {
			height -= 2;
			if (browserType == "NE") {
				width -= 2;
				boxWidth  -= 2;
				boxHeight -= 2;
			}
			resizeObject(curPopupBox, boxWidth, boxHeight);
		}
	
		resizeObject(curPopupBox.contents, width, height);
	
		moveObject(curPopupBox, pos.y, pos.x);
		offDisplay(popupMoveBox);
		offDisplay(popupMoveBoxHide);
		
		curPopupBox.width			= width;
		curPopupBox.height			= height;
	
		popupMoveBox.resizeStart	= false;
		popupMoveBox.onmouseup		= null;
		document.onmousemove		= null;
		document.onmouseup			= null;	
		document.body.focus();
	}
}


/**
 * 팝업박스 사이즈 변경
 */
function resizePopupBox(evt) {
	if (popupMoveBox.resizeStart == false) {
		popupMoveBox.resizeStart = true;
	}

	var mouseX = checkMouseX(evt);
	var mouseY = checkMouseY(evt);
	var posX	= curPopupBox.positionX;
	var posY	= curPopupBox.positionY;

	var width	= getWidth(curPopupBox);
	var height	= getHeight(curPopupBox);
	if (browserType == "NE") {
		width -= 6;
		height -= 6;
	}
	
	if (curPopupBox.resizeStyle.indexOf("e") > -1) {
		width	= mouseX - curPopupBox.positionX;
	}
	if (curPopupBox.resizeStyle.indexOf("s") > -1) {
		height	= mouseY - curPopupBox.positionY;
	}
	if (curPopupBox.resizeStyle.indexOf("w") > -1) {
		width	= curPopupBox.positionX2 - mouseX;
	}

	if (width > 100) {
		resizeWidth(popupMoveBox, width);
	}
	if (height > 50) {
		resizeHeight(popupMoveBox, height);
	}
	if (width > 100 && height > 50 && curPopupBox.resizeStyle.indexOf("w") > -1) {
		moveObject(popupMoveBox, posY, mouseX);
	}
}


/**
 * 팝업박스 닫기
 */
function closePopupBox(objId) {
	if (objId) {
		var popupBoxObj = getObject(objId);
		offDisplay(popupBoxObj);
	}
	else {
		offDisplay(this);
	}
	offDisplay(popupBackBox);
}


/**
 * 이동박스 초기화
 */
function initPopupMoveBox() {
	if (popupMoveBox == null) {
		document.write("<div id='popupMoveBox'></div>");
		popupMoveBox = getObject("popupMoveBox");
		popupMoveBox.style.position			= "absolute";
		popupMoveBox.style.border			= "3px black solid";
		popupMoveBox.style.background		= "white";
		popupMoveBox.style.display			= "none";
		popupMoveBox.style.filter			= "alpha(opacity=30)";
		popupMoveBox.addPosX				= 0;
		popupMoveBox.addPosY				= 0;
		popupMoveBox.moveStart				= false;
		if (browserType == "NE") {
			popupMoveBox.style.border		= "3px #808080 solid";
			popupMoveBox.style.background	= "none";
		}
		
		document.write("<div id='popupMoveBoxHide'></div>");
		popupMoveBoxHide = getObject("popupMoveBoxHide");
		popupMoveBoxHide.style.position			= "absolute";
		popupMoveBoxHide.style.background		= "white";
		popupMoveBoxHide.style.display			= "none";
		popupMoveBoxHide.style.filter			= "alpha(opacity=0)";
		if (browserType == "NE") {
			popupMoveBoxHide.style.background	= "none";
		}
	}
}


/**
 * 배경 박스 초기화
 */
function initPopupBackBox() {
	if (popupBackBox == null) {
		document.write("<div id='popupBackBox'></div>");
		popupBackBox = getObject("popupBackBox");
		popupBackBox.style.position			= "absolute";
		popupBackBox.style.top				= "0px";
		popupBackBox.style.left				= "0px";
		popupBackBox.style.background		= "white";
		popupBackBox.style.display			= "none";
		popupBackBox.style.filter			= "alpha(opacity=0)";
		if (browserType == "NE") {
			popupBackBox.style.background	= "none";
		}
	}
}


/**
 * 팝업박스 호출
 */
function displayPopupBox(evt) {
	if (evt != "center" && evt != "absolute") {
		var mouseX = checkMouseX(evt);
		var mouseY = checkMouseY(evt)
		moveObject(curPopupBox, mouseY-5, mouseX-5);	
	}
	else if (evt == "center") {
		var winLeft		= (getWindowWidth() - curPopupBox.width) / 2;
		var winTop		= (getWindowHeight() - curPopupBox.height) / 2;
		moveObject(curPopupBox, winTop, winLeft);
	}
	else if (evt == "absolute") {
		moveObject(curPopupBox, curPopupBox.top, curPopupBox.left);
	}

	var bodyHeight = getHeight(document.body);
	if (curPopupBox.modal == "yes") {
		resizeObject(popupBackBox, document.body.scrollWidth, document.body.scrollHeight);
		onDisplay(popupBackBox);
	}

	makeOnTop(curPopupBox);	
	onDisplay(curPopupBox);
	curPopupBox.focus();
	document.onclick = null;
}


/**
 * 팝업박스 표시 호출
 */
function showPopupBox(popupBoxObj) {
	if (!popupBoxObj.id) {
		popupBoxObj = eval(popupBoxObj);
	}

	if (popupBoxObj != null) {
		curPopupBox = popupBoxObj;
		if (curPopupBox.position == "mouse") {
			document.onclick = displayPopupBox;
		}
		else if (curPopupBox.position == "absolute") {
			displayPopupBox("absolute");
		}
		else if (curPopupBox.position == "center") {
			displayPopupBox("center");
		}
	}
}



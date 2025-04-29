/*******************************************************************************
	ContextMenu 생성을 위한 스크립트
	
	사용법:
	1. ContextMenu 객체를 생성한다.
		ex) var menu1 = ContextMenu("right");
			button:		left: 	마우스 왼쪽 버튼
						right:	마우스 오른쪽 버튼 (빈값인 경우 right 적용)
			
	2. 메뉴 아이템을 추가한다.
		ex) addContextMenuItem(menuObj, itemText, itemValue, func, icon);
			menuObj		: 메뉴 아이템을 추가할 대상 ContextMenu 객체
			itemText	: 메뉴 아이템 텍스트
			itemValue	: 메뉴선택시 전달할 메뉴값
			func		: 메뉴 선택시 호출할 함수명
			icon		: 메뉴에 표시할 아이콘 이미지명(이미지명만 입력, 사이즈(16 x 16),
						  이미지는 "contextMenuImgPath"에 지정된 경로에 저장)
	
	3. 메뉴 구분자를 추가한다.
		ex) addContextMenuItem(menuObj);
			menuObj		: 메뉴 구분자를 추가할 대상 ContextMenu 객체

	4. ContextMenu를 호출한다.
		ex) onmousedown="showContextMenu(MENU_OBJECT);"
			MENU_OBJECT	: 생성된 ContextMenu 객체
			<td style="cursor:default" onmousedown="showContextMenu(menu1)">테스트</td>

	5. 현재 선택된 메뉴의 값을 가져온다.
		ex) var value = getContextMenuValue();

	Author: Kim Kyoung shil
*******************************************************************************/


// 메뉴 이미지 경로
var contextMenuImgPath		= "/lmsdata/images/contextmenu/";

// 메뉴 폰트 지정
var contextMenuFontSize		= "9pt";
var contextMenuFontFamily	= "굴림";

// 메뉴 색상 지정
var contextMenuBgColor		= "#F9F8F7";	// 배경색
var contextMenuLineColor	= "#808080";	// 테두리색
var contextMenuOverColor	= "#B6BDD2";	// 마우스 오버 색
var contextMenuOverLineColor= "#0A246A";	// 마우스 오버 테두리색
var contextMenuIconBgColor	= "#E0DED8";	// 아이콘 표시영역 배경색

var curContextMenu			= null;		// 선택된 메뉴 객체
var curContextMenuDisplay	= null;
var contextMenuAddCount		= 0;


/**
 * Context 메뉴 객체를 생성한다.
 * @param button		버튼(left/right 빈값일 경우 "right" 값 적용)
 */
function ContextMenu(button) {
	contextMenuAddCount += 1;

	button = nullToEmpty(button).toLowerCase();
	if (button == "right" || button == "") {
		button = "right";
	}
	else {
		button = "left";
	}

	var contextMenuId = "CTXMENU"+contextMenuAddCount;
	document.write("<div id='"+contextMenuId+"'> dd</div>");        // 현메소드를 호출하는 위치가 div 가 생성이 될수 있는 곳에 위치해야함..

	var newContextMenu						= getObject(contextMenuId);
	newContextMenu.id 						= contextMenuId;
	newContextMenu.style.position			= "absolute";
	newContextMenu.style.backgroundColor	= contextMenuBgColor;
	newContextMenu.style.border				= "1px solid "+contextMenuLineColor;
	newContextMenu.style.cursor				= "default";
	newContextMenu.style.visibility			= "hidden";
	newContextMenu.style.paddingLeft		= "2px";
	newContextMenu.style.paddingRight		= "2px";
	newContextMenu.style.paddingTop			= "2px";
	newContextMenu.style.paddingBottom		= "2px";
	newContextMenu.style.top				= "0px";
	newContextMenu.style.left				= "0px";

	var menuContent = "<table border=0 cellspacing=0 cellpadding=0><tr>"
		+ "<td id='"+contextMenuId+"_iconBox'></td>"
		+ "<td id='"+contextMenuId+"_menuBox'></td>"
		+ "</tr></table>";

	newContextMenu.innerHTML = menuContent;
	
	newContextMenu.itemNo 			= 0;
	newContextMenu.curItemId 		= "";
	newContextMenu.menuValue		= "";
	newContextMenu.button			= button;
	newContextMenu.iconBox			= getObject(contextMenuId+"_iconBox");
	newContextMenu.menuBox			= getObject(contextMenuId+"_menuBox");
	newContextMenu.onselectstart	= function() {return false}
	newContextMenu.ondragstart		= function() {return false}
	
	return newContextMenu;
}


/**
 * 메뉴 아이템 추가
 * @param menuObj		추가할 대상 메뉴 객체
 * @param itemText		아이템 텍스트
 * @param itemValue		아이템 값
 * @param func			아이템을 선택할때 실행할 함수 (파라메터는 지정하지 않는다.)
 * @param icon			아이콘 (파일명만 지정한다. 경로는 "contextMenuImgPath"에 정의)
 */
function addContextMenuItem(menuObj, itemText, itemValue, func, icon) {
	itemText	= nullToEmpty(itemText);
	itemValue	= nullToEmpty(itemValue);
	func		= nullToEmpty(func);
	icon		= nullToEmpty(icon);
	
	var menuId		= getObjectAttribute(menuObj, "id");
	var itemNo 		= menuObj.itemNo + 1; 
	menuObj.itemNo	= itemNo;
			
	if (icon !== "") {
		icon = "<img src='"+contextMenuImgPath+icon+"'>";
	}

	var iconItem	= document.createElement("div");
	iconItem.id		= menuId+"_icon"+itemNo;
	iconItem.style.width			= "20px";
	iconItem.style.backgroundColor	= contextMenuIconBgColor;
	iconItem.style.borderLeft		= "1px solid "+contextMenuIconBgColor;
	iconItemHeight					= 5;
	
	var iconContent = ""
	if (itemText !== "") {
		iconItem.style.borderTop		= "1px solid "+contextMenuIconBgColor;
		iconItem.style.borderBottom		= "1px solid "+contextMenuIconBgColor;
		iconItemHeight = 20;
	}

	iconContent	= "<table border=0 cellspacing=0 cellpadding=0 width=100% height="+iconItemHeight+"><tr>"
		+ "<td align='center'>"+icon+"</td>"
		+ "</tr></table>";

	iconItem.innerHTML = iconContent;
	menuObj.iconBox.appendChild(iconItem);

	var menuItem			= document.createElement("div");
	menuItem.id				= menuId+"_menu"+itemNo;
	
	var menuContent = "";
	if (itemText !== "") {
		menuItem.style.borderTop		= "1px solid "+contextMenuBgColor;
		menuItem.style.borderRight		= "1px solid "+contextMenuBgColor;
		menuItem.style.borderBottom		= "1px solid "+contextMenuBgColor;
	
		menuContent = "<table border=0 cellspacing=0 cellpadding=0 height=20 width=98%><tr>"
			+ "<td width=5><img src='"+contextMenuImgPath+"blank.gif' height=1 width=5></td>"
			+ "<td style='font-size:"+contextMenuFontSize+";font-family:"+contextMenuFontFamily+";"
			+ "line-height:20px' nowrap>"+itemText+"</td>"
			+ "<td width=15><img src='"+contextMenuImgPath+"blank.gif' height=1 width=15></td>"
			+ "</tr></table>";
		
		menuItem.onmousedown	= selectContextMenuItem;	
		menuItem.onmouseover	= onContextMenuColor;
		menuItem.onmouseout		= offContextMenuColor;
		menuItem.itemValue		= itemValue;
		menuItem.func			= func;
		menuItem.iconItem		= iconItem;
		menuItem.menuObj		= menuObj;
	}
	else {
		menuContent = "<table border=0 cellspacing=0 cellpadding=0 width=100% height=5><tr>"
			+ "<td width=5></td>"
			+ "<td valign=middle>"
			+ "<img src='"+contextMenuImgPath+"line.gif' height=1 width=100%>"
			+ "</td>"
			+ "</tr></table>";
	}	
	menuItem.innerHTML		= menuContent;
	
	menuObj.menuBox.appendChild(menuItem);
}


/**
 * 마우스 오버시 색상 변경
 */
function onContextMenuColor() {
	var menuItem	= this;
	var iconItem	= menuItem.iconItem;
	iconItem.style.backgroundColor	= contextMenuOverColor;
	iconItem.style.borderTop		= "1px solid "+contextMenuOverLineColor;
	iconItem.style.borderLeft		= "1px solid "+contextMenuOverLineColor;
	iconItem.style.borderBottom		= "1px solid "+contextMenuOverLineColor;

	menuItem.style.backgroundColor	= contextMenuOverColor;
	menuItem.style.borderTop		= "1px solid "+contextMenuOverLineColor;
	menuItem.style.borderRight		= "1px solid "+contextMenuOverLineColor;
	menuItem.style.borderBottom		= "1px solid "+contextMenuOverLineColor;
}


/**
 * 마우스 아웃시 색상 변경
 */
function offContextMenuColor() {
	var menuItem	= this;
	var iconItem	= menuItem.iconItem;
	iconItem.style.backgroundColor	= contextMenuIconBgColor;
	iconItem.style.borderTop		= "1px solid "+contextMenuIconBgColor;
	iconItem.style.borderLeft		= "1px solid "+contextMenuIconBgColor;
	iconItem.style.borderBottom		= "1px solid "+contextMenuIconBgColor;
	
	menuItem.style.backgroundColor	= contextMenuBgColor;
	menuItem.style.borderTop		= "1px solid "+contextMenuBgColor;
	menuItem.style.borderRight		= "1px solid "+contextMenuBgColor;
	menuItem.style.borderBottom		= "1px solid "+contextMenuBgColor;
}


/**
 * 메뉴 보이기
 * @param menuObj		표시할 메뉴 객체
 */
function showContextMenu(menuObj) {
	if (menuObj == null) {
		return false;
	}
	if (curContextMenu != null && curContextMenu.id != menuObj.id) {
		hideContextMenu();
	}

	document.oncontextmenu	= function() {return false}
	curContextMenu 			= menuObj;
	curContextMenuDisplay	= menuObj;
	document.onmousedown 	= displayContextMenu;
}


/**
 * 아이템 선택
 */
function selectContextMenuItem() {
	curContextMenu			= this.menuObj;
	this.menuObj.menuValue	= this.itemValue;
	hideContextMenu();
	eval(this.func);
}


/**
 * 메뉴 표시
 */
function displayContextMenu(evt) {
	var mouseButton = getMouseButton(evt);

	if (curContextMenuDisplay == null || mouseButton != curContextMenu.button) {
		hideContextMenu();
		return false;	
	}

	hiddenObject(curContextMenu);
	onDisplay(curContextMenu);

	var mouseX		= checkMouseX(evt);
	var mouseY		= checkMouseY(evt);
    var rightEdge	= getWindowWidth() - mouseX;
	var bottomEdge	= getWindowHeight() - mouseY;
	var menuWidth	= getWidth(curContextMenu);
	var menuHeight	= getHeight(curContextMenu);

	var leftPosition	= 0;
	var topPosition		= 0;

	if (rightEdge < menuWidth) {
		if (browserType == "IE") {
			leftPosition = mouseX - menuWidth + getScrollX() + 5;
		}
		else {
			leftPosition = mouseX - menuWidth + 5;
		}
	}
	else {
		if (browserType == "IE") {
			leftPosition = mouseX + getScrollX() - 5;
		}
		else {
			leftPosition = mouseX - 5;
		}
	}

	if (bottomEdge < menuHeight) {
		if (browserType == "IE") {
			topPosition = mouseY - menuHeight + getScrollY() + 5;
		}
		else {
			topPosition = mouseY - menuHeight + 5;
		}
	}
	else {
		if (browserType == "IE") {
			topPosition = mouseY + getScrollY() - 5;
		}
		else {
			topPosition = mouseY - 5;
		}
	}

	moveObject(curContextMenu, topPosition, leftPosition);
	visibleObject(curContextMenu);
	curContextMenuDisplay = null;

	return false;
}


/**
 * 메뉴 숨김
 */
function hideContextMenu() {
	if (curContextMenu != null) {
		offDisplay(curContextMenu);
		document.onmousedown	= null;
		document.oncontextmenu	= null;
	}
}


/**
 * 현재 선택된 메뉴의 값 가져오기
 */
function getContextMenuValue() {
	var value = "";
	if (curContextMenu !== null) {
		value = curContextMenu.menuValue;
	}
	return value;
}

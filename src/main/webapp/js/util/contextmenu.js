/*******************************************************************************
	ContextMenu ������ ���� ��ũ��Ʈ
	
	����:
	1. ContextMenu ��ü�� �����Ѵ�.
		ex) var menu1 = ContextMenu("right");
			button:		left: 	���콺 ���� ��ư
						right:	���콺 ������ ��ư (���� ��� right ����)
			
	2. �޴� �������� �߰��Ѵ�.
		ex) addContextMenuItem(menuObj, itemText, itemValue, func, icon);
			menuObj		: �޴� �������� �߰��� ��� ContextMenu ��ü
			itemText	: �޴� ������ �ؽ�Ʈ
			itemValue	: �޴����ý� ������ �޴���
			func		: �޴� ���ý� ȣ���� �Լ���
			icon		: �޴��� ǥ���� ������ �̹�����(�̹����� �Է�, ������(16 x 16),
						  �̹����� "contextMenuImgPath"�� ������ ��ο� ����)
	
	3. �޴� �����ڸ� �߰��Ѵ�.
		ex) addContextMenuItem(menuObj);
			menuObj		: �޴� �����ڸ� �߰��� ��� ContextMenu ��ü

	4. ContextMenu�� ȣ���Ѵ�.
		ex) onmousedown="showContextMenu(MENU_OBJECT);"
			MENU_OBJECT	: ������ ContextMenu ��ü
			<td style="cursor:default" onmousedown="showContextMenu(menu1)">�׽�Ʈ</td>

	5. ���� ���õ� �޴��� ���� �����´�.
		ex) var value = getContextMenuValue();

	Author: Kim Kyoung shil
*******************************************************************************/


// �޴� �̹��� ���
var contextMenuImgPath		= "/lmsdata/images/contextmenu/";

// �޴� ��Ʈ ����
var contextMenuFontSize		= "9pt";
var contextMenuFontFamily	= "����";

// �޴� ���� ����
var contextMenuBgColor		= "#F9F8F7";	// ����
var contextMenuLineColor	= "#808080";	// �׵θ���
var contextMenuOverColor	= "#B6BDD2";	// ���콺 ���� ��
var contextMenuOverLineColor= "#0A246A";	// ���콺 ���� �׵θ���
var contextMenuIconBgColor	= "#E0DED8";	// ������ ǥ�ÿ��� ����

var curContextMenu			= null;		// ���õ� �޴� ��ü
var curContextMenuDisplay	= null;
var contextMenuAddCount		= 0;


/**
 * Context �޴� ��ü�� �����Ѵ�.
 * @param button		��ư(left/right ���� ��� "right" �� ����)
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
	document.write("<div id='"+contextMenuId+"'> dd</div>");        // ���޼ҵ带 ȣ���ϴ� ��ġ�� div �� ������ �ɼ� �ִ� ���� ��ġ�ؾ���..

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
 * �޴� ������ �߰�
 * @param menuObj		�߰��� ��� �޴� ��ü
 * @param itemText		������ �ؽ�Ʈ
 * @param itemValue		������ ��
 * @param func			�������� �����Ҷ� ������ �Լ� (�Ķ���ʹ� �������� �ʴ´�.)
 * @param icon			������ (���ϸ� �����Ѵ�. ��δ� "contextMenuImgPath"�� ����)
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
 * ���콺 ������ ���� ����
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
 * ���콺 �ƿ��� ���� ����
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
 * �޴� ���̱�
 * @param menuObj		ǥ���� �޴� ��ü
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
 * ������ ����
 */
function selectContextMenuItem() {
	curContextMenu			= this.menuObj;
	this.menuObj.menuValue	= this.itemValue;
	hideContextMenu();
	eval(this.func);
}


/**
 * �޴� ǥ��
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
 * �޴� ����
 */
function hideContextMenu() {
	if (curContextMenu != null) {
		offDisplay(curContextMenu);
		document.onmousedown	= null;
		document.oncontextmenu	= null;
	}
}


/**
 * ���� ���õ� �޴��� �� ��������
 */
function getContextMenuValue() {
	var value = "";
	if (curContextMenu !== null) {
		value = curContextMenu.menuValue;
	}
	return value;
}

/*******************************************************************************
	Tree ������ ���� ��ũ��Ʈ

	����:
	1. Ʈ�� ��ü�� �����Ѵ�. 
		var TREENAME = Tree(targetName, text, value, color, func, attribute);
			targetName:	Ʈ���� �߰��� ��� ��ü�� �Ǵ� ��ü (������ ��� ������ġ�� ǥ��)
			text:		Ʈ�� ��Ʈ ��忡 ǥ���� �ؽ�Ʈ
			value:		Ʈ�� ��Ʈ ��尪
			color:		Ʈ�� ��Ʈ ��� ����
			func:		Ʈ�� �������� ���������� ������ �Լ�, �Ķ���� ���� �Լ�.
			attribute:	Ʈ���� �Ӽ�
				opendepth:	�ʱ⿡ ������ ����� ����(�ܰ�)
				icontype:	Ʈ���� ������ ǥ������ (������ ��� 'item'���� ����
					auto: �ڵ�, �θ���� �ڽĳ�带 �����ؼ� ǥ��,
					folder: �������� ��� �������������� ǥ��, 
					icon: �⺻ ������ ǥ��(icon.gif)
					item: �������� ��� ������ ���������� ǥ��(item.gif)
					foldericon: �θ��带 �������������� ǥ���ϰ�, �ڽĳ��� ���������� ǥ��
				foldertype:	������ ǥ������(
					simple: ������ �׸��� �ʰ� ������ �����ϰ� ǥ�� 
					normal: ������ ������ ǥ��, �������� ������ �������� �ӵ��� ������.
				rootdisplay: ��Ʈ����� ǥ�ÿ���(yes:ǥ��, no:ǥ�þ���)
				ontitle: Ÿ��Ʋ ǳ������ ǥ�� ���� (yes/no, �⺻���� "no")
		ex) tree1 = Tree("treeBox1", "Ʈ��1", "root", "click1()", "openDepth=1,icontype=auto,folder=simple,rootdisplay=yes,ontitle=yes");

	2. Ʈ�� �������� �߰��Ѵ�.
		var ITEMNAME = addTreeItem(parentNode, itemText, itemValue, itemStyle, iconName)
			parentNode:	�߰��� ��� ��ü(�θ� ���, Ʈ�� ��ü �Ǵ� ������ ��ü)
			itemText:	Ʈ�� �������� ǥ���� �ؽ�Ʈ
			value:		�����۰�
			color:		������ ���� (�⺻���� ����)
			icon:		�����ۿ� ǥ���� ������ �̹����� (�̹����� "treeImgPath"�� ������ ��ο� �־�� �ϸ�,
						�̹����� �Է�)
			selected:	������ �⺻ ����(yes|no ���� ������ "no"�� ����, ����:���̺��� 1�� ���Ϸ� ���õǾ�� �Ѵ�.)
		ex) item1 = addTreeItem(TREENAME, "��1��", "��1", "blue", "folder.gif", "no");
	
	3. ������ �����۰� ��������
		getTreeItemValue(treeObj);
			treeObj:	Ʈ�� ��ü (1.���� ������ TREENAME) 
		ex) var value = getTreeItemValue(TREENAME);
			2.�� ������ ������ �������� �������� ��� value�� "��1"�� �ȴ�.

	4. ������ ������ �ؽ�Ʈ ��������
		getTreeItemText(treeObj);
			treeObj:	Ʈ�� ��ü (1.���� ������ TREENAME) 
		ex) var value = getTreeItemValue(TREENAME);
			2.�� ������ ������ �������� �������� ��� value�� "��1��"�� �ȴ�.

	5. ������ ������ �ܰ� ��������
		getTreeItemDepth(treeObj);

	6. ������ ������ ���� ��� �� ��������
		getTreeItemParentValue(treeObj);


	Author: Kim Kyoung shil
*******************************************************************************/

// ������ �̹��� ���
var treeImgPath				= "/img/1/icon/tree/";

// Ʈ�� ��Ʈ ����
var treeFontSize			= "9pt";
var treeFontFamily			= "����";

// line ���� ����
var treeLineHeight			= "18px";

// ���콺 ������ ����
var treeOverColor			= "lightgrey";

var treeRootImg				= new Image();					// Tree Root ������
treeRootImg.src				= treeImgPath+"folder2.gif";

var treeFolderImg			= new Image();					// ���� ������
treeFolderImg.src			= treeImgPath+"folder2.gif";

var treeItemImg				= new Image();					// ������ ������
treeItemImg.src				= treeImgPath+"item2.gif";

var treeIconImg				= new Image();					// ������
treeIconImg.src				= treeImgPath+"icon.gif";

var treeBlankImg			= new Image();
treeBlankImg.src			= treeImgPath+"blank.gif";

var treeFolderPlusImg		= new Image();
treeFolderPlusImg.src		= treeImgPath+"plus_simple.gif";

var treeFolderMinusImg		= new Image();
treeFolderMinusImg.src		= treeImgPath+"minus_simple.gif";

var treePlusNodeImg			= new Image();
treePlusNodeImg.src			= treeImgPath+"plus_node.gif";

var treePlusEndImg			= new Image();
treePlusEndImg.src			= treeImgPath+"plus_end.gif";

var treeMinusNodeImg		= new Image();
treeMinusNodeImg.src		= treeImgPath+"minus_node.gif";

var treeMinusEndImg			= new Image();
treeMinusEndImg.src			= treeImgPath+"minus_end.gif";

var treeLineNodeImg			= new Image();
treeLineNodeImg.src			= treeImgPath+"line_node.gif";

var treeLineEndImg			= new Image();
treeLineEndImg.src			= treeImgPath+"line_end.gif";

var treeLineImg				= new Image();
treeLineImg.src				= treeImgPath+"line.gif";

var treeAddCount			= 0;


/**
 * Ʈ����ü�� �����Ѵ�.
 *
 * @param targetName	Ʈ���� ������ ��� ��ü�� �Ǵ� ��ü (���� ��� ������ġ�� �����ȴ�.)
 * @param text			Ʈ���� ��Ʈ��� �ؽ�Ʈ
 * @param value			��Ʈ ������ ��
 * @param color			��Ʈ ������ ����
 * @param func			�������� Ŭ������ ��� ������ �Լ�
 * @param attribute		Ʈ�� �Ӽ�
 * 			opendepth	�ʱ⿡ ��ĥ Tree�� �ܰ��
 * 			icontype	Ʈ���� ������ ������ ����(folder:����, item:������, auto:�ڵ�)
 * 			foldertype	���� ���� (simple:�����ϰ�(�⺻��), normal:����)
 * 			rootdisplay	��Ʈ ��� ǥ�� ����(yes: ǥ��, no:ǥ�þ���, ���� yes)
 *			ontitle		Ÿ��Ʋ ǳ������ ǥ�� ���� (yes/no, �⺻���� "no")
 * @return newTree		������ Ʈ�� ��ü
 */
function Tree(targetName, text, value, color, func, contextmenu, attribute) {
	var openDepth		= getAttributeValue(attribute, "opendepth");
	var iconType		= getAttributeValue(attribute, "icontype");
	var folderType		= getAttributeValue(attribute, "foldertype");
	var rootDisplay		= getAttributeValue(attribute, "rootdisplay");
	var ontitle			= getAttributeValue(attribute, "ontitle");
	
	if (openDepth === "") {
		openDepth = "1";
	}

	if (iconType === "") {
		iconType = "auto";
	}

	if (folderType === "") {
		folderType = "simple";
	}

	if (rootDisplay === "") {
		rootDisplay = "yes";
	}
	
	if (nullToEmpty(color) === "") {
		color = "black";
	}
	
	if (ontitle === "") {
		ontitle = "no";
	}
	ontitle = ontitle.toLowerCase();
	if (ontitle == "yes" || ontitle == "true" || ontitle == "y") {
		ontitle = "yes";
	}
	
	treeAddCount += 1;

	// ���ο� Ʈ����ü ����
	var newTree = null;
	var treeId = "TREE"+treeAddCount;	
	if (targetName !== null && targetName !== "")	{
		newTree = document.createElement("div");
		newTree.id	= treeId;
		
		if (!targetName.id) {
			targetName = getObject(targetName);
		}
		
		targetName.appendChild(newTree);
	}
	else {
		document.write("<div id=\""+treeId+"\"></div>");
		newTree = getObject(treeId);
	}

	newTree.treeId		= treeId;
	newTree.depth		= 0;
	newTree.child		= 1;
	newTree.openDepth	= openDepth;
	newTree.iconType	= iconType;
	newTree.folderType	= folderType;
	newTree.endNodeId	= "";
	newTree.itemNo		= 0;
	newTree.func		= nullToEmpty(func);
	newTree.contextmenu	= nullToEmpty(contextmenu);
	newTree.curItemId	= "";
	newTree.value		= value;
	newTree.ontitle		= ontitle;
	newTree.onselectstart  = function() {return false;};
	newTree.ondragstart    = function() {return false;};
	
	var rootDisplayStyle = "block";
	if (rootDisplay == "no") {
		rootDisplayStyle = "none";
	}

	var onTitle = "";
	if (ontitle == "yes") {
		onTitle = "title=\""+text+"\"";
	}

	var treeContent = ""
		+ "<table border=0 cellpadding=0 cellspacing=0 style='display:"+rootDisplayStyle+"'><tr>"
		+ "<td><img id='"+treeId+"_icon' src='"+treeRootImg.src+"'></td>"
		+ "<td id='"+treeId+"_text' style='white-space:nowrap;cursor:default;padding-top:2px;"
		+ "padding-left:3px;padding-right:3px;font-size:"+treeFontSize+";font-family:"+treeFontFamily+";"
		+ "color:"+color+";cursor:pointer;' "+onTitle+">"+text+"</td>"
		+ "</tr></table>";
	
	newTree.innerHTML = treeContent;

	var newTreeText = getObject(treeId+"_text");
	newTreeText.onmousedown		= selectTreeItem;
	newTreeText.onmouseover		= onTreeItemColor;
	newTreeText.onmouseout		= offTreeItemColor;
	newTreeText.treeId 			= treeId;
	newTreeText.itemId 			= treeId;
	newTreeText.color			= color;

	var childNodeSet	= document.createElement("div");
	childNodeSet.id		= treeId+"_sub";
	childNodeSet.style.display = "block";
	newTree.appendChild(childNodeSet);

	return newTree;
}


/**
 * Ʈ���� �������� �߰��ϴ� �Լ�
 *
 * @param parentNode	�߰��� �θ��� ��ü
 * @param text			ǥ���� ������ �ؽ�Ʈ
 * @param value			������ ��
 * @param color			������ �ؽ�Ʈ�� ���� (���� ������ �⺻�� ����)
 * @param icon			������ �����ܸ� (���� ������ �⺻�� ����)
 * @param selected		������ �⺻ ����(yes/no ���� ������ "no"�� ����)
 * @return newItem		������ ������ ��ü
 */
function addTreeItem(parentNode, text, value, color, icon, selected) {
	value				= nullToEmpty(value);
	color				= nullToEmpty(color);
	icon				= nullToEmpty(icon);
	selected			= nullToEmpty(selected).toLowerCase();

	if (color == "") {
		color = "black";
	}
	
	if (selected == "") {
		selected = "no";
	}

	var parentId		= parentNode.id;
	var treeId			= parentNode.treeId;
	var depth			= parentNode.depth;
	var child			= parentNode.child;
	var prevEndNodeId	= parentNode.endNodeId;
	var ontitle			= parentNode.ontitle;

	var curTreeObj		= getObject(treeId);	// ���� Tree ��ü
	var openDepth		= curTreeObj.openDepth;	// ó���� ��ĥ Tree�� �ܰ��
	var iconType		= curTreeObj.iconType;	// ������ ����
	var folderType		= curTreeObj.folderType;
	var itemNo			= curTreeObj.itemNo + 1;
	var childNodeSet	= null;
	curTreeObj.itemNo	= itemNo;

	if (depth != null) {
		depth = depth + 1;
	}

	// Child ������ ���� ��� ����
	if (child == 0) {
		childNodeSet = document.createElement("div");
		childNodeSet.id		= parentId+"_sub";

		var childNodeSetImg = "";

		if (depth <= parseInt(openDepth)) { 
			childNodeSet.style.display = "block";

			if (folderType == "normal") {
				childNodeSetImg = treeMinusEndImg.src;				
			}
			else {
				childNodeSetImg = treeFolderMinusImg.src;
			}
		}
		else {
			childNodeSet.style.display = "none";

			if (folderType == "normal") {
				childNodeSetImg = treePlusEndImg.src;
			}
			else {
				childNodeSetImg = treeFolderPlusImg.src;
			}
		}

		parentNode.appendChild(childNodeSet);
		parentNode.child = 1;

		var parentChildObj = getObject(parentId+"_child");
		var childImgContent = "<img id='"+parentId+"_childimg' src='"+childNodeSetImg+"' "
							+ "onClick=\"changeTreeChildView(this, '"+parentId+"', '"+folderType+"')\">";
		parentChildObj.innerHTML = childImgContent;

		// Parent Node�� ������ ����
		if (iconType == "auto" || iconType == "foldericon") {
			var parentIconName = parentNode.icon;
			if (parentIconName == "") {
				var parentIcon = getObject(parentId+"_icon");
				parentIcon.src = treeFolderImg.src;
			}
		}
	}
	else {
		childNodeSet = getObject(parentId+"_sub");
	}

	// ������ ��ü ����
	var newItem = document.createElement("div");
	var itemId = treeId+"_"+itemNo;
	newItem.id				= itemId;
	newItem.treeId			= treeId;
	newItem.depth			= depth;
	newItem.child			= 0;
	newItem.endNodeId		= "";
	newItem.icon			= icon;
	newItem.value			= value;
	newItem.ontitle			= ontitle;
	newItem.parentId		= parentId;
	parentNode.endNodeId	= itemId;
	parentNode.child 		= child + 1;
	
	// depth�� ���� ���� �Ǵ� line �̹��� ���
	var depthImg = "";
	for (var i=1; i<depth; i+=1) {
		depthImg += "<img id='"+itemId+"_blank_"+i+"' src='"+treeBlankImg.src+"'>";
	}

	var itemImgSrc = "";
	// �����ۿ� ������ �������� �������� ���
	if (icon != "") {
		itemImgSrc	= treeImgPath + icon;
	}
	// �����ۿ� ������ �������� �������� �ʾ��� ��� �⺻ ���������� ����
	else {
		// Ʈ���� ������ ������ "folder"�� �������� ���
		if (iconType == "folder") {
			itemImgSrc = treeFolderImg.src;
		}
		// Ʈ���� ������ ������ "icon"���� �������� ���
		else if (iconType == "icon" || iconType == "foldericon") {
			itemImgSrc = treeIconImg.src;
		}
		// Ʈ���� ������ ������ "item"���� �������� ���
		else {
			itemImgSrc = treeItemImg.src;
		}
	}

	var haveChildImg = treeBlankImg;

	// Ʈ���� line�� ǥ���� ��� �̹��� ǥ�� ���
	if (folderType == "normal") {
		if (prevEndNodeId != "") {
			var prevItem			= getObject(prevEndNodeId);
			var prevItemChildImg	= getObject(prevEndNodeId+"_childimg");
			var prevChildValue		= prevItem.child;

			if (prevChildValue == 0) {
				prevItemChildImg.src = treeLineNodeImg.src;
			}
		}
		haveChildImg = treeLineEndImg.src;
	}
	// Ʈ���� �����ϰ� ǥ��
	else {
		haveChildImg = treeBlankImg.src;
	}

	var onTitle = "";
	if (ontitle == "yes") {
		onTitle = "title=\""+text+"\"";
	}
	
	var itemStyle = ""
		+ "white-space:nowrap;"
		+ "font-size:"+treeFontSize+";"
		+ "font-family:"+treeFontFamily+";"
		+ "height:"+treeLineHeight+";"
		+ "padding-top:2px;"
		+ "padding-left:3px;"
		+ "padding-right:3px;"
		+ "vertical-align:middle;"
		+ "overflow:hidden;"
		+ "cursor:default;"
		+ "color:"+color+";"
		+ "cursor:pointer;";

	var endNodeId	= parentNode.endNodeId;

	var itemContent = ""
		+ "<table border=0 cellpadding=0 cellspacing=0><tr>"
		+ "<td>"+depthImg+"</td>"
		+ "<td id='"+itemId+"_child'><img id='"+itemId+"_childimg' src='"+haveChildImg+"'></td>"
		+ "<td><img id='"+itemId+"_icon' src='"+itemImgSrc+"'></td>"
		+ "<td id='"+itemId+"_text' style='"+itemStyle+"' "+onTitle+">"+text+"</td>"
		+ "</tr></table>";

	newItem.innerHTML = itemContent;
	childNodeSet.appendChild(newItem);

	var newItemText = getObject(itemId+"_text");
	newItemText.onmousedown = selectTreeItem;
	newItemText.onmouseover	= onTreeItemColor;
	newItemText.onmouseout	= offTreeItemColor;	
	newItemText.treeId 		= treeId;
	newItemText.itemId 		= itemId;
	newItemText.color		= color;

	// line �׸��� ȣ��
	if (folderType == "normal") {
		var prevSiblingObj	= newItem.previousSibling;

		if (prevSiblingObj != null){
			drawTreeLine(prevSiblingObj, depth);
		}	
	}

	// �������� �̸� ���õǾ� ���� ��� ó��
	if (selected == "yes" || selected == "y" || selected == "true") {
		if (depth > openDepth) {
			changeSelectedTreeItem(parentId)
		}
		
		curTreeObj.curItemId = itemId;
		onSelectColor(newItemText);
		newItemText.overcolor = "on";	
	}

	return newItem;
}


/**
 * Ʈ���� ������ �׸��� �Լ�
 */
function drawTreeLine(obj, depth) {
	var list = obj.childNodes;
	var imglist = "";

	if (list != null) {
		for (var i=0; i<list.length; i+=1) {
			if (list.item(i).tagName == "DIV") {
				var itemSubObj = list.item(i);
				var objId = itemSubObj.id;
				var idx = objId.indexOf("_sub");
				var itemId = objId.substring(0, idx)

				if (idx > -1) {
					var itemObj = getObject(itemId);
					var haveChildImg = getObject(itemId+"_childimg");

					if (itemSubObj.style.display == "block") {
						haveChildImg.src = treeMinusNodeImg.src;
					}
					else {
						haveChildImg.src = treePlusNodeImg.src;
					}

					drawTreeLine(itemSubObj, depth);
				}
				else if (objId.indexOf("_value") > -1) {

				}
				else {
					var treeBlankImg = getObject(objId+"_blank_"+depth);
					treeBlankImg.src = treeLineImg.src;

					var subObj = getObject(objId+"_sub");
					if (subObj != null) {
						drawTreeLine(subObj, depth);
					}
				}
			}
		}
	}
}


/**
 * �������� �̸� ���õǾ� ���� ��� ó��
 */
function changeSelectedTreeItem(itemId) {
	var itemObj 	= getObject(itemId);
	var depth		= itemObj.depth
	var parentId 	= itemObj.parentId;
	var treeObj		= getObject(itemObj.treeId);
	
	if (depth > 0) {
		var curImg		= getObject(itemId+"_childimg");
		changeTreeChildView(curImg, itemId, treeObj.folderType);
		changeSelectedTreeItem(parentId);
	}
}


/**
 * Child Node���� ǥ�ÿ��� ����
 */
function changeTreeChildView(curImg, objId, folderType) {
	var subObj			= getObject(objId+"_sub");
	var displayStyle	= subObj.style.display;
	var parentObj		= getObject(objId);
	var nextSibilingObj = parentObj.nextSibling;

	if (displayStyle == "block") {
		offDisplay(subObj);

		if (folderType == "normal") {
			if (nextSibilingObj != null) {
				curImg.src = treePlusNodeImg.src;
			}
			else {
				curImg.src = treePlusEndImg.src;
			}
		}
		else {
			curImg.src = treeFolderPlusImg.src;
		}
	}
	else {
		onDisplay(subObj);
		
		if (folderType == "normal") {
			if (nextSibilingObj != null) {
				curImg.src = treeMinusNodeImg.src;
			}
			else {
				curImg.src = treeMinusEndImg.src;
			}
		}
		else {
			curImg.src = treeFolderMinusImg.src;
		}
	}
}


/**
 * Ʈ�� ITEM ����
 */
function selectTreeItem(evt) {
	// ���콺 ��ư üũ
	var mouseButton = getMouseButton(evt);
	
	var treeObj		= getObject(this.treeId);
	var curItemId	= treeObj.curItemId;
	var func		= treeObj.func;
	var contextmenu	= treeObj.contextmenu;

	if (mouseButton == "left" || mouseButton == "right") {	
		if (curItemId != this.itemId && curItemId != "") {
			var prevItemText = getObject(curItemId+"_text");
			offSelectColor(prevItemText, prevItemText.color);
			prevItemText.overcolor = null;
		}
		treeObj.curItemId = this.itemId;
		onSelectColor(this);
		this.overcolor = "on";
	}

	// ���콺 ���� ��ư Ŭ��
	if (mouseButton == "left" && func != "") {
		eval(func);
	}
	// ���콺 ������ ��ư Ŭ��
	else if (mouseButton == "right" && contextmenu !== "") {
		showContextMenu(eval(contextmenu));
	}
}


/**
 * ITEM�� Value�� ��ȯ
 * @param treeObj	Ʈ�� ��ü
 */
function getTreeItemValue(treeObj) {
	var itemObj		= getObject(treeObj.curItemId);
	var value = "";
	if (itemObj) {
		value = itemObj.value;
	}
	return value;
}


/**
 * ITEM�� Text�� ��ȯ
 * @param treeObj	Ʈ�� ��ü
 */
function getTreeItemText(treeObj) {
	var itemTextObj	= getObject(treeObj.curItemId+"_text");
	var value = "";
	if (itemTextObj) {
		value = itemTextObj.innerHTML
	}
	return value;
}


/**
 * ITEM�� depth ��ȯ
 * @param treeObj	Ʈ�� ��ü
 */
function getTreeItemDepth(treeObj) {
	var itemObj	= getObject(treeObj.curItemId);
	
	return itemObj.depth;
}


/**
 * ITEM�� ���� ��� �� ��ȯ
 * @param treeObj	Ʈ�� ��ü
 */
function getTreeItemParentValue(treeObj) {
	var itemObj	= getObject(treeObj.curItemId);
	var parentObj = getObject(itemObj.parentId);
	
	var value = "";
	if (itemObj) {
		value = parentObj.value;
	}
	return value;
}


/**
 * ���콺 ������ �ƾ��� ����
 */
function onTreeItemColor() {
	if (this.overcolor == null) {
		this.style.backgroundColor = treeOverColor;
		this.overcolor = treeOverColor;
	}
}


/**
 * ���콺 �ƿ��� ������ ����
 */
function offTreeItemColor() {
	if (this.overcolor == treeOverColor) {
		this.style.backgroundColor = "";
		this.overcolor = null;
	}
}
// �̹��� �� ��ũ��Ʈ

// @ author : (��)�޵���Ǿ� ��û��
// @ date : 2006�� 05�� 03��
// @ last modify : 

function imgresize(type,contextPath,systemCode,img){ 
	var imgPath = "";
	if(type == 'sys_img')
		imgPath = contextPath+"/data/sys_img/"+systemCode+"/";
		
	img1= new Image(); 
	img1.src=(imgPath+img); 
	imgControll(imgPath+img); 
} 

function imgControll(img){ 
  if((img1.width!=0)&&(img1.height!=0)){ 
    viewImage(img); 
  } 
  else{ 
    controller="imgControll('"+img+"')"; 
    intervalID=setTimeout(controller,20); 
  } 
} 

function viewImage(img){ 
        W=img1.width; 
        H=img1.height; 
        O="width="+W+",height="+H; 
        imgWin=window.open("","",O); 
        imgWin.document.write("<html><head><title>�̹��� �̸�����</title></head>");
        imgWin.document.write("<body topmargin=0 leftmargin=0><center>");
        imgWin.document.write("<img src="+img+" onclick='self.close()' style='cursor:hand'>");
        imgWin.document.close();
} 



///////////////// 2007.06.27 add by HerSunJa /////////////

/*@desc  : �̹��� �̸����⸦ ���� ���̾ �׸���...
 *@refer : jsp/1/community/makeCommunity.jsp (drawImageLayer , viewphoto, closephoto �Ѽ�Ʈ��)
 *@param : layerName(���̾� ���� �޴´�) --> �������� ���̾ �� ��쿡 ���
 *@param : layerLeft(���̾� ��ġ)
 *@param : layerTop (���̾� ��ġ)
 *@param : imgWidth (�̹��� �ʺ�)
 *@param : imgHeight(�̹��� ����)
 */
function drawImageLayer(layerName, layerLeft, layerTop, imgWidth, imgHeight ){ 

    var LayerText;
    
    LayerText  = "\n<div id="+layerName+" style=\"position:absolute;left:"+layerLeft+"px; top:"+layerTop+"px; z-index:2; border: 1px none #000000; visibility: hidden\">";
    LayerText += "\n	<table border=0 cellspacing=1 cellpadding=5 style=\"line-height:160%; margin-top:0; margin-bottom:0;\" bgcolor='#D5CCC3'>";
    LayerText += "\n		<tr> ";
    LayerText += "\n			<td align=center bgcolor='#F8F6F3'>";
    LayerText += "\n					<p align=center style=\"line-height:160%; margin-top:0; margin-bottom:0;\">";
    LayerText += "\n    				<span style=\"font-size:10pt;\"><b><font color='#816554'>�� �̹��� �̸����� ��</font></b></span>";
    LayerText += "\n			</td>";
    LayerText += "\n		</tr>";
    LayerText += "\n		<tr>";
    LayerText += "\n			<td align=center bgcolor=white>";
    LayerText += "\n				<p style=\"line-height:160%; margin-top:0; margin-bottom:0;\">";
    LayerText += "\n				<img src='' name="+layerName+"_photo id=photo width="+imgWidth+" height="+imgHeight+">";
    LayerText += "\n				</p>";
    LayerText += "\n				<p style=\"line-height:160%; margin-top:0; margin-bottom:0;\">";
    LayerText += "\n				<input type=checkbox name=chkclose onClick=\"javascript:closephoto('"+layerName+"');\" class=solid0><span style=\"font-size:9pt;\">â�ݱ�</span>";
    LayerText += "\n				</p>";
    LayerText += "\n			</td>";
    LayerText += "\n		</tr>";
    LayerText += "\n	</table>";
    LayerText += "\n</div>";	

    return document.write(LayerText);
}


/*@desc  : �̹��� �̸�����(���̾ �̹����� �ε���Ų��)
 *@param : context(���ؽ�Ʈ ���) 
 *@param : pimage(�̹��� ���) 
 *@param : layerName(���̾� ���� �޴´�) --> �������� ���̾ �� ��쿡 ���
 */
function viewphoto(context, pimage, layerName) {
	var url = context + '/data/' + pimage;
	var objNm = eval('document.all.' +layerName  );
	var imgObjNm = eval('document.all.' +layerName +"_photo" );
	
	objNm.style.visibility='visible';   // ���̾� view
	imgObjNm.src = url;                 // �̹��� �ε�
}


/*@desc  : �̹��� ���̾� close
 *@param : layerName(���̾� ���� �޴´�) --> �������� ���̾ �� ��쿡 ���
 */
function closephoto(layerName) {
	var objNm = eval('document.all.' +layerName );
	
	document.all.chkclose.checked=false;
	objNm.style.visibility='hidden';
}



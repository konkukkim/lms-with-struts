/********************************************************************************************************
	Alert time
********************************************************************************************************/

var SetDiv   = null;

var page_x   = 100;
var page_y   = 100;

var box_w    = 400;			// �ڽ� ����
var box_h    = 80;			// �ڽ� ����
var win_w    = 800;         // ������ ����
var win_h    = 600;         // ������ ����

function alert_time(time) {
	var par = top.frames[0];

	if (par.document.talert) {
		if (document.all) {                 // for IE
			SetDiv  = par.document.all["alert_t"];
			win_w   = par.document.body.offsetWidth;
			win_h   = par.document.body.offsetHeight;
		}
		else if (document.getElementById) { // for Netscape 6.x or 7.x
			SetDiv  = par.document.getElementById("alert_t");
			win_w   = par.window.innerWidth;
			win_h   = par.window.innerHeight;
		}
		else if (document.layers) {         // for Netscape 4.x
			SetDiv  = par.document.layers["alert_t"];
			win_w   = par.window.innerWidth;
			win_h   = par.window.innerHeight;
		}
	}

	if (SetDiv != null)	{
		page_x = (win_w - box_w) / 2;
		page_y = ((win_h - box_h) / 2) - 50;

		SetDiv.style.top  = page_y;
		SetDiv.style.left = page_x; 
		SetDiv.style.visibility = "visible";

		var disp_str = ""
			+ "<table width="+box_w+">"
			+ "<tr>"
			+ "    <td height="+box_h+" align=center>"
			+ "        <font size=2 align=center><b>�� ���� �ð��� <font color=red>"+time+"</font> �� ���ҽ��ϴ�. ��</b></font>"
			+ "    </td>"
			+ "</tr>"
			+ "<tr>"
			+ "    <td align=center>��� �ۼ��� �������ϰ�, ��������� �����Ͻñ� �ٶ��ϴ�.<br><br><font color=red><b>�� ����</b></font> : ���� ���� �ð��� ������ ���ݱ��� Ǯ���� ����<br>������ �ڵ����� ����˴ϴ�.</font>"
			+ "    </td>"
			+ "</tr>"
			+ "</table><br>"
			+ "<center><form>"
			+ "<input type=button value=' Ȯ �� ' onclick=off_alert_time() class=bt1>"
			+ "</form></center>";

		SetDiv.innerHTML = disp_str;
	}
}

/********************************************************************************************************
	Alert time
********************************************************************************************************/

var SetDiv   = null;

var page_x   = 100;
var page_y   = 100;

var box_w    = 400;			// 박스 넓이
var box_h    = 80;			// 박스 높이
var win_w    = 800;         // 윈도우 넓이
var win_h    = 600;         // 윈도우 높이

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
			+ "        <font size=2 align=center><b>◈ 시험 시간이 <font color=red>"+time+"</font> 분 남았습니다. ◈</b></font>"
			+ "    </td>"
			+ "</tr>"
			+ "<tr>"
			+ "    <td align=center>답안 작성을 마무리하고, 답안제출을 제출하시기 바랍니다.<br><br><font color=red><b>※ 주의</b></font> : 시험 종료 시간이 지나면 지금까지 풀었던 문제<br>까지만 자동으로 제출됩니다.</font>"
			+ "    </td>"
			+ "</tr>"
			+ "</table><br>"
			+ "<center><form>"
			+ "<input type=button value=' 확 인 ' onclick=off_alert_time() class=bt1>"
			+ "</form></center>";

		SetDiv.innerHTML = disp_str;
	}
}

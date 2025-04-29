/********************************************************************************************************
  날짜선택을 위한  달력 출력 프로그램.

  사용방법
  date_select('Form Name', 'Year Field', 'Month Field', 'Day Field' [,Current Year, Current Month])

  예) date_select('Input','year','month','day')
      date_select('Input','year','month','day',2003,6)

  Kim Kyoung shil
********************************************************************************************************/

document.write("<div id=calendar class=calendar></div>");

// 월, 날짜 표시 방법
// 1: 9 --> 09 로 표시
// 0: 9 --> 9 로 표시
var out_type = 1;

// define Icon
var img_dir  = "/webapps/img/img1/common/"; // image directory
var b_close  = img_dir+"date_close.gif";
var b_pyear  = img_dir+"date_pyear.gif";
var b_pmon   = img_dir+"date_pmon.gif";
var b_nyear  = img_dir+"date_nyear.gif";
var b_nmon   = img_dir+"date_nmon.gif";

var iform    = null;
var iyear    = null;
var imonth   = null;
var iday     = null;
var ihour    = null;
var iminute = null;

var SetDiv   = null;
// Date, 날짜 갯수 추가 
var idate = "";
var icount    = 0;

var st_date  = new Date();
var now_year = st_date.getYear();
var now_mon  = st_date.getMonth()+1;
var now_day  = st_date.getDate();
var br_type  = "IE";
var page_x   = 50;
var page_y   = 50;
var scroll_x = 0;
var scroll_y = 0;

var cal_w    = 160 + 20;    // 달력 넓이
var cal_h    = 190 + 20;    // 달력 높이
var win_w    = 300;         // 윈도우 넓이
var win_h    = 300;         // 윈도우 높이

document.onmousedown = check_mouse;
if (document.captureEvents) {
    document.captureEvents(Event.MOUSEDOWN);
}

function check_page() {
    if (document.all) {                 // for IE
        SetDiv   = document.all["calendar"];
        br_type  = "IE";
        win_w    = document.body.offsetWidth;
        win_h    = document.body.offsetHeight;
        scroll_x = document.body.scrollLeft;
        scroll_y = document.body.scrollTop;
    }
    else if (document.getElementById) { // for Netscape 6.x or 7.x
        SetDiv   = document.getElementById("calendar");
        br_type  = "NE7";
        win_w    = window.innerWidth;
        win_h    = window.innerHeight;
        now_year += 1900;
        scroll_x = window.pageXOffset;
        scroll_y = window.pageYOffset;
    }
    else if (document.layers) {         // for Netscape 4.x
        SetDiv  = document.layers["calendar"];
        br_type = "NE4";
        win_w   = window.innerWidth;
        win_h   = window.innerHeight;
        now_year += 1900;
        scroll_x = window.pageXOffset;
        scroll_y = window.pageYOffset;
    }
}

function check_mouse(e) {
    if (br_type == "IE"){
        page_x = event.clientX;
        page_y = event.clientY;
    }
    else if (br_type == "NE7"){
        page_x = e.clientX;
        page_y = e.clientY;
    }
    else if (br_type == "NE4"){
        page_x = e.x;
        page_y = e.y;
    }
}


function date_select(pform, pyear, pmonth, pday, phour, pminute, cu_year, cu_month, pdate, count) {
    check_page();
    iform    = pform;
    iyear    = pyear;
    imonth   = pmonth;
    iday     = pday;
    ihour    = phour;
    iminute = pminute;
    idate    = pdate;
    icount   =  count;
     
    if (cu_month != null && cu_month != "") {
        cu_month = parseInt(cu_month);
    }
    disp_calendar(cu_year, cu_month);

    page_x -= 10;
    page_y -= 5;

    if ((page_x + cal_w) > win_w) {
        page_x = win_w - cal_w;
    }
    if ((page_y + cal_h) > win_h) {
        page_y = win_h - cal_h;
    }

    if (page_x < 0) {
        page_x = 0;
    }

    if (page_y < 0) {
        page_y = 0;
    }

    SetDiv.style.top  = page_y + scroll_y;
    SetDiv.style.left = page_x + scroll_x; 
    SetDiv.style.visibility = "visible";
}


function disp_calendar(st_year, st_mon) {
    st_day  = 0;
    st_week = 0;    // 월의 시작 요일
    ed_day  = 0;    // 월의 날짜
    day_cnt = 0;    // 날짜 카운트
    pr_year = 0;    // 이전 년도 
    pr_mon  = 0;    // 이전 월   
    ne_year = 0;    // 다음 년도 
    ne_mon  = 0;    // 다음 월   

    if (st_year == 0 || st_year == null) {
        st_year = now_year;
    }
    if (st_mon == 0 || st_mon == null) {
        st_mon    = now_mon;
    }
    if (st_day == 0 || st_day == null) {
        st_day    = now_day;
    }

    // 현재 월이 12월일 경우
    if (st_mon >= 12) {
        ed_day  = 31;
        pr_year = st_year;      
        pr_mon  = st_mon - 1;   
        ne_year = st_year + 1;  
        ne_mon  = 1;            
    }
    else {
        var month_days = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);

        // 윤년 체크
        if (st_year%4 == 0 || st_year%100 == 0) {
            month_days[1] = 29;
        }
        else{
            month_days[1] = 28;
        }

        ed_day  = month_days[st_mon-1]; // 마지막 날짜

        // 현재 월이 1월인 경우
        if (st_mon == 1) {
            pr_year = st_year - 1;  // 이전 년도
            pr_mon  = 12;           // 이전 월
        }
        else {
            pr_year = st_year;      // 이전 년도
            pr_mon = st_mon - 1;    // 이전 월
        }
        ne_year = st_year;          // 다음 년도
        ne_mon  = st_mon + 1;       // 다음 월
    }

    st_date.setYear(st_year);
    st_date.setMonth(st_mon-1);
    st_date.setDate(1);
    st_week = st_date.getDay()+1; 
    if (st_week == 0) {
        st_week = 7;
    }

    var date_str = ""
        + "<table border=0 cellspacing=0 cellpadding=0 width=100% class=cal_box>"
        + "<tr>"
        + "    <td colspan=7>"
        + "        <table border=0 cellspacing=0 cellpadding=3 width=100% class=cal_title>"
        + "        <tr>"
        + "            <td>날짜선택</td>"
        + "            <td align=right><a href=javascript:off_calendar() "
        + "                onMouseOver=\"window.status='Close'; return true\" "
        + "                onMouseOut=\"window.status=''; return true\"><img src="+b_close+" border=0></a></td>"
        + "        </tr>"
        + "        </table>"
        + "    </td>"
        + "</tr>"
        + "<tr>"
        + "    <td colspan=7 class=cal_box>"
        + "        <table border=0 cellspacing=0 cellpadding=1 width=100% class=cal_head>"
        + "        <tr>"
        + "            <td align=center>"
        + "                <a href=javascript:disp_calendar("+(st_year-1)+","+st_mon+") "
        + "                onMouseOver=\"window.status='"+(st_year-1)+"/"+st_mon+"'; return true\" "
        + "                onMouseOut=\"window.status=''; return true\"><img src="+b_pyear+" border=0></a>"
        + "            </td>"
        + "            <td align=center>"
        + "                <a href=javascript:disp_calendar("+pr_year+","+pr_mon+") "
        + "                onMouseOver=\"window.status='"+st_year+"/"+pr_mon+"'; return true\" "
        + "                onMouseOut=\"window.status=''; return true\"><img src="+b_pmon+" border=0></a>"
        + "            </td>"
        + "            <td align=center class=cal_ym>"+st_year+"년&nbsp;"+st_mon+"월</td>"
        + "            <td align=center>"
        + "                <a href=javascript:disp_calendar("+ne_year+","+ne_mon+") "
        + "                onMouseOver=\"window.status='"+ne_year+"/"+ne_mon+"'; return true\" "
        + "                onMouseOut=\"window.status=''; return true\"><img src="+b_nmon+" border=0></a>"
        + "            </td>"
        + "            <td align=center>"
        + "                <a href=javascript:disp_calendar("+(st_year+1)+","+st_mon+") "
        + "                onMouseOver=\"window.status='"+(st_year+1)+"/"+st_mon+"'; return true\" "
        + "                onMouseOut=\"window.status=''; return true\"><img src="+b_nyear+" border=0></a>"
        + "            </td>"
        + "        </tr>"
        + "        </table>"
        + "    </td>"
        + "</tr>"
        + "<tr>"
        + "    <td class=cal_head width=15%><font color=red>일</font></td>"
        + "    <td class=cal_head width=14%>월</td>"
        + "    <td class=cal_head width=14%>화</td>"
        + "    <td class=cal_head width=14%>수</td>"
        + "    <td class=cal_head width=14%>목</td>"
        + "    <td class=cal_head width=14%>금</td>"
        + "    <td class=cal_head width=15%><font color=blue>토</font></td>"
        + "</tr>";

    for (var i=1; i<=6; i++) {
        date_str += "<tr>";
        for (var j=1; j<=7; j++) {
            if (i==1 && j<st_week) {
                date_str += "<td class=cal_list>&nbsp;</td>";
            }
            else {
                day_cnt++;
                if (day_cnt > ed_day) {
                    date_str += "<td class=cal_list>&nbsp;</td>";
                }
                else {
                    var a_class  = "";

                    if (a_class == "") {
                        if (j==1) {
                            a_class = "red";
                        }
                        else if (j==7) {
                            a_class = "blue";
                        }
                        else {
                            a_class = "black";
                        }
                    }

                    date_str += "<td class=cal_list "
                             + "onMouseDown='sel_date("+st_year+","+st_mon+","+day_cnt+")' "
                             + "onMouseOver='on_bg(this)' "
                             + "onMouseOut='off_bg(this,"+day_cnt+")'><font color="+a_class+">"+day_cnt+"</font></td>";
                }
            }
        }
        date_str += "</tr>";
        // 날짜를 모두 출력했으면 loop를 빠져나감
        if (day_cnt >= ed_day) {
            break;
        }
    }
    date_str += "</table>";
    SetDiv.innerHTML = date_str;
}


function sel_date(sel_year, sel_mon, sel_day) {
    if (out_type == 1) {
        if (sel_mon < 10) {
            sel_mon = "0"+sel_mon;
        }
        if (sel_day < 10) {
            sel_day = "0"+sel_day;
        }
    }
    var form = document.forms[iform];
 	
    form[iyear+icount].value = sel_year;
    form[imonth+icount].value = sel_mon;
    form[iday+icount].value = sel_day;
    SetDiv.style.visibility = "hidden";

    checkDate(iform,iyear, imonth, iday, ihour, iminute, idate,icount);
}

function on_bg(did) {
    did.style.backgroundColor="#CCCCFF";
    if (br_type == "IE") {
        did.style.cursor = "hand";
    }
    else {
        did.style.cursor="crosshair";
    }
}

function off_bg(did, sday) {
    did.style.backgroundColor="#F0F0FF";
}

function off_calendar() {
    SetDiv.style.visibility = "hidden";
}

		       function checkDate(iform, iyear, imonth, iday, ihour, iminute, idate, ct){
		           var form = document.forms[iform];
		           var ymd1 = "";
		           var hh1 = "";
		           var mi1 = "";   
                     
		           if(ct  ==  "1"){
                        var startDt = getFullDate(iform, iyear, imonth, iday, ihour, iminute, idate, "1");
 		 		                
		                 if(Number(startDt) < 0) return; 
		                  		 		                
		                if(form[idate+"2"].value != "")  
		                    if(Number(form[idate+"2"].value) < Number(startDt)){
		                         alert("기간 설정이 잘못되었습니다.");
		                         dateClear(iform, iyear, imonth, iday, ihour, iminute, idate,ct);
		                         return;
		                    }   

 		                form[idate+"1"].value = startDt;
		                //--------------------------------------------------------------//
		            }else if(ct == "2"){ 	                  
		                 var endDt =  getFullDate(iform, iyear, imonth, iday, ihour, iminute, idate,"2");

  						 if(Number(endDt) < 0) return; 
                          
		                if(form[idate+"1"].value != "")  
		                    if(Number(endDt) < Number(form[idate+"1"].value)){
		                         alert("기간 설정이 잘못되었습니다.");
		                         dateClear(iform, iyear, imonth, iday, ihour, iminute, idate,ct);
		                         return;
		                    } 
		                 
		              
		                 form[idate+"2"].value = endDt;
		            }  
		       }

              function getFullDate(iform, iyear, imonth, iday, ihour, iminute, idate, ct){
                   var form = document.forms[iform];
		           var ymd2 = "";
		           var hh2 = "";
		           var mi2 = "";

          		   ymd2 = form[iyear+ct].value+form[imonth+ct].value+form[iday+ct].value;
	               hh2 = form[ihour+ct].value;
	               if(hh2 == "") 
	                 hh2 = "23";
	              else
	                  if(parseInt(hh2) < 0 && parseInt(hh2) >23) {
	                      alert("시간 입력이 잘못 되었습니다.");
	                      form[ihour+ct].value="1";
	                      return -1;
	                  } 

	              mi2 = form[iminute+ct].value;
	              if(mi2 == "")
	                  mi2 = "1";
	              else
	                  if(parseInt(mi2) < 0 && parseInt(mi2) > 59) {
	                      alert("분 입력이 잘못 되었습니다.");
	                      form[iminute+ct].value="1";
	                      return -1;
	                  } 
	                 return  ymd2+getPadDt(hh2)+getPadDt(mi2)+"01";
              }
 		       
		      function dateClear(iform, iyear, imonth, iday, ihour, iminute, idate, ct){
		        var form = document.forms[iform];
		        var preDate = form[idate+ct].value;

                 if(preDate.length == 14){		        
			        form[iyear+ct].value = preDate.substr(0, 4);
	                form[imonth+ct].value = preDate.substr(4, 2);
	                form[iday+ct].value = preDate.substr(6, 2);			        
			        form[ihour+ct].value=preDate.substr(8, 2);
			        form[iminute+ct].value=preDate.substr(10, 2);
			     }else{
			        form[iyear+ct].value = "";
                	form[imonth+ct].value = "";
                	form[iday+ct].value = "";			        
		        	form[ihour+ct].value="";
		        	form[iminute+ct].value="";
			     }   
		      }
		      
		     function getPadDt(dt){
			  if(dt == "") return "00";
			  
			  if(dt.length < 2) return '0'+dt;
			  else return dt;
	    	}


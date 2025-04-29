/***********************************************************************************************
 *  Project         : EBS 인터넷 수능 강의 시스템(LMS)
 *  File Name       : common.js
 *  Description     : 공통으로 사용하는 js
 *  Arguement       :
 *  Written Date    : 2004-03-03        Written By  :
 *  Modified Date   :                   Modified By :
 **********************************************************************************************/

    /**
     * 문자열의 byte length를 얻는다.
     *
     * @param   str 문자열
     * @return  byte length
     * @author  marie
     */
    function jsByteLength(str) {
        if (str == "") {
            return  0;
        }

        var len = 0;

        for (var i = 0; i < str.length; i++) {
            if (str.charCodeAt(i) > 128) {
                len++;
            }
            len++;
        }

        return  len;
    }

    /**
     * Object에 값을 세팅한다.
     *
     * @param   obj
     * @param   value
     */
    function jsSetValue(obj, value) {
        if (obj) {
            if (obj.type == "text") {
                obj.value = value;
            } else if (obj.tagName == "SELECT") {
                for (var i = 0; i < obj.length; i++) {
                    if (obj.options[i].value == value) {
                        obj.options[i].selected = true;
                        break;
                    }
                }
            }
        }
    }

    /**
     * 주민등록번호를 체크한다.
     *
     * @param   obj 주민등록번호 필드
     * @return  true - 올바른 번호
     *          false - 틀린 번호
     */
    function jsCheckJumin1(obj) {
        var str = deleteHyphen(obj.value);  // 필드에 있는 주민번호에서 '-'제거

        if( !jsCheckJumin(str) ) {
            alert("잘못된 주민등록번호입니다.")
            obj.value="";
            //obj.focus();
            if (window.event) {
                window.event.returnValue = false;
            }
            return  false;
        }

        obj.value = str;
        return  true;
    }

    /**
     * 주민등록번호를 체크한다.
     *
     * @param   str 주민등록번호
     * @return  true - 올바른 번호
     *          false - 틀린 번호
     */
    function jsCheckJumin(str) {
        var tmp = 0;
        var sex = str.substring(6, 7);
        var birthday;

        if (str.length != 13) {
            return  false;
        }

        if (sex == 1 || sex == 2) {
            birthday = "19" + str.substring(0, 6);
        } else if (sex == 3  || sex == 4) {
            birthday = "20" + str.substring(0, 6);
        } else {
            return  false;
        }

        if (!isDate(birthday)) {
            return  false;
        }

        for (var i = 0; i < 12 ; i++) {
            tmp = tmp + ((i%8+2) * parseInt(str.substring(i,i+1)));
        }

        tmp = 11 - (tmp %11);
        tmp = tmp % 10;

        if (tmp != str.substring(12, 13)) {
            return  false;
        }

        return  true;
    }

    /**
     * 주민번호를 체크한다.
     *
     * @param       주민번호(앞자리뒷자리 합친)
     * @param       주민번호 앞자리
     * @param       주민번호 뒷자리
     * @param       다음으로 이동할 포커스
     * @author      강병곤
     * @since       2003-12-04
     */
    function checkJuminNo(juminNo, juminNo1, juminNo2, nextFocus)
    {
        var form    = document.form1;
        var flag    = true;

        var juminNoElm  = eval(form.elements[juminNo]);
        var juminNo1Elm     = eval(form.elements[juminNo1]);
        var juminNo2Elm     = eval(form.elements[juminNo2]);
        var nextFocusElm    = eval(form.elements[nextFocus]);
        //alert("juminNo ::"+ juminNoElm.value +"/ juminNo1 ::"+ juminNo1Elm.value +"/ juminNo2 ::"+ juminNo2Elm.value +"/ nextFocus ::"+ nextFocusElm.value);

        if(juminNo2Elm.value == "" || juminNo2Elm.value.length < 7)
        {
            jsRange(7, 7);
            juminNo2Elm.focus();
            return;
        }

        if(!jsCheckJumin(juminNo1Elm.value + juminNo2Elm.value))
        {
            alert("잘못된 주민번호입니다.");
            juminNo1Elm.value = "";
            juminNo2Elm.value = "";
            juminNo1Elm.focus();
        }
        else
        {
            juminNoElm.value    = juminNo1Elm.value + juminNo2Elm.value;
            nextFocusElm.focus();
        }
    }


	function MemJuminCheck()
	{
		var J1=memUsersForm.res_no1.value;
		var J2=memUsersForm.res_no2.value;

	 // 주민등록번호 1 ~ 6 자리까지의 처리
	 // 주민등록번호에 숫자가 아닌 문자가 있을 때 처리
	 for(i=0;i<J1.length;i++){
	  if (J1.charAt(i) >= 0 || J1.charAt(i) <= 9) {
	   // 숫자면 값을 곱해 더한다.
	   if(i == 0){
		SUM = (i+2) * J1.charAt(i);
	   }else{
		SUM = SUM +(i+2) * J1.charAt
		(i);
	   }
	  }else{
	  // 숫자가 아닌 문자가 있을 때의 처리
	   alert("주민등록번호는 숫자만 입력하셔야 합니다.");
	   document.memUsersForm.res_no1.focus();
	   return false;
	  }
	 }
	 for(i=0;i<2;i++){
	  // 주민등록번호 7 ~ 8 자리까지의 처리
	  if (J2.charAt(i) >= 0 || J2.charAt(i) <= 9) {
	   SUM = SUM + (i+8) * J2.charAt(i);
	  }else{
	  // 숫자가 아닌 문자가 있을 때의 처리
	   alert("주민등록번호는 숫자만 입력하셔야 합니다.");
	   document.memUsersForm.res_no2.focus();
	   return false;
	  }
	 }
	 for(i=2;i<6;i++){
	  // 주민등록번호 9 ~ 12 자리까지의 처리
	  if (J2.charAt(i) >= 0 || J2.charAt(i) <= 9) {
	   SUM = SUM + (i) * J2.charAt(i);
	  }else{
	   // 숫자가 아닌 문자가 있을 때의 처리
	   alert("주민등록번호는 숫자만 입력하셔야 합니다.");
	   document.memUsersForm.res_no2.focus();
	   return false;
	  }
	 }
	 // 나머지 구하기
	 var checkSUM = SUM % 11;
	 // 나머지가 0 이면 10 을 설정
	 if(checkSUM == 0){
	  var checkCODE = 10;
	  // 나머지가 1 이면 11 을 설정
	 }else if(checkSUM ==1){
	  var checkCODE = 11;
	 }else{
	  var checkCODE = checkSUM;
	 }
	  // 나머지를 11 에서 뺀다
	 var check1 = 11 - checkCODE;
	 if (J2.charAt(6) >= 0 || J2.charAt(6) <= 9) {
	  var check2 = parseInt(J2.charAt(6))
	 }else{
	  // 숫자가 아닌 문자가 있을 때의 처리
	  alert("주민등록번호는 숫자만 입력하셔야 합니다.");
	  return false;
	 }
	  if(check1 != check2){
	   // 주민등록번호가 틀릴 때의 처리
	   alert("주민등록 번호를 다시 확인 하세요.");
	   document.memUsersForm.res_no1.focus();
	   return false;
	  }else{
	   return true;
	  }
	 return false;
	}

    /**
     * 사용자(USR ID, 사용자명) 검색 팝업창을 띄운다.
     *
     * @param   column 컬럼명
     *          USR_IDNO USR ID
     *          USR_NAME 사용자명
     * @param   keyWord 검색어
     * @param   fn 펑션명
     * @use     function setSmusr(usrId, usrName) { }
     */
    function jsSmusr(column, keyWord, fn) {
        var url = "/SystemServlet?cmd=LssmusrPopup&column=" + column + "&keyWord=" + keyWord + "&fn=" + fn;
        var name = "";
        var features = "width=600,height=550,scrollbars=yes,top=100,left=100";
        var popupWin = window.open(url, name, features);
        centerSubWindow(popupWin, 600, 550);
        popupWin.focus();
    }

    /**
     * 오직 숫자로만 이루어져 있는지 체크 한다.
     *
     * @param   num
     * @return  boolean
     */
    function isNumber(num) {
        re = /[0-9]*[0-9]$/;

        if (re.test(num)) {
            return  true;
        }

        return  false;
    }

    /**
     * 정수 체크
     *
     * 1. +, - 부호를 생략하거나 넣을 수 있다 : ^[\+-]?
     * 2. 0에서 9까지 숫자가 0번 이상 올 수 있다 : [0-9]*
     * 3. 마지막은 숫자로 끝나야 한다 : [0-9]$
     *
     * @param   num
     * @return  boolean
     */
    function isInteger(num) {
        re = /^[\+-]?[0-9]*[0-9]$/;

        if (re.test(num)) {
            return  true;
        }

        return  false;
    }

    /**
     * 유리수 체크
     *
     * 1. +, - 부호를 생략하거나 넣을 수 있다 : ^[\+-]?
     * 2. 0에서 9까지 숫자가 0번 이상 올 수 있다 : [0-9]*
     * 3. 소수점을 넣을 수 있다 : [.]?
     * 4. 소수점 이하 자리에 0에서 9까지 숫자가 올 수 있다 : [0-9]*
     * 5. 마지막은 숫자로 끝나야 한다 : [0-9]$
     *
     * @param   num
     * @return  boolean
     */
    function isFloat(num) {
        re = /^[\+-]?[0-9]*[.]?[0-9]*[0-9]$/;

        if (re.test(num)) {
            return  true;
        }

        return  false;
    }

    /**
     * 이메일 체크
     *
     * @param   email
     * @return  boolean
     */
    function isEmail(email) {
        re = /[^@]+@[A-Za-z0-9_-]+[.]+[A-Za-z]+/;

        if (re.test(email)) {
            return  true;
        }

        return  false;
    }

    /**
     * 이메일 주소 체크 - 정밀하게
     */
    function emailCheck(emailStr) {
        var checkTLD=1;
        var knownDomsPat=/^(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum)$/;
        var emailPat=/^(.+)@(.+)$/;
        var specialChars="\\(\\)><@,;:\\\\\\\"\\.\\[\\]";
        var validChars="\[^\\s" + specialChars + "\]";
        var quotedUser="(\"[^\"]*\")";
        var ipDomainPat=/^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/;
        var atom=validChars + '+';
        var word="(" + atom + "|" + quotedUser + ")";
        var userPat=new RegExp("^" + word + "(\\." + word + ")*$");
        var domainPat=new RegExp("^" + atom + "(\\." + atom +")*$");
        var matchArray=emailStr.match(emailPat);

        if (matchArray==null) {
            alert("이메일 주소가 정확하지 않습니다 (체크 @ and .'s)");
            return false;
        }
        var user=matchArray[1];
        var domain=matchArray[2];
        for (i=0; i<user.length; i++) {
            if (user.charCodeAt(i)>127) {
                alert("잘못된 이메일 주소를 입력 하셨습니다.");
                return false;
            }
        }
        for (i=0; i<domain.length; i++) {
            if (domain.charCodeAt(i)>127) {
                alert("도메인 이름이 잘못 기제 되었습니다.");
                return false;
            }
        }
        if (user.match(userPat)==null) {
            alert("이메일 주소가 아닙니다.");
            return false;
        }
        var IPArray=domain.match(ipDomainPat);
        if (IPArray!=null) {
            for (var i=1;i<=4;i++) {
                if (IPArray[i]>255) {
                    alert("IP주소가 틀립니다!");
                    return false;
                }
            }
            return true;
        }
        var atomPat=new RegExp("^" + atom + "$");
        var domArr=domain.split(".");
        var len=domArr.length;
        for (i=0;i<len;i++) {
            if (domArr[i].search(atomPat)==-1) {
                alert("도메인 이 존재 하지 않습니다.");
                return false;
            }
        }
        if (checkTLD && domArr[domArr.length-1].length!=2 &&
            domArr[domArr.length-1].search(knownDomsPat)==-1) {
            alert("알려진 형식으로 끝이 나야합니다." + "country.");
            return false;
        }
        if (len<2) {
        alert("Hostname이 틀립니다. !");
        return false;
        }

        return true;
    }

    /**
     * 날짜 체크
     *
     * @param   date
     * @return  boolean
     */
    function isDate(date) {
        if (date == null || date.length != 8) {
            return  false;
        }

        if (!isNumber(date)) {
            return  false;
        }

        var year = eval(date.substring(0, 4));
        var month = eval(date.substring(4, 6));
        var day = eval(date.substring(6, 8));

		if(year == "0000") {
			return false;
		}

        if (month > 12 || month == "00") {
            return  false;
        }

        var totalDays;

        switch (eval(month)){

            case 1 :
                totalDays = 31;
                break;
            case 2 :
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
                    totalDays = 29;
                else
                    totalDays = 28;
                break;
            case 3 :
                totalDays = 31;
                break;
            case 4 :
                totalDays = 30;
                break;
            case 5 :
                totalDays = 31;
                break;
            case 6 :
                totalDays = 30;
                break;
            case 7 :
                totalDays = 31;
                break;
            case 8 :
                totalDays = 31;
                break;
            case 9 :
                totalDays = 30;
                break;
            case 10 :
                totalDays = 31;
                break;
            case 11 :
                totalDays = 30;
                break;
            case 12 :
                totalDays = 31;
                break;
        }

        if (day > totalDays) {
            return  false;
        }

        if (day == "00") {
            return  false;
        }

        return  true;
    }

    /**
     * 데이터 유효성을 체크한다.
     *
     * @param   form
     */
    function validate(form) {
        var obj;
        var dispName;
        var dataType;
        var minValue;
        var maxValue;
        var isValid;
        var value;
        var message;
        var messageAdd;

        for (i = 0; i < form.elements.length; i++) {

            obj = form.elements[i];

			if(obj.name != ""){ //이름이 있는 경우만 체크함.(2004.10.26 suna)

				obj.value = trim(obj.value);
				dispName = obj.getAttribute("dispName");
				dataType = obj.getAttribute("dataType");
				minValue = obj.getAttribute("minValue");
				maxValue = obj.getAttribute("maxValue");
				len      = obj.getAttribute("len");
				lenCheck = obj.getAttribute("lenCheck");
				message  = obj.getAttribute("message"); /// notNull 일때 사용자가 정의한 메세지를 보여주기 위해서
				messageAdd  = obj.getAttribute("messageAdd"); /// notNull 일때 기본메세지 + 사용자정의메세지를 보여준다.
				value = obj.value;

				if (dispName == null) {
					dispName = obj.name;
				}

				/// 퍼미션체크[2004-08-09]
				/* 사용법 : 펑션에서
				/// validation 체크
				frm.perm.value  = "U";
				if( !validate(frm))
					return;

				<input type="hidden" name="perm" message="권한이 없습니다." perm permCheck="<%= "C|R|U|D" %>" value="">
				*/
				if(obj.getAttribute("perm") != null)
				{
					var permCheck   = obj.getAttribute("permCheck");
					var permCheckName   = "";
					var permCheckArr= null;
					var isPerm      = false;

					//alert("permCheck ::"+ permCheck  +"/ obj.name ::"+ obj.name);
					if(permCheck != null && permCheck != "")
					{
						permCheckArr    = tokenCommaPatt(permCheck, "|");
						/// CRUD 권한 체크
						for(var j=0; j<permCheckArr.length; j++)
						{
							if(obj.value == permCheckArr[j])
							{
								isPerm  = false;
								break;
							}
							else
								isPerm  = true;
						}
						if(isPerm)
						{
							if(obj.value == "C")
								permCheckName   = "등록 ";
							else if(obj.value == "R")
								permCheckName   = "조회 ";
							else if(obj.value == "U")
								permCheckName   = "수정 ";
							else if(obj.value == "D")
								permCheckName   = "삭제 ";

							if(message == null || message == "")
								message = "권한이 없습니다.";
							alert(permCheckName + message);
							//obj.focus();
							if(window.event)
							{
								window.event.returnValue = false;
							}
							return  false;
						}

					}
				}
				/// 퍼미션체크[2004-08-09]

				// 필수 입력 항목 체크
				//if (obj.getAttribute("notNull") != null) {
				if (obj.getAttribute("isNull") == "N") {
					isValid = false;

					if (obj.type == "radio" || obj.type == "checkbox") {
						if (form.elements(obj.name).length) {
							for (j = 0; j < form.elements(obj.name).length; j++) {
								if (form.elements(obj.name)[j].checked) {
									isValid = true;
									break;
								}
							}
						} else {
							if (obj.checked) {
								isValid = true;
							}
						}
					} else {
						if (value != "") {
							isValid = true;
						} else {
							if (obj.getAttribute("comma") != null) {
								obj.value = 0;
								isValid = true;
							}
						}
					}

					if (!isValid) {
						if(message == "" || message == null)
							alert(dispName + "을(를) 입력하십시오."+ ((messageAdd == "" || messageAdd == null) ? "" : "\n"+ messageAdd));
						else
							alert(message);
						if(obj.type != "hidden")
							obj.focus();
						if (window.event) {
							window.event.returnValue = false;
						}
						return  false;
					}
				}

				// 데이터 길이 체크
				if (len != null) {
					if (value.length != eval(len)) {
						alert(dispName + "은(는) " + len + "자리를 입력해야 합니다.");
						obj.focus();
						if (window.event) {
							window.event.returnValue = false;
						}
						return  false;
					}
				}

				if(lenCheck != null)
				{
				  if(value.length > eval(lenCheck))
					{
					  alert(dispName + "은(는) " + lenCheck + " 자리를 넘을수 없습니다 현재 글자수("+value.length+")");
					  obj.focus();
					  if(window.event)
						{
						   window.event.returnValue = false;
						}

						return false;
					}
				}

				if (obj.type == "text") {
					// 데이터 타입 체크
//					if (dataType == null) { // 2002.01.30 추가
					if (dataType == null && obj.maxLength!=null && obj.maxLength != -1) { // 2002.01.30 추가
						if (obj.readOnly == false && jsByteLength(value) > (obj.maxLength*2)) {
						//if (obj.readOnly == false && jsByteLength(value) > obj.maxLength) {
							alert(dispName + " 길이가 " + obj.maxLength + " 을(를) 넘습니다.");
							obj.focus();
							if (window.event) {
								window.event.returnValue = false;
							}

							return  false;
						}
					} else if ((value != "") && (dataType != null)) {
						isValid = true;
						checkValue = false;

						if (dataType == "date") {
							value = deleteDateFormatStr(value);
							isValid = isDate(value);
							checkValue = true;
						} else if (dataType == "email") {
							isValid = isEmail(value);
						} else if (dataType == "float") {
							value = deleteCommaStr(value);
							isValid = isFloat(value);
							checkValue = true;
						} else if (dataType == "integer") {
							value = deleteCommaStr(value);
							isValid = isInteger(value);
							checkValue = true;
						} else if (dataType == "number") {
							value = deleteCommaStr(value);
							isValid = isNumber(value);
							checkValue = true;
						} else if (dataType == "double") {
							value = deleteCommaStr(value);
							isValid = isNumber(value);
							checkValue = true;
						}

						if (!isValid) {
							alert(dispName + " 형식이 올바르지 않습니다.");
							if (dataType == "float" || dataType == "integer" || dataType == "number" || dataType == "double") {
								obj.value = "0";
							}
							obj.focus();
							if (window.event) {
								window.event.returnValue = false;
							}
							return  false;
						}

						if (checkValue) {
							if (minValue != null) {
								if (eval(minValue) > eval(value)) {
									alert(dispName + " 값은 최소값(" + minValue + ") 이상입니다.");
									obj.focus();
									if (window.event) {
										window.event.returnValue = false;
									}
									return  false;
								}
							}

							if (isValid && (maxValue != null)) {
								if (eval(maxValue) < eval(value)) {
									alert(dispName + " 값이 최대값(" + maxValue + ")을 초과합니다.");
									obj.focus();
									if (window.event) {
										window.event.returnValue = false;
									}
									return  false;
								}
							}
						}
					}
				}

			}
        } /// end of for()

        return  true;
    }

    /**
     * 숫자에 comma를 붙인다.
     *
     * @param   obj
     */
    function addComma(obj) {
        obj.value = trim(obj.value);
        var value = obj.value;

        if (value == "") {
            return;
        }

        value = deleteCommaStr(value);

        if (!isFloat(value)) {
            dispName = obj.getAttribute("dispName");

            if (dispName == null) {
                dispName = "";
            }

            alert(dispName + " 형식이 올바르지 않습니다.");
            obj.value = "0";
            obj.focus();
            if (window.event) {
                window.event.returnValue = false;
            }
            return;
        }

        obj.value = addCommaStr(value);
    }

    /**
     * 숫자에 comma를 붙인다.
     */
    function addComma2() {
        var obj = window.event.srcElement;
        addComma(obj);
    }

    /**
     * 숫자에 comma를 붙인다.
     *
     * @param   str
     */
    function addCommaStr(str) {
        var rxSplit = new RegExp('([0-9])([0-9][0-9][0-9][,.])');
        var arrNumber = str.split('.');
        arrNumber[0] += '.';
        do {
            arrNumber[0] = arrNumber[0].replace(rxSplit, '$1,$2');
        } while (rxSplit.test(arrNumber[0]));

        if (arrNumber.length > 1) {
            replaceStr = arrNumber.join("");
        } else {
            replaceStr = arrNumber[0].split(".")[0];
        }
        return replaceStr;
    }

    /**
     * 숫자에서 comma를 없앤다.
     *
     * @param   obj
     */
    function deleteComma(obj) {
        obj.value = deleteCommaStr(obj.value);
    }

    /**
     * 숫자에서 comma를 없앤다.
     */
    function deleteComma2() {
        var obj = window.event.srcElement;
        deleteComma(obj);
        obj.select();
    }

    /**
     * 숫자에서 comma를 없앤다.
     *
     * @param   str
     */
    function deleteCommaStr(str) {
        var temp = '';

        for (var i = 0; i < str.length; i++) {
            if (str.charAt(i) == ',') {
                continue;
            } else {
                temp += str.charAt(i);
            }
        }

        return  temp;
    }

    /**
     * 날짜에 "/"를 붙인다.
     *
     * @param   obj
     */
    function addDateFormat(obj) {
        var value = obj.value;

        if (trim(value) == "") {
            return;
        }

        value = deleteDateFormatStr(value);

        if (!isDate(value)) {
            dispName = obj.getAttribute("dispName");

            if (dispName == null) {
                dispName = "";
            }

            alert(dispName + " 형식이 올바르지 않습니다.");
            obj.focus();

            return;
        }

        obj.value = addDateFormatStr(value);
    }

    /**
     * 날짜(년월)에 "/"를 붙인다.
     *
     * @param   obj
     */
    function addYmFormat(obj) {
        var value = obj.value;

        if (trim(value) == "") {
            return;
        }

        value = deleteDateFormatStr(value);

        if (!isDate(value + "01")) {
            dispName = obj.getAttribute("dispName");

            if (dispName == null) {
                dispName = "";
            }

            alert(dispName + " 형식이 올바르지 않습니다.");
            obj.focus();

            return;
        }

        obj.value = addYmFormatStr(value);
    }

    /**
     * 날짜에 "/"를 붙인다.
     */
    function addDateFormat2() {
        var obj = window.event.srcElement;
        addDateFormat(obj);
    }

    /**
     * 날짜에 "/"를 붙인다.
     */
    function addYmFormat2() {
        var obj = window.event.srcElement;
        addYmFormat(obj);
    }

    /**
     * 날짜에 "/"를 붙인다.
     *
     * @param   str
     */
    function addDateFormatStr(str) {
        return  str.substring(0, 4) + "/" + str.substring(4, 6) + "/" + str.substring(6, 8);
    }

    /**
     * 날짜(년월)에 "/"를 붙인다.
     *
     * @param   str
     */
    function addYmFormatStr(str) {
        return  str.substring(0, 4) + "/" + str.substring(4, 6);
    }

    /**
     * 날짜에서 "/"를 없앤다.
     *
     * @param   obj
     */
    function deleteDateFormat(obj) {
        obj.value = deleteDateFormatStr(obj.value);
    }

    /**
     * 날짜에서 "/"를 없앤다.
     */
    function deleteDateFormat2() {
        var obj = window.event.srcElement;
        deleteDateFormat(obj);
        obj.select();
    }

    /**
     * 날짜에서 "/"를 없앤다.
     *
     * @param   str
     */
    function deleteDateFormatStr(str) {
        var temp = '';

        for (var i = 0; i < str.length; i++) {
            if (str.charAt(i) == '/') {
                continue;
            } else {
                temp += str.charAt(i);
            }
        }

        return  temp;
    }

    /**
     * trim
     *
     * @param   text
     * @return  string
     */
    function trim(text) {
        if (text == "") {
            return  text;
        }

        var len = text.length;
        var st = 0;

        while ((st < len) && (text.charAt(st) <= ' ')) {
            st++;
        }

        while ((st < len) && (text.charAt(len - 1) <= ' ')) {
            len--;
        }

        return  ((st > 0) || (len < text.length)) ? text.substring(st, len) : text;
    }

    /**
     * ltrim
     *
     * @param   text
     * @return  string
     */
    function ltrim(text) {
        if (text == "") {
            return  text;
        }

        var len = text.length;
        var st = 0;

        while ((st < len) && (text.charAt(st) <= ' ')) {
            st++;
        }

        return  (st > 0) ? text.substring(st, len) : text;
    }

    /**
     * rtrim
     *
     * @param   text
     * @return  string
     */
    function rtrim(text) {
        if (text == "") {
            return  text;
        }

        var len = text.length;
        var st = 0;

        while ((st < len) && (text.charAt(len - 1) <= ' ')) {
            len--;
        }

        return  (len < text.length) ? text.substring(st, len) : text;
    }

    /**
     * 이벤트 핸들러를 등록한다.
     */
    function setEventHandler() {
        for (i = 0; i < document.forms.length; i++) {

            var elements = document.forms[i].elements;

            for (j = 0; j < elements.length; j++) {
                // INPUT 객체의 onblur 이벤트에 핸들러를 등록한다.
                if (elements[j].tagName == "INPUT") {

                    dataType = elements[j].getAttribute("dataType");

                    if (dataType == "date") {
                        elements[j].onblur = addDateFormat2;
                        elements[j].onfocus = deleteDateFormat2;
                        addDateFormat(elements[j]);
                    } else if (dataType == "number" || dataType == "integer" || dataType == "float" || dataType == "double") {
                        if (elements[j].getAttribute("comma") != null) {
                            elements[j].onblur = addComma2;
                            elements[j].onfocus = deleteComma2;
                            addComma(elements[j]);
                        }
                    } else if (dataType == "yyyymm") {
                        elements[j].onblur = addYmFormat2;
                        elements[j].onfocus = deleteDateFormat2;
                        addYmFormat(elements[j]);
                    }
                }
            }
        }
    }

    /**
     * 자리수의 최소값, 최대값
     *
     * 최소값만 체크 : jsRange(2, -1)
     * 최대값만 체크 : jsRange(-1, 10)
     * 최소값, 최대값 모두 체크 : jsRange(2, 10)
     * 최소값, 최대값 둘다 체크 안함 : jsRange(-1, -1)
     *
     */
    function jsRange(minValue, maxValue)
    {
        jsMinLength(minValue);
        jsMaxLength(maxValue);
    }

    /**
     * 최대값
     */
    function jsMaxLength(maxValue)
    {
        var obj         = window.event.srcElement;
        var dispName    = obj.getAttribute("dispName");
        //var maxValue    = obj.getAttribute("maxValue");
        var val         = jsByteLength(obj.value);

        if( val > (maxValue*2))
        {
            alert(dispName +" 값이 최대값("+ maxValue +")을 초과합니다.\n초과 길이 :"+ (val - (maxValue*2)));
            //obj.value = "0";
            obj.focus();
            if(window.event)
            {
                window.event.returnValue = false;
            }
            return;
        }
    }

    /**
     * 최소값
     */
    function jsMinLength(minValue)
    {
        var obj         = window.event.srcElement;
        var dispName    = obj.getAttribute("dispName");
        //var minValue    = obj.getAttribute("minValue");
        var val         = jsByteLength(obj.value);
        if(minValue != -1 && val < minValue)
        {
            alert(dispName +" 값이 최소값(" + minValue + ") 미만입니다.\n부족 길이 :"+ (minValue - val));
            //obj.value = "0";
            obj.focus();
            if(window.event)
            {
                window.event.returnValue = false;
            }
            return;
        }
    }

    /**
     * 숫자이면 숫자, 숫자가 아니면 0
     */
    function nvlNumber(val)
    {
        if(val == "" || isNaN(val) || val == "undefined")
            return 0;

        return Number(val);
    }

    /**
     * 숫자형식에서 comma를 없애고, 날짜형식에서 "/" 를 없앤다.
     *
     * @param   form
     */
    function makeValue(form) {
        for (i = 0; i < form.elements.length; i++) {
            obj = form.elements[i];

            if (obj.tagName == "INPUT") {
                dataType = obj.getAttribute("dataType");

                if (dataType == "date") {
                    deleteDateFormat(obj);
                } else if (dataType == "number" || dataType == "integer" || dataType == "float" || dataType == "double") {
                    if (obj.getAttribute("comma") != null) {
                        deleteComma(obj);
                    }
                } else if (dataType == "yyyymm") {
                    deleteDateFormat(obj);
                }
                /// notHyphen 이라고 선언했다면 하이픈을 모두 제거한다.
                if(obj.getAttribute("notHyphen") != null) {
                    deleteHyphenObj(obj);
                }
            }
        }
    }

    /**
      * 문자에서 Hyphen을 없앤다.
      *
      * @param  obj
      */
    function deleteHyphenObj(obj) {
        obj.value = deleteHyphen(obj.value);
    }

    /**
     * 데이터 유효성을 체크한다.
     * 하나의 오브젝트에 대한 것임.
     *
     * @param   form
     * @param   obj
     */
    function validateObj(form, obj) {

        var dispName;
        var dataType;
        var minValue;
        var maxValue;
        var isValid;
        var value;

        obj.value = trim(obj.value);
        dispName = obj.getAttribute("dispName");
        dataType = obj.getAttribute("dataType");
        minValue = obj.getAttribute("minValue");
        maxValue = obj.getAttribute("maxValue");
        len      = obj.getAttribute("len");
        value = obj.value;

        if (dispName == null) {
            dispName = obj.name;
        }

        // 필수 입력 항목 체크
        if (obj.getAttribute("notNull") != null) {
            isValid = false;

            if (obj.type == "radio" || obj.type == "checkbox") {
                if (form.elements(obj.name).length) {
                    for (j = 0; j < form.elements(obj.name).length; j++) {
                        if (form.elements(obj.name)[j].checked) {
                            isValid = true;
                            break;
                        }
                    }
                } else {
                    if (obj.checked) {
                        isValid = true;
                    }
                }
            } else {
                if (value != "") {
                    isValid = true;
                } else {
                    if (obj.getAttribute("comma") != null) {
                        obj.value = 0;
                        isValid = true;
                    }
                }
            }

            if (!isValid) {
                alert(dispName + "을(를) 입력하십시오.");
                obj.focus();
                if (window.event) {
                    window.event.returnValue = false;
                }
                return  false;
            }
        }

        // 데이터 길이 체크
        if (len != null) {
            if (value.length != eval(len)) {
                alert(dispName + "은(는) " + len + "자리를 입력해야 합니다.");
                obj.focus();
                if (window.event) {
                    window.event.returnValue = false;
                }
                return  false;
            }
        }

        if (obj.type == "text") {
            // 데이터 타입 체크
            if ((value != "") && (dataType != null)) {
                isValid = true;
                checkValue = false;

                if (dataType == "date") {
                    value = deleteDateFormatStr(value);
                    isValid = isDate(value);
                    checkValue = true;
                } else if (dataType == "email") {
                    isValid = isEmail(value);
                } else if (dataType == "float") {
                    value = deleteCommaStr(value);
                    isValid = isFloat(value);
                    checkValue = true;
                } else if (dataType == "integer") {
                    value = deleteCommaStr(value);
                    isValid = isInteger(value);
                    checkValue = true;
                } else if (dataType == "number") {
                    value = deleteCommaStr(value);
                    isValid = isNumber(value);
                    checkValue = true;
                } else if (dataType == "double") {
                    value = deleteCommaStr(value);
                    isValid = isNumber(value);
                    checkValue = true;
                }

                if (!isValid) {
                    alert(dispName + " 형식이 올바르지 않습니다.");
                    if (dataType == "float" || dataType == "integer" || dataType == "number" || dataType == "double") {
                        obj.value = "0";
                    }
                    obj.focus();
                    if (window.event) {
                        window.event.returnValue = false;
                    }
                    return  false;
                }

                if (checkValue) {
                    if (minValue != null) {
                        if (eval(minValue) > eval(value)) {
                            alert(dispName + " 값이 최소값(" + minValue + ") 미만입니다.");
                            obj.focus();
                            if (window.event) {
                                window.event.returnValue = false;
                            }
                            return  false;
                        }
                    }

                    if (isValid && (maxValue != null)) {
                        if (eval(maxValue) < eval(value)) {
                            alert(dispName + " 값이 최대값(" + maxValue + ")을 초과합니다.");
                            obj.focus();
                            if (window.event) {
                                window.event.returnValue = false;
                            }
                            return  false;
                        }
                    }
                }
            }
        }

        return  true;
    }

    /**
     * 숫자형식에서 comma를 없애고, 날짜형식에서 "/" 를 없앤다.
     * 하나의 오브젝트에 대한 것임.
     *
     * @param   form
     * @param   obj
     */
    function makeValueObj(form, obj) {
        if (obj.tagName == "INPUT") {
            dataType = obj.getAttribute("dataType");

            if (dataType == "date") {
                deleteDateFormat(obj);
            } else if (dataType == "number" || dataType == "integer" || dataType == "float" || dataType == "double") {
                if (obj.getAttribute("comma") != null) {
                    deleteComma(obj);
                }
            }
        }
    }

     /**
      * 문자에서 Hyphen을 없앤다.
      *
      * @param   str
      */
    function deleteHyphen(str) {
        var temp = '';

        for (var i = 0; i < str.length; i++) {
            if (str.charAt(i) == '-') {
                continue;
            } else {
                temp += str.charAt(i);
            }
        }

        return  temp;
    }

    /**
     * 주민등록번호&사업자번호에 '-'넣기
     */
     function setJuminHyphen(obj) {
        var str = deleteHyphen(obj.value);

        if(str.length == 13) {  // 주민등록번호  6-7
            str = str.substring(0, 6) + "-" + str.substring(6);
        }else if(str.length == 10) { // 사업자번호 3-2-5
            str = str.substring(0, 3) + "-"+ str.substring(3, 5) + "-"+ str.substring(5);
        }
        obj.value = str;
     }

    /**
     * 법인번호 에 '-'넣기
     */
    function setPupinHyphen(obj) {
        var str = deleteHyphen(obj.value);

        if(str.length == 13) {  // 주민등록번호  6-7
            str = str.substring(0, 6) + "-" + str.substring(6);
        }
        obj.value = str;
    }

    /**
     * 납입주기에 따른 이율을 계산한다.
     * (소수로 반환한다.)
     *
     * @param   currencyCd 통화
     * @param   yRate 년이율
     * @param   term 납입주기
     * @return  소수 이율
     */
    function jsRateCalc(currencyCd, yRate, term) {

        var yday = jsYdayCalc(currencyCd);
        var rate = eval((yRate / 100) * (term / 12) * (365 / yday));

        return  rate;
    }

    /**
     * 금액을 단수 처리한다.
     *
     * 원화(WON)
     *
     *  단수단위
     *      0 - 원미만
     *      1 - 십원미만
     *      2 - 백원미만
     *      3 - 천원미만
     *      4 - 만원미만
     *
     *  단수처리
     *      1 - 반올림
     *      2 - 절상
     *      3 - 절사
     *
     * 외화
     *
     *  단수단위
     *      0 - 소수점 0 미만
     *      1 - 소수점 1 미만
     *      2 - 소수점 2 미만
     *
     *  단수처리
     *      1 - 반올림
     *      2 - 절상
     *      3 - 절사
     * @param   currency 통화 (text)
     * @param   amt 금액 (text)
     * @param   unit 단수단위 (text)
     * @param   method 단수처리 (text)
     */
    function jsTruncAmt(currency, amt, unit, method) {

        var after = eval(amt);

        if (currency == "WON") {

            after /= Math.pow(10, eval(unit));

            if (method == "1") {
                after = Math.round(after);
            } else if (method == "2") {
                after = Math.ceil(after);
            } else if (method == "3") {
                after = Math.floor(after);
            }

            after *= Math.pow(10, eval(unit));
        } else {
            after *= Math.pow(10, eval(unit));

            if (method == "1") {
                after = Math.round(after);
            } else if (method == "2") {
                after = Math.ceil(after);
            } else if (method == "3") {
                after = Math.floor(after);
            }

            after /= Math.pow(10, eval(unit));
        }

        return  after;
    }

    /**
     * String이 null인 경우 '0'으로 바꾸어 준다.
     *
     * @param   string
     * @return  String
     */
    function jsNumnvl(str) {
        if(str == null || str == "") {
            return "0";
        }
        return  str;
    }

    function jsNvl(str) {
        if(str == null) {
            return "";
        }
        return  str;
    }

    /**
     * 폼 안의 숫자 오브젝트에 콤마를 붙여준다.
     */
    function setComma() {

        for (i = 0; i < document.forms.length; i++) {
            var elements = document.forms[i].elements;
            for (j = 0; j < elements.length; j++) {
                if (elements[j].tagName == "INPUT") {
                    dataType = elements[j].getAttribute("dataType");
                    if (dataType == "number" || dataType == "integer" ||
                    dataType == "float" || dataType == "double") {
                        if (elements[j].getAttribute("comma") != null) {
                            addComma(elements[j]);
                        }
                    }
                }
            }
        }
    }

    /**
     * 일수를 계산한다.(초일산입 말일불산입)
     *
     * @param   from 시작일
     * @param   to 종료일
     * @return  일수
     */
    function jsGetDays(from, to) {

        var fromDt = deleteDateFormatStr(from);
        var toDt = deleteDateFormatStr(to);
        var days = 0 ;

        var fromYy = eval(fromDt.substring(0,4));
        var fromMm = eval(fromDt.substring(4,6) - 1);
        var fromDd = eval(fromDt.substring(6,8));

        var toYy = eval(toDt.substring(0,4));
        var toMm = eval(toDt.substring(4,6) - 1);
        var toDd = eval(toDt.substring(6,8));

        var fromDate = new Date(fromYy, fromMm, fromDd) ;
        var toDate = new Date(toYy, toMm, toDd) ;

        days = ((toDate - fromDate) / 60 / 60 / 24 / 1000);

        return  days;
    }

    /**
     * 비밀번호 체크
     */
    function passChk(p_id, p_pass, obj) {

        var cnt = 0;
        var cnt2 = 1;
        var cnt3 = 1;
        var temp = "";

        /* 비밀번호에에 숫자만 입력되는것을 체크 - 이혁*/
        regNum = /^[0-9]+$/gi;
        bNum = regNum.test(p_pass);
        if(bNum) {
            alert('비밀번호는 숫자만으로 구성하실수는 없습니다.');
               obj.focus();
            return false;
        }
        /* 비밀번호에에 문자만 입력되는것을 체크 - 이혁*/
        regNum = /^[a-zA-Z]+$/gi;
        bNum = regNum.test(p_pass);
        if(bNum) {
            alert('비밀번호는 문자만으로 구성하실수는 없습니다.');
               obj.focus();
            return false;
        }

        for(var i = 0; i < p_id.length; i++) {
            temp_id = p_id.charAt(i);

            for(var j = 0; j < p_pass.length; j++) {
                if (cnt > 0) {
                    j = tmp_pass_no + 1;
                }

                if (temp == "r") {
                    j=0;
                    temp="";
                }

                temp_pass = p_pass.charAt(j);

                if (temp_id == temp_pass){
                    cnt = cnt + 1;
                    tmp_pass_no = j;
                    break;
                } else if (cnt > 0 && j > 0){
                    temp="r";
                    cnt = 0;
                } else {
                    cnt = 0;
                }
            }

            if (cnt > 3) {
                break;
            }
        }

        if (cnt > 3){
            alert("비밀번호가 ID와 4자 이상 중복되거나, \n연속된 글자나 순차적인 숫자를 4개이상 사용해서는 안됩니다.");
            obj.focus();
            return  false;
        }

        for(var i = 0; i < p_pass.length; i++) {
            temp_pass1 = p_pass.charAt(i);
            next_pass = (parseInt(temp_pass1.charCodeAt(0)))+1;
            temp_p = p_pass.charAt(i+1);
            temp_pass2 = (parseInt(temp_p.charCodeAt(0)));

            if (temp_pass2 == next_pass) {
                cnt2 = cnt2 + 1;
            } else {
                cnt2 = 1;
            }

            if (temp_pass1 == temp_p) {
                cnt3 = cnt3 + 1;
            } else {
                cnt3 = 1;
            }

            if (cnt2 > 3) {
                break;
            }

            if (cnt3 > 3) {
                break;
            }
        }

        if (cnt2 > 3){
            alert("비밀번호에 연속된 글이나 순차적인 숫자를 4개이상 사용해서는 안됩니다.");
            obj.focus();
            return  false;
        }

        if (cnt3 > 3){
            alert("비밀번호에 반복된 문자/숫자를 4개이상 사용해서는 안됩니다.");
            obj.focus();
            return  false;
        }

        return true;
    }

    /**
     * 브라우저의 버전을 체크한다.
     *
     * @param   none
     * @return  none
     */
    function objDetectBrowser() {
        var strUA, s, i;
        this.isIE = false;  // 인터넷 익스플로러인지를 나타내는 속성
        this.isNS = false;  // 넷스케이프인지를 나타내는 속성
        this.version = null; // 브라우저 버전을 나타내는 속성

        // Agent 정보를 담고 있는 문자열.
        strUA = navigator.userAgent;

        s = "MSIE";
        // Agent 문자열(strUA) "MSIE"란 문자열이 들어 있는지 체크

        if ((i = strUA.indexOf(s)) >= 0) {
            this.isIE = true;
            // 변수 i에는 strUA 문자열 중 MSIE가 시작된 위치 값이 들어있고,
            // s.length는 MSIE의 길이 즉, 4가 들어 있다.
            // strUA.substr(i + s.length)를 하면 strUA 문자열 중 MSIE 다음에
            // 나오는 문자열을 잘라온다.
            // 그 문자열을 parseFloat()로 변환하면 버전을 알아낼 수 있다.
            this.version = parseFloat(strUA.substr(i + s.length));
            return;
        }

        s = "Netscape6/";
        // Agent 문자열(strUA) "Netscape6/"이란 문자열이 들어 있는지 체크

        if ((i = strUA.indexOf(s)) >= 0) {
            this.isNS = true;
            this.version = parseFloat(strUA.substr(i + s.length));
            return;
        }

        // 다른 "Gecko" 브라우저는 NS 6.1로 취급.

        s = "Gecko";
        if ((i = strUA.indexOf(s)) >= 0) {
            this.isNS = true;
            this.version = 6.1;
            return;
        }
    }

  /**
   * 화면 크기를 1024*768로 고정 시킨다.
   */
  function fixScreen(){
    if ((screen.availWidth >= 1024) & (screen.availHeight >= 768)){
      availX = 1024;
      availY = 768;
      screenX = screen.availWidth;
      screenY = screen.availHeight;
      windowX = (screenX - availX)/2;
      windowY = (screenY - availY)/2;
    }
    else {
      //availX = 1024;
      //availY = 768;
      availX = screen.availWidth;
      availY = screen.availHeight;
      windowX = 0;
      windowY = 0;
    }
    moveTo(windowX,windowY);
    resizeTo(availX, availY);
  }

    /**
     * sub 화면을 가운데에 위치 시킨다.
     * centerSubWindow(winName, wx, wy)
     * winName : 서브윈도우의 이름
     * ww : 서브윈도우로 열 창의 너비
     * wh : 서브윈도우로 열 창의 높이
     */
    function centerSubWindow(winName, ww, wh){
        if (document.layers) {
            sw = screen.availWidth;
            sh = screen.availHeight;
        }
        if (document.all) {
            sw = screen.width;
            sh = screen.height;
        }

        w = (sw - ww)/2;
        h = (sh - wh)/2;
        winName.moveTo(w,h);
    }

    /**
     * 문자열에서 삭제를 원하는 문자를 삭제한다.
     *
     * @param   val 문자열
     * @param   str 삭제할 문자
     */
    function jsTrim(val, str) {
        var temp  = val.value;
        temp = temp.split(str);

        val.value = temp.join("");
    }

    /**
     * 폼 전체를 읽기전용으로 만든다.
     *
     * @param    form명
     */
    function setAllDisabled(tform) {
        var len = tform.elements.length;
        alert("len ::"+ len);
        for(i=0; i<len; i++) {
            if(tform.elements[i].type == "text" || tform.elements[i].type == "select-one"
               || tform.elements[i].type == "textarea" || tform.elements[i].type == "file"
               || tform.elements[i].type == "radio" || tform.elements[i].type == "checkbox") {
                 tform.elements[i].disabled = true;
            }
        }
    }

    /**
     * 폼 전체를 읽기전용을 정상으로 돌려 놓는다.
     *
     * @param    form명
     */
    function setAllEnabled(tform) {
        var len = tform.elements.length;
        for(i=0; i<len; i++) {
            if(tform.elements[i].type == "text" || tform.elements[i].type == "select-one"
               || tform.elements[i].type == "textarea" || tform.elements[i].type == "file"
               || tform.elements[i].type == "radio" || tform.elements[i].type == "checkbox") {
                 tform.elements[i].disabled = false;
            }
        }
    }

    /**
     * tokenCommaPatt
     *
     * @param    val
     * @param    patt
     * @ String val을 String patt로 구분하여배열로 리턴한다.
     * example
     *  var TestArray = tokenCommaPatt( "abcd efgh ijkl", " ")
     *  TestArray[0] = "abcd";
     *  TestArray[1] = "efgh";
     *  TestArray[2] = "ijkl";
     */
    function tokenCommaPatt(val, patt){
        var i = 0, iFst = 0;
        var sCheckValue = val;
        var arrRst = new Array();
        while( ( iFst = sCheckValue.indexOf( patt ) ) >= 0 ) {
            arrRst[i++] = sCheckValue.substring( 0 , iFst );
            sCheckValue = sCheckValue.substring( iFst + patt.length ,  sCheckValue.length );
            }
        arrRst[i] = sCheckValue;
        return arrRst;
    }

    /**
     * 숫자로만 이루어져 있는지 체크 한다.
     *
     * @param    num
     * @return   boolean
     */
    function isNumber2(num){
        var inText = num.value;
        var ret;

        for (var i = 0; i < inText.length; i++) {
            ret = inText.charCodeAt(i);
            if (!((ret > 47) && (ret < 58)))  {
                alert("숫자만 입력 가능합니다.");
                num.value = "";
                num.focus();
                return false;
            }
        }
        return true;
    }

    /**
     * 한글로만 이루어져 있는지 체크 한다.
     *
     * @param    han
     * @return   boolean
     */
    function isHangul(han) {
        var inText = han.value;
        var ret;

        ret = inText.charCodeAt();
        if (ret > 31 && ret < 127) {
            alert("한글만 입력 가능합니다.");
            han.value = "";
            han.focus();
            return false;
        }

        return true;
    }

    /**
    * 한글을 입력했는지 체크, 한글을 입력했다면 false를 반환한다.
    * 영어와 숫자만 패스시킨다.
    **/
    function isHangulChat(han) {
		var ret;

		ret = han.charCodeAt();
		re = /[0-9]*[0-9]$/;

		if (re.test(han) || (ret > 31 && ret < 127)) {
			return true;
		}

		return false;
	}

   /**
    * 영문캐릭터인지 체크(대문자)
    *
    * param obj
    * return
    */
    function checkChar(obj)
    {
        var strValue = obj.value

        var retChar = strValue.toUpperCase();

        if (retChar <  "A" || retChar  > "Z")
        {
            alert("영문자만 입력이 가능합니다.");
            obj.value = "";
            obj.focus();
            return;
        }
        obj.value = retChar;
    }

    /**
     * 키보드 입력시 숫자만 입력 가능
     */
    function onlyNumber(){
        if ((event.keyCode >= 32 && event.keyCode < 48)
            || (event.keyCode > 57 && event.keyCode < 65)
            || (event.keyCode > 90 && event.keyCode < 97)
            || (event.keyCode >= 97 && event.keyCode <= 122)
            || (event.keyCode >= 65 && event.keyCode <= 90))
            event.returnValue = false;
    }

    /**
     * 키보드 입력시 수자 및 ','가 입력 가능
     */
    function AmtNumber(){
        if ((event.keyCode >= 32 && event.keyCode < 44)
            || (event.keyCode >= 45 && event.keyCode < 48)
            || (event.keyCode > 57 && event.keyCode < 65)
            || (event.keyCode > 90 && event.keyCode < 97)
            || (event.keyCode >= 97 && event.keyCode <= 122)
            || (event.keyCode >= 65 && event.keyCode <= 90))
            event.returnValue = false;
    }

    /**
     * 키보드 입력시 수자 및 '.'가 입력 가능
     */
    function RateNumber(){
        if ((event.keyCode >= 32 && event.keyCode < 46)
            || (event.keyCode >= 47 && event.keyCode < 48)
            || (event.keyCode > 57 && event.keyCode < 65)
            || (event.keyCode > 90 && event.keyCode < 97)
            || (event.keyCode >= 97 && event.keyCode <= 122)
            || (event.keyCode >= 65 && event.keyCode <= 90))
            event.returnValue = false;
    }

    /**
     * 숫자형식에 null이 입력되면 0으로 셋팅한다.
     *
     * @param   form
     */
    function setZero(form) {
        for (i = 0; i < form.elements.length; i++) {
            obj = form.elements[i];

            if (obj.tagName == "INPUT") {
                dataType = obj.getAttribute("dataType");

                if (dataType == "number" || dataType == "integer" || dataType == "float" || dataType == "double") {
                    if (obj.value == null || obj.value == "") {
                        obj.value = "0";
                    }
                }
            }
        }
    }

    /* 날짜관련 *******************************************************************************/
    var dateBase  = new Date();

    /**
     * 년
     */
    function getYear()
    {
        return dateBase.getFullYear();
    }

    /**
     * 월
     */
    function getMonth()
    {
        var month = dateBase.getMonth()+1;
        if (("" + month).length == 1)
            month = "0" + month;
        return month;
    }

    /**
     * 일
     */
    function getDay()
    {
        var day = dateBase.getDate();
        if(("" + day).length == 1)
            day   = "0" + day;
        return day;
    }

    /**
     * 현재일부터 특정일자 이전(0), 이후(1)의 날짜를 리턴한다.(YYYYMMDD)
     */
    function getIntervalDate(term, isPrevNext)
    {
        var year2, month2, day2;
        var dt = new Date(getMonth() +"-"+ getDay() +"-"+ getYear());
        var anyTime;
        var anyDate;
        if(isPrevNext == "0") /// 이전
            anyTime = dt.getTime() - (term) * 1000 * 3600 * 24;
        else /// 이후
            anyTime = dt.getTime() + (term) * 1000 * 3600 * 24;
        anyDate = new Date();
        anyDate.setTime(anyTime);
        year2 = ( (anyDate.getYear()<100) ? "19"+ anyDate.getYear() : anyDate.getYear() );
        month2 = anyDate.getMonth()+1;
        day2 = anyDate.getDate();
        if (("" + month2).length == 1)
            month2 = "0" + month2;
        if(("" + day2).length == 1)
            day2   = "0" + day2;
        //alert("["+ year2 +"/"+ month2 +"/"+ day2 +"]");

        return year2 +""+ month2 +""+ day2;
    }

    /**
     * 기준일부터 특정일자 이전(0), 이후(1)의 날짜를 리턴한다.(YYYYMMDD)
     */
    function getIntervalDate2(kijunDate, term, isPrevNext)
    {
        var year2, month2, day2;
        var dt = toTimeObject(deleteDateFormatStr(kijunDate) +"0000");
        var anyTime;
        var anyDate;
        if(isPrevNext == "0") /// 이전
            anyTime = dt.getTime() - (term) * 1000 * 3600 * 24;
        else /// 이후
            anyTime = dt.getTime() + (term) * 1000 * 3600 * 24;
        anyDate = new Date();
        anyDate.setTime(anyTime);
        year2 = ( (anyDate.getYear()<100) ? "19"+ anyDate.getYear() : anyDate.getYear() );
        month2 = anyDate.getMonth()+1;
        day2 = anyDate.getDate();
        if (("" + month2).length == 1)
            month2 = "0" + month2;
        if(("" + day2).length == 1)
            day2   = "0" + day2;
        //alert("["+ year2 +"/"+ month2 +"/"+ day2 +"]");

        return year2 +""+ month2 +""+ day2;
    }

    /**
     * 기준일부터 특정일자 이전(0), 이후(1)의 개월수 만큼 차이나는 날짜를 리턴한다.(YYYYMMDD)
     */
    function getIntervalMonth(kijunDate, term, isPrevNext)
    {
        var kijunDate   = deleteDateFormatStr(kijunDate);
        var year        = kijunDate.substring(0,4); /// 년
        var month       = kijunDate.substring(4,6); /// 월
        var date        = kijunDate.substring(6,8); /// 일
        var addMonth;
        var addYear;
        var tempYear;
        var tempMonth;
        var rtnDate;

        if(isPrevNext == "0") /// 이전
        {
            addMonth    = eval(month) - eval(term);
            addYear     = Math.floor(eval(addMonth/12)); /// 빼줄 년도 계산
            tempYear    = eval(addYear) + eval(addMonth%12);
            if(tempYear > 0)
            {
                tempMonth   = eval(tempYear%13);
            }
            else
            {
                tempMonth   = eval(12 + addMonth%12);
                if(tempYear == 0)
                    addYear     = addYear-1;
            }
        }
        else /// 이후
        {
            addMonth    = eval(month) + eval(term);
            addYear     = Math.floor(eval(addMonth/13)); /// 더해줄 년도 계산
            tempYear    = eval(addYear) + eval(addMonth%13);

            if(tempYear < 13)
            {
                tempMonth   = eval(tempYear%13);
            }
            else
            {
                tempMonth   = eval(tempYear%13 +1);
                addYear     = addYear+1;
            }
        }

        tempMonth   = tempMonth + ""; /// 길이를 알아보기위해 string으로 바꿔줌.
        if(tempMonth.length == 1)
        {
            tempMonth = "0" + tempMonth;
        }
        /// 해당월에 해당일이 존재하는지 체크하고 존재하지 않는다면 마지막 일을 가져온다.
        if( !isValidDay(eval(year) + eval(addYear), tempMonth, date))
            date = getLastDay(eval(year) + eval(addYear), tempMonth);

        rtnDate = eval(year) + eval(addYear) +""+ tempMonth +""+ date;
        //alert(">날짜 ::"+ rtnDate);

        return rtnDate;
    }

    /**
     * Time 스트링을 자바스크립트 Date 객체로 변환
     *
     * parameter time: Time 형식의 String
     */
    function toTimeObject(time)
    { //parseTime(time)
        var year  = time.substr(0,4);
        var month = time.substr(4,2) - 1; // 1월=0,12월=11
        var day   = time.substr(6,2);
        var hour  = time.substr(8,2);
        var min   = time.substr(10,2);

        return new Date(year,month,day,hour,min);
    }

    /**
     * 자바스크립트 Date 객체를 Time 스트링으로 변환
     *
     * parameter date: JavaScript Date Object
     */
    function toTimeString(date)
    { //formatTime(date)
        var year  = date.getFullYear();
        var month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
        var day   = date.getDate();
        var hour  = date.getHours();
        var min   = date.getMinutes();

        if(("" + month).length == 1) { month = "0" + month; }
        if(("" + day).length   == 1) { day   = "0" + day;   }
        if(("" + hour).length  == 1) { hour  = "0" + hour;  }
        if(("" + min).length   == 1) { min   = "0" + min;   }

        return ("" + year + month + day + hour + min)
    }

    /**
     * 유효한(존재하는) 월(月)인지 체크
     */
    function isValidMonth(mm)
    {
        var m = parseInt(mm,10);
        return (m >= 1 && m <= 12);
    }

    /**
     * 유효한(존재하는) 일(日)인지 체크
     */
    function isValidDay(yyyy, mm, dd)
    {
        var m = parseInt(mm,10) - 1;
        var d = parseInt(dd,10);

        var end = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
        if ((yyyy % 4 == 0 && yyyy % 100 != 0) || yyyy % 400 == 0) {
            end[1] = 29;
        }

        return (d >= 1 && d <= end[m]);
    }

    /**
     * 해당 월의 마지막 일을 가져온다.
     */
    function getLastDay(yyyy, mm)
    {
        var m = parseInt(mm,10) - 1;
        var d;

        var end = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
        if ((yyyy % 4 == 0 && yyyy % 100 != 0) || yyyy % 400 == 0) {
            end[1] = 29;
        }
        for(var i=0; i<end.length; i++)
        {
            if(m == i)
                d = end[i];
        }
        //alert("d ::"+ d);

        return d;
    }

    /**
     * 유효한(존재하는) 시(時)인지 체크
     */
    function isValidHour(hh)
    {
        var h = parseInt(hh,10);
        return (h >= 1 && h <= 24);
    }

    /**
     * 유효한(존재하는) 분(分)인지 체크
     */
    function isValidMin(mi)
    {
        var m = parseInt(mi,10);
        return (m >= 1 && m <= 60);
    }

    /**
     * 현재날짜를 리턴한다.
     *
     */
    function getCurDate()
    {
        var date  = new Date();
        var year  = date.getFullYear();
        var month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
        var day   = date.getDate();
        var hour  = date.getHours();
        var min   = date.getMinutes();

        if (("" + month).length == 1) { month = "0" + month; }
        if (("" + day).length   == 1) { day   = "0" + day;   }
        if (("" + hour).length  == 1) { hour  = "0" + hour;  }
        if (("" + min).length   == 1) { min   = "0" + min;   }

        return ("" + year + month + day)
    }
    /* 날짜관련 *******************************************************************************/

    /**
     * 날짜를 체크하여 금월을 return
     *
     * @param       날짜
     */
    function jsThisMonth(nowDate) {
        var form = document.form1;

        var nowYear = nowDate.substring(0, 4);
        var nowMonth = nowDate.substring(4, 6);
        var nowDay = nowDate.substring(6, 8);
        var newDay = "";

        for(var i=28; i<=31; i++) {
            if (isDate(nowYear + nowMonth + i)) {
                newDay = i + "";
            }
        }

        form.fromDate.value = addDateFormatStr(nowYear + nowMonth + "01");
        form.toDate.value = addDateFormatStr(nowYear + nowMonth + newDay);
    }

    /**
     * 날짜를 체크하여 금주를 return
     *
     * @param       날짜
     */
    function jsThisWeek(nowDate) {
        var form = document.form1;

        var dateWeek = getDateWeek(nowDate);
        var monday = Number(nowDate) - dateWeek + 1;
        var sunday = monday + 6 ;

        form.fromDate.value = addDateFormatStr(monday + "");
        form.toDate.value = addDateFormatStr(sunday + "");
    }

    /**
     * 날짜를 체크하여 금일를 return
     *
     * @param       날짜
     */
    function jsThisDay(nowDate) {
        var form = document.form1;

        form.fromDate.value = addDateFormatStr(nowDate);
        form.toDate.value = addDateFormatStr(nowDate);
    }

    /**
     * 지정한 날짜의 요일(1 -> 월, ~ 7 -> 일)
     *
     * @param       날짜
     */
    function getDateWeek(val){
        var day;
        var d = new Date();

        d.setUTCFullYear(Number(val.substring(0, 4)));
        d.setUTCMonth(Number(val.substring(4, 6)) - 1);
        d.setUTCDate(Number(val.substring(6, 8)));

        day = d.getDay();

        return day;
    }

    /**
     * 엔터키 누르면 자동으로 다음 필드로 이동
     */
    function enterNextField(field, event)
    {
        var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;

        if(keyCode == 13)
        {
            var i;
            for(i = 0; i<field.form.elements.length; i++)
            {
                if(field == field.form.elements[i])
                    break;
            }

            i = (i + 1) % field.form.elements.length;
            field.form.elements[i].focus();
            return false;
        }
        else
            return true;
    }

    /**
     * 화면의 첫번째 TextField에 포커스 이동
     */
    function firstTextFocus()
    {
        var elements;
        var obj;

        for(var j=0; j<document.forms.length; j++)
        {
            elements = document.forms[j].elements;

            for(var i=0; i<elements.length; i++)
            {
                obj = elements[i];

                if(obj.tagName == "INPUT")
                {
                    if(obj.type == "text" && obj.disabled == false)
                    {
                        obj.focus();
                        endFocus(obj);
                        return;
                    }
                }
            }
        } /// end of for()
    }

    /**
     * 화면의 원하는 TextField 나 TextArea에 포커스 이동
     */
    function firstTextFocus(formName)
    {
        var elements;
        var obj;

        for(var j=0; j<document.forms.length; j++)
        {
            elements = document.forms[j].elements;

            for(var i=0; i<elements.length; i++)
            {
                obj = elements[i];

                if(obj.name == formName)
                {
                    if(obj.tagName == "INPUT" || obj.tagName.toUpperCase() == "TEXTAREA")
                    {
                        //alert("obj.name ::"+ obj.name +"/ obj.tagName ::"+ obj.tagName +"/ formName ::"+ formName +"/ obj.type ::"+ obj.type);
                        if((obj.type == "text" || obj.type == "textarea") && obj.disabled == false)
                        {
                            obj.focus();
                            endFocus(obj);
                            return;
                        }
                    }
                }
                else if(formName == "" || formName == null)
                {
                    if(obj.tagName == "INPUT")
                    {
                        if(obj.type == "text" && obj.disabled == false)
                        {
                            obj.focus();
                            endFocus(obj);
                            return;
                        }
                    }
                }
            }
        } /// end of for()
    }

    /**
     * FM## - getFM(12, 4) -> 0012로 변경한다.
     * @param       val 원본 값
     * @param       len 사이즈 (0을 채울 갯수)
     */
    function getFM(val, len)
    {
        if(val == "")
            return val;
        var str     = "";
        var zero    = "";
        var valLen  = new String(val).length;
        var forLen  = len-valLen;
        if(len <= valLen)
            return val;
        for(var i=0; i<(forLen); i++)
        {
            zero    += "0";
        }
        str = zero+val;

        return str;
    }

    /**
     * 퍼미션 체크
     * 하나의 오브젝트에 대한 것임.
     *
     * @param   form
     * @param   obj
     */
    function permission(form, obj)
    {
        /// 버튼에 다음과 같이 정의해야 한다. perm permType="C" permCheck="<%= "C" %>"
        var permType    = ""; /// 현재 버튼의 CRUD 타입
        var permCheck   = ""; /// 사용자가 가진 퍼미션
        var dispName    = ""; /// 디스플레이명
        var permCheckArr= new Array();

        alert("> obj.tagName ::"+ obj.tagName);
        for(i = 0; i < form.elements.length; i++)
        {
            if(obj.getAttribute("perm") != null)
            {
                if(obj.tagName == "INPUT" || obj.tagName == "IMG")
                {
                    permType    = obj.getAttribute("permType");
                    permCheck   = obj.getAttribute("permCheck");
                    dispName    = obj.getAttribute("dispName");
                    if(permType != null && permType != "")
                    {
                        if(permCheck != null && permCheck != "")
                        {
                            permCheckArr    = tokenCommaPatt(permCheck, "|");
                        }
                        /// CRUD 권한 체크
                        for(var j=0; j<permCheckArr.length; j++)
                        {
                            if(permType == permCheckArr[j])
                            {
                                break;
                            }
                            else
                            {
                                alert(dispName +" 권한이 없습니다.");
                                obj.focus();
                                if(window.event)
                                {
                                    window.event.returnValue = false;
                                }
                                return  false;
                            }
                        }
                    }
                }
                break;
            }
        }
        alert("permType ::"+ permType +"/ permCheck ::"+ permCheck);
    }

    /**
     * 텍스트필드의 맨 끝(오른쪽)으로 포커스 이동하기.
     * <input type="textfield" name="addr" onFocus="endFocus(this);">
     *
     * @param   obj
     */
    function endFocus(obj)
    {
        obj.value = obj.value + '';
    }

    /**
     * 라디오버튼의 선택되어진 개체의 값을 가져온다.
     *
     * @param       frm document.form
     * @param       elem 라디오버튼 개체명
     */
    function getRadioValue(frm, elem)
    {
        var val = "";

        if(elem == null || elem == "")
            return "";

        if(frm.elements[elem].length > 0)
        {

            for(var i=0; i<frm.elements[elem].length; i++)
            {
                if(frm.elements[elem][i].checked)
                {
                    val = frm.elements[elem][i].value;
                    break;
                }
            } /// end of for()
        }
        else
            val = frm.elements[elem].value;

        return val;
    }

    /**
     * 숫자체크.
     *
     * @param       objtext1 document.form.obj
     * @param       oVal 디폴트값
     */
	// 숫자체크 (사용예 : onkeyup="Javascript:onlynum(frm.num1, '3');" )
	function onlynum(objtext1, oVal){
		var inText = objtext1.value;
		var ret;

		for (var i = 0; i < inText.length; i++) {
		    ret = inText.charCodeAt(i);
			if (!((ret > 47) && (ret < 58)))  {
	                   alert("숫자만을 입력하세요");
	                   objtext1.value = oVal;
	                   objtext1.focus();
	                   return false;
			}
		}
		return true;
	}

	//popup login
	function LoginPop(url, gubun) {
		var LoginUrl = "/MemUsersFront.do?cmd=memUserLoginPop&url="+url+"&gubun="+gubun+"&mode=pop";
		var Login = window.open(LoginUrl, "Login", "status=no,scrollbars=yes, width=405, height=310, left=100, top=100");
	}


	//email_send(arrId, arrName)
	function etcEmailWritePop(arrId, arrName) {
		var writeUrl = "/EtcEmailSend.do?cmd=etcEmailSendWriteForm&rec_user_id="+arrId+"&rec_nm="+arrName;
		var writeInbox = window.open(writeUrl, "writeInbox", "status=no,scrollbars=yes, width=710, height=500, left=100, top=100");
	}

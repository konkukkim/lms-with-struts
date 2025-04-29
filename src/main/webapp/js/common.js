/***********************************************************************************************
 *  Project         : EBS ���ͳ� ���� ���� �ý���(LMS)
 *  File Name       : common.js
 *  Description     : �������� ����ϴ� js
 *  Arguement       :
 *  Written Date    : 2004-03-03        Written By  :
 *  Modified Date   :                   Modified By :
 **********************************************************************************************/

    /**
     * ���ڿ��� byte length�� ��´�.
     *
     * @param   str ���ڿ�
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
     * Object�� ���� �����Ѵ�.
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
     * �ֹε�Ϲ�ȣ�� üũ�Ѵ�.
     *
     * @param   obj �ֹε�Ϲ�ȣ �ʵ�
     * @return  true - �ùٸ� ��ȣ
     *          false - Ʋ�� ��ȣ
     */
    function jsCheckJumin1(obj) {
        var str = deleteHyphen(obj.value);  // �ʵ忡 �ִ� �ֹι�ȣ���� '-'����

        if( !jsCheckJumin(str) ) {
            alert("�߸��� �ֹε�Ϲ�ȣ�Դϴ�.")
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
     * �ֹε�Ϲ�ȣ�� üũ�Ѵ�.
     *
     * @param   str �ֹε�Ϲ�ȣ
     * @return  true - �ùٸ� ��ȣ
     *          false - Ʋ�� ��ȣ
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
     * �ֹι�ȣ�� üũ�Ѵ�.
     *
     * @param       �ֹι�ȣ(���ڸ����ڸ� ��ģ)
     * @param       �ֹι�ȣ ���ڸ�
     * @param       �ֹι�ȣ ���ڸ�
     * @param       �������� �̵��� ��Ŀ��
     * @author      ������
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
            alert("�߸��� �ֹι�ȣ�Դϴ�.");
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

	 // �ֹε�Ϲ�ȣ 1 ~ 6 �ڸ������� ó��
	 // �ֹε�Ϲ�ȣ�� ���ڰ� �ƴ� ���ڰ� ���� �� ó��
	 for(i=0;i<J1.length;i++){
	  if (J1.charAt(i) >= 0 || J1.charAt(i) <= 9) {
	   // ���ڸ� ���� ���� ���Ѵ�.
	   if(i == 0){
		SUM = (i+2) * J1.charAt(i);
	   }else{
		SUM = SUM +(i+2) * J1.charAt
		(i);
	   }
	  }else{
	  // ���ڰ� �ƴ� ���ڰ� ���� ���� ó��
	   alert("�ֹε�Ϲ�ȣ�� ���ڸ� �Է��ϼž� �մϴ�.");
	   document.memUsersForm.res_no1.focus();
	   return false;
	  }
	 }
	 for(i=0;i<2;i++){
	  // �ֹε�Ϲ�ȣ 7 ~ 8 �ڸ������� ó��
	  if (J2.charAt(i) >= 0 || J2.charAt(i) <= 9) {
	   SUM = SUM + (i+8) * J2.charAt(i);
	  }else{
	  // ���ڰ� �ƴ� ���ڰ� ���� ���� ó��
	   alert("�ֹε�Ϲ�ȣ�� ���ڸ� �Է��ϼž� �մϴ�.");
	   document.memUsersForm.res_no2.focus();
	   return false;
	  }
	 }
	 for(i=2;i<6;i++){
	  // �ֹε�Ϲ�ȣ 9 ~ 12 �ڸ������� ó��
	  if (J2.charAt(i) >= 0 || J2.charAt(i) <= 9) {
	   SUM = SUM + (i) * J2.charAt(i);
	  }else{
	   // ���ڰ� �ƴ� ���ڰ� ���� ���� ó��
	   alert("�ֹε�Ϲ�ȣ�� ���ڸ� �Է��ϼž� �մϴ�.");
	   document.memUsersForm.res_no2.focus();
	   return false;
	  }
	 }
	 // ������ ���ϱ�
	 var checkSUM = SUM % 11;
	 // �������� 0 �̸� 10 �� ����
	 if(checkSUM == 0){
	  var checkCODE = 10;
	  // �������� 1 �̸� 11 �� ����
	 }else if(checkSUM ==1){
	  var checkCODE = 11;
	 }else{
	  var checkCODE = checkSUM;
	 }
	  // �������� 11 ���� ����
	 var check1 = 11 - checkCODE;
	 if (J2.charAt(6) >= 0 || J2.charAt(6) <= 9) {
	  var check2 = parseInt(J2.charAt(6))
	 }else{
	  // ���ڰ� �ƴ� ���ڰ� ���� ���� ó��
	  alert("�ֹε�Ϲ�ȣ�� ���ڸ� �Է��ϼž� �մϴ�.");
	  return false;
	 }
	  if(check1 != check2){
	   // �ֹε�Ϲ�ȣ�� Ʋ�� ���� ó��
	   alert("�ֹε�� ��ȣ�� �ٽ� Ȯ�� �ϼ���.");
	   document.memUsersForm.res_no1.focus();
	   return false;
	  }else{
	   return true;
	  }
	 return false;
	}

    /**
     * �����(USR ID, ����ڸ�) �˻� �˾�â�� ����.
     *
     * @param   column �÷���
     *          USR_IDNO USR ID
     *          USR_NAME ����ڸ�
     * @param   keyWord �˻���
     * @param   fn ��Ǹ�
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
     * ���� ���ڷθ� �̷���� �ִ��� üũ �Ѵ�.
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
     * ���� üũ
     *
     * 1. +, - ��ȣ�� �����ϰų� ���� �� �ִ� : ^[\+-]?
     * 2. 0���� 9���� ���ڰ� 0�� �̻� �� �� �ִ� : [0-9]*
     * 3. �������� ���ڷ� ������ �Ѵ� : [0-9]$
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
     * ������ üũ
     *
     * 1. +, - ��ȣ�� �����ϰų� ���� �� �ִ� : ^[\+-]?
     * 2. 0���� 9���� ���ڰ� 0�� �̻� �� �� �ִ� : [0-9]*
     * 3. �Ҽ����� ���� �� �ִ� : [.]?
     * 4. �Ҽ��� ���� �ڸ��� 0���� 9���� ���ڰ� �� �� �ִ� : [0-9]*
     * 5. �������� ���ڷ� ������ �Ѵ� : [0-9]$
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
     * �̸��� üũ
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
     * �̸��� �ּ� üũ - �����ϰ�
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
            alert("�̸��� �ּҰ� ��Ȯ���� �ʽ��ϴ� (üũ @ and .'s)");
            return false;
        }
        var user=matchArray[1];
        var domain=matchArray[2];
        for (i=0; i<user.length; i++) {
            if (user.charCodeAt(i)>127) {
                alert("�߸��� �̸��� �ּҸ� �Է� �ϼ̽��ϴ�.");
                return false;
            }
        }
        for (i=0; i<domain.length; i++) {
            if (domain.charCodeAt(i)>127) {
                alert("������ �̸��� �߸� ���� �Ǿ����ϴ�.");
                return false;
            }
        }
        if (user.match(userPat)==null) {
            alert("�̸��� �ּҰ� �ƴմϴ�.");
            return false;
        }
        var IPArray=domain.match(ipDomainPat);
        if (IPArray!=null) {
            for (var i=1;i<=4;i++) {
                if (IPArray[i]>255) {
                    alert("IP�ּҰ� Ʋ���ϴ�!");
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
                alert("������ �� ���� ���� �ʽ��ϴ�.");
                return false;
            }
        }
        if (checkTLD && domArr[domArr.length-1].length!=2 &&
            domArr[domArr.length-1].search(knownDomsPat)==-1) {
            alert("�˷��� �������� ���� �����մϴ�." + "country.");
            return false;
        }
        if (len<2) {
        alert("Hostname�� Ʋ���ϴ�. !");
        return false;
        }

        return true;
    }

    /**
     * ��¥ üũ
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
     * ������ ��ȿ���� üũ�Ѵ�.
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

			if(obj.name != ""){ //�̸��� �ִ� ��츸 üũ��.(2004.10.26 suna)

				obj.value = trim(obj.value);
				dispName = obj.getAttribute("dispName");
				dataType = obj.getAttribute("dataType");
				minValue = obj.getAttribute("minValue");
				maxValue = obj.getAttribute("maxValue");
				len      = obj.getAttribute("len");
				lenCheck = obj.getAttribute("lenCheck");
				message  = obj.getAttribute("message"); /// notNull �϶� ����ڰ� ������ �޼����� �����ֱ� ���ؼ�
				messageAdd  = obj.getAttribute("messageAdd"); /// notNull �϶� �⺻�޼��� + ��������Ǹ޼����� �����ش�.
				value = obj.value;

				if (dispName == null) {
					dispName = obj.name;
				}

				/// �۹̼�üũ[2004-08-09]
				/* ���� : ��ǿ���
				/// validation üũ
				frm.perm.value  = "U";
				if( !validate(frm))
					return;

				<input type="hidden" name="perm" message="������ �����ϴ�." perm permCheck="<%= "C|R|U|D" %>" value="">
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
						/// CRUD ���� üũ
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
								permCheckName   = "��� ";
							else if(obj.value == "R")
								permCheckName   = "��ȸ ";
							else if(obj.value == "U")
								permCheckName   = "���� ";
							else if(obj.value == "D")
								permCheckName   = "���� ";

							if(message == null || message == "")
								message = "������ �����ϴ�.";
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
				/// �۹̼�üũ[2004-08-09]

				// �ʼ� �Է� �׸� üũ
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
							alert(dispName + "��(��) �Է��Ͻʽÿ�."+ ((messageAdd == "" || messageAdd == null) ? "" : "\n"+ messageAdd));
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

				// ������ ���� üũ
				if (len != null) {
					if (value.length != eval(len)) {
						alert(dispName + "��(��) " + len + "�ڸ��� �Է��ؾ� �մϴ�.");
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
					  alert(dispName + "��(��) " + lenCheck + " �ڸ��� ������ �����ϴ� ���� ���ڼ�("+value.length+")");
					  obj.focus();
					  if(window.event)
						{
						   window.event.returnValue = false;
						}

						return false;
					}
				}

				if (obj.type == "text") {
					// ������ Ÿ�� üũ
//					if (dataType == null) { // 2002.01.30 �߰�
					if (dataType == null && obj.maxLength!=null && obj.maxLength != -1) { // 2002.01.30 �߰�
						if (obj.readOnly == false && jsByteLength(value) > (obj.maxLength*2)) {
						//if (obj.readOnly == false && jsByteLength(value) > obj.maxLength) {
							alert(dispName + " ���̰� " + obj.maxLength + " ��(��) �ѽ��ϴ�.");
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
							alert(dispName + " ������ �ùٸ��� �ʽ��ϴ�.");
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
									alert(dispName + " ���� �ּҰ�(" + minValue + ") �̻��Դϴ�.");
									obj.focus();
									if (window.event) {
										window.event.returnValue = false;
									}
									return  false;
								}
							}

							if (isValid && (maxValue != null)) {
								if (eval(maxValue) < eval(value)) {
									alert(dispName + " ���� �ִ밪(" + maxValue + ")�� �ʰ��մϴ�.");
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
     * ���ڿ� comma�� ���δ�.
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

            alert(dispName + " ������ �ùٸ��� �ʽ��ϴ�.");
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
     * ���ڿ� comma�� ���δ�.
     */
    function addComma2() {
        var obj = window.event.srcElement;
        addComma(obj);
    }

    /**
     * ���ڿ� comma�� ���δ�.
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
     * ���ڿ��� comma�� ���ش�.
     *
     * @param   obj
     */
    function deleteComma(obj) {
        obj.value = deleteCommaStr(obj.value);
    }

    /**
     * ���ڿ��� comma�� ���ش�.
     */
    function deleteComma2() {
        var obj = window.event.srcElement;
        deleteComma(obj);
        obj.select();
    }

    /**
     * ���ڿ��� comma�� ���ش�.
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
     * ��¥�� "/"�� ���δ�.
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

            alert(dispName + " ������ �ùٸ��� �ʽ��ϴ�.");
            obj.focus();

            return;
        }

        obj.value = addDateFormatStr(value);
    }

    /**
     * ��¥(���)�� "/"�� ���δ�.
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

            alert(dispName + " ������ �ùٸ��� �ʽ��ϴ�.");
            obj.focus();

            return;
        }

        obj.value = addYmFormatStr(value);
    }

    /**
     * ��¥�� "/"�� ���δ�.
     */
    function addDateFormat2() {
        var obj = window.event.srcElement;
        addDateFormat(obj);
    }

    /**
     * ��¥�� "/"�� ���δ�.
     */
    function addYmFormat2() {
        var obj = window.event.srcElement;
        addYmFormat(obj);
    }

    /**
     * ��¥�� "/"�� ���δ�.
     *
     * @param   str
     */
    function addDateFormatStr(str) {
        return  str.substring(0, 4) + "/" + str.substring(4, 6) + "/" + str.substring(6, 8);
    }

    /**
     * ��¥(���)�� "/"�� ���δ�.
     *
     * @param   str
     */
    function addYmFormatStr(str) {
        return  str.substring(0, 4) + "/" + str.substring(4, 6);
    }

    /**
     * ��¥���� "/"�� ���ش�.
     *
     * @param   obj
     */
    function deleteDateFormat(obj) {
        obj.value = deleteDateFormatStr(obj.value);
    }

    /**
     * ��¥���� "/"�� ���ش�.
     */
    function deleteDateFormat2() {
        var obj = window.event.srcElement;
        deleteDateFormat(obj);
        obj.select();
    }

    /**
     * ��¥���� "/"�� ���ش�.
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
     * �̺�Ʈ �ڵ鷯�� ����Ѵ�.
     */
    function setEventHandler() {
        for (i = 0; i < document.forms.length; i++) {

            var elements = document.forms[i].elements;

            for (j = 0; j < elements.length; j++) {
                // INPUT ��ü�� onblur �̺�Ʈ�� �ڵ鷯�� ����Ѵ�.
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
     * �ڸ����� �ּҰ�, �ִ밪
     *
     * �ּҰ��� üũ : jsRange(2, -1)
     * �ִ밪�� üũ : jsRange(-1, 10)
     * �ּҰ�, �ִ밪 ��� üũ : jsRange(2, 10)
     * �ּҰ�, �ִ밪 �Ѵ� üũ ���� : jsRange(-1, -1)
     *
     */
    function jsRange(minValue, maxValue)
    {
        jsMinLength(minValue);
        jsMaxLength(maxValue);
    }

    /**
     * �ִ밪
     */
    function jsMaxLength(maxValue)
    {
        var obj         = window.event.srcElement;
        var dispName    = obj.getAttribute("dispName");
        //var maxValue    = obj.getAttribute("maxValue");
        var val         = jsByteLength(obj.value);

        if( val > (maxValue*2))
        {
            alert(dispName +" ���� �ִ밪("+ maxValue +")�� �ʰ��մϴ�.\n�ʰ� ���� :"+ (val - (maxValue*2)));
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
     * �ּҰ�
     */
    function jsMinLength(minValue)
    {
        var obj         = window.event.srcElement;
        var dispName    = obj.getAttribute("dispName");
        //var minValue    = obj.getAttribute("minValue");
        var val         = jsByteLength(obj.value);
        if(minValue != -1 && val < minValue)
        {
            alert(dispName +" ���� �ּҰ�(" + minValue + ") �̸��Դϴ�.\n���� ���� :"+ (minValue - val));
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
     * �����̸� ����, ���ڰ� �ƴϸ� 0
     */
    function nvlNumber(val)
    {
        if(val == "" || isNaN(val) || val == "undefined")
            return 0;

        return Number(val);
    }

    /**
     * �������Ŀ��� comma�� ���ְ�, ��¥���Ŀ��� "/" �� ���ش�.
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
                /// notHyphen �̶�� �����ߴٸ� �������� ��� �����Ѵ�.
                if(obj.getAttribute("notHyphen") != null) {
                    deleteHyphenObj(obj);
                }
            }
        }
    }

    /**
      * ���ڿ��� Hyphen�� ���ش�.
      *
      * @param  obj
      */
    function deleteHyphenObj(obj) {
        obj.value = deleteHyphen(obj.value);
    }

    /**
     * ������ ��ȿ���� üũ�Ѵ�.
     * �ϳ��� ������Ʈ�� ���� ����.
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

        // �ʼ� �Է� �׸� üũ
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
                alert(dispName + "��(��) �Է��Ͻʽÿ�.");
                obj.focus();
                if (window.event) {
                    window.event.returnValue = false;
                }
                return  false;
            }
        }

        // ������ ���� üũ
        if (len != null) {
            if (value.length != eval(len)) {
                alert(dispName + "��(��) " + len + "�ڸ��� �Է��ؾ� �մϴ�.");
                obj.focus();
                if (window.event) {
                    window.event.returnValue = false;
                }
                return  false;
            }
        }

        if (obj.type == "text") {
            // ������ Ÿ�� üũ
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
                    alert(dispName + " ������ �ùٸ��� �ʽ��ϴ�.");
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
                            alert(dispName + " ���� �ּҰ�(" + minValue + ") �̸��Դϴ�.");
                            obj.focus();
                            if (window.event) {
                                window.event.returnValue = false;
                            }
                            return  false;
                        }
                    }

                    if (isValid && (maxValue != null)) {
                        if (eval(maxValue) < eval(value)) {
                            alert(dispName + " ���� �ִ밪(" + maxValue + ")�� �ʰ��մϴ�.");
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
     * �������Ŀ��� comma�� ���ְ�, ��¥���Ŀ��� "/" �� ���ش�.
     * �ϳ��� ������Ʈ�� ���� ����.
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
      * ���ڿ��� Hyphen�� ���ش�.
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
     * �ֹε�Ϲ�ȣ&����ڹ�ȣ�� '-'�ֱ�
     */
     function setJuminHyphen(obj) {
        var str = deleteHyphen(obj.value);

        if(str.length == 13) {  // �ֹε�Ϲ�ȣ  6-7
            str = str.substring(0, 6) + "-" + str.substring(6);
        }else if(str.length == 10) { // ����ڹ�ȣ 3-2-5
            str = str.substring(0, 3) + "-"+ str.substring(3, 5) + "-"+ str.substring(5);
        }
        obj.value = str;
     }

    /**
     * ���ι�ȣ �� '-'�ֱ�
     */
    function setPupinHyphen(obj) {
        var str = deleteHyphen(obj.value);

        if(str.length == 13) {  // �ֹε�Ϲ�ȣ  6-7
            str = str.substring(0, 6) + "-" + str.substring(6);
        }
        obj.value = str;
    }

    /**
     * �����ֱ⿡ ���� ������ ����Ѵ�.
     * (�Ҽ��� ��ȯ�Ѵ�.)
     *
     * @param   currencyCd ��ȭ
     * @param   yRate ������
     * @param   term �����ֱ�
     * @return  �Ҽ� ����
     */
    function jsRateCalc(currencyCd, yRate, term) {

        var yday = jsYdayCalc(currencyCd);
        var rate = eval((yRate / 100) * (term / 12) * (365 / yday));

        return  rate;
    }

    /**
     * �ݾ��� �ܼ� ó���Ѵ�.
     *
     * ��ȭ(WON)
     *
     *  �ܼ�����
     *      0 - ���̸�
     *      1 - �ʿ��̸�
     *      2 - ����̸�
     *      3 - õ���̸�
     *      4 - �����̸�
     *
     *  �ܼ�ó��
     *      1 - �ݿø�
     *      2 - ����
     *      3 - ����
     *
     * ��ȭ
     *
     *  �ܼ�����
     *      0 - �Ҽ��� 0 �̸�
     *      1 - �Ҽ��� 1 �̸�
     *      2 - �Ҽ��� 2 �̸�
     *
     *  �ܼ�ó��
     *      1 - �ݿø�
     *      2 - ����
     *      3 - ����
     * @param   currency ��ȭ (text)
     * @param   amt �ݾ� (text)
     * @param   unit �ܼ����� (text)
     * @param   method �ܼ�ó�� (text)
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
     * String�� null�� ��� '0'���� �ٲپ� �ش�.
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
     * �� ���� ���� ������Ʈ�� �޸��� �ٿ��ش�.
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
     * �ϼ��� ����Ѵ�.(���ϻ��� ���Ϻһ���)
     *
     * @param   from ������
     * @param   to ������
     * @return  �ϼ�
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
     * ��й�ȣ üũ
     */
    function passChk(p_id, p_pass, obj) {

        var cnt = 0;
        var cnt2 = 1;
        var cnt3 = 1;
        var temp = "";

        /* ��й�ȣ���� ���ڸ� �ԷµǴ°��� üũ - ����*/
        regNum = /^[0-9]+$/gi;
        bNum = regNum.test(p_pass);
        if(bNum) {
            alert('��й�ȣ�� ���ڸ����� �����ϽǼ��� �����ϴ�.');
               obj.focus();
            return false;
        }
        /* ��й�ȣ���� ���ڸ� �ԷµǴ°��� üũ - ����*/
        regNum = /^[a-zA-Z]+$/gi;
        bNum = regNum.test(p_pass);
        if(bNum) {
            alert('��й�ȣ�� ���ڸ����� �����ϽǼ��� �����ϴ�.');
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
            alert("��й�ȣ�� ID�� 4�� �̻� �ߺ��ǰų�, \n���ӵ� ���ڳ� �������� ���ڸ� 4���̻� ����ؼ��� �ȵ˴ϴ�.");
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
            alert("��й�ȣ�� ���ӵ� ���̳� �������� ���ڸ� 4���̻� ����ؼ��� �ȵ˴ϴ�.");
            obj.focus();
            return  false;
        }

        if (cnt3 > 3){
            alert("��й�ȣ�� �ݺ��� ����/���ڸ� 4���̻� ����ؼ��� �ȵ˴ϴ�.");
            obj.focus();
            return  false;
        }

        return true;
    }

    /**
     * �������� ������ üũ�Ѵ�.
     *
     * @param   none
     * @return  none
     */
    function objDetectBrowser() {
        var strUA, s, i;
        this.isIE = false;  // ���ͳ� �ͽ��÷η������� ��Ÿ���� �Ӽ�
        this.isNS = false;  // �ݽ������������� ��Ÿ���� �Ӽ�
        this.version = null; // ������ ������ ��Ÿ���� �Ӽ�

        // Agent ������ ��� �ִ� ���ڿ�.
        strUA = navigator.userAgent;

        s = "MSIE";
        // Agent ���ڿ�(strUA) "MSIE"�� ���ڿ��� ��� �ִ��� üũ

        if ((i = strUA.indexOf(s)) >= 0) {
            this.isIE = true;
            // ���� i���� strUA ���ڿ� �� MSIE�� ���۵� ��ġ ���� ����ְ�,
            // s.length�� MSIE�� ���� ��, 4�� ��� �ִ�.
            // strUA.substr(i + s.length)�� �ϸ� strUA ���ڿ� �� MSIE ������
            // ������ ���ڿ��� �߶�´�.
            // �� ���ڿ��� parseFloat()�� ��ȯ�ϸ� ������ �˾Ƴ� �� �ִ�.
            this.version = parseFloat(strUA.substr(i + s.length));
            return;
        }

        s = "Netscape6/";
        // Agent ���ڿ�(strUA) "Netscape6/"�̶� ���ڿ��� ��� �ִ��� üũ

        if ((i = strUA.indexOf(s)) >= 0) {
            this.isNS = true;
            this.version = parseFloat(strUA.substr(i + s.length));
            return;
        }

        // �ٸ� "Gecko" �������� NS 6.1�� ���.

        s = "Gecko";
        if ((i = strUA.indexOf(s)) >= 0) {
            this.isNS = true;
            this.version = 6.1;
            return;
        }
    }

  /**
   * ȭ�� ũ�⸦ 1024*768�� ���� ��Ų��.
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
     * sub ȭ���� ����� ��ġ ��Ų��.
     * centerSubWindow(winName, wx, wy)
     * winName : ������������ �̸�
     * ww : ����������� �� â�� �ʺ�
     * wh : ����������� �� â�� ����
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
     * ���ڿ����� ������ ���ϴ� ���ڸ� �����Ѵ�.
     *
     * @param   val ���ڿ�
     * @param   str ������ ����
     */
    function jsTrim(val, str) {
        var temp  = val.value;
        temp = temp.split(str);

        val.value = temp.join("");
    }

    /**
     * �� ��ü�� �б��������� �����.
     *
     * @param    form��
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
     * �� ��ü�� �б������� �������� ���� ���´�.
     *
     * @param    form��
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
     * @ String val�� String patt�� �����Ͽ��迭�� �����Ѵ�.
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
     * ���ڷθ� �̷���� �ִ��� üũ �Ѵ�.
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
                alert("���ڸ� �Է� �����մϴ�.");
                num.value = "";
                num.focus();
                return false;
            }
        }
        return true;
    }

    /**
     * �ѱ۷θ� �̷���� �ִ��� üũ �Ѵ�.
     *
     * @param    han
     * @return   boolean
     */
    function isHangul(han) {
        var inText = han.value;
        var ret;

        ret = inText.charCodeAt();
        if (ret > 31 && ret < 127) {
            alert("�ѱ۸� �Է� �����մϴ�.");
            han.value = "";
            han.focus();
            return false;
        }

        return true;
    }

    /**
    * �ѱ��� �Է��ߴ��� üũ, �ѱ��� �Է��ߴٸ� false�� ��ȯ�Ѵ�.
    * ����� ���ڸ� �н���Ų��.
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
    * ����ĳ�������� üũ(�빮��)
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
            alert("�����ڸ� �Է��� �����մϴ�.");
            obj.value = "";
            obj.focus();
            return;
        }
        obj.value = retChar;
    }

    /**
     * Ű���� �Է½� ���ڸ� �Է� ����
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
     * Ű���� �Է½� ���� �� ','�� �Է� ����
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
     * Ű���� �Է½� ���� �� '.'�� �Է� ����
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
     * �������Ŀ� null�� �ԷµǸ� 0���� �����Ѵ�.
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

    /* ��¥���� *******************************************************************************/
    var dateBase  = new Date();

    /**
     * ��
     */
    function getYear()
    {
        return dateBase.getFullYear();
    }

    /**
     * ��
     */
    function getMonth()
    {
        var month = dateBase.getMonth()+1;
        if (("" + month).length == 1)
            month = "0" + month;
        return month;
    }

    /**
     * ��
     */
    function getDay()
    {
        var day = dateBase.getDate();
        if(("" + day).length == 1)
            day   = "0" + day;
        return day;
    }

    /**
     * �����Ϻ��� Ư������ ����(0), ����(1)�� ��¥�� �����Ѵ�.(YYYYMMDD)
     */
    function getIntervalDate(term, isPrevNext)
    {
        var year2, month2, day2;
        var dt = new Date(getMonth() +"-"+ getDay() +"-"+ getYear());
        var anyTime;
        var anyDate;
        if(isPrevNext == "0") /// ����
            anyTime = dt.getTime() - (term) * 1000 * 3600 * 24;
        else /// ����
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
     * �����Ϻ��� Ư������ ����(0), ����(1)�� ��¥�� �����Ѵ�.(YYYYMMDD)
     */
    function getIntervalDate2(kijunDate, term, isPrevNext)
    {
        var year2, month2, day2;
        var dt = toTimeObject(deleteDateFormatStr(kijunDate) +"0000");
        var anyTime;
        var anyDate;
        if(isPrevNext == "0") /// ����
            anyTime = dt.getTime() - (term) * 1000 * 3600 * 24;
        else /// ����
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
     * �����Ϻ��� Ư������ ����(0), ����(1)�� ������ ��ŭ ���̳��� ��¥�� �����Ѵ�.(YYYYMMDD)
     */
    function getIntervalMonth(kijunDate, term, isPrevNext)
    {
        var kijunDate   = deleteDateFormatStr(kijunDate);
        var year        = kijunDate.substring(0,4); /// ��
        var month       = kijunDate.substring(4,6); /// ��
        var date        = kijunDate.substring(6,8); /// ��
        var addMonth;
        var addYear;
        var tempYear;
        var tempMonth;
        var rtnDate;

        if(isPrevNext == "0") /// ����
        {
            addMonth    = eval(month) - eval(term);
            addYear     = Math.floor(eval(addMonth/12)); /// ���� �⵵ ���
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
        else /// ����
        {
            addMonth    = eval(month) + eval(term);
            addYear     = Math.floor(eval(addMonth/13)); /// ������ �⵵ ���
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

        tempMonth   = tempMonth + ""; /// ���̸� �˾ƺ������� string���� �ٲ���.
        if(tempMonth.length == 1)
        {
            tempMonth = "0" + tempMonth;
        }
        /// �ش���� �ش����� �����ϴ��� üũ�ϰ� �������� �ʴ´ٸ� ������ ���� �����´�.
        if( !isValidDay(eval(year) + eval(addYear), tempMonth, date))
            date = getLastDay(eval(year) + eval(addYear), tempMonth);

        rtnDate = eval(year) + eval(addYear) +""+ tempMonth +""+ date;
        //alert(">��¥ ::"+ rtnDate);

        return rtnDate;
    }

    /**
     * Time ��Ʈ���� �ڹٽ�ũ��Ʈ Date ��ü�� ��ȯ
     *
     * parameter time: Time ������ String
     */
    function toTimeObject(time)
    { //parseTime(time)
        var year  = time.substr(0,4);
        var month = time.substr(4,2) - 1; // 1��=0,12��=11
        var day   = time.substr(6,2);
        var hour  = time.substr(8,2);
        var min   = time.substr(10,2);

        return new Date(year,month,day,hour,min);
    }

    /**
     * �ڹٽ�ũ��Ʈ Date ��ü�� Time ��Ʈ������ ��ȯ
     *
     * parameter date: JavaScript Date Object
     */
    function toTimeString(date)
    { //formatTime(date)
        var year  = date.getFullYear();
        var month = date.getMonth() + 1; // 1��=0,12��=11�̹Ƿ� 1 ����
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
     * ��ȿ��(�����ϴ�) ��(��)���� üũ
     */
    function isValidMonth(mm)
    {
        var m = parseInt(mm,10);
        return (m >= 1 && m <= 12);
    }

    /**
     * ��ȿ��(�����ϴ�) ��(��)���� üũ
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
     * �ش� ���� ������ ���� �����´�.
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
     * ��ȿ��(�����ϴ�) ��(��)���� üũ
     */
    function isValidHour(hh)
    {
        var h = parseInt(hh,10);
        return (h >= 1 && h <= 24);
    }

    /**
     * ��ȿ��(�����ϴ�) ��(��)���� üũ
     */
    function isValidMin(mi)
    {
        var m = parseInt(mi,10);
        return (m >= 1 && m <= 60);
    }

    /**
     * ���糯¥�� �����Ѵ�.
     *
     */
    function getCurDate()
    {
        var date  = new Date();
        var year  = date.getFullYear();
        var month = date.getMonth() + 1; // 1��=0,12��=11�̹Ƿ� 1 ����
        var day   = date.getDate();
        var hour  = date.getHours();
        var min   = date.getMinutes();

        if (("" + month).length == 1) { month = "0" + month; }
        if (("" + day).length   == 1) { day   = "0" + day;   }
        if (("" + hour).length  == 1) { hour  = "0" + hour;  }
        if (("" + min).length   == 1) { min   = "0" + min;   }

        return ("" + year + month + day)
    }
    /* ��¥���� *******************************************************************************/

    /**
     * ��¥�� üũ�Ͽ� �ݿ��� return
     *
     * @param       ��¥
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
     * ��¥�� üũ�Ͽ� ���ָ� return
     *
     * @param       ��¥
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
     * ��¥�� üũ�Ͽ� ���ϸ� return
     *
     * @param       ��¥
     */
    function jsThisDay(nowDate) {
        var form = document.form1;

        form.fromDate.value = addDateFormatStr(nowDate);
        form.toDate.value = addDateFormatStr(nowDate);
    }

    /**
     * ������ ��¥�� ����(1 -> ��, ~ 7 -> ��)
     *
     * @param       ��¥
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
     * ����Ű ������ �ڵ����� ���� �ʵ�� �̵�
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
     * ȭ���� ù��° TextField�� ��Ŀ�� �̵�
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
     * ȭ���� ���ϴ� TextField �� TextArea�� ��Ŀ�� �̵�
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
     * FM## - getFM(12, 4) -> 0012�� �����Ѵ�.
     * @param       val ���� ��
     * @param       len ������ (0�� ä�� ����)
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
     * �۹̼� üũ
     * �ϳ��� ������Ʈ�� ���� ����.
     *
     * @param   form
     * @param   obj
     */
    function permission(form, obj)
    {
        /// ��ư�� ������ ���� �����ؾ� �Ѵ�. perm permType="C" permCheck="<%= "C" %>"
        var permType    = ""; /// ���� ��ư�� CRUD Ÿ��
        var permCheck   = ""; /// ����ڰ� ���� �۹̼�
        var dispName    = ""; /// ���÷��̸�
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
                        /// CRUD ���� üũ
                        for(var j=0; j<permCheckArr.length; j++)
                        {
                            if(permType == permCheckArr[j])
                            {
                                break;
                            }
                            else
                            {
                                alert(dispName +" ������ �����ϴ�.");
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
     * �ؽ�Ʈ�ʵ��� �� ��(������)���� ��Ŀ�� �̵��ϱ�.
     * <input type="textfield" name="addr" onFocus="endFocus(this);">
     *
     * @param   obj
     */
    function endFocus(obj)
    {
        obj.value = obj.value + '';
    }

    /**
     * ������ư�� ���õǾ��� ��ü�� ���� �����´�.
     *
     * @param       frm document.form
     * @param       elem ������ư ��ü��
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
     * ����üũ.
     *
     * @param       objtext1 document.form.obj
     * @param       oVal ����Ʈ��
     */
	// ����üũ (��뿹 : onkeyup="Javascript:onlynum(frm.num1, '3');" )
	function onlynum(objtext1, oVal){
		var inText = objtext1.value;
		var ret;

		for (var i = 0; i < inText.length; i++) {
		    ret = inText.charCodeAt(i);
			if (!((ret > 47) && (ret < 58)))  {
	                   alert("���ڸ��� �Է��ϼ���");
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

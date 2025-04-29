
function cartSubmit(bNo) {
	document.cartForm.submit();
}

function Balert() {
	alert("회원들에게 제공되는 자료입니다.\n로그인을 하셔야 합니다.");
	return;
}

function Calert() {
	alert("유료 회원들에게 제공되는 자료입니다.\n로그인을 하시고 유료회원으로 등록을 하셔야 합니다.");
	return;
}

function Dalert() {
	alert("건당결제가 필요한 유료자료입니다.\n로그인을 하신 후 결제하기 버튼을 클릭하세요.\n유료회원인 경우 할인율을 적용받으실 수 있습니다.");
	return;
}

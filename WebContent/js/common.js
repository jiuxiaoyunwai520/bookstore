function _change() {
	$("#vCode").attr("src", "/bookstore/VerifyCode?time=" + new Date().getTime());
}
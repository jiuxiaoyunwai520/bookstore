$(function(){

	//页面加载时刻，判断span中是否有错误信息，若果有，显示，如果没有，隐藏
	$(".spanError").each(function(){
		//判断是否有文本信息
		if($(this).text()){
			$(this).show();
		}else{
			$(this).hide();
		}
	});
	
	/**
	 * 当鼠标点击输入框，隐藏错误信息
	 * $(".tdInput").focus指的是给每一个input加上onfocus事件
	 */
	$(".tdInput").focus(function(){
		var name = $(this).attr("name");
		$("#"+name+"Error").hide();
	});
	
	$(".tdInput").blur(function(){
		var name = $(this).attr("name");
		invokeValidateMethod(name);
	});
	
});

function invokeValidateMethod(name){
	var functionName = "validate"+name+"()";
	eval(functionName);
	//validateloginnname();
}

/*
 * 校验密码
 */
function validateloginpass() {
	var bool = true;
	$("#loginpassError").css("display", "none");
	var value = $("#loginpass").val();
	if(!value) {// 非空校验
		$("#loginpassError").css("display", "");
		$("#loginpassError").text("密码不能为空！");
		bool = false;
	} else if(value.length < 3 || value.length > 20) {//长度校验
		$("#loginpassError").css("display", "");
		$("#loginpassError").text("密码长度必须在3 ~ 20之间！");
		bool = false;
	} else {// 验证原密码是否正确
		$.ajax({
			cache: false,
			async: false,
			type: "POST",
			dataType: "json",
			data: {flag: "validateLoginpass", loginpass: value},
			url: "/bookstore/UserAction",
			success: function(flag) {
				if(!flag) {
					$("#loginpassError").css("display", "");
					$("#loginpassError").text("原密码错误！");
					bool = false;				
				}
			}
		});
	}
	return bool;
}

// 校验新密码
function validatenewpass() {
	var bool = true;
	$("#newpassError").css("display", "none");
	var value = $("#newpass").val();
	if(!value) {// 非空校验
		$("#newpassError").css("display", "");
		$("#newpassError").text("新密码不能为空！");
		bool = false;
	} else if(value.length < 3 || value.length > 20) {//长度校验
		$("#newpassError").css("display", "");
		$("#newpassError").text("新密码长度必须在3 ~ 20之间！");
		bool = false;
	}
	return bool;
}

/*
 * 校验确认密码
 */
function validatereloginpass() {
	
	var bool = true;
	$("#reloginpassError").css("display", "none");
	var value = $("#reloginpass").val();
	if(!value) {// 非空校验
		$("#reloginpassError").css("display", "");
		$("#reloginpassError").text("确认密码不能为空！");
		bool = false;
	} else if(value != $("#newpass").val()) {//两次输入是否一致
		$("#reloginpassError").css("display", "");
		$("#reloginpassError").text("两次密码输入不一致！");
		bool = false;
	}
	return bool;	
}

function validateverifyCode(){
	var name="verifyCode";
	var verifyCode = $("#verifyCode").val();
	if(!verifyCode){
		$("#"+name+"Error").show();
		$("#"+name+"Error").text("验证码不能为空");
	}else if(verifyCode.length!=4){
		$("#"+name+"Error").show();
		$("#"+name+"Error").text("验证码错误，长度必须4位");
	}else{
		//ajax访问数据库，查看用户名是否已经被注册
		//ajax访问数据库，查看用户名是否已经被注册
		$.ajax({
			url:"/bookstore/UserAction",
			data:{flag:"validateVerifyCode",verifyCode:verifyCode},
			dataType:"json",
			success:function(result){
				//如果已经注册，返回false
				if(!result){
					//说明提示用户，修改
					$("#"+name+"Error").show();
					$("#"+name+"Error").text("验证码错误！");
				}
			}
		});
	}
	
}

function _change(){
	var img = document.getElementById("img");
	img.src="/bookstore/VerifyCode?time="+new Date().getTime();
}

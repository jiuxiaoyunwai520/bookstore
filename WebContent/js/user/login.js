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
//校验用户名
function validateloginname(){
	var name="loginname";
	/**
	 * 用户名不能为空；
		用户名长度必须在2 ~ 20之间；
		用户名已被注册。
	 */
	var loginnameValue = $("#loginname").val();
	if(loginnameValue==null||loginnameValue==""){
		$("#"+name+"Error").show();
		$("#"+name+"Error").text("用户名不能为空");
	}else if(loginnameValue.length<2||loginnameValue.length>20){
		$("#"+name+"Error").show();
		$("#"+name+"Error").text("用户名长度必须在2到20之间");
	}else{
		//ajax访问数据库，查看用户名是否已经被注册
		$.ajax({
			url:"/bookstore/UserAction",
			data:{flag:"validateLoginname",loginname:loginnameValue},
			type:"post",
			dataType:"json",
			success:function(result){
				//如果已经注册，返回false
				if(result){
					//说明提示用户，修改用户名
					$("#"+name+"Error").show();
					$("#"+name+"Error").text("请更换用户名，用户名不存在！");
				}
			}
		});
	}
	
}
/**
 * 校验密码
 *   密码不能为空；
密码长度必须在3 ~ 10之间；
 */
function validateloginpass(){
	var name = "loginpass";
	var loginpass = $("#loginpass").val();
	if(loginpass==null||loginpass==""){
		$("#"+name+"Error").show();
		$("#"+name+"Error").text("密码不能为空");
	}else if(loginpass.length<3||loginpass.length>10){
		$("#"+name+"Error").show();
		$("#"+name+"Error").text("密码长度必须在3到10之间");
	}
}

/**
 * 验证码不能为空；
	验证码错误
 */
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
$().ready(function() {
	$("#login_form").validate({
		submitHandler:function(){
			
			$.ajax({
		        type:"post",
		        url:"/login",
		        data:$("#login_form").serialize(),
		        dataType:"text",
		        success: function(str){
		        	if(str == "success"){
		        		location="/toIndex";
		        	}else {
		        		$("#login_error").html("<font color='red'>用户名或密码错误！</font>");
		        	}
		        }
		      });
		 },
		rules: {
			username: {
				required: true,
				minlength: 6,
				maxlength:20
			},
			password: {
				required: true,
				minlength: 6,
				maxlength:20
			},
		},
		messages: {
			username: {
				required:"请输入用户名",
				minlength:jQuery.format("用户名不能小于{0}个字 符"),
				maxlength:jQuery.format("用户名不能大于{0}个字 符")
			},
			password: {
				required: "请输入密码",
				minlength: jQuery.format("密码不能小于{0}个字 符"),
				maxlength: jQuery.format("密码不能大于{0}个字 符")
			},
		}
	});
	$("#register_form").validate({
		
		submitHandler:function(){
			
			$.ajax({
		        type:"post",
		        url:"/register",
		        data:$("#register_form").serialize(),
		        dataType:"text",
		        success: function(data){
		        	if(data == "success"){
		        		location="/toIndex";
		        	}else{
		        		$("#register_error").html("<font color='red'>用户名或邮箱已经存在！</font>");
		        	}
		        }
		      });
		 },
		rules: {
			username: {
				
				required: true,
				minlength: 6,
				maxlength: 20,
				specialCharFilter:true
			},
			password: {
				required: true,
				minlength: 6,
				maxlength: 20
			},
			rpassword: {
				equalTo: "#register_password"
			},
			email: {
				required: true,
				email: true
			}
		},
		messages: {
			username: {
				required:"必须输入用户名",
				minlength:jQuery.format("用户名不能小于{0}个字 符"),
				maxlength:jQuery.format("用户名不能大于{0}个字 符"),
				specialCharFilter:"不能使用特殊字符"
			},
			password: {
				required: "请输入密码",
				minlength: jQuery.format("密码不能小于{0}个字 符"),
				maxlength: jQuery.format("密码不能大于{0}个字 符")
			},
			rpassword: {
				required: "请重复输入密码",
				equalTo: "两次密码不一样"
			},
			email: {
				required: "请输入邮箱",
				email: "请输入有效邮箱"
			}
		}
	});
});
$(function() {
	$("#register_btn").click(function() {
		$("#register_form").css("display", "block");
		$("#login_form").css("display", "none");
	    $(".error").remove();
	});
	$("#back_btn").click(function() {
		$("#register_form").css("display", "none");
		$("#login_form").css("display", "block");
		$(".error").remove();
	});
	
});
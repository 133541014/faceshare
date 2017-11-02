$().ready(function() {
	$("#add_userinfo_form").validate({
		submitHandler:function(){
			$("#add_userinfo_form").submit();
		 },
		rules: {
			nickname: {
				required: true,
				minlength: 3,
				maxlength:12
			},
			description: {
				required: true,
				minlength: 10,
				maxlength:500
			},
		},
		messages: {
			nickname: {
				required:"请输入昵称",
				minlength:jQuery.format("昵称不能小于{0}个字 符"),
				maxlength:jQuery.format("昵称不能大于{0}个字 符")
			},
			description: {
				required: "请填写个人描述",
				minlength:jQuery.format("个人描述不能小于{0}个字 符"),
				maxlength:jQuery.format("个人描述不能大于{0}个字 符")
			},
		}
	});

});

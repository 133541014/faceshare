$().ready(function() {
	$("#mood_share_form").validate({
		submitHandler:function(){
			$("#mood_share_form").submit();
		 },
		rules: {
			content: {
				required: true,
				minlength: 10,
				maxlength:500
			}
		},
		messages: {
			content: {
				required:"必须输入内容！",
				minlength:jQuery.format("内容不能小于{0}个字 符"),
				maxlength:jQuery.format("内容不能大于{0}个字 符")
			}
		}
	});

});

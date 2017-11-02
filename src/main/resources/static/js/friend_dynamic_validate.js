$().ready(function() {
	$("#send_comment_form").validate({
		submitHandler:function(form){
			form.submit();
		 },
		rules: {
			content: {
				required: true,
				maxlength:50
			}
		},
		messages: {
			content: {
				required:"必须输入内容！",
				maxlength:jQuery.format("内容不能大于{0}个字 符")
			}
		}
	});
	
	$("#send_reply_form").validate({
		submitHandler:function(form){
			form.submit();
		 },
		rules: {
			content: {
				required: true,
				maxlength:50
			}
		},
		messages: {
			content: {
				required:"必须输入内容！",
				maxlength:jQuery.format("内容不能大于{0}个字 符")
			}
		}
	});

});

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=basePath %>/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/ajaxfileupload.js"></script>
<title>文件上传与下载</title>
</head>
<body>
	
	<form type="post">
		帐号：<input id="user" name="user" /><br> 
		密码：<input id="password" name="password" /><br> 
		<label>上传文件：</label> 
		<input id="file" name="file" type="file" onchange="fileChange(this)" /> 
		<a href="<%=basePath%>downModel.xlsx">下载模版文档</a> <br> 
		<a href="javascript:;" class="submitFn">提交</a>
	</form>

	<script type="text/javascript">

		function fileChange(val) {
			$('#file').empty();
			if (excel_check(val.id)) {
				$.ajaxFileUpload({
					fileElementId : val.id, 			//需要上传的文件域的ID，即<input type="file">的ID。
					url : '../file/tempFile', 		//后台方法的路径
					type : 'post', 						//当要提交自定义参数时，这个参数要设置成post
					dataType : 'json', 					//服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
					secureuri : false, 					//是否启用安全提交，默认为false。
					async : true, 						//是否是异步
					success : function(data) { 			//提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
						$('#file').append(data.result)
					},
					error : function() { 				//提交失败自动执行的处理函数。
						alert('上传失败')
					}
				});
			}
		}
		function excel_check(feid) {
			var exc = document.getElementById(feid);
			return /.(xls|xlsx)$/.test(exc.value) ? true : (function() {
				alert("选择excel文件");
				return false;
			})();
		}
		
		
  		$('.submitFn').off().on('click',function(){
  			var username = $('#user').val();
  			var password = $('#password').val();
  			var excelFile =  $('#file').text();
 			if( $.trim(username) == '' ){
 				alert("帐号不为空");
 				return false;
 			}
 			if( $.trim(password) == '' ){
 				alert("密码不为空");
 				return false;
 			}
 			if( $.trim(excelFile) == '' ){
 				alert("请上传文件");
 				return false;
 			} 
 			
  			$.ajax({
 				url:'../file/addFile',
 				type:'post',
 				dataType:'json',
 				data:{"username":username,"password":password,"excelFile":excelFile},
 				success: function(data){
 					if(data.result == 1){
 						alert("success");
 					}else{
 						alert(data.result);
 					}
 				}
 			}); 
		})  

	</script>

</body>
</html>
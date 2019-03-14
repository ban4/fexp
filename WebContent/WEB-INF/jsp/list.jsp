<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=basePath %>/js/jquery-2.2.3.min.js"></script>
<title>管理员工</title>
</head>
<body>
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<div class="header">
					<h1>管理</h1>
				</div>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover" id="emps_table">
					<thead>
						<tr>
							<th>id</th>
							<th>帐号</th>
							<th>密码</th>
							<th>excelId</th>
							<th>excel名称</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 显示分页信息 -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6" id="page_info_area">
				
			</div>
			<!-- 分页条信息 -->
			<div class="col-md-6" id="page_nav_area">
						
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		
		//1.页面加载完成  发送ajax 获得分页数据
		$(function(){
			to_page(1);
		});
		//页面跳转
		function to_page(pn){
			$.ajax({
				url:"../file/queryList",
				data:"pn="+pn,
				type:"post",
				success:function(result){
					//1.解析并显示员工数据
					build_emps_table(result);
					//2.解析显示分页信息
					build_page_info(result);
					//3.解析显示分页条
					build_page_nav(result);
				}
			});
		}
		
		function build_emps_table(result){
			$("#emps_table tbody").empty();
			var j = result.pageInfo.list;
			$.each(j,function(index,item){
				var idTd = $("<td></td>").append(item.id);
				var nameTd = $("<td></td>").append(item.username);
				var passwordTd = $("<td></td>").append(item.password);
				var excelidTd = $("<td></td>").append(item.excelid);
				var excelnameTd = $("<td></td>").append(item.excelname);
				var btnTd = $("<td></td>").html('<form class="hide" action="../file/exportExcel?excelId='
						+ $(this).attr('excelid')+'" method="post"> <button id="btnExport" type="submit" class="btn">导出</button> </form>');
				$("<tr></tr>").append(idTd)
					.append(nameTd)
					.append(passwordTd)
					.append(excelidTd)
					.append(excelnameTd)
					.append(btnTd)
					.appendTo("#emps_table tbody");
			});
		}
		
		function build_page_info(result){
			$("#page_info_area").empty();
			$("#page_info_area").append("当前"+result.pageInfo.pageNum+"页,总共"
											+result.pageInfo.pages+"页,总共条"
											+result.pageInfo.total+"记录");
		}
		
		function build_page_nav(result){
			$("#page_nav_area").empty();
			var ul = $("<ul></ul>").addClass("pagination");
			//构建元素
			var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
			var prePageLi = $("<li></li>").append($("<a></a>").append("上一页"));
			if(result.pageInfo.hasPreviousPage == false){
				firstPageLi.addClass("disabled");
				prePageLi.addClass("disabled");
			}else{
				//为元素添加点击事件
				firstPageLi.click(function(){
					to_page(1);
				});
				prePageLi.click(function(){
					to_page(result.pageInfo.pageNum-1);
				})
			}
			var nextPageLi = $("<li></li>").append($("<a></a>").append("下一页"));
			var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
			if(result.pageInfo.hasNextPage == false){
				nextPageLi.addClass("disabled");
				lastPageLi.addClass("disabled");
			}else{
				nextPageLi.click(function(){
					to_page(result.pageInfo.pageNum+1);
				})
				lastPageLi.click(function(){
					to_page(result.pageInfo.pages);
				});
			}
			//显示 
			//添加首页和前一页的提示
			ul.append(firstPageLi).append(prePageLi);
			//1 2 3 4 5 遍历给ul中添加页码提示
			$.each(result.pageInfo.navigatepageNums,function(index,item){
				var numLi =  $("<li></li>").append($("<a></a>").append(item));
				if(result.pageInfo.pageNum == item){
					numLi.addClass("active");
				}
				numLi.click(function(){
					to_page(item);
				})
				ul.append(numLi);
			});
			//添加下一页和末页提示
			ul.append(nextPageLi).append(lastPageLi)
			var navEle = $("<nav></nav>").append(ul);
			navEle.appendTo("#page_nav_area");
		}
		
		
		/* $(document).on("click",".btn",function(){
			console.info( $('.exportId') );
			
		}); */
	</script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
	
	<div class="container">
		<form class="form-group">
			<div>
				<label for = "title">Title</label>
				<input type ="text" class ="form-control" placeholder = "enter title"
					name="title" id = "title">
			</div>
			<br/>
			<div class = "form-group">
				<label for="content">content</label>
				<textarea rows="5" class="form-cotrol summernote" id="content" name ="content"></textarea>
			</div>
		</form>
		<button type="button" id="btn-save" class="btn btn-primary">글쓰기 완료</button>
	</div>
	<br/>
	<br/>
	<script>
		$(".summernote").summernote({
			 placeholder: 'enter content',
		     tabsize: 5,
		     height: 300
		});
		
		
	</script>
	
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>
	
	

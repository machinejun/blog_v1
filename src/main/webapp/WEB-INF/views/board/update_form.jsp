<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>	
	<div class="container">
		<form class="form-group">
			<div>
				<input type="hidden" id="id" value="${board.id }">
				<label for = "title">Title</label>
				<input type ="text" class ="form-control" placeholder = "enter title"
					name="title" id = "title" value="${board.title }">
			</div>
			<br/>
			<div class = "form-group">
				<label for="content">content</label>
				<textarea rows="5" class="form-cotrol summernote" id="content" name ="content" >${board.content}</textarea>
			</div>
		</form>
		<button type="button" id="btn-detail-update" class="btn btn-primary">글수정 완료</button>
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
	
	

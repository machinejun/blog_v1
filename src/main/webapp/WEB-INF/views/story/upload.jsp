<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

<main class="container py-5">
	<div>
		<form action="/story/image/upload" method="post" enctype="multipart/form-data"><!-- enctype = 동시에 문자열과 binory 타입을 보낼때 사용 -->
			<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
			<div class="input-group mt-3">
				<input type="file" name="file" class="custom-file-input" required="required"/>
				<label class="custom-file-label" for="customFile">Choose file</label>			
			</div>
			<div class="input-group mt-3">
				<div class="input-group-prepend">
					<span class="input-group-text">STORY Img 설명</span>				
				</div>
				<input type="text" name="storyText" class="form-control" required="required">				
			</div>
			
			
			<div class="input-group mt-3">
				<button class="btn btn-info" type="submit"> 스토리 등록</button>
			</div> 		
			
			

		</form>
	</div>
</main>


<script>
// Add the following code if you want the name of the file appear on select
$(".custom-file-input").bind("change", function() {
  console.log($(this).val());
  let fileName = $(this).val().split("\\").pop();
  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});
</script>
<%@ include file="../layout/footer.jsp" %>
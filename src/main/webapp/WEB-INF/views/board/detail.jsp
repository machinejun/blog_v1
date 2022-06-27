<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
    
<div class ="container" >
	<button class="btn btn-success" onclick="history.back();">돌아가기</button>
	
	<c:if test="${board.userId.id == principal.user.id }">
		<a href="/board/${board.id}/board_form" class="btn btn-warning" id="btn-update">수정하기</a>
		<button class="btn btn-danger" id="btn-delete">삭제하기</button>
	</c:if>
	
	<br/><br/>
	
	<div class="form-group m-2">
		<h3>${board.title }</h3>
	</div>
	
	<hr/>
	
	<div class="form-group m-2">
		<h3>${board.content }</h3>
	</div>
	
	<br/><br/>
	
	<div>
		글번호 : <span id="board-id"><i>${board.id}</i></span><br/>
		글작성자 : <span id=""><i>${board.userId.username}</i></span>
	</div>
	<br/><br/>
</div>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>  
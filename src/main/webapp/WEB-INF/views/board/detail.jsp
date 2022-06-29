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
	<br/>
	<hr/>
	<br/><br/>
	
	
	<div class="card">
		<div>
			<div class="card-body"><textarea row="1" class="form-control" id="reply-content"></textarea></div>
			<div class="card-footer">
				<button class="btn btn-primary" type="button" id="btn-reply-save">등록</button>
			</div>
		</div>
	</div>
	<br/>
	<div class="card">
		<div class="card-header">댓글 목록</div>
	</div>
	<ul class="list-group list-group-flush" id="reply--box">
		<c:forEach var="reply" items="${board.replys}">
		  	<li class="list-group-item d-flex justify-content-between" id="reply--1">
  				<div> ${reply.content }</div>
  				<div class="">
  					<div>작성자 : ${reply.user.username} &nbsp;&nbsp;</div>
  					<button class="badge badge-danger ">삭제</button>
  				</div>
  			</li>
		</c:forEach>

	</ul>
	<br/><br/>
</div>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>  
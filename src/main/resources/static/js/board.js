let index = {
	init: function() {
		$("#btn-save").bind("click", () => {
			this.save();
		});
		
		$("#btn-delete").bind("click", () => {
			this.deleteById();
		});
		
		$("#btn-detail-update").bind("click", () => {
			this.update();
		});
		
		$("#btn-reply-save").bind("click", () => {
			this.replySave();
		});
	},
	
	save: function() {
		// 데이터 가져오기
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
			
		}
		if(data.title == ""){
			alert("제목은 공백일수 없습니다.")
			return;
		}
		
		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
			
		}).done(function(response){
			if(response.status == 200){
				console.log(response.data);
				alert("글쓰기 완료");
				location.href="/";
			}
			
		}).fail(function(error){
			alert("글쓰기 실패")
			
		})
	},
	
	deleteById: function(){
		let id = $("#board-id").text();
		
		$.ajax({
			type:"DELETE",
			url:"/api/board/" + id		
		}).done(function(data){
			if(data.status == 200){
				alert("삭제 완료!!!!!");
				location.href="/";
			}
			
		}).fail(function(error){
			alert("삭제 실패");
		})
	},
	
	update: function() {
		// 데이터 가져오기
		
		let boardId = $("#id").val();
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
			
		}
		console.log("버튼 클릭");
		console.log(data);
		
		$.ajax({
			type: "PUT",
			url: "/api/board/" + boardId,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			async: false // 동기 통신
		}).done(function(data){
			if(data.status == 200){
				alert("글 수정 완료\n"+ "title: " + data.data.title + "\n" + "content: complete change" );
				location.href="/";
			}
			
		}).fail(function(error){
			alert("글 수정 실패")
		})
	},
	
	// `` 백틱(자바스크립트 변수를 문자열 안에 넣어서 사용할 수 있따.)
	replySave: function() {
		// 데이터 가져오기
		let data = {
			boardId: $("#board-id").text(), 
			content: $("#reply-content").val(),
			
			
		}
		if(data.content == ""){
			alert("머라도 좀 쳐라");
			return;
		}
		
		$.ajax({
			type: "POST",
			url: `/api/board/${data.boardId}/reply`,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
			
		}).done(function(response){
			if(response.status){
				alert("댓글작성 완료");
				console.log(response.data);
				addReplyElemet(response.data);
			}
			
		}).fail(function(error){
			alert("글쓰기 실패")
			
		})
		
	},
	
	replyDelete: function(boardId, replyId) {
		console.log(boardId+ "+" + replyId);
		$.ajax({
			type: "DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`,
			dataType:'json'
		}).done(function(response){
			alert("댓글 삭제 완료")
			var tag = $("#hint").val();
			console.log($('#' + tag));
			$('#' + tag).remove;
		}).fail(function(err){
			console.log(err)
		})
	}
}
function addReplyElemet(reply){
	let pricipalId = $("pricipaId").val();
	let childElement = `<li class="list-group-item d-flex justify-content-between" id="reply--${reply.user.id}">
  				<div> ${reply.content}</div>
  				<input type="hidden" value="reply--${reply.user.id}" id="hint"></input>
  				<div class="">
  					<div>작성자 : ${reply.user.username} &nbsp;&nbsp;</div>
  					<c:if test="${reply.user.id == pricipalId }">
  						<button class="badge badge-danger ml-5" onclick="index.replyDelete(${reply.board.id},${reply.id});">삭제</button>
  					</c:if>
  				</div>
  			</li>`;
  	
  	$("#reply--box").prepend(childElement);
  	$("#reply-content").val("");
}


index.init();
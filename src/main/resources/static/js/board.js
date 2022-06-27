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
	},
	
	save: function() {
		// 데이터 가져오기
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
			
		}
		console.log("버튼 클릭");
		console.log(data);
		
		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
			
		}).done(function(data, textStatus){
			if(data.status == 200){
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
}
index.init();
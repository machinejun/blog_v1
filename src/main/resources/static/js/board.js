let index = {
	init: function() {
		$("#btn-save").bind("click", () => {
			this.save();
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
	}
}
index.init();
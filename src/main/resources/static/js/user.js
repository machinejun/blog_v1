let index = {
	
	init: function(){
		$("#btn-save").bind("click ", () => {
			alert("btn-save 버튼이 눌러졌습니다")
			this.save();
		});
		
		//전통적인 로그인 방식일 때 사용한 부분
		/*
		$("#btn-login").bind("click ", () => {
			alert("btn-login 버튼이 눌러졌습니다")
			this.login();
		});
		*/
		
		$("#btn-update").bind("click ", () => {
			alert("btn-login 버튼이 눌러졌습니다")
			this.update();
		});
	},
	
	save: function(){
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
		//console.log(data);	
		
		//ajax 호출
		$.ajax({
			// 서버측에 회원가입 요청
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json" // 응답이 왔을 때 기본 데이터 타입(Buffered 문자열) -> javaScrpit object 자동 변환
		}).done(function(data, textStatus, xhr){
			// 통신 성공시
			console.log("xhr: " + xhr);
			console.log(xhr);
			console.log("data: " + data);
			console.log("textStatus: " + textStatus);
			alert("회원가입이 완료되었습니다"); // 얼마가 걸릴지 모른다.
			location.href ="/"; 
		}).fail(function(error, status){
			// 통신 실패시
			console.log(error);
			console.log(status);
			alert("회원가입에 실패하였습니다");
		});
	},
	/*
	login: function() {
		let data = {
			username: $("#username").val(),
			password: $("#password").val()
		}
		
		//ajax 호출
		$.ajax({
			// 회원 로그인 요청 -> get(기록에 남는다, 유출 위험성 높아짐), post를 사용해야한다.
			type: "POST",
			url: "/api/user/login",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8", 
			dataType: "json"
		}).done(function(data, textStatus, xhr) {
			alert("로그인이 완료되었습니다.");
			console.log(data);
			location.href ="/blog";
			
		}).fail(function(error) {
			alert("로그인에 실패하였습니다");
			console.log(error);
		});
		
	}
	*/
	
	update: function() {
		let data = {
			id: $("#id").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
		console.log(data);
		$.ajax({
			type:"PUT",
			url: "/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(data) {
			if(data.status == 200){
				alert("회원 정보 수정이 완료되었습니다.");
				location.href="/";
			}
			
		}).fail(function() {
			alert("회원 정보 수정이 실패하였습니다.");
		});
	}
}

index.init();
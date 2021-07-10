/**
 * 
 */
let index ={
	init: function(){
		$("#btn-save").on("click", ()=>{ //function(){}, ()=>{} this를 바인딩하기 위해서 사용
			this.save();
		});
		$("#btn-login").on("click", ()=>{ //function(){}, ()=>{} this를 바인딩하기 위해서 사용
			this.login();
		});
	},
	
	save:function(){
		//alert('save 함수 호출');
		let data = {
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		};
//		console.log(data)
		$.ajax({
			type:"POST",
			url:"/blog/api/user",
			data: JSON.stringify(data),
			contentType:"application/json;charset=utf-8",
			dataType:"json"
		}).done(function(resp){
			alert("회원가입 완료!");
			location.href = "/blog";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); //ajax 통신을 이용해서 3개의 데이터를 json 형식으로 받음 
	},
	
	login:function(){
		//alert('save 함수 호출');
		let data = {
			username:$("#username").val(),
			password:$("#password").val()
		};
//		console.log(data)
		$.ajax({
			type:"POST",
			url:"/blog/api/user/login",
			data: JSON.stringify(data),
			contentType:"application/json;charset=utf-8",
			dataType:"json"
		}).done(function(resp){
			alert("로그인 완료!");
			location.href = "/blog";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	}
}

index.init();
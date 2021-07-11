/**
 * 
 */
let index ={
	init: function(){
		$("#btn-save").on("click", ()=>{ //function(){}, ()=>{} this를 바인딩하기 위해서 사용
			this.save();
		});
		$("#btn-delete").on("click", ()=>{ //function(){}, ()=>{} this를 바인딩하기 위해서 사용
			this.deleteById();
		});
		$("#btn-update").on("click", ()=>{ //function(){}, ()=>{} this를 바인딩하기 위해서 사용
			this.update();
		});
	},
	
	save:function(){
		//alert('save 함수 호출');
		let data = {
			title:$("#title").val(),
			content:$("#content").val(),
		};
//		console.log(data)
		$.ajax({
			type:"POST",
			url:"/api/board",
			data: JSON.stringify(data),
			contentType:"application/json;charset=utf-8",
			dataType:"json"
		}).done(function(resp){
			alert("글쓰기 완료!");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	deleteById:function(){
		let id = $("#id").text();
		$.ajax({
			type:"DELETE",
			url:"/api/board/"+id,
			dataType:"json"
		}).done(function(resp){
			alert(id+"번글 삭제 완료!");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	update:function(){
		let id = $('#id').val();
		
		
		let data = {
			title:$("#title").val(),
			content:$("#content").val(),
		};

		$.ajax({
			type:"PUT",
			url:"/api/board/"+id,
			data: JSON.stringify(data),
			contentType:"application/json;charset=utf-8",
			dataType:"json"
		}).done(function(resp){
			alert(id+"번글 수정 완료!");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
}

index.init();
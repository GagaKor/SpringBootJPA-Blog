<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">
		<form>
			<input type="hidden" id="id" value="${principal.user.id}">
			<div class="form-group">
				<label for="username">UserName:</label> 
				<input type="text" class="form-control" value="${principal.user.username }" placeholder="Enter username" id="username" readonly="readonly">
			</div>
			<c:if test="${empty principal.user.oauth }">
			<div class="form-group">
				<label for="password">Password:</label> <input type="password" class="form-control" placeholder="Enter password" id="password">
			</div>
			</c:if>
			<div class="form-group">
				<label for="email">Email address:</label>
				 <input type="email" class="form-control" value="${principal.user.email}" placeholder="Enter email" id="email"  readonly="readonly">
			</div>
		</form>
			<button id="btn-update" class="btn btn-primary">정보 수정 </button>
	</div>
<script type="text/javascript" src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>
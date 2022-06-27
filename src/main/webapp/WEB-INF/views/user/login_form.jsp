<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ include
file="../layout/header.jsp" %>

<div class= "container-md">
<!-- loginProc를 만들지 않음 스프링 시큐리티가 가로채서 전달 -->
    <form action="/auth/loginProc" method="post">
      <div class="form-group">
        <label for="username">user name:</label>
        <input type="text" class="form-control" name = "username" placeholder="Enter username" id="username">
      </div>

      <div class="form-group">
        <label for="password">password:</label>
        <input type="password" class="form-control" name = "password" placeholder="Enter password" id="password">
      </div>
      <button id="btn-login" type="submit" class="btn btn-primary">로그인</button> 
    </form>
     
</div>
<br/>
<!--  <script src="/js/user.js"></script> -->
<%@ include file="../layout/footer.jsp"%>

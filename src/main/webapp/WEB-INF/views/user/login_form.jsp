<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ include
file="../layout/header.jsp" %>

<div class= "container">
    <form action="#">
      <div class="form-group">
        <label for="username">user name:</label>
        <input type="text" class="form-control" placeholder="Enter username" id="username">
      </div>

      <div class="form-group">
        <label for="password">password:</label>
        <input type="password" class="form-control" placeholder="Enter password" id="password">
      </div> 
    </form>
     <button id="btn-login" type="button" class="btn btn-primary">로그인</button>
</div>
<br/>
<script src="/blog/js/user.js"></script>

<%@ include file="../layout/footer.jsp"%>

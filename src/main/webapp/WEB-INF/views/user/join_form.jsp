<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ include
file="../layout/header.jsp" %>

<div class="container">
  <form action="#">
    <div class="form-group">
      <label for="username">user name:</label>
      <input
        type="text"
        class="form-control"
        placeholder="Enter username"
        id="username"/>
    </div>

    <div class="form-group">
      <label for="password">password:</label>
      <input
        type="password"
        class="form-control"
        placeholder="Enter password"
        id="password"/>
    </div>

    <div class="form-group">
      <label for="email">Email address:</label>
      <input
        type="email"
        class="form-control"
        placeholder="Enter email"
        id="email"/>
    </div>
  </form>
     <button
      type="button"
      class="btn btn-primary"
      id= "btn-save">
      회원가입
    </button>
</div>
<br/>
<script src="/blog/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>

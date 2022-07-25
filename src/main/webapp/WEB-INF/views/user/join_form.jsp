<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ include
file="../layout/header.jsp" %>

<div class="container">
  <form action="/auth/joinProc" method="post">
  	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token }"/>
    <div class="form-group">
      <label for="username"
        >user name:</label
      >
      <input
        type="text"
        class="form-control"
        placeholder="Enter username"
        id="username"
<<<<<<< HEAD
        name="username"/>
=======
      />
>>>>>>> parent of 75973b1 (로그인(no security))
    </div>

    <div class="form-group">
      <label for="password"
        >password:</label
      >
      <input
        type="password"
        class="form-control"
        placeholder="Enter password"
        id="password"
<<<<<<< HEAD
        name="password"/>
=======
      />
>>>>>>> parent of 75973b1 (로그인(no security))
    </div>

    <div class="form-group">
      <label for="email"
        >Email address:</label
      >
      <input
        type="email"
        class="form-control"
        placeholder="Enter email"
        id="email"
<<<<<<< HEAD
        name="email"/>
    </div>
    <button
      type="submit"
=======
      />
    </div>

    <button
      type="button"
>>>>>>> parent of 75973b1 (로그인(no security))
      class="btn btn-primary"
      id= "btn-save"
    >
      회원가입
    </button>
  </form>
<<<<<<< HEAD
     
</div>
<br/>
<!--  
<script src="/js/user.js"></script>
-->
<%@ include file="../layout/footer.jsp"%>
=======
</div>
<br />
<script src="/blog/js/user.js"></script>

<%@ include file="../layout/footer.jsp"
%>
>>>>>>> parent of 75973b1 (로그인(no security))

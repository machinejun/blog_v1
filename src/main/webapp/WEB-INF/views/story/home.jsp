<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<header class="bg-dark py-5">
       <div class="container px-4 px-lg-5 my-5">
           <div class="text-center text-white">
                <h1 class="display-4 fw-bolder">Story style</h1>
                <p class="lead fw-normal text-white-50 mb-0">this blog story page </p>
           </div>
       </div>
</header>

<div class="container">
        <section class="py-5">
            <div class="container px-4 px-lg-5 mt-5">
                <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
                    <c:forEach var="storyImg" items="${imagePages.content}">
	                     <div class="col mb-5">
	                        <div class="card h-100">
	                            <!-- Product image-->
	                            <img class="card-img-top" width=230 height=220 src="http://localhost:9090/upload/${storyImg.storyImgUrl}" alt="..." />
	                            
	                            <!-- Product details-->
	                            <div class="card-body p-4">
	                                <div class="text-center">
	                                	<h6>${storyImg.originFileName}</h6>
	                                    <h5 class="fw-bolder">${storyImg.storyText }</h5>
	                                </div>
	                            </div>
	                            <!-- Product actions-->
	                            <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
	                                <div class="text-center"><a class="btn btn-outline-dark mt-auto" href="#">View options</a></div>
	                            </div>
	                        </div>
	                    </div>                   
                    </c:forEach>

                              
                </div>
            </div>
        </section>
</div>



<%@ include file="../layout/footer.jsp" %>
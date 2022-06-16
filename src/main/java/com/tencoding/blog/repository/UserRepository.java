package com.tencoding.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tencoding.blog.model.User;


public interface UserRepository extends JpaRepository<User, Integer>{
// T = table, Id = primary key
// jpaReposiory 자동으로 Ioc 가 이 객체를 가지고 있나요.
// Bean으로 등록될수 있나요 = Ioc 컨테이너에 담나요
// 그럼 어떻게 가져오나요	
}

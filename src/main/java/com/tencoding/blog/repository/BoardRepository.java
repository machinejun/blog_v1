package com.tencoding.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tencoding.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{
	// SELECT * FROM board WHERE title LIKE = "%title%"
	Page<Board> findByTitleContaining(String title, Pageable pageable);
	
	// 기본 데이터 등은 관리 대상이지만 오브젝트는 관리 대상이 아니다.
	// board : 갯수 등 ....
	
}

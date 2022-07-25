package com.tencoding.blog.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import com.tencoding.blog.dto.ReplyCountBoardDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor // 어노테이션을 사용한 생성자 주입
public class ReplyCountOfBoardRepository {
	
	private final EntityManager entityManager;
	
	public List<ReplyCountBoardDto> replyCount(){
		List<ReplyCountBoardDto> list = new ArrayList<ReplyCountBoardDto>();
		String queryText = "SELECT A.id, A.content, (SELECT COUNT(id) "
				+ "			FROM reply AS b "
				+ "            WHERE b.id = a.id) AS replyCount "
				+ "FROM reply AS a ";
		/*
		 * 2가지 방식
		 * 1. 직접 문자열을 컨트롤 해서 object로 맵핑하는 방식
		 * 2. QLRM 라이브러리를 사용해서 object로 맵핑하는 방식
		 */
		Query query  = entityManager.createNativeQuery(queryText);
		// 1 >>>
		List<Object[]> resultList = query.getResultList();
		System.out.println(resultList.toString());
		resultList.forEach(t ->{
			System.out.println(t.toString());
		});
		
		
		JpaResultMapper jpaResultMapper = new JpaResultMapper();
		return jpaResultMapper.list(query, ReplyCountBoardDto.class);
	}
}

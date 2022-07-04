package com.tencoding.blog.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tencoding.blog.model.Board;
import com.tencoding.blog.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
	@GetMapping({"", "/", "/board/search"})
	public String index(@PageableDefault(size = 4, direction = Direction.DESC, sort = "id", page = 0) Pageable pageable,
				String q, Model model) {
		System.out.println(q);
		String searchTitle = q == null ? "" : q;
		Page<Board> pages = boardService.searchBoardByTitle(searchTitle, pageable);
		
		
		model.addAttribute("pageable", pages);
		model.addAttribute("pagenumbers", makePageNumbers(pages));
		model.addAttribute("searchValue", searchTitle);
		return "/index";
		
		//[1 2 3 4 5 '6' 7 8 9 10]
		//1번 : 현재 페이지 앞 뒤로 5블록 씩 보여야 한다.
		//2번 : 현재 페이지는 비활성화(+ active) 다른 페이지는 활성화
		//3번 : 페이지 버튼을 누르면 해당 페이지로 화면을 이동
	}
	
	private ArrayList<Integer> makePageNumbers(Page<Board> pages) {
		int nowPage = pages.getPageable().getPageNumber();
		int startPage = Math.max(nowPage - 2, 0); //두 인트값 중에 큰 값을 반환 한다.
		int endPage = Math.min(nowPage + 2, pages.getTotalPages()-1);
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = startPage; i <= endPage; i++) {
			list.add(i);
		}
		return list;
	}
	
	@GetMapping("/board/save_form")
	public String saveForm() {
		log.info("saveForm 메서드 호출");
		return "/board/save_form";
	}
	
	@GetMapping("/board/{id}")
	public String findyById(@PathVariable int id, Model model){
		model.addAttribute("board", boardService.boardDetail(id));
		return "/board/detail";
	}
	
	@GetMapping("/board/{id}/board_form")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.boardDetail(id));
		return "/board/update_form";
	}
	
	// security에 맡기지 말고 직접 처리해 보자
	
}

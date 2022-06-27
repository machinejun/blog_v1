package com.tencoding.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tencoding.blog.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
	@GetMapping({"", "/"})
	public String index(@PageableDefault(size = 4, direction = Direction.DESC, sort = "id", page = 0) Pageable pageable, Model model) {
		model.addAttribute("pageable", boardService.getBoardList(pageable));
		return "/index";
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
	

}

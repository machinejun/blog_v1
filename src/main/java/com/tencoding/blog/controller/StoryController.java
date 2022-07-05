package com.tencoding.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tencoding.blog.auth.PrincipalDetail;
import com.tencoding.blog.dto.RequestFileDto;
import com.tencoding.blog.model.Image;
import com.tencoding.blog.service.StoryService;

@Controller
@RequestMapping("/story")
public class StoryController {
	@Autowired
	private StoryService storyService;
	
	
	@GetMapping("/home")
	public String storyHome(Model model, @PageableDefault(size=10, sort = "id", direction = Direction.DESC)Pageable pageable) {
		Page<Image> imagePages = storyService.getImageList(pageable);
		model.addAttribute("imagePages", imagePages);
		return "/story/home";
	}
	
	@GetMapping("/upload")
	public String storyUpload() {
		return "/story/upload";
	}
	
	@PostMapping("/image/upload")
	//public String storyImgUpload(MultipartFile file, String storyText)
	public String storyImgUpload(RequestFileDto fileDto,
			@AuthenticationPrincipal PrincipalDetail principaldetail) {
		
		storyService.imageUpload(fileDto, principaldetail.getUser());		
		return "redirect:/story/home";
	}
}	

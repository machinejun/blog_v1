package com.tencoding.blog.dto;

import org.springframework.web.multipart.MultipartFile;

import com.tencoding.blog.model.Image;
import com.tencoding.blog.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestFileDto {
	//private MultipartFile[] file; 여러개면 배열로 받으면 된다.
	private MultipartFile file;
	private String uuid; // 고유한 파일이름을 만들기 위한 속성
	private String storyText;
	
	public Image toEntity(String storyImageUrl, User user) {
		return Image.builder()
				.storyText(storyText)
				.storyImgUrl(storyImageUrl)
				.originFileName(file.getOriginalFilename())
				.user(user)
				.build();
	}
}

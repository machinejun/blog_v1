package com.tencoding.blog.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tencoding.blog.dto.RequestFileDto;
import com.tencoding.blog.model.Image;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.StoryRepository;

@Service
public class StoryService {
	@Value("${file.path}")
	private String uploadFolder;
	
	@Autowired
	private StoryRepository storyRepository;
	
	
	public Page<Image> getImageList(Pageable pageable){
		return storyRepository.findAll(pageable);
	}
	
	@Transactional
	public void imageUpload(RequestFileDto fileDto, User user) {
		// 파일 업로드 기능이기 때문에 해당 서버에 바이너리 파일을 생성하고 성공하면 DB 저장
		UUID uuid = UUID.randomUUID();
		String imgfileName = uuid + "_" + "story";
		Path imgFilePath = Paths.get(uploadFolder + imgfileName);
		try {
			Files.write(imgFilePath, fileDto.getFile().getBytes());
			Image image = fileDto.toEntity(imgfileName, user);
			storyRepository.save(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

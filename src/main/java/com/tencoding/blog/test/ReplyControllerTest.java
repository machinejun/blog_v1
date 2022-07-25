package com.tencoding.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.dto.ReplyCountBoardDto;
import com.tencoding.blog.model.Board;
import com.tencoding.blog.model.Reply;
import com.tencoding.blog.repository.BoardRepository;
import com.tencoding.blog.repository.ReplyCountOfBoardRepository;
import com.tencoding.blog.repository.ReplyRepository;

@RestController
public class ReplyControllerTest {
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private ReplyRepository replyRepository;
	@Autowired
	private ReplyCountOfBoardRepository boardRepository2;
	
	@GetMapping("/test/board/{boardid}")
	public Board board(@PathVariable int boardid) {
		/*
		 *  jackson 라이브러리 실행될 때 오브젝트 파싱 
		 *  (json을 파싱할 때 get를 호출하는 메서드 동작이 있는데 이때 무한 참조가 걸린다 )
		 *  
		 */
		return boardRepository.findById(boardid).get();
	}
	
	/*
	 * board를 호출했을 때 reply에 포함된 board를 무시하고
	 * reply에서 호출됬을 때는 무시하지 않는다.
	 * 
	 * details.jsp 에서 reply.board를 호출하는 무한 참조가 일어난다. 즉 stack overflow 발생
	 * but 호출하지 않았기 때문에 발생하지 않았다.
	 * 
	 * 해결방법은 
	 * @JsonIgnoreProperties 사용
	 */
	@GetMapping("/test/reply")
	public List<Reply> getReply(){
		return replyRepository.findAll();
	}
		
	@GetMapping("/test/group-by-count3")
	public String test4() {
		List<ReplyCountBoardDto> result = boardRepository2.replyCount();
		return result.toString();
	}

}

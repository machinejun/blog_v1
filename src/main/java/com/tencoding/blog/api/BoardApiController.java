package com.tencoding.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.auth.PrincipalDetail;
import com.tencoding.blog.dto.ResponseDto;
import com.tencoding.blog.model.Board;
import com.tencoding.blog.model.Reply;
import com.tencoding.blog.service.BoardService;

@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	// 1.주소 맵핑 , 데이터 받기
	// 2. 서비스 레이어 만들기
	//
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail detail) {
		// 세션을 어떻게 가져와야 하는가 ?
		boardService.write(board, detail.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		boardService.deleteById(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Board> update(@PathVariable int id, @RequestBody Board board){
		Board newboard =boardService.modifyBoard(id, board);
		return new ResponseDto<Board>(HttpStatus.OK.value(), newboard);
	}
	
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Reply> saveReply(@PathVariable int boardId,@RequestBody Reply reply,@AuthenticationPrincipal PrincipalDetail detail){
		//서비스에 넘겨서 데이터 처리
		Reply replyEntity = boardService.writeReply(detail.getUser(), boardId, reply);
		return new ResponseDto<Reply>(HttpStatus.OK.value(), replyEntity);
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<String> deleteReply(@PathVariable int boardId, @PathVariable int replyId){
		System.out.println("boardID : " + boardId);
		System.out.println("replyID : " + replyId);
		boardService.deleteReplyById(replyId);
		return new ResponseDto<String>(HttpStatus.OK.value(), "ok");
	}
	

}

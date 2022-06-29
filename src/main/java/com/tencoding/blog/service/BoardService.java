package com.tencoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.blog.model.Board;
import com.tencoding.blog.model.Reply;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.BoardRepository;
import com.tencoding.blog.repository.ReplyRepository;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private ReplyRepository replyRepository;
	
	
	@Transactional
	public void write(Board board, User user) {  // title, content, 
		board.setCount(0);
		board.setUserId(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> getBoardList(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board boardDetail(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당글은 찾을 수 없습니다.");
		});
		
	}
	
	@Transactional
	public void deleteById(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	@Modifying
	public Board modifyBoard(int id, Board board) {
		Board boardEntity = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당글은 찾을 수 없습니다.");
		});
		boardEntity.setTitle(board.getTitle());
		boardEntity.setContent(board.getContent());
		return boardEntity;
	}
	
	@Transactional
	public Reply writeReply(User user, int BoardId, Reply requestReply) {
		Board boardEntitiy = boardRepository.findById(BoardId).orElseThrow(() -> {
			return new IllegalArgumentException("댓글 쓰기 실패: 존재하지 않은 게시글");
		});
		
		
		requestReply.setUser(user);
		requestReply.setBoard(boardEntitiy);
		Reply replyEntity = replyRepository.save(requestReply);
		return replyEntity;
	}
}

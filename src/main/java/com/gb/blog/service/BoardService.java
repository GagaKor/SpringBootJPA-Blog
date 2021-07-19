package com.gb.blog.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gb.blog.dto.ReplySaveRequestDTO;
import com.gb.blog.model.Board;
import com.gb.blog.model.User;
import com.gb.blog.repository.BoardRepository;
import com.gb.blog.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;

	@Transactional
	public void 글쓰기(Board board, User user) {
		
		board.setCount(0);
		board.setUser(user); 
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board>  글목록(Pageable pagealbe) {
		return boardRepository.findAll(pagealbe);
	}

	@Transactional(readOnly =  true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id).orElseThrow(()->{return new IllegalArgumentException("글 상세보기 실패 : 해당 아이디를 찾을 수 없습니다.");});		
	}

	
	@Transactional
	public void 삭제하기(int id) {
		 boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정(int id, Board requestBoard) {
		Board board =boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 해당 아이디를 찾을 수 없습니다.");
					});//영속화
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
	}

	@Transactional
	public void 댓글쓰기(ReplySaveRequestDTO replySaveRequestDto) {
		int result = replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		System.out.println("BoardService : "+result);
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}

}

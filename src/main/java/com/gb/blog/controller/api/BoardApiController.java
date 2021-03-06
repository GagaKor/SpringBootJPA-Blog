package com.gb.blog.controller.api;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gb.blog.config.auth.PrinciaplDetail;
import com.gb.blog.dto.ReplySaveRequestDTO;
import com.gb.blog.dto.ResponseDTO;
import com.gb.blog.model.Board;
import com.gb.blog.service.BoardService;

@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;

	@PostMapping("/api/board")
	public ResponseDTO<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrinciaplDetail principal) {
		boardService.글쓰기(board, principal.getUser());
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDTO<Integer> deleteById(@PathVariable int id){
		boardService.삭제하기(id);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	@PutMapping("/api/board/{id}")
	public ResponseDTO<Integer> update(@PathVariable int id, @RequestBody Board board){
		boardService.글수정(id, board);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	// 데이터 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
		@PostMapping("/api/board/{boardId}/reply")
		public ResponseDTO<Integer> replySave(@RequestBody ReplySaveRequestDTO replySaveRequestDto) {
			boardService.댓글쓰기(replySaveRequestDto);
			return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1); 
		}
		
		@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
		public ResponseDTO<Integer> replyDelete(@PathVariable int replyId) {
			boardService.댓글삭제(replyId);
			return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1); 
		}

}

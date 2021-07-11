package com.gb.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gb.blog.model.Board;


public interface BoardRepository extends JpaRepository<Board, Integer> {
	
}


package com.example.springkadaitodo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springkadaitodo.entity.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo, Integer> {

    // title名で完全一致検索
    List<ToDo> findByTitle(String title);
}

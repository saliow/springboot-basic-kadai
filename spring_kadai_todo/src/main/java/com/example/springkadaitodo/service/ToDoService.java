package com.example.springkadaitodo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springkadaitodo.entity.ToDo;
import com.example.springkadaitodo.repository.ToDoRepository;

@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;

    // 依存性の注入（DI）を行う（コンストラクタインジェクション）
    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    // 新規タイトルの登録メソッド
    public void createToDo(String title, String priority, String status) {
        // タイトル名の未入力チェック（空欄はNG）
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("タイトル名を入力してください。");
        }

        // タイトル名の重複チェック（完全一致はNG）
        if (!toDoRepository.findByTitle(title).isEmpty()) {
            throw new IllegalArgumentException("そのタイトル名は既に使用されています。");
        }

        // タイトル登録用のエンティティを作成
        ToDo toDo = new ToDo();
        toDo.setTitle(title);
        toDo.setPriority(priority);
        toDo.setStatus(status);

        // タイトルの登録
        toDoRepository.save(toDo);
    }

    // タイトルの一括取得メソッド
    public List<ToDo> getAllToDos() {
        return toDoRepository.findAll();
    }
}
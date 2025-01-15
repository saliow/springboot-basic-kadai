package com.example.springkadaitodo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springkadaitodo.entity.ToDo;
import com.example.springkadaitodo.service.ToDoService;

@Controller
public class ToDoController {
    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("/todo")
    public String ToDo(Model model) {
        // 最新のユーザーリストを取得
        List<ToDo> todos = toDoService.getAllToDos();

        // ビューにユーザーリストを渡す
        model.addAttribute("todos", todos);

        return "todoView";
    }

    @PostMapping("/register")
    public String registerToDo(RedirectAttributes redirectAttributes,
            @RequestParam("title") String title,
            @RequestParam("priority") String priority,
            @RequestParam("status") String status) {

        try {
            // リクエストパラメータからのデータを用いてユーザー登録
            toDoService.createToDo(title, priority, status);

            // 登録成功時は完了メッセージをビューに受け渡す
            redirectAttributes.addFlashAttribute("successMessage", "タイトル登録が完了しました。");

        } catch (IllegalArgumentException e) {
            // 登録失敗時はエラーメッセージをビューに受け渡す
            redirectAttributes.addFlashAttribute("failureMessage", e.getMessage());

            // 再表示用の入力データをビューに受け渡す（パスワードは除く）
            redirectAttributes.addFlashAttribute("title", title);
            redirectAttributes.addFlashAttribute("status", status);
        }

        // todoにリダイレクトしてリストを再表示
        return "redirect:/todo";
    }
}

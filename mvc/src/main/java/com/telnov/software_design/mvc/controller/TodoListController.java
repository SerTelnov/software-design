package com.telnov.software_design.mvc.controller;

import com.telnov.software_design.mvc.model.Todo;
import com.telnov.software_design.mvc.model.TodoDTO;
import com.telnov.software_design.mvc.model.TodoStatus;
import com.telnov.software_design.mvc.service.TodoListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TodoListController {

    private final TodoListService service;

    public TodoListController(TodoListService service) {
        this.service = service;
    }

    @GetMapping("/todo-list")
    public String getTodoList(Model model) {
        prepareModelMap(model, service.getTodoList());
        return "todo_list";
    }

    @PostMapping("/add-todo")
    public String addTodo(@ModelAttribute("todo") TodoDTO todo) {
        if (!StringUtils.isEmpty(todo.getName()) &&
            !StringUtils.isEmpty(todo.getDescription())
        ) {
            service.addTodo(todo);
        }
        return "redirect:/todo-list";
    }

    @PostMapping("/remove-todo")
    public String removeTodoList(@RequestParam("id") Long todoId) {
        service.removeTodo(todoId);
        return "redirect:/todo-list";
    }

    @PostMapping("/mark-todo")
    public String markTodo(@RequestParam("id") long id) {
        service.markTodo(id);
        return "redirect:/todo-list";
    }

    private void prepareModelMap(Model model, List<Todo> todoList) {
        model.addAttribute("todo_entities", todoList);
        model.addAttribute("todo", new TodoDTO());
    }
}

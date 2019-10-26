package com.telnov.software_design.mvc.controller;

import com.telnov.software_design.mvc.model.Todo;
import com.telnov.software_design.mvc.model.TodoDTO;
import com.telnov.software_design.mvc.model.TodoStatus;
import com.telnov.software_design.mvc.service.TodoListService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/todo")
public class TodoListController {

    private final TodoListService service;

    public TodoListController(TodoListService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Todo> getTodoList() {
        return service.getTodoList();
    }

    @PostMapping("/add")
    public void addTodoList(
            @RequestBody
            List<TodoDTO> todoList
    ) {
        service.addTodo(todoList);
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeTodoList(
            @RequestBody
            List<Long> todoIds
    ) {
        List<Long> invalidIds = todoIds.stream()
                .filter(service::notContainTodoId)
                .collect(Collectors.toList());
        if (!invalidIds.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Invalid ids " +
                            StringUtils.collectionToDelimitedString(invalidIds, ", ", "[", "]")
                    );
        }

        service.removeTodo(todoIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mark")
    public ResponseEntity<String> markTodo(
            @RequestParam("id")
            long id,
            @RequestParam(name = "status", defaultValue = "DONE")
            TodoStatus status
    ) {
        if (service.notContainTodoId(id)) {
            return ResponseEntity.badRequest().body("Invalid id '" + id + "'");
        }

        service.markTodo(id, status);
        return ResponseEntity.ok().build();
    }
}

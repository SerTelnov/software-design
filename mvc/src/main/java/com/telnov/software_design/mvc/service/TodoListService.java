package com.telnov.software_design.mvc.service;

import com.telnov.software_design.mvc.dao.TodoListDao;
import com.telnov.software_design.mvc.model.Todo;
import com.telnov.software_design.mvc.model.TodoDTO;
import com.telnov.software_design.mvc.model.TodoStatus;
import org.springframework.stereotype.Service;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Service
@ParametersAreNonnullByDefault
public class TodoListService {

    private final TodoListDao todoListDao;

    public List<Todo> getTodoList() {
        return todoListDao.findTodoList();
    }

    public TodoListService(TodoListDao todoListDao) {
        this.todoListDao = todoListDao;
    }

    public void addTodo(TodoDTO entities) {
        todoListDao.addTodo(entities);
    }

    public void removeTodo(Long entityId) {
        todoListDao.removeTodo(entityId);
    }

    public void markTodo(final long entityId) {
        todoListDao.markTodo(entityId);
    }
}

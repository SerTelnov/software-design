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

    public boolean notContainTodoId(final long entityId) {
        return todoListDao.findTodoById(entityId).isEmpty();
    }

    public TodoListService(TodoListDao todoListDao) {
        this.todoListDao = todoListDao;
    }

    public void addTodo(List<TodoDTO> entities) {
        entities.forEach(todoListDao::addTodo);
    }

    public void removeTodo(List<Long> entityIds) {
        entityIds.forEach(todoListDao::removeTodo);
    }

    public void markTodo(final long entityId, TodoStatus status) {
        todoListDao.markTodo(entityId, status);
    }
}

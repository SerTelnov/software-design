package com.telnov.software_design.mvc.dao;

import com.telnov.software_design.mvc.model.Todo;
import com.telnov.software_design.mvc.model.TodoDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Component
public class TodoListDaoShitImpl implements TodoListDao {

    @Nonnull
    @Override
    public List<Todo> findTodoList() {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public Optional<Todo> findTodoById(long entityId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addTodo(TodoDTO entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeTodo(long entityId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void markTodo(long entityId) {
        throw new UnsupportedOperationException();
    }
}

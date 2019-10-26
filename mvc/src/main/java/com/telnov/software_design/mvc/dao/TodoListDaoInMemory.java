package com.telnov.software_design.mvc.dao;

import com.google.common.annotations.VisibleForTesting;
import com.telnov.software_design.mvc.exception.InternalException;
import com.telnov.software_design.mvc.model.Todo;
import com.telnov.software_design.mvc.model.TodoDTO;
import com.telnov.software_design.mvc.model.TodoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TodoListDaoInMemory implements TodoListDao {

    private final AtomicLong idCounter = new AtomicLong(0);
    private final Map<Long, Todo> storage;
    private final Clock clock;

    @Autowired
    public TodoListDaoInMemory() {
        this.clock = Clock.systemDefaultZone();
        this.storage = new HashMap<>();
    }

    @VisibleForTesting
    TodoListDaoInMemory(Map<Long, Todo> storage, Clock clock) {
        this.clock = clock;
        this.storage = storage;
    }

    @Nonnull
    @Override
    public List<Todo> findTodoList() {
        return List.copyOf(storage.values());
    }

    @Nonnull
    @Override
    public Optional<Todo> findTodoById(long entityId) {
        return Optional.ofNullable(storage.get(entityId));
    }

    @Override
    public void addTodo(TodoDTO entity) {
        final long id = idCounter.incrementAndGet();
        storage.put(
                id,
                Todo.builder()
                        .setId(id)
                        .setName(entity.getName())
                        .setCreationTime(Instant.now(clock))
                        .setStatus(TodoStatus.BACKLOG)
                        .build()
        );
    }

    @Override
    public void removeTodo(long entityId) {
        validateId(entityId);
        storage.remove(entityId);
    }

    @Override
    public void markTodo(long entityId, TodoStatus status) {
        validateId(entityId);

        Todo todo = storage.get(entityId);
        storage.replace(
                entityId,
                todo.updateStatus(status)
        );
    }

    private void validateId(long entityId) {
        if (!storage.containsKey(entityId)) {
            throw new InternalException("Can't find id '" + entityId + "'");
        }
    }
}

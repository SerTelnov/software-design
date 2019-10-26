package com.telnov.software_design.mvc.dao;

import com.telnov.software_design.mvc.exception.InternalException;
import com.telnov.software_design.mvc.model.Todo;
import com.telnov.software_design.mvc.model.TodoDTO;
import com.telnov.software_design.mvc.model.TodoStatus;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

/**
 * Dao to work with to-do list.
 *
 * @author Sergei Telnov
 */
public interface TodoListDao {

    /**
     * Find all to-do entities.
     */
    @Nonnull
    List<Todo> findTodoList();

    /**
     * Find to-do entity by id.
     *
     * @param entityId to-do entity id
     */
    @Nonnull
    Optional<Todo> findTodoById(final long entityId);

    /**
     * Add to-do entity into storage.
     *
     * @param entity new to-do entity
     */
    void addTodo(TodoDTO entity);

    /**
     * Remove to-do entity from storage.
     *
     * @param entityId to-do entity id
     * @throws InternalException if {@code entityId} don't exist
     */
    void removeTodo(final long entityId);

    /**
     * Mark to-do entity as {@link TodoStatus#DONE}.
     *
     * @param entityId to-do entity id
     * @throws InternalException if {@code entityId} don't exist
     */
    void markTodo(final long entityId, TodoStatus status);
}

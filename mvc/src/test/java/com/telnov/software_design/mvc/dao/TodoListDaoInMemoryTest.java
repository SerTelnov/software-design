package com.telnov.software_design.mvc.dao;

import com.telnov.software_design.mvc.FunctionalTest;
import com.telnov.software_design.mvc.TestUtil;
import com.telnov.software_design.mvc.exception.InternalException;
import com.telnov.software_design.mvc.model.Todo;
import com.telnov.software_design.mvc.model.TodoDTO;
import com.telnov.software_design.mvc.model.TodoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TodoListDaoInMemoryTest extends FunctionalTest {

    private Map<Long, Todo> storage;
    private TodoListDaoInMemory dao;

    @BeforeEach
    void setup() {
        this.storage = new HashMap<>();
        this.dao = new TodoListDaoInMemory(
                storage,
                TestUtil.CLOCK
        );
    }

    @Test
    void findTodoListTest() {
        List<Todo> list = LongStream.range(1L, 5L)
                .mapToObj(TestUtil::makeTodoEntity)
                .collect(Collectors.toList());
        storage.putAll(
                list.stream()
                        .collect(Collectors.toMap(
                                Todo::getId,
                                Function.identity())
                        )
        );

        assertThat(dao.findTodoList())
                .containsExactlyElementsOf(list);
    }

    @Test
    void findTodoByIdTest() {
        Todo todo = TestUtil.makeTodoEntity(1L);
        storage.put(1L, todo);

        Optional<Todo> op = dao.findTodoById(1L);
        assertThat(op.isPresent()).isTrue();
        assertThat(op.get()).isEqualTo(todo);
    }

    @Test
    void findTodoByIdNotFoundTest() {
        Optional<Todo> op = dao.findTodoById(1L);
        assertThat(op.isPresent()).isFalse();
    }

    @Test
    void addEntityTest() {
        TodoDTO entity = new TodoDTO("TODO_1", "Description_1");

        dao.addTodo(entity);
        assertThat(storage).hasSize(1);
        assertThat(storage.get(1L)).isEqualTo(TestUtil.makeTodoEntity(1L));
    }

    @Test
    void removeEntityTest() {
        Todo entity = TestUtil.makeTodoEntity(1L);
        storage.put(1L, entity);

        dao.removeTodo(entity.getId());
        assertThat(storage).isEmpty();
    }

    @Test
    void removeEntityNotFoundTest() {
        assertThatThrownBy(() -> dao.removeTodo(1L))
                .hasMessage("Can't find id '1'");
    }

    @Test
    void markEntityTest() {
        Todo entity = TestUtil.makeTodoEntity(1L);
        storage.put(1L, entity);

        dao.markTodo(entity.getId(), TodoStatus.DONE);
        assertThat(storage.get(1L))
                .isEqualTo(TestUtil.makeTodoEntity(1L, TodoStatus.DONE));
    }

    @Test
    void markEntityNotFoundTest() {
        assertThatThrownBy(() -> dao.markTodo(1L, TodoStatus.BACKLOG))
                .hasMessage("Can't find id '1'");
    }
}

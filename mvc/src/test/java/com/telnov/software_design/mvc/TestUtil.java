package com.telnov.software_design.mvc;

import com.telnov.software_design.mvc.model.Todo;
import com.telnov.software_design.mvc.model.TodoStatus;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class TestUtil {

    private TestUtil() {
        throw new UnsupportedOperationException();
    }

    public static final Clock CLOCK = Clock.fixed(
            Instant.parse("2019-10-26T02:15:30Z"),
            ZoneId.of("UTC")
    );

    public static Todo makeTodoEntity(final long id) {
        return makeTodoEntity(id, TodoStatus.BACKLOG);
    }

    public static Todo makeTodoEntity(final long id, TodoStatus status) {
        return Todo.builder()
                .setId(id)
                .setName("TODO_" + id)
                .setDescription("Description_" + id)
                .setStatus(status)
                .setCreationTime(Instant.now(CLOCK))
                .build();
    }
}

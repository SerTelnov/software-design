package com.telnov.software_design.mvc.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.Instant;
import java.util.Objects;

/**
 * To-do entity.
 *
 * @author Sergei Telnov
 */
@ParametersAreNonnullByDefault
public class Todo {

    /**
     * To-do id.
     */
    private final long id;
    /**
     * To-do name.
     */
    @Nonnull
    private final String name;
    /**
     * To-do description.
     */
    @Nullable
    private final String description;
    /**
     * To-do creation time
     */
    @Nonnull
    private final Instant creationTime;
    /**
     * To-do status.
     */
    @Nonnull
    private final TodoStatus status;

    private Todo(Builder builder) {
        this.id = requirePositiveLong(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.description = builder.description;
        this.creationTime = Objects.requireNonNull(builder.creationTime, "creationTime");
        this.status = Objects.requireNonNull(builder.status, "status");
    }

    private Todo(Todo that, TodoStatus status) {
        this.id = that.id;
        this.name = that.name;
        this.description = that.description;
        this.creationTime = that.creationTime;
        this.status = status;
    }

    private static long requirePositiveLong(long value, String property) {
        if (value < 1) {
            throw new IllegalArgumentException(property + "must to positive");
        }
        return value;
    }

    public long getId() {
        return id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nonnull
    public Instant getCreationTime() {
        return creationTime;
    }

    @Nonnull
    public TodoStatus getStatus() {
        return status;
    }

    @Nonnull
    public Todo updateStatus(TodoStatus status) {
        return new Todo(this, status);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Todo)) return false;
        Todo todo = (Todo) o;
        return id == todo.id &&
                name.equals(todo.name) &&
                Objects.equals(description, todo.description) &&
                creationTime.equals(todo.creationTime) &&
                status == todo.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, creationTime, status);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creationTime=" + creationTime +
                ", status=" + status +
                '}';
    }

    public static class Builder {

        private Long id;
        private String name;
        private String description;
        private Instant creationTime;
        private TodoStatus status;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(@Nullable String description) {
            this.description = description;
            return this;
        }

        public Builder setCreationTime(Instant creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        public Builder setStatus(TodoStatus status) {
            this.status = status;
            return this;
        }


        public Todo build() {
            return new Todo(this);
        }
    }
}

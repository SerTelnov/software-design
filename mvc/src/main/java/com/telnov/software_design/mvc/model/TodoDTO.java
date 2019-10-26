package com.telnov.software_design.mvc.model;

import com.google.common.base.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.beans.ConstructorProperties;

@ParametersAreNonnullByDefault
public class TodoDTO {

    @Nonnull
    private final String name;
    @Nullable
    private final String description;

    @ConstructorProperties({
            "name",
            "description"
    })
    public TodoDTO(
            String name,
            @Nullable
            String description
    ) {
        this.name = name;
        this.description = description;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoDTO todoDTO = (TodoDTO) o;
        return Objects.equal(name, todoDTO.name) &&
                Objects.equal(description, todoDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, description);
    }

    @Override
    public String toString() {
        return "TodoDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

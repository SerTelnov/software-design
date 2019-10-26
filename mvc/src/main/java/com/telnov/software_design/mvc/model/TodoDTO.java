package com.telnov.software_design.mvc.model;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.beans.ConstructorProperties;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class TodoDTO {

    @Nonnull
    private final String name;

    @ConstructorProperties({
            "name"
    })
    public TodoDTO(String name) {
        this.name = name;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoDTO todoDTO = (TodoDTO) o;
        return name.equals(todoDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "TodoDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}

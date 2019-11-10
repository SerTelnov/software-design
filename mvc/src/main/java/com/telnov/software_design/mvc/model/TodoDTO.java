package com.telnov.software_design.mvc.model;

import com.google.common.base.Objects;

public class TodoDTO {

    private String name;
    private String description;

    public TodoDTO() {
    }

    public TodoDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

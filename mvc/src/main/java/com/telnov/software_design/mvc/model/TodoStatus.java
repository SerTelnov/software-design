package com.telnov.software_design.mvc.model;

public enum TodoStatus {
    BACKLOG,
    DONE;

    public TodoStatus changeStatus() {
        switch (this) {
            case DONE:
                return BACKLOG;
            case BACKLOG:
                return DONE;
            default:
                throw new RuntimeException();
        }
    }
}

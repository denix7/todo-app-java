package com.todo.app.domain.entities;

public enum Status {
    PENDING() {
        public boolean isPending(){
            return true;
        }
    },
    DOING() {
        public boolean isDoing() {
            return true;
        }
    },
    DONE() {
        public boolean isDone() {
            return false;
        }
    }
}

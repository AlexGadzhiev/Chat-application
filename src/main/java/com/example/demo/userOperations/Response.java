package com.example.demo.userOperations;

public class Response {
    private int message;

    public Response(int message) {
        this.message = message;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}

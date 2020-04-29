package com.votemine;

public class APIResponse<T> {
    private int status;
    private boolean success;
    private T data;

    public APIResponse() {
    }

    public double getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }
}

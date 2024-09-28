package com.myserieslist.exceptions;

public class MySeriesListException extends RuntimeException {
    private String errorMessage;

    private Integer status;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public MySeriesListException() {
        super();
    }

    public MySeriesListException(String errorMessage, Integer status) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.status = status;
    }
}

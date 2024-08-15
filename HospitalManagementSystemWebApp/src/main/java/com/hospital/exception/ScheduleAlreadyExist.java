package com.hospital.exception;

public class ScheduleAlreadyExist extends Exception {
    public ScheduleAlreadyExist() {
    }

    public ScheduleAlreadyExist(String message) {
        super(message);
    }

    public ScheduleAlreadyExist(String message, Throwable cause) {
        super(message, cause);
    }

    public ScheduleAlreadyExist(Throwable cause) {
        super(cause);
    }

    public ScheduleAlreadyExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

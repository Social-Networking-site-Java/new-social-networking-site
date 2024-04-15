package com.titus.socialnetworkingsite2.Exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;


public enum BusinessErrorCodes {

    NO_CODE(0, NOT_IMPLEMENTED, "No code"),

    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "Current password is Incorrect"),

    NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "New Password does not match"),

    ACCOUNT_LOCKED(302, FORBIDDEN, "Account locked"),

    ACCOUNT_DISABLED(303, FORBIDDEN, "Account disabled"),

    BAD_CREDENTIALS(304, BAD_REQUEST, "Bad credentials");


    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}

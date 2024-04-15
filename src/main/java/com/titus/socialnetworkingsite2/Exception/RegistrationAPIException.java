package com.titus.socialnetworkingsite2.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class RegistrationAPIException extends RuntimeException{

    private HttpStatus httpStatus;
    private String message;
}

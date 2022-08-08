package com.techelevator.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.FORBIDDEN, reason = "User Not Self.")
public class UserNotSelfException extends RuntimeException {
}

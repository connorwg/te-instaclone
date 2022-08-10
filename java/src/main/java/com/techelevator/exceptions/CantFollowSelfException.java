package com.techelevator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Can't Follow Self.", value = HttpStatus.BAD_REQUEST)
public class CantFollowSelfException extends RuntimeException {
}

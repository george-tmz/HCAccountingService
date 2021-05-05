package cn.wbomb.accounting.exception;

import org.springframework.http.HttpStatus;

import java.net.HttpCookie;

public class InvalidParameterException extends ServiceException {

    public InvalidParameterException(String message) {
        super(message);
        this.setStatusCode(HttpStatus.BAD_REQUEST.value());
    }
}

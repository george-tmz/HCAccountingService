package cn.wbomb.accounting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.HttpCookie;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidParameterException extends ServiceException {

    public InvalidParameterException(String message) {
        super(message);
        this.setErrorCode("INVALID_PARAMETER_EXCEPTION");
        this.setStatusCode(HttpStatus.BAD_REQUEST.value());
        this.setErrorType(ErrorType.Client);
    }
}

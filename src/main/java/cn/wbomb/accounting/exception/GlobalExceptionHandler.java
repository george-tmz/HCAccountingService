package cn.wbomb.accounting.exception;

import lombok.val;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final int STATUS_400 = 400;

    @ExceptionHandler(ServiceException.class)
    ResponseEntity<?> handleServiceException(ServiceException ex) {
        val errorResponse = ErrorResponse.builder()
            .statusCode(ex.getStatusCode())
            .message(ex.getMessage())
            .code(ex.getErrorCode())
            .errorType(ex.getErrorType())
            .build();
        return ResponseEntity.status(
                ex.getStatusCode() != 0 ? ex.getStatusCode() : HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(errorResponse);
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    ResponseEntity<?> handleIncorrectCredentialsException(IncorrectCredentialsException ex) {
        val errorResponse = ErrorResponse.builder()
            .statusCode(STATUS_400)
            .message(ex.getMessage())
            .code("INCORRECT_CREDENTIALS")
            .errorType(ServiceException.ErrorType.Client)
            .build();
        return ResponseEntity.status(
                STATUS_400)
            .body(errorResponse);
    }
}

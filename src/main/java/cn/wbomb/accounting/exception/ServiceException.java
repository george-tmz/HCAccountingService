package cn.wbomb.accounting.exception;

import lombok.Data;

/**
 * HC Accounting Service Exception.
 */
@Data
public class ServiceException extends RuntimeException {
    private int statusCode;
    private String errorCode; //biz error code
    private ServiceException.ErrorType errorType; // Service,Client, Unknown


    public enum ErrorType {
        Client,
        Service,
        Unknown
    }

    public ServiceException(String message) {
        super(message);
    }
}

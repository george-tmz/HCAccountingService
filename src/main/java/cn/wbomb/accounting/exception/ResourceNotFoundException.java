package cn.wbomb.accounting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends ServiceException {

    /**
     * Construct method.
     * @param message exception message
     */
    public ResourceNotFoundException(String message) {
        super(message);
        this.setStatusCode(HttpStatus.NOT_FOUND.value());
        this.setErrorCode(BizErrorCode.USER_INFO_NOT_FOUND);
        this.setErrorType(ErrorType.Client);
    }
}

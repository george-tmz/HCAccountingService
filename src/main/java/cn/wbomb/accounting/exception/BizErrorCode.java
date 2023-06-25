package cn.wbomb.accounting.exception;

public enum BizErrorCode {
    INCORRECT_CREDENTIALS("INCORRECT_CREDENTIALS"),
    USER_INFO_NOT_FOUND("USER_INFO_NOT_FOUND"),
    INVALID_PARAMETER_EXCEPTION("INVALID_PARAMETER_EXCEPTION"),
    NO_AUTHORIZED("NO_AUTHORIZED");

    private String message;
    BizErrorCode(String message){
        this.message = message;
    }
}

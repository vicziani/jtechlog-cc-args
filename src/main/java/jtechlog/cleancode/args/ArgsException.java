package jtechlog.cleancode.args;

public class ArgsException extends RuntimeException {


    private final char errorArgumentId;
    private final String errorParameter;
    private final ErrorCode errorCode;

    public static ArgsException withDefaultMessage(ErrorCode errorCode, char errorArgumentId) {
        return new ArgsException(errorCode.getMessage(errorArgumentId), errorCode, errorArgumentId);
    }

    public static ArgsException withDefaultMessage(ErrorCode errorCode, char errorArgumentId, String errorParameter) {
        return new ArgsException(errorCode.getMessage(errorArgumentId, errorParameter), errorCode, errorArgumentId, errorParameter);
    }

    private ArgsException(String errorMessage, ErrorCode errorCode, char errorArgumentId) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorArgumentId = errorArgumentId;
        this.errorParameter = null;
    }


    private ArgsException(String errorMessage, ErrorCode errorCode, char errorArgumentId, String errorParameter) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorArgumentId = errorArgumentId;
        this.errorParameter = errorParameter;
    }

    public char getErrorArgumentId() {
        return errorArgumentId;
    }

    public String getErrorParameter() {
        return errorParameter;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

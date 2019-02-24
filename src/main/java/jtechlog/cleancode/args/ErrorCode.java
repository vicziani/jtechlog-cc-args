package jtechlog.cleancode.args;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public enum ErrorCode {

    INVALID_FORMAT, UNEXPECTED_ARGUMENT, INVALID_ARGUMENT_ID,
    MISSING_STRING,
    MISSING_INTEGER, INVALID_INTEGER,
    MISSING_DOUBLE, INVALID_DOUBLE,
    MISSING_MAP, MALFORMED_MAP;

    private static final String MESSAGE_KEY_PREFIX = "ErrorMessage.";
    private static final String DISPLAYED_ARGUMENT_ID_PREFIX = "-";

    public String getMessage(char errorArgumentId) {
        return MessageFormat.format(getMessageTemplate(), prefixed(errorArgumentId));
    }

    public String getMessage(char errorArgumentId, String errorParameter) {
        return MessageFormat.format(getMessageTemplate(), prefixed(errorArgumentId), errorParameter);
    }

    private static String prefixed(char errorArgumentId) {
        return DISPLAYED_ARGUMENT_ID_PREFIX + errorArgumentId;
    }

    private String getMessageTemplate() {
        return ResourceBundle.getBundle("args", Locale.US)
                .getString(MESSAGE_KEY_PREFIX + name());
    }

}

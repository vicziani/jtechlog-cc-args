package jtechlog.cleancode.args;

public class StringParser implements ArgumentParser<String> {

    private char argumentId;

    private String value;

    public StringParser(char argumentId) {
        this.argumentId = argumentId;
    }

    @Override
    public char getArgumentId() {
        return argumentId;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void parseArgument(String value) {
        this.value = value;
    }

    @Override
    public void handleNotFound() {
        throw ArgsException.withDefaultMessage(ErrorCode.MISSING_STRING, argumentId);
    }
}

package jtechlog.cleancode.args;

public class IntParser implements ArgumentParser<Integer> {

    private char argumentId;

    private int value;

    public IntParser(char argumentId) {
        this.argumentId = argumentId;
    }

    @Override
    public char getArgumentId() {
        return argumentId;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void parseArgument(String arg) {
        try {
            value = Integer.parseInt(arg);
        }
        catch (NumberFormatException nfe) {
            throw ArgsException.withDefaultMessage(ErrorCode.INVALID_INTEGER, argumentId, arg);
        }
    }

    @Override
    public void handleNotFound() {
        throw ArgsException.withDefaultMessage(ErrorCode.MISSING_INTEGER, argumentId);
    }
}

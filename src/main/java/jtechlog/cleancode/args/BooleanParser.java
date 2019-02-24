package jtechlog.cleancode.args;

public class BooleanParser implements ArgumentParser<Boolean> {

    private char argumentId;

    public BooleanParser(char argumentId) {
        this.argumentId = argumentId;
    }

    @Override
    public Boolean getValue() {
        return true;
    }

    @Override
    public char getArgumentId() {
        return argumentId;
    }

    @Override
    public boolean isFlag() {
        return true;
    }

    @Override
    public void parseArgument(String arg) {
        throw new UnsupportedOperationException("Should not have value");
    }

    @Override
    public void handleNotFound() {
        throw new UnsupportedOperationException("Should not have value");
    }
}

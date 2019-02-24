package jtechlog.cleancode.args;

import java.util.ArrayList;
import java.util.List;

public class StringArrayParser implements ArgumentParser<String[]> {

    private char argumentId;

    private List<String> value = new ArrayList<>();

    public StringArrayParser(char argumentId) {
        this.argumentId = argumentId;
    }

    @Override
    public char getArgumentId() {
        return argumentId;
    }

    @Override
    public String[] getValue() {
        return value.toArray(new String[]{});
    }

    @Override
    public void parseArgument(String value) {
        this.value.add(value);
    }

    @Override
    public void handleNotFound() {
        throw ArgsException.withDefaultMessage(ErrorCode.MISSING_STRING, argumentId);
    }
}

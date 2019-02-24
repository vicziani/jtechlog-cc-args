package jtechlog.cleancode.args;

import java.util.HashMap;
import java.util.Map;

public class MapParser implements ArgumentParser<Map<String, String>> {

    private static final String MAP_ENTRY_SEPARATOR = ",";

    private char argumentId;

    private Map<String, String> value = new HashMap<>();

    public MapParser(char argumentId) {
        this.argumentId = argumentId;
    }

    @Override
    public char getArgumentId() {
        return argumentId;
    }

    @Override
    public Map<String, String> getValue() {
        return value;
    }

    @Override
    public void parseArgument(String arg) {
        String[] entries = arg.split(MAP_ENTRY_SEPARATOR);
        for (String entry : entries) {
            String[] tokens = entry.split(":");
            if (tokens.length != 2) {
                throw ArgsException.withDefaultMessage(ErrorCode.MALFORMED_MAP, argumentId);
            }
            value.put(tokens[0], tokens[1]);
        }
    }

    @Override
    public void handleNotFound() {
        throw ArgsException.withDefaultMessage(ErrorCode.MISSING_MAP, argumentId);
    }
}

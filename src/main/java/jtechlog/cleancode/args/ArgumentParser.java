package jtechlog.cleancode.args;

import java.util.List;

public interface ArgumentParser<T> {

    List<String> ARGUMENT_TYPE_DEFINITIONS = List.of("", "*", "#", "##", "[*]", "&");

    char getArgumentId();

    T getValue();

    default boolean isFlag() {
        return false;
    }

    static boolean isValidArgumentTypeDefinition(String format) {
        return ARGUMENT_TYPE_DEFINITIONS.contains(format);
    }

    static ArgumentParser getParser(char argumentId, String argumentTypeDefinition) {
        switch (argumentTypeDefinition) {
            case "":
                return new BooleanParser(argumentId);
            case "*":
                return new StringParser(argumentId);
            case "#":
                return new IntParser(argumentId);
            case "##":
                return new DoubleParser(argumentId);
            case "[*]":
                return new StringArrayParser(argumentId);
            case "&":
                return new MapParser(argumentId);
            default:
                throw new UnsupportedOperationException("Programming error, see the ARGUMENT_TYPE_DEFINITIONS");
        }

    }

    void parseArgument(String arg);

    void handleNotFound();
}

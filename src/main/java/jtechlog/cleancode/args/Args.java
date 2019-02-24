package jtechlog.cleancode.args;

import java.util.*;

public class Args {

    private Map<Character, ArgumentParser> argumentParsers;

    public Args(String schema, String[] args) {
        Map<Character, String> schemaElements = new SchemaReader().readSchema(schema);
        argumentParsers = new ArgumentsReader(schemaElements).parseArguments(args);
    }

    public boolean getBoolean(char argumentId) {
        ArgumentParser parser = argumentParsers.get(argumentId);
        if (parser instanceof BooleanParser) {
            return ((BooleanParser) parser).getValue();
        }
        return false;
    }

    public int getInt(char argumentId) {
        ArgumentParser parser = argumentParsers.get(argumentId);
        if (parser instanceof IntParser) {
            return ((IntParser) parser).getValue();
        }
        return 0;
    }

    public String getString(char argumentId) {
        ArgumentParser parser = argumentParsers.get(argumentId);
        if (parser instanceof StringParser) {
            return ((StringParser) parser).getValue();
        }
        return "";
    }

    public String[] getStringArray(char argumentId) {
        ArgumentParser parser = argumentParsers.get(argumentId);
        if (parser instanceof StringArrayParser) {
            return ((StringArrayParser) parser).getValue();
        }
        return new String[]{};
    }

    public double getDouble(char argumentId) {
        ArgumentParser parser = argumentParsers.get(argumentId);
        if (parser instanceof DoubleParser) {
            return ((DoubleParser) parser).getValue();
        }
        return 0.0;
    }

    public Map<String, String> getMap(char argumentId) {
        ArgumentParser parser = argumentParsers.get(argumentId);
        if (parser instanceof MapParser) {
            return ((MapParser) parser).getValue();
        }
        return Map.of();
    }

    public boolean has(char argumentId) {
        return argumentParsers.containsKey(argumentId);
    }

}

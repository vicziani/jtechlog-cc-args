package jtechlog.cleancode.args;

import java.util.*;

public class ArgumentsReader {

    private static final String ARGUMENT_ID_PREFIX = "-";

    private final Map<Character, String> schemaElements;

    private Queue<ArgumentParser> argumentParsersQueue = new ArrayDeque<>();

    private Map<Character, ArgumentParser> argumentParsers = new HashMap<>();

    public ArgumentsReader(Map<Character, String> schemaElements) {
        this.schemaElements = schemaElements;
    }

    public Map<Character, ArgumentParser> parseArguments(String[] args) {
        Iterator<String> argsIterator = Arrays.asList(args).iterator();
        boolean shouldParseNext = true;
        while (argsIterator.hasNext() && shouldParseNext) {
            shouldParseNext = parseArgument(argsIterator.next());
        }

        if (!argumentParsersQueue.isEmpty()) {
            argumentParsersQueue.poll().handleNotFound();
        }
        return argumentParsers;
    }

    private boolean parseArgument(String arg) {
        if (!argumentParsersQueue.isEmpty()) {
            parseArgumentValue(arg);
            return true;
        } else if (arg.startsWith(ARGUMENT_ID_PREFIX)) {
            parseArgumentIds(arg);
            return true;
        } else {
            return false;
        }
    }

    private void parseArgumentValue(String arg) {
        ArgumentParser parser = argumentParsersQueue.poll();
        Objects.requireNonNull(parser).parseArgument(arg);
    }

    private void parseArgumentIds(String arg) {
        for (int i = 1; i < arg.length(); i++) {
            char argumentId = arg.charAt(i);
            parseArgumentId(argumentId);
        }
    }

    private void parseArgumentId(char argumentId) {
        if (!schemaElements.containsKey(argumentId)) {
            throw ArgsException.withDefaultMessage(ErrorCode.UNEXPECTED_ARGUMENT, argumentId);
        }

        ArgumentParser parser = argumentParsers.get(argumentId);
        if (parser == null) {
            parser = ArgumentParser.getParser(argumentId, schemaElements.get(argumentId));
            argumentParsers.put(parser.getArgumentId(), parser);
        }

        if (!parser.isFlag()) {
            argumentParsersQueue.add(parser);
        }
    }
}

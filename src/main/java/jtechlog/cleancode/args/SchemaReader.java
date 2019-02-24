package jtechlog.cleancode.args;

import java.util.HashMap;
import java.util.Map;

public class SchemaReader {

    private Map<Character, String> schemaElements = new HashMap<>();

    public Map<Character, String> readSchema(String schema) {
        if ("".equals(schema.trim())) {
            return schemaElements;
        }

        String[] tokens = schema.split(",");
        for (String token : tokens) {
            addTokenToSchemaElements(token.trim());
        }
        return schemaElements;
    }

    private void addTokenToSchemaElements(String token) {
        char argumentId = token.charAt(0);
        if (!Character.isLetter(argumentId)) {
            throw ArgsException.withDefaultMessage(ErrorCode.INVALID_ARGUMENT_ID, argumentId);
        }

        String argumentTypeDefinition = token.substring(1);
        if (!ArgumentParser.isValidArgumentTypeDefinition(argumentTypeDefinition)) {
            throw ArgsException.withDefaultMessage(ErrorCode.INVALID_FORMAT, argumentId);
        }

        schemaElements.put(argumentId, argumentTypeDefinition);
    }

}

package jtechlog.cleancode.args;

import org.junit.jupiter.api.Test;

import static jtechlog.cleancode.args.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArgsExceptionTest {

    @Test
    public void testUnexpectedMessage() {
        ArgsException e =
                ArgsException.withDefaultMessage(UNEXPECTED_ARGUMENT,
                        'x');
        assertEquals("Argument -x unexpected.", e.getMessage());
    }

    @Test
    public void testInvalidMessage() {
        ArgsException e =
                ArgsException.withDefaultMessage(INVALID_ARGUMENT_ID,
                        '0');
        assertEquals("'-0' is not a valid argument name.", e.getMessage());
    }


    @Test
    public void testMissingStringMessage() {
        ArgsException e = ArgsException.withDefaultMessage(MISSING_STRING,
                'x');
        assertEquals("Could not find string parameter for -x.", e.getMessage());
    }

    @Test
    public void testInvalidIntegerMessage() {
        ArgsException e =
                ArgsException.withDefaultMessage(INVALID_INTEGER,
                        'x', "Forty two");
        assertEquals("Argument -x expects an integer but was 'Forty two'.",
                e.getMessage());
    }

    @Test
    public void testMissingIntegerMessage() {
        ArgsException e =
                ArgsException.withDefaultMessage(MISSING_INTEGER, 'x');
        assertEquals("Could not find integer parameter for -x.", e.getMessage());
    }

    @Test
    public void testInvalidDoubleMessage() {
        ArgsException e = ArgsException.withDefaultMessage(INVALID_DOUBLE,
                'x', "Forty two");
        assertEquals("Argument -x expects a double but was 'Forty two'.",
                e.getMessage());
    }

    @Test
    public void testMissingDoubleMessage() {
        ArgsException e = ArgsException.withDefaultMessage(MISSING_DOUBLE,
                'x');
        assertEquals("Could not find double parameter for -x.", e.getMessage());
    }
}
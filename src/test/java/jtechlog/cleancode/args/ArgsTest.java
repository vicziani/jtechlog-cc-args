package jtechlog.cleancode.args;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {

    @Test
    public void testSimpleBooleanPresent() {
        Args args = new Args("x", new String[]{"-x"});
        assertTrue(args.getBoolean('x'));
    }

    @Test
    public void testSimpleStringPresent() {
        Args args = new Args("x*", new String[]{"-x", "param"});
        assertTrue(args.has('x'));
        assertEquals("param", args.getString('x'));
    }

    @Test
    public void testSimpleIntPresent() {
        Args args = new Args("x#", new String[]{"-x", "42"});
        assertTrue(args.has('x'));
        assertEquals(42, args.getInt('x'));
    }

    @Test
    public void testSimpleDoublePresent() {
        Args args = new Args("x##", new String[]{"-x", "42.3"});
        assertTrue(args.has('x'));
        assertEquals(42.3, args.getDouble('x'), .001);
    }

    @Test
    public void testCreateWithNoSchemaOrArguments() {
        new Args("", new String[0]);
    }

    @Test
    public void testWithNoSchemaButWithOneArgument() {
        ArgsException e = assertThrows(ArgsException.class,
                () -> new Args("", new String[]{"-x"}));

        assertEquals(ErrorCode.UNEXPECTED_ARGUMENT,
                e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
    }

    @Test
    public void testWithNoSchemaButWithMultipleArguments() {
        ArgsException e = assertThrows(ArgsException.class,
                () -> new Args("", new String[]{"-x", "-y"}));
        assertEquals(ErrorCode.UNEXPECTED_ARGUMENT,
                e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
    }

    @Test
    public void testNonLetterSchema() {
        ArgsException e = assertThrows(ArgsException.class,
                () -> new Args("*", new String[]{}));

        assertEquals(ErrorCode.INVALID_ARGUMENT_ID,
                e.getErrorCode());
        assertEquals('*', e.getErrorArgumentId());
    }

    @Test
    public void testInvalidArgumentFormat() {
        ArgsException e = assertThrows(ArgsException.class,
                () -> new Args("f~", new String[]{}));

        assertEquals(ErrorCode.INVALID_FORMAT, e.getErrorCode());
        assertEquals('f', e.getErrorArgumentId());
    }

    @Test
    public void testMissingStringArgument() {
        ArgsException e = assertThrows(ArgsException.class,
                () -> new Args("x*", new String[]{"-x"}));

        assertEquals(ErrorCode.MISSING_STRING, e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
    }

    @Test
    public void testSpacesInFormat() {
        Args args = new Args("x, y", new String[]{"-xy"});
        assertTrue(args.has('x'));
        assertTrue(args.has('y'));
    }

    @Test
    public void testInvalidInteger() {
        ArgsException e = assertThrows(ArgsException.class,
                () -> new Args("x#", new String[]{"-x", "Forty two"}));

        assertEquals(ErrorCode.INVALID_INTEGER, e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
        assertEquals("Forty two", e.getErrorParameter());
    }

    @Test
    public void testMissingInteger() {
        ArgsException e = assertThrows(ArgsException.class,
                () -> new Args("x#", new String[]{"-x"}));

        assertEquals(ErrorCode.MISSING_INTEGER, e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
    }

    @Test
    public void testInvalidDouble() {
        ArgsException e = assertThrows(ArgsException.class,
                () -> new Args("x##", new String[]{"-x", "Forty two"}));

        assertEquals(ErrorCode.INVALID_DOUBLE, e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
        assertEquals("Forty two", e.getErrorParameter());
    }

    @Test
    public void testMissingDouble() {
        ArgsException e = assertThrows(ArgsException.class,
                () -> new Args("x##", new String[]{"-x"}));

        assertEquals(ErrorCode.MISSING_DOUBLE, e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
    }

    @Test
    public void testExtraArguments() {
        Args args = new Args("x,y*", new String[]{"-x", "-y", "alpha", "beta"});
        assertTrue(args.getBoolean('x'));
        assertEquals("alpha", args.getString('y'));
    }

    @Test
    public void testStringArray() {
        Args args = new Args("x[*]", new String[]{"-x", "alpha"});
        assertTrue(args.has('x'));
        String[] result = args.getStringArray('x');
        assertEquals(1, result.length);
        assertEquals("alpha", result[0]);
    }

    @Test
    public void testMissingStringArrayElement() {

        ArgsException e = assertThrows(ArgsException.class,
                () -> new Args("x[*]", new String[]{"-x"}));

        assertEquals(ErrorCode.MISSING_STRING, e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
    }

    @Test
    public void testManyStringArrayElements() {
        Args args = new Args("x[*]", new String[]{"-x", "alpha", "-x", "beta", "-x", "gamma"});
        assertTrue(args.has('x'));
        String[] result = args.getStringArray('x');
        assertEquals(3, result.length);
        assertEquals("alpha", result[0]);
        assertEquals("beta", result[1]);
        assertEquals("gamma", result[2]);
    }

    @Test
    public void testMapArgument() {
        Args args = new Args("f&", new String[] {"-f", "key1:val1,key2:val2"});
        assertTrue(args.has('f'));
        Map<String, String> map = args.getMap('f');
        assertEquals(2, map.size());
        assertEquals("val1", map.get("key1"));
        assertEquals("val2", map.get("key2"));
    }

    @Test
    public void testMissingMap() {

        ArgsException e = assertThrows(ArgsException.class,
                () -> new Args("x&", new String[]{"-x"}));

        assertEquals(ErrorCode.MISSING_MAP, e.getErrorCode());
        assertEquals('x', e.getErrorArgumentId());
    }

    @Test
    public void testMalformedMapArgument() {
        ArgsException e = assertThrows(ArgsException.class,
                () -> new Args("f&", new String[] {"-f", "key1:val1,key2"}));

        assertEquals(ErrorCode.MALFORMED_MAP, e.getErrorCode());
        assertEquals('f', e.getErrorArgumentId());
    }

    @Test
    public void oneMapArgument() {
        Args args = new Args("f&", new String[] {"-f", "key1:val1"});
        assertTrue(args.has('f'));
        Map<String, String> map = args.getMap('f');
        assertEquals(1, map.size());
        assertEquals("val1", map.get("key1"));
    }

    @Test
    public void testExtraArgumentsThatLookLikeFlags() {
        Args args = new Args("x,y", new String[]{"-x", "alpha", "-y", "beta"});
        assertTrue(args.has('x'));
        assertFalse(args.has('y'));
        assertTrue(args.getBoolean('x'));
        assertFalse(args.getBoolean('y'));
    }

    // Additional

    @Test
    public void testDefaultBooleanDefaultValue() {
        Args args = new Args("x*", new String[]{"-x", "alpha"});
        assertFalse(args.getBoolean('x'));
    }

    @Test
    public void testStringDefaultValue() {
        Args args = new Args("x", new String[]{"-x", "alpha"});
        assertEquals("", args.getString('x'));
    }

    @Test
    public void testIntDefaultValue() {
        Args args = new Args("x", new String[]{"-x", "alpha"});
        assertEquals(0, args.getInt('x'));
    }

    @Test
    public void testDoubleDefaultValue() {
        Args args = new Args("x", new String[]{"-x", "alpha"});
        assertEquals(0.0, args.getDouble('x'));
    }

    @Test
    public void testStringArrayDefaultValue() {
        Args args = new Args("x", new String[]{"-x", "alpha"});
        assertArrayEquals(new String[]{}, args.getStringArray('x'));
    }

    @Test
    public void testMapDefaultValue() {
        Args args = new Args("x", new String[]{"-x", "alpha"});
        assertEquals(Map.of(), args.getMap('x'));
    }

    @Test
    public void testNotFlag() {
        new Args("x", new String[]{"-"});
    }

    @Test
    public void testDuplicate() {
        Args args = new Args("x*", new String[]{"-x", "alpha", "-x", "beta"});
        assertEquals("beta", args.getString('x'));
    }

    @Test
    public void testCompoundParams() {
        Args args = new Args("x*,y*", new String[]{"-xy", "alpha", "beta"});
        assertEquals("alpha", args.getString('x'));
        assertEquals("beta", args.getString('y'));
    }

    @Test
    public void testLooksLikeCompoundParams() {
        Args args = new Args("x*,y*", new String[]{"-x", "-y", "alpha", "beta"});
        assertEquals("-y", args.getString('x'));
    }

}

package org.tamilscriptconverter;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author James Selvakumar
 * @since 1.0
 */
public class TamilScriptConverterTest
{
    @Test
    public void testConvertChar() {
        assertEquals("a", TamilScriptConverter.convertChar("அ"));
        assertEquals("aa", TamilScriptConverter.convertChar("ஆ"));
        assertEquals("i", TamilScriptConverter.convertChar("இ"));
        assertEquals("ee", TamilScriptConverter.convertChar("ஈ"));
        assertEquals("u", TamilScriptConverter.convertChar("உ"));
        assertEquals("oo", TamilScriptConverter.convertChar("ஊ"));
        assertEquals("e", TamilScriptConverter.convertChar("எ"));
        assertEquals("ae", TamilScriptConverter.convertChar("ஏ"));
        assertEquals("ai", TamilScriptConverter.convertChar("ஐ"));
        assertEquals("o", TamilScriptConverter.convertChar("ஒ"));
        assertEquals("oa", TamilScriptConverter.convertChar("ஓ"));
    }

    @Test
    public void testSplitUnicodeChars()
    {
        String input = "அம்மா";
        List<String> expected = Arrays.asList("அ", "ம்", "மா");
        assertEquals(expected, TamilScriptConverter.splitUnicodeChars(input));
    }

    @Test
    public void testConvert() {
        assertEquals("ammaa", TamilScriptConverter.convertWord("அம்மா"));
        assertEquals("appaa", TamilScriptConverter.convertWord("அப்பா"));
    }
}

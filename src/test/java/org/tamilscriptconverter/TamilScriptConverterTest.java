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
    public void testConvertWordsStartingWith_அ() {
        assertEquals("ammaa", TamilScriptConverter.convertWord("அம்மா"));
        assertEquals("appaa", TamilScriptConverter.convertWord("அப்பா"));
        assertEquals("aamaam", TamilScriptConverter.convertWord("ஆமாம்"));
        assertEquals("appam", TamilScriptConverter.convertWord("அப்பம்"));
        assertEquals("annan", TamilScriptConverter.convertWord("அண்ணன்"));
        assertEquals("akkaa", TamilScriptConverter.convertWord("அக்கா"));
    }

    public void testConvertWordsStartingWith_ஆ() {
        assertEquals("aappam", TamilScriptConverter.convertWord("ஆப்பம்"));
    }

    public void testConvertWordsStartingWith_இ() {
        assertEquals("inbam", TamilScriptConverter.convertWord("இன்பம்"));
    }

    public void testConvertWordsStartingWith_ஈ() {
        assertEquals("eenthaar", TamilScriptConverter.convertWord("ஈந்தார்"));
        assertEquals("eesal", TamilScriptConverter.convertWord("ஈசல்"));
    }

    public void testConvertWordsStartingWith_ஐ() {
        assertEquals("aiyam", TamilScriptConverter.convertWord("ஐயம்"));
    }

    public void testConvertWordsStartingWith_ப() {
        assertEquals("payam", TamilScriptConverter.convertWord("பயம்"));
    }
}

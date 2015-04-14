package org.tamilscriptconverter;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * @author James Selvakumar
 * @since 1.0
 */
public class TamilScriptConverterTest
{
    @Test
    public void testIsVowelSign()
    {
        assertTrue(TamilScriptConverter.isVowelSign(TamilScriptConverter.VOWEL_SIGN_AA));
        assertTrue(TamilScriptConverter.isVowelSign(TamilScriptConverter.VOWEL_SIGN_I));
        assertTrue(TamilScriptConverter.isVowelSign(TamilScriptConverter.VOWEL_SIGN_II));
        assertTrue(TamilScriptConverter.isVowelSign(TamilScriptConverter.VOWEL_SIGN_U));
        assertTrue(TamilScriptConverter.isVowelSign(TamilScriptConverter.VOWEL_SIGN_UU));
        assertTrue(TamilScriptConverter.isVowelSign(TamilScriptConverter.VOWEL_SIGN_E));
        assertTrue(TamilScriptConverter.isVowelSign(TamilScriptConverter.VOWEL_SIGN_EE));
        assertTrue(TamilScriptConverter.isVowelSign(TamilScriptConverter.VOWEL_SIGN_AI));
    }

    @Test
    public void testIsVowelSignAfterChar()
    {
        assertTrue(TamilScriptConverter.isSignAfterChar(TamilScriptConverter.VOWEL_SIGN_AA));
        assertTrue(TamilScriptConverter.isVowelSign(TamilScriptConverter.VOWEL_SIGN_I));
        assertTrue(TamilScriptConverter.isVowelSign(TamilScriptConverter.VOWEL_SIGN_II));
        assertTrue(TamilScriptConverter.isVowelSign(TamilScriptConverter.VOWEL_SIGN_U));
        assertTrue(TamilScriptConverter.isVowelSign(TamilScriptConverter.VOWEL_SIGN_UU));
    }

    @Test
    public void testIsVowelSignBeforeChar()
    {
        assertTrue(TamilScriptConverter.isVowelSign(TamilScriptConverter.VOWEL_SIGN_E));
        assertTrue(TamilScriptConverter.isVowelSign(TamilScriptConverter.VOWEL_SIGN_EE));
        assertTrue(TamilScriptConverter.isVowelSign(TamilScriptConverter.VOWEL_SIGN_AI));
    }

    @Test
    public void testEndsWithVowelSignAfterChar()
    {
        assertTrue(TamilScriptConverter.endsWithVowelSignAfterChar("அம்மா"));
        assertFalse(TamilScriptConverter.endsWithVowelSignAfterChar("ம்"));
        assertFalse(TamilScriptConverter.endsWithVowelSignAfterChar("ம"));
    }

    @Test
    public void testConvertChar() {
        //uyir
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
        //mei
        assertEquals("ch", TamilScriptConverter.convertChar("ச்"));
    }

    @Test
    public void testConvertCharWithVowelSignAfterChar()
    {
        //vowel sign aa
        assertEquals("paa", TamilScriptConverter.convertCharWithVowelSignAfterChar("பா"));
        assertEquals("maa", TamilScriptConverter.convertCharWithVowelSignAfterChar("மா"));
        //vowel sign i
        assertEquals("chi", TamilScriptConverter.convertCharWithVowelSignAfterChar("சி"));
        assertEquals("ki", TamilScriptConverter.convertCharWithVowelSignAfterChar("கி"));
        assertEquals("thi", TamilScriptConverter.convertCharWithVowelSignAfterChar("தி"));
        //vowel sign ii
        assertEquals("mee", TamilScriptConverter.convertCharWithVowelSignAfterChar("மீ"));
        //vowel sign u
        assertEquals("mu", TamilScriptConverter.convertCharWithVowelSignAfterChar("மு"));
    }

    @Test
    public void testSplitUnicodeChars()
    {
        String input = "அம்மா";
        List<String> expected = Arrays.asList("அ", "ம்", "மா");
        assertEquals(expected, TamilScriptConverter.splitUnicodeChars(input));
        assertEquals(Arrays.asList("மு", "த", "ல"), TamilScriptConverter.splitUnicodeChars("முதல"));
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

    @Test
    public void testConvertWordsStartingWith_ஆ() {
        assertEquals("aappam", TamilScriptConverter.convertWord("ஆப்பம்"));
    }

    @Test
    public void testConvertWordsStartingWith_இ() {
        assertEquals("inpam", TamilScriptConverter.convertWord("இன்பம்"));
    }

    @Test
    public void testConvertWordsStartingWith_ஈ() {
        assertEquals("eenththaar", TamilScriptConverter.convertWord("ஈந்தார்"));
        assertEquals("eesal", TamilScriptConverter.convertWord("ஈசல்"));
        assertEquals("eetti", TamilScriptConverter.convertWord("ஈட்டி"));
    }

    @Test
    public void testConvertWordsStartingWith_ஐ() {
        assertEquals("aiyam", TamilScriptConverter.convertWord("ஐயம்"));
    }

    @Test
    public void testConvertWordsStartingWith_ப() {
        assertEquals("payam", TamilScriptConverter.convertWord("பயம்"));
    }

    @Test
    public void testConvertWordsStartingWith_ம() {
        assertEquals("meettaar", TamilScriptConverter.convertWord("மீட்டார்"));
        assertEquals("meetpu", TamilScriptConverter.convertWord("மீட்பு"));
        assertEquals("muthala", TamilScriptConverter.convertWord("முதல"));
    }

    @Test
    public void testConvertWordsStartingWith_ச() {
        assertEquals("chinnavan", TamilScriptConverter.convertWord("சின்னவன்"));
        assertEquals("cheental", TamilScriptConverter.convertWord("சீண்டல்"));
    }

    @Test
    public void testConvertWords()
    {
        String input = "";
    }
}

package org.tamilscriptconverter;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public void testEndsWithVowelSignAfterChar()
    {
        assertTrue(TamilScriptConverter.endsWithVowelSign("அம்மா"));
        assertFalse(TamilScriptConverter.endsWithVowelSign("ம்"));
        assertFalse(TamilScriptConverter.endsWithVowelSign("ம"));
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
    public void testConvertCharWithVowelSign()
    {
        //vowel sign aa
        assertEquals("paa", TamilScriptConverter.convertCharWithVowelSign("பா", " "));
        assertEquals("maa", TamilScriptConverter.convertCharWithVowelSign("மா", " "));
        //vowel sign i
        assertEquals("chi", TamilScriptConverter.convertCharWithVowelSign("சி", " "));
        assertEquals("ki", TamilScriptConverter.convertCharWithVowelSign("கி", " "));
        assertEquals("thi", TamilScriptConverter.convertCharWithVowelSign("தி", " "));
        //vowel sign ii
        assertEquals("mee", TamilScriptConverter.convertCharWithVowelSign("மீ", " "));
        //vowel sign u
        assertEquals("mu", TamilScriptConverter.convertCharWithVowelSign("மு", " "));
        //vowel sign uu
        assertEquals("moo", TamilScriptConverter.convertCharWithVowelSign("மூ", "  "));
        //vowel sign o
        assertEquals("ko", TamilScriptConverter.convertCharWithVowelSign("கொ", "  "));
        //vowel sign oo
        assertEquals("yoa", TamilScriptConverter.convertCharWithVowelSign("யோ", "  "));
    }

    @Test
    public void testSplitUnicodeChars()
    {
        String input = "அம்மா";
        List<String> expected = Arrays.asList("அ", "ம்", "மா");
        assertEquals(expected, TamilScriptConverter.splitUnicodeChars(input));
        assertEquals(Arrays.asList("மு", "த", "ல"), TamilScriptConverter.splitUnicodeChars("முதல"));
        assertEquals(Arrays.asList("பெ", "ரி", "ய"), TamilScriptConverter.splitUnicodeChars("பெரிய"));
        assertEquals(Arrays.asList("பே", "ரி", "ன்", "ப", "ம்"), TamilScriptConverter.splitUnicodeChars("பேரின்பம்"));
        assertEquals(Arrays.asList("யே", "சு"), TamilScriptConverter.splitUnicodeChars("இயேசு"));
        assertEquals(Arrays.asList("வை", "கை"), TamilScriptConverter.splitUnicodeChars("வைகை"));
        assertEquals(Arrays.asList("பொ", "ங்", "க", "ல்"), TamilScriptConverter.splitUnicodeChars("பொங்கல்"));
        assertEquals(Arrays.asList("போ", "ட்", "டி"), TamilScriptConverter.splitUnicodeChars("போட்டி"));
        assertEquals(Arrays.asList("பௌ", "ர்", "ண", "மி"), TamilScriptConverter.splitUnicodeChars("பௌர்ணமி"));
        assertEquals(Arrays.asList("அ", "க", "ர", " ", "மு", "த", "ல"), TamilScriptConverter.splitUnicodeChars("அகர முதல"));
    }

    @Test
    public void testTrimUnicodeChars()
    {
        assertEquals(Arrays.asList("யே", "சு"), TamilScriptConverter.trimUnicodeChars(TamilScriptConverter.splitUnicodeChars("இயேசு")));
    }

    @Test
    public void testConvertWordsStartingWith_அ() {

        assertEquals("appaa", TamilScriptConverter.convert("அப்பா"));
        assertEquals("appam", TamilScriptConverter.convert("அப்பம்"));
        assertEquals("annan", TamilScriptConverter.convert("அண்ணன்"));
        assertEquals("akkaa", TamilScriptConverter.convert("அக்கா"));
        assertEquals("ammaa ingkae vaa vaa", TamilScriptConverter.convert("அம்மா இங்கே வா வா"));
        assertEquals("anpu kooruvaen innum athikamaay", TamilScriptConverter.convert("அன்பு கூருவேன் இன்னும் அதிகமாய்"));
    }

    @Test
    public void testConvertWordsStartingWith_ஆ() {
        assertEquals("aappam", TamilScriptConverter.convert("ஆப்பம்"));
        assertEquals("aamaam", TamilScriptConverter.convert("ஆமாம்"));
        assertEquals("aantavar pataiththa vetrriyin naalithu", TamilScriptConverter.convert("ஆண்டவர் படைத்த வெற்றியின் நாளிது"));
    }

    @Test
    public void testConvertWordsStartingWith_இ() {
        assertEquals("inpam", TamilScriptConverter.convert("இன்பம்"));
        assertEquals("yaesu kiristhuvae aantavar", TamilScriptConverter.convert("இயேசு கிறிஸ்துவே ஆண்டவர்"));
        assertEquals("raththam jeyam", TamilScriptConverter.convert("இரத்தம் ஜெயம்"));
    }

    @Test
    public void testConvertWordsStartingWith_ஈ() {
        assertEquals("eenthaar", TamilScriptConverter.convert("ஈந்தார்"));
        assertEquals("eesal", TamilScriptConverter.convert("ஈசல்"));
        assertEquals("eetti", TamilScriptConverter.convert("ஈட்டி"));
    }

    @Test
    public void testConvertWordsStartingWith_உ() {
        //assertEquals("ulagu", TamilScriptConverter.convert("உலகு"));
    }

    @Test
    public void testConvertWordsStartingWith_ஐ() {
        assertEquals("aiyam", TamilScriptConverter.convert("ஐயம்"));
    }

    @Test
    public void testConvertWordsStartingWith_ஒ() {
        assertEquals("onru", TamilScriptConverter.convert("ஒன்று"));
        assertEquals("otrrumai", TamilScriptConverter.convert("ஒற்றுமை"));
    }

    @Test
    public void testConvertWordsStartingWith_ப() {
        assertEquals("payam", TamilScriptConverter.convert("பயம்"));
    }

    @Test
    public void testConvertWordsStartingWith_ம() {
        assertEquals("meettaar", TamilScriptConverter.convert("மீட்டார்"));
        assertEquals("meetpu", TamilScriptConverter.convert("மீட்பு"));
        assertEquals("muthala", TamilScriptConverter.convert("முதல"));
        assertEquals("moochchu", TamilScriptConverter.convert("மூச்சு"));
        assertEquals("moonru", TamilScriptConverter.convert("மூன்று"));
    }

    @Test
    public void testConvertWordsStartingWith_ச() {
        assertEquals("chinnavan", TamilScriptConverter.convert("சின்னவன்"));
        assertEquals("cheental", TamilScriptConverter.convert("சீண்டல்"));
        assertEquals("suntal", TamilScriptConverter.convert("சுண்டல்"));
        assertEquals("saetrrilirunthu thookkinaar", TamilScriptConverter.convert("சேற்றிலிருந்து தூக்கினார்"));
    }

    @Test
    public void testConvertFile() throws IOException
    {
        File source = new File("src/test/resources/Urugaayoa Nenjamae Nee.txt");
        File target = new File("target/Urugaayoa Nenjamae Nee.txt");
        TamilScriptConverter.convertFile(source, target);
        assertTrue(target.exists());
        System.out.println(new String(Files.readAllBytes(Paths.get(target.toURI()))));
    }

    @Test
    public void testGetTargetFile()
    {
        File source = new File("target/Urugaayoa Nenjamae Nee.txt");
        File expected = new File(source.getParent() + File.separator + "converted", source.getName());
        assertEquals(expected, TamilScriptConverter.getTargetFile(source));
    }
}

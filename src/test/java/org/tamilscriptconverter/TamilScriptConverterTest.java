package org.tamilscriptconverter;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.*;

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
    public void testConvertChar()
    {
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
        assertEquals("s", TamilScriptConverter.convertChar("ச்"));
    }

    @Test
    public void testConvertCharWithVowelSign()
    {
        //vowel sign aa
        assertEquals("paa", TamilScriptConverter.convertCharWithVowelSign("பா", " "));
        assertEquals("maa", TamilScriptConverter.convertCharWithVowelSign("மா", " "));
        //vowel sign i
        assertEquals("si", TamilScriptConverter.convertCharWithVowelSign("சி", " "));
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
        assertEquals(Arrays.asList("இ", "யே", "சு"), TamilScriptConverter.splitUnicodeChars("இயேசு"));
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
    public void testConvertWordsStartingWith_அ()
    {

        assertEquals("appaa", TamilScriptConverter.convert("அப்பா"));
        assertEquals("appam", TamilScriptConverter.convert("அப்பம்"));
        assertEquals("annan", TamilScriptConverter.convert("அண்ணன்"));
        assertEquals("akkaa", TamilScriptConverter.convert("அக்கா"));
        assertEquals("ammaa ingkae vaa vaa", TamilScriptConverter.convert("அம்மா இங்கே வா வா"));
        assertEquals("anbu kooruvaen innum athikamaay", TamilScriptConverter.convert("அன்பு கூருவேன் இன்னும் அதிகமாய்"));
    }

    @Test
    public void testConvertWordsStartingWith_ஆ()
    {
        assertEquals("aappam", TamilScriptConverter.convert("ஆப்பம்"));
        assertEquals("aamaam", TamilScriptConverter.convert("ஆமாம்"));
        assertEquals("aandavar pataiththa vettriyin naalithu", TamilScriptConverter.convert("ஆண்டவர் படைத்த வெற்றியின் நாளிது"));
    }

    @Test
    public void testConvertWordsStartingWith_இ()
    {
        assertEquals("iyaesu kiristhuvae aandavar", TamilScriptConverter.convert("இயேசு கிறிஸ்துவே ஆண்டவர்"));
        assertEquals("iraththam jeyam", TamilScriptConverter.convert("இரத்தம் ஜெயம்"));
    }

    @Test
    public void testConvertWordsStartingWith_ஈ()
    {
        assertEquals("eenthaar", TamilScriptConverter.convert("ஈந்தார்"));
        assertEquals("eesal", TamilScriptConverter.convert("ஈசல்"));
        assertEquals("eetti", TamilScriptConverter.convert("ஈட்டி"));
    }

    @Test
    public void testConvertWordsStartingWith_உ()
    {
        //assertEquals("ulagu", TamilScriptConverter.convert("உலகு"));
    }

    @Test
    public void testConvertWordsStartingWith_ஐ()
    {
        assertEquals("aiyam", TamilScriptConverter.convert("ஐயம்"));
    }

    @Test
    public void testConvertWordsStartingWith_ஒ()
    {
        assertEquals("ondru", TamilScriptConverter.convert("ஒன்று"));
        assertEquals("ottrumai", TamilScriptConverter.convert("ஒற்றுமை"));
    }

    @Test
    public void testConvertWordsStartingWith_ப()
    {
        assertEquals("payam", TamilScriptConverter.convert("பயம்"));
    }

    @Test
    public void testConvertWordsStartingWith_ம()
    {
        assertEquals("meettaar", TamilScriptConverter.convert("மீட்டார்"));
        assertEquals("meetpu", TamilScriptConverter.convert("மீட்பு"));
        assertEquals("muthala", TamilScriptConverter.convert("முதல"));
        assertEquals("moochchu", TamilScriptConverter.convert("மூச்சு"));
        assertEquals("moondru", TamilScriptConverter.convert("மூன்று"));
    }

    @Test
    public void testConvertWordsStartingWith_ச()
    {
        assertEquals("sinnavan", TamilScriptConverter.convert("சின்னவன்"));
        assertEquals("seendal", TamilScriptConverter.convert("சீண்டல்"));
        assertEquals("sundal", TamilScriptConverter.convert("சுண்டல்"));
        assertEquals("saettrilirunthu thookkinaar", TamilScriptConverter.convert("சேற்றிலிருந்து தூக்கினார்"));
    }

    @Test
    public void testConvertSpecialSoundWord_Ka_Family()
    {
        //ka
        assertEquals("pugazh", TamilScriptConverter.convert("புகழ்"));
        assertEquals("magan", TamilScriptConverter.convert("மகன்"));
        assertEquals("thaganabali", TamilScriptConverter.convert("தகனபலி"));
        assertEquals("agamagizhnthu", TamilScriptConverter.convert("அகமகிழ்ந்து"));
        assertEquals("aegaparan", TamilScriptConverter.convert("ஏகபரன்"));
        assertEquals("vaazhga", TamilScriptConverter.convert("வாழ்க"));
        assertEquals("ulagam", TamilScriptConverter.convert("உலகம்"));
        assertEquals("enggal", TamilScriptConverter.convert("எங்கள்"));
        assertEquals("thaagam", TamilScriptConverter.convert("தாகம்"));
        assertEquals("kaalgal", TamilScriptConverter.convert("கால்கள்"));

        //kaa
        assertEquals("urugaayoa", TamilScriptConverter.convert("உருகாயோ"));

        //ki
        assertEquals("thongginaar", TamilScriptConverter.convert("தொங்கினார்"));
        assertEquals("thaanggidavoa", TamilScriptConverter.convert("தாங்கிடவோ"));

        //ku
        assertEquals("mangguthae", TamilScriptConverter.convert("மங்குதே"));
        assertEquals("vegu", TamilScriptConverter.convert("வெகு"));
        assertEquals("ulagu", TamilScriptConverter.convert("உலகு"));

        //koa
        assertEquals("senggoal", TamilScriptConverter.convert("செங்கோல்"));

        //kai
        assertEquals("ulagai", TamilScriptConverter.convert("உலகை"));
    }

    @Test
    public void testConvertSpecialSoundWord()
    {
        assertEquals("kaatchi", TamilScriptConverter.convert("காட்சி"));
        assertEquals("nenjam", TamilScriptConverter.convert("நெஞ்சம்"));
        assertEquals("inji", TamilScriptConverter.convert("இஞ்சி"));
        assertEquals("panju", TamilScriptConverter.convert("பஞ்சு"));

        assertEquals("kurai", TamilScriptConverter.convert("குறை"));
        assertEquals("kuttram", TamilScriptConverter.convert("குற்றம்"));
        assertEquals("vettri", TamilScriptConverter.convert("வெற்றி"));
        assertEquals("tharkaaththu", TamilScriptConverter.convert("தற்காத்து"));

        assertEquals("gnaanam", TamilScriptConverter.convert("ஞானம்"));
        assertEquals("gnaayiru", TamilScriptConverter.convert("ஞாயிறு"));

        assertEquals("irakkam", TamilScriptConverter.convert("இரக்கம்"));

        assertEquals("paadiduvoam", TamilScriptConverter.convert("பாடிடுவோம்"));
        assertEquals("manudaril", TamilScriptConverter.convert("மனுடரில்"));
        assertEquals("varudam", TamilScriptConverter.convert("வருடம்"));
        assertEquals("naavarandu", TamilScriptConverter.convert("நாவறண்டு"));

        assertEquals("anbu", TamilScriptConverter.convert("அன்பு"));
        assertEquals("maanbu", TamilScriptConverter.convert("மாண்பு"));
        assertEquals("kirubai", TamilScriptConverter.convert("கிருபை"));
        assertEquals("sabai", TamilScriptConverter.convert("சபை"));
        assertEquals("inbam", TamilScriptConverter.convert("இன்பம்"));
        assertEquals("thunbam", TamilScriptConverter.convert("துன்பம்"));

        assertEquals("androa", TamilScriptConverter.convert("அன்றோ"));
        assertEquals("moondraani", TamilScriptConverter.convert("மூன்றாணி"));
        assertEquals("nandru", TamilScriptConverter.convert("நன்று"));
        assertEquals("nandri", TamilScriptConverter.convert("நன்றி"));
    }

    @Test
    public void testConvertSpecialSoundChar()
    {
        assertEquals("ga", TamilScriptConverter.convertSpecialSoundChar("க", "ஏ", null));
        assertEquals("ga", TamilScriptConverter.convertSpecialSoundChar("க", "ஏ", "ப"));
        assertEquals("gu", TamilScriptConverter.convertSpecialSoundChar("கு", "ங்", null));

        assertEquals("a", TamilScriptConverter.convertSpecialSoundChar("ச", "ஞ்", null));
        assertEquals("i", TamilScriptConverter.convertSpecialSoundChar("சி", "ஞ்", null));
        assertEquals("chi", TamilScriptConverter.convertSpecialSoundChar("சி", "ட்", null));
        assertEquals("u", TamilScriptConverter.convertSpecialSoundChar("சு", "ஞ்", null));

        assertEquals("bu", TamilScriptConverter.convertSpecialSoundChar("பு", "ண்", null));
        assertEquals("bu", TamilScriptConverter.convertSpecialSoundChar("பு", "ன்", null));

        assertEquals("droa", TamilScriptConverter.convertSpecialSoundChar("றோ", "ன்", null));
    }

    @Test
    public void testConvertFile() throws IOException
    {
        File source = new File("src/test/resources/Urugaayoa nenjamae nee.txt");
        File target = new File("target/Urugaayoa Nenjamae Nee.txt");
        TamilScriptConverter.convertFile(source, target);
        assertTrue(target.exists());
        assertEquals(getExpected(), new String(Files.readAllBytes(Paths.get(target.toURI()))));
    }

    @Test
    public void testGetTargetFile()
    {
        File source = new File("target/Urugaayoa Nenjamae Nee.txt");
        File expected = new File(source.getParent() + File.separator + "converted", source.getName());
        assertEquals(expected, TamilScriptConverter.getTargetFile(source));
    }

    private String getExpected()
    {
        return "1. உருகாயோ நெஞ்சமே\r\n" +
                "1. urugaayoa nenjamae\r\n" +
                "குருசினில் அந்தோ பார்!\r\n" +
                "kurusinil anthoa paar!\r\n" +
                "கரங் கால்கள் ஆணி யேறித்\r\n" +
                "karang kaalgal aani yaerith\r\n" +
                "திருமேனி நையுதே!\r\n" +
                "thirumaeni naiyuthae!\r\n" +
                "\r\n" +
                "\r\n" +
                "2. மன்னுயிர்க்காய்த் தன்னுயிரை\r\n" +
                "2. mannuyirkkaayth thannuyirai\r\n" +
                "மாய்க்க வந்த மன்னவர்தாம் ,\r\n" +
                "maaykka vantha mannavarthaam ,\r\n" +
                "இந்நிலமெல் லாம் புரக்க\r\n" +
                "innilamel laam purakka\r\n" +
                "ஈன குரு சேறினார்.\r\n" +
                "eena kuru saerinaar.\r\n" +
                "\r\n" +
                "\r\n" +
                "3. தாக மிஞ்சி நாவறண்டு\r\n" +
                "3. thaaga minji naavarandu\r\n" +
                "தங்க மேனி மங்குதே ,\r\n" +
                "thangga maeni mangguthae ,\r\n" +
                "ஏகபரன் கண்ணயர்ந்து \r\n" +
                "aegaparan kannayarnthu \r\n" +
                "எத்தனையாய் ஏங்குறார்.\r\n" +
                "eththanaiyaay aengguraar.\r\n" +
                "\r\n" +
                "\r\n" +
                "4. மூவுலகைத் தாங்கும் தேவன்\r\n" +
                "4. moovulagaith thaanggum thaevan\r\n" +
                "மூன்றாணி தாங்கிடவோ?\r\n" +
                "moondraani thaanggidavoa?\r\n" +
                "சாவு வேளை வந்த போது\r\n" +
                "saavu vaelai vantha poathu\r\n" +
                "சிலுவையில் தொங்கினார்.\r\n" +
                "siluvaiyil thongginaar.\r\n" +
                "\r\n" +
                "\r\n" +
                "5. வல்ல பேயை வெல்ல வானம்\r\n" +
                "5. valla paeyai vella vaanam\r\n" +
                "விட்டு வந்த தெய்வம் பாராய்\r\n" +
                "vittu vantha theyvam paaraay\r\n" +
                "புல்லர் இதோ நன்றி கெட்டுப் \r\n" +
                "pullar ithoa nandri kettup \r\n" +
                "புறம் பாக்கி னார் அன்றோ?\r\n" +
                "puram paakki naar androa?\r\n";
    }
}

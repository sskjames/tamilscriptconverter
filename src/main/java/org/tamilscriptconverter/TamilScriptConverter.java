package org.tamilscriptconverter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author James Selvakumar
 * @since 1.0
 */
public class TamilScriptConverter
{
    public static final char VOWEL_SIGN_AA = '\u0bbe';
    public static final char VOWEL_SIGN_I = '\u0bbf';
    public static final char VOWEL_SIGN_II = '\u0bc0';
    public static final char VOWEL_SIGN_U = '\u0bc1';
    public static final char VOWEL_SIGN_UU = '\u0bc2';
    public static final char VOWEL_SIGN_EE = '\u0bc7';
    public static final char VOWEL_SIGN_E = '\u0bc6';
    public static final char VOWEL_SIGN_AI = '\u0bc8';
    public static final char VOWEL_SIGN_O = '\u0bca';
    public static final char VOWEL_SIGN_OO = '\u0bcb';
    public static final char VOWEL_SIGN_AU = '\u0bcc';
    public static final char PULLI = '\u0bcd';
    public static final List<Character> VOWEL_SIGNS = Arrays.asList(VOWEL_SIGN_AA, VOWEL_SIGN_I, VOWEL_SIGN_II,
            VOWEL_SIGN_U, VOWEL_SIGN_UU, VOWEL_SIGN_E, VOWEL_SIGN_EE, VOWEL_SIGN_AI, VOWEL_SIGN_O, VOWEL_SIGN_OO,
            VOWEL_SIGN_AU);
    private static Logger logger = LoggerFactory.getLogger(TamilScriptConverter.class);
    private static Map<String, String> charMap = new HashMap<String, String>();
    private static Set<SpecialSoundChar> specialSoundChars = new HashSet<>();

    static {
        //uyir
        charMap.put("அ", "a");
        charMap.put("ஆ", "aa");
        charMap.put("இ", "i");
        charMap.put("ஈ", "ee");
        charMap.put("உ", "u");
        charMap.put("ஊ", "oo");
        charMap.put("எ", "e");
        charMap.put("ஏ", "ae");
        charMap.put("ஐ", "ai");
        charMap.put("ஒ", "o");
        charMap.put("ஓ", "oa");
        //mei
        charMap.put("க்", "k");
        charMap.put("ங்", "ng");
        charMap.put("ச்", "s");
        charMap.put("ஜ்", "j");
        charMap.put("ஞ்", "nj");
        charMap.put("ட்", "t");
        charMap.put("ட்'", "d");
        charMap.put("ண்", "n");
        charMap.put("த்", "th");
        charMap.put("ந்", "n");
        charMap.put("ன்", "n");
        charMap.put("ப்", "p");
        charMap.put("ம்", "m");
        charMap.put("ய்", "y");
        charMap.put("ர்", "r");
        charMap.put("ற்", "r");
        charMap.put("ல்", "l");
        charMap.put("ள்", "l");
        charMap.put("ழ்", "zh");
        charMap.put("வ்", "v");
        charMap.put("ஷ்", "sh");
        charMap.put("ஸ்", "s");

        //uyirmei
        charMap.put("க", "ga");
        charMap.put("ங", "nga");
        charMap.put("ச", "sa");
        charMap.put("ஜ", "ja");
        charMap.put("ஞ", "nya");
        charMap.put("ட", "da");
        charMap.put("ண", "na");
        charMap.put("த", "tha");
        charMap.put("ந", "na");
        charMap.put("ன", "na");
        charMap.put("ப", "pa");
        charMap.put("ம", "ma");
        charMap.put("ய", "ya");
        charMap.put("ர", "ra");
        charMap.put("ற", "ra");
        charMap.put("ல", "la");
        charMap.put("ள", "la");
        charMap.put("ழ", "zha");
        charMap.put("வ", "va");
        charMap.put("ஷ", "sha");
        charMap.put("ஸ", "sa");

        specialSoundChars.add(new SpecialSoundChar("க", null, ".", "ka"));
        specialSoundChars.add(new SpecialSoundChar("கா", null, ".", "kaa"));
        specialSoundChars.add(new SpecialSoundChar("கா", ".", "த்", "kaa"));

        specialSoundChars.add(new SpecialSoundChar("க", "க்|ட்", ".", "ka"));
        specialSoundChars.add(new SpecialSoundChar("கா", "க்|ட்", ".", "kaa"));

        specialSoundChars.add(new SpecialSoundChar("கு", "ங்", ".", "u"));
        specialSoundChars.add(new SpecialSoundChar("க்", "ல", ".", "g"));

        specialSoundChars.add(new SpecialSoundChar("ச", "ஞ்", ".", "a"));
        specialSoundChars.add(new SpecialSoundChar("சி", "ஞ்", ".", "i"));
        specialSoundChars.add(new SpecialSoundChar("சி", "ட்", ".", "chi"));
        specialSoundChars.add(new SpecialSoundChar("சு", "ஞ்", ".", "u"));
        specialSoundChars.add(new SpecialSoundChar("து", "த்", ".", "u"));

        specialSoundChars.add(new SpecialSoundChar("ச்", ".", "சு", "ch"));
        specialSoundChars.add(new SpecialSoundChar("ற்", ".", "றி|று", "tr"));
        specialSoundChars.add(new SpecialSoundChar("சு", "ச்", ".", "chu"));

        specialSoundChars.add(new SpecialSoundChar("டா", "ட்", ".", "taa"));
        specialSoundChars.add(new SpecialSoundChar("ட்", "ட்", ".", "t"));

        specialSoundChars.add(new SpecialSoundChar("ப", "ண்|ன்", "ம்", "ba"));
        specialSoundChars.add(new SpecialSoundChar("ப்", "ண்|ன்", ".", "b"));

        specialSoundChars.add(new SpecialSoundChar("றோ", "ன்", ".", "droa"));
    }

    public static void convertFiles(File source) throws IOException
    {
        if (source.exists()) {
            if (source.isFile()) {
                convertFile(source);
            } else {
                File[] files = source.listFiles();
                logger.info("Preparing to convert the files in {}", files.length, source);
                for (File file : files) {
                    convertFile(file);
                }
                logger.info("Finished converting the files!", files.length);
            }
        } else {
            logger.error("File {} doesn't exist!", source);
        }
    }

    static void convertFile(File source) throws IOException
    {
        convertFile(source, getTargetFile(source));
    }

    static void convertFile(File source, File target) throws IOException
    {
        if (source != null && source.exists()) {
            if (!target.getParentFile().exists()) {
                target.getParentFile().mkdirs();
            }
            logger.info("Preparing to convert Tamil script in the source {} to {}...", source.getName(), target);
            BufferedReader reader = null;
            BufferedWriter writer = null;
            try {
                reader = new BufferedReader(new FileReader(source));
                writer = new BufferedWriter(new FileWriter(target));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    writer.write(line + "\r\n");
                    writer.write(convert(line) + "\r\n");
                }
                logger.info("Finished converting {}", source);
            } catch (IOException ex) {
                logger.error("Error occurred while reading source file", ex);
            } finally {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            }
        } else {
            logger.error("File {} doesn't exist!", source);
        }
    }

    public static String convert(String text)
    {
        StringBuilder convertedWord = new StringBuilder();
        List<String> unicodeChars = splitUnicodeChars(text);
        for (int i = 0; i < unicodeChars.size(); i++) {
            String unicodeChar = unicodeChars.get(i);
            logger.debug("Unicode char: {}", unicodeChar);
            final String previousChar = i > 0 ? unicodeChars.get(i - 1) : "  ";
            final String nextChar = i < unicodeChars.size() - 1 ? unicodeChars.get(i + 1) : " ";
            String convertedSpecialSoundCharValue = convertSpecialSoundChar(unicodeChar, previousChar, nextChar);
            if (convertedSpecialSoundCharValue != null) {
                convertedWord.append(convertedSpecialSoundCharValue);
            } else if (endsWithVowelSign(unicodeChar)) {
                convertedWord.append(convertCharWithVowelSign(unicodeChar, previousChar));
            } else {
                convertedWord.append(convertChar(unicodeChar));
            }
        }
        return convertedWord.toString();
    }

    public static List<String> splitUnicodeChars(String input)
    {
        logger.debug("Input string: {}", input);
        List<String> unicodeChars = new ArrayList<String>();
        char[] chars = input.toCharArray();
        logger.info("Chars: {}", chars);
        for (int i = 0; i < chars.length; i++) {
            logger.debug("Preparing to add the char: {}", chars[i]);
            if (isSignAfterChar(chars[i])) {
                int lastAddedCharIndex = unicodeChars.size() - 1;
                String lastAddedChar = unicodeChars.get(lastAddedCharIndex);
                unicodeChars.remove(lastAddedCharIndex);
                unicodeChars.add(lastAddedChar + "" + chars[i]);
            } else {
                unicodeChars.add(chars[i] + "");
            }
        }
        logger.debug("Unicode chars: {}", unicodeChars);
        return trimUnicodeChars(unicodeChars);
    }

    static String convertFirstPartInUyirMeiChar(String charToBeConverted)
    {
        logger.debug("Converting the tamil char: {}", charToBeConverted);
        String convertedString = charMap.get(charToBeConverted + "'");
        return convertedString != null ? convertedString : convertChar(charToBeConverted);
    }

    static String convertChar(String charToBeConverted)
    {
        logger.debug("Converting the tamil char: {}", charToBeConverted);
        String convertedString = charMap.get(charToBeConverted);
        return convertedString != null ? convertedString : charToBeConverted;
    }

    static String convertSpecialSoundChar(String charToBeConverted, String previousChar, String nextChar)
    {
        SpecialSoundChar matchedSpecialSoundChar = specialSoundChars.stream()
                .filter(specialSoundChar -> matchesSpecialSoundChar(specialSoundChar, charToBeConverted, previousChar, nextChar))
                .findFirst().orElse(null);
        logger.debug("Matched special sound char: {}", matchedSpecialSoundChar);
        return matchedSpecialSoundChar != null ? matchedSpecialSoundChar.getValueChar() : null;
    }

    private static boolean matchesSpecialSoundChar(SpecialSoundChar specialSoundChar,
                                                   String charToBeConverted, String previousChar, String nextChar)
    {
        String previousCharToBeMatched = specialSoundChar.getPreviousChar();
        String nextCharToBeMatched = specialSoundChar.getNextChar();
        if (specialSoundChar.getKeyChar().equals(charToBeConverted)) {
            return matchesChar(previousChar, previousCharToBeMatched) && matchesChar(nextChar, nextCharToBeMatched);
        }
        return false;
    }

    static boolean matchesChar(String string, String charToBeMatched)
    {
        if (StringUtils.isBlank(string) && StringUtils.isBlank(charToBeMatched)) {
            return true;
        } else if (string != null && charToBeMatched != null) {
            return matches(string, charToBeMatched);
        }
        return false;
    }

    static String convertCharWithVowelSign(String unicodeChar, String previousChar)
    {
        if (unicodeChar.length() > 1) {
            logger.debug("Unicode char: {}, previous char: {}", unicodeChar, previousChar);
            char[] chars = unicodeChar.toCharArray();
            char[] previousChars = previousChar.toCharArray();
            char firstCharPart = chars[0];
            char secondCharPart = chars[1];
            logger.debug("First char part: {}, second char part: {}", firstCharPart, secondCharPart);
            switch (secondCharPart) {
                case VOWEL_SIGN_AA:
                    return convertFirstPartInUyirMeiChar(firstCharPart + "") + "a";
                case VOWEL_SIGN_I:
                    if (firstCharPart == 'ற') {
                        return "ri";
                    }
                    return convertFirstPartInUyirMeiChar(firstCharPart + "" + PULLI, previousChar) + "i";
                case VOWEL_SIGN_II:
                    return convertFirstPartInUyirMeiChar(firstCharPart + "" + PULLI) + "ee";
                case VOWEL_SIGN_U:
                    if (previousChars[0] != 'ச' && firstCharPart == 'ச') {
                        return "su";
                    } else if (firstCharPart == 'ற') {
                        return "ru";
                    }
                    return convertFirstPartInUyirMeiChar(firstCharPart + "" + PULLI, previousChar) + "u";
                case VOWEL_SIGN_UU:
                    return convertFirstPartInUyirMeiChar(firstCharPart + "" + PULLI) + "oo";
                case VOWEL_SIGN_E:
                    return convertFirstPartInUyirMeiChar(firstCharPart + "" + PULLI) + "e";
                case VOWEL_SIGN_EE:
                    logger.debug("Character has vowel sign {}", VOWEL_SIGN_EE);
                    if (StringUtils.isBlank(previousChar) && firstCharPart == 'ச') {
                        return "sae";
                    }
                    return convertFirstPartInUyirMeiChar(firstCharPart + "" + PULLI, previousChar) + "ae";
                case VOWEL_SIGN_AI:
                    return convertFirstPartInUyirMeiChar(firstCharPart + "" + PULLI, previousChar) + "ai";
                case VOWEL_SIGN_O:
                    return convertFirstPartInUyirMeiChar(firstCharPart + "" + PULLI) + "o";
                case VOWEL_SIGN_OO:
                    return convertFirstPartInUyirMeiChar(firstCharPart + "" + PULLI, previousChar) + "oa";
            }
        }
        return unicodeChar;
    }

    private static String convertFirstPartInUyirMeiChar(String charToBeConverted, String previousChar)
    {
        String specialSoundChar = convertSpecialSoundChar(charToBeConverted, previousChar, ".");
        return specialSoundChar != null ? specialSoundChar : convertFirstPartInUyirMeiChar(charToBeConverted);
    }

    static boolean isSignAfterChar(char tamilChar)
    {
        return PULLI == tamilChar || VOWEL_SIGNS.contains(tamilChar);
    }

    static boolean isVowelSign(char tamilChar)
    {
        return VOWEL_SIGNS.contains(tamilChar);
    }

    static boolean endsWithVowelSign(String unicodeChar)
    {
        if (unicodeChar.length() > 1) {
            return isVowelSign(unicodeChar.toCharArray()[unicodeChar.length() - 1]);
        }
        return false;
    }

    public static List<String> trimUnicodeChars(List<String> unicodeChars)
    {
        if (unicodeChars.size() > 2) {
            logger.debug("Preparing to trim unicode chars...");
            logger.debug("Char at index 0: {}, char at index 1: {}", unicodeChars.get(0), unicodeChars.get(1));
            if (unicodeChars.get(0).equals("இ")) {
                String secondChar = unicodeChars.get(1);
                switch (secondChar) {
                    case "யே":
                    case "ர":
                        unicodeChars.remove(0);
                }
            } else {
                logger.debug("Nothing to trim");
            }
        }
        return unicodeChars;
    }

    public static File getTargetFile(File source)
    {
        return new File(source.getParent() + File.separator + "converted", source.getName());
    }

    public static boolean matches(String text, String regex)
    {
        return getMatcher(text, regex).find();
    }

    private static Matcher getMatcher(String text, String regex)
    {
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        return pattern.matcher(text);
    }
}
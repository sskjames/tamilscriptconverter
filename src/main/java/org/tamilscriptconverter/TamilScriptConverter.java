package org.tamilscriptconverter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.acl.LastOwnerException;
import java.util.*;

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
        charMap.put("க", "ka");
        charMap.put("ங", "nga");
        charMap.put("ச", "sa");
        charMap.put("ஞ", "gna");
        charMap.put("ட", "ta");
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

        specialSoundChars.add(new SpecialSoundChar("க", "ஏ", null, "ga"));
        specialSoundChars.add(new SpecialSoundChar("க", "அ", null, "ga"));
        specialSoundChars.add(new SpecialSoundChar("க", "பு", null, "ga"));
        specialSoundChars.add(new SpecialSoundChar("க", "ம", null, "ga"));
        specialSoundChars.add(new SpecialSoundChar("க", "த", null, "ga"));
        specialSoundChars.add(new SpecialSoundChar("க", "தா", null, "ga"));
        specialSoundChars.add(new SpecialSoundChar("க", "ங்", null, "ga"));
        specialSoundChars.add(new SpecialSoundChar("க", "ல்", null, "ga"));
        specialSoundChars.add(new SpecialSoundChar("க", "ல", null, "ga"));
        specialSoundChars.add(new SpecialSoundChar("க", "ழ்", null, "ga"));

        specialSoundChars.add(new SpecialSoundChar("கா", "ரு", null, "gaa"));

        specialSoundChars.add(new SpecialSoundChar("கி", "ம", null, "gi"));
        specialSoundChars.add(new SpecialSoundChar("கி", "ங்", null, "gi"));

        specialSoundChars.add(new SpecialSoundChar("கு", "ங்", null, "gu"));
        specialSoundChars.add(new SpecialSoundChar("கு", "வெ", null, "gu"));
        specialSoundChars.add(new SpecialSoundChar("கு", "ல", null, "gu"));

        specialSoundChars.add(new SpecialSoundChar("கோ", "ங்", null, "goa"));
        specialSoundChars.add(new SpecialSoundChar("கை", "ல", null, "gai"));

        specialSoundChars.add(new SpecialSoundChar("ச", "ஞ்", null, "a"));
        specialSoundChars.add(new SpecialSoundChar("சி", "ஞ்", null, "i"));
        specialSoundChars.add(new SpecialSoundChar("சி", "ட்", null, "chi"));
        specialSoundChars.add(new SpecialSoundChar("சு", "ஞ்", null, "u"));

        specialSoundChars.add(new SpecialSoundChar("ட", "னு", null, "da"));
        specialSoundChars.add(new SpecialSoundChar("ட", "ரு", null, "da"));
        specialSoundChars.add(new SpecialSoundChar("டி", "பா", null, "di"));
        specialSoundChars.add(new SpecialSoundChar("டு", "டி", null, "du"));
        specialSoundChars.add(new SpecialSoundChar("டு", "ண்", null, "du"));

        specialSoundChars.add(new SpecialSoundChar("ச்", null, "சு", "ch"));
        specialSoundChars.add(new SpecialSoundChar("சு", "ச்", null, "chu"));

        specialSoundChars.add(new SpecialSoundChar("ட", "ண்", null, "da"));
        specialSoundChars.add(new SpecialSoundChar("ட", "கி", null, "da"));

        specialSoundChars.add(new SpecialSoundChar("ப", "ன", null, "ba"));
        specialSoundChars.add(new SpecialSoundChar("ப", "ன்", null, "ba"));
        specialSoundChars.add(new SpecialSoundChar("பு", "ண்", null, "bu"));
        specialSoundChars.add(new SpecialSoundChar("பு", "ன்", null, "bu"));
        specialSoundChars.add(new SpecialSoundChar("பை", "ரு", null, "bai"));
        specialSoundChars.add(new SpecialSoundChar("பை", "ச", null, "bai"));

        specialSoundChars.add(new SpecialSoundChar("ற்", null, "ற", "t"));
        specialSoundChars.add(new SpecialSoundChar("ற்", null, "றி", "t"));
        specialSoundChars.add(new SpecialSoundChar("ற்", null, "று", "t"));
        specialSoundChars.add(new SpecialSoundChar("ற", "ற்", null, "tra"));
        specialSoundChars.add(new SpecialSoundChar("றா", "ன்", null, "draa"));
        specialSoundChars.add(new SpecialSoundChar("றி", "ற்", null, "tri"));
        specialSoundChars.add(new SpecialSoundChar("றி", "ன்", null, "dri"));
        specialSoundChars.add(new SpecialSoundChar("று", "ற்", null, "tru"));
        specialSoundChars.add(new SpecialSoundChar("று", "ன்", null, "dru"));
        specialSoundChars.add(new SpecialSoundChar("றோ", "ன்", null, "droa"));
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
        logger.debug("Converting the text: {}", text);
        StringBuilder convertedWord = new StringBuilder();
        List<String> unicodeChars = splitUnicodeChars(text);
        for (int i = 0; i < unicodeChars.size(); i++) {
            String unicodeChar = unicodeChars.get(i);
            logger.trace("Unicode char: {}", unicodeChar);
            final String previousChar = i > 0 ? unicodeChars.get(i - 1) : "  ";
            final String nextChar = i < unicodeChars.size() - 1 ? unicodeChars.get(i + 1) : " ";
            String convertedSpecialSoundCharValue = convertSpecialSoundChar(unicodeChar, previousChar, nextChar);
            logger.trace("Converted special sound char value: {}", convertedSpecialSoundCharValue);
            if (convertedSpecialSoundCharValue != null) {
                convertedWord.append(convertedSpecialSoundCharValue);
            } else if (endsWithVowelSign(unicodeChar)) {
                convertedWord.append(convertCharWithVowelSign(unicodeChar, previousChar));
            } else {
                convertedWord.append(convertChar(unicodeChar));
            }
        }
        String convertedText = convertedWord.toString();
        logger.debug("Converted text: {}\n", convertedText);
        return convertedText;
    }

    public static List<String> splitUnicodeChars(String input)
    {
        logger.trace("Input string: {}", input);
        List<String> unicodeChars = new ArrayList<String>();
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int nextCharIndex = i + 1;
            if (nextCharIndex < chars.length) {
                logger.trace("Preparing to add the char: {}", chars[i]);
                if (isSignAfterChar(chars[nextCharIndex])) {
                    unicodeChars.add(chars[i] + "" + chars[nextCharIndex]);
                } else if (!isSignAfterChar(chars[i])) {
                    unicodeChars.add(chars[i] + "");
                }
            } else if (!isSignAfterChar(chars[i])) {
                unicodeChars.add(chars[i] + "");
            }
        }
        logger.trace("Unicode chars: {}", unicodeChars);
        return unicodeChars;
    }

    static String convertChar(String charToBeConverted)
    {
        logger.trace("Converting the tamil char: {}", charToBeConverted);
        String convertedString = charMap.get(charToBeConverted);
        return convertedString != null ? convertedString : charToBeConverted;
    }

    static String convertSpecialSoundChar(String charToBeConverted, String previousChar, String nextChar)
    {
        SpecialSoundChar matchedSpecialSoundChar = specialSoundChars.stream()
                .filter(specialSoundChar -> matchesSpecialSoundChar(specialSoundChar, charToBeConverted, previousChar, nextChar))
                .findFirst().orElse(null);
        if (matchedSpecialSoundChar == null) {
            matchedSpecialSoundChar = specialSoundChars.stream()
                    .filter(specialSoundChar -> matchesOnlyPreviousChar(specialSoundChar, charToBeConverted, previousChar)
                            || matchesOnlyNextChar(specialSoundChar, charToBeConverted, nextChar))
                    .findFirst().orElse(null);
        }
        logger.trace("Matched special sound char: {}", matchedSpecialSoundChar);
        return matchedSpecialSoundChar != null ? matchedSpecialSoundChar.getValueChar() : null;
    }

    private static boolean matchesSpecialSoundChar(SpecialSoundChar specialSoundChar,
                                                   String charToBeConverted, String previousChar, String nextChar)
    {
        String previousCharToBeMatched = specialSoundChar.getPreviousChar();
        String nextCharToBeMatched = specialSoundChar.getNextChar();
        if (specialSoundChar.getKeyChar().equals(charToBeConverted)
                && previousCharToBeMatched != null && nextCharToBeMatched != null) {
            return previousCharToBeMatched.equals(previousChar) && nextCharToBeMatched.equals(nextChar);
        }
        return false;
    }

    private static boolean matchesOnlyPreviousChar(SpecialSoundChar specialSoundChar,
                                                   String charToBeConverted, String previousChar)
    {
        if (specialSoundChar.getKeyChar().equals(charToBeConverted) && specialSoundChar.getPreviousChar() != null) {
            return specialSoundChar.getPreviousChar().equals(previousChar);
        }
        return false;
    }

    private static boolean matchesOnlyNextChar(SpecialSoundChar specialSoundChar,
                                               String charToBeConverted, String nextChar)
    {
        if (specialSoundChar.getKeyChar().equals(charToBeConverted) && specialSoundChar.getNextChar() != null) {
            return specialSoundChar.getNextChar().equals(nextChar);
        }
        return false;
    }

    static String convertCharWithVowelSign(String unicodeChar, String previousChar)
    {
        if (unicodeChar.length() > 1) {
            logger.trace("Unicode char: {}, previous char: {}", unicodeChar, previousChar);
            char[] chars = unicodeChar.toCharArray();
            char[] previousChars = previousChar.toCharArray();
            char firstCharPart = chars[0];
            char secondCharPart = chars[1];
            logger.trace("First char part: {}, second char part: {}", firstCharPart, secondCharPart);
            switch (secondCharPart) {
                case VOWEL_SIGN_AA:
                    return convertChar(firstCharPart + "") + "a";
                case VOWEL_SIGN_I:
                    if (firstCharPart == 'ற') {
                        return "ri";
                    }
                    return convertChar(firstCharPart + "" + PULLI) + "i";
                case VOWEL_SIGN_II:
                    return convertChar(firstCharPart + "" + PULLI) + "ee";
                case VOWEL_SIGN_U:
                    if (previousChars[0] != 'ச' && firstCharPart == 'ச') {
                        return "su";
                    } else if (firstCharPart == 'ற') {
                        return "ru";
                    }
                    return convertChar(firstCharPart + "" + PULLI) + "u";
                case VOWEL_SIGN_UU:
                    return convertChar(firstCharPart + "" + PULLI) + "oo";
                case VOWEL_SIGN_E:
                    return convertChar(firstCharPart + "" + PULLI) + "e";
                case VOWEL_SIGN_EE:
                    logger.trace("Character has vowel sign {}", VOWEL_SIGN_EE);
                    if (StringUtils.isBlank(previousChar) && firstCharPart == 'ச') {
                        return "sae";
                    }
                    return convertChar(firstCharPart + "" + PULLI) + "ae";
                case VOWEL_SIGN_AI:
                    return convertChar(firstCharPart + "" + PULLI) + "ai";
                case VOWEL_SIGN_O:
                    return convertChar(firstCharPart + "" + PULLI) + "o";
                case VOWEL_SIGN_OO:
                    return convertChar(firstCharPart + "" + PULLI) + "oa";
            }
        }
        return unicodeChar;
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
            logger.trace("Preparing to trim unicode chars...");
            logger.trace("Char at index 0: {}, char at index 1: {}", unicodeChars.get(0), unicodeChars.get(1));
            if (unicodeChars.get(0).equals("இ")) {
                String secondChar = unicodeChars.get(1);
                switch (secondChar) {
                    case "யே":
                    case "ர":
                        unicodeChars.remove(0);
                }
            } else {
                logger.trace("Nothing to trim");
            }
        }
        return unicodeChars;
    }

    public static File getTargetFile(File source)
    {
        return new File(source.getParent() + File.separator + "converted", source.getName());
    }
}
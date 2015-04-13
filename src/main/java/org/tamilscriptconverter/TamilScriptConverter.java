package org.tamilscriptconverter;

import java.util.*;

/**
 * @author James Selvakumar
 * @since 1.0
 */
public class TamilScriptConverter
{
    private static Map<String, String> charMap = new HashMap<String, String>();
    private static final char PULLI = '\u0bcd';
    private static final char VOWEL_SIGN_AA = '\u0bbe';
    private static final List<Character> VOWEL_SIGNS_AFTER_CHAR = Arrays.asList(PULLI, VOWEL_SIGN_AA, '\u0bbf');

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
        charMap.put("ச்", "ch");
        charMap.put("ஜ", "j");
        charMap.put("ஞ்", "nj");
        charMap.put("ட்", "t");
        charMap.put("ண்", "n");
        charMap.put("த்", "th");
        charMap.put("ந்", "nth");
        charMap.put("ன்", "n");
        charMap.put("ப்", "p");
        charMap.put("ம்", "m");
        charMap.put("ய்", "y");
        charMap.put("ர்", "r");
        charMap.put("ற", "r");
        charMap.put("ல", "l");
        charMap.put("ள", "l");
        charMap.put("ழ்", "zh");
        charMap.put("வ்", "v");
        charMap.put("ஷ்", "sh");
        charMap.put("ஸ்", "s");

        //uyirmei
        charMap.put("க", "ka");
        charMap.put("ங", "nga");
        charMap.put("ச", "sa");
        charMap.put("ஞ", "nya");
        charMap.put("ட", "ta");
        charMap.put("ண", "na");
        charMap.put("த", "tha");
        charMap.put("ந", "na");
        charMap.put("ன", "na");
        charMap.put("ப", "pa");
        charMap.put("ம", "ma" );
        charMap.put("ய", "ya");
        charMap.put("ர", "ra");
        charMap.put("ற", "ra");
        charMap.put("ல", "la");
        charMap.put("ள", "la");
        charMap.put("ழ", "zha");
        charMap.put("வ", "va");
        charMap.put("ஷ", "sha");
        charMap.put("ஸ", "sa");
    }

    public static String convertChar(String tamilChar)
    {
        String convertedString = charMap.get(tamilChar);
        return convertedString != null ? convertedString : "";
    }

    public static String convertWord(String word)
    {
        StringBuilder convertedWord = new StringBuilder();
        List<String> unicodeChars = splitUnicodeChars(word);
        for (int i = 0; i < unicodeChars.size(); i++) {
            String unicodeChar = unicodeChars.get(i);
            System.out.println("Unicode char: " + unicodeChar);
            if(unicodeChar.contains(VOWEL_SIGN_AA + "")) {
                convertedWord.append(appendVowelSign(unicodeChar.toCharArray()[0]));
            } else {
                convertedWord.append(convertChar(unicodeChar));
            }
        }
        return convertedWord.toString();
    }

    public static List<String> splitUnicodeChars(String input)
    {
        System.out.println("Input string: " + input);
        List<String> unicodeChars = new ArrayList<String>();
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int nextCharIndex = i + 1;
            if(nextCharIndex < chars.length) {
                if(isVowelSignAfterChar(chars[nextCharIndex])) {
                    unicodeChars.add(chars[i] + "" + chars[nextCharIndex]);
                } else if(!isVowelSignAfterChar(chars[i])) {
                    unicodeChars.add(chars[i] + "");
                }
            }
        }
        return unicodeChars;
    }

    static boolean isVowelSignAfterChar(char tamilChar)
    {
        return VOWEL_SIGNS_AFTER_CHAR.contains(tamilChar);
    }

    static String appendVowelSign(char tamilChar) {
        return convertChar(tamilChar + "") + "a";
    }
}
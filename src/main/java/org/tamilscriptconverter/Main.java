package org.tamilscriptconverter;

import java.io.File;
import java.io.IOException;

/**
 * @author James Selvakumar
 * @since 1.0.0
 */
public class Main
{
    public static void main(String[] args)
    {
        if(args.length > 0) {
            try {
                TamilScriptConverter.convertFiles(new File(args[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Run the program again by specifying the name of the file or directory which you would like to convert");
            System.out.println("Example 1:");
            System.out.println("java -jar <jar-file> /foo/files-to-be-converted/file1.txt");
            System.out.println("");
            System.out.println("Example 2:");
            System.out.println("java -jar <jar-file> /foo/files-to-be-converted");
        }
    }
}

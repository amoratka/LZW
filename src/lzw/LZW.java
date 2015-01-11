/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lzw;

/**
 *
 * @author Kamila
 */
import java.io.FileNotFoundException;
import java.util.*;
import static lzw.FileInAndOut.readuncompressed;
import static lzw.FileInAndOut.writecompressed;
import static lzw.FileInAndOut.writedecompressed;
 
public class LZW {
    /** Compress a string to a list of output symbols. */
    public static List<Integer> compress(String uncompressed) throws FileNotFoundException {
        
// Build the dictionary.
        
        int dictSize = 256;
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        
        for (int i = 0; i < 256; i++)            
            dictionary.put("" + (char)i, i);       
        
        String w = "";
        List<Integer> result = new ArrayList<Integer>();
        
        for (char c : uncompressed.toCharArray()) {
            
            String wc = w + c;
            
            if (dictionary.containsKey(wc))
                w = wc;
            else {
                result.add(dictionary.get(w));
                // Add wc to the dictionary.
                dictionary.put(wc, dictSize++);
                w = "" + c;
            }
            
        }
 
        // Output the code for w.
        if (!w.equals(""))
            result.add(dictionary.get(w));

        return result;
    }
 
    /** Decompress a list of output ks to a string. */
    public static String decompress(List<Integer> compressed) {
        // Build the dictionary.
        int dictSize = 256;
        Map<Integer,String> dictionary = new HashMap<Integer,String>();
        
        for (int i = 0; i < 256; i++)            
            dictionary.put(i, "" + (char)i);
 
        String w = "" + (char)(int)compressed.remove(0);
        StringBuffer result = new StringBuffer(w);
        
        for (int k : compressed) {
            String entry;
            if (dictionary.containsKey(k))
                entry = dictionary.get(k);
            else if (k == dictSize)
                entry = w + w.charAt(0);
            else
                throw new IllegalArgumentException("Bad compressed k: " + k);
 
            result.append(entry);
 
            // Add w+entry[0] to the dictionary.
            dictionary.put(dictSize++, w + entry.charAt(0));
 
            w = entry;
                     
        }
       
        return result.toString();
    }
 
    public static void main(String[] args) throws FileNotFoundException { 
        String input;
        input = readuncompressed("input3.txt");
        List<Integer> compressed = compress(input);
        writecompressed(compressed);
        System.out.println(compressed);
        String decompressed = decompress(compressed);
        System.out.println(decompressed);
        writedecompressed(decompressed);
       
    }
}

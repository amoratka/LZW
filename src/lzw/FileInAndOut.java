/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lzw;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kamila
 */
public class FileInAndOut {

    public static String readuncompressed(String input) {

        FileReader fr = null;
        String linia = "";

        // OTWIERANIE PLIKU:
        try {
            fr = new FileReader(input);
        } catch (FileNotFoundException e) {
            System.out.println("BŁĄD PRZY OTWIERANIU PLIKU!");
            System.exit(1);
        }

        StringBuilder strlist = new StringBuilder();
        BufferedReader bfr = new BufferedReader(fr);
        char i;
        i = (char) 10;
        // ODCZYT KOLEJNYCH LINII Z PLIKU:
        try {
            while ((linia = bfr.readLine()) != null) {

                strlist.append(linia);
                strlist.append(String.valueOf(i));

            }
        } catch (IOException e) {
            System.out.println("BŁĄD ODCZYTU Z PLIKU!");
            System.exit(2);
        }

        // ZAMYKANIE PLIKU
        try {
            fr.close();
        } catch (IOException e) {
            System.out.println("BŁĄD PRZY ZAMYKANIU PLIKU!");
            System.exit(3);
        }
        return strlist.toString();
    }

    public static List<Integer> readcompressed(String outputcompressed) {

        FileReader fr = null;
        String linia = "";

        // OTWIERANIE PLIKU:
        try {
            fr = new FileReader(outputcompressed);
        } catch (FileNotFoundException e) {
            System.out.println("BŁĄD PRZY OTWIERANIU PLIKU!");
            System.exit(1);
        }
        List<Integer> strlist = new ArrayList();
        BufferedReader bfr = new BufferedReader(fr);

        // ODCZYT KOLEJNYCH LINII Z PLIKU:
        try {
            while ((linia = bfr.readLine()) != null) {

                strlist.add(Integer.valueOf(linia));

            }
        } catch (IOException e) {
            System.out.println("BŁĄD ODCZYTU Z PLIKU!");
            System.exit(2);
        }

        // ZAMYKANIE PLIKU
        try {
            fr.close();
        } catch (IOException e) {
            System.out.println("BŁĄD PRZY ZAMYKANIU PLIKU!");
            System.exit(3);
        }
        return strlist;
    }

    /**
     *
     * @param compressed
     * @param dictionary
     * @throws java.io.FileNotFoundException
     */
    public static void writecompressed(List<Integer> compressed) throws FileNotFoundException {

        try (PrintWriter zapis = new PrintWriter("outputcompressed.txt")) {

            //FileOutputStream fos = null;
            String str = "Tekst do zapisania w pliku i dwie liczby: 123 i 321";
            String toString = compressed.toString();
            String[] split;
            split = toString.split(",");
            int n = compressed.size();
            for (int i = 0; i < n; i++) {
                zapis.println(compressed.get(i));
            }
        }
    }

    public static void writedecompressed(String decompressed, String outputfile) throws FileNotFoundException {

        try (PrintWriter zapis = new PrintWriter(outputfile)) {

            zapis.print(decompressed);

        }
    }

}

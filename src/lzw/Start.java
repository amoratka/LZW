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
import java.awt.EventQueue;
import java.awt.Menu;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static lzw.FileInAndOut.readuncompressed;

public class Start {

    /**
     * Compress a string to a list of output symbols.
     * @param args
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
  java.awt.EventQueue.invokeLater(() -> {
      try {
          new MyFrame().setVisible(true);
          
          /*String input;
          input = readuncompressed("input3.txt");
          System.out.println(input);
          List<Integer> compressed = null;
          try {
          compressed = compress(input);
          } catch (FileNotFoundException ex) {
          Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
          }
          try {
          writecompressed(compressed);
          } catch (FileNotFoundException ex) {
          Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
          }
          System.out.println(compressed);
          String decompressed = decompress(compressed);
          System.out.println(decompressed);
          String outputfile = "outputdecompressed.txt";
          try {
          writedecompressed(decompressed, outputfile);
          } catch (FileNotFoundException ex) {
          Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
          }
          try {
          szukaj(outputfile);
          } catch (IOException ex) {
          Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
          }*/
      } catch (IOException ex) {
          Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
      }
  });
                
                
    }
    
}

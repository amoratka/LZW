/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lzw;

import bzip2.Bzip2;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import static lzw.FileInAndOut.readuncompressed;
import static lzw.LZW.compress;
import static lzw.FileInAndOut.writecompressed;
import static lzw.LZW.decompress;
import static lzw.FileInAndOut.writedecompressed;
import static lzw.KMP.wyszukaj;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import static lzw.KMP.wyszukaj;

/**
 *
 * @author Kamila
 */
public final class MyFrame extends JFrame {

    List<Integer> compressed = null;
    String input;
    String inputfile;
    String outputfile;

    public JButton file;
    public JButton fileLZW;
    public JButton fileBZIP;
    public JButton saveLZW;
    public JButton saveBZIP;
    public JButton reset;
    public JTextField box;
    public JButton search;
    public JTextArea comp;
    public JTextArea textAreainput;
    public JTextArea textAreacompress;
    public JTextArea naglowek1;
    public JTextArea naglowek2;
    public JLabel label1;
    public JLabel label2;
    public String text = "";
    public String inputText = "";
    public String decompressed = "";
    public File inputpath;
    public Bzip2 bzip;

    public MyFrame() throws IOException {
        init();

        JRadioButton male = new JRadioButton("LZW");
        JRadioButton female = new JRadioButton("BZIP2");
        ButtonGroup bG = new ButtonGroup();
        male.setBounds(1200, 270, 250, 50);
        female.setBounds(1200, 310, 250, 50);
        bG.add(male);
        bG.add(female);
        female.setVisible(true);
        male.setVisible(true);
        this.add(male);
        this.add(female);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem openAction = new JMenuItem("Open");
        JMenuItem exitAction = new JMenuItem("Exit");
        fileMenu.add(openAction);
        openAction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = null;

                    selectedFile = fileChooser.getSelectedFile();
                    inputfile = selectedFile.getName();
                    System.out.println(inputfile);
                }
            }
        });

        TextArea obszarTekstowy2 = new TextArea(5, 30);
        add(obszarTekstowy2);

        fileMenu.add(exitAction);
        exitAction.addActionListener(new exitApp());

        /*

         JPanel scroll;
         scroll = new JPanel ();
         scroll.add(naglowek1);
         scroll.setBounds(10, 10, 500, 35);
         scroll.setSize(500, 35);
         this.add(scroll);

         */
        naglowek1 = new JTextArea("Plik wczytywany");
        naglowek1.setBounds(10, 10, 500, 35);
        naglowek1.setBackground(new Color(255, 255, 255, 0));
        naglowek1.setFont(new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 30));
        naglowek1.setOpaque(false);

        this.add(naglowek1);

        naglowek2 = new JTextArea("Plik zapisywany");
        naglowek2.setBounds(530, 10, 500, 35);
        naglowek2.setBackground(new Color(255, 255, 255, 0));
        naglowek2.setFont(new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 30));
        naglowek2.setOpaque(false);
        this.add(naglowek2);

        textAreainput = new JTextArea();
        textAreainput.setFont(new Font("Times New Roman", Font.LAYOUT_LEFT_TO_RIGHT, 20));
        textAreainput.setBounds(10, 50, 500, 700);
        textAreainput.setLineWrap(true);
        textAreainput.setEditable(false);
        this.add(textAreainput);

        textAreacompress = new JTextArea();
        textAreacompress.setFont(new Font("Times New Roman", Font.LAYOUT_LEFT_TO_RIGHT, 20));
        textAreacompress.setBounds(530, 50, 500, 700);
        textAreacompress.setLineWrap(true);
        textAreacompress.setEditable(false);
        this.add(textAreacompress);

        JButton LZWC = new JButton("Kompresja");
        LZWC.setBounds(1500, 50, 250, 50);
        LZWC.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent lzwc) {
                input = readuncompressed(inputfile);
                System.out.println(input);
                textAreainput.setText("");
                textAreainput.setText(input);
                if (male.isSelected() == true) {
                    //Kamilki
                    try {
                        compressed = compress(input);
                        writecompressed(compressed);
                        System.out.println(compressed);

                        textAreacompress.setText("");
                        textAreacompress.setText(compressed.toString());

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    //Martynki
                    MyFrame.this.bzip = new Bzip2();
                    try {
                        MyFrame.this.bzip.encode(inputfile);
                        FileReader fr = null;
                        String linia = "";

                        // OTWIERANIE PLIKU:
                        try {
                            fr = new FileReader("wynikto.txt");
                        } catch (FileNotFoundException e) {
                            System.out.println("BŁĄD PRZY OTWIERANIU PLIKU!");
                            System.exit(1);
                        }

                        BufferedReader bfr = new BufferedReader(fr);

                        // ODCZYT KOLEJNYCH LINII Z PLIKU:
                        try {
                            linia = bfr.readLine();

                        } catch (IOException e) {
                            System.out.println("BŁĄD ODCZYTU Z PLIKU!");
                            System.exit(2);
                        }
                        textAreacompress.setText("");
                        textAreacompress.setText(linia);

                        /* try {
                         compressed = compress(input);
                         writecompressed(compressed);
                         System.out.println(compressed);
                         } catch (FileNotFoundException ex) {
                         Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                         }*/
                    } catch (Exception ex) {
                        Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        this.add(LZWC);

        JButton LZWDC = new JButton("Dekompresja ");
        LZWDC.setBounds(1500, 120, 250, 50);
        LZWDC.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent lzwdc) {
                textAreacompress.setText("");

                if (male.isSelected() == true) {
                    textAreacompress.setText(compressed.toString());
                    decompressed = decompress(compressed);
                    System.out.println(decompressed);
                    outputfile = "outputdecompressed.txt";

                    try {
                        writedecompressed(decompressed, outputfile);
                        textAreacompress.setText("");
                        textAreacompress.setText(decompressed);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        bzip.decode();
                        FileReader fr = null;
                        String linia = "";

                        // OTWIERANIE PLIKU:
                        try {
                            fr = new FileReader(input);
                        } catch (FileNotFoundException e) {
                            System.out.println("BŁĄD PRZY OTWIERANIU PLIKU!");
                            System.exit(1);
                        }

                        BufferedReader bfr = new BufferedReader(fr);

                        // ODCZYT KOLEJNYCH LINII Z PLIKU:
                        try {
                            linia = bfr.readLine();

                        } catch (IOException e) {
                            System.out.println("BŁĄD ODCZYTU Z PLIKU!");
                            System.exit(2);
                        }
                        textAreacompress.setText("");
                        textAreacompress.setText(linia);

                        /* try {
                         compressed = compress(input);
                         writecompressed(compressed);
                         System.out.println(compressed);
                         } catch (FileNotFoundException ex) {
                         Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                         }*/
                    } catch (Exception ex) {
                        Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        this.add(LZWDC);

        //  JButton LZWDC = new JButton("Dekompresja");
        // LZWDC.setBounds(1500, 120, 250, 50);
        LZWDC.addActionListener((ActionEvent lzwdc) -> {

        }
        );
        this.add(LZWDC);

        box = new JTextField();
        box.setBounds(1200, 50, 250, 25);
        this.add(box);
        JButton SEARCH = new JButton("Szukaj");
        SEARCH.setBounds(1200, 120, 250, 50);
        SEARCH.addActionListener(new ActionListener() {
            //.removeAllHighlights();
            @Override
            public void actionPerformed(ActionEvent search1) {
                if (!box.getText().isEmpty()) {
                    Highlighter highlighter;
                    highlighter = textAreainput.getHighlighter();

                    highlighter.removeAllHighlights();
                    HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
                    ArrayList<Index> indeksy;
                    indeksy = new ArrayList();
                    //decompressed
                    String fraza = box.getText();
                    try {
                        wyszukaj(indeksy, outputfile, fraza);
                    } catch (IOException ex) {
                        Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {

                        FileReader fr = null;
                        String linia = "";

                        // OTWIERANIE PLIKU:
                        try {
                            fr = new FileReader("zdekodowany.txt");
                        } catch (FileNotFoundException e) {
                            System.out.println("BŁĄD PRZY OTWIERANIU PLIKU!");
                            System.exit(1);
                        }

                        BufferedReader bfr = new BufferedReader(fr);
                        int j = 0;
                        textAreacompress.setText("");
                        String result = "";
                        // ODCZYT KOLEJNYCH LINII Z PLIKU:
                        try {
                            while ((linia = bfr.readLine()) != null) {
                                int n = indeksy.size();
                                int indeks;
                                int nrlinia;
                                while (j < n) {
                                    nrlinia = indeksy.get(j).getLinia();
                                    indeks = indeksy.get(j).getIndex();
                                    if (j == nrlinia) {
                                        highlighter.addHighlight(indeks, fraza.length() + indeks, painter);
                                    } else {
                                        break;
                                    }
                                    j++;
                                }
                                result = result + linia;
                            }

                        } catch (IOException e) {
                            System.out.println("BŁĄD ODCZYTU Z PLIKU!");
                            System.exit(2);
                        }

                        textAreacompress.setText(result);

                        /* try {
                         compressed = compress(input);
                         writecompressed(compressed);
                         System.out.println(compressed);
                         } catch (FileNotFoundException ex) {
                         int n= indeksy.size();
                         for(int i = 0 ; i<n; i++){
                         int linia=indeksy.get(i).getLinia();
                         int indeks=indeksy.get(i).getIndex();
                         highlighter.addHighlight(indeks, fraza.length()+indeks, painter);
                         }                 Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                         }*/
                    } catch (Exception ex) {
                        Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {

                        int n = indeksy.size();
                        for (int i = 0; i < n; i++) {
                            int linia = indeksy.get(i).getLinia();
                            int indeks = indeksy.get(i).getIndex();
                            highlighter.addHighlight(indeks, fraza.length() + indeks, painter);
                        }
                    } catch (BadLocationException ex) {
                        Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(indeksy);

                }
            }
        });
        this.add(SEARCH);

    }

    public void init() {
        setVisible(true);
        setSize(1800, 900);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Menu");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    static class exitApp implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

}

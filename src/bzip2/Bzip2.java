package bzip2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Bzip2 {
	
	public void encode(String path) throws Exception{
		Scanner odczyt = new Scanner(new File(path));
		String line = new String();
		line=odczyt.nextLine();
		char[] tablica;
		tablica=line.toCharArray();
		BurrowsWheeler bw = new BurrowsWheeler(tablica);
		bw.doBurrowsWheelerTransform(tablica);
		MoveToFront mtf = new MoveToFront(bw.output);
		mtf.MTFencode();
		HuffmanCoding huffman = new HuffmanCoding(mtf.output);
		huffman.HuffmanEncode();
	}
	public void decode() throws IOException{
		
		HuffmanDecoding huffmand = new HuffmanDecoding();
		MoveToFrontDecode mtfd = new MoveToFrontDecode(huffmand.output);
		mtfd.MTFdecode();
		BurrowsWheelerDecode bwd= new BurrowsWheelerDecode(mtfd.output);
		bwd.setFirstColumn();
		bwd.setVectorT();
		bwd.decode();
		
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Hello world");
		String napisek = new String("Matyna bleeeeeee");
		char[] tablica;
		tablica=napisek.toCharArray();
		//kodowanie
		
		//bw.printTable(bw.output.suffixTable);
		//System.out.println("number: "+bw.output.numberOfCombination);
		//System.out.println("*********");
		
		//bw.printTable(bw.output.suffixTable);
		
	}

}

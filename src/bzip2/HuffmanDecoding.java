package bzip2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class HuffmanDecoding {
	
	String napis;
	BufferedReader br;
	String[] inputString=new String[2];
	Map<String, Integer> map;
	ArrayList<Short> shortList;
	short[] output;
	
	public HuffmanDecoding() throws IOException{
		this.napis=new String();
		br = new BufferedReader(new FileReader("wynikto.txt"));
		shortList = new ArrayList<Short>();
		inputString= new String[2];
		this.map = new HashMap<String, Integer>();
		napis=br.readLine();
		System.out.println("Przeczytana linia z pliku: ");
		System.out.println(napis);
		readMap();
		decode();
		listToArray();
	}
	
	public void readMap(){
		this.inputString=this.napis.split(" ");
		String temp = new String();
		temp= this.inputString[0];
		int endKey, endCode;
		int combination=temp.indexOf("-");
		this.shortList.add(Short.valueOf(temp.substring(0,combination)));
		temp=temp.substring(combination+1);
		while(temp.length()>0){
			endKey=temp.indexOf(":");
			//System.out.println("poz : "+endKey);
			endCode=temp.indexOf(";");
			//System.out.println("poz ; "+endCode);
			//System.out.println(temp.substring(0, endKey));
			this.map.put(temp.substring(endKey+1, endCode),Integer.parseInt(temp.substring(0, endKey)));
			//System.out.println("Z mapy:");
			temp=temp.substring(endCode+1);
		//	System.out.println(temp);
		}
		//System.out.println(map.get(4));
	}
	public void decode(){
		String buff = new String();
		String toScan = new String();
		toScan=this.inputString[1];
		//System.out.println("toScan: "+toScan);
		while(toScan.length()>0){
			buff=buff.concat(toScan.substring(0, 1));
			toScan=toScan.substring(1);
			if(map.containsKey(buff)==true){
				this.shortList.add(Short.valueOf(String.valueOf(map.get(buff))));
				buff="";
			}
		}	
		this.printList();
	}
	public void printList(){
		for( int i=0; i<this.shortList.size(); i++){
			System.out.print(Short.toString(this.shortList.get(i))+" ");
		}
		System.out.println("");
	}
	public void listToArray(){
		this.output= new short[this.shortList.size()];
		for(int i=0; i<this.shortList.size(); i++){
			this.output[i]=this.shortList.get(i);
		}
	}
}

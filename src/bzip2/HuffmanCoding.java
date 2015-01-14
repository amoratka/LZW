package bzip2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class HuffmanCoding {
	
	Queue<LeafNode> nodeQueue;
	short[] input;
	short[] uniqShorts;
	int[] freqTable;
	ArrayList<HuffmanOutput> output;
	byte[] outputToFile;
	String header;
	BitWriter writer;
	PrintWriter zapis;
	File file;
	FileWriter fileWritter;
	BufferedWriter zapisz;
	FileOutputStream fop;
	public HuffmanCoding(short[] input) throws Exception{
		nodeQueue = new PriorityQueue<LeafNode>();
		this.input = new short[input.length-1];
		this.copyTable(input, this.input);
		this.uniqShorts= new short[input.length];
		this.zeroTable(this.uniqShorts);
		this.freqTable=new int[input.length];
		this.output= new ArrayList<HuffmanOutput>();
		this.header=new String();
		this.header=this.header.concat(Integer.toString(input[0]*(-1))+"-");
		//this.writer= new BitWriter("wynikxx.txt");
		//this.writer.writeBit(0);
		//this.writer.flush();
		file= new File("wynik.txt");
		fileWritter = new FileWriter(file,true);
		zapisz= new BufferedWriter(fileWritter);
		//zapis = new PrintWriter("wynikxx.txt");
		fop=new FileOutputStream("wynikto.txt");
	}
	public void HuffmanEncode() throws IOException{
		this.setUniqShorts();
		this.setFreqTable();
		this.createNodeQueue();
		this.createHuffmanTree();
		this.setCodes(this.nodeQueue.poll(), new StringBuffer());
		this.printOutput();
		this.writeToFile();
	}
	/*void EncodeNode(LeafNode node, BitWriter writer)
	{
	    if (node.IsLeafNode)
	    {
	        writer.writeBit(1);
	        writer.WriteByte(node.Value);
	    }
	    else
	    {
	        writer.writeBit(0);
	        EncodeNode(node.leftChild, writer);
	        EncodeNode(node.rightChild, writer);
	    }
	}*/
	public int countLength(){
		int length=0;
		for(int i=0; i<this.output.size(); i++)
			length=length+this.output.get(i).code.length;
		return length;
	}
	/*public void iniHuffmanOutput(int size){
		this.output= new HuffmanOutput[size];
		for(int i=0; i<size; i++){
			this.output[i]=new HuffmanOutput();
		}
	}*/
	
	public int findCodeElement(short character){
		for(int i=0; i<this.output.size();i++){
			if(this.output.get(i).symbol==character)
				return i;
		}
		return -1;
	}
	public void addToTable(byte[] tableToAdd, byte[] destinationTable,int positionToStart){
		for(int i=0; i<tableToAdd.length; i++){
			destinationTable[positionToStart]=tableToAdd[i];
			positionToStart++;
		}
	}
	public void createHeader() throws IOException{
		/*for(int i=0; i<this.output.size();i++){
			this.header=this.header.concat(output.get(i).toString());
		}*/
		//this.zapisz.print(this.header);
		for(int i=0; i< this.output.size(); i++){
			this.header=this.header.concat(Short.toString(this.output.get(i).symbol)+":"+new String(this.output.get(i).code)+";");
		}
		this.header=this.header.concat(" ");
		this.zapisz.write(this.header);
	}
	public void writeToFile() throws IOException{
		/*FileOutputStream stream = new FileOutputStream("/home/martyna/workspace/bzip2/wynik.bin");
		try {
			for(int i=0; i<this.input.length; i++){
				stream.write(this.output.get(this.findCodeElement(this.input[i])).code);
			}
		} finally {
		    stream.close();
		}*/
		//this.createHeader();
		//this.header=this.header.concat(" ");
		
		this.createHeader();
		System.out.println("Header: ");
		System.out.println(this.header);
	//	this.zapis.flush();
	//	this.zapisz.flush();
		this.outputToFile=new byte[this.countLength()+this.header.length()];
		int positionToStart=0;
		String code = new String();
		this.addToTable(this.header.getBytes(), this.outputToFile, positionToStart);
		positionToStart=this.header.getBytes().length-1;
		for(int i=0; i<this.input.length;i++){
			//Files.write(Paths.get("target-file"),this.output.get(this.findCodeElement(this.input[i])).code);
			this.addToTable(this.output.get(this.findCodeElement(this.input[i])).code, this.outputToFile, positionToStart);
			code=code.concat(new String(this.output.get(this.findCodeElement(this.input[i])).code));
			positionToStart+=this.output.get(this.findCodeElement(this.input[i])).code.length-1;
		}
		System.out.println("Po addToTable:");
		this.printByteArray(this.outputToFile);
		System.out.println("Code: ");
		System.out.println(code);
		this.header=this.header.concat(code);
		this.fop.write(header.getBytes());
		this.fop.flush();
		
		/*this.createHeader();
		this.zapisz.flush();*/
		//Files.write(Paths.get("target-file"),this.outputToFile, StandardOpenOption.APPEND );	
	}
	public void setCodes(LeafNode tree, StringBuffer prefix) {
        if(tree.leftChild==null&&tree.rightChild==null){
        	System.out.println("Prefix length: "+prefix.length());
          //  this.output.get(prefix.length()-1).setOutputsize(prefix.length());
        //	header=header.concat("1:");
        //	header=header.concat(Short.toString(tree.symbol));
        //	header=header.concat(";");
        	
            this.output.add(new HuffmanOutput(prefix,tree.symbol));
           // this.output[prefix.length()-1].code=String.valueOf(prefix).getBytes();
          //  this.output[prefix.length()-1].symbol=tree.symbol;
          //  writer.writeBit(1);
	      //  writer.writeInt((int)tree.symbol);
        } else {
        	// traverse left
        //	writer.writeBit(0);
        //	header=header.concat("0");
            prefix.append('0');
            setCodes(tree.leftChild, prefix);
            prefix.deleteCharAt(prefix.length()-1);
 
            // traverse right
          //  writer.writeBit(0);
          //  header=header.concat("0");
            prefix.append('1');
            setCodes(tree.rightChild, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }
	public void printOutput(){
		for( int i=0; i<this.output.size(); i++){
			System.out.print("Znak: +"+ output.get(i).symbol+" : ");
			this.printByteArray(output.get(i).code);
		}
	}
	public void printByteArray(byte[] array){
		for(int i=0; i<array.length; i++){
			System.out.print(array[i]+" ");
		}
		System.out.println("");
	}
	public void createHuffmanTree(){
		LeafNode internalNode;
		while(this.nodeQueue.size()>1){
			internalNode= new LeafNode(this.nodeQueue.poll(),this.nodeQueue.poll());
			this.nodeQueue.add(internalNode);
		}
	}
	public void createNodeQueue(){
		//for(int i=0; this.uniqShorts[i]>0; i++){
		for( int i=0; i<this.uniqShorts.length; i++){	
			this.nodeQueue.add(new LeafNode(this.uniqShorts[i],this.freqTable[i]));
		}
		//this.printQueue();
	}
	public void printQueue(){
		LeafNode node=this.nodeQueue.poll();
		System.out.println(node);
		while(node!=null){
			node=this.nodeQueue.poll();
			System.out.println(node);
		}
		
	}
	public void setFreqTable(){
		System.out.println("FreqTable: ");
		int i=0;
		//for(i=0; this.uniqShorts[i]>0; i++){
		for(i=0; i<this.uniqShorts.length;i++){
			this.freqTable[i]=this.countFrequencyOfShort(this.uniqShorts[i]);
			System.out.println("Wartość uniqShorts[i]: "+this.uniqShorts[i]+" i: "+i);
			System.out.println("freqTable[i]: "+this.freqTable[i]);
		}
		//this.iniHuffmanOutput(i);
	}
	public void setUniqShorts(){
		boolean alreadyInTable = false;
		int iterator=0;
		for( int i=0; i<this.input.length; i++){
			for(int j=0; j<this.uniqShorts.length; j++)
				if(this.input[i]==this.uniqShorts[j]){
					alreadyInTable=true;
					break;
				}
			if(alreadyInTable==false){
				this.uniqShorts[iterator]=this.input[i];
				iterator++;
			}
			alreadyInTable=false;
		}
		System.out.println("uniqTable:");
		this.printTable(this.uniqShorts);
		
	}
	public int countFrequencyOfShort(short toCount){
		int count=0;
		for(int i=0; i<this.input.length; i++){
			if(this.input[i]==toCount)
				count++;
		}
		return count;
	}
	public void zeroTable(short[] table){
		for(int i=0; i<table.length; i++)
			table[i]=0;
	}
	public void copyTable(short[] tableToCopy, short[] destinationTable){
		int iterator=1;
		for(int i=0; i<tableToCopy.length-1; i++){
			destinationTable[i]=tableToCopy[iterator];
			iterator++;
		}
	}
	public void printTable(short[] tableToPrint){
		for(int i=0; i<tableToPrint.length; i++){
			System.out.print(tableToPrint[i]+" ");
		}
		System.out.println("");
	}
}

package bzip2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;


public class BurrowsWheelerDecode {
	Suffix firstColumn;
	Suffix inputCopy;
	Suffix input;
	int[] vectorT;
	char[] output;
	PrintWriter zapis;
	//File file;
	//FileWriter fileWritter;
	//BufferedWriter zapisz;
	//FileOutputStream fop;
	public BurrowsWheelerDecode(Suffix suffix) throws FileNotFoundException{
		this.input=new Suffix(suffix.suffixTable.length, suffix.numberOfCombination);
		this.copyTable(suffix.suffixTable, this.input.suffixTable);
		//fop=new FileOutputStream("zdekodowany.txt");
		zapis = new PrintWriter("zdekodowany.txt");
	}
	public void writeTofile(){
		zapis.write(String.valueOf(this.output));
		zapis.flush();
	}
	public void decode(){
		output= new char[this.input.suffixTable.length];
		int pom=0;
		int i=this.input.numberOfCombination; 
		while(pom<this.input.suffixTable.length){
			output[pom]=this.input.suffixTable[i];
			i=this.vectorT[i];
			pom++;
		}
		//Drukowanie output
		System.out.println("Output:");
		this.printTable(output);
		this.writeTofile();
	}
	
	public void setVectorT(){
		//kopiuje ĹźÄby sobie zaznaczaÄ ktĂłre elementy byĹy juĹź uĹźyte
		inputCopy = new Suffix(input.suffixTable.length, input.numberOfCombination);
		this.copyTable(input.suffixTable, this.inputCopy.suffixTable);
		vectorT= new int[input.suffixTable.length];
		
		for(int i=0; i<firstColumn.suffixTable.length; i++){
			vectorT[i]=this.findCharPosition(this.firstColumn.suffixTable[i], this.inputCopy.suffixTable);
			this.inputCopy.suffixTable[this.vectorT[i]]='-';
		}
		//drukowanie wektoraT:
		/*
		System.out.println("wektor T: ");
		for(int i=0; i<vectorT.length; i++){
			System.out.print(vectorT[i]+" ");
		}
		System.out.println("");
		*/
	}
	public int findCharPosition(char a, char[] table){
		int i;
		for(i=0; i<table.length; i++){
			if(table[i]==a)
				break;
		}
		return i;
	}
	public void setFirstColumn(){
		this.firstColumn=new Suffix(this.input.suffixTable.length, this.input.numberOfCombination);
		this.copyTable(this.input.suffixTable, this.firstColumn.suffixTable);
		Arrays.sort(this.firstColumn.suffixTable);
		this.printTable(this.firstColumn.suffixTable);
	}
	public void copyTable(char[] tableToCopy, char[] destinationTable){
		for(int i=0; i<tableToCopy.length; i++){
			destinationTable[i]=tableToCopy[i];
		}
	}
	public void printTable(char[] tableToPrint){
		for(int i=0; i<tableToPrint.length; i++){
			System.out.print(tableToPrint[i]);
		}
		System.out.println("");
	}
}
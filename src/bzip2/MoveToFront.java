package bzip2;

public class MoveToFront {
	
	String inputWord;
	char[] input;
	short[] output;
	char[] dictionary;
	
	public MoveToFront(Suffix input){
		//inputWord = new String("ddddddbbbbbccccaaadd");
		//input=this.inputWord.toCharArray();
		this.input=input.suffixTable;
		output = new short[this.input.length+1];
		output[0]=(short) (input.numberOfCombination*(-1));
		dictionary=iniDictionary();
		//System.out.println("output[0]: "+Short.toString(output[0]));
		//System.out.println("Przeczytana tablica: ");
	//	this.printCharArray(this.input);
		//output[17]=0;
	}
	public void printCharArray(char[] array){
		for(int i=0;i<array.length; i++ )
			System.out.print(array[i]+" ");
		System.out.println("");
	}
	public char[] iniDictionary(){
		char[] dictionary= new char[256];
		for(int i=0; i<256; i++){
			dictionary[i]=(char) i;
		}
		//this.printTable(dictionary);
		return dictionary;		
	}
	public void MTFencode(){
		int iterator=1;
		for(int i=0; i<input.length; i++){
			output[iterator]=(short) (this.findCharPosition(input[i], dictionary)+1);
			shift(output[iterator]-1,dictionary);
			iterator++;
		}
		//drukowanie zakodowanego
		for(int i=0; i<output.length; i++)
			System.out.print(output[i]+" ");
	}
	public void shift(int fromIndex, char[] table){
		char temp=table[fromIndex];
		for(int i=fromIndex-1; i>=0; i--){
			table[i+1]=table[i];
		}
		table[0]=temp;
	}
	public int findCharPosition(char a, char[] table){
		int i;
		for(i=0; i<table.length; i++){
			if(table[i]==a)
				break;
		}
		return i;
	}
	public void printShortTable(short[] tableToPrint){
		for(int i=0; i<tableToPrint.length; i++){
			System.out.print(tableToPrint[i]);
		}
		System.out.println("");
	}
	public void printTable(char[] tableToPrint){
		for(int i=0; i<tableToPrint.length; i++){
			System.out.print(tableToPrint[i]);
		}
		System.out.println("");
	}
}

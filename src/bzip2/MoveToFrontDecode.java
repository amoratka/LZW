package bzip2;

public class MoveToFrontDecode {
	Suffix output;
	short[] input;
	char[] dictionary;
	
	public MoveToFrontDecode(short[] input){
		dictionary=iniDictionary();
		this.input= new short[input.length-1];
		output= new Suffix(input.length-1,input[0]);
		
		//this.output.suffixTable= new char[input.length];
		this.copyTable(input,this.input);
	}
	public char[] iniDictionary(){
		char[] dictionary= new char[256];
		for(int i=0; i<256; i++){
			dictionary[i]=(char) i;
		}
		//this.printTable(dictionary);
		return dictionary;		
	}
	public void MTFdecode(){
		for(int i=0; i<this.input.length; i++){
			this.output.suffixTable[i]=this.dictionary[this.input[i]-1];
			this.shift(input[i]-1,dictionary);
		}
		System.out.println("Odkodowane");
		this.printTable(this.output.suffixTable);
	}
	public void shift(int fromIndex, char[] table){
		char temp=table[fromIndex];
		for(int i=fromIndex-1; i>=0; i--){
			table[i+1]=table[i];
		}
		table[0]=temp;
	}
	public void copyTable(short[] tableToCopy, short[] destinationTable){
		int iterator=1;
		for(int i=0; i<tableToCopy.length-1; i++){
			destinationTable[i]=tableToCopy[iterator];
			iterator++;
		}
	}
	public void printTable(char[] tableToPrint){
		for(int i=0; i<tableToPrint.length; i++){
			System.out.print(tableToPrint[i]);
		}
		System.out.println("");
	}
}

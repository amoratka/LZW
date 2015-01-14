package bzip2;

import java.util.Arrays;

public class BurrowsWheeler {
	
	Suffix[] suffixes;
	Suffix output;
	
	public BurrowsWheeler(char[] table){
		suffixes= new Suffix[table.length];
		output= new Suffix(table.length,0);
	}
	
	public void doBurrowsWheelerTransform(char[] table){
		this.readSuffixesFromTable(table);
		this.sortSuffixes();
		this.readLastSuffix();
	}
	
	public void readLastSuffix(){
		for(int i=0; i<suffixes.length; i++){
			output.suffixTable[i]=suffixes[i].suffixTable[suffixes.length-1];
			if(suffixes[i].numberOfCombination==1)
				output.numberOfCombination=i;
		}
	}
	
	public void readSuffixesFromTable(char[] table){
		 
		this.initiateSuffixTable(this.suffixes);
		this.copyTable(table, this.suffixes[0].suffixTable);
		
		for(int i=1; i<table.length; i++){
			this.copyTable(this.rightRotate(this.suffixes[i-1].suffixTable), this.suffixes[i].suffixTable);
		}
		this.writeSuffixesFromTable(suffixes);
		//this.sortSuffixes();
	}
	
	public void sortSuffixes(){
		Arrays.sort(this.suffixes);
		System.out.println("po sortowaniu:");
		this.writeSuffixesFromTable(this.suffixes);
	}
	
	public void writeSuffixesFromTable(Suffix[] suffixTable){
		for(int i=0; i<suffixTable.length; i++){
			System.out.print(suffixTable[i].numberOfCombination+" ");
			this.printTable(suffixTable[i].suffixTable);
		}
	}
	
	public void initiateSuffixTable(Suffix[] suffixTable){
		for(int i=0; i<suffixTable.length; i++){
			suffixTable[i]=new Suffix(suffixTable.length,i);
		}
	}
	
	public char[] rightRotate(char[] tableToRotate){
		char[] tableToRotateCopy=new char[tableToRotate.length];
		this.copyTable(tableToRotate, tableToRotateCopy);
		char tmp2;
		char tmp=tableToRotateCopy[tableToRotateCopy.length-2];
		tableToRotateCopy[tableToRotateCopy.length-2]=tableToRotateCopy[tableToRotateCopy.length-1];
		for(int i=tableToRotateCopy.length-2; i>=0; i--){
			if(i==0){
				tableToRotateCopy[tableToRotate.length-1]=tmp;
				break;
			}
			tmp2=tableToRotateCopy[i-1];
			tableToRotateCopy[i-1]=tmp;
			tmp=tmp2;
			//była rotacja w prawo
			/*if(i==tableToRotateCopy.length-1){
				tableToRotateCopy[0]=tmp;
				break;
			}
			tmp2=tableToRotateCopy[i+1];
			tableToRotateCopy[i+1]=tmp;
			tmp=tmp2;*/
		}
		return tableToRotateCopy;
	}
	
	public void printTable(char[] tableToPrint){
		for(int i=0; i<tableToPrint.length; i++){
			System.out.print(tableToPrint[i]);
		}
		System.out.println("");
	}
	
	public void copyTable(char[] tableToCopy, char[] destinationTable){
		for(int i=0; i<tableToCopy.length; i++){
			destinationTable[i]=tableToCopy[i];
		}
	}
}

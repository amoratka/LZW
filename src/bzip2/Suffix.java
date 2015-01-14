package bzip2;

public class Suffix implements Comparable<Suffix> {
	public char[] suffixTable;
	int numberOfCombination;
	
	public Suffix(int size, int numberOfCombination){
		this.suffixTable=new char[size];
		this.numberOfCombination=numberOfCombination;
	}

	@Override
	public int compareTo(Suffix o) {
		for(int i=0; i<o.suffixTable.length; i++){
			if(suffixTable[i]<o.suffixTable[i])
				return -1;
			if(suffixTable[i]>o.suffixTable[i])
				return 1;
		}
		return 0;
	}

}

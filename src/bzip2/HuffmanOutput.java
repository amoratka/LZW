package bzip2;

import java.io.UnsupportedEncodingException;

public class HuffmanOutput {
	byte[] code;
	short symbol;
	
	public HuffmanOutput(StringBuffer prefix, short symbol){
		this.symbol=symbol;
		this.code=String.valueOf(prefix).getBytes();
	}
	public void copyByteArray(byte[] arrayToCopy, byte[] destinationArray){
		for(int i=0; i<arrayToCopy.length; i++){
			destinationArray[i]=arrayToCopy[i];
		}
	}
	public void setOutputsize(int size){
		this.code= new byte[size];
	}
	@Override
	public String toString(){
		String name = new String(Short.toString(this.symbol));
		String fileString;
		try {
			fileString = new String(this.code,"UTF-8");
			name=name.concat(":");
			name=name.concat(String.valueOf(this.code.length));
			name=name.concat(":");
			name=name.concat(fileString);
			name=name.concat(";");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return name;
	}
}

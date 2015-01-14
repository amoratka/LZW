package bzip2;

public class LeafNode implements Comparable<LeafNode>{
	short symbol;
	int frequency;
	LeafNode parent;
	LeafNode leftChild;
	LeafNode rightChild;
	
	public LeafNode(short symbol, int frequency){
		this.symbol=symbol;
		this.frequency=frequency;
		this.parent=null;
		this.leftChild=null;
		this.rightChild=null;
	}
	public LeafNode(LeafNode left, LeafNode right){
		this.leftChild=left;
		this.rightChild=right;
		this.frequency=left.frequency+right.frequency;
	}
	public LeafNode(){
		
	}
	@Override
    public int compareTo(LeafNode o) {
        if(this.frequency<o.frequency)
            return -1;
        if(this.frequency>o.frequency)
                return 1;
        else return 0;
    }
	@Override
	public String toString(){
		return this.symbol+" "+this.frequency;
	}
}

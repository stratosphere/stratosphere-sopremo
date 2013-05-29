package eu.stratosphere.sopremo.base;

import eu.stratosphere.sopremo.expressions.BinaryBooleanExpression;

public class InputTuple implements Comparable<InputTuple>{
	
	private int firstInputIndex;
	private int secondInputIndex;
	private BinaryBooleanExpression associatedExpression; 
	
	public InputTuple(BinaryBooleanExpression associatedExpression, int firstInputIndex, int secondInputIndex){
		this.associatedExpression = associatedExpression;
		this.firstInputIndex = firstInputIndex;
		this.secondInputIndex = secondInputIndex;
	}


	public int getSecondInputIndex() {
		return secondInputIndex;
	}


	public void setSecondInputIndex(int secondInputIndex) {
		this.secondInputIndex = secondInputIndex;
	}


	public int getFirstInputIndex() {
		return firstInputIndex;
	}

	public void setFirstInputIndex(int firstInputIndex) {
		this.firstInputIndex = firstInputIndex;
	}

	public int getHigherInputIndex(){
		if(this.firstInputIndex > this.secondInputIndex)
			return firstInputIndex;
		else
			return secondInputIndex;
	}
	public int getLowerInputIndex(){
		if(this.firstInputIndex < this.secondInputIndex)
			return firstInputIndex;
		else
			return secondInputIndex;
	}

	public BinaryBooleanExpression getAssociatedExpression() {
		return associatedExpression;
	}


	public void setAssociatedExpression(BinaryBooleanExpression associatedExpression) {
		this.associatedExpression = associatedExpression;
	}


	@Override
	public int compareTo(InputTuple anInputTupleToCompareTo) {
		if(this.getHigherInputIndex() < anInputTupleToCompareTo.getHigherInputIndex()){
			return -1;
		} else if(this.getHigherInputIndex() == anInputTupleToCompareTo.getHigherInputIndex()){
			if(this.getLowerInputIndex() < anInputTupleToCompareTo.getLowerInputIndex()){
				return -1;
			}else if(this.getLowerInputIndex() == anInputTupleToCompareTo.getLowerInputIndex()){
				return 0;
			}
			else return 1;
		} else
			return 1;
	}

	@Override
	public String toString(){
		return this.getLowerInputIndex()+":"+this.getHigherInputIndex();
	}

}

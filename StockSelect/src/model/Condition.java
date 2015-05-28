package model;

public class Condition {

	public static final String[] CONDITIONS = 
			new String[]{"priceChangeRatio", "curPrice", "pe", "dynamicPE", "pb"};
	
	private int index;
	private String condition;
	private Object min;
	private Object max;
	private Boolean isChosen;
	
	public Condition(int index, Object min, Object max){
		this.index = index;
		this.condition = CONDITIONS[index];
		this.min = min;
		this.max = max;
		this.isChosen = false;
	}
	
	public int getIndex() {
		return index;
	}

	public String getValiabel() {
		return condition;
	}

	public Object getMin() {
		return min;
	}

	public Object getMax() {
		return max;
	}

	public Boolean getIsChosen() {
		return isChosen;
	}

	public void setIsChosen(Boolean isChosen) {
		this.isChosen = isChosen;
	}

	
}

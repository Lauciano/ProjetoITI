
public class Codigo {
	private int low;
	private int high;
	private int total;
	
	public Codigo(int low, int high){
		this.low = low;
		this.high = high;
		this.total = 1;
	}
	
	public Codigo(Codigo c){
		this.low = c.low;
		this.high = c.high;
		this.total = c.total;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}
	
	public int getTotal(){
		return total;
	}
	
	public void setTotal(int total){
		this.total = total;
	}
	
	public String toString(){
		return "[" + low + ", " + high + ") em " + total;
	}
}


public class ObservableArgs {

	private String argName;
	private Object value;
	
	public ObservableArgs(String argName, Object value) {
		
		this.argName = argName;
		this.value = value;
		
	}
	
	public String getName() {
		return this.argName;
	}
	
	public Object getValue() {
		return value;
	}
	
}

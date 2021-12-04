package CODE;


import java.io.Serializable;

public class ColInfo implements Serializable {
	private String col_name;
	private String col_type;
	
	
	
	
	public ColInfo(String col_name, String col_type) {
		
		this.col_name = col_name;
		this.col_type = col_type;
	}
	public String getCol_type() {
		return col_type;
	}
	public void setCol_type(String col_type) {
		this.col_type = col_type;
	}
	public String getCol_name() {
		return col_name;
	}
	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}
}

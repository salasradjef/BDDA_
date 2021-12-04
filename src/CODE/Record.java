package CODE;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Record {
	
	private RelationInfo relInfo;
	private String[] values;
	
	
	public Record(RelationInfo rel,String[] values) {
		this.setRelInfo(rel);
		this.values = values;
	}
	
	
	public void writeToBuffer(ByteBuffer buff, int position) {
		buff.position(position);
		for(int i=0;i<=relInfo.getNbr_col();i++) {
			String values_type = relInfo.getCol()[i].getCol_type();
			String[] pw = values_type.split("string");

			if(values_type.equals("int")) {
				buff.putInt(Integer.parseInt(values[i]));
			}
			
			if(values_type.equals("float")) {
				buff.putFloat(Float.parseFloat(values[i]));
			}
			
			if(pw[0].equals("string")) {
				for(int j=0;j<= Integer.parseInt(pw[1]);j++ ) {
					String[] sp = values[i].split("");
					for(int z=0;z<=sp.length;z++) {
						buff.putChar(sp[z].charAt(0));
					}
					
				}
			}
				
		}

	}
	
	
	public void readFromBuffer(ByteBuffer buff,int position) {
		buff.position(position);
		for(int i=0;i<=relInfo.getNbr_col();i++) {
			String values_type = relInfo.getCol()[i].getCol_type();
			String[] pw = values_type.split("string");

			if(values_type.equals("int")) {
				values[i] = String.valueOf(buff.getInt());
				
			}
			
			if(values_type.equals("float")) {
				values[i] = String.valueOf(buff.getFloat());
				
				
			}
			
			if(pw[0].equals("string")) {
				StringBuffer sb = new StringBuffer();
				for(int j=0;j<= Integer.parseInt(pw[1]);j++ ) {
					buff.getChar();
					sb.append(buff.getChar());
				}
				values[i] = sb.toString();
			}
		}
		
	}

	
	
	
	

	public RelationInfo getRelInfo() {
		return relInfo;
	}


	public void setRelInfo(RelationInfo relInfo) {
		this.relInfo = relInfo;
	}


	public String[] getValues() {
		return values;
	}


	public void setValues(String[] values) {
		this.values = values;
	}
}


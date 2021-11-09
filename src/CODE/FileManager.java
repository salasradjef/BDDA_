package miniSGBD;

import java.nio.ByteBuffer;

public class FileManager {
private static FileManager INSTANCE;
	
	public static FileManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new FileManager();
			
		}
		
		return INSTANCE;
	}
	
	
	
	public PageId readPageIdFromPageBuffer(ByteBuffer buff,boolean first) {
		
		return null;
		
	}
	
	public PageId writePageIdToPageBuffer(PageId PID,ByteBuffer buff,boolean first) {
		return null;
		
	}
	
	public PageId createHeaderPage() {
		return null;
	}
	
	public PageId addDataPage(RelationInfo relInfo) {
		return null;
	}
	
	
	public PageId getFreeDataPageId(RelationInfo relInfo) {
		return null;
	}
	
	public Rid writeRecordToDataPage(RelationInfo relInfo , Record record, PageId PID) {
		return null;
		
	}
	
	
}

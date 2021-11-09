package CODE;

import java.io.IOException;
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
		
		if(first) {
			buff.position(0);
			return new PageId(buff.getInt(), buff.getInt());
			}else {
			buff.position(8);
			return new PageId(buff.getInt(), buff.getInt());
		}
	}
	
	public void writePageIdToPageBuffer(PageId PID,ByteBuffer buff,boolean first) {
		if(first) {
			buff.position(0);
			buff.putInt(Integer.valueOf(PID.getFileIdx()));
			buff.putInt(Integer.valueOf(PID.getPageIdx()));
			
		}else {
			buff.position(8);
			buff.putInt(Integer.valueOf(PID.getFileIdx()));
			buff.putInt(Integer.valueOf(PID.getPageIdx()));
		}
		
	}
	
	public PageId createHeaderPage() throws IOException {
		DiskManager disk = DiskManager.getInstance();
		PageId header = disk.AllocPage();
		
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

package CODE;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.sun.jdi.Field;


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
		
		
		PageId fac = new PageId(-1,0);
		BufferManager BM = BufferManager.getInstance();
		
		byte[] a = BM.getPage(header);
		ByteBuffer tmp = ByteBuffer.wrap(a);
		
		
		writePageIdToPageBuffer(fac, tmp, true);
		writePageIdToPageBuffer(fac,tmp,false);
		
		
		BM.FreePage(header, 1);
		
		return header;
	}
	
	public PageId addDataPage(RelationInfo relInfo) throws IOException {
		DiskManager disk = DiskManager.getInstance();
		BufferManager BM = BufferManager.getInstance();
		
		PageId pageV = disk.AllocPage();
		
		//lecture de la headerPage
		PageId headerPage = relInfo.getHeaderPageId();
		byte[] a = BM.getPage(headerPage); //Charger HeaderPage
		ByteBuffer tmp = ByteBuffer.wrap(a); 
		
		
		byte[] b = BM.getPage(pageV); // Charger notre nouvelle page
		ByteBuffer tmp2 = ByteBuffer.wrap(b);
		
		
		
		
		writePageIdToPageBuffer(pageV, tmp, true); //ecrire dans le premierPageId de la headerPage le PID de notre nouvelle
		
		writePageIdToPageBuffer(headerPage, tmp2, true);
		writePageIdToPageBuffer(headerPage, tmp2, false);
		
		
		BM.FreePage(headerPage, 1);
		
		
		tmp2.position(16);
		for(int i=0;i<relInfo.getSlotCount();i++) {
			tmp2.putInt(0);
		}
		
		
		BM.FreePage(pageV, 1);
		return pageV;
	}
	
	
	public PageId getFreeDataPageId(RelationInfo relInfo) throws IOException {
		BufferManager BM = BufferManager.getInstance();
		byte[] tmp = BM.getPage(relInfo.getHeaderPageId());
		
		ByteBuffer sd = ByteBuffer.wrap(tmp);
		BM.FreePage(relInfo.getHeaderPageId(), 0);
		
		PageId firstEmpty = getInstance().readPageIdFromPageBuffer(sd, true);
		byte[] ts = BM.getPage(firstEmpty);
		ByteBuffer tsp = ByteBuffer.wrap(ts); int i=0;
		BM.FreePage(firstEmpty, 0);
		
		
		boolean trouve = false;
		while(!trouve) {
			if(i!=0) {
				firstEmpty = getInstance().readPageIdFromPageBuffer(tsp, false);
				ts = BM.getPage(firstEmpty);
				tsp = ByteBuffer.wrap(ts);
				BM.FreePage(firstEmpty, i);
			}
			
			boolean caseVIDE = false;
			if(firstEmpty.getFileIdx() != -1 && firstEmpty.getPageIdx() !=0) {
				int cmp=0;
				while(!caseVIDE) {
					tsp.position(16);
					if(tsp.get(cmp) == 0) {
						caseVIDE = true;
						trouve = true;
						break;
					}
				}
			}else {
				firstEmpty = getInstance().addDataPage(relInfo);	
				break;
			}

			
			
			
			if(caseVIDE && trouve) {
				break;
			}
		}
		
	
		return firstEmpty;
	}
	
	public Rid writeRecordToDataPage(RelationInfo relInfo , Record record, PageId PID) throws IOException {
		BufferManager BM = BufferManager.getInstance();
		byte[] a = BM.getPage(PID);
		ByteBuffer buff = ByteBuffer.wrap(a);
		boolean trv = false;
		int sltID = 0;
		int s;
		Rid fin = null;
		while(!trv) {
			buff.position(16);
			for(int i=0;i<relInfo.getSlotCount();i++) {
				s = buff.getInt();
				if(s == 0 ) {
					trv = true;
					sltID = i;
				}
			}
		}
		
		 int z; int pos;
		for(int i=0;i<=sltID;i++) {
			z = buff.getInt();
			if (i == sltID) {
				pos = buff.position();
				record.writeToBuffer(buff, pos);
				 fin = new Rid(PID, sltID);
			}
		}
		
		
		
		
		if(isItFull(PID)) {
			byte[] h_array = BM.getPage(relInfo.getHeaderPageId());
			ByteBuffer h_Buffer = ByteBuffer.wrap(h_array);
			
			
			
			
			PageId firstFULL = readPageIdFromPageBuffer(h_Buffer, false);
			
			
			
			this.writePageIdToPageBuffer(PID, h_Buffer, false);
			BM.FreePage(relInfo.getHeaderPageId(), 1);
			
			ByteBuffer firstFull_Buffer = byteToBuffer(BM.getPage(firstFULL)); // Chargement de la premiere page rempli
			
			
			
			
		}	
		BM.FreePage(PID, 1);
		return fin;	
	
		
	}
	
	
	private boolean isItFull(PageId PID) throws IOException {
		
		boolean trv = false;
		BufferManager BM = BufferManager.getInstance();
		byte[] a = BM.getPage(PID);
		ByteBuffer buff = ByteBuffer.wrap(a);
		BM.FreePage(PID, 0);
		buff.position(16);
		int s;
		for(int i = 0 ;i<buff.capacity() / 4;i++) {
			s = buff.getInt();
			if(s == 0) {
				trv = true;
				break;
			}
		}
		
		return trv;
	}
	
	
	private ByteBuffer byteToBuffer(byte[] a) {
		return ByteBuffer.wrap(a);
	}
	
	
	public ArrayList<Record> getRecordsInDataPage(RelationInfo relinfo,PageId PID){
		
		return null;
	}
	
	public Rid InsertRecordIntoRelation(RelationInfo relinfo, Record record) {
		return null;
	}
	
	public ArrayList<Record> getAllRecords(RelationInfo relinfo){
		return null;
	}
	
	
}

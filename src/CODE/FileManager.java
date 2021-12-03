package CODE;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;


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
		

		if(!isNotFull(PID,relInfo)) {
			byte[] h_array = BM.getPage(relInfo.getHeaderPageId());
			ByteBuffer h_Buffer = ByteBuffer.wrap(h_array);
			PageId firstFULL = readPageIdFromPageBuffer(h_Buffer, false);
			this.writePageIdToPageBuffer(PID, h_Buffer, false);
			BM.FreePage(relInfo.getHeaderPageId(), 1);
			ByteBuffer firstFull_Buffer = byteToBuffer(BM.getPage(firstFULL)); // Chargement de la premiere page remplie
		}
		//TODO
		BM.FreePage(PID, 1);
		return fin;
	}
	
	
	public boolean isNotFull(PageId PID,RelationInfo rel) throws IOException {
		boolean trv = false;
		BufferManager BM = BufferManager.getInstance();
		ByteBuffer buff = byteToBuffer(BM.getPage(PID));
		BM.FreePage(PID, 0);
		buff.position(16);
		int s=-1;
		for(int i = 0 ;i<rel.getSlotCount();i++) {
			s = buff.getInt();
			if(s == 0) {
				trv = true;
				break;
			}
		}
		return trv;
	}
	
	
	public ByteBuffer byteToBuffer(byte[] a) {
		return ByteBuffer.wrap(a);
	}
	
	
	public ArrayList<Record> getRecordsInDataPage(RelationInfo relinfo,PageId PID) throws IOException{
		ArrayList<Record> listRecords = new ArrayList<>();
		ArrayList <Integer>  ID_RECORDs = new ArrayList<>();
		BufferManager BM = BufferManager.getInstance();
		ByteBuffer buff = byteToBuffer(BM.getPage(PID));
		BM.FreePage(PID,0);
		buff.position(16);
		int s;
		for(int i=0;i<relinfo.getSlotCount();i++) {
			s = buff.getInt();
			if(s==1) {
				ID_RECORDs.add(i);
			}
		}
		buff.position(16+(4*relinfo.getSlotCount()));
		for(int i = 0;i<relinfo.getSlotCount();i++) {
			if (ID_RECORDs.contains(i)) {
				Record rec = new Record(relinfo);
				rec.readFromBuffer(buff, 16 + (4 * relinfo.getSlotCount()) + (i * relinfo.getRecordSize()));
				listRecords.add(rec);
			}
		}
		return listRecords;
	}
	
	public Rid InsertRecordIntoRelation(RelationInfo relinfo, Record record) throws IOException {
		PageId headerPage = relinfo.getHeaderPageId();
		PageId freePage = INSTANCE.getFreeDataPageId(relinfo);
		Rid recordId = INSTANCE.writeRecordToDataPage(relinfo,record,freePage);
		return recordId;
	}


	public ArrayList<Record> getAllRecords(RelationInfo relinfo) throws IOException {
		ArrayList<Record> listRecord = new ArrayList<>();
		BufferManager BM = BufferManager.getInstance();

		PageId headerPage = relinfo.getHeaderPageId();
		ByteBuffer headerPage_Buffer = INSTANCE.byteToBuffer(BM.getPage(headerPage));
		PageId firstEmpty = INSTANCE.readPageIdFromPageBuffer(headerPage_Buffer,true); //Premiere page pleine
		PageId pid2 = INSTANCE.readPageIdFromPageBuffer(headerPage_Buffer,false); //Premiere page pas pleine
		BM.FreePage(headerPage,0);

		ArrayList<Record> tmpPid1 = new ArrayList<>();
		ArrayList<Record> tmpPid2 = new ArrayList<>();
		ArrayList<Record> ListOfRecords = new ArrayList<>();


		ByteBuffer buffer = INSTANCE.byteToBuffer(BM.getPage(firstEmpty)); // Chargement de la premiere dataPage
		int i=0;



		//ListOfRecords.addAll(tmpPid1);
		//ListOfRecords.addAll(tmpPid2);
		return null;




	}
	
	
}

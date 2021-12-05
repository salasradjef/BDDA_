package CODE;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;


public class FileManager {

	private static FileManager INSTANCE;

	public static FileManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new FileManager();
		}
		return INSTANCE;
	}


	public PageId readPageIdFromPageBuffer(ByteBuffer buff, boolean first) {
		/*Tested*/

		if (first) {
			buff.position(0);
			return new PageId(buff.getInt(), buff.getInt());
		} else {
			buff.position(8);
			return new PageId(buff.getInt(), buff.getInt());
		}
	}

	public void writePageIdToPageBuffer(PageId PID, ByteBuffer buff, boolean first) {
		/*Tested*/
		if (first) {
			buff.position(0);
			buff.putInt(Integer.valueOf(PID.getFileIdx()));
			buff.putInt(Integer.valueOf(PID.getPageIdx()));
		} else {
			buff.position(8);
			buff.putInt(Integer.valueOf(PID.getFileIdx()));
			buff.putInt(Integer.valueOf(PID.getPageIdx()));
		}

	}

	public PageId createHeaderPage() throws IOException {
		/*Tested*/
		DiskManager disk = DiskManager.getInstance();
		PageId header = disk.AllocPage();
		PageId fac = new PageId(-1, 0);
		BufferManager BM = BufferManager.getInstance();

		byte[] a = BM.getPage(header);
		ByteBuffer tmp = ByteBuffer.wrap(a);
		writePageIdToPageBuffer(fac, tmp, true);
		writePageIdToPageBuffer(fac, tmp, false);
		BM.FreePage(header, 1);

		return header;
	}


	public PageId addDataPage(RelationInfo relInfo) throws IOException {
		/*Tested*/
		DiskManager disk = DiskManager.getInstance();
		BufferManager BM = BufferManager.getInstance();

		PageId pageV = disk.AllocPage();

		//lecture de la headerPage
		PageId headerPage = relInfo.getHeaderPageId();
		byte[] a = BM.getPage(headerPage); //Charger HeaderPage
		ByteBuffer tmp = ByteBuffer.wrap(a);
		writePageIdToPageBuffer(pageV, tmp, true);
		BM.FreePage(headerPage, 1);

		byte[] b = BM.getPage(pageV); // Charger notre nouvelle page
		ByteBuffer tmp2 = ByteBuffer.wrap(b);
		 //ecrire dans le premierPageId de la headerPage le PID de notre nouvelle

		writePageIdToPageBuffer(headerPage, tmp2, true);
		writePageIdToPageBuffer(headerPage, tmp2, false);
		tmp2.position(0);
		for(int i=0;i< relInfo.getSlotCount();i++){
			tmp2.put((byte)0);
		}
		BM.FreePage(pageV, 1);
		return pageV;
	}




	public PageId getFreeDataPageId(RelationInfo rel) throws IOException {
		/*Tested*/
		BufferManager BM = BufferManager.getInstance();
		PageId headerPage = rel.getHeaderPageId();
		ByteBuffer headerPageBuffer = byteToBuffer(BM.getPage(headerPage));
		PageId firstEmpty = INSTANCE.readPageIdFromPageBuffer(headerPageBuffer,true);
		BM.FreePage(headerPage,0);

		if(firstEmpty.getFileIdx() == -1 && firstEmpty.getPageIdx() ==0){
			PageId freePage= INSTANCE.addDataPage(rel);
			ByteBuffer freePageBuffer = byteToBuffer(BM.getPage(freePage));
			INSTANCE.writePageIdToPageBuffer(headerPage,freePageBuffer,true);
			PageId fact = new PageId(-1,0);
			INSTANCE.writePageIdToPageBuffer(fact,freePageBuffer,false);
			BM.FreePage(freePage,1);
			headerPageBuffer = byteToBuffer(BM.getPage(headerPage));
			writePageIdToPageBuffer(freePage,headerPageBuffer,true);
			BM.FreePage(headerPage,1);
			return freePage;
		}else {
			return  firstEmpty;
		}


	}

	public Rid writeRecordToDataPage(RelationInfo relInfo, Record record, PageId PID) throws IOException {
		/*Tested*/
		BufferManager BM = BufferManager.getInstance(); //BufferManager
		ByteBuffer buff = byteToBuffer(BM.getPage(PID));

		buff.position(16);
		byte z = 0 ; int sltVide = -1;
		for(int i=0;i<relInfo.getSlotCount();i++) {
			z = buff.get();
			if(z == 0){
				sltVide = i;
				buff.position(buff.position()-1);
				buff.put((byte)1);
				break;
			}
		} //Trouver la case vide du BitMap et la mettre a 1
		Rid rid = null;
		buff.position(16+ relInfo.getSlotCount());
		int pos=-1;
		for(int i = 0;i< relInfo.getSlotCount();i++){
			if(i == sltVide){
				pos = buff.position();
				record.writeToBuffer(buff,pos);
				rid = new Rid(PID, sltVide);
				break;
			}
			buff.position(buff.position() + relInfo.getRecordSize());

		}



		//Voir si la page est devenue pleine
		buff.position(16); boolean trv = true;
		byte s = -1;
		for(int i=0;i< relInfo.getSlotCount();i++){
			s = buff.get();
			if(s == 0){
				trv = false;
				break;
			}
		}
		BM.FreePage(PID, 1);


		//Deplacement ou pas de la page vers la liste des pages pleines

		if (trv) {

			ByteBuffer headerPage_buff = byteToBuffer(BM.getPage(relInfo.getHeaderPageId()));
			PageId firstFull = readPageIdFromPageBuffer(headerPage_buff, true);
			writePageIdToPageBuffer(PID, headerPage_buff, false);
			BM.FreePage(relInfo.getHeaderPageId(), 1);

			ByteBuffer firstFull_buff = byteToBuffer(BM.getPage(firstFull));
			writePageIdToPageBuffer(PID, firstFull_buff, true);
			BM.FreePage(firstFull, 1);

			ByteBuffer PID_buff = byteToBuffer(BM.getPage(PID));
			writePageIdToPageBuffer(firstFull, PID_buff, false);
			writePageIdToPageBuffer(relInfo.getHeaderPageId(), PID_buff, true);

			BM.FreePage(PID, 1);
		}
		BM.FreePage(PID, 1);
		return rid;
	}


	public ByteBuffer byteToBuffer(byte[] a) {
		return ByteBuffer.wrap(a);
	}


	public ArrayList<Record> getRecordsInDataPage(RelationInfo relinfo, PageId PID) throws IOException {
		ArrayList<Record> listRecords = new ArrayList<>();
		ArrayList<Integer> ID_RECORDs = new ArrayList<>();
		BufferManager BM = BufferManager.getInstance();
		ByteBuffer buff = byteToBuffer(BM.getPage(PID));
		BM.FreePage(PID, 0);
		buff.position(16);
		byte s;

		for (int i = 0; i < relinfo.getSlotCount(); i++) {
			s = buff.get();
			if (s == 1) {
				ID_RECORDs.add(i);
			}
		}
		buff.position(16 +  relinfo.getSlotCount());

		for (int i = 0; i < relinfo.getSlotCount(); i++) {
			if (ID_RECORDs.contains(i)) {

				String[] values = new String[relinfo.getNbr_col()];
				Record rec = new Record(relinfo,values);

				rec.readFromBuffer(buff, 16 +  relinfo.getSlotCount() + (i * relinfo.getRecordSize()));

				listRecords.add(rec);
			}
		}

	return listRecords;
	}


	/*----------------------------------------------------API--------------------------------------*/
	public Rid InsertRecordIntoRelation(RelationInfo relinfo, Record record) throws IOException {
		/*Tested*/
		BufferManager BM = BufferManager.getInstance();
		PageId freePage = INSTANCE.getFreeDataPageId(relinfo);
		Rid recordId = INSTANCE.writeRecordToDataPage(relinfo, record, freePage);
		BM.FlushAll();
		return recordId;
	}


	public ArrayList<Record> getAllRecords(RelationInfo relinfo) throws IOException {

		ArrayList<Record> listRecord = new ArrayList<>();
		BufferManager BM = BufferManager.getInstance();
		BM.FlushAll();
		PageId headerPage = relinfo.getHeaderPageId();
		ByteBuffer headerPage_Buffer = INSTANCE.byteToBuffer(BM.getPage(headerPage));
		PageId firstEmpty = INSTANCE.readPageIdFromPageBuffer(headerPage_Buffer, true); //Premiere page pleine
		PageId firstFull = INSTANCE.readPageIdFromPageBuffer(headerPage_Buffer, false);
		BM.FreePage(headerPage, 0);

		ArrayList<Record> tmpPid1 = new ArrayList<>();
		ArrayList<Record> tmpPid2 = new ArrayList<>();
		ArrayList<Record> ListOfRecords = new ArrayList<>();



		//*Boucle qui permet de charger la listes des records qui se trouve dans les pages non pleines*//*
		while (true) {
			ByteBuffer buffer = INSTANCE.byteToBuffer(BM.getPage(firstEmpty));
			PageId next = readPageIdFromPageBuffer(buffer, false);
			tmpPid1.addAll(getRecordsInDataPage(relinfo, firstEmpty));
			BM.FreePage(firstEmpty, 0);
			firstEmpty = next;

			if (firstEmpty.getFileIdx() == 0 && firstEmpty.getPageIdx() == -1) {
				break;
			}
		}
		//*Boucle qui permet de charger la listes des records qui se trouve dans les pages pleines*//*
		while (true) {
			ByteBuffer buff2 = INSTANCE.byteToBuffer(BM.getPage(firstFull));
			PageId next2 = readPageIdFromPageBuffer(buff2, false);
			tmpPid2.addAll(getRecordsInDataPage(relinfo, firstFull));
			BM.FreePage(firstFull, 0);
			firstFull = next2;

			if (firstFull.getFileIdx() == -1 && firstFull.getPageIdx() == 0) {
				break;
			}

		}


		tmpPid1.addAll(tmpPid2);

		return tmpPid1;

	}


}
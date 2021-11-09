package miniSGBD;

import java.io.IOException;

public class BufferManager {
	
	private static BufferManager INSTANCE;
	private Frame[] bpool;
	private int time;
	
	public static BufferManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new BufferManager();
			
		}
		
		return INSTANCE;
	}



	private BufferManager() {
		bpool = new Frame[DBParams.frameCount];
		time =0;
		for(int i=0;i<DBParams.frameCount;i++) {
			bpool[i] = new Frame();
		}
	}
	
	
	public byte[] getPage(PageId PID) throws IOException {
		
		byte[] b = new byte[DBParams.pageSize];
		int cases = DBParams.frameCount;
		int j = 0;
		DiskManager disk = DiskManager.getInstance();
		for (int i = 0; i < cases; i++) {
			if (bpool[i].getPID() != null) {
				if (bpool[i].getPID() == PID) {
					bpool[i].setPin_count(bpool[i].getPin_count() + 1);

					return bpool[i].getBuff();
				}
			}
		}
		for (int i = 0; i < cases; i++) {
			if (bpool[i].getPID() == null) {
				bpool[i].setPin_count(bpool[i].getPin_count() + 1);
				disk.ReadPage(PID, b);
				bpool[i].setBuff(b);
				bpool[i].setPID(PID);
				return bpool[i].getBuff();
			}
		}
		int cpt = bpool[0].getTemps_free();
		for (int i = 1; i < cases; i++) {
			if (cpt < bpool[i].getTemps_free()) {
				cpt = bpool[i].getTemps_free();
				j=i;
			}
		}
		if(bpool[j].getPin_count()==0 && bpool[j].getDirty()==0) {
			bpool[j].setPin_count(bpool[j].getPin_count() + 1);
			disk.ReadPage(PID, b);
			bpool[j].setBuff(b);
			return bpool[j].getBuff();

		}
		if(bpool[j].getPin_count()==0 && bpool[j].getDirty()!=0) {
			disk.WritePage(bpool[j].getPID(), bpool[j].getBuff());
			bpool[j].setPin_count(bpool[j].getPin_count() + 1);
			disk.ReadPage(PID, b);
			bpool[j].setBuff(b);
			return bpool[j].getBuff();
		}
		return null;

	}
	
	
	
	
	public void FreePage(PageId PID, int valdirty) {
		int cases = DBParams.frameCount;
		for (int i = 0; i < cases; i++) {
			if (bpool[i].getPID()==PID) {
				bpool[i].setDirty(valdirty);
				bpool[i].setPin_count(bpool[i].getPin_count()-1);
				time++;
				bpool[i].setTemps_free(time);
			}
		}
	}
	
	public void FlushAll() throws IOException {
		int cases = DBParams.frameCount;
		DiskManager disk = DiskManager.getInstance();
		for (int i = 0; i < cases; i++) {
			if(bpool[i].getDirty()!=0) {
				disk.WritePage(bpool[i].getPID(), bpool[i].getBuff());
				bpool[i].setDirty(0);
				bpool[i].setPin_count(0);
				bpool[i].setBuff(new byte[DBParams.pageSize]);
				bpool[i].setTemps_free(0);
			}else {
				bpool[i].setBuff(new byte[DBParams.pageSize]);
				bpool[i].setPin_count(0);
				bpool[i].setTemps_free(0);
			}
		}
	}
	
	
	
	public Frame[] getBpool() {
		return bpool;
	}



	public void setBpool(Frame[] bpool) {
		this.bpool = bpool;
	}
	
}
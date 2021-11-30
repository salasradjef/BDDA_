package CODE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;


public class DiskManager  {
	
	private static DiskManager INSTANCE;
	
	
	private DiskManager() {
	
	}
	
	public static DiskManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new DiskManager();
		}
		
		return INSTANCE;
		
	}
	
	
	//methode qui permet d'allouer une page
	public PageId AllocPage() throws IOException {
		String DBPath = DBParams.DBPath;
		File f= new File(DBPath);
		
		//filtre qui permet de recuperer tout les fichiers qui se termine avec df et leurs taille est inf�rieur a 16348o
		FilenameFilter filter1 = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				File file = new File(dir,name);
				return name.endsWith(".df") && file.length()<16348 ;
			}
			
		};

		//filtre qui permet de recuperer tout les fichiers qui se termine avec df 

		FilenameFilter filter2 = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.endsWith(".df");
			}
			
		};
		
		
		//
		String[] paths = f.list(filter1);
		
		
		
		
		if(paths.length == 0) {
			
			String[] lasts = f.list(filter2);
			if(lasts.length == 0) {
				// aucun fichier dans ma db
				File df = new File(DBPath+"\\"+"F0.df");
				RandomAccessFile rf= new RandomAccessFile(df,"rw");
				rf.setLength(4096);
				rf.close();
				PageId pid = new PageId(0,0);
				return pid;
				
			}else {
				
				//aucun fichier pas complet
				String last = lasts[lasts.length-1];
				lasts = last.split("");
				
				int FileId = Integer.parseInt(lasts[1])+1;
				String filename = "F" + FileId + ".df";
				File df = new File(DBPath+"\\"+filename);
				RandomAccessFile rf= new RandomAccessFile(df,"rw");
				rf.setLength(4096);
				rf.close();
				PageId pid = new PageId(FileId, 0);
				return pid;
				
				
				
			}
			
			
		}else {
			
			String[] fileId = paths[0].split("");
			
			File df = new File(DBPath+"\\"+paths[0]);
			
			int FileId = Integer.parseInt(fileId[1]);
			RandomAccessFile rf = new RandomAccessFile(df, "rw");
			rf.setLength(df.length()+4096);
			rf.close();
			if(df.length() == 4096) {
				PageId pid = new PageId(FileId, 1);
				return pid;
			}
			if(df.length() == 8192) {
				PageId pid = new PageId(FileId, 2);
				return pid;
			}
			
			if(df.length() == 12288 || df.length() == 16384) {
				PageId pid = new PageId(FileId, 3);
				return pid;
			}
		}
		return null;
		

	}
	//methode qui permet de lire une page et de recuper ses infos dans un buff
	public  void ReadPage(PageId pageId,byte[] buff) throws IOException {
		int pageID = pageId.getPageIdx();
		int fileId = pageId.getFileIdx();
		String DBPath = DBParams.DBPath;
		String filename = "F" + fileId + ".df"; 
		File df = new File(DBPath+"\\"+filename);
		
		RandomAccessFile rf = new RandomAccessFile(df, "r");
		
		if(pageID==0) {
			byte[] bytes = new byte[DBParams.pageSize];
			rf.read(bytes);
			rf.close();
			
			for(int i=0; i<buff.length;i++) {
				buff[i] = bytes[i];
			}
			
			
		}
		
		if(pageID ==1) {
			rf.skipBytes(4096);
			byte[] bytes = new byte[DBParams.pageSize];
			rf.read(bytes);
			rf.close();
			for(int i=0; i<bytes.length;i++) {
				buff[i] = bytes[i];
			}}
		
		if(pageID == 2) {
			rf.skipBytes(8192);
			byte[] bytes = new byte[DBParams.pageSize];
			rf.read(bytes);
			rf.close();
			for(int i=0; i<bytes.length;i++) {
				buff[i] = bytes[i];
			}
		}
		
		if(pageID ==3 ) {
			rf.skipBytes(12287);
			byte[] bytes = new byte[DBParams.pageSize];
			rf.read(bytes);
			rf.close();
			for(int i=0; i<bytes.length;i++) {
				buff[i] = bytes[i];
			}
		}
	}
	
	
	//methode qui permet d'ecrire dans une page
	public void WritePage(PageId pageId,byte[] buff) throws IOException {
		int pageID = pageId.getPageIdx();
		int fileId = pageId.getFileIdx();
		String DBPath = DBParams.DBPath;
		String filename = "F" + fileId + ".df"; 
		File df = new File(DBPath+"\\"+filename);
		
		RandomAccessFile rf = new RandomAccessFile(df, "rw");
		
		if(pageID==0) {
			
			rf.write(buff);
			rf.close();
			}
		
		if(pageID ==1) {
			rf.skipBytes(4096);
			rf.write(buff);
			rf.close();
			
		}
		
		if(pageID == 2) {
			rf.skipBytes(8192);
			rf.write(buff);
			rf.close();
		}
		
		if(pageID ==3 ) {
			rf.skipBytes(12287);
			rf.write(buff);
			rf.close();
			
		}
		
	}
	
	
	
	public void clean_all() {
		File f= new File(DBParams.DBPath);
		for(File file: f.listFiles()) 
		    if (!file.isDirectory()) 
		        file.delete();
	}
}

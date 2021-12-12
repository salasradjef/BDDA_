package CODE;


import java.io.Serializable;

public class RelationInfo implements Serializable {
	private String name;
	private int nbr_col;
	private ColInfo[] col;
	private PageId headerPageId;
	private int recordSize;
	private int slotCount;
	
	
	
	
	public RelationInfo(String name, int nbr_col, ColInfo[] col, PageId headerPageId) {
		this.name = name;
		this.nbr_col = nbr_col;
		this.col = col;
		this.headerPageId = headerPageId;
		
		//Calcul du recordSize
		for(int i=0;i<nbr_col;i++) {
			String type = getCol()[i].getCol_type();
			String[] pw = type.split("string");

			if(type.equals("int") || type.equals("float")) {
				this.recordSize += 4;
			}else if(type.contains("string")) {
				for(int j=0;j<Integer.parseInt(pw[1]);j++) {
					this.recordSize += 2;
				}
			}

		}
		
		//Calcul du nombre de cases
		int sizeOFPageId = 8;
		this.slotCount = (DBParams.pageSize - sizeOFPageId*2) / (this.recordSize+1);
		
		
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNbr_col() {
		return nbr_col;
	}
	public void setNbr_col(int nbr_col) {
		this.nbr_col = nbr_col;
	}
	public ColInfo[] getCol() {
		return col;
	}
	public void setCol(ColInfo[] col) {
		this.col = col;
	}
	public PageId getHeaderPageId() {
		return headerPageId;
	}
	public void setHeaderPageId(PageId headerPageId) {
		this.headerPageId = headerPageId;
	}
	public int getRecordSize() {
		return recordSize;
	}
	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}
	public int getSlotCount() {
		return slotCount;
	}
	public void setSlotCount(int slotCount) {
		this.slotCount = slotCount;
	}
}

package miniSGBD;

public class RelationInfo {
	private String name;
	private int nbr_col;
	private ColInfo[] col;
	private PageId headerPageId;
	private int recordSize;
	private int slotCount;
	
	
	
	
	public RelationInfo(String name, int nbr_col, ColInfo[] col, PageId headerPageId, int recordSize, int slotCount) {
		this.name = name;
		this.nbr_col = nbr_col;
		this.col = col;
		this.headerPageId = headerPageId;
		this.recordSize = recordSize;
		this.slotCount = slotCount;
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
}
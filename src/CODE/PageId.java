package CODE;


import java.io.Serializable;

public class PageId implements Serializable {
	private int FileIdx;
	private int PageIdx;
	
	public PageId(int fileIdx, int PageIdx) {
		this.FileIdx = fileIdx;
		this.PageIdx = PageIdx;
	}

	public int getFileIdx() {
		return FileIdx;
	}
	public int getPageIdx() {
		return PageIdx;
	}
	
	@Override
	public String toString() {
		return "(" + getFileIdx() +"," + getPageIdx() +")";
	}
	
}

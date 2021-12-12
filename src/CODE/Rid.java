package CODE;


public class Rid {
	private PageId Rid;
	private int slotIdx;
	
	public Rid(PageId rid, int slotIdx) {
		this.Rid = rid;
		this.slotIdx = slotIdx;
	}
	public PageId getRid() {
		return Rid;
	}
	public void setRid(PageId rid) {
		Rid = rid;
	}
	public int getSlotIdx() {
		return slotIdx;
	}
	public void setSlotIdx(int slotIdx) {
		this.slotIdx = slotIdx;
	}
	
	
	
}

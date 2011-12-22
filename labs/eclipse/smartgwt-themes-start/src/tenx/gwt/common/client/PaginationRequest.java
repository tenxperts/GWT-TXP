package tenx.gwt.common.client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PaginationRequest implements Serializable {
	private int startRow;
	private int endRow;

	PaginationRequest() {
		super();
	}
	
	public PaginationRequest(int startRow, int endRow) {
		super();
		this.startRow = startRow;
		this.endRow = endRow;
	}
	
	public int getStartRow() {
		return startRow;
	}
	public int getEndRow() {
		return endRow;
	}

	@Override
	public String toString() {
		return "PaginationRequest [startRow=" + startRow + ", endRow=" + endRow
				+ "]";
	}
	
}

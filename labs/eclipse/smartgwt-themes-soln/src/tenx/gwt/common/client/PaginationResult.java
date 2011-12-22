package tenx.gwt.common.client;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class PaginationResult<T> implements Serializable {
	int totalSize;
	List<T> currentPage;

	PaginationResult() {
	}
	
	public PaginationResult(int totalSize, List<T> currentPage) {
		super();
		this.totalSize = totalSize;
		this.currentPage = currentPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public List<T> getCurrentPage() {
		return currentPage;
	}

	@Override
	public String toString() {
		return "PaginationResult [totalSize=" + totalSize + ", currentPageSize="
				+ currentPage.size() + "]";
	}

	
}

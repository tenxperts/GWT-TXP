package tenx.gwt.portfolio.client.rpc;

import java.util.Collection;
import java.util.List;

import tenx.gwt.common.client.PaginationRequest;
import tenx.gwt.common.client.PaginationResult;
import tenx.gwt.portfolio.shared.Portfolio;
import tenx.gwt.portfolio.shared.PortfolioEntry;
import tenx.gwt.portfolio.shared.PortfolioLite;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PortfolioServiceAsync {

	void getPortfolioDetail(Integer id, AsyncCallback<Portfolio> callback);

	void getPortfoliosList(AsyncCallback<Collection<PortfolioLite>> callback);

	void addEntry(Integer portfolioId, PortfolioEntry newEntry,
			AsyncCallback<Void> callback);

	void removeEntries(Integer portfolioId, List<Integer> entryIds,
			AsyncCallback<Void> callback);

	void getPortfolioEntries(Integer portfolioId,
			String sortColumn, boolean ascending, PaginationRequest paginationRequest,
			AsyncCallback<PaginationResult<PortfolioEntry>> callback);

	void updateEntry(Integer portfolioId, PortfolioEntry newEntry,
			AsyncCallback<Void> callback);
}

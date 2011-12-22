package tenx.gwt.portfolio.client.rpc;

import java.util.Collection;
import java.util.List;

import tenx.gwt.common.client.PaginationRequest;
import tenx.gwt.common.client.PaginationResult;
import tenx.gwt.portfolio.shared.Portfolio;
import tenx.gwt.portfolio.shared.PortfolioEntry;
import tenx.gwt.portfolio.shared.PortfolioLite;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("portfolioService")
public interface PortfolioService extends RemoteService {
	Collection<PortfolioLite> getPortfoliosList();
	Portfolio getPortfolioDetail(Integer id) throws PortfolioNotFoundException;
	
	PaginationResult<PortfolioEntry> getPortfolioEntries(Integer portfolioId,
			String sortColumn, boolean ascending,
			PaginationRequest paginationRequest) throws PortfolioNotFoundException;
	
	void addEntry(Integer portfolioId,PortfolioEntry newEntry) throws PortfolioNotFoundException;
	void updateEntry(Integer portfolioId,PortfolioEntry newEntry) throws PortfolioNotFoundException;
	void removeEntries(Integer portfolioId,List<Integer> entryIds) throws PortfolioNotFoundException;
}

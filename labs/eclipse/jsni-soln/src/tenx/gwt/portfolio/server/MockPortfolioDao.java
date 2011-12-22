package tenx.gwt.portfolio.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tenx.gwt.portfolio.shared.Portfolio;
import tenx.gwt.portfolio.shared.PortfolioEntry;
import tenx.gwt.portfolio.shared.PortfolioLite;

public class MockPortfolioDao implements PortfolioDao {
	private Map<Integer,Portfolio> portfolios = new HashMap<Integer, Portfolio>();

	public MockPortfolioDao() {
		initializeDummyData(portfolios);
	}
	
	@Override
	public Collection<PortfolioLite> getPortfolioList() {
		return convertToPortfolioLite(portfolios.values());
	}
	
	@Override
	public Portfolio findById(Integer id) {
		return portfolios.get(id);
	}
	
	

	@Override
	public void update(Portfolio portfolio) {
		portfolios.put(portfolio.getId(),portfolio);
	}

	private Collection<PortfolioLite> convertToPortfolioLite(
			Collection<Portfolio> portfolios) {
		List<PortfolioLite> result = new ArrayList<PortfolioLite>();
		for(Portfolio portfolio:portfolios) {
			result.add(new PortfolioLite(portfolio.getId(),portfolio.getName()));
		}
		return result;
	}


	private void initializeDummyData(Map<Integer, Portfolio> portfolios) {
		int portFolioId = 1;
		int portFolioEntryId = 1;
		portfolios.put(portFolioId, new Portfolio(portFolioId++, "Long term")
							.addEntry(new PortfolioEntry(portFolioEntryId++, "INFY",10, 25000,22500, -2500))
							.addEntry(new PortfolioEntry(portFolioEntryId++, "RIL",10, 10000,9000, -1000))
							);
		portfolios.put(portFolioId, new Portfolio(portFolioId++, "Short term")
							.addEntry(new PortfolioEntry(portFolioEntryId++, "DLF",5, 25000,22500, -2500))
							.addEntry(new PortfolioEntry(portFolioEntryId++, "ITC",10, 20000,22500, 2500))
							);
		
	}

}

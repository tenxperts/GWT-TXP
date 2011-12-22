package tenx.gwt.portfolio.server;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import tenx.gwt.portfolio.client.rpc.PortfolioNotFoundException;
import tenx.gwt.portfolio.client.rpc.PortfolioService;
import tenx.gwt.portfolio.shared.Portfolio;
import tenx.gwt.portfolio.shared.PortfolioEntry;
import tenx.gwt.portfolio.shared.PortfolioLite;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PortfolioServiceImpl extends RemoteServiceServlet implements
		PortfolioService {

	private PortfolioDao portfolioDao = new MockPortfolioDao();

	@Override
	public Collection<PortfolioLite> getPortfoliosList() {
		return portfolioDao.getPortfolioList();
	}

	@Override
	public Portfolio getPortfolioDetail(Integer id)
			throws PortfolioNotFoundException {
		Portfolio portfolio = portfolioDao.findById(id);
		if (portfolio == null) {
			throw new PortfolioNotFoundException();
		}

		return portfolio.setAsOnDate(new Date());
	}

	@Override
	public void addEntry(Integer portfolioId, PortfolioEntry newEntry)
			throws PortfolioNotFoundException {
		portfolioDao.update(getPortfolioDetail(portfolioId).addEntry(newEntry));

	}

	@Override
	public void removeEntries(Integer portfolioId, List<Integer> entryIds)
			throws PortfolioNotFoundException {
		Portfolio portfolio = getPortfolioDetail(portfolioId);
		for (Integer entryId : entryIds) {
			portfolio.removeEntry(entryId);
		}
		portfolioDao.update(portfolio);
	}

	public void setPortfolioDao(PortfolioDao portfolioDao) {
		this.portfolioDao = portfolioDao;
	}

}

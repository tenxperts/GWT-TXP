package tenx.gwt.portfolio.server;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import tenx.gwt.common.client.PaginationRequest;
import tenx.gwt.common.client.PaginationResult;
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
	public Portfolio getPortfolioDetail(Integer id) throws PortfolioNotFoundException {
		Portfolio portfolio = portfolioDao.findById(id);
		if (portfolio == null) {
			throw new PortfolioNotFoundException();
		}
		
		return portfolio.setAsOnDate(new Date());
	}
	

	@Override
	public PaginationResult<PortfolioEntry> getPortfolioEntries(
			Integer portfolioId,final String sortColumn, final boolean ascending, PaginationRequest paginationRequest) throws PortfolioNotFoundException {
		
		Portfolio portfolio = getPortfolioDetail(portfolioId);
		List<PortfolioEntry> entries = portfolio.getEntries();
		sort(sortColumn, ascending, entries);
		
		int startRow = paginationRequest.getStartRow() > 0 ? paginationRequest.getStartRow() : 0;
		int endRow = paginationRequest.getEndRow() > entries.size() ? entries.size() : paginationRequest.getEndRow();
		
		List<PortfolioEntry> currentPage = new ArrayList<PortfolioEntry>();
		for(int i=startRow;i<endRow;i++) {
			currentPage.add(entries.get(i));
		}
		
		return new PaginationResult<PortfolioEntry>(entries.size(),currentPage);
	}


	// a quick hack to test the UI
	private void sort(final String sortColumn, final boolean ascending,
			List<PortfolioEntry> entries) {
		Collections.sort(entries,new Comparator<PortfolioEntry>() {
			@Override
			public int compare(PortfolioEntry o1, PortfolioEntry o2) {
				try {
					Comparable prop1 = (Comparable) PropertyUtils.getProperty(o1, sortColumn);
					Object prop2 = PropertyUtils.getProperty(o2, sortColumn);
					
					int result = prop1.compareTo(prop2);
					return ascending ? result : result * -1;
				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				} 
			}
		});
	}


	@Override
	public void addEntry(Integer portfolioId, PortfolioEntry newEntry) throws PortfolioNotFoundException {
		portfolioDao.update(getPortfolioDetail(portfolioId).addEntry(newEntry));

	}

	@Override
	public void removeEntries(Integer portfolioId, List<Integer> entryIds) throws PortfolioNotFoundException {
		Portfolio portfolio = getPortfolioDetail(portfolioId);
		for(Integer entryId:entryIds) {
			portfolio.removeEntry(entryId);
		}
		portfolioDao.update(portfolio);
	}
}

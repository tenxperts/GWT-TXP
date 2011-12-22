package tenx.gwt.portfolio.server;

import java.util.Collection;

import tenx.gwt.portfolio.shared.Portfolio;
import tenx.gwt.portfolio.shared.PortfolioLite;

public interface PortfolioDao {

	Collection<PortfolioLite> getPortfolioList();

	Portfolio findById(Integer id);
	
	void update(Portfolio portfolio);
	
}

package tenx.gwt.portfolio.server.web;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tenx.gwt.portfolio.client.rpc.PortfolioNotFoundException;
import tenx.gwt.portfolio.client.rpc.PortfolioService;
import tenx.gwt.portfolio.shared.Portfolio;
import tenx.gwt.portfolio.shared.PortfolioLite;

@Controller
public class PortfolioController {
	
	private PortfolioService portfolioService;

	@RequestMapping(value="/portfolios",method=RequestMethod.GET)
	public @ResponseBody Collection<PortfolioLite> listPortfolios() {
		return portfolioService.getPortfoliosList();
	}

	@RequestMapping(value="/portfolios/{id}",method=RequestMethod.GET)
	public @ResponseBody Portfolio getPortfolio(@PathVariable("id") int id) throws PortfolioNotFoundException {
		return portfolioService.getPortfolioDetail(id);
	}

	public void setPortfolioService(PortfolioService portfolioService) {
		this.portfolioService = portfolioService;
	}
}

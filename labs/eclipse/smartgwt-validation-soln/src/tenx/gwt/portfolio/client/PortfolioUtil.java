package tenx.gwt.portfolio.client;

import java.util.Arrays;
import java.util.List;

import tenx.gwt.portfolio.shared.PortfolioEntry;

public class PortfolioUtil {
	public static List<PortfolioEntry> getDummyData() {
		PortfolioEntry[] portfolioEntries = new PortfolioEntry[] {
				new PortfolioEntry(1,"INFY", 10,25000,23500,-1500),
				new PortfolioEntry(2,"RIL", 5,5000,5100,100),
		};
		return Arrays.asList(portfolioEntries);
	}
}

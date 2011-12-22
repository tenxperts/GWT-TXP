package tenx.gwt.portfolio.client.list;

import java.util.Collection;

import tenx.gwt.portfolio.client.common.View;
import tenx.gwt.portfolio.shared.PortfolioLite;

public interface PortfolioListView extends View<PortfolioListPresenter> {
	public void setData(Collection<PortfolioLite> data);
}

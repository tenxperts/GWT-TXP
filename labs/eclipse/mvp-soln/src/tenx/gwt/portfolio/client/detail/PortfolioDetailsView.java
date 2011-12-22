package tenx.gwt.portfolio.client.detail;

import com.google.gwt.user.client.ui.HasText;

import tenx.gwt.portfolio.client.common.View;
import tenx.gwt.portfolio.shared.Portfolio;


public interface PortfolioDetailsView extends View<PortfolioDetailsPresenter> {
	public void setData(Portfolio portfolio);
	public HasText getStatusBar();
}

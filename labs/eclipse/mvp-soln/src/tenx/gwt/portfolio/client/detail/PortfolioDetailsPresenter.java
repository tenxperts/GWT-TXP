package tenx.gwt.portfolio.client.detail;

import tenx.gwt.portfolio.client.common.Presenter;

public interface PortfolioDetailsPresenter extends Presenter {
	public void onRefresh();
	public void onNext();
	public void onPrev();
	public void setPortfolioId(int portfolioId);
}

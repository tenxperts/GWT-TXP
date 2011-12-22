package tenx.gwt.portfolio.client.detail;

import tenx.gwt.portfolio.client.rpc.PortfolioServiceAsync;
import tenx.gwt.portfolio.shared.Portfolio;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class PortfolioDetailsPresenterImpl implements PortfolioDetailsPresenter {

	PortfolioServiceAsync portofolioService;
	PortfolioDetailsView portfolioDetailsView;
	private int portfolioId;

	public PortfolioDetailsPresenterImpl(int portfolioId,
			PortfolioDetailsView portfolioDetailsView,
			PortfolioServiceAsync portofolioService) {
		super();
		this.portfolioDetailsView = portfolioDetailsView;
		this.portofolioService = portofolioService;
		this.portfolioId = portfolioId;
	}

	@Override
	public void bind() {
		portfolioDetailsView.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.add(portfolioDetailsView.asWidget());
		onRefresh();
	}

	@Override
	public void onRefresh() {
		portfolioDetailsView.getStatusBar().setText("Loading...");
		portofolioService.getPortfolioDetail(portfolioId, new AsyncCallback<Portfolio>() {
			@Override
			public void onFailure(Throwable caught) {
				portfolioDetailsView.getStatusBar().setText("Loading failed due to server error");
			}

			@Override
			public void onSuccess(Portfolio result) {
				portfolioDetailsView.setData(result);
				portfolioDetailsView.getStatusBar().setText("Data as on " + result.getAsOnDate());
			}
		});
	}
	
	public void setPortfolioId(int portfolioId) {
		this.portfolioId = portfolioId;
		onRefresh();
	}

	@Override
	public void onNext() {
		changePortolio(portfolioId+1);
	}

	private void changePortolio(int portfolioId) {
		History.newItem("portfolios:id="+portfolioId);
	}

	@Override
	public void onPrev() {
		changePortolio(portfolioId-1);
	}

}

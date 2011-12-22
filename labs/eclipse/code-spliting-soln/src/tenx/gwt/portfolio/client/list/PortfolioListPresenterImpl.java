package tenx.gwt.portfolio.client.list;

import java.util.Collection;

import tenx.gwt.portfolio.client.rpc.PortfolioService;
import tenx.gwt.portfolio.client.rpc.PortfolioServiceAsync;
import tenx.gwt.portfolio.shared.PortfolioLite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class PortfolioListPresenterImpl implements PortfolioListPresenter {
	private PortfolioServiceAsync portfolioServiceAsync = GWT.create(PortfolioService.class);
	private PortfolioListView portfolioListView;
	
	public PortfolioListPresenterImpl(final PortfolioListView portfolioListView) {
		super();
		this.portfolioListView = portfolioListView;
		portfolioServiceAsync.getPortfoliosList(new AsyncCallback<Collection<PortfolioLite>>() {
			@Override
			public void onSuccess(Collection<PortfolioLite> portfolios) {
				portfolioListView.setData(portfolios);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("remote call failed");
			}
		});
	}
	
	public void bind() {
		portfolioListView.setPresenter(this);
	}

	@Override
	public void go(HasWidgets cotainer) {
		cotainer.add(portfolioListView.asWidget());
	}

	@Override
	public void onPortfolioSelected(int id) {
		History.newItem("portfolio:id=" +id);
	}

}

package tenx.gwt.portfolio.client;

import java.util.Collection;

import tenx.gwt.portfolio.client.rpc.PortfolioService;
import tenx.gwt.portfolio.client.rpc.PortfolioServiceAsync;
import tenx.gwt.portfolio.shared.PortfolioLite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class PortfolioListWidget extends Composite {

	private PortfolioServiceAsync portfolioServiceAsync = GWT.create(PortfolioService.class);
	private FlexTable portfolioList;
	
	public PortfolioListWidget() {
		portfolioList = new FlexTable();
		portfolioList.setBorderWidth(1);
		portfolioList.setCellSpacing(0);
		
		portfolioList.setWidget(0, 0, new Label("Name"));
		portfolioList.setWidget(0, 1, new Label("Description"));
		initWidget(portfolioList);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		portfolioServiceAsync.getPortfoliosList(new AsyncCallback<Collection<PortfolioLite>>() {
			@Override
			public void onSuccess(Collection<PortfolioLite> portfolios) {
				int row = 1;
				for(final PortfolioLite portfolio:portfolios) {
					Anchor portfolioLink = new Anchor(portfolio.getName());
					portfolioLink.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							History.newItem("portfolio:id=" + portfolio.getId());
						}
					});
					portfolioList.setWidget(row, 0, portfolioLink);
					portfolioList.setWidget(row, 1, new Label(portfolio.getName()));
					row++;
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("remote call failed");
			}
		});
	}
	
}

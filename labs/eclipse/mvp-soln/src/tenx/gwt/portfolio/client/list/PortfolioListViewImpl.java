package tenx.gwt.portfolio.client.list;

import java.util.Collection;

import tenx.gwt.portfolio.shared.PortfolioLite;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

public class PortfolioListViewImpl extends Composite implements PortfolioListView {

	private PortfolioListPresenter portfolioListPresenter;
	
	private FlexTable portfolioList;
	
	public PortfolioListViewImpl() {
		portfolioList = new FlexTable();
		portfolioList.setBorderWidth(1);
		portfolioList.setCellSpacing(0);
		
		portfolioList.setWidget(0, 0, new Label("Name"));
		portfolioList.setWidget(0, 1, new Label("Description"));
		initWidget(portfolioList);
	}

	public void setData(Collection<PortfolioLite> portfolios) {
		int row = 1;
		for(final PortfolioLite portfolio:portfolios) {
			Anchor portfolioLink = new Anchor(portfolio.getName());
			portfolioLink.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					portfolioListPresenter.onPortfolioSelected(portfolio.getId());
				}
			});
			portfolioList.setWidget(row, 0, portfolioLink);
			portfolioList.setWidget(row, 1, new Label(portfolio.getName()));
			row++;
		}
	}

	@Override
	protected void onLoad() {
		super.onLoad();
	}

	@Override
	public void setPresenter(PortfolioListPresenter presenter) {
		this.portfolioListPresenter = presenter;
	}
}

package tenx.gwt.portfolio.client;

import java.util.Date;
import java.util.List;

import tenx.gwt.portfolio.shared.PortfolioEntry;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PortfolioDetailsWidget extends Composite {

	
	public PortfolioDetailsWidget() {
		super();
		FlexTable portfolioEntriesList = new FlexTable();
		initHeaders(portfolioEntriesList);
		fetchAndPopulateData(portfolioEntriesList);
		portfolioEntriesList.setBorderWidth(1);
		portfolioEntriesList.setCellSpacing(0);
		portfolioEntriesList.setWidth("100%");
		
		HorizontalPanel buttons = new HorizontalPanel();
		
		buttons.setSpacing(10);
		
		buttons.add(new Button("Refresh"));
		buttons.add(new Button("Add New"));
		buttons.add(new Button("Delete"));
		
		VerticalPanel container = new VerticalPanel();
		container.add(buttons);
		container.add(portfolioEntriesList);
		
		container.add(new Label("Data as of " + new Date()));
		
		container.setWidth("100%");
		
		initWidget(container);
	}

	private void fetchAndPopulateData(FlexTable portfolioListWidget) {
		List<PortfolioEntry> portfolioEntries = PortfolioUtil.getDummyData();
		int row = 1;
		for(PortfolioEntry portfolioEntry:portfolioEntries) {
			int col = 0;
			
			portfolioListWidget.setWidget(row, col, new CheckBox());
			portfolioListWidget.getCellFormatter().setWidth(row, col++, "15em"); 
			
			
			portfolioListWidget.setWidget(row, col++, new Label(portfolioEntry.getSymbol()));
			portfolioListWidget.getCellFormatter().addStyleName(row, col, "flex-table-numeric");
			portfolioListWidget.setWidget(row, col++, new Label(portfolioEntry.getQuantity().toString()));
			portfolioListWidget.getCellFormatter().addStyleName(row, col, "flex-table-numeric");
			portfolioListWidget.setWidget(row, col++, new Label(Float.toString(portfolioEntry.getPurchasePrice())));
			portfolioListWidget.getCellFormatter().addStyleName(row, col, "flex-table-numeric");
			portfolioListWidget.setWidget(row, col++, new Label(Float.toString(portfolioEntry.getMarketPrice())));
			portfolioListWidget.getCellFormatter().addStyleName(row, col, "flex-table-numeric");
			portfolioListWidget.setWidget(row, col++, new Label(Float.toString(portfolioEntry.getProfitOrLoss())));
			row++;
		}
	}

	


	private void initHeaders(FlexTable portfolioList) {
		int col = 0;
		portfolioList.getRowFormatter().addStyleName(0, "portfolio-table-header");
		portfolioList.setWidget(0, col++, new CheckBox());
		portfolioList.setWidget(0, col++, new Label("Symbol"));
		portfolioList.setWidget(0, col++, new Label("Quantity"));
		portfolioList.setWidget(0, col++, new Label("Purchase Price"));
		portfolioList.setWidget(0, col++, new Label("Curent Price"));
		portfolioList.setWidget(0, col++, new Label("Profit / Loss"));
	}
	
	

	
	
}

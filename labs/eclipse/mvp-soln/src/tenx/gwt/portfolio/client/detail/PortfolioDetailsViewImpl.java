package tenx.gwt.portfolio.client.detail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tenx.gwt.portfolio.client.NewPortfolioEntryForm;
import tenx.gwt.portfolio.client.PortfolioConstants;
import tenx.gwt.portfolio.client.PortfolioMessages;
import tenx.gwt.portfolio.shared.Portfolio;
import tenx.gwt.portfolio.shared.PortfolioEntry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PortfolioDetailsViewImpl extends Composite implements ClickHandler, PortfolioDetailsView {
	
	PortfolioConstants constants = GWT.create(PortfolioConstants.class);
	PortfolioMessages messages = GWT.create(PortfolioMessages.class);
	NumberFormat currencyFormat = NumberFormat.getCurrencyFormat();
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_SHORT);
	
	private Button refreshButton;
	private Button addNewButton;
	private Button deleteButton;
	private Button nextPortfolio;
	private Button prevPortfolio;
	
	private ClickHandler checkBoxHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			CheckBox checkBox = (CheckBox) event.getSource();
			if (checked.contains(checkBox)) {
				unchecked.add(checkBox);
				checked.remove(checkBox);
			} else {
				checked.add(checkBox);
				unchecked.remove(checkBox);
			}
			updateDeleteButtonState();
		}
	};
	
	private Set<CheckBox> checked = new HashSet<CheckBox>();
	private Set<CheckBox> unchecked = new HashSet<CheckBox>();

	private Label statusBar;

	private FlexTable portfolioEntriesList;

	private PortfolioDetailsPresenter presenter;

	public PortfolioDetailsViewImpl() {
		super();
		portfolioEntriesList = new FlexTable();
		portfolioEntriesList.setBorderWidth(1);
		portfolioEntriesList.setCellSpacing(0);
		portfolioEntriesList.setWidth("100%");
		
		HorizontalPanel buttons = new HorizontalPanel();
		
		buttons.setSpacing(10);
		
		Anchor home = new Anchor("Home");
		home.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("home");
			}
		});
		
		buttons.add(home);
		
		Anchor backToList = new Anchor("Back to List");
		backToList.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("list");
			}
		});
		
		buttons.add(backToList);
		
		refreshButton = new Button(constants.buttonRefresh(),this);
		buttons.add(refreshButton);
		
		addNewButton = new Button(constants.buttonAddNew(),this);
		buttons.add(addNewButton);
		
		deleteButton = new Button(constants.buttonDelete(),this);
		deleteButton.setEnabled(false);
		buttons.add(deleteButton);

		nextPortfolio = new Button("Next",this);
		buttons.add(nextPortfolio);
		
		prevPortfolio = new Button("Prev",this);
		buttons.add(prevPortfolio);
		
		VerticalPanel container = new VerticalPanel();
		container.add(buttons);
		container.add(portfolioEntriesList);
		
		statusBar = new Label();
		
		container.add(statusBar);
		
		container.setWidth("100%");

		initHeaders(portfolioEntriesList);
		initWidget(container);
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
	}

	private void populateData(FlexTable portfolioListWidget,
			List<PortfolioEntry> portfolioEntries) {
		int row = 1;
		for(PortfolioEntry portfolioEntry:portfolioEntries) {
			int col = 0;
			
			CheckBox checkBox = new CheckBox();
			unchecked.add(checkBox);
			checkBox.addClickHandler(checkBoxHandler);
			
			portfolioListWidget.setWidget(row, col, checkBox);
			portfolioListWidget.getCellFormatter().setWidth(row, col++, "15em"); 
			portfolioListWidget.setWidget(row, col++, new Label(portfolioEntry.getSymbol()));
			portfolioListWidget.getCellFormatter().addStyleName(row, col, "flex-table-numeric");
			portfolioListWidget.setWidget(row, col++, new Label(portfolioEntry.getQuantity().toString()));
			portfolioListWidget.getCellFormatter().addStyleName(row, col, "flex-table-numeric");
			portfolioListWidget.setWidget(row, col++, new Label(currencyFormat.format(portfolioEntry.getPurchasePrice())));
			portfolioListWidget.getCellFormatter().addStyleName(row, col, "flex-table-numeric");
			portfolioListWidget.setWidget(row, col++, new Label(currencyFormat.format(portfolioEntry.getMarketPrice())));
			portfolioListWidget.getCellFormatter().addStyleName(row, col, "flex-table-numeric");
			portfolioListWidget.setWidget(row, col++, new Label(currencyFormat.format(portfolioEntry.getProfitOrLoss())));
			row++;
		}
	}


	private void initHeaders(FlexTable portfolioList) {
		int col = 0;
		portfolioList.getRowFormatter().addStyleName(0, "portfolio-table-header");
		
		CheckBox controllingCheckBox = new CheckBox();
		controllingCheckBox.addClickHandler(new ClickHandler() {
			boolean isChecked = false;
			@Override
			public void onClick(ClickEvent event) {
				isChecked = !isChecked;
				if (isChecked) {
					for(CheckBox checkBox:unchecked) {
						checkBox.setValue(true);
						checked.add(checkBox);
					}
					unchecked.clear();
				} else {
					for(CheckBox checkBox:checked) {
						checkBox.setValue(false);
						unchecked.add(checkBox);
					}
					checked.clear();
				}
				updateDeleteButtonState();
			}
		});
		
		portfolioList.setWidget(0, col++, controllingCheckBox);
		portfolioList.setWidget(0, col++, new Label(constants.labelSymbol()));
		portfolioList.setWidget(0, col++, new Label(constants.labelQuantity()));
		portfolioList.setWidget(0, col++, new Label(constants.labelPurchasePrice()));
		portfolioList.setWidget(0, col++, new Label(constants.labelCurrentPrice()));
		portfolioList.setWidget(0, col++, new Label(constants.labelProfitLoss()));
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == refreshButton) {
			presenter.onRefresh();
		} else if (event.getSource() == addNewButton) {
		 	final DialogBox newEntryDialog =  new DialogBox();
		 	newEntryDialog.setText("Add new portfolio entry");
		 	NewPortfolioEntryForm newPortfolioEntryDialog = new NewPortfolioEntryForm();
		 	newPortfolioEntryDialog.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					newEntryDialog.hide();					
				}
			});
			newEntryDialog.setWidget(newPortfolioEntryDialog);
		 	newEntryDialog.center();
		} else if (event.getSource() == deleteButton) {
			Window.alert("TODO - implement delete");
		} else if (event.getSource() == nextPortfolio) {
			presenter.onNext();
		} else if (event.getSource() == prevPortfolio) {
			presenter.onPrev();
		}
		
	}

	@Override
	public void setData(Portfolio portfolio) {
		portfolioEntriesList.clear();
		initHeaders(portfolioEntriesList);
		populateData(portfolioEntriesList, portfolio.getEntries());
	}
	
	@Override
	public HasText getStatusBar() {
		return statusBar;
	}

	private void updateDeleteButtonState() {
		deleteButton.setEnabled(checked.size() > 0);
	}


	@Override
	public void setPresenter(PortfolioDetailsPresenter presenter) {
		this.presenter = presenter;
	}

	
}

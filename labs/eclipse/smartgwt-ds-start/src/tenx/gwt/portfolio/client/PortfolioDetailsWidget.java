package tenx.gwt.portfolio.client;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import tenx.gwt.portfolio.client.rpc.PortfolioService;
import tenx.gwt.portfolio.client.rpc.PortfolioServiceAsync;
import tenx.gwt.portfolio.shared.Portfolio;
import tenx.gwt.portfolio.shared.PortfolioEntry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PortfolioDetailsWidget extends Composite implements ClickHandler {
	
	private static final String GRID_FIELD_ID = "ID";

	private static final String GRID_FIELD_PROFIT_LOSS = "profitLoss";

	private static final String GRID_FIELD_MARKET_PRICE = "marketPrice";

	private static final String GRID_FIELD_PURCHASE_PRICE = "purchasePrice";

	private static final String GRID_FIELD_QUANTITY = "quantity";

	private static final String GRID_FIELD_SYMBOL = "symbol";

	private static final Logger LOGGER = Logger.getLogger(PortfolioDetailsWidget.class.getName());
	
	PortfolioServiceAsync portofolioService = GWT.create(PortfolioService.class);
	
	PortfolioConstants constants = GWT.create(PortfolioConstants.class);
	PortfolioMessages messages = GWT.create(PortfolioMessages.class);
	NumberFormat currencyFormat = NumberFormat.getCurrencyFormat();
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_SHORT);
	
	private IButton refreshButton;
	private IButton addNewButton;
	private IButton deleteButton;
	private IButton nextPortfolio;
	private IButton prevPortfolio;
	
	private Label statusBar;
	
	private ListGrid listGrid;
	
	private int portFolioId  = 1;

	public PortfolioDetailsWidget(int portfolioId) {
		super();
		this.portFolioId = portfolioId;
		HLayout buttons = new HLayout();
		buttons.setWidth100();
		buttons.setMembersMargin(5);
		
		refreshButton = new IButton(constants.buttonRefresh(),this);
		buttons.addMember(refreshButton);
		
		addNewButton = new IButton(constants.buttonAddNew(),this);
		buttons.addMember(addNewButton);
		
		deleteButton = new IButton(constants.buttonDelete(),this);
		deleteButton.disable();
		buttons.addMember(deleteButton);

		nextPortfolio = new IButton("Next");
		buttons.addMember(nextPortfolio);
		
		prevPortfolio = new IButton("Prev");
		buttons.addMember(prevPortfolio);
		
		VLayout container = new VLayout();
		container.setWidth100();
		container.setMembersMargin(10);
		container.addMember(buttons);
		
		listGrid = new ListGrid();
		listGrid.setMinHeight(100);
		listGrid.setHeight(200);
		listGrid.setWidth("65%");
		listGrid.setCanResizeFields(false);
		listGrid.setCanAutoFitFields(false);
		listGrid.setCanEdit(false);
		listGrid.setCanFreezeFields(false);
		listGrid.setCanGroupBy(false);
		listGrid.setCanPickFields(false);
		
		initializeGrid(listGrid);
		
		HLayout gridAndForm = new HLayout();
		gridAndForm.setWidth100();
		gridAndForm.setMembersMargin(5);
		gridAndForm.addMember(listGrid);
		
	
		portfolioEntryEditForm = new DynamicForm();
		portfolioEntryEditForm.setTitle("Edit Portfolio Entry");
		
		ButtonItem saveButtonItem = new ButtonItem("save", "Save");
		saveButtonItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(
					com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				Window.alert("Saving data");
			}
		});
		
		portfolioEntryEditForm.setFields(
					new HeaderItem("header","Edit porftolio Entry"),
					new TextItem(GRID_FIELD_SYMBOL,constants.labelSymbol()),
					new SpinnerItem(GRID_FIELD_QUANTITY,constants.labelQuantity()),
					new TextItem(GRID_FIELD_PURCHASE_PRICE,constants.labelPurchasePrice()),
					new DateItem("purchaseDate","Purchase Date"),
					saveButtonItem);

		portfolioEntryEditForm.setBorder("1px solid black");
		portfolioEntryEditForm.setCellPadding(5);
		
		
		gridAndForm.addMember(portfolioEntryEditForm);
		
		container.addMember(gridAndForm);
		
		statusBar = new Label();
		
		updateStatusBar(new Date());
		container.addMember(statusBar);
		
		container.setWidth("100%");


		initWidget(container);
		
		fetchAndPopulateData();
	}
	
	


	Timer autoRefreshTimer;

	private DynamicForm portfolioEntryEditForm;

	@Override
	protected void onLoad() {
		super.onLoad();
		LOGGER.info("Setting up auto refresh timer");
		autoRefreshTimer = new Timer() {
			@Override
			public void run() {
				LOGGER.info("Refresh timer triggered");
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						refresh();
					}
				});
				LOGGER.info("Timer execution complete");
			}
		};
		//autoRefreshTimer.scheduleRepeating(30000);
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		LOGGER.info("Cancel auto refresh timer");
		autoRefreshTimer.cancel();
	}



	private void updateStatusBar(Date date) {
		statusBar.setText(messages.statusMessage( dateFormat.format(date)));
	}

	private void fetchAndPopulateData() {
		LOGGER.info("Make server call to get portfolio for id " + portFolioId);
		statusBar.setText("Loading...");
		portofolioService.getPortfolioDetail(portFolioId, new AsyncCallback<Portfolio>() {
			@Override
			public void onFailure(Throwable caught) {
				LOGGER.log(Level.SEVERE,"getPortfolioDetail failed",caught);
				Window.alert("Error fetching portfolio details : " + caught.getMessage());
				statusBar.setText("Failed to load portfolio");
			}

			@Override
			public void onSuccess(Portfolio result) {
				LOGGER.info("Fetch portfolio details successfully");
				updateListGrid(result.getEntries());
				updateStatusBar(result.getAsOnDate());
			}
		});
	}

	private void initializeGrid(ListGrid listGrid) {
		ListGridField id = new ListGridField(GRID_FIELD_ID,GRID_FIELD_ID);
		id.setHidden(true);
		
		ListGridField symbol = new ListGridField(GRID_FIELD_SYMBOL,constants.labelSymbol());
		ListGridField quantity = new ListGridField(GRID_FIELD_QUANTITY,constants.labelQuantity());		
		quantity.setAlign(Alignment.RIGHT);
		ListGridField purchasePrice = new ListGridField(GRID_FIELD_PURCHASE_PRICE,constants.labelPurchasePrice());
		ListGridField marketPrice = new ListGridField(GRID_FIELD_MARKET_PRICE,constants.labelCurrentPrice());
		ListGridField profitLoss = new ListGridField(GRID_FIELD_PROFIT_LOSS,constants.labelProfitLoss());
		
		listGrid.setFields(id,
				symbol
				,quantity,purchasePrice,marketPrice,profitLoss
				);
		
		listGrid.addSelectionChangedHandler(new SelectionChangedHandler() {
			
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				if (event.getSelectedRecord() != null) {
					updateForm(event.getSelectedRecord());
				} else {
					portfolioEntryEditForm.resetValues();
				}
				if (event.getSelectedRecord() != null) { 
					deleteButton.enable(); 
				} else {
					deleteButton.disable(); 
				}
			}
		});
		
	}

	protected void updateForm(ListGridRecord selectedRecord) {
		portfolioEntryEditForm.setValue(GRID_FIELD_SYMBOL, selectedRecord.getAttribute(GRID_FIELD_SYMBOL));
		portfolioEntryEditForm.setValue(GRID_FIELD_QUANTITY, selectedRecord.getAttribute(GRID_FIELD_QUANTITY));
		portfolioEntryEditForm.setValue(GRID_FIELD_PURCHASE_PRICE, selectedRecord.getAttribute(GRID_FIELD_PURCHASE_PRICE));
	}

	protected void updateListGrid(List<PortfolioEntry> entries) {
		ListGridRecord[] records = new ListGridRecord[entries.size()];
		int i = 0;
		for(PortfolioEntry portfolioEntry:entries) {
			ListGridRecord record = new ListGridRecord();
			record.setAttribute(GRID_FIELD_ID, portfolioEntry.getId());
			record.setAttribute(GRID_FIELD_SYMBOL,portfolioEntry.getSymbol());
			record.setAttribute(GRID_FIELD_QUANTITY,portfolioEntry.getQuantity());
			record.setAttribute(GRID_FIELD_PURCHASE_PRICE, portfolioEntry.getPurchasePrice());
			record.setAttribute(GRID_FIELD_MARKET_PRICE,portfolioEntry.getMarketPrice());
			record.setAttribute(GRID_FIELD_PROFIT_LOSS,portfolioEntry.getProfitOrLoss());
			records[i++] = record;
		}
		listGrid.setData(records);
		LOGGER.info("set records on to grid ");
	}


	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == refreshButton) {
			//Window.alert("TODO - implement refresh");
			refresh();
			
		} else if (event.getSource() == addNewButton) {
		 	final com.smartgwt.client.widgets.Window popupWindow = new com.smartgwt.client.widgets.Window();
		 	popupWindow.setTitle("New portfolio entry");
		 	NewPortfolioEntryForm newPortfolioEntryDialog = new NewPortfolioEntryForm();
		 	newPortfolioEntryDialog.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {
				@Override
				public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
					popupWindow.hide();				
				}
			});
		 	popupWindow.setIsModal(true);
		 	popupWindow.setAutoSize(true);
		 	popupWindow.setAutoCenter(true);
		 	popupWindow.setModalMaskOpacity(10);
		 	popupWindow.addItem(newPortfolioEntryDialog);
		 	popupWindow.show();
		} else if (event.getSource() == deleteButton) {
			Window.alert("Deleting portfolio entry with id " + listGrid.getSelectedRecord().getAttribute(GRID_FIELD_ID));
			
		} else if (event.getSource() == nextPortfolio) {
			portFolioId++;
			refresh();
		} else if (event.getSource() == prevPortfolio) {
			portFolioId--;
			refresh();
		}
		
	}

	private void refresh() {
		LOGGER.info("Clearing list for refresh");
		listGrid.setData(new ListGridRecord[0]);
		fetchAndPopulateData();
	}
}
